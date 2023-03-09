package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.Goals;
import com.venturedive.vendian_mono.domain.UserGoalRaterAttributes;
import com.venturedive.vendian_mono.domain.UserGoals;
import com.venturedive.vendian_mono.repository.UserGoalsRepository;
import com.venturedive.vendian_mono.service.criteria.UserGoalsCriteria;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link UserGoalsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserGoalsResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_MEASUREMENT = "AAAAAAAAAA";
    private static final String UPDATED_MEASUREMENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_WEIGHTAGE = 1;
    private static final Integer UPDATED_WEIGHTAGE = 2;
    private static final Integer SMALLER_WEIGHTAGE = 1 - 1;

    private static final Integer DEFAULT_PROGRESS = 1;
    private static final Integer UPDATED_PROGRESS = 2;
    private static final Integer SMALLER_PROGRESS = 1 - 1;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DUE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DUE_DATE = LocalDate.ofEpochDay(-1L);

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;
    private static final Integer SMALLER_VERSION = 1 - 1;

    private static final String ENTITY_API_URL = "/api/user-goals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserGoalsRepository userGoalsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserGoalsMockMvc;

    private UserGoals userGoals;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserGoals createEntity(EntityManager em) {
        UserGoals userGoals = new UserGoals()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .measurement(DEFAULT_MEASUREMENT)
            .weightage(DEFAULT_WEIGHTAGE)
            .progress(DEFAULT_PROGRESS)
            .status(DEFAULT_STATUS)
            .dueDate(DEFAULT_DUE_DATE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT)
            .version(DEFAULT_VERSION);
        return userGoals;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserGoals createUpdatedEntity(EntityManager em) {
        UserGoals userGoals = new UserGoals()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .measurement(UPDATED_MEASUREMENT)
            .weightage(UPDATED_WEIGHTAGE)
            .progress(UPDATED_PROGRESS)
            .status(UPDATED_STATUS)
            .dueDate(UPDATED_DUE_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);
        return userGoals;
    }

    @BeforeEach
    public void initTest() {
        userGoals = createEntity(em);
    }

    @Test
    @Transactional
    void createUserGoals() throws Exception {
        int databaseSizeBeforeCreate = userGoalsRepository.findAll().size();
        // Create the UserGoals
        restUserGoalsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userGoals))
            )
            .andExpect(status().isCreated());

        // Validate the UserGoals in the database
        List<UserGoals> userGoalsList = userGoalsRepository.findAll();
        assertThat(userGoalsList).hasSize(databaseSizeBeforeCreate + 1);
        UserGoals testUserGoals = userGoalsList.get(userGoalsList.size() - 1);
        assertThat(testUserGoals.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testUserGoals.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testUserGoals.getMeasurement()).isEqualTo(DEFAULT_MEASUREMENT);
        assertThat(testUserGoals.getWeightage()).isEqualTo(DEFAULT_WEIGHTAGE);
        assertThat(testUserGoals.getProgress()).isEqualTo(DEFAULT_PROGRESS);
        assertThat(testUserGoals.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUserGoals.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testUserGoals.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testUserGoals.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testUserGoals.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testUserGoals.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createUserGoalsWithExistingId() throws Exception {
        // Create the UserGoals with an existing ID
        userGoals.setId(1L);

        int databaseSizeBeforeCreate = userGoalsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserGoalsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userGoals))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserGoals in the database
        List<UserGoals> userGoalsList = userGoalsRepository.findAll();
        assertThat(userGoalsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = userGoalsRepository.findAll().size();
        // set the field null
        userGoals.setTitle(null);

        // Create the UserGoals, which fails.

        restUserGoalsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userGoals))
            )
            .andExpect(status().isBadRequest());

        List<UserGoals> userGoalsList = userGoalsRepository.findAll();
        assertThat(userGoalsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = userGoalsRepository.findAll().size();
        // set the field null
        userGoals.setCreatedAt(null);

        // Create the UserGoals, which fails.

        restUserGoalsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userGoals))
            )
            .andExpect(status().isBadRequest());

        List<UserGoals> userGoalsList = userGoalsRepository.findAll();
        assertThat(userGoalsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = userGoalsRepository.findAll().size();
        // set the field null
        userGoals.setUpdatedAt(null);

        // Create the UserGoals, which fails.

        restUserGoalsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userGoals))
            )
            .andExpect(status().isBadRequest());

        List<UserGoals> userGoalsList = userGoalsRepository.findAll();
        assertThat(userGoalsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = userGoalsRepository.findAll().size();
        // set the field null
        userGoals.setVersion(null);

        // Create the UserGoals, which fails.

        restUserGoalsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userGoals))
            )
            .andExpect(status().isBadRequest());

        List<UserGoals> userGoalsList = userGoalsRepository.findAll();
        assertThat(userGoalsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUserGoals() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList
        restUserGoalsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userGoals.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].measurement").value(hasItem(DEFAULT_MEASUREMENT)))
            .andExpect(jsonPath("$.[*].weightage").value(hasItem(DEFAULT_WEIGHTAGE)))
            .andExpect(jsonPath("$.[*].progress").value(hasItem(DEFAULT_PROGRESS)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getUserGoals() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get the userGoals
        restUserGoalsMockMvc
            .perform(get(ENTITY_API_URL_ID, userGoals.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userGoals.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.measurement").value(DEFAULT_MEASUREMENT))
            .andExpect(jsonPath("$.weightage").value(DEFAULT_WEIGHTAGE))
            .andExpect(jsonPath("$.progress").value(DEFAULT_PROGRESS))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getUserGoalsByIdFiltering() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        Long id = userGoals.getId();

        defaultUserGoalsShouldBeFound("id.equals=" + id);
        defaultUserGoalsShouldNotBeFound("id.notEquals=" + id);

        defaultUserGoalsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUserGoalsShouldNotBeFound("id.greaterThan=" + id);

        defaultUserGoalsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUserGoalsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllUserGoalsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where title equals to DEFAULT_TITLE
        defaultUserGoalsShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the userGoalsList where title equals to UPDATED_TITLE
        defaultUserGoalsShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllUserGoalsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultUserGoalsShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the userGoalsList where title equals to UPDATED_TITLE
        defaultUserGoalsShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllUserGoalsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where title is not null
        defaultUserGoalsShouldBeFound("title.specified=true");

        // Get all the userGoalsList where title is null
        defaultUserGoalsShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllUserGoalsByTitleContainsSomething() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where title contains DEFAULT_TITLE
        defaultUserGoalsShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the userGoalsList where title contains UPDATED_TITLE
        defaultUserGoalsShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllUserGoalsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where title does not contain DEFAULT_TITLE
        defaultUserGoalsShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the userGoalsList where title does not contain UPDATED_TITLE
        defaultUserGoalsShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllUserGoalsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where description equals to DEFAULT_DESCRIPTION
        defaultUserGoalsShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the userGoalsList where description equals to UPDATED_DESCRIPTION
        defaultUserGoalsShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllUserGoalsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultUserGoalsShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the userGoalsList where description equals to UPDATED_DESCRIPTION
        defaultUserGoalsShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllUserGoalsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where description is not null
        defaultUserGoalsShouldBeFound("description.specified=true");

        // Get all the userGoalsList where description is null
        defaultUserGoalsShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllUserGoalsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where description contains DEFAULT_DESCRIPTION
        defaultUserGoalsShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the userGoalsList where description contains UPDATED_DESCRIPTION
        defaultUserGoalsShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllUserGoalsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where description does not contain DEFAULT_DESCRIPTION
        defaultUserGoalsShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the userGoalsList where description does not contain UPDATED_DESCRIPTION
        defaultUserGoalsShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllUserGoalsByMeasurementIsEqualToSomething() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where measurement equals to DEFAULT_MEASUREMENT
        defaultUserGoalsShouldBeFound("measurement.equals=" + DEFAULT_MEASUREMENT);

        // Get all the userGoalsList where measurement equals to UPDATED_MEASUREMENT
        defaultUserGoalsShouldNotBeFound("measurement.equals=" + UPDATED_MEASUREMENT);
    }

    @Test
    @Transactional
    void getAllUserGoalsByMeasurementIsInShouldWork() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where measurement in DEFAULT_MEASUREMENT or UPDATED_MEASUREMENT
        defaultUserGoalsShouldBeFound("measurement.in=" + DEFAULT_MEASUREMENT + "," + UPDATED_MEASUREMENT);

        // Get all the userGoalsList where measurement equals to UPDATED_MEASUREMENT
        defaultUserGoalsShouldNotBeFound("measurement.in=" + UPDATED_MEASUREMENT);
    }

    @Test
    @Transactional
    void getAllUserGoalsByMeasurementIsNullOrNotNull() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where measurement is not null
        defaultUserGoalsShouldBeFound("measurement.specified=true");

        // Get all the userGoalsList where measurement is null
        defaultUserGoalsShouldNotBeFound("measurement.specified=false");
    }

    @Test
    @Transactional
    void getAllUserGoalsByMeasurementContainsSomething() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where measurement contains DEFAULT_MEASUREMENT
        defaultUserGoalsShouldBeFound("measurement.contains=" + DEFAULT_MEASUREMENT);

        // Get all the userGoalsList where measurement contains UPDATED_MEASUREMENT
        defaultUserGoalsShouldNotBeFound("measurement.contains=" + UPDATED_MEASUREMENT);
    }

    @Test
    @Transactional
    void getAllUserGoalsByMeasurementNotContainsSomething() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where measurement does not contain DEFAULT_MEASUREMENT
        defaultUserGoalsShouldNotBeFound("measurement.doesNotContain=" + DEFAULT_MEASUREMENT);

        // Get all the userGoalsList where measurement does not contain UPDATED_MEASUREMENT
        defaultUserGoalsShouldBeFound("measurement.doesNotContain=" + UPDATED_MEASUREMENT);
    }

    @Test
    @Transactional
    void getAllUserGoalsByWeightageIsEqualToSomething() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where weightage equals to DEFAULT_WEIGHTAGE
        defaultUserGoalsShouldBeFound("weightage.equals=" + DEFAULT_WEIGHTAGE);

        // Get all the userGoalsList where weightage equals to UPDATED_WEIGHTAGE
        defaultUserGoalsShouldNotBeFound("weightage.equals=" + UPDATED_WEIGHTAGE);
    }

    @Test
    @Transactional
    void getAllUserGoalsByWeightageIsInShouldWork() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where weightage in DEFAULT_WEIGHTAGE or UPDATED_WEIGHTAGE
        defaultUserGoalsShouldBeFound("weightage.in=" + DEFAULT_WEIGHTAGE + "," + UPDATED_WEIGHTAGE);

        // Get all the userGoalsList where weightage equals to UPDATED_WEIGHTAGE
        defaultUserGoalsShouldNotBeFound("weightage.in=" + UPDATED_WEIGHTAGE);
    }

    @Test
    @Transactional
    void getAllUserGoalsByWeightageIsNullOrNotNull() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where weightage is not null
        defaultUserGoalsShouldBeFound("weightage.specified=true");

        // Get all the userGoalsList where weightage is null
        defaultUserGoalsShouldNotBeFound("weightage.specified=false");
    }

    @Test
    @Transactional
    void getAllUserGoalsByWeightageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where weightage is greater than or equal to DEFAULT_WEIGHTAGE
        defaultUserGoalsShouldBeFound("weightage.greaterThanOrEqual=" + DEFAULT_WEIGHTAGE);

        // Get all the userGoalsList where weightage is greater than or equal to UPDATED_WEIGHTAGE
        defaultUserGoalsShouldNotBeFound("weightage.greaterThanOrEqual=" + UPDATED_WEIGHTAGE);
    }

    @Test
    @Transactional
    void getAllUserGoalsByWeightageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where weightage is less than or equal to DEFAULT_WEIGHTAGE
        defaultUserGoalsShouldBeFound("weightage.lessThanOrEqual=" + DEFAULT_WEIGHTAGE);

        // Get all the userGoalsList where weightage is less than or equal to SMALLER_WEIGHTAGE
        defaultUserGoalsShouldNotBeFound("weightage.lessThanOrEqual=" + SMALLER_WEIGHTAGE);
    }

    @Test
    @Transactional
    void getAllUserGoalsByWeightageIsLessThanSomething() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where weightage is less than DEFAULT_WEIGHTAGE
        defaultUserGoalsShouldNotBeFound("weightage.lessThan=" + DEFAULT_WEIGHTAGE);

        // Get all the userGoalsList where weightage is less than UPDATED_WEIGHTAGE
        defaultUserGoalsShouldBeFound("weightage.lessThan=" + UPDATED_WEIGHTAGE);
    }

    @Test
    @Transactional
    void getAllUserGoalsByWeightageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where weightage is greater than DEFAULT_WEIGHTAGE
        defaultUserGoalsShouldNotBeFound("weightage.greaterThan=" + DEFAULT_WEIGHTAGE);

        // Get all the userGoalsList where weightage is greater than SMALLER_WEIGHTAGE
        defaultUserGoalsShouldBeFound("weightage.greaterThan=" + SMALLER_WEIGHTAGE);
    }

    @Test
    @Transactional
    void getAllUserGoalsByProgressIsEqualToSomething() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where progress equals to DEFAULT_PROGRESS
        defaultUserGoalsShouldBeFound("progress.equals=" + DEFAULT_PROGRESS);

        // Get all the userGoalsList where progress equals to UPDATED_PROGRESS
        defaultUserGoalsShouldNotBeFound("progress.equals=" + UPDATED_PROGRESS);
    }

    @Test
    @Transactional
    void getAllUserGoalsByProgressIsInShouldWork() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where progress in DEFAULT_PROGRESS or UPDATED_PROGRESS
        defaultUserGoalsShouldBeFound("progress.in=" + DEFAULT_PROGRESS + "," + UPDATED_PROGRESS);

        // Get all the userGoalsList where progress equals to UPDATED_PROGRESS
        defaultUserGoalsShouldNotBeFound("progress.in=" + UPDATED_PROGRESS);
    }

    @Test
    @Transactional
    void getAllUserGoalsByProgressIsNullOrNotNull() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where progress is not null
        defaultUserGoalsShouldBeFound("progress.specified=true");

        // Get all the userGoalsList where progress is null
        defaultUserGoalsShouldNotBeFound("progress.specified=false");
    }

    @Test
    @Transactional
    void getAllUserGoalsByProgressIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where progress is greater than or equal to DEFAULT_PROGRESS
        defaultUserGoalsShouldBeFound("progress.greaterThanOrEqual=" + DEFAULT_PROGRESS);

        // Get all the userGoalsList where progress is greater than or equal to UPDATED_PROGRESS
        defaultUserGoalsShouldNotBeFound("progress.greaterThanOrEqual=" + UPDATED_PROGRESS);
    }

    @Test
    @Transactional
    void getAllUserGoalsByProgressIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where progress is less than or equal to DEFAULT_PROGRESS
        defaultUserGoalsShouldBeFound("progress.lessThanOrEqual=" + DEFAULT_PROGRESS);

        // Get all the userGoalsList where progress is less than or equal to SMALLER_PROGRESS
        defaultUserGoalsShouldNotBeFound("progress.lessThanOrEqual=" + SMALLER_PROGRESS);
    }

    @Test
    @Transactional
    void getAllUserGoalsByProgressIsLessThanSomething() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where progress is less than DEFAULT_PROGRESS
        defaultUserGoalsShouldNotBeFound("progress.lessThan=" + DEFAULT_PROGRESS);

        // Get all the userGoalsList where progress is less than UPDATED_PROGRESS
        defaultUserGoalsShouldBeFound("progress.lessThan=" + UPDATED_PROGRESS);
    }

    @Test
    @Transactional
    void getAllUserGoalsByProgressIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where progress is greater than DEFAULT_PROGRESS
        defaultUserGoalsShouldNotBeFound("progress.greaterThan=" + DEFAULT_PROGRESS);

        // Get all the userGoalsList where progress is greater than SMALLER_PROGRESS
        defaultUserGoalsShouldBeFound("progress.greaterThan=" + SMALLER_PROGRESS);
    }

    @Test
    @Transactional
    void getAllUserGoalsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where status equals to DEFAULT_STATUS
        defaultUserGoalsShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the userGoalsList where status equals to UPDATED_STATUS
        defaultUserGoalsShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllUserGoalsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultUserGoalsShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the userGoalsList where status equals to UPDATED_STATUS
        defaultUserGoalsShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllUserGoalsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where status is not null
        defaultUserGoalsShouldBeFound("status.specified=true");

        // Get all the userGoalsList where status is null
        defaultUserGoalsShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllUserGoalsByStatusContainsSomething() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where status contains DEFAULT_STATUS
        defaultUserGoalsShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the userGoalsList where status contains UPDATED_STATUS
        defaultUserGoalsShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllUserGoalsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where status does not contain DEFAULT_STATUS
        defaultUserGoalsShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the userGoalsList where status does not contain UPDATED_STATUS
        defaultUserGoalsShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllUserGoalsByDueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where dueDate equals to DEFAULT_DUE_DATE
        defaultUserGoalsShouldBeFound("dueDate.equals=" + DEFAULT_DUE_DATE);

        // Get all the userGoalsList where dueDate equals to UPDATED_DUE_DATE
        defaultUserGoalsShouldNotBeFound("dueDate.equals=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllUserGoalsByDueDateIsInShouldWork() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where dueDate in DEFAULT_DUE_DATE or UPDATED_DUE_DATE
        defaultUserGoalsShouldBeFound("dueDate.in=" + DEFAULT_DUE_DATE + "," + UPDATED_DUE_DATE);

        // Get all the userGoalsList where dueDate equals to UPDATED_DUE_DATE
        defaultUserGoalsShouldNotBeFound("dueDate.in=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllUserGoalsByDueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where dueDate is not null
        defaultUserGoalsShouldBeFound("dueDate.specified=true");

        // Get all the userGoalsList where dueDate is null
        defaultUserGoalsShouldNotBeFound("dueDate.specified=false");
    }

    @Test
    @Transactional
    void getAllUserGoalsByDueDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where dueDate is greater than or equal to DEFAULT_DUE_DATE
        defaultUserGoalsShouldBeFound("dueDate.greaterThanOrEqual=" + DEFAULT_DUE_DATE);

        // Get all the userGoalsList where dueDate is greater than or equal to UPDATED_DUE_DATE
        defaultUserGoalsShouldNotBeFound("dueDate.greaterThanOrEqual=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllUserGoalsByDueDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where dueDate is less than or equal to DEFAULT_DUE_DATE
        defaultUserGoalsShouldBeFound("dueDate.lessThanOrEqual=" + DEFAULT_DUE_DATE);

        // Get all the userGoalsList where dueDate is less than or equal to SMALLER_DUE_DATE
        defaultUserGoalsShouldNotBeFound("dueDate.lessThanOrEqual=" + SMALLER_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllUserGoalsByDueDateIsLessThanSomething() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where dueDate is less than DEFAULT_DUE_DATE
        defaultUserGoalsShouldNotBeFound("dueDate.lessThan=" + DEFAULT_DUE_DATE);

        // Get all the userGoalsList where dueDate is less than UPDATED_DUE_DATE
        defaultUserGoalsShouldBeFound("dueDate.lessThan=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllUserGoalsByDueDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where dueDate is greater than DEFAULT_DUE_DATE
        defaultUserGoalsShouldNotBeFound("dueDate.greaterThan=" + DEFAULT_DUE_DATE);

        // Get all the userGoalsList where dueDate is greater than SMALLER_DUE_DATE
        defaultUserGoalsShouldBeFound("dueDate.greaterThan=" + SMALLER_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllUserGoalsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where createdAt equals to DEFAULT_CREATED_AT
        defaultUserGoalsShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the userGoalsList where createdAt equals to UPDATED_CREATED_AT
        defaultUserGoalsShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllUserGoalsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultUserGoalsShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the userGoalsList where createdAt equals to UPDATED_CREATED_AT
        defaultUserGoalsShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllUserGoalsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where createdAt is not null
        defaultUserGoalsShouldBeFound("createdAt.specified=true");

        // Get all the userGoalsList where createdAt is null
        defaultUserGoalsShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllUserGoalsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultUserGoalsShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the userGoalsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultUserGoalsShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllUserGoalsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultUserGoalsShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the userGoalsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultUserGoalsShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllUserGoalsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where updatedAt is not null
        defaultUserGoalsShouldBeFound("updatedAt.specified=true");

        // Get all the userGoalsList where updatedAt is null
        defaultUserGoalsShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllUserGoalsByDeletedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where deletedAt equals to DEFAULT_DELETED_AT
        defaultUserGoalsShouldBeFound("deletedAt.equals=" + DEFAULT_DELETED_AT);

        // Get all the userGoalsList where deletedAt equals to UPDATED_DELETED_AT
        defaultUserGoalsShouldNotBeFound("deletedAt.equals=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllUserGoalsByDeletedAtIsInShouldWork() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where deletedAt in DEFAULT_DELETED_AT or UPDATED_DELETED_AT
        defaultUserGoalsShouldBeFound("deletedAt.in=" + DEFAULT_DELETED_AT + "," + UPDATED_DELETED_AT);

        // Get all the userGoalsList where deletedAt equals to UPDATED_DELETED_AT
        defaultUserGoalsShouldNotBeFound("deletedAt.in=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllUserGoalsByDeletedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where deletedAt is not null
        defaultUserGoalsShouldBeFound("deletedAt.specified=true");

        // Get all the userGoalsList where deletedAt is null
        defaultUserGoalsShouldNotBeFound("deletedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllUserGoalsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where version equals to DEFAULT_VERSION
        defaultUserGoalsShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the userGoalsList where version equals to UPDATED_VERSION
        defaultUserGoalsShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllUserGoalsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultUserGoalsShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the userGoalsList where version equals to UPDATED_VERSION
        defaultUserGoalsShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllUserGoalsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where version is not null
        defaultUserGoalsShouldBeFound("version.specified=true");

        // Get all the userGoalsList where version is null
        defaultUserGoalsShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllUserGoalsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where version is greater than or equal to DEFAULT_VERSION
        defaultUserGoalsShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the userGoalsList where version is greater than or equal to UPDATED_VERSION
        defaultUserGoalsShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllUserGoalsByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where version is less than or equal to DEFAULT_VERSION
        defaultUserGoalsShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the userGoalsList where version is less than or equal to SMALLER_VERSION
        defaultUserGoalsShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllUserGoalsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where version is less than DEFAULT_VERSION
        defaultUserGoalsShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the userGoalsList where version is less than UPDATED_VERSION
        defaultUserGoalsShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllUserGoalsByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList where version is greater than DEFAULT_VERSION
        defaultUserGoalsShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the userGoalsList where version is greater than SMALLER_VERSION
        defaultUserGoalsShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllUserGoalsByUserIsEqualToSomething() throws Exception {
        Employees user;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            userGoalsRepository.saveAndFlush(userGoals);
            user = EmployeesResourceIT.createEntity(em);
        } else {
            user = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(user);
        em.flush();
        userGoals.setUser(user);
        userGoalsRepository.saveAndFlush(userGoals);
        Long userId = user.getId();

        // Get all the userGoalsList where user equals to userId
        defaultUserGoalsShouldBeFound("userId.equals=" + userId);

        // Get all the userGoalsList where user equals to (userId + 1)
        defaultUserGoalsShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    @Test
    @Transactional
    void getAllUserGoalsByReferenceGoalIsEqualToSomething() throws Exception {
        Goals referenceGoal;
        if (TestUtil.findAll(em, Goals.class).isEmpty()) {
            userGoalsRepository.saveAndFlush(userGoals);
            referenceGoal = GoalsResourceIT.createEntity(em);
        } else {
            referenceGoal = TestUtil.findAll(em, Goals.class).get(0);
        }
        em.persist(referenceGoal);
        em.flush();
        userGoals.setReferenceGoal(referenceGoal);
        userGoalsRepository.saveAndFlush(userGoals);
        Long referenceGoalId = referenceGoal.getId();

        // Get all the userGoalsList where referenceGoal equals to referenceGoalId
        defaultUserGoalsShouldBeFound("referenceGoalId.equals=" + referenceGoalId);

        // Get all the userGoalsList where referenceGoal equals to (referenceGoalId + 1)
        defaultUserGoalsShouldNotBeFound("referenceGoalId.equals=" + (referenceGoalId + 1));
    }

    @Test
    @Transactional
    void getAllUserGoalsByUsergoalraterattributesUsergoalIsEqualToSomething() throws Exception {
        UserGoalRaterAttributes usergoalraterattributesUsergoal;
        if (TestUtil.findAll(em, UserGoalRaterAttributes.class).isEmpty()) {
            userGoalsRepository.saveAndFlush(userGoals);
            usergoalraterattributesUsergoal = UserGoalRaterAttributesResourceIT.createEntity(em);
        } else {
            usergoalraterattributesUsergoal = TestUtil.findAll(em, UserGoalRaterAttributes.class).get(0);
        }
        em.persist(usergoalraterattributesUsergoal);
        em.flush();
        userGoals.addUsergoalraterattributesUsergoal(usergoalraterattributesUsergoal);
        userGoalsRepository.saveAndFlush(userGoals);
        Long usergoalraterattributesUsergoalId = usergoalraterattributesUsergoal.getId();

        // Get all the userGoalsList where usergoalraterattributesUsergoal equals to usergoalraterattributesUsergoalId
        defaultUserGoalsShouldBeFound("usergoalraterattributesUsergoalId.equals=" + usergoalraterattributesUsergoalId);

        // Get all the userGoalsList where usergoalraterattributesUsergoal equals to (usergoalraterattributesUsergoalId + 1)
        defaultUserGoalsShouldNotBeFound("usergoalraterattributesUsergoalId.equals=" + (usergoalraterattributesUsergoalId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserGoalsShouldBeFound(String filter) throws Exception {
        restUserGoalsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userGoals.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].measurement").value(hasItem(DEFAULT_MEASUREMENT)))
            .andExpect(jsonPath("$.[*].weightage").value(hasItem(DEFAULT_WEIGHTAGE)))
            .andExpect(jsonPath("$.[*].progress").value(hasItem(DEFAULT_PROGRESS)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restUserGoalsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUserGoalsShouldNotBeFound(String filter) throws Exception {
        restUserGoalsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserGoalsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingUserGoals() throws Exception {
        // Get the userGoals
        restUserGoalsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUserGoals() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        int databaseSizeBeforeUpdate = userGoalsRepository.findAll().size();

        // Update the userGoals
        UserGoals updatedUserGoals = userGoalsRepository.findById(userGoals.getId()).get();
        // Disconnect from session so that the updates on updatedUserGoals are not directly saved in db
        em.detach(updatedUserGoals);
        updatedUserGoals
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .measurement(UPDATED_MEASUREMENT)
            .weightage(UPDATED_WEIGHTAGE)
            .progress(UPDATED_PROGRESS)
            .status(UPDATED_STATUS)
            .dueDate(UPDATED_DUE_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restUserGoalsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUserGoals.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUserGoals))
            )
            .andExpect(status().isOk());

        // Validate the UserGoals in the database
        List<UserGoals> userGoalsList = userGoalsRepository.findAll();
        assertThat(userGoalsList).hasSize(databaseSizeBeforeUpdate);
        UserGoals testUserGoals = userGoalsList.get(userGoalsList.size() - 1);
        assertThat(testUserGoals.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testUserGoals.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testUserGoals.getMeasurement()).isEqualTo(UPDATED_MEASUREMENT);
        assertThat(testUserGoals.getWeightage()).isEqualTo(UPDATED_WEIGHTAGE);
        assertThat(testUserGoals.getProgress()).isEqualTo(UPDATED_PROGRESS);
        assertThat(testUserGoals.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUserGoals.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testUserGoals.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testUserGoals.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testUserGoals.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testUserGoals.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingUserGoals() throws Exception {
        int databaseSizeBeforeUpdate = userGoalsRepository.findAll().size();
        userGoals.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserGoalsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userGoals.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userGoals))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserGoals in the database
        List<UserGoals> userGoalsList = userGoalsRepository.findAll();
        assertThat(userGoalsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserGoals() throws Exception {
        int databaseSizeBeforeUpdate = userGoalsRepository.findAll().size();
        userGoals.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserGoalsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userGoals))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserGoals in the database
        List<UserGoals> userGoalsList = userGoalsRepository.findAll();
        assertThat(userGoalsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserGoals() throws Exception {
        int databaseSizeBeforeUpdate = userGoalsRepository.findAll().size();
        userGoals.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserGoalsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userGoals))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserGoals in the database
        List<UserGoals> userGoalsList = userGoalsRepository.findAll();
        assertThat(userGoalsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserGoalsWithPatch() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        int databaseSizeBeforeUpdate = userGoalsRepository.findAll().size();

        // Update the userGoals using partial update
        UserGoals partialUpdatedUserGoals = new UserGoals();
        partialUpdatedUserGoals.setId(userGoals.getId());

        partialUpdatedUserGoals
            .description(UPDATED_DESCRIPTION)
            .measurement(UPDATED_MEASUREMENT)
            .weightage(UPDATED_WEIGHTAGE)
            .progress(UPDATED_PROGRESS)
            .dueDate(UPDATED_DUE_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT);

        restUserGoalsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserGoals.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserGoals))
            )
            .andExpect(status().isOk());

        // Validate the UserGoals in the database
        List<UserGoals> userGoalsList = userGoalsRepository.findAll();
        assertThat(userGoalsList).hasSize(databaseSizeBeforeUpdate);
        UserGoals testUserGoals = userGoalsList.get(userGoalsList.size() - 1);
        assertThat(testUserGoals.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testUserGoals.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testUserGoals.getMeasurement()).isEqualTo(UPDATED_MEASUREMENT);
        assertThat(testUserGoals.getWeightage()).isEqualTo(UPDATED_WEIGHTAGE);
        assertThat(testUserGoals.getProgress()).isEqualTo(UPDATED_PROGRESS);
        assertThat(testUserGoals.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUserGoals.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testUserGoals.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testUserGoals.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testUserGoals.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testUserGoals.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateUserGoalsWithPatch() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        int databaseSizeBeforeUpdate = userGoalsRepository.findAll().size();

        // Update the userGoals using partial update
        UserGoals partialUpdatedUserGoals = new UserGoals();
        partialUpdatedUserGoals.setId(userGoals.getId());

        partialUpdatedUserGoals
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .measurement(UPDATED_MEASUREMENT)
            .weightage(UPDATED_WEIGHTAGE)
            .progress(UPDATED_PROGRESS)
            .status(UPDATED_STATUS)
            .dueDate(UPDATED_DUE_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restUserGoalsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserGoals.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserGoals))
            )
            .andExpect(status().isOk());

        // Validate the UserGoals in the database
        List<UserGoals> userGoalsList = userGoalsRepository.findAll();
        assertThat(userGoalsList).hasSize(databaseSizeBeforeUpdate);
        UserGoals testUserGoals = userGoalsList.get(userGoalsList.size() - 1);
        assertThat(testUserGoals.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testUserGoals.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testUserGoals.getMeasurement()).isEqualTo(UPDATED_MEASUREMENT);
        assertThat(testUserGoals.getWeightage()).isEqualTo(UPDATED_WEIGHTAGE);
        assertThat(testUserGoals.getProgress()).isEqualTo(UPDATED_PROGRESS);
        assertThat(testUserGoals.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUserGoals.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testUserGoals.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testUserGoals.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testUserGoals.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testUserGoals.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingUserGoals() throws Exception {
        int databaseSizeBeforeUpdate = userGoalsRepository.findAll().size();
        userGoals.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserGoalsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userGoals.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userGoals))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserGoals in the database
        List<UserGoals> userGoalsList = userGoalsRepository.findAll();
        assertThat(userGoalsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserGoals() throws Exception {
        int databaseSizeBeforeUpdate = userGoalsRepository.findAll().size();
        userGoals.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserGoalsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userGoals))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserGoals in the database
        List<UserGoals> userGoalsList = userGoalsRepository.findAll();
        assertThat(userGoalsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserGoals() throws Exception {
        int databaseSizeBeforeUpdate = userGoalsRepository.findAll().size();
        userGoals.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserGoalsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userGoals))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserGoals in the database
        List<UserGoals> userGoalsList = userGoalsRepository.findAll();
        assertThat(userGoalsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserGoals() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        int databaseSizeBeforeDelete = userGoalsRepository.findAll().size();

        // Delete the userGoals
        restUserGoalsMockMvc
            .perform(delete(ENTITY_API_URL_ID, userGoals.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserGoals> userGoalsList = userGoalsRepository.findAll();
        assertThat(userGoalsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
