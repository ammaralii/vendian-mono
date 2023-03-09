package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.LeaveRequestApprovers;
import com.venturedive.vendian_mono.repository.LeaveRequestApproversRepository;
import com.venturedive.vendian_mono.service.LeaveRequestApproversQueryService;
import com.venturedive.vendian_mono.service.LeaveRequestApproversService;
import com.venturedive.vendian_mono.service.criteria.LeaveRequestApproversCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.LeaveRequestApprovers}.
 */
@RestController
@RequestMapping("/api")
public class LeaveRequestApproversResource {

    private final Logger log = LoggerFactory.getLogger(LeaveRequestApproversResource.class);

    private static final String ENTITY_NAME = "leaveRequestApprovers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeaveRequestApproversService leaveRequestApproversService;

    private final LeaveRequestApproversRepository leaveRequestApproversRepository;

    private final LeaveRequestApproversQueryService leaveRequestApproversQueryService;

    public LeaveRequestApproversResource(
        LeaveRequestApproversService leaveRequestApproversService,
        LeaveRequestApproversRepository leaveRequestApproversRepository,
        LeaveRequestApproversQueryService leaveRequestApproversQueryService
    ) {
        this.leaveRequestApproversService = leaveRequestApproversService;
        this.leaveRequestApproversRepository = leaveRequestApproversRepository;
        this.leaveRequestApproversQueryService = leaveRequestApproversQueryService;
    }

    /**
     * {@code POST  /leave-request-approvers} : Create a new leaveRequestApprovers.
     *
     * @param leaveRequestApprovers the leaveRequestApprovers to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leaveRequestApprovers, or with status {@code 400 (Bad Request)} if the leaveRequestApprovers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/leave-request-approvers")
    public ResponseEntity<LeaveRequestApprovers> createLeaveRequestApprovers(
        @Valid @RequestBody LeaveRequestApprovers leaveRequestApprovers
    ) throws URISyntaxException {
        log.debug("REST request to save LeaveRequestApprovers : {}", leaveRequestApprovers);
        if (leaveRequestApprovers.getId() != null) {
            throw new BadRequestAlertException("A new leaveRequestApprovers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeaveRequestApprovers result = leaveRequestApproversService.save(leaveRequestApprovers);
        return ResponseEntity
            .created(new URI("/api/leave-request-approvers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /leave-request-approvers/:id} : Updates an existing leaveRequestApprovers.
     *
     * @param id the id of the leaveRequestApprovers to save.
     * @param leaveRequestApprovers the leaveRequestApprovers to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveRequestApprovers,
     * or with status {@code 400 (Bad Request)} if the leaveRequestApprovers is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leaveRequestApprovers couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/leave-request-approvers/{id}")
    public ResponseEntity<LeaveRequestApprovers> updateLeaveRequestApprovers(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LeaveRequestApprovers leaveRequestApprovers
    ) throws URISyntaxException {
        log.debug("REST request to update LeaveRequestApprovers : {}, {}", id, leaveRequestApprovers);
        if (leaveRequestApprovers.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveRequestApprovers.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveRequestApproversRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LeaveRequestApprovers result = leaveRequestApproversService.update(leaveRequestApprovers);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveRequestApprovers.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /leave-request-approvers/:id} : Partial updates given fields of an existing leaveRequestApprovers, field will ignore if it is null
     *
     * @param id the id of the leaveRequestApprovers to save.
     * @param leaveRequestApprovers the leaveRequestApprovers to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveRequestApprovers,
     * or with status {@code 400 (Bad Request)} if the leaveRequestApprovers is not valid,
     * or with status {@code 404 (Not Found)} if the leaveRequestApprovers is not found,
     * or with status {@code 500 (Internal Server Error)} if the leaveRequestApprovers couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/leave-request-approvers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LeaveRequestApprovers> partialUpdateLeaveRequestApprovers(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LeaveRequestApprovers leaveRequestApprovers
    ) throws URISyntaxException {
        log.debug("REST request to partial update LeaveRequestApprovers partially : {}, {}", id, leaveRequestApprovers);
        if (leaveRequestApprovers.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveRequestApprovers.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveRequestApproversRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LeaveRequestApprovers> result = leaveRequestApproversService.partialUpdate(leaveRequestApprovers);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveRequestApprovers.getId().toString())
        );
    }

    /**
     * {@code GET  /leave-request-approvers} : get all the leaveRequestApprovers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leaveRequestApprovers in body.
     */
    @GetMapping("/leave-request-approvers")
    public ResponseEntity<List<LeaveRequestApprovers>> getAllLeaveRequestApprovers(
        LeaveRequestApproversCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LeaveRequestApprovers by criteria: {}", criteria);
        Page<LeaveRequestApprovers> page = leaveRequestApproversQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /leave-request-approvers/count} : count all the leaveRequestApprovers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/leave-request-approvers/count")
    public ResponseEntity<Long> countLeaveRequestApprovers(LeaveRequestApproversCriteria criteria) {
        log.debug("REST request to count LeaveRequestApprovers by criteria: {}", criteria);
        return ResponseEntity.ok().body(leaveRequestApproversQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /leave-request-approvers/:id} : get the "id" leaveRequestApprovers.
     *
     * @param id the id of the leaveRequestApprovers to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leaveRequestApprovers, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/leave-request-approvers/{id}")
    public ResponseEntity<LeaveRequestApprovers> getLeaveRequestApprovers(@PathVariable Long id) {
        log.debug("REST request to get LeaveRequestApprovers : {}", id);
        Optional<LeaveRequestApprovers> leaveRequestApprovers = leaveRequestApproversService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leaveRequestApprovers);
    }

    /**
     * {@code DELETE  /leave-request-approvers/:id} : delete the "id" leaveRequestApprovers.
     *
     * @param id the id of the leaveRequestApprovers to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/leave-request-approvers/{id}")
    public ResponseEntity<Void> deleteLeaveRequestApprovers(@PathVariable Long id) {
        log.debug("REST request to delete LeaveRequestApprovers : {}", id);
        leaveRequestApproversService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
