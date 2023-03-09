package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.EmployeeWorks;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmployeeWorks entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeWorksRepository extends JpaRepository<EmployeeWorks, Long>, JpaSpecificationExecutor<EmployeeWorks> {}
