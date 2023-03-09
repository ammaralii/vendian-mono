package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.ClaimDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ClaimDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClaimDetailsRepository extends JpaRepository<ClaimDetails, Long>, JpaSpecificationExecutor<ClaimDetails> {}
