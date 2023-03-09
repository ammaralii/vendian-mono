package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.FeedbackQuestions;
import com.venturedive.vendian_mono.domain.FeedbackResponses;
import com.venturedive.vendian_mono.repository.FeedbackQuestionsRepository;
import com.venturedive.vendian_mono.service.criteria.FeedbackQuestionsCriteria;
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
 * Integration tests for the {@link FeedbackQuestionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FeedbackQuestionsResourceIT {

    private static final String DEFAULT_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ISDEFAULT = false;
    private static final Boolean UPDATED_ISDEFAULT = true;

    private static final String DEFAULT_AREA = "AAAAAAAAAA";
    private static final String UPDATED_AREA = "BBBBBBBBBB";

    private static final String DEFAULT_COMPETENCY = "AAAAAAAAAA";
    private static final String UPDATED_COMPETENCY = "BBBBBBBBBB";

    private static final Integer DEFAULT_CATEGORY = 1;
    private static final Integer UPDATED_CATEGORY = 2;
    private static final Integer SMALLER_CATEGORY = 1 - 1;

    private static final Boolean DEFAULT_ISSKILL = false;
    private static final Boolean UPDATED_ISSKILL = true;

    private static final Integer DEFAULT_SKILLTYPE = 1;
    private static final Integer UPDATED_SKILLTYPE = 2;
    private static final Integer SMALLER_SKILLTYPE = 1 - 1;

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/feedback-questions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FeedbackQuestionsRepository feedbackQuestionsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFeedbackQuestionsMockMvc;

    private FeedbackQuestions feedbackQuestions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FeedbackQuestions createEntity(EntityManager em) {
        FeedbackQuestions feedbackQuestions = new FeedbackQuestions()
            .question(DEFAULT_QUESTION)
            .isdefault(DEFAULT_ISDEFAULT)
            .area(DEFAULT_AREA)
            .competency(DEFAULT_COMPETENCY)
            .category(DEFAULT_CATEGORY)
            .isskill(DEFAULT_ISSKILL)
            .skilltype(DEFAULT_SKILLTYPE)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        return feedbackQuestions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FeedbackQuestions createUpdatedEntity(EntityManager em) {
        FeedbackQuestions feedbackQuestions = new FeedbackQuestions()
            .question(UPDATED_QUESTION)
            .isdefault(UPDATED_ISDEFAULT)
            .area(UPDATED_AREA)
            .competency(UPDATED_COMPETENCY)
            .category(UPDATED_CATEGORY)
            .isskill(UPDATED_ISSKILL)
            .skilltype(UPDATED_SKILLTYPE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        return feedbackQuestions;
    }

    @BeforeEach
    public void initTest() {
        feedbackQuestions = createEntity(em);
    }

    @Test
    @Transactional
    void createFeedbackQuestions() throws Exception {
        int databaseSizeBeforeCreate = feedbackQuestionsRepository.findAll().size();
        // Create the FeedbackQuestions
        restFeedbackQuestionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackQuestions))
            )
            .andExpect(status().isCreated());

        // Validate the FeedbackQuestions in the database
        List<FeedbackQuestions> feedbackQuestionsList = feedbackQuestionsRepository.findAll();
        assertThat(feedbackQuestionsList).hasSize(databaseSizeBeforeCreate + 1);
        FeedbackQuestions testFeedbackQuestions = feedbackQuestionsList.get(feedbackQuestionsList.size() - 1);
        assertThat(testFeedbackQuestions.getQuestion()).isEqualTo(DEFAULT_QUESTION);
        assertThat(testFeedbackQuestions.getIsdefault()).isEqualTo(DEFAULT_ISDEFAULT);
        assertThat(testFeedbackQuestions.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testFeedbackQuestions.getCompetency()).isEqualTo(DEFAULT_COMPETENCY);
        assertThat(testFeedbackQuestions.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testFeedbackQuestions.getIsskill()).isEqualTo(DEFAULT_ISSKILL);
        assertThat(testFeedbackQuestions.getSkilltype()).isEqualTo(DEFAULT_SKILLTYPE);
        assertThat(testFeedbackQuestions.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testFeedbackQuestions.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createFeedbackQuestionsWithExistingId() throws Exception {
        // Create the FeedbackQuestions with an existing ID
        feedbackQuestions.setId(1L);

        int databaseSizeBeforeCreate = feedbackQuestionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeedbackQuestionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackQuestions))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedbackQuestions in the database
        List<FeedbackQuestions> feedbackQuestionsList = feedbackQuestionsRepository.findAll();
        assertThat(feedbackQuestionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedbackQuestionsRepository.findAll().size();
        // set the field null
        feedbackQuestions.setCreatedat(null);

        // Create the FeedbackQuestions, which fails.

        restFeedbackQuestionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackQuestions))
            )
            .andExpect(status().isBadRequest());

        List<FeedbackQuestions> feedbackQuestionsList = feedbackQuestionsRepository.findAll();
        assertThat(feedbackQuestionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedbackQuestionsRepository.findAll().size();
        // set the field null
        feedbackQuestions.setUpdatedat(null);

        // Create the FeedbackQuestions, which fails.

        restFeedbackQuestionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackQuestions))
            )
            .andExpect(status().isBadRequest());

        List<FeedbackQuestions> feedbackQuestionsList = feedbackQuestionsRepository.findAll();
        assertThat(feedbackQuestionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFeedbackQuestions() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList
        restFeedbackQuestionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feedbackQuestions.getId().intValue())))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION)))
            .andExpect(jsonPath("$.[*].isdefault").value(hasItem(DEFAULT_ISDEFAULT.booleanValue())))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA)))
            .andExpect(jsonPath("$.[*].competency").value(hasItem(DEFAULT_COMPETENCY)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].isskill").value(hasItem(DEFAULT_ISSKILL.booleanValue())))
            .andExpect(jsonPath("$.[*].skilltype").value(hasItem(DEFAULT_SKILLTYPE)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getFeedbackQuestions() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get the feedbackQuestions
        restFeedbackQuestionsMockMvc
            .perform(get(ENTITY_API_URL_ID, feedbackQuestions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(feedbackQuestions.getId().intValue()))
            .andExpect(jsonPath("$.question").value(DEFAULT_QUESTION))
            .andExpect(jsonPath("$.isdefault").value(DEFAULT_ISDEFAULT.booleanValue()))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA))
            .andExpect(jsonPath("$.competency").value(DEFAULT_COMPETENCY))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
            .andExpect(jsonPath("$.isskill").value(DEFAULT_ISSKILL.booleanValue()))
            .andExpect(jsonPath("$.skilltype").value(DEFAULT_SKILLTYPE))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getFeedbackQuestionsByIdFiltering() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        Long id = feedbackQuestions.getId();

        defaultFeedbackQuestionsShouldBeFound("id.equals=" + id);
        defaultFeedbackQuestionsShouldNotBeFound("id.notEquals=" + id);

        defaultFeedbackQuestionsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFeedbackQuestionsShouldNotBeFound("id.greaterThan=" + id);

        defaultFeedbackQuestionsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFeedbackQuestionsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsByQuestionIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where question equals to DEFAULT_QUESTION
        defaultFeedbackQuestionsShouldBeFound("question.equals=" + DEFAULT_QUESTION);

        // Get all the feedbackQuestionsList where question equals to UPDATED_QUESTION
        defaultFeedbackQuestionsShouldNotBeFound("question.equals=" + UPDATED_QUESTION);
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsByQuestionIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where question in DEFAULT_QUESTION or UPDATED_QUESTION
        defaultFeedbackQuestionsShouldBeFound("question.in=" + DEFAULT_QUESTION + "," + UPDATED_QUESTION);

        // Get all the feedbackQuestionsList where question equals to UPDATED_QUESTION
        defaultFeedbackQuestionsShouldNotBeFound("question.in=" + UPDATED_QUESTION);
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsByQuestionIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where question is not null
        defaultFeedbackQuestionsShouldBeFound("question.specified=true");

        // Get all the feedbackQuestionsList where question is null
        defaultFeedbackQuestionsShouldNotBeFound("question.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsByQuestionContainsSomething() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where question contains DEFAULT_QUESTION
        defaultFeedbackQuestionsShouldBeFound("question.contains=" + DEFAULT_QUESTION);

        // Get all the feedbackQuestionsList where question contains UPDATED_QUESTION
        defaultFeedbackQuestionsShouldNotBeFound("question.contains=" + UPDATED_QUESTION);
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsByQuestionNotContainsSomething() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where question does not contain DEFAULT_QUESTION
        defaultFeedbackQuestionsShouldNotBeFound("question.doesNotContain=" + DEFAULT_QUESTION);

        // Get all the feedbackQuestionsList where question does not contain UPDATED_QUESTION
        defaultFeedbackQuestionsShouldBeFound("question.doesNotContain=" + UPDATED_QUESTION);
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsByIsdefaultIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where isdefault equals to DEFAULT_ISDEFAULT
        defaultFeedbackQuestionsShouldBeFound("isdefault.equals=" + DEFAULT_ISDEFAULT);

        // Get all the feedbackQuestionsList where isdefault equals to UPDATED_ISDEFAULT
        defaultFeedbackQuestionsShouldNotBeFound("isdefault.equals=" + UPDATED_ISDEFAULT);
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsByIsdefaultIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where isdefault in DEFAULT_ISDEFAULT or UPDATED_ISDEFAULT
        defaultFeedbackQuestionsShouldBeFound("isdefault.in=" + DEFAULT_ISDEFAULT + "," + UPDATED_ISDEFAULT);

        // Get all the feedbackQuestionsList where isdefault equals to UPDATED_ISDEFAULT
        defaultFeedbackQuestionsShouldNotBeFound("isdefault.in=" + UPDATED_ISDEFAULT);
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsByIsdefaultIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where isdefault is not null
        defaultFeedbackQuestionsShouldBeFound("isdefault.specified=true");

        // Get all the feedbackQuestionsList where isdefault is null
        defaultFeedbackQuestionsShouldNotBeFound("isdefault.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsByAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where area equals to DEFAULT_AREA
        defaultFeedbackQuestionsShouldBeFound("area.equals=" + DEFAULT_AREA);

        // Get all the feedbackQuestionsList where area equals to UPDATED_AREA
        defaultFeedbackQuestionsShouldNotBeFound("area.equals=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsByAreaIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where area in DEFAULT_AREA or UPDATED_AREA
        defaultFeedbackQuestionsShouldBeFound("area.in=" + DEFAULT_AREA + "," + UPDATED_AREA);

        // Get all the feedbackQuestionsList where area equals to UPDATED_AREA
        defaultFeedbackQuestionsShouldNotBeFound("area.in=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsByAreaIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where area is not null
        defaultFeedbackQuestionsShouldBeFound("area.specified=true");

        // Get all the feedbackQuestionsList where area is null
        defaultFeedbackQuestionsShouldNotBeFound("area.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsByAreaContainsSomething() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where area contains DEFAULT_AREA
        defaultFeedbackQuestionsShouldBeFound("area.contains=" + DEFAULT_AREA);

        // Get all the feedbackQuestionsList where area contains UPDATED_AREA
        defaultFeedbackQuestionsShouldNotBeFound("area.contains=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsByAreaNotContainsSomething() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where area does not contain DEFAULT_AREA
        defaultFeedbackQuestionsShouldNotBeFound("area.doesNotContain=" + DEFAULT_AREA);

        // Get all the feedbackQuestionsList where area does not contain UPDATED_AREA
        defaultFeedbackQuestionsShouldBeFound("area.doesNotContain=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsByCompetencyIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where competency equals to DEFAULT_COMPETENCY
        defaultFeedbackQuestionsShouldBeFound("competency.equals=" + DEFAULT_COMPETENCY);

        // Get all the feedbackQuestionsList where competency equals to UPDATED_COMPETENCY
        defaultFeedbackQuestionsShouldNotBeFound("competency.equals=" + UPDATED_COMPETENCY);
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsByCompetencyIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where competency in DEFAULT_COMPETENCY or UPDATED_COMPETENCY
        defaultFeedbackQuestionsShouldBeFound("competency.in=" + DEFAULT_COMPETENCY + "," + UPDATED_COMPETENCY);

        // Get all the feedbackQuestionsList where competency equals to UPDATED_COMPETENCY
        defaultFeedbackQuestionsShouldNotBeFound("competency.in=" + UPDATED_COMPETENCY);
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsByCompetencyIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where competency is not null
        defaultFeedbackQuestionsShouldBeFound("competency.specified=true");

        // Get all the feedbackQuestionsList where competency is null
        defaultFeedbackQuestionsShouldNotBeFound("competency.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsByCompetencyContainsSomething() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where competency contains DEFAULT_COMPETENCY
        defaultFeedbackQuestionsShouldBeFound("competency.contains=" + DEFAULT_COMPETENCY);

        // Get all the feedbackQuestionsList where competency contains UPDATED_COMPETENCY
        defaultFeedbackQuestionsShouldNotBeFound("competency.contains=" + UPDATED_COMPETENCY);
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsByCompetencyNotContainsSomething() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where competency does not contain DEFAULT_COMPETENCY
        defaultFeedbackQuestionsShouldNotBeFound("competency.doesNotContain=" + DEFAULT_COMPETENCY);

        // Get all the feedbackQuestionsList where competency does not contain UPDATED_COMPETENCY
        defaultFeedbackQuestionsShouldBeFound("competency.doesNotContain=" + UPDATED_COMPETENCY);
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where category equals to DEFAULT_CATEGORY
        defaultFeedbackQuestionsShouldBeFound("category.equals=" + DEFAULT_CATEGORY);

        // Get all the feedbackQuestionsList where category equals to UPDATED_CATEGORY
        defaultFeedbackQuestionsShouldNotBeFound("category.equals=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsByCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where category in DEFAULT_CATEGORY or UPDATED_CATEGORY
        defaultFeedbackQuestionsShouldBeFound("category.in=" + DEFAULT_CATEGORY + "," + UPDATED_CATEGORY);

        // Get all the feedbackQuestionsList where category equals to UPDATED_CATEGORY
        defaultFeedbackQuestionsShouldNotBeFound("category.in=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsByCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where category is not null
        defaultFeedbackQuestionsShouldBeFound("category.specified=true");

        // Get all the feedbackQuestionsList where category is null
        defaultFeedbackQuestionsShouldNotBeFound("category.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsByCategoryIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where category is greater than or equal to DEFAULT_CATEGORY
        defaultFeedbackQuestionsShouldBeFound("category.greaterThanOrEqual=" + DEFAULT_CATEGORY);

        // Get all the feedbackQuestionsList where category is greater than or equal to UPDATED_CATEGORY
        defaultFeedbackQuestionsShouldNotBeFound("category.greaterThanOrEqual=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsByCategoryIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where category is less than or equal to DEFAULT_CATEGORY
        defaultFeedbackQuestionsShouldBeFound("category.lessThanOrEqual=" + DEFAULT_CATEGORY);

        // Get all the feedbackQuestionsList where category is less than or equal to SMALLER_CATEGORY
        defaultFeedbackQuestionsShouldNotBeFound("category.lessThanOrEqual=" + SMALLER_CATEGORY);
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsByCategoryIsLessThanSomething() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where category is less than DEFAULT_CATEGORY
        defaultFeedbackQuestionsShouldNotBeFound("category.lessThan=" + DEFAULT_CATEGORY);

        // Get all the feedbackQuestionsList where category is less than UPDATED_CATEGORY
        defaultFeedbackQuestionsShouldBeFound("category.lessThan=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsByCategoryIsGreaterThanSomething() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where category is greater than DEFAULT_CATEGORY
        defaultFeedbackQuestionsShouldNotBeFound("category.greaterThan=" + DEFAULT_CATEGORY);

        // Get all the feedbackQuestionsList where category is greater than SMALLER_CATEGORY
        defaultFeedbackQuestionsShouldBeFound("category.greaterThan=" + SMALLER_CATEGORY);
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsByIsskillIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where isskill equals to DEFAULT_ISSKILL
        defaultFeedbackQuestionsShouldBeFound("isskill.equals=" + DEFAULT_ISSKILL);

        // Get all the feedbackQuestionsList where isskill equals to UPDATED_ISSKILL
        defaultFeedbackQuestionsShouldNotBeFound("isskill.equals=" + UPDATED_ISSKILL);
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsByIsskillIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where isskill in DEFAULT_ISSKILL or UPDATED_ISSKILL
        defaultFeedbackQuestionsShouldBeFound("isskill.in=" + DEFAULT_ISSKILL + "," + UPDATED_ISSKILL);

        // Get all the feedbackQuestionsList where isskill equals to UPDATED_ISSKILL
        defaultFeedbackQuestionsShouldNotBeFound("isskill.in=" + UPDATED_ISSKILL);
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsByIsskillIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where isskill is not null
        defaultFeedbackQuestionsShouldBeFound("isskill.specified=true");

        // Get all the feedbackQuestionsList where isskill is null
        defaultFeedbackQuestionsShouldNotBeFound("isskill.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsBySkilltypeIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where skilltype equals to DEFAULT_SKILLTYPE
        defaultFeedbackQuestionsShouldBeFound("skilltype.equals=" + DEFAULT_SKILLTYPE);

        // Get all the feedbackQuestionsList where skilltype equals to UPDATED_SKILLTYPE
        defaultFeedbackQuestionsShouldNotBeFound("skilltype.equals=" + UPDATED_SKILLTYPE);
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsBySkilltypeIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where skilltype in DEFAULT_SKILLTYPE or UPDATED_SKILLTYPE
        defaultFeedbackQuestionsShouldBeFound("skilltype.in=" + DEFAULT_SKILLTYPE + "," + UPDATED_SKILLTYPE);

        // Get all the feedbackQuestionsList where skilltype equals to UPDATED_SKILLTYPE
        defaultFeedbackQuestionsShouldNotBeFound("skilltype.in=" + UPDATED_SKILLTYPE);
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsBySkilltypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where skilltype is not null
        defaultFeedbackQuestionsShouldBeFound("skilltype.specified=true");

        // Get all the feedbackQuestionsList where skilltype is null
        defaultFeedbackQuestionsShouldNotBeFound("skilltype.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsBySkilltypeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where skilltype is greater than or equal to DEFAULT_SKILLTYPE
        defaultFeedbackQuestionsShouldBeFound("skilltype.greaterThanOrEqual=" + DEFAULT_SKILLTYPE);

        // Get all the feedbackQuestionsList where skilltype is greater than or equal to UPDATED_SKILLTYPE
        defaultFeedbackQuestionsShouldNotBeFound("skilltype.greaterThanOrEqual=" + UPDATED_SKILLTYPE);
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsBySkilltypeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where skilltype is less than or equal to DEFAULT_SKILLTYPE
        defaultFeedbackQuestionsShouldBeFound("skilltype.lessThanOrEqual=" + DEFAULT_SKILLTYPE);

        // Get all the feedbackQuestionsList where skilltype is less than or equal to SMALLER_SKILLTYPE
        defaultFeedbackQuestionsShouldNotBeFound("skilltype.lessThanOrEqual=" + SMALLER_SKILLTYPE);
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsBySkilltypeIsLessThanSomething() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where skilltype is less than DEFAULT_SKILLTYPE
        defaultFeedbackQuestionsShouldNotBeFound("skilltype.lessThan=" + DEFAULT_SKILLTYPE);

        // Get all the feedbackQuestionsList where skilltype is less than UPDATED_SKILLTYPE
        defaultFeedbackQuestionsShouldBeFound("skilltype.lessThan=" + UPDATED_SKILLTYPE);
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsBySkilltypeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where skilltype is greater than DEFAULT_SKILLTYPE
        defaultFeedbackQuestionsShouldNotBeFound("skilltype.greaterThan=" + DEFAULT_SKILLTYPE);

        // Get all the feedbackQuestionsList where skilltype is greater than SMALLER_SKILLTYPE
        defaultFeedbackQuestionsShouldBeFound("skilltype.greaterThan=" + SMALLER_SKILLTYPE);
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where createdat equals to DEFAULT_CREATEDAT
        defaultFeedbackQuestionsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the feedbackQuestionsList where createdat equals to UPDATED_CREATEDAT
        defaultFeedbackQuestionsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultFeedbackQuestionsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the feedbackQuestionsList where createdat equals to UPDATED_CREATEDAT
        defaultFeedbackQuestionsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where createdat is not null
        defaultFeedbackQuestionsShouldBeFound("createdat.specified=true");

        // Get all the feedbackQuestionsList where createdat is null
        defaultFeedbackQuestionsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultFeedbackQuestionsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the feedbackQuestionsList where updatedat equals to UPDATED_UPDATEDAT
        defaultFeedbackQuestionsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultFeedbackQuestionsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the feedbackQuestionsList where updatedat equals to UPDATED_UPDATEDAT
        defaultFeedbackQuestionsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        // Get all the feedbackQuestionsList where updatedat is not null
        defaultFeedbackQuestionsShouldBeFound("updatedat.specified=true");

        // Get all the feedbackQuestionsList where updatedat is null
        defaultFeedbackQuestionsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsByEmployeeIsEqualToSomething() throws Exception {
        Employees employee;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);
            employee = EmployeesResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employee);
        em.flush();
        feedbackQuestions.setEmployee(employee);
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);
        Long employeeId = employee.getId();

        // Get all the feedbackQuestionsList where employee equals to employeeId
        defaultFeedbackQuestionsShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the feedbackQuestionsList where employee equals to (employeeId + 1)
        defaultFeedbackQuestionsShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    @Test
    @Transactional
    void getAllFeedbackQuestionsByFeedbackresponsesQuestionIsEqualToSomething() throws Exception {
        FeedbackResponses feedbackresponsesQuestion;
        if (TestUtil.findAll(em, FeedbackResponses.class).isEmpty()) {
            feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);
            feedbackresponsesQuestion = FeedbackResponsesResourceIT.createEntity(em);
        } else {
            feedbackresponsesQuestion = TestUtil.findAll(em, FeedbackResponses.class).get(0);
        }
        em.persist(feedbackresponsesQuestion);
        em.flush();
        feedbackQuestions.addFeedbackresponsesQuestion(feedbackresponsesQuestion);
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);
        Long feedbackresponsesQuestionId = feedbackresponsesQuestion.getId();

        // Get all the feedbackQuestionsList where feedbackresponsesQuestion equals to feedbackresponsesQuestionId
        defaultFeedbackQuestionsShouldBeFound("feedbackresponsesQuestionId.equals=" + feedbackresponsesQuestionId);

        // Get all the feedbackQuestionsList where feedbackresponsesQuestion equals to (feedbackresponsesQuestionId + 1)
        defaultFeedbackQuestionsShouldNotBeFound("feedbackresponsesQuestionId.equals=" + (feedbackresponsesQuestionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFeedbackQuestionsShouldBeFound(String filter) throws Exception {
        restFeedbackQuestionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feedbackQuestions.getId().intValue())))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION)))
            .andExpect(jsonPath("$.[*].isdefault").value(hasItem(DEFAULT_ISDEFAULT.booleanValue())))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA)))
            .andExpect(jsonPath("$.[*].competency").value(hasItem(DEFAULT_COMPETENCY)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].isskill").value(hasItem(DEFAULT_ISSKILL.booleanValue())))
            .andExpect(jsonPath("$.[*].skilltype").value(hasItem(DEFAULT_SKILLTYPE)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restFeedbackQuestionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFeedbackQuestionsShouldNotBeFound(String filter) throws Exception {
        restFeedbackQuestionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFeedbackQuestionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFeedbackQuestions() throws Exception {
        // Get the feedbackQuestions
        restFeedbackQuestionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFeedbackQuestions() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        int databaseSizeBeforeUpdate = feedbackQuestionsRepository.findAll().size();

        // Update the feedbackQuestions
        FeedbackQuestions updatedFeedbackQuestions = feedbackQuestionsRepository.findById(feedbackQuestions.getId()).get();
        // Disconnect from session so that the updates on updatedFeedbackQuestions are not directly saved in db
        em.detach(updatedFeedbackQuestions);
        updatedFeedbackQuestions
            .question(UPDATED_QUESTION)
            .isdefault(UPDATED_ISDEFAULT)
            .area(UPDATED_AREA)
            .competency(UPDATED_COMPETENCY)
            .category(UPDATED_CATEGORY)
            .isskill(UPDATED_ISSKILL)
            .skilltype(UPDATED_SKILLTYPE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restFeedbackQuestionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFeedbackQuestions.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFeedbackQuestions))
            )
            .andExpect(status().isOk());

        // Validate the FeedbackQuestions in the database
        List<FeedbackQuestions> feedbackQuestionsList = feedbackQuestionsRepository.findAll();
        assertThat(feedbackQuestionsList).hasSize(databaseSizeBeforeUpdate);
        FeedbackQuestions testFeedbackQuestions = feedbackQuestionsList.get(feedbackQuestionsList.size() - 1);
        assertThat(testFeedbackQuestions.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testFeedbackQuestions.getIsdefault()).isEqualTo(UPDATED_ISDEFAULT);
        assertThat(testFeedbackQuestions.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testFeedbackQuestions.getCompetency()).isEqualTo(UPDATED_COMPETENCY);
        assertThat(testFeedbackQuestions.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testFeedbackQuestions.getIsskill()).isEqualTo(UPDATED_ISSKILL);
        assertThat(testFeedbackQuestions.getSkilltype()).isEqualTo(UPDATED_SKILLTYPE);
        assertThat(testFeedbackQuestions.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testFeedbackQuestions.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingFeedbackQuestions() throws Exception {
        int databaseSizeBeforeUpdate = feedbackQuestionsRepository.findAll().size();
        feedbackQuestions.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeedbackQuestionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, feedbackQuestions.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackQuestions))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedbackQuestions in the database
        List<FeedbackQuestions> feedbackQuestionsList = feedbackQuestionsRepository.findAll();
        assertThat(feedbackQuestionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFeedbackQuestions() throws Exception {
        int databaseSizeBeforeUpdate = feedbackQuestionsRepository.findAll().size();
        feedbackQuestions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedbackQuestionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackQuestions))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedbackQuestions in the database
        List<FeedbackQuestions> feedbackQuestionsList = feedbackQuestionsRepository.findAll();
        assertThat(feedbackQuestionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFeedbackQuestions() throws Exception {
        int databaseSizeBeforeUpdate = feedbackQuestionsRepository.findAll().size();
        feedbackQuestions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedbackQuestionsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackQuestions))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FeedbackQuestions in the database
        List<FeedbackQuestions> feedbackQuestionsList = feedbackQuestionsRepository.findAll();
        assertThat(feedbackQuestionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFeedbackQuestionsWithPatch() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        int databaseSizeBeforeUpdate = feedbackQuestionsRepository.findAll().size();

        // Update the feedbackQuestions using partial update
        FeedbackQuestions partialUpdatedFeedbackQuestions = new FeedbackQuestions();
        partialUpdatedFeedbackQuestions.setId(feedbackQuestions.getId());

        partialUpdatedFeedbackQuestions.area(UPDATED_AREA).category(UPDATED_CATEGORY).skilltype(UPDATED_SKILLTYPE);

        restFeedbackQuestionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFeedbackQuestions.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFeedbackQuestions))
            )
            .andExpect(status().isOk());

        // Validate the FeedbackQuestions in the database
        List<FeedbackQuestions> feedbackQuestionsList = feedbackQuestionsRepository.findAll();
        assertThat(feedbackQuestionsList).hasSize(databaseSizeBeforeUpdate);
        FeedbackQuestions testFeedbackQuestions = feedbackQuestionsList.get(feedbackQuestionsList.size() - 1);
        assertThat(testFeedbackQuestions.getQuestion()).isEqualTo(DEFAULT_QUESTION);
        assertThat(testFeedbackQuestions.getIsdefault()).isEqualTo(DEFAULT_ISDEFAULT);
        assertThat(testFeedbackQuestions.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testFeedbackQuestions.getCompetency()).isEqualTo(DEFAULT_COMPETENCY);
        assertThat(testFeedbackQuestions.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testFeedbackQuestions.getIsskill()).isEqualTo(DEFAULT_ISSKILL);
        assertThat(testFeedbackQuestions.getSkilltype()).isEqualTo(UPDATED_SKILLTYPE);
        assertThat(testFeedbackQuestions.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testFeedbackQuestions.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateFeedbackQuestionsWithPatch() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        int databaseSizeBeforeUpdate = feedbackQuestionsRepository.findAll().size();

        // Update the feedbackQuestions using partial update
        FeedbackQuestions partialUpdatedFeedbackQuestions = new FeedbackQuestions();
        partialUpdatedFeedbackQuestions.setId(feedbackQuestions.getId());

        partialUpdatedFeedbackQuestions
            .question(UPDATED_QUESTION)
            .isdefault(UPDATED_ISDEFAULT)
            .area(UPDATED_AREA)
            .competency(UPDATED_COMPETENCY)
            .category(UPDATED_CATEGORY)
            .isskill(UPDATED_ISSKILL)
            .skilltype(UPDATED_SKILLTYPE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restFeedbackQuestionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFeedbackQuestions.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFeedbackQuestions))
            )
            .andExpect(status().isOk());

        // Validate the FeedbackQuestions in the database
        List<FeedbackQuestions> feedbackQuestionsList = feedbackQuestionsRepository.findAll();
        assertThat(feedbackQuestionsList).hasSize(databaseSizeBeforeUpdate);
        FeedbackQuestions testFeedbackQuestions = feedbackQuestionsList.get(feedbackQuestionsList.size() - 1);
        assertThat(testFeedbackQuestions.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testFeedbackQuestions.getIsdefault()).isEqualTo(UPDATED_ISDEFAULT);
        assertThat(testFeedbackQuestions.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testFeedbackQuestions.getCompetency()).isEqualTo(UPDATED_COMPETENCY);
        assertThat(testFeedbackQuestions.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testFeedbackQuestions.getIsskill()).isEqualTo(UPDATED_ISSKILL);
        assertThat(testFeedbackQuestions.getSkilltype()).isEqualTo(UPDATED_SKILLTYPE);
        assertThat(testFeedbackQuestions.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testFeedbackQuestions.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingFeedbackQuestions() throws Exception {
        int databaseSizeBeforeUpdate = feedbackQuestionsRepository.findAll().size();
        feedbackQuestions.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeedbackQuestionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, feedbackQuestions.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(feedbackQuestions))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedbackQuestions in the database
        List<FeedbackQuestions> feedbackQuestionsList = feedbackQuestionsRepository.findAll();
        assertThat(feedbackQuestionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFeedbackQuestions() throws Exception {
        int databaseSizeBeforeUpdate = feedbackQuestionsRepository.findAll().size();
        feedbackQuestions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedbackQuestionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(feedbackQuestions))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedbackQuestions in the database
        List<FeedbackQuestions> feedbackQuestionsList = feedbackQuestionsRepository.findAll();
        assertThat(feedbackQuestionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFeedbackQuestions() throws Exception {
        int databaseSizeBeforeUpdate = feedbackQuestionsRepository.findAll().size();
        feedbackQuestions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedbackQuestionsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(feedbackQuestions))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FeedbackQuestions in the database
        List<FeedbackQuestions> feedbackQuestionsList = feedbackQuestionsRepository.findAll();
        assertThat(feedbackQuestionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFeedbackQuestions() throws Exception {
        // Initialize the database
        feedbackQuestionsRepository.saveAndFlush(feedbackQuestions);

        int databaseSizeBeforeDelete = feedbackQuestionsRepository.findAll().size();

        // Delete the feedbackQuestions
        restFeedbackQuestionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, feedbackQuestions.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FeedbackQuestions> feedbackQuestionsList = feedbackQuestionsRepository.findAll();
        assertThat(feedbackQuestionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
