package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.LeaveTypeEscalationRules;
import com.venturedive.vendian_mono.repository.LeaveTypeEscalationRulesRepository;
import com.venturedive.vendian_mono.service.LeaveTypeEscalationRulesQueryService;
import com.venturedive.vendian_mono.service.LeaveTypeEscalationRulesService;
import com.venturedive.vendian_mono.service.criteria.LeaveTypeEscalationRulesCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.LeaveTypeEscalationRules}.
 */
@RestController
@RequestMapping("/api")
public class LeaveTypeEscalationRulesResource {

    private final Logger log = LoggerFactory.getLogger(LeaveTypeEscalationRulesResource.class);

    private static final String ENTITY_NAME = "leaveTypeEscalationRules";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeaveTypeEscalationRulesService leaveTypeEscalationRulesService;

    private final LeaveTypeEscalationRulesRepository leaveTypeEscalationRulesRepository;

    private final LeaveTypeEscalationRulesQueryService leaveTypeEscalationRulesQueryService;

    public LeaveTypeEscalationRulesResource(
        LeaveTypeEscalationRulesService leaveTypeEscalationRulesService,
        LeaveTypeEscalationRulesRepository leaveTypeEscalationRulesRepository,
        LeaveTypeEscalationRulesQueryService leaveTypeEscalationRulesQueryService
    ) {
        this.leaveTypeEscalationRulesService = leaveTypeEscalationRulesService;
        this.leaveTypeEscalationRulesRepository = leaveTypeEscalationRulesRepository;
        this.leaveTypeEscalationRulesQueryService = leaveTypeEscalationRulesQueryService;
    }

    /**
     * {@code POST  /leave-type-escalation-rules} : Create a new leaveTypeEscalationRules.
     *
     * @param leaveTypeEscalationRules the leaveTypeEscalationRules to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leaveTypeEscalationRules, or with status {@code 400 (Bad Request)} if the leaveTypeEscalationRules has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/leave-type-escalation-rules")
    public ResponseEntity<LeaveTypeEscalationRules> createLeaveTypeEscalationRules(
        @Valid @RequestBody LeaveTypeEscalationRules leaveTypeEscalationRules
    ) throws URISyntaxException {
        log.debug("REST request to save LeaveTypeEscalationRules : {}", leaveTypeEscalationRules);
        if (leaveTypeEscalationRules.getId() != null) {
            throw new BadRequestAlertException("A new leaveTypeEscalationRules cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeaveTypeEscalationRules result = leaveTypeEscalationRulesService.save(leaveTypeEscalationRules);
        return ResponseEntity
            .created(new URI("/api/leave-type-escalation-rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /leave-type-escalation-rules/:id} : Updates an existing leaveTypeEscalationRules.
     *
     * @param id the id of the leaveTypeEscalationRules to save.
     * @param leaveTypeEscalationRules the leaveTypeEscalationRules to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveTypeEscalationRules,
     * or with status {@code 400 (Bad Request)} if the leaveTypeEscalationRules is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leaveTypeEscalationRules couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/leave-type-escalation-rules/{id}")
    public ResponseEntity<LeaveTypeEscalationRules> updateLeaveTypeEscalationRules(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LeaveTypeEscalationRules leaveTypeEscalationRules
    ) throws URISyntaxException {
        log.debug("REST request to update LeaveTypeEscalationRules : {}, {}", id, leaveTypeEscalationRules);
        if (leaveTypeEscalationRules.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveTypeEscalationRules.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveTypeEscalationRulesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LeaveTypeEscalationRules result = leaveTypeEscalationRulesService.update(leaveTypeEscalationRules);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveTypeEscalationRules.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /leave-type-escalation-rules/:id} : Partial updates given fields of an existing leaveTypeEscalationRules, field will ignore if it is null
     *
     * @param id the id of the leaveTypeEscalationRules to save.
     * @param leaveTypeEscalationRules the leaveTypeEscalationRules to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveTypeEscalationRules,
     * or with status {@code 400 (Bad Request)} if the leaveTypeEscalationRules is not valid,
     * or with status {@code 404 (Not Found)} if the leaveTypeEscalationRules is not found,
     * or with status {@code 500 (Internal Server Error)} if the leaveTypeEscalationRules couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/leave-type-escalation-rules/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LeaveTypeEscalationRules> partialUpdateLeaveTypeEscalationRules(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LeaveTypeEscalationRules leaveTypeEscalationRules
    ) throws URISyntaxException {
        log.debug("REST request to partial update LeaveTypeEscalationRules partially : {}, {}", id, leaveTypeEscalationRules);
        if (leaveTypeEscalationRules.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveTypeEscalationRules.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveTypeEscalationRulesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LeaveTypeEscalationRules> result = leaveTypeEscalationRulesService.partialUpdate(leaveTypeEscalationRules);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveTypeEscalationRules.getId().toString())
        );
    }

    /**
     * {@code GET  /leave-type-escalation-rules} : get all the leaveTypeEscalationRules.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leaveTypeEscalationRules in body.
     */
    @GetMapping("/leave-type-escalation-rules")
    public ResponseEntity<List<LeaveTypeEscalationRules>> getAllLeaveTypeEscalationRules(
        LeaveTypeEscalationRulesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LeaveTypeEscalationRules by criteria: {}", criteria);
        Page<LeaveTypeEscalationRules> page = leaveTypeEscalationRulesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /leave-type-escalation-rules/count} : count all the leaveTypeEscalationRules.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/leave-type-escalation-rules/count")
    public ResponseEntity<Long> countLeaveTypeEscalationRules(LeaveTypeEscalationRulesCriteria criteria) {
        log.debug("REST request to count LeaveTypeEscalationRules by criteria: {}", criteria);
        return ResponseEntity.ok().body(leaveTypeEscalationRulesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /leave-type-escalation-rules/:id} : get the "id" leaveTypeEscalationRules.
     *
     * @param id the id of the leaveTypeEscalationRules to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leaveTypeEscalationRules, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/leave-type-escalation-rules/{id}")
    public ResponseEntity<LeaveTypeEscalationRules> getLeaveTypeEscalationRules(@PathVariable Long id) {
        log.debug("REST request to get LeaveTypeEscalationRules : {}", id);
        Optional<LeaveTypeEscalationRules> leaveTypeEscalationRules = leaveTypeEscalationRulesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leaveTypeEscalationRules);
    }

    /**
     * {@code DELETE  /leave-type-escalation-rules/:id} : delete the "id" leaveTypeEscalationRules.
     *
     * @param id the id of the leaveTypeEscalationRules to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/leave-type-escalation-rules/{id}")
    public ResponseEntity<Void> deleteLeaveTypeEscalationRules(@PathVariable Long id) {
        log.debug("REST request to delete LeaveTypeEscalationRules : {}", id);
        leaveTypeEscalationRulesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
