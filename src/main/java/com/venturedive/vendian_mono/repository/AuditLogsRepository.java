package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.AuditLogs;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AuditLogs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuditLogsRepository extends JpaRepository<AuditLogs, Long>, JpaSpecificationExecutor<AuditLogs> {}
