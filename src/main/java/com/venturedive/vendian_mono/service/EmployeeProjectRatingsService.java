package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.EmployeeProjectRatings;
import com.venturedive.vendian_mono.repository.EmployeeProjectRatingsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmployeeProjectRatings}.
 */
@Service
@Transactional
public class EmployeeProjectRatingsService {

    private final Logger log = LoggerFactory.getLogger(EmployeeProjectRatingsService.class);

    private final EmployeeProjectRatingsRepository employeeProjectRatingsRepository;

    public EmployeeProjectRatingsService(EmployeeProjectRatingsRepository employeeProjectRatingsRepository) {
        this.employeeProjectRatingsRepository = employeeProjectRatingsRepository;
    }

    /**
     * Save a employeeProjectRatings.
     *
     * @param employeeProjectRatings the entity to save.
     * @return the persisted entity.
     */
    public EmployeeProjectRatings save(EmployeeProjectRatings employeeProjectRatings) {
        log.debug("Request to save EmployeeProjectRatings : {}", employeeProjectRatings);
        return employeeProjectRatingsRepository.save(employeeProjectRatings);
    }

    /**
     * Update a employeeProjectRatings.
     *
     * @param employeeProjectRatings the entity to save.
     * @return the persisted entity.
     */
    public EmployeeProjectRatings update(EmployeeProjectRatings employeeProjectRatings) {
        log.debug("Request to update EmployeeProjectRatings : {}", employeeProjectRatings);
        return employeeProjectRatingsRepository.save(employeeProjectRatings);
    }

