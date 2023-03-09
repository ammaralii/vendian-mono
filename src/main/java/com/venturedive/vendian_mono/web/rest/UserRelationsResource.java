package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.UserRelations;
import com.venturedive.vendian_mono.repository.UserRelationsRepository;
import com.venturedive.vendian_mono.service.UserRelationsQueryService;
import com.venturedive.vendian_mono.service.UserRelationsService;
import com.venturedive.vendian_mono.service.criteria.UserRelationsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.UserRelations}.
 */
@RestController
@RequestMapping("/api")
public class UserRelationsResource {

    private final Logger log = LoggerFactory.getLogger(UserRelationsResource.class);

    private static final String ENTITY_NAME = "userRelations";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserRelationsService userRelationsService;

    private final UserRelationsRepository userRelationsRepository;

    private final UserRelationsQueryService userRelationsQueryService;

    public UserRelationsResource(
        UserRelationsService userRelationsService,
        UserRelationsRepository userRelationsRepository,
        UserRelationsQueryService userRelationsQueryService
    ) {
        this.userRelationsService = userRelationsService;
        this.userRelationsRepository = userRelationsRepository;
        this.userRelationsQueryService = userRelationsQueryService;
    }

    /**
     * {@code POST  /user-relations} : Create a new userRelations.
     *
     * @param userRelations the userRelations to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userRelations, or with status {@code 400 (Bad Request)} if the userRelations has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-relations")
    public ResponseEntity<UserRelations> createUserRelations(@Valid @RequestBody UserRelations userRelations) throws URISyntaxException {
        log.debug("REST request to save UserRelations : {}", userRelations);
        if (userRelations.getId() != null) {
            throw new BadRequestAlertException("A new userRelations cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserRelations result = userRelationsService.save(userRelations);
        return ResponseEntity
            .created(new URI("/api/user-relations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-relations/:id} : Updates an existing userRelations.
     *
     * @param id the id of the userRelations to save.
     * @param userRelations the userRelations to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userRelations,
     * or with status {@code 400 (Bad Request)} if the userRelations is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userRelations couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-relations/{id}")
    public ResponseEntity<UserRelations> updateUserRelations(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UserRelations userRelations
    ) throws URISyntaxException {
        log.debug("REST request to update UserRelations : {}, {}", id, userRelations);
        if (userRelations.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userRelations.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userRelationsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserRelations result = userRelationsService.update(userRelations);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userRelations.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-relations/:id} : Partial updates given fields of an existing userRelations, field will ignore if it is null
     *
     * @param id the id of the userRelations to save.
     * @param userRelations the userRelations to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userRelations,
     * or with status {@code 400 (Bad Request)} if the userRelations is not valid,
     * or with status {@code 404 (Not Found)} if the userRelations is not found,
     * or with status {@code 500 (Internal Server Error)} if the userRelations couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-relations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserRelations> partialUpdateUserRelations(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UserRelations userRelations
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserRelations partially : {}, {}", id, userRelations);
        if (userRelations.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userRelations.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userRelationsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserRelations> result = userRelationsService.partialUpdate(userRelations);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userRelations.getId().toString())
        );
    }

    /**
     * {@code GET  /user-relations} : get all the userRelations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userRelations in body.
     */
    @GetMapping("/user-relations")
    public ResponseEntity<List<UserRelations>> getAllUserRelations(
        UserRelationsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get UserRelations by criteria: {}", criteria);
        Page<UserRelations> page = userRelationsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-relations/count} : count all the userRelations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/user-relations/count")
    public ResponseEntity<Long> countUserRelations(UserRelationsCriteria criteria) {
        log.debug("REST request to count UserRelations by criteria: {}", criteria);
        return ResponseEntity.ok().body(userRelationsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /user-relations/:id} : get the "id" userRelations.
     *
     * @param id the id of the userRelations to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userRelations, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-relations/{id}")
    public ResponseEntity<UserRelations> getUserRelations(@PathVariable Long id) {
        log.debug("REST request to get UserRelations : {}", id);
        Optional<UserRelations> userRelations = userRelationsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userRelations);
    }

    /**
     * {@code DELETE  /user-relations/:id} : delete the "id" userRelations.
     *
     * @param id the id of the userRelations to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-relations/{id}")
    public ResponseEntity<Void> deleteUserRelations(@PathVariable Long id) {
        log.debug("REST request to delete UserRelations : {}", id);
        userRelationsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
