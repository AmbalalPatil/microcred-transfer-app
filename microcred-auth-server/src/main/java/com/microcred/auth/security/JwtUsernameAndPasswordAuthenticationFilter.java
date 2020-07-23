package com.microcred.auth.security;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Filter to validate user credentials and add token in the response header
 * @author Ambalal Patil
 */
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter   {
	
	// Auth manager to validate the user credentials
	private AuthenticationManager authManager;
	
	// Secret key to generate JWT token
	private String secretKey;
    
	public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authManager, String secretKey) {
		this.authManager = authManager;
		this.secretKey = secretKey;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		try {
			// 1. Get credentials from request
			UserCredentials creds = new ObjectMapper().readValue(request.getInputStream(), UserCredentials.class);
			
			// 2. Create auth object (contains credentials) which will be used by auth manager
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
					creds.getUsername(), creds.getPassword(), Collections.emptyList());
			
			// 3. Authentication manager authenticate the user, and use UserDetialsServiceImpl::loadUserByUsername() method to load the user.
			return authManager.authenticate(authToken);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	// On successful authentication, generate a token.
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		
		Calendar now = Calendar.getInstance();
		Date issuedDate = now.getTime();
		now.add(Calendar.MINUTE, 5);
		String token = Jwts.builder()
			.setSubject(auth.getName())	
			.claim("authorities", auth.getAuthorities().stream()
													.map(GrantedAuthority::getAuthority)
													.collect(Collectors.toList()))
			.setIssuedAt(issuedDate)
			.setExpiration(now.getTime())  // in milliseconds
			.signWith(SignatureAlgorithm.HS512, secretKey.getBytes())
			.compact();
		
		// Add token to header
		response.addHeader("Authentication", "Bearer " + token);
	}
	
	private static class UserCredentials {
	    private String username;
	    private String password;
	    
	    public String getUsername() {
			return username;
		}
	    
	    public void setUsername(String username) {
			this.username = username;
		}
	    public String getPassword() {
			return password;
		}
	    public void setPassword(String password) {
			this.password = password;
		}
	}
}