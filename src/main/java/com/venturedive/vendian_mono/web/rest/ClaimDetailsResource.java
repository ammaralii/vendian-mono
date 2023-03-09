package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.ClaimDetails;
import com.venturedive.vendian_mono.repository.ClaimDetailsRepository;
import com.venturedive.vendian_mono.service.ClaimDetailsQueryService;
import com.venturedive.vendian_mono.service.ClaimDetailsService;
import com.venturedive.vendian_mono.service.criteria.ClaimDetailsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.ClaimDetails}.
 */
@RestController
@RequestMapping("/api")
public class ClaimDetailsResource {

    private final Logger log = LoggerFactory.getLogger(ClaimDetailsResource.class);

    private static final String ENTITY_NAME = "claimDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClaimDetailsService claimDetailsService;

    private final ClaimDetailsRepository claimDetailsRepository;

    private final ClaimDetailsQueryService claimDetailsQueryService;

    public ClaimDetailsResource(
        ClaimDetailsService claimDetailsService,
        ClaimDetailsRepository claimDetailsRepository,
        ClaimDetailsQueryService claimDetailsQueryService
    ) {
        this.claimDetailsService = claimDetailsService;
        this.claimDetailsRepository = claimDetailsRepository;
        this.claimDetailsQueryService = claimDetailsQueryService;
    }

    /**
     * {@code POST  /claim-details} : Create a new claimDetails.
     *
     * @param claimDetails the claimDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new claimDetails, or with status {@code 400 (Bad Request)} if the claimDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/claim-details")
    public ResponseEntity<ClaimDetails> createClaimDetails(@Valid @RequestBody ClaimDetails claimDetails) throws URISyntaxException {
        log.debug("REST request to save ClaimDetails : {}", claimDetails);
        if (claimDetails.getId() != null) {
            throw new BadRequestAlertException("A new claimDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClaimDetails result = claimDetailsService.save(claimDetails);
        return ResponseEntity
            .created(new URI("/api/claim-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /claim-details/:id} : Updates an existing claimDetails.
     *
     * @param id the id of the claimDetails to save.
     * @param claimDetails the claimDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated claimDetails,
     * or with status {@code 400 (Bad Request)} if the claimDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the claimDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/claim-details/{id}")
    public ResponseEntity<ClaimDetails> updateClaimDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ClaimDetails claimDetails
    ) throws URISyntaxException {
        log.debug("REST request to update ClaimDetails : {}, {}", id, claimDetails);
        if (claimDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, claimDetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!claimDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClaimDetails result = claimDetailsService.update(claimDetails);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, claimDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /claim-details/:id} : Partial updates given fields of an existing claimDetails, field will ignore if it is null
     *
     * @param id the id of the claimDetails to save.
     * @param claimDetails the claimDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated claimDetails,
     * or with status {@code 400 (Bad Request)} if the claimDetails is not valid,
     * or with status {@code 404 (Not Found)} if the claimDetails is not found,
     * or with status {@code 500 (Internal Server Error)} if the claimDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/claim-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ClaimDetails> partialUpdateClaimDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ClaimDetails claimDetails
    ) throws URISyntaxException {
        log.debug("REST request to partial update ClaimDetails partially : {}, {}", id, claimDetails);
        if (claimDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, claimDetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!claimDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClaimDetails> result = claimDetailsService.partialUpdate(claimDetails);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, claimDetails.getId().toString())
        );
    }

    /**
     * {@code GET  /claim-details} : get all the claimDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of claimDetails in body.
     */
    @GetMapping("/claim-details")
    public ResponseEntity<List<ClaimDetails>> getAllClaimDetails(
        ClaimDetailsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ClaimDetails by criteria: {}", criteria);
        Page<ClaimDetails> page = claimDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /claim-details/count} : count all the claimDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/claim-details/count")
    public ResponseEntity<Long> countClaimDetails(ClaimDetailsCriteria criteria) {
        log.debug("REST request to count ClaimDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(claimDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /claim-details/:id} : get the "id" claimDetails.
     *
     * @param id the id of the claimDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the claimDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/claim-details/{id}")
    public ResponseEntity<ClaimDetails> getClaimDetails(@PathVariable Long id) {
        log.debug("REST request to get ClaimDetails : {}", id);
        Optional<ClaimDetails> claimDetails = claimDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(claimDetails);
    }

    /**
     * {@code DELETE  /claim-details/:id} : delete the "id" claimDetails.
     *
     * @param id the id of the claimDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/claim-details/{id}")
    public ResponseEntity<Void> deleteClaimDetails(@PathVariable Long id) {
        log.debug("REST request to delete ClaimDetails : {}", id);
        claimDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
