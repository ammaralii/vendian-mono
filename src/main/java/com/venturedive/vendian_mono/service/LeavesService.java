package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.Leaves;
import com.venturedive.vendian_mono.repository.LeavesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Leaves}.
 */
@Service
@Transactional
public class LeavesService {

    private final Logger log = LoggerFactory.getLogger(LeavesService.class);

    private final LeavesRepository leavesRepository;

    public LeavesService(LeavesRepository leavesRepository) {
        this.leavesRepository = leavesRepository;
    }

    /**
     * Save a leaves.
     *
     * @param leaves the entity to save.
     * @return the persisted entity.
     */
    public Leaves save(Leaves leaves) {
        log.debug("Request to save Leaves : {}", leaves);
        return leavesRepository.save(leaves);
    }

    /**
     * Update a leaves.
     *
     * @param leaves the entity to save.
     * @return the persisted entity.
     */
    public Leaves update(Leaves leaves) {
        log.debug("Request to update Leaves : {}", leaves);
        return leavesRepository.save(leaves);
    }

    /**
     * Partially update a leaves.
     *
     * @param leaves the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Leaves> partialUpdate(Leaves leaves) {
        log.debug("Request to partially update Leaves : {}", leaves);

        return leavesRepository
            .findById(leaves.getId())
            .map(existingLeaves -> {
                if (leaves.getEffDate() != null) {
                    existingLeaves.setEffDate(leaves.getEffDate());
                }
                if (leaves.getCreatedAt() != null) {
                    existingLeaves.setCreatedAt(leaves.getCreatedAt());
                }
                if (leaves.getUpdatedAt() != null) {
                    existingLeaves.setUpdatedAt(leaves.getUpdatedAt());
                }
                if (leaves.getEndDate() != null) {
                    existingLeaves.setEndDate(leaves.getEndDate());
                }
                if (leaves.getVersion() != null) {
                    existingLeaves.setVersion(leaves.getVersion());
                }

                return existingLeaves;
            })
            .map(leavesRepository::save);
    }

    /**
     * Get all the leaves.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Leaves> findAll(Pageable pageable) {
        log.debug("Request to get all Leaves");
        return leavesRepository.findAll(pageable);
    }

    /**
     * Get one leaves by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Leaves> findOne(Long id) {
        log.debug("Request to get Leaves : {}", id);
        return leavesRepository.findById(id);
    }

    /**
     * Delete the leaves by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Leaves : {}", id);
        leavesRepository.deleteById(id);
    }
}
