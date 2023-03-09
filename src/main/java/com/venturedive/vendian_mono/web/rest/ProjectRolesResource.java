package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.ProjectRoles;
import com.venturedive.vendian_mono.repository.ProjectRolesRepository;
import com.venturedive.vendian_mono.service.ProjectRolesQueryService;
import com.venturedive.vendian_mono.service.ProjectRolesService;
import com.venturedive.vendian_mono.service.criteria.ProjectRolesCriteria;
import com.venturedive.vendian_mono.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.ProjectRoles}.
 */
@RestController
@RequestMapping("/api")
public class ProjectRolesResource {

    private final Logger log = LoggerFactory.getLogger(ProjectRolesResource.class);

    private static final String ENTITY_NAME = "projectRoles";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectRolesService projectRolesService;

    private final ProjectRolesRepository projectRolesRepository;

    private final ProjectRolesQueryService projectRolesQueryService;

    public ProjectRolesResource(
        ProjectRolesService projectRolesService,
        ProjectRolesRepository projectRolesRepository,
        ProjectRolesQueryService projectRolesQueryService
    ) {
        this.projectRolesService = projectRolesService;
        this.projectRolesRepository = projectRolesRepository;
        this.projectRolesQueryService = projectRolesQueryService;
    }

    /**
     * {@code POST  /project-roles} : Create a new projectRoles.
     *
     * @param projectRoles the projectRoles to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectRoles, or with status {@code 400 (Bad Request)} if the projectRoles has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-roles")
    public ResponseEntity<ProjectRoles> createProjectRoles(@Valid @RequestBody ProjectRoles projectRoles) throws URISyntaxException {
        log.debug("REST request to save ProjectRoles : {}", projectRoles);
        if (projectRoles.getId() != null) {
            throw new BadRequestAlertException("A new projectRoles cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectRoles result = projectRolesService.save(projectRoles);
        return ResponseEntity
            .created(new URI("/api/project-roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-roles/:id} : Updates an existing projectRoles.
     *
     * @param id the id of the projectRoles to save.
     * @param projectRoles the projectRoles to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectRoles,
     * or with status {@code 400 (Bad Request)} if the projectRoles is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectRoles couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-roles/{id}")
    public ResponseEntity<ProjectRoles> updateProjectRoles(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProjectRoles projectRoles
    ) throws URISyntaxException {
        log.debug("REST request to update ProjectRoles : {}, {}", id, projectRoles);
        if (projectRoles.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectRoles.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectRolesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProjectRoles result = projectRolesService.update(projectRoles);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, projectRoles.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /project-roles/:id} : Partial updates given fields of an existing projectRoles, field will ignore if it is null
     *
     * @param id the id of the projectRoles to save.
     * @param projectRoles the projectRoles to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectRoles,
     * or with status {@code 400 (Bad Request)} if the projectRoles is not valid,
     * or with status {@code 404 (Not Found)} if the projectRoles is not found,
     * or with status {@code 500 (Internal Server Error)} if the projectRoles couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/project-roles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProjectRoles> partialUpdateProjectRoles(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProjectRoles projectRoles
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProjectRoles partially : {}, {}", id, projectRoles);
        if (projectRoles.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectRoles.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectRolesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProjectRoles> result = projectRolesService.partialUpdate(projectRoles);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, projectRoles.getId().toString())
        );
    }

    /**
     * {@code GET  /project-roles} : get all the projectRoles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectRoles in body.
     */
    @GetMapping("/project-roles")
    public ResponseEntity<List<ProjectRoles>> getAllProjectRoles(
        ProjectRolesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ProjectRoles by criteria: {}", criteria);
        Page<ProjectRoles> page = projectRolesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-roles/count} : count all the projectRoles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/project-roles/count")
    public ResponseEntity<Long> countProjectRoles(ProjectRolesCriteria criteria) {
        log.debug("REST request to count ProjectRoles by criteria: {}", criteria);
        return ResponseEntity.ok().body(projectRolesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /project-roles/:id} : get the "id" projectRoles.
     *
     * @param id the id of the projectRoles to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectRoles, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-roles/{id}")
    public ResponseEntity<ProjectRoles> getProjectRoles(@PathVariable Long id) {
        log.debug("REST request to get ProjectRoles : {}", id);
        Optional<ProjectRoles> projectRoles = projectRolesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectRoles);
    }

    /**
     * {@code DELETE  /project-roles/:id} : delete the "id" projectRoles.
     *
     * @param id the id of the projectRoles to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-roles/{id}")
    public ResponseEntity<Void> deleteProjectRoles(@PathVariable Long id) {
        log.debug("REST request to delete ProjectRoles : {}", id);
        projectRolesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
