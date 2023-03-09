package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.LeaveEscalationCriterias;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LeaveEscalationCriterias entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeaveEscalationCriteriasRepository
    extends JpaRepository<LeaveEscalationCriterias, Long>, JpaSpecificationExecutor<LeaveEscalationCriterias> {}
