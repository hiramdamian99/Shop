
package com.veradat.domain.exception;

import lombok.Getter;

import java.util.List;

public class NonRetriableException extends Exception {

    private static final long serialVersionUID = -8954756864987645L;

	@Getter
    /**
     * microservice id
     */
    private final String msid;

    @Getter
    /**
     * class name
     */
    private final String className;

    @Getter
    /**
     * method name
     */
    private final String methodName;

    @Getter
    /**
     * exceptions detail
     */
    private final List<String> exceptions;

    @Getter
    /**
     * message
     */
    private final String message;

    @Getter
    /**
     * process uuid trazability
     */
    private final String processUuid;

    @Getter
    /**
     * uuid exception
     */
    private final String exceptionUuid;

    @Getter
    private final Integer statusCode;

    public NonRetriableException(String message){
        this(message, null, null, null, null, null, null, null);
    }

    public NonRetriableException(String message, String msid, String className, String methodName,
                                 List<String> exceptions, String processUuid, String exceptionUuid, Integer statusCode) {
        super(message);
        this.msid = msid;
        this.className = className;
        this.methodName = methodName;
        this.exceptions = exceptions;
        this.message = message;
        this.processUuid = processUuid;
        this.exceptionUuid = exceptionUuid;
        this.statusCode = statusCode;
    }
}
