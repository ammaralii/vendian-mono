package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.EmploymentTypes;
import com.venturedive.vendian_mono.repository.EmploymentTypesRepository;
import com.venturedive.vendian_mono.service.EmploymentTypesQueryService;
import com.venturedive.vendian_mono.service.EmploymentTypesService;
import com.venturedive.vendian_mono.service.criteria.EmploymentTypesCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.EmploymentTypes}.
 */
@RestController
@RequestMapping("/api")
public class EmploymentTypesResource {

    private final Logger log = LoggerFactory.getLogger(EmploymentTypesResource.class);

    private static final String ENTITY_NAME = "employmentTypes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmploymentTypesService employmentTypesService;

    private final EmploymentTypesRepository employmentTypesRepository;

    private final EmploymentTypesQueryService employmentTypesQueryService;

    public EmploymentTypesResource(
        EmploymentTypesService employmentTypesService,
        EmploymentTypesRepository employmentTypesRepository,
        EmploymentTypesQueryService employmentTypesQueryService
    ) {
        this.employmentTypesService = employmentTypesService;
        this.employmentTypesRepository = employmentTypesRepository;
        this.employmentTypesQueryService = employmentTypesQueryService;
    }

    /**
     * {@code POST  /employment-types} : Create a new employmentTypes.
     *
     * @param employmentTypes the employmentTypes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employmentTypes, or with status {@code 400 (Bad Request)} if the employmentTypes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employment-types")
    public ResponseEntity<EmploymentTypes> createEmploymentTypes(@Valid @RequestBody EmploymentTypes employmentTypes)
        throws URISyntaxException {
        log.debug("REST request to save EmploymentTypes : {}", employmentTypes);
        if (employmentTypes.getId() != null) {
            throw new BadRequestAlertException("A new employmentTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmploymentTypes result = employmentTypesService.save(employmentTypes);
        return ResponseEntity
            .created(new URI("/api/employment-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employment-types/:id} : Updates an existing employmentTypes.
     *
     * @param id the id of the employmentTypes to save.
     * @param employmentTypes the employmentTypes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employmentTypes,
     * or with status {@code 400 (Bad Request)} if the employmentTypes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employmentTypes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employment-types/{id}")
    public ResponseEntity<EmploymentTypes> updateEmploymentTypes(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EmploymentTypes employmentTypes
    ) throws URISyntaxException {
        log.debug("REST request to update EmploymentTypes : {}, {}", id, employmentTypes);
        if (employmentTypes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employmentTypes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employmentTypesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmploymentTypes result = employmentTypesService.update(employmentTypes);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employmentTypes.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /employment-types/:id} : Partial updates given fields of an existing employmentTypes, field will ignore if it is null
     *
     * @param id the id of the employmentTypes to save.
     * @param employmentTypes the employmentTypes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employmentTypes,
     * or with status {@code 400 (Bad Request)} if the employmentTypes is not valid,
     * or with status {@code 404 (Not Found)} if the employmentTypes is not found,
     * or with status {@code 500 (Internal Server Error)} if the employmentTypes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/employment-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmploymentTypes> partialUpdateEmploymentTypes(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EmploymentTypes employmentTypes
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmploymentTypes partially : {}, {}", id, employmentTypes);
        if (employmentTypes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employmentTypes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employmentTypesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmploymentTypes> result = employmentTypesService.partialUpdate(employmentTypes);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employmentTypes.getId().toString())
        );
    }

    /**
     * {@code GET  /employment-types} : get all the employmentTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employmentTypes in body.
     */
    @GetMapping("/employment-types")
    public ResponseEntity<List<EmploymentTypes>> getAllEmploymentTypes(
        EmploymentTypesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EmploymentTypes by criteria: {}", criteria);
        Page<EmploymentTypes> page = employmentTypesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employment-types/count} : count all the employmentTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employment-types/count")
    public ResponseEntity<Long> countEmploymentTypes(EmploymentTypesCriteria criteria) {
        log.debug("REST request to count EmploymentTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(employmentTypesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employment-types/:id} : get the "id" employmentTypes.
     *
     * @param id the id of the employmentTypes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employmentTypes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employment-types/{id}")
    public ResponseEntity<EmploymentTypes> getEmploymentTypes(@PathVariable Long id) {
        log.debug("REST request to get EmploymentTypes : {}", id);
        Optional<EmploymentTypes> employmentTypes = employmentTypesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employmentTypes);
    }

    /**
     * {@code DELETE  /employment-types/:id} : delete the "id" employmentTypes.
     *
     * @param id the id of the employmentTypes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employment-types/{id}")
    public ResponseEntity<Void> deleteEmploymentTypes(@PathVariable Long id) {
        log.debug("REST request to delete EmploymentTypes : {}", id);
        employmentTypesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
