package com.microcred.execute;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Execute transfer service application
 * @author Ambalal Patil
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ExecuteTransferServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExecuteTransferServiceApplication.class, args);
	}

}
