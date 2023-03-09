package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.LeavesOlds;
import com.venturedive.vendian_mono.repository.LeavesOldsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LeavesOlds}.
 */
@Service
@Transactional
public class LeavesOldsService {

    private final Logger log = LoggerFactory.getLogger(LeavesOldsService.class);

    private final LeavesOldsRepository leavesOldsRepository;

    public LeavesOldsService(LeavesOldsRepository leavesOldsRepository) {
        this.leavesOldsRepository = leavesOldsRepository;
    }

    /**
     * Save a leavesOlds.
     *
     * @param leavesOlds the entity to save.
     * @return the persisted entity.
     */
    public LeavesOlds save(LeavesOlds leavesOlds) {
        log.debug("Request to save LeavesOlds : {}", leavesOlds);
        return leavesOldsRepository.save(leavesOlds);
    }

    /**
     * Update a leavesOlds.
     *
     * @param leavesOlds the entity to save.
     * @return the persisted entity.
     */
    public LeavesOlds update(LeavesOlds leavesOlds) {
        log.debug("Request to update LeavesOlds : {}", leavesOlds);
        return leavesOldsRepository.save(leavesOlds);
    }

    /**
     * Partially update a leavesOlds.
     *
     * @param leavesOlds the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LeavesOlds> partialUpdate(LeavesOlds leavesOlds) {
        log.debug("Request to partially update LeavesOlds : {}", leavesOlds);

        return leavesOldsRepository
            .findById(leavesOlds.getId())
            .map(existingLeavesOlds -> {
                if (leavesOlds.getAnnualtotal() != null) {
                    existingLeavesOlds.setAnnualtotal(leavesOlds.getAnnualtotal());
                }
                if (leavesOlds.getAnnualused() != null) {
                    existingLeavesOlds.setAnnualused(leavesOlds.getAnnualused());
                }
                if (leavesOlds.getAnnualadjustments() != null) {
                    existingLeavesOlds.setAnnualadjustments(leavesOlds.getAnnualadjustments());
                }
                if (leavesOlds.getCasualtotal() != null) {
                    existingLeavesOlds.setCasualtotal(leavesOlds.getCasualtotal());
                }
                if (leavesOlds.getCasualused() != null) {
                    existingLeavesOlds.setCasualused(leavesOlds.getCasualused());
                }
                if (leavesOlds.getSicktotal() != null) {
                    existingLeavesOlds.setSicktotal(leavesOlds.getSicktotal());
                }
                if (leavesOlds.getSickused() != null) {
                    existingLeavesOlds.setSickused(leavesOlds.getSickused());
                }
                if (leavesOlds.getAnnualtotalnextyear() != null) {
                    existingLeavesOlds.setAnnualtotalnextyear(leavesOlds.getAnnualtotalnextyear());
                }
                if (leavesOlds.getAnnualusednextyear() != null) {
                    existingLeavesOlds.setAnnualusednextyear(leavesOlds.getAnnualusednextyear());
                }
                if (leavesOlds.getCasualtotalnextyear() != null) {
                    existingLeavesOlds.setCasualtotalnextyear(leavesOlds.getCasualtotalnextyear());
                }
                if (leavesOlds.getCasualusednextyear() != null) {
                    existingLeavesOlds.setCasualusednextyear(leavesOlds.getCasualusednextyear());
                }
                if (leavesOlds.getSicktotalnextyear() != null) {
                    existingLeavesOlds.setSicktotalnextyear(leavesOlds.getSicktotalnextyear());
                }
                if (leavesOlds.getSickusednextyear() != null) {
                    existingLeavesOlds.setSickusednextyear(leavesOlds.getSickusednextyear());
                }
                if (leavesOlds.getCarryforward() != null) {
                    existingLeavesOlds.setCarryforward(leavesOlds.getCarryforward());
                }
                if (leavesOlds.getCreatedat() != null) {
                    existingLeavesOlds.setCreatedat(leavesOlds.getCreatedat());
                }
                if (leavesOlds.getUpdatedat() != null) {
                    existingLeavesOlds.setUpdatedat(leavesOlds.getUpdatedat());
                }

                return existingLeavesOlds;
            })
            .map(leavesOldsRepository::save);
    }

    /**
     * Get all the leavesOlds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LeavesOlds> findAll(Pageable pageable) {
        log.debug("Request to get all LeavesOlds");
        return leavesOldsRepository.findAll(pageable);
    }

    /**
     * Get one leavesOlds by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LeavesOlds> findOne(Long id) {
        log.debug("Request to get LeavesOlds : {}", id);
        return leavesOldsRepository.findById(id);
    }

    /**
     * Delete the leavesOlds by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LeavesOlds : {}", id);
        leavesOldsRepository.deleteById(id);
    }
}
