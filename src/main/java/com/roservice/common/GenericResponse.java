/**
 * RamkumarSudalaimani
 */
package com.roservice.common;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * 
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1305520096197686289L;
	@Override
	public String toString() {
		return "GenericResponse [status=" + status + ", errorDescription=" + errorDescription + ", errorCode="
				+ errorCode + ", userDisplayMesg=" + userDisplayMesg + ", Data=" + Data + "]";
	}

	private String status;

	private String errorDescription;
	private int errorCode;
	private String userDisplayMesg;
	private Object Data;

}
