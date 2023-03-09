package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.QualificationTypes;
import com.venturedive.vendian_mono.repository.QualificationTypesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link QualificationTypes}.
 */
@Service
@Transactional
public class QualificationTypesService {

    private final Logger log = LoggerFactory.getLogger(QualificationTypesService.class);

    private final QualificationTypesRepository qualificationTypesRepository;

    public QualificationTypesService(QualificationTypesRepository qualificationTypesRepository) {
        this.qualificationTypesRepository = qualificationTypesRepository;
    }

    /**
     * Save a qualificationTypes.
     *
     * @param qualificationTypes the entity to save.
     * @return the persisted entity.
     */
    public QualificationTypes save(QualificationTypes qualificationTypes) {
        log.debug("Request to save QualificationTypes : {}", qualificationTypes);
        return qualificationTypesRepository.save(qualificationTypes);
    }

    /**
     * Update a qualificationTypes.
     *
     * @param qualificationTypes the entity to save.
     * @return the persisted entity.
     */
    public QualificationTypes update(QualificationTypes qualificationTypes) {
        log.debug("Request to update QualificationTypes : {}", qualificationTypes);
        return qualificationTypesRepository.save(qualificationTypes);
    }

    /**
     * Partially update a qualificationTypes.
     *
     * @param qualificationTypes the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<QualificationTypes> partialUpdate(QualificationTypes qualificationTypes) {
        log.debug("Request to partially update QualificationTypes : {}", qualificationTypes);

        return qualificationTypesRepository
            .findById(qualificationTypes.getId())
            .map(existingQualificationTypes -> {
                if (qualificationTypes.getName() != null) {
                    existingQualificationTypes.setName(qualificationTypes.getName());
                }
                if (qualificationTypes.getCreatedat() != null) {
                    existingQualificationTypes.setCreatedat(qualificationTypes.getCreatedat());
                }
                if (qualificationTypes.getUpdatedat() != null) {
                    existingQualificationTypes.setUpdatedat(qualificationTypes.getUpdatedat());
                }

                return existingQualificationTypes;
            })
            .map(qualificationTypesRepository::save);
    }

    /**
     * Get all the qualificationTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<QualificationTypes> findAll(Pageable pageable) {
        log.debug("Request to get all QualificationTypes");
        return qualificationTypesRepository.findAll(pageable);
    }

    /**
     * Get one qualificationTypes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<QualificationTypes> findOne(Long id) {
        log.debug("Request to get QualificationTypes : {}", id);
        return qualificationTypesRepository.findById(id);
    }

    /**
     * Delete the qualificationTypes by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete QualificationTypes : {}", id);
        qualificationTypesRepository.deleteById(id);
    }
}
