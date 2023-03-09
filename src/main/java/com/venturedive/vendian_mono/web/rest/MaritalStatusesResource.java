package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.MaritalStatuses;
import com.venturedive.vendian_mono.repository.MaritalStatusesRepository;
import com.venturedive.vendian_mono.service.MaritalStatusesQueryService;
import com.venturedive.vendian_mono.service.MaritalStatusesService;
import com.venturedive.vendian_mono.service.criteria.MaritalStatusesCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.MaritalStatuses}.
 */
@RestController
@RequestMapping("/api")
public class MaritalStatusesResource {

    private final Logger log = LoggerFactory.getLogger(MaritalStatusesResource.class);

    private static final String ENTITY_NAME = "maritalStatuses";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MaritalStatusesService maritalStatusesService;

    private final MaritalStatusesRepository maritalStatusesRepository;

    private final MaritalStatusesQueryService maritalStatusesQueryService;

    public MaritalStatusesResource(
        MaritalStatusesService maritalStatusesService,
        MaritalStatusesRepository maritalStatusesRepository,
        MaritalStatusesQueryService maritalStatusesQueryService
    ) {
        this.maritalStatusesService = maritalStatusesService;
        this.maritalStatusesRepository = maritalStatusesRepository;
        this.maritalStatusesQueryService = maritalStatusesQueryService;
    }

    /**
     * {@code POST  /marital-statuses} : Create a new maritalStatuses.
     *
     * @param maritalStatuses the maritalStatuses to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new maritalStatuses, or with status {@code 400 (Bad Request)} if the maritalStatuses has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/marital-statuses")
    public ResponseEntity<MaritalStatuses> createMaritalStatuses(@Valid @RequestBody MaritalStatuses maritalStatuses)
        throws URISyntaxException {
        log.debug("REST request to save MaritalStatuses : {}", maritalStatuses);
        if (maritalStatuses.getId() != null) {
            throw new BadRequestAlertException("A new maritalStatuses cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MaritalStatuses result = maritalStatusesService.save(maritalStatuses);
        return ResponseEntity
            .created(new URI("/api/marital-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /marital-statuses/:id} : Updates an existing maritalStatuses.
     *
     * @param id the id of the maritalStatuses to save.
     * @param maritalStatuses the maritalStatuses to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated maritalStatuses,
     * or with status {@code 400 (Bad Request)} if the maritalStatuses is not valid,
     * or with status {@code 500 (Internal Server Error)} if the maritalStatuses couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/marital-statuses/{id}")
    public ResponseEntity<MaritalStatuses> updateMaritalStatuses(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MaritalStatuses maritalStatuses
    ) throws URISyntaxException {
        log.debug("REST request to update MaritalStatuses : {}, {}", id, maritalStatuses);
        if (maritalStatuses.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, maritalStatuses.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!maritalStatusesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MaritalStatuses result = maritalStatusesService.update(maritalStatuses);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, maritalStatuses.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /marital-statuses/:id} : Partial updates given fields of an existing maritalStatuses, field will ignore if it is null
     *
     * @param id the id of the maritalStatuses to save.
     * @param maritalStatuses the maritalStatuses to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated maritalStatuses,
     * or with status {@code 400 (Bad Request)} if the maritalStatuses is not valid,
     * or with status {@code 404 (Not Found)} if the maritalStatuses is not found,
     * or with status {@code 500 (Internal Server Error)} if the maritalStatuses couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/marital-statuses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MaritalStatuses> partialUpdateMaritalStatuses(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MaritalStatuses maritalStatuses
    ) throws URISyntaxException {
        log.debug("REST request to partial update MaritalStatuses partially : {}, {}", id, maritalStatuses);
        if (maritalStatuses.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, maritalStatuses.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!maritalStatusesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MaritalStatuses> result = maritalStatusesService.partialUpdate(maritalStatuses);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, maritalStatuses.getId().toString())
        );
    }

    /**
     * {@code GET  /marital-statuses} : get all the maritalStatuses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of maritalStatuses in body.
     */
    @GetMapping("/marital-statuses")
    public ResponseEntity<List<MaritalStatuses>> getAllMaritalStatuses(
        MaritalStatusesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get MaritalStatuses by criteria: {}", criteria);
        Page<MaritalStatuses> page = maritalStatusesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /marital-statuses/count} : count all the maritalStatuses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/marital-statuses/count")
    public ResponseEntity<Long> countMaritalStatuses(MaritalStatusesCriteria criteria) {
        log.debug("REST request to count MaritalStatuses by criteria: {}", criteria);
        return ResponseEntity.ok().body(maritalStatusesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /marital-statuses/:id} : get the "id" maritalStatuses.
     *
     * @param id the id of the maritalStatuses to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the maritalStatuses, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/marital-statuses/{id}")
    public ResponseEntity<MaritalStatuses> getMaritalStatuses(@PathVariable Long id) {
        log.debug("REST request to get MaritalStatuses : {}", id);
        Optional<MaritalStatuses> maritalStatuses = maritalStatusesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(maritalStatuses);
    }

    /**
     * {@code DELETE  /marital-statuses/:id} : delete the "id" maritalStatuses.
     *
     * @param id the id of the maritalStatuses to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/marital-statuses/{id}")
    public ResponseEntity<Void> deleteMaritalStatuses(@PathVariable Long id) {
        log.debug("REST request to delete MaritalStatuses : {}", id);
        maritalStatusesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
