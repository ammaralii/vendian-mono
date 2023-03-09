package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.PcRatingsTrainings;
import com.venturedive.vendian_mono.repository.PcRatingsTrainingsRepository;
import com.venturedive.vendian_mono.service.PcRatingsTrainingsQueryService;
import com.venturedive.vendian_mono.service.PcRatingsTrainingsService;
import com.venturedive.vendian_mono.service.criteria.PcRatingsTrainingsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.PcRatingsTrainings}.
 */
@RestController
@RequestMapping("/api")
public class PcRatingsTrainingsResource {

    private final Logger log = LoggerFactory.getLogger(PcRatingsTrainingsResource.class);

    private static final String ENTITY_NAME = "pcRatingsTrainings";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PcRatingsTrainingsService pcRatingsTrainingsService;

    private final PcRatingsTrainingsRepository pcRatingsTrainingsRepository;

    private final PcRatingsTrainingsQueryService pcRatingsTrainingsQueryService;

    public PcRatingsTrainingsResource(
        PcRatingsTrainingsService pcRatingsTrainingsService,
        PcRatingsTrainingsRepository pcRatingsTrainingsRepository,
        PcRatingsTrainingsQueryService pcRatingsTrainingsQueryService
    ) {
        this.pcRatingsTrainingsService = pcRatingsTrainingsService;
        this.pcRatingsTrainingsRepository = pcRatingsTrainingsRepository;
        this.pcRatingsTrainingsQueryService = pcRatingsTrainingsQueryService;
    }

    /**
     * {@code POST  /pc-ratings-trainings} : Create a new pcRatingsTrainings.
     *
     * @param pcRatingsTrainings the pcRatingsTrainings to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pcRatingsTrainings, or with status {@code 400 (Bad Request)} if the pcRatingsTrainings has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pc-ratings-trainings")
    public ResponseEntity<PcRatingsTrainings> createPcRatingsTrainings(@Valid @RequestBody PcRatingsTrainings pcRatingsTrainings)
        throws URISyntaxException {
        log.debug("REST request to save PcRatingsTrainings : {}", pcRatingsTrainings);
        if (pcRatingsTrainings.getId() != null) {
            throw new BadRequestAlertException("A new pcRatingsTrainings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PcRatingsTrainings result = pcRatingsTrainingsService.save(pcRatingsTrainings);
        return ResponseEntity
            .created(new URI("/api/pc-ratings-trainings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pc-ratings-trainings/:id} : Updates an existing pcRatingsTrainings.
     *
     * @param id the id of the pcRatingsTrainings to save.
     * @param pcRatingsTrainings the pcRatingsTrainings to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pcRatingsTrainings,
     * or with status {@code 400 (Bad Request)} if the pcRatingsTrainings is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pcRatingsTrainings couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pc-ratings-trainings/{id}")
    public ResponseEntity<PcRatingsTrainings> updatePcRatingsTrainings(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PcRatingsTrainings pcRatingsTrainings
    ) throws URISyntaxException {
        log.debug("REST request to update PcRatingsTrainings : {}, {}", id, pcRatingsTrainings);
        if (pcRatingsTrainings.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pcRatingsTrainings.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pcRatingsTrainingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PcRatingsTrainings result = pcRatingsTrainingsService.update(pcRatingsTrainings);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pcRatingsTrainings.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pc-ratings-trainings/:id} : Partial updates given fields of an existing pcRatingsTrainings, field will ignore if it is null
     *
     * @param id the id of the pcRatingsTrainings to save.
     * @param pcRatingsTrainings the pcRatingsTrainings to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pcRatingsTrainings,
     * or with status {@code 400 (Bad Request)} if the pcRatingsTrainings is not valid,
     * or with status {@code 404 (Not Found)} if the pcRatingsTrainings is not found,
     * or with status {@code 500 (Internal Server Error)} if the pcRatingsTrainings couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pc-ratings-trainings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PcRatingsTrainings> partialUpdatePcRatingsTrainings(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PcRatingsTrainings pcRatingsTrainings
    ) throws URISyntaxException {
        log.debug("REST request to partial update PcRatingsTrainings partially : {}, {}", id, pcRatingsTrainings);
        if (pcRatingsTrainings.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pcRatingsTrainings.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pcRatingsTrainingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PcRatingsTrainings> result = pcRatingsTrainingsService.partialUpdate(pcRatingsTrainings);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pcRatingsTrainings.getId().toString())
        );
    }

    /**
     * {@code GET  /pc-ratings-trainings} : get all the pcRatingsTrainings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pcRatingsTrainings in body.
     */
    @GetMapping("/pc-ratings-trainings")
    public ResponseEntity<List<PcRatingsTrainings>> getAllPcRatingsTrainings(
        PcRatingsTrainingsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PcRatingsTrainings by criteria: {}", criteria);
        Page<PcRatingsTrainings> page = pcRatingsTrainingsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pc-ratings-trainings/count} : count all the pcRatingsTrainings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/pc-ratings-trainings/count")
    public ResponseEntity<Long> countPcRatingsTrainings(PcRatingsTrainingsCriteria criteria) {
        log.debug("REST request to count PcRatingsTrainings by criteria: {}", criteria);
        return ResponseEntity.ok().body(pcRatingsTrainingsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /pc-ratings-trainings/:id} : get the "id" pcRatingsTrainings.
     *
     * @param id the id of the pcRatingsTrainings to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pcRatingsTrainings, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pc-ratings-trainings/{id}")
    public ResponseEntity<PcRatingsTrainings> getPcRatingsTrainings(@PathVariable Long id) {
        log.debug("REST request to get PcRatingsTrainings : {}", id);
        Optional<PcRatingsTrainings> pcRatingsTrainings = pcRatingsTrainingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pcRatingsTrainings);
    }

    /**
     * {@code DELETE  /pc-ratings-trainings/:id} : delete the "id" pcRatingsTrainings.
     *
     * @param id the id of the pcRatingsTrainings to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pc-ratings-trainings/{id}")
    public ResponseEntity<Void> deletePcRatingsTrainings(@PathVariable Long id) {
        log.debug("REST request to delete PcRatingsTrainings : {}", id);
        pcRatingsTrainingsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
