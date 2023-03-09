package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.EmployeeProfileHistoryLogs;
import com.venturedive.vendian_mono.repository.EmployeeProfileHistoryLogsRepository;
import com.venturedive.vendian_mono.service.EmployeeProfileHistoryLogsQueryService;
import com.venturedive.vendian_mono.service.EmployeeProfileHistoryLogsService;
import com.venturedive.vendian_mono.service.criteria.EmployeeProfileHistoryLogsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.EmployeeProfileHistoryLogs}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeProfileHistoryLogsResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeProfileHistoryLogsResource.class);

    private static final String ENTITY_NAME = "employeeProfileHistoryLogs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeProfileHistoryLogsService employeeProfileHistoryLogsService;

    private final EmployeeProfileHistoryLogsRepository employeeProfileHistoryLogsRepository;

    private final EmployeeProfileHistoryLogsQueryService employeeProfileHistoryLogsQueryService;

    public EmployeeProfileHistoryLogsResource(
        EmployeeProfileHistoryLogsService employeeProfileHistoryLogsService,
        EmployeeProfileHistoryLogsRepository employeeProfileHistoryLogsRepository,
        EmployeeProfileHistoryLogsQueryService employeeProfileHistoryLogsQueryService
    ) {
        this.employeeProfileHistoryLogsService = employeeProfileHistoryLogsService;
        this.employeeProfileHistoryLogsRepository = employeeProfileHistoryLogsRepository;
        this.employeeProfileHistoryLogsQueryService = employeeProfileHistoryLogsQueryService;
    }

    /**
     * {@code POST  /employee-profile-history-logs} : Create a new employeeProfileHistoryLogs.
     *
     * @param employeeProfileHistoryLogs the employeeProfileHistoryLogs to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeProfileHistoryLogs, or with status {@code 400 (Bad Request)} if the employeeProfileHistoryLogs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-profile-history-logs")
    public ResponseEntity<EmployeeProfileHistoryLogs> createEmployeeProfileHistoryLogs(
        @Valid @RequestBody EmployeeProfileHistoryLogs employeeProfileHistoryLogs
    ) throws URISyntaxException {
        log.debug("REST request to save EmployeeProfileHistoryLogs : {}", employeeProfileHistoryLogs);
        if (employeeProfileHistoryLogs.getId() != null) {
            throw new BadRequestAlertException("A new employeeProfileHistoryLogs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeProfileHistoryLogs result = employeeProfileHistoryLogsService.save(employeeProfileHistoryLogs);
        return ResponseEntity
            .created(new URI("/api/employee-profile-history-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-profile-history-logs/:id} : Updates an existing employeeProfileHistoryLogs.
     *
     * @param id the id of the employeeProfileHistoryLogs to save.
     * @param employeeProfileHistoryLogs the employeeProfileHistoryLogs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeProfileHistoryLogs,
     * or with status {@code 400 (Bad Request)} if the employeeProfileHistoryLogs is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeProfileHistoryLogs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-profile-history-logs/{id}")
    public ResponseEntity<EmployeeProfileHistoryLogs> updateEmployeeProfileHistoryLogs(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EmployeeProfileHistoryLogs employeeProfileHistoryLogs
    ) throws URISyntaxException {
        log.debug("REST request to update EmployeeProfileHistoryLogs : {}, {}", id, employeeProfileHistoryLogs);
        if (employeeProfileHistoryLogs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeProfileHistoryLogs.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeProfileHistoryLogsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmployeeProfileHistoryLogs result = employeeProfileHistoryLogsService.update(employeeProfileHistoryLogs);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeProfileHistoryLogs.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /employee-profile-history-logs/:id} : Partial updates given fields of an existing employeeProfileHistoryLogs, field will ignore if it is null
     *
     * @param id the id of the employeeProfileHistoryLogs to save.
     * @param employeeProfileHistoryLogs the employeeProfileHistoryLogs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeProfileHistoryLogs,
     * or with status {@code 400 (Bad Request)} if the employeeProfileHistoryLogs is not valid,
     * or with status {@code 404 (Not Found)} if the employeeProfileHistoryLogs is not found,
     * or with status {@code 500 (Internal Server Error)} if the employeeProfileHistoryLogs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/employee-profile-history-logs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmployeeProfileHistoryLogs> partialUpdateEmployeeProfileHistoryLogs(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EmployeeProfileHistoryLogs employeeProfileHistoryLogs
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmployeeProfileHistoryLogs partially : {}, {}", id, employeeProfileHistoryLogs);
        if (employeeProfileHistoryLogs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeProfileHistoryLogs.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeProfileHistoryLogsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmployeeProfileHistoryLogs> result = employeeProfileHistoryLogsService.partialUpdate(employeeProfileHistoryLogs);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeProfileHistoryLogs.getId().toString())
        );
    }

    /**
     * {@code GET  /employee-profile-history-logs} : get all the employeeProfileHistoryLogs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeProfileHistoryLogs in body.
     */
    @GetMapping("/employee-profile-history-logs")
    public ResponseEntity<List<EmployeeProfileHistoryLogs>> getAllEmployeeProfileHistoryLogs(
        EmployeeProfileHistoryLogsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EmployeeProfileHistoryLogs by criteria: {}", criteria);
        Page<EmployeeProfileHistoryLogs> page = employeeProfileHistoryLogsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-profile-history-logs/count} : count all the employeeProfileHistoryLogs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employee-profile-history-logs/count")
    public ResponseEntity<Long> countEmployeeProfileHistoryLogs(EmployeeProfileHistoryLogsCriteria criteria) {
        log.debug("REST request to count EmployeeProfileHistoryLogs by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeeProfileHistoryLogsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employee-profile-history-logs/:id} : get the "id" employeeProfileHistoryLogs.
     *
     * @param id the id of the employeeProfileHistoryLogs to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeProfileHistoryLogs, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-profile-history-logs/{id}")
    public ResponseEntity<EmployeeProfileHistoryLogs> getEmployeeProfileHistoryLogs(@PathVariable Long id) {
        log.debug("REST request to get EmployeeProfileHistoryLogs : {}", id);
        Optional<EmployeeProfileHistoryLogs> employeeProfileHistoryLogs = employeeProfileHistoryLogsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeProfileHistoryLogs);
    }

    /**
     * {@code DELETE  /employee-profile-history-logs/:id} : delete the "id" employeeProfileHistoryLogs.
     *
     * @param id the id of the employeeProfileHistoryLogs to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-profile-history-logs/{id}")
    public ResponseEntity<Void> deleteEmployeeProfileHistoryLogs(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeProfileHistoryLogs : {}", id);
        employeeProfileHistoryLogsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
