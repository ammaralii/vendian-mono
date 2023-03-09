package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.Approvers;
import com.venturedive.vendian_mono.repository.ApproversRepository;
import com.venturedive.vendian_mono.service.ApproversQueryService;
import com.venturedive.vendian_mono.service.ApproversService;
import com.venturedive.vendian_mono.service.criteria.ApproversCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.Approvers}.
 */
@RestController
@RequestMapping("/api")
public class ApproversResource {

    private final Logger log = LoggerFactory.getLogger(ApproversResource.class);

    private static final String ENTITY_NAME = "approvers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApproversService approversService;

    private final ApproversRepository approversRepository;

    private final ApproversQueryService approversQueryService;

    public ApproversResource(
        ApproversService approversService,
        ApproversRepository approversRepository,
        ApproversQueryService approversQueryService
    ) {
        this.approversService = approversService;
        this.approversRepository = approversRepository;
        this.approversQueryService = approversQueryService;
    }

    /**
     * {@code POST  /approvers} : Create a new approvers.
     *
     * @param approvers the approvers to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new approvers, or with status {@code 400 (Bad Request)} if the approvers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/approvers")
    public ResponseEntity<Approvers> createApprovers(@Valid @RequestBody Approvers approvers) throws URISyntaxException {
        log.debug("REST request to save Approvers : {}", approvers);
        if (approvers.getId() != null) {
            throw new BadRequestAlertException("A new approvers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Approvers result = approversService.save(approvers);
        return ResponseEntity
            .created(new URI("/api/approvers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /approvers/:id} : Updates an existing approvers.
     *
     * @param id the id of the approvers to save.
     * @param approvers the approvers to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated approvers,
     * or with status {@code 400 (Bad Request)} if the approvers is not valid,
     * or with status {@code 500 (Internal Server Error)} if the approvers couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/approvers/{id}")
    public ResponseEntity<Approvers> updateApprovers(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Approvers approvers
    ) throws URISyntaxException {
        log.debug("REST request to update Approvers : {}, {}", id, approvers);
        if (approvers.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, approvers.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!approversRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Approvers result = approversService.update(approvers);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, approvers.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /approvers/:id} : Partial updates given fields of an existing approvers, field will ignore if it is null
     *
     * @param id the id of the approvers to save.
     * @param approvers the approvers to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated approvers,
     * or with status {@code 400 (Bad Request)} if the approvers is not valid,
     * or with status {@code 404 (Not Found)} if the approvers is not found,
     * or with status {@code 500 (Internal Server Error)} if the approvers couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/approvers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Approvers> partialUpdateApprovers(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Approvers approvers
    ) throws URISyntaxException {
        log.debug("REST request to partial update Approvers partially : {}, {}", id, approvers);
        if (approvers.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, approvers.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!approversRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Approvers> result = approversService.partialUpdate(approvers);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, approvers.getId().toString())
        );
    }

    /**
     * {@code GET  /approvers} : get all the approvers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of approvers in body.
     */
    @GetMapping("/approvers")
    public ResponseEntity<List<Approvers>> getAllApprovers(
        ApproversCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Approvers by criteria: {}", criteria);
        Page<Approvers> page = approversQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /approvers/count} : count all the approvers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/approvers/count")
    public ResponseEntity<Long> countApprovers(ApproversCriteria criteria) {
        log.debug("REST request to count Approvers by criteria: {}", criteria);
        return ResponseEntity.ok().body(approversQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /approvers/:id} : get the "id" approvers.
     *
     * @param id the id of the approvers to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the approvers, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/approvers/{id}")
    public ResponseEntity<Approvers> getApprovers(@PathVariable Long id) {
        log.debug("REST request to get Approvers : {}", id);
        Optional<Approvers> approvers = approversService.findOne(id);
        return ResponseUtil.wrapOrNotFound(approvers);
    }

    /**
     * {@code DELETE  /approvers/:id} : delete the "id" approvers.
     *
     * @param id the id of the approvers to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/approvers/{id}")
    public ResponseEntity<Void> deleteApprovers(@PathVariable Long id) {
        log.debug("REST request to delete Approvers : {}", id);
        approversService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
