package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.RatingCategories;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RatingCategories entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RatingCategoriesRepository extends JpaRepository<RatingCategories, Long>, JpaSpecificationExecutor<RatingCategories> {}
