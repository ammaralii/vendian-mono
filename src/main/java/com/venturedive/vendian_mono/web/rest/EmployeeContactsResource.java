package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.EmployeeContacts;
import com.venturedive.vendian_mono.repository.EmployeeContactsRepository;
import com.venturedive.vendian_mono.service.EmployeeContactsQueryService;
import com.venturedive.vendian_mono.service.EmployeeContactsService;
import com.venturedive.vendian_mono.service.criteria.EmployeeContactsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.EmployeeContacts}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeContactsResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeContactsResource.class);

    private static final String ENTITY_NAME = "employeeContacts";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeContactsService employeeContactsService;

    private final EmployeeContactsRepository employeeContactsRepository;

    private final EmployeeContactsQueryService employeeContactsQueryService;

    public EmployeeContactsResource(
        EmployeeContactsService employeeContactsService,
        EmployeeContactsRepository employeeContactsRepository,
        EmployeeContactsQueryService employeeContactsQueryService
    ) {
        this.employeeContactsService = employeeContactsService;
        this.employeeContactsRepository = employeeContactsRepository;
        this.employeeContactsQueryService = employeeContactsQueryService;
    }

    /**
     * {@code POST  /employee-contacts} : Create a new employeeContacts.
     *
     * @param employeeContacts the employeeContacts to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeContacts, or with status {@code 400 (Bad Request)} if the employeeContacts has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-contacts")
    public ResponseEntity<EmployeeContacts> createEmployeeContacts(@Valid @RequestBody EmployeeContacts employeeContacts)
        throws URISyntaxException {
        log.debug("REST request to save EmployeeContacts : {}", employeeContacts);
        if (employeeContacts.getId() != null) {
            throw new BadRequestAlertException("A new employeeContacts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeContacts result = employeeContactsService.save(employeeContacts);
        return ResponseEntity
            .created(new URI("/api/employee-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-contacts/:id} : Updates an existing employeeContacts.
     *
     * @param id the id of the employeeContacts to save.
     * @param employeeContacts the employeeContacts to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeContacts,
     * or with status {@code 400 (Bad Request)} if the employeeContacts is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeContacts couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-contacts/{id}")
    public ResponseEntity<EmployeeContacts> updateEmployeeContacts(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EmployeeContacts employeeContacts
    ) throws URISyntaxException {
        log.debug("REST request to update EmployeeContacts : {}, {}", id, employeeContacts);
        if (employeeContacts.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeContacts.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeContactsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmployeeContacts result = employeeContactsService.update(employeeContacts);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeContacts.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /employee-contacts/:id} : Partial updates given fields of an existing employeeContacts, field will ignore if it is null
     *
     * @param id the id of the employeeContacts to save.
     * @param employeeContacts the employeeContacts to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeContacts,
     * or with status {@code 400 (Bad Request)} if the employeeContacts is not valid,
     * or with status {@code 404 (Not Found)} if the employeeContacts is not found,
     * or with status {@code 500 (Internal Server Error)} if the employeeContacts couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/employee-contacts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmployeeContacts> partialUpdateEmployeeContacts(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EmployeeContacts employeeContacts
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmployeeContacts partially : {}, {}", id, employeeContacts);
        if (employeeContacts.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeContacts.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeContactsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmployeeContacts> result = employeeContactsService.partialUpdate(employeeContacts);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeContacts.getId().toString())
        );
    }

    /**
     * {@code GET  /employee-contacts} : get all the employeeContacts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeContacts in body.
     */
    @GetMapping("/employee-contacts")
    public ResponseEntity<List<EmployeeContacts>> getAllEmployeeContacts(
        EmployeeContactsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EmployeeContacts by criteria: {}", criteria);
        Page<EmployeeContacts> page = employeeContactsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-contacts/count} : count all the employeeContacts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employee-contacts/count")
    public ResponseEntity<Long> countEmployeeContacts(EmployeeContactsCriteria criteria) {
        log.debug("REST request to count EmployeeContacts by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeeContactsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employee-contacts/:id} : get the "id" employeeContacts.
     *
     * @param id the id of the employeeContacts to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeContacts, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-contacts/{id}")
    public ResponseEntity<EmployeeContacts> getEmployeeContacts(@PathVariable Long id) {
        log.debug("REST request to get EmployeeContacts : {}", id);
        Optional<EmployeeContacts> employeeContacts = employeeContactsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeContacts);
    }

    /**
     * {@code DELETE  /employee-contacts/:id} : delete the "id" employeeContacts.
     *
     * @param id the id of the employeeContacts to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-contacts/{id}")
    public ResponseEntity<Void> deleteEmployeeContacts(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeContacts : {}", id);
        employeeContactsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
