/*******************************************************************************
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 * 	
 * @(#) NotificationsUseCase.java
 * Contains all business process and validations to get dash board notifications information
 *
 * @author Oscar Gerardo Trejo Rivera
 * @version 1.0
 * @createdAt 19/12/2023
 * @updatedAt 19/12/2023
 *******************************************************************************/
package com.veradat.domain.usecases;

import java.util.ArrayList;
import java.util.List;

import com.veradat.domain.model.notifications.ShopingCart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.veradat.domain.constants.CrimeType;
import com.veradat.domain.exception.CustomValidationException;
import com.veradat.domain.exception.OutportException;
import com.veradat.domain.inputport.sync.NotificationsSyncInputPort;
import com.veradat.domain.model.notifications.NotificationDTO;
import com.veradat.domain.model.notifications.NotificationsResponseDTO;
import com.veradat.domain.outputport.sync.NotificationsSyncOuputPort;

@Service
public class NotificationsUseCase implements NotificationsSyncInputPort {
	

	private final Logger logger = LoggerFactory.getLogger(NotificationsUseCase.class);
	
	private static final String NOT_FOUND_NOTIFICATION = "No hay notificaciones";
	
	private NotificationsSyncOuputPort notificationsSyncOuputPort;
	
	@Autowired
	public NotificationsUseCase(NotificationsSyncOuputPort notificationsSyncOuputPort) {
		this.notificationsSyncOuputPort = notificationsSyncOuputPort;
	}

	@Override
	public List<NotificationsResponseDTO> getShopingCart(ShopingCart shopingCart) throws CustomValidationException {

		List<NotificationsResponseDTO> shopingCart = notificationsSyncOuputPort.getShopingCart(shopingCart);


			List<NotificationsResponseDTO> notificationsResponseDTO = new ArrayList<>();

		return getNotificationsResponseDTO(notifications);
	}


}
