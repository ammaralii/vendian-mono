package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.ClaimApprovers;
import com.venturedive.vendian_mono.repository.ClaimApproversRepository;
import com.venturedive.vendian_mono.service.ClaimApproversQueryService;
import com.venturedive.vendian_mono.service.ClaimApproversService;
import com.venturedive.vendian_mono.service.criteria.ClaimApproversCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.ClaimApprovers}.
 */
@RestController
@RequestMapping("/api")
public class ClaimApproversResource {

    private final Logger log = LoggerFactory.getLogger(ClaimApproversResource.class);

    private static final String ENTITY_NAME = "claimApprovers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClaimApproversService claimApproversService;

    private final ClaimApproversRepository claimApproversRepository;

    private final ClaimApproversQueryService claimApproversQueryService;

    public ClaimApproversResource(
        ClaimApproversService claimApproversService,
        ClaimApproversRepository claimApproversRepository,
        ClaimApproversQueryService claimApproversQueryService
    ) {
        this.claimApproversService = claimApproversService;
        this.claimApproversRepository = claimApproversRepository;
        this.claimApproversQueryService = claimApproversQueryService;
    }

    /**
     * {@code POST  /claim-approvers} : Create a new claimApprovers.
     *
     * @param claimApprovers the claimApprovers to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new claimApprovers, or with status {@code 400 (Bad Request)} if the claimApprovers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/claim-approvers")
    public ResponseEntity<ClaimApprovers> createClaimApprovers(@Valid @RequestBody ClaimApprovers claimApprovers)
        throws URISyntaxException {
        log.debug("REST request to save ClaimApprovers : {}", claimApprovers);
        if (claimApprovers.getId() != null) {
            throw new BadRequestAlertException("A new claimApprovers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClaimApprovers result = claimApproversService.save(claimApprovers);
        return ResponseEntity
            .created(new URI("/api/claim-approvers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /claim-approvers/:id} : Updates an existing claimApprovers.
     *
     * @param id the id of the claimApprovers to save.
     * @param claimApprovers the claimApprovers to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated claimApprovers,
     * or with status {@code 400 (Bad Request)} if the claimApprovers is not valid,
     * or with status {@code 500 (Internal Server Error)} if the claimApprovers couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/claim-approvers/{id}")
    public ResponseEntity<ClaimApprovers> updateClaimApprovers(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ClaimApprovers claimApprovers
    ) throws URISyntaxException {
        log.debug("REST request to update ClaimApprovers : {}, {}", id, claimApprovers);
        if (claimApprovers.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, claimApprovers.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!claimApproversRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClaimApprovers result = claimApproversService.update(claimApprovers);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, claimApprovers.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /claim-approvers/:id} : Partial updates given fields of an existing claimApprovers, field will ignore if it is null
     *
     * @param id the id of the claimApprovers to save.
     * @param claimApprovers the claimApprovers to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated claimApprovers,
     * or with status {@code 400 (Bad Request)} if the claimApprovers is not valid,
     * or with status {@code 404 (Not Found)} if the claimApprovers is not found,
     * or with status {@code 500 (Internal Server Error)} if the claimApprovers couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/claim-approvers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ClaimApprovers> partialUpdateClaimApprovers(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ClaimApprovers claimApprovers
    ) throws URISyntaxException {
        log.debug("REST request to partial update ClaimApprovers partially : {}, {}", id, claimApprovers);
        if (claimApprovers.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, claimApprovers.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!claimApproversRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClaimApprovers> result = claimApproversService.partialUpdate(claimApprovers);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, claimApprovers.getId().toString())
        );
    }

    /**
     * {@code GET  /claim-approvers} : get all the claimApprovers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of claimApprovers in body.
     */
    @GetMapping("/claim-approvers")
    public ResponseEntity<List<ClaimApprovers>> getAllClaimApprovers(
        ClaimApproversCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ClaimApprovers by criteria: {}", criteria);
        Page<ClaimApprovers> page = claimApproversQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /claim-approvers/count} : count all the claimApprovers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/claim-approvers/count")
    public ResponseEntity<Long> countClaimApprovers(ClaimApproversCriteria criteria) {
        log.debug("REST request to count ClaimApprovers by criteria: {}", criteria);
        return ResponseEntity.ok().body(claimApproversQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /claim-approvers/:id} : get the "id" claimApprovers.
     *
     * @param id the id of the claimApprovers to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the claimApprovers, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/claim-approvers/{id}")
    public ResponseEntity<ClaimApprovers> getClaimApprovers(@PathVariable Long id) {
        log.debug("REST request to get ClaimApprovers : {}", id);
        Optional<ClaimApprovers> claimApprovers = claimApproversService.findOne(id);
        return ResponseUtil.wrapOrNotFound(claimApprovers);
    }

    /**
     * {@code DELETE  /claim-approvers/:id} : delete the "id" claimApprovers.
     *
     * @param id the id of the claimApprovers to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/claim-approvers/{id}")
    public ResponseEntity<Void> deleteClaimApprovers(@PathVariable Long id) {
        log.debug("REST request to delete ClaimApprovers : {}", id);
        claimApproversService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
