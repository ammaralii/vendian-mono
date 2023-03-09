package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.Skills;
import com.venturedive.vendian_mono.repository.SkillsRepository;
import com.venturedive.vendian_mono.service.SkillsQueryService;
import com.venturedive.vendian_mono.service.SkillsService;
import com.venturedive.vendian_mono.service.criteria.SkillsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.Skills}.
 */
@RestController
@RequestMapping("/api")
public class SkillsResource {

    private final Logger log = LoggerFactory.getLogger(SkillsResource.class);

    private static final String ENTITY_NAME = "skills";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SkillsService skillsService;

    private final SkillsRepository skillsRepository;

    private final SkillsQueryService skillsQueryService;

    public SkillsResource(SkillsService skillsService, SkillsRepository skillsRepository, SkillsQueryService skillsQueryService) {
        this.skillsService = skillsService;
        this.skillsRepository = skillsRepository;
        this.skillsQueryService = skillsQueryService;
    }

    /**
     * {@code POST  /skills} : Create a new skills.
     *
     * @param skills the skills to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new skills, or with status {@code 400 (Bad Request)} if the skills has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/skills")
    public ResponseEntity<Skills> createSkills(@Valid @RequestBody Skills skills) throws URISyntaxException {
        log.debug("REST request to save Skills : {}", skills);
        if (skills.getId() != null) {
            throw new BadRequestAlertException("A new skills cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Skills result = skillsService.save(skills);
        return ResponseEntity
            .created(new URI("/api/skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /skills/:id} : Updates an existing skills.
     *
     * @param id the id of the skills to save.
     * @param skills the skills to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated skills,
     * or with status {@code 400 (Bad Request)} if the skills is not valid,
     * or with status {@code 500 (Internal Server Error)} if the skills couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/skills/{id}")
    public ResponseEntity<Skills> updateSkills(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Skills skills
    ) throws URISyntaxException {
        log.debug("REST request to update Skills : {}, {}", id, skills);
        if (skills.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, skills.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!skillsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Skills result = skillsService.update(skills);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, skills.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /skills/:id} : Partial updates given fields of an existing skills, field will ignore if it is null
     *
     * @param id the id of the skills to save.
     * @param skills the skills to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated skills,
     * or with status {@code 400 (Bad Request)} if the skills is not valid,
     * or with status {@code 404 (Not Found)} if the skills is not found,
     * or with status {@code 500 (Internal Server Error)} if the skills couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/skills/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Skills> partialUpdateSkills(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Skills skills
    ) throws URISyntaxException {
        log.debug("REST request to partial update Skills partially : {}, {}", id, skills);
        if (skills.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, skills.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!skillsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Skills> result = skillsService.partialUpdate(skills);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, skills.getId().toString())
        );
    }

    /**
     * {@code GET  /skills} : get all the skills.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of skills in body.
     */
    @GetMapping("/skills")
    public ResponseEntity<List<Skills>> getAllSkills(
        SkillsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Skills by criteria: {}", criteria);
        Page<Skills> page = skillsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /skills/count} : count all the skills.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/skills/count")
    public ResponseEntity<Long> countSkills(SkillsCriteria criteria) {
        log.debug("REST request to count Skills by criteria: {}", criteria);
        return ResponseEntity.ok().body(skillsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /skills/:id} : get the "id" skills.
     *
     * @param id the id of the skills to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the skills, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/skills/{id}")
    public ResponseEntity<Skills> getSkills(@PathVariable Long id) {
        log.debug("REST request to get Skills : {}", id);
        Optional<Skills> skills = skillsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(skills);
    }

    /**
     * {@code DELETE  /skills/:id} : delete the "id" skills.
     *
     * @param id the id of the skills to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/skills/{id}")
    public ResponseEntity<Void> deleteSkills(@PathVariable Long id) {
        log.debug("REST request to delete Skills : {}", id);
        skillsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
