package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.EmployeeRoles;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmployeeRoles entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeRolesRepository extends JpaRepository<EmployeeRoles, Long>, JpaSpecificationExecutor<EmployeeRoles> {}
