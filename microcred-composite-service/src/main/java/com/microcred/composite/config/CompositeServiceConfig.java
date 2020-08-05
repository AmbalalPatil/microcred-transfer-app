package com.microcred.composite.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for composite service
 * @author Ambalal Patil
 */
@Configuration
public class CompositeServiceConfig {

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
		messageConverters.add(converter);
		restTemplate.setMessageConverters(messageConverters);
		return restTemplate;  
	}
}
