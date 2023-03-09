package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.UserRelationApprovalRules;
import com.venturedive.vendian_mono.repository.UserRelationApprovalRulesRepository;
import com.venturedive.vendian_mono.service.UserRelationApprovalRulesQueryService;
import com.venturedive.vendian_mono.service.UserRelationApprovalRulesService;
import com.venturedive.vendian_mono.service.criteria.UserRelationApprovalRulesCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.UserRelationApprovalRules}.
 */
@RestController
@RequestMapping("/api")
public class UserRelationApprovalRulesResource {

    private final Logger log = LoggerFactory.getLogger(UserRelationApprovalRulesResource.class);

    private static final String ENTITY_NAME = "userRelationApprovalRules";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserRelationApprovalRulesService userRelationApprovalRulesService;

    private final UserRelationApprovalRulesRepository userRelationApprovalRulesRepository;

    private final UserRelationApprovalRulesQueryService userRelationApprovalRulesQueryService;

    public UserRelationApprovalRulesResource(
        UserRelationApprovalRulesService userRelationApprovalRulesService,
        UserRelationApprovalRulesRepository userRelationApprovalRulesRepository,
        UserRelationApprovalRulesQueryService userRelationApprovalRulesQueryService
    ) {
        this.userRelationApprovalRulesService = userRelationApprovalRulesService;
        this.userRelationApprovalRulesRepository = userRelationApprovalRulesRepository;
        this.userRelationApprovalRulesQueryService = userRelationApprovalRulesQueryService;
    }

    /**
     * {@code POST  /user-relation-approval-rules} : Create a new userRelationApprovalRules.
     *
     * @param userRelationApprovalRules the userRelationApprovalRules to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userRelationApprovalRules, or with status {@code 400 (Bad Request)} if the userRelationApprovalRules has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-relation-approval-rules")
    public ResponseEntity<UserRelationApprovalRules> createUserRelationApprovalRules(
        @Valid @RequestBody UserRelationApprovalRules userRelationApprovalRules
    ) throws URISyntaxException {
        log.debug("REST request to save UserRelationApprovalRules : {}", userRelationApprovalRules);
        if (userRelationApprovalRules.getId() != null) {
            throw new BadRequestAlertException("A new userRelationApprovalRules cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserRelationApprovalRules result = userRelationApprovalRulesService.save(userRelationApprovalRules);
        return ResponseEntity
            .created(new URI("/api/user-relation-approval-rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-relation-approval-rules/:id} : Updates an existing userRelationApprovalRules.
     *
     * @param id the id of the userRelationApprovalRules to save.
     * @param userRelationApprovalRules the userRelationApprovalRules to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userRelationApprovalRules,
     * or with status {@code 400 (Bad Request)} if the userRelationApprovalRules is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userRelationApprovalRules couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-relation-approval-rules/{id}")
    public ResponseEntity<UserRelationApprovalRules> updateUserRelationApprovalRules(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UserRelationApprovalRules userRelationApprovalRules
    ) throws URISyntaxException {
        log.debug("REST request to update UserRelationApprovalRules : {}, {}", id, userRelationApprovalRules);
        if (userRelationApprovalRules.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userRelationApprovalRules.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userRelationApprovalRulesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserRelationApprovalRules result = userRelationApprovalRulesService.update(userRelationApprovalRules);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userRelationApprovalRules.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-relation-approval-rules/:id} : Partial updates given fields of an existing userRelationApprovalRules, field will ignore if it is null
     *
     * @param id the id of the userRelationApprovalRules to save.
     * @param userRelationApprovalRules the userRelationApprovalRules to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userRelationApprovalRules,
     * or with status {@code 400 (Bad Request)} if the userRelationApprovalRules is not valid,
     * or with status {@code 404 (Not Found)} if the userRelationApprovalRules is not found,
     * or with status {@code 500 (Internal Server Error)} if the userRelationApprovalRules couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-relation-approval-rules/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserRelationApprovalRules> partialUpdateUserRelationApprovalRules(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UserRelationApprovalRules userRelationApprovalRules
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserRelationApprovalRules partially : {}, {}", id, userRelationApprovalRules);
        if (userRelationApprovalRules.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userRelationApprovalRules.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userRelationApprovalRulesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserRelationApprovalRules> result = userRelationApprovalRulesService.partialUpdate(userRelationApprovalRules);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userRelationApprovalRules.getId().toString())
        );
    }

    /**
     * {@code GET  /user-relation-approval-rules} : get all the userRelationApprovalRules.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userRelationApprovalRules in body.
     */
    @GetMapping("/user-relation-approval-rules")
    public ResponseEntity<List<UserRelationApprovalRules>> getAllUserRelationApprovalRules(
        UserRelationApprovalRulesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get UserRelationApprovalRules by criteria: {}", criteria);
        Page<UserRelationApprovalRules> page = userRelationApprovalRulesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-relation-approval-rules/count} : count all the userRelationApprovalRules.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/user-relation-approval-rules/count")
    public ResponseEntity<Long> countUserRelationApprovalRules(UserRelationApprovalRulesCriteria criteria) {
        log.debug("REST request to count UserRelationApprovalRules by criteria: {}", criteria);
        return ResponseEntity.ok().body(userRelationApprovalRulesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /user-relation-approval-rules/:id} : get the "id" userRelationApprovalRules.
     *
     * @param id the id of the userRelationApprovalRules to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userRelationApprovalRules, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-relation-approval-rules/{id}")
    public ResponseEntity<UserRelationApprovalRules> getUserRelationApprovalRules(@PathVariable Long id) {
        log.debug("REST request to get UserRelationApprovalRules : {}", id);
        Optional<UserRelationApprovalRules> userRelationApprovalRules = userRelationApprovalRulesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userRelationApprovalRules);
    }

    /**
     * {@code DELETE  /user-relation-approval-rules/:id} : delete the "id" userRelationApprovalRules.
     *
     * @param id the id of the userRelationApprovalRules to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-relation-approval-rules/{id}")
    public ResponseEntity<Void> deleteUserRelationApprovalRules(@PathVariable Long id) {
        log.debug("REST request to delete UserRelationApprovalRules : {}", id);
        userRelationApprovalRulesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
