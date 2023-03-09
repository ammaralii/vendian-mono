package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.EmployeeProjectRatings20190826;
import com.venturedive.vendian_mono.repository.EmployeeProjectRatings20190826Repository;
import com.venturedive.vendian_mono.service.EmployeeProjectRatings20190826QueryService;
import com.venturedive.vendian_mono.service.EmployeeProjectRatings20190826Service;
import com.venturedive.vendian_mono.service.criteria.EmployeeProjectRatings20190826Criteria;
import com.venturedive.vendian_mono.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.EmployeeProjectRatings20190826}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeProjectRatings20190826Resource {

    private final Logger log = LoggerFactory.getLogger(EmployeeProjectRatings20190826Resource.class);

    private static final String ENTITY_NAME = "employeeProjectRatings20190826";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeProjectRatings20190826Service employeeProjectRatings20190826Service;

    private final EmployeeProjectRatings20190826Repository employeeProjectRatings20190826Repository;

    private final EmployeeProjectRatings20190826QueryService employeeProjectRatings20190826QueryService;

    public EmployeeProjectRatings20190826Resource(
        EmployeeProjectRatings20190826Service employeeProjectRatings20190826Service,
        EmployeeProjectRatings20190826Repository employeeProjectRatings20190826Repository,
        EmployeeProjectRatings20190826QueryService employeeProjectRatings20190826QueryService
    ) {
        this.employeeProjectRatings20190826Service = employeeProjectRatings20190826Service;
        this.employeeProjectRatings20190826Repository = employeeProjectRatings20190826Repository;
        this.employeeProjectRatings20190826QueryService = employeeProjectRatings20190826QueryService;
    }

    /**
     * {@code POST  /employee-project-ratings-20190826-s} : Create a new employeeProjectRatings20190826.
     *
     * @param employeeProjectRatings20190826 the employeeProjectRatings20190826 to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeProjectRatings20190826, or with status {@code 400 (Bad Request)} if the employeeProjectRatings20190826 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-project-ratings-20190826-s")
    public ResponseEntity<EmployeeProjectRatings20190826> createEmployeeProjectRatings20190826(
        @RequestBody EmployeeProjectRatings20190826 employeeProjectRatings20190826
    ) throws URISyntaxException {
        log.debug("REST request to save EmployeeProjectRatings20190826 : {}", employeeProjectRatings20190826);
        if (employeeProjectRatings20190826.getId() != null) {
            throw new BadRequestAlertException("A new employeeProjectRatings20190826 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeProjectRatings20190826 result = employeeProjectRatings20190826Service.save(employeeProjectRatings20190826);
        return ResponseEntity
            .created(new URI("/api/employee-project-ratings-20190826-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-project-ratings-20190826-s/:id} : Updates an existing employeeProjectRatings20190826.
     *
     * @param id the id of the employeeProjectRatings20190826 to save.
     * @param employeeProjectRatings20190826 the employeeProjectRatings20190826 to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeProjectRatings20190826,
     * or with status {@code 400 (Bad Request)} if the employeeProjectRatings20190826 is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeProjectRatings20190826 couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-project-ratings-20190826-s/{id}")
    public ResponseEntity<EmployeeProjectRatings20190826> updateEmployeeProjectRatings20190826(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmployeeProjectRatings20190826 employeeProjectRatings20190826
    ) throws URISyntaxException {
        log.debug("REST request to update EmployeeProjectRatings20190826 : {}, {}", id, employeeProjectRatings20190826);
        if (employeeProjectRatings20190826.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeProjectRatings20190826.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeProjectRatings20190826Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmployeeProjectRatings20190826 result = employeeProjectRatings20190826Service.update(employeeProjectRatings20190826);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeProjectRatings20190826.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /employee-project-ratings-20190826-s/:id} : Partial updates given fields of an existing employeeProjectRatings20190826, field will ignore if it is null
     *
     * @param id the id of the employeeProjectRatings20190826 to save.
     * @param employeeProjectRatings20190826 the employeeProjectRatings20190826 to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeProjectRatings20190826,
     * or with status {@code 400 (Bad Request)} if the employeeProjectRatings20190826 is not valid,
     * or with status {@code 404 (Not Found)} if the employeeProjectRatings20190826 is not found,
     * or with status {@code 500 (Internal Server Error)} if the employeeProjectRatings20190826 couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/employee-project-ratings-20190826-s/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmployeeProjectRatings20190826> partialUpdateEmployeeProjectRatings20190826(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmployeeProjectRatings20190826 employeeProjectRatings20190826
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmployeeProjectRatings20190826 partially : {}, {}", id, employeeProjectRatings20190826);
        if (employeeProjectRatings20190826.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeProjectRatings20190826.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeProjectRatings20190826Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmployeeProjectRatings20190826> result = employeeProjectRatings20190826Service.partialUpdate(
            employeeProjectRatings20190826
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeProjectRatings20190826.getId().toString())
        );
    }

    /**
     * {@code GET  /employee-project-ratings-20190826-s} : get all the employeeProjectRatings20190826s.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeProjectRatings20190826s in body.
     */
    @GetMapping("/employee-project-ratings-20190826-s")
    public ResponseEntity<List<EmployeeProjectRatings20190826>> getAllEmployeeProjectRatings20190826s(
        EmployeeProjectRatings20190826Criteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EmployeeProjectRatings20190826s by criteria: {}", criteria);
        Page<EmployeeProjectRatings20190826> page = employeeProjectRatings20190826QueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-project-ratings-20190826-s/count} : count all the employeeProjectRatings20190826s.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employee-project-ratings-20190826-s/count")
    public ResponseEntity<Long> countEmployeeProjectRatings20190826s(EmployeeProjectRatings20190826Criteria criteria) {
        log.debug("REST request to count EmployeeProjectRatings20190826s by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeeProjectRatings20190826QueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employee-project-ratings-20190826-s/:id} : get the "id" employeeProjectRatings20190826.
     *
     * @param id the id of the employeeProjectRatings20190826 to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeProjectRatings20190826, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-project-ratings-20190826-s/{id}")
    public ResponseEntity<EmployeeProjectRatings20190826> getEmployeeProjectRatings20190826(@PathVariable Long id) {
        log.debug("REST request to get EmployeeProjectRatings20190826 : {}", id);
        Optional<EmployeeProjectRatings20190826> employeeProjectRatings20190826 = employeeProjectRatings20190826Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeProjectRatings20190826);
    }

    /**
     * {@code DELETE  /employee-project-ratings-20190826-s/:id} : delete the "id" employeeProjectRatings20190826.
     *
     * @param id the id of the employeeProjectRatings20190826 to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-project-ratings-20190826-s/{id}")
    public ResponseEntity<Void> deleteEmployeeProjectRatings20190826(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeProjectRatings20190826 : {}", id);
        employeeProjectRatings20190826Service.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
