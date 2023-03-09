package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.RatingAttributeMappings;
import com.venturedive.vendian_mono.repository.RatingAttributeMappingsRepository;
import com.venturedive.vendian_mono.service.RatingAttributeMappingsQueryService;
import com.venturedive.vendian_mono.service.RatingAttributeMappingsService;
import com.venturedive.vendian_mono.service.criteria.RatingAttributeMappingsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.RatingAttributeMappings}.
 */
@RestController
@RequestMapping("/api")
public class RatingAttributeMappingsResource {

    private final Logger log = LoggerFactory.getLogger(RatingAttributeMappingsResource.class);

    private static final String ENTITY_NAME = "ratingAttributeMappings";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RatingAttributeMappingsService ratingAttributeMappingsService;

    private final RatingAttributeMappingsRepository ratingAttributeMappingsRepository;

    private final RatingAttributeMappingsQueryService ratingAttributeMappingsQueryService;

    public RatingAttributeMappingsResource(
        RatingAttributeMappingsService ratingAttributeMappingsService,
        RatingAttributeMappingsRepository ratingAttributeMappingsRepository,
        RatingAttributeMappingsQueryService ratingAttributeMappingsQueryService
    ) {
        this.ratingAttributeMappingsService = ratingAttributeMappingsService;
        this.ratingAttributeMappingsRepository = ratingAttributeMappingsRepository;
        this.ratingAttributeMappingsQueryService = ratingAttributeMappingsQueryService;
    }

    /**
     * {@code POST  /rating-attribute-mappings} : Create a new ratingAttributeMappings.
     *
     * @param ratingAttributeMappings the ratingAttributeMappings to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ratingAttributeMappings, or with status {@code 400 (Bad Request)} if the ratingAttributeMappings has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rating-attribute-mappings")
    public ResponseEntity<RatingAttributeMappings> createRatingAttributeMappings(
        @Valid @RequestBody RatingAttributeMappings ratingAttributeMappings
    ) throws URISyntaxException {
        log.debug("REST request to save RatingAttributeMappings : {}", ratingAttributeMappings);
        if (ratingAttributeMappings.getId() != null) {
            throw new BadRequestAlertException("A new ratingAttributeMappings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RatingAttributeMappings result = ratingAttributeMappingsService.save(ratingAttributeMappings);
        return ResponseEntity
            .created(new URI("/api/rating-attribute-mappings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rating-attribute-mappings/:id} : Updates an existing ratingAttributeMappings.
     *
     * @param id the id of the ratingAttributeMappings to save.
     * @param ratingAttributeMappings the ratingAttributeMappings to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ratingAttributeMappings,
     * or with status {@code 400 (Bad Request)} if the ratingAttributeMappings is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ratingAttributeMappings couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rating-attribute-mappings/{id}")
    public ResponseEntity<RatingAttributeMappings> updateRatingAttributeMappings(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RatingAttributeMappings ratingAttributeMappings
    ) throws URISyntaxException {
        log.debug("REST request to update RatingAttributeMappings : {}, {}", id, ratingAttributeMappings);
        if (ratingAttributeMappings.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ratingAttributeMappings.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ratingAttributeMappingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RatingAttributeMappings result = ratingAttributeMappingsService.update(ratingAttributeMappings);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ratingAttributeMappings.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rating-attribute-mappings/:id} : Partial updates given fields of an existing ratingAttributeMappings, field will ignore if it is null
     *
     * @param id the id of the ratingAttributeMappings to save.
     * @param ratingAttributeMappings the ratingAttributeMappings to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ratingAttributeMappings,
     * or with status {@code 400 (Bad Request)} if the ratingAttributeMappings is not valid,
     * or with status {@code 404 (Not Found)} if the ratingAttributeMappings is not found,
     * or with status {@code 500 (Internal Server Error)} if the ratingAttributeMappings couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rating-attribute-mappings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RatingAttributeMappings> partialUpdateRatingAttributeMappings(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RatingAttributeMappings ratingAttributeMappings
    ) throws URISyntaxException {
        log.debug("REST request to partial update RatingAttributeMappings partially : {}, {}", id, ratingAttributeMappings);
        if (ratingAttributeMappings.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ratingAttributeMappings.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ratingAttributeMappingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RatingAttributeMappings> result = ratingAttributeMappingsService.partialUpdate(ratingAttributeMappings);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ratingAttributeMappings.getId().toString())
        );
    }

    /**
     * {@code GET  /rating-attribute-mappings} : get all the ratingAttributeMappings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ratingAttributeMappings in body.
     */
    @GetMapping("/rating-attribute-mappings")
    public ResponseEntity<List<RatingAttributeMappings>> getAllRatingAttributeMappings(
        RatingAttributeMappingsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get RatingAttributeMappings by criteria: {}", criteria);
        Page<RatingAttributeMappings> page = ratingAttributeMappingsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rating-attribute-mappings/count} : count all the ratingAttributeMappings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/rating-attribute-mappings/count")
    public ResponseEntity<Long> countRatingAttributeMappings(RatingAttributeMappingsCriteria criteria) {
        log.debug("REST request to count RatingAttributeMappings by criteria: {}", criteria);
        return ResponseEntity.ok().body(ratingAttributeMappingsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /rating-attribute-mappings/:id} : get the "id" ratingAttributeMappings.
     *
     * @param id the id of the ratingAttributeMappings to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ratingAttributeMappings, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rating-attribute-mappings/{id}")
    public ResponseEntity<RatingAttributeMappings> getRatingAttributeMappings(@PathVariable Long id) {
        log.debug("REST request to get RatingAttributeMappings : {}", id);
        Optional<RatingAttributeMappings> ratingAttributeMappings = ratingAttributeMappingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ratingAttributeMappings);
    }

    /**
     * {@code DELETE  /rating-attribute-mappings/:id} : delete the "id" ratingAttributeMappings.
     *
     * @param id the id of the ratingAttributeMappings to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rating-attribute-mappings/{id}")
    public ResponseEntity<Void> deleteRatingAttributeMappings(@PathVariable Long id) {
        log.debug("REST request to delete RatingAttributeMappings : {}", id);
        ratingAttributeMappingsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
