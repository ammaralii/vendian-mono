package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.QuestionsProcessingEventLogs;
import com.venturedive.vendian_mono.repository.QuestionsProcessingEventLogsRepository;
import com.venturedive.vendian_mono.service.criteria.QuestionsProcessingEventLogsCriteria;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link QuestionsProcessingEventLogsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QuestionsProcessingEventLogsResourceIT {

    private static final String DEFAULT_PROCESSED_ON = "AAAAAAAAAA";
    private static final String UPDATED_PROCESSED_ON = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/questions-processing-event-logs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QuestionsProcessingEventLogsRepository questionsProcessingEventLogsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuestionsProcessingEventLogsMockMvc;

    private QuestionsProcessingEventLogs questionsProcessingEventLogs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionsProcessingEventLogs createEntity(EntityManager em) {
        QuestionsProcessingEventLogs questionsProcessingEventLogs = new QuestionsProcessingEventLogs()
            .processedOn(DEFAULT_PROCESSED_ON)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        return questionsProcessingEventLogs;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionsProcessingEventLogs createUpdatedEntity(EntityManager em) {
        QuestionsProcessingEventLogs questionsProcessingEventLogs = new QuestionsProcessingEventLogs()
            .processedOn(UPDATED_PROCESSED_ON)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        return questionsProcessingEventLogs;
    }

    @BeforeEach
    public void initTest() {
        questionsProcessingEventLogs = createEntity(em);
    }

    @Test
    @Transactional
    void createQuestionsProcessingEventLogs() throws Exception {
        int databaseSizeBeforeCreate = questionsProcessingEventLogsRepository.findAll().size();
        // Create the QuestionsProcessingEventLogs
        restQuestionsProcessingEventLogsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionsProcessingEventLogs))
            )
            .andExpect(status().isCreated());

        // Validate the QuestionsProcessingEventLogs in the database
        List<QuestionsProcessingEventLogs> questionsProcessingEventLogsList = questionsProcessingEventLogsRepository.findAll();
        assertThat(questionsProcessingEventLogsList).hasSize(databaseSizeBeforeCreate + 1);
        QuestionsProcessingEventLogs testQuestionsProcessingEventLogs = questionsProcessingEventLogsList.get(
            questionsProcessingEventLogsList.size() - 1
        );
        assertThat(testQuestionsProcessingEventLogs.getProcessedOn()).isEqualTo(DEFAULT_PROCESSED_ON);
        assertThat(testQuestionsProcessingEventLogs.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testQuestionsProcessingEventLogs.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createQuestionsProcessingEventLogsWithExistingId() throws Exception {
        // Create the QuestionsProcessingEventLogs with an existing ID
        questionsProcessingEventLogs.setId(1L);

        int databaseSizeBeforeCreate = questionsProcessingEventLogsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionsProcessingEventLogsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionsProcessingEventLogs))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuestionsProcessingEventLogs in the database
        List<QuestionsProcessingEventLogs> questionsProcessingEventLogsList = questionsProcessingEventLogsRepository.findAll();
        assertThat(questionsProcessingEventLogsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkProcessedOnIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionsProcessingEventLogsRepository.findAll().size();
        // set the field null
        questionsProcessingEventLogs.setProcessedOn(null);

        // Create the QuestionsProcessingEventLogs, which fails.

        restQuestionsProcessingEventLogsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionsProcessingEventLogs))
            )
            .andExpect(status().isBadRequest());

        List<QuestionsProcessingEventLogs> questionsProcessingEventLogsList = questionsProcessingEventLogsRepository.findAll();
        assertThat(questionsProcessingEventLogsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionsProcessingEventLogsRepository.findAll().size();
        // set the field null
        questionsProcessingEventLogs.setCreatedat(null);

        // Create the QuestionsProcessingEventLogs, which fails.

        restQuestionsProcessingEventLogsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionsProcessingEventLogs))
            )
            .andExpect(status().isBadRequest());

        List<QuestionsProcessingEventLogs> questionsProcessingEventLogsList = questionsProcessingEventLogsRepository.findAll();
        assertThat(questionsProcessingEventLogsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionsProcessingEventLogsRepository.findAll().size();
        // set the field null
        questionsProcessingEventLogs.setUpdatedat(null);

        // Create the QuestionsProcessingEventLogs, which fails.

        restQuestionsProcessingEventLogsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionsProcessingEventLogs))
            )
            .andExpect(status().isBadRequest());

        List<QuestionsProcessingEventLogs> questionsProcessingEventLogsList = questionsProcessingEventLogsRepository.findAll();
        assertThat(questionsProcessingEventLogsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllQuestionsProcessingEventLogs() throws Exception {
        // Initialize the database
        questionsProcessingEventLogsRepository.saveAndFlush(questionsProcessingEventLogs);

        // Get all the questionsProcessingEventLogsList
        restQuestionsProcessingEventLogsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionsProcessingEventLogs.getId().intValue())))
            .andExpect(jsonPath("$.[*].processedOn").value(hasItem(DEFAULT_PROCESSED_ON)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getQuestionsProcessingEventLogs() throws Exception {
        // Initialize the database
        questionsProcessingEventLogsRepository.saveAndFlush(questionsProcessingEventLogs);

        // Get the questionsProcessingEventLogs
        restQuestionsProcessingEventLogsMockMvc
            .perform(get(ENTITY_API_URL_ID, questionsProcessingEventLogs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(questionsProcessingEventLogs.getId().intValue()))
            .andExpect(jsonPath("$.processedOn").value(DEFAULT_PROCESSED_ON))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getQuestionsProcessingEventLogsByIdFiltering() throws Exception {
        // Initialize the database
        questionsProcessingEventLogsRepository.saveAndFlush(questionsProcessingEventLogs);

        Long id = questionsProcessingEventLogs.getId();

        defaultQuestionsProcessingEventLogsShouldBeFound("id.equals=" + id);
        defaultQuestionsProcessingEventLogsShouldNotBeFound("id.notEquals=" + id);

        defaultQuestionsProcessingEventLogsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultQuestionsProcessingEventLogsShouldNotBeFound("id.greaterThan=" + id);

        defaultQuestionsProcessingEventLogsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultQuestionsProcessingEventLogsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllQuestionsProcessingEventLogsByProcessedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        questionsProcessingEventLogsRepository.saveAndFlush(questionsProcessingEventLogs);

        // Get all the questionsProcessingEventLogsList where processedOn equals to DEFAULT_PROCESSED_ON
        defaultQuestionsProcessingEventLogsShouldBeFound("processedOn.equals=" + DEFAULT_PROCESSED_ON);

        // Get all the questionsProcessingEventLogsList where processedOn equals to UPDATED_PROCESSED_ON
        defaultQuestionsProcessingEventLogsShouldNotBeFound("processedOn.equals=" + UPDATED_PROCESSED_ON);
    }

    @Test
    @Transactional
    void getAllQuestionsProcessingEventLogsByProcessedOnIsInShouldWork() throws Exception {
        // Initialize the database
        questionsProcessingEventLogsRepository.saveAndFlush(questionsProcessingEventLogs);

        // Get all the questionsProcessingEventLogsList where processedOn in DEFAULT_PROCESSED_ON or UPDATED_PROCESSED_ON
        defaultQuestionsProcessingEventLogsShouldBeFound("processedOn.in=" + DEFAULT_PROCESSED_ON + "," + UPDATED_PROCESSED_ON);

        // Get all the questionsProcessingEventLogsList where processedOn equals to UPDATED_PROCESSED_ON
        defaultQuestionsProcessingEventLogsShouldNotBeFound("processedOn.in=" + UPDATED_PROCESSED_ON);
    }

    @Test
    @Transactional
    void getAllQuestionsProcessingEventLogsByProcessedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionsProcessingEventLogsRepository.saveAndFlush(questionsProcessingEventLogs);

        // Get all the questionsProcessingEventLogsList where processedOn is not null
        defaultQuestionsProcessingEventLogsShouldBeFound("processedOn.specified=true");

        // Get all the questionsProcessingEventLogsList where processedOn is null
        defaultQuestionsProcessingEventLogsShouldNotBeFound("processedOn.specified=false");
    }

    @Test
    @Transactional
    void getAllQuestionsProcessingEventLogsByProcessedOnContainsSomething() throws Exception {
        // Initialize the database
        questionsProcessingEventLogsRepository.saveAndFlush(questionsProcessingEventLogs);

        // Get all the questionsProcessingEventLogsList where processedOn contains DEFAULT_PROCESSED_ON
        defaultQuestionsProcessingEventLogsShouldBeFound("processedOn.contains=" + DEFAULT_PROCESSED_ON);

        // Get all the questionsProcessingEventLogsList where processedOn contains UPDATED_PROCESSED_ON
        defaultQuestionsProcessingEventLogsShouldNotBeFound("processedOn.contains=" + UPDATED_PROCESSED_ON);
    }

    @Test
    @Transactional
    void getAllQuestionsProcessingEventLogsByProcessedOnNotContainsSomething() throws Exception {
        // Initialize the database
        questionsProcessingEventLogsRepository.saveAndFlush(questionsProcessingEventLogs);

        // Get all the questionsProcessingEventLogsList where processedOn does not contain DEFAULT_PROCESSED_ON
        defaultQuestionsProcessingEventLogsShouldNotBeFound("processedOn.doesNotContain=" + DEFAULT_PROCESSED_ON);

        // Get all the questionsProcessingEventLogsList where processedOn does not contain UPDATED_PROCESSED_ON
        defaultQuestionsProcessingEventLogsShouldBeFound("processedOn.doesNotContain=" + UPDATED_PROCESSED_ON);
    }

    @Test
    @Transactional
    void getAllQuestionsProcessingEventLogsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        questionsProcessingEventLogsRepository.saveAndFlush(questionsProcessingEventLogs);

        // Get all the questionsProcessingEventLogsList where createdat equals to DEFAULT_CREATEDAT
        defaultQuestionsProcessingEventLogsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the questionsProcessingEventLogsList where createdat equals to UPDATED_CREATEDAT
        defaultQuestionsProcessingEventLogsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllQuestionsProcessingEventLogsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        questionsProcessingEventLogsRepository.saveAndFlush(questionsProcessingEventLogs);

        // Get all the questionsProcessingEventLogsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultQuestionsProcessingEventLogsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the questionsProcessingEventLogsList where createdat equals to UPDATED_CREATEDAT
        defaultQuestionsProcessingEventLogsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllQuestionsProcessingEventLogsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionsProcessingEventLogsRepository.saveAndFlush(questionsProcessingEventLogs);

        // Get all the questionsProcessingEventLogsList where createdat is not null
        defaultQuestionsProcessingEventLogsShouldBeFound("createdat.specified=true");

        // Get all the questionsProcessingEventLogsList where createdat is null
        defaultQuestionsProcessingEventLogsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllQuestionsProcessingEventLogsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        questionsProcessingEventLogsRepository.saveAndFlush(questionsProcessingEventLogs);

        // Get all the questionsProcessingEventLogsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultQuestionsProcessingEventLogsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the questionsProcessingEventLogsList where updatedat equals to UPDATED_UPDATEDAT
        defaultQuestionsProcessingEventLogsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllQuestionsProcessingEventLogsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        questionsProcessingEventLogsRepository.saveAndFlush(questionsProcessingEventLogs);

        // Get all the questionsProcessingEventLogsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultQuestionsProcessingEventLogsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the questionsProcessingEventLogsList where updatedat equals to UPDATED_UPDATEDAT
        defaultQuestionsProcessingEventLogsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllQuestionsProcessingEventLogsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionsProcessingEventLogsRepository.saveAndFlush(questionsProcessingEventLogs);

        // Get all the questionsProcessingEventLogsList where updatedat is not null
        defaultQuestionsProcessingEventLogsShouldBeFound("updatedat.specified=true");

        // Get all the questionsProcessingEventLogsList where updatedat is null
        defaultQuestionsProcessingEventLogsShouldNotBeFound("updatedat.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultQuestionsProcessingEventLogsShouldBeFound(String filter) throws Exception {
        restQuestionsProcessingEventLogsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionsProcessingEventLogs.getId().intValue())))
            .andExpect(jsonPath("$.[*].processedOn").value(hasItem(DEFAULT_PROCESSED_ON)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restQuestionsProcessingEventLogsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultQuestionsProcessingEventLogsShouldNotBeFound(String filter) throws Exception {
        restQuestionsProcessingEventLogsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restQuestionsProcessingEventLogsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingQuestionsProcessingEventLogs() throws Exception {
        // Get the questionsProcessingEventLogs
        restQuestionsProcessingEventLogsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingQuestionsProcessingEventLogs() throws Exception {
        // Initialize the database
        questionsProcessingEventLogsRepository.saveAndFlush(questionsProcessingEventLogs);

        int databaseSizeBeforeUpdate = questionsProcessingEventLogsRepository.findAll().size();

        // Update the questionsProcessingEventLogs
        QuestionsProcessingEventLogs updatedQuestionsProcessingEventLogs = questionsProcessingEventLogsRepository
            .findById(questionsProcessingEventLogs.getId())
            .get();
        // Disconnect from session so that the updates on updatedQuestionsProcessingEventLogs are not directly saved in db
        em.detach(updatedQuestionsProcessingEventLogs);
        updatedQuestionsProcessingEventLogs.processedOn(UPDATED_PROCESSED_ON).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restQuestionsProcessingEventLogsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedQuestionsProcessingEventLogs.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedQuestionsProcessingEventLogs))
            )
            .andExpect(status().isOk());

        // Validate the QuestionsProcessingEventLogs in the database
        List<QuestionsProcessingEventLogs> questionsProcessingEventLogsList = questionsProcessingEventLogsRepository.findAll();
        assertThat(questionsProcessingEventLogsList).hasSize(databaseSizeBeforeUpdate);
        QuestionsProcessingEventLogs testQuestionsProcessingEventLogs = questionsProcessingEventLogsList.get(
            questionsProcessingEventLogsList.size() - 1
        );
        assertThat(testQuestionsProcessingEventLogs.getProcessedOn()).isEqualTo(UPDATED_PROCESSED_ON);
        assertThat(testQuestionsProcessingEventLogs.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testQuestionsProcessingEventLogs.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingQuestionsProcessingEventLogs() throws Exception {
        int databaseSizeBeforeUpdate = questionsProcessingEventLogsRepository.findAll().size();
        questionsProcessingEventLogs.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionsProcessingEventLogsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, questionsProcessingEventLogs.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionsProcessingEventLogs))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuestionsProcessingEventLogs in the database
        List<QuestionsProcessingEventLogs> questionsProcessingEventLogsList = questionsProcessingEventLogsRepository.findAll();
        assertThat(questionsProcessingEventLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQuestionsProcessingEventLogs() throws Exception {
        int databaseSizeBeforeUpdate = questionsProcessingEventLogsRepository.findAll().size();
        questionsProcessingEventLogs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionsProcessingEventLogsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionsProcessingEventLogs))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuestionsProcessingEventLogs in the database
        List<QuestionsProcessingEventLogs> questionsProcessingEventLogsList = questionsProcessingEventLogsRepository.findAll();
        assertThat(questionsProcessingEventLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQuestionsProcessingEventLogs() throws Exception {
        int databaseSizeBeforeUpdate = questionsProcessingEventLogsRepository.findAll().size();
        questionsProcessingEventLogs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionsProcessingEventLogsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionsProcessingEventLogs))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QuestionsProcessingEventLogs in the database
        List<QuestionsProcessingEventLogs> questionsProcessingEventLogsList = questionsProcessingEventLogsRepository.findAll();
        assertThat(questionsProcessingEventLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQuestionsProcessingEventLogsWithPatch() throws Exception {
        // Initialize the database
        questionsProcessingEventLogsRepository.saveAndFlush(questionsProcessingEventLogs);

        int databaseSizeBeforeUpdate = questionsProcessingEventLogsRepository.findAll().size();

        // Update the questionsProcessingEventLogs using partial update
        QuestionsProcessingEventLogs partialUpdatedQuestionsProcessingEventLogs = new QuestionsProcessingEventLogs();
        partialUpdatedQuestionsProcessingEventLogs.setId(questionsProcessingEventLogs.getId());

        partialUpdatedQuestionsProcessingEventLogs.createdat(UPDATED_CREATEDAT);

        restQuestionsProcessingEventLogsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuestionsProcessingEventLogs.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuestionsProcessingEventLogs))
            )
            .andExpect(status().isOk());

        // Validate the QuestionsProcessingEventLogs in the database
        List<QuestionsProcessingEventLogs> questionsProcessingEventLogsList = questionsProcessingEventLogsRepository.findAll();
        assertThat(questionsProcessingEventLogsList).hasSize(databaseSizeBeforeUpdate);
        QuestionsProcessingEventLogs testQuestionsProcessingEventLogs = questionsProcessingEventLogsList.get(
            questionsProcessingEventLogsList.size() - 1
        );
        assertThat(testQuestionsProcessingEventLogs.getProcessedOn()).isEqualTo(DEFAULT_PROCESSED_ON);
        assertThat(testQuestionsProcessingEventLogs.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testQuestionsProcessingEventLogs.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateQuestionsProcessingEventLogsWithPatch() throws Exception {
        // Initialize the database
        questionsProcessingEventLogsRepository.saveAndFlush(questionsProcessingEventLogs);

        int databaseSizeBeforeUpdate = questionsProcessingEventLogsRepository.findAll().size();

        // Update the questionsProcessingEventLogs using partial update
        QuestionsProcessingEventLogs partialUpdatedQuestionsProcessingEventLogs = new QuestionsProcessingEventLogs();
        partialUpdatedQuestionsProcessingEventLogs.setId(questionsProcessingEventLogs.getId());

        partialUpdatedQuestionsProcessingEventLogs
            .processedOn(UPDATED_PROCESSED_ON)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restQuestionsProcessingEventLogsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuestionsProcessingEventLogs.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuestionsProcessingEventLogs))
            )
            .andExpect(status().isOk());

        // Validate the QuestionsProcessingEventLogs in the database
        List<QuestionsProcessingEventLogs> questionsProcessingEventLogsList = questionsProcessingEventLogsRepository.findAll();
        assertThat(questionsProcessingEventLogsList).hasSize(databaseSizeBeforeUpdate);
        QuestionsProcessingEventLogs testQuestionsProcessingEventLogs = questionsProcessingEventLogsList.get(
            questionsProcessingEventLogsList.size() - 1
        );
        assertThat(testQuestionsProcessingEventLogs.getProcessedOn()).isEqualTo(UPDATED_PROCESSED_ON);
        assertThat(testQuestionsProcessingEventLogs.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testQuestionsProcessingEventLogs.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingQuestionsProcessingEventLogs() throws Exception {
        int databaseSizeBeforeUpdate = questionsProcessingEventLogsRepository.findAll().size();
        questionsProcessingEventLogs.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionsProcessingEventLogsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, questionsProcessingEventLogs.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(questionsProcessingEventLogs))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuestionsProcessingEventLogs in the database
        List<QuestionsProcessingEventLogs> questionsProcessingEventLogsList = questionsProcessingEventLogsRepository.findAll();
        assertThat(questionsProcessingEventLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQuestionsProcessingEventLogs() throws Exception {
        int databaseSizeBeforeUpdate = questionsProcessingEventLogsRepository.findAll().size();
        questionsProcessingEventLogs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionsProcessingEventLogsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(questionsProcessingEventLogs))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuestionsProcessingEventLogs in the database
        List<QuestionsProcessingEventLogs> questionsProcessingEventLogsList = questionsProcessingEventLogsRepository.findAll();
        assertThat(questionsProcessingEventLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQuestionsProcessingEventLogs() throws Exception {
        int databaseSizeBeforeUpdate = questionsProcessingEventLogsRepository.findAll().size();
        questionsProcessingEventLogs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionsProcessingEventLogsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(questionsProcessingEventLogs))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QuestionsProcessingEventLogs in the database
        List<QuestionsProcessingEventLogs> questionsProcessingEventLogsList = questionsProcessingEventLogsRepository.findAll();
        assertThat(questionsProcessingEventLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQuestionsProcessingEventLogs() throws Exception {
        // Initialize the database
        questionsProcessingEventLogsRepository.saveAndFlush(questionsProcessingEventLogs);

        int databaseSizeBeforeDelete = questionsProcessingEventLogsRepository.findAll().size();

        // Delete the questionsProcessingEventLogs
        restQuestionsProcessingEventLogsMockMvc
            .perform(delete(ENTITY_API_URL_ID, questionsProcessingEventLogs.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QuestionsProcessingEventLogs> questionsProcessingEventLogsList = questionsProcessingEventLogsRepository.findAll();
        assertThat(questionsProcessingEventLogsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
