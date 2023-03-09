package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.EmployeeBirthdays;
import com.venturedive.vendian_mono.repository.EmployeeBirthdaysRepository;
import com.venturedive.vendian_mono.service.EmployeeBirthdaysQueryService;
import com.venturedive.vendian_mono.service.EmployeeBirthdaysService;
import com.venturedive.vendian_mono.service.criteria.EmployeeBirthdaysCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.EmployeeBirthdays}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeBirthdaysResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeBirthdaysResource.class);

    private static final String ENTITY_NAME = "employeeBirthdays";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeBirthdaysService employeeBirthdaysService;

    private final EmployeeBirthdaysRepository employeeBirthdaysRepository;

    private final EmployeeBirthdaysQueryService employeeBirthdaysQueryService;

    public EmployeeBirthdaysResource(
        EmployeeBirthdaysService employeeBirthdaysService,
        EmployeeBirthdaysRepository employeeBirthdaysRepository,
        EmployeeBirthdaysQueryService employeeBirthdaysQueryService
    ) {
        this.employeeBirthdaysService = employeeBirthdaysService;
        this.employeeBirthdaysRepository = employeeBirthdaysRepository;
        this.employeeBirthdaysQueryService = employeeBirthdaysQueryService;
    }

    /**
     * {@code POST  /employee-birthdays} : Create a new employeeBirthdays.
     *
     * @param employeeBirthdays the employeeBirthdays to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeBirthdays, or with status {@code 400 (Bad Request)} if the employeeBirthdays has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-birthdays")
    public ResponseEntity<EmployeeBirthdays> createEmployeeBirthdays(@Valid @RequestBody EmployeeBirthdays employeeBirthdays)
        throws URISyntaxException {
        log.debug("REST request to save EmployeeBirthdays : {}", employeeBirthdays);
        if (employeeBirthdays.getId() != null) {
            throw new BadRequestAlertException("A new employeeBirthdays cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeBirthdays result = employeeBirthdaysService.save(employeeBirthdays);
        return ResponseEntity
            .created(new URI("/api/employee-birthdays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-birthdays/:id} : Updates an existing employeeBirthdays.
     *
     * @param id the id of the employeeBirthdays to save.
     * @param employeeBirthdays the employeeBirthdays to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeBirthdays,
     * or with status {@code 400 (Bad Request)} if the employeeBirthdays is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeBirthdays couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-birthdays/{id}")
    public ResponseEntity<EmployeeBirthdays> updateEmployeeBirthdays(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EmployeeBirthdays employeeBirthdays
    ) throws URISyntaxException {
        log.debug("REST request to update EmployeeBirthdays : {}, {}", id, employeeBirthdays);
        if (employeeBirthdays.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeBirthdays.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeBirthdaysRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmployeeBirthdays result = employeeBirthdaysService.update(employeeBirthdays);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeBirthdays.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /employee-birthdays/:id} : Partial updates given fields of an existing employeeBirthdays, field will ignore if it is null
     *
     * @param id the id of the employeeBirthdays to save.
     * @param employeeBirthdays the employeeBirthdays to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeBirthdays,
     * or with status {@code 400 (Bad Request)} if the employeeBirthdays is not valid,
     * or with status {@code 404 (Not Found)} if the employeeBirthdays is not found,
     * or with status {@code 500 (Internal Server Error)} if the employeeBirthdays couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/employee-birthdays/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmployeeBirthdays> partialUpdateEmployeeBirthdays(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EmployeeBirthdays employeeBirthdays
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmployeeBirthdays partially : {}, {}", id, employeeBirthdays);
        if (employeeBirthdays.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeBirthdays.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeBirthdaysRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmployeeBirthdays> result = employeeBirthdaysService.partialUpdate(employeeBirthdays);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeBirthdays.getId().toString())
        );
    }

    /**
     * {@code GET  /employee-birthdays} : get all the employeeBirthdays.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeBirthdays in body.
     */
    @GetMapping("/employee-birthdays")
    public ResponseEntity<List<EmployeeBirthdays>> getAllEmployeeBirthdays(
        EmployeeBirthdaysCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EmployeeBirthdays by criteria: {}", criteria);
        Page<EmployeeBirthdays> page = employeeBirthdaysQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-birthdays/count} : count all the employeeBirthdays.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employee-birthdays/count")
    public ResponseEntity<Long> countEmployeeBirthdays(EmployeeBirthdaysCriteria criteria) {
        log.debug("REST request to count EmployeeBirthdays by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeeBirthdaysQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employee-birthdays/:id} : get the "id" employeeBirthdays.
     *
     * @param id the id of the employeeBirthdays to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeBirthdays, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-birthdays/{id}")
    public ResponseEntity<EmployeeBirthdays> getEmployeeBirthdays(@PathVariable Long id) {
        log.debug("REST request to get EmployeeBirthdays : {}", id);
        Optional<EmployeeBirthdays> employeeBirthdays = employeeBirthdaysService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeBirthdays);
    }

    /**
     * {@code DELETE  /employee-birthdays/:id} : delete the "id" employeeBirthdays.
     *
     * @param id the id of the employeeBirthdays to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-birthdays/{id}")
    public ResponseEntity<Void> deleteEmployeeBirthdays(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeBirthdays : {}", id);
        employeeBirthdaysService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
