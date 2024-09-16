/**
 * RamkumarSudalaimani
 */
package com.roservice.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roservice.common.AuthenticationDTO;
import com.roservice.common.ErrorCode;
import com.roservice.common.ErrorMessages;
import com.roservice.common.GenericResponse;
import com.roservice.common.Library;
import com.roservice.dto.SearchRequestDTO;
import com.roservice.entity.ServiceDetailsEntity;
import com.roservice.repository.ServiceRepository;
import com.roservice.service.RoService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.log4j.Log4j2;

/**
 * 
 */
@Service
@Log4j2
public class RoServiceImpl implements RoService {
	
	@Autowired
	private ServiceRepository serviceRepository;

	@Autowired
	private EntityManager entityManager;

	@Override
	public GenericResponse addServiceDetails(AuthenticationDTO authenticationDTO, ServiceDetailsEntity serviceDetails) {

		log.info("Inside RoServiceImpl :: addServiceDetails() - START");

		if (null != authenticationDTO
				&& (authenticationDTO.getIsAdminUser() || authenticationDTO.getIsOfficeAssistant())) {

			try {

				if (null == serviceDetails.getId() || serviceDetails.getId() <= 0) {

					serviceDetails.setCreatedBy(authenticationDTO.getUserId());
					serviceDetails.setModifiedBy(authenticationDTO.getUserId());
					serviceDetails.setCreatedDate(new Date());
					serviceDetails.setModifiedDate(new Date());

				}

				serviceRepository.save(serviceDetails);

			} catch (Exception exception) {

				log.error("<==== Error while add the service details ====>", exception);
				exception.printStackTrace();
				return Library.getFailResponseCode(ErrorCode.EXCEPTION.getErrorCode(), ErrorMessages.EXCEPTION_MESSAGE);

			}

		} else {
			return Library.getFailResponseCode(ErrorCode.UNAUTHORIZED.getErrorCode(), ErrorMessages.UNAUTHORIZED_USER);
		}

		log.info("Inside RoServiceImpl :: addServiceDetails() - END");

		return Library.getSuccessfulResponse(null, ErrorCode.SUCCESS_RESPONSE.getErrorCode(),
				ErrorMessages.RECORED_CREATED);
	}

	@Override
	public GenericResponse lazySearch(AuthenticationDTO authenticationDTO, SearchRequestDTO searchRequestDTO) {

		log.info("Inside RoServiceImpl :: lazySearch() - START");

		if (null != authenticationDTO
				&& (authenticationDTO.getIsAdminUser() || authenticationDTO.getIsOfficeAssistant())) {

			List<ServiceDetailsEntity> serviceDetailsList = new ArrayList<ServiceDetailsEntity>();

			try {

				if (null != searchRequestDTO && null != searchRequestDTO.getFilters()) {

					Map<String, Object> filterMap = new ConcurrentHashMap<String, Object>();
					filterMap = searchRequestDTO.getFilters();

					// Step 1: Create a CriteriaBuilder instance
					CriteriaBuilder cb = entityManager.getCriteriaBuilder();

					// Step 2: Create a CriteriaQuery instance (specifying the result type)
					CriteriaQuery<ServiceDetailsEntity> cq = cb.createQuery(ServiceDetailsEntity.class);

					// Step 3: Define the root of the query (the FROM clause)
					Root<ServiceDetailsEntity> ServiceDetailsEntity = cq.from(ServiceDetailsEntity.class);

					// Step 4: Create a list to store predicates (conditions)
					List<Predicate> predicates = new ArrayList<>();

					// Step 5: Add predicates (dynamic conditions) based on the input criteria
					if (null != filterMap.get("machineModelCode")
							&& !filterMap.get("machineModelCode").toString().trim().isEmpty()) {
						predicates.add(cb.equal(ServiceDetailsEntity.get("machineModelCode"),
								filterMap.get("machineModelCode").toString()));
					}

					if (null != filterMap.get("machineModelValue")
							&& !filterMap.get("machineModelValue").toString().trim().isEmpty()) {
						predicates.add(cb.equal(ServiceDetailsEntity.get("machineModelValue"),
								filterMap.get("machineModelValue").toString()));
					}

					if (null != filterMap.get("customerName")
							&& !filterMap.get("customerName").toString().trim().isEmpty()) {
						predicates.add(cb.equal(ServiceDetailsEntity.get("customerName"),
								filterMap.get("customerName").toString()));
					}

					if (null != filterMap.get("customerMobileNumber")
							&& !filterMap.get("customerMobileNumber").toString().trim().isEmpty()) {
						predicates.add(cb.equal(ServiceDetailsEntity.get("customerMobileNumber"),
								filterMap.get("customerMobileNumber").toString()));
					}

					if (null != filterMap.get("customerArea")
							&& !filterMap.get("customerArea").toString().trim().isEmpty()) {
						predicates.add(cb.equal(ServiceDetailsEntity.get("customerArea"),
								filterMap.get("customerArea").toString()));
					}

					// Step 6: Set the predicates (conditions) to the CriteriaQuery
					cq.where(cb.and(predicates.toArray(new Predicate[0])));

					// Step 7: Create a query from the CriteriaQuery and execute it
					TypedQuery<ServiceDetailsEntity> query = entityManager.createQuery(cq);
					serviceDetailsList = (List<ServiceDetailsEntity>) query.getResultList();

				}

			} catch (Exception exception) {

				log.error("<==== Error while RoServiceImpl :: lazySearch() ====>", exception);
				exception.printStackTrace();
				return Library.getFailResponseCode(ErrorCode.EXCEPTION.getErrorCode(), ErrorMessages.EXCEPTION_MESSAGE);

			}

			log.info("Inside RoServiceImpl :: lazySearch() - END");
			return Library.getSuccessfulResponse(serviceDetailsList, ErrorCode.SUCCESS_RESPONSE.getErrorCode(),
					ErrorMessages.RECORED_FOUND);

		} else {
			return Library.getFailResponseCode(ErrorCode.UNAUTHORIZED.getErrorCode(), ErrorMessages.UNAUTHORIZED_USER);
		}

	}

}
