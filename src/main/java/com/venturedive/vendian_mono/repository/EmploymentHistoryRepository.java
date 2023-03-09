package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.EmploymentHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmploymentHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmploymentHistoryRepository extends JpaRepository<EmploymentHistory, Long>, JpaSpecificationExecutor<EmploymentHistory> {}
