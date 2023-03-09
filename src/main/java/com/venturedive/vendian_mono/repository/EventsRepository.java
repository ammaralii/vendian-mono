package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.Events;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Events entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventsRepository extends JpaRepository<Events, Long>, JpaSpecificationExecutor<Events> {}
