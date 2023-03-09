package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.LeaveStatusWorkFlows;
import com.venturedive.vendian_mono.repository.LeaveStatusWorkFlowsRepository;
import com.venturedive.vendian_mono.service.LeaveStatusWorkFlowsQueryService;
import com.venturedive.vendian_mono.service.LeaveStatusWorkFlowsService;
import com.venturedive.vendian_mono.service.criteria.LeaveStatusWorkFlowsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.LeaveStatusWorkFlows}.
 */
@RestController
@RequestMapping("/api")
public class LeaveStatusWorkFlowsResource {

    private final Logger log = LoggerFactory.getLogger(LeaveStatusWorkFlowsResource.class);

    private static final String ENTITY_NAME = "leaveStatusWorkFlows";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeaveStatusWorkFlowsService leaveStatusWorkFlowsService;

    private final LeaveStatusWorkFlowsRepository leaveStatusWorkFlowsRepository;

    private final LeaveStatusWorkFlowsQueryService leaveStatusWorkFlowsQueryService;

    public LeaveStatusWorkFlowsResource(
        LeaveStatusWorkFlowsService leaveStatusWorkFlowsService,
        LeaveStatusWorkFlowsRepository leaveStatusWorkFlowsRepository,
        LeaveStatusWorkFlowsQueryService leaveStatusWorkFlowsQueryService
    ) {
        this.leaveStatusWorkFlowsService = leaveStatusWorkFlowsService;
        this.leaveStatusWorkFlowsRepository = leaveStatusWorkFlowsRepository;
        this.leaveStatusWorkFlowsQueryService = leaveStatusWorkFlowsQueryService;
    }

    /**
     * {@code POST  /leave-status-work-flows} : Create a new leaveStatusWorkFlows.
     *
     * @param leaveStatusWorkFlows the leaveStatusWorkFlows to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leaveStatusWorkFlows, or with status {@code 400 (Bad Request)} if the leaveStatusWorkFlows has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/leave-status-work-flows")
    public ResponseEntity<LeaveStatusWorkFlows> createLeaveStatusWorkFlows(@Valid @RequestBody LeaveStatusWorkFlows leaveStatusWorkFlows)
        throws URISyntaxException {
        log.debug("REST request to save LeaveStatusWorkFlows : {}", leaveStatusWorkFlows);
        if (leaveStatusWorkFlows.getId() != null) {
            throw new BadRequestAlertException("A new leaveStatusWorkFlows cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeaveStatusWorkFlows result = leaveStatusWorkFlowsService.save(leaveStatusWorkFlows);
        return ResponseEntity
            .created(new URI("/api/leave-status-work-flows/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /leave-status-work-flows/:id} : Updates an existing leaveStatusWorkFlows.
     *
     * @param id the id of the leaveStatusWorkFlows to save.
     * @param leaveStatusWorkFlows the leaveStatusWorkFlows to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveStatusWorkFlows,
     * or with status {@code 400 (Bad Request)} if the leaveStatusWorkFlows is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leaveStatusWorkFlows couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/leave-status-work-flows/{id}")
    public ResponseEntity<LeaveStatusWorkFlows> updateLeaveStatusWorkFlows(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LeaveStatusWorkFlows leaveStatusWorkFlows
    ) throws URISyntaxException {
        log.debug("REST request to update LeaveStatusWorkFlows : {}, {}", id, leaveStatusWorkFlows);
        if (leaveStatusWorkFlows.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveStatusWorkFlows.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveStatusWorkFlowsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LeaveStatusWorkFlows result = leaveStatusWorkFlowsService.update(leaveStatusWorkFlows);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveStatusWorkFlows.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /leave-status-work-flows/:id} : Partial updates given fields of an existing leaveStatusWorkFlows, field will ignore if it is null
     *
     * @param id the id of the leaveStatusWorkFlows to save.
     * @param leaveStatusWorkFlows the leaveStatusWorkFlows to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveStatusWorkFlows,
     * or with status {@code 400 (Bad Request)} if the leaveStatusWorkFlows is not valid,
     * or with status {@code 404 (Not Found)} if the leaveStatusWorkFlows is not found,
     * or with status {@code 500 (Internal Server Error)} if the leaveStatusWorkFlows couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/leave-status-work-flows/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LeaveStatusWorkFlows> partialUpdateLeaveStatusWorkFlows(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LeaveStatusWorkFlows leaveStatusWorkFlows
    ) throws URISyntaxException {
        log.debug("REST request to partial update LeaveStatusWorkFlows partially : {}, {}", id, leaveStatusWorkFlows);
        if (leaveStatusWorkFlows.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveStatusWorkFlows.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveStatusWorkFlowsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LeaveStatusWorkFlows> result = leaveStatusWorkFlowsService.partialUpdate(leaveStatusWorkFlows);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveStatusWorkFlows.getId().toString())
        );
    }

    /**
     * {@code GET  /leave-status-work-flows} : get all the leaveStatusWorkFlows.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leaveStatusWorkFlows in body.
     */
    @GetMapping("/leave-status-work-flows")
    public ResponseEntity<List<LeaveStatusWorkFlows>> getAllLeaveStatusWorkFlows(
        LeaveStatusWorkFlowsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LeaveStatusWorkFlows by criteria: {}", criteria);
        Page<LeaveStatusWorkFlows> page = leaveStatusWorkFlowsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /leave-status-work-flows/count} : count all the leaveStatusWorkFlows.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/leave-status-work-flows/count")
    public ResponseEntity<Long> countLeaveStatusWorkFlows(LeaveStatusWorkFlowsCriteria criteria) {
        log.debug("REST request to count LeaveStatusWorkFlows by criteria: {}", criteria);
        return ResponseEntity.ok().body(leaveStatusWorkFlowsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /leave-status-work-flows/:id} : get the "id" leaveStatusWorkFlows.
     *
     * @param id the id of the leaveStatusWorkFlows to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leaveStatusWorkFlows, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/leave-status-work-flows/{id}")
    public ResponseEntity<LeaveStatusWorkFlows> getLeaveStatusWorkFlows(@PathVariable Long id) {
        log.debug("REST request to get LeaveStatusWorkFlows : {}", id);
        Optional<LeaveStatusWorkFlows> leaveStatusWorkFlows = leaveStatusWorkFlowsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leaveStatusWorkFlows);
    }

    /**
     * {@code DELETE  /leave-status-work-flows/:id} : delete the "id" leaveStatusWorkFlows.
     *
     * @param id the id of the leaveStatusWorkFlows to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/leave-status-work-flows/{id}")
    public ResponseEntity<Void> deleteLeaveStatusWorkFlows(@PathVariable Long id) {
        log.debug("REST request to delete LeaveStatusWorkFlows : {}", id);
        leaveStatusWorkFlowsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
