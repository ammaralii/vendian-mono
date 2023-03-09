package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.Tracks;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Tracks entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TracksRepository extends JpaRepository<Tracks, Long>, JpaSpecificationExecutor<Tracks> {}
