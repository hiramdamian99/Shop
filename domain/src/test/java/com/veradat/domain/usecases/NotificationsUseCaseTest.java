/*******************************************************************************
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 	
 * @(#) NotificationsUseCaseTest.java
 * COntain the Test of the notifications use case
 *
 * @author Oscar Gerardo Trejo Rivera
 * @version 1.0
 * @createdAt 19/12/2023
 * @updatedAt 19/12/2023
 *******************************************************************************/
/**
 * 
 */
package com.veradat.domain.usecases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.veradat.domain.exception.CustomValidationException;
import com.veradat.domain.model.notifications.NotificationDTO;
import com.veradat.domain.model.notifications.NotificationsResponseDTO;
import com.veradat.domain.outputport.sync.NotificationsSyncOuputPort;

import lombok.SneakyThrows;

/**
 * 
 */

@RunWith(SpringRunner.class)
public class NotificationsUseCaseTest {
	
	 @TestConfiguration
	 static class NotificationsUseCaseTestContextConfiguration {
	 
	 }
	 
	 @InjectMocks
	 public NotificationsUseCase notificationsUseCase;
	
	 @Mock
	 private NotificationsSyncOuputPort notificationsSyncOuputPort;
	 
    @Test
	@SneakyThrows
	public void testNotifications1() {
    	List<NotificationDTO> notifications = new ArrayList<>();
    	when(notificationsSyncOuputPort.getNotifications("ENLV")).thenReturn(null);
    	when(notificationsSyncOuputPort.getNotifications("ENPF")).thenReturn(notifications);
    	
    	CustomValidationException exception = assertThrows(CustomValidationException.class, ()-> {
    		notificationsUseCase.getNotifications("ENLV");
    	});
    	
    	assertEquals("No hay notificaciones", exception.getMessage());
    	
    	exception = assertThrows(CustomValidationException.class, ()-> {
    		notificationsUseCase.getNotifications("ENPF");
    	});
    	
    	assertEquals("No hay notificaciones", exception.getMessage());
    	
    	exception = assertThrows(CustomValidationException.class, ()-> {
    		notificationsUseCase.getNotifications("SCCT1");
    	});
    	
    	assertEquals("Se ha ingresado un área de operacion incorrecta", exception.getMessage());
		 
		 
    }
    
    @Test
   	@SneakyThrows
   	public void testNotifications2() {
       	List<NotificationDTO> notifications = new ArrayList<>();
       	NotificationDTO notification1 = new NotificationDTO();
       	notification1.setLabelName("NEWENQUERY");
       	notification1.setTotalResult(40);
       	NotificationDTO notification2 = new NotificationDTO();
    	notification2.setLabelName("NEWALERT");
       	notification2.setTotalResult(10);
       	NotificationDTO notification3 = new NotificationDTO();
    	notification3.setLabelName("NEWINTERNALMESSAGE");
       	notification3.setTotalResult(5);
       	NotificationDTO notification4 = new NotificationDTO();
    	notification4.setLabelName("NEWEXTERNALMESSAGEREQUEST");
       	notification4.setTotalResult(4);
       	NotificationDTO notification5 = new NotificationDTO();
    	notification5.setLabelName("NEWEXTERNALMESSAGE");
       	notification5.setTotalResult(400);
       	
       	List<NotificationsResponseDTO> expecteResponse = new ArrayList<>();
       	NotificationsResponseDTO universarViewerNotifications = new NotificationsResponseDTO();
		NotificationsResponseDTO requestsReceived = new NotificationsResponseDTO();
		List<NotificationDTO> viewerUniversal = new ArrayList<>();
		List<NotificationDTO> messagesReceived = new ArrayList<>();
		
		universarViewerNotifications.setNotificationName("Universal Viewer Notifications");
		requestsReceived.setNotificationName("Requests Received");
       	
       	notifications.add(notification1);
       	notifications.add(notification2);
       	notifications.add(notification3);
       	notifications.add(notification4);
       	notifications.add(notification5);
       	
       	viewerUniversal.add(notification1);
		viewerUniversal.add(notification2);
		viewerUniversal.add(notification3);
		
		messagesReceived.add(notification4);
		messagesReceived.add(notification5);
       	
       	universarViewerNotifications.setNotifications(viewerUniversal);
       	requestsReceived.setNotifications(messagesReceived);
       	
       	expecteResponse.add(universarViewerNotifications);
       	expecteResponse.add(requestsReceived);
       	
       	
       	when(notificationsSyncOuputPort.getNotifications(anyString())).thenReturn(notifications);
       	List<NotificationsResponseDTO> obtainResponse = notificationsUseCase.getNotifications("ENLV");
       	
       	assertEquals(expecteResponse, obtainResponse);
   }
    
    
    @Test
   	@SneakyThrows
   	public void testNotifications3() {
       	List<NotificationDTO> notifications = new ArrayList<>();
     
       	NotificationDTO notification4 = new NotificationDTO();
    	notification4.setLabelName("NEWEXTERNALMESSAGEREQUEST");
       	notification4.setTotalResult(4);
       	NotificationDTO notification5 = new NotificationDTO();
    	notification5.setLabelName("NEWEXTERNALMESSAGE");
       	notification5.setTotalResult(400);
       	
       	List<NotificationsResponseDTO> expecteResponse = new ArrayList<>();
       	NotificationsResponseDTO universarViewerNotifications = new NotificationsResponseDTO();
		NotificationsResponseDTO requestsReceived = new NotificationsResponseDTO();
		List<NotificationDTO> viewerUniversal = new ArrayList<>();
		List<NotificationDTO> messagesReceived = new ArrayList<>();
		
		universarViewerNotifications.setNotificationName("Universal Viewer Notifications");
		requestsReceived.setNotificationName("Requests Received");
       
       	notifications.add(notification4);
       	notifications.add(notification5);
       	
		messagesReceived.add(notification4);
		messagesReceived.add(notification5);
       	
       	universarViewerNotifications.setNotifications(viewerUniversal);
       	requestsReceived.setNotifications(messagesReceived);
       	
       	expecteResponse.add(requestsReceived);
       	
       	
       	when(notificationsSyncOuputPort.getNotifications(anyString())).thenReturn(notifications);
       	List<NotificationsResponseDTO> obtainResponse = notificationsUseCase.getNotifications("ENLV");
       	
       	assertEquals(expecteResponse, obtainResponse);
   }
    
