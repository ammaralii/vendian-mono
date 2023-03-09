package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.EmployeeProfileHistoryLogs;
import com.venturedive.vendian_mono.repository.EmployeeProfileHistoryLogsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmployeeProfileHistoryLogs}.
 */
@Service
@Transactional
public class EmployeeProfileHistoryLogsService {

    private final Logger log = LoggerFactory.getLogger(EmployeeProfileHistoryLogsService.class);

    private final EmployeeProfileHistoryLogsRepository employeeProfileHistoryLogsRepository;

    public EmployeeProfileHistoryLogsService(EmployeeProfileHistoryLogsRepository employeeProfileHistoryLogsRepository) {
        this.employeeProfileHistoryLogsRepository = employeeProfileHistoryLogsRepository;
    }

    /**
     * Save a employeeProfileHistoryLogs.
     *
     * @param employeeProfileHistoryLogs the entity to save.
     * @return the persisted entity.
     */
    public EmployeeProfileHistoryLogs save(EmployeeProfileHistoryLogs employeeProfileHistoryLogs) {
        log.debug("Request to save EmployeeProfileHistoryLogs : {}", employeeProfileHistoryLogs);
        return employeeProfileHistoryLogsRepository.save(employeeProfileHistoryLogs);
    }

    /**
     * Update a employeeProfileHistoryLogs.
     *
     * @param employeeProfileHistoryLogs the entity to save.
     * @return the persisted entity.
     */
    public EmployeeProfileHistoryLogs update(EmployeeProfileHistoryLogs employeeProfileHistoryLogs) {
        log.debug("Request to update EmployeeProfileHistoryLogs : {}", employeeProfileHistoryLogs);
        return employeeProfileHistoryLogsRepository.save(employeeProfileHistoryLogs);
    }

    /**
     * Partially update a employeeProfileHistoryLogs.
     *
     * @param employeeProfileHistoryLogs the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmployeeProfileHistoryLogs> partialUpdate(EmployeeProfileHistoryLogs employeeProfileHistoryLogs) {
        log.debug("Request to partially update EmployeeProfileHistoryLogs : {}", employeeProfileHistoryLogs);

        return employeeProfileHistoryLogsRepository
            .findById(employeeProfileHistoryLogs.getId())
            .map(existingEmployeeProfileHistoryLogs -> {
                if (employeeProfileHistoryLogs.getTablename() != null) {
                    existingEmployeeProfileHistoryLogs.setTablename(employeeProfileHistoryLogs.getTablename());
                }
                if (employeeProfileHistoryLogs.getRowid() != null) {
                    existingEmployeeProfileHistoryLogs.setRowid(employeeProfileHistoryLogs.getRowid());
                }
                if (employeeProfileHistoryLogs.getEventtype() != null) {
                    existingEmployeeProfileHistoryLogs.setEventtype(employeeProfileHistoryLogs.getEventtype());
                }
                if (employeeProfileHistoryLogs.getFields() != null) {
                    existingEmployeeProfileHistoryLogs.setFields(employeeProfileHistoryLogs.getFields());
                }
                if (employeeProfileHistoryLogs.getFieldsContentType() != null) {
                    existingEmployeeProfileHistoryLogs.setFieldsContentType(employeeProfileHistoryLogs.getFieldsContentType());
                }
                if (employeeProfileHistoryLogs.getUpdatedbyid() != null) {
                    existingEmployeeProfileHistoryLogs.setUpdatedbyid(employeeProfileHistoryLogs.getUpdatedbyid());
                }
                if (employeeProfileHistoryLogs.getActivityid() != null) {
                    existingEmployeeProfileHistoryLogs.setActivityid(employeeProfileHistoryLogs.getActivityid());
                }
                if (employeeProfileHistoryLogs.getCreatedat() != null) {
                    existingEmployeeProfileHistoryLogs.setCreatedat(employeeProfileHistoryLogs.getCreatedat());
                }
                if (employeeProfileHistoryLogs.getUpdatedat() != null) {
                    existingEmployeeProfileHistoryLogs.setUpdatedat(employeeProfileHistoryLogs.getUpdatedat());
                }
                if (employeeProfileHistoryLogs.getCategory() != null) {
                    existingEmployeeProfileHistoryLogs.setCategory(employeeProfileHistoryLogs.getCategory());
                }

                return existingEmployeeProfileHistoryLogs;
            })
            .map(employeeProfileHistoryLogsRepository::save);
    }

    /**
     * Get all the employeeProfileHistoryLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeProfileHistoryLogs> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeProfileHistoryLogs");
        return employeeProfileHistoryLogsRepository.findAll(pageable);
    }

    /**
     * Get one employeeProfileHistoryLogs by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeProfileHistoryLogs> findOne(Long id) {
        log.debug("Request to get EmployeeProfileHistoryLogs : {}", id);
        return employeeProfileHistoryLogsRepository.findById(id);
    }

    /**
     * Delete the employeeProfileHistoryLogs by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeProfileHistoryLogs : {}", id);
        employeeProfileHistoryLogsRepository.deleteById(id);
    }
}
