package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Attributes;
import com.venturedive.vendian_mono.domain.LeaveApprovalCriterias;
import com.venturedive.vendian_mono.domain.UserRelationApprovalRules;
import com.venturedive.vendian_mono.repository.UserRelationApprovalRulesRepository;
import com.venturedive.vendian_mono.service.criteria.UserRelationApprovalRulesCriteria;
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
 * Integration tests for the {@link UserRelationApprovalRulesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserRelationApprovalRulesResourceIT {

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

    private static final String ENTITY_API_URL = "/api/user-relation-approval-rules";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserRelationApprovalRulesRepository userRelationApprovalRulesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserRelationApprovalRulesMockMvc;

    private UserRelationApprovalRules userRelationApprovalRules;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserRelationApprovalRules createEntity(EntityManager em) {
        UserRelationApprovalRules userRelationApprovalRules = new UserRelationApprovalRules()
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
        userRelationApprovalRules.setAttribute(attributes);
        // Add required entity
        LeaveApprovalCriterias leaveApprovalCriterias;
        if (TestUtil.findAll(em, LeaveApprovalCriterias.class).isEmpty()) {
            leaveApprovalCriterias = LeaveApprovalCriteriasResourceIT.createEntity(em);
            em.persist(leaveApprovalCriterias);
            em.flush();
        } else {
            leaveApprovalCriterias = TestUtil.findAll(em, LeaveApprovalCriterias.class).get(0);
        }
        userRelationApprovalRules.setLeaveApprovalCriteria(leaveApprovalCriterias);
        return userRelationApprovalRules;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserRelationApprovalRules createUpdatedEntity(EntityManager em) {
        UserRelationApprovalRules userRelationApprovalRules = new UserRelationApprovalRules()
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
        userRelationApprovalRules.setAttribute(attributes);
        // Add required entity
        LeaveApprovalCriterias leaveApprovalCriterias;
        if (TestUtil.findAll(em, LeaveApprovalCriterias.class).isEmpty()) {
            leaveApprovalCriterias = LeaveApprovalCriteriasResourceIT.createUpdatedEntity(em);
            em.persist(leaveApprovalCriterias);
            em.flush();
        } else {
            leaveApprovalCriterias = TestUtil.findAll(em, LeaveApprovalCriterias.class).get(0);
        }
        userRelationApprovalRules.setLeaveApprovalCriteria(leaveApprovalCriterias);
        return userRelationApprovalRules;
    }

    @BeforeEach
    public void initTest() {
        userRelationApprovalRules = createEntity(em);
    }

    @Test
    @Transactional
    void createUserRelationApprovalRules() throws Exception {
        int databaseSizeBeforeCreate = userRelationApprovalRulesRepository.findAll().size();
        // Create the UserRelationApprovalRules
        restUserRelationApprovalRulesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRelationApprovalRules))
            )
            .andExpect(status().isCreated());

        // Validate the UserRelationApprovalRules in the database
        List<UserRelationApprovalRules> userRelationApprovalRulesList = userRelationApprovalRulesRepository.findAll();
        assertThat(userRelationApprovalRulesList).hasSize(databaseSizeBeforeCreate + 1);
        UserRelationApprovalRules testUserRelationApprovalRules = userRelationApprovalRulesList.get(
            userRelationApprovalRulesList.size() - 1
        );
        assertThat(testUserRelationApprovalRules.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testUserRelationApprovalRules.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testUserRelationApprovalRules.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testUserRelationApprovalRules.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testUserRelationApprovalRules.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createUserRelationApprovalRulesWithExistingId() throws Exception {
        // Create the UserRelationApprovalRules with an existing ID
        userRelationApprovalRules.setId(1L);

        int databaseSizeBeforeCreate = userRelationApprovalRulesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserRelationApprovalRulesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRelationApprovalRules))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserRelationApprovalRules in the database
        List<UserRelationApprovalRules> userRelationApprovalRulesList = userRelationApprovalRulesRepository.findAll();
        assertThat(userRelationApprovalRulesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEffDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = userRelationApprovalRulesRepository.findAll().size();
        // set the field null
        userRelationApprovalRules.setEffDate(null);

        // Create the UserRelationApprovalRules, which fails.

        restUserRelationApprovalRulesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRelationApprovalRules))
            )
            .andExpect(status().isBadRequest());

        List<UserRelationApprovalRules> userRelationApprovalRulesList = userRelationApprovalRulesRepository.findAll();
        assertThat(userRelationApprovalRulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = userRelationApprovalRulesRepository.findAll().size();
        // set the field null
        userRelationApprovalRules.setCreatedAt(null);

        // Create the UserRelationApprovalRules, which fails.

        restUserRelationApprovalRulesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRelationApprovalRules))
            )
            .andExpect(status().isBadRequest());

        List<UserRelationApprovalRules> userRelationApprovalRulesList = userRelationApprovalRulesRepository.findAll();
        assertThat(userRelationApprovalRulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = userRelationApprovalRulesRepository.findAll().size();
        // set the field null
        userRelationApprovalRules.setUpdatedAt(null);

        // Create the UserRelationApprovalRules, which fails.

        restUserRelationApprovalRulesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRelationApprovalRules))
            )
            .andExpect(status().isBadRequest());

        List<UserRelationApprovalRules> userRelationApprovalRulesList = userRelationApprovalRulesRepository.findAll();
        assertThat(userRelationApprovalRulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = userRelationApprovalRulesRepository.findAll().size();
        // set the field null
        userRelationApprovalRules.setVersion(null);

        // Create the UserRelationApprovalRules, which fails.

        restUserRelationApprovalRulesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRelationApprovalRules))
            )
            .andExpect(status().isBadRequest());

        List<UserRelationApprovalRules> userRelationApprovalRulesList = userRelationApprovalRulesRepository.findAll();
        assertThat(userRelationApprovalRulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUserRelationApprovalRules() throws Exception {
        // Initialize the database
        userRelationApprovalRulesRepository.saveAndFlush(userRelationApprovalRules);

        // Get all the userRelationApprovalRulesList
        restUserRelationApprovalRulesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userRelationApprovalRules.getId().intValue())))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getUserRelationApprovalRules() throws Exception {
        // Initialize the database
        userRelationApprovalRulesRepository.saveAndFlush(userRelationApprovalRules);

        // Get the userRelationApprovalRules
        restUserRelationApprovalRulesMockMvc
            .perform(get(ENTITY_API_URL_ID, userRelationApprovalRules.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userRelationApprovalRules.getId().intValue()))
            .andExpect(jsonPath("$.effDate").value(DEFAULT_EFF_DATE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getUserRelationApprovalRulesByIdFiltering() throws Exception {
        // Initialize the database
        userRelationApprovalRulesRepository.saveAndFlush(userRelationApprovalRules);

        Long id = userRelationApprovalRules.getId();

        defaultUserRelationApprovalRulesShouldBeFound("id.equals=" + id);
        defaultUserRelationApprovalRulesShouldNotBeFound("id.notEquals=" + id);

        defaultUserRelationApprovalRulesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUserRelationApprovalRulesShouldNotBeFound("id.greaterThan=" + id);

        defaultUserRelationApprovalRulesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUserRelationApprovalRulesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllUserRelationApprovalRulesByEffDateIsEqualToSomething() throws Exception {
        // Initialize the database
        userRelationApprovalRulesRepository.saveAndFlush(userRelationApprovalRules);

        // Get all the userRelationApprovalRulesList where effDate equals to DEFAULT_EFF_DATE
        defaultUserRelationApprovalRulesShouldBeFound("effDate.equals=" + DEFAULT_EFF_DATE);

        // Get all the userRelationApprovalRulesList where effDate equals to UPDATED_EFF_DATE
        defaultUserRelationApprovalRulesShouldNotBeFound("effDate.equals=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllUserRelationApprovalRulesByEffDateIsInShouldWork() throws Exception {
        // Initialize the database
        userRelationApprovalRulesRepository.saveAndFlush(userRelationApprovalRules);

        // Get all the userRelationApprovalRulesList where effDate in DEFAULT_EFF_DATE or UPDATED_EFF_DATE
        defaultUserRelationApprovalRulesShouldBeFound("effDate.in=" + DEFAULT_EFF_DATE + "," + UPDATED_EFF_DATE);

        // Get all the userRelationApprovalRulesList where effDate equals to UPDATED_EFF_DATE
        defaultUserRelationApprovalRulesShouldNotBeFound("effDate.in=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllUserRelationApprovalRulesByEffDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRelationApprovalRulesRepository.saveAndFlush(userRelationApprovalRules);

        // Get all the userRelationApprovalRulesList where effDate is not null
        defaultUserRelationApprovalRulesShouldBeFound("effDate.specified=true");

        // Get all the userRelationApprovalRulesList where effDate is null
        defaultUserRelationApprovalRulesShouldNotBeFound("effDate.specified=false");
    }

    @Test
    @Transactional
    void getAllUserRelationApprovalRulesByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        userRelationApprovalRulesRepository.saveAndFlush(userRelationApprovalRules);

        // Get all the userRelationApprovalRulesList where createdAt equals to DEFAULT_CREATED_AT
        defaultUserRelationApprovalRulesShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the userRelationApprovalRulesList where createdAt equals to UPDATED_CREATED_AT
        defaultUserRelationApprovalRulesShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllUserRelationApprovalRulesByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        userRelationApprovalRulesRepository.saveAndFlush(userRelationApprovalRules);

        // Get all the userRelationApprovalRulesList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultUserRelationApprovalRulesShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the userRelationApprovalRulesList where createdAt equals to UPDATED_CREATED_AT
        defaultUserRelationApprovalRulesShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllUserRelationApprovalRulesByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRelationApprovalRulesRepository.saveAndFlush(userRelationApprovalRules);

        // Get all the userRelationApprovalRulesList where createdAt is not null
        defaultUserRelationApprovalRulesShouldBeFound("createdAt.specified=true");

        // Get all the userRelationApprovalRulesList where createdAt is null
        defaultUserRelationApprovalRulesShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllUserRelationApprovalRulesByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        userRelationApprovalRulesRepository.saveAndFlush(userRelationApprovalRules);

        // Get all the userRelationApprovalRulesList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultUserRelationApprovalRulesShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the userRelationApprovalRulesList where updatedAt equals to UPDATED_UPDATED_AT
        defaultUserRelationApprovalRulesShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllUserRelationApprovalRulesByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        userRelationApprovalRulesRepository.saveAndFlush(userRelationApprovalRules);

        // Get all the userRelationApprovalRulesList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultUserRelationApprovalRulesShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the userRelationApprovalRulesList where updatedAt equals to UPDATED_UPDATED_AT
        defaultUserRelationApprovalRulesShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllUserRelationApprovalRulesByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRelationApprovalRulesRepository.saveAndFlush(userRelationApprovalRules);

        // Get all the userRelationApprovalRulesList where updatedAt is not null
        defaultUserRelationApprovalRulesShouldBeFound("updatedAt.specified=true");

        // Get all the userRelationApprovalRulesList where updatedAt is null
        defaultUserRelationApprovalRulesShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllUserRelationApprovalRulesByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        userRelationApprovalRulesRepository.saveAndFlush(userRelationApprovalRules);

        // Get all the userRelationApprovalRulesList where endDate equals to DEFAULT_END_DATE
        defaultUserRelationApprovalRulesShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the userRelationApprovalRulesList where endDate equals to UPDATED_END_DATE
        defaultUserRelationApprovalRulesShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllUserRelationApprovalRulesByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        userRelationApprovalRulesRepository.saveAndFlush(userRelationApprovalRules);

        // Get all the userRelationApprovalRulesList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultUserRelationApprovalRulesShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the userRelationApprovalRulesList where endDate equals to UPDATED_END_DATE
        defaultUserRelationApprovalRulesShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllUserRelationApprovalRulesByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRelationApprovalRulesRepository.saveAndFlush(userRelationApprovalRules);

        // Get all the userRelationApprovalRulesList where endDate is not null
        defaultUserRelationApprovalRulesShouldBeFound("endDate.specified=true");

        // Get all the userRelationApprovalRulesList where endDate is null
        defaultUserRelationApprovalRulesShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllUserRelationApprovalRulesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        userRelationApprovalRulesRepository.saveAndFlush(userRelationApprovalRules);

        // Get all the userRelationApprovalRulesList where version equals to DEFAULT_VERSION
        defaultUserRelationApprovalRulesShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the userRelationApprovalRulesList where version equals to UPDATED_VERSION
        defaultUserRelationApprovalRulesShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllUserRelationApprovalRulesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        userRelationApprovalRulesRepository.saveAndFlush(userRelationApprovalRules);

        // Get all the userRelationApprovalRulesList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultUserRelationApprovalRulesShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the userRelationApprovalRulesList where version equals to UPDATED_VERSION
        defaultUserRelationApprovalRulesShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllUserRelationApprovalRulesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRelationApprovalRulesRepository.saveAndFlush(userRelationApprovalRules);

        // Get all the userRelationApprovalRulesList where version is not null
        defaultUserRelationApprovalRulesShouldBeFound("version.specified=true");

        // Get all the userRelationApprovalRulesList where version is null
        defaultUserRelationApprovalRulesShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllUserRelationApprovalRulesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userRelationApprovalRulesRepository.saveAndFlush(userRelationApprovalRules);

        // Get all the userRelationApprovalRulesList where version is greater than or equal to DEFAULT_VERSION
        defaultUserRelationApprovalRulesShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the userRelationApprovalRulesList where version is greater than or equal to UPDATED_VERSION
        defaultUserRelationApprovalRulesShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllUserRelationApprovalRulesByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userRelationApprovalRulesRepository.saveAndFlush(userRelationApprovalRules);

        // Get all the userRelationApprovalRulesList where version is less than or equal to DEFAULT_VERSION
        defaultUserRelationApprovalRulesShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the userRelationApprovalRulesList where version is less than or equal to SMALLER_VERSION
        defaultUserRelationApprovalRulesShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllUserRelationApprovalRulesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        userRelationApprovalRulesRepository.saveAndFlush(userRelationApprovalRules);

        // Get all the userRelationApprovalRulesList where version is less than DEFAULT_VERSION
        defaultUserRelationApprovalRulesShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the userRelationApprovalRulesList where version is less than UPDATED_VERSION
        defaultUserRelationApprovalRulesShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllUserRelationApprovalRulesByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userRelationApprovalRulesRepository.saveAndFlush(userRelationApprovalRules);

        // Get all the userRelationApprovalRulesList where version is greater than DEFAULT_VERSION
        defaultUserRelationApprovalRulesShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the userRelationApprovalRulesList where version is greater than SMALLER_VERSION
        defaultUserRelationApprovalRulesShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllUserRelationApprovalRulesByAttributeIsEqualToSomething() throws Exception {
        Attributes attribute;
        if (TestUtil.findAll(em, Attributes.class).isEmpty()) {
            userRelationApprovalRulesRepository.saveAndFlush(userRelationApprovalRules);
            attribute = AttributesResourceIT.createEntity(em);
        } else {
            attribute = TestUtil.findAll(em, Attributes.class).get(0);
        }
        em.persist(attribute);
        em.flush();
        userRelationApprovalRules.setAttribute(attribute);
        userRelationApprovalRulesRepository.saveAndFlush(userRelationApprovalRules);
        Long attributeId = attribute.getId();

        // Get all the userRelationApprovalRulesList where attribute equals to attributeId
        defaultUserRelationApprovalRulesShouldBeFound("attributeId.equals=" + attributeId);

        // Get all the userRelationApprovalRulesList where attribute equals to (attributeId + 1)
        defaultUserRelationApprovalRulesShouldNotBeFound("attributeId.equals=" + (attributeId + 1));
    }

    @Test
    @Transactional
    void getAllUserRelationApprovalRulesByLeaveApprovalCriteriaIsEqualToSomething() throws Exception {
        LeaveApprovalCriterias leaveApprovalCriteria;
        if (TestUtil.findAll(em, LeaveApprovalCriterias.class).isEmpty()) {
            userRelationApprovalRulesRepository.saveAndFlush(userRelationApprovalRules);
            leaveApprovalCriteria = LeaveApprovalCriteriasResourceIT.createEntity(em);
        } else {
            leaveApprovalCriteria = TestUtil.findAll(em, LeaveApprovalCriterias.class).get(0);
        }
        em.persist(leaveApprovalCriteria);
        em.flush();
        userRelationApprovalRules.setLeaveApprovalCriteria(leaveApprovalCriteria);
        userRelationApprovalRulesRepository.saveAndFlush(userRelationApprovalRules);
        Long leaveApprovalCriteriaId = leaveApprovalCriteria.getId();

        // Get all the userRelationApprovalRulesList where leaveApprovalCriteria equals to leaveApprovalCriteriaId
        defaultUserRelationApprovalRulesShouldBeFound("leaveApprovalCriteriaId.equals=" + leaveApprovalCriteriaId);

        // Get all the userRelationApprovalRulesList where leaveApprovalCriteria equals to (leaveApprovalCriteriaId + 1)
        defaultUserRelationApprovalRulesShouldNotBeFound("leaveApprovalCriteriaId.equals=" + (leaveApprovalCriteriaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserRelationApprovalRulesShouldBeFound(String filter) throws Exception {
        restUserRelationApprovalRulesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userRelationApprovalRules.getId().intValue())))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restUserRelationApprovalRulesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUserRelationApprovalRulesShouldNotBeFound(String filter) throws Exception {
        restUserRelationApprovalRulesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserRelationApprovalRulesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingUserRelationApprovalRules() throws Exception {
        // Get the userRelationApprovalRules
        restUserRelationApprovalRulesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUserRelationApprovalRules() throws Exception {
        // Initialize the database
        userRelationApprovalRulesRepository.saveAndFlush(userRelationApprovalRules);

        int databaseSizeBeforeUpdate = userRelationApprovalRulesRepository.findAll().size();

        // Update the userRelationApprovalRules
        UserRelationApprovalRules updatedUserRelationApprovalRules = userRelationApprovalRulesRepository
            .findById(userRelationApprovalRules.getId())
            .get();
        // Disconnect from session so that the updates on updatedUserRelationApprovalRules are not directly saved in db
        em.detach(updatedUserRelationApprovalRules);
        updatedUserRelationApprovalRules
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restUserRelationApprovalRulesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUserRelationApprovalRules.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUserRelationApprovalRules))
            )
            .andExpect(status().isOk());

        // Validate the UserRelationApprovalRules in the database
        List<UserRelationApprovalRules> userRelationApprovalRulesList = userRelationApprovalRulesRepository.findAll();
        assertThat(userRelationApprovalRulesList).hasSize(databaseSizeBeforeUpdate);
        UserRelationApprovalRules testUserRelationApprovalRules = userRelationApprovalRulesList.get(
            userRelationApprovalRulesList.size() - 1
        );
        assertThat(testUserRelationApprovalRules.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testUserRelationApprovalRules.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testUserRelationApprovalRules.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testUserRelationApprovalRules.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testUserRelationApprovalRules.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingUserRelationApprovalRules() throws Exception {
        int databaseSizeBeforeUpdate = userRelationApprovalRulesRepository.findAll().size();
        userRelationApprovalRules.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserRelationApprovalRulesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userRelationApprovalRules.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRelationApprovalRules))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserRelationApprovalRules in the database
        List<UserRelationApprovalRules> userRelationApprovalRulesList = userRelationApprovalRulesRepository.findAll();
        assertThat(userRelationApprovalRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserRelationApprovalRules() throws Exception {
        int databaseSizeBeforeUpdate = userRelationApprovalRulesRepository.findAll().size();
        userRelationApprovalRules.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserRelationApprovalRulesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRelationApprovalRules))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserRelationApprovalRules in the database
        List<UserRelationApprovalRules> userRelationApprovalRulesList = userRelationApprovalRulesRepository.findAll();
        assertThat(userRelationApprovalRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserRelationApprovalRules() throws Exception {
        int databaseSizeBeforeUpdate = userRelationApprovalRulesRepository.findAll().size();
        userRelationApprovalRules.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserRelationApprovalRulesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRelationApprovalRules))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserRelationApprovalRules in the database
        List<UserRelationApprovalRules> userRelationApprovalRulesList = userRelationApprovalRulesRepository.findAll();
        assertThat(userRelationApprovalRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserRelationApprovalRulesWithPatch() throws Exception {
        // Initialize the database
        userRelationApprovalRulesRepository.saveAndFlush(userRelationApprovalRules);

        int databaseSizeBeforeUpdate = userRelationApprovalRulesRepository.findAll().size();

        // Update the userRelationApprovalRules using partial update
        UserRelationApprovalRules partialUpdatedUserRelationApprovalRules = new UserRelationApprovalRules();
        partialUpdatedUserRelationApprovalRules.setId(userRelationApprovalRules.getId());

        partialUpdatedUserRelationApprovalRules.effDate(UPDATED_EFF_DATE).version(UPDATED_VERSION);

        restUserRelationApprovalRulesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserRelationApprovalRules.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserRelationApprovalRules))
            )
            .andExpect(status().isOk());

        // Validate the UserRelationApprovalRules in the database
        List<UserRelationApprovalRules> userRelationApprovalRulesList = userRelationApprovalRulesRepository.findAll();
        assertThat(userRelationApprovalRulesList).hasSize(databaseSizeBeforeUpdate);
        UserRelationApprovalRules testUserRelationApprovalRules = userRelationApprovalRulesList.get(
            userRelationApprovalRulesList.size() - 1
        );
        assertThat(testUserRelationApprovalRules.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testUserRelationApprovalRules.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testUserRelationApprovalRules.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testUserRelationApprovalRules.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testUserRelationApprovalRules.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateUserRelationApprovalRulesWithPatch() throws Exception {
        // Initialize the database
        userRelationApprovalRulesRepository.saveAndFlush(userRelationApprovalRules);

        int databaseSizeBeforeUpdate = userRelationApprovalRulesRepository.findAll().size();

        // Update the userRelationApprovalRules using partial update
        UserRelationApprovalRules partialUpdatedUserRelationApprovalRules = new UserRelationApprovalRules();
        partialUpdatedUserRelationApprovalRules.setId(userRelationApprovalRules.getId());

        partialUpdatedUserRelationApprovalRules
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restUserRelationApprovalRulesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserRelationApprovalRules.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserRelationApprovalRules))
            )
            .andExpect(status().isOk());

        // Validate the UserRelationApprovalRules in the database
        List<UserRelationApprovalRules> userRelationApprovalRulesList = userRelationApprovalRulesRepository.findAll();
        assertThat(userRelationApprovalRulesList).hasSize(databaseSizeBeforeUpdate);
        UserRelationApprovalRules testUserRelationApprovalRules = userRelationApprovalRulesList.get(
            userRelationApprovalRulesList.size() - 1
        );
        assertThat(testUserRelationApprovalRules.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testUserRelationApprovalRules.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testUserRelationApprovalRules.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testUserRelationApprovalRules.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testUserRelationApprovalRules.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingUserRelationApprovalRules() throws Exception {
        int databaseSizeBeforeUpdate = userRelationApprovalRulesRepository.findAll().size();
        userRelationApprovalRules.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserRelationApprovalRulesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userRelationApprovalRules.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userRelationApprovalRules))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserRelationApprovalRules in the database
        List<UserRelationApprovalRules> userRelationApprovalRulesList = userRelationApprovalRulesRepository.findAll();
        assertThat(userRelationApprovalRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserRelationApprovalRules() throws Exception {
        int databaseSizeBeforeUpdate = userRelationApprovalRulesRepository.findAll().size();
        userRelationApprovalRules.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserRelationApprovalRulesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userRelationApprovalRules))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserRelationApprovalRules in the database
        List<UserRelationApprovalRules> userRelationApprovalRulesList = userRelationApprovalRulesRepository.findAll();
        assertThat(userRelationApprovalRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserRelationApprovalRules() throws Exception {
        int databaseSizeBeforeUpdate = userRelationApprovalRulesRepository.findAll().size();
        userRelationApprovalRules.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserRelationApprovalRulesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userRelationApprovalRules))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserRelationApprovalRules in the database
        List<UserRelationApprovalRules> userRelationApprovalRulesList = userRelationApprovalRulesRepository.findAll();
        assertThat(userRelationApprovalRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserRelationApprovalRules() throws Exception {
        // Initialize the database
        userRelationApprovalRulesRepository.saveAndFlush(userRelationApprovalRules);

        int databaseSizeBeforeDelete = userRelationApprovalRulesRepository.findAll().size();

        // Delete the userRelationApprovalRules
        restUserRelationApprovalRulesMockMvc
            .perform(delete(ENTITY_API_URL_ID, userRelationApprovalRules.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserRelationApprovalRules> userRelationApprovalRulesList = userRelationApprovalRulesRepository.findAll();
        assertThat(userRelationApprovalRulesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
