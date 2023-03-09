package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.ProjectLeadership;
import com.venturedive.vendian_mono.repository.ProjectLeadershipRepository;
import com.venturedive.vendian_mono.service.ProjectLeadershipQueryService;
import com.venturedive.vendian_mono.service.ProjectLeadershipService;
import com.venturedive.vendian_mono.service.criteria.ProjectLeadershipCriteria;
import com.venturedive.vendian_mono.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.ProjectLeadership}.
 */
@RestController
@RequestMapping("/api")
public class ProjectLeadershipResource {

    private final Logger log = LoggerFactory.getLogger(ProjectLeadershipResource.class);

    private static final String ENTITY_NAME = "projectLeadership";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectLeadershipService projectLeadershipService;

    private final ProjectLeadershipRepository projectLeadershipRepository;

    private final ProjectLeadershipQueryService projectLeadershipQueryService;

    public ProjectLeadershipResource(
        ProjectLeadershipService projectLeadershipService,
        ProjectLeadershipRepository projectLeadershipRepository,
        ProjectLeadershipQueryService projectLeadershipQueryService
    ) {
        this.projectLeadershipService = projectLeadershipService;
        this.projectLeadershipRepository = projectLeadershipRepository;
        this.projectLeadershipQueryService = projectLeadershipQueryService;
    }

    /**
     * {@code POST  /project-leaderships} : Create a new projectLeadership.
     *
     * @param projectLeadership the projectLeadership to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectLeadership, or with status {@code 400 (Bad Request)} if the projectLeadership has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-leaderships")
    public ResponseEntity<ProjectLeadership> createProjectLeadership(@RequestBody ProjectLeadership projectLeadership)
        throws URISyntaxException {
        log.debug("REST request to save ProjectLeadership : {}", projectLeadership);
        if (projectLeadership.getId() != null) {
            throw new BadRequestAlertException("A new projectLeadership cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectLeadership result = projectLeadershipService.save(projectLeadership);
        return ResponseEntity
            .created(new URI("/api/project-leaderships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-leaderships/:id} : Updates an existing projectLeadership.
     *
     * @param id the id of the projectLeadership to save.
     * @param projectLeadership the projectLeadership to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectLeadership,
     * or with status {@code 400 (Bad Request)} if the projectLeadership is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectLeadership couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-leaderships/{id}")
    public ResponseEntity<ProjectLeadership> updateProjectLeadership(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProjectLeadership projectLeadership
    ) throws URISyntaxException {
        log.debug("REST request to update ProjectLeadership : {}, {}", id, projectLeadership);
        if (projectLeadership.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectLeadership.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectLeadershipRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProjectLeadership result = projectLeadershipService.update(projectLeadership);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, projectLeadership.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /project-leaderships/:id} : Partial updates given fields of an existing projectLeadership, field will ignore if it is null
     *
     * @param id the id of the projectLeadership to save.
     * @param projectLeadership the projectLeadership to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectLeadership,
     * or with status {@code 400 (Bad Request)} if the projectLeadership is not valid,
     * or with status {@code 404 (Not Found)} if the projectLeadership is not found,
     * or with status {@code 500 (Internal Server Error)} if the projectLeadership couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/project-leaderships/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProjectLeadership> partialUpdateProjectLeadership(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProjectLeadership projectLeadership
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProjectLeadership partially : {}, {}", id, projectLeadership);
        if (projectLeadership.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectLeadership.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectLeadershipRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProjectLeadership> result = projectLeadershipService.partialUpdate(projectLeadership);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, projectLeadership.getId().toString())
        );
    }

    /**
     * {@code GET  /project-leaderships} : get all the projectLeaderships.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectLeaderships in body.
     */
    @GetMapping("/project-leaderships")
    public ResponseEntity<List<ProjectLeadership>> getAllProjectLeaderships(
        ProjectLeadershipCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ProjectLeaderships by criteria: {}", criteria);
        Page<ProjectLeadership> page = projectLeadershipQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-leaderships/count} : count all the projectLeaderships.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/project-leaderships/count")
    public ResponseEntity<Long> countProjectLeaderships(ProjectLeadershipCriteria criteria) {
        log.debug("REST request to count ProjectLeaderships by criteria: {}", criteria);
        return ResponseEntity.ok().body(projectLeadershipQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /project-leaderships/:id} : get the "id" projectLeadership.
     *
     * @param id the id of the projectLeadership to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectLeadership, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-leaderships/{id}")
    public ResponseEntity<ProjectLeadership> getProjectLeadership(@PathVariable Long id) {
        log.debug("REST request to get ProjectLeadership : {}", id);
        Optional<ProjectLeadership> projectLeadership = projectLeadershipService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectLeadership);
    }

    /**
     * {@code DELETE  /project-leaderships/:id} : delete the "id" projectLeadership.
     *
     * @param id the id of the projectLeadership to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-leaderships/{id}")
    public ResponseEntity<Void> deleteProjectLeadership(@PathVariable Long id) {
        log.debug("REST request to delete ProjectLeadership : {}", id);
        projectLeadershipService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
