package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.EmployeeRoles;
import com.venturedive.vendian_mono.repository.EmployeeRolesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmployeeRoles}.
 */
@Service
@Transactional
public class EmployeeRolesService {

    private final Logger log = LoggerFactory.getLogger(EmployeeRolesService.class);

    private final EmployeeRolesRepository employeeRolesRepository;

    public EmployeeRolesService(EmployeeRolesRepository employeeRolesRepository) {
        this.employeeRolesRepository = employeeRolesRepository;
    }

    /**
     * Save a employeeRoles.
     *
     * @param employeeRoles the entity to save.
     * @return the persisted entity.
     */
    public EmployeeRoles save(EmployeeRoles employeeRoles) {
        log.debug("Request to save EmployeeRoles : {}", employeeRoles);
        return employeeRolesRepository.save(employeeRoles);
    }

    /**
     * Update a employeeRoles.
     *
     * @param employeeRoles the entity to save.
     * @return the persisted entity.
     */
    public EmployeeRoles update(EmployeeRoles employeeRoles) {
        log.debug("Request to update EmployeeRoles : {}", employeeRoles);
        return employeeRolesRepository.save(employeeRoles);
    }

    /**
     * Partially update a employeeRoles.
     *
     * @param employeeRoles the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmployeeRoles> partialUpdate(EmployeeRoles employeeRoles) {
        log.debug("Request to partially update EmployeeRoles : {}", employeeRoles);

        return employeeRolesRepository
            .findById(employeeRoles.getId())
            .map(existingEmployeeRoles -> {
                if (employeeRoles.getCreatedat() != null) {
                    existingEmployeeRoles.setCreatedat(employeeRoles.getCreatedat());
                }
                if (employeeRoles.getUpdatedat() != null) {
                    existingEmployeeRoles.setUpdatedat(employeeRoles.getUpdatedat());
                }
                if (employeeRoles.getEmployeeid() != null) {
                    existingEmployeeRoles.setEmployeeid(employeeRoles.getEmployeeid());
                }

                return existingEmployeeRoles;
            })
            .map(employeeRolesRepository::save);
    }

    /**
     * Get all the employeeRoles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeRoles> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeRoles");
        return employeeRolesRepository.findAll(pageable);
    }

    /**
     * Get one employeeRoles by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeRoles> findOne(Long id) {
        log.debug("Request to get EmployeeRoles : {}", id);
        return employeeRolesRepository.findById(id);
    }

    /**
     * Delete the employeeRoles by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeRoles : {}", id);
        employeeRolesRepository.deleteById(id);
    }
}
