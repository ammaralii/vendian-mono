package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.Goals;
import com.venturedive.vendian_mono.repository.GoalsRepository;
import com.venturedive.vendian_mono.service.GoalsQueryService;
import com.venturedive.vendian_mono.service.GoalsService;
import com.venturedive.vendian_mono.service.criteria.GoalsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.Goals}.
 */
@RestController
@RequestMapping("/api")
public class GoalsResource {

    private final Logger log = LoggerFactory.getLogger(GoalsResource.class);

    private static final String ENTITY_NAME = "goals";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GoalsService goalsService;

    private final GoalsRepository goalsRepository;

    private final GoalsQueryService goalsQueryService;

    public GoalsResource(GoalsService goalsService, GoalsRepository goalsRepository, GoalsQueryService goalsQueryService) {
        this.goalsService = goalsService;
        this.goalsRepository = goalsRepository;
        this.goalsQueryService = goalsQueryService;
    }

    /**
     * {@code POST  /goals} : Create a new goals.
     *
     * @param goals the goals to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new goals, or with status {@code 400 (Bad Request)} if the goals has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/goals")
    public ResponseEntity<Goals> createGoals(@Valid @RequestBody Goals goals) throws URISyntaxException {
        log.debug("REST request to save Goals : {}", goals);
        if (goals.getId() != null) {
            throw new BadRequestAlertException("A new goals cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Goals result = goalsService.save(goals);
        return ResponseEntity
            .created(new URI("/api/goals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /goals/:id} : Updates an existing goals.
     *
     * @param id the id of the goals to save.
     * @param goals the goals to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated goals,
     * or with status {@code 400 (Bad Request)} if the goals is not valid,
     * or with status {@code 500 (Internal Server Error)} if the goals couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/goals/{id}")
    public ResponseEntity<Goals> updateGoals(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Goals goals)
        throws URISyntaxException {
        log.debug("REST request to update Goals : {}, {}", id, goals);
        if (goals.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, goals.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!goalsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Goals result = goalsService.update(goals);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, goals.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /goals/:id} : Partial updates given fields of an existing goals, field will ignore if it is null
     *
     * @param id the id of the goals to save.
     * @param goals the goals to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated goals,
     * or with status {@code 400 (Bad Request)} if the goals is not valid,
     * or with status {@code 404 (Not Found)} if the goals is not found,
     * or with status {@code 500 (Internal Server Error)} if the goals couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/goals/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Goals> partialUpdateGoals(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Goals goals
    ) throws URISyntaxException {
        log.debug("REST request to partial update Goals partially : {}, {}", id, goals);
        if (goals.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, goals.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!goalsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Goals> result = goalsService.partialUpdate(goals);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, goals.getId().toString())
        );
    }

    /**
     * {@code GET  /goals} : get all the goals.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of goals in body.
     */
    @GetMapping("/goals")
    public ResponseEntity<List<Goals>> getAllGoals(
        GoalsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Goals by criteria: {}", criteria);
        Page<Goals> page = goalsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /goals/count} : count all the goals.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/goals/count")
    public ResponseEntity<Long> countGoals(GoalsCriteria criteria) {
        log.debug("REST request to count Goals by criteria: {}", criteria);
        return ResponseEntity.ok().body(goalsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /goals/:id} : get the "id" goals.
     *
     * @param id the id of the goals to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the goals, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/goals/{id}")
    public ResponseEntity<Goals> getGoals(@PathVariable Long id) {
        log.debug("REST request to get Goals : {}", id);
        Optional<Goals> goals = goalsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(goals);
    }

    /**
     * {@code DELETE  /goals/:id} : delete the "id" goals.
     *
     * @param id the id of the goals to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/goals/{id}")
    public ResponseEntity<Void> deleteGoals(@PathVariable Long id) {
        log.debug("REST request to delete Goals : {}", id);
        goalsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
