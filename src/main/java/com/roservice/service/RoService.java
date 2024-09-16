/**
 * RamkumarSudalaimani
 */
package com.roservice.service;

import com.roservice.common.AuthenticationDTO;
import com.roservice.common.GenericResponse;
import com.roservice.dto.SearchRequestDTO;
import com.roservice.entity.ServiceDetailsEntity;

/**
 * 
 */
public interface RoService {

	GenericResponse addServiceDetails(AuthenticationDTO authenticationDTO, ServiceDetailsEntity addServiceDetails);

	GenericResponse lazySearch(AuthenticationDTO authenticationDTO, SearchRequestDTO searchRequestDTO);

}
