package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.EmployeeProjectRatings;
import com.venturedive.vendian_mono.repository.EmployeeProjectRatingsRepository;
import com.venturedive.vendian_mono.service.EmployeeProjectRatingsQueryService;
import com.venturedive.vendian_mono.service.EmployeeProjectRatingsService;
import com.venturedive.vendian_mono.service.criteria.EmployeeProjectRatingsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.EmployeeProjectRatings}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeProjectRatingsResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeProjectRatingsResource.class);

    private static final String ENTITY_NAME = "employeeProjectRatings";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeProjectRatingsService employeeProjectRatingsService;

    private final EmployeeProjectRatingsRepository employeeProjectRatingsRepository;

    private final EmployeeProjectRatingsQueryService employeeProjectRatingsQueryService;

    public EmployeeProjectRatingsResource(
        EmployeeProjectRatingsService employeeProjectRatingsService,
        EmployeeProjectRatingsRepository employeeProjectRatingsRepository,
        EmployeeProjectRatingsQueryService employeeProjectRatingsQueryService
    ) {
        this.employeeProjectRatingsService = employeeProjectRatingsService;
        this.employeeProjectRatingsRepository = employeeProjectRatingsRepository;
        this.employeeProjectRatingsQueryService = employeeProjectRatingsQueryService;
    }

    /**
     * {@code POST  /employee-project-ratings} : Create a new employeeProjectRatings.
     *
     * @param employeeProjectRatings the employeeProjectRatings to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeProjectRatings, or with status {@code 400 (Bad Request)} if the employeeProjectRatings has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-project-ratings")
    public ResponseEntity<EmployeeProjectRatings> createEmployeeProjectRatings(@RequestBody EmployeeProjectRatings employeeProjectRatings)
        throws URISyntaxException {
        log.debug("REST request to save EmployeeProjectRatings : {}", employeeProjectRatings);
        if (employeeProjectRatings.getId() != null) {
            throw new BadRequestAlertException("A new employeeProjectRatings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeProjectRatings result = employeeProjectRatingsService.save(employeeProjectRatings);
        return ResponseEntity
            .created(new URI("/api/employee-project-ratings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-project-ratings/:id} : Updates an existing employeeProjectRatings.
     *
     * @param id the id of the employeeProjectRatings to save.
     * @param employeeProjectRatings the employeeProjectRatings to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeProjectRatings,
     * or with status {@code 400 (Bad Request)} if the employeeProjectRatings is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeProjectRatings couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-project-ratings/{id}")
    public ResponseEntity<EmployeeProjectRatings> updateEmployeeProjectRatings(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmployeeProjectRatings employeeProjectRatings
    ) throws URISyntaxException {
        log.debug("REST request to update EmployeeProjectRatings : {}, {}", id, employeeProjectRatings);
        if (employeeProjectRatings.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeProjectRatings.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeProjectRatingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmployeeProjectRatings result = employeeProjectRatingsService.update(employeeProjectRatings);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeProjectRatings.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /employee-project-ratings/:id} : Partial updates given fields of an existing employeeProjectRatings, field will ignore if it is null
     *
     * @param id the id of the employeeProjectRatings to save.
     * @param employeeProjectRatings the employeeProjectRatings to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeProjectRatings,
     * or with status {@code 400 (Bad Request)} if the employeeProjectRatings is not valid,
     * or with status {@code 404 (Not Found)} if the employeeProjectRatings is not found,
     * or with status {@code 500 (Internal Server Error)} if the employeeProjectRatings couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/employee-project-ratings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmployeeProjectRatings> partialUpdateEmployeeProjectRatings(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmployeeProjectRatings employeeProjectRatings
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmployeeProjectRatings partially : {}, {}", id, employeeProjectRatings);
        if (employeeProjectRatings.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeProjectRatings.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeProjectRatingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmployeeProjectRatings> result = employeeProjectRatingsService.partialUpdate(employeeProjectRatings);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeProjectRatings.getId().toString())
        );
    }

    /**
     * {@code GET  /employee-project-ratings} : get all the employeeProjectRatings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeProjectRatings in body.
     */
    @GetMapping("/employee-project-ratings")
    public ResponseEntity<List<EmployeeProjectRatings>> getAllEmployeeProjectRatings(
        EmployeeProjectRatingsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EmployeeProjectRatings by criteria: {}", criteria);
        Page<EmployeeProjectRatings> page = employeeProjectRatingsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-project-ratings/count} : count all the employeeProjectRatings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employee-project-ratings/count")
    public ResponseEntity<Long> countEmployeeProjectRatings(EmployeeProjectRatingsCriteria criteria) {
        log.debug("REST request to count EmployeeProjectRatings by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeeProjectRatingsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employee-project-ratings/:id} : get the "id" employeeProjectRatings.
     *
     * @param id the id of the employeeProjectRatings to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeProjectRatings, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-project-ratings/{id}")
    public ResponseEntity<EmployeeProjectRatings> getEmployeeProjectRatings(@PathVariable Long id) {
        log.debug("REST request to get EmployeeProjectRatings : {}", id);
        Optional<EmployeeProjectRatings> employeeProjectRatings = employeeProjectRatingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeProjectRatings);
    }

    /**
     * {@code DELETE  /employee-project-ratings/:id} : delete the "id" employeeProjectRatings.
     *
     * @param id the id of the employeeProjectRatings to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-project-ratings/{id}")
    public ResponseEntity<Void> deleteEmployeeProjectRatings(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeProjectRatings : {}", id);
        employeeProjectRatingsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
