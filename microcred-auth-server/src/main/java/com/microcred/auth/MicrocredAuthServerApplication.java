package com.microcred.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Authentication server application
 * @author Ambalal Patil
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MicrocredAuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicrocredAuthServerApplication.class, args);
	}

}
