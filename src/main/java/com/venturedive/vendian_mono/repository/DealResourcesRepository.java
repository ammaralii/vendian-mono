package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.DealResources;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DealResources entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DealResourcesRepository extends JpaRepository<DealResources, Long>, JpaSpecificationExecutor<DealResources> {}
