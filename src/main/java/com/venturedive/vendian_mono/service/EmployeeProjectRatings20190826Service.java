package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.EmployeeProjectRatings20190826;
import com.venturedive.vendian_mono.repository.EmployeeProjectRatings20190826Repository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmployeeProjectRatings20190826}.
 */
@Service
@Transactional
public class EmployeeProjectRatings20190826Service {

    private final Logger log = LoggerFactory.getLogger(EmployeeProjectRatings20190826Service.class);

    private final EmployeeProjectRatings20190826Repository employeeProjectRatings20190826Repository;

    public EmployeeProjectRatings20190826Service(EmployeeProjectRatings20190826Repository employeeProjectRatings20190826Repository) {
        this.employeeProjectRatings20190826Repository = employeeProjectRatings20190826Repository;
    }

    /**
     * Save a employeeProjectRatings20190826.
     *
     * @param employeeProjectRatings20190826 the entity to save.
     * @return the persisted entity.
     */
    public EmployeeProjectRatings20190826 save(EmployeeProjectRatings20190826 employeeProjectRatings20190826) {
        log.debug("Request to save EmployeeProjectRatings20190826 : {}", employeeProjectRatings20190826);
        return employeeProjectRatings20190826Repository.save(employeeProjectRatings20190826);
    }

    /**
     * Update a employeeProjectRatings20190826.
     *
     * @param employeeProjectRatings20190826 the entity to save.
     * @return the persisted entity.
     */
    public EmployeeProjectRatings20190826 update(EmployeeProjectRatings20190826 employeeProjectRatings20190826) {
        log.debug("Request to update EmployeeProjectRatings20190826 : {}", employeeProjectRatings20190826);
        return employeeProjectRatings20190826Repository.save(employeeProjectRatings20190826);
    }

