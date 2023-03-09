package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.NotificationEvents;
import com.venturedive.vendian_mono.domain.NotificationSentEmailLogs;
import com.venturedive.vendian_mono.domain.NotificationTemplates;
import com.venturedive.vendian_mono.repository.NotificationTemplatesRepository;
import com.venturedive.vendian_mono.service.criteria.NotificationTemplatesCriteria;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link NotificationTemplatesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NotificationTemplatesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAA";
    private static final String UPDATED_TYPE = "BBBBB";

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final byte[] DEFAULT_TEMPLATE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_TEMPLATE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_TEMPLATE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_TEMPLATE_CONTENT_TYPE = "image/png";

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

    private static final String ENTITY_API_URL = "/api/notification-templates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NotificationTemplatesRepository notificationTemplatesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNotificationTemplatesMockMvc;

    private NotificationTemplates notificationTemplates;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotificationTemplates createEntity(EntityManager em) {
        NotificationTemplates notificationTemplates = new NotificationTemplates()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .subject(DEFAULT_SUBJECT)
            .template(DEFAULT_TEMPLATE)
            .templateContentType(DEFAULT_TEMPLATE_CONTENT_TYPE)
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
        notificationTemplates.setNotificationEvent(notificationEvents);
        return notificationTemplates;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotificationTemplates createUpdatedEntity(EntityManager em) {
        NotificationTemplates notificationTemplates = new NotificationTemplates()
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .subject(UPDATED_SUBJECT)
            .template(UPDATED_TEMPLATE)
            .templateContentType(UPDATED_TEMPLATE_CONTENT_TYPE)
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
        notificationTemplates.setNotificationEvent(notificationEvents);
        return notificationTemplates;
    }

    @BeforeEach
    public void initTest() {
        notificationTemplates = createEntity(em);
    }

    @Test
    @Transactional
    void createNotificationTemplates() throws Exception {
        int databaseSizeBeforeCreate = notificationTemplatesRepository.findAll().size();
        // Create the NotificationTemplates
        restNotificationTemplatesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationTemplates))
            )
            .andExpect(status().isCreated());

        // Validate the NotificationTemplates in the database
        List<NotificationTemplates> notificationTemplatesList = notificationTemplatesRepository.findAll();
        assertThat(notificationTemplatesList).hasSize(databaseSizeBeforeCreate + 1);
        NotificationTemplates testNotificationTemplates = notificationTemplatesList.get(notificationTemplatesList.size() - 1);
        assertThat(testNotificationTemplates.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNotificationTemplates.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testNotificationTemplates.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testNotificationTemplates.getTemplate()).isEqualTo(DEFAULT_TEMPLATE);
        assertThat(testNotificationTemplates.getTemplateContentType()).isEqualTo(DEFAULT_TEMPLATE_CONTENT_TYPE);
        assertThat(testNotificationTemplates.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testNotificationTemplates.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testNotificationTemplates.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testNotificationTemplates.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testNotificationTemplates.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createNotificationTemplatesWithExistingId() throws Exception {
        // Create the NotificationTemplates with an existing ID
        notificationTemplates.setId(1L);

        int databaseSizeBeforeCreate = notificationTemplatesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotificationTemplatesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationTemplates))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationTemplates in the database
        List<NotificationTemplates> notificationTemplatesList = notificationTemplatesRepository.findAll();
        assertThat(notificationTemplatesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationTemplatesRepository.findAll().size();
        // set the field null
        notificationTemplates.setName(null);

        // Create the NotificationTemplates, which fails.

        restNotificationTemplatesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationTemplates))
            )
            .andExpect(status().isBadRequest());

        List<NotificationTemplates> notificationTemplatesList = notificationTemplatesRepository.findAll();
        assertThat(notificationTemplatesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationTemplatesRepository.findAll().size();
        // set the field null
        notificationTemplates.setType(null);

        // Create the NotificationTemplates, which fails.

        restNotificationTemplatesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationTemplates))
            )
            .andExpect(status().isBadRequest());

        List<NotificationTemplates> notificationTemplatesList = notificationTemplatesRepository.findAll();
        assertThat(notificationTemplatesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEffDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationTemplatesRepository.findAll().size();
        // set the field null
        notificationTemplates.setEffDate(null);

        // Create the NotificationTemplates, which fails.

        restNotificationTemplatesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationTemplates))
            )
            .andExpect(status().isBadRequest());

        List<NotificationTemplates> notificationTemplatesList = notificationTemplatesRepository.findAll();
        assertThat(notificationTemplatesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationTemplatesRepository.findAll().size();
        // set the field null
        notificationTemplates.setCreatedAt(null);

        // Create the NotificationTemplates, which fails.

        restNotificationTemplatesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationTemplates))
            )
            .andExpect(status().isBadRequest());

        List<NotificationTemplates> notificationTemplatesList = notificationTemplatesRepository.findAll();
        assertThat(notificationTemplatesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationTemplatesRepository.findAll().size();
        // set the field null
        notificationTemplates.setUpdatedAt(null);

        // Create the NotificationTemplates, which fails.

        restNotificationTemplatesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationTemplates))
            )
            .andExpect(status().isBadRequest());

        List<NotificationTemplates> notificationTemplatesList = notificationTemplatesRepository.findAll();
        assertThat(notificationTemplatesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationTemplatesRepository.findAll().size();
        // set the field null
        notificationTemplates.setVersion(null);

        // Create the NotificationTemplates, which fails.

        restNotificationTemplatesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationTemplates))
            )
            .andExpect(status().isBadRequest());

        List<NotificationTemplates> notificationTemplatesList = notificationTemplatesRepository.findAll();
        assertThat(notificationTemplatesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNotificationTemplates() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        // Get all the notificationTemplatesList
        restNotificationTemplatesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notificationTemplates.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].templateContentType").value(hasItem(DEFAULT_TEMPLATE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].template").value(hasItem(Base64Utils.encodeToString(DEFAULT_TEMPLATE))))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getNotificationTemplates() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        // Get the notificationTemplates
        restNotificationTemplatesMockMvc
            .perform(get(ENTITY_API_URL_ID, notificationTemplates.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(notificationTemplates.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT))
            .andExpect(jsonPath("$.templateContentType").value(DEFAULT_TEMPLATE_CONTENT_TYPE))
            .andExpect(jsonPath("$.template").value(Base64Utils.encodeToString(DEFAULT_TEMPLATE)))
            .andExpect(jsonPath("$.effDate").value(DEFAULT_EFF_DATE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getNotificationTemplatesByIdFiltering() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        Long id = notificationTemplates.getId();

        defaultNotificationTemplatesShouldBeFound("id.equals=" + id);
        defaultNotificationTemplatesShouldNotBeFound("id.notEquals=" + id);

        defaultNotificationTemplatesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNotificationTemplatesShouldNotBeFound("id.greaterThan=" + id);

        defaultNotificationTemplatesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNotificationTemplatesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        // Get all the notificationTemplatesList where name equals to DEFAULT_NAME
        defaultNotificationTemplatesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the notificationTemplatesList where name equals to UPDATED_NAME
        defaultNotificationTemplatesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        // Get all the notificationTemplatesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultNotificationTemplatesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the notificationTemplatesList where name equals to UPDATED_NAME
        defaultNotificationTemplatesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        // Get all the notificationTemplatesList where name is not null
        defaultNotificationTemplatesShouldBeFound("name.specified=true");

        // Get all the notificationTemplatesList where name is null
        defaultNotificationTemplatesShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByNameContainsSomething() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        // Get all the notificationTemplatesList where name contains DEFAULT_NAME
        defaultNotificationTemplatesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the notificationTemplatesList where name contains UPDATED_NAME
        defaultNotificationTemplatesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        // Get all the notificationTemplatesList where name does not contain DEFAULT_NAME
        defaultNotificationTemplatesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the notificationTemplatesList where name does not contain UPDATED_NAME
        defaultNotificationTemplatesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        // Get all the notificationTemplatesList where type equals to DEFAULT_TYPE
        defaultNotificationTemplatesShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the notificationTemplatesList where type equals to UPDATED_TYPE
        defaultNotificationTemplatesShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        // Get all the notificationTemplatesList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultNotificationTemplatesShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the notificationTemplatesList where type equals to UPDATED_TYPE
        defaultNotificationTemplatesShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        // Get all the notificationTemplatesList where type is not null
        defaultNotificationTemplatesShouldBeFound("type.specified=true");

        // Get all the notificationTemplatesList where type is null
        defaultNotificationTemplatesShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByTypeContainsSomething() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        // Get all the notificationTemplatesList where type contains DEFAULT_TYPE
        defaultNotificationTemplatesShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the notificationTemplatesList where type contains UPDATED_TYPE
        defaultNotificationTemplatesShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        // Get all the notificationTemplatesList where type does not contain DEFAULT_TYPE
        defaultNotificationTemplatesShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the notificationTemplatesList where type does not contain UPDATED_TYPE
        defaultNotificationTemplatesShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesBySubjectIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        // Get all the notificationTemplatesList where subject equals to DEFAULT_SUBJECT
        defaultNotificationTemplatesShouldBeFound("subject.equals=" + DEFAULT_SUBJECT);

        // Get all the notificationTemplatesList where subject equals to UPDATED_SUBJECT
        defaultNotificationTemplatesShouldNotBeFound("subject.equals=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesBySubjectIsInShouldWork() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        // Get all the notificationTemplatesList where subject in DEFAULT_SUBJECT or UPDATED_SUBJECT
        defaultNotificationTemplatesShouldBeFound("subject.in=" + DEFAULT_SUBJECT + "," + UPDATED_SUBJECT);

        // Get all the notificationTemplatesList where subject equals to UPDATED_SUBJECT
        defaultNotificationTemplatesShouldNotBeFound("subject.in=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesBySubjectIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        // Get all the notificationTemplatesList where subject is not null
        defaultNotificationTemplatesShouldBeFound("subject.specified=true");

        // Get all the notificationTemplatesList where subject is null
        defaultNotificationTemplatesShouldNotBeFound("subject.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesBySubjectContainsSomething() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        // Get all the notificationTemplatesList where subject contains DEFAULT_SUBJECT
        defaultNotificationTemplatesShouldBeFound("subject.contains=" + DEFAULT_SUBJECT);

        // Get all the notificationTemplatesList where subject contains UPDATED_SUBJECT
        defaultNotificationTemplatesShouldNotBeFound("subject.contains=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesBySubjectNotContainsSomething() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        // Get all the notificationTemplatesList where subject does not contain DEFAULT_SUBJECT
        defaultNotificationTemplatesShouldNotBeFound("subject.doesNotContain=" + DEFAULT_SUBJECT);

        // Get all the notificationTemplatesList where subject does not contain UPDATED_SUBJECT
        defaultNotificationTemplatesShouldBeFound("subject.doesNotContain=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByEffDateIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        // Get all the notificationTemplatesList where effDate equals to DEFAULT_EFF_DATE
        defaultNotificationTemplatesShouldBeFound("effDate.equals=" + DEFAULT_EFF_DATE);

        // Get all the notificationTemplatesList where effDate equals to UPDATED_EFF_DATE
        defaultNotificationTemplatesShouldNotBeFound("effDate.equals=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByEffDateIsInShouldWork() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        // Get all the notificationTemplatesList where effDate in DEFAULT_EFF_DATE or UPDATED_EFF_DATE
        defaultNotificationTemplatesShouldBeFound("effDate.in=" + DEFAULT_EFF_DATE + "," + UPDATED_EFF_DATE);

        // Get all the notificationTemplatesList where effDate equals to UPDATED_EFF_DATE
        defaultNotificationTemplatesShouldNotBeFound("effDate.in=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByEffDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        // Get all the notificationTemplatesList where effDate is not null
        defaultNotificationTemplatesShouldBeFound("effDate.specified=true");

        // Get all the notificationTemplatesList where effDate is null
        defaultNotificationTemplatesShouldNotBeFound("effDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        // Get all the notificationTemplatesList where createdAt equals to DEFAULT_CREATED_AT
        defaultNotificationTemplatesShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the notificationTemplatesList where createdAt equals to UPDATED_CREATED_AT
        defaultNotificationTemplatesShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        // Get all the notificationTemplatesList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultNotificationTemplatesShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the notificationTemplatesList where createdAt equals to UPDATED_CREATED_AT
        defaultNotificationTemplatesShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        // Get all the notificationTemplatesList where createdAt is not null
        defaultNotificationTemplatesShouldBeFound("createdAt.specified=true");

        // Get all the notificationTemplatesList where createdAt is null
        defaultNotificationTemplatesShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        // Get all the notificationTemplatesList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultNotificationTemplatesShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the notificationTemplatesList where updatedAt equals to UPDATED_UPDATED_AT
        defaultNotificationTemplatesShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        // Get all the notificationTemplatesList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultNotificationTemplatesShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the notificationTemplatesList where updatedAt equals to UPDATED_UPDATED_AT
        defaultNotificationTemplatesShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        // Get all the notificationTemplatesList where updatedAt is not null
        defaultNotificationTemplatesShouldBeFound("updatedAt.specified=true");

        // Get all the notificationTemplatesList where updatedAt is null
        defaultNotificationTemplatesShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        // Get all the notificationTemplatesList where endDate equals to DEFAULT_END_DATE
        defaultNotificationTemplatesShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the notificationTemplatesList where endDate equals to UPDATED_END_DATE
        defaultNotificationTemplatesShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        // Get all the notificationTemplatesList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultNotificationTemplatesShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the notificationTemplatesList where endDate equals to UPDATED_END_DATE
        defaultNotificationTemplatesShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        // Get all the notificationTemplatesList where endDate is not null
        defaultNotificationTemplatesShouldBeFound("endDate.specified=true");

        // Get all the notificationTemplatesList where endDate is null
        defaultNotificationTemplatesShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        // Get all the notificationTemplatesList where version equals to DEFAULT_VERSION
        defaultNotificationTemplatesShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the notificationTemplatesList where version equals to UPDATED_VERSION
        defaultNotificationTemplatesShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        // Get all the notificationTemplatesList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultNotificationTemplatesShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the notificationTemplatesList where version equals to UPDATED_VERSION
        defaultNotificationTemplatesShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        // Get all the notificationTemplatesList where version is not null
        defaultNotificationTemplatesShouldBeFound("version.specified=true");

        // Get all the notificationTemplatesList where version is null
        defaultNotificationTemplatesShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        // Get all the notificationTemplatesList where version is greater than or equal to DEFAULT_VERSION
        defaultNotificationTemplatesShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the notificationTemplatesList where version is greater than or equal to UPDATED_VERSION
        defaultNotificationTemplatesShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        // Get all the notificationTemplatesList where version is less than or equal to DEFAULT_VERSION
        defaultNotificationTemplatesShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the notificationTemplatesList where version is less than or equal to SMALLER_VERSION
        defaultNotificationTemplatesShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        // Get all the notificationTemplatesList where version is less than DEFAULT_VERSION
        defaultNotificationTemplatesShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the notificationTemplatesList where version is less than UPDATED_VERSION
        defaultNotificationTemplatesShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        // Get all the notificationTemplatesList where version is greater than DEFAULT_VERSION
        defaultNotificationTemplatesShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the notificationTemplatesList where version is greater than SMALLER_VERSION
        defaultNotificationTemplatesShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByNotificationEventIsEqualToSomething() throws Exception {
        NotificationEvents notificationEvent;
        if (TestUtil.findAll(em, NotificationEvents.class).isEmpty()) {
            notificationTemplatesRepository.saveAndFlush(notificationTemplates);
            notificationEvent = NotificationEventsResourceIT.createEntity(em);
        } else {
            notificationEvent = TestUtil.findAll(em, NotificationEvents.class).get(0);
        }
        em.persist(notificationEvent);
        em.flush();
        notificationTemplates.setNotificationEvent(notificationEvent);
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);
        Long notificationEventId = notificationEvent.getId();

        // Get all the notificationTemplatesList where notificationEvent equals to notificationEventId
        defaultNotificationTemplatesShouldBeFound("notificationEventId.equals=" + notificationEventId);

        // Get all the notificationTemplatesList where notificationEvent equals to (notificationEventId + 1)
        defaultNotificationTemplatesShouldNotBeFound("notificationEventId.equals=" + (notificationEventId + 1));
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByNotificationsentemaillogsNotificationtemplateIsEqualToSomething() throws Exception {
        NotificationSentEmailLogs notificationsentemaillogsNotificationtemplate;
        if (TestUtil.findAll(em, NotificationSentEmailLogs.class).isEmpty()) {
            notificationTemplatesRepository.saveAndFlush(notificationTemplates);
            notificationsentemaillogsNotificationtemplate = NotificationSentEmailLogsResourceIT.createEntity(em);
        } else {
            notificationsentemaillogsNotificationtemplate = TestUtil.findAll(em, NotificationSentEmailLogs.class).get(0);
        }
        em.persist(notificationsentemaillogsNotificationtemplate);
        em.flush();
        notificationTemplates.addNotificationsentemaillogsNotificationtemplate(notificationsentemaillogsNotificationtemplate);
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);
        Long notificationsentemaillogsNotificationtemplateId = notificationsentemaillogsNotificationtemplate.getId();

        // Get all the notificationTemplatesList where notificationsentemaillogsNotificationtemplate equals to notificationsentemaillogsNotificationtemplateId
        defaultNotificationTemplatesShouldBeFound(
            "notificationsentemaillogsNotificationtemplateId.equals=" + notificationsentemaillogsNotificationtemplateId
        );

        // Get all the notificationTemplatesList where notificationsentemaillogsNotificationtemplate equals to (notificationsentemaillogsNotificationtemplateId + 1)
        defaultNotificationTemplatesShouldNotBeFound(
            "notificationsentemaillogsNotificationtemplateId.equals=" + (notificationsentemaillogsNotificationtemplateId + 1)
        );
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNotificationTemplatesShouldBeFound(String filter) throws Exception {
        restNotificationTemplatesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notificationTemplates.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].templateContentType").value(hasItem(DEFAULT_TEMPLATE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].template").value(hasItem(Base64Utils.encodeToString(DEFAULT_TEMPLATE))))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restNotificationTemplatesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNotificationTemplatesShouldNotBeFound(String filter) throws Exception {
        restNotificationTemplatesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNotificationTemplatesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNotificationTemplates() throws Exception {
        // Get the notificationTemplates
        restNotificationTemplatesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNotificationTemplates() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        int databaseSizeBeforeUpdate = notificationTemplatesRepository.findAll().size();

        // Update the notificationTemplates
        NotificationTemplates updatedNotificationTemplates = notificationTemplatesRepository.findById(notificationTemplates.getId()).get();
        // Disconnect from session so that the updates on updatedNotificationTemplates are not directly saved in db
        em.detach(updatedNotificationTemplates);
        updatedNotificationTemplates
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .subject(UPDATED_SUBJECT)
            .template(UPDATED_TEMPLATE)
            .templateContentType(UPDATED_TEMPLATE_CONTENT_TYPE)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restNotificationTemplatesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNotificationTemplates.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNotificationTemplates))
            )
            .andExpect(status().isOk());

        // Validate the NotificationTemplates in the database
        List<NotificationTemplates> notificationTemplatesList = notificationTemplatesRepository.findAll();
        assertThat(notificationTemplatesList).hasSize(databaseSizeBeforeUpdate);
        NotificationTemplates testNotificationTemplates = notificationTemplatesList.get(notificationTemplatesList.size() - 1);
        assertThat(testNotificationTemplates.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNotificationTemplates.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testNotificationTemplates.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testNotificationTemplates.getTemplate()).isEqualTo(UPDATED_TEMPLATE);
        assertThat(testNotificationTemplates.getTemplateContentType()).isEqualTo(UPDATED_TEMPLATE_CONTENT_TYPE);
        assertThat(testNotificationTemplates.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testNotificationTemplates.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testNotificationTemplates.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testNotificationTemplates.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testNotificationTemplates.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingNotificationTemplates() throws Exception {
        int databaseSizeBeforeUpdate = notificationTemplatesRepository.findAll().size();
        notificationTemplates.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificationTemplatesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, notificationTemplates.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationTemplates))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationTemplates in the database
        List<NotificationTemplates> notificationTemplatesList = notificationTemplatesRepository.findAll();
        assertThat(notificationTemplatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNotificationTemplates() throws Exception {
        int databaseSizeBeforeUpdate = notificationTemplatesRepository.findAll().size();
        notificationTemplates.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationTemplatesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationTemplates))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationTemplates in the database
        List<NotificationTemplates> notificationTemplatesList = notificationTemplatesRepository.findAll();
        assertThat(notificationTemplatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNotificationTemplates() throws Exception {
        int databaseSizeBeforeUpdate = notificationTemplatesRepository.findAll().size();
        notificationTemplates.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationTemplatesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationTemplates))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NotificationTemplates in the database
        List<NotificationTemplates> notificationTemplatesList = notificationTemplatesRepository.findAll();
        assertThat(notificationTemplatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNotificationTemplatesWithPatch() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        int databaseSizeBeforeUpdate = notificationTemplatesRepository.findAll().size();

        // Update the notificationTemplates using partial update
        NotificationTemplates partialUpdatedNotificationTemplates = new NotificationTemplates();
        partialUpdatedNotificationTemplates.setId(notificationTemplates.getId());

        partialUpdatedNotificationTemplates
            .type(UPDATED_TYPE)
            .template(UPDATED_TEMPLATE)
            .templateContentType(UPDATED_TEMPLATE_CONTENT_TYPE)
            .effDate(UPDATED_EFF_DATE)
            .updatedAt(UPDATED_UPDATED_AT);

        restNotificationTemplatesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotificationTemplates.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNotificationTemplates))
            )
            .andExpect(status().isOk());

        // Validate the NotificationTemplates in the database
        List<NotificationTemplates> notificationTemplatesList = notificationTemplatesRepository.findAll();
        assertThat(notificationTemplatesList).hasSize(databaseSizeBeforeUpdate);
        NotificationTemplates testNotificationTemplates = notificationTemplatesList.get(notificationTemplatesList.size() - 1);
        assertThat(testNotificationTemplates.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNotificationTemplates.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testNotificationTemplates.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testNotificationTemplates.getTemplate()).isEqualTo(UPDATED_TEMPLATE);
        assertThat(testNotificationTemplates.getTemplateContentType()).isEqualTo(UPDATED_TEMPLATE_CONTENT_TYPE);
        assertThat(testNotificationTemplates.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testNotificationTemplates.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testNotificationTemplates.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testNotificationTemplates.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testNotificationTemplates.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateNotificationTemplatesWithPatch() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        int databaseSizeBeforeUpdate = notificationTemplatesRepository.findAll().size();

        // Update the notificationTemplates using partial update
        NotificationTemplates partialUpdatedNotificationTemplates = new NotificationTemplates();
        partialUpdatedNotificationTemplates.setId(notificationTemplates.getId());

        partialUpdatedNotificationTemplates
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .subject(UPDATED_SUBJECT)
            .template(UPDATED_TEMPLATE)
            .templateContentType(UPDATED_TEMPLATE_CONTENT_TYPE)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restNotificationTemplatesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotificationTemplates.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNotificationTemplates))
            )
            .andExpect(status().isOk());

        // Validate the NotificationTemplates in the database
        List<NotificationTemplates> notificationTemplatesList = notificationTemplatesRepository.findAll();
        assertThat(notificationTemplatesList).hasSize(databaseSizeBeforeUpdate);
        NotificationTemplates testNotificationTemplates = notificationTemplatesList.get(notificationTemplatesList.size() - 1);
        assertThat(testNotificationTemplates.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNotificationTemplates.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testNotificationTemplates.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testNotificationTemplates.getTemplate()).isEqualTo(UPDATED_TEMPLATE);
        assertThat(testNotificationTemplates.getTemplateContentType()).isEqualTo(UPDATED_TEMPLATE_CONTENT_TYPE);
        assertThat(testNotificationTemplates.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testNotificationTemplates.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testNotificationTemplates.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testNotificationTemplates.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testNotificationTemplates.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingNotificationTemplates() throws Exception {
        int databaseSizeBeforeUpdate = notificationTemplatesRepository.findAll().size();
        notificationTemplates.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificationTemplatesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, notificationTemplates.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notificationTemplates))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationTemplates in the database
        List<NotificationTemplates> notificationTemplatesList = notificationTemplatesRepository.findAll();
        assertThat(notificationTemplatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNotificationTemplates() throws Exception {
        int databaseSizeBeforeUpdate = notificationTemplatesRepository.findAll().size();
        notificationTemplates.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationTemplatesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notificationTemplates))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationTemplates in the database
        List<NotificationTemplates> notificationTemplatesList = notificationTemplatesRepository.findAll();
        assertThat(notificationTemplatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNotificationTemplates() throws Exception {
        int databaseSizeBeforeUpdate = notificationTemplatesRepository.findAll().size();
        notificationTemplates.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationTemplatesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notificationTemplates))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NotificationTemplates in the database
        List<NotificationTemplates> notificationTemplatesList = notificationTemplatesRepository.findAll();
        assertThat(notificationTemplatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNotificationTemplates() throws Exception {
        // Initialize the database
        notificationTemplatesRepository.saveAndFlush(notificationTemplates);

        int databaseSizeBeforeDelete = notificationTemplatesRepository.findAll().size();

        // Delete the notificationTemplates
        restNotificationTemplatesMockMvc
            .perform(delete(ENTITY_API_URL_ID, notificationTemplates.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NotificationTemplates> notificationTemplatesList = notificationTemplatesRepository.findAll();
        assertThat(notificationTemplatesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
