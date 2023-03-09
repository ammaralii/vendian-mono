package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.UserAttributes;
import com.venturedive.vendian_mono.repository.UserAttributesRepository;
import com.venturedive.vendian_mono.service.UserAttributesQueryService;
import com.venturedive.vendian_mono.service.UserAttributesService;
import com.venturedive.vendian_mono.service.criteria.UserAttributesCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.UserAttributes}.
 */
@RestController
@RequestMapping("/api")
public class UserAttributesResource {

    private final Logger log = LoggerFactory.getLogger(UserAttributesResource.class);

    private static final String ENTITY_NAME = "userAttributes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserAttributesService userAttributesService;

    private final UserAttributesRepository userAttributesRepository;

    private final UserAttributesQueryService userAttributesQueryService;

    public UserAttributesResource(
        UserAttributesService userAttributesService,
        UserAttributesRepository userAttributesRepository,
        UserAttributesQueryService userAttributesQueryService
    ) {
        this.userAttributesService = userAttributesService;
        this.userAttributesRepository = userAttributesRepository;
        this.userAttributesQueryService = userAttributesQueryService;
    }

    /**
     * {@code POST  /user-attributes} : Create a new userAttributes.
     *
     * @param userAttributes the userAttributes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userAttributes, or with status {@code 400 (Bad Request)} if the userAttributes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-attributes")
    public ResponseEntity<UserAttributes> createUserAttributes(@Valid @RequestBody UserAttributes userAttributes)
        throws URISyntaxException {
        log.debug("REST request to save UserAttributes : {}", userAttributes);
        if (userAttributes.getId() != null) {
            throw new BadRequestAlertException("A new userAttributes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserAttributes result = userAttributesService.save(userAttributes);
        return ResponseEntity
            .created(new URI("/api/user-attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-attributes/:id} : Updates an existing userAttributes.
     *
     * @param id the id of the userAttributes to save.
     * @param userAttributes the userAttributes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userAttributes,
     * or with status {@code 400 (Bad Request)} if the userAttributes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userAttributes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-attributes/{id}")
    public ResponseEntity<UserAttributes> updateUserAttributes(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UserAttributes userAttributes
    ) throws URISyntaxException {
        log.debug("REST request to update UserAttributes : {}, {}", id, userAttributes);
        if (userAttributes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userAttributes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userAttributesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserAttributes result = userAttributesService.update(userAttributes);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userAttributes.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-attributes/:id} : Partial updates given fields of an existing userAttributes, field will ignore if it is null
     *
     * @param id the id of the userAttributes to save.
     * @param userAttributes the userAttributes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userAttributes,
     * or with status {@code 400 (Bad Request)} if the userAttributes is not valid,
     * or with status {@code 404 (Not Found)} if the userAttributes is not found,
     * or with status {@code 500 (Internal Server Error)} if the userAttributes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-attributes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserAttributes> partialUpdateUserAttributes(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UserAttributes userAttributes
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserAttributes partially : {}, {}", id, userAttributes);
        if (userAttributes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userAttributes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userAttributesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserAttributes> result = userAttributesService.partialUpdate(userAttributes);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userAttributes.getId().toString())
        );
    }

    /**
     * {@code GET  /user-attributes} : get all the userAttributes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userAttributes in body.
     */
    @GetMapping("/user-attributes")
    public ResponseEntity<List<UserAttributes>> getAllUserAttributes(
        UserAttributesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get UserAttributes by criteria: {}", criteria);
        Page<UserAttributes> page = userAttributesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-attributes/count} : count all the userAttributes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/user-attributes/count")
    public ResponseEntity<Long> countUserAttributes(UserAttributesCriteria criteria) {
        log.debug("REST request to count UserAttributes by criteria: {}", criteria);
        return ResponseEntity.ok().body(userAttributesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /user-attributes/:id} : get the "id" userAttributes.
     *
     * @param id the id of the userAttributes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userAttributes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-attributes/{id}")
    public ResponseEntity<UserAttributes> getUserAttributes(@PathVariable Long id) {
        log.debug("REST request to get UserAttributes : {}", id);
        Optional<UserAttributes> userAttributes = userAttributesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userAttributes);
    }

    /**
     * {@code DELETE  /user-attributes/:id} : delete the "id" userAttributes.
     *
     * @param id the id of the userAttributes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-attributes/{id}")
    public ResponseEntity<Void> deleteUserAttributes(@PathVariable Long id) {
        log.debug("REST request to delete UserAttributes : {}", id);
        userAttributesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
