package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.LeaveStatuses;
import com.venturedive.vendian_mono.repository.LeaveStatusesRepository;
import com.venturedive.vendian_mono.service.LeaveStatusesQueryService;
import com.venturedive.vendian_mono.service.LeaveStatusesService;
import com.venturedive.vendian_mono.service.criteria.LeaveStatusesCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.LeaveStatuses}.
 */
@RestController
@RequestMapping("/api")
public class LeaveStatusesResource {

    private final Logger log = LoggerFactory.getLogger(LeaveStatusesResource.class);

    private static final String ENTITY_NAME = "leaveStatuses";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeaveStatusesService leaveStatusesService;

    private final LeaveStatusesRepository leaveStatusesRepository;

    private final LeaveStatusesQueryService leaveStatusesQueryService;

    public LeaveStatusesResource(
        LeaveStatusesService leaveStatusesService,
        LeaveStatusesRepository leaveStatusesRepository,
        LeaveStatusesQueryService leaveStatusesQueryService
    ) {
        this.leaveStatusesService = leaveStatusesService;
        this.leaveStatusesRepository = leaveStatusesRepository;
        this.leaveStatusesQueryService = leaveStatusesQueryService;
    }

    /**
     * {@code POST  /leave-statuses} : Create a new leaveStatuses.
     *
     * @param leaveStatuses the leaveStatuses to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leaveStatuses, or with status {@code 400 (Bad Request)} if the leaveStatuses has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/leave-statuses")
    public ResponseEntity<LeaveStatuses> createLeaveStatuses(@Valid @RequestBody LeaveStatuses leaveStatuses) throws URISyntaxException {
        log.debug("REST request to save LeaveStatuses : {}", leaveStatuses);
        if (leaveStatuses.getId() != null) {
            throw new BadRequestAlertException("A new leaveStatuses cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeaveStatuses result = leaveStatusesService.save(leaveStatuses);
        return ResponseEntity
            .created(new URI("/api/leave-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /leave-statuses/:id} : Updates an existing leaveStatuses.
     *
     * @param id the id of the leaveStatuses to save.
     * @param leaveStatuses the leaveStatuses to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveStatuses,
     * or with status {@code 400 (Bad Request)} if the leaveStatuses is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leaveStatuses couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/leave-statuses/{id}")
    public ResponseEntity<LeaveStatuses> updateLeaveStatuses(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LeaveStatuses leaveStatuses
    ) throws URISyntaxException {
        log.debug("REST request to update LeaveStatuses : {}, {}", id, leaveStatuses);
        if (leaveStatuses.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveStatuses.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveStatusesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LeaveStatuses result = leaveStatusesService.update(leaveStatuses);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveStatuses.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /leave-statuses/:id} : Partial updates given fields of an existing leaveStatuses, field will ignore if it is null
     *
     * @param id the id of the leaveStatuses to save.
     * @param leaveStatuses the leaveStatuses to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveStatuses,
     * or with status {@code 400 (Bad Request)} if the leaveStatuses is not valid,
     * or with status {@code 404 (Not Found)} if the leaveStatuses is not found,
     * or with status {@code 500 (Internal Server Error)} if the leaveStatuses couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/leave-statuses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LeaveStatuses> partialUpdateLeaveStatuses(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LeaveStatuses leaveStatuses
    ) throws URISyntaxException {
        log.debug("REST request to partial update LeaveStatuses partially : {}, {}", id, leaveStatuses);
        if (leaveStatuses.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveStatuses.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveStatusesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LeaveStatuses> result = leaveStatusesService.partialUpdate(leaveStatuses);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveStatuses.getId().toString())
        );
    }

    /**
     * {@code GET  /leave-statuses} : get all the leaveStatuses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leaveStatuses in body.
     */
    @GetMapping("/leave-statuses")
    public ResponseEntity<List<LeaveStatuses>> getAllLeaveStatuses(
        LeaveStatusesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LeaveStatuses by criteria: {}", criteria);
        Page<LeaveStatuses> page = leaveStatusesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /leave-statuses/count} : count all the leaveStatuses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/leave-statuses/count")
    public ResponseEntity<Long> countLeaveStatuses(LeaveStatusesCriteria criteria) {
        log.debug("REST request to count LeaveStatuses by criteria: {}", criteria);
        return ResponseEntity.ok().body(leaveStatusesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /leave-statuses/:id} : get the "id" leaveStatuses.
     *
     * @param id the id of the leaveStatuses to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leaveStatuses, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/leave-statuses/{id}")
    public ResponseEntity<LeaveStatuses> getLeaveStatuses(@PathVariable Long id) {
        log.debug("REST request to get LeaveStatuses : {}", id);
        Optional<LeaveStatuses> leaveStatuses = leaveStatusesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leaveStatuses);
    }

    /**
     * {@code DELETE  /leave-statuses/:id} : delete the "id" leaveStatuses.
     *
     * @param id the id of the leaveStatuses to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/leave-statuses/{id}")
    public ResponseEntity<Void> deleteLeaveStatuses(@PathVariable Long id) {
        log.debug("REST request to delete LeaveStatuses : {}", id);
        leaveStatusesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
