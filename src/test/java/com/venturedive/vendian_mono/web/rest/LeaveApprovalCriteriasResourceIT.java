package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.LeaveApprovalCriterias;
import com.venturedive.vendian_mono.domain.LeaveApprovers;
import com.venturedive.vendian_mono.domain.LeaveTypeApprovalRules;
import com.venturedive.vendian_mono.domain.UserAttributeApprovalRules;
import com.venturedive.vendian_mono.domain.UserRelationApprovalRules;
import com.venturedive.vendian_mono.repository.LeaveApprovalCriteriasRepository;
import com.venturedive.vendian_mono.service.criteria.LeaveApprovalCriteriasCriteria;
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
 * Integration tests for the {@link LeaveApprovalCriteriasResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeaveApprovalCriteriasResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/leave-approval-criterias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LeaveApprovalCriteriasRepository leaveApprovalCriteriasRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeaveApprovalCriteriasMockMvc;

    private LeaveApprovalCriterias leaveApprovalCriterias;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveApprovalCriterias createEntity(EntityManager em) {
        LeaveApprovalCriterias leaveApprovalCriterias = new LeaveApprovalCriterias()
            .name(DEFAULT_NAME)
            .priority(DEFAULT_PRIORITY)
            .effDate(DEFAULT_EFF_DATE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .endDate(DEFAULT_END_DATE)
            .version(DEFAULT_VERSION);
        return leaveApprovalCriterias;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveApprovalCriterias createUpdatedEntity(EntityManager em) {
        LeaveApprovalCriterias leaveApprovalCriterias = new LeaveApprovalCriterias()
            .name(UPDATED_NAME)
            .priority(UPDATED_PRIORITY)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);
        return leaveApprovalCriterias;
    }

    @BeforeEach
    public void initTest() {
        leaveApprovalCriterias = createEntity(em);
    }

    @Test
    @Transactional
    void createLeaveApprovalCriterias() throws Exception {
        int databaseSizeBeforeCreate = leaveApprovalCriteriasRepository.findAll().size();
        // Create the LeaveApprovalCriterias
        restLeaveApprovalCriteriasMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveApprovalCriterias))
            )
            .andExpect(status().isCreated());

        // Validate the LeaveApprovalCriterias in the database
        List<LeaveApprovalCriterias> leaveApprovalCriteriasList = leaveApprovalCriteriasRepository.findAll();
        assertThat(leaveApprovalCriteriasList).hasSize(databaseSizeBeforeCreate + 1);
        LeaveApprovalCriterias testLeaveApprovalCriterias = leaveApprovalCriteriasList.get(leaveApprovalCriteriasList.size() - 1);
        assertThat(testLeaveApprovalCriterias.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLeaveApprovalCriterias.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testLeaveApprovalCriterias.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testLeaveApprovalCriterias.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testLeaveApprovalCriterias.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testLeaveApprovalCriterias.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testLeaveApprovalCriterias.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createLeaveApprovalCriteriasWithExistingId() throws Exception {
        // Create the LeaveApprovalCriterias with an existing ID
        leaveApprovalCriterias.setId(1L);

        int databaseSizeBeforeCreate = leaveApprovalCriteriasRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeaveApprovalCriteriasMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveApprovalCriterias))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveApprovalCriterias in the database
        List<LeaveApprovalCriterias> leaveApprovalCriteriasList = leaveApprovalCriteriasRepository.findAll();
        assertThat(leaveApprovalCriteriasList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveApprovalCriteriasRepository.findAll().size();
        // set the field null
        leaveApprovalCriterias.setName(null);

        // Create the LeaveApprovalCriterias, which fails.

        restLeaveApprovalCriteriasMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveApprovalCriterias))
            )
            .andExpect(status().isBadRequest());

        List<LeaveApprovalCriterias> leaveApprovalCriteriasList = leaveApprovalCriteriasRepository.findAll();
        assertThat(leaveApprovalCriteriasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriorityIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveApprovalCriteriasRepository.findAll().size();
        // set the field null
        leaveApprovalCriterias.setPriority(null);

        // Create the LeaveApprovalCriterias, which fails.

        restLeaveApprovalCriteriasMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveApprovalCriterias))
            )
            .andExpect(status().isBadRequest());

        List<LeaveApprovalCriterias> leaveApprovalCriteriasList = leaveApprovalCriteriasRepository.findAll();
        assertThat(leaveApprovalCriteriasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEffDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveApprovalCriteriasRepository.findAll().size();
        // set the field null
        leaveApprovalCriterias.setEffDate(null);

        // Create the LeaveApprovalCriterias, which fails.

        restLeaveApprovalCriteriasMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveApprovalCriterias))
            )
            .andExpect(status().isBadRequest());

        List<LeaveApprovalCriterias> leaveApprovalCriteriasList = leaveApprovalCriteriasRepository.findAll();
        assertThat(leaveApprovalCriteriasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveApprovalCriteriasRepository.findAll().size();
        // set the field null
        leaveApprovalCriterias.setCreatedAt(null);

        // Create the LeaveApprovalCriterias, which fails.

        restLeaveApprovalCriteriasMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveApprovalCriterias))
            )
            .andExpect(status().isBadRequest());

        List<LeaveApprovalCriterias> leaveApprovalCriteriasList = leaveApprovalCriteriasRepository.findAll();
        assertThat(leaveApprovalCriteriasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveApprovalCriteriasRepository.findAll().size();
        // set the field null
        leaveApprovalCriterias.setUpdatedAt(null);

        // Create the LeaveApprovalCriterias, which fails.

        restLeaveApprovalCriteriasMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveApprovalCriterias))
            )
            .andExpect(status().isBadRequest());

        List<LeaveApprovalCriterias> leaveApprovalCriteriasList = leaveApprovalCriteriasRepository.findAll();
        assertThat(leaveApprovalCriteriasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveApprovalCriteriasRepository.findAll().size();
        // set the field null
        leaveApprovalCriterias.setVersion(null);

        // Create the LeaveApprovalCriterias, which fails.

        restLeaveApprovalCriteriasMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveApprovalCriterias))
            )
            .andExpect(status().isBadRequest());

        List<LeaveApprovalCriterias> leaveApprovalCriteriasList = leaveApprovalCriteriasRepository.findAll();
        assertThat(leaveApprovalCriteriasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLeaveApprovalCriterias() throws Exception {
        // Initialize the database
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);

        // Get all the leaveApprovalCriteriasList
        restLeaveApprovalCriteriasMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveApprovalCriterias.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getLeaveApprovalCriterias() throws Exception {
        // Initialize the database
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);

        // Get the leaveApprovalCriterias
        restLeaveApprovalCriteriasMockMvc
            .perform(get(ENTITY_API_URL_ID, leaveApprovalCriterias.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leaveApprovalCriterias.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY))
            .andExpect(jsonPath("$.effDate").value(DEFAULT_EFF_DATE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getLeaveApprovalCriteriasByIdFiltering() throws Exception {
        // Initialize the database
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);

        Long id = leaveApprovalCriterias.getId();

        defaultLeaveApprovalCriteriasShouldBeFound("id.equals=" + id);
        defaultLeaveApprovalCriteriasShouldNotBeFound("id.notEquals=" + id);

        defaultLeaveApprovalCriteriasShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLeaveApprovalCriteriasShouldNotBeFound("id.greaterThan=" + id);

        defaultLeaveApprovalCriteriasShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLeaveApprovalCriteriasShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLeaveApprovalCriteriasByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);

        // Get all the leaveApprovalCriteriasList where name equals to DEFAULT_NAME
        defaultLeaveApprovalCriteriasShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the leaveApprovalCriteriasList where name equals to UPDATED_NAME
        defaultLeaveApprovalCriteriasShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllLeaveApprovalCriteriasByNameIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);

        // Get all the leaveApprovalCriteriasList where name in DEFAULT_NAME or UPDATED_NAME
        defaultLeaveApprovalCriteriasShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the leaveApprovalCriteriasList where name equals to UPDATED_NAME
        defaultLeaveApprovalCriteriasShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllLeaveApprovalCriteriasByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);

        // Get all the leaveApprovalCriteriasList where name is not null
        defaultLeaveApprovalCriteriasShouldBeFound("name.specified=true");

        // Get all the leaveApprovalCriteriasList where name is null
        defaultLeaveApprovalCriteriasShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveApprovalCriteriasByNameContainsSomething() throws Exception {
        // Initialize the database
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);

        // Get all the leaveApprovalCriteriasList where name contains DEFAULT_NAME
        defaultLeaveApprovalCriteriasShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the leaveApprovalCriteriasList where name contains UPDATED_NAME
        defaultLeaveApprovalCriteriasShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllLeaveApprovalCriteriasByNameNotContainsSomething() throws Exception {
        // Initialize the database
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);

        // Get all the leaveApprovalCriteriasList where name does not contain DEFAULT_NAME
        defaultLeaveApprovalCriteriasShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the leaveApprovalCriteriasList where name does not contain UPDATED_NAME
        defaultLeaveApprovalCriteriasShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllLeaveApprovalCriteriasByPriorityIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);

        // Get all the leaveApprovalCriteriasList where priority equals to DEFAULT_PRIORITY
        defaultLeaveApprovalCriteriasShouldBeFound("priority.equals=" + DEFAULT_PRIORITY);

        // Get all the leaveApprovalCriteriasList where priority equals to UPDATED_PRIORITY
        defaultLeaveApprovalCriteriasShouldNotBeFound("priority.equals=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllLeaveApprovalCriteriasByPriorityIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);

        // Get all the leaveApprovalCriteriasList where priority in DEFAULT_PRIORITY or UPDATED_PRIORITY
        defaultLeaveApprovalCriteriasShouldBeFound("priority.in=" + DEFAULT_PRIORITY + "," + UPDATED_PRIORITY);

        // Get all the leaveApprovalCriteriasList where priority equals to UPDATED_PRIORITY
        defaultLeaveApprovalCriteriasShouldNotBeFound("priority.in=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllLeaveApprovalCriteriasByPriorityIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);

        // Get all the leaveApprovalCriteriasList where priority is not null
        defaultLeaveApprovalCriteriasShouldBeFound("priority.specified=true");

        // Get all the leaveApprovalCriteriasList where priority is null
        defaultLeaveApprovalCriteriasShouldNotBeFound("priority.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveApprovalCriteriasByPriorityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);

        // Get all the leaveApprovalCriteriasList where priority is greater than or equal to DEFAULT_PRIORITY
        defaultLeaveApprovalCriteriasShouldBeFound("priority.greaterThanOrEqual=" + DEFAULT_PRIORITY);

        // Get all the leaveApprovalCriteriasList where priority is greater than or equal to UPDATED_PRIORITY
        defaultLeaveApprovalCriteriasShouldNotBeFound("priority.greaterThanOrEqual=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllLeaveApprovalCriteriasByPriorityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);

        // Get all the leaveApprovalCriteriasList where priority is less than or equal to DEFAULT_PRIORITY
        defaultLeaveApprovalCriteriasShouldBeFound("priority.lessThanOrEqual=" + DEFAULT_PRIORITY);

        // Get all the leaveApprovalCriteriasList where priority is less than or equal to SMALLER_PRIORITY
        defaultLeaveApprovalCriteriasShouldNotBeFound("priority.lessThanOrEqual=" + SMALLER_PRIORITY);
    }

    @Test
    @Transactional
    void getAllLeaveApprovalCriteriasByPriorityIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);

        // Get all the leaveApprovalCriteriasList where priority is less than DEFAULT_PRIORITY
        defaultLeaveApprovalCriteriasShouldNotBeFound("priority.lessThan=" + DEFAULT_PRIORITY);

        // Get all the leaveApprovalCriteriasList where priority is less than UPDATED_PRIORITY
        defaultLeaveApprovalCriteriasShouldBeFound("priority.lessThan=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllLeaveApprovalCriteriasByPriorityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);

        // Get all the leaveApprovalCriteriasList where priority is greater than DEFAULT_PRIORITY
        defaultLeaveApprovalCriteriasShouldNotBeFound("priority.greaterThan=" + DEFAULT_PRIORITY);

        // Get all the leaveApprovalCriteriasList where priority is greater than SMALLER_PRIORITY
        defaultLeaveApprovalCriteriasShouldBeFound("priority.greaterThan=" + SMALLER_PRIORITY);
    }

    @Test
    @Transactional
    void getAllLeaveApprovalCriteriasByEffDateIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);

        // Get all the leaveApprovalCriteriasList where effDate equals to DEFAULT_EFF_DATE
        defaultLeaveApprovalCriteriasShouldBeFound("effDate.equals=" + DEFAULT_EFF_DATE);

        // Get all the leaveApprovalCriteriasList where effDate equals to UPDATED_EFF_DATE
        defaultLeaveApprovalCriteriasShouldNotBeFound("effDate.equals=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveApprovalCriteriasByEffDateIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);

        // Get all the leaveApprovalCriteriasList where effDate in DEFAULT_EFF_DATE or UPDATED_EFF_DATE
        defaultLeaveApprovalCriteriasShouldBeFound("effDate.in=" + DEFAULT_EFF_DATE + "," + UPDATED_EFF_DATE);

        // Get all the leaveApprovalCriteriasList where effDate equals to UPDATED_EFF_DATE
        defaultLeaveApprovalCriteriasShouldNotBeFound("effDate.in=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveApprovalCriteriasByEffDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);

        // Get all the leaveApprovalCriteriasList where effDate is not null
        defaultLeaveApprovalCriteriasShouldBeFound("effDate.specified=true");

        // Get all the leaveApprovalCriteriasList where effDate is null
        defaultLeaveApprovalCriteriasShouldNotBeFound("effDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveApprovalCriteriasByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);

        // Get all the leaveApprovalCriteriasList where createdAt equals to DEFAULT_CREATED_AT
        defaultLeaveApprovalCriteriasShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the leaveApprovalCriteriasList where createdAt equals to UPDATED_CREATED_AT
        defaultLeaveApprovalCriteriasShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveApprovalCriteriasByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);

        // Get all the leaveApprovalCriteriasList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultLeaveApprovalCriteriasShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the leaveApprovalCriteriasList where createdAt equals to UPDATED_CREATED_AT
        defaultLeaveApprovalCriteriasShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveApprovalCriteriasByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);

        // Get all the leaveApprovalCriteriasList where createdAt is not null
        defaultLeaveApprovalCriteriasShouldBeFound("createdAt.specified=true");

        // Get all the leaveApprovalCriteriasList where createdAt is null
        defaultLeaveApprovalCriteriasShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveApprovalCriteriasByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);

        // Get all the leaveApprovalCriteriasList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultLeaveApprovalCriteriasShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the leaveApprovalCriteriasList where updatedAt equals to UPDATED_UPDATED_AT
        defaultLeaveApprovalCriteriasShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveApprovalCriteriasByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);

        // Get all the leaveApprovalCriteriasList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultLeaveApprovalCriteriasShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the leaveApprovalCriteriasList where updatedAt equals to UPDATED_UPDATED_AT
        defaultLeaveApprovalCriteriasShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveApprovalCriteriasByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);

        // Get all the leaveApprovalCriteriasList where updatedAt is not null
        defaultLeaveApprovalCriteriasShouldBeFound("updatedAt.specified=true");

        // Get all the leaveApprovalCriteriasList where updatedAt is null
        defaultLeaveApprovalCriteriasShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveApprovalCriteriasByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);

        // Get all the leaveApprovalCriteriasList where endDate equals to DEFAULT_END_DATE
        defaultLeaveApprovalCriteriasShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the leaveApprovalCriteriasList where endDate equals to UPDATED_END_DATE
        defaultLeaveApprovalCriteriasShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveApprovalCriteriasByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);

        // Get all the leaveApprovalCriteriasList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultLeaveApprovalCriteriasShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the leaveApprovalCriteriasList where endDate equals to UPDATED_END_DATE
        defaultLeaveApprovalCriteriasShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveApprovalCriteriasByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);

        // Get all the leaveApprovalCriteriasList where endDate is not null
        defaultLeaveApprovalCriteriasShouldBeFound("endDate.specified=true");

        // Get all the leaveApprovalCriteriasList where endDate is null
        defaultLeaveApprovalCriteriasShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveApprovalCriteriasByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);

        // Get all the leaveApprovalCriteriasList where version equals to DEFAULT_VERSION
        defaultLeaveApprovalCriteriasShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the leaveApprovalCriteriasList where version equals to UPDATED_VERSION
        defaultLeaveApprovalCriteriasShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveApprovalCriteriasByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);

        // Get all the leaveApprovalCriteriasList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultLeaveApprovalCriteriasShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the leaveApprovalCriteriasList where version equals to UPDATED_VERSION
        defaultLeaveApprovalCriteriasShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveApprovalCriteriasByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);

        // Get all the leaveApprovalCriteriasList where version is not null
        defaultLeaveApprovalCriteriasShouldBeFound("version.specified=true");

        // Get all the leaveApprovalCriteriasList where version is null
        defaultLeaveApprovalCriteriasShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveApprovalCriteriasByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);

        // Get all the leaveApprovalCriteriasList where version is greater than or equal to DEFAULT_VERSION
        defaultLeaveApprovalCriteriasShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the leaveApprovalCriteriasList where version is greater than or equal to UPDATED_VERSION
        defaultLeaveApprovalCriteriasShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveApprovalCriteriasByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);

        // Get all the leaveApprovalCriteriasList where version is less than or equal to DEFAULT_VERSION
        defaultLeaveApprovalCriteriasShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the leaveApprovalCriteriasList where version is less than or equal to SMALLER_VERSION
        defaultLeaveApprovalCriteriasShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveApprovalCriteriasByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);

        // Get all the leaveApprovalCriteriasList where version is less than DEFAULT_VERSION
        defaultLeaveApprovalCriteriasShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the leaveApprovalCriteriasList where version is less than UPDATED_VERSION
        defaultLeaveApprovalCriteriasShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveApprovalCriteriasByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);

        // Get all the leaveApprovalCriteriasList where version is greater than DEFAULT_VERSION
        defaultLeaveApprovalCriteriasShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the leaveApprovalCriteriasList where version is greater than SMALLER_VERSION
        defaultLeaveApprovalCriteriasShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveApprovalCriteriasByLeaveapproversLeaveapprovalcriteriaIsEqualToSomething() throws Exception {
        LeaveApprovers leaveapproversLeaveapprovalcriteria;
        if (TestUtil.findAll(em, LeaveApprovers.class).isEmpty()) {
            leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);
            leaveapproversLeaveapprovalcriteria = LeaveApproversResourceIT.createEntity(em);
        } else {
            leaveapproversLeaveapprovalcriteria = TestUtil.findAll(em, LeaveApprovers.class).get(0);
        }
        em.persist(leaveapproversLeaveapprovalcriteria);
        em.flush();
        leaveApprovalCriterias.addLeaveapproversLeaveapprovalcriteria(leaveapproversLeaveapprovalcriteria);
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);
        Long leaveapproversLeaveapprovalcriteriaId = leaveapproversLeaveapprovalcriteria.getId();

        // Get all the leaveApprovalCriteriasList where leaveapproversLeaveapprovalcriteria equals to leaveapproversLeaveapprovalcriteriaId
        defaultLeaveApprovalCriteriasShouldBeFound("leaveapproversLeaveapprovalcriteriaId.equals=" + leaveapproversLeaveapprovalcriteriaId);

        // Get all the leaveApprovalCriteriasList where leaveapproversLeaveapprovalcriteria equals to (leaveapproversLeaveapprovalcriteriaId + 1)
        defaultLeaveApprovalCriteriasShouldNotBeFound(
            "leaveapproversLeaveapprovalcriteriaId.equals=" + (leaveapproversLeaveapprovalcriteriaId + 1)
        );
    }

    @Test
    @Transactional
    void getAllLeaveApprovalCriteriasByLeavetypeapprovalrulesLeaveapprovalcriteriaIsEqualToSomething() throws Exception {
        LeaveTypeApprovalRules leavetypeapprovalrulesLeaveapprovalcriteria;
        if (TestUtil.findAll(em, LeaveTypeApprovalRules.class).isEmpty()) {
            leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);
            leavetypeapprovalrulesLeaveapprovalcriteria = LeaveTypeApprovalRulesResourceIT.createEntity(em);
        } else {
            leavetypeapprovalrulesLeaveapprovalcriteria = TestUtil.findAll(em, LeaveTypeApprovalRules.class).get(0);
        }
        em.persist(leavetypeapprovalrulesLeaveapprovalcriteria);
        em.flush();
        leaveApprovalCriterias.addLeavetypeapprovalrulesLeaveapprovalcriteria(leavetypeapprovalrulesLeaveapprovalcriteria);
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);
        Long leavetypeapprovalrulesLeaveapprovalcriteriaId = leavetypeapprovalrulesLeaveapprovalcriteria.getId();

        // Get all the leaveApprovalCriteriasList where leavetypeapprovalrulesLeaveapprovalcriteria equals to leavetypeapprovalrulesLeaveapprovalcriteriaId
        defaultLeaveApprovalCriteriasShouldBeFound(
            "leavetypeapprovalrulesLeaveapprovalcriteriaId.equals=" + leavetypeapprovalrulesLeaveapprovalcriteriaId
        );

        // Get all the leaveApprovalCriteriasList where leavetypeapprovalrulesLeaveapprovalcriteria equals to (leavetypeapprovalrulesLeaveapprovalcriteriaId + 1)
        defaultLeaveApprovalCriteriasShouldNotBeFound(
            "leavetypeapprovalrulesLeaveapprovalcriteriaId.equals=" + (leavetypeapprovalrulesLeaveapprovalcriteriaId + 1)
        );
    }

    @Test
    @Transactional
    void getAllLeaveApprovalCriteriasByUserattributeapprovalrulesLeaveapprovalcriteriaIsEqualToSomething() throws Exception {
        UserAttributeApprovalRules userattributeapprovalrulesLeaveapprovalcriteria;
        if (TestUtil.findAll(em, UserAttributeApprovalRules.class).isEmpty()) {
            leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);
            userattributeapprovalrulesLeaveapprovalcriteria = UserAttributeApprovalRulesResourceIT.createEntity(em);
        } else {
            userattributeapprovalrulesLeaveapprovalcriteria = TestUtil.findAll(em, UserAttributeApprovalRules.class).get(0);
        }
        em.persist(userattributeapprovalrulesLeaveapprovalcriteria);
        em.flush();
        leaveApprovalCriterias.addUserattributeapprovalrulesLeaveapprovalcriteria(userattributeapprovalrulesLeaveapprovalcriteria);
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);
        Long userattributeapprovalrulesLeaveapprovalcriteriaId = userattributeapprovalrulesLeaveapprovalcriteria.getId();

        // Get all the leaveApprovalCriteriasList where userattributeapprovalrulesLeaveapprovalcriteria equals to userattributeapprovalrulesLeaveapprovalcriteriaId
        defaultLeaveApprovalCriteriasShouldBeFound(
            "userattributeapprovalrulesLeaveapprovalcriteriaId.equals=" + userattributeapprovalrulesLeaveapprovalcriteriaId
        );

        // Get all the leaveApprovalCriteriasList where userattributeapprovalrulesLeaveapprovalcriteria equals to (userattributeapprovalrulesLeaveapprovalcriteriaId + 1)
        defaultLeaveApprovalCriteriasShouldNotBeFound(
            "userattributeapprovalrulesLeaveapprovalcriteriaId.equals=" + (userattributeapprovalrulesLeaveapprovalcriteriaId + 1)
        );
    }

    @Test
    @Transactional
    void getAllLeaveApprovalCriteriasByUserrelationapprovalrulesLeaveapprovalcriteriaIsEqualToSomething() throws Exception {
        UserRelationApprovalRules userrelationapprovalrulesLeaveapprovalcriteria;
        if (TestUtil.findAll(em, UserRelationApprovalRules.class).isEmpty()) {
            leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);
            userrelationapprovalrulesLeaveapprovalcriteria = UserRelationApprovalRulesResourceIT.createEntity(em);
        } else {
            userrelationapprovalrulesLeaveapprovalcriteria = TestUtil.findAll(em, UserRelationApprovalRules.class).get(0);
        }
        em.persist(userrelationapprovalrulesLeaveapprovalcriteria);
        em.flush();
        leaveApprovalCriterias.addUserrelationapprovalrulesLeaveapprovalcriteria(userrelationapprovalrulesLeaveapprovalcriteria);
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);
        Long userrelationapprovalrulesLeaveapprovalcriteriaId = userrelationapprovalrulesLeaveapprovalcriteria.getId();

        // Get all the leaveApprovalCriteriasList where userrelationapprovalrulesLeaveapprovalcriteria equals to userrelationapprovalrulesLeaveapprovalcriteriaId
        defaultLeaveApprovalCriteriasShouldBeFound(
            "userrelationapprovalrulesLeaveapprovalcriteriaId.equals=" + userrelationapprovalrulesLeaveapprovalcriteriaId
        );

        // Get all the leaveApprovalCriteriasList where userrelationapprovalrulesLeaveapprovalcriteria equals to (userrelationapprovalrulesLeaveapprovalcriteriaId + 1)
        defaultLeaveApprovalCriteriasShouldNotBeFound(
            "userrelationapprovalrulesLeaveapprovalcriteriaId.equals=" + (userrelationapprovalrulesLeaveapprovalcriteriaId + 1)
        );
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLeaveApprovalCriteriasShouldBeFound(String filter) throws Exception {
        restLeaveApprovalCriteriasMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveApprovalCriterias.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restLeaveApprovalCriteriasMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLeaveApprovalCriteriasShouldNotBeFound(String filter) throws Exception {
        restLeaveApprovalCriteriasMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLeaveApprovalCriteriasMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLeaveApprovalCriterias() throws Exception {
        // Get the leaveApprovalCriterias
        restLeaveApprovalCriteriasMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLeaveApprovalCriterias() throws Exception {
        // Initialize the database
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);

        int databaseSizeBeforeUpdate = leaveApprovalCriteriasRepository.findAll().size();

        // Update the leaveApprovalCriterias
        LeaveApprovalCriterias updatedLeaveApprovalCriterias = leaveApprovalCriteriasRepository
            .findById(leaveApprovalCriterias.getId())
            .get();
        // Disconnect from session so that the updates on updatedLeaveApprovalCriterias are not directly saved in db
        em.detach(updatedLeaveApprovalCriterias);
        updatedLeaveApprovalCriterias
            .name(UPDATED_NAME)
            .priority(UPDATED_PRIORITY)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restLeaveApprovalCriteriasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLeaveApprovalCriterias.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLeaveApprovalCriterias))
            )
            .andExpect(status().isOk());

        // Validate the LeaveApprovalCriterias in the database
        List<LeaveApprovalCriterias> leaveApprovalCriteriasList = leaveApprovalCriteriasRepository.findAll();
        assertThat(leaveApprovalCriteriasList).hasSize(databaseSizeBeforeUpdate);
        LeaveApprovalCriterias testLeaveApprovalCriterias = leaveApprovalCriteriasList.get(leaveApprovalCriteriasList.size() - 1);
        assertThat(testLeaveApprovalCriterias.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLeaveApprovalCriterias.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testLeaveApprovalCriterias.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testLeaveApprovalCriterias.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveApprovalCriterias.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaveApprovalCriterias.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testLeaveApprovalCriterias.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingLeaveApprovalCriterias() throws Exception {
        int databaseSizeBeforeUpdate = leaveApprovalCriteriasRepository.findAll().size();
        leaveApprovalCriterias.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveApprovalCriteriasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leaveApprovalCriterias.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveApprovalCriterias))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveApprovalCriterias in the database
        List<LeaveApprovalCriterias> leaveApprovalCriteriasList = leaveApprovalCriteriasRepository.findAll();
        assertThat(leaveApprovalCriteriasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeaveApprovalCriterias() throws Exception {
        int databaseSizeBeforeUpdate = leaveApprovalCriteriasRepository.findAll().size();
        leaveApprovalCriterias.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveApprovalCriteriasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveApprovalCriterias))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveApprovalCriterias in the database
        List<LeaveApprovalCriterias> leaveApprovalCriteriasList = leaveApprovalCriteriasRepository.findAll();
        assertThat(leaveApprovalCriteriasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeaveApprovalCriterias() throws Exception {
        int databaseSizeBeforeUpdate = leaveApprovalCriteriasRepository.findAll().size();
        leaveApprovalCriterias.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveApprovalCriteriasMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveApprovalCriterias))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveApprovalCriterias in the database
        List<LeaveApprovalCriterias> leaveApprovalCriteriasList = leaveApprovalCriteriasRepository.findAll();
        assertThat(leaveApprovalCriteriasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeaveApprovalCriteriasWithPatch() throws Exception {
        // Initialize the database
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);

        int databaseSizeBeforeUpdate = leaveApprovalCriteriasRepository.findAll().size();

        // Update the leaveApprovalCriterias using partial update
        LeaveApprovalCriterias partialUpdatedLeaveApprovalCriterias = new LeaveApprovalCriterias();
        partialUpdatedLeaveApprovalCriterias.setId(leaveApprovalCriterias.getId());

        partialUpdatedLeaveApprovalCriterias.name(UPDATED_NAME).effDate(UPDATED_EFF_DATE).createdAt(UPDATED_CREATED_AT);

        restLeaveApprovalCriteriasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveApprovalCriterias.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveApprovalCriterias))
            )
            .andExpect(status().isOk());

        // Validate the LeaveApprovalCriterias in the database
        List<LeaveApprovalCriterias> leaveApprovalCriteriasList = leaveApprovalCriteriasRepository.findAll();
        assertThat(leaveApprovalCriteriasList).hasSize(databaseSizeBeforeUpdate);
        LeaveApprovalCriterias testLeaveApprovalCriterias = leaveApprovalCriteriasList.get(leaveApprovalCriteriasList.size() - 1);
        assertThat(testLeaveApprovalCriterias.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLeaveApprovalCriterias.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testLeaveApprovalCriterias.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testLeaveApprovalCriterias.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveApprovalCriterias.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testLeaveApprovalCriterias.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testLeaveApprovalCriterias.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateLeaveApprovalCriteriasWithPatch() throws Exception {
        // Initialize the database
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);

        int databaseSizeBeforeUpdate = leaveApprovalCriteriasRepository.findAll().size();

        // Update the leaveApprovalCriterias using partial update
        LeaveApprovalCriterias partialUpdatedLeaveApprovalCriterias = new LeaveApprovalCriterias();
        partialUpdatedLeaveApprovalCriterias.setId(leaveApprovalCriterias.getId());

        partialUpdatedLeaveApprovalCriterias
            .name(UPDATED_NAME)
            .priority(UPDATED_PRIORITY)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restLeaveApprovalCriteriasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveApprovalCriterias.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveApprovalCriterias))
            )
            .andExpect(status().isOk());

        // Validate the LeaveApprovalCriterias in the database
        List<LeaveApprovalCriterias> leaveApprovalCriteriasList = leaveApprovalCriteriasRepository.findAll();
        assertThat(leaveApprovalCriteriasList).hasSize(databaseSizeBeforeUpdate);
        LeaveApprovalCriterias testLeaveApprovalCriterias = leaveApprovalCriteriasList.get(leaveApprovalCriteriasList.size() - 1);
        assertThat(testLeaveApprovalCriterias.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLeaveApprovalCriterias.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testLeaveApprovalCriterias.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testLeaveApprovalCriterias.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveApprovalCriterias.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaveApprovalCriterias.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testLeaveApprovalCriterias.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingLeaveApprovalCriterias() throws Exception {
        int databaseSizeBeforeUpdate = leaveApprovalCriteriasRepository.findAll().size();
        leaveApprovalCriterias.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveApprovalCriteriasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leaveApprovalCriterias.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveApprovalCriterias))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveApprovalCriterias in the database
        List<LeaveApprovalCriterias> leaveApprovalCriteriasList = leaveApprovalCriteriasRepository.findAll();
        assertThat(leaveApprovalCriteriasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeaveApprovalCriterias() throws Exception {
        int databaseSizeBeforeUpdate = leaveApprovalCriteriasRepository.findAll().size();
        leaveApprovalCriterias.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveApprovalCriteriasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveApprovalCriterias))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveApprovalCriterias in the database
        List<LeaveApprovalCriterias> leaveApprovalCriteriasList = leaveApprovalCriteriasRepository.findAll();
        assertThat(leaveApprovalCriteriasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeaveApprovalCriterias() throws Exception {
        int databaseSizeBeforeUpdate = leaveApprovalCriteriasRepository.findAll().size();
        leaveApprovalCriterias.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveApprovalCriteriasMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveApprovalCriterias))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveApprovalCriterias in the database
        List<LeaveApprovalCriterias> leaveApprovalCriteriasList = leaveApprovalCriteriasRepository.findAll();
        assertThat(leaveApprovalCriteriasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLeaveApprovalCriterias() throws Exception {
        // Initialize the database
        leaveApprovalCriteriasRepository.saveAndFlush(leaveApprovalCriterias);

        int databaseSizeBeforeDelete = leaveApprovalCriteriasRepository.findAll().size();

        // Delete the leaveApprovalCriterias
        restLeaveApprovalCriteriasMockMvc
            .perform(delete(ENTITY_API_URL_ID, leaveApprovalCriterias.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeaveApprovalCriterias> leaveApprovalCriteriasList = leaveApprovalCriteriasRepository.findAll();
        assertThat(leaveApprovalCriteriasList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
