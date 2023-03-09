package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.Divisions;
import com.venturedive.vendian_mono.repository.DivisionsRepository;
import com.venturedive.vendian_mono.service.DivisionsQueryService;
import com.venturedive.vendian_mono.service.DivisionsService;
import com.venturedive.vendian_mono.service.criteria.DivisionsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.Divisions}.
 */
@RestController
@RequestMapping("/api")
public class DivisionsResource {

    private final Logger log = LoggerFactory.getLogger(DivisionsResource.class);

    private static final String ENTITY_NAME = "divisions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DivisionsService divisionsService;

    private final DivisionsRepository divisionsRepository;

    private final DivisionsQueryService divisionsQueryService;

    public DivisionsResource(
        DivisionsService divisionsService,
        DivisionsRepository divisionsRepository,
        DivisionsQueryService divisionsQueryService
    ) {
        this.divisionsService = divisionsService;
        this.divisionsRepository = divisionsRepository;
        this.divisionsQueryService = divisionsQueryService;
    }

    /**
     * {@code POST  /divisions} : Create a new divisions.
     *
     * @param divisions the divisions to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new divisions, or with status {@code 400 (Bad Request)} if the divisions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/divisions")
    public ResponseEntity<Divisions> createDivisions(@Valid @RequestBody Divisions divisions) throws URISyntaxException {
        log.debug("REST request to save Divisions : {}", divisions);
        if (divisions.getId() != null) {
            throw new BadRequestAlertException("A new divisions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Divisions result = divisionsService.save(divisions);
        return ResponseEntity
            .created(new URI("/api/divisions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /divisions/:id} : Updates an existing divisions.
     *
     * @param id the id of the divisions to save.
     * @param divisions the divisions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated divisions,
     * or with status {@code 400 (Bad Request)} if the divisions is not valid,
     * or with status {@code 500 (Internal Server Error)} if the divisions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/divisions/{id}")
    public ResponseEntity<Divisions> updateDivisions(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Divisions divisions
    ) throws URISyntaxException {
        log.debug("REST request to update Divisions : {}, {}", id, divisions);
        if (divisions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, divisions.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!divisionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Divisions result = divisionsService.update(divisions);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, divisions.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /divisions/:id} : Partial updates given fields of an existing divisions, field will ignore if it is null
     *
     * @param id the id of the divisions to save.
     * @param divisions the divisions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated divisions,
     * or with status {@code 400 (Bad Request)} if the divisions is not valid,
     * or with status {@code 404 (Not Found)} if the divisions is not found,
     * or with status {@code 500 (Internal Server Error)} if the divisions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/divisions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Divisions> partialUpdateDivisions(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Divisions divisions
    ) throws URISyntaxException {
        log.debug("REST request to partial update Divisions partially : {}, {}", id, divisions);
        if (divisions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, divisions.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!divisionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Divisions> result = divisionsService.partialUpdate(divisions);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, divisions.getId().toString())
        );
    }

    /**
     * {@code GET  /divisions} : get all the divisions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of divisions in body.
     */
    @GetMapping("/divisions")
    public ResponseEntity<List<Divisions>> getAllDivisions(
        DivisionsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Divisions by criteria: {}", criteria);
        Page<Divisions> page = divisionsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /divisions/count} : count all the divisions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/divisions/count")
    public ResponseEntity<Long> countDivisions(DivisionsCriteria criteria) {
        log.debug("REST request to count Divisions by criteria: {}", criteria);
        return ResponseEntity.ok().body(divisionsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /divisions/:id} : get the "id" divisions.
     *
     * @param id the id of the divisions to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the divisions, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/divisions/{id}")
    public ResponseEntity<Divisions> getDivisions(@PathVariable Long id) {
        log.debug("REST request to get Divisions : {}", id);
        Optional<Divisions> divisions = divisionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(divisions);
    }

    /**
     * {@code DELETE  /divisions/:id} : delete the "id" divisions.
     *
     * @param id the id of the divisions to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/divisions/{id}")
    public ResponseEntity<Void> deleteDivisions(@PathVariable Long id) {
        log.debug("REST request to delete Divisions : {}", id);
        divisionsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
