/*******************************************************************************
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 * 	
 * @(#) NotificationsResponseDTO.java
 * Is the response to the notification component of the micro service 
 *
 * @author Oscar Gerardo Trejo Rivera
 * @version 1.0
 * @createdAt 19/12/2023
 * @updatedAt 19/12/2023
 *******************************************************************************/
package com.veradat.domain.model.notifications;

import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.ToString;


@Data
@ToString(includeFieldNames = true)
public class NotificationsResponseDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String notificationName;
	
	private List<NotificationDTO> notifications;

}
