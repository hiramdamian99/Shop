
package com.veradat.domain.outputport.sync;

import java.util.List;

import com.veradat.domain.exception.CustomValidationException;
import com.veradat.domain.model.notifications.NotificationDTO;
import com.veradat.domain.model.notifications.ShopingCart;

public interface NotificationsSyncOuputPort {
	
	/**
	 * This function calls 
	 * 
	 * @param 
	 * @return 
	 * @throws OutportException
	 * 
	 */
	List<NotificationDTO> getShopingCart(ShopingCart shopingCart) throws CustomValidationException;


}
