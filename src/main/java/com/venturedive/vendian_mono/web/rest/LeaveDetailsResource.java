package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.LeaveDetails;
import com.venturedive.vendian_mono.repository.LeaveDetailsRepository;
import com.venturedive.vendian_mono.service.LeaveDetailsQueryService;
import com.venturedive.vendian_mono.service.LeaveDetailsService;
import com.venturedive.vendian_mono.service.criteria.LeaveDetailsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.LeaveDetails}.
 */
@RestController
@RequestMapping("/api")
public class LeaveDetailsResource {

    private final Logger log = LoggerFactory.getLogger(LeaveDetailsResource.class);

    private static final String ENTITY_NAME = "leaveDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeaveDetailsService leaveDetailsService;

    private final LeaveDetailsRepository leaveDetailsRepository;

    private final LeaveDetailsQueryService leaveDetailsQueryService;

    public LeaveDetailsResource(
        LeaveDetailsService leaveDetailsService,
        LeaveDetailsRepository leaveDetailsRepository,
        LeaveDetailsQueryService leaveDetailsQueryService
    ) {
        this.leaveDetailsService = leaveDetailsService;
        this.leaveDetailsRepository = leaveDetailsRepository;
        this.leaveDetailsQueryService = leaveDetailsQueryService;
    }

    /**
     * {@code POST  /leave-details} : Create a new leaveDetails.
     *
     * @param leaveDetails the leaveDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leaveDetails, or with status {@code 400 (Bad Request)} if the leaveDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/leave-details")
    public ResponseEntity<LeaveDetails> createLeaveDetails(@Valid @RequestBody LeaveDetails leaveDetails) throws URISyntaxException {
        log.debug("REST request to save LeaveDetails : {}", leaveDetails);
        if (leaveDetails.getId() != null) {
            throw new BadRequestAlertException("A new leaveDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeaveDetails result = leaveDetailsService.save(leaveDetails);
        return ResponseEntity
            .created(new URI("/api/leave-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /leave-details/:id} : Updates an existing leaveDetails.
     *
     * @param id the id of the leaveDetails to save.
     * @param leaveDetails the leaveDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveDetails,
     * or with status {@code 400 (Bad Request)} if the leaveDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leaveDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/leave-details/{id}")
    public ResponseEntity<LeaveDetails> updateLeaveDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LeaveDetails leaveDetails
    ) throws URISyntaxException {
        log.debug("REST request to update LeaveDetails : {}, {}", id, leaveDetails);
        if (leaveDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveDetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LeaveDetails result = leaveDetailsService.update(leaveDetails);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /leave-details/:id} : Partial updates given fields of an existing leaveDetails, field will ignore if it is null
     *
     * @param id the id of the leaveDetails to save.
     * @param leaveDetails the leaveDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveDetails,
     * or with status {@code 400 (Bad Request)} if the leaveDetails is not valid,
     * or with status {@code 404 (Not Found)} if the leaveDetails is not found,
     * or with status {@code 500 (Internal Server Error)} if the leaveDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/leave-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LeaveDetails> partialUpdateLeaveDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LeaveDetails leaveDetails
    ) throws URISyntaxException {
        log.debug("REST request to partial update LeaveDetails partially : {}, {}", id, leaveDetails);
        if (leaveDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveDetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LeaveDetails> result = leaveDetailsService.partialUpdate(leaveDetails);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveDetails.getId().toString())
        );
    }

    /**
     * {@code GET  /leave-details} : get all the leaveDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leaveDetails in body.
     */
    @GetMapping("/leave-details")
    public ResponseEntity<List<LeaveDetails>> getAllLeaveDetails(
        LeaveDetailsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LeaveDetails by criteria: {}", criteria);
        Page<LeaveDetails> page = leaveDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /leave-details/count} : count all the leaveDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/leave-details/count")
    public ResponseEntity<Long> countLeaveDetails(LeaveDetailsCriteria criteria) {
        log.debug("REST request to count LeaveDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(leaveDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /leave-details/:id} : get the "id" leaveDetails.
     *
     * @param id the id of the leaveDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leaveDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/leave-details/{id}")
    public ResponseEntity<LeaveDetails> getLeaveDetails(@PathVariable Long id) {
        log.debug("REST request to get LeaveDetails : {}", id);
        Optional<LeaveDetails> leaveDetails = leaveDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leaveDetails);
    }

    /**
     * {@code DELETE  /leave-details/:id} : delete the "id" leaveDetails.
     *
     * @param id the id of the leaveDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/leave-details/{id}")
    public ResponseEntity<Void> deleteLeaveDetails(@PathVariable Long id) {
        log.debug("REST request to delete LeaveDetails : {}", id);
        leaveDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
