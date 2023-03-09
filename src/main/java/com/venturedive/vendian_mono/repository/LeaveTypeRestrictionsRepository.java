package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.LeaveTypeRestrictions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LeaveTypeRestrictions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeaveTypeRestrictionsRepository
    extends JpaRepository<LeaveTypeRestrictions, Long>, JpaSpecificationExecutor<LeaveTypeRestrictions> {}
