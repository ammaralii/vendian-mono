package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.EmployeeProjectRoles;
import com.venturedive.vendian_mono.repository.EmployeeProjectRolesRepository;
import com.venturedive.vendian_mono.service.EmployeeProjectRolesQueryService;
import com.venturedive.vendian_mono.service.EmployeeProjectRolesService;
import com.venturedive.vendian_mono.service.criteria.EmployeeProjectRolesCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.EmployeeProjectRoles}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeProjectRolesResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeProjectRolesResource.class);

    private static final String ENTITY_NAME = "employeeProjectRoles";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeProjectRolesService employeeProjectRolesService;

    private final EmployeeProjectRolesRepository employeeProjectRolesRepository;

    private final EmployeeProjectRolesQueryService employeeProjectRolesQueryService;

    public EmployeeProjectRolesResource(
        EmployeeProjectRolesService employeeProjectRolesService,
        EmployeeProjectRolesRepository employeeProjectRolesRepository,
        EmployeeProjectRolesQueryService employeeProjectRolesQueryService
    ) {
        this.employeeProjectRolesService = employeeProjectRolesService;
        this.employeeProjectRolesRepository = employeeProjectRolesRepository;
        this.employeeProjectRolesQueryService = employeeProjectRolesQueryService;
    }

    /**
     * {@code POST  /employee-project-roles} : Create a new employeeProjectRoles.
     *
     * @param employeeProjectRoles the employeeProjectRoles to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeProjectRoles, or with status {@code 400 (Bad Request)} if the employeeProjectRoles has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-project-roles")
    public ResponseEntity<EmployeeProjectRoles> createEmployeeProjectRoles(@Valid @RequestBody EmployeeProjectRoles employeeProjectRoles)
        throws URISyntaxException {
        log.debug("REST request to save EmployeeProjectRoles : {}", employeeProjectRoles);
        if (employeeProjectRoles.getId() != null) {
            throw new BadRequestAlertException("A new employeeProjectRoles cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeProjectRoles result = employeeProjectRolesService.save(employeeProjectRoles);
        return ResponseEntity
            .created(new URI("/api/employee-project-roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-project-roles/:id} : Updates an existing employeeProjectRoles.
     *
     * @param id the id of the employeeProjectRoles to save.
     * @param employeeProjectRoles the employeeProjectRoles to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeProjectRoles,
     * or with status {@code 400 (Bad Request)} if the employeeProjectRoles is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeProjectRoles couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-project-roles/{id}")
    public ResponseEntity<EmployeeProjectRoles> updateEmployeeProjectRoles(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EmployeeProjectRoles employeeProjectRoles
    ) throws URISyntaxException {
        log.debug("REST request to update EmployeeProjectRoles : {}, {}", id, employeeProjectRoles);
        if (employeeProjectRoles.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeProjectRoles.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeProjectRolesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmployeeProjectRoles result = employeeProjectRolesService.update(employeeProjectRoles);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeProjectRoles.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /employee-project-roles/:id} : Partial updates given fields of an existing employeeProjectRoles, field will ignore if it is null
     *
     * @param id the id of the employeeProjectRoles to save.
     * @param employeeProjectRoles the employeeProjectRoles to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeProjectRoles,
     * or with status {@code 400 (Bad Request)} if the employeeProjectRoles is not valid,
     * or with status {@code 404 (Not Found)} if the employeeProjectRoles is not found,
     * or with status {@code 500 (Internal Server Error)} if the employeeProjectRoles couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/employee-project-roles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmployeeProjectRoles> partialUpdateEmployeeProjectRoles(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EmployeeProjectRoles employeeProjectRoles
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmployeeProjectRoles partially : {}, {}", id, employeeProjectRoles);
        if (employeeProjectRoles.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeProjectRoles.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeProjectRolesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmployeeProjectRoles> result = employeeProjectRolesService.partialUpdate(employeeProjectRoles);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeProjectRoles.getId().toString())
        );
    }

    /**
     * {@code GET  /employee-project-roles} : get all the employeeProjectRoles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeProjectRoles in body.
     */
    @GetMapping("/employee-project-roles")
    public ResponseEntity<List<EmployeeProjectRoles>> getAllEmployeeProjectRoles(
        EmployeeProjectRolesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EmployeeProjectRoles by criteria: {}", criteria);
        Page<EmployeeProjectRoles> page = employeeProjectRolesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-project-roles/count} : count all the employeeProjectRoles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employee-project-roles/count")
    public ResponseEntity<Long> countEmployeeProjectRoles(EmployeeProjectRolesCriteria criteria) {
        log.debug("REST request to count EmployeeProjectRoles by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeeProjectRolesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employee-project-roles/:id} : get the "id" employeeProjectRoles.
     *
     * @param id the id of the employeeProjectRoles to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeProjectRoles, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-project-roles/{id}")
    public ResponseEntity<EmployeeProjectRoles> getEmployeeProjectRoles(@PathVariable Long id) {
        log.debug("REST request to get EmployeeProjectRoles : {}", id);
        Optional<EmployeeProjectRoles> employeeProjectRoles = employeeProjectRolesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeProjectRoles);
    }

    /**
     * {@code DELETE  /employee-project-roles/:id} : delete the "id" employeeProjectRoles.
     *
     * @param id the id of the employeeProjectRoles to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-project-roles/{id}")
    public ResponseEntity<Void> deleteEmployeeProjectRoles(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeProjectRoles : {}", id);
        employeeProjectRolesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