    @Test
   	@SneakyThrows
   	public void testNotifications4() {
       	List<NotificationDTO> notifications = new ArrayList<>();
       	NotificationDTO notification1 = new NotificationDTO();
       	notification1.setLabelName("NEWENQUERY");
       	notification1.setTotalResult(40);
       	NotificationDTO notification2 = new NotificationDTO();
    	notification2.setLabelName("NEWALERT");
       	notification2.setTotalResult(10);
       	NotificationDTO notification3 = new NotificationDTO();
    	notification3.setLabelName("NEWINTERNALMESSAGE");
       	notification3.setTotalResult(5);
       	
       	List<NotificationsResponseDTO> expecteResponse = new ArrayList<>();
       	NotificationsResponseDTO universarViewerNotifications = new NotificationsResponseDTO();
		NotificationsResponseDTO requestsReceived = new NotificationsResponseDTO();
		List<NotificationDTO> viewerUniversal = new ArrayList<>();
		List<NotificationDTO> messagesReceived = new ArrayList<>();
		
		universarViewerNotifications.setNotificationName("Universal Viewer Notifications");
		requestsReceived.setNotificationName("Requests Received");
       	
       	notifications.add(notification1);
       	notifications.add(notification2);
       	notifications.add(notification3);
       	
       	viewerUniversal.add(notification1);
		viewerUniversal.add(notification2);
		viewerUniversal.add(notification3);

       	universarViewerNotifications.setNotifications(viewerUniversal);
       	requestsReceived.setNotifications(messagesReceived);
       	
       	expecteResponse.add(universarViewerNotifications);

       	
       	
       	when(notificationsSyncOuputPort.getNotifications(anyString())).thenReturn(notifications);
       	List<NotificationsResponseDTO> obtainResponse = notificationsUseCase.getNotifications("ENLV");
       	
       	assertEquals(expecteResponse, obtainResponse);
   }
    
    @Test
   	@SneakyThrows
   	public void testNotifications5() {
       	List<NotificationDTO> notifications = new ArrayList<>();
       	NotificationDTO notification1 = new NotificationDTO();
       	notification1.setLabelName("ABC");
       	notification1.setTotalResult(40);
       	NotificationDTO notification2 = new NotificationDTO();
    	notification2.setLabelName("DFC");
       	notification2.setTotalResult(10);
       	NotificationDTO notification3 = new NotificationDTO();
    	notification3.setLabelName("APJF");
       	notification3.setTotalResult(5);
       	NotificationDTO notification4 = new NotificationDTO();
    	notification4.setLabelName("BADINFO");
       	notification4.setTotalResult(4);
       	NotificationDTO notification5 = new NotificationDTO();
    	notification5.setLabelName("example");
       	notification5.setTotalResult(400);
       	
       	List<NotificationsResponseDTO> expecteResponse = new ArrayList<>();
       	NotificationsResponseDTO universarViewerNotifications = new NotificationsResponseDTO();
		NotificationsResponseDTO requestsReceived = new NotificationsResponseDTO();
		List<NotificationDTO> viewerUniversal = new ArrayList<>();
		List<NotificationDTO> messagesReceived = new ArrayList<>();
		
		universarViewerNotifications.setNotificationName("Universal Viewer Notifications");
		requestsReceived.setNotificationName("Requests Received");
       	
       	notifications.add(notification1);
       	notifications.add(notification2);
       	notifications.add(notification3);
       	notifications.add(notification4);
       	notifications.add(notification5);
       	
       	viewerUniversal.add(notification1);
		viewerUniversal.add(notification2);
		viewerUniversal.add(notification3);
		
		messagesReceived.add(notification4);
		messagesReceived.add(notification5);
       	
       	universarViewerNotifications.setNotifications(viewerUniversal);
       	requestsReceived.setNotifications(messagesReceived);
       	
       	expecteResponse.add(universarViewerNotifications);
       	expecteResponse.add(requestsReceived);
       	
       	
       	when(notificationsSyncOuputPort.getNotifications(anyString())).thenReturn(notifications);
       	
       	
       	CustomValidationException exception = assertThrows(CustomValidationException.class, ()-> {
       		notificationsUseCase.getNotifications("ENLV");
    	});
       	
       	
       	assertEquals("No hay notificaciones", exception.getMessage());
   }
    
    
}

