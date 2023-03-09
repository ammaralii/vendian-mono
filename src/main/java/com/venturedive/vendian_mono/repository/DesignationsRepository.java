package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.Designations;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Designations entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DesignationsRepository extends JpaRepository<Designations, Long>, JpaSpecificationExecutor<Designations> {}
