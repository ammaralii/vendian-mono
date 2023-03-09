package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.LeavesOlds;
import com.venturedive.vendian_mono.repository.LeavesOldsRepository;
import com.venturedive.vendian_mono.service.LeavesOldsQueryService;
import com.venturedive.vendian_mono.service.LeavesOldsService;
import com.venturedive.vendian_mono.service.criteria.LeavesOldsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.LeavesOlds}.
 */
@RestController
@RequestMapping("/api")
public class LeavesOldsResource {

    private final Logger log = LoggerFactory.getLogger(LeavesOldsResource.class);

    private static final String ENTITY_NAME = "leavesOlds";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeavesOldsService leavesOldsService;

    private final LeavesOldsRepository leavesOldsRepository;

    private final LeavesOldsQueryService leavesOldsQueryService;

    public LeavesOldsResource(
        LeavesOldsService leavesOldsService,
        LeavesOldsRepository leavesOldsRepository,
        LeavesOldsQueryService leavesOldsQueryService
    ) {
        this.leavesOldsService = leavesOldsService;
        this.leavesOldsRepository = leavesOldsRepository;
        this.leavesOldsQueryService = leavesOldsQueryService;
    }

    /**
     * {@code POST  /leaves-olds} : Create a new leavesOlds.
     *
     * @param leavesOlds the leavesOlds to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leavesOlds, or with status {@code 400 (Bad Request)} if the leavesOlds has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/leaves-olds")
    public ResponseEntity<LeavesOlds> createLeavesOlds(@Valid @RequestBody LeavesOlds leavesOlds) throws URISyntaxException {
        log.debug("REST request to save LeavesOlds : {}", leavesOlds);
        if (leavesOlds.getId() != null) {
            throw new BadRequestAlertException("A new leavesOlds cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeavesOlds result = leavesOldsService.save(leavesOlds);
        return ResponseEntity
            .created(new URI("/api/leaves-olds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /leaves-olds/:id} : Updates an existing leavesOlds.
     *
     * @param id the id of the leavesOlds to save.
     * @param leavesOlds the leavesOlds to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leavesOlds,
     * or with status {@code 400 (Bad Request)} if the leavesOlds is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leavesOlds couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/leaves-olds/{id}")
    public ResponseEntity<LeavesOlds> updateLeavesOlds(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LeavesOlds leavesOlds
    ) throws URISyntaxException {
        log.debug("REST request to update LeavesOlds : {}, {}", id, leavesOlds);
        if (leavesOlds.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leavesOlds.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leavesOldsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LeavesOlds result = leavesOldsService.update(leavesOlds);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leavesOlds.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /leaves-olds/:id} : Partial updates given fields of an existing leavesOlds, field will ignore if it is null
     *
     * @param id the id of the leavesOlds to save.
     * @param leavesOlds the leavesOlds to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leavesOlds,
     * or with status {@code 400 (Bad Request)} if the leavesOlds is not valid,
     * or with status {@code 404 (Not Found)} if the leavesOlds is not found,
     * or with status {@code 500 (Internal Server Error)} if the leavesOlds couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/leaves-olds/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LeavesOlds> partialUpdateLeavesOlds(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LeavesOlds leavesOlds
    ) throws URISyntaxException {
        log.debug("REST request to partial update LeavesOlds partially : {}, {}", id, leavesOlds);
        if (leavesOlds.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leavesOlds.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leavesOldsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LeavesOlds> result = leavesOldsService.partialUpdate(leavesOlds);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leavesOlds.getId().toString())
        );
    }

    /**
     * {@code GET  /leaves-olds} : get all the leavesOlds.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leavesOlds in body.
     */
    @GetMapping("/leaves-olds")
    public ResponseEntity<List<LeavesOlds>> getAllLeavesOlds(
        LeavesOldsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LeavesOlds by criteria: {}", criteria);
        Page<LeavesOlds> page = leavesOldsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /leaves-olds/count} : count all the leavesOlds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/leaves-olds/count")
    public ResponseEntity<Long> countLeavesOlds(LeavesOldsCriteria criteria) {
        log.debug("REST request to count LeavesOlds by criteria: {}", criteria);
        return ResponseEntity.ok().body(leavesOldsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /leaves-olds/:id} : get the "id" leavesOlds.
     *
     * @param id the id of the leavesOlds to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leavesOlds, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/leaves-olds/{id}")
    public ResponseEntity<LeavesOlds> getLeavesOlds(@PathVariable Long id) {
        log.debug("REST request to get LeavesOlds : {}", id);
        Optional<LeavesOlds> leavesOlds = leavesOldsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leavesOlds);
    }

    /**
     * {@code DELETE  /leaves-olds/:id} : delete the "id" leavesOlds.
     *
     * @param id the id of the leavesOlds to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/leaves-olds/{id}")
    public ResponseEntity<Void> deleteLeavesOlds(@PathVariable Long id) {
        log.debug("REST request to delete LeavesOlds : {}", id);
        leavesOldsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
