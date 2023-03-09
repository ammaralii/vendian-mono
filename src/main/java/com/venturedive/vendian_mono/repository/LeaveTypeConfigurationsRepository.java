package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.LeaveTypeConfigurations;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LeaveTypeConfigurations entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeaveTypeConfigurationsRepository
    extends JpaRepository<LeaveTypeConfigurations, Long>, JpaSpecificationExecutor<LeaveTypeConfigurations> {}
