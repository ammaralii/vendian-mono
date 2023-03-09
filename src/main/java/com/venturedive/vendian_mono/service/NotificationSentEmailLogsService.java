package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.NotificationSentEmailLogs;
import com.venturedive.vendian_mono.repository.NotificationSentEmailLogsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NotificationSentEmailLogs}.
 */
@Service
@Transactional
public class NotificationSentEmailLogsService {

    private final Logger log = LoggerFactory.getLogger(NotificationSentEmailLogsService.class);

    private final NotificationSentEmailLogsRepository notificationSentEmailLogsRepository;

    public NotificationSentEmailLogsService(NotificationSentEmailLogsRepository notificationSentEmailLogsRepository) {
        this.notificationSentEmailLogsRepository = notificationSentEmailLogsRepository;
    }

    /**
     * Save a notificationSentEmailLogs.
     *
     * @param notificationSentEmailLogs the entity to save.
     * @return the persisted entity.
     */
    public NotificationSentEmailLogs save(NotificationSentEmailLogs notificationSentEmailLogs) {
        log.debug("Request to save NotificationSentEmailLogs : {}", notificationSentEmailLogs);
        return notificationSentEmailLogsRepository.save(notificationSentEmailLogs);
    }

    /**
     * Update a notificationSentEmailLogs.
     *
     * @param notificationSentEmailLogs the entity to save.
     * @return the persisted entity.
     */
    public NotificationSentEmailLogs update(NotificationSentEmailLogs notificationSentEmailLogs) {
        log.debug("Request to update NotificationSentEmailLogs : {}", notificationSentEmailLogs);
        return notificationSentEmailLogsRepository.save(notificationSentEmailLogs);
    }

    /**
     * Partially update a notificationSentEmailLogs.
     *
     * @param notificationSentEmailLogs the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NotificationSentEmailLogs> partialUpdate(NotificationSentEmailLogs notificationSentEmailLogs) {
        log.debug("Request to partially update NotificationSentEmailLogs : {}", notificationSentEmailLogs);

        return notificationSentEmailLogsRepository
            .findById(notificationSentEmailLogs.getId())
            .map(existingNotificationSentEmailLogs -> {
                if (notificationSentEmailLogs.getEmail() != null) {
                    existingNotificationSentEmailLogs.setEmail(notificationSentEmailLogs.getEmail());
                }
                if (notificationSentEmailLogs.getCreatedAt() != null) {
                    existingNotificationSentEmailLogs.setCreatedAt(notificationSentEmailLogs.getCreatedAt());
                }
                if (notificationSentEmailLogs.getUpdatedAt() != null) {
                    existingNotificationSentEmailLogs.setUpdatedAt(notificationSentEmailLogs.getUpdatedAt());
                }
                if (notificationSentEmailLogs.getDeletedAt() != null) {
                    existingNotificationSentEmailLogs.setDeletedAt(notificationSentEmailLogs.getDeletedAt());
                }
                if (notificationSentEmailLogs.getVersion() != null) {
                    existingNotificationSentEmailLogs.setVersion(notificationSentEmailLogs.getVersion());
                }

                return existingNotificationSentEmailLogs;
            })
            .map(notificationSentEmailLogsRepository::save);
    }

    /**
     * Get all the notificationSentEmailLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NotificationSentEmailLogs> findAll(Pageable pageable) {
        log.debug("Request to get all NotificationSentEmailLogs");
        return notificationSentEmailLogsRepository.findAll(pageable);
    }

    /**
     * Get one notificationSentEmailLogs by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NotificationSentEmailLogs> findOne(Long id) {
        log.debug("Request to get NotificationSentEmailLogs : {}", id);
        return notificationSentEmailLogsRepository.findById(id);
    }

    /**
     * Delete the notificationSentEmailLogs by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete NotificationSentEmailLogs : {}", id);
        notificationSentEmailLogsRepository.deleteById(id);
    }
}
