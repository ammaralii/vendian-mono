package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.Reasons;
import com.venturedive.vendian_mono.repository.ReasonsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Reasons}.
 */
@Service
@Transactional
public class ReasonsService {

    private final Logger log = LoggerFactory.getLogger(ReasonsService.class);

    private final ReasonsRepository reasonsRepository;

    public ReasonsService(ReasonsRepository reasonsRepository) {
        this.reasonsRepository = reasonsRepository;
    }

    /**
     * Save a reasons.
     *
     * @param reasons the entity to save.
     * @return the persisted entity.
     */
    public Reasons save(Reasons reasons) {
        log.debug("Request to save Reasons : {}", reasons);
        return reasonsRepository.save(reasons);
    }

    /**
     * Update a reasons.
     *
     * @param reasons the entity to save.
     * @return the persisted entity.
     */
    public Reasons update(Reasons reasons) {
        log.debug("Request to update Reasons : {}", reasons);
        return reasonsRepository.save(reasons);
    }

    /**
     * Partially update a reasons.
     *
     * @param reasons the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Reasons> partialUpdate(Reasons reasons) {
        log.debug("Request to partially update Reasons : {}", reasons);

        return reasonsRepository
            .findById(reasons.getId())
            .map(existingReasons -> {
                if (reasons.getName() != null) {
                    existingReasons.setName(reasons.getName());
                }
                if (reasons.getIsdefault() != null) {
                    existingReasons.setIsdefault(reasons.getIsdefault());
                }
                if (reasons.getCreatedat() != null) {
                    existingReasons.setCreatedat(reasons.getCreatedat());
                }
                if (reasons.getUpdatedat() != null) {
                    existingReasons.setUpdatedat(reasons.getUpdatedat());
                }

                return existingReasons;
            })
            .map(reasonsRepository::save);
    }

    /**
     * Get all the reasons.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Reasons> findAll(Pageable pageable) {
        log.debug("Request to get all Reasons");
        return reasonsRepository.findAll(pageable);
    }

    /**
     * Get one reasons by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Reasons> findOne(Long id) {
        log.debug("Request to get Reasons : {}", id);
        return reasonsRepository.findById(id);
    }

    /**
     * Delete the reasons by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Reasons : {}", id);
        reasonsRepository.deleteById(id);
    }
}
