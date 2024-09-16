/**
 * RamkumarSudalaimani
 */
package com.roservice.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * 
 */
@Data
public class UserDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5111510841494169184L;

	private String userName;

	private String password;

}
