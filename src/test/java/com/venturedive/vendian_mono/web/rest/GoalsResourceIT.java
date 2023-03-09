package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.GoalGroupMappings;
import com.venturedive.vendian_mono.domain.Goals;
import com.venturedive.vendian_mono.domain.UserGoals;
import com.venturedive.vendian_mono.repository.GoalsRepository;
import com.venturedive.vendian_mono.service.criteria.GoalsCriteria;
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
 * Integration tests for the {@link GoalsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GoalsResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_MEASUREMENT = "AAAAAAAAAA";
    private static final String UPDATED_MEASUREMENT = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;
    private static final Integer SMALLER_VERSION = 1 - 1;

    private static final String ENTITY_API_URL = "/api/goals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GoalsRepository goalsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGoalsMockMvc;

    private Goals goals;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Goals createEntity(EntityManager em) {
        Goals goals = new Goals()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .measurement(DEFAULT_MEASUREMENT)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT)
            .version(DEFAULT_VERSION);
        return goals;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Goals createUpdatedEntity(EntityManager em) {
        Goals goals = new Goals()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .measurement(UPDATED_MEASUREMENT)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);
        return goals;
    }

    @BeforeEach
    public void initTest() {
        goals = createEntity(em);
    }

    @Test
    @Transactional
    void createGoals() throws Exception {
        int databaseSizeBeforeCreate = goalsRepository.findAll().size();
        // Create the Goals
        restGoalsMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(goals))
            )
            .andExpect(status().isCreated());

        // Validate the Goals in the database
        List<Goals> goalsList = goalsRepository.findAll();
        assertThat(goalsList).hasSize(databaseSizeBeforeCreate + 1);
        Goals testGoals = goalsList.get(goalsList.size() - 1);
        assertThat(testGoals.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testGoals.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testGoals.getMeasurement()).isEqualTo(DEFAULT_MEASUREMENT);
        assertThat(testGoals.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testGoals.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testGoals.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testGoals.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createGoalsWithExistingId() throws Exception {
        // Create the Goals with an existing ID
        goals.setId(1L);

        int databaseSizeBeforeCreate = goalsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGoalsMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(goals))
            )
            .andExpect(status().isBadRequest());

        // Validate the Goals in the database
        List<Goals> goalsList = goalsRepository.findAll();
        assertThat(goalsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = goalsRepository.findAll().size();
        // set the field null
        goals.setTitle(null);

        // Create the Goals, which fails.

        restGoalsMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(goals))
            )
            .andExpect(status().isBadRequest());

        List<Goals> goalsList = goalsRepository.findAll();
        assertThat(goalsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = goalsRepository.findAll().size();
        // set the field null
        goals.setCreatedAt(null);

        // Create the Goals, which fails.

        restGoalsMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(goals))
            )
            .andExpect(status().isBadRequest());

        List<Goals> goalsList = goalsRepository.findAll();
        assertThat(goalsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = goalsRepository.findAll().size();
        // set the field null
        goals.setUpdatedAt(null);

        // Create the Goals, which fails.

        restGoalsMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(goals))
            )
            .andExpect(status().isBadRequest());

        List<Goals> goalsList = goalsRepository.findAll();
        assertThat(goalsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = goalsRepository.findAll().size();
        // set the field null
        goals.setVersion(null);

        // Create the Goals, which fails.

        restGoalsMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(goals))
            )
            .andExpect(status().isBadRequest());

        List<Goals> goalsList = goalsRepository.findAll();
        assertThat(goalsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGoals() throws Exception {
        // Initialize the database
        goalsRepository.saveAndFlush(goals);

        // Get all the goalsList
        restGoalsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(goals.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].measurement").value(hasItem(DEFAULT_MEASUREMENT)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getGoals() throws Exception {
        // Initialize the database
        goalsRepository.saveAndFlush(goals);

        // Get the goals
        restGoalsMockMvc
            .perform(get(ENTITY_API_URL_ID, goals.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(goals.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.measurement").value(DEFAULT_MEASUREMENT))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getGoalsByIdFiltering() throws Exception {
        // Initialize the database
        goalsRepository.saveAndFlush(goals);

        Long id = goals.getId();

        defaultGoalsShouldBeFound("id.equals=" + id);
        defaultGoalsShouldNotBeFound("id.notEquals=" + id);

        defaultGoalsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultGoalsShouldNotBeFound("id.greaterThan=" + id);

        defaultGoalsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultGoalsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllGoalsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        goalsRepository.saveAndFlush(goals);

        // Get all the goalsList where title equals to DEFAULT_TITLE
        defaultGoalsShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the goalsList where title equals to UPDATED_TITLE
        defaultGoalsShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllGoalsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        goalsRepository.saveAndFlush(goals);

        // Get all the goalsList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultGoalsShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the goalsList where title equals to UPDATED_TITLE
        defaultGoalsShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllGoalsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        goalsRepository.saveAndFlush(goals);

        // Get all the goalsList where title is not null
        defaultGoalsShouldBeFound("title.specified=true");

        // Get all the goalsList where title is null
        defaultGoalsShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllGoalsByTitleContainsSomething() throws Exception {
        // Initialize the database
        goalsRepository.saveAndFlush(goals);

        // Get all the goalsList where title contains DEFAULT_TITLE
        defaultGoalsShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the goalsList where title contains UPDATED_TITLE
        defaultGoalsShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllGoalsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        goalsRepository.saveAndFlush(goals);

        // Get all the goalsList where title does not contain DEFAULT_TITLE
        defaultGoalsShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the goalsList where title does not contain UPDATED_TITLE
        defaultGoalsShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllGoalsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        goalsRepository.saveAndFlush(goals);

        // Get all the goalsList where description equals to DEFAULT_DESCRIPTION
        defaultGoalsShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the goalsList where description equals to UPDATED_DESCRIPTION
        defaultGoalsShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllGoalsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        goalsRepository.saveAndFlush(goals);

        // Get all the goalsList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultGoalsShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the goalsList where description equals to UPDATED_DESCRIPTION
        defaultGoalsShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllGoalsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        goalsRepository.saveAndFlush(goals);

        // Get all the goalsList where description is not null
        defaultGoalsShouldBeFound("description.specified=true");

        // Get all the goalsList where description is null
        defaultGoalsShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllGoalsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        goalsRepository.saveAndFlush(goals);

        // Get all the goalsList where description contains DEFAULT_DESCRIPTION
        defaultGoalsShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the goalsList where description contains UPDATED_DESCRIPTION
        defaultGoalsShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllGoalsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        goalsRepository.saveAndFlush(goals);

        // Get all the goalsList where description does not contain DEFAULT_DESCRIPTION
        defaultGoalsShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the goalsList where description does not contain UPDATED_DESCRIPTION
        defaultGoalsShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllGoalsByMeasurementIsEqualToSomething() throws Exception {
        // Initialize the database
        goalsRepository.saveAndFlush(goals);

        // Get all the goalsList where measurement equals to DEFAULT_MEASUREMENT
        defaultGoalsShouldBeFound("measurement.equals=" + DEFAULT_MEASUREMENT);

        // Get all the goalsList where measurement equals to UPDATED_MEASUREMENT
        defaultGoalsShouldNotBeFound("measurement.equals=" + UPDATED_MEASUREMENT);
    }

    @Test
    @Transactional
    void getAllGoalsByMeasurementIsInShouldWork() throws Exception {
        // Initialize the database
        goalsRepository.saveAndFlush(goals);

        // Get all the goalsList where measurement in DEFAULT_MEASUREMENT or UPDATED_MEASUREMENT
        defaultGoalsShouldBeFound("measurement.in=" + DEFAULT_MEASUREMENT + "," + UPDATED_MEASUREMENT);

        // Get all the goalsList where measurement equals to UPDATED_MEASUREMENT
        defaultGoalsShouldNotBeFound("measurement.in=" + UPDATED_MEASUREMENT);
    }

    @Test
    @Transactional
    void getAllGoalsByMeasurementIsNullOrNotNull() throws Exception {
        // Initialize the database
        goalsRepository.saveAndFlush(goals);

        // Get all the goalsList where measurement is not null
        defaultGoalsShouldBeFound("measurement.specified=true");

        // Get all the goalsList where measurement is null
        defaultGoalsShouldNotBeFound("measurement.specified=false");
    }

    @Test
    @Transactional
    void getAllGoalsByMeasurementContainsSomething() throws Exception {
        // Initialize the database
        goalsRepository.saveAndFlush(goals);

        // Get all the goalsList where measurement contains DEFAULT_MEASUREMENT
        defaultGoalsShouldBeFound("measurement.contains=" + DEFAULT_MEASUREMENT);

        // Get all the goalsList where measurement contains UPDATED_MEASUREMENT
        defaultGoalsShouldNotBeFound("measurement.contains=" + UPDATED_MEASUREMENT);
    }

    @Test
    @Transactional
    void getAllGoalsByMeasurementNotContainsSomething() throws Exception {
        // Initialize the database
        goalsRepository.saveAndFlush(goals);

        // Get all the goalsList where measurement does not contain DEFAULT_MEASUREMENT
        defaultGoalsShouldNotBeFound("measurement.doesNotContain=" + DEFAULT_MEASUREMENT);

        // Get all the goalsList where measurement does not contain UPDATED_MEASUREMENT
        defaultGoalsShouldBeFound("measurement.doesNotContain=" + UPDATED_MEASUREMENT);
    }

    @Test
    @Transactional
    void getAllGoalsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        goalsRepository.saveAndFlush(goals);

        // Get all the goalsList where createdAt equals to DEFAULT_CREATED_AT
        defaultGoalsShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the goalsList where createdAt equals to UPDATED_CREATED_AT
        defaultGoalsShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllGoalsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        goalsRepository.saveAndFlush(goals);

        // Get all the goalsList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultGoalsShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the goalsList where createdAt equals to UPDATED_CREATED_AT
        defaultGoalsShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllGoalsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        goalsRepository.saveAndFlush(goals);

        // Get all the goalsList where createdAt is not null
        defaultGoalsShouldBeFound("createdAt.specified=true");

        // Get all the goalsList where createdAt is null
        defaultGoalsShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllGoalsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        goalsRepository.saveAndFlush(goals);

        // Get all the goalsList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultGoalsShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the goalsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultGoalsShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllGoalsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        goalsRepository.saveAndFlush(goals);

        // Get all the goalsList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultGoalsShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the goalsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultGoalsShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllGoalsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        goalsRepository.saveAndFlush(goals);

        // Get all the goalsList where updatedAt is not null
        defaultGoalsShouldBeFound("updatedAt.specified=true");

        // Get all the goalsList where updatedAt is null
        defaultGoalsShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllGoalsByDeletedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        goalsRepository.saveAndFlush(goals);

        // Get all the goalsList where deletedAt equals to DEFAULT_DELETED_AT
        defaultGoalsShouldBeFound("deletedAt.equals=" + DEFAULT_DELETED_AT);

        // Get all the goalsList where deletedAt equals to UPDATED_DELETED_AT
        defaultGoalsShouldNotBeFound("deletedAt.equals=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllGoalsByDeletedAtIsInShouldWork() throws Exception {
        // Initialize the database
        goalsRepository.saveAndFlush(goals);

        // Get all the goalsList where deletedAt in DEFAULT_DELETED_AT or UPDATED_DELETED_AT
        defaultGoalsShouldBeFound("deletedAt.in=" + DEFAULT_DELETED_AT + "," + UPDATED_DELETED_AT);

        // Get all the goalsList where deletedAt equals to UPDATED_DELETED_AT
        defaultGoalsShouldNotBeFound("deletedAt.in=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllGoalsByDeletedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        goalsRepository.saveAndFlush(goals);

        // Get all the goalsList where deletedAt is not null
        defaultGoalsShouldBeFound("deletedAt.specified=true");

        // Get all the goalsList where deletedAt is null
        defaultGoalsShouldNotBeFound("deletedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllGoalsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        goalsRepository.saveAndFlush(goals);

        // Get all the goalsList where version equals to DEFAULT_VERSION
        defaultGoalsShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the goalsList where version equals to UPDATED_VERSION
        defaultGoalsShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllGoalsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        goalsRepository.saveAndFlush(goals);

        // Get all the goalsList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultGoalsShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the goalsList where version equals to UPDATED_VERSION
        defaultGoalsShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllGoalsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        goalsRepository.saveAndFlush(goals);

        // Get all the goalsList where version is not null
        defaultGoalsShouldBeFound("version.specified=true");

        // Get all the goalsList where version is null
        defaultGoalsShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllGoalsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        goalsRepository.saveAndFlush(goals);

        // Get all the goalsList where version is greater than or equal to DEFAULT_VERSION
        defaultGoalsShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the goalsList where version is greater than or equal to UPDATED_VERSION
        defaultGoalsShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllGoalsByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        goalsRepository.saveAndFlush(goals);

        // Get all the goalsList where version is less than or equal to DEFAULT_VERSION
        defaultGoalsShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the goalsList where version is less than or equal to SMALLER_VERSION
        defaultGoalsShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllGoalsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        goalsRepository.saveAndFlush(goals);

        // Get all the goalsList where version is less than DEFAULT_VERSION
        defaultGoalsShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the goalsList where version is less than UPDATED_VERSION
        defaultGoalsShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllGoalsByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        goalsRepository.saveAndFlush(goals);

        // Get all the goalsList where version is greater than DEFAULT_VERSION
        defaultGoalsShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the goalsList where version is greater than SMALLER_VERSION
        defaultGoalsShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllGoalsByGoalgroupmappingsGoalIsEqualToSomething() throws Exception {
        GoalGroupMappings goalgroupmappingsGoal;
        if (TestUtil.findAll(em, GoalGroupMappings.class).isEmpty()) {
            goalsRepository.saveAndFlush(goals);
            goalgroupmappingsGoal = GoalGroupMappingsResourceIT.createEntity(em);
        } else {
            goalgroupmappingsGoal = TestUtil.findAll(em, GoalGroupMappings.class).get(0);
        }
        em.persist(goalgroupmappingsGoal);
        em.flush();
        goals.addGoalgroupmappingsGoal(goalgroupmappingsGoal);
        goalsRepository.saveAndFlush(goals);
        Long goalgroupmappingsGoalId = goalgroupmappingsGoal.getId();

        // Get all the goalsList where goalgroupmappingsGoal equals to goalgroupmappingsGoalId
        defaultGoalsShouldBeFound("goalgroupmappingsGoalId.equals=" + goalgroupmappingsGoalId);

        // Get all the goalsList where goalgroupmappingsGoal equals to (goalgroupmappingsGoalId + 1)
        defaultGoalsShouldNotBeFound("goalgroupmappingsGoalId.equals=" + (goalgroupmappingsGoalId + 1));
    }

    @Test
    @Transactional
    void getAllGoalsByUsergoalsReferencegoalIsEqualToSomething() throws Exception {
        UserGoals usergoalsReferencegoal;
        if (TestUtil.findAll(em, UserGoals.class).isEmpty()) {
            goalsRepository.saveAndFlush(goals);
            usergoalsReferencegoal = UserGoalsResourceIT.createEntity(em);
        } else {
            usergoalsReferencegoal = TestUtil.findAll(em, UserGoals.class).get(0);
        }
        em.persist(usergoalsReferencegoal);
        em.flush();
        goals.addUsergoalsReferencegoal(usergoalsReferencegoal);
        goalsRepository.saveAndFlush(goals);
        Long usergoalsReferencegoalId = usergoalsReferencegoal.getId();

        // Get all the goalsList where usergoalsReferencegoal equals to usergoalsReferencegoalId
        defaultGoalsShouldBeFound("usergoalsReferencegoalId.equals=" + usergoalsReferencegoalId);

        // Get all the goalsList where usergoalsReferencegoal equals to (usergoalsReferencegoalId + 1)
        defaultGoalsShouldNotBeFound("usergoalsReferencegoalId.equals=" + (usergoalsReferencegoalId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultGoalsShouldBeFound(String filter) throws Exception {
        restGoalsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(goals.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].measurement").value(hasItem(DEFAULT_MEASUREMENT)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restGoalsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultGoalsShouldNotBeFound(String filter) throws Exception {
        restGoalsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGoalsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingGoals() throws Exception {
        // Get the goals
        restGoalsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGoals() throws Exception {
        // Initialize the database
        goalsRepository.saveAndFlush(goals);

        int databaseSizeBeforeUpdate = goalsRepository.findAll().size();

        // Update the goals
        Goals updatedGoals = goalsRepository.findById(goals.getId()).get();
        // Disconnect from session so that the updates on updatedGoals are not directly saved in db
        em.detach(updatedGoals);
        updatedGoals
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .measurement(UPDATED_MEASUREMENT)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restGoalsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGoals.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGoals))
            )
            .andExpect(status().isOk());

        // Validate the Goals in the database
        List<Goals> goalsList = goalsRepository.findAll();
        assertThat(goalsList).hasSize(databaseSizeBeforeUpdate);
        Goals testGoals = goalsList.get(goalsList.size() - 1);
        assertThat(testGoals.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testGoals.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testGoals.getMeasurement()).isEqualTo(UPDATED_MEASUREMENT);
        assertThat(testGoals.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testGoals.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testGoals.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testGoals.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingGoals() throws Exception {
        int databaseSizeBeforeUpdate = goalsRepository.findAll().size();
        goals.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGoalsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, goals.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(goals))
            )
            .andExpect(status().isBadRequest());

        // Validate the Goals in the database
        List<Goals> goalsList = goalsRepository.findAll();
        assertThat(goalsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGoals() throws Exception {
        int databaseSizeBeforeUpdate = goalsRepository.findAll().size();
        goals.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGoalsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(goals))
            )
            .andExpect(status().isBadRequest());

        // Validate the Goals in the database
        List<Goals> goalsList = goalsRepository.findAll();
        assertThat(goalsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGoals() throws Exception {
        int databaseSizeBeforeUpdate = goalsRepository.findAll().size();
        goals.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGoalsMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(goals))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Goals in the database
        List<Goals> goalsList = goalsRepository.findAll();
        assertThat(goalsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGoalsWithPatch() throws Exception {
        // Initialize the database
        goalsRepository.saveAndFlush(goals);

        int databaseSizeBeforeUpdate = goalsRepository.findAll().size();

        // Update the goals using partial update
        Goals partialUpdatedGoals = new Goals();
        partialUpdatedGoals.setId(goals.getId());

        partialUpdatedGoals
            .measurement(UPDATED_MEASUREMENT)
            .createdAt(UPDATED_CREATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restGoalsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGoals.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGoals))
            )
            .andExpect(status().isOk());

        // Validate the Goals in the database
        List<Goals> goalsList = goalsRepository.findAll();
        assertThat(goalsList).hasSize(databaseSizeBeforeUpdate);
        Goals testGoals = goalsList.get(goalsList.size() - 1);
        assertThat(testGoals.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testGoals.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testGoals.getMeasurement()).isEqualTo(UPDATED_MEASUREMENT);
        assertThat(testGoals.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testGoals.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testGoals.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testGoals.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateGoalsWithPatch() throws Exception {
        // Initialize the database
        goalsRepository.saveAndFlush(goals);

        int databaseSizeBeforeUpdate = goalsRepository.findAll().size();

        // Update the goals using partial update
        Goals partialUpdatedGoals = new Goals();
        partialUpdatedGoals.setId(goals.getId());

        partialUpdatedGoals
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .measurement(UPDATED_MEASUREMENT)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restGoalsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGoals.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGoals))
            )
            .andExpect(status().isOk());

        // Validate the Goals in the database
        List<Goals> goalsList = goalsRepository.findAll();
        assertThat(goalsList).hasSize(databaseSizeBeforeUpdate);
        Goals testGoals = goalsList.get(goalsList.size() - 1);
        assertThat(testGoals.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testGoals.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testGoals.getMeasurement()).isEqualTo(UPDATED_MEASUREMENT);
        assertThat(testGoals.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testGoals.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testGoals.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testGoals.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingGoals() throws Exception {
        int databaseSizeBeforeUpdate = goalsRepository.findAll().size();
        goals.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGoalsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, goals.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(goals))
            )
            .andExpect(status().isBadRequest());

        // Validate the Goals in the database
        List<Goals> goalsList = goalsRepository.findAll();
        assertThat(goalsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGoals() throws Exception {
        int databaseSizeBeforeUpdate = goalsRepository.findAll().size();
        goals.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGoalsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(goals))
            )
            .andExpect(status().isBadRequest());

        // Validate the Goals in the database
        List<Goals> goalsList = goalsRepository.findAll();
        assertThat(goalsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGoals() throws Exception {
        int databaseSizeBeforeUpdate = goalsRepository.findAll().size();
        goals.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGoalsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(goals))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Goals in the database
        List<Goals> goalsList = goalsRepository.findAll();
        assertThat(goalsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGoals() throws Exception {
        // Initialize the database
        goalsRepository.saveAndFlush(goals);

        int databaseSizeBeforeDelete = goalsRepository.findAll().size();

        // Delete the goals
        restGoalsMockMvc
            .perform(delete(ENTITY_API_URL_ID, goals.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Goals> goalsList = goalsRepository.findAll();
        assertThat(goalsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
