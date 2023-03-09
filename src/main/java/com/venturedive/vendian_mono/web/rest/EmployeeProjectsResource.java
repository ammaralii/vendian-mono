package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.EmployeeProjects;
import com.venturedive.vendian_mono.repository.EmployeeProjectsRepository;
import com.venturedive.vendian_mono.service.EmployeeProjectsQueryService;
import com.venturedive.vendian_mono.service.EmployeeProjectsService;
import com.venturedive.vendian_mono.service.criteria.EmployeeProjectsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.EmployeeProjects}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeProjectsResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeProjectsResource.class);

    private static final String ENTITY_NAME = "employeeProjects";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeProjectsService employeeProjectsService;

    private final EmployeeProjectsRepository employeeProjectsRepository;

    private final EmployeeProjectsQueryService employeeProjectsQueryService;

    public EmployeeProjectsResource(
        EmployeeProjectsService employeeProjectsService,
        EmployeeProjectsRepository employeeProjectsRepository,
        EmployeeProjectsQueryService employeeProjectsQueryService
    ) {
        this.employeeProjectsService = employeeProjectsService;
        this.employeeProjectsRepository = employeeProjectsRepository;
        this.employeeProjectsQueryService = employeeProjectsQueryService;
    }

    /**
     * {@code POST  /employee-projects} : Create a new employeeProjects.
     *
     * @param employeeProjects the employeeProjects to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeProjects, or with status {@code 400 (Bad Request)} if the employeeProjects has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-projects")
    public ResponseEntity<EmployeeProjects> createEmployeeProjects(@Valid @RequestBody EmployeeProjects employeeProjects)
        throws URISyntaxException {
        log.debug("REST request to save EmployeeProjects : {}", employeeProjects);
        if (employeeProjects.getId() != null) {
            throw new BadRequestAlertException("A new employeeProjects cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeProjects result = employeeProjectsService.save(employeeProjects);
        return ResponseEntity
            .created(new URI("/api/employee-projects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-projects/:id} : Updates an existing employeeProjects.
     *
     * @param id the id of the employeeProjects to save.
     * @param employeeProjects the employeeProjects to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeProjects,
     * or with status {@code 400 (Bad Request)} if the employeeProjects is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeProjects couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-projects/{id}")
    public ResponseEntity<EmployeeProjects> updateEmployeeProjects(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EmployeeProjects employeeProjects
    ) throws URISyntaxException {
        log.debug("REST request to update EmployeeProjects : {}, {}", id, employeeProjects);
        if (employeeProjects.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeProjects.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeProjectsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmployeeProjects result = employeeProjectsService.update(employeeProjects);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeProjects.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /employee-projects/:id} : Partial updates given fields of an existing employeeProjects, field will ignore if it is null
     *
     * @param id the id of the employeeProjects to save.
     * @param employeeProjects the employeeProjects to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeProjects,
     * or with status {@code 400 (Bad Request)} if the employeeProjects is not valid,
     * or with status {@code 404 (Not Found)} if the employeeProjects is not found,
     * or with status {@code 500 (Internal Server Error)} if the employeeProjects couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/employee-projects/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmployeeProjects> partialUpdateEmployeeProjects(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EmployeeProjects employeeProjects
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmployeeProjects partially : {}, {}", id, employeeProjects);
        if (employeeProjects.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeProjects.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeProjectsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmployeeProjects> result = employeeProjectsService.partialUpdate(employeeProjects);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeProjects.getId().toString())
        );
    }

    /**
     * {@code GET  /employee-projects} : get all the employeeProjects.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeProjects in body.
     */
    @GetMapping("/employee-projects")
    public ResponseEntity<List<EmployeeProjects>> getAllEmployeeProjects(
        EmployeeProjectsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EmployeeProjects by criteria: {}", criteria);
        Page<EmployeeProjects> page = employeeProjectsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-projects/count} : count all the employeeProjects.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employee-projects/count")
    public ResponseEntity<Long> countEmployeeProjects(EmployeeProjectsCriteria criteria) {
        log.debug("REST request to count EmployeeProjects by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeeProjectsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employee-projects/:id} : get the "id" employeeProjects.
     *
     * @param id the id of the employeeProjects to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeProjects, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-projects/{id}")
    public ResponseEntity<EmployeeProjects> getEmployeeProjects(@PathVariable Long id) {
        log.debug("REST request to get EmployeeProjects : {}", id);
        Optional<EmployeeProjects> employeeProjects = employeeProjectsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeProjects);
    }

    /**
     * {@code DELETE  /employee-projects/:id} : delete the "id" employeeProjects.
     *
     * @param id the id of the employeeProjects to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-projects/{id}")
    public ResponseEntity<Void> deleteEmployeeProjects(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeProjects : {}", id);
        employeeProjectsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
