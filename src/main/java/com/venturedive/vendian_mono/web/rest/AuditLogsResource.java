package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.AuditLogs;
import com.venturedive.vendian_mono.repository.AuditLogsRepository;
import com.venturedive.vendian_mono.service.AuditLogsQueryService;
import com.venturedive.vendian_mono.service.AuditLogsService;
import com.venturedive.vendian_mono.service.criteria.AuditLogsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.AuditLogs}.
 */
@RestController
@RequestMapping("/api")
public class AuditLogsResource {

    private final Logger log = LoggerFactory.getLogger(AuditLogsResource.class);

    private static final String ENTITY_NAME = "auditLogs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AuditLogsService auditLogsService;

    private final AuditLogsRepository auditLogsRepository;

    private final AuditLogsQueryService auditLogsQueryService;

    public AuditLogsResource(
        AuditLogsService auditLogsService,
        AuditLogsRepository auditLogsRepository,
        AuditLogsQueryService auditLogsQueryService
    ) {
        this.auditLogsService = auditLogsService;
        this.auditLogsRepository = auditLogsRepository;
        this.auditLogsQueryService = auditLogsQueryService;
    }

    /**
     * {@code POST  /audit-logs} : Create a new auditLogs.
     *
     * @param auditLogs the auditLogs to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new auditLogs, or with status {@code 400 (Bad Request)} if the auditLogs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/audit-logs")
    public ResponseEntity<AuditLogs> createAuditLogs(@Valid @RequestBody AuditLogs auditLogs) throws URISyntaxException {
        log.debug("REST request to save AuditLogs : {}", auditLogs);
        if (auditLogs.getId() != null) {
            throw new BadRequestAlertException("A new auditLogs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AuditLogs result = auditLogsService.save(auditLogs);
        return ResponseEntity
            .created(new URI("/api/audit-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /audit-logs/:id} : Updates an existing auditLogs.
     *
     * @param id the id of the auditLogs to save.
     * @param auditLogs the auditLogs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated auditLogs,
     * or with status {@code 400 (Bad Request)} if the auditLogs is not valid,
     * or with status {@code 500 (Internal Server Error)} if the auditLogs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/audit-logs/{id}")
    public ResponseEntity<AuditLogs> updateAuditLogs(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AuditLogs auditLogs
    ) throws URISyntaxException {
        log.debug("REST request to update AuditLogs : {}, {}", id, auditLogs);
        if (auditLogs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, auditLogs.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!auditLogsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AuditLogs result = auditLogsService.update(auditLogs);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, auditLogs.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /audit-logs/:id} : Partial updates given fields of an existing auditLogs, field will ignore if it is null
     *
     * @param id the id of the auditLogs to save.
     * @param auditLogs the auditLogs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated auditLogs,
     * or with status {@code 400 (Bad Request)} if the auditLogs is not valid,
     * or with status {@code 404 (Not Found)} if the auditLogs is not found,
     * or with status {@code 500 (Internal Server Error)} if the auditLogs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/audit-logs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AuditLogs> partialUpdateAuditLogs(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AuditLogs auditLogs
    ) throws URISyntaxException {
        log.debug("REST request to partial update AuditLogs partially : {}, {}", id, auditLogs);
        if (auditLogs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, auditLogs.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!auditLogsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AuditLogs> result = auditLogsService.partialUpdate(auditLogs);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, auditLogs.getId().toString())
        );
    }

    /**
     * {@code GET  /audit-logs} : get all the auditLogs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of auditLogs in body.
     */
    @GetMapping("/audit-logs")
    public ResponseEntity<List<AuditLogs>> getAllAuditLogs(
        AuditLogsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get AuditLogs by criteria: {}", criteria);
        Page<AuditLogs> page = auditLogsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /audit-logs/count} : count all the auditLogs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/audit-logs/count")
    public ResponseEntity<Long> countAuditLogs(AuditLogsCriteria criteria) {
        log.debug("REST request to count AuditLogs by criteria: {}", criteria);
        return ResponseEntity.ok().body(auditLogsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /audit-logs/:id} : get the "id" auditLogs.
     *
     * @param id the id of the auditLogs to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the auditLogs, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/audit-logs/{id}")
    public ResponseEntity<AuditLogs> getAuditLogs(@PathVariable Long id) {
        log.debug("REST request to get AuditLogs : {}", id);
        Optional<AuditLogs> auditLogs = auditLogsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(auditLogs);
    }

    /**
     * {@code DELETE  /audit-logs/:id} : delete the "id" auditLogs.
     *
     * @param id the id of the auditLogs to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/audit-logs/{id}")
    public ResponseEntity<Void> deleteAuditLogs(@PathVariable Long id) {
        log.debug("REST request to delete AuditLogs : {}", id);
        auditLogsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
