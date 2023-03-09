package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.EmployeeProjectRatings;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmployeeProjectRatings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeProjectRatingsRepository
    extends JpaRepository<EmployeeProjectRatings, Long>, JpaSpecificationExecutor<EmployeeProjectRatings> {}
