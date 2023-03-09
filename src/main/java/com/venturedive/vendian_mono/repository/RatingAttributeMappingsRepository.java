package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.RatingAttributeMappings;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RatingAttributeMappings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RatingAttributeMappingsRepository
    extends JpaRepository<RatingAttributeMappings, Long>, JpaSpecificationExecutor<RatingAttributeMappings> {}
