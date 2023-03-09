package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.EmployeeFamilyInfo;
import com.venturedive.vendian_mono.repository.EmployeeFamilyInfoRepository;
import com.venturedive.vendian_mono.service.EmployeeFamilyInfoQueryService;
import com.venturedive.vendian_mono.service.EmployeeFamilyInfoService;
import com.venturedive.vendian_mono.service.criteria.EmployeeFamilyInfoCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.EmployeeFamilyInfo}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeFamilyInfoResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeFamilyInfoResource.class);

    private static final String ENTITY_NAME = "employeeFamilyInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeFamilyInfoService employeeFamilyInfoService;

    private final EmployeeFamilyInfoRepository employeeFamilyInfoRepository;

    private final EmployeeFamilyInfoQueryService employeeFamilyInfoQueryService;

    public EmployeeFamilyInfoResource(
        EmployeeFamilyInfoService employeeFamilyInfoService,
        EmployeeFamilyInfoRepository employeeFamilyInfoRepository,
        EmployeeFamilyInfoQueryService employeeFamilyInfoQueryService
    ) {
        this.employeeFamilyInfoService = employeeFamilyInfoService;
        this.employeeFamilyInfoRepository = employeeFamilyInfoRepository;
        this.employeeFamilyInfoQueryService = employeeFamilyInfoQueryService;
    }

    /**
     * {@code POST  /employee-family-infos} : Create a new employeeFamilyInfo.
     *
     * @param employeeFamilyInfo the employeeFamilyInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeFamilyInfo, or with status {@code 400 (Bad Request)} if the employeeFamilyInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-family-infos")
    public ResponseEntity<EmployeeFamilyInfo> createEmployeeFamilyInfo(@Valid @RequestBody EmployeeFamilyInfo employeeFamilyInfo)
        throws URISyntaxException {
        log.debug("REST request to save EmployeeFamilyInfo : {}", employeeFamilyInfo);
        if (employeeFamilyInfo.getId() != null) {
            throw new BadRequestAlertException("A new employeeFamilyInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeFamilyInfo result = employeeFamilyInfoService.save(employeeFamilyInfo);
        return ResponseEntity
            .created(new URI("/api/employee-family-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-family-infos/:id} : Updates an existing employeeFamilyInfo.
     *
     * @param id the id of the employeeFamilyInfo to save.
     * @param employeeFamilyInfo the employeeFamilyInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeFamilyInfo,
     * or with status {@code 400 (Bad Request)} if the employeeFamilyInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeFamilyInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-family-infos/{id}")
    public ResponseEntity<EmployeeFamilyInfo> updateEmployeeFamilyInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EmployeeFamilyInfo employeeFamilyInfo
    ) throws URISyntaxException {
        log.debug("REST request to update EmployeeFamilyInfo : {}, {}", id, employeeFamilyInfo);
        if (employeeFamilyInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeFamilyInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeFamilyInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmployeeFamilyInfo result = employeeFamilyInfoService.update(employeeFamilyInfo);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeFamilyInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /employee-family-infos/:id} : Partial updates given fields of an existing employeeFamilyInfo, field will ignore if it is null
     *
     * @param id the id of the employeeFamilyInfo to save.
     * @param employeeFamilyInfo the employeeFamilyInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeFamilyInfo,
     * or with status {@code 400 (Bad Request)} if the employeeFamilyInfo is not valid,
     * or with status {@code 404 (Not Found)} if the employeeFamilyInfo is not found,
     * or with status {@code 500 (Internal Server Error)} if the employeeFamilyInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/employee-family-infos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmployeeFamilyInfo> partialUpdateEmployeeFamilyInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EmployeeFamilyInfo employeeFamilyInfo
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmployeeFamilyInfo partially : {}, {}", id, employeeFamilyInfo);
        if (employeeFamilyInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeFamilyInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeFamilyInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmployeeFamilyInfo> result = employeeFamilyInfoService.partialUpdate(employeeFamilyInfo);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeFamilyInfo.getId().toString())
        );
    }

    /**
     * {@code GET  /employee-family-infos} : get all the employeeFamilyInfos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeFamilyInfos in body.
     */
    @GetMapping("/employee-family-infos")
    public ResponseEntity<List<EmployeeFamilyInfo>> getAllEmployeeFamilyInfos(
        EmployeeFamilyInfoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EmployeeFamilyInfos by criteria: {}", criteria);
        Page<EmployeeFamilyInfo> page = employeeFamilyInfoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-family-infos/count} : count all the employeeFamilyInfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employee-family-infos/count")
    public ResponseEntity<Long> countEmployeeFamilyInfos(EmployeeFamilyInfoCriteria criteria) {
        log.debug("REST request to count EmployeeFamilyInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeeFamilyInfoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employee-family-infos/:id} : get the "id" employeeFamilyInfo.
     *
     * @param id the id of the employeeFamilyInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeFamilyInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-family-infos/{id}")
    public ResponseEntity<EmployeeFamilyInfo> getEmployeeFamilyInfo(@PathVariable Long id) {
        log.debug("REST request to get EmployeeFamilyInfo : {}", id);
        Optional<EmployeeFamilyInfo> employeeFamilyInfo = employeeFamilyInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeFamilyInfo);
    }

    /**
     * {@code DELETE  /employee-family-infos/:id} : delete the "id" employeeFamilyInfo.
     *
     * @param id the id of the employeeFamilyInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-family-infos/{id}")
    public ResponseEntity<Void> deleteEmployeeFamilyInfo(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeFamilyInfo : {}", id);
        employeeFamilyInfoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
