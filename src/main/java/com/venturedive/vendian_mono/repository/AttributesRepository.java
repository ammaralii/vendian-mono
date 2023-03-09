package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.Attributes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Attributes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttributesRepository extends JpaRepository<Attributes, Long>, JpaSpecificationExecutor<Attributes> {}
