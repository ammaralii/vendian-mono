package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.EmployeeWorks;
import com.venturedive.vendian_mono.repository.EmployeeWorksRepository;
import com.venturedive.vendian_mono.service.EmployeeWorksQueryService;
import com.venturedive.vendian_mono.service.EmployeeWorksService;
import com.venturedive.vendian_mono.service.criteria.EmployeeWorksCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.EmployeeWorks}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeWorksResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeWorksResource.class);

    private static final String ENTITY_NAME = "employeeWorks";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeWorksService employeeWorksService;

    private final EmployeeWorksRepository employeeWorksRepository;

    private final EmployeeWorksQueryService employeeWorksQueryService;

    public EmployeeWorksResource(
        EmployeeWorksService employeeWorksService,
        EmployeeWorksRepository employeeWorksRepository,
        EmployeeWorksQueryService employeeWorksQueryService
    ) {
        this.employeeWorksService = employeeWorksService;
        this.employeeWorksRepository = employeeWorksRepository;
        this.employeeWorksQueryService = employeeWorksQueryService;
    }

    /**
     * {@code POST  /employee-works} : Create a new employeeWorks.
     *
     * @param employeeWorks the employeeWorks to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeWorks, or with status {@code 400 (Bad Request)} if the employeeWorks has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-works")
    public ResponseEntity<EmployeeWorks> createEmployeeWorks(@Valid @RequestBody EmployeeWorks employeeWorks) throws URISyntaxException {
        log.debug("REST request to save EmployeeWorks : {}", employeeWorks);
        if (employeeWorks.getId() != null) {
            throw new BadRequestAlertException("A new employeeWorks cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeWorks result = employeeWorksService.save(employeeWorks);
        return ResponseEntity
            .created(new URI("/api/employee-works/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-works/:id} : Updates an existing employeeWorks.
     *
     * @param id the id of the employeeWorks to save.
     * @param employeeWorks the employeeWorks to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeWorks,
     * or with status {@code 400 (Bad Request)} if the employeeWorks is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeWorks couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-works/{id}")
    public ResponseEntity<EmployeeWorks> updateEmployeeWorks(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EmployeeWorks employeeWorks
    ) throws URISyntaxException {
        log.debug("REST request to update EmployeeWorks : {}, {}", id, employeeWorks);
        if (employeeWorks.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeWorks.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeWorksRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmployeeWorks result = employeeWorksService.update(employeeWorks);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeWorks.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /employee-works/:id} : Partial updates given fields of an existing employeeWorks, field will ignore if it is null
     *
     * @param id the id of the employeeWorks to save.
     * @param employeeWorks the employeeWorks to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeWorks,
     * or with status {@code 400 (Bad Request)} if the employeeWorks is not valid,
     * or with status {@code 404 (Not Found)} if the employeeWorks is not found,
     * or with status {@code 500 (Internal Server Error)} if the employeeWorks couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/employee-works/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmployeeWorks> partialUpdateEmployeeWorks(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EmployeeWorks employeeWorks
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmployeeWorks partially : {}, {}", id, employeeWorks);
        if (employeeWorks.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeWorks.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeWorksRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmployeeWorks> result = employeeWorksService.partialUpdate(employeeWorks);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeWorks.getId().toString())
        );
    }

    /**
     * {@code GET  /employee-works} : get all the employeeWorks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeWorks in body.
     */
    @GetMapping("/employee-works")
    public ResponseEntity<List<EmployeeWorks>> getAllEmployeeWorks(
        EmployeeWorksCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EmployeeWorks by criteria: {}", criteria);
        Page<EmployeeWorks> page = employeeWorksQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-works/count} : count all the employeeWorks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employee-works/count")
    public ResponseEntity<Long> countEmployeeWorks(EmployeeWorksCriteria criteria) {
        log.debug("REST request to count EmployeeWorks by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeeWorksQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employee-works/:id} : get the "id" employeeWorks.
     *
     * @param id the id of the employeeWorks to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeWorks, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-works/{id}")
    public ResponseEntity<EmployeeWorks> getEmployeeWorks(@PathVariable Long id) {
        log.debug("REST request to get EmployeeWorks : {}", id);
        Optional<EmployeeWorks> employeeWorks = employeeWorksService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeWorks);
    }

    /**
     * {@code DELETE  /employee-works/:id} : delete the "id" employeeWorks.
     *
     * @param id the id of the employeeWorks to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-works/{id}")
    public ResponseEntity<Void> deleteEmployeeWorks(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeWorks : {}", id);
        employeeWorksService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
