package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.LeaveTypeRestrictions;
import com.venturedive.vendian_mono.domain.LeaveTypes;
import com.venturedive.vendian_mono.repository.LeaveTypeRestrictionsRepository;
import com.venturedive.vendian_mono.service.criteria.LeaveTypeRestrictionsCriteria;
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
 * Integration tests for the {@link LeaveTypeRestrictionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeaveTypeRestrictionsResourceIT {

    private static final String DEFAULT_RESTRICTION_JSON = "AAAAAAAAAA";
    private static final String UPDATED_RESTRICTION_JSON = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/leave-type-restrictions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LeaveTypeRestrictionsRepository leaveTypeRestrictionsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeaveTypeRestrictionsMockMvc;

    private LeaveTypeRestrictions leaveTypeRestrictions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveTypeRestrictions createEntity(EntityManager em) {
        LeaveTypeRestrictions leaveTypeRestrictions = new LeaveTypeRestrictions()
            .restrictionJson(DEFAULT_RESTRICTION_JSON)
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
        leaveTypeRestrictions.setLeaveType(leaveTypes);
        return leaveTypeRestrictions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveTypeRestrictions createUpdatedEntity(EntityManager em) {
        LeaveTypeRestrictions leaveTypeRestrictions = new LeaveTypeRestrictions()
            .restrictionJson(UPDATED_RESTRICTION_JSON)
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
        leaveTypeRestrictions.setLeaveType(leaveTypes);
        return leaveTypeRestrictions;
    }

    @BeforeEach
    public void initTest() {
        leaveTypeRestrictions = createEntity(em);
    }

    @Test
    @Transactional
    void createLeaveTypeRestrictions() throws Exception {
        int databaseSizeBeforeCreate = leaveTypeRestrictionsRepository.findAll().size();
        // Create the LeaveTypeRestrictions
        restLeaveTypeRestrictionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeRestrictions))
            )
            .andExpect(status().isCreated());

        // Validate the LeaveTypeRestrictions in the database
        List<LeaveTypeRestrictions> leaveTypeRestrictionsList = leaveTypeRestrictionsRepository.findAll();
        assertThat(leaveTypeRestrictionsList).hasSize(databaseSizeBeforeCreate + 1);
        LeaveTypeRestrictions testLeaveTypeRestrictions = leaveTypeRestrictionsList.get(leaveTypeRestrictionsList.size() - 1);
        assertThat(testLeaveTypeRestrictions.getRestrictionJson()).isEqualTo(DEFAULT_RESTRICTION_JSON);
        assertThat(testLeaveTypeRestrictions.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testLeaveTypeRestrictions.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testLeaveTypeRestrictions.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testLeaveTypeRestrictions.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testLeaveTypeRestrictions.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createLeaveTypeRestrictionsWithExistingId() throws Exception {
        // Create the LeaveTypeRestrictions with an existing ID
        leaveTypeRestrictions.setId(1L);

        int databaseSizeBeforeCreate = leaveTypeRestrictionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeaveTypeRestrictionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeRestrictions))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveTypeRestrictions in the database
        List<LeaveTypeRestrictions> leaveTypeRestrictionsList = leaveTypeRestrictionsRepository.findAll();
        assertThat(leaveTypeRestrictionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRestrictionJsonIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveTypeRestrictionsRepository.findAll().size();
        // set the field null
        leaveTypeRestrictions.setRestrictionJson(null);

        // Create the LeaveTypeRestrictions, which fails.

        restLeaveTypeRestrictionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeRestrictions))
            )
            .andExpect(status().isBadRequest());

        List<LeaveTypeRestrictions> leaveTypeRestrictionsList = leaveTypeRestrictionsRepository.findAll();
        assertThat(leaveTypeRestrictionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEffDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveTypeRestrictionsRepository.findAll().size();
        // set the field null
        leaveTypeRestrictions.setEffDate(null);

        // Create the LeaveTypeRestrictions, which fails.

        restLeaveTypeRestrictionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeRestrictions))
            )
            .andExpect(status().isBadRequest());

        List<LeaveTypeRestrictions> leaveTypeRestrictionsList = leaveTypeRestrictionsRepository.findAll();
        assertThat(leaveTypeRestrictionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveTypeRestrictionsRepository.findAll().size();
        // set the field null
        leaveTypeRestrictions.setCreatedAt(null);

        // Create the LeaveTypeRestrictions, which fails.

        restLeaveTypeRestrictionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeRestrictions))
            )
            .andExpect(status().isBadRequest());

        List<LeaveTypeRestrictions> leaveTypeRestrictionsList = leaveTypeRestrictionsRepository.findAll();
        assertThat(leaveTypeRestrictionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveTypeRestrictionsRepository.findAll().size();
        // set the field null
        leaveTypeRestrictions.setUpdatedAt(null);

        // Create the LeaveTypeRestrictions, which fails.

        restLeaveTypeRestrictionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeRestrictions))
            )
            .andExpect(status().isBadRequest());

        List<LeaveTypeRestrictions> leaveTypeRestrictionsList = leaveTypeRestrictionsRepository.findAll();
        assertThat(leaveTypeRestrictionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveTypeRestrictionsRepository.findAll().size();
        // set the field null
        leaveTypeRestrictions.setVersion(null);

        // Create the LeaveTypeRestrictions, which fails.

        restLeaveTypeRestrictionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeRestrictions))
            )
            .andExpect(status().isBadRequest());

        List<LeaveTypeRestrictions> leaveTypeRestrictionsList = leaveTypeRestrictionsRepository.findAll();
        assertThat(leaveTypeRestrictionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLeaveTypeRestrictions() throws Exception {
        // Initialize the database
        leaveTypeRestrictionsRepository.saveAndFlush(leaveTypeRestrictions);

        // Get all the leaveTypeRestrictionsList
        restLeaveTypeRestrictionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveTypeRestrictions.getId().intValue())))
            .andExpect(jsonPath("$.[*].restrictionJson").value(hasItem(DEFAULT_RESTRICTION_JSON)))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getLeaveTypeRestrictions() throws Exception {
        // Initialize the database
        leaveTypeRestrictionsRepository.saveAndFlush(leaveTypeRestrictions);

        // Get the leaveTypeRestrictions
        restLeaveTypeRestrictionsMockMvc
            .perform(get(ENTITY_API_URL_ID, leaveTypeRestrictions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leaveTypeRestrictions.getId().intValue()))
            .andExpect(jsonPath("$.restrictionJson").value(DEFAULT_RESTRICTION_JSON))
            .andExpect(jsonPath("$.effDate").value(DEFAULT_EFF_DATE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getLeaveTypeRestrictionsByIdFiltering() throws Exception {
        // Initialize the database
        leaveTypeRestrictionsRepository.saveAndFlush(leaveTypeRestrictions);

        Long id = leaveTypeRestrictions.getId();

        defaultLeaveTypeRestrictionsShouldBeFound("id.equals=" + id);
        defaultLeaveTypeRestrictionsShouldNotBeFound("id.notEquals=" + id);

        defaultLeaveTypeRestrictionsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLeaveTypeRestrictionsShouldNotBeFound("id.greaterThan=" + id);

        defaultLeaveTypeRestrictionsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLeaveTypeRestrictionsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLeaveTypeRestrictionsByRestrictionJsonIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRestrictionsRepository.saveAndFlush(leaveTypeRestrictions);

        // Get all the leaveTypeRestrictionsList where restrictionJson equals to DEFAULT_RESTRICTION_JSON
        defaultLeaveTypeRestrictionsShouldBeFound("restrictionJson.equals=" + DEFAULT_RESTRICTION_JSON);

        // Get all the leaveTypeRestrictionsList where restrictionJson equals to UPDATED_RESTRICTION_JSON
        defaultLeaveTypeRestrictionsShouldNotBeFound("restrictionJson.equals=" + UPDATED_RESTRICTION_JSON);
    }

    @Test
    @Transactional
    void getAllLeaveTypeRestrictionsByRestrictionJsonIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeRestrictionsRepository.saveAndFlush(leaveTypeRestrictions);

        // Get all the leaveTypeRestrictionsList where restrictionJson in DEFAULT_RESTRICTION_JSON or UPDATED_RESTRICTION_JSON
        defaultLeaveTypeRestrictionsShouldBeFound("restrictionJson.in=" + DEFAULT_RESTRICTION_JSON + "," + UPDATED_RESTRICTION_JSON);

        // Get all the leaveTypeRestrictionsList where restrictionJson equals to UPDATED_RESTRICTION_JSON
        defaultLeaveTypeRestrictionsShouldNotBeFound("restrictionJson.in=" + UPDATED_RESTRICTION_JSON);
    }

    @Test
    @Transactional
    void getAllLeaveTypeRestrictionsByRestrictionJsonIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeRestrictionsRepository.saveAndFlush(leaveTypeRestrictions);

        // Get all the leaveTypeRestrictionsList where restrictionJson is not null
        defaultLeaveTypeRestrictionsShouldBeFound("restrictionJson.specified=true");

        // Get all the leaveTypeRestrictionsList where restrictionJson is null
        defaultLeaveTypeRestrictionsShouldNotBeFound("restrictionJson.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypeRestrictionsByRestrictionJsonContainsSomething() throws Exception {
        // Initialize the database
        leaveTypeRestrictionsRepository.saveAndFlush(leaveTypeRestrictions);

        // Get all the leaveTypeRestrictionsList where restrictionJson contains DEFAULT_RESTRICTION_JSON
        defaultLeaveTypeRestrictionsShouldBeFound("restrictionJson.contains=" + DEFAULT_RESTRICTION_JSON);

        // Get all the leaveTypeRestrictionsList where restrictionJson contains UPDATED_RESTRICTION_JSON
        defaultLeaveTypeRestrictionsShouldNotBeFound("restrictionJson.contains=" + UPDATED_RESTRICTION_JSON);
    }

    @Test
    @Transactional
    void getAllLeaveTypeRestrictionsByRestrictionJsonNotContainsSomething() throws Exception {
        // Initialize the database
        leaveTypeRestrictionsRepository.saveAndFlush(leaveTypeRestrictions);

        // Get all the leaveTypeRestrictionsList where restrictionJson does not contain DEFAULT_RESTRICTION_JSON
        defaultLeaveTypeRestrictionsShouldNotBeFound("restrictionJson.doesNotContain=" + DEFAULT_RESTRICTION_JSON);

        // Get all the leaveTypeRestrictionsList where restrictionJson does not contain UPDATED_RESTRICTION_JSON
        defaultLeaveTypeRestrictionsShouldBeFound("restrictionJson.doesNotContain=" + UPDATED_RESTRICTION_JSON);
    }

    @Test
    @Transactional
    void getAllLeaveTypeRestrictionsByEffDateIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRestrictionsRepository.saveAndFlush(leaveTypeRestrictions);

        // Get all the leaveTypeRestrictionsList where effDate equals to DEFAULT_EFF_DATE
        defaultLeaveTypeRestrictionsShouldBeFound("effDate.equals=" + DEFAULT_EFF_DATE);

        // Get all the leaveTypeRestrictionsList where effDate equals to UPDATED_EFF_DATE
        defaultLeaveTypeRestrictionsShouldNotBeFound("effDate.equals=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveTypeRestrictionsByEffDateIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeRestrictionsRepository.saveAndFlush(leaveTypeRestrictions);

        // Get all the leaveTypeRestrictionsList where effDate in DEFAULT_EFF_DATE or UPDATED_EFF_DATE
        defaultLeaveTypeRestrictionsShouldBeFound("effDate.in=" + DEFAULT_EFF_DATE + "," + UPDATED_EFF_DATE);

        // Get all the leaveTypeRestrictionsList where effDate equals to UPDATED_EFF_DATE
        defaultLeaveTypeRestrictionsShouldNotBeFound("effDate.in=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveTypeRestrictionsByEffDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeRestrictionsRepository.saveAndFlush(leaveTypeRestrictions);

        // Get all the leaveTypeRestrictionsList where effDate is not null
        defaultLeaveTypeRestrictionsShouldBeFound("effDate.specified=true");

        // Get all the leaveTypeRestrictionsList where effDate is null
        defaultLeaveTypeRestrictionsShouldNotBeFound("effDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypeRestrictionsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRestrictionsRepository.saveAndFlush(leaveTypeRestrictions);

        // Get all the leaveTypeRestrictionsList where createdAt equals to DEFAULT_CREATED_AT
        defaultLeaveTypeRestrictionsShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the leaveTypeRestrictionsList where createdAt equals to UPDATED_CREATED_AT
        defaultLeaveTypeRestrictionsShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveTypeRestrictionsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeRestrictionsRepository.saveAndFlush(leaveTypeRestrictions);

        // Get all the leaveTypeRestrictionsList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultLeaveTypeRestrictionsShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the leaveTypeRestrictionsList where createdAt equals to UPDATED_CREATED_AT
        defaultLeaveTypeRestrictionsShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveTypeRestrictionsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeRestrictionsRepository.saveAndFlush(leaveTypeRestrictions);

        // Get all the leaveTypeRestrictionsList where createdAt is not null
        defaultLeaveTypeRestrictionsShouldBeFound("createdAt.specified=true");

        // Get all the leaveTypeRestrictionsList where createdAt is null
        defaultLeaveTypeRestrictionsShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypeRestrictionsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRestrictionsRepository.saveAndFlush(leaveTypeRestrictions);

        // Get all the leaveTypeRestrictionsList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultLeaveTypeRestrictionsShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the leaveTypeRestrictionsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultLeaveTypeRestrictionsShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveTypeRestrictionsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeRestrictionsRepository.saveAndFlush(leaveTypeRestrictions);

        // Get all the leaveTypeRestrictionsList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultLeaveTypeRestrictionsShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the leaveTypeRestrictionsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultLeaveTypeRestrictionsShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllLeaveTypeRestrictionsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeRestrictionsRepository.saveAndFlush(leaveTypeRestrictions);

        // Get all the leaveTypeRestrictionsList where updatedAt is not null
        defaultLeaveTypeRestrictionsShouldBeFound("updatedAt.specified=true");

        // Get all the leaveTypeRestrictionsList where updatedAt is null
        defaultLeaveTypeRestrictionsShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypeRestrictionsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRestrictionsRepository.saveAndFlush(leaveTypeRestrictions);

        // Get all the leaveTypeRestrictionsList where endDate equals to DEFAULT_END_DATE
        defaultLeaveTypeRestrictionsShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the leaveTypeRestrictionsList where endDate equals to UPDATED_END_DATE
        defaultLeaveTypeRestrictionsShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveTypeRestrictionsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeRestrictionsRepository.saveAndFlush(leaveTypeRestrictions);

        // Get all the leaveTypeRestrictionsList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultLeaveTypeRestrictionsShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the leaveTypeRestrictionsList where endDate equals to UPDATED_END_DATE
        defaultLeaveTypeRestrictionsShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveTypeRestrictionsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeRestrictionsRepository.saveAndFlush(leaveTypeRestrictions);

        // Get all the leaveTypeRestrictionsList where endDate is not null
        defaultLeaveTypeRestrictionsShouldBeFound("endDate.specified=true");

        // Get all the leaveTypeRestrictionsList where endDate is null
        defaultLeaveTypeRestrictionsShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypeRestrictionsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRestrictionsRepository.saveAndFlush(leaveTypeRestrictions);

        // Get all the leaveTypeRestrictionsList where version equals to DEFAULT_VERSION
        defaultLeaveTypeRestrictionsShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the leaveTypeRestrictionsList where version equals to UPDATED_VERSION
        defaultLeaveTypeRestrictionsShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveTypeRestrictionsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeRestrictionsRepository.saveAndFlush(leaveTypeRestrictions);

        // Get all the leaveTypeRestrictionsList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultLeaveTypeRestrictionsShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the leaveTypeRestrictionsList where version equals to UPDATED_VERSION
        defaultLeaveTypeRestrictionsShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveTypeRestrictionsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeRestrictionsRepository.saveAndFlush(leaveTypeRestrictions);

        // Get all the leaveTypeRestrictionsList where version is not null
        defaultLeaveTypeRestrictionsShouldBeFound("version.specified=true");

        // Get all the leaveTypeRestrictionsList where version is null
        defaultLeaveTypeRestrictionsShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypeRestrictionsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRestrictionsRepository.saveAndFlush(leaveTypeRestrictions);

        // Get all the leaveTypeRestrictionsList where version is greater than or equal to DEFAULT_VERSION
        defaultLeaveTypeRestrictionsShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the leaveTypeRestrictionsList where version is greater than or equal to UPDATED_VERSION
        defaultLeaveTypeRestrictionsShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveTypeRestrictionsByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRestrictionsRepository.saveAndFlush(leaveTypeRestrictions);

        // Get all the leaveTypeRestrictionsList where version is less than or equal to DEFAULT_VERSION
        defaultLeaveTypeRestrictionsShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the leaveTypeRestrictionsList where version is less than or equal to SMALLER_VERSION
        defaultLeaveTypeRestrictionsShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveTypeRestrictionsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveTypeRestrictionsRepository.saveAndFlush(leaveTypeRestrictions);

        // Get all the leaveTypeRestrictionsList where version is less than DEFAULT_VERSION
        defaultLeaveTypeRestrictionsShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the leaveTypeRestrictionsList where version is less than UPDATED_VERSION
        defaultLeaveTypeRestrictionsShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveTypeRestrictionsByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveTypeRestrictionsRepository.saveAndFlush(leaveTypeRestrictions);

        // Get all the leaveTypeRestrictionsList where version is greater than DEFAULT_VERSION
        defaultLeaveTypeRestrictionsShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the leaveTypeRestrictionsList where version is greater than SMALLER_VERSION
        defaultLeaveTypeRestrictionsShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllLeaveTypeRestrictionsByLeaveTypeIsEqualToSomething() throws Exception {
        LeaveTypes leaveType;
        if (TestUtil.findAll(em, LeaveTypes.class).isEmpty()) {
            leaveTypeRestrictionsRepository.saveAndFlush(leaveTypeRestrictions);
            leaveType = LeaveTypesResourceIT.createEntity(em);
        } else {
            leaveType = TestUtil.findAll(em, LeaveTypes.class).get(0);
        }
        em.persist(leaveType);
        em.flush();
        leaveTypeRestrictions.setLeaveType(leaveType);
        leaveTypeRestrictionsRepository.saveAndFlush(leaveTypeRestrictions);
        Long leaveTypeId = leaveType.getId();

        // Get all the leaveTypeRestrictionsList where leaveType equals to leaveTypeId
        defaultLeaveTypeRestrictionsShouldBeFound("leaveTypeId.equals=" + leaveTypeId);

        // Get all the leaveTypeRestrictionsList where leaveType equals to (leaveTypeId + 1)
        defaultLeaveTypeRestrictionsShouldNotBeFound("leaveTypeId.equals=" + (leaveTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLeaveTypeRestrictionsShouldBeFound(String filter) throws Exception {
        restLeaveTypeRestrictionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveTypeRestrictions.getId().intValue())))
            .andExpect(jsonPath("$.[*].restrictionJson").value(hasItem(DEFAULT_RESTRICTION_JSON)))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restLeaveTypeRestrictionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLeaveTypeRestrictionsShouldNotBeFound(String filter) throws Exception {
        restLeaveTypeRestrictionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLeaveTypeRestrictionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLeaveTypeRestrictions() throws Exception {
        // Get the leaveTypeRestrictions
        restLeaveTypeRestrictionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLeaveTypeRestrictions() throws Exception {
        // Initialize the database
        leaveTypeRestrictionsRepository.saveAndFlush(leaveTypeRestrictions);

        int databaseSizeBeforeUpdate = leaveTypeRestrictionsRepository.findAll().size();

        // Update the leaveTypeRestrictions
        LeaveTypeRestrictions updatedLeaveTypeRestrictions = leaveTypeRestrictionsRepository.findById(leaveTypeRestrictions.getId()).get();
        // Disconnect from session so that the updates on updatedLeaveTypeRestrictions are not directly saved in db
        em.detach(updatedLeaveTypeRestrictions);
        updatedLeaveTypeRestrictions
            .restrictionJson(UPDATED_RESTRICTION_JSON)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restLeaveTypeRestrictionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLeaveTypeRestrictions.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLeaveTypeRestrictions))
            )
            .andExpect(status().isOk());

        // Validate the LeaveTypeRestrictions in the database
        List<LeaveTypeRestrictions> leaveTypeRestrictionsList = leaveTypeRestrictionsRepository.findAll();
        assertThat(leaveTypeRestrictionsList).hasSize(databaseSizeBeforeUpdate);
        LeaveTypeRestrictions testLeaveTypeRestrictions = leaveTypeRestrictionsList.get(leaveTypeRestrictionsList.size() - 1);
        assertThat(testLeaveTypeRestrictions.getRestrictionJson()).isEqualTo(UPDATED_RESTRICTION_JSON);
        assertThat(testLeaveTypeRestrictions.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testLeaveTypeRestrictions.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveTypeRestrictions.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaveTypeRestrictions.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testLeaveTypeRestrictions.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingLeaveTypeRestrictions() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypeRestrictionsRepository.findAll().size();
        leaveTypeRestrictions.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveTypeRestrictionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leaveTypeRestrictions.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeRestrictions))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveTypeRestrictions in the database
        List<LeaveTypeRestrictions> leaveTypeRestrictionsList = leaveTypeRestrictionsRepository.findAll();
        assertThat(leaveTypeRestrictionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeaveTypeRestrictions() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypeRestrictionsRepository.findAll().size();
        leaveTypeRestrictions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveTypeRestrictionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeRestrictions))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveTypeRestrictions in the database
        List<LeaveTypeRestrictions> leaveTypeRestrictionsList = leaveTypeRestrictionsRepository.findAll();
        assertThat(leaveTypeRestrictionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeaveTypeRestrictions() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypeRestrictionsRepository.findAll().size();
        leaveTypeRestrictions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveTypeRestrictionsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeRestrictions))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveTypeRestrictions in the database
        List<LeaveTypeRestrictions> leaveTypeRestrictionsList = leaveTypeRestrictionsRepository.findAll();
        assertThat(leaveTypeRestrictionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeaveTypeRestrictionsWithPatch() throws Exception {
        // Initialize the database
        leaveTypeRestrictionsRepository.saveAndFlush(leaveTypeRestrictions);

        int databaseSizeBeforeUpdate = leaveTypeRestrictionsRepository.findAll().size();

        // Update the leaveTypeRestrictions using partial update
        LeaveTypeRestrictions partialUpdatedLeaveTypeRestrictions = new LeaveTypeRestrictions();
        partialUpdatedLeaveTypeRestrictions.setId(leaveTypeRestrictions.getId());

        partialUpdatedLeaveTypeRestrictions
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restLeaveTypeRestrictionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveTypeRestrictions.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveTypeRestrictions))
            )
            .andExpect(status().isOk());

        // Validate the LeaveTypeRestrictions in the database
        List<LeaveTypeRestrictions> leaveTypeRestrictionsList = leaveTypeRestrictionsRepository.findAll();
        assertThat(leaveTypeRestrictionsList).hasSize(databaseSizeBeforeUpdate);
        LeaveTypeRestrictions testLeaveTypeRestrictions = leaveTypeRestrictionsList.get(leaveTypeRestrictionsList.size() - 1);
        assertThat(testLeaveTypeRestrictions.getRestrictionJson()).isEqualTo(DEFAULT_RESTRICTION_JSON);
        assertThat(testLeaveTypeRestrictions.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testLeaveTypeRestrictions.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveTypeRestrictions.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaveTypeRestrictions.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testLeaveTypeRestrictions.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateLeaveTypeRestrictionsWithPatch() throws Exception {
        // Initialize the database
        leaveTypeRestrictionsRepository.saveAndFlush(leaveTypeRestrictions);

        int databaseSizeBeforeUpdate = leaveTypeRestrictionsRepository.findAll().size();

        // Update the leaveTypeRestrictions using partial update
        LeaveTypeRestrictions partialUpdatedLeaveTypeRestrictions = new LeaveTypeRestrictions();
        partialUpdatedLeaveTypeRestrictions.setId(leaveTypeRestrictions.getId());

        partialUpdatedLeaveTypeRestrictions
            .restrictionJson(UPDATED_RESTRICTION_JSON)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restLeaveTypeRestrictionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveTypeRestrictions.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveTypeRestrictions))
            )
            .andExpect(status().isOk());

        // Validate the LeaveTypeRestrictions in the database
        List<LeaveTypeRestrictions> leaveTypeRestrictionsList = leaveTypeRestrictionsRepository.findAll();
        assertThat(leaveTypeRestrictionsList).hasSize(databaseSizeBeforeUpdate);
        LeaveTypeRestrictions testLeaveTypeRestrictions = leaveTypeRestrictionsList.get(leaveTypeRestrictionsList.size() - 1);
        assertThat(testLeaveTypeRestrictions.getRestrictionJson()).isEqualTo(UPDATED_RESTRICTION_JSON);
        assertThat(testLeaveTypeRestrictions.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testLeaveTypeRestrictions.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeaveTypeRestrictions.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeaveTypeRestrictions.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testLeaveTypeRestrictions.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingLeaveTypeRestrictions() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypeRestrictionsRepository.findAll().size();
        leaveTypeRestrictions.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveTypeRestrictionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leaveTypeRestrictions.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeRestrictions))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveTypeRestrictions in the database
        List<LeaveTypeRestrictions> leaveTypeRestrictionsList = leaveTypeRestrictionsRepository.findAll();
        assertThat(leaveTypeRestrictionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeaveTypeRestrictions() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypeRestrictionsRepository.findAll().size();
        leaveTypeRestrictions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveTypeRestrictionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeRestrictions))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveTypeRestrictions in the database
        List<LeaveTypeRestrictions> leaveTypeRestrictionsList = leaveTypeRestrictionsRepository.findAll();
        assertThat(leaveTypeRestrictionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeaveTypeRestrictions() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypeRestrictionsRepository.findAll().size();
        leaveTypeRestrictions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveTypeRestrictionsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeRestrictions))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveTypeRestrictions in the database
        List<LeaveTypeRestrictions> leaveTypeRestrictionsList = leaveTypeRestrictionsRepository.findAll();
        assertThat(leaveTypeRestrictionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLeaveTypeRestrictions() throws Exception {
        // Initialize the database
        leaveTypeRestrictionsRepository.saveAndFlush(leaveTypeRestrictions);

        int databaseSizeBeforeDelete = leaveTypeRestrictionsRepository.findAll().size();

        // Delete the leaveTypeRestrictions
        restLeaveTypeRestrictionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, leaveTypeRestrictions.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeaveTypeRestrictions> leaveTypeRestrictionsList = leaveTypeRestrictionsRepository.findAll();
        assertThat(leaveTypeRestrictionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
