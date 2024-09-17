/**
 * RamkumarSudalaimani
 */
package com.roservice.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5111510841494169184L;

	private String userName;

	private String password;

	private String emailId;

	private String mobileNumber;

	private String firstName;

	private String middleName;

	private String lastName;

	private Boolean isAdminUser = false;

	private Boolean isOfficeAssistant = false;

	private Boolean isUserLogedIn = false;

	private Long employeeId;

	public UserDTO(String userName, String emailId, String mobileNumber, String firstName, String middleName,
			String lastName, Boolean isAdminUser, Boolean isOfficeAssistant, Boolean isUserLogedIn, Long employeeId) {
		super();

		this.userName = userName;
		this.emailId = emailId;
		this.mobileNumber = mobileNumber;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.isAdminUser = isAdminUser;
		this.isOfficeAssistant = isOfficeAssistant;
		this.isUserLogedIn = isUserLogedIn;
		this.employeeId = employeeId;

	}

}
