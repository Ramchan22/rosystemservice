/**
 * RamkumarSudalaimani
 */
package com.roservice.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.roservice.common.AuthenticationDTO;
import com.roservice.common.ErrorCode;
import com.roservice.common.ErrorMessages;
import com.roservice.common.GenericResponse;
import com.roservice.common.Library;
import com.roservice.dto.UserDTO;
import com.roservice.entity.User;
import com.roservice.repository.UserRepository;
import com.roservice.service.UserService;

import lombok.extern.log4j.Log4j2;

/**
 * 
 */
@Service
@Log4j2
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public GenericResponse createNewUser(AuthenticationDTO authenticationDTO, User userDetails) {
		log.info("Inside UserServiceImpl :: createNewUser() - START");

		if (null != authenticationDTO
				&& (authenticationDTO.getIsAdminUser() || authenticationDTO.getIsOfficeAssistant())) {

			try {

				if (null != userDetails) {
					if (null != userDetails.getUserAddressDetailsList()
							&& !userDetails.getUserAddressDetailsList().isEmpty()
							&& userDetails.getUserAddressDetailsList().size() > 0) {

						userDetails.getUserAddressDetailsList().stream().forEach(addressDetails -> {
							addressDetails.setAddress(null != addressDetails.getHouseNumber()
									&& StringUtils.hasLength(addressDetails.getHouseNumber().trim())
											? addressDetails.getHouseNumber().concat(", ")
											: "".concat(null != addressDetails.getStreet()
													&& StringUtils.hasLength(addressDetails.getStreet().trim())
															? addressDetails.getStreet().concat(", ")
															: "")
													.concat(null != addressDetails.getArea()
															&& StringUtils.hasLength(addressDetails.getArea().trim())
																	? addressDetails.getArea().concat(", ")
																	: "")
													.concat(null != addressDetails.getCity()
															&& StringUtils.hasLength(addressDetails.getCity().trim())
																	? addressDetails.getCity().concat(", ")
																	: "")
													.concat(null != addressDetails.getPincode()
															&& StringUtils.hasLength(addressDetails.getPincode().trim())
																	? addressDetails.getPincode().concat(", ")
																	: ""));
						});

					}

					userDetails.setCreatedDate(new Date());
					userDetails.setModifiedDate(new Date());
					userDetails.setPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));

					userRepository.save(userDetails);

				}

			} catch (Exception exception) {

				log.error("<==== Error while create the new user ===>", exception);
				exception.printStackTrace();
				return Library.getFailResponseCode(ErrorCode.FAILURE_RESPONSE.getErrorCode(),
						ErrorMessages.USER_CREATION_FAILURE);

			}

		} else {
			return Library.getFailResponseCode(ErrorCode.UNAUTHORIZED.getErrorCode(), ErrorMessages.UNAUTHORIZED_USER);
		}

		log.info("Inside UserServiceImpl :: createNewUser() - END");
		return Library.getSuccessfulResponse(null, ErrorCode.CREATED.getErrorCode(),
				ErrorMessages.USER_CREATED_SUCCESSFULLY);
	}

	/**
	 * For User Authentication Do not do anything here
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		log.info("Inside UserServiceImpl :: loadUserByUsername() - START");

		User user = userRepository.findByUserName(username);

		if (null == user)
			user = userRepository.findByEmailId(username);

		if (null == user)
			throw new UsernameNotFoundException(username);

		log.info("Inside UserServiceImpl :: loadUserByUsername() - END");

		return new org.springframework.security.core.userdetails.User(username, user.getPassword(), new ArrayList<>());
	}

	/**
	 * For User Authentication Do not do anything here
	 */
	@Override
	public AuthenticationDTO getUserDetails(String userName) {

		log.info("Inside UserServiceImpl :: getUserDetails() - START");

		User userDetails = userRepository.findByUserName(userName);

		if (null == userDetails)
			userDetails = userRepository.findByEmailId(userName);

		if (null == userDetails)
			throw new UsernameNotFoundException(userName);

		AuthenticationDTO authenticationDTO = new AuthenticationDTO();

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.map(userDetails, authenticationDTO);

//		BeanUtils.copyProperties(authenticationDTO, userDetails);

//		authenticationDTO.setFirstName(userDetails.getFirstName());
//		authenticationDTO.setIsAdminUser(userDetails.getIsAdminUser());
//		authenticationDTO.setIsOfficeAssistant(userDetails.getIsOfficeAssistant());
//		authenticationDTO.setLastName(userDetails.getLastName());
//		authenticationDTO.setMiddleName(userDetails.getMiddleName());
//		authenticationDTO.setUserId(userDetails.getId());
//		authenticationDTO.setUserName(userDetails.getUserName());

		userDetails.setIsUserLogedIn(true);
		userRepository.save(userDetails);

		log.info("Inside UserServiceImpl :: getUserDetails() - END");

		return authenticationDTO;
	}

	/**
	 * Method is used to logOut the user.
	 */
	@Override
	public GenericResponse logout(AuthenticationDTO authenticationDTO) {

		if (null != authenticationDTO) {

			User user = userRepository.findByUserNameAndIsUserLogedIn(authenticationDTO.getUserName(), true);

			if (null != user) {
				user.setIsUserLogedIn(false);
				userRepository.save(user);

				return Library.getSuccessfulResponse(null, ErrorCode.SUCCESS_RESPONSE.getErrorCode(),
						ErrorMessages.LOGOUT_AUTHORIZED);

			} else {
				return Library.getFailResponseCode(ErrorCode.EXCEPTION.getErrorCode(), ErrorMessages.EXCEPTION_MESSAGE);
			}

		} else {
			return Library.getFailResponseCode(ErrorCode.EXCEPTION.getErrorCode(), ErrorMessages.EXCEPTION_MESSAGE);
		}

	}

	/**
	 *
	 */
	@Override
	public GenericResponse getAllUser(AuthenticationDTO authenticationDTO) {
		log.info("Inside UserServiceImpl :: getAllUser() - STRAT");

		List<UserDTO> userDetailsDTOList = new ArrayList<UserDTO>();
		try {
			List<User> userDetailsList = new ArrayList<User>();

			User user = userRepository.findByUserNameAndId(authenticationDTO.getUserName(),
					authenticationDTO.getUserId());

			if (null == user)
				return Library.getFailResponseCode(ErrorCode.EXCEPTION.getErrorCode(), ErrorMessages.EXCEPTION_MESSAGE);

			if (user.getIsAdminUser()) {

				userDetailsList = userRepository.findAllUserDetails();

				if (null != userDetailsList && !userDetailsList.isEmpty() && userDetailsList.size() > 0) {

					userDetailsDTOList = userDetailsList.stream()
							.filter(userDetails -> !authenticationDTO.getUserName().equals(userDetails.getUserName()))
							.map(userDetails -> new UserDTO(userDetails.getUserName(), userDetails.getEmailId(),
									userDetails.getMobileNumber(), userDetails.getFirstName(),
									userDetails.getMiddleName(), userDetails.getLastName(),
									userDetails.getIsAdminUser(), userDetails.getIsOfficeAssistant(),
									userDetails.getIsUserLogedIn(), userDetails.getId()))
							.collect(Collectors.toList());

				}

			} else if (user.getIsOfficeAssistant()) {

				userDetailsList = userRepository.findAllUserDetailsExceptAdminUser();

				if (null != userDetailsList && !userDetailsList.isEmpty() && userDetailsList.size() > 0) {

					userDetailsDTOList = userDetailsList.stream()
							.map(userDetails -> new UserDTO(userDetails.getUserName(), userDetails.getEmailId(),
									userDetails.getMobileNumber(), userDetails.getFirstName(),
									userDetails.getMiddleName(), userDetails.getLastName(),
									userDetails.getIsAdminUser(), userDetails.getIsOfficeAssistant(),
									userDetails.getIsUserLogedIn(), userDetails.getId()))
							.collect(Collectors.toList());

				}

			}

		} catch (Exception exception) {
			log.error("<=== Error while get all the user details in UserServiceImpl :: getAllUser() ===>", exception);
			exception.printStackTrace();
		}

		log.info("Inside UserServiceImpl :: getAllUser() - END");
		return Library.getSuccessfulResponse(userDetailsDTOList, ErrorCode.SUCCESS_RESPONSE.getErrorCode(),
				ErrorMessages.RECORED_FOUND);
	}

}
