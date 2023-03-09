package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.Leaves;
import com.venturedive.vendian_mono.repository.LeavesRepository;
import com.venturedive.vendian_mono.service.LeavesQueryService;
import com.venturedive.vendian_mono.service.LeavesService;
import com.venturedive.vendian_mono.service.criteria.LeavesCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.Leaves}.
 */
@RestController
@RequestMapping("/api")
public class LeavesResource {

    private final Logger log = LoggerFactory.getLogger(LeavesResource.class);

    private static final String ENTITY_NAME = "leaves";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeavesService leavesService;

    private final LeavesRepository leavesRepository;

    private final LeavesQueryService leavesQueryService;

    public LeavesResource(LeavesService leavesService, LeavesRepository leavesRepository, LeavesQueryService leavesQueryService) {
        this.leavesService = leavesService;
        this.leavesRepository = leavesRepository;
        this.leavesQueryService = leavesQueryService;
    }

    /**
     * {@code POST  /leaves} : Create a new leaves.
     *
     * @param leaves the leaves to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leaves, or with status {@code 400 (Bad Request)} if the leaves has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/leaves")
    public ResponseEntity<Leaves> createLeaves(@Valid @RequestBody Leaves leaves) throws URISyntaxException {
        log.debug("REST request to save Leaves : {}", leaves);
        if (leaves.getId() != null) {
            throw new BadRequestAlertException("A new leaves cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Leaves result = leavesService.save(leaves);
        return ResponseEntity
            .created(new URI("/api/leaves/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /leaves/:id} : Updates an existing leaves.
     *
     * @param id the id of the leaves to save.
     * @param leaves the leaves to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaves,
     * or with status {@code 400 (Bad Request)} if the leaves is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leaves couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/leaves/{id}")
    public ResponseEntity<Leaves> updateLeaves(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Leaves leaves
    ) throws URISyntaxException {
        log.debug("REST request to update Leaves : {}, {}", id, leaves);
        if (leaves.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaves.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leavesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Leaves result = leavesService.update(leaves);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaves.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /leaves/:id} : Partial updates given fields of an existing leaves, field will ignore if it is null
     *
     * @param id the id of the leaves to save.
     * @param leaves the leaves to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaves,
     * or with status {@code 400 (Bad Request)} if the leaves is not valid,
     * or with status {@code 404 (Not Found)} if the leaves is not found,
     * or with status {@code 500 (Internal Server Error)} if the leaves couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/leaves/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Leaves> partialUpdateLeaves(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Leaves leaves
    ) throws URISyntaxException {
        log.debug("REST request to partial update Leaves partially : {}, {}", id, leaves);
        if (leaves.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaves.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leavesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Leaves> result = leavesService.partialUpdate(leaves);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaves.getId().toString())
        );
    }

    /**
     * {@code GET  /leaves} : get all the leaves.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leaves in body.
     */
    @GetMapping("/leaves")
    public ResponseEntity<List<Leaves>> getAllLeaves(
        LeavesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Leaves by criteria: {}", criteria);
        Page<Leaves> page = leavesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /leaves/count} : count all the leaves.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/leaves/count")
    public ResponseEntity<Long> countLeaves(LeavesCriteria criteria) {
        log.debug("REST request to count Leaves by criteria: {}", criteria);
        return ResponseEntity.ok().body(leavesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /leaves/:id} : get the "id" leaves.
     *
     * @param id the id of the leaves to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leaves, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/leaves/{id}")
    public ResponseEntity<Leaves> getLeaves(@PathVariable Long id) {
        log.debug("REST request to get Leaves : {}", id);
        Optional<Leaves> leaves = leavesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leaves);
    }

    /**
     * {@code DELETE  /leaves/:id} : delete the "id" leaves.
     *
     * @param id the id of the leaves to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/leaves/{id}")
    public ResponseEntity<Void> deleteLeaves(@PathVariable Long id) {
        log.debug("REST request to delete Leaves : {}", id);
        leavesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
