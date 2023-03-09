package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.PcStatuses;
import com.venturedive.vendian_mono.repository.PcStatusesRepository;
import com.venturedive.vendian_mono.service.PcStatusesQueryService;
import com.venturedive.vendian_mono.service.PcStatusesService;
import com.venturedive.vendian_mono.service.criteria.PcStatusesCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.PcStatuses}.
 */
@RestController
@RequestMapping("/api")
public class PcStatusesResource {

    private final Logger log = LoggerFactory.getLogger(PcStatusesResource.class);

    private static final String ENTITY_NAME = "pcStatuses";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PcStatusesService pcStatusesService;

    private final PcStatusesRepository pcStatusesRepository;

    private final PcStatusesQueryService pcStatusesQueryService;

    public PcStatusesResource(
        PcStatusesService pcStatusesService,
        PcStatusesRepository pcStatusesRepository,
        PcStatusesQueryService pcStatusesQueryService
    ) {
        this.pcStatusesService = pcStatusesService;
        this.pcStatusesRepository = pcStatusesRepository;
        this.pcStatusesQueryService = pcStatusesQueryService;
    }

    /**
     * {@code POST  /pc-statuses} : Create a new pcStatuses.
     *
     * @param pcStatuses the pcStatuses to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pcStatuses, or with status {@code 400 (Bad Request)} if the pcStatuses has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pc-statuses")
    public ResponseEntity<PcStatuses> createPcStatuses(@Valid @RequestBody PcStatuses pcStatuses) throws URISyntaxException {
        log.debug("REST request to save PcStatuses : {}", pcStatuses);
        if (pcStatuses.getId() != null) {
            throw new BadRequestAlertException("A new pcStatuses cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PcStatuses result = pcStatusesService.save(pcStatuses);
        return ResponseEntity
            .created(new URI("/api/pc-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pc-statuses/:id} : Updates an existing pcStatuses.
     *
     * @param id the id of the pcStatuses to save.
     * @param pcStatuses the pcStatuses to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pcStatuses,
     * or with status {@code 400 (Bad Request)} if the pcStatuses is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pcStatuses couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pc-statuses/{id}")
    public ResponseEntity<PcStatuses> updatePcStatuses(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PcStatuses pcStatuses
    ) throws URISyntaxException {
        log.debug("REST request to update PcStatuses : {}, {}", id, pcStatuses);
        if (pcStatuses.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pcStatuses.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pcStatusesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PcStatuses result = pcStatusesService.update(pcStatuses);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pcStatuses.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pc-statuses/:id} : Partial updates given fields of an existing pcStatuses, field will ignore if it is null
     *
     * @param id the id of the pcStatuses to save.
     * @param pcStatuses the pcStatuses to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pcStatuses,
     * or with status {@code 400 (Bad Request)} if the pcStatuses is not valid,
     * or with status {@code 404 (Not Found)} if the pcStatuses is not found,
     * or with status {@code 500 (Internal Server Error)} if the pcStatuses couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pc-statuses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PcStatuses> partialUpdatePcStatuses(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PcStatuses pcStatuses
    ) throws URISyntaxException {
        log.debug("REST request to partial update PcStatuses partially : {}, {}", id, pcStatuses);
        if (pcStatuses.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pcStatuses.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pcStatusesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PcStatuses> result = pcStatusesService.partialUpdate(pcStatuses);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pcStatuses.getId().toString())
        );
    }

    /**
     * {@code GET  /pc-statuses} : get all the pcStatuses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pcStatuses in body.
     */
    @GetMapping("/pc-statuses")
    public ResponseEntity<List<PcStatuses>> getAllPcStatuses(
        PcStatusesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PcStatuses by criteria: {}", criteria);
        Page<PcStatuses> page = pcStatusesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pc-statuses/count} : count all the pcStatuses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/pc-statuses/count")
    public ResponseEntity<Long> countPcStatuses(PcStatusesCriteria criteria) {
        log.debug("REST request to count PcStatuses by criteria: {}", criteria);
        return ResponseEntity.ok().body(pcStatusesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /pc-statuses/:id} : get the "id" pcStatuses.
     *
     * @param id the id of the pcStatuses to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pcStatuses, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pc-statuses/{id}")
    public ResponseEntity<PcStatuses> getPcStatuses(@PathVariable Long id) {
        log.debug("REST request to get PcStatuses : {}", id);
        Optional<PcStatuses> pcStatuses = pcStatusesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pcStatuses);
    }

    /**
     * {@code DELETE  /pc-statuses/:id} : delete the "id" pcStatuses.
     *
     * @param id the id of the pcStatuses to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pc-statuses/{id}")
    public ResponseEntity<Void> deletePcStatuses(@PathVariable Long id) {
        log.debug("REST request to delete PcStatuses : {}", id);
        pcStatusesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
