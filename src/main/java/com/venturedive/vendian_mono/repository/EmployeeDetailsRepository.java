package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.EmployeeDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmployeeDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeDetailsRepository extends JpaRepository<EmployeeDetails, Long>, JpaSpecificationExecutor<EmployeeDetails> {}
