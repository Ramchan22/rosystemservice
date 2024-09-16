/**
 * RamkumarSudalaimani
 */
package com.roservice.common;

import java.io.Serializable;

/**
 * 
 */
public class Library implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3271666290937558427L;

	public static GenericResponse getSuccessfulResponse(Object obj, int erorrCode, String msg) {
		GenericResponse GenericResponse = new GenericResponse();
		GenericResponse.setData(obj);
		GenericResponse.setErrorCode(erorrCode);
		GenericResponse.setStatus("s");
		GenericResponse.setUserDisplayMesg("Success");
		GenericResponse.setUserDisplayMesg(msg);
		return GenericResponse;
	}

	public static GenericResponse noRecordFoundResponse(String msg) {
		GenericResponse GenericResponse = new GenericResponse();
		GenericResponse.setData(null);
		GenericResponse.setErrorCode(0);
		GenericResponse.setStatus("s");
		GenericResponse.setUserDisplayMesg("Success");
		GenericResponse.setUserDisplayMesg(msg);
		return GenericResponse;
	}

	public static GenericResponse getFailResponseCode(int erorrCode, String strMsg) {
		GenericResponse GenericResponse = new GenericResponse();
		GenericResponse.setData(null);
		GenericResponse.setErrorCode(erorrCode);
		GenericResponse.setStatus("f");
		GenericResponse.setUserDisplayMesg("Failed");
		GenericResponse.setUserDisplayMesg(strMsg);
		return GenericResponse;
	}

}
