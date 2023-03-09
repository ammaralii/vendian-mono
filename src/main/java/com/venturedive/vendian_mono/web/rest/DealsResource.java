package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.Deals;
import com.venturedive.vendian_mono.repository.DealsRepository;
import com.venturedive.vendian_mono.service.DealsQueryService;
import com.venturedive.vendian_mono.service.DealsService;
import com.venturedive.vendian_mono.service.criteria.DealsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.Deals}.
 */
@RestController
@RequestMapping("/api")
public class DealsResource {

    private final Logger log = LoggerFactory.getLogger(DealsResource.class);

    private static final String ENTITY_NAME = "deals";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DealsService dealsService;

    private final DealsRepository dealsRepository;

    private final DealsQueryService dealsQueryService;

    public DealsResource(DealsService dealsService, DealsRepository dealsRepository, DealsQueryService dealsQueryService) {
        this.dealsService = dealsService;
        this.dealsRepository = dealsRepository;
        this.dealsQueryService = dealsQueryService;
    }

    /**
     * {@code POST  /deals} : Create a new deals.
     *
     * @param deals the deals to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deals, or with status {@code 400 (Bad Request)} if the deals has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/deals")
    public ResponseEntity<Deals> createDeals(@Valid @RequestBody Deals deals) throws URISyntaxException {
        log.debug("REST request to save Deals : {}", deals);
        if (deals.getId() != null) {
            throw new BadRequestAlertException("A new deals cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Deals result = dealsService.save(deals);
        return ResponseEntity
            .created(new URI("/api/deals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /deals/:id} : Updates an existing deals.
     *
     * @param id the id of the deals to save.
     * @param deals the deals to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deals,
     * or with status {@code 400 (Bad Request)} if the deals is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deals couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/deals/{id}")
    public ResponseEntity<Deals> updateDeals(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Deals deals)
        throws URISyntaxException {
        log.debug("REST request to update Deals : {}, {}", id, deals);
        if (deals.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deals.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dealsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Deals result = dealsService.update(deals);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, deals.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /deals/:id} : Partial updates given fields of an existing deals, field will ignore if it is null
     *
     * @param id the id of the deals to save.
     * @param deals the deals to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deals,
     * or with status {@code 400 (Bad Request)} if the deals is not valid,
     * or with status {@code 404 (Not Found)} if the deals is not found,
     * or with status {@code 500 (Internal Server Error)} if the deals couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/deals/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Deals> partialUpdateDeals(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Deals deals
    ) throws URISyntaxException {
        log.debug("REST request to partial update Deals partially : {}, {}", id, deals);
        if (deals.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deals.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dealsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Deals> result = dealsService.partialUpdate(deals);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, deals.getId().toString())
        );
    }

    /**
     * {@code GET  /deals} : get all the deals.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deals in body.
     */
    @GetMapping("/deals")
    public ResponseEntity<List<Deals>> getAllDeals(
        DealsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Deals by criteria: {}", criteria);
        Page<Deals> page = dealsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /deals/count} : count all the deals.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/deals/count")
    public ResponseEntity<Long> countDeals(DealsCriteria criteria) {
        log.debug("REST request to count Deals by criteria: {}", criteria);
        return ResponseEntity.ok().body(dealsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /deals/:id} : get the "id" deals.
     *
     * @param id the id of the deals to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deals, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/deals/{id}")
    public ResponseEntity<Deals> getDeals(@PathVariable Long id) {
        log.debug("REST request to get Deals : {}", id);
        Optional<Deals> deals = dealsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(deals);
    }

    /**
     * {@code DELETE  /deals/:id} : delete the "id" deals.
     *
     * @param id the id of the deals to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/deals/{id}")
    public ResponseEntity<Void> deleteDeals(@PathVariable Long id) {
        log.debug("REST request to delete Deals : {}", id);
        dealsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
