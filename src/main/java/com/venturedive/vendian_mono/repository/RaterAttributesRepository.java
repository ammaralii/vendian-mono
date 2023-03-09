package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.RaterAttributes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RaterAttributes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RaterAttributesRepository extends JpaRepository<RaterAttributes, Long>, JpaSpecificationExecutor<RaterAttributes> {}
