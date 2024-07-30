/*******************************************************************************
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 * 	
 * @(#) HTTPClientAdapter.java
 * Is the adapter to consumes external services
 *
 * @author Diego Acosta
 * @version 1.0
 * @createdAt 19/12/2023
 * @updatedAt 19/12/2023
 *******************************************************************************/
package com.veradat.http.client.adapter;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.veradat.domain.exception.NonRetriableException;
import com.veradat.http.client.cfg.RestTemplateConfig;
import com.veradat.lib.security.DetailsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@ConfigurationProperties(prefix = "veradat")
@Import(value = RestTemplateConfig.class)
@Service
public class HTTPClientAdapter {

    private static final Logger logger = LoggerFactory.getLogger(HTTPClientAdapter.class);
    private static final String CLASS_NAME = "ClassName";
    private static final String PROCESS_UUID = "ProcessUUID";
    private static final String MESSAGE_UUID = "MessageUUID";
    private static final String EXCEPTION_UUID = "ExceptionUUID";

  
    private DiscoveryClient discoveryClient;

    private RestTemplate restTemplate;
    
	private DetailsService detailsService;
	
	@Autowired
    public HTTPClientAdapter(DiscoveryClient discoveryClient, RestTemplate restTemplate, DetailsService detailsService) {
    	this.discoveryClient = discoveryClient;
    	this.restTemplate = restTemplate;
    	this.detailsService = detailsService;
    }
    
    
    @Getter
    @Setter
    public List<Map<String, Object>> restClients;

   
    public <T, R> T exchangeService(Class<T> responseClazz, String serviceId, R requestBody, Object... pathVariables) throws NonRetriableException {

        Optional<RestClientDetails> clientDetails = restClients
                .stream()
                .filter(clients -> clients.containsKey("name") && clients.containsValue(serviceId))
                .findFirst().map(HTTPClientAdapter::mapToClientDetails);

        try {
            String url = buildURL(clientDetails.get().getController(), clientDetails.get().getPath(), clientDetails.get().getMethod());
            HttpEntity<R> requestObject = null;
            logger.info("Access URL: {} {}", clientDetails.get().getMethod(), url);
            logger.info("token: {}", detailsService.getJwtToken());
            HttpHeaders headers = new HttpHeaders();
			headers.set("authorization-vdt", detailsService.getJwtToken());
            headers.setContentType(MediaType.APPLICATION_JSON);
            if(headers == null) {
            	requestObject = new HttpEntity<>(requestBody);
            }else {
            	requestObject = new HttpEntity<>(requestBody, headers);
            }
            
            ResponseEntity<String> responseService = (pathVariables != null && pathVariables.length > 0)
                    ? restTemplate.exchange(url, HttpMethod.valueOf(clientDetails.get().getMethod().toUpperCase()), requestObject, String.class, pathVariables)
                    : restTemplate.exchange(url, HttpMethod.valueOf(clientDetails.get().getMethod().toUpperCase()), requestObject, String.class);

            ObjectMapper mapper = new ObjectMapper();

            JavaType javaType = mapper.getTypeFactory().constructType(responseClazz);

            if (responseService != null && responseService.getStatusCode().is2xxSuccessful()) {
                return mapper.readValue(responseService.getBody(), javaType);
            } else {
                logger.error("Error en la solicitud. Motivo: La repuesta en el servicio es nula.");
                throw new NonRetriableException("Error en la solicitud: " + responseService.getStatusCode(),
                        MESSAGE_UUID,CLASS_NAME,clientDetails.get().getMethod().toUpperCase(),null,PROCESS_UUID,EXCEPTION_UUID, responseService.getStatusCode().value());
            }

        } catch (HttpClientErrorException e) {
            throw new NonRetriableException("Error en la solicitud: " + e.getMessage() + " Code: " + e.getStatusCode().value(), MESSAGE_UUID, CLASS_NAME,
                    clientDetails.get().getMethod().toUpperCase(), null, PROCESS_UUID, EXCEPTION_UUID,  e.getStatusCode().value() ) ;
        } catch(JsonProcessingException e) {
            throw new NonRetriableException("Error en la solicitud: " + e.getMessage() + " Code: " + null, MESSAGE_UUID, CLASS_NAME,
                    clientDetails.get().getMethod().toUpperCase(), null, PROCESS_UUID, EXCEPTION_UUID,  null);
        }
    }

