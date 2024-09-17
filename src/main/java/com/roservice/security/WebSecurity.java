/**
 * RamkumarSudalaimani
 */
package com.roservice.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import lombok.extern.log4j.Log4j2;

/**
 * 
 */
@Configuration
@EnableWebSecurity
@Log4j2
public class WebSecurity {
	
	private final UserDetailsService userDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public WebSecurity(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
		log.info("Inside WebSecurity :: configure() - START");

		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class);

		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);

		AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

		// Custom Authentication Filter
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager);
		authenticationFilter.setFilterProcessesUrl("/user/login");

		// Add the AuthorizationFilter to the filter chain
		AuthorizationFilter authorizationFilter = new AuthorizationFilter(authenticationManager);

		// Configure HttpSecurity
		http.csrf(csrf -> csrf.disable()) // Disable CSRF
				.cors(cors -> cors.configurationSource(request -> {
					// Customize CORS configuration for your endpoints
					CorsConfiguration config = new CorsConfiguration();
					config.setAllowedOrigins(Arrays.asList("http://localhost:4200", "https://ramaquatech.netlify.app")); // Add allowed origins
					config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Add allowed
																										// methods
					config.setAllowedHeaders(Arrays.asList("*"));
					config.setExposedHeaders(Arrays.asList("Authorization", "userId"));// Allow all headers
					config.setAllowCredentials(true); // Allow credentials like cookies

					return config;
				}))
				.authorizeHttpRequests(
						authorize -> authorize.requestMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL)
								.permitAll().anyRequest().authenticated())
				.authenticationManager(authenticationManager).addFilter(authenticationFilter)
				.addFilter(authorizationFilter) // Add Authorization Filter
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		log.info("Inside WebSecurity :: configure() - END");

		return http.build();
	}

}
