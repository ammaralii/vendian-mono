package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.Reasons;
import com.venturedive.vendian_mono.repository.ReasonsRepository;
import com.venturedive.vendian_mono.service.ReasonsQueryService;
import com.venturedive.vendian_mono.service.ReasonsService;
import com.venturedive.vendian_mono.service.criteria.ReasonsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.Reasons}.
 */
@RestController
@RequestMapping("/api")
public class ReasonsResource {

    private final Logger log = LoggerFactory.getLogger(ReasonsResource.class);

    private static final String ENTITY_NAME = "reasons";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReasonsService reasonsService;

    private final ReasonsRepository reasonsRepository;

    private final ReasonsQueryService reasonsQueryService;

    public ReasonsResource(ReasonsService reasonsService, ReasonsRepository reasonsRepository, ReasonsQueryService reasonsQueryService) {
        this.reasonsService = reasonsService;
        this.reasonsRepository = reasonsRepository;
        this.reasonsQueryService = reasonsQueryService;
    }

    /**
     * {@code POST  /reasons} : Create a new reasons.
     *
     * @param reasons the reasons to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reasons, or with status {@code 400 (Bad Request)} if the reasons has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reasons")
    public ResponseEntity<Reasons> createReasons(@Valid @RequestBody Reasons reasons) throws URISyntaxException {
        log.debug("REST request to save Reasons : {}", reasons);
        if (reasons.getId() != null) {
            throw new BadRequestAlertException("A new reasons cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Reasons result = reasonsService.save(reasons);
        return ResponseEntity
            .created(new URI("/api/reasons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reasons/:id} : Updates an existing reasons.
     *
     * @param id the id of the reasons to save.
     * @param reasons the reasons to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reasons,
     * or with status {@code 400 (Bad Request)} if the reasons is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reasons couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reasons/{id}")
    public ResponseEntity<Reasons> updateReasons(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Reasons reasons
    ) throws URISyntaxException {
        log.debug("REST request to update Reasons : {}, {}", id, reasons);
        if (reasons.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reasons.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reasonsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Reasons result = reasonsService.update(reasons);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, reasons.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /reasons/:id} : Partial updates given fields of an existing reasons, field will ignore if it is null
     *
     * @param id the id of the reasons to save.
     * @param reasons the reasons to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reasons,
     * or with status {@code 400 (Bad Request)} if the reasons is not valid,
     * or with status {@code 404 (Not Found)} if the reasons is not found,
     * or with status {@code 500 (Internal Server Error)} if the reasons couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/reasons/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Reasons> partialUpdateReasons(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Reasons reasons
    ) throws URISyntaxException {
        log.debug("REST request to partial update Reasons partially : {}, {}", id, reasons);
        if (reasons.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reasons.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reasonsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Reasons> result = reasonsService.partialUpdate(reasons);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, reasons.getId().toString())
        );
    }

    /**
     * {@code GET  /reasons} : get all the reasons.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reasons in body.
     */
    @GetMapping("/reasons")
    public ResponseEntity<List<Reasons>> getAllReasons(
        ReasonsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Reasons by criteria: {}", criteria);
        Page<Reasons> page = reasonsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /reasons/count} : count all the reasons.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/reasons/count")
    public ResponseEntity<Long> countReasons(ReasonsCriteria criteria) {
        log.debug("REST request to count Reasons by criteria: {}", criteria);
        return ResponseEntity.ok().body(reasonsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /reasons/:id} : get the "id" reasons.
     *
     * @param id the id of the reasons to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reasons, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reasons/{id}")
    public ResponseEntity<Reasons> getReasons(@PathVariable Long id) {
        log.debug("REST request to get Reasons : {}", id);
        Optional<Reasons> reasons = reasonsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reasons);
    }

    /**
     * {@code DELETE  /reasons/:id} : delete the "id" reasons.
     *
     * @param id the id of the reasons to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reasons/{id}")
    public ResponseEntity<Void> deleteReasons(@PathVariable Long id) {
        log.debug("REST request to delete Reasons : {}", id);
        reasonsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
