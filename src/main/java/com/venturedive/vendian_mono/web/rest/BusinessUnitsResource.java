package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.BusinessUnits;
import com.venturedive.vendian_mono.repository.BusinessUnitsRepository;
import com.venturedive.vendian_mono.service.BusinessUnitsQueryService;
import com.venturedive.vendian_mono.service.BusinessUnitsService;
import com.venturedive.vendian_mono.service.criteria.BusinessUnitsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.BusinessUnits}.
 */
@RestController
@RequestMapping("/api")
public class BusinessUnitsResource {

    private final Logger log = LoggerFactory.getLogger(BusinessUnitsResource.class);

    private static final String ENTITY_NAME = "businessUnits";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BusinessUnitsService businessUnitsService;

    private final BusinessUnitsRepository businessUnitsRepository;

    private final BusinessUnitsQueryService businessUnitsQueryService;

    public BusinessUnitsResource(
        BusinessUnitsService businessUnitsService,
        BusinessUnitsRepository businessUnitsRepository,
        BusinessUnitsQueryService businessUnitsQueryService
    ) {
        this.businessUnitsService = businessUnitsService;
        this.businessUnitsRepository = businessUnitsRepository;
        this.businessUnitsQueryService = businessUnitsQueryService;
    }

    /**
     * {@code POST  /business-units} : Create a new businessUnits.
     *
     * @param businessUnits the businessUnits to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new businessUnits, or with status {@code 400 (Bad Request)} if the businessUnits has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/business-units")
    public ResponseEntity<BusinessUnits> createBusinessUnits(@Valid @RequestBody BusinessUnits businessUnits) throws URISyntaxException {
        log.debug("REST request to save BusinessUnits : {}", businessUnits);
        if (businessUnits.getId() != null) {
            throw new BadRequestAlertException("A new businessUnits cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BusinessUnits result = businessUnitsService.save(businessUnits);
        return ResponseEntity
            .created(new URI("/api/business-units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /business-units/:id} : Updates an existing businessUnits.
     *
     * @param id the id of the businessUnits to save.
     * @param businessUnits the businessUnits to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated businessUnits,
     * or with status {@code 400 (Bad Request)} if the businessUnits is not valid,
     * or with status {@code 500 (Internal Server Error)} if the businessUnits couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/business-units/{id}")
    public ResponseEntity<BusinessUnits> updateBusinessUnits(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BusinessUnits businessUnits
    ) throws URISyntaxException {
        log.debug("REST request to update BusinessUnits : {}, {}", id, businessUnits);
        if (businessUnits.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, businessUnits.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!businessUnitsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BusinessUnits result = businessUnitsService.update(businessUnits);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, businessUnits.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /business-units/:id} : Partial updates given fields of an existing businessUnits, field will ignore if it is null
     *
     * @param id the id of the businessUnits to save.
     * @param businessUnits the businessUnits to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated businessUnits,
     * or with status {@code 400 (Bad Request)} if the businessUnits is not valid,
     * or with status {@code 404 (Not Found)} if the businessUnits is not found,
     * or with status {@code 500 (Internal Server Error)} if the businessUnits couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/business-units/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BusinessUnits> partialUpdateBusinessUnits(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BusinessUnits businessUnits
    ) throws URISyntaxException {
        log.debug("REST request to partial update BusinessUnits partially : {}, {}", id, businessUnits);
        if (businessUnits.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, businessUnits.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!businessUnitsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BusinessUnits> result = businessUnitsService.partialUpdate(businessUnits);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, businessUnits.getId().toString())
        );
    }

    /**
     * {@code GET  /business-units} : get all the businessUnits.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of businessUnits in body.
     */
    @GetMapping("/business-units")
    public ResponseEntity<List<BusinessUnits>> getAllBusinessUnits(
        BusinessUnitsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get BusinessUnits by criteria: {}", criteria);
        Page<BusinessUnits> page = businessUnitsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /business-units/count} : count all the businessUnits.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/business-units/count")
    public ResponseEntity<Long> countBusinessUnits(BusinessUnitsCriteria criteria) {
        log.debug("REST request to count BusinessUnits by criteria: {}", criteria);
        return ResponseEntity.ok().body(businessUnitsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /business-units/:id} : get the "id" businessUnits.
     *
     * @param id the id of the businessUnits to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the businessUnits, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/business-units/{id}")
    public ResponseEntity<BusinessUnits> getBusinessUnits(@PathVariable Long id) {
        log.debug("REST request to get BusinessUnits : {}", id);
        Optional<BusinessUnits> businessUnits = businessUnitsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(businessUnits);
    }

    /**
     * {@code DELETE  /business-units/:id} : delete the "id" businessUnits.
     *
     * @param id the id of the businessUnits to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/business-units/{id}")
    public ResponseEntity<Void> deleteBusinessUnits(@PathVariable Long id) {
        log.debug("REST request to delete BusinessUnits : {}", id);
        businessUnitsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
