package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.LeaveRequestApproverFlows;
import com.venturedive.vendian_mono.domain.LeaveStatuses;
import com.venturedive.vendian_mono.repository.LeaveRequestApproverFlowsRepository;
import com.venturedive.vendian_mono.service.criteria.LeaveRequestApproverFlowsCriteria;
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
 * Integration tests for the {@link LeaveRequestApproverFlowsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeaveRequestApproverFlowsResourceIT {

    private static final String DEFAULT_APPROVALS = "AAA";
    private static final String UPDATED_APPROVALS = "BBB";

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

    private static final String ENTITY_API_URL = "/api/leave-request-approver-flows";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LeaveRequestApproverFlowsRepository leaveRequestApproverFlowsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeaveRequestApproverFlowsMockMvc;

    private LeaveRequestApproverFlows leaveRequestApproverFlows;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveRequestApproverFlows createEntity(EntityManager em) {
        LeaveRequestApproverFlows leaveRequestApproverFlows = new LeaveRequestApproverFlows()
            .approvals(DEFAULT_APPROVALS)
            .effDate(DEFAULT_EFF_DATE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .endDate(DEFAULT_END_DATE)
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
        leaveRequestApproverFlows.setApproverStatus(leaveStatuses);
        // Add required entity
        leaveRequestApproverFlows.setCurrentLeaveRequestStatus(leaveStatuses);
        // Add required entity
        leaveRequestApproverFlows.setNextLeaveRequestStatus(leaveStatuses);
        return leaveRequestApproverFlows;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveRequestApproverFlows createUpdatedEntity(EntityManager em) {
        LeaveRequestApproverFlows leaveRequestApproverFlows = new LeaveRequestApproverFlows()
            .approvals(UPDATED_APPROVALS)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
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
        leaveRequestApproverFlows.setApproverStatus(leaveStatuses);
        // Add required entity
        leaveRequestApproverFlows.setCurrentLeaveRequestStatus(leaveStatuses);
        // Add required entity
        leaveRequestApproverFlows.setNextLeaveRequestStatus(leaveStatuses);
        return leaveRequestApproverFlows;
    }

    @BeforeEach
    public void initTest() {
        leaveRequestApproverFlows = createEntity(em);
    }

    @Test
    @Transactional
    void createLeaveRequestApproverFlows() throws Exception {
        int databaseSizeBeforeCreate = leaveRequestApproverFlowsRepository.findAll().size();
        // Create the LeaveRequestApproverFlows
        restLeaveRequestApproverFlowsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequestApproverFlows))
            )
            .andExpect(status().isCreated());

        // Validate the LeaveRequestApproverFlows in the database
        List<LeaveRequestApproverFlows> leaveRequestApproverFlowsList = leaveRequestApproverFlowsRepository.findAll();
        assertThat(leaveRequestApproverFlowsList).hasSize(databaseSizeBeforeCreate + 1);
        LeaveRequestApproverFlows testLeaveRequestApproverFlows = leaveRequestApproverFlowsList.get(
            leaveRequestApproverFlowsList.size() - 1
        );
        assertThat(testLeaveRequestApproverFlows.getApprovals()).isEqualTo(DEFAULT_APPROVALS);
        assertThat(testLeaveRequestApproverFlows.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testLeaveRequestApproverFlows.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testLeaveRequestApproverFlows.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testLeaveRequestApproverFlows.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testLeaveRequestApproverFlows.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createLeaveRequestApproverFlowsWithExistingId() throws Exception {
        // Create the LeaveRequestApproverFlows with an existing ID
        leaveRequestApproverFlows.setId(1L);

        int databaseSizeBeforeCreate = leaveRequestApproverFlowsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeaveRequestApproverFlowsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequestApproverFlows))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveRequestApproverFlows in the database
        List<LeaveRequestApproverFlows> leaveRequestApproverFlowsList = leaveRequestApproverFlowsRepository.findAll();
        assertThat(leaveRequestApproverFlowsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkApprovalsIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveRequestApproverFlowsRepository.findAll().size();
        // set the field null
        leaveRequestApproverFlows.setApprovals(null);

        // Create the LeaveRequestApproverFlows, which fails.

        restLeaveRequestApproverFlowsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequestApproverFlows))
            )
            .andExpect(status().isBadRequest());

        List<LeaveRequestApproverFlows> leaveRequestApproverFlowsList = leaveRequestApproverFlowsRepository.findAll();
        assertThat(leaveRequestApproverFlowsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEffDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveRequestApproverFlowsRepository.findAll().size();
        // set the field null
        leaveRequestApproverFlows.setEffDate(null);

        // Create the LeaveRequestApproverFlows, which fails.

        restLeaveRequestApproverFlowsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequestApproverFlows))
            )
            .andExpect(status().isBadRequest());

        List<LeaveRequestApproverFlows> leaveRequestApproverFlowsList = leaveRequestApproverFlowsRepository.findAll();
        assertThat(leaveRequestApproverFlowsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveRequestApproverFlowsRepository.findAll().size();
        // set the field null
        leaveRequestApproverFlows.setCreatedAt(null);

        // Create the LeaveRequestApproverFlows, which fails.

        restLeaveRequestApproverFlowsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequestApproverFlows))
            )
            .andExpect(status().isBadRequest());

        List<LeaveRequestApproverFlows> leaveRequestApproverFlowsList = leaveRequestApproverFlowsRepository.findAll();
        assertThat(leaveRequestApproverFlowsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveRequestApproverFlowsRepository.findAll().size();
        // set the field null
        leaveRequestApproverFlows.setUpdatedAt(null);

        // Create the LeaveRequestApproverFlows, which fails.

        restLeaveRequestApproverFlowsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequestApproverFlows))
            )
            .andExpect(status().isBadRequest());

        List<LeaveRequestApproverFlows> leaveRequestApproverFlowsList = leaveRequestApproverFlowsRepository.findAll();
        assertThat(leaveRequestApproverFlowsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveRequestApproverFlowsRepository.findAll().size();
        // set the field null
        leaveRequestApproverFlows.setVersion(null);

        // Create the LeaveRequestApproverFlows, which fails.

        restLeaveRequestApproverFlowsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequestApproverFlows))
            )
            .andExpect(status().isBadRequest());

        List<LeaveRequestApproverFlows> leaveRequestApproverFlowsList = leaveRequestApproverFlowsRepository.findAll();
        assertThat(leaveRequestApproverFlowsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproverFlows() throws Exception {
        // Initialize the database
        leaveRequestApproverFlowsRepository.saveAndFlush(leaveRequestApproverFlows);

        // Get all the leaveRequestApproverFlowsList
        restLeaveRequestApproverFlowsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveRequestApproverFlows.getId().intValue())))
            .andExpect(jsonPath("$.[*].approvals").value(hasItem(DEFAULT_APPROVALS)))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getLeaveRequestApproverFlows() throws Exception {
        // Initialize the database
        leaveRequestApproverFlowsRepository.saveAndFlush(leaveRequestApproverFlows);

        // Get the leaveRequestApproverFlows
        restLeaveRequestApproverFlowsMockMvc
            .perform(get(ENTITY_API_URL_ID, leaveRequestApproverFlows.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leaveRequestApproverFlows.getId().intValue()))
            .andExpect(jsonPath("$.approvals").value(DEFAULT_APPROVALS))
            .andExpect(jsonPath("$.effDate").value(DEFAULT_EFF_DATE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getLeaveRequestApproverFlowsByIdFiltering() throws Exception {
        // Initialize the database
        leaveRequestApproverFlowsRepository.saveAndFlush(leaveRequestApproverFlows);

        Long id = leaveRequestApproverFlows.getId();

        defaultLeaveRequestApproverFlowsShouldBeFound("id.equals=" + id);
        defaultLeaveRequestApproverFlowsShouldNotBeFound("id.notEquals=" + id);

        defaultLeaveRequestApproverFlowsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLeaveRequestApproverFlowsShouldNotBeFound("id.greaterThan=" + id);

        defaultLeaveRequestApproverFlowsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLeaveRequestApproverFlowsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproverFlowsByApprovalsIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestApproverFlowsRepository.saveAndFlush(leaveRequestApproverFlows);

        // Get all the leaveRequestApproverFlowsList where approvals equals to DEFAULT_APPROVALS
        defaultLeaveRequestApproverFlowsShouldBeFound("approvals.equals=" + DEFAULT_APPROVALS);

        // Get all the leaveRequestApproverFlowsList where approvals equals to UPDATED_APPROVALS
        defaultLeaveRequestApproverFlowsShouldNotBeFound("approvals.equals=" + UPDATED_APPROVALS);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproverFlowsByApprovalsIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestApproverFlowsRepository.saveAndFlush(leaveRequestApproverFlows);

        // Get all the leaveRequestApproverFlowsList where approvals in DEFAULT_APPROVALS or UPDATED_APPROVALS
        defaultLeaveRequestApproverFlowsShouldBeFound("approvals.in=" + DEFAULT_APPROVALS + "," + UPDATED_APPROVALS);

        // Get all the leaveRequestApproverFlowsList where approvals equals to UPDATED_APPROVALS
        defaultLeaveRequestApproverFlowsShouldNotBeFound("approvals.in=" + UPDATED_APPROVALS);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproverFlowsByApprovalsIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestApproverFlowsRepository.saveAndFlush(leaveRequestApproverFlows);

        // Get all the leaveRequestApproverFlowsList where approvals is not null
        defaultLeaveRequestApproverFlowsShouldBeFound("approvals.specified=true");

        // Get all the leaveRequestApproverFlowsList where approvals is null
        defaultLeaveRequestApproverFlowsShouldNotBeFound("approvals.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproverFlowsByApprovalsContainsSomething() throws Exception {
        // Initialize the database
        leaveRequestApproverFlowsRepository.saveAndFlush(leaveRequestApproverFlows);

        // Get all the leaveRequestApproverFlowsList where approvals contains DEFAULT_APPROVALS
        defaultLeaveRequestApproverFlowsShouldBeFound("approvals.contains=" + DEFAULT_APPROVALS);

        // Get all the leaveRequestApproverFlowsList where approvals contains UPDATED_APPROVALS
        defaultLeaveRequestApproverFlowsShouldNotBeFound("approvals.contains=" + UPDATED_APPROVALS);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproverFlowsByApprovalsNotContainsSomething() throws Exception {
        // Initialize the database
        leaveRequestApproverFlowsRepository.saveAndFlush(leaveRequestApproverFlows);

        // Get all the leaveRequestApproverFlowsList where approvals does not contain DEFAULT_APPROVALS
        defaultLeaveRequestApproverFlowsShouldNotBeFound("approvals.doesNotContain=" + DEFAULT_APPROVALS);

        // Get all the leaveRequestApproverFlowsList where approvals does not contain UPDATED_APPROVALS
        defaultLeaveRequestApproverFlowsShouldBeFound("approvals.doesNotContain=" + UPDATED_APPROVALS);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproverFlowsByEffDateIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestApproverFlowsRepository.saveAndFlush(leaveRequestApproverFlows);

        // Get all the leaveRequestApproverFlowsList where effDate equals to DEFAULT_EFF_DATE
        defaultLeaveRequestApproverFlowsShouldBeFound("effDate.equals=" + DEFAULT_EFF_DATE);

        // Get all the leaveRequestApproverFlowsList where effDate equals to UPDATED_EFF_DATE
        defaultLeaveRequestApproverFlowsShouldNotBeFound("effDate.equals=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproverFlowsByEffDateIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestApproverFlowsRepository.saveAndFlush(leaveRequestApproverFlows);

        // Get all the leaveRequestApproverFlowsList where effDate in DEFAULT_EFF_DATE or UPDATED_EFF_DATE
        defaultLeaveRequestApproverFlowsShouldBeFound("effDate.in=" + DEFAULT_EFF_DATE + "," + UPDATED_EFF_DATE);

        // Get all the leaveRequestApproverFlowsList where effDate equals to UPDATED_EFF_DATE
        defaultLeaveRequestApproverFlowsShouldNotBeFound("effDate.in=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproverFlowsByEffDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestApproverFlowsRepository.saveAndFlush(leaveRequestApproverFlows);

        // Get all the leaveRequestApproverFlowsList where effDate is not null
        defaultLeaveRequestApproverFlowsShouldBeFound("effDate.specified=true");

        // Get all the leaveRequestApproverFlowsList where effDate is null
        defaultLeaveRequestApproverFlowsShouldNotBeFound("effDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproverFlowsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestApproverFlowsRepository.saveAndFlush(leaveRequestApproverFlows);

        // Get all the leaveRequestApproverFlowsList where createdAt equals to DEFAULT_CREATED_AT
        defaultLeaveRequestApproverFlowsShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the leaveRequestApproverFlowsList where createdAt equals to UPDATED_CREATED_AT
        defaultLeaveRequestApproverFlowsShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproverFlowsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestApproverFlowsRepository.saveAndFlush(leaveRequestApproverFlows);

        // Get all the leaveRequestApproverFlowsList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultLeaveRequestApproverFlowsShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the leaveRequestApproverFlowsList where createdAt equals to UPDATED_CREATED_AT
        defaultLeaveRequestApproverFlowsShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproverFlowsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestApproverFlowsRepository.saveAndFlush(leaveRequestApproverFlows);

        // Get all the leaveRequestApproverFlowsList where createdAt is not null
        defaultLeaveRequestApproverFlowsShouldBeFound("createdAt.specified=true");

        // Get all the leaveRequestApproverFlowsList where createdAt is null
        defaultLeaveRequestApproverFlowsShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproverFlowsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestApproverFlowsRepository.saveAndFlush(leaveRequestApproverFlows);

        // Get all the leaveRequestApproverFlowsList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultLeaveRequestApproverFlowsShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the leaveRequestApproverFlowsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultLeaveRequestApproverFlowsShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproverFlowsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestApproverFlowsRepository.saveAndFlush(leaveRequestApproverFlows);

        // Get all the leaveRequestApproverFlowsList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultLeaveRequestApproverFlowsShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the leaveRequestApproverFlowsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultLeaveRequestApproverFlowsShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproverFlowsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestApproverFlowsRepository.saveAndFlush(leaveRequestApproverFlows);

        // Get all the leaveRequestApproverFlowsList where updatedAt is not null
        defaultLeaveRequestApproverFlowsShouldBeFound("updatedAt.specified=true");

        // Get all the leaveRequestApproverFlowsList where updatedAt is null
        defaultLeaveRequestApproverFlowsShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproverFlowsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestApproverFlowsRepository.saveAndFlush(leaveRequestApproverFlows);

        // Get all the leaveRequestApproverFlowsList where endDate equals to DEFAULT_END_DATE
        defaultLeaveRequestApproverFlowsShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the leaveRequestApproverFlowsList where endDate equals to UPDATED_END_DATE
        defaultLeaveRequestApproverFlowsShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproverFlowsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestApproverFlowsRepository.saveAndFlush(leaveRequestApproverFlows);

        // Get all the leaveRequestApproverFlowsList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultLeaveRequestApproverFlowsShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the leaveRequestApproverFlowsList where endDate equals to UPDATED_END_DATE
        defaultLeaveRequestApproverFlowsShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproverFlowsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestApproverFlowsRepository.saveAndFlush(leaveRequestApproverFlows);

        // Get all the leaveRequestApproverFlowsList where endDate is not null
        defaultLeaveRequestApproverFlowsShouldBeFound("endDate.specified=true");

        // Get all the leaveRequestApproverFlowsList where endDate is null
        defaultLeaveRequestApproverFlowsShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproverFlowsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestApproverFlowsRepository.saveAndFlush(leaveRequestApproverFlows);

        // Get all the leaveRequestApproverFlowsList where version equals to DEFAULT_VERSION
        defaultLeaveRequestApproverFlowsShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the leaveRequestApproverFlowsList where version equals to UPDATED_VERSION
        defaultLeaveRequestApproverFlowsShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproverFlowsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestApproverFlowsRepository.saveAndFlush(leaveRequestApproverFlows);

        // Get all the leaveRequestApproverFlowsList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultLeaveRequestApproverFlowsShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the leaveRequestApproverFlowsList where version equals to UPDATED_VERSION
        defaultLeaveRequestApproverFlowsShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproverFlowsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestApproverFlowsRepository.saveAndFlush(leaveRequestApproverFlows);

        // Get all the leaveRequestApproverFlowsList where version is not null
        defaultLeaveRequestApproverFlowsShouldBeFound("version.specified=true");

        // Get all the leaveRequestApproverFlowsList where version is null
        defaultLeaveRequestApproverFlowsShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproverFlowsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestApproverFlowsRepository.saveAndFlush(leaveRequestApproverFlows);

        // Get all the leaveRequestApproverFlowsList where version is greater than or equal to DEFAULT_VERSION
        defaultLeaveRequestApproverFlowsShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the leaveRequestApproverFlowsList where version is greater than or equal to UPDATED_VERSION
        defaultLeaveRequestApproverFlowsShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproverFlowsByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestApproverFlowsRepository.saveAndFlush(leaveRequestApproverFlows);

        // Get all the leaveRequestApproverFlowsList where version is less than or equal to DEFAULT_VERSION
        defaultLeaveRequestApproverFlowsShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the leaveRequestApproverFlowsList where version is less than or equal to SMALLER_VERSION
        defaultLeaveRequestApproverFlowsShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproverFlowsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveRequestApproverFlowsRepository.saveAndFlush(leaveRequestApproverFlows);

        // Get all the leaveRequestApproverFlowsList where version is less than DEFAULT_VERSION
        defaultLeaveRequestApproverFlowsShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the leaveRequestApproverFlowsList where version is less than UPDATED_VERSION
        defaultLeaveRequestApproverFlowsShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproverFlowsByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveRequestApproverFlowsRepository.saveAndFlush(leaveRequestApproverFlows);

        // Get all the leaveRequestApproverFlowsList where version is greater than DEFAULT_VERSION
        defaultLeaveRequestApproverFlowsShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the leaveRequestApproverFlowsList where version is greater than SMALLER_VERSION
        defaultLeaveRequestApproverFlowsShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproverFlowsByApproverStatusIsEqualToSomething() throws Exception {
        LeaveStatuses approverStatus;
        if (TestUtil.findAll(em, LeaveStatuses.class).isEmpty()) {
            leaveRequestApproverFlowsRepository.saveAndFlush(leaveRequestApproverFlows);
            approverStatus = LeaveStatusesResourceIT.createEntity(em);
        } else {
            approverStatus = TestUtil.findAll(em, LeaveStatuses.class).get(0);
        }
        em.persist(approverStatus);
        em.flush();
        leaveRequestApproverFlows.setApproverStatus(approverStatus);
        leaveRequestApproverFlowsRepository.saveAndFlush(leaveRequestApproverFlows);
        Long approverStatusId = approverStatus.getId();

        // Get all the leaveRequestApproverFlowsList where approverStatus equals to approverStatusId
        defaultLeaveRequestApproverFlowsShouldBeFound("approverStatusId.equals=" + approverStatusId);

        // Get all the leaveRequestApproverFlowsList where approverStatus equals to (approverStatusId + 1)
        defaultLeaveRequestApproverFlowsShouldNotBeFound("approverStatusId.equals=" + (approverStatusId + 1));
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproverFlowsByCurrentLeaveRequestStatusIsEqualToSomething() throws Exception {
        LeaveStatuses currentLeaveRequestStatus;
        if (TestUtil.findAll(em, LeaveStatuses.class).isEmpty()) {
            leaveRequestApproverFlowsRepository.saveAndFlush(leaveRequestApproverFlows);
            currentLeaveRequestStatus = LeaveStatusesResourceIT.createEntity(em);
        } else {
            currentLeaveRequestStatus = TestUtil.findAll(em, LeaveStatuses.class).get(0);
        }
        em.persist(currentLeaveRequestStatus);
        em.flush();
        leaveRequestApproverFlows.setCurrentLeaveRequestStatus(currentLeaveRequestStatus);
        leaveRequestApproverFlowsRepository.saveAndFlush(leaveRequestApproverFlows);
        Long currentLeaveRequestStatusId = currentLeaveRequestStatus.getId();

        // Get all the leaveRequestApproverFlowsList where currentLeaveRequestStatus equals to currentLeaveRequestStatusId
        defaultLeaveRequestApproverFlowsShouldBeFound("currentLeaveRequestStatusId.equals=" + currentLeaveRequestStatusId);

        // Get all the leaveRequestApproverFlowsList where currentLeaveRequestStatus equals to (currentLeaveRequestStatusId + 1)
        defaultLeaveRequestApproverFlowsShouldNotBeFound("currentLeaveRequestStatusId.equals=" + (currentLeaveRequestStatusId + 1));
    }

    @Test
    @Transactional
    void getAllLeaveRequestApproverFlowsByNextLeaveRequestStatusIsEqualToSomething() throws Exception {
        LeaveStatuses nextLeaveRequestStatus;
        if (TestUtil.findAll(em, LeaveStatuses.class).isEmpty()) {
            leaveRequestApproverFlowsRepository.saveAndFlush(leaveRequestApproverFlows);
            nextLeaveRequestStatus = LeaveStatusesResourceIT.createEntity(em);
        } else {
            nextLeaveRequestStatus = TestUtil.findAll(em, LeaveStatuses.class).get(0);
        }
        em.persist(nextLeaveRequestStatus);
        em.flush();
        leaveRequestApproverFlows.setNextLeaveRequestStatus(nextLeaveRequestStatus);
        leaveRequestApproverFlowsRepository.saveAndFlush(leaveRequestApproverFlows);
        Long nextLeaveRequestStatusId = nextLeaveRequestStatus.getId();

        // Get all the leaveRequestApproverFlowsList where nextLeaveRequestStatus equals to nextLeaveRequestStatusId
        defaultLeaveRequestApproverFlowsShouldBeFound("nextLeaveRequestStatusId.equals=" + nextLeaveRequestStatusId);

        // Get all the leaveRequestApproverFlowsList where nextLeaveRequestStatus equals to (nextLeaveRequestStatusId + 1)
        defaultLeaveRequestApproverFlowsShouldNotBeFound("nextLeaveRequestStatusId.equals=" + (nextLeaveRequestStatusId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLeaveRequestApproverFlowsShouldBeFound(String filter) throws Exception {
        restLeaveRequestApproverFlowsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveRequestApproverFlows.getId().intValue())))
            .andExpect(jsonPath("$.[*].approvals").value(hasItem(DEFAULT_APPROVALS)))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restLeaveRequestApproverFlowsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLeaveRequestApproverFlowsShouldNotBeFound(String filter) throws Exception {
        restLeaveRequestApproverFlowsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLeaveRequestApproverFlowsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLeaveRequestApproverFlows() throws Exception {
        // Get the leaveRequestApproverFlows
        restLeaveRequestApproverFlowsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLeaveRequestApproverFlows() throws Exception {
        // Initialize the database
        leaveRequestApproverFlowsRepository.saveAndFlush(leaveRequestApproverFlows);

        int databaseSizeBeforeUpdate = leaveRequestApproverFlowsRepository.findAll().size();

        // Update the leaveRequestApproverFlows
        LeaveRequestApproverFlows updatedLeaveRequestApproverFlows = leaveRequestApproverFlowsRepository
            .findById(leaveRequestApproverFlows.getId())
            .get();
        // Disconnect from session so that the updates on updatedLeaveRequestApproverFlows are not directly saved in db
        em.detach(updatedLeaveRequestApproverFlows);
        updatedLeaveRequestApproverFlows
            .approvals(UPDATED_APPROVALS)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restLeaveRequestApproverFlowsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLeaveRequestApproverFlows.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLeaveRequestApproverFlows))
            )
            .andExpect(status().isOk());

        // Validate the LeaveRequestApproverFlows in the database
        List<LeaveRequestApproverFlows> leaveRequestApproverFlowsList = leaveRequestApproverFlowsRepository.findAll();
        assertThat(leaveRequestApproverFlowsList).hasSize(databaseSizeBeforeUpdate);
        LeaveRequestApproverFlows testLeaveRequestApproverFlows = leaveRequestApproverFlowsList.get(
            leaveRequestApproverFlowsList.size() - 1
        );
        assertThat(testLeaveRequestApproverFlows.getApprovals()).isEqualTo(UPDATED_APPROVALS);
        assertThat(testLeaveRequestApproverFlows.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testLeaveRequestApproverFlows.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveRequestApproverFlows.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaveRequestApproverFlows.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testLeaveRequestApproverFlows.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingLeaveRequestApproverFlows() throws Exception {
        int databaseSizeBeforeUpdate = leaveRequestApproverFlowsRepository.findAll().size();
        leaveRequestApproverFlows.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveRequestApproverFlowsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leaveRequestApproverFlows.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequestApproverFlows))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveRequestApproverFlows in the database
        List<LeaveRequestApproverFlows> leaveRequestApproverFlowsList = leaveRequestApproverFlowsRepository.findAll();
        assertThat(leaveRequestApproverFlowsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeaveRequestApproverFlows() throws Exception {
        int databaseSizeBeforeUpdate = leaveRequestApproverFlowsRepository.findAll().size();
        leaveRequestApproverFlows.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveRequestApproverFlowsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequestApproverFlows))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveRequestApproverFlows in the database
        List<LeaveRequestApproverFlows> leaveRequestApproverFlowsList = leaveRequestApproverFlowsRepository.findAll();
        assertThat(leaveRequestApproverFlowsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeaveRequestApproverFlows() throws Exception {
        int databaseSizeBeforeUpdate = leaveRequestApproverFlowsRepository.findAll().size();
        leaveRequestApproverFlows.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveRequestApproverFlowsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequestApproverFlows))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveRequestApproverFlows in the database
        List<LeaveRequestApproverFlows> leaveRequestApproverFlowsList = leaveRequestApproverFlowsRepository.findAll();
        assertThat(leaveRequestApproverFlowsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeaveRequestApproverFlowsWithPatch() throws Exception {
        // Initialize the database
        leaveRequestApproverFlowsRepository.saveAndFlush(leaveRequestApproverFlows);

        int databaseSizeBeforeUpdate = leaveRequestApproverFlowsRepository.findAll().size();

        // Update the leaveRequestApproverFlows using partial update
        LeaveRequestApproverFlows partialUpdatedLeaveRequestApproverFlows = new LeaveRequestApproverFlows();
        partialUpdatedLeaveRequestApproverFlows.setId(leaveRequestApproverFlows.getId());

        partialUpdatedLeaveRequestApproverFlows.effDate(UPDATED_EFF_DATE);

        restLeaveRequestApproverFlowsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveRequestApproverFlows.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveRequestApproverFlows))
            )
            .andExpect(status().isOk());

        // Validate the LeaveRequestApproverFlows in the database
        List<LeaveRequestApproverFlows> leaveRequestApproverFlowsList = leaveRequestApproverFlowsRepository.findAll();
        assertThat(leaveRequestApproverFlowsList).hasSize(databaseSizeBeforeUpdate);
        LeaveRequestApproverFlows testLeaveRequestApproverFlows = leaveRequestApproverFlowsList.get(
            leaveRequestApproverFlowsList.size() - 1
        );
        assertThat(testLeaveRequestApproverFlows.getApprovals()).isEqualTo(DEFAULT_APPROVALS);
        assertThat(testLeaveRequestApproverFlows.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testLeaveRequestApproverFlows.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testLeaveRequestApproverFlows.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testLeaveRequestApproverFlows.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testLeaveRequestApproverFlows.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateLeaveRequestApproverFlowsWithPatch() throws Exception {
        // Initialize the database
        leaveRequestApproverFlowsRepository.saveAndFlush(leaveRequestApproverFlows);

        int databaseSizeBeforeUpdate = leaveRequestApproverFlowsRepository.findAll().size();

        // Update the leaveRequestApproverFlows using partial update
        LeaveRequestApproverFlows partialUpdatedLeaveRequestApproverFlows = new LeaveRequestApproverFlows();
        partialUpdatedLeaveRequestApproverFlows.setId(leaveRequestApproverFlows.getId());

        partialUpdatedLeaveRequestApproverFlows
            .approvals(UPDATED_APPROVALS)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restLeaveRequestApproverFlowsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveRequestApproverFlows.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveRequestApproverFlows))
            )
            .andExpect(status().isOk());

        // Validate the LeaveRequestApproverFlows in the database
        List<LeaveRequestApproverFlows> leaveRequestApproverFlowsList = leaveRequestApproverFlowsRepository.findAll();
        assertThat(leaveRequestApproverFlowsList).hasSize(databaseSizeBeforeUpdate);
        LeaveRequestApproverFlows testLeaveRequestApproverFlows = leaveRequestApproverFlowsList.get(
            leaveRequestApproverFlowsList.size() - 1
        );
        assertThat(testLeaveRequestApproverFlows.getApprovals()).isEqualTo(UPDATED_APPROVALS);
        assertThat(testLeaveRequestApproverFlows.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testLeaveRequestApproverFlows.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveRequestApproverFlows.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaveRequestApproverFlows.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testLeaveRequestApproverFlows.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingLeaveRequestApproverFlows() throws Exception {
        int databaseSizeBeforeUpdate = leaveRequestApproverFlowsRepository.findAll().size();
        leaveRequestApproverFlows.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveRequestApproverFlowsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leaveRequestApproverFlows.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequestApproverFlows))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveRequestApproverFlows in the database
        List<LeaveRequestApproverFlows> leaveRequestApproverFlowsList = leaveRequestApproverFlowsRepository.findAll();
        assertThat(leaveRequestApproverFlowsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeaveRequestApproverFlows() throws Exception {
        int databaseSizeBeforeUpdate = leaveRequestApproverFlowsRepository.findAll().size();
        leaveRequestApproverFlows.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveRequestApproverFlowsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequestApproverFlows))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveRequestApproverFlows in the database
        List<LeaveRequestApproverFlows> leaveRequestApproverFlowsList = leaveRequestApproverFlowsRepository.findAll();
        assertThat(leaveRequestApproverFlowsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeaveRequestApproverFlows() throws Exception {
        int databaseSizeBeforeUpdate = leaveRequestApproverFlowsRepository.findAll().size();
        leaveRequestApproverFlows.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveRequestApproverFlowsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequestApproverFlows))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveRequestApproverFlows in the database
        List<LeaveRequestApproverFlows> leaveRequestApproverFlowsList = leaveRequestApproverFlowsRepository.findAll();
        assertThat(leaveRequestApproverFlowsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLeaveRequestApproverFlows() throws Exception {
        // Initialize the database
        leaveRequestApproverFlowsRepository.saveAndFlush(leaveRequestApproverFlows);

        int databaseSizeBeforeDelete = leaveRequestApproverFlowsRepository.findAll().size();

        // Delete the leaveRequestApproverFlows
        restLeaveRequestApproverFlowsMockMvc
            .perform(delete(ENTITY_API_URL_ID, leaveRequestApproverFlows.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeaveRequestApproverFlows> leaveRequestApproverFlowsList = leaveRequestApproverFlowsRepository.findAll();
        assertThat(leaveRequestApproverFlowsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
