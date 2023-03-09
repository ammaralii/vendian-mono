package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.LeaveEscalationApprovers;
import com.venturedive.vendian_mono.domain.LeaveEscalationCriterias;
import com.venturedive.vendian_mono.domain.LeaveTypeEscalationRules;
import com.venturedive.vendian_mono.domain.UserAttributeEscalationRules;
import com.venturedive.vendian_mono.repository.LeaveEscalationCriteriasRepository;
import com.venturedive.vendian_mono.service.criteria.LeaveEscalationCriteriasCriteria;
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
 * Integration tests for the {@link LeaveEscalationCriteriasResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeaveEscalationCriteriasResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRIORITY = 1;
    private static final Integer UPDATED_PRIORITY = 2;
    private static final Integer SMALLER_PRIORITY = 1 - 1;

    private static final Integer DEFAULT_TOTAL = 1;
    private static final Integer UPDATED_TOTAL = 2;
    private static final Integer SMALLER_TOTAL = 1 - 1;

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

    private static final String ENTITY_API_URL = "/api/leave-escalation-criterias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LeaveEscalationCriteriasRepository leaveEscalationCriteriasRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeaveEscalationCriteriasMockMvc;

    private LeaveEscalationCriterias leaveEscalationCriterias;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveEscalationCriterias createEntity(EntityManager em) {
        LeaveEscalationCriterias leaveEscalationCriterias = new LeaveEscalationCriterias()
            .name(DEFAULT_NAME)
            .priority(DEFAULT_PRIORITY)
            .total(DEFAULT_TOTAL)
            .effDate(DEFAULT_EFF_DATE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .endDate(DEFAULT_END_DATE)
            .version(DEFAULT_VERSION);
        return leaveEscalationCriterias;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveEscalationCriterias createUpdatedEntity(EntityManager em) {
        LeaveEscalationCriterias leaveEscalationCriterias = new LeaveEscalationCriterias()
            .name(UPDATED_NAME)
            .priority(UPDATED_PRIORITY)
            .total(UPDATED_TOTAL)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);
        return leaveEscalationCriterias;
    }

    @BeforeEach
    public void initTest() {
        leaveEscalationCriterias = createEntity(em);
    }

    @Test
    @Transactional
    void createLeaveEscalationCriterias() throws Exception {
        int databaseSizeBeforeCreate = leaveEscalationCriteriasRepository.findAll().size();
        // Create the LeaveEscalationCriterias
        restLeaveEscalationCriteriasMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveEscalationCriterias))
            )
            .andExpect(status().isCreated());

        // Validate the LeaveEscalationCriterias in the database
        List<LeaveEscalationCriterias> leaveEscalationCriteriasList = leaveEscalationCriteriasRepository.findAll();
        assertThat(leaveEscalationCriteriasList).hasSize(databaseSizeBeforeCreate + 1);
        LeaveEscalationCriterias testLeaveEscalationCriterias = leaveEscalationCriteriasList.get(leaveEscalationCriteriasList.size() - 1);
        assertThat(testLeaveEscalationCriterias.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLeaveEscalationCriterias.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testLeaveEscalationCriterias.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testLeaveEscalationCriterias.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testLeaveEscalationCriterias.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testLeaveEscalationCriterias.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testLeaveEscalationCriterias.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testLeaveEscalationCriterias.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createLeaveEscalationCriteriasWithExistingId() throws Exception {
        // Create the LeaveEscalationCriterias with an existing ID
        leaveEscalationCriterias.setId(1L);

        int databaseSizeBeforeCreate = leaveEscalationCriteriasRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeaveEscalationCriteriasMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveEscalationCriterias))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveEscalationCriterias in the database
        List<LeaveEscalationCriterias> leaveEscalationCriteriasList = leaveEscalationCriteriasRepository.findAll();
        assertThat(leaveEscalationCriteriasList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveEscalationCriteriasRepository.findAll().size();
        // set the field null
        leaveEscalationCriterias.setName(null);

        // Create the LeaveEscalationCriterias, which fails.

        restLeaveEscalationCriteriasMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveEscalationCriterias))
            )
            .andExpect(status().isBadRequest());

        List<LeaveEscalationCriterias> leaveEscalationCriteriasList = leaveEscalationCriteriasRepository.findAll();
        assertThat(leaveEscalationCriteriasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriorityIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveEscalationCriteriasRepository.findAll().size();
        // set the field null
        leaveEscalationCriterias.setPriority(null);

        // Create the LeaveEscalationCriterias, which fails.

        restLeaveEscalationCriteriasMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveEscalationCriterias))
            )
            .andExpect(status().isBadRequest());

        List<LeaveEscalationCriterias> leaveEscalationCriteriasList = leaveEscalationCriteriasRepository.findAll();
        assertThat(leaveEscalationCriteriasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveEscalationCriteriasRepository.findAll().size();
        // set the field null
        leaveEscalationCriterias.setTotal(null);

        // Create the LeaveEscalationCriterias, which fails.

        restLeaveEscalationCriteriasMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveEscalationCriterias))
            )
            .andExpect(status().isBadRequest());

        List<LeaveEscalationCriterias> leaveEscalationCriteriasList = leaveEscalationCriteriasRepository.findAll();
        assertThat(leaveEscalationCriteriasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEffDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveEscalationCriteriasRepository.findAll().size();
        // set the field null
        leaveEscalationCriterias.setEffDate(null);

        // Create the LeaveEscalationCriterias, which fails.

        restLeaveEscalationCriteriasMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveEscalationCriterias))
            )
            .andExpect(status().isBadRequest());

        List<LeaveEscalationCriterias> leaveEscalationCriteriasList = leaveEscalationCriteriasRepository.findAll();
        assertThat(leaveEscalationCriteriasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveEscalationCriteriasRepository.findAll().size();
        // set the field null
        leaveEscalationCriterias.setCreatedAt(null);

        // Create the LeaveEscalationCriterias, which fails.

        restLeaveEscalationCriteriasMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveEscalationCriterias))
            )
            .andExpect(status().isBadRequest());

        List<LeaveEscalationCriterias> leaveEscalationCriteriasList = leaveEscalationCriteriasRepository.findAll();
        assertThat(leaveEscalationCriteriasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveEscalationCriteriasRepository.findAll().size();
        // set the field null
        leaveEscalationCriterias.setUpdatedAt(null);

        // Create the LeaveEscalationCriterias, which fails.

        restLeaveEscalationCriteriasMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveEscalationCriterias))
            )
            .andExpect(status().isBadRequest());

        List<LeaveEscalationCriterias> leaveEscalationCriteriasList = leaveEscalationCriteriasRepository.findAll();
        assertThat(leaveEscalationCriteriasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveEscalationCriteriasRepository.findAll().size();
        // set the field null
        leaveEscalationCriterias.setVersion(null);

        // Create the LeaveEscalationCriterias, which fails.

        restLeaveEscalationCriteriasMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveEscalationCriterias))
            )
            .andExpect(status().isBadRequest());

        List<LeaveEscalationCriterias> leaveEscalationCriteriasList = leaveEscalationCriteriasRepository.findAll();
        assertThat(leaveEscalationCriteriasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriterias() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get all the leaveEscalationCriteriasList
        restLeaveEscalationCriteriasMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveEscalationCriterias.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL)))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getLeaveEscalationCriterias() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get the leaveEscalationCriterias
        restLeaveEscalationCriteriasMockMvc
            .perform(get(ENTITY_API_URL_ID, leaveEscalationCriterias.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leaveEscalationCriterias.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL))
            .andExpect(jsonPath("$.effDate").value(DEFAULT_EFF_DATE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getLeaveEscalationCriteriasByIdFiltering() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        Long id = leaveEscalationCriterias.getId();

        defaultLeaveEscalationCriteriasShouldBeFound("id.equals=" + id);
        defaultLeaveEscalationCriteriasShouldNotBeFound("id.notEquals=" + id);

        defaultLeaveEscalationCriteriasShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLeaveEscalationCriteriasShouldNotBeFound("id.greaterThan=" + id);

        defaultLeaveEscalationCriteriasShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLeaveEscalationCriteriasShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get all the leaveEscalationCriteriasList where name equals to DEFAULT_NAME
        defaultLeaveEscalationCriteriasShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the leaveEscalationCriteriasList where name equals to UPDATED_NAME
        defaultLeaveEscalationCriteriasShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByNameIsInShouldWork() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get all the leaveEscalationCriteriasList where name in DEFAULT_NAME or UPDATED_NAME
        defaultLeaveEscalationCriteriasShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the leaveEscalationCriteriasList where name equals to UPDATED_NAME
        defaultLeaveEscalationCriteriasShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get all the leaveEscalationCriteriasList where name is not null
        defaultLeaveEscalationCriteriasShouldBeFound("name.specified=true");

        // Get all the leaveEscalationCriteriasList where name is null
        defaultLeaveEscalationCriteriasShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByNameContainsSomething() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get all the leaveEscalationCriteriasList where name contains DEFAULT_NAME
        defaultLeaveEscalationCriteriasShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the leaveEscalationCriteriasList where name contains UPDATED_NAME
        defaultLeaveEscalationCriteriasShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByNameNotContainsSomething() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get all the leaveEscalationCriteriasList where name does not contain DEFAULT_NAME
        defaultLeaveEscalationCriteriasShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the leaveEscalationCriteriasList where name does not contain UPDATED_NAME
        defaultLeaveEscalationCriteriasShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByPriorityIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get all the leaveEscalationCriteriasList where priority equals to DEFAULT_PRIORITY
        defaultLeaveEscalationCriteriasShouldBeFound("priority.equals=" + DEFAULT_PRIORITY);

        // Get all the leaveEscalationCriteriasList where priority equals to UPDATED_PRIORITY
        defaultLeaveEscalationCriteriasShouldNotBeFound("priority.equals=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByPriorityIsInShouldWork() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get all the leaveEscalationCriteriasList where priority in DEFAULT_PRIORITY or UPDATED_PRIORITY
        defaultLeaveEscalationCriteriasShouldBeFound("priority.in=" + DEFAULT_PRIORITY + "," + UPDATED_PRIORITY);

        // Get all the leaveEscalationCriteriasList where priority equals to UPDATED_PRIORITY
        defaultLeaveEscalationCriteriasShouldNotBeFound("priority.in=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByPriorityIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get all the leaveEscalationCriteriasList where priority is not null
        defaultLeaveEscalationCriteriasShouldBeFound("priority.specified=true");

        // Get all the leaveEscalationCriteriasList where priority is null
        defaultLeaveEscalationCriteriasShouldNotBeFound("priority.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByPriorityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get all the leaveEscalationCriteriasList where priority is greater than or equal to DEFAULT_PRIORITY
        defaultLeaveEscalationCriteriasShouldBeFound("priority.greaterThanOrEqual=" + DEFAULT_PRIORITY);

        // Get all the leaveEscalationCriteriasList where priority is greater than or equal to UPDATED_PRIORITY
        defaultLeaveEscalationCriteriasShouldNotBeFound("priority.greaterThanOrEqual=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByPriorityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get all the leaveEscalationCriteriasList where priority is less than or equal to DEFAULT_PRIORITY
        defaultLeaveEscalationCriteriasShouldBeFound("priority.lessThanOrEqual=" + DEFAULT_PRIORITY);

        // Get all the leaveEscalationCriteriasList where priority is less than or equal to SMALLER_PRIORITY
        defaultLeaveEscalationCriteriasShouldNotBeFound("priority.lessThanOrEqual=" + SMALLER_PRIORITY);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByPriorityIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get all the leaveEscalationCriteriasList where priority is less than DEFAULT_PRIORITY
        defaultLeaveEscalationCriteriasShouldNotBeFound("priority.lessThan=" + DEFAULT_PRIORITY);

        // Get all the leaveEscalationCriteriasList where priority is less than UPDATED_PRIORITY
        defaultLeaveEscalationCriteriasShouldBeFound("priority.lessThan=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByPriorityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get all the leaveEscalationCriteriasList where priority is greater than DEFAULT_PRIORITY
        defaultLeaveEscalationCriteriasShouldNotBeFound("priority.greaterThan=" + DEFAULT_PRIORITY);

        // Get all the leaveEscalationCriteriasList where priority is greater than SMALLER_PRIORITY
        defaultLeaveEscalationCriteriasShouldBeFound("priority.greaterThan=" + SMALLER_PRIORITY);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get all the leaveEscalationCriteriasList where total equals to DEFAULT_TOTAL
        defaultLeaveEscalationCriteriasShouldBeFound("total.equals=" + DEFAULT_TOTAL);

        // Get all the leaveEscalationCriteriasList where total equals to UPDATED_TOTAL
        defaultLeaveEscalationCriteriasShouldNotBeFound("total.equals=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByTotalIsInShouldWork() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get all the leaveEscalationCriteriasList where total in DEFAULT_TOTAL or UPDATED_TOTAL
        defaultLeaveEscalationCriteriasShouldBeFound("total.in=" + DEFAULT_TOTAL + "," + UPDATED_TOTAL);

        // Get all the leaveEscalationCriteriasList where total equals to UPDATED_TOTAL
        defaultLeaveEscalationCriteriasShouldNotBeFound("total.in=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get all the leaveEscalationCriteriasList where total is not null
        defaultLeaveEscalationCriteriasShouldBeFound("total.specified=true");

        // Get all the leaveEscalationCriteriasList where total is null
        defaultLeaveEscalationCriteriasShouldNotBeFound("total.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get all the leaveEscalationCriteriasList where total is greater than or equal to DEFAULT_TOTAL
        defaultLeaveEscalationCriteriasShouldBeFound("total.greaterThanOrEqual=" + DEFAULT_TOTAL);

        // Get all the leaveEscalationCriteriasList where total is greater than or equal to UPDATED_TOTAL
        defaultLeaveEscalationCriteriasShouldNotBeFound("total.greaterThanOrEqual=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get all the leaveEscalationCriteriasList where total is less than or equal to DEFAULT_TOTAL
        defaultLeaveEscalationCriteriasShouldBeFound("total.lessThanOrEqual=" + DEFAULT_TOTAL);

        // Get all the leaveEscalationCriteriasList where total is less than or equal to SMALLER_TOTAL
        defaultLeaveEscalationCriteriasShouldNotBeFound("total.lessThanOrEqual=" + SMALLER_TOTAL);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get all the leaveEscalationCriteriasList where total is less than DEFAULT_TOTAL
        defaultLeaveEscalationCriteriasShouldNotBeFound("total.lessThan=" + DEFAULT_TOTAL);

        // Get all the leaveEscalationCriteriasList where total is less than UPDATED_TOTAL
        defaultLeaveEscalationCriteriasShouldBeFound("total.lessThan=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get all the leaveEscalationCriteriasList where total is greater than DEFAULT_TOTAL
        defaultLeaveEscalationCriteriasShouldNotBeFound("total.greaterThan=" + DEFAULT_TOTAL);

        // Get all the leaveEscalationCriteriasList where total is greater than SMALLER_TOTAL
        defaultLeaveEscalationCriteriasShouldBeFound("total.greaterThan=" + SMALLER_TOTAL);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByEffDateIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get all the leaveEscalationCriteriasList where effDate equals to DEFAULT_EFF_DATE
        defaultLeaveEscalationCriteriasShouldBeFound("effDate.equals=" + DEFAULT_EFF_DATE);

        // Get all the leaveEscalationCriteriasList where effDate equals to UPDATED_EFF_DATE
        defaultLeaveEscalationCriteriasShouldNotBeFound("effDate.equals=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByEffDateIsInShouldWork() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get all the leaveEscalationCriteriasList where effDate in DEFAULT_EFF_DATE or UPDATED_EFF_DATE
        defaultLeaveEscalationCriteriasShouldBeFound("effDate.in=" + DEFAULT_EFF_DATE + "," + UPDATED_EFF_DATE);

        // Get all the leaveEscalationCriteriasList where effDate equals to UPDATED_EFF_DATE
        defaultLeaveEscalationCriteriasShouldNotBeFound("effDate.in=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByEffDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get all the leaveEscalationCriteriasList where effDate is not null
        defaultLeaveEscalationCriteriasShouldBeFound("effDate.specified=true");

        // Get all the leaveEscalationCriteriasList where effDate is null
        defaultLeaveEscalationCriteriasShouldNotBeFound("effDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get all the leaveEscalationCriteriasList where createdAt equals to DEFAULT_CREATED_AT
        defaultLeaveEscalationCriteriasShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the leaveEscalationCriteriasList where createdAt equals to UPDATED_CREATED_AT
        defaultLeaveEscalationCriteriasShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get all the leaveEscalationCriteriasList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultLeaveEscalationCriteriasShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the leaveEscalationCriteriasList where createdAt equals to UPDATED_CREATED_AT
        defaultLeaveEscalationCriteriasShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get all the leaveEscalationCriteriasList where createdAt is not null
        defaultLeaveEscalationCriteriasShouldBeFound("createdAt.specified=true");

        // Get all the leaveEscalationCriteriasList where createdAt is null
        defaultLeaveEscalationCriteriasShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get all the leaveEscalationCriteriasList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultLeaveEscalationCriteriasShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the leaveEscalationCriteriasList where updatedAt equals to UPDATED_UPDATED_AT
        defaultLeaveEscalationCriteriasShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get all the leaveEscalationCriteriasList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultLeaveEscalationCriteriasShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the leaveEscalationCriteriasList where updatedAt equals to UPDATED_UPDATED_AT
        defaultLeaveEscalationCriteriasShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get all the leaveEscalationCriteriasList where updatedAt is not null
        defaultLeaveEscalationCriteriasShouldBeFound("updatedAt.specified=true");

        // Get all the leaveEscalationCriteriasList where updatedAt is null
        defaultLeaveEscalationCriteriasShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get all the leaveEscalationCriteriasList where endDate equals to DEFAULT_END_DATE
        defaultLeaveEscalationCriteriasShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the leaveEscalationCriteriasList where endDate equals to UPDATED_END_DATE
        defaultLeaveEscalationCriteriasShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get all the leaveEscalationCriteriasList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultLeaveEscalationCriteriasShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the leaveEscalationCriteriasList where endDate equals to UPDATED_END_DATE
        defaultLeaveEscalationCriteriasShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get all the leaveEscalationCriteriasList where endDate is not null
        defaultLeaveEscalationCriteriasShouldBeFound("endDate.specified=true");

        // Get all the leaveEscalationCriteriasList where endDate is null
        defaultLeaveEscalationCriteriasShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get all the leaveEscalationCriteriasList where version equals to DEFAULT_VERSION
        defaultLeaveEscalationCriteriasShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the leaveEscalationCriteriasList where version equals to UPDATED_VERSION
        defaultLeaveEscalationCriteriasShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get all the leaveEscalationCriteriasList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultLeaveEscalationCriteriasShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the leaveEscalationCriteriasList where version equals to UPDATED_VERSION
        defaultLeaveEscalationCriteriasShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get all the leaveEscalationCriteriasList where version is not null
        defaultLeaveEscalationCriteriasShouldBeFound("version.specified=true");

        // Get all the leaveEscalationCriteriasList where version is null
        defaultLeaveEscalationCriteriasShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get all the leaveEscalationCriteriasList where version is greater than or equal to DEFAULT_VERSION
        defaultLeaveEscalationCriteriasShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the leaveEscalationCriteriasList where version is greater than or equal to UPDATED_VERSION
        defaultLeaveEscalationCriteriasShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get all the leaveEscalationCriteriasList where version is less than or equal to DEFAULT_VERSION
        defaultLeaveEscalationCriteriasShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the leaveEscalationCriteriasList where version is less than or equal to SMALLER_VERSION
        defaultLeaveEscalationCriteriasShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get all the leaveEscalationCriteriasList where version is less than DEFAULT_VERSION
        defaultLeaveEscalationCriteriasShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the leaveEscalationCriteriasList where version is less than UPDATED_VERSION
        defaultLeaveEscalationCriteriasShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        // Get all the leaveEscalationCriteriasList where version is greater than DEFAULT_VERSION
        defaultLeaveEscalationCriteriasShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the leaveEscalationCriteriasList where version is greater than SMALLER_VERSION
        defaultLeaveEscalationCriteriasShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByLeaveescalationapproversLeaveescalationcriteriaIsEqualToSomething() throws Exception {
        LeaveEscalationApprovers leaveescalationapproversLeaveescalationcriteria;
        if (TestUtil.findAll(em, LeaveEscalationApprovers.class).isEmpty()) {
            leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);
            leaveescalationapproversLeaveescalationcriteria = LeaveEscalationApproversResourceIT.createEntity(em);
        } else {
            leaveescalationapproversLeaveescalationcriteria = TestUtil.findAll(em, LeaveEscalationApprovers.class).get(0);
        }
        em.persist(leaveescalationapproversLeaveescalationcriteria);
        em.flush();
        leaveEscalationCriterias.addLeaveescalationapproversLeaveescalationcriteria(leaveescalationapproversLeaveescalationcriteria);
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);
        Long leaveescalationapproversLeaveescalationcriteriaId = leaveescalationapproversLeaveescalationcriteria.getId();

        // Get all the leaveEscalationCriteriasList where leaveescalationapproversLeaveescalationcriteria equals to leaveescalationapproversLeaveescalationcriteriaId
        defaultLeaveEscalationCriteriasShouldBeFound(
            "leaveescalationapproversLeaveescalationcriteriaId.equals=" + leaveescalationapproversLeaveescalationcriteriaId
        );

        // Get all the leaveEscalationCriteriasList where leaveescalationapproversLeaveescalationcriteria equals to (leaveescalationapproversLeaveescalationcriteriaId + 1)
        defaultLeaveEscalationCriteriasShouldNotBeFound(
            "leaveescalationapproversLeaveescalationcriteriaId.equals=" + (leaveescalationapproversLeaveescalationcriteriaId + 1)
        );
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByLeavetypeescalationrulesLeaveescalationcriteriaIsEqualToSomething() throws Exception {
        LeaveTypeEscalationRules leavetypeescalationrulesLeaveescalationcriteria;
        if (TestUtil.findAll(em, LeaveTypeEscalationRules.class).isEmpty()) {
            leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);
            leavetypeescalationrulesLeaveescalationcriteria = LeaveTypeEscalationRulesResourceIT.createEntity(em);
        } else {
            leavetypeescalationrulesLeaveescalationcriteria = TestUtil.findAll(em, LeaveTypeEscalationRules.class).get(0);
        }
        em.persist(leavetypeescalationrulesLeaveescalationcriteria);
        em.flush();
        leaveEscalationCriterias.addLeavetypeescalationrulesLeaveescalationcriteria(leavetypeescalationrulesLeaveescalationcriteria);
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);
        Long leavetypeescalationrulesLeaveescalationcriteriaId = leavetypeescalationrulesLeaveescalationcriteria.getId();

        // Get all the leaveEscalationCriteriasList where leavetypeescalationrulesLeaveescalationcriteria equals to leavetypeescalationrulesLeaveescalationcriteriaId
        defaultLeaveEscalationCriteriasShouldBeFound(
            "leavetypeescalationrulesLeaveescalationcriteriaId.equals=" + leavetypeescalationrulesLeaveescalationcriteriaId
        );

        // Get all the leaveEscalationCriteriasList where leavetypeescalationrulesLeaveescalationcriteria equals to (leavetypeescalationrulesLeaveescalationcriteriaId + 1)
        defaultLeaveEscalationCriteriasShouldNotBeFound(
            "leavetypeescalationrulesLeaveescalationcriteriaId.equals=" + (leavetypeescalationrulesLeaveescalationcriteriaId + 1)
        );
    }

    @Test
    @Transactional
    void getAllLeaveEscalationCriteriasByUserattributeescalationrulesLeaveescalationcriteriaIsEqualToSomething() throws Exception {
        UserAttributeEscalationRules userattributeescalationrulesLeaveescalationcriteria;
        if (TestUtil.findAll(em, UserAttributeEscalationRules.class).isEmpty()) {
            leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);
            userattributeescalationrulesLeaveescalationcriteria = UserAttributeEscalationRulesResourceIT.createEntity(em);
        } else {
            userattributeescalationrulesLeaveescalationcriteria = TestUtil.findAll(em, UserAttributeEscalationRules.class).get(0);
        }
        em.persist(userattributeescalationrulesLeaveescalationcriteria);
        em.flush();
        leaveEscalationCriterias.addUserattributeescalationrulesLeaveescalationcriteria(
            userattributeescalationrulesLeaveescalationcriteria
        );
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);
        Long userattributeescalationrulesLeaveescalationcriteriaId = userattributeescalationrulesLeaveescalationcriteria.getId();

        // Get all the leaveEscalationCriteriasList where userattributeescalationrulesLeaveescalationcriteria equals to userattributeescalationrulesLeaveescalationcriteriaId
        defaultLeaveEscalationCriteriasShouldBeFound(
            "userattributeescalationrulesLeaveescalationcriteriaId.equals=" + userattributeescalationrulesLeaveescalationcriteriaId
        );

        // Get all the leaveEscalationCriteriasList where userattributeescalationrulesLeaveescalationcriteria equals to (userattributeescalationrulesLeaveescalationcriteriaId + 1)
        defaultLeaveEscalationCriteriasShouldNotBeFound(
            "userattributeescalationrulesLeaveescalationcriteriaId.equals=" + (userattributeescalationrulesLeaveescalationcriteriaId + 1)
        );
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLeaveEscalationCriteriasShouldBeFound(String filter) throws Exception {
        restLeaveEscalationCriteriasMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveEscalationCriterias.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL)))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restLeaveEscalationCriteriasMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLeaveEscalationCriteriasShouldNotBeFound(String filter) throws Exception {
        restLeaveEscalationCriteriasMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLeaveEscalationCriteriasMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLeaveEscalationCriterias() throws Exception {
        // Get the leaveEscalationCriterias
        restLeaveEscalationCriteriasMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLeaveEscalationCriterias() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        int databaseSizeBeforeUpdate = leaveEscalationCriteriasRepository.findAll().size();

        // Update the leaveEscalationCriterias
        LeaveEscalationCriterias updatedLeaveEscalationCriterias = leaveEscalationCriteriasRepository
            .findById(leaveEscalationCriterias.getId())
            .get();
        // Disconnect from session so that the updates on updatedLeaveEscalationCriterias are not directly saved in db
        em.detach(updatedLeaveEscalationCriterias);
        updatedLeaveEscalationCriterias
            .name(UPDATED_NAME)
            .priority(UPDATED_PRIORITY)
            .total(UPDATED_TOTAL)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restLeaveEscalationCriteriasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLeaveEscalationCriterias.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLeaveEscalationCriterias))
            )
            .andExpect(status().isOk());

        // Validate the LeaveEscalationCriterias in the database
        List<LeaveEscalationCriterias> leaveEscalationCriteriasList = leaveEscalationCriteriasRepository.findAll();
        assertThat(leaveEscalationCriteriasList).hasSize(databaseSizeBeforeUpdate);
        LeaveEscalationCriterias testLeaveEscalationCriterias = leaveEscalationCriteriasList.get(leaveEscalationCriteriasList.size() - 1);
        assertThat(testLeaveEscalationCriterias.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLeaveEscalationCriterias.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testLeaveEscalationCriterias.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testLeaveEscalationCriterias.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testLeaveEscalationCriterias.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveEscalationCriterias.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaveEscalationCriterias.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testLeaveEscalationCriterias.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingLeaveEscalationCriterias() throws Exception {
        int databaseSizeBeforeUpdate = leaveEscalationCriteriasRepository.findAll().size();
        leaveEscalationCriterias.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveEscalationCriteriasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leaveEscalationCriterias.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveEscalationCriterias))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveEscalationCriterias in the database
        List<LeaveEscalationCriterias> leaveEscalationCriteriasList = leaveEscalationCriteriasRepository.findAll();
        assertThat(leaveEscalationCriteriasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeaveEscalationCriterias() throws Exception {
        int databaseSizeBeforeUpdate = leaveEscalationCriteriasRepository.findAll().size();
        leaveEscalationCriterias.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveEscalationCriteriasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveEscalationCriterias))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveEscalationCriterias in the database
        List<LeaveEscalationCriterias> leaveEscalationCriteriasList = leaveEscalationCriteriasRepository.findAll();
        assertThat(leaveEscalationCriteriasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeaveEscalationCriterias() throws Exception {
        int databaseSizeBeforeUpdate = leaveEscalationCriteriasRepository.findAll().size();
        leaveEscalationCriterias.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveEscalationCriteriasMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveEscalationCriterias))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveEscalationCriterias in the database
        List<LeaveEscalationCriterias> leaveEscalationCriteriasList = leaveEscalationCriteriasRepository.findAll();
        assertThat(leaveEscalationCriteriasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeaveEscalationCriteriasWithPatch() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        int databaseSizeBeforeUpdate = leaveEscalationCriteriasRepository.findAll().size();

        // Update the leaveEscalationCriterias using partial update
        LeaveEscalationCriterias partialUpdatedLeaveEscalationCriterias = new LeaveEscalationCriterias();
        partialUpdatedLeaveEscalationCriterias.setId(leaveEscalationCriterias.getId());

        partialUpdatedLeaveEscalationCriterias
            .priority(UPDATED_PRIORITY)
            .total(UPDATED_TOTAL)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .version(UPDATED_VERSION);

        restLeaveEscalationCriteriasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveEscalationCriterias.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveEscalationCriterias))
            )
            .andExpect(status().isOk());

        // Validate the LeaveEscalationCriterias in the database
        List<LeaveEscalationCriterias> leaveEscalationCriteriasList = leaveEscalationCriteriasRepository.findAll();
        assertThat(leaveEscalationCriteriasList).hasSize(databaseSizeBeforeUpdate);
        LeaveEscalationCriterias testLeaveEscalationCriterias = leaveEscalationCriteriasList.get(leaveEscalationCriteriasList.size() - 1);
        assertThat(testLeaveEscalationCriterias.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLeaveEscalationCriterias.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testLeaveEscalationCriterias.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testLeaveEscalationCriterias.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testLeaveEscalationCriterias.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveEscalationCriterias.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testLeaveEscalationCriterias.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testLeaveEscalationCriterias.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateLeaveEscalationCriteriasWithPatch() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        int databaseSizeBeforeUpdate = leaveEscalationCriteriasRepository.findAll().size();

        // Update the leaveEscalationCriterias using partial update
        LeaveEscalationCriterias partialUpdatedLeaveEscalationCriterias = new LeaveEscalationCriterias();
        partialUpdatedLeaveEscalationCriterias.setId(leaveEscalationCriterias.getId());

        partialUpdatedLeaveEscalationCriterias
            .name(UPDATED_NAME)
            .priority(UPDATED_PRIORITY)
            .total(UPDATED_TOTAL)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restLeaveEscalationCriteriasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveEscalationCriterias.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveEscalationCriterias))
            )
            .andExpect(status().isOk());

        // Validate the LeaveEscalationCriterias in the database
        List<LeaveEscalationCriterias> leaveEscalationCriteriasList = leaveEscalationCriteriasRepository.findAll();
        assertThat(leaveEscalationCriteriasList).hasSize(databaseSizeBeforeUpdate);
        LeaveEscalationCriterias testLeaveEscalationCriterias = leaveEscalationCriteriasList.get(leaveEscalationCriteriasList.size() - 1);
        assertThat(testLeaveEscalationCriterias.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLeaveEscalationCriterias.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testLeaveEscalationCriterias.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testLeaveEscalationCriterias.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testLeaveEscalationCriterias.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveEscalationCriterias.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaveEscalationCriterias.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testLeaveEscalationCriterias.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingLeaveEscalationCriterias() throws Exception {
        int databaseSizeBeforeUpdate = leaveEscalationCriteriasRepository.findAll().size();
        leaveEscalationCriterias.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveEscalationCriteriasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leaveEscalationCriterias.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveEscalationCriterias))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveEscalationCriterias in the database
        List<LeaveEscalationCriterias> leaveEscalationCriteriasList = leaveEscalationCriteriasRepository.findAll();
        assertThat(leaveEscalationCriteriasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeaveEscalationCriterias() throws Exception {
        int databaseSizeBeforeUpdate = leaveEscalationCriteriasRepository.findAll().size();
        leaveEscalationCriterias.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveEscalationCriteriasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveEscalationCriterias))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveEscalationCriterias in the database
        List<LeaveEscalationCriterias> leaveEscalationCriteriasList = leaveEscalationCriteriasRepository.findAll();
        assertThat(leaveEscalationCriteriasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeaveEscalationCriterias() throws Exception {
        int databaseSizeBeforeUpdate = leaveEscalationCriteriasRepository.findAll().size();
        leaveEscalationCriterias.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveEscalationCriteriasMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveEscalationCriterias))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveEscalationCriterias in the database
        List<LeaveEscalationCriterias> leaveEscalationCriteriasList = leaveEscalationCriteriasRepository.findAll();
        assertThat(leaveEscalationCriteriasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLeaveEscalationCriterias() throws Exception {
        // Initialize the database
        leaveEscalationCriteriasRepository.saveAndFlush(leaveEscalationCriterias);

        int databaseSizeBeforeDelete = leaveEscalationCriteriasRepository.findAll().size();

        // Delete the leaveEscalationCriterias
        restLeaveEscalationCriteriasMockMvc
            .perform(delete(ENTITY_API_URL_ID, leaveEscalationCriterias.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeaveEscalationCriterias> leaveEscalationCriteriasList = leaveEscalationCriteriasRepository.findAll();
        assertThat(leaveEscalationCriteriasList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
