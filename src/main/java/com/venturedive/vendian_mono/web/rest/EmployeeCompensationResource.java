package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.EmployeeCompensation;
import com.venturedive.vendian_mono.repository.EmployeeCompensationRepository;
import com.venturedive.vendian_mono.service.EmployeeCompensationQueryService;
import com.venturedive.vendian_mono.service.EmployeeCompensationService;
import com.venturedive.vendian_mono.service.criteria.EmployeeCompensationCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.EmployeeCompensation}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeCompensationResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeCompensationResource.class);

    private static final String ENTITY_NAME = "employeeCompensation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeCompensationService employeeCompensationService;

    private final EmployeeCompensationRepository employeeCompensationRepository;

    private final EmployeeCompensationQueryService employeeCompensationQueryService;

    public EmployeeCompensationResource(
        EmployeeCompensationService employeeCompensationService,
        EmployeeCompensationRepository employeeCompensationRepository,
        EmployeeCompensationQueryService employeeCompensationQueryService
    ) {
        this.employeeCompensationService = employeeCompensationService;
        this.employeeCompensationRepository = employeeCompensationRepository;
        this.employeeCompensationQueryService = employeeCompensationQueryService;
    }

    /**
     * {@code POST  /employee-compensations} : Create a new employeeCompensation.
     *
     * @param employeeCompensation the employeeCompensation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeCompensation, or with status {@code 400 (Bad Request)} if the employeeCompensation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-compensations")
    public ResponseEntity<EmployeeCompensation> createEmployeeCompensation(@Valid @RequestBody EmployeeCompensation employeeCompensation)
        throws URISyntaxException {
        log.debug("REST request to save EmployeeCompensation : {}", employeeCompensation);
        if (employeeCompensation.getId() != null) {
            throw new BadRequestAlertException("A new employeeCompensation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeCompensation result = employeeCompensationService.save(employeeCompensation);
        return ResponseEntity
            .created(new URI("/api/employee-compensations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-compensations/:id} : Updates an existing employeeCompensation.
     *
     * @param id the id of the employeeCompensation to save.
     * @param employeeCompensation the employeeCompensation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeCompensation,
     * or with status {@code 400 (Bad Request)} if the employeeCompensation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeCompensation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-compensations/{id}")
    public ResponseEntity<EmployeeCompensation> updateEmployeeCompensation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EmployeeCompensation employeeCompensation
    ) throws URISyntaxException {
        log.debug("REST request to update EmployeeCompensation : {}, {}", id, employeeCompensation);
        if (employeeCompensation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeCompensation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeCompensationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmployeeCompensation result = employeeCompensationService.update(employeeCompensation);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeCompensation.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /employee-compensations/:id} : Partial updates given fields of an existing employeeCompensation, field will ignore if it is null
     *
     * @param id the id of the employeeCompensation to save.
     * @param employeeCompensation the employeeCompensation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeCompensation,
     * or with status {@code 400 (Bad Request)} if the employeeCompensation is not valid,
     * or with status {@code 404 (Not Found)} if the employeeCompensation is not found,
     * or with status {@code 500 (Internal Server Error)} if the employeeCompensation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/employee-compensations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmployeeCompensation> partialUpdateEmployeeCompensation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EmployeeCompensation employeeCompensation
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmployeeCompensation partially : {}, {}", id, employeeCompensation);
        if (employeeCompensation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeCompensation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeCompensationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmployeeCompensation> result = employeeCompensationService.partialUpdate(employeeCompensation);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeCompensation.getId().toString())
        );
    }

    /**
     * {@code GET  /employee-compensations} : get all the employeeCompensations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeCompensations in body.
     */
    @GetMapping("/employee-compensations")
    public ResponseEntity<List<EmployeeCompensation>> getAllEmployeeCompensations(
        EmployeeCompensationCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EmployeeCompensations by criteria: {}", criteria);
        Page<EmployeeCompensation> page = employeeCompensationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-compensations/count} : count all the employeeCompensations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employee-compensations/count")
    public ResponseEntity<Long> countEmployeeCompensations(EmployeeCompensationCriteria criteria) {
        log.debug("REST request to count EmployeeCompensations by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeeCompensationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employee-compensations/:id} : get the "id" employeeCompensation.
     *
     * @param id the id of the employeeCompensation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeCompensation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-compensations/{id}")
    public ResponseEntity<EmployeeCompensation> getEmployeeCompensation(@PathVariable Long id) {
        log.debug("REST request to get EmployeeCompensation : {}", id);
        Optional<EmployeeCompensation> employeeCompensation = employeeCompensationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeCompensation);
    }

    /**
     * {@code DELETE  /employee-compensations/:id} : delete the "id" employeeCompensation.
     *
     * @param id the id of the employeeCompensation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-compensations/{id}")
    public ResponseEntity<Void> deleteEmployeeCompensation(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeCompensation : {}", id);
        employeeCompensationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
