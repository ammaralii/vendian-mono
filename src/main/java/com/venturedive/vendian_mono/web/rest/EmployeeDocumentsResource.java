package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.EmployeeDocuments;
import com.venturedive.vendian_mono.repository.EmployeeDocumentsRepository;
import com.venturedive.vendian_mono.service.EmployeeDocumentsQueryService;
import com.venturedive.vendian_mono.service.EmployeeDocumentsService;
import com.venturedive.vendian_mono.service.criteria.EmployeeDocumentsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.EmployeeDocuments}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeDocumentsResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeDocumentsResource.class);

    private static final String ENTITY_NAME = "employeeDocuments";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeDocumentsService employeeDocumentsService;

    private final EmployeeDocumentsRepository employeeDocumentsRepository;

    private final EmployeeDocumentsQueryService employeeDocumentsQueryService;

    public EmployeeDocumentsResource(
        EmployeeDocumentsService employeeDocumentsService,
        EmployeeDocumentsRepository employeeDocumentsRepository,
        EmployeeDocumentsQueryService employeeDocumentsQueryService
    ) {
        this.employeeDocumentsService = employeeDocumentsService;
        this.employeeDocumentsRepository = employeeDocumentsRepository;
        this.employeeDocumentsQueryService = employeeDocumentsQueryService;
    }

    /**
     * {@code POST  /employee-documents} : Create a new employeeDocuments.
     *
     * @param employeeDocuments the employeeDocuments to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeDocuments, or with status {@code 400 (Bad Request)} if the employeeDocuments has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-documents")
    public ResponseEntity<EmployeeDocuments> createEmployeeDocuments(@Valid @RequestBody EmployeeDocuments employeeDocuments)
        throws URISyntaxException {
        log.debug("REST request to save EmployeeDocuments : {}", employeeDocuments);
        if (employeeDocuments.getId() != null) {
            throw new BadRequestAlertException("A new employeeDocuments cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeDocuments result = employeeDocumentsService.save(employeeDocuments);
        return ResponseEntity
            .created(new URI("/api/employee-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-documents/:id} : Updates an existing employeeDocuments.
     *
     * @param id the id of the employeeDocuments to save.
     * @param employeeDocuments the employeeDocuments to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeDocuments,
     * or with status {@code 400 (Bad Request)} if the employeeDocuments is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeDocuments couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-documents/{id}")
    public ResponseEntity<EmployeeDocuments> updateEmployeeDocuments(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EmployeeDocuments employeeDocuments
    ) throws URISyntaxException {
        log.debug("REST request to update EmployeeDocuments : {}, {}", id, employeeDocuments);
        if (employeeDocuments.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeDocuments.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeDocumentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmployeeDocuments result = employeeDocumentsService.update(employeeDocuments);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeDocuments.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /employee-documents/:id} : Partial updates given fields of an existing employeeDocuments, field will ignore if it is null
     *
     * @param id the id of the employeeDocuments to save.
     * @param employeeDocuments the employeeDocuments to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeDocuments,
     * or with status {@code 400 (Bad Request)} if the employeeDocuments is not valid,
     * or with status {@code 404 (Not Found)} if the employeeDocuments is not found,
     * or with status {@code 500 (Internal Server Error)} if the employeeDocuments couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/employee-documents/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmployeeDocuments> partialUpdateEmployeeDocuments(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EmployeeDocuments employeeDocuments
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmployeeDocuments partially : {}, {}", id, employeeDocuments);
        if (employeeDocuments.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeDocuments.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeDocumentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmployeeDocuments> result = employeeDocumentsService.partialUpdate(employeeDocuments);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeDocuments.getId().toString())
        );
    }

    /**
     * {@code GET  /employee-documents} : get all the employeeDocuments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeDocuments in body.
     */
    @GetMapping("/employee-documents")
    public ResponseEntity<List<EmployeeDocuments>> getAllEmployeeDocuments(
        EmployeeDocumentsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EmployeeDocuments by criteria: {}", criteria);
        Page<EmployeeDocuments> page = employeeDocumentsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-documents/count} : count all the employeeDocuments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employee-documents/count")
    public ResponseEntity<Long> countEmployeeDocuments(EmployeeDocumentsCriteria criteria) {
        log.debug("REST request to count EmployeeDocuments by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeeDocumentsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employee-documents/:id} : get the "id" employeeDocuments.
     *
     * @param id the id of the employeeDocuments to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeDocuments, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-documents/{id}")
    public ResponseEntity<EmployeeDocuments> getEmployeeDocuments(@PathVariable Long id) {
        log.debug("REST request to get EmployeeDocuments : {}", id);
        Optional<EmployeeDocuments> employeeDocuments = employeeDocumentsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeDocuments);
    }

    /**
     * {@code DELETE  /employee-documents/:id} : delete the "id" employeeDocuments.
     *
     * @param id the id of the employeeDocuments to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-documents/{id}")
    public ResponseEntity<Void> deleteEmployeeDocuments(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeDocuments : {}", id);
        employeeDocumentsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
