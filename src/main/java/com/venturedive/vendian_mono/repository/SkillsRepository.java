package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.Skills;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Skills entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SkillsRepository extends JpaRepository<Skills, Long>, JpaSpecificationExecutor<Skills> {}
