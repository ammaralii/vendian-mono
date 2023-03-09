package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.RolePermissions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RolePermissions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RolePermissionsRepository extends JpaRepository<RolePermissions, Long>, JpaSpecificationExecutor<RolePermissions> {}
