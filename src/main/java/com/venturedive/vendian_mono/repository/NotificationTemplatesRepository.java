package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.NotificationTemplates;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NotificationTemplates entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotificationTemplatesRepository
    extends JpaRepository<NotificationTemplates, Long>, JpaSpecificationExecutor<NotificationTemplates> {}
