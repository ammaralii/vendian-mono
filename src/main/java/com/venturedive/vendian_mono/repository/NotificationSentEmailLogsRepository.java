package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.NotificationSentEmailLogs;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NotificationSentEmailLogs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotificationSentEmailLogsRepository
    extends JpaRepository<NotificationSentEmailLogs, Long>, JpaSpecificationExecutor<NotificationSentEmailLogs> {}
