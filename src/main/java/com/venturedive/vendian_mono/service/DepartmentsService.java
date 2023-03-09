package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.Departments;
import com.venturedive.vendian_mono.repository.DepartmentsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Departments}.
 */
@Service
@Transactional
public class DepartmentsService {

    private final Logger log = LoggerFactory.getLogger(DepartmentsService.class);

    private final DepartmentsRepository departmentsRepository;

    public DepartmentsService(DepartmentsRepository departmentsRepository) {
        this.departmentsRepository = departmentsRepository;
    }

    /**
     * Save a departments.
     *
     * @param departments the entity to save.
     * @return the persisted entity.
     */
    public Departments save(Departments departments) {
        log.debug("Request to save Departments : {}", departments);
        return departmentsRepository.save(departments);
    }

    /**
     * Update a departments.
     *
     * @param departments the entity to save.
     * @return the persisted entity.
     */
    public Departments update(Departments departments) {
        log.debug("Request to update Departments : {}", departments);
        return departmentsRepository.save(departments);
    }

    /**
     * Partially update a departments.
     *
     * @param departments the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Departments> partialUpdate(Departments departments) {
        log.debug("Request to partially update Departments : {}", departments);

        return departmentsRepository
            .findById(departments.getId())
            .map(existingDepartments -> {
                if (departments.getName() != null) {
                    existingDepartments.setName(departments.getName());
                }
                if (departments.getCreatedat() != null) {
                    existingDepartments.setCreatedat(departments.getCreatedat());
                }
                if (departments.getUpdatedat() != null) {
                    existingDepartments.setUpdatedat(departments.getUpdatedat());
                }

                return existingDepartments;
            })
            .map(departmentsRepository::save);
    }

    /**
     * Get all the departments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Departments> findAll(Pageable pageable) {
        log.debug("Request to get all Departments");
        return departmentsRepository.findAll(pageable);
    }

    /**
     * Get one departments by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Departments> findOne(Long id) {
        log.debug("Request to get Departments : {}", id);
        return departmentsRepository.findById(id);
    }

    /**
     * Delete the departments by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Departments : {}", id);
        departmentsRepository.deleteById(id);
    }
}
