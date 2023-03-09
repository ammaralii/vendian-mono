package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.ClaimRequests;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ClaimRequests entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClaimRequestsRepository extends JpaRepository<ClaimRequests, Long>, JpaSpecificationExecutor<ClaimRequests> {}
