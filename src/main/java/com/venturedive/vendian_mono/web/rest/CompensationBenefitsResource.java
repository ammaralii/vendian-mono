package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.CompensationBenefits;
import com.venturedive.vendian_mono.repository.CompensationBenefitsRepository;
import com.venturedive.vendian_mono.service.CompensationBenefitsQueryService;
import com.venturedive.vendian_mono.service.CompensationBenefitsService;
import com.venturedive.vendian_mono.service.criteria.CompensationBenefitsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.CompensationBenefits}.
 */
@RestController
@RequestMapping("/api")
public class CompensationBenefitsResource {

    private final Logger log = LoggerFactory.getLogger(CompensationBenefitsResource.class);

    private static final String ENTITY_NAME = "compensationBenefits";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompensationBenefitsService compensationBenefitsService;

    private final CompensationBenefitsRepository compensationBenefitsRepository;

    private final CompensationBenefitsQueryService compensationBenefitsQueryService;

    public CompensationBenefitsResource(
        CompensationBenefitsService compensationBenefitsService,
        CompensationBenefitsRepository compensationBenefitsRepository,
        CompensationBenefitsQueryService compensationBenefitsQueryService
    ) {
        this.compensationBenefitsService = compensationBenefitsService;
        this.compensationBenefitsRepository = compensationBenefitsRepository;
        this.compensationBenefitsQueryService = compensationBenefitsQueryService;
    }

    /**
     * {@code POST  /compensation-benefits} : Create a new compensationBenefits.
     *
     * @param compensationBenefits the compensationBenefits to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new compensationBenefits, or with status {@code 400 (Bad Request)} if the compensationBenefits has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/compensation-benefits")
    public ResponseEntity<CompensationBenefits> createCompensationBenefits(@Valid @RequestBody CompensationBenefits compensationBenefits)
        throws URISyntaxException {
        log.debug("REST request to save CompensationBenefits : {}", compensationBenefits);
        if (compensationBenefits.getId() != null) {
            throw new BadRequestAlertException("A new compensationBenefits cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompensationBenefits result = compensationBenefitsService.save(compensationBenefits);
        return ResponseEntity
            .created(new URI("/api/compensation-benefits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /compensation-benefits/:id} : Updates an existing compensationBenefits.
     *
     * @param id the id of the compensationBenefits to save.
     * @param compensationBenefits the compensationBenefits to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated compensationBenefits,
     * or with status {@code 400 (Bad Request)} if the compensationBenefits is not valid,
     * or with status {@code 500 (Internal Server Error)} if the compensationBenefits couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/compensation-benefits/{id}")
    public ResponseEntity<CompensationBenefits> updateCompensationBenefits(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CompensationBenefits compensationBenefits
    ) throws URISyntaxException {
        log.debug("REST request to update CompensationBenefits : {}, {}", id, compensationBenefits);
        if (compensationBenefits.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, compensationBenefits.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!compensationBenefitsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CompensationBenefits result = compensationBenefitsService.update(compensationBenefits);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, compensationBenefits.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /compensation-benefits/:id} : Partial updates given fields of an existing compensationBenefits, field will ignore if it is null
     *
     * @param id the id of the compensationBenefits to save.
     * @param compensationBenefits the compensationBenefits to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated compensationBenefits,
     * or with status {@code 400 (Bad Request)} if the compensationBenefits is not valid,
     * or with status {@code 404 (Not Found)} if the compensationBenefits is not found,
     * or with status {@code 500 (Internal Server Error)} if the compensationBenefits couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/compensation-benefits/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CompensationBenefits> partialUpdateCompensationBenefits(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CompensationBenefits compensationBenefits
    ) throws URISyntaxException {
        log.debug("REST request to partial update CompensationBenefits partially : {}, {}", id, compensationBenefits);
        if (compensationBenefits.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, compensationBenefits.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!compensationBenefitsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CompensationBenefits> result = compensationBenefitsService.partialUpdate(compensationBenefits);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, compensationBenefits.getId().toString())
        );
    }

    /**
     * {@code GET  /compensation-benefits} : get all the compensationBenefits.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of compensationBenefits in body.
     */
    @GetMapping("/compensation-benefits")
    public ResponseEntity<List<CompensationBenefits>> getAllCompensationBenefits(
        CompensationBenefitsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CompensationBenefits by criteria: {}", criteria);
        Page<CompensationBenefits> page = compensationBenefitsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /compensation-benefits/count} : count all the compensationBenefits.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/compensation-benefits/count")
    public ResponseEntity<Long> countCompensationBenefits(CompensationBenefitsCriteria criteria) {
        log.debug("REST request to count CompensationBenefits by criteria: {}", criteria);
        return ResponseEntity.ok().body(compensationBenefitsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /compensation-benefits/:id} : get the "id" compensationBenefits.
     *
     * @param id the id of the compensationBenefits to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the compensationBenefits, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/compensation-benefits/{id}")
    public ResponseEntity<CompensationBenefits> getCompensationBenefits(@PathVariable Long id) {
        log.debug("REST request to get CompensationBenefits : {}", id);
        Optional<CompensationBenefits> compensationBenefits = compensationBenefitsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(compensationBenefits);
    }

    /**
     * {@code DELETE  /compensation-benefits/:id} : delete the "id" compensationBenefits.
     *
     * @param id the id of the compensationBenefits to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/compensation-benefits/{id}")
    public ResponseEntity<Void> deleteCompensationBenefits(@PathVariable Long id) {
        log.debug("REST request to delete CompensationBenefits : {}", id);
        compensationBenefitsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