    /**
     * Partially update a employeeProjectRatings20190826.
     *
     * @param employeeProjectRatings20190826 the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmployeeProjectRatings20190826> partialUpdate(EmployeeProjectRatings20190826 employeeProjectRatings20190826) {
        log.debug("Request to partially update EmployeeProjectRatings20190826 : {}", employeeProjectRatings20190826);

        return employeeProjectRatings20190826Repository
            .findById(employeeProjectRatings20190826.getId())
            .map(existingEmployeeProjectRatings20190826 -> {
                if (employeeProjectRatings20190826.getCreatedat() != null) {
                    existingEmployeeProjectRatings20190826.setCreatedat(employeeProjectRatings20190826.getCreatedat());
                }
                if (employeeProjectRatings20190826.getUpdatedat() != null) {
                    existingEmployeeProjectRatings20190826.setUpdatedat(employeeProjectRatings20190826.getUpdatedat());
                }
                if (employeeProjectRatings20190826.getPmquality() != null) {
                    existingEmployeeProjectRatings20190826.setPmquality(employeeProjectRatings20190826.getPmquality());
                }
                if (employeeProjectRatings20190826.getPmqualityContentType() != null) {
                    existingEmployeeProjectRatings20190826.setPmqualityContentType(
                        employeeProjectRatings20190826.getPmqualityContentType()
                    );
                }
                if (employeeProjectRatings20190826.getPmownership() != null) {
                    existingEmployeeProjectRatings20190826.setPmownership(employeeProjectRatings20190826.getPmownership());
                }
                if (employeeProjectRatings20190826.getPmownershipContentType() != null) {
                    existingEmployeeProjectRatings20190826.setPmownershipContentType(
                        employeeProjectRatings20190826.getPmownershipContentType()
                    );
                }
                if (employeeProjectRatings20190826.getPmskill() != null) {
                    existingEmployeeProjectRatings20190826.setPmskill(employeeProjectRatings20190826.getPmskill());
                }
                if (employeeProjectRatings20190826.getPmskillContentType() != null) {
                    existingEmployeeProjectRatings20190826.setPmskillContentType(employeeProjectRatings20190826.getPmskillContentType());
                }
                if (employeeProjectRatings20190826.getPmethics() != null) {
                    existingEmployeeProjectRatings20190826.setPmethics(employeeProjectRatings20190826.getPmethics());
                }
                if (employeeProjectRatings20190826.getPmethicsContentType() != null) {
                    existingEmployeeProjectRatings20190826.setPmethicsContentType(employeeProjectRatings20190826.getPmethicsContentType());
                }
                if (employeeProjectRatings20190826.getPmefficiency() != null) {
                    existingEmployeeProjectRatings20190826.setPmefficiency(employeeProjectRatings20190826.getPmefficiency());
                }
                if (employeeProjectRatings20190826.getPmefficiencyContentType() != null) {
                    existingEmployeeProjectRatings20190826.setPmefficiencyContentType(
                        employeeProjectRatings20190826.getPmefficiencyContentType()
                    );
                }
                if (employeeProjectRatings20190826.getPmfreeze() != null) {
                    existingEmployeeProjectRatings20190826.setPmfreeze(employeeProjectRatings20190826.getPmfreeze());
                }
                if (employeeProjectRatings20190826.getPmfreezeContentType() != null) {
                    existingEmployeeProjectRatings20190826.setPmfreezeContentType(employeeProjectRatings20190826.getPmfreezeContentType());
                }
                if (employeeProjectRatings20190826.getArchfreeze() != null) {
                    existingEmployeeProjectRatings20190826.setArchfreeze(employeeProjectRatings20190826.getArchfreeze());
                }
                if (employeeProjectRatings20190826.getArchfreezeContentType() != null) {
                    existingEmployeeProjectRatings20190826.setArchfreezeContentType(
                        employeeProjectRatings20190826.getArchfreezeContentType()
                    );
                }
                if (employeeProjectRatings20190826.getPmcomment() != null) {
                    existingEmployeeProjectRatings20190826.setPmcomment(employeeProjectRatings20190826.getPmcomment());
                }
                if (employeeProjectRatings20190826.getPmcommentContentType() != null) {
                    existingEmployeeProjectRatings20190826.setPmcommentContentType(
                        employeeProjectRatings20190826.getPmcommentContentType()
                    );
                }
                if (employeeProjectRatings20190826.getArchquality() != null) {
                    existingEmployeeProjectRatings20190826.setArchquality(employeeProjectRatings20190826.getArchquality());
                }
                if (employeeProjectRatings20190826.getArchqualityContentType() != null) {
                    existingEmployeeProjectRatings20190826.setArchqualityContentType(
                        employeeProjectRatings20190826.getArchqualityContentType()
                    );
                }
                if (employeeProjectRatings20190826.getArchownership() != null) {
                    existingEmployeeProjectRatings20190826.setArchownership(employeeProjectRatings20190826.getArchownership());
                }
                if (employeeProjectRatings20190826.getArchownershipContentType() != null) {
                    existingEmployeeProjectRatings20190826.setArchownershipContentType(
                        employeeProjectRatings20190826.getArchownershipContentType()
                    );
                }
                if (employeeProjectRatings20190826.getArchskill() != null) {
                    existingEmployeeProjectRatings20190826.setArchskill(employeeProjectRatings20190826.getArchskill());
                }
                if (employeeProjectRatings20190826.getArchskillContentType() != null) {
                    existingEmployeeProjectRatings20190826.setArchskillContentType(
                        employeeProjectRatings20190826.getArchskillContentType()
                    );
                }
                if (employeeProjectRatings20190826.getArchethics() != null) {
                    existingEmployeeProjectRatings20190826.setArchethics(employeeProjectRatings20190826.getArchethics());
                }
                if (employeeProjectRatings20190826.getArchethicsContentType() != null) {
                    existingEmployeeProjectRatings20190826.setArchethicsContentType(
                        employeeProjectRatings20190826.getArchethicsContentType()
                    );
                }
                if (employeeProjectRatings20190826.getArchefficiency() != null) {
                    existingEmployeeProjectRatings20190826.setArchefficiency(employeeProjectRatings20190826.getArchefficiency());
                }
                if (employeeProjectRatings20190826.getArchefficiencyContentType() != null) {
                    existingEmployeeProjectRatings20190826.setArchefficiencyContentType(
                        employeeProjectRatings20190826.getArchefficiencyContentType()
                    );
                }
                if (employeeProjectRatings20190826.getArchcomment() != null) {
                    existingEmployeeProjectRatings20190826.setArchcomment(employeeProjectRatings20190826.getArchcomment());
                }
                if (employeeProjectRatings20190826.getArchcommentContentType() != null) {
                    existingEmployeeProjectRatings20190826.setArchcommentContentType(
                        employeeProjectRatings20190826.getArchcommentContentType()
                    );
                }
                if (employeeProjectRatings20190826.getProjectarchitectid() != null) {
                    existingEmployeeProjectRatings20190826.setProjectarchitectid(employeeProjectRatings20190826.getProjectarchitectid());
                }
                if (employeeProjectRatings20190826.getProjectmanagerid() != null) {
                    existingEmployeeProjectRatings20190826.setProjectmanagerid(employeeProjectRatings20190826.getProjectmanagerid());
                }
                if (employeeProjectRatings20190826.getEmployeeid() != null) {
                    existingEmployeeProjectRatings20190826.setEmployeeid(employeeProjectRatings20190826.getEmployeeid());
                }
                if (employeeProjectRatings20190826.getProjectcycleid() != null) {
                    existingEmployeeProjectRatings20190826.setProjectcycleid(employeeProjectRatings20190826.getProjectcycleid());
                }

                return existingEmployeeProjectRatings20190826;
            })
            .map(employeeProjectRatings20190826Repository::save);
    }

    /**
     * Get all the employeeProjectRatings20190826s.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeProjectRatings20190826> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeProjectRatings20190826s");
        return employeeProjectRatings20190826Repository.findAll(pageable);
    }

    /**
     * Get one employeeProjectRatings20190826 by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeProjectRatings20190826> findOne(Long id) {
        log.debug("Request to get EmployeeProjectRatings20190826 : {}", id);
        return employeeProjectRatings20190826Repository.findById(id);
    }

    /**
     * Delete the employeeProjectRatings20190826 by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeProjectRatings20190826 : {}", id);
        employeeProjectRatings20190826Repository.deleteById(id);
    }
}
