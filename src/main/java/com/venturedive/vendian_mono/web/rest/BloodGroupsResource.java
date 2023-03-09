package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.BloodGroups;
import com.venturedive.vendian_mono.repository.BloodGroupsRepository;
import com.venturedive.vendian_mono.service.BloodGroupsQueryService;
import com.venturedive.vendian_mono.service.BloodGroupsService;
import com.venturedive.vendian_mono.service.criteria.BloodGroupsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.BloodGroups}.
 */
@RestController
@RequestMapping("/api")
public class BloodGroupsResource {

    private final Logger log = LoggerFactory.getLogger(BloodGroupsResource.class);

    private static final String ENTITY_NAME = "bloodGroups";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BloodGroupsService bloodGroupsService;

    private final BloodGroupsRepository bloodGroupsRepository;

    private final BloodGroupsQueryService bloodGroupsQueryService;

    public BloodGroupsResource(
        BloodGroupsService bloodGroupsService,
        BloodGroupsRepository bloodGroupsRepository,
        BloodGroupsQueryService bloodGroupsQueryService
    ) {
        this.bloodGroupsService = bloodGroupsService;
        this.bloodGroupsRepository = bloodGroupsRepository;
        this.bloodGroupsQueryService = bloodGroupsQueryService;
    }

    /**
     * {@code POST  /blood-groups} : Create a new bloodGroups.
     *
     * @param bloodGroups the bloodGroups to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bloodGroups, or with status {@code 400 (Bad Request)} if the bloodGroups has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/blood-groups")
    public ResponseEntity<BloodGroups> createBloodGroups(@Valid @RequestBody BloodGroups bloodGroups) throws URISyntaxException {
        log.debug("REST request to save BloodGroups : {}", bloodGroups);
        if (bloodGroups.getId() != null) {
            throw new BadRequestAlertException("A new bloodGroups cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BloodGroups result = bloodGroupsService.save(bloodGroups);
        return ResponseEntity
            .created(new URI("/api/blood-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /blood-groups/:id} : Updates an existing bloodGroups.
     *
     * @param id the id of the bloodGroups to save.
     * @param bloodGroups the bloodGroups to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bloodGroups,
     * or with status {@code 400 (Bad Request)} if the bloodGroups is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bloodGroups couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/blood-groups/{id}")
    public ResponseEntity<BloodGroups> updateBloodGroups(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BloodGroups bloodGroups
    ) throws URISyntaxException {
        log.debug("REST request to update BloodGroups : {}, {}", id, bloodGroups);
        if (bloodGroups.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bloodGroups.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bloodGroupsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BloodGroups result = bloodGroupsService.update(bloodGroups);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bloodGroups.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /blood-groups/:id} : Partial updates given fields of an existing bloodGroups, field will ignore if it is null
     *
     * @param id the id of the bloodGroups to save.
     * @param bloodGroups the bloodGroups to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bloodGroups,
     * or with status {@code 400 (Bad Request)} if the bloodGroups is not valid,
     * or with status {@code 404 (Not Found)} if the bloodGroups is not found,
     * or with status {@code 500 (Internal Server Error)} if the bloodGroups couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/blood-groups/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BloodGroups> partialUpdateBloodGroups(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BloodGroups bloodGroups
    ) throws URISyntaxException {
        log.debug("REST request to partial update BloodGroups partially : {}, {}", id, bloodGroups);
        if (bloodGroups.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bloodGroups.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bloodGroupsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BloodGroups> result = bloodGroupsService.partialUpdate(bloodGroups);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bloodGroups.getId().toString())
        );
    }

    /**
     * {@code GET  /blood-groups} : get all the bloodGroups.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bloodGroups in body.
     */
    @GetMapping("/blood-groups")
    public ResponseEntity<List<BloodGroups>> getAllBloodGroups(
        BloodGroupsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get BloodGroups by criteria: {}", criteria);
        Page<BloodGroups> page = bloodGroupsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /blood-groups/count} : count all the bloodGroups.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/blood-groups/count")
    public ResponseEntity<Long> countBloodGroups(BloodGroupsCriteria criteria) {
        log.debug("REST request to count BloodGroups by criteria: {}", criteria);
        return ResponseEntity.ok().body(bloodGroupsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /blood-groups/:id} : get the "id" bloodGroups.
     *
     * @param id the id of the bloodGroups to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bloodGroups, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/blood-groups/{id}")
    public ResponseEntity<BloodGroups> getBloodGroups(@PathVariable Long id) {
        log.debug("REST request to get BloodGroups : {}", id);
        Optional<BloodGroups> bloodGroups = bloodGroupsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bloodGroups);
    }

    /**
     * {@code DELETE  /blood-groups/:id} : delete the "id" bloodGroups.
     *
     * @param id the id of the bloodGroups to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/blood-groups/{id}")
    public ResponseEntity<Void> deleteBloodGroups(@PathVariable Long id) {
        log.debug("REST request to delete BloodGroups : {}", id);
        bloodGroupsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
