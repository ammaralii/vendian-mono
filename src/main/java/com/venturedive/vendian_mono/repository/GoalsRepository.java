package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.Goals;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Goals entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GoalsRepository extends JpaRepository<Goals, Long>, JpaSpecificationExecutor<Goals> {}
