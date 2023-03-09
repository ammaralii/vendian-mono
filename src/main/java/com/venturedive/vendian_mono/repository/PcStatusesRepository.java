package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.PcStatuses;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PcStatuses entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PcStatusesRepository extends JpaRepository<PcStatuses, Long>, JpaSpecificationExecutor<PcStatuses> {}
