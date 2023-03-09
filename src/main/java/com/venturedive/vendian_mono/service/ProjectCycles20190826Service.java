package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.ProjectCycles20190826;
import com.venturedive.vendian_mono.repository.ProjectCycles20190826Repository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProjectCycles20190826}.
 */
@Service
@Transactional
public class ProjectCycles20190826Service {

    private final Logger log = LoggerFactory.getLogger(ProjectCycles20190826Service.class);

    private final ProjectCycles20190826Repository projectCycles20190826Repository;

    public ProjectCycles20190826Service(ProjectCycles20190826Repository projectCycles20190826Repository) {
        this.projectCycles20190826Repository = projectCycles20190826Repository;
    }

    /**
     * Save a projectCycles20190826.
     *
     * @param projectCycles20190826 the entity to save.
     * @return the persisted entity.
     */
    public ProjectCycles20190826 save(ProjectCycles20190826 projectCycles20190826) {
        log.debug("Request to save ProjectCycles20190826 : {}", projectCycles20190826);
        return projectCycles20190826Repository.save(projectCycles20190826);
    }

    /**
     * Update a projectCycles20190826.
     *
     * @param projectCycles20190826 the entity to save.
     * @return the persisted entity.
     */
    public ProjectCycles20190826 update(ProjectCycles20190826 projectCycles20190826) {
        log.debug("Request to update ProjectCycles20190826 : {}", projectCycles20190826);
        return projectCycles20190826Repository.save(projectCycles20190826);
    }

    /**
     * Partially update a projectCycles20190826.
     *
     * @param projectCycles20190826 the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProjectCycles20190826> partialUpdate(ProjectCycles20190826 projectCycles20190826) {
        log.debug("Request to partially update ProjectCycles20190826 : {}", projectCycles20190826);

        return projectCycles20190826Repository
            .findById(projectCycles20190826.getId())
            .map(existingProjectCycles20190826 -> {
                if (projectCycles20190826.getIsactive() != null) {
                    existingProjectCycles20190826.setIsactive(projectCycles20190826.getIsactive());
                }
                if (projectCycles20190826.getCreatedat() != null) {
                    existingProjectCycles20190826.setCreatedat(projectCycles20190826.getCreatedat());
                }
                if (projectCycles20190826.getUpdatedat() != null) {
                    existingProjectCycles20190826.setUpdatedat(projectCycles20190826.getUpdatedat());
                }
                if (projectCycles20190826.getPerformancecycleid() != null) {
                    existingProjectCycles20190826.setPerformancecycleid(projectCycles20190826.getPerformancecycleid());
                }
                if (projectCycles20190826.getProjectid() != null) {
                    existingProjectCycles20190826.setProjectid(projectCycles20190826.getProjectid());
                }
                if (projectCycles20190826.getAllowedafterduedateby() != null) {
                    existingProjectCycles20190826.setAllowedafterduedateby(projectCycles20190826.getAllowedafterduedateby());
                }
                if (projectCycles20190826.getAllowedafterduedateat() != null) {
                    existingProjectCycles20190826.setAllowedafterduedateat(projectCycles20190826.getAllowedafterduedateat());
                }
                if (projectCycles20190826.getDuedate() != null) {
                    existingProjectCycles20190826.setDuedate(projectCycles20190826.getDuedate());
                }

                return existingProjectCycles20190826;
            })
            .map(projectCycles20190826Repository::save);
    }

    /**
     * Get all the projectCycles20190826s.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectCycles20190826> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectCycles20190826s");
        return projectCycles20190826Repository.findAll(pageable);
    }

    /**
     * Get one projectCycles20190826 by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectCycles20190826> findOne(Long id) {
        log.debug("Request to get ProjectCycles20190826 : {}", id);
        return projectCycles20190826Repository.findById(id);
    }

    /**
     * Delete the projectCycles20190826 by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectCycles20190826 : {}", id);
        projectCycles20190826Repository.deleteById(id);
    }
}
