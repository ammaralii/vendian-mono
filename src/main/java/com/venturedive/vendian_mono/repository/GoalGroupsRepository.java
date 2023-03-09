package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.GoalGroups;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the GoalGroups entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GoalGroupsRepository extends JpaRepository<GoalGroups, Long>, JpaSpecificationExecutor<GoalGroups> {}
