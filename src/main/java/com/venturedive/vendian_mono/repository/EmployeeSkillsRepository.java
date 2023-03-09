package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.EmployeeSkills;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmployeeSkills entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeSkillsRepository extends JpaRepository<EmployeeSkills, Long>, JpaSpecificationExecutor<EmployeeSkills> {}
