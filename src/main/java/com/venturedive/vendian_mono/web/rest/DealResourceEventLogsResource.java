package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.DealResourceEventLogs;
import com.venturedive.vendian_mono.repository.DealResourceEventLogsRepository;
import com.venturedive.vendian_mono.service.DealResourceEventLogsQueryService;
import com.venturedive.vendian_mono.service.DealResourceEventLogsService;
import com.venturedive.vendian_mono.service.criteria.DealResourceEventLogsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.DealResourceEventLogs}.
 */
@RestController
@RequestMapping("/api")
public class DealResourceEventLogsResource {

    private final Logger log = LoggerFactory.getLogger(DealResourceEventLogsResource.class);

    private static final String ENTITY_NAME = "dealResourceEventLogs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DealResourceEventLogsService dealResourceEventLogsService;

    private final DealResourceEventLogsRepository dealResourceEventLogsRepository;

    private final DealResourceEventLogsQueryService dealResourceEventLogsQueryService;

    public DealResourceEventLogsResource(
        DealResourceEventLogsService dealResourceEventLogsService,
        DealResourceEventLogsRepository dealResourceEventLogsRepository,
        DealResourceEventLogsQueryService dealResourceEventLogsQueryService
    ) {
        this.dealResourceEventLogsService = dealResourceEventLogsService;
        this.dealResourceEventLogsRepository = dealResourceEventLogsRepository;
        this.dealResourceEventLogsQueryService = dealResourceEventLogsQueryService;
    }

    /**
     * {@code POST  /deal-resource-event-logs} : Create a new dealResourceEventLogs.
     *
     * @param dealResourceEventLogs the dealResourceEventLogs to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dealResourceEventLogs, or with status {@code 400 (Bad Request)} if the dealResourceEventLogs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/deal-resource-event-logs")
    public ResponseEntity<DealResourceEventLogs> createDealResourceEventLogs(
        @Valid @RequestBody DealResourceEventLogs dealResourceEventLogs
    ) throws URISyntaxException {
        log.debug("REST request to save DealResourceEventLogs : {}", dealResourceEventLogs);
        if (dealResourceEventLogs.getId() != null) {
            throw new BadRequestAlertException("A new dealResourceEventLogs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DealResourceEventLogs result = dealResourceEventLogsService.save(dealResourceEventLogs);
        return ResponseEntity
            .created(new URI("/api/deal-resource-event-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /deal-resource-event-logs/:id} : Updates an existing dealResourceEventLogs.
     *
     * @param id the id of the dealResourceEventLogs to save.
     * @param dealResourceEventLogs the dealResourceEventLogs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dealResourceEventLogs,
     * or with status {@code 400 (Bad Request)} if the dealResourceEventLogs is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dealResourceEventLogs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/deal-resource-event-logs/{id}")
    public ResponseEntity<DealResourceEventLogs> updateDealResourceEventLogs(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DealResourceEventLogs dealResourceEventLogs
    ) throws URISyntaxException {
        log.debug("REST request to update DealResourceEventLogs : {}, {}", id, dealResourceEventLogs);
        if (dealResourceEventLogs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dealResourceEventLogs.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dealResourceEventLogsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DealResourceEventLogs result = dealResourceEventLogsService.update(dealResourceEventLogs);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dealResourceEventLogs.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /deal-resource-event-logs/:id} : Partial updates given fields of an existing dealResourceEventLogs, field will ignore if it is null
     *
     * @param id the id of the dealResourceEventLogs to save.
     * @param dealResourceEventLogs the dealResourceEventLogs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dealResourceEventLogs,
     * or with status {@code 400 (Bad Request)} if the dealResourceEventLogs is not valid,
     * or with status {@code 404 (Not Found)} if the dealResourceEventLogs is not found,
     * or with status {@code 500 (Internal Server Error)} if the dealResourceEventLogs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/deal-resource-event-logs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DealResourceEventLogs> partialUpdateDealResourceEventLogs(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DealResourceEventLogs dealResourceEventLogs
    ) throws URISyntaxException {
        log.debug("REST request to partial update DealResourceEventLogs partially : {}, {}", id, dealResourceEventLogs);
        if (dealResourceEventLogs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dealResourceEventLogs.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dealResourceEventLogsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DealResourceEventLogs> result = dealResourceEventLogsService.partialUpdate(dealResourceEventLogs);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dealResourceEventLogs.getId().toString())
        );
    }

    /**
     * {@code GET  /deal-resource-event-logs} : get all the dealResourceEventLogs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dealResourceEventLogs in body.
     */
    @GetMapping("/deal-resource-event-logs")
    public ResponseEntity<List<DealResourceEventLogs>> getAllDealResourceEventLogs(
        DealResourceEventLogsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get DealResourceEventLogs by criteria: {}", criteria);
        Page<DealResourceEventLogs> page = dealResourceEventLogsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /deal-resource-event-logs/count} : count all the dealResourceEventLogs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/deal-resource-event-logs/count")
    public ResponseEntity<Long> countDealResourceEventLogs(DealResourceEventLogsCriteria criteria) {
        log.debug("REST request to count DealResourceEventLogs by criteria: {}", criteria);
        return ResponseEntity.ok().body(dealResourceEventLogsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /deal-resource-event-logs/:id} : get the "id" dealResourceEventLogs.
     *
     * @param id the id of the dealResourceEventLogs to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dealResourceEventLogs, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/deal-resource-event-logs/{id}")
    public ResponseEntity<DealResourceEventLogs> getDealResourceEventLogs(@PathVariable Long id) {
        log.debug("REST request to get DealResourceEventLogs : {}", id);
        Optional<DealResourceEventLogs> dealResourceEventLogs = dealResourceEventLogsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dealResourceEventLogs);
    }

    /**
     * {@code DELETE  /deal-resource-event-logs/:id} : delete the "id" dealResourceEventLogs.
     *
     * @param id the id of the dealResourceEventLogs to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/deal-resource-event-logs/{id}")
    public ResponseEntity<Void> deleteDealResourceEventLogs(@PathVariable Long id) {
        log.debug("REST request to delete DealResourceEventLogs : {}", id);
        dealResourceEventLogsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
