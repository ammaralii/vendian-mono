package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.LeaveRequestsOlds;
import com.venturedive.vendian_mono.repository.LeaveRequestsOldsRepository;
import com.venturedive.vendian_mono.service.LeaveRequestsOldsQueryService;
import com.venturedive.vendian_mono.service.LeaveRequestsOldsService;
import com.venturedive.vendian_mono.service.criteria.LeaveRequestsOldsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.LeaveRequestsOlds}.
 */
@RestController
@RequestMapping("/api")
public class LeaveRequestsOldsResource {

    private final Logger log = LoggerFactory.getLogger(LeaveRequestsOldsResource.class);

    private static final String ENTITY_NAME = "leaveRequestsOlds";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeaveRequestsOldsService leaveRequestsOldsService;

    private final LeaveRequestsOldsRepository leaveRequestsOldsRepository;

    private final LeaveRequestsOldsQueryService leaveRequestsOldsQueryService;

    public LeaveRequestsOldsResource(
        LeaveRequestsOldsService leaveRequestsOldsService,
        LeaveRequestsOldsRepository leaveRequestsOldsRepository,
        LeaveRequestsOldsQueryService leaveRequestsOldsQueryService
    ) {
        this.leaveRequestsOldsService = leaveRequestsOldsService;
        this.leaveRequestsOldsRepository = leaveRequestsOldsRepository;
        this.leaveRequestsOldsQueryService = leaveRequestsOldsQueryService;
    }

    /**
     * {@code POST  /leave-requests-olds} : Create a new leaveRequestsOlds.
     *
     * @param leaveRequestsOlds the leaveRequestsOlds to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leaveRequestsOlds, or with status {@code 400 (Bad Request)} if the leaveRequestsOlds has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/leave-requests-olds")
    public ResponseEntity<LeaveRequestsOlds> createLeaveRequestsOlds(@Valid @RequestBody LeaveRequestsOlds leaveRequestsOlds)
        throws URISyntaxException {
        log.debug("REST request to save LeaveRequestsOlds : {}", leaveRequestsOlds);
        if (leaveRequestsOlds.getId() != null) {
            throw new BadRequestAlertException("A new leaveRequestsOlds cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeaveRequestsOlds result = leaveRequestsOldsService.save(leaveRequestsOlds);
        return ResponseEntity
            .created(new URI("/api/leave-requests-olds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /leave-requests-olds/:id} : Updates an existing leaveRequestsOlds.
     *
     * @param id the id of the leaveRequestsOlds to save.
     * @param leaveRequestsOlds the leaveRequestsOlds to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveRequestsOlds,
     * or with status {@code 400 (Bad Request)} if the leaveRequestsOlds is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leaveRequestsOlds couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/leave-requests-olds/{id}")
    public ResponseEntity<LeaveRequestsOlds> updateLeaveRequestsOlds(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LeaveRequestsOlds leaveRequestsOlds
    ) throws URISyntaxException {
        log.debug("REST request to update LeaveRequestsOlds : {}, {}", id, leaveRequestsOlds);
        if (leaveRequestsOlds.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveRequestsOlds.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveRequestsOldsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LeaveRequestsOlds result = leaveRequestsOldsService.update(leaveRequestsOlds);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveRequestsOlds.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /leave-requests-olds/:id} : Partial updates given fields of an existing leaveRequestsOlds, field will ignore if it is null
     *
     * @param id the id of the leaveRequestsOlds to save.
     * @param leaveRequestsOlds the leaveRequestsOlds to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveRequestsOlds,
     * or with status {@code 400 (Bad Request)} if the leaveRequestsOlds is not valid,
     * or with status {@code 404 (Not Found)} if the leaveRequestsOlds is not found,
     * or with status {@code 500 (Internal Server Error)} if the leaveRequestsOlds couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/leave-requests-olds/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LeaveRequestsOlds> partialUpdateLeaveRequestsOlds(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LeaveRequestsOlds leaveRequestsOlds
    ) throws URISyntaxException {
        log.debug("REST request to partial update LeaveRequestsOlds partially : {}, {}", id, leaveRequestsOlds);
        if (leaveRequestsOlds.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveRequestsOlds.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveRequestsOldsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LeaveRequestsOlds> result = leaveRequestsOldsService.partialUpdate(leaveRequestsOlds);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveRequestsOlds.getId().toString())
        );
    }

    /**
     * {@code GET  /leave-requests-olds} : get all the leaveRequestsOlds.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leaveRequestsOlds in body.
     */
    @GetMapping("/leave-requests-olds")
    public ResponseEntity<List<LeaveRequestsOlds>> getAllLeaveRequestsOlds(
        LeaveRequestsOldsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LeaveRequestsOlds by criteria: {}", criteria);
        Page<LeaveRequestsOlds> page = leaveRequestsOldsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /leave-requests-olds/count} : count all the leaveRequestsOlds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/leave-requests-olds/count")
    public ResponseEntity<Long> countLeaveRequestsOlds(LeaveRequestsOldsCriteria criteria) {
        log.debug("REST request to count LeaveRequestsOlds by criteria: {}", criteria);
        return ResponseEntity.ok().body(leaveRequestsOldsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /leave-requests-olds/:id} : get the "id" leaveRequestsOlds.
     *
     * @param id the id of the leaveRequestsOlds to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leaveRequestsOlds, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/leave-requests-olds/{id}")
    public ResponseEntity<LeaveRequestsOlds> getLeaveRequestsOlds(@PathVariable Long id) {
        log.debug("REST request to get LeaveRequestsOlds : {}", id);
        Optional<LeaveRequestsOlds> leaveRequestsOlds = leaveRequestsOldsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leaveRequestsOlds);
    }

    /**
     * {@code DELETE  /leave-requests-olds/:id} : delete the "id" leaveRequestsOlds.
     *
     * @param id the id of the leaveRequestsOlds to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/leave-requests-olds/{id}")
    public ResponseEntity<Void> deleteLeaveRequestsOlds(@PathVariable Long id) {
        log.debug("REST request to delete LeaveRequestsOlds : {}", id);
        leaveRequestsOldsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
