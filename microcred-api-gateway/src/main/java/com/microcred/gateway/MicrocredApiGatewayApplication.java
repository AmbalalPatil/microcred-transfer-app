package com.microcred.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Api gateway application
 * @author Ambalal Patil
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class MicrocredApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicrocredApiGatewayApplication.class, args);
	}
}
