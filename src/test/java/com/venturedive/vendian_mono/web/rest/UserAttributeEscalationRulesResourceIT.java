package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Attributes;
import com.venturedive.vendian_mono.domain.LeaveEscalationCriterias;
import com.venturedive.vendian_mono.domain.LeaveStatuses;
import com.venturedive.vendian_mono.domain.UserAttributeEscalationRules;
import com.venturedive.vendian_mono.repository.UserAttributeEscalationRulesRepository;
import com.venturedive.vendian_mono.service.criteria.UserAttributeEscalationRulesCriteria;
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
 * Integration tests for the {@link UserAttributeEscalationRulesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserAttributeEscalationRulesResourceIT {

    private static final Integer DEFAULT_LEAVE_ESCALATION_CRITERIA_ID = 1;
    private static final Integer UPDATED_LEAVE_ESCALATION_CRITERIA_ID = 2;
    private static final Integer SMALLER_LEAVE_ESCALATION_CRITERIA_ID = 1 - 1;

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

    private static final String ENTITY_API_URL = "/api/user-attribute-escalation-rules";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserAttributeEscalationRulesRepository userAttributeEscalationRulesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserAttributeEscalationRulesMockMvc;

    private UserAttributeEscalationRules userAttributeEscalationRules;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAttributeEscalationRules createEntity(EntityManager em) {
        UserAttributeEscalationRules userAttributeEscalationRules = new UserAttributeEscalationRules()
            .leaveEscalationCriteriaId(DEFAULT_LEAVE_ESCALATION_CRITERIA_ID)
            .noOfDays(DEFAULT_NO_OF_DAYS)
            .effDate(DEFAULT_EFF_DATE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .endDate(DEFAULT_END_DATE)
            .version(DEFAULT_VERSION);
        // Add required entity
        Attributes attributes;
        if (TestUtil.findAll(em, Attributes.class).isEmpty()) {
            attributes = AttributesResourceIT.createEntity(em);
            em.persist(attributes);
            em.flush();
        } else {
            attributes = TestUtil.findAll(em, Attributes.class).get(0);
        }
        userAttributeEscalationRules.setAttribute(attributes);
        // Add required entity
        LeaveStatuses leaveStatuses;
        if (TestUtil.findAll(em, LeaveStatuses.class).isEmpty()) {
            leaveStatuses = LeaveStatusesResourceIT.createEntity(em);
            em.persist(leaveStatuses);
            em.flush();
        } else {
            leaveStatuses = TestUtil.findAll(em, LeaveStatuses.class).get(0);
        }
        userAttributeEscalationRules.setApproverStatus(leaveStatuses);
        return userAttributeEscalationRules;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAttributeEscalationRules createUpdatedEntity(EntityManager em) {
        UserAttributeEscalationRules userAttributeEscalationRules = new UserAttributeEscalationRules()
            .leaveEscalationCriteriaId(UPDATED_LEAVE_ESCALATION_CRITERIA_ID)
            .noOfDays(UPDATED_NO_OF_DAYS)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);
        // Add required entity
        Attributes attributes;
        if (TestUtil.findAll(em, Attributes.class).isEmpty()) {
            attributes = AttributesResourceIT.createUpdatedEntity(em);
            em.persist(attributes);
            em.flush();
        } else {
            attributes = TestUtil.findAll(em, Attributes.class).get(0);
        }
        userAttributeEscalationRules.setAttribute(attributes);
        // Add required entity
        LeaveStatuses leaveStatuses;
        if (TestUtil.findAll(em, LeaveStatuses.class).isEmpty()) {
            leaveStatuses = LeaveStatusesResourceIT.createUpdatedEntity(em);
            em.persist(leaveStatuses);
            em.flush();
        } else {
            leaveStatuses = TestUtil.findAll(em, LeaveStatuses.class).get(0);
        }
        userAttributeEscalationRules.setApproverStatus(leaveStatuses);
        return userAttributeEscalationRules;
    }

    @BeforeEach
    public void initTest() {
        userAttributeEscalationRules = createEntity(em);
    }

    @Test
    @Transactional
    void createUserAttributeEscalationRules() throws Exception {
        int databaseSizeBeforeCreate = userAttributeEscalationRulesRepository.findAll().size();
        // Create the UserAttributeEscalationRules
        restUserAttributeEscalationRulesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAttributeEscalationRules))
            )
            .andExpect(status().isCreated());

        // Validate the UserAttributeEscalationRules in the database
        List<UserAttributeEscalationRules> userAttributeEscalationRulesList = userAttributeEscalationRulesRepository.findAll();
        assertThat(userAttributeEscalationRulesList).hasSize(databaseSizeBeforeCreate + 1);
        UserAttributeEscalationRules testUserAttributeEscalationRules = userAttributeEscalationRulesList.get(
            userAttributeEscalationRulesList.size() - 1
        );
        assertThat(testUserAttributeEscalationRules.getLeaveEscalationCriteriaId()).isEqualTo(DEFAULT_LEAVE_ESCALATION_CRITERIA_ID);
        assertThat(testUserAttributeEscalationRules.getNoOfDays()).isEqualTo(DEFAULT_NO_OF_DAYS);
        assertThat(testUserAttributeEscalationRules.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testUserAttributeEscalationRules.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testUserAttributeEscalationRules.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testUserAttributeEscalationRules.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testUserAttributeEscalationRules.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createUserAttributeEscalationRulesWithExistingId() throws Exception {
        // Create the UserAttributeEscalationRules with an existing ID
        userAttributeEscalationRules.setId(1L);

        int databaseSizeBeforeCreate = userAttributeEscalationRulesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserAttributeEscalationRulesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAttributeEscalationRules))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAttributeEscalationRules in the database
        List<UserAttributeEscalationRules> userAttributeEscalationRulesList = userAttributeEscalationRulesRepository.findAll();
        assertThat(userAttributeEscalationRulesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLeaveEscalationCriteriaIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAttributeEscalationRulesRepository.findAll().size();
        // set the field null
        userAttributeEscalationRules.setLeaveEscalationCriteriaId(null);

        // Create the UserAttributeEscalationRules, which fails.

        restUserAttributeEscalationRulesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAttributeEscalationRules))
            )
            .andExpect(status().isBadRequest());

        List<UserAttributeEscalationRules> userAttributeEscalationRulesList = userAttributeEscalationRulesRepository.findAll();
        assertThat(userAttributeEscalationRulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNoOfDaysIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAttributeEscalationRulesRepository.findAll().size();
        // set the field null
        userAttributeEscalationRules.setNoOfDays(null);

        // Create the UserAttributeEscalationRules, which fails.

        restUserAttributeEscalationRulesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAttributeEscalationRules))
            )
            .andExpect(status().isBadRequest());

        List<UserAttributeEscalationRules> userAttributeEscalationRulesList = userAttributeEscalationRulesRepository.findAll();
        assertThat(userAttributeEscalationRulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEffDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAttributeEscalationRulesRepository.findAll().size();
        // set the field null
        userAttributeEscalationRules.setEffDate(null);

        // Create the UserAttributeEscalationRules, which fails.

        restUserAttributeEscalationRulesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAttributeEscalationRules))
            )
            .andExpect(status().isBadRequest());

        List<UserAttributeEscalationRules> userAttributeEscalationRulesList = userAttributeEscalationRulesRepository.findAll();
        assertThat(userAttributeEscalationRulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAttributeEscalationRulesRepository.findAll().size();
        // set the field null
        userAttributeEscalationRules.setCreatedAt(null);

        // Create the UserAttributeEscalationRules, which fails.

        restUserAttributeEscalationRulesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAttributeEscalationRules))
            )
            .andExpect(status().isBadRequest());

        List<UserAttributeEscalationRules> userAttributeEscalationRulesList = userAttributeEscalationRulesRepository.findAll();
        assertThat(userAttributeEscalationRulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAttributeEscalationRulesRepository.findAll().size();
        // set the field null
        userAttributeEscalationRules.setUpdatedAt(null);

        // Create the UserAttributeEscalationRules, which fails.

        restUserAttributeEscalationRulesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAttributeEscalationRules))
            )
            .andExpect(status().isBadRequest());

        List<UserAttributeEscalationRules> userAttributeEscalationRulesList = userAttributeEscalationRulesRepository.findAll();
        assertThat(userAttributeEscalationRulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAttributeEscalationRulesRepository.findAll().size();
        // set the field null
        userAttributeEscalationRules.setVersion(null);

        // Create the UserAttributeEscalationRules, which fails.

        restUserAttributeEscalationRulesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAttributeEscalationRules))
            )
            .andExpect(status().isBadRequest());

        List<UserAttributeEscalationRules> userAttributeEscalationRulesList = userAttributeEscalationRulesRepository.findAll();
        assertThat(userAttributeEscalationRulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUserAttributeEscalationRules() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        // Get all the userAttributeEscalationRulesList
        restUserAttributeEscalationRulesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAttributeEscalationRules.getId().intValue())))
            .andExpect(jsonPath("$.[*].leaveEscalationCriteriaId").value(hasItem(DEFAULT_LEAVE_ESCALATION_CRITERIA_ID)))
            .andExpect(jsonPath("$.[*].noOfDays").value(hasItem(DEFAULT_NO_OF_DAYS)))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getUserAttributeEscalationRules() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        // Get the userAttributeEscalationRules
        restUserAttributeEscalationRulesMockMvc
            .perform(get(ENTITY_API_URL_ID, userAttributeEscalationRules.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userAttributeEscalationRules.getId().intValue()))
            .andExpect(jsonPath("$.leaveEscalationCriteriaId").value(DEFAULT_LEAVE_ESCALATION_CRITERIA_ID))
            .andExpect(jsonPath("$.noOfDays").value(DEFAULT_NO_OF_DAYS))
            .andExpect(jsonPath("$.effDate").value(DEFAULT_EFF_DATE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getUserAttributeEscalationRulesByIdFiltering() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        Long id = userAttributeEscalationRules.getId();

        defaultUserAttributeEscalationRulesShouldBeFound("id.equals=" + id);
        defaultUserAttributeEscalationRulesShouldNotBeFound("id.notEquals=" + id);

        defaultUserAttributeEscalationRulesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUserAttributeEscalationRulesShouldNotBeFound("id.greaterThan=" + id);

        defaultUserAttributeEscalationRulesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUserAttributeEscalationRulesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllUserAttributeEscalationRulesByLeaveEscalationCriteriaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        // Get all the userAttributeEscalationRulesList where leaveEscalationCriteriaId equals to DEFAULT_LEAVE_ESCALATION_CRITERIA_ID
        defaultUserAttributeEscalationRulesShouldBeFound("leaveEscalationCriteriaId.equals=" + DEFAULT_LEAVE_ESCALATION_CRITERIA_ID);

        // Get all the userAttributeEscalationRulesList where leaveEscalationCriteriaId equals to UPDATED_LEAVE_ESCALATION_CRITERIA_ID
        defaultUserAttributeEscalationRulesShouldNotBeFound("leaveEscalationCriteriaId.equals=" + UPDATED_LEAVE_ESCALATION_CRITERIA_ID);
    }

    @Test
    @Transactional
    void getAllUserAttributeEscalationRulesByLeaveEscalationCriteriaIdIsInShouldWork() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        // Get all the userAttributeEscalationRulesList where leaveEscalationCriteriaId in DEFAULT_LEAVE_ESCALATION_CRITERIA_ID or UPDATED_LEAVE_ESCALATION_CRITERIA_ID
        defaultUserAttributeEscalationRulesShouldBeFound(
            "leaveEscalationCriteriaId.in=" + DEFAULT_LEAVE_ESCALATION_CRITERIA_ID + "," + UPDATED_LEAVE_ESCALATION_CRITERIA_ID
        );

        // Get all the userAttributeEscalationRulesList where leaveEscalationCriteriaId equals to UPDATED_LEAVE_ESCALATION_CRITERIA_ID
        defaultUserAttributeEscalationRulesShouldNotBeFound("leaveEscalationCriteriaId.in=" + UPDATED_LEAVE_ESCALATION_CRITERIA_ID);
    }

    @Test
    @Transactional
    void getAllUserAttributeEscalationRulesByLeaveEscalationCriteriaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        // Get all the userAttributeEscalationRulesList where leaveEscalationCriteriaId is not null
        defaultUserAttributeEscalationRulesShouldBeFound("leaveEscalationCriteriaId.specified=true");

        // Get all the userAttributeEscalationRulesList where leaveEscalationCriteriaId is null
        defaultUserAttributeEscalationRulesShouldNotBeFound("leaveEscalationCriteriaId.specified=false");
    }

    @Test
    @Transactional
    void getAllUserAttributeEscalationRulesByLeaveEscalationCriteriaIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        // Get all the userAttributeEscalationRulesList where leaveEscalationCriteriaId is greater than or equal to DEFAULT_LEAVE_ESCALATION_CRITERIA_ID
        defaultUserAttributeEscalationRulesShouldBeFound(
            "leaveEscalationCriteriaId.greaterThanOrEqual=" + DEFAULT_LEAVE_ESCALATION_CRITERIA_ID
        );

        // Get all the userAttributeEscalationRulesList where leaveEscalationCriteriaId is greater than or equal to UPDATED_LEAVE_ESCALATION_CRITERIA_ID
        defaultUserAttributeEscalationRulesShouldNotBeFound(
            "leaveEscalationCriteriaId.greaterThanOrEqual=" + UPDATED_LEAVE_ESCALATION_CRITERIA_ID
        );
    }

    @Test
    @Transactional
    void getAllUserAttributeEscalationRulesByLeaveEscalationCriteriaIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        // Get all the userAttributeEscalationRulesList where leaveEscalationCriteriaId is less than or equal to DEFAULT_LEAVE_ESCALATION_CRITERIA_ID
        defaultUserAttributeEscalationRulesShouldBeFound(
            "leaveEscalationCriteriaId.lessThanOrEqual=" + DEFAULT_LEAVE_ESCALATION_CRITERIA_ID
        );

        // Get all the userAttributeEscalationRulesList where leaveEscalationCriteriaId is less than or equal to SMALLER_LEAVE_ESCALATION_CRITERIA_ID
        defaultUserAttributeEscalationRulesShouldNotBeFound(
            "leaveEscalationCriteriaId.lessThanOrEqual=" + SMALLER_LEAVE_ESCALATION_CRITERIA_ID
        );
    }

    @Test
    @Transactional
    void getAllUserAttributeEscalationRulesByLeaveEscalationCriteriaIdIsLessThanSomething() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        // Get all the userAttributeEscalationRulesList where leaveEscalationCriteriaId is less than DEFAULT_LEAVE_ESCALATION_CRITERIA_ID
        defaultUserAttributeEscalationRulesShouldNotBeFound("leaveEscalationCriteriaId.lessThan=" + DEFAULT_LEAVE_ESCALATION_CRITERIA_ID);

        // Get all the userAttributeEscalationRulesList where leaveEscalationCriteriaId is less than UPDATED_LEAVE_ESCALATION_CRITERIA_ID
        defaultUserAttributeEscalationRulesShouldBeFound("leaveEscalationCriteriaId.lessThan=" + UPDATED_LEAVE_ESCALATION_CRITERIA_ID);
    }

    @Test
    @Transactional
    void getAllUserAttributeEscalationRulesByLeaveEscalationCriteriaIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        // Get all the userAttributeEscalationRulesList where leaveEscalationCriteriaId is greater than DEFAULT_LEAVE_ESCALATION_CRITERIA_ID
        defaultUserAttributeEscalationRulesShouldNotBeFound(
            "leaveEscalationCriteriaId.greaterThan=" + DEFAULT_LEAVE_ESCALATION_CRITERIA_ID
        );

        // Get all the userAttributeEscalationRulesList where leaveEscalationCriteriaId is greater than SMALLER_LEAVE_ESCALATION_CRITERIA_ID
        defaultUserAttributeEscalationRulesShouldBeFound("leaveEscalationCriteriaId.greaterThan=" + SMALLER_LEAVE_ESCALATION_CRITERIA_ID);
    }

    @Test
    @Transactional
    void getAllUserAttributeEscalationRulesByNoOfDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        // Get all the userAttributeEscalationRulesList where noOfDays equals to DEFAULT_NO_OF_DAYS
        defaultUserAttributeEscalationRulesShouldBeFound("noOfDays.equals=" + DEFAULT_NO_OF_DAYS);

        // Get all the userAttributeEscalationRulesList where noOfDays equals to UPDATED_NO_OF_DAYS
        defaultUserAttributeEscalationRulesShouldNotBeFound("noOfDays.equals=" + UPDATED_NO_OF_DAYS);
    }

    @Test
    @Transactional
    void getAllUserAttributeEscalationRulesByNoOfDaysIsInShouldWork() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        // Get all the userAttributeEscalationRulesList where noOfDays in DEFAULT_NO_OF_DAYS or UPDATED_NO_OF_DAYS
        defaultUserAttributeEscalationRulesShouldBeFound("noOfDays.in=" + DEFAULT_NO_OF_DAYS + "," + UPDATED_NO_OF_DAYS);

        // Get all the userAttributeEscalationRulesList where noOfDays equals to UPDATED_NO_OF_DAYS
        defaultUserAttributeEscalationRulesShouldNotBeFound("noOfDays.in=" + UPDATED_NO_OF_DAYS);
    }

    @Test
    @Transactional
    void getAllUserAttributeEscalationRulesByNoOfDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        // Get all the userAttributeEscalationRulesList where noOfDays is not null
        defaultUserAttributeEscalationRulesShouldBeFound("noOfDays.specified=true");

        // Get all the userAttributeEscalationRulesList where noOfDays is null
        defaultUserAttributeEscalationRulesShouldNotBeFound("noOfDays.specified=false");
    }

    @Test
    @Transactional
    void getAllUserAttributeEscalationRulesByNoOfDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        // Get all the userAttributeEscalationRulesList where noOfDays is greater than or equal to DEFAULT_NO_OF_DAYS
        defaultUserAttributeEscalationRulesShouldBeFound("noOfDays.greaterThanOrEqual=" + DEFAULT_NO_OF_DAYS);

        // Get all the userAttributeEscalationRulesList where noOfDays is greater than or equal to UPDATED_NO_OF_DAYS
        defaultUserAttributeEscalationRulesShouldNotBeFound("noOfDays.greaterThanOrEqual=" + UPDATED_NO_OF_DAYS);
    }

    @Test
    @Transactional
    void getAllUserAttributeEscalationRulesByNoOfDaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        // Get all the userAttributeEscalationRulesList where noOfDays is less than or equal to DEFAULT_NO_OF_DAYS
        defaultUserAttributeEscalationRulesShouldBeFound("noOfDays.lessThanOrEqual=" + DEFAULT_NO_OF_DAYS);

        // Get all the userAttributeEscalationRulesList where noOfDays is less than or equal to SMALLER_NO_OF_DAYS
        defaultUserAttributeEscalationRulesShouldNotBeFound("noOfDays.lessThanOrEqual=" + SMALLER_NO_OF_DAYS);
    }

    @Test
    @Transactional
    void getAllUserAttributeEscalationRulesByNoOfDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        // Get all the userAttributeEscalationRulesList where noOfDays is less than DEFAULT_NO_OF_DAYS
        defaultUserAttributeEscalationRulesShouldNotBeFound("noOfDays.lessThan=" + DEFAULT_NO_OF_DAYS);

        // Get all the userAttributeEscalationRulesList where noOfDays is less than UPDATED_NO_OF_DAYS
        defaultUserAttributeEscalationRulesShouldBeFound("noOfDays.lessThan=" + UPDATED_NO_OF_DAYS);
    }

    @Test
    @Transactional
    void getAllUserAttributeEscalationRulesByNoOfDaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        // Get all the userAttributeEscalationRulesList where noOfDays is greater than DEFAULT_NO_OF_DAYS
        defaultUserAttributeEscalationRulesShouldNotBeFound("noOfDays.greaterThan=" + DEFAULT_NO_OF_DAYS);

        // Get all the userAttributeEscalationRulesList where noOfDays is greater than SMALLER_NO_OF_DAYS
        defaultUserAttributeEscalationRulesShouldBeFound("noOfDays.greaterThan=" + SMALLER_NO_OF_DAYS);
    }

    @Test
    @Transactional
    void getAllUserAttributeEscalationRulesByEffDateIsEqualToSomething() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        // Get all the userAttributeEscalationRulesList where effDate equals to DEFAULT_EFF_DATE
        defaultUserAttributeEscalationRulesShouldBeFound("effDate.equals=" + DEFAULT_EFF_DATE);

        // Get all the userAttributeEscalationRulesList where effDate equals to UPDATED_EFF_DATE
        defaultUserAttributeEscalationRulesShouldNotBeFound("effDate.equals=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllUserAttributeEscalationRulesByEffDateIsInShouldWork() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        // Get all the userAttributeEscalationRulesList where effDate in DEFAULT_EFF_DATE or UPDATED_EFF_DATE
        defaultUserAttributeEscalationRulesShouldBeFound("effDate.in=" + DEFAULT_EFF_DATE + "," + UPDATED_EFF_DATE);

        // Get all the userAttributeEscalationRulesList where effDate equals to UPDATED_EFF_DATE
        defaultUserAttributeEscalationRulesShouldNotBeFound("effDate.in=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllUserAttributeEscalationRulesByEffDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        // Get all the userAttributeEscalationRulesList where effDate is not null
        defaultUserAttributeEscalationRulesShouldBeFound("effDate.specified=true");

        // Get all the userAttributeEscalationRulesList where effDate is null
        defaultUserAttributeEscalationRulesShouldNotBeFound("effDate.specified=false");
    }

    @Test
    @Transactional
    void getAllUserAttributeEscalationRulesByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        // Get all the userAttributeEscalationRulesList where createdAt equals to DEFAULT_CREATED_AT
        defaultUserAttributeEscalationRulesShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the userAttributeEscalationRulesList where createdAt equals to UPDATED_CREATED_AT
        defaultUserAttributeEscalationRulesShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllUserAttributeEscalationRulesByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        // Get all the userAttributeEscalationRulesList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultUserAttributeEscalationRulesShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the userAttributeEscalationRulesList where createdAt equals to UPDATED_CREATED_AT
        defaultUserAttributeEscalationRulesShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllUserAttributeEscalationRulesByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        // Get all the userAttributeEscalationRulesList where createdAt is not null
        defaultUserAttributeEscalationRulesShouldBeFound("createdAt.specified=true");

        // Get all the userAttributeEscalationRulesList where createdAt is null
        defaultUserAttributeEscalationRulesShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllUserAttributeEscalationRulesByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        // Get all the userAttributeEscalationRulesList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultUserAttributeEscalationRulesShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the userAttributeEscalationRulesList where updatedAt equals to UPDATED_UPDATED_AT
        defaultUserAttributeEscalationRulesShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllUserAttributeEscalationRulesByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        // Get all the userAttributeEscalationRulesList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultUserAttributeEscalationRulesShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the userAttributeEscalationRulesList where updatedAt equals to UPDATED_UPDATED_AT
        defaultUserAttributeEscalationRulesShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllUserAttributeEscalationRulesByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        // Get all the userAttributeEscalationRulesList where updatedAt is not null
        defaultUserAttributeEscalationRulesShouldBeFound("updatedAt.specified=true");

        // Get all the userAttributeEscalationRulesList where updatedAt is null
        defaultUserAttributeEscalationRulesShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllUserAttributeEscalationRulesByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        // Get all the userAttributeEscalationRulesList where endDate equals to DEFAULT_END_DATE
        defaultUserAttributeEscalationRulesShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the userAttributeEscalationRulesList where endDate equals to UPDATED_END_DATE
        defaultUserAttributeEscalationRulesShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllUserAttributeEscalationRulesByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        // Get all the userAttributeEscalationRulesList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultUserAttributeEscalationRulesShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the userAttributeEscalationRulesList where endDate equals to UPDATED_END_DATE
        defaultUserAttributeEscalationRulesShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllUserAttributeEscalationRulesByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        // Get all the userAttributeEscalationRulesList where endDate is not null
        defaultUserAttributeEscalationRulesShouldBeFound("endDate.specified=true");

        // Get all the userAttributeEscalationRulesList where endDate is null
        defaultUserAttributeEscalationRulesShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllUserAttributeEscalationRulesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        // Get all the userAttributeEscalationRulesList where version equals to DEFAULT_VERSION
        defaultUserAttributeEscalationRulesShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the userAttributeEscalationRulesList where version equals to UPDATED_VERSION
        defaultUserAttributeEscalationRulesShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllUserAttributeEscalationRulesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        // Get all the userAttributeEscalationRulesList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultUserAttributeEscalationRulesShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the userAttributeEscalationRulesList where version equals to UPDATED_VERSION
        defaultUserAttributeEscalationRulesShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllUserAttributeEscalationRulesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        // Get all the userAttributeEscalationRulesList where version is not null
        defaultUserAttributeEscalationRulesShouldBeFound("version.specified=true");

        // Get all the userAttributeEscalationRulesList where version is null
        defaultUserAttributeEscalationRulesShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllUserAttributeEscalationRulesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        // Get all the userAttributeEscalationRulesList where version is greater than or equal to DEFAULT_VERSION
        defaultUserAttributeEscalationRulesShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the userAttributeEscalationRulesList where version is greater than or equal to UPDATED_VERSION
        defaultUserAttributeEscalationRulesShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllUserAttributeEscalationRulesByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        // Get all the userAttributeEscalationRulesList where version is less than or equal to DEFAULT_VERSION
        defaultUserAttributeEscalationRulesShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the userAttributeEscalationRulesList where version is less than or equal to SMALLER_VERSION
        defaultUserAttributeEscalationRulesShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllUserAttributeEscalationRulesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        // Get all the userAttributeEscalationRulesList where version is less than DEFAULT_VERSION
        defaultUserAttributeEscalationRulesShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the userAttributeEscalationRulesList where version is less than UPDATED_VERSION
        defaultUserAttributeEscalationRulesShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllUserAttributeEscalationRulesByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        // Get all the userAttributeEscalationRulesList where version is greater than DEFAULT_VERSION
        defaultUserAttributeEscalationRulesShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the userAttributeEscalationRulesList where version is greater than SMALLER_VERSION
        defaultUserAttributeEscalationRulesShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllUserAttributeEscalationRulesByAttributeIsEqualToSomething() throws Exception {
        Attributes attribute;
        if (TestUtil.findAll(em, Attributes.class).isEmpty()) {
            userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);
            attribute = AttributesResourceIT.createEntity(em);
        } else {
            attribute = TestUtil.findAll(em, Attributes.class).get(0);
        }
        em.persist(attribute);
        em.flush();
        userAttributeEscalationRules.setAttribute(attribute);
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);
        Long attributeId = attribute.getId();

        // Get all the userAttributeEscalationRulesList where attribute equals to attributeId
        defaultUserAttributeEscalationRulesShouldBeFound("attributeId.equals=" + attributeId);

        // Get all the userAttributeEscalationRulesList where attribute equals to (attributeId + 1)
        defaultUserAttributeEscalationRulesShouldNotBeFound("attributeId.equals=" + (attributeId + 1));
    }

    @Test
    @Transactional
    void getAllUserAttributeEscalationRulesByApproverStatusIsEqualToSomething() throws Exception {
        LeaveStatuses approverStatus;
        if (TestUtil.findAll(em, LeaveStatuses.class).isEmpty()) {
            userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);
            approverStatus = LeaveStatusesResourceIT.createEntity(em);
        } else {
            approverStatus = TestUtil.findAll(em, LeaveStatuses.class).get(0);
        }
        em.persist(approverStatus);
        em.flush();
        userAttributeEscalationRules.setApproverStatus(approverStatus);
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);
        Long approverStatusId = approverStatus.getId();

        // Get all the userAttributeEscalationRulesList where approverStatus equals to approverStatusId
        defaultUserAttributeEscalationRulesShouldBeFound("approverStatusId.equals=" + approverStatusId);

        // Get all the userAttributeEscalationRulesList where approverStatus equals to (approverStatusId + 1)
        defaultUserAttributeEscalationRulesShouldNotBeFound("approverStatusId.equals=" + (approverStatusId + 1));
    }

    @Test
    @Transactional
    void getAllUserAttributeEscalationRulesByLeaveescalationcriteriaIsEqualToSomething() throws Exception {
        LeaveEscalationCriterias leaveescalationcriteria;
        if (TestUtil.findAll(em, LeaveEscalationCriterias.class).isEmpty()) {
            userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);
            leaveescalationcriteria = LeaveEscalationCriteriasResourceIT.createEntity(em);
        } else {
            leaveescalationcriteria = TestUtil.findAll(em, LeaveEscalationCriterias.class).get(0);
        }
        em.persist(leaveescalationcriteria);
        em.flush();
        userAttributeEscalationRules.setLeaveescalationcriteria(leaveescalationcriteria);
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);
        Long leaveescalationcriteriaId = leaveescalationcriteria.getId();

        // Get all the userAttributeEscalationRulesList where leaveescalationcriteria equals to leaveescalationcriteriaId
        defaultUserAttributeEscalationRulesShouldBeFound("leaveescalationcriteriaId.equals=" + leaveescalationcriteriaId);

        // Get all the userAttributeEscalationRulesList where leaveescalationcriteria equals to (leaveescalationcriteriaId + 1)
        defaultUserAttributeEscalationRulesShouldNotBeFound("leaveescalationcriteriaId.equals=" + (leaveescalationcriteriaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserAttributeEscalationRulesShouldBeFound(String filter) throws Exception {
        restUserAttributeEscalationRulesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAttributeEscalationRules.getId().intValue())))
            .andExpect(jsonPath("$.[*].leaveEscalationCriteriaId").value(hasItem(DEFAULT_LEAVE_ESCALATION_CRITERIA_ID)))
            .andExpect(jsonPath("$.[*].noOfDays").value(hasItem(DEFAULT_NO_OF_DAYS)))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restUserAttributeEscalationRulesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUserAttributeEscalationRulesShouldNotBeFound(String filter) throws Exception {
        restUserAttributeEscalationRulesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserAttributeEscalationRulesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingUserAttributeEscalationRules() throws Exception {
        // Get the userAttributeEscalationRules
        restUserAttributeEscalationRulesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUserAttributeEscalationRules() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        int databaseSizeBeforeUpdate = userAttributeEscalationRulesRepository.findAll().size();

        // Update the userAttributeEscalationRules
        UserAttributeEscalationRules updatedUserAttributeEscalationRules = userAttributeEscalationRulesRepository
            .findById(userAttributeEscalationRules.getId())
            .get();
        // Disconnect from session so that the updates on updatedUserAttributeEscalationRules are not directly saved in db
        em.detach(updatedUserAttributeEscalationRules);
        updatedUserAttributeEscalationRules
            .leaveEscalationCriteriaId(UPDATED_LEAVE_ESCALATION_CRITERIA_ID)
            .noOfDays(UPDATED_NO_OF_DAYS)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restUserAttributeEscalationRulesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUserAttributeEscalationRules.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUserAttributeEscalationRules))
            )
            .andExpect(status().isOk());

        // Validate the UserAttributeEscalationRules in the database
        List<UserAttributeEscalationRules> userAttributeEscalationRulesList = userAttributeEscalationRulesRepository.findAll();
        assertThat(userAttributeEscalationRulesList).hasSize(databaseSizeBeforeUpdate);
        UserAttributeEscalationRules testUserAttributeEscalationRules = userAttributeEscalationRulesList.get(
            userAttributeEscalationRulesList.size() - 1
        );
        assertThat(testUserAttributeEscalationRules.getLeaveEscalationCriteriaId()).isEqualTo(UPDATED_LEAVE_ESCALATION_CRITERIA_ID);
        assertThat(testUserAttributeEscalationRules.getNoOfDays()).isEqualTo(UPDATED_NO_OF_DAYS);
        assertThat(testUserAttributeEscalationRules.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testUserAttributeEscalationRules.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testUserAttributeEscalationRules.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testUserAttributeEscalationRules.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testUserAttributeEscalationRules.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingUserAttributeEscalationRules() throws Exception {
        int databaseSizeBeforeUpdate = userAttributeEscalationRulesRepository.findAll().size();
        userAttributeEscalationRules.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserAttributeEscalationRulesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userAttributeEscalationRules.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAttributeEscalationRules))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAttributeEscalationRules in the database
        List<UserAttributeEscalationRules> userAttributeEscalationRulesList = userAttributeEscalationRulesRepository.findAll();
        assertThat(userAttributeEscalationRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserAttributeEscalationRules() throws Exception {
        int databaseSizeBeforeUpdate = userAttributeEscalationRulesRepository.findAll().size();
        userAttributeEscalationRules.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAttributeEscalationRulesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAttributeEscalationRules))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAttributeEscalationRules in the database
        List<UserAttributeEscalationRules> userAttributeEscalationRulesList = userAttributeEscalationRulesRepository.findAll();
        assertThat(userAttributeEscalationRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserAttributeEscalationRules() throws Exception {
        int databaseSizeBeforeUpdate = userAttributeEscalationRulesRepository.findAll().size();
        userAttributeEscalationRules.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAttributeEscalationRulesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAttributeEscalationRules))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserAttributeEscalationRules in the database
        List<UserAttributeEscalationRules> userAttributeEscalationRulesList = userAttributeEscalationRulesRepository.findAll();
        assertThat(userAttributeEscalationRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserAttributeEscalationRulesWithPatch() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        int databaseSizeBeforeUpdate = userAttributeEscalationRulesRepository.findAll().size();

        // Update the userAttributeEscalationRules using partial update
        UserAttributeEscalationRules partialUpdatedUserAttributeEscalationRules = new UserAttributeEscalationRules();
        partialUpdatedUserAttributeEscalationRules.setId(userAttributeEscalationRules.getId());

        partialUpdatedUserAttributeEscalationRules
            .leaveEscalationCriteriaId(UPDATED_LEAVE_ESCALATION_CRITERIA_ID)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restUserAttributeEscalationRulesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserAttributeEscalationRules.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserAttributeEscalationRules))
            )
            .andExpect(status().isOk());

        // Validate the UserAttributeEscalationRules in the database
        List<UserAttributeEscalationRules> userAttributeEscalationRulesList = userAttributeEscalationRulesRepository.findAll();
        assertThat(userAttributeEscalationRulesList).hasSize(databaseSizeBeforeUpdate);
        UserAttributeEscalationRules testUserAttributeEscalationRules = userAttributeEscalationRulesList.get(
            userAttributeEscalationRulesList.size() - 1
        );
        assertThat(testUserAttributeEscalationRules.getLeaveEscalationCriteriaId()).isEqualTo(UPDATED_LEAVE_ESCALATION_CRITERIA_ID);
        assertThat(testUserAttributeEscalationRules.getNoOfDays()).isEqualTo(DEFAULT_NO_OF_DAYS);
        assertThat(testUserAttributeEscalationRules.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testUserAttributeEscalationRules.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testUserAttributeEscalationRules.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testUserAttributeEscalationRules.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testUserAttributeEscalationRules.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateUserAttributeEscalationRulesWithPatch() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        int databaseSizeBeforeUpdate = userAttributeEscalationRulesRepository.findAll().size();

        // Update the userAttributeEscalationRules using partial update
        UserAttributeEscalationRules partialUpdatedUserAttributeEscalationRules = new UserAttributeEscalationRules();
        partialUpdatedUserAttributeEscalationRules.setId(userAttributeEscalationRules.getId());

        partialUpdatedUserAttributeEscalationRules
            .leaveEscalationCriteriaId(UPDATED_LEAVE_ESCALATION_CRITERIA_ID)
            .noOfDays(UPDATED_NO_OF_DAYS)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restUserAttributeEscalationRulesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserAttributeEscalationRules.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserAttributeEscalationRules))
            )
            .andExpect(status().isOk());

        // Validate the UserAttributeEscalationRules in the database
        List<UserAttributeEscalationRules> userAttributeEscalationRulesList = userAttributeEscalationRulesRepository.findAll();
        assertThat(userAttributeEscalationRulesList).hasSize(databaseSizeBeforeUpdate);
        UserAttributeEscalationRules testUserAttributeEscalationRules = userAttributeEscalationRulesList.get(
            userAttributeEscalationRulesList.size() - 1
        );
        assertThat(testUserAttributeEscalationRules.getLeaveEscalationCriteriaId()).isEqualTo(UPDATED_LEAVE_ESCALATION_CRITERIA_ID);
        assertThat(testUserAttributeEscalationRules.getNoOfDays()).isEqualTo(UPDATED_NO_OF_DAYS);
        assertThat(testUserAttributeEscalationRules.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testUserAttributeEscalationRules.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testUserAttributeEscalationRules.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testUserAttributeEscalationRules.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testUserAttributeEscalationRules.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingUserAttributeEscalationRules() throws Exception {
        int databaseSizeBeforeUpdate = userAttributeEscalationRulesRepository.findAll().size();
        userAttributeEscalationRules.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserAttributeEscalationRulesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userAttributeEscalationRules.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userAttributeEscalationRules))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAttributeEscalationRules in the database
        List<UserAttributeEscalationRules> userAttributeEscalationRulesList = userAttributeEscalationRulesRepository.findAll();
        assertThat(userAttributeEscalationRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserAttributeEscalationRules() throws Exception {
        int databaseSizeBeforeUpdate = userAttributeEscalationRulesRepository.findAll().size();
        userAttributeEscalationRules.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAttributeEscalationRulesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userAttributeEscalationRules))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAttributeEscalationRules in the database
        List<UserAttributeEscalationRules> userAttributeEscalationRulesList = userAttributeEscalationRulesRepository.findAll();
        assertThat(userAttributeEscalationRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserAttributeEscalationRules() throws Exception {
        int databaseSizeBeforeUpdate = userAttributeEscalationRulesRepository.findAll().size();
        userAttributeEscalationRules.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAttributeEscalationRulesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userAttributeEscalationRules))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserAttributeEscalationRules in the database
        List<UserAttributeEscalationRules> userAttributeEscalationRulesList = userAttributeEscalationRulesRepository.findAll();
        assertThat(userAttributeEscalationRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserAttributeEscalationRules() throws Exception {
        // Initialize the database
        userAttributeEscalationRulesRepository.saveAndFlush(userAttributeEscalationRules);

        int databaseSizeBeforeDelete = userAttributeEscalationRulesRepository.findAll().size();

        // Delete the userAttributeEscalationRules
        restUserAttributeEscalationRulesMockMvc
            .perform(delete(ENTITY_API_URL_ID, userAttributeEscalationRules.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserAttributeEscalationRules> userAttributeEscalationRulesList = userAttributeEscalationRulesRepository.findAll();
        assertThat(userAttributeEscalationRulesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
