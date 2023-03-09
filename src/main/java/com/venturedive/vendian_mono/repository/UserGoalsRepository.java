package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.UserGoals;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UserGoals entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserGoalsRepository extends JpaRepository<UserGoals, Long>, JpaSpecificationExecutor<UserGoals> {}
