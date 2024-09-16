/**
 * RamkumarSudalaimani
 */
package com.roservice.security;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roservice.SpringApplicationContext;
import com.roservice.common.AuthenticationDTO;
import com.roservice.dto.UserDTO;
import com.roservice.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

/**
 * 
 */
@Log4j2
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	public AuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		log.info("Inside AuthenticationFilter :: attemptAuthentication() - START");

		try {

			UserDTO userCredentials = new ObjectMapper().readValue(request.getInputStream(), UserDTO.class);

			log.info("Inside AuthenticationFilter :: attemptAuthentication() - END");

			return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
					userCredentials.getUserName(), userCredentials.getPassword(), new ArrayList<>()));

		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain, Authentication authentication) throws IOException, ServletException {

		log.info("Inside AuthenticationFilter :: successfulAuthentication() - START");

		byte[] secretKeyBytes = Base64.getEncoder().encode(SecurityConstants.TOKEN_SECRET.getBytes());
		SecretKey secretKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());
		Instant instant = Instant.now();

		// Get the username from the authentication object
		String userName = ((User) authentication.getPrincipal()).getUsername();

		// Retrieve the user's details
		UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
		AuthenticationDTO authenticationDTO = userService.getUserDetails(userName);

		// Generate the JWT token
		String token = Jwts.builder().setSubject(userName).claim("userName", authenticationDTO.getUserName())// Use the
																												// username
																												// as
																												// the
																												// token
																												// subject
				.claim("userId", authenticationDTO.getUserId()) // Store user details as claims
				.claim("firstName", authenticationDTO.getFirstName()).claim("lastName", authenticationDTO.getLastName())
				.claim("isAdminUser", authenticationDTO.getIsAdminUser())
				.claim("isOfficeAssistant", authenticationDTO.getIsOfficeAssistant())
				.setExpiration(Date.from(instant.plusMillis(SecurityConstants.EXPIRATION_TIME))) // Token expiration
																									// time
				.setIssuedAt(Date.from(instant)) // Issue time
				.signWith(secretKey, SignatureAlgorithm.HS512) // Sign the token
				.compact();

		log.info("Inside AuthenticationFilter :: successfulAuthentication() - END");

		// Add the token to the response header
		response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX.concat(token));
		response.addHeader("userId", authenticationDTO.getUserId().toString());
	}

}
