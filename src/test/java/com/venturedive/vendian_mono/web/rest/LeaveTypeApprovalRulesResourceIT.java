package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.LeaveApprovalCriterias;
import com.venturedive.vendian_mono.domain.LeaveTypeApprovalRules;
import com.venturedive.vendian_mono.domain.LeaveTypes;
import com.venturedive.vendian_mono.repository.LeaveTypeApprovalRulesRepository;
import com.venturedive.vendian_mono.service.criteria.LeaveTypeApprovalRulesCriteria;
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
 * Integration tests for the {@link LeaveTypeApprovalRulesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeaveTypeApprovalRulesResourceIT {

    private static final Instant DEFAULT_EFF_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EFF_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;
    private static final Integer SMALLER_VERSION = 1 - 1;

    private static final String ENTITY_API_URL = "/api/leave-type-approval-rules";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LeaveTypeApprovalRulesRepository leaveTypeApprovalRulesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeaveTypeApprovalRulesMockMvc;

    private LeaveTypeApprovalRules leaveTypeApprovalRules;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveTypeApprovalRules createEntity(EntityManager em) {
        LeaveTypeApprovalRules leaveTypeApprovalRules = new LeaveTypeApprovalRules()
            .effDate(DEFAULT_EFF_DATE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT)
            .version(DEFAULT_VERSION);
        // Add required entity
        LeaveApprovalCriterias leaveApprovalCriterias;
        if (TestUtil.findAll(em, LeaveApprovalCriterias.class).isEmpty()) {
            leaveApprovalCriterias = LeaveApprovalCriteriasResourceIT.createEntity(em);
            em.persist(leaveApprovalCriterias);
            em.flush();
        } else {
            leaveApprovalCriterias = TestUtil.findAll(em, LeaveApprovalCriterias.class).get(0);
        }
        leaveTypeApprovalRules.setLeaveApprovalCriteria(leaveApprovalCriterias);
        // Add required entity
        LeaveTypes leaveTypes;
        if (TestUtil.findAll(em, LeaveTypes.class).isEmpty()) {
            leaveTypes = LeaveTypesResourceIT.createEntity(em);
            em.persist(leaveTypes);
            em.flush();
        } else {
            leaveTypes = TestUtil.findAll(em, LeaveTypes.class).get(0);
        }
        leaveTypeApprovalRules.setLeaveType(leaveTypes);
        return leaveTypeApprovalRules;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveTypeApprovalRules createUpdatedEntity(EntityManager em) {
        LeaveTypeApprovalRules leaveTypeApprovalRules = new LeaveTypeApprovalRules()
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);
        // Add required entity
        LeaveApprovalCriterias leaveApprovalCriterias;
        if (TestUtil.findAll(em, LeaveApprovalCriterias.class).isEmpty()) {
            leaveApprovalCriterias = LeaveApprovalCriteriasResourceIT.createUpdatedEntity(em);
            em.persist(leaveApprovalCriterias);
            em.flush();
        } else {
            leaveApprovalCriterias = TestUtil.findAll(em, LeaveApprovalCriterias.class).get(0);
        }
        leaveTypeApprovalRules.setLeaveApprovalCriteria(leaveApprovalCriterias);
        // Add required entity
        LeaveTypes leaveTypes;
        if (TestUtil.findAll(em, LeaveTypes.class).isEmpty()) {
            leaveTypes = LeaveTypesResourceIT.createUpdatedEntity(em);
            em.persist(leaveTypes);
            em.flush();
        } else {
            leaveTypes = TestUtil.findAll(em, LeaveTypes.class).get(0);
        }
        leaveTypeApprovalRules.setLeaveType(leaveTypes);
        return leaveTypeApprovalRules;
    }

    @BeforeEach
    public void initTest() {
        leaveTypeApprovalRules = createEntity(em);
    }

    @Test
    @Transactional
    void createLeaveTypeApprovalRules() throws Exception {
        int databaseSizeBeforeCreate = leaveTypeApprovalRulesRepository.findAll().size();
        // Create the LeaveTypeApprovalRules
        restLeaveTypeApprovalRulesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeApprovalRules))
            )
            .andExpect(status().isCreated());

        // Validate the LeaveTypeApprovalRules in the database
        List<LeaveTypeApprovalRules> leaveTypeApprovalRulesList = leaveTypeApprovalRulesRepository.findAll();
        assertThat(leaveTypeApprovalRulesList).hasSize(databaseSizeBeforeCreate + 1);
        LeaveTypeApprovalRules testLeaveTypeApprovalRules = leaveTypeApprovalRulesList.get(leaveTypeApprovalRulesList.size() - 1);
        assertThat(testLeaveTypeApprovalRules.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testLeaveTypeApprovalRules.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testLeaveTypeApprovalRules.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testLeaveTypeApprovalRules.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testLeaveTypeApprovalRules.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createLeaveTypeApprovalRulesWithExistingId() throws Exception {
        // Create the LeaveTypeApprovalRules with an existing ID
        leaveTypeApprovalRules.setId(1L);

        int databaseSizeBeforeCreate = leaveTypeApprovalRulesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeaveTypeApprovalRulesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeApprovalRules))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveTypeApprovalRules in the database
        List<LeaveTypeApprovalRules> leaveTypeApprovalRulesList = leaveTypeApprovalRulesRepository.findAll();
        assertThat(leaveTypeApprovalRulesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEffDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveTypeApprovalRulesRepository.findAll().size();
        // set the field null
        leaveTypeApprovalRules.setEffDate(null);

        // Create the LeaveTypeApprovalRules, which fails.

        restLeaveTypeApprovalRulesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeApprovalRules))
            )
            .andExpect(status().isBadRequest());

        List<LeaveTypeApprovalRules> leaveTypeApprovalRulesList = leaveTypeApprovalRulesRepository.findAll();
        assertThat(leaveTypeApprovalRulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveTypeApprovalRulesRepository.findAll().size();
        // set the field null
        leaveTypeApprovalRules.setCreatedAt(null);

        // Create the LeaveTypeApprovalRules, which fails.

        restLeaveTypeApprovalRulesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeApprovalRules))
            )
            .andExpect(status().isBadRequest());

        List<LeaveTypeApprovalRules> leaveTypeApprovalRulesList = leaveTypeApprovalRulesRepository.findAll();
        assertThat(leaveTypeApprovalRulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveTypeApprovalRulesRepository.findAll().size();
        // set the field null
        leaveTypeApprovalRules.setUpdatedAt(null);

        // Create the LeaveTypeApprovalRules, which fails.

        restLeaveTypeApprovalRulesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeApprovalRules))
            )
            .andExpect(status().isBadRequest());

        List<LeaveTypeApprovalRules> leaveTypeApprovalRulesList = leaveTypeApprovalRulesRepository.findAll();
        assertThat(leaveTypeApprovalRulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveTypeApprovalRulesRepository.findAll().size();
        // set the field null
        leaveTypeApprovalRules.setVersion(null);

        // Create the LeaveTypeApprovalRules, which fails.

        restLeaveTypeApprovalRulesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeApprovalRules))
            )
            .andExpect(status().isBadRequest());

        List<LeaveTypeApprovalRules> leaveTypeApprovalRulesList = leaveTypeApprovalRulesRepository.findAll();
        assertThat(leaveTypeApprovalRulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLeaveTypeApprovalRules() throws Exception {
        // Initialize the database
        leaveTypeApprovalRulesRepository.saveAndFlush(leaveTypeApprovalRules);

        // Get all the leaveTypeApprovalRulesList
        restLeaveTypeApprovalRulesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveTypeApprovalRules.getId().intValue())))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getLeaveTypeApprovalRules() throws Exception {
        // Initialize the database
        leaveTypeApprovalRulesRepository.saveAndFlush(leaveTypeApprovalRules);

        // Get the leaveTypeApprovalRules
        restLeaveTypeApprovalRulesMockMvc
            .perform(get(ENTITY_API_URL_ID, leaveTypeApprovalRules.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leaveTypeApprovalRules.getId().intValue()))
            .andExpect(jsonPath("$.effDate").value(DEFAULT_EFF_DATE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getLeaveTypeApprovalRulesByIdFiltering() throws Exception {
        // Initialize the database
        leaveTypeApprovalRulesRepository.saveAndFlush(leaveTypeApprovalRules);

        Long id = leaveTypeApprovalRules.getId();

        defaultLeaveTypeApprovalRulesShouldBeFound("id.equals=" + id);
        defaultLeaveTypeApprovalRulesShouldNotBeFound("id.notEquals=" + id);

        defaultLeaveTypeApprovalRulesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLeaveTypeApprovalRulesShouldNotBeFound("id.greaterThan=" + id);

        defaultLeaveTypeApprovalRulesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLeaveTypeApprovalRulesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLeaveTypeApprovalRulesByEffDateIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeApprovalRulesRepository.saveAndFlush(leaveTypeApprovalRules);

        // Get all the leaveTypeApprovalRulesList where effDate equals to DEFAULT_EFF_DATE
        defaultLeaveTypeApprovalRulesShouldBeFound("effDate.equals=" + DEFAULT_EFF_DATE);

        // Get all the leaveTypeApprovalRulesList where effDate equals to UPDATED_EFF_DATE
        defaultLeaveTypeApprovalRulesShouldNotBeFound("effDate.equals=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveTypeApprovalRulesByEffDateIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeApprovalRulesRepository.saveAndFlush(leaveTypeApprovalRules);

        // Get all the leaveTypeApprovalRulesList where effDate in DEFAULT_EFF_DATE or UPDATED_EFF_DATE
        defaultLeaveTypeApprovalRulesShouldBeFound("effDate.in=" + DEFAULT_EFF_DATE + "," + UPDATED_EFF_DATE);

        // Get all the leaveTypeApprovalRulesList where effDate equals to UPDATED_EFF_DATE
        defaultLeaveTypeApprovalRulesShouldNotBeFound("effDate.in=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveTypeApprovalRulesByEffDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeApprovalRulesRepository.saveAndFlush(leaveTypeApprovalRules);

        // Get all the leaveTypeApprovalRulesList where effDate is not null
        defaultLeaveTypeApprovalRulesShouldBeFound("effDate.specified=true");

        // Get all the leaveTypeApprovalRulesList where effDate is null
        defaultLeaveTypeApprovalRulesShouldNotBeFound("effDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypeApprovalRulesByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeApprovalRulesRepository.saveAndFlush(leaveTypeApprovalRules);

        // Get all the leaveTypeApprovalRulesList where createdAt equals to DEFAULT_CREATED_AT
        defaultLeaveTypeApprovalRulesShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the leaveTypeApprovalRulesList where createdAt equals to UPDATED_CREATED_AT
        defaultLeaveTypeApprovalRulesShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveTypeApprovalRulesByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeApprovalRulesRepository.saveAndFlush(leaveTypeApprovalRules);

        // Get all the leaveTypeApprovalRulesList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultLeaveTypeApprovalRulesShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the leaveTypeApprovalRulesList where createdAt equals to UPDATED_CREATED_AT
        defaultLeaveTypeApprovalRulesShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveTypeApprovalRulesByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeApprovalRulesRepository.saveAndFlush(leaveTypeApprovalRules);

        // Get all the leaveTypeApprovalRulesList where createdAt is not null
        defaultLeaveTypeApprovalRulesShouldBeFound("createdAt.specified=true");

        // Get all the leaveTypeApprovalRulesList where createdAt is null
        defaultLeaveTypeApprovalRulesShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypeApprovalRulesByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeApprovalRulesRepository.saveAndFlush(leaveTypeApprovalRules);

        // Get all the leaveTypeApprovalRulesList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultLeaveTypeApprovalRulesShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the leaveTypeApprovalRulesList where updatedAt equals to UPDATED_UPDATED_AT
        defaultLeaveTypeApprovalRulesShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveTypeApprovalRulesByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeApprovalRulesRepository.saveAndFlush(leaveTypeApprovalRules);

        // Get all the leaveTypeApprovalRulesList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultLeaveTypeApprovalRulesShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the leaveTypeApprovalRulesList where updatedAt equals to UPDATED_UPDATED_AT
        defaultLeaveTypeApprovalRulesShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveTypeApprovalRulesByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeApprovalRulesRepository.saveAndFlush(leaveTypeApprovalRules);

        // Get all the leaveTypeApprovalRulesList where updatedAt is not null
        defaultLeaveTypeApprovalRulesShouldBeFound("updatedAt.specified=true");

        // Get all the leaveTypeApprovalRulesList where updatedAt is null
        defaultLeaveTypeApprovalRulesShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypeApprovalRulesByDeletedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeApprovalRulesRepository.saveAndFlush(leaveTypeApprovalRules);

        // Get all the leaveTypeApprovalRulesList where deletedAt equals to DEFAULT_DELETED_AT
        defaultLeaveTypeApprovalRulesShouldBeFound("deletedAt.equals=" + DEFAULT_DELETED_AT);

        // Get all the leaveTypeApprovalRulesList where deletedAt equals to UPDATED_DELETED_AT
        defaultLeaveTypeApprovalRulesShouldNotBeFound("deletedAt.equals=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveTypeApprovalRulesByDeletedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeApprovalRulesRepository.saveAndFlush(leaveTypeApprovalRules);

        // Get all the leaveTypeApprovalRulesList where deletedAt in DEFAULT_DELETED_AT or UPDATED_DELETED_AT
        defaultLeaveTypeApprovalRulesShouldBeFound("deletedAt.in=" + DEFAULT_DELETED_AT + "," + UPDATED_DELETED_AT);

        // Get all the leaveTypeApprovalRulesList where deletedAt equals to UPDATED_DELETED_AT
        defaultLeaveTypeApprovalRulesShouldNotBeFound("deletedAt.in=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveTypeApprovalRulesByDeletedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeApprovalRulesRepository.saveAndFlush(leaveTypeApprovalRules);

        // Get all the leaveTypeApprovalRulesList where deletedAt is not null
        defaultLeaveTypeApprovalRulesShouldBeFound("deletedAt.specified=true");

        // Get all the leaveTypeApprovalRulesList where deletedAt is null
        defaultLeaveTypeApprovalRulesShouldNotBeFound("deletedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypeApprovalRulesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeApprovalRulesRepository.saveAndFlush(leaveTypeApprovalRules);

        // Get all the leaveTypeApprovalRulesList where version equals to DEFAULT_VERSION
        defaultLeaveTypeApprovalRulesShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the leaveTypeApprovalRulesList where version equals to UPDATED_VERSION
        defaultLeaveTypeApprovalRulesShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveTypeApprovalRulesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeApprovalRulesRepository.saveAndFlush(leaveTypeApprovalRules);

        // Get all the leaveTypeApprovalRulesList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultLeaveTypeApprovalRulesShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the leaveTypeApprovalRulesList where version equals to UPDATED_VERSION
        defaultLeaveTypeApprovalRulesShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveTypeApprovalRulesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeApprovalRulesRepository.saveAndFlush(leaveTypeApprovalRules);

        // Get all the leaveTypeApprovalRulesList where version is not null
        defaultLeaveTypeApprovalRulesShouldBeFound("version.specified=true");

        // Get all the leaveTypeApprovalRulesList where version is null
        defaultLeaveTypeApprovalRulesShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypeApprovalRulesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeApprovalRulesRepository.saveAndFlush(leaveTypeApprovalRules);

        // Get all the leaveTypeApprovalRulesList where version is greater than or equal to DEFAULT_VERSION
        defaultLeaveTypeApprovalRulesShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the leaveTypeApprovalRulesList where version is greater than or equal to UPDATED_VERSION
        defaultLeaveTypeApprovalRulesShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveTypeApprovalRulesByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeApprovalRulesRepository.saveAndFlush(leaveTypeApprovalRules);

        // Get all the leaveTypeApprovalRulesList where version is less than or equal to DEFAULT_VERSION
        defaultLeaveTypeApprovalRulesShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the leaveTypeApprovalRulesList where version is less than or equal to SMALLER_VERSION
        defaultLeaveTypeApprovalRulesShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveTypeApprovalRulesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveTypeApprovalRulesRepository.saveAndFlush(leaveTypeApprovalRules);

        // Get all the leaveTypeApprovalRulesList where version is less than DEFAULT_VERSION
        defaultLeaveTypeApprovalRulesShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the leaveTypeApprovalRulesList where version is less than UPDATED_VERSION
        defaultLeaveTypeApprovalRulesShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveTypeApprovalRulesByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveTypeApprovalRulesRepository.saveAndFlush(leaveTypeApprovalRules);

        // Get all the leaveTypeApprovalRulesList where version is greater than DEFAULT_VERSION
        defaultLeaveTypeApprovalRulesShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the leaveTypeApprovalRulesList where version is greater than SMALLER_VERSION
        defaultLeaveTypeApprovalRulesShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveTypeApprovalRulesByLeaveApprovalCriteriaIsEqualToSomething() throws Exception {
        LeaveApprovalCriterias leaveApprovalCriteria;
        if (TestUtil.findAll(em, LeaveApprovalCriterias.class).isEmpty()) {
            leaveTypeApprovalRulesRepository.saveAndFlush(leaveTypeApprovalRules);
            leaveApprovalCriteria = LeaveApprovalCriteriasResourceIT.createEntity(em);
        } else {
            leaveApprovalCriteria = TestUtil.findAll(em, LeaveApprovalCriterias.class).get(0);
        }
        em.persist(leaveApprovalCriteria);
        em.flush();
        leaveTypeApprovalRules.setLeaveApprovalCriteria(leaveApprovalCriteria);
        leaveTypeApprovalRulesRepository.saveAndFlush(leaveTypeApprovalRules);
        Long leaveApprovalCriteriaId = leaveApprovalCriteria.getId();

        // Get all the leaveTypeApprovalRulesList where leaveApprovalCriteria equals to leaveApprovalCriteriaId
        defaultLeaveTypeApprovalRulesShouldBeFound("leaveApprovalCriteriaId.equals=" + leaveApprovalCriteriaId);

        // Get all the leaveTypeApprovalRulesList where leaveApprovalCriteria equals to (leaveApprovalCriteriaId + 1)
        defaultLeaveTypeApprovalRulesShouldNotBeFound("leaveApprovalCriteriaId.equals=" + (leaveApprovalCriteriaId + 1));
    }

    @Test
    @Transactional
    void getAllLeaveTypeApprovalRulesByLeaveTypeIsEqualToSomething() throws Exception {
        LeaveTypes leaveType;
        if (TestUtil.findAll(em, LeaveTypes.class).isEmpty()) {
            leaveTypeApprovalRulesRepository.saveAndFlush(leaveTypeApprovalRules);
            leaveType = LeaveTypesResourceIT.createEntity(em);
        } else {
            leaveType = TestUtil.findAll(em, LeaveTypes.class).get(0);
        }
        em.persist(leaveType);
        em.flush();
        leaveTypeApprovalRules.setLeaveType(leaveType);
        leaveTypeApprovalRulesRepository.saveAndFlush(leaveTypeApprovalRules);
        Long leaveTypeId = leaveType.getId();

        // Get all the leaveTypeApprovalRulesList where leaveType equals to leaveTypeId
        defaultLeaveTypeApprovalRulesShouldBeFound("leaveTypeId.equals=" + leaveTypeId);

        // Get all the leaveTypeApprovalRulesList where leaveType equals to (leaveTypeId + 1)
        defaultLeaveTypeApprovalRulesShouldNotBeFound("leaveTypeId.equals=" + (leaveTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLeaveTypeApprovalRulesShouldBeFound(String filter) throws Exception {
        restLeaveTypeApprovalRulesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveTypeApprovalRules.getId().intValue())))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restLeaveTypeApprovalRulesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLeaveTypeApprovalRulesShouldNotBeFound(String filter) throws Exception {
        restLeaveTypeApprovalRulesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLeaveTypeApprovalRulesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLeaveTypeApprovalRules() throws Exception {
        // Get the leaveTypeApprovalRules
        restLeaveTypeApprovalRulesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLeaveTypeApprovalRules() throws Exception {
        // Initialize the database
        leaveTypeApprovalRulesRepository.saveAndFlush(leaveTypeApprovalRules);

        int databaseSizeBeforeUpdate = leaveTypeApprovalRulesRepository.findAll().size();

        // Update the leaveTypeApprovalRules
        LeaveTypeApprovalRules updatedLeaveTypeApprovalRules = leaveTypeApprovalRulesRepository
            .findById(leaveTypeApprovalRules.getId())
            .get();
        // Disconnect from session so that the updates on updatedLeaveTypeApprovalRules are not directly saved in db
        em.detach(updatedLeaveTypeApprovalRules);
        updatedLeaveTypeApprovalRules
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restLeaveTypeApprovalRulesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLeaveTypeApprovalRules.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLeaveTypeApprovalRules))
            )
            .andExpect(status().isOk());

        // Validate the LeaveTypeApprovalRules in the database
        List<LeaveTypeApprovalRules> leaveTypeApprovalRulesList = leaveTypeApprovalRulesRepository.findAll();
        assertThat(leaveTypeApprovalRulesList).hasSize(databaseSizeBeforeUpdate);
        LeaveTypeApprovalRules testLeaveTypeApprovalRules = leaveTypeApprovalRulesList.get(leaveTypeApprovalRulesList.size() - 1);
        assertThat(testLeaveTypeApprovalRules.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testLeaveTypeApprovalRules.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveTypeApprovalRules.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaveTypeApprovalRules.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testLeaveTypeApprovalRules.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingLeaveTypeApprovalRules() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypeApprovalRulesRepository.findAll().size();
        leaveTypeApprovalRules.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveTypeApprovalRulesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leaveTypeApprovalRules.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeApprovalRules))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveTypeApprovalRules in the database
        List<LeaveTypeApprovalRules> leaveTypeApprovalRulesList = leaveTypeApprovalRulesRepository.findAll();
        assertThat(leaveTypeApprovalRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeaveTypeApprovalRules() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypeApprovalRulesRepository.findAll().size();
        leaveTypeApprovalRules.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveTypeApprovalRulesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeApprovalRules))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveTypeApprovalRules in the database
        List<LeaveTypeApprovalRules> leaveTypeApprovalRulesList = leaveTypeApprovalRulesRepository.findAll();
        assertThat(leaveTypeApprovalRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeaveTypeApprovalRules() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypeApprovalRulesRepository.findAll().size();
        leaveTypeApprovalRules.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveTypeApprovalRulesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeApprovalRules))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveTypeApprovalRules in the database
        List<LeaveTypeApprovalRules> leaveTypeApprovalRulesList = leaveTypeApprovalRulesRepository.findAll();
        assertThat(leaveTypeApprovalRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeaveTypeApprovalRulesWithPatch() throws Exception {
        // Initialize the database
        leaveTypeApprovalRulesRepository.saveAndFlush(leaveTypeApprovalRules);

        int databaseSizeBeforeUpdate = leaveTypeApprovalRulesRepository.findAll().size();

        // Update the leaveTypeApprovalRules using partial update
        LeaveTypeApprovalRules partialUpdatedLeaveTypeApprovalRules = new LeaveTypeApprovalRules();
        partialUpdatedLeaveTypeApprovalRules.setId(leaveTypeApprovalRules.getId());

        partialUpdatedLeaveTypeApprovalRules.updatedAt(UPDATED_UPDATED_AT).deletedAt(UPDATED_DELETED_AT);

        restLeaveTypeApprovalRulesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveTypeApprovalRules.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveTypeApprovalRules))
            )
            .andExpect(status().isOk());

        // Validate the LeaveTypeApprovalRules in the database
        List<LeaveTypeApprovalRules> leaveTypeApprovalRulesList = leaveTypeApprovalRulesRepository.findAll();
        assertThat(leaveTypeApprovalRulesList).hasSize(databaseSizeBeforeUpdate);
        LeaveTypeApprovalRules testLeaveTypeApprovalRules = leaveTypeApprovalRulesList.get(leaveTypeApprovalRulesList.size() - 1);
        assertThat(testLeaveTypeApprovalRules.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testLeaveTypeApprovalRules.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testLeaveTypeApprovalRules.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaveTypeApprovalRules.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testLeaveTypeApprovalRules.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateLeaveTypeApprovalRulesWithPatch() throws Exception {
        // Initialize the database
        leaveTypeApprovalRulesRepository.saveAndFlush(leaveTypeApprovalRules);

        int databaseSizeBeforeUpdate = leaveTypeApprovalRulesRepository.findAll().size();

        // Update the leaveTypeApprovalRules using partial update
        LeaveTypeApprovalRules partialUpdatedLeaveTypeApprovalRules = new LeaveTypeApprovalRules();
        partialUpdatedLeaveTypeApprovalRules.setId(leaveTypeApprovalRules.getId());

        partialUpdatedLeaveTypeApprovalRules
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restLeaveTypeApprovalRulesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveTypeApprovalRules.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveTypeApprovalRules))
            )
            .andExpect(status().isOk());

        // Validate the LeaveTypeApprovalRules in the database
        List<LeaveTypeApprovalRules> leaveTypeApprovalRulesList = leaveTypeApprovalRulesRepository.findAll();
        assertThat(leaveTypeApprovalRulesList).hasSize(databaseSizeBeforeUpdate);
        LeaveTypeApprovalRules testLeaveTypeApprovalRules = leaveTypeApprovalRulesList.get(leaveTypeApprovalRulesList.size() - 1);
        assertThat(testLeaveTypeApprovalRules.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testLeaveTypeApprovalRules.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveTypeApprovalRules.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaveTypeApprovalRules.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testLeaveTypeApprovalRules.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingLeaveTypeApprovalRules() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypeApprovalRulesRepository.findAll().size();
        leaveTypeApprovalRules.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveTypeApprovalRulesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leaveTypeApprovalRules.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeApprovalRules))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveTypeApprovalRules in the database
        List<LeaveTypeApprovalRules> leaveTypeApprovalRulesList = leaveTypeApprovalRulesRepository.findAll();
        assertThat(leaveTypeApprovalRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeaveTypeApprovalRules() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypeApprovalRulesRepository.findAll().size();
        leaveTypeApprovalRules.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveTypeApprovalRulesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeApprovalRules))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveTypeApprovalRules in the database
        List<LeaveTypeApprovalRules> leaveTypeApprovalRulesList = leaveTypeApprovalRulesRepository.findAll();
        assertThat(leaveTypeApprovalRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeaveTypeApprovalRules() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypeApprovalRulesRepository.findAll().size();
        leaveTypeApprovalRules.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveTypeApprovalRulesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeApprovalRules))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveTypeApprovalRules in the database
        List<LeaveTypeApprovalRules> leaveTypeApprovalRulesList = leaveTypeApprovalRulesRepository.findAll();
        assertThat(leaveTypeApprovalRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLeaveTypeApprovalRules() throws Exception {
        // Initialize the database
        leaveTypeApprovalRulesRepository.saveAndFlush(leaveTypeApprovalRules);

        int databaseSizeBeforeDelete = leaveTypeApprovalRulesRepository.findAll().size();

        // Delete the leaveTypeApprovalRules
        restLeaveTypeApprovalRulesMockMvc
            .perform(delete(ENTITY_API_URL_ID, leaveTypeApprovalRules.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeaveTypeApprovalRules> leaveTypeApprovalRulesList = leaveTypeApprovalRulesRepository.findAll();
        assertThat(leaveTypeApprovalRulesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
