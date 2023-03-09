package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.LeaveEscalationApprovers;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LeaveEscalationApprovers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeaveEscalationApproversRepository
    extends JpaRepository<LeaveEscalationApprovers, Long>, JpaSpecificationExecutor<LeaveEscalationApprovers> {}
