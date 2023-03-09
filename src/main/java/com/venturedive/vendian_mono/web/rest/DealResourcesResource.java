package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.DealResources;
import com.venturedive.vendian_mono.repository.DealResourcesRepository;
import com.venturedive.vendian_mono.service.DealResourcesQueryService;
import com.venturedive.vendian_mono.service.DealResourcesService;
import com.venturedive.vendian_mono.service.criteria.DealResourcesCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.DealResources}.
 */
@RestController
@RequestMapping("/api")
public class DealResourcesResource {

    private final Logger log = LoggerFactory.getLogger(DealResourcesResource.class);

    private static final String ENTITY_NAME = "dealResources";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DealResourcesService dealResourcesService;

    private final DealResourcesRepository dealResourcesRepository;

    private final DealResourcesQueryService dealResourcesQueryService;

    public DealResourcesResource(
        DealResourcesService dealResourcesService,
        DealResourcesRepository dealResourcesRepository,
        DealResourcesQueryService dealResourcesQueryService
    ) {
        this.dealResourcesService = dealResourcesService;
        this.dealResourcesRepository = dealResourcesRepository;
        this.dealResourcesQueryService = dealResourcesQueryService;
    }

    /**
     * {@code POST  /deal-resources} : Create a new dealResources.
     *
     * @param dealResources the dealResources to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dealResources, or with status {@code 400 (Bad Request)} if the dealResources has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/deal-resources")
    public ResponseEntity<DealResources> createDealResources(@Valid @RequestBody DealResources dealResources) throws URISyntaxException {
        log.debug("REST request to save DealResources : {}", dealResources);
        if (dealResources.getId() != null) {
            throw new BadRequestAlertException("A new dealResources cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DealResources result = dealResourcesService.save(dealResources);
        return ResponseEntity
            .created(new URI("/api/deal-resources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /deal-resources/:id} : Updates an existing dealResources.
     *
     * @param id the id of the dealResources to save.
     * @param dealResources the dealResources to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dealResources,
     * or with status {@code 400 (Bad Request)} if the dealResources is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dealResources couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/deal-resources/{id}")
    public ResponseEntity<DealResources> updateDealResources(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DealResources dealResources
    ) throws URISyntaxException {
        log.debug("REST request to update DealResources : {}, {}", id, dealResources);
        if (dealResources.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dealResources.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dealResourcesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DealResources result = dealResourcesService.update(dealResources);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dealResources.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /deal-resources/:id} : Partial updates given fields of an existing dealResources, field will ignore if it is null
     *
     * @param id the id of the dealResources to save.
     * @param dealResources the dealResources to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dealResources,
     * or with status {@code 400 (Bad Request)} if the dealResources is not valid,
     * or with status {@code 404 (Not Found)} if the dealResources is not found,
     * or with status {@code 500 (Internal Server Error)} if the dealResources couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/deal-resources/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DealResources> partialUpdateDealResources(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DealResources dealResources
    ) throws URISyntaxException {
        log.debug("REST request to partial update DealResources partially : {}, {}", id, dealResources);
        if (dealResources.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dealResources.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dealResourcesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DealResources> result = dealResourcesService.partialUpdate(dealResources);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dealResources.getId().toString())
        );
    }

    /**
     * {@code GET  /deal-resources} : get all the dealResources.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dealResources in body.
     */
    @GetMapping("/deal-resources")
    public ResponseEntity<List<DealResources>> getAllDealResources(
        DealResourcesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get DealResources by criteria: {}", criteria);
        Page<DealResources> page = dealResourcesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /deal-resources/count} : count all the dealResources.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/deal-resources/count")
    public ResponseEntity<Long> countDealResources(DealResourcesCriteria criteria) {
        log.debug("REST request to count DealResources by criteria: {}", criteria);
        return ResponseEntity.ok().body(dealResourcesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /deal-resources/:id} : get the "id" dealResources.
     *
     * @param id the id of the dealResources to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dealResources, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/deal-resources/{id}")
    public ResponseEntity<DealResources> getDealResources(@PathVariable Long id) {
        log.debug("REST request to get DealResources : {}", id);
        Optional<DealResources> dealResources = dealResourcesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dealResources);
    }

    /**
     * {@code DELETE  /deal-resources/:id} : delete the "id" dealResources.
     *
     * @param id the id of the dealResources to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/deal-resources/{id}")
    public ResponseEntity<Void> deleteDealResources(@PathVariable Long id) {
        log.debug("REST request to delete DealResources : {}", id);
        dealResourcesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
