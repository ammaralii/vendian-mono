package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.UserRatingsRemarks;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UserRatingsRemarks entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserRatingsRemarksRepository
    extends JpaRepository<UserRatingsRemarks, Long>, JpaSpecificationExecutor<UserRatingsRemarks> {}
