package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.ApproverGroups;
import com.venturedive.vendian_mono.domain.Approvers;
import com.venturedive.vendian_mono.repository.ApproverGroupsRepository;
import com.venturedive.vendian_mono.service.criteria.ApproverGroupsCriteria;
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
 * Integration tests for the {@link ApproverGroupsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ApproverGroupsResourceIT {

    private static final Integer DEFAULT_REFERENCE_ID = 1;
    private static final Integer UPDATED_REFERENCE_ID = 2;
    private static final Integer SMALLER_REFERENCE_ID = 1 - 1;

    private static final String DEFAULT_REFERENCE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_TYPE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;
    private static final Integer SMALLER_VERSION = 1 - 1;

    private static final String ENTITY_API_URL = "/api/approver-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ApproverGroupsRepository approverGroupsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApproverGroupsMockMvc;

    private ApproverGroups approverGroups;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApproverGroups createEntity(EntityManager em) {
        ApproverGroups approverGroups = new ApproverGroups()
            .referenceId(DEFAULT_REFERENCE_ID)
            .referenceType(DEFAULT_REFERENCE_TYPE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT)
            .version(DEFAULT_VERSION);
        return approverGroups;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApproverGroups createUpdatedEntity(EntityManager em) {
        ApproverGroups approverGroups = new ApproverGroups()
            .referenceId(UPDATED_REFERENCE_ID)
            .referenceType(UPDATED_REFERENCE_TYPE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);
        return approverGroups;
    }

    @BeforeEach
    public void initTest() {
        approverGroups = createEntity(em);
    }

    @Test
    @Transactional
    void createApproverGroups() throws Exception {
        int databaseSizeBeforeCreate = approverGroupsRepository.findAll().size();
        // Create the ApproverGroups
        restApproverGroupsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approverGroups))
            )
            .andExpect(status().isCreated());

        // Validate the ApproverGroups in the database
        List<ApproverGroups> approverGroupsList = approverGroupsRepository.findAll();
        assertThat(approverGroupsList).hasSize(databaseSizeBeforeCreate + 1);
        ApproverGroups testApproverGroups = approverGroupsList.get(approverGroupsList.size() - 1);
        assertThat(testApproverGroups.getReferenceId()).isEqualTo(DEFAULT_REFERENCE_ID);
        assertThat(testApproverGroups.getReferenceType()).isEqualTo(DEFAULT_REFERENCE_TYPE);
        assertThat(testApproverGroups.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testApproverGroups.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testApproverGroups.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testApproverGroups.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createApproverGroupsWithExistingId() throws Exception {
        // Create the ApproverGroups with an existing ID
        approverGroups.setId(1L);

        int databaseSizeBeforeCreate = approverGroupsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApproverGroupsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approverGroups))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApproverGroups in the database
        List<ApproverGroups> approverGroupsList = approverGroupsRepository.findAll();
        assertThat(approverGroupsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkReferenceTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = approverGroupsRepository.findAll().size();
        // set the field null
        approverGroups.setReferenceType(null);

        // Create the ApproverGroups, which fails.

        restApproverGroupsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approverGroups))
            )
            .andExpect(status().isBadRequest());

        List<ApproverGroups> approverGroupsList = approverGroupsRepository.findAll();
        assertThat(approverGroupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = approverGroupsRepository.findAll().size();
        // set the field null
        approverGroups.setCreatedAt(null);

        // Create the ApproverGroups, which fails.

        restApproverGroupsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approverGroups))
            )
            .andExpect(status().isBadRequest());

        List<ApproverGroups> approverGroupsList = approverGroupsRepository.findAll();
        assertThat(approverGroupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = approverGroupsRepository.findAll().size();
        // set the field null
        approverGroups.setUpdatedAt(null);

        // Create the ApproverGroups, which fails.

        restApproverGroupsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approverGroups))
            )
            .andExpect(status().isBadRequest());

        List<ApproverGroups> approverGroupsList = approverGroupsRepository.findAll();
        assertThat(approverGroupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = approverGroupsRepository.findAll().size();
        // set the field null
        approverGroups.setVersion(null);

        // Create the ApproverGroups, which fails.

        restApproverGroupsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approverGroups))
            )
            .andExpect(status().isBadRequest());

        List<ApproverGroups> approverGroupsList = approverGroupsRepository.findAll();
        assertThat(approverGroupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllApproverGroups() throws Exception {
        // Initialize the database
        approverGroupsRepository.saveAndFlush(approverGroups);

        // Get all the approverGroupsList
        restApproverGroupsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(approverGroups.getId().intValue())))
            .andExpect(jsonPath("$.[*].referenceId").value(hasItem(DEFAULT_REFERENCE_ID)))
            .andExpect(jsonPath("$.[*].referenceType").value(hasItem(DEFAULT_REFERENCE_TYPE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getApproverGroups() throws Exception {
        // Initialize the database
        approverGroupsRepository.saveAndFlush(approverGroups);

        // Get the approverGroups
        restApproverGroupsMockMvc
            .perform(get(ENTITY_API_URL_ID, approverGroups.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(approverGroups.getId().intValue()))
            .andExpect(jsonPath("$.referenceId").value(DEFAULT_REFERENCE_ID))
            .andExpect(jsonPath("$.referenceType").value(DEFAULT_REFERENCE_TYPE))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getApproverGroupsByIdFiltering() throws Exception {
        // Initialize the database
        approverGroupsRepository.saveAndFlush(approverGroups);

        Long id = approverGroups.getId();

        defaultApproverGroupsShouldBeFound("id.equals=" + id);
        defaultApproverGroupsShouldNotBeFound("id.notEquals=" + id);

        defaultApproverGroupsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultApproverGroupsShouldNotBeFound("id.greaterThan=" + id);

        defaultApproverGroupsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultApproverGroupsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllApproverGroupsByReferenceIdIsEqualToSomething() throws Exception {
        // Initialize the database
        approverGroupsRepository.saveAndFlush(approverGroups);

        // Get all the approverGroupsList where referenceId equals to DEFAULT_REFERENCE_ID
        defaultApproverGroupsShouldBeFound("referenceId.equals=" + DEFAULT_REFERENCE_ID);

        // Get all the approverGroupsList where referenceId equals to UPDATED_REFERENCE_ID
        defaultApproverGroupsShouldNotBeFound("referenceId.equals=" + UPDATED_REFERENCE_ID);
    }

    @Test
    @Transactional
    void getAllApproverGroupsByReferenceIdIsInShouldWork() throws Exception {
        // Initialize the database
        approverGroupsRepository.saveAndFlush(approverGroups);

        // Get all the approverGroupsList where referenceId in DEFAULT_REFERENCE_ID or UPDATED_REFERENCE_ID
        defaultApproverGroupsShouldBeFound("referenceId.in=" + DEFAULT_REFERENCE_ID + "," + UPDATED_REFERENCE_ID);

        // Get all the approverGroupsList where referenceId equals to UPDATED_REFERENCE_ID
        defaultApproverGroupsShouldNotBeFound("referenceId.in=" + UPDATED_REFERENCE_ID);
    }

    @Test
    @Transactional
    void getAllApproverGroupsByReferenceIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        approverGroupsRepository.saveAndFlush(approverGroups);

        // Get all the approverGroupsList where referenceId is not null
        defaultApproverGroupsShouldBeFound("referenceId.specified=true");

        // Get all the approverGroupsList where referenceId is null
        defaultApproverGroupsShouldNotBeFound("referenceId.specified=false");
    }

    @Test
    @Transactional
    void getAllApproverGroupsByReferenceIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        approverGroupsRepository.saveAndFlush(approverGroups);

        // Get all the approverGroupsList where referenceId is greater than or equal to DEFAULT_REFERENCE_ID
        defaultApproverGroupsShouldBeFound("referenceId.greaterThanOrEqual=" + DEFAULT_REFERENCE_ID);

        // Get all the approverGroupsList where referenceId is greater than or equal to UPDATED_REFERENCE_ID
        defaultApproverGroupsShouldNotBeFound("referenceId.greaterThanOrEqual=" + UPDATED_REFERENCE_ID);
    }

    @Test
    @Transactional
    void getAllApproverGroupsByReferenceIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        approverGroupsRepository.saveAndFlush(approverGroups);

        // Get all the approverGroupsList where referenceId is less than or equal to DEFAULT_REFERENCE_ID
        defaultApproverGroupsShouldBeFound("referenceId.lessThanOrEqual=" + DEFAULT_REFERENCE_ID);

        // Get all the approverGroupsList where referenceId is less than or equal to SMALLER_REFERENCE_ID
        defaultApproverGroupsShouldNotBeFound("referenceId.lessThanOrEqual=" + SMALLER_REFERENCE_ID);
    }

    @Test
    @Transactional
    void getAllApproverGroupsByReferenceIdIsLessThanSomething() throws Exception {
        // Initialize the database
        approverGroupsRepository.saveAndFlush(approverGroups);

        // Get all the approverGroupsList where referenceId is less than DEFAULT_REFERENCE_ID
        defaultApproverGroupsShouldNotBeFound("referenceId.lessThan=" + DEFAULT_REFERENCE_ID);

        // Get all the approverGroupsList where referenceId is less than UPDATED_REFERENCE_ID
        defaultApproverGroupsShouldBeFound("referenceId.lessThan=" + UPDATED_REFERENCE_ID);
    }

    @Test
    @Transactional
    void getAllApproverGroupsByReferenceIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        approverGroupsRepository.saveAndFlush(approverGroups);

        // Get all the approverGroupsList where referenceId is greater than DEFAULT_REFERENCE_ID
        defaultApproverGroupsShouldNotBeFound("referenceId.greaterThan=" + DEFAULT_REFERENCE_ID);

        // Get all the approverGroupsList where referenceId is greater than SMALLER_REFERENCE_ID
        defaultApproverGroupsShouldBeFound("referenceId.greaterThan=" + SMALLER_REFERENCE_ID);
    }

    @Test
    @Transactional
    void getAllApproverGroupsByReferenceTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        approverGroupsRepository.saveAndFlush(approverGroups);

        // Get all the approverGroupsList where referenceType equals to DEFAULT_REFERENCE_TYPE
        defaultApproverGroupsShouldBeFound("referenceType.equals=" + DEFAULT_REFERENCE_TYPE);

        // Get all the approverGroupsList where referenceType equals to UPDATED_REFERENCE_TYPE
        defaultApproverGroupsShouldNotBeFound("referenceType.equals=" + UPDATED_REFERENCE_TYPE);
    }

    @Test
    @Transactional
    void getAllApproverGroupsByReferenceTypeIsInShouldWork() throws Exception {
        // Initialize the database
        approverGroupsRepository.saveAndFlush(approverGroups);

        // Get all the approverGroupsList where referenceType in DEFAULT_REFERENCE_TYPE or UPDATED_REFERENCE_TYPE
        defaultApproverGroupsShouldBeFound("referenceType.in=" + DEFAULT_REFERENCE_TYPE + "," + UPDATED_REFERENCE_TYPE);

        // Get all the approverGroupsList where referenceType equals to UPDATED_REFERENCE_TYPE
        defaultApproverGroupsShouldNotBeFound("referenceType.in=" + UPDATED_REFERENCE_TYPE);
    }

    @Test
    @Transactional
    void getAllApproverGroupsByReferenceTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        approverGroupsRepository.saveAndFlush(approverGroups);

        // Get all the approverGroupsList where referenceType is not null
        defaultApproverGroupsShouldBeFound("referenceType.specified=true");

        // Get all the approverGroupsList where referenceType is null
        defaultApproverGroupsShouldNotBeFound("referenceType.specified=false");
    }

    @Test
    @Transactional
    void getAllApproverGroupsByReferenceTypeContainsSomething() throws Exception {
        // Initialize the database
        approverGroupsRepository.saveAndFlush(approverGroups);

        // Get all the approverGroupsList where referenceType contains DEFAULT_REFERENCE_TYPE
        defaultApproverGroupsShouldBeFound("referenceType.contains=" + DEFAULT_REFERENCE_TYPE);

        // Get all the approverGroupsList where referenceType contains UPDATED_REFERENCE_TYPE
        defaultApproverGroupsShouldNotBeFound("referenceType.contains=" + UPDATED_REFERENCE_TYPE);
    }

    @Test
    @Transactional
    void getAllApproverGroupsByReferenceTypeNotContainsSomething() throws Exception {
        // Initialize the database
        approverGroupsRepository.saveAndFlush(approverGroups);

        // Get all the approverGroupsList where referenceType does not contain DEFAULT_REFERENCE_TYPE
        defaultApproverGroupsShouldNotBeFound("referenceType.doesNotContain=" + DEFAULT_REFERENCE_TYPE);

        // Get all the approverGroupsList where referenceType does not contain UPDATED_REFERENCE_TYPE
        defaultApproverGroupsShouldBeFound("referenceType.doesNotContain=" + UPDATED_REFERENCE_TYPE);
    }

    @Test
    @Transactional
    void getAllApproverGroupsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        approverGroupsRepository.saveAndFlush(approverGroups);

        // Get all the approverGroupsList where createdAt equals to DEFAULT_CREATED_AT
        defaultApproverGroupsShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the approverGroupsList where createdAt equals to UPDATED_CREATED_AT
        defaultApproverGroupsShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllApproverGroupsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        approverGroupsRepository.saveAndFlush(approverGroups);

        // Get all the approverGroupsList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultApproverGroupsShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the approverGroupsList where createdAt equals to UPDATED_CREATED_AT
        defaultApproverGroupsShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllApproverGroupsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        approverGroupsRepository.saveAndFlush(approverGroups);

        // Get all the approverGroupsList where createdAt is not null
        defaultApproverGroupsShouldBeFound("createdAt.specified=true");

        // Get all the approverGroupsList where createdAt is null
        defaultApproverGroupsShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllApproverGroupsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        approverGroupsRepository.saveAndFlush(approverGroups);

        // Get all the approverGroupsList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultApproverGroupsShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the approverGroupsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultApproverGroupsShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllApproverGroupsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        approverGroupsRepository.saveAndFlush(approverGroups);

        // Get all the approverGroupsList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultApproverGroupsShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the approverGroupsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultApproverGroupsShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllApproverGroupsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        approverGroupsRepository.saveAndFlush(approverGroups);

        // Get all the approverGroupsList where updatedAt is not null
        defaultApproverGroupsShouldBeFound("updatedAt.specified=true");

        // Get all the approverGroupsList where updatedAt is null
        defaultApproverGroupsShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllApproverGroupsByDeletedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        approverGroupsRepository.saveAndFlush(approverGroups);

        // Get all the approverGroupsList where deletedAt equals to DEFAULT_DELETED_AT
        defaultApproverGroupsShouldBeFound("deletedAt.equals=" + DEFAULT_DELETED_AT);

        // Get all the approverGroupsList where deletedAt equals to UPDATED_DELETED_AT
        defaultApproverGroupsShouldNotBeFound("deletedAt.equals=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllApproverGroupsByDeletedAtIsInShouldWork() throws Exception {
        // Initialize the database
        approverGroupsRepository.saveAndFlush(approverGroups);

        // Get all the approverGroupsList where deletedAt in DEFAULT_DELETED_AT or UPDATED_DELETED_AT
        defaultApproverGroupsShouldBeFound("deletedAt.in=" + DEFAULT_DELETED_AT + "," + UPDATED_DELETED_AT);

        // Get all the approverGroupsList where deletedAt equals to UPDATED_DELETED_AT
        defaultApproverGroupsShouldNotBeFound("deletedAt.in=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllApproverGroupsByDeletedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        approverGroupsRepository.saveAndFlush(approverGroups);

        // Get all the approverGroupsList where deletedAt is not null
        defaultApproverGroupsShouldBeFound("deletedAt.specified=true");

        // Get all the approverGroupsList where deletedAt is null
        defaultApproverGroupsShouldNotBeFound("deletedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllApproverGroupsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        approverGroupsRepository.saveAndFlush(approverGroups);

        // Get all the approverGroupsList where version equals to DEFAULT_VERSION
        defaultApproverGroupsShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the approverGroupsList where version equals to UPDATED_VERSION
        defaultApproverGroupsShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllApproverGroupsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        approverGroupsRepository.saveAndFlush(approverGroups);

        // Get all the approverGroupsList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultApproverGroupsShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the approverGroupsList where version equals to UPDATED_VERSION
        defaultApproverGroupsShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllApproverGroupsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        approverGroupsRepository.saveAndFlush(approverGroups);

        // Get all the approverGroupsList where version is not null
        defaultApproverGroupsShouldBeFound("version.specified=true");

        // Get all the approverGroupsList where version is null
        defaultApproverGroupsShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllApproverGroupsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        approverGroupsRepository.saveAndFlush(approverGroups);

        // Get all the approverGroupsList where version is greater than or equal to DEFAULT_VERSION
        defaultApproverGroupsShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the approverGroupsList where version is greater than or equal to UPDATED_VERSION
        defaultApproverGroupsShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllApproverGroupsByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        approverGroupsRepository.saveAndFlush(approverGroups);

        // Get all the approverGroupsList where version is less than or equal to DEFAULT_VERSION
        defaultApproverGroupsShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the approverGroupsList where version is less than or equal to SMALLER_VERSION
        defaultApproverGroupsShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllApproverGroupsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        approverGroupsRepository.saveAndFlush(approverGroups);

        // Get all the approverGroupsList where version is less than DEFAULT_VERSION
        defaultApproverGroupsShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the approverGroupsList where version is less than UPDATED_VERSION
        defaultApproverGroupsShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllApproverGroupsByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        approverGroupsRepository.saveAndFlush(approverGroups);

        // Get all the approverGroupsList where version is greater than DEFAULT_VERSION
        defaultApproverGroupsShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the approverGroupsList where version is greater than SMALLER_VERSION
        defaultApproverGroupsShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllApproverGroupsByApproversApprovergroupIsEqualToSomething() throws Exception {
        Approvers approversApprovergroup;
        if (TestUtil.findAll(em, Approvers.class).isEmpty()) {
            approverGroupsRepository.saveAndFlush(approverGroups);
            approversApprovergroup = ApproversResourceIT.createEntity(em);
        } else {
            approversApprovergroup = TestUtil.findAll(em, Approvers.class).get(0);
        }
        em.persist(approversApprovergroup);
        em.flush();
        approverGroups.addApproversApprovergroup(approversApprovergroup);
        approverGroupsRepository.saveAndFlush(approverGroups);
        Long approversApprovergroupId = approversApprovergroup.getId();

        // Get all the approverGroupsList where approversApprovergroup equals to approversApprovergroupId
        defaultApproverGroupsShouldBeFound("approversApprovergroupId.equals=" + approversApprovergroupId);

        // Get all the approverGroupsList where approversApprovergroup equals to (approversApprovergroupId + 1)
        defaultApproverGroupsShouldNotBeFound("approversApprovergroupId.equals=" + (approversApprovergroupId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultApproverGroupsShouldBeFound(String filter) throws Exception {
        restApproverGroupsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(approverGroups.getId().intValue())))
            .andExpect(jsonPath("$.[*].referenceId").value(hasItem(DEFAULT_REFERENCE_ID)))
            .andExpect(jsonPath("$.[*].referenceType").value(hasItem(DEFAULT_REFERENCE_TYPE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restApproverGroupsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultApproverGroupsShouldNotBeFound(String filter) throws Exception {
        restApproverGroupsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApproverGroupsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingApproverGroups() throws Exception {
        // Get the approverGroups
        restApproverGroupsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingApproverGroups() throws Exception {
        // Initialize the database
        approverGroupsRepository.saveAndFlush(approverGroups);

        int databaseSizeBeforeUpdate = approverGroupsRepository.findAll().size();

        // Update the approverGroups
        ApproverGroups updatedApproverGroups = approverGroupsRepository.findById(approverGroups.getId()).get();
        // Disconnect from session so that the updates on updatedApproverGroups are not directly saved in db
        em.detach(updatedApproverGroups);
        updatedApproverGroups
            .referenceId(UPDATED_REFERENCE_ID)
            .referenceType(UPDATED_REFERENCE_TYPE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restApproverGroupsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedApproverGroups.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedApproverGroups))
            )
            .andExpect(status().isOk());

        // Validate the ApproverGroups in the database
        List<ApproverGroups> approverGroupsList = approverGroupsRepository.findAll();
        assertThat(approverGroupsList).hasSize(databaseSizeBeforeUpdate);
        ApproverGroups testApproverGroups = approverGroupsList.get(approverGroupsList.size() - 1);
        assertThat(testApproverGroups.getReferenceId()).isEqualTo(UPDATED_REFERENCE_ID);
        assertThat(testApproverGroups.getReferenceType()).isEqualTo(UPDATED_REFERENCE_TYPE);
        assertThat(testApproverGroups.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testApproverGroups.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testApproverGroups.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testApproverGroups.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingApproverGroups() throws Exception {
        int databaseSizeBeforeUpdate = approverGroupsRepository.findAll().size();
        approverGroups.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApproverGroupsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, approverGroups.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approverGroups))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApproverGroups in the database
        List<ApproverGroups> approverGroupsList = approverGroupsRepository.findAll();
        assertThat(approverGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApproverGroups() throws Exception {
        int databaseSizeBeforeUpdate = approverGroupsRepository.findAll().size();
        approverGroups.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApproverGroupsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approverGroups))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApproverGroups in the database
        List<ApproverGroups> approverGroupsList = approverGroupsRepository.findAll();
        assertThat(approverGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApproverGroups() throws Exception {
        int databaseSizeBeforeUpdate = approverGroupsRepository.findAll().size();
        approverGroups.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApproverGroupsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approverGroups))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApproverGroups in the database
        List<ApproverGroups> approverGroupsList = approverGroupsRepository.findAll();
        assertThat(approverGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApproverGroupsWithPatch() throws Exception {
        // Initialize the database
        approverGroupsRepository.saveAndFlush(approverGroups);

        int databaseSizeBeforeUpdate = approverGroupsRepository.findAll().size();

        // Update the approverGroups using partial update
        ApproverGroups partialUpdatedApproverGroups = new ApproverGroups();
        partialUpdatedApproverGroups.setId(approverGroups.getId());

        partialUpdatedApproverGroups
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restApproverGroupsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApproverGroups.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApproverGroups))
            )
            .andExpect(status().isOk());

        // Validate the ApproverGroups in the database
        List<ApproverGroups> approverGroupsList = approverGroupsRepository.findAll();
        assertThat(approverGroupsList).hasSize(databaseSizeBeforeUpdate);
        ApproverGroups testApproverGroups = approverGroupsList.get(approverGroupsList.size() - 1);
        assertThat(testApproverGroups.getReferenceId()).isEqualTo(DEFAULT_REFERENCE_ID);
        assertThat(testApproverGroups.getReferenceType()).isEqualTo(DEFAULT_REFERENCE_TYPE);
        assertThat(testApproverGroups.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testApproverGroups.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testApproverGroups.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testApproverGroups.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateApproverGroupsWithPatch() throws Exception {
        // Initialize the database
        approverGroupsRepository.saveAndFlush(approverGroups);

        int databaseSizeBeforeUpdate = approverGroupsRepository.findAll().size();

        // Update the approverGroups using partial update
        ApproverGroups partialUpdatedApproverGroups = new ApproverGroups();
        partialUpdatedApproverGroups.setId(approverGroups.getId());

        partialUpdatedApproverGroups
            .referenceId(UPDATED_REFERENCE_ID)
            .referenceType(UPDATED_REFERENCE_TYPE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restApproverGroupsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApproverGroups.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApproverGroups))
            )
            .andExpect(status().isOk());

        // Validate the ApproverGroups in the database
        List<ApproverGroups> approverGroupsList = approverGroupsRepository.findAll();
        assertThat(approverGroupsList).hasSize(databaseSizeBeforeUpdate);
        ApproverGroups testApproverGroups = approverGroupsList.get(approverGroupsList.size() - 1);
        assertThat(testApproverGroups.getReferenceId()).isEqualTo(UPDATED_REFERENCE_ID);
        assertThat(testApproverGroups.getReferenceType()).isEqualTo(UPDATED_REFERENCE_TYPE);
        assertThat(testApproverGroups.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testApproverGroups.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testApproverGroups.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testApproverGroups.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingApproverGroups() throws Exception {
        int databaseSizeBeforeUpdate = approverGroupsRepository.findAll().size();
        approverGroups.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApproverGroupsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, approverGroups.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(approverGroups))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApproverGroups in the database
        List<ApproverGroups> approverGroupsList = approverGroupsRepository.findAll();
        assertThat(approverGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApproverGroups() throws Exception {
        int databaseSizeBeforeUpdate = approverGroupsRepository.findAll().size();
        approverGroups.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApproverGroupsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(approverGroups))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApproverGroups in the database
        List<ApproverGroups> approverGroupsList = approverGroupsRepository.findAll();
        assertThat(approverGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApproverGroups() throws Exception {
        int databaseSizeBeforeUpdate = approverGroupsRepository.findAll().size();
        approverGroups.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApproverGroupsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(approverGroups))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApproverGroups in the database
        List<ApproverGroups> approverGroupsList = approverGroupsRepository.findAll();
        assertThat(approverGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApproverGroups() throws Exception {
        // Initialize the database
        approverGroupsRepository.saveAndFlush(approverGroups);

        int databaseSizeBeforeDelete = approverGroupsRepository.findAll().size();

        // Delete the approverGroups
        restApproverGroupsMockMvc
            .perform(delete(ENTITY_API_URL_ID, approverGroups.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApproverGroups> approverGroupsList = approverGroupsRepository.findAll();
        assertThat(approverGroupsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
