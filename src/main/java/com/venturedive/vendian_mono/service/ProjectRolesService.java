package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.ProjectRoles;
import com.venturedive.vendian_mono.repository.ProjectRolesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProjectRoles}.
 */
@Service
@Transactional
public class ProjectRolesService {

    private final Logger log = LoggerFactory.getLogger(ProjectRolesService.class);

    private final ProjectRolesRepository projectRolesRepository;

    public ProjectRolesService(ProjectRolesRepository projectRolesRepository) {
        this.projectRolesRepository = projectRolesRepository;
    }

    /**
     * Save a projectRoles.
     *
     * @param projectRoles the entity to save.
     * @return the persisted entity.
     */
    public ProjectRoles save(ProjectRoles projectRoles) {
        log.debug("Request to save ProjectRoles : {}", projectRoles);
        return projectRolesRepository.save(projectRoles);
    }

    /**
     * Update a projectRoles.
     *
     * @param projectRoles the entity to save.
     * @return the persisted entity.
     */
    public ProjectRoles update(ProjectRoles projectRoles) {
        log.debug("Request to update ProjectRoles : {}", projectRoles);
        return projectRolesRepository.save(projectRoles);
    }

    /**
     * Partially update a projectRoles.
     *
     * @param projectRoles the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProjectRoles> partialUpdate(ProjectRoles projectRoles) {
        log.debug("Request to partially update ProjectRoles : {}", projectRoles);

        return projectRolesRepository
            .findById(projectRoles.getId())
            .map(existingProjectRoles -> {
                if (projectRoles.getRole() != null) {
                    existingProjectRoles.setRole(projectRoles.getRole());
                }
                if (projectRoles.getCreatedat() != null) {
                    existingProjectRoles.setCreatedat(projectRoles.getCreatedat());
                }
                if (projectRoles.getUpdatedat() != null) {
                    existingProjectRoles.setUpdatedat(projectRoles.getUpdatedat());
                }
                if (projectRoles.getIsleadership() != null) {
                    existingProjectRoles.setIsleadership(projectRoles.getIsleadership());
                }

                return existingProjectRoles;
            })
            .map(projectRolesRepository::save);
    }

    /**
     * Get all the projectRoles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectRoles> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectRoles");
        return projectRolesRepository.findAll(pageable);
    }

    /**
     * Get one projectRoles by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectRoles> findOne(Long id) {
        log.debug("Request to get ProjectRoles : {}", id);
        return projectRolesRepository.findById(id);
    }

    /**
     * Delete the projectRoles by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectRoles : {}", id);
        projectRolesRepository.deleteById(id);
    }
}
