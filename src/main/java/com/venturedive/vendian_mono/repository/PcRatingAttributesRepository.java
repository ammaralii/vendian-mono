package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.PcRatingAttributes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PcRatingAttributes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PcRatingAttributesRepository
    extends JpaRepository<PcRatingAttributes, Long>, JpaSpecificationExecutor<PcRatingAttributes> {}
