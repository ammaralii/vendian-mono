package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.NotificationEvents;
import com.venturedive.vendian_mono.domain.NotificationMergeFields;
import com.venturedive.vendian_mono.repository.NotificationMergeFieldsRepository;
import com.venturedive.vendian_mono.service.criteria.NotificationMergeFieldsCriteria;
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
 * Integration tests for the {@link NotificationMergeFieldsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NotificationMergeFieldsResourceIT {

    private static final String DEFAULT_FIELD = "AAAAAAAAAA";
    private static final String UPDATED_FIELD = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/notification-merge-fields";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NotificationMergeFieldsRepository notificationMergeFieldsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNotificationMergeFieldsMockMvc;

    private NotificationMergeFields notificationMergeFields;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotificationMergeFields createEntity(EntityManager em) {
        NotificationMergeFields notificationMergeFields = new NotificationMergeFields()
            .field(DEFAULT_FIELD)
            .effDate(DEFAULT_EFF_DATE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .endDate(DEFAULT_END_DATE)
            .version(DEFAULT_VERSION);
        // Add required entity
        NotificationEvents notificationEvents;
        if (TestUtil.findAll(em, NotificationEvents.class).isEmpty()) {
            notificationEvents = NotificationEventsResourceIT.createEntity(em);
            em.persist(notificationEvents);
            em.flush();
        } else {
            notificationEvents = TestUtil.findAll(em, NotificationEvents.class).get(0);
        }
        notificationMergeFields.setNotificationEvent(notificationEvents);
        return notificationMergeFields;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotificationMergeFields createUpdatedEntity(EntityManager em) {
        NotificationMergeFields notificationMergeFields = new NotificationMergeFields()
            .field(UPDATED_FIELD)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);
        // Add required entity
        NotificationEvents notificationEvents;
        if (TestUtil.findAll(em, NotificationEvents.class).isEmpty()) {
            notificationEvents = NotificationEventsResourceIT.createUpdatedEntity(em);
            em.persist(notificationEvents);
            em.flush();
        } else {
            notificationEvents = TestUtil.findAll(em, NotificationEvents.class).get(0);
        }
        notificationMergeFields.setNotificationEvent(notificationEvents);
        return notificationMergeFields;
    }

    @BeforeEach
    public void initTest() {
        notificationMergeFields = createEntity(em);
    }

    @Test
    @Transactional
    void createNotificationMergeFields() throws Exception {
        int databaseSizeBeforeCreate = notificationMergeFieldsRepository.findAll().size();
        // Create the NotificationMergeFields
        restNotificationMergeFieldsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationMergeFields))
            )
            .andExpect(status().isCreated());

        // Validate the NotificationMergeFields in the database
        List<NotificationMergeFields> notificationMergeFieldsList = notificationMergeFieldsRepository.findAll();
        assertThat(notificationMergeFieldsList).hasSize(databaseSizeBeforeCreate + 1);
        NotificationMergeFields testNotificationMergeFields = notificationMergeFieldsList.get(notificationMergeFieldsList.size() - 1);
        assertThat(testNotificationMergeFields.getField()).isEqualTo(DEFAULT_FIELD);
        assertThat(testNotificationMergeFields.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testNotificationMergeFields.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testNotificationMergeFields.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testNotificationMergeFields.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testNotificationMergeFields.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createNotificationMergeFieldsWithExistingId() throws Exception {
        // Create the NotificationMergeFields with an existing ID
        notificationMergeFields.setId(1L);

        int databaseSizeBeforeCreate = notificationMergeFieldsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotificationMergeFieldsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationMergeFields))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationMergeFields in the database
        List<NotificationMergeFields> notificationMergeFieldsList = notificationMergeFieldsRepository.findAll();
        assertThat(notificationMergeFieldsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFieldIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationMergeFieldsRepository.findAll().size();
        // set the field null
        notificationMergeFields.setField(null);

        // Create the NotificationMergeFields, which fails.

        restNotificationMergeFieldsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationMergeFields))
            )
            .andExpect(status().isBadRequest());

        List<NotificationMergeFields> notificationMergeFieldsList = notificationMergeFieldsRepository.findAll();
        assertThat(notificationMergeFieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEffDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationMergeFieldsRepository.findAll().size();
        // set the field null
        notificationMergeFields.setEffDate(null);

        // Create the NotificationMergeFields, which fails.

        restNotificationMergeFieldsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationMergeFields))
            )
            .andExpect(status().isBadRequest());

        List<NotificationMergeFields> notificationMergeFieldsList = notificationMergeFieldsRepository.findAll();
        assertThat(notificationMergeFieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationMergeFieldsRepository.findAll().size();
        // set the field null
        notificationMergeFields.setCreatedAt(null);

        // Create the NotificationMergeFields, which fails.

        restNotificationMergeFieldsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationMergeFields))
            )
            .andExpect(status().isBadRequest());

        List<NotificationMergeFields> notificationMergeFieldsList = notificationMergeFieldsRepository.findAll();
        assertThat(notificationMergeFieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationMergeFieldsRepository.findAll().size();
        // set the field null
        notificationMergeFields.setUpdatedAt(null);

        // Create the NotificationMergeFields, which fails.

        restNotificationMergeFieldsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationMergeFields))
            )
            .andExpect(status().isBadRequest());

        List<NotificationMergeFields> notificationMergeFieldsList = notificationMergeFieldsRepository.findAll();
        assertThat(notificationMergeFieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationMergeFieldsRepository.findAll().size();
        // set the field null
        notificationMergeFields.setVersion(null);

        // Create the NotificationMergeFields, which fails.

        restNotificationMergeFieldsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationMergeFields))
            )
            .andExpect(status().isBadRequest());

        List<NotificationMergeFields> notificationMergeFieldsList = notificationMergeFieldsRepository.findAll();
        assertThat(notificationMergeFieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNotificationMergeFields() throws Exception {
        // Initialize the database
        notificationMergeFieldsRepository.saveAndFlush(notificationMergeFields);

        // Get all the notificationMergeFieldsList
        restNotificationMergeFieldsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notificationMergeFields.getId().intValue())))
            .andExpect(jsonPath("$.[*].field").value(hasItem(DEFAULT_FIELD)))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getNotificationMergeFields() throws Exception {
        // Initialize the database
        notificationMergeFieldsRepository.saveAndFlush(notificationMergeFields);

        // Get the notificationMergeFields
        restNotificationMergeFieldsMockMvc
            .perform(get(ENTITY_API_URL_ID, notificationMergeFields.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(notificationMergeFields.getId().intValue()))
            .andExpect(jsonPath("$.field").value(DEFAULT_FIELD))
            .andExpect(jsonPath("$.effDate").value(DEFAULT_EFF_DATE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getNotificationMergeFieldsByIdFiltering() throws Exception {
        // Initialize the database
        notificationMergeFieldsRepository.saveAndFlush(notificationMergeFields);

        Long id = notificationMergeFields.getId();

        defaultNotificationMergeFieldsShouldBeFound("id.equals=" + id);
        defaultNotificationMergeFieldsShouldNotBeFound("id.notEquals=" + id);

        defaultNotificationMergeFieldsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNotificationMergeFieldsShouldNotBeFound("id.greaterThan=" + id);

        defaultNotificationMergeFieldsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNotificationMergeFieldsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNotificationMergeFieldsByFieldIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationMergeFieldsRepository.saveAndFlush(notificationMergeFields);

        // Get all the notificationMergeFieldsList where field equals to DEFAULT_FIELD
        defaultNotificationMergeFieldsShouldBeFound("field.equals=" + DEFAULT_FIELD);

        // Get all the notificationMergeFieldsList where field equals to UPDATED_FIELD
        defaultNotificationMergeFieldsShouldNotBeFound("field.equals=" + UPDATED_FIELD);
    }

    @Test
    @Transactional
    void getAllNotificationMergeFieldsByFieldIsInShouldWork() throws Exception {
        // Initialize the database
        notificationMergeFieldsRepository.saveAndFlush(notificationMergeFields);

        // Get all the notificationMergeFieldsList where field in DEFAULT_FIELD or UPDATED_FIELD
        defaultNotificationMergeFieldsShouldBeFound("field.in=" + DEFAULT_FIELD + "," + UPDATED_FIELD);

        // Get all the notificationMergeFieldsList where field equals to UPDATED_FIELD
        defaultNotificationMergeFieldsShouldNotBeFound("field.in=" + UPDATED_FIELD);
    }

    @Test
    @Transactional
    void getAllNotificationMergeFieldsByFieldIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationMergeFieldsRepository.saveAndFlush(notificationMergeFields);

        // Get all the notificationMergeFieldsList where field is not null
        defaultNotificationMergeFieldsShouldBeFound("field.specified=true");

        // Get all the notificationMergeFieldsList where field is null
        defaultNotificationMergeFieldsShouldNotBeFound("field.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationMergeFieldsByFieldContainsSomething() throws Exception {
        // Initialize the database
        notificationMergeFieldsRepository.saveAndFlush(notificationMergeFields);

        // Get all the notificationMergeFieldsList where field contains DEFAULT_FIELD
        defaultNotificationMergeFieldsShouldBeFound("field.contains=" + DEFAULT_FIELD);

        // Get all the notificationMergeFieldsList where field contains UPDATED_FIELD
        defaultNotificationMergeFieldsShouldNotBeFound("field.contains=" + UPDATED_FIELD);
    }

    @Test
    @Transactional
    void getAllNotificationMergeFieldsByFieldNotContainsSomething() throws Exception {
        // Initialize the database
        notificationMergeFieldsRepository.saveAndFlush(notificationMergeFields);

        // Get all the notificationMergeFieldsList where field does not contain DEFAULT_FIELD
        defaultNotificationMergeFieldsShouldNotBeFound("field.doesNotContain=" + DEFAULT_FIELD);

        // Get all the notificationMergeFieldsList where field does not contain UPDATED_FIELD
        defaultNotificationMergeFieldsShouldBeFound("field.doesNotContain=" + UPDATED_FIELD);
    }

    @Test
    @Transactional
    void getAllNotificationMergeFieldsByEffDateIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationMergeFieldsRepository.saveAndFlush(notificationMergeFields);

        // Get all the notificationMergeFieldsList where effDate equals to DEFAULT_EFF_DATE
        defaultNotificationMergeFieldsShouldBeFound("effDate.equals=" + DEFAULT_EFF_DATE);

        // Get all the notificationMergeFieldsList where effDate equals to UPDATED_EFF_DATE
        defaultNotificationMergeFieldsShouldNotBeFound("effDate.equals=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllNotificationMergeFieldsByEffDateIsInShouldWork() throws Exception {
        // Initialize the database
        notificationMergeFieldsRepository.saveAndFlush(notificationMergeFields);

        // Get all the notificationMergeFieldsList where effDate in DEFAULT_EFF_DATE or UPDATED_EFF_DATE
        defaultNotificationMergeFieldsShouldBeFound("effDate.in=" + DEFAULT_EFF_DATE + "," + UPDATED_EFF_DATE);

        // Get all the notificationMergeFieldsList where effDate equals to UPDATED_EFF_DATE
        defaultNotificationMergeFieldsShouldNotBeFound("effDate.in=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllNotificationMergeFieldsByEffDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationMergeFieldsRepository.saveAndFlush(notificationMergeFields);

        // Get all the notificationMergeFieldsList where effDate is not null
        defaultNotificationMergeFieldsShouldBeFound("effDate.specified=true");

        // Get all the notificationMergeFieldsList where effDate is null
        defaultNotificationMergeFieldsShouldNotBeFound("effDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationMergeFieldsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationMergeFieldsRepository.saveAndFlush(notificationMergeFields);

        // Get all the notificationMergeFieldsList where createdAt equals to DEFAULT_CREATED_AT
        defaultNotificationMergeFieldsShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the notificationMergeFieldsList where createdAt equals to UPDATED_CREATED_AT
        defaultNotificationMergeFieldsShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllNotificationMergeFieldsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        notificationMergeFieldsRepository.saveAndFlush(notificationMergeFields);

        // Get all the notificationMergeFieldsList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultNotificationMergeFieldsShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the notificationMergeFieldsList where createdAt equals to UPDATED_CREATED_AT
        defaultNotificationMergeFieldsShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllNotificationMergeFieldsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationMergeFieldsRepository.saveAndFlush(notificationMergeFields);

        // Get all the notificationMergeFieldsList where createdAt is not null
        defaultNotificationMergeFieldsShouldBeFound("createdAt.specified=true");

        // Get all the notificationMergeFieldsList where createdAt is null
        defaultNotificationMergeFieldsShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationMergeFieldsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationMergeFieldsRepository.saveAndFlush(notificationMergeFields);

        // Get all the notificationMergeFieldsList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultNotificationMergeFieldsShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the notificationMergeFieldsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultNotificationMergeFieldsShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllNotificationMergeFieldsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        notificationMergeFieldsRepository.saveAndFlush(notificationMergeFields);

        // Get all the notificationMergeFieldsList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultNotificationMergeFieldsShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the notificationMergeFieldsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultNotificationMergeFieldsShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllNotificationMergeFieldsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationMergeFieldsRepository.saveAndFlush(notificationMergeFields);

        // Get all the notificationMergeFieldsList where updatedAt is not null
        defaultNotificationMergeFieldsShouldBeFound("updatedAt.specified=true");

        // Get all the notificationMergeFieldsList where updatedAt is null
        defaultNotificationMergeFieldsShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationMergeFieldsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationMergeFieldsRepository.saveAndFlush(notificationMergeFields);

        // Get all the notificationMergeFieldsList where endDate equals to DEFAULT_END_DATE
        defaultNotificationMergeFieldsShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the notificationMergeFieldsList where endDate equals to UPDATED_END_DATE
        defaultNotificationMergeFieldsShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllNotificationMergeFieldsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        notificationMergeFieldsRepository.saveAndFlush(notificationMergeFields);

        // Get all the notificationMergeFieldsList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultNotificationMergeFieldsShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the notificationMergeFieldsList where endDate equals to UPDATED_END_DATE
        defaultNotificationMergeFieldsShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllNotificationMergeFieldsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationMergeFieldsRepository.saveAndFlush(notificationMergeFields);

        // Get all the notificationMergeFieldsList where endDate is not null
        defaultNotificationMergeFieldsShouldBeFound("endDate.specified=true");

        // Get all the notificationMergeFieldsList where endDate is null
        defaultNotificationMergeFieldsShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationMergeFieldsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationMergeFieldsRepository.saveAndFlush(notificationMergeFields);

        // Get all the notificationMergeFieldsList where version equals to DEFAULT_VERSION
        defaultNotificationMergeFieldsShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the notificationMergeFieldsList where version equals to UPDATED_VERSION
        defaultNotificationMergeFieldsShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllNotificationMergeFieldsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        notificationMergeFieldsRepository.saveAndFlush(notificationMergeFields);

        // Get all the notificationMergeFieldsList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultNotificationMergeFieldsShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the notificationMergeFieldsList where version equals to UPDATED_VERSION
        defaultNotificationMergeFieldsShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllNotificationMergeFieldsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationMergeFieldsRepository.saveAndFlush(notificationMergeFields);

        // Get all the notificationMergeFieldsList where version is not null
        defaultNotificationMergeFieldsShouldBeFound("version.specified=true");

        // Get all the notificationMergeFieldsList where version is null
        defaultNotificationMergeFieldsShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationMergeFieldsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notificationMergeFieldsRepository.saveAndFlush(notificationMergeFields);

        // Get all the notificationMergeFieldsList where version is greater than or equal to DEFAULT_VERSION
        defaultNotificationMergeFieldsShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the notificationMergeFieldsList where version is greater than or equal to UPDATED_VERSION
        defaultNotificationMergeFieldsShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllNotificationMergeFieldsByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notificationMergeFieldsRepository.saveAndFlush(notificationMergeFields);

        // Get all the notificationMergeFieldsList where version is less than or equal to DEFAULT_VERSION
        defaultNotificationMergeFieldsShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the notificationMergeFieldsList where version is less than or equal to SMALLER_VERSION
        defaultNotificationMergeFieldsShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllNotificationMergeFieldsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        notificationMergeFieldsRepository.saveAndFlush(notificationMergeFields);

        // Get all the notificationMergeFieldsList where version is less than DEFAULT_VERSION
        defaultNotificationMergeFieldsShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the notificationMergeFieldsList where version is less than UPDATED_VERSION
        defaultNotificationMergeFieldsShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllNotificationMergeFieldsByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        notificationMergeFieldsRepository.saveAndFlush(notificationMergeFields);

        // Get all the notificationMergeFieldsList where version is greater than DEFAULT_VERSION
        defaultNotificationMergeFieldsShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the notificationMergeFieldsList where version is greater than SMALLER_VERSION
        defaultNotificationMergeFieldsShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllNotificationMergeFieldsByNotificationEventIsEqualToSomething() throws Exception {
        NotificationEvents notificationEvent;
        if (TestUtil.findAll(em, NotificationEvents.class).isEmpty()) {
            notificationMergeFieldsRepository.saveAndFlush(notificationMergeFields);
            notificationEvent = NotificationEventsResourceIT.createEntity(em);
        } else {
            notificationEvent = TestUtil.findAll(em, NotificationEvents.class).get(0);
        }
        em.persist(notificationEvent);
        em.flush();
        notificationMergeFields.setNotificationEvent(notificationEvent);
        notificationMergeFieldsRepository.saveAndFlush(notificationMergeFields);
        Long notificationEventId = notificationEvent.getId();

        // Get all the notificationMergeFieldsList where notificationEvent equals to notificationEventId
        defaultNotificationMergeFieldsShouldBeFound("notificationEventId.equals=" + notificationEventId);

        // Get all the notificationMergeFieldsList where notificationEvent equals to (notificationEventId + 1)
        defaultNotificationMergeFieldsShouldNotBeFound("notificationEventId.equals=" + (notificationEventId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNotificationMergeFieldsShouldBeFound(String filter) throws Exception {
        restNotificationMergeFieldsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notificationMergeFields.getId().intValue())))
            .andExpect(jsonPath("$.[*].field").value(hasItem(DEFAULT_FIELD)))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restNotificationMergeFieldsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNotificationMergeFieldsShouldNotBeFound(String filter) throws Exception {
        restNotificationMergeFieldsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNotificationMergeFieldsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNotificationMergeFields() throws Exception {
        // Get the notificationMergeFields
        restNotificationMergeFieldsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNotificationMergeFields() throws Exception {
        // Initialize the database
        notificationMergeFieldsRepository.saveAndFlush(notificationMergeFields);

        int databaseSizeBeforeUpdate = notificationMergeFieldsRepository.findAll().size();

        // Update the notificationMergeFields
        NotificationMergeFields updatedNotificationMergeFields = notificationMergeFieldsRepository
            .findById(notificationMergeFields.getId())
            .get();
        // Disconnect from session so that the updates on updatedNotificationMergeFields are not directly saved in db
        em.detach(updatedNotificationMergeFields);
        updatedNotificationMergeFields
            .field(UPDATED_FIELD)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restNotificationMergeFieldsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNotificationMergeFields.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNotificationMergeFields))
            )
            .andExpect(status().isOk());

        // Validate the NotificationMergeFields in the database
        List<NotificationMergeFields> notificationMergeFieldsList = notificationMergeFieldsRepository.findAll();
        assertThat(notificationMergeFieldsList).hasSize(databaseSizeBeforeUpdate);
        NotificationMergeFields testNotificationMergeFields = notificationMergeFieldsList.get(notificationMergeFieldsList.size() - 1);
        assertThat(testNotificationMergeFields.getField()).isEqualTo(UPDATED_FIELD);
        assertThat(testNotificationMergeFields.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testNotificationMergeFields.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testNotificationMergeFields.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testNotificationMergeFields.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testNotificationMergeFields.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingNotificationMergeFields() throws Exception {
        int databaseSizeBeforeUpdate = notificationMergeFieldsRepository.findAll().size();
        notificationMergeFields.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificationMergeFieldsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, notificationMergeFields.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationMergeFields))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationMergeFields in the database
        List<NotificationMergeFields> notificationMergeFieldsList = notificationMergeFieldsRepository.findAll();
        assertThat(notificationMergeFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNotificationMergeFields() throws Exception {
        int databaseSizeBeforeUpdate = notificationMergeFieldsRepository.findAll().size();
        notificationMergeFields.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationMergeFieldsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationMergeFields))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationMergeFields in the database
        List<NotificationMergeFields> notificationMergeFieldsList = notificationMergeFieldsRepository.findAll();
        assertThat(notificationMergeFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNotificationMergeFields() throws Exception {
        int databaseSizeBeforeUpdate = notificationMergeFieldsRepository.findAll().size();
        notificationMergeFields.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationMergeFieldsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationMergeFields))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NotificationMergeFields in the database
        List<NotificationMergeFields> notificationMergeFieldsList = notificationMergeFieldsRepository.findAll();
        assertThat(notificationMergeFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNotificationMergeFieldsWithPatch() throws Exception {
        // Initialize the database
        notificationMergeFieldsRepository.saveAndFlush(notificationMergeFields);

        int databaseSizeBeforeUpdate = notificationMergeFieldsRepository.findAll().size();

        // Update the notificationMergeFields using partial update
        NotificationMergeFields partialUpdatedNotificationMergeFields = new NotificationMergeFields();
        partialUpdatedNotificationMergeFields.setId(notificationMergeFields.getId());

        partialUpdatedNotificationMergeFields.endDate(UPDATED_END_DATE).version(UPDATED_VERSION);

        restNotificationMergeFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotificationMergeFields.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNotificationMergeFields))
            )
            .andExpect(status().isOk());

        // Validate the NotificationMergeFields in the database
        List<NotificationMergeFields> notificationMergeFieldsList = notificationMergeFieldsRepository.findAll();
        assertThat(notificationMergeFieldsList).hasSize(databaseSizeBeforeUpdate);
        NotificationMergeFields testNotificationMergeFields = notificationMergeFieldsList.get(notificationMergeFieldsList.size() - 1);
        assertThat(testNotificationMergeFields.getField()).isEqualTo(DEFAULT_FIELD);
        assertThat(testNotificationMergeFields.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testNotificationMergeFields.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testNotificationMergeFields.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testNotificationMergeFields.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testNotificationMergeFields.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateNotificationMergeFieldsWithPatch() throws Exception {
        // Initialize the database
        notificationMergeFieldsRepository.saveAndFlush(notificationMergeFields);

        int databaseSizeBeforeUpdate = notificationMergeFieldsRepository.findAll().size();

        // Update the notificationMergeFields using partial update
        NotificationMergeFields partialUpdatedNotificationMergeFields = new NotificationMergeFields();
        partialUpdatedNotificationMergeFields.setId(notificationMergeFields.getId());

        partialUpdatedNotificationMergeFields
            .field(UPDATED_FIELD)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restNotificationMergeFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotificationMergeFields.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNotificationMergeFields))
            )
            .andExpect(status().isOk());

        // Validate the NotificationMergeFields in the database
        List<NotificationMergeFields> notificationMergeFieldsList = notificationMergeFieldsRepository.findAll();
        assertThat(notificationMergeFieldsList).hasSize(databaseSizeBeforeUpdate);
        NotificationMergeFields testNotificationMergeFields = notificationMergeFieldsList.get(notificationMergeFieldsList.size() - 1);
        assertThat(testNotificationMergeFields.getField()).isEqualTo(UPDATED_FIELD);
        assertThat(testNotificationMergeFields.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testNotificationMergeFields.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testNotificationMergeFields.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testNotificationMergeFields.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testNotificationMergeFields.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingNotificationMergeFields() throws Exception {
        int databaseSizeBeforeUpdate = notificationMergeFieldsRepository.findAll().size();
        notificationMergeFields.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificationMergeFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, notificationMergeFields.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notificationMergeFields))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationMergeFields in the database
        List<NotificationMergeFields> notificationMergeFieldsList = notificationMergeFieldsRepository.findAll();
        assertThat(notificationMergeFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNotificationMergeFields() throws Exception {
        int databaseSizeBeforeUpdate = notificationMergeFieldsRepository.findAll().size();
        notificationMergeFields.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationMergeFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notificationMergeFields))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationMergeFields in the database
        List<NotificationMergeFields> notificationMergeFieldsList = notificationMergeFieldsRepository.findAll();
        assertThat(notificationMergeFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNotificationMergeFields() throws Exception {
        int databaseSizeBeforeUpdate = notificationMergeFieldsRepository.findAll().size();
        notificationMergeFields.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationMergeFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notificationMergeFields))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NotificationMergeFields in the database
        List<NotificationMergeFields> notificationMergeFieldsList = notificationMergeFieldsRepository.findAll();
        assertThat(notificationMergeFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNotificationMergeFields() throws Exception {
        // Initialize the database
        notificationMergeFieldsRepository.saveAndFlush(notificationMergeFields);

        int databaseSizeBeforeDelete = notificationMergeFieldsRepository.findAll().size();

        // Delete the notificationMergeFields
        restNotificationMergeFieldsMockMvc
            .perform(delete(ENTITY_API_URL_ID, notificationMergeFields.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NotificationMergeFields> notificationMergeFieldsList = notificationMergeFieldsRepository.findAll();
        assertThat(notificationMergeFieldsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
