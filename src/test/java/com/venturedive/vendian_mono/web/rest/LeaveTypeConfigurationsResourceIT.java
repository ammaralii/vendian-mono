package com.venturedive.vendian_mono.web.rest;

import static com.venturedive.vendian_mono.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.LeaveTypeConfigurations;
import com.venturedive.vendian_mono.domain.LeaveTypes;
import com.venturedive.vendian_mono.repository.LeaveTypeConfigurationsRepository;
import com.venturedive.vendian_mono.service.criteria.LeaveTypeConfigurationsCriteria;
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
 * Integration tests for the {@link LeaveTypeConfigurationsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeaveTypeConfigurationsResourceIT {

    private static final BigDecimal DEFAULT_NO_OF_LEAVES = new BigDecimal(1);
    private static final BigDecimal UPDATED_NO_OF_LEAVES = new BigDecimal(2);
    private static final BigDecimal SMALLER_NO_OF_LEAVES = new BigDecimal(1 - 1);

    private static final String DEFAULT_TENURE_CYCLE = "AAAAA";
    private static final String UPDATED_TENURE_CYCLE = "BBBBB";

    private static final Integer DEFAULT_TO = 1;
    private static final Integer UPDATED_TO = 2;
    private static final Integer SMALLER_TO = 1 - 1;

    private static final Integer DEFAULT_FROM = 1;
    private static final Integer UPDATED_FROM = 2;
    private static final Integer SMALLER_FROM = 1 - 1;

    private static final String DEFAULT_INCLUSIVITY = "AAAAAAAAAA";
    private static final String UPDATED_INCLUSIVITY = "BBBBBBBBBB";

    private static final Integer DEFAULT_MAX_QUOTA = 1;
    private static final Integer UPDATED_MAX_QUOTA = 2;
    private static final Integer SMALLER_MAX_QUOTA = 1 - 1;

    private static final Boolean DEFAULT_IS_ACCURED = false;
    private static final Boolean UPDATED_IS_ACCURED = true;

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

    private static final String ENTITY_API_URL = "/api/leave-type-configurations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LeaveTypeConfigurationsRepository leaveTypeConfigurationsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeaveTypeConfigurationsMockMvc;

    private LeaveTypeConfigurations leaveTypeConfigurations;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveTypeConfigurations createEntity(EntityManager em) {
        LeaveTypeConfigurations leaveTypeConfigurations = new LeaveTypeConfigurations()
            .noOfLeaves(DEFAULT_NO_OF_LEAVES)
            .tenureCycle(DEFAULT_TENURE_CYCLE)
            .to(DEFAULT_TO)
            .from(DEFAULT_FROM)
            .inclusivity(DEFAULT_INCLUSIVITY)
            .maxQuota(DEFAULT_MAX_QUOTA)
            .isAccured(DEFAULT_IS_ACCURED)
            .effDate(DEFAULT_EFF_DATE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .endDate(DEFAULT_END_DATE)
            .version(DEFAULT_VERSION);
        // Add required entity
        LeaveTypes leaveTypes;
        if (TestUtil.findAll(em, LeaveTypes.class).isEmpty()) {
            leaveTypes = LeaveTypesResourceIT.createEntity(em);
            em.persist(leaveTypes);
            em.flush();
        } else {
            leaveTypes = TestUtil.findAll(em, LeaveTypes.class).get(0);
        }
        leaveTypeConfigurations.setLeaveType(leaveTypes);
        return leaveTypeConfigurations;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveTypeConfigurations createUpdatedEntity(EntityManager em) {
        LeaveTypeConfigurations leaveTypeConfigurations = new LeaveTypeConfigurations()
            .noOfLeaves(UPDATED_NO_OF_LEAVES)
            .tenureCycle(UPDATED_TENURE_CYCLE)
            .to(UPDATED_TO)
            .from(UPDATED_FROM)
            .inclusivity(UPDATED_INCLUSIVITY)
            .maxQuota(UPDATED_MAX_QUOTA)
            .isAccured(UPDATED_IS_ACCURED)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);
        // Add required entity
        LeaveTypes leaveTypes;
        if (TestUtil.findAll(em, LeaveTypes.class).isEmpty()) {
            leaveTypes = LeaveTypesResourceIT.createUpdatedEntity(em);
            em.persist(leaveTypes);
            em.flush();
        } else {
            leaveTypes = TestUtil.findAll(em, LeaveTypes.class).get(0);
        }
        leaveTypeConfigurations.setLeaveType(leaveTypes);
        return leaveTypeConfigurations;
    }

    @BeforeEach
    public void initTest() {
        leaveTypeConfigurations = createEntity(em);
    }

    @Test
    @Transactional
    void createLeaveTypeConfigurations() throws Exception {
        int databaseSizeBeforeCreate = leaveTypeConfigurationsRepository.findAll().size();
        // Create the LeaveTypeConfigurations
        restLeaveTypeConfigurationsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeConfigurations))
            )
            .andExpect(status().isCreated());

        // Validate the LeaveTypeConfigurations in the database
        List<LeaveTypeConfigurations> leaveTypeConfigurationsList = leaveTypeConfigurationsRepository.findAll();
        assertThat(leaveTypeConfigurationsList).hasSize(databaseSizeBeforeCreate + 1);
        LeaveTypeConfigurations testLeaveTypeConfigurations = leaveTypeConfigurationsList.get(leaveTypeConfigurationsList.size() - 1);
        assertThat(testLeaveTypeConfigurations.getNoOfLeaves()).isEqualByComparingTo(DEFAULT_NO_OF_LEAVES);
        assertThat(testLeaveTypeConfigurations.getTenureCycle()).isEqualTo(DEFAULT_TENURE_CYCLE);
        assertThat(testLeaveTypeConfigurations.getTo()).isEqualTo(DEFAULT_TO);
        assertThat(testLeaveTypeConfigurations.getFrom()).isEqualTo(DEFAULT_FROM);
        assertThat(testLeaveTypeConfigurations.getInclusivity()).isEqualTo(DEFAULT_INCLUSIVITY);
        assertThat(testLeaveTypeConfigurations.getMaxQuota()).isEqualTo(DEFAULT_MAX_QUOTA);
        assertThat(testLeaveTypeConfigurations.getIsAccured()).isEqualTo(DEFAULT_IS_ACCURED);
        assertThat(testLeaveTypeConfigurations.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testLeaveTypeConfigurations.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testLeaveTypeConfigurations.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testLeaveTypeConfigurations.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testLeaveTypeConfigurations.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createLeaveTypeConfigurationsWithExistingId() throws Exception {
        // Create the LeaveTypeConfigurations with an existing ID
        leaveTypeConfigurations.setId(1L);

        int databaseSizeBeforeCreate = leaveTypeConfigurationsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeaveTypeConfigurationsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeConfigurations))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveTypeConfigurations in the database
        List<LeaveTypeConfigurations> leaveTypeConfigurationsList = leaveTypeConfigurationsRepository.findAll();
        assertThat(leaveTypeConfigurationsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNoOfLeavesIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveTypeConfigurationsRepository.findAll().size();
        // set the field null
        leaveTypeConfigurations.setNoOfLeaves(null);

        // Create the LeaveTypeConfigurations, which fails.

        restLeaveTypeConfigurationsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeConfigurations))
            )
            .andExpect(status().isBadRequest());

        List<LeaveTypeConfigurations> leaveTypeConfigurationsList = leaveTypeConfigurationsRepository.findAll();
        assertThat(leaveTypeConfigurationsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTenureCycleIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveTypeConfigurationsRepository.findAll().size();
        // set the field null
        leaveTypeConfigurations.setTenureCycle(null);

        // Create the LeaveTypeConfigurations, which fails.

        restLeaveTypeConfigurationsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeConfigurations))
            )
            .andExpect(status().isBadRequest());

        List<LeaveTypeConfigurations> leaveTypeConfigurationsList = leaveTypeConfigurationsRepository.findAll();
        assertThat(leaveTypeConfigurationsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEffDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveTypeConfigurationsRepository.findAll().size();
        // set the field null
        leaveTypeConfigurations.setEffDate(null);

        // Create the LeaveTypeConfigurations, which fails.

        restLeaveTypeConfigurationsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeConfigurations))
            )
            .andExpect(status().isBadRequest());

        List<LeaveTypeConfigurations> leaveTypeConfigurationsList = leaveTypeConfigurationsRepository.findAll();
        assertThat(leaveTypeConfigurationsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveTypeConfigurationsRepository.findAll().size();
        // set the field null
        leaveTypeConfigurations.setCreatedAt(null);

        // Create the LeaveTypeConfigurations, which fails.

        restLeaveTypeConfigurationsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeConfigurations))
            )
            .andExpect(status().isBadRequest());

        List<LeaveTypeConfigurations> leaveTypeConfigurationsList = leaveTypeConfigurationsRepository.findAll();
        assertThat(leaveTypeConfigurationsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveTypeConfigurationsRepository.findAll().size();
        // set the field null
        leaveTypeConfigurations.setUpdatedAt(null);

        // Create the LeaveTypeConfigurations, which fails.

        restLeaveTypeConfigurationsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeConfigurations))
            )
            .andExpect(status().isBadRequest());

        List<LeaveTypeConfigurations> leaveTypeConfigurationsList = leaveTypeConfigurationsRepository.findAll();
        assertThat(leaveTypeConfigurationsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveTypeConfigurationsRepository.findAll().size();
        // set the field null
        leaveTypeConfigurations.setVersion(null);

        // Create the LeaveTypeConfigurations, which fails.

        restLeaveTypeConfigurationsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeConfigurations))
            )
            .andExpect(status().isBadRequest());

        List<LeaveTypeConfigurations> leaveTypeConfigurationsList = leaveTypeConfigurationsRepository.findAll();
        assertThat(leaveTypeConfigurationsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurations() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList
        restLeaveTypeConfigurationsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveTypeConfigurations.getId().intValue())))
            .andExpect(jsonPath("$.[*].noOfLeaves").value(hasItem(sameNumber(DEFAULT_NO_OF_LEAVES))))
            .andExpect(jsonPath("$.[*].tenureCycle").value(hasItem(DEFAULT_TENURE_CYCLE)))
            .andExpect(jsonPath("$.[*].to").value(hasItem(DEFAULT_TO)))
            .andExpect(jsonPath("$.[*].from").value(hasItem(DEFAULT_FROM)))
            .andExpect(jsonPath("$.[*].inclusivity").value(hasItem(DEFAULT_INCLUSIVITY)))
            .andExpect(jsonPath("$.[*].maxQuota").value(hasItem(DEFAULT_MAX_QUOTA)))
            .andExpect(jsonPath("$.[*].isAccured").value(hasItem(DEFAULT_IS_ACCURED.booleanValue())))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getLeaveTypeConfigurations() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get the leaveTypeConfigurations
        restLeaveTypeConfigurationsMockMvc
            .perform(get(ENTITY_API_URL_ID, leaveTypeConfigurations.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leaveTypeConfigurations.getId().intValue()))
            .andExpect(jsonPath("$.noOfLeaves").value(sameNumber(DEFAULT_NO_OF_LEAVES)))
            .andExpect(jsonPath("$.tenureCycle").value(DEFAULT_TENURE_CYCLE))
            .andExpect(jsonPath("$.to").value(DEFAULT_TO))
            .andExpect(jsonPath("$.from").value(DEFAULT_FROM))
            .andExpect(jsonPath("$.inclusivity").value(DEFAULT_INCLUSIVITY))
            .andExpect(jsonPath("$.maxQuota").value(DEFAULT_MAX_QUOTA))
            .andExpect(jsonPath("$.isAccured").value(DEFAULT_IS_ACCURED.booleanValue()))
            .andExpect(jsonPath("$.effDate").value(DEFAULT_EFF_DATE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getLeaveTypeConfigurationsByIdFiltering() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        Long id = leaveTypeConfigurations.getId();

        defaultLeaveTypeConfigurationsShouldBeFound("id.equals=" + id);
        defaultLeaveTypeConfigurationsShouldNotBeFound("id.notEquals=" + id);

        defaultLeaveTypeConfigurationsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLeaveTypeConfigurationsShouldNotBeFound("id.greaterThan=" + id);

        defaultLeaveTypeConfigurationsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLeaveTypeConfigurationsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByNoOfLeavesIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where noOfLeaves equals to DEFAULT_NO_OF_LEAVES
        defaultLeaveTypeConfigurationsShouldBeFound("noOfLeaves.equals=" + DEFAULT_NO_OF_LEAVES);

        // Get all the leaveTypeConfigurationsList where noOfLeaves equals to UPDATED_NO_OF_LEAVES
        defaultLeaveTypeConfigurationsShouldNotBeFound("noOfLeaves.equals=" + UPDATED_NO_OF_LEAVES);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByNoOfLeavesIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where noOfLeaves in DEFAULT_NO_OF_LEAVES or UPDATED_NO_OF_LEAVES
        defaultLeaveTypeConfigurationsShouldBeFound("noOfLeaves.in=" + DEFAULT_NO_OF_LEAVES + "," + UPDATED_NO_OF_LEAVES);

        // Get all the leaveTypeConfigurationsList where noOfLeaves equals to UPDATED_NO_OF_LEAVES
        defaultLeaveTypeConfigurationsShouldNotBeFound("noOfLeaves.in=" + UPDATED_NO_OF_LEAVES);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByNoOfLeavesIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where noOfLeaves is not null
        defaultLeaveTypeConfigurationsShouldBeFound("noOfLeaves.specified=true");

        // Get all the leaveTypeConfigurationsList where noOfLeaves is null
        defaultLeaveTypeConfigurationsShouldNotBeFound("noOfLeaves.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByNoOfLeavesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where noOfLeaves is greater than or equal to DEFAULT_NO_OF_LEAVES
        defaultLeaveTypeConfigurationsShouldBeFound("noOfLeaves.greaterThanOrEqual=" + DEFAULT_NO_OF_LEAVES);

        // Get all the leaveTypeConfigurationsList where noOfLeaves is greater than or equal to UPDATED_NO_OF_LEAVES
        defaultLeaveTypeConfigurationsShouldNotBeFound("noOfLeaves.greaterThanOrEqual=" + UPDATED_NO_OF_LEAVES);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByNoOfLeavesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where noOfLeaves is less than or equal to DEFAULT_NO_OF_LEAVES
        defaultLeaveTypeConfigurationsShouldBeFound("noOfLeaves.lessThanOrEqual=" + DEFAULT_NO_OF_LEAVES);

        // Get all the leaveTypeConfigurationsList where noOfLeaves is less than or equal to SMALLER_NO_OF_LEAVES
        defaultLeaveTypeConfigurationsShouldNotBeFound("noOfLeaves.lessThanOrEqual=" + SMALLER_NO_OF_LEAVES);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByNoOfLeavesIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where noOfLeaves is less than DEFAULT_NO_OF_LEAVES
        defaultLeaveTypeConfigurationsShouldNotBeFound("noOfLeaves.lessThan=" + DEFAULT_NO_OF_LEAVES);

        // Get all the leaveTypeConfigurationsList where noOfLeaves is less than UPDATED_NO_OF_LEAVES
        defaultLeaveTypeConfigurationsShouldBeFound("noOfLeaves.lessThan=" + UPDATED_NO_OF_LEAVES);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByNoOfLeavesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where noOfLeaves is greater than DEFAULT_NO_OF_LEAVES
        defaultLeaveTypeConfigurationsShouldNotBeFound("noOfLeaves.greaterThan=" + DEFAULT_NO_OF_LEAVES);

        // Get all the leaveTypeConfigurationsList where noOfLeaves is greater than SMALLER_NO_OF_LEAVES
        defaultLeaveTypeConfigurationsShouldBeFound("noOfLeaves.greaterThan=" + SMALLER_NO_OF_LEAVES);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByTenureCycleIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where tenureCycle equals to DEFAULT_TENURE_CYCLE
        defaultLeaveTypeConfigurationsShouldBeFound("tenureCycle.equals=" + DEFAULT_TENURE_CYCLE);

        // Get all the leaveTypeConfigurationsList where tenureCycle equals to UPDATED_TENURE_CYCLE
        defaultLeaveTypeConfigurationsShouldNotBeFound("tenureCycle.equals=" + UPDATED_TENURE_CYCLE);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByTenureCycleIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where tenureCycle in DEFAULT_TENURE_CYCLE or UPDATED_TENURE_CYCLE
        defaultLeaveTypeConfigurationsShouldBeFound("tenureCycle.in=" + DEFAULT_TENURE_CYCLE + "," + UPDATED_TENURE_CYCLE);

        // Get all the leaveTypeConfigurationsList where tenureCycle equals to UPDATED_TENURE_CYCLE
        defaultLeaveTypeConfigurationsShouldNotBeFound("tenureCycle.in=" + UPDATED_TENURE_CYCLE);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByTenureCycleIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where tenureCycle is not null
        defaultLeaveTypeConfigurationsShouldBeFound("tenureCycle.specified=true");

        // Get all the leaveTypeConfigurationsList where tenureCycle is null
        defaultLeaveTypeConfigurationsShouldNotBeFound("tenureCycle.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByTenureCycleContainsSomething() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where tenureCycle contains DEFAULT_TENURE_CYCLE
        defaultLeaveTypeConfigurationsShouldBeFound("tenureCycle.contains=" + DEFAULT_TENURE_CYCLE);

        // Get all the leaveTypeConfigurationsList where tenureCycle contains UPDATED_TENURE_CYCLE
        defaultLeaveTypeConfigurationsShouldNotBeFound("tenureCycle.contains=" + UPDATED_TENURE_CYCLE);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByTenureCycleNotContainsSomething() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where tenureCycle does not contain DEFAULT_TENURE_CYCLE
        defaultLeaveTypeConfigurationsShouldNotBeFound("tenureCycle.doesNotContain=" + DEFAULT_TENURE_CYCLE);

        // Get all the leaveTypeConfigurationsList where tenureCycle does not contain UPDATED_TENURE_CYCLE
        defaultLeaveTypeConfigurationsShouldBeFound("tenureCycle.doesNotContain=" + UPDATED_TENURE_CYCLE);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByToIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where to equals to DEFAULT_TO
        defaultLeaveTypeConfigurationsShouldBeFound("to.equals=" + DEFAULT_TO);

        // Get all the leaveTypeConfigurationsList where to equals to UPDATED_TO
        defaultLeaveTypeConfigurationsShouldNotBeFound("to.equals=" + UPDATED_TO);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByToIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where to in DEFAULT_TO or UPDATED_TO
        defaultLeaveTypeConfigurationsShouldBeFound("to.in=" + DEFAULT_TO + "," + UPDATED_TO);

        // Get all the leaveTypeConfigurationsList where to equals to UPDATED_TO
        defaultLeaveTypeConfigurationsShouldNotBeFound("to.in=" + UPDATED_TO);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByToIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where to is not null
        defaultLeaveTypeConfigurationsShouldBeFound("to.specified=true");

        // Get all the leaveTypeConfigurationsList where to is null
        defaultLeaveTypeConfigurationsShouldNotBeFound("to.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByToIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where to is greater than or equal to DEFAULT_TO
        defaultLeaveTypeConfigurationsShouldBeFound("to.greaterThanOrEqual=" + DEFAULT_TO);

        // Get all the leaveTypeConfigurationsList where to is greater than or equal to UPDATED_TO
        defaultLeaveTypeConfigurationsShouldNotBeFound("to.greaterThanOrEqual=" + UPDATED_TO);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByToIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where to is less than or equal to DEFAULT_TO
        defaultLeaveTypeConfigurationsShouldBeFound("to.lessThanOrEqual=" + DEFAULT_TO);

        // Get all the leaveTypeConfigurationsList where to is less than or equal to SMALLER_TO
        defaultLeaveTypeConfigurationsShouldNotBeFound("to.lessThanOrEqual=" + SMALLER_TO);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByToIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where to is less than DEFAULT_TO
        defaultLeaveTypeConfigurationsShouldNotBeFound("to.lessThan=" + DEFAULT_TO);

        // Get all the leaveTypeConfigurationsList where to is less than UPDATED_TO
        defaultLeaveTypeConfigurationsShouldBeFound("to.lessThan=" + UPDATED_TO);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByToIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where to is greater than DEFAULT_TO
        defaultLeaveTypeConfigurationsShouldNotBeFound("to.greaterThan=" + DEFAULT_TO);

        // Get all the leaveTypeConfigurationsList where to is greater than SMALLER_TO
        defaultLeaveTypeConfigurationsShouldBeFound("to.greaterThan=" + SMALLER_TO);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByFromIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where from equals to DEFAULT_FROM
        defaultLeaveTypeConfigurationsShouldBeFound("from.equals=" + DEFAULT_FROM);

        // Get all the leaveTypeConfigurationsList where from equals to UPDATED_FROM
        defaultLeaveTypeConfigurationsShouldNotBeFound("from.equals=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByFromIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where from in DEFAULT_FROM or UPDATED_FROM
        defaultLeaveTypeConfigurationsShouldBeFound("from.in=" + DEFAULT_FROM + "," + UPDATED_FROM);

        // Get all the leaveTypeConfigurationsList where from equals to UPDATED_FROM
        defaultLeaveTypeConfigurationsShouldNotBeFound("from.in=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where from is not null
        defaultLeaveTypeConfigurationsShouldBeFound("from.specified=true");

        // Get all the leaveTypeConfigurationsList where from is null
        defaultLeaveTypeConfigurationsShouldNotBeFound("from.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByFromIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where from is greater than or equal to DEFAULT_FROM
        defaultLeaveTypeConfigurationsShouldBeFound("from.greaterThanOrEqual=" + DEFAULT_FROM);

        // Get all the leaveTypeConfigurationsList where from is greater than or equal to UPDATED_FROM
        defaultLeaveTypeConfigurationsShouldNotBeFound("from.greaterThanOrEqual=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByFromIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where from is less than or equal to DEFAULT_FROM
        defaultLeaveTypeConfigurationsShouldBeFound("from.lessThanOrEqual=" + DEFAULT_FROM);

        // Get all the leaveTypeConfigurationsList where from is less than or equal to SMALLER_FROM
        defaultLeaveTypeConfigurationsShouldNotBeFound("from.lessThanOrEqual=" + SMALLER_FROM);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByFromIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where from is less than DEFAULT_FROM
        defaultLeaveTypeConfigurationsShouldNotBeFound("from.lessThan=" + DEFAULT_FROM);

        // Get all the leaveTypeConfigurationsList where from is less than UPDATED_FROM
        defaultLeaveTypeConfigurationsShouldBeFound("from.lessThan=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByFromIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where from is greater than DEFAULT_FROM
        defaultLeaveTypeConfigurationsShouldNotBeFound("from.greaterThan=" + DEFAULT_FROM);

        // Get all the leaveTypeConfigurationsList where from is greater than SMALLER_FROM
        defaultLeaveTypeConfigurationsShouldBeFound("from.greaterThan=" + SMALLER_FROM);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByInclusivityIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where inclusivity equals to DEFAULT_INCLUSIVITY
        defaultLeaveTypeConfigurationsShouldBeFound("inclusivity.equals=" + DEFAULT_INCLUSIVITY);

        // Get all the leaveTypeConfigurationsList where inclusivity equals to UPDATED_INCLUSIVITY
        defaultLeaveTypeConfigurationsShouldNotBeFound("inclusivity.equals=" + UPDATED_INCLUSIVITY);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByInclusivityIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where inclusivity in DEFAULT_INCLUSIVITY or UPDATED_INCLUSIVITY
        defaultLeaveTypeConfigurationsShouldBeFound("inclusivity.in=" + DEFAULT_INCLUSIVITY + "," + UPDATED_INCLUSIVITY);

        // Get all the leaveTypeConfigurationsList where inclusivity equals to UPDATED_INCLUSIVITY
        defaultLeaveTypeConfigurationsShouldNotBeFound("inclusivity.in=" + UPDATED_INCLUSIVITY);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByInclusivityIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where inclusivity is not null
        defaultLeaveTypeConfigurationsShouldBeFound("inclusivity.specified=true");

        // Get all the leaveTypeConfigurationsList where inclusivity is null
        defaultLeaveTypeConfigurationsShouldNotBeFound("inclusivity.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByInclusivityContainsSomething() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where inclusivity contains DEFAULT_INCLUSIVITY
        defaultLeaveTypeConfigurationsShouldBeFound("inclusivity.contains=" + DEFAULT_INCLUSIVITY);

        // Get all the leaveTypeConfigurationsList where inclusivity contains UPDATED_INCLUSIVITY
        defaultLeaveTypeConfigurationsShouldNotBeFound("inclusivity.contains=" + UPDATED_INCLUSIVITY);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByInclusivityNotContainsSomething() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where inclusivity does not contain DEFAULT_INCLUSIVITY
        defaultLeaveTypeConfigurationsShouldNotBeFound("inclusivity.doesNotContain=" + DEFAULT_INCLUSIVITY);

        // Get all the leaveTypeConfigurationsList where inclusivity does not contain UPDATED_INCLUSIVITY
        defaultLeaveTypeConfigurationsShouldBeFound("inclusivity.doesNotContain=" + UPDATED_INCLUSIVITY);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByMaxQuotaIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where maxQuota equals to DEFAULT_MAX_QUOTA
        defaultLeaveTypeConfigurationsShouldBeFound("maxQuota.equals=" + DEFAULT_MAX_QUOTA);

        // Get all the leaveTypeConfigurationsList where maxQuota equals to UPDATED_MAX_QUOTA
        defaultLeaveTypeConfigurationsShouldNotBeFound("maxQuota.equals=" + UPDATED_MAX_QUOTA);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByMaxQuotaIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where maxQuota in DEFAULT_MAX_QUOTA or UPDATED_MAX_QUOTA
        defaultLeaveTypeConfigurationsShouldBeFound("maxQuota.in=" + DEFAULT_MAX_QUOTA + "," + UPDATED_MAX_QUOTA);

        // Get all the leaveTypeConfigurationsList where maxQuota equals to UPDATED_MAX_QUOTA
        defaultLeaveTypeConfigurationsShouldNotBeFound("maxQuota.in=" + UPDATED_MAX_QUOTA);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByMaxQuotaIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where maxQuota is not null
        defaultLeaveTypeConfigurationsShouldBeFound("maxQuota.specified=true");

        // Get all the leaveTypeConfigurationsList where maxQuota is null
        defaultLeaveTypeConfigurationsShouldNotBeFound("maxQuota.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByMaxQuotaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where maxQuota is greater than or equal to DEFAULT_MAX_QUOTA
        defaultLeaveTypeConfigurationsShouldBeFound("maxQuota.greaterThanOrEqual=" + DEFAULT_MAX_QUOTA);

        // Get all the leaveTypeConfigurationsList where maxQuota is greater than or equal to UPDATED_MAX_QUOTA
        defaultLeaveTypeConfigurationsShouldNotBeFound("maxQuota.greaterThanOrEqual=" + UPDATED_MAX_QUOTA);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByMaxQuotaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where maxQuota is less than or equal to DEFAULT_MAX_QUOTA
        defaultLeaveTypeConfigurationsShouldBeFound("maxQuota.lessThanOrEqual=" + DEFAULT_MAX_QUOTA);

        // Get all the leaveTypeConfigurationsList where maxQuota is less than or equal to SMALLER_MAX_QUOTA
        defaultLeaveTypeConfigurationsShouldNotBeFound("maxQuota.lessThanOrEqual=" + SMALLER_MAX_QUOTA);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByMaxQuotaIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where maxQuota is less than DEFAULT_MAX_QUOTA
        defaultLeaveTypeConfigurationsShouldNotBeFound("maxQuota.lessThan=" + DEFAULT_MAX_QUOTA);

        // Get all the leaveTypeConfigurationsList where maxQuota is less than UPDATED_MAX_QUOTA
        defaultLeaveTypeConfigurationsShouldBeFound("maxQuota.lessThan=" + UPDATED_MAX_QUOTA);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByMaxQuotaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where maxQuota is greater than DEFAULT_MAX_QUOTA
        defaultLeaveTypeConfigurationsShouldNotBeFound("maxQuota.greaterThan=" + DEFAULT_MAX_QUOTA);

        // Get all the leaveTypeConfigurationsList where maxQuota is greater than SMALLER_MAX_QUOTA
        defaultLeaveTypeConfigurationsShouldBeFound("maxQuota.greaterThan=" + SMALLER_MAX_QUOTA);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByIsAccuredIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where isAccured equals to DEFAULT_IS_ACCURED
        defaultLeaveTypeConfigurationsShouldBeFound("isAccured.equals=" + DEFAULT_IS_ACCURED);

        // Get all the leaveTypeConfigurationsList where isAccured equals to UPDATED_IS_ACCURED
        defaultLeaveTypeConfigurationsShouldNotBeFound("isAccured.equals=" + UPDATED_IS_ACCURED);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByIsAccuredIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where isAccured in DEFAULT_IS_ACCURED or UPDATED_IS_ACCURED
        defaultLeaveTypeConfigurationsShouldBeFound("isAccured.in=" + DEFAULT_IS_ACCURED + "," + UPDATED_IS_ACCURED);

        // Get all the leaveTypeConfigurationsList where isAccured equals to UPDATED_IS_ACCURED
        defaultLeaveTypeConfigurationsShouldNotBeFound("isAccured.in=" + UPDATED_IS_ACCURED);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByIsAccuredIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where isAccured is not null
        defaultLeaveTypeConfigurationsShouldBeFound("isAccured.specified=true");

        // Get all the leaveTypeConfigurationsList where isAccured is null
        defaultLeaveTypeConfigurationsShouldNotBeFound("isAccured.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByEffDateIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where effDate equals to DEFAULT_EFF_DATE
        defaultLeaveTypeConfigurationsShouldBeFound("effDate.equals=" + DEFAULT_EFF_DATE);

        // Get all the leaveTypeConfigurationsList where effDate equals to UPDATED_EFF_DATE
        defaultLeaveTypeConfigurationsShouldNotBeFound("effDate.equals=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByEffDateIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where effDate in DEFAULT_EFF_DATE or UPDATED_EFF_DATE
        defaultLeaveTypeConfigurationsShouldBeFound("effDate.in=" + DEFAULT_EFF_DATE + "," + UPDATED_EFF_DATE);

        // Get all the leaveTypeConfigurationsList where effDate equals to UPDATED_EFF_DATE
        defaultLeaveTypeConfigurationsShouldNotBeFound("effDate.in=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByEffDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where effDate is not null
        defaultLeaveTypeConfigurationsShouldBeFound("effDate.specified=true");

        // Get all the leaveTypeConfigurationsList where effDate is null
        defaultLeaveTypeConfigurationsShouldNotBeFound("effDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where createdAt equals to DEFAULT_CREATED_AT
        defaultLeaveTypeConfigurationsShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the leaveTypeConfigurationsList where createdAt equals to UPDATED_CREATED_AT
        defaultLeaveTypeConfigurationsShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultLeaveTypeConfigurationsShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the leaveTypeConfigurationsList where createdAt equals to UPDATED_CREATED_AT
        defaultLeaveTypeConfigurationsShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where createdAt is not null
        defaultLeaveTypeConfigurationsShouldBeFound("createdAt.specified=true");

        // Get all the leaveTypeConfigurationsList where createdAt is null
        defaultLeaveTypeConfigurationsShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultLeaveTypeConfigurationsShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the leaveTypeConfigurationsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultLeaveTypeConfigurationsShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultLeaveTypeConfigurationsShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the leaveTypeConfigurationsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultLeaveTypeConfigurationsShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where updatedAt is not null
        defaultLeaveTypeConfigurationsShouldBeFound("updatedAt.specified=true");

        // Get all the leaveTypeConfigurationsList where updatedAt is null
        defaultLeaveTypeConfigurationsShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where endDate equals to DEFAULT_END_DATE
        defaultLeaveTypeConfigurationsShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the leaveTypeConfigurationsList where endDate equals to UPDATED_END_DATE
        defaultLeaveTypeConfigurationsShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultLeaveTypeConfigurationsShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the leaveTypeConfigurationsList where endDate equals to UPDATED_END_DATE
        defaultLeaveTypeConfigurationsShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where endDate is not null
        defaultLeaveTypeConfigurationsShouldBeFound("endDate.specified=true");

        // Get all the leaveTypeConfigurationsList where endDate is null
        defaultLeaveTypeConfigurationsShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where version equals to DEFAULT_VERSION
        defaultLeaveTypeConfigurationsShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the leaveTypeConfigurationsList where version equals to UPDATED_VERSION
        defaultLeaveTypeConfigurationsShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultLeaveTypeConfigurationsShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the leaveTypeConfigurationsList where version equals to UPDATED_VERSION
        defaultLeaveTypeConfigurationsShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where version is not null
        defaultLeaveTypeConfigurationsShouldBeFound("version.specified=true");

        // Get all the leaveTypeConfigurationsList where version is null
        defaultLeaveTypeConfigurationsShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where version is greater than or equal to DEFAULT_VERSION
        defaultLeaveTypeConfigurationsShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the leaveTypeConfigurationsList where version is greater than or equal to UPDATED_VERSION
        defaultLeaveTypeConfigurationsShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where version is less than or equal to DEFAULT_VERSION
        defaultLeaveTypeConfigurationsShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the leaveTypeConfigurationsList where version is less than or equal to SMALLER_VERSION
        defaultLeaveTypeConfigurationsShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where version is less than DEFAULT_VERSION
        defaultLeaveTypeConfigurationsShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the leaveTypeConfigurationsList where version is less than UPDATED_VERSION
        defaultLeaveTypeConfigurationsShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        // Get all the leaveTypeConfigurationsList where version is greater than DEFAULT_VERSION
        defaultLeaveTypeConfigurationsShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the leaveTypeConfigurationsList where version is greater than SMALLER_VERSION
        defaultLeaveTypeConfigurationsShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveTypeConfigurationsByLeaveTypeIsEqualToSomething() throws Exception {
        LeaveTypes leaveType;
        if (TestUtil.findAll(em, LeaveTypes.class).isEmpty()) {
            leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);
            leaveType = LeaveTypesResourceIT.createEntity(em);
        } else {
            leaveType = TestUtil.findAll(em, LeaveTypes.class).get(0);
        }
        em.persist(leaveType);
        em.flush();
        leaveTypeConfigurations.setLeaveType(leaveType);
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);
        Long leaveTypeId = leaveType.getId();

        // Get all the leaveTypeConfigurationsList where leaveType equals to leaveTypeId
        defaultLeaveTypeConfigurationsShouldBeFound("leaveTypeId.equals=" + leaveTypeId);

        // Get all the leaveTypeConfigurationsList where leaveType equals to (leaveTypeId + 1)
        defaultLeaveTypeConfigurationsShouldNotBeFound("leaveTypeId.equals=" + (leaveTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLeaveTypeConfigurationsShouldBeFound(String filter) throws Exception {
        restLeaveTypeConfigurationsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveTypeConfigurations.getId().intValue())))
            .andExpect(jsonPath("$.[*].noOfLeaves").value(hasItem(sameNumber(DEFAULT_NO_OF_LEAVES))))
            .andExpect(jsonPath("$.[*].tenureCycle").value(hasItem(DEFAULT_TENURE_CYCLE)))
            .andExpect(jsonPath("$.[*].to").value(hasItem(DEFAULT_TO)))
            .andExpect(jsonPath("$.[*].from").value(hasItem(DEFAULT_FROM)))
            .andExpect(jsonPath("$.[*].inclusivity").value(hasItem(DEFAULT_INCLUSIVITY)))
            .andExpect(jsonPath("$.[*].maxQuota").value(hasItem(DEFAULT_MAX_QUOTA)))
            .andExpect(jsonPath("$.[*].isAccured").value(hasItem(DEFAULT_IS_ACCURED.booleanValue())))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restLeaveTypeConfigurationsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLeaveTypeConfigurationsShouldNotBeFound(String filter) throws Exception {
        restLeaveTypeConfigurationsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLeaveTypeConfigurationsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLeaveTypeConfigurations() throws Exception {
        // Get the leaveTypeConfigurations
        restLeaveTypeConfigurationsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLeaveTypeConfigurations() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        int databaseSizeBeforeUpdate = leaveTypeConfigurationsRepository.findAll().size();

        // Update the leaveTypeConfigurations
        LeaveTypeConfigurations updatedLeaveTypeConfigurations = leaveTypeConfigurationsRepository
            .findById(leaveTypeConfigurations.getId())
            .get();
        // Disconnect from session so that the updates on updatedLeaveTypeConfigurations are not directly saved in db
        em.detach(updatedLeaveTypeConfigurations);
        updatedLeaveTypeConfigurations
            .noOfLeaves(UPDATED_NO_OF_LEAVES)
            .tenureCycle(UPDATED_TENURE_CYCLE)
            .to(UPDATED_TO)
            .from(UPDATED_FROM)
            .inclusivity(UPDATED_INCLUSIVITY)
            .maxQuota(UPDATED_MAX_QUOTA)
            .isAccured(UPDATED_IS_ACCURED)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restLeaveTypeConfigurationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLeaveTypeConfigurations.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLeaveTypeConfigurations))
            )
            .andExpect(status().isOk());

        // Validate the LeaveTypeConfigurations in the database
        List<LeaveTypeConfigurations> leaveTypeConfigurationsList = leaveTypeConfigurationsRepository.findAll();
        assertThat(leaveTypeConfigurationsList).hasSize(databaseSizeBeforeUpdate);
        LeaveTypeConfigurations testLeaveTypeConfigurations = leaveTypeConfigurationsList.get(leaveTypeConfigurationsList.size() - 1);
        assertThat(testLeaveTypeConfigurations.getNoOfLeaves()).isEqualByComparingTo(UPDATED_NO_OF_LEAVES);
        assertThat(testLeaveTypeConfigurations.getTenureCycle()).isEqualTo(UPDATED_TENURE_CYCLE);
        assertThat(testLeaveTypeConfigurations.getTo()).isEqualTo(UPDATED_TO);
        assertThat(testLeaveTypeConfigurations.getFrom()).isEqualTo(UPDATED_FROM);
        assertThat(testLeaveTypeConfigurations.getInclusivity()).isEqualTo(UPDATED_INCLUSIVITY);
        assertThat(testLeaveTypeConfigurations.getMaxQuota()).isEqualTo(UPDATED_MAX_QUOTA);
        assertThat(testLeaveTypeConfigurations.getIsAccured()).isEqualTo(UPDATED_IS_ACCURED);
        assertThat(testLeaveTypeConfigurations.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testLeaveTypeConfigurations.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveTypeConfigurations.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaveTypeConfigurations.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testLeaveTypeConfigurations.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingLeaveTypeConfigurations() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypeConfigurationsRepository.findAll().size();
        leaveTypeConfigurations.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveTypeConfigurationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leaveTypeConfigurations.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeConfigurations))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveTypeConfigurations in the database
        List<LeaveTypeConfigurations> leaveTypeConfigurationsList = leaveTypeConfigurationsRepository.findAll();
        assertThat(leaveTypeConfigurationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeaveTypeConfigurations() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypeConfigurationsRepository.findAll().size();
        leaveTypeConfigurations.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveTypeConfigurationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeConfigurations))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveTypeConfigurations in the database
        List<LeaveTypeConfigurations> leaveTypeConfigurationsList = leaveTypeConfigurationsRepository.findAll();
        assertThat(leaveTypeConfigurationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeaveTypeConfigurations() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypeConfigurationsRepository.findAll().size();
        leaveTypeConfigurations.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveTypeConfigurationsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeConfigurations))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveTypeConfigurations in the database
        List<LeaveTypeConfigurations> leaveTypeConfigurationsList = leaveTypeConfigurationsRepository.findAll();
        assertThat(leaveTypeConfigurationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeaveTypeConfigurationsWithPatch() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        int databaseSizeBeforeUpdate = leaveTypeConfigurationsRepository.findAll().size();

        // Update the leaveTypeConfigurations using partial update
        LeaveTypeConfigurations partialUpdatedLeaveTypeConfigurations = new LeaveTypeConfigurations();
        partialUpdatedLeaveTypeConfigurations.setId(leaveTypeConfigurations.getId());

        partialUpdatedLeaveTypeConfigurations
            .noOfLeaves(UPDATED_NO_OF_LEAVES)
            .inclusivity(UPDATED_INCLUSIVITY)
            .isAccured(UPDATED_IS_ACCURED);

        restLeaveTypeConfigurationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveTypeConfigurations.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveTypeConfigurations))
            )
            .andExpect(status().isOk());

        // Validate the LeaveTypeConfigurations in the database
        List<LeaveTypeConfigurations> leaveTypeConfigurationsList = leaveTypeConfigurationsRepository.findAll();
        assertThat(leaveTypeConfigurationsList).hasSize(databaseSizeBeforeUpdate);
        LeaveTypeConfigurations testLeaveTypeConfigurations = leaveTypeConfigurationsList.get(leaveTypeConfigurationsList.size() - 1);
        assertThat(testLeaveTypeConfigurations.getNoOfLeaves()).isEqualByComparingTo(UPDATED_NO_OF_LEAVES);
        assertThat(testLeaveTypeConfigurations.getTenureCycle()).isEqualTo(DEFAULT_TENURE_CYCLE);
        assertThat(testLeaveTypeConfigurations.getTo()).isEqualTo(DEFAULT_TO);
        assertThat(testLeaveTypeConfigurations.getFrom()).isEqualTo(DEFAULT_FROM);
        assertThat(testLeaveTypeConfigurations.getInclusivity()).isEqualTo(UPDATED_INCLUSIVITY);
        assertThat(testLeaveTypeConfigurations.getMaxQuota()).isEqualTo(DEFAULT_MAX_QUOTA);
        assertThat(testLeaveTypeConfigurations.getIsAccured()).isEqualTo(UPDATED_IS_ACCURED);
        assertThat(testLeaveTypeConfigurations.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testLeaveTypeConfigurations.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testLeaveTypeConfigurations.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testLeaveTypeConfigurations.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testLeaveTypeConfigurations.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateLeaveTypeConfigurationsWithPatch() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        int databaseSizeBeforeUpdate = leaveTypeConfigurationsRepository.findAll().size();

        // Update the leaveTypeConfigurations using partial update
        LeaveTypeConfigurations partialUpdatedLeaveTypeConfigurations = new LeaveTypeConfigurations();
        partialUpdatedLeaveTypeConfigurations.setId(leaveTypeConfigurations.getId());

        partialUpdatedLeaveTypeConfigurations
            .noOfLeaves(UPDATED_NO_OF_LEAVES)
            .tenureCycle(UPDATED_TENURE_CYCLE)
            .to(UPDATED_TO)
            .from(UPDATED_FROM)
            .inclusivity(UPDATED_INCLUSIVITY)
            .maxQuota(UPDATED_MAX_QUOTA)
            .isAccured(UPDATED_IS_ACCURED)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restLeaveTypeConfigurationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveTypeConfigurations.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveTypeConfigurations))
            )
            .andExpect(status().isOk());

        // Validate the LeaveTypeConfigurations in the database
        List<LeaveTypeConfigurations> leaveTypeConfigurationsList = leaveTypeConfigurationsRepository.findAll();
        assertThat(leaveTypeConfigurationsList).hasSize(databaseSizeBeforeUpdate);
        LeaveTypeConfigurations testLeaveTypeConfigurations = leaveTypeConfigurationsList.get(leaveTypeConfigurationsList.size() - 1);
        assertThat(testLeaveTypeConfigurations.getNoOfLeaves()).isEqualByComparingTo(UPDATED_NO_OF_LEAVES);
        assertThat(testLeaveTypeConfigurations.getTenureCycle()).isEqualTo(UPDATED_TENURE_CYCLE);
        assertThat(testLeaveTypeConfigurations.getTo()).isEqualTo(UPDATED_TO);
        assertThat(testLeaveTypeConfigurations.getFrom()).isEqualTo(UPDATED_FROM);
        assertThat(testLeaveTypeConfigurations.getInclusivity()).isEqualTo(UPDATED_INCLUSIVITY);
        assertThat(testLeaveTypeConfigurations.getMaxQuota()).isEqualTo(UPDATED_MAX_QUOTA);
        assertThat(testLeaveTypeConfigurations.getIsAccured()).isEqualTo(UPDATED_IS_ACCURED);
        assertThat(testLeaveTypeConfigurations.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testLeaveTypeConfigurations.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveTypeConfigurations.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaveTypeConfigurations.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testLeaveTypeConfigurations.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingLeaveTypeConfigurations() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypeConfigurationsRepository.findAll().size();
        leaveTypeConfigurations.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveTypeConfigurationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leaveTypeConfigurations.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeConfigurations))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveTypeConfigurations in the database
        List<LeaveTypeConfigurations> leaveTypeConfigurationsList = leaveTypeConfigurationsRepository.findAll();
        assertThat(leaveTypeConfigurationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeaveTypeConfigurations() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypeConfigurationsRepository.findAll().size();
        leaveTypeConfigurations.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveTypeConfigurationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeConfigurations))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveTypeConfigurations in the database
        List<LeaveTypeConfigurations> leaveTypeConfigurationsList = leaveTypeConfigurationsRepository.findAll();
        assertThat(leaveTypeConfigurationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeaveTypeConfigurations() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypeConfigurationsRepository.findAll().size();
        leaveTypeConfigurations.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveTypeConfigurationsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeConfigurations))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveTypeConfigurations in the database
        List<LeaveTypeConfigurations> leaveTypeConfigurationsList = leaveTypeConfigurationsRepository.findAll();
        assertThat(leaveTypeConfigurationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLeaveTypeConfigurations() throws Exception {
        // Initialize the database
        leaveTypeConfigurationsRepository.saveAndFlush(leaveTypeConfigurations);

        int databaseSizeBeforeDelete = leaveTypeConfigurationsRepository.findAll().size();

        // Delete the leaveTypeConfigurations
        restLeaveTypeConfigurationsMockMvc
            .perform(delete(ENTITY_API_URL_ID, leaveTypeConfigurations.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeaveTypeConfigurations> leaveTypeConfigurationsList = leaveTypeConfigurationsRepository.findAll();
        assertThat(leaveTypeConfigurationsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
