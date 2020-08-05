package com.microcred.gateway.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microcred.common.exception.ResponseCodeEnum;
import com.microcred.common.response.MicroCredResponse;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);
	
	@SuppressWarnings("rawtypes")
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex)
			throws IOException, ServletException {

		MicroCredResponse microCredResponse = new MicroCredResponse<>();
		microCredResponse.setType(ResponseCodeEnum.ERROR.name());
		microCredResponse.setCode(ResponseCodeEnum.API_GATEWAY_FAILURE_AUTH_TOKEN_INVALID.name());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String rsString = new ObjectMapper().writeValueAsString(microCredResponse);
		response.getWriter().write(rsString); 
        LOGGER.error("Invalid auth token found. Response: ", rsString);
	}

}

