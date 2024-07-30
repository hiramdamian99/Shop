/*******************************************************************************
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 * 	
 * @(#) ControllerExceptionHandler.java
 * This class management the possible exceptions of the business
 *
 * @author Oscar Gerardo Trejo Rivera
 * @version 1.0
 * @createdAt 19/12/2023
 * @updatedAt 19/12/2023
 *******************************************************************************/


package com.veradat.infrastructure.rest.exception;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.validation.FieldError;

import com.veradat.domain.exception.ExceptionUtils;
import com.veradat.domain.exception.OutportException;
import com.veradat.domain.exception.VeradatException;
import com.veradat.domain.model.ErrorMessage;

@RestControllerAdvice
public class ControllerExceptionHandler {

	@Value("${microservicio}")
	private String idMicroservicio;
	
    private ExceptionUtils exceptionUtils;
    
    @Autowired
    public ControllerExceptionHandler(ExceptionUtils exceptionUtils) {
    	this.exceptionUtils = exceptionUtils;
    }
	
	@ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
	 	protected ResponseEntity<ErrorMessage> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex) {
        ErrorMessage em = exceptionUtils.buildErrorMessage(ex, "El tipo de medio no es admitido");
        return new ResponseEntity<>(em, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    protected ResponseEntity<ErrorMessage> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
        ErrorMessage em = exceptionUtils.buildErrorMessage(ex, "El tipo de datos enviados no son soportados");
        return new ResponseEntity<>(em, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorMessage> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        ErrorMessage em = exceptionUtils.buildErrorMessage(ex, "Tipo de Metodo http no es soportado para la operacion solicitada");
        return new ResponseEntity<>(em, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ErrorMessage> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        ErrorMessage em = exceptionUtils.buildErrorMessage(ex, "El cuerpo del mensaje es requerido y no se envio o no se puede interpretar");
        return new ResponseEntity<>(em, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorMessage> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

        ErrorMessage em = exceptionUtils.buildErrorMessage(ex, "Los argumentos del mensaje no son validos");

        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        for (org.springframework.validation.FieldError fieldError : fieldErrors) {
            em.addFieldError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return new ResponseEntity<>(em, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResponseStatusException.class)
    protected ResponseEntity<ErrorMessage> handleHttpMessageUnauthorized(ResponseStatusException ex) {
        ErrorMessage em = exceptionUtils.buildErrorMessage(ex, "No esta facultado para esta funcionalidad \n Contacte a su supervisor");
        return new ResponseEntity<>(em, ex.getStatusCode());
    }
	
	@ExceptionHandler(VeradatException.class)
    protected ResponseEntity<ErrorMessage> handleVeradatException(VeradatException veradatException) {
        ErrorMessage errorMessage = exceptionUtils.buildErrorMessage(veradatException, veradatException.getErrorMessage());
        return new ResponseEntity<>(errorMessage, veradatException.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorMessage> handleException(Exception exception) {
        ErrorMessage errorMessage = exceptionUtils.buildErrorMessage(exception, "Ocurrió un error inténtelo más tarde");
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(OutportException.class)
    protected ResponseEntity<ErrorMessage> handleException(OutportException exception) {
        ErrorMessage errorMessage = exceptionUtils.buildErrorMessage(exception, exception.getErrorMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
