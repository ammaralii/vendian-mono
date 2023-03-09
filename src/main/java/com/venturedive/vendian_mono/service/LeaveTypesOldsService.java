package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.LeaveTypesOlds;
import com.venturedive.vendian_mono.repository.LeaveTypesOldsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LeaveTypesOlds}.
 */
@Service
@Transactional
public class LeaveTypesOldsService {

    private final Logger log = LoggerFactory.getLogger(LeaveTypesOldsService.class);

    private final LeaveTypesOldsRepository leaveTypesOldsRepository;

    public LeaveTypesOldsService(LeaveTypesOldsRepository leaveTypesOldsRepository) {
        this.leaveTypesOldsRepository = leaveTypesOldsRepository;
    }

    /**
     * Save a leaveTypesOlds.
     *
     * @param leaveTypesOlds the entity to save.
     * @return the persisted entity.
     */
    public LeaveTypesOlds save(LeaveTypesOlds leaveTypesOlds) {
        log.debug("Request to save LeaveTypesOlds : {}", leaveTypesOlds);
        return leaveTypesOldsRepository.save(leaveTypesOlds);
    }

    /**
     * Update a leaveTypesOlds.
     *
     * @param leaveTypesOlds the entity to save.
     * @return the persisted entity.
     */
    public LeaveTypesOlds update(LeaveTypesOlds leaveTypesOlds) {
        log.debug("Request to update LeaveTypesOlds : {}", leaveTypesOlds);
        return leaveTypesOldsRepository.save(leaveTypesOlds);
    }

    /**
     * Partially update a leaveTypesOlds.
     *
     * @param leaveTypesOlds the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LeaveTypesOlds> partialUpdate(LeaveTypesOlds leaveTypesOlds) {
        log.debug("Request to partially update LeaveTypesOlds : {}", leaveTypesOlds);

        return leaveTypesOldsRepository
            .findById(leaveTypesOlds.getId())
            .map(existingLeaveTypesOlds -> {
                if (leaveTypesOlds.getName() != null) {
                    existingLeaveTypesOlds.setName(leaveTypesOlds.getName());
                }
                if (leaveTypesOlds.getIsactive() != null) {
                    existingLeaveTypesOlds.setIsactive(leaveTypesOlds.getIsactive());
                }
                if (leaveTypesOlds.getCreatedat() != null) {
                    existingLeaveTypesOlds.setCreatedat(leaveTypesOlds.getCreatedat());
                }
                if (leaveTypesOlds.getUpdatedat() != null) {
                    existingLeaveTypesOlds.setUpdatedat(leaveTypesOlds.getUpdatedat());
                }

                return existingLeaveTypesOlds;
            })
            .map(leaveTypesOldsRepository::save);
    }

    /**
     * Get all the leaveTypesOlds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveTypesOlds> findAll(Pageable pageable) {
        log.debug("Request to get all LeaveTypesOlds");
        return leaveTypesOldsRepository.findAll(pageable);
    }

    /**
     * Get one leaveTypesOlds by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LeaveTypesOlds> findOne(Long id) {
        log.debug("Request to get LeaveTypesOlds : {}", id);
        return leaveTypesOldsRepository.findById(id);
    }

    /**
     * Delete the leaveTypesOlds by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LeaveTypesOlds : {}", id);
        leaveTypesOldsRepository.deleteById(id);
    }
}
