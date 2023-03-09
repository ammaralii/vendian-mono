package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.PcStatuses;
import com.venturedive.vendian_mono.repository.PcStatusesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PcStatuses}.
 */
@Service
@Transactional
public class PcStatusesService {

    private final Logger log = LoggerFactory.getLogger(PcStatusesService.class);

    private final PcStatusesRepository pcStatusesRepository;

    public PcStatusesService(PcStatusesRepository pcStatusesRepository) {
        this.pcStatusesRepository = pcStatusesRepository;
    }

    /**
     * Save a pcStatuses.
     *
     * @param pcStatuses the entity to save.
     * @return the persisted entity.
     */
    public PcStatuses save(PcStatuses pcStatuses) {
        log.debug("Request to save PcStatuses : {}", pcStatuses);
        return pcStatusesRepository.save(pcStatuses);
    }

    /**
     * Update a pcStatuses.
     *
     * @param pcStatuses the entity to save.
     * @return the persisted entity.
     */
    public PcStatuses update(PcStatuses pcStatuses) {
        log.debug("Request to update PcStatuses : {}", pcStatuses);
        return pcStatusesRepository.save(pcStatuses);
    }

    /**
     * Partially update a pcStatuses.
     *
     * @param pcStatuses the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PcStatuses> partialUpdate(PcStatuses pcStatuses) {
        log.debug("Request to partially update PcStatuses : {}", pcStatuses);

        return pcStatusesRepository
            .findById(pcStatuses.getId())
            .map(existingPcStatuses -> {
                if (pcStatuses.getName() != null) {
                    existingPcStatuses.setName(pcStatuses.getName());
                }
                if (pcStatuses.getGroup() != null) {
                    existingPcStatuses.setGroup(pcStatuses.getGroup());
                }
                if (pcStatuses.getSystemKey() != null) {
                    existingPcStatuses.setSystemKey(pcStatuses.getSystemKey());
                }
                if (pcStatuses.getCreatedAt() != null) {
                    existingPcStatuses.setCreatedAt(pcStatuses.getCreatedAt());
                }
                if (pcStatuses.getUpdatedAt() != null) {
                    existingPcStatuses.setUpdatedAt(pcStatuses.getUpdatedAt());
                }
                if (pcStatuses.getDeletedAt() != null) {
                    existingPcStatuses.setDeletedAt(pcStatuses.getDeletedAt());
                }
                if (pcStatuses.getVersion() != null) {
                    existingPcStatuses.setVersion(pcStatuses.getVersion());
                }

                return existingPcStatuses;
            })
            .map(pcStatusesRepository::save);
    }

    /**
     * Get all the pcStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PcStatuses> findAll(Pageable pageable) {
        log.debug("Request to get all PcStatuses");
        return pcStatusesRepository.findAll(pageable);
    }

    /**
     * Get one pcStatuses by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PcStatuses> findOne(Long id) {
        log.debug("Request to get PcStatuses : {}", id);
        return pcStatusesRepository.findById(id);
    }

    /**
     * Delete the pcStatuses by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PcStatuses : {}", id);
        pcStatusesRepository.deleteById(id);
    }
}
