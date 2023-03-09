package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.DealResourceSkills;
import com.venturedive.vendian_mono.repository.DealResourceSkillsRepository;
import com.venturedive.vendian_mono.service.DealResourceSkillsQueryService;
import com.venturedive.vendian_mono.service.DealResourceSkillsService;
import com.venturedive.vendian_mono.service.criteria.DealResourceSkillsCriteria;
import com.venturedive.vendian_mono.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.DealResourceSkills}.
 */
@RestController
@RequestMapping("/api")
public class DealResourceSkillsResource {

    private final Logger log = LoggerFactory.getLogger(DealResourceSkillsResource.class);

    private static final String ENTITY_NAME = "dealResourceSkills";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DealResourceSkillsService dealResourceSkillsService;

    private final DealResourceSkillsRepository dealResourceSkillsRepository;

    private final DealResourceSkillsQueryService dealResourceSkillsQueryService;

    public DealResourceSkillsResource(
        DealResourceSkillsService dealResourceSkillsService,
        DealResourceSkillsRepository dealResourceSkillsRepository,
        DealResourceSkillsQueryService dealResourceSkillsQueryService
    ) {
        this.dealResourceSkillsService = dealResourceSkillsService;
        this.dealResourceSkillsRepository = dealResourceSkillsRepository;
        this.dealResourceSkillsQueryService = dealResourceSkillsQueryService;
    }

    /**
     * {@code POST  /deal-resource-skills} : Create a new dealResourceSkills.
     *
     * @param dealResourceSkills the dealResourceSkills to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dealResourceSkills, or with status {@code 400 (Bad Request)} if the dealResourceSkills has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/deal-resource-skills")
    public ResponseEntity<DealResourceSkills> createDealResourceSkills(@RequestBody DealResourceSkills dealResourceSkills)
        throws URISyntaxException {
        log.debug("REST request to save DealResourceSkills : {}", dealResourceSkills);
        if (dealResourceSkills.getId() != null) {
            throw new BadRequestAlertException("A new dealResourceSkills cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DealResourceSkills result = dealResourceSkillsService.save(dealResourceSkills);
        return ResponseEntity
            .created(new URI("/api/deal-resource-skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /deal-resource-skills/:id} : Updates an existing dealResourceSkills.
     *
     * @param id the id of the dealResourceSkills to save.
     * @param dealResourceSkills the dealResourceSkills to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dealResourceSkills,
     * or with status {@code 400 (Bad Request)} if the dealResourceSkills is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dealResourceSkills couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/deal-resource-skills/{id}")
    public ResponseEntity<DealResourceSkills> updateDealResourceSkills(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DealResourceSkills dealResourceSkills
    ) throws URISyntaxException {
        log.debug("REST request to update DealResourceSkills : {}, {}", id, dealResourceSkills);
        if (dealResourceSkills.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dealResourceSkills.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dealResourceSkillsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DealResourceSkills result = dealResourceSkillsService.update(dealResourceSkills);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dealResourceSkills.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /deal-resource-skills/:id} : Partial updates given fields of an existing dealResourceSkills, field will ignore if it is null
     *
     * @param id the id of the dealResourceSkills to save.
     * @param dealResourceSkills the dealResourceSkills to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dealResourceSkills,
     * or with status {@code 400 (Bad Request)} if the dealResourceSkills is not valid,
     * or with status {@code 404 (Not Found)} if the dealResourceSkills is not found,
     * or with status {@code 500 (Internal Server Error)} if the dealResourceSkills couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/deal-resource-skills/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DealResourceSkills> partialUpdateDealResourceSkills(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DealResourceSkills dealResourceSkills
    ) throws URISyntaxException {
        log.debug("REST request to partial update DealResourceSkills partially : {}, {}", id, dealResourceSkills);
        if (dealResourceSkills.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dealResourceSkills.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dealResourceSkillsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DealResourceSkills> result = dealResourceSkillsService.partialUpdate(dealResourceSkills);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dealResourceSkills.getId().toString())
        );
    }

    /**
     * {@code GET  /deal-resource-skills} : get all the dealResourceSkills.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dealResourceSkills in body.
     */
    @GetMapping("/deal-resource-skills")
    public ResponseEntity<List<DealResourceSkills>> getAllDealResourceSkills(
        DealResourceSkillsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get DealResourceSkills by criteria: {}", criteria);
        Page<DealResourceSkills> page = dealResourceSkillsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /deal-resource-skills/count} : count all the dealResourceSkills.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/deal-resource-skills/count")
    public ResponseEntity<Long> countDealResourceSkills(DealResourceSkillsCriteria criteria) {
        log.debug("REST request to count DealResourceSkills by criteria: {}", criteria);
        return ResponseEntity.ok().body(dealResourceSkillsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /deal-resource-skills/:id} : get the "id" dealResourceSkills.
     *
     * @param id the id of the dealResourceSkills to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dealResourceSkills, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/deal-resource-skills/{id}")
    public ResponseEntity<DealResourceSkills> getDealResourceSkills(@PathVariable Long id) {
        log.debug("REST request to get DealResourceSkills : {}", id);
        Optional<DealResourceSkills> dealResourceSkills = dealResourceSkillsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dealResourceSkills);
    }

    /**
     * {@code DELETE  /deal-resource-skills/:id} : delete the "id" dealResourceSkills.
     *
     * @param id the id of the dealResourceSkills to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/deal-resource-skills/{id}")
    public ResponseEntity<Void> deleteDealResourceSkills(@PathVariable Long id) {
        log.debug("REST request to delete DealResourceSkills : {}", id);
        dealResourceSkillsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
