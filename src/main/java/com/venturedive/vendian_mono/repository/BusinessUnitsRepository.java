package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.BusinessUnits;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BusinessUnits entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BusinessUnitsRepository extends JpaRepository<BusinessUnits, Long>, JpaSpecificationExecutor<BusinessUnits> {}
