package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.LeaveApprovalCriterias;
import com.venturedive.vendian_mono.repository.LeaveApprovalCriteriasRepository;
import com.venturedive.vendian_mono.service.LeaveApprovalCriteriasQueryService;
import com.venturedive.vendian_mono.service.LeaveApprovalCriteriasService;
import com.venturedive.vendian_mono.service.criteria.LeaveApprovalCriteriasCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.LeaveApprovalCriterias}.
 */
@RestController
@RequestMapping("/api")
public class LeaveApprovalCriteriasResource {

    private final Logger log = LoggerFactory.getLogger(LeaveApprovalCriteriasResource.class);

    private static final String ENTITY_NAME = "leaveApprovalCriterias";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeaveApprovalCriteriasService leaveApprovalCriteriasService;

    private final LeaveApprovalCriteriasRepository leaveApprovalCriteriasRepository;

    private final LeaveApprovalCriteriasQueryService leaveApprovalCriteriasQueryService;

    public LeaveApprovalCriteriasResource(
        LeaveApprovalCriteriasService leaveApprovalCriteriasService,
        LeaveApprovalCriteriasRepository leaveApprovalCriteriasRepository,
        LeaveApprovalCriteriasQueryService leaveApprovalCriteriasQueryService
    ) {
        this.leaveApprovalCriteriasService = leaveApprovalCriteriasService;
        this.leaveApprovalCriteriasRepository = leaveApprovalCriteriasRepository;
        this.leaveApprovalCriteriasQueryService = leaveApprovalCriteriasQueryService;
    }

    /**
     * {@code POST  /leave-approval-criterias} : Create a new leaveApprovalCriterias.
     *
     * @param leaveApprovalCriterias the leaveApprovalCriterias to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leaveApprovalCriterias, or with status {@code 400 (Bad Request)} if the leaveApprovalCriterias has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/leave-approval-criterias")
    public ResponseEntity<LeaveApprovalCriterias> createLeaveApprovalCriterias(
        @Valid @RequestBody LeaveApprovalCriterias leaveApprovalCriterias
    ) throws URISyntaxException {
        log.debug("REST request to save LeaveApprovalCriterias : {}", leaveApprovalCriterias);
        if (leaveApprovalCriterias.getId() != null) {
            throw new BadRequestAlertException("A new leaveApprovalCriterias cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeaveApprovalCriterias result = leaveApprovalCriteriasService.save(leaveApprovalCriterias);
        return ResponseEntity
            .created(new URI("/api/leave-approval-criterias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /leave-approval-criterias/:id} : Updates an existing leaveApprovalCriterias.
     *
     * @param id the id of the leaveApprovalCriterias to save.
     * @param leaveApprovalCriterias the leaveApprovalCriterias to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveApprovalCriterias,
     * or with status {@code 400 (Bad Request)} if the leaveApprovalCriterias is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leaveApprovalCriterias couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/leave-approval-criterias/{id}")
    public ResponseEntity<LeaveApprovalCriterias> updateLeaveApprovalCriterias(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LeaveApprovalCriterias leaveApprovalCriterias
    ) throws URISyntaxException {
        log.debug("REST request to update LeaveApprovalCriterias : {}, {}", id, leaveApprovalCriterias);
        if (leaveApprovalCriterias.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveApprovalCriterias.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveApprovalCriteriasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LeaveApprovalCriterias result = leaveApprovalCriteriasService.update(leaveApprovalCriterias);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveApprovalCriterias.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /leave-approval-criterias/:id} : Partial updates given fields of an existing leaveApprovalCriterias, field will ignore if it is null
     *
     * @param id the id of the leaveApprovalCriterias to save.
     * @param leaveApprovalCriterias the leaveApprovalCriterias to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveApprovalCriterias,
     * or with status {@code 400 (Bad Request)} if the leaveApprovalCriterias is not valid,
     * or with status {@code 404 (Not Found)} if the leaveApprovalCriterias is not found,
     * or with status {@code 500 (Internal Server Error)} if the leaveApprovalCriterias couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/leave-approval-criterias/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LeaveApprovalCriterias> partialUpdateLeaveApprovalCriterias(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LeaveApprovalCriterias leaveApprovalCriterias
    ) throws URISyntaxException {
        log.debug("REST request to partial update LeaveApprovalCriterias partially : {}, {}", id, leaveApprovalCriterias);
        if (leaveApprovalCriterias.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveApprovalCriterias.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveApprovalCriteriasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LeaveApprovalCriterias> result = leaveApprovalCriteriasService.partialUpdate(leaveApprovalCriterias);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveApprovalCriterias.getId().toString())
        );
    }

    /**
     * {@code GET  /leave-approval-criterias} : get all the leaveApprovalCriterias.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leaveApprovalCriterias in body.
     */
    @GetMapping("/leave-approval-criterias")
    public ResponseEntity<List<LeaveApprovalCriterias>> getAllLeaveApprovalCriterias(
        LeaveApprovalCriteriasCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LeaveApprovalCriterias by criteria: {}", criteria);
        Page<LeaveApprovalCriterias> page = leaveApprovalCriteriasQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /leave-approval-criterias/count} : count all the leaveApprovalCriterias.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/leave-approval-criterias/count")
    public ResponseEntity<Long> countLeaveApprovalCriterias(LeaveApprovalCriteriasCriteria criteria) {
        log.debug("REST request to count LeaveApprovalCriterias by criteria: {}", criteria);
        return ResponseEntity.ok().body(leaveApprovalCriteriasQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /leave-approval-criterias/:id} : get the "id" leaveApprovalCriterias.
     *
     * @param id the id of the leaveApprovalCriterias to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leaveApprovalCriterias, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/leave-approval-criterias/{id}")
    public ResponseEntity<LeaveApprovalCriterias> getLeaveApprovalCriterias(@PathVariable Long id) {
        log.debug("REST request to get LeaveApprovalCriterias : {}", id);
        Optional<LeaveApprovalCriterias> leaveApprovalCriterias = leaveApprovalCriteriasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leaveApprovalCriterias);
    }

    /**
     * {@code DELETE  /leave-approval-criterias/:id} : delete the "id" leaveApprovalCriterias.
     *
     * @param id the id of the leaveApprovalCriterias to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/leave-approval-criterias/{id}")
    public ResponseEntity<Void> deleteLeaveApprovalCriterias(@PathVariable Long id) {
        log.debug("REST request to delete LeaveApprovalCriterias : {}", id);
        leaveApprovalCriteriasService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
