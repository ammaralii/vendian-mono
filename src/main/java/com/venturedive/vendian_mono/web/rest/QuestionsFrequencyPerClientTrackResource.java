package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.QuestionsFrequencyPerClientTrack;
import com.venturedive.vendian_mono.repository.QuestionsFrequencyPerClientTrackRepository;
import com.venturedive.vendian_mono.service.QuestionsFrequencyPerClientTrackQueryService;
import com.venturedive.vendian_mono.service.QuestionsFrequencyPerClientTrackService;
import com.venturedive.vendian_mono.service.criteria.QuestionsFrequencyPerClientTrackCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.QuestionsFrequencyPerClientTrack}.
 */
@RestController
@RequestMapping("/api")
public class QuestionsFrequencyPerClientTrackResource {

    private final Logger log = LoggerFactory.getLogger(QuestionsFrequencyPerClientTrackResource.class);

    private static final String ENTITY_NAME = "questionsFrequencyPerClientTrack";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuestionsFrequencyPerClientTrackService questionsFrequencyPerClientTrackService;

    private final QuestionsFrequencyPerClientTrackRepository questionsFrequencyPerClientTrackRepository;

    private final QuestionsFrequencyPerClientTrackQueryService questionsFrequencyPerClientTrackQueryService;

    public QuestionsFrequencyPerClientTrackResource(
        QuestionsFrequencyPerClientTrackService questionsFrequencyPerClientTrackService,
        QuestionsFrequencyPerClientTrackRepository questionsFrequencyPerClientTrackRepository,
        QuestionsFrequencyPerClientTrackQueryService questionsFrequencyPerClientTrackQueryService
    ) {
        this.questionsFrequencyPerClientTrackService = questionsFrequencyPerClientTrackService;
        this.questionsFrequencyPerClientTrackRepository = questionsFrequencyPerClientTrackRepository;
        this.questionsFrequencyPerClientTrackQueryService = questionsFrequencyPerClientTrackQueryService;
    }

