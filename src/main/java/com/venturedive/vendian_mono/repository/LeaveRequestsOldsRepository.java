package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.LeaveRequestsOlds;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LeaveRequestsOlds entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeaveRequestsOldsRepository extends JpaRepository<LeaveRequestsOlds, Long>, JpaSpecificationExecutor<LeaveRequestsOlds> {}
