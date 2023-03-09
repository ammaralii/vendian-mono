package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.Configurations;
import com.venturedive.vendian_mono.repository.ConfigurationsRepository;
import com.venturedive.vendian_mono.service.ConfigurationsQueryService;
import com.venturedive.vendian_mono.service.ConfigurationsService;
import com.venturedive.vendian_mono.service.criteria.ConfigurationsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.Configurations}.
 */
@RestController
@RequestMapping("/api")
public class ConfigurationsResource {

    private final Logger log = LoggerFactory.getLogger(ConfigurationsResource.class);

    private static final String ENTITY_NAME = "configurations";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConfigurationsService configurationsService;

    private final ConfigurationsRepository configurationsRepository;

    private final ConfigurationsQueryService configurationsQueryService;

    public ConfigurationsResource(
        ConfigurationsService configurationsService,
        ConfigurationsRepository configurationsRepository,
        ConfigurationsQueryService configurationsQueryService
    ) {
        this.configurationsService = configurationsService;
        this.configurationsRepository = configurationsRepository;
        this.configurationsQueryService = configurationsQueryService;
    }

    /**
     * {@code POST  /configurations} : Create a new configurations.
     *
     * @param configurations the configurations to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new configurations, or with status {@code 400 (Bad Request)} if the configurations has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/configurations")
    public ResponseEntity<Configurations> createConfigurations(@Valid @RequestBody Configurations configurations)
        throws URISyntaxException {
        log.debug("REST request to save Configurations : {}", configurations);
        if (configurations.getId() != null) {
            throw new BadRequestAlertException("A new configurations cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Configurations result = configurationsService.save(configurations);
        return ResponseEntity
            .created(new URI("/api/configurations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /configurations/:id} : Updates an existing configurations.
     *
     * @param id the id of the configurations to save.
     * @param configurations the configurations to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated configurations,
     * or with status {@code 400 (Bad Request)} if the configurations is not valid,
     * or with status {@code 500 (Internal Server Error)} if the configurations couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/configurations/{id}")
    public ResponseEntity<Configurations> updateConfigurations(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Configurations configurations
    ) throws URISyntaxException {
        log.debug("REST request to update Configurations : {}, {}", id, configurations);
        if (configurations.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, configurations.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!configurationsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Configurations result = configurationsService.update(configurations);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, configurations.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /configurations/:id} : Partial updates given fields of an existing configurations, field will ignore if it is null
     *
     * @param id the id of the configurations to save.
     * @param configurations the configurations to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated configurations,
     * or with status {@code 400 (Bad Request)} if the configurations is not valid,
     * or with status {@code 404 (Not Found)} if the configurations is not found,
     * or with status {@code 500 (Internal Server Error)} if the configurations couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/configurations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Configurations> partialUpdateConfigurations(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Configurations configurations
    ) throws URISyntaxException {
        log.debug("REST request to partial update Configurations partially : {}, {}", id, configurations);
        if (configurations.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, configurations.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!configurationsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Configurations> result = configurationsService.partialUpdate(configurations);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, configurations.getId().toString())
        );
    }

    /**
     * {@code GET  /configurations} : get all the configurations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of configurations in body.
     */
    @GetMapping("/configurations")
    public ResponseEntity<List<Configurations>> getAllConfigurations(
        ConfigurationsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Configurations by criteria: {}", criteria);
        Page<Configurations> page = configurationsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /configurations/count} : count all the configurations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/configurations/count")
    public ResponseEntity<Long> countConfigurations(ConfigurationsCriteria criteria) {
        log.debug("REST request to count Configurations by criteria: {}", criteria);
        return ResponseEntity.ok().body(configurationsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /configurations/:id} : get the "id" configurations.
     *
     * @param id the id of the configurations to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the configurations, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/configurations/{id}")
    public ResponseEntity<Configurations> getConfigurations(@PathVariable Long id) {
        log.debug("REST request to get Configurations : {}", id);
        Optional<Configurations> configurations = configurationsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(configurations);
    }

    /**
     * {@code DELETE  /configurations/:id} : delete the "id" configurations.
     *
     * @param id the id of the configurations to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/configurations/{id}")
    public ResponseEntity<Void> deleteConfigurations(@PathVariable Long id) {
        log.debug("REST request to delete Configurations : {}", id);
        configurationsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
