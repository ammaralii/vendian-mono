package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Projects;
import com.venturedive.vendian_mono.domain.QuestionsFrequencyPerClientTrack;
import com.venturedive.vendian_mono.domain.Tracks;
import com.venturedive.vendian_mono.repository.QuestionsFrequencyPerClientTrackRepository;
import com.venturedive.vendian_mono.service.criteria.QuestionsFrequencyPerClientTrackCriteria;
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
 * Integration tests for the {@link QuestionsFrequencyPerClientTrackResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QuestionsFrequencyPerClientTrackResourceIT {

    private static final String DEFAULT_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_FREQUENCY = 1;
    private static final Integer UPDATED_FREQUENCY = 2;
    private static final Integer SMALLER_FREQUENCY = 1 - 1;

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/questions-frequency-per-client-tracks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QuestionsFrequencyPerClientTrackRepository questionsFrequencyPerClientTrackRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuestionsFrequencyPerClientTrackMockMvc;

    private QuestionsFrequencyPerClientTrack questionsFrequencyPerClientTrack;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionsFrequencyPerClientTrack createEntity(EntityManager em) {
        QuestionsFrequencyPerClientTrack questionsFrequencyPerClientTrack = new QuestionsFrequencyPerClientTrack()
            .question(DEFAULT_QUESTION)
            .frequency(DEFAULT_FREQUENCY)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        return questionsFrequencyPerClientTrack;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionsFrequencyPerClientTrack createUpdatedEntity(EntityManager em) {
        QuestionsFrequencyPerClientTrack questionsFrequencyPerClientTrack = new QuestionsFrequencyPerClientTrack()
            .question(UPDATED_QUESTION)
            .frequency(UPDATED_FREQUENCY)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        return questionsFrequencyPerClientTrack;
    }

    @BeforeEach
    public void initTest() {
        questionsFrequencyPerClientTrack = createEntity(em);
    }

    @Test
    @Transactional
    void createQuestionsFrequencyPerClientTrack() throws Exception {
        int databaseSizeBeforeCreate = questionsFrequencyPerClientTrackRepository.findAll().size();
        // Create the QuestionsFrequencyPerClientTrack
        restQuestionsFrequencyPerClientTrackMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionsFrequencyPerClientTrack))
            )
            .andExpect(status().isCreated());

        // Validate the QuestionsFrequencyPerClientTrack in the database
        List<QuestionsFrequencyPerClientTrack> questionsFrequencyPerClientTrackList = questionsFrequencyPerClientTrackRepository.findAll();
        assertThat(questionsFrequencyPerClientTrackList).hasSize(databaseSizeBeforeCreate + 1);
        QuestionsFrequencyPerClientTrack testQuestionsFrequencyPerClientTrack = questionsFrequencyPerClientTrackList.get(
            questionsFrequencyPerClientTrackList.size() - 1
        );
        assertThat(testQuestionsFrequencyPerClientTrack.getQuestion()).isEqualTo(DEFAULT_QUESTION);
        assertThat(testQuestionsFrequencyPerClientTrack.getFrequency()).isEqualTo(DEFAULT_FREQUENCY);
        assertThat(testQuestionsFrequencyPerClientTrack.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testQuestionsFrequencyPerClientTrack.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createQuestionsFrequencyPerClientTrackWithExistingId() throws Exception {
        // Create the QuestionsFrequencyPerClientTrack with an existing ID
        questionsFrequencyPerClientTrack.setId(1L);

        int databaseSizeBeforeCreate = questionsFrequencyPerClientTrackRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionsFrequencyPerClientTrackMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionsFrequencyPerClientTrack))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuestionsFrequencyPerClientTrack in the database
        List<QuestionsFrequencyPerClientTrack> questionsFrequencyPerClientTrackList = questionsFrequencyPerClientTrackRepository.findAll();
        assertThat(questionsFrequencyPerClientTrackList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkQuestionIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionsFrequencyPerClientTrackRepository.findAll().size();
        // set the field null
        questionsFrequencyPerClientTrack.setQuestion(null);

        // Create the QuestionsFrequencyPerClientTrack, which fails.

        restQuestionsFrequencyPerClientTrackMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionsFrequencyPerClientTrack))
            )
            .andExpect(status().isBadRequest());

        List<QuestionsFrequencyPerClientTrack> questionsFrequencyPerClientTrackList = questionsFrequencyPerClientTrackRepository.findAll();
        assertThat(questionsFrequencyPerClientTrackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFrequencyIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionsFrequencyPerClientTrackRepository.findAll().size();
        // set the field null
        questionsFrequencyPerClientTrack.setFrequency(null);

        // Create the QuestionsFrequencyPerClientTrack, which fails.

        restQuestionsFrequencyPerClientTrackMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionsFrequencyPerClientTrack))
            )
            .andExpect(status().isBadRequest());

        List<QuestionsFrequencyPerClientTrack> questionsFrequencyPerClientTrackList = questionsFrequencyPerClientTrackRepository.findAll();
        assertThat(questionsFrequencyPerClientTrackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionsFrequencyPerClientTrackRepository.findAll().size();
        // set the field null
        questionsFrequencyPerClientTrack.setCreatedat(null);

        // Create the QuestionsFrequencyPerClientTrack, which fails.

        restQuestionsFrequencyPerClientTrackMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionsFrequencyPerClientTrack))
            )
            .andExpect(status().isBadRequest());

        List<QuestionsFrequencyPerClientTrack> questionsFrequencyPerClientTrackList = questionsFrequencyPerClientTrackRepository.findAll();
        assertThat(questionsFrequencyPerClientTrackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionsFrequencyPerClientTrackRepository.findAll().size();
        // set the field null
        questionsFrequencyPerClientTrack.setUpdatedat(null);

        // Create the QuestionsFrequencyPerClientTrack, which fails.

        restQuestionsFrequencyPerClientTrackMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionsFrequencyPerClientTrack))
            )
            .andExpect(status().isBadRequest());

        List<QuestionsFrequencyPerClientTrack> questionsFrequencyPerClientTrackList = questionsFrequencyPerClientTrackRepository.findAll();
        assertThat(questionsFrequencyPerClientTrackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerClientTracks() throws Exception {
        // Initialize the database
        questionsFrequencyPerClientTrackRepository.saveAndFlush(questionsFrequencyPerClientTrack);

        // Get all the questionsFrequencyPerClientTrackList
        restQuestionsFrequencyPerClientTrackMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionsFrequencyPerClientTrack.getId().intValue())))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION)))
            .andExpect(jsonPath("$.[*].frequency").value(hasItem(DEFAULT_FREQUENCY)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getQuestionsFrequencyPerClientTrack() throws Exception {
        // Initialize the database
        questionsFrequencyPerClientTrackRepository.saveAndFlush(questionsFrequencyPerClientTrack);

        // Get the questionsFrequencyPerClientTrack
        restQuestionsFrequencyPerClientTrackMockMvc
            .perform(get(ENTITY_API_URL_ID, questionsFrequencyPerClientTrack.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(questionsFrequencyPerClientTrack.getId().intValue()))
            .andExpect(jsonPath("$.question").value(DEFAULT_QUESTION))
            .andExpect(jsonPath("$.frequency").value(DEFAULT_FREQUENCY))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getQuestionsFrequencyPerClientTracksByIdFiltering() throws Exception {
        // Initialize the database
        questionsFrequencyPerClientTrackRepository.saveAndFlush(questionsFrequencyPerClientTrack);

        Long id = questionsFrequencyPerClientTrack.getId();

        defaultQuestionsFrequencyPerClientTrackShouldBeFound("id.equals=" + id);
        defaultQuestionsFrequencyPerClientTrackShouldNotBeFound("id.notEquals=" + id);

        defaultQuestionsFrequencyPerClientTrackShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultQuestionsFrequencyPerClientTrackShouldNotBeFound("id.greaterThan=" + id);

        defaultQuestionsFrequencyPerClientTrackShouldBeFound("id.lessThanOrEqual=" + id);
        defaultQuestionsFrequencyPerClientTrackShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerClientTracksByQuestionIsEqualToSomething() throws Exception {
        // Initialize the database
        questionsFrequencyPerClientTrackRepository.saveAndFlush(questionsFrequencyPerClientTrack);

        // Get all the questionsFrequencyPerClientTrackList where question equals to DEFAULT_QUESTION
        defaultQuestionsFrequencyPerClientTrackShouldBeFound("question.equals=" + DEFAULT_QUESTION);

        // Get all the questionsFrequencyPerClientTrackList where question equals to UPDATED_QUESTION
        defaultQuestionsFrequencyPerClientTrackShouldNotBeFound("question.equals=" + UPDATED_QUESTION);
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerClientTracksByQuestionIsInShouldWork() throws Exception {
        // Initialize the database
        questionsFrequencyPerClientTrackRepository.saveAndFlush(questionsFrequencyPerClientTrack);

        // Get all the questionsFrequencyPerClientTrackList where question in DEFAULT_QUESTION or UPDATED_QUESTION
        defaultQuestionsFrequencyPerClientTrackShouldBeFound("question.in=" + DEFAULT_QUESTION + "," + UPDATED_QUESTION);

        // Get all the questionsFrequencyPerClientTrackList where question equals to UPDATED_QUESTION
        defaultQuestionsFrequencyPerClientTrackShouldNotBeFound("question.in=" + UPDATED_QUESTION);
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerClientTracksByQuestionIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionsFrequencyPerClientTrackRepository.saveAndFlush(questionsFrequencyPerClientTrack);

        // Get all the questionsFrequencyPerClientTrackList where question is not null
        defaultQuestionsFrequencyPerClientTrackShouldBeFound("question.specified=true");

        // Get all the questionsFrequencyPerClientTrackList where question is null
        defaultQuestionsFrequencyPerClientTrackShouldNotBeFound("question.specified=false");
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerClientTracksByQuestionContainsSomething() throws Exception {
        // Initialize the database
        questionsFrequencyPerClientTrackRepository.saveAndFlush(questionsFrequencyPerClientTrack);

        // Get all the questionsFrequencyPerClientTrackList where question contains DEFAULT_QUESTION
        defaultQuestionsFrequencyPerClientTrackShouldBeFound("question.contains=" + DEFAULT_QUESTION);

        // Get all the questionsFrequencyPerClientTrackList where question contains UPDATED_QUESTION
        defaultQuestionsFrequencyPerClientTrackShouldNotBeFound("question.contains=" + UPDATED_QUESTION);
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerClientTracksByQuestionNotContainsSomething() throws Exception {
        // Initialize the database
        questionsFrequencyPerClientTrackRepository.saveAndFlush(questionsFrequencyPerClientTrack);

        // Get all the questionsFrequencyPerClientTrackList where question does not contain DEFAULT_QUESTION
        defaultQuestionsFrequencyPerClientTrackShouldNotBeFound("question.doesNotContain=" + DEFAULT_QUESTION);

        // Get all the questionsFrequencyPerClientTrackList where question does not contain UPDATED_QUESTION
        defaultQuestionsFrequencyPerClientTrackShouldBeFound("question.doesNotContain=" + UPDATED_QUESTION);
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerClientTracksByFrequencyIsEqualToSomething() throws Exception {
        // Initialize the database
        questionsFrequencyPerClientTrackRepository.saveAndFlush(questionsFrequencyPerClientTrack);

        // Get all the questionsFrequencyPerClientTrackList where frequency equals to DEFAULT_FREQUENCY
        defaultQuestionsFrequencyPerClientTrackShouldBeFound("frequency.equals=" + DEFAULT_FREQUENCY);

        // Get all the questionsFrequencyPerClientTrackList where frequency equals to UPDATED_FREQUENCY
        defaultQuestionsFrequencyPerClientTrackShouldNotBeFound("frequency.equals=" + UPDATED_FREQUENCY);
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerClientTracksByFrequencyIsInShouldWork() throws Exception {
        // Initialize the database
        questionsFrequencyPerClientTrackRepository.saveAndFlush(questionsFrequencyPerClientTrack);

        // Get all the questionsFrequencyPerClientTrackList where frequency in DEFAULT_FREQUENCY or UPDATED_FREQUENCY
        defaultQuestionsFrequencyPerClientTrackShouldBeFound("frequency.in=" + DEFAULT_FREQUENCY + "," + UPDATED_FREQUENCY);

        // Get all the questionsFrequencyPerClientTrackList where frequency equals to UPDATED_FREQUENCY
        defaultQuestionsFrequencyPerClientTrackShouldNotBeFound("frequency.in=" + UPDATED_FREQUENCY);
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerClientTracksByFrequencyIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionsFrequencyPerClientTrackRepository.saveAndFlush(questionsFrequencyPerClientTrack);

        // Get all the questionsFrequencyPerClientTrackList where frequency is not null
        defaultQuestionsFrequencyPerClientTrackShouldBeFound("frequency.specified=true");

        // Get all the questionsFrequencyPerClientTrackList where frequency is null
        defaultQuestionsFrequencyPerClientTrackShouldNotBeFound("frequency.specified=false");
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerClientTracksByFrequencyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        questionsFrequencyPerClientTrackRepository.saveAndFlush(questionsFrequencyPerClientTrack);

        // Get all the questionsFrequencyPerClientTrackList where frequency is greater than or equal to DEFAULT_FREQUENCY
        defaultQuestionsFrequencyPerClientTrackShouldBeFound("frequency.greaterThanOrEqual=" + DEFAULT_FREQUENCY);

        // Get all the questionsFrequencyPerClientTrackList where frequency is greater than or equal to UPDATED_FREQUENCY
        defaultQuestionsFrequencyPerClientTrackShouldNotBeFound("frequency.greaterThanOrEqual=" + UPDATED_FREQUENCY);
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerClientTracksByFrequencyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        questionsFrequencyPerClientTrackRepository.saveAndFlush(questionsFrequencyPerClientTrack);

        // Get all the questionsFrequencyPerClientTrackList where frequency is less than or equal to DEFAULT_FREQUENCY
        defaultQuestionsFrequencyPerClientTrackShouldBeFound("frequency.lessThanOrEqual=" + DEFAULT_FREQUENCY);

        // Get all the questionsFrequencyPerClientTrackList where frequency is less than or equal to SMALLER_FREQUENCY
        defaultQuestionsFrequencyPerClientTrackShouldNotBeFound("frequency.lessThanOrEqual=" + SMALLER_FREQUENCY);
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerClientTracksByFrequencyIsLessThanSomething() throws Exception {
        // Initialize the database
        questionsFrequencyPerClientTrackRepository.saveAndFlush(questionsFrequencyPerClientTrack);

        // Get all the questionsFrequencyPerClientTrackList where frequency is less than DEFAULT_FREQUENCY
        defaultQuestionsFrequencyPerClientTrackShouldNotBeFound("frequency.lessThan=" + DEFAULT_FREQUENCY);

        // Get all the questionsFrequencyPerClientTrackList where frequency is less than UPDATED_FREQUENCY
        defaultQuestionsFrequencyPerClientTrackShouldBeFound("frequency.lessThan=" + UPDATED_FREQUENCY);
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerClientTracksByFrequencyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        questionsFrequencyPerClientTrackRepository.saveAndFlush(questionsFrequencyPerClientTrack);

        // Get all the questionsFrequencyPerClientTrackList where frequency is greater than DEFAULT_FREQUENCY
        defaultQuestionsFrequencyPerClientTrackShouldNotBeFound("frequency.greaterThan=" + DEFAULT_FREQUENCY);

        // Get all the questionsFrequencyPerClientTrackList where frequency is greater than SMALLER_FREQUENCY
        defaultQuestionsFrequencyPerClientTrackShouldBeFound("frequency.greaterThan=" + SMALLER_FREQUENCY);
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerClientTracksByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        questionsFrequencyPerClientTrackRepository.saveAndFlush(questionsFrequencyPerClientTrack);

        // Get all the questionsFrequencyPerClientTrackList where createdat equals to DEFAULT_CREATEDAT
        defaultQuestionsFrequencyPerClientTrackShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the questionsFrequencyPerClientTrackList where createdat equals to UPDATED_CREATEDAT
        defaultQuestionsFrequencyPerClientTrackShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerClientTracksByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        questionsFrequencyPerClientTrackRepository.saveAndFlush(questionsFrequencyPerClientTrack);

        // Get all the questionsFrequencyPerClientTrackList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultQuestionsFrequencyPerClientTrackShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the questionsFrequencyPerClientTrackList where createdat equals to UPDATED_CREATEDAT
        defaultQuestionsFrequencyPerClientTrackShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerClientTracksByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionsFrequencyPerClientTrackRepository.saveAndFlush(questionsFrequencyPerClientTrack);

        // Get all the questionsFrequencyPerClientTrackList where createdat is not null
        defaultQuestionsFrequencyPerClientTrackShouldBeFound("createdat.specified=true");

        // Get all the questionsFrequencyPerClientTrackList where createdat is null
        defaultQuestionsFrequencyPerClientTrackShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerClientTracksByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        questionsFrequencyPerClientTrackRepository.saveAndFlush(questionsFrequencyPerClientTrack);

        // Get all the questionsFrequencyPerClientTrackList where updatedat equals to DEFAULT_UPDATEDAT
        defaultQuestionsFrequencyPerClientTrackShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the questionsFrequencyPerClientTrackList where updatedat equals to UPDATED_UPDATEDAT
        defaultQuestionsFrequencyPerClientTrackShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerClientTracksByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        questionsFrequencyPerClientTrackRepository.saveAndFlush(questionsFrequencyPerClientTrack);

        // Get all the questionsFrequencyPerClientTrackList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultQuestionsFrequencyPerClientTrackShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the questionsFrequencyPerClientTrackList where updatedat equals to UPDATED_UPDATEDAT
        defaultQuestionsFrequencyPerClientTrackShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerClientTracksByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionsFrequencyPerClientTrackRepository.saveAndFlush(questionsFrequencyPerClientTrack);

        // Get all the questionsFrequencyPerClientTrackList where updatedat is not null
        defaultQuestionsFrequencyPerClientTrackShouldBeFound("updatedat.specified=true");

        // Get all the questionsFrequencyPerClientTrackList where updatedat is null
        defaultQuestionsFrequencyPerClientTrackShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerClientTracksByProjectIsEqualToSomething() throws Exception {
        Projects project;
        if (TestUtil.findAll(em, Projects.class).isEmpty()) {
            questionsFrequencyPerClientTrackRepository.saveAndFlush(questionsFrequencyPerClientTrack);
            project = ProjectsResourceIT.createEntity(em);
        } else {
            project = TestUtil.findAll(em, Projects.class).get(0);
        }
        em.persist(project);
        em.flush();
        questionsFrequencyPerClientTrack.setProject(project);
        questionsFrequencyPerClientTrackRepository.saveAndFlush(questionsFrequencyPerClientTrack);
        Long projectId = project.getId();

        // Get all the questionsFrequencyPerClientTrackList where project equals to projectId
        defaultQuestionsFrequencyPerClientTrackShouldBeFound("projectId.equals=" + projectId);

        // Get all the questionsFrequencyPerClientTrackList where project equals to (projectId + 1)
        defaultQuestionsFrequencyPerClientTrackShouldNotBeFound("projectId.equals=" + (projectId + 1));
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerClientTracksByTrackIsEqualToSomething() throws Exception {
        Tracks track;
        if (TestUtil.findAll(em, Tracks.class).isEmpty()) {
            questionsFrequencyPerClientTrackRepository.saveAndFlush(questionsFrequencyPerClientTrack);
            track = TracksResourceIT.createEntity(em);
        } else {
            track = TestUtil.findAll(em, Tracks.class).get(0);
        }
        em.persist(track);
        em.flush();
        questionsFrequencyPerClientTrack.setTrack(track);
        questionsFrequencyPerClientTrackRepository.saveAndFlush(questionsFrequencyPerClientTrack);
        Long trackId = track.getId();

        // Get all the questionsFrequencyPerClientTrackList where track equals to trackId
        defaultQuestionsFrequencyPerClientTrackShouldBeFound("trackId.equals=" + trackId);

        // Get all the questionsFrequencyPerClientTrackList where track equals to (trackId + 1)
        defaultQuestionsFrequencyPerClientTrackShouldNotBeFound("trackId.equals=" + (trackId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultQuestionsFrequencyPerClientTrackShouldBeFound(String filter) throws Exception {
        restQuestionsFrequencyPerClientTrackMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionsFrequencyPerClientTrack.getId().intValue())))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION)))
            .andExpect(jsonPath("$.[*].frequency").value(hasItem(DEFAULT_FREQUENCY)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restQuestionsFrequencyPerClientTrackMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultQuestionsFrequencyPerClientTrackShouldNotBeFound(String filter) throws Exception {
        restQuestionsFrequencyPerClientTrackMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restQuestionsFrequencyPerClientTrackMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingQuestionsFrequencyPerClientTrack() throws Exception {
        // Get the questionsFrequencyPerClientTrack
        restQuestionsFrequencyPerClientTrackMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingQuestionsFrequencyPerClientTrack() throws Exception {
        // Initialize the database
        questionsFrequencyPerClientTrackRepository.saveAndFlush(questionsFrequencyPerClientTrack);

        int databaseSizeBeforeUpdate = questionsFrequencyPerClientTrackRepository.findAll().size();

        // Update the questionsFrequencyPerClientTrack
        QuestionsFrequencyPerClientTrack updatedQuestionsFrequencyPerClientTrack = questionsFrequencyPerClientTrackRepository
            .findById(questionsFrequencyPerClientTrack.getId())
            .get();
        // Disconnect from session so that the updates on updatedQuestionsFrequencyPerClientTrack are not directly saved in db
        em.detach(updatedQuestionsFrequencyPerClientTrack);
        updatedQuestionsFrequencyPerClientTrack
            .question(UPDATED_QUESTION)
            .frequency(UPDATED_FREQUENCY)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restQuestionsFrequencyPerClientTrackMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedQuestionsFrequencyPerClientTrack.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedQuestionsFrequencyPerClientTrack))
            )
            .andExpect(status().isOk());

        // Validate the QuestionsFrequencyPerClientTrack in the database
        List<QuestionsFrequencyPerClientTrack> questionsFrequencyPerClientTrackList = questionsFrequencyPerClientTrackRepository.findAll();
        assertThat(questionsFrequencyPerClientTrackList).hasSize(databaseSizeBeforeUpdate);
        QuestionsFrequencyPerClientTrack testQuestionsFrequencyPerClientTrack = questionsFrequencyPerClientTrackList.get(
            questionsFrequencyPerClientTrackList.size() - 1
        );
        assertThat(testQuestionsFrequencyPerClientTrack.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testQuestionsFrequencyPerClientTrack.getFrequency()).isEqualTo(UPDATED_FREQUENCY);
        assertThat(testQuestionsFrequencyPerClientTrack.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testQuestionsFrequencyPerClientTrack.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingQuestionsFrequencyPerClientTrack() throws Exception {
        int databaseSizeBeforeUpdate = questionsFrequencyPerClientTrackRepository.findAll().size();
        questionsFrequencyPerClientTrack.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionsFrequencyPerClientTrackMockMvc
            .perform(
                put(ENTITY_API_URL_ID, questionsFrequencyPerClientTrack.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionsFrequencyPerClientTrack))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuestionsFrequencyPerClientTrack in the database
        List<QuestionsFrequencyPerClientTrack> questionsFrequencyPerClientTrackList = questionsFrequencyPerClientTrackRepository.findAll();
        assertThat(questionsFrequencyPerClientTrackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQuestionsFrequencyPerClientTrack() throws Exception {
        int databaseSizeBeforeUpdate = questionsFrequencyPerClientTrackRepository.findAll().size();
        questionsFrequencyPerClientTrack.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionsFrequencyPerClientTrackMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionsFrequencyPerClientTrack))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuestionsFrequencyPerClientTrack in the database
        List<QuestionsFrequencyPerClientTrack> questionsFrequencyPerClientTrackList = questionsFrequencyPerClientTrackRepository.findAll();
        assertThat(questionsFrequencyPerClientTrackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQuestionsFrequencyPerClientTrack() throws Exception {
        int databaseSizeBeforeUpdate = questionsFrequencyPerClientTrackRepository.findAll().size();
        questionsFrequencyPerClientTrack.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionsFrequencyPerClientTrackMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionsFrequencyPerClientTrack))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QuestionsFrequencyPerClientTrack in the database
        List<QuestionsFrequencyPerClientTrack> questionsFrequencyPerClientTrackList = questionsFrequencyPerClientTrackRepository.findAll();
        assertThat(questionsFrequencyPerClientTrackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQuestionsFrequencyPerClientTrackWithPatch() throws Exception {
        // Initialize the database
        questionsFrequencyPerClientTrackRepository.saveAndFlush(questionsFrequencyPerClientTrack);

        int databaseSizeBeforeUpdate = questionsFrequencyPerClientTrackRepository.findAll().size();

        // Update the questionsFrequencyPerClientTrack using partial update
        QuestionsFrequencyPerClientTrack partialUpdatedQuestionsFrequencyPerClientTrack = new QuestionsFrequencyPerClientTrack();
        partialUpdatedQuestionsFrequencyPerClientTrack.setId(questionsFrequencyPerClientTrack.getId());

        partialUpdatedQuestionsFrequencyPerClientTrack
            .question(UPDATED_QUESTION)
            .frequency(UPDATED_FREQUENCY)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restQuestionsFrequencyPerClientTrackMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuestionsFrequencyPerClientTrack.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuestionsFrequencyPerClientTrack))
            )
            .andExpect(status().isOk());

        // Validate the QuestionsFrequencyPerClientTrack in the database
        List<QuestionsFrequencyPerClientTrack> questionsFrequencyPerClientTrackList = questionsFrequencyPerClientTrackRepository.findAll();
        assertThat(questionsFrequencyPerClientTrackList).hasSize(databaseSizeBeforeUpdate);
        QuestionsFrequencyPerClientTrack testQuestionsFrequencyPerClientTrack = questionsFrequencyPerClientTrackList.get(
            questionsFrequencyPerClientTrackList.size() - 1
        );
        assertThat(testQuestionsFrequencyPerClientTrack.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testQuestionsFrequencyPerClientTrack.getFrequency()).isEqualTo(UPDATED_FREQUENCY);
        assertThat(testQuestionsFrequencyPerClientTrack.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testQuestionsFrequencyPerClientTrack.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateQuestionsFrequencyPerClientTrackWithPatch() throws Exception {
        // Initialize the database
        questionsFrequencyPerClientTrackRepository.saveAndFlush(questionsFrequencyPerClientTrack);

        int databaseSizeBeforeUpdate = questionsFrequencyPerClientTrackRepository.findAll().size();

        // Update the questionsFrequencyPerClientTrack using partial update
        QuestionsFrequencyPerClientTrack partialUpdatedQuestionsFrequencyPerClientTrack = new QuestionsFrequencyPerClientTrack();
        partialUpdatedQuestionsFrequencyPerClientTrack.setId(questionsFrequencyPerClientTrack.getId());

        partialUpdatedQuestionsFrequencyPerClientTrack
            .question(UPDATED_QUESTION)
            .frequency(UPDATED_FREQUENCY)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restQuestionsFrequencyPerClientTrackMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuestionsFrequencyPerClientTrack.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuestionsFrequencyPerClientTrack))
            )
            .andExpect(status().isOk());

        // Validate the QuestionsFrequencyPerClientTrack in the database
        List<QuestionsFrequencyPerClientTrack> questionsFrequencyPerClientTrackList = questionsFrequencyPerClientTrackRepository.findAll();
        assertThat(questionsFrequencyPerClientTrackList).hasSize(databaseSizeBeforeUpdate);
        QuestionsFrequencyPerClientTrack testQuestionsFrequencyPerClientTrack = questionsFrequencyPerClientTrackList.get(
            questionsFrequencyPerClientTrackList.size() - 1
        );
        assertThat(testQuestionsFrequencyPerClientTrack.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testQuestionsFrequencyPerClientTrack.getFrequency()).isEqualTo(UPDATED_FREQUENCY);
        assertThat(testQuestionsFrequencyPerClientTrack.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testQuestionsFrequencyPerClientTrack.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingQuestionsFrequencyPerClientTrack() throws Exception {
        int databaseSizeBeforeUpdate = questionsFrequencyPerClientTrackRepository.findAll().size();
        questionsFrequencyPerClientTrack.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionsFrequencyPerClientTrackMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, questionsFrequencyPerClientTrack.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(questionsFrequencyPerClientTrack))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuestionsFrequencyPerClientTrack in the database
        List<QuestionsFrequencyPerClientTrack> questionsFrequencyPerClientTrackList = questionsFrequencyPerClientTrackRepository.findAll();
        assertThat(questionsFrequencyPerClientTrackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQuestionsFrequencyPerClientTrack() throws Exception {
        int databaseSizeBeforeUpdate = questionsFrequencyPerClientTrackRepository.findAll().size();
        questionsFrequencyPerClientTrack.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionsFrequencyPerClientTrackMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(questionsFrequencyPerClientTrack))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuestionsFrequencyPerClientTrack in the database
        List<QuestionsFrequencyPerClientTrack> questionsFrequencyPerClientTrackList = questionsFrequencyPerClientTrackRepository.findAll();
        assertThat(questionsFrequencyPerClientTrackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQuestionsFrequencyPerClientTrack() throws Exception {
        int databaseSizeBeforeUpdate = questionsFrequencyPerClientTrackRepository.findAll().size();
        questionsFrequencyPerClientTrack.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionsFrequencyPerClientTrackMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(questionsFrequencyPerClientTrack))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QuestionsFrequencyPerClientTrack in the database
        List<QuestionsFrequencyPerClientTrack> questionsFrequencyPerClientTrackList = questionsFrequencyPerClientTrackRepository.findAll();
        assertThat(questionsFrequencyPerClientTrackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQuestionsFrequencyPerClientTrack() throws Exception {
        // Initialize the database
        questionsFrequencyPerClientTrackRepository.saveAndFlush(questionsFrequencyPerClientTrack);

        int databaseSizeBeforeDelete = questionsFrequencyPerClientTrackRepository.findAll().size();

        // Delete the questionsFrequencyPerClientTrack
        restQuestionsFrequencyPerClientTrackMockMvc
            .perform(delete(ENTITY_API_URL_ID, questionsFrequencyPerClientTrack.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QuestionsFrequencyPerClientTrack> questionsFrequencyPerClientTrackList = questionsFrequencyPerClientTrackRepository.findAll();
        assertThat(questionsFrequencyPerClientTrackList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
