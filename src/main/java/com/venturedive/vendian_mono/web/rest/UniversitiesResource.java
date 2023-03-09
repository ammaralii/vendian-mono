package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.Universities;
import com.venturedive.vendian_mono.repository.UniversitiesRepository;
import com.venturedive.vendian_mono.service.UniversitiesQueryService;
import com.venturedive.vendian_mono.service.UniversitiesService;
import com.venturedive.vendian_mono.service.criteria.UniversitiesCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.Universities}.
 */
@RestController
@RequestMapping("/api")
public class UniversitiesResource {

    private final Logger log = LoggerFactory.getLogger(UniversitiesResource.class);

    private static final String ENTITY_NAME = "universities";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UniversitiesService universitiesService;

    private final UniversitiesRepository universitiesRepository;

    private final UniversitiesQueryService universitiesQueryService;

    public UniversitiesResource(
        UniversitiesService universitiesService,
        UniversitiesRepository universitiesRepository,
        UniversitiesQueryService universitiesQueryService
    ) {
        this.universitiesService = universitiesService;
        this.universitiesRepository = universitiesRepository;
        this.universitiesQueryService = universitiesQueryService;
    }

    /**
     * {@code POST  /universities} : Create a new universities.
     *
     * @param universities the universities to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new universities, or with status {@code 400 (Bad Request)} if the universities has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/universities")
    public ResponseEntity<Universities> createUniversities(@Valid @RequestBody Universities universities) throws URISyntaxException {
        log.debug("REST request to save Universities : {}", universities);
        if (universities.getId() != null) {
            throw new BadRequestAlertException("A new universities cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Universities result = universitiesService.save(universities);
        return ResponseEntity
            .created(new URI("/api/universities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /universities/:id} : Updates an existing universities.
     *
     * @param id the id of the universities to save.
     * @param universities the universities to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated universities,
     * or with status {@code 400 (Bad Request)} if the universities is not valid,
     * or with status {@code 500 (Internal Server Error)} if the universities couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/universities/{id}")
    public ResponseEntity<Universities> updateUniversities(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Universities universities
    ) throws URISyntaxException {
        log.debug("REST request to update Universities : {}, {}", id, universities);
        if (universities.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, universities.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!universitiesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Universities result = universitiesService.update(universities);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, universities.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /universities/:id} : Partial updates given fields of an existing universities, field will ignore if it is null
     *
     * @param id the id of the universities to save.
     * @param universities the universities to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated universities,
     * or with status {@code 400 (Bad Request)} if the universities is not valid,
     * or with status {@code 404 (Not Found)} if the universities is not found,
     * or with status {@code 500 (Internal Server Error)} if the universities couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/universities/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Universities> partialUpdateUniversities(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Universities universities
    ) throws URISyntaxException {
        log.debug("REST request to partial update Universities partially : {}, {}", id, universities);
        if (universities.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, universities.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!universitiesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Universities> result = universitiesService.partialUpdate(universities);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, universities.getId().toString())
        );
    }

    /**
     * {@code GET  /universities} : get all the universities.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of universities in body.
     */
    @GetMapping("/universities")
    public ResponseEntity<List<Universities>> getAllUniversities(
        UniversitiesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Universities by criteria: {}", criteria);
        Page<Universities> page = universitiesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /universities/count} : count all the universities.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/universities/count")
    public ResponseEntity<Long> countUniversities(UniversitiesCriteria criteria) {
        log.debug("REST request to count Universities by criteria: {}", criteria);
        return ResponseEntity.ok().body(universitiesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /universities/:id} : get the "id" universities.
     *
     * @param id the id of the universities to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the universities, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/universities/{id}")
    public ResponseEntity<Universities> getUniversities(@PathVariable Long id) {
        log.debug("REST request to get Universities : {}", id);
        Optional<Universities> universities = universitiesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(universities);
    }

    /**
     * {@code DELETE  /universities/:id} : delete the "id" universities.
     *
     * @param id the id of the universities to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/universities/{id}")
    public ResponseEntity<Void> deleteUniversities(@PathVariable Long id) {
        log.debug("REST request to delete Universities : {}", id);
        universitiesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
