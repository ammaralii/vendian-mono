package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.Ratings;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Ratings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RatingsRepository extends JpaRepository<Ratings, Long>, JpaSpecificationExecutor<Ratings> {}
