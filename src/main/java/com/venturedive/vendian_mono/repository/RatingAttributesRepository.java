package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.RatingAttributes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RatingAttributes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RatingAttributesRepository extends JpaRepository<RatingAttributes, Long>, JpaSpecificationExecutor<RatingAttributes> {}
