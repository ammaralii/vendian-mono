package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.DealResourceStatus;
import com.venturedive.vendian_mono.repository.DealResourceStatusRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DealResourceStatus}.
 */
@Service
@Transactional
public class DealResourceStatusService {

    private final Logger log = LoggerFactory.getLogger(DealResourceStatusService.class);

    private final DealResourceStatusRepository dealResourceStatusRepository;

    public DealResourceStatusService(DealResourceStatusRepository dealResourceStatusRepository) {
        this.dealResourceStatusRepository = dealResourceStatusRepository;
    }

    /**
     * Save a dealResourceStatus.
     *
     * @param dealResourceStatus the entity to save.
     * @return the persisted entity.
     */
    public DealResourceStatus save(DealResourceStatus dealResourceStatus) {
        log.debug("Request to save DealResourceStatus : {}", dealResourceStatus);
        return dealResourceStatusRepository.save(dealResourceStatus);
    }

    /**
     * Update a dealResourceStatus.
     *
     * @param dealResourceStatus the entity to save.
     * @return the persisted entity.
     */
    public DealResourceStatus update(DealResourceStatus dealResourceStatus) {
        log.debug("Request to update DealResourceStatus : {}", dealResourceStatus);
        return dealResourceStatusRepository.save(dealResourceStatus);
    }

    /**
     * Partially update a dealResourceStatus.
     *
     * @param dealResourceStatus the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DealResourceStatus> partialUpdate(DealResourceStatus dealResourceStatus) {
        log.debug("Request to partially update DealResourceStatus : {}", dealResourceStatus);

        return dealResourceStatusRepository
            .findById(dealResourceStatus.getId())
            .map(existingDealResourceStatus -> {
                if (dealResourceStatus.getDisplayname() != null) {
                    existingDealResourceStatus.setDisplayname(dealResourceStatus.getDisplayname());
                }
                if (dealResourceStatus.getGroup() != null) {
                    existingDealResourceStatus.setGroup(dealResourceStatus.getGroup());
                }
                if (dealResourceStatus.getSystemKey() != null) {
                    existingDealResourceStatus.setSystemKey(dealResourceStatus.getSystemKey());
                }
                if (dealResourceStatus.getEffectivedate() != null) {
                    existingDealResourceStatus.setEffectivedate(dealResourceStatus.getEffectivedate());
                }
                if (dealResourceStatus.getEnddate() != null) {
                    existingDealResourceStatus.setEnddate(dealResourceStatus.getEnddate());
                }
                if (dealResourceStatus.getCreatedat() != null) {
                    existingDealResourceStatus.setCreatedat(dealResourceStatus.getCreatedat());
                }
                if (dealResourceStatus.getUpdatedat() != null) {
                    existingDealResourceStatus.setUpdatedat(dealResourceStatus.getUpdatedat());
                }

                return existingDealResourceStatus;
            })
            .map(dealResourceStatusRepository::save);
    }

    /**
     * Get all the dealResourceStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DealResourceStatus> findAll(Pageable pageable) {
        log.debug("Request to get all DealResourceStatuses");
        return dealResourceStatusRepository.findAll(pageable);
    }

    /**
     * Get one dealResourceStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DealResourceStatus> findOne(Long id) {
        log.debug("Request to get DealResourceStatus : {}", id);
        return dealResourceStatusRepository.findById(id);
    }

    /**
     * Delete the dealResourceStatus by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DealResourceStatus : {}", id);
        dealResourceStatusRepository.deleteById(id);
    }
}
