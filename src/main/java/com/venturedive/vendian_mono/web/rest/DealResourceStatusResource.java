package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.DealResourceStatus;
import com.venturedive.vendian_mono.repository.DealResourceStatusRepository;
import com.venturedive.vendian_mono.service.DealResourceStatusQueryService;
import com.venturedive.vendian_mono.service.DealResourceStatusService;
import com.venturedive.vendian_mono.service.criteria.DealResourceStatusCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.DealResourceStatus}.
 */
@RestController
@RequestMapping("/api")
public class DealResourceStatusResource {

    private final Logger log = LoggerFactory.getLogger(DealResourceStatusResource.class);

    private static final String ENTITY_NAME = "dealResourceStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DealResourceStatusService dealResourceStatusService;

    private final DealResourceStatusRepository dealResourceStatusRepository;

    private final DealResourceStatusQueryService dealResourceStatusQueryService;

    public DealResourceStatusResource(
        DealResourceStatusService dealResourceStatusService,
        DealResourceStatusRepository dealResourceStatusRepository,
        DealResourceStatusQueryService dealResourceStatusQueryService
    ) {
        this.dealResourceStatusService = dealResourceStatusService;
        this.dealResourceStatusRepository = dealResourceStatusRepository;
        this.dealResourceStatusQueryService = dealResourceStatusQueryService;
    }

    /**
     * {@code POST  /deal-resource-statuses} : Create a new dealResourceStatus.
     *
     * @param dealResourceStatus the dealResourceStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dealResourceStatus, or with status {@code 400 (Bad Request)} if the dealResourceStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/deal-resource-statuses")
    public ResponseEntity<DealResourceStatus> createDealResourceStatus(@Valid @RequestBody DealResourceStatus dealResourceStatus)
        throws URISyntaxException {
        log.debug("REST request to save DealResourceStatus : {}", dealResourceStatus);
        if (dealResourceStatus.getId() != null) {
            throw new BadRequestAlertException("A new dealResourceStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DealResourceStatus result = dealResourceStatusService.save(dealResourceStatus);
        return ResponseEntity
            .created(new URI("/api/deal-resource-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /deal-resource-statuses/:id} : Updates an existing dealResourceStatus.
     *
     * @param id the id of the dealResourceStatus to save.
     * @param dealResourceStatus the dealResourceStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dealResourceStatus,
     * or with status {@code 400 (Bad Request)} if the dealResourceStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dealResourceStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/deal-resource-statuses/{id}")
    public ResponseEntity<DealResourceStatus> updateDealResourceStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DealResourceStatus dealResourceStatus
    ) throws URISyntaxException {
        log.debug("REST request to update DealResourceStatus : {}, {}", id, dealResourceStatus);
        if (dealResourceStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dealResourceStatus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dealResourceStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DealResourceStatus result = dealResourceStatusService.update(dealResourceStatus);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dealResourceStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /deal-resource-statuses/:id} : Partial updates given fields of an existing dealResourceStatus, field will ignore if it is null
     *
     * @param id the id of the dealResourceStatus to save.
     * @param dealResourceStatus the dealResourceStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dealResourceStatus,
     * or with status {@code 400 (Bad Request)} if the dealResourceStatus is not valid,
     * or with status {@code 404 (Not Found)} if the dealResourceStatus is not found,
     * or with status {@code 500 (Internal Server Error)} if the dealResourceStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/deal-resource-statuses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DealResourceStatus> partialUpdateDealResourceStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DealResourceStatus dealResourceStatus
    ) throws URISyntaxException {
        log.debug("REST request to partial update DealResourceStatus partially : {}, {}", id, dealResourceStatus);
        if (dealResourceStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dealResourceStatus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dealResourceStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DealResourceStatus> result = dealResourceStatusService.partialUpdate(dealResourceStatus);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dealResourceStatus.getId().toString())
        );
    }

    /**
     * {@code GET  /deal-resource-statuses} : get all the dealResourceStatuses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dealResourceStatuses in body.
     */
    @GetMapping("/deal-resource-statuses")
    public ResponseEntity<List<DealResourceStatus>> getAllDealResourceStatuses(
        DealResourceStatusCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get DealResourceStatuses by criteria: {}", criteria);
        Page<DealResourceStatus> page = dealResourceStatusQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /deal-resource-statuses/count} : count all the dealResourceStatuses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/deal-resource-statuses/count")
    public ResponseEntity<Long> countDealResourceStatuses(DealResourceStatusCriteria criteria) {
        log.debug("REST request to count DealResourceStatuses by criteria: {}", criteria);
        return ResponseEntity.ok().body(dealResourceStatusQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /deal-resource-statuses/:id} : get the "id" dealResourceStatus.
     *
     * @param id the id of the dealResourceStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dealResourceStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/deal-resource-statuses/{id}")
    public ResponseEntity<DealResourceStatus> getDealResourceStatus(@PathVariable Long id) {
        log.debug("REST request to get DealResourceStatus : {}", id);
        Optional<DealResourceStatus> dealResourceStatus = dealResourceStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dealResourceStatus);
    }

    /**
     * {@code DELETE  /deal-resource-statuses/:id} : delete the "id" dealResourceStatus.
     *
     * @param id the id of the dealResourceStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/deal-resource-statuses/{id}")
    public ResponseEntity<Void> deleteDealResourceStatus(@PathVariable Long id) {
        log.debug("REST request to delete DealResourceStatus : {}", id);
        dealResourceStatusService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
