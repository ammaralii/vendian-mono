package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.Designations;
import com.venturedive.vendian_mono.repository.DesignationsRepository;
import com.venturedive.vendian_mono.service.DesignationsQueryService;
import com.venturedive.vendian_mono.service.DesignationsService;
import com.venturedive.vendian_mono.service.criteria.DesignationsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.Designations}.
 */
@RestController
@RequestMapping("/api")
public class DesignationsResource {

    private final Logger log = LoggerFactory.getLogger(DesignationsResource.class);

    private static final String ENTITY_NAME = "designations";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DesignationsService designationsService;

    private final DesignationsRepository designationsRepository;

    private final DesignationsQueryService designationsQueryService;

    public DesignationsResource(
        DesignationsService designationsService,
        DesignationsRepository designationsRepository,
        DesignationsQueryService designationsQueryService
    ) {
        this.designationsService = designationsService;
        this.designationsRepository = designationsRepository;
        this.designationsQueryService = designationsQueryService;
    }

    /**
     * {@code POST  /designations} : Create a new designations.
     *
     * @param designations the designations to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new designations, or with status {@code 400 (Bad Request)} if the designations has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/designations")
    public ResponseEntity<Designations> createDesignations(@Valid @RequestBody Designations designations) throws URISyntaxException {
        log.debug("REST request to save Designations : {}", designations);
        if (designations.getId() != null) {
            throw new BadRequestAlertException("A new designations cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Designations result = designationsService.save(designations);
        return ResponseEntity
            .created(new URI("/api/designations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /designations/:id} : Updates an existing designations.
     *
     * @param id the id of the designations to save.
     * @param designations the designations to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated designations,
     * or with status {@code 400 (Bad Request)} if the designations is not valid,
     * or with status {@code 500 (Internal Server Error)} if the designations couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/designations/{id}")
    public ResponseEntity<Designations> updateDesignations(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Designations designations
    ) throws URISyntaxException {
        log.debug("REST request to update Designations : {}, {}", id, designations);
        if (designations.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, designations.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!designationsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Designations result = designationsService.update(designations);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, designations.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /designations/:id} : Partial updates given fields of an existing designations, field will ignore if it is null
     *
     * @param id the id of the designations to save.
     * @param designations the designations to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated designations,
     * or with status {@code 400 (Bad Request)} if the designations is not valid,
     * or with status {@code 404 (Not Found)} if the designations is not found,
     * or with status {@code 500 (Internal Server Error)} if the designations couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/designations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Designations> partialUpdateDesignations(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Designations designations
    ) throws URISyntaxException {
        log.debug("REST request to partial update Designations partially : {}, {}", id, designations);
        if (designations.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, designations.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!designationsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Designations> result = designationsService.partialUpdate(designations);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, designations.getId().toString())
        );
    }

    /**
     * {@code GET  /designations} : get all the designations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of designations in body.
     */
    @GetMapping("/designations")
    public ResponseEntity<List<Designations>> getAllDesignations(
        DesignationsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Designations by criteria: {}", criteria);
        Page<Designations> page = designationsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /designations/count} : count all the designations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/designations/count")
    public ResponseEntity<Long> countDesignations(DesignationsCriteria criteria) {
        log.debug("REST request to count Designations by criteria: {}", criteria);
        return ResponseEntity.ok().body(designationsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /designations/:id} : get the "id" designations.
     *
     * @param id the id of the designations to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the designations, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/designations/{id}")
    public ResponseEntity<Designations> getDesignations(@PathVariable Long id) {
        log.debug("REST request to get Designations : {}", id);
        Optional<Designations> designations = designationsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(designations);
    }

    /**
     * {@code DELETE  /designations/:id} : delete the "id" designations.
     *
     * @param id the id of the designations to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/designations/{id}")
    public ResponseEntity<Void> deleteDesignations(@PathVariable Long id) {
        log.debug("REST request to delete Designations : {}", id);
        designationsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
