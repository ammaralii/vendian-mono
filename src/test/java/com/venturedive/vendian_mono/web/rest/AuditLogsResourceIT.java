package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.AuditLogs;
import com.venturedive.vendian_mono.repository.AuditLogsRepository;
import com.venturedive.vendian_mono.service.criteria.AuditLogsCriteria;
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
 * Integration tests for the {@link AuditLogsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AuditLogsResourceIT {

    private static final String DEFAULT_EVENT = "AAAAAAAAAA";
    private static final String UPDATED_EVENT = "BBBBBBBBBB";

    private static final Instant DEFAULT_EVENT_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EVENT_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_OLD_CHANGE = "AAAAAAAAAA";
    private static final String UPDATED_OLD_CHANGE = "BBBBBBBBBB";

    private static final String DEFAULT_NEW_CHANGE = "AAAAAAAAAA";
    private static final String UPDATED_NEW_CHANGE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;
    private static final Integer SMALLER_VERSION = 1 - 1;

    private static final String ENTITY_API_URL = "/api/audit-logs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AuditLogsRepository auditLogsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAuditLogsMockMvc;

    private AuditLogs auditLogs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuditLogs createEntity(EntityManager em) {
        AuditLogs auditLogs = new AuditLogs()
            .event(DEFAULT_EVENT)
            .eventTime(DEFAULT_EVENT_TIME)
            .description(DEFAULT_DESCRIPTION)
            .oldChange(DEFAULT_OLD_CHANGE)
            .newChange(DEFAULT_NEW_CHANGE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .version(DEFAULT_VERSION);
        return auditLogs;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuditLogs createUpdatedEntity(EntityManager em) {
        AuditLogs auditLogs = new AuditLogs()
            .event(UPDATED_EVENT)
            .eventTime(UPDATED_EVENT_TIME)
            .description(UPDATED_DESCRIPTION)
            .oldChange(UPDATED_OLD_CHANGE)
            .newChange(UPDATED_NEW_CHANGE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .version(UPDATED_VERSION);
        return auditLogs;
    }

    @BeforeEach
    public void initTest() {
        auditLogs = createEntity(em);
    }

    @Test
    @Transactional
    void createAuditLogs() throws Exception {
        int databaseSizeBeforeCreate = auditLogsRepository.findAll().size();
        // Create the AuditLogs
        restAuditLogsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(auditLogs))
            )
            .andExpect(status().isCreated());

        // Validate the AuditLogs in the database
        List<AuditLogs> auditLogsList = auditLogsRepository.findAll();
        assertThat(auditLogsList).hasSize(databaseSizeBeforeCreate + 1);
        AuditLogs testAuditLogs = auditLogsList.get(auditLogsList.size() - 1);
        assertThat(testAuditLogs.getEvent()).isEqualTo(DEFAULT_EVENT);
        assertThat(testAuditLogs.getEventTime()).isEqualTo(DEFAULT_EVENT_TIME);
        assertThat(testAuditLogs.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAuditLogs.getOldChange()).isEqualTo(DEFAULT_OLD_CHANGE);
        assertThat(testAuditLogs.getNewChange()).isEqualTo(DEFAULT_NEW_CHANGE);
        assertThat(testAuditLogs.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testAuditLogs.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testAuditLogs.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createAuditLogsWithExistingId() throws Exception {
        // Create the AuditLogs with an existing ID
        auditLogs.setId(1L);

        int databaseSizeBeforeCreate = auditLogsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuditLogsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(auditLogs))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuditLogs in the database
        List<AuditLogs> auditLogsList = auditLogsRepository.findAll();
        assertThat(auditLogsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEventIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditLogsRepository.findAll().size();
        // set the field null
        auditLogs.setEvent(null);

        // Create the AuditLogs, which fails.

        restAuditLogsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(auditLogs))
            )
            .andExpect(status().isBadRequest());

        List<AuditLogs> auditLogsList = auditLogsRepository.findAll();
        assertThat(auditLogsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEventTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditLogsRepository.findAll().size();
        // set the field null
        auditLogs.setEventTime(null);

        // Create the AuditLogs, which fails.

        restAuditLogsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(auditLogs))
            )
            .andExpect(status().isBadRequest());

        List<AuditLogs> auditLogsList = auditLogsRepository.findAll();
        assertThat(auditLogsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditLogsRepository.findAll().size();
        // set the field null
        auditLogs.setCreatedAt(null);

        // Create the AuditLogs, which fails.

        restAuditLogsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(auditLogs))
            )
            .andExpect(status().isBadRequest());

        List<AuditLogs> auditLogsList = auditLogsRepository.findAll();
        assertThat(auditLogsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditLogsRepository.findAll().size();
        // set the field null
        auditLogs.setUpdatedAt(null);

        // Create the AuditLogs, which fails.

        restAuditLogsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(auditLogs))
            )
            .andExpect(status().isBadRequest());

        List<AuditLogs> auditLogsList = auditLogsRepository.findAll();
        assertThat(auditLogsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditLogsRepository.findAll().size();
        // set the field null
        auditLogs.setVersion(null);

        // Create the AuditLogs, which fails.

        restAuditLogsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(auditLogs))
            )
            .andExpect(status().isBadRequest());

        List<AuditLogs> auditLogsList = auditLogsRepository.findAll();
        assertThat(auditLogsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAuditLogs() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        // Get all the auditLogsList
        restAuditLogsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auditLogs.getId().intValue())))
            .andExpect(jsonPath("$.[*].event").value(hasItem(DEFAULT_EVENT)))
            .andExpect(jsonPath("$.[*].eventTime").value(hasItem(DEFAULT_EVENT_TIME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].oldChange").value(hasItem(DEFAULT_OLD_CHANGE)))
            .andExpect(jsonPath("$.[*].newChange").value(hasItem(DEFAULT_NEW_CHANGE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getAuditLogs() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        // Get the auditLogs
        restAuditLogsMockMvc
            .perform(get(ENTITY_API_URL_ID, auditLogs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(auditLogs.getId().intValue()))
            .andExpect(jsonPath("$.event").value(DEFAULT_EVENT))
            .andExpect(jsonPath("$.eventTime").value(DEFAULT_EVENT_TIME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.oldChange").value(DEFAULT_OLD_CHANGE))
            .andExpect(jsonPath("$.newChange").value(DEFAULT_NEW_CHANGE))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getAuditLogsByIdFiltering() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        Long id = auditLogs.getId();

        defaultAuditLogsShouldBeFound("id.equals=" + id);
        defaultAuditLogsShouldNotBeFound("id.notEquals=" + id);

        defaultAuditLogsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAuditLogsShouldNotBeFound("id.greaterThan=" + id);

        defaultAuditLogsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAuditLogsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAuditLogsByEventIsEqualToSomething() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        // Get all the auditLogsList where event equals to DEFAULT_EVENT
        defaultAuditLogsShouldBeFound("event.equals=" + DEFAULT_EVENT);

        // Get all the auditLogsList where event equals to UPDATED_EVENT
        defaultAuditLogsShouldNotBeFound("event.equals=" + UPDATED_EVENT);
    }

    @Test
    @Transactional
    void getAllAuditLogsByEventIsInShouldWork() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        // Get all the auditLogsList where event in DEFAULT_EVENT or UPDATED_EVENT
        defaultAuditLogsShouldBeFound("event.in=" + DEFAULT_EVENT + "," + UPDATED_EVENT);

        // Get all the auditLogsList where event equals to UPDATED_EVENT
        defaultAuditLogsShouldNotBeFound("event.in=" + UPDATED_EVENT);
    }

    @Test
    @Transactional
    void getAllAuditLogsByEventIsNullOrNotNull() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        // Get all the auditLogsList where event is not null
        defaultAuditLogsShouldBeFound("event.specified=true");

        // Get all the auditLogsList where event is null
        defaultAuditLogsShouldNotBeFound("event.specified=false");
    }

    @Test
    @Transactional
    void getAllAuditLogsByEventContainsSomething() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        // Get all the auditLogsList where event contains DEFAULT_EVENT
        defaultAuditLogsShouldBeFound("event.contains=" + DEFAULT_EVENT);

        // Get all the auditLogsList where event contains UPDATED_EVENT
        defaultAuditLogsShouldNotBeFound("event.contains=" + UPDATED_EVENT);
    }

    @Test
    @Transactional
    void getAllAuditLogsByEventNotContainsSomething() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        // Get all the auditLogsList where event does not contain DEFAULT_EVENT
        defaultAuditLogsShouldNotBeFound("event.doesNotContain=" + DEFAULT_EVENT);

        // Get all the auditLogsList where event does not contain UPDATED_EVENT
        defaultAuditLogsShouldBeFound("event.doesNotContain=" + UPDATED_EVENT);
    }

    @Test
    @Transactional
    void getAllAuditLogsByEventTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        // Get all the auditLogsList where eventTime equals to DEFAULT_EVENT_TIME
        defaultAuditLogsShouldBeFound("eventTime.equals=" + DEFAULT_EVENT_TIME);

        // Get all the auditLogsList where eventTime equals to UPDATED_EVENT_TIME
        defaultAuditLogsShouldNotBeFound("eventTime.equals=" + UPDATED_EVENT_TIME);
    }

    @Test
    @Transactional
    void getAllAuditLogsByEventTimeIsInShouldWork() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        // Get all the auditLogsList where eventTime in DEFAULT_EVENT_TIME or UPDATED_EVENT_TIME
        defaultAuditLogsShouldBeFound("eventTime.in=" + DEFAULT_EVENT_TIME + "," + UPDATED_EVENT_TIME);

        // Get all the auditLogsList where eventTime equals to UPDATED_EVENT_TIME
        defaultAuditLogsShouldNotBeFound("eventTime.in=" + UPDATED_EVENT_TIME);
    }

    @Test
    @Transactional
    void getAllAuditLogsByEventTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        // Get all the auditLogsList where eventTime is not null
        defaultAuditLogsShouldBeFound("eventTime.specified=true");

        // Get all the auditLogsList where eventTime is null
        defaultAuditLogsShouldNotBeFound("eventTime.specified=false");
    }

    @Test
    @Transactional
    void getAllAuditLogsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        // Get all the auditLogsList where description equals to DEFAULT_DESCRIPTION
        defaultAuditLogsShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the auditLogsList where description equals to UPDATED_DESCRIPTION
        defaultAuditLogsShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAuditLogsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        // Get all the auditLogsList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultAuditLogsShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the auditLogsList where description equals to UPDATED_DESCRIPTION
        defaultAuditLogsShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAuditLogsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        // Get all the auditLogsList where description is not null
        defaultAuditLogsShouldBeFound("description.specified=true");

        // Get all the auditLogsList where description is null
        defaultAuditLogsShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllAuditLogsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        // Get all the auditLogsList where description contains DEFAULT_DESCRIPTION
        defaultAuditLogsShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the auditLogsList where description contains UPDATED_DESCRIPTION
        defaultAuditLogsShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAuditLogsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        // Get all the auditLogsList where description does not contain DEFAULT_DESCRIPTION
        defaultAuditLogsShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the auditLogsList where description does not contain UPDATED_DESCRIPTION
        defaultAuditLogsShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAuditLogsByOldChangeIsEqualToSomething() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        // Get all the auditLogsList where oldChange equals to DEFAULT_OLD_CHANGE
        defaultAuditLogsShouldBeFound("oldChange.equals=" + DEFAULT_OLD_CHANGE);

        // Get all the auditLogsList where oldChange equals to UPDATED_OLD_CHANGE
        defaultAuditLogsShouldNotBeFound("oldChange.equals=" + UPDATED_OLD_CHANGE);
    }

    @Test
    @Transactional
    void getAllAuditLogsByOldChangeIsInShouldWork() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        // Get all the auditLogsList where oldChange in DEFAULT_OLD_CHANGE or UPDATED_OLD_CHANGE
        defaultAuditLogsShouldBeFound("oldChange.in=" + DEFAULT_OLD_CHANGE + "," + UPDATED_OLD_CHANGE);

        // Get all the auditLogsList where oldChange equals to UPDATED_OLD_CHANGE
        defaultAuditLogsShouldNotBeFound("oldChange.in=" + UPDATED_OLD_CHANGE);
    }

    @Test
    @Transactional
    void getAllAuditLogsByOldChangeIsNullOrNotNull() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        // Get all the auditLogsList where oldChange is not null
        defaultAuditLogsShouldBeFound("oldChange.specified=true");

        // Get all the auditLogsList where oldChange is null
        defaultAuditLogsShouldNotBeFound("oldChange.specified=false");
    }

    @Test
    @Transactional
    void getAllAuditLogsByOldChangeContainsSomething() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        // Get all the auditLogsList where oldChange contains DEFAULT_OLD_CHANGE
        defaultAuditLogsShouldBeFound("oldChange.contains=" + DEFAULT_OLD_CHANGE);

        // Get all the auditLogsList where oldChange contains UPDATED_OLD_CHANGE
        defaultAuditLogsShouldNotBeFound("oldChange.contains=" + UPDATED_OLD_CHANGE);
    }

    @Test
    @Transactional
    void getAllAuditLogsByOldChangeNotContainsSomething() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        // Get all the auditLogsList where oldChange does not contain DEFAULT_OLD_CHANGE
        defaultAuditLogsShouldNotBeFound("oldChange.doesNotContain=" + DEFAULT_OLD_CHANGE);

        // Get all the auditLogsList where oldChange does not contain UPDATED_OLD_CHANGE
        defaultAuditLogsShouldBeFound("oldChange.doesNotContain=" + UPDATED_OLD_CHANGE);
    }

    @Test
    @Transactional
    void getAllAuditLogsByNewChangeIsEqualToSomething() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        // Get all the auditLogsList where newChange equals to DEFAULT_NEW_CHANGE
        defaultAuditLogsShouldBeFound("newChange.equals=" + DEFAULT_NEW_CHANGE);

        // Get all the auditLogsList where newChange equals to UPDATED_NEW_CHANGE
        defaultAuditLogsShouldNotBeFound("newChange.equals=" + UPDATED_NEW_CHANGE);
    }

    @Test
    @Transactional
    void getAllAuditLogsByNewChangeIsInShouldWork() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        // Get all the auditLogsList where newChange in DEFAULT_NEW_CHANGE or UPDATED_NEW_CHANGE
        defaultAuditLogsShouldBeFound("newChange.in=" + DEFAULT_NEW_CHANGE + "," + UPDATED_NEW_CHANGE);

        // Get all the auditLogsList where newChange equals to UPDATED_NEW_CHANGE
        defaultAuditLogsShouldNotBeFound("newChange.in=" + UPDATED_NEW_CHANGE);
    }

    @Test
    @Transactional
    void getAllAuditLogsByNewChangeIsNullOrNotNull() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        // Get all the auditLogsList where newChange is not null
        defaultAuditLogsShouldBeFound("newChange.specified=true");

        // Get all the auditLogsList where newChange is null
        defaultAuditLogsShouldNotBeFound("newChange.specified=false");
    }

    @Test
    @Transactional
    void getAllAuditLogsByNewChangeContainsSomething() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        // Get all the auditLogsList where newChange contains DEFAULT_NEW_CHANGE
        defaultAuditLogsShouldBeFound("newChange.contains=" + DEFAULT_NEW_CHANGE);

        // Get all the auditLogsList where newChange contains UPDATED_NEW_CHANGE
        defaultAuditLogsShouldNotBeFound("newChange.contains=" + UPDATED_NEW_CHANGE);
    }

    @Test
    @Transactional
    void getAllAuditLogsByNewChangeNotContainsSomething() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        // Get all the auditLogsList where newChange does not contain DEFAULT_NEW_CHANGE
        defaultAuditLogsShouldNotBeFound("newChange.doesNotContain=" + DEFAULT_NEW_CHANGE);

        // Get all the auditLogsList where newChange does not contain UPDATED_NEW_CHANGE
        defaultAuditLogsShouldBeFound("newChange.doesNotContain=" + UPDATED_NEW_CHANGE);
    }

    @Test
    @Transactional
    void getAllAuditLogsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        // Get all the auditLogsList where createdAt equals to DEFAULT_CREATED_AT
        defaultAuditLogsShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the auditLogsList where createdAt equals to UPDATED_CREATED_AT
        defaultAuditLogsShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllAuditLogsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        // Get all the auditLogsList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultAuditLogsShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the auditLogsList where createdAt equals to UPDATED_CREATED_AT
        defaultAuditLogsShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllAuditLogsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        // Get all the auditLogsList where createdAt is not null
        defaultAuditLogsShouldBeFound("createdAt.specified=true");

        // Get all the auditLogsList where createdAt is null
        defaultAuditLogsShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllAuditLogsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        // Get all the auditLogsList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultAuditLogsShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the auditLogsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultAuditLogsShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllAuditLogsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        // Get all the auditLogsList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultAuditLogsShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the auditLogsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultAuditLogsShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllAuditLogsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        // Get all the auditLogsList where updatedAt is not null
        defaultAuditLogsShouldBeFound("updatedAt.specified=true");

        // Get all the auditLogsList where updatedAt is null
        defaultAuditLogsShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllAuditLogsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        // Get all the auditLogsList where version equals to DEFAULT_VERSION
        defaultAuditLogsShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the auditLogsList where version equals to UPDATED_VERSION
        defaultAuditLogsShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllAuditLogsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        // Get all the auditLogsList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultAuditLogsShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the auditLogsList where version equals to UPDATED_VERSION
        defaultAuditLogsShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllAuditLogsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        // Get all the auditLogsList where version is not null
        defaultAuditLogsShouldBeFound("version.specified=true");

        // Get all the auditLogsList where version is null
        defaultAuditLogsShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllAuditLogsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        // Get all the auditLogsList where version is greater than or equal to DEFAULT_VERSION
        defaultAuditLogsShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the auditLogsList where version is greater than or equal to UPDATED_VERSION
        defaultAuditLogsShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllAuditLogsByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        // Get all the auditLogsList where version is less than or equal to DEFAULT_VERSION
        defaultAuditLogsShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the auditLogsList where version is less than or equal to SMALLER_VERSION
        defaultAuditLogsShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllAuditLogsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        // Get all the auditLogsList where version is less than DEFAULT_VERSION
        defaultAuditLogsShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the auditLogsList where version is less than UPDATED_VERSION
        defaultAuditLogsShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllAuditLogsByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        // Get all the auditLogsList where version is greater than DEFAULT_VERSION
        defaultAuditLogsShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the auditLogsList where version is greater than SMALLER_VERSION
        defaultAuditLogsShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAuditLogsShouldBeFound(String filter) throws Exception {
        restAuditLogsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auditLogs.getId().intValue())))
            .andExpect(jsonPath("$.[*].event").value(hasItem(DEFAULT_EVENT)))
            .andExpect(jsonPath("$.[*].eventTime").value(hasItem(DEFAULT_EVENT_TIME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].oldChange").value(hasItem(DEFAULT_OLD_CHANGE)))
            .andExpect(jsonPath("$.[*].newChange").value(hasItem(DEFAULT_NEW_CHANGE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restAuditLogsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAuditLogsShouldNotBeFound(String filter) throws Exception {
        restAuditLogsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAuditLogsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAuditLogs() throws Exception {
        // Get the auditLogs
        restAuditLogsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAuditLogs() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        int databaseSizeBeforeUpdate = auditLogsRepository.findAll().size();

        // Update the auditLogs
        AuditLogs updatedAuditLogs = auditLogsRepository.findById(auditLogs.getId()).get();
        // Disconnect from session so that the updates on updatedAuditLogs are not directly saved in db
        em.detach(updatedAuditLogs);
        updatedAuditLogs
            .event(UPDATED_EVENT)
            .eventTime(UPDATED_EVENT_TIME)
            .description(UPDATED_DESCRIPTION)
            .oldChange(UPDATED_OLD_CHANGE)
            .newChange(UPDATED_NEW_CHANGE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .version(UPDATED_VERSION);

        restAuditLogsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAuditLogs.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAuditLogs))
            )
            .andExpect(status().isOk());

        // Validate the AuditLogs in the database
        List<AuditLogs> auditLogsList = auditLogsRepository.findAll();
        assertThat(auditLogsList).hasSize(databaseSizeBeforeUpdate);
        AuditLogs testAuditLogs = auditLogsList.get(auditLogsList.size() - 1);
        assertThat(testAuditLogs.getEvent()).isEqualTo(UPDATED_EVENT);
        assertThat(testAuditLogs.getEventTime()).isEqualTo(UPDATED_EVENT_TIME);
        assertThat(testAuditLogs.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAuditLogs.getOldChange()).isEqualTo(UPDATED_OLD_CHANGE);
        assertThat(testAuditLogs.getNewChange()).isEqualTo(UPDATED_NEW_CHANGE);
        assertThat(testAuditLogs.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testAuditLogs.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testAuditLogs.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingAuditLogs() throws Exception {
        int databaseSizeBeforeUpdate = auditLogsRepository.findAll().size();
        auditLogs.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuditLogsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, auditLogs.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(auditLogs))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuditLogs in the database
        List<AuditLogs> auditLogsList = auditLogsRepository.findAll();
        assertThat(auditLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAuditLogs() throws Exception {
        int databaseSizeBeforeUpdate = auditLogsRepository.findAll().size();
        auditLogs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuditLogsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(auditLogs))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuditLogs in the database
        List<AuditLogs> auditLogsList = auditLogsRepository.findAll();
        assertThat(auditLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAuditLogs() throws Exception {
        int databaseSizeBeforeUpdate = auditLogsRepository.findAll().size();
        auditLogs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuditLogsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(auditLogs))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AuditLogs in the database
        List<AuditLogs> auditLogsList = auditLogsRepository.findAll();
        assertThat(auditLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAuditLogsWithPatch() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        int databaseSizeBeforeUpdate = auditLogsRepository.findAll().size();

        // Update the auditLogs using partial update
        AuditLogs partialUpdatedAuditLogs = new AuditLogs();
        partialUpdatedAuditLogs.setId(auditLogs.getId());

        partialUpdatedAuditLogs.updatedAt(UPDATED_UPDATED_AT).version(UPDATED_VERSION);

        restAuditLogsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuditLogs.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAuditLogs))
            )
            .andExpect(status().isOk());

        // Validate the AuditLogs in the database
        List<AuditLogs> auditLogsList = auditLogsRepository.findAll();
        assertThat(auditLogsList).hasSize(databaseSizeBeforeUpdate);
        AuditLogs testAuditLogs = auditLogsList.get(auditLogsList.size() - 1);
        assertThat(testAuditLogs.getEvent()).isEqualTo(DEFAULT_EVENT);
        assertThat(testAuditLogs.getEventTime()).isEqualTo(DEFAULT_EVENT_TIME);
        assertThat(testAuditLogs.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAuditLogs.getOldChange()).isEqualTo(DEFAULT_OLD_CHANGE);
        assertThat(testAuditLogs.getNewChange()).isEqualTo(DEFAULT_NEW_CHANGE);
        assertThat(testAuditLogs.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testAuditLogs.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testAuditLogs.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateAuditLogsWithPatch() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        int databaseSizeBeforeUpdate = auditLogsRepository.findAll().size();

        // Update the auditLogs using partial update
        AuditLogs partialUpdatedAuditLogs = new AuditLogs();
        partialUpdatedAuditLogs.setId(auditLogs.getId());

        partialUpdatedAuditLogs
            .event(UPDATED_EVENT)
            .eventTime(UPDATED_EVENT_TIME)
            .description(UPDATED_DESCRIPTION)
            .oldChange(UPDATED_OLD_CHANGE)
            .newChange(UPDATED_NEW_CHANGE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .version(UPDATED_VERSION);

        restAuditLogsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuditLogs.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAuditLogs))
            )
            .andExpect(status().isOk());

        // Validate the AuditLogs in the database
        List<AuditLogs> auditLogsList = auditLogsRepository.findAll();
        assertThat(auditLogsList).hasSize(databaseSizeBeforeUpdate);
        AuditLogs testAuditLogs = auditLogsList.get(auditLogsList.size() - 1);
        assertThat(testAuditLogs.getEvent()).isEqualTo(UPDATED_EVENT);
        assertThat(testAuditLogs.getEventTime()).isEqualTo(UPDATED_EVENT_TIME);
        assertThat(testAuditLogs.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAuditLogs.getOldChange()).isEqualTo(UPDATED_OLD_CHANGE);
        assertThat(testAuditLogs.getNewChange()).isEqualTo(UPDATED_NEW_CHANGE);
        assertThat(testAuditLogs.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testAuditLogs.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testAuditLogs.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingAuditLogs() throws Exception {
        int databaseSizeBeforeUpdate = auditLogsRepository.findAll().size();
        auditLogs.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuditLogsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, auditLogs.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(auditLogs))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuditLogs in the database
        List<AuditLogs> auditLogsList = auditLogsRepository.findAll();
        assertThat(auditLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAuditLogs() throws Exception {
        int databaseSizeBeforeUpdate = auditLogsRepository.findAll().size();
        auditLogs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuditLogsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(auditLogs))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuditLogs in the database
        List<AuditLogs> auditLogsList = auditLogsRepository.findAll();
        assertThat(auditLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAuditLogs() throws Exception {
        int databaseSizeBeforeUpdate = auditLogsRepository.findAll().size();
        auditLogs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuditLogsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(auditLogs))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AuditLogs in the database
        List<AuditLogs> auditLogsList = auditLogsRepository.findAll();
        assertThat(auditLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAuditLogs() throws Exception {
        // Initialize the database
        auditLogsRepository.saveAndFlush(auditLogs);

        int databaseSizeBeforeDelete = auditLogsRepository.findAll().size();

        // Delete the auditLogs
        restAuditLogsMockMvc
            .perform(delete(ENTITY_API_URL_ID, auditLogs.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AuditLogs> auditLogsList = auditLogsRepository.findAll();
        assertThat(auditLogsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
