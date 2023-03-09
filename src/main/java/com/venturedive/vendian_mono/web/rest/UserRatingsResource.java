package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.UserRatings;
import com.venturedive.vendian_mono.repository.UserRatingsRepository;
import com.venturedive.vendian_mono.service.UserRatingsQueryService;
import com.venturedive.vendian_mono.service.UserRatingsService;
import com.venturedive.vendian_mono.service.criteria.UserRatingsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.UserRatings}.
 */
@RestController
@RequestMapping("/api")
public class UserRatingsResource {

    private final Logger log = LoggerFactory.getLogger(UserRatingsResource.class);

    private static final String ENTITY_NAME = "userRatings";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserRatingsService userRatingsService;

    private final UserRatingsRepository userRatingsRepository;

    private final UserRatingsQueryService userRatingsQueryService;

    public UserRatingsResource(
        UserRatingsService userRatingsService,
        UserRatingsRepository userRatingsRepository,
        UserRatingsQueryService userRatingsQueryService
    ) {
        this.userRatingsService = userRatingsService;
        this.userRatingsRepository = userRatingsRepository;
        this.userRatingsQueryService = userRatingsQueryService;
    }

    /**
     * {@code POST  /user-ratings} : Create a new userRatings.
     *
     * @param userRatings the userRatings to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userRatings, or with status {@code 400 (Bad Request)} if the userRatings has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-ratings")
    public ResponseEntity<UserRatings> createUserRatings(@Valid @RequestBody UserRatings userRatings) throws URISyntaxException {
        log.debug("REST request to save UserRatings : {}", userRatings);
        if (userRatings.getId() != null) {
            throw new BadRequestAlertException("A new userRatings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserRatings result = userRatingsService.save(userRatings);
        return ResponseEntity
            .created(new URI("/api/user-ratings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-ratings/:id} : Updates an existing userRatings.
     *
     * @param id the id of the userRatings to save.
     * @param userRatings the userRatings to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userRatings,
     * or with status {@code 400 (Bad Request)} if the userRatings is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userRatings couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-ratings/{id}")
    public ResponseEntity<UserRatings> updateUserRatings(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UserRatings userRatings
    ) throws URISyntaxException {
        log.debug("REST request to update UserRatings : {}, {}", id, userRatings);
        if (userRatings.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userRatings.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userRatingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserRatings result = userRatingsService.update(userRatings);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userRatings.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-ratings/:id} : Partial updates given fields of an existing userRatings, field will ignore if it is null
     *
     * @param id the id of the userRatings to save.
     * @param userRatings the userRatings to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userRatings,
     * or with status {@code 400 (Bad Request)} if the userRatings is not valid,
     * or with status {@code 404 (Not Found)} if the userRatings is not found,
     * or with status {@code 500 (Internal Server Error)} if the userRatings couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-ratings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserRatings> partialUpdateUserRatings(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UserRatings userRatings
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserRatings partially : {}, {}", id, userRatings);
        if (userRatings.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userRatings.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userRatingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserRatings> result = userRatingsService.partialUpdate(userRatings);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userRatings.getId().toString())
        );
    }

    /**
     * {@code GET  /user-ratings} : get all the userRatings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userRatings in body.
     */
    @GetMapping("/user-ratings")
    public ResponseEntity<List<UserRatings>> getAllUserRatings(
        UserRatingsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get UserRatings by criteria: {}", criteria);
        Page<UserRatings> page = userRatingsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-ratings/count} : count all the userRatings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/user-ratings/count")
    public ResponseEntity<Long> countUserRatings(UserRatingsCriteria criteria) {
        log.debug("REST request to count UserRatings by criteria: {}", criteria);
        return ResponseEntity.ok().body(userRatingsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /user-ratings/:id} : get the "id" userRatings.
     *
     * @param id the id of the userRatings to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userRatings, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-ratings/{id}")
    public ResponseEntity<UserRatings> getUserRatings(@PathVariable Long id) {
        log.debug("REST request to get UserRatings : {}", id);
        Optional<UserRatings> userRatings = userRatingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userRatings);
    }

    /**
     * {@code DELETE  /user-ratings/:id} : delete the "id" userRatings.
     *
     * @param id the id of the userRatings to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-ratings/{id}")
    public ResponseEntity<Void> deleteUserRatings(@PathVariable Long id) {
        log.debug("REST request to delete UserRatings : {}", id);
        userRatingsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
