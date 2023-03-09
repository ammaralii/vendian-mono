package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.UserAttributeEscalationRules;
import com.venturedive.vendian_mono.repository.UserAttributeEscalationRulesRepository;
import com.venturedive.vendian_mono.service.UserAttributeEscalationRulesQueryService;
import com.venturedive.vendian_mono.service.UserAttributeEscalationRulesService;
import com.venturedive.vendian_mono.service.criteria.UserAttributeEscalationRulesCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.UserAttributeEscalationRules}.
 */
@RestController
@RequestMapping("/api")
public class UserAttributeEscalationRulesResource {

    private final Logger log = LoggerFactory.getLogger(UserAttributeEscalationRulesResource.class);

    private static final String ENTITY_NAME = "userAttributeEscalationRules";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserAttributeEscalationRulesService userAttributeEscalationRulesService;

    private final UserAttributeEscalationRulesRepository userAttributeEscalationRulesRepository;

    private final UserAttributeEscalationRulesQueryService userAttributeEscalationRulesQueryService;

    public UserAttributeEscalationRulesResource(
        UserAttributeEscalationRulesService userAttributeEscalationRulesService,
        UserAttributeEscalationRulesRepository userAttributeEscalationRulesRepository,
        UserAttributeEscalationRulesQueryService userAttributeEscalationRulesQueryService
    ) {
        this.userAttributeEscalationRulesService = userAttributeEscalationRulesService;
        this.userAttributeEscalationRulesRepository = userAttributeEscalationRulesRepository;
        this.userAttributeEscalationRulesQueryService = userAttributeEscalationRulesQueryService;
    }

    /**
     * {@code POST  /user-attribute-escalation-rules} : Create a new userAttributeEscalationRules.
     *
     * @param userAttributeEscalationRules the userAttributeEscalationRules to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userAttributeEscalationRules, or with status {@code 400 (Bad Request)} if the userAttributeEscalationRules has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-attribute-escalation-rules")
    public ResponseEntity<UserAttributeEscalationRules> createUserAttributeEscalationRules(
        @Valid @RequestBody UserAttributeEscalationRules userAttributeEscalationRules
    ) throws URISyntaxException {
        log.debug("REST request to save UserAttributeEscalationRules : {}", userAttributeEscalationRules);
        if (userAttributeEscalationRules.getId() != null) {
            throw new BadRequestAlertException("A new userAttributeEscalationRules cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserAttributeEscalationRules result = userAttributeEscalationRulesService.save(userAttributeEscalationRules);
        return ResponseEntity
            .created(new URI("/api/user-attribute-escalation-rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-attribute-escalation-rules/:id} : Updates an existing userAttributeEscalationRules.
     *
     * @param id the id of the userAttributeEscalationRules to save.
     * @param userAttributeEscalationRules the userAttributeEscalationRules to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userAttributeEscalationRules,
     * or with status {@code 400 (Bad Request)} if the userAttributeEscalationRules is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userAttributeEscalationRules couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-attribute-escalation-rules/{id}")
    public ResponseEntity<UserAttributeEscalationRules> updateUserAttributeEscalationRules(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UserAttributeEscalationRules userAttributeEscalationRules
    ) throws URISyntaxException {
        log.debug("REST request to update UserAttributeEscalationRules : {}, {}", id, userAttributeEscalationRules);
        if (userAttributeEscalationRules.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userAttributeEscalationRules.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userAttributeEscalationRulesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserAttributeEscalationRules result = userAttributeEscalationRulesService.update(userAttributeEscalationRules);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userAttributeEscalationRules.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /user-attribute-escalation-rules/:id} : Partial updates given fields of an existing userAttributeEscalationRules, field will ignore if it is null
     *
     * @param id the id of the userAttributeEscalationRules to save.
     * @param userAttributeEscalationRules the userAttributeEscalationRules to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userAttributeEscalationRules,
     * or with status {@code 400 (Bad Request)} if the userAttributeEscalationRules is not valid,
     * or with status {@code 404 (Not Found)} if the userAttributeEscalationRules is not found,
     * or with status {@code 500 (Internal Server Error)} if the userAttributeEscalationRules couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-attribute-escalation-rules/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserAttributeEscalationRules> partialUpdateUserAttributeEscalationRules(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UserAttributeEscalationRules userAttributeEscalationRules
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserAttributeEscalationRules partially : {}, {}", id, userAttributeEscalationRules);
        if (userAttributeEscalationRules.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userAttributeEscalationRules.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userAttributeEscalationRulesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserAttributeEscalationRules> result = userAttributeEscalationRulesService.partialUpdate(userAttributeEscalationRules);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userAttributeEscalationRules.getId().toString())
        );
    }

    /**
     * {@code GET  /user-attribute-escalation-rules} : get all the userAttributeEscalationRules.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userAttributeEscalationRules in body.
     */
    @GetMapping("/user-attribute-escalation-rules")
    public ResponseEntity<List<UserAttributeEscalationRules>> getAllUserAttributeEscalationRules(
        UserAttributeEscalationRulesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get UserAttributeEscalationRules by criteria: {}", criteria);
        Page<UserAttributeEscalationRules> page = userAttributeEscalationRulesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-attribute-escalation-rules/count} : count all the userAttributeEscalationRules.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/user-attribute-escalation-rules/count")
    public ResponseEntity<Long> countUserAttributeEscalationRules(UserAttributeEscalationRulesCriteria criteria) {
        log.debug("REST request to count UserAttributeEscalationRules by criteria: {}", criteria);
        return ResponseEntity.ok().body(userAttributeEscalationRulesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /user-attribute-escalation-rules/:id} : get the "id" userAttributeEscalationRules.
     *
     * @param id the id of the userAttributeEscalationRules to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userAttributeEscalationRules, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-attribute-escalation-rules/{id}")
    public ResponseEntity<UserAttributeEscalationRules> getUserAttributeEscalationRules(@PathVariable Long id) {
        log.debug("REST request to get UserAttributeEscalationRules : {}", id);
        Optional<UserAttributeEscalationRules> userAttributeEscalationRules = userAttributeEscalationRulesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userAttributeEscalationRules);
    }

    /**
     * {@code DELETE  /user-attribute-escalation-rules/:id} : delete the "id" userAttributeEscalationRules.
     *
     * @param id the id of the userAttributeEscalationRules to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-attribute-escalation-rules/{id}")
    public ResponseEntity<Void> deleteUserAttributeEscalationRules(@PathVariable Long id) {
        log.debug("REST request to delete UserAttributeEscalationRules : {}", id);
        userAttributeEscalationRulesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
