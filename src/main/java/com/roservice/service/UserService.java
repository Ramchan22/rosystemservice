/**
 * RamkumarSudalaimani
 */
package com.roservice.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.roservice.common.AuthenticationDTO;
import com.roservice.common.GenericResponse;
import com.roservice.entity.User;

/**
 * 
 */
public interface UserService extends UserDetailsService {
	
	GenericResponse createNewUser(AuthenticationDTO authenticationDTO, User userDetails);

	AuthenticationDTO getUserDetails(String userName);

	GenericResponse logout(AuthenticationDTO authenticationDTO);

	GenericResponse getAllUser(AuthenticationDTO authenticationDTO);

}
