package com.microcred.auth.security;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for user management
 * @author Ambalal Patil
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService  {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	// Encoder class bean for password encoding
	@Autowired
	private BCryptPasswordEncoder encoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOGGER.debug("Load user for username: [{}]", username);
		
		// 1. Check if valid user found in DB
		Optional<User> loggedInUser = getUsersFromDB().stream()
												.filter(user -> user.getUsername().equals(username))
												.findFirst();
		// 2. Return loggedIn user details
		if(loggedInUser.isPresent()) {
			LOGGER.debug("User found for username: [{}]", username);
			return loggedInUser.get();
		}	
		
		// 3. Throw exception if valid user not found
		LOGGER.error("User not found for username: [{}]", username);
		throw new UsernameNotFoundException("Username: " + username + " not found");
}
	
	// User mock data
	private List<User> getUsersFromDB(){
		return Arrays.asList(
			new User("Ambalal", encoder.encode("Patil"), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_TRAINEE")),
			new User("Sourav", encoder.encode("Dutta"), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_MENTOR"))
		);
	}
}