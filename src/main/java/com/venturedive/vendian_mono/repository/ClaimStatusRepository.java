package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.ClaimStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ClaimStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClaimStatusRepository extends JpaRepository<ClaimStatus, Long>, JpaSpecificationExecutor<ClaimStatus> {}
