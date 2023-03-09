package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.UserGoals;
import com.venturedive.vendian_mono.repository.UserGoalsRepository;
import com.venturedive.vendian_mono.service.UserGoalsQueryService;
import com.venturedive.vendian_mono.service.UserGoalsService;
import com.venturedive.vendian_mono.service.criteria.UserGoalsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.UserGoals}.
 */
@RestController
@RequestMapping("/api")
public class UserGoalsResource {

    private final Logger log = LoggerFactory.getLogger(UserGoalsResource.class);

    private static final String ENTITY_NAME = "userGoals";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserGoalsService userGoalsService;

    private final UserGoalsRepository userGoalsRepository;

    private final UserGoalsQueryService userGoalsQueryService;

    public UserGoalsResource(
        UserGoalsService userGoalsService,
        UserGoalsRepository userGoalsRepository,
        UserGoalsQueryService userGoalsQueryService
    ) {
        this.userGoalsService = userGoalsService;
        this.userGoalsRepository = userGoalsRepository;
        this.userGoalsQueryService = userGoalsQueryService;
    }

    /**
     * {@code POST  /user-goals} : Create a new userGoals.
     *
     * @param userGoals the userGoals to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userGoals, or with status {@code 400 (Bad Request)} if the userGoals has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-goals")
    public ResponseEntity<UserGoals> createUserGoals(@Valid @RequestBody UserGoals userGoals) throws URISyntaxException {
        log.debug("REST request to save UserGoals : {}", userGoals);
        if (userGoals.getId() != null) {
            throw new BadRequestAlertException("A new userGoals cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserGoals result = userGoalsService.save(userGoals);
        return ResponseEntity
            .created(new URI("/api/user-goals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-goals/:id} : Updates an existing userGoals.
     *
     * @param id the id of the userGoals to save.
     * @param userGoals the userGoals to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userGoals,
     * or with status {@code 400 (Bad Request)} if the userGoals is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userGoals couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-goals/{id}")
    public ResponseEntity<UserGoals> updateUserGoals(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UserGoals userGoals
    ) throws URISyntaxException {
        log.debug("REST request to update UserGoals : {}, {}", id, userGoals);
        if (userGoals.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userGoals.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userGoalsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserGoals result = userGoalsService.update(userGoals);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userGoals.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-goals/:id} : Partial updates given fields of an existing userGoals, field will ignore if it is null
     *
     * @param id the id of the userGoals to save.
     * @param userGoals the userGoals to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userGoals,
     * or with status {@code 400 (Bad Request)} if the userGoals is not valid,
     * or with status {@code 404 (Not Found)} if the userGoals is not found,
     * or with status {@code 500 (Internal Server Error)} if the userGoals couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-goals/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserGoals> partialUpdateUserGoals(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UserGoals userGoals
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserGoals partially : {}, {}", id, userGoals);
        if (userGoals.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userGoals.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userGoalsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserGoals> result = userGoalsService.partialUpdate(userGoals);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userGoals.getId().toString())
        );
    }

    /**
     * {@code GET  /user-goals} : get all the userGoals.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userGoals in body.
     */
    @GetMapping("/user-goals")
    public ResponseEntity<List<UserGoals>> getAllUserGoals(
        UserGoalsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get UserGoals by criteria: {}", criteria);
        Page<UserGoals> page = userGoalsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-goals/count} : count all the userGoals.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/user-goals/count")
    public ResponseEntity<Long> countUserGoals(UserGoalsCriteria criteria) {
        log.debug("REST request to count UserGoals by criteria: {}", criteria);
        return ResponseEntity.ok().body(userGoalsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /user-goals/:id} : get the "id" userGoals.
     *
     * @param id the id of the userGoals to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userGoals, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-goals/{id}")
    public ResponseEntity<UserGoals> getUserGoals(@PathVariable Long id) {
        log.debug("REST request to get UserGoals : {}", id);
        Optional<UserGoals> userGoals = userGoalsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userGoals);
    }

    /**
     * {@code DELETE  /user-goals/:id} : delete the "id" userGoals.
     *
     * @param id the id of the userGoals to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-goals/{id}")
    public ResponseEntity<Void> deleteUserGoals(@PathVariable Long id) {
        log.debug("REST request to delete UserGoals : {}", id);
        userGoalsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
