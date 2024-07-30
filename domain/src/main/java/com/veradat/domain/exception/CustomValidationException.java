
package com.veradat.domain.exception;

import org.springframework.http.HttpStatus;

public class CustomValidationException extends Throwable {

	private static final long serialVersionUID = -905489589647L;

	public CustomValidationException(String message) {
        super(message, false, HttpStatus.BAD_REQUEST);
    }

}
