package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.GoalGroups;
import com.venturedive.vendian_mono.repository.GoalGroupsRepository;
import com.venturedive.vendian_mono.service.GoalGroupsQueryService;
import com.venturedive.vendian_mono.service.GoalGroupsService;
import com.venturedive.vendian_mono.service.criteria.GoalGroupsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.GoalGroups}.
 */
@RestController
@RequestMapping("/api")
public class GoalGroupsResource {

    private final Logger log = LoggerFactory.getLogger(GoalGroupsResource.class);

    private static final String ENTITY_NAME = "goalGroups";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GoalGroupsService goalGroupsService;

    private final GoalGroupsRepository goalGroupsRepository;

    private final GoalGroupsQueryService goalGroupsQueryService;

    public GoalGroupsResource(
        GoalGroupsService goalGroupsService,
        GoalGroupsRepository goalGroupsRepository,
        GoalGroupsQueryService goalGroupsQueryService
    ) {
        this.goalGroupsService = goalGroupsService;
        this.goalGroupsRepository = goalGroupsRepository;
        this.goalGroupsQueryService = goalGroupsQueryService;
    }

    /**
     * {@code POST  /goal-groups} : Create a new goalGroups.
     *
     * @param goalGroups the goalGroups to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new goalGroups, or with status {@code 400 (Bad Request)} if the goalGroups has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/goal-groups")
    public ResponseEntity<GoalGroups> createGoalGroups(@Valid @RequestBody GoalGroups goalGroups) throws URISyntaxException {
        log.debug("REST request to save GoalGroups : {}", goalGroups);
        if (goalGroups.getId() != null) {
            throw new BadRequestAlertException("A new goalGroups cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GoalGroups result = goalGroupsService.save(goalGroups);
        return ResponseEntity
            .created(new URI("/api/goal-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /goal-groups/:id} : Updates an existing goalGroups.
     *
     * @param id the id of the goalGroups to save.
     * @param goalGroups the goalGroups to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated goalGroups,
     * or with status {@code 400 (Bad Request)} if the goalGroups is not valid,
     * or with status {@code 500 (Internal Server Error)} if the goalGroups couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/goal-groups/{id}")
    public ResponseEntity<GoalGroups> updateGoalGroups(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GoalGroups goalGroups
    ) throws URISyntaxException {
        log.debug("REST request to update GoalGroups : {}, {}", id, goalGroups);
        if (goalGroups.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, goalGroups.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!goalGroupsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GoalGroups result = goalGroupsService.update(goalGroups);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, goalGroups.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /goal-groups/:id} : Partial updates given fields of an existing goalGroups, field will ignore if it is null
     *
     * @param id the id of the goalGroups to save.
     * @param goalGroups the goalGroups to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated goalGroups,
     * or with status {@code 400 (Bad Request)} if the goalGroups is not valid,
     * or with status {@code 404 (Not Found)} if the goalGroups is not found,
     * or with status {@code 500 (Internal Server Error)} if the goalGroups couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/goal-groups/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GoalGroups> partialUpdateGoalGroups(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GoalGroups goalGroups
    ) throws URISyntaxException {
        log.debug("REST request to partial update GoalGroups partially : {}, {}", id, goalGroups);
        if (goalGroups.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, goalGroups.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!goalGroupsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GoalGroups> result = goalGroupsService.partialUpdate(goalGroups);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, goalGroups.getId().toString())
        );
    }

    /**
     * {@code GET  /goal-groups} : get all the goalGroups.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of goalGroups in body.
     */
    @GetMapping("/goal-groups")
    public ResponseEntity<List<GoalGroups>> getAllGoalGroups(
        GoalGroupsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get GoalGroups by criteria: {}", criteria);
        Page<GoalGroups> page = goalGroupsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /goal-groups/count} : count all the goalGroups.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/goal-groups/count")
    public ResponseEntity<Long> countGoalGroups(GoalGroupsCriteria criteria) {
        log.debug("REST request to count GoalGroups by criteria: {}", criteria);
        return ResponseEntity.ok().body(goalGroupsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /goal-groups/:id} : get the "id" goalGroups.
     *
     * @param id the id of the goalGroups to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the goalGroups, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/goal-groups/{id}")
    public ResponseEntity<GoalGroups> getGoalGroups(@PathVariable Long id) {
        log.debug("REST request to get GoalGroups : {}", id);
        Optional<GoalGroups> goalGroups = goalGroupsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(goalGroups);
    }

    /**
     * {@code DELETE  /goal-groups/:id} : delete the "id" goalGroups.
     *
     * @param id the id of the goalGroups to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/goal-groups/{id}")
    public ResponseEntity<Void> deleteGoalGroups(@PathVariable Long id) {
        log.debug("REST request to delete GoalGroups : {}", id);
        goalGroupsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
