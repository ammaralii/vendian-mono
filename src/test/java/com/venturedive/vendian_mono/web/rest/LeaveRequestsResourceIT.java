package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.LeaveDetails;
import com.venturedive.vendian_mono.domain.LeaveRequestApprovers;
import com.venturedive.vendian_mono.domain.LeaveRequests;
import com.venturedive.vendian_mono.domain.LeaveRequests;
import com.venturedive.vendian_mono.domain.LeaveStatuses;
import com.venturedive.vendian_mono.repository.LeaveRequestsRepository;
import com.venturedive.vendian_mono.service.criteria.LeaveRequestsCriteria;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link LeaveRequestsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeaveRequestsResourceIT {

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final LocalDate DEFAULT_REQUEST_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REQUEST_START_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_REQUEST_START_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_REQUEST_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REQUEST_END_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_REQUEST_END_DATE = LocalDate.ofEpochDay(-1L);

    private static final Boolean DEFAULT_IS_HALF_DAY = false;
    private static final Boolean UPDATED_IS_HALF_DAY = true;

    private static final Instant DEFAULT_STATUS_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STATUS_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;
    private static final Integer SMALLER_VERSION = 1 - 1;

    private static final String ENTITY_API_URL = "/api/leave-requests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LeaveRequestsRepository leaveRequestsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeaveRequestsMockMvc;

    private LeaveRequests leaveRequests;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveRequests createEntity(EntityManager em) {
        LeaveRequests leaveRequests = new LeaveRequests()
            .createdAt(DEFAULT_CREATED_AT)
            .requestStartDate(DEFAULT_REQUEST_START_DATE)
            .requestEndDate(DEFAULT_REQUEST_END_DATE)
            .isHalfDay(DEFAULT_IS_HALF_DAY)
            .statusDate(DEFAULT_STATUS_DATE)
            .comments(DEFAULT_COMMENTS)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT)
            .version(DEFAULT_VERSION);
        // Add required entity
        LeaveDetails leaveDetails;
        if (TestUtil.findAll(em, LeaveDetails.class).isEmpty()) {
            leaveDetails = LeaveDetailsResourceIT.createEntity(em);
            em.persist(leaveDetails);
            em.flush();
        } else {
            leaveDetails = TestUtil.findAll(em, LeaveDetails.class).get(0);
        }
        leaveRequests.setLeaveDetail(leaveDetails);
        // Add required entity
        LeaveStatuses leaveStatuses;
        if (TestUtil.findAll(em, LeaveStatuses.class).isEmpty()) {
            leaveStatuses = LeaveStatusesResourceIT.createEntity(em);
            em.persist(leaveStatuses);
            em.flush();
        } else {
            leaveStatuses = TestUtil.findAll(em, LeaveStatuses.class).get(0);
        }
        leaveRequests.setLeaveStatus(leaveStatuses);
        return leaveRequests;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveRequests createUpdatedEntity(EntityManager em) {
        LeaveRequests leaveRequests = new LeaveRequests()
            .createdAt(UPDATED_CREATED_AT)
            .requestStartDate(UPDATED_REQUEST_START_DATE)
            .requestEndDate(UPDATED_REQUEST_END_DATE)
            .isHalfDay(UPDATED_IS_HALF_DAY)
            .statusDate(UPDATED_STATUS_DATE)
            .comments(UPDATED_COMMENTS)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);
        // Add required entity
        LeaveDetails leaveDetails;
        if (TestUtil.findAll(em, LeaveDetails.class).isEmpty()) {
            leaveDetails = LeaveDetailsResourceIT.createUpdatedEntity(em);
            em.persist(leaveDetails);
            em.flush();
        } else {
            leaveDetails = TestUtil.findAll(em, LeaveDetails.class).get(0);
        }
        leaveRequests.setLeaveDetail(leaveDetails);
        // Add required entity
        LeaveStatuses leaveStatuses;
        if (TestUtil.findAll(em, LeaveStatuses.class).isEmpty()) {
            leaveStatuses = LeaveStatusesResourceIT.createUpdatedEntity(em);
            em.persist(leaveStatuses);
            em.flush();
        } else {
            leaveStatuses = TestUtil.findAll(em, LeaveStatuses.class).get(0);
        }
        leaveRequests.setLeaveStatus(leaveStatuses);
        return leaveRequests;
    }

    @BeforeEach
    public void initTest() {
        leaveRequests = createEntity(em);
    }

    @Test
    @Transactional
    void createLeaveRequests() throws Exception {
        int databaseSizeBeforeCreate = leaveRequestsRepository.findAll().size();
        // Create the LeaveRequests
        restLeaveRequestsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequests))
            )
            .andExpect(status().isCreated());

        // Validate the LeaveRequests in the database
        List<LeaveRequests> leaveRequestsList = leaveRequestsRepository.findAll();
        assertThat(leaveRequestsList).hasSize(databaseSizeBeforeCreate + 1);
        LeaveRequests testLeaveRequests = leaveRequestsList.get(leaveRequestsList.size() - 1);
        assertThat(testLeaveRequests.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testLeaveRequests.getRequestStartDate()).isEqualTo(DEFAULT_REQUEST_START_DATE);
        assertThat(testLeaveRequests.getRequestEndDate()).isEqualTo(DEFAULT_REQUEST_END_DATE);
        assertThat(testLeaveRequests.getIsHalfDay()).isEqualTo(DEFAULT_IS_HALF_DAY);
        assertThat(testLeaveRequests.getStatusDate()).isEqualTo(DEFAULT_STATUS_DATE);
        assertThat(testLeaveRequests.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testLeaveRequests.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testLeaveRequests.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testLeaveRequests.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createLeaveRequestsWithExistingId() throws Exception {
        // Create the LeaveRequests with an existing ID
        leaveRequests.setId(1L);

        int databaseSizeBeforeCreate = leaveRequestsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeaveRequestsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequests))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveRequests in the database
        List<LeaveRequests> leaveRequestsList = leaveRequestsRepository.findAll();
        assertThat(leaveRequestsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveRequestsRepository.findAll().size();
        // set the field null
        leaveRequests.setCreatedAt(null);

        // Create the LeaveRequests, which fails.

        restLeaveRequestsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequests))
            )
            .andExpect(status().isBadRequest());

        List<LeaveRequests> leaveRequestsList = leaveRequestsRepository.findAll();
        assertThat(leaveRequestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRequestStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveRequestsRepository.findAll().size();
        // set the field null
        leaveRequests.setRequestStartDate(null);

        // Create the LeaveRequests, which fails.

        restLeaveRequestsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequests))
            )
            .andExpect(status().isBadRequest());

        List<LeaveRequests> leaveRequestsList = leaveRequestsRepository.findAll();
        assertThat(leaveRequestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRequestEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveRequestsRepository.findAll().size();
        // set the field null
        leaveRequests.setRequestEndDate(null);

        // Create the LeaveRequests, which fails.

        restLeaveRequestsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequests))
            )
            .andExpect(status().isBadRequest());

        List<LeaveRequests> leaveRequestsList = leaveRequestsRepository.findAll();
        assertThat(leaveRequestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveRequestsRepository.findAll().size();
        // set the field null
        leaveRequests.setStatusDate(null);

        // Create the LeaveRequests, which fails.

        restLeaveRequestsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequests))
            )
            .andExpect(status().isBadRequest());

        List<LeaveRequests> leaveRequestsList = leaveRequestsRepository.findAll();
        assertThat(leaveRequestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCommentsIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveRequestsRepository.findAll().size();
        // set the field null
        leaveRequests.setComments(null);

        // Create the LeaveRequests, which fails.

        restLeaveRequestsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequests))
            )
            .andExpect(status().isBadRequest());

        List<LeaveRequests> leaveRequestsList = leaveRequestsRepository.findAll();
        assertThat(leaveRequestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveRequestsRepository.findAll().size();
        // set the field null
        leaveRequests.setUpdatedAt(null);

        // Create the LeaveRequests, which fails.

        restLeaveRequestsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequests))
            )
            .andExpect(status().isBadRequest());

        List<LeaveRequests> leaveRequestsList = leaveRequestsRepository.findAll();
        assertThat(leaveRequestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveRequestsRepository.findAll().size();
        // set the field null
        leaveRequests.setVersion(null);

        // Create the LeaveRequests, which fails.

        restLeaveRequestsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequests))
            )
            .andExpect(status().isBadRequest());

        List<LeaveRequests> leaveRequestsList = leaveRequestsRepository.findAll();
        assertThat(leaveRequestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLeaveRequests() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList
        restLeaveRequestsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveRequests.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].requestStartDate").value(hasItem(DEFAULT_REQUEST_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].requestEndDate").value(hasItem(DEFAULT_REQUEST_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].isHalfDay").value(hasItem(DEFAULT_IS_HALF_DAY.booleanValue())))
            .andExpect(jsonPath("$.[*].statusDate").value(hasItem(DEFAULT_STATUS_DATE.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getLeaveRequests() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get the leaveRequests
        restLeaveRequestsMockMvc
            .perform(get(ENTITY_API_URL_ID, leaveRequests.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leaveRequests.getId().intValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.requestStartDate").value(DEFAULT_REQUEST_START_DATE.toString()))
            .andExpect(jsonPath("$.requestEndDate").value(DEFAULT_REQUEST_END_DATE.toString()))
            .andExpect(jsonPath("$.isHalfDay").value(DEFAULT_IS_HALF_DAY.booleanValue()))
            .andExpect(jsonPath("$.statusDate").value(DEFAULT_STATUS_DATE.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getLeaveRequestsByIdFiltering() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        Long id = leaveRequests.getId();

        defaultLeaveRequestsShouldBeFound("id.equals=" + id);
        defaultLeaveRequestsShouldNotBeFound("id.notEquals=" + id);

        defaultLeaveRequestsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLeaveRequestsShouldNotBeFound("id.greaterThan=" + id);

        defaultLeaveRequestsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLeaveRequestsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where createdAt equals to DEFAULT_CREATED_AT
        defaultLeaveRequestsShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the leaveRequestsList where createdAt equals to UPDATED_CREATED_AT
        defaultLeaveRequestsShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultLeaveRequestsShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the leaveRequestsList where createdAt equals to UPDATED_CREATED_AT
        defaultLeaveRequestsShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where createdAt is not null
        defaultLeaveRequestsShouldBeFound("createdAt.specified=true");

        // Get all the leaveRequestsList where createdAt is null
        defaultLeaveRequestsShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByRequestStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where requestStartDate equals to DEFAULT_REQUEST_START_DATE
        defaultLeaveRequestsShouldBeFound("requestStartDate.equals=" + DEFAULT_REQUEST_START_DATE);

        // Get all the leaveRequestsList where requestStartDate equals to UPDATED_REQUEST_START_DATE
        defaultLeaveRequestsShouldNotBeFound("requestStartDate.equals=" + UPDATED_REQUEST_START_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByRequestStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where requestStartDate in DEFAULT_REQUEST_START_DATE or UPDATED_REQUEST_START_DATE
        defaultLeaveRequestsShouldBeFound("requestStartDate.in=" + DEFAULT_REQUEST_START_DATE + "," + UPDATED_REQUEST_START_DATE);

        // Get all the leaveRequestsList where requestStartDate equals to UPDATED_REQUEST_START_DATE
        defaultLeaveRequestsShouldNotBeFound("requestStartDate.in=" + UPDATED_REQUEST_START_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByRequestStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where requestStartDate is not null
        defaultLeaveRequestsShouldBeFound("requestStartDate.specified=true");

        // Get all the leaveRequestsList where requestStartDate is null
        defaultLeaveRequestsShouldNotBeFound("requestStartDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByRequestStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where requestStartDate is greater than or equal to DEFAULT_REQUEST_START_DATE
        defaultLeaveRequestsShouldBeFound("requestStartDate.greaterThanOrEqual=" + DEFAULT_REQUEST_START_DATE);

        // Get all the leaveRequestsList where requestStartDate is greater than or equal to UPDATED_REQUEST_START_DATE
        defaultLeaveRequestsShouldNotBeFound("requestStartDate.greaterThanOrEqual=" + UPDATED_REQUEST_START_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByRequestStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where requestStartDate is less than or equal to DEFAULT_REQUEST_START_DATE
        defaultLeaveRequestsShouldBeFound("requestStartDate.lessThanOrEqual=" + DEFAULT_REQUEST_START_DATE);

        // Get all the leaveRequestsList where requestStartDate is less than or equal to SMALLER_REQUEST_START_DATE
        defaultLeaveRequestsShouldNotBeFound("requestStartDate.lessThanOrEqual=" + SMALLER_REQUEST_START_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByRequestStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where requestStartDate is less than DEFAULT_REQUEST_START_DATE
        defaultLeaveRequestsShouldNotBeFound("requestStartDate.lessThan=" + DEFAULT_REQUEST_START_DATE);

        // Get all the leaveRequestsList where requestStartDate is less than UPDATED_REQUEST_START_DATE
        defaultLeaveRequestsShouldBeFound("requestStartDate.lessThan=" + UPDATED_REQUEST_START_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByRequestStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where requestStartDate is greater than DEFAULT_REQUEST_START_DATE
        defaultLeaveRequestsShouldNotBeFound("requestStartDate.greaterThan=" + DEFAULT_REQUEST_START_DATE);

        // Get all the leaveRequestsList where requestStartDate is greater than SMALLER_REQUEST_START_DATE
        defaultLeaveRequestsShouldBeFound("requestStartDate.greaterThan=" + SMALLER_REQUEST_START_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByRequestEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where requestEndDate equals to DEFAULT_REQUEST_END_DATE
        defaultLeaveRequestsShouldBeFound("requestEndDate.equals=" + DEFAULT_REQUEST_END_DATE);

        // Get all the leaveRequestsList where requestEndDate equals to UPDATED_REQUEST_END_DATE
        defaultLeaveRequestsShouldNotBeFound("requestEndDate.equals=" + UPDATED_REQUEST_END_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByRequestEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where requestEndDate in DEFAULT_REQUEST_END_DATE or UPDATED_REQUEST_END_DATE
        defaultLeaveRequestsShouldBeFound("requestEndDate.in=" + DEFAULT_REQUEST_END_DATE + "," + UPDATED_REQUEST_END_DATE);

        // Get all the leaveRequestsList where requestEndDate equals to UPDATED_REQUEST_END_DATE
        defaultLeaveRequestsShouldNotBeFound("requestEndDate.in=" + UPDATED_REQUEST_END_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByRequestEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where requestEndDate is not null
        defaultLeaveRequestsShouldBeFound("requestEndDate.specified=true");

        // Get all the leaveRequestsList where requestEndDate is null
        defaultLeaveRequestsShouldNotBeFound("requestEndDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByRequestEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where requestEndDate is greater than or equal to DEFAULT_REQUEST_END_DATE
        defaultLeaveRequestsShouldBeFound("requestEndDate.greaterThanOrEqual=" + DEFAULT_REQUEST_END_DATE);

        // Get all the leaveRequestsList where requestEndDate is greater than or equal to UPDATED_REQUEST_END_DATE
        defaultLeaveRequestsShouldNotBeFound("requestEndDate.greaterThanOrEqual=" + UPDATED_REQUEST_END_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByRequestEndDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where requestEndDate is less than or equal to DEFAULT_REQUEST_END_DATE
        defaultLeaveRequestsShouldBeFound("requestEndDate.lessThanOrEqual=" + DEFAULT_REQUEST_END_DATE);

        // Get all the leaveRequestsList where requestEndDate is less than or equal to SMALLER_REQUEST_END_DATE
        defaultLeaveRequestsShouldNotBeFound("requestEndDate.lessThanOrEqual=" + SMALLER_REQUEST_END_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByRequestEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where requestEndDate is less than DEFAULT_REQUEST_END_DATE
        defaultLeaveRequestsShouldNotBeFound("requestEndDate.lessThan=" + DEFAULT_REQUEST_END_DATE);

        // Get all the leaveRequestsList where requestEndDate is less than UPDATED_REQUEST_END_DATE
        defaultLeaveRequestsShouldBeFound("requestEndDate.lessThan=" + UPDATED_REQUEST_END_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByRequestEndDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where requestEndDate is greater than DEFAULT_REQUEST_END_DATE
        defaultLeaveRequestsShouldNotBeFound("requestEndDate.greaterThan=" + DEFAULT_REQUEST_END_DATE);

        // Get all the leaveRequestsList where requestEndDate is greater than SMALLER_REQUEST_END_DATE
        defaultLeaveRequestsShouldBeFound("requestEndDate.greaterThan=" + SMALLER_REQUEST_END_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByIsHalfDayIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where isHalfDay equals to DEFAULT_IS_HALF_DAY
        defaultLeaveRequestsShouldBeFound("isHalfDay.equals=" + DEFAULT_IS_HALF_DAY);

        // Get all the leaveRequestsList where isHalfDay equals to UPDATED_IS_HALF_DAY
        defaultLeaveRequestsShouldNotBeFound("isHalfDay.equals=" + UPDATED_IS_HALF_DAY);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByIsHalfDayIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where isHalfDay in DEFAULT_IS_HALF_DAY or UPDATED_IS_HALF_DAY
        defaultLeaveRequestsShouldBeFound("isHalfDay.in=" + DEFAULT_IS_HALF_DAY + "," + UPDATED_IS_HALF_DAY);

        // Get all the leaveRequestsList where isHalfDay equals to UPDATED_IS_HALF_DAY
        defaultLeaveRequestsShouldNotBeFound("isHalfDay.in=" + UPDATED_IS_HALF_DAY);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByIsHalfDayIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where isHalfDay is not null
        defaultLeaveRequestsShouldBeFound("isHalfDay.specified=true");

        // Get all the leaveRequestsList where isHalfDay is null
        defaultLeaveRequestsShouldNotBeFound("isHalfDay.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByStatusDateIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where statusDate equals to DEFAULT_STATUS_DATE
        defaultLeaveRequestsShouldBeFound("statusDate.equals=" + DEFAULT_STATUS_DATE);

        // Get all the leaveRequestsList where statusDate equals to UPDATED_STATUS_DATE
        defaultLeaveRequestsShouldNotBeFound("statusDate.equals=" + UPDATED_STATUS_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByStatusDateIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where statusDate in DEFAULT_STATUS_DATE or UPDATED_STATUS_DATE
        defaultLeaveRequestsShouldBeFound("statusDate.in=" + DEFAULT_STATUS_DATE + "," + UPDATED_STATUS_DATE);

        // Get all the leaveRequestsList where statusDate equals to UPDATED_STATUS_DATE
        defaultLeaveRequestsShouldNotBeFound("statusDate.in=" + UPDATED_STATUS_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByStatusDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where statusDate is not null
        defaultLeaveRequestsShouldBeFound("statusDate.specified=true");

        // Get all the leaveRequestsList where statusDate is null
        defaultLeaveRequestsShouldNotBeFound("statusDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where comments equals to DEFAULT_COMMENTS
        defaultLeaveRequestsShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the leaveRequestsList where comments equals to UPDATED_COMMENTS
        defaultLeaveRequestsShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultLeaveRequestsShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the leaveRequestsList where comments equals to UPDATED_COMMENTS
        defaultLeaveRequestsShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where comments is not null
        defaultLeaveRequestsShouldBeFound("comments.specified=true");

        // Get all the leaveRequestsList where comments is null
        defaultLeaveRequestsShouldNotBeFound("comments.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByCommentsContainsSomething() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where comments contains DEFAULT_COMMENTS
        defaultLeaveRequestsShouldBeFound("comments.contains=" + DEFAULT_COMMENTS);

        // Get all the leaveRequestsList where comments contains UPDATED_COMMENTS
        defaultLeaveRequestsShouldNotBeFound("comments.contains=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where comments does not contain DEFAULT_COMMENTS
        defaultLeaveRequestsShouldNotBeFound("comments.doesNotContain=" + DEFAULT_COMMENTS);

        // Get all the leaveRequestsList where comments does not contain UPDATED_COMMENTS
        defaultLeaveRequestsShouldBeFound("comments.doesNotContain=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultLeaveRequestsShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the leaveRequestsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultLeaveRequestsShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultLeaveRequestsShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the leaveRequestsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultLeaveRequestsShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where updatedAt is not null
        defaultLeaveRequestsShouldBeFound("updatedAt.specified=true");

        // Get all the leaveRequestsList where updatedAt is null
        defaultLeaveRequestsShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByDeletedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where deletedAt equals to DEFAULT_DELETED_AT
        defaultLeaveRequestsShouldBeFound("deletedAt.equals=" + DEFAULT_DELETED_AT);

        // Get all the leaveRequestsList where deletedAt equals to UPDATED_DELETED_AT
        defaultLeaveRequestsShouldNotBeFound("deletedAt.equals=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByDeletedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where deletedAt in DEFAULT_DELETED_AT or UPDATED_DELETED_AT
        defaultLeaveRequestsShouldBeFound("deletedAt.in=" + DEFAULT_DELETED_AT + "," + UPDATED_DELETED_AT);

        // Get all the leaveRequestsList where deletedAt equals to UPDATED_DELETED_AT
        defaultLeaveRequestsShouldNotBeFound("deletedAt.in=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByDeletedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where deletedAt is not null
        defaultLeaveRequestsShouldBeFound("deletedAt.specified=true");

        // Get all the leaveRequestsList where deletedAt is null
        defaultLeaveRequestsShouldNotBeFound("deletedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where version equals to DEFAULT_VERSION
        defaultLeaveRequestsShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the leaveRequestsList where version equals to UPDATED_VERSION
        defaultLeaveRequestsShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultLeaveRequestsShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the leaveRequestsList where version equals to UPDATED_VERSION
        defaultLeaveRequestsShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where version is not null
        defaultLeaveRequestsShouldBeFound("version.specified=true");

        // Get all the leaveRequestsList where version is null
        defaultLeaveRequestsShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where version is greater than or equal to DEFAULT_VERSION
        defaultLeaveRequestsShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the leaveRequestsList where version is greater than or equal to UPDATED_VERSION
        defaultLeaveRequestsShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where version is less than or equal to DEFAULT_VERSION
        defaultLeaveRequestsShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the leaveRequestsList where version is less than or equal to SMALLER_VERSION
        defaultLeaveRequestsShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where version is less than DEFAULT_VERSION
        defaultLeaveRequestsShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the leaveRequestsList where version is less than UPDATED_VERSION
        defaultLeaveRequestsShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        // Get all the leaveRequestsList where version is greater than DEFAULT_VERSION
        defaultLeaveRequestsShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the leaveRequestsList where version is greater than SMALLER_VERSION
        defaultLeaveRequestsShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByLeaveDetailIsEqualToSomething() throws Exception {
        LeaveDetails leaveDetail;
        if (TestUtil.findAll(em, LeaveDetails.class).isEmpty()) {
            leaveRequestsRepository.saveAndFlush(leaveRequests);
            leaveDetail = LeaveDetailsResourceIT.createEntity(em);
        } else {
            leaveDetail = TestUtil.findAll(em, LeaveDetails.class).get(0);
        }
        em.persist(leaveDetail);
        em.flush();
        leaveRequests.setLeaveDetail(leaveDetail);
        leaveRequestsRepository.saveAndFlush(leaveRequests);
        Long leaveDetailId = leaveDetail.getId();

        // Get all the leaveRequestsList where leaveDetail equals to leaveDetailId
        defaultLeaveRequestsShouldBeFound("leaveDetailId.equals=" + leaveDetailId);

        // Get all the leaveRequestsList where leaveDetail equals to (leaveDetailId + 1)
        defaultLeaveRequestsShouldNotBeFound("leaveDetailId.equals=" + (leaveDetailId + 1));
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByLeaveStatusIsEqualToSomething() throws Exception {
        LeaveStatuses leaveStatus;
        if (TestUtil.findAll(em, LeaveStatuses.class).isEmpty()) {
            leaveRequestsRepository.saveAndFlush(leaveRequests);
            leaveStatus = LeaveStatusesResourceIT.createEntity(em);
        } else {
            leaveStatus = TestUtil.findAll(em, LeaveStatuses.class).get(0);
        }
        em.persist(leaveStatus);
        em.flush();
        leaveRequests.setLeaveStatus(leaveStatus);
        leaveRequestsRepository.saveAndFlush(leaveRequests);
        Long leaveStatusId = leaveStatus.getId();

        // Get all the leaveRequestsList where leaveStatus equals to leaveStatusId
        defaultLeaveRequestsShouldBeFound("leaveStatusId.equals=" + leaveStatusId);

        // Get all the leaveRequestsList where leaveStatus equals to (leaveStatusId + 1)
        defaultLeaveRequestsShouldNotBeFound("leaveStatusId.equals=" + (leaveStatusId + 1));
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByParentLeaveRequestIsEqualToSomething() throws Exception {
        LeaveRequests parentLeaveRequest;
        if (TestUtil.findAll(em, LeaveRequests.class).isEmpty()) {
            leaveRequestsRepository.saveAndFlush(leaveRequests);
            parentLeaveRequest = LeaveRequestsResourceIT.createEntity(em);
        } else {
            parentLeaveRequest = TestUtil.findAll(em, LeaveRequests.class).get(0);
        }
        em.persist(parentLeaveRequest);
        em.flush();
        leaveRequests.setParentLeaveRequest(parentLeaveRequest);
        leaveRequestsRepository.saveAndFlush(leaveRequests);
        Long parentLeaveRequestId = parentLeaveRequest.getId();

        // Get all the leaveRequestsList where parentLeaveRequest equals to parentLeaveRequestId
        defaultLeaveRequestsShouldBeFound("parentLeaveRequestId.equals=" + parentLeaveRequestId);

        // Get all the leaveRequestsList where parentLeaveRequest equals to (parentLeaveRequestId + 1)
        defaultLeaveRequestsShouldNotBeFound("parentLeaveRequestId.equals=" + (parentLeaveRequestId + 1));
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByLeaverequestapproversLeaverequestIsEqualToSomething() throws Exception {
        LeaveRequestApprovers leaverequestapproversLeaverequest;
        if (TestUtil.findAll(em, LeaveRequestApprovers.class).isEmpty()) {
            leaveRequestsRepository.saveAndFlush(leaveRequests);
            leaverequestapproversLeaverequest = LeaveRequestApproversResourceIT.createEntity(em);
        } else {
            leaverequestapproversLeaverequest = TestUtil.findAll(em, LeaveRequestApprovers.class).get(0);
        }
        em.persist(leaverequestapproversLeaverequest);
        em.flush();
        leaveRequests.addLeaverequestapproversLeaverequest(leaverequestapproversLeaverequest);
        leaveRequestsRepository.saveAndFlush(leaveRequests);
        Long leaverequestapproversLeaverequestId = leaverequestapproversLeaverequest.getId();

        // Get all the leaveRequestsList where leaverequestapproversLeaverequest equals to leaverequestapproversLeaverequestId
        defaultLeaveRequestsShouldBeFound("leaverequestapproversLeaverequestId.equals=" + leaverequestapproversLeaverequestId);

        // Get all the leaveRequestsList where leaverequestapproversLeaverequest equals to (leaverequestapproversLeaverequestId + 1)
        defaultLeaveRequestsShouldNotBeFound("leaverequestapproversLeaverequestId.equals=" + (leaverequestapproversLeaverequestId + 1));
    }

    @Test
    @Transactional
    void getAllLeaveRequestsByLeaverequestsParentleaverequestIsEqualToSomething() throws Exception {
        LeaveRequests leaverequestsParentleaverequest;
        if (TestUtil.findAll(em, LeaveRequests.class).isEmpty()) {
            leaveRequestsRepository.saveAndFlush(leaveRequests);
            leaverequestsParentleaverequest = LeaveRequestsResourceIT.createEntity(em);
        } else {
            leaverequestsParentleaverequest = TestUtil.findAll(em, LeaveRequests.class).get(0);
        }
        em.persist(leaverequestsParentleaverequest);
        em.flush();
        leaveRequests.addLeaverequestsParentleaverequest(leaverequestsParentleaverequest);
        leaveRequestsRepository.saveAndFlush(leaveRequests);
        Long leaverequestsParentleaverequestId = leaverequestsParentleaverequest.getId();

        // Get all the leaveRequestsList where leaverequestsParentleaverequest equals to leaverequestsParentleaverequestId
        defaultLeaveRequestsShouldBeFound("leaverequestsParentleaverequestId.equals=" + leaverequestsParentleaverequestId);

        // Get all the leaveRequestsList where leaverequestsParentleaverequest equals to (leaverequestsParentleaverequestId + 1)
        defaultLeaveRequestsShouldNotBeFound("leaverequestsParentleaverequestId.equals=" + (leaverequestsParentleaverequestId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLeaveRequestsShouldBeFound(String filter) throws Exception {
        restLeaveRequestsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveRequests.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].requestStartDate").value(hasItem(DEFAULT_REQUEST_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].requestEndDate").value(hasItem(DEFAULT_REQUEST_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].isHalfDay").value(hasItem(DEFAULT_IS_HALF_DAY.booleanValue())))
            .andExpect(jsonPath("$.[*].statusDate").value(hasItem(DEFAULT_STATUS_DATE.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restLeaveRequestsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLeaveRequestsShouldNotBeFound(String filter) throws Exception {
        restLeaveRequestsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLeaveRequestsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLeaveRequests() throws Exception {
        // Get the leaveRequests
        restLeaveRequestsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLeaveRequests() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        int databaseSizeBeforeUpdate = leaveRequestsRepository.findAll().size();

        // Update the leaveRequests
        LeaveRequests updatedLeaveRequests = leaveRequestsRepository.findById(leaveRequests.getId()).get();
        // Disconnect from session so that the updates on updatedLeaveRequests are not directly saved in db
        em.detach(updatedLeaveRequests);
        updatedLeaveRequests
            .createdAt(UPDATED_CREATED_AT)
            .requestStartDate(UPDATED_REQUEST_START_DATE)
            .requestEndDate(UPDATED_REQUEST_END_DATE)
            .isHalfDay(UPDATED_IS_HALF_DAY)
            .statusDate(UPDATED_STATUS_DATE)
            .comments(UPDATED_COMMENTS)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restLeaveRequestsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLeaveRequests.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLeaveRequests))
            )
            .andExpect(status().isOk());

        // Validate the LeaveRequests in the database
        List<LeaveRequests> leaveRequestsList = leaveRequestsRepository.findAll();
        assertThat(leaveRequestsList).hasSize(databaseSizeBeforeUpdate);
        LeaveRequests testLeaveRequests = leaveRequestsList.get(leaveRequestsList.size() - 1);
        assertThat(testLeaveRequests.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveRequests.getRequestStartDate()).isEqualTo(UPDATED_REQUEST_START_DATE);
        assertThat(testLeaveRequests.getRequestEndDate()).isEqualTo(UPDATED_REQUEST_END_DATE);
        assertThat(testLeaveRequests.getIsHalfDay()).isEqualTo(UPDATED_IS_HALF_DAY);
        assertThat(testLeaveRequests.getStatusDate()).isEqualTo(UPDATED_STATUS_DATE);
        assertThat(testLeaveRequests.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testLeaveRequests.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaveRequests.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testLeaveRequests.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingLeaveRequests() throws Exception {
        int databaseSizeBeforeUpdate = leaveRequestsRepository.findAll().size();
        leaveRequests.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveRequestsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leaveRequests.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequests))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveRequests in the database
        List<LeaveRequests> leaveRequestsList = leaveRequestsRepository.findAll();
        assertThat(leaveRequestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeaveRequests() throws Exception {
        int databaseSizeBeforeUpdate = leaveRequestsRepository.findAll().size();
        leaveRequests.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveRequestsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequests))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveRequests in the database
        List<LeaveRequests> leaveRequestsList = leaveRequestsRepository.findAll();
        assertThat(leaveRequestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeaveRequests() throws Exception {
        int databaseSizeBeforeUpdate = leaveRequestsRepository.findAll().size();
        leaveRequests.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveRequestsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequests))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveRequests in the database
        List<LeaveRequests> leaveRequestsList = leaveRequestsRepository.findAll();
        assertThat(leaveRequestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeaveRequestsWithPatch() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        int databaseSizeBeforeUpdate = leaveRequestsRepository.findAll().size();

        // Update the leaveRequests using partial update
        LeaveRequests partialUpdatedLeaveRequests = new LeaveRequests();
        partialUpdatedLeaveRequests.setId(leaveRequests.getId());

        partialUpdatedLeaveRequests
            .createdAt(UPDATED_CREATED_AT)
            .requestStartDate(UPDATED_REQUEST_START_DATE)
            .isHalfDay(UPDATED_IS_HALF_DAY)
            .statusDate(UPDATED_STATUS_DATE)
            .comments(UPDATED_COMMENTS);

        restLeaveRequestsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveRequests.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveRequests))
            )
            .andExpect(status().isOk());

        // Validate the LeaveRequests in the database
        List<LeaveRequests> leaveRequestsList = leaveRequestsRepository.findAll();
        assertThat(leaveRequestsList).hasSize(databaseSizeBeforeUpdate);
        LeaveRequests testLeaveRequests = leaveRequestsList.get(leaveRequestsList.size() - 1);
        assertThat(testLeaveRequests.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveRequests.getRequestStartDate()).isEqualTo(UPDATED_REQUEST_START_DATE);
        assertThat(testLeaveRequests.getRequestEndDate()).isEqualTo(DEFAULT_REQUEST_END_DATE);
        assertThat(testLeaveRequests.getIsHalfDay()).isEqualTo(UPDATED_IS_HALF_DAY);
        assertThat(testLeaveRequests.getStatusDate()).isEqualTo(UPDATED_STATUS_DATE);
        assertThat(testLeaveRequests.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testLeaveRequests.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testLeaveRequests.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testLeaveRequests.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateLeaveRequestsWithPatch() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        int databaseSizeBeforeUpdate = leaveRequestsRepository.findAll().size();

        // Update the leaveRequests using partial update
        LeaveRequests partialUpdatedLeaveRequests = new LeaveRequests();
        partialUpdatedLeaveRequests.setId(leaveRequests.getId());

        partialUpdatedLeaveRequests
            .createdAt(UPDATED_CREATED_AT)
            .requestStartDate(UPDATED_REQUEST_START_DATE)
            .requestEndDate(UPDATED_REQUEST_END_DATE)
            .isHalfDay(UPDATED_IS_HALF_DAY)
            .statusDate(UPDATED_STATUS_DATE)
            .comments(UPDATED_COMMENTS)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restLeaveRequestsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveRequests.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveRequests))
            )
            .andExpect(status().isOk());

        // Validate the LeaveRequests in the database
        List<LeaveRequests> leaveRequestsList = leaveRequestsRepository.findAll();
        assertThat(leaveRequestsList).hasSize(databaseSizeBeforeUpdate);
        LeaveRequests testLeaveRequests = leaveRequestsList.get(leaveRequestsList.size() - 1);
        assertThat(testLeaveRequests.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveRequests.getRequestStartDate()).isEqualTo(UPDATED_REQUEST_START_DATE);
        assertThat(testLeaveRequests.getRequestEndDate()).isEqualTo(UPDATED_REQUEST_END_DATE);
        assertThat(testLeaveRequests.getIsHalfDay()).isEqualTo(UPDATED_IS_HALF_DAY);
        assertThat(testLeaveRequests.getStatusDate()).isEqualTo(UPDATED_STATUS_DATE);
        assertThat(testLeaveRequests.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testLeaveRequests.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaveRequests.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testLeaveRequests.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingLeaveRequests() throws Exception {
        int databaseSizeBeforeUpdate = leaveRequestsRepository.findAll().size();
        leaveRequests.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveRequestsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leaveRequests.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequests))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveRequests in the database
        List<LeaveRequests> leaveRequestsList = leaveRequestsRepository.findAll();
        assertThat(leaveRequestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeaveRequests() throws Exception {
        int databaseSizeBeforeUpdate = leaveRequestsRepository.findAll().size();
        leaveRequests.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveRequestsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequests))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveRequests in the database
        List<LeaveRequests> leaveRequestsList = leaveRequestsRepository.findAll();
        assertThat(leaveRequestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeaveRequests() throws Exception {
        int databaseSizeBeforeUpdate = leaveRequestsRepository.findAll().size();
        leaveRequests.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveRequestsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequests))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveRequests in the database
        List<LeaveRequests> leaveRequestsList = leaveRequestsRepository.findAll();
        assertThat(leaveRequestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLeaveRequests() throws Exception {
        // Initialize the database
        leaveRequestsRepository.saveAndFlush(leaveRequests);

        int databaseSizeBeforeDelete = leaveRequestsRepository.findAll().size();

        // Delete the leaveRequests
        restLeaveRequestsMockMvc
            .perform(delete(ENTITY_API_URL_ID, leaveRequests.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeaveRequests> leaveRequestsList = leaveRequestsRepository.findAll();
        assertThat(leaveRequestsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
