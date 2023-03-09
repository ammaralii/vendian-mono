package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.NotificationMergeFields;
import com.venturedive.vendian_mono.repository.NotificationMergeFieldsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NotificationMergeFields}.
 */
@Service
@Transactional
public class NotificationMergeFieldsService {

    private final Logger log = LoggerFactory.getLogger(NotificationMergeFieldsService.class);

    private final NotificationMergeFieldsRepository notificationMergeFieldsRepository;

    public NotificationMergeFieldsService(NotificationMergeFieldsRepository notificationMergeFieldsRepository) {
        this.notificationMergeFieldsRepository = notificationMergeFieldsRepository;
    }

    /**
     * Save a notificationMergeFields.
     *
     * @param notificationMergeFields the entity to save.
     * @return the persisted entity.
     */
    public NotificationMergeFields save(NotificationMergeFields notificationMergeFields) {
        log.debug("Request to save NotificationMergeFields : {}", notificationMergeFields);
        return notificationMergeFieldsRepository.save(notificationMergeFields);
    }

    /**
     * Update a notificationMergeFields.
     *
     * @param notificationMergeFields the entity to save.
     * @return the persisted entity.
     */
    public NotificationMergeFields update(NotificationMergeFields notificationMergeFields) {
        log.debug("Request to update NotificationMergeFields : {}", notificationMergeFields);
        return notificationMergeFieldsRepository.save(notificationMergeFields);
    }

    /**
     * Partially update a notificationMergeFields.
     *
     * @param notificationMergeFields the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NotificationMergeFields> partialUpdate(NotificationMergeFields notificationMergeFields) {
        log.debug("Request to partially update NotificationMergeFields : {}", notificationMergeFields);

        return notificationMergeFieldsRepository
            .findById(notificationMergeFields.getId())
            .map(existingNotificationMergeFields -> {
                if (notificationMergeFields.getField() != null) {
                    existingNotificationMergeFields.setField(notificationMergeFields.getField());
                }
                if (notificationMergeFields.getEffDate() != null) {
                    existingNotificationMergeFields.setEffDate(notificationMergeFields.getEffDate());
                }
                if (notificationMergeFields.getCreatedAt() != null) {
                    existingNotificationMergeFields.setCreatedAt(notificationMergeFields.getCreatedAt());
                }
                if (notificationMergeFields.getUpdatedAt() != null) {
                    existingNotificationMergeFields.setUpdatedAt(notificationMergeFields.getUpdatedAt());
                }
                if (notificationMergeFields.getEndDate() != null) {
                    existingNotificationMergeFields.setEndDate(notificationMergeFields.getEndDate());
                }
                if (notificationMergeFields.getVersion() != null) {
                    existingNotificationMergeFields.setVersion(notificationMergeFields.getVersion());
                }

                return existingNotificationMergeFields;
            })
            .map(notificationMergeFieldsRepository::save);
    }

    /**
     * Get all the notificationMergeFields.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NotificationMergeFields> findAll(Pageable pageable) {
        log.debug("Request to get all NotificationMergeFields");
        return notificationMergeFieldsRepository.findAll(pageable);
    }

    /**
     * Get one notificationMergeFields by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NotificationMergeFields> findOne(Long id) {
        log.debug("Request to get NotificationMergeFields : {}", id);
        return notificationMergeFieldsRepository.findById(id);
    }

    /**
     * Delete the notificationMergeFields by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete NotificationMergeFields : {}", id);
        notificationMergeFieldsRepository.deleteById(id);
    }
}
