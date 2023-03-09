package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.HrPerformanceCycles;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the HrPerformanceCycles entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HrPerformanceCyclesRepository
    extends JpaRepository<HrPerformanceCycles, Long>, JpaSpecificationExecutor<HrPerformanceCycles> {}
