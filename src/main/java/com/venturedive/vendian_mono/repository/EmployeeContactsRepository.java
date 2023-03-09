package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.EmployeeContacts;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmployeeContacts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeContactsRepository extends JpaRepository<EmployeeContacts, Long>, JpaSpecificationExecutor<EmployeeContacts> {}
