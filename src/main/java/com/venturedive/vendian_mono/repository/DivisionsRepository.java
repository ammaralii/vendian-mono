package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.Divisions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Divisions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DivisionsRepository extends JpaRepository<Divisions, Long>, JpaSpecificationExecutor<Divisions> {}
