package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.EmployeeProjects;
import com.venturedive.vendian_mono.repository.EmployeeProjectsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmployeeProjects}.
 */
@Service
@Transactional
public class EmployeeProjectsService {

    private final Logger log = LoggerFactory.getLogger(EmployeeProjectsService.class);

    private final EmployeeProjectsRepository employeeProjectsRepository;

    public EmployeeProjectsService(EmployeeProjectsRepository employeeProjectsRepository) {
        this.employeeProjectsRepository = employeeProjectsRepository;
    }

    /**
     * Save a employeeProjects.
     *
     * @param employeeProjects the entity to save.
     * @return the persisted entity.
     */
    public EmployeeProjects save(EmployeeProjects employeeProjects) {
        log.debug("Request to save EmployeeProjects : {}", employeeProjects);
        return employeeProjectsRepository.save(employeeProjects);
    }

    /**
     * Update a employeeProjects.
     *
     * @param employeeProjects the entity to save.
     * @return the persisted entity.
     */
    public EmployeeProjects update(EmployeeProjects employeeProjects) {
        log.debug("Request to update EmployeeProjects : {}", employeeProjects);
        return employeeProjectsRepository.save(employeeProjects);
    }

    /**
     * Partially update a employeeProjects.
     *
     * @param employeeProjects the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmployeeProjects> partialUpdate(EmployeeProjects employeeProjects) {
        log.debug("Request to partially update EmployeeProjects : {}", employeeProjects);

        return employeeProjectsRepository
            .findById(employeeProjects.getId())
            .map(existingEmployeeProjects -> {
                if (employeeProjects.getStatus() != null) {
                    existingEmployeeProjects.setStatus(employeeProjects.getStatus());
                }
                if (employeeProjects.getType() != null) {
                    existingEmployeeProjects.setType(employeeProjects.getType());
                }
                if (employeeProjects.getStartdate() != null) {
                    existingEmployeeProjects.setStartdate(employeeProjects.getStartdate());
                }
                if (employeeProjects.getEnddate() != null) {
                    existingEmployeeProjects.setEnddate(employeeProjects.getEnddate());
                }
                if (employeeProjects.getAllocation() != null) {
                    existingEmployeeProjects.setAllocation(employeeProjects.getAllocation());
                }
                if (employeeProjects.getBilled() != null) {
                    existingEmployeeProjects.setBilled(employeeProjects.getBilled());
                }
                if (employeeProjects.getCreatedat() != null) {
                    existingEmployeeProjects.setCreatedat(employeeProjects.getCreatedat());
                }
                if (employeeProjects.getUpdatedat() != null) {
                    existingEmployeeProjects.setUpdatedat(employeeProjects.getUpdatedat());
                }
                if (employeeProjects.getRoleid() != null) {
                    existingEmployeeProjects.setRoleid(employeeProjects.getRoleid());
                }
                if (employeeProjects.getNotes() != null) {
                    existingEmployeeProjects.setNotes(employeeProjects.getNotes());
                }
                if (employeeProjects.getExtendedenddate() != null) {
                    existingEmployeeProjects.setExtendedenddate(employeeProjects.getExtendedenddate());
                }
                if (employeeProjects.getProbability() != null) {
                    existingEmployeeProjects.setProbability(employeeProjects.getProbability());
                }

                return existingEmployeeProjects;
            })
            .map(employeeProjectsRepository::save);
    }

    /**
     * Get all the employeeProjects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeProjects> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeProjects");
        return employeeProjectsRepository.findAll(pageable);
    }

    /**
     * Get one employeeProjects by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeProjects> findOne(Long id) {
        log.debug("Request to get EmployeeProjects : {}", id);
        return employeeProjectsRepository.findById(id);
    }

    /**
     * Delete the employeeProjects by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeProjects : {}", id);
        employeeProjectsRepository.deleteById(id);
    }
}
