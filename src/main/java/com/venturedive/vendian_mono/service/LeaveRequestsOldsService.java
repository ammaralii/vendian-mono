package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.LeaveRequestsOlds;
import com.venturedive.vendian_mono.repository.LeaveRequestsOldsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LeaveRequestsOlds}.
 */
@Service
@Transactional
public class LeaveRequestsOldsService {

    private final Logger log = LoggerFactory.getLogger(LeaveRequestsOldsService.class);

    private final LeaveRequestsOldsRepository leaveRequestsOldsRepository;

    public LeaveRequestsOldsService(LeaveRequestsOldsRepository leaveRequestsOldsRepository) {
        this.leaveRequestsOldsRepository = leaveRequestsOldsRepository;
    }

    /**
     * Save a leaveRequestsOlds.
     *
     * @param leaveRequestsOlds the entity to save.
     * @return the persisted entity.
     */
    public LeaveRequestsOlds save(LeaveRequestsOlds leaveRequestsOlds) {
        log.debug("Request to save LeaveRequestsOlds : {}", leaveRequestsOlds);
        return leaveRequestsOldsRepository.save(leaveRequestsOlds);
    }

    /**
     * Update a leaveRequestsOlds.
     *
     * @param leaveRequestsOlds the entity to save.
     * @return the persisted entity.
     */
    public LeaveRequestsOlds update(LeaveRequestsOlds leaveRequestsOlds) {
        log.debug("Request to update LeaveRequestsOlds : {}", leaveRequestsOlds);
        return leaveRequestsOldsRepository.save(leaveRequestsOlds);
    }

    /**
     * Partially update a leaveRequestsOlds.
     *
     * @param leaveRequestsOlds the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LeaveRequestsOlds> partialUpdate(LeaveRequestsOlds leaveRequestsOlds) {
        log.debug("Request to partially update LeaveRequestsOlds : {}", leaveRequestsOlds);

        return leaveRequestsOldsRepository
            .findById(leaveRequestsOlds.getId())
            .map(existingLeaveRequestsOlds -> {
                if (leaveRequestsOlds.getStartdate() != null) {
                    existingLeaveRequestsOlds.setStartdate(leaveRequestsOlds.getStartdate());
                }
                if (leaveRequestsOlds.getEnddate() != null) {
                    existingLeaveRequestsOlds.setEnddate(leaveRequestsOlds.getEnddate());
                }
                if (leaveRequestsOlds.getRequesternote() != null) {
                    existingLeaveRequestsOlds.setRequesternote(leaveRequestsOlds.getRequesternote());
                }
                if (leaveRequestsOlds.getManagernote() != null) {
                    existingLeaveRequestsOlds.setManagernote(leaveRequestsOlds.getManagernote());
                }
                if (leaveRequestsOlds.getCurrentstatus() != null) {
                    existingLeaveRequestsOlds.setCurrentstatus(leaveRequestsOlds.getCurrentstatus());
                }
                if (leaveRequestsOlds.getLeavescanceled() != null) {
                    existingLeaveRequestsOlds.setLeavescanceled(leaveRequestsOlds.getLeavescanceled());
                }
                if (leaveRequestsOlds.getRequestdate() != null) {
                    existingLeaveRequestsOlds.setRequestdate(leaveRequestsOlds.getRequestdate());
                }
                if (leaveRequestsOlds.getLinkstring() != null) {
                    existingLeaveRequestsOlds.setLinkstring(leaveRequestsOlds.getLinkstring());
                }
                if (leaveRequestsOlds.getLinkused() != null) {
                    existingLeaveRequestsOlds.setLinkused(leaveRequestsOlds.getLinkused());
                }
                if (leaveRequestsOlds.getCreatedat() != null) {
                    existingLeaveRequestsOlds.setCreatedat(leaveRequestsOlds.getCreatedat());
                }
                if (leaveRequestsOlds.getUpdatedat() != null) {
                    existingLeaveRequestsOlds.setUpdatedat(leaveRequestsOlds.getUpdatedat());
                }
                if (leaveRequestsOlds.getIshalfday() != null) {
                    existingLeaveRequestsOlds.setIshalfday(leaveRequestsOlds.getIshalfday());
                }
                if (leaveRequestsOlds.getActiondate() != null) {
                    existingLeaveRequestsOlds.setActiondate(leaveRequestsOlds.getActiondate());
                }
                if (leaveRequestsOlds.getLmstatus() != null) {
                    existingLeaveRequestsOlds.setLmstatus(leaveRequestsOlds.getLmstatus());
                }
                if (leaveRequestsOlds.getPmstatus() != null) {
                    existingLeaveRequestsOlds.setPmstatus(leaveRequestsOlds.getPmstatus());
                }
                if (leaveRequestsOlds.getIsbench() != null) {
                    existingLeaveRequestsOlds.setIsbench(leaveRequestsOlds.getIsbench());
                }
                if (leaveRequestsOlds.getIsescalated() != null) {
                    existingLeaveRequestsOlds.setIsescalated(leaveRequestsOlds.getIsescalated());
                }
                if (leaveRequestsOlds.getIscommented() != null) {
                    existingLeaveRequestsOlds.setIscommented(leaveRequestsOlds.getIscommented());
                }
                if (leaveRequestsOlds.getIsreminded() != null) {
                    existingLeaveRequestsOlds.setIsreminded(leaveRequestsOlds.getIsreminded());
                }
                if (leaveRequestsOlds.getIsnotified() != null) {
                    existingLeaveRequestsOlds.setIsnotified(leaveRequestsOlds.getIsnotified());
                }
                if (leaveRequestsOlds.getIsremindedhr() != null) {
                    existingLeaveRequestsOlds.setIsremindedhr(leaveRequestsOlds.getIsremindedhr());
                }
                if (leaveRequestsOlds.getIsdm() != null) {
                    existingLeaveRequestsOlds.setIsdm(leaveRequestsOlds.getIsdm());
                }

                return existingLeaveRequestsOlds;
            })
            .map(leaveRequestsOldsRepository::save);
    }

    /**
     * Get all the leaveRequestsOlds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveRequestsOlds> findAll(Pageable pageable) {
        log.debug("Request to get all LeaveRequestsOlds");
        return leaveRequestsOldsRepository.findAll(pageable);
    }

    /**
     * Get one leaveRequestsOlds by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LeaveRequestsOlds> findOne(Long id) {
        log.debug("Request to get LeaveRequestsOlds : {}", id);
        return leaveRequestsOldsRepository.findById(id);
    }

    /**
     * Delete the leaveRequestsOlds by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LeaveRequestsOlds : {}", id);
        leaveRequestsOldsRepository.deleteById(id);
    }
}
