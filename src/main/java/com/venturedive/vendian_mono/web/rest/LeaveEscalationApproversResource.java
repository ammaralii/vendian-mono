package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.LeaveEscalationApprovers;
import com.venturedive.vendian_mono.repository.LeaveEscalationApproversRepository;
import com.venturedive.vendian_mono.service.LeaveEscalationApproversQueryService;
import com.venturedive.vendian_mono.service.LeaveEscalationApproversService;
import com.venturedive.vendian_mono.service.criteria.LeaveEscalationApproversCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.LeaveEscalationApprovers}.
 */
@RestController
@RequestMapping("/api")
public class LeaveEscalationApproversResource {

    private final Logger log = LoggerFactory.getLogger(LeaveEscalationApproversResource.class);

    private static final String ENTITY_NAME = "leaveEscalationApprovers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeaveEscalationApproversService leaveEscalationApproversService;

    private final LeaveEscalationApproversRepository leaveEscalationApproversRepository;

    private final LeaveEscalationApproversQueryService leaveEscalationApproversQueryService;

    public LeaveEscalationApproversResource(
        LeaveEscalationApproversService leaveEscalationApproversService,
        LeaveEscalationApproversRepository leaveEscalationApproversRepository,
        LeaveEscalationApproversQueryService leaveEscalationApproversQueryService
    ) {
        this.leaveEscalationApproversService = leaveEscalationApproversService;
        this.leaveEscalationApproversRepository = leaveEscalationApproversRepository;
        this.leaveEscalationApproversQueryService = leaveEscalationApproversQueryService;
    }

    /**
     * {@code POST  /leave-escalation-approvers} : Create a new leaveEscalationApprovers.
     *
     * @param leaveEscalationApprovers the leaveEscalationApprovers to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leaveEscalationApprovers, or with status {@code 400 (Bad Request)} if the leaveEscalationApprovers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/leave-escalation-approvers")
    public ResponseEntity<LeaveEscalationApprovers> createLeaveEscalationApprovers(
        @Valid @RequestBody LeaveEscalationApprovers leaveEscalationApprovers
    ) throws URISyntaxException {
        log.debug("REST request to save LeaveEscalationApprovers : {}", leaveEscalationApprovers);
        if (leaveEscalationApprovers.getId() != null) {
            throw new BadRequestAlertException("A new leaveEscalationApprovers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeaveEscalationApprovers result = leaveEscalationApproversService.save(leaveEscalationApprovers);
        return ResponseEntity
            .created(new URI("/api/leave-escalation-approvers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /leave-escalation-approvers/:id} : Updates an existing leaveEscalationApprovers.
     *
     * @param id the id of the leaveEscalationApprovers to save.
     * @param leaveEscalationApprovers the leaveEscalationApprovers to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveEscalationApprovers,
     * or with status {@code 400 (Bad Request)} if the leaveEscalationApprovers is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leaveEscalationApprovers couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/leave-escalation-approvers/{id}")
    public ResponseEntity<LeaveEscalationApprovers> updateLeaveEscalationApprovers(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LeaveEscalationApprovers leaveEscalationApprovers
    ) throws URISyntaxException {
        log.debug("REST request to update LeaveEscalationApprovers : {}, {}", id, leaveEscalationApprovers);
        if (leaveEscalationApprovers.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveEscalationApprovers.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveEscalationApproversRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LeaveEscalationApprovers result = leaveEscalationApproversService.update(leaveEscalationApprovers);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveEscalationApprovers.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /leave-escalation-approvers/:id} : Partial updates given fields of an existing leaveEscalationApprovers, field will ignore if it is null
     *
     * @param id the id of the leaveEscalationApprovers to save.
     * @param leaveEscalationApprovers the leaveEscalationApprovers to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveEscalationApprovers,
     * or with status {@code 400 (Bad Request)} if the leaveEscalationApprovers is not valid,
     * or with status {@code 404 (Not Found)} if the leaveEscalationApprovers is not found,
     * or with status {@code 500 (Internal Server Error)} if the leaveEscalationApprovers couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/leave-escalation-approvers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LeaveEscalationApprovers> partialUpdateLeaveEscalationApprovers(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LeaveEscalationApprovers leaveEscalationApprovers
    ) throws URISyntaxException {
        log.debug("REST request to partial update LeaveEscalationApprovers partially : {}, {}", id, leaveEscalationApprovers);
        if (leaveEscalationApprovers.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveEscalationApprovers.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveEscalationApproversRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LeaveEscalationApprovers> result = leaveEscalationApproversService.partialUpdate(leaveEscalationApprovers);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveEscalationApprovers.getId().toString())
        );
    }

    /**
     * {@code GET  /leave-escalation-approvers} : get all the leaveEscalationApprovers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leaveEscalationApprovers in body.
     */
    @GetMapping("/leave-escalation-approvers")
    public ResponseEntity<List<LeaveEscalationApprovers>> getAllLeaveEscalationApprovers(
        LeaveEscalationApproversCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LeaveEscalationApprovers by criteria: {}", criteria);
        Page<LeaveEscalationApprovers> page = leaveEscalationApproversQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /leave-escalation-approvers/count} : count all the leaveEscalationApprovers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/leave-escalation-approvers/count")
    public ResponseEntity<Long> countLeaveEscalationApprovers(LeaveEscalationApproversCriteria criteria) {
        log.debug("REST request to count LeaveEscalationApprovers by criteria: {}", criteria);
        return ResponseEntity.ok().body(leaveEscalationApproversQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /leave-escalation-approvers/:id} : get the "id" leaveEscalationApprovers.
     *
     * @param id the id of the leaveEscalationApprovers to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leaveEscalationApprovers, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/leave-escalation-approvers/{id}")
    public ResponseEntity<LeaveEscalationApprovers> getLeaveEscalationApprovers(@PathVariable Long id) {
        log.debug("REST request to get LeaveEscalationApprovers : {}", id);
        Optional<LeaveEscalationApprovers> leaveEscalationApprovers = leaveEscalationApproversService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leaveEscalationApprovers);
    }

    /**
     * {@code DELETE  /leave-escalation-approvers/:id} : delete the "id" leaveEscalationApprovers.
     *
     * @param id the id of the leaveEscalationApprovers to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/leave-escalation-approvers/{id}")
    public ResponseEntity<Void> deleteLeaveEscalationApprovers(@PathVariable Long id) {
        log.debug("REST request to delete LeaveEscalationApprovers : {}", id);
        leaveEscalationApproversService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
