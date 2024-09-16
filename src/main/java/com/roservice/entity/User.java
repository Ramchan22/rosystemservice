/**
 * RamkumarSudalaimani
 */
package com.roservice.entity;

import java.util.List;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.roservice.common.Trackable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 */
@Entity
@Data
@Table(name = "user")
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Audited(withModifiedFlag = true)
public class User extends Trackable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 444669573483992048L;

	@Column(unique = true, nullable = false)
	private String userName;

	@Column(unique = true)
	private String emailId;

	@Column(unique = true, nullable = false)
	private String mobileNumber;

	private String password;

	@Column(nullable = false)
	private String firstName;

	private String middleName;

	private String lastName;

	private Boolean isAdminUser = false;

	private Boolean isOfficeAssistant = false;

	private Boolean isUserLogedIn = false;

	@JsonManagedReference
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<UserAddressDetails> userAddressDetailsList;

}
