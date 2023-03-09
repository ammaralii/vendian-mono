package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.PcRatings;
import com.venturedive.vendian_mono.domain.PcStatuses;
import com.venturedive.vendian_mono.repository.PcStatusesRepository;
import com.venturedive.vendian_mono.service.criteria.PcStatusesCriteria;
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
 * Integration tests for the {@link PcStatusesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PcStatusesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GROUP = "AAAAAAAAAA";
    private static final String UPDATED_GROUP = "BBBBBBBBBB";

    private static final String DEFAULT_SYSTEM_KEY = "AAAAAAAAAA";
    private static final String UPDATED_SYSTEM_KEY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;
    private static final Integer SMALLER_VERSION = 1 - 1;

    private static final String ENTITY_API_URL = "/api/pc-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PcStatusesRepository pcStatusesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPcStatusesMockMvc;

    private PcStatuses pcStatuses;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PcStatuses createEntity(EntityManager em) {
        PcStatuses pcStatuses = new PcStatuses()
            .name(DEFAULT_NAME)
            .group(DEFAULT_GROUP)
            .systemKey(DEFAULT_SYSTEM_KEY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT)
            .version(DEFAULT_VERSION);
        return pcStatuses;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PcStatuses createUpdatedEntity(EntityManager em) {
        PcStatuses pcStatuses = new PcStatuses()
            .name(UPDATED_NAME)
            .group(UPDATED_GROUP)
            .systemKey(UPDATED_SYSTEM_KEY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);
        return pcStatuses;
    }

    @BeforeEach
    public void initTest() {
        pcStatuses = createEntity(em);
    }

    @Test
    @Transactional
    void createPcStatuses() throws Exception {
        int databaseSizeBeforeCreate = pcStatusesRepository.findAll().size();
        // Create the PcStatuses
        restPcStatusesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcStatuses))
            )
            .andExpect(status().isCreated());

        // Validate the PcStatuses in the database
        List<PcStatuses> pcStatusesList = pcStatusesRepository.findAll();
        assertThat(pcStatusesList).hasSize(databaseSizeBeforeCreate + 1);
        PcStatuses testPcStatuses = pcStatusesList.get(pcStatusesList.size() - 1);
        assertThat(testPcStatuses.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPcStatuses.getGroup()).isEqualTo(DEFAULT_GROUP);
        assertThat(testPcStatuses.getSystemKey()).isEqualTo(DEFAULT_SYSTEM_KEY);
        assertThat(testPcStatuses.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testPcStatuses.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testPcStatuses.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testPcStatuses.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createPcStatusesWithExistingId() throws Exception {
        // Create the PcStatuses with an existing ID
        pcStatuses.setId(1L);

        int databaseSizeBeforeCreate = pcStatusesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPcStatusesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcStatuses))
            )
            .andExpect(status().isBadRequest());

        // Validate the PcStatuses in the database
        List<PcStatuses> pcStatusesList = pcStatusesRepository.findAll();
        assertThat(pcStatusesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = pcStatusesRepository.findAll().size();
        // set the field null
        pcStatuses.setName(null);

        // Create the PcStatuses, which fails.

        restPcStatusesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcStatuses))
            )
            .andExpect(status().isBadRequest());

        List<PcStatuses> pcStatusesList = pcStatusesRepository.findAll();
        assertThat(pcStatusesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGroupIsRequired() throws Exception {
        int databaseSizeBeforeTest = pcStatusesRepository.findAll().size();
        // set the field null
        pcStatuses.setGroup(null);

        // Create the PcStatuses, which fails.

        restPcStatusesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcStatuses))
            )
            .andExpect(status().isBadRequest());

        List<PcStatuses> pcStatusesList = pcStatusesRepository.findAll();
        assertThat(pcStatusesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = pcStatusesRepository.findAll().size();
        // set the field null
        pcStatuses.setCreatedAt(null);

        // Create the PcStatuses, which fails.

        restPcStatusesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcStatuses))
            )
            .andExpect(status().isBadRequest());

        List<PcStatuses> pcStatusesList = pcStatusesRepository.findAll();
        assertThat(pcStatusesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = pcStatusesRepository.findAll().size();
        // set the field null
        pcStatuses.setUpdatedAt(null);

        // Create the PcStatuses, which fails.

        restPcStatusesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcStatuses))
            )
            .andExpect(status().isBadRequest());

        List<PcStatuses> pcStatusesList = pcStatusesRepository.findAll();
        assertThat(pcStatusesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = pcStatusesRepository.findAll().size();
        // set the field null
        pcStatuses.setVersion(null);

        // Create the PcStatuses, which fails.

        restPcStatusesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcStatuses))
            )
            .andExpect(status().isBadRequest());

        List<PcStatuses> pcStatusesList = pcStatusesRepository.findAll();
        assertThat(pcStatusesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPcStatuses() throws Exception {
        // Initialize the database
        pcStatusesRepository.saveAndFlush(pcStatuses);

        // Get all the pcStatusesList
        restPcStatusesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pcStatuses.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].group").value(hasItem(DEFAULT_GROUP)))
            .andExpect(jsonPath("$.[*].systemKey").value(hasItem(DEFAULT_SYSTEM_KEY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getPcStatuses() throws Exception {
        // Initialize the database
        pcStatusesRepository.saveAndFlush(pcStatuses);

        // Get the pcStatuses
        restPcStatusesMockMvc
            .perform(get(ENTITY_API_URL_ID, pcStatuses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pcStatuses.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.group").value(DEFAULT_GROUP))
            .andExpect(jsonPath("$.systemKey").value(DEFAULT_SYSTEM_KEY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getPcStatusesByIdFiltering() throws Exception {
        // Initialize the database
        pcStatusesRepository.saveAndFlush(pcStatuses);

        Long id = pcStatuses.getId();

        defaultPcStatusesShouldBeFound("id.equals=" + id);
        defaultPcStatusesShouldNotBeFound("id.notEquals=" + id);

        defaultPcStatusesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPcStatusesShouldNotBeFound("id.greaterThan=" + id);

        defaultPcStatusesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPcStatusesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPcStatusesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        pcStatusesRepository.saveAndFlush(pcStatuses);

        // Get all the pcStatusesList where name equals to DEFAULT_NAME
        defaultPcStatusesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the pcStatusesList where name equals to UPDATED_NAME
        defaultPcStatusesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPcStatusesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        pcStatusesRepository.saveAndFlush(pcStatuses);

        // Get all the pcStatusesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPcStatusesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the pcStatusesList where name equals to UPDATED_NAME
        defaultPcStatusesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPcStatusesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcStatusesRepository.saveAndFlush(pcStatuses);

        // Get all the pcStatusesList where name is not null
        defaultPcStatusesShouldBeFound("name.specified=true");

        // Get all the pcStatusesList where name is null
        defaultPcStatusesShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllPcStatusesByNameContainsSomething() throws Exception {
        // Initialize the database
        pcStatusesRepository.saveAndFlush(pcStatuses);

        // Get all the pcStatusesList where name contains DEFAULT_NAME
        defaultPcStatusesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the pcStatusesList where name contains UPDATED_NAME
        defaultPcStatusesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPcStatusesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        pcStatusesRepository.saveAndFlush(pcStatuses);

        // Get all the pcStatusesList where name does not contain DEFAULT_NAME
        defaultPcStatusesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the pcStatusesList where name does not contain UPDATED_NAME
        defaultPcStatusesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPcStatusesByGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        pcStatusesRepository.saveAndFlush(pcStatuses);

        // Get all the pcStatusesList where group equals to DEFAULT_GROUP
        defaultPcStatusesShouldBeFound("group.equals=" + DEFAULT_GROUP);

        // Get all the pcStatusesList where group equals to UPDATED_GROUP
        defaultPcStatusesShouldNotBeFound("group.equals=" + UPDATED_GROUP);
    }

    @Test
    @Transactional
    void getAllPcStatusesByGroupIsInShouldWork() throws Exception {
        // Initialize the database
        pcStatusesRepository.saveAndFlush(pcStatuses);

        // Get all the pcStatusesList where group in DEFAULT_GROUP or UPDATED_GROUP
        defaultPcStatusesShouldBeFound("group.in=" + DEFAULT_GROUP + "," + UPDATED_GROUP);

        // Get all the pcStatusesList where group equals to UPDATED_GROUP
        defaultPcStatusesShouldNotBeFound("group.in=" + UPDATED_GROUP);
    }

    @Test
    @Transactional
    void getAllPcStatusesByGroupIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcStatusesRepository.saveAndFlush(pcStatuses);

        // Get all the pcStatusesList where group is not null
        defaultPcStatusesShouldBeFound("group.specified=true");

        // Get all the pcStatusesList where group is null
        defaultPcStatusesShouldNotBeFound("group.specified=false");
    }

    @Test
    @Transactional
    void getAllPcStatusesByGroupContainsSomething() throws Exception {
        // Initialize the database
        pcStatusesRepository.saveAndFlush(pcStatuses);

        // Get all the pcStatusesList where group contains DEFAULT_GROUP
        defaultPcStatusesShouldBeFound("group.contains=" + DEFAULT_GROUP);

        // Get all the pcStatusesList where group contains UPDATED_GROUP
        defaultPcStatusesShouldNotBeFound("group.contains=" + UPDATED_GROUP);
    }

    @Test
    @Transactional
    void getAllPcStatusesByGroupNotContainsSomething() throws Exception {
        // Initialize the database
        pcStatusesRepository.saveAndFlush(pcStatuses);

        // Get all the pcStatusesList where group does not contain DEFAULT_GROUP
        defaultPcStatusesShouldNotBeFound("group.doesNotContain=" + DEFAULT_GROUP);

        // Get all the pcStatusesList where group does not contain UPDATED_GROUP
        defaultPcStatusesShouldBeFound("group.doesNotContain=" + UPDATED_GROUP);
    }

    @Test
    @Transactional
    void getAllPcStatusesBySystemKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        pcStatusesRepository.saveAndFlush(pcStatuses);

        // Get all the pcStatusesList where systemKey equals to DEFAULT_SYSTEM_KEY
        defaultPcStatusesShouldBeFound("systemKey.equals=" + DEFAULT_SYSTEM_KEY);

        // Get all the pcStatusesList where systemKey equals to UPDATED_SYSTEM_KEY
        defaultPcStatusesShouldNotBeFound("systemKey.equals=" + UPDATED_SYSTEM_KEY);
    }

    @Test
    @Transactional
    void getAllPcStatusesBySystemKeyIsInShouldWork() throws Exception {
        // Initialize the database
        pcStatusesRepository.saveAndFlush(pcStatuses);

        // Get all the pcStatusesList where systemKey in DEFAULT_SYSTEM_KEY or UPDATED_SYSTEM_KEY
        defaultPcStatusesShouldBeFound("systemKey.in=" + DEFAULT_SYSTEM_KEY + "," + UPDATED_SYSTEM_KEY);

        // Get all the pcStatusesList where systemKey equals to UPDATED_SYSTEM_KEY
        defaultPcStatusesShouldNotBeFound("systemKey.in=" + UPDATED_SYSTEM_KEY);
    }

    @Test
    @Transactional
    void getAllPcStatusesBySystemKeyIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcStatusesRepository.saveAndFlush(pcStatuses);

        // Get all the pcStatusesList where systemKey is not null
        defaultPcStatusesShouldBeFound("systemKey.specified=true");

        // Get all the pcStatusesList where systemKey is null
        defaultPcStatusesShouldNotBeFound("systemKey.specified=false");
    }

    @Test
    @Transactional
    void getAllPcStatusesBySystemKeyContainsSomething() throws Exception {
        // Initialize the database
        pcStatusesRepository.saveAndFlush(pcStatuses);

        // Get all the pcStatusesList where systemKey contains DEFAULT_SYSTEM_KEY
        defaultPcStatusesShouldBeFound("systemKey.contains=" + DEFAULT_SYSTEM_KEY);

        // Get all the pcStatusesList where systemKey contains UPDATED_SYSTEM_KEY
        defaultPcStatusesShouldNotBeFound("systemKey.contains=" + UPDATED_SYSTEM_KEY);
    }

    @Test
    @Transactional
    void getAllPcStatusesBySystemKeyNotContainsSomething() throws Exception {
        // Initialize the database
        pcStatusesRepository.saveAndFlush(pcStatuses);

        // Get all the pcStatusesList where systemKey does not contain DEFAULT_SYSTEM_KEY
        defaultPcStatusesShouldNotBeFound("systemKey.doesNotContain=" + DEFAULT_SYSTEM_KEY);

        // Get all the pcStatusesList where systemKey does not contain UPDATED_SYSTEM_KEY
        defaultPcStatusesShouldBeFound("systemKey.doesNotContain=" + UPDATED_SYSTEM_KEY);
    }

    @Test
    @Transactional
    void getAllPcStatusesByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        pcStatusesRepository.saveAndFlush(pcStatuses);

        // Get all the pcStatusesList where createdAt equals to DEFAULT_CREATED_AT
        defaultPcStatusesShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the pcStatusesList where createdAt equals to UPDATED_CREATED_AT
        defaultPcStatusesShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllPcStatusesByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        pcStatusesRepository.saveAndFlush(pcStatuses);

        // Get all the pcStatusesList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultPcStatusesShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the pcStatusesList where createdAt equals to UPDATED_CREATED_AT
        defaultPcStatusesShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllPcStatusesByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcStatusesRepository.saveAndFlush(pcStatuses);

        // Get all the pcStatusesList where createdAt is not null
        defaultPcStatusesShouldBeFound("createdAt.specified=true");

        // Get all the pcStatusesList where createdAt is null
        defaultPcStatusesShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllPcStatusesByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        pcStatusesRepository.saveAndFlush(pcStatuses);

        // Get all the pcStatusesList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultPcStatusesShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the pcStatusesList where updatedAt equals to UPDATED_UPDATED_AT
        defaultPcStatusesShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllPcStatusesByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        pcStatusesRepository.saveAndFlush(pcStatuses);

        // Get all the pcStatusesList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultPcStatusesShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the pcStatusesList where updatedAt equals to UPDATED_UPDATED_AT
        defaultPcStatusesShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllPcStatusesByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcStatusesRepository.saveAndFlush(pcStatuses);

        // Get all the pcStatusesList where updatedAt is not null
        defaultPcStatusesShouldBeFound("updatedAt.specified=true");

        // Get all the pcStatusesList where updatedAt is null
        defaultPcStatusesShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllPcStatusesByDeletedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        pcStatusesRepository.saveAndFlush(pcStatuses);

        // Get all the pcStatusesList where deletedAt equals to DEFAULT_DELETED_AT
        defaultPcStatusesShouldBeFound("deletedAt.equals=" + DEFAULT_DELETED_AT);

        // Get all the pcStatusesList where deletedAt equals to UPDATED_DELETED_AT
        defaultPcStatusesShouldNotBeFound("deletedAt.equals=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllPcStatusesByDeletedAtIsInShouldWork() throws Exception {
        // Initialize the database
        pcStatusesRepository.saveAndFlush(pcStatuses);

        // Get all the pcStatusesList where deletedAt in DEFAULT_DELETED_AT or UPDATED_DELETED_AT
        defaultPcStatusesShouldBeFound("deletedAt.in=" + DEFAULT_DELETED_AT + "," + UPDATED_DELETED_AT);

        // Get all the pcStatusesList where deletedAt equals to UPDATED_DELETED_AT
        defaultPcStatusesShouldNotBeFound("deletedAt.in=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllPcStatusesByDeletedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcStatusesRepository.saveAndFlush(pcStatuses);

        // Get all the pcStatusesList where deletedAt is not null
        defaultPcStatusesShouldBeFound("deletedAt.specified=true");

        // Get all the pcStatusesList where deletedAt is null
        defaultPcStatusesShouldNotBeFound("deletedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllPcStatusesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        pcStatusesRepository.saveAndFlush(pcStatuses);

        // Get all the pcStatusesList where version equals to DEFAULT_VERSION
        defaultPcStatusesShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the pcStatusesList where version equals to UPDATED_VERSION
        defaultPcStatusesShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllPcStatusesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        pcStatusesRepository.saveAndFlush(pcStatuses);

        // Get all the pcStatusesList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultPcStatusesShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the pcStatusesList where version equals to UPDATED_VERSION
        defaultPcStatusesShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllPcStatusesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcStatusesRepository.saveAndFlush(pcStatuses);

        // Get all the pcStatusesList where version is not null
        defaultPcStatusesShouldBeFound("version.specified=true");

        // Get all the pcStatusesList where version is null
        defaultPcStatusesShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllPcStatusesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pcStatusesRepository.saveAndFlush(pcStatuses);

        // Get all the pcStatusesList where version is greater than or equal to DEFAULT_VERSION
        defaultPcStatusesShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the pcStatusesList where version is greater than or equal to UPDATED_VERSION
        defaultPcStatusesShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllPcStatusesByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pcStatusesRepository.saveAndFlush(pcStatuses);

        // Get all the pcStatusesList where version is less than or equal to DEFAULT_VERSION
        defaultPcStatusesShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the pcStatusesList where version is less than or equal to SMALLER_VERSION
        defaultPcStatusesShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllPcStatusesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        pcStatusesRepository.saveAndFlush(pcStatuses);

        // Get all the pcStatusesList where version is less than DEFAULT_VERSION
        defaultPcStatusesShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the pcStatusesList where version is less than UPDATED_VERSION
        defaultPcStatusesShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllPcStatusesByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pcStatusesRepository.saveAndFlush(pcStatuses);

        // Get all the pcStatusesList where version is greater than DEFAULT_VERSION
        defaultPcStatusesShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the pcStatusesList where version is greater than SMALLER_VERSION
        defaultPcStatusesShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllPcStatusesByPcratingsStatusIsEqualToSomething() throws Exception {
        PcRatings pcratingsStatus;
        if (TestUtil.findAll(em, PcRatings.class).isEmpty()) {
            pcStatusesRepository.saveAndFlush(pcStatuses);
            pcratingsStatus = PcRatingsResourceIT.createEntity(em);
        } else {
            pcratingsStatus = TestUtil.findAll(em, PcRatings.class).get(0);
        }
        em.persist(pcratingsStatus);
        em.flush();
        pcStatuses.addPcratingsStatus(pcratingsStatus);
        pcStatusesRepository.saveAndFlush(pcStatuses);
        Long pcratingsStatusId = pcratingsStatus.getId();

        // Get all the pcStatusesList where pcratingsStatus equals to pcratingsStatusId
        defaultPcStatusesShouldBeFound("pcratingsStatusId.equals=" + pcratingsStatusId);

        // Get all the pcStatusesList where pcratingsStatus equals to (pcratingsStatusId + 1)
        defaultPcStatusesShouldNotBeFound("pcratingsStatusId.equals=" + (pcratingsStatusId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPcStatusesShouldBeFound(String filter) throws Exception {
        restPcStatusesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pcStatuses.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].group").value(hasItem(DEFAULT_GROUP)))
            .andExpect(jsonPath("$.[*].systemKey").value(hasItem(DEFAULT_SYSTEM_KEY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restPcStatusesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPcStatusesShouldNotBeFound(String filter) throws Exception {
        restPcStatusesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPcStatusesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPcStatuses() throws Exception {
        // Get the pcStatuses
        restPcStatusesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPcStatuses() throws Exception {
        // Initialize the database
        pcStatusesRepository.saveAndFlush(pcStatuses);

        int databaseSizeBeforeUpdate = pcStatusesRepository.findAll().size();

        // Update the pcStatuses
        PcStatuses updatedPcStatuses = pcStatusesRepository.findById(pcStatuses.getId()).get();
        // Disconnect from session so that the updates on updatedPcStatuses are not directly saved in db
        em.detach(updatedPcStatuses);
        updatedPcStatuses
            .name(UPDATED_NAME)
            .group(UPDATED_GROUP)
            .systemKey(UPDATED_SYSTEM_KEY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restPcStatusesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPcStatuses.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPcStatuses))
            )
            .andExpect(status().isOk());

        // Validate the PcStatuses in the database
        List<PcStatuses> pcStatusesList = pcStatusesRepository.findAll();
        assertThat(pcStatusesList).hasSize(databaseSizeBeforeUpdate);
        PcStatuses testPcStatuses = pcStatusesList.get(pcStatusesList.size() - 1);
        assertThat(testPcStatuses.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPcStatuses.getGroup()).isEqualTo(UPDATED_GROUP);
        assertThat(testPcStatuses.getSystemKey()).isEqualTo(UPDATED_SYSTEM_KEY);
        assertThat(testPcStatuses.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPcStatuses.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testPcStatuses.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testPcStatuses.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingPcStatuses() throws Exception {
        int databaseSizeBeforeUpdate = pcStatusesRepository.findAll().size();
        pcStatuses.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPcStatusesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pcStatuses.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcStatuses))
            )
            .andExpect(status().isBadRequest());

        // Validate the PcStatuses in the database
        List<PcStatuses> pcStatusesList = pcStatusesRepository.findAll();
        assertThat(pcStatusesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPcStatuses() throws Exception {
        int databaseSizeBeforeUpdate = pcStatusesRepository.findAll().size();
        pcStatuses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPcStatusesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcStatuses))
            )
            .andExpect(status().isBadRequest());

        // Validate the PcStatuses in the database
        List<PcStatuses> pcStatusesList = pcStatusesRepository.findAll();
        assertThat(pcStatusesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPcStatuses() throws Exception {
        int databaseSizeBeforeUpdate = pcStatusesRepository.findAll().size();
        pcStatuses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPcStatusesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcStatuses))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PcStatuses in the database
        List<PcStatuses> pcStatusesList = pcStatusesRepository.findAll();
        assertThat(pcStatusesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePcStatusesWithPatch() throws Exception {
        // Initialize the database
        pcStatusesRepository.saveAndFlush(pcStatuses);

        int databaseSizeBeforeUpdate = pcStatusesRepository.findAll().size();

        // Update the pcStatuses using partial update
        PcStatuses partialUpdatedPcStatuses = new PcStatuses();
        partialUpdatedPcStatuses.setId(pcStatuses.getId());

        partialUpdatedPcStatuses
            .systemKey(UPDATED_SYSTEM_KEY)
            .createdAt(UPDATED_CREATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restPcStatusesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPcStatuses.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPcStatuses))
            )
            .andExpect(status().isOk());

        // Validate the PcStatuses in the database
        List<PcStatuses> pcStatusesList = pcStatusesRepository.findAll();
        assertThat(pcStatusesList).hasSize(databaseSizeBeforeUpdate);
        PcStatuses testPcStatuses = pcStatusesList.get(pcStatusesList.size() - 1);
        assertThat(testPcStatuses.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPcStatuses.getGroup()).isEqualTo(DEFAULT_GROUP);
        assertThat(testPcStatuses.getSystemKey()).isEqualTo(UPDATED_SYSTEM_KEY);
        assertThat(testPcStatuses.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPcStatuses.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testPcStatuses.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testPcStatuses.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void fullUpdatePcStatusesWithPatch() throws Exception {
        // Initialize the database
        pcStatusesRepository.saveAndFlush(pcStatuses);

        int databaseSizeBeforeUpdate = pcStatusesRepository.findAll().size();

        // Update the pcStatuses using partial update
        PcStatuses partialUpdatedPcStatuses = new PcStatuses();
        partialUpdatedPcStatuses.setId(pcStatuses.getId());

        partialUpdatedPcStatuses
            .name(UPDATED_NAME)
            .group(UPDATED_GROUP)
            .systemKey(UPDATED_SYSTEM_KEY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restPcStatusesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPcStatuses.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPcStatuses))
            )
            .andExpect(status().isOk());

        // Validate the PcStatuses in the database
        List<PcStatuses> pcStatusesList = pcStatusesRepository.findAll();
        assertThat(pcStatusesList).hasSize(databaseSizeBeforeUpdate);
        PcStatuses testPcStatuses = pcStatusesList.get(pcStatusesList.size() - 1);
        assertThat(testPcStatuses.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPcStatuses.getGroup()).isEqualTo(UPDATED_GROUP);
        assertThat(testPcStatuses.getSystemKey()).isEqualTo(UPDATED_SYSTEM_KEY);
        assertThat(testPcStatuses.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPcStatuses.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testPcStatuses.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testPcStatuses.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingPcStatuses() throws Exception {
        int databaseSizeBeforeUpdate = pcStatusesRepository.findAll().size();
        pcStatuses.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPcStatusesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pcStatuses.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pcStatuses))
            )
            .andExpect(status().isBadRequest());

        // Validate the PcStatuses in the database
        List<PcStatuses> pcStatusesList = pcStatusesRepository.findAll();
        assertThat(pcStatusesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPcStatuses() throws Exception {
        int databaseSizeBeforeUpdate = pcStatusesRepository.findAll().size();
        pcStatuses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPcStatusesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pcStatuses))
            )
            .andExpect(status().isBadRequest());

        // Validate the PcStatuses in the database
        List<PcStatuses> pcStatusesList = pcStatusesRepository.findAll();
        assertThat(pcStatusesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPcStatuses() throws Exception {
        int databaseSizeBeforeUpdate = pcStatusesRepository.findAll().size();
        pcStatuses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPcStatusesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pcStatuses))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PcStatuses in the database
        List<PcStatuses> pcStatusesList = pcStatusesRepository.findAll();
        assertThat(pcStatusesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePcStatuses() throws Exception {
        // Initialize the database
        pcStatusesRepository.saveAndFlush(pcStatuses);

        int databaseSizeBeforeDelete = pcStatusesRepository.findAll().size();

        // Delete the pcStatuses
        restPcStatusesMockMvc
            .perform(delete(ENTITY_API_URL_ID, pcStatuses.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PcStatuses> pcStatusesList = pcStatusesRepository.findAll();
        assertThat(pcStatusesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
