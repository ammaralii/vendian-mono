package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.HrPerformanceCycles;
import com.venturedive.vendian_mono.repository.HrPerformanceCyclesRepository;
import com.venturedive.vendian_mono.service.HrPerformanceCyclesQueryService;
import com.venturedive.vendian_mono.service.HrPerformanceCyclesService;
import com.venturedive.vendian_mono.service.criteria.HrPerformanceCyclesCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.HrPerformanceCycles}.
 */
@RestController
@RequestMapping("/api")
public class HrPerformanceCyclesResource {

    private final Logger log = LoggerFactory.getLogger(HrPerformanceCyclesResource.class);

    private static final String ENTITY_NAME = "hrPerformanceCycles";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HrPerformanceCyclesService hrPerformanceCyclesService;

    private final HrPerformanceCyclesRepository hrPerformanceCyclesRepository;

    private final HrPerformanceCyclesQueryService hrPerformanceCyclesQueryService;

    public HrPerformanceCyclesResource(
        HrPerformanceCyclesService hrPerformanceCyclesService,
        HrPerformanceCyclesRepository hrPerformanceCyclesRepository,
        HrPerformanceCyclesQueryService hrPerformanceCyclesQueryService
    ) {
        this.hrPerformanceCyclesService = hrPerformanceCyclesService;
        this.hrPerformanceCyclesRepository = hrPerformanceCyclesRepository;
        this.hrPerformanceCyclesQueryService = hrPerformanceCyclesQueryService;
    }

    /**
     * {@code POST  /hr-performance-cycles} : Create a new hrPerformanceCycles.
     *
     * @param hrPerformanceCycles the hrPerformanceCycles to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hrPerformanceCycles, or with status {@code 400 (Bad Request)} if the hrPerformanceCycles has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hr-performance-cycles")
    public ResponseEntity<HrPerformanceCycles> createHrPerformanceCycles(@Valid @RequestBody HrPerformanceCycles hrPerformanceCycles)
        throws URISyntaxException {
        log.debug("REST request to save HrPerformanceCycles : {}", hrPerformanceCycles);
        if (hrPerformanceCycles.getId() != null) {
            throw new BadRequestAlertException("A new hrPerformanceCycles cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HrPerformanceCycles result = hrPerformanceCyclesService.save(hrPerformanceCycles);
        return ResponseEntity
            .created(new URI("/api/hr-performance-cycles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hr-performance-cycles/:id} : Updates an existing hrPerformanceCycles.
     *
     * @param id the id of the hrPerformanceCycles to save.
     * @param hrPerformanceCycles the hrPerformanceCycles to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hrPerformanceCycles,
     * or with status {@code 400 (Bad Request)} if the hrPerformanceCycles is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hrPerformanceCycles couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hr-performance-cycles/{id}")
    public ResponseEntity<HrPerformanceCycles> updateHrPerformanceCycles(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody HrPerformanceCycles hrPerformanceCycles
    ) throws URISyntaxException {
        log.debug("REST request to update HrPerformanceCycles : {}, {}", id, hrPerformanceCycles);
        if (hrPerformanceCycles.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hrPerformanceCycles.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hrPerformanceCyclesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HrPerformanceCycles result = hrPerformanceCyclesService.update(hrPerformanceCycles);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, hrPerformanceCycles.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /hr-performance-cycles/:id} : Partial updates given fields of an existing hrPerformanceCycles, field will ignore if it is null
     *
     * @param id the id of the hrPerformanceCycles to save.
     * @param hrPerformanceCycles the hrPerformanceCycles to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hrPerformanceCycles,
     * or with status {@code 400 (Bad Request)} if the hrPerformanceCycles is not valid,
     * or with status {@code 404 (Not Found)} if the hrPerformanceCycles is not found,
     * or with status {@code 500 (Internal Server Error)} if the hrPerformanceCycles couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/hr-performance-cycles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HrPerformanceCycles> partialUpdateHrPerformanceCycles(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody HrPerformanceCycles hrPerformanceCycles
    ) throws URISyntaxException {
        log.debug("REST request to partial update HrPerformanceCycles partially : {}, {}", id, hrPerformanceCycles);
        if (hrPerformanceCycles.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hrPerformanceCycles.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hrPerformanceCyclesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HrPerformanceCycles> result = hrPerformanceCyclesService.partialUpdate(hrPerformanceCycles);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, hrPerformanceCycles.getId().toString())
        );
    }

    /**
     * {@code GET  /hr-performance-cycles} : get all the hrPerformanceCycles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hrPerformanceCycles in body.
     */
    @GetMapping("/hr-performance-cycles")
    public ResponseEntity<List<HrPerformanceCycles>> getAllHrPerformanceCycles(
        HrPerformanceCyclesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get HrPerformanceCycles by criteria: {}", criteria);
        Page<HrPerformanceCycles> page = hrPerformanceCyclesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /hr-performance-cycles/count} : count all the hrPerformanceCycles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/hr-performance-cycles/count")
    public ResponseEntity<Long> countHrPerformanceCycles(HrPerformanceCyclesCriteria criteria) {
        log.debug("REST request to count HrPerformanceCycles by criteria: {}", criteria);
        return ResponseEntity.ok().body(hrPerformanceCyclesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /hr-performance-cycles/:id} : get the "id" hrPerformanceCycles.
     *
     * @param id the id of the hrPerformanceCycles to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hrPerformanceCycles, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hr-performance-cycles/{id}")
    public ResponseEntity<HrPerformanceCycles> getHrPerformanceCycles(@PathVariable Long id) {
        log.debug("REST request to get HrPerformanceCycles : {}", id);
        Optional<HrPerformanceCycles> hrPerformanceCycles = hrPerformanceCyclesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hrPerformanceCycles);
    }

    /**
     * {@code DELETE  /hr-performance-cycles/:id} : delete the "id" hrPerformanceCycles.
     *
     * @param id the id of the hrPerformanceCycles to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hr-performance-cycles/{id}")
    public ResponseEntity<Void> deleteHrPerformanceCycles(@PathVariable Long id) {
        log.debug("REST request to delete HrPerformanceCycles : {}", id);
        hrPerformanceCyclesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
