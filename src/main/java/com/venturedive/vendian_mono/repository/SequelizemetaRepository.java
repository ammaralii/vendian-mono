package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.Sequelizemeta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Sequelizemeta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SequelizemetaRepository extends JpaRepository<Sequelizemeta, Long>, JpaSpecificationExecutor<Sequelizemeta> {}
