package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.EmployeeTalents;
import com.venturedive.vendian_mono.repository.EmployeeTalentsRepository;
import com.venturedive.vendian_mono.service.EmployeeTalentsQueryService;
import com.venturedive.vendian_mono.service.EmployeeTalentsService;
import com.venturedive.vendian_mono.service.criteria.EmployeeTalentsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.EmployeeTalents}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeTalentsResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeTalentsResource.class);

    private static final String ENTITY_NAME = "employeeTalents";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeTalentsService employeeTalentsService;

    private final EmployeeTalentsRepository employeeTalentsRepository;

    private final EmployeeTalentsQueryService employeeTalentsQueryService;

    public EmployeeTalentsResource(
        EmployeeTalentsService employeeTalentsService,
        EmployeeTalentsRepository employeeTalentsRepository,
        EmployeeTalentsQueryService employeeTalentsQueryService
    ) {
        this.employeeTalentsService = employeeTalentsService;
        this.employeeTalentsRepository = employeeTalentsRepository;
        this.employeeTalentsQueryService = employeeTalentsQueryService;
    }

    /**
     * {@code POST  /employee-talents} : Create a new employeeTalents.
     *
     * @param employeeTalents the employeeTalents to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeTalents, or with status {@code 400 (Bad Request)} if the employeeTalents has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-talents")
    public ResponseEntity<EmployeeTalents> createEmployeeTalents(@Valid @RequestBody EmployeeTalents employeeTalents)
        throws URISyntaxException {
        log.debug("REST request to save EmployeeTalents : {}", employeeTalents);
        if (employeeTalents.getId() != null) {
            throw new BadRequestAlertException("A new employeeTalents cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeTalents result = employeeTalentsService.save(employeeTalents);
        return ResponseEntity
            .created(new URI("/api/employee-talents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-talents/:id} : Updates an existing employeeTalents.
     *
     * @param id the id of the employeeTalents to save.
     * @param employeeTalents the employeeTalents to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeTalents,
     * or with status {@code 400 (Bad Request)} if the employeeTalents is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeTalents couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-talents/{id}")
    public ResponseEntity<EmployeeTalents> updateEmployeeTalents(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EmployeeTalents employeeTalents
    ) throws URISyntaxException {
        log.debug("REST request to update EmployeeTalents : {}, {}", id, employeeTalents);
        if (employeeTalents.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeTalents.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeTalentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmployeeTalents result = employeeTalentsService.update(employeeTalents);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeTalents.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /employee-talents/:id} : Partial updates given fields of an existing employeeTalents, field will ignore if it is null
     *
     * @param id the id of the employeeTalents to save.
     * @param employeeTalents the employeeTalents to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeTalents,
     * or with status {@code 400 (Bad Request)} if the employeeTalents is not valid,
     * or with status {@code 404 (Not Found)} if the employeeTalents is not found,
     * or with status {@code 500 (Internal Server Error)} if the employeeTalents couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/employee-talents/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmployeeTalents> partialUpdateEmployeeTalents(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EmployeeTalents employeeTalents
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmployeeTalents partially : {}, {}", id, employeeTalents);
        if (employeeTalents.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeTalents.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeTalentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmployeeTalents> result = employeeTalentsService.partialUpdate(employeeTalents);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeTalents.getId().toString())
        );
    }

    /**
     * {@code GET  /employee-talents} : get all the employeeTalents.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeTalents in body.
     */
    @GetMapping("/employee-talents")
    public ResponseEntity<List<EmployeeTalents>> getAllEmployeeTalents(
        EmployeeTalentsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EmployeeTalents by criteria: {}", criteria);
        Page<EmployeeTalents> page = employeeTalentsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-talents/count} : count all the employeeTalents.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employee-talents/count")
    public ResponseEntity<Long> countEmployeeTalents(EmployeeTalentsCriteria criteria) {
        log.debug("REST request to count EmployeeTalents by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeeTalentsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employee-talents/:id} : get the "id" employeeTalents.
     *
     * @param id the id of the employeeTalents to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeTalents, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-talents/{id}")
    public ResponseEntity<EmployeeTalents> getEmployeeTalents(@PathVariable Long id) {
        log.debug("REST request to get EmployeeTalents : {}", id);
        Optional<EmployeeTalents> employeeTalents = employeeTalentsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeTalents);
    }

    /**
     * {@code DELETE  /employee-talents/:id} : delete the "id" employeeTalents.
     *
     * @param id the id of the employeeTalents to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-talents/{id}")
    public ResponseEntity<Void> deleteEmployeeTalents(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeTalents : {}", id);
        employeeTalentsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
