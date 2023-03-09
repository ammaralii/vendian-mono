package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.PerformanceCycles;
import com.venturedive.vendian_mono.repository.PerformanceCyclesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PerformanceCycles}.
 */
@Service
@Transactional
public class PerformanceCyclesService {

    private final Logger log = LoggerFactory.getLogger(PerformanceCyclesService.class);

    private final PerformanceCyclesRepository performanceCyclesRepository;

    public PerformanceCyclesService(PerformanceCyclesRepository performanceCyclesRepository) {
        this.performanceCyclesRepository = performanceCyclesRepository;
    }

    /**
     * Save a performanceCycles.
     *
     * @param performanceCycles the entity to save.
     * @return the persisted entity.
     */
    public PerformanceCycles save(PerformanceCycles performanceCycles) {
        log.debug("Request to save PerformanceCycles : {}", performanceCycles);
        return performanceCyclesRepository.save(performanceCycles);
    }

    /**
     * Update a performanceCycles.
     *
     * @param performanceCycles the entity to save.
     * @return the persisted entity.
     */
    public PerformanceCycles update(PerformanceCycles performanceCycles) {
        log.debug("Request to update PerformanceCycles : {}", performanceCycles);
        return performanceCyclesRepository.save(performanceCycles);
    }

    /**
     * Partially update a performanceCycles.
     *
     * @param performanceCycles the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PerformanceCycles> partialUpdate(PerformanceCycles performanceCycles) {
        log.debug("Request to partially update PerformanceCycles : {}", performanceCycles);

        return performanceCyclesRepository
            .findById(performanceCycles.getId())
            .map(existingPerformanceCycles -> {
                if (performanceCycles.getMonth() != null) {
                    existingPerformanceCycles.setMonth(performanceCycles.getMonth());
                }
                if (performanceCycles.getYear() != null) {
                    existingPerformanceCycles.setYear(performanceCycles.getYear());
                }
                if (performanceCycles.getTotalresources() != null) {
                    existingPerformanceCycles.setTotalresources(performanceCycles.getTotalresources());
                }
                if (performanceCycles.getPmreviewed() != null) {
                    existingPerformanceCycles.setPmreviewed(performanceCycles.getPmreviewed());
                }
                if (performanceCycles.getArchreviewed() != null) {
                    existingPerformanceCycles.setArchreviewed(performanceCycles.getArchreviewed());
                }
                if (performanceCycles.getStartdate() != null) {
                    existingPerformanceCycles.setStartdate(performanceCycles.getStartdate());
                }
                if (performanceCycles.getEnddate() != null) {
                    existingPerformanceCycles.setEnddate(performanceCycles.getEnddate());
                }
                if (performanceCycles.getIsactive() != null) {
                    existingPerformanceCycles.setIsactive(performanceCycles.getIsactive());
                }
                if (performanceCycles.getCreatedat() != null) {
                    existingPerformanceCycles.setCreatedat(performanceCycles.getCreatedat());
                }
                if (performanceCycles.getUpdatedat() != null) {
                    existingPerformanceCycles.setUpdatedat(performanceCycles.getUpdatedat());
                }
                if (performanceCycles.getProjectcount() != null) {
                    existingPerformanceCycles.setProjectcount(performanceCycles.getProjectcount());
                }
                if (performanceCycles.getCriteria() != null) {
                    existingPerformanceCycles.setCriteria(performanceCycles.getCriteria());
                }
                if (performanceCycles.getNotificationsent() != null) {
                    existingPerformanceCycles.setNotificationsent(performanceCycles.getNotificationsent());
                }
                if (performanceCycles.getDuedate() != null) {
                    existingPerformanceCycles.setDuedate(performanceCycles.getDuedate());
                }
                if (performanceCycles.getInitiationdate() != null) {
                    existingPerformanceCycles.setInitiationdate(performanceCycles.getInitiationdate());
                }

                return existingPerformanceCycles;
            })
            .map(performanceCyclesRepository::save);
    }

    /**
     * Get all the performanceCycles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PerformanceCycles> findAll(Pageable pageable) {
        log.debug("Request to get all PerformanceCycles");
        return performanceCyclesRepository.findAll(pageable);
    }

    /**
     * Get all the performanceCycles with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<PerformanceCycles> findAllWithEagerRelationships(Pageable pageable) {
        return performanceCyclesRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one performanceCycles by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PerformanceCycles> findOne(Long id) {
        log.debug("Request to get PerformanceCycles : {}", id);
        return performanceCyclesRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the performanceCycles by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PerformanceCycles : {}", id);
        performanceCyclesRepository.deleteById(id);
    }
}
