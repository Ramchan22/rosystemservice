/**
 * RamkumarSudalaimani
 */
package com.roservice.common;

import java.io.Serializable;

import lombok.Data;

/**
 * 
 */
@Data
public class AuthenticationDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 424582632318815671L;

	private Long userId;

	private String userName;

	private String firstName;

	private String middleName;

	private String lastName;

	private Boolean isAdminUser = false;

	private Boolean isOfficeAssistant = false;

}
