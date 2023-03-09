package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.EmployeeRoles;
import com.venturedive.vendian_mono.repository.EmployeeRolesRepository;
import com.venturedive.vendian_mono.service.EmployeeRolesQueryService;
import com.venturedive.vendian_mono.service.EmployeeRolesService;
import com.venturedive.vendian_mono.service.criteria.EmployeeRolesCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.EmployeeRoles}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeRolesResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeRolesResource.class);

    private static final String ENTITY_NAME = "employeeRoles";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeRolesService employeeRolesService;

    private final EmployeeRolesRepository employeeRolesRepository;

    private final EmployeeRolesQueryService employeeRolesQueryService;

    public EmployeeRolesResource(
        EmployeeRolesService employeeRolesService,
        EmployeeRolesRepository employeeRolesRepository,
        EmployeeRolesQueryService employeeRolesQueryService
    ) {
        this.employeeRolesService = employeeRolesService;
        this.employeeRolesRepository = employeeRolesRepository;
        this.employeeRolesQueryService = employeeRolesQueryService;
    }

    /**
     * {@code POST  /employee-roles} : Create a new employeeRoles.
     *
     * @param employeeRoles the employeeRoles to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeRoles, or with status {@code 400 (Bad Request)} if the employeeRoles has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-roles")
    public ResponseEntity<EmployeeRoles> createEmployeeRoles(@Valid @RequestBody EmployeeRoles employeeRoles) throws URISyntaxException {
        log.debug("REST request to save EmployeeRoles : {}", employeeRoles);
        if (employeeRoles.getId() != null) {
            throw new BadRequestAlertException("A new employeeRoles cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeRoles result = employeeRolesService.save(employeeRoles);
        return ResponseEntity
            .created(new URI("/api/employee-roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-roles/:id} : Updates an existing employeeRoles.
     *
     * @param id the id of the employeeRoles to save.
     * @param employeeRoles the employeeRoles to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeRoles,
     * or with status {@code 400 (Bad Request)} if the employeeRoles is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeRoles couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-roles/{id}")
    public ResponseEntity<EmployeeRoles> updateEmployeeRoles(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EmployeeRoles employeeRoles
    ) throws URISyntaxException {
        log.debug("REST request to update EmployeeRoles : {}, {}", id, employeeRoles);
        if (employeeRoles.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeRoles.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeRolesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmployeeRoles result = employeeRolesService.update(employeeRoles);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeRoles.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /employee-roles/:id} : Partial updates given fields of an existing employeeRoles, field will ignore if it is null
     *
     * @param id the id of the employeeRoles to save.
     * @param employeeRoles the employeeRoles to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeRoles,
     * or with status {@code 400 (Bad Request)} if the employeeRoles is not valid,
     * or with status {@code 404 (Not Found)} if the employeeRoles is not found,
     * or with status {@code 500 (Internal Server Error)} if the employeeRoles couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/employee-roles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmployeeRoles> partialUpdateEmployeeRoles(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EmployeeRoles employeeRoles
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmployeeRoles partially : {}, {}", id, employeeRoles);
        if (employeeRoles.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeRoles.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeRolesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmployeeRoles> result = employeeRolesService.partialUpdate(employeeRoles);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeRoles.getId().toString())
        );
    }

    /**
     * {@code GET  /employee-roles} : get all the employeeRoles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeRoles in body.
     */
    @GetMapping("/employee-roles")
    public ResponseEntity<List<EmployeeRoles>> getAllEmployeeRoles(
        EmployeeRolesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EmployeeRoles by criteria: {}", criteria);
        Page<EmployeeRoles> page = employeeRolesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-roles/count} : count all the employeeRoles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employee-roles/count")
    public ResponseEntity<Long> countEmployeeRoles(EmployeeRolesCriteria criteria) {
        log.debug("REST request to count EmployeeRoles by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeeRolesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employee-roles/:id} : get the "id" employeeRoles.
     *
     * @param id the id of the employeeRoles to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeRoles, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-roles/{id}")
    public ResponseEntity<EmployeeRoles> getEmployeeRoles(@PathVariable Long id) {
        log.debug("REST request to get EmployeeRoles : {}", id);
        Optional<EmployeeRoles> employeeRoles = employeeRolesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeRoles);
    }

    /**
     * {@code DELETE  /employee-roles/:id} : delete the "id" employeeRoles.
     *
     * @param id the id of the employeeRoles to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-roles/{id}")
    public ResponseEntity<Void> deleteEmployeeRoles(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeRoles : {}", id);
        employeeRolesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
