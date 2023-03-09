package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.PerformanceCycles20190826;
import com.venturedive.vendian_mono.repository.PerformanceCycles20190826Repository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PerformanceCycles20190826}.
 */
@Service
@Transactional
public class PerformanceCycles20190826Service {

    private final Logger log = LoggerFactory.getLogger(PerformanceCycles20190826Service.class);

    private final PerformanceCycles20190826Repository performanceCycles20190826Repository;

    public PerformanceCycles20190826Service(PerformanceCycles20190826Repository performanceCycles20190826Repository) {
        this.performanceCycles20190826Repository = performanceCycles20190826Repository;
    }

    /**
     * Save a performanceCycles20190826.
     *
     * @param performanceCycles20190826 the entity to save.
     * @return the persisted entity.
     */
    public PerformanceCycles20190826 save(PerformanceCycles20190826 performanceCycles20190826) {
        log.debug("Request to save PerformanceCycles20190826 : {}", performanceCycles20190826);
        return performanceCycles20190826Repository.save(performanceCycles20190826);
    }

    /**
     * Update a performanceCycles20190826.
     *
     * @param performanceCycles20190826 the entity to save.
     * @return the persisted entity.
     */
    public PerformanceCycles20190826 update(PerformanceCycles20190826 performanceCycles20190826) {
        log.debug("Request to update PerformanceCycles20190826 : {}", performanceCycles20190826);
        return performanceCycles20190826Repository.save(performanceCycles20190826);
    }

    /**
     * Partially update a performanceCycles20190826.
     *
     * @param performanceCycles20190826 the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PerformanceCycles20190826> partialUpdate(PerformanceCycles20190826 performanceCycles20190826) {
        log.debug("Request to partially update PerformanceCycles20190826 : {}", performanceCycles20190826);

        return performanceCycles20190826Repository
            .findById(performanceCycles20190826.getId())
            .map(existingPerformanceCycles20190826 -> {
                if (performanceCycles20190826.getMonth() != null) {
                    existingPerformanceCycles20190826.setMonth(performanceCycles20190826.getMonth());
                }
                if (performanceCycles20190826.getYear() != null) {
                    existingPerformanceCycles20190826.setYear(performanceCycles20190826.getYear());
                }
                if (performanceCycles20190826.getTotalresources() != null) {
                    existingPerformanceCycles20190826.setTotalresources(performanceCycles20190826.getTotalresources());
                }
                if (performanceCycles20190826.getPmreviewed() != null) {
                    existingPerformanceCycles20190826.setPmreviewed(performanceCycles20190826.getPmreviewed());
                }
                if (performanceCycles20190826.getArchreviewed() != null) {
                    existingPerformanceCycles20190826.setArchreviewed(performanceCycles20190826.getArchreviewed());
                }
                if (performanceCycles20190826.getStartdate() != null) {
                    existingPerformanceCycles20190826.setStartdate(performanceCycles20190826.getStartdate());
                }
                if (performanceCycles20190826.getEnddate() != null) {
                    existingPerformanceCycles20190826.setEnddate(performanceCycles20190826.getEnddate());
                }
                if (performanceCycles20190826.getIsactive() != null) {
                    existingPerformanceCycles20190826.setIsactive(performanceCycles20190826.getIsactive());
                }
                if (performanceCycles20190826.getCreatedat() != null) {
                    existingPerformanceCycles20190826.setCreatedat(performanceCycles20190826.getCreatedat());
                }
                if (performanceCycles20190826.getUpdatedat() != null) {
                    existingPerformanceCycles20190826.setUpdatedat(performanceCycles20190826.getUpdatedat());
                }
                if (performanceCycles20190826.getProjectcount() != null) {
                    existingPerformanceCycles20190826.setProjectcount(performanceCycles20190826.getProjectcount());
                }
                if (performanceCycles20190826.getCriteria() != null) {
                    existingPerformanceCycles20190826.setCriteria(performanceCycles20190826.getCriteria());
                }
                if (performanceCycles20190826.getNotificationsent() != null) {
                    existingPerformanceCycles20190826.setNotificationsent(performanceCycles20190826.getNotificationsent());
                }
                if (performanceCycles20190826.getDuedate() != null) {
                    existingPerformanceCycles20190826.setDuedate(performanceCycles20190826.getDuedate());
                }
                if (performanceCycles20190826.getInitiationdate() != null) {
                    existingPerformanceCycles20190826.setInitiationdate(performanceCycles20190826.getInitiationdate());
                }

                return existingPerformanceCycles20190826;
            })
            .map(performanceCycles20190826Repository::save);
    }

    /**
     * Get all the performanceCycles20190826s.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PerformanceCycles20190826> findAll(Pageable pageable) {
        log.debug("Request to get all PerformanceCycles20190826s");
        return performanceCycles20190826Repository.findAll(pageable);
    }

    /**
     * Get one performanceCycles20190826 by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PerformanceCycles20190826> findOne(Long id) {
        log.debug("Request to get PerformanceCycles20190826 : {}", id);
        return performanceCycles20190826Repository.findById(id);
    }

    /**
     * Delete the performanceCycles20190826 by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PerformanceCycles20190826 : {}", id);
        performanceCycles20190826Repository.deleteById(id);
    }
}
