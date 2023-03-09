package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.Reasons;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Reasons entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReasonsRepository extends JpaRepository<Reasons, Long>, JpaSpecificationExecutor<Reasons> {}
