package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.RaterAttributes;
import com.venturedive.vendian_mono.repository.RaterAttributesRepository;
import com.venturedive.vendian_mono.service.RaterAttributesQueryService;
import com.venturedive.vendian_mono.service.RaterAttributesService;
import com.venturedive.vendian_mono.service.criteria.RaterAttributesCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.RaterAttributes}.
 */
@RestController
@RequestMapping("/api")
public class RaterAttributesResource {

    private final Logger log = LoggerFactory.getLogger(RaterAttributesResource.class);

    private static final String ENTITY_NAME = "raterAttributes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RaterAttributesService raterAttributesService;

    private final RaterAttributesRepository raterAttributesRepository;

    private final RaterAttributesQueryService raterAttributesQueryService;

    public RaterAttributesResource(
        RaterAttributesService raterAttributesService,
        RaterAttributesRepository raterAttributesRepository,
        RaterAttributesQueryService raterAttributesQueryService
    ) {
        this.raterAttributesService = raterAttributesService;
        this.raterAttributesRepository = raterAttributesRepository;
        this.raterAttributesQueryService = raterAttributesQueryService;
    }

    /**
     * {@code POST  /rater-attributes} : Create a new raterAttributes.
     *
     * @param raterAttributes the raterAttributes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new raterAttributes, or with status {@code 400 (Bad Request)} if the raterAttributes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rater-attributes")
    public ResponseEntity<RaterAttributes> createRaterAttributes(@Valid @RequestBody RaterAttributes raterAttributes)
        throws URISyntaxException {
        log.debug("REST request to save RaterAttributes : {}", raterAttributes);
        if (raterAttributes.getId() != null) {
            throw new BadRequestAlertException("A new raterAttributes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RaterAttributes result = raterAttributesService.save(raterAttributes);
        return ResponseEntity
            .created(new URI("/api/rater-attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rater-attributes/:id} : Updates an existing raterAttributes.
     *
     * @param id the id of the raterAttributes to save.
     * @param raterAttributes the raterAttributes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated raterAttributes,
     * or with status {@code 400 (Bad Request)} if the raterAttributes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the raterAttributes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rater-attributes/{id}")
    public ResponseEntity<RaterAttributes> updateRaterAttributes(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RaterAttributes raterAttributes
    ) throws URISyntaxException {
        log.debug("REST request to update RaterAttributes : {}, {}", id, raterAttributes);
        if (raterAttributes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, raterAttributes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!raterAttributesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RaterAttributes result = raterAttributesService.update(raterAttributes);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, raterAttributes.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rater-attributes/:id} : Partial updates given fields of an existing raterAttributes, field will ignore if it is null
     *
     * @param id the id of the raterAttributes to save.
     * @param raterAttributes the raterAttributes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated raterAttributes,
     * or with status {@code 400 (Bad Request)} if the raterAttributes is not valid,
     * or with status {@code 404 (Not Found)} if the raterAttributes is not found,
     * or with status {@code 500 (Internal Server Error)} if the raterAttributes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rater-attributes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RaterAttributes> partialUpdateRaterAttributes(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RaterAttributes raterAttributes
    ) throws URISyntaxException {
        log.debug("REST request to partial update RaterAttributes partially : {}, {}", id, raterAttributes);
        if (raterAttributes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, raterAttributes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!raterAttributesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RaterAttributes> result = raterAttributesService.partialUpdate(raterAttributes);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, raterAttributes.getId().toString())
        );
    }

    /**
     * {@code GET  /rater-attributes} : get all the raterAttributes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of raterAttributes in body.
     */
    @GetMapping("/rater-attributes")
    public ResponseEntity<List<RaterAttributes>> getAllRaterAttributes(
        RaterAttributesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get RaterAttributes by criteria: {}", criteria);
        Page<RaterAttributes> page = raterAttributesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rater-attributes/count} : count all the raterAttributes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/rater-attributes/count")
    public ResponseEntity<Long> countRaterAttributes(RaterAttributesCriteria criteria) {
        log.debug("REST request to count RaterAttributes by criteria: {}", criteria);
        return ResponseEntity.ok().body(raterAttributesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /rater-attributes/:id} : get the "id" raterAttributes.
     *
     * @param id the id of the raterAttributes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the raterAttributes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rater-attributes/{id}")
    public ResponseEntity<RaterAttributes> getRaterAttributes(@PathVariable Long id) {
        log.debug("REST request to get RaterAttributes : {}", id);
        Optional<RaterAttributes> raterAttributes = raterAttributesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(raterAttributes);
    }

    /**
     * {@code DELETE  /rater-attributes/:id} : delete the "id" raterAttributes.
     *
     * @param id the id of the raterAttributes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rater-attributes/{id}")
    public ResponseEntity<Void> deleteRaterAttributes(@PathVariable Long id) {
        log.debug("REST request to delete RaterAttributes : {}", id);
        raterAttributesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
