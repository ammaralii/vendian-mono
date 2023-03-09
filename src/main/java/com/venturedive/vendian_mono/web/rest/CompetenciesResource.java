package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.Competencies;
import com.venturedive.vendian_mono.repository.CompetenciesRepository;
import com.venturedive.vendian_mono.service.CompetenciesQueryService;
import com.venturedive.vendian_mono.service.CompetenciesService;
import com.venturedive.vendian_mono.service.criteria.CompetenciesCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.Competencies}.
 */
@RestController
@RequestMapping("/api")
public class CompetenciesResource {

    private final Logger log = LoggerFactory.getLogger(CompetenciesResource.class);

    private static final String ENTITY_NAME = "competencies";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompetenciesService competenciesService;

    private final CompetenciesRepository competenciesRepository;

    private final CompetenciesQueryService competenciesQueryService;

    public CompetenciesResource(
        CompetenciesService competenciesService,
        CompetenciesRepository competenciesRepository,
        CompetenciesQueryService competenciesQueryService
    ) {
        this.competenciesService = competenciesService;
        this.competenciesRepository = competenciesRepository;
        this.competenciesQueryService = competenciesQueryService;
    }

    /**
     * {@code POST  /competencies} : Create a new competencies.
     *
     * @param competencies the competencies to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new competencies, or with status {@code 400 (Bad Request)} if the competencies has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/competencies")
    public ResponseEntity<Competencies> createCompetencies(@Valid @RequestBody Competencies competencies) throws URISyntaxException {
        log.debug("REST request to save Competencies : {}", competencies);
        if (competencies.getId() != null) {
            throw new BadRequestAlertException("A new competencies cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Competencies result = competenciesService.save(competencies);
        return ResponseEntity
            .created(new URI("/api/competencies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /competencies/:id} : Updates an existing competencies.
     *
     * @param id the id of the competencies to save.
     * @param competencies the competencies to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competencies,
     * or with status {@code 400 (Bad Request)} if the competencies is not valid,
     * or with status {@code 500 (Internal Server Error)} if the competencies couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/competencies/{id}")
    public ResponseEntity<Competencies> updateCompetencies(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Competencies competencies
    ) throws URISyntaxException {
        log.debug("REST request to update Competencies : {}, {}", id, competencies);
        if (competencies.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, competencies.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!competenciesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Competencies result = competenciesService.update(competencies);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, competencies.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /competencies/:id} : Partial updates given fields of an existing competencies, field will ignore if it is null
     *
     * @param id the id of the competencies to save.
     * @param competencies the competencies to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competencies,
     * or with status {@code 400 (Bad Request)} if the competencies is not valid,
     * or with status {@code 404 (Not Found)} if the competencies is not found,
     * or with status {@code 500 (Internal Server Error)} if the competencies couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/competencies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Competencies> partialUpdateCompetencies(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Competencies competencies
    ) throws URISyntaxException {
        log.debug("REST request to partial update Competencies partially : {}, {}", id, competencies);
        if (competencies.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, competencies.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!competenciesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Competencies> result = competenciesService.partialUpdate(competencies);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, competencies.getId().toString())
        );
    }

    /**
     * {@code GET  /competencies} : get all the competencies.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of competencies in body.
     */
    @GetMapping("/competencies")
    public ResponseEntity<List<Competencies>> getAllCompetencies(
        CompetenciesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Competencies by criteria: {}", criteria);
        Page<Competencies> page = competenciesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /competencies/count} : count all the competencies.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/competencies/count")
    public ResponseEntity<Long> countCompetencies(CompetenciesCriteria criteria) {
        log.debug("REST request to count Competencies by criteria: {}", criteria);
        return ResponseEntity.ok().body(competenciesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /competencies/:id} : get the "id" competencies.
     *
     * @param id the id of the competencies to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the competencies, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/competencies/{id}")
    public ResponseEntity<Competencies> getCompetencies(@PathVariable Long id) {
        log.debug("REST request to get Competencies : {}", id);
        Optional<Competencies> competencies = competenciesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(competencies);
    }

    /**
     * {@code DELETE  /competencies/:id} : delete the "id" competencies.
     *
     * @param id the id of the competencies to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/competencies/{id}")
    public ResponseEntity<Void> deleteCompetencies(@PathVariable Long id) {
        log.debug("REST request to delete Competencies : {}", id);
        competenciesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
