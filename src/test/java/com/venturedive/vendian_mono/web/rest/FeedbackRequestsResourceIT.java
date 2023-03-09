package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.FeedbackRequests;
import com.venturedive.vendian_mono.domain.FeedbackRespondents;
import com.venturedive.vendian_mono.repository.FeedbackRequestsRepository;
import com.venturedive.vendian_mono.service.criteria.FeedbackRequestsCriteria;
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
 * Integration tests for the {@link FeedbackRequestsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FeedbackRequestsResourceIT {

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;
    private static final Integer SMALLER_STATUS = 1 - 1;

    private static final Boolean DEFAULT_ISREPORTAVAILABLE = false;
    private static final Boolean UPDATED_ISREPORTAVAILABLE = true;

    private static final String DEFAULT_REPORTPATH = "AAAAAAAAAA";
    private static final String UPDATED_REPORTPATH = "BBBBBBBBBB";

    private static final Instant DEFAULT_APPROVEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_APPROVEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_EXPIREDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPIREDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/feedback-requests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FeedbackRequestsRepository feedbackRequestsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFeedbackRequestsMockMvc;

    private FeedbackRequests feedbackRequests;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FeedbackRequests createEntity(EntityManager em) {
        FeedbackRequests feedbackRequests = new FeedbackRequests()
            .status(DEFAULT_STATUS)
            .isreportavailable(DEFAULT_ISREPORTAVAILABLE)
            .reportpath(DEFAULT_REPORTPATH)
            .approvedat(DEFAULT_APPROVEDAT)
            .expiredat(DEFAULT_EXPIREDAT)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        return feedbackRequests;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FeedbackRequests createUpdatedEntity(EntityManager em) {
        FeedbackRequests feedbackRequests = new FeedbackRequests()
            .status(UPDATED_STATUS)
            .isreportavailable(UPDATED_ISREPORTAVAILABLE)
            .reportpath(UPDATED_REPORTPATH)
            .approvedat(UPDATED_APPROVEDAT)
            .expiredat(UPDATED_EXPIREDAT)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        return feedbackRequests;
    }

    @BeforeEach
    public void initTest() {
        feedbackRequests = createEntity(em);
    }

    @Test
    @Transactional
    void createFeedbackRequests() throws Exception {
        int databaseSizeBeforeCreate = feedbackRequestsRepository.findAll().size();
        // Create the FeedbackRequests
        restFeedbackRequestsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackRequests))
            )
            .andExpect(status().isCreated());

        // Validate the FeedbackRequests in the database
        List<FeedbackRequests> feedbackRequestsList = feedbackRequestsRepository.findAll();
        assertThat(feedbackRequestsList).hasSize(databaseSizeBeforeCreate + 1);
        FeedbackRequests testFeedbackRequests = feedbackRequestsList.get(feedbackRequestsList.size() - 1);
        assertThat(testFeedbackRequests.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testFeedbackRequests.getIsreportavailable()).isEqualTo(DEFAULT_ISREPORTAVAILABLE);
        assertThat(testFeedbackRequests.getReportpath()).isEqualTo(DEFAULT_REPORTPATH);
        assertThat(testFeedbackRequests.getApprovedat()).isEqualTo(DEFAULT_APPROVEDAT);
        assertThat(testFeedbackRequests.getExpiredat()).isEqualTo(DEFAULT_EXPIREDAT);
        assertThat(testFeedbackRequests.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testFeedbackRequests.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createFeedbackRequestsWithExistingId() throws Exception {
        // Create the FeedbackRequests with an existing ID
        feedbackRequests.setId(1L);

        int databaseSizeBeforeCreate = feedbackRequestsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeedbackRequestsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackRequests))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedbackRequests in the database
        List<FeedbackRequests> feedbackRequestsList = feedbackRequestsRepository.findAll();
        assertThat(feedbackRequestsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedbackRequestsRepository.findAll().size();
        // set the field null
        feedbackRequests.setCreatedat(null);

        // Create the FeedbackRequests, which fails.

        restFeedbackRequestsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackRequests))
            )
            .andExpect(status().isBadRequest());

        List<FeedbackRequests> feedbackRequestsList = feedbackRequestsRepository.findAll();
        assertThat(feedbackRequestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedbackRequestsRepository.findAll().size();
        // set the field null
        feedbackRequests.setUpdatedat(null);

        // Create the FeedbackRequests, which fails.

        restFeedbackRequestsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackRequests))
            )
            .andExpect(status().isBadRequest());

        List<FeedbackRequests> feedbackRequestsList = feedbackRequestsRepository.findAll();
        assertThat(feedbackRequestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFeedbackRequests() throws Exception {
        // Initialize the database
        feedbackRequestsRepository.saveAndFlush(feedbackRequests);

        // Get all the feedbackRequestsList
        restFeedbackRequestsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feedbackRequests.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].isreportavailable").value(hasItem(DEFAULT_ISREPORTAVAILABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].reportpath").value(hasItem(DEFAULT_REPORTPATH)))
            .andExpect(jsonPath("$.[*].approvedat").value(hasItem(DEFAULT_APPROVEDAT.toString())))
            .andExpect(jsonPath("$.[*].expiredat").value(hasItem(DEFAULT_EXPIREDAT.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getFeedbackRequests() throws Exception {
        // Initialize the database
        feedbackRequestsRepository.saveAndFlush(feedbackRequests);

        // Get the feedbackRequests
        restFeedbackRequestsMockMvc
            .perform(get(ENTITY_API_URL_ID, feedbackRequests.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(feedbackRequests.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.isreportavailable").value(DEFAULT_ISREPORTAVAILABLE.booleanValue()))
            .andExpect(jsonPath("$.reportpath").value(DEFAULT_REPORTPATH))
            .andExpect(jsonPath("$.approvedat").value(DEFAULT_APPROVEDAT.toString()))
            .andExpect(jsonPath("$.expiredat").value(DEFAULT_EXPIREDAT.toString()))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getFeedbackRequestsByIdFiltering() throws Exception {
        // Initialize the database
        feedbackRequestsRepository.saveAndFlush(feedbackRequests);

        Long id = feedbackRequests.getId();

        defaultFeedbackRequestsShouldBeFound("id.equals=" + id);
        defaultFeedbackRequestsShouldNotBeFound("id.notEquals=" + id);

        defaultFeedbackRequestsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFeedbackRequestsShouldNotBeFound("id.greaterThan=" + id);

        defaultFeedbackRequestsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFeedbackRequestsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFeedbackRequestsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackRequestsRepository.saveAndFlush(feedbackRequests);

        // Get all the feedbackRequestsList where status equals to DEFAULT_STATUS
        defaultFeedbackRequestsShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the feedbackRequestsList where status equals to UPDATED_STATUS
        defaultFeedbackRequestsShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllFeedbackRequestsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackRequestsRepository.saveAndFlush(feedbackRequests);

        // Get all the feedbackRequestsList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultFeedbackRequestsShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the feedbackRequestsList where status equals to UPDATED_STATUS
        defaultFeedbackRequestsShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllFeedbackRequestsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackRequestsRepository.saveAndFlush(feedbackRequests);

        // Get all the feedbackRequestsList where status is not null
        defaultFeedbackRequestsShouldBeFound("status.specified=true");

        // Get all the feedbackRequestsList where status is null
        defaultFeedbackRequestsShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedbackRequestsByStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feedbackRequestsRepository.saveAndFlush(feedbackRequests);

        // Get all the feedbackRequestsList where status is greater than or equal to DEFAULT_STATUS
        defaultFeedbackRequestsShouldBeFound("status.greaterThanOrEqual=" + DEFAULT_STATUS);

        // Get all the feedbackRequestsList where status is greater than or equal to UPDATED_STATUS
        defaultFeedbackRequestsShouldNotBeFound("status.greaterThanOrEqual=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllFeedbackRequestsByStatusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feedbackRequestsRepository.saveAndFlush(feedbackRequests);

        // Get all the feedbackRequestsList where status is less than or equal to DEFAULT_STATUS
        defaultFeedbackRequestsShouldBeFound("status.lessThanOrEqual=" + DEFAULT_STATUS);

        // Get all the feedbackRequestsList where status is less than or equal to SMALLER_STATUS
        defaultFeedbackRequestsShouldNotBeFound("status.lessThanOrEqual=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    void getAllFeedbackRequestsByStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        feedbackRequestsRepository.saveAndFlush(feedbackRequests);

        // Get all the feedbackRequestsList where status is less than DEFAULT_STATUS
        defaultFeedbackRequestsShouldNotBeFound("status.lessThan=" + DEFAULT_STATUS);

        // Get all the feedbackRequestsList where status is less than UPDATED_STATUS
        defaultFeedbackRequestsShouldBeFound("status.lessThan=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllFeedbackRequestsByStatusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        feedbackRequestsRepository.saveAndFlush(feedbackRequests);

        // Get all the feedbackRequestsList where status is greater than DEFAULT_STATUS
        defaultFeedbackRequestsShouldNotBeFound("status.greaterThan=" + DEFAULT_STATUS);

        // Get all the feedbackRequestsList where status is greater than SMALLER_STATUS
        defaultFeedbackRequestsShouldBeFound("status.greaterThan=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    void getAllFeedbackRequestsByIsreportavailableIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackRequestsRepository.saveAndFlush(feedbackRequests);

        // Get all the feedbackRequestsList where isreportavailable equals to DEFAULT_ISREPORTAVAILABLE
        defaultFeedbackRequestsShouldBeFound("isreportavailable.equals=" + DEFAULT_ISREPORTAVAILABLE);

        // Get all the feedbackRequestsList where isreportavailable equals to UPDATED_ISREPORTAVAILABLE
        defaultFeedbackRequestsShouldNotBeFound("isreportavailable.equals=" + UPDATED_ISREPORTAVAILABLE);
    }

    @Test
    @Transactional
    void getAllFeedbackRequestsByIsreportavailableIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackRequestsRepository.saveAndFlush(feedbackRequests);

        // Get all the feedbackRequestsList where isreportavailable in DEFAULT_ISREPORTAVAILABLE or UPDATED_ISREPORTAVAILABLE
        defaultFeedbackRequestsShouldBeFound("isreportavailable.in=" + DEFAULT_ISREPORTAVAILABLE + "," + UPDATED_ISREPORTAVAILABLE);

        // Get all the feedbackRequestsList where isreportavailable equals to UPDATED_ISREPORTAVAILABLE
        defaultFeedbackRequestsShouldNotBeFound("isreportavailable.in=" + UPDATED_ISREPORTAVAILABLE);
    }

    @Test
    @Transactional
    void getAllFeedbackRequestsByIsreportavailableIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackRequestsRepository.saveAndFlush(feedbackRequests);

        // Get all the feedbackRequestsList where isreportavailable is not null
        defaultFeedbackRequestsShouldBeFound("isreportavailable.specified=true");

        // Get all the feedbackRequestsList where isreportavailable is null
        defaultFeedbackRequestsShouldNotBeFound("isreportavailable.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedbackRequestsByReportpathIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackRequestsRepository.saveAndFlush(feedbackRequests);

        // Get all the feedbackRequestsList where reportpath equals to DEFAULT_REPORTPATH
        defaultFeedbackRequestsShouldBeFound("reportpath.equals=" + DEFAULT_REPORTPATH);

        // Get all the feedbackRequestsList where reportpath equals to UPDATED_REPORTPATH
        defaultFeedbackRequestsShouldNotBeFound("reportpath.equals=" + UPDATED_REPORTPATH);
    }

    @Test
    @Transactional
    void getAllFeedbackRequestsByReportpathIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackRequestsRepository.saveAndFlush(feedbackRequests);

        // Get all the feedbackRequestsList where reportpath in DEFAULT_REPORTPATH or UPDATED_REPORTPATH
        defaultFeedbackRequestsShouldBeFound("reportpath.in=" + DEFAULT_REPORTPATH + "," + UPDATED_REPORTPATH);

        // Get all the feedbackRequestsList where reportpath equals to UPDATED_REPORTPATH
        defaultFeedbackRequestsShouldNotBeFound("reportpath.in=" + UPDATED_REPORTPATH);
    }

    @Test
    @Transactional
    void getAllFeedbackRequestsByReportpathIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackRequestsRepository.saveAndFlush(feedbackRequests);

        // Get all the feedbackRequestsList where reportpath is not null
        defaultFeedbackRequestsShouldBeFound("reportpath.specified=true");

        // Get all the feedbackRequestsList where reportpath is null
        defaultFeedbackRequestsShouldNotBeFound("reportpath.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedbackRequestsByReportpathContainsSomething() throws Exception {
        // Initialize the database
        feedbackRequestsRepository.saveAndFlush(feedbackRequests);

        // Get all the feedbackRequestsList where reportpath contains DEFAULT_REPORTPATH
        defaultFeedbackRequestsShouldBeFound("reportpath.contains=" + DEFAULT_REPORTPATH);

        // Get all the feedbackRequestsList where reportpath contains UPDATED_REPORTPATH
        defaultFeedbackRequestsShouldNotBeFound("reportpath.contains=" + UPDATED_REPORTPATH);
    }

    @Test
    @Transactional
    void getAllFeedbackRequestsByReportpathNotContainsSomething() throws Exception {
        // Initialize the database
        feedbackRequestsRepository.saveAndFlush(feedbackRequests);

        // Get all the feedbackRequestsList where reportpath does not contain DEFAULT_REPORTPATH
        defaultFeedbackRequestsShouldNotBeFound("reportpath.doesNotContain=" + DEFAULT_REPORTPATH);

        // Get all the feedbackRequestsList where reportpath does not contain UPDATED_REPORTPATH
        defaultFeedbackRequestsShouldBeFound("reportpath.doesNotContain=" + UPDATED_REPORTPATH);
    }

    @Test
    @Transactional
    void getAllFeedbackRequestsByApprovedatIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackRequestsRepository.saveAndFlush(feedbackRequests);

        // Get all the feedbackRequestsList where approvedat equals to DEFAULT_APPROVEDAT
        defaultFeedbackRequestsShouldBeFound("approvedat.equals=" + DEFAULT_APPROVEDAT);

        // Get all the feedbackRequestsList where approvedat equals to UPDATED_APPROVEDAT
        defaultFeedbackRequestsShouldNotBeFound("approvedat.equals=" + UPDATED_APPROVEDAT);
    }

    @Test
    @Transactional
    void getAllFeedbackRequestsByApprovedatIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackRequestsRepository.saveAndFlush(feedbackRequests);

        // Get all the feedbackRequestsList where approvedat in DEFAULT_APPROVEDAT or UPDATED_APPROVEDAT
        defaultFeedbackRequestsShouldBeFound("approvedat.in=" + DEFAULT_APPROVEDAT + "," + UPDATED_APPROVEDAT);

        // Get all the feedbackRequestsList where approvedat equals to UPDATED_APPROVEDAT
        defaultFeedbackRequestsShouldNotBeFound("approvedat.in=" + UPDATED_APPROVEDAT);
    }

    @Test
    @Transactional
    void getAllFeedbackRequestsByApprovedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackRequestsRepository.saveAndFlush(feedbackRequests);

        // Get all the feedbackRequestsList where approvedat is not null
        defaultFeedbackRequestsShouldBeFound("approvedat.specified=true");

        // Get all the feedbackRequestsList where approvedat is null
        defaultFeedbackRequestsShouldNotBeFound("approvedat.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedbackRequestsByExpiredatIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackRequestsRepository.saveAndFlush(feedbackRequests);

        // Get all the feedbackRequestsList where expiredat equals to DEFAULT_EXPIREDAT
        defaultFeedbackRequestsShouldBeFound("expiredat.equals=" + DEFAULT_EXPIREDAT);

        // Get all the feedbackRequestsList where expiredat equals to UPDATED_EXPIREDAT
        defaultFeedbackRequestsShouldNotBeFound("expiredat.equals=" + UPDATED_EXPIREDAT);
    }

    @Test
    @Transactional
    void getAllFeedbackRequestsByExpiredatIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackRequestsRepository.saveAndFlush(feedbackRequests);

        // Get all the feedbackRequestsList where expiredat in DEFAULT_EXPIREDAT or UPDATED_EXPIREDAT
        defaultFeedbackRequestsShouldBeFound("expiredat.in=" + DEFAULT_EXPIREDAT + "," + UPDATED_EXPIREDAT);

        // Get all the feedbackRequestsList where expiredat equals to UPDATED_EXPIREDAT
        defaultFeedbackRequestsShouldNotBeFound("expiredat.in=" + UPDATED_EXPIREDAT);
    }

    @Test
    @Transactional
    void getAllFeedbackRequestsByExpiredatIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackRequestsRepository.saveAndFlush(feedbackRequests);

        // Get all the feedbackRequestsList where expiredat is not null
        defaultFeedbackRequestsShouldBeFound("expiredat.specified=true");

        // Get all the feedbackRequestsList where expiredat is null
        defaultFeedbackRequestsShouldNotBeFound("expiredat.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedbackRequestsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackRequestsRepository.saveAndFlush(feedbackRequests);

        // Get all the feedbackRequestsList where createdat equals to DEFAULT_CREATEDAT
        defaultFeedbackRequestsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the feedbackRequestsList where createdat equals to UPDATED_CREATEDAT
        defaultFeedbackRequestsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllFeedbackRequestsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackRequestsRepository.saveAndFlush(feedbackRequests);

        // Get all the feedbackRequestsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultFeedbackRequestsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the feedbackRequestsList where createdat equals to UPDATED_CREATEDAT
        defaultFeedbackRequestsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllFeedbackRequestsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackRequestsRepository.saveAndFlush(feedbackRequests);

        // Get all the feedbackRequestsList where createdat is not null
        defaultFeedbackRequestsShouldBeFound("createdat.specified=true");

        // Get all the feedbackRequestsList where createdat is null
        defaultFeedbackRequestsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedbackRequestsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackRequestsRepository.saveAndFlush(feedbackRequests);

        // Get all the feedbackRequestsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultFeedbackRequestsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the feedbackRequestsList where updatedat equals to UPDATED_UPDATEDAT
        defaultFeedbackRequestsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllFeedbackRequestsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackRequestsRepository.saveAndFlush(feedbackRequests);

        // Get all the feedbackRequestsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultFeedbackRequestsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the feedbackRequestsList where updatedat equals to UPDATED_UPDATEDAT
        defaultFeedbackRequestsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllFeedbackRequestsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackRequestsRepository.saveAndFlush(feedbackRequests);

        // Get all the feedbackRequestsList where updatedat is not null
        defaultFeedbackRequestsShouldBeFound("updatedat.specified=true");

        // Get all the feedbackRequestsList where updatedat is null
        defaultFeedbackRequestsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedbackRequestsByEmployeeIsEqualToSomething() throws Exception {
        Employees employee;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            feedbackRequestsRepository.saveAndFlush(feedbackRequests);
            employee = EmployeesResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employee);
        em.flush();
        feedbackRequests.setEmployee(employee);
        feedbackRequestsRepository.saveAndFlush(feedbackRequests);
        Long employeeId = employee.getId();

        // Get all the feedbackRequestsList where employee equals to employeeId
        defaultFeedbackRequestsShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the feedbackRequestsList where employee equals to (employeeId + 1)
        defaultFeedbackRequestsShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    @Test
    @Transactional
    void getAllFeedbackRequestsByLinemanagerIsEqualToSomething() throws Exception {
        Employees linemanager;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            feedbackRequestsRepository.saveAndFlush(feedbackRequests);
            linemanager = EmployeesResourceIT.createEntity(em);
        } else {
            linemanager = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(linemanager);
        em.flush();
        feedbackRequests.setLinemanager(linemanager);
        feedbackRequestsRepository.saveAndFlush(feedbackRequests);
        Long linemanagerId = linemanager.getId();

        // Get all the feedbackRequestsList where linemanager equals to linemanagerId
        defaultFeedbackRequestsShouldBeFound("linemanagerId.equals=" + linemanagerId);

        // Get all the feedbackRequestsList where linemanager equals to (linemanagerId + 1)
        defaultFeedbackRequestsShouldNotBeFound("linemanagerId.equals=" + (linemanagerId + 1));
    }

    @Test
    @Transactional
    void getAllFeedbackRequestsByFeedbackrespondentsRequestIsEqualToSomething() throws Exception {
        FeedbackRespondents feedbackrespondentsRequest;
        if (TestUtil.findAll(em, FeedbackRespondents.class).isEmpty()) {
            feedbackRequestsRepository.saveAndFlush(feedbackRequests);
            feedbackrespondentsRequest = FeedbackRespondentsResourceIT.createEntity(em);
        } else {
            feedbackrespondentsRequest = TestUtil.findAll(em, FeedbackRespondents.class).get(0);
        }
        em.persist(feedbackrespondentsRequest);
        em.flush();
        feedbackRequests.addFeedbackrespondentsRequest(feedbackrespondentsRequest);
        feedbackRequestsRepository.saveAndFlush(feedbackRequests);
        Long feedbackrespondentsRequestId = feedbackrespondentsRequest.getId();

        // Get all the feedbackRequestsList where feedbackrespondentsRequest equals to feedbackrespondentsRequestId
        defaultFeedbackRequestsShouldBeFound("feedbackrespondentsRequestId.equals=" + feedbackrespondentsRequestId);

        // Get all the feedbackRequestsList where feedbackrespondentsRequest equals to (feedbackrespondentsRequestId + 1)
        defaultFeedbackRequestsShouldNotBeFound("feedbackrespondentsRequestId.equals=" + (feedbackrespondentsRequestId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFeedbackRequestsShouldBeFound(String filter) throws Exception {
        restFeedbackRequestsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feedbackRequests.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].isreportavailable").value(hasItem(DEFAULT_ISREPORTAVAILABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].reportpath").value(hasItem(DEFAULT_REPORTPATH)))
            .andExpect(jsonPath("$.[*].approvedat").value(hasItem(DEFAULT_APPROVEDAT.toString())))
            .andExpect(jsonPath("$.[*].expiredat").value(hasItem(DEFAULT_EXPIREDAT.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restFeedbackRequestsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFeedbackRequestsShouldNotBeFound(String filter) throws Exception {
        restFeedbackRequestsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFeedbackRequestsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFeedbackRequests() throws Exception {
        // Get the feedbackRequests
        restFeedbackRequestsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFeedbackRequests() throws Exception {
        // Initialize the database
        feedbackRequestsRepository.saveAndFlush(feedbackRequests);

        int databaseSizeBeforeUpdate = feedbackRequestsRepository.findAll().size();

        // Update the feedbackRequests
        FeedbackRequests updatedFeedbackRequests = feedbackRequestsRepository.findById(feedbackRequests.getId()).get();
        // Disconnect from session so that the updates on updatedFeedbackRequests are not directly saved in db
        em.detach(updatedFeedbackRequests);
        updatedFeedbackRequests
            .status(UPDATED_STATUS)
            .isreportavailable(UPDATED_ISREPORTAVAILABLE)
            .reportpath(UPDATED_REPORTPATH)
            .approvedat(UPDATED_APPROVEDAT)
            .expiredat(UPDATED_EXPIREDAT)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restFeedbackRequestsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFeedbackRequests.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFeedbackRequests))
            )
            .andExpect(status().isOk());

        // Validate the FeedbackRequests in the database
        List<FeedbackRequests> feedbackRequestsList = feedbackRequestsRepository.findAll();
        assertThat(feedbackRequestsList).hasSize(databaseSizeBeforeUpdate);
        FeedbackRequests testFeedbackRequests = feedbackRequestsList.get(feedbackRequestsList.size() - 1);
        assertThat(testFeedbackRequests.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testFeedbackRequests.getIsreportavailable()).isEqualTo(UPDATED_ISREPORTAVAILABLE);
        assertThat(testFeedbackRequests.getReportpath()).isEqualTo(UPDATED_REPORTPATH);
        assertThat(testFeedbackRequests.getApprovedat()).isEqualTo(UPDATED_APPROVEDAT);
        assertThat(testFeedbackRequests.getExpiredat()).isEqualTo(UPDATED_EXPIREDAT);
        assertThat(testFeedbackRequests.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testFeedbackRequests.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingFeedbackRequests() throws Exception {
        int databaseSizeBeforeUpdate = feedbackRequestsRepository.findAll().size();
        feedbackRequests.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeedbackRequestsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, feedbackRequests.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackRequests))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedbackRequests in the database
        List<FeedbackRequests> feedbackRequestsList = feedbackRequestsRepository.findAll();
        assertThat(feedbackRequestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFeedbackRequests() throws Exception {
        int databaseSizeBeforeUpdate = feedbackRequestsRepository.findAll().size();
        feedbackRequests.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedbackRequestsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackRequests))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedbackRequests in the database
        List<FeedbackRequests> feedbackRequestsList = feedbackRequestsRepository.findAll();
        assertThat(feedbackRequestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFeedbackRequests() throws Exception {
        int databaseSizeBeforeUpdate = feedbackRequestsRepository.findAll().size();
        feedbackRequests.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedbackRequestsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackRequests))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FeedbackRequests in the database
        List<FeedbackRequests> feedbackRequestsList = feedbackRequestsRepository.findAll();
        assertThat(feedbackRequestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFeedbackRequestsWithPatch() throws Exception {
        // Initialize the database
        feedbackRequestsRepository.saveAndFlush(feedbackRequests);

        int databaseSizeBeforeUpdate = feedbackRequestsRepository.findAll().size();

        // Update the feedbackRequests using partial update
        FeedbackRequests partialUpdatedFeedbackRequests = new FeedbackRequests();
        partialUpdatedFeedbackRequests.setId(feedbackRequests.getId());

        partialUpdatedFeedbackRequests.status(UPDATED_STATUS).approvedat(UPDATED_APPROVEDAT);

        restFeedbackRequestsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFeedbackRequests.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFeedbackRequests))
            )
            .andExpect(status().isOk());

        // Validate the FeedbackRequests in the database
        List<FeedbackRequests> feedbackRequestsList = feedbackRequestsRepository.findAll();
        assertThat(feedbackRequestsList).hasSize(databaseSizeBeforeUpdate);
        FeedbackRequests testFeedbackRequests = feedbackRequestsList.get(feedbackRequestsList.size() - 1);
        assertThat(testFeedbackRequests.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testFeedbackRequests.getIsreportavailable()).isEqualTo(DEFAULT_ISREPORTAVAILABLE);
        assertThat(testFeedbackRequests.getReportpath()).isEqualTo(DEFAULT_REPORTPATH);
        assertThat(testFeedbackRequests.getApprovedat()).isEqualTo(UPDATED_APPROVEDAT);
        assertThat(testFeedbackRequests.getExpiredat()).isEqualTo(DEFAULT_EXPIREDAT);
        assertThat(testFeedbackRequests.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testFeedbackRequests.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateFeedbackRequestsWithPatch() throws Exception {
        // Initialize the database
        feedbackRequestsRepository.saveAndFlush(feedbackRequests);

        int databaseSizeBeforeUpdate = feedbackRequestsRepository.findAll().size();

        // Update the feedbackRequests using partial update
        FeedbackRequests partialUpdatedFeedbackRequests = new FeedbackRequests();
        partialUpdatedFeedbackRequests.setId(feedbackRequests.getId());

        partialUpdatedFeedbackRequests
            .status(UPDATED_STATUS)
            .isreportavailable(UPDATED_ISREPORTAVAILABLE)
            .reportpath(UPDATED_REPORTPATH)
            .approvedat(UPDATED_APPROVEDAT)
            .expiredat(UPDATED_EXPIREDAT)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restFeedbackRequestsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFeedbackRequests.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFeedbackRequests))
            )
            .andExpect(status().isOk());

        // Validate the FeedbackRequests in the database
        List<FeedbackRequests> feedbackRequestsList = feedbackRequestsRepository.findAll();
        assertThat(feedbackRequestsList).hasSize(databaseSizeBeforeUpdate);
        FeedbackRequests testFeedbackRequests = feedbackRequestsList.get(feedbackRequestsList.size() - 1);
        assertThat(testFeedbackRequests.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testFeedbackRequests.getIsreportavailable()).isEqualTo(UPDATED_ISREPORTAVAILABLE);
        assertThat(testFeedbackRequests.getReportpath()).isEqualTo(UPDATED_REPORTPATH);
        assertThat(testFeedbackRequests.getApprovedat()).isEqualTo(UPDATED_APPROVEDAT);
        assertThat(testFeedbackRequests.getExpiredat()).isEqualTo(UPDATED_EXPIREDAT);
        assertThat(testFeedbackRequests.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testFeedbackRequests.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingFeedbackRequests() throws Exception {
        int databaseSizeBeforeUpdate = feedbackRequestsRepository.findAll().size();
        feedbackRequests.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeedbackRequestsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, feedbackRequests.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(feedbackRequests))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedbackRequests in the database
        List<FeedbackRequests> feedbackRequestsList = feedbackRequestsRepository.findAll();
        assertThat(feedbackRequestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFeedbackRequests() throws Exception {
        int databaseSizeBeforeUpdate = feedbackRequestsRepository.findAll().size();
        feedbackRequests.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedbackRequestsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(feedbackRequests))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedbackRequests in the database
        List<FeedbackRequests> feedbackRequestsList = feedbackRequestsRepository.findAll();
        assertThat(feedbackRequestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFeedbackRequests() throws Exception {
        int databaseSizeBeforeUpdate = feedbackRequestsRepository.findAll().size();
        feedbackRequests.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedbackRequestsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(feedbackRequests))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FeedbackRequests in the database
        List<FeedbackRequests> feedbackRequestsList = feedbackRequestsRepository.findAll();
        assertThat(feedbackRequestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFeedbackRequests() throws Exception {
        // Initialize the database
        feedbackRequestsRepository.saveAndFlush(feedbackRequests);

        int databaseSizeBeforeDelete = feedbackRequestsRepository.findAll().size();

        // Delete the feedbackRequests
        restFeedbackRequestsMockMvc
            .perform(delete(ENTITY_API_URL_ID, feedbackRequests.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FeedbackRequests> feedbackRequestsList = feedbackRequestsRepository.findAll();
        assertThat(feedbackRequestsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
