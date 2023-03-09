package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.LeaveApprovers;
import com.venturedive.vendian_mono.repository.LeaveApproversRepository;
import com.venturedive.vendian_mono.service.LeaveApproversQueryService;
import com.venturedive.vendian_mono.service.LeaveApproversService;
import com.venturedive.vendian_mono.service.criteria.LeaveApproversCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.LeaveApprovers}.
 */
@RestController
@RequestMapping("/api")
public class LeaveApproversResource {

    private final Logger log = LoggerFactory.getLogger(LeaveApproversResource.class);

    private static final String ENTITY_NAME = "leaveApprovers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeaveApproversService leaveApproversService;

    private final LeaveApproversRepository leaveApproversRepository;

    private final LeaveApproversQueryService leaveApproversQueryService;

    public LeaveApproversResource(
        LeaveApproversService leaveApproversService,
        LeaveApproversRepository leaveApproversRepository,
        LeaveApproversQueryService leaveApproversQueryService
    ) {
        this.leaveApproversService = leaveApproversService;
        this.leaveApproversRepository = leaveApproversRepository;
        this.leaveApproversQueryService = leaveApproversQueryService;
    }

    /**
     * {@code POST  /leave-approvers} : Create a new leaveApprovers.
     *
     * @param leaveApprovers the leaveApprovers to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leaveApprovers, or with status {@code 400 (Bad Request)} if the leaveApprovers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/leave-approvers")
    public ResponseEntity<LeaveApprovers> createLeaveApprovers(@Valid @RequestBody LeaveApprovers leaveApprovers)
        throws URISyntaxException {
        log.debug("REST request to save LeaveApprovers : {}", leaveApprovers);
        if (leaveApprovers.getId() != null) {
            throw new BadRequestAlertException("A new leaveApprovers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeaveApprovers result = leaveApproversService.save(leaveApprovers);
        return ResponseEntity
            .created(new URI("/api/leave-approvers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /leave-approvers/:id} : Updates an existing leaveApprovers.
     *
     * @param id the id of the leaveApprovers to save.
     * @param leaveApprovers the leaveApprovers to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveApprovers,
     * or with status {@code 400 (Bad Request)} if the leaveApprovers is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leaveApprovers couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/leave-approvers/{id}")
    public ResponseEntity<LeaveApprovers> updateLeaveApprovers(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LeaveApprovers leaveApprovers
    ) throws URISyntaxException {
        log.debug("REST request to update LeaveApprovers : {}, {}", id, leaveApprovers);
        if (leaveApprovers.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveApprovers.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveApproversRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LeaveApprovers result = leaveApproversService.update(leaveApprovers);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveApprovers.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /leave-approvers/:id} : Partial updates given fields of an existing leaveApprovers, field will ignore if it is null
     *
     * @param id the id of the leaveApprovers to save.
     * @param leaveApprovers the leaveApprovers to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveApprovers,
     * or with status {@code 400 (Bad Request)} if the leaveApprovers is not valid,
     * or with status {@code 404 (Not Found)} if the leaveApprovers is not found,
     * or with status {@code 500 (Internal Server Error)} if the leaveApprovers couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/leave-approvers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LeaveApprovers> partialUpdateLeaveApprovers(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LeaveApprovers leaveApprovers
    ) throws URISyntaxException {
        log.debug("REST request to partial update LeaveApprovers partially : {}, {}", id, leaveApprovers);
        if (leaveApprovers.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveApprovers.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveApproversRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LeaveApprovers> result = leaveApproversService.partialUpdate(leaveApprovers);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveApprovers.getId().toString())
        );
    }

    /**
     * {@code GET  /leave-approvers} : get all the leaveApprovers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leaveApprovers in body.
     */
    @GetMapping("/leave-approvers")
    public ResponseEntity<List<LeaveApprovers>> getAllLeaveApprovers(
        LeaveApproversCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LeaveApprovers by criteria: {}", criteria);
        Page<LeaveApprovers> page = leaveApproversQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /leave-approvers/count} : count all the leaveApprovers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/leave-approvers/count")
    public ResponseEntity<Long> countLeaveApprovers(LeaveApproversCriteria criteria) {
        log.debug("REST request to count LeaveApprovers by criteria: {}", criteria);
        return ResponseEntity.ok().body(leaveApproversQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /leave-approvers/:id} : get the "id" leaveApprovers.
     *
     * @param id the id of the leaveApprovers to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leaveApprovers, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/leave-approvers/{id}")
    public ResponseEntity<LeaveApprovers> getLeaveApprovers(@PathVariable Long id) {
        log.debug("REST request to get LeaveApprovers : {}", id);
        Optional<LeaveApprovers> leaveApprovers = leaveApproversService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leaveApprovers);
    }

    /**
     * {@code DELETE  /leave-approvers/:id} : delete the "id" leaveApprovers.
     *
     * @param id the id of the leaveApprovers to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/leave-approvers/{id}")
    public ResponseEntity<Void> deleteLeaveApprovers(@PathVariable Long id) {
        log.debug("REST request to delete LeaveApprovers : {}", id);
        leaveApproversService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
