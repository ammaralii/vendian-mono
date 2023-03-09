package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.DealRequirements;
import com.venturedive.vendian_mono.repository.DealRequirementsRepository;
import com.venturedive.vendian_mono.service.DealRequirementsQueryService;
import com.venturedive.vendian_mono.service.DealRequirementsService;
import com.venturedive.vendian_mono.service.criteria.DealRequirementsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.DealRequirements}.
 */
@RestController
@RequestMapping("/api")
public class DealRequirementsResource {

    private final Logger log = LoggerFactory.getLogger(DealRequirementsResource.class);

    private static final String ENTITY_NAME = "dealRequirements";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DealRequirementsService dealRequirementsService;

    private final DealRequirementsRepository dealRequirementsRepository;

    private final DealRequirementsQueryService dealRequirementsQueryService;

    public DealRequirementsResource(
        DealRequirementsService dealRequirementsService,
        DealRequirementsRepository dealRequirementsRepository,
        DealRequirementsQueryService dealRequirementsQueryService
    ) {
        this.dealRequirementsService = dealRequirementsService;
        this.dealRequirementsRepository = dealRequirementsRepository;
        this.dealRequirementsQueryService = dealRequirementsQueryService;
    }

    /**
     * {@code POST  /deal-requirements} : Create a new dealRequirements.
     *
     * @param dealRequirements the dealRequirements to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dealRequirements, or with status {@code 400 (Bad Request)} if the dealRequirements has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/deal-requirements")
    public ResponseEntity<DealRequirements> createDealRequirements(@Valid @RequestBody DealRequirements dealRequirements)
        throws URISyntaxException {
        log.debug("REST request to save DealRequirements : {}", dealRequirements);
        if (dealRequirements.getId() != null) {
            throw new BadRequestAlertException("A new dealRequirements cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DealRequirements result = dealRequirementsService.save(dealRequirements);
        return ResponseEntity
            .created(new URI("/api/deal-requirements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /deal-requirements/:id} : Updates an existing dealRequirements.
     *
     * @param id the id of the dealRequirements to save.
     * @param dealRequirements the dealRequirements to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dealRequirements,
     * or with status {@code 400 (Bad Request)} if the dealRequirements is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dealRequirements couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/deal-requirements/{id}")
    public ResponseEntity<DealRequirements> updateDealRequirements(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DealRequirements dealRequirements
    ) throws URISyntaxException {
        log.debug("REST request to update DealRequirements : {}, {}", id, dealRequirements);
        if (dealRequirements.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dealRequirements.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dealRequirementsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DealRequirements result = dealRequirementsService.update(dealRequirements);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dealRequirements.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /deal-requirements/:id} : Partial updates given fields of an existing dealRequirements, field will ignore if it is null
     *
     * @param id the id of the dealRequirements to save.
     * @param dealRequirements the dealRequirements to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dealRequirements,
     * or with status {@code 400 (Bad Request)} if the dealRequirements is not valid,
     * or with status {@code 404 (Not Found)} if the dealRequirements is not found,
     * or with status {@code 500 (Internal Server Error)} if the dealRequirements couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/deal-requirements/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DealRequirements> partialUpdateDealRequirements(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DealRequirements dealRequirements
    ) throws URISyntaxException {
        log.debug("REST request to partial update DealRequirements partially : {}, {}", id, dealRequirements);
        if (dealRequirements.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dealRequirements.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dealRequirementsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DealRequirements> result = dealRequirementsService.partialUpdate(dealRequirements);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dealRequirements.getId().toString())
        );
    }

    /**
     * {@code GET  /deal-requirements} : get all the dealRequirements.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dealRequirements in body.
     */
    @GetMapping("/deal-requirements")
    public ResponseEntity<List<DealRequirements>> getAllDealRequirements(
        DealRequirementsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get DealRequirements by criteria: {}", criteria);
        Page<DealRequirements> page = dealRequirementsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /deal-requirements/count} : count all the dealRequirements.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/deal-requirements/count")
    public ResponseEntity<Long> countDealRequirements(DealRequirementsCriteria criteria) {
        log.debug("REST request to count DealRequirements by criteria: {}", criteria);
        return ResponseEntity.ok().body(dealRequirementsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /deal-requirements/:id} : get the "id" dealRequirements.
     *
     * @param id the id of the dealRequirements to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dealRequirements, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/deal-requirements/{id}")
    public ResponseEntity<DealRequirements> getDealRequirements(@PathVariable Long id) {
        log.debug("REST request to get DealRequirements : {}", id);
        Optional<DealRequirements> dealRequirements = dealRequirementsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dealRequirements);
    }

    /**
     * {@code DELETE  /deal-requirements/:id} : delete the "id" dealRequirements.
     *
     * @param id the id of the dealRequirements to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/deal-requirements/{id}")
    public ResponseEntity<Void> deleteDealRequirements(@PathVariable Long id) {
        log.debug("REST request to delete DealRequirements : {}", id);
        dealRequirementsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
