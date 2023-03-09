package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.ClaimImages;
import com.venturedive.vendian_mono.repository.ClaimImagesRepository;
import com.venturedive.vendian_mono.service.ClaimImagesQueryService;
import com.venturedive.vendian_mono.service.ClaimImagesService;
import com.venturedive.vendian_mono.service.criteria.ClaimImagesCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.ClaimImages}.
 */
@RestController
@RequestMapping("/api")
public class ClaimImagesResource {

    private final Logger log = LoggerFactory.getLogger(ClaimImagesResource.class);

    private static final String ENTITY_NAME = "claimImages";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClaimImagesService claimImagesService;

    private final ClaimImagesRepository claimImagesRepository;

    private final ClaimImagesQueryService claimImagesQueryService;

    public ClaimImagesResource(
        ClaimImagesService claimImagesService,
        ClaimImagesRepository claimImagesRepository,
        ClaimImagesQueryService claimImagesQueryService
    ) {
        this.claimImagesService = claimImagesService;
        this.claimImagesRepository = claimImagesRepository;
        this.claimImagesQueryService = claimImagesQueryService;
    }

    /**
     * {@code POST  /claim-images} : Create a new claimImages.
     *
     * @param claimImages the claimImages to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new claimImages, or with status {@code 400 (Bad Request)} if the claimImages has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/claim-images")
    public ResponseEntity<ClaimImages> createClaimImages(@Valid @RequestBody ClaimImages claimImages) throws URISyntaxException {
        log.debug("REST request to save ClaimImages : {}", claimImages);
        if (claimImages.getId() != null) {
            throw new BadRequestAlertException("A new claimImages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClaimImages result = claimImagesService.save(claimImages);
        return ResponseEntity
            .created(new URI("/api/claim-images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /claim-images/:id} : Updates an existing claimImages.
     *
     * @param id the id of the claimImages to save.
     * @param claimImages the claimImages to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated claimImages,
     * or with status {@code 400 (Bad Request)} if the claimImages is not valid,
     * or with status {@code 500 (Internal Server Error)} if the claimImages couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/claim-images/{id}")
    public ResponseEntity<ClaimImages> updateClaimImages(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ClaimImages claimImages
    ) throws URISyntaxException {
        log.debug("REST request to update ClaimImages : {}, {}", id, claimImages);
        if (claimImages.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, claimImages.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!claimImagesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClaimImages result = claimImagesService.update(claimImages);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, claimImages.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /claim-images/:id} : Partial updates given fields of an existing claimImages, field will ignore if it is null
     *
     * @param id the id of the claimImages to save.
     * @param claimImages the claimImages to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated claimImages,
     * or with status {@code 400 (Bad Request)} if the claimImages is not valid,
     * or with status {@code 404 (Not Found)} if the claimImages is not found,
     * or with status {@code 500 (Internal Server Error)} if the claimImages couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/claim-images/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ClaimImages> partialUpdateClaimImages(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ClaimImages claimImages
    ) throws URISyntaxException {
        log.debug("REST request to partial update ClaimImages partially : {}, {}", id, claimImages);
        if (claimImages.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, claimImages.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!claimImagesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClaimImages> result = claimImagesService.partialUpdate(claimImages);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, claimImages.getId().toString())
        );
    }

    /**
     * {@code GET  /claim-images} : get all the claimImages.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of claimImages in body.
     */
    @GetMapping("/claim-images")
    public ResponseEntity<List<ClaimImages>> getAllClaimImages(
        ClaimImagesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ClaimImages by criteria: {}", criteria);
        Page<ClaimImages> page = claimImagesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /claim-images/count} : count all the claimImages.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/claim-images/count")
    public ResponseEntity<Long> countClaimImages(ClaimImagesCriteria criteria) {
        log.debug("REST request to count ClaimImages by criteria: {}", criteria);
        return ResponseEntity.ok().body(claimImagesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /claim-images/:id} : get the "id" claimImages.
     *
     * @param id the id of the claimImages to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the claimImages, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/claim-images/{id}")
    public ResponseEntity<ClaimImages> getClaimImages(@PathVariable Long id) {
        log.debug("REST request to get ClaimImages : {}", id);
        Optional<ClaimImages> claimImages = claimImagesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(claimImages);
    }

    /**
     * {@code DELETE  /claim-images/:id} : delete the "id" claimImages.
     *
     * @param id the id of the claimImages to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/claim-images/{id}")
    public ResponseEntity<Void> deleteClaimImages(@PathVariable Long id) {
        log.debug("REST request to delete ClaimImages : {}", id);
        claimImagesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
