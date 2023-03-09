package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.PcRatingAttributes;
import com.venturedive.vendian_mono.repository.PcRatingAttributesRepository;
import com.venturedive.vendian_mono.service.PcRatingAttributesQueryService;
import com.venturedive.vendian_mono.service.PcRatingAttributesService;
import com.venturedive.vendian_mono.service.criteria.PcRatingAttributesCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.PcRatingAttributes}.
 */
@RestController
@RequestMapping("/api")
public class PcRatingAttributesResource {

    private final Logger log = LoggerFactory.getLogger(PcRatingAttributesResource.class);

    private static final String ENTITY_NAME = "pcRatingAttributes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PcRatingAttributesService pcRatingAttributesService;

    private final PcRatingAttributesRepository pcRatingAttributesRepository;

    private final PcRatingAttributesQueryService pcRatingAttributesQueryService;

    public PcRatingAttributesResource(
        PcRatingAttributesService pcRatingAttributesService,
        PcRatingAttributesRepository pcRatingAttributesRepository,
        PcRatingAttributesQueryService pcRatingAttributesQueryService
    ) {
        this.pcRatingAttributesService = pcRatingAttributesService;
        this.pcRatingAttributesRepository = pcRatingAttributesRepository;
        this.pcRatingAttributesQueryService = pcRatingAttributesQueryService;
    }

    /**
     * {@code POST  /pc-rating-attributes} : Create a new pcRatingAttributes.
     *
     * @param pcRatingAttributes the pcRatingAttributes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pcRatingAttributes, or with status {@code 400 (Bad Request)} if the pcRatingAttributes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pc-rating-attributes")
    public ResponseEntity<PcRatingAttributes> createPcRatingAttributes(@Valid @RequestBody PcRatingAttributes pcRatingAttributes)
        throws URISyntaxException {
        log.debug("REST request to save PcRatingAttributes : {}", pcRatingAttributes);
        if (pcRatingAttributes.getId() != null) {
            throw new BadRequestAlertException("A new pcRatingAttributes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PcRatingAttributes result = pcRatingAttributesService.save(pcRatingAttributes);
        return ResponseEntity
            .created(new URI("/api/pc-rating-attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pc-rating-attributes/:id} : Updates an existing pcRatingAttributes.
     *
     * @param id the id of the pcRatingAttributes to save.
     * @param pcRatingAttributes the pcRatingAttributes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pcRatingAttributes,
     * or with status {@code 400 (Bad Request)} if the pcRatingAttributes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pcRatingAttributes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pc-rating-attributes/{id}")
    public ResponseEntity<PcRatingAttributes> updatePcRatingAttributes(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PcRatingAttributes pcRatingAttributes
    ) throws URISyntaxException {
        log.debug("REST request to update PcRatingAttributes : {}, {}", id, pcRatingAttributes);
        if (pcRatingAttributes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pcRatingAttributes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pcRatingAttributesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PcRatingAttributes result = pcRatingAttributesService.update(pcRatingAttributes);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pcRatingAttributes.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pc-rating-attributes/:id} : Partial updates given fields of an existing pcRatingAttributes, field will ignore if it is null
     *
     * @param id the id of the pcRatingAttributes to save.
     * @param pcRatingAttributes the pcRatingAttributes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pcRatingAttributes,
     * or with status {@code 400 (Bad Request)} if the pcRatingAttributes is not valid,
     * or with status {@code 404 (Not Found)} if the pcRatingAttributes is not found,
     * or with status {@code 500 (Internal Server Error)} if the pcRatingAttributes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pc-rating-attributes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PcRatingAttributes> partialUpdatePcRatingAttributes(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PcRatingAttributes pcRatingAttributes
    ) throws URISyntaxException {
        log.debug("REST request to partial update PcRatingAttributes partially : {}, {}", id, pcRatingAttributes);
        if (pcRatingAttributes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pcRatingAttributes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pcRatingAttributesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PcRatingAttributes> result = pcRatingAttributesService.partialUpdate(pcRatingAttributes);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pcRatingAttributes.getId().toString())
        );
    }

    /**
     * {@code GET  /pc-rating-attributes} : get all the pcRatingAttributes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pcRatingAttributes in body.
     */
    @GetMapping("/pc-rating-attributes")
    public ResponseEntity<List<PcRatingAttributes>> getAllPcRatingAttributes(
        PcRatingAttributesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PcRatingAttributes by criteria: {}", criteria);
        Page<PcRatingAttributes> page = pcRatingAttributesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pc-rating-attributes/count} : count all the pcRatingAttributes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/pc-rating-attributes/count")
    public ResponseEntity<Long> countPcRatingAttributes(PcRatingAttributesCriteria criteria) {
        log.debug("REST request to count PcRatingAttributes by criteria: {}", criteria);
        return ResponseEntity.ok().body(pcRatingAttributesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /pc-rating-attributes/:id} : get the "id" pcRatingAttributes.
     *
     * @param id the id of the pcRatingAttributes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pcRatingAttributes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pc-rating-attributes/{id}")
    public ResponseEntity<PcRatingAttributes> getPcRatingAttributes(@PathVariable Long id) {
        log.debug("REST request to get PcRatingAttributes : {}", id);
        Optional<PcRatingAttributes> pcRatingAttributes = pcRatingAttributesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pcRatingAttributes);
    }

    /**
     * {@code DELETE  /pc-rating-attributes/:id} : delete the "id" pcRatingAttributes.
     *
     * @param id the id of the pcRatingAttributes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pc-rating-attributes/{id}")
    public ResponseEntity<Void> deletePcRatingAttributes(@PathVariable Long id) {
        log.debug("REST request to delete PcRatingAttributes : {}", id);
        pcRatingAttributesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
