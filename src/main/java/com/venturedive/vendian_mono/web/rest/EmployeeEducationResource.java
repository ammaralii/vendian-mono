package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.EmployeeEducation;
import com.venturedive.vendian_mono.repository.EmployeeEducationRepository;
import com.venturedive.vendian_mono.service.EmployeeEducationQueryService;
import com.venturedive.vendian_mono.service.EmployeeEducationService;
import com.venturedive.vendian_mono.service.criteria.EmployeeEducationCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.EmployeeEducation}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeEducationResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeEducationResource.class);

    private static final String ENTITY_NAME = "employeeEducation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeEducationService employeeEducationService;

    private final EmployeeEducationRepository employeeEducationRepository;

    private final EmployeeEducationQueryService employeeEducationQueryService;

    public EmployeeEducationResource(
        EmployeeEducationService employeeEducationService,
        EmployeeEducationRepository employeeEducationRepository,
        EmployeeEducationQueryService employeeEducationQueryService
    ) {
        this.employeeEducationService = employeeEducationService;
        this.employeeEducationRepository = employeeEducationRepository;
        this.employeeEducationQueryService = employeeEducationQueryService;
    }

    /**
     * {@code POST  /employee-educations} : Create a new employeeEducation.
     *
     * @param employeeEducation the employeeEducation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeEducation, or with status {@code 400 (Bad Request)} if the employeeEducation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-educations")
    public ResponseEntity<EmployeeEducation> createEmployeeEducation(@Valid @RequestBody EmployeeEducation employeeEducation)
        throws URISyntaxException {
        log.debug("REST request to save EmployeeEducation : {}", employeeEducation);
        if (employeeEducation.getId() != null) {
            throw new BadRequestAlertException("A new employeeEducation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeEducation result = employeeEducationService.save(employeeEducation);
        return ResponseEntity
            .created(new URI("/api/employee-educations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-educations/:id} : Updates an existing employeeEducation.
     *
     * @param id the id of the employeeEducation to save.
     * @param employeeEducation the employeeEducation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeEducation,
     * or with status {@code 400 (Bad Request)} if the employeeEducation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeEducation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-educations/{id}")
    public ResponseEntity<EmployeeEducation> updateEmployeeEducation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EmployeeEducation employeeEducation
    ) throws URISyntaxException {
        log.debug("REST request to update EmployeeEducation : {}, {}", id, employeeEducation);
        if (employeeEducation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeEducation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeEducationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmployeeEducation result = employeeEducationService.update(employeeEducation);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeEducation.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /employee-educations/:id} : Partial updates given fields of an existing employeeEducation, field will ignore if it is null
     *
     * @param id the id of the employeeEducation to save.
     * @param employeeEducation the employeeEducation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeEducation,
     * or with status {@code 400 (Bad Request)} if the employeeEducation is not valid,
     * or with status {@code 404 (Not Found)} if the employeeEducation is not found,
     * or with status {@code 500 (Internal Server Error)} if the employeeEducation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/employee-educations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmployeeEducation> partialUpdateEmployeeEducation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EmployeeEducation employeeEducation
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmployeeEducation partially : {}, {}", id, employeeEducation);
        if (employeeEducation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeEducation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeEducationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmployeeEducation> result = employeeEducationService.partialUpdate(employeeEducation);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeEducation.getId().toString())
        );
    }

    /**
     * {@code GET  /employee-educations} : get all the employeeEducations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeEducations in body.
     */
    @GetMapping("/employee-educations")
    public ResponseEntity<List<EmployeeEducation>> getAllEmployeeEducations(
        EmployeeEducationCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EmployeeEducations by criteria: {}", criteria);
        Page<EmployeeEducation> page = employeeEducationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-educations/count} : count all the employeeEducations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employee-educations/count")
    public ResponseEntity<Long> countEmployeeEducations(EmployeeEducationCriteria criteria) {
        log.debug("REST request to count EmployeeEducations by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeeEducationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employee-educations/:id} : get the "id" employeeEducation.
     *
     * @param id the id of the employeeEducation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeEducation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-educations/{id}")
    public ResponseEntity<EmployeeEducation> getEmployeeEducation(@PathVariable Long id) {
        log.debug("REST request to get EmployeeEducation : {}", id);
        Optional<EmployeeEducation> employeeEducation = employeeEducationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeEducation);
    }

    /**
     * {@code DELETE  /employee-educations/:id} : delete the "id" employeeEducation.
     *
     * @param id the id of the employeeEducation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-educations/{id}")
    public ResponseEntity<Void> deleteEmployeeEducation(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeEducation : {}", id);
        employeeEducationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
