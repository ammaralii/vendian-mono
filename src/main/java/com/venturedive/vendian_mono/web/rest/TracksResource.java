package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.Tracks;
import com.venturedive.vendian_mono.repository.TracksRepository;
import com.venturedive.vendian_mono.service.TracksQueryService;
import com.venturedive.vendian_mono.service.TracksService;
import com.venturedive.vendian_mono.service.criteria.TracksCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.Tracks}.
 */
@RestController
@RequestMapping("/api")
public class TracksResource {

    private final Logger log = LoggerFactory.getLogger(TracksResource.class);

    private static final String ENTITY_NAME = "tracks";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TracksService tracksService;

    private final TracksRepository tracksRepository;

    private final TracksQueryService tracksQueryService;

    public TracksResource(TracksService tracksService, TracksRepository tracksRepository, TracksQueryService tracksQueryService) {
        this.tracksService = tracksService;
        this.tracksRepository = tracksRepository;
        this.tracksQueryService = tracksQueryService;
    }

    /**
     * {@code POST  /tracks} : Create a new tracks.
     *
     * @param tracks the tracks to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tracks, or with status {@code 400 (Bad Request)} if the tracks has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tracks")
    public ResponseEntity<Tracks> createTracks(@Valid @RequestBody Tracks tracks) throws URISyntaxException {
        log.debug("REST request to save Tracks : {}", tracks);
        if (tracks.getId() != null) {
            throw new BadRequestAlertException("A new tracks cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Tracks result = tracksService.save(tracks);
        return ResponseEntity
            .created(new URI("/api/tracks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tracks/:id} : Updates an existing tracks.
     *
     * @param id the id of the tracks to save.
     * @param tracks the tracks to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tracks,
     * or with status {@code 400 (Bad Request)} if the tracks is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tracks couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tracks/{id}")
    public ResponseEntity<Tracks> updateTracks(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Tracks tracks
    ) throws URISyntaxException {
        log.debug("REST request to update Tracks : {}, {}", id, tracks);
        if (tracks.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tracks.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tracksRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Tracks result = tracksService.update(tracks);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tracks.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tracks/:id} : Partial updates given fields of an existing tracks, field will ignore if it is null
     *
     * @param id the id of the tracks to save.
     * @param tracks the tracks to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tracks,
     * or with status {@code 400 (Bad Request)} if the tracks is not valid,
     * or with status {@code 404 (Not Found)} if the tracks is not found,
     * or with status {@code 500 (Internal Server Error)} if the tracks couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tracks/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Tracks> partialUpdateTracks(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Tracks tracks
    ) throws URISyntaxException {
        log.debug("REST request to partial update Tracks partially : {}, {}", id, tracks);
        if (tracks.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tracks.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tracksRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Tracks> result = tracksService.partialUpdate(tracks);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tracks.getId().toString())
        );
    }

    /**
     * {@code GET  /tracks} : get all the tracks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tracks in body.
     */
    @GetMapping("/tracks")
    public ResponseEntity<List<Tracks>> getAllTracks(
        TracksCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Tracks by criteria: {}", criteria);
        Page<Tracks> page = tracksQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tracks/count} : count all the tracks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/tracks/count")
    public ResponseEntity<Long> countTracks(TracksCriteria criteria) {
        log.debug("REST request to count Tracks by criteria: {}", criteria);
        return ResponseEntity.ok().body(tracksQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tracks/:id} : get the "id" tracks.
     *
     * @param id the id of the tracks to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tracks, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tracks/{id}")
    public ResponseEntity<Tracks> getTracks(@PathVariable Long id) {
        log.debug("REST request to get Tracks : {}", id);
        Optional<Tracks> tracks = tracksService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tracks);
    }

    /**
     * {@code DELETE  /tracks/:id} : delete the "id" tracks.
     *
     * @param id the id of the tracks to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tracks/{id}")
    public ResponseEntity<Void> deleteTracks(@PathVariable Long id) {
        log.debug("REST request to delete Tracks : {}", id);
        tracksService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
