package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.QuestionsProcessingEventLogs;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the QuestionsProcessingEventLogs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionsProcessingEventLogsRepository
    extends JpaRepository<QuestionsProcessingEventLogs, Long>, JpaSpecificationExecutor<QuestionsProcessingEventLogs> {}
