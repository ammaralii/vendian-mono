package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.PerformanceCycles20190826;
import com.venturedive.vendian_mono.repository.PerformanceCycles20190826Repository;
import com.venturedive.vendian_mono.service.PerformanceCycles20190826QueryService;
import com.venturedive.vendian_mono.service.PerformanceCycles20190826Service;
import com.venturedive.vendian_mono.service.criteria.PerformanceCycles20190826Criteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.PerformanceCycles20190826}.
 */
@RestController
@RequestMapping("/api")
public class PerformanceCycles20190826Resource {

    private final Logger log = LoggerFactory.getLogger(PerformanceCycles20190826Resource.class);

    private static final String ENTITY_NAME = "performanceCycles20190826";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PerformanceCycles20190826Service performanceCycles20190826Service;

    private final PerformanceCycles20190826Repository performanceCycles20190826Repository;

    private final PerformanceCycles20190826QueryService performanceCycles20190826QueryService;

    public PerformanceCycles20190826Resource(
        PerformanceCycles20190826Service performanceCycles20190826Service,
        PerformanceCycles20190826Repository performanceCycles20190826Repository,
        PerformanceCycles20190826QueryService performanceCycles20190826QueryService
    ) {
        this.performanceCycles20190826Service = performanceCycles20190826Service;
        this.performanceCycles20190826Repository = performanceCycles20190826Repository;
        this.performanceCycles20190826QueryService = performanceCycles20190826QueryService;
    }

    /**
     * {@code POST  /performance-cycles-20190826-s} : Create a new performanceCycles20190826.
     *
     * @param performanceCycles20190826 the performanceCycles20190826 to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new performanceCycles20190826, or with status {@code 400 (Bad Request)} if the performanceCycles20190826 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/performance-cycles-20190826-s")
    public ResponseEntity<PerformanceCycles20190826> createPerformanceCycles20190826(
        @RequestBody PerformanceCycles20190826 performanceCycles20190826
    ) throws URISyntaxException {
        log.debug("REST request to save PerformanceCycles20190826 : {}", performanceCycles20190826);
        if (performanceCycles20190826.getId() != null) {
            throw new BadRequestAlertException("A new performanceCycles20190826 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PerformanceCycles20190826 result = performanceCycles20190826Service.save(performanceCycles20190826);
        return ResponseEntity
            .created(new URI("/api/performance-cycles-20190826-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /performance-cycles-20190826-s/:id} : Updates an existing performanceCycles20190826.
     *
     * @param id the id of the performanceCycles20190826 to save.
     * @param performanceCycles20190826 the performanceCycles20190826 to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated performanceCycles20190826,
     * or with status {@code 400 (Bad Request)} if the performanceCycles20190826 is not valid,
     * or with status {@code 500 (Internal Server Error)} if the performanceCycles20190826 couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/performance-cycles-20190826-s/{id}")
    public ResponseEntity<PerformanceCycles20190826> updatePerformanceCycles20190826(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PerformanceCycles20190826 performanceCycles20190826
    ) throws URISyntaxException {
        log.debug("REST request to update PerformanceCycles20190826 : {}, {}", id, performanceCycles20190826);
        if (performanceCycles20190826.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, performanceCycles20190826.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!performanceCycles20190826Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PerformanceCycles20190826 result = performanceCycles20190826Service.update(performanceCycles20190826);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, performanceCycles20190826.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /performance-cycles-20190826-s/:id} : Partial updates given fields of an existing performanceCycles20190826, field will ignore if it is null
     *
     * @param id the id of the performanceCycles20190826 to save.
     * @param performanceCycles20190826 the performanceCycles20190826 to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated performanceCycles20190826,
     * or with status {@code 400 (Bad Request)} if the performanceCycles20190826 is not valid,
     * or with status {@code 404 (Not Found)} if the performanceCycles20190826 is not found,
     * or with status {@code 500 (Internal Server Error)} if the performanceCycles20190826 couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/performance-cycles-20190826-s/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PerformanceCycles20190826> partialUpdatePerformanceCycles20190826(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PerformanceCycles20190826 performanceCycles20190826
    ) throws URISyntaxException {
        log.debug("REST request to partial update PerformanceCycles20190826 partially : {}, {}", id, performanceCycles20190826);
        if (performanceCycles20190826.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, performanceCycles20190826.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!performanceCycles20190826Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PerformanceCycles20190826> result = performanceCycles20190826Service.partialUpdate(performanceCycles20190826);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, performanceCycles20190826.getId().toString())
        );
    }

    /**
     * {@code GET  /performance-cycles-20190826-s} : get all the performanceCycles20190826s.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of performanceCycles20190826s in body.
     */
    @GetMapping("/performance-cycles-20190826-s")
    public ResponseEntity<List<PerformanceCycles20190826>> getAllPerformanceCycles20190826s(
        PerformanceCycles20190826Criteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PerformanceCycles20190826s by criteria: {}", criteria);
        Page<PerformanceCycles20190826> page = performanceCycles20190826QueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /performance-cycles-20190826-s/count} : count all the performanceCycles20190826s.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/performance-cycles-20190826-s/count")
    public ResponseEntity<Long> countPerformanceCycles20190826s(PerformanceCycles20190826Criteria criteria) {
        log.debug("REST request to count PerformanceCycles20190826s by criteria: {}", criteria);
        return ResponseEntity.ok().body(performanceCycles20190826QueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /performance-cycles-20190826-s/:id} : get the "id" performanceCycles20190826.
     *
     * @param id the id of the performanceCycles20190826 to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the performanceCycles20190826, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/performance-cycles-20190826-s/{id}")
    public ResponseEntity<PerformanceCycles20190826> getPerformanceCycles20190826(@PathVariable Long id) {
        log.debug("REST request to get PerformanceCycles20190826 : {}", id);
        Optional<PerformanceCycles20190826> performanceCycles20190826 = performanceCycles20190826Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(performanceCycles20190826);
    }

    /**
     * {@code DELETE  /performance-cycles-20190826-s/:id} : delete the "id" performanceCycles20190826.
     *
     * @param id the id of the performanceCycles20190826 to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/performance-cycles-20190826-s/{id}")
    public ResponseEntity<Void> deletePerformanceCycles20190826(@PathVariable Long id) {
        log.debug("REST request to delete PerformanceCycles20190826 : {}", id);
        performanceCycles20190826Service.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
