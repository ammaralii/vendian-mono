package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.Designations;
import com.venturedive.vendian_mono.repository.DesignationsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Designations}.
 */
@Service
@Transactional
public class DesignationsService {

    private final Logger log = LoggerFactory.getLogger(DesignationsService.class);

    private final DesignationsRepository designationsRepository;

    public DesignationsService(DesignationsRepository designationsRepository) {
        this.designationsRepository = designationsRepository;
    }

    /**
     * Save a designations.
     *
     * @param designations the entity to save.
     * @return the persisted entity.
     */
    public Designations save(Designations designations) {
        log.debug("Request to save Designations : {}", designations);
        return designationsRepository.save(designations);
    }

    /**
     * Update a designations.
     *
     * @param designations the entity to save.
     * @return the persisted entity.
     */
    public Designations update(Designations designations) {
        log.debug("Request to update Designations : {}", designations);
        return designationsRepository.save(designations);
    }

    /**
     * Partially update a designations.
     *
     * @param designations the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Designations> partialUpdate(Designations designations) {
        log.debug("Request to partially update Designations : {}", designations);

        return designationsRepository
            .findById(designations.getId())
            .map(existingDesignations -> {
                if (designations.getName() != null) {
                    existingDesignations.setName(designations.getName());
                }
                if (designations.getCreatedat() != null) {
                    existingDesignations.setCreatedat(designations.getCreatedat());
                }
                if (designations.getUpdatedat() != null) {
                    existingDesignations.setUpdatedat(designations.getUpdatedat());
                }
                if (designations.getDeletedat() != null) {
                    existingDesignations.setDeletedat(designations.getDeletedat());
                }

                return existingDesignations;
            })
            .map(designationsRepository::save);
    }

    /**
     * Get all the designations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Designations> findAll(Pageable pageable) {
        log.debug("Request to get all Designations");
        return designationsRepository.findAll(pageable);
    }

    /**
     * Get one designations by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Designations> findOne(Long id) {
        log.debug("Request to get Designations : {}", id);
        return designationsRepository.findById(id);
    }

    /**
     * Delete the designations by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Designations : {}", id);
        designationsRepository.deleteById(id);
    }
}
