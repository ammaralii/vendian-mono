package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.EmployeeAddresses;
import com.venturedive.vendian_mono.repository.EmployeeAddressesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmployeeAddresses}.
 */
@Service
@Transactional
public class EmployeeAddressesService {

    private final Logger log = LoggerFactory.getLogger(EmployeeAddressesService.class);

    private final EmployeeAddressesRepository employeeAddressesRepository;

    public EmployeeAddressesService(EmployeeAddressesRepository employeeAddressesRepository) {
        this.employeeAddressesRepository = employeeAddressesRepository;
    }

    /**
     * Save a employeeAddresses.
     *
     * @param employeeAddresses the entity to save.
     * @return the persisted entity.
     */
    public EmployeeAddresses save(EmployeeAddresses employeeAddresses) {
        log.debug("Request to save EmployeeAddresses : {}", employeeAddresses);
        return employeeAddressesRepository.save(employeeAddresses);
    }

    /**
     * Update a employeeAddresses.
     *
     * @param employeeAddresses the entity to save.
     * @return the persisted entity.
     */
    public EmployeeAddresses update(EmployeeAddresses employeeAddresses) {
        log.debug("Request to update EmployeeAddresses : {}", employeeAddresses);
        return employeeAddressesRepository.save(employeeAddresses);
    }

    /**
     * Partially update a employeeAddresses.
     *
     * @param employeeAddresses the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmployeeAddresses> partialUpdate(EmployeeAddresses employeeAddresses) {
        log.debug("Request to partially update EmployeeAddresses : {}", employeeAddresses);

        return employeeAddressesRepository
            .findById(employeeAddresses.getId())
            .map(existingEmployeeAddresses -> {
                if (employeeAddresses.getIsdeleted() != null) {
                    existingEmployeeAddresses.setIsdeleted(employeeAddresses.getIsdeleted());
                }
                if (employeeAddresses.getCreatedat() != null) {
                    existingEmployeeAddresses.setCreatedat(employeeAddresses.getCreatedat());
                }
                if (employeeAddresses.getUpdatedat() != null) {
                    existingEmployeeAddresses.setUpdatedat(employeeAddresses.getUpdatedat());
                }
                if (employeeAddresses.getType() != null) {
                    existingEmployeeAddresses.setType(employeeAddresses.getType());
                }

                return existingEmployeeAddresses;
            })
            .map(employeeAddressesRepository::save);
    }

    /**
     * Get all the employeeAddresses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeAddresses> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeAddresses");
        return employeeAddressesRepository.findAll(pageable);
    }

    /**
     * Get one employeeAddresses by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeAddresses> findOne(Long id) {
        log.debug("Request to get EmployeeAddresses : {}", id);
        return employeeAddressesRepository.findById(id);
    }

    /**
     * Delete the employeeAddresses by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeAddresses : {}", id);
        employeeAddressesRepository.deleteById(id);
    }
}
