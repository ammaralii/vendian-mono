package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.NotificationTemplates;
import com.venturedive.vendian_mono.repository.NotificationTemplatesRepository;
import com.venturedive.vendian_mono.service.NotificationTemplatesQueryService;
import com.venturedive.vendian_mono.service.NotificationTemplatesService;
import com.venturedive.vendian_mono.service.criteria.NotificationTemplatesCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.NotificationTemplates}.
 */
@RestController
@RequestMapping("/api")
public class NotificationTemplatesResource {

    private final Logger log = LoggerFactory.getLogger(NotificationTemplatesResource.class);

    private static final String ENTITY_NAME = "notificationTemplates";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NotificationTemplatesService notificationTemplatesService;

    private final NotificationTemplatesRepository notificationTemplatesRepository;

    private final NotificationTemplatesQueryService notificationTemplatesQueryService;

    public NotificationTemplatesResource(
        NotificationTemplatesService notificationTemplatesService,
        NotificationTemplatesRepository notificationTemplatesRepository,
        NotificationTemplatesQueryService notificationTemplatesQueryService
    ) {
        this.notificationTemplatesService = notificationTemplatesService;
        this.notificationTemplatesRepository = notificationTemplatesRepository;
        this.notificationTemplatesQueryService = notificationTemplatesQueryService;
    }

    /**
     * {@code POST  /notification-templates} : Create a new notificationTemplates.
     *
     * @param notificationTemplates the notificationTemplates to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new notificationTemplates, or with status {@code 400 (Bad Request)} if the notificationTemplates has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/notification-templates")
    public ResponseEntity<NotificationTemplates> createNotificationTemplates(
        @Valid @RequestBody NotificationTemplates notificationTemplates
    ) throws URISyntaxException {
        log.debug("REST request to save NotificationTemplates : {}", notificationTemplates);
        if (notificationTemplates.getId() != null) {
            throw new BadRequestAlertException("A new notificationTemplates cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NotificationTemplates result = notificationTemplatesService.save(notificationTemplates);
        return ResponseEntity
            .created(new URI("/api/notification-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /notification-templates/:id} : Updates an existing notificationTemplates.
     *
     * @param id the id of the notificationTemplates to save.
     * @param notificationTemplates the notificationTemplates to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notificationTemplates,
     * or with status {@code 400 (Bad Request)} if the notificationTemplates is not valid,
     * or with status {@code 500 (Internal Server Error)} if the notificationTemplates couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/notification-templates/{id}")
    public ResponseEntity<NotificationTemplates> updateNotificationTemplates(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NotificationTemplates notificationTemplates
    ) throws URISyntaxException {
        log.debug("REST request to update NotificationTemplates : {}, {}", id, notificationTemplates);
        if (notificationTemplates.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, notificationTemplates.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notificationTemplatesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NotificationTemplates result = notificationTemplatesService.update(notificationTemplates);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, notificationTemplates.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /notification-templates/:id} : Partial updates given fields of an existing notificationTemplates, field will ignore if it is null
     *
     * @param id the id of the notificationTemplates to save.
     * @param notificationTemplates the notificationTemplates to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notificationTemplates,
     * or with status {@code 400 (Bad Request)} if the notificationTemplates is not valid,
     * or with status {@code 404 (Not Found)} if the notificationTemplates is not found,
     * or with status {@code 500 (Internal Server Error)} if the notificationTemplates couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/notification-templates/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NotificationTemplates> partialUpdateNotificationTemplates(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NotificationTemplates notificationTemplates
    ) throws URISyntaxException {
        log.debug("REST request to partial update NotificationTemplates partially : {}, {}", id, notificationTemplates);
        if (notificationTemplates.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, notificationTemplates.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notificationTemplatesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NotificationTemplates> result = notificationTemplatesService.partialUpdate(notificationTemplates);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, notificationTemplates.getId().toString())
        );
    }

    /**
     * {@code GET  /notification-templates} : get all the notificationTemplates.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notificationTemplates in body.
     */
    @GetMapping("/notification-templates")
    public ResponseEntity<List<NotificationTemplates>> getAllNotificationTemplates(
        NotificationTemplatesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get NotificationTemplates by criteria: {}", criteria);
        Page<NotificationTemplates> page = notificationTemplatesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /notification-templates/count} : count all the notificationTemplates.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/notification-templates/count")
    public ResponseEntity<Long> countNotificationTemplates(NotificationTemplatesCriteria criteria) {
        log.debug("REST request to count NotificationTemplates by criteria: {}", criteria);
        return ResponseEntity.ok().body(notificationTemplatesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /notification-templates/:id} : get the "id" notificationTemplates.
     *
     * @param id the id of the notificationTemplates to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the notificationTemplates, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/notification-templates/{id}")
    public ResponseEntity<NotificationTemplates> getNotificationTemplates(@PathVariable Long id) {
        log.debug("REST request to get NotificationTemplates : {}", id);
        Optional<NotificationTemplates> notificationTemplates = notificationTemplatesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(notificationTemplates);
    }

    /**
     * {@code DELETE  /notification-templates/:id} : delete the "id" notificationTemplates.
     *
     * @param id the id of the notificationTemplates to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/notification-templates/{id}")
    public ResponseEntity<Void> deleteNotificationTemplates(@PathVariable Long id) {
        log.debug("REST request to delete NotificationTemplates : {}", id);
        notificationTemplatesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
