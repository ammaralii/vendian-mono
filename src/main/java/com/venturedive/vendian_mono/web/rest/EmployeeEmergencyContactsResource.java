package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.EmployeeEmergencyContacts;
import com.venturedive.vendian_mono.repository.EmployeeEmergencyContactsRepository;
import com.venturedive.vendian_mono.service.EmployeeEmergencyContactsQueryService;
import com.venturedive.vendian_mono.service.EmployeeEmergencyContactsService;
import com.venturedive.vendian_mono.service.criteria.EmployeeEmergencyContactsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.EmployeeEmergencyContacts}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeEmergencyContactsResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeEmergencyContactsResource.class);

    private static final String ENTITY_NAME = "employeeEmergencyContacts";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeEmergencyContactsService employeeEmergencyContactsService;

    private final EmployeeEmergencyContactsRepository employeeEmergencyContactsRepository;

    private final EmployeeEmergencyContactsQueryService employeeEmergencyContactsQueryService;

    public EmployeeEmergencyContactsResource(
        EmployeeEmergencyContactsService employeeEmergencyContactsService,
        EmployeeEmergencyContactsRepository employeeEmergencyContactsRepository,
        EmployeeEmergencyContactsQueryService employeeEmergencyContactsQueryService
    ) {
        this.employeeEmergencyContactsService = employeeEmergencyContactsService;
        this.employeeEmergencyContactsRepository = employeeEmergencyContactsRepository;
        this.employeeEmergencyContactsQueryService = employeeEmergencyContactsQueryService;
    }

    /**
     * {@code POST  /employee-emergency-contacts} : Create a new employeeEmergencyContacts.
     *
     * @param employeeEmergencyContacts the employeeEmergencyContacts to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeEmergencyContacts, or with status {@code 400 (Bad Request)} if the employeeEmergencyContacts has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-emergency-contacts")
    public ResponseEntity<EmployeeEmergencyContacts> createEmployeeEmergencyContacts(
        @Valid @RequestBody EmployeeEmergencyContacts employeeEmergencyContacts
    ) throws URISyntaxException {
        log.debug("REST request to save EmployeeEmergencyContacts : {}", employeeEmergencyContacts);
        if (employeeEmergencyContacts.getId() != null) {
            throw new BadRequestAlertException("A new employeeEmergencyContacts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeEmergencyContacts result = employeeEmergencyContactsService.save(employeeEmergencyContacts);
        return ResponseEntity
            .created(new URI("/api/employee-emergency-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-emergency-contacts/:id} : Updates an existing employeeEmergencyContacts.
     *
     * @param id the id of the employeeEmergencyContacts to save.
     * @param employeeEmergencyContacts the employeeEmergencyContacts to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeEmergencyContacts,
     * or with status {@code 400 (Bad Request)} if the employeeEmergencyContacts is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeEmergencyContacts couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-emergency-contacts/{id}")
    public ResponseEntity<EmployeeEmergencyContacts> updateEmployeeEmergencyContacts(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EmployeeEmergencyContacts employeeEmergencyContacts
    ) throws URISyntaxException {
        log.debug("REST request to update EmployeeEmergencyContacts : {}, {}", id, employeeEmergencyContacts);
        if (employeeEmergencyContacts.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeEmergencyContacts.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeEmergencyContactsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmployeeEmergencyContacts result = employeeEmergencyContactsService.update(employeeEmergencyContacts);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeEmergencyContacts.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /employee-emergency-contacts/:id} : Partial updates given fields of an existing employeeEmergencyContacts, field will ignore if it is null
     *
     * @param id the id of the employeeEmergencyContacts to save.
     * @param employeeEmergencyContacts the employeeEmergencyContacts to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeEmergencyContacts,
     * or with status {@code 400 (Bad Request)} if the employeeEmergencyContacts is not valid,
     * or with status {@code 404 (Not Found)} if the employeeEmergencyContacts is not found,
     * or with status {@code 500 (Internal Server Error)} if the employeeEmergencyContacts couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/employee-emergency-contacts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmployeeEmergencyContacts> partialUpdateEmployeeEmergencyContacts(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EmployeeEmergencyContacts employeeEmergencyContacts
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmployeeEmergencyContacts partially : {}, {}", id, employeeEmergencyContacts);
        if (employeeEmergencyContacts.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeEmergencyContacts.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeEmergencyContactsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmployeeEmergencyContacts> result = employeeEmergencyContactsService.partialUpdate(employeeEmergencyContacts);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeEmergencyContacts.getId().toString())
        );
    }

    /**
     * {@code GET  /employee-emergency-contacts} : get all the employeeEmergencyContacts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeEmergencyContacts in body.
     */
    @GetMapping("/employee-emergency-contacts")
    public ResponseEntity<List<EmployeeEmergencyContacts>> getAllEmployeeEmergencyContacts(
        EmployeeEmergencyContactsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EmployeeEmergencyContacts by criteria: {}", criteria);
        Page<EmployeeEmergencyContacts> page = employeeEmergencyContactsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-emergency-contacts/count} : count all the employeeEmergencyContacts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employee-emergency-contacts/count")
    public ResponseEntity<Long> countEmployeeEmergencyContacts(EmployeeEmergencyContactsCriteria criteria) {
        log.debug("REST request to count EmployeeEmergencyContacts by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeeEmergencyContactsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employee-emergency-contacts/:id} : get the "id" employeeEmergencyContacts.
     *
     * @param id the id of the employeeEmergencyContacts to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeEmergencyContacts, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-emergency-contacts/{id}")
    public ResponseEntity<EmployeeEmergencyContacts> getEmployeeEmergencyContacts(@PathVariable Long id) {
        log.debug("REST request to get EmployeeEmergencyContacts : {}", id);
        Optional<EmployeeEmergencyContacts> employeeEmergencyContacts = employeeEmergencyContactsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeEmergencyContacts);
    }

    /**
     * {@code DELETE  /employee-emergency-contacts/:id} : delete the "id" employeeEmergencyContacts.
     *
     * @param id the id of the employeeEmergencyContacts to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-emergency-contacts/{id}")
    public ResponseEntity<Void> deleteEmployeeEmergencyContacts(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeEmergencyContacts : {}", id);
        employeeEmergencyContactsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
