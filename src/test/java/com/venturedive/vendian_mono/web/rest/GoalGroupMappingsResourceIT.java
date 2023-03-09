package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.GoalGroupMappings;
import com.venturedive.vendian_mono.domain.GoalGroups;
import com.venturedive.vendian_mono.domain.Goals;
import com.venturedive.vendian_mono.repository.GoalGroupMappingsRepository;
import com.venturedive.vendian_mono.service.criteria.GoalGroupMappingsCriteria;
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
 * Integration tests for the {@link GoalGroupMappingsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GoalGroupMappingsResourceIT {

    private static final Integer DEFAULT_WEIGHTAGE = 1;
    private static final Integer UPDATED_WEIGHTAGE = 2;
    private static final Integer SMALLER_WEIGHTAGE = 1 - 1;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;
    private static final Integer SMALLER_VERSION = 1 - 1;

    private static final String ENTITY_API_URL = "/api/goal-group-mappings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GoalGroupMappingsRepository goalGroupMappingsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGoalGroupMappingsMockMvc;

    private GoalGroupMappings goalGroupMappings;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GoalGroupMappings createEntity(EntityManager em) {
        GoalGroupMappings goalGroupMappings = new GoalGroupMappings()
            .weightage(DEFAULT_WEIGHTAGE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT)
            .version(DEFAULT_VERSION);
        // Add required entity
        GoalGroups goalGroups;
        if (TestUtil.findAll(em, GoalGroups.class).isEmpty()) {
            goalGroups = GoalGroupsResourceIT.createEntity(em);
            em.persist(goalGroups);
            em.flush();
        } else {
            goalGroups = TestUtil.findAll(em, GoalGroups.class).get(0);
        }
        goalGroupMappings.setGoalGroup(goalGroups);
        // Add required entity
        Goals goals;
        if (TestUtil.findAll(em, Goals.class).isEmpty()) {
            goals = GoalsResourceIT.createEntity(em);
            em.persist(goals);
            em.flush();
        } else {
            goals = TestUtil.findAll(em, Goals.class).get(0);
        }
        goalGroupMappings.setGoal(goals);
        return goalGroupMappings;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GoalGroupMappings createUpdatedEntity(EntityManager em) {
        GoalGroupMappings goalGroupMappings = new GoalGroupMappings()
            .weightage(UPDATED_WEIGHTAGE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);
        // Add required entity
        GoalGroups goalGroups;
        if (TestUtil.findAll(em, GoalGroups.class).isEmpty()) {
            goalGroups = GoalGroupsResourceIT.createUpdatedEntity(em);
            em.persist(goalGroups);
            em.flush();
        } else {
            goalGroups = TestUtil.findAll(em, GoalGroups.class).get(0);
        }
        goalGroupMappings.setGoalGroup(goalGroups);
        // Add required entity
        Goals goals;
        if (TestUtil.findAll(em, Goals.class).isEmpty()) {
            goals = GoalsResourceIT.createUpdatedEntity(em);
            em.persist(goals);
            em.flush();
        } else {
            goals = TestUtil.findAll(em, Goals.class).get(0);
        }
        goalGroupMappings.setGoal(goals);
        return goalGroupMappings;
    }

    @BeforeEach
    public void initTest() {
        goalGroupMappings = createEntity(em);
    }

    @Test
    @Transactional
    void createGoalGroupMappings() throws Exception {
        int databaseSizeBeforeCreate = goalGroupMappingsRepository.findAll().size();
        // Create the GoalGroupMappings
        restGoalGroupMappingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(goalGroupMappings))
            )
            .andExpect(status().isCreated());

        // Validate the GoalGroupMappings in the database
        List<GoalGroupMappings> goalGroupMappingsList = goalGroupMappingsRepository.findAll();
        assertThat(goalGroupMappingsList).hasSize(databaseSizeBeforeCreate + 1);
        GoalGroupMappings testGoalGroupMappings = goalGroupMappingsList.get(goalGroupMappingsList.size() - 1);
        assertThat(testGoalGroupMappings.getWeightage()).isEqualTo(DEFAULT_WEIGHTAGE);
        assertThat(testGoalGroupMappings.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testGoalGroupMappings.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testGoalGroupMappings.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testGoalGroupMappings.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createGoalGroupMappingsWithExistingId() throws Exception {
        // Create the GoalGroupMappings with an existing ID
        goalGroupMappings.setId(1L);

        int databaseSizeBeforeCreate = goalGroupMappingsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGoalGroupMappingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(goalGroupMappings))
            )
            .andExpect(status().isBadRequest());

        // Validate the GoalGroupMappings in the database
        List<GoalGroupMappings> goalGroupMappingsList = goalGroupMappingsRepository.findAll();
        assertThat(goalGroupMappingsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkWeightageIsRequired() throws Exception {
        int databaseSizeBeforeTest = goalGroupMappingsRepository.findAll().size();
        // set the field null
        goalGroupMappings.setWeightage(null);

        // Create the GoalGroupMappings, which fails.

        restGoalGroupMappingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(goalGroupMappings))
            )
            .andExpect(status().isBadRequest());

        List<GoalGroupMappings> goalGroupMappingsList = goalGroupMappingsRepository.findAll();
        assertThat(goalGroupMappingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = goalGroupMappingsRepository.findAll().size();
        // set the field null
        goalGroupMappings.setCreatedAt(null);

        // Create the GoalGroupMappings, which fails.

        restGoalGroupMappingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(goalGroupMappings))
            )
            .andExpect(status().isBadRequest());

        List<GoalGroupMappings> goalGroupMappingsList = goalGroupMappingsRepository.findAll();
        assertThat(goalGroupMappingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = goalGroupMappingsRepository.findAll().size();
        // set the field null
        goalGroupMappings.setUpdatedAt(null);

        // Create the GoalGroupMappings, which fails.

        restGoalGroupMappingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(goalGroupMappings))
            )
            .andExpect(status().isBadRequest());

        List<GoalGroupMappings> goalGroupMappingsList = goalGroupMappingsRepository.findAll();
        assertThat(goalGroupMappingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = goalGroupMappingsRepository.findAll().size();
        // set the field null
        goalGroupMappings.setVersion(null);

        // Create the GoalGroupMappings, which fails.

        restGoalGroupMappingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(goalGroupMappings))
            )
            .andExpect(status().isBadRequest());

        List<GoalGroupMappings> goalGroupMappingsList = goalGroupMappingsRepository.findAll();
        assertThat(goalGroupMappingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGoalGroupMappings() throws Exception {
        // Initialize the database
        goalGroupMappingsRepository.saveAndFlush(goalGroupMappings);

        // Get all the goalGroupMappingsList
        restGoalGroupMappingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(goalGroupMappings.getId().intValue())))
            .andExpect(jsonPath("$.[*].weightage").value(hasItem(DEFAULT_WEIGHTAGE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getGoalGroupMappings() throws Exception {
        // Initialize the database
        goalGroupMappingsRepository.saveAndFlush(goalGroupMappings);

        // Get the goalGroupMappings
        restGoalGroupMappingsMockMvc
            .perform(get(ENTITY_API_URL_ID, goalGroupMappings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(goalGroupMappings.getId().intValue()))
            .andExpect(jsonPath("$.weightage").value(DEFAULT_WEIGHTAGE))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getGoalGroupMappingsByIdFiltering() throws Exception {
        // Initialize the database
        goalGroupMappingsRepository.saveAndFlush(goalGroupMappings);

        Long id = goalGroupMappings.getId();

        defaultGoalGroupMappingsShouldBeFound("id.equals=" + id);
        defaultGoalGroupMappingsShouldNotBeFound("id.notEquals=" + id);

        defaultGoalGroupMappingsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultGoalGroupMappingsShouldNotBeFound("id.greaterThan=" + id);

        defaultGoalGroupMappingsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultGoalGroupMappingsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllGoalGroupMappingsByWeightageIsEqualToSomething() throws Exception {
        // Initialize the database
        goalGroupMappingsRepository.saveAndFlush(goalGroupMappings);

        // Get all the goalGroupMappingsList where weightage equals to DEFAULT_WEIGHTAGE
        defaultGoalGroupMappingsShouldBeFound("weightage.equals=" + DEFAULT_WEIGHTAGE);

        // Get all the goalGroupMappingsList where weightage equals to UPDATED_WEIGHTAGE
        defaultGoalGroupMappingsShouldNotBeFound("weightage.equals=" + UPDATED_WEIGHTAGE);
    }

    @Test
    @Transactional
    void getAllGoalGroupMappingsByWeightageIsInShouldWork() throws Exception {
        // Initialize the database
        goalGroupMappingsRepository.saveAndFlush(goalGroupMappings);

        // Get all the goalGroupMappingsList where weightage in DEFAULT_WEIGHTAGE or UPDATED_WEIGHTAGE
        defaultGoalGroupMappingsShouldBeFound("weightage.in=" + DEFAULT_WEIGHTAGE + "," + UPDATED_WEIGHTAGE);

        // Get all the goalGroupMappingsList where weightage equals to UPDATED_WEIGHTAGE
        defaultGoalGroupMappingsShouldNotBeFound("weightage.in=" + UPDATED_WEIGHTAGE);
    }

    @Test
    @Transactional
    void getAllGoalGroupMappingsByWeightageIsNullOrNotNull() throws Exception {
        // Initialize the database
        goalGroupMappingsRepository.saveAndFlush(goalGroupMappings);

        // Get all the goalGroupMappingsList where weightage is not null
        defaultGoalGroupMappingsShouldBeFound("weightage.specified=true");

        // Get all the goalGroupMappingsList where weightage is null
        defaultGoalGroupMappingsShouldNotBeFound("weightage.specified=false");
    }

    @Test
    @Transactional
    void getAllGoalGroupMappingsByWeightageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        goalGroupMappingsRepository.saveAndFlush(goalGroupMappings);

        // Get all the goalGroupMappingsList where weightage is greater than or equal to DEFAULT_WEIGHTAGE
        defaultGoalGroupMappingsShouldBeFound("weightage.greaterThanOrEqual=" + DEFAULT_WEIGHTAGE);

        // Get all the goalGroupMappingsList where weightage is greater than or equal to UPDATED_WEIGHTAGE
        defaultGoalGroupMappingsShouldNotBeFound("weightage.greaterThanOrEqual=" + UPDATED_WEIGHTAGE);
    }

    @Test
    @Transactional
    void getAllGoalGroupMappingsByWeightageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        goalGroupMappingsRepository.saveAndFlush(goalGroupMappings);

        // Get all the goalGroupMappingsList where weightage is less than or equal to DEFAULT_WEIGHTAGE
        defaultGoalGroupMappingsShouldBeFound("weightage.lessThanOrEqual=" + DEFAULT_WEIGHTAGE);

        // Get all the goalGroupMappingsList where weightage is less than or equal to SMALLER_WEIGHTAGE
        defaultGoalGroupMappingsShouldNotBeFound("weightage.lessThanOrEqual=" + SMALLER_WEIGHTAGE);
    }

    @Test
    @Transactional
    void getAllGoalGroupMappingsByWeightageIsLessThanSomething() throws Exception {
        // Initialize the database
        goalGroupMappingsRepository.saveAndFlush(goalGroupMappings);

        // Get all the goalGroupMappingsList where weightage is less than DEFAULT_WEIGHTAGE
        defaultGoalGroupMappingsShouldNotBeFound("weightage.lessThan=" + DEFAULT_WEIGHTAGE);

        // Get all the goalGroupMappingsList where weightage is less than UPDATED_WEIGHTAGE
        defaultGoalGroupMappingsShouldBeFound("weightage.lessThan=" + UPDATED_WEIGHTAGE);
    }

    @Test
    @Transactional
    void getAllGoalGroupMappingsByWeightageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        goalGroupMappingsRepository.saveAndFlush(goalGroupMappings);

        // Get all the goalGroupMappingsList where weightage is greater than DEFAULT_WEIGHTAGE
        defaultGoalGroupMappingsShouldNotBeFound("weightage.greaterThan=" + DEFAULT_WEIGHTAGE);

        // Get all the goalGroupMappingsList where weightage is greater than SMALLER_WEIGHTAGE
        defaultGoalGroupMappingsShouldBeFound("weightage.greaterThan=" + SMALLER_WEIGHTAGE);
    }

    @Test
    @Transactional
    void getAllGoalGroupMappingsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        goalGroupMappingsRepository.saveAndFlush(goalGroupMappings);

        // Get all the goalGroupMappingsList where createdAt equals to DEFAULT_CREATED_AT
        defaultGoalGroupMappingsShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the goalGroupMappingsList where createdAt equals to UPDATED_CREATED_AT
        defaultGoalGroupMappingsShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllGoalGroupMappingsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        goalGroupMappingsRepository.saveAndFlush(goalGroupMappings);

        // Get all the goalGroupMappingsList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultGoalGroupMappingsShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the goalGroupMappingsList where createdAt equals to UPDATED_CREATED_AT
        defaultGoalGroupMappingsShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllGoalGroupMappingsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        goalGroupMappingsRepository.saveAndFlush(goalGroupMappings);

        // Get all the goalGroupMappingsList where createdAt is not null
        defaultGoalGroupMappingsShouldBeFound("createdAt.specified=true");

        // Get all the goalGroupMappingsList where createdAt is null
        defaultGoalGroupMappingsShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllGoalGroupMappingsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        goalGroupMappingsRepository.saveAndFlush(goalGroupMappings);

        // Get all the goalGroupMappingsList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultGoalGroupMappingsShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the goalGroupMappingsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultGoalGroupMappingsShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllGoalGroupMappingsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        goalGroupMappingsRepository.saveAndFlush(goalGroupMappings);

        // Get all the goalGroupMappingsList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultGoalGroupMappingsShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the goalGroupMappingsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultGoalGroupMappingsShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllGoalGroupMappingsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        goalGroupMappingsRepository.saveAndFlush(goalGroupMappings);

        // Get all the goalGroupMappingsList where updatedAt is not null
        defaultGoalGroupMappingsShouldBeFound("updatedAt.specified=true");

        // Get all the goalGroupMappingsList where updatedAt is null
        defaultGoalGroupMappingsShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllGoalGroupMappingsByDeletedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        goalGroupMappingsRepository.saveAndFlush(goalGroupMappings);

        // Get all the goalGroupMappingsList where deletedAt equals to DEFAULT_DELETED_AT
        defaultGoalGroupMappingsShouldBeFound("deletedAt.equals=" + DEFAULT_DELETED_AT);

        // Get all the goalGroupMappingsList where deletedAt equals to UPDATED_DELETED_AT
        defaultGoalGroupMappingsShouldNotBeFound("deletedAt.equals=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllGoalGroupMappingsByDeletedAtIsInShouldWork() throws Exception {
        // Initialize the database
        goalGroupMappingsRepository.saveAndFlush(goalGroupMappings);

        // Get all the goalGroupMappingsList where deletedAt in DEFAULT_DELETED_AT or UPDATED_DELETED_AT
        defaultGoalGroupMappingsShouldBeFound("deletedAt.in=" + DEFAULT_DELETED_AT + "," + UPDATED_DELETED_AT);

        // Get all the goalGroupMappingsList where deletedAt equals to UPDATED_DELETED_AT
        defaultGoalGroupMappingsShouldNotBeFound("deletedAt.in=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllGoalGroupMappingsByDeletedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        goalGroupMappingsRepository.saveAndFlush(goalGroupMappings);

        // Get all the goalGroupMappingsList where deletedAt is not null
        defaultGoalGroupMappingsShouldBeFound("deletedAt.specified=true");

        // Get all the goalGroupMappingsList where deletedAt is null
        defaultGoalGroupMappingsShouldNotBeFound("deletedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllGoalGroupMappingsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        goalGroupMappingsRepository.saveAndFlush(goalGroupMappings);

        // Get all the goalGroupMappingsList where version equals to DEFAULT_VERSION
        defaultGoalGroupMappingsShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the goalGroupMappingsList where version equals to UPDATED_VERSION
        defaultGoalGroupMappingsShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllGoalGroupMappingsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        goalGroupMappingsRepository.saveAndFlush(goalGroupMappings);

        // Get all the goalGroupMappingsList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultGoalGroupMappingsShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the goalGroupMappingsList where version equals to UPDATED_VERSION
        defaultGoalGroupMappingsShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllGoalGroupMappingsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        goalGroupMappingsRepository.saveAndFlush(goalGroupMappings);

        // Get all the goalGroupMappingsList where version is not null
        defaultGoalGroupMappingsShouldBeFound("version.specified=true");

        // Get all the goalGroupMappingsList where version is null
        defaultGoalGroupMappingsShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllGoalGroupMappingsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        goalGroupMappingsRepository.saveAndFlush(goalGroupMappings);

        // Get all the goalGroupMappingsList where version is greater than or equal to DEFAULT_VERSION
        defaultGoalGroupMappingsShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the goalGroupMappingsList where version is greater than or equal to UPDATED_VERSION
        defaultGoalGroupMappingsShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllGoalGroupMappingsByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        goalGroupMappingsRepository.saveAndFlush(goalGroupMappings);

        // Get all the goalGroupMappingsList where version is less than or equal to DEFAULT_VERSION
        defaultGoalGroupMappingsShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the goalGroupMappingsList where version is less than or equal to SMALLER_VERSION
        defaultGoalGroupMappingsShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllGoalGroupMappingsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        goalGroupMappingsRepository.saveAndFlush(goalGroupMappings);

        // Get all the goalGroupMappingsList where version is less than DEFAULT_VERSION
        defaultGoalGroupMappingsShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the goalGroupMappingsList where version is less than UPDATED_VERSION
        defaultGoalGroupMappingsShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllGoalGroupMappingsByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        goalGroupMappingsRepository.saveAndFlush(goalGroupMappings);

        // Get all the goalGroupMappingsList where version is greater than DEFAULT_VERSION
        defaultGoalGroupMappingsShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the goalGroupMappingsList where version is greater than SMALLER_VERSION
        defaultGoalGroupMappingsShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllGoalGroupMappingsByGoalGroupIsEqualToSomething() throws Exception {
        GoalGroups goalGroup;
        if (TestUtil.findAll(em, GoalGroups.class).isEmpty()) {
            goalGroupMappingsRepository.saveAndFlush(goalGroupMappings);
            goalGroup = GoalGroupsResourceIT.createEntity(em);
        } else {
            goalGroup = TestUtil.findAll(em, GoalGroups.class).get(0);
        }
        em.persist(goalGroup);
        em.flush();
        goalGroupMappings.setGoalGroup(goalGroup);
        goalGroupMappingsRepository.saveAndFlush(goalGroupMappings);
        Long goalGroupId = goalGroup.getId();

        // Get all the goalGroupMappingsList where goalGroup equals to goalGroupId
        defaultGoalGroupMappingsShouldBeFound("goalGroupId.equals=" + goalGroupId);

        // Get all the goalGroupMappingsList where goalGroup equals to (goalGroupId + 1)
        defaultGoalGroupMappingsShouldNotBeFound("goalGroupId.equals=" + (goalGroupId + 1));
    }

    @Test
    @Transactional
    void getAllGoalGroupMappingsByGoalIsEqualToSomething() throws Exception {
        Goals goal;
        if (TestUtil.findAll(em, Goals.class).isEmpty()) {
            goalGroupMappingsRepository.saveAndFlush(goalGroupMappings);
            goal = GoalsResourceIT.createEntity(em);
        } else {
            goal = TestUtil.findAll(em, Goals.class).get(0);
        }
        em.persist(goal);
        em.flush();
        goalGroupMappings.setGoal(goal);
        goalGroupMappingsRepository.saveAndFlush(goalGroupMappings);
        Long goalId = goal.getId();

        // Get all the goalGroupMappingsList where goal equals to goalId
        defaultGoalGroupMappingsShouldBeFound("goalId.equals=" + goalId);

        // Get all the goalGroupMappingsList where goal equals to (goalId + 1)
        defaultGoalGroupMappingsShouldNotBeFound("goalId.equals=" + (goalId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultGoalGroupMappingsShouldBeFound(String filter) throws Exception {
        restGoalGroupMappingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(goalGroupMappings.getId().intValue())))
            .andExpect(jsonPath("$.[*].weightage").value(hasItem(DEFAULT_WEIGHTAGE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restGoalGroupMappingsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultGoalGroupMappingsShouldNotBeFound(String filter) throws Exception {
        restGoalGroupMappingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGoalGroupMappingsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingGoalGroupMappings() throws Exception {
        // Get the goalGroupMappings
        restGoalGroupMappingsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGoalGroupMappings() throws Exception {
        // Initialize the database
        goalGroupMappingsRepository.saveAndFlush(goalGroupMappings);

        int databaseSizeBeforeUpdate = goalGroupMappingsRepository.findAll().size();

        // Update the goalGroupMappings
        GoalGroupMappings updatedGoalGroupMappings = goalGroupMappingsRepository.findById(goalGroupMappings.getId()).get();
        // Disconnect from session so that the updates on updatedGoalGroupMappings are not directly saved in db
        em.detach(updatedGoalGroupMappings);
        updatedGoalGroupMappings
            .weightage(UPDATED_WEIGHTAGE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restGoalGroupMappingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGoalGroupMappings.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGoalGroupMappings))
            )
            .andExpect(status().isOk());

        // Validate the GoalGroupMappings in the database
        List<GoalGroupMappings> goalGroupMappingsList = goalGroupMappingsRepository.findAll();
        assertThat(goalGroupMappingsList).hasSize(databaseSizeBeforeUpdate);
        GoalGroupMappings testGoalGroupMappings = goalGroupMappingsList.get(goalGroupMappingsList.size() - 1);
        assertThat(testGoalGroupMappings.getWeightage()).isEqualTo(UPDATED_WEIGHTAGE);
        assertThat(testGoalGroupMappings.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testGoalGroupMappings.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testGoalGroupMappings.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testGoalGroupMappings.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingGoalGroupMappings() throws Exception {
        int databaseSizeBeforeUpdate = goalGroupMappingsRepository.findAll().size();
        goalGroupMappings.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGoalGroupMappingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, goalGroupMappings.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(goalGroupMappings))
            )
            .andExpect(status().isBadRequest());

        // Validate the GoalGroupMappings in the database
        List<GoalGroupMappings> goalGroupMappingsList = goalGroupMappingsRepository.findAll();
        assertThat(goalGroupMappingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGoalGroupMappings() throws Exception {
        int databaseSizeBeforeUpdate = goalGroupMappingsRepository.findAll().size();
        goalGroupMappings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGoalGroupMappingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(goalGroupMappings))
            )
            .andExpect(status().isBadRequest());

        // Validate the GoalGroupMappings in the database
        List<GoalGroupMappings> goalGroupMappingsList = goalGroupMappingsRepository.findAll();
        assertThat(goalGroupMappingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGoalGroupMappings() throws Exception {
        int databaseSizeBeforeUpdate = goalGroupMappingsRepository.findAll().size();
        goalGroupMappings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGoalGroupMappingsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(goalGroupMappings))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GoalGroupMappings in the database
        List<GoalGroupMappings> goalGroupMappingsList = goalGroupMappingsRepository.findAll();
        assertThat(goalGroupMappingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGoalGroupMappingsWithPatch() throws Exception {
        // Initialize the database
        goalGroupMappingsRepository.saveAndFlush(goalGroupMappings);

        int databaseSizeBeforeUpdate = goalGroupMappingsRepository.findAll().size();

        // Update the goalGroupMappings using partial update
        GoalGroupMappings partialUpdatedGoalGroupMappings = new GoalGroupMappings();
        partialUpdatedGoalGroupMappings.setId(goalGroupMappings.getId());

        partialUpdatedGoalGroupMappings.updatedAt(UPDATED_UPDATED_AT).deletedAt(UPDATED_DELETED_AT).version(UPDATED_VERSION);

        restGoalGroupMappingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGoalGroupMappings.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGoalGroupMappings))
            )
            .andExpect(status().isOk());

        // Validate the GoalGroupMappings in the database
        List<GoalGroupMappings> goalGroupMappingsList = goalGroupMappingsRepository.findAll();
        assertThat(goalGroupMappingsList).hasSize(databaseSizeBeforeUpdate);
        GoalGroupMappings testGoalGroupMappings = goalGroupMappingsList.get(goalGroupMappingsList.size() - 1);
        assertThat(testGoalGroupMappings.getWeightage()).isEqualTo(DEFAULT_WEIGHTAGE);
        assertThat(testGoalGroupMappings.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testGoalGroupMappings.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testGoalGroupMappings.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testGoalGroupMappings.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateGoalGroupMappingsWithPatch() throws Exception {
        // Initialize the database
        goalGroupMappingsRepository.saveAndFlush(goalGroupMappings);

        int databaseSizeBeforeUpdate = goalGroupMappingsRepository.findAll().size();

        // Update the goalGroupMappings using partial update
        GoalGroupMappings partialUpdatedGoalGroupMappings = new GoalGroupMappings();
        partialUpdatedGoalGroupMappings.setId(goalGroupMappings.getId());

        partialUpdatedGoalGroupMappings
            .weightage(UPDATED_WEIGHTAGE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restGoalGroupMappingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGoalGroupMappings.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGoalGroupMappings))
            )
            .andExpect(status().isOk());

        // Validate the GoalGroupMappings in the database
        List<GoalGroupMappings> goalGroupMappingsList = goalGroupMappingsRepository.findAll();
        assertThat(goalGroupMappingsList).hasSize(databaseSizeBeforeUpdate);
        GoalGroupMappings testGoalGroupMappings = goalGroupMappingsList.get(goalGroupMappingsList.size() - 1);
        assertThat(testGoalGroupMappings.getWeightage()).isEqualTo(UPDATED_WEIGHTAGE);
        assertThat(testGoalGroupMappings.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testGoalGroupMappings.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testGoalGroupMappings.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testGoalGroupMappings.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingGoalGroupMappings() throws Exception {
        int databaseSizeBeforeUpdate = goalGroupMappingsRepository.findAll().size();
        goalGroupMappings.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGoalGroupMappingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, goalGroupMappings.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(goalGroupMappings))
            )
            .andExpect(status().isBadRequest());

        // Validate the GoalGroupMappings in the database
        List<GoalGroupMappings> goalGroupMappingsList = goalGroupMappingsRepository.findAll();
        assertThat(goalGroupMappingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGoalGroupMappings() throws Exception {
        int databaseSizeBeforeUpdate = goalGroupMappingsRepository.findAll().size();
        goalGroupMappings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGoalGroupMappingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(goalGroupMappings))
            )
            .andExpect(status().isBadRequest());

        // Validate the GoalGroupMappings in the database
        List<GoalGroupMappings> goalGroupMappingsList = goalGroupMappingsRepository.findAll();
        assertThat(goalGroupMappingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGoalGroupMappings() throws Exception {
        int databaseSizeBeforeUpdate = goalGroupMappingsRepository.findAll().size();
        goalGroupMappings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGoalGroupMappingsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(goalGroupMappings))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GoalGroupMappings in the database
        List<GoalGroupMappings> goalGroupMappingsList = goalGroupMappingsRepository.findAll();
        assertThat(goalGroupMappingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGoalGroupMappings() throws Exception {
        // Initialize the database
        goalGroupMappingsRepository.saveAndFlush(goalGroupMappings);

        int databaseSizeBeforeDelete = goalGroupMappingsRepository.findAll().size();

        // Delete the goalGroupMappings
        restGoalGroupMappingsMockMvc
            .perform(delete(ENTITY_API_URL_ID, goalGroupMappings.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GoalGroupMappings> goalGroupMappingsList = goalGroupMappingsRepository.findAll();
        assertThat(goalGroupMappingsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
