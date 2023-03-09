package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.ProjectLeadership;
import com.venturedive.vendian_mono.repository.ProjectLeadershipRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProjectLeadership}.
 */
@Service
@Transactional
public class ProjectLeadershipService {

    private final Logger log = LoggerFactory.getLogger(ProjectLeadershipService.class);

    private final ProjectLeadershipRepository projectLeadershipRepository;

    public ProjectLeadershipService(ProjectLeadershipRepository projectLeadershipRepository) {
        this.projectLeadershipRepository = projectLeadershipRepository;
    }

    /**
     * Save a projectLeadership.
     *
     * @param projectLeadership the entity to save.
     * @return the persisted entity.
     */
    public ProjectLeadership save(ProjectLeadership projectLeadership) {
        log.debug("Request to save ProjectLeadership : {}", projectLeadership);
        return projectLeadershipRepository.save(projectLeadership);
    }

    /**
     * Update a projectLeadership.
     *
     * @param projectLeadership the entity to save.
     * @return the persisted entity.
     */
    public ProjectLeadership update(ProjectLeadership projectLeadership) {
        log.debug("Request to update ProjectLeadership : {}", projectLeadership);
        return projectLeadershipRepository.save(projectLeadership);
    }

    /**
     * Partially update a projectLeadership.
     *
     * @param projectLeadership the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProjectLeadership> partialUpdate(ProjectLeadership projectLeadership) {
        log.debug("Request to partially update ProjectLeadership : {}", projectLeadership);

        return projectLeadershipRepository
            .findById(projectLeadership.getId())
            .map(existingProjectLeadership -> {
                if (projectLeadership.getCreatedat() != null) {
                    existingProjectLeadership.setCreatedat(projectLeadership.getCreatedat());
                }
                if (projectLeadership.getUpdatedat() != null) {
                    existingProjectLeadership.setUpdatedat(projectLeadership.getUpdatedat());
                }

                return existingProjectLeadership;
            })
            .map(projectLeadershipRepository::save);
    }

    /**
     * Get all the projectLeaderships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectLeadership> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectLeaderships");
        return projectLeadershipRepository.findAll(pageable);
    }

    /**
     * Get one projectLeadership by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectLeadership> findOne(Long id) {
        log.debug("Request to get ProjectLeadership : {}", id);
        return projectLeadershipRepository.findById(id);
    }

    /**
     * Delete the projectLeadership by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectLeadership : {}", id);
        projectLeadershipRepository.deleteById(id);
    }
}
