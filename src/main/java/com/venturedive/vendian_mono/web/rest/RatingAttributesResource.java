package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.RatingAttributes;
import com.venturedive.vendian_mono.repository.RatingAttributesRepository;
import com.venturedive.vendian_mono.service.RatingAttributesQueryService;
import com.venturedive.vendian_mono.service.RatingAttributesService;
import com.venturedive.vendian_mono.service.criteria.RatingAttributesCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.RatingAttributes}.
 */
@RestController
@RequestMapping("/api")
public class RatingAttributesResource {

    private final Logger log = LoggerFactory.getLogger(RatingAttributesResource.class);

    private static final String ENTITY_NAME = "ratingAttributes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RatingAttributesService ratingAttributesService;

    private final RatingAttributesRepository ratingAttributesRepository;

    private final RatingAttributesQueryService ratingAttributesQueryService;

    public RatingAttributesResource(
        RatingAttributesService ratingAttributesService,
        RatingAttributesRepository ratingAttributesRepository,
        RatingAttributesQueryService ratingAttributesQueryService
    ) {
        this.ratingAttributesService = ratingAttributesService;
        this.ratingAttributesRepository = ratingAttributesRepository;
        this.ratingAttributesQueryService = ratingAttributesQueryService;
    }

    /**
     * {@code POST  /rating-attributes} : Create a new ratingAttributes.
     *
     * @param ratingAttributes the ratingAttributes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ratingAttributes, or with status {@code 400 (Bad Request)} if the ratingAttributes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rating-attributes")
    public ResponseEntity<RatingAttributes> createRatingAttributes(@Valid @RequestBody RatingAttributes ratingAttributes)
        throws URISyntaxException {
        log.debug("REST request to save RatingAttributes : {}", ratingAttributes);
        if (ratingAttributes.getId() != null) {
            throw new BadRequestAlertException("A new ratingAttributes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RatingAttributes result = ratingAttributesService.save(ratingAttributes);
        return ResponseEntity
            .created(new URI("/api/rating-attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rating-attributes/:id} : Updates an existing ratingAttributes.
     *
     * @param id the id of the ratingAttributes to save.
     * @param ratingAttributes the ratingAttributes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ratingAttributes,
     * or with status {@code 400 (Bad Request)} if the ratingAttributes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ratingAttributes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rating-attributes/{id}")
    public ResponseEntity<RatingAttributes> updateRatingAttributes(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RatingAttributes ratingAttributes
    ) throws URISyntaxException {
        log.debug("REST request to update RatingAttributes : {}, {}", id, ratingAttributes);
        if (ratingAttributes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ratingAttributes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ratingAttributesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RatingAttributes result = ratingAttributesService.update(ratingAttributes);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ratingAttributes.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rating-attributes/:id} : Partial updates given fields of an existing ratingAttributes, field will ignore if it is null
     *
     * @param id the id of the ratingAttributes to save.
     * @param ratingAttributes the ratingAttributes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ratingAttributes,
     * or with status {@code 400 (Bad Request)} if the ratingAttributes is not valid,
     * or with status {@code 404 (Not Found)} if the ratingAttributes is not found,
     * or with status {@code 500 (Internal Server Error)} if the ratingAttributes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rating-attributes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RatingAttributes> partialUpdateRatingAttributes(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RatingAttributes ratingAttributes
    ) throws URISyntaxException {
        log.debug("REST request to partial update RatingAttributes partially : {}, {}", id, ratingAttributes);
        if (ratingAttributes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ratingAttributes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ratingAttributesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RatingAttributes> result = ratingAttributesService.partialUpdate(ratingAttributes);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ratingAttributes.getId().toString())
        );
    }

    /**
     * {@code GET  /rating-attributes} : get all the ratingAttributes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ratingAttributes in body.
     */
    @GetMapping("/rating-attributes")
    public ResponseEntity<List<RatingAttributes>> getAllRatingAttributes(
        RatingAttributesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get RatingAttributes by criteria: {}", criteria);
        Page<RatingAttributes> page = ratingAttributesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rating-attributes/count} : count all the ratingAttributes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/rating-attributes/count")
    public ResponseEntity<Long> countRatingAttributes(RatingAttributesCriteria criteria) {
        log.debug("REST request to count RatingAttributes by criteria: {}", criteria);
        return ResponseEntity.ok().body(ratingAttributesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /rating-attributes/:id} : get the "id" ratingAttributes.
     *
     * @param id the id of the ratingAttributes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ratingAttributes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rating-attributes/{id}")
    public ResponseEntity<RatingAttributes> getRatingAttributes(@PathVariable Long id) {
        log.debug("REST request to get RatingAttributes : {}", id);
        Optional<RatingAttributes> ratingAttributes = ratingAttributesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ratingAttributes);
    }

    /**
     * {@code DELETE  /rating-attributes/:id} : delete the "id" ratingAttributes.
     *
     * @param id the id of the ratingAttributes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rating-attributes/{id}")
    public ResponseEntity<Void> deleteRatingAttributes(@PathVariable Long id) {
        log.debug("REST request to delete RatingAttributes : {}", id);
        ratingAttributesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