    /**
     * {@code POST  /questions-frequency-per-client-tracks} : Create a new questionsFrequencyPerClientTrack.
     *
     * @param questionsFrequencyPerClientTrack the questionsFrequencyPerClientTrack to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new questionsFrequencyPerClientTrack, or with status {@code 400 (Bad Request)} if the questionsFrequencyPerClientTrack has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/questions-frequency-per-client-tracks")
    public ResponseEntity<QuestionsFrequencyPerClientTrack> createQuestionsFrequencyPerClientTrack(
        @Valid @RequestBody QuestionsFrequencyPerClientTrack questionsFrequencyPerClientTrack
    ) throws URISyntaxException {
        log.debug("REST request to save QuestionsFrequencyPerClientTrack : {}", questionsFrequencyPerClientTrack);
        if (questionsFrequencyPerClientTrack.getId() != null) {
            throw new BadRequestAlertException("A new questionsFrequencyPerClientTrack cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuestionsFrequencyPerClientTrack result = questionsFrequencyPerClientTrackService.save(questionsFrequencyPerClientTrack);
        return ResponseEntity
            .created(new URI("/api/questions-frequency-per-client-tracks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /questions-frequency-per-client-tracks/:id} : Updates an existing questionsFrequencyPerClientTrack.
     *
     * @param id the id of the questionsFrequencyPerClientTrack to save.
     * @param questionsFrequencyPerClientTrack the questionsFrequencyPerClientTrack to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated questionsFrequencyPerClientTrack,
     * or with status {@code 400 (Bad Request)} if the questionsFrequencyPerClientTrack is not valid,
     * or with status {@code 500 (Internal Server Error)} if the questionsFrequencyPerClientTrack couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/questions-frequency-per-client-tracks/{id}")
    public ResponseEntity<QuestionsFrequencyPerClientTrack> updateQuestionsFrequencyPerClientTrack(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody QuestionsFrequencyPerClientTrack questionsFrequencyPerClientTrack
    ) throws URISyntaxException {
        log.debug("REST request to update QuestionsFrequencyPerClientTrack : {}, {}", id, questionsFrequencyPerClientTrack);
        if (questionsFrequencyPerClientTrack.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, questionsFrequencyPerClientTrack.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!questionsFrequencyPerClientTrackRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        QuestionsFrequencyPerClientTrack result = questionsFrequencyPerClientTrackService.update(questionsFrequencyPerClientTrack);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, questionsFrequencyPerClientTrack.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /questions-frequency-per-client-tracks/:id} : Partial updates given fields of an existing questionsFrequencyPerClientTrack, field will ignore if it is null
     *
     * @param id the id of the questionsFrequencyPerClientTrack to save.
     * @param questionsFrequencyPerClientTrack the questionsFrequencyPerClientTrack to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated questionsFrequencyPerClientTrack,
     * or with status {@code 400 (Bad Request)} if the questionsFrequencyPerClientTrack is not valid,
     * or with status {@code 404 (Not Found)} if the questionsFrequencyPerClientTrack is not found,
     * or with status {@code 500 (Internal Server Error)} if the questionsFrequencyPerClientTrack couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/questions-frequency-per-client-tracks/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<QuestionsFrequencyPerClientTrack> partialUpdateQuestionsFrequencyPerClientTrack(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody QuestionsFrequencyPerClientTrack questionsFrequencyPerClientTrack
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update QuestionsFrequencyPerClientTrack partially : {}, {}",
            id,
            questionsFrequencyPerClientTrack
        );
        if (questionsFrequencyPerClientTrack.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, questionsFrequencyPerClientTrack.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!questionsFrequencyPerClientTrackRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<QuestionsFrequencyPerClientTrack> result = questionsFrequencyPerClientTrackService.partialUpdate(
            questionsFrequencyPerClientTrack
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, questionsFrequencyPerClientTrack.getId().toString())
        );
    }

    /**
     * {@code GET  /questions-frequency-per-client-tracks} : get all the questionsFrequencyPerClientTracks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of questionsFrequencyPerClientTracks in body.
     */
    @GetMapping("/questions-frequency-per-client-tracks")
    public ResponseEntity<List<QuestionsFrequencyPerClientTrack>> getAllQuestionsFrequencyPerClientTracks(
        QuestionsFrequencyPerClientTrackCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get QuestionsFrequencyPerClientTracks by criteria: {}", criteria);
        Page<QuestionsFrequencyPerClientTrack> page = questionsFrequencyPerClientTrackQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /questions-frequency-per-client-tracks/count} : count all the questionsFrequencyPerClientTracks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/questions-frequency-per-client-tracks/count")
    public ResponseEntity<Long> countQuestionsFrequencyPerClientTracks(QuestionsFrequencyPerClientTrackCriteria criteria) {
        log.debug("REST request to count QuestionsFrequencyPerClientTracks by criteria: {}", criteria);
        return ResponseEntity.ok().body(questionsFrequencyPerClientTrackQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /questions-frequency-per-client-tracks/:id} : get the "id" questionsFrequencyPerClientTrack.
     *
     * @param id the id of the questionsFrequencyPerClientTrack to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the questionsFrequencyPerClientTrack, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/questions-frequency-per-client-tracks/{id}")
    public ResponseEntity<QuestionsFrequencyPerClientTrack> getQuestionsFrequencyPerClientTrack(@PathVariable Long id) {
        log.debug("REST request to get QuestionsFrequencyPerClientTrack : {}", id);
        Optional<QuestionsFrequencyPerClientTrack> questionsFrequencyPerClientTrack = questionsFrequencyPerClientTrackService.findOne(id);
        return ResponseUtil.wrapOrNotFound(questionsFrequencyPerClientTrack);
    }

    /**
     * {@code DELETE  /questions-frequency-per-client-tracks/:id} : delete the "id" questionsFrequencyPerClientTrack.
     *
     * @param id the id of the questionsFrequencyPerClientTrack to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/questions-frequency-per-client-tracks/{id}")
    public ResponseEntity<Void> deleteQuestionsFrequencyPerClientTrack(@PathVariable Long id) {
        log.debug("REST request to delete QuestionsFrequencyPerClientTrack : {}", id);
        questionsFrequencyPerClientTrackService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
