package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.EmployeeAddresses;
import com.venturedive.vendian_mono.repository.EmployeeAddressesRepository;
import com.venturedive.vendian_mono.service.EmployeeAddressesQueryService;
import com.venturedive.vendian_mono.service.EmployeeAddressesService;
import com.venturedive.vendian_mono.service.criteria.EmployeeAddressesCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.EmployeeAddresses}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeAddressesResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeAddressesResource.class);

    private static final String ENTITY_NAME = "employeeAddresses";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeAddressesService employeeAddressesService;

    private final EmployeeAddressesRepository employeeAddressesRepository;

    private final EmployeeAddressesQueryService employeeAddressesQueryService;

    public EmployeeAddressesResource(
        EmployeeAddressesService employeeAddressesService,
        EmployeeAddressesRepository employeeAddressesRepository,
        EmployeeAddressesQueryService employeeAddressesQueryService
    ) {
        this.employeeAddressesService = employeeAddressesService;
        this.employeeAddressesRepository = employeeAddressesRepository;
        this.employeeAddressesQueryService = employeeAddressesQueryService;
    }

    /**
     * {@code POST  /employee-addresses} : Create a new employeeAddresses.
     *
     * @param employeeAddresses the employeeAddresses to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeAddresses, or with status {@code 400 (Bad Request)} if the employeeAddresses has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-addresses")
    public ResponseEntity<EmployeeAddresses> createEmployeeAddresses(@Valid @RequestBody EmployeeAddresses employeeAddresses)
        throws URISyntaxException {
        log.debug("REST request to save EmployeeAddresses : {}", employeeAddresses);
        if (employeeAddresses.getId() != null) {
            throw new BadRequestAlertException("A new employeeAddresses cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeAddresses result = employeeAddressesService.save(employeeAddresses);
        return ResponseEntity
            .created(new URI("/api/employee-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-addresses/:id} : Updates an existing employeeAddresses.
     *
     * @param id the id of the employeeAddresses to save.
     * @param employeeAddresses the employeeAddresses to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeAddresses,
     * or with status {@code 400 (Bad Request)} if the employeeAddresses is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeAddresses couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-addresses/{id}")
    public ResponseEntity<EmployeeAddresses> updateEmployeeAddresses(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EmployeeAddresses employeeAddresses
    ) throws URISyntaxException {
        log.debug("REST request to update EmployeeAddresses : {}, {}", id, employeeAddresses);
        if (employeeAddresses.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeAddresses.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeAddressesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmployeeAddresses result = employeeAddressesService.update(employeeAddresses);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeAddresses.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /employee-addresses/:id} : Partial updates given fields of an existing employeeAddresses, field will ignore if it is null
     *
     * @param id the id of the employeeAddresses to save.
     * @param employeeAddresses the employeeAddresses to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeAddresses,
     * or with status {@code 400 (Bad Request)} if the employeeAddresses is not valid,
     * or with status {@code 404 (Not Found)} if the employeeAddresses is not found,
     * or with status {@code 500 (Internal Server Error)} if the employeeAddresses couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/employee-addresses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmployeeAddresses> partialUpdateEmployeeAddresses(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EmployeeAddresses employeeAddresses
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmployeeAddresses partially : {}, {}", id, employeeAddresses);
        if (employeeAddresses.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeAddresses.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeAddressesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmployeeAddresses> result = employeeAddressesService.partialUpdate(employeeAddresses);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeAddresses.getId().toString())
        );
    }

    /**
     * {@code GET  /employee-addresses} : get all the employeeAddresses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeAddresses in body.
     */
    @GetMapping("/employee-addresses")
    public ResponseEntity<List<EmployeeAddresses>> getAllEmployeeAddresses(
        EmployeeAddressesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EmployeeAddresses by criteria: {}", criteria);
        Page<EmployeeAddresses> page = employeeAddressesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-addresses/count} : count all the employeeAddresses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employee-addresses/count")
    public ResponseEntity<Long> countEmployeeAddresses(EmployeeAddressesCriteria criteria) {
        log.debug("REST request to count EmployeeAddresses by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeeAddressesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employee-addresses/:id} : get the "id" employeeAddresses.
     *
     * @param id the id of the employeeAddresses to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeAddresses, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-addresses/{id}")
    public ResponseEntity<EmployeeAddresses> getEmployeeAddresses(@PathVariable Long id) {
        log.debug("REST request to get EmployeeAddresses : {}", id);
        Optional<EmployeeAddresses> employeeAddresses = employeeAddressesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeAddresses);
    }

    /**
     * {@code DELETE  /employee-addresses/:id} : delete the "id" employeeAddresses.
     *
     * @param id the id of the employeeAddresses to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-addresses/{id}")
    public ResponseEntity<Void> deleteEmployeeAddresses(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeAddresses : {}", id);
        employeeAddressesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
