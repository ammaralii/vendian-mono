package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.EmployeeEmergencyContacts;
import com.venturedive.vendian_mono.repository.EmployeeEmergencyContactsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmployeeEmergencyContacts}.
 */
@Service
@Transactional
public class EmployeeEmergencyContactsService {

    private final Logger log = LoggerFactory.getLogger(EmployeeEmergencyContactsService.class);

    private final EmployeeEmergencyContactsRepository employeeEmergencyContactsRepository;

    public EmployeeEmergencyContactsService(EmployeeEmergencyContactsRepository employeeEmergencyContactsRepository) {
        this.employeeEmergencyContactsRepository = employeeEmergencyContactsRepository;
    }

    /**
     * Save a employeeEmergencyContacts.
     *
     * @param employeeEmergencyContacts the entity to save.
     * @return the persisted entity.
     */
    public EmployeeEmergencyContacts save(EmployeeEmergencyContacts employeeEmergencyContacts) {
        log.debug("Request to save EmployeeEmergencyContacts : {}", employeeEmergencyContacts);
        return employeeEmergencyContactsRepository.save(employeeEmergencyContacts);
    }

    /**
     * Update a employeeEmergencyContacts.
     *
     * @param employeeEmergencyContacts the entity to save.
     * @return the persisted entity.
     */
    public EmployeeEmergencyContacts update(EmployeeEmergencyContacts employeeEmergencyContacts) {
        log.debug("Request to update EmployeeEmergencyContacts : {}", employeeEmergencyContacts);
        return employeeEmergencyContactsRepository.save(employeeEmergencyContacts);
    }

    /**
     * Partially update a employeeEmergencyContacts.
     *
     * @param employeeEmergencyContacts the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmployeeEmergencyContacts> partialUpdate(EmployeeEmergencyContacts employeeEmergencyContacts) {
        log.debug("Request to partially update EmployeeEmergencyContacts : {}", employeeEmergencyContacts);

        return employeeEmergencyContactsRepository
            .findById(employeeEmergencyContacts.getId())
            .map(existingEmployeeEmergencyContacts -> {
                if (employeeEmergencyContacts.getFullname() != null) {
                    existingEmployeeEmergencyContacts.setFullname(employeeEmergencyContacts.getFullname());
                }
                if (employeeEmergencyContacts.getRelationship() != null) {
                    existingEmployeeEmergencyContacts.setRelationship(employeeEmergencyContacts.getRelationship());
                }
                if (employeeEmergencyContacts.getContactno() != null) {
                    existingEmployeeEmergencyContacts.setContactno(employeeEmergencyContacts.getContactno());
                }
                if (employeeEmergencyContacts.getCreatedat() != null) {
                    existingEmployeeEmergencyContacts.setCreatedat(employeeEmergencyContacts.getCreatedat());
                }
                if (employeeEmergencyContacts.getUpdatedat() != null) {
                    existingEmployeeEmergencyContacts.setUpdatedat(employeeEmergencyContacts.getUpdatedat());
                }

                return existingEmployeeEmergencyContacts;
            })
            .map(employeeEmergencyContactsRepository::save);
    }

    /**
     * Get all the employeeEmergencyContacts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeEmergencyContacts> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeEmergencyContacts");
        return employeeEmergencyContactsRepository.findAll(pageable);
    }

    /**
     * Get one employeeEmergencyContacts by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeEmergencyContacts> findOne(Long id) {
        log.debug("Request to get EmployeeEmergencyContacts : {}", id);
        return employeeEmergencyContactsRepository.findById(id);
    }

    /**
     * Delete the employeeEmergencyContacts by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeEmergencyContacts : {}", id);
        employeeEmergencyContactsRepository.deleteById(id);
    }
}
