package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.RatingOptions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RatingOptions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RatingOptionsRepository extends JpaRepository<RatingOptions, Long>, JpaSpecificationExecutor<RatingOptions> {}
