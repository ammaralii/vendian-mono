package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.PcRatingsTrainings;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PcRatingsTrainings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PcRatingsTrainingsRepository
    extends JpaRepository<PcRatingsTrainings, Long>, JpaSpecificationExecutor<PcRatingsTrainings> {}
