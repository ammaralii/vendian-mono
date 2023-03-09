package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.RolePermissions;
import com.venturedive.vendian_mono.repository.RolePermissionsRepository;
import com.venturedive.vendian_mono.service.RolePermissionsQueryService;
import com.venturedive.vendian_mono.service.RolePermissionsService;
import com.venturedive.vendian_mono.service.criteria.RolePermissionsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.RolePermissions}.
 */
@RestController
@RequestMapping("/api")
public class RolePermissionsResource {

    private final Logger log = LoggerFactory.getLogger(RolePermissionsResource.class);

    private static final String ENTITY_NAME = "rolePermissions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RolePermissionsService rolePermissionsService;

    private final RolePermissionsRepository rolePermissionsRepository;

    private final RolePermissionsQueryService rolePermissionsQueryService;

    public RolePermissionsResource(
        RolePermissionsService rolePermissionsService,
        RolePermissionsRepository rolePermissionsRepository,
        RolePermissionsQueryService rolePermissionsQueryService
    ) {
        this.rolePermissionsService = rolePermissionsService;
        this.rolePermissionsRepository = rolePermissionsRepository;
        this.rolePermissionsQueryService = rolePermissionsQueryService;
    }

    /**
     * {@code POST  /role-permissions} : Create a new rolePermissions.
     *
     * @param rolePermissions the rolePermissions to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rolePermissions, or with status {@code 400 (Bad Request)} if the rolePermissions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/role-permissions")
    public ResponseEntity<RolePermissions> createRolePermissions(@Valid @RequestBody RolePermissions rolePermissions)
        throws URISyntaxException {
        log.debug("REST request to save RolePermissions : {}", rolePermissions);
        if (rolePermissions.getId() != null) {
            throw new BadRequestAlertException("A new rolePermissions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RolePermissions result = rolePermissionsService.save(rolePermissions);
        return ResponseEntity
            .created(new URI("/api/role-permissions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /role-permissions/:id} : Updates an existing rolePermissions.
     *
     * @param id the id of the rolePermissions to save.
     * @param rolePermissions the rolePermissions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rolePermissions,
     * or with status {@code 400 (Bad Request)} if the rolePermissions is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rolePermissions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/role-permissions/{id}")
    public ResponseEntity<RolePermissions> updateRolePermissions(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RolePermissions rolePermissions
    ) throws URISyntaxException {
        log.debug("REST request to update RolePermissions : {}, {}", id, rolePermissions);
        if (rolePermissions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rolePermissions.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rolePermissionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RolePermissions result = rolePermissionsService.update(rolePermissions);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rolePermissions.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /role-permissions/:id} : Partial updates given fields of an existing rolePermissions, field will ignore if it is null
     *
     * @param id the id of the rolePermissions to save.
     * @param rolePermissions the rolePermissions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rolePermissions,
     * or with status {@code 400 (Bad Request)} if the rolePermissions is not valid,
     * or with status {@code 404 (Not Found)} if the rolePermissions is not found,
     * or with status {@code 500 (Internal Server Error)} if the rolePermissions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/role-permissions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RolePermissions> partialUpdateRolePermissions(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RolePermissions rolePermissions
    ) throws URISyntaxException {
        log.debug("REST request to partial update RolePermissions partially : {}, {}", id, rolePermissions);
        if (rolePermissions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rolePermissions.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rolePermissionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RolePermissions> result = rolePermissionsService.partialUpdate(rolePermissions);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rolePermissions.getId().toString())
        );
    }

    /**
     * {@code GET  /role-permissions} : get all the rolePermissions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rolePermissions in body.
     */
    @GetMapping("/role-permissions")
    public ResponseEntity<List<RolePermissions>> getAllRolePermissions(
        RolePermissionsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get RolePermissions by criteria: {}", criteria);
        Page<RolePermissions> page = rolePermissionsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /role-permissions/count} : count all the rolePermissions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/role-permissions/count")
    public ResponseEntity<Long> countRolePermissions(RolePermissionsCriteria criteria) {
        log.debug("REST request to count RolePermissions by criteria: {}", criteria);
        return ResponseEntity.ok().body(rolePermissionsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /role-permissions/:id} : get the "id" rolePermissions.
     *
     * @param id the id of the rolePermissions to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rolePermissions, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/role-permissions/{id}")
    public ResponseEntity<RolePermissions> getRolePermissions(@PathVariable Long id) {
        log.debug("REST request to get RolePermissions : {}", id);
        Optional<RolePermissions> rolePermissions = rolePermissionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rolePermissions);
    }

    /**
     * {@code DELETE  /role-permissions/:id} : delete the "id" rolePermissions.
     *
     * @param id the id of the rolePermissions to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/role-permissions/{id}")
    public ResponseEntity<Void> deleteRolePermissions(@PathVariable Long id) {
        log.debug("REST request to delete RolePermissions : {}", id);
        rolePermissionsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
