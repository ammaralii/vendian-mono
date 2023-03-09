package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.FeedbackEmails;
import com.venturedive.vendian_mono.domain.FeedbackRequests;
import com.venturedive.vendian_mono.domain.FeedbackRespondents;
import com.venturedive.vendian_mono.domain.FeedbackResponses;
import com.venturedive.vendian_mono.repository.FeedbackRespondentsRepository;
import com.venturedive.vendian_mono.service.criteria.FeedbackRespondentsCriteria;
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
 * Integration tests for the {@link FeedbackRespondentsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FeedbackRespondentsResourceIT {

    private static final Integer DEFAULT_CATEGORY = 1;
    private static final Integer UPDATED_CATEGORY = 2;
    private static final Integer SMALLER_CATEGORY = 1 - 1;

    private static final Boolean DEFAULT_HASRESPONDED = false;
    private static final Boolean UPDATED_HASRESPONDED = true;

    private static final Instant DEFAULT_RESPONDEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RESPONDEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/feedback-respondents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FeedbackRespondentsRepository feedbackRespondentsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFeedbackRespondentsMockMvc;

    private FeedbackRespondents feedbackRespondents;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FeedbackRespondents createEntity(EntityManager em) {
        FeedbackRespondents feedbackRespondents = new FeedbackRespondents()
            .category(DEFAULT_CATEGORY)
            .hasresponded(DEFAULT_HASRESPONDED)
            .respondedat(DEFAULT_RESPONDEDAT)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        return feedbackRespondents;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FeedbackRespondents createUpdatedEntity(EntityManager em) {
        FeedbackRespondents feedbackRespondents = new FeedbackRespondents()
            .category(UPDATED_CATEGORY)
            .hasresponded(UPDATED_HASRESPONDED)
            .respondedat(UPDATED_RESPONDEDAT)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        return feedbackRespondents;
    }

    @BeforeEach
    public void initTest() {
        feedbackRespondents = createEntity(em);
    }

    @Test
    @Transactional
    void createFeedbackRespondents() throws Exception {
        int databaseSizeBeforeCreate = feedbackRespondentsRepository.findAll().size();
        // Create the FeedbackRespondents
        restFeedbackRespondentsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackRespondents))
            )
            .andExpect(status().isCreated());

        // Validate the FeedbackRespondents in the database
        List<FeedbackRespondents> feedbackRespondentsList = feedbackRespondentsRepository.findAll();
        assertThat(feedbackRespondentsList).hasSize(databaseSizeBeforeCreate + 1);
        FeedbackRespondents testFeedbackRespondents = feedbackRespondentsList.get(feedbackRespondentsList.size() - 1);
        assertThat(testFeedbackRespondents.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testFeedbackRespondents.getHasresponded()).isEqualTo(DEFAULT_HASRESPONDED);
        assertThat(testFeedbackRespondents.getRespondedat()).isEqualTo(DEFAULT_RESPONDEDAT);
        assertThat(testFeedbackRespondents.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testFeedbackRespondents.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createFeedbackRespondentsWithExistingId() throws Exception {
        // Create the FeedbackRespondents with an existing ID
        feedbackRespondents.setId(1L);

        int databaseSizeBeforeCreate = feedbackRespondentsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeedbackRespondentsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackRespondents))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedbackRespondents in the database
        List<FeedbackRespondents> feedbackRespondentsList = feedbackRespondentsRepository.findAll();
        assertThat(feedbackRespondentsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedbackRespondentsRepository.findAll().size();
        // set the field null
        feedbackRespondents.setCategory(null);

        // Create the FeedbackRespondents, which fails.

        restFeedbackRespondentsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackRespondents))
            )
            .andExpect(status().isBadRequest());

        List<FeedbackRespondents> feedbackRespondentsList = feedbackRespondentsRepository.findAll();
        assertThat(feedbackRespondentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedbackRespondentsRepository.findAll().size();
        // set the field null
        feedbackRespondents.setCreatedat(null);

        // Create the FeedbackRespondents, which fails.

        restFeedbackRespondentsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackRespondents))
            )
            .andExpect(status().isBadRequest());

        List<FeedbackRespondents> feedbackRespondentsList = feedbackRespondentsRepository.findAll();
        assertThat(feedbackRespondentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedbackRespondentsRepository.findAll().size();
        // set the field null
        feedbackRespondents.setUpdatedat(null);

        // Create the FeedbackRespondents, which fails.

        restFeedbackRespondentsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackRespondents))
            )
            .andExpect(status().isBadRequest());

        List<FeedbackRespondents> feedbackRespondentsList = feedbackRespondentsRepository.findAll();
        assertThat(feedbackRespondentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFeedbackRespondents() throws Exception {
        // Initialize the database
        feedbackRespondentsRepository.saveAndFlush(feedbackRespondents);

        // Get all the feedbackRespondentsList
        restFeedbackRespondentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feedbackRespondents.getId().intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].hasresponded").value(hasItem(DEFAULT_HASRESPONDED.booleanValue())))
            .andExpect(jsonPath("$.[*].respondedat").value(hasItem(DEFAULT_RESPONDEDAT.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getFeedbackRespondents() throws Exception {
        // Initialize the database
        feedbackRespondentsRepository.saveAndFlush(feedbackRespondents);

        // Get the feedbackRespondents
        restFeedbackRespondentsMockMvc
            .perform(get(ENTITY_API_URL_ID, feedbackRespondents.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(feedbackRespondents.getId().intValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
            .andExpect(jsonPath("$.hasresponded").value(DEFAULT_HASRESPONDED.booleanValue()))
            .andExpect(jsonPath("$.respondedat").value(DEFAULT_RESPONDEDAT.toString()))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getFeedbackRespondentsByIdFiltering() throws Exception {
        // Initialize the database
        feedbackRespondentsRepository.saveAndFlush(feedbackRespondents);

        Long id = feedbackRespondents.getId();

        defaultFeedbackRespondentsShouldBeFound("id.equals=" + id);
        defaultFeedbackRespondentsShouldNotBeFound("id.notEquals=" + id);

        defaultFeedbackRespondentsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFeedbackRespondentsShouldNotBeFound("id.greaterThan=" + id);

        defaultFeedbackRespondentsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFeedbackRespondentsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFeedbackRespondentsByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackRespondentsRepository.saveAndFlush(feedbackRespondents);

        // Get all the feedbackRespondentsList where category equals to DEFAULT_CATEGORY
        defaultFeedbackRespondentsShouldBeFound("category.equals=" + DEFAULT_CATEGORY);

        // Get all the feedbackRespondentsList where category equals to UPDATED_CATEGORY
        defaultFeedbackRespondentsShouldNotBeFound("category.equals=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllFeedbackRespondentsByCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackRespondentsRepository.saveAndFlush(feedbackRespondents);

        // Get all the feedbackRespondentsList where category in DEFAULT_CATEGORY or UPDATED_CATEGORY
        defaultFeedbackRespondentsShouldBeFound("category.in=" + DEFAULT_CATEGORY + "," + UPDATED_CATEGORY);

        // Get all the feedbackRespondentsList where category equals to UPDATED_CATEGORY
        defaultFeedbackRespondentsShouldNotBeFound("category.in=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllFeedbackRespondentsByCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackRespondentsRepository.saveAndFlush(feedbackRespondents);

        // Get all the feedbackRespondentsList where category is not null
        defaultFeedbackRespondentsShouldBeFound("category.specified=true");

        // Get all the feedbackRespondentsList where category is null
        defaultFeedbackRespondentsShouldNotBeFound("category.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedbackRespondentsByCategoryIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feedbackRespondentsRepository.saveAndFlush(feedbackRespondents);

        // Get all the feedbackRespondentsList where category is greater than or equal to DEFAULT_CATEGORY
        defaultFeedbackRespondentsShouldBeFound("category.greaterThanOrEqual=" + DEFAULT_CATEGORY);

        // Get all the feedbackRespondentsList where category is greater than or equal to UPDATED_CATEGORY
        defaultFeedbackRespondentsShouldNotBeFound("category.greaterThanOrEqual=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllFeedbackRespondentsByCategoryIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feedbackRespondentsRepository.saveAndFlush(feedbackRespondents);

        // Get all the feedbackRespondentsList where category is less than or equal to DEFAULT_CATEGORY
        defaultFeedbackRespondentsShouldBeFound("category.lessThanOrEqual=" + DEFAULT_CATEGORY);

        // Get all the feedbackRespondentsList where category is less than or equal to SMALLER_CATEGORY
        defaultFeedbackRespondentsShouldNotBeFound("category.lessThanOrEqual=" + SMALLER_CATEGORY);
    }

    @Test
    @Transactional
    void getAllFeedbackRespondentsByCategoryIsLessThanSomething() throws Exception {
        // Initialize the database
        feedbackRespondentsRepository.saveAndFlush(feedbackRespondents);

        // Get all the feedbackRespondentsList where category is less than DEFAULT_CATEGORY
        defaultFeedbackRespondentsShouldNotBeFound("category.lessThan=" + DEFAULT_CATEGORY);

        // Get all the feedbackRespondentsList where category is less than UPDATED_CATEGORY
        defaultFeedbackRespondentsShouldBeFound("category.lessThan=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllFeedbackRespondentsByCategoryIsGreaterThanSomething() throws Exception {
        // Initialize the database
        feedbackRespondentsRepository.saveAndFlush(feedbackRespondents);

        // Get all the feedbackRespondentsList where category is greater than DEFAULT_CATEGORY
        defaultFeedbackRespondentsShouldNotBeFound("category.greaterThan=" + DEFAULT_CATEGORY);

        // Get all the feedbackRespondentsList where category is greater than SMALLER_CATEGORY
        defaultFeedbackRespondentsShouldBeFound("category.greaterThan=" + SMALLER_CATEGORY);
    }

    @Test
    @Transactional
    void getAllFeedbackRespondentsByHasrespondedIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackRespondentsRepository.saveAndFlush(feedbackRespondents);

        // Get all the feedbackRespondentsList where hasresponded equals to DEFAULT_HASRESPONDED
        defaultFeedbackRespondentsShouldBeFound("hasresponded.equals=" + DEFAULT_HASRESPONDED);

        // Get all the feedbackRespondentsList where hasresponded equals to UPDATED_HASRESPONDED
        defaultFeedbackRespondentsShouldNotBeFound("hasresponded.equals=" + UPDATED_HASRESPONDED);
    }

    @Test
    @Transactional
    void getAllFeedbackRespondentsByHasrespondedIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackRespondentsRepository.saveAndFlush(feedbackRespondents);

        // Get all the feedbackRespondentsList where hasresponded in DEFAULT_HASRESPONDED or UPDATED_HASRESPONDED
        defaultFeedbackRespondentsShouldBeFound("hasresponded.in=" + DEFAULT_HASRESPONDED + "," + UPDATED_HASRESPONDED);

        // Get all the feedbackRespondentsList where hasresponded equals to UPDATED_HASRESPONDED
        defaultFeedbackRespondentsShouldNotBeFound("hasresponded.in=" + UPDATED_HASRESPONDED);
    }

    @Test
    @Transactional
    void getAllFeedbackRespondentsByHasrespondedIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackRespondentsRepository.saveAndFlush(feedbackRespondents);

        // Get all the feedbackRespondentsList where hasresponded is not null
        defaultFeedbackRespondentsShouldBeFound("hasresponded.specified=true");

        // Get all the feedbackRespondentsList where hasresponded is null
        defaultFeedbackRespondentsShouldNotBeFound("hasresponded.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedbackRespondentsByRespondedatIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackRespondentsRepository.saveAndFlush(feedbackRespondents);

        // Get all the feedbackRespondentsList where respondedat equals to DEFAULT_RESPONDEDAT
        defaultFeedbackRespondentsShouldBeFound("respondedat.equals=" + DEFAULT_RESPONDEDAT);

        // Get all the feedbackRespondentsList where respondedat equals to UPDATED_RESPONDEDAT
        defaultFeedbackRespondentsShouldNotBeFound("respondedat.equals=" + UPDATED_RESPONDEDAT);
    }

    @Test
    @Transactional
    void getAllFeedbackRespondentsByRespondedatIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackRespondentsRepository.saveAndFlush(feedbackRespondents);

        // Get all the feedbackRespondentsList where respondedat in DEFAULT_RESPONDEDAT or UPDATED_RESPONDEDAT
        defaultFeedbackRespondentsShouldBeFound("respondedat.in=" + DEFAULT_RESPONDEDAT + "," + UPDATED_RESPONDEDAT);

        // Get all the feedbackRespondentsList where respondedat equals to UPDATED_RESPONDEDAT
        defaultFeedbackRespondentsShouldNotBeFound("respondedat.in=" + UPDATED_RESPONDEDAT);
    }

    @Test
    @Transactional
    void getAllFeedbackRespondentsByRespondedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackRespondentsRepository.saveAndFlush(feedbackRespondents);

        // Get all the feedbackRespondentsList where respondedat is not null
        defaultFeedbackRespondentsShouldBeFound("respondedat.specified=true");

        // Get all the feedbackRespondentsList where respondedat is null
        defaultFeedbackRespondentsShouldNotBeFound("respondedat.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedbackRespondentsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackRespondentsRepository.saveAndFlush(feedbackRespondents);

        // Get all the feedbackRespondentsList where createdat equals to DEFAULT_CREATEDAT
        defaultFeedbackRespondentsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the feedbackRespondentsList where createdat equals to UPDATED_CREATEDAT
        defaultFeedbackRespondentsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllFeedbackRespondentsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackRespondentsRepository.saveAndFlush(feedbackRespondents);

        // Get all the feedbackRespondentsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultFeedbackRespondentsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the feedbackRespondentsList where createdat equals to UPDATED_CREATEDAT
        defaultFeedbackRespondentsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllFeedbackRespondentsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackRespondentsRepository.saveAndFlush(feedbackRespondents);

        // Get all the feedbackRespondentsList where createdat is not null
        defaultFeedbackRespondentsShouldBeFound("createdat.specified=true");

        // Get all the feedbackRespondentsList where createdat is null
        defaultFeedbackRespondentsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedbackRespondentsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackRespondentsRepository.saveAndFlush(feedbackRespondents);

        // Get all the feedbackRespondentsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultFeedbackRespondentsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the feedbackRespondentsList where updatedat equals to UPDATED_UPDATEDAT
        defaultFeedbackRespondentsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllFeedbackRespondentsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackRespondentsRepository.saveAndFlush(feedbackRespondents);

        // Get all the feedbackRespondentsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultFeedbackRespondentsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the feedbackRespondentsList where updatedat equals to UPDATED_UPDATEDAT
        defaultFeedbackRespondentsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllFeedbackRespondentsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackRespondentsRepository.saveAndFlush(feedbackRespondents);

        // Get all the feedbackRespondentsList where updatedat is not null
        defaultFeedbackRespondentsShouldBeFound("updatedat.specified=true");

        // Get all the feedbackRespondentsList where updatedat is null
        defaultFeedbackRespondentsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedbackRespondentsByEmployeeIsEqualToSomething() throws Exception {
        Employees employee;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            feedbackRespondentsRepository.saveAndFlush(feedbackRespondents);
            employee = EmployeesResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employee);
        em.flush();
        feedbackRespondents.setEmployee(employee);
        feedbackRespondentsRepository.saveAndFlush(feedbackRespondents);
        Long employeeId = employee.getId();

        // Get all the feedbackRespondentsList where employee equals to employeeId
        defaultFeedbackRespondentsShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the feedbackRespondentsList where employee equals to (employeeId + 1)
        defaultFeedbackRespondentsShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    @Test
    @Transactional
    void getAllFeedbackRespondentsByRequestIsEqualToSomething() throws Exception {
        FeedbackRequests request;
        if (TestUtil.findAll(em, FeedbackRequests.class).isEmpty()) {
            feedbackRespondentsRepository.saveAndFlush(feedbackRespondents);
            request = FeedbackRequestsResourceIT.createEntity(em);
        } else {
            request = TestUtil.findAll(em, FeedbackRequests.class).get(0);
        }
        em.persist(request);
        em.flush();
        feedbackRespondents.setRequest(request);
        feedbackRespondentsRepository.saveAndFlush(feedbackRespondents);
        Long requestId = request.getId();

        // Get all the feedbackRespondentsList where request equals to requestId
        defaultFeedbackRespondentsShouldBeFound("requestId.equals=" + requestId);

        // Get all the feedbackRespondentsList where request equals to (requestId + 1)
        defaultFeedbackRespondentsShouldNotBeFound("requestId.equals=" + (requestId + 1));
    }

    @Test
    @Transactional
    void getAllFeedbackRespondentsByFeedbackemailsRespondentIsEqualToSomething() throws Exception {
        FeedbackEmails feedbackemailsRespondent;
        if (TestUtil.findAll(em, FeedbackEmails.class).isEmpty()) {
            feedbackRespondentsRepository.saveAndFlush(feedbackRespondents);
            feedbackemailsRespondent = FeedbackEmailsResourceIT.createEntity(em);
        } else {
            feedbackemailsRespondent = TestUtil.findAll(em, FeedbackEmails.class).get(0);
        }
        em.persist(feedbackemailsRespondent);
        em.flush();
        feedbackRespondents.addFeedbackemailsRespondent(feedbackemailsRespondent);
        feedbackRespondentsRepository.saveAndFlush(feedbackRespondents);
        Long feedbackemailsRespondentId = feedbackemailsRespondent.getId();

        // Get all the feedbackRespondentsList where feedbackemailsRespondent equals to feedbackemailsRespondentId
        defaultFeedbackRespondentsShouldBeFound("feedbackemailsRespondentId.equals=" + feedbackemailsRespondentId);

        // Get all the feedbackRespondentsList where feedbackemailsRespondent equals to (feedbackemailsRespondentId + 1)
        defaultFeedbackRespondentsShouldNotBeFound("feedbackemailsRespondentId.equals=" + (feedbackemailsRespondentId + 1));
    }

    @Test
    @Transactional
    void getAllFeedbackRespondentsByFeedbackresponsesRespondentIsEqualToSomething() throws Exception {
        FeedbackResponses feedbackresponsesRespondent;
        if (TestUtil.findAll(em, FeedbackResponses.class).isEmpty()) {
            feedbackRespondentsRepository.saveAndFlush(feedbackRespondents);
            feedbackresponsesRespondent = FeedbackResponsesResourceIT.createEntity(em);
        } else {
            feedbackresponsesRespondent = TestUtil.findAll(em, FeedbackResponses.class).get(0);
        }
        em.persist(feedbackresponsesRespondent);
        em.flush();
        feedbackRespondents.addFeedbackresponsesRespondent(feedbackresponsesRespondent);
        feedbackRespondentsRepository.saveAndFlush(feedbackRespondents);
        Long feedbackresponsesRespondentId = feedbackresponsesRespondent.getId();

        // Get all the feedbackRespondentsList where feedbackresponsesRespondent equals to feedbackresponsesRespondentId
        defaultFeedbackRespondentsShouldBeFound("feedbackresponsesRespondentId.equals=" + feedbackresponsesRespondentId);

        // Get all the feedbackRespondentsList where feedbackresponsesRespondent equals to (feedbackresponsesRespondentId + 1)
        defaultFeedbackRespondentsShouldNotBeFound("feedbackresponsesRespondentId.equals=" + (feedbackresponsesRespondentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFeedbackRespondentsShouldBeFound(String filter) throws Exception {
        restFeedbackRespondentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feedbackRespondents.getId().intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].hasresponded").value(hasItem(DEFAULT_HASRESPONDED.booleanValue())))
            .andExpect(jsonPath("$.[*].respondedat").value(hasItem(DEFAULT_RESPONDEDAT.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restFeedbackRespondentsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFeedbackRespondentsShouldNotBeFound(String filter) throws Exception {
        restFeedbackRespondentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFeedbackRespondentsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFeedbackRespondents() throws Exception {
        // Get the feedbackRespondents
        restFeedbackRespondentsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFeedbackRespondents() throws Exception {
        // Initialize the database
        feedbackRespondentsRepository.saveAndFlush(feedbackRespondents);

        int databaseSizeBeforeUpdate = feedbackRespondentsRepository.findAll().size();

        // Update the feedbackRespondents
        FeedbackRespondents updatedFeedbackRespondents = feedbackRespondentsRepository.findById(feedbackRespondents.getId()).get();
        // Disconnect from session so that the updates on updatedFeedbackRespondents are not directly saved in db
        em.detach(updatedFeedbackRespondents);
        updatedFeedbackRespondents
            .category(UPDATED_CATEGORY)
            .hasresponded(UPDATED_HASRESPONDED)
            .respondedat(UPDATED_RESPONDEDAT)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restFeedbackRespondentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFeedbackRespondents.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFeedbackRespondents))
            )
            .andExpect(status().isOk());

        // Validate the FeedbackRespondents in the database
        List<FeedbackRespondents> feedbackRespondentsList = feedbackRespondentsRepository.findAll();
        assertThat(feedbackRespondentsList).hasSize(databaseSizeBeforeUpdate);
        FeedbackRespondents testFeedbackRespondents = feedbackRespondentsList.get(feedbackRespondentsList.size() - 1);
        assertThat(testFeedbackRespondents.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testFeedbackRespondents.getHasresponded()).isEqualTo(UPDATED_HASRESPONDED);
        assertThat(testFeedbackRespondents.getRespondedat()).isEqualTo(UPDATED_RESPONDEDAT);
        assertThat(testFeedbackRespondents.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testFeedbackRespondents.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingFeedbackRespondents() throws Exception {
        int databaseSizeBeforeUpdate = feedbackRespondentsRepository.findAll().size();
        feedbackRespondents.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeedbackRespondentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, feedbackRespondents.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackRespondents))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedbackRespondents in the database
        List<FeedbackRespondents> feedbackRespondentsList = feedbackRespondentsRepository.findAll();
        assertThat(feedbackRespondentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFeedbackRespondents() throws Exception {
        int databaseSizeBeforeUpdate = feedbackRespondentsRepository.findAll().size();
        feedbackRespondents.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedbackRespondentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackRespondents))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedbackRespondents in the database
        List<FeedbackRespondents> feedbackRespondentsList = feedbackRespondentsRepository.findAll();
        assertThat(feedbackRespondentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFeedbackRespondents() throws Exception {
        int databaseSizeBeforeUpdate = feedbackRespondentsRepository.findAll().size();
        feedbackRespondents.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedbackRespondentsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackRespondents))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FeedbackRespondents in the database
        List<FeedbackRespondents> feedbackRespondentsList = feedbackRespondentsRepository.findAll();
        assertThat(feedbackRespondentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFeedbackRespondentsWithPatch() throws Exception {
        // Initialize the database
        feedbackRespondentsRepository.saveAndFlush(feedbackRespondents);

        int databaseSizeBeforeUpdate = feedbackRespondentsRepository.findAll().size();

        // Update the feedbackRespondents using partial update
        FeedbackRespondents partialUpdatedFeedbackRespondents = new FeedbackRespondents();
        partialUpdatedFeedbackRespondents.setId(feedbackRespondents.getId());

        partialUpdatedFeedbackRespondents.respondedat(UPDATED_RESPONDEDAT).createdat(UPDATED_CREATEDAT);

        restFeedbackRespondentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFeedbackRespondents.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFeedbackRespondents))
            )
            .andExpect(status().isOk());

        // Validate the FeedbackRespondents in the database
        List<FeedbackRespondents> feedbackRespondentsList = feedbackRespondentsRepository.findAll();
        assertThat(feedbackRespondentsList).hasSize(databaseSizeBeforeUpdate);
        FeedbackRespondents testFeedbackRespondents = feedbackRespondentsList.get(feedbackRespondentsList.size() - 1);
        assertThat(testFeedbackRespondents.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testFeedbackRespondents.getHasresponded()).isEqualTo(DEFAULT_HASRESPONDED);
        assertThat(testFeedbackRespondents.getRespondedat()).isEqualTo(UPDATED_RESPONDEDAT);
        assertThat(testFeedbackRespondents.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testFeedbackRespondents.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateFeedbackRespondentsWithPatch() throws Exception {
        // Initialize the database
        feedbackRespondentsRepository.saveAndFlush(feedbackRespondents);

        int databaseSizeBeforeUpdate = feedbackRespondentsRepository.findAll().size();

        // Update the feedbackRespondents using partial update
        FeedbackRespondents partialUpdatedFeedbackRespondents = new FeedbackRespondents();
        partialUpdatedFeedbackRespondents.setId(feedbackRespondents.getId());

        partialUpdatedFeedbackRespondents
            .category(UPDATED_CATEGORY)
            .hasresponded(UPDATED_HASRESPONDED)
            .respondedat(UPDATED_RESPONDEDAT)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restFeedbackRespondentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFeedbackRespondents.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFeedbackRespondents))
            )
            .andExpect(status().isOk());

        // Validate the FeedbackRespondents in the database
        List<FeedbackRespondents> feedbackRespondentsList = feedbackRespondentsRepository.findAll();
        assertThat(feedbackRespondentsList).hasSize(databaseSizeBeforeUpdate);
        FeedbackRespondents testFeedbackRespondents = feedbackRespondentsList.get(feedbackRespondentsList.size() - 1);
        assertThat(testFeedbackRespondents.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testFeedbackRespondents.getHasresponded()).isEqualTo(UPDATED_HASRESPONDED);
        assertThat(testFeedbackRespondents.getRespondedat()).isEqualTo(UPDATED_RESPONDEDAT);
        assertThat(testFeedbackRespondents.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testFeedbackRespondents.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingFeedbackRespondents() throws Exception {
        int databaseSizeBeforeUpdate = feedbackRespondentsRepository.findAll().size();
        feedbackRespondents.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeedbackRespondentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, feedbackRespondents.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(feedbackRespondents))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedbackRespondents in the database
        List<FeedbackRespondents> feedbackRespondentsList = feedbackRespondentsRepository.findAll();
        assertThat(feedbackRespondentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFeedbackRespondents() throws Exception {
        int databaseSizeBeforeUpdate = feedbackRespondentsRepository.findAll().size();
        feedbackRespondents.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedbackRespondentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(feedbackRespondents))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedbackRespondents in the database
        List<FeedbackRespondents> feedbackRespondentsList = feedbackRespondentsRepository.findAll();
        assertThat(feedbackRespondentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFeedbackRespondents() throws Exception {
        int databaseSizeBeforeUpdate = feedbackRespondentsRepository.findAll().size();
        feedbackRespondents.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedbackRespondentsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(feedbackRespondents))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FeedbackRespondents in the database
        List<FeedbackRespondents> feedbackRespondentsList = feedbackRespondentsRepository.findAll();
        assertThat(feedbackRespondentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFeedbackRespondents() throws Exception {
        // Initialize the database
        feedbackRespondentsRepository.saveAndFlush(feedbackRespondents);

        int databaseSizeBeforeDelete = feedbackRespondentsRepository.findAll().size();

        // Delete the feedbackRespondents
        restFeedbackRespondentsMockMvc
            .perform(delete(ENTITY_API_URL_ID, feedbackRespondents.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FeedbackRespondents> feedbackRespondentsList = feedbackRespondentsRepository.findAll();
        assertThat(feedbackRespondentsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
