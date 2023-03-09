package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.EmployeeBirthdays;
import com.venturedive.vendian_mono.repository.EmployeeBirthdaysRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmployeeBirthdays}.
 */
@Service
@Transactional
public class EmployeeBirthdaysService {

    private final Logger log = LoggerFactory.getLogger(EmployeeBirthdaysService.class);

    private final EmployeeBirthdaysRepository employeeBirthdaysRepository;

    public EmployeeBirthdaysService(EmployeeBirthdaysRepository employeeBirthdaysRepository) {
        this.employeeBirthdaysRepository = employeeBirthdaysRepository;
    }

    /**
     * Save a employeeBirthdays.
     *
     * @param employeeBirthdays the entity to save.
     * @return the persisted entity.
     */
    public EmployeeBirthdays save(EmployeeBirthdays employeeBirthdays) {
        log.debug("Request to save EmployeeBirthdays : {}", employeeBirthdays);
        return employeeBirthdaysRepository.save(employeeBirthdays);
    }

    /**
     * Update a employeeBirthdays.
     *
     * @param employeeBirthdays the entity to save.
     * @return the persisted entity.
     */
    public EmployeeBirthdays update(EmployeeBirthdays employeeBirthdays) {
        log.debug("Request to update EmployeeBirthdays : {}", employeeBirthdays);
        return employeeBirthdaysRepository.save(employeeBirthdays);
    }

    /**
     * Partially update a employeeBirthdays.
     *
     * @param employeeBirthdays the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmployeeBirthdays> partialUpdate(EmployeeBirthdays employeeBirthdays) {
        log.debug("Request to partially update EmployeeBirthdays : {}", employeeBirthdays);

        return employeeBirthdaysRepository
            .findById(employeeBirthdays.getId())
            .map(existingEmployeeBirthdays -> {
                if (employeeBirthdays.getBirthdayDetails() != null) {
                    existingEmployeeBirthdays.setBirthdayDetails(employeeBirthdays.getBirthdayDetails());
                }
                if (employeeBirthdays.getYear() != null) {
                    existingEmployeeBirthdays.setYear(employeeBirthdays.getYear());
                }
                if (employeeBirthdays.getCreatedat() != null) {
                    existingEmployeeBirthdays.setCreatedat(employeeBirthdays.getCreatedat());
                }
                if (employeeBirthdays.getUpdatedat() != null) {
                    existingEmployeeBirthdays.setUpdatedat(employeeBirthdays.getUpdatedat());
                }

                return existingEmployeeBirthdays;
            })
            .map(employeeBirthdaysRepository::save);
    }

    /**
     * Get all the employeeBirthdays.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeBirthdays> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeBirthdays");
        return employeeBirthdaysRepository.findAll(pageable);
    }

    /**
     * Get one employeeBirthdays by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeBirthdays> findOne(Long id) {
        log.debug("Request to get EmployeeBirthdays : {}", id);
        return employeeBirthdaysRepository.findById(id);
    }

    /**
     * Delete the employeeBirthdays by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeBirthdays : {}", id);
        employeeBirthdaysRepository.deleteById(id);
    }
}
