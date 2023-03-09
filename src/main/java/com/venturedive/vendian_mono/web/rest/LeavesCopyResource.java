package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.LeavesCopy;
import com.venturedive.vendian_mono.repository.LeavesCopyRepository;
import com.venturedive.vendian_mono.service.LeavesCopyQueryService;
import com.venturedive.vendian_mono.service.LeavesCopyService;
import com.venturedive.vendian_mono.service.criteria.LeavesCopyCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.LeavesCopy}.
 */
@RestController
@RequestMapping("/api")
public class LeavesCopyResource {

    private final Logger log = LoggerFactory.getLogger(LeavesCopyResource.class);

    private static final String ENTITY_NAME = "leavesCopy";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeavesCopyService leavesCopyService;

    private final LeavesCopyRepository leavesCopyRepository;

    private final LeavesCopyQueryService leavesCopyQueryService;

    public LeavesCopyResource(
        LeavesCopyService leavesCopyService,
        LeavesCopyRepository leavesCopyRepository,
        LeavesCopyQueryService leavesCopyQueryService
    ) {
        this.leavesCopyService = leavesCopyService;
        this.leavesCopyRepository = leavesCopyRepository;
        this.leavesCopyQueryService = leavesCopyQueryService;
    }

    /**
     * {@code POST  /leaves-copies} : Create a new leavesCopy.
     *
     * @param leavesCopy the leavesCopy to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leavesCopy, or with status {@code 400 (Bad Request)} if the leavesCopy has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/leaves-copies")
    public ResponseEntity<LeavesCopy> createLeavesCopy(@Valid @RequestBody LeavesCopy leavesCopy) throws URISyntaxException {
        log.debug("REST request to save LeavesCopy : {}", leavesCopy);
        if (leavesCopy.getId() != null) {
            throw new BadRequestAlertException("A new leavesCopy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeavesCopy result = leavesCopyService.save(leavesCopy);
        return ResponseEntity
            .created(new URI("/api/leaves-copies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /leaves-copies/:id} : Updates an existing leavesCopy.
     *
     * @param id the id of the leavesCopy to save.
     * @param leavesCopy the leavesCopy to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leavesCopy,
     * or with status {@code 400 (Bad Request)} if the leavesCopy is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leavesCopy couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/leaves-copies/{id}")
    public ResponseEntity<LeavesCopy> updateLeavesCopy(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LeavesCopy leavesCopy
    ) throws URISyntaxException {
        log.debug("REST request to update LeavesCopy : {}, {}", id, leavesCopy);
        if (leavesCopy.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leavesCopy.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leavesCopyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LeavesCopy result = leavesCopyService.update(leavesCopy);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leavesCopy.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /leaves-copies/:id} : Partial updates given fields of an existing leavesCopy, field will ignore if it is null
     *
     * @param id the id of the leavesCopy to save.
     * @param leavesCopy the leavesCopy to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leavesCopy,
     * or with status {@code 400 (Bad Request)} if the leavesCopy is not valid,
     * or with status {@code 404 (Not Found)} if the leavesCopy is not found,
     * or with status {@code 500 (Internal Server Error)} if the leavesCopy couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/leaves-copies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LeavesCopy> partialUpdateLeavesCopy(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LeavesCopy leavesCopy
    ) throws URISyntaxException {
        log.debug("REST request to partial update LeavesCopy partially : {}, {}", id, leavesCopy);
        if (leavesCopy.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leavesCopy.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leavesCopyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LeavesCopy> result = leavesCopyService.partialUpdate(leavesCopy);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leavesCopy.getId().toString())
        );
    }

    /**
     * {@code GET  /leaves-copies} : get all the leavesCopies.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leavesCopies in body.
     */
    @GetMapping("/leaves-copies")
    public ResponseEntity<List<LeavesCopy>> getAllLeavesCopies(
        LeavesCopyCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LeavesCopies by criteria: {}", criteria);
        Page<LeavesCopy> page = leavesCopyQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /leaves-copies/count} : count all the leavesCopies.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/leaves-copies/count")
    public ResponseEntity<Long> countLeavesCopies(LeavesCopyCriteria criteria) {
        log.debug("REST request to count LeavesCopies by criteria: {}", criteria);
        return ResponseEntity.ok().body(leavesCopyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /leaves-copies/:id} : get the "id" leavesCopy.
     *
     * @param id the id of the leavesCopy to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leavesCopy, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/leaves-copies/{id}")
    public ResponseEntity<LeavesCopy> getLeavesCopy(@PathVariable Long id) {
        log.debug("REST request to get LeavesCopy : {}", id);
        Optional<LeavesCopy> leavesCopy = leavesCopyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leavesCopy);
    }

    /**
     * {@code DELETE  /leaves-copies/:id} : delete the "id" leavesCopy.
     *
     * @param id the id of the leavesCopy to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/leaves-copies/{id}")
    public ResponseEntity<Void> deleteLeavesCopy(@PathVariable Long id) {
        log.debug("REST request to delete LeavesCopy : {}", id);
        leavesCopyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
