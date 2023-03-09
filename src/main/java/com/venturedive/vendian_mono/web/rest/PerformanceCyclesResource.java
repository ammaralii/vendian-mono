package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.PerformanceCycles;
import com.venturedive.vendian_mono.repository.PerformanceCyclesRepository;
import com.venturedive.vendian_mono.service.PerformanceCyclesQueryService;
import com.venturedive.vendian_mono.service.PerformanceCyclesService;
import com.venturedive.vendian_mono.service.criteria.PerformanceCyclesCriteria;
import com.venturedive.vendian_mono.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.PerformanceCycles}.
 */
@RestController
@RequestMapping("/api")
public class PerformanceCyclesResource {

    private final Logger log = LoggerFactory.getLogger(PerformanceCyclesResource.class);

    private static final String ENTITY_NAME = "performanceCycles";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PerformanceCyclesService performanceCyclesService;

    private final PerformanceCyclesRepository performanceCyclesRepository;

    private final PerformanceCyclesQueryService performanceCyclesQueryService;

    public PerformanceCyclesResource(
        PerformanceCyclesService performanceCyclesService,
        PerformanceCyclesRepository performanceCyclesRepository,
        PerformanceCyclesQueryService performanceCyclesQueryService
    ) {
        this.performanceCyclesService = performanceCyclesService;
        this.performanceCyclesRepository = performanceCyclesRepository;
        this.performanceCyclesQueryService = performanceCyclesQueryService;
    }

    /**
     * {@code POST  /performance-cycles} : Create a new performanceCycles.
     *
     * @param performanceCycles the performanceCycles to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new performanceCycles, or with status {@code 400 (Bad Request)} if the performanceCycles has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/performance-cycles")
    public ResponseEntity<PerformanceCycles> createPerformanceCycles(@RequestBody PerformanceCycles performanceCycles)
        throws URISyntaxException {
        log.debug("REST request to save PerformanceCycles : {}", performanceCycles);
        if (performanceCycles.getId() != null) {
            throw new BadRequestAlertException("A new performanceCycles cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PerformanceCycles result = performanceCyclesService.save(performanceCycles);
        return ResponseEntity
            .created(new URI("/api/performance-cycles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /performance-cycles/:id} : Updates an existing performanceCycles.
     *
     * @param id the id of the performanceCycles to save.
     * @param performanceCycles the performanceCycles to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated performanceCycles,
     * or with status {@code 400 (Bad Request)} if the performanceCycles is not valid,
     * or with status {@code 500 (Internal Server Error)} if the performanceCycles couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/performance-cycles/{id}")
    public ResponseEntity<PerformanceCycles> updatePerformanceCycles(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PerformanceCycles performanceCycles
    ) throws URISyntaxException {
        log.debug("REST request to update PerformanceCycles : {}, {}", id, performanceCycles);
        if (performanceCycles.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, performanceCycles.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!performanceCyclesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PerformanceCycles result = performanceCyclesService.update(performanceCycles);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, performanceCycles.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /performance-cycles/:id} : Partial updates given fields of an existing performanceCycles, field will ignore if it is null
     *
     * @param id the id of the performanceCycles to save.
     * @param performanceCycles the performanceCycles to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated performanceCycles,
     * or with status {@code 400 (Bad Request)} if the performanceCycles is not valid,
     * or with status {@code 404 (Not Found)} if the performanceCycles is not found,
     * or with status {@code 500 (Internal Server Error)} if the performanceCycles couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/performance-cycles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PerformanceCycles> partialUpdatePerformanceCycles(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PerformanceCycles performanceCycles
    ) throws URISyntaxException {
        log.debug("REST request to partial update PerformanceCycles partially : {}, {}", id, performanceCycles);
        if (performanceCycles.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, performanceCycles.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!performanceCyclesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PerformanceCycles> result = performanceCyclesService.partialUpdate(performanceCycles);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, performanceCycles.getId().toString())
        );
    }

    /**
     * {@code GET  /performance-cycles} : get all the performanceCycles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of performanceCycles in body.
     */
    @GetMapping("/performance-cycles")
    public ResponseEntity<List<PerformanceCycles>> getAllPerformanceCycles(
        PerformanceCyclesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PerformanceCycles by criteria: {}", criteria);
        Page<PerformanceCycles> page = performanceCyclesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /performance-cycles/count} : count all the performanceCycles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/performance-cycles/count")
    public ResponseEntity<Long> countPerformanceCycles(PerformanceCyclesCriteria criteria) {
        log.debug("REST request to count PerformanceCycles by criteria: {}", criteria);
        return ResponseEntity.ok().body(performanceCyclesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /performance-cycles/:id} : get the "id" performanceCycles.
     *
     * @param id the id of the performanceCycles to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the performanceCycles, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/performance-cycles/{id}")
    public ResponseEntity<PerformanceCycles> getPerformanceCycles(@PathVariable Long id) {
        log.debug("REST request to get PerformanceCycles : {}", id);
        Optional<PerformanceCycles> performanceCycles = performanceCyclesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(performanceCycles);
    }

    /**
     * {@code DELETE  /performance-cycles/:id} : delete the "id" performanceCycles.
     *
     * @param id the id of the performanceCycles to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/performance-cycles/{id}")
    public ResponseEntity<Void> deletePerformanceCycles(@PathVariable Long id) {
        log.debug("REST request to delete PerformanceCycles : {}", id);
        performanceCyclesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
