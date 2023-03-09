package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.Projects;
import com.venturedive.vendian_mono.repository.ProjectsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Projects}.
 */
@Service
@Transactional
public class ProjectsService {

    private final Logger log = LoggerFactory.getLogger(ProjectsService.class);

    private final ProjectsRepository projectsRepository;

    public ProjectsService(ProjectsRepository projectsRepository) {
        this.projectsRepository = projectsRepository;
    }

    /**
     * Save a projects.
     *
     * @param projects the entity to save.
     * @return the persisted entity.
     */
    public Projects save(Projects projects) {
        log.debug("Request to save Projects : {}", projects);
        return projectsRepository.save(projects);
    }

    /**
     * Update a projects.
     *
     * @param projects the entity to save.
     * @return the persisted entity.
     */
    public Projects update(Projects projects) {
        log.debug("Request to update Projects : {}", projects);
        return projectsRepository.save(projects);
    }

    /**
     * Partially update a projects.
     *
     * @param projects the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Projects> partialUpdate(Projects projects) {
        log.debug("Request to partially update Projects : {}", projects);

        return projectsRepository
            .findById(projects.getId())
            .map(existingProjects -> {
                if (projects.getName() != null) {
                    existingProjects.setName(projects.getName());
                }
                if (projects.getIsactive() != null) {
                    existingProjects.setIsactive(projects.getIsactive());
                }
                if (projects.getIsdelete() != null) {
                    existingProjects.setIsdelete(projects.getIsdelete());
                }
                if (projects.getStartdate() != null) {
                    existingProjects.setStartdate(projects.getStartdate());
                }
                if (projects.getEnddate() != null) {
                    existingProjects.setEnddate(projects.getEnddate());
                }
                if (projects.getColorcode() != null) {
                    existingProjects.setColorcode(projects.getColorcode());
                }
                if (projects.getCreatedat() != null) {
                    existingProjects.setCreatedat(projects.getCreatedat());
                }
                if (projects.getUpdatedat() != null) {
                    existingProjects.setUpdatedat(projects.getUpdatedat());
                }
                if (projects.getDeliverymanagerid() != null) {
                    existingProjects.setDeliverymanagerid(projects.getDeliverymanagerid());
                }
                if (projects.getArchitectid() != null) {
                    existingProjects.setArchitectid(projects.getArchitectid());
                }
                if (projects.getIsdeleted() != null) {
                    existingProjects.setIsdeleted(projects.getIsdeleted());
                }
                if (projects.getApprovedresources() != null) {
                    existingProjects.setApprovedresources(projects.getApprovedresources());
                }
                if (projects.getReleaseat() != null) {
                    existingProjects.setReleaseat(projects.getReleaseat());
                }

                return existingProjects;
            })
            .map(projectsRepository::save);
    }

    /**
     * Get all the projects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Projects> findAll(Pageable pageable) {
        log.debug("Request to get all Projects");
        return projectsRepository.findAll(pageable);
    }

    /**
     * Get one projects by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Projects> findOne(Long id) {
        log.debug("Request to get Projects : {}", id);
        return projectsRepository.findById(id);
    }

    /**
     * Delete the projects by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Projects : {}", id);
        projectsRepository.deleteById(id);
    }
}
