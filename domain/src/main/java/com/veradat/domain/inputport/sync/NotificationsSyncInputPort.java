/*******************************************************************************
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 * 	
 * @(#) NotificationsSyncInputPort.java
 * Contain the methods declaration to call use case and get dashboard notifications
 *
 * @author Oscar Gerardo Trejo Rivera
 * @version 1.0
 * @createdAt 19/12/2023
 * @updatedAt 19/12/2023
 *******************************************************************************/
package com.veradat.domain.inputport.sync;

import java.util.List;

import com.veradat.domain.exception.CustomValidationException;
import com.veradat.domain.exception.OutportException;
import com.veradat.domain.model.notifications.NotificationsResponseDTO;

public interface NotificationsSyncInputPort {
	
	/**
	 * This function calls the use case to obtain all notifications
	 * 
	 * @param String crimeType is the crime to filter notifications
	 * @return List<NotificationsResponseDTO> is the list with notifications object
	 * @throws OutportException, NotFoundException, CustomValidationException
	 * 
	 */
	List<NotificationsResponseDTO> getNotifications(String operationArea) throws CustomValidationException;

}
