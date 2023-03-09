package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.DealResourceSkills;
import com.venturedive.vendian_mono.repository.DealResourceSkillsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DealResourceSkills}.
 */
@Service
@Transactional
public class DealResourceSkillsService {

    private final Logger log = LoggerFactory.getLogger(DealResourceSkillsService.class);

    private final DealResourceSkillsRepository dealResourceSkillsRepository;

    public DealResourceSkillsService(DealResourceSkillsRepository dealResourceSkillsRepository) {
        this.dealResourceSkillsRepository = dealResourceSkillsRepository;
    }

    /**
     * Save a dealResourceSkills.
     *
     * @param dealResourceSkills the entity to save.
     * @return the persisted entity.
     */
    public DealResourceSkills save(DealResourceSkills dealResourceSkills) {
        log.debug("Request to save DealResourceSkills : {}", dealResourceSkills);
        return dealResourceSkillsRepository.save(dealResourceSkills);
    }

    /**
     * Update a dealResourceSkills.
     *
     * @param dealResourceSkills the entity to save.
     * @return the persisted entity.
     */
    public DealResourceSkills update(DealResourceSkills dealResourceSkills) {
        log.debug("Request to update DealResourceSkills : {}", dealResourceSkills);
        return dealResourceSkillsRepository.save(dealResourceSkills);
    }

    /**
     * Partially update a dealResourceSkills.
     *
     * @param dealResourceSkills the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DealResourceSkills> partialUpdate(DealResourceSkills dealResourceSkills) {
        log.debug("Request to partially update DealResourceSkills : {}", dealResourceSkills);

        return dealResourceSkillsRepository
            .findById(dealResourceSkills.getId())
            .map(existingDealResourceSkills -> {
                if (dealResourceSkills.getCreatedat() != null) {
                    existingDealResourceSkills.setCreatedat(dealResourceSkills.getCreatedat());
                }
                if (dealResourceSkills.getUpdatedat() != null) {
                    existingDealResourceSkills.setUpdatedat(dealResourceSkills.getUpdatedat());
                }
                if (dealResourceSkills.getDeletedat() != null) {
                    existingDealResourceSkills.setDeletedat(dealResourceSkills.getDeletedat());
                }

                return existingDealResourceSkills;
            })
            .map(dealResourceSkillsRepository::save);
    }

    /**
     * Get all the dealResourceSkills.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DealResourceSkills> findAll(Pageable pageable) {
        log.debug("Request to get all DealResourceSkills");
        return dealResourceSkillsRepository.findAll(pageable);
    }

    /**
     * Get one dealResourceSkills by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DealResourceSkills> findOne(Long id) {
        log.debug("Request to get DealResourceSkills : {}", id);
        return dealResourceSkillsRepository.findById(id);
    }

    /**
     * Delete the dealResourceSkills by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DealResourceSkills : {}", id);
        dealResourceSkillsRepository.deleteById(id);
    }
}
