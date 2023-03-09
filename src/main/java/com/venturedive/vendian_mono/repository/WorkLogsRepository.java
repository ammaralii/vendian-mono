package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.WorkLogs;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the WorkLogs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkLogsRepository extends JpaRepository<WorkLogs, Long>, JpaSpecificationExecutor<WorkLogs> {}
