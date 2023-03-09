package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.EmployeeCompensation;
import com.venturedive.vendian_mono.repository.EmployeeCompensationRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmployeeCompensation}.
 */
@Service
@Transactional
public class EmployeeCompensationService {

    private final Logger log = LoggerFactory.getLogger(EmployeeCompensationService.class);

    private final EmployeeCompensationRepository employeeCompensationRepository;

    public EmployeeCompensationService(EmployeeCompensationRepository employeeCompensationRepository) {
        this.employeeCompensationRepository = employeeCompensationRepository;
    }

    /**
     * Save a employeeCompensation.
     *
     * @param employeeCompensation the entity to save.
     * @return the persisted entity.
     */
    public EmployeeCompensation save(EmployeeCompensation employeeCompensation) {
        log.debug("Request to save EmployeeCompensation : {}", employeeCompensation);
        return employeeCompensationRepository.save(employeeCompensation);
    }

    /**
     * Update a employeeCompensation.
     *
     * @param employeeCompensation the entity to save.
     * @return the persisted entity.
     */
    public EmployeeCompensation update(EmployeeCompensation employeeCompensation) {
        log.debug("Request to update EmployeeCompensation : {}", employeeCompensation);
        return employeeCompensationRepository.save(employeeCompensation);
    }

    /**
     * Partially update a employeeCompensation.
     *
     * @param employeeCompensation the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmployeeCompensation> partialUpdate(EmployeeCompensation employeeCompensation) {
        log.debug("Request to partially update EmployeeCompensation : {}", employeeCompensation);

        return employeeCompensationRepository
            .findById(employeeCompensation.getId())
            .map(existingEmployeeCompensation -> {
                if (employeeCompensation.getAmount() != null) {
                    existingEmployeeCompensation.setAmount(employeeCompensation.getAmount());
                }
                if (employeeCompensation.getAmountContentType() != null) {
                    existingEmployeeCompensation.setAmountContentType(employeeCompensation.getAmountContentType());
                }
                if (employeeCompensation.getDate() != null) {
                    existingEmployeeCompensation.setDate(employeeCompensation.getDate());
                }
                if (employeeCompensation.getEcReason() != null) {
                    existingEmployeeCompensation.setEcReason(employeeCompensation.getEcReason());
                }
                if (employeeCompensation.getEcReasonContentType() != null) {
                    existingEmployeeCompensation.setEcReasonContentType(employeeCompensation.getEcReasonContentType());
                }
                if (employeeCompensation.getType() != null) {
                    existingEmployeeCompensation.setType(employeeCompensation.getType());
                }
                if (employeeCompensation.getCommitmentuntil() != null) {
                    existingEmployeeCompensation.setCommitmentuntil(employeeCompensation.getCommitmentuntil());
                }
                if (employeeCompensation.getComments() != null) {
                    existingEmployeeCompensation.setComments(employeeCompensation.getComments());
                }
                if (employeeCompensation.getCreatedat() != null) {
                    existingEmployeeCompensation.setCreatedat(employeeCompensation.getCreatedat());
                }
                if (employeeCompensation.getUpdatedat() != null) {
                    existingEmployeeCompensation.setUpdatedat(employeeCompensation.getUpdatedat());
                }
                if (employeeCompensation.getReasoncomments() != null) {
                    existingEmployeeCompensation.setReasoncomments(employeeCompensation.getReasoncomments());
                }

                return existingEmployeeCompensation;
            })
            .map(employeeCompensationRepository::save);
    }

    /**
     * Get all the employeeCompensations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeCompensation> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeCompensations");
        return employeeCompensationRepository.findAll(pageable);
    }

    /**
     * Get all the employeeCompensations with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<EmployeeCompensation> findAllWithEagerRelationships(Pageable pageable) {
        return employeeCompensationRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one employeeCompensation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeCompensation> findOne(Long id) {
        log.debug("Request to get EmployeeCompensation : {}", id);
        return employeeCompensationRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the employeeCompensation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeCompensation : {}", id);
        employeeCompensationRepository.deleteById(id);
    }
}
