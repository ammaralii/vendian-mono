package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.WorkLogs;
import com.venturedive.vendian_mono.repository.WorkLogsRepository;
import com.venturedive.vendian_mono.service.WorkLogsQueryService;
import com.venturedive.vendian_mono.service.WorkLogsService;
import com.venturedive.vendian_mono.service.criteria.WorkLogsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.WorkLogs}.
 */
@RestController
@RequestMapping("/api")
public class WorkLogsResource {

    private final Logger log = LoggerFactory.getLogger(WorkLogsResource.class);

    private static final String ENTITY_NAME = "workLogs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkLogsService workLogsService;

    private final WorkLogsRepository workLogsRepository;

    private final WorkLogsQueryService workLogsQueryService;

    public WorkLogsResource(
        WorkLogsService workLogsService,
        WorkLogsRepository workLogsRepository,
        WorkLogsQueryService workLogsQueryService
    ) {
        this.workLogsService = workLogsService;
        this.workLogsRepository = workLogsRepository;
        this.workLogsQueryService = workLogsQueryService;
    }

    /**
     * {@code POST  /work-logs} : Create a new workLogs.
     *
     * @param workLogs the workLogs to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workLogs, or with status {@code 400 (Bad Request)} if the workLogs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/work-logs")
    public ResponseEntity<WorkLogs> createWorkLogs(@Valid @RequestBody WorkLogs workLogs) throws URISyntaxException {
        log.debug("REST request to save WorkLogs : {}", workLogs);
        if (workLogs.getId() != null) {
            throw new BadRequestAlertException("A new workLogs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkLogs result = workLogsService.save(workLogs);
        return ResponseEntity
            .created(new URI("/api/work-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /work-logs/:id} : Updates an existing workLogs.
     *
     * @param id the id of the workLogs to save.
     * @param workLogs the workLogs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workLogs,
     * or with status {@code 400 (Bad Request)} if the workLogs is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workLogs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/work-logs/{id}")
    public ResponseEntity<WorkLogs> updateWorkLogs(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody WorkLogs workLogs
    ) throws URISyntaxException {
        log.debug("REST request to update WorkLogs : {}, {}", id, workLogs);
        if (workLogs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workLogs.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workLogsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WorkLogs result = workLogsService.update(workLogs);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, workLogs.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /work-logs/:id} : Partial updates given fields of an existing workLogs, field will ignore if it is null
     *
     * @param id the id of the workLogs to save.
     * @param workLogs the workLogs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workLogs,
     * or with status {@code 400 (Bad Request)} if the workLogs is not valid,
     * or with status {@code 404 (Not Found)} if the workLogs is not found,
     * or with status {@code 500 (Internal Server Error)} if the workLogs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/work-logs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WorkLogs> partialUpdateWorkLogs(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody WorkLogs workLogs
    ) throws URISyntaxException {
        log.debug("REST request to partial update WorkLogs partially : {}, {}", id, workLogs);
        if (workLogs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workLogs.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workLogsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WorkLogs> result = workLogsService.partialUpdate(workLogs);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, workLogs.getId().toString())
        );
    }

    /**
     * {@code GET  /work-logs} : get all the workLogs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workLogs in body.
     */
    @GetMapping("/work-logs")
    public ResponseEntity<List<WorkLogs>> getAllWorkLogs(
        WorkLogsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get WorkLogs by criteria: {}", criteria);
        Page<WorkLogs> page = workLogsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /work-logs/count} : count all the workLogs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/work-logs/count")
    public ResponseEntity<Long> countWorkLogs(WorkLogsCriteria criteria) {
        log.debug("REST request to count WorkLogs by criteria: {}", criteria);
        return ResponseEntity.ok().body(workLogsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /work-logs/:id} : get the "id" workLogs.
     *
     * @param id the id of the workLogs to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workLogs, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/work-logs/{id}")
    public ResponseEntity<WorkLogs> getWorkLogs(@PathVariable Long id) {
        log.debug("REST request to get WorkLogs : {}", id);
        Optional<WorkLogs> workLogs = workLogsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workLogs);
    }

    /**
     * {@code DELETE  /work-logs/:id} : delete the "id" workLogs.
     *
     * @param id the id of the workLogs to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/work-logs/{id}")
    public ResponseEntity<Void> deleteWorkLogs(@PathVariable Long id) {
        log.debug("REST request to delete WorkLogs : {}", id);
        workLogsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
