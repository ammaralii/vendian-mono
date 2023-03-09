package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.EmploymentStatuses;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmploymentStatuses entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmploymentStatusesRepository
    extends JpaRepository<EmploymentStatuses, Long>, JpaSpecificationExecutor<EmploymentStatuses> {}
