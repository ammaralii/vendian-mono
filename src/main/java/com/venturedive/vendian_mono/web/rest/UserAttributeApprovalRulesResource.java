package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.UserAttributeApprovalRules;
import com.venturedive.vendian_mono.repository.UserAttributeApprovalRulesRepository;
import com.venturedive.vendian_mono.service.UserAttributeApprovalRulesQueryService;
import com.venturedive.vendian_mono.service.UserAttributeApprovalRulesService;
import com.venturedive.vendian_mono.service.criteria.UserAttributeApprovalRulesCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.UserAttributeApprovalRules}.
 */
@RestController
@RequestMapping("/api")
public class UserAttributeApprovalRulesResource {

    private final Logger log = LoggerFactory.getLogger(UserAttributeApprovalRulesResource.class);

    private static final String ENTITY_NAME = "userAttributeApprovalRules";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserAttributeApprovalRulesService userAttributeApprovalRulesService;

    private final UserAttributeApprovalRulesRepository userAttributeApprovalRulesRepository;

    private final UserAttributeApprovalRulesQueryService userAttributeApprovalRulesQueryService;

    public UserAttributeApprovalRulesResource(
        UserAttributeApprovalRulesService userAttributeApprovalRulesService,
        UserAttributeApprovalRulesRepository userAttributeApprovalRulesRepository,
        UserAttributeApprovalRulesQueryService userAttributeApprovalRulesQueryService
    ) {
        this.userAttributeApprovalRulesService = userAttributeApprovalRulesService;
        this.userAttributeApprovalRulesRepository = userAttributeApprovalRulesRepository;
        this.userAttributeApprovalRulesQueryService = userAttributeApprovalRulesQueryService;
    }

    /**
     * {@code POST  /user-attribute-approval-rules} : Create a new userAttributeApprovalRules.
     *
     * @param userAttributeApprovalRules the userAttributeApprovalRules to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userAttributeApprovalRules, or with status {@code 400 (Bad Request)} if the userAttributeApprovalRules has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-attribute-approval-rules")
    public ResponseEntity<UserAttributeApprovalRules> createUserAttributeApprovalRules(
        @Valid @RequestBody UserAttributeApprovalRules userAttributeApprovalRules
    ) throws URISyntaxException {
        log.debug("REST request to save UserAttributeApprovalRules : {}", userAttributeApprovalRules);
        if (userAttributeApprovalRules.getId() != null) {
            throw new BadRequestAlertException("A new userAttributeApprovalRules cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserAttributeApprovalRules result = userAttributeApprovalRulesService.save(userAttributeApprovalRules);
        return ResponseEntity
            .created(new URI("/api/user-attribute-approval-rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-attribute-approval-rules/:id} : Updates an existing userAttributeApprovalRules.
     *
     * @param id the id of the userAttributeApprovalRules to save.
     * @param userAttributeApprovalRules the userAttributeApprovalRules to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userAttributeApprovalRules,
     * or with status {@code 400 (Bad Request)} if the userAttributeApprovalRules is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userAttributeApprovalRules couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-attribute-approval-rules/{id}")
    public ResponseEntity<UserAttributeApprovalRules> updateUserAttributeApprovalRules(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UserAttributeApprovalRules userAttributeApprovalRules
    ) throws URISyntaxException {
        log.debug("REST request to update UserAttributeApprovalRules : {}, {}", id, userAttributeApprovalRules);
        if (userAttributeApprovalRules.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userAttributeApprovalRules.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userAttributeApprovalRulesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserAttributeApprovalRules result = userAttributeApprovalRulesService.update(userAttributeApprovalRules);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userAttributeApprovalRules.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-attribute-approval-rules/:id} : Partial updates given fields of an existing userAttributeApprovalRules, field will ignore if it is null
     *
     * @param id the id of the userAttributeApprovalRules to save.
     * @param userAttributeApprovalRules the userAttributeApprovalRules to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userAttributeApprovalRules,
     * or with status {@code 400 (Bad Request)} if the userAttributeApprovalRules is not valid,
     * or with status {@code 404 (Not Found)} if the userAttributeApprovalRules is not found,
     * or with status {@code 500 (Internal Server Error)} if the userAttributeApprovalRules couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-attribute-approval-rules/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserAttributeApprovalRules> partialUpdateUserAttributeApprovalRules(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UserAttributeApprovalRules userAttributeApprovalRules
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserAttributeApprovalRules partially : {}, {}", id, userAttributeApprovalRules);
        if (userAttributeApprovalRules.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userAttributeApprovalRules.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userAttributeApprovalRulesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserAttributeApprovalRules> result = userAttributeApprovalRulesService.partialUpdate(userAttributeApprovalRules);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userAttributeApprovalRules.getId().toString())
        );
    }

    /**
     * {@code GET  /user-attribute-approval-rules} : get all the userAttributeApprovalRules.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userAttributeApprovalRules in body.
     */
    @GetMapping("/user-attribute-approval-rules")
    public ResponseEntity<List<UserAttributeApprovalRules>> getAllUserAttributeApprovalRules(
        UserAttributeApprovalRulesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get UserAttributeApprovalRules by criteria: {}", criteria);
        Page<UserAttributeApprovalRules> page = userAttributeApprovalRulesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-attribute-approval-rules/count} : count all the userAttributeApprovalRules.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/user-attribute-approval-rules/count")
    public ResponseEntity<Long> countUserAttributeApprovalRules(UserAttributeApprovalRulesCriteria criteria) {
        log.debug("REST request to count UserAttributeApprovalRules by criteria: {}", criteria);
        return ResponseEntity.ok().body(userAttributeApprovalRulesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /user-attribute-approval-rules/:id} : get the "id" userAttributeApprovalRules.
     *
     * @param id the id of the userAttributeApprovalRules to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userAttributeApprovalRules, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-attribute-approval-rules/{id}")
    public ResponseEntity<UserAttributeApprovalRules> getUserAttributeApprovalRules(@PathVariable Long id) {
        log.debug("REST request to get UserAttributeApprovalRules : {}", id);
        Optional<UserAttributeApprovalRules> userAttributeApprovalRules = userAttributeApprovalRulesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userAttributeApprovalRules);
    }

    /**
     * {@code DELETE  /user-attribute-approval-rules/:id} : delete the "id" userAttributeApprovalRules.
     *
     * @param id the id of the userAttributeApprovalRules to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-attribute-approval-rules/{id}")
    public ResponseEntity<Void> deleteUserAttributeApprovalRules(@PathVariable Long id) {
        log.debug("REST request to delete UserAttributeApprovalRules : {}", id);
        userAttributeApprovalRulesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
