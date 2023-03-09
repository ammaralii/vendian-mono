package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.DesignationJobDescriptions;
import com.venturedive.vendian_mono.repository.DesignationJobDescriptionsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DesignationJobDescriptions}.
 */
@Service
@Transactional
public class DesignationJobDescriptionsService {

    private final Logger log = LoggerFactory.getLogger(DesignationJobDescriptionsService.class);

    private final DesignationJobDescriptionsRepository designationJobDescriptionsRepository;

    public DesignationJobDescriptionsService(DesignationJobDescriptionsRepository designationJobDescriptionsRepository) {
        this.designationJobDescriptionsRepository = designationJobDescriptionsRepository;
    }

    /**
     * Save a designationJobDescriptions.
     *
     * @param designationJobDescriptions the entity to save.
     * @return the persisted entity.
     */
    public DesignationJobDescriptions save(DesignationJobDescriptions designationJobDescriptions) {
        log.debug("Request to save DesignationJobDescriptions : {}", designationJobDescriptions);
        return designationJobDescriptionsRepository.save(designationJobDescriptions);
    }

    /**
     * Update a designationJobDescriptions.
     *
     * @param designationJobDescriptions the entity to save.
     * @return the persisted entity.
     */
    public DesignationJobDescriptions update(DesignationJobDescriptions designationJobDescriptions) {
        log.debug("Request to update DesignationJobDescriptions : {}", designationJobDescriptions);
        return designationJobDescriptionsRepository.save(designationJobDescriptions);
    }

    /**
     * Partially update a designationJobDescriptions.
     *
     * @param designationJobDescriptions the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DesignationJobDescriptions> partialUpdate(DesignationJobDescriptions designationJobDescriptions) {
        log.debug("Request to partially update DesignationJobDescriptions : {}", designationJobDescriptions);

        return designationJobDescriptionsRepository
            .findById(designationJobDescriptions.getId())
            .map(existingDesignationJobDescriptions -> {
                if (designationJobDescriptions.getCreatedat() != null) {
                    existingDesignationJobDescriptions.setCreatedat(designationJobDescriptions.getCreatedat());
                }
                if (designationJobDescriptions.getUpdatedat() != null) {
                    existingDesignationJobDescriptions.setUpdatedat(designationJobDescriptions.getUpdatedat());
                }

                return existingDesignationJobDescriptions;
            })
            .map(designationJobDescriptionsRepository::save);
    }

    /**
     * Get all the designationJobDescriptions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DesignationJobDescriptions> findAll(Pageable pageable) {
        log.debug("Request to get all DesignationJobDescriptions");
        return designationJobDescriptionsRepository.findAll(pageable);
    }

    /**
     * Get one designationJobDescriptions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DesignationJobDescriptions> findOne(Long id) {
        log.debug("Request to get DesignationJobDescriptions : {}", id);
        return designationJobDescriptionsRepository.findById(id);
    }

    /**
     * Delete the designationJobDescriptions by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DesignationJobDescriptions : {}", id);
        designationJobDescriptionsRepository.deleteById(id);
    }
}
