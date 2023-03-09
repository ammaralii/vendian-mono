package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.DealRequirementMatchingResources;
import com.venturedive.vendian_mono.repository.DealRequirementMatchingResourcesRepository;
import com.venturedive.vendian_mono.service.DealRequirementMatchingResourcesQueryService;
import com.venturedive.vendian_mono.service.DealRequirementMatchingResourcesService;
import com.venturedive.vendian_mono.service.criteria.DealRequirementMatchingResourcesCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.DealRequirementMatchingResources}.
 */
@RestController
@RequestMapping("/api")
public class DealRequirementMatchingResourcesResource {

    private final Logger log = LoggerFactory.getLogger(DealRequirementMatchingResourcesResource.class);

    private static final String ENTITY_NAME = "dealRequirementMatchingResources";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DealRequirementMatchingResourcesService dealRequirementMatchingResourcesService;

    private final DealRequirementMatchingResourcesRepository dealRequirementMatchingResourcesRepository;

    private final DealRequirementMatchingResourcesQueryService dealRequirementMatchingResourcesQueryService;

    public DealRequirementMatchingResourcesResource(
        DealRequirementMatchingResourcesService dealRequirementMatchingResourcesService,
        DealRequirementMatchingResourcesRepository dealRequirementMatchingResourcesRepository,
        DealRequirementMatchingResourcesQueryService dealRequirementMatchingResourcesQueryService
    ) {
        this.dealRequirementMatchingResourcesService = dealRequirementMatchingResourcesService;
        this.dealRequirementMatchingResourcesRepository = dealRequirementMatchingResourcesRepository;
        this.dealRequirementMatchingResourcesQueryService = dealRequirementMatchingResourcesQueryService;
    }

    /**
     * {@code POST  /deal-requirement-matching-resources} : Create a new dealRequirementMatchingResources.
     *
     * @param dealRequirementMatchingResources the dealRequirementMatchingResources to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dealRequirementMatchingResources, or with status {@code 400 (Bad Request)} if the dealRequirementMatchingResources has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/deal-requirement-matching-resources")
    public ResponseEntity<DealRequirementMatchingResources> createDealRequirementMatchingResources(
        @Valid @RequestBody DealRequirementMatchingResources dealRequirementMatchingResources
    ) throws URISyntaxException {
        log.debug("REST request to save DealRequirementMatchingResources : {}", dealRequirementMatchingResources);
        if (dealRequirementMatchingResources.getId() != null) {
            throw new BadRequestAlertException("A new dealRequirementMatchingResources cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DealRequirementMatchingResources result = dealRequirementMatchingResourcesService.save(dealRequirementMatchingResources);
        return ResponseEntity
            .created(new URI("/api/deal-requirement-matching-resources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /deal-requirement-matching-resources/:id} : Updates an existing dealRequirementMatchingResources.
     *
     * @param id the id of the dealRequirementMatchingResources to save.
     * @param dealRequirementMatchingResources the dealRequirementMatchingResources to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dealRequirementMatchingResources,
     * or with status {@code 400 (Bad Request)} if the dealRequirementMatchingResources is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dealRequirementMatchingResources couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/deal-requirement-matching-resources/{id}")
    public ResponseEntity<DealRequirementMatchingResources> updateDealRequirementMatchingResources(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DealRequirementMatchingResources dealRequirementMatchingResources
    ) throws URISyntaxException {
        log.debug("REST request to update DealRequirementMatchingResources : {}, {}", id, dealRequirementMatchingResources);
        if (dealRequirementMatchingResources.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dealRequirementMatchingResources.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dealRequirementMatchingResourcesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DealRequirementMatchingResources result = dealRequirementMatchingResourcesService.update(dealRequirementMatchingResources);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dealRequirementMatchingResources.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /deal-requirement-matching-resources/:id} : Partial updates given fields of an existing dealRequirementMatchingResources, field will ignore if it is null
     *
     * @param id the id of the dealRequirementMatchingResources to save.
     * @param dealRequirementMatchingResources the dealRequirementMatchingResources to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dealRequirementMatchingResources,
     * or with status {@code 400 (Bad Request)} if the dealRequirementMatchingResources is not valid,
     * or with status {@code 404 (Not Found)} if the dealRequirementMatchingResources is not found,
     * or with status {@code 500 (Internal Server Error)} if the dealRequirementMatchingResources couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/deal-requirement-matching-resources/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DealRequirementMatchingResources> partialUpdateDealRequirementMatchingResources(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DealRequirementMatchingResources dealRequirementMatchingResources
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update DealRequirementMatchingResources partially : {}, {}",
            id,
            dealRequirementMatchingResources
        );
        if (dealRequirementMatchingResources.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dealRequirementMatchingResources.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dealRequirementMatchingResourcesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DealRequirementMatchingResources> result = dealRequirementMatchingResourcesService.partialUpdate(
            dealRequirementMatchingResources
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dealRequirementMatchingResources.getId().toString())
        );
    }

    /**
     * {@code GET  /deal-requirement-matching-resources} : get all the dealRequirementMatchingResources.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dealRequirementMatchingResources in body.
     */
    @GetMapping("/deal-requirement-matching-resources")
    public ResponseEntity<List<DealRequirementMatchingResources>> getAllDealRequirementMatchingResources(
        DealRequirementMatchingResourcesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get DealRequirementMatchingResources by criteria: {}", criteria);
        Page<DealRequirementMatchingResources> page = dealRequirementMatchingResourcesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /deal-requirement-matching-resources/count} : count all the dealRequirementMatchingResources.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/deal-requirement-matching-resources/count")
    public ResponseEntity<Long> countDealRequirementMatchingResources(DealRequirementMatchingResourcesCriteria criteria) {
        log.debug("REST request to count DealRequirementMatchingResources by criteria: {}", criteria);
        return ResponseEntity.ok().body(dealRequirementMatchingResourcesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /deal-requirement-matching-resources/:id} : get the "id" dealRequirementMatchingResources.
     *
     * @param id the id of the dealRequirementMatchingResources to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dealRequirementMatchingResources, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/deal-requirement-matching-resources/{id}")
    public ResponseEntity<DealRequirementMatchingResources> getDealRequirementMatchingResources(@PathVariable Long id) {
        log.debug("REST request to get DealRequirementMatchingResources : {}", id);
        Optional<DealRequirementMatchingResources> dealRequirementMatchingResources = dealRequirementMatchingResourcesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dealRequirementMatchingResources);
    }

    /**
     * {@code DELETE  /deal-requirement-matching-resources/:id} : delete the "id" dealRequirementMatchingResources.
     *
     * @param id the id of the dealRequirementMatchingResources to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/deal-requirement-matching-resources/{id}")
    public ResponseEntity<Void> deleteDealRequirementMatchingResources(@PathVariable Long id) {
        log.debug("REST request to delete DealRequirementMatchingResources : {}", id);
        dealRequirementMatchingResourcesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
