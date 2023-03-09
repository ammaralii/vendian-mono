package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.LeaveTypeEscalationRules;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LeaveTypeEscalationRules entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeaveTypeEscalationRulesRepository
    extends JpaRepository<LeaveTypeEscalationRules, Long>, JpaSpecificationExecutor<LeaveTypeEscalationRules> {}
