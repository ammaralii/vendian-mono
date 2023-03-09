package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.EmployeeProjectRoles;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmployeeProjectRoles entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeProjectRolesRepository
    extends JpaRepository<EmployeeProjectRoles, Long>, JpaSpecificationExecutor<EmployeeProjectRoles> {}
