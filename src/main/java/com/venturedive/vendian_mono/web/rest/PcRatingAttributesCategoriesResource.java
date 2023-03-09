package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.PcRatingAttributesCategories;
import com.venturedive.vendian_mono.repository.PcRatingAttributesCategoriesRepository;
import com.venturedive.vendian_mono.service.PcRatingAttributesCategoriesQueryService;
import com.venturedive.vendian_mono.service.PcRatingAttributesCategoriesService;
import com.venturedive.vendian_mono.service.criteria.PcRatingAttributesCategoriesCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.PcRatingAttributesCategories}.
 */
@RestController
@RequestMapping("/api")
public class PcRatingAttributesCategoriesResource {

    private final Logger log = LoggerFactory.getLogger(PcRatingAttributesCategoriesResource.class);

    private static final String ENTITY_NAME = "pcRatingAttributesCategories";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PcRatingAttributesCategoriesService pcRatingAttributesCategoriesService;

    private final PcRatingAttributesCategoriesRepository pcRatingAttributesCategoriesRepository;

    private final PcRatingAttributesCategoriesQueryService pcRatingAttributesCategoriesQueryService;

    public PcRatingAttributesCategoriesResource(
        PcRatingAttributesCategoriesService pcRatingAttributesCategoriesService,
        PcRatingAttributesCategoriesRepository pcRatingAttributesCategoriesRepository,
        PcRatingAttributesCategoriesQueryService pcRatingAttributesCategoriesQueryService
    ) {
        this.pcRatingAttributesCategoriesService = pcRatingAttributesCategoriesService;
        this.pcRatingAttributesCategoriesRepository = pcRatingAttributesCategoriesRepository;
        this.pcRatingAttributesCategoriesQueryService = pcRatingAttributesCategoriesQueryService;
    }

    /**
     * {@code POST  /pc-rating-attributes-categories} : Create a new pcRatingAttributesCategories.
     *
     * @param pcRatingAttributesCategories the pcRatingAttributesCategories to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pcRatingAttributesCategories, or with status {@code 400 (Bad Request)} if the pcRatingAttributesCategories has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pc-rating-attributes-categories")
    public ResponseEntity<PcRatingAttributesCategories> createPcRatingAttributesCategories(
        @Valid @RequestBody PcRatingAttributesCategories pcRatingAttributesCategories
    ) throws URISyntaxException {
        log.debug("REST request to save PcRatingAttributesCategories : {}", pcRatingAttributesCategories);
        if (pcRatingAttributesCategories.getId() != null) {
            throw new BadRequestAlertException("A new pcRatingAttributesCategories cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PcRatingAttributesCategories result = pcRatingAttributesCategoriesService.save(pcRatingAttributesCategories);
        return ResponseEntity
            .created(new URI("/api/pc-rating-attributes-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pc-rating-attributes-categories/:id} : Updates an existing pcRatingAttributesCategories.
     *
     * @param id the id of the pcRatingAttributesCategories to save.
     * @param pcRatingAttributesCategories the pcRatingAttributesCategories to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pcRatingAttributesCategories,
     * or with status {@code 400 (Bad Request)} if the pcRatingAttributesCategories is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pcRatingAttributesCategories couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pc-rating-attributes-categories/{id}")
    public ResponseEntity<PcRatingAttributesCategories> updatePcRatingAttributesCategories(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PcRatingAttributesCategories pcRatingAttributesCategories
    ) throws URISyntaxException {
        log.debug("REST request to update PcRatingAttributesCategories : {}, {}", id, pcRatingAttributesCategories);
        if (pcRatingAttributesCategories.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pcRatingAttributesCategories.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pcRatingAttributesCategoriesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PcRatingAttributesCategories result = pcRatingAttributesCategoriesService.update(pcRatingAttributesCategories);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pcRatingAttributesCategories.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /pc-rating-attributes-categories/:id} : Partial updates given fields of an existing pcRatingAttributesCategories, field will ignore if it is null
     *
     * @param id the id of the pcRatingAttributesCategories to save.
     * @param pcRatingAttributesCategories the pcRatingAttributesCategories to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pcRatingAttributesCategories,
     * or with status {@code 400 (Bad Request)} if the pcRatingAttributesCategories is not valid,
     * or with status {@code 404 (Not Found)} if the pcRatingAttributesCategories is not found,
     * or with status {@code 500 (Internal Server Error)} if the pcRatingAttributesCategories couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pc-rating-attributes-categories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PcRatingAttributesCategories> partialUpdatePcRatingAttributesCategories(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PcRatingAttributesCategories pcRatingAttributesCategories
    ) throws URISyntaxException {
        log.debug("REST request to partial update PcRatingAttributesCategories partially : {}, {}", id, pcRatingAttributesCategories);
        if (pcRatingAttributesCategories.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pcRatingAttributesCategories.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pcRatingAttributesCategoriesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PcRatingAttributesCategories> result = pcRatingAttributesCategoriesService.partialUpdate(pcRatingAttributesCategories);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pcRatingAttributesCategories.getId().toString())
        );
    }

    /**
     * {@code GET  /pc-rating-attributes-categories} : get all the pcRatingAttributesCategories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pcRatingAttributesCategories in body.
     */
    @GetMapping("/pc-rating-attributes-categories")
    public ResponseEntity<List<PcRatingAttributesCategories>> getAllPcRatingAttributesCategories(
        PcRatingAttributesCategoriesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PcRatingAttributesCategories by criteria: {}", criteria);
        Page<PcRatingAttributesCategories> page = pcRatingAttributesCategoriesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pc-rating-attributes-categories/count} : count all the pcRatingAttributesCategories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/pc-rating-attributes-categories/count")
    public ResponseEntity<Long> countPcRatingAttributesCategories(PcRatingAttributesCategoriesCriteria criteria) {
        log.debug("REST request to count PcRatingAttributesCategories by criteria: {}", criteria);
        return ResponseEntity.ok().body(pcRatingAttributesCategoriesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /pc-rating-attributes-categories/:id} : get the "id" pcRatingAttributesCategories.
     *
     * @param id the id of the pcRatingAttributesCategories to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pcRatingAttributesCategories, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pc-rating-attributes-categories/{id}")
    public ResponseEntity<PcRatingAttributesCategories> getPcRatingAttributesCategories(@PathVariable Long id) {
        log.debug("REST request to get PcRatingAttributesCategories : {}", id);
        Optional<PcRatingAttributesCategories> pcRatingAttributesCategories = pcRatingAttributesCategoriesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pcRatingAttributesCategories);
    }

    /**
     * {@code DELETE  /pc-rating-attributes-categories/:id} : delete the "id" pcRatingAttributesCategories.
     *
     * @param id the id of the pcRatingAttributesCategories to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pc-rating-attributes-categories/{id}")
    public ResponseEntity<Void> deletePcRatingAttributesCategories(@PathVariable Long id) {
        log.debug("REST request to delete PcRatingAttributesCategories : {}", id);
        pcRatingAttributesCategoriesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
