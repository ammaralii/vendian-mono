package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.EmploymentHistory;
import com.venturedive.vendian_mono.repository.EmploymentHistoryRepository;
import com.venturedive.vendian_mono.service.EmploymentHistoryQueryService;
import com.venturedive.vendian_mono.service.EmploymentHistoryService;
import com.venturedive.vendian_mono.service.criteria.EmploymentHistoryCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.EmploymentHistory}.
 */
@RestController
@RequestMapping("/api")
public class EmploymentHistoryResource {

    private final Logger log = LoggerFactory.getLogger(EmploymentHistoryResource.class);

    private static final String ENTITY_NAME = "employmentHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmploymentHistoryService employmentHistoryService;

    private final EmploymentHistoryRepository employmentHistoryRepository;

    private final EmploymentHistoryQueryService employmentHistoryQueryService;

    public EmploymentHistoryResource(
        EmploymentHistoryService employmentHistoryService,
        EmploymentHistoryRepository employmentHistoryRepository,
        EmploymentHistoryQueryService employmentHistoryQueryService
    ) {
        this.employmentHistoryService = employmentHistoryService;
        this.employmentHistoryRepository = employmentHistoryRepository;
        this.employmentHistoryQueryService = employmentHistoryQueryService;
    }

    /**
     * {@code POST  /employment-histories} : Create a new employmentHistory.
     *
     * @param employmentHistory the employmentHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employmentHistory, or with status {@code 400 (Bad Request)} if the employmentHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employment-histories")
    public ResponseEntity<EmploymentHistory> createEmploymentHistory(@Valid @RequestBody EmploymentHistory employmentHistory)
        throws URISyntaxException {
        log.debug("REST request to save EmploymentHistory : {}", employmentHistory);
        if (employmentHistory.getId() != null) {
            throw new BadRequestAlertException("A new employmentHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmploymentHistory result = employmentHistoryService.save(employmentHistory);
        return ResponseEntity
            .created(new URI("/api/employment-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employment-histories/:id} : Updates an existing employmentHistory.
     *
     * @param id the id of the employmentHistory to save.
     * @param employmentHistory the employmentHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employmentHistory,
     * or with status {@code 400 (Bad Request)} if the employmentHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employmentHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employment-histories/{id}")
    public ResponseEntity<EmploymentHistory> updateEmploymentHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EmploymentHistory employmentHistory
    ) throws URISyntaxException {
        log.debug("REST request to update EmploymentHistory : {}, {}", id, employmentHistory);
        if (employmentHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employmentHistory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employmentHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmploymentHistory result = employmentHistoryService.update(employmentHistory);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employmentHistory.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /employment-histories/:id} : Partial updates given fields of an existing employmentHistory, field will ignore if it is null
     *
     * @param id the id of the employmentHistory to save.
     * @param employmentHistory the employmentHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employmentHistory,
     * or with status {@code 400 (Bad Request)} if the employmentHistory is not valid,
     * or with status {@code 404 (Not Found)} if the employmentHistory is not found,
     * or with status {@code 500 (Internal Server Error)} if the employmentHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/employment-histories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmploymentHistory> partialUpdateEmploymentHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EmploymentHistory employmentHistory
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmploymentHistory partially : {}, {}", id, employmentHistory);
        if (employmentHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employmentHistory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employmentHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmploymentHistory> result = employmentHistoryService.partialUpdate(employmentHistory);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employmentHistory.getId().toString())
        );
    }

    /**
     * {@code GET  /employment-histories} : get all the employmentHistories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employmentHistories in body.
     */
    @GetMapping("/employment-histories")
    public ResponseEntity<List<EmploymentHistory>> getAllEmploymentHistories(
        EmploymentHistoryCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EmploymentHistories by criteria: {}", criteria);
        Page<EmploymentHistory> page = employmentHistoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employment-histories/count} : count all the employmentHistories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employment-histories/count")
    public ResponseEntity<Long> countEmploymentHistories(EmploymentHistoryCriteria criteria) {
        log.debug("REST request to count EmploymentHistories by criteria: {}", criteria);
        return ResponseEntity.ok().body(employmentHistoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employment-histories/:id} : get the "id" employmentHistory.
     *
     * @param id the id of the employmentHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employmentHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employment-histories/{id}")
    public ResponseEntity<EmploymentHistory> getEmploymentHistory(@PathVariable Long id) {
        log.debug("REST request to get EmploymentHistory : {}", id);
        Optional<EmploymentHistory> employmentHistory = employmentHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employmentHistory);
    }

    /**
     * {@code DELETE  /employment-histories/:id} : delete the "id" employmentHistory.
     *
     * @param id the id of the employmentHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employment-histories/{id}")
    public ResponseEntity<Void> deleteEmploymentHistory(@PathVariable Long id) {
        log.debug("REST request to delete EmploymentHistory : {}", id);
        employmentHistoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
