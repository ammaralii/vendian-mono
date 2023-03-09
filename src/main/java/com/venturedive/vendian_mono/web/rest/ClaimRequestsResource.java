package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.ClaimRequests;
import com.venturedive.vendian_mono.repository.ClaimRequestsRepository;
import com.venturedive.vendian_mono.service.ClaimRequestsQueryService;
import com.venturedive.vendian_mono.service.ClaimRequestsService;
import com.venturedive.vendian_mono.service.criteria.ClaimRequestsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.ClaimRequests}.
 */
@RestController
@RequestMapping("/api")
public class ClaimRequestsResource {

    private final Logger log = LoggerFactory.getLogger(ClaimRequestsResource.class);

    private static final String ENTITY_NAME = "claimRequests";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClaimRequestsService claimRequestsService;

    private final ClaimRequestsRepository claimRequestsRepository;

    private final ClaimRequestsQueryService claimRequestsQueryService;

    public ClaimRequestsResource(
        ClaimRequestsService claimRequestsService,
        ClaimRequestsRepository claimRequestsRepository,
        ClaimRequestsQueryService claimRequestsQueryService
    ) {
        this.claimRequestsService = claimRequestsService;
        this.claimRequestsRepository = claimRequestsRepository;
        this.claimRequestsQueryService = claimRequestsQueryService;
    }

    /**
     * {@code POST  /claim-requests} : Create a new claimRequests.
     *
     * @param claimRequests the claimRequests to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new claimRequests, or with status {@code 400 (Bad Request)} if the claimRequests has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/claim-requests")
    public ResponseEntity<ClaimRequests> createClaimRequests(@Valid @RequestBody ClaimRequests claimRequests) throws URISyntaxException {
        log.debug("REST request to save ClaimRequests : {}", claimRequests);
        if (claimRequests.getId() != null) {
            throw new BadRequestAlertException("A new claimRequests cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClaimRequests result = claimRequestsService.save(claimRequests);
        return ResponseEntity
            .created(new URI("/api/claim-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /claim-requests/:id} : Updates an existing claimRequests.
     *
     * @param id the id of the claimRequests to save.
     * @param claimRequests the claimRequests to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated claimRequests,
     * or with status {@code 400 (Bad Request)} if the claimRequests is not valid,
     * or with status {@code 500 (Internal Server Error)} if the claimRequests couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/claim-requests/{id}")
    public ResponseEntity<ClaimRequests> updateClaimRequests(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ClaimRequests claimRequests
    ) throws URISyntaxException {
        log.debug("REST request to update ClaimRequests : {}, {}", id, claimRequests);
        if (claimRequests.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, claimRequests.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!claimRequestsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClaimRequests result = claimRequestsService.update(claimRequests);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, claimRequests.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /claim-requests/:id} : Partial updates given fields of an existing claimRequests, field will ignore if it is null
     *
     * @param id the id of the claimRequests to save.
     * @param claimRequests the claimRequests to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated claimRequests,
     * or with status {@code 400 (Bad Request)} if the claimRequests is not valid,
     * or with status {@code 404 (Not Found)} if the claimRequests is not found,
     * or with status {@code 500 (Internal Server Error)} if the claimRequests couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/claim-requests/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ClaimRequests> partialUpdateClaimRequests(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ClaimRequests claimRequests
    ) throws URISyntaxException {
        log.debug("REST request to partial update ClaimRequests partially : {}, {}", id, claimRequests);
        if (claimRequests.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, claimRequests.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!claimRequestsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClaimRequests> result = claimRequestsService.partialUpdate(claimRequests);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, claimRequests.getId().toString())
        );
    }

    /**
     * {@code GET  /claim-requests} : get all the claimRequests.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of claimRequests in body.
     */
    @GetMapping("/claim-requests")
    public ResponseEntity<List<ClaimRequests>> getAllClaimRequests(
        ClaimRequestsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ClaimRequests by criteria: {}", criteria);
        Page<ClaimRequests> page = claimRequestsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /claim-requests/count} : count all the claimRequests.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/claim-requests/count")
    public ResponseEntity<Long> countClaimRequests(ClaimRequestsCriteria criteria) {
        log.debug("REST request to count ClaimRequests by criteria: {}", criteria);
        return ResponseEntity.ok().body(claimRequestsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /claim-requests/:id} : get the "id" claimRequests.
     *
     * @param id the id of the claimRequests to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the claimRequests, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/claim-requests/{id}")
    public ResponseEntity<ClaimRequests> getClaimRequests(@PathVariable Long id) {
        log.debug("REST request to get ClaimRequests : {}", id);
        Optional<ClaimRequests> claimRequests = claimRequestsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(claimRequests);
    }

    /**
     * {@code DELETE  /claim-requests/:id} : delete the "id" claimRequests.
     *
     * @param id the id of the claimRequests to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/claim-requests/{id}")
    public ResponseEntity<Void> deleteClaimRequests(@PathVariable Long id) {
        log.debug("REST request to delete ClaimRequests : {}", id);
        claimRequestsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
