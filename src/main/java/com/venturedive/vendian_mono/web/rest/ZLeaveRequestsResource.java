package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.ZLeaveRequests;
import com.venturedive.vendian_mono.repository.ZLeaveRequestsRepository;
import com.venturedive.vendian_mono.service.ZLeaveRequestsQueryService;
import com.venturedive.vendian_mono.service.ZLeaveRequestsService;
import com.venturedive.vendian_mono.service.criteria.ZLeaveRequestsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.ZLeaveRequests}.
 */
@RestController
@RequestMapping("/api")
public class ZLeaveRequestsResource {

    private final Logger log = LoggerFactory.getLogger(ZLeaveRequestsResource.class);

    private static final String ENTITY_NAME = "zLeaveRequests";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ZLeaveRequestsService zLeaveRequestsService;

    private final ZLeaveRequestsRepository zLeaveRequestsRepository;

    private final ZLeaveRequestsQueryService zLeaveRequestsQueryService;

    public ZLeaveRequestsResource(
        ZLeaveRequestsService zLeaveRequestsService,
        ZLeaveRequestsRepository zLeaveRequestsRepository,
        ZLeaveRequestsQueryService zLeaveRequestsQueryService
    ) {
        this.zLeaveRequestsService = zLeaveRequestsService;
        this.zLeaveRequestsRepository = zLeaveRequestsRepository;
        this.zLeaveRequestsQueryService = zLeaveRequestsQueryService;
    }

    /**
     * {@code POST  /z-leave-requests} : Create a new zLeaveRequests.
     *
     * @param zLeaveRequests the zLeaveRequests to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new zLeaveRequests, or with status {@code 400 (Bad Request)} if the zLeaveRequests has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/z-leave-requests")
    public ResponseEntity<ZLeaveRequests> createZLeaveRequests(@Valid @RequestBody ZLeaveRequests zLeaveRequests)
        throws URISyntaxException {
        log.debug("REST request to save ZLeaveRequests : {}", zLeaveRequests);
        if (zLeaveRequests.getId() != null) {
            throw new BadRequestAlertException("A new zLeaveRequests cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ZLeaveRequests result = zLeaveRequestsService.save(zLeaveRequests);
        return ResponseEntity
            .created(new URI("/api/z-leave-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /z-leave-requests/:id} : Updates an existing zLeaveRequests.
     *
     * @param id the id of the zLeaveRequests to save.
     * @param zLeaveRequests the zLeaveRequests to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated zLeaveRequests,
     * or with status {@code 400 (Bad Request)} if the zLeaveRequests is not valid,
     * or with status {@code 500 (Internal Server Error)} if the zLeaveRequests couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/z-leave-requests/{id}")
    public ResponseEntity<ZLeaveRequests> updateZLeaveRequests(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ZLeaveRequests zLeaveRequests
    ) throws URISyntaxException {
        log.debug("REST request to update ZLeaveRequests : {}, {}", id, zLeaveRequests);
        if (zLeaveRequests.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, zLeaveRequests.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!zLeaveRequestsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ZLeaveRequests result = zLeaveRequestsService.update(zLeaveRequests);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, zLeaveRequests.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /z-leave-requests/:id} : Partial updates given fields of an existing zLeaveRequests, field will ignore if it is null
     *
     * @param id the id of the zLeaveRequests to save.
     * @param zLeaveRequests the zLeaveRequests to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated zLeaveRequests,
     * or with status {@code 400 (Bad Request)} if the zLeaveRequests is not valid,
     * or with status {@code 404 (Not Found)} if the zLeaveRequests is not found,
     * or with status {@code 500 (Internal Server Error)} if the zLeaveRequests couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/z-leave-requests/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ZLeaveRequests> partialUpdateZLeaveRequests(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ZLeaveRequests zLeaveRequests
    ) throws URISyntaxException {
        log.debug("REST request to partial update ZLeaveRequests partially : {}, {}", id, zLeaveRequests);
        if (zLeaveRequests.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, zLeaveRequests.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!zLeaveRequestsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ZLeaveRequests> result = zLeaveRequestsService.partialUpdate(zLeaveRequests);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, zLeaveRequests.getId().toString())
        );
    }

    /**
     * {@code GET  /z-leave-requests} : get all the zLeaveRequests.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of zLeaveRequests in body.
     */
    @GetMapping("/z-leave-requests")
    public ResponseEntity<List<ZLeaveRequests>> getAllZLeaveRequests(
        ZLeaveRequestsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ZLeaveRequests by criteria: {}", criteria);
        Page<ZLeaveRequests> page = zLeaveRequestsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /z-leave-requests/count} : count all the zLeaveRequests.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/z-leave-requests/count")
    public ResponseEntity<Long> countZLeaveRequests(ZLeaveRequestsCriteria criteria) {
        log.debug("REST request to count ZLeaveRequests by criteria: {}", criteria);
        return ResponseEntity.ok().body(zLeaveRequestsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /z-leave-requests/:id} : get the "id" zLeaveRequests.
     *
     * @param id the id of the zLeaveRequests to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the zLeaveRequests, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/z-leave-requests/{id}")
    public ResponseEntity<ZLeaveRequests> getZLeaveRequests(@PathVariable Long id) {
        log.debug("REST request to get ZLeaveRequests : {}", id);
        Optional<ZLeaveRequests> zLeaveRequests = zLeaveRequestsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(zLeaveRequests);
    }

    /**
     * {@code DELETE  /z-leave-requests/:id} : delete the "id" zLeaveRequests.
     *
     * @param id the id of the zLeaveRequests to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/z-leave-requests/{id}")
    public ResponseEntity<Void> deleteZLeaveRequests(@PathVariable Long id) {
        log.debug("REST request to delete ZLeaveRequests : {}", id);
        zLeaveRequestsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
