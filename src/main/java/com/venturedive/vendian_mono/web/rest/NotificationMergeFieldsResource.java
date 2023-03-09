package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.NotificationMergeFields;
import com.venturedive.vendian_mono.repository.NotificationMergeFieldsRepository;
import com.venturedive.vendian_mono.service.NotificationMergeFieldsQueryService;
import com.venturedive.vendian_mono.service.NotificationMergeFieldsService;
import com.venturedive.vendian_mono.service.criteria.NotificationMergeFieldsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.NotificationMergeFields}.
 */
@RestController
@RequestMapping("/api")
public class NotificationMergeFieldsResource {

    private final Logger log = LoggerFactory.getLogger(NotificationMergeFieldsResource.class);

    private static final String ENTITY_NAME = "notificationMergeFields";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NotificationMergeFieldsService notificationMergeFieldsService;

    private final NotificationMergeFieldsRepository notificationMergeFieldsRepository;

    private final NotificationMergeFieldsQueryService notificationMergeFieldsQueryService;

    public NotificationMergeFieldsResource(
        NotificationMergeFieldsService notificationMergeFieldsService,
        NotificationMergeFieldsRepository notificationMergeFieldsRepository,
        NotificationMergeFieldsQueryService notificationMergeFieldsQueryService
    ) {
        this.notificationMergeFieldsService = notificationMergeFieldsService;
        this.notificationMergeFieldsRepository = notificationMergeFieldsRepository;
        this.notificationMergeFieldsQueryService = notificationMergeFieldsQueryService;
    }

    /**
     * {@code POST  /notification-merge-fields} : Create a new notificationMergeFields.
     *
     * @param notificationMergeFields the notificationMergeFields to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new notificationMergeFields, or with status {@code 400 (Bad Request)} if the notificationMergeFields has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/notification-merge-fields")
    public ResponseEntity<NotificationMergeFields> createNotificationMergeFields(
        @Valid @RequestBody NotificationMergeFields notificationMergeFields
    ) throws URISyntaxException {
        log.debug("REST request to save NotificationMergeFields : {}", notificationMergeFields);
        if (notificationMergeFields.getId() != null) {
            throw new BadRequestAlertException("A new notificationMergeFields cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NotificationMergeFields result = notificationMergeFieldsService.save(notificationMergeFields);
        return ResponseEntity
            .created(new URI("/api/notification-merge-fields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /notification-merge-fields/:id} : Updates an existing notificationMergeFields.
     *
     * @param id the id of the notificationMergeFields to save.
     * @param notificationMergeFields the notificationMergeFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notificationMergeFields,
     * or with status {@code 400 (Bad Request)} if the notificationMergeFields is not valid,
     * or with status {@code 500 (Internal Server Error)} if the notificationMergeFields couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/notification-merge-fields/{id}")
    public ResponseEntity<NotificationMergeFields> updateNotificationMergeFields(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NotificationMergeFields notificationMergeFields
    ) throws URISyntaxException {
        log.debug("REST request to update NotificationMergeFields : {}, {}", id, notificationMergeFields);
        if (notificationMergeFields.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, notificationMergeFields.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notificationMergeFieldsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NotificationMergeFields result = notificationMergeFieldsService.update(notificationMergeFields);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, notificationMergeFields.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /notification-merge-fields/:id} : Partial updates given fields of an existing notificationMergeFields, field will ignore if it is null
     *
     * @param id the id of the notificationMergeFields to save.
     * @param notificationMergeFields the notificationMergeFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notificationMergeFields,
     * or with status {@code 400 (Bad Request)} if the notificationMergeFields is not valid,
     * or with status {@code 404 (Not Found)} if the notificationMergeFields is not found,
     * or with status {@code 500 (Internal Server Error)} if the notificationMergeFields couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/notification-merge-fields/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NotificationMergeFields> partialUpdateNotificationMergeFields(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NotificationMergeFields notificationMergeFields
    ) throws URISyntaxException {
        log.debug("REST request to partial update NotificationMergeFields partially : {}, {}", id, notificationMergeFields);
        if (notificationMergeFields.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, notificationMergeFields.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notificationMergeFieldsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NotificationMergeFields> result = notificationMergeFieldsService.partialUpdate(notificationMergeFields);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, notificationMergeFields.getId().toString())
        );
    }

    /**
     * {@code GET  /notification-merge-fields} : get all the notificationMergeFields.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notificationMergeFields in body.
     */
    @GetMapping("/notification-merge-fields")
    public ResponseEntity<List<NotificationMergeFields>> getAllNotificationMergeFields(
        NotificationMergeFieldsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get NotificationMergeFields by criteria: {}", criteria);
        Page<NotificationMergeFields> page = notificationMergeFieldsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /notification-merge-fields/count} : count all the notificationMergeFields.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/notification-merge-fields/count")
    public ResponseEntity<Long> countNotificationMergeFields(NotificationMergeFieldsCriteria criteria) {
        log.debug("REST request to count NotificationMergeFields by criteria: {}", criteria);
        return ResponseEntity.ok().body(notificationMergeFieldsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /notification-merge-fields/:id} : get the "id" notificationMergeFields.
     *
     * @param id the id of the notificationMergeFields to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the notificationMergeFields, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/notification-merge-fields/{id}")
    public ResponseEntity<NotificationMergeFields> getNotificationMergeFields(@PathVariable Long id) {
        log.debug("REST request to get NotificationMergeFields : {}", id);
        Optional<NotificationMergeFields> notificationMergeFields = notificationMergeFieldsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(notificationMergeFields);
    }

    /**
     * {@code DELETE  /notification-merge-fields/:id} : delete the "id" notificationMergeFields.
     *
     * @param id the id of the notificationMergeFields to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/notification-merge-fields/{id}")
    public ResponseEntity<Void> deleteNotificationMergeFields(@PathVariable Long id) {
        log.debug("REST request to delete NotificationMergeFields : {}", id);
        notificationMergeFieldsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
