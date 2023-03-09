package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.QuestionsProcessingEventLogs;
import com.venturedive.vendian_mono.repository.QuestionsProcessingEventLogsRepository;
import com.venturedive.vendian_mono.service.QuestionsProcessingEventLogsQueryService;
import com.venturedive.vendian_mono.service.QuestionsProcessingEventLogsService;
import com.venturedive.vendian_mono.service.criteria.QuestionsProcessingEventLogsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.QuestionsProcessingEventLogs}.
 */
@RestController
@RequestMapping("/api")
public class QuestionsProcessingEventLogsResource {

    private final Logger log = LoggerFactory.getLogger(QuestionsProcessingEventLogsResource.class);

    private static final String ENTITY_NAME = "questionsProcessingEventLogs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuestionsProcessingEventLogsService questionsProcessingEventLogsService;

    private final QuestionsProcessingEventLogsRepository questionsProcessingEventLogsRepository;

    private final QuestionsProcessingEventLogsQueryService questionsProcessingEventLogsQueryService;

    public QuestionsProcessingEventLogsResource(
        QuestionsProcessingEventLogsService questionsProcessingEventLogsService,
        QuestionsProcessingEventLogsRepository questionsProcessingEventLogsRepository,
        QuestionsProcessingEventLogsQueryService questionsProcessingEventLogsQueryService
    ) {
        this.questionsProcessingEventLogsService = questionsProcessingEventLogsService;
        this.questionsProcessingEventLogsRepository = questionsProcessingEventLogsRepository;
        this.questionsProcessingEventLogsQueryService = questionsProcessingEventLogsQueryService;
    }

    /**
     * {@code POST  /questions-processing-event-logs} : Create a new questionsProcessingEventLogs.
     *
     * @param questionsProcessingEventLogs the questionsProcessingEventLogs to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new questionsProcessingEventLogs, or with status {@code 400 (Bad Request)} if the questionsProcessingEventLogs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/questions-processing-event-logs")
    public ResponseEntity<QuestionsProcessingEventLogs> createQuestionsProcessingEventLogs(
        @Valid @RequestBody QuestionsProcessingEventLogs questionsProcessingEventLogs
    ) throws URISyntaxException {
        log.debug("REST request to save QuestionsProcessingEventLogs : {}", questionsProcessingEventLogs);
        if (questionsProcessingEventLogs.getId() != null) {
            throw new BadRequestAlertException("A new questionsProcessingEventLogs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuestionsProcessingEventLogs result = questionsProcessingEventLogsService.save(questionsProcessingEventLogs);
        return ResponseEntity
            .created(new URI("/api/questions-processing-event-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /questions-processing-event-logs/:id} : Updates an existing questionsProcessingEventLogs.
     *
     * @param id the id of the questionsProcessingEventLogs to save.
     * @param questionsProcessingEventLogs the questionsProcessingEventLogs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated questionsProcessingEventLogs,
     * or with status {@code 400 (Bad Request)} if the questionsProcessingEventLogs is not valid,
     * or with status {@code 500 (Internal Server Error)} if the questionsProcessingEventLogs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/questions-processing-event-logs/{id}")
    public ResponseEntity<QuestionsProcessingEventLogs> updateQuestionsProcessingEventLogs(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody QuestionsProcessingEventLogs questionsProcessingEventLogs
    ) throws URISyntaxException {
        log.debug("REST request to update QuestionsProcessingEventLogs : {}, {}", id, questionsProcessingEventLogs);
        if (questionsProcessingEventLogs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, questionsProcessingEventLogs.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!questionsProcessingEventLogsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        QuestionsProcessingEventLogs result = questionsProcessingEventLogsService.update(questionsProcessingEventLogs);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, questionsProcessingEventLogs.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /questions-processing-event-logs/:id} : Partial updates given fields of an existing questionsProcessingEventLogs, field will ignore if it is null
     *
     * @param id the id of the questionsProcessingEventLogs to save.
     * @param questionsProcessingEventLogs the questionsProcessingEventLogs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated questionsProcessingEventLogs,
     * or with status {@code 400 (Bad Request)} if the questionsProcessingEventLogs is not valid,
     * or with status {@code 404 (Not Found)} if the questionsProcessingEventLogs is not found,
     * or with status {@code 500 (Internal Server Error)} if the questionsProcessingEventLogs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/questions-processing-event-logs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<QuestionsProcessingEventLogs> partialUpdateQuestionsProcessingEventLogs(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody QuestionsProcessingEventLogs questionsProcessingEventLogs
    ) throws URISyntaxException {
        log.debug("REST request to partial update QuestionsProcessingEventLogs partially : {}, {}", id, questionsProcessingEventLogs);
        if (questionsProcessingEventLogs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, questionsProcessingEventLogs.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!questionsProcessingEventLogsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<QuestionsProcessingEventLogs> result = questionsProcessingEventLogsService.partialUpdate(questionsProcessingEventLogs);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, questionsProcessingEventLogs.getId().toString())
        );
    }

    /**
     * {@code GET  /questions-processing-event-logs} : get all the questionsProcessingEventLogs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of questionsProcessingEventLogs in body.
     */
    @GetMapping("/questions-processing-event-logs")
    public ResponseEntity<List<QuestionsProcessingEventLogs>> getAllQuestionsProcessingEventLogs(
        QuestionsProcessingEventLogsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get QuestionsProcessingEventLogs by criteria: {}", criteria);
        Page<QuestionsProcessingEventLogs> page = questionsProcessingEventLogsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /questions-processing-event-logs/count} : count all the questionsProcessingEventLogs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/questions-processing-event-logs/count")
    public ResponseEntity<Long> countQuestionsProcessingEventLogs(QuestionsProcessingEventLogsCriteria criteria) {
        log.debug("REST request to count QuestionsProcessingEventLogs by criteria: {}", criteria);
        return ResponseEntity.ok().body(questionsProcessingEventLogsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /questions-processing-event-logs/:id} : get the "id" questionsProcessingEventLogs.
     *
     * @param id the id of the questionsProcessingEventLogs to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the questionsProcessingEventLogs, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/questions-processing-event-logs/{id}")
    public ResponseEntity<QuestionsProcessingEventLogs> getQuestionsProcessingEventLogs(@PathVariable Long id) {
        log.debug("REST request to get QuestionsProcessingEventLogs : {}", id);
        Optional<QuestionsProcessingEventLogs> questionsProcessingEventLogs = questionsProcessingEventLogsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(questionsProcessingEventLogs);
    }

    /**
     * {@code DELETE  /questions-processing-event-logs/:id} : delete the "id" questionsProcessingEventLogs.
     *
     * @param id the id of the questionsProcessingEventLogs to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/questions-processing-event-logs/{id}")
    public ResponseEntity<Void> deleteQuestionsProcessingEventLogs(@PathVariable Long id) {
        log.debug("REST request to delete QuestionsProcessingEventLogs : {}", id);
        questionsProcessingEventLogsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
