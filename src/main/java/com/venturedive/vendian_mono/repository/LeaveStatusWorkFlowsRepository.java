package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.LeaveStatusWorkFlows;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LeaveStatusWorkFlows entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeaveStatusWorkFlowsRepository
    extends JpaRepository<LeaveStatusWorkFlows, Long>, JpaSpecificationExecutor<LeaveStatusWorkFlows> {}
