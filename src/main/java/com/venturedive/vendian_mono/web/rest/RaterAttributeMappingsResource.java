package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.RaterAttributeMappings;
import com.venturedive.vendian_mono.repository.RaterAttributeMappingsRepository;
import com.venturedive.vendian_mono.service.RaterAttributeMappingsQueryService;
import com.venturedive.vendian_mono.service.RaterAttributeMappingsService;
import com.venturedive.vendian_mono.service.criteria.RaterAttributeMappingsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.RaterAttributeMappings}.
 */
@RestController
@RequestMapping("/api")
public class RaterAttributeMappingsResource {

    private final Logger log = LoggerFactory.getLogger(RaterAttributeMappingsResource.class);

    private static final String ENTITY_NAME = "raterAttributeMappings";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RaterAttributeMappingsService raterAttributeMappingsService;

    private final RaterAttributeMappingsRepository raterAttributeMappingsRepository;

    private final RaterAttributeMappingsQueryService raterAttributeMappingsQueryService;

    public RaterAttributeMappingsResource(
        RaterAttributeMappingsService raterAttributeMappingsService,
        RaterAttributeMappingsRepository raterAttributeMappingsRepository,
        RaterAttributeMappingsQueryService raterAttributeMappingsQueryService
    ) {
        this.raterAttributeMappingsService = raterAttributeMappingsService;
        this.raterAttributeMappingsRepository = raterAttributeMappingsRepository;
        this.raterAttributeMappingsQueryService = raterAttributeMappingsQueryService;
    }

    /**
     * {@code POST  /rater-attribute-mappings} : Create a new raterAttributeMappings.
     *
     * @param raterAttributeMappings the raterAttributeMappings to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new raterAttributeMappings, or with status {@code 400 (Bad Request)} if the raterAttributeMappings has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rater-attribute-mappings")
    public ResponseEntity<RaterAttributeMappings> createRaterAttributeMappings(
        @Valid @RequestBody RaterAttributeMappings raterAttributeMappings
    ) throws URISyntaxException {
        log.debug("REST request to save RaterAttributeMappings : {}", raterAttributeMappings);
        if (raterAttributeMappings.getId() != null) {
            throw new BadRequestAlertException("A new raterAttributeMappings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RaterAttributeMappings result = raterAttributeMappingsService.save(raterAttributeMappings);
        return ResponseEntity
            .created(new URI("/api/rater-attribute-mappings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rater-attribute-mappings/:id} : Updates an existing raterAttributeMappings.
     *
     * @param id the id of the raterAttributeMappings to save.
     * @param raterAttributeMappings the raterAttributeMappings to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated raterAttributeMappings,
     * or with status {@code 400 (Bad Request)} if the raterAttributeMappings is not valid,
     * or with status {@code 500 (Internal Server Error)} if the raterAttributeMappings couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rater-attribute-mappings/{id}")
    public ResponseEntity<RaterAttributeMappings> updateRaterAttributeMappings(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RaterAttributeMappings raterAttributeMappings
    ) throws URISyntaxException {
        log.debug("REST request to update RaterAttributeMappings : {}, {}", id, raterAttributeMappings);
        if (raterAttributeMappings.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, raterAttributeMappings.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!raterAttributeMappingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RaterAttributeMappings result = raterAttributeMappingsService.update(raterAttributeMappings);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, raterAttributeMappings.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rater-attribute-mappings/:id} : Partial updates given fields of an existing raterAttributeMappings, field will ignore if it is null
     *
     * @param id the id of the raterAttributeMappings to save.
     * @param raterAttributeMappings the raterAttributeMappings to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated raterAttributeMappings,
     * or with status {@code 400 (Bad Request)} if the raterAttributeMappings is not valid,
     * or with status {@code 404 (Not Found)} if the raterAttributeMappings is not found,
     * or with status {@code 500 (Internal Server Error)} if the raterAttributeMappings couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rater-attribute-mappings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RaterAttributeMappings> partialUpdateRaterAttributeMappings(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RaterAttributeMappings raterAttributeMappings
    ) throws URISyntaxException {
        log.debug("REST request to partial update RaterAttributeMappings partially : {}, {}", id, raterAttributeMappings);
        if (raterAttributeMappings.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, raterAttributeMappings.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!raterAttributeMappingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RaterAttributeMappings> result = raterAttributeMappingsService.partialUpdate(raterAttributeMappings);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, raterAttributeMappings.getId().toString())
        );
    }

    /**
     * {@code GET  /rater-attribute-mappings} : get all the raterAttributeMappings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of raterAttributeMappings in body.
     */
    @GetMapping("/rater-attribute-mappings")
    public ResponseEntity<List<RaterAttributeMappings>> getAllRaterAttributeMappings(
        RaterAttributeMappingsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get RaterAttributeMappings by criteria: {}", criteria);
        Page<RaterAttributeMappings> page = raterAttributeMappingsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rater-attribute-mappings/count} : count all the raterAttributeMappings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/rater-attribute-mappings/count")
    public ResponseEntity<Long> countRaterAttributeMappings(RaterAttributeMappingsCriteria criteria) {
        log.debug("REST request to count RaterAttributeMappings by criteria: {}", criteria);
        return ResponseEntity.ok().body(raterAttributeMappingsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /rater-attribute-mappings/:id} : get the "id" raterAttributeMappings.
     *
     * @param id the id of the raterAttributeMappings to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the raterAttributeMappings, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rater-attribute-mappings/{id}")
    public ResponseEntity<RaterAttributeMappings> getRaterAttributeMappings(@PathVariable Long id) {
        log.debug("REST request to get RaterAttributeMappings : {}", id);
        Optional<RaterAttributeMappings> raterAttributeMappings = raterAttributeMappingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(raterAttributeMappings);
    }

    /**
     * {@code DELETE  /rater-attribute-mappings/:id} : delete the "id" raterAttributeMappings.
     *
     * @param id the id of the raterAttributeMappings to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rater-attribute-mappings/{id}")
    public ResponseEntity<Void> deleteRaterAttributeMappings(@PathVariable Long id) {
        log.debug("REST request to delete RaterAttributeMappings : {}", id);
        raterAttributeMappingsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
