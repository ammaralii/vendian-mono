package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.ClaimRequestViews;
import com.venturedive.vendian_mono.repository.ClaimRequestViewsRepository;
import com.venturedive.vendian_mono.service.ClaimRequestViewsQueryService;
import com.venturedive.vendian_mono.service.ClaimRequestViewsService;
import com.venturedive.vendian_mono.service.criteria.ClaimRequestViewsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.ClaimRequestViews}.
 */
@RestController
@RequestMapping("/api")
public class ClaimRequestViewsResource {

    private final Logger log = LoggerFactory.getLogger(ClaimRequestViewsResource.class);

    private static final String ENTITY_NAME = "claimRequestViews";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClaimRequestViewsService claimRequestViewsService;

    private final ClaimRequestViewsRepository claimRequestViewsRepository;

    private final ClaimRequestViewsQueryService claimRequestViewsQueryService;

    public ClaimRequestViewsResource(
        ClaimRequestViewsService claimRequestViewsService,
        ClaimRequestViewsRepository claimRequestViewsRepository,
        ClaimRequestViewsQueryService claimRequestViewsQueryService
    ) {
        this.claimRequestViewsService = claimRequestViewsService;
        this.claimRequestViewsRepository = claimRequestViewsRepository;
        this.claimRequestViewsQueryService = claimRequestViewsQueryService;
    }

    /**
     * {@code POST  /claim-request-views} : Create a new claimRequestViews.
     *
     * @param claimRequestViews the claimRequestViews to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new claimRequestViews, or with status {@code 400 (Bad Request)} if the claimRequestViews has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/claim-request-views")
    public ResponseEntity<ClaimRequestViews> createClaimRequestViews(@Valid @RequestBody ClaimRequestViews claimRequestViews)
        throws URISyntaxException {
        log.debug("REST request to save ClaimRequestViews : {}", claimRequestViews);
        if (claimRequestViews.getId() != null) {
            throw new BadRequestAlertException("A new claimRequestViews cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClaimRequestViews result = claimRequestViewsService.save(claimRequestViews);
        return ResponseEntity
            .created(new URI("/api/claim-request-views/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /claim-request-views/:id} : Updates an existing claimRequestViews.
     *
     * @param id the id of the claimRequestViews to save.
     * @param claimRequestViews the claimRequestViews to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated claimRequestViews,
     * or with status {@code 400 (Bad Request)} if the claimRequestViews is not valid,
     * or with status {@code 500 (Internal Server Error)} if the claimRequestViews couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/claim-request-views/{id}")
    public ResponseEntity<ClaimRequestViews> updateClaimRequestViews(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ClaimRequestViews claimRequestViews
    ) throws URISyntaxException {
        log.debug("REST request to update ClaimRequestViews : {}, {}", id, claimRequestViews);
        if (claimRequestViews.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, claimRequestViews.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!claimRequestViewsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClaimRequestViews result = claimRequestViewsService.update(claimRequestViews);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, claimRequestViews.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /claim-request-views/:id} : Partial updates given fields of an existing claimRequestViews, field will ignore if it is null
     *
     * @param id the id of the claimRequestViews to save.
     * @param claimRequestViews the claimRequestViews to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated claimRequestViews,
     * or with status {@code 400 (Bad Request)} if the claimRequestViews is not valid,
     * or with status {@code 404 (Not Found)} if the claimRequestViews is not found,
     * or with status {@code 500 (Internal Server Error)} if the claimRequestViews couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/claim-request-views/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ClaimRequestViews> partialUpdateClaimRequestViews(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ClaimRequestViews claimRequestViews
    ) throws URISyntaxException {
        log.debug("REST request to partial update ClaimRequestViews partially : {}, {}", id, claimRequestViews);
        if (claimRequestViews.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, claimRequestViews.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!claimRequestViewsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClaimRequestViews> result = claimRequestViewsService.partialUpdate(claimRequestViews);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, claimRequestViews.getId().toString())
        );
    }

    /**
     * {@code GET  /claim-request-views} : get all the claimRequestViews.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of claimRequestViews in body.
     */
    @GetMapping("/claim-request-views")
    public ResponseEntity<List<ClaimRequestViews>> getAllClaimRequestViews(
        ClaimRequestViewsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ClaimRequestViews by criteria: {}", criteria);
        Page<ClaimRequestViews> page = claimRequestViewsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /claim-request-views/count} : count all the claimRequestViews.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/claim-request-views/count")
    public ResponseEntity<Long> countClaimRequestViews(ClaimRequestViewsCriteria criteria) {
        log.debug("REST request to count ClaimRequestViews by criteria: {}", criteria);
        return ResponseEntity.ok().body(claimRequestViewsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /claim-request-views/:id} : get the "id" claimRequestViews.
     *
     * @param id the id of the claimRequestViews to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the claimRequestViews, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/claim-request-views/{id}")
    public ResponseEntity<ClaimRequestViews> getClaimRequestViews(@PathVariable Long id) {
        log.debug("REST request to get ClaimRequestViews : {}", id);
        Optional<ClaimRequestViews> claimRequestViews = claimRequestViewsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(claimRequestViews);
    }

    /**
     * {@code DELETE  /claim-request-views/:id} : delete the "id" claimRequestViews.
     *
     * @param id the id of the claimRequestViews to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/claim-request-views/{id}")
    public ResponseEntity<Void> deleteClaimRequestViews(@PathVariable Long id) {
        log.debug("REST request to delete ClaimRequestViews : {}", id);
        claimRequestViewsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
