package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.LeaveRequestApprovers;
import com.venturedive.vendian_mono.domain.LeaveRequests;
import com.venturedive.vendian_mono.domain.LeaveStatuses;
import com.venturedive.vendian_mono.repository.LeaveRequestApproversRepository;
import com.venturedive.vendian_mono.service.criteria.LeaveRequestApproversCriteria;
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
 * Integration tests for the {@link LeaveRequestApproversResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeaveRequestApproversResourceIT {

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_AS = "AAAAAAAAAA";
    private static final String UPDATED_AS = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_APPROVER_GROUP = "AAAAAAAAAA";
    private static final String UPDATED_APPROVER_GROUP = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRIORITY = 1;
    private static final Integer UPDATED_PRIORITY = 2;
    private static final Integer SMALLER_PRIORITY = 1 - 1;

    private static final Instant DEFAULT_STATUS_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STATUS_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;
    private static final Integer SMALLER_VERSION = 1 - 1;

    private static final String ENTITY_API_URL = "/api/leave-request-approvers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LeaveRequestApproversRepository leaveRequestApproversRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeaveRequestApproversMockMvc;

    private LeaveRequestApprovers leaveRequestApprovers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveRequestApprovers createEntity(EntityManager em) {
        LeaveRequestApprovers leaveRequestApprovers = new LeaveRequestApprovers()
            .reference(DEFAULT_REFERENCE)
            .as(DEFAULT_AS)
            .comments(DEFAULT_COMMENTS)
            .approverGroup(DEFAULT_APPROVER_GROUP)
            .priority(DEFAULT_PRIORITY)
            .statusDate(DEFAULT_STATUS_DATE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT)
            .version(DEFAULT_VERSION);
        // Add required entity
        LeaveRequests leaveRequests;
        if (TestUtil.findAll(em, LeaveRequests.class).isEmpty()) {
            leaveRequests = LeaveRequestsResourceIT.createEntity(em);
            em.persist(leaveRequests);
            em.flush();
        } else {
            leaveRequests = TestUtil.findAll(em, LeaveRequests.class).get(0);
        }
        leaveRequestApprovers.setLeaveRequest(leaveRequests);
        // Add required entity
        LeaveStatuses leaveStatuses;
        if (TestUtil.findAll(em, LeaveStatuses.class).isEmpty()) {
            leaveStatuses = LeaveStatusesResourceIT.createEntity(em);
            em.persist(leaveStatuses);
            em.flush();
        } else {
            leaveStatuses = TestUtil.findAll(em, LeaveStatuses.class).get(0);
        }
        leaveRequestApprovers.setStatus(leaveStatuses);
        return leaveRequestApprovers;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveRequestApprovers createUpdatedEntity(EntityManager em) {
        LeaveRequestApprovers leaveRequestApprovers = new LeaveRequestApprovers()
            .reference(UPDATED_REFERENCE)
            .as(UPDATED_AS)
            .comments(UPDATED_COMMENTS)
            .approverGroup(UPDATED_APPROVER_GROUP)
            .priority(UPDATED_PRIORITY)
            .statusDate(UPDATED_STATUS_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);
        // Add required entity
        LeaveRequests leaveRequests;
        if (TestUtil.findAll(em, LeaveRequests.class).isEmpty()) {
            leaveRequests = LeaveRequestsResourceIT.createUpdatedEntity(em);
            em.persist(leaveRequests);
            em.flush();
        } else {
            leaveRequests = TestUtil.findAll(em, LeaveRequests.class).get(0);
        }
        leaveRequestApprovers.setLeaveRequest(leaveRequests);
        // Add required entity
        LeaveStatuses leaveStatuses;
        if (TestUtil.findAll(em, LeaveStatuses.class).isEmpty()) {
            leaveStatuses = LeaveStatusesResourceIT.createUpdatedEntity(em);
            em.persist(leaveStatuses);
            em.flush();
        } else {
            leaveStatuses = TestUtil.findAll(em, LeaveStatuses.class).get(0);
        }
        leaveRequestApprovers.setStatus(leaveStatuses);
        return leaveRequestApprovers;
    }

    @BeforeEach
    public void initTest() {
        leaveRequestApprovers = createEntity(em);
    }

    @Test
    @Transactional
    void createLeaveRequestApprovers() throws Exception {
        int databaseSizeBeforeCreate = leaveRequestApproversRepository.findAll().size();
        // Create the LeaveRequestApprovers
        restLeaveRequestApproversMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequestApprovers))
            )
            .andExpect(status().isCreated());

        // Validate the LeaveRequestApprovers in the database
        List<LeaveRequestApprovers> leaveRequestApproversList = leaveRequestApproversRepository.findAll();
        assertThat(leaveRequestApproversList).hasSize(databaseSizeBeforeCreate + 1);
        LeaveRequestApprovers testLeaveRequestApprovers = leaveRequestApproversList.get(leaveRequestApproversList.size() - 1);
        assertThat(testLeaveRequestApprovers.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testLeaveRequestApprovers.getAs()).isEqualTo(DEFAULT_AS);
        assertThat(testLeaveRequestApprovers.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testLeaveRequestApprovers.getApproverGroup()).isEqualTo(DEFAULT_APPROVER_GROUP);
        assertThat(testLeaveRequestApprovers.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testLeaveRequestApprovers.getStatusDate()).isEqualTo(DEFAULT_STATUS_DATE);
        assertThat(testLeaveRequestApprovers.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testLeaveRequestApprovers.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testLeaveRequestApprovers.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testLeaveRequestApprovers.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createLeaveRequestApproversWithExistingId() throws Exception {
        // Create the LeaveRequestApprovers with an existing ID
        leaveRequestApprovers.setId(1L);

        int databaseSizeBeforeCreate = leaveRequestApproversRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeaveRequestApproversMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequestApprovers))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveRequestApprovers in the database
        List<LeaveRequestApprovers> leaveRequestApproversList = leaveRequestApproversRepository.findAll();
        assertThat(leaveRequestApproversList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkReferenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveRequestApproversRepository.findAll().size();
        // set the field null
        leaveRequestApprovers.setReference(null);

        // Create the LeaveRequestApprovers, which fails.

        restLeaveRequestApproversMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequestApprovers))
            )
            .andExpect(status().isBadRequest());

        List<LeaveRequestApprovers> leaveRequestApproversList = leaveRequestApproversRepository.findAll();
        assertThat(leaveRequestApproversList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAsIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveRequestApproversRepository.findAll().size();
        // set the field null
        leaveRequestApprovers.setAs(null);

        // Create the LeaveRequestApprovers, which fails.

        restLeaveRequestApproversMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequestApprovers))
            )
            .andExpect(status().isBadRequest());

        List<LeaveRequestApprovers> leaveRequestApproversList = leaveRequestApproversRepository.findAll();
        assertThat(leaveRequestApproversList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkApproverGroupIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveRequestApproversRepository.findAll().size();
        // set the field null
        leaveRequestApprovers.setApproverGroup(null);

        // Create the LeaveRequestApprovers, which fails.

        restLeaveRequestApproversMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequestApprovers))
            )
            .andExpect(status().isBadRequest());

        List<LeaveRequestApprovers> leaveRequestApproversList = leaveRequestApproversRepository.findAll();
        assertThat(leaveRequestApproversList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriorityIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveRequestApproversRepository.findAll().size();
        // set the field null
        leaveRequestApprovers.setPriority(null);

        // Create the LeaveRequestApprovers, which fails.

        restLeaveRequestApproversMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequestApprovers))
            )
            .andExpect(status().isBadRequest());

        List<LeaveRequestApprovers> leaveRequestApproversList = leaveRequestApproversRepository.findAll();
        assertThat(leaveRequestApproversList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveRequestApproversRepository.findAll().size();
        // set the field null
        leaveRequestApprovers.setStatusDate(null);

        // Create the LeaveRequestApprovers, which fails.

        restLeaveRequestApproversMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequestApprovers))
            )
            .andExpect(status().isBadRequest());

        List<LeaveRequestApprovers> leaveRequestApproversList = leaveRequestApproversRepository.findAll();
        assertThat(leaveRequestApproversList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveRequestApproversRepository.findAll().size();
        // set the field null
        leaveRequestApprovers.setCreatedAt(null);

        // Create the LeaveRequestApprovers, which fails.

        restLeaveRequestApproversMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequestApprovers))
            )
            .andExpect(status().isBadRequest());

        List<LeaveRequestApprovers> leaveRequestApproversList = leaveRequestApproversRepository.findAll();
        assertThat(leaveRequestApproversList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveRequestApproversRepository.findAll().size();
        // set the field null
        leaveRequestApprovers.setUpdatedAt(null);

        // Create the LeaveRequestApprovers, which fails.

        restLeaveRequestApproversMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequestApprovers))
            )
            .andExpect(status().isBadRequest());

        List<LeaveRequestApprovers> leaveRequestApproversList = leaveRequestApproversRepository.findAll();
        assertThat(leaveRequestApproversList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveRequestApproversRepository.findAll().size();
        // set the field null
        leaveRequestApprovers.setVersion(null);

        // Create the LeaveRequestApprovers, which fails.

        restLeaveRequestApproversMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequestApprovers))
            )
            .andExpect(status().isBadRequest());

        List<LeaveRequestApprovers> leaveRequestApproversList = leaveRequestApproversRepository.findAll();
        assertThat(leaveRequestApproversList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApprovers() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList
        restLeaveRequestApproversMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveRequestApprovers.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].as").value(hasItem(DEFAULT_AS)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].approverGroup").value(hasItem(DEFAULT_APPROVER_GROUP)))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].statusDate").value(hasItem(DEFAULT_STATUS_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getLeaveRequestApprovers() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get the leaveRequestApprovers
        restLeaveRequestApproversMockMvc
            .perform(get(ENTITY_API_URL_ID, leaveRequestApprovers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leaveRequestApprovers.getId().intValue()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE))
            .andExpect(jsonPath("$.as").value(DEFAULT_AS))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.approverGroup").value(DEFAULT_APPROVER_GROUP))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY))
            .andExpect(jsonPath("$.statusDate").value(DEFAULT_STATUS_DATE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getLeaveRequestApproversByIdFiltering() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        Long id = leaveRequestApprovers.getId();

        defaultLeaveRequestApproversShouldBeFound("id.equals=" + id);
        defaultLeaveRequestApproversShouldNotBeFound("id.notEquals=" + id);

        defaultLeaveRequestApproversShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLeaveRequestApproversShouldNotBeFound("id.greaterThan=" + id);

        defaultLeaveRequestApproversShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLeaveRequestApproversShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByReferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where reference equals to DEFAULT_REFERENCE
        defaultLeaveRequestApproversShouldBeFound("reference.equals=" + DEFAULT_REFERENCE);

        // Get all the leaveRequestApproversList where reference equals to UPDATED_REFERENCE
        defaultLeaveRequestApproversShouldNotBeFound("reference.equals=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByReferenceIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where reference in DEFAULT_REFERENCE or UPDATED_REFERENCE
        defaultLeaveRequestApproversShouldBeFound("reference.in=" + DEFAULT_REFERENCE + "," + UPDATED_REFERENCE);

        // Get all the leaveRequestApproversList where reference equals to UPDATED_REFERENCE
        defaultLeaveRequestApproversShouldNotBeFound("reference.in=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByReferenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where reference is not null
        defaultLeaveRequestApproversShouldBeFound("reference.specified=true");

        // Get all the leaveRequestApproversList where reference is null
        defaultLeaveRequestApproversShouldNotBeFound("reference.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByReferenceContainsSomething() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where reference contains DEFAULT_REFERENCE
        defaultLeaveRequestApproversShouldBeFound("reference.contains=" + DEFAULT_REFERENCE);

        // Get all the leaveRequestApproversList where reference contains UPDATED_REFERENCE
        defaultLeaveRequestApproversShouldNotBeFound("reference.contains=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByReferenceNotContainsSomething() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where reference does not contain DEFAULT_REFERENCE
        defaultLeaveRequestApproversShouldNotBeFound("reference.doesNotContain=" + DEFAULT_REFERENCE);

        // Get all the leaveRequestApproversList where reference does not contain UPDATED_REFERENCE
        defaultLeaveRequestApproversShouldBeFound("reference.doesNotContain=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByAsIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where as equals to DEFAULT_AS
        defaultLeaveRequestApproversShouldBeFound("as.equals=" + DEFAULT_AS);

        // Get all the leaveRequestApproversList where as equals to UPDATED_AS
        defaultLeaveRequestApproversShouldNotBeFound("as.equals=" + UPDATED_AS);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByAsIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where as in DEFAULT_AS or UPDATED_AS
        defaultLeaveRequestApproversShouldBeFound("as.in=" + DEFAULT_AS + "," + UPDATED_AS);

        // Get all the leaveRequestApproversList where as equals to UPDATED_AS
        defaultLeaveRequestApproversShouldNotBeFound("as.in=" + UPDATED_AS);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByAsIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where as is not null
        defaultLeaveRequestApproversShouldBeFound("as.specified=true");

        // Get all the leaveRequestApproversList where as is null
        defaultLeaveRequestApproversShouldNotBeFound("as.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByAsContainsSomething() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where as contains DEFAULT_AS
        defaultLeaveRequestApproversShouldBeFound("as.contains=" + DEFAULT_AS);

        // Get all the leaveRequestApproversList where as contains UPDATED_AS
        defaultLeaveRequestApproversShouldNotBeFound("as.contains=" + UPDATED_AS);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByAsNotContainsSomething() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where as does not contain DEFAULT_AS
        defaultLeaveRequestApproversShouldNotBeFound("as.doesNotContain=" + DEFAULT_AS);

        // Get all the leaveRequestApproversList where as does not contain UPDATED_AS
        defaultLeaveRequestApproversShouldBeFound("as.doesNotContain=" + UPDATED_AS);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where comments equals to DEFAULT_COMMENTS
        defaultLeaveRequestApproversShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the leaveRequestApproversList where comments equals to UPDATED_COMMENTS
        defaultLeaveRequestApproversShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultLeaveRequestApproversShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the leaveRequestApproversList where comments equals to UPDATED_COMMENTS
        defaultLeaveRequestApproversShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where comments is not null
        defaultLeaveRequestApproversShouldBeFound("comments.specified=true");

        // Get all the leaveRequestApproversList where comments is null
        defaultLeaveRequestApproversShouldNotBeFound("comments.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByCommentsContainsSomething() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where comments contains DEFAULT_COMMENTS
        defaultLeaveRequestApproversShouldBeFound("comments.contains=" + DEFAULT_COMMENTS);

        // Get all the leaveRequestApproversList where comments contains UPDATED_COMMENTS
        defaultLeaveRequestApproversShouldNotBeFound("comments.contains=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where comments does not contain DEFAULT_COMMENTS
        defaultLeaveRequestApproversShouldNotBeFound("comments.doesNotContain=" + DEFAULT_COMMENTS);

        // Get all the leaveRequestApproversList where comments does not contain UPDATED_COMMENTS
        defaultLeaveRequestApproversShouldBeFound("comments.doesNotContain=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByApproverGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where approverGroup equals to DEFAULT_APPROVER_GROUP
        defaultLeaveRequestApproversShouldBeFound("approverGroup.equals=" + DEFAULT_APPROVER_GROUP);

        // Get all the leaveRequestApproversList where approverGroup equals to UPDATED_APPROVER_GROUP
        defaultLeaveRequestApproversShouldNotBeFound("approverGroup.equals=" + UPDATED_APPROVER_GROUP);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByApproverGroupIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where approverGroup in DEFAULT_APPROVER_GROUP or UPDATED_APPROVER_GROUP
        defaultLeaveRequestApproversShouldBeFound("approverGroup.in=" + DEFAULT_APPROVER_GROUP + "," + UPDATED_APPROVER_GROUP);

        // Get all the leaveRequestApproversList where approverGroup equals to UPDATED_APPROVER_GROUP
        defaultLeaveRequestApproversShouldNotBeFound("approverGroup.in=" + UPDATED_APPROVER_GROUP);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByApproverGroupIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where approverGroup is not null
        defaultLeaveRequestApproversShouldBeFound("approverGroup.specified=true");

        // Get all the leaveRequestApproversList where approverGroup is null
        defaultLeaveRequestApproversShouldNotBeFound("approverGroup.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByApproverGroupContainsSomething() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where approverGroup contains DEFAULT_APPROVER_GROUP
        defaultLeaveRequestApproversShouldBeFound("approverGroup.contains=" + DEFAULT_APPROVER_GROUP);

        // Get all the leaveRequestApproversList where approverGroup contains UPDATED_APPROVER_GROUP
        defaultLeaveRequestApproversShouldNotBeFound("approverGroup.contains=" + UPDATED_APPROVER_GROUP);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByApproverGroupNotContainsSomething() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where approverGroup does not contain DEFAULT_APPROVER_GROUP
        defaultLeaveRequestApproversShouldNotBeFound("approverGroup.doesNotContain=" + DEFAULT_APPROVER_GROUP);

        // Get all the leaveRequestApproversList where approverGroup does not contain UPDATED_APPROVER_GROUP
        defaultLeaveRequestApproversShouldBeFound("approverGroup.doesNotContain=" + UPDATED_APPROVER_GROUP);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByPriorityIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where priority equals to DEFAULT_PRIORITY
        defaultLeaveRequestApproversShouldBeFound("priority.equals=" + DEFAULT_PRIORITY);

        // Get all the leaveRequestApproversList where priority equals to UPDATED_PRIORITY
        defaultLeaveRequestApproversShouldNotBeFound("priority.equals=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByPriorityIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where priority in DEFAULT_PRIORITY or UPDATED_PRIORITY
        defaultLeaveRequestApproversShouldBeFound("priority.in=" + DEFAULT_PRIORITY + "," + UPDATED_PRIORITY);

        // Get all the leaveRequestApproversList where priority equals to UPDATED_PRIORITY
        defaultLeaveRequestApproversShouldNotBeFound("priority.in=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByPriorityIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where priority is not null
        defaultLeaveRequestApproversShouldBeFound("priority.specified=true");

        // Get all the leaveRequestApproversList where priority is null
        defaultLeaveRequestApproversShouldNotBeFound("priority.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByPriorityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where priority is greater than or equal to DEFAULT_PRIORITY
        defaultLeaveRequestApproversShouldBeFound("priority.greaterThanOrEqual=" + DEFAULT_PRIORITY);

        // Get all the leaveRequestApproversList where priority is greater than or equal to UPDATED_PRIORITY
        defaultLeaveRequestApproversShouldNotBeFound("priority.greaterThanOrEqual=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByPriorityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where priority is less than or equal to DEFAULT_PRIORITY
        defaultLeaveRequestApproversShouldBeFound("priority.lessThanOrEqual=" + DEFAULT_PRIORITY);

        // Get all the leaveRequestApproversList where priority is less than or equal to SMALLER_PRIORITY
        defaultLeaveRequestApproversShouldNotBeFound("priority.lessThanOrEqual=" + SMALLER_PRIORITY);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByPriorityIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where priority is less than DEFAULT_PRIORITY
        defaultLeaveRequestApproversShouldNotBeFound("priority.lessThan=" + DEFAULT_PRIORITY);

        // Get all the leaveRequestApproversList where priority is less than UPDATED_PRIORITY
        defaultLeaveRequestApproversShouldBeFound("priority.lessThan=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByPriorityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where priority is greater than DEFAULT_PRIORITY
        defaultLeaveRequestApproversShouldNotBeFound("priority.greaterThan=" + DEFAULT_PRIORITY);

        // Get all the leaveRequestApproversList where priority is greater than SMALLER_PRIORITY
        defaultLeaveRequestApproversShouldBeFound("priority.greaterThan=" + SMALLER_PRIORITY);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByStatusDateIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where statusDate equals to DEFAULT_STATUS_DATE
        defaultLeaveRequestApproversShouldBeFound("statusDate.equals=" + DEFAULT_STATUS_DATE);

        // Get all the leaveRequestApproversList where statusDate equals to UPDATED_STATUS_DATE
        defaultLeaveRequestApproversShouldNotBeFound("statusDate.equals=" + UPDATED_STATUS_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByStatusDateIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where statusDate in DEFAULT_STATUS_DATE or UPDATED_STATUS_DATE
        defaultLeaveRequestApproversShouldBeFound("statusDate.in=" + DEFAULT_STATUS_DATE + "," + UPDATED_STATUS_DATE);

        // Get all the leaveRequestApproversList where statusDate equals to UPDATED_STATUS_DATE
        defaultLeaveRequestApproversShouldNotBeFound("statusDate.in=" + UPDATED_STATUS_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByStatusDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where statusDate is not null
        defaultLeaveRequestApproversShouldBeFound("statusDate.specified=true");

        // Get all the leaveRequestApproversList where statusDate is null
        defaultLeaveRequestApproversShouldNotBeFound("statusDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where createdAt equals to DEFAULT_CREATED_AT
        defaultLeaveRequestApproversShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the leaveRequestApproversList where createdAt equals to UPDATED_CREATED_AT
        defaultLeaveRequestApproversShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultLeaveRequestApproversShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the leaveRequestApproversList where createdAt equals to UPDATED_CREATED_AT
        defaultLeaveRequestApproversShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where createdAt is not null
        defaultLeaveRequestApproversShouldBeFound("createdAt.specified=true");

        // Get all the leaveRequestApproversList where createdAt is null
        defaultLeaveRequestApproversShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultLeaveRequestApproversShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the leaveRequestApproversList where updatedAt equals to UPDATED_UPDATED_AT
        defaultLeaveRequestApproversShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultLeaveRequestApproversShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the leaveRequestApproversList where updatedAt equals to UPDATED_UPDATED_AT
        defaultLeaveRequestApproversShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where updatedAt is not null
        defaultLeaveRequestApproversShouldBeFound("updatedAt.specified=true");

        // Get all the leaveRequestApproversList where updatedAt is null
        defaultLeaveRequestApproversShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByDeletedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where deletedAt equals to DEFAULT_DELETED_AT
        defaultLeaveRequestApproversShouldBeFound("deletedAt.equals=" + DEFAULT_DELETED_AT);

        // Get all the leaveRequestApproversList where deletedAt equals to UPDATED_DELETED_AT
        defaultLeaveRequestApproversShouldNotBeFound("deletedAt.equals=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByDeletedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where deletedAt in DEFAULT_DELETED_AT or UPDATED_DELETED_AT
        defaultLeaveRequestApproversShouldBeFound("deletedAt.in=" + DEFAULT_DELETED_AT + "," + UPDATED_DELETED_AT);

        // Get all the leaveRequestApproversList where deletedAt equals to UPDATED_DELETED_AT
        defaultLeaveRequestApproversShouldNotBeFound("deletedAt.in=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByDeletedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where deletedAt is not null
        defaultLeaveRequestApproversShouldBeFound("deletedAt.specified=true");

        // Get all the leaveRequestApproversList where deletedAt is null
        defaultLeaveRequestApproversShouldNotBeFound("deletedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where version equals to DEFAULT_VERSION
        defaultLeaveRequestApproversShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the leaveRequestApproversList where version equals to UPDATED_VERSION
        defaultLeaveRequestApproversShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultLeaveRequestApproversShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the leaveRequestApproversList where version equals to UPDATED_VERSION
        defaultLeaveRequestApproversShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where version is not null
        defaultLeaveRequestApproversShouldBeFound("version.specified=true");

        // Get all the leaveRequestApproversList where version is null
        defaultLeaveRequestApproversShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where version is greater than or equal to DEFAULT_VERSION
        defaultLeaveRequestApproversShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the leaveRequestApproversList where version is greater than or equal to UPDATED_VERSION
        defaultLeaveRequestApproversShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where version is less than or equal to DEFAULT_VERSION
        defaultLeaveRequestApproversShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the leaveRequestApproversList where version is less than or equal to SMALLER_VERSION
        defaultLeaveRequestApproversShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where version is less than DEFAULT_VERSION
        defaultLeaveRequestApproversShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the leaveRequestApproversList where version is less than UPDATED_VERSION
        defaultLeaveRequestApproversShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        // Get all the leaveRequestApproversList where version is greater than DEFAULT_VERSION
        defaultLeaveRequestApproversShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the leaveRequestApproversList where version is greater than SMALLER_VERSION
        defaultLeaveRequestApproversShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByLeaveRequestIsEqualToSomething() throws Exception {
        LeaveRequests leaveRequest;
        if (TestUtil.findAll(em, LeaveRequests.class).isEmpty()) {
            leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);
            leaveRequest = LeaveRequestsResourceIT.createEntity(em);
        } else {
            leaveRequest = TestUtil.findAll(em, LeaveRequests.class).get(0);
        }
        em.persist(leaveRequest);
        em.flush();
        leaveRequestApprovers.setLeaveRequest(leaveRequest);
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);
        Long leaveRequestId = leaveRequest.getId();

        // Get all the leaveRequestApproversList where leaveRequest equals to leaveRequestId
        defaultLeaveRequestApproversShouldBeFound("leaveRequestId.equals=" + leaveRequestId);

        // Get all the leaveRequestApproversList where leaveRequest equals to (leaveRequestId + 1)
        defaultLeaveRequestApproversShouldNotBeFound("leaveRequestId.equals=" + (leaveRequestId + 1));
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByUserIsEqualToSomething() throws Exception {
        Employees user;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);
            user = EmployeesResourceIT.createEntity(em);
        } else {
            user = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(user);
        em.flush();
        leaveRequestApprovers.setUser(user);
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);
        Long userId = user.getId();

        // Get all the leaveRequestApproversList where user equals to userId
        defaultLeaveRequestApproversShouldBeFound("userId.equals=" + userId);

        // Get all the leaveRequestApproversList where user equals to (userId + 1)
        defaultLeaveRequestApproversShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproversByStatusIsEqualToSomething() throws Exception {
        LeaveStatuses status;
        if (TestUtil.findAll(em, LeaveStatuses.class).isEmpty()) {
            leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);
            status = LeaveStatusesResourceIT.createEntity(em);
        } else {
            status = TestUtil.findAll(em, LeaveStatuses.class).get(0);
        }
        em.persist(status);
        em.flush();
        leaveRequestApprovers.setStatus(status);
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);
        Long statusId = status.getId();

        // Get all the leaveRequestApproversList where status equals to statusId
        defaultLeaveRequestApproversShouldBeFound("statusId.equals=" + statusId);

        // Get all the leaveRequestApproversList where status equals to (statusId + 1)
        defaultLeaveRequestApproversShouldNotBeFound("statusId.equals=" + (statusId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLeaveRequestApproversShouldBeFound(String filter) throws Exception {
        restLeaveRequestApproversMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveRequestApprovers.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].as").value(hasItem(DEFAULT_AS)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].approverGroup").value(hasItem(DEFAULT_APPROVER_GROUP)))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].statusDate").value(hasItem(DEFAULT_STATUS_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restLeaveRequestApproversMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLeaveRequestApproversShouldNotBeFound(String filter) throws Exception {
        restLeaveRequestApproversMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLeaveRequestApproversMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLeaveRequestApprovers() throws Exception {
        // Get the leaveRequestApprovers
        restLeaveRequestApproversMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLeaveRequestApprovers() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        int databaseSizeBeforeUpdate = leaveRequestApproversRepository.findAll().size();

        // Update the leaveRequestApprovers
        LeaveRequestApprovers updatedLeaveRequestApprovers = leaveRequestApproversRepository.findById(leaveRequestApprovers.getId()).get();
        // Disconnect from session so that the updates on updatedLeaveRequestApprovers are not directly saved in db
        em.detach(updatedLeaveRequestApprovers);
        updatedLeaveRequestApprovers
            .reference(UPDATED_REFERENCE)
            .as(UPDATED_AS)
            .comments(UPDATED_COMMENTS)
            .approverGroup(UPDATED_APPROVER_GROUP)
            .priority(UPDATED_PRIORITY)
            .statusDate(UPDATED_STATUS_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restLeaveRequestApproversMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLeaveRequestApprovers.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLeaveRequestApprovers))
            )
            .andExpect(status().isOk());

        // Validate the LeaveRequestApprovers in the database
        List<LeaveRequestApprovers> leaveRequestApproversList = leaveRequestApproversRepository.findAll();
        assertThat(leaveRequestApproversList).hasSize(databaseSizeBeforeUpdate);
        LeaveRequestApprovers testLeaveRequestApprovers = leaveRequestApproversList.get(leaveRequestApproversList.size() - 1);
        assertThat(testLeaveRequestApprovers.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testLeaveRequestApprovers.getAs()).isEqualTo(UPDATED_AS);
        assertThat(testLeaveRequestApprovers.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testLeaveRequestApprovers.getApproverGroup()).isEqualTo(UPDATED_APPROVER_GROUP);
        assertThat(testLeaveRequestApprovers.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testLeaveRequestApprovers.getStatusDate()).isEqualTo(UPDATED_STATUS_DATE);
        assertThat(testLeaveRequestApprovers.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveRequestApprovers.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaveRequestApprovers.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testLeaveRequestApprovers.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingLeaveRequestApprovers() throws Exception {
        int databaseSizeBeforeUpdate = leaveRequestApproversRepository.findAll().size();
        leaveRequestApprovers.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveRequestApproversMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leaveRequestApprovers.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequestApprovers))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveRequestApprovers in the database
        List<LeaveRequestApprovers> leaveRequestApproversList = leaveRequestApproversRepository.findAll();
        assertThat(leaveRequestApproversList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeaveRequestApprovers() throws Exception {
        int databaseSizeBeforeUpdate = leaveRequestApproversRepository.findAll().size();
        leaveRequestApprovers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveRequestApproversMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequestApprovers))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveRequestApprovers in the database
        List<LeaveRequestApprovers> leaveRequestApproversList = leaveRequestApproversRepository.findAll();
        assertThat(leaveRequestApproversList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeaveRequestApprovers() throws Exception {
        int databaseSizeBeforeUpdate = leaveRequestApproversRepository.findAll().size();
        leaveRequestApprovers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveRequestApproversMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequestApprovers))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveRequestApprovers in the database
        List<LeaveRequestApprovers> leaveRequestApproversList = leaveRequestApproversRepository.findAll();
        assertThat(leaveRequestApproversList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeaveRequestApproversWithPatch() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        int databaseSizeBeforeUpdate = leaveRequestApproversRepository.findAll().size();

        // Update the leaveRequestApprovers using partial update
        LeaveRequestApprovers partialUpdatedLeaveRequestApprovers = new LeaveRequestApprovers();
        partialUpdatedLeaveRequestApprovers.setId(leaveRequestApprovers.getId());

        partialUpdatedLeaveRequestApprovers.reference(UPDATED_REFERENCE).updatedAt(UPDATED_UPDATED_AT).deletedAt(UPDATED_DELETED_AT);

        restLeaveRequestApproversMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveRequestApprovers.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveRequestApprovers))
            )
            .andExpect(status().isOk());

        // Validate the LeaveRequestApprovers in the database
        List<LeaveRequestApprovers> leaveRequestApproversList = leaveRequestApproversRepository.findAll();
        assertThat(leaveRequestApproversList).hasSize(databaseSizeBeforeUpdate);
        LeaveRequestApprovers testLeaveRequestApprovers = leaveRequestApproversList.get(leaveRequestApproversList.size() - 1);
        assertThat(testLeaveRequestApprovers.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testLeaveRequestApprovers.getAs()).isEqualTo(DEFAULT_AS);
        assertThat(testLeaveRequestApprovers.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testLeaveRequestApprovers.getApproverGroup()).isEqualTo(DEFAULT_APPROVER_GROUP);
        assertThat(testLeaveRequestApprovers.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testLeaveRequestApprovers.getStatusDate()).isEqualTo(DEFAULT_STATUS_DATE);
        assertThat(testLeaveRequestApprovers.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testLeaveRequestApprovers.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaveRequestApprovers.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testLeaveRequestApprovers.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateLeaveRequestApproversWithPatch() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        int databaseSizeBeforeUpdate = leaveRequestApproversRepository.findAll().size();

        // Update the leaveRequestApprovers using partial update
        LeaveRequestApprovers partialUpdatedLeaveRequestApprovers = new LeaveRequestApprovers();
        partialUpdatedLeaveRequestApprovers.setId(leaveRequestApprovers.getId());

        partialUpdatedLeaveRequestApprovers
            .reference(UPDATED_REFERENCE)
            .as(UPDATED_AS)
            .comments(UPDATED_COMMENTS)
            .approverGroup(UPDATED_APPROVER_GROUP)
            .priority(UPDATED_PRIORITY)
            .statusDate(UPDATED_STATUS_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restLeaveRequestApproversMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveRequestApprovers.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveRequestApprovers))
            )
            .andExpect(status().isOk());

        // Validate the LeaveRequestApprovers in the database
        List<LeaveRequestApprovers> leaveRequestApproversList = leaveRequestApproversRepository.findAll();
        assertThat(leaveRequestApproversList).hasSize(databaseSizeBeforeUpdate);
        LeaveRequestApprovers testLeaveRequestApprovers = leaveRequestApproversList.get(leaveRequestApproversList.size() - 1);
        assertThat(testLeaveRequestApprovers.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testLeaveRequestApprovers.getAs()).isEqualTo(UPDATED_AS);
        assertThat(testLeaveRequestApprovers.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testLeaveRequestApprovers.getApproverGroup()).isEqualTo(UPDATED_APPROVER_GROUP);
        assertThat(testLeaveRequestApprovers.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testLeaveRequestApprovers.getStatusDate()).isEqualTo(UPDATED_STATUS_DATE);
        assertThat(testLeaveRequestApprovers.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveRequestApprovers.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaveRequestApprovers.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testLeaveRequestApprovers.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingLeaveRequestApprovers() throws Exception {
        int databaseSizeBeforeUpdate = leaveRequestApproversRepository.findAll().size();
        leaveRequestApprovers.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveRequestApproversMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leaveRequestApprovers.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequestApprovers))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveRequestApprovers in the database
        List<LeaveRequestApprovers> leaveRequestApproversList = leaveRequestApproversRepository.findAll();
        assertThat(leaveRequestApproversList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeaveRequestApprovers() throws Exception {
        int databaseSizeBeforeUpdate = leaveRequestApproversRepository.findAll().size();
        leaveRequestApprovers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveRequestApproversMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequestApprovers))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveRequestApprovers in the database
        List<LeaveRequestApprovers> leaveRequestApproversList = leaveRequestApproversRepository.findAll();
        assertThat(leaveRequestApproversList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeaveRequestApprovers() throws Exception {
        int databaseSizeBeforeUpdate = leaveRequestApproversRepository.findAll().size();
        leaveRequestApprovers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveRequestApproversMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequestApprovers))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveRequestApprovers in the database
        List<LeaveRequestApprovers> leaveRequestApproversList = leaveRequestApproversRepository.findAll();
        assertThat(leaveRequestApproversList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLeaveRequestApprovers() throws Exception {
        // Initialize the database
        leaveRequestApproversRepository.saveAndFlush(leaveRequestApprovers);

        int databaseSizeBeforeDelete = leaveRequestApproversRepository.findAll().size();

        // Delete the leaveRequestApprovers
        restLeaveRequestApproversMockMvc
            .perform(delete(ENTITY_API_URL_ID, leaveRequestApprovers.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeaveRequestApprovers> leaveRequestApproversList = leaveRequestApproversRepository.findAll();
        assertThat(leaveRequestApproversList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
