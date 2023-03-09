package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.MaritalStatuses;
import com.venturedive.vendian_mono.repository.MaritalStatusesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MaritalStatuses}.
 */
@Service
@Transactional
public class MaritalStatusesService {

    private final Logger log = LoggerFactory.getLogger(MaritalStatusesService.class);

    private final MaritalStatusesRepository maritalStatusesRepository;

    public MaritalStatusesService(MaritalStatusesRepository maritalStatusesRepository) {
        this.maritalStatusesRepository = maritalStatusesRepository;
    }

    /**
     * Save a maritalStatuses.
     *
     * @param maritalStatuses the entity to save.
     * @return the persisted entity.
     */
    public MaritalStatuses save(MaritalStatuses maritalStatuses) {
        log.debug("Request to save MaritalStatuses : {}", maritalStatuses);
        return maritalStatusesRepository.save(maritalStatuses);
    }

    /**
     * Update a maritalStatuses.
     *
     * @param maritalStatuses the entity to save.
     * @return the persisted entity.
     */
    public MaritalStatuses update(MaritalStatuses maritalStatuses) {
        log.debug("Request to update MaritalStatuses : {}", maritalStatuses);
        return maritalStatusesRepository.save(maritalStatuses);
    }

    /**
     * Partially update a maritalStatuses.
     *
     * @param maritalStatuses the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MaritalStatuses> partialUpdate(MaritalStatuses maritalStatuses) {
        log.debug("Request to partially update MaritalStatuses : {}", maritalStatuses);

        return maritalStatusesRepository
            .findById(maritalStatuses.getId())
            .map(existingMaritalStatuses -> {
                if (maritalStatuses.getStatus() != null) {
                    existingMaritalStatuses.setStatus(maritalStatuses.getStatus());
                }
                if (maritalStatuses.getCreatedat() != null) {
                    existingMaritalStatuses.setCreatedat(maritalStatuses.getCreatedat());
                }
                if (maritalStatuses.getUpdatedat() != null) {
                    existingMaritalStatuses.setUpdatedat(maritalStatuses.getUpdatedat());
                }

                return existingMaritalStatuses;
            })
            .map(maritalStatusesRepository::save);
    }

    /**
     * Get all the maritalStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MaritalStatuses> findAll(Pageable pageable) {
        log.debug("Request to get all MaritalStatuses");
        return maritalStatusesRepository.findAll(pageable);
    }

    /**
     * Get one maritalStatuses by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MaritalStatuses> findOne(Long id) {
        log.debug("Request to get MaritalStatuses : {}", id);
        return maritalStatusesRepository.findById(id);
    }

    /**
     * Delete the maritalStatuses by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MaritalStatuses : {}", id);
        maritalStatusesRepository.deleteById(id);
    }
}
