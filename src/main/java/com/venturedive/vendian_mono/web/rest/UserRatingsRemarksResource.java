package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.UserRatingsRemarks;
import com.venturedive.vendian_mono.repository.UserRatingsRemarksRepository;
import com.venturedive.vendian_mono.service.UserRatingsRemarksQueryService;
import com.venturedive.vendian_mono.service.UserRatingsRemarksService;
import com.venturedive.vendian_mono.service.criteria.UserRatingsRemarksCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.UserRatingsRemarks}.
 */
@RestController
@RequestMapping("/api")
public class UserRatingsRemarksResource {

    private final Logger log = LoggerFactory.getLogger(UserRatingsRemarksResource.class);

    private static final String ENTITY_NAME = "userRatingsRemarks";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserRatingsRemarksService userRatingsRemarksService;

    private final UserRatingsRemarksRepository userRatingsRemarksRepository;

    private final UserRatingsRemarksQueryService userRatingsRemarksQueryService;

    public UserRatingsRemarksResource(
        UserRatingsRemarksService userRatingsRemarksService,
        UserRatingsRemarksRepository userRatingsRemarksRepository,
        UserRatingsRemarksQueryService userRatingsRemarksQueryService
    ) {
        this.userRatingsRemarksService = userRatingsRemarksService;
        this.userRatingsRemarksRepository = userRatingsRemarksRepository;
        this.userRatingsRemarksQueryService = userRatingsRemarksQueryService;
    }

    /**
     * {@code POST  /user-ratings-remarks} : Create a new userRatingsRemarks.
     *
     * @param userRatingsRemarks the userRatingsRemarks to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userRatingsRemarks, or with status {@code 400 (Bad Request)} if the userRatingsRemarks has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-ratings-remarks")
    public ResponseEntity<UserRatingsRemarks> createUserRatingsRemarks(@Valid @RequestBody UserRatingsRemarks userRatingsRemarks)
        throws URISyntaxException {
        log.debug("REST request to save UserRatingsRemarks : {}", userRatingsRemarks);
        if (userRatingsRemarks.getId() != null) {
            throw new BadRequestAlertException("A new userRatingsRemarks cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserRatingsRemarks result = userRatingsRemarksService.save(userRatingsRemarks);
        return ResponseEntity
            .created(new URI("/api/user-ratings-remarks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-ratings-remarks/:id} : Updates an existing userRatingsRemarks.
     *
     * @param id the id of the userRatingsRemarks to save.
     * @param userRatingsRemarks the userRatingsRemarks to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userRatingsRemarks,
     * or with status {@code 400 (Bad Request)} if the userRatingsRemarks is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userRatingsRemarks couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-ratings-remarks/{id}")
    public ResponseEntity<UserRatingsRemarks> updateUserRatingsRemarks(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UserRatingsRemarks userRatingsRemarks
    ) throws URISyntaxException {
        log.debug("REST request to update UserRatingsRemarks : {}, {}", id, userRatingsRemarks);
        if (userRatingsRemarks.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userRatingsRemarks.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userRatingsRemarksRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserRatingsRemarks result = userRatingsRemarksService.update(userRatingsRemarks);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userRatingsRemarks.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-ratings-remarks/:id} : Partial updates given fields of an existing userRatingsRemarks, field will ignore if it is null
     *
     * @param id the id of the userRatingsRemarks to save.
     * @param userRatingsRemarks the userRatingsRemarks to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userRatingsRemarks,
     * or with status {@code 400 (Bad Request)} if the userRatingsRemarks is not valid,
     * or with status {@code 404 (Not Found)} if the userRatingsRemarks is not found,
     * or with status {@code 500 (Internal Server Error)} if the userRatingsRemarks couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-ratings-remarks/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserRatingsRemarks> partialUpdateUserRatingsRemarks(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UserRatingsRemarks userRatingsRemarks
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserRatingsRemarks partially : {}, {}", id, userRatingsRemarks);
        if (userRatingsRemarks.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userRatingsRemarks.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userRatingsRemarksRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserRatingsRemarks> result = userRatingsRemarksService.partialUpdate(userRatingsRemarks);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userRatingsRemarks.getId().toString())
        );
    }

    /**
     * {@code GET  /user-ratings-remarks} : get all the userRatingsRemarks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userRatingsRemarks in body.
     */
    @GetMapping("/user-ratings-remarks")
    public ResponseEntity<List<UserRatingsRemarks>> getAllUserRatingsRemarks(
        UserRatingsRemarksCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get UserRatingsRemarks by criteria: {}", criteria);
        Page<UserRatingsRemarks> page = userRatingsRemarksQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-ratings-remarks/count} : count all the userRatingsRemarks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/user-ratings-remarks/count")
    public ResponseEntity<Long> countUserRatingsRemarks(UserRatingsRemarksCriteria criteria) {
        log.debug("REST request to count UserRatingsRemarks by criteria: {}", criteria);
        return ResponseEntity.ok().body(userRatingsRemarksQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /user-ratings-remarks/:id} : get the "id" userRatingsRemarks.
     *
     * @param id the id of the userRatingsRemarks to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userRatingsRemarks, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-ratings-remarks/{id}")
    public ResponseEntity<UserRatingsRemarks> getUserRatingsRemarks(@PathVariable Long id) {
        log.debug("REST request to get UserRatingsRemarks : {}", id);
        Optional<UserRatingsRemarks> userRatingsRemarks = userRatingsRemarksService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userRatingsRemarks);
    }

    /**
     * {@code DELETE  /user-ratings-remarks/:id} : delete the "id" userRatingsRemarks.
     *
     * @param id the id of the userRatingsRemarks to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-ratings-remarks/{id}")
    public ResponseEntity<Void> deleteUserRatingsRemarks(@PathVariable Long id) {
        log.debug("REST request to delete UserRatingsRemarks : {}", id);
        userRatingsRemarksService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
