package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.LeaveDetailAdjustmentLogs;
import com.venturedive.vendian_mono.repository.LeaveDetailAdjustmentLogsRepository;
import com.venturedive.vendian_mono.service.LeaveDetailAdjustmentLogsQueryService;
import com.venturedive.vendian_mono.service.LeaveDetailAdjustmentLogsService;
import com.venturedive.vendian_mono.service.criteria.LeaveDetailAdjustmentLogsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.LeaveDetailAdjustmentLogs}.
 */
@RestController
@RequestMapping("/api")
public class LeaveDetailAdjustmentLogsResource {

    private final Logger log = LoggerFactory.getLogger(LeaveDetailAdjustmentLogsResource.class);

    private static final String ENTITY_NAME = "leaveDetailAdjustmentLogs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeaveDetailAdjustmentLogsService leaveDetailAdjustmentLogsService;

    private final LeaveDetailAdjustmentLogsRepository leaveDetailAdjustmentLogsRepository;

    private final LeaveDetailAdjustmentLogsQueryService leaveDetailAdjustmentLogsQueryService;

    public LeaveDetailAdjustmentLogsResource(
        LeaveDetailAdjustmentLogsService leaveDetailAdjustmentLogsService,
        LeaveDetailAdjustmentLogsRepository leaveDetailAdjustmentLogsRepository,
        LeaveDetailAdjustmentLogsQueryService leaveDetailAdjustmentLogsQueryService
    ) {
        this.leaveDetailAdjustmentLogsService = leaveDetailAdjustmentLogsService;
        this.leaveDetailAdjustmentLogsRepository = leaveDetailAdjustmentLogsRepository;
        this.leaveDetailAdjustmentLogsQueryService = leaveDetailAdjustmentLogsQueryService;
    }

    /**
     * {@code POST  /leave-detail-adjustment-logs} : Create a new leaveDetailAdjustmentLogs.
     *
     * @param leaveDetailAdjustmentLogs the leaveDetailAdjustmentLogs to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leaveDetailAdjustmentLogs, or with status {@code 400 (Bad Request)} if the leaveDetailAdjustmentLogs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/leave-detail-adjustment-logs")
    public ResponseEntity<LeaveDetailAdjustmentLogs> createLeaveDetailAdjustmentLogs(
        @Valid @RequestBody LeaveDetailAdjustmentLogs leaveDetailAdjustmentLogs
    ) throws URISyntaxException {
        log.debug("REST request to save LeaveDetailAdjustmentLogs : {}", leaveDetailAdjustmentLogs);
        if (leaveDetailAdjustmentLogs.getId() != null) {
            throw new BadRequestAlertException("A new leaveDetailAdjustmentLogs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeaveDetailAdjustmentLogs result = leaveDetailAdjustmentLogsService.save(leaveDetailAdjustmentLogs);
        return ResponseEntity
            .created(new URI("/api/leave-detail-adjustment-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /leave-detail-adjustment-logs/:id} : Updates an existing leaveDetailAdjustmentLogs.
     *
     * @param id the id of the leaveDetailAdjustmentLogs to save.
     * @param leaveDetailAdjustmentLogs the leaveDetailAdjustmentLogs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveDetailAdjustmentLogs,
     * or with status {@code 400 (Bad Request)} if the leaveDetailAdjustmentLogs is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leaveDetailAdjustmentLogs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/leave-detail-adjustment-logs/{id}")
    public ResponseEntity<LeaveDetailAdjustmentLogs> updateLeaveDetailAdjustmentLogs(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LeaveDetailAdjustmentLogs leaveDetailAdjustmentLogs
    ) throws URISyntaxException {
        log.debug("REST request to update LeaveDetailAdjustmentLogs : {}, {}", id, leaveDetailAdjustmentLogs);
        if (leaveDetailAdjustmentLogs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveDetailAdjustmentLogs.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveDetailAdjustmentLogsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LeaveDetailAdjustmentLogs result = leaveDetailAdjustmentLogsService.update(leaveDetailAdjustmentLogs);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveDetailAdjustmentLogs.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /leave-detail-adjustment-logs/:id} : Partial updates given fields of an existing leaveDetailAdjustmentLogs, field will ignore if it is null
     *
     * @param id the id of the leaveDetailAdjustmentLogs to save.
     * @param leaveDetailAdjustmentLogs the leaveDetailAdjustmentLogs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveDetailAdjustmentLogs,
     * or with status {@code 400 (Bad Request)} if the leaveDetailAdjustmentLogs is not valid,
     * or with status {@code 404 (Not Found)} if the leaveDetailAdjustmentLogs is not found,
     * or with status {@code 500 (Internal Server Error)} if the leaveDetailAdjustmentLogs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/leave-detail-adjustment-logs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LeaveDetailAdjustmentLogs> partialUpdateLeaveDetailAdjustmentLogs(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LeaveDetailAdjustmentLogs leaveDetailAdjustmentLogs
    ) throws URISyntaxException {
        log.debug("REST request to partial update LeaveDetailAdjustmentLogs partially : {}, {}", id, leaveDetailAdjustmentLogs);
        if (leaveDetailAdjustmentLogs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveDetailAdjustmentLogs.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveDetailAdjustmentLogsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LeaveDetailAdjustmentLogs> result = leaveDetailAdjustmentLogsService.partialUpdate(leaveDetailAdjustmentLogs);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveDetailAdjustmentLogs.getId().toString())
        );
    }

    /**
     * {@code GET  /leave-detail-adjustment-logs} : get all the leaveDetailAdjustmentLogs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leaveDetailAdjustmentLogs in body.
     */
    @GetMapping("/leave-detail-adjustment-logs")
    public ResponseEntity<List<LeaveDetailAdjustmentLogs>> getAllLeaveDetailAdjustmentLogs(
        LeaveDetailAdjustmentLogsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LeaveDetailAdjustmentLogs by criteria: {}", criteria);
        Page<LeaveDetailAdjustmentLogs> page = leaveDetailAdjustmentLogsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /leave-detail-adjustment-logs/count} : count all the leaveDetailAdjustmentLogs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/leave-detail-adjustment-logs/count")
    public ResponseEntity<Long> countLeaveDetailAdjustmentLogs(LeaveDetailAdjustmentLogsCriteria criteria) {
        log.debug("REST request to count LeaveDetailAdjustmentLogs by criteria: {}", criteria);
        return ResponseEntity.ok().body(leaveDetailAdjustmentLogsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /leave-detail-adjustment-logs/:id} : get the "id" leaveDetailAdjustmentLogs.
     *
     * @param id the id of the leaveDetailAdjustmentLogs to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leaveDetailAdjustmentLogs, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/leave-detail-adjustment-logs/{id}")
    public ResponseEntity<LeaveDetailAdjustmentLogs> getLeaveDetailAdjustmentLogs(@PathVariable Long id) {
        log.debug("REST request to get LeaveDetailAdjustmentLogs : {}", id);
        Optional<LeaveDetailAdjustmentLogs> leaveDetailAdjustmentLogs = leaveDetailAdjustmentLogsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leaveDetailAdjustmentLogs);
    }

    /**
     * {@code DELETE  /leave-detail-adjustment-logs/:id} : delete the "id" leaveDetailAdjustmentLogs.
     *
     * @param id the id of the leaveDetailAdjustmentLogs to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/leave-detail-adjustment-logs/{id}")
    public ResponseEntity<Void> deleteLeaveDetailAdjustmentLogs(@PathVariable Long id) {
        log.debug("REST request to delete LeaveDetailAdjustmentLogs : {}", id);
        leaveDetailAdjustmentLogsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
