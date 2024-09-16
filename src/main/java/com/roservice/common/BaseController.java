/**
 * RamkumarSudalaimani
 */
package com.roservice.common;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 
 */
public class BaseController {

	/**
	 * This method will be used to get the authentication DTO from the security
	 * context
	 * 
	 * @return
	 */
	public AuthenticationDTO findAuthenticationObject() {
		if (SecurityContextHolder.getContext() != null
				&& SecurityContextHolder.getContext().getAuthentication() != null) {
			AuthenticationDTO authenticationDTO = (AuthenticationDTO) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			return authenticationDTO;
		}
		return null;
	}

}
