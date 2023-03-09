package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.GoalGroupMappings;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the GoalGroupMappings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GoalGroupMappingsRepository extends JpaRepository<GoalGroupMappings, Long>, JpaSpecificationExecutor<GoalGroupMappings> {}
