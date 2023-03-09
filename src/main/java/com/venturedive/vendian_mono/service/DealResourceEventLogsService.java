package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.DealResourceEventLogs;
import com.venturedive.vendian_mono.repository.DealResourceEventLogsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DealResourceEventLogs}.
 */
@Service
@Transactional
public class DealResourceEventLogsService {

    private final Logger log = LoggerFactory.getLogger(DealResourceEventLogsService.class);

    private final DealResourceEventLogsRepository dealResourceEventLogsRepository;

    public DealResourceEventLogsService(DealResourceEventLogsRepository dealResourceEventLogsRepository) {
        this.dealResourceEventLogsRepository = dealResourceEventLogsRepository;
    }

    /**
     * Save a dealResourceEventLogs.
     *
     * @param dealResourceEventLogs the entity to save.
     * @return the persisted entity.
     */
    public DealResourceEventLogs save(DealResourceEventLogs dealResourceEventLogs) {
        log.debug("Request to save DealResourceEventLogs : {}", dealResourceEventLogs);
        return dealResourceEventLogsRepository.save(dealResourceEventLogs);
    }

    /**
     * Update a dealResourceEventLogs.
     *
     * @param dealResourceEventLogs the entity to save.
     * @return the persisted entity.
     */
    public DealResourceEventLogs update(DealResourceEventLogs dealResourceEventLogs) {
        log.debug("Request to update DealResourceEventLogs : {}", dealResourceEventLogs);
        return dealResourceEventLogsRepository.save(dealResourceEventLogs);
    }

    /**
     * Partially update a dealResourceEventLogs.
     *
     * @param dealResourceEventLogs the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DealResourceEventLogs> partialUpdate(DealResourceEventLogs dealResourceEventLogs) {
        log.debug("Request to partially update DealResourceEventLogs : {}", dealResourceEventLogs);

        return dealResourceEventLogsRepository
            .findById(dealResourceEventLogs.getId())
            .map(existingDealResourceEventLogs -> {
                if (dealResourceEventLogs.getComments() != null) {
                    existingDealResourceEventLogs.setComments(dealResourceEventLogs.getComments());
                }
                if (dealResourceEventLogs.getCreatedat() != null) {
                    existingDealResourceEventLogs.setCreatedat(dealResourceEventLogs.getCreatedat());
                }

                return existingDealResourceEventLogs;
            })
            .map(dealResourceEventLogsRepository::save);
    }

    /**
     * Get all the dealResourceEventLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DealResourceEventLogs> findAll(Pageable pageable) {
        log.debug("Request to get all DealResourceEventLogs");
        return dealResourceEventLogsRepository.findAll(pageable);
    }

    /**
     * Get all the dealResourceEventLogs with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<DealResourceEventLogs> findAllWithEagerRelationships(Pageable pageable) {
        return dealResourceEventLogsRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one dealResourceEventLogs by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DealResourceEventLogs> findOne(Long id) {
        log.debug("Request to get DealResourceEventLogs : {}", id);
        return dealResourceEventLogsRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the dealResourceEventLogs by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DealResourceEventLogs : {}", id);
        dealResourceEventLogsRepository.deleteById(id);
    }
}
