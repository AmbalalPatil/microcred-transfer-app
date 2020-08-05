package com.microcred.execute;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * Execute transfer service application
 * @author Ambalal Patil
 */
@SpringBootApplication
@EnableDiscoveryClient
@EntityScan("com.microcred.common.dbentity")
public class ExecuteTransferServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExecuteTransferServiceApplication.class, args);
	}

}
