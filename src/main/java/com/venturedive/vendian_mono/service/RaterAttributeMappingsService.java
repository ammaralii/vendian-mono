package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.RaterAttributeMappings;
import com.venturedive.vendian_mono.repository.RaterAttributeMappingsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RaterAttributeMappings}.
 */
@Service
@Transactional
public class RaterAttributeMappingsService {

    private final Logger log = LoggerFactory.getLogger(RaterAttributeMappingsService.class);

    private final RaterAttributeMappingsRepository raterAttributeMappingsRepository;

    public RaterAttributeMappingsService(RaterAttributeMappingsRepository raterAttributeMappingsRepository) {
        this.raterAttributeMappingsRepository = raterAttributeMappingsRepository;
    }

    /**
     * Save a raterAttributeMappings.
     *
     * @param raterAttributeMappings the entity to save.
     * @return the persisted entity.
     */
    public RaterAttributeMappings save(RaterAttributeMappings raterAttributeMappings) {
        log.debug("Request to save RaterAttributeMappings : {}", raterAttributeMappings);
        return raterAttributeMappingsRepository.save(raterAttributeMappings);
    }

    /**
     * Update a raterAttributeMappings.
     *
     * @param raterAttributeMappings the entity to save.
     * @return the persisted entity.
     */
    public RaterAttributeMappings update(RaterAttributeMappings raterAttributeMappings) {
        log.debug("Request to update RaterAttributeMappings : {}", raterAttributeMappings);
        return raterAttributeMappingsRepository.save(raterAttributeMappings);
    }

    /**
     * Partially update a raterAttributeMappings.
     *
     * @param raterAttributeMappings the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RaterAttributeMappings> partialUpdate(RaterAttributeMappings raterAttributeMappings) {
        log.debug("Request to partially update RaterAttributeMappings : {}", raterAttributeMappings);

        return raterAttributeMappingsRepository
            .findById(raterAttributeMappings.getId())
            .map(existingRaterAttributeMappings -> {
                if (raterAttributeMappings.getEffdate() != null) {
                    existingRaterAttributeMappings.setEffdate(raterAttributeMappings.getEffdate());
                }
                if (raterAttributeMappings.getEnddate() != null) {
                    existingRaterAttributeMappings.setEnddate(raterAttributeMappings.getEnddate());
                }
                if (raterAttributeMappings.getCreatedat() != null) {
                    existingRaterAttributeMappings.setCreatedat(raterAttributeMappings.getCreatedat());
                }
                if (raterAttributeMappings.getUpdatedat() != null) {
                    existingRaterAttributeMappings.setUpdatedat(raterAttributeMappings.getUpdatedat());
                }

                return existingRaterAttributeMappings;
            })
            .map(raterAttributeMappingsRepository::save);
    }

    /**
     * Get all the raterAttributeMappings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RaterAttributeMappings> findAll(Pageable pageable) {
        log.debug("Request to get all RaterAttributeMappings");
        return raterAttributeMappingsRepository.findAll(pageable);
    }

    /**
     * Get one raterAttributeMappings by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RaterAttributeMappings> findOne(Long id) {
        log.debug("Request to get RaterAttributeMappings : {}", id);
        return raterAttributeMappingsRepository.findById(id);
    }

    /**
     * Delete the raterAttributeMappings by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RaterAttributeMappings : {}", id);
        raterAttributeMappingsRepository.deleteById(id);
    }
}
