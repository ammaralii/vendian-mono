package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.PerformanceCycleEmployeeRating;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PerformanceCycleEmployeeRating entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PerformanceCycleEmployeeRatingRepository
    extends JpaRepository<PerformanceCycleEmployeeRating, Long>, JpaSpecificationExecutor<PerformanceCycleEmployeeRating> {}
