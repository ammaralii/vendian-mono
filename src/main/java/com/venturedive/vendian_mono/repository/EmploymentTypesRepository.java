package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.EmploymentTypes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmploymentTypes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmploymentTypesRepository extends JpaRepository<EmploymentTypes, Long>, JpaSpecificationExecutor<EmploymentTypes> {}
