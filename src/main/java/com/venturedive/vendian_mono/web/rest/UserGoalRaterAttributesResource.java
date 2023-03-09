package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.UserGoalRaterAttributes;
import com.venturedive.vendian_mono.repository.UserGoalRaterAttributesRepository;
import com.venturedive.vendian_mono.service.UserGoalRaterAttributesQueryService;
import com.venturedive.vendian_mono.service.UserGoalRaterAttributesService;
import com.venturedive.vendian_mono.service.criteria.UserGoalRaterAttributesCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.UserGoalRaterAttributes}.
 */
@RestController
@RequestMapping("/api")
public class UserGoalRaterAttributesResource {

    private final Logger log = LoggerFactory.getLogger(UserGoalRaterAttributesResource.class);

    private static final String ENTITY_NAME = "userGoalRaterAttributes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserGoalRaterAttributesService userGoalRaterAttributesService;

    private final UserGoalRaterAttributesRepository userGoalRaterAttributesRepository;

    private final UserGoalRaterAttributesQueryService userGoalRaterAttributesQueryService;

    public UserGoalRaterAttributesResource(
        UserGoalRaterAttributesService userGoalRaterAttributesService,
        UserGoalRaterAttributesRepository userGoalRaterAttributesRepository,
        UserGoalRaterAttributesQueryService userGoalRaterAttributesQueryService
    ) {
        this.userGoalRaterAttributesService = userGoalRaterAttributesService;
        this.userGoalRaterAttributesRepository = userGoalRaterAttributesRepository;
        this.userGoalRaterAttributesQueryService = userGoalRaterAttributesQueryService;
    }

    /**
     * {@code POST  /user-goal-rater-attributes} : Create a new userGoalRaterAttributes.
     *
     * @param userGoalRaterAttributes the userGoalRaterAttributes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userGoalRaterAttributes, or with status {@code 400 (Bad Request)} if the userGoalRaterAttributes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-goal-rater-attributes")
    public ResponseEntity<UserGoalRaterAttributes> createUserGoalRaterAttributes(
        @Valid @RequestBody UserGoalRaterAttributes userGoalRaterAttributes
    ) throws URISyntaxException {
        log.debug("REST request to save UserGoalRaterAttributes : {}", userGoalRaterAttributes);
        if (userGoalRaterAttributes.getId() != null) {
            throw new BadRequestAlertException("A new userGoalRaterAttributes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserGoalRaterAttributes result = userGoalRaterAttributesService.save(userGoalRaterAttributes);
        return ResponseEntity
            .created(new URI("/api/user-goal-rater-attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-goal-rater-attributes/:id} : Updates an existing userGoalRaterAttributes.
     *
     * @param id the id of the userGoalRaterAttributes to save.
     * @param userGoalRaterAttributes the userGoalRaterAttributes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userGoalRaterAttributes,
     * or with status {@code 400 (Bad Request)} if the userGoalRaterAttributes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userGoalRaterAttributes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-goal-rater-attributes/{id}")
    public ResponseEntity<UserGoalRaterAttributes> updateUserGoalRaterAttributes(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UserGoalRaterAttributes userGoalRaterAttributes
    ) throws URISyntaxException {
        log.debug("REST request to update UserGoalRaterAttributes : {}, {}", id, userGoalRaterAttributes);
        if (userGoalRaterAttributes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userGoalRaterAttributes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userGoalRaterAttributesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserGoalRaterAttributes result = userGoalRaterAttributesService.update(userGoalRaterAttributes);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userGoalRaterAttributes.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-goal-rater-attributes/:id} : Partial updates given fields of an existing userGoalRaterAttributes, field will ignore if it is null
     *
     * @param id the id of the userGoalRaterAttributes to save.
     * @param userGoalRaterAttributes the userGoalRaterAttributes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userGoalRaterAttributes,
     * or with status {@code 400 (Bad Request)} if the userGoalRaterAttributes is not valid,
     * or with status {@code 404 (Not Found)} if the userGoalRaterAttributes is not found,
     * or with status {@code 500 (Internal Server Error)} if the userGoalRaterAttributes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-goal-rater-attributes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserGoalRaterAttributes> partialUpdateUserGoalRaterAttributes(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UserGoalRaterAttributes userGoalRaterAttributes
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserGoalRaterAttributes partially : {}, {}", id, userGoalRaterAttributes);
        if (userGoalRaterAttributes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userGoalRaterAttributes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userGoalRaterAttributesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserGoalRaterAttributes> result = userGoalRaterAttributesService.partialUpdate(userGoalRaterAttributes);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userGoalRaterAttributes.getId().toString())
        );
    }

    /**
     * {@code GET  /user-goal-rater-attributes} : get all the userGoalRaterAttributes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userGoalRaterAttributes in body.
     */
    @GetMapping("/user-goal-rater-attributes")
    public ResponseEntity<List<UserGoalRaterAttributes>> getAllUserGoalRaterAttributes(
        UserGoalRaterAttributesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get UserGoalRaterAttributes by criteria: {}", criteria);
        Page<UserGoalRaterAttributes> page = userGoalRaterAttributesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-goal-rater-attributes/count} : count all the userGoalRaterAttributes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/user-goal-rater-attributes/count")
    public ResponseEntity<Long> countUserGoalRaterAttributes(UserGoalRaterAttributesCriteria criteria) {
        log.debug("REST request to count UserGoalRaterAttributes by criteria: {}", criteria);
        return ResponseEntity.ok().body(userGoalRaterAttributesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /user-goal-rater-attributes/:id} : get the "id" userGoalRaterAttributes.
     *
     * @param id the id of the userGoalRaterAttributes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userGoalRaterAttributes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-goal-rater-attributes/{id}")
    public ResponseEntity<UserGoalRaterAttributes> getUserGoalRaterAttributes(@PathVariable Long id) {
        log.debug("REST request to get UserGoalRaterAttributes : {}", id);
        Optional<UserGoalRaterAttributes> userGoalRaterAttributes = userGoalRaterAttributesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userGoalRaterAttributes);
    }

    /**
     * {@code DELETE  /user-goal-rater-attributes/:id} : delete the "id" userGoalRaterAttributes.
     *
     * @param id the id of the userGoalRaterAttributes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-goal-rater-attributes/{id}")
    public ResponseEntity<Void> deleteUserGoalRaterAttributes(@PathVariable Long id) {
        log.debug("REST request to delete UserGoalRaterAttributes : {}", id);
        userGoalRaterAttributesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
