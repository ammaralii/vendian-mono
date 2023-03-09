package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.LeaveTypes;
import com.venturedive.vendian_mono.repository.LeaveTypesRepository;
import com.venturedive.vendian_mono.service.LeaveTypesQueryService;
import com.venturedive.vendian_mono.service.LeaveTypesService;
import com.venturedive.vendian_mono.service.criteria.LeaveTypesCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.LeaveTypes}.
 */
@RestController
@RequestMapping("/api")
public class LeaveTypesResource {

    private final Logger log = LoggerFactory.getLogger(LeaveTypesResource.class);

    private static final String ENTITY_NAME = "leaveTypes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeaveTypesService leaveTypesService;

    private final LeaveTypesRepository leaveTypesRepository;

    private final LeaveTypesQueryService leaveTypesQueryService;

    public LeaveTypesResource(
        LeaveTypesService leaveTypesService,
        LeaveTypesRepository leaveTypesRepository,
        LeaveTypesQueryService leaveTypesQueryService
    ) {
        this.leaveTypesService = leaveTypesService;
        this.leaveTypesRepository = leaveTypesRepository;
        this.leaveTypesQueryService = leaveTypesQueryService;
    }

    /**
     * {@code POST  /leave-types} : Create a new leaveTypes.
     *
     * @param leaveTypes the leaveTypes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leaveTypes, or with status {@code 400 (Bad Request)} if the leaveTypes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/leave-types")
    public ResponseEntity<LeaveTypes> createLeaveTypes(@Valid @RequestBody LeaveTypes leaveTypes) throws URISyntaxException {
        log.debug("REST request to save LeaveTypes : {}", leaveTypes);
        if (leaveTypes.getId() != null) {
            throw new BadRequestAlertException("A new leaveTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeaveTypes result = leaveTypesService.save(leaveTypes);
        return ResponseEntity
            .created(new URI("/api/leave-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /leave-types/:id} : Updates an existing leaveTypes.
     *
     * @param id the id of the leaveTypes to save.
     * @param leaveTypes the leaveTypes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveTypes,
     * or with status {@code 400 (Bad Request)} if the leaveTypes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leaveTypes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/leave-types/{id}")
    public ResponseEntity<LeaveTypes> updateLeaveTypes(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LeaveTypes leaveTypes
    ) throws URISyntaxException {
        log.debug("REST request to update LeaveTypes : {}, {}", id, leaveTypes);
        if (leaveTypes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveTypes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveTypesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LeaveTypes result = leaveTypesService.update(leaveTypes);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveTypes.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /leave-types/:id} : Partial updates given fields of an existing leaveTypes, field will ignore if it is null
     *
     * @param id the id of the leaveTypes to save.
     * @param leaveTypes the leaveTypes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveTypes,
     * or with status {@code 400 (Bad Request)} if the leaveTypes is not valid,
     * or with status {@code 404 (Not Found)} if the leaveTypes is not found,
     * or with status {@code 500 (Internal Server Error)} if the leaveTypes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/leave-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LeaveTypes> partialUpdateLeaveTypes(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LeaveTypes leaveTypes
    ) throws URISyntaxException {
        log.debug("REST request to partial update LeaveTypes partially : {}, {}", id, leaveTypes);
        if (leaveTypes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveTypes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveTypesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LeaveTypes> result = leaveTypesService.partialUpdate(leaveTypes);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveTypes.getId().toString())
        );
    }

    /**
     * {@code GET  /leave-types} : get all the leaveTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leaveTypes in body.
     */
    @GetMapping("/leave-types")
    public ResponseEntity<List<LeaveTypes>> getAllLeaveTypes(
        LeaveTypesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LeaveTypes by criteria: {}", criteria);
        Page<LeaveTypes> page = leaveTypesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /leave-types/count} : count all the leaveTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/leave-types/count")
    public ResponseEntity<Long> countLeaveTypes(LeaveTypesCriteria criteria) {
        log.debug("REST request to count LeaveTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(leaveTypesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /leave-types/:id} : get the "id" leaveTypes.
     *
     * @param id the id of the leaveTypes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leaveTypes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/leave-types/{id}")
    public ResponseEntity<LeaveTypes> getLeaveTypes(@PathVariable Long id) {
        log.debug("REST request to get LeaveTypes : {}", id);
        Optional<LeaveTypes> leaveTypes = leaveTypesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leaveTypes);
    }

    /**
     * {@code DELETE  /leave-types/:id} : delete the "id" leaveTypes.
     *
     * @param id the id of the leaveTypes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/leave-types/{id}")
    public ResponseEntity<Void> deleteLeaveTypes(@PathVariable Long id) {
        log.debug("REST request to delete LeaveTypes : {}", id);
        leaveTypesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
