package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.RaterAttributeMappings;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RaterAttributeMappings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RaterAttributeMappingsRepository
    extends JpaRepository<RaterAttributeMappings, Long>, JpaSpecificationExecutor<RaterAttributeMappings> {}
