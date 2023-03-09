package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.LeaveDetails;
import com.venturedive.vendian_mono.domain.Leaves;
import com.venturedive.vendian_mono.repository.LeavesRepository;
import com.venturedive.vendian_mono.service.criteria.LeavesCriteria;
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
 * Integration tests for the {@link LeavesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeavesResourceIT {

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

    private static final String ENTITY_API_URL = "/api/leaves";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LeavesRepository leavesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeavesMockMvc;

    private Leaves leaves;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Leaves createEntity(EntityManager em) {
        Leaves leaves = new Leaves()
            .effDate(DEFAULT_EFF_DATE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .endDate(DEFAULT_END_DATE)
            .version(DEFAULT_VERSION);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        leaves.setUser(employees);
        return leaves;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Leaves createUpdatedEntity(EntityManager em) {
        Leaves leaves = new Leaves()
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createUpdatedEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        leaves.setUser(employees);
        return leaves;
    }

    @BeforeEach
    public void initTest() {
        leaves = createEntity(em);
    }

    @Test
    @Transactional
    void createLeaves() throws Exception {
        int databaseSizeBeforeCreate = leavesRepository.findAll().size();
        // Create the Leaves
        restLeavesMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leaves))
            )
            .andExpect(status().isCreated());

        // Validate the Leaves in the database
        List<Leaves> leavesList = leavesRepository.findAll();
        assertThat(leavesList).hasSize(databaseSizeBeforeCreate + 1);
        Leaves testLeaves = leavesList.get(leavesList.size() - 1);
        assertThat(testLeaves.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testLeaves.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testLeaves.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testLeaves.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testLeaves.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createLeavesWithExistingId() throws Exception {
        // Create the Leaves with an existing ID
        leaves.setId(1L);

        int databaseSizeBeforeCreate = leavesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeavesMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leaves))
            )
            .andExpect(status().isBadRequest());

        // Validate the Leaves in the database
        List<Leaves> leavesList = leavesRepository.findAll();
        assertThat(leavesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEffDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = leavesRepository.findAll().size();
        // set the field null
        leaves.setEffDate(null);

        // Create the Leaves, which fails.

        restLeavesMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leaves))
            )
            .andExpect(status().isBadRequest());

        List<Leaves> leavesList = leavesRepository.findAll();
        assertThat(leavesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = leavesRepository.findAll().size();
        // set the field null
        leaves.setCreatedAt(null);

        // Create the Leaves, which fails.

        restLeavesMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leaves))
            )
            .andExpect(status().isBadRequest());

        List<Leaves> leavesList = leavesRepository.findAll();
        assertThat(leavesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = leavesRepository.findAll().size();
        // set the field null
        leaves.setUpdatedAt(null);

        // Create the Leaves, which fails.

        restLeavesMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leaves))
            )
            .andExpect(status().isBadRequest());

        List<Leaves> leavesList = leavesRepository.findAll();
        assertThat(leavesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = leavesRepository.findAll().size();
        // set the field null
        leaves.setVersion(null);

        // Create the Leaves, which fails.

        restLeavesMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leaves))
            )
            .andExpect(status().isBadRequest());

        List<Leaves> leavesList = leavesRepository.findAll();
        assertThat(leavesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLeaves() throws Exception {
        // Initialize the database
        leavesRepository.saveAndFlush(leaves);

        // Get all the leavesList
        restLeavesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaves.getId().intValue())))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getLeaves() throws Exception {
        // Initialize the database
        leavesRepository.saveAndFlush(leaves);

        // Get the leaves
        restLeavesMockMvc
            .perform(get(ENTITY_API_URL_ID, leaves.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leaves.getId().intValue()))
            .andExpect(jsonPath("$.effDate").value(DEFAULT_EFF_DATE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getLeavesByIdFiltering() throws Exception {
        // Initialize the database
        leavesRepository.saveAndFlush(leaves);

        Long id = leaves.getId();

        defaultLeavesShouldBeFound("id.equals=" + id);
        defaultLeavesShouldNotBeFound("id.notEquals=" + id);

        defaultLeavesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLeavesShouldNotBeFound("id.greaterThan=" + id);

        defaultLeavesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLeavesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLeavesByEffDateIsEqualToSomething() throws Exception {
        // Initialize the database
        leavesRepository.saveAndFlush(leaves);

        // Get all the leavesList where effDate equals to DEFAULT_EFF_DATE
        defaultLeavesShouldBeFound("effDate.equals=" + DEFAULT_EFF_DATE);

        // Get all the leavesList where effDate equals to UPDATED_EFF_DATE
        defaultLeavesShouldNotBeFound("effDate.equals=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllLeavesByEffDateIsInShouldWork() throws Exception {
        // Initialize the database
        leavesRepository.saveAndFlush(leaves);

        // Get all the leavesList where effDate in DEFAULT_EFF_DATE or UPDATED_EFF_DATE
        defaultLeavesShouldBeFound("effDate.in=" + DEFAULT_EFF_DATE + "," + UPDATED_EFF_DATE);

        // Get all the leavesList where effDate equals to UPDATED_EFF_DATE
        defaultLeavesShouldNotBeFound("effDate.in=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllLeavesByEffDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavesRepository.saveAndFlush(leaves);

        // Get all the leavesList where effDate is not null
        defaultLeavesShouldBeFound("effDate.specified=true");

        // Get all the leavesList where effDate is null
        defaultLeavesShouldNotBeFound("effDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavesByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leavesRepository.saveAndFlush(leaves);

        // Get all the leavesList where createdAt equals to DEFAULT_CREATED_AT
        defaultLeavesShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the leavesList where createdAt equals to UPDATED_CREATED_AT
        defaultLeavesShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllLeavesByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leavesRepository.saveAndFlush(leaves);

        // Get all the leavesList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultLeavesShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the leavesList where createdAt equals to UPDATED_CREATED_AT
        defaultLeavesShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllLeavesByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavesRepository.saveAndFlush(leaves);

        // Get all the leavesList where createdAt is not null
        defaultLeavesShouldBeFound("createdAt.specified=true");

        // Get all the leavesList where createdAt is null
        defaultLeavesShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavesByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leavesRepository.saveAndFlush(leaves);

        // Get all the leavesList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultLeavesShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the leavesList where updatedAt equals to UPDATED_UPDATED_AT
        defaultLeavesShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllLeavesByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leavesRepository.saveAndFlush(leaves);

        // Get all the leavesList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultLeavesShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the leavesList where updatedAt equals to UPDATED_UPDATED_AT
        defaultLeavesShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllLeavesByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavesRepository.saveAndFlush(leaves);

        // Get all the leavesList where updatedAt is not null
        defaultLeavesShouldBeFound("updatedAt.specified=true");

        // Get all the leavesList where updatedAt is null
        defaultLeavesShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavesByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        leavesRepository.saveAndFlush(leaves);

        // Get all the leavesList where endDate equals to DEFAULT_END_DATE
        defaultLeavesShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the leavesList where endDate equals to UPDATED_END_DATE
        defaultLeavesShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllLeavesByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        leavesRepository.saveAndFlush(leaves);

        // Get all the leavesList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultLeavesShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the leavesList where endDate equals to UPDATED_END_DATE
        defaultLeavesShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllLeavesByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavesRepository.saveAndFlush(leaves);

        // Get all the leavesList where endDate is not null
        defaultLeavesShouldBeFound("endDate.specified=true");

        // Get all the leavesList where endDate is null
        defaultLeavesShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        leavesRepository.saveAndFlush(leaves);

        // Get all the leavesList where version equals to DEFAULT_VERSION
        defaultLeavesShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the leavesList where version equals to UPDATED_VERSION
        defaultLeavesShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeavesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        leavesRepository.saveAndFlush(leaves);

        // Get all the leavesList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultLeavesShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the leavesList where version equals to UPDATED_VERSION
        defaultLeavesShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeavesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavesRepository.saveAndFlush(leaves);

        // Get all the leavesList where version is not null
        defaultLeavesShouldBeFound("version.specified=true");

        // Get all the leavesList where version is null
        defaultLeavesShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesRepository.saveAndFlush(leaves);

        // Get all the leavesList where version is greater than or equal to DEFAULT_VERSION
        defaultLeavesShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the leavesList where version is greater than or equal to UPDATED_VERSION
        defaultLeavesShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeavesByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesRepository.saveAndFlush(leaves);

        // Get all the leavesList where version is less than or equal to DEFAULT_VERSION
        defaultLeavesShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the leavesList where version is less than or equal to SMALLER_VERSION
        defaultLeavesShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllLeavesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        leavesRepository.saveAndFlush(leaves);

        // Get all the leavesList where version is less than DEFAULT_VERSION
        defaultLeavesShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the leavesList where version is less than UPDATED_VERSION
        defaultLeavesShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeavesByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leavesRepository.saveAndFlush(leaves);

        // Get all the leavesList where version is greater than DEFAULT_VERSION
        defaultLeavesShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the leavesList where version is greater than SMALLER_VERSION
        defaultLeavesShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllLeavesByUserIsEqualToSomething() throws Exception {
        Employees user;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            leavesRepository.saveAndFlush(leaves);
            user = EmployeesResourceIT.createEntity(em);
        } else {
            user = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(user);
        em.flush();
        leaves.setUser(user);
        leavesRepository.saveAndFlush(leaves);
        Long userId = user.getId();

        // Get all the leavesList where user equals to userId
        defaultLeavesShouldBeFound("userId.equals=" + userId);

        // Get all the leavesList where user equals to (userId + 1)
        defaultLeavesShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    @Test
    @Transactional
    void getAllLeavesByLeavedetailsLeaveIsEqualToSomething() throws Exception {
        LeaveDetails leavedetailsLeave;
        if (TestUtil.findAll(em, LeaveDetails.class).isEmpty()) {
            leavesRepository.saveAndFlush(leaves);
            leavedetailsLeave = LeaveDetailsResourceIT.createEntity(em);
        } else {
            leavedetailsLeave = TestUtil.findAll(em, LeaveDetails.class).get(0);
        }
        em.persist(leavedetailsLeave);
        em.flush();
        leaves.addLeavedetailsLeave(leavedetailsLeave);
        leavesRepository.saveAndFlush(leaves);
        Long leavedetailsLeaveId = leavedetailsLeave.getId();

        // Get all the leavesList where leavedetailsLeave equals to leavedetailsLeaveId
        defaultLeavesShouldBeFound("leavedetailsLeaveId.equals=" + leavedetailsLeaveId);

        // Get all the leavesList where leavedetailsLeave equals to (leavedetailsLeaveId + 1)
        defaultLeavesShouldNotBeFound("leavedetailsLeaveId.equals=" + (leavedetailsLeaveId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLeavesShouldBeFound(String filter) throws Exception {
        restLeavesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaves.getId().intValue())))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restLeavesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLeavesShouldNotBeFound(String filter) throws Exception {
        restLeavesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLeavesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLeaves() throws Exception {
        // Get the leaves
        restLeavesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLeaves() throws Exception {
        // Initialize the database
        leavesRepository.saveAndFlush(leaves);

        int databaseSizeBeforeUpdate = leavesRepository.findAll().size();

        // Update the leaves
        Leaves updatedLeaves = leavesRepository.findById(leaves.getId()).get();
        // Disconnect from session so that the updates on updatedLeaves are not directly saved in db
        em.detach(updatedLeaves);
        updatedLeaves
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restLeavesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLeaves.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLeaves))
            )
            .andExpect(status().isOk());

        // Validate the Leaves in the database
        List<Leaves> leavesList = leavesRepository.findAll();
        assertThat(leavesList).hasSize(databaseSizeBeforeUpdate);
        Leaves testLeaves = leavesList.get(leavesList.size() - 1);
        assertThat(testLeaves.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testLeaves.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaves.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaves.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testLeaves.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingLeaves() throws Exception {
        int databaseSizeBeforeUpdate = leavesRepository.findAll().size();
        leaves.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeavesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leaves.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaves))
            )
            .andExpect(status().isBadRequest());

        // Validate the Leaves in the database
        List<Leaves> leavesList = leavesRepository.findAll();
        assertThat(leavesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeaves() throws Exception {
        int databaseSizeBeforeUpdate = leavesRepository.findAll().size();
        leaves.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeavesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaves))
            )
            .andExpect(status().isBadRequest());

        // Validate the Leaves in the database
        List<Leaves> leavesList = leavesRepository.findAll();
        assertThat(leavesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeaves() throws Exception {
        int databaseSizeBeforeUpdate = leavesRepository.findAll().size();
        leaves.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeavesMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leaves))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Leaves in the database
        List<Leaves> leavesList = leavesRepository.findAll();
        assertThat(leavesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeavesWithPatch() throws Exception {
        // Initialize the database
        leavesRepository.saveAndFlush(leaves);

        int databaseSizeBeforeUpdate = leavesRepository.findAll().size();

        // Update the leaves using partial update
        Leaves partialUpdatedLeaves = new Leaves();
        partialUpdatedLeaves.setId(leaves.getId());

        partialUpdatedLeaves.createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT).version(UPDATED_VERSION);

        restLeavesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaves.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaves))
            )
            .andExpect(status().isOk());

        // Validate the Leaves in the database
        List<Leaves> leavesList = leavesRepository.findAll();
        assertThat(leavesList).hasSize(databaseSizeBeforeUpdate);
        Leaves testLeaves = leavesList.get(leavesList.size() - 1);
        assertThat(testLeaves.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testLeaves.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaves.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaves.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testLeaves.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateLeavesWithPatch() throws Exception {
        // Initialize the database
        leavesRepository.saveAndFlush(leaves);

        int databaseSizeBeforeUpdate = leavesRepository.findAll().size();

        // Update the leaves using partial update
        Leaves partialUpdatedLeaves = new Leaves();
        partialUpdatedLeaves.setId(leaves.getId());

        partialUpdatedLeaves
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restLeavesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaves.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaves))
            )
            .andExpect(status().isOk());

        // Validate the Leaves in the database
        List<Leaves> leavesList = leavesRepository.findAll();
        assertThat(leavesList).hasSize(databaseSizeBeforeUpdate);
        Leaves testLeaves = leavesList.get(leavesList.size() - 1);
        assertThat(testLeaves.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testLeaves.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaves.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaves.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testLeaves.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingLeaves() throws Exception {
        int databaseSizeBeforeUpdate = leavesRepository.findAll().size();
        leaves.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeavesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leaves.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaves))
            )
            .andExpect(status().isBadRequest());

        // Validate the Leaves in the database
        List<Leaves> leavesList = leavesRepository.findAll();
        assertThat(leavesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeaves() throws Exception {
        int databaseSizeBeforeUpdate = leavesRepository.findAll().size();
        leaves.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeavesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaves))
            )
            .andExpect(status().isBadRequest());

        // Validate the Leaves in the database
        List<Leaves> leavesList = leavesRepository.findAll();
        assertThat(leavesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeaves() throws Exception {
        int databaseSizeBeforeUpdate = leavesRepository.findAll().size();
        leaves.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeavesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaves))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Leaves in the database
        List<Leaves> leavesList = leavesRepository.findAll();
        assertThat(leavesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLeaves() throws Exception {
        // Initialize the database
        leavesRepository.saveAndFlush(leaves);

        int databaseSizeBeforeDelete = leavesRepository.findAll().size();

        // Delete the leaves
        restLeavesMockMvc
            .perform(delete(ENTITY_API_URL_ID, leaves.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Leaves> leavesList = leavesRepository.findAll();
        assertThat(leavesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
