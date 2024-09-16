/**
 * RamkumarSudalaimani
 */
package com.roservice.security;

import java.io.Serializable;

/**
 * 
 */
public class SecurityConstants implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6617746819530280069L;

	public static final long EXPIRATION_TIME = 86400000;
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/user/login";
	public static final String TOKEN_SECRET = "7VfKmzJuS/MeUksZcT5tq5Zyw6sjrZn5HlIbOHfKw74=fw34bc45gyhu71s20pkj9=";

}
