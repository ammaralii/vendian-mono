package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.ProjectRoles;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProjectRoles entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectRolesRepository extends JpaRepository<ProjectRoles, Long>, JpaSpecificationExecutor<ProjectRoles> {}
