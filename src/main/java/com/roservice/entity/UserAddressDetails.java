/**
 * RamkumarSudalaimani
 */
package com.roservice.entity;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.roservice.common.Trackable;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 
 */
@Entity
@Data
@Table(name = "user_address_details")
@EqualsAndHashCode(callSuper = true)
@Audited(withModifiedFlag = true)
public class UserAddressDetails extends Trackable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 619538229111271767L;

	private String countryCode;

	private String stateCode;

	private String districtCode;

	private String countryValue;

	private String stateValue;

	private String districtValue;

	private String area;

	private String city;

	private String street;

	private String pincode;

	private String houseNumber;

	private String address;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "user_id")
	@ToString.Exclude
	private User user;

}
