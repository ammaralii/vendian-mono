package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.EmployeeJobInfo;
import com.venturedive.vendian_mono.repository.EmployeeJobInfoRepository;
import com.venturedive.vendian_mono.service.EmployeeJobInfoQueryService;
import com.venturedive.vendian_mono.service.EmployeeJobInfoService;
import com.venturedive.vendian_mono.service.criteria.EmployeeJobInfoCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.EmployeeJobInfo}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeJobInfoResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeJobInfoResource.class);

    private static final String ENTITY_NAME = "employeeJobInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeJobInfoService employeeJobInfoService;

    private final EmployeeJobInfoRepository employeeJobInfoRepository;

    private final EmployeeJobInfoQueryService employeeJobInfoQueryService;

    public EmployeeJobInfoResource(
        EmployeeJobInfoService employeeJobInfoService,
        EmployeeJobInfoRepository employeeJobInfoRepository,
        EmployeeJobInfoQueryService employeeJobInfoQueryService
    ) {
        this.employeeJobInfoService = employeeJobInfoService;
        this.employeeJobInfoRepository = employeeJobInfoRepository;
        this.employeeJobInfoQueryService = employeeJobInfoQueryService;
    }

    /**
     * {@code POST  /employee-job-infos} : Create a new employeeJobInfo.
     *
     * @param employeeJobInfo the employeeJobInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeJobInfo, or with status {@code 400 (Bad Request)} if the employeeJobInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-job-infos")
    public ResponseEntity<EmployeeJobInfo> createEmployeeJobInfo(@Valid @RequestBody EmployeeJobInfo employeeJobInfo)
        throws URISyntaxException {
        log.debug("REST request to save EmployeeJobInfo : {}", employeeJobInfo);
        if (employeeJobInfo.getId() != null) {
            throw new BadRequestAlertException("A new employeeJobInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeJobInfo result = employeeJobInfoService.save(employeeJobInfo);
        return ResponseEntity
            .created(new URI("/api/employee-job-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-job-infos/:id} : Updates an existing employeeJobInfo.
     *
     * @param id the id of the employeeJobInfo to save.
     * @param employeeJobInfo the employeeJobInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeJobInfo,
     * or with status {@code 400 (Bad Request)} if the employeeJobInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeJobInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-job-infos/{id}")
    public ResponseEntity<EmployeeJobInfo> updateEmployeeJobInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EmployeeJobInfo employeeJobInfo
    ) throws URISyntaxException {
        log.debug("REST request to update EmployeeJobInfo : {}, {}", id, employeeJobInfo);
        if (employeeJobInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeJobInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeJobInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmployeeJobInfo result = employeeJobInfoService.update(employeeJobInfo);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeJobInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /employee-job-infos/:id} : Partial updates given fields of an existing employeeJobInfo, field will ignore if it is null
     *
     * @param id the id of the employeeJobInfo to save.
     * @param employeeJobInfo the employeeJobInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeJobInfo,
     * or with status {@code 400 (Bad Request)} if the employeeJobInfo is not valid,
     * or with status {@code 404 (Not Found)} if the employeeJobInfo is not found,
     * or with status {@code 500 (Internal Server Error)} if the employeeJobInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/employee-job-infos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmployeeJobInfo> partialUpdateEmployeeJobInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EmployeeJobInfo employeeJobInfo
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmployeeJobInfo partially : {}, {}", id, employeeJobInfo);
        if (employeeJobInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeJobInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeJobInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmployeeJobInfo> result = employeeJobInfoService.partialUpdate(employeeJobInfo);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeJobInfo.getId().toString())
        );
    }

    /**
     * {@code GET  /employee-job-infos} : get all the employeeJobInfos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeJobInfos in body.
     */
    @GetMapping("/employee-job-infos")
    public ResponseEntity<List<EmployeeJobInfo>> getAllEmployeeJobInfos(
        EmployeeJobInfoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EmployeeJobInfos by criteria: {}", criteria);
        Page<EmployeeJobInfo> page = employeeJobInfoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-job-infos/count} : count all the employeeJobInfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employee-job-infos/count")
    public ResponseEntity<Long> countEmployeeJobInfos(EmployeeJobInfoCriteria criteria) {
        log.debug("REST request to count EmployeeJobInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeeJobInfoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employee-job-infos/:id} : get the "id" employeeJobInfo.
     *
     * @param id the id of the employeeJobInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeJobInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-job-infos/{id}")
    public ResponseEntity<EmployeeJobInfo> getEmployeeJobInfo(@PathVariable Long id) {
        log.debug("REST request to get EmployeeJobInfo : {}", id);
        Optional<EmployeeJobInfo> employeeJobInfo = employeeJobInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeJobInfo);
    }

    /**
     * {@code DELETE  /employee-job-infos/:id} : delete the "id" employeeJobInfo.
     *
     * @param id the id of the employeeJobInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-job-infos/{id}")
    public ResponseEntity<Void> deleteEmployeeJobInfo(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeJobInfo : {}", id);
        employeeJobInfoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
