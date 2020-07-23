package com.microcred.composite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Composite service application
 * @author Ambalal Patil
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MicrocredCompositeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicrocredCompositeServiceApplication.class, args);
	}

}
