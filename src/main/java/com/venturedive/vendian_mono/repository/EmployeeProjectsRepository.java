package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.EmployeeProjects;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmployeeProjects entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeProjectsRepository extends JpaRepository<EmployeeProjects, Long>, JpaSpecificationExecutor<EmployeeProjects> {}
