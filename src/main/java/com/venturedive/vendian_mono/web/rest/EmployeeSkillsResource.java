package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.EmployeeSkills;
import com.venturedive.vendian_mono.repository.EmployeeSkillsRepository;
import com.venturedive.vendian_mono.service.EmployeeSkillsQueryService;
import com.venturedive.vendian_mono.service.EmployeeSkillsService;
import com.venturedive.vendian_mono.service.criteria.EmployeeSkillsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.EmployeeSkills}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeSkillsResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeSkillsResource.class);

    private static final String ENTITY_NAME = "employeeSkills";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeSkillsService employeeSkillsService;

    private final EmployeeSkillsRepository employeeSkillsRepository;

    private final EmployeeSkillsQueryService employeeSkillsQueryService;

    public EmployeeSkillsResource(
        EmployeeSkillsService employeeSkillsService,
        EmployeeSkillsRepository employeeSkillsRepository,
        EmployeeSkillsQueryService employeeSkillsQueryService
    ) {
        this.employeeSkillsService = employeeSkillsService;
        this.employeeSkillsRepository = employeeSkillsRepository;
        this.employeeSkillsQueryService = employeeSkillsQueryService;
    }

    /**
     * {@code POST  /employee-skills} : Create a new employeeSkills.
     *
     * @param employeeSkills the employeeSkills to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeSkills, or with status {@code 400 (Bad Request)} if the employeeSkills has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-skills")
    public ResponseEntity<EmployeeSkills> createEmployeeSkills(@Valid @RequestBody EmployeeSkills employeeSkills)
        throws URISyntaxException {
        log.debug("REST request to save EmployeeSkills : {}", employeeSkills);
        if (employeeSkills.getId() != null) {
            throw new BadRequestAlertException("A new employeeSkills cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeSkills result = employeeSkillsService.save(employeeSkills);
        return ResponseEntity
            .created(new URI("/api/employee-skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-skills/:id} : Updates an existing employeeSkills.
     *
     * @param id the id of the employeeSkills to save.
     * @param employeeSkills the employeeSkills to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeSkills,
     * or with status {@code 400 (Bad Request)} if the employeeSkills is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeSkills couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-skills/{id}")
    public ResponseEntity<EmployeeSkills> updateEmployeeSkills(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EmployeeSkills employeeSkills
    ) throws URISyntaxException {
        log.debug("REST request to update EmployeeSkills : {}, {}", id, employeeSkills);
        if (employeeSkills.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeSkills.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeSkillsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmployeeSkills result = employeeSkillsService.update(employeeSkills);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeSkills.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /employee-skills/:id} : Partial updates given fields of an existing employeeSkills, field will ignore if it is null
     *
     * @param id the id of the employeeSkills to save.
     * @param employeeSkills the employeeSkills to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeSkills,
     * or with status {@code 400 (Bad Request)} if the employeeSkills is not valid,
     * or with status {@code 404 (Not Found)} if the employeeSkills is not found,
     * or with status {@code 500 (Internal Server Error)} if the employeeSkills couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/employee-skills/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmployeeSkills> partialUpdateEmployeeSkills(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EmployeeSkills employeeSkills
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmployeeSkills partially : {}, {}", id, employeeSkills);
        if (employeeSkills.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeSkills.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeSkillsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmployeeSkills> result = employeeSkillsService.partialUpdate(employeeSkills);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeSkills.getId().toString())
        );
    }

    /**
     * {@code GET  /employee-skills} : get all the employeeSkills.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeSkills in body.
     */
    @GetMapping("/employee-skills")
    public ResponseEntity<List<EmployeeSkills>> getAllEmployeeSkills(
        EmployeeSkillsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EmployeeSkills by criteria: {}", criteria);
        Page<EmployeeSkills> page = employeeSkillsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-skills/count} : count all the employeeSkills.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employee-skills/count")
    public ResponseEntity<Long> countEmployeeSkills(EmployeeSkillsCriteria criteria) {
        log.debug("REST request to count EmployeeSkills by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeeSkillsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employee-skills/:id} : get the "id" employeeSkills.
     *
     * @param id the id of the employeeSkills to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeSkills, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-skills/{id}")
    public ResponseEntity<EmployeeSkills> getEmployeeSkills(@PathVariable Long id) {
        log.debug("REST request to get EmployeeSkills : {}", id);
        Optional<EmployeeSkills> employeeSkills = employeeSkillsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeSkills);
    }

    /**
     * {@code DELETE  /employee-skills/:id} : delete the "id" employeeSkills.
     *
     * @param id the id of the employeeSkills to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-skills/{id}")
    public ResponseEntity<Void> deleteEmployeeSkills(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeSkills : {}", id);
        employeeSkillsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
