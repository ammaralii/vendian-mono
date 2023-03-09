package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Interviews;
import com.venturedive.vendian_mono.domain.Projects;
import com.venturedive.vendian_mono.domain.Questions;
import com.venturedive.vendian_mono.domain.Tracks;
import com.venturedive.vendian_mono.repository.QuestionsRepository;
import com.venturedive.vendian_mono.service.criteria.QuestionsCriteria;
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
 * Integration tests for the {@link QuestionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QuestionsResourceIT {

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_ANSWER = "AAAAAAAAAA";
    private static final String UPDATED_ANSWER = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CLEANEDUPTEXT = "AAAAAAAAAA";
    private static final String UPDATED_CLEANEDUPTEXT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/questions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QuestionsRepository questionsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuestionsMockMvc;

    private Questions questions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Questions createEntity(EntityManager em) {
        Questions questions = new Questions()
            .text(DEFAULT_TEXT)
            .answer(DEFAULT_ANSWER)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT)
            .deletedat(DEFAULT_DELETEDAT)
            .cleaneduptext(DEFAULT_CLEANEDUPTEXT);
        return questions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Questions createUpdatedEntity(EntityManager em) {
        Questions questions = new Questions()
            .text(UPDATED_TEXT)
            .answer(UPDATED_ANSWER)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .deletedat(UPDATED_DELETEDAT)
            .cleaneduptext(UPDATED_CLEANEDUPTEXT);
        return questions;
    }

    @BeforeEach
    public void initTest() {
        questions = createEntity(em);
    }

    @Test
    @Transactional
    void createQuestions() throws Exception {
        int databaseSizeBeforeCreate = questionsRepository.findAll().size();
        // Create the Questions
        restQuestionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questions))
            )
            .andExpect(status().isCreated());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeCreate + 1);
        Questions testQuestions = questionsList.get(questionsList.size() - 1);
        assertThat(testQuestions.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testQuestions.getAnswer()).isEqualTo(DEFAULT_ANSWER);
        assertThat(testQuestions.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testQuestions.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testQuestions.getDeletedat()).isEqualTo(DEFAULT_DELETEDAT);
        assertThat(testQuestions.getCleaneduptext()).isEqualTo(DEFAULT_CLEANEDUPTEXT);
    }

    @Test
    @Transactional
    void createQuestionsWithExistingId() throws Exception {
        // Create the Questions with an existing ID
        questions.setId(1L);

        int databaseSizeBeforeCreate = questionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionsRepository.findAll().size();
        // set the field null
        questions.setCreatedat(null);

        // Create the Questions, which fails.

        restQuestionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questions))
            )
            .andExpect(status().isBadRequest());

        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionsRepository.findAll().size();
        // set the field null
        questions.setUpdatedat(null);

        // Create the Questions, which fails.

        restQuestionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questions))
            )
            .andExpect(status().isBadRequest());

        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllQuestions() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList
        restQuestionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questions.getId().intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].answer").value(hasItem(DEFAULT_ANSWER)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].deletedat").value(hasItem(DEFAULT_DELETEDAT.toString())))
            .andExpect(jsonPath("$.[*].cleaneduptext").value(hasItem(DEFAULT_CLEANEDUPTEXT)));
    }

    @Test
    @Transactional
    void getQuestions() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get the questions
        restQuestionsMockMvc
            .perform(get(ENTITY_API_URL_ID, questions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(questions.getId().intValue()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT))
            .andExpect(jsonPath("$.answer").value(DEFAULT_ANSWER))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()))
            .andExpect(jsonPath("$.deletedat").value(DEFAULT_DELETEDAT.toString()))
            .andExpect(jsonPath("$.cleaneduptext").value(DEFAULT_CLEANEDUPTEXT));
    }

    @Test
    @Transactional
    void getQuestionsByIdFiltering() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        Long id = questions.getId();

        defaultQuestionsShouldBeFound("id.equals=" + id);
        defaultQuestionsShouldNotBeFound("id.notEquals=" + id);

        defaultQuestionsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultQuestionsShouldNotBeFound("id.greaterThan=" + id);

        defaultQuestionsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultQuestionsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllQuestionsByTextIsEqualToSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where text equals to DEFAULT_TEXT
        defaultQuestionsShouldBeFound("text.equals=" + DEFAULT_TEXT);

        // Get all the questionsList where text equals to UPDATED_TEXT
        defaultQuestionsShouldNotBeFound("text.equals=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    void getAllQuestionsByTextIsInShouldWork() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where text in DEFAULT_TEXT or UPDATED_TEXT
        defaultQuestionsShouldBeFound("text.in=" + DEFAULT_TEXT + "," + UPDATED_TEXT);

        // Get all the questionsList where text equals to UPDATED_TEXT
        defaultQuestionsShouldNotBeFound("text.in=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    void getAllQuestionsByTextIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where text is not null
        defaultQuestionsShouldBeFound("text.specified=true");

        // Get all the questionsList where text is null
        defaultQuestionsShouldNotBeFound("text.specified=false");
    }

    @Test
    @Transactional
    void getAllQuestionsByTextContainsSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where text contains DEFAULT_TEXT
        defaultQuestionsShouldBeFound("text.contains=" + DEFAULT_TEXT);

        // Get all the questionsList where text contains UPDATED_TEXT
        defaultQuestionsShouldNotBeFound("text.contains=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    void getAllQuestionsByTextNotContainsSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where text does not contain DEFAULT_TEXT
        defaultQuestionsShouldNotBeFound("text.doesNotContain=" + DEFAULT_TEXT);

        // Get all the questionsList where text does not contain UPDATED_TEXT
        defaultQuestionsShouldBeFound("text.doesNotContain=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    void getAllQuestionsByAnswerIsEqualToSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where answer equals to DEFAULT_ANSWER
        defaultQuestionsShouldBeFound("answer.equals=" + DEFAULT_ANSWER);

        // Get all the questionsList where answer equals to UPDATED_ANSWER
        defaultQuestionsShouldNotBeFound("answer.equals=" + UPDATED_ANSWER);
    }

    @Test
    @Transactional
    void getAllQuestionsByAnswerIsInShouldWork() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where answer in DEFAULT_ANSWER or UPDATED_ANSWER
        defaultQuestionsShouldBeFound("answer.in=" + DEFAULT_ANSWER + "," + UPDATED_ANSWER);

        // Get all the questionsList where answer equals to UPDATED_ANSWER
        defaultQuestionsShouldNotBeFound("answer.in=" + UPDATED_ANSWER);
    }

    @Test
    @Transactional
    void getAllQuestionsByAnswerIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where answer is not null
        defaultQuestionsShouldBeFound("answer.specified=true");

        // Get all the questionsList where answer is null
        defaultQuestionsShouldNotBeFound("answer.specified=false");
    }

    @Test
    @Transactional
    void getAllQuestionsByAnswerContainsSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where answer contains DEFAULT_ANSWER
        defaultQuestionsShouldBeFound("answer.contains=" + DEFAULT_ANSWER);

        // Get all the questionsList where answer contains UPDATED_ANSWER
        defaultQuestionsShouldNotBeFound("answer.contains=" + UPDATED_ANSWER);
    }

    @Test
    @Transactional
    void getAllQuestionsByAnswerNotContainsSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where answer does not contain DEFAULT_ANSWER
        defaultQuestionsShouldNotBeFound("answer.doesNotContain=" + DEFAULT_ANSWER);

        // Get all the questionsList where answer does not contain UPDATED_ANSWER
        defaultQuestionsShouldBeFound("answer.doesNotContain=" + UPDATED_ANSWER);
    }

    @Test
    @Transactional
    void getAllQuestionsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where createdat equals to DEFAULT_CREATEDAT
        defaultQuestionsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the questionsList where createdat equals to UPDATED_CREATEDAT
        defaultQuestionsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllQuestionsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultQuestionsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the questionsList where createdat equals to UPDATED_CREATEDAT
        defaultQuestionsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllQuestionsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where createdat is not null
        defaultQuestionsShouldBeFound("createdat.specified=true");

        // Get all the questionsList where createdat is null
        defaultQuestionsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllQuestionsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultQuestionsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the questionsList where updatedat equals to UPDATED_UPDATEDAT
        defaultQuestionsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllQuestionsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultQuestionsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the questionsList where updatedat equals to UPDATED_UPDATEDAT
        defaultQuestionsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllQuestionsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where updatedat is not null
        defaultQuestionsShouldBeFound("updatedat.specified=true");

        // Get all the questionsList where updatedat is null
        defaultQuestionsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllQuestionsByDeletedatIsEqualToSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where deletedat equals to DEFAULT_DELETEDAT
        defaultQuestionsShouldBeFound("deletedat.equals=" + DEFAULT_DELETEDAT);

        // Get all the questionsList where deletedat equals to UPDATED_DELETEDAT
        defaultQuestionsShouldNotBeFound("deletedat.equals=" + UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void getAllQuestionsByDeletedatIsInShouldWork() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where deletedat in DEFAULT_DELETEDAT or UPDATED_DELETEDAT
        defaultQuestionsShouldBeFound("deletedat.in=" + DEFAULT_DELETEDAT + "," + UPDATED_DELETEDAT);

        // Get all the questionsList where deletedat equals to UPDATED_DELETEDAT
        defaultQuestionsShouldNotBeFound("deletedat.in=" + UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void getAllQuestionsByDeletedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where deletedat is not null
        defaultQuestionsShouldBeFound("deletedat.specified=true");

        // Get all the questionsList where deletedat is null
        defaultQuestionsShouldNotBeFound("deletedat.specified=false");
    }

    @Test
    @Transactional
    void getAllQuestionsByCleaneduptextIsEqualToSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where cleaneduptext equals to DEFAULT_CLEANEDUPTEXT
        defaultQuestionsShouldBeFound("cleaneduptext.equals=" + DEFAULT_CLEANEDUPTEXT);

        // Get all the questionsList where cleaneduptext equals to UPDATED_CLEANEDUPTEXT
        defaultQuestionsShouldNotBeFound("cleaneduptext.equals=" + UPDATED_CLEANEDUPTEXT);
    }

    @Test
    @Transactional
    void getAllQuestionsByCleaneduptextIsInShouldWork() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where cleaneduptext in DEFAULT_CLEANEDUPTEXT or UPDATED_CLEANEDUPTEXT
        defaultQuestionsShouldBeFound("cleaneduptext.in=" + DEFAULT_CLEANEDUPTEXT + "," + UPDATED_CLEANEDUPTEXT);

        // Get all the questionsList where cleaneduptext equals to UPDATED_CLEANEDUPTEXT
        defaultQuestionsShouldNotBeFound("cleaneduptext.in=" + UPDATED_CLEANEDUPTEXT);
    }

    @Test
    @Transactional
    void getAllQuestionsByCleaneduptextIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where cleaneduptext is not null
        defaultQuestionsShouldBeFound("cleaneduptext.specified=true");

        // Get all the questionsList where cleaneduptext is null
        defaultQuestionsShouldNotBeFound("cleaneduptext.specified=false");
    }

    @Test
    @Transactional
    void getAllQuestionsByCleaneduptextContainsSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where cleaneduptext contains DEFAULT_CLEANEDUPTEXT
        defaultQuestionsShouldBeFound("cleaneduptext.contains=" + DEFAULT_CLEANEDUPTEXT);

        // Get all the questionsList where cleaneduptext contains UPDATED_CLEANEDUPTEXT
        defaultQuestionsShouldNotBeFound("cleaneduptext.contains=" + UPDATED_CLEANEDUPTEXT);
    }

    @Test
    @Transactional
    void getAllQuestionsByCleaneduptextNotContainsSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where cleaneduptext does not contain DEFAULT_CLEANEDUPTEXT
        defaultQuestionsShouldNotBeFound("cleaneduptext.doesNotContain=" + DEFAULT_CLEANEDUPTEXT);

        // Get all the questionsList where cleaneduptext does not contain UPDATED_CLEANEDUPTEXT
        defaultQuestionsShouldBeFound("cleaneduptext.doesNotContain=" + UPDATED_CLEANEDUPTEXT);
    }

    @Test
    @Transactional
    void getAllQuestionsByInterviewIsEqualToSomething() throws Exception {
        Interviews interview;
        if (TestUtil.findAll(em, Interviews.class).isEmpty()) {
            questionsRepository.saveAndFlush(questions);
            interview = InterviewsResourceIT.createEntity(em);
        } else {
            interview = TestUtil.findAll(em, Interviews.class).get(0);
        }
        em.persist(interview);
        em.flush();
        questions.setInterview(interview);
        questionsRepository.saveAndFlush(questions);
        Long interviewId = interview.getId();

        // Get all the questionsList where interview equals to interviewId
        defaultQuestionsShouldBeFound("interviewId.equals=" + interviewId);

        // Get all the questionsList where interview equals to (interviewId + 1)
        defaultQuestionsShouldNotBeFound("interviewId.equals=" + (interviewId + 1));
    }

    @Test
    @Transactional
    void getAllQuestionsByProjectIsEqualToSomething() throws Exception {
        Projects project;
        if (TestUtil.findAll(em, Projects.class).isEmpty()) {
            questionsRepository.saveAndFlush(questions);
            project = ProjectsResourceIT.createEntity(em);
        } else {
            project = TestUtil.findAll(em, Projects.class).get(0);
        }
        em.persist(project);
        em.flush();
        questions.setProject(project);
        questionsRepository.saveAndFlush(questions);
        Long projectId = project.getId();

        // Get all the questionsList where project equals to projectId
        defaultQuestionsShouldBeFound("projectId.equals=" + projectId);

        // Get all the questionsList where project equals to (projectId + 1)
        defaultQuestionsShouldNotBeFound("projectId.equals=" + (projectId + 1));
    }

    @Test
    @Transactional
    void getAllQuestionsByTrackIsEqualToSomething() throws Exception {
        Tracks track;
        if (TestUtil.findAll(em, Tracks.class).isEmpty()) {
            questionsRepository.saveAndFlush(questions);
            track = TracksResourceIT.createEntity(em);
        } else {
            track = TestUtil.findAll(em, Tracks.class).get(0);
        }
        em.persist(track);
        em.flush();
        questions.setTrack(track);
        questionsRepository.saveAndFlush(questions);
        Long trackId = track.getId();

        // Get all the questionsList where track equals to trackId
        defaultQuestionsShouldBeFound("trackId.equals=" + trackId);

        // Get all the questionsList where track equals to (trackId + 1)
        defaultQuestionsShouldNotBeFound("trackId.equals=" + (trackId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultQuestionsShouldBeFound(String filter) throws Exception {
        restQuestionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questions.getId().intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].answer").value(hasItem(DEFAULT_ANSWER)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].deletedat").value(hasItem(DEFAULT_DELETEDAT.toString())))
            .andExpect(jsonPath("$.[*].cleaneduptext").value(hasItem(DEFAULT_CLEANEDUPTEXT)));

        // Check, that the count call also returns 1
        restQuestionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultQuestionsShouldNotBeFound(String filter) throws Exception {
        restQuestionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restQuestionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingQuestions() throws Exception {
        // Get the questions
        restQuestionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingQuestions() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        int databaseSizeBeforeUpdate = questionsRepository.findAll().size();

        // Update the questions
        Questions updatedQuestions = questionsRepository.findById(questions.getId()).get();
        // Disconnect from session so that the updates on updatedQuestions are not directly saved in db
        em.detach(updatedQuestions);
        updatedQuestions
            .text(UPDATED_TEXT)
            .answer(UPDATED_ANSWER)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .deletedat(UPDATED_DELETEDAT)
            .cleaneduptext(UPDATED_CLEANEDUPTEXT);

        restQuestionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedQuestions.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedQuestions))
            )
            .andExpect(status().isOk());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeUpdate);
        Questions testQuestions = questionsList.get(questionsList.size() - 1);
        assertThat(testQuestions.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testQuestions.getAnswer()).isEqualTo(UPDATED_ANSWER);
        assertThat(testQuestions.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testQuestions.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testQuestions.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
        assertThat(testQuestions.getCleaneduptext()).isEqualTo(UPDATED_CLEANEDUPTEXT);
    }

    @Test
    @Transactional
    void putNonExistingQuestions() throws Exception {
        int databaseSizeBeforeUpdate = questionsRepository.findAll().size();
        questions.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, questions.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQuestions() throws Exception {
        int databaseSizeBeforeUpdate = questionsRepository.findAll().size();
        questions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQuestions() throws Exception {
        int databaseSizeBeforeUpdate = questionsRepository.findAll().size();
        questions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questions))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQuestionsWithPatch() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        int databaseSizeBeforeUpdate = questionsRepository.findAll().size();

        // Update the questions using partial update
        Questions partialUpdatedQuestions = new Questions();
        partialUpdatedQuestions.setId(questions.getId());

        partialUpdatedQuestions
            .text(UPDATED_TEXT)
            .createdat(UPDATED_CREATEDAT)
            .deletedat(UPDATED_DELETEDAT)
            .cleaneduptext(UPDATED_CLEANEDUPTEXT);

        restQuestionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuestions.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuestions))
            )
            .andExpect(status().isOk());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeUpdate);
        Questions testQuestions = questionsList.get(questionsList.size() - 1);
        assertThat(testQuestions.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testQuestions.getAnswer()).isEqualTo(DEFAULT_ANSWER);
        assertThat(testQuestions.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testQuestions.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testQuestions.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
        assertThat(testQuestions.getCleaneduptext()).isEqualTo(UPDATED_CLEANEDUPTEXT);
    }

    @Test
    @Transactional
    void fullUpdateQuestionsWithPatch() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        int databaseSizeBeforeUpdate = questionsRepository.findAll().size();

        // Update the questions using partial update
        Questions partialUpdatedQuestions = new Questions();
        partialUpdatedQuestions.setId(questions.getId());

        partialUpdatedQuestions
            .text(UPDATED_TEXT)
            .answer(UPDATED_ANSWER)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .deletedat(UPDATED_DELETEDAT)
            .cleaneduptext(UPDATED_CLEANEDUPTEXT);

        restQuestionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuestions.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuestions))
            )
            .andExpect(status().isOk());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeUpdate);
        Questions testQuestions = questionsList.get(questionsList.size() - 1);
        assertThat(testQuestions.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testQuestions.getAnswer()).isEqualTo(UPDATED_ANSWER);
        assertThat(testQuestions.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testQuestions.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testQuestions.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
        assertThat(testQuestions.getCleaneduptext()).isEqualTo(UPDATED_CLEANEDUPTEXT);
    }

    @Test
    @Transactional
    void patchNonExistingQuestions() throws Exception {
        int databaseSizeBeforeUpdate = questionsRepository.findAll().size();
        questions.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, questions.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(questions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQuestions() throws Exception {
        int databaseSizeBeforeUpdate = questionsRepository.findAll().size();
        questions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(questions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQuestions() throws Exception {
        int databaseSizeBeforeUpdate = questionsRepository.findAll().size();
        questions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(questions))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQuestions() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        int databaseSizeBeforeDelete = questionsRepository.findAll().size();

        // Delete the questions
        restQuestionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, questions.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
