
package com.veradat.infrastructure.rest.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.veradat.domain.exception.CustomValidationException;
import com.veradat.domain.exception.OutportException;
import com.veradat.domain.inputport.sync.NotificationsSyncInputPort;
import com.veradat.domain.model.notifications.NotificationsResponseDTO;
import com.veradat.infrastructure.rest.controller.NotificationsController;
import com.veradat.lib.security.annotations.VeradatAuthority;

import jakarta.validation.Valid;


@RestController
public class NotificationsControllerImpl implements NotificationsController {
	
	private NotificationsSyncInputPort notificationsSyncInputPort;
	
	@Autowired
	public NotificationsControllerImpl(NotificationsSyncInputPort notificationsSyncInputPort) {
		this.notificationsSyncInputPort = notificationsSyncInputPort;
	}


	@Override
	public ResponseEntity<List<NotificationsResponseDTO>> getNotifications(@RequestHeader HttpHeaders header,
			@Valid String operationArea)
			throws  CustomValidationException {
		return new ResponseEntity<>(notificationsSyncInputPort.getNotifications(operationArea), HttpStatus.OK);
	}

}