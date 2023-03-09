package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.repository.EmployeesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Employees}.
 */
@Service
@Transactional
public class EmployeesService {

    private final Logger log = LoggerFactory.getLogger(EmployeesService.class);

    private final EmployeesRepository employeesRepository;

    public EmployeesService(EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }

    /**
     * Save a employees.
     *
     * @param employees the entity to save.
     * @return the persisted entity.
     */
    public Employees save(Employees employees) {
        log.debug("Request to save Employees : {}", employees);
        return employeesRepository.save(employees);
    }

    /**
     * Update a employees.
     *
     * @param employees the entity to save.
     * @return the persisted entity.
     */
    public Employees update(Employees employees) {
        log.debug("Request to update Employees : {}", employees);
        return employeesRepository.save(employees);
    }

    /**
     * Partially update a employees.
     *
     * @param employees the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Employees> partialUpdate(Employees employees) {
        log.debug("Request to partially update Employees : {}", employees);

        return employeesRepository
            .findById(employees.getId())
            .map(existingEmployees -> {
                if (employees.getFirstname() != null) {
                    existingEmployees.setFirstname(employees.getFirstname());
                }
                if (employees.getLastname() != null) {
                    existingEmployees.setLastname(employees.getLastname());
                }
                if (employees.getPhonenumber() != null) {
                    existingEmployees.setPhonenumber(employees.getPhonenumber());
                }
                if (employees.getDateofbirth() != null) {
                    existingEmployees.setDateofbirth(employees.getDateofbirth());
                }
                if (employees.getEmail() != null) {
                    existingEmployees.setEmail(employees.getEmail());
                }
                if (employees.getSkype() != null) {
                    existingEmployees.setSkype(employees.getSkype());
                }
                if (employees.getEmployeeDesignation() != null) {
                    existingEmployees.setEmployeeDesignation(employees.getEmployeeDesignation());
                }
                if (employees.getJoiningdate() != null) {
                    existingEmployees.setJoiningdate(employees.getJoiningdate());
                }
                if (employees.getArea() != null) {
                    existingEmployees.setArea(employees.getArea());
                }
                if (employees.getLeavingdate() != null) {
                    existingEmployees.setLeavingdate(employees.getLeavingdate());
                }
                if (employees.getNotes() != null) {
                    existingEmployees.setNotes(employees.getNotes());
                }
                if (employees.getIsactive() != null) {
                    existingEmployees.setIsactive(employees.getIsactive());
                }
                if (employees.getGoogleid() != null) {
                    existingEmployees.setGoogleid(employees.getGoogleid());
                }
                if (employees.getOracleid() != null) {
                    existingEmployees.setOracleid(employees.getOracleid());
                }
                if (employees.getDeptt() != null) {
                    existingEmployees.setDeptt(employees.getDeptt());
                }
                if (employees.getCreatedat() != null) {
                    existingEmployees.setCreatedat(employees.getCreatedat());
                }
                if (employees.getUpdatedat() != null) {
                    existingEmployees.setUpdatedat(employees.getUpdatedat());
                }
                if (employees.getGenderid() != null) {
                    existingEmployees.setGenderid(employees.getGenderid());
                }
                if (employees.getOnprobation() != null) {
                    existingEmployees.setOnprobation(employees.getOnprobation());
                }
                if (employees.getEmployeeCompetency() != null) {
                    existingEmployees.setEmployeeCompetency(employees.getEmployeeCompetency());
                }
                if (employees.getResourcetype() != null) {
                    existingEmployees.setResourcetype(employees.getResourcetype());
                }
                if (employees.getGrade() != null) {
                    existingEmployees.setGrade(employees.getGrade());
                }
                if (employees.getSubgrade() != null) {
                    existingEmployees.setSubgrade(employees.getSubgrade());
                }
                if (employees.getImageurl() != null) {
                    existingEmployees.setImageurl(employees.getImageurl());
                }
                if (employees.getResignationdate() != null) {
                    existingEmployees.setResignationdate(employees.getResignationdate());
                }
                if (employees.getGraduationdate() != null) {
                    existingEmployees.setGraduationdate(employees.getGraduationdate());
                }
                if (employees.getCareerstartdate() != null) {
                    existingEmployees.setCareerstartdate(employees.getCareerstartdate());
                }
                if (employees.getExternalexpyears() != null) {
                    existingEmployees.setExternalexpyears(employees.getExternalexpyears());
                }
                if (employees.getExternalexpmonths() != null) {
                    existingEmployees.setExternalexpmonths(employees.getExternalexpmonths());
                }
                if (employees.getPlaceofbirth() != null) {
                    existingEmployees.setPlaceofbirth(employees.getPlaceofbirth());
                }
                if (employees.getHireddate() != null) {
                    existingEmployees.setHireddate(employees.getHireddate());
                }
                if (employees.getLasttrackingupdate() != null) {
                    existingEmployees.setLasttrackingupdate(employees.getLasttrackingupdate());
                }
                if (employees.getMiddlename() != null) {
                    existingEmployees.setMiddlename(employees.getMiddlename());
                }
                if (employees.getGrosssalary() != null) {
                    existingEmployees.setGrosssalary(employees.getGrosssalary());
                }
                if (employees.getGrosssalaryContentType() != null) {
                    existingEmployees.setGrosssalaryContentType(employees.getGrosssalaryContentType());
                }
                if (employees.getFuelallowance() != null) {
                    existingEmployees.setFuelallowance(employees.getFuelallowance());
                }
                if (employees.getFuelallowanceContentType() != null) {
                    existingEmployees.setFuelallowanceContentType(employees.getFuelallowanceContentType());
                }
                if (employees.getMobilenumber() != null) {
                    existingEmployees.setMobilenumber(employees.getMobilenumber());
                }
                if (employees.getResignationtype() != null) {
                    existingEmployees.setResignationtype(employees.getResignationtype());
                }
                if (employees.getPrimaryreasonforleaving() != null) {
                    existingEmployees.setPrimaryreasonforleaving(employees.getPrimaryreasonforleaving());
                }
                if (employees.getSecondaryreasonforleaving() != null) {
                    existingEmployees.setSecondaryreasonforleaving(employees.getSecondaryreasonforleaving());
                }
                if (employees.getNoticeperiodduration() != null) {
                    existingEmployees.setNoticeperiodduration(employees.getNoticeperiodduration());
                }
                if (employees.getNoticeperiodserved() != null) {
                    existingEmployees.setNoticeperiodserved(employees.getNoticeperiodserved());
                }
                if (employees.getProbationperiodduration() != null) {
                    existingEmployees.setProbationperiodduration(employees.getProbationperiodduration());
                }

                return existingEmployees;
            })
            .map(employeesRepository::save);
    }

    /**
     * Get all the employees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Employees> findAll(Pageable pageable) {
        log.debug("Request to get all Employees");
        return employeesRepository.findAll(pageable);
    }

    /**
     * Get all the employees with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Employees> findAllWithEagerRelationships(Pageable pageable) {
        return employeesRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one employees by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Employees> findOne(Long id) {
        log.debug("Request to get Employees : {}", id);
        return employeesRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the employees by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Employees : {}", id);
        employeesRepository.deleteById(id);
    }
}
