package com.venturedive.vendian_mono.web.rest;

import static com.venturedive.vendian_mono.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.LeaveDetailAdjustmentLogs;
import com.venturedive.vendian_mono.domain.LeaveDetails;
import com.venturedive.vendian_mono.domain.LeaveRequests;
import com.venturedive.vendian_mono.domain.LeaveTypes;
import com.venturedive.vendian_mono.domain.Leaves;
import com.venturedive.vendian_mono.repository.LeaveDetailsRepository;
import com.venturedive.vendian_mono.service.criteria.LeaveDetailsCriteria;
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
 * Integration tests for the {@link LeaveDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeaveDetailsResourceIT {

    private static final BigDecimal DEFAULT_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_USED = new BigDecimal(1);
    private static final BigDecimal UPDATED_USED = new BigDecimal(2);
    private static final BigDecimal SMALLER_USED = new BigDecimal(1 - 1);

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

    private static final String ENTITY_API_URL = "/api/leave-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LeaveDetailsRepository leaveDetailsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeaveDetailsMockMvc;

    private LeaveDetails leaveDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveDetails createEntity(EntityManager em) {
        LeaveDetails leaveDetails = new LeaveDetails()
            .total(DEFAULT_TOTAL)
            .used(DEFAULT_USED)
            .effDate(DEFAULT_EFF_DATE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .endDate(DEFAULT_END_DATE)
            .version(DEFAULT_VERSION);
        // Add required entity
        Leaves leaves;
        if (TestUtil.findAll(em, Leaves.class).isEmpty()) {
            leaves = LeavesResourceIT.createEntity(em);
            em.persist(leaves);
            em.flush();
        } else {
            leaves = TestUtil.findAll(em, Leaves.class).get(0);
        }
        leaveDetails.setLeave(leaves);
        // Add required entity
        LeaveTypes leaveTypes;
        if (TestUtil.findAll(em, LeaveTypes.class).isEmpty()) {
            leaveTypes = LeaveTypesResourceIT.createEntity(em);
            em.persist(leaveTypes);
            em.flush();
        } else {
            leaveTypes = TestUtil.findAll(em, LeaveTypes.class).get(0);
        }
        leaveDetails.setLeaveType(leaveTypes);
        return leaveDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveDetails createUpdatedEntity(EntityManager em) {
        LeaveDetails leaveDetails = new LeaveDetails()
            .total(UPDATED_TOTAL)
            .used(UPDATED_USED)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);
        // Add required entity
        Leaves leaves;
        if (TestUtil.findAll(em, Leaves.class).isEmpty()) {
            leaves = LeavesResourceIT.createUpdatedEntity(em);
            em.persist(leaves);
            em.flush();
        } else {
            leaves = TestUtil.findAll(em, Leaves.class).get(0);
        }
        leaveDetails.setLeave(leaves);
        // Add required entity
        LeaveTypes leaveTypes;
        if (TestUtil.findAll(em, LeaveTypes.class).isEmpty()) {
            leaveTypes = LeaveTypesResourceIT.createUpdatedEntity(em);
            em.persist(leaveTypes);
            em.flush();
        } else {
            leaveTypes = TestUtil.findAll(em, LeaveTypes.class).get(0);
        }
        leaveDetails.setLeaveType(leaveTypes);
        return leaveDetails;
    }

    @BeforeEach
    public void initTest() {
        leaveDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createLeaveDetails() throws Exception {
        int databaseSizeBeforeCreate = leaveDetailsRepository.findAll().size();
        // Create the LeaveDetails
        restLeaveDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveDetails))
            )
            .andExpect(status().isCreated());

        // Validate the LeaveDetails in the database
        List<LeaveDetails> leaveDetailsList = leaveDetailsRepository.findAll();
        assertThat(leaveDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        LeaveDetails testLeaveDetails = leaveDetailsList.get(leaveDetailsList.size() - 1);
        assertThat(testLeaveDetails.getTotal()).isEqualByComparingTo(DEFAULT_TOTAL);
        assertThat(testLeaveDetails.getUsed()).isEqualByComparingTo(DEFAULT_USED);
        assertThat(testLeaveDetails.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testLeaveDetails.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testLeaveDetails.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testLeaveDetails.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testLeaveDetails.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createLeaveDetailsWithExistingId() throws Exception {
        // Create the LeaveDetails with an existing ID
        leaveDetails.setId(1L);

        int databaseSizeBeforeCreate = leaveDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeaveDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveDetails in the database
        List<LeaveDetails> leaveDetailsList = leaveDetailsRepository.findAll();
        assertThat(leaveDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkUsedIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveDetailsRepository.findAll().size();
        // set the field null
        leaveDetails.setUsed(null);

        // Create the LeaveDetails, which fails.

        restLeaveDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveDetails))
            )
            .andExpect(status().isBadRequest());

        List<LeaveDetails> leaveDetailsList = leaveDetailsRepository.findAll();
        assertThat(leaveDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEffDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveDetailsRepository.findAll().size();
        // set the field null
        leaveDetails.setEffDate(null);

        // Create the LeaveDetails, which fails.

        restLeaveDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveDetails))
            )
            .andExpect(status().isBadRequest());

        List<LeaveDetails> leaveDetailsList = leaveDetailsRepository.findAll();
        assertThat(leaveDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveDetailsRepository.findAll().size();
        // set the field null
        leaveDetails.setCreatedAt(null);

        // Create the LeaveDetails, which fails.

        restLeaveDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveDetails))
            )
            .andExpect(status().isBadRequest());

        List<LeaveDetails> leaveDetailsList = leaveDetailsRepository.findAll();
        assertThat(leaveDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveDetailsRepository.findAll().size();
        // set the field null
        leaveDetails.setUpdatedAt(null);

        // Create the LeaveDetails, which fails.

        restLeaveDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveDetails))
            )
            .andExpect(status().isBadRequest());

        List<LeaveDetails> leaveDetailsList = leaveDetailsRepository.findAll();
        assertThat(leaveDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveDetailsRepository.findAll().size();
        // set the field null
        leaveDetails.setVersion(null);

        // Create the LeaveDetails, which fails.

        restLeaveDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveDetails))
            )
            .andExpect(status().isBadRequest());

        List<LeaveDetails> leaveDetailsList = leaveDetailsRepository.findAll();
        assertThat(leaveDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLeaveDetails() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        // Get all the leaveDetailsList
        restLeaveDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(sameNumber(DEFAULT_TOTAL))))
            .andExpect(jsonPath("$.[*].used").value(hasItem(sameNumber(DEFAULT_USED))))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getLeaveDetails() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        // Get the leaveDetails
        restLeaveDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, leaveDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leaveDetails.getId().intValue()))
            .andExpect(jsonPath("$.total").value(sameNumber(DEFAULT_TOTAL)))
            .andExpect(jsonPath("$.used").value(sameNumber(DEFAULT_USED)))
            .andExpect(jsonPath("$.effDate").value(DEFAULT_EFF_DATE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getLeaveDetailsByIdFiltering() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        Long id = leaveDetails.getId();

        defaultLeaveDetailsShouldBeFound("id.equals=" + id);
        defaultLeaveDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultLeaveDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLeaveDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultLeaveDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLeaveDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLeaveDetailsByTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        // Get all the leaveDetailsList where total equals to DEFAULT_TOTAL
        defaultLeaveDetailsShouldBeFound("total.equals=" + DEFAULT_TOTAL);

        // Get all the leaveDetailsList where total equals to UPDATED_TOTAL
        defaultLeaveDetailsShouldNotBeFound("total.equals=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    void getAllLeaveDetailsByTotalIsInShouldWork() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        // Get all the leaveDetailsList where total in DEFAULT_TOTAL or UPDATED_TOTAL
        defaultLeaveDetailsShouldBeFound("total.in=" + DEFAULT_TOTAL + "," + UPDATED_TOTAL);

        // Get all the leaveDetailsList where total equals to UPDATED_TOTAL
        defaultLeaveDetailsShouldNotBeFound("total.in=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    void getAllLeaveDetailsByTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        // Get all the leaveDetailsList where total is not null
        defaultLeaveDetailsShouldBeFound("total.specified=true");

        // Get all the leaveDetailsList where total is null
        defaultLeaveDetailsShouldNotBeFound("total.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveDetailsByTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        // Get all the leaveDetailsList where total is greater than or equal to DEFAULT_TOTAL
        defaultLeaveDetailsShouldBeFound("total.greaterThanOrEqual=" + DEFAULT_TOTAL);

        // Get all the leaveDetailsList where total is greater than or equal to UPDATED_TOTAL
        defaultLeaveDetailsShouldNotBeFound("total.greaterThanOrEqual=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    void getAllLeaveDetailsByTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        // Get all the leaveDetailsList where total is less than or equal to DEFAULT_TOTAL
        defaultLeaveDetailsShouldBeFound("total.lessThanOrEqual=" + DEFAULT_TOTAL);

        // Get all the leaveDetailsList where total is less than or equal to SMALLER_TOTAL
        defaultLeaveDetailsShouldNotBeFound("total.lessThanOrEqual=" + SMALLER_TOTAL);
    }

    @Test
    @Transactional
    void getAllLeaveDetailsByTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        // Get all the leaveDetailsList where total is less than DEFAULT_TOTAL
        defaultLeaveDetailsShouldNotBeFound("total.lessThan=" + DEFAULT_TOTAL);

        // Get all the leaveDetailsList where total is less than UPDATED_TOTAL
        defaultLeaveDetailsShouldBeFound("total.lessThan=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    void getAllLeaveDetailsByTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        // Get all the leaveDetailsList where total is greater than DEFAULT_TOTAL
        defaultLeaveDetailsShouldNotBeFound("total.greaterThan=" + DEFAULT_TOTAL);

        // Get all the leaveDetailsList where total is greater than SMALLER_TOTAL
        defaultLeaveDetailsShouldBeFound("total.greaterThan=" + SMALLER_TOTAL);
    }

    @Test
    @Transactional
    void getAllLeaveDetailsByUsedIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        // Get all the leaveDetailsList where used equals to DEFAULT_USED
        defaultLeaveDetailsShouldBeFound("used.equals=" + DEFAULT_USED);

        // Get all the leaveDetailsList where used equals to UPDATED_USED
        defaultLeaveDetailsShouldNotBeFound("used.equals=" + UPDATED_USED);
    }

    @Test
    @Transactional
    void getAllLeaveDetailsByUsedIsInShouldWork() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        // Get all the leaveDetailsList where used in DEFAULT_USED or UPDATED_USED
        defaultLeaveDetailsShouldBeFound("used.in=" + DEFAULT_USED + "," + UPDATED_USED);

        // Get all the leaveDetailsList where used equals to UPDATED_USED
        defaultLeaveDetailsShouldNotBeFound("used.in=" + UPDATED_USED);
    }

    @Test
    @Transactional
    void getAllLeaveDetailsByUsedIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        // Get all the leaveDetailsList where used is not null
        defaultLeaveDetailsShouldBeFound("used.specified=true");

        // Get all the leaveDetailsList where used is null
        defaultLeaveDetailsShouldNotBeFound("used.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveDetailsByUsedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        // Get all the leaveDetailsList where used is greater than or equal to DEFAULT_USED
        defaultLeaveDetailsShouldBeFound("used.greaterThanOrEqual=" + DEFAULT_USED);

        // Get all the leaveDetailsList where used is greater than or equal to UPDATED_USED
        defaultLeaveDetailsShouldNotBeFound("used.greaterThanOrEqual=" + UPDATED_USED);
    }

    @Test
    @Transactional
    void getAllLeaveDetailsByUsedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        // Get all the leaveDetailsList where used is less than or equal to DEFAULT_USED
        defaultLeaveDetailsShouldBeFound("used.lessThanOrEqual=" + DEFAULT_USED);

        // Get all the leaveDetailsList where used is less than or equal to SMALLER_USED
        defaultLeaveDetailsShouldNotBeFound("used.lessThanOrEqual=" + SMALLER_USED);
    }

    @Test
    @Transactional
    void getAllLeaveDetailsByUsedIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        // Get all the leaveDetailsList where used is less than DEFAULT_USED
        defaultLeaveDetailsShouldNotBeFound("used.lessThan=" + DEFAULT_USED);

        // Get all the leaveDetailsList where used is less than UPDATED_USED
        defaultLeaveDetailsShouldBeFound("used.lessThan=" + UPDATED_USED);
    }

    @Test
    @Transactional
    void getAllLeaveDetailsByUsedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        // Get all the leaveDetailsList where used is greater than DEFAULT_USED
        defaultLeaveDetailsShouldNotBeFound("used.greaterThan=" + DEFAULT_USED);

        // Get all the leaveDetailsList where used is greater than SMALLER_USED
        defaultLeaveDetailsShouldBeFound("used.greaterThan=" + SMALLER_USED);
    }

    @Test
    @Transactional
    void getAllLeaveDetailsByEffDateIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        // Get all the leaveDetailsList where effDate equals to DEFAULT_EFF_DATE
        defaultLeaveDetailsShouldBeFound("effDate.equals=" + DEFAULT_EFF_DATE);

        // Get all the leaveDetailsList where effDate equals to UPDATED_EFF_DATE
        defaultLeaveDetailsShouldNotBeFound("effDate.equals=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveDetailsByEffDateIsInShouldWork() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        // Get all the leaveDetailsList where effDate in DEFAULT_EFF_DATE or UPDATED_EFF_DATE
        defaultLeaveDetailsShouldBeFound("effDate.in=" + DEFAULT_EFF_DATE + "," + UPDATED_EFF_DATE);

        // Get all the leaveDetailsList where effDate equals to UPDATED_EFF_DATE
        defaultLeaveDetailsShouldNotBeFound("effDate.in=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveDetailsByEffDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        // Get all the leaveDetailsList where effDate is not null
        defaultLeaveDetailsShouldBeFound("effDate.specified=true");

        // Get all the leaveDetailsList where effDate is null
        defaultLeaveDetailsShouldNotBeFound("effDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveDetailsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        // Get all the leaveDetailsList where createdAt equals to DEFAULT_CREATED_AT
        defaultLeaveDetailsShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the leaveDetailsList where createdAt equals to UPDATED_CREATED_AT
        defaultLeaveDetailsShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveDetailsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        // Get all the leaveDetailsList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultLeaveDetailsShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the leaveDetailsList where createdAt equals to UPDATED_CREATED_AT
        defaultLeaveDetailsShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveDetailsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        // Get all the leaveDetailsList where createdAt is not null
        defaultLeaveDetailsShouldBeFound("createdAt.specified=true");

        // Get all the leaveDetailsList where createdAt is null
        defaultLeaveDetailsShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveDetailsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        // Get all the leaveDetailsList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultLeaveDetailsShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the leaveDetailsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultLeaveDetailsShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveDetailsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        // Get all the leaveDetailsList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultLeaveDetailsShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the leaveDetailsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultLeaveDetailsShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveDetailsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        // Get all the leaveDetailsList where updatedAt is not null
        defaultLeaveDetailsShouldBeFound("updatedAt.specified=true");

        // Get all the leaveDetailsList where updatedAt is null
        defaultLeaveDetailsShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveDetailsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        // Get all the leaveDetailsList where endDate equals to DEFAULT_END_DATE
        defaultLeaveDetailsShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the leaveDetailsList where endDate equals to UPDATED_END_DATE
        defaultLeaveDetailsShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveDetailsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        // Get all the leaveDetailsList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultLeaveDetailsShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the leaveDetailsList where endDate equals to UPDATED_END_DATE
        defaultLeaveDetailsShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveDetailsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        // Get all the leaveDetailsList where endDate is not null
        defaultLeaveDetailsShouldBeFound("endDate.specified=true");

        // Get all the leaveDetailsList where endDate is null
        defaultLeaveDetailsShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveDetailsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        // Get all the leaveDetailsList where version equals to DEFAULT_VERSION
        defaultLeaveDetailsShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the leaveDetailsList where version equals to UPDATED_VERSION
        defaultLeaveDetailsShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveDetailsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        // Get all the leaveDetailsList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultLeaveDetailsShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the leaveDetailsList where version equals to UPDATED_VERSION
        defaultLeaveDetailsShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveDetailsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        // Get all the leaveDetailsList where version is not null
        defaultLeaveDetailsShouldBeFound("version.specified=true");

        // Get all the leaveDetailsList where version is null
        defaultLeaveDetailsShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveDetailsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        // Get all the leaveDetailsList where version is greater than or equal to DEFAULT_VERSION
        defaultLeaveDetailsShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the leaveDetailsList where version is greater than or equal to UPDATED_VERSION
        defaultLeaveDetailsShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveDetailsByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        // Get all the leaveDetailsList where version is less than or equal to DEFAULT_VERSION
        defaultLeaveDetailsShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the leaveDetailsList where version is less than or equal to SMALLER_VERSION
        defaultLeaveDetailsShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveDetailsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        // Get all the leaveDetailsList where version is less than DEFAULT_VERSION
        defaultLeaveDetailsShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the leaveDetailsList where version is less than UPDATED_VERSION
        defaultLeaveDetailsShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveDetailsByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        // Get all the leaveDetailsList where version is greater than DEFAULT_VERSION
        defaultLeaveDetailsShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the leaveDetailsList where version is greater than SMALLER_VERSION
        defaultLeaveDetailsShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveDetailsByLeaveIsEqualToSomething() throws Exception {
        Leaves leave;
        if (TestUtil.findAll(em, Leaves.class).isEmpty()) {
            leaveDetailsRepository.saveAndFlush(leaveDetails);
            leave = LeavesResourceIT.createEntity(em);
        } else {
            leave = TestUtil.findAll(em, Leaves.class).get(0);
        }
        em.persist(leave);
        em.flush();
        leaveDetails.setLeave(leave);
        leaveDetailsRepository.saveAndFlush(leaveDetails);
        Long leaveId = leave.getId();

        // Get all the leaveDetailsList where leave equals to leaveId
        defaultLeaveDetailsShouldBeFound("leaveId.equals=" + leaveId);

        // Get all the leaveDetailsList where leave equals to (leaveId + 1)
        defaultLeaveDetailsShouldNotBeFound("leaveId.equals=" + (leaveId + 1));
    }

    @Test
    @Transactional
    void getAllLeaveDetailsByLeaveTypeIsEqualToSomething() throws Exception {
        LeaveTypes leaveType;
        if (TestUtil.findAll(em, LeaveTypes.class).isEmpty()) {
            leaveDetailsRepository.saveAndFlush(leaveDetails);
            leaveType = LeaveTypesResourceIT.createEntity(em);
        } else {
            leaveType = TestUtil.findAll(em, LeaveTypes.class).get(0);
        }
        em.persist(leaveType);
        em.flush();
        leaveDetails.setLeaveType(leaveType);
        leaveDetailsRepository.saveAndFlush(leaveDetails);
        Long leaveTypeId = leaveType.getId();

        // Get all the leaveDetailsList where leaveType equals to leaveTypeId
        defaultLeaveDetailsShouldBeFound("leaveTypeId.equals=" + leaveTypeId);

        // Get all the leaveDetailsList where leaveType equals to (leaveTypeId + 1)
        defaultLeaveDetailsShouldNotBeFound("leaveTypeId.equals=" + (leaveTypeId + 1));
    }

    @Test
    @Transactional
    void getAllLeaveDetailsByLeavedetailadjustmentlogsLeavedetailIsEqualToSomething() throws Exception {
        LeaveDetailAdjustmentLogs leavedetailadjustmentlogsLeavedetail;
        if (TestUtil.findAll(em, LeaveDetailAdjustmentLogs.class).isEmpty()) {
            leaveDetailsRepository.saveAndFlush(leaveDetails);
            leavedetailadjustmentlogsLeavedetail = LeaveDetailAdjustmentLogsResourceIT.createEntity(em);
        } else {
            leavedetailadjustmentlogsLeavedetail = TestUtil.findAll(em, LeaveDetailAdjustmentLogs.class).get(0);
        }
        em.persist(leavedetailadjustmentlogsLeavedetail);
        em.flush();
        leaveDetails.addLeavedetailadjustmentlogsLeavedetail(leavedetailadjustmentlogsLeavedetail);
        leaveDetailsRepository.saveAndFlush(leaveDetails);
        Long leavedetailadjustmentlogsLeavedetailId = leavedetailadjustmentlogsLeavedetail.getId();

        // Get all the leaveDetailsList where leavedetailadjustmentlogsLeavedetail equals to leavedetailadjustmentlogsLeavedetailId
        defaultLeaveDetailsShouldBeFound("leavedetailadjustmentlogsLeavedetailId.equals=" + leavedetailadjustmentlogsLeavedetailId);

        // Get all the leaveDetailsList where leavedetailadjustmentlogsLeavedetail equals to (leavedetailadjustmentlogsLeavedetailId + 1)
        defaultLeaveDetailsShouldNotBeFound(
            "leavedetailadjustmentlogsLeavedetailId.equals=" + (leavedetailadjustmentlogsLeavedetailId + 1)
        );
    }

    @Test
    @Transactional
    void getAllLeaveDetailsByLeaverequestsLeavedetailIsEqualToSomething() throws Exception {
        LeaveRequests leaverequestsLeavedetail;
        if (TestUtil.findAll(em, LeaveRequests.class).isEmpty()) {
            leaveDetailsRepository.saveAndFlush(leaveDetails);
            leaverequestsLeavedetail = LeaveRequestsResourceIT.createEntity(em);
        } else {
            leaverequestsLeavedetail = TestUtil.findAll(em, LeaveRequests.class).get(0);
        }
        em.persist(leaverequestsLeavedetail);
        em.flush();
        leaveDetails.addLeaverequestsLeavedetail(leaverequestsLeavedetail);
        leaveDetailsRepository.saveAndFlush(leaveDetails);
        Long leaverequestsLeavedetailId = leaverequestsLeavedetail.getId();

        // Get all the leaveDetailsList where leaverequestsLeavedetail equals to leaverequestsLeavedetailId
        defaultLeaveDetailsShouldBeFound("leaverequestsLeavedetailId.equals=" + leaverequestsLeavedetailId);

        // Get all the leaveDetailsList where leaverequestsLeavedetail equals to (leaverequestsLeavedetailId + 1)
        defaultLeaveDetailsShouldNotBeFound("leaverequestsLeavedetailId.equals=" + (leaverequestsLeavedetailId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLeaveDetailsShouldBeFound(String filter) throws Exception {
        restLeaveDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(sameNumber(DEFAULT_TOTAL))))
            .andExpect(jsonPath("$.[*].used").value(hasItem(sameNumber(DEFAULT_USED))))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restLeaveDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLeaveDetailsShouldNotBeFound(String filter) throws Exception {
        restLeaveDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLeaveDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLeaveDetails() throws Exception {
        // Get the leaveDetails
        restLeaveDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLeaveDetails() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        int databaseSizeBeforeUpdate = leaveDetailsRepository.findAll().size();

        // Update the leaveDetails
        LeaveDetails updatedLeaveDetails = leaveDetailsRepository.findById(leaveDetails.getId()).get();
        // Disconnect from session so that the updates on updatedLeaveDetails are not directly saved in db
        em.detach(updatedLeaveDetails);
        updatedLeaveDetails
            .total(UPDATED_TOTAL)
            .used(UPDATED_USED)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restLeaveDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLeaveDetails.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLeaveDetails))
            )
            .andExpect(status().isOk());

        // Validate the LeaveDetails in the database
        List<LeaveDetails> leaveDetailsList = leaveDetailsRepository.findAll();
        assertThat(leaveDetailsList).hasSize(databaseSizeBeforeUpdate);
        LeaveDetails testLeaveDetails = leaveDetailsList.get(leaveDetailsList.size() - 1);
        assertThat(testLeaveDetails.getTotal()).isEqualByComparingTo(UPDATED_TOTAL);
        assertThat(testLeaveDetails.getUsed()).isEqualByComparingTo(UPDATED_USED);
        assertThat(testLeaveDetails.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testLeaveDetails.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveDetails.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaveDetails.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testLeaveDetails.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingLeaveDetails() throws Exception {
        int databaseSizeBeforeUpdate = leaveDetailsRepository.findAll().size();
        leaveDetails.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leaveDetails.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveDetails in the database
        List<LeaveDetails> leaveDetailsList = leaveDetailsRepository.findAll();
        assertThat(leaveDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeaveDetails() throws Exception {
        int databaseSizeBeforeUpdate = leaveDetailsRepository.findAll().size();
        leaveDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveDetails in the database
        List<LeaveDetails> leaveDetailsList = leaveDetailsRepository.findAll();
        assertThat(leaveDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeaveDetails() throws Exception {
        int databaseSizeBeforeUpdate = leaveDetailsRepository.findAll().size();
        leaveDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveDetailsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveDetails))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveDetails in the database
        List<LeaveDetails> leaveDetailsList = leaveDetailsRepository.findAll();
        assertThat(leaveDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeaveDetailsWithPatch() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        int databaseSizeBeforeUpdate = leaveDetailsRepository.findAll().size();

        // Update the leaveDetails using partial update
        LeaveDetails partialUpdatedLeaveDetails = new LeaveDetails();
        partialUpdatedLeaveDetails.setId(leaveDetails.getId());

        partialUpdatedLeaveDetails.total(UPDATED_TOTAL).createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT);

        restLeaveDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveDetails.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveDetails))
            )
            .andExpect(status().isOk());

        // Validate the LeaveDetails in the database
        List<LeaveDetails> leaveDetailsList = leaveDetailsRepository.findAll();
        assertThat(leaveDetailsList).hasSize(databaseSizeBeforeUpdate);
        LeaveDetails testLeaveDetails = leaveDetailsList.get(leaveDetailsList.size() - 1);
        assertThat(testLeaveDetails.getTotal()).isEqualByComparingTo(UPDATED_TOTAL);
        assertThat(testLeaveDetails.getUsed()).isEqualByComparingTo(DEFAULT_USED);
        assertThat(testLeaveDetails.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testLeaveDetails.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveDetails.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaveDetails.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testLeaveDetails.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateLeaveDetailsWithPatch() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        int databaseSizeBeforeUpdate = leaveDetailsRepository.findAll().size();

        // Update the leaveDetails using partial update
        LeaveDetails partialUpdatedLeaveDetails = new LeaveDetails();
        partialUpdatedLeaveDetails.setId(leaveDetails.getId());

        partialUpdatedLeaveDetails
            .total(UPDATED_TOTAL)
            .used(UPDATED_USED)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restLeaveDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveDetails.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveDetails))
            )
            .andExpect(status().isOk());

        // Validate the LeaveDetails in the database
        List<LeaveDetails> leaveDetailsList = leaveDetailsRepository.findAll();
        assertThat(leaveDetailsList).hasSize(databaseSizeBeforeUpdate);
        LeaveDetails testLeaveDetails = leaveDetailsList.get(leaveDetailsList.size() - 1);
        assertThat(testLeaveDetails.getTotal()).isEqualByComparingTo(UPDATED_TOTAL);
        assertThat(testLeaveDetails.getUsed()).isEqualByComparingTo(UPDATED_USED);
        assertThat(testLeaveDetails.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testLeaveDetails.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveDetails.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaveDetails.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testLeaveDetails.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingLeaveDetails() throws Exception {
        int databaseSizeBeforeUpdate = leaveDetailsRepository.findAll().size();
        leaveDetails.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leaveDetails.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveDetails in the database
        List<LeaveDetails> leaveDetailsList = leaveDetailsRepository.findAll();
        assertThat(leaveDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeaveDetails() throws Exception {
        int databaseSizeBeforeUpdate = leaveDetailsRepository.findAll().size();
        leaveDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveDetails in the database
        List<LeaveDetails> leaveDetailsList = leaveDetailsRepository.findAll();
        assertThat(leaveDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeaveDetails() throws Exception {
        int databaseSizeBeforeUpdate = leaveDetailsRepository.findAll().size();
        leaveDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveDetails))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveDetails in the database
        List<LeaveDetails> leaveDetailsList = leaveDetailsRepository.findAll();
        assertThat(leaveDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLeaveDetails() throws Exception {
        // Initialize the database
        leaveDetailsRepository.saveAndFlush(leaveDetails);

        int databaseSizeBeforeDelete = leaveDetailsRepository.findAll().size();

        // Delete the leaveDetails
        restLeaveDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, leaveDetails.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeaveDetails> leaveDetailsList = leaveDetailsRepository.findAll();
        assertThat(leaveDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
