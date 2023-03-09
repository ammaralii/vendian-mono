package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.PcRatings;
import com.venturedive.vendian_mono.repository.PcRatingsRepository;
import com.venturedive.vendian_mono.service.PcRatingsQueryService;
import com.venturedive.vendian_mono.service.PcRatingsService;
import com.venturedive.vendian_mono.service.criteria.PcRatingsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.PcRatings}.
 */
@RestController
@RequestMapping("/api")
public class PcRatingsResource {

    private final Logger log = LoggerFactory.getLogger(PcRatingsResource.class);

    private static final String ENTITY_NAME = "pcRatings";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PcRatingsService pcRatingsService;

    private final PcRatingsRepository pcRatingsRepository;

    private final PcRatingsQueryService pcRatingsQueryService;

    public PcRatingsResource(
        PcRatingsService pcRatingsService,
        PcRatingsRepository pcRatingsRepository,
        PcRatingsQueryService pcRatingsQueryService
    ) {
        this.pcRatingsService = pcRatingsService;
        this.pcRatingsRepository = pcRatingsRepository;
        this.pcRatingsQueryService = pcRatingsQueryService;
    }

    /**
     * {@code POST  /pc-ratings} : Create a new pcRatings.
     *
     * @param pcRatings the pcRatings to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pcRatings, or with status {@code 400 (Bad Request)} if the pcRatings has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pc-ratings")
    public ResponseEntity<PcRatings> createPcRatings(@Valid @RequestBody PcRatings pcRatings) throws URISyntaxException {
        log.debug("REST request to save PcRatings : {}", pcRatings);
        if (pcRatings.getId() != null) {
            throw new BadRequestAlertException("A new pcRatings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PcRatings result = pcRatingsService.save(pcRatings);
        return ResponseEntity
            .created(new URI("/api/pc-ratings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pc-ratings/:id} : Updates an existing pcRatings.
     *
     * @param id the id of the pcRatings to save.
     * @param pcRatings the pcRatings to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pcRatings,
     * or with status {@code 400 (Bad Request)} if the pcRatings is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pcRatings couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pc-ratings/{id}")
    public ResponseEntity<PcRatings> updatePcRatings(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PcRatings pcRatings
    ) throws URISyntaxException {
        log.debug("REST request to update PcRatings : {}, {}", id, pcRatings);
        if (pcRatings.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pcRatings.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pcRatingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PcRatings result = pcRatingsService.update(pcRatings);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pcRatings.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pc-ratings/:id} : Partial updates given fields of an existing pcRatings, field will ignore if it is null
     *
     * @param id the id of the pcRatings to save.
     * @param pcRatings the pcRatings to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pcRatings,
     * or with status {@code 400 (Bad Request)} if the pcRatings is not valid,
     * or with status {@code 404 (Not Found)} if the pcRatings is not found,
     * or with status {@code 500 (Internal Server Error)} if the pcRatings couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pc-ratings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PcRatings> partialUpdatePcRatings(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PcRatings pcRatings
    ) throws URISyntaxException {
        log.debug("REST request to partial update PcRatings partially : {}, {}", id, pcRatings);
        if (pcRatings.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pcRatings.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pcRatingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PcRatings> result = pcRatingsService.partialUpdate(pcRatings);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pcRatings.getId().toString())
        );
    }

    /**
     * {@code GET  /pc-ratings} : get all the pcRatings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pcRatings in body.
     */
    @GetMapping("/pc-ratings")
    public ResponseEntity<List<PcRatings>> getAllPcRatings(
        PcRatingsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PcRatings by criteria: {}", criteria);
        Page<PcRatings> page = pcRatingsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pc-ratings/count} : count all the pcRatings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/pc-ratings/count")
    public ResponseEntity<Long> countPcRatings(PcRatingsCriteria criteria) {
        log.debug("REST request to count PcRatings by criteria: {}", criteria);
        return ResponseEntity.ok().body(pcRatingsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /pc-ratings/:id} : get the "id" pcRatings.
     *
     * @param id the id of the pcRatings to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pcRatings, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pc-ratings/{id}")
    public ResponseEntity<PcRatings> getPcRatings(@PathVariable Long id) {
        log.debug("REST request to get PcRatings : {}", id);
        Optional<PcRatings> pcRatings = pcRatingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pcRatings);
    }

    /**
     * {@code DELETE  /pc-ratings/:id} : delete the "id" pcRatings.
     *
     * @param id the id of the pcRatings to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pc-ratings/{id}")
    public ResponseEntity<Void> deletePcRatings(@PathVariable Long id) {
        log.debug("REST request to delete PcRatings : {}", id);
        pcRatingsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
