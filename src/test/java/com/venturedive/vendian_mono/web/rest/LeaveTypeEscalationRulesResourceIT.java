package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.LeaveEscalationCriterias;
import com.venturedive.vendian_mono.domain.LeaveStatuses;
import com.venturedive.vendian_mono.domain.LeaveTypeEscalationRules;
import com.venturedive.vendian_mono.domain.LeaveTypes;
import com.venturedive.vendian_mono.repository.LeaveTypeEscalationRulesRepository;
import com.venturedive.vendian_mono.service.criteria.LeaveTypeEscalationRulesCriteria;
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
 * Integration tests for the {@link LeaveTypeEscalationRulesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeaveTypeEscalationRulesResourceIT {

    private static final Integer DEFAULT_NO_OF_DAYS = 1;
    private static final Integer UPDATED_NO_OF_DAYS = 2;
    private static final Integer SMALLER_NO_OF_DAYS = 1 - 1;

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

    private static final String ENTITY_API_URL = "/api/leave-type-escalation-rules";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LeaveTypeEscalationRulesRepository leaveTypeEscalationRulesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeaveTypeEscalationRulesMockMvc;

    private LeaveTypeEscalationRules leaveTypeEscalationRules;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveTypeEscalationRules createEntity(EntityManager em) {
        LeaveTypeEscalationRules leaveTypeEscalationRules = new LeaveTypeEscalationRules()
            .noOfDays(DEFAULT_NO_OF_DAYS)
            .effDate(DEFAULT_EFF_DATE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .endDate(DEFAULT_END_DATE)
            .version(DEFAULT_VERSION);
        // Add required entity
        LeaveEscalationCriterias leaveEscalationCriterias;
        if (TestUtil.findAll(em, LeaveEscalationCriterias.class).isEmpty()) {
            leaveEscalationCriterias = LeaveEscalationCriteriasResourceIT.createEntity(em);
            em.persist(leaveEscalationCriterias);
            em.flush();
        } else {
            leaveEscalationCriterias = TestUtil.findAll(em, LeaveEscalationCriterias.class).get(0);
        }
        leaveTypeEscalationRules.setLeaveEscalationCriteria(leaveEscalationCriterias);
        // Add required entity
        LeaveStatuses leaveStatuses;
        if (TestUtil.findAll(em, LeaveStatuses.class).isEmpty()) {
            leaveStatuses = LeaveStatusesResourceIT.createEntity(em);
            em.persist(leaveStatuses);
            em.flush();
        } else {
            leaveStatuses = TestUtil.findAll(em, LeaveStatuses.class).get(0);
        }
        leaveTypeEscalationRules.setLeaveRequestStatus(leaveStatuses);
        // Add required entity
        LeaveTypes leaveTypes;
        if (TestUtil.findAll(em, LeaveTypes.class).isEmpty()) {
            leaveTypes = LeaveTypesResourceIT.createEntity(em);
            em.persist(leaveTypes);
            em.flush();
        } else {
            leaveTypes = TestUtil.findAll(em, LeaveTypes.class).get(0);
        }
        leaveTypeEscalationRules.setLeaveType(leaveTypes);
        return leaveTypeEscalationRules;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveTypeEscalationRules createUpdatedEntity(EntityManager em) {
        LeaveTypeEscalationRules leaveTypeEscalationRules = new LeaveTypeEscalationRules()
            .noOfDays(UPDATED_NO_OF_DAYS)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);
        // Add required entity
        LeaveEscalationCriterias leaveEscalationCriterias;
        if (TestUtil.findAll(em, LeaveEscalationCriterias.class).isEmpty()) {
            leaveEscalationCriterias = LeaveEscalationCriteriasResourceIT.createUpdatedEntity(em);
            em.persist(leaveEscalationCriterias);
            em.flush();
        } else {
            leaveEscalationCriterias = TestUtil.findAll(em, LeaveEscalationCriterias.class).get(0);
        }
        leaveTypeEscalationRules.setLeaveEscalationCriteria(leaveEscalationCriterias);
        // Add required entity
        LeaveStatuses leaveStatuses;
        if (TestUtil.findAll(em, LeaveStatuses.class).isEmpty()) {
            leaveStatuses = LeaveStatusesResourceIT.createUpdatedEntity(em);
            em.persist(leaveStatuses);
            em.flush();
        } else {
            leaveStatuses = TestUtil.findAll(em, LeaveStatuses.class).get(0);
        }
        leaveTypeEscalationRules.setLeaveRequestStatus(leaveStatuses);
        // Add required entity
        LeaveTypes leaveTypes;
        if (TestUtil.findAll(em, LeaveTypes.class).isEmpty()) {
            leaveTypes = LeaveTypesResourceIT.createUpdatedEntity(em);
            em.persist(leaveTypes);
            em.flush();
        } else {
            leaveTypes = TestUtil.findAll(em, LeaveTypes.class).get(0);
        }
        leaveTypeEscalationRules.setLeaveType(leaveTypes);
        return leaveTypeEscalationRules;
    }

    @BeforeEach
    public void initTest() {
        leaveTypeEscalationRules = createEntity(em);
    }

    @Test
    @Transactional
    void createLeaveTypeEscalationRules() throws Exception {
        int databaseSizeBeforeCreate = leaveTypeEscalationRulesRepository.findAll().size();
        // Create the LeaveTypeEscalationRules
        restLeaveTypeEscalationRulesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeEscalationRules))
            )
            .andExpect(status().isCreated());

        // Validate the LeaveTypeEscalationRules in the database
        List<LeaveTypeEscalationRules> leaveTypeEscalationRulesList = leaveTypeEscalationRulesRepository.findAll();
        assertThat(leaveTypeEscalationRulesList).hasSize(databaseSizeBeforeCreate + 1);
        LeaveTypeEscalationRules testLeaveTypeEscalationRules = leaveTypeEscalationRulesList.get(leaveTypeEscalationRulesList.size() - 1);
        assertThat(testLeaveTypeEscalationRules.getNoOfDays()).isEqualTo(DEFAULT_NO_OF_DAYS);
        assertThat(testLeaveTypeEscalationRules.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testLeaveTypeEscalationRules.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testLeaveTypeEscalationRules.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testLeaveTypeEscalationRules.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testLeaveTypeEscalationRules.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createLeaveTypeEscalationRulesWithExistingId() throws Exception {
        // Create the LeaveTypeEscalationRules with an existing ID
        leaveTypeEscalationRules.setId(1L);

        int databaseSizeBeforeCreate = leaveTypeEscalationRulesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeaveTypeEscalationRulesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeEscalationRules))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveTypeEscalationRules in the database
        List<LeaveTypeEscalationRules> leaveTypeEscalationRulesList = leaveTypeEscalationRulesRepository.findAll();
        assertThat(leaveTypeEscalationRulesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEffDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveTypeEscalationRulesRepository.findAll().size();
        // set the field null
        leaveTypeEscalationRules.setEffDate(null);

        // Create the LeaveTypeEscalationRules, which fails.

        restLeaveTypeEscalationRulesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeEscalationRules))
            )
            .andExpect(status().isBadRequest());

        List<LeaveTypeEscalationRules> leaveTypeEscalationRulesList = leaveTypeEscalationRulesRepository.findAll();
        assertThat(leaveTypeEscalationRulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveTypeEscalationRulesRepository.findAll().size();
        // set the field null
        leaveTypeEscalationRules.setCreatedAt(null);

        // Create the LeaveTypeEscalationRules, which fails.

        restLeaveTypeEscalationRulesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeEscalationRules))
            )
            .andExpect(status().isBadRequest());

        List<LeaveTypeEscalationRules> leaveTypeEscalationRulesList = leaveTypeEscalationRulesRepository.findAll();
        assertThat(leaveTypeEscalationRulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveTypeEscalationRulesRepository.findAll().size();
        // set the field null
        leaveTypeEscalationRules.setUpdatedAt(null);

        // Create the LeaveTypeEscalationRules, which fails.

        restLeaveTypeEscalationRulesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeEscalationRules))
            )
            .andExpect(status().isBadRequest());

        List<LeaveTypeEscalationRules> leaveTypeEscalationRulesList = leaveTypeEscalationRulesRepository.findAll();
        assertThat(leaveTypeEscalationRulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveTypeEscalationRulesRepository.findAll().size();
        // set the field null
        leaveTypeEscalationRules.setVersion(null);

        // Create the LeaveTypeEscalationRules, which fails.

        restLeaveTypeEscalationRulesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeEscalationRules))
            )
            .andExpect(status().isBadRequest());

        List<LeaveTypeEscalationRules> leaveTypeEscalationRulesList = leaveTypeEscalationRulesRepository.findAll();
        assertThat(leaveTypeEscalationRulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLeaveTypeEscalationRules() throws Exception {
        // Initialize the database
        leaveTypeEscalationRulesRepository.saveAndFlush(leaveTypeEscalationRules);

        // Get all the leaveTypeEscalationRulesList
        restLeaveTypeEscalationRulesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveTypeEscalationRules.getId().intValue())))
            .andExpect(jsonPath("$.[*].noOfDays").value(hasItem(DEFAULT_NO_OF_DAYS)))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getLeaveTypeEscalationRules() throws Exception {
        // Initialize the database
        leaveTypeEscalationRulesRepository.saveAndFlush(leaveTypeEscalationRules);

        // Get the leaveTypeEscalationRules
        restLeaveTypeEscalationRulesMockMvc
            .perform(get(ENTITY_API_URL_ID, leaveTypeEscalationRules.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leaveTypeEscalationRules.getId().intValue()))
            .andExpect(jsonPath("$.noOfDays").value(DEFAULT_NO_OF_DAYS))
            .andExpect(jsonPath("$.effDate").value(DEFAULT_EFF_DATE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getLeaveTypeEscalationRulesByIdFiltering() throws Exception {
        // Initialize the database
        leaveTypeEscalationRulesRepository.saveAndFlush(leaveTypeEscalationRules);

        Long id = leaveTypeEscalationRules.getId();

        defaultLeaveTypeEscalationRulesShouldBeFound("id.equals=" + id);
        defaultLeaveTypeEscalationRulesShouldNotBeFound("id.notEquals=" + id);

        defaultLeaveTypeEscalationRulesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLeaveTypeEscalationRulesShouldNotBeFound("id.greaterThan=" + id);

        defaultLeaveTypeEscalationRulesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLeaveTypeEscalationRulesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLeaveTypeEscalationRulesByNoOfDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeEscalationRulesRepository.saveAndFlush(leaveTypeEscalationRules);

        // Get all the leaveTypeEscalationRulesList where noOfDays equals to DEFAULT_NO_OF_DAYS
        defaultLeaveTypeEscalationRulesShouldBeFound("noOfDays.equals=" + DEFAULT_NO_OF_DAYS);

        // Get all the leaveTypeEscalationRulesList where noOfDays equals to UPDATED_NO_OF_DAYS
        defaultLeaveTypeEscalationRulesShouldNotBeFound("noOfDays.equals=" + UPDATED_NO_OF_DAYS);
    }

    @Test
    @Transactional
    void getAllLeaveTypeEscalationRulesByNoOfDaysIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeEscalationRulesRepository.saveAndFlush(leaveTypeEscalationRules);

        // Get all the leaveTypeEscalationRulesList where noOfDays in DEFAULT_NO_OF_DAYS or UPDATED_NO_OF_DAYS
        defaultLeaveTypeEscalationRulesShouldBeFound("noOfDays.in=" + DEFAULT_NO_OF_DAYS + "," + UPDATED_NO_OF_DAYS);

        // Get all the leaveTypeEscalationRulesList where noOfDays equals to UPDATED_NO_OF_DAYS
        defaultLeaveTypeEscalationRulesShouldNotBeFound("noOfDays.in=" + UPDATED_NO_OF_DAYS);
    }

    @Test
    @Transactional
    void getAllLeaveTypeEscalationRulesByNoOfDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeEscalationRulesRepository.saveAndFlush(leaveTypeEscalationRules);

        // Get all the leaveTypeEscalationRulesList where noOfDays is not null
        defaultLeaveTypeEscalationRulesShouldBeFound("noOfDays.specified=true");

        // Get all the leaveTypeEscalationRulesList where noOfDays is null
        defaultLeaveTypeEscalationRulesShouldNotBeFound("noOfDays.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypeEscalationRulesByNoOfDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeEscalationRulesRepository.saveAndFlush(leaveTypeEscalationRules);

        // Get all the leaveTypeEscalationRulesList where noOfDays is greater than or equal to DEFAULT_NO_OF_DAYS
        defaultLeaveTypeEscalationRulesShouldBeFound("noOfDays.greaterThanOrEqual=" + DEFAULT_NO_OF_DAYS);

        // Get all the leaveTypeEscalationRulesList where noOfDays is greater than or equal to UPDATED_NO_OF_DAYS
        defaultLeaveTypeEscalationRulesShouldNotBeFound("noOfDays.greaterThanOrEqual=" + UPDATED_NO_OF_DAYS);
    }

    @Test
    @Transactional
    void getAllLeaveTypeEscalationRulesByNoOfDaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeEscalationRulesRepository.saveAndFlush(leaveTypeEscalationRules);

        // Get all the leaveTypeEscalationRulesList where noOfDays is less than or equal to DEFAULT_NO_OF_DAYS
        defaultLeaveTypeEscalationRulesShouldBeFound("noOfDays.lessThanOrEqual=" + DEFAULT_NO_OF_DAYS);

        // Get all the leaveTypeEscalationRulesList where noOfDays is less than or equal to SMALLER_NO_OF_DAYS
        defaultLeaveTypeEscalationRulesShouldNotBeFound("noOfDays.lessThanOrEqual=" + SMALLER_NO_OF_DAYS);
    }

    @Test
    @Transactional
    void getAllLeaveTypeEscalationRulesByNoOfDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveTypeEscalationRulesRepository.saveAndFlush(leaveTypeEscalationRules);

        // Get all the leaveTypeEscalationRulesList where noOfDays is less than DEFAULT_NO_OF_DAYS
        defaultLeaveTypeEscalationRulesShouldNotBeFound("noOfDays.lessThan=" + DEFAULT_NO_OF_DAYS);

        // Get all the leaveTypeEscalationRulesList where noOfDays is less than UPDATED_NO_OF_DAYS
        defaultLeaveTypeEscalationRulesShouldBeFound("noOfDays.lessThan=" + UPDATED_NO_OF_DAYS);
    }

    @Test
    @Transactional
    void getAllLeaveTypeEscalationRulesByNoOfDaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveTypeEscalationRulesRepository.saveAndFlush(leaveTypeEscalationRules);

        // Get all the leaveTypeEscalationRulesList where noOfDays is greater than DEFAULT_NO_OF_DAYS
        defaultLeaveTypeEscalationRulesShouldNotBeFound("noOfDays.greaterThan=" + DEFAULT_NO_OF_DAYS);

        // Get all the leaveTypeEscalationRulesList where noOfDays is greater than SMALLER_NO_OF_DAYS
        defaultLeaveTypeEscalationRulesShouldBeFound("noOfDays.greaterThan=" + SMALLER_NO_OF_DAYS);
    }

    @Test
    @Transactional
    void getAllLeaveTypeEscalationRulesByEffDateIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeEscalationRulesRepository.saveAndFlush(leaveTypeEscalationRules);

        // Get all the leaveTypeEscalationRulesList where effDate equals to DEFAULT_EFF_DATE
        defaultLeaveTypeEscalationRulesShouldBeFound("effDate.equals=" + DEFAULT_EFF_DATE);

        // Get all the leaveTypeEscalationRulesList where effDate equals to UPDATED_EFF_DATE
        defaultLeaveTypeEscalationRulesShouldNotBeFound("effDate.equals=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveTypeEscalationRulesByEffDateIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeEscalationRulesRepository.saveAndFlush(leaveTypeEscalationRules);

        // Get all the leaveTypeEscalationRulesList where effDate in DEFAULT_EFF_DATE or UPDATED_EFF_DATE
        defaultLeaveTypeEscalationRulesShouldBeFound("effDate.in=" + DEFAULT_EFF_DATE + "," + UPDATED_EFF_DATE);

        // Get all the leaveTypeEscalationRulesList where effDate equals to UPDATED_EFF_DATE
        defaultLeaveTypeEscalationRulesShouldNotBeFound("effDate.in=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveTypeEscalationRulesByEffDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeEscalationRulesRepository.saveAndFlush(leaveTypeEscalationRules);

        // Get all the leaveTypeEscalationRulesList where effDate is not null
        defaultLeaveTypeEscalationRulesShouldBeFound("effDate.specified=true");

        // Get all the leaveTypeEscalationRulesList where effDate is null
        defaultLeaveTypeEscalationRulesShouldNotBeFound("effDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypeEscalationRulesByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeEscalationRulesRepository.saveAndFlush(leaveTypeEscalationRules);

        // Get all the leaveTypeEscalationRulesList where createdAt equals to DEFAULT_CREATED_AT
        defaultLeaveTypeEscalationRulesShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the leaveTypeEscalationRulesList where createdAt equals to UPDATED_CREATED_AT
        defaultLeaveTypeEscalationRulesShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveTypeEscalationRulesByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeEscalationRulesRepository.saveAndFlush(leaveTypeEscalationRules);

        // Get all the leaveTypeEscalationRulesList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultLeaveTypeEscalationRulesShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the leaveTypeEscalationRulesList where createdAt equals to UPDATED_CREATED_AT
        defaultLeaveTypeEscalationRulesShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveTypeEscalationRulesByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeEscalationRulesRepository.saveAndFlush(leaveTypeEscalationRules);

        // Get all the leaveTypeEscalationRulesList where createdAt is not null
        defaultLeaveTypeEscalationRulesShouldBeFound("createdAt.specified=true");

        // Get all the leaveTypeEscalationRulesList where createdAt is null
        defaultLeaveTypeEscalationRulesShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypeEscalationRulesByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeEscalationRulesRepository.saveAndFlush(leaveTypeEscalationRules);

        // Get all the leaveTypeEscalationRulesList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultLeaveTypeEscalationRulesShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the leaveTypeEscalationRulesList where updatedAt equals to UPDATED_UPDATED_AT
        defaultLeaveTypeEscalationRulesShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveTypeEscalationRulesByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeEscalationRulesRepository.saveAndFlush(leaveTypeEscalationRules);

        // Get all the leaveTypeEscalationRulesList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultLeaveTypeEscalationRulesShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the leaveTypeEscalationRulesList where updatedAt equals to UPDATED_UPDATED_AT
        defaultLeaveTypeEscalationRulesShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveTypeEscalationRulesByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeEscalationRulesRepository.saveAndFlush(leaveTypeEscalationRules);

        // Get all the leaveTypeEscalationRulesList where updatedAt is not null
        defaultLeaveTypeEscalationRulesShouldBeFound("updatedAt.specified=true");

        // Get all the leaveTypeEscalationRulesList where updatedAt is null
        defaultLeaveTypeEscalationRulesShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypeEscalationRulesByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeEscalationRulesRepository.saveAndFlush(leaveTypeEscalationRules);

        // Get all the leaveTypeEscalationRulesList where endDate equals to DEFAULT_END_DATE
        defaultLeaveTypeEscalationRulesShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the leaveTypeEscalationRulesList where endDate equals to UPDATED_END_DATE
        defaultLeaveTypeEscalationRulesShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveTypeEscalationRulesByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeEscalationRulesRepository.saveAndFlush(leaveTypeEscalationRules);

        // Get all the leaveTypeEscalationRulesList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultLeaveTypeEscalationRulesShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the leaveTypeEscalationRulesList where endDate equals to UPDATED_END_DATE
        defaultLeaveTypeEscalationRulesShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveTypeEscalationRulesByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeEscalationRulesRepository.saveAndFlush(leaveTypeEscalationRules);

        // Get all the leaveTypeEscalationRulesList where endDate is not null
        defaultLeaveTypeEscalationRulesShouldBeFound("endDate.specified=true");

        // Get all the leaveTypeEscalationRulesList where endDate is null
        defaultLeaveTypeEscalationRulesShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypeEscalationRulesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeEscalationRulesRepository.saveAndFlush(leaveTypeEscalationRules);

        // Get all the leaveTypeEscalationRulesList where version equals to DEFAULT_VERSION
        defaultLeaveTypeEscalationRulesShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the leaveTypeEscalationRulesList where version equals to UPDATED_VERSION
        defaultLeaveTypeEscalationRulesShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveTypeEscalationRulesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeEscalationRulesRepository.saveAndFlush(leaveTypeEscalationRules);

        // Get all the leaveTypeEscalationRulesList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultLeaveTypeEscalationRulesShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the leaveTypeEscalationRulesList where version equals to UPDATED_VERSION
        defaultLeaveTypeEscalationRulesShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveTypeEscalationRulesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeEscalationRulesRepository.saveAndFlush(leaveTypeEscalationRules);

        // Get all the leaveTypeEscalationRulesList where version is not null
        defaultLeaveTypeEscalationRulesShouldBeFound("version.specified=true");

        // Get all the leaveTypeEscalationRulesList where version is null
        defaultLeaveTypeEscalationRulesShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypeEscalationRulesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeEscalationRulesRepository.saveAndFlush(leaveTypeEscalationRules);

        // Get all the leaveTypeEscalationRulesList where version is greater than or equal to DEFAULT_VERSION
        defaultLeaveTypeEscalationRulesShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the leaveTypeEscalationRulesList where version is greater than or equal to UPDATED_VERSION
        defaultLeaveTypeEscalationRulesShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveTypeEscalationRulesByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeEscalationRulesRepository.saveAndFlush(leaveTypeEscalationRules);

        // Get all the leaveTypeEscalationRulesList where version is less than or equal to DEFAULT_VERSION
        defaultLeaveTypeEscalationRulesShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the leaveTypeEscalationRulesList where version is less than or equal to SMALLER_VERSION
        defaultLeaveTypeEscalationRulesShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveTypeEscalationRulesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveTypeEscalationRulesRepository.saveAndFlush(leaveTypeEscalationRules);

        // Get all the leaveTypeEscalationRulesList where version is less than DEFAULT_VERSION
        defaultLeaveTypeEscalationRulesShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the leaveTypeEscalationRulesList where version is less than UPDATED_VERSION
        defaultLeaveTypeEscalationRulesShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveTypeEscalationRulesByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveTypeEscalationRulesRepository.saveAndFlush(leaveTypeEscalationRules);

        // Get all the leaveTypeEscalationRulesList where version is greater than DEFAULT_VERSION
        defaultLeaveTypeEscalationRulesShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the leaveTypeEscalationRulesList where version is greater than SMALLER_VERSION
        defaultLeaveTypeEscalationRulesShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveTypeEscalationRulesByLeaveEscalationCriteriaIsEqualToSomething() throws Exception {
        LeaveEscalationCriterias leaveEscalationCriteria;
        if (TestUtil.findAll(em, LeaveEscalationCriterias.class).isEmpty()) {
            leaveTypeEscalationRulesRepository.saveAndFlush(leaveTypeEscalationRules);
            leaveEscalationCriteria = LeaveEscalationCriteriasResourceIT.createEntity(em);
        } else {
            leaveEscalationCriteria = TestUtil.findAll(em, LeaveEscalationCriterias.class).get(0);
        }
        em.persist(leaveEscalationCriteria);
        em.flush();
        leaveTypeEscalationRules.setLeaveEscalationCriteria(leaveEscalationCriteria);
        leaveTypeEscalationRulesRepository.saveAndFlush(leaveTypeEscalationRules);
        Long leaveEscalationCriteriaId = leaveEscalationCriteria.getId();

        // Get all the leaveTypeEscalationRulesList where leaveEscalationCriteria equals to leaveEscalationCriteriaId
        defaultLeaveTypeEscalationRulesShouldBeFound("leaveEscalationCriteriaId.equals=" + leaveEscalationCriteriaId);

        // Get all the leaveTypeEscalationRulesList where leaveEscalationCriteria equals to (leaveEscalationCriteriaId + 1)
        defaultLeaveTypeEscalationRulesShouldNotBeFound("leaveEscalationCriteriaId.equals=" + (leaveEscalationCriteriaId + 1));
    }

    @Test
    @Transactional
    void getAllLeaveTypeEscalationRulesByLeaveRequestStatusIsEqualToSomething() throws Exception {
        LeaveStatuses leaveRequestStatus;
        if (TestUtil.findAll(em, LeaveStatuses.class).isEmpty()) {
            leaveTypeEscalationRulesRepository.saveAndFlush(leaveTypeEscalationRules);
            leaveRequestStatus = LeaveStatusesResourceIT.createEntity(em);
        } else {
            leaveRequestStatus = TestUtil.findAll(em, LeaveStatuses.class).get(0);
        }
        em.persist(leaveRequestStatus);
        em.flush();
        leaveTypeEscalationRules.setLeaveRequestStatus(leaveRequestStatus);
        leaveTypeEscalationRulesRepository.saveAndFlush(leaveTypeEscalationRules);
        Long leaveRequestStatusId = leaveRequestStatus.getId();

        // Get all the leaveTypeEscalationRulesList where leaveRequestStatus equals to leaveRequestStatusId
        defaultLeaveTypeEscalationRulesShouldBeFound("leaveRequestStatusId.equals=" + leaveRequestStatusId);

        // Get all the leaveTypeEscalationRulesList where leaveRequestStatus equals to (leaveRequestStatusId + 1)
        defaultLeaveTypeEscalationRulesShouldNotBeFound("leaveRequestStatusId.equals=" + (leaveRequestStatusId + 1));
    }

    @Test
    @Transactional
    void getAllLeaveTypeEscalationRulesByLeaveTypeIsEqualToSomething() throws Exception {
        LeaveTypes leaveType;
        if (TestUtil.findAll(em, LeaveTypes.class).isEmpty()) {
            leaveTypeEscalationRulesRepository.saveAndFlush(leaveTypeEscalationRules);
            leaveType = LeaveTypesResourceIT.createEntity(em);
        } else {
            leaveType = TestUtil.findAll(em, LeaveTypes.class).get(0);
        }
        em.persist(leaveType);
        em.flush();
        leaveTypeEscalationRules.setLeaveType(leaveType);
        leaveTypeEscalationRulesRepository.saveAndFlush(leaveTypeEscalationRules);
        Long leaveTypeId = leaveType.getId();

        // Get all the leaveTypeEscalationRulesList where leaveType equals to leaveTypeId
        defaultLeaveTypeEscalationRulesShouldBeFound("leaveTypeId.equals=" + leaveTypeId);

        // Get all the leaveTypeEscalationRulesList where leaveType equals to (leaveTypeId + 1)
        defaultLeaveTypeEscalationRulesShouldNotBeFound("leaveTypeId.equals=" + (leaveTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLeaveTypeEscalationRulesShouldBeFound(String filter) throws Exception {
        restLeaveTypeEscalationRulesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveTypeEscalationRules.getId().intValue())))
            .andExpect(jsonPath("$.[*].noOfDays").value(hasItem(DEFAULT_NO_OF_DAYS)))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restLeaveTypeEscalationRulesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLeaveTypeEscalationRulesShouldNotBeFound(String filter) throws Exception {
        restLeaveTypeEscalationRulesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLeaveTypeEscalationRulesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLeaveTypeEscalationRules() throws Exception {
        // Get the leaveTypeEscalationRules
        restLeaveTypeEscalationRulesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLeaveTypeEscalationRules() throws Exception {
        // Initialize the database
        leaveTypeEscalationRulesRepository.saveAndFlush(leaveTypeEscalationRules);

        int databaseSizeBeforeUpdate = leaveTypeEscalationRulesRepository.findAll().size();

        // Update the leaveTypeEscalationRules
        LeaveTypeEscalationRules updatedLeaveTypeEscalationRules = leaveTypeEscalationRulesRepository
            .findById(leaveTypeEscalationRules.getId())
            .get();
        // Disconnect from session so that the updates on updatedLeaveTypeEscalationRules are not directly saved in db
        em.detach(updatedLeaveTypeEscalationRules);
        updatedLeaveTypeEscalationRules
            .noOfDays(UPDATED_NO_OF_DAYS)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restLeaveTypeEscalationRulesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLeaveTypeEscalationRules.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLeaveTypeEscalationRules))
            )
            .andExpect(status().isOk());

        // Validate the LeaveTypeEscalationRules in the database
        List<LeaveTypeEscalationRules> leaveTypeEscalationRulesList = leaveTypeEscalationRulesRepository.findAll();
        assertThat(leaveTypeEscalationRulesList).hasSize(databaseSizeBeforeUpdate);
        LeaveTypeEscalationRules testLeaveTypeEscalationRules = leaveTypeEscalationRulesList.get(leaveTypeEscalationRulesList.size() - 1);
        assertThat(testLeaveTypeEscalationRules.getNoOfDays()).isEqualTo(UPDATED_NO_OF_DAYS);
        assertThat(testLeaveTypeEscalationRules.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testLeaveTypeEscalationRules.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveTypeEscalationRules.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaveTypeEscalationRules.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testLeaveTypeEscalationRules.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingLeaveTypeEscalationRules() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypeEscalationRulesRepository.findAll().size();
        leaveTypeEscalationRules.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveTypeEscalationRulesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leaveTypeEscalationRules.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeEscalationRules))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveTypeEscalationRules in the database
        List<LeaveTypeEscalationRules> leaveTypeEscalationRulesList = leaveTypeEscalationRulesRepository.findAll();
        assertThat(leaveTypeEscalationRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeaveTypeEscalationRules() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypeEscalationRulesRepository.findAll().size();
        leaveTypeEscalationRules.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveTypeEscalationRulesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeEscalationRules))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveTypeEscalationRules in the database
        List<LeaveTypeEscalationRules> leaveTypeEscalationRulesList = leaveTypeEscalationRulesRepository.findAll();
        assertThat(leaveTypeEscalationRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeaveTypeEscalationRules() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypeEscalationRulesRepository.findAll().size();
        leaveTypeEscalationRules.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveTypeEscalationRulesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeEscalationRules))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveTypeEscalationRules in the database
        List<LeaveTypeEscalationRules> leaveTypeEscalationRulesList = leaveTypeEscalationRulesRepository.findAll();
        assertThat(leaveTypeEscalationRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeaveTypeEscalationRulesWithPatch() throws Exception {
        // Initialize the database
        leaveTypeEscalationRulesRepository.saveAndFlush(leaveTypeEscalationRules);

        int databaseSizeBeforeUpdate = leaveTypeEscalationRulesRepository.findAll().size();

        // Update the leaveTypeEscalationRules using partial update
        LeaveTypeEscalationRules partialUpdatedLeaveTypeEscalationRules = new LeaveTypeEscalationRules();
        partialUpdatedLeaveTypeEscalationRules.setId(leaveTypeEscalationRules.getId());

        partialUpdatedLeaveTypeEscalationRules.createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT);

        restLeaveTypeEscalationRulesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveTypeEscalationRules.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveTypeEscalationRules))
            )
            .andExpect(status().isOk());

        // Validate the LeaveTypeEscalationRules in the database
        List<LeaveTypeEscalationRules> leaveTypeEscalationRulesList = leaveTypeEscalationRulesRepository.findAll();
        assertThat(leaveTypeEscalationRulesList).hasSize(databaseSizeBeforeUpdate);
        LeaveTypeEscalationRules testLeaveTypeEscalationRules = leaveTypeEscalationRulesList.get(leaveTypeEscalationRulesList.size() - 1);
        assertThat(testLeaveTypeEscalationRules.getNoOfDays()).isEqualTo(DEFAULT_NO_OF_DAYS);
        assertThat(testLeaveTypeEscalationRules.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testLeaveTypeEscalationRules.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveTypeEscalationRules.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaveTypeEscalationRules.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testLeaveTypeEscalationRules.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateLeaveTypeEscalationRulesWithPatch() throws Exception {
        // Initialize the database
        leaveTypeEscalationRulesRepository.saveAndFlush(leaveTypeEscalationRules);

        int databaseSizeBeforeUpdate = leaveTypeEscalationRulesRepository.findAll().size();

        // Update the leaveTypeEscalationRules using partial update
        LeaveTypeEscalationRules partialUpdatedLeaveTypeEscalationRules = new LeaveTypeEscalationRules();
        partialUpdatedLeaveTypeEscalationRules.setId(leaveTypeEscalationRules.getId());

        partialUpdatedLeaveTypeEscalationRules
            .noOfDays(UPDATED_NO_OF_DAYS)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restLeaveTypeEscalationRulesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveTypeEscalationRules.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveTypeEscalationRules))
            )
            .andExpect(status().isOk());

        // Validate the LeaveTypeEscalationRules in the database
        List<LeaveTypeEscalationRules> leaveTypeEscalationRulesList = leaveTypeEscalationRulesRepository.findAll();
        assertThat(leaveTypeEscalationRulesList).hasSize(databaseSizeBeforeUpdate);
        LeaveTypeEscalationRules testLeaveTypeEscalationRules = leaveTypeEscalationRulesList.get(leaveTypeEscalationRulesList.size() - 1);
        assertThat(testLeaveTypeEscalationRules.getNoOfDays()).isEqualTo(UPDATED_NO_OF_DAYS);
        assertThat(testLeaveTypeEscalationRules.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testLeaveTypeEscalationRules.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveTypeEscalationRules.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaveTypeEscalationRules.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testLeaveTypeEscalationRules.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingLeaveTypeEscalationRules() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypeEscalationRulesRepository.findAll().size();
        leaveTypeEscalationRules.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveTypeEscalationRulesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leaveTypeEscalationRules.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeEscalationRules))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveTypeEscalationRules in the database
        List<LeaveTypeEscalationRules> leaveTypeEscalationRulesList = leaveTypeEscalationRulesRepository.findAll();
        assertThat(leaveTypeEscalationRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeaveTypeEscalationRules() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypeEscalationRulesRepository.findAll().size();
        leaveTypeEscalationRules.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveTypeEscalationRulesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeEscalationRules))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveTypeEscalationRules in the database
        List<LeaveTypeEscalationRules> leaveTypeEscalationRulesList = leaveTypeEscalationRulesRepository.findAll();
        assertThat(leaveTypeEscalationRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeaveTypeEscalationRules() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypeEscalationRulesRepository.findAll().size();
        leaveTypeEscalationRules.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveTypeEscalationRulesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeEscalationRules))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveTypeEscalationRules in the database
        List<LeaveTypeEscalationRules> leaveTypeEscalationRulesList = leaveTypeEscalationRulesRepository.findAll();
        assertThat(leaveTypeEscalationRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLeaveTypeEscalationRules() throws Exception {
        // Initialize the database
        leaveTypeEscalationRulesRepository.saveAndFlush(leaveTypeEscalationRules);

        int databaseSizeBeforeDelete = leaveTypeEscalationRulesRepository.findAll().size();

        // Delete the leaveTypeEscalationRules
        restLeaveTypeEscalationRulesMockMvc
            .perform(delete(ENTITY_API_URL_ID, leaveTypeEscalationRules.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeaveTypeEscalationRules> leaveTypeEscalationRulesList = leaveTypeEscalationRulesRepository.findAll();
        assertThat(leaveTypeEscalationRulesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
