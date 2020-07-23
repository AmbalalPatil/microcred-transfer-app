package com.microcred.composite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for composite service
 * @author Ambalal Patil
 */
@Configuration
public class CompositeServiceConfig {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
