package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.Sequelizedata;
import com.venturedive.vendian_mono.repository.SequelizedataRepository;
import com.venturedive.vendian_mono.service.SequelizedataQueryService;
import com.venturedive.vendian_mono.service.SequelizedataService;
import com.venturedive.vendian_mono.service.criteria.SequelizedataCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.Sequelizedata}.
 */
@RestController
@RequestMapping("/api")
public class SequelizedataResource {

    private final Logger log = LoggerFactory.getLogger(SequelizedataResource.class);

    private static final String ENTITY_NAME = "sequelizedata";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SequelizedataService sequelizedataService;

    private final SequelizedataRepository sequelizedataRepository;

    private final SequelizedataQueryService sequelizedataQueryService;

    public SequelizedataResource(
        SequelizedataService sequelizedataService,
        SequelizedataRepository sequelizedataRepository,
        SequelizedataQueryService sequelizedataQueryService
    ) {
        this.sequelizedataService = sequelizedataService;
        this.sequelizedataRepository = sequelizedataRepository;
        this.sequelizedataQueryService = sequelizedataQueryService;
    }

    /**
     * {@code POST  /sequelizedata} : Create a new sequelizedata.
     *
     * @param sequelizedata the sequelizedata to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sequelizedata, or with status {@code 400 (Bad Request)} if the sequelizedata has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sequelizedata")
    public ResponseEntity<Sequelizedata> createSequelizedata(@Valid @RequestBody Sequelizedata sequelizedata) throws URISyntaxException {
        log.debug("REST request to save Sequelizedata : {}", sequelizedata);
        if (sequelizedata.getId() != null) {
            throw new BadRequestAlertException("A new sequelizedata cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sequelizedata result = sequelizedataService.save(sequelizedata);
        return ResponseEntity
            .created(new URI("/api/sequelizedata/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sequelizedata/:id} : Updates an existing sequelizedata.
     *
     * @param id the id of the sequelizedata to save.
     * @param sequelizedata the sequelizedata to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sequelizedata,
     * or with status {@code 400 (Bad Request)} if the sequelizedata is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sequelizedata couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sequelizedata/{id}")
    public ResponseEntity<Sequelizedata> updateSequelizedata(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Sequelizedata sequelizedata
    ) throws URISyntaxException {
        log.debug("REST request to update Sequelizedata : {}, {}", id, sequelizedata);
        if (sequelizedata.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sequelizedata.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sequelizedataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Sequelizedata result = sequelizedataService.update(sequelizedata);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sequelizedata.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sequelizedata/:id} : Partial updates given fields of an existing sequelizedata, field will ignore if it is null
     *
     * @param id the id of the sequelizedata to save.
     * @param sequelizedata the sequelizedata to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sequelizedata,
     * or with status {@code 400 (Bad Request)} if the sequelizedata is not valid,
     * or with status {@code 404 (Not Found)} if the sequelizedata is not found,
     * or with status {@code 500 (Internal Server Error)} if the sequelizedata couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sequelizedata/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Sequelizedata> partialUpdateSequelizedata(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Sequelizedata sequelizedata
    ) throws URISyntaxException {
        log.debug("REST request to partial update Sequelizedata partially : {}, {}", id, sequelizedata);
        if (sequelizedata.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sequelizedata.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sequelizedataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Sequelizedata> result = sequelizedataService.partialUpdate(sequelizedata);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sequelizedata.getId().toString())
        );
    }

    /**
     * {@code GET  /sequelizedata} : get all the sequelizedata.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sequelizedata in body.
     */
    @GetMapping("/sequelizedata")
    public ResponseEntity<List<Sequelizedata>> getAllSequelizedata(
        SequelizedataCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Sequelizedata by criteria: {}", criteria);
        Page<Sequelizedata> page = sequelizedataQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sequelizedata/count} : count all the sequelizedata.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/sequelizedata/count")
    public ResponseEntity<Long> countSequelizedata(SequelizedataCriteria criteria) {
        log.debug("REST request to count Sequelizedata by criteria: {}", criteria);
        return ResponseEntity.ok().body(sequelizedataQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sequelizedata/:id} : get the "id" sequelizedata.
     *
     * @param id the id of the sequelizedata to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sequelizedata, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sequelizedata/{id}")
    public ResponseEntity<Sequelizedata> getSequelizedata(@PathVariable Long id) {
        log.debug("REST request to get Sequelizedata : {}", id);
        Optional<Sequelizedata> sequelizedata = sequelizedataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sequelizedata);
    }

    /**
     * {@code DELETE  /sequelizedata/:id} : delete the "id" sequelizedata.
     *
     * @param id the id of the sequelizedata to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sequelizedata/{id}")
    public ResponseEntity<Void> deleteSequelizedata(@PathVariable Long id) {
        log.debug("REST request to delete Sequelizedata : {}", id);
        sequelizedataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
