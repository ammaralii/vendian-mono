package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.LeavesCopy;
import com.venturedive.vendian_mono.repository.LeavesCopyRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LeavesCopy}.
 */
@Service
@Transactional
public class LeavesCopyService {

    private final Logger log = LoggerFactory.getLogger(LeavesCopyService.class);

    private final LeavesCopyRepository leavesCopyRepository;

    public LeavesCopyService(LeavesCopyRepository leavesCopyRepository) {
        this.leavesCopyRepository = leavesCopyRepository;
    }

    /**
     * Save a leavesCopy.
     *
     * @param leavesCopy the entity to save.
     * @return the persisted entity.
     */
    public LeavesCopy save(LeavesCopy leavesCopy) {
        log.debug("Request to save LeavesCopy : {}", leavesCopy);
        return leavesCopyRepository.save(leavesCopy);
    }

    /**
     * Update a leavesCopy.
     *
     * @param leavesCopy the entity to save.
     * @return the persisted entity.
     */
    public LeavesCopy update(LeavesCopy leavesCopy) {
        log.debug("Request to update LeavesCopy : {}", leavesCopy);
        return leavesCopyRepository.save(leavesCopy);
    }

    /**
     * Partially update a leavesCopy.
     *
     * @param leavesCopy the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LeavesCopy> partialUpdate(LeavesCopy leavesCopy) {
        log.debug("Request to partially update LeavesCopy : {}", leavesCopy);

        return leavesCopyRepository
            .findById(leavesCopy.getId())
            .map(existingLeavesCopy -> {
                if (leavesCopy.getAnnualtotal() != null) {
                    existingLeavesCopy.setAnnualtotal(leavesCopy.getAnnualtotal());
                }
                if (leavesCopy.getAnnualused() != null) {
                    existingLeavesCopy.setAnnualused(leavesCopy.getAnnualused());
                }
                if (leavesCopy.getAnnualadjustments() != null) {
                    existingLeavesCopy.setAnnualadjustments(leavesCopy.getAnnualadjustments());
                }
                if (leavesCopy.getCasualtotal() != null) {
                    existingLeavesCopy.setCasualtotal(leavesCopy.getCasualtotal());
                }
                if (leavesCopy.getCasualused() != null) {
                    existingLeavesCopy.setCasualused(leavesCopy.getCasualused());
                }
                if (leavesCopy.getSicktotal() != null) {
                    existingLeavesCopy.setSicktotal(leavesCopy.getSicktotal());
                }
                if (leavesCopy.getSickused() != null) {
                    existingLeavesCopy.setSickused(leavesCopy.getSickused());
                }
                if (leavesCopy.getAnnualtotalnextyear() != null) {
                    existingLeavesCopy.setAnnualtotalnextyear(leavesCopy.getAnnualtotalnextyear());
                }
                if (leavesCopy.getAnnualusednextyear() != null) {
                    existingLeavesCopy.setAnnualusednextyear(leavesCopy.getAnnualusednextyear());
                }
                if (leavesCopy.getCasualtotalnextyear() != null) {
                    existingLeavesCopy.setCasualtotalnextyear(leavesCopy.getCasualtotalnextyear());
                }
                if (leavesCopy.getCasualusednextyear() != null) {
                    existingLeavesCopy.setCasualusednextyear(leavesCopy.getCasualusednextyear());
                }
                if (leavesCopy.getSicktotalnextyear() != null) {
                    existingLeavesCopy.setSicktotalnextyear(leavesCopy.getSicktotalnextyear());
                }
                if (leavesCopy.getSickusednextyear() != null) {
                    existingLeavesCopy.setSickusednextyear(leavesCopy.getSickusednextyear());
                }
                if (leavesCopy.getCarryforward() != null) {
                    existingLeavesCopy.setCarryforward(leavesCopy.getCarryforward());
                }
                if (leavesCopy.getCreatedat() != null) {
                    existingLeavesCopy.setCreatedat(leavesCopy.getCreatedat());
                }
                if (leavesCopy.getUpdatedat() != null) {
                    existingLeavesCopy.setUpdatedat(leavesCopy.getUpdatedat());
                }

                return existingLeavesCopy;
            })
            .map(leavesCopyRepository::save);
    }

    /**
     * Get all the leavesCopies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LeavesCopy> findAll(Pageable pageable) {
        log.debug("Request to get all LeavesCopies");
        return leavesCopyRepository.findAll(pageable);
    }

    /**
     * Get one leavesCopy by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LeavesCopy> findOne(Long id) {
        log.debug("Request to get LeavesCopy : {}", id);
        return leavesCopyRepository.findById(id);
    }

    /**
     * Delete the leavesCopy by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LeavesCopy : {}", id);
        leavesCopyRepository.deleteById(id);
    }
}
