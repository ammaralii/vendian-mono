package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.BloodGroups;
import com.venturedive.vendian_mono.repository.BloodGroupsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BloodGroups}.
 */
@Service
@Transactional
public class BloodGroupsService {

    private final Logger log = LoggerFactory.getLogger(BloodGroupsService.class);

    private final BloodGroupsRepository bloodGroupsRepository;

    public BloodGroupsService(BloodGroupsRepository bloodGroupsRepository) {
        this.bloodGroupsRepository = bloodGroupsRepository;
    }

    /**
     * Save a bloodGroups.
     *
     * @param bloodGroups the entity to save.
     * @return the persisted entity.
     */
    public BloodGroups save(BloodGroups bloodGroups) {
        log.debug("Request to save BloodGroups : {}", bloodGroups);
        return bloodGroupsRepository.save(bloodGroups);
    }

    /**
     * Update a bloodGroups.
     *
     * @param bloodGroups the entity to save.
     * @return the persisted entity.
     */
    public BloodGroups update(BloodGroups bloodGroups) {
        log.debug("Request to update BloodGroups : {}", bloodGroups);
        return bloodGroupsRepository.save(bloodGroups);
    }

    /**
     * Partially update a bloodGroups.
     *
     * @param bloodGroups the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BloodGroups> partialUpdate(BloodGroups bloodGroups) {
        log.debug("Request to partially update BloodGroups : {}", bloodGroups);

        return bloodGroupsRepository
            .findById(bloodGroups.getId())
            .map(existingBloodGroups -> {
                if (bloodGroups.getName() != null) {
                    existingBloodGroups.setName(bloodGroups.getName());
                }
                if (bloodGroups.getCreatedat() != null) {
                    existingBloodGroups.setCreatedat(bloodGroups.getCreatedat());
                }
                if (bloodGroups.getUpdatedat() != null) {
                    existingBloodGroups.setUpdatedat(bloodGroups.getUpdatedat());
                }

                return existingBloodGroups;
            })
            .map(bloodGroupsRepository::save);
    }

    /**
     * Get all the bloodGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BloodGroups> findAll(Pageable pageable) {
        log.debug("Request to get all BloodGroups");
        return bloodGroupsRepository.findAll(pageable);
    }

    /**
     * Get one bloodGroups by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BloodGroups> findOne(Long id) {
        log.debug("Request to get BloodGroups : {}", id);
        return bloodGroupsRepository.findById(id);
    }

    /**
     * Delete the bloodGroups by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BloodGroups : {}", id);
        bloodGroupsRepository.deleteById(id);
    }
}
