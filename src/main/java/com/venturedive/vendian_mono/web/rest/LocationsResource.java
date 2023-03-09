package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.Locations;
import com.venturedive.vendian_mono.repository.LocationsRepository;
import com.venturedive.vendian_mono.service.LocationsQueryService;
import com.venturedive.vendian_mono.service.LocationsService;
import com.venturedive.vendian_mono.service.criteria.LocationsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.Locations}.
 */
@RestController
@RequestMapping("/api")
public class LocationsResource {

    private final Logger log = LoggerFactory.getLogger(LocationsResource.class);

    private static final String ENTITY_NAME = "locations";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocationsService locationsService;

    private final LocationsRepository locationsRepository;

    private final LocationsQueryService locationsQueryService;

    public LocationsResource(
        LocationsService locationsService,
        LocationsRepository locationsRepository,
        LocationsQueryService locationsQueryService
    ) {
        this.locationsService = locationsService;
        this.locationsRepository = locationsRepository;
        this.locationsQueryService = locationsQueryService;
    }

    /**
     * {@code POST  /locations} : Create a new locations.
     *
     * @param locations the locations to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new locations, or with status {@code 400 (Bad Request)} if the locations has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/locations")
    public ResponseEntity<Locations> createLocations(@Valid @RequestBody Locations locations) throws URISyntaxException {
        log.debug("REST request to save Locations : {}", locations);
        if (locations.getId() != null) {
            throw new BadRequestAlertException("A new locations cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Locations result = locationsService.save(locations);
        return ResponseEntity
            .created(new URI("/api/locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /locations/:id} : Updates an existing locations.
     *
     * @param id the id of the locations to save.
     * @param locations the locations to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locations,
     * or with status {@code 400 (Bad Request)} if the locations is not valid,
     * or with status {@code 500 (Internal Server Error)} if the locations couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/locations/{id}")
    public ResponseEntity<Locations> updateLocations(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Locations locations
    ) throws URISyntaxException {
        log.debug("REST request to update Locations : {}, {}", id, locations);
        if (locations.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, locations.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!locationsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Locations result = locationsService.update(locations);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, locations.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /locations/:id} : Partial updates given fields of an existing locations, field will ignore if it is null
     *
     * @param id the id of the locations to save.
     * @param locations the locations to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locations,
     * or with status {@code 400 (Bad Request)} if the locations is not valid,
     * or with status {@code 404 (Not Found)} if the locations is not found,
     * or with status {@code 500 (Internal Server Error)} if the locations couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/locations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Locations> partialUpdateLocations(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Locations locations
    ) throws URISyntaxException {
        log.debug("REST request to partial update Locations partially : {}, {}", id, locations);
        if (locations.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, locations.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!locationsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Locations> result = locationsService.partialUpdate(locations);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, locations.getId().toString())
        );
    }

    /**
     * {@code GET  /locations} : get all the locations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of locations in body.
     */
    @GetMapping("/locations")
    public ResponseEntity<List<Locations>> getAllLocations(
        LocationsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Locations by criteria: {}", criteria);
        Page<Locations> page = locationsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /locations/count} : count all the locations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/locations/count")
    public ResponseEntity<Long> countLocations(LocationsCriteria criteria) {
        log.debug("REST request to count Locations by criteria: {}", criteria);
        return ResponseEntity.ok().body(locationsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /locations/:id} : get the "id" locations.
     *
     * @param id the id of the locations to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the locations, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/locations/{id}")
    public ResponseEntity<Locations> getLocations(@PathVariable Long id) {
        log.debug("REST request to get Locations : {}", id);
        Optional<Locations> locations = locationsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(locations);
    }

    /**
     * {@code DELETE  /locations/:id} : delete the "id" locations.
     *
     * @param id the id of the locations to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/locations/{id}")
    public ResponseEntity<Void> deleteLocations(@PathVariable Long id) {
        log.debug("REST request to delete Locations : {}", id);
        locationsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
