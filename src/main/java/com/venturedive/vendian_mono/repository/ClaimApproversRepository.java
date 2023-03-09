package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.ClaimApprovers;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ClaimApprovers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClaimApproversRepository extends JpaRepository<ClaimApprovers, Long>, JpaSpecificationExecutor<ClaimApprovers> {}
