package com.microcred.validate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 
 * @author Ambalal Patil
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ValidateTransferServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ValidateTransferServiceApplication.class, args);
	}

}
