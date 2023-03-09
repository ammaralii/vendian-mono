package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.RatingCategories;
import com.venturedive.vendian_mono.repository.RatingCategoriesRepository;
import com.venturedive.vendian_mono.service.RatingCategoriesQueryService;
import com.venturedive.vendian_mono.service.RatingCategoriesService;
import com.venturedive.vendian_mono.service.criteria.RatingCategoriesCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.RatingCategories}.
 */
@RestController
@RequestMapping("/api")
public class RatingCategoriesResource {

    private final Logger log = LoggerFactory.getLogger(RatingCategoriesResource.class);

    private static final String ENTITY_NAME = "ratingCategories";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RatingCategoriesService ratingCategoriesService;

    private final RatingCategoriesRepository ratingCategoriesRepository;

    private final RatingCategoriesQueryService ratingCategoriesQueryService;

    public RatingCategoriesResource(
        RatingCategoriesService ratingCategoriesService,
        RatingCategoriesRepository ratingCategoriesRepository,
        RatingCategoriesQueryService ratingCategoriesQueryService
    ) {
        this.ratingCategoriesService = ratingCategoriesService;
        this.ratingCategoriesRepository = ratingCategoriesRepository;
        this.ratingCategoriesQueryService = ratingCategoriesQueryService;
    }

    /**
     * {@code POST  /rating-categories} : Create a new ratingCategories.
     *
     * @param ratingCategories the ratingCategories to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ratingCategories, or with status {@code 400 (Bad Request)} if the ratingCategories has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rating-categories")
    public ResponseEntity<RatingCategories> createRatingCategories(@Valid @RequestBody RatingCategories ratingCategories)
        throws URISyntaxException {
        log.debug("REST request to save RatingCategories : {}", ratingCategories);
        if (ratingCategories.getId() != null) {
            throw new BadRequestAlertException("A new ratingCategories cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RatingCategories result = ratingCategoriesService.save(ratingCategories);
        return ResponseEntity
            .created(new URI("/api/rating-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rating-categories/:id} : Updates an existing ratingCategories.
     *
     * @param id the id of the ratingCategories to save.
     * @param ratingCategories the ratingCategories to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ratingCategories,
     * or with status {@code 400 (Bad Request)} if the ratingCategories is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ratingCategories couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rating-categories/{id}")
    public ResponseEntity<RatingCategories> updateRatingCategories(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RatingCategories ratingCategories
    ) throws URISyntaxException {
        log.debug("REST request to update RatingCategories : {}, {}", id, ratingCategories);
        if (ratingCategories.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ratingCategories.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ratingCategoriesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RatingCategories result = ratingCategoriesService.update(ratingCategories);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ratingCategories.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rating-categories/:id} : Partial updates given fields of an existing ratingCategories, field will ignore if it is null
     *
     * @param id the id of the ratingCategories to save.
     * @param ratingCategories the ratingCategories to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ratingCategories,
     * or with status {@code 400 (Bad Request)} if the ratingCategories is not valid,
     * or with status {@code 404 (Not Found)} if the ratingCategories is not found,
     * or with status {@code 500 (Internal Server Error)} if the ratingCategories couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rating-categories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RatingCategories> partialUpdateRatingCategories(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RatingCategories ratingCategories
    ) throws URISyntaxException {
        log.debug("REST request to partial update RatingCategories partially : {}, {}", id, ratingCategories);
        if (ratingCategories.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ratingCategories.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ratingCategoriesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RatingCategories> result = ratingCategoriesService.partialUpdate(ratingCategories);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ratingCategories.getId().toString())
        );
    }

    /**
     * {@code GET  /rating-categories} : get all the ratingCategories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ratingCategories in body.
     */
    @GetMapping("/rating-categories")
    public ResponseEntity<List<RatingCategories>> getAllRatingCategories(
        RatingCategoriesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get RatingCategories by criteria: {}", criteria);
        Page<RatingCategories> page = ratingCategoriesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rating-categories/count} : count all the ratingCategories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/rating-categories/count")
    public ResponseEntity<Long> countRatingCategories(RatingCategoriesCriteria criteria) {
        log.debug("REST request to count RatingCategories by criteria: {}", criteria);
        return ResponseEntity.ok().body(ratingCategoriesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /rating-categories/:id} : get the "id" ratingCategories.
     *
     * @param id the id of the ratingCategories to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ratingCategories, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rating-categories/{id}")
    public ResponseEntity<RatingCategories> getRatingCategories(@PathVariable Long id) {
        log.debug("REST request to get RatingCategories : {}", id);
        Optional<RatingCategories> ratingCategories = ratingCategoriesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ratingCategories);
    }

    /**
     * {@code DELETE  /rating-categories/:id} : delete the "id" ratingCategories.
     *
     * @param id the id of the ratingCategories to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rating-categories/{id}")
    public ResponseEntity<Void> deleteRatingCategories(@PathVariable Long id) {
        log.debug("REST request to delete RatingCategories : {}", id);
        ratingCategoriesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
