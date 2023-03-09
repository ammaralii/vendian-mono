package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.LeaveTypeApprovalRules;
import com.venturedive.vendian_mono.repository.LeaveTypeApprovalRulesRepository;
import com.venturedive.vendian_mono.service.LeaveTypeApprovalRulesQueryService;
import com.venturedive.vendian_mono.service.LeaveTypeApprovalRulesService;
import com.venturedive.vendian_mono.service.criteria.LeaveTypeApprovalRulesCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.LeaveTypeApprovalRules}.
 */
@RestController
@RequestMapping("/api")
public class LeaveTypeApprovalRulesResource {

    private final Logger log = LoggerFactory.getLogger(LeaveTypeApprovalRulesResource.class);

    private static final String ENTITY_NAME = "leaveTypeApprovalRules";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeaveTypeApprovalRulesService leaveTypeApprovalRulesService;

    private final LeaveTypeApprovalRulesRepository leaveTypeApprovalRulesRepository;

    private final LeaveTypeApprovalRulesQueryService leaveTypeApprovalRulesQueryService;

    public LeaveTypeApprovalRulesResource(
        LeaveTypeApprovalRulesService leaveTypeApprovalRulesService,
        LeaveTypeApprovalRulesRepository leaveTypeApprovalRulesRepository,
        LeaveTypeApprovalRulesQueryService leaveTypeApprovalRulesQueryService
    ) {
        this.leaveTypeApprovalRulesService = leaveTypeApprovalRulesService;
        this.leaveTypeApprovalRulesRepository = leaveTypeApprovalRulesRepository;
        this.leaveTypeApprovalRulesQueryService = leaveTypeApprovalRulesQueryService;
    }

    /**
     * {@code POST  /leave-type-approval-rules} : Create a new leaveTypeApprovalRules.
     *
     * @param leaveTypeApprovalRules the leaveTypeApprovalRules to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leaveTypeApprovalRules, or with status {@code 400 (Bad Request)} if the leaveTypeApprovalRules has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/leave-type-approval-rules")
    public ResponseEntity<LeaveTypeApprovalRules> createLeaveTypeApprovalRules(
        @Valid @RequestBody LeaveTypeApprovalRules leaveTypeApprovalRules
    ) throws URISyntaxException {
        log.debug("REST request to save LeaveTypeApprovalRules : {}", leaveTypeApprovalRules);
        if (leaveTypeApprovalRules.getId() != null) {
            throw new BadRequestAlertException("A new leaveTypeApprovalRules cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeaveTypeApprovalRules result = leaveTypeApprovalRulesService.save(leaveTypeApprovalRules);
        return ResponseEntity
            .created(new URI("/api/leave-type-approval-rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /leave-type-approval-rules/:id} : Updates an existing leaveTypeApprovalRules.
     *
     * @param id the id of the leaveTypeApprovalRules to save.
     * @param leaveTypeApprovalRules the leaveTypeApprovalRules to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveTypeApprovalRules,
     * or with status {@code 400 (Bad Request)} if the leaveTypeApprovalRules is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leaveTypeApprovalRules couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/leave-type-approval-rules/{id}")
    public ResponseEntity<LeaveTypeApprovalRules> updateLeaveTypeApprovalRules(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LeaveTypeApprovalRules leaveTypeApprovalRules
    ) throws URISyntaxException {
        log.debug("REST request to update LeaveTypeApprovalRules : {}, {}", id, leaveTypeApprovalRules);
        if (leaveTypeApprovalRules.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveTypeApprovalRules.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveTypeApprovalRulesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LeaveTypeApprovalRules result = leaveTypeApprovalRulesService.update(leaveTypeApprovalRules);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveTypeApprovalRules.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /leave-type-approval-rules/:id} : Partial updates given fields of an existing leaveTypeApprovalRules, field will ignore if it is null
     *
     * @param id the id of the leaveTypeApprovalRules to save.
     * @param leaveTypeApprovalRules the leaveTypeApprovalRules to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveTypeApprovalRules,
     * or with status {@code 400 (Bad Request)} if the leaveTypeApprovalRules is not valid,
     * or with status {@code 404 (Not Found)} if the leaveTypeApprovalRules is not found,
     * or with status {@code 500 (Internal Server Error)} if the leaveTypeApprovalRules couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/leave-type-approval-rules/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LeaveTypeApprovalRules> partialUpdateLeaveTypeApprovalRules(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LeaveTypeApprovalRules leaveTypeApprovalRules
    ) throws URISyntaxException {
        log.debug("REST request to partial update LeaveTypeApprovalRules partially : {}, {}", id, leaveTypeApprovalRules);
        if (leaveTypeApprovalRules.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveTypeApprovalRules.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveTypeApprovalRulesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LeaveTypeApprovalRules> result = leaveTypeApprovalRulesService.partialUpdate(leaveTypeApprovalRules);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveTypeApprovalRules.getId().toString())
        );
    }

    /**
     * {@code GET  /leave-type-approval-rules} : get all the leaveTypeApprovalRules.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leaveTypeApprovalRules in body.
     */
    @GetMapping("/leave-type-approval-rules")
    public ResponseEntity<List<LeaveTypeApprovalRules>> getAllLeaveTypeApprovalRules(
        LeaveTypeApprovalRulesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LeaveTypeApprovalRules by criteria: {}", criteria);
        Page<LeaveTypeApprovalRules> page = leaveTypeApprovalRulesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /leave-type-approval-rules/count} : count all the leaveTypeApprovalRules.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/leave-type-approval-rules/count")
    public ResponseEntity<Long> countLeaveTypeApprovalRules(LeaveTypeApprovalRulesCriteria criteria) {
        log.debug("REST request to count LeaveTypeApprovalRules by criteria: {}", criteria);
        return ResponseEntity.ok().body(leaveTypeApprovalRulesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /leave-type-approval-rules/:id} : get the "id" leaveTypeApprovalRules.
     *
     * @param id the id of the leaveTypeApprovalRules to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leaveTypeApprovalRules, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/leave-type-approval-rules/{id}")
    public ResponseEntity<LeaveTypeApprovalRules> getLeaveTypeApprovalRules(@PathVariable Long id) {
        log.debug("REST request to get LeaveTypeApprovalRules : {}", id);
        Optional<LeaveTypeApprovalRules> leaveTypeApprovalRules = leaveTypeApprovalRulesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leaveTypeApprovalRules);
    }

    /**
     * {@code DELETE  /leave-type-approval-rules/:id} : delete the "id" leaveTypeApprovalRules.
     *
     * @param id the id of the leaveTypeApprovalRules to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/leave-type-approval-rules/{id}")
    public ResponseEntity<Void> deleteLeaveTypeApprovalRules(@PathVariable Long id) {
        log.debug("REST request to delete LeaveTypeApprovalRules : {}", id);
        leaveTypeApprovalRulesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
