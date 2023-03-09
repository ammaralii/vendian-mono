package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.Sequelizemeta;
import com.venturedive.vendian_mono.repository.SequelizemetaRepository;
import com.venturedive.vendian_mono.service.SequelizemetaQueryService;
import com.venturedive.vendian_mono.service.SequelizemetaService;
import com.venturedive.vendian_mono.service.criteria.SequelizemetaCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.Sequelizemeta}.
 */
@RestController
@RequestMapping("/api")
public class SequelizemetaResource {

    private final Logger log = LoggerFactory.getLogger(SequelizemetaResource.class);

    private static final String ENTITY_NAME = "sequelizemeta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SequelizemetaService sequelizemetaService;

    private final SequelizemetaRepository sequelizemetaRepository;

    private final SequelizemetaQueryService sequelizemetaQueryService;

    public SequelizemetaResource(
        SequelizemetaService sequelizemetaService,
        SequelizemetaRepository sequelizemetaRepository,
        SequelizemetaQueryService sequelizemetaQueryService
    ) {
        this.sequelizemetaService = sequelizemetaService;
        this.sequelizemetaRepository = sequelizemetaRepository;
        this.sequelizemetaQueryService = sequelizemetaQueryService;
    }

    /**
     * {@code POST  /sequelizemetas} : Create a new sequelizemeta.
     *
     * @param sequelizemeta the sequelizemeta to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sequelizemeta, or with status {@code 400 (Bad Request)} if the sequelizemeta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sequelizemetas")
    public ResponseEntity<Sequelizemeta> createSequelizemeta(@Valid @RequestBody Sequelizemeta sequelizemeta) throws URISyntaxException {
        log.debug("REST request to save Sequelizemeta : {}", sequelizemeta);
        if (sequelizemeta.getId() != null) {
            throw new BadRequestAlertException("A new sequelizemeta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sequelizemeta result = sequelizemetaService.save(sequelizemeta);
        return ResponseEntity
            .created(new URI("/api/sequelizemetas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sequelizemetas/:id} : Updates an existing sequelizemeta.
     *
     * @param id the id of the sequelizemeta to save.
     * @param sequelizemeta the sequelizemeta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sequelizemeta,
     * or with status {@code 400 (Bad Request)} if the sequelizemeta is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sequelizemeta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sequelizemetas/{id}")
    public ResponseEntity<Sequelizemeta> updateSequelizemeta(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Sequelizemeta sequelizemeta
    ) throws URISyntaxException {
        log.debug("REST request to update Sequelizemeta : {}, {}", id, sequelizemeta);
        if (sequelizemeta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sequelizemeta.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sequelizemetaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Sequelizemeta result = sequelizemetaService.update(sequelizemeta);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sequelizemeta.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sequelizemetas/:id} : Partial updates given fields of an existing sequelizemeta, field will ignore if it is null
     *
     * @param id the id of the sequelizemeta to save.
     * @param sequelizemeta the sequelizemeta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sequelizemeta,
     * or with status {@code 400 (Bad Request)} if the sequelizemeta is not valid,
     * or with status {@code 404 (Not Found)} if the sequelizemeta is not found,
     * or with status {@code 500 (Internal Server Error)} if the sequelizemeta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sequelizemetas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Sequelizemeta> partialUpdateSequelizemeta(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Sequelizemeta sequelizemeta
    ) throws URISyntaxException {
        log.debug("REST request to partial update Sequelizemeta partially : {}, {}", id, sequelizemeta);
        if (sequelizemeta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sequelizemeta.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sequelizemetaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Sequelizemeta> result = sequelizemetaService.partialUpdate(sequelizemeta);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sequelizemeta.getId().toString())
        );
    }

    /**
     * {@code GET  /sequelizemetas} : get all the sequelizemetas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sequelizemetas in body.
     */
    @GetMapping("/sequelizemetas")
    public ResponseEntity<List<Sequelizemeta>> getAllSequelizemetas(
        SequelizemetaCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Sequelizemetas by criteria: {}", criteria);
        Page<Sequelizemeta> page = sequelizemetaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sequelizemetas/count} : count all the sequelizemetas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/sequelizemetas/count")
    public ResponseEntity<Long> countSequelizemetas(SequelizemetaCriteria criteria) {
        log.debug("REST request to count Sequelizemetas by criteria: {}", criteria);
        return ResponseEntity.ok().body(sequelizemetaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sequelizemetas/:id} : get the "id" sequelizemeta.
     *
     * @param id the id of the sequelizemeta to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sequelizemeta, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sequelizemetas/{id}")
    public ResponseEntity<Sequelizemeta> getSequelizemeta(@PathVariable Long id) {
        log.debug("REST request to get Sequelizemeta : {}", id);
        Optional<Sequelizemeta> sequelizemeta = sequelizemetaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sequelizemeta);
    }

    /**
     * {@code DELETE  /sequelizemetas/:id} : delete the "id" sequelizemeta.
     *
     * @param id the id of the sequelizemeta to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sequelizemetas/{id}")
    public ResponseEntity<Void> deleteSequelizemeta(@PathVariable Long id) {
        log.debug("REST request to delete Sequelizemeta : {}", id);
        sequelizemetaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
