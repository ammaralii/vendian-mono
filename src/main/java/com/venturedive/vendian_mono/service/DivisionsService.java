package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.Divisions;
import com.venturedive.vendian_mono.repository.DivisionsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Divisions}.
 */
@Service
@Transactional
public class DivisionsService {

    private final Logger log = LoggerFactory.getLogger(DivisionsService.class);

    private final DivisionsRepository divisionsRepository;

    public DivisionsService(DivisionsRepository divisionsRepository) {
        this.divisionsRepository = divisionsRepository;
    }

    /**
     * Save a divisions.
     *
     * @param divisions the entity to save.
     * @return the persisted entity.
     */
    public Divisions save(Divisions divisions) {
        log.debug("Request to save Divisions : {}", divisions);
        return divisionsRepository.save(divisions);
    }

    /**
     * Update a divisions.
     *
     * @param divisions the entity to save.
     * @return the persisted entity.
     */
    public Divisions update(Divisions divisions) {
        log.debug("Request to update Divisions : {}", divisions);
        return divisionsRepository.save(divisions);
    }

    /**
     * Partially update a divisions.
     *
     * @param divisions the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Divisions> partialUpdate(Divisions divisions) {
        log.debug("Request to partially update Divisions : {}", divisions);

        return divisionsRepository
            .findById(divisions.getId())
            .map(existingDivisions -> {
                if (divisions.getName() != null) {
                    existingDivisions.setName(divisions.getName());
                }
                if (divisions.getCreatedat() != null) {
                    existingDivisions.setCreatedat(divisions.getCreatedat());
                }
                if (divisions.getUpdatedat() != null) {
                    existingDivisions.setUpdatedat(divisions.getUpdatedat());
                }

                return existingDivisions;
            })
            .map(divisionsRepository::save);
    }

    /**
     * Get all the divisions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Divisions> findAll(Pageable pageable) {
        log.debug("Request to get all Divisions");
        return divisionsRepository.findAll(pageable);
    }

    /**
     * Get one divisions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Divisions> findOne(Long id) {
        log.debug("Request to get Divisions : {}", id);
        return divisionsRepository.findById(id);
    }

    /**
     * Delete the divisions by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Divisions : {}", id);
        divisionsRepository.deleteById(id);
    }
}
