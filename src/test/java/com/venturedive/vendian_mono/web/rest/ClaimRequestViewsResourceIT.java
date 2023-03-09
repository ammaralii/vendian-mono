package com.venturedive.vendian_mono.web.rest;

import static com.venturedive.vendian_mono.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.ClaimRequestViews;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.repository.ClaimRequestViewsRepository;
import com.venturedive.vendian_mono.service.criteria.ClaimRequestViewsCriteria;
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
 * Integration tests for the {@link ClaimRequestViewsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClaimRequestViewsResourceIT {

    private static final String DEFAULT_COSTCENTER = "AAAAAAAAAA";
    private static final String UPDATED_COSTCENTER = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_AMOUNTRELEASED = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNTRELEASED = new BigDecimal(2);
    private static final BigDecimal SMALLER_AMOUNTRELEASED = new BigDecimal(1 - 1);

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTMENT = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTMENT = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_MANAGER = "AAAAAAAAAA";
    private static final String UPDATED_MANAGER = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/claim-request-views";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClaimRequestViewsRepository claimRequestViewsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClaimRequestViewsMockMvc;

    private ClaimRequestViews claimRequestViews;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClaimRequestViews createEntity(EntityManager em) {
        ClaimRequestViews claimRequestViews = new ClaimRequestViews()
            .costcenter(DEFAULT_COSTCENTER)
            .comments(DEFAULT_COMMENTS)
            .amountreleased(DEFAULT_AMOUNTRELEASED)
            .designation(DEFAULT_DESIGNATION)
            .department(DEFAULT_DEPARTMENT)
            .location(DEFAULT_LOCATION)
            .manager(DEFAULT_MANAGER)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        return claimRequestViews;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClaimRequestViews createUpdatedEntity(EntityManager em) {
        ClaimRequestViews claimRequestViews = new ClaimRequestViews()
            .costcenter(UPDATED_COSTCENTER)
            .comments(UPDATED_COMMENTS)
            .amountreleased(UPDATED_AMOUNTRELEASED)
            .designation(UPDATED_DESIGNATION)
            .department(UPDATED_DEPARTMENT)
            .location(UPDATED_LOCATION)
            .manager(UPDATED_MANAGER)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        return claimRequestViews;
    }

    @BeforeEach
    public void initTest() {
        claimRequestViews = createEntity(em);
    }

    @Test
    @Transactional
    void createClaimRequestViews() throws Exception {
        int databaseSizeBeforeCreate = claimRequestViewsRepository.findAll().size();
        // Create the ClaimRequestViews
        restClaimRequestViewsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimRequestViews))
            )
            .andExpect(status().isCreated());

        // Validate the ClaimRequestViews in the database
        List<ClaimRequestViews> claimRequestViewsList = claimRequestViewsRepository.findAll();
        assertThat(claimRequestViewsList).hasSize(databaseSizeBeforeCreate + 1);
        ClaimRequestViews testClaimRequestViews = claimRequestViewsList.get(claimRequestViewsList.size() - 1);
        assertThat(testClaimRequestViews.getCostcenter()).isEqualTo(DEFAULT_COSTCENTER);
        assertThat(testClaimRequestViews.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testClaimRequestViews.getAmountreleased()).isEqualByComparingTo(DEFAULT_AMOUNTRELEASED);
        assertThat(testClaimRequestViews.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testClaimRequestViews.getDepartment()).isEqualTo(DEFAULT_DEPARTMENT);
        assertThat(testClaimRequestViews.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testClaimRequestViews.getManager()).isEqualTo(DEFAULT_MANAGER);
        assertThat(testClaimRequestViews.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testClaimRequestViews.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createClaimRequestViewsWithExistingId() throws Exception {
        // Create the ClaimRequestViews with an existing ID
        claimRequestViews.setId(1L);

        int databaseSizeBeforeCreate = claimRequestViewsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClaimRequestViewsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimRequestViews))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimRequestViews in the database
        List<ClaimRequestViews> claimRequestViewsList = claimRequestViewsRepository.findAll();
        assertThat(claimRequestViewsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = claimRequestViewsRepository.findAll().size();
        // set the field null
        claimRequestViews.setUpdatedat(null);

        // Create the ClaimRequestViews, which fails.

        restClaimRequestViewsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimRequestViews))
            )
            .andExpect(status().isBadRequest());

        List<ClaimRequestViews> claimRequestViewsList = claimRequestViewsRepository.findAll();
        assertThat(claimRequestViewsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllClaimRequestViews() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList
        restClaimRequestViewsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(claimRequestViews.getId().intValue())))
            .andExpect(jsonPath("$.[*].costcenter").value(hasItem(DEFAULT_COSTCENTER)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].amountreleased").value(hasItem(sameNumber(DEFAULT_AMOUNTRELEASED))))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].manager").value(hasItem(DEFAULT_MANAGER)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getClaimRequestViews() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get the claimRequestViews
        restClaimRequestViewsMockMvc
            .perform(get(ENTITY_API_URL_ID, claimRequestViews.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(claimRequestViews.getId().intValue()))
            .andExpect(jsonPath("$.costcenter").value(DEFAULT_COSTCENTER))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.amountreleased").value(sameNumber(DEFAULT_AMOUNTRELEASED)))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.department").value(DEFAULT_DEPARTMENT))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.manager").value(DEFAULT_MANAGER))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getClaimRequestViewsByIdFiltering() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        Long id = claimRequestViews.getId();

        defaultClaimRequestViewsShouldBeFound("id.equals=" + id);
        defaultClaimRequestViewsShouldNotBeFound("id.notEquals=" + id);

        defaultClaimRequestViewsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClaimRequestViewsShouldNotBeFound("id.greaterThan=" + id);

        defaultClaimRequestViewsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClaimRequestViewsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByCostcenterIsEqualToSomething() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where costcenter equals to DEFAULT_COSTCENTER
        defaultClaimRequestViewsShouldBeFound("costcenter.equals=" + DEFAULT_COSTCENTER);

        // Get all the claimRequestViewsList where costcenter equals to UPDATED_COSTCENTER
        defaultClaimRequestViewsShouldNotBeFound("costcenter.equals=" + UPDATED_COSTCENTER);
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByCostcenterIsInShouldWork() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where costcenter in DEFAULT_COSTCENTER or UPDATED_COSTCENTER
        defaultClaimRequestViewsShouldBeFound("costcenter.in=" + DEFAULT_COSTCENTER + "," + UPDATED_COSTCENTER);

        // Get all the claimRequestViewsList where costcenter equals to UPDATED_COSTCENTER
        defaultClaimRequestViewsShouldNotBeFound("costcenter.in=" + UPDATED_COSTCENTER);
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByCostcenterIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where costcenter is not null
        defaultClaimRequestViewsShouldBeFound("costcenter.specified=true");

        // Get all the claimRequestViewsList where costcenter is null
        defaultClaimRequestViewsShouldNotBeFound("costcenter.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByCostcenterContainsSomething() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where costcenter contains DEFAULT_COSTCENTER
        defaultClaimRequestViewsShouldBeFound("costcenter.contains=" + DEFAULT_COSTCENTER);

        // Get all the claimRequestViewsList where costcenter contains UPDATED_COSTCENTER
        defaultClaimRequestViewsShouldNotBeFound("costcenter.contains=" + UPDATED_COSTCENTER);
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByCostcenterNotContainsSomething() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where costcenter does not contain DEFAULT_COSTCENTER
        defaultClaimRequestViewsShouldNotBeFound("costcenter.doesNotContain=" + DEFAULT_COSTCENTER);

        // Get all the claimRequestViewsList where costcenter does not contain UPDATED_COSTCENTER
        defaultClaimRequestViewsShouldBeFound("costcenter.doesNotContain=" + UPDATED_COSTCENTER);
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where comments equals to DEFAULT_COMMENTS
        defaultClaimRequestViewsShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the claimRequestViewsList where comments equals to UPDATED_COMMENTS
        defaultClaimRequestViewsShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultClaimRequestViewsShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the claimRequestViewsList where comments equals to UPDATED_COMMENTS
        defaultClaimRequestViewsShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where comments is not null
        defaultClaimRequestViewsShouldBeFound("comments.specified=true");

        // Get all the claimRequestViewsList where comments is null
        defaultClaimRequestViewsShouldNotBeFound("comments.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByCommentsContainsSomething() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where comments contains DEFAULT_COMMENTS
        defaultClaimRequestViewsShouldBeFound("comments.contains=" + DEFAULT_COMMENTS);

        // Get all the claimRequestViewsList where comments contains UPDATED_COMMENTS
        defaultClaimRequestViewsShouldNotBeFound("comments.contains=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where comments does not contain DEFAULT_COMMENTS
        defaultClaimRequestViewsShouldNotBeFound("comments.doesNotContain=" + DEFAULT_COMMENTS);

        // Get all the claimRequestViewsList where comments does not contain UPDATED_COMMENTS
        defaultClaimRequestViewsShouldBeFound("comments.doesNotContain=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByAmountreleasedIsEqualToSomething() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where amountreleased equals to DEFAULT_AMOUNTRELEASED
        defaultClaimRequestViewsShouldBeFound("amountreleased.equals=" + DEFAULT_AMOUNTRELEASED);

        // Get all the claimRequestViewsList where amountreleased equals to UPDATED_AMOUNTRELEASED
        defaultClaimRequestViewsShouldNotBeFound("amountreleased.equals=" + UPDATED_AMOUNTRELEASED);
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByAmountreleasedIsInShouldWork() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where amountreleased in DEFAULT_AMOUNTRELEASED or UPDATED_AMOUNTRELEASED
        defaultClaimRequestViewsShouldBeFound("amountreleased.in=" + DEFAULT_AMOUNTRELEASED + "," + UPDATED_AMOUNTRELEASED);

        // Get all the claimRequestViewsList where amountreleased equals to UPDATED_AMOUNTRELEASED
        defaultClaimRequestViewsShouldNotBeFound("amountreleased.in=" + UPDATED_AMOUNTRELEASED);
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByAmountreleasedIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where amountreleased is not null
        defaultClaimRequestViewsShouldBeFound("amountreleased.specified=true");

        // Get all the claimRequestViewsList where amountreleased is null
        defaultClaimRequestViewsShouldNotBeFound("amountreleased.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByAmountreleasedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where amountreleased is greater than or equal to DEFAULT_AMOUNTRELEASED
        defaultClaimRequestViewsShouldBeFound("amountreleased.greaterThanOrEqual=" + DEFAULT_AMOUNTRELEASED);

        // Get all the claimRequestViewsList where amountreleased is greater than or equal to UPDATED_AMOUNTRELEASED
        defaultClaimRequestViewsShouldNotBeFound("amountreleased.greaterThanOrEqual=" + UPDATED_AMOUNTRELEASED);
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByAmountreleasedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where amountreleased is less than or equal to DEFAULT_AMOUNTRELEASED
        defaultClaimRequestViewsShouldBeFound("amountreleased.lessThanOrEqual=" + DEFAULT_AMOUNTRELEASED);

        // Get all the claimRequestViewsList where amountreleased is less than or equal to SMALLER_AMOUNTRELEASED
        defaultClaimRequestViewsShouldNotBeFound("amountreleased.lessThanOrEqual=" + SMALLER_AMOUNTRELEASED);
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByAmountreleasedIsLessThanSomething() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where amountreleased is less than DEFAULT_AMOUNTRELEASED
        defaultClaimRequestViewsShouldNotBeFound("amountreleased.lessThan=" + DEFAULT_AMOUNTRELEASED);

        // Get all the claimRequestViewsList where amountreleased is less than UPDATED_AMOUNTRELEASED
        defaultClaimRequestViewsShouldBeFound("amountreleased.lessThan=" + UPDATED_AMOUNTRELEASED);
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByAmountreleasedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where amountreleased is greater than DEFAULT_AMOUNTRELEASED
        defaultClaimRequestViewsShouldNotBeFound("amountreleased.greaterThan=" + DEFAULT_AMOUNTRELEASED);

        // Get all the claimRequestViewsList where amountreleased is greater than SMALLER_AMOUNTRELEASED
        defaultClaimRequestViewsShouldBeFound("amountreleased.greaterThan=" + SMALLER_AMOUNTRELEASED);
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where designation equals to DEFAULT_DESIGNATION
        defaultClaimRequestViewsShouldBeFound("designation.equals=" + DEFAULT_DESIGNATION);

        // Get all the claimRequestViewsList where designation equals to UPDATED_DESIGNATION
        defaultClaimRequestViewsShouldNotBeFound("designation.equals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByDesignationIsInShouldWork() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where designation in DEFAULT_DESIGNATION or UPDATED_DESIGNATION
        defaultClaimRequestViewsShouldBeFound("designation.in=" + DEFAULT_DESIGNATION + "," + UPDATED_DESIGNATION);

        // Get all the claimRequestViewsList where designation equals to UPDATED_DESIGNATION
        defaultClaimRequestViewsShouldNotBeFound("designation.in=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByDesignationIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where designation is not null
        defaultClaimRequestViewsShouldBeFound("designation.specified=true");

        // Get all the claimRequestViewsList where designation is null
        defaultClaimRequestViewsShouldNotBeFound("designation.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByDesignationContainsSomething() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where designation contains DEFAULT_DESIGNATION
        defaultClaimRequestViewsShouldBeFound("designation.contains=" + DEFAULT_DESIGNATION);

        // Get all the claimRequestViewsList where designation contains UPDATED_DESIGNATION
        defaultClaimRequestViewsShouldNotBeFound("designation.contains=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByDesignationNotContainsSomething() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where designation does not contain DEFAULT_DESIGNATION
        defaultClaimRequestViewsShouldNotBeFound("designation.doesNotContain=" + DEFAULT_DESIGNATION);

        // Get all the claimRequestViewsList where designation does not contain UPDATED_DESIGNATION
        defaultClaimRequestViewsShouldBeFound("designation.doesNotContain=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByDepartmentIsEqualToSomething() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where department equals to DEFAULT_DEPARTMENT
        defaultClaimRequestViewsShouldBeFound("department.equals=" + DEFAULT_DEPARTMENT);

        // Get all the claimRequestViewsList where department equals to UPDATED_DEPARTMENT
        defaultClaimRequestViewsShouldNotBeFound("department.equals=" + UPDATED_DEPARTMENT);
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByDepartmentIsInShouldWork() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where department in DEFAULT_DEPARTMENT or UPDATED_DEPARTMENT
        defaultClaimRequestViewsShouldBeFound("department.in=" + DEFAULT_DEPARTMENT + "," + UPDATED_DEPARTMENT);

        // Get all the claimRequestViewsList where department equals to UPDATED_DEPARTMENT
        defaultClaimRequestViewsShouldNotBeFound("department.in=" + UPDATED_DEPARTMENT);
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByDepartmentIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where department is not null
        defaultClaimRequestViewsShouldBeFound("department.specified=true");

        // Get all the claimRequestViewsList where department is null
        defaultClaimRequestViewsShouldNotBeFound("department.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByDepartmentContainsSomething() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where department contains DEFAULT_DEPARTMENT
        defaultClaimRequestViewsShouldBeFound("department.contains=" + DEFAULT_DEPARTMENT);

        // Get all the claimRequestViewsList where department contains UPDATED_DEPARTMENT
        defaultClaimRequestViewsShouldNotBeFound("department.contains=" + UPDATED_DEPARTMENT);
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByDepartmentNotContainsSomething() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where department does not contain DEFAULT_DEPARTMENT
        defaultClaimRequestViewsShouldNotBeFound("department.doesNotContain=" + DEFAULT_DEPARTMENT);

        // Get all the claimRequestViewsList where department does not contain UPDATED_DEPARTMENT
        defaultClaimRequestViewsShouldBeFound("department.doesNotContain=" + UPDATED_DEPARTMENT);
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where location equals to DEFAULT_LOCATION
        defaultClaimRequestViewsShouldBeFound("location.equals=" + DEFAULT_LOCATION);

        // Get all the claimRequestViewsList where location equals to UPDATED_LOCATION
        defaultClaimRequestViewsShouldNotBeFound("location.equals=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByLocationIsInShouldWork() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where location in DEFAULT_LOCATION or UPDATED_LOCATION
        defaultClaimRequestViewsShouldBeFound("location.in=" + DEFAULT_LOCATION + "," + UPDATED_LOCATION);

        // Get all the claimRequestViewsList where location equals to UPDATED_LOCATION
        defaultClaimRequestViewsShouldNotBeFound("location.in=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where location is not null
        defaultClaimRequestViewsShouldBeFound("location.specified=true");

        // Get all the claimRequestViewsList where location is null
        defaultClaimRequestViewsShouldNotBeFound("location.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByLocationContainsSomething() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where location contains DEFAULT_LOCATION
        defaultClaimRequestViewsShouldBeFound("location.contains=" + DEFAULT_LOCATION);

        // Get all the claimRequestViewsList where location contains UPDATED_LOCATION
        defaultClaimRequestViewsShouldNotBeFound("location.contains=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByLocationNotContainsSomething() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where location does not contain DEFAULT_LOCATION
        defaultClaimRequestViewsShouldNotBeFound("location.doesNotContain=" + DEFAULT_LOCATION);

        // Get all the claimRequestViewsList where location does not contain UPDATED_LOCATION
        defaultClaimRequestViewsShouldBeFound("location.doesNotContain=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByManagerIsEqualToSomething() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where manager equals to DEFAULT_MANAGER
        defaultClaimRequestViewsShouldBeFound("manager.equals=" + DEFAULT_MANAGER);

        // Get all the claimRequestViewsList where manager equals to UPDATED_MANAGER
        defaultClaimRequestViewsShouldNotBeFound("manager.equals=" + UPDATED_MANAGER);
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByManagerIsInShouldWork() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where manager in DEFAULT_MANAGER or UPDATED_MANAGER
        defaultClaimRequestViewsShouldBeFound("manager.in=" + DEFAULT_MANAGER + "," + UPDATED_MANAGER);

        // Get all the claimRequestViewsList where manager equals to UPDATED_MANAGER
        defaultClaimRequestViewsShouldNotBeFound("manager.in=" + UPDATED_MANAGER);
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByManagerIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where manager is not null
        defaultClaimRequestViewsShouldBeFound("manager.specified=true");

        // Get all the claimRequestViewsList where manager is null
        defaultClaimRequestViewsShouldNotBeFound("manager.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByManagerContainsSomething() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where manager contains DEFAULT_MANAGER
        defaultClaimRequestViewsShouldBeFound("manager.contains=" + DEFAULT_MANAGER);

        // Get all the claimRequestViewsList where manager contains UPDATED_MANAGER
        defaultClaimRequestViewsShouldNotBeFound("manager.contains=" + UPDATED_MANAGER);
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByManagerNotContainsSomething() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where manager does not contain DEFAULT_MANAGER
        defaultClaimRequestViewsShouldNotBeFound("manager.doesNotContain=" + DEFAULT_MANAGER);

        // Get all the claimRequestViewsList where manager does not contain UPDATED_MANAGER
        defaultClaimRequestViewsShouldBeFound("manager.doesNotContain=" + UPDATED_MANAGER);
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where createdat equals to DEFAULT_CREATEDAT
        defaultClaimRequestViewsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the claimRequestViewsList where createdat equals to UPDATED_CREATEDAT
        defaultClaimRequestViewsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultClaimRequestViewsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the claimRequestViewsList where createdat equals to UPDATED_CREATEDAT
        defaultClaimRequestViewsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where createdat is not null
        defaultClaimRequestViewsShouldBeFound("createdat.specified=true");

        // Get all the claimRequestViewsList where createdat is null
        defaultClaimRequestViewsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultClaimRequestViewsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the claimRequestViewsList where updatedat equals to UPDATED_UPDATEDAT
        defaultClaimRequestViewsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultClaimRequestViewsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the claimRequestViewsList where updatedat equals to UPDATED_UPDATEDAT
        defaultClaimRequestViewsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        // Get all the claimRequestViewsList where updatedat is not null
        defaultClaimRequestViewsShouldBeFound("updatedat.specified=true");

        // Get all the claimRequestViewsList where updatedat is null
        defaultClaimRequestViewsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimRequestViewsByEmployeeIsEqualToSomething() throws Exception {
        Employees employee;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            claimRequestViewsRepository.saveAndFlush(claimRequestViews);
            employee = EmployeesResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employee);
        em.flush();
        claimRequestViews.setEmployee(employee);
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);
        Long employeeId = employee.getId();

        // Get all the claimRequestViewsList where employee equals to employeeId
        defaultClaimRequestViewsShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the claimRequestViewsList where employee equals to (employeeId + 1)
        defaultClaimRequestViewsShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClaimRequestViewsShouldBeFound(String filter) throws Exception {
        restClaimRequestViewsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(claimRequestViews.getId().intValue())))
            .andExpect(jsonPath("$.[*].costcenter").value(hasItem(DEFAULT_COSTCENTER)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].amountreleased").value(hasItem(sameNumber(DEFAULT_AMOUNTRELEASED))))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].manager").value(hasItem(DEFAULT_MANAGER)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restClaimRequestViewsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClaimRequestViewsShouldNotBeFound(String filter) throws Exception {
        restClaimRequestViewsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClaimRequestViewsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingClaimRequestViews() throws Exception {
        // Get the claimRequestViews
        restClaimRequestViewsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingClaimRequestViews() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        int databaseSizeBeforeUpdate = claimRequestViewsRepository.findAll().size();

        // Update the claimRequestViews
        ClaimRequestViews updatedClaimRequestViews = claimRequestViewsRepository.findById(claimRequestViews.getId()).get();
        // Disconnect from session so that the updates on updatedClaimRequestViews are not directly saved in db
        em.detach(updatedClaimRequestViews);
        updatedClaimRequestViews
            .costcenter(UPDATED_COSTCENTER)
            .comments(UPDATED_COMMENTS)
            .amountreleased(UPDATED_AMOUNTRELEASED)
            .designation(UPDATED_DESIGNATION)
            .department(UPDATED_DEPARTMENT)
            .location(UPDATED_LOCATION)
            .manager(UPDATED_MANAGER)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restClaimRequestViewsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClaimRequestViews.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedClaimRequestViews))
            )
            .andExpect(status().isOk());

        // Validate the ClaimRequestViews in the database
        List<ClaimRequestViews> claimRequestViewsList = claimRequestViewsRepository.findAll();
        assertThat(claimRequestViewsList).hasSize(databaseSizeBeforeUpdate);
        ClaimRequestViews testClaimRequestViews = claimRequestViewsList.get(claimRequestViewsList.size() - 1);
        assertThat(testClaimRequestViews.getCostcenter()).isEqualTo(UPDATED_COSTCENTER);
        assertThat(testClaimRequestViews.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testClaimRequestViews.getAmountreleased()).isEqualByComparingTo(UPDATED_AMOUNTRELEASED);
        assertThat(testClaimRequestViews.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testClaimRequestViews.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testClaimRequestViews.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testClaimRequestViews.getManager()).isEqualTo(UPDATED_MANAGER);
        assertThat(testClaimRequestViews.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testClaimRequestViews.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingClaimRequestViews() throws Exception {
        int databaseSizeBeforeUpdate = claimRequestViewsRepository.findAll().size();
        claimRequestViews.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClaimRequestViewsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, claimRequestViews.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimRequestViews))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimRequestViews in the database
        List<ClaimRequestViews> claimRequestViewsList = claimRequestViewsRepository.findAll();
        assertThat(claimRequestViewsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClaimRequestViews() throws Exception {
        int databaseSizeBeforeUpdate = claimRequestViewsRepository.findAll().size();
        claimRequestViews.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimRequestViewsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimRequestViews))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimRequestViews in the database
        List<ClaimRequestViews> claimRequestViewsList = claimRequestViewsRepository.findAll();
        assertThat(claimRequestViewsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClaimRequestViews() throws Exception {
        int databaseSizeBeforeUpdate = claimRequestViewsRepository.findAll().size();
        claimRequestViews.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimRequestViewsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimRequestViews))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClaimRequestViews in the database
        List<ClaimRequestViews> claimRequestViewsList = claimRequestViewsRepository.findAll();
        assertThat(claimRequestViewsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClaimRequestViewsWithPatch() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        int databaseSizeBeforeUpdate = claimRequestViewsRepository.findAll().size();

        // Update the claimRequestViews using partial update
        ClaimRequestViews partialUpdatedClaimRequestViews = new ClaimRequestViews();
        partialUpdatedClaimRequestViews.setId(claimRequestViews.getId());

        partialUpdatedClaimRequestViews
            .costcenter(UPDATED_COSTCENTER)
            .comments(UPDATED_COMMENTS)
            .department(UPDATED_DEPARTMENT)
            .location(UPDATED_LOCATION)
            .manager(UPDATED_MANAGER)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restClaimRequestViewsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClaimRequestViews.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClaimRequestViews))
            )
            .andExpect(status().isOk());

        // Validate the ClaimRequestViews in the database
        List<ClaimRequestViews> claimRequestViewsList = claimRequestViewsRepository.findAll();
        assertThat(claimRequestViewsList).hasSize(databaseSizeBeforeUpdate);
        ClaimRequestViews testClaimRequestViews = claimRequestViewsList.get(claimRequestViewsList.size() - 1);
        assertThat(testClaimRequestViews.getCostcenter()).isEqualTo(UPDATED_COSTCENTER);
        assertThat(testClaimRequestViews.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testClaimRequestViews.getAmountreleased()).isEqualByComparingTo(DEFAULT_AMOUNTRELEASED);
        assertThat(testClaimRequestViews.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testClaimRequestViews.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testClaimRequestViews.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testClaimRequestViews.getManager()).isEqualTo(UPDATED_MANAGER);
        assertThat(testClaimRequestViews.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testClaimRequestViews.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateClaimRequestViewsWithPatch() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        int databaseSizeBeforeUpdate = claimRequestViewsRepository.findAll().size();

        // Update the claimRequestViews using partial update
        ClaimRequestViews partialUpdatedClaimRequestViews = new ClaimRequestViews();
        partialUpdatedClaimRequestViews.setId(claimRequestViews.getId());

        partialUpdatedClaimRequestViews
            .costcenter(UPDATED_COSTCENTER)
            .comments(UPDATED_COMMENTS)
            .amountreleased(UPDATED_AMOUNTRELEASED)
            .designation(UPDATED_DESIGNATION)
            .department(UPDATED_DEPARTMENT)
            .location(UPDATED_LOCATION)
            .manager(UPDATED_MANAGER)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restClaimRequestViewsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClaimRequestViews.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClaimRequestViews))
            )
            .andExpect(status().isOk());

        // Validate the ClaimRequestViews in the database
        List<ClaimRequestViews> claimRequestViewsList = claimRequestViewsRepository.findAll();
        assertThat(claimRequestViewsList).hasSize(databaseSizeBeforeUpdate);
        ClaimRequestViews testClaimRequestViews = claimRequestViewsList.get(claimRequestViewsList.size() - 1);
        assertThat(testClaimRequestViews.getCostcenter()).isEqualTo(UPDATED_COSTCENTER);
        assertThat(testClaimRequestViews.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testClaimRequestViews.getAmountreleased()).isEqualByComparingTo(UPDATED_AMOUNTRELEASED);
        assertThat(testClaimRequestViews.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testClaimRequestViews.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testClaimRequestViews.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testClaimRequestViews.getManager()).isEqualTo(UPDATED_MANAGER);
        assertThat(testClaimRequestViews.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testClaimRequestViews.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingClaimRequestViews() throws Exception {
        int databaseSizeBeforeUpdate = claimRequestViewsRepository.findAll().size();
        claimRequestViews.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClaimRequestViewsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, claimRequestViews.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(claimRequestViews))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimRequestViews in the database
        List<ClaimRequestViews> claimRequestViewsList = claimRequestViewsRepository.findAll();
        assertThat(claimRequestViewsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClaimRequestViews() throws Exception {
        int databaseSizeBeforeUpdate = claimRequestViewsRepository.findAll().size();
        claimRequestViews.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimRequestViewsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(claimRequestViews))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimRequestViews in the database
        List<ClaimRequestViews> claimRequestViewsList = claimRequestViewsRepository.findAll();
        assertThat(claimRequestViewsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClaimRequestViews() throws Exception {
        int databaseSizeBeforeUpdate = claimRequestViewsRepository.findAll().size();
        claimRequestViews.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimRequestViewsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(claimRequestViews))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClaimRequestViews in the database
        List<ClaimRequestViews> claimRequestViewsList = claimRequestViewsRepository.findAll();
        assertThat(claimRequestViewsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClaimRequestViews() throws Exception {
        // Initialize the database
        claimRequestViewsRepository.saveAndFlush(claimRequestViews);

        int databaseSizeBeforeDelete = claimRequestViewsRepository.findAll().size();

        // Delete the claimRequestViews
        restClaimRequestViewsMockMvc
            .perform(delete(ENTITY_API_URL_ID, claimRequestViews.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClaimRequestViews> claimRequestViewsList = claimRequestViewsRepository.findAll();
        assertThat(claimRequestViewsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
