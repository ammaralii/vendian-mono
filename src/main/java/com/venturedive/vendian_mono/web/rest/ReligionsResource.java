package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.Religions;
import com.venturedive.vendian_mono.repository.ReligionsRepository;
import com.venturedive.vendian_mono.service.ReligionsQueryService;
import com.venturedive.vendian_mono.service.ReligionsService;
import com.venturedive.vendian_mono.service.criteria.ReligionsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.Religions}.
 */
@RestController
@RequestMapping("/api")
public class ReligionsResource {

    private final Logger log = LoggerFactory.getLogger(ReligionsResource.class);

    private static final String ENTITY_NAME = "religions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReligionsService religionsService;

    private final ReligionsRepository religionsRepository;

    private final ReligionsQueryService religionsQueryService;

    public ReligionsResource(
        ReligionsService religionsService,
        ReligionsRepository religionsRepository,
        ReligionsQueryService religionsQueryService
    ) {
        this.religionsService = religionsService;
        this.religionsRepository = religionsRepository;
        this.religionsQueryService = religionsQueryService;
    }

    /**
     * {@code POST  /religions} : Create a new religions.
     *
     * @param religions the religions to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new religions, or with status {@code 400 (Bad Request)} if the religions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/religions")
    public ResponseEntity<Religions> createReligions(@Valid @RequestBody Religions religions) throws URISyntaxException {
        log.debug("REST request to save Religions : {}", religions);
        if (religions.getId() != null) {
            throw new BadRequestAlertException("A new religions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Religions result = religionsService.save(religions);
        return ResponseEntity
            .created(new URI("/api/religions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /religions/:id} : Updates an existing religions.
     *
     * @param id the id of the religions to save.
     * @param religions the religions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated religions,
     * or with status {@code 400 (Bad Request)} if the religions is not valid,
     * or with status {@code 500 (Internal Server Error)} if the religions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/religions/{id}")
    public ResponseEntity<Religions> updateReligions(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Religions religions
    ) throws URISyntaxException {
        log.debug("REST request to update Religions : {}, {}", id, religions);
        if (religions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, religions.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!religionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Religions result = religionsService.update(religions);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, religions.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /religions/:id} : Partial updates given fields of an existing religions, field will ignore if it is null
     *
     * @param id the id of the religions to save.
     * @param religions the religions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated religions,
     * or with status {@code 400 (Bad Request)} if the religions is not valid,
     * or with status {@code 404 (Not Found)} if the religions is not found,
     * or with status {@code 500 (Internal Server Error)} if the religions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/religions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Religions> partialUpdateReligions(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Religions religions
    ) throws URISyntaxException {
        log.debug("REST request to partial update Religions partially : {}, {}", id, religions);
        if (religions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, religions.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!religionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Religions> result = religionsService.partialUpdate(religions);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, religions.getId().toString())
        );
    }

    /**
     * {@code GET  /religions} : get all the religions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of religions in body.
     */
    @GetMapping("/religions")
    public ResponseEntity<List<Religions>> getAllReligions(
        ReligionsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Religions by criteria: {}", criteria);
        Page<Religions> page = religionsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /religions/count} : count all the religions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/religions/count")
    public ResponseEntity<Long> countReligions(ReligionsCriteria criteria) {
        log.debug("REST request to count Religions by criteria: {}", criteria);
        return ResponseEntity.ok().body(religionsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /religions/:id} : get the "id" religions.
     *
     * @param id the id of the religions to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the religions, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/religions/{id}")
    public ResponseEntity<Religions> getReligions(@PathVariable Long id) {
        log.debug("REST request to get Religions : {}", id);
        Optional<Religions> religions = religionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(religions);
    }

    /**
     * {@code DELETE  /religions/:id} : delete the "id" religions.
     *
     * @param id the id of the religions to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/religions/{id}")
    public ResponseEntity<Void> deleteReligions(@PathVariable Long id) {
        log.debug("REST request to delete Religions : {}", id);
        religionsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
