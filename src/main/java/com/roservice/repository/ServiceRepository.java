/**
 * RamkumarSudalaimani
 */
package com.roservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roservice.entity.ServiceDetailsEntity;

/**
 * 
 */
@Repository
public interface ServiceRepository extends JpaRepository<ServiceDetailsEntity, Long> {

}
