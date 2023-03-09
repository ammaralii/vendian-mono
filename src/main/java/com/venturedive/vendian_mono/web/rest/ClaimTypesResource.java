package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.ClaimTypes;
import com.venturedive.vendian_mono.repository.ClaimTypesRepository;
import com.venturedive.vendian_mono.service.ClaimTypesQueryService;
import com.venturedive.vendian_mono.service.ClaimTypesService;
import com.venturedive.vendian_mono.service.criteria.ClaimTypesCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.ClaimTypes}.
 */
@RestController
@RequestMapping("/api")
public class ClaimTypesResource {

    private final Logger log = LoggerFactory.getLogger(ClaimTypesResource.class);

    private static final String ENTITY_NAME = "claimTypes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClaimTypesService claimTypesService;

    private final ClaimTypesRepository claimTypesRepository;

    private final ClaimTypesQueryService claimTypesQueryService;

    public ClaimTypesResource(
        ClaimTypesService claimTypesService,
        ClaimTypesRepository claimTypesRepository,
        ClaimTypesQueryService claimTypesQueryService
    ) {
        this.claimTypesService = claimTypesService;
        this.claimTypesRepository = claimTypesRepository;
        this.claimTypesQueryService = claimTypesQueryService;
    }

    /**
     * {@code POST  /claim-types} : Create a new claimTypes.
     *
     * @param claimTypes the claimTypes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new claimTypes, or with status {@code 400 (Bad Request)} if the claimTypes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/claim-types")
    public ResponseEntity<ClaimTypes> createClaimTypes(@Valid @RequestBody ClaimTypes claimTypes) throws URISyntaxException {
        log.debug("REST request to save ClaimTypes : {}", claimTypes);
        if (claimTypes.getId() != null) {
            throw new BadRequestAlertException("A new claimTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClaimTypes result = claimTypesService.save(claimTypes);
        return ResponseEntity
            .created(new URI("/api/claim-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /claim-types/:id} : Updates an existing claimTypes.
     *
     * @param id the id of the claimTypes to save.
     * @param claimTypes the claimTypes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated claimTypes,
     * or with status {@code 400 (Bad Request)} if the claimTypes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the claimTypes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/claim-types/{id}")
    public ResponseEntity<ClaimTypes> updateClaimTypes(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ClaimTypes claimTypes
    ) throws URISyntaxException {
        log.debug("REST request to update ClaimTypes : {}, {}", id, claimTypes);
        if (claimTypes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, claimTypes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!claimTypesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClaimTypes result = claimTypesService.update(claimTypes);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, claimTypes.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /claim-types/:id} : Partial updates given fields of an existing claimTypes, field will ignore if it is null
     *
     * @param id the id of the claimTypes to save.
     * @param claimTypes the claimTypes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated claimTypes,
     * or with status {@code 400 (Bad Request)} if the claimTypes is not valid,
     * or with status {@code 404 (Not Found)} if the claimTypes is not found,
     * or with status {@code 500 (Internal Server Error)} if the claimTypes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/claim-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ClaimTypes> partialUpdateClaimTypes(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ClaimTypes claimTypes
    ) throws URISyntaxException {
        log.debug("REST request to partial update ClaimTypes partially : {}, {}", id, claimTypes);
        if (claimTypes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, claimTypes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!claimTypesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClaimTypes> result = claimTypesService.partialUpdate(claimTypes);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, claimTypes.getId().toString())
        );
    }

    /**
     * {@code GET  /claim-types} : get all the claimTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of claimTypes in body.
     */
    @GetMapping("/claim-types")
    public ResponseEntity<List<ClaimTypes>> getAllClaimTypes(
        ClaimTypesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ClaimTypes by criteria: {}", criteria);
        Page<ClaimTypes> page = claimTypesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /claim-types/count} : count all the claimTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/claim-types/count")
    public ResponseEntity<Long> countClaimTypes(ClaimTypesCriteria criteria) {
        log.debug("REST request to count ClaimTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(claimTypesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /claim-types/:id} : get the "id" claimTypes.
     *
     * @param id the id of the claimTypes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the claimTypes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/claim-types/{id}")
    public ResponseEntity<ClaimTypes> getClaimTypes(@PathVariable Long id) {
        log.debug("REST request to get ClaimTypes : {}", id);
        Optional<ClaimTypes> claimTypes = claimTypesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(claimTypes);
    }

    /**
     * {@code DELETE  /claim-types/:id} : delete the "id" claimTypes.
     *
     * @param id the id of the claimTypes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/claim-types/{id}")
    public ResponseEntity<Void> deleteClaimTypes(@PathVariable Long id) {
        log.debug("REST request to delete ClaimTypes : {}", id);
        claimTypesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