    public <T, R> List<T> exchangeForCollectionService(Class<T> responseClazz, String serviceId, R requestBody, Object... pathVariables) throws NonRetriableException {

        Optional<RestClientDetails> clientDetails = restClients

                .stream()

                .filter(clients -> clients.containsKey("name") && clients.containsValue(serviceId))

                .findFirst().map(HTTPClientAdapter::mapToClientDetails);



        try {
        	HttpEntity<R> requestObject = null;
            String url = buildURL(clientDetails.get().getController(), clientDetails.get().getPath(), clientDetails.get().getMethod());
            logger.info("Access URL: {}", url);

            HttpHeaders headers = new HttpHeaders();
            logger.info("token: {}", detailsService.getJwtToken());
			headers.set("authorization-vdt", detailsService.getJwtToken());
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            if(headers == null) {
            	requestObject = new HttpEntity<>(requestBody);
            }else {
            	requestObject = new HttpEntity<>(requestBody, headers);
            }
           
            ResponseEntity<String> responseService = (pathVariables != null && pathVariables.length > 0)

                    ? restTemplate.exchange(url, HttpMethod.valueOf(clientDetails.get().getMethod().toUpperCase()), requestObject, String.class, pathVariables)

                    : restTemplate.exchange(url, HttpMethod.valueOf(clientDetails.get().getMethod().toUpperCase()), requestObject, String.class);

            ObjectMapper mapper = new ObjectMapper();

            CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, responseClazz);

            if (responseService != null && responseService.getStatusCode().is2xxSuccessful()) {

                return mapper.readValue(responseService.getBody(), collectionType);

            } else {

                logger.error("Error en la solicitud. Motivo: La repuesta en el servicio es nula.");

                throw new NonRetriableException("Error en la solicitud: " + responseService.getStatusCode(), MESSAGE_UUID, CLASS_NAME,

                        clientDetails.get().getMethod().toUpperCase(), null, PROCESS_UUID, EXCEPTION_UUID, responseService.getStatusCode().value());

            }

        } catch (HttpClientErrorException e) {

            throw new NonRetriableException("Error en la solicitud: " + e.getMessage() + " Code: " + e.getStatusCode().value(), MESSAGE_UUID, CLASS_NAME,

                    clientDetails.get().getMethod().toUpperCase(), null, PROCESS_UUID, EXCEPTION_UUID,  e.getStatusCode().value());

        } catch(JsonProcessingException e) {

            throw new NonRetriableException("Error en la solicitud: " + e.getMessage() + " Code: " + null, MESSAGE_UUID, CLASS_NAME,

                    clientDetails.get().getMethod().toUpperCase(), null, PROCESS_UUID, EXCEPTION_UUID,  null);

        }

    }



    private String buildURL(@NotNull @NotBlank String service, @NotNull @NotBlank String servicePath, @NotNull @NotBlank String method) throws NonRetriableException {

        String url = null;

        List<ServiceInstance> instances = discoveryClient.getInstances(service);

        if(instances == null || instances.isEmpty()) {

            logger.error("Error en la construcción de URL. ARGS, Service: {}, Path: {}, Method: {}", service, servicePath, method);

            throw new NonRetriableException("Error en la construcción de URL: " + HttpStatus.NOT_FOUND, MESSAGE_UUID, CLASS_NAME,

                    method, null, "processUuid", "exceptionUuid", HttpStatus.NOT_FOUND.value());

        }

        url = instances.get(0).getUri() + servicePath;

        if(url.isBlank()) {

            logger.error("Error en la construcción de URL. La url es nula.");

            throw new NonRetriableException("Error en la construcción de URL: " + HttpStatus.NOT_FOUND, MESSAGE_UUID, CLASS_NAME,

                    "postMethod", null, PROCESS_UUID, EXCEPTION_UUID, HttpStatus.NOT_FOUND.value());

        }

        return url;

    }



    private static RestClientDetails mapToClientDetails(Map<String, Object> clientDetailsMap) {

        RestClientDetails clientDetails = new RestClientDetails();

        clientDetails.setName((String) clientDetailsMap.get("name"));

        clientDetails.setController((String) clientDetailsMap.get("controller"));

        clientDetails.setPath((String) clientDetailsMap.get("path"));

        clientDetails.setMethod((String) clientDetailsMap.get("method"));

        return clientDetails;



    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RestClientDetails {

        private String name;
        private String controller;
        private String path;
        private String method;
    }

}