package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.LeaveTypeApprovalRules;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LeaveTypeApprovalRules entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeaveTypeApprovalRulesRepository
    extends JpaRepository<LeaveTypeApprovalRules, Long>, JpaSpecificationExecutor<LeaveTypeApprovalRules> {}
