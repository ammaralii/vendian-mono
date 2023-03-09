package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.DealRequirements;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DealRequirements entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DealRequirementsRepository extends JpaRepository<DealRequirements, Long>, JpaSpecificationExecutor<DealRequirements> {}
