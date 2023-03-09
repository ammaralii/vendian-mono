package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.EmployeeProjectRoles;
import com.venturedive.vendian_mono.repository.EmployeeProjectRolesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmployeeProjectRoles}.
 */
@Service
@Transactional
public class EmployeeProjectRolesService {

    private final Logger log = LoggerFactory.getLogger(EmployeeProjectRolesService.class);

    private final EmployeeProjectRolesRepository employeeProjectRolesRepository;

    public EmployeeProjectRolesService(EmployeeProjectRolesRepository employeeProjectRolesRepository) {
        this.employeeProjectRolesRepository = employeeProjectRolesRepository;
    }

    /**
     * Save a employeeProjectRoles.
     *
     * @param employeeProjectRoles the entity to save.
     * @return the persisted entity.
     */
    public EmployeeProjectRoles save(EmployeeProjectRoles employeeProjectRoles) {
        log.debug("Request to save EmployeeProjectRoles : {}", employeeProjectRoles);
        return employeeProjectRolesRepository.save(employeeProjectRoles);
    }

    /**
     * Update a employeeProjectRoles.
     *
     * @param employeeProjectRoles the entity to save.
     * @return the persisted entity.
     */
    public EmployeeProjectRoles update(EmployeeProjectRoles employeeProjectRoles) {
        log.debug("Request to update EmployeeProjectRoles : {}", employeeProjectRoles);
        return employeeProjectRolesRepository.save(employeeProjectRoles);
    }

    /**
     * Partially update a employeeProjectRoles.
     *
     * @param employeeProjectRoles the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmployeeProjectRoles> partialUpdate(EmployeeProjectRoles employeeProjectRoles) {
        log.debug("Request to partially update EmployeeProjectRoles : {}", employeeProjectRoles);

        return employeeProjectRolesRepository
            .findById(employeeProjectRoles.getId())
            .map(existingEmployeeProjectRoles -> {
                if (employeeProjectRoles.getStatus() != null) {
                    existingEmployeeProjectRoles.setStatus(employeeProjectRoles.getStatus());
                }
                if (employeeProjectRoles.getCreatedat() != null) {
                    existingEmployeeProjectRoles.setCreatedat(employeeProjectRoles.getCreatedat());
                }
                if (employeeProjectRoles.getUpdatedat() != null) {
                    existingEmployeeProjectRoles.setUpdatedat(employeeProjectRoles.getUpdatedat());
                }

                return existingEmployeeProjectRoles;
            })
            .map(employeeProjectRolesRepository::save);
    }

    /**
     * Get all the employeeProjectRoles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeProjectRoles> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeProjectRoles");
        return employeeProjectRolesRepository.findAll(pageable);
    }

    /**
     * Get one employeeProjectRoles by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeProjectRoles> findOne(Long id) {
        log.debug("Request to get EmployeeProjectRoles : {}", id);
        return employeeProjectRolesRepository.findById(id);
    }

    /**
     * Delete the employeeProjectRoles by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeProjectRoles : {}", id);
        employeeProjectRolesRepository.deleteById(id);
    }
}
