package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.LeaveApprovalCriterias;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LeaveApprovalCriterias entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeaveApprovalCriteriasRepository
    extends JpaRepository<LeaveApprovalCriterias, Long>, JpaSpecificationExecutor<LeaveApprovalCriterias> {}
