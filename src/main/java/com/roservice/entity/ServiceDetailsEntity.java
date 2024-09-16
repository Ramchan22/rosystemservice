/**
 * RamkumarSudalaimani
 */
package com.roservice.entity;

import java.util.Date;

import org.hibernate.envers.Audited;

import com.roservice.common.Trackable;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 */
@Entity
@Getter
@Setter
@Table(name = "service_details")
@Audited(withModifiedFlag = true)
public class ServiceDetailsEntity extends Trackable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9036450272755845776L;

	private String machineModelCode;

	private String machineModelValue;

	private String customerName;

	private String customerMobileNumber;

	private String customerArea;

	private String customerAddress;

	private Double materialPrice = 0.0d;

	private Double regularRetailPrice = 0.0d;

	private Double deliveredPrice = 0.0d;

	private Boolean isWarrentyCovers = false;

	private int numberOfWarrentyDays = 0;

	private Boolean isNewMachineOrder = false;

	private String serviceDetails;

	private Double profit = 0.0d;

	@Temporal(TemporalType.DATE)
	private Date serviceDate;

}
