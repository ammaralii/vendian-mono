package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.ProjectCycles;
import com.venturedive.vendian_mono.repository.ProjectCyclesRepository;
import com.venturedive.vendian_mono.service.ProjectCyclesQueryService;
import com.venturedive.vendian_mono.service.ProjectCyclesService;
import com.venturedive.vendian_mono.service.criteria.ProjectCyclesCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.ProjectCycles}.
 */
@RestController
@RequestMapping("/api")
public class ProjectCyclesResource {

    private final Logger log = LoggerFactory.getLogger(ProjectCyclesResource.class);

    private static final String ENTITY_NAME = "projectCycles";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectCyclesService projectCyclesService;

    private final ProjectCyclesRepository projectCyclesRepository;

    private final ProjectCyclesQueryService projectCyclesQueryService;

    public ProjectCyclesResource(
        ProjectCyclesService projectCyclesService,
        ProjectCyclesRepository projectCyclesRepository,
        ProjectCyclesQueryService projectCyclesQueryService
    ) {
        this.projectCyclesService = projectCyclesService;
        this.projectCyclesRepository = projectCyclesRepository;
        this.projectCyclesQueryService = projectCyclesQueryService;
    }

    /**
     * {@code POST  /project-cycles} : Create a new projectCycles.
     *
     * @param projectCycles the projectCycles to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectCycles, or with status {@code 400 (Bad Request)} if the projectCycles has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-cycles")
    public ResponseEntity<ProjectCycles> createProjectCycles(@RequestBody ProjectCycles projectCycles) throws URISyntaxException {
        log.debug("REST request to save ProjectCycles : {}", projectCycles);
        if (projectCycles.getId() != null) {
            throw new BadRequestAlertException("A new projectCycles cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectCycles result = projectCyclesService.save(projectCycles);
        return ResponseEntity
            .created(new URI("/api/project-cycles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-cycles/:id} : Updates an existing projectCycles.
     *
     * @param id the id of the projectCycles to save.
     * @param projectCycles the projectCycles to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectCycles,
     * or with status {@code 400 (Bad Request)} if the projectCycles is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectCycles couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-cycles/{id}")
    public ResponseEntity<ProjectCycles> updateProjectCycles(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProjectCycles projectCycles
    ) throws URISyntaxException {
        log.debug("REST request to update ProjectCycles : {}, {}", id, projectCycles);
        if (projectCycles.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectCycles.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectCyclesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProjectCycles result = projectCyclesService.update(projectCycles);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, projectCycles.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /project-cycles/:id} : Partial updates given fields of an existing projectCycles, field will ignore if it is null
     *
     * @param id the id of the projectCycles to save.
     * @param projectCycles the projectCycles to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectCycles,
     * or with status {@code 400 (Bad Request)} if the projectCycles is not valid,
     * or with status {@code 404 (Not Found)} if the projectCycles is not found,
     * or with status {@code 500 (Internal Server Error)} if the projectCycles couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/project-cycles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProjectCycles> partialUpdateProjectCycles(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProjectCycles projectCycles
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProjectCycles partially : {}, {}", id, projectCycles);
        if (projectCycles.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectCycles.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectCyclesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProjectCycles> result = projectCyclesService.partialUpdate(projectCycles);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, projectCycles.getId().toString())
        );
    }

    /**
     * {@code GET  /project-cycles} : get all the projectCycles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectCycles in body.
     */
    @GetMapping("/project-cycles")
    public ResponseEntity<List<ProjectCycles>> getAllProjectCycles(
        ProjectCyclesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ProjectCycles by criteria: {}", criteria);
        Page<ProjectCycles> page = projectCyclesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-cycles/count} : count all the projectCycles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/project-cycles/count")
    public ResponseEntity<Long> countProjectCycles(ProjectCyclesCriteria criteria) {
        log.debug("REST request to count ProjectCycles by criteria: {}", criteria);
        return ResponseEntity.ok().body(projectCyclesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /project-cycles/:id} : get the "id" projectCycles.
     *
     * @param id the id of the projectCycles to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectCycles, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-cycles/{id}")
    public ResponseEntity<ProjectCycles> getProjectCycles(@PathVariable Long id) {
        log.debug("REST request to get ProjectCycles : {}", id);
        Optional<ProjectCycles> projectCycles = projectCyclesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectCycles);
    }

    /**
     * {@code DELETE  /project-cycles/:id} : delete the "id" projectCycles.
     *
     * @param id the id of the projectCycles to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-cycles/{id}")
    public ResponseEntity<Void> deleteProjectCycles(@PathVariable Long id) {
        log.debug("REST request to delete ProjectCycles : {}", id);
        projectCyclesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
