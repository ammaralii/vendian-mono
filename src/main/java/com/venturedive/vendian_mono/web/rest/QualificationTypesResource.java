package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.QualificationTypes;
import com.venturedive.vendian_mono.repository.QualificationTypesRepository;
import com.venturedive.vendian_mono.service.QualificationTypesQueryService;
import com.venturedive.vendian_mono.service.QualificationTypesService;
import com.venturedive.vendian_mono.service.criteria.QualificationTypesCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.QualificationTypes}.
 */
@RestController
@RequestMapping("/api")
public class QualificationTypesResource {

    private final Logger log = LoggerFactory.getLogger(QualificationTypesResource.class);

    private static final String ENTITY_NAME = "qualificationTypes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QualificationTypesService qualificationTypesService;

    private final QualificationTypesRepository qualificationTypesRepository;

    private final QualificationTypesQueryService qualificationTypesQueryService;

    public QualificationTypesResource(
        QualificationTypesService qualificationTypesService,
        QualificationTypesRepository qualificationTypesRepository,
        QualificationTypesQueryService qualificationTypesQueryService
    ) {
        this.qualificationTypesService = qualificationTypesService;
        this.qualificationTypesRepository = qualificationTypesRepository;
        this.qualificationTypesQueryService = qualificationTypesQueryService;
    }

    /**
     * {@code POST  /qualification-types} : Create a new qualificationTypes.
     *
     * @param qualificationTypes the qualificationTypes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new qualificationTypes, or with status {@code 400 (Bad Request)} if the qualificationTypes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/qualification-types")
    public ResponseEntity<QualificationTypes> createQualificationTypes(@Valid @RequestBody QualificationTypes qualificationTypes)
        throws URISyntaxException {
        log.debug("REST request to save QualificationTypes : {}", qualificationTypes);
        if (qualificationTypes.getId() != null) {
            throw new BadRequestAlertException("A new qualificationTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QualificationTypes result = qualificationTypesService.save(qualificationTypes);
        return ResponseEntity
            .created(new URI("/api/qualification-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /qualification-types/:id} : Updates an existing qualificationTypes.
     *
     * @param id the id of the qualificationTypes to save.
     * @param qualificationTypes the qualificationTypes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qualificationTypes,
     * or with status {@code 400 (Bad Request)} if the qualificationTypes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the qualificationTypes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/qualification-types/{id}")
    public ResponseEntity<QualificationTypes> updateQualificationTypes(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody QualificationTypes qualificationTypes
    ) throws URISyntaxException {
        log.debug("REST request to update QualificationTypes : {}, {}", id, qualificationTypes);
        if (qualificationTypes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qualificationTypes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qualificationTypesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        QualificationTypes result = qualificationTypesService.update(qualificationTypes);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, qualificationTypes.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /qualification-types/:id} : Partial updates given fields of an existing qualificationTypes, field will ignore if it is null
     *
     * @param id the id of the qualificationTypes to save.
     * @param qualificationTypes the qualificationTypes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qualificationTypes,
     * or with status {@code 400 (Bad Request)} if the qualificationTypes is not valid,
     * or with status {@code 404 (Not Found)} if the qualificationTypes is not found,
     * or with status {@code 500 (Internal Server Error)} if the qualificationTypes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/qualification-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<QualificationTypes> partialUpdateQualificationTypes(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody QualificationTypes qualificationTypes
    ) throws URISyntaxException {
        log.debug("REST request to partial update QualificationTypes partially : {}, {}", id, qualificationTypes);
        if (qualificationTypes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qualificationTypes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qualificationTypesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<QualificationTypes> result = qualificationTypesService.partialUpdate(qualificationTypes);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, qualificationTypes.getId().toString())
        );
    }

    /**
     * {@code GET  /qualification-types} : get all the qualificationTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of qualificationTypes in body.
     */
    @GetMapping("/qualification-types")
    public ResponseEntity<List<QualificationTypes>> getAllQualificationTypes(
        QualificationTypesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get QualificationTypes by criteria: {}", criteria);
        Page<QualificationTypes> page = qualificationTypesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /qualification-types/count} : count all the qualificationTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/qualification-types/count")
    public ResponseEntity<Long> countQualificationTypes(QualificationTypesCriteria criteria) {
        log.debug("REST request to count QualificationTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(qualificationTypesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /qualification-types/:id} : get the "id" qualificationTypes.
     *
     * @param id the id of the qualificationTypes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the qualificationTypes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/qualification-types/{id}")
    public ResponseEntity<QualificationTypes> getQualificationTypes(@PathVariable Long id) {
        log.debug("REST request to get QualificationTypes : {}", id);
        Optional<QualificationTypes> qualificationTypes = qualificationTypesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(qualificationTypes);
    }

    /**
     * {@code DELETE  /qualification-types/:id} : delete the "id" qualificationTypes.
     *
     * @param id the id of the qualificationTypes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/qualification-types/{id}")
    public ResponseEntity<Void> deleteQualificationTypes(@PathVariable Long id) {
        log.debug("REST request to delete QualificationTypes : {}", id);
        qualificationTypesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
