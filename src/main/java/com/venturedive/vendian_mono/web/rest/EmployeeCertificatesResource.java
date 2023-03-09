package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.EmployeeCertificates;
import com.venturedive.vendian_mono.repository.EmployeeCertificatesRepository;
import com.venturedive.vendian_mono.service.EmployeeCertificatesQueryService;
import com.venturedive.vendian_mono.service.EmployeeCertificatesService;
import com.venturedive.vendian_mono.service.criteria.EmployeeCertificatesCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.EmployeeCertificates}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeCertificatesResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeCertificatesResource.class);

    private static final String ENTITY_NAME = "employeeCertificates";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeCertificatesService employeeCertificatesService;

    private final EmployeeCertificatesRepository employeeCertificatesRepository;

    private final EmployeeCertificatesQueryService employeeCertificatesQueryService;

    public EmployeeCertificatesResource(
        EmployeeCertificatesService employeeCertificatesService,
        EmployeeCertificatesRepository employeeCertificatesRepository,
        EmployeeCertificatesQueryService employeeCertificatesQueryService
    ) {
        this.employeeCertificatesService = employeeCertificatesService;
        this.employeeCertificatesRepository = employeeCertificatesRepository;
        this.employeeCertificatesQueryService = employeeCertificatesQueryService;
    }

    /**
     * {@code POST  /employee-certificates} : Create a new employeeCertificates.
     *
     * @param employeeCertificates the employeeCertificates to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeCertificates, or with status {@code 400 (Bad Request)} if the employeeCertificates has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-certificates")
    public ResponseEntity<EmployeeCertificates> createEmployeeCertificates(@Valid @RequestBody EmployeeCertificates employeeCertificates)
        throws URISyntaxException {
        log.debug("REST request to save EmployeeCertificates : {}", employeeCertificates);
        if (employeeCertificates.getId() != null) {
            throw new BadRequestAlertException("A new employeeCertificates cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeCertificates result = employeeCertificatesService.save(employeeCertificates);
        return ResponseEntity
            .created(new URI("/api/employee-certificates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-certificates/:id} : Updates an existing employeeCertificates.
     *
     * @param id the id of the employeeCertificates to save.
     * @param employeeCertificates the employeeCertificates to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeCertificates,
     * or with status {@code 400 (Bad Request)} if the employeeCertificates is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeCertificates couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-certificates/{id}")
    public ResponseEntity<EmployeeCertificates> updateEmployeeCertificates(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EmployeeCertificates employeeCertificates
    ) throws URISyntaxException {
        log.debug("REST request to update EmployeeCertificates : {}, {}", id, employeeCertificates);
        if (employeeCertificates.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeCertificates.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeCertificatesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmployeeCertificates result = employeeCertificatesService.update(employeeCertificates);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeCertificates.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /employee-certificates/:id} : Partial updates given fields of an existing employeeCertificates, field will ignore if it is null
     *
     * @param id the id of the employeeCertificates to save.
     * @param employeeCertificates the employeeCertificates to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeCertificates,
     * or with status {@code 400 (Bad Request)} if the employeeCertificates is not valid,
     * or with status {@code 404 (Not Found)} if the employeeCertificates is not found,
     * or with status {@code 500 (Internal Server Error)} if the employeeCertificates couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/employee-certificates/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmployeeCertificates> partialUpdateEmployeeCertificates(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EmployeeCertificates employeeCertificates
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmployeeCertificates partially : {}, {}", id, employeeCertificates);
        if (employeeCertificates.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeCertificates.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeCertificatesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmployeeCertificates> result = employeeCertificatesService.partialUpdate(employeeCertificates);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeCertificates.getId().toString())
        );
    }

    /**
     * {@code GET  /employee-certificates} : get all the employeeCertificates.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeCertificates in body.
     */
    @GetMapping("/employee-certificates")
    public ResponseEntity<List<EmployeeCertificates>> getAllEmployeeCertificates(
        EmployeeCertificatesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EmployeeCertificates by criteria: {}", criteria);
        Page<EmployeeCertificates> page = employeeCertificatesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-certificates/count} : count all the employeeCertificates.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employee-certificates/count")
    public ResponseEntity<Long> countEmployeeCertificates(EmployeeCertificatesCriteria criteria) {
        log.debug("REST request to count EmployeeCertificates by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeeCertificatesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employee-certificates/:id} : get the "id" employeeCertificates.
     *
     * @param id the id of the employeeCertificates to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeCertificates, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-certificates/{id}")
    public ResponseEntity<EmployeeCertificates> getEmployeeCertificates(@PathVariable Long id) {
        log.debug("REST request to get EmployeeCertificates : {}", id);
        Optional<EmployeeCertificates> employeeCertificates = employeeCertificatesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeCertificates);
    }

    /**
     * {@code DELETE  /employee-certificates/:id} : delete the "id" employeeCertificates.
     *
     * @param id the id of the employeeCertificates to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-certificates/{id}")
    public ResponseEntity<Void> deleteEmployeeCertificates(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeCertificates : {}", id);
        employeeCertificatesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
