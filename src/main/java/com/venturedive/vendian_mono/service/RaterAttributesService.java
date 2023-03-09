package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.RaterAttributes;
import com.venturedive.vendian_mono.repository.RaterAttributesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RaterAttributes}.
 */
@Service
@Transactional
public class RaterAttributesService {

    private final Logger log = LoggerFactory.getLogger(RaterAttributesService.class);

    private final RaterAttributesRepository raterAttributesRepository;

    public RaterAttributesService(RaterAttributesRepository raterAttributesRepository) {
        this.raterAttributesRepository = raterAttributesRepository;
    }

    /**
     * Save a raterAttributes.
     *
     * @param raterAttributes the entity to save.
     * @return the persisted entity.
     */
    public RaterAttributes save(RaterAttributes raterAttributes) {
        log.debug("Request to save RaterAttributes : {}", raterAttributes);
        return raterAttributesRepository.save(raterAttributes);
    }

    /**
     * Update a raterAttributes.
     *
     * @param raterAttributes the entity to save.
     * @return the persisted entity.
     */
    public RaterAttributes update(RaterAttributes raterAttributes) {
        log.debug("Request to update RaterAttributes : {}", raterAttributes);
        return raterAttributesRepository.save(raterAttributes);
    }

    /**
     * Partially update a raterAttributes.
     *
     * @param raterAttributes the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RaterAttributes> partialUpdate(RaterAttributes raterAttributes) {
        log.debug("Request to partially update RaterAttributes : {}", raterAttributes);

        return raterAttributesRepository
            .findById(raterAttributes.getId())
            .map(existingRaterAttributes -> {
                if (raterAttributes.getName() != null) {
                    existingRaterAttributes.setName(raterAttributes.getName());
                }
                if (raterAttributes.getTitle() != null) {
                    existingRaterAttributes.setTitle(raterAttributes.getTitle());
                }
                if (raterAttributes.getDescription() != null) {
                    existingRaterAttributes.setDescription(raterAttributes.getDescription());
                }
                if (raterAttributes.getEffdate() != null) {
                    existingRaterAttributes.setEffdate(raterAttributes.getEffdate());
                }
                if (raterAttributes.getEnddate() != null) {
                    existingRaterAttributes.setEnddate(raterAttributes.getEnddate());
                }
                if (raterAttributes.getCreatedat() != null) {
                    existingRaterAttributes.setCreatedat(raterAttributes.getCreatedat());
                }
                if (raterAttributes.getUpdatedat() != null) {
                    existingRaterAttributes.setUpdatedat(raterAttributes.getUpdatedat());
                }

                return existingRaterAttributes;
            })
            .map(raterAttributesRepository::save);
    }

    /**
     * Get all the raterAttributes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RaterAttributes> findAll(Pageable pageable) {
        log.debug("Request to get all RaterAttributes");
        return raterAttributesRepository.findAll(pageable);
    }

    /**
     * Get one raterAttributes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RaterAttributes> findOne(Long id) {
        log.debug("Request to get RaterAttributes : {}", id);
        return raterAttributesRepository.findById(id);
    }

    /**
     * Delete the raterAttributes by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RaterAttributes : {}", id);
        raterAttributesRepository.deleteById(id);
    }
}
