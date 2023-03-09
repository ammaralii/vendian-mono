package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.Deals;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Deals entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DealsRepository extends JpaRepository<Deals, Long>, JpaSpecificationExecutor<Deals> {}
