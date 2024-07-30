
package com.veradat.http.client.service;

import java.util.List;

import com.veradat.domain.exception.CustomValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.veradat.domain.exception.OutportException;
import com.veradat.domain.model.notifications.NotificationDTO;
import com.veradat.domain.outputport.sync.NotificationsSyncOuputPort;
import com.veradat.http.client.adapter.HTTPClientAdapter;


@Service
public class NotificationsSyncOutputPortService implements NotificationsSyncOuputPort {
	
	private final Logger logger = LoggerFactory.getLogger(NotificationsSyncOutputPortService.class);

    private HTTPClientAdapter httpClientAdapter;

    @Autowired
	public NotificationsSyncOutputPortService(HTTPClientAdapter httpClientAdapter) {
		this.httpClientAdapter = httpClientAdapter;
	}

	@Override
	public List<NotificationDTO> getNotifications(String crimeType) throws CustomValidationException {
		try {
			return httpClientAdapter.exchangeForCollectionService(NotificationDTO.class, "OBTAIN_NOTIFICATIONS", null, crimeType);
		} catch (Exception e) {
			logger.error("Ha ocurrido un error al intentar consultar la información de usuarios: ", e);
		}
	}

}
