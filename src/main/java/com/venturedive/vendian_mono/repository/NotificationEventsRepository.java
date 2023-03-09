package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.NotificationEvents;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NotificationEvents entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotificationEventsRepository
    extends JpaRepository<NotificationEvents, Long>, JpaSpecificationExecutor<NotificationEvents> {}
