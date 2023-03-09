package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.EmployeeEmergencyContacts;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmployeeEmergencyContacts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeEmergencyContactsRepository
    extends JpaRepository<EmployeeEmergencyContacts, Long>, JpaSpecificationExecutor<EmployeeEmergencyContacts> {}