    /**
     * Partially update a employeeProjectRatings.
     *
     * @param employeeProjectRatings the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmployeeProjectRatings> partialUpdate(EmployeeProjectRatings employeeProjectRatings) {
        log.debug("Request to partially update EmployeeProjectRatings : {}", employeeProjectRatings);

        return employeeProjectRatingsRepository
            .findById(employeeProjectRatings.getId())
            .map(existingEmployeeProjectRatings -> {
                if (employeeProjectRatings.getCreatedat() != null) {
                    existingEmployeeProjectRatings.setCreatedat(employeeProjectRatings.getCreatedat());
                }
                if (employeeProjectRatings.getUpdatedat() != null) {
                    existingEmployeeProjectRatings.setUpdatedat(employeeProjectRatings.getUpdatedat());
                }
                if (employeeProjectRatings.getPmquality() != null) {
                    existingEmployeeProjectRatings.setPmquality(employeeProjectRatings.getPmquality());
                }
                if (employeeProjectRatings.getPmqualityContentType() != null) {
                    existingEmployeeProjectRatings.setPmqualityContentType(employeeProjectRatings.getPmqualityContentType());
                }
                if (employeeProjectRatings.getPmownership() != null) {
                    existingEmployeeProjectRatings.setPmownership(employeeProjectRatings.getPmownership());
                }
                if (employeeProjectRatings.getPmownershipContentType() != null) {
                    existingEmployeeProjectRatings.setPmownershipContentType(employeeProjectRatings.getPmownershipContentType());
                }
                if (employeeProjectRatings.getPmskill() != null) {
                    existingEmployeeProjectRatings.setPmskill(employeeProjectRatings.getPmskill());
                }
                if (employeeProjectRatings.getPmskillContentType() != null) {
                    existingEmployeeProjectRatings.setPmskillContentType(employeeProjectRatings.getPmskillContentType());
                }
                if (employeeProjectRatings.getPmethics() != null) {
                    existingEmployeeProjectRatings.setPmethics(employeeProjectRatings.getPmethics());
                }
                if (employeeProjectRatings.getPmethicsContentType() != null) {
                    existingEmployeeProjectRatings.setPmethicsContentType(employeeProjectRatings.getPmethicsContentType());
                }
                if (employeeProjectRatings.getPmefficiency() != null) {
                    existingEmployeeProjectRatings.setPmefficiency(employeeProjectRatings.getPmefficiency());
                }
                if (employeeProjectRatings.getPmefficiencyContentType() != null) {
                    existingEmployeeProjectRatings.setPmefficiencyContentType(employeeProjectRatings.getPmefficiencyContentType());
                }
                if (employeeProjectRatings.getPmfreeze() != null) {
                    existingEmployeeProjectRatings.setPmfreeze(employeeProjectRatings.getPmfreeze());
                }
                if (employeeProjectRatings.getPmfreezeContentType() != null) {
                    existingEmployeeProjectRatings.setPmfreezeContentType(employeeProjectRatings.getPmfreezeContentType());
                }
                if (employeeProjectRatings.getArchfreeze() != null) {
                    existingEmployeeProjectRatings.setArchfreeze(employeeProjectRatings.getArchfreeze());
                }
                if (employeeProjectRatings.getArchfreezeContentType() != null) {
                    existingEmployeeProjectRatings.setArchfreezeContentType(employeeProjectRatings.getArchfreezeContentType());
                }
                if (employeeProjectRatings.getPmcomment() != null) {
                    existingEmployeeProjectRatings.setPmcomment(employeeProjectRatings.getPmcomment());
                }
                if (employeeProjectRatings.getPmcommentContentType() != null) {
                    existingEmployeeProjectRatings.setPmcommentContentType(employeeProjectRatings.getPmcommentContentType());
                }
                if (employeeProjectRatings.getArchquality() != null) {
                    existingEmployeeProjectRatings.setArchquality(employeeProjectRatings.getArchquality());
                }
                if (employeeProjectRatings.getArchqualityContentType() != null) {
                    existingEmployeeProjectRatings.setArchqualityContentType(employeeProjectRatings.getArchqualityContentType());
                }
                if (employeeProjectRatings.getArchownership() != null) {
                    existingEmployeeProjectRatings.setArchownership(employeeProjectRatings.getArchownership());
                }
                if (employeeProjectRatings.getArchownershipContentType() != null) {
                    existingEmployeeProjectRatings.setArchownershipContentType(employeeProjectRatings.getArchownershipContentType());
                }
                if (employeeProjectRatings.getArchskill() != null) {
                    existingEmployeeProjectRatings.setArchskill(employeeProjectRatings.getArchskill());
                }
                if (employeeProjectRatings.getArchskillContentType() != null) {
                    existingEmployeeProjectRatings.setArchskillContentType(employeeProjectRatings.getArchskillContentType());
                }
                if (employeeProjectRatings.getArchethics() != null) {
                    existingEmployeeProjectRatings.setArchethics(employeeProjectRatings.getArchethics());
                }
                if (employeeProjectRatings.getArchethicsContentType() != null) {
                    existingEmployeeProjectRatings.setArchethicsContentType(employeeProjectRatings.getArchethicsContentType());
                }
                if (employeeProjectRatings.getArchefficiency() != null) {
                    existingEmployeeProjectRatings.setArchefficiency(employeeProjectRatings.getArchefficiency());
                }
                if (employeeProjectRatings.getArchefficiencyContentType() != null) {
                    existingEmployeeProjectRatings.setArchefficiencyContentType(employeeProjectRatings.getArchefficiencyContentType());
                }
                if (employeeProjectRatings.getArchcomment() != null) {
                    existingEmployeeProjectRatings.setArchcomment(employeeProjectRatings.getArchcomment());
                }
                if (employeeProjectRatings.getArchcommentContentType() != null) {
                    existingEmployeeProjectRatings.setArchcommentContentType(employeeProjectRatings.getArchcommentContentType());
                }
                if (employeeProjectRatings.getArchcodequality() != null) {
                    existingEmployeeProjectRatings.setArchcodequality(employeeProjectRatings.getArchcodequality());
                }
                if (employeeProjectRatings.getArchcodequalityContentType() != null) {
                    existingEmployeeProjectRatings.setArchcodequalityContentType(employeeProjectRatings.getArchcodequalityContentType());
                }
                if (employeeProjectRatings.getArchdocumentation() != null) {
                    existingEmployeeProjectRatings.setArchdocumentation(employeeProjectRatings.getArchdocumentation());
                }
                if (employeeProjectRatings.getArchdocumentationContentType() != null) {
                    existingEmployeeProjectRatings.setArchdocumentationContentType(
                        employeeProjectRatings.getArchdocumentationContentType()
                    );
                }
                if (employeeProjectRatings.getArchcollaboration() != null) {
                    existingEmployeeProjectRatings.setArchcollaboration(employeeProjectRatings.getArchcollaboration());
                }
                if (employeeProjectRatings.getArchcollaborationContentType() != null) {
                    existingEmployeeProjectRatings.setArchcollaborationContentType(
                        employeeProjectRatings.getArchcollaborationContentType()
                    );
                }
                if (employeeProjectRatings.getPmdocumentation() != null) {
                    existingEmployeeProjectRatings.setPmdocumentation(employeeProjectRatings.getPmdocumentation());
                }
                if (employeeProjectRatings.getPmdocumentationContentType() != null) {
                    existingEmployeeProjectRatings.setPmdocumentationContentType(employeeProjectRatings.getPmdocumentationContentType());
                }
                if (employeeProjectRatings.getPmcollaboration() != null) {
                    existingEmployeeProjectRatings.setPmcollaboration(employeeProjectRatings.getPmcollaboration());
                }
                if (employeeProjectRatings.getPmcollaborationContentType() != null) {
                    existingEmployeeProjectRatings.setPmcollaborationContentType(employeeProjectRatings.getPmcollaborationContentType());
                }

                return existingEmployeeProjectRatings;
            })
            .map(employeeProjectRatingsRepository::save);
    }

    /**
     * Get all the employeeProjectRatings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeProjectRatings> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeProjectRatings");
        return employeeProjectRatingsRepository.findAll(pageable);
    }

    /**
     * Get one employeeProjectRatings by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeProjectRatings> findOne(Long id) {
        log.debug("Request to get EmployeeProjectRatings : {}", id);
        return employeeProjectRatingsRepository.findById(id);
    }

    /**
     * Delete the employeeProjectRatings by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeProjectRatings : {}", id);
        employeeProjectRatingsRepository.deleteById(id);
    }
}
