package com.microcred.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka discovery server application
 * @author Ambalal Patil
 *
 */
@SpringBootApplication
@EnableEurekaServer
public class MicrocredDiscoveryServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicrocredDiscoveryServerApplication.class, args);
	}

}
