package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.EmployeeProfileHistoryLogs;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmployeeProfileHistoryLogs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeProfileHistoryLogsRepository
    extends JpaRepository<EmployeeProfileHistoryLogs, Long>, JpaSpecificationExecutor<EmployeeProfileHistoryLogs> {}
