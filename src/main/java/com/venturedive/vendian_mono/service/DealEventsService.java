package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.DealEvents;
import com.venturedive.vendian_mono.repository.DealEventsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DealEvents}.
 */
@Service
@Transactional
public class DealEventsService {

    private final Logger log = LoggerFactory.getLogger(DealEventsService.class);

    private final DealEventsRepository dealEventsRepository;

    public DealEventsService(DealEventsRepository dealEventsRepository) {
        this.dealEventsRepository = dealEventsRepository;
    }

    /**
     * Save a dealEvents.
     *
     * @param dealEvents the entity to save.
     * @return the persisted entity.
     */
    public DealEvents save(DealEvents dealEvents) {
        log.debug("Request to save DealEvents : {}", dealEvents);
        return dealEventsRepository.save(dealEvents);
    }

    /**
     * Update a dealEvents.
     *
     * @param dealEvents the entity to save.
     * @return the persisted entity.
     */
    public DealEvents update(DealEvents dealEvents) {
        log.debug("Request to update DealEvents : {}", dealEvents);
        return dealEventsRepository.save(dealEvents);
    }

    /**
     * Partially update a dealEvents.
     *
     * @param dealEvents the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DealEvents> partialUpdate(DealEvents dealEvents) {
        log.debug("Request to partially update DealEvents : {}", dealEvents);

        return dealEventsRepository
            .findById(dealEvents.getId())
            .map(existingDealEvents -> {
                if (dealEvents.getEventtype() != null) {
                    existingDealEvents.setEventtype(dealEvents.getEventtype());
                }
                if (dealEvents.getCreatedat() != null) {
                    existingDealEvents.setCreatedat(dealEvents.getCreatedat());
                }

                return existingDealEvents;
            })
            .map(dealEventsRepository::save);
    }

    /**
     * Get all the dealEvents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DealEvents> findAll(Pageable pageable) {
        log.debug("Request to get all DealEvents");
        return dealEventsRepository.findAll(pageable);
    }

    /**
     * Get one dealEvents by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DealEvents> findOne(Long id) {
        log.debug("Request to get DealEvents : {}", id);
        return dealEventsRepository.findById(id);
    }

    /**
     * Delete the dealEvents by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DealEvents : {}", id);
        dealEventsRepository.deleteById(id);
    }
}
