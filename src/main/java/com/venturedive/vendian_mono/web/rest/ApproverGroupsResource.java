package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.ApproverGroups;
import com.venturedive.vendian_mono.repository.ApproverGroupsRepository;
import com.venturedive.vendian_mono.service.ApproverGroupsQueryService;
import com.venturedive.vendian_mono.service.ApproverGroupsService;
import com.venturedive.vendian_mono.service.criteria.ApproverGroupsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.ApproverGroups}.
 */
@RestController
@RequestMapping("/api")
public class ApproverGroupsResource {

    private final Logger log = LoggerFactory.getLogger(ApproverGroupsResource.class);

    private static final String ENTITY_NAME = "approverGroups";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApproverGroupsService approverGroupsService;

    private final ApproverGroupsRepository approverGroupsRepository;

    private final ApproverGroupsQueryService approverGroupsQueryService;

    public ApproverGroupsResource(
        ApproverGroupsService approverGroupsService,
        ApproverGroupsRepository approverGroupsRepository,
        ApproverGroupsQueryService approverGroupsQueryService
    ) {
        this.approverGroupsService = approverGroupsService;
        this.approverGroupsRepository = approverGroupsRepository;
        this.approverGroupsQueryService = approverGroupsQueryService;
    }

    /**
     * {@code POST  /approver-groups} : Create a new approverGroups.
     *
     * @param approverGroups the approverGroups to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new approverGroups, or with status {@code 400 (Bad Request)} if the approverGroups has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/approver-groups")
    public ResponseEntity<ApproverGroups> createApproverGroups(@Valid @RequestBody ApproverGroups approverGroups)
        throws URISyntaxException {
        log.debug("REST request to save ApproverGroups : {}", approverGroups);
        if (approverGroups.getId() != null) {
            throw new BadRequestAlertException("A new approverGroups cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApproverGroups result = approverGroupsService.save(approverGroups);
        return ResponseEntity
            .created(new URI("/api/approver-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /approver-groups/:id} : Updates an existing approverGroups.
     *
     * @param id the id of the approverGroups to save.
     * @param approverGroups the approverGroups to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated approverGroups,
     * or with status {@code 400 (Bad Request)} if the approverGroups is not valid,
     * or with status {@code 500 (Internal Server Error)} if the approverGroups couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/approver-groups/{id}")
    public ResponseEntity<ApproverGroups> updateApproverGroups(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ApproverGroups approverGroups
    ) throws URISyntaxException {
        log.debug("REST request to update ApproverGroups : {}, {}", id, approverGroups);
        if (approverGroups.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, approverGroups.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!approverGroupsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ApproverGroups result = approverGroupsService.update(approverGroups);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, approverGroups.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /approver-groups/:id} : Partial updates given fields of an existing approverGroups, field will ignore if it is null
     *
     * @param id the id of the approverGroups to save.
     * @param approverGroups the approverGroups to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated approverGroups,
     * or with status {@code 400 (Bad Request)} if the approverGroups is not valid,
     * or with status {@code 404 (Not Found)} if the approverGroups is not found,
     * or with status {@code 500 (Internal Server Error)} if the approverGroups couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/approver-groups/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ApproverGroups> partialUpdateApproverGroups(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ApproverGroups approverGroups
    ) throws URISyntaxException {
        log.debug("REST request to partial update ApproverGroups partially : {}, {}", id, approverGroups);
        if (approverGroups.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, approverGroups.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!approverGroupsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ApproverGroups> result = approverGroupsService.partialUpdate(approverGroups);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, approverGroups.getId().toString())
        );
    }

    /**
     * {@code GET  /approver-groups} : get all the approverGroups.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of approverGroups in body.
     */
    @GetMapping("/approver-groups")
    public ResponseEntity<List<ApproverGroups>> getAllApproverGroups(
        ApproverGroupsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ApproverGroups by criteria: {}", criteria);
        Page<ApproverGroups> page = approverGroupsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /approver-groups/count} : count all the approverGroups.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/approver-groups/count")
    public ResponseEntity<Long> countApproverGroups(ApproverGroupsCriteria criteria) {
        log.debug("REST request to count ApproverGroups by criteria: {}", criteria);
        return ResponseEntity.ok().body(approverGroupsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /approver-groups/:id} : get the "id" approverGroups.
     *
     * @param id the id of the approverGroups to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the approverGroups, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/approver-groups/{id}")
    public ResponseEntity<ApproverGroups> getApproverGroups(@PathVariable Long id) {
        log.debug("REST request to get ApproverGroups : {}", id);
        Optional<ApproverGroups> approverGroups = approverGroupsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(approverGroups);
    }

    /**
     * {@code DELETE  /approver-groups/:id} : delete the "id" approverGroups.
     *
     * @param id the id of the approverGroups to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/approver-groups/{id}")
    public ResponseEntity<Void> deleteApproverGroups(@PathVariable Long id) {
        log.debug("REST request to delete ApproverGroups : {}", id);
        approverGroupsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
