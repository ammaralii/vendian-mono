package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.LeaveRequests;
import com.venturedive.vendian_mono.repository.LeaveRequestsRepository;
import com.venturedive.vendian_mono.service.LeaveRequestsQueryService;
import com.venturedive.vendian_mono.service.LeaveRequestsService;
import com.venturedive.vendian_mono.service.criteria.LeaveRequestsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.LeaveRequests}.
 */
@RestController
@RequestMapping("/api")
public class LeaveRequestsResource {

    private final Logger log = LoggerFactory.getLogger(LeaveRequestsResource.class);

    private static final String ENTITY_NAME = "leaveRequests";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeaveRequestsService leaveRequestsService;

    private final LeaveRequestsRepository leaveRequestsRepository;

    private final LeaveRequestsQueryService leaveRequestsQueryService;

    public LeaveRequestsResource(
        LeaveRequestsService leaveRequestsService,
        LeaveRequestsRepository leaveRequestsRepository,
        LeaveRequestsQueryService leaveRequestsQueryService
    ) {
        this.leaveRequestsService = leaveRequestsService;
        this.leaveRequestsRepository = leaveRequestsRepository;
        this.leaveRequestsQueryService = leaveRequestsQueryService;
    }

    /**
     * {@code POST  /leave-requests} : Create a new leaveRequests.
     *
     * @param leaveRequests the leaveRequests to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leaveRequests, or with status {@code 400 (Bad Request)} if the leaveRequests has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/leave-requests")
    public ResponseEntity<LeaveRequests> createLeaveRequests(@Valid @RequestBody LeaveRequests leaveRequests) throws URISyntaxException {
        log.debug("REST request to save LeaveRequests : {}", leaveRequests);
        if (leaveRequests.getId() != null) {
            throw new BadRequestAlertException("A new leaveRequests cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeaveRequests result = leaveRequestsService.save(leaveRequests);
        return ResponseEntity
            .created(new URI("/api/leave-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /leave-requests/:id} : Updates an existing leaveRequests.
     *
     * @param id the id of the leaveRequests to save.
     * @param leaveRequests the leaveRequests to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveRequests,
     * or with status {@code 400 (Bad Request)} if the leaveRequests is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leaveRequests couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/leave-requests/{id}")
    public ResponseEntity<LeaveRequests> updateLeaveRequests(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LeaveRequests leaveRequests
    ) throws URISyntaxException {
        log.debug("REST request to update LeaveRequests : {}, {}", id, leaveRequests);
        if (leaveRequests.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveRequests.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveRequestsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LeaveRequests result = leaveRequestsService.update(leaveRequests);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveRequests.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /leave-requests/:id} : Partial updates given fields of an existing leaveRequests, field will ignore if it is null
     *
     * @param id the id of the leaveRequests to save.
     * @param leaveRequests the leaveRequests to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveRequests,
     * or with status {@code 400 (Bad Request)} if the leaveRequests is not valid,
     * or with status {@code 404 (Not Found)} if the leaveRequests is not found,
     * or with status {@code 500 (Internal Server Error)} if the leaveRequests couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/leave-requests/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LeaveRequests> partialUpdateLeaveRequests(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LeaveRequests leaveRequests
    ) throws URISyntaxException {
        log.debug("REST request to partial update LeaveRequests partially : {}, {}", id, leaveRequests);
        if (leaveRequests.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveRequests.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveRequestsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LeaveRequests> result = leaveRequestsService.partialUpdate(leaveRequests);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveRequests.getId().toString())
        );
    }

    /**
     * {@code GET  /leave-requests} : get all the leaveRequests.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leaveRequests in body.
     */
    @GetMapping("/leave-requests")
    public ResponseEntity<List<LeaveRequests>> getAllLeaveRequests(
        LeaveRequestsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LeaveRequests by criteria: {}", criteria);
        Page<LeaveRequests> page = leaveRequestsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /leave-requests/count} : count all the leaveRequests.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/leave-requests/count")
    public ResponseEntity<Long> countLeaveRequests(LeaveRequestsCriteria criteria) {
        log.debug("REST request to count LeaveRequests by criteria: {}", criteria);
        return ResponseEntity.ok().body(leaveRequestsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /leave-requests/:id} : get the "id" leaveRequests.
     *
     * @param id the id of the leaveRequests to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leaveRequests, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/leave-requests/{id}")
    public ResponseEntity<LeaveRequests> getLeaveRequests(@PathVariable Long id) {
        log.debug("REST request to get LeaveRequests : {}", id);
        Optional<LeaveRequests> leaveRequests = leaveRequestsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leaveRequests);
    }

    /**
     * {@code DELETE  /leave-requests/:id} : delete the "id" leaveRequests.
     *
     * @param id the id of the leaveRequests to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/leave-requests/{id}")
    public ResponseEntity<Void> deleteLeaveRequests(@PathVariable Long id) {
        log.debug("REST request to delete LeaveRequests : {}", id);
        leaveRequestsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
