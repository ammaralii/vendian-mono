package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.Benefits;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Benefits entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BenefitsRepository extends JpaRepository<Benefits, Long>, JpaSpecificationExecutor<Benefits> {}
