package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.RatingOptions;
import com.venturedive.vendian_mono.repository.RatingOptionsRepository;
import com.venturedive.vendian_mono.service.RatingOptionsQueryService;
import com.venturedive.vendian_mono.service.RatingOptionsService;
import com.venturedive.vendian_mono.service.criteria.RatingOptionsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.RatingOptions}.
 */
@RestController
@RequestMapping("/api")
public class RatingOptionsResource {

    private final Logger log = LoggerFactory.getLogger(RatingOptionsResource.class);

    private static final String ENTITY_NAME = "ratingOptions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RatingOptionsService ratingOptionsService;

    private final RatingOptionsRepository ratingOptionsRepository;

    private final RatingOptionsQueryService ratingOptionsQueryService;

    public RatingOptionsResource(
        RatingOptionsService ratingOptionsService,
        RatingOptionsRepository ratingOptionsRepository,
        RatingOptionsQueryService ratingOptionsQueryService
    ) {
        this.ratingOptionsService = ratingOptionsService;
        this.ratingOptionsRepository = ratingOptionsRepository;
        this.ratingOptionsQueryService = ratingOptionsQueryService;
    }

    /**
     * {@code POST  /rating-options} : Create a new ratingOptions.
     *
     * @param ratingOptions the ratingOptions to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ratingOptions, or with status {@code 400 (Bad Request)} if the ratingOptions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rating-options")
    public ResponseEntity<RatingOptions> createRatingOptions(@Valid @RequestBody RatingOptions ratingOptions) throws URISyntaxException {
        log.debug("REST request to save RatingOptions : {}", ratingOptions);
        if (ratingOptions.getId() != null) {
            throw new BadRequestAlertException("A new ratingOptions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RatingOptions result = ratingOptionsService.save(ratingOptions);
        return ResponseEntity
            .created(new URI("/api/rating-options/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rating-options/:id} : Updates an existing ratingOptions.
     *
     * @param id the id of the ratingOptions to save.
     * @param ratingOptions the ratingOptions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ratingOptions,
     * or with status {@code 400 (Bad Request)} if the ratingOptions is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ratingOptions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rating-options/{id}")
    public ResponseEntity<RatingOptions> updateRatingOptions(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RatingOptions ratingOptions
    ) throws URISyntaxException {
        log.debug("REST request to update RatingOptions : {}, {}", id, ratingOptions);
        if (ratingOptions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ratingOptions.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ratingOptionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RatingOptions result = ratingOptionsService.update(ratingOptions);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ratingOptions.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rating-options/:id} : Partial updates given fields of an existing ratingOptions, field will ignore if it is null
     *
     * @param id the id of the ratingOptions to save.
     * @param ratingOptions the ratingOptions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ratingOptions,
     * or with status {@code 400 (Bad Request)} if the ratingOptions is not valid,
     * or with status {@code 404 (Not Found)} if the ratingOptions is not found,
     * or with status {@code 500 (Internal Server Error)} if the ratingOptions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rating-options/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RatingOptions> partialUpdateRatingOptions(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RatingOptions ratingOptions
    ) throws URISyntaxException {
        log.debug("REST request to partial update RatingOptions partially : {}, {}", id, ratingOptions);
        if (ratingOptions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ratingOptions.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ratingOptionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RatingOptions> result = ratingOptionsService.partialUpdate(ratingOptions);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ratingOptions.getId().toString())
        );
    }

    /**
     * {@code GET  /rating-options} : get all the ratingOptions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ratingOptions in body.
     */
    @GetMapping("/rating-options")
    public ResponseEntity<List<RatingOptions>> getAllRatingOptions(
        RatingOptionsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get RatingOptions by criteria: {}", criteria);
        Page<RatingOptions> page = ratingOptionsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rating-options/count} : count all the ratingOptions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/rating-options/count")
    public ResponseEntity<Long> countRatingOptions(RatingOptionsCriteria criteria) {
        log.debug("REST request to count RatingOptions by criteria: {}", criteria);
        return ResponseEntity.ok().body(ratingOptionsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /rating-options/:id} : get the "id" ratingOptions.
     *
     * @param id the id of the ratingOptions to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ratingOptions, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rating-options/{id}")
    public ResponseEntity<RatingOptions> getRatingOptions(@PathVariable Long id) {
        log.debug("REST request to get RatingOptions : {}", id);
        Optional<RatingOptions> ratingOptions = ratingOptionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ratingOptions);
    }

    /**
     * {@code DELETE  /rating-options/:id} : delete the "id" ratingOptions.
     *
     * @param id the id of the ratingOptions to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rating-options/{id}")
    public ResponseEntity<Void> deleteRatingOptions(@PathVariable Long id) {
        log.debug("REST request to delete RatingOptions : {}", id);
        ratingOptionsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
