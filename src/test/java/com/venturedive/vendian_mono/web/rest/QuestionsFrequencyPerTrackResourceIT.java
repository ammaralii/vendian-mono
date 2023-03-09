package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.QuestionsFrequencyPerTrack;
import com.venturedive.vendian_mono.domain.Tracks;
import com.venturedive.vendian_mono.repository.QuestionsFrequencyPerTrackRepository;
import com.venturedive.vendian_mono.service.criteria.QuestionsFrequencyPerTrackCriteria;
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
 * Integration tests for the {@link QuestionsFrequencyPerTrackResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QuestionsFrequencyPerTrackResourceIT {

    private static final String DEFAULT_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_FREQUENCY = 1;
    private static final Integer UPDATED_FREQUENCY = 2;
    private static final Integer SMALLER_FREQUENCY = 1 - 1;

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/questions-frequency-per-tracks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QuestionsFrequencyPerTrackRepository questionsFrequencyPerTrackRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuestionsFrequencyPerTrackMockMvc;

    private QuestionsFrequencyPerTrack questionsFrequencyPerTrack;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionsFrequencyPerTrack createEntity(EntityManager em) {
        QuestionsFrequencyPerTrack questionsFrequencyPerTrack = new QuestionsFrequencyPerTrack()
            .question(DEFAULT_QUESTION)
            .frequency(DEFAULT_FREQUENCY)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        // Add required entity
        Tracks tracks;
        if (TestUtil.findAll(em, Tracks.class).isEmpty()) {
            tracks = TracksResourceIT.createEntity(em);
            em.persist(tracks);
            em.flush();
        } else {
            tracks = TestUtil.findAll(em, Tracks.class).get(0);
        }
        questionsFrequencyPerTrack.setTrack(tracks);
        return questionsFrequencyPerTrack;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionsFrequencyPerTrack createUpdatedEntity(EntityManager em) {
        QuestionsFrequencyPerTrack questionsFrequencyPerTrack = new QuestionsFrequencyPerTrack()
            .question(UPDATED_QUESTION)
            .frequency(UPDATED_FREQUENCY)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        // Add required entity
        Tracks tracks;
        if (TestUtil.findAll(em, Tracks.class).isEmpty()) {
            tracks = TracksResourceIT.createUpdatedEntity(em);
            em.persist(tracks);
            em.flush();
        } else {
            tracks = TestUtil.findAll(em, Tracks.class).get(0);
        }
        questionsFrequencyPerTrack.setTrack(tracks);
        return questionsFrequencyPerTrack;
    }

    @BeforeEach
    public void initTest() {
        questionsFrequencyPerTrack = createEntity(em);
    }

    @Test
    @Transactional
    void createQuestionsFrequencyPerTrack() throws Exception {
        int databaseSizeBeforeCreate = questionsFrequencyPerTrackRepository.findAll().size();
        // Create the QuestionsFrequencyPerTrack
        restQuestionsFrequencyPerTrackMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionsFrequencyPerTrack))
            )
            .andExpect(status().isCreated());

        // Validate the QuestionsFrequencyPerTrack in the database
        List<QuestionsFrequencyPerTrack> questionsFrequencyPerTrackList = questionsFrequencyPerTrackRepository.findAll();
        assertThat(questionsFrequencyPerTrackList).hasSize(databaseSizeBeforeCreate + 1);
        QuestionsFrequencyPerTrack testQuestionsFrequencyPerTrack = questionsFrequencyPerTrackList.get(
            questionsFrequencyPerTrackList.size() - 1
        );
        assertThat(testQuestionsFrequencyPerTrack.getQuestion()).isEqualTo(DEFAULT_QUESTION);
        assertThat(testQuestionsFrequencyPerTrack.getFrequency()).isEqualTo(DEFAULT_FREQUENCY);
        assertThat(testQuestionsFrequencyPerTrack.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testQuestionsFrequencyPerTrack.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createQuestionsFrequencyPerTrackWithExistingId() throws Exception {
        // Create the QuestionsFrequencyPerTrack with an existing ID
        questionsFrequencyPerTrack.setId(1L);

        int databaseSizeBeforeCreate = questionsFrequencyPerTrackRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionsFrequencyPerTrackMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionsFrequencyPerTrack))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuestionsFrequencyPerTrack in the database
        List<QuestionsFrequencyPerTrack> questionsFrequencyPerTrackList = questionsFrequencyPerTrackRepository.findAll();
        assertThat(questionsFrequencyPerTrackList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkQuestionIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionsFrequencyPerTrackRepository.findAll().size();
        // set the field null
        questionsFrequencyPerTrack.setQuestion(null);

        // Create the QuestionsFrequencyPerTrack, which fails.

        restQuestionsFrequencyPerTrackMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionsFrequencyPerTrack))
            )
            .andExpect(status().isBadRequest());

        List<QuestionsFrequencyPerTrack> questionsFrequencyPerTrackList = questionsFrequencyPerTrackRepository.findAll();
        assertThat(questionsFrequencyPerTrackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFrequencyIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionsFrequencyPerTrackRepository.findAll().size();
        // set the field null
        questionsFrequencyPerTrack.setFrequency(null);

        // Create the QuestionsFrequencyPerTrack, which fails.

        restQuestionsFrequencyPerTrackMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionsFrequencyPerTrack))
            )
            .andExpect(status().isBadRequest());

        List<QuestionsFrequencyPerTrack> questionsFrequencyPerTrackList = questionsFrequencyPerTrackRepository.findAll();
        assertThat(questionsFrequencyPerTrackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionsFrequencyPerTrackRepository.findAll().size();
        // set the field null
        questionsFrequencyPerTrack.setCreatedat(null);

        // Create the QuestionsFrequencyPerTrack, which fails.

        restQuestionsFrequencyPerTrackMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionsFrequencyPerTrack))
            )
            .andExpect(status().isBadRequest());

        List<QuestionsFrequencyPerTrack> questionsFrequencyPerTrackList = questionsFrequencyPerTrackRepository.findAll();
        assertThat(questionsFrequencyPerTrackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionsFrequencyPerTrackRepository.findAll().size();
        // set the field null
        questionsFrequencyPerTrack.setUpdatedat(null);

        // Create the QuestionsFrequencyPerTrack, which fails.

        restQuestionsFrequencyPerTrackMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionsFrequencyPerTrack))
            )
            .andExpect(status().isBadRequest());

        List<QuestionsFrequencyPerTrack> questionsFrequencyPerTrackList = questionsFrequencyPerTrackRepository.findAll();
        assertThat(questionsFrequencyPerTrackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerTracks() throws Exception {
        // Initialize the database
        questionsFrequencyPerTrackRepository.saveAndFlush(questionsFrequencyPerTrack);

        // Get all the questionsFrequencyPerTrackList
        restQuestionsFrequencyPerTrackMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionsFrequencyPerTrack.getId().intValue())))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION)))
            .andExpect(jsonPath("$.[*].frequency").value(hasItem(DEFAULT_FREQUENCY)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getQuestionsFrequencyPerTrack() throws Exception {
        // Initialize the database
        questionsFrequencyPerTrackRepository.saveAndFlush(questionsFrequencyPerTrack);

        // Get the questionsFrequencyPerTrack
        restQuestionsFrequencyPerTrackMockMvc
            .perform(get(ENTITY_API_URL_ID, questionsFrequencyPerTrack.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(questionsFrequencyPerTrack.getId().intValue()))
            .andExpect(jsonPath("$.question").value(DEFAULT_QUESTION))
            .andExpect(jsonPath("$.frequency").value(DEFAULT_FREQUENCY))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getQuestionsFrequencyPerTracksByIdFiltering() throws Exception {
        // Initialize the database
        questionsFrequencyPerTrackRepository.saveAndFlush(questionsFrequencyPerTrack);

        Long id = questionsFrequencyPerTrack.getId();

        defaultQuestionsFrequencyPerTrackShouldBeFound("id.equals=" + id);
        defaultQuestionsFrequencyPerTrackShouldNotBeFound("id.notEquals=" + id);

        defaultQuestionsFrequencyPerTrackShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultQuestionsFrequencyPerTrackShouldNotBeFound("id.greaterThan=" + id);

        defaultQuestionsFrequencyPerTrackShouldBeFound("id.lessThanOrEqual=" + id);
        defaultQuestionsFrequencyPerTrackShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerTracksByQuestionIsEqualToSomething() throws Exception {
        // Initialize the database
        questionsFrequencyPerTrackRepository.saveAndFlush(questionsFrequencyPerTrack);

        // Get all the questionsFrequencyPerTrackList where question equals to DEFAULT_QUESTION
        defaultQuestionsFrequencyPerTrackShouldBeFound("question.equals=" + DEFAULT_QUESTION);

        // Get all the questionsFrequencyPerTrackList where question equals to UPDATED_QUESTION
        defaultQuestionsFrequencyPerTrackShouldNotBeFound("question.equals=" + UPDATED_QUESTION);
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerTracksByQuestionIsInShouldWork() throws Exception {
        // Initialize the database
        questionsFrequencyPerTrackRepository.saveAndFlush(questionsFrequencyPerTrack);

        // Get all the questionsFrequencyPerTrackList where question in DEFAULT_QUESTION or UPDATED_QUESTION
        defaultQuestionsFrequencyPerTrackShouldBeFound("question.in=" + DEFAULT_QUESTION + "," + UPDATED_QUESTION);

        // Get all the questionsFrequencyPerTrackList where question equals to UPDATED_QUESTION
        defaultQuestionsFrequencyPerTrackShouldNotBeFound("question.in=" + UPDATED_QUESTION);
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerTracksByQuestionIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionsFrequencyPerTrackRepository.saveAndFlush(questionsFrequencyPerTrack);

        // Get all the questionsFrequencyPerTrackList where question is not null
        defaultQuestionsFrequencyPerTrackShouldBeFound("question.specified=true");

        // Get all the questionsFrequencyPerTrackList where question is null
        defaultQuestionsFrequencyPerTrackShouldNotBeFound("question.specified=false");
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerTracksByQuestionContainsSomething() throws Exception {
        // Initialize the database
        questionsFrequencyPerTrackRepository.saveAndFlush(questionsFrequencyPerTrack);

        // Get all the questionsFrequencyPerTrackList where question contains DEFAULT_QUESTION
        defaultQuestionsFrequencyPerTrackShouldBeFound("question.contains=" + DEFAULT_QUESTION);

        // Get all the questionsFrequencyPerTrackList where question contains UPDATED_QUESTION
        defaultQuestionsFrequencyPerTrackShouldNotBeFound("question.contains=" + UPDATED_QUESTION);
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerTracksByQuestionNotContainsSomething() throws Exception {
        // Initialize the database
        questionsFrequencyPerTrackRepository.saveAndFlush(questionsFrequencyPerTrack);

        // Get all the questionsFrequencyPerTrackList where question does not contain DEFAULT_QUESTION
        defaultQuestionsFrequencyPerTrackShouldNotBeFound("question.doesNotContain=" + DEFAULT_QUESTION);

        // Get all the questionsFrequencyPerTrackList where question does not contain UPDATED_QUESTION
        defaultQuestionsFrequencyPerTrackShouldBeFound("question.doesNotContain=" + UPDATED_QUESTION);
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerTracksByFrequencyIsEqualToSomething() throws Exception {
        // Initialize the database
        questionsFrequencyPerTrackRepository.saveAndFlush(questionsFrequencyPerTrack);

        // Get all the questionsFrequencyPerTrackList where frequency equals to DEFAULT_FREQUENCY
        defaultQuestionsFrequencyPerTrackShouldBeFound("frequency.equals=" + DEFAULT_FREQUENCY);

        // Get all the questionsFrequencyPerTrackList where frequency equals to UPDATED_FREQUENCY
        defaultQuestionsFrequencyPerTrackShouldNotBeFound("frequency.equals=" + UPDATED_FREQUENCY);
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerTracksByFrequencyIsInShouldWork() throws Exception {
        // Initialize the database
        questionsFrequencyPerTrackRepository.saveAndFlush(questionsFrequencyPerTrack);

        // Get all the questionsFrequencyPerTrackList where frequency in DEFAULT_FREQUENCY or UPDATED_FREQUENCY
        defaultQuestionsFrequencyPerTrackShouldBeFound("frequency.in=" + DEFAULT_FREQUENCY + "," + UPDATED_FREQUENCY);

        // Get all the questionsFrequencyPerTrackList where frequency equals to UPDATED_FREQUENCY
        defaultQuestionsFrequencyPerTrackShouldNotBeFound("frequency.in=" + UPDATED_FREQUENCY);
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerTracksByFrequencyIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionsFrequencyPerTrackRepository.saveAndFlush(questionsFrequencyPerTrack);

        // Get all the questionsFrequencyPerTrackList where frequency is not null
        defaultQuestionsFrequencyPerTrackShouldBeFound("frequency.specified=true");

        // Get all the questionsFrequencyPerTrackList where frequency is null
        defaultQuestionsFrequencyPerTrackShouldNotBeFound("frequency.specified=false");
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerTracksByFrequencyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        questionsFrequencyPerTrackRepository.saveAndFlush(questionsFrequencyPerTrack);

        // Get all the questionsFrequencyPerTrackList where frequency is greater than or equal to DEFAULT_FREQUENCY
        defaultQuestionsFrequencyPerTrackShouldBeFound("frequency.greaterThanOrEqual=" + DEFAULT_FREQUENCY);

        // Get all the questionsFrequencyPerTrackList where frequency is greater than or equal to UPDATED_FREQUENCY
        defaultQuestionsFrequencyPerTrackShouldNotBeFound("frequency.greaterThanOrEqual=" + UPDATED_FREQUENCY);
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerTracksByFrequencyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        questionsFrequencyPerTrackRepository.saveAndFlush(questionsFrequencyPerTrack);

        // Get all the questionsFrequencyPerTrackList where frequency is less than or equal to DEFAULT_FREQUENCY
        defaultQuestionsFrequencyPerTrackShouldBeFound("frequency.lessThanOrEqual=" + DEFAULT_FREQUENCY);

        // Get all the questionsFrequencyPerTrackList where frequency is less than or equal to SMALLER_FREQUENCY
        defaultQuestionsFrequencyPerTrackShouldNotBeFound("frequency.lessThanOrEqual=" + SMALLER_FREQUENCY);
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerTracksByFrequencyIsLessThanSomething() throws Exception {
        // Initialize the database
        questionsFrequencyPerTrackRepository.saveAndFlush(questionsFrequencyPerTrack);

        // Get all the questionsFrequencyPerTrackList where frequency is less than DEFAULT_FREQUENCY
        defaultQuestionsFrequencyPerTrackShouldNotBeFound("frequency.lessThan=" + DEFAULT_FREQUENCY);

        // Get all the questionsFrequencyPerTrackList where frequency is less than UPDATED_FREQUENCY
        defaultQuestionsFrequencyPerTrackShouldBeFound("frequency.lessThan=" + UPDATED_FREQUENCY);
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerTracksByFrequencyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        questionsFrequencyPerTrackRepository.saveAndFlush(questionsFrequencyPerTrack);

        // Get all the questionsFrequencyPerTrackList where frequency is greater than DEFAULT_FREQUENCY
        defaultQuestionsFrequencyPerTrackShouldNotBeFound("frequency.greaterThan=" + DEFAULT_FREQUENCY);

        // Get all the questionsFrequencyPerTrackList where frequency is greater than SMALLER_FREQUENCY
        defaultQuestionsFrequencyPerTrackShouldBeFound("frequency.greaterThan=" + SMALLER_FREQUENCY);
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerTracksByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        questionsFrequencyPerTrackRepository.saveAndFlush(questionsFrequencyPerTrack);

        // Get all the questionsFrequencyPerTrackList where createdat equals to DEFAULT_CREATEDAT
        defaultQuestionsFrequencyPerTrackShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the questionsFrequencyPerTrackList where createdat equals to UPDATED_CREATEDAT
        defaultQuestionsFrequencyPerTrackShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerTracksByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        questionsFrequencyPerTrackRepository.saveAndFlush(questionsFrequencyPerTrack);

        // Get all the questionsFrequencyPerTrackList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultQuestionsFrequencyPerTrackShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the questionsFrequencyPerTrackList where createdat equals to UPDATED_CREATEDAT
        defaultQuestionsFrequencyPerTrackShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerTracksByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionsFrequencyPerTrackRepository.saveAndFlush(questionsFrequencyPerTrack);

        // Get all the questionsFrequencyPerTrackList where createdat is not null
        defaultQuestionsFrequencyPerTrackShouldBeFound("createdat.specified=true");

        // Get all the questionsFrequencyPerTrackList where createdat is null
        defaultQuestionsFrequencyPerTrackShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerTracksByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        questionsFrequencyPerTrackRepository.saveAndFlush(questionsFrequencyPerTrack);

        // Get all the questionsFrequencyPerTrackList where updatedat equals to DEFAULT_UPDATEDAT
        defaultQuestionsFrequencyPerTrackShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the questionsFrequencyPerTrackList where updatedat equals to UPDATED_UPDATEDAT
        defaultQuestionsFrequencyPerTrackShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerTracksByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        questionsFrequencyPerTrackRepository.saveAndFlush(questionsFrequencyPerTrack);

        // Get all the questionsFrequencyPerTrackList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultQuestionsFrequencyPerTrackShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the questionsFrequencyPerTrackList where updatedat equals to UPDATED_UPDATEDAT
        defaultQuestionsFrequencyPerTrackShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerTracksByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionsFrequencyPerTrackRepository.saveAndFlush(questionsFrequencyPerTrack);

        // Get all the questionsFrequencyPerTrackList where updatedat is not null
        defaultQuestionsFrequencyPerTrackShouldBeFound("updatedat.specified=true");

        // Get all the questionsFrequencyPerTrackList where updatedat is null
        defaultQuestionsFrequencyPerTrackShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllQuestionsFrequencyPerTracksByTrackIsEqualToSomething() throws Exception {
        Tracks track;
        if (TestUtil.findAll(em, Tracks.class).isEmpty()) {
            questionsFrequencyPerTrackRepository.saveAndFlush(questionsFrequencyPerTrack);
            track = TracksResourceIT.createEntity(em);
        } else {
            track = TestUtil.findAll(em, Tracks.class).get(0);
        }
        em.persist(track);
        em.flush();
        questionsFrequencyPerTrack.setTrack(track);
        questionsFrequencyPerTrackRepository.saveAndFlush(questionsFrequencyPerTrack);
        Long trackId = track.getId();

        // Get all the questionsFrequencyPerTrackList where track equals to trackId
        defaultQuestionsFrequencyPerTrackShouldBeFound("trackId.equals=" + trackId);

        // Get all the questionsFrequencyPerTrackList where track equals to (trackId + 1)
        defaultQuestionsFrequencyPerTrackShouldNotBeFound("trackId.equals=" + (trackId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultQuestionsFrequencyPerTrackShouldBeFound(String filter) throws Exception {
        restQuestionsFrequencyPerTrackMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionsFrequencyPerTrack.getId().intValue())))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION)))
            .andExpect(jsonPath("$.[*].frequency").value(hasItem(DEFAULT_FREQUENCY)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restQuestionsFrequencyPerTrackMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultQuestionsFrequencyPerTrackShouldNotBeFound(String filter) throws Exception {
        restQuestionsFrequencyPerTrackMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restQuestionsFrequencyPerTrackMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingQuestionsFrequencyPerTrack() throws Exception {
        // Get the questionsFrequencyPerTrack
        restQuestionsFrequencyPerTrackMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingQuestionsFrequencyPerTrack() throws Exception {
        // Initialize the database
        questionsFrequencyPerTrackRepository.saveAndFlush(questionsFrequencyPerTrack);

        int databaseSizeBeforeUpdate = questionsFrequencyPerTrackRepository.findAll().size();

        // Update the questionsFrequencyPerTrack
        QuestionsFrequencyPerTrack updatedQuestionsFrequencyPerTrack = questionsFrequencyPerTrackRepository
            .findById(questionsFrequencyPerTrack.getId())
            .get();
        // Disconnect from session so that the updates on updatedQuestionsFrequencyPerTrack are not directly saved in db
        em.detach(updatedQuestionsFrequencyPerTrack);
        updatedQuestionsFrequencyPerTrack
            .question(UPDATED_QUESTION)
            .frequency(UPDATED_FREQUENCY)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restQuestionsFrequencyPerTrackMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedQuestionsFrequencyPerTrack.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedQuestionsFrequencyPerTrack))
            )
            .andExpect(status().isOk());

        // Validate the QuestionsFrequencyPerTrack in the database
        List<QuestionsFrequencyPerTrack> questionsFrequencyPerTrackList = questionsFrequencyPerTrackRepository.findAll();
        assertThat(questionsFrequencyPerTrackList).hasSize(databaseSizeBeforeUpdate);
        QuestionsFrequencyPerTrack testQuestionsFrequencyPerTrack = questionsFrequencyPerTrackList.get(
            questionsFrequencyPerTrackList.size() - 1
        );
        assertThat(testQuestionsFrequencyPerTrack.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testQuestionsFrequencyPerTrack.getFrequency()).isEqualTo(UPDATED_FREQUENCY);
        assertThat(testQuestionsFrequencyPerTrack.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testQuestionsFrequencyPerTrack.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingQuestionsFrequencyPerTrack() throws Exception {
        int databaseSizeBeforeUpdate = questionsFrequencyPerTrackRepository.findAll().size();
        questionsFrequencyPerTrack.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionsFrequencyPerTrackMockMvc
            .perform(
                put(ENTITY_API_URL_ID, questionsFrequencyPerTrack.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionsFrequencyPerTrack))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuestionsFrequencyPerTrack in the database
        List<QuestionsFrequencyPerTrack> questionsFrequencyPerTrackList = questionsFrequencyPerTrackRepository.findAll();
        assertThat(questionsFrequencyPerTrackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQuestionsFrequencyPerTrack() throws Exception {
        int databaseSizeBeforeUpdate = questionsFrequencyPerTrackRepository.findAll().size();
        questionsFrequencyPerTrack.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionsFrequencyPerTrackMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionsFrequencyPerTrack))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuestionsFrequencyPerTrack in the database
        List<QuestionsFrequencyPerTrack> questionsFrequencyPerTrackList = questionsFrequencyPerTrackRepository.findAll();
        assertThat(questionsFrequencyPerTrackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQuestionsFrequencyPerTrack() throws Exception {
        int databaseSizeBeforeUpdate = questionsFrequencyPerTrackRepository.findAll().size();
        questionsFrequencyPerTrack.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionsFrequencyPerTrackMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionsFrequencyPerTrack))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QuestionsFrequencyPerTrack in the database
        List<QuestionsFrequencyPerTrack> questionsFrequencyPerTrackList = questionsFrequencyPerTrackRepository.findAll();
        assertThat(questionsFrequencyPerTrackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQuestionsFrequencyPerTrackWithPatch() throws Exception {
        // Initialize the database
        questionsFrequencyPerTrackRepository.saveAndFlush(questionsFrequencyPerTrack);

        int databaseSizeBeforeUpdate = questionsFrequencyPerTrackRepository.findAll().size();

        // Update the questionsFrequencyPerTrack using partial update
        QuestionsFrequencyPerTrack partialUpdatedQuestionsFrequencyPerTrack = new QuestionsFrequencyPerTrack();
        partialUpdatedQuestionsFrequencyPerTrack.setId(questionsFrequencyPerTrack.getId());

        partialUpdatedQuestionsFrequencyPerTrack.frequency(UPDATED_FREQUENCY).createdat(UPDATED_CREATEDAT);

        restQuestionsFrequencyPerTrackMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuestionsFrequencyPerTrack.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuestionsFrequencyPerTrack))
            )
            .andExpect(status().isOk());

        // Validate the QuestionsFrequencyPerTrack in the database
        List<QuestionsFrequencyPerTrack> questionsFrequencyPerTrackList = questionsFrequencyPerTrackRepository.findAll();
        assertThat(questionsFrequencyPerTrackList).hasSize(databaseSizeBeforeUpdate);
        QuestionsFrequencyPerTrack testQuestionsFrequencyPerTrack = questionsFrequencyPerTrackList.get(
            questionsFrequencyPerTrackList.size() - 1
        );
        assertThat(testQuestionsFrequencyPerTrack.getQuestion()).isEqualTo(DEFAULT_QUESTION);
        assertThat(testQuestionsFrequencyPerTrack.getFrequency()).isEqualTo(UPDATED_FREQUENCY);
        assertThat(testQuestionsFrequencyPerTrack.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testQuestionsFrequencyPerTrack.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateQuestionsFrequencyPerTrackWithPatch() throws Exception {
        // Initialize the database
        questionsFrequencyPerTrackRepository.saveAndFlush(questionsFrequencyPerTrack);

        int databaseSizeBeforeUpdate = questionsFrequencyPerTrackRepository.findAll().size();

        // Update the questionsFrequencyPerTrack using partial update
        QuestionsFrequencyPerTrack partialUpdatedQuestionsFrequencyPerTrack = new QuestionsFrequencyPerTrack();
        partialUpdatedQuestionsFrequencyPerTrack.setId(questionsFrequencyPerTrack.getId());

        partialUpdatedQuestionsFrequencyPerTrack
            .question(UPDATED_QUESTION)
            .frequency(UPDATED_FREQUENCY)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restQuestionsFrequencyPerTrackMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuestionsFrequencyPerTrack.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuestionsFrequencyPerTrack))
            )
            .andExpect(status().isOk());

        // Validate the QuestionsFrequencyPerTrack in the database
        List<QuestionsFrequencyPerTrack> questionsFrequencyPerTrackList = questionsFrequencyPerTrackRepository.findAll();
        assertThat(questionsFrequencyPerTrackList).hasSize(databaseSizeBeforeUpdate);
        QuestionsFrequencyPerTrack testQuestionsFrequencyPerTrack = questionsFrequencyPerTrackList.get(
            questionsFrequencyPerTrackList.size() - 1
        );
        assertThat(testQuestionsFrequencyPerTrack.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testQuestionsFrequencyPerTrack.getFrequency()).isEqualTo(UPDATED_FREQUENCY);
        assertThat(testQuestionsFrequencyPerTrack.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testQuestionsFrequencyPerTrack.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingQuestionsFrequencyPerTrack() throws Exception {
        int databaseSizeBeforeUpdate = questionsFrequencyPerTrackRepository.findAll().size();
        questionsFrequencyPerTrack.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionsFrequencyPerTrackMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, questionsFrequencyPerTrack.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(questionsFrequencyPerTrack))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuestionsFrequencyPerTrack in the database
        List<QuestionsFrequencyPerTrack> questionsFrequencyPerTrackList = questionsFrequencyPerTrackRepository.findAll();
        assertThat(questionsFrequencyPerTrackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQuestionsFrequencyPerTrack() throws Exception {
        int databaseSizeBeforeUpdate = questionsFrequencyPerTrackRepository.findAll().size();
        questionsFrequencyPerTrack.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionsFrequencyPerTrackMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(questionsFrequencyPerTrack))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuestionsFrequencyPerTrack in the database
        List<QuestionsFrequencyPerTrack> questionsFrequencyPerTrackList = questionsFrequencyPerTrackRepository.findAll();
        assertThat(questionsFrequencyPerTrackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQuestionsFrequencyPerTrack() throws Exception {
        int databaseSizeBeforeUpdate = questionsFrequencyPerTrackRepository.findAll().size();
        questionsFrequencyPerTrack.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionsFrequencyPerTrackMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(questionsFrequencyPerTrack))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QuestionsFrequencyPerTrack in the database
        List<QuestionsFrequencyPerTrack> questionsFrequencyPerTrackList = questionsFrequencyPerTrackRepository.findAll();
        assertThat(questionsFrequencyPerTrackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQuestionsFrequencyPerTrack() throws Exception {
        // Initialize the database
        questionsFrequencyPerTrackRepository.saveAndFlush(questionsFrequencyPerTrack);

        int databaseSizeBeforeDelete = questionsFrequencyPerTrackRepository.findAll().size();

        // Delete the questionsFrequencyPerTrack
        restQuestionsFrequencyPerTrackMockMvc
            .perform(delete(ENTITY_API_URL_ID, questionsFrequencyPerTrack.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QuestionsFrequencyPerTrack> questionsFrequencyPerTrackList = questionsFrequencyPerTrackRepository.findAll();
        assertThat(questionsFrequencyPerTrackList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
