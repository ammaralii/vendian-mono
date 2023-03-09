package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.PcRatingAttributesCategories;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PcRatingAttributesCategories entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PcRatingAttributesCategoriesRepository
    extends JpaRepository<PcRatingAttributesCategories, Long>, JpaSpecificationExecutor<PcRatingAttributesCategories> {}
