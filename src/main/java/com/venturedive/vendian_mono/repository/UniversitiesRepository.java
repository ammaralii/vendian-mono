package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.Universities;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Universities entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UniversitiesRepository extends JpaRepository<Universities, Long>, JpaSpecificationExecutor<Universities> {}
