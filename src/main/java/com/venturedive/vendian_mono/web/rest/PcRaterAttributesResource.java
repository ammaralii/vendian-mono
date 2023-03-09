package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.PcRaterAttributes;
import com.venturedive.vendian_mono.repository.PcRaterAttributesRepository;
import com.venturedive.vendian_mono.service.PcRaterAttributesQueryService;
import com.venturedive.vendian_mono.service.PcRaterAttributesService;
import com.venturedive.vendian_mono.service.criteria.PcRaterAttributesCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.PcRaterAttributes}.
 */
@RestController
@RequestMapping("/api")
public class PcRaterAttributesResource {

    private final Logger log = LoggerFactory.getLogger(PcRaterAttributesResource.class);

    private static final String ENTITY_NAME = "pcRaterAttributes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PcRaterAttributesService pcRaterAttributesService;

    private final PcRaterAttributesRepository pcRaterAttributesRepository;

    private final PcRaterAttributesQueryService pcRaterAttributesQueryService;

    public PcRaterAttributesResource(
        PcRaterAttributesService pcRaterAttributesService,
        PcRaterAttributesRepository pcRaterAttributesRepository,
        PcRaterAttributesQueryService pcRaterAttributesQueryService
    ) {
        this.pcRaterAttributesService = pcRaterAttributesService;
        this.pcRaterAttributesRepository = pcRaterAttributesRepository;
        this.pcRaterAttributesQueryService = pcRaterAttributesQueryService;
    }

    /**
     * {@code POST  /pc-rater-attributes} : Create a new pcRaterAttributes.
     *
     * @param pcRaterAttributes the pcRaterAttributes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pcRaterAttributes, or with status {@code 400 (Bad Request)} if the pcRaterAttributes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pc-rater-attributes")
    public ResponseEntity<PcRaterAttributes> createPcRaterAttributes(@Valid @RequestBody PcRaterAttributes pcRaterAttributes)
        throws URISyntaxException {
        log.debug("REST request to save PcRaterAttributes : {}", pcRaterAttributes);
        if (pcRaterAttributes.getId() != null) {
            throw new BadRequestAlertException("A new pcRaterAttributes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PcRaterAttributes result = pcRaterAttributesService.save(pcRaterAttributes);
        return ResponseEntity
            .created(new URI("/api/pc-rater-attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pc-rater-attributes/:id} : Updates an existing pcRaterAttributes.
     *
     * @param id the id of the pcRaterAttributes to save.
     * @param pcRaterAttributes the pcRaterAttributes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pcRaterAttributes,
     * or with status {@code 400 (Bad Request)} if the pcRaterAttributes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pcRaterAttributes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pc-rater-attributes/{id}")
    public ResponseEntity<PcRaterAttributes> updatePcRaterAttributes(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PcRaterAttributes pcRaterAttributes
    ) throws URISyntaxException {
        log.debug("REST request to update PcRaterAttributes : {}, {}", id, pcRaterAttributes);
        if (pcRaterAttributes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pcRaterAttributes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pcRaterAttributesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PcRaterAttributes result = pcRaterAttributesService.update(pcRaterAttributes);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pcRaterAttributes.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pc-rater-attributes/:id} : Partial updates given fields of an existing pcRaterAttributes, field will ignore if it is null
     *
     * @param id the id of the pcRaterAttributes to save.
     * @param pcRaterAttributes the pcRaterAttributes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pcRaterAttributes,
     * or with status {@code 400 (Bad Request)} if the pcRaterAttributes is not valid,
     * or with status {@code 404 (Not Found)} if the pcRaterAttributes is not found,
     * or with status {@code 500 (Internal Server Error)} if the pcRaterAttributes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pc-rater-attributes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PcRaterAttributes> partialUpdatePcRaterAttributes(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PcRaterAttributes pcRaterAttributes
    ) throws URISyntaxException {
        log.debug("REST request to partial update PcRaterAttributes partially : {}, {}", id, pcRaterAttributes);
        if (pcRaterAttributes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pcRaterAttributes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pcRaterAttributesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PcRaterAttributes> result = pcRaterAttributesService.partialUpdate(pcRaterAttributes);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pcRaterAttributes.getId().toString())
        );
    }

    /**
     * {@code GET  /pc-rater-attributes} : get all the pcRaterAttributes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pcRaterAttributes in body.
     */
    @GetMapping("/pc-rater-attributes")
    public ResponseEntity<List<PcRaterAttributes>> getAllPcRaterAttributes(
        PcRaterAttributesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PcRaterAttributes by criteria: {}", criteria);
        Page<PcRaterAttributes> page = pcRaterAttributesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pc-rater-attributes/count} : count all the pcRaterAttributes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/pc-rater-attributes/count")
    public ResponseEntity<Long> countPcRaterAttributes(PcRaterAttributesCriteria criteria) {
        log.debug("REST request to count PcRaterAttributes by criteria: {}", criteria);
        return ResponseEntity.ok().body(pcRaterAttributesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /pc-rater-attributes/:id} : get the "id" pcRaterAttributes.
     *
     * @param id the id of the pcRaterAttributes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pcRaterAttributes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pc-rater-attributes/{id}")
    public ResponseEntity<PcRaterAttributes> getPcRaterAttributes(@PathVariable Long id) {
        log.debug("REST request to get PcRaterAttributes : {}", id);
        Optional<PcRaterAttributes> pcRaterAttributes = pcRaterAttributesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pcRaterAttributes);
    }

    /**
     * {@code DELETE  /pc-rater-attributes/:id} : delete the "id" pcRaterAttributes.
     *
     * @param id the id of the pcRaterAttributes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pc-rater-attributes/{id}")
    public ResponseEntity<Void> deletePcRaterAttributes(@PathVariable Long id) {
        log.debug("REST request to delete PcRaterAttributes : {}", id);
        pcRaterAttributesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
