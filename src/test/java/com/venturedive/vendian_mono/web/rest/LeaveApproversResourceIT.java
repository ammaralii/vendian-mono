package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Attributes;
import com.venturedive.vendian_mono.domain.LeaveApprovalCriterias;
import com.venturedive.vendian_mono.domain.LeaveApprovers;
import com.venturedive.vendian_mono.repository.LeaveApproversRepository;
import com.venturedive.vendian_mono.service.criteria.LeaveApproversCriteria;
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
 * Integration tests for the {@link LeaveApproversResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeaveApproversResourceIT {

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final Integer DEFAULT_MIN_APPROVALS = 1;
    private static final Integer UPDATED_MIN_APPROVALS = 2;
    private static final Integer SMALLER_MIN_APPROVALS = 1 - 1;

    private static final Integer DEFAULT_PRIORITY = 1;
    private static final Integer UPDATED_PRIORITY = 2;
    private static final Integer SMALLER_PRIORITY = 1 - 1;

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

    private static final String ENTITY_API_URL = "/api/leave-approvers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LeaveApproversRepository leaveApproversRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeaveApproversMockMvc;

    private LeaveApprovers leaveApprovers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveApprovers createEntity(EntityManager em) {
        LeaveApprovers leaveApprovers = new LeaveApprovers()
            .source(DEFAULT_SOURCE)
            .minApprovals(DEFAULT_MIN_APPROVALS)
            .priority(DEFAULT_PRIORITY)
            .effDate(DEFAULT_EFF_DATE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .endDate(DEFAULT_END_DATE)
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
        leaveApprovers.setLeaveApprovalCriteria(leaveApprovalCriterias);
        // Add required entity
        Attributes attributes;
        if (TestUtil.findAll(em, Attributes.class).isEmpty()) {
            attributes = AttributesResourceIT.createEntity(em);
            em.persist(attributes);
            em.flush();
        } else {
            attributes = TestUtil.findAll(em, Attributes.class).get(0);
        }
        leaveApprovers.setAttribute(attributes);
        return leaveApprovers;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveApprovers createUpdatedEntity(EntityManager em) {
        LeaveApprovers leaveApprovers = new LeaveApprovers()
            .source(UPDATED_SOURCE)
            .minApprovals(UPDATED_MIN_APPROVALS)
            .priority(UPDATED_PRIORITY)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
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
        leaveApprovers.setLeaveApprovalCriteria(leaveApprovalCriterias);
        // Add required entity
        Attributes attributes;
        if (TestUtil.findAll(em, Attributes.class).isEmpty()) {
            attributes = AttributesResourceIT.createUpdatedEntity(em);
            em.persist(attributes);
            em.flush();
        } else {
            attributes = TestUtil.findAll(em, Attributes.class).get(0);
        }
        leaveApprovers.setAttribute(attributes);
        return leaveApprovers;
    }

    @BeforeEach
    public void initTest() {
        leaveApprovers = createEntity(em);
    }

    @Test
    @Transactional
    void createLeaveApprovers() throws Exception {
        int databaseSizeBeforeCreate = leaveApproversRepository.findAll().size();
        // Create the LeaveApprovers
        restLeaveApproversMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveApprovers))
            )
            .andExpect(status().isCreated());

        // Validate the LeaveApprovers in the database
        List<LeaveApprovers> leaveApproversList = leaveApproversRepository.findAll();
        assertThat(leaveApproversList).hasSize(databaseSizeBeforeCreate + 1);
        LeaveApprovers testLeaveApprovers = leaveApproversList.get(leaveApproversList.size() - 1);
        assertThat(testLeaveApprovers.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testLeaveApprovers.getMinApprovals()).isEqualTo(DEFAULT_MIN_APPROVALS);
        assertThat(testLeaveApprovers.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testLeaveApprovers.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testLeaveApprovers.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testLeaveApprovers.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testLeaveApprovers.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testLeaveApprovers.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createLeaveApproversWithExistingId() throws Exception {
        // Create the LeaveApprovers with an existing ID
        leaveApprovers.setId(1L);

        int databaseSizeBeforeCreate = leaveApproversRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeaveApproversMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveApprovers))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveApprovers in the database
        List<LeaveApprovers> leaveApproversList = leaveApproversRepository.findAll();
        assertThat(leaveApproversList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSourceIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveApproversRepository.findAll().size();
        // set the field null
        leaveApprovers.setSource(null);

        // Create the LeaveApprovers, which fails.

        restLeaveApproversMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveApprovers))
            )
            .andExpect(status().isBadRequest());

        List<LeaveApprovers> leaveApproversList = leaveApproversRepository.findAll();
        assertThat(leaveApproversList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriorityIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveApproversRepository.findAll().size();
        // set the field null
        leaveApprovers.setPriority(null);

        // Create the LeaveApprovers, which fails.

        restLeaveApproversMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveApprovers))
            )
            .andExpect(status().isBadRequest());

        List<LeaveApprovers> leaveApproversList = leaveApproversRepository.findAll();
        assertThat(leaveApproversList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEffDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveApproversRepository.findAll().size();
        // set the field null
        leaveApprovers.setEffDate(null);

        // Create the LeaveApprovers, which fails.

        restLeaveApproversMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveApprovers))
            )
            .andExpect(status().isBadRequest());

        List<LeaveApprovers> leaveApproversList = leaveApproversRepository.findAll();
        assertThat(leaveApproversList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveApproversRepository.findAll().size();
        // set the field null
        leaveApprovers.setCreatedAt(null);

        // Create the LeaveApprovers, which fails.

        restLeaveApproversMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveApprovers))
            )
            .andExpect(status().isBadRequest());

        List<LeaveApprovers> leaveApproversList = leaveApproversRepository.findAll();
        assertThat(leaveApproversList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveApproversRepository.findAll().size();
        // set the field null
        leaveApprovers.setUpdatedAt(null);

        // Create the LeaveApprovers, which fails.

        restLeaveApproversMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveApprovers))
            )
            .andExpect(status().isBadRequest());

        List<LeaveApprovers> leaveApproversList = leaveApproversRepository.findAll();
        assertThat(leaveApproversList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveApproversRepository.findAll().size();
        // set the field null
        leaveApprovers.setVersion(null);

        // Create the LeaveApprovers, which fails.

        restLeaveApproversMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveApprovers))
            )
            .andExpect(status().isBadRequest());

        List<LeaveApprovers> leaveApproversList = leaveApproversRepository.findAll();
        assertThat(leaveApproversList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLeaveApprovers() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get all the leaveApproversList
        restLeaveApproversMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveApprovers.getId().intValue())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].minApprovals").value(hasItem(DEFAULT_MIN_APPROVALS)))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getLeaveApprovers() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get the leaveApprovers
        restLeaveApproversMockMvc
            .perform(get(ENTITY_API_URL_ID, leaveApprovers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leaveApprovers.getId().intValue()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE))
            .andExpect(jsonPath("$.minApprovals").value(DEFAULT_MIN_APPROVALS))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY))
            .andExpect(jsonPath("$.effDate").value(DEFAULT_EFF_DATE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getLeaveApproversByIdFiltering() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        Long id = leaveApprovers.getId();

        defaultLeaveApproversShouldBeFound("id.equals=" + id);
        defaultLeaveApproversShouldNotBeFound("id.notEquals=" + id);

        defaultLeaveApproversShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLeaveApproversShouldNotBeFound("id.greaterThan=" + id);

        defaultLeaveApproversShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLeaveApproversShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLeaveApproversBySourceIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get all the leaveApproversList where source equals to DEFAULT_SOURCE
        defaultLeaveApproversShouldBeFound("source.equals=" + DEFAULT_SOURCE);

        // Get all the leaveApproversList where source equals to UPDATED_SOURCE
        defaultLeaveApproversShouldNotBeFound("source.equals=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    void getAllLeaveApproversBySourceIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get all the leaveApproversList where source in DEFAULT_SOURCE or UPDATED_SOURCE
        defaultLeaveApproversShouldBeFound("source.in=" + DEFAULT_SOURCE + "," + UPDATED_SOURCE);

        // Get all the leaveApproversList where source equals to UPDATED_SOURCE
        defaultLeaveApproversShouldNotBeFound("source.in=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    void getAllLeaveApproversBySourceIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get all the leaveApproversList where source is not null
        defaultLeaveApproversShouldBeFound("source.specified=true");

        // Get all the leaveApproversList where source is null
        defaultLeaveApproversShouldNotBeFound("source.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveApproversBySourceContainsSomething() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get all the leaveApproversList where source contains DEFAULT_SOURCE
        defaultLeaveApproversShouldBeFound("source.contains=" + DEFAULT_SOURCE);

        // Get all the leaveApproversList where source contains UPDATED_SOURCE
        defaultLeaveApproversShouldNotBeFound("source.contains=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    void getAllLeaveApproversBySourceNotContainsSomething() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get all the leaveApproversList where source does not contain DEFAULT_SOURCE
        defaultLeaveApproversShouldNotBeFound("source.doesNotContain=" + DEFAULT_SOURCE);

        // Get all the leaveApproversList where source does not contain UPDATED_SOURCE
        defaultLeaveApproversShouldBeFound("source.doesNotContain=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    void getAllLeaveApproversByMinApprovalsIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get all the leaveApproversList where minApprovals equals to DEFAULT_MIN_APPROVALS
        defaultLeaveApproversShouldBeFound("minApprovals.equals=" + DEFAULT_MIN_APPROVALS);

        // Get all the leaveApproversList where minApprovals equals to UPDATED_MIN_APPROVALS
        defaultLeaveApproversShouldNotBeFound("minApprovals.equals=" + UPDATED_MIN_APPROVALS);
    }

    @Test
    @Transactional
    void getAllLeaveApproversByMinApprovalsIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get all the leaveApproversList where minApprovals in DEFAULT_MIN_APPROVALS or UPDATED_MIN_APPROVALS
        defaultLeaveApproversShouldBeFound("minApprovals.in=" + DEFAULT_MIN_APPROVALS + "," + UPDATED_MIN_APPROVALS);

        // Get all the leaveApproversList where minApprovals equals to UPDATED_MIN_APPROVALS
        defaultLeaveApproversShouldNotBeFound("minApprovals.in=" + UPDATED_MIN_APPROVALS);
    }

    @Test
    @Transactional
    void getAllLeaveApproversByMinApprovalsIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get all the leaveApproversList where minApprovals is not null
        defaultLeaveApproversShouldBeFound("minApprovals.specified=true");

        // Get all the leaveApproversList where minApprovals is null
        defaultLeaveApproversShouldNotBeFound("minApprovals.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveApproversByMinApprovalsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get all the leaveApproversList where minApprovals is greater than or equal to DEFAULT_MIN_APPROVALS
        defaultLeaveApproversShouldBeFound("minApprovals.greaterThanOrEqual=" + DEFAULT_MIN_APPROVALS);

        // Get all the leaveApproversList where minApprovals is greater than or equal to UPDATED_MIN_APPROVALS
        defaultLeaveApproversShouldNotBeFound("minApprovals.greaterThanOrEqual=" + UPDATED_MIN_APPROVALS);
    }

    @Test
    @Transactional
    void getAllLeaveApproversByMinApprovalsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get all the leaveApproversList where minApprovals is less than or equal to DEFAULT_MIN_APPROVALS
        defaultLeaveApproversShouldBeFound("minApprovals.lessThanOrEqual=" + DEFAULT_MIN_APPROVALS);

        // Get all the leaveApproversList where minApprovals is less than or equal to SMALLER_MIN_APPROVALS
        defaultLeaveApproversShouldNotBeFound("minApprovals.lessThanOrEqual=" + SMALLER_MIN_APPROVALS);
    }

    @Test
    @Transactional
    void getAllLeaveApproversByMinApprovalsIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get all the leaveApproversList where minApprovals is less than DEFAULT_MIN_APPROVALS
        defaultLeaveApproversShouldNotBeFound("minApprovals.lessThan=" + DEFAULT_MIN_APPROVALS);

        // Get all the leaveApproversList where minApprovals is less than UPDATED_MIN_APPROVALS
        defaultLeaveApproversShouldBeFound("minApprovals.lessThan=" + UPDATED_MIN_APPROVALS);
    }

    @Test
    @Transactional
    void getAllLeaveApproversByMinApprovalsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get all the leaveApproversList where minApprovals is greater than DEFAULT_MIN_APPROVALS
        defaultLeaveApproversShouldNotBeFound("minApprovals.greaterThan=" + DEFAULT_MIN_APPROVALS);

        // Get all the leaveApproversList where minApprovals is greater than SMALLER_MIN_APPROVALS
        defaultLeaveApproversShouldBeFound("minApprovals.greaterThan=" + SMALLER_MIN_APPROVALS);
    }

    @Test
    @Transactional
    void getAllLeaveApproversByPriorityIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get all the leaveApproversList where priority equals to DEFAULT_PRIORITY
        defaultLeaveApproversShouldBeFound("priority.equals=" + DEFAULT_PRIORITY);

        // Get all the leaveApproversList where priority equals to UPDATED_PRIORITY
        defaultLeaveApproversShouldNotBeFound("priority.equals=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllLeaveApproversByPriorityIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get all the leaveApproversList where priority in DEFAULT_PRIORITY or UPDATED_PRIORITY
        defaultLeaveApproversShouldBeFound("priority.in=" + DEFAULT_PRIORITY + "," + UPDATED_PRIORITY);

        // Get all the leaveApproversList where priority equals to UPDATED_PRIORITY
        defaultLeaveApproversShouldNotBeFound("priority.in=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllLeaveApproversByPriorityIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get all the leaveApproversList where priority is not null
        defaultLeaveApproversShouldBeFound("priority.specified=true");

        // Get all the leaveApproversList where priority is null
        defaultLeaveApproversShouldNotBeFound("priority.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveApproversByPriorityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get all the leaveApproversList where priority is greater than or equal to DEFAULT_PRIORITY
        defaultLeaveApproversShouldBeFound("priority.greaterThanOrEqual=" + DEFAULT_PRIORITY);

        // Get all the leaveApproversList where priority is greater than or equal to UPDATED_PRIORITY
        defaultLeaveApproversShouldNotBeFound("priority.greaterThanOrEqual=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllLeaveApproversByPriorityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get all the leaveApproversList where priority is less than or equal to DEFAULT_PRIORITY
        defaultLeaveApproversShouldBeFound("priority.lessThanOrEqual=" + DEFAULT_PRIORITY);

        // Get all the leaveApproversList where priority is less than or equal to SMALLER_PRIORITY
        defaultLeaveApproversShouldNotBeFound("priority.lessThanOrEqual=" + SMALLER_PRIORITY);
    }

    @Test
    @Transactional
    void getAllLeaveApproversByPriorityIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get all the leaveApproversList where priority is less than DEFAULT_PRIORITY
        defaultLeaveApproversShouldNotBeFound("priority.lessThan=" + DEFAULT_PRIORITY);

        // Get all the leaveApproversList where priority is less than UPDATED_PRIORITY
        defaultLeaveApproversShouldBeFound("priority.lessThan=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllLeaveApproversByPriorityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get all the leaveApproversList where priority is greater than DEFAULT_PRIORITY
        defaultLeaveApproversShouldNotBeFound("priority.greaterThan=" + DEFAULT_PRIORITY);

        // Get all the leaveApproversList where priority is greater than SMALLER_PRIORITY
        defaultLeaveApproversShouldBeFound("priority.greaterThan=" + SMALLER_PRIORITY);
    }

    @Test
    @Transactional
    void getAllLeaveApproversByEffDateIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get all the leaveApproversList where effDate equals to DEFAULT_EFF_DATE
        defaultLeaveApproversShouldBeFound("effDate.equals=" + DEFAULT_EFF_DATE);

        // Get all the leaveApproversList where effDate equals to UPDATED_EFF_DATE
        defaultLeaveApproversShouldNotBeFound("effDate.equals=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveApproversByEffDateIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get all the leaveApproversList where effDate in DEFAULT_EFF_DATE or UPDATED_EFF_DATE
        defaultLeaveApproversShouldBeFound("effDate.in=" + DEFAULT_EFF_DATE + "," + UPDATED_EFF_DATE);

        // Get all the leaveApproversList where effDate equals to UPDATED_EFF_DATE
        defaultLeaveApproversShouldNotBeFound("effDate.in=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveApproversByEffDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get all the leaveApproversList where effDate is not null
        defaultLeaveApproversShouldBeFound("effDate.specified=true");

        // Get all the leaveApproversList where effDate is null
        defaultLeaveApproversShouldNotBeFound("effDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveApproversByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get all the leaveApproversList where createdAt equals to DEFAULT_CREATED_AT
        defaultLeaveApproversShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the leaveApproversList where createdAt equals to UPDATED_CREATED_AT
        defaultLeaveApproversShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveApproversByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get all the leaveApproversList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultLeaveApproversShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the leaveApproversList where createdAt equals to UPDATED_CREATED_AT
        defaultLeaveApproversShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveApproversByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get all the leaveApproversList where createdAt is not null
        defaultLeaveApproversShouldBeFound("createdAt.specified=true");

        // Get all the leaveApproversList where createdAt is null
        defaultLeaveApproversShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveApproversByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get all the leaveApproversList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultLeaveApproversShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the leaveApproversList where updatedAt equals to UPDATED_UPDATED_AT
        defaultLeaveApproversShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveApproversByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get all the leaveApproversList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultLeaveApproversShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the leaveApproversList where updatedAt equals to UPDATED_UPDATED_AT
        defaultLeaveApproversShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveApproversByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get all the leaveApproversList where updatedAt is not null
        defaultLeaveApproversShouldBeFound("updatedAt.specified=true");

        // Get all the leaveApproversList where updatedAt is null
        defaultLeaveApproversShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveApproversByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get all the leaveApproversList where endDate equals to DEFAULT_END_DATE
        defaultLeaveApproversShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the leaveApproversList where endDate equals to UPDATED_END_DATE
        defaultLeaveApproversShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveApproversByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get all the leaveApproversList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultLeaveApproversShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the leaveApproversList where endDate equals to UPDATED_END_DATE
        defaultLeaveApproversShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveApproversByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get all the leaveApproversList where endDate is not null
        defaultLeaveApproversShouldBeFound("endDate.specified=true");

        // Get all the leaveApproversList where endDate is null
        defaultLeaveApproversShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveApproversByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get all the leaveApproversList where version equals to DEFAULT_VERSION
        defaultLeaveApproversShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the leaveApproversList where version equals to UPDATED_VERSION
        defaultLeaveApproversShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveApproversByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get all the leaveApproversList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultLeaveApproversShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the leaveApproversList where version equals to UPDATED_VERSION
        defaultLeaveApproversShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveApproversByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get all the leaveApproversList where version is not null
        defaultLeaveApproversShouldBeFound("version.specified=true");

        // Get all the leaveApproversList where version is null
        defaultLeaveApproversShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveApproversByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get all the leaveApproversList where version is greater than or equal to DEFAULT_VERSION
        defaultLeaveApproversShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the leaveApproversList where version is greater than or equal to UPDATED_VERSION
        defaultLeaveApproversShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveApproversByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get all the leaveApproversList where version is less than or equal to DEFAULT_VERSION
        defaultLeaveApproversShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the leaveApproversList where version is less than or equal to SMALLER_VERSION
        defaultLeaveApproversShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveApproversByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get all the leaveApproversList where version is less than DEFAULT_VERSION
        defaultLeaveApproversShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the leaveApproversList where version is less than UPDATED_VERSION
        defaultLeaveApproversShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveApproversByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        // Get all the leaveApproversList where version is greater than DEFAULT_VERSION
        defaultLeaveApproversShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the leaveApproversList where version is greater than SMALLER_VERSION
        defaultLeaveApproversShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveApproversByLeaveApprovalCriteriaIsEqualToSomething() throws Exception {
        LeaveApprovalCriterias leaveApprovalCriteria;
        if (TestUtil.findAll(em, LeaveApprovalCriterias.class).isEmpty()) {
            leaveApproversRepository.saveAndFlush(leaveApprovers);
            leaveApprovalCriteria = LeaveApprovalCriteriasResourceIT.createEntity(em);
        } else {
            leaveApprovalCriteria = TestUtil.findAll(em, LeaveApprovalCriterias.class).get(0);
        }
        em.persist(leaveApprovalCriteria);
        em.flush();
        leaveApprovers.setLeaveApprovalCriteria(leaveApprovalCriteria);
        leaveApproversRepository.saveAndFlush(leaveApprovers);
        Long leaveApprovalCriteriaId = leaveApprovalCriteria.getId();

        // Get all the leaveApproversList where leaveApprovalCriteria equals to leaveApprovalCriteriaId
        defaultLeaveApproversShouldBeFound("leaveApprovalCriteriaId.equals=" + leaveApprovalCriteriaId);

        // Get all the leaveApproversList where leaveApprovalCriteria equals to (leaveApprovalCriteriaId + 1)
        defaultLeaveApproversShouldNotBeFound("leaveApprovalCriteriaId.equals=" + (leaveApprovalCriteriaId + 1));
    }

    @Test
    @Transactional
    void getAllLeaveApproversByAttributeIsEqualToSomething() throws Exception {
        Attributes attribute;
        if (TestUtil.findAll(em, Attributes.class).isEmpty()) {
            leaveApproversRepository.saveAndFlush(leaveApprovers);
            attribute = AttributesResourceIT.createEntity(em);
        } else {
            attribute = TestUtil.findAll(em, Attributes.class).get(0);
        }
        em.persist(attribute);
        em.flush();
        leaveApprovers.setAttribute(attribute);
        leaveApproversRepository.saveAndFlush(leaveApprovers);
        Long attributeId = attribute.getId();

        // Get all the leaveApproversList where attribute equals to attributeId
        defaultLeaveApproversShouldBeFound("attributeId.equals=" + attributeId);

        // Get all the leaveApproversList where attribute equals to (attributeId + 1)
        defaultLeaveApproversShouldNotBeFound("attributeId.equals=" + (attributeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLeaveApproversShouldBeFound(String filter) throws Exception {
        restLeaveApproversMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveApprovers.getId().intValue())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].minApprovals").value(hasItem(DEFAULT_MIN_APPROVALS)))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restLeaveApproversMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLeaveApproversShouldNotBeFound(String filter) throws Exception {
        restLeaveApproversMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLeaveApproversMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLeaveApprovers() throws Exception {
        // Get the leaveApprovers
        restLeaveApproversMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLeaveApprovers() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        int databaseSizeBeforeUpdate = leaveApproversRepository.findAll().size();

        // Update the leaveApprovers
        LeaveApprovers updatedLeaveApprovers = leaveApproversRepository.findById(leaveApprovers.getId()).get();
        // Disconnect from session so that the updates on updatedLeaveApprovers are not directly saved in db
        em.detach(updatedLeaveApprovers);
        updatedLeaveApprovers
            .source(UPDATED_SOURCE)
            .minApprovals(UPDATED_MIN_APPROVALS)
            .priority(UPDATED_PRIORITY)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restLeaveApproversMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLeaveApprovers.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLeaveApprovers))
            )
            .andExpect(status().isOk());

        // Validate the LeaveApprovers in the database
        List<LeaveApprovers> leaveApproversList = leaveApproversRepository.findAll();
        assertThat(leaveApproversList).hasSize(databaseSizeBeforeUpdate);
        LeaveApprovers testLeaveApprovers = leaveApproversList.get(leaveApproversList.size() - 1);
        assertThat(testLeaveApprovers.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testLeaveApprovers.getMinApprovals()).isEqualTo(UPDATED_MIN_APPROVALS);
        assertThat(testLeaveApprovers.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testLeaveApprovers.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testLeaveApprovers.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveApprovers.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaveApprovers.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testLeaveApprovers.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingLeaveApprovers() throws Exception {
        int databaseSizeBeforeUpdate = leaveApproversRepository.findAll().size();
        leaveApprovers.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveApproversMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leaveApprovers.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveApprovers))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveApprovers in the database
        List<LeaveApprovers> leaveApproversList = leaveApproversRepository.findAll();
        assertThat(leaveApproversList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeaveApprovers() throws Exception {
        int databaseSizeBeforeUpdate = leaveApproversRepository.findAll().size();
        leaveApprovers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveApproversMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveApprovers))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveApprovers in the database
        List<LeaveApprovers> leaveApproversList = leaveApproversRepository.findAll();
        assertThat(leaveApproversList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeaveApprovers() throws Exception {
        int databaseSizeBeforeUpdate = leaveApproversRepository.findAll().size();
        leaveApprovers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveApproversMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveApprovers))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveApprovers in the database
        List<LeaveApprovers> leaveApproversList = leaveApproversRepository.findAll();
        assertThat(leaveApproversList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeaveApproversWithPatch() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        int databaseSizeBeforeUpdate = leaveApproversRepository.findAll().size();

        // Update the leaveApprovers using partial update
        LeaveApprovers partialUpdatedLeaveApprovers = new LeaveApprovers();
        partialUpdatedLeaveApprovers.setId(leaveApprovers.getId());

        partialUpdatedLeaveApprovers
            .minApprovals(UPDATED_MIN_APPROVALS)
            .priority(UPDATED_PRIORITY)
            .createdAt(UPDATED_CREATED_AT)
            .version(UPDATED_VERSION);

        restLeaveApproversMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveApprovers.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveApprovers))
            )
            .andExpect(status().isOk());

        // Validate the LeaveApprovers in the database
        List<LeaveApprovers> leaveApproversList = leaveApproversRepository.findAll();
        assertThat(leaveApproversList).hasSize(databaseSizeBeforeUpdate);
        LeaveApprovers testLeaveApprovers = leaveApproversList.get(leaveApproversList.size() - 1);
        assertThat(testLeaveApprovers.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testLeaveApprovers.getMinApprovals()).isEqualTo(UPDATED_MIN_APPROVALS);
        assertThat(testLeaveApprovers.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testLeaveApprovers.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testLeaveApprovers.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveApprovers.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testLeaveApprovers.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testLeaveApprovers.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateLeaveApproversWithPatch() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        int databaseSizeBeforeUpdate = leaveApproversRepository.findAll().size();

        // Update the leaveApprovers using partial update
        LeaveApprovers partialUpdatedLeaveApprovers = new LeaveApprovers();
        partialUpdatedLeaveApprovers.setId(leaveApprovers.getId());

        partialUpdatedLeaveApprovers
            .source(UPDATED_SOURCE)
            .minApprovals(UPDATED_MIN_APPROVALS)
            .priority(UPDATED_PRIORITY)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restLeaveApproversMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveApprovers.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveApprovers))
            )
            .andExpect(status().isOk());

        // Validate the LeaveApprovers in the database
        List<LeaveApprovers> leaveApproversList = leaveApproversRepository.findAll();
        assertThat(leaveApproversList).hasSize(databaseSizeBeforeUpdate);
        LeaveApprovers testLeaveApprovers = leaveApproversList.get(leaveApproversList.size() - 1);
        assertThat(testLeaveApprovers.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testLeaveApprovers.getMinApprovals()).isEqualTo(UPDATED_MIN_APPROVALS);
        assertThat(testLeaveApprovers.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testLeaveApprovers.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testLeaveApprovers.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveApprovers.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaveApprovers.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testLeaveApprovers.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingLeaveApprovers() throws Exception {
        int databaseSizeBeforeUpdate = leaveApproversRepository.findAll().size();
        leaveApprovers.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveApproversMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leaveApprovers.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveApprovers))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveApprovers in the database
        List<LeaveApprovers> leaveApproversList = leaveApproversRepository.findAll();
        assertThat(leaveApproversList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeaveApprovers() throws Exception {
        int databaseSizeBeforeUpdate = leaveApproversRepository.findAll().size();
        leaveApprovers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveApproversMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveApprovers))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveApprovers in the database
        List<LeaveApprovers> leaveApproversList = leaveApproversRepository.findAll();
        assertThat(leaveApproversList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeaveApprovers() throws Exception {
        int databaseSizeBeforeUpdate = leaveApproversRepository.findAll().size();
        leaveApprovers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveApproversMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveApprovers))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveApprovers in the database
        List<LeaveApprovers> leaveApproversList = leaveApproversRepository.findAll();
        assertThat(leaveApproversList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLeaveApprovers() throws Exception {
        // Initialize the database
        leaveApproversRepository.saveAndFlush(leaveApprovers);

        int databaseSizeBeforeDelete = leaveApproversRepository.findAll().size();

        // Delete the leaveApprovers
        restLeaveApproversMockMvc
            .perform(delete(ENTITY_API_URL_ID, leaveApprovers.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeaveApprovers> leaveApproversList = leaveApproversRepository.findAll();
        assertThat(leaveApproversList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
