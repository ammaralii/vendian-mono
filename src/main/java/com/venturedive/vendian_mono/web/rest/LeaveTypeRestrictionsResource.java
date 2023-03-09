package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.LeaveTypeRestrictions;
import com.venturedive.vendian_mono.repository.LeaveTypeRestrictionsRepository;
import com.venturedive.vendian_mono.service.LeaveTypeRestrictionsQueryService;
import com.venturedive.vendian_mono.service.LeaveTypeRestrictionsService;
import com.venturedive.vendian_mono.service.criteria.LeaveTypeRestrictionsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.LeaveTypeRestrictions}.
 */
@RestController
@RequestMapping("/api")
public class LeaveTypeRestrictionsResource {

    private final Logger log = LoggerFactory.getLogger(LeaveTypeRestrictionsResource.class);

    private static final String ENTITY_NAME = "leaveTypeRestrictions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeaveTypeRestrictionsService leaveTypeRestrictionsService;

    private final LeaveTypeRestrictionsRepository leaveTypeRestrictionsRepository;

    private final LeaveTypeRestrictionsQueryService leaveTypeRestrictionsQueryService;

    public LeaveTypeRestrictionsResource(
        LeaveTypeRestrictionsService leaveTypeRestrictionsService,
        LeaveTypeRestrictionsRepository leaveTypeRestrictionsRepository,
        LeaveTypeRestrictionsQueryService leaveTypeRestrictionsQueryService
    ) {
        this.leaveTypeRestrictionsService = leaveTypeRestrictionsService;
        this.leaveTypeRestrictionsRepository = leaveTypeRestrictionsRepository;
        this.leaveTypeRestrictionsQueryService = leaveTypeRestrictionsQueryService;
    }

    /**
     * {@code POST  /leave-type-restrictions} : Create a new leaveTypeRestrictions.
     *
     * @param leaveTypeRestrictions the leaveTypeRestrictions to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leaveTypeRestrictions, or with status {@code 400 (Bad Request)} if the leaveTypeRestrictions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/leave-type-restrictions")
    public ResponseEntity<LeaveTypeRestrictions> createLeaveTypeRestrictions(
        @Valid @RequestBody LeaveTypeRestrictions leaveTypeRestrictions
    ) throws URISyntaxException {
        log.debug("REST request to save LeaveTypeRestrictions : {}", leaveTypeRestrictions);
        if (leaveTypeRestrictions.getId() != null) {
            throw new BadRequestAlertException("A new leaveTypeRestrictions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeaveTypeRestrictions result = leaveTypeRestrictionsService.save(leaveTypeRestrictions);
        return ResponseEntity
            .created(new URI("/api/leave-type-restrictions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /leave-type-restrictions/:id} : Updates an existing leaveTypeRestrictions.
     *
     * @param id the id of the leaveTypeRestrictions to save.
     * @param leaveTypeRestrictions the leaveTypeRestrictions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveTypeRestrictions,
     * or with status {@code 400 (Bad Request)} if the leaveTypeRestrictions is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leaveTypeRestrictions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/leave-type-restrictions/{id}")
    public ResponseEntity<LeaveTypeRestrictions> updateLeaveTypeRestrictions(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LeaveTypeRestrictions leaveTypeRestrictions
    ) throws URISyntaxException {
        log.debug("REST request to update LeaveTypeRestrictions : {}, {}", id, leaveTypeRestrictions);
        if (leaveTypeRestrictions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveTypeRestrictions.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveTypeRestrictionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LeaveTypeRestrictions result = leaveTypeRestrictionsService.update(leaveTypeRestrictions);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveTypeRestrictions.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /leave-type-restrictions/:id} : Partial updates given fields of an existing leaveTypeRestrictions, field will ignore if it is null
     *
     * @param id the id of the leaveTypeRestrictions to save.
     * @param leaveTypeRestrictions the leaveTypeRestrictions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveTypeRestrictions,
     * or with status {@code 400 (Bad Request)} if the leaveTypeRestrictions is not valid,
     * or with status {@code 404 (Not Found)} if the leaveTypeRestrictions is not found,
     * or with status {@code 500 (Internal Server Error)} if the leaveTypeRestrictions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/leave-type-restrictions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LeaveTypeRestrictions> partialUpdateLeaveTypeRestrictions(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LeaveTypeRestrictions leaveTypeRestrictions
    ) throws URISyntaxException {
        log.debug("REST request to partial update LeaveTypeRestrictions partially : {}, {}", id, leaveTypeRestrictions);
        if (leaveTypeRestrictions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveTypeRestrictions.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveTypeRestrictionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LeaveTypeRestrictions> result = leaveTypeRestrictionsService.partialUpdate(leaveTypeRestrictions);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveTypeRestrictions.getId().toString())
        );
    }

    /**
     * {@code GET  /leave-type-restrictions} : get all the leaveTypeRestrictions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leaveTypeRestrictions in body.
     */
    @GetMapping("/leave-type-restrictions")
    public ResponseEntity<List<LeaveTypeRestrictions>> getAllLeaveTypeRestrictions(
        LeaveTypeRestrictionsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LeaveTypeRestrictions by criteria: {}", criteria);
        Page<LeaveTypeRestrictions> page = leaveTypeRestrictionsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /leave-type-restrictions/count} : count all the leaveTypeRestrictions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/leave-type-restrictions/count")
    public ResponseEntity<Long> countLeaveTypeRestrictions(LeaveTypeRestrictionsCriteria criteria) {
        log.debug("REST request to count LeaveTypeRestrictions by criteria: {}", criteria);
        return ResponseEntity.ok().body(leaveTypeRestrictionsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /leave-type-restrictions/:id} : get the "id" leaveTypeRestrictions.
     *
     * @param id the id of the leaveTypeRestrictions to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leaveTypeRestrictions, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/leave-type-restrictions/{id}")
    public ResponseEntity<LeaveTypeRestrictions> getLeaveTypeRestrictions(@PathVariable Long id) {
        log.debug("REST request to get LeaveTypeRestrictions : {}", id);
        Optional<LeaveTypeRestrictions> leaveTypeRestrictions = leaveTypeRestrictionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leaveTypeRestrictions);
    }

    /**
     * {@code DELETE  /leave-type-restrictions/:id} : delete the "id" leaveTypeRestrictions.
     *
     * @param id the id of the leaveTypeRestrictions to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/leave-type-restrictions/{id}")
    public ResponseEntity<Void> deleteLeaveTypeRestrictions(@PathVariable Long id) {
        log.debug("REST request to delete LeaveTypeRestrictions : {}", id);
        leaveTypeRestrictionsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
