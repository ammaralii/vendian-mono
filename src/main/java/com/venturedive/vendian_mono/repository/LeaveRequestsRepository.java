package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.LeaveRequests;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LeaveRequests entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeaveRequestsRepository extends JpaRepository<LeaveRequests, Long>, JpaSpecificationExecutor<LeaveRequests> {}
