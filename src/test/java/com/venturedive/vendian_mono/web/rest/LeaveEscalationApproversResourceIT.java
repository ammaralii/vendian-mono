package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Attributes;
import com.venturedive.vendian_mono.domain.LeaveEscalationApprovers;
import com.venturedive.vendian_mono.domain.LeaveEscalationCriterias;
import com.venturedive.vendian_mono.repository.LeaveEscalationApproversRepository;
import com.venturedive.vendian_mono.service.criteria.LeaveEscalationApproversCriteria;
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
 * Integration tests for the {@link LeaveEscalationApproversResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeaveEscalationApproversResourceIT {

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

    private static final String ENTITY_API_URL = "/api/leave-escalation-approvers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LeaveEscalationApproversRepository leaveEscalationApproversRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeaveEscalationApproversMockMvc;

    private LeaveEscalationApprovers leaveEscalationApprovers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveEscalationApprovers createEntity(EntityManager em) {
        LeaveEscalationApprovers leaveEscalationApprovers = new LeaveEscalationApprovers()
            .source(DEFAULT_SOURCE)
            .minApprovals(DEFAULT_MIN_APPROVALS)
            .priority(DEFAULT_PRIORITY)
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
        leaveEscalationApprovers.setLeaveEscalationCriteria(leaveEscalationCriterias);
        // Add required entity
        Attributes attributes;
        if (TestUtil.findAll(em, Attributes.class).isEmpty()) {
            attributes = AttributesResourceIT.createEntity(em);
            em.persist(attributes);
            em.flush();
        } else {
            attributes = TestUtil.findAll(em, Attributes.class).get(0);
        }
        leaveEscalationApprovers.setAttribute(attributes);
        return leaveEscalationApprovers;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveEscalationApprovers createUpdatedEntity(EntityManager em) {
        LeaveEscalationApprovers leaveEscalationApprovers = new LeaveEscalationApprovers()
            .source(UPDATED_SOURCE)
            .minApprovals(UPDATED_MIN_APPROVALS)
            .priority(UPDATED_PRIORITY)
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
        leaveEscalationApprovers.setLeaveEscalationCriteria(leaveEscalationCriterias);
        // Add required entity
        Attributes attributes;
        if (TestUtil.findAll(em, Attributes.class).isEmpty()) {
            attributes = AttributesResourceIT.createUpdatedEntity(em);
            em.persist(attributes);
            em.flush();
        } else {
            attributes = TestUtil.findAll(em, Attributes.class).get(0);
        }
        leaveEscalationApprovers.setAttribute(attributes);
        return leaveEscalationApprovers;
    }

    @BeforeEach
    public void initTest() {
        leaveEscalationApprovers = createEntity(em);
    }

    @Test
    @Transactional
    void createLeaveEscalationApprovers() throws Exception {
        int databaseSizeBeforeCreate = leaveEscalationApproversRepository.findAll().size();
        // Create the LeaveEscalationApprovers
        restLeaveEscalationApproversMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveEscalationApprovers))
            )
            .andExpect(status().isCreated());

        // Validate the LeaveEscalationApprovers in the database
        List<LeaveEscalationApprovers> leaveEscalationApproversList = leaveEscalationApproversRepository.findAll();
        assertThat(leaveEscalationApproversList).hasSize(databaseSizeBeforeCreate + 1);
        LeaveEscalationApprovers testLeaveEscalationApprovers = leaveEscalationApproversList.get(leaveEscalationApproversList.size() - 1);
        assertThat(testLeaveEscalationApprovers.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testLeaveEscalationApprovers.getMinApprovals()).isEqualTo(DEFAULT_MIN_APPROVALS);
        assertThat(testLeaveEscalationApprovers.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testLeaveEscalationApprovers.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testLeaveEscalationApprovers.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testLeaveEscalationApprovers.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testLeaveEscalationApprovers.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testLeaveEscalationApprovers.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createLeaveEscalationApproversWithExistingId() throws Exception {
        // Create the LeaveEscalationApprovers with an existing ID
        leaveEscalationApprovers.setId(1L);

        int databaseSizeBeforeCreate = leaveEscalationApproversRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeaveEscalationApproversMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveEscalationApprovers))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveEscalationApprovers in the database
        List<LeaveEscalationApprovers> leaveEscalationApproversList = leaveEscalationApproversRepository.findAll();
        assertThat(leaveEscalationApproversList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSourceIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveEscalationApproversRepository.findAll().size();
        // set the field null
        leaveEscalationApprovers.setSource(null);

        // Create the LeaveEscalationApprovers, which fails.

        restLeaveEscalationApproversMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveEscalationApprovers))
            )
            .andExpect(status().isBadRequest());

        List<LeaveEscalationApprovers> leaveEscalationApproversList = leaveEscalationApproversRepository.findAll();
        assertThat(leaveEscalationApproversList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriorityIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveEscalationApproversRepository.findAll().size();
        // set the field null
        leaveEscalationApprovers.setPriority(null);

        // Create the LeaveEscalationApprovers, which fails.

        restLeaveEscalationApproversMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveEscalationApprovers))
            )
            .andExpect(status().isBadRequest());

        List<LeaveEscalationApprovers> leaveEscalationApproversList = leaveEscalationApproversRepository.findAll();
        assertThat(leaveEscalationApproversList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEffDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveEscalationApproversRepository.findAll().size();
        // set the field null
        leaveEscalationApprovers.setEffDate(null);

        // Create the LeaveEscalationApprovers, which fails.

        restLeaveEscalationApproversMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveEscalationApprovers))
            )
            .andExpect(status().isBadRequest());

        List<LeaveEscalationApprovers> leaveEscalationApproversList = leaveEscalationApproversRepository.findAll();
        assertThat(leaveEscalationApproversList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveEscalationApproversRepository.findAll().size();
        // set the field null
        leaveEscalationApprovers.setCreatedAt(null);

        // Create the LeaveEscalationApprovers, which fails.

        restLeaveEscalationApproversMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveEscalationApprovers))
            )
            .andExpect(status().isBadRequest());

        List<LeaveEscalationApprovers> leaveEscalationApproversList = leaveEscalationApproversRepository.findAll();
        assertThat(leaveEscalationApproversList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveEscalationApproversRepository.findAll().size();
        // set the field null
        leaveEscalationApprovers.setUpdatedAt(null);

        // Create the LeaveEscalationApprovers, which fails.

        restLeaveEscalationApproversMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveEscalationApprovers))
            )
            .andExpect(status().isBadRequest());

        List<LeaveEscalationApprovers> leaveEscalationApproversList = leaveEscalationApproversRepository.findAll();
        assertThat(leaveEscalationApproversList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveEscalationApproversRepository.findAll().size();
        // set the field null
        leaveEscalationApprovers.setVersion(null);

        // Create the LeaveEscalationApprovers, which fails.

        restLeaveEscalationApproversMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveEscalationApprovers))
            )
            .andExpect(status().isBadRequest());

        List<LeaveEscalationApprovers> leaveEscalationApproversList = leaveEscalationApproversRepository.findAll();
        assertThat(leaveEscalationApproversList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApprovers() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get all the leaveEscalationApproversList
        restLeaveEscalationApproversMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveEscalationApprovers.getId().intValue())))
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
    void getLeaveEscalationApprovers() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get the leaveEscalationApprovers
        restLeaveEscalationApproversMockMvc
            .perform(get(ENTITY_API_URL_ID, leaveEscalationApprovers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leaveEscalationApprovers.getId().intValue()))
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
    void getLeaveEscalationApproversByIdFiltering() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        Long id = leaveEscalationApprovers.getId();

        defaultLeaveEscalationApproversShouldBeFound("id.equals=" + id);
        defaultLeaveEscalationApproversShouldNotBeFound("id.notEquals=" + id);

        defaultLeaveEscalationApproversShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLeaveEscalationApproversShouldNotBeFound("id.greaterThan=" + id);

        defaultLeaveEscalationApproversShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLeaveEscalationApproversShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversBySourceIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get all the leaveEscalationApproversList where source equals to DEFAULT_SOURCE
        defaultLeaveEscalationApproversShouldBeFound("source.equals=" + DEFAULT_SOURCE);

        // Get all the leaveEscalationApproversList where source equals to UPDATED_SOURCE
        defaultLeaveEscalationApproversShouldNotBeFound("source.equals=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversBySourceIsInShouldWork() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get all the leaveEscalationApproversList where source in DEFAULT_SOURCE or UPDATED_SOURCE
        defaultLeaveEscalationApproversShouldBeFound("source.in=" + DEFAULT_SOURCE + "," + UPDATED_SOURCE);

        // Get all the leaveEscalationApproversList where source equals to UPDATED_SOURCE
        defaultLeaveEscalationApproversShouldNotBeFound("source.in=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversBySourceIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get all the leaveEscalationApproversList where source is not null
        defaultLeaveEscalationApproversShouldBeFound("source.specified=true");

        // Get all the leaveEscalationApproversList where source is null
        defaultLeaveEscalationApproversShouldNotBeFound("source.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversBySourceContainsSomething() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get all the leaveEscalationApproversList where source contains DEFAULT_SOURCE
        defaultLeaveEscalationApproversShouldBeFound("source.contains=" + DEFAULT_SOURCE);

        // Get all the leaveEscalationApproversList where source contains UPDATED_SOURCE
        defaultLeaveEscalationApproversShouldNotBeFound("source.contains=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversBySourceNotContainsSomething() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get all the leaveEscalationApproversList where source does not contain DEFAULT_SOURCE
        defaultLeaveEscalationApproversShouldNotBeFound("source.doesNotContain=" + DEFAULT_SOURCE);

        // Get all the leaveEscalationApproversList where source does not contain UPDATED_SOURCE
        defaultLeaveEscalationApproversShouldBeFound("source.doesNotContain=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversByMinApprovalsIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get all the leaveEscalationApproversList where minApprovals equals to DEFAULT_MIN_APPROVALS
        defaultLeaveEscalationApproversShouldBeFound("minApprovals.equals=" + DEFAULT_MIN_APPROVALS);

        // Get all the leaveEscalationApproversList where minApprovals equals to UPDATED_MIN_APPROVALS
        defaultLeaveEscalationApproversShouldNotBeFound("minApprovals.equals=" + UPDATED_MIN_APPROVALS);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversByMinApprovalsIsInShouldWork() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get all the leaveEscalationApproversList where minApprovals in DEFAULT_MIN_APPROVALS or UPDATED_MIN_APPROVALS
        defaultLeaveEscalationApproversShouldBeFound("minApprovals.in=" + DEFAULT_MIN_APPROVALS + "," + UPDATED_MIN_APPROVALS);

        // Get all the leaveEscalationApproversList where minApprovals equals to UPDATED_MIN_APPROVALS
        defaultLeaveEscalationApproversShouldNotBeFound("minApprovals.in=" + UPDATED_MIN_APPROVALS);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversByMinApprovalsIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get all the leaveEscalationApproversList where minApprovals is not null
        defaultLeaveEscalationApproversShouldBeFound("minApprovals.specified=true");

        // Get all the leaveEscalationApproversList where minApprovals is null
        defaultLeaveEscalationApproversShouldNotBeFound("minApprovals.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversByMinApprovalsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get all the leaveEscalationApproversList where minApprovals is greater than or equal to DEFAULT_MIN_APPROVALS
        defaultLeaveEscalationApproversShouldBeFound("minApprovals.greaterThanOrEqual=" + DEFAULT_MIN_APPROVALS);

        // Get all the leaveEscalationApproversList where minApprovals is greater than or equal to UPDATED_MIN_APPROVALS
        defaultLeaveEscalationApproversShouldNotBeFound("minApprovals.greaterThanOrEqual=" + UPDATED_MIN_APPROVALS);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversByMinApprovalsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get all the leaveEscalationApproversList where minApprovals is less than or equal to DEFAULT_MIN_APPROVALS
        defaultLeaveEscalationApproversShouldBeFound("minApprovals.lessThanOrEqual=" + DEFAULT_MIN_APPROVALS);

        // Get all the leaveEscalationApproversList where minApprovals is less than or equal to SMALLER_MIN_APPROVALS
        defaultLeaveEscalationApproversShouldNotBeFound("minApprovals.lessThanOrEqual=" + SMALLER_MIN_APPROVALS);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversByMinApprovalsIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get all the leaveEscalationApproversList where minApprovals is less than DEFAULT_MIN_APPROVALS
        defaultLeaveEscalationApproversShouldNotBeFound("minApprovals.lessThan=" + DEFAULT_MIN_APPROVALS);

        // Get all the leaveEscalationApproversList where minApprovals is less than UPDATED_MIN_APPROVALS
        defaultLeaveEscalationApproversShouldBeFound("minApprovals.lessThan=" + UPDATED_MIN_APPROVALS);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversByMinApprovalsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get all the leaveEscalationApproversList where minApprovals is greater than DEFAULT_MIN_APPROVALS
        defaultLeaveEscalationApproversShouldNotBeFound("minApprovals.greaterThan=" + DEFAULT_MIN_APPROVALS);

        // Get all the leaveEscalationApproversList where minApprovals is greater than SMALLER_MIN_APPROVALS
        defaultLeaveEscalationApproversShouldBeFound("minApprovals.greaterThan=" + SMALLER_MIN_APPROVALS);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversByPriorityIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get all the leaveEscalationApproversList where priority equals to DEFAULT_PRIORITY
        defaultLeaveEscalationApproversShouldBeFound("priority.equals=" + DEFAULT_PRIORITY);

        // Get all the leaveEscalationApproversList where priority equals to UPDATED_PRIORITY
        defaultLeaveEscalationApproversShouldNotBeFound("priority.equals=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversByPriorityIsInShouldWork() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get all the leaveEscalationApproversList where priority in DEFAULT_PRIORITY or UPDATED_PRIORITY
        defaultLeaveEscalationApproversShouldBeFound("priority.in=" + DEFAULT_PRIORITY + "," + UPDATED_PRIORITY);

        // Get all the leaveEscalationApproversList where priority equals to UPDATED_PRIORITY
        defaultLeaveEscalationApproversShouldNotBeFound("priority.in=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversByPriorityIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get all the leaveEscalationApproversList where priority is not null
        defaultLeaveEscalationApproversShouldBeFound("priority.specified=true");

        // Get all the leaveEscalationApproversList where priority is null
        defaultLeaveEscalationApproversShouldNotBeFound("priority.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversByPriorityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get all the leaveEscalationApproversList where priority is greater than or equal to DEFAULT_PRIORITY
        defaultLeaveEscalationApproversShouldBeFound("priority.greaterThanOrEqual=" + DEFAULT_PRIORITY);

        // Get all the leaveEscalationApproversList where priority is greater than or equal to UPDATED_PRIORITY
        defaultLeaveEscalationApproversShouldNotBeFound("priority.greaterThanOrEqual=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversByPriorityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get all the leaveEscalationApproversList where priority is less than or equal to DEFAULT_PRIORITY
        defaultLeaveEscalationApproversShouldBeFound("priority.lessThanOrEqual=" + DEFAULT_PRIORITY);

        // Get all the leaveEscalationApproversList where priority is less than or equal to SMALLER_PRIORITY
        defaultLeaveEscalationApproversShouldNotBeFound("priority.lessThanOrEqual=" + SMALLER_PRIORITY);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversByPriorityIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get all the leaveEscalationApproversList where priority is less than DEFAULT_PRIORITY
        defaultLeaveEscalationApproversShouldNotBeFound("priority.lessThan=" + DEFAULT_PRIORITY);

        // Get all the leaveEscalationApproversList where priority is less than UPDATED_PRIORITY
        defaultLeaveEscalationApproversShouldBeFound("priority.lessThan=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversByPriorityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get all the leaveEscalationApproversList where priority is greater than DEFAULT_PRIORITY
        defaultLeaveEscalationApproversShouldNotBeFound("priority.greaterThan=" + DEFAULT_PRIORITY);

        // Get all the leaveEscalationApproversList where priority is greater than SMALLER_PRIORITY
        defaultLeaveEscalationApproversShouldBeFound("priority.greaterThan=" + SMALLER_PRIORITY);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversByEffDateIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get all the leaveEscalationApproversList where effDate equals to DEFAULT_EFF_DATE
        defaultLeaveEscalationApproversShouldBeFound("effDate.equals=" + DEFAULT_EFF_DATE);

        // Get all the leaveEscalationApproversList where effDate equals to UPDATED_EFF_DATE
        defaultLeaveEscalationApproversShouldNotBeFound("effDate.equals=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversByEffDateIsInShouldWork() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get all the leaveEscalationApproversList where effDate in DEFAULT_EFF_DATE or UPDATED_EFF_DATE
        defaultLeaveEscalationApproversShouldBeFound("effDate.in=" + DEFAULT_EFF_DATE + "," + UPDATED_EFF_DATE);

        // Get all the leaveEscalationApproversList where effDate equals to UPDATED_EFF_DATE
        defaultLeaveEscalationApproversShouldNotBeFound("effDate.in=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversByEffDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get all the leaveEscalationApproversList where effDate is not null
        defaultLeaveEscalationApproversShouldBeFound("effDate.specified=true");

        // Get all the leaveEscalationApproversList where effDate is null
        defaultLeaveEscalationApproversShouldNotBeFound("effDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get all the leaveEscalationApproversList where createdAt equals to DEFAULT_CREATED_AT
        defaultLeaveEscalationApproversShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the leaveEscalationApproversList where createdAt equals to UPDATED_CREATED_AT
        defaultLeaveEscalationApproversShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get all the leaveEscalationApproversList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultLeaveEscalationApproversShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the leaveEscalationApproversList where createdAt equals to UPDATED_CREATED_AT
        defaultLeaveEscalationApproversShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get all the leaveEscalationApproversList where createdAt is not null
        defaultLeaveEscalationApproversShouldBeFound("createdAt.specified=true");

        // Get all the leaveEscalationApproversList where createdAt is null
        defaultLeaveEscalationApproversShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get all the leaveEscalationApproversList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultLeaveEscalationApproversShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the leaveEscalationApproversList where updatedAt equals to UPDATED_UPDATED_AT
        defaultLeaveEscalationApproversShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get all the leaveEscalationApproversList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultLeaveEscalationApproversShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the leaveEscalationApproversList where updatedAt equals to UPDATED_UPDATED_AT
        defaultLeaveEscalationApproversShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get all the leaveEscalationApproversList where updatedAt is not null
        defaultLeaveEscalationApproversShouldBeFound("updatedAt.specified=true");

        // Get all the leaveEscalationApproversList where updatedAt is null
        defaultLeaveEscalationApproversShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get all the leaveEscalationApproversList where endDate equals to DEFAULT_END_DATE
        defaultLeaveEscalationApproversShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the leaveEscalationApproversList where endDate equals to UPDATED_END_DATE
        defaultLeaveEscalationApproversShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get all the leaveEscalationApproversList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultLeaveEscalationApproversShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the leaveEscalationApproversList where endDate equals to UPDATED_END_DATE
        defaultLeaveEscalationApproversShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get all the leaveEscalationApproversList where endDate is not null
        defaultLeaveEscalationApproversShouldBeFound("endDate.specified=true");

        // Get all the leaveEscalationApproversList where endDate is null
        defaultLeaveEscalationApproversShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get all the leaveEscalationApproversList where version equals to DEFAULT_VERSION
        defaultLeaveEscalationApproversShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the leaveEscalationApproversList where version equals to UPDATED_VERSION
        defaultLeaveEscalationApproversShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get all the leaveEscalationApproversList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultLeaveEscalationApproversShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the leaveEscalationApproversList where version equals to UPDATED_VERSION
        defaultLeaveEscalationApproversShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get all the leaveEscalationApproversList where version is not null
        defaultLeaveEscalationApproversShouldBeFound("version.specified=true");

        // Get all the leaveEscalationApproversList where version is null
        defaultLeaveEscalationApproversShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get all the leaveEscalationApproversList where version is greater than or equal to DEFAULT_VERSION
        defaultLeaveEscalationApproversShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the leaveEscalationApproversList where version is greater than or equal to UPDATED_VERSION
        defaultLeaveEscalationApproversShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get all the leaveEscalationApproversList where version is less than or equal to DEFAULT_VERSION
        defaultLeaveEscalationApproversShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the leaveEscalationApproversList where version is less than or equal to SMALLER_VERSION
        defaultLeaveEscalationApproversShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get all the leaveEscalationApproversList where version is less than DEFAULT_VERSION
        defaultLeaveEscalationApproversShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the leaveEscalationApproversList where version is less than UPDATED_VERSION
        defaultLeaveEscalationApproversShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        // Get all the leaveEscalationApproversList where version is greater than DEFAULT_VERSION
        defaultLeaveEscalationApproversShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the leaveEscalationApproversList where version is greater than SMALLER_VERSION
        defaultLeaveEscalationApproversShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversByLeaveEscalationCriteriaIsEqualToSomething() throws Exception {
        LeaveEscalationCriterias leaveEscalationCriteria;
        if (TestUtil.findAll(em, LeaveEscalationCriterias.class).isEmpty()) {
            leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);
            leaveEscalationCriteria = LeaveEscalationCriteriasResourceIT.createEntity(em);
        } else {
            leaveEscalationCriteria = TestUtil.findAll(em, LeaveEscalationCriterias.class).get(0);
        }
        em.persist(leaveEscalationCriteria);
        em.flush();
        leaveEscalationApprovers.setLeaveEscalationCriteria(leaveEscalationCriteria);
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);
        Long leaveEscalationCriteriaId = leaveEscalationCriteria.getId();

        // Get all the leaveEscalationApproversList where leaveEscalationCriteria equals to leaveEscalationCriteriaId
        defaultLeaveEscalationApproversShouldBeFound("leaveEscalationCriteriaId.equals=" + leaveEscalationCriteriaId);

        // Get all the leaveEscalationApproversList where leaveEscalationCriteria equals to (leaveEscalationCriteriaId + 1)
        defaultLeaveEscalationApproversShouldNotBeFound("leaveEscalationCriteriaId.equals=" + (leaveEscalationCriteriaId + 1));
    }

    @Test
    @Transactional
    void getAllLeaveEscalationApproversByAttributeIsEqualToSomething() throws Exception {
        Attributes attribute;
        if (TestUtil.findAll(em, Attributes.class).isEmpty()) {
            leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);
            attribute = AttributesResourceIT.createEntity(em);
        } else {
            attribute = TestUtil.findAll(em, Attributes.class).get(0);
        }
        em.persist(attribute);
        em.flush();
        leaveEscalationApprovers.setAttribute(attribute);
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);
        Long attributeId = attribute.getId();

        // Get all the leaveEscalationApproversList where attribute equals to attributeId
        defaultLeaveEscalationApproversShouldBeFound("attributeId.equals=" + attributeId);

        // Get all the leaveEscalationApproversList where attribute equals to (attributeId + 1)
        defaultLeaveEscalationApproversShouldNotBeFound("attributeId.equals=" + (attributeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLeaveEscalationApproversShouldBeFound(String filter) throws Exception {
        restLeaveEscalationApproversMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveEscalationApprovers.getId().intValue())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].minApprovals").value(hasItem(DEFAULT_MIN_APPROVALS)))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restLeaveEscalationApproversMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLeaveEscalationApproversShouldNotBeFound(String filter) throws Exception {
        restLeaveEscalationApproversMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLeaveEscalationApproversMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLeaveEscalationApprovers() throws Exception {
        // Get the leaveEscalationApprovers
        restLeaveEscalationApproversMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLeaveEscalationApprovers() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        int databaseSizeBeforeUpdate = leaveEscalationApproversRepository.findAll().size();

        // Update the leaveEscalationApprovers
        LeaveEscalationApprovers updatedLeaveEscalationApprovers = leaveEscalationApproversRepository
            .findById(leaveEscalationApprovers.getId())
            .get();
        // Disconnect from session so that the updates on updatedLeaveEscalationApprovers are not directly saved in db
        em.detach(updatedLeaveEscalationApprovers);
        updatedLeaveEscalationApprovers
            .source(UPDATED_SOURCE)
            .minApprovals(UPDATED_MIN_APPROVALS)
            .priority(UPDATED_PRIORITY)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restLeaveEscalationApproversMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLeaveEscalationApprovers.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLeaveEscalationApprovers))
            )
            .andExpect(status().isOk());

        // Validate the LeaveEscalationApprovers in the database
        List<LeaveEscalationApprovers> leaveEscalationApproversList = leaveEscalationApproversRepository.findAll();
        assertThat(leaveEscalationApproversList).hasSize(databaseSizeBeforeUpdate);
        LeaveEscalationApprovers testLeaveEscalationApprovers = leaveEscalationApproversList.get(leaveEscalationApproversList.size() - 1);
        assertThat(testLeaveEscalationApprovers.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testLeaveEscalationApprovers.getMinApprovals()).isEqualTo(UPDATED_MIN_APPROVALS);
        assertThat(testLeaveEscalationApprovers.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testLeaveEscalationApprovers.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testLeaveEscalationApprovers.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveEscalationApprovers.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaveEscalationApprovers.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testLeaveEscalationApprovers.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingLeaveEscalationApprovers() throws Exception {
        int databaseSizeBeforeUpdate = leaveEscalationApproversRepository.findAll().size();
        leaveEscalationApprovers.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveEscalationApproversMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leaveEscalationApprovers.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveEscalationApprovers))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveEscalationApprovers in the database
        List<LeaveEscalationApprovers> leaveEscalationApproversList = leaveEscalationApproversRepository.findAll();
        assertThat(leaveEscalationApproversList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeaveEscalationApprovers() throws Exception {
        int databaseSizeBeforeUpdate = leaveEscalationApproversRepository.findAll().size();
        leaveEscalationApprovers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveEscalationApproversMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveEscalationApprovers))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveEscalationApprovers in the database
        List<LeaveEscalationApprovers> leaveEscalationApproversList = leaveEscalationApproversRepository.findAll();
        assertThat(leaveEscalationApproversList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeaveEscalationApprovers() throws Exception {
        int databaseSizeBeforeUpdate = leaveEscalationApproversRepository.findAll().size();
        leaveEscalationApprovers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveEscalationApproversMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveEscalationApprovers))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveEscalationApprovers in the database
        List<LeaveEscalationApprovers> leaveEscalationApproversList = leaveEscalationApproversRepository.findAll();
        assertThat(leaveEscalationApproversList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeaveEscalationApproversWithPatch() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        int databaseSizeBeforeUpdate = leaveEscalationApproversRepository.findAll().size();

        // Update the leaveEscalationApprovers using partial update
        LeaveEscalationApprovers partialUpdatedLeaveEscalationApprovers = new LeaveEscalationApprovers();
        partialUpdatedLeaveEscalationApprovers.setId(leaveEscalationApprovers.getId());

        partialUpdatedLeaveEscalationApprovers.minApprovals(UPDATED_MIN_APPROVALS).effDate(UPDATED_EFF_DATE).createdAt(UPDATED_CREATED_AT);

        restLeaveEscalationApproversMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveEscalationApprovers.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveEscalationApprovers))
            )
            .andExpect(status().isOk());

        // Validate the LeaveEscalationApprovers in the database
        List<LeaveEscalationApprovers> leaveEscalationApproversList = leaveEscalationApproversRepository.findAll();
        assertThat(leaveEscalationApproversList).hasSize(databaseSizeBeforeUpdate);
        LeaveEscalationApprovers testLeaveEscalationApprovers = leaveEscalationApproversList.get(leaveEscalationApproversList.size() - 1);
        assertThat(testLeaveEscalationApprovers.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testLeaveEscalationApprovers.getMinApprovals()).isEqualTo(UPDATED_MIN_APPROVALS);
        assertThat(testLeaveEscalationApprovers.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testLeaveEscalationApprovers.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testLeaveEscalationApprovers.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveEscalationApprovers.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testLeaveEscalationApprovers.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testLeaveEscalationApprovers.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateLeaveEscalationApproversWithPatch() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        int databaseSizeBeforeUpdate = leaveEscalationApproversRepository.findAll().size();

        // Update the leaveEscalationApprovers using partial update
        LeaveEscalationApprovers partialUpdatedLeaveEscalationApprovers = new LeaveEscalationApprovers();
        partialUpdatedLeaveEscalationApprovers.setId(leaveEscalationApprovers.getId());

        partialUpdatedLeaveEscalationApprovers
            .source(UPDATED_SOURCE)
            .minApprovals(UPDATED_MIN_APPROVALS)
            .priority(UPDATED_PRIORITY)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restLeaveEscalationApproversMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveEscalationApprovers.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveEscalationApprovers))
            )
            .andExpect(status().isOk());

        // Validate the LeaveEscalationApprovers in the database
        List<LeaveEscalationApprovers> leaveEscalationApproversList = leaveEscalationApproversRepository.findAll();
        assertThat(leaveEscalationApproversList).hasSize(databaseSizeBeforeUpdate);
        LeaveEscalationApprovers testLeaveEscalationApprovers = leaveEscalationApproversList.get(leaveEscalationApproversList.size() - 1);
        assertThat(testLeaveEscalationApprovers.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testLeaveEscalationApprovers.getMinApprovals()).isEqualTo(UPDATED_MIN_APPROVALS);
        assertThat(testLeaveEscalationApprovers.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testLeaveEscalationApprovers.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testLeaveEscalationApprovers.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveEscalationApprovers.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaveEscalationApprovers.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testLeaveEscalationApprovers.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingLeaveEscalationApprovers() throws Exception {
        int databaseSizeBeforeUpdate = leaveEscalationApproversRepository.findAll().size();
        leaveEscalationApprovers.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveEscalationApproversMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leaveEscalationApprovers.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveEscalationApprovers))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveEscalationApprovers in the database
        List<LeaveEscalationApprovers> leaveEscalationApproversList = leaveEscalationApproversRepository.findAll();
        assertThat(leaveEscalationApproversList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeaveEscalationApprovers() throws Exception {
        int databaseSizeBeforeUpdate = leaveEscalationApproversRepository.findAll().size();
        leaveEscalationApprovers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveEscalationApproversMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveEscalationApprovers))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveEscalationApprovers in the database
        List<LeaveEscalationApprovers> leaveEscalationApproversList = leaveEscalationApproversRepository.findAll();
        assertThat(leaveEscalationApproversList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeaveEscalationApprovers() throws Exception {
        int databaseSizeBeforeUpdate = leaveEscalationApproversRepository.findAll().size();
        leaveEscalationApprovers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveEscalationApproversMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveEscalationApprovers))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveEscalationApprovers in the database
        List<LeaveEscalationApprovers> leaveEscalationApproversList = leaveEscalationApproversRepository.findAll();
        assertThat(leaveEscalationApproversList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLeaveEscalationApprovers() throws Exception {
        // Initialize the database
        leaveEscalationApproversRepository.saveAndFlush(leaveEscalationApprovers);

        int databaseSizeBeforeDelete = leaveEscalationApproversRepository.findAll().size();

        // Delete the leaveEscalationApprovers
        restLeaveEscalationApproversMockMvc
            .perform(delete(ENTITY_API_URL_ID, leaveEscalationApprovers.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeaveEscalationApprovers> leaveEscalationApproversList = leaveEscalationApproversRepository.findAll();
        assertThat(leaveEscalationApproversList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
