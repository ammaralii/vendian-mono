package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.LeaveDetails;
import com.venturedive.vendian_mono.domain.LeaveTypeApprovalRules;
import com.venturedive.vendian_mono.domain.LeaveTypeConfigurations;
import com.venturedive.vendian_mono.domain.LeaveTypeEscalationRules;
import com.venturedive.vendian_mono.domain.LeaveTypeRestrictions;
import com.venturedive.vendian_mono.domain.LeaveTypes;
import com.venturedive.vendian_mono.repository.LeaveTypesRepository;
import com.venturedive.vendian_mono.service.criteria.LeaveTypesCriteria;
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
 * Integration tests for the {@link LeaveTypesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeaveTypesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBB";

    private static final String DEFAULT_CYCLE_TYPE = "AAAAA";
    private static final String UPDATED_CYCLE_TYPE = "BBBBB";

    private static final Integer DEFAULT_CYCLE_COUNT = 1;
    private static final Integer UPDATED_CYCLE_COUNT = 2;
    private static final Integer SMALLER_CYCLE_COUNT = 1 - 1;

    private static final Integer DEFAULT_MAX_QUOTA = 1;
    private static final Integer UPDATED_MAX_QUOTA = 2;
    private static final Integer SMALLER_MAX_QUOTA = 1 - 1;

    private static final Instant DEFAULT_EFF_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EFF_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;
    private static final Integer SMALLER_VERSION = 1 - 1;

    private static final String ENTITY_API_URL = "/api/leave-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LeaveTypesRepository leaveTypesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeaveTypesMockMvc;

    private LeaveTypes leaveTypes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveTypes createEntity(EntityManager em) {
        LeaveTypes leaveTypes = new LeaveTypes()
            .name(DEFAULT_NAME)
            .category(DEFAULT_CATEGORY)
            .cycleType(DEFAULT_CYCLE_TYPE)
            .cycleCount(DEFAULT_CYCLE_COUNT)
            .maxQuota(DEFAULT_MAX_QUOTA)
            .effDate(DEFAULT_EFF_DATE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .endDate(DEFAULT_END_DATE)
            .version(DEFAULT_VERSION);
        return leaveTypes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveTypes createUpdatedEntity(EntityManager em) {
        LeaveTypes leaveTypes = new LeaveTypes()
            .name(UPDATED_NAME)
            .category(UPDATED_CATEGORY)
            .cycleType(UPDATED_CYCLE_TYPE)
            .cycleCount(UPDATED_CYCLE_COUNT)
            .maxQuota(UPDATED_MAX_QUOTA)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);
        return leaveTypes;
    }

    @BeforeEach
    public void initTest() {
        leaveTypes = createEntity(em);
    }

    @Test
    @Transactional
    void createLeaveTypes() throws Exception {
        int databaseSizeBeforeCreate = leaveTypesRepository.findAll().size();
        // Create the LeaveTypes
        restLeaveTypesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypes))
            )
            .andExpect(status().isCreated());

        // Validate the LeaveTypes in the database
        List<LeaveTypes> leaveTypesList = leaveTypesRepository.findAll();
        assertThat(leaveTypesList).hasSize(databaseSizeBeforeCreate + 1);
        LeaveTypes testLeaveTypes = leaveTypesList.get(leaveTypesList.size() - 1);
        assertThat(testLeaveTypes.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLeaveTypes.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testLeaveTypes.getCycleType()).isEqualTo(DEFAULT_CYCLE_TYPE);
        assertThat(testLeaveTypes.getCycleCount()).isEqualTo(DEFAULT_CYCLE_COUNT);
        assertThat(testLeaveTypes.getMaxQuota()).isEqualTo(DEFAULT_MAX_QUOTA);
        assertThat(testLeaveTypes.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testLeaveTypes.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testLeaveTypes.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testLeaveTypes.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testLeaveTypes.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createLeaveTypesWithExistingId() throws Exception {
        // Create the LeaveTypes with an existing ID
        leaveTypes.setId(1L);

        int databaseSizeBeforeCreate = leaveTypesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeaveTypesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypes))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveTypes in the database
        List<LeaveTypes> leaveTypesList = leaveTypesRepository.findAll();
        assertThat(leaveTypesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveTypesRepository.findAll().size();
        // set the field null
        leaveTypes.setName(null);

        // Create the LeaveTypes, which fails.

        restLeaveTypesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypes))
            )
            .andExpect(status().isBadRequest());

        List<LeaveTypes> leaveTypesList = leaveTypesRepository.findAll();
        assertThat(leaveTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveTypesRepository.findAll().size();
        // set the field null
        leaveTypes.setCategory(null);

        // Create the LeaveTypes, which fails.

        restLeaveTypesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypes))
            )
            .andExpect(status().isBadRequest());

        List<LeaveTypes> leaveTypesList = leaveTypesRepository.findAll();
        assertThat(leaveTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCycleTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveTypesRepository.findAll().size();
        // set the field null
        leaveTypes.setCycleType(null);

        // Create the LeaveTypes, which fails.

        restLeaveTypesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypes))
            )
            .andExpect(status().isBadRequest());

        List<LeaveTypes> leaveTypesList = leaveTypesRepository.findAll();
        assertThat(leaveTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCycleCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveTypesRepository.findAll().size();
        // set the field null
        leaveTypes.setCycleCount(null);

        // Create the LeaveTypes, which fails.

        restLeaveTypesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypes))
            )
            .andExpect(status().isBadRequest());

        List<LeaveTypes> leaveTypesList = leaveTypesRepository.findAll();
        assertThat(leaveTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEffDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveTypesRepository.findAll().size();
        // set the field null
        leaveTypes.setEffDate(null);

        // Create the LeaveTypes, which fails.

        restLeaveTypesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypes))
            )
            .andExpect(status().isBadRequest());

        List<LeaveTypes> leaveTypesList = leaveTypesRepository.findAll();
        assertThat(leaveTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveTypesRepository.findAll().size();
        // set the field null
        leaveTypes.setCreatedAt(null);

        // Create the LeaveTypes, which fails.

        restLeaveTypesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypes))
            )
            .andExpect(status().isBadRequest());

        List<LeaveTypes> leaveTypesList = leaveTypesRepository.findAll();
        assertThat(leaveTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveTypesRepository.findAll().size();
        // set the field null
        leaveTypes.setUpdatedAt(null);

        // Create the LeaveTypes, which fails.

        restLeaveTypesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypes))
            )
            .andExpect(status().isBadRequest());

        List<LeaveTypes> leaveTypesList = leaveTypesRepository.findAll();
        assertThat(leaveTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveTypesRepository.findAll().size();
        // set the field null
        leaveTypes.setVersion(null);

        // Create the LeaveTypes, which fails.

        restLeaveTypesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypes))
            )
            .andExpect(status().isBadRequest());

        List<LeaveTypes> leaveTypesList = leaveTypesRepository.findAll();
        assertThat(leaveTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLeaveTypes() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList
        restLeaveTypesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].cycleType").value(hasItem(DEFAULT_CYCLE_TYPE)))
            .andExpect(jsonPath("$.[*].cycleCount").value(hasItem(DEFAULT_CYCLE_COUNT)))
            .andExpect(jsonPath("$.[*].maxQuota").value(hasItem(DEFAULT_MAX_QUOTA)))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getLeaveTypes() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get the leaveTypes
        restLeaveTypesMockMvc
            .perform(get(ENTITY_API_URL_ID, leaveTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leaveTypes.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
            .andExpect(jsonPath("$.cycleType").value(DEFAULT_CYCLE_TYPE))
            .andExpect(jsonPath("$.cycleCount").value(DEFAULT_CYCLE_COUNT))
            .andExpect(jsonPath("$.maxQuota").value(DEFAULT_MAX_QUOTA))
            .andExpect(jsonPath("$.effDate").value(DEFAULT_EFF_DATE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getLeaveTypesByIdFiltering() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        Long id = leaveTypes.getId();

        defaultLeaveTypesShouldBeFound("id.equals=" + id);
        defaultLeaveTypesShouldNotBeFound("id.notEquals=" + id);

        defaultLeaveTypesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLeaveTypesShouldNotBeFound("id.greaterThan=" + id);

        defaultLeaveTypesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLeaveTypesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where name equals to DEFAULT_NAME
        defaultLeaveTypesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the leaveTypesList where name equals to UPDATED_NAME
        defaultLeaveTypesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultLeaveTypesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the leaveTypesList where name equals to UPDATED_NAME
        defaultLeaveTypesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where name is not null
        defaultLeaveTypesShouldBeFound("name.specified=true");

        // Get all the leaveTypesList where name is null
        defaultLeaveTypesShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where name contains DEFAULT_NAME
        defaultLeaveTypesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the leaveTypesList where name contains UPDATED_NAME
        defaultLeaveTypesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where name does not contain DEFAULT_NAME
        defaultLeaveTypesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the leaveTypesList where name does not contain UPDATED_NAME
        defaultLeaveTypesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where category equals to DEFAULT_CATEGORY
        defaultLeaveTypesShouldBeFound("category.equals=" + DEFAULT_CATEGORY);

        // Get all the leaveTypesList where category equals to UPDATED_CATEGORY
        defaultLeaveTypesShouldNotBeFound("category.equals=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where category in DEFAULT_CATEGORY or UPDATED_CATEGORY
        defaultLeaveTypesShouldBeFound("category.in=" + DEFAULT_CATEGORY + "," + UPDATED_CATEGORY);

        // Get all the leaveTypesList where category equals to UPDATED_CATEGORY
        defaultLeaveTypesShouldNotBeFound("category.in=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where category is not null
        defaultLeaveTypesShouldBeFound("category.specified=true");

        // Get all the leaveTypesList where category is null
        defaultLeaveTypesShouldNotBeFound("category.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypesByCategoryContainsSomething() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where category contains DEFAULT_CATEGORY
        defaultLeaveTypesShouldBeFound("category.contains=" + DEFAULT_CATEGORY);

        // Get all the leaveTypesList where category contains UPDATED_CATEGORY
        defaultLeaveTypesShouldNotBeFound("category.contains=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByCategoryNotContainsSomething() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where category does not contain DEFAULT_CATEGORY
        defaultLeaveTypesShouldNotBeFound("category.doesNotContain=" + DEFAULT_CATEGORY);

        // Get all the leaveTypesList where category does not contain UPDATED_CATEGORY
        defaultLeaveTypesShouldBeFound("category.doesNotContain=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByCycleTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where cycleType equals to DEFAULT_CYCLE_TYPE
        defaultLeaveTypesShouldBeFound("cycleType.equals=" + DEFAULT_CYCLE_TYPE);

        // Get all the leaveTypesList where cycleType equals to UPDATED_CYCLE_TYPE
        defaultLeaveTypesShouldNotBeFound("cycleType.equals=" + UPDATED_CYCLE_TYPE);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByCycleTypeIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where cycleType in DEFAULT_CYCLE_TYPE or UPDATED_CYCLE_TYPE
        defaultLeaveTypesShouldBeFound("cycleType.in=" + DEFAULT_CYCLE_TYPE + "," + UPDATED_CYCLE_TYPE);

        // Get all the leaveTypesList where cycleType equals to UPDATED_CYCLE_TYPE
        defaultLeaveTypesShouldNotBeFound("cycleType.in=" + UPDATED_CYCLE_TYPE);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByCycleTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where cycleType is not null
        defaultLeaveTypesShouldBeFound("cycleType.specified=true");

        // Get all the leaveTypesList where cycleType is null
        defaultLeaveTypesShouldNotBeFound("cycleType.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypesByCycleTypeContainsSomething() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where cycleType contains DEFAULT_CYCLE_TYPE
        defaultLeaveTypesShouldBeFound("cycleType.contains=" + DEFAULT_CYCLE_TYPE);

        // Get all the leaveTypesList where cycleType contains UPDATED_CYCLE_TYPE
        defaultLeaveTypesShouldNotBeFound("cycleType.contains=" + UPDATED_CYCLE_TYPE);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByCycleTypeNotContainsSomething() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where cycleType does not contain DEFAULT_CYCLE_TYPE
        defaultLeaveTypesShouldNotBeFound("cycleType.doesNotContain=" + DEFAULT_CYCLE_TYPE);

        // Get all the leaveTypesList where cycleType does not contain UPDATED_CYCLE_TYPE
        defaultLeaveTypesShouldBeFound("cycleType.doesNotContain=" + UPDATED_CYCLE_TYPE);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByCycleCountIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where cycleCount equals to DEFAULT_CYCLE_COUNT
        defaultLeaveTypesShouldBeFound("cycleCount.equals=" + DEFAULT_CYCLE_COUNT);

        // Get all the leaveTypesList where cycleCount equals to UPDATED_CYCLE_COUNT
        defaultLeaveTypesShouldNotBeFound("cycleCount.equals=" + UPDATED_CYCLE_COUNT);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByCycleCountIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where cycleCount in DEFAULT_CYCLE_COUNT or UPDATED_CYCLE_COUNT
        defaultLeaveTypesShouldBeFound("cycleCount.in=" + DEFAULT_CYCLE_COUNT + "," + UPDATED_CYCLE_COUNT);

        // Get all the leaveTypesList where cycleCount equals to UPDATED_CYCLE_COUNT
        defaultLeaveTypesShouldNotBeFound("cycleCount.in=" + UPDATED_CYCLE_COUNT);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByCycleCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where cycleCount is not null
        defaultLeaveTypesShouldBeFound("cycleCount.specified=true");

        // Get all the leaveTypesList where cycleCount is null
        defaultLeaveTypesShouldNotBeFound("cycleCount.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypesByCycleCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where cycleCount is greater than or equal to DEFAULT_CYCLE_COUNT
        defaultLeaveTypesShouldBeFound("cycleCount.greaterThanOrEqual=" + DEFAULT_CYCLE_COUNT);

        // Get all the leaveTypesList where cycleCount is greater than or equal to UPDATED_CYCLE_COUNT
        defaultLeaveTypesShouldNotBeFound("cycleCount.greaterThanOrEqual=" + UPDATED_CYCLE_COUNT);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByCycleCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where cycleCount is less than or equal to DEFAULT_CYCLE_COUNT
        defaultLeaveTypesShouldBeFound("cycleCount.lessThanOrEqual=" + DEFAULT_CYCLE_COUNT);

        // Get all the leaveTypesList where cycleCount is less than or equal to SMALLER_CYCLE_COUNT
        defaultLeaveTypesShouldNotBeFound("cycleCount.lessThanOrEqual=" + SMALLER_CYCLE_COUNT);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByCycleCountIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where cycleCount is less than DEFAULT_CYCLE_COUNT
        defaultLeaveTypesShouldNotBeFound("cycleCount.lessThan=" + DEFAULT_CYCLE_COUNT);

        // Get all the leaveTypesList where cycleCount is less than UPDATED_CYCLE_COUNT
        defaultLeaveTypesShouldBeFound("cycleCount.lessThan=" + UPDATED_CYCLE_COUNT);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByCycleCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where cycleCount is greater than DEFAULT_CYCLE_COUNT
        defaultLeaveTypesShouldNotBeFound("cycleCount.greaterThan=" + DEFAULT_CYCLE_COUNT);

        // Get all the leaveTypesList where cycleCount is greater than SMALLER_CYCLE_COUNT
        defaultLeaveTypesShouldBeFound("cycleCount.greaterThan=" + SMALLER_CYCLE_COUNT);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByMaxQuotaIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where maxQuota equals to DEFAULT_MAX_QUOTA
        defaultLeaveTypesShouldBeFound("maxQuota.equals=" + DEFAULT_MAX_QUOTA);

        // Get all the leaveTypesList where maxQuota equals to UPDATED_MAX_QUOTA
        defaultLeaveTypesShouldNotBeFound("maxQuota.equals=" + UPDATED_MAX_QUOTA);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByMaxQuotaIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where maxQuota in DEFAULT_MAX_QUOTA or UPDATED_MAX_QUOTA
        defaultLeaveTypesShouldBeFound("maxQuota.in=" + DEFAULT_MAX_QUOTA + "," + UPDATED_MAX_QUOTA);

        // Get all the leaveTypesList where maxQuota equals to UPDATED_MAX_QUOTA
        defaultLeaveTypesShouldNotBeFound("maxQuota.in=" + UPDATED_MAX_QUOTA);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByMaxQuotaIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where maxQuota is not null
        defaultLeaveTypesShouldBeFound("maxQuota.specified=true");

        // Get all the leaveTypesList where maxQuota is null
        defaultLeaveTypesShouldNotBeFound("maxQuota.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypesByMaxQuotaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where maxQuota is greater than or equal to DEFAULT_MAX_QUOTA
        defaultLeaveTypesShouldBeFound("maxQuota.greaterThanOrEqual=" + DEFAULT_MAX_QUOTA);

        // Get all the leaveTypesList where maxQuota is greater than or equal to UPDATED_MAX_QUOTA
        defaultLeaveTypesShouldNotBeFound("maxQuota.greaterThanOrEqual=" + UPDATED_MAX_QUOTA);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByMaxQuotaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where maxQuota is less than or equal to DEFAULT_MAX_QUOTA
        defaultLeaveTypesShouldBeFound("maxQuota.lessThanOrEqual=" + DEFAULT_MAX_QUOTA);

        // Get all the leaveTypesList where maxQuota is less than or equal to SMALLER_MAX_QUOTA
        defaultLeaveTypesShouldNotBeFound("maxQuota.lessThanOrEqual=" + SMALLER_MAX_QUOTA);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByMaxQuotaIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where maxQuota is less than DEFAULT_MAX_QUOTA
        defaultLeaveTypesShouldNotBeFound("maxQuota.lessThan=" + DEFAULT_MAX_QUOTA);

        // Get all the leaveTypesList where maxQuota is less than UPDATED_MAX_QUOTA
        defaultLeaveTypesShouldBeFound("maxQuota.lessThan=" + UPDATED_MAX_QUOTA);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByMaxQuotaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where maxQuota is greater than DEFAULT_MAX_QUOTA
        defaultLeaveTypesShouldNotBeFound("maxQuota.greaterThan=" + DEFAULT_MAX_QUOTA);

        // Get all the leaveTypesList where maxQuota is greater than SMALLER_MAX_QUOTA
        defaultLeaveTypesShouldBeFound("maxQuota.greaterThan=" + SMALLER_MAX_QUOTA);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByEffDateIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where effDate equals to DEFAULT_EFF_DATE
        defaultLeaveTypesShouldBeFound("effDate.equals=" + DEFAULT_EFF_DATE);

        // Get all the leaveTypesList where effDate equals to UPDATED_EFF_DATE
        defaultLeaveTypesShouldNotBeFound("effDate.equals=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByEffDateIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where effDate in DEFAULT_EFF_DATE or UPDATED_EFF_DATE
        defaultLeaveTypesShouldBeFound("effDate.in=" + DEFAULT_EFF_DATE + "," + UPDATED_EFF_DATE);

        // Get all the leaveTypesList where effDate equals to UPDATED_EFF_DATE
        defaultLeaveTypesShouldNotBeFound("effDate.in=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByEffDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where effDate is not null
        defaultLeaveTypesShouldBeFound("effDate.specified=true");

        // Get all the leaveTypesList where effDate is null
        defaultLeaveTypesShouldNotBeFound("effDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypesByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where createdAt equals to DEFAULT_CREATED_AT
        defaultLeaveTypesShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the leaveTypesList where createdAt equals to UPDATED_CREATED_AT
        defaultLeaveTypesShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultLeaveTypesShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the leaveTypesList where createdAt equals to UPDATED_CREATED_AT
        defaultLeaveTypesShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where createdAt is not null
        defaultLeaveTypesShouldBeFound("createdAt.specified=true");

        // Get all the leaveTypesList where createdAt is null
        defaultLeaveTypesShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypesByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultLeaveTypesShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the leaveTypesList where updatedAt equals to UPDATED_UPDATED_AT
        defaultLeaveTypesShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultLeaveTypesShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the leaveTypesList where updatedAt equals to UPDATED_UPDATED_AT
        defaultLeaveTypesShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where updatedAt is not null
        defaultLeaveTypesShouldBeFound("updatedAt.specified=true");

        // Get all the leaveTypesList where updatedAt is null
        defaultLeaveTypesShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypesByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where endDate equals to DEFAULT_END_DATE
        defaultLeaveTypesShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the leaveTypesList where endDate equals to UPDATED_END_DATE
        defaultLeaveTypesShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultLeaveTypesShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the leaveTypesList where endDate equals to UPDATED_END_DATE
        defaultLeaveTypesShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where endDate is not null
        defaultLeaveTypesShouldBeFound("endDate.specified=true");

        // Get all the leaveTypesList where endDate is null
        defaultLeaveTypesShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where version equals to DEFAULT_VERSION
        defaultLeaveTypesShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the leaveTypesList where version equals to UPDATED_VERSION
        defaultLeaveTypesShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultLeaveTypesShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the leaveTypesList where version equals to UPDATED_VERSION
        defaultLeaveTypesShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where version is not null
        defaultLeaveTypesShouldBeFound("version.specified=true");

        // Get all the leaveTypesList where version is null
        defaultLeaveTypesShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where version is greater than or equal to DEFAULT_VERSION
        defaultLeaveTypesShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the leaveTypesList where version is greater than or equal to UPDATED_VERSION
        defaultLeaveTypesShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where version is less than or equal to DEFAULT_VERSION
        defaultLeaveTypesShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the leaveTypesList where version is less than or equal to SMALLER_VERSION
        defaultLeaveTypesShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where version is less than DEFAULT_VERSION
        defaultLeaveTypesShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the leaveTypesList where version is less than UPDATED_VERSION
        defaultLeaveTypesShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        // Get all the leaveTypesList where version is greater than DEFAULT_VERSION
        defaultLeaveTypesShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the leaveTypesList where version is greater than SMALLER_VERSION
        defaultLeaveTypesShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByLeavedetailsLeavetypeIsEqualToSomething() throws Exception {
        LeaveDetails leavedetailsLeavetype;
        if (TestUtil.findAll(em, LeaveDetails.class).isEmpty()) {
            leaveTypesRepository.saveAndFlush(leaveTypes);
            leavedetailsLeavetype = LeaveDetailsResourceIT.createEntity(em);
        } else {
            leavedetailsLeavetype = TestUtil.findAll(em, LeaveDetails.class).get(0);
        }
        em.persist(leavedetailsLeavetype);
        em.flush();
        leaveTypes.addLeavedetailsLeavetype(leavedetailsLeavetype);
        leaveTypesRepository.saveAndFlush(leaveTypes);
        Long leavedetailsLeavetypeId = leavedetailsLeavetype.getId();

        // Get all the leaveTypesList where leavedetailsLeavetype equals to leavedetailsLeavetypeId
        defaultLeaveTypesShouldBeFound("leavedetailsLeavetypeId.equals=" + leavedetailsLeavetypeId);

        // Get all the leaveTypesList where leavedetailsLeavetype equals to (leavedetailsLeavetypeId + 1)
        defaultLeaveTypesShouldNotBeFound("leavedetailsLeavetypeId.equals=" + (leavedetailsLeavetypeId + 1));
    }

    @Test
    @Transactional
    void getAllLeaveTypesByLeavetypeapprovalrulesLeavetypeIsEqualToSomething() throws Exception {
        LeaveTypeApprovalRules leavetypeapprovalrulesLeavetype;
        if (TestUtil.findAll(em, LeaveTypeApprovalRules.class).isEmpty()) {
            leaveTypesRepository.saveAndFlush(leaveTypes);
            leavetypeapprovalrulesLeavetype = LeaveTypeApprovalRulesResourceIT.createEntity(em);
        } else {
            leavetypeapprovalrulesLeavetype = TestUtil.findAll(em, LeaveTypeApprovalRules.class).get(0);
        }
        em.persist(leavetypeapprovalrulesLeavetype);
        em.flush();
        leaveTypes.addLeavetypeapprovalrulesLeavetype(leavetypeapprovalrulesLeavetype);
        leaveTypesRepository.saveAndFlush(leaveTypes);
        Long leavetypeapprovalrulesLeavetypeId = leavetypeapprovalrulesLeavetype.getId();

        // Get all the leaveTypesList where leavetypeapprovalrulesLeavetype equals to leavetypeapprovalrulesLeavetypeId
        defaultLeaveTypesShouldBeFound("leavetypeapprovalrulesLeavetypeId.equals=" + leavetypeapprovalrulesLeavetypeId);

        // Get all the leaveTypesList where leavetypeapprovalrulesLeavetype equals to (leavetypeapprovalrulesLeavetypeId + 1)
        defaultLeaveTypesShouldNotBeFound("leavetypeapprovalrulesLeavetypeId.equals=" + (leavetypeapprovalrulesLeavetypeId + 1));
    }

    @Test
    @Transactional
    void getAllLeaveTypesByLeavetypeconfigurationsLeavetypeIsEqualToSomething() throws Exception {
        LeaveTypeConfigurations leavetypeconfigurationsLeavetype;
        if (TestUtil.findAll(em, LeaveTypeConfigurations.class).isEmpty()) {
            leaveTypesRepository.saveAndFlush(leaveTypes);
            leavetypeconfigurationsLeavetype = LeaveTypeConfigurationsResourceIT.createEntity(em);
        } else {
            leavetypeconfigurationsLeavetype = TestUtil.findAll(em, LeaveTypeConfigurations.class).get(0);
        }
        em.persist(leavetypeconfigurationsLeavetype);
        em.flush();
        leaveTypes.addLeavetypeconfigurationsLeavetype(leavetypeconfigurationsLeavetype);
        leaveTypesRepository.saveAndFlush(leaveTypes);
        Long leavetypeconfigurationsLeavetypeId = leavetypeconfigurationsLeavetype.getId();

        // Get all the leaveTypesList where leavetypeconfigurationsLeavetype equals to leavetypeconfigurationsLeavetypeId
        defaultLeaveTypesShouldBeFound("leavetypeconfigurationsLeavetypeId.equals=" + leavetypeconfigurationsLeavetypeId);

        // Get all the leaveTypesList where leavetypeconfigurationsLeavetype equals to (leavetypeconfigurationsLeavetypeId + 1)
        defaultLeaveTypesShouldNotBeFound("leavetypeconfigurationsLeavetypeId.equals=" + (leavetypeconfigurationsLeavetypeId + 1));
    }

    @Test
    @Transactional
    void getAllLeaveTypesByLeavetypeescalationrulesLeavetypeIsEqualToSomething() throws Exception {
        LeaveTypeEscalationRules leavetypeescalationrulesLeavetype;
        if (TestUtil.findAll(em, LeaveTypeEscalationRules.class).isEmpty()) {
            leaveTypesRepository.saveAndFlush(leaveTypes);
            leavetypeescalationrulesLeavetype = LeaveTypeEscalationRulesResourceIT.createEntity(em);
        } else {
            leavetypeescalationrulesLeavetype = TestUtil.findAll(em, LeaveTypeEscalationRules.class).get(0);
        }
        em.persist(leavetypeescalationrulesLeavetype);
        em.flush();
        leaveTypes.addLeavetypeescalationrulesLeavetype(leavetypeescalationrulesLeavetype);
        leaveTypesRepository.saveAndFlush(leaveTypes);
        Long leavetypeescalationrulesLeavetypeId = leavetypeescalationrulesLeavetype.getId();

        // Get all the leaveTypesList where leavetypeescalationrulesLeavetype equals to leavetypeescalationrulesLeavetypeId
        defaultLeaveTypesShouldBeFound("leavetypeescalationrulesLeavetypeId.equals=" + leavetypeescalationrulesLeavetypeId);

        // Get all the leaveTypesList where leavetypeescalationrulesLeavetype equals to (leavetypeescalationrulesLeavetypeId + 1)
        defaultLeaveTypesShouldNotBeFound("leavetypeescalationrulesLeavetypeId.equals=" + (leavetypeescalationrulesLeavetypeId + 1));
    }

    @Test
    @Transactional
    void getAllLeaveTypesByLeavetyperestrictionsLeavetypeIsEqualToSomething() throws Exception {
        LeaveTypeRestrictions leavetyperestrictionsLeavetype;
        if (TestUtil.findAll(em, LeaveTypeRestrictions.class).isEmpty()) {
            leaveTypesRepository.saveAndFlush(leaveTypes);
            leavetyperestrictionsLeavetype = LeaveTypeRestrictionsResourceIT.createEntity(em);
        } else {
            leavetyperestrictionsLeavetype = TestUtil.findAll(em, LeaveTypeRestrictions.class).get(0);
        }
        em.persist(leavetyperestrictionsLeavetype);
        em.flush();
        leaveTypes.addLeavetyperestrictionsLeavetype(leavetyperestrictionsLeavetype);
        leaveTypesRepository.saveAndFlush(leaveTypes);
        Long leavetyperestrictionsLeavetypeId = leavetyperestrictionsLeavetype.getId();

        // Get all the leaveTypesList where leavetyperestrictionsLeavetype equals to leavetyperestrictionsLeavetypeId
        defaultLeaveTypesShouldBeFound("leavetyperestrictionsLeavetypeId.equals=" + leavetyperestrictionsLeavetypeId);

        // Get all the leaveTypesList where leavetyperestrictionsLeavetype equals to (leavetyperestrictionsLeavetypeId + 1)
        defaultLeaveTypesShouldNotBeFound("leavetyperestrictionsLeavetypeId.equals=" + (leavetyperestrictionsLeavetypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLeaveTypesShouldBeFound(String filter) throws Exception {
        restLeaveTypesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].cycleType").value(hasItem(DEFAULT_CYCLE_TYPE)))
            .andExpect(jsonPath("$.[*].cycleCount").value(hasItem(DEFAULT_CYCLE_COUNT)))
            .andExpect(jsonPath("$.[*].maxQuota").value(hasItem(DEFAULT_MAX_QUOTA)))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restLeaveTypesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLeaveTypesShouldNotBeFound(String filter) throws Exception {
        restLeaveTypesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLeaveTypesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLeaveTypes() throws Exception {
        // Get the leaveTypes
        restLeaveTypesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLeaveTypes() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        int databaseSizeBeforeUpdate = leaveTypesRepository.findAll().size();

        // Update the leaveTypes
        LeaveTypes updatedLeaveTypes = leaveTypesRepository.findById(leaveTypes.getId()).get();
        // Disconnect from session so that the updates on updatedLeaveTypes are not directly saved in db
        em.detach(updatedLeaveTypes);
        updatedLeaveTypes
            .name(UPDATED_NAME)
            .category(UPDATED_CATEGORY)
            .cycleType(UPDATED_CYCLE_TYPE)
            .cycleCount(UPDATED_CYCLE_COUNT)
            .maxQuota(UPDATED_MAX_QUOTA)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restLeaveTypesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLeaveTypes.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLeaveTypes))
            )
            .andExpect(status().isOk());

        // Validate the LeaveTypes in the database
        List<LeaveTypes> leaveTypesList = leaveTypesRepository.findAll();
        assertThat(leaveTypesList).hasSize(databaseSizeBeforeUpdate);
        LeaveTypes testLeaveTypes = leaveTypesList.get(leaveTypesList.size() - 1);
        assertThat(testLeaveTypes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLeaveTypes.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testLeaveTypes.getCycleType()).isEqualTo(UPDATED_CYCLE_TYPE);
        assertThat(testLeaveTypes.getCycleCount()).isEqualTo(UPDATED_CYCLE_COUNT);
        assertThat(testLeaveTypes.getMaxQuota()).isEqualTo(UPDATED_MAX_QUOTA);
        assertThat(testLeaveTypes.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testLeaveTypes.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveTypes.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaveTypes.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testLeaveTypes.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingLeaveTypes() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypesRepository.findAll().size();
        leaveTypes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveTypesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leaveTypes.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypes))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveTypes in the database
        List<LeaveTypes> leaveTypesList = leaveTypesRepository.findAll();
        assertThat(leaveTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeaveTypes() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypesRepository.findAll().size();
        leaveTypes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveTypesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypes))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveTypes in the database
        List<LeaveTypes> leaveTypesList = leaveTypesRepository.findAll();
        assertThat(leaveTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeaveTypes() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypesRepository.findAll().size();
        leaveTypes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveTypesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveTypes in the database
        List<LeaveTypes> leaveTypesList = leaveTypesRepository.findAll();
        assertThat(leaveTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeaveTypesWithPatch() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        int databaseSizeBeforeUpdate = leaveTypesRepository.findAll().size();

        // Update the leaveTypes using partial update
        LeaveTypes partialUpdatedLeaveTypes = new LeaveTypes();
        partialUpdatedLeaveTypes.setId(leaveTypes.getId());

        partialUpdatedLeaveTypes
            .name(UPDATED_NAME)
            .cycleType(UPDATED_CYCLE_TYPE)
            .cycleCount(UPDATED_CYCLE_COUNT)
            .maxQuota(UPDATED_MAX_QUOTA)
            .createdAt(UPDATED_CREATED_AT)
            .endDate(UPDATED_END_DATE);

        restLeaveTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveTypes.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveTypes))
            )
            .andExpect(status().isOk());

        // Validate the LeaveTypes in the database
        List<LeaveTypes> leaveTypesList = leaveTypesRepository.findAll();
        assertThat(leaveTypesList).hasSize(databaseSizeBeforeUpdate);
        LeaveTypes testLeaveTypes = leaveTypesList.get(leaveTypesList.size() - 1);
        assertThat(testLeaveTypes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLeaveTypes.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testLeaveTypes.getCycleType()).isEqualTo(UPDATED_CYCLE_TYPE);
        assertThat(testLeaveTypes.getCycleCount()).isEqualTo(UPDATED_CYCLE_COUNT);
        assertThat(testLeaveTypes.getMaxQuota()).isEqualTo(UPDATED_MAX_QUOTA);
        assertThat(testLeaveTypes.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testLeaveTypes.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveTypes.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testLeaveTypes.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testLeaveTypes.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateLeaveTypesWithPatch() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        int databaseSizeBeforeUpdate = leaveTypesRepository.findAll().size();

        // Update the leaveTypes using partial update
        LeaveTypes partialUpdatedLeaveTypes = new LeaveTypes();
        partialUpdatedLeaveTypes.setId(leaveTypes.getId());

        partialUpdatedLeaveTypes
            .name(UPDATED_NAME)
            .category(UPDATED_CATEGORY)
            .cycleType(UPDATED_CYCLE_TYPE)
            .cycleCount(UPDATED_CYCLE_COUNT)
            .maxQuota(UPDATED_MAX_QUOTA)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restLeaveTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveTypes.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveTypes))
            )
            .andExpect(status().isOk());

        // Validate the LeaveTypes in the database
        List<LeaveTypes> leaveTypesList = leaveTypesRepository.findAll();
        assertThat(leaveTypesList).hasSize(databaseSizeBeforeUpdate);
        LeaveTypes testLeaveTypes = leaveTypesList.get(leaveTypesList.size() - 1);
        assertThat(testLeaveTypes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLeaveTypes.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testLeaveTypes.getCycleType()).isEqualTo(UPDATED_CYCLE_TYPE);
        assertThat(testLeaveTypes.getCycleCount()).isEqualTo(UPDATED_CYCLE_COUNT);
        assertThat(testLeaveTypes.getMaxQuota()).isEqualTo(UPDATED_MAX_QUOTA);
        assertThat(testLeaveTypes.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testLeaveTypes.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveTypes.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaveTypes.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testLeaveTypes.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingLeaveTypes() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypesRepository.findAll().size();
        leaveTypes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leaveTypes.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypes))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveTypes in the database
        List<LeaveTypes> leaveTypesList = leaveTypesRepository.findAll();
        assertThat(leaveTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeaveTypes() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypesRepository.findAll().size();
        leaveTypes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypes))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveTypes in the database
        List<LeaveTypes> leaveTypesList = leaveTypesRepository.findAll();
        assertThat(leaveTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeaveTypes() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypesRepository.findAll().size();
        leaveTypes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveTypesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveTypes in the database
        List<LeaveTypes> leaveTypesList = leaveTypesRepository.findAll();
        assertThat(leaveTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLeaveTypes() throws Exception {
        // Initialize the database
        leaveTypesRepository.saveAndFlush(leaveTypes);

        int databaseSizeBeforeDelete = leaveTypesRepository.findAll().size();

        // Delete the leaveTypes
        restLeaveTypesMockMvc
            .perform(delete(ENTITY_API_URL_ID, leaveTypes.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeaveTypes> leaveTypesList = leaveTypesRepository.findAll();
        assertThat(leaveTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
