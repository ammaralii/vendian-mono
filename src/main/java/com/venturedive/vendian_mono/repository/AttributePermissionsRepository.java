package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.AttributePermissions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AttributePermissions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttributePermissionsRepository
    extends JpaRepository<AttributePermissions, Long>, JpaSpecificationExecutor<AttributePermissions> {}
