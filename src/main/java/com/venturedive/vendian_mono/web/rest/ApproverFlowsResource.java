package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.ApproverFlows;
import com.venturedive.vendian_mono.repository.ApproverFlowsRepository;
import com.venturedive.vendian_mono.service.ApproverFlowsQueryService;
import com.venturedive.vendian_mono.service.ApproverFlowsService;
import com.venturedive.vendian_mono.service.criteria.ApproverFlowsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.ApproverFlows}.
 */
@RestController
@RequestMapping("/api")
public class ApproverFlowsResource {

    private final Logger log = LoggerFactory.getLogger(ApproverFlowsResource.class);

    private static final String ENTITY_NAME = "approverFlows";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApproverFlowsService approverFlowsService;

    private final ApproverFlowsRepository approverFlowsRepository;

    private final ApproverFlowsQueryService approverFlowsQueryService;

    public ApproverFlowsResource(
        ApproverFlowsService approverFlowsService,
        ApproverFlowsRepository approverFlowsRepository,
        ApproverFlowsQueryService approverFlowsQueryService
    ) {
        this.approverFlowsService = approverFlowsService;
        this.approverFlowsRepository = approverFlowsRepository;
        this.approverFlowsQueryService = approverFlowsQueryService;
    }

    /**
     * {@code POST  /approver-flows} : Create a new approverFlows.
     *
     * @param approverFlows the approverFlows to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new approverFlows, or with status {@code 400 (Bad Request)} if the approverFlows has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/approver-flows")
    public ResponseEntity<ApproverFlows> createApproverFlows(@Valid @RequestBody ApproverFlows approverFlows) throws URISyntaxException {
        log.debug("REST request to save ApproverFlows : {}", approverFlows);
        if (approverFlows.getId() != null) {
            throw new BadRequestAlertException("A new approverFlows cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApproverFlows result = approverFlowsService.save(approverFlows);
        return ResponseEntity
            .created(new URI("/api/approver-flows/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /approver-flows/:id} : Updates an existing approverFlows.
     *
     * @param id the id of the approverFlows to save.
     * @param approverFlows the approverFlows to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated approverFlows,
     * or with status {@code 400 (Bad Request)} if the approverFlows is not valid,
     * or with status {@code 500 (Internal Server Error)} if the approverFlows couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/approver-flows/{id}")
    public ResponseEntity<ApproverFlows> updateApproverFlows(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ApproverFlows approverFlows
    ) throws URISyntaxException {
        log.debug("REST request to update ApproverFlows : {}, {}", id, approverFlows);
        if (approverFlows.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, approverFlows.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!approverFlowsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ApproverFlows result = approverFlowsService.update(approverFlows);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, approverFlows.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /approver-flows/:id} : Partial updates given fields of an existing approverFlows, field will ignore if it is null
     *
     * @param id the id of the approverFlows to save.
     * @param approverFlows the approverFlows to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated approverFlows,
     * or with status {@code 400 (Bad Request)} if the approverFlows is not valid,
     * or with status {@code 404 (Not Found)} if the approverFlows is not found,
     * or with status {@code 500 (Internal Server Error)} if the approverFlows couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/approver-flows/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ApproverFlows> partialUpdateApproverFlows(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ApproverFlows approverFlows
    ) throws URISyntaxException {
        log.debug("REST request to partial update ApproverFlows partially : {}, {}", id, approverFlows);
        if (approverFlows.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, approverFlows.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!approverFlowsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ApproverFlows> result = approverFlowsService.partialUpdate(approverFlows);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, approverFlows.getId().toString())
        );
    }

    /**
     * {@code GET  /approver-flows} : get all the approverFlows.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of approverFlows in body.
     */
    @GetMapping("/approver-flows")
    public ResponseEntity<List<ApproverFlows>> getAllApproverFlows(
        ApproverFlowsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ApproverFlows by criteria: {}", criteria);
        Page<ApproverFlows> page = approverFlowsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /approver-flows/count} : count all the approverFlows.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/approver-flows/count")
    public ResponseEntity<Long> countApproverFlows(ApproverFlowsCriteria criteria) {
        log.debug("REST request to count ApproverFlows by criteria: {}", criteria);
        return ResponseEntity.ok().body(approverFlowsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /approver-flows/:id} : get the "id" approverFlows.
     *
     * @param id the id of the approverFlows to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the approverFlows, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/approver-flows/{id}")
    public ResponseEntity<ApproverFlows> getApproverFlows(@PathVariable Long id) {
        log.debug("REST request to get ApproverFlows : {}", id);
        Optional<ApproverFlows> approverFlows = approverFlowsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(approverFlows);
    }

    /**
     * {@code DELETE  /approver-flows/:id} : delete the "id" approverFlows.
     *
     * @param id the id of the approverFlows to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/approver-flows/{id}")
    public ResponseEntity<Void> deleteApproverFlows(@PathVariable Long id) {
        log.debug("REST request to delete ApproverFlows : {}", id);
        approverFlowsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
