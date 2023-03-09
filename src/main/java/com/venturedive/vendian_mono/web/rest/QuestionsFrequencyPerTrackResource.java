package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.QuestionsFrequencyPerTrack;
import com.venturedive.vendian_mono.repository.QuestionsFrequencyPerTrackRepository;
import com.venturedive.vendian_mono.service.QuestionsFrequencyPerTrackQueryService;
import com.venturedive.vendian_mono.service.QuestionsFrequencyPerTrackService;
import com.venturedive.vendian_mono.service.criteria.QuestionsFrequencyPerTrackCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.QuestionsFrequencyPerTrack}.
 */
@RestController
@RequestMapping("/api")
public class QuestionsFrequencyPerTrackResource {

    private final Logger log = LoggerFactory.getLogger(QuestionsFrequencyPerTrackResource.class);

    private static final String ENTITY_NAME = "questionsFrequencyPerTrack";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuestionsFrequencyPerTrackService questionsFrequencyPerTrackService;

    private final QuestionsFrequencyPerTrackRepository questionsFrequencyPerTrackRepository;

    private final QuestionsFrequencyPerTrackQueryService questionsFrequencyPerTrackQueryService;

    public QuestionsFrequencyPerTrackResource(
        QuestionsFrequencyPerTrackService questionsFrequencyPerTrackService,
        QuestionsFrequencyPerTrackRepository questionsFrequencyPerTrackRepository,
        QuestionsFrequencyPerTrackQueryService questionsFrequencyPerTrackQueryService
    ) {
        this.questionsFrequencyPerTrackService = questionsFrequencyPerTrackService;
        this.questionsFrequencyPerTrackRepository = questionsFrequencyPerTrackRepository;
        this.questionsFrequencyPerTrackQueryService = questionsFrequencyPerTrackQueryService;
    }

    /**
     * {@code POST  /questions-frequency-per-tracks} : Create a new questionsFrequencyPerTrack.
     *
     * @param questionsFrequencyPerTrack the questionsFrequencyPerTrack to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new questionsFrequencyPerTrack, or with status {@code 400 (Bad Request)} if the questionsFrequencyPerTrack has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/questions-frequency-per-tracks")
    public ResponseEntity<QuestionsFrequencyPerTrack> createQuestionsFrequencyPerTrack(
        @Valid @RequestBody QuestionsFrequencyPerTrack questionsFrequencyPerTrack
    ) throws URISyntaxException {
        log.debug("REST request to save QuestionsFrequencyPerTrack : {}", questionsFrequencyPerTrack);
        if (questionsFrequencyPerTrack.getId() != null) {
            throw new BadRequestAlertException("A new questionsFrequencyPerTrack cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuestionsFrequencyPerTrack result = questionsFrequencyPerTrackService.save(questionsFrequencyPerTrack);
        return ResponseEntity
            .created(new URI("/api/questions-frequency-per-tracks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /questions-frequency-per-tracks/:id} : Updates an existing questionsFrequencyPerTrack.
     *
     * @param id the id of the questionsFrequencyPerTrack to save.
     * @param questionsFrequencyPerTrack the questionsFrequencyPerTrack to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated questionsFrequencyPerTrack,
     * or with status {@code 400 (Bad Request)} if the questionsFrequencyPerTrack is not valid,
     * or with status {@code 500 (Internal Server Error)} if the questionsFrequencyPerTrack couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/questions-frequency-per-tracks/{id}")
    public ResponseEntity<QuestionsFrequencyPerTrack> updateQuestionsFrequencyPerTrack(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody QuestionsFrequencyPerTrack questionsFrequencyPerTrack
    ) throws URISyntaxException {
        log.debug("REST request to update QuestionsFrequencyPerTrack : {}, {}", id, questionsFrequencyPerTrack);
        if (questionsFrequencyPerTrack.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, questionsFrequencyPerTrack.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!questionsFrequencyPerTrackRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        QuestionsFrequencyPerTrack result = questionsFrequencyPerTrackService.update(questionsFrequencyPerTrack);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, questionsFrequencyPerTrack.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /questions-frequency-per-tracks/:id} : Partial updates given fields of an existing questionsFrequencyPerTrack, field will ignore if it is null
     *
     * @param id the id of the questionsFrequencyPerTrack to save.
     * @param questionsFrequencyPerTrack the questionsFrequencyPerTrack to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated questionsFrequencyPerTrack,
     * or with status {@code 400 (Bad Request)} if the questionsFrequencyPerTrack is not valid,
     * or with status {@code 404 (Not Found)} if the questionsFrequencyPerTrack is not found,
     * or with status {@code 500 (Internal Server Error)} if the questionsFrequencyPerTrack couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/questions-frequency-per-tracks/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<QuestionsFrequencyPerTrack> partialUpdateQuestionsFrequencyPerTrack(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody QuestionsFrequencyPerTrack questionsFrequencyPerTrack
    ) throws URISyntaxException {
        log.debug("REST request to partial update QuestionsFrequencyPerTrack partially : {}, {}", id, questionsFrequencyPerTrack);
        if (questionsFrequencyPerTrack.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, questionsFrequencyPerTrack.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!questionsFrequencyPerTrackRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<QuestionsFrequencyPerTrack> result = questionsFrequencyPerTrackService.partialUpdate(questionsFrequencyPerTrack);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, questionsFrequencyPerTrack.getId().toString())
        );
    }

    /**
     * {@code GET  /questions-frequency-per-tracks} : get all the questionsFrequencyPerTracks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of questionsFrequencyPerTracks in body.
     */
    @GetMapping("/questions-frequency-per-tracks")
    public ResponseEntity<List<QuestionsFrequencyPerTrack>> getAllQuestionsFrequencyPerTracks(
        QuestionsFrequencyPerTrackCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get QuestionsFrequencyPerTracks by criteria: {}", criteria);
        Page<QuestionsFrequencyPerTrack> page = questionsFrequencyPerTrackQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /questions-frequency-per-tracks/count} : count all the questionsFrequencyPerTracks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/questions-frequency-per-tracks/count")
    public ResponseEntity<Long> countQuestionsFrequencyPerTracks(QuestionsFrequencyPerTrackCriteria criteria) {
        log.debug("REST request to count QuestionsFrequencyPerTracks by criteria: {}", criteria);
        return ResponseEntity.ok().body(questionsFrequencyPerTrackQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /questions-frequency-per-tracks/:id} : get the "id" questionsFrequencyPerTrack.
     *
     * @param id the id of the questionsFrequencyPerTrack to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the questionsFrequencyPerTrack, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/questions-frequency-per-tracks/{id}")
    public ResponseEntity<QuestionsFrequencyPerTrack> getQuestionsFrequencyPerTrack(@PathVariable Long id) {
        log.debug("REST request to get QuestionsFrequencyPerTrack : {}", id);
        Optional<QuestionsFrequencyPerTrack> questionsFrequencyPerTrack = questionsFrequencyPerTrackService.findOne(id);
        return ResponseUtil.wrapOrNotFound(questionsFrequencyPerTrack);
    }

    /**
     * {@code DELETE  /questions-frequency-per-tracks/:id} : delete the "id" questionsFrequencyPerTrack.
     *
     * @param id the id of the questionsFrequencyPerTrack to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/questions-frequency-per-tracks/{id}")
    public ResponseEntity<Void> deleteQuestionsFrequencyPerTrack(@PathVariable Long id) {
        log.debug("REST request to delete QuestionsFrequencyPerTrack : {}", id);
        questionsFrequencyPerTrackService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
