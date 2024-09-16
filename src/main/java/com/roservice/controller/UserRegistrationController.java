/**
 * RamkumarSudalaimani
 */
package com.roservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roservice.common.AuthenticationDTO;
import com.roservice.common.BaseController;
import com.roservice.common.GenericResponse;
import com.roservice.entity.User;
import com.roservice.service.UserService;

import lombok.extern.log4j.Log4j2;

/**
 * 
 */
@RestController
@RequestMapping("/user")
@Log4j2
public class UserRegistrationController extends BaseController {

	@Autowired
	private UserService userService;

	@PostMapping("/signUp")
	public GenericResponse createNewUser(@RequestBody User userDetails) {

		log.info("Inside UserRegistrationController :: createNewUser() - START");
		log.info("payload: {}", userDetails);

		AuthenticationDTO authenticationDTO = findAuthenticationObject();

		GenericResponse genericResponse = userService.createNewUser(authenticationDTO, userDetails);

		log.info("Inside UserRegistrationController :: createNewUser() - END");

		return genericResponse;
	}

	@GetMapping("/logout")
	public GenericResponse logout() {
		log.info("Inside UserRegistrationController :: login() - START");

		AuthenticationDTO authenticationDTO = findAuthenticationObject();

		GenericResponse genericResponse = userService.logout(authenticationDTO);

		log.info("Inside UserRegistrationController :: login() - END");

		return genericResponse;
	}

}
