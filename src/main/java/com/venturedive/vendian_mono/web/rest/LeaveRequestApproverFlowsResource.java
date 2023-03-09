package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.LeaveRequestApproverFlows;
import com.venturedive.vendian_mono.repository.LeaveRequestApproverFlowsRepository;
import com.venturedive.vendian_mono.service.LeaveRequestApproverFlowsQueryService;
import com.venturedive.vendian_mono.service.LeaveRequestApproverFlowsService;
import com.venturedive.vendian_mono.service.criteria.LeaveRequestApproverFlowsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.LeaveRequestApproverFlows}.
 */
@RestController
@RequestMapping("/api")
public class LeaveRequestApproverFlowsResource {

    private final Logger log = LoggerFactory.getLogger(LeaveRequestApproverFlowsResource.class);

    private static final String ENTITY_NAME = "leaveRequestApproverFlows";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeaveRequestApproverFlowsService leaveRequestApproverFlowsService;

    private final LeaveRequestApproverFlowsRepository leaveRequestApproverFlowsRepository;

    private final LeaveRequestApproverFlowsQueryService leaveRequestApproverFlowsQueryService;

    public LeaveRequestApproverFlowsResource(
        LeaveRequestApproverFlowsService leaveRequestApproverFlowsService,
        LeaveRequestApproverFlowsRepository leaveRequestApproverFlowsRepository,
        LeaveRequestApproverFlowsQueryService leaveRequestApproverFlowsQueryService
    ) {
        this.leaveRequestApproverFlowsService = leaveRequestApproverFlowsService;
        this.leaveRequestApproverFlowsRepository = leaveRequestApproverFlowsRepository;
        this.leaveRequestApproverFlowsQueryService = leaveRequestApproverFlowsQueryService;
    }

    /**
     * {@code POST  /leave-request-approver-flows} : Create a new leaveRequestApproverFlows.
     *
     * @param leaveRequestApproverFlows the leaveRequestApproverFlows to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leaveRequestApproverFlows, or with status {@code 400 (Bad Request)} if the leaveRequestApproverFlows has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/leave-request-approver-flows")
    public ResponseEntity<LeaveRequestApproverFlows> createLeaveRequestApproverFlows(
        @Valid @RequestBody LeaveRequestApproverFlows leaveRequestApproverFlows
    ) throws URISyntaxException {
        log.debug("REST request to save LeaveRequestApproverFlows : {}", leaveRequestApproverFlows);
        if (leaveRequestApproverFlows.getId() != null) {
            throw new BadRequestAlertException("A new leaveRequestApproverFlows cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeaveRequestApproverFlows result = leaveRequestApproverFlowsService.save(leaveRequestApproverFlows);
        return ResponseEntity
            .created(new URI("/api/leave-request-approver-flows/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /leave-request-approver-flows/:id} : Updates an existing leaveRequestApproverFlows.
     *
     * @param id the id of the leaveRequestApproverFlows to save.
     * @param leaveRequestApproverFlows the leaveRequestApproverFlows to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveRequestApproverFlows,
     * or with status {@code 400 (Bad Request)} if the leaveRequestApproverFlows is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leaveRequestApproverFlows couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/leave-request-approver-flows/{id}")
    public ResponseEntity<LeaveRequestApproverFlows> updateLeaveRequestApproverFlows(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LeaveRequestApproverFlows leaveRequestApproverFlows
    ) throws URISyntaxException {
        log.debug("REST request to update LeaveRequestApproverFlows : {}, {}", id, leaveRequestApproverFlows);
        if (leaveRequestApproverFlows.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveRequestApproverFlows.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveRequestApproverFlowsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LeaveRequestApproverFlows result = leaveRequestApproverFlowsService.update(leaveRequestApproverFlows);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveRequestApproverFlows.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /leave-request-approver-flows/:id} : Partial updates given fields of an existing leaveRequestApproverFlows, field will ignore if it is null
     *
     * @param id the id of the leaveRequestApproverFlows to save.
     * @param leaveRequestApproverFlows the leaveRequestApproverFlows to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveRequestApproverFlows,
     * or with status {@code 400 (Bad Request)} if the leaveRequestApproverFlows is not valid,
     * or with status {@code 404 (Not Found)} if the leaveRequestApproverFlows is not found,
     * or with status {@code 500 (Internal Server Error)} if the leaveRequestApproverFlows couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/leave-request-approver-flows/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LeaveRequestApproverFlows> partialUpdateLeaveRequestApproverFlows(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LeaveRequestApproverFlows leaveRequestApproverFlows
    ) throws URISyntaxException {
        log.debug("REST request to partial update LeaveRequestApproverFlows partially : {}, {}", id, leaveRequestApproverFlows);
        if (leaveRequestApproverFlows.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveRequestApproverFlows.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveRequestApproverFlowsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LeaveRequestApproverFlows> result = leaveRequestApproverFlowsService.partialUpdate(leaveRequestApproverFlows);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveRequestApproverFlows.getId().toString())
        );
    }

    /**
     * {@code GET  /leave-request-approver-flows} : get all the leaveRequestApproverFlows.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leaveRequestApproverFlows in body.
     */
    @GetMapping("/leave-request-approver-flows")
    public ResponseEntity<List<LeaveRequestApproverFlows>> getAllLeaveRequestApproverFlows(
        LeaveRequestApproverFlowsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LeaveRequestApproverFlows by criteria: {}", criteria);
        Page<LeaveRequestApproverFlows> page = leaveRequestApproverFlowsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /leave-request-approver-flows/count} : count all the leaveRequestApproverFlows.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/leave-request-approver-flows/count")
    public ResponseEntity<Long> countLeaveRequestApproverFlows(LeaveRequestApproverFlowsCriteria criteria) {
        log.debug("REST request to count LeaveRequestApproverFlows by criteria: {}", criteria);
        return ResponseEntity.ok().body(leaveRequestApproverFlowsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /leave-request-approver-flows/:id} : get the "id" leaveRequestApproverFlows.
     *
     * @param id the id of the leaveRequestApproverFlows to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leaveRequestApproverFlows, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/leave-request-approver-flows/{id}")
    public ResponseEntity<LeaveRequestApproverFlows> getLeaveRequestApproverFlows(@PathVariable Long id) {
        log.debug("REST request to get LeaveRequestApproverFlows : {}", id);
        Optional<LeaveRequestApproverFlows> leaveRequestApproverFlows = leaveRequestApproverFlowsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leaveRequestApproverFlows);
    }

    /**
     * {@code DELETE  /leave-request-approver-flows/:id} : delete the "id" leaveRequestApproverFlows.
     *
     * @param id the id of the leaveRequestApproverFlows to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/leave-request-approver-flows/{id}")
    public ResponseEntity<Void> deleteLeaveRequestApproverFlows(@PathVariable Long id) {
        log.debug("REST request to delete LeaveRequestApproverFlows : {}", id);
        leaveRequestApproverFlowsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
