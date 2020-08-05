package com.microcred.auth.security;

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

public class UserAuthenticationEntryPoint implements AuthenticationEntryPoint{

	private static final Logger LOGGER = LoggerFactory.getLogger(UserAuthenticationEntryPoint.class);
	
	@SuppressWarnings("rawtypes")
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex)
			throws IOException, ServletException {

		MicroCredResponse microCredResponse = new MicroCredResponse<>();
		microCredResponse.setType(ResponseCodeEnum.ERROR.name());
		microCredResponse.setCode(ResponseCodeEnum.AUTH_SERVER_FAILURE_USER_IS_UNAUTHORIZED.name());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String rsString = new ObjectMapper().writeValueAsString(microCredResponse);
		response.getWriter().write(rsString); 
        LOGGER.error("User is unauthorized. Response: ", rsString);
	}

}
