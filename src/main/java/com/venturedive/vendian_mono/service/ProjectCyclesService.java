package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.ProjectCycles;
import com.venturedive.vendian_mono.repository.ProjectCyclesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProjectCycles}.
 */
@Service
@Transactional
public class ProjectCyclesService {

    private final Logger log = LoggerFactory.getLogger(ProjectCyclesService.class);

    private final ProjectCyclesRepository projectCyclesRepository;

    public ProjectCyclesService(ProjectCyclesRepository projectCyclesRepository) {
        this.projectCyclesRepository = projectCyclesRepository;
    }

    /**
     * Save a projectCycles.
     *
     * @param projectCycles the entity to save.
     * @return the persisted entity.
     */
    public ProjectCycles save(ProjectCycles projectCycles) {
        log.debug("Request to save ProjectCycles : {}", projectCycles);
        return projectCyclesRepository.save(projectCycles);
    }

    /**
     * Update a projectCycles.
     *
     * @param projectCycles the entity to save.
     * @return the persisted entity.
     */
    public ProjectCycles update(ProjectCycles projectCycles) {
        log.debug("Request to update ProjectCycles : {}", projectCycles);
        return projectCyclesRepository.save(projectCycles);
    }

    /**
     * Partially update a projectCycles.
     *
     * @param projectCycles the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProjectCycles> partialUpdate(ProjectCycles projectCycles) {
        log.debug("Request to partially update ProjectCycles : {}", projectCycles);

        return projectCyclesRepository
            .findById(projectCycles.getId())
            .map(existingProjectCycles -> {
                if (projectCycles.getIsactive() != null) {
                    existingProjectCycles.setIsactive(projectCycles.getIsactive());
                }
                if (projectCycles.getCreatedat() != null) {
                    existingProjectCycles.setCreatedat(projectCycles.getCreatedat());
                }
                if (projectCycles.getUpdatedat() != null) {
                    existingProjectCycles.setUpdatedat(projectCycles.getUpdatedat());
                }
                if (projectCycles.getAllowedafterduedateat() != null) {
                    existingProjectCycles.setAllowedafterduedateat(projectCycles.getAllowedafterduedateat());
                }
                if (projectCycles.getDuedate() != null) {
                    existingProjectCycles.setDuedate(projectCycles.getDuedate());
                }
                if (projectCycles.getAuditlogs() != null) {
                    existingProjectCycles.setAuditlogs(projectCycles.getAuditlogs());
                }
                if (projectCycles.getDeletedat() != null) {
                    existingProjectCycles.setDeletedat(projectCycles.getDeletedat());
                }

                return existingProjectCycles;
            })
            .map(projectCyclesRepository::save);
    }

    /**
     * Get all the projectCycles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectCycles> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectCycles");
        return projectCyclesRepository.findAll(pageable);
    }

    /**
     * Get all the projectCycles with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ProjectCycles> findAllWithEagerRelationships(Pageable pageable) {
        return projectCyclesRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one projectCycles by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectCycles> findOne(Long id) {
        log.debug("Request to get ProjectCycles : {}", id);
        return projectCyclesRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the projectCycles by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectCycles : {}", id);
        projectCyclesRepository.deleteById(id);
    }
}
