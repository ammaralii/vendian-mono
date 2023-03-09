package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.DesignationJobDescriptions;
import com.venturedive.vendian_mono.repository.DesignationJobDescriptionsRepository;
import com.venturedive.vendian_mono.service.DesignationJobDescriptionsQueryService;
import com.venturedive.vendian_mono.service.DesignationJobDescriptionsService;
import com.venturedive.vendian_mono.service.criteria.DesignationJobDescriptionsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.DesignationJobDescriptions}.
 */
@RestController
@RequestMapping("/api")
public class DesignationJobDescriptionsResource {

    private final Logger log = LoggerFactory.getLogger(DesignationJobDescriptionsResource.class);

    private static final String ENTITY_NAME = "designationJobDescriptions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DesignationJobDescriptionsService designationJobDescriptionsService;

    private final DesignationJobDescriptionsRepository designationJobDescriptionsRepository;

    private final DesignationJobDescriptionsQueryService designationJobDescriptionsQueryService;

    public DesignationJobDescriptionsResource(
        DesignationJobDescriptionsService designationJobDescriptionsService,
        DesignationJobDescriptionsRepository designationJobDescriptionsRepository,
        DesignationJobDescriptionsQueryService designationJobDescriptionsQueryService
    ) {
        this.designationJobDescriptionsService = designationJobDescriptionsService;
        this.designationJobDescriptionsRepository = designationJobDescriptionsRepository;
        this.designationJobDescriptionsQueryService = designationJobDescriptionsQueryService;
    }

    /**
     * {@code POST  /designation-job-descriptions} : Create a new designationJobDescriptions.
     *
     * @param designationJobDescriptions the designationJobDescriptions to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new designationJobDescriptions, or with status {@code 400 (Bad Request)} if the designationJobDescriptions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/designation-job-descriptions")
    public ResponseEntity<DesignationJobDescriptions> createDesignationJobDescriptions(
        @Valid @RequestBody DesignationJobDescriptions designationJobDescriptions
    ) throws URISyntaxException {
        log.debug("REST request to save DesignationJobDescriptions : {}", designationJobDescriptions);
        if (designationJobDescriptions.getId() != null) {
            throw new BadRequestAlertException("A new designationJobDescriptions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DesignationJobDescriptions result = designationJobDescriptionsService.save(designationJobDescriptions);
        return ResponseEntity
            .created(new URI("/api/designation-job-descriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /designation-job-descriptions/:id} : Updates an existing designationJobDescriptions.
     *
     * @param id the id of the designationJobDescriptions to save.
     * @param designationJobDescriptions the designationJobDescriptions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated designationJobDescriptions,
     * or with status {@code 400 (Bad Request)} if the designationJobDescriptions is not valid,
     * or with status {@code 500 (Internal Server Error)} if the designationJobDescriptions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/designation-job-descriptions/{id}")
    public ResponseEntity<DesignationJobDescriptions> updateDesignationJobDescriptions(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DesignationJobDescriptions designationJobDescriptions
    ) throws URISyntaxException {
        log.debug("REST request to update DesignationJobDescriptions : {}, {}", id, designationJobDescriptions);
        if (designationJobDescriptions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, designationJobDescriptions.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!designationJobDescriptionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DesignationJobDescriptions result = designationJobDescriptionsService.update(designationJobDescriptions);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, designationJobDescriptions.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /designation-job-descriptions/:id} : Partial updates given fields of an existing designationJobDescriptions, field will ignore if it is null
     *
     * @param id the id of the designationJobDescriptions to save.
     * @param designationJobDescriptions the designationJobDescriptions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated designationJobDescriptions,
     * or with status {@code 400 (Bad Request)} if the designationJobDescriptions is not valid,
     * or with status {@code 404 (Not Found)} if the designationJobDescriptions is not found,
     * or with status {@code 500 (Internal Server Error)} if the designationJobDescriptions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/designation-job-descriptions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DesignationJobDescriptions> partialUpdateDesignationJobDescriptions(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DesignationJobDescriptions designationJobDescriptions
    ) throws URISyntaxException {
        log.debug("REST request to partial update DesignationJobDescriptions partially : {}, {}", id, designationJobDescriptions);
        if (designationJobDescriptions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, designationJobDescriptions.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!designationJobDescriptionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DesignationJobDescriptions> result = designationJobDescriptionsService.partialUpdate(designationJobDescriptions);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, designationJobDescriptions.getId().toString())
        );
    }

    /**
     * {@code GET  /designation-job-descriptions} : get all the designationJobDescriptions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of designationJobDescriptions in body.
     */
    @GetMapping("/designation-job-descriptions")
    public ResponseEntity<List<DesignationJobDescriptions>> getAllDesignationJobDescriptions(
        DesignationJobDescriptionsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get DesignationJobDescriptions by criteria: {}", criteria);
        Page<DesignationJobDescriptions> page = designationJobDescriptionsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /designation-job-descriptions/count} : count all the designationJobDescriptions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/designation-job-descriptions/count")
    public ResponseEntity<Long> countDesignationJobDescriptions(DesignationJobDescriptionsCriteria criteria) {
        log.debug("REST request to count DesignationJobDescriptions by criteria: {}", criteria);
        return ResponseEntity.ok().body(designationJobDescriptionsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /designation-job-descriptions/:id} : get the "id" designationJobDescriptions.
     *
     * @param id the id of the designationJobDescriptions to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the designationJobDescriptions, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/designation-job-descriptions/{id}")
    public ResponseEntity<DesignationJobDescriptions> getDesignationJobDescriptions(@PathVariable Long id) {
        log.debug("REST request to get DesignationJobDescriptions : {}", id);
        Optional<DesignationJobDescriptions> designationJobDescriptions = designationJobDescriptionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(designationJobDescriptions);
    }

    /**
     * {@code DELETE  /designation-job-descriptions/:id} : delete the "id" designationJobDescriptions.
     *
     * @param id the id of the designationJobDescriptions to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/designation-job-descriptions/{id}")
    public ResponseEntity<Void> deleteDesignationJobDescriptions(@PathVariable Long id) {
        log.debug("REST request to delete DesignationJobDescriptions : {}", id);
        designationJobDescriptionsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
