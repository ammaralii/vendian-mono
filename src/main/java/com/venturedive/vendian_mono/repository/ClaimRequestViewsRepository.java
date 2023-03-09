package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.ClaimRequestViews;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ClaimRequestViews entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClaimRequestViewsRepository extends JpaRepository<ClaimRequestViews, Long>, JpaSpecificationExecutor<ClaimRequestViews> {}
