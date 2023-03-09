package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Competencies;
import com.venturedive.vendian_mono.domain.Interviews;
import com.venturedive.vendian_mono.domain.Questions;
import com.venturedive.vendian_mono.domain.QuestionsFrequencyPerClientTrack;
import com.venturedive.vendian_mono.domain.QuestionsFrequencyPerTrack;
import com.venturedive.vendian_mono.domain.Tracks;
import com.venturedive.vendian_mono.repository.TracksRepository;
import com.venturedive.vendian_mono.service.criteria.TracksCriteria;
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
 * Integration tests for the {@link TracksResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TracksResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/tracks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TracksRepository tracksRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTracksMockMvc;

    private Tracks tracks;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tracks createEntity(EntityManager em) {
        Tracks tracks = new Tracks()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT)
            .deletedat(DEFAULT_DELETEDAT);
        return tracks;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tracks createUpdatedEntity(EntityManager em) {
        Tracks tracks = new Tracks()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .deletedat(UPDATED_DELETEDAT);
        return tracks;
    }

    @BeforeEach
    public void initTest() {
        tracks = createEntity(em);
    }

    @Test
    @Transactional
    void createTracks() throws Exception {
        int databaseSizeBeforeCreate = tracksRepository.findAll().size();
        // Create the Tracks
        restTracksMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tracks))
            )
            .andExpect(status().isCreated());

        // Validate the Tracks in the database
        List<Tracks> tracksList = tracksRepository.findAll();
        assertThat(tracksList).hasSize(databaseSizeBeforeCreate + 1);
        Tracks testTracks = tracksList.get(tracksList.size() - 1);
        assertThat(testTracks.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTracks.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTracks.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testTracks.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testTracks.getDeletedat()).isEqualTo(DEFAULT_DELETEDAT);
    }

    @Test
    @Transactional
    void createTracksWithExistingId() throws Exception {
        // Create the Tracks with an existing ID
        tracks.setId(1L);

        int databaseSizeBeforeCreate = tracksRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTracksMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tracks))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tracks in the database
        List<Tracks> tracksList = tracksRepository.findAll();
        assertThat(tracksList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = tracksRepository.findAll().size();
        // set the field null
        tracks.setCreatedat(null);

        // Create the Tracks, which fails.

        restTracksMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tracks))
            )
            .andExpect(status().isBadRequest());

        List<Tracks> tracksList = tracksRepository.findAll();
        assertThat(tracksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = tracksRepository.findAll().size();
        // set the field null
        tracks.setUpdatedat(null);

        // Create the Tracks, which fails.

        restTracksMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tracks))
            )
            .andExpect(status().isBadRequest());

        List<Tracks> tracksList = tracksRepository.findAll();
        assertThat(tracksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTracks() throws Exception {
        // Initialize the database
        tracksRepository.saveAndFlush(tracks);

        // Get all the tracksList
        restTracksMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tracks.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].deletedat").value(hasItem(DEFAULT_DELETEDAT.toString())));
    }

    @Test
    @Transactional
    void getTracks() throws Exception {
        // Initialize the database
        tracksRepository.saveAndFlush(tracks);

        // Get the tracks
        restTracksMockMvc
            .perform(get(ENTITY_API_URL_ID, tracks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tracks.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()))
            .andExpect(jsonPath("$.deletedat").value(DEFAULT_DELETEDAT.toString()));
    }

    @Test
    @Transactional
    void getTracksByIdFiltering() throws Exception {
        // Initialize the database
        tracksRepository.saveAndFlush(tracks);

        Long id = tracks.getId();

        defaultTracksShouldBeFound("id.equals=" + id);
        defaultTracksShouldNotBeFound("id.notEquals=" + id);

        defaultTracksShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTracksShouldNotBeFound("id.greaterThan=" + id);

        defaultTracksShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTracksShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTracksByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        tracksRepository.saveAndFlush(tracks);

        // Get all the tracksList where name equals to DEFAULT_NAME
        defaultTracksShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the tracksList where name equals to UPDATED_NAME
        defaultTracksShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTracksByNameIsInShouldWork() throws Exception {
        // Initialize the database
        tracksRepository.saveAndFlush(tracks);

        // Get all the tracksList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTracksShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the tracksList where name equals to UPDATED_NAME
        defaultTracksShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTracksByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        tracksRepository.saveAndFlush(tracks);

        // Get all the tracksList where name is not null
        defaultTracksShouldBeFound("name.specified=true");

        // Get all the tracksList where name is null
        defaultTracksShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllTracksByNameContainsSomething() throws Exception {
        // Initialize the database
        tracksRepository.saveAndFlush(tracks);

        // Get all the tracksList where name contains DEFAULT_NAME
        defaultTracksShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the tracksList where name contains UPDATED_NAME
        defaultTracksShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTracksByNameNotContainsSomething() throws Exception {
        // Initialize the database
        tracksRepository.saveAndFlush(tracks);

        // Get all the tracksList where name does not contain DEFAULT_NAME
        defaultTracksShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the tracksList where name does not contain UPDATED_NAME
        defaultTracksShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTracksByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        tracksRepository.saveAndFlush(tracks);

        // Get all the tracksList where description equals to DEFAULT_DESCRIPTION
        defaultTracksShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the tracksList where description equals to UPDATED_DESCRIPTION
        defaultTracksShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllTracksByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        tracksRepository.saveAndFlush(tracks);

        // Get all the tracksList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultTracksShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the tracksList where description equals to UPDATED_DESCRIPTION
        defaultTracksShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllTracksByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        tracksRepository.saveAndFlush(tracks);

        // Get all the tracksList where description is not null
        defaultTracksShouldBeFound("description.specified=true");

        // Get all the tracksList where description is null
        defaultTracksShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllTracksByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        tracksRepository.saveAndFlush(tracks);

        // Get all the tracksList where description contains DEFAULT_DESCRIPTION
        defaultTracksShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the tracksList where description contains UPDATED_DESCRIPTION
        defaultTracksShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllTracksByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        tracksRepository.saveAndFlush(tracks);

        // Get all the tracksList where description does not contain DEFAULT_DESCRIPTION
        defaultTracksShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the tracksList where description does not contain UPDATED_DESCRIPTION
        defaultTracksShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllTracksByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        tracksRepository.saveAndFlush(tracks);

        // Get all the tracksList where createdat equals to DEFAULT_CREATEDAT
        defaultTracksShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the tracksList where createdat equals to UPDATED_CREATEDAT
        defaultTracksShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllTracksByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        tracksRepository.saveAndFlush(tracks);

        // Get all the tracksList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultTracksShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the tracksList where createdat equals to UPDATED_CREATEDAT
        defaultTracksShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllTracksByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        tracksRepository.saveAndFlush(tracks);

        // Get all the tracksList where createdat is not null
        defaultTracksShouldBeFound("createdat.specified=true");

        // Get all the tracksList where createdat is null
        defaultTracksShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllTracksByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        tracksRepository.saveAndFlush(tracks);

        // Get all the tracksList where updatedat equals to DEFAULT_UPDATEDAT
        defaultTracksShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the tracksList where updatedat equals to UPDATED_UPDATEDAT
        defaultTracksShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllTracksByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        tracksRepository.saveAndFlush(tracks);

        // Get all the tracksList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultTracksShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the tracksList where updatedat equals to UPDATED_UPDATEDAT
        defaultTracksShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllTracksByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        tracksRepository.saveAndFlush(tracks);

        // Get all the tracksList where updatedat is not null
        defaultTracksShouldBeFound("updatedat.specified=true");

        // Get all the tracksList where updatedat is null
        defaultTracksShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllTracksByDeletedatIsEqualToSomething() throws Exception {
        // Initialize the database
        tracksRepository.saveAndFlush(tracks);

        // Get all the tracksList where deletedat equals to DEFAULT_DELETEDAT
        defaultTracksShouldBeFound("deletedat.equals=" + DEFAULT_DELETEDAT);

        // Get all the tracksList where deletedat equals to UPDATED_DELETEDAT
        defaultTracksShouldNotBeFound("deletedat.equals=" + UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void getAllTracksByDeletedatIsInShouldWork() throws Exception {
        // Initialize the database
        tracksRepository.saveAndFlush(tracks);

        // Get all the tracksList where deletedat in DEFAULT_DELETEDAT or UPDATED_DELETEDAT
        defaultTracksShouldBeFound("deletedat.in=" + DEFAULT_DELETEDAT + "," + UPDATED_DELETEDAT);

        // Get all the tracksList where deletedat equals to UPDATED_DELETEDAT
        defaultTracksShouldNotBeFound("deletedat.in=" + UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void getAllTracksByDeletedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        tracksRepository.saveAndFlush(tracks);

        // Get all the tracksList where deletedat is not null
        defaultTracksShouldBeFound("deletedat.specified=true");

        // Get all the tracksList where deletedat is null
        defaultTracksShouldNotBeFound("deletedat.specified=false");
    }

    @Test
    @Transactional
    void getAllTracksByCompetencyIsEqualToSomething() throws Exception {
        Competencies competency;
        if (TestUtil.findAll(em, Competencies.class).isEmpty()) {
            tracksRepository.saveAndFlush(tracks);
            competency = CompetenciesResourceIT.createEntity(em);
        } else {
            competency = TestUtil.findAll(em, Competencies.class).get(0);
        }
        em.persist(competency);
        em.flush();
        tracks.setCompetency(competency);
        tracksRepository.saveAndFlush(tracks);
        Long competencyId = competency.getId();

        // Get all the tracksList where competency equals to competencyId
        defaultTracksShouldBeFound("competencyId.equals=" + competencyId);

        // Get all the tracksList where competency equals to (competencyId + 1)
        defaultTracksShouldNotBeFound("competencyId.equals=" + (competencyId + 1));
    }

    @Test
    @Transactional
    void getAllTracksByInterviewsTrackIsEqualToSomething() throws Exception {
        Interviews interviewsTrack;
        if (TestUtil.findAll(em, Interviews.class).isEmpty()) {
            tracksRepository.saveAndFlush(tracks);
            interviewsTrack = InterviewsResourceIT.createEntity(em);
        } else {
            interviewsTrack = TestUtil.findAll(em, Interviews.class).get(0);
        }
        em.persist(interviewsTrack);
        em.flush();
        tracks.addInterviewsTrack(interviewsTrack);
        tracksRepository.saveAndFlush(tracks);
        Long interviewsTrackId = interviewsTrack.getId();

        // Get all the tracksList where interviewsTrack equals to interviewsTrackId
        defaultTracksShouldBeFound("interviewsTrackId.equals=" + interviewsTrackId);

        // Get all the tracksList where interviewsTrack equals to (interviewsTrackId + 1)
        defaultTracksShouldNotBeFound("interviewsTrackId.equals=" + (interviewsTrackId + 1));
    }

    @Test
    @Transactional
    void getAllTracksByQuestionsTrackIsEqualToSomething() throws Exception {
        Questions questionsTrack;
        if (TestUtil.findAll(em, Questions.class).isEmpty()) {
            tracksRepository.saveAndFlush(tracks);
            questionsTrack = QuestionsResourceIT.createEntity(em);
        } else {
            questionsTrack = TestUtil.findAll(em, Questions.class).get(0);
        }
        em.persist(questionsTrack);
        em.flush();
        tracks.addQuestionsTrack(questionsTrack);
        tracksRepository.saveAndFlush(tracks);
        Long questionsTrackId = questionsTrack.getId();

        // Get all the tracksList where questionsTrack equals to questionsTrackId
        defaultTracksShouldBeFound("questionsTrackId.equals=" + questionsTrackId);

        // Get all the tracksList where questionsTrack equals to (questionsTrackId + 1)
        defaultTracksShouldNotBeFound("questionsTrackId.equals=" + (questionsTrackId + 1));
    }

    @Test
    @Transactional
    void getAllTracksByQuestionsfrequencyperclienttrackTrackIsEqualToSomething() throws Exception {
        QuestionsFrequencyPerClientTrack questionsfrequencyperclienttrackTrack;
        if (TestUtil.findAll(em, QuestionsFrequencyPerClientTrack.class).isEmpty()) {
            tracksRepository.saveAndFlush(tracks);
            questionsfrequencyperclienttrackTrack = QuestionsFrequencyPerClientTrackResourceIT.createEntity(em);
        } else {
            questionsfrequencyperclienttrackTrack = TestUtil.findAll(em, QuestionsFrequencyPerClientTrack.class).get(0);
        }
        em.persist(questionsfrequencyperclienttrackTrack);
        em.flush();
        tracks.addQuestionsfrequencyperclienttrackTrack(questionsfrequencyperclienttrackTrack);
        tracksRepository.saveAndFlush(tracks);
        Long questionsfrequencyperclienttrackTrackId = questionsfrequencyperclienttrackTrack.getId();

        // Get all the tracksList where questionsfrequencyperclienttrackTrack equals to questionsfrequencyperclienttrackTrackId
        defaultTracksShouldBeFound("questionsfrequencyperclienttrackTrackId.equals=" + questionsfrequencyperclienttrackTrackId);

        // Get all the tracksList where questionsfrequencyperclienttrackTrack equals to (questionsfrequencyperclienttrackTrackId + 1)
        defaultTracksShouldNotBeFound("questionsfrequencyperclienttrackTrackId.equals=" + (questionsfrequencyperclienttrackTrackId + 1));
    }

    @Test
    @Transactional
    void getAllTracksByQuestionsfrequencypertrackTrackIsEqualToSomething() throws Exception {
        QuestionsFrequencyPerTrack questionsfrequencypertrackTrack;
        if (TestUtil.findAll(em, QuestionsFrequencyPerTrack.class).isEmpty()) {
            tracksRepository.saveAndFlush(tracks);
            questionsfrequencypertrackTrack = QuestionsFrequencyPerTrackResourceIT.createEntity(em);
        } else {
            questionsfrequencypertrackTrack = TestUtil.findAll(em, QuestionsFrequencyPerTrack.class).get(0);
        }
        em.persist(questionsfrequencypertrackTrack);
        em.flush();
        tracks.addQuestionsfrequencypertrackTrack(questionsfrequencypertrackTrack);
        tracksRepository.saveAndFlush(tracks);
        Long questionsfrequencypertrackTrackId = questionsfrequencypertrackTrack.getId();

        // Get all the tracksList where questionsfrequencypertrackTrack equals to questionsfrequencypertrackTrackId
        defaultTracksShouldBeFound("questionsfrequencypertrackTrackId.equals=" + questionsfrequencypertrackTrackId);

        // Get all the tracksList where questionsfrequencypertrackTrack equals to (questionsfrequencypertrackTrackId + 1)
        defaultTracksShouldNotBeFound("questionsfrequencypertrackTrackId.equals=" + (questionsfrequencypertrackTrackId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTracksShouldBeFound(String filter) throws Exception {
        restTracksMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tracks.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].deletedat").value(hasItem(DEFAULT_DELETEDAT.toString())));

        // Check, that the count call also returns 1
        restTracksMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTracksShouldNotBeFound(String filter) throws Exception {
        restTracksMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTracksMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTracks() throws Exception {
        // Get the tracks
        restTracksMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTracks() throws Exception {
        // Initialize the database
        tracksRepository.saveAndFlush(tracks);

        int databaseSizeBeforeUpdate = tracksRepository.findAll().size();

        // Update the tracks
        Tracks updatedTracks = tracksRepository.findById(tracks.getId()).get();
        // Disconnect from session so that the updates on updatedTracks are not directly saved in db
        em.detach(updatedTracks);
        updatedTracks
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .deletedat(UPDATED_DELETEDAT);

        restTracksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTracks.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTracks))
            )
            .andExpect(status().isOk());

        // Validate the Tracks in the database
        List<Tracks> tracksList = tracksRepository.findAll();
        assertThat(tracksList).hasSize(databaseSizeBeforeUpdate);
        Tracks testTracks = tracksList.get(tracksList.size() - 1);
        assertThat(testTracks.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTracks.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTracks.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testTracks.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testTracks.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void putNonExistingTracks() throws Exception {
        int databaseSizeBeforeUpdate = tracksRepository.findAll().size();
        tracks.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTracksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tracks.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tracks))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tracks in the database
        List<Tracks> tracksList = tracksRepository.findAll();
        assertThat(tracksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTracks() throws Exception {
        int databaseSizeBeforeUpdate = tracksRepository.findAll().size();
        tracks.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTracksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tracks))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tracks in the database
        List<Tracks> tracksList = tracksRepository.findAll();
        assertThat(tracksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTracks() throws Exception {
        int databaseSizeBeforeUpdate = tracksRepository.findAll().size();
        tracks.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTracksMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tracks))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tracks in the database
        List<Tracks> tracksList = tracksRepository.findAll();
        assertThat(tracksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTracksWithPatch() throws Exception {
        // Initialize the database
        tracksRepository.saveAndFlush(tracks);

        int databaseSizeBeforeUpdate = tracksRepository.findAll().size();

        // Update the tracks using partial update
        Tracks partialUpdatedTracks = new Tracks();
        partialUpdatedTracks.setId(tracks.getId());

        partialUpdatedTracks.name(UPDATED_NAME).deletedat(UPDATED_DELETEDAT);

        restTracksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTracks.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTracks))
            )
            .andExpect(status().isOk());

        // Validate the Tracks in the database
        List<Tracks> tracksList = tracksRepository.findAll();
        assertThat(tracksList).hasSize(databaseSizeBeforeUpdate);
        Tracks testTracks = tracksList.get(tracksList.size() - 1);
        assertThat(testTracks.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTracks.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTracks.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testTracks.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testTracks.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void fullUpdateTracksWithPatch() throws Exception {
        // Initialize the database
        tracksRepository.saveAndFlush(tracks);

        int databaseSizeBeforeUpdate = tracksRepository.findAll().size();

        // Update the tracks using partial update
        Tracks partialUpdatedTracks = new Tracks();
        partialUpdatedTracks.setId(tracks.getId());

        partialUpdatedTracks
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .deletedat(UPDATED_DELETEDAT);

        restTracksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTracks.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTracks))
            )
            .andExpect(status().isOk());

        // Validate the Tracks in the database
        List<Tracks> tracksList = tracksRepository.findAll();
        assertThat(tracksList).hasSize(databaseSizeBeforeUpdate);
        Tracks testTracks = tracksList.get(tracksList.size() - 1);
        assertThat(testTracks.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTracks.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTracks.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testTracks.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testTracks.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingTracks() throws Exception {
        int databaseSizeBeforeUpdate = tracksRepository.findAll().size();
        tracks.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTracksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tracks.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tracks))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tracks in the database
        List<Tracks> tracksList = tracksRepository.findAll();
        assertThat(tracksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTracks() throws Exception {
        int databaseSizeBeforeUpdate = tracksRepository.findAll().size();
        tracks.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTracksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tracks))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tracks in the database
        List<Tracks> tracksList = tracksRepository.findAll();
        assertThat(tracksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTracks() throws Exception {
        int databaseSizeBeforeUpdate = tracksRepository.findAll().size();
        tracks.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTracksMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tracks))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tracks in the database
        List<Tracks> tracksList = tracksRepository.findAll();
        assertThat(tracksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTracks() throws Exception {
        // Initialize the database
        tracksRepository.saveAndFlush(tracks);

        int databaseSizeBeforeDelete = tracksRepository.findAll().size();

        // Delete the tracks
        restTracksMockMvc
            .perform(delete(ENTITY_API_URL_ID, tracks.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tracks> tracksList = tracksRepository.findAll();
        assertThat(tracksList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
