package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.UserGoalRaterAttributes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UserGoalRaterAttributes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserGoalRaterAttributesRepository
    extends JpaRepository<UserGoalRaterAttributes, Long>, JpaSpecificationExecutor<UserGoalRaterAttributes> {}
