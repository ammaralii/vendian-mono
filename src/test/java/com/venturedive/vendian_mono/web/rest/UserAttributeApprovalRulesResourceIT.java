package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Attributes;
import com.venturedive.vendian_mono.domain.LeaveApprovalCriterias;
import com.venturedive.vendian_mono.domain.UserAttributeApprovalRules;
import com.venturedive.vendian_mono.repository.UserAttributeApprovalRulesRepository;
import com.venturedive.vendian_mono.service.criteria.UserAttributeApprovalRulesCriteria;
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
 * Integration tests for the {@link UserAttributeApprovalRulesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserAttributeApprovalRulesResourceIT {

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

    private static final String ENTITY_API_URL = "/api/user-attribute-approval-rules";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserAttributeApprovalRulesRepository userAttributeApprovalRulesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserAttributeApprovalRulesMockMvc;

    private UserAttributeApprovalRules userAttributeApprovalRules;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAttributeApprovalRules createEntity(EntityManager em) {
        UserAttributeApprovalRules userAttributeApprovalRules = new UserAttributeApprovalRules()
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
        userAttributeApprovalRules.setAttribute(attributes);
        // Add required entity
        LeaveApprovalCriterias leaveApprovalCriterias;
        if (TestUtil.findAll(em, LeaveApprovalCriterias.class).isEmpty()) {
            leaveApprovalCriterias = LeaveApprovalCriteriasResourceIT.createEntity(em);
            em.persist(leaveApprovalCriterias);
            em.flush();
        } else {
            leaveApprovalCriterias = TestUtil.findAll(em, LeaveApprovalCriterias.class).get(0);
        }
        userAttributeApprovalRules.setLeaveApprovalCriteria(leaveApprovalCriterias);
        return userAttributeApprovalRules;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAttributeApprovalRules createUpdatedEntity(EntityManager em) {
        UserAttributeApprovalRules userAttributeApprovalRules = new UserAttributeApprovalRules()
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
        userAttributeApprovalRules.setAttribute(attributes);
        // Add required entity
        LeaveApprovalCriterias leaveApprovalCriterias;
        if (TestUtil.findAll(em, LeaveApprovalCriterias.class).isEmpty()) {
            leaveApprovalCriterias = LeaveApprovalCriteriasResourceIT.createUpdatedEntity(em);
            em.persist(leaveApprovalCriterias);
            em.flush();
        } else {
            leaveApprovalCriterias = TestUtil.findAll(em, LeaveApprovalCriterias.class).get(0);
        }
        userAttributeApprovalRules.setLeaveApprovalCriteria(leaveApprovalCriterias);
        return userAttributeApprovalRules;
    }

    @BeforeEach
    public void initTest() {
        userAttributeApprovalRules = createEntity(em);
    }

    @Test
    @Transactional
    void createUserAttributeApprovalRules() throws Exception {
        int databaseSizeBeforeCreate = userAttributeApprovalRulesRepository.findAll().size();
        // Create the UserAttributeApprovalRules
        restUserAttributeApprovalRulesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAttributeApprovalRules))
            )
            .andExpect(status().isCreated());

        // Validate the UserAttributeApprovalRules in the database
        List<UserAttributeApprovalRules> userAttributeApprovalRulesList = userAttributeApprovalRulesRepository.findAll();
        assertThat(userAttributeApprovalRulesList).hasSize(databaseSizeBeforeCreate + 1);
        UserAttributeApprovalRules testUserAttributeApprovalRules = userAttributeApprovalRulesList.get(
            userAttributeApprovalRulesList.size() - 1
        );
        assertThat(testUserAttributeApprovalRules.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testUserAttributeApprovalRules.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testUserAttributeApprovalRules.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testUserAttributeApprovalRules.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testUserAttributeApprovalRules.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createUserAttributeApprovalRulesWithExistingId() throws Exception {
        // Create the UserAttributeApprovalRules with an existing ID
        userAttributeApprovalRules.setId(1L);

        int databaseSizeBeforeCreate = userAttributeApprovalRulesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserAttributeApprovalRulesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAttributeApprovalRules))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAttributeApprovalRules in the database
        List<UserAttributeApprovalRules> userAttributeApprovalRulesList = userAttributeApprovalRulesRepository.findAll();
        assertThat(userAttributeApprovalRulesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEffDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAttributeApprovalRulesRepository.findAll().size();
        // set the field null
        userAttributeApprovalRules.setEffDate(null);

        // Create the UserAttributeApprovalRules, which fails.

        restUserAttributeApprovalRulesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAttributeApprovalRules))
            )
            .andExpect(status().isBadRequest());

        List<UserAttributeApprovalRules> userAttributeApprovalRulesList = userAttributeApprovalRulesRepository.findAll();
        assertThat(userAttributeApprovalRulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAttributeApprovalRulesRepository.findAll().size();
        // set the field null
        userAttributeApprovalRules.setCreatedAt(null);

        // Create the UserAttributeApprovalRules, which fails.

        restUserAttributeApprovalRulesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAttributeApprovalRules))
            )
            .andExpect(status().isBadRequest());

        List<UserAttributeApprovalRules> userAttributeApprovalRulesList = userAttributeApprovalRulesRepository.findAll();
        assertThat(userAttributeApprovalRulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAttributeApprovalRulesRepository.findAll().size();
        // set the field null
        userAttributeApprovalRules.setUpdatedAt(null);

        // Create the UserAttributeApprovalRules, which fails.

        restUserAttributeApprovalRulesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAttributeApprovalRules))
            )
            .andExpect(status().isBadRequest());

        List<UserAttributeApprovalRules> userAttributeApprovalRulesList = userAttributeApprovalRulesRepository.findAll();
        assertThat(userAttributeApprovalRulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAttributeApprovalRulesRepository.findAll().size();
        // set the field null
        userAttributeApprovalRules.setVersion(null);

        // Create the UserAttributeApprovalRules, which fails.

        restUserAttributeApprovalRulesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAttributeApprovalRules))
            )
            .andExpect(status().isBadRequest());

        List<UserAttributeApprovalRules> userAttributeApprovalRulesList = userAttributeApprovalRulesRepository.findAll();
        assertThat(userAttributeApprovalRulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUserAttributeApprovalRules() throws Exception {
        // Initialize the database
        userAttributeApprovalRulesRepository.saveAndFlush(userAttributeApprovalRules);

        // Get all the userAttributeApprovalRulesList
        restUserAttributeApprovalRulesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAttributeApprovalRules.getId().intValue())))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getUserAttributeApprovalRules() throws Exception {
        // Initialize the database
        userAttributeApprovalRulesRepository.saveAndFlush(userAttributeApprovalRules);

        // Get the userAttributeApprovalRules
        restUserAttributeApprovalRulesMockMvc
            .perform(get(ENTITY_API_URL_ID, userAttributeApprovalRules.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userAttributeApprovalRules.getId().intValue()))
            .andExpect(jsonPath("$.effDate").value(DEFAULT_EFF_DATE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getUserAttributeApprovalRulesByIdFiltering() throws Exception {
        // Initialize the database
        userAttributeApprovalRulesRepository.saveAndFlush(userAttributeApprovalRules);

        Long id = userAttributeApprovalRules.getId();

        defaultUserAttributeApprovalRulesShouldBeFound("id.equals=" + id);
        defaultUserAttributeApprovalRulesShouldNotBeFound("id.notEquals=" + id);

        defaultUserAttributeApprovalRulesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUserAttributeApprovalRulesShouldNotBeFound("id.greaterThan=" + id);

        defaultUserAttributeApprovalRulesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUserAttributeApprovalRulesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllUserAttributeApprovalRulesByEffDateIsEqualToSomething() throws Exception {
        // Initialize the database
        userAttributeApprovalRulesRepository.saveAndFlush(userAttributeApprovalRules);

        // Get all the userAttributeApprovalRulesList where effDate equals to DEFAULT_EFF_DATE
        defaultUserAttributeApprovalRulesShouldBeFound("effDate.equals=" + DEFAULT_EFF_DATE);

        // Get all the userAttributeApprovalRulesList where effDate equals to UPDATED_EFF_DATE
        defaultUserAttributeApprovalRulesShouldNotBeFound("effDate.equals=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllUserAttributeApprovalRulesByEffDateIsInShouldWork() throws Exception {
        // Initialize the database
        userAttributeApprovalRulesRepository.saveAndFlush(userAttributeApprovalRules);

        // Get all the userAttributeApprovalRulesList where effDate in DEFAULT_EFF_DATE or UPDATED_EFF_DATE
        defaultUserAttributeApprovalRulesShouldBeFound("effDate.in=" + DEFAULT_EFF_DATE + "," + UPDATED_EFF_DATE);

        // Get all the userAttributeApprovalRulesList where effDate equals to UPDATED_EFF_DATE
        defaultUserAttributeApprovalRulesShouldNotBeFound("effDate.in=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllUserAttributeApprovalRulesByEffDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        userAttributeApprovalRulesRepository.saveAndFlush(userAttributeApprovalRules);

        // Get all the userAttributeApprovalRulesList where effDate is not null
        defaultUserAttributeApprovalRulesShouldBeFound("effDate.specified=true");

        // Get all the userAttributeApprovalRulesList where effDate is null
        defaultUserAttributeApprovalRulesShouldNotBeFound("effDate.specified=false");
    }

    @Test
    @Transactional
    void getAllUserAttributeApprovalRulesByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        userAttributeApprovalRulesRepository.saveAndFlush(userAttributeApprovalRules);

        // Get all the userAttributeApprovalRulesList where createdAt equals to DEFAULT_CREATED_AT
        defaultUserAttributeApprovalRulesShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the userAttributeApprovalRulesList where createdAt equals to UPDATED_CREATED_AT
        defaultUserAttributeApprovalRulesShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllUserAttributeApprovalRulesByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        userAttributeApprovalRulesRepository.saveAndFlush(userAttributeApprovalRules);

        // Get all the userAttributeApprovalRulesList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultUserAttributeApprovalRulesShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the userAttributeApprovalRulesList where createdAt equals to UPDATED_CREATED_AT
        defaultUserAttributeApprovalRulesShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllUserAttributeApprovalRulesByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        userAttributeApprovalRulesRepository.saveAndFlush(userAttributeApprovalRules);

        // Get all the userAttributeApprovalRulesList where createdAt is not null
        defaultUserAttributeApprovalRulesShouldBeFound("createdAt.specified=true");

        // Get all the userAttributeApprovalRulesList where createdAt is null
        defaultUserAttributeApprovalRulesShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllUserAttributeApprovalRulesByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        userAttributeApprovalRulesRepository.saveAndFlush(userAttributeApprovalRules);

        // Get all the userAttributeApprovalRulesList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultUserAttributeApprovalRulesShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the userAttributeApprovalRulesList where updatedAt equals to UPDATED_UPDATED_AT
        defaultUserAttributeApprovalRulesShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllUserAttributeApprovalRulesByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        userAttributeApprovalRulesRepository.saveAndFlush(userAttributeApprovalRules);

        // Get all the userAttributeApprovalRulesList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultUserAttributeApprovalRulesShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the userAttributeApprovalRulesList where updatedAt equals to UPDATED_UPDATED_AT
        defaultUserAttributeApprovalRulesShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllUserAttributeApprovalRulesByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        userAttributeApprovalRulesRepository.saveAndFlush(userAttributeApprovalRules);

        // Get all the userAttributeApprovalRulesList where updatedAt is not null
        defaultUserAttributeApprovalRulesShouldBeFound("updatedAt.specified=true");

        // Get all the userAttributeApprovalRulesList where updatedAt is null
        defaultUserAttributeApprovalRulesShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllUserAttributeApprovalRulesByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        userAttributeApprovalRulesRepository.saveAndFlush(userAttributeApprovalRules);

        // Get all the userAttributeApprovalRulesList where endDate equals to DEFAULT_END_DATE
        defaultUserAttributeApprovalRulesShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the userAttributeApprovalRulesList where endDate equals to UPDATED_END_DATE
        defaultUserAttributeApprovalRulesShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllUserAttributeApprovalRulesByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        userAttributeApprovalRulesRepository.saveAndFlush(userAttributeApprovalRules);

        // Get all the userAttributeApprovalRulesList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultUserAttributeApprovalRulesShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the userAttributeApprovalRulesList where endDate equals to UPDATED_END_DATE
        defaultUserAttributeApprovalRulesShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllUserAttributeApprovalRulesByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        userAttributeApprovalRulesRepository.saveAndFlush(userAttributeApprovalRules);

        // Get all the userAttributeApprovalRulesList where endDate is not null
        defaultUserAttributeApprovalRulesShouldBeFound("endDate.specified=true");

        // Get all the userAttributeApprovalRulesList where endDate is null
        defaultUserAttributeApprovalRulesShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllUserAttributeApprovalRulesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        userAttributeApprovalRulesRepository.saveAndFlush(userAttributeApprovalRules);

        // Get all the userAttributeApprovalRulesList where version equals to DEFAULT_VERSION
        defaultUserAttributeApprovalRulesShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the userAttributeApprovalRulesList where version equals to UPDATED_VERSION
        defaultUserAttributeApprovalRulesShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllUserAttributeApprovalRulesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        userAttributeApprovalRulesRepository.saveAndFlush(userAttributeApprovalRules);

        // Get all the userAttributeApprovalRulesList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultUserAttributeApprovalRulesShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the userAttributeApprovalRulesList where version equals to UPDATED_VERSION
        defaultUserAttributeApprovalRulesShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllUserAttributeApprovalRulesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        userAttributeApprovalRulesRepository.saveAndFlush(userAttributeApprovalRules);

        // Get all the userAttributeApprovalRulesList where version is not null
        defaultUserAttributeApprovalRulesShouldBeFound("version.specified=true");

        // Get all the userAttributeApprovalRulesList where version is null
        defaultUserAttributeApprovalRulesShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllUserAttributeApprovalRulesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userAttributeApprovalRulesRepository.saveAndFlush(userAttributeApprovalRules);

        // Get all the userAttributeApprovalRulesList where version is greater than or equal to DEFAULT_VERSION
        defaultUserAttributeApprovalRulesShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the userAttributeApprovalRulesList where version is greater than or equal to UPDATED_VERSION
        defaultUserAttributeApprovalRulesShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllUserAttributeApprovalRulesByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userAttributeApprovalRulesRepository.saveAndFlush(userAttributeApprovalRules);

        // Get all the userAttributeApprovalRulesList where version is less than or equal to DEFAULT_VERSION
        defaultUserAttributeApprovalRulesShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the userAttributeApprovalRulesList where version is less than or equal to SMALLER_VERSION
        defaultUserAttributeApprovalRulesShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllUserAttributeApprovalRulesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        userAttributeApprovalRulesRepository.saveAndFlush(userAttributeApprovalRules);

        // Get all the userAttributeApprovalRulesList where version is less than DEFAULT_VERSION
        defaultUserAttributeApprovalRulesShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the userAttributeApprovalRulesList where version is less than UPDATED_VERSION
        defaultUserAttributeApprovalRulesShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllUserAttributeApprovalRulesByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userAttributeApprovalRulesRepository.saveAndFlush(userAttributeApprovalRules);

        // Get all the userAttributeApprovalRulesList where version is greater than DEFAULT_VERSION
        defaultUserAttributeApprovalRulesShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the userAttributeApprovalRulesList where version is greater than SMALLER_VERSION
        defaultUserAttributeApprovalRulesShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllUserAttributeApprovalRulesByAttributeIsEqualToSomething() throws Exception {
        Attributes attribute;
        if (TestUtil.findAll(em, Attributes.class).isEmpty()) {
            userAttributeApprovalRulesRepository.saveAndFlush(userAttributeApprovalRules);
            attribute = AttributesResourceIT.createEntity(em);
        } else {
            attribute = TestUtil.findAll(em, Attributes.class).get(0);
        }
        em.persist(attribute);
        em.flush();
        userAttributeApprovalRules.setAttribute(attribute);
        userAttributeApprovalRulesRepository.saveAndFlush(userAttributeApprovalRules);
        Long attributeId = attribute.getId();

        // Get all the userAttributeApprovalRulesList where attribute equals to attributeId
        defaultUserAttributeApprovalRulesShouldBeFound("attributeId.equals=" + attributeId);

        // Get all the userAttributeApprovalRulesList where attribute equals to (attributeId + 1)
        defaultUserAttributeApprovalRulesShouldNotBeFound("attributeId.equals=" + (attributeId + 1));
    }

    @Test
    @Transactional
    void getAllUserAttributeApprovalRulesByLeaveApprovalCriteriaIsEqualToSomething() throws Exception {
        LeaveApprovalCriterias leaveApprovalCriteria;
        if (TestUtil.findAll(em, LeaveApprovalCriterias.class).isEmpty()) {
            userAttributeApprovalRulesRepository.saveAndFlush(userAttributeApprovalRules);
            leaveApprovalCriteria = LeaveApprovalCriteriasResourceIT.createEntity(em);
        } else {
            leaveApprovalCriteria = TestUtil.findAll(em, LeaveApprovalCriterias.class).get(0);
        }
        em.persist(leaveApprovalCriteria);
        em.flush();
        userAttributeApprovalRules.setLeaveApprovalCriteria(leaveApprovalCriteria);
        userAttributeApprovalRulesRepository.saveAndFlush(userAttributeApprovalRules);
        Long leaveApprovalCriteriaId = leaveApprovalCriteria.getId();

        // Get all the userAttributeApprovalRulesList where leaveApprovalCriteria equals to leaveApprovalCriteriaId
        defaultUserAttributeApprovalRulesShouldBeFound("leaveApprovalCriteriaId.equals=" + leaveApprovalCriteriaId);

        // Get all the userAttributeApprovalRulesList where leaveApprovalCriteria equals to (leaveApprovalCriteriaId + 1)
        defaultUserAttributeApprovalRulesShouldNotBeFound("leaveApprovalCriteriaId.equals=" + (leaveApprovalCriteriaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserAttributeApprovalRulesShouldBeFound(String filter) throws Exception {
        restUserAttributeApprovalRulesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAttributeApprovalRules.getId().intValue())))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restUserAttributeApprovalRulesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUserAttributeApprovalRulesShouldNotBeFound(String filter) throws Exception {
        restUserAttributeApprovalRulesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserAttributeApprovalRulesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingUserAttributeApprovalRules() throws Exception {
        // Get the userAttributeApprovalRules
        restUserAttributeApprovalRulesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUserAttributeApprovalRules() throws Exception {
        // Initialize the database
        userAttributeApprovalRulesRepository.saveAndFlush(userAttributeApprovalRules);

        int databaseSizeBeforeUpdate = userAttributeApprovalRulesRepository.findAll().size();

        // Update the userAttributeApprovalRules
        UserAttributeApprovalRules updatedUserAttributeApprovalRules = userAttributeApprovalRulesRepository
            .findById(userAttributeApprovalRules.getId())
            .get();
        // Disconnect from session so that the updates on updatedUserAttributeApprovalRules are not directly saved in db
        em.detach(updatedUserAttributeApprovalRules);
        updatedUserAttributeApprovalRules
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restUserAttributeApprovalRulesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUserAttributeApprovalRules.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUserAttributeApprovalRules))
            )
            .andExpect(status().isOk());

        // Validate the UserAttributeApprovalRules in the database
        List<UserAttributeApprovalRules> userAttributeApprovalRulesList = userAttributeApprovalRulesRepository.findAll();
        assertThat(userAttributeApprovalRulesList).hasSize(databaseSizeBeforeUpdate);
        UserAttributeApprovalRules testUserAttributeApprovalRules = userAttributeApprovalRulesList.get(
            userAttributeApprovalRulesList.size() - 1
        );
        assertThat(testUserAttributeApprovalRules.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testUserAttributeApprovalRules.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testUserAttributeApprovalRules.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testUserAttributeApprovalRules.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testUserAttributeApprovalRules.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingUserAttributeApprovalRules() throws Exception {
        int databaseSizeBeforeUpdate = userAttributeApprovalRulesRepository.findAll().size();
        userAttributeApprovalRules.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserAttributeApprovalRulesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userAttributeApprovalRules.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAttributeApprovalRules))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAttributeApprovalRules in the database
        List<UserAttributeApprovalRules> userAttributeApprovalRulesList = userAttributeApprovalRulesRepository.findAll();
        assertThat(userAttributeApprovalRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserAttributeApprovalRules() throws Exception {
        int databaseSizeBeforeUpdate = userAttributeApprovalRulesRepository.findAll().size();
        userAttributeApprovalRules.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAttributeApprovalRulesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAttributeApprovalRules))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAttributeApprovalRules in the database
        List<UserAttributeApprovalRules> userAttributeApprovalRulesList = userAttributeApprovalRulesRepository.findAll();
        assertThat(userAttributeApprovalRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserAttributeApprovalRules() throws Exception {
        int databaseSizeBeforeUpdate = userAttributeApprovalRulesRepository.findAll().size();
        userAttributeApprovalRules.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAttributeApprovalRulesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAttributeApprovalRules))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserAttributeApprovalRules in the database
        List<UserAttributeApprovalRules> userAttributeApprovalRulesList = userAttributeApprovalRulesRepository.findAll();
        assertThat(userAttributeApprovalRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserAttributeApprovalRulesWithPatch() throws Exception {
        // Initialize the database
        userAttributeApprovalRulesRepository.saveAndFlush(userAttributeApprovalRules);

        int databaseSizeBeforeUpdate = userAttributeApprovalRulesRepository.findAll().size();

        // Update the userAttributeApprovalRules using partial update
        UserAttributeApprovalRules partialUpdatedUserAttributeApprovalRules = new UserAttributeApprovalRules();
        partialUpdatedUserAttributeApprovalRules.setId(userAttributeApprovalRules.getId());

        partialUpdatedUserAttributeApprovalRules.effDate(UPDATED_EFF_DATE).updatedAt(UPDATED_UPDATED_AT);

        restUserAttributeApprovalRulesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserAttributeApprovalRules.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserAttributeApprovalRules))
            )
            .andExpect(status().isOk());

        // Validate the UserAttributeApprovalRules in the database
        List<UserAttributeApprovalRules> userAttributeApprovalRulesList = userAttributeApprovalRulesRepository.findAll();
        assertThat(userAttributeApprovalRulesList).hasSize(databaseSizeBeforeUpdate);
        UserAttributeApprovalRules testUserAttributeApprovalRules = userAttributeApprovalRulesList.get(
            userAttributeApprovalRulesList.size() - 1
        );
        assertThat(testUserAttributeApprovalRules.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testUserAttributeApprovalRules.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testUserAttributeApprovalRules.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testUserAttributeApprovalRules.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testUserAttributeApprovalRules.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateUserAttributeApprovalRulesWithPatch() throws Exception {
        // Initialize the database
        userAttributeApprovalRulesRepository.saveAndFlush(userAttributeApprovalRules);

        int databaseSizeBeforeUpdate = userAttributeApprovalRulesRepository.findAll().size();

        // Update the userAttributeApprovalRules using partial update
        UserAttributeApprovalRules partialUpdatedUserAttributeApprovalRules = new UserAttributeApprovalRules();
        partialUpdatedUserAttributeApprovalRules.setId(userAttributeApprovalRules.getId());

        partialUpdatedUserAttributeApprovalRules
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restUserAttributeApprovalRulesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserAttributeApprovalRules.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserAttributeApprovalRules))
            )
            .andExpect(status().isOk());

        // Validate the UserAttributeApprovalRules in the database
        List<UserAttributeApprovalRules> userAttributeApprovalRulesList = userAttributeApprovalRulesRepository.findAll();
        assertThat(userAttributeApprovalRulesList).hasSize(databaseSizeBeforeUpdate);
        UserAttributeApprovalRules testUserAttributeApprovalRules = userAttributeApprovalRulesList.get(
            userAttributeApprovalRulesList.size() - 1
        );
        assertThat(testUserAttributeApprovalRules.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testUserAttributeApprovalRules.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testUserAttributeApprovalRules.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testUserAttributeApprovalRules.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testUserAttributeApprovalRules.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingUserAttributeApprovalRules() throws Exception {
        int databaseSizeBeforeUpdate = userAttributeApprovalRulesRepository.findAll().size();
        userAttributeApprovalRules.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserAttributeApprovalRulesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userAttributeApprovalRules.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userAttributeApprovalRules))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAttributeApprovalRules in the database
        List<UserAttributeApprovalRules> userAttributeApprovalRulesList = userAttributeApprovalRulesRepository.findAll();
        assertThat(userAttributeApprovalRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserAttributeApprovalRules() throws Exception {
        int databaseSizeBeforeUpdate = userAttributeApprovalRulesRepository.findAll().size();
        userAttributeApprovalRules.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAttributeApprovalRulesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userAttributeApprovalRules))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAttributeApprovalRules in the database
        List<UserAttributeApprovalRules> userAttributeApprovalRulesList = userAttributeApprovalRulesRepository.findAll();
        assertThat(userAttributeApprovalRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserAttributeApprovalRules() throws Exception {
        int databaseSizeBeforeUpdate = userAttributeApprovalRulesRepository.findAll().size();
        userAttributeApprovalRules.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAttributeApprovalRulesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userAttributeApprovalRules))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserAttributeApprovalRules in the database
        List<UserAttributeApprovalRules> userAttributeApprovalRulesList = userAttributeApprovalRulesRepository.findAll();
        assertThat(userAttributeApprovalRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserAttributeApprovalRules() throws Exception {
        // Initialize the database
        userAttributeApprovalRulesRepository.saveAndFlush(userAttributeApprovalRules);

        int databaseSizeBeforeDelete = userAttributeApprovalRulesRepository.findAll().size();

        // Delete the userAttributeApprovalRules
        restUserAttributeApprovalRulesMockMvc
            .perform(delete(ENTITY_API_URL_ID, userAttributeApprovalRules.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserAttributeApprovalRules> userAttributeApprovalRulesList = userAttributeApprovalRulesRepository.findAll();
        assertThat(userAttributeApprovalRulesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
