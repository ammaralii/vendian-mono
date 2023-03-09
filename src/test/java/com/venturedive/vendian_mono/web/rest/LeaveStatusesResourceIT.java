package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.LeaveRequestApproverFlows;
import com.venturedive.vendian_mono.domain.LeaveRequestApprovers;
import com.venturedive.vendian_mono.domain.LeaveRequests;
import com.venturedive.vendian_mono.domain.LeaveStatusWorkFlows;
import com.venturedive.vendian_mono.domain.LeaveStatuses;
import com.venturedive.vendian_mono.domain.LeaveTypeEscalationRules;
import com.venturedive.vendian_mono.domain.UserAttributeEscalationRules;
import com.venturedive.vendian_mono.repository.LeaveStatusesRepository;
import com.venturedive.vendian_mono.service.criteria.LeaveStatusesCriteria;
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
 * Integration tests for the {@link LeaveStatusesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeaveStatusesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LEAVE_GROUP = "AAAAAAAAAA";
    private static final String UPDATED_LEAVE_GROUP = "BBBBBBBBBB";

    private static final String DEFAULT_SYSTEM_KEY = "AAAAAAAAAA";
    private static final String UPDATED_SYSTEM_KEY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_DEFAULT = false;
    private static final Boolean UPDATED_IS_DEFAULT = true;

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

    private static final String ENTITY_API_URL = "/api/leave-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LeaveStatusesRepository leaveStatusesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeaveStatusesMockMvc;

    private LeaveStatuses leaveStatuses;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveStatuses createEntity(EntityManager em) {
        LeaveStatuses leaveStatuses = new LeaveStatuses()
            .name(DEFAULT_NAME)
            .leaveGroup(DEFAULT_LEAVE_GROUP)
            .systemKey(DEFAULT_SYSTEM_KEY)
            .isDefault(DEFAULT_IS_DEFAULT)
            .effDate(DEFAULT_EFF_DATE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .endDate(DEFAULT_END_DATE)
            .version(DEFAULT_VERSION);
        return leaveStatuses;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveStatuses createUpdatedEntity(EntityManager em) {
        LeaveStatuses leaveStatuses = new LeaveStatuses()
            .name(UPDATED_NAME)
            .leaveGroup(UPDATED_LEAVE_GROUP)
            .systemKey(UPDATED_SYSTEM_KEY)
            .isDefault(UPDATED_IS_DEFAULT)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);
        return leaveStatuses;
    }

    @BeforeEach
    public void initTest() {
        leaveStatuses = createEntity(em);
    }

    @Test
    @Transactional
    void createLeaveStatuses() throws Exception {
        int databaseSizeBeforeCreate = leaveStatusesRepository.findAll().size();
        // Create the LeaveStatuses
        restLeaveStatusesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveStatuses))
            )
            .andExpect(status().isCreated());

        // Validate the LeaveStatuses in the database
        List<LeaveStatuses> leaveStatusesList = leaveStatusesRepository.findAll();
        assertThat(leaveStatusesList).hasSize(databaseSizeBeforeCreate + 1);
        LeaveStatuses testLeaveStatuses = leaveStatusesList.get(leaveStatusesList.size() - 1);
        assertThat(testLeaveStatuses.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLeaveStatuses.getLeaveGroup()).isEqualTo(DEFAULT_LEAVE_GROUP);
        assertThat(testLeaveStatuses.getSystemKey()).isEqualTo(DEFAULT_SYSTEM_KEY);
        assertThat(testLeaveStatuses.getIsDefault()).isEqualTo(DEFAULT_IS_DEFAULT);
        assertThat(testLeaveStatuses.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testLeaveStatuses.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testLeaveStatuses.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testLeaveStatuses.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testLeaveStatuses.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createLeaveStatusesWithExistingId() throws Exception {
        // Create the LeaveStatuses with an existing ID
        leaveStatuses.setId(1L);

        int databaseSizeBeforeCreate = leaveStatusesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeaveStatusesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveStatuses))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveStatuses in the database
        List<LeaveStatuses> leaveStatusesList = leaveStatusesRepository.findAll();
        assertThat(leaveStatusesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveStatusesRepository.findAll().size();
        // set the field null
        leaveStatuses.setName(null);

        // Create the LeaveStatuses, which fails.

        restLeaveStatusesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveStatuses))
            )
            .andExpect(status().isBadRequest());

        List<LeaveStatuses> leaveStatusesList = leaveStatusesRepository.findAll();
        assertThat(leaveStatusesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLeaveGroupIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveStatusesRepository.findAll().size();
        // set the field null
        leaveStatuses.setLeaveGroup(null);

        // Create the LeaveStatuses, which fails.

        restLeaveStatusesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveStatuses))
            )
            .andExpect(status().isBadRequest());

        List<LeaveStatuses> leaveStatusesList = leaveStatusesRepository.findAll();
        assertThat(leaveStatusesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsDefaultIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveStatusesRepository.findAll().size();
        // set the field null
        leaveStatuses.setIsDefault(null);

        // Create the LeaveStatuses, which fails.

        restLeaveStatusesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveStatuses))
            )
            .andExpect(status().isBadRequest());

        List<LeaveStatuses> leaveStatusesList = leaveStatusesRepository.findAll();
        assertThat(leaveStatusesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEffDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveStatusesRepository.findAll().size();
        // set the field null
        leaveStatuses.setEffDate(null);

        // Create the LeaveStatuses, which fails.

        restLeaveStatusesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveStatuses))
            )
            .andExpect(status().isBadRequest());

        List<LeaveStatuses> leaveStatusesList = leaveStatusesRepository.findAll();
        assertThat(leaveStatusesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveStatusesRepository.findAll().size();
        // set the field null
        leaveStatuses.setCreatedAt(null);

        // Create the LeaveStatuses, which fails.

        restLeaveStatusesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveStatuses))
            )
            .andExpect(status().isBadRequest());

        List<LeaveStatuses> leaveStatusesList = leaveStatusesRepository.findAll();
        assertThat(leaveStatusesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveStatusesRepository.findAll().size();
        // set the field null
        leaveStatuses.setUpdatedAt(null);

        // Create the LeaveStatuses, which fails.

        restLeaveStatusesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveStatuses))
            )
            .andExpect(status().isBadRequest());

        List<LeaveStatuses> leaveStatusesList = leaveStatusesRepository.findAll();
        assertThat(leaveStatusesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveStatusesRepository.findAll().size();
        // set the field null
        leaveStatuses.setVersion(null);

        // Create the LeaveStatuses, which fails.

        restLeaveStatusesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveStatuses))
            )
            .andExpect(status().isBadRequest());

        List<LeaveStatuses> leaveStatusesList = leaveStatusesRepository.findAll();
        assertThat(leaveStatusesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLeaveStatuses() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        // Get all the leaveStatusesList
        restLeaveStatusesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveStatuses.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].leaveGroup").value(hasItem(DEFAULT_LEAVE_GROUP)))
            .andExpect(jsonPath("$.[*].systemKey").value(hasItem(DEFAULT_SYSTEM_KEY)))
            .andExpect(jsonPath("$.[*].isDefault").value(hasItem(DEFAULT_IS_DEFAULT.booleanValue())))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getLeaveStatuses() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        // Get the leaveStatuses
        restLeaveStatusesMockMvc
            .perform(get(ENTITY_API_URL_ID, leaveStatuses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leaveStatuses.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.leaveGroup").value(DEFAULT_LEAVE_GROUP))
            .andExpect(jsonPath("$.systemKey").value(DEFAULT_SYSTEM_KEY))
            .andExpect(jsonPath("$.isDefault").value(DEFAULT_IS_DEFAULT.booleanValue()))
            .andExpect(jsonPath("$.effDate").value(DEFAULT_EFF_DATE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getLeaveStatusesByIdFiltering() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        Long id = leaveStatuses.getId();

        defaultLeaveStatusesShouldBeFound("id.equals=" + id);
        defaultLeaveStatusesShouldNotBeFound("id.notEquals=" + id);

        defaultLeaveStatusesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLeaveStatusesShouldNotBeFound("id.greaterThan=" + id);

        defaultLeaveStatusesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLeaveStatusesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        // Get all the leaveStatusesList where name equals to DEFAULT_NAME
        defaultLeaveStatusesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the leaveStatusesList where name equals to UPDATED_NAME
        defaultLeaveStatusesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        // Get all the leaveStatusesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultLeaveStatusesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the leaveStatusesList where name equals to UPDATED_NAME
        defaultLeaveStatusesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        // Get all the leaveStatusesList where name is not null
        defaultLeaveStatusesShouldBeFound("name.specified=true");

        // Get all the leaveStatusesList where name is null
        defaultLeaveStatusesShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByNameContainsSomething() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        // Get all the leaveStatusesList where name contains DEFAULT_NAME
        defaultLeaveStatusesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the leaveStatusesList where name contains UPDATED_NAME
        defaultLeaveStatusesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        // Get all the leaveStatusesList where name does not contain DEFAULT_NAME
        defaultLeaveStatusesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the leaveStatusesList where name does not contain UPDATED_NAME
        defaultLeaveStatusesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByLeaveGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        // Get all the leaveStatusesList where leaveGroup equals to DEFAULT_LEAVE_GROUP
        defaultLeaveStatusesShouldBeFound("leaveGroup.equals=" + DEFAULT_LEAVE_GROUP);

        // Get all the leaveStatusesList where leaveGroup equals to UPDATED_LEAVE_GROUP
        defaultLeaveStatusesShouldNotBeFound("leaveGroup.equals=" + UPDATED_LEAVE_GROUP);
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByLeaveGroupIsInShouldWork() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        // Get all the leaveStatusesList where leaveGroup in DEFAULT_LEAVE_GROUP or UPDATED_LEAVE_GROUP
        defaultLeaveStatusesShouldBeFound("leaveGroup.in=" + DEFAULT_LEAVE_GROUP + "," + UPDATED_LEAVE_GROUP);

        // Get all the leaveStatusesList where leaveGroup equals to UPDATED_LEAVE_GROUP
        defaultLeaveStatusesShouldNotBeFound("leaveGroup.in=" + UPDATED_LEAVE_GROUP);
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByLeaveGroupIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        // Get all the leaveStatusesList where leaveGroup is not null
        defaultLeaveStatusesShouldBeFound("leaveGroup.specified=true");

        // Get all the leaveStatusesList where leaveGroup is null
        defaultLeaveStatusesShouldNotBeFound("leaveGroup.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByLeaveGroupContainsSomething() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        // Get all the leaveStatusesList where leaveGroup contains DEFAULT_LEAVE_GROUP
        defaultLeaveStatusesShouldBeFound("leaveGroup.contains=" + DEFAULT_LEAVE_GROUP);

        // Get all the leaveStatusesList where leaveGroup contains UPDATED_LEAVE_GROUP
        defaultLeaveStatusesShouldNotBeFound("leaveGroup.contains=" + UPDATED_LEAVE_GROUP);
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByLeaveGroupNotContainsSomething() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        // Get all the leaveStatusesList where leaveGroup does not contain DEFAULT_LEAVE_GROUP
        defaultLeaveStatusesShouldNotBeFound("leaveGroup.doesNotContain=" + DEFAULT_LEAVE_GROUP);

        // Get all the leaveStatusesList where leaveGroup does not contain UPDATED_LEAVE_GROUP
        defaultLeaveStatusesShouldBeFound("leaveGroup.doesNotContain=" + UPDATED_LEAVE_GROUP);
    }

    @Test
    @Transactional
    void getAllLeaveStatusesBySystemKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        // Get all the leaveStatusesList where systemKey equals to DEFAULT_SYSTEM_KEY
        defaultLeaveStatusesShouldBeFound("systemKey.equals=" + DEFAULT_SYSTEM_KEY);

        // Get all the leaveStatusesList where systemKey equals to UPDATED_SYSTEM_KEY
        defaultLeaveStatusesShouldNotBeFound("systemKey.equals=" + UPDATED_SYSTEM_KEY);
    }

    @Test
    @Transactional
    void getAllLeaveStatusesBySystemKeyIsInShouldWork() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        // Get all the leaveStatusesList where systemKey in DEFAULT_SYSTEM_KEY or UPDATED_SYSTEM_KEY
        defaultLeaveStatusesShouldBeFound("systemKey.in=" + DEFAULT_SYSTEM_KEY + "," + UPDATED_SYSTEM_KEY);

        // Get all the leaveStatusesList where systemKey equals to UPDATED_SYSTEM_KEY
        defaultLeaveStatusesShouldNotBeFound("systemKey.in=" + UPDATED_SYSTEM_KEY);
    }

    @Test
    @Transactional
    void getAllLeaveStatusesBySystemKeyIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        // Get all the leaveStatusesList where systemKey is not null
        defaultLeaveStatusesShouldBeFound("systemKey.specified=true");

        // Get all the leaveStatusesList where systemKey is null
        defaultLeaveStatusesShouldNotBeFound("systemKey.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveStatusesBySystemKeyContainsSomething() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        // Get all the leaveStatusesList where systemKey contains DEFAULT_SYSTEM_KEY
        defaultLeaveStatusesShouldBeFound("systemKey.contains=" + DEFAULT_SYSTEM_KEY);

        // Get all the leaveStatusesList where systemKey contains UPDATED_SYSTEM_KEY
        defaultLeaveStatusesShouldNotBeFound("systemKey.contains=" + UPDATED_SYSTEM_KEY);
    }

    @Test
    @Transactional
    void getAllLeaveStatusesBySystemKeyNotContainsSomething() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        // Get all the leaveStatusesList where systemKey does not contain DEFAULT_SYSTEM_KEY
        defaultLeaveStatusesShouldNotBeFound("systemKey.doesNotContain=" + DEFAULT_SYSTEM_KEY);

        // Get all the leaveStatusesList where systemKey does not contain UPDATED_SYSTEM_KEY
        defaultLeaveStatusesShouldBeFound("systemKey.doesNotContain=" + UPDATED_SYSTEM_KEY);
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByIsDefaultIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        // Get all the leaveStatusesList where isDefault equals to DEFAULT_IS_DEFAULT
        defaultLeaveStatusesShouldBeFound("isDefault.equals=" + DEFAULT_IS_DEFAULT);

        // Get all the leaveStatusesList where isDefault equals to UPDATED_IS_DEFAULT
        defaultLeaveStatusesShouldNotBeFound("isDefault.equals=" + UPDATED_IS_DEFAULT);
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByIsDefaultIsInShouldWork() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        // Get all the leaveStatusesList where isDefault in DEFAULT_IS_DEFAULT or UPDATED_IS_DEFAULT
        defaultLeaveStatusesShouldBeFound("isDefault.in=" + DEFAULT_IS_DEFAULT + "," + UPDATED_IS_DEFAULT);

        // Get all the leaveStatusesList where isDefault equals to UPDATED_IS_DEFAULT
        defaultLeaveStatusesShouldNotBeFound("isDefault.in=" + UPDATED_IS_DEFAULT);
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByIsDefaultIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        // Get all the leaveStatusesList where isDefault is not null
        defaultLeaveStatusesShouldBeFound("isDefault.specified=true");

        // Get all the leaveStatusesList where isDefault is null
        defaultLeaveStatusesShouldNotBeFound("isDefault.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByEffDateIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        // Get all the leaveStatusesList where effDate equals to DEFAULT_EFF_DATE
        defaultLeaveStatusesShouldBeFound("effDate.equals=" + DEFAULT_EFF_DATE);

        // Get all the leaveStatusesList where effDate equals to UPDATED_EFF_DATE
        defaultLeaveStatusesShouldNotBeFound("effDate.equals=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByEffDateIsInShouldWork() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        // Get all the leaveStatusesList where effDate in DEFAULT_EFF_DATE or UPDATED_EFF_DATE
        defaultLeaveStatusesShouldBeFound("effDate.in=" + DEFAULT_EFF_DATE + "," + UPDATED_EFF_DATE);

        // Get all the leaveStatusesList where effDate equals to UPDATED_EFF_DATE
        defaultLeaveStatusesShouldNotBeFound("effDate.in=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByEffDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        // Get all the leaveStatusesList where effDate is not null
        defaultLeaveStatusesShouldBeFound("effDate.specified=true");

        // Get all the leaveStatusesList where effDate is null
        defaultLeaveStatusesShouldNotBeFound("effDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        // Get all the leaveStatusesList where createdAt equals to DEFAULT_CREATED_AT
        defaultLeaveStatusesShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the leaveStatusesList where createdAt equals to UPDATED_CREATED_AT
        defaultLeaveStatusesShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        // Get all the leaveStatusesList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultLeaveStatusesShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the leaveStatusesList where createdAt equals to UPDATED_CREATED_AT
        defaultLeaveStatusesShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        // Get all the leaveStatusesList where createdAt is not null
        defaultLeaveStatusesShouldBeFound("createdAt.specified=true");

        // Get all the leaveStatusesList where createdAt is null
        defaultLeaveStatusesShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        // Get all the leaveStatusesList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultLeaveStatusesShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the leaveStatusesList where updatedAt equals to UPDATED_UPDATED_AT
        defaultLeaveStatusesShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        // Get all the leaveStatusesList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultLeaveStatusesShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the leaveStatusesList where updatedAt equals to UPDATED_UPDATED_AT
        defaultLeaveStatusesShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        // Get all the leaveStatusesList where updatedAt is not null
        defaultLeaveStatusesShouldBeFound("updatedAt.specified=true");

        // Get all the leaveStatusesList where updatedAt is null
        defaultLeaveStatusesShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        // Get all the leaveStatusesList where endDate equals to DEFAULT_END_DATE
        defaultLeaveStatusesShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the leaveStatusesList where endDate equals to UPDATED_END_DATE
        defaultLeaveStatusesShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        // Get all the leaveStatusesList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultLeaveStatusesShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the leaveStatusesList where endDate equals to UPDATED_END_DATE
        defaultLeaveStatusesShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        // Get all the leaveStatusesList where endDate is not null
        defaultLeaveStatusesShouldBeFound("endDate.specified=true");

        // Get all the leaveStatusesList where endDate is null
        defaultLeaveStatusesShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        // Get all the leaveStatusesList where version equals to DEFAULT_VERSION
        defaultLeaveStatusesShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the leaveStatusesList where version equals to UPDATED_VERSION
        defaultLeaveStatusesShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        // Get all the leaveStatusesList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultLeaveStatusesShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the leaveStatusesList where version equals to UPDATED_VERSION
        defaultLeaveStatusesShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        // Get all the leaveStatusesList where version is not null
        defaultLeaveStatusesShouldBeFound("version.specified=true");

        // Get all the leaveStatusesList where version is null
        defaultLeaveStatusesShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        // Get all the leaveStatusesList where version is greater than or equal to DEFAULT_VERSION
        defaultLeaveStatusesShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the leaveStatusesList where version is greater than or equal to UPDATED_VERSION
        defaultLeaveStatusesShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        // Get all the leaveStatusesList where version is less than or equal to DEFAULT_VERSION
        defaultLeaveStatusesShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the leaveStatusesList where version is less than or equal to SMALLER_VERSION
        defaultLeaveStatusesShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        // Get all the leaveStatusesList where version is less than DEFAULT_VERSION
        defaultLeaveStatusesShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the leaveStatusesList where version is less than UPDATED_VERSION
        defaultLeaveStatusesShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        // Get all the leaveStatusesList where version is greater than DEFAULT_VERSION
        defaultLeaveStatusesShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the leaveStatusesList where version is greater than SMALLER_VERSION
        defaultLeaveStatusesShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByLeaverequestapproverflowsApproverstatusIsEqualToSomething() throws Exception {
        LeaveRequestApproverFlows leaverequestapproverflowsApproverstatus;
        if (TestUtil.findAll(em, LeaveRequestApproverFlows.class).isEmpty()) {
            leaveStatusesRepository.saveAndFlush(leaveStatuses);
            leaverequestapproverflowsApproverstatus = LeaveRequestApproverFlowsResourceIT.createEntity(em);
        } else {
            leaverequestapproverflowsApproverstatus = TestUtil.findAll(em, LeaveRequestApproverFlows.class).get(0);
        }
        em.persist(leaverequestapproverflowsApproverstatus);
        em.flush();
        leaveStatuses.addLeaverequestapproverflowsApproverstatus(leaverequestapproverflowsApproverstatus);
        leaveStatusesRepository.saveAndFlush(leaveStatuses);
        Long leaverequestapproverflowsApproverstatusId = leaverequestapproverflowsApproverstatus.getId();

        // Get all the leaveStatusesList where leaverequestapproverflowsApproverstatus equals to leaverequestapproverflowsApproverstatusId
        defaultLeaveStatusesShouldBeFound("leaverequestapproverflowsApproverstatusId.equals=" + leaverequestapproverflowsApproverstatusId);

        // Get all the leaveStatusesList where leaverequestapproverflowsApproverstatus equals to (leaverequestapproverflowsApproverstatusId + 1)
        defaultLeaveStatusesShouldNotBeFound(
            "leaverequestapproverflowsApproverstatusId.equals=" + (leaverequestapproverflowsApproverstatusId + 1)
        );
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByLeaverequestapproverflowsCurrentleaverequeststatusIsEqualToSomething() throws Exception {
        LeaveRequestApproverFlows leaverequestapproverflowsCurrentleaverequeststatus;
        if (TestUtil.findAll(em, LeaveRequestApproverFlows.class).isEmpty()) {
            leaveStatusesRepository.saveAndFlush(leaveStatuses);
            leaverequestapproverflowsCurrentleaverequeststatus = LeaveRequestApproverFlowsResourceIT.createEntity(em);
        } else {
            leaverequestapproverflowsCurrentleaverequeststatus = TestUtil.findAll(em, LeaveRequestApproverFlows.class).get(0);
        }
        em.persist(leaverequestapproverflowsCurrentleaverequeststatus);
        em.flush();
        leaveStatuses.addLeaverequestapproverflowsCurrentleaverequeststatus(leaverequestapproverflowsCurrentleaverequeststatus);
        leaveStatusesRepository.saveAndFlush(leaveStatuses);
        Long leaverequestapproverflowsCurrentleaverequeststatusId = leaverequestapproverflowsCurrentleaverequeststatus.getId();

        // Get all the leaveStatusesList where leaverequestapproverflowsCurrentleaverequeststatus equals to leaverequestapproverflowsCurrentleaverequeststatusId
        defaultLeaveStatusesShouldBeFound(
            "leaverequestapproverflowsCurrentleaverequeststatusId.equals=" + leaverequestapproverflowsCurrentleaverequeststatusId
        );

        // Get all the leaveStatusesList where leaverequestapproverflowsCurrentleaverequeststatus equals to (leaverequestapproverflowsCurrentleaverequeststatusId + 1)
        defaultLeaveStatusesShouldNotBeFound(
            "leaverequestapproverflowsCurrentleaverequeststatusId.equals=" + (leaverequestapproverflowsCurrentleaverequeststatusId + 1)
        );
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByLeaverequestapproverflowsNextleaverequeststatusIsEqualToSomething() throws Exception {
        LeaveRequestApproverFlows leaverequestapproverflowsNextleaverequeststatus;
        if (TestUtil.findAll(em, LeaveRequestApproverFlows.class).isEmpty()) {
            leaveStatusesRepository.saveAndFlush(leaveStatuses);
            leaverequestapproverflowsNextleaverequeststatus = LeaveRequestApproverFlowsResourceIT.createEntity(em);
        } else {
            leaverequestapproverflowsNextleaverequeststatus = TestUtil.findAll(em, LeaveRequestApproverFlows.class).get(0);
        }
        em.persist(leaverequestapproverflowsNextleaverequeststatus);
        em.flush();
        leaveStatuses.addLeaverequestapproverflowsNextleaverequeststatus(leaverequestapproverflowsNextleaverequeststatus);
        leaveStatusesRepository.saveAndFlush(leaveStatuses);
        Long leaverequestapproverflowsNextleaverequeststatusId = leaverequestapproverflowsNextleaverequeststatus.getId();

        // Get all the leaveStatusesList where leaverequestapproverflowsNextleaverequeststatus equals to leaverequestapproverflowsNextleaverequeststatusId
        defaultLeaveStatusesShouldBeFound(
            "leaverequestapproverflowsNextleaverequeststatusId.equals=" + leaverequestapproverflowsNextleaverequeststatusId
        );

        // Get all the leaveStatusesList where leaverequestapproverflowsNextleaverequeststatus equals to (leaverequestapproverflowsNextleaverequeststatusId + 1)
        defaultLeaveStatusesShouldNotBeFound(
            "leaverequestapproverflowsNextleaverequeststatusId.equals=" + (leaverequestapproverflowsNextleaverequeststatusId + 1)
        );
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByLeaverequestapproversStatusIsEqualToSomething() throws Exception {
        LeaveRequestApprovers leaverequestapproversStatus;
        if (TestUtil.findAll(em, LeaveRequestApprovers.class).isEmpty()) {
            leaveStatusesRepository.saveAndFlush(leaveStatuses);
            leaverequestapproversStatus = LeaveRequestApproversResourceIT.createEntity(em);
        } else {
            leaverequestapproversStatus = TestUtil.findAll(em, LeaveRequestApprovers.class).get(0);
        }
        em.persist(leaverequestapproversStatus);
        em.flush();
        leaveStatuses.addLeaverequestapproversStatus(leaverequestapproversStatus);
        leaveStatusesRepository.saveAndFlush(leaveStatuses);
        Long leaverequestapproversStatusId = leaverequestapproversStatus.getId();

        // Get all the leaveStatusesList where leaverequestapproversStatus equals to leaverequestapproversStatusId
        defaultLeaveStatusesShouldBeFound("leaverequestapproversStatusId.equals=" + leaverequestapproversStatusId);

        // Get all the leaveStatusesList where leaverequestapproversStatus equals to (leaverequestapproversStatusId + 1)
        defaultLeaveStatusesShouldNotBeFound("leaverequestapproversStatusId.equals=" + (leaverequestapproversStatusId + 1));
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByLeaverequestsLeavestatusIsEqualToSomething() throws Exception {
        LeaveRequests leaverequestsLeavestatus;
        if (TestUtil.findAll(em, LeaveRequests.class).isEmpty()) {
            leaveStatusesRepository.saveAndFlush(leaveStatuses);
            leaverequestsLeavestatus = LeaveRequestsResourceIT.createEntity(em);
        } else {
            leaverequestsLeavestatus = TestUtil.findAll(em, LeaveRequests.class).get(0);
        }
        em.persist(leaverequestsLeavestatus);
        em.flush();
        leaveStatuses.addLeaverequestsLeavestatus(leaverequestsLeavestatus);
        leaveStatusesRepository.saveAndFlush(leaveStatuses);
        Long leaverequestsLeavestatusId = leaverequestsLeavestatus.getId();

        // Get all the leaveStatusesList where leaverequestsLeavestatus equals to leaverequestsLeavestatusId
        defaultLeaveStatusesShouldBeFound("leaverequestsLeavestatusId.equals=" + leaverequestsLeavestatusId);

        // Get all the leaveStatusesList where leaverequestsLeavestatus equals to (leaverequestsLeavestatusId + 1)
        defaultLeaveStatusesShouldNotBeFound("leaverequestsLeavestatusId.equals=" + (leaverequestsLeavestatusId + 1));
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByLeavestatusworkflowsCurrentstatusIsEqualToSomething() throws Exception {
        LeaveStatusWorkFlows leavestatusworkflowsCurrentstatus;
        if (TestUtil.findAll(em, LeaveStatusWorkFlows.class).isEmpty()) {
            leaveStatusesRepository.saveAndFlush(leaveStatuses);
            leavestatusworkflowsCurrentstatus = LeaveStatusWorkFlowsResourceIT.createEntity(em);
        } else {
            leavestatusworkflowsCurrentstatus = TestUtil.findAll(em, LeaveStatusWorkFlows.class).get(0);
        }
        em.persist(leavestatusworkflowsCurrentstatus);
        em.flush();
        leaveStatuses.addLeavestatusworkflowsCurrentstatus(leavestatusworkflowsCurrentstatus);
        leaveStatusesRepository.saveAndFlush(leaveStatuses);
        Long leavestatusworkflowsCurrentstatusId = leavestatusworkflowsCurrentstatus.getId();

        // Get all the leaveStatusesList where leavestatusworkflowsCurrentstatus equals to leavestatusworkflowsCurrentstatusId
        defaultLeaveStatusesShouldBeFound("leavestatusworkflowsCurrentstatusId.equals=" + leavestatusworkflowsCurrentstatusId);

        // Get all the leaveStatusesList where leavestatusworkflowsCurrentstatus equals to (leavestatusworkflowsCurrentstatusId + 1)
        defaultLeaveStatusesShouldNotBeFound("leavestatusworkflowsCurrentstatusId.equals=" + (leavestatusworkflowsCurrentstatusId + 1));
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByLeavestatusworkflowsNextstatusIsEqualToSomething() throws Exception {
        LeaveStatusWorkFlows leavestatusworkflowsNextstatus;
        if (TestUtil.findAll(em, LeaveStatusWorkFlows.class).isEmpty()) {
            leaveStatusesRepository.saveAndFlush(leaveStatuses);
            leavestatusworkflowsNextstatus = LeaveStatusWorkFlowsResourceIT.createEntity(em);
        } else {
            leavestatusworkflowsNextstatus = TestUtil.findAll(em, LeaveStatusWorkFlows.class).get(0);
        }
        em.persist(leavestatusworkflowsNextstatus);
        em.flush();
        leaveStatuses.addLeavestatusworkflowsNextstatus(leavestatusworkflowsNextstatus);
        leaveStatusesRepository.saveAndFlush(leaveStatuses);
        Long leavestatusworkflowsNextstatusId = leavestatusworkflowsNextstatus.getId();

        // Get all the leaveStatusesList where leavestatusworkflowsNextstatus equals to leavestatusworkflowsNextstatusId
        defaultLeaveStatusesShouldBeFound("leavestatusworkflowsNextstatusId.equals=" + leavestatusworkflowsNextstatusId);

        // Get all the leaveStatusesList where leavestatusworkflowsNextstatus equals to (leavestatusworkflowsNextstatusId + 1)
        defaultLeaveStatusesShouldNotBeFound("leavestatusworkflowsNextstatusId.equals=" + (leavestatusworkflowsNextstatusId + 1));
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByLeavestatusworkflowsSkiptostatusIsEqualToSomething() throws Exception {
        LeaveStatusWorkFlows leavestatusworkflowsSkiptostatus;
        if (TestUtil.findAll(em, LeaveStatusWorkFlows.class).isEmpty()) {
            leaveStatusesRepository.saveAndFlush(leaveStatuses);
            leavestatusworkflowsSkiptostatus = LeaveStatusWorkFlowsResourceIT.createEntity(em);
        } else {
            leavestatusworkflowsSkiptostatus = TestUtil.findAll(em, LeaveStatusWorkFlows.class).get(0);
        }
        em.persist(leavestatusworkflowsSkiptostatus);
        em.flush();
        leaveStatuses.addLeavestatusworkflowsSkiptostatus(leavestatusworkflowsSkiptostatus);
        leaveStatusesRepository.saveAndFlush(leaveStatuses);
        Long leavestatusworkflowsSkiptostatusId = leavestatusworkflowsSkiptostatus.getId();

        // Get all the leaveStatusesList where leavestatusworkflowsSkiptostatus equals to leavestatusworkflowsSkiptostatusId
        defaultLeaveStatusesShouldBeFound("leavestatusworkflowsSkiptostatusId.equals=" + leavestatusworkflowsSkiptostatusId);

        // Get all the leaveStatusesList where leavestatusworkflowsSkiptostatus equals to (leavestatusworkflowsSkiptostatusId + 1)
        defaultLeaveStatusesShouldNotBeFound("leavestatusworkflowsSkiptostatusId.equals=" + (leavestatusworkflowsSkiptostatusId + 1));
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByLeavetypeescalationrulesLeaverequeststatusIsEqualToSomething() throws Exception {
        LeaveTypeEscalationRules leavetypeescalationrulesLeaverequeststatus;
        if (TestUtil.findAll(em, LeaveTypeEscalationRules.class).isEmpty()) {
            leaveStatusesRepository.saveAndFlush(leaveStatuses);
            leavetypeescalationrulesLeaverequeststatus = LeaveTypeEscalationRulesResourceIT.createEntity(em);
        } else {
            leavetypeescalationrulesLeaverequeststatus = TestUtil.findAll(em, LeaveTypeEscalationRules.class).get(0);
        }
        em.persist(leavetypeescalationrulesLeaverequeststatus);
        em.flush();
        leaveStatuses.addLeavetypeescalationrulesLeaverequeststatus(leavetypeescalationrulesLeaverequeststatus);
        leaveStatusesRepository.saveAndFlush(leaveStatuses);
        Long leavetypeescalationrulesLeaverequeststatusId = leavetypeescalationrulesLeaverequeststatus.getId();

        // Get all the leaveStatusesList where leavetypeescalationrulesLeaverequeststatus equals to leavetypeescalationrulesLeaverequeststatusId
        defaultLeaveStatusesShouldBeFound(
            "leavetypeescalationrulesLeaverequeststatusId.equals=" + leavetypeescalationrulesLeaverequeststatusId
        );

        // Get all the leaveStatusesList where leavetypeescalationrulesLeaverequeststatus equals to (leavetypeescalationrulesLeaverequeststatusId + 1)
        defaultLeaveStatusesShouldNotBeFound(
            "leavetypeescalationrulesLeaverequeststatusId.equals=" + (leavetypeescalationrulesLeaverequeststatusId + 1)
        );
    }

    @Test
    @Transactional
    void getAllLeaveStatusesByUserattributeescalationrulesApproverstatusIsEqualToSomething() throws Exception {
        UserAttributeEscalationRules userattributeescalationrulesApproverstatus;
        if (TestUtil.findAll(em, UserAttributeEscalationRules.class).isEmpty()) {
            leaveStatusesRepository.saveAndFlush(leaveStatuses);
            userattributeescalationrulesApproverstatus = UserAttributeEscalationRulesResourceIT.createEntity(em);
        } else {
            userattributeescalationrulesApproverstatus = TestUtil.findAll(em, UserAttributeEscalationRules.class).get(0);
        }
        em.persist(userattributeescalationrulesApproverstatus);
        em.flush();
        leaveStatuses.addUserattributeescalationrulesApproverstatus(userattributeescalationrulesApproverstatus);
        leaveStatusesRepository.saveAndFlush(leaveStatuses);
        Long userattributeescalationrulesApproverstatusId = userattributeescalationrulesApproverstatus.getId();

        // Get all the leaveStatusesList where userattributeescalationrulesApproverstatus equals to userattributeescalationrulesApproverstatusId
        defaultLeaveStatusesShouldBeFound(
            "userattributeescalationrulesApproverstatusId.equals=" + userattributeescalationrulesApproverstatusId
        );

        // Get all the leaveStatusesList where userattributeescalationrulesApproverstatus equals to (userattributeescalationrulesApproverstatusId + 1)
        defaultLeaveStatusesShouldNotBeFound(
            "userattributeescalationrulesApproverstatusId.equals=" + (userattributeescalationrulesApproverstatusId + 1)
        );
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLeaveStatusesShouldBeFound(String filter) throws Exception {
        restLeaveStatusesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveStatuses.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].leaveGroup").value(hasItem(DEFAULT_LEAVE_GROUP)))
            .andExpect(jsonPath("$.[*].systemKey").value(hasItem(DEFAULT_SYSTEM_KEY)))
            .andExpect(jsonPath("$.[*].isDefault").value(hasItem(DEFAULT_IS_DEFAULT.booleanValue())))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restLeaveStatusesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLeaveStatusesShouldNotBeFound(String filter) throws Exception {
        restLeaveStatusesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLeaveStatusesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLeaveStatuses() throws Exception {
        // Get the leaveStatuses
        restLeaveStatusesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLeaveStatuses() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        int databaseSizeBeforeUpdate = leaveStatusesRepository.findAll().size();

        // Update the leaveStatuses
        LeaveStatuses updatedLeaveStatuses = leaveStatusesRepository.findById(leaveStatuses.getId()).get();
        // Disconnect from session so that the updates on updatedLeaveStatuses are not directly saved in db
        em.detach(updatedLeaveStatuses);
        updatedLeaveStatuses
            .name(UPDATED_NAME)
            .leaveGroup(UPDATED_LEAVE_GROUP)
            .systemKey(UPDATED_SYSTEM_KEY)
            .isDefault(UPDATED_IS_DEFAULT)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restLeaveStatusesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLeaveStatuses.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLeaveStatuses))
            )
            .andExpect(status().isOk());

        // Validate the LeaveStatuses in the database
        List<LeaveStatuses> leaveStatusesList = leaveStatusesRepository.findAll();
        assertThat(leaveStatusesList).hasSize(databaseSizeBeforeUpdate);
        LeaveStatuses testLeaveStatuses = leaveStatusesList.get(leaveStatusesList.size() - 1);
        assertThat(testLeaveStatuses.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLeaveStatuses.getLeaveGroup()).isEqualTo(UPDATED_LEAVE_GROUP);
        assertThat(testLeaveStatuses.getSystemKey()).isEqualTo(UPDATED_SYSTEM_KEY);
        assertThat(testLeaveStatuses.getIsDefault()).isEqualTo(UPDATED_IS_DEFAULT);
        assertThat(testLeaveStatuses.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testLeaveStatuses.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveStatuses.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaveStatuses.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testLeaveStatuses.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingLeaveStatuses() throws Exception {
        int databaseSizeBeforeUpdate = leaveStatusesRepository.findAll().size();
        leaveStatuses.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveStatusesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leaveStatuses.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveStatuses))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveStatuses in the database
        List<LeaveStatuses> leaveStatusesList = leaveStatusesRepository.findAll();
        assertThat(leaveStatusesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeaveStatuses() throws Exception {
        int databaseSizeBeforeUpdate = leaveStatusesRepository.findAll().size();
        leaveStatuses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveStatusesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveStatuses))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveStatuses in the database
        List<LeaveStatuses> leaveStatusesList = leaveStatusesRepository.findAll();
        assertThat(leaveStatusesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeaveStatuses() throws Exception {
        int databaseSizeBeforeUpdate = leaveStatusesRepository.findAll().size();
        leaveStatuses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveStatusesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveStatuses))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveStatuses in the database
        List<LeaveStatuses> leaveStatusesList = leaveStatusesRepository.findAll();
        assertThat(leaveStatusesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeaveStatusesWithPatch() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        int databaseSizeBeforeUpdate = leaveStatusesRepository.findAll().size();

        // Update the leaveStatuses using partial update
        LeaveStatuses partialUpdatedLeaveStatuses = new LeaveStatuses();
        partialUpdatedLeaveStatuses.setId(leaveStatuses.getId());

        partialUpdatedLeaveStatuses.name(UPDATED_NAME).createdAt(UPDATED_CREATED_AT).endDate(UPDATED_END_DATE).version(UPDATED_VERSION);

        restLeaveStatusesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveStatuses.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveStatuses))
            )
            .andExpect(status().isOk());

        // Validate the LeaveStatuses in the database
        List<LeaveStatuses> leaveStatusesList = leaveStatusesRepository.findAll();
        assertThat(leaveStatusesList).hasSize(databaseSizeBeforeUpdate);
        LeaveStatuses testLeaveStatuses = leaveStatusesList.get(leaveStatusesList.size() - 1);
        assertThat(testLeaveStatuses.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLeaveStatuses.getLeaveGroup()).isEqualTo(DEFAULT_LEAVE_GROUP);
        assertThat(testLeaveStatuses.getSystemKey()).isEqualTo(DEFAULT_SYSTEM_KEY);
        assertThat(testLeaveStatuses.getIsDefault()).isEqualTo(DEFAULT_IS_DEFAULT);
        assertThat(testLeaveStatuses.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testLeaveStatuses.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveStatuses.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testLeaveStatuses.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testLeaveStatuses.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateLeaveStatusesWithPatch() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        int databaseSizeBeforeUpdate = leaveStatusesRepository.findAll().size();

        // Update the leaveStatuses using partial update
        LeaveStatuses partialUpdatedLeaveStatuses = new LeaveStatuses();
        partialUpdatedLeaveStatuses.setId(leaveStatuses.getId());

        partialUpdatedLeaveStatuses
            .name(UPDATED_NAME)
            .leaveGroup(UPDATED_LEAVE_GROUP)
            .systemKey(UPDATED_SYSTEM_KEY)
            .isDefault(UPDATED_IS_DEFAULT)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restLeaveStatusesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveStatuses.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveStatuses))
            )
            .andExpect(status().isOk());

        // Validate the LeaveStatuses in the database
        List<LeaveStatuses> leaveStatusesList = leaveStatusesRepository.findAll();
        assertThat(leaveStatusesList).hasSize(databaseSizeBeforeUpdate);
        LeaveStatuses testLeaveStatuses = leaveStatusesList.get(leaveStatusesList.size() - 1);
        assertThat(testLeaveStatuses.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLeaveStatuses.getLeaveGroup()).isEqualTo(UPDATED_LEAVE_GROUP);
        assertThat(testLeaveStatuses.getSystemKey()).isEqualTo(UPDATED_SYSTEM_KEY);
        assertThat(testLeaveStatuses.getIsDefault()).isEqualTo(UPDATED_IS_DEFAULT);
        assertThat(testLeaveStatuses.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testLeaveStatuses.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveStatuses.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaveStatuses.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testLeaveStatuses.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingLeaveStatuses() throws Exception {
        int databaseSizeBeforeUpdate = leaveStatusesRepository.findAll().size();
        leaveStatuses.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveStatusesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leaveStatuses.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveStatuses))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveStatuses in the database
        List<LeaveStatuses> leaveStatusesList = leaveStatusesRepository.findAll();
        assertThat(leaveStatusesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeaveStatuses() throws Exception {
        int databaseSizeBeforeUpdate = leaveStatusesRepository.findAll().size();
        leaveStatuses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveStatusesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveStatuses))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveStatuses in the database
        List<LeaveStatuses> leaveStatusesList = leaveStatusesRepository.findAll();
        assertThat(leaveStatusesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeaveStatuses() throws Exception {
        int databaseSizeBeforeUpdate = leaveStatusesRepository.findAll().size();
        leaveStatuses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveStatusesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveStatuses))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveStatuses in the database
        List<LeaveStatuses> leaveStatusesList = leaveStatusesRepository.findAll();
        assertThat(leaveStatusesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLeaveStatuses() throws Exception {
        // Initialize the database
        leaveStatusesRepository.saveAndFlush(leaveStatuses);

        int databaseSizeBeforeDelete = leaveStatusesRepository.findAll().size();

        // Delete the leaveStatuses
        restLeaveStatusesMockMvc
            .perform(delete(ENTITY_API_URL_ID, leaveStatuses.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeaveStatuses> leaveStatusesList = leaveStatusesRepository.findAll();
        assertThat(leaveStatusesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
