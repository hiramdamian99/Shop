
package com.veradat.infrastructure.rest.controller;

import com.veradat.domain.exception.CustomValidationException;
import com.veradat.domain.exception.OutportException;
import com.veradat.domain.model.ErrorMessage;
import com.veradat.domain.model.notifications.NotificationsResponseDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("api/v1/dashboards")
public interface NotificationsController {
	
	
	/**
	 * This function expose the end point to get the different notifications
	 * 
	 * @param HttpHeaders header is the token information 
	 * @param String operationArea is the client area
	 * @param String crimeType is the crime type of notifications
	 * @return  ResponseEntity <List<NotificationsResponseDTO>> is the list with every notifications founded
	 * @throws OutportException, NotFoundException, CustomValidationException
	 * 
	 */
	@ApiResponse(responseCode = "200", content = {
			@Content(schema = @Schema(implementation = NotificationsResponseDTO.class), mediaType = "application/json"), }, description = "OK")
	@ApiResponse(responseCode = "500", content = {
			@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json") }, description = "Internal server error")
	@ApiResponse(responseCode = "400", content = {
			@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json") }, description = "Bad request error")
	@ApiResponse(responseCode = "404", content = {
			@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json") }, description = "Not Found")
	@GetMapping(value = "/notifications")
    ResponseEntity <List<NotificationsResponseDTO>> getNotifications(HttpHeaders header,
    		 @RequestParam("operationArea")  @Valid @NotBlank(message = "Debe ingresarse el area de operación") String operationArea) throws OutportException, CustomValidationException;

}
