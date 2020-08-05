package com.microcred.gateway.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Config class for JWT token security configuration
 * @author Ambalal Patil
 */
@EnableWebSecurity
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Override
  	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
	    // Do not store user's state.
 	    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) 	
		.and()
	    // Handle an authorized attempt 
	    .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED)) 	
		.and()
		// Filter to validate the tokens with every request
		.addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
		.authorizeRequests()
		   // Allow all who are accessing "/login" path
		   .antMatchers("/login").permitAll()  
		   // Any other request must be authenticated
		   .anyRequest().authenticated(); 
	}
}
