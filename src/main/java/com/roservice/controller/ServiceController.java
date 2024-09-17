/**
 * RamkumarSudalaimani
 */
package com.roservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roservice.common.AuthenticationDTO;
import com.roservice.common.BaseController;
import com.roservice.common.GenericResponse;
import com.roservice.dto.SearchRequestDTO;
import com.roservice.entity.ServiceDetailsEntity;
import com.roservice.service.RoService;

import lombok.extern.log4j.Log4j2;

/**
 * 
 */
@RestController
@RequestMapping("/serviceDetails")
@Log4j2
public class ServiceController extends BaseController {
	
	@Autowired
	private RoService roService;

	@PostMapping("/addServiceDetails")
	private GenericResponse addServiceDetails(@RequestBody ServiceDetailsEntity addServiceDetails) {
		log.info("Inside ServiceController :: addServiceDetails() - START");
		AuthenticationDTO authenticationDTO = findAuthenticationObject();
		GenericResponse genericResponse = roService.addServiceDetails(authenticationDTO, addServiceDetails);
		log.info("Inside ServiceController :: addServiceDetails() - END");
		return genericResponse;
	}

	@PostMapping("/lazySearch")
	private GenericResponse lazySearch(@RequestBody SearchRequestDTO searchRequestDTO) {
		log.info("Inside ServiceController :: lazySearch() - START");
		AuthenticationDTO authenticationDTO = findAuthenticationObject();
		GenericResponse genericResponse = roService.lazySearch(authenticationDTO, searchRequestDTO);
		log.info("Inside ServiceController :: lazySearch() - END");
		return genericResponse;
	}

}
