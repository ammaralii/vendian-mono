package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.NotificationEvents;
import com.venturedive.vendian_mono.repository.NotificationEventsRepository;
import com.venturedive.vendian_mono.service.NotificationEventsQueryService;
import com.venturedive.vendian_mono.service.NotificationEventsService;
import com.venturedive.vendian_mono.service.criteria.NotificationEventsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.NotificationEvents}.
 */
@RestController
@RequestMapping("/api")
public class NotificationEventsResource {

    private final Logger log = LoggerFactory.getLogger(NotificationEventsResource.class);

    private static final String ENTITY_NAME = "notificationEvents";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NotificationEventsService notificationEventsService;

    private final NotificationEventsRepository notificationEventsRepository;

    private final NotificationEventsQueryService notificationEventsQueryService;

    public NotificationEventsResource(
        NotificationEventsService notificationEventsService,
        NotificationEventsRepository notificationEventsRepository,
        NotificationEventsQueryService notificationEventsQueryService
    ) {
        this.notificationEventsService = notificationEventsService;
        this.notificationEventsRepository = notificationEventsRepository;
        this.notificationEventsQueryService = notificationEventsQueryService;
    }

    /**
     * {@code POST  /notification-events} : Create a new notificationEvents.
     *
     * @param notificationEvents the notificationEvents to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new notificationEvents, or with status {@code 400 (Bad Request)} if the notificationEvents has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/notification-events")
    public ResponseEntity<NotificationEvents> createNotificationEvents(@Valid @RequestBody NotificationEvents notificationEvents)
        throws URISyntaxException {
        log.debug("REST request to save NotificationEvents : {}", notificationEvents);
        if (notificationEvents.getId() != null) {
            throw new BadRequestAlertException("A new notificationEvents cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NotificationEvents result = notificationEventsService.save(notificationEvents);
        return ResponseEntity
            .created(new URI("/api/notification-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /notification-events/:id} : Updates an existing notificationEvents.
     *
     * @param id the id of the notificationEvents to save.
     * @param notificationEvents the notificationEvents to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notificationEvents,
     * or with status {@code 400 (Bad Request)} if the notificationEvents is not valid,
     * or with status {@code 500 (Internal Server Error)} if the notificationEvents couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/notification-events/{id}")
    public ResponseEntity<NotificationEvents> updateNotificationEvents(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NotificationEvents notificationEvents
    ) throws URISyntaxException {
        log.debug("REST request to update NotificationEvents : {}, {}", id, notificationEvents);
        if (notificationEvents.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, notificationEvents.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notificationEventsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NotificationEvents result = notificationEventsService.update(notificationEvents);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, notificationEvents.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /notification-events/:id} : Partial updates given fields of an existing notificationEvents, field will ignore if it is null
     *
     * @param id the id of the notificationEvents to save.
     * @param notificationEvents the notificationEvents to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notificationEvents,
     * or with status {@code 400 (Bad Request)} if the notificationEvents is not valid,
     * or with status {@code 404 (Not Found)} if the notificationEvents is not found,
     * or with status {@code 500 (Internal Server Error)} if the notificationEvents couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/notification-events/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NotificationEvents> partialUpdateNotificationEvents(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NotificationEvents notificationEvents
    ) throws URISyntaxException {
        log.debug("REST request to partial update NotificationEvents partially : {}, {}", id, notificationEvents);
        if (notificationEvents.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, notificationEvents.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notificationEventsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NotificationEvents> result = notificationEventsService.partialUpdate(notificationEvents);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, notificationEvents.getId().toString())
        );
    }

    /**
     * {@code GET  /notification-events} : get all the notificationEvents.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notificationEvents in body.
     */
    @GetMapping("/notification-events")
    public ResponseEntity<List<NotificationEvents>> getAllNotificationEvents(
        NotificationEventsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get NotificationEvents by criteria: {}", criteria);
        Page<NotificationEvents> page = notificationEventsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /notification-events/count} : count all the notificationEvents.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/notification-events/count")
    public ResponseEntity<Long> countNotificationEvents(NotificationEventsCriteria criteria) {
        log.debug("REST request to count NotificationEvents by criteria: {}", criteria);
        return ResponseEntity.ok().body(notificationEventsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /notification-events/:id} : get the "id" notificationEvents.
     *
     * @param id the id of the notificationEvents to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the notificationEvents, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/notification-events/{id}")
    public ResponseEntity<NotificationEvents> getNotificationEvents(@PathVariable Long id) {
        log.debug("REST request to get NotificationEvents : {}", id);
        Optional<NotificationEvents> notificationEvents = notificationEventsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(notificationEvents);
    }

    /**
     * {@code DELETE  /notification-events/:id} : delete the "id" notificationEvents.
     *
     * @param id the id of the notificationEvents to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/notification-events/{id}")
    public ResponseEntity<Void> deleteNotificationEvents(@PathVariable Long id) {
        log.debug("REST request to delete NotificationEvents : {}", id);
        notificationEventsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
