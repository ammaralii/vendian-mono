package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.NotificationSentEmailLogs;
import com.venturedive.vendian_mono.domain.NotificationTemplates;
import com.venturedive.vendian_mono.repository.NotificationSentEmailLogsRepository;
import com.venturedive.vendian_mono.service.criteria.NotificationSentEmailLogsCriteria;
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
 * Integration tests for the {@link NotificationSentEmailLogsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NotificationSentEmailLogsResourceIT {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;
    private static final Integer SMALLER_VERSION = 1 - 1;

    private static final String ENTITY_API_URL = "/api/notification-sent-email-logs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NotificationSentEmailLogsRepository notificationSentEmailLogsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNotificationSentEmailLogsMockMvc;

    private NotificationSentEmailLogs notificationSentEmailLogs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotificationSentEmailLogs createEntity(EntityManager em) {
        NotificationSentEmailLogs notificationSentEmailLogs = new NotificationSentEmailLogs()
            .email(DEFAULT_EMAIL)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT)
            .version(DEFAULT_VERSION);
        // Add required entity
        NotificationTemplates notificationTemplates;
        if (TestUtil.findAll(em, NotificationTemplates.class).isEmpty()) {
            notificationTemplates = NotificationTemplatesResourceIT.createEntity(em);
            em.persist(notificationTemplates);
            em.flush();
        } else {
            notificationTemplates = TestUtil.findAll(em, NotificationTemplates.class).get(0);
        }
        notificationSentEmailLogs.setNotificationTemplate(notificationTemplates);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        notificationSentEmailLogs.setRecipient(employees);
        return notificationSentEmailLogs;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotificationSentEmailLogs createUpdatedEntity(EntityManager em) {
        NotificationSentEmailLogs notificationSentEmailLogs = new NotificationSentEmailLogs()
            .email(UPDATED_EMAIL)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);
        // Add required entity
        NotificationTemplates notificationTemplates;
        if (TestUtil.findAll(em, NotificationTemplates.class).isEmpty()) {
            notificationTemplates = NotificationTemplatesResourceIT.createUpdatedEntity(em);
            em.persist(notificationTemplates);
            em.flush();
        } else {
            notificationTemplates = TestUtil.findAll(em, NotificationTemplates.class).get(0);
        }
        notificationSentEmailLogs.setNotificationTemplate(notificationTemplates);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createUpdatedEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        notificationSentEmailLogs.setRecipient(employees);
        return notificationSentEmailLogs;
    }

    @BeforeEach
    public void initTest() {
        notificationSentEmailLogs = createEntity(em);
    }

    @Test
    @Transactional
    void createNotificationSentEmailLogs() throws Exception {
        int databaseSizeBeforeCreate = notificationSentEmailLogsRepository.findAll().size();
        // Create the NotificationSentEmailLogs
        restNotificationSentEmailLogsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationSentEmailLogs))
            )
            .andExpect(status().isCreated());

        // Validate the NotificationSentEmailLogs in the database
        List<NotificationSentEmailLogs> notificationSentEmailLogsList = notificationSentEmailLogsRepository.findAll();
        assertThat(notificationSentEmailLogsList).hasSize(databaseSizeBeforeCreate + 1);
        NotificationSentEmailLogs testNotificationSentEmailLogs = notificationSentEmailLogsList.get(
            notificationSentEmailLogsList.size() - 1
        );
        assertThat(testNotificationSentEmailLogs.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testNotificationSentEmailLogs.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testNotificationSentEmailLogs.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testNotificationSentEmailLogs.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testNotificationSentEmailLogs.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createNotificationSentEmailLogsWithExistingId() throws Exception {
        // Create the NotificationSentEmailLogs with an existing ID
        notificationSentEmailLogs.setId(1L);

        int databaseSizeBeforeCreate = notificationSentEmailLogsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotificationSentEmailLogsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationSentEmailLogs))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationSentEmailLogs in the database
        List<NotificationSentEmailLogs> notificationSentEmailLogsList = notificationSentEmailLogsRepository.findAll();
        assertThat(notificationSentEmailLogsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationSentEmailLogsRepository.findAll().size();
        // set the field null
        notificationSentEmailLogs.setEmail(null);

        // Create the NotificationSentEmailLogs, which fails.

        restNotificationSentEmailLogsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationSentEmailLogs))
            )
            .andExpect(status().isBadRequest());

        List<NotificationSentEmailLogs> notificationSentEmailLogsList = notificationSentEmailLogsRepository.findAll();
        assertThat(notificationSentEmailLogsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationSentEmailLogsRepository.findAll().size();
        // set the field null
        notificationSentEmailLogs.setCreatedAt(null);

        // Create the NotificationSentEmailLogs, which fails.

        restNotificationSentEmailLogsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationSentEmailLogs))
            )
            .andExpect(status().isBadRequest());

        List<NotificationSentEmailLogs> notificationSentEmailLogsList = notificationSentEmailLogsRepository.findAll();
        assertThat(notificationSentEmailLogsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationSentEmailLogsRepository.findAll().size();
        // set the field null
        notificationSentEmailLogs.setUpdatedAt(null);

        // Create the NotificationSentEmailLogs, which fails.

        restNotificationSentEmailLogsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationSentEmailLogs))
            )
            .andExpect(status().isBadRequest());

        List<NotificationSentEmailLogs> notificationSentEmailLogsList = notificationSentEmailLogsRepository.findAll();
        assertThat(notificationSentEmailLogsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationSentEmailLogsRepository.findAll().size();
        // set the field null
        notificationSentEmailLogs.setVersion(null);

        // Create the NotificationSentEmailLogs, which fails.

        restNotificationSentEmailLogsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationSentEmailLogs))
            )
            .andExpect(status().isBadRequest());

        List<NotificationSentEmailLogs> notificationSentEmailLogsList = notificationSentEmailLogsRepository.findAll();
        assertThat(notificationSentEmailLogsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNotificationSentEmailLogs() throws Exception {
        // Initialize the database
        notificationSentEmailLogsRepository.saveAndFlush(notificationSentEmailLogs);

        // Get all the notificationSentEmailLogsList
        restNotificationSentEmailLogsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notificationSentEmailLogs.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getNotificationSentEmailLogs() throws Exception {
        // Initialize the database
        notificationSentEmailLogsRepository.saveAndFlush(notificationSentEmailLogs);

        // Get the notificationSentEmailLogs
        restNotificationSentEmailLogsMockMvc
            .perform(get(ENTITY_API_URL_ID, notificationSentEmailLogs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(notificationSentEmailLogs.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getNotificationSentEmailLogsByIdFiltering() throws Exception {
        // Initialize the database
        notificationSentEmailLogsRepository.saveAndFlush(notificationSentEmailLogs);

        Long id = notificationSentEmailLogs.getId();

        defaultNotificationSentEmailLogsShouldBeFound("id.equals=" + id);
        defaultNotificationSentEmailLogsShouldNotBeFound("id.notEquals=" + id);

        defaultNotificationSentEmailLogsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNotificationSentEmailLogsShouldNotBeFound("id.greaterThan=" + id);

        defaultNotificationSentEmailLogsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNotificationSentEmailLogsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNotificationSentEmailLogsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationSentEmailLogsRepository.saveAndFlush(notificationSentEmailLogs);

        // Get all the notificationSentEmailLogsList where email equals to DEFAULT_EMAIL
        defaultNotificationSentEmailLogsShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the notificationSentEmailLogsList where email equals to UPDATED_EMAIL
        defaultNotificationSentEmailLogsShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllNotificationSentEmailLogsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        notificationSentEmailLogsRepository.saveAndFlush(notificationSentEmailLogs);

        // Get all the notificationSentEmailLogsList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultNotificationSentEmailLogsShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the notificationSentEmailLogsList where email equals to UPDATED_EMAIL
        defaultNotificationSentEmailLogsShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllNotificationSentEmailLogsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationSentEmailLogsRepository.saveAndFlush(notificationSentEmailLogs);

        // Get all the notificationSentEmailLogsList where email is not null
        defaultNotificationSentEmailLogsShouldBeFound("email.specified=true");

        // Get all the notificationSentEmailLogsList where email is null
        defaultNotificationSentEmailLogsShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationSentEmailLogsByEmailContainsSomething() throws Exception {
        // Initialize the database
        notificationSentEmailLogsRepository.saveAndFlush(notificationSentEmailLogs);

        // Get all the notificationSentEmailLogsList where email contains DEFAULT_EMAIL
        defaultNotificationSentEmailLogsShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the notificationSentEmailLogsList where email contains UPDATED_EMAIL
        defaultNotificationSentEmailLogsShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllNotificationSentEmailLogsByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        notificationSentEmailLogsRepository.saveAndFlush(notificationSentEmailLogs);

        // Get all the notificationSentEmailLogsList where email does not contain DEFAULT_EMAIL
        defaultNotificationSentEmailLogsShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the notificationSentEmailLogsList where email does not contain UPDATED_EMAIL
        defaultNotificationSentEmailLogsShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllNotificationSentEmailLogsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationSentEmailLogsRepository.saveAndFlush(notificationSentEmailLogs);

        // Get all the notificationSentEmailLogsList where createdAt equals to DEFAULT_CREATED_AT
        defaultNotificationSentEmailLogsShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the notificationSentEmailLogsList where createdAt equals to UPDATED_CREATED_AT
        defaultNotificationSentEmailLogsShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllNotificationSentEmailLogsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        notificationSentEmailLogsRepository.saveAndFlush(notificationSentEmailLogs);

        // Get all the notificationSentEmailLogsList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultNotificationSentEmailLogsShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the notificationSentEmailLogsList where createdAt equals to UPDATED_CREATED_AT
        defaultNotificationSentEmailLogsShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllNotificationSentEmailLogsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationSentEmailLogsRepository.saveAndFlush(notificationSentEmailLogs);

        // Get all the notificationSentEmailLogsList where createdAt is not null
        defaultNotificationSentEmailLogsShouldBeFound("createdAt.specified=true");

        // Get all the notificationSentEmailLogsList where createdAt is null
        defaultNotificationSentEmailLogsShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationSentEmailLogsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationSentEmailLogsRepository.saveAndFlush(notificationSentEmailLogs);

        // Get all the notificationSentEmailLogsList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultNotificationSentEmailLogsShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the notificationSentEmailLogsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultNotificationSentEmailLogsShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllNotificationSentEmailLogsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        notificationSentEmailLogsRepository.saveAndFlush(notificationSentEmailLogs);

        // Get all the notificationSentEmailLogsList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultNotificationSentEmailLogsShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the notificationSentEmailLogsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultNotificationSentEmailLogsShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllNotificationSentEmailLogsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationSentEmailLogsRepository.saveAndFlush(notificationSentEmailLogs);

        // Get all the notificationSentEmailLogsList where updatedAt is not null
        defaultNotificationSentEmailLogsShouldBeFound("updatedAt.specified=true");

        // Get all the notificationSentEmailLogsList where updatedAt is null
        defaultNotificationSentEmailLogsShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationSentEmailLogsByDeletedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationSentEmailLogsRepository.saveAndFlush(notificationSentEmailLogs);

        // Get all the notificationSentEmailLogsList where deletedAt equals to DEFAULT_DELETED_AT
        defaultNotificationSentEmailLogsShouldBeFound("deletedAt.equals=" + DEFAULT_DELETED_AT);

        // Get all the notificationSentEmailLogsList where deletedAt equals to UPDATED_DELETED_AT
        defaultNotificationSentEmailLogsShouldNotBeFound("deletedAt.equals=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllNotificationSentEmailLogsByDeletedAtIsInShouldWork() throws Exception {
        // Initialize the database
        notificationSentEmailLogsRepository.saveAndFlush(notificationSentEmailLogs);

        // Get all the notificationSentEmailLogsList where deletedAt in DEFAULT_DELETED_AT or UPDATED_DELETED_AT
        defaultNotificationSentEmailLogsShouldBeFound("deletedAt.in=" + DEFAULT_DELETED_AT + "," + UPDATED_DELETED_AT);

        // Get all the notificationSentEmailLogsList where deletedAt equals to UPDATED_DELETED_AT
        defaultNotificationSentEmailLogsShouldNotBeFound("deletedAt.in=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllNotificationSentEmailLogsByDeletedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationSentEmailLogsRepository.saveAndFlush(notificationSentEmailLogs);

        // Get all the notificationSentEmailLogsList where deletedAt is not null
        defaultNotificationSentEmailLogsShouldBeFound("deletedAt.specified=true");

        // Get all the notificationSentEmailLogsList where deletedAt is null
        defaultNotificationSentEmailLogsShouldNotBeFound("deletedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationSentEmailLogsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationSentEmailLogsRepository.saveAndFlush(notificationSentEmailLogs);

        // Get all the notificationSentEmailLogsList where version equals to DEFAULT_VERSION
        defaultNotificationSentEmailLogsShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the notificationSentEmailLogsList where version equals to UPDATED_VERSION
        defaultNotificationSentEmailLogsShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllNotificationSentEmailLogsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        notificationSentEmailLogsRepository.saveAndFlush(notificationSentEmailLogs);

        // Get all the notificationSentEmailLogsList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultNotificationSentEmailLogsShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the notificationSentEmailLogsList where version equals to UPDATED_VERSION
        defaultNotificationSentEmailLogsShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllNotificationSentEmailLogsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationSentEmailLogsRepository.saveAndFlush(notificationSentEmailLogs);

        // Get all the notificationSentEmailLogsList where version is not null
        defaultNotificationSentEmailLogsShouldBeFound("version.specified=true");

        // Get all the notificationSentEmailLogsList where version is null
        defaultNotificationSentEmailLogsShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationSentEmailLogsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notificationSentEmailLogsRepository.saveAndFlush(notificationSentEmailLogs);

        // Get all the notificationSentEmailLogsList where version is greater than or equal to DEFAULT_VERSION
        defaultNotificationSentEmailLogsShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the notificationSentEmailLogsList where version is greater than or equal to UPDATED_VERSION
        defaultNotificationSentEmailLogsShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllNotificationSentEmailLogsByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notificationSentEmailLogsRepository.saveAndFlush(notificationSentEmailLogs);

        // Get all the notificationSentEmailLogsList where version is less than or equal to DEFAULT_VERSION
        defaultNotificationSentEmailLogsShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the notificationSentEmailLogsList where version is less than or equal to SMALLER_VERSION
        defaultNotificationSentEmailLogsShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllNotificationSentEmailLogsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        notificationSentEmailLogsRepository.saveAndFlush(notificationSentEmailLogs);

        // Get all the notificationSentEmailLogsList where version is less than DEFAULT_VERSION
        defaultNotificationSentEmailLogsShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the notificationSentEmailLogsList where version is less than UPDATED_VERSION
        defaultNotificationSentEmailLogsShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllNotificationSentEmailLogsByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        notificationSentEmailLogsRepository.saveAndFlush(notificationSentEmailLogs);

        // Get all the notificationSentEmailLogsList where version is greater than DEFAULT_VERSION
        defaultNotificationSentEmailLogsShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the notificationSentEmailLogsList where version is greater than SMALLER_VERSION
        defaultNotificationSentEmailLogsShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllNotificationSentEmailLogsByNotificationTemplateIsEqualToSomething() throws Exception {
        NotificationTemplates notificationTemplate;
        if (TestUtil.findAll(em, NotificationTemplates.class).isEmpty()) {
            notificationSentEmailLogsRepository.saveAndFlush(notificationSentEmailLogs);
            notificationTemplate = NotificationTemplatesResourceIT.createEntity(em);
        } else {
            notificationTemplate = TestUtil.findAll(em, NotificationTemplates.class).get(0);
        }
        em.persist(notificationTemplate);
        em.flush();
        notificationSentEmailLogs.setNotificationTemplate(notificationTemplate);
        notificationSentEmailLogsRepository.saveAndFlush(notificationSentEmailLogs);
        Long notificationTemplateId = notificationTemplate.getId();

        // Get all the notificationSentEmailLogsList where notificationTemplate equals to notificationTemplateId
        defaultNotificationSentEmailLogsShouldBeFound("notificationTemplateId.equals=" + notificationTemplateId);

        // Get all the notificationSentEmailLogsList where notificationTemplate equals to (notificationTemplateId + 1)
        defaultNotificationSentEmailLogsShouldNotBeFound("notificationTemplateId.equals=" + (notificationTemplateId + 1));
    }

    @Test
    @Transactional
    void getAllNotificationSentEmailLogsByRecipientIsEqualToSomething() throws Exception {
        Employees recipient;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            notificationSentEmailLogsRepository.saveAndFlush(notificationSentEmailLogs);
            recipient = EmployeesResourceIT.createEntity(em);
        } else {
            recipient = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(recipient);
        em.flush();
        notificationSentEmailLogs.setRecipient(recipient);
        notificationSentEmailLogsRepository.saveAndFlush(notificationSentEmailLogs);
        Long recipientId = recipient.getId();

        // Get all the notificationSentEmailLogsList where recipient equals to recipientId
        defaultNotificationSentEmailLogsShouldBeFound("recipientId.equals=" + recipientId);

        // Get all the notificationSentEmailLogsList where recipient equals to (recipientId + 1)
        defaultNotificationSentEmailLogsShouldNotBeFound("recipientId.equals=" + (recipientId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNotificationSentEmailLogsShouldBeFound(String filter) throws Exception {
        restNotificationSentEmailLogsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notificationSentEmailLogs.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restNotificationSentEmailLogsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNotificationSentEmailLogsShouldNotBeFound(String filter) throws Exception {
        restNotificationSentEmailLogsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNotificationSentEmailLogsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNotificationSentEmailLogs() throws Exception {
        // Get the notificationSentEmailLogs
        restNotificationSentEmailLogsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNotificationSentEmailLogs() throws Exception {
        // Initialize the database
        notificationSentEmailLogsRepository.saveAndFlush(notificationSentEmailLogs);

        int databaseSizeBeforeUpdate = notificationSentEmailLogsRepository.findAll().size();

        // Update the notificationSentEmailLogs
        NotificationSentEmailLogs updatedNotificationSentEmailLogs = notificationSentEmailLogsRepository
            .findById(notificationSentEmailLogs.getId())
            .get();
        // Disconnect from session so that the updates on updatedNotificationSentEmailLogs are not directly saved in db
        em.detach(updatedNotificationSentEmailLogs);
        updatedNotificationSentEmailLogs
            .email(UPDATED_EMAIL)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restNotificationSentEmailLogsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNotificationSentEmailLogs.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNotificationSentEmailLogs))
            )
            .andExpect(status().isOk());

        // Validate the NotificationSentEmailLogs in the database
        List<NotificationSentEmailLogs> notificationSentEmailLogsList = notificationSentEmailLogsRepository.findAll();
        assertThat(notificationSentEmailLogsList).hasSize(databaseSizeBeforeUpdate);
        NotificationSentEmailLogs testNotificationSentEmailLogs = notificationSentEmailLogsList.get(
            notificationSentEmailLogsList.size() - 1
        );
        assertThat(testNotificationSentEmailLogs.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testNotificationSentEmailLogs.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testNotificationSentEmailLogs.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testNotificationSentEmailLogs.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testNotificationSentEmailLogs.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingNotificationSentEmailLogs() throws Exception {
        int databaseSizeBeforeUpdate = notificationSentEmailLogsRepository.findAll().size();
        notificationSentEmailLogs.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificationSentEmailLogsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, notificationSentEmailLogs.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationSentEmailLogs))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationSentEmailLogs in the database
        List<NotificationSentEmailLogs> notificationSentEmailLogsList = notificationSentEmailLogsRepository.findAll();
        assertThat(notificationSentEmailLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNotificationSentEmailLogs() throws Exception {
        int databaseSizeBeforeUpdate = notificationSentEmailLogsRepository.findAll().size();
        notificationSentEmailLogs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationSentEmailLogsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationSentEmailLogs))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationSentEmailLogs in the database
        List<NotificationSentEmailLogs> notificationSentEmailLogsList = notificationSentEmailLogsRepository.findAll();
        assertThat(notificationSentEmailLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNotificationSentEmailLogs() throws Exception {
        int databaseSizeBeforeUpdate = notificationSentEmailLogsRepository.findAll().size();
        notificationSentEmailLogs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationSentEmailLogsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationSentEmailLogs))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NotificationSentEmailLogs in the database
        List<NotificationSentEmailLogs> notificationSentEmailLogsList = notificationSentEmailLogsRepository.findAll();
        assertThat(notificationSentEmailLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNotificationSentEmailLogsWithPatch() throws Exception {
        // Initialize the database
        notificationSentEmailLogsRepository.saveAndFlush(notificationSentEmailLogs);

        int databaseSizeBeforeUpdate = notificationSentEmailLogsRepository.findAll().size();

        // Update the notificationSentEmailLogs using partial update
        NotificationSentEmailLogs partialUpdatedNotificationSentEmailLogs = new NotificationSentEmailLogs();
        partialUpdatedNotificationSentEmailLogs.setId(notificationSentEmailLogs.getId());

        partialUpdatedNotificationSentEmailLogs.deletedAt(UPDATED_DELETED_AT);

        restNotificationSentEmailLogsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotificationSentEmailLogs.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNotificationSentEmailLogs))
            )
            .andExpect(status().isOk());

        // Validate the NotificationSentEmailLogs in the database
        List<NotificationSentEmailLogs> notificationSentEmailLogsList = notificationSentEmailLogsRepository.findAll();
        assertThat(notificationSentEmailLogsList).hasSize(databaseSizeBeforeUpdate);
        NotificationSentEmailLogs testNotificationSentEmailLogs = notificationSentEmailLogsList.get(
            notificationSentEmailLogsList.size() - 1
        );
        assertThat(testNotificationSentEmailLogs.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testNotificationSentEmailLogs.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testNotificationSentEmailLogs.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testNotificationSentEmailLogs.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testNotificationSentEmailLogs.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateNotificationSentEmailLogsWithPatch() throws Exception {
        // Initialize the database
        notificationSentEmailLogsRepository.saveAndFlush(notificationSentEmailLogs);

        int databaseSizeBeforeUpdate = notificationSentEmailLogsRepository.findAll().size();

        // Update the notificationSentEmailLogs using partial update
        NotificationSentEmailLogs partialUpdatedNotificationSentEmailLogs = new NotificationSentEmailLogs();
        partialUpdatedNotificationSentEmailLogs.setId(notificationSentEmailLogs.getId());

        partialUpdatedNotificationSentEmailLogs
            .email(UPDATED_EMAIL)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restNotificationSentEmailLogsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotificationSentEmailLogs.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNotificationSentEmailLogs))
            )
            .andExpect(status().isOk());

        // Validate the NotificationSentEmailLogs in the database
        List<NotificationSentEmailLogs> notificationSentEmailLogsList = notificationSentEmailLogsRepository.findAll();
        assertThat(notificationSentEmailLogsList).hasSize(databaseSizeBeforeUpdate);
        NotificationSentEmailLogs testNotificationSentEmailLogs = notificationSentEmailLogsList.get(
            notificationSentEmailLogsList.size() - 1
        );
        assertThat(testNotificationSentEmailLogs.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testNotificationSentEmailLogs.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testNotificationSentEmailLogs.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testNotificationSentEmailLogs.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testNotificationSentEmailLogs.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingNotificationSentEmailLogs() throws Exception {
        int databaseSizeBeforeUpdate = notificationSentEmailLogsRepository.findAll().size();
        notificationSentEmailLogs.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificationSentEmailLogsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, notificationSentEmailLogs.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notificationSentEmailLogs))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationSentEmailLogs in the database
        List<NotificationSentEmailLogs> notificationSentEmailLogsList = notificationSentEmailLogsRepository.findAll();
        assertThat(notificationSentEmailLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNotificationSentEmailLogs() throws Exception {
        int databaseSizeBeforeUpdate = notificationSentEmailLogsRepository.findAll().size();
        notificationSentEmailLogs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationSentEmailLogsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notificationSentEmailLogs))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationSentEmailLogs in the database
        List<NotificationSentEmailLogs> notificationSentEmailLogsList = notificationSentEmailLogsRepository.findAll();
        assertThat(notificationSentEmailLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNotificationSentEmailLogs() throws Exception {
        int databaseSizeBeforeUpdate = notificationSentEmailLogsRepository.findAll().size();
        notificationSentEmailLogs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationSentEmailLogsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notificationSentEmailLogs))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NotificationSentEmailLogs in the database
        List<NotificationSentEmailLogs> notificationSentEmailLogsList = notificationSentEmailLogsRepository.findAll();
        assertThat(notificationSentEmailLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNotificationSentEmailLogs() throws Exception {
        // Initialize the database
        notificationSentEmailLogsRepository.saveAndFlush(notificationSentEmailLogs);

        int databaseSizeBeforeDelete = notificationSentEmailLogsRepository.findAll().size();

        // Delete the notificationSentEmailLogs
        restNotificationSentEmailLogsMockMvc
            .perform(delete(ENTITY_API_URL_ID, notificationSentEmailLogs.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NotificationSentEmailLogs> notificationSentEmailLogsList = notificationSentEmailLogsRepository.findAll();
        assertThat(notificationSentEmailLogsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
