package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.Ratings;
import com.venturedive.vendian_mono.repository.RatingsRepository;
import com.venturedive.vendian_mono.service.RatingsQueryService;
import com.venturedive.vendian_mono.service.RatingsService;
import com.venturedive.vendian_mono.service.criteria.RatingsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.Ratings}.
 */
@RestController
@RequestMapping("/api")
public class RatingsResource {

    private final Logger log = LoggerFactory.getLogger(RatingsResource.class);

    private static final String ENTITY_NAME = "ratings";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RatingsService ratingsService;

    private final RatingsRepository ratingsRepository;

    private final RatingsQueryService ratingsQueryService;

    public RatingsResource(RatingsService ratingsService, RatingsRepository ratingsRepository, RatingsQueryService ratingsQueryService) {
        this.ratingsService = ratingsService;
        this.ratingsRepository = ratingsRepository;
        this.ratingsQueryService = ratingsQueryService;
    }

    /**
     * {@code POST  /ratings} : Create a new ratings.
     *
     * @param ratings the ratings to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ratings, or with status {@code 400 (Bad Request)} if the ratings has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ratings")
    public ResponseEntity<Ratings> createRatings(@Valid @RequestBody Ratings ratings) throws URISyntaxException {
        log.debug("REST request to save Ratings : {}", ratings);
        if (ratings.getId() != null) {
            throw new BadRequestAlertException("A new ratings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ratings result = ratingsService.save(ratings);
        return ResponseEntity
            .created(new URI("/api/ratings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ratings/:id} : Updates an existing ratings.
     *
     * @param id the id of the ratings to save.
     * @param ratings the ratings to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ratings,
     * or with status {@code 400 (Bad Request)} if the ratings is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ratings couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ratings/{id}")
    public ResponseEntity<Ratings> updateRatings(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Ratings ratings
    ) throws URISyntaxException {
        log.debug("REST request to update Ratings : {}, {}", id, ratings);
        if (ratings.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ratings.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ratingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Ratings result = ratingsService.update(ratings);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ratings.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ratings/:id} : Partial updates given fields of an existing ratings, field will ignore if it is null
     *
     * @param id the id of the ratings to save.
     * @param ratings the ratings to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ratings,
     * or with status {@code 400 (Bad Request)} if the ratings is not valid,
     * or with status {@code 404 (Not Found)} if the ratings is not found,
     * or with status {@code 500 (Internal Server Error)} if the ratings couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ratings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Ratings> partialUpdateRatings(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Ratings ratings
    ) throws URISyntaxException {
        log.debug("REST request to partial update Ratings partially : {}, {}", id, ratings);
        if (ratings.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ratings.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ratingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Ratings> result = ratingsService.partialUpdate(ratings);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ratings.getId().toString())
        );
    }

    /**
     * {@code GET  /ratings} : get all the ratings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ratings in body.
     */
    @GetMapping("/ratings")
    public ResponseEntity<List<Ratings>> getAllRatings(
        RatingsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Ratings by criteria: {}", criteria);
        Page<Ratings> page = ratingsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ratings/count} : count all the ratings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ratings/count")
    public ResponseEntity<Long> countRatings(RatingsCriteria criteria) {
        log.debug("REST request to count Ratings by criteria: {}", criteria);
        return ResponseEntity.ok().body(ratingsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ratings/:id} : get the "id" ratings.
     *
     * @param id the id of the ratings to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ratings, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ratings/{id}")
    public ResponseEntity<Ratings> getRatings(@PathVariable Long id) {
        log.debug("REST request to get Ratings : {}", id);
        Optional<Ratings> ratings = ratingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ratings);
    }

    /**
     * {@code DELETE  /ratings/:id} : delete the "id" ratings.
     *
     * @param id the id of the ratings to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ratings/{id}")
    public ResponseEntity<Void> deleteRatings(@PathVariable Long id) {
        log.debug("REST request to delete Ratings : {}", id);
        ratingsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
