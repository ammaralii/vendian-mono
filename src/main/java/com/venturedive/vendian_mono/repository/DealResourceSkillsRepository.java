package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.DealResourceSkills;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DealResourceSkills entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DealResourceSkillsRepository
    extends JpaRepository<DealResourceSkills, Long>, JpaSpecificationExecutor<DealResourceSkills> {}
