package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.ZEmployeeProjects;
import com.venturedive.vendian_mono.repository.ZEmployeeProjectsRepository;
import com.venturedive.vendian_mono.service.ZEmployeeProjectsQueryService;
import com.venturedive.vendian_mono.service.ZEmployeeProjectsService;
import com.venturedive.vendian_mono.service.criteria.ZEmployeeProjectsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.ZEmployeeProjects}.
 */
@RestController
@RequestMapping("/api")
public class ZEmployeeProjectsResource {

    private final Logger log = LoggerFactory.getLogger(ZEmployeeProjectsResource.class);

    private static final String ENTITY_NAME = "zEmployeeProjects";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ZEmployeeProjectsService zEmployeeProjectsService;

    private final ZEmployeeProjectsRepository zEmployeeProjectsRepository;

    private final ZEmployeeProjectsQueryService zEmployeeProjectsQueryService;

    public ZEmployeeProjectsResource(
        ZEmployeeProjectsService zEmployeeProjectsService,
        ZEmployeeProjectsRepository zEmployeeProjectsRepository,
        ZEmployeeProjectsQueryService zEmployeeProjectsQueryService
    ) {
        this.zEmployeeProjectsService = zEmployeeProjectsService;
        this.zEmployeeProjectsRepository = zEmployeeProjectsRepository;
        this.zEmployeeProjectsQueryService = zEmployeeProjectsQueryService;
    }

    /**
     * {@code POST  /z-employee-projects} : Create a new zEmployeeProjects.
     *
     * @param zEmployeeProjects the zEmployeeProjects to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new zEmployeeProjects, or with status {@code 400 (Bad Request)} if the zEmployeeProjects has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/z-employee-projects")
    public ResponseEntity<ZEmployeeProjects> createZEmployeeProjects(@Valid @RequestBody ZEmployeeProjects zEmployeeProjects)
        throws URISyntaxException {
        log.debug("REST request to save ZEmployeeProjects : {}", zEmployeeProjects);
        if (zEmployeeProjects.getId() != null) {
            throw new BadRequestAlertException("A new zEmployeeProjects cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ZEmployeeProjects result = zEmployeeProjectsService.save(zEmployeeProjects);
        return ResponseEntity
            .created(new URI("/api/z-employee-projects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /z-employee-projects/:id} : Updates an existing zEmployeeProjects.
     *
     * @param id the id of the zEmployeeProjects to save.
     * @param zEmployeeProjects the zEmployeeProjects to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated zEmployeeProjects,
     * or with status {@code 400 (Bad Request)} if the zEmployeeProjects is not valid,
     * or with status {@code 500 (Internal Server Error)} if the zEmployeeProjects couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/z-employee-projects/{id}")
    public ResponseEntity<ZEmployeeProjects> updateZEmployeeProjects(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ZEmployeeProjects zEmployeeProjects
    ) throws URISyntaxException {
        log.debug("REST request to update ZEmployeeProjects : {}, {}", id, zEmployeeProjects);
        if (zEmployeeProjects.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, zEmployeeProjects.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!zEmployeeProjectsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ZEmployeeProjects result = zEmployeeProjectsService.update(zEmployeeProjects);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, zEmployeeProjects.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /z-employee-projects/:id} : Partial updates given fields of an existing zEmployeeProjects, field will ignore if it is null
     *
     * @param id the id of the zEmployeeProjects to save.
     * @param zEmployeeProjects the zEmployeeProjects to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated zEmployeeProjects,
     * or with status {@code 400 (Bad Request)} if the zEmployeeProjects is not valid,
     * or with status {@code 404 (Not Found)} if the zEmployeeProjects is not found,
     * or with status {@code 500 (Internal Server Error)} if the zEmployeeProjects couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/z-employee-projects/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ZEmployeeProjects> partialUpdateZEmployeeProjects(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ZEmployeeProjects zEmployeeProjects
    ) throws URISyntaxException {
        log.debug("REST request to partial update ZEmployeeProjects partially : {}, {}", id, zEmployeeProjects);
        if (zEmployeeProjects.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, zEmployeeProjects.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!zEmployeeProjectsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ZEmployeeProjects> result = zEmployeeProjectsService.partialUpdate(zEmployeeProjects);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, zEmployeeProjects.getId().toString())
        );
    }

    /**
     * {@code GET  /z-employee-projects} : get all the zEmployeeProjects.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of zEmployeeProjects in body.
     */
    @GetMapping("/z-employee-projects")
    public ResponseEntity<List<ZEmployeeProjects>> getAllZEmployeeProjects(
        ZEmployeeProjectsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ZEmployeeProjects by criteria: {}", criteria);
        Page<ZEmployeeProjects> page = zEmployeeProjectsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /z-employee-projects/count} : count all the zEmployeeProjects.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/z-employee-projects/count")
    public ResponseEntity<Long> countZEmployeeProjects(ZEmployeeProjectsCriteria criteria) {
        log.debug("REST request to count ZEmployeeProjects by criteria: {}", criteria);
        return ResponseEntity.ok().body(zEmployeeProjectsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /z-employee-projects/:id} : get the "id" zEmployeeProjects.
     *
     * @param id the id of the zEmployeeProjects to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the zEmployeeProjects, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/z-employee-projects/{id}")
    public ResponseEntity<ZEmployeeProjects> getZEmployeeProjects(@PathVariable Long id) {
        log.debug("REST request to get ZEmployeeProjects : {}", id);
        Optional<ZEmployeeProjects> zEmployeeProjects = zEmployeeProjectsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(zEmployeeProjects);
    }

    /**
     * {@code DELETE  /z-employee-projects/:id} : delete the "id" zEmployeeProjects.
     *
     * @param id the id of the zEmployeeProjects to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/z-employee-projects/{id}")
    public ResponseEntity<Void> deleteZEmployeeProjects(@PathVariable Long id) {
        log.debug("REST request to delete ZEmployeeProjects : {}", id);
        zEmployeeProjectsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
