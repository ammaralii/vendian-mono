package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.LeaveTypeConfigurations;
import com.venturedive.vendian_mono.repository.LeaveTypeConfigurationsRepository;
import com.venturedive.vendian_mono.service.LeaveTypeConfigurationsQueryService;
import com.venturedive.vendian_mono.service.LeaveTypeConfigurationsService;
import com.venturedive.vendian_mono.service.criteria.LeaveTypeConfigurationsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.LeaveTypeConfigurations}.
 */
@RestController
@RequestMapping("/api")
public class LeaveTypeConfigurationsResource {

    private final Logger log = LoggerFactory.getLogger(LeaveTypeConfigurationsResource.class);

    private static final String ENTITY_NAME = "leaveTypeConfigurations";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeaveTypeConfigurationsService leaveTypeConfigurationsService;

    private final LeaveTypeConfigurationsRepository leaveTypeConfigurationsRepository;

    private final LeaveTypeConfigurationsQueryService leaveTypeConfigurationsQueryService;

    public LeaveTypeConfigurationsResource(
        LeaveTypeConfigurationsService leaveTypeConfigurationsService,
        LeaveTypeConfigurationsRepository leaveTypeConfigurationsRepository,
        LeaveTypeConfigurationsQueryService leaveTypeConfigurationsQueryService
    ) {
        this.leaveTypeConfigurationsService = leaveTypeConfigurationsService;
        this.leaveTypeConfigurationsRepository = leaveTypeConfigurationsRepository;
        this.leaveTypeConfigurationsQueryService = leaveTypeConfigurationsQueryService;
    }

    /**
     * {@code POST  /leave-type-configurations} : Create a new leaveTypeConfigurations.
     *
     * @param leaveTypeConfigurations the leaveTypeConfigurations to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leaveTypeConfigurations, or with status {@code 400 (Bad Request)} if the leaveTypeConfigurations has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/leave-type-configurations")
    public ResponseEntity<LeaveTypeConfigurations> createLeaveTypeConfigurations(
        @Valid @RequestBody LeaveTypeConfigurations leaveTypeConfigurations
    ) throws URISyntaxException {
        log.debug("REST request to save LeaveTypeConfigurations : {}", leaveTypeConfigurations);
        if (leaveTypeConfigurations.getId() != null) {
            throw new BadRequestAlertException("A new leaveTypeConfigurations cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeaveTypeConfigurations result = leaveTypeConfigurationsService.save(leaveTypeConfigurations);
        return ResponseEntity
            .created(new URI("/api/leave-type-configurations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /leave-type-configurations/:id} : Updates an existing leaveTypeConfigurations.
     *
     * @param id the id of the leaveTypeConfigurations to save.
     * @param leaveTypeConfigurations the leaveTypeConfigurations to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveTypeConfigurations,
     * or with status {@code 400 (Bad Request)} if the leaveTypeConfigurations is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leaveTypeConfigurations couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/leave-type-configurations/{id}")
    public ResponseEntity<LeaveTypeConfigurations> updateLeaveTypeConfigurations(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LeaveTypeConfigurations leaveTypeConfigurations
    ) throws URISyntaxException {
        log.debug("REST request to update LeaveTypeConfigurations : {}, {}", id, leaveTypeConfigurations);
        if (leaveTypeConfigurations.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveTypeConfigurations.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveTypeConfigurationsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LeaveTypeConfigurations result = leaveTypeConfigurationsService.update(leaveTypeConfigurations);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveTypeConfigurations.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /leave-type-configurations/:id} : Partial updates given fields of an existing leaveTypeConfigurations, field will ignore if it is null
     *
     * @param id the id of the leaveTypeConfigurations to save.
     * @param leaveTypeConfigurations the leaveTypeConfigurations to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveTypeConfigurations,
     * or with status {@code 400 (Bad Request)} if the leaveTypeConfigurations is not valid,
     * or with status {@code 404 (Not Found)} if the leaveTypeConfigurations is not found,
     * or with status {@code 500 (Internal Server Error)} if the leaveTypeConfigurations couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/leave-type-configurations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LeaveTypeConfigurations> partialUpdateLeaveTypeConfigurations(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LeaveTypeConfigurations leaveTypeConfigurations
    ) throws URISyntaxException {
        log.debug("REST request to partial update LeaveTypeConfigurations partially : {}, {}", id, leaveTypeConfigurations);
        if (leaveTypeConfigurations.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveTypeConfigurations.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveTypeConfigurationsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LeaveTypeConfigurations> result = leaveTypeConfigurationsService.partialUpdate(leaveTypeConfigurations);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveTypeConfigurations.getId().toString())
        );
    }

    /**
     * {@code GET  /leave-type-configurations} : get all the leaveTypeConfigurations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leaveTypeConfigurations in body.
     */
    @GetMapping("/leave-type-configurations")
    public ResponseEntity<List<LeaveTypeConfigurations>> getAllLeaveTypeConfigurations(
        LeaveTypeConfigurationsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LeaveTypeConfigurations by criteria: {}", criteria);
        Page<LeaveTypeConfigurations> page = leaveTypeConfigurationsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /leave-type-configurations/count} : count all the leaveTypeConfigurations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/leave-type-configurations/count")
    public ResponseEntity<Long> countLeaveTypeConfigurations(LeaveTypeConfigurationsCriteria criteria) {
        log.debug("REST request to count LeaveTypeConfigurations by criteria: {}", criteria);
        return ResponseEntity.ok().body(leaveTypeConfigurationsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /leave-type-configurations/:id} : get the "id" leaveTypeConfigurations.
     *
     * @param id the id of the leaveTypeConfigurations to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leaveTypeConfigurations, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/leave-type-configurations/{id}")
    public ResponseEntity<LeaveTypeConfigurations> getLeaveTypeConfigurations(@PathVariable Long id) {
        log.debug("REST request to get LeaveTypeConfigurations : {}", id);
        Optional<LeaveTypeConfigurations> leaveTypeConfigurations = leaveTypeConfigurationsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leaveTypeConfigurations);
    }

    /**
     * {@code DELETE  /leave-type-configurations/:id} : delete the "id" leaveTypeConfigurations.
     *
     * @param id the id of the leaveTypeConfigurations to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/leave-type-configurations/{id}")
    public ResponseEntity<Void> deleteLeaveTypeConfigurations(@PathVariable Long id) {
        log.debug("REST request to delete LeaveTypeConfigurations : {}", id);
        leaveTypeConfigurationsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
