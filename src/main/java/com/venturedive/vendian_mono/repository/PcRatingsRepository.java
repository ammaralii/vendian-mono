package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.PcRatings;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PcRatings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PcRatingsRepository extends JpaRepository<PcRatings, Long>, JpaSpecificationExecutor<PcRatings> {}
