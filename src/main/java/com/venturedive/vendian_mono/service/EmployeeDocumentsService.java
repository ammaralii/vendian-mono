package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.EmployeeDocuments;
import com.venturedive.vendian_mono.repository.EmployeeDocumentsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmployeeDocuments}.
 */
@Service
@Transactional
public class EmployeeDocumentsService {

    private final Logger log = LoggerFactory.getLogger(EmployeeDocumentsService.class);

    private final EmployeeDocumentsRepository employeeDocumentsRepository;

    public EmployeeDocumentsService(EmployeeDocumentsRepository employeeDocumentsRepository) {
        this.employeeDocumentsRepository = employeeDocumentsRepository;
    }

    /**
     * Save a employeeDocuments.
     *
     * @param employeeDocuments the entity to save.
     * @return the persisted entity.
     */
    public EmployeeDocuments save(EmployeeDocuments employeeDocuments) {
        log.debug("Request to save EmployeeDocuments : {}", employeeDocuments);
        return employeeDocumentsRepository.save(employeeDocuments);
    }

    /**
     * Update a employeeDocuments.
     *
     * @param employeeDocuments the entity to save.
     * @return the persisted entity.
     */
    public EmployeeDocuments update(EmployeeDocuments employeeDocuments) {
        log.debug("Request to update EmployeeDocuments : {}", employeeDocuments);
        return employeeDocumentsRepository.save(employeeDocuments);
    }

    /**
     * Partially update a employeeDocuments.
     *
     * @param employeeDocuments the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmployeeDocuments> partialUpdate(EmployeeDocuments employeeDocuments) {
        log.debug("Request to partially update EmployeeDocuments : {}", employeeDocuments);

        return employeeDocumentsRepository
            .findById(employeeDocuments.getId())
            .map(existingEmployeeDocuments -> {
                if (employeeDocuments.getCreatedat() != null) {
                    existingEmployeeDocuments.setCreatedat(employeeDocuments.getCreatedat());
                }
                if (employeeDocuments.getUpdatedat() != null) {
                    existingEmployeeDocuments.setUpdatedat(employeeDocuments.getUpdatedat());
                }

                return existingEmployeeDocuments;
            })
            .map(employeeDocumentsRepository::save);
    }

    /**
     * Get all the employeeDocuments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeDocuments> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeDocuments");
        return employeeDocumentsRepository.findAll(pageable);
    }

    /**
     * Get one employeeDocuments by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeDocuments> findOne(Long id) {
        log.debug("Request to get EmployeeDocuments : {}", id);
        return employeeDocumentsRepository.findById(id);
    }

    /**
     * Delete the employeeDocuments by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeDocuments : {}", id);
        employeeDocumentsRepository.deleteById(id);
    }
}
