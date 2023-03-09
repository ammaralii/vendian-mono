package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.DealEvents;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DealEvents entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DealEventsRepository extends JpaRepository<DealEvents, Long>, JpaSpecificationExecutor<DealEvents> {}
