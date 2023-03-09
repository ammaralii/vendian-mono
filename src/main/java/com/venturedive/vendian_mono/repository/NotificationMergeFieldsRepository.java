package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.NotificationMergeFields;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NotificationMergeFields entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotificationMergeFieldsRepository
    extends JpaRepository<NotificationMergeFields, Long>, JpaSpecificationExecutor<NotificationMergeFields> {}
