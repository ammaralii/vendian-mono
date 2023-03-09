package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.EmployeeComments;
import com.venturedive.vendian_mono.repository.EmployeeCommentsRepository;
import com.venturedive.vendian_mono.service.EmployeeCommentsQueryService;
import com.venturedive.vendian_mono.service.EmployeeCommentsService;
import com.venturedive.vendian_mono.service.criteria.EmployeeCommentsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.EmployeeComments}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeCommentsResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeCommentsResource.class);

    private static final String ENTITY_NAME = "employeeComments";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeCommentsService employeeCommentsService;

    private final EmployeeCommentsRepository employeeCommentsRepository;

    private final EmployeeCommentsQueryService employeeCommentsQueryService;

    public EmployeeCommentsResource(
        EmployeeCommentsService employeeCommentsService,
        EmployeeCommentsRepository employeeCommentsRepository,
        EmployeeCommentsQueryService employeeCommentsQueryService
    ) {
        this.employeeCommentsService = employeeCommentsService;
        this.employeeCommentsRepository = employeeCommentsRepository;
        this.employeeCommentsQueryService = employeeCommentsQueryService;
    }

    /**
     * {@code POST  /employee-comments} : Create a new employeeComments.
     *
     * @param employeeComments the employeeComments to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeComments, or with status {@code 400 (Bad Request)} if the employeeComments has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-comments")
    public ResponseEntity<EmployeeComments> createEmployeeComments(@Valid @RequestBody EmployeeComments employeeComments)
        throws URISyntaxException {
        log.debug("REST request to save EmployeeComments : {}", employeeComments);
        if (employeeComments.getId() != null) {
            throw new BadRequestAlertException("A new employeeComments cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeComments result = employeeCommentsService.save(employeeComments);
        return ResponseEntity
            .created(new URI("/api/employee-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-comments/:id} : Updates an existing employeeComments.
     *
     * @param id the id of the employeeComments to save.
     * @param employeeComments the employeeComments to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeComments,
     * or with status {@code 400 (Bad Request)} if the employeeComments is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeComments couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-comments/{id}")
    public ResponseEntity<EmployeeComments> updateEmployeeComments(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EmployeeComments employeeComments
    ) throws URISyntaxException {
        log.debug("REST request to update EmployeeComments : {}, {}", id, employeeComments);
        if (employeeComments.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeComments.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeCommentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmployeeComments result = employeeCommentsService.update(employeeComments);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeComments.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /employee-comments/:id} : Partial updates given fields of an existing employeeComments, field will ignore if it is null
     *
     * @param id the id of the employeeComments to save.
     * @param employeeComments the employeeComments to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeComments,
     * or with status {@code 400 (Bad Request)} if the employeeComments is not valid,
     * or with status {@code 404 (Not Found)} if the employeeComments is not found,
     * or with status {@code 500 (Internal Server Error)} if the employeeComments couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/employee-comments/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmployeeComments> partialUpdateEmployeeComments(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EmployeeComments employeeComments
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmployeeComments partially : {}, {}", id, employeeComments);
        if (employeeComments.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeComments.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeCommentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmployeeComments> result = employeeCommentsService.partialUpdate(employeeComments);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeComments.getId().toString())
        );
    }

    /**
     * {@code GET  /employee-comments} : get all the employeeComments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeComments in body.
     */
    @GetMapping("/employee-comments")
    public ResponseEntity<List<EmployeeComments>> getAllEmployeeComments(
        EmployeeCommentsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EmployeeComments by criteria: {}", criteria);
        Page<EmployeeComments> page = employeeCommentsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-comments/count} : count all the employeeComments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employee-comments/count")
    public ResponseEntity<Long> countEmployeeComments(EmployeeCommentsCriteria criteria) {
        log.debug("REST request to count EmployeeComments by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeeCommentsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employee-comments/:id} : get the "id" employeeComments.
     *
     * @param id the id of the employeeComments to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeComments, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-comments/{id}")
    public ResponseEntity<EmployeeComments> getEmployeeComments(@PathVariable Long id) {
        log.debug("REST request to get EmployeeComments : {}", id);
        Optional<EmployeeComments> employeeComments = employeeCommentsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeComments);
    }

    /**
     * {@code DELETE  /employee-comments/:id} : delete the "id" employeeComments.
     *
     * @param id the id of the employeeComments to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-comments/{id}")
    public ResponseEntity<Void> deleteEmployeeComments(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeComments : {}", id);
        employeeCommentsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
