/**
 * RamkumarSudalaimani
 */
package com.roservice.entity;

import org.hibernate.envers.Audited;

import com.roservice.common.Trackable;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 */
@Entity
@Table(name = "machine_models")
@Getter
@Setter
@Audited(withModifiedFlag = true)
public class RoMachineModelsEntity extends Trackable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 779703833439087934L;

	private String modelCode;

	private String modelValue;

	private Double price = 0.0d;

	private String colour;

	private Long capacity;

}
