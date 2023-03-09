package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.GoalGroupMappings;
import com.venturedive.vendian_mono.domain.GoalGroups;
import com.venturedive.vendian_mono.repository.GoalGroupsRepository;
import com.venturedive.vendian_mono.service.criteria.GoalGroupsCriteria;
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
 * Integration tests for the {@link GoalGroupsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GoalGroupsResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;
    private static final Integer SMALLER_VERSION = 1 - 1;

    private static final String ENTITY_API_URL = "/api/goal-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GoalGroupsRepository goalGroupsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGoalGroupsMockMvc;

    private GoalGroups goalGroups;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GoalGroups createEntity(EntityManager em) {
        GoalGroups goalGroups = new GoalGroups()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT)
            .version(DEFAULT_VERSION);
        return goalGroups;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GoalGroups createUpdatedEntity(EntityManager em) {
        GoalGroups goalGroups = new GoalGroups()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);
        return goalGroups;
    }

    @BeforeEach
    public void initTest() {
        goalGroups = createEntity(em);
    }

    @Test
    @Transactional
    void createGoalGroups() throws Exception {
        int databaseSizeBeforeCreate = goalGroupsRepository.findAll().size();
        // Create the GoalGroups
        restGoalGroupsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(goalGroups))
            )
            .andExpect(status().isCreated());

        // Validate the GoalGroups in the database
        List<GoalGroups> goalGroupsList = goalGroupsRepository.findAll();
        assertThat(goalGroupsList).hasSize(databaseSizeBeforeCreate + 1);
        GoalGroups testGoalGroups = goalGroupsList.get(goalGroupsList.size() - 1);
        assertThat(testGoalGroups.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testGoalGroups.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testGoalGroups.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testGoalGroups.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testGoalGroups.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testGoalGroups.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createGoalGroupsWithExistingId() throws Exception {
        // Create the GoalGroups with an existing ID
        goalGroups.setId(1L);

        int databaseSizeBeforeCreate = goalGroupsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGoalGroupsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(goalGroups))
            )
            .andExpect(status().isBadRequest());

        // Validate the GoalGroups in the database
        List<GoalGroups> goalGroupsList = goalGroupsRepository.findAll();
        assertThat(goalGroupsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = goalGroupsRepository.findAll().size();
        // set the field null
        goalGroups.setTitle(null);

        // Create the GoalGroups, which fails.

        restGoalGroupsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(goalGroups))
            )
            .andExpect(status().isBadRequest());

        List<GoalGroups> goalGroupsList = goalGroupsRepository.findAll();
        assertThat(goalGroupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = goalGroupsRepository.findAll().size();
        // set the field null
        goalGroups.setCreatedAt(null);

        // Create the GoalGroups, which fails.

        restGoalGroupsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(goalGroups))
            )
            .andExpect(status().isBadRequest());

        List<GoalGroups> goalGroupsList = goalGroupsRepository.findAll();
        assertThat(goalGroupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = goalGroupsRepository.findAll().size();
        // set the field null
        goalGroups.setUpdatedAt(null);

        // Create the GoalGroups, which fails.

        restGoalGroupsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(goalGroups))
            )
            .andExpect(status().isBadRequest());

        List<GoalGroups> goalGroupsList = goalGroupsRepository.findAll();
        assertThat(goalGroupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = goalGroupsRepository.findAll().size();
        // set the field null
        goalGroups.setVersion(null);

        // Create the GoalGroups, which fails.

        restGoalGroupsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(goalGroups))
            )
            .andExpect(status().isBadRequest());

        List<GoalGroups> goalGroupsList = goalGroupsRepository.findAll();
        assertThat(goalGroupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGoalGroups() throws Exception {
        // Initialize the database
        goalGroupsRepository.saveAndFlush(goalGroups);

        // Get all the goalGroupsList
        restGoalGroupsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(goalGroups.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getGoalGroups() throws Exception {
        // Initialize the database
        goalGroupsRepository.saveAndFlush(goalGroups);

        // Get the goalGroups
        restGoalGroupsMockMvc
            .perform(get(ENTITY_API_URL_ID, goalGroups.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(goalGroups.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getGoalGroupsByIdFiltering() throws Exception {
        // Initialize the database
        goalGroupsRepository.saveAndFlush(goalGroups);

        Long id = goalGroups.getId();

        defaultGoalGroupsShouldBeFound("id.equals=" + id);
        defaultGoalGroupsShouldNotBeFound("id.notEquals=" + id);

        defaultGoalGroupsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultGoalGroupsShouldNotBeFound("id.greaterThan=" + id);

        defaultGoalGroupsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultGoalGroupsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllGoalGroupsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        goalGroupsRepository.saveAndFlush(goalGroups);

        // Get all the goalGroupsList where title equals to DEFAULT_TITLE
        defaultGoalGroupsShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the goalGroupsList where title equals to UPDATED_TITLE
        defaultGoalGroupsShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllGoalGroupsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        goalGroupsRepository.saveAndFlush(goalGroups);

        // Get all the goalGroupsList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultGoalGroupsShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the goalGroupsList where title equals to UPDATED_TITLE
        defaultGoalGroupsShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllGoalGroupsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        goalGroupsRepository.saveAndFlush(goalGroups);

        // Get all the goalGroupsList where title is not null
        defaultGoalGroupsShouldBeFound("title.specified=true");

        // Get all the goalGroupsList where title is null
        defaultGoalGroupsShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllGoalGroupsByTitleContainsSomething() throws Exception {
        // Initialize the database
        goalGroupsRepository.saveAndFlush(goalGroups);

        // Get all the goalGroupsList where title contains DEFAULT_TITLE
        defaultGoalGroupsShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the goalGroupsList where title contains UPDATED_TITLE
        defaultGoalGroupsShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllGoalGroupsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        goalGroupsRepository.saveAndFlush(goalGroups);

        // Get all the goalGroupsList where title does not contain DEFAULT_TITLE
        defaultGoalGroupsShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the goalGroupsList where title does not contain UPDATED_TITLE
        defaultGoalGroupsShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllGoalGroupsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        goalGroupsRepository.saveAndFlush(goalGroups);

        // Get all the goalGroupsList where description equals to DEFAULT_DESCRIPTION
        defaultGoalGroupsShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the goalGroupsList where description equals to UPDATED_DESCRIPTION
        defaultGoalGroupsShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllGoalGroupsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        goalGroupsRepository.saveAndFlush(goalGroups);

        // Get all the goalGroupsList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultGoalGroupsShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the goalGroupsList where description equals to UPDATED_DESCRIPTION
        defaultGoalGroupsShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllGoalGroupsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        goalGroupsRepository.saveAndFlush(goalGroups);

        // Get all the goalGroupsList where description is not null
        defaultGoalGroupsShouldBeFound("description.specified=true");

        // Get all the goalGroupsList where description is null
        defaultGoalGroupsShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllGoalGroupsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        goalGroupsRepository.saveAndFlush(goalGroups);

        // Get all the goalGroupsList where description contains DEFAULT_DESCRIPTION
        defaultGoalGroupsShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the goalGroupsList where description contains UPDATED_DESCRIPTION
        defaultGoalGroupsShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllGoalGroupsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        goalGroupsRepository.saveAndFlush(goalGroups);

        // Get all the goalGroupsList where description does not contain DEFAULT_DESCRIPTION
        defaultGoalGroupsShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the goalGroupsList where description does not contain UPDATED_DESCRIPTION
        defaultGoalGroupsShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllGoalGroupsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        goalGroupsRepository.saveAndFlush(goalGroups);

        // Get all the goalGroupsList where createdAt equals to DEFAULT_CREATED_AT
        defaultGoalGroupsShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the goalGroupsList where createdAt equals to UPDATED_CREATED_AT
        defaultGoalGroupsShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllGoalGroupsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        goalGroupsRepository.saveAndFlush(goalGroups);

        // Get all the goalGroupsList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultGoalGroupsShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the goalGroupsList where createdAt equals to UPDATED_CREATED_AT
        defaultGoalGroupsShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllGoalGroupsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        goalGroupsRepository.saveAndFlush(goalGroups);

        // Get all the goalGroupsList where createdAt is not null
        defaultGoalGroupsShouldBeFound("createdAt.specified=true");

        // Get all the goalGroupsList where createdAt is null
        defaultGoalGroupsShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllGoalGroupsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        goalGroupsRepository.saveAndFlush(goalGroups);

        // Get all the goalGroupsList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultGoalGroupsShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the goalGroupsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultGoalGroupsShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllGoalGroupsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        goalGroupsRepository.saveAndFlush(goalGroups);

        // Get all the goalGroupsList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultGoalGroupsShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the goalGroupsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultGoalGroupsShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllGoalGroupsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        goalGroupsRepository.saveAndFlush(goalGroups);

        // Get all the goalGroupsList where updatedAt is not null
        defaultGoalGroupsShouldBeFound("updatedAt.specified=true");

        // Get all the goalGroupsList where updatedAt is null
        defaultGoalGroupsShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllGoalGroupsByDeletedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        goalGroupsRepository.saveAndFlush(goalGroups);

        // Get all the goalGroupsList where deletedAt equals to DEFAULT_DELETED_AT
        defaultGoalGroupsShouldBeFound("deletedAt.equals=" + DEFAULT_DELETED_AT);

        // Get all the goalGroupsList where deletedAt equals to UPDATED_DELETED_AT
        defaultGoalGroupsShouldNotBeFound("deletedAt.equals=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllGoalGroupsByDeletedAtIsInShouldWork() throws Exception {
        // Initialize the database
        goalGroupsRepository.saveAndFlush(goalGroups);

        // Get all the goalGroupsList where deletedAt in DEFAULT_DELETED_AT or UPDATED_DELETED_AT
        defaultGoalGroupsShouldBeFound("deletedAt.in=" + DEFAULT_DELETED_AT + "," + UPDATED_DELETED_AT);

        // Get all the goalGroupsList where deletedAt equals to UPDATED_DELETED_AT
        defaultGoalGroupsShouldNotBeFound("deletedAt.in=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllGoalGroupsByDeletedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        goalGroupsRepository.saveAndFlush(goalGroups);

        // Get all the goalGroupsList where deletedAt is not null
        defaultGoalGroupsShouldBeFound("deletedAt.specified=true");

        // Get all the goalGroupsList where deletedAt is null
        defaultGoalGroupsShouldNotBeFound("deletedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllGoalGroupsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        goalGroupsRepository.saveAndFlush(goalGroups);

        // Get all the goalGroupsList where version equals to DEFAULT_VERSION
        defaultGoalGroupsShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the goalGroupsList where version equals to UPDATED_VERSION
        defaultGoalGroupsShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllGoalGroupsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        goalGroupsRepository.saveAndFlush(goalGroups);

        // Get all the goalGroupsList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultGoalGroupsShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the goalGroupsList where version equals to UPDATED_VERSION
        defaultGoalGroupsShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllGoalGroupsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        goalGroupsRepository.saveAndFlush(goalGroups);

        // Get all the goalGroupsList where version is not null
        defaultGoalGroupsShouldBeFound("version.specified=true");

        // Get all the goalGroupsList where version is null
        defaultGoalGroupsShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllGoalGroupsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        goalGroupsRepository.saveAndFlush(goalGroups);

        // Get all the goalGroupsList where version is greater than or equal to DEFAULT_VERSION
        defaultGoalGroupsShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the goalGroupsList where version is greater than or equal to UPDATED_VERSION
        defaultGoalGroupsShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllGoalGroupsByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        goalGroupsRepository.saveAndFlush(goalGroups);

        // Get all the goalGroupsList where version is less than or equal to DEFAULT_VERSION
        defaultGoalGroupsShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the goalGroupsList where version is less than or equal to SMALLER_VERSION
        defaultGoalGroupsShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllGoalGroupsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        goalGroupsRepository.saveAndFlush(goalGroups);

        // Get all the goalGroupsList where version is less than DEFAULT_VERSION
        defaultGoalGroupsShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the goalGroupsList where version is less than UPDATED_VERSION
        defaultGoalGroupsShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllGoalGroupsByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        goalGroupsRepository.saveAndFlush(goalGroups);

        // Get all the goalGroupsList where version is greater than DEFAULT_VERSION
        defaultGoalGroupsShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the goalGroupsList where version is greater than SMALLER_VERSION
        defaultGoalGroupsShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllGoalGroupsByGoalgroupmappingsGoalgroupIsEqualToSomething() throws Exception {
        GoalGroupMappings goalgroupmappingsGoalgroup;
        if (TestUtil.findAll(em, GoalGroupMappings.class).isEmpty()) {
            goalGroupsRepository.saveAndFlush(goalGroups);
            goalgroupmappingsGoalgroup = GoalGroupMappingsResourceIT.createEntity(em);
        } else {
            goalgroupmappingsGoalgroup = TestUtil.findAll(em, GoalGroupMappings.class).get(0);
        }
        em.persist(goalgroupmappingsGoalgroup);
        em.flush();
        goalGroups.addGoalgroupmappingsGoalgroup(goalgroupmappingsGoalgroup);
        goalGroupsRepository.saveAndFlush(goalGroups);
        Long goalgroupmappingsGoalgroupId = goalgroupmappingsGoalgroup.getId();

        // Get all the goalGroupsList where goalgroupmappingsGoalgroup equals to goalgroupmappingsGoalgroupId
        defaultGoalGroupsShouldBeFound("goalgroupmappingsGoalgroupId.equals=" + goalgroupmappingsGoalgroupId);

        // Get all the goalGroupsList where goalgroupmappingsGoalgroup equals to (goalgroupmappingsGoalgroupId + 1)
        defaultGoalGroupsShouldNotBeFound("goalgroupmappingsGoalgroupId.equals=" + (goalgroupmappingsGoalgroupId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultGoalGroupsShouldBeFound(String filter) throws Exception {
        restGoalGroupsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(goalGroups.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restGoalGroupsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultGoalGroupsShouldNotBeFound(String filter) throws Exception {
        restGoalGroupsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGoalGroupsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingGoalGroups() throws Exception {
        // Get the goalGroups
        restGoalGroupsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGoalGroups() throws Exception {
        // Initialize the database
        goalGroupsRepository.saveAndFlush(goalGroups);

        int databaseSizeBeforeUpdate = goalGroupsRepository.findAll().size();

        // Update the goalGroups
        GoalGroups updatedGoalGroups = goalGroupsRepository.findById(goalGroups.getId()).get();
        // Disconnect from session so that the updates on updatedGoalGroups are not directly saved in db
        em.detach(updatedGoalGroups);
        updatedGoalGroups
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restGoalGroupsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGoalGroups.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGoalGroups))
            )
            .andExpect(status().isOk());

        // Validate the GoalGroups in the database
        List<GoalGroups> goalGroupsList = goalGroupsRepository.findAll();
        assertThat(goalGroupsList).hasSize(databaseSizeBeforeUpdate);
        GoalGroups testGoalGroups = goalGroupsList.get(goalGroupsList.size() - 1);
        assertThat(testGoalGroups.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testGoalGroups.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testGoalGroups.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testGoalGroups.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testGoalGroups.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testGoalGroups.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingGoalGroups() throws Exception {
        int databaseSizeBeforeUpdate = goalGroupsRepository.findAll().size();
        goalGroups.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGoalGroupsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, goalGroups.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(goalGroups))
            )
            .andExpect(status().isBadRequest());

        // Validate the GoalGroups in the database
        List<GoalGroups> goalGroupsList = goalGroupsRepository.findAll();
        assertThat(goalGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGoalGroups() throws Exception {
        int databaseSizeBeforeUpdate = goalGroupsRepository.findAll().size();
        goalGroups.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGoalGroupsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(goalGroups))
            )
            .andExpect(status().isBadRequest());

        // Validate the GoalGroups in the database
        List<GoalGroups> goalGroupsList = goalGroupsRepository.findAll();
        assertThat(goalGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGoalGroups() throws Exception {
        int databaseSizeBeforeUpdate = goalGroupsRepository.findAll().size();
        goalGroups.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGoalGroupsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(goalGroups))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GoalGroups in the database
        List<GoalGroups> goalGroupsList = goalGroupsRepository.findAll();
        assertThat(goalGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGoalGroupsWithPatch() throws Exception {
        // Initialize the database
        goalGroupsRepository.saveAndFlush(goalGroups);

        int databaseSizeBeforeUpdate = goalGroupsRepository.findAll().size();

        // Update the goalGroups using partial update
        GoalGroups partialUpdatedGoalGroups = new GoalGroups();
        partialUpdatedGoalGroups.setId(goalGroups.getId());

        partialUpdatedGoalGroups.createdAt(UPDATED_CREATED_AT);

        restGoalGroupsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGoalGroups.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGoalGroups))
            )
            .andExpect(status().isOk());

        // Validate the GoalGroups in the database
        List<GoalGroups> goalGroupsList = goalGroupsRepository.findAll();
        assertThat(goalGroupsList).hasSize(databaseSizeBeforeUpdate);
        GoalGroups testGoalGroups = goalGroupsList.get(goalGroupsList.size() - 1);
        assertThat(testGoalGroups.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testGoalGroups.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testGoalGroups.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testGoalGroups.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testGoalGroups.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testGoalGroups.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateGoalGroupsWithPatch() throws Exception {
        // Initialize the database
        goalGroupsRepository.saveAndFlush(goalGroups);

        int databaseSizeBeforeUpdate = goalGroupsRepository.findAll().size();

        // Update the goalGroups using partial update
        GoalGroups partialUpdatedGoalGroups = new GoalGroups();
        partialUpdatedGoalGroups.setId(goalGroups.getId());

        partialUpdatedGoalGroups
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restGoalGroupsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGoalGroups.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGoalGroups))
            )
            .andExpect(status().isOk());

        // Validate the GoalGroups in the database
        List<GoalGroups> goalGroupsList = goalGroupsRepository.findAll();
        assertThat(goalGroupsList).hasSize(databaseSizeBeforeUpdate);
        GoalGroups testGoalGroups = goalGroupsList.get(goalGroupsList.size() - 1);
        assertThat(testGoalGroups.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testGoalGroups.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testGoalGroups.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testGoalGroups.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testGoalGroups.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testGoalGroups.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingGoalGroups() throws Exception {
        int databaseSizeBeforeUpdate = goalGroupsRepository.findAll().size();
        goalGroups.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGoalGroupsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, goalGroups.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(goalGroups))
            )
            .andExpect(status().isBadRequest());

        // Validate the GoalGroups in the database
        List<GoalGroups> goalGroupsList = goalGroupsRepository.findAll();
        assertThat(goalGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGoalGroups() throws Exception {
        int databaseSizeBeforeUpdate = goalGroupsRepository.findAll().size();
        goalGroups.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGoalGroupsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(goalGroups))
            )
            .andExpect(status().isBadRequest());

        // Validate the GoalGroups in the database
        List<GoalGroups> goalGroupsList = goalGroupsRepository.findAll();
        assertThat(goalGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGoalGroups() throws Exception {
        int databaseSizeBeforeUpdate = goalGroupsRepository.findAll().size();
        goalGroups.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGoalGroupsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(goalGroups))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GoalGroups in the database
        List<GoalGroups> goalGroupsList = goalGroupsRepository.findAll();
        assertThat(goalGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGoalGroups() throws Exception {
        // Initialize the database
        goalGroupsRepository.saveAndFlush(goalGroups);

        int databaseSizeBeforeDelete = goalGroupsRepository.findAll().size();

        // Delete the goalGroups
        restGoalGroupsMockMvc
            .perform(delete(ENTITY_API_URL_ID, goalGroups.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GoalGroups> goalGroupsList = goalGroupsRepository.findAll();
        assertThat(goalGroupsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
