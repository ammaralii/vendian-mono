package com.venturedive.vendian_mono.web.rest;

import static com.venturedive.vendian_mono.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.LeaveDetailAdjustmentLogs;
import com.venturedive.vendian_mono.domain.LeaveDetails;
import com.venturedive.vendian_mono.repository.LeaveDetailAdjustmentLogsRepository;
import com.venturedive.vendian_mono.service.criteria.LeaveDetailAdjustmentLogsCriteria;
import java.math.BigDecimal;
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
 * Integration tests for the {@link LeaveDetailAdjustmentLogsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeaveDetailAdjustmentLogsResourceIT {

    private static final String DEFAULT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_ACTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_COUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_COUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_COUNT = new BigDecimal(1 - 1);

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;
    private static final Integer SMALLER_VERSION = 1 - 1;

    private static final BigDecimal DEFAULT_QUOTA_BEFORE_ADJUSTMENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUOTA_BEFORE_ADJUSTMENT = new BigDecimal(2);
    private static final BigDecimal SMALLER_QUOTA_BEFORE_ADJUSTMENT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_QUOTA_AFTER_ADJUSTMENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUOTA_AFTER_ADJUSTMENT = new BigDecimal(2);
    private static final BigDecimal SMALLER_QUOTA_AFTER_ADJUSTMENT = new BigDecimal(1 - 1);

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/leave-detail-adjustment-logs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LeaveDetailAdjustmentLogsRepository leaveDetailAdjustmentLogsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeaveDetailAdjustmentLogsMockMvc;

    private LeaveDetailAdjustmentLogs leaveDetailAdjustmentLogs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveDetailAdjustmentLogs createEntity(EntityManager em) {
        LeaveDetailAdjustmentLogs leaveDetailAdjustmentLogs = new LeaveDetailAdjustmentLogs()
            .action(DEFAULT_ACTION)
            .count(DEFAULT_COUNT)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .version(DEFAULT_VERSION)
            .quotaBeforeAdjustment(DEFAULT_QUOTA_BEFORE_ADJUSTMENT)
            .quotaAfterAdjustment(DEFAULT_QUOTA_AFTER_ADJUSTMENT)
            .comment(DEFAULT_COMMENT);
        return leaveDetailAdjustmentLogs;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveDetailAdjustmentLogs createUpdatedEntity(EntityManager em) {
        LeaveDetailAdjustmentLogs leaveDetailAdjustmentLogs = new LeaveDetailAdjustmentLogs()
            .action(UPDATED_ACTION)
            .count(UPDATED_COUNT)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .version(UPDATED_VERSION)
            .quotaBeforeAdjustment(UPDATED_QUOTA_BEFORE_ADJUSTMENT)
            .quotaAfterAdjustment(UPDATED_QUOTA_AFTER_ADJUSTMENT)
            .comment(UPDATED_COMMENT);
        return leaveDetailAdjustmentLogs;
    }

    @BeforeEach
    public void initTest() {
        leaveDetailAdjustmentLogs = createEntity(em);
    }

    @Test
    @Transactional
    void createLeaveDetailAdjustmentLogs() throws Exception {
        int databaseSizeBeforeCreate = leaveDetailAdjustmentLogsRepository.findAll().size();
        // Create the LeaveDetailAdjustmentLogs
        restLeaveDetailAdjustmentLogsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveDetailAdjustmentLogs))
            )
            .andExpect(status().isCreated());

        // Validate the LeaveDetailAdjustmentLogs in the database
        List<LeaveDetailAdjustmentLogs> leaveDetailAdjustmentLogsList = leaveDetailAdjustmentLogsRepository.findAll();
        assertThat(leaveDetailAdjustmentLogsList).hasSize(databaseSizeBeforeCreate + 1);
        LeaveDetailAdjustmentLogs testLeaveDetailAdjustmentLogs = leaveDetailAdjustmentLogsList.get(
            leaveDetailAdjustmentLogsList.size() - 1
        );
        assertThat(testLeaveDetailAdjustmentLogs.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testLeaveDetailAdjustmentLogs.getCount()).isEqualByComparingTo(DEFAULT_COUNT);
        assertThat(testLeaveDetailAdjustmentLogs.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testLeaveDetailAdjustmentLogs.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testLeaveDetailAdjustmentLogs.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testLeaveDetailAdjustmentLogs.getQuotaBeforeAdjustment()).isEqualByComparingTo(DEFAULT_QUOTA_BEFORE_ADJUSTMENT);
        assertThat(testLeaveDetailAdjustmentLogs.getQuotaAfterAdjustment()).isEqualByComparingTo(DEFAULT_QUOTA_AFTER_ADJUSTMENT);
        assertThat(testLeaveDetailAdjustmentLogs.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    void createLeaveDetailAdjustmentLogsWithExistingId() throws Exception {
        // Create the LeaveDetailAdjustmentLogs with an existing ID
        leaveDetailAdjustmentLogs.setId(1L);

        int databaseSizeBeforeCreate = leaveDetailAdjustmentLogsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeaveDetailAdjustmentLogsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveDetailAdjustmentLogs))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveDetailAdjustmentLogs in the database
        List<LeaveDetailAdjustmentLogs> leaveDetailAdjustmentLogsList = leaveDetailAdjustmentLogsRepository.findAll();
        assertThat(leaveDetailAdjustmentLogsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveDetailAdjustmentLogsRepository.findAll().size();
        // set the field null
        leaveDetailAdjustmentLogs.setCreatedAt(null);

        // Create the LeaveDetailAdjustmentLogs, which fails.

        restLeaveDetailAdjustmentLogsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveDetailAdjustmentLogs))
            )
            .andExpect(status().isBadRequest());

        List<LeaveDetailAdjustmentLogs> leaveDetailAdjustmentLogsList = leaveDetailAdjustmentLogsRepository.findAll();
        assertThat(leaveDetailAdjustmentLogsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveDetailAdjustmentLogsRepository.findAll().size();
        // set the field null
        leaveDetailAdjustmentLogs.setUpdatedAt(null);

        // Create the LeaveDetailAdjustmentLogs, which fails.

        restLeaveDetailAdjustmentLogsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveDetailAdjustmentLogs))
            )
            .andExpect(status().isBadRequest());

        List<LeaveDetailAdjustmentLogs> leaveDetailAdjustmentLogsList = leaveDetailAdjustmentLogsRepository.findAll();
        assertThat(leaveDetailAdjustmentLogsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogs() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList
        restLeaveDetailAdjustmentLogsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveDetailAdjustmentLogs.getId().intValue())))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)))
            .andExpect(jsonPath("$.[*].count").value(hasItem(sameNumber(DEFAULT_COUNT))))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].quotaBeforeAdjustment").value(hasItem(sameNumber(DEFAULT_QUOTA_BEFORE_ADJUSTMENT))))
            .andExpect(jsonPath("$.[*].quotaAfterAdjustment").value(hasItem(sameNumber(DEFAULT_QUOTA_AFTER_ADJUSTMENT))))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)));
    }

    @Test
    @Transactional
    void getLeaveDetailAdjustmentLogs() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get the leaveDetailAdjustmentLogs
        restLeaveDetailAdjustmentLogsMockMvc
            .perform(get(ENTITY_API_URL_ID, leaveDetailAdjustmentLogs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leaveDetailAdjustmentLogs.getId().intValue()))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION))
            .andExpect(jsonPath("$.count").value(sameNumber(DEFAULT_COUNT)))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.quotaBeforeAdjustment").value(sameNumber(DEFAULT_QUOTA_BEFORE_ADJUSTMENT)))
            .andExpect(jsonPath("$.quotaAfterAdjustment").value(sameNumber(DEFAULT_QUOTA_AFTER_ADJUSTMENT)))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT));
    }

    @Test
    @Transactional
    void getLeaveDetailAdjustmentLogsByIdFiltering() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        Long id = leaveDetailAdjustmentLogs.getId();

        defaultLeaveDetailAdjustmentLogsShouldBeFound("id.equals=" + id);
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("id.notEquals=" + id);

        defaultLeaveDetailAdjustmentLogsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("id.greaterThan=" + id);

        defaultLeaveDetailAdjustmentLogsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByActionIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where action equals to DEFAULT_ACTION
        defaultLeaveDetailAdjustmentLogsShouldBeFound("action.equals=" + DEFAULT_ACTION);

        // Get all the leaveDetailAdjustmentLogsList where action equals to UPDATED_ACTION
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("action.equals=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByActionIsInShouldWork() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where action in DEFAULT_ACTION or UPDATED_ACTION
        defaultLeaveDetailAdjustmentLogsShouldBeFound("action.in=" + DEFAULT_ACTION + "," + UPDATED_ACTION);

        // Get all the leaveDetailAdjustmentLogsList where action equals to UPDATED_ACTION
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("action.in=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByActionIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where action is not null
        defaultLeaveDetailAdjustmentLogsShouldBeFound("action.specified=true");

        // Get all the leaveDetailAdjustmentLogsList where action is null
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("action.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByActionContainsSomething() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where action contains DEFAULT_ACTION
        defaultLeaveDetailAdjustmentLogsShouldBeFound("action.contains=" + DEFAULT_ACTION);

        // Get all the leaveDetailAdjustmentLogsList where action contains UPDATED_ACTION
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("action.contains=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByActionNotContainsSomething() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where action does not contain DEFAULT_ACTION
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("action.doesNotContain=" + DEFAULT_ACTION);

        // Get all the leaveDetailAdjustmentLogsList where action does not contain UPDATED_ACTION
        defaultLeaveDetailAdjustmentLogsShouldBeFound("action.doesNotContain=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByCountIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where count equals to DEFAULT_COUNT
        defaultLeaveDetailAdjustmentLogsShouldBeFound("count.equals=" + DEFAULT_COUNT);

        // Get all the leaveDetailAdjustmentLogsList where count equals to UPDATED_COUNT
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("count.equals=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByCountIsInShouldWork() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where count in DEFAULT_COUNT or UPDATED_COUNT
        defaultLeaveDetailAdjustmentLogsShouldBeFound("count.in=" + DEFAULT_COUNT + "," + UPDATED_COUNT);

        // Get all the leaveDetailAdjustmentLogsList where count equals to UPDATED_COUNT
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("count.in=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where count is not null
        defaultLeaveDetailAdjustmentLogsShouldBeFound("count.specified=true");

        // Get all the leaveDetailAdjustmentLogsList where count is null
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("count.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where count is greater than or equal to DEFAULT_COUNT
        defaultLeaveDetailAdjustmentLogsShouldBeFound("count.greaterThanOrEqual=" + DEFAULT_COUNT);

        // Get all the leaveDetailAdjustmentLogsList where count is greater than or equal to UPDATED_COUNT
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("count.greaterThanOrEqual=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where count is less than or equal to DEFAULT_COUNT
        defaultLeaveDetailAdjustmentLogsShouldBeFound("count.lessThanOrEqual=" + DEFAULT_COUNT);

        // Get all the leaveDetailAdjustmentLogsList where count is less than or equal to SMALLER_COUNT
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("count.lessThanOrEqual=" + SMALLER_COUNT);
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByCountIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where count is less than DEFAULT_COUNT
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("count.lessThan=" + DEFAULT_COUNT);

        // Get all the leaveDetailAdjustmentLogsList where count is less than UPDATED_COUNT
        defaultLeaveDetailAdjustmentLogsShouldBeFound("count.lessThan=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where count is greater than DEFAULT_COUNT
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("count.greaterThan=" + DEFAULT_COUNT);

        // Get all the leaveDetailAdjustmentLogsList where count is greater than SMALLER_COUNT
        defaultLeaveDetailAdjustmentLogsShouldBeFound("count.greaterThan=" + SMALLER_COUNT);
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where createdAt equals to DEFAULT_CREATED_AT
        defaultLeaveDetailAdjustmentLogsShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the leaveDetailAdjustmentLogsList where createdAt equals to UPDATED_CREATED_AT
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultLeaveDetailAdjustmentLogsShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the leaveDetailAdjustmentLogsList where createdAt equals to UPDATED_CREATED_AT
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where createdAt is not null
        defaultLeaveDetailAdjustmentLogsShouldBeFound("createdAt.specified=true");

        // Get all the leaveDetailAdjustmentLogsList where createdAt is null
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultLeaveDetailAdjustmentLogsShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the leaveDetailAdjustmentLogsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultLeaveDetailAdjustmentLogsShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the leaveDetailAdjustmentLogsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where updatedAt is not null
        defaultLeaveDetailAdjustmentLogsShouldBeFound("updatedAt.specified=true");

        // Get all the leaveDetailAdjustmentLogsList where updatedAt is null
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where version equals to DEFAULT_VERSION
        defaultLeaveDetailAdjustmentLogsShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the leaveDetailAdjustmentLogsList where version equals to UPDATED_VERSION
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultLeaveDetailAdjustmentLogsShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the leaveDetailAdjustmentLogsList where version equals to UPDATED_VERSION
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where version is not null
        defaultLeaveDetailAdjustmentLogsShouldBeFound("version.specified=true");

        // Get all the leaveDetailAdjustmentLogsList where version is null
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where version is greater than or equal to DEFAULT_VERSION
        defaultLeaveDetailAdjustmentLogsShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the leaveDetailAdjustmentLogsList where version is greater than or equal to UPDATED_VERSION
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where version is less than or equal to DEFAULT_VERSION
        defaultLeaveDetailAdjustmentLogsShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the leaveDetailAdjustmentLogsList where version is less than or equal to SMALLER_VERSION
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where version is less than DEFAULT_VERSION
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the leaveDetailAdjustmentLogsList where version is less than UPDATED_VERSION
        defaultLeaveDetailAdjustmentLogsShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where version is greater than DEFAULT_VERSION
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the leaveDetailAdjustmentLogsList where version is greater than SMALLER_VERSION
        defaultLeaveDetailAdjustmentLogsShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByQuotaBeforeAdjustmentIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where quotaBeforeAdjustment equals to DEFAULT_QUOTA_BEFORE_ADJUSTMENT
        defaultLeaveDetailAdjustmentLogsShouldBeFound("quotaBeforeAdjustment.equals=" + DEFAULT_QUOTA_BEFORE_ADJUSTMENT);

        // Get all the leaveDetailAdjustmentLogsList where quotaBeforeAdjustment equals to UPDATED_QUOTA_BEFORE_ADJUSTMENT
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("quotaBeforeAdjustment.equals=" + UPDATED_QUOTA_BEFORE_ADJUSTMENT);
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByQuotaBeforeAdjustmentIsInShouldWork() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where quotaBeforeAdjustment in DEFAULT_QUOTA_BEFORE_ADJUSTMENT or UPDATED_QUOTA_BEFORE_ADJUSTMENT
        defaultLeaveDetailAdjustmentLogsShouldBeFound(
            "quotaBeforeAdjustment.in=" + DEFAULT_QUOTA_BEFORE_ADJUSTMENT + "," + UPDATED_QUOTA_BEFORE_ADJUSTMENT
        );

        // Get all the leaveDetailAdjustmentLogsList where quotaBeforeAdjustment equals to UPDATED_QUOTA_BEFORE_ADJUSTMENT
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("quotaBeforeAdjustment.in=" + UPDATED_QUOTA_BEFORE_ADJUSTMENT);
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByQuotaBeforeAdjustmentIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where quotaBeforeAdjustment is not null
        defaultLeaveDetailAdjustmentLogsShouldBeFound("quotaBeforeAdjustment.specified=true");

        // Get all the leaveDetailAdjustmentLogsList where quotaBeforeAdjustment is null
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("quotaBeforeAdjustment.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByQuotaBeforeAdjustmentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where quotaBeforeAdjustment is greater than or equal to DEFAULT_QUOTA_BEFORE_ADJUSTMENT
        defaultLeaveDetailAdjustmentLogsShouldBeFound("quotaBeforeAdjustment.greaterThanOrEqual=" + DEFAULT_QUOTA_BEFORE_ADJUSTMENT);

        // Get all the leaveDetailAdjustmentLogsList where quotaBeforeAdjustment is greater than or equal to UPDATED_QUOTA_BEFORE_ADJUSTMENT
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("quotaBeforeAdjustment.greaterThanOrEqual=" + UPDATED_QUOTA_BEFORE_ADJUSTMENT);
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByQuotaBeforeAdjustmentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where quotaBeforeAdjustment is less than or equal to DEFAULT_QUOTA_BEFORE_ADJUSTMENT
        defaultLeaveDetailAdjustmentLogsShouldBeFound("quotaBeforeAdjustment.lessThanOrEqual=" + DEFAULT_QUOTA_BEFORE_ADJUSTMENT);

        // Get all the leaveDetailAdjustmentLogsList where quotaBeforeAdjustment is less than or equal to SMALLER_QUOTA_BEFORE_ADJUSTMENT
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("quotaBeforeAdjustment.lessThanOrEqual=" + SMALLER_QUOTA_BEFORE_ADJUSTMENT);
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByQuotaBeforeAdjustmentIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where quotaBeforeAdjustment is less than DEFAULT_QUOTA_BEFORE_ADJUSTMENT
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("quotaBeforeAdjustment.lessThan=" + DEFAULT_QUOTA_BEFORE_ADJUSTMENT);

        // Get all the leaveDetailAdjustmentLogsList where quotaBeforeAdjustment is less than UPDATED_QUOTA_BEFORE_ADJUSTMENT
        defaultLeaveDetailAdjustmentLogsShouldBeFound("quotaBeforeAdjustment.lessThan=" + UPDATED_QUOTA_BEFORE_ADJUSTMENT);
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByQuotaBeforeAdjustmentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where quotaBeforeAdjustment is greater than DEFAULT_QUOTA_BEFORE_ADJUSTMENT
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("quotaBeforeAdjustment.greaterThan=" + DEFAULT_QUOTA_BEFORE_ADJUSTMENT);

        // Get all the leaveDetailAdjustmentLogsList where quotaBeforeAdjustment is greater than SMALLER_QUOTA_BEFORE_ADJUSTMENT
        defaultLeaveDetailAdjustmentLogsShouldBeFound("quotaBeforeAdjustment.greaterThan=" + SMALLER_QUOTA_BEFORE_ADJUSTMENT);
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByQuotaAfterAdjustmentIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where quotaAfterAdjustment equals to DEFAULT_QUOTA_AFTER_ADJUSTMENT
        defaultLeaveDetailAdjustmentLogsShouldBeFound("quotaAfterAdjustment.equals=" + DEFAULT_QUOTA_AFTER_ADJUSTMENT);

        // Get all the leaveDetailAdjustmentLogsList where quotaAfterAdjustment equals to UPDATED_QUOTA_AFTER_ADJUSTMENT
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("quotaAfterAdjustment.equals=" + UPDATED_QUOTA_AFTER_ADJUSTMENT);
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByQuotaAfterAdjustmentIsInShouldWork() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where quotaAfterAdjustment in DEFAULT_QUOTA_AFTER_ADJUSTMENT or UPDATED_QUOTA_AFTER_ADJUSTMENT
        defaultLeaveDetailAdjustmentLogsShouldBeFound(
            "quotaAfterAdjustment.in=" + DEFAULT_QUOTA_AFTER_ADJUSTMENT + "," + UPDATED_QUOTA_AFTER_ADJUSTMENT
        );

        // Get all the leaveDetailAdjustmentLogsList where quotaAfterAdjustment equals to UPDATED_QUOTA_AFTER_ADJUSTMENT
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("quotaAfterAdjustment.in=" + UPDATED_QUOTA_AFTER_ADJUSTMENT);
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByQuotaAfterAdjustmentIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where quotaAfterAdjustment is not null
        defaultLeaveDetailAdjustmentLogsShouldBeFound("quotaAfterAdjustment.specified=true");

        // Get all the leaveDetailAdjustmentLogsList where quotaAfterAdjustment is null
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("quotaAfterAdjustment.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByQuotaAfterAdjustmentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where quotaAfterAdjustment is greater than or equal to DEFAULT_QUOTA_AFTER_ADJUSTMENT
        defaultLeaveDetailAdjustmentLogsShouldBeFound("quotaAfterAdjustment.greaterThanOrEqual=" + DEFAULT_QUOTA_AFTER_ADJUSTMENT);

        // Get all the leaveDetailAdjustmentLogsList where quotaAfterAdjustment is greater than or equal to UPDATED_QUOTA_AFTER_ADJUSTMENT
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("quotaAfterAdjustment.greaterThanOrEqual=" + UPDATED_QUOTA_AFTER_ADJUSTMENT);
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByQuotaAfterAdjustmentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where quotaAfterAdjustment is less than or equal to DEFAULT_QUOTA_AFTER_ADJUSTMENT
        defaultLeaveDetailAdjustmentLogsShouldBeFound("quotaAfterAdjustment.lessThanOrEqual=" + DEFAULT_QUOTA_AFTER_ADJUSTMENT);

        // Get all the leaveDetailAdjustmentLogsList where quotaAfterAdjustment is less than or equal to SMALLER_QUOTA_AFTER_ADJUSTMENT
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("quotaAfterAdjustment.lessThanOrEqual=" + SMALLER_QUOTA_AFTER_ADJUSTMENT);
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByQuotaAfterAdjustmentIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where quotaAfterAdjustment is less than DEFAULT_QUOTA_AFTER_ADJUSTMENT
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("quotaAfterAdjustment.lessThan=" + DEFAULT_QUOTA_AFTER_ADJUSTMENT);

        // Get all the leaveDetailAdjustmentLogsList where quotaAfterAdjustment is less than UPDATED_QUOTA_AFTER_ADJUSTMENT
        defaultLeaveDetailAdjustmentLogsShouldBeFound("quotaAfterAdjustment.lessThan=" + UPDATED_QUOTA_AFTER_ADJUSTMENT);
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByQuotaAfterAdjustmentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where quotaAfterAdjustment is greater than DEFAULT_QUOTA_AFTER_ADJUSTMENT
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("quotaAfterAdjustment.greaterThan=" + DEFAULT_QUOTA_AFTER_ADJUSTMENT);

        // Get all the leaveDetailAdjustmentLogsList where quotaAfterAdjustment is greater than SMALLER_QUOTA_AFTER_ADJUSTMENT
        defaultLeaveDetailAdjustmentLogsShouldBeFound("quotaAfterAdjustment.greaterThan=" + SMALLER_QUOTA_AFTER_ADJUSTMENT);
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where comment equals to DEFAULT_COMMENT
        defaultLeaveDetailAdjustmentLogsShouldBeFound("comment.equals=" + DEFAULT_COMMENT);

        // Get all the leaveDetailAdjustmentLogsList where comment equals to UPDATED_COMMENT
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("comment.equals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByCommentIsInShouldWork() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where comment in DEFAULT_COMMENT or UPDATED_COMMENT
        defaultLeaveDetailAdjustmentLogsShouldBeFound("comment.in=" + DEFAULT_COMMENT + "," + UPDATED_COMMENT);

        // Get all the leaveDetailAdjustmentLogsList where comment equals to UPDATED_COMMENT
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("comment.in=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByCommentIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where comment is not null
        defaultLeaveDetailAdjustmentLogsShouldBeFound("comment.specified=true");

        // Get all the leaveDetailAdjustmentLogsList where comment is null
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("comment.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByCommentContainsSomething() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where comment contains DEFAULT_COMMENT
        defaultLeaveDetailAdjustmentLogsShouldBeFound("comment.contains=" + DEFAULT_COMMENT);

        // Get all the leaveDetailAdjustmentLogsList where comment contains UPDATED_COMMENT
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("comment.contains=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByCommentNotContainsSomething() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        // Get all the leaveDetailAdjustmentLogsList where comment does not contain DEFAULT_COMMENT
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("comment.doesNotContain=" + DEFAULT_COMMENT);

        // Get all the leaveDetailAdjustmentLogsList where comment does not contain UPDATED_COMMENT
        defaultLeaveDetailAdjustmentLogsShouldBeFound("comment.doesNotContain=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByLeaveDetailIsEqualToSomething() throws Exception {
        LeaveDetails leaveDetail;
        if (TestUtil.findAll(em, LeaveDetails.class).isEmpty()) {
            leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);
            leaveDetail = LeaveDetailsResourceIT.createEntity(em);
        } else {
            leaveDetail = TestUtil.findAll(em, LeaveDetails.class).get(0);
        }
        em.persist(leaveDetail);
        em.flush();
        leaveDetailAdjustmentLogs.setLeaveDetail(leaveDetail);
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);
        Long leaveDetailId = leaveDetail.getId();

        // Get all the leaveDetailAdjustmentLogsList where leaveDetail equals to leaveDetailId
        defaultLeaveDetailAdjustmentLogsShouldBeFound("leaveDetailId.equals=" + leaveDetailId);

        // Get all the leaveDetailAdjustmentLogsList where leaveDetail equals to (leaveDetailId + 1)
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("leaveDetailId.equals=" + (leaveDetailId + 1));
    }

    @Test
    @Transactional
    void getAllLeaveDetailAdjustmentLogsByAdjustedByIsEqualToSomething() throws Exception {
        Employees adjustedBy;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);
            adjustedBy = EmployeesResourceIT.createEntity(em);
        } else {
            adjustedBy = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(adjustedBy);
        em.flush();
        leaveDetailAdjustmentLogs.setAdjustedBy(adjustedBy);
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);
        Long adjustedById = adjustedBy.getId();

        // Get all the leaveDetailAdjustmentLogsList where adjustedBy equals to adjustedById
        defaultLeaveDetailAdjustmentLogsShouldBeFound("adjustedById.equals=" + adjustedById);

        // Get all the leaveDetailAdjustmentLogsList where adjustedBy equals to (adjustedById + 1)
        defaultLeaveDetailAdjustmentLogsShouldNotBeFound("adjustedById.equals=" + (adjustedById + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLeaveDetailAdjustmentLogsShouldBeFound(String filter) throws Exception {
        restLeaveDetailAdjustmentLogsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveDetailAdjustmentLogs.getId().intValue())))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)))
            .andExpect(jsonPath("$.[*].count").value(hasItem(sameNumber(DEFAULT_COUNT))))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].quotaBeforeAdjustment").value(hasItem(sameNumber(DEFAULT_QUOTA_BEFORE_ADJUSTMENT))))
            .andExpect(jsonPath("$.[*].quotaAfterAdjustment").value(hasItem(sameNumber(DEFAULT_QUOTA_AFTER_ADJUSTMENT))))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)));

        // Check, that the count call also returns 1
        restLeaveDetailAdjustmentLogsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLeaveDetailAdjustmentLogsShouldNotBeFound(String filter) throws Exception {
        restLeaveDetailAdjustmentLogsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLeaveDetailAdjustmentLogsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLeaveDetailAdjustmentLogs() throws Exception {
        // Get the leaveDetailAdjustmentLogs
        restLeaveDetailAdjustmentLogsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLeaveDetailAdjustmentLogs() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        int databaseSizeBeforeUpdate = leaveDetailAdjustmentLogsRepository.findAll().size();

        // Update the leaveDetailAdjustmentLogs
        LeaveDetailAdjustmentLogs updatedLeaveDetailAdjustmentLogs = leaveDetailAdjustmentLogsRepository
            .findById(leaveDetailAdjustmentLogs.getId())
            .get();
        // Disconnect from session so that the updates on updatedLeaveDetailAdjustmentLogs are not directly saved in db
        em.detach(updatedLeaveDetailAdjustmentLogs);
        updatedLeaveDetailAdjustmentLogs
            .action(UPDATED_ACTION)
            .count(UPDATED_COUNT)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .version(UPDATED_VERSION)
            .quotaBeforeAdjustment(UPDATED_QUOTA_BEFORE_ADJUSTMENT)
            .quotaAfterAdjustment(UPDATED_QUOTA_AFTER_ADJUSTMENT)
            .comment(UPDATED_COMMENT);

        restLeaveDetailAdjustmentLogsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLeaveDetailAdjustmentLogs.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLeaveDetailAdjustmentLogs))
            )
            .andExpect(status().isOk());

        // Validate the LeaveDetailAdjustmentLogs in the database
        List<LeaveDetailAdjustmentLogs> leaveDetailAdjustmentLogsList = leaveDetailAdjustmentLogsRepository.findAll();
        assertThat(leaveDetailAdjustmentLogsList).hasSize(databaseSizeBeforeUpdate);
        LeaveDetailAdjustmentLogs testLeaveDetailAdjustmentLogs = leaveDetailAdjustmentLogsList.get(
            leaveDetailAdjustmentLogsList.size() - 1
        );
        assertThat(testLeaveDetailAdjustmentLogs.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testLeaveDetailAdjustmentLogs.getCount()).isEqualByComparingTo(UPDATED_COUNT);
        assertThat(testLeaveDetailAdjustmentLogs.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveDetailAdjustmentLogs.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaveDetailAdjustmentLogs.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testLeaveDetailAdjustmentLogs.getQuotaBeforeAdjustment()).isEqualByComparingTo(UPDATED_QUOTA_BEFORE_ADJUSTMENT);
        assertThat(testLeaveDetailAdjustmentLogs.getQuotaAfterAdjustment()).isEqualByComparingTo(UPDATED_QUOTA_AFTER_ADJUSTMENT);
        assertThat(testLeaveDetailAdjustmentLogs.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void putNonExistingLeaveDetailAdjustmentLogs() throws Exception {
        int databaseSizeBeforeUpdate = leaveDetailAdjustmentLogsRepository.findAll().size();
        leaveDetailAdjustmentLogs.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveDetailAdjustmentLogsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leaveDetailAdjustmentLogs.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveDetailAdjustmentLogs))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveDetailAdjustmentLogs in the database
        List<LeaveDetailAdjustmentLogs> leaveDetailAdjustmentLogsList = leaveDetailAdjustmentLogsRepository.findAll();
        assertThat(leaveDetailAdjustmentLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeaveDetailAdjustmentLogs() throws Exception {
        int databaseSizeBeforeUpdate = leaveDetailAdjustmentLogsRepository.findAll().size();
        leaveDetailAdjustmentLogs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveDetailAdjustmentLogsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveDetailAdjustmentLogs))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveDetailAdjustmentLogs in the database
        List<LeaveDetailAdjustmentLogs> leaveDetailAdjustmentLogsList = leaveDetailAdjustmentLogsRepository.findAll();
        assertThat(leaveDetailAdjustmentLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeaveDetailAdjustmentLogs() throws Exception {
        int databaseSizeBeforeUpdate = leaveDetailAdjustmentLogsRepository.findAll().size();
        leaveDetailAdjustmentLogs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveDetailAdjustmentLogsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveDetailAdjustmentLogs))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveDetailAdjustmentLogs in the database
        List<LeaveDetailAdjustmentLogs> leaveDetailAdjustmentLogsList = leaveDetailAdjustmentLogsRepository.findAll();
        assertThat(leaveDetailAdjustmentLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeaveDetailAdjustmentLogsWithPatch() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        int databaseSizeBeforeUpdate = leaveDetailAdjustmentLogsRepository.findAll().size();

        // Update the leaveDetailAdjustmentLogs using partial update
        LeaveDetailAdjustmentLogs partialUpdatedLeaveDetailAdjustmentLogs = new LeaveDetailAdjustmentLogs();
        partialUpdatedLeaveDetailAdjustmentLogs.setId(leaveDetailAdjustmentLogs.getId());

        partialUpdatedLeaveDetailAdjustmentLogs.quotaAfterAdjustment(UPDATED_QUOTA_AFTER_ADJUSTMENT).comment(UPDATED_COMMENT);

        restLeaveDetailAdjustmentLogsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveDetailAdjustmentLogs.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveDetailAdjustmentLogs))
            )
            .andExpect(status().isOk());

        // Validate the LeaveDetailAdjustmentLogs in the database
        List<LeaveDetailAdjustmentLogs> leaveDetailAdjustmentLogsList = leaveDetailAdjustmentLogsRepository.findAll();
        assertThat(leaveDetailAdjustmentLogsList).hasSize(databaseSizeBeforeUpdate);
        LeaveDetailAdjustmentLogs testLeaveDetailAdjustmentLogs = leaveDetailAdjustmentLogsList.get(
            leaveDetailAdjustmentLogsList.size() - 1
        );
        assertThat(testLeaveDetailAdjustmentLogs.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testLeaveDetailAdjustmentLogs.getCount()).isEqualByComparingTo(DEFAULT_COUNT);
        assertThat(testLeaveDetailAdjustmentLogs.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testLeaveDetailAdjustmentLogs.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testLeaveDetailAdjustmentLogs.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testLeaveDetailAdjustmentLogs.getQuotaBeforeAdjustment()).isEqualByComparingTo(DEFAULT_QUOTA_BEFORE_ADJUSTMENT);
        assertThat(testLeaveDetailAdjustmentLogs.getQuotaAfterAdjustment()).isEqualByComparingTo(UPDATED_QUOTA_AFTER_ADJUSTMENT);
        assertThat(testLeaveDetailAdjustmentLogs.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void fullUpdateLeaveDetailAdjustmentLogsWithPatch() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        int databaseSizeBeforeUpdate = leaveDetailAdjustmentLogsRepository.findAll().size();

        // Update the leaveDetailAdjustmentLogs using partial update
        LeaveDetailAdjustmentLogs partialUpdatedLeaveDetailAdjustmentLogs = new LeaveDetailAdjustmentLogs();
        partialUpdatedLeaveDetailAdjustmentLogs.setId(leaveDetailAdjustmentLogs.getId());

        partialUpdatedLeaveDetailAdjustmentLogs
            .action(UPDATED_ACTION)
            .count(UPDATED_COUNT)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .version(UPDATED_VERSION)
            .quotaBeforeAdjustment(UPDATED_QUOTA_BEFORE_ADJUSTMENT)
            .quotaAfterAdjustment(UPDATED_QUOTA_AFTER_ADJUSTMENT)
            .comment(UPDATED_COMMENT);

        restLeaveDetailAdjustmentLogsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveDetailAdjustmentLogs.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveDetailAdjustmentLogs))
            )
            .andExpect(status().isOk());

        // Validate the LeaveDetailAdjustmentLogs in the database
        List<LeaveDetailAdjustmentLogs> leaveDetailAdjustmentLogsList = leaveDetailAdjustmentLogsRepository.findAll();
        assertThat(leaveDetailAdjustmentLogsList).hasSize(databaseSizeBeforeUpdate);
        LeaveDetailAdjustmentLogs testLeaveDetailAdjustmentLogs = leaveDetailAdjustmentLogsList.get(
            leaveDetailAdjustmentLogsList.size() - 1
        );
        assertThat(testLeaveDetailAdjustmentLogs.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testLeaveDetailAdjustmentLogs.getCount()).isEqualByComparingTo(UPDATED_COUNT);
        assertThat(testLeaveDetailAdjustmentLogs.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveDetailAdjustmentLogs.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaveDetailAdjustmentLogs.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testLeaveDetailAdjustmentLogs.getQuotaBeforeAdjustment()).isEqualByComparingTo(UPDATED_QUOTA_BEFORE_ADJUSTMENT);
        assertThat(testLeaveDetailAdjustmentLogs.getQuotaAfterAdjustment()).isEqualByComparingTo(UPDATED_QUOTA_AFTER_ADJUSTMENT);
        assertThat(testLeaveDetailAdjustmentLogs.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void patchNonExistingLeaveDetailAdjustmentLogs() throws Exception {
        int databaseSizeBeforeUpdate = leaveDetailAdjustmentLogsRepository.findAll().size();
        leaveDetailAdjustmentLogs.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveDetailAdjustmentLogsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leaveDetailAdjustmentLogs.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveDetailAdjustmentLogs))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveDetailAdjustmentLogs in the database
        List<LeaveDetailAdjustmentLogs> leaveDetailAdjustmentLogsList = leaveDetailAdjustmentLogsRepository.findAll();
        assertThat(leaveDetailAdjustmentLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeaveDetailAdjustmentLogs() throws Exception {
        int databaseSizeBeforeUpdate = leaveDetailAdjustmentLogsRepository.findAll().size();
        leaveDetailAdjustmentLogs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveDetailAdjustmentLogsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveDetailAdjustmentLogs))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveDetailAdjustmentLogs in the database
        List<LeaveDetailAdjustmentLogs> leaveDetailAdjustmentLogsList = leaveDetailAdjustmentLogsRepository.findAll();
        assertThat(leaveDetailAdjustmentLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeaveDetailAdjustmentLogs() throws Exception {
        int databaseSizeBeforeUpdate = leaveDetailAdjustmentLogsRepository.findAll().size();
        leaveDetailAdjustmentLogs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveDetailAdjustmentLogsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveDetailAdjustmentLogs))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveDetailAdjustmentLogs in the database
        List<LeaveDetailAdjustmentLogs> leaveDetailAdjustmentLogsList = leaveDetailAdjustmentLogsRepository.findAll();
        assertThat(leaveDetailAdjustmentLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLeaveDetailAdjustmentLogs() throws Exception {
        // Initialize the database
        leaveDetailAdjustmentLogsRepository.saveAndFlush(leaveDetailAdjustmentLogs);

        int databaseSizeBeforeDelete = leaveDetailAdjustmentLogsRepository.findAll().size();

        // Delete the leaveDetailAdjustmentLogs
        restLeaveDetailAdjustmentLogsMockMvc
            .perform(delete(ENTITY_API_URL_ID, leaveDetailAdjustmentLogs.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeaveDetailAdjustmentLogs> leaveDetailAdjustmentLogsList = leaveDetailAdjustmentLogsRepository.findAll();
        assertThat(leaveDetailAdjustmentLogsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
