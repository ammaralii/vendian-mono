package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.DealEvents;
import com.venturedive.vendian_mono.repository.DealEventsRepository;
import com.venturedive.vendian_mono.service.DealEventsQueryService;
import com.venturedive.vendian_mono.service.DealEventsService;
import com.venturedive.vendian_mono.service.criteria.DealEventsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.DealEvents}.
 */
@RestController
@RequestMapping("/api")
public class DealEventsResource {

    private final Logger log = LoggerFactory.getLogger(DealEventsResource.class);

    private static final String ENTITY_NAME = "dealEvents";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DealEventsService dealEventsService;

    private final DealEventsRepository dealEventsRepository;

    private final DealEventsQueryService dealEventsQueryService;

    public DealEventsResource(
        DealEventsService dealEventsService,
        DealEventsRepository dealEventsRepository,
        DealEventsQueryService dealEventsQueryService
    ) {
        this.dealEventsService = dealEventsService;
        this.dealEventsRepository = dealEventsRepository;
        this.dealEventsQueryService = dealEventsQueryService;
    }

    /**
     * {@code POST  /deal-events} : Create a new dealEvents.
     *
     * @param dealEvents the dealEvents to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dealEvents, or with status {@code 400 (Bad Request)} if the dealEvents has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/deal-events")
    public ResponseEntity<DealEvents> createDealEvents(@Valid @RequestBody DealEvents dealEvents) throws URISyntaxException {
        log.debug("REST request to save DealEvents : {}", dealEvents);
        if (dealEvents.getId() != null) {
            throw new BadRequestAlertException("A new dealEvents cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DealEvents result = dealEventsService.save(dealEvents);
        return ResponseEntity
            .created(new URI("/api/deal-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /deal-events/:id} : Updates an existing dealEvents.
     *
     * @param id the id of the dealEvents to save.
     * @param dealEvents the dealEvents to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dealEvents,
     * or with status {@code 400 (Bad Request)} if the dealEvents is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dealEvents couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/deal-events/{id}")
    public ResponseEntity<DealEvents> updateDealEvents(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DealEvents dealEvents
    ) throws URISyntaxException {
        log.debug("REST request to update DealEvents : {}, {}", id, dealEvents);
        if (dealEvents.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dealEvents.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dealEventsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DealEvents result = dealEventsService.update(dealEvents);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dealEvents.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /deal-events/:id} : Partial updates given fields of an existing dealEvents, field will ignore if it is null
     *
     * @param id the id of the dealEvents to save.
     * @param dealEvents the dealEvents to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dealEvents,
     * or with status {@code 400 (Bad Request)} if the dealEvents is not valid,
     * or with status {@code 404 (Not Found)} if the dealEvents is not found,
     * or with status {@code 500 (Internal Server Error)} if the dealEvents couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/deal-events/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DealEvents> partialUpdateDealEvents(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DealEvents dealEvents
    ) throws URISyntaxException {
        log.debug("REST request to partial update DealEvents partially : {}, {}", id, dealEvents);
        if (dealEvents.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dealEvents.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dealEventsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DealEvents> result = dealEventsService.partialUpdate(dealEvents);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dealEvents.getId().toString())
        );
    }

    /**
     * {@code GET  /deal-events} : get all the dealEvents.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dealEvents in body.
     */
    @GetMapping("/deal-events")
    public ResponseEntity<List<DealEvents>> getAllDealEvents(
        DealEventsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get DealEvents by criteria: {}", criteria);
        Page<DealEvents> page = dealEventsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /deal-events/count} : count all the dealEvents.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/deal-events/count")
    public ResponseEntity<Long> countDealEvents(DealEventsCriteria criteria) {
        log.debug("REST request to count DealEvents by criteria: {}", criteria);
        return ResponseEntity.ok().body(dealEventsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /deal-events/:id} : get the "id" dealEvents.
     *
     * @param id the id of the dealEvents to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dealEvents, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/deal-events/{id}")
    public ResponseEntity<DealEvents> getDealEvents(@PathVariable Long id) {
        log.debug("REST request to get DealEvents : {}", id);
        Optional<DealEvents> dealEvents = dealEventsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dealEvents);
    }

    /**
     * {@code DELETE  /deal-events/:id} : delete the "id" dealEvents.
     *
     * @param id the id of the dealEvents to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/deal-events/{id}")
    public ResponseEntity<Void> deleteDealEvents(@PathVariable Long id) {
        log.debug("REST request to delete DealEvents : {}", id);
        dealEventsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
