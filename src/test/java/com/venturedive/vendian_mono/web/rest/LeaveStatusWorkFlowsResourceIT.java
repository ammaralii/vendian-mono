package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.LeaveStatusWorkFlows;
import com.venturedive.vendian_mono.domain.LeaveStatuses;
import com.venturedive.vendian_mono.repository.LeaveStatusWorkFlowsRepository;
import com.venturedive.vendian_mono.service.criteria.LeaveStatusWorkFlowsCriteria;
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
 * Integration tests for the {@link LeaveStatusWorkFlowsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeaveStatusWorkFlowsResourceIT {

    private static final Boolean DEFAULT_IF_APPROVALS = false;
    private static final Boolean UPDATED_IF_APPROVALS = true;

    private static final Boolean DEFAULT_APPROVAL_REQUIRED = false;
    private static final Boolean UPDATED_APPROVAL_REQUIRED = true;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;
    private static final Integer SMALLER_VERSION = 1 - 1;

    private static final String ENTITY_API_URL = "/api/leave-status-work-flows";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LeaveStatusWorkFlowsRepository leaveStatusWorkFlowsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeaveStatusWorkFlowsMockMvc;

    private LeaveStatusWorkFlows leaveStatusWorkFlows;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveStatusWorkFlows createEntity(EntityManager em) {
        LeaveStatusWorkFlows leaveStatusWorkFlows = new LeaveStatusWorkFlows()
            .ifApprovals(DEFAULT_IF_APPROVALS)
            .approvalRequired(DEFAULT_APPROVAL_REQUIRED)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT)
            .version(DEFAULT_VERSION);
        // Add required entity
        LeaveStatuses leaveStatuses;
        if (TestUtil.findAll(em, LeaveStatuses.class).isEmpty()) {
            leaveStatuses = LeaveStatusesResourceIT.createEntity(em);
            em.persist(leaveStatuses);
            em.flush();
        } else {
            leaveStatuses = TestUtil.findAll(em, LeaveStatuses.class).get(0);
        }
        leaveStatusWorkFlows.setCurrentStatus(leaveStatuses);
        // Add required entity
        leaveStatusWorkFlows.setNextStatus(leaveStatuses);
        return leaveStatusWorkFlows;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveStatusWorkFlows createUpdatedEntity(EntityManager em) {
        LeaveStatusWorkFlows leaveStatusWorkFlows = new LeaveStatusWorkFlows()
            .ifApprovals(UPDATED_IF_APPROVALS)
            .approvalRequired(UPDATED_APPROVAL_REQUIRED)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);
        // Add required entity
        LeaveStatuses leaveStatuses;
        if (TestUtil.findAll(em, LeaveStatuses.class).isEmpty()) {
            leaveStatuses = LeaveStatusesResourceIT.createUpdatedEntity(em);
            em.persist(leaveStatuses);
            em.flush();
        } else {
            leaveStatuses = TestUtil.findAll(em, LeaveStatuses.class).get(0);
        }
        leaveStatusWorkFlows.setCurrentStatus(leaveStatuses);
        // Add required entity
        leaveStatusWorkFlows.setNextStatus(leaveStatuses);
        return leaveStatusWorkFlows;
    }

    @BeforeEach
    public void initTest() {
        leaveStatusWorkFlows = createEntity(em);
    }

    @Test
    @Transactional
    void createLeaveStatusWorkFlows() throws Exception {
        int databaseSizeBeforeCreate = leaveStatusWorkFlowsRepository.findAll().size();
        // Create the LeaveStatusWorkFlows
        restLeaveStatusWorkFlowsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveStatusWorkFlows))
            )
            .andExpect(status().isCreated());

        // Validate the LeaveStatusWorkFlows in the database
        List<LeaveStatusWorkFlows> leaveStatusWorkFlowsList = leaveStatusWorkFlowsRepository.findAll();
        assertThat(leaveStatusWorkFlowsList).hasSize(databaseSizeBeforeCreate + 1);
        LeaveStatusWorkFlows testLeaveStatusWorkFlows = leaveStatusWorkFlowsList.get(leaveStatusWorkFlowsList.size() - 1);
        assertThat(testLeaveStatusWorkFlows.getIfApprovals()).isEqualTo(DEFAULT_IF_APPROVALS);
        assertThat(testLeaveStatusWorkFlows.getApprovalRequired()).isEqualTo(DEFAULT_APPROVAL_REQUIRED);
        assertThat(testLeaveStatusWorkFlows.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testLeaveStatusWorkFlows.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testLeaveStatusWorkFlows.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testLeaveStatusWorkFlows.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createLeaveStatusWorkFlowsWithExistingId() throws Exception {
        // Create the LeaveStatusWorkFlows with an existing ID
        leaveStatusWorkFlows.setId(1L);

        int databaseSizeBeforeCreate = leaveStatusWorkFlowsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeaveStatusWorkFlowsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveStatusWorkFlows))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveStatusWorkFlows in the database
        List<LeaveStatusWorkFlows> leaveStatusWorkFlowsList = leaveStatusWorkFlowsRepository.findAll();
        assertThat(leaveStatusWorkFlowsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIfApprovalsIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveStatusWorkFlowsRepository.findAll().size();
        // set the field null
        leaveStatusWorkFlows.setIfApprovals(null);

        // Create the LeaveStatusWorkFlows, which fails.

        restLeaveStatusWorkFlowsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveStatusWorkFlows))
            )
            .andExpect(status().isBadRequest());

        List<LeaveStatusWorkFlows> leaveStatusWorkFlowsList = leaveStatusWorkFlowsRepository.findAll();
        assertThat(leaveStatusWorkFlowsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkApprovalRequiredIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveStatusWorkFlowsRepository.findAll().size();
        // set the field null
        leaveStatusWorkFlows.setApprovalRequired(null);

        // Create the LeaveStatusWorkFlows, which fails.

        restLeaveStatusWorkFlowsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveStatusWorkFlows))
            )
            .andExpect(status().isBadRequest());

        List<LeaveStatusWorkFlows> leaveStatusWorkFlowsList = leaveStatusWorkFlowsRepository.findAll();
        assertThat(leaveStatusWorkFlowsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveStatusWorkFlowsRepository.findAll().size();
        // set the field null
        leaveStatusWorkFlows.setCreatedAt(null);

        // Create the LeaveStatusWorkFlows, which fails.

        restLeaveStatusWorkFlowsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveStatusWorkFlows))
            )
            .andExpect(status().isBadRequest());

        List<LeaveStatusWorkFlows> leaveStatusWorkFlowsList = leaveStatusWorkFlowsRepository.findAll();
        assertThat(leaveStatusWorkFlowsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveStatusWorkFlowsRepository.findAll().size();
        // set the field null
        leaveStatusWorkFlows.setUpdatedAt(null);

        // Create the LeaveStatusWorkFlows, which fails.

        restLeaveStatusWorkFlowsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveStatusWorkFlows))
            )
            .andExpect(status().isBadRequest());

        List<LeaveStatusWorkFlows> leaveStatusWorkFlowsList = leaveStatusWorkFlowsRepository.findAll();
        assertThat(leaveStatusWorkFlowsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveStatusWorkFlowsRepository.findAll().size();
        // set the field null
        leaveStatusWorkFlows.setVersion(null);

        // Create the LeaveStatusWorkFlows, which fails.

        restLeaveStatusWorkFlowsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveStatusWorkFlows))
            )
            .andExpect(status().isBadRequest());

        List<LeaveStatusWorkFlows> leaveStatusWorkFlowsList = leaveStatusWorkFlowsRepository.findAll();
        assertThat(leaveStatusWorkFlowsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLeaveStatusWorkFlows() throws Exception {
        // Initialize the database
        leaveStatusWorkFlowsRepository.saveAndFlush(leaveStatusWorkFlows);

        // Get all the leaveStatusWorkFlowsList
        restLeaveStatusWorkFlowsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveStatusWorkFlows.getId().intValue())))
            .andExpect(jsonPath("$.[*].ifApprovals").value(hasItem(DEFAULT_IF_APPROVALS.booleanValue())))
            .andExpect(jsonPath("$.[*].approvalRequired").value(hasItem(DEFAULT_APPROVAL_REQUIRED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getLeaveStatusWorkFlows() throws Exception {
        // Initialize the database
        leaveStatusWorkFlowsRepository.saveAndFlush(leaveStatusWorkFlows);

        // Get the leaveStatusWorkFlows
        restLeaveStatusWorkFlowsMockMvc
            .perform(get(ENTITY_API_URL_ID, leaveStatusWorkFlows.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leaveStatusWorkFlows.getId().intValue()))
            .andExpect(jsonPath("$.ifApprovals").value(DEFAULT_IF_APPROVALS.booleanValue()))
            .andExpect(jsonPath("$.approvalRequired").value(DEFAULT_APPROVAL_REQUIRED.booleanValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getLeaveStatusWorkFlowsByIdFiltering() throws Exception {
        // Initialize the database
        leaveStatusWorkFlowsRepository.saveAndFlush(leaveStatusWorkFlows);

        Long id = leaveStatusWorkFlows.getId();

        defaultLeaveStatusWorkFlowsShouldBeFound("id.equals=" + id);
        defaultLeaveStatusWorkFlowsShouldNotBeFound("id.notEquals=" + id);

        defaultLeaveStatusWorkFlowsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLeaveStatusWorkFlowsShouldNotBeFound("id.greaterThan=" + id);

        defaultLeaveStatusWorkFlowsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLeaveStatusWorkFlowsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLeaveStatusWorkFlowsByIfApprovalsIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveStatusWorkFlowsRepository.saveAndFlush(leaveStatusWorkFlows);

        // Get all the leaveStatusWorkFlowsList where ifApprovals equals to DEFAULT_IF_APPROVALS
        defaultLeaveStatusWorkFlowsShouldBeFound("ifApprovals.equals=" + DEFAULT_IF_APPROVALS);

        // Get all the leaveStatusWorkFlowsList where ifApprovals equals to UPDATED_IF_APPROVALS
        defaultLeaveStatusWorkFlowsShouldNotBeFound("ifApprovals.equals=" + UPDATED_IF_APPROVALS);
    }

    @Test
    @Transactional
    void getAllLeaveStatusWorkFlowsByIfApprovalsIsInShouldWork() throws Exception {
        // Initialize the database
        leaveStatusWorkFlowsRepository.saveAndFlush(leaveStatusWorkFlows);

        // Get all the leaveStatusWorkFlowsList where ifApprovals in DEFAULT_IF_APPROVALS or UPDATED_IF_APPROVALS
        defaultLeaveStatusWorkFlowsShouldBeFound("ifApprovals.in=" + DEFAULT_IF_APPROVALS + "," + UPDATED_IF_APPROVALS);

        // Get all the leaveStatusWorkFlowsList where ifApprovals equals to UPDATED_IF_APPROVALS
        defaultLeaveStatusWorkFlowsShouldNotBeFound("ifApprovals.in=" + UPDATED_IF_APPROVALS);
    }

    @Test
    @Transactional
    void getAllLeaveStatusWorkFlowsByIfApprovalsIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveStatusWorkFlowsRepository.saveAndFlush(leaveStatusWorkFlows);

        // Get all the leaveStatusWorkFlowsList where ifApprovals is not null
        defaultLeaveStatusWorkFlowsShouldBeFound("ifApprovals.specified=true");

        // Get all the leaveStatusWorkFlowsList where ifApprovals is null
        defaultLeaveStatusWorkFlowsShouldNotBeFound("ifApprovals.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveStatusWorkFlowsByApprovalRequiredIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveStatusWorkFlowsRepository.saveAndFlush(leaveStatusWorkFlows);

        // Get all the leaveStatusWorkFlowsList where approvalRequired equals to DEFAULT_APPROVAL_REQUIRED
        defaultLeaveStatusWorkFlowsShouldBeFound("approvalRequired.equals=" + DEFAULT_APPROVAL_REQUIRED);

        // Get all the leaveStatusWorkFlowsList where approvalRequired equals to UPDATED_APPROVAL_REQUIRED
        defaultLeaveStatusWorkFlowsShouldNotBeFound("approvalRequired.equals=" + UPDATED_APPROVAL_REQUIRED);
    }

    @Test
    @Transactional
    void getAllLeaveStatusWorkFlowsByApprovalRequiredIsInShouldWork() throws Exception {
        // Initialize the database
        leaveStatusWorkFlowsRepository.saveAndFlush(leaveStatusWorkFlows);

        // Get all the leaveStatusWorkFlowsList where approvalRequired in DEFAULT_APPROVAL_REQUIRED or UPDATED_APPROVAL_REQUIRED
        defaultLeaveStatusWorkFlowsShouldBeFound("approvalRequired.in=" + DEFAULT_APPROVAL_REQUIRED + "," + UPDATED_APPROVAL_REQUIRED);

        // Get all the leaveStatusWorkFlowsList where approvalRequired equals to UPDATED_APPROVAL_REQUIRED
        defaultLeaveStatusWorkFlowsShouldNotBeFound("approvalRequired.in=" + UPDATED_APPROVAL_REQUIRED);
    }

    @Test
    @Transactional
    void getAllLeaveStatusWorkFlowsByApprovalRequiredIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveStatusWorkFlowsRepository.saveAndFlush(leaveStatusWorkFlows);

        // Get all the leaveStatusWorkFlowsList where approvalRequired is not null
        defaultLeaveStatusWorkFlowsShouldBeFound("approvalRequired.specified=true");

        // Get all the leaveStatusWorkFlowsList where approvalRequired is null
        defaultLeaveStatusWorkFlowsShouldNotBeFound("approvalRequired.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveStatusWorkFlowsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveStatusWorkFlowsRepository.saveAndFlush(leaveStatusWorkFlows);

        // Get all the leaveStatusWorkFlowsList where createdAt equals to DEFAULT_CREATED_AT
        defaultLeaveStatusWorkFlowsShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the leaveStatusWorkFlowsList where createdAt equals to UPDATED_CREATED_AT
        defaultLeaveStatusWorkFlowsShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveStatusWorkFlowsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leaveStatusWorkFlowsRepository.saveAndFlush(leaveStatusWorkFlows);

        // Get all the leaveStatusWorkFlowsList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultLeaveStatusWorkFlowsShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the leaveStatusWorkFlowsList where createdAt equals to UPDATED_CREATED_AT
        defaultLeaveStatusWorkFlowsShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveStatusWorkFlowsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveStatusWorkFlowsRepository.saveAndFlush(leaveStatusWorkFlows);

        // Get all the leaveStatusWorkFlowsList where createdAt is not null
        defaultLeaveStatusWorkFlowsShouldBeFound("createdAt.specified=true");

        // Get all the leaveStatusWorkFlowsList where createdAt is null
        defaultLeaveStatusWorkFlowsShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveStatusWorkFlowsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveStatusWorkFlowsRepository.saveAndFlush(leaveStatusWorkFlows);

        // Get all the leaveStatusWorkFlowsList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultLeaveStatusWorkFlowsShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the leaveStatusWorkFlowsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultLeaveStatusWorkFlowsShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveStatusWorkFlowsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leaveStatusWorkFlowsRepository.saveAndFlush(leaveStatusWorkFlows);

        // Get all the leaveStatusWorkFlowsList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultLeaveStatusWorkFlowsShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the leaveStatusWorkFlowsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultLeaveStatusWorkFlowsShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveStatusWorkFlowsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveStatusWorkFlowsRepository.saveAndFlush(leaveStatusWorkFlows);

        // Get all the leaveStatusWorkFlowsList where updatedAt is not null
        defaultLeaveStatusWorkFlowsShouldBeFound("updatedAt.specified=true");

        // Get all the leaveStatusWorkFlowsList where updatedAt is null
        defaultLeaveStatusWorkFlowsShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveStatusWorkFlowsByDeletedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveStatusWorkFlowsRepository.saveAndFlush(leaveStatusWorkFlows);

        // Get all the leaveStatusWorkFlowsList where deletedAt equals to DEFAULT_DELETED_AT
        defaultLeaveStatusWorkFlowsShouldBeFound("deletedAt.equals=" + DEFAULT_DELETED_AT);

        // Get all the leaveStatusWorkFlowsList where deletedAt equals to UPDATED_DELETED_AT
        defaultLeaveStatusWorkFlowsShouldNotBeFound("deletedAt.equals=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveStatusWorkFlowsByDeletedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leaveStatusWorkFlowsRepository.saveAndFlush(leaveStatusWorkFlows);

        // Get all the leaveStatusWorkFlowsList where deletedAt in DEFAULT_DELETED_AT or UPDATED_DELETED_AT
        defaultLeaveStatusWorkFlowsShouldBeFound("deletedAt.in=" + DEFAULT_DELETED_AT + "," + UPDATED_DELETED_AT);

        // Get all the leaveStatusWorkFlowsList where deletedAt equals to UPDATED_DELETED_AT
        defaultLeaveStatusWorkFlowsShouldNotBeFound("deletedAt.in=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveStatusWorkFlowsByDeletedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveStatusWorkFlowsRepository.saveAndFlush(leaveStatusWorkFlows);

        // Get all the leaveStatusWorkFlowsList where deletedAt is not null
        defaultLeaveStatusWorkFlowsShouldBeFound("deletedAt.specified=true");

        // Get all the leaveStatusWorkFlowsList where deletedAt is null
        defaultLeaveStatusWorkFlowsShouldNotBeFound("deletedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveStatusWorkFlowsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveStatusWorkFlowsRepository.saveAndFlush(leaveStatusWorkFlows);

        // Get all the leaveStatusWorkFlowsList where version equals to DEFAULT_VERSION
        defaultLeaveStatusWorkFlowsShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the leaveStatusWorkFlowsList where version equals to UPDATED_VERSION
        defaultLeaveStatusWorkFlowsShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveStatusWorkFlowsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        leaveStatusWorkFlowsRepository.saveAndFlush(leaveStatusWorkFlows);

        // Get all the leaveStatusWorkFlowsList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultLeaveStatusWorkFlowsShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the leaveStatusWorkFlowsList where version equals to UPDATED_VERSION
        defaultLeaveStatusWorkFlowsShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveStatusWorkFlowsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveStatusWorkFlowsRepository.saveAndFlush(leaveStatusWorkFlows);

        // Get all the leaveStatusWorkFlowsList where version is not null
        defaultLeaveStatusWorkFlowsShouldBeFound("version.specified=true");

        // Get all the leaveStatusWorkFlowsList where version is null
        defaultLeaveStatusWorkFlowsShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveStatusWorkFlowsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveStatusWorkFlowsRepository.saveAndFlush(leaveStatusWorkFlows);

        // Get all the leaveStatusWorkFlowsList where version is greater than or equal to DEFAULT_VERSION
        defaultLeaveStatusWorkFlowsShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the leaveStatusWorkFlowsList where version is greater than or equal to UPDATED_VERSION
        defaultLeaveStatusWorkFlowsShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveStatusWorkFlowsByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveStatusWorkFlowsRepository.saveAndFlush(leaveStatusWorkFlows);

        // Get all the leaveStatusWorkFlowsList where version is less than or equal to DEFAULT_VERSION
        defaultLeaveStatusWorkFlowsShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the leaveStatusWorkFlowsList where version is less than or equal to SMALLER_VERSION
        defaultLeaveStatusWorkFlowsShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveStatusWorkFlowsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveStatusWorkFlowsRepository.saveAndFlush(leaveStatusWorkFlows);

        // Get all the leaveStatusWorkFlowsList where version is less than DEFAULT_VERSION
        defaultLeaveStatusWorkFlowsShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the leaveStatusWorkFlowsList where version is less than UPDATED_VERSION
        defaultLeaveStatusWorkFlowsShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveStatusWorkFlowsByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveStatusWorkFlowsRepository.saveAndFlush(leaveStatusWorkFlows);

        // Get all the leaveStatusWorkFlowsList where version is greater than DEFAULT_VERSION
        defaultLeaveStatusWorkFlowsShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the leaveStatusWorkFlowsList where version is greater than SMALLER_VERSION
        defaultLeaveStatusWorkFlowsShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveStatusWorkFlowsByCurrentStatusIsEqualToSomething() throws Exception {
        LeaveStatuses currentStatus;
        if (TestUtil.findAll(em, LeaveStatuses.class).isEmpty()) {
            leaveStatusWorkFlowsRepository.saveAndFlush(leaveStatusWorkFlows);
            currentStatus = LeaveStatusesResourceIT.createEntity(em);
        } else {
            currentStatus = TestUtil.findAll(em, LeaveStatuses.class).get(0);
        }
        em.persist(currentStatus);
        em.flush();
        leaveStatusWorkFlows.setCurrentStatus(currentStatus);
        leaveStatusWorkFlowsRepository.saveAndFlush(leaveStatusWorkFlows);
        Long currentStatusId = currentStatus.getId();

        // Get all the leaveStatusWorkFlowsList where currentStatus equals to currentStatusId
        defaultLeaveStatusWorkFlowsShouldBeFound("currentStatusId.equals=" + currentStatusId);

        // Get all the leaveStatusWorkFlowsList where currentStatus equals to (currentStatusId + 1)
        defaultLeaveStatusWorkFlowsShouldNotBeFound("currentStatusId.equals=" + (currentStatusId + 1));
    }

    @Test
    @Transactional
    void getAllLeaveStatusWorkFlowsByNextStatusIsEqualToSomething() throws Exception {
        LeaveStatuses nextStatus;
        if (TestUtil.findAll(em, LeaveStatuses.class).isEmpty()) {
            leaveStatusWorkFlowsRepository.saveAndFlush(leaveStatusWorkFlows);
            nextStatus = LeaveStatusesResourceIT.createEntity(em);
        } else {
            nextStatus = TestUtil.findAll(em, LeaveStatuses.class).get(0);
        }
        em.persist(nextStatus);
        em.flush();
        leaveStatusWorkFlows.setNextStatus(nextStatus);
        leaveStatusWorkFlowsRepository.saveAndFlush(leaveStatusWorkFlows);
        Long nextStatusId = nextStatus.getId();

        // Get all the leaveStatusWorkFlowsList where nextStatus equals to nextStatusId
        defaultLeaveStatusWorkFlowsShouldBeFound("nextStatusId.equals=" + nextStatusId);

        // Get all the leaveStatusWorkFlowsList where nextStatus equals to (nextStatusId + 1)
        defaultLeaveStatusWorkFlowsShouldNotBeFound("nextStatusId.equals=" + (nextStatusId + 1));
    }

    @Test
    @Transactional
    void getAllLeaveStatusWorkFlowsBySkipToStatusIsEqualToSomething() throws Exception {
        LeaveStatuses skipToStatus;
        if (TestUtil.findAll(em, LeaveStatuses.class).isEmpty()) {
            leaveStatusWorkFlowsRepository.saveAndFlush(leaveStatusWorkFlows);
            skipToStatus = LeaveStatusesResourceIT.createEntity(em);
        } else {
            skipToStatus = TestUtil.findAll(em, LeaveStatuses.class).get(0);
        }
        em.persist(skipToStatus);
        em.flush();
        leaveStatusWorkFlows.setSkipToStatus(skipToStatus);
        leaveStatusWorkFlowsRepository.saveAndFlush(leaveStatusWorkFlows);
        Long skipToStatusId = skipToStatus.getId();

        // Get all the leaveStatusWorkFlowsList where skipToStatus equals to skipToStatusId
        defaultLeaveStatusWorkFlowsShouldBeFound("skipToStatusId.equals=" + skipToStatusId);

        // Get all the leaveStatusWorkFlowsList where skipToStatus equals to (skipToStatusId + 1)
        defaultLeaveStatusWorkFlowsShouldNotBeFound("skipToStatusId.equals=" + (skipToStatusId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLeaveStatusWorkFlowsShouldBeFound(String filter) throws Exception {
        restLeaveStatusWorkFlowsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveStatusWorkFlows.getId().intValue())))
            .andExpect(jsonPath("$.[*].ifApprovals").value(hasItem(DEFAULT_IF_APPROVALS.booleanValue())))
            .andExpect(jsonPath("$.[*].approvalRequired").value(hasItem(DEFAULT_APPROVAL_REQUIRED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restLeaveStatusWorkFlowsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLeaveStatusWorkFlowsShouldNotBeFound(String filter) throws Exception {
        restLeaveStatusWorkFlowsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLeaveStatusWorkFlowsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLeaveStatusWorkFlows() throws Exception {
        // Get the leaveStatusWorkFlows
        restLeaveStatusWorkFlowsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLeaveStatusWorkFlows() throws Exception {
        // Initialize the database
        leaveStatusWorkFlowsRepository.saveAndFlush(leaveStatusWorkFlows);

        int databaseSizeBeforeUpdate = leaveStatusWorkFlowsRepository.findAll().size();

        // Update the leaveStatusWorkFlows
        LeaveStatusWorkFlows updatedLeaveStatusWorkFlows = leaveStatusWorkFlowsRepository.findById(leaveStatusWorkFlows.getId()).get();
        // Disconnect from session so that the updates on updatedLeaveStatusWorkFlows are not directly saved in db
        em.detach(updatedLeaveStatusWorkFlows);
        updatedLeaveStatusWorkFlows
            .ifApprovals(UPDATED_IF_APPROVALS)
            .approvalRequired(UPDATED_APPROVAL_REQUIRED)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restLeaveStatusWorkFlowsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLeaveStatusWorkFlows.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLeaveStatusWorkFlows))
            )
            .andExpect(status().isOk());

        // Validate the LeaveStatusWorkFlows in the database
        List<LeaveStatusWorkFlows> leaveStatusWorkFlowsList = leaveStatusWorkFlowsRepository.findAll();
        assertThat(leaveStatusWorkFlowsList).hasSize(databaseSizeBeforeUpdate);
        LeaveStatusWorkFlows testLeaveStatusWorkFlows = leaveStatusWorkFlowsList.get(leaveStatusWorkFlowsList.size() - 1);
        assertThat(testLeaveStatusWorkFlows.getIfApprovals()).isEqualTo(UPDATED_IF_APPROVALS);
        assertThat(testLeaveStatusWorkFlows.getApprovalRequired()).isEqualTo(UPDATED_APPROVAL_REQUIRED);
        assertThat(testLeaveStatusWorkFlows.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveStatusWorkFlows.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaveStatusWorkFlows.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testLeaveStatusWorkFlows.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingLeaveStatusWorkFlows() throws Exception {
        int databaseSizeBeforeUpdate = leaveStatusWorkFlowsRepository.findAll().size();
        leaveStatusWorkFlows.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveStatusWorkFlowsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leaveStatusWorkFlows.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveStatusWorkFlows))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveStatusWorkFlows in the database
        List<LeaveStatusWorkFlows> leaveStatusWorkFlowsList = leaveStatusWorkFlowsRepository.findAll();
        assertThat(leaveStatusWorkFlowsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeaveStatusWorkFlows() throws Exception {
        int databaseSizeBeforeUpdate = leaveStatusWorkFlowsRepository.findAll().size();
        leaveStatusWorkFlows.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveStatusWorkFlowsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveStatusWorkFlows))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveStatusWorkFlows in the database
        List<LeaveStatusWorkFlows> leaveStatusWorkFlowsList = leaveStatusWorkFlowsRepository.findAll();
        assertThat(leaveStatusWorkFlowsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeaveStatusWorkFlows() throws Exception {
        int databaseSizeBeforeUpdate = leaveStatusWorkFlowsRepository.findAll().size();
        leaveStatusWorkFlows.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveStatusWorkFlowsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveStatusWorkFlows))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveStatusWorkFlows in the database
        List<LeaveStatusWorkFlows> leaveStatusWorkFlowsList = leaveStatusWorkFlowsRepository.findAll();
        assertThat(leaveStatusWorkFlowsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeaveStatusWorkFlowsWithPatch() throws Exception {
        // Initialize the database
        leaveStatusWorkFlowsRepository.saveAndFlush(leaveStatusWorkFlows);

        int databaseSizeBeforeUpdate = leaveStatusWorkFlowsRepository.findAll().size();

        // Update the leaveStatusWorkFlows using partial update
        LeaveStatusWorkFlows partialUpdatedLeaveStatusWorkFlows = new LeaveStatusWorkFlows();
        partialUpdatedLeaveStatusWorkFlows.setId(leaveStatusWorkFlows.getId());

        partialUpdatedLeaveStatusWorkFlows.approvalRequired(UPDATED_APPROVAL_REQUIRED);

        restLeaveStatusWorkFlowsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveStatusWorkFlows.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveStatusWorkFlows))
            )
            .andExpect(status().isOk());

        // Validate the LeaveStatusWorkFlows in the database
        List<LeaveStatusWorkFlows> leaveStatusWorkFlowsList = leaveStatusWorkFlowsRepository.findAll();
        assertThat(leaveStatusWorkFlowsList).hasSize(databaseSizeBeforeUpdate);
        LeaveStatusWorkFlows testLeaveStatusWorkFlows = leaveStatusWorkFlowsList.get(leaveStatusWorkFlowsList.size() - 1);
        assertThat(testLeaveStatusWorkFlows.getIfApprovals()).isEqualTo(DEFAULT_IF_APPROVALS);
        assertThat(testLeaveStatusWorkFlows.getApprovalRequired()).isEqualTo(UPDATED_APPROVAL_REQUIRED);
        assertThat(testLeaveStatusWorkFlows.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testLeaveStatusWorkFlows.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testLeaveStatusWorkFlows.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testLeaveStatusWorkFlows.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateLeaveStatusWorkFlowsWithPatch() throws Exception {
        // Initialize the database
        leaveStatusWorkFlowsRepository.saveAndFlush(leaveStatusWorkFlows);

        int databaseSizeBeforeUpdate = leaveStatusWorkFlowsRepository.findAll().size();

        // Update the leaveStatusWorkFlows using partial update
        LeaveStatusWorkFlows partialUpdatedLeaveStatusWorkFlows = new LeaveStatusWorkFlows();
        partialUpdatedLeaveStatusWorkFlows.setId(leaveStatusWorkFlows.getId());

        partialUpdatedLeaveStatusWorkFlows
            .ifApprovals(UPDATED_IF_APPROVALS)
            .approvalRequired(UPDATED_APPROVAL_REQUIRED)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restLeaveStatusWorkFlowsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveStatusWorkFlows.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveStatusWorkFlows))
            )
            .andExpect(status().isOk());

        // Validate the LeaveStatusWorkFlows in the database
        List<LeaveStatusWorkFlows> leaveStatusWorkFlowsList = leaveStatusWorkFlowsRepository.findAll();
        assertThat(leaveStatusWorkFlowsList).hasSize(databaseSizeBeforeUpdate);
        LeaveStatusWorkFlows testLeaveStatusWorkFlows = leaveStatusWorkFlowsList.get(leaveStatusWorkFlowsList.size() - 1);
        assertThat(testLeaveStatusWorkFlows.getIfApprovals()).isEqualTo(UPDATED_IF_APPROVALS);
        assertThat(testLeaveStatusWorkFlows.getApprovalRequired()).isEqualTo(UPDATED_APPROVAL_REQUIRED);
        assertThat(testLeaveStatusWorkFlows.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveStatusWorkFlows.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaveStatusWorkFlows.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testLeaveStatusWorkFlows.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingLeaveStatusWorkFlows() throws Exception {
        int databaseSizeBeforeUpdate = leaveStatusWorkFlowsRepository.findAll().size();
        leaveStatusWorkFlows.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveStatusWorkFlowsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leaveStatusWorkFlows.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveStatusWorkFlows))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveStatusWorkFlows in the database
        List<LeaveStatusWorkFlows> leaveStatusWorkFlowsList = leaveStatusWorkFlowsRepository.findAll();
        assertThat(leaveStatusWorkFlowsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeaveStatusWorkFlows() throws Exception {
        int databaseSizeBeforeUpdate = leaveStatusWorkFlowsRepository.findAll().size();
        leaveStatusWorkFlows.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveStatusWorkFlowsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveStatusWorkFlows))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveStatusWorkFlows in the database
        List<LeaveStatusWorkFlows> leaveStatusWorkFlowsList = leaveStatusWorkFlowsRepository.findAll();
        assertThat(leaveStatusWorkFlowsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeaveStatusWorkFlows() throws Exception {
        int databaseSizeBeforeUpdate = leaveStatusWorkFlowsRepository.findAll().size();
        leaveStatusWorkFlows.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveStatusWorkFlowsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveStatusWorkFlows))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveStatusWorkFlows in the database
        List<LeaveStatusWorkFlows> leaveStatusWorkFlowsList = leaveStatusWorkFlowsRepository.findAll();
        assertThat(leaveStatusWorkFlowsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLeaveStatusWorkFlows() throws Exception {
        // Initialize the database
        leaveStatusWorkFlowsRepository.saveAndFlush(leaveStatusWorkFlows);

        int databaseSizeBeforeDelete = leaveStatusWorkFlowsRepository.findAll().size();

        // Delete the leaveStatusWorkFlows
        restLeaveStatusWorkFlowsMockMvc
            .perform(delete(ENTITY_API_URL_ID, leaveStatusWorkFlows.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeaveStatusWorkFlows> leaveStatusWorkFlowsList = leaveStatusWorkFlowsRepository.findAll();
        assertThat(leaveStatusWorkFlowsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
