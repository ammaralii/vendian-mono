package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.LeaveStatuses;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LeaveStatuses entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeaveStatusesRepository extends JpaRepository<LeaveStatuses, Long>, JpaSpecificationExecutor<LeaveStatuses> {}
