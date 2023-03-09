package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.ZLeaveRequests;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ZLeaveRequests entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZLeaveRequestsRepository extends JpaRepository<ZLeaveRequests, Long>, JpaSpecificationExecutor<ZLeaveRequests> {}
