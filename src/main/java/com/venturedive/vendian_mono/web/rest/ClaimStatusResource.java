package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.ClaimStatus;
import com.venturedive.vendian_mono.repository.ClaimStatusRepository;
import com.venturedive.vendian_mono.service.ClaimStatusQueryService;
import com.venturedive.vendian_mono.service.ClaimStatusService;
import com.venturedive.vendian_mono.service.criteria.ClaimStatusCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.ClaimStatus}.
 */
@RestController
@RequestMapping("/api")
public class ClaimStatusResource {

    private final Logger log = LoggerFactory.getLogger(ClaimStatusResource.class);

    private static final String ENTITY_NAME = "claimStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClaimStatusService claimStatusService;

    private final ClaimStatusRepository claimStatusRepository;

    private final ClaimStatusQueryService claimStatusQueryService;

    public ClaimStatusResource(
        ClaimStatusService claimStatusService,
        ClaimStatusRepository claimStatusRepository,
        ClaimStatusQueryService claimStatusQueryService
    ) {
        this.claimStatusService = claimStatusService;
        this.claimStatusRepository = claimStatusRepository;
        this.claimStatusQueryService = claimStatusQueryService;
    }

    /**
     * {@code POST  /claim-statuses} : Create a new claimStatus.
     *
     * @param claimStatus the claimStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new claimStatus, or with status {@code 400 (Bad Request)} if the claimStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/claim-statuses")
    public ResponseEntity<ClaimStatus> createClaimStatus(@Valid @RequestBody ClaimStatus claimStatus) throws URISyntaxException {
        log.debug("REST request to save ClaimStatus : {}", claimStatus);
        if (claimStatus.getId() != null) {
            throw new BadRequestAlertException("A new claimStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClaimStatus result = claimStatusService.save(claimStatus);
        return ResponseEntity
            .created(new URI("/api/claim-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /claim-statuses/:id} : Updates an existing claimStatus.
     *
     * @param id the id of the claimStatus to save.
     * @param claimStatus the claimStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated claimStatus,
     * or with status {@code 400 (Bad Request)} if the claimStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the claimStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/claim-statuses/{id}")
    public ResponseEntity<ClaimStatus> updateClaimStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ClaimStatus claimStatus
    ) throws URISyntaxException {
        log.debug("REST request to update ClaimStatus : {}, {}", id, claimStatus);
        if (claimStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, claimStatus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!claimStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClaimStatus result = claimStatusService.update(claimStatus);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, claimStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /claim-statuses/:id} : Partial updates given fields of an existing claimStatus, field will ignore if it is null
     *
     * @param id the id of the claimStatus to save.
     * @param claimStatus the claimStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated claimStatus,
     * or with status {@code 400 (Bad Request)} if the claimStatus is not valid,
     * or with status {@code 404 (Not Found)} if the claimStatus is not found,
     * or with status {@code 500 (Internal Server Error)} if the claimStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/claim-statuses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ClaimStatus> partialUpdateClaimStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ClaimStatus claimStatus
    ) throws URISyntaxException {
        log.debug("REST request to partial update ClaimStatus partially : {}, {}", id, claimStatus);
        if (claimStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, claimStatus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!claimStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClaimStatus> result = claimStatusService.partialUpdate(claimStatus);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, claimStatus.getId().toString())
        );
    }

    /**
     * {@code GET  /claim-statuses} : get all the claimStatuses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of claimStatuses in body.
     */
    @GetMapping("/claim-statuses")
    public ResponseEntity<List<ClaimStatus>> getAllClaimStatuses(
        ClaimStatusCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ClaimStatuses by criteria: {}", criteria);
        Page<ClaimStatus> page = claimStatusQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /claim-statuses/count} : count all the claimStatuses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/claim-statuses/count")
    public ResponseEntity<Long> countClaimStatuses(ClaimStatusCriteria criteria) {
        log.debug("REST request to count ClaimStatuses by criteria: {}", criteria);
        return ResponseEntity.ok().body(claimStatusQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /claim-statuses/:id} : get the "id" claimStatus.
     *
     * @param id the id of the claimStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the claimStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/claim-statuses/{id}")
    public ResponseEntity<ClaimStatus> getClaimStatus(@PathVariable Long id) {
        log.debug("REST request to get ClaimStatus : {}", id);
        Optional<ClaimStatus> claimStatus = claimStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(claimStatus);
    }

    /**
     * {@code DELETE  /claim-statuses/:id} : delete the "id" claimStatus.
     *
     * @param id the id of the claimStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/claim-statuses/{id}")
    public ResponseEntity<Void> deleteClaimStatus(@PathVariable Long id) {
        log.debug("REST request to delete ClaimStatus : {}", id);
        claimStatusService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
