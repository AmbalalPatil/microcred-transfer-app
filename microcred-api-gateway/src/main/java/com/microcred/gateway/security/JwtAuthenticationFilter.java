package com.microcred.gateway.security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * Filter to validate JWT token
 * @author Ambalal Patil
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	private static final String TOKEN_PREFIX = "Bearer ";
	
	@Value("${jwt.secret:ambalal}")
	private String secretKey;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		
		// 1. Get the authentication header. Tokens are supposed to be passed in the authentication header
		String header = request.getHeader("Authentication");
		
		// 2. Validate the header and check the prefix. If not valid, go to the next filter.
		// Go to next filter. May be user is accessing a public/unsecured url or asking for token
		if(header == null || !header.startsWith(TOKEN_PREFIX)) {
			chain.doFilter(request, response);  		
			return;
		}
		
		// 3. Get the token
		String token = header.replace(TOKEN_PREFIX, "");
		
		// 4. Validate the token
		// Exception will be thrown while creating the claims when token is not valid
		Claims claims = Jwts.parser()
				.setSigningKey(secretKey.getBytes())
				.parseClaimsJws(token)
				.getBody();
		
		String username = claims.getSubject();
		if(username != null) {
			@SuppressWarnings("unchecked")
			List<String> authorities = (List<String>) claims.get("authorities");
			
			// 5. Create auth object
			 UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
							 username, null, authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
			 
			 // 6. Set authenticated user into security context
			 SecurityContextHolder.getContext().setAuthentication(auth);
		}
		
		// Go to the next filter in the filter chain
		chain.doFilter(request, response);
	}
}
