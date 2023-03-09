package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Attributes;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.UserRelations;
import com.venturedive.vendian_mono.repository.UserRelationsRepository;
import com.venturedive.vendian_mono.service.criteria.UserRelationsCriteria;
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
 * Integration tests for the {@link UserRelationsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserRelationsResourceIT {

    private static final String DEFAULT_REFERENCE_TYPE = "AAAAAAA";
    private static final String UPDATED_REFERENCE_TYPE = "BBBBBBB";

    private static final Integer DEFAULT_REFERENCE_ID = 1;
    private static final Integer UPDATED_REFERENCE_ID = 2;
    private static final Integer SMALLER_REFERENCE_ID = 1 - 1;

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

    private static final String ENTITY_API_URL = "/api/user-relations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserRelationsRepository userRelationsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserRelationsMockMvc;

    private UserRelations userRelations;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserRelations createEntity(EntityManager em) {
        UserRelations userRelations = new UserRelations()
            .referenceType(DEFAULT_REFERENCE_TYPE)
            .referenceId(DEFAULT_REFERENCE_ID)
            .effDate(DEFAULT_EFF_DATE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .endDate(DEFAULT_END_DATE)
            .version(DEFAULT_VERSION);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        userRelations.setUser(employees);
        // Add required entity
        Attributes attributes;
        if (TestUtil.findAll(em, Attributes.class).isEmpty()) {
            attributes = AttributesResourceIT.createEntity(em);
            em.persist(attributes);
            em.flush();
        } else {
            attributes = TestUtil.findAll(em, Attributes.class).get(0);
        }
        userRelations.setAttribute(attributes);
        return userRelations;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserRelations createUpdatedEntity(EntityManager em) {
        UserRelations userRelations = new UserRelations()
            .referenceType(UPDATED_REFERENCE_TYPE)
            .referenceId(UPDATED_REFERENCE_ID)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createUpdatedEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        userRelations.setUser(employees);
        // Add required entity
        Attributes attributes;
        if (TestUtil.findAll(em, Attributes.class).isEmpty()) {
            attributes = AttributesResourceIT.createUpdatedEntity(em);
            em.persist(attributes);
            em.flush();
        } else {
            attributes = TestUtil.findAll(em, Attributes.class).get(0);
        }
        userRelations.setAttribute(attributes);
        return userRelations;
    }

    @BeforeEach
    public void initTest() {
        userRelations = createEntity(em);
    }

    @Test
    @Transactional
    void createUserRelations() throws Exception {
        int databaseSizeBeforeCreate = userRelationsRepository.findAll().size();
        // Create the UserRelations
        restUserRelationsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRelations))
            )
            .andExpect(status().isCreated());

        // Validate the UserRelations in the database
        List<UserRelations> userRelationsList = userRelationsRepository.findAll();
        assertThat(userRelationsList).hasSize(databaseSizeBeforeCreate + 1);
        UserRelations testUserRelations = userRelationsList.get(userRelationsList.size() - 1);
        assertThat(testUserRelations.getReferenceType()).isEqualTo(DEFAULT_REFERENCE_TYPE);
        assertThat(testUserRelations.getReferenceId()).isEqualTo(DEFAULT_REFERENCE_ID);
        assertThat(testUserRelations.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testUserRelations.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testUserRelations.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testUserRelations.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testUserRelations.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createUserRelationsWithExistingId() throws Exception {
        // Create the UserRelations with an existing ID
        userRelations.setId(1L);

        int databaseSizeBeforeCreate = userRelationsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserRelationsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRelations))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserRelations in the database
        List<UserRelations> userRelationsList = userRelationsRepository.findAll();
        assertThat(userRelationsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEffDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = userRelationsRepository.findAll().size();
        // set the field null
        userRelations.setEffDate(null);

        // Create the UserRelations, which fails.

        restUserRelationsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRelations))
            )
            .andExpect(status().isBadRequest());

        List<UserRelations> userRelationsList = userRelationsRepository.findAll();
        assertThat(userRelationsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = userRelationsRepository.findAll().size();
        // set the field null
        userRelations.setCreatedAt(null);

        // Create the UserRelations, which fails.

        restUserRelationsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRelations))
            )
            .andExpect(status().isBadRequest());

        List<UserRelations> userRelationsList = userRelationsRepository.findAll();
        assertThat(userRelationsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = userRelationsRepository.findAll().size();
        // set the field null
        userRelations.setUpdatedAt(null);

        // Create the UserRelations, which fails.

        restUserRelationsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRelations))
            )
            .andExpect(status().isBadRequest());

        List<UserRelations> userRelationsList = userRelationsRepository.findAll();
        assertThat(userRelationsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = userRelationsRepository.findAll().size();
        // set the field null
        userRelations.setVersion(null);

        // Create the UserRelations, which fails.

        restUserRelationsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRelations))
            )
            .andExpect(status().isBadRequest());

        List<UserRelations> userRelationsList = userRelationsRepository.findAll();
        assertThat(userRelationsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUserRelations() throws Exception {
        // Initialize the database
        userRelationsRepository.saveAndFlush(userRelations);

        // Get all the userRelationsList
        restUserRelationsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userRelations.getId().intValue())))
            .andExpect(jsonPath("$.[*].referenceType").value(hasItem(DEFAULT_REFERENCE_TYPE)))
            .andExpect(jsonPath("$.[*].referenceId").value(hasItem(DEFAULT_REFERENCE_ID)))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getUserRelations() throws Exception {
        // Initialize the database
        userRelationsRepository.saveAndFlush(userRelations);

        // Get the userRelations
        restUserRelationsMockMvc
            .perform(get(ENTITY_API_URL_ID, userRelations.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userRelations.getId().intValue()))
            .andExpect(jsonPath("$.referenceType").value(DEFAULT_REFERENCE_TYPE))
            .andExpect(jsonPath("$.referenceId").value(DEFAULT_REFERENCE_ID))
            .andExpect(jsonPath("$.effDate").value(DEFAULT_EFF_DATE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getUserRelationsByIdFiltering() throws Exception {
        // Initialize the database
        userRelationsRepository.saveAndFlush(userRelations);

        Long id = userRelations.getId();

        defaultUserRelationsShouldBeFound("id.equals=" + id);
        defaultUserRelationsShouldNotBeFound("id.notEquals=" + id);

        defaultUserRelationsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUserRelationsShouldNotBeFound("id.greaterThan=" + id);

        defaultUserRelationsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUserRelationsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllUserRelationsByReferenceTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        userRelationsRepository.saveAndFlush(userRelations);

        // Get all the userRelationsList where referenceType equals to DEFAULT_REFERENCE_TYPE
        defaultUserRelationsShouldBeFound("referenceType.equals=" + DEFAULT_REFERENCE_TYPE);

        // Get all the userRelationsList where referenceType equals to UPDATED_REFERENCE_TYPE
        defaultUserRelationsShouldNotBeFound("referenceType.equals=" + UPDATED_REFERENCE_TYPE);
    }

    @Test
    @Transactional
    void getAllUserRelationsByReferenceTypeIsInShouldWork() throws Exception {
        // Initialize the database
        userRelationsRepository.saveAndFlush(userRelations);

        // Get all the userRelationsList where referenceType in DEFAULT_REFERENCE_TYPE or UPDATED_REFERENCE_TYPE
        defaultUserRelationsShouldBeFound("referenceType.in=" + DEFAULT_REFERENCE_TYPE + "," + UPDATED_REFERENCE_TYPE);

        // Get all the userRelationsList where referenceType equals to UPDATED_REFERENCE_TYPE
        defaultUserRelationsShouldNotBeFound("referenceType.in=" + UPDATED_REFERENCE_TYPE);
    }

    @Test
    @Transactional
    void getAllUserRelationsByReferenceTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRelationsRepository.saveAndFlush(userRelations);

        // Get all the userRelationsList where referenceType is not null
        defaultUserRelationsShouldBeFound("referenceType.specified=true");

        // Get all the userRelationsList where referenceType is null
        defaultUserRelationsShouldNotBeFound("referenceType.specified=false");
    }

    @Test
    @Transactional
    void getAllUserRelationsByReferenceTypeContainsSomething() throws Exception {
        // Initialize the database
        userRelationsRepository.saveAndFlush(userRelations);

        // Get all the userRelationsList where referenceType contains DEFAULT_REFERENCE_TYPE
        defaultUserRelationsShouldBeFound("referenceType.contains=" + DEFAULT_REFERENCE_TYPE);

        // Get all the userRelationsList where referenceType contains UPDATED_REFERENCE_TYPE
        defaultUserRelationsShouldNotBeFound("referenceType.contains=" + UPDATED_REFERENCE_TYPE);
    }

    @Test
    @Transactional
    void getAllUserRelationsByReferenceTypeNotContainsSomething() throws Exception {
        // Initialize the database
        userRelationsRepository.saveAndFlush(userRelations);

        // Get all the userRelationsList where referenceType does not contain DEFAULT_REFERENCE_TYPE
        defaultUserRelationsShouldNotBeFound("referenceType.doesNotContain=" + DEFAULT_REFERENCE_TYPE);

        // Get all the userRelationsList where referenceType does not contain UPDATED_REFERENCE_TYPE
        defaultUserRelationsShouldBeFound("referenceType.doesNotContain=" + UPDATED_REFERENCE_TYPE);
    }

    @Test
    @Transactional
    void getAllUserRelationsByReferenceIdIsEqualToSomething() throws Exception {
        // Initialize the database
        userRelationsRepository.saveAndFlush(userRelations);

        // Get all the userRelationsList where referenceId equals to DEFAULT_REFERENCE_ID
        defaultUserRelationsShouldBeFound("referenceId.equals=" + DEFAULT_REFERENCE_ID);

        // Get all the userRelationsList where referenceId equals to UPDATED_REFERENCE_ID
        defaultUserRelationsShouldNotBeFound("referenceId.equals=" + UPDATED_REFERENCE_ID);
    }

    @Test
    @Transactional
    void getAllUserRelationsByReferenceIdIsInShouldWork() throws Exception {
        // Initialize the database
        userRelationsRepository.saveAndFlush(userRelations);

        // Get all the userRelationsList where referenceId in DEFAULT_REFERENCE_ID or UPDATED_REFERENCE_ID
        defaultUserRelationsShouldBeFound("referenceId.in=" + DEFAULT_REFERENCE_ID + "," + UPDATED_REFERENCE_ID);

        // Get all the userRelationsList where referenceId equals to UPDATED_REFERENCE_ID
        defaultUserRelationsShouldNotBeFound("referenceId.in=" + UPDATED_REFERENCE_ID);
    }

    @Test
    @Transactional
    void getAllUserRelationsByReferenceIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRelationsRepository.saveAndFlush(userRelations);

        // Get all the userRelationsList where referenceId is not null
        defaultUserRelationsShouldBeFound("referenceId.specified=true");

        // Get all the userRelationsList where referenceId is null
        defaultUserRelationsShouldNotBeFound("referenceId.specified=false");
    }

    @Test
    @Transactional
    void getAllUserRelationsByReferenceIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userRelationsRepository.saveAndFlush(userRelations);

        // Get all the userRelationsList where referenceId is greater than or equal to DEFAULT_REFERENCE_ID
        defaultUserRelationsShouldBeFound("referenceId.greaterThanOrEqual=" + DEFAULT_REFERENCE_ID);

        // Get all the userRelationsList where referenceId is greater than or equal to UPDATED_REFERENCE_ID
        defaultUserRelationsShouldNotBeFound("referenceId.greaterThanOrEqual=" + UPDATED_REFERENCE_ID);
    }

    @Test
    @Transactional
    void getAllUserRelationsByReferenceIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userRelationsRepository.saveAndFlush(userRelations);

        // Get all the userRelationsList where referenceId is less than or equal to DEFAULT_REFERENCE_ID
        defaultUserRelationsShouldBeFound("referenceId.lessThanOrEqual=" + DEFAULT_REFERENCE_ID);

        // Get all the userRelationsList where referenceId is less than or equal to SMALLER_REFERENCE_ID
        defaultUserRelationsShouldNotBeFound("referenceId.lessThanOrEqual=" + SMALLER_REFERENCE_ID);
    }

    @Test
    @Transactional
    void getAllUserRelationsByReferenceIdIsLessThanSomething() throws Exception {
        // Initialize the database
        userRelationsRepository.saveAndFlush(userRelations);

        // Get all the userRelationsList where referenceId is less than DEFAULT_REFERENCE_ID
        defaultUserRelationsShouldNotBeFound("referenceId.lessThan=" + DEFAULT_REFERENCE_ID);

        // Get all the userRelationsList where referenceId is less than UPDATED_REFERENCE_ID
        defaultUserRelationsShouldBeFound("referenceId.lessThan=" + UPDATED_REFERENCE_ID);
    }

    @Test
    @Transactional
    void getAllUserRelationsByReferenceIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userRelationsRepository.saveAndFlush(userRelations);

        // Get all the userRelationsList where referenceId is greater than DEFAULT_REFERENCE_ID
        defaultUserRelationsShouldNotBeFound("referenceId.greaterThan=" + DEFAULT_REFERENCE_ID);

        // Get all the userRelationsList where referenceId is greater than SMALLER_REFERENCE_ID
        defaultUserRelationsShouldBeFound("referenceId.greaterThan=" + SMALLER_REFERENCE_ID);
    }

    @Test
    @Transactional
    void getAllUserRelationsByEffDateIsEqualToSomething() throws Exception {
        // Initialize the database
        userRelationsRepository.saveAndFlush(userRelations);

        // Get all the userRelationsList where effDate equals to DEFAULT_EFF_DATE
        defaultUserRelationsShouldBeFound("effDate.equals=" + DEFAULT_EFF_DATE);

        // Get all the userRelationsList where effDate equals to UPDATED_EFF_DATE
        defaultUserRelationsShouldNotBeFound("effDate.equals=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllUserRelationsByEffDateIsInShouldWork() throws Exception {
        // Initialize the database
        userRelationsRepository.saveAndFlush(userRelations);

        // Get all the userRelationsList where effDate in DEFAULT_EFF_DATE or UPDATED_EFF_DATE
        defaultUserRelationsShouldBeFound("effDate.in=" + DEFAULT_EFF_DATE + "," + UPDATED_EFF_DATE);

        // Get all the userRelationsList where effDate equals to UPDATED_EFF_DATE
        defaultUserRelationsShouldNotBeFound("effDate.in=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllUserRelationsByEffDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRelationsRepository.saveAndFlush(userRelations);

        // Get all the userRelationsList where effDate is not null
        defaultUserRelationsShouldBeFound("effDate.specified=true");

        // Get all the userRelationsList where effDate is null
        defaultUserRelationsShouldNotBeFound("effDate.specified=false");
    }

    @Test
    @Transactional
    void getAllUserRelationsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        userRelationsRepository.saveAndFlush(userRelations);

        // Get all the userRelationsList where createdAt equals to DEFAULT_CREATED_AT
        defaultUserRelationsShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the userRelationsList where createdAt equals to UPDATED_CREATED_AT
        defaultUserRelationsShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllUserRelationsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        userRelationsRepository.saveAndFlush(userRelations);

        // Get all the userRelationsList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultUserRelationsShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the userRelationsList where createdAt equals to UPDATED_CREATED_AT
        defaultUserRelationsShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllUserRelationsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRelationsRepository.saveAndFlush(userRelations);

        // Get all the userRelationsList where createdAt is not null
        defaultUserRelationsShouldBeFound("createdAt.specified=true");

        // Get all the userRelationsList where createdAt is null
        defaultUserRelationsShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllUserRelationsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        userRelationsRepository.saveAndFlush(userRelations);

        // Get all the userRelationsList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultUserRelationsShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the userRelationsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultUserRelationsShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllUserRelationsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        userRelationsRepository.saveAndFlush(userRelations);

        // Get all the userRelationsList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultUserRelationsShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the userRelationsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultUserRelationsShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllUserRelationsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRelationsRepository.saveAndFlush(userRelations);

        // Get all the userRelationsList where updatedAt is not null
        defaultUserRelationsShouldBeFound("updatedAt.specified=true");

        // Get all the userRelationsList where updatedAt is null
        defaultUserRelationsShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllUserRelationsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        userRelationsRepository.saveAndFlush(userRelations);

        // Get all the userRelationsList where endDate equals to DEFAULT_END_DATE
        defaultUserRelationsShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the userRelationsList where endDate equals to UPDATED_END_DATE
        defaultUserRelationsShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllUserRelationsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        userRelationsRepository.saveAndFlush(userRelations);

        // Get all the userRelationsList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultUserRelationsShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the userRelationsList where endDate equals to UPDATED_END_DATE
        defaultUserRelationsShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllUserRelationsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRelationsRepository.saveAndFlush(userRelations);

        // Get all the userRelationsList where endDate is not null
        defaultUserRelationsShouldBeFound("endDate.specified=true");

        // Get all the userRelationsList where endDate is null
        defaultUserRelationsShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllUserRelationsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        userRelationsRepository.saveAndFlush(userRelations);

        // Get all the userRelationsList where version equals to DEFAULT_VERSION
        defaultUserRelationsShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the userRelationsList where version equals to UPDATED_VERSION
        defaultUserRelationsShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllUserRelationsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        userRelationsRepository.saveAndFlush(userRelations);

        // Get all the userRelationsList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultUserRelationsShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the userRelationsList where version equals to UPDATED_VERSION
        defaultUserRelationsShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllUserRelationsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRelationsRepository.saveAndFlush(userRelations);

        // Get all the userRelationsList where version is not null
        defaultUserRelationsShouldBeFound("version.specified=true");

        // Get all the userRelationsList where version is null
        defaultUserRelationsShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllUserRelationsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userRelationsRepository.saveAndFlush(userRelations);

        // Get all the userRelationsList where version is greater than or equal to DEFAULT_VERSION
        defaultUserRelationsShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the userRelationsList where version is greater than or equal to UPDATED_VERSION
        defaultUserRelationsShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllUserRelationsByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userRelationsRepository.saveAndFlush(userRelations);

        // Get all the userRelationsList where version is less than or equal to DEFAULT_VERSION
        defaultUserRelationsShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the userRelationsList where version is less than or equal to SMALLER_VERSION
        defaultUserRelationsShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllUserRelationsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        userRelationsRepository.saveAndFlush(userRelations);

        // Get all the userRelationsList where version is less than DEFAULT_VERSION
        defaultUserRelationsShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the userRelationsList where version is less than UPDATED_VERSION
        defaultUserRelationsShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllUserRelationsByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userRelationsRepository.saveAndFlush(userRelations);

        // Get all the userRelationsList where version is greater than DEFAULT_VERSION
        defaultUserRelationsShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the userRelationsList where version is greater than SMALLER_VERSION
        defaultUserRelationsShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllUserRelationsByUserIsEqualToSomething() throws Exception {
        Employees user;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            userRelationsRepository.saveAndFlush(userRelations);
            user = EmployeesResourceIT.createEntity(em);
        } else {
            user = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(user);
        em.flush();
        userRelations.setUser(user);
        userRelationsRepository.saveAndFlush(userRelations);
        Long userId = user.getId();

        // Get all the userRelationsList where user equals to userId
        defaultUserRelationsShouldBeFound("userId.equals=" + userId);

        // Get all the userRelationsList where user equals to (userId + 1)
        defaultUserRelationsShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    @Test
    @Transactional
    void getAllUserRelationsByAttributeIsEqualToSomething() throws Exception {
        Attributes attribute;
        if (TestUtil.findAll(em, Attributes.class).isEmpty()) {
            userRelationsRepository.saveAndFlush(userRelations);
            attribute = AttributesResourceIT.createEntity(em);
        } else {
            attribute = TestUtil.findAll(em, Attributes.class).get(0);
        }
        em.persist(attribute);
        em.flush();
        userRelations.setAttribute(attribute);
        userRelationsRepository.saveAndFlush(userRelations);
        Long attributeId = attribute.getId();

        // Get all the userRelationsList where attribute equals to attributeId
        defaultUserRelationsShouldBeFound("attributeId.equals=" + attributeId);

        // Get all the userRelationsList where attribute equals to (attributeId + 1)
        defaultUserRelationsShouldNotBeFound("attributeId.equals=" + (attributeId + 1));
    }

    @Test
    @Transactional
    void getAllUserRelationsByRelatedUserIsEqualToSomething() throws Exception {
        Employees relatedUser;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            userRelationsRepository.saveAndFlush(userRelations);
            relatedUser = EmployeesResourceIT.createEntity(em);
        } else {
            relatedUser = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(relatedUser);
        em.flush();
        userRelations.setRelatedUser(relatedUser);
        userRelationsRepository.saveAndFlush(userRelations);
        Long relatedUserId = relatedUser.getId();

        // Get all the userRelationsList where relatedUser equals to relatedUserId
        defaultUserRelationsShouldBeFound("relatedUserId.equals=" + relatedUserId);

        // Get all the userRelationsList where relatedUser equals to (relatedUserId + 1)
        defaultUserRelationsShouldNotBeFound("relatedUserId.equals=" + (relatedUserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserRelationsShouldBeFound(String filter) throws Exception {
        restUserRelationsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userRelations.getId().intValue())))
            .andExpect(jsonPath("$.[*].referenceType").value(hasItem(DEFAULT_REFERENCE_TYPE)))
            .andExpect(jsonPath("$.[*].referenceId").value(hasItem(DEFAULT_REFERENCE_ID)))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restUserRelationsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUserRelationsShouldNotBeFound(String filter) throws Exception {
        restUserRelationsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserRelationsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingUserRelations() throws Exception {
        // Get the userRelations
        restUserRelationsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUserRelations() throws Exception {
        // Initialize the database
        userRelationsRepository.saveAndFlush(userRelations);

        int databaseSizeBeforeUpdate = userRelationsRepository.findAll().size();

        // Update the userRelations
        UserRelations updatedUserRelations = userRelationsRepository.findById(userRelations.getId()).get();
        // Disconnect from session so that the updates on updatedUserRelations are not directly saved in db
        em.detach(updatedUserRelations);
        updatedUserRelations
            .referenceType(UPDATED_REFERENCE_TYPE)
            .referenceId(UPDATED_REFERENCE_ID)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restUserRelationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUserRelations.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUserRelations))
            )
            .andExpect(status().isOk());

        // Validate the UserRelations in the database
        List<UserRelations> userRelationsList = userRelationsRepository.findAll();
        assertThat(userRelationsList).hasSize(databaseSizeBeforeUpdate);
        UserRelations testUserRelations = userRelationsList.get(userRelationsList.size() - 1);
        assertThat(testUserRelations.getReferenceType()).isEqualTo(UPDATED_REFERENCE_TYPE);
        assertThat(testUserRelations.getReferenceId()).isEqualTo(UPDATED_REFERENCE_ID);
        assertThat(testUserRelations.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testUserRelations.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testUserRelations.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testUserRelations.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testUserRelations.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingUserRelations() throws Exception {
        int databaseSizeBeforeUpdate = userRelationsRepository.findAll().size();
        userRelations.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserRelationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userRelations.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRelations))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserRelations in the database
        List<UserRelations> userRelationsList = userRelationsRepository.findAll();
        assertThat(userRelationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserRelations() throws Exception {
        int databaseSizeBeforeUpdate = userRelationsRepository.findAll().size();
        userRelations.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserRelationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRelations))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserRelations in the database
        List<UserRelations> userRelationsList = userRelationsRepository.findAll();
        assertThat(userRelationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserRelations() throws Exception {
        int databaseSizeBeforeUpdate = userRelationsRepository.findAll().size();
        userRelations.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserRelationsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRelations))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserRelations in the database
        List<UserRelations> userRelationsList = userRelationsRepository.findAll();
        assertThat(userRelationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserRelationsWithPatch() throws Exception {
        // Initialize the database
        userRelationsRepository.saveAndFlush(userRelations);

        int databaseSizeBeforeUpdate = userRelationsRepository.findAll().size();

        // Update the userRelations using partial update
        UserRelations partialUpdatedUserRelations = new UserRelations();
        partialUpdatedUserRelations.setId(userRelations.getId());

        partialUpdatedUserRelations
            .referenceId(UPDATED_REFERENCE_ID)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restUserRelationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserRelations.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserRelations))
            )
            .andExpect(status().isOk());

        // Validate the UserRelations in the database
        List<UserRelations> userRelationsList = userRelationsRepository.findAll();
        assertThat(userRelationsList).hasSize(databaseSizeBeforeUpdate);
        UserRelations testUserRelations = userRelationsList.get(userRelationsList.size() - 1);
        assertThat(testUserRelations.getReferenceType()).isEqualTo(DEFAULT_REFERENCE_TYPE);
        assertThat(testUserRelations.getReferenceId()).isEqualTo(UPDATED_REFERENCE_ID);
        assertThat(testUserRelations.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testUserRelations.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testUserRelations.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testUserRelations.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testUserRelations.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateUserRelationsWithPatch() throws Exception {
        // Initialize the database
        userRelationsRepository.saveAndFlush(userRelations);

        int databaseSizeBeforeUpdate = userRelationsRepository.findAll().size();

        // Update the userRelations using partial update
        UserRelations partialUpdatedUserRelations = new UserRelations();
        partialUpdatedUserRelations.setId(userRelations.getId());

        partialUpdatedUserRelations
            .referenceType(UPDATED_REFERENCE_TYPE)
            .referenceId(UPDATED_REFERENCE_ID)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restUserRelationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserRelations.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserRelations))
            )
            .andExpect(status().isOk());

        // Validate the UserRelations in the database
        List<UserRelations> userRelationsList = userRelationsRepository.findAll();
        assertThat(userRelationsList).hasSize(databaseSizeBeforeUpdate);
        UserRelations testUserRelations = userRelationsList.get(userRelationsList.size() - 1);
        assertThat(testUserRelations.getReferenceType()).isEqualTo(UPDATED_REFERENCE_TYPE);
        assertThat(testUserRelations.getReferenceId()).isEqualTo(UPDATED_REFERENCE_ID);
        assertThat(testUserRelations.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testUserRelations.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testUserRelations.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testUserRelations.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testUserRelations.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingUserRelations() throws Exception {
        int databaseSizeBeforeUpdate = userRelationsRepository.findAll().size();
        userRelations.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserRelationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userRelations.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userRelations))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserRelations in the database
        List<UserRelations> userRelationsList = userRelationsRepository.findAll();
        assertThat(userRelationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserRelations() throws Exception {
        int databaseSizeBeforeUpdate = userRelationsRepository.findAll().size();
        userRelations.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserRelationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userRelations))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserRelations in the database
        List<UserRelations> userRelationsList = userRelationsRepository.findAll();
        assertThat(userRelationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserRelations() throws Exception {
        int databaseSizeBeforeUpdate = userRelationsRepository.findAll().size();
        userRelations.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserRelationsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userRelations))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserRelations in the database
        List<UserRelations> userRelationsList = userRelationsRepository.findAll();
        assertThat(userRelationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserRelations() throws Exception {
        // Initialize the database
        userRelationsRepository.saveAndFlush(userRelations);

        int databaseSizeBeforeDelete = userRelationsRepository.findAll().size();

        // Delete the userRelations
        restUserRelationsMockMvc
            .perform(delete(ENTITY_API_URL_ID, userRelations.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserRelations> userRelationsList = userRelationsRepository.findAll();
        assertThat(userRelationsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
