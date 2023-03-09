package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.EmployeeEducation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmployeeEducation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeEducationRepository extends JpaRepository<EmployeeEducation, Long>, JpaSpecificationExecutor<EmployeeEducation> {}
