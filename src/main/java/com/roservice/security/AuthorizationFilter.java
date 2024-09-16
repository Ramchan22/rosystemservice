/**
 * RamkumarSudalaimani
 */
package com.roservice.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import com.roservice.common.AuthenticationDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
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
public class AuthorizationFilter extends BasicAuthenticationFilter {
	
	public AuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		log.info("Inside AuthorizationFilter :: doFilterInternal() - START");

		String header = request.getHeader(SecurityConstants.HEADER_STRING);

		if (header == null || !StringUtils.hasLength(header.trim())
				|| !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {

			chain.doFilter(request, response);
			return;

		}
		UsernamePasswordAuthenticationToken authenticationFilter = getAuthentication(request);
		SecurityContextHolder.getContext().setAuthentication(authenticationFilter);
		chain.doFilter(request, response);

		log.info("Inside AuthorizationFilter :: doFilterInternal() - END");

	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {

		log.info("Inside AuthorizationFilter :: getAuthentication() - START");

		String authorizationHeader = request.getHeader(SecurityConstants.HEADER_STRING);

		if (null == authorizationHeader || !StringUtils.hasLength(authorizationHeader.trim())) {
			return null;
		}

		String token = authorizationHeader.replace(SecurityConstants.TOKEN_PREFIX, "");
		byte[] secretKeyByteArray = Base64.getEncoder().encode(SecurityConstants.TOKEN_SECRET.getBytes());
		SecretKey secretKey = new SecretKeySpec(secretKeyByteArray, SignatureAlgorithm.HS512.getJcaName());

		JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build();

		Jwt<Header, Claims> jwt = jwtParser.parse(token);
		Claims claims = jwt.getBody();
		String subject = claims.getSubject();

		if (null == subject || !StringUtils.hasLength(subject.trim())) {
			return null;
		}

		// Create an AuthenticationDTO object from claims
		AuthenticationDTO authenticationDTO = new AuthenticationDTO();
		authenticationDTO.setUserId(Long.parseLong(claims.get("userId").toString()));
		authenticationDTO.setUserName(claims.get("userName").toString());
		authenticationDTO.setFirstName(claims.get("firstName").toString());
		authenticationDTO.setLastName(claims.get("lastName").toString());
		authenticationDTO.setIsAdminUser(Boolean.valueOf(claims.get("isAdminUser").toString()));
		authenticationDTO.setIsOfficeAssistant(Boolean.valueOf(claims.get("isOfficeAssistant").toString()));

		log.info("Inside AuthorizationFilter :: getAuthentication() - END");

		// Return the token with AuthenticationDTO as the principal
		return new UsernamePasswordAuthenticationToken(authenticationDTO, null, new ArrayList<>());
	}

}
