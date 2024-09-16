/**
 * RamkumarSudalaimani
 */
package com.roservice.common;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

/**
 * 
 */
@Data
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
/* @Audited */
@EnableJpaAuditing
public class Trackable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -141289637039401046L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	public Date createdDate;

	@CreatedBy
	@Column(name = "created_by")
	public Long createdBy;

	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date")
	public Date modifiedDate;

	@LastModifiedBy
	@Column(name = "modified_by")
	public Long modifiedBy;

}
