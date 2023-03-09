package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.AuditLogs;
import com.venturedive.vendian_mono.repository.AuditLogsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AuditLogs}.
 */
@Service
@Transactional
public class AuditLogsService {

    private final Logger log = LoggerFactory.getLogger(AuditLogsService.class);

    private final AuditLogsRepository auditLogsRepository;

    public AuditLogsService(AuditLogsRepository auditLogsRepository) {
        this.auditLogsRepository = auditLogsRepository;
    }

    /**
     * Save a auditLogs.
     *
     * @param auditLogs the entity to save.
     * @return the persisted entity.
     */
    public AuditLogs save(AuditLogs auditLogs) {
        log.debug("Request to save AuditLogs : {}", auditLogs);
        return auditLogsRepository.save(auditLogs);
    }

    /**
     * Update a auditLogs.
     *
     * @param auditLogs the entity to save.
     * @return the persisted entity.
     */
    public AuditLogs update(AuditLogs auditLogs) {
        log.debug("Request to update AuditLogs : {}", auditLogs);
        return auditLogsRepository.save(auditLogs);
    }

    /**
     * Partially update a auditLogs.
     *
     * @param auditLogs the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AuditLogs> partialUpdate(AuditLogs auditLogs) {
        log.debug("Request to partially update AuditLogs : {}", auditLogs);

        return auditLogsRepository
            .findById(auditLogs.getId())
            .map(existingAuditLogs -> {
                if (auditLogs.getEvent() != null) {
                    existingAuditLogs.setEvent(auditLogs.getEvent());
                }
                if (auditLogs.getEventTime() != null) {
                    existingAuditLogs.setEventTime(auditLogs.getEventTime());
                }
                if (auditLogs.getDescription() != null) {
                    existingAuditLogs.setDescription(auditLogs.getDescription());
                }
                if (auditLogs.getOldChange() != null) {
                    existingAuditLogs.setOldChange(auditLogs.getOldChange());
                }
                if (auditLogs.getNewChange() != null) {
                    existingAuditLogs.setNewChange(auditLogs.getNewChange());
                }
                if (auditLogs.getCreatedAt() != null) {
                    existingAuditLogs.setCreatedAt(auditLogs.getCreatedAt());
                }
                if (auditLogs.getUpdatedAt() != null) {
                    existingAuditLogs.setUpdatedAt(auditLogs.getUpdatedAt());
                }
                if (auditLogs.getVersion() != null) {
                    existingAuditLogs.setVersion(auditLogs.getVersion());
                }

                return existingAuditLogs;
            })
            .map(auditLogsRepository::save);
    }

    /**
     * Get all the auditLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AuditLogs> findAll(Pageable pageable) {
        log.debug("Request to get all AuditLogs");
        return auditLogsRepository.findAll(pageable);
    }

    /**
     * Get one auditLogs by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AuditLogs> findOne(Long id) {
        log.debug("Request to get AuditLogs : {}", id);
        return auditLogsRepository.findById(id);
    }

    /**
     * Delete the auditLogs by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AuditLogs : {}", id);
        auditLogsRepository.deleteById(id);
    }
}
