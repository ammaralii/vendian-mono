package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.PerformanceCycleEmployeeRating;
import com.venturedive.vendian_mono.repository.PerformanceCycleEmployeeRatingRepository;
import com.venturedive.vendian_mono.service.PerformanceCycleEmployeeRatingQueryService;
import com.venturedive.vendian_mono.service.PerformanceCycleEmployeeRatingService;
import com.venturedive.vendian_mono.service.criteria.PerformanceCycleEmployeeRatingCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.PerformanceCycleEmployeeRating}.
 */
@RestController
@RequestMapping("/api")
public class PerformanceCycleEmployeeRatingResource {

    private final Logger log = LoggerFactory.getLogger(PerformanceCycleEmployeeRatingResource.class);

    private static final String ENTITY_NAME = "performanceCycleEmployeeRating";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PerformanceCycleEmployeeRatingService performanceCycleEmployeeRatingService;

    private final PerformanceCycleEmployeeRatingRepository performanceCycleEmployeeRatingRepository;

    private final PerformanceCycleEmployeeRatingQueryService performanceCycleEmployeeRatingQueryService;

    public PerformanceCycleEmployeeRatingResource(
        PerformanceCycleEmployeeRatingService performanceCycleEmployeeRatingService,
        PerformanceCycleEmployeeRatingRepository performanceCycleEmployeeRatingRepository,
        PerformanceCycleEmployeeRatingQueryService performanceCycleEmployeeRatingQueryService
    ) {
        this.performanceCycleEmployeeRatingService = performanceCycleEmployeeRatingService;
        this.performanceCycleEmployeeRatingRepository = performanceCycleEmployeeRatingRepository;
        this.performanceCycleEmployeeRatingQueryService = performanceCycleEmployeeRatingQueryService;
    }

    /**
     * {@code POST  /performance-cycle-employee-ratings} : Create a new performanceCycleEmployeeRating.
     *
     * @param performanceCycleEmployeeRating the performanceCycleEmployeeRating to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new performanceCycleEmployeeRating, or with status {@code 400 (Bad Request)} if the performanceCycleEmployeeRating has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/performance-cycle-employee-ratings")
    public ResponseEntity<PerformanceCycleEmployeeRating> createPerformanceCycleEmployeeRating(
        @Valid @RequestBody PerformanceCycleEmployeeRating performanceCycleEmployeeRating
    ) throws URISyntaxException {
        log.debug("REST request to save PerformanceCycleEmployeeRating : {}", performanceCycleEmployeeRating);
        if (performanceCycleEmployeeRating.getId() != null) {
            throw new BadRequestAlertException("A new performanceCycleEmployeeRating cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PerformanceCycleEmployeeRating result = performanceCycleEmployeeRatingService.save(performanceCycleEmployeeRating);
        return ResponseEntity
            .created(new URI("/api/performance-cycle-employee-ratings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /performance-cycle-employee-ratings/:id} : Updates an existing performanceCycleEmployeeRating.
     *
     * @param id the id of the performanceCycleEmployeeRating to save.
     * @param performanceCycleEmployeeRating the performanceCycleEmployeeRating to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated performanceCycleEmployeeRating,
     * or with status {@code 400 (Bad Request)} if the performanceCycleEmployeeRating is not valid,
     * or with status {@code 500 (Internal Server Error)} if the performanceCycleEmployeeRating couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/performance-cycle-employee-ratings/{id}")
    public ResponseEntity<PerformanceCycleEmployeeRating> updatePerformanceCycleEmployeeRating(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PerformanceCycleEmployeeRating performanceCycleEmployeeRating
    ) throws URISyntaxException {
        log.debug("REST request to update PerformanceCycleEmployeeRating : {}, {}", id, performanceCycleEmployeeRating);
        if (performanceCycleEmployeeRating.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, performanceCycleEmployeeRating.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!performanceCycleEmployeeRatingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PerformanceCycleEmployeeRating result = performanceCycleEmployeeRatingService.update(performanceCycleEmployeeRating);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, performanceCycleEmployeeRating.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /performance-cycle-employee-ratings/:id} : Partial updates given fields of an existing performanceCycleEmployeeRating, field will ignore if it is null
     *
     * @param id the id of the performanceCycleEmployeeRating to save.
     * @param performanceCycleEmployeeRating the performanceCycleEmployeeRating to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated performanceCycleEmployeeRating,
     * or with status {@code 400 (Bad Request)} if the performanceCycleEmployeeRating is not valid,
     * or with status {@code 404 (Not Found)} if the performanceCycleEmployeeRating is not found,
     * or with status {@code 500 (Internal Server Error)} if the performanceCycleEmployeeRating couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/performance-cycle-employee-ratings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PerformanceCycleEmployeeRating> partialUpdatePerformanceCycleEmployeeRating(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PerformanceCycleEmployeeRating performanceCycleEmployeeRating
    ) throws URISyntaxException {
        log.debug("REST request to partial update PerformanceCycleEmployeeRating partially : {}, {}", id, performanceCycleEmployeeRating);
        if (performanceCycleEmployeeRating.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, performanceCycleEmployeeRating.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!performanceCycleEmployeeRatingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PerformanceCycleEmployeeRating> result = performanceCycleEmployeeRatingService.partialUpdate(
            performanceCycleEmployeeRating
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, performanceCycleEmployeeRating.getId().toString())
        );
    }

    /**
     * {@code GET  /performance-cycle-employee-ratings} : get all the performanceCycleEmployeeRatings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of performanceCycleEmployeeRatings in body.
     */
    @GetMapping("/performance-cycle-employee-ratings")
    public ResponseEntity<List<PerformanceCycleEmployeeRating>> getAllPerformanceCycleEmployeeRatings(
        PerformanceCycleEmployeeRatingCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PerformanceCycleEmployeeRatings by criteria: {}", criteria);
        Page<PerformanceCycleEmployeeRating> page = performanceCycleEmployeeRatingQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /performance-cycle-employee-ratings/count} : count all the performanceCycleEmployeeRatings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/performance-cycle-employee-ratings/count")
    public ResponseEntity<Long> countPerformanceCycleEmployeeRatings(PerformanceCycleEmployeeRatingCriteria criteria) {
        log.debug("REST request to count PerformanceCycleEmployeeRatings by criteria: {}", criteria);
        return ResponseEntity.ok().body(performanceCycleEmployeeRatingQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /performance-cycle-employee-ratings/:id} : get the "id" performanceCycleEmployeeRating.
     *
     * @param id the id of the performanceCycleEmployeeRating to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the performanceCycleEmployeeRating, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/performance-cycle-employee-ratings/{id}")
    public ResponseEntity<PerformanceCycleEmployeeRating> getPerformanceCycleEmployeeRating(@PathVariable Long id) {
        log.debug("REST request to get PerformanceCycleEmployeeRating : {}", id);
        Optional<PerformanceCycleEmployeeRating> performanceCycleEmployeeRating = performanceCycleEmployeeRatingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(performanceCycleEmployeeRating);
    }

    /**
     * {@code DELETE  /performance-cycle-employee-ratings/:id} : delete the "id" performanceCycleEmployeeRating.
     *
     * @param id the id of the performanceCycleEmployeeRating to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/performance-cycle-employee-ratings/{id}")
    public ResponseEntity<Void> deletePerformanceCycleEmployeeRating(@PathVariable Long id) {
        log.debug("REST request to delete PerformanceCycleEmployeeRating : {}", id);
        performanceCycleEmployeeRatingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
