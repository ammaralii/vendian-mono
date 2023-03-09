package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.WorkLogs;
import com.venturedive.vendian_mono.repository.WorkLogsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link WorkLogs}.
 */
@Service
@Transactional
public class WorkLogsService {

    private final Logger log = LoggerFactory.getLogger(WorkLogsService.class);

    private final WorkLogsRepository workLogsRepository;

    public WorkLogsService(WorkLogsRepository workLogsRepository) {
        this.workLogsRepository = workLogsRepository;
    }

    /**
     * Save a workLogs.
     *
     * @param workLogs the entity to save.
     * @return the persisted entity.
     */
    public WorkLogs save(WorkLogs workLogs) {
        log.debug("Request to save WorkLogs : {}", workLogs);
        return workLogsRepository.save(workLogs);
    }

    /**
     * Update a workLogs.
     *
     * @param workLogs the entity to save.
     * @return the persisted entity.
     */
    public WorkLogs update(WorkLogs workLogs) {
        log.debug("Request to update WorkLogs : {}", workLogs);
        return workLogsRepository.save(workLogs);
    }

    /**
     * Partially update a workLogs.
     *
     * @param workLogs the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<WorkLogs> partialUpdate(WorkLogs workLogs) {
        log.debug("Request to partially update WorkLogs : {}", workLogs);

        return workLogsRepository
            .findById(workLogs.getId())
            .map(existingWorkLogs -> {
                if (workLogs.getDate() != null) {
                    existingWorkLogs.setDate(workLogs.getDate());
                }
                if (workLogs.getMood() != null) {
                    existingWorkLogs.setMood(workLogs.getMood());
                }
                if (workLogs.getCreatedat() != null) {
                    existingWorkLogs.setCreatedat(workLogs.getCreatedat());
                }
                if (workLogs.getUpdatedat() != null) {
                    existingWorkLogs.setUpdatedat(workLogs.getUpdatedat());
                }

                return existingWorkLogs;
            })
            .map(workLogsRepository::save);
    }

    /**
     * Get all the workLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkLogs> findAll(Pageable pageable) {
        log.debug("Request to get all WorkLogs");
        return workLogsRepository.findAll(pageable);
    }

    /**
     * Get one workLogs by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WorkLogs> findOne(Long id) {
        log.debug("Request to get WorkLogs : {}", id);
        return workLogsRepository.findById(id);
    }

    /**
     * Delete the workLogs by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkLogs : {}", id);
        workLogsRepository.deleteById(id);
    }
}
