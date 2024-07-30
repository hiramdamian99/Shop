/*******************************************************************************
 * D. R. © Veradat Smart Network, S.A.P.I de C.V., Ciudad de México, 2023
 * VERADAT PROPRIETARY/CONFIDENCIAL. Use is subject to license terms.
 * 	
 * @(#) ApplicationLauncher.java
 * This class is the initializer of the project
 *
 * @author Oscar Gerardo Trejo Rivera
 * @version 1.0
 * @createdAt 19/12/2023
 * @updatedAt 19/12/2023
 *******************************************************************************/
package com.veradat;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

import io.micrometer.common.util.StringUtils;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAutoConfiguration(exclude= {RabbitAutoConfiguration.class} )
@ComponentScan(basePackages = {"com.veradat"})
public class ApplicationLauncher {

    /**
     * Método principal que inicia la aplicación.
     *
     * @param args Los argumentos de línea de comandos.
     */
    public static void main(String[] args) {
    	TimeZone.setDefault(TimeZone.getTimeZone(
				StringUtils.isNotBlank(System.getenv("veradat.timezone")) ? System.getenv("veradat.timezone")
						: "GMT-06"));
        SpringApplication.run(ApplicationLauncher.class, args);
    }
}
