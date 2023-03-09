package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.AttributePermissions;
import com.venturedive.vendian_mono.repository.AttributePermissionsRepository;
import com.venturedive.vendian_mono.service.AttributePermissionsQueryService;
import com.venturedive.vendian_mono.service.AttributePermissionsService;
import com.venturedive.vendian_mono.service.criteria.AttributePermissionsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.AttributePermissions}.
 */
@RestController
@RequestMapping("/api")
public class AttributePermissionsResource {

    private final Logger log = LoggerFactory.getLogger(AttributePermissionsResource.class);

    private static final String ENTITY_NAME = "attributePermissions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttributePermissionsService attributePermissionsService;

    private final AttributePermissionsRepository attributePermissionsRepository;

    private final AttributePermissionsQueryService attributePermissionsQueryService;

    public AttributePermissionsResource(
        AttributePermissionsService attributePermissionsService,
        AttributePermissionsRepository attributePermissionsRepository,
        AttributePermissionsQueryService attributePermissionsQueryService
    ) {
        this.attributePermissionsService = attributePermissionsService;
        this.attributePermissionsRepository = attributePermissionsRepository;
        this.attributePermissionsQueryService = attributePermissionsQueryService;
    }

    /**
     * {@code POST  /attribute-permissions} : Create a new attributePermissions.
     *
     * @param attributePermissions the attributePermissions to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attributePermissions, or with status {@code 400 (Bad Request)} if the attributePermissions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attribute-permissions")
    public ResponseEntity<AttributePermissions> createAttributePermissions(@Valid @RequestBody AttributePermissions attributePermissions)
        throws URISyntaxException {
        log.debug("REST request to save AttributePermissions : {}", attributePermissions);
        if (attributePermissions.getId() != null) {
            throw new BadRequestAlertException("A new attributePermissions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttributePermissions result = attributePermissionsService.save(attributePermissions);
        return ResponseEntity
            .created(new URI("/api/attribute-permissions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attribute-permissions/:id} : Updates an existing attributePermissions.
     *
     * @param id the id of the attributePermissions to save.
     * @param attributePermissions the attributePermissions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attributePermissions,
     * or with status {@code 400 (Bad Request)} if the attributePermissions is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attributePermissions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attribute-permissions/{id}")
    public ResponseEntity<AttributePermissions> updateAttributePermissions(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AttributePermissions attributePermissions
    ) throws URISyntaxException {
        log.debug("REST request to update AttributePermissions : {}, {}", id, attributePermissions);
        if (attributePermissions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attributePermissions.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attributePermissionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AttributePermissions result = attributePermissionsService.update(attributePermissions);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, attributePermissions.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /attribute-permissions/:id} : Partial updates given fields of an existing attributePermissions, field will ignore if it is null
     *
     * @param id the id of the attributePermissions to save.
     * @param attributePermissions the attributePermissions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attributePermissions,
     * or with status {@code 400 (Bad Request)} if the attributePermissions is not valid,
     * or with status {@code 404 (Not Found)} if the attributePermissions is not found,
     * or with status {@code 500 (Internal Server Error)} if the attributePermissions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/attribute-permissions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AttributePermissions> partialUpdateAttributePermissions(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AttributePermissions attributePermissions
    ) throws URISyntaxException {
        log.debug("REST request to partial update AttributePermissions partially : {}, {}", id, attributePermissions);
        if (attributePermissions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attributePermissions.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attributePermissionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AttributePermissions> result = attributePermissionsService.partialUpdate(attributePermissions);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, attributePermissions.getId().toString())
        );
    }

    /**
     * {@code GET  /attribute-permissions} : get all the attributePermissions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attributePermissions in body.
     */
    @GetMapping("/attribute-permissions")
    public ResponseEntity<List<AttributePermissions>> getAllAttributePermissions(
        AttributePermissionsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get AttributePermissions by criteria: {}", criteria);
        Page<AttributePermissions> page = attributePermissionsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /attribute-permissions/count} : count all the attributePermissions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/attribute-permissions/count")
    public ResponseEntity<Long> countAttributePermissions(AttributePermissionsCriteria criteria) {
        log.debug("REST request to count AttributePermissions by criteria: {}", criteria);
        return ResponseEntity.ok().body(attributePermissionsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /attribute-permissions/:id} : get the "id" attributePermissions.
     *
     * @param id the id of the attributePermissions to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attributePermissions, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attribute-permissions/{id}")
    public ResponseEntity<AttributePermissions> getAttributePermissions(@PathVariable Long id) {
        log.debug("REST request to get AttributePermissions : {}", id);
        Optional<AttributePermissions> attributePermissions = attributePermissionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attributePermissions);
    }

    /**
     * {@code DELETE  /attribute-permissions/:id} : delete the "id" attributePermissions.
     *
     * @param id the id of the attributePermissions to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attribute-permissions/{id}")
    public ResponseEntity<Void> deleteAttributePermissions(@PathVariable Long id) {
        log.debug("REST request to delete AttributePermissions : {}", id);
        attributePermissionsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
