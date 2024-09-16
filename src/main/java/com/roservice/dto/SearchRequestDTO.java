/**
 * RamkumarSudalaimani
 */
package com.roservice.dto;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;

/**
 * 
 */
@Data
public class SearchRequestDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 976222512835605251L;

	private Map<String, Object> filters;

	private int pageNo;

	private int pageSize;

}
