package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.LeaveRequestApproverFlows;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LeaveRequestApproverFlows entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeaveRequestApproverFlowsRepository
    extends JpaRepository<LeaveRequestApproverFlows, Long>, JpaSpecificationExecutor<LeaveRequestApproverFlows> {}
