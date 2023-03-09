package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.ClaimApprovers;
import com.venturedive.vendian_mono.domain.ClaimDetails;
import com.venturedive.vendian_mono.domain.ClaimImages;
import com.venturedive.vendian_mono.domain.ClaimRequests;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.repository.ClaimRequestsRepository;
import com.venturedive.vendian_mono.service.criteria.ClaimRequestsCriteria;
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
 * Integration tests for the {@link ClaimRequestsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClaimRequestsResourceIT {

    private static final Integer DEFAULT_PROJECTID = 1;
    private static final Integer UPDATED_PROJECTID = 2;
    private static final Integer SMALLER_PROJECTID = 1 - 1;

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final Integer DEFAULT_AMOUNTRELEASED = 1;
    private static final Integer UPDATED_AMOUNTRELEASED = 2;
    private static final Integer SMALLER_AMOUNTRELEASED = 1 - 1;

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

    private static final String ENTITY_API_URL = "/api/claim-requests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClaimRequestsRepository claimRequestsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClaimRequestsMockMvc;

    private ClaimRequests claimRequests;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClaimRequests createEntity(EntityManager em) {
        ClaimRequests claimRequests = new ClaimRequests()
            .projectid(DEFAULT_PROJECTID)
            .comments(DEFAULT_COMMENTS)
            .amountreleased(DEFAULT_AMOUNTRELEASED)
            .designation(DEFAULT_DESIGNATION)
            .department(DEFAULT_DEPARTMENT)
            .location(DEFAULT_LOCATION)
            .manager(DEFAULT_MANAGER)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        return claimRequests;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClaimRequests createUpdatedEntity(EntityManager em) {
        ClaimRequests claimRequests = new ClaimRequests()
            .projectid(UPDATED_PROJECTID)
            .comments(UPDATED_COMMENTS)
            .amountreleased(UPDATED_AMOUNTRELEASED)
            .designation(UPDATED_DESIGNATION)
            .department(UPDATED_DEPARTMENT)
            .location(UPDATED_LOCATION)
            .manager(UPDATED_MANAGER)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        return claimRequests;
    }

    @BeforeEach
    public void initTest() {
        claimRequests = createEntity(em);
    }

    @Test
    @Transactional
    void createClaimRequests() throws Exception {
        int databaseSizeBeforeCreate = claimRequestsRepository.findAll().size();
        // Create the ClaimRequests
        restClaimRequestsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimRequests))
            )
            .andExpect(status().isCreated());

        // Validate the ClaimRequests in the database
        List<ClaimRequests> claimRequestsList = claimRequestsRepository.findAll();
        assertThat(claimRequestsList).hasSize(databaseSizeBeforeCreate + 1);
        ClaimRequests testClaimRequests = claimRequestsList.get(claimRequestsList.size() - 1);
        assertThat(testClaimRequests.getProjectid()).isEqualTo(DEFAULT_PROJECTID);
        assertThat(testClaimRequests.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testClaimRequests.getAmountreleased()).isEqualTo(DEFAULT_AMOUNTRELEASED);
        assertThat(testClaimRequests.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testClaimRequests.getDepartment()).isEqualTo(DEFAULT_DEPARTMENT);
        assertThat(testClaimRequests.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testClaimRequests.getManager()).isEqualTo(DEFAULT_MANAGER);
        assertThat(testClaimRequests.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testClaimRequests.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createClaimRequestsWithExistingId() throws Exception {
        // Create the ClaimRequests with an existing ID
        claimRequests.setId(1L);

        int databaseSizeBeforeCreate = claimRequestsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClaimRequestsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimRequests))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimRequests in the database
        List<ClaimRequests> claimRequestsList = claimRequestsRepository.findAll();
        assertThat(claimRequestsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClaimRequests() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList
        restClaimRequestsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(claimRequests.getId().intValue())))
            .andExpect(jsonPath("$.[*].projectid").value(hasItem(DEFAULT_PROJECTID)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].amountreleased").value(hasItem(DEFAULT_AMOUNTRELEASED)))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].manager").value(hasItem(DEFAULT_MANAGER)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getClaimRequests() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get the claimRequests
        restClaimRequestsMockMvc
            .perform(get(ENTITY_API_URL_ID, claimRequests.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(claimRequests.getId().intValue()))
            .andExpect(jsonPath("$.projectid").value(DEFAULT_PROJECTID))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.amountreleased").value(DEFAULT_AMOUNTRELEASED))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.department").value(DEFAULT_DEPARTMENT))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.manager").value(DEFAULT_MANAGER))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getClaimRequestsByIdFiltering() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        Long id = claimRequests.getId();

        defaultClaimRequestsShouldBeFound("id.equals=" + id);
        defaultClaimRequestsShouldNotBeFound("id.notEquals=" + id);

        defaultClaimRequestsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClaimRequestsShouldNotBeFound("id.greaterThan=" + id);

        defaultClaimRequestsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClaimRequestsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllClaimRequestsByProjectidIsEqualToSomething() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where projectid equals to DEFAULT_PROJECTID
        defaultClaimRequestsShouldBeFound("projectid.equals=" + DEFAULT_PROJECTID);

        // Get all the claimRequestsList where projectid equals to UPDATED_PROJECTID
        defaultClaimRequestsShouldNotBeFound("projectid.equals=" + UPDATED_PROJECTID);
    }

    @Test
    @Transactional
    void getAllClaimRequestsByProjectidIsInShouldWork() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where projectid in DEFAULT_PROJECTID or UPDATED_PROJECTID
        defaultClaimRequestsShouldBeFound("projectid.in=" + DEFAULT_PROJECTID + "," + UPDATED_PROJECTID);

        // Get all the claimRequestsList where projectid equals to UPDATED_PROJECTID
        defaultClaimRequestsShouldNotBeFound("projectid.in=" + UPDATED_PROJECTID);
    }

    @Test
    @Transactional
    void getAllClaimRequestsByProjectidIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where projectid is not null
        defaultClaimRequestsShouldBeFound("projectid.specified=true");

        // Get all the claimRequestsList where projectid is null
        defaultClaimRequestsShouldNotBeFound("projectid.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimRequestsByProjectidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where projectid is greater than or equal to DEFAULT_PROJECTID
        defaultClaimRequestsShouldBeFound("projectid.greaterThanOrEqual=" + DEFAULT_PROJECTID);

        // Get all the claimRequestsList where projectid is greater than or equal to UPDATED_PROJECTID
        defaultClaimRequestsShouldNotBeFound("projectid.greaterThanOrEqual=" + UPDATED_PROJECTID);
    }

    @Test
    @Transactional
    void getAllClaimRequestsByProjectidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where projectid is less than or equal to DEFAULT_PROJECTID
        defaultClaimRequestsShouldBeFound("projectid.lessThanOrEqual=" + DEFAULT_PROJECTID);

        // Get all the claimRequestsList where projectid is less than or equal to SMALLER_PROJECTID
        defaultClaimRequestsShouldNotBeFound("projectid.lessThanOrEqual=" + SMALLER_PROJECTID);
    }

    @Test
    @Transactional
    void getAllClaimRequestsByProjectidIsLessThanSomething() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where projectid is less than DEFAULT_PROJECTID
        defaultClaimRequestsShouldNotBeFound("projectid.lessThan=" + DEFAULT_PROJECTID);

        // Get all the claimRequestsList where projectid is less than UPDATED_PROJECTID
        defaultClaimRequestsShouldBeFound("projectid.lessThan=" + UPDATED_PROJECTID);
    }

    @Test
    @Transactional
    void getAllClaimRequestsByProjectidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where projectid is greater than DEFAULT_PROJECTID
        defaultClaimRequestsShouldNotBeFound("projectid.greaterThan=" + DEFAULT_PROJECTID);

        // Get all the claimRequestsList where projectid is greater than SMALLER_PROJECTID
        defaultClaimRequestsShouldBeFound("projectid.greaterThan=" + SMALLER_PROJECTID);
    }

    @Test
    @Transactional
    void getAllClaimRequestsByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where comments equals to DEFAULT_COMMENTS
        defaultClaimRequestsShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the claimRequestsList where comments equals to UPDATED_COMMENTS
        defaultClaimRequestsShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllClaimRequestsByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultClaimRequestsShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the claimRequestsList where comments equals to UPDATED_COMMENTS
        defaultClaimRequestsShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllClaimRequestsByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where comments is not null
        defaultClaimRequestsShouldBeFound("comments.specified=true");

        // Get all the claimRequestsList where comments is null
        defaultClaimRequestsShouldNotBeFound("comments.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimRequestsByCommentsContainsSomething() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where comments contains DEFAULT_COMMENTS
        defaultClaimRequestsShouldBeFound("comments.contains=" + DEFAULT_COMMENTS);

        // Get all the claimRequestsList where comments contains UPDATED_COMMENTS
        defaultClaimRequestsShouldNotBeFound("comments.contains=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllClaimRequestsByCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where comments does not contain DEFAULT_COMMENTS
        defaultClaimRequestsShouldNotBeFound("comments.doesNotContain=" + DEFAULT_COMMENTS);

        // Get all the claimRequestsList where comments does not contain UPDATED_COMMENTS
        defaultClaimRequestsShouldBeFound("comments.doesNotContain=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllClaimRequestsByAmountreleasedIsEqualToSomething() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where amountreleased equals to DEFAULT_AMOUNTRELEASED
        defaultClaimRequestsShouldBeFound("amountreleased.equals=" + DEFAULT_AMOUNTRELEASED);

        // Get all the claimRequestsList where amountreleased equals to UPDATED_AMOUNTRELEASED
        defaultClaimRequestsShouldNotBeFound("amountreleased.equals=" + UPDATED_AMOUNTRELEASED);
    }

    @Test
    @Transactional
    void getAllClaimRequestsByAmountreleasedIsInShouldWork() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where amountreleased in DEFAULT_AMOUNTRELEASED or UPDATED_AMOUNTRELEASED
        defaultClaimRequestsShouldBeFound("amountreleased.in=" + DEFAULT_AMOUNTRELEASED + "," + UPDATED_AMOUNTRELEASED);

        // Get all the claimRequestsList where amountreleased equals to UPDATED_AMOUNTRELEASED
        defaultClaimRequestsShouldNotBeFound("amountreleased.in=" + UPDATED_AMOUNTRELEASED);
    }

    @Test
    @Transactional
    void getAllClaimRequestsByAmountreleasedIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where amountreleased is not null
        defaultClaimRequestsShouldBeFound("amountreleased.specified=true");

        // Get all the claimRequestsList where amountreleased is null
        defaultClaimRequestsShouldNotBeFound("amountreleased.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimRequestsByAmountreleasedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where amountreleased is greater than or equal to DEFAULT_AMOUNTRELEASED
        defaultClaimRequestsShouldBeFound("amountreleased.greaterThanOrEqual=" + DEFAULT_AMOUNTRELEASED);

        // Get all the claimRequestsList where amountreleased is greater than or equal to UPDATED_AMOUNTRELEASED
        defaultClaimRequestsShouldNotBeFound("amountreleased.greaterThanOrEqual=" + UPDATED_AMOUNTRELEASED);
    }

    @Test
    @Transactional
    void getAllClaimRequestsByAmountreleasedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where amountreleased is less than or equal to DEFAULT_AMOUNTRELEASED
        defaultClaimRequestsShouldBeFound("amountreleased.lessThanOrEqual=" + DEFAULT_AMOUNTRELEASED);

        // Get all the claimRequestsList where amountreleased is less than or equal to SMALLER_AMOUNTRELEASED
        defaultClaimRequestsShouldNotBeFound("amountreleased.lessThanOrEqual=" + SMALLER_AMOUNTRELEASED);
    }

    @Test
    @Transactional
    void getAllClaimRequestsByAmountreleasedIsLessThanSomething() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where amountreleased is less than DEFAULT_AMOUNTRELEASED
        defaultClaimRequestsShouldNotBeFound("amountreleased.lessThan=" + DEFAULT_AMOUNTRELEASED);

        // Get all the claimRequestsList where amountreleased is less than UPDATED_AMOUNTRELEASED
        defaultClaimRequestsShouldBeFound("amountreleased.lessThan=" + UPDATED_AMOUNTRELEASED);
    }

    @Test
    @Transactional
    void getAllClaimRequestsByAmountreleasedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where amountreleased is greater than DEFAULT_AMOUNTRELEASED
        defaultClaimRequestsShouldNotBeFound("amountreleased.greaterThan=" + DEFAULT_AMOUNTRELEASED);

        // Get all the claimRequestsList where amountreleased is greater than SMALLER_AMOUNTRELEASED
        defaultClaimRequestsShouldBeFound("amountreleased.greaterThan=" + SMALLER_AMOUNTRELEASED);
    }

    @Test
    @Transactional
    void getAllClaimRequestsByDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where designation equals to DEFAULT_DESIGNATION
        defaultClaimRequestsShouldBeFound("designation.equals=" + DEFAULT_DESIGNATION);

        // Get all the claimRequestsList where designation equals to UPDATED_DESIGNATION
        defaultClaimRequestsShouldNotBeFound("designation.equals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllClaimRequestsByDesignationIsInShouldWork() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where designation in DEFAULT_DESIGNATION or UPDATED_DESIGNATION
        defaultClaimRequestsShouldBeFound("designation.in=" + DEFAULT_DESIGNATION + "," + UPDATED_DESIGNATION);

        // Get all the claimRequestsList where designation equals to UPDATED_DESIGNATION
        defaultClaimRequestsShouldNotBeFound("designation.in=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllClaimRequestsByDesignationIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where designation is not null
        defaultClaimRequestsShouldBeFound("designation.specified=true");

        // Get all the claimRequestsList where designation is null
        defaultClaimRequestsShouldNotBeFound("designation.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimRequestsByDesignationContainsSomething() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where designation contains DEFAULT_DESIGNATION
        defaultClaimRequestsShouldBeFound("designation.contains=" + DEFAULT_DESIGNATION);

        // Get all the claimRequestsList where designation contains UPDATED_DESIGNATION
        defaultClaimRequestsShouldNotBeFound("designation.contains=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllClaimRequestsByDesignationNotContainsSomething() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where designation does not contain DEFAULT_DESIGNATION
        defaultClaimRequestsShouldNotBeFound("designation.doesNotContain=" + DEFAULT_DESIGNATION);

        // Get all the claimRequestsList where designation does not contain UPDATED_DESIGNATION
        defaultClaimRequestsShouldBeFound("designation.doesNotContain=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllClaimRequestsByDepartmentIsEqualToSomething() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where department equals to DEFAULT_DEPARTMENT
        defaultClaimRequestsShouldBeFound("department.equals=" + DEFAULT_DEPARTMENT);

        // Get all the claimRequestsList where department equals to UPDATED_DEPARTMENT
        defaultClaimRequestsShouldNotBeFound("department.equals=" + UPDATED_DEPARTMENT);
    }

    @Test
    @Transactional
    void getAllClaimRequestsByDepartmentIsInShouldWork() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where department in DEFAULT_DEPARTMENT or UPDATED_DEPARTMENT
        defaultClaimRequestsShouldBeFound("department.in=" + DEFAULT_DEPARTMENT + "," + UPDATED_DEPARTMENT);

        // Get all the claimRequestsList where department equals to UPDATED_DEPARTMENT
        defaultClaimRequestsShouldNotBeFound("department.in=" + UPDATED_DEPARTMENT);
    }

    @Test
    @Transactional
    void getAllClaimRequestsByDepartmentIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where department is not null
        defaultClaimRequestsShouldBeFound("department.specified=true");

        // Get all the claimRequestsList where department is null
        defaultClaimRequestsShouldNotBeFound("department.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimRequestsByDepartmentContainsSomething() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where department contains DEFAULT_DEPARTMENT
        defaultClaimRequestsShouldBeFound("department.contains=" + DEFAULT_DEPARTMENT);

        // Get all the claimRequestsList where department contains UPDATED_DEPARTMENT
        defaultClaimRequestsShouldNotBeFound("department.contains=" + UPDATED_DEPARTMENT);
    }

    @Test
    @Transactional
    void getAllClaimRequestsByDepartmentNotContainsSomething() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where department does not contain DEFAULT_DEPARTMENT
        defaultClaimRequestsShouldNotBeFound("department.doesNotContain=" + DEFAULT_DEPARTMENT);

        // Get all the claimRequestsList where department does not contain UPDATED_DEPARTMENT
        defaultClaimRequestsShouldBeFound("department.doesNotContain=" + UPDATED_DEPARTMENT);
    }

    @Test
    @Transactional
    void getAllClaimRequestsByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where location equals to DEFAULT_LOCATION
        defaultClaimRequestsShouldBeFound("location.equals=" + DEFAULT_LOCATION);

        // Get all the claimRequestsList where location equals to UPDATED_LOCATION
        defaultClaimRequestsShouldNotBeFound("location.equals=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllClaimRequestsByLocationIsInShouldWork() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where location in DEFAULT_LOCATION or UPDATED_LOCATION
        defaultClaimRequestsShouldBeFound("location.in=" + DEFAULT_LOCATION + "," + UPDATED_LOCATION);

        // Get all the claimRequestsList where location equals to UPDATED_LOCATION
        defaultClaimRequestsShouldNotBeFound("location.in=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllClaimRequestsByLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where location is not null
        defaultClaimRequestsShouldBeFound("location.specified=true");

        // Get all the claimRequestsList where location is null
        defaultClaimRequestsShouldNotBeFound("location.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimRequestsByLocationContainsSomething() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where location contains DEFAULT_LOCATION
        defaultClaimRequestsShouldBeFound("location.contains=" + DEFAULT_LOCATION);

        // Get all the claimRequestsList where location contains UPDATED_LOCATION
        defaultClaimRequestsShouldNotBeFound("location.contains=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllClaimRequestsByLocationNotContainsSomething() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where location does not contain DEFAULT_LOCATION
        defaultClaimRequestsShouldNotBeFound("location.doesNotContain=" + DEFAULT_LOCATION);

        // Get all the claimRequestsList where location does not contain UPDATED_LOCATION
        defaultClaimRequestsShouldBeFound("location.doesNotContain=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllClaimRequestsByManagerIsEqualToSomething() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where manager equals to DEFAULT_MANAGER
        defaultClaimRequestsShouldBeFound("manager.equals=" + DEFAULT_MANAGER);

        // Get all the claimRequestsList where manager equals to UPDATED_MANAGER
        defaultClaimRequestsShouldNotBeFound("manager.equals=" + UPDATED_MANAGER);
    }

    @Test
    @Transactional
    void getAllClaimRequestsByManagerIsInShouldWork() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where manager in DEFAULT_MANAGER or UPDATED_MANAGER
        defaultClaimRequestsShouldBeFound("manager.in=" + DEFAULT_MANAGER + "," + UPDATED_MANAGER);

        // Get all the claimRequestsList where manager equals to UPDATED_MANAGER
        defaultClaimRequestsShouldNotBeFound("manager.in=" + UPDATED_MANAGER);
    }

    @Test
    @Transactional
    void getAllClaimRequestsByManagerIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where manager is not null
        defaultClaimRequestsShouldBeFound("manager.specified=true");

        // Get all the claimRequestsList where manager is null
        defaultClaimRequestsShouldNotBeFound("manager.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimRequestsByManagerContainsSomething() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where manager contains DEFAULT_MANAGER
        defaultClaimRequestsShouldBeFound("manager.contains=" + DEFAULT_MANAGER);

        // Get all the claimRequestsList where manager contains UPDATED_MANAGER
        defaultClaimRequestsShouldNotBeFound("manager.contains=" + UPDATED_MANAGER);
    }

    @Test
    @Transactional
    void getAllClaimRequestsByManagerNotContainsSomething() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where manager does not contain DEFAULT_MANAGER
        defaultClaimRequestsShouldNotBeFound("manager.doesNotContain=" + DEFAULT_MANAGER);

        // Get all the claimRequestsList where manager does not contain UPDATED_MANAGER
        defaultClaimRequestsShouldBeFound("manager.doesNotContain=" + UPDATED_MANAGER);
    }

    @Test
    @Transactional
    void getAllClaimRequestsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where createdat equals to DEFAULT_CREATEDAT
        defaultClaimRequestsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the claimRequestsList where createdat equals to UPDATED_CREATEDAT
        defaultClaimRequestsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllClaimRequestsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultClaimRequestsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the claimRequestsList where createdat equals to UPDATED_CREATEDAT
        defaultClaimRequestsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllClaimRequestsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where createdat is not null
        defaultClaimRequestsShouldBeFound("createdat.specified=true");

        // Get all the claimRequestsList where createdat is null
        defaultClaimRequestsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimRequestsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultClaimRequestsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the claimRequestsList where updatedat equals to UPDATED_UPDATEDAT
        defaultClaimRequestsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllClaimRequestsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultClaimRequestsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the claimRequestsList where updatedat equals to UPDATED_UPDATEDAT
        defaultClaimRequestsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllClaimRequestsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        // Get all the claimRequestsList where updatedat is not null
        defaultClaimRequestsShouldBeFound("updatedat.specified=true");

        // Get all the claimRequestsList where updatedat is null
        defaultClaimRequestsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimRequestsByEmployeeIsEqualToSomething() throws Exception {
        Employees employee;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            claimRequestsRepository.saveAndFlush(claimRequests);
            employee = EmployeesResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employee);
        em.flush();
        claimRequests.setEmployee(employee);
        claimRequestsRepository.saveAndFlush(claimRequests);
        Long employeeId = employee.getId();

        // Get all the claimRequestsList where employee equals to employeeId
        defaultClaimRequestsShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the claimRequestsList where employee equals to (employeeId + 1)
        defaultClaimRequestsShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    @Test
    @Transactional
    void getAllClaimRequestsByClaimapproversClaimrequestIsEqualToSomething() throws Exception {
        ClaimApprovers claimapproversClaimrequest;
        if (TestUtil.findAll(em, ClaimApprovers.class).isEmpty()) {
            claimRequestsRepository.saveAndFlush(claimRequests);
            claimapproversClaimrequest = ClaimApproversResourceIT.createEntity(em);
        } else {
            claimapproversClaimrequest = TestUtil.findAll(em, ClaimApprovers.class).get(0);
        }
        em.persist(claimapproversClaimrequest);
        em.flush();
        claimRequests.addClaimapproversClaimrequest(claimapproversClaimrequest);
        claimRequestsRepository.saveAndFlush(claimRequests);
        Long claimapproversClaimrequestId = claimapproversClaimrequest.getId();

        // Get all the claimRequestsList where claimapproversClaimrequest equals to claimapproversClaimrequestId
        defaultClaimRequestsShouldBeFound("claimapproversClaimrequestId.equals=" + claimapproversClaimrequestId);

        // Get all the claimRequestsList where claimapproversClaimrequest equals to (claimapproversClaimrequestId + 1)
        defaultClaimRequestsShouldNotBeFound("claimapproversClaimrequestId.equals=" + (claimapproversClaimrequestId + 1));
    }

    @Test
    @Transactional
    void getAllClaimRequestsByClaimdetailsClaimrequestIsEqualToSomething() throws Exception {
        ClaimDetails claimdetailsClaimrequest;
        if (TestUtil.findAll(em, ClaimDetails.class).isEmpty()) {
            claimRequestsRepository.saveAndFlush(claimRequests);
            claimdetailsClaimrequest = ClaimDetailsResourceIT.createEntity(em);
        } else {
            claimdetailsClaimrequest = TestUtil.findAll(em, ClaimDetails.class).get(0);
        }
        em.persist(claimdetailsClaimrequest);
        em.flush();
        claimRequests.addClaimdetailsClaimrequest(claimdetailsClaimrequest);
        claimRequestsRepository.saveAndFlush(claimRequests);
        Long claimdetailsClaimrequestId = claimdetailsClaimrequest.getId();

        // Get all the claimRequestsList where claimdetailsClaimrequest equals to claimdetailsClaimrequestId
        defaultClaimRequestsShouldBeFound("claimdetailsClaimrequestId.equals=" + claimdetailsClaimrequestId);

        // Get all the claimRequestsList where claimdetailsClaimrequest equals to (claimdetailsClaimrequestId + 1)
        defaultClaimRequestsShouldNotBeFound("claimdetailsClaimrequestId.equals=" + (claimdetailsClaimrequestId + 1));
    }

    @Test
    @Transactional
    void getAllClaimRequestsByClaimimagesClaimrequestIsEqualToSomething() throws Exception {
        ClaimImages claimimagesClaimrequest;
        if (TestUtil.findAll(em, ClaimImages.class).isEmpty()) {
            claimRequestsRepository.saveAndFlush(claimRequests);
            claimimagesClaimrequest = ClaimImagesResourceIT.createEntity(em);
        } else {
            claimimagesClaimrequest = TestUtil.findAll(em, ClaimImages.class).get(0);
        }
        em.persist(claimimagesClaimrequest);
        em.flush();
        claimRequests.addClaimimagesClaimrequest(claimimagesClaimrequest);
        claimRequestsRepository.saveAndFlush(claimRequests);
        Long claimimagesClaimrequestId = claimimagesClaimrequest.getId();

        // Get all the claimRequestsList where claimimagesClaimrequest equals to claimimagesClaimrequestId
        defaultClaimRequestsShouldBeFound("claimimagesClaimrequestId.equals=" + claimimagesClaimrequestId);

        // Get all the claimRequestsList where claimimagesClaimrequest equals to (claimimagesClaimrequestId + 1)
        defaultClaimRequestsShouldNotBeFound("claimimagesClaimrequestId.equals=" + (claimimagesClaimrequestId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClaimRequestsShouldBeFound(String filter) throws Exception {
        restClaimRequestsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(claimRequests.getId().intValue())))
            .andExpect(jsonPath("$.[*].projectid").value(hasItem(DEFAULT_PROJECTID)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].amountreleased").value(hasItem(DEFAULT_AMOUNTRELEASED)))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].manager").value(hasItem(DEFAULT_MANAGER)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restClaimRequestsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClaimRequestsShouldNotBeFound(String filter) throws Exception {
        restClaimRequestsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClaimRequestsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingClaimRequests() throws Exception {
        // Get the claimRequests
        restClaimRequestsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingClaimRequests() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        int databaseSizeBeforeUpdate = claimRequestsRepository.findAll().size();

        // Update the claimRequests
        ClaimRequests updatedClaimRequests = claimRequestsRepository.findById(claimRequests.getId()).get();
        // Disconnect from session so that the updates on updatedClaimRequests are not directly saved in db
        em.detach(updatedClaimRequests);
        updatedClaimRequests
            .projectid(UPDATED_PROJECTID)
            .comments(UPDATED_COMMENTS)
            .amountreleased(UPDATED_AMOUNTRELEASED)
            .designation(UPDATED_DESIGNATION)
            .department(UPDATED_DEPARTMENT)
            .location(UPDATED_LOCATION)
            .manager(UPDATED_MANAGER)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restClaimRequestsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClaimRequests.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedClaimRequests))
            )
            .andExpect(status().isOk());

        // Validate the ClaimRequests in the database
        List<ClaimRequests> claimRequestsList = claimRequestsRepository.findAll();
        assertThat(claimRequestsList).hasSize(databaseSizeBeforeUpdate);
        ClaimRequests testClaimRequests = claimRequestsList.get(claimRequestsList.size() - 1);
        assertThat(testClaimRequests.getProjectid()).isEqualTo(UPDATED_PROJECTID);
        assertThat(testClaimRequests.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testClaimRequests.getAmountreleased()).isEqualTo(UPDATED_AMOUNTRELEASED);
        assertThat(testClaimRequests.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testClaimRequests.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testClaimRequests.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testClaimRequests.getManager()).isEqualTo(UPDATED_MANAGER);
        assertThat(testClaimRequests.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testClaimRequests.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingClaimRequests() throws Exception {
        int databaseSizeBeforeUpdate = claimRequestsRepository.findAll().size();
        claimRequests.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClaimRequestsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, claimRequests.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimRequests))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimRequests in the database
        List<ClaimRequests> claimRequestsList = claimRequestsRepository.findAll();
        assertThat(claimRequestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClaimRequests() throws Exception {
        int databaseSizeBeforeUpdate = claimRequestsRepository.findAll().size();
        claimRequests.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimRequestsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimRequests))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimRequests in the database
        List<ClaimRequests> claimRequestsList = claimRequestsRepository.findAll();
        assertThat(claimRequestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClaimRequests() throws Exception {
        int databaseSizeBeforeUpdate = claimRequestsRepository.findAll().size();
        claimRequests.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimRequestsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimRequests))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClaimRequests in the database
        List<ClaimRequests> claimRequestsList = claimRequestsRepository.findAll();
        assertThat(claimRequestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClaimRequestsWithPatch() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        int databaseSizeBeforeUpdate = claimRequestsRepository.findAll().size();

        // Update the claimRequests using partial update
        ClaimRequests partialUpdatedClaimRequests = new ClaimRequests();
        partialUpdatedClaimRequests.setId(claimRequests.getId());

        partialUpdatedClaimRequests
            .comments(UPDATED_COMMENTS)
            .amountreleased(UPDATED_AMOUNTRELEASED)
            .department(UPDATED_DEPARTMENT)
            .location(UPDATED_LOCATION)
            .manager(UPDATED_MANAGER);

        restClaimRequestsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClaimRequests.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClaimRequests))
            )
            .andExpect(status().isOk());

        // Validate the ClaimRequests in the database
        List<ClaimRequests> claimRequestsList = claimRequestsRepository.findAll();
        assertThat(claimRequestsList).hasSize(databaseSizeBeforeUpdate);
        ClaimRequests testClaimRequests = claimRequestsList.get(claimRequestsList.size() - 1);
        assertThat(testClaimRequests.getProjectid()).isEqualTo(DEFAULT_PROJECTID);
        assertThat(testClaimRequests.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testClaimRequests.getAmountreleased()).isEqualTo(UPDATED_AMOUNTRELEASED);
        assertThat(testClaimRequests.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testClaimRequests.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testClaimRequests.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testClaimRequests.getManager()).isEqualTo(UPDATED_MANAGER);
        assertThat(testClaimRequests.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testClaimRequests.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateClaimRequestsWithPatch() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        int databaseSizeBeforeUpdate = claimRequestsRepository.findAll().size();

        // Update the claimRequests using partial update
        ClaimRequests partialUpdatedClaimRequests = new ClaimRequests();
        partialUpdatedClaimRequests.setId(claimRequests.getId());

        partialUpdatedClaimRequests
            .projectid(UPDATED_PROJECTID)
            .comments(UPDATED_COMMENTS)
            .amountreleased(UPDATED_AMOUNTRELEASED)
            .designation(UPDATED_DESIGNATION)
            .department(UPDATED_DEPARTMENT)
            .location(UPDATED_LOCATION)
            .manager(UPDATED_MANAGER)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restClaimRequestsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClaimRequests.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClaimRequests))
            )
            .andExpect(status().isOk());

        // Validate the ClaimRequests in the database
        List<ClaimRequests> claimRequestsList = claimRequestsRepository.findAll();
        assertThat(claimRequestsList).hasSize(databaseSizeBeforeUpdate);
        ClaimRequests testClaimRequests = claimRequestsList.get(claimRequestsList.size() - 1);
        assertThat(testClaimRequests.getProjectid()).isEqualTo(UPDATED_PROJECTID);
        assertThat(testClaimRequests.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testClaimRequests.getAmountreleased()).isEqualTo(UPDATED_AMOUNTRELEASED);
        assertThat(testClaimRequests.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testClaimRequests.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testClaimRequests.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testClaimRequests.getManager()).isEqualTo(UPDATED_MANAGER);
        assertThat(testClaimRequests.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testClaimRequests.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingClaimRequests() throws Exception {
        int databaseSizeBeforeUpdate = claimRequestsRepository.findAll().size();
        claimRequests.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClaimRequestsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, claimRequests.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(claimRequests))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimRequests in the database
        List<ClaimRequests> claimRequestsList = claimRequestsRepository.findAll();
        assertThat(claimRequestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClaimRequests() throws Exception {
        int databaseSizeBeforeUpdate = claimRequestsRepository.findAll().size();
        claimRequests.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimRequestsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(claimRequests))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimRequests in the database
        List<ClaimRequests> claimRequestsList = claimRequestsRepository.findAll();
        assertThat(claimRequestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClaimRequests() throws Exception {
        int databaseSizeBeforeUpdate = claimRequestsRepository.findAll().size();
        claimRequests.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimRequestsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(claimRequests))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClaimRequests in the database
        List<ClaimRequests> claimRequestsList = claimRequestsRepository.findAll();
        assertThat(claimRequestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClaimRequests() throws Exception {
        // Initialize the database
        claimRequestsRepository.saveAndFlush(claimRequests);

        int databaseSizeBeforeDelete = claimRequestsRepository.findAll().size();

        // Delete the claimRequests
        restClaimRequestsMockMvc
            .perform(delete(ENTITY_API_URL_ID, claimRequests.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClaimRequests> claimRequestsList = claimRequestsRepository.findAll();
        assertThat(claimRequestsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
