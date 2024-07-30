
package com.veradat.domain.exception;

import com.veradat.domain.model.ErrorMessage;
import com.veradat.lib.util.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ExceptionUtils {

    private final Logger logger = LoggerFactory.getLogger(ExceptionUtils.class);

    @Value("${microservicio:FEEORQ}")
    private String idMicroservicio;

    /**
     * Generates an ErrorMessage object based on an exception and a message.
     *
     * @param exception The exception from which to obtain the information.
     * @param message   The error message associated with the exception.
     * @return The generated ErrorMessage object.
     */
    public ErrorMessage buildErrorMessage(Exception exception, String message) {
        StackTraceElement[] stackTrace = exception.getStackTrace();

        String methodName = stackTrace.length == 0 ? "" : stackTrace[0].getMethodName();
        String className = stackTrace.length == 0 ? "" : stackTrace[0].getClassName();

        String classId = getClassId(className);

        String errorDetail = "%s-%s-%s-%s-%s".formatted(
            UUIDGenerator.generateVeradatUUID(),
            idMicroservicio,
            classId,
            methodName,
            message
        );

        logger.error(errorDetail);
        return getErrorMessage(message, methodName, classId, errorDetail);
    }

    private String getClassId(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            return IdentifierManager.getIdentifier(clazz);
        } catch (ClassNotFoundException e) {
            return "";
        }
    }

    private ErrorMessage getErrorMessage(String message, String methodName, String classId, String errorDetail) {
        ErrorMessage em = new ErrorMessage();
        em.setIdMicroservice(idMicroservicio);
        em.setIdClass(classId);
        em.setIdMethod(methodName);
        em.setMessage(message);
        em.setErrorDetail(errorDetail);
        em.setTimestamp(getTimestampNow());
        return em;
    }

    private String getTimestampNow() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }
}

