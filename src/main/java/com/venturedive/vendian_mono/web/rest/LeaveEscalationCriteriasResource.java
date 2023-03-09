package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.LeaveEscalationCriterias;
import com.venturedive.vendian_mono.repository.LeaveEscalationCriteriasRepository;
import com.venturedive.vendian_mono.service.LeaveEscalationCriteriasQueryService;
import com.venturedive.vendian_mono.service.LeaveEscalationCriteriasService;
import com.venturedive.vendian_mono.service.criteria.LeaveEscalationCriteriasCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.LeaveEscalationCriterias}.
 */
@RestController
@RequestMapping("/api")
public class LeaveEscalationCriteriasResource {

    private final Logger log = LoggerFactory.getLogger(LeaveEscalationCriteriasResource.class);

    private static final String ENTITY_NAME = "leaveEscalationCriterias";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeaveEscalationCriteriasService leaveEscalationCriteriasService;

    private final LeaveEscalationCriteriasRepository leaveEscalationCriteriasRepository;

    private final LeaveEscalationCriteriasQueryService leaveEscalationCriteriasQueryService;

    public LeaveEscalationCriteriasResource(
        LeaveEscalationCriteriasService leaveEscalationCriteriasService,
        LeaveEscalationCriteriasRepository leaveEscalationCriteriasRepository,
        LeaveEscalationCriteriasQueryService leaveEscalationCriteriasQueryService
    ) {
        this.leaveEscalationCriteriasService = leaveEscalationCriteriasService;
        this.leaveEscalationCriteriasRepository = leaveEscalationCriteriasRepository;
        this.leaveEscalationCriteriasQueryService = leaveEscalationCriteriasQueryService;
    }

    /**
     * {@code POST  /leave-escalation-criterias} : Create a new leaveEscalationCriterias.
     *
     * @param leaveEscalationCriterias the leaveEscalationCriterias to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leaveEscalationCriterias, or with status {@code 400 (Bad Request)} if the leaveEscalationCriterias has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/leave-escalation-criterias")
    public ResponseEntity<LeaveEscalationCriterias> createLeaveEscalationCriterias(
        @Valid @RequestBody LeaveEscalationCriterias leaveEscalationCriterias
    ) throws URISyntaxException {
        log.debug("REST request to save LeaveEscalationCriterias : {}", leaveEscalationCriterias);
        if (leaveEscalationCriterias.getId() != null) {
            throw new BadRequestAlertException("A new leaveEscalationCriterias cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeaveEscalationCriterias result = leaveEscalationCriteriasService.save(leaveEscalationCriterias);
        return ResponseEntity
            .created(new URI("/api/leave-escalation-criterias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /leave-escalation-criterias/:id} : Updates an existing leaveEscalationCriterias.
     *
     * @param id the id of the leaveEscalationCriterias to save.
     * @param leaveEscalationCriterias the leaveEscalationCriterias to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveEscalationCriterias,
     * or with status {@code 400 (Bad Request)} if the leaveEscalationCriterias is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leaveEscalationCriterias couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/leave-escalation-criterias/{id}")
    public ResponseEntity<LeaveEscalationCriterias> updateLeaveEscalationCriterias(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LeaveEscalationCriterias leaveEscalationCriterias
    ) throws URISyntaxException {
        log.debug("REST request to update LeaveEscalationCriterias : {}, {}", id, leaveEscalationCriterias);
        if (leaveEscalationCriterias.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveEscalationCriterias.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveEscalationCriteriasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LeaveEscalationCriterias result = leaveEscalationCriteriasService.update(leaveEscalationCriterias);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveEscalationCriterias.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /leave-escalation-criterias/:id} : Partial updates given fields of an existing leaveEscalationCriterias, field will ignore if it is null
     *
     * @param id the id of the leaveEscalationCriterias to save.
     * @param leaveEscalationCriterias the leaveEscalationCriterias to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveEscalationCriterias,
     * or with status {@code 400 (Bad Request)} if the leaveEscalationCriterias is not valid,
     * or with status {@code 404 (Not Found)} if the leaveEscalationCriterias is not found,
     * or with status {@code 500 (Internal Server Error)} if the leaveEscalationCriterias couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/leave-escalation-criterias/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LeaveEscalationCriterias> partialUpdateLeaveEscalationCriterias(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LeaveEscalationCriterias leaveEscalationCriterias
    ) throws URISyntaxException {
        log.debug("REST request to partial update LeaveEscalationCriterias partially : {}, {}", id, leaveEscalationCriterias);
        if (leaveEscalationCriterias.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveEscalationCriterias.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveEscalationCriteriasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LeaveEscalationCriterias> result = leaveEscalationCriteriasService.partialUpdate(leaveEscalationCriterias);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveEscalationCriterias.getId().toString())
        );
    }

    /**
     * {@code GET  /leave-escalation-criterias} : get all the leaveEscalationCriterias.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leaveEscalationCriterias in body.
     */
    @GetMapping("/leave-escalation-criterias")
    public ResponseEntity<List<LeaveEscalationCriterias>> getAllLeaveEscalationCriterias(
        LeaveEscalationCriteriasCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LeaveEscalationCriterias by criteria: {}", criteria);
        Page<LeaveEscalationCriterias> page = leaveEscalationCriteriasQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /leave-escalation-criterias/count} : count all the leaveEscalationCriterias.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/leave-escalation-criterias/count")
    public ResponseEntity<Long> countLeaveEscalationCriterias(LeaveEscalationCriteriasCriteria criteria) {
        log.debug("REST request to count LeaveEscalationCriterias by criteria: {}", criteria);
        return ResponseEntity.ok().body(leaveEscalationCriteriasQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /leave-escalation-criterias/:id} : get the "id" leaveEscalationCriterias.
     *
     * @param id the id of the leaveEscalationCriterias to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leaveEscalationCriterias, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/leave-escalation-criterias/{id}")
    public ResponseEntity<LeaveEscalationCriterias> getLeaveEscalationCriterias(@PathVariable Long id) {
        log.debug("REST request to get LeaveEscalationCriterias : {}", id);
        Optional<LeaveEscalationCriterias> leaveEscalationCriterias = leaveEscalationCriteriasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leaveEscalationCriterias);
    }

    /**
     * {@code DELETE  /leave-escalation-criterias/:id} : delete the "id" leaveEscalationCriterias.
     *
     * @param id the id of the leaveEscalationCriterias to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/leave-escalation-criterias/{id}")
    public ResponseEntity<Void> deleteLeaveEscalationCriterias(@PathVariable Long id) {
        log.debug("REST request to delete LeaveEscalationCriterias : {}", id);
        leaveEscalationCriteriasService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
