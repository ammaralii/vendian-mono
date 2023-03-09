package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.NotificationEvents;
import com.venturedive.vendian_mono.repository.NotificationEventsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NotificationEvents}.
 */
@Service
@Transactional
public class NotificationEventsService {

    private final Logger log = LoggerFactory.getLogger(NotificationEventsService.class);

    private final NotificationEventsRepository notificationEventsRepository;

    public NotificationEventsService(NotificationEventsRepository notificationEventsRepository) {
        this.notificationEventsRepository = notificationEventsRepository;
    }

    /**
     * Save a notificationEvents.
     *
     * @param notificationEvents the entity to save.
     * @return the persisted entity.
     */
    public NotificationEvents save(NotificationEvents notificationEvents) {
        log.debug("Request to save NotificationEvents : {}", notificationEvents);
        return notificationEventsRepository.save(notificationEvents);
    }

    /**
     * Update a notificationEvents.
     *
     * @param notificationEvents the entity to save.
     * @return the persisted entity.
     */
    public NotificationEvents update(NotificationEvents notificationEvents) {
        log.debug("Request to update NotificationEvents : {}", notificationEvents);
        return notificationEventsRepository.save(notificationEvents);
    }

    /**
     * Partially update a notificationEvents.
     *
     * @param notificationEvents the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NotificationEvents> partialUpdate(NotificationEvents notificationEvents) {
        log.debug("Request to partially update NotificationEvents : {}", notificationEvents);

        return notificationEventsRepository
            .findById(notificationEvents.getId())
            .map(existingNotificationEvents -> {
                if (notificationEvents.getName() != null) {
                    existingNotificationEvents.setName(notificationEvents.getName());
                }
                if (notificationEvents.getEffDate() != null) {
                    existingNotificationEvents.setEffDate(notificationEvents.getEffDate());
                }
                if (notificationEvents.getCreatedAt() != null) {
                    existingNotificationEvents.setCreatedAt(notificationEvents.getCreatedAt());
                }
                if (notificationEvents.getUpdatedAt() != null) {
                    existingNotificationEvents.setUpdatedAt(notificationEvents.getUpdatedAt());
                }
                if (notificationEvents.getEndDate() != null) {
                    existingNotificationEvents.setEndDate(notificationEvents.getEndDate());
                }
                if (notificationEvents.getVersion() != null) {
                    existingNotificationEvents.setVersion(notificationEvents.getVersion());
                }

                return existingNotificationEvents;
            })
            .map(notificationEventsRepository::save);
    }

    /**
     * Get all the notificationEvents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NotificationEvents> findAll(Pageable pageable) {
        log.debug("Request to get all NotificationEvents");
        return notificationEventsRepository.findAll(pageable);
    }

    /**
     * Get one notificationEvents by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NotificationEvents> findOne(Long id) {
        log.debug("Request to get NotificationEvents : {}", id);
        return notificationEventsRepository.findById(id);
    }

    /**
     * Delete the notificationEvents by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete NotificationEvents : {}", id);
        notificationEventsRepository.deleteById(id);
    }
}
