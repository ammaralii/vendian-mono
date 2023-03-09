package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.UserRatings;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UserRatings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserRatingsRepository extends JpaRepository<UserRatings, Long>, JpaSpecificationExecutor<UserRatings> {}
