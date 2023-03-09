package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.NotificationTemplates;
import com.venturedive.vendian_mono.repository.NotificationTemplatesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NotificationTemplates}.
 */
@Service
@Transactional
public class NotificationTemplatesService {

    private final Logger log = LoggerFactory.getLogger(NotificationTemplatesService.class);

    private final NotificationTemplatesRepository notificationTemplatesRepository;

    public NotificationTemplatesService(NotificationTemplatesRepository notificationTemplatesRepository) {
        this.notificationTemplatesRepository = notificationTemplatesRepository;
    }

    /**
     * Save a notificationTemplates.
     *
     * @param notificationTemplates the entity to save.
     * @return the persisted entity.
     */
    public NotificationTemplates save(NotificationTemplates notificationTemplates) {
        log.debug("Request to save NotificationTemplates : {}", notificationTemplates);
        return notificationTemplatesRepository.save(notificationTemplates);
    }

    /**
     * Update a notificationTemplates.
     *
     * @param notificationTemplates the entity to save.
     * @return the persisted entity.
     */
    public NotificationTemplates update(NotificationTemplates notificationTemplates) {
        log.debug("Request to update NotificationTemplates : {}", notificationTemplates);
        return notificationTemplatesRepository.save(notificationTemplates);
    }

    /**
     * Partially update a notificationTemplates.
     *
     * @param notificationTemplates the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NotificationTemplates> partialUpdate(NotificationTemplates notificationTemplates) {
        log.debug("Request to partially update NotificationTemplates : {}", notificationTemplates);

        return notificationTemplatesRepository
            .findById(notificationTemplates.getId())
            .map(existingNotificationTemplates -> {
                if (notificationTemplates.getName() != null) {
                    existingNotificationTemplates.setName(notificationTemplates.getName());
                }
                if (notificationTemplates.getType() != null) {
                    existingNotificationTemplates.setType(notificationTemplates.getType());
                }
                if (notificationTemplates.getSubject() != null) {
                    existingNotificationTemplates.setSubject(notificationTemplates.getSubject());
                }
                if (notificationTemplates.getTemplate() != null) {
                    existingNotificationTemplates.setTemplate(notificationTemplates.getTemplate());
                }
                if (notificationTemplates.getTemplateContentType() != null) {
                    existingNotificationTemplates.setTemplateContentType(notificationTemplates.getTemplateContentType());
                }
                if (notificationTemplates.getEffDate() != null) {
                    existingNotificationTemplates.setEffDate(notificationTemplates.getEffDate());
                }
                if (notificationTemplates.getCreatedAt() != null) {
                    existingNotificationTemplates.setCreatedAt(notificationTemplates.getCreatedAt());
                }
                if (notificationTemplates.getUpdatedAt() != null) {
                    existingNotificationTemplates.setUpdatedAt(notificationTemplates.getUpdatedAt());
                }
                if (notificationTemplates.getEndDate() != null) {
                    existingNotificationTemplates.setEndDate(notificationTemplates.getEndDate());
                }
                if (notificationTemplates.getVersion() != null) {
                    existingNotificationTemplates.setVersion(notificationTemplates.getVersion());
                }

                return existingNotificationTemplates;
            })
            .map(notificationTemplatesRepository::save);
    }

    /**
     * Get all the notificationTemplates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NotificationTemplates> findAll(Pageable pageable) {
        log.debug("Request to get all NotificationTemplates");
        return notificationTemplatesRepository.findAll(pageable);
    }

    /**
     * Get one notificationTemplates by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NotificationTemplates> findOne(Long id) {
        log.debug("Request to get NotificationTemplates : {}", id);
        return notificationTemplatesRepository.findById(id);
    }

    /**
     * Delete the notificationTemplates by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete NotificationTemplates : {}", id);
        notificationTemplatesRepository.deleteById(id);
    }
}
