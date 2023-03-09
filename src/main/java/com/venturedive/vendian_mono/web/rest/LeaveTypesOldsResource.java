package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.LeaveTypesOlds;
import com.venturedive.vendian_mono.repository.LeaveTypesOldsRepository;
import com.venturedive.vendian_mono.service.LeaveTypesOldsQueryService;
import com.venturedive.vendian_mono.service.LeaveTypesOldsService;
import com.venturedive.vendian_mono.service.criteria.LeaveTypesOldsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.LeaveTypesOlds}.
 */
@RestController
@RequestMapping("/api")
public class LeaveTypesOldsResource {

    private final Logger log = LoggerFactory.getLogger(LeaveTypesOldsResource.class);

    private static final String ENTITY_NAME = "leaveTypesOlds";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeaveTypesOldsService leaveTypesOldsService;

    private final LeaveTypesOldsRepository leaveTypesOldsRepository;

    private final LeaveTypesOldsQueryService leaveTypesOldsQueryService;

    public LeaveTypesOldsResource(
        LeaveTypesOldsService leaveTypesOldsService,
        LeaveTypesOldsRepository leaveTypesOldsRepository,
        LeaveTypesOldsQueryService leaveTypesOldsQueryService
    ) {
        this.leaveTypesOldsService = leaveTypesOldsService;
        this.leaveTypesOldsRepository = leaveTypesOldsRepository;
        this.leaveTypesOldsQueryService = leaveTypesOldsQueryService;
    }

    /**
     * {@code POST  /leave-types-olds} : Create a new leaveTypesOlds.
     *
     * @param leaveTypesOlds the leaveTypesOlds to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leaveTypesOlds, or with status {@code 400 (Bad Request)} if the leaveTypesOlds has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/leave-types-olds")
    public ResponseEntity<LeaveTypesOlds> createLeaveTypesOlds(@Valid @RequestBody LeaveTypesOlds leaveTypesOlds)
        throws URISyntaxException {
        log.debug("REST request to save LeaveTypesOlds : {}", leaveTypesOlds);
        if (leaveTypesOlds.getId() != null) {
            throw new BadRequestAlertException("A new leaveTypesOlds cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeaveTypesOlds result = leaveTypesOldsService.save(leaveTypesOlds);
        return ResponseEntity
            .created(new URI("/api/leave-types-olds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /leave-types-olds/:id} : Updates an existing leaveTypesOlds.
     *
     * @param id the id of the leaveTypesOlds to save.
     * @param leaveTypesOlds the leaveTypesOlds to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveTypesOlds,
     * or with status {@code 400 (Bad Request)} if the leaveTypesOlds is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leaveTypesOlds couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/leave-types-olds/{id}")
    public ResponseEntity<LeaveTypesOlds> updateLeaveTypesOlds(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LeaveTypesOlds leaveTypesOlds
    ) throws URISyntaxException {
        log.debug("REST request to update LeaveTypesOlds : {}, {}", id, leaveTypesOlds);
        if (leaveTypesOlds.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveTypesOlds.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveTypesOldsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LeaveTypesOlds result = leaveTypesOldsService.update(leaveTypesOlds);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveTypesOlds.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /leave-types-olds/:id} : Partial updates given fields of an existing leaveTypesOlds, field will ignore if it is null
     *
     * @param id the id of the leaveTypesOlds to save.
     * @param leaveTypesOlds the leaveTypesOlds to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveTypesOlds,
     * or with status {@code 400 (Bad Request)} if the leaveTypesOlds is not valid,
     * or with status {@code 404 (Not Found)} if the leaveTypesOlds is not found,
     * or with status {@code 500 (Internal Server Error)} if the leaveTypesOlds couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/leave-types-olds/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LeaveTypesOlds> partialUpdateLeaveTypesOlds(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LeaveTypesOlds leaveTypesOlds
    ) throws URISyntaxException {
        log.debug("REST request to partial update LeaveTypesOlds partially : {}, {}", id, leaveTypesOlds);
        if (leaveTypesOlds.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveTypesOlds.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveTypesOldsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LeaveTypesOlds> result = leaveTypesOldsService.partialUpdate(leaveTypesOlds);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveTypesOlds.getId().toString())
        );
    }

    /**
     * {@code GET  /leave-types-olds} : get all the leaveTypesOlds.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leaveTypesOlds in body.
     */
    @GetMapping("/leave-types-olds")
    public ResponseEntity<List<LeaveTypesOlds>> getAllLeaveTypesOlds(
        LeaveTypesOldsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LeaveTypesOlds by criteria: {}", criteria);
        Page<LeaveTypesOlds> page = leaveTypesOldsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /leave-types-olds/count} : count all the leaveTypesOlds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/leave-types-olds/count")
    public ResponseEntity<Long> countLeaveTypesOlds(LeaveTypesOldsCriteria criteria) {
        log.debug("REST request to count LeaveTypesOlds by criteria: {}", criteria);
        return ResponseEntity.ok().body(leaveTypesOldsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /leave-types-olds/:id} : get the "id" leaveTypesOlds.
     *
     * @param id the id of the leaveTypesOlds to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leaveTypesOlds, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/leave-types-olds/{id}")
    public ResponseEntity<LeaveTypesOlds> getLeaveTypesOlds(@PathVariable Long id) {
        log.debug("REST request to get LeaveTypesOlds : {}", id);
        Optional<LeaveTypesOlds> leaveTypesOlds = leaveTypesOldsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leaveTypesOlds);
    }

    /**
     * {@code DELETE  /leave-types-olds/:id} : delete the "id" leaveTypesOlds.
     *
     * @param id the id of the leaveTypesOlds to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/leave-types-olds/{id}")
    public ResponseEntity<Void> deleteLeaveTypesOlds(@PathVariable Long id) {
        log.debug("REST request to delete LeaveTypesOlds : {}", id);
        leaveTypesOldsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
