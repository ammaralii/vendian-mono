package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.EmployeeContacts;
import com.venturedive.vendian_mono.repository.EmployeeContactsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmployeeContacts}.
 */
@Service
@Transactional
public class EmployeeContactsService {

    private final Logger log = LoggerFactory.getLogger(EmployeeContactsService.class);

    private final EmployeeContactsRepository employeeContactsRepository;

    public EmployeeContactsService(EmployeeContactsRepository employeeContactsRepository) {
        this.employeeContactsRepository = employeeContactsRepository;
    }

    /**
     * Save a employeeContacts.
     *
     * @param employeeContacts the entity to save.
     * @return the persisted entity.
     */
    public EmployeeContacts save(EmployeeContacts employeeContacts) {
        log.debug("Request to save EmployeeContacts : {}", employeeContacts);
        return employeeContactsRepository.save(employeeContacts);
    }

    /**
     * Update a employeeContacts.
     *
     * @param employeeContacts the entity to save.
     * @return the persisted entity.
     */
    public EmployeeContacts update(EmployeeContacts employeeContacts) {
        log.debug("Request to update EmployeeContacts : {}", employeeContacts);
        return employeeContactsRepository.save(employeeContacts);
    }

    /**
     * Partially update a employeeContacts.
     *
     * @param employeeContacts the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmployeeContacts> partialUpdate(EmployeeContacts employeeContacts) {
        log.debug("Request to partially update EmployeeContacts : {}", employeeContacts);

        return employeeContactsRepository
            .findById(employeeContacts.getId())
            .map(existingEmployeeContacts -> {
                if (employeeContacts.getNumber() != null) {
                    existingEmployeeContacts.setNumber(employeeContacts.getNumber());
                }
                if (employeeContacts.getNumberContentType() != null) {
                    existingEmployeeContacts.setNumberContentType(employeeContacts.getNumberContentType());
                }
                if (employeeContacts.getType() != null) {
                    existingEmployeeContacts.setType(employeeContacts.getType());
                }
                if (employeeContacts.getCreatedat() != null) {
                    existingEmployeeContacts.setCreatedat(employeeContacts.getCreatedat());
                }
                if (employeeContacts.getUpdatedat() != null) {
                    existingEmployeeContacts.setUpdatedat(employeeContacts.getUpdatedat());
                }

                return existingEmployeeContacts;
            })
            .map(employeeContactsRepository::save);
    }

    /**
     * Get all the employeeContacts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeContacts> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeContacts");
        return employeeContactsRepository.findAll(pageable);
    }

    /**
     * Get one employeeContacts by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeContacts> findOne(Long id) {
        log.debug("Request to get EmployeeContacts : {}", id);
        return employeeContactsRepository.findById(id);
    }

    /**
     * Delete the employeeContacts by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeContacts : {}", id);
        employeeContactsRepository.deleteById(id);
    }
}
