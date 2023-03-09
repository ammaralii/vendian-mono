package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.Competencies;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Competencies entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompetenciesRepository extends JpaRepository<Competencies, Long>, JpaSpecificationExecutor<Competencies> {}
