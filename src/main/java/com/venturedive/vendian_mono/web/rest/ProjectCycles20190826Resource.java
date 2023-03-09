package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.ProjectCycles20190826;
import com.venturedive.vendian_mono.repository.ProjectCycles20190826Repository;
import com.venturedive.vendian_mono.service.ProjectCycles20190826QueryService;
import com.venturedive.vendian_mono.service.ProjectCycles20190826Service;
import com.venturedive.vendian_mono.service.criteria.ProjectCycles20190826Criteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.ProjectCycles20190826}.
 */
@RestController
@RequestMapping("/api")
public class ProjectCycles20190826Resource {

    private final Logger log = LoggerFactory.getLogger(ProjectCycles20190826Resource.class);

    private static final String ENTITY_NAME = "projectCycles20190826";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectCycles20190826Service projectCycles20190826Service;

    private final ProjectCycles20190826Repository projectCycles20190826Repository;

    private final ProjectCycles20190826QueryService projectCycles20190826QueryService;

    public ProjectCycles20190826Resource(
        ProjectCycles20190826Service projectCycles20190826Service,
        ProjectCycles20190826Repository projectCycles20190826Repository,
        ProjectCycles20190826QueryService projectCycles20190826QueryService
    ) {
        this.projectCycles20190826Service = projectCycles20190826Service;
        this.projectCycles20190826Repository = projectCycles20190826Repository;
        this.projectCycles20190826QueryService = projectCycles20190826QueryService;
    }

    /**
     * {@code POST  /project-cycles-20190826-s} : Create a new projectCycles20190826.
     *
     * @param projectCycles20190826 the projectCycles20190826 to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectCycles20190826, or with status {@code 400 (Bad Request)} if the projectCycles20190826 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-cycles-20190826-s")
    public ResponseEntity<ProjectCycles20190826> createProjectCycles20190826(@RequestBody ProjectCycles20190826 projectCycles20190826)
        throws URISyntaxException {
        log.debug("REST request to save ProjectCycles20190826 : {}", projectCycles20190826);
        if (projectCycles20190826.getId() != null) {
            throw new BadRequestAlertException("A new projectCycles20190826 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectCycles20190826 result = projectCycles20190826Service.save(projectCycles20190826);
        return ResponseEntity
            .created(new URI("/api/project-cycles-20190826-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-cycles-20190826-s/:id} : Updates an existing projectCycles20190826.
     *
     * @param id the id of the projectCycles20190826 to save.
     * @param projectCycles20190826 the projectCycles20190826 to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectCycles20190826,
     * or with status {@code 400 (Bad Request)} if the projectCycles20190826 is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectCycles20190826 couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-cycles-20190826-s/{id}")
    public ResponseEntity<ProjectCycles20190826> updateProjectCycles20190826(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProjectCycles20190826 projectCycles20190826
    ) throws URISyntaxException {
        log.debug("REST request to update ProjectCycles20190826 : {}, {}", id, projectCycles20190826);
        if (projectCycles20190826.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectCycles20190826.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectCycles20190826Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProjectCycles20190826 result = projectCycles20190826Service.update(projectCycles20190826);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, projectCycles20190826.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /project-cycles-20190826-s/:id} : Partial updates given fields of an existing projectCycles20190826, field will ignore if it is null
     *
     * @param id the id of the projectCycles20190826 to save.
     * @param projectCycles20190826 the projectCycles20190826 to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectCycles20190826,
     * or with status {@code 400 (Bad Request)} if the projectCycles20190826 is not valid,
     * or with status {@code 404 (Not Found)} if the projectCycles20190826 is not found,
     * or with status {@code 500 (Internal Server Error)} if the projectCycles20190826 couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/project-cycles-20190826-s/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProjectCycles20190826> partialUpdateProjectCycles20190826(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProjectCycles20190826 projectCycles20190826
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProjectCycles20190826 partially : {}, {}", id, projectCycles20190826);
        if (projectCycles20190826.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectCycles20190826.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectCycles20190826Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProjectCycles20190826> result = projectCycles20190826Service.partialUpdate(projectCycles20190826);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, projectCycles20190826.getId().toString())
        );
    }

    /**
     * {@code GET  /project-cycles-20190826-s} : get all the projectCycles20190826s.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectCycles20190826s in body.
     */
    @GetMapping("/project-cycles-20190826-s")
    public ResponseEntity<List<ProjectCycles20190826>> getAllProjectCycles20190826s(
        ProjectCycles20190826Criteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ProjectCycles20190826s by criteria: {}", criteria);
        Page<ProjectCycles20190826> page = projectCycles20190826QueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-cycles-20190826-s/count} : count all the projectCycles20190826s.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/project-cycles-20190826-s/count")
    public ResponseEntity<Long> countProjectCycles20190826s(ProjectCycles20190826Criteria criteria) {
        log.debug("REST request to count ProjectCycles20190826s by criteria: {}", criteria);
        return ResponseEntity.ok().body(projectCycles20190826QueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /project-cycles-20190826-s/:id} : get the "id" projectCycles20190826.
     *
     * @param id the id of the projectCycles20190826 to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectCycles20190826, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-cycles-20190826-s/{id}")
    public ResponseEntity<ProjectCycles20190826> getProjectCycles20190826(@PathVariable Long id) {
        log.debug("REST request to get ProjectCycles20190826 : {}", id);
        Optional<ProjectCycles20190826> projectCycles20190826 = projectCycles20190826Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectCycles20190826);
    }

    /**
     * {@code DELETE  /project-cycles-20190826-s/:id} : delete the "id" projectCycles20190826.
     *
     * @param id the id of the projectCycles20190826 to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-cycles-20190826-s/{id}")
    public ResponseEntity<Void> deleteProjectCycles20190826(@PathVariable Long id) {
        log.debug("REST request to delete ProjectCycles20190826 : {}", id);
        projectCycles20190826Service.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
