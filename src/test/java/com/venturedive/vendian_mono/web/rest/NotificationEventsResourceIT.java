package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.NotificationEvents;
import com.venturedive.vendian_mono.domain.NotificationMergeFields;
import com.venturedive.vendian_mono.domain.NotificationTemplates;
import com.venturedive.vendian_mono.repository.NotificationEventsRepository;
import com.venturedive.vendian_mono.service.criteria.NotificationEventsCriteria;
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
 * Integration tests for the {@link NotificationEventsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NotificationEventsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/notification-events";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NotificationEventsRepository notificationEventsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNotificationEventsMockMvc;

    private NotificationEvents notificationEvents;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotificationEvents createEntity(EntityManager em) {
        NotificationEvents notificationEvents = new NotificationEvents()
            .name(DEFAULT_NAME)
            .effDate(DEFAULT_EFF_DATE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .endDate(DEFAULT_END_DATE)
            .version(DEFAULT_VERSION);
        return notificationEvents;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotificationEvents createUpdatedEntity(EntityManager em) {
        NotificationEvents notificationEvents = new NotificationEvents()
            .name(UPDATED_NAME)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);
        return notificationEvents;
    }

    @BeforeEach
    public void initTest() {
        notificationEvents = createEntity(em);
    }

    @Test
    @Transactional
    void createNotificationEvents() throws Exception {
        int databaseSizeBeforeCreate = notificationEventsRepository.findAll().size();
        // Create the NotificationEvents
        restNotificationEventsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationEvents))
            )
            .andExpect(status().isCreated());

        // Validate the NotificationEvents in the database
        List<NotificationEvents> notificationEventsList = notificationEventsRepository.findAll();
        assertThat(notificationEventsList).hasSize(databaseSizeBeforeCreate + 1);
        NotificationEvents testNotificationEvents = notificationEventsList.get(notificationEventsList.size() - 1);
        assertThat(testNotificationEvents.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNotificationEvents.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testNotificationEvents.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testNotificationEvents.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testNotificationEvents.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testNotificationEvents.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createNotificationEventsWithExistingId() throws Exception {
        // Create the NotificationEvents with an existing ID
        notificationEvents.setId(1L);

        int databaseSizeBeforeCreate = notificationEventsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotificationEventsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationEvents))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationEvents in the database
        List<NotificationEvents> notificationEventsList = notificationEventsRepository.findAll();
        assertThat(notificationEventsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationEventsRepository.findAll().size();
        // set the field null
        notificationEvents.setName(null);

        // Create the NotificationEvents, which fails.

        restNotificationEventsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationEvents))
            )
            .andExpect(status().isBadRequest());

        List<NotificationEvents> notificationEventsList = notificationEventsRepository.findAll();
        assertThat(notificationEventsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEffDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationEventsRepository.findAll().size();
        // set the field null
        notificationEvents.setEffDate(null);

        // Create the NotificationEvents, which fails.

        restNotificationEventsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationEvents))
            )
            .andExpect(status().isBadRequest());

        List<NotificationEvents> notificationEventsList = notificationEventsRepository.findAll();
        assertThat(notificationEventsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationEventsRepository.findAll().size();
        // set the field null
        notificationEvents.setCreatedAt(null);

        // Create the NotificationEvents, which fails.

        restNotificationEventsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationEvents))
            )
            .andExpect(status().isBadRequest());

        List<NotificationEvents> notificationEventsList = notificationEventsRepository.findAll();
        assertThat(notificationEventsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationEventsRepository.findAll().size();
        // set the field null
        notificationEvents.setUpdatedAt(null);

        // Create the NotificationEvents, which fails.

        restNotificationEventsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationEvents))
            )
            .andExpect(status().isBadRequest());

        List<NotificationEvents> notificationEventsList = notificationEventsRepository.findAll();
        assertThat(notificationEventsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationEventsRepository.findAll().size();
        // set the field null
        notificationEvents.setVersion(null);

        // Create the NotificationEvents, which fails.

        restNotificationEventsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationEvents))
            )
            .andExpect(status().isBadRequest());

        List<NotificationEvents> notificationEventsList = notificationEventsRepository.findAll();
        assertThat(notificationEventsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNotificationEvents() throws Exception {
        // Initialize the database
        notificationEventsRepository.saveAndFlush(notificationEvents);

        // Get all the notificationEventsList
        restNotificationEventsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notificationEvents.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getNotificationEvents() throws Exception {
        // Initialize the database
        notificationEventsRepository.saveAndFlush(notificationEvents);

        // Get the notificationEvents
        restNotificationEventsMockMvc
            .perform(get(ENTITY_API_URL_ID, notificationEvents.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(notificationEvents.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.effDate").value(DEFAULT_EFF_DATE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getNotificationEventsByIdFiltering() throws Exception {
        // Initialize the database
        notificationEventsRepository.saveAndFlush(notificationEvents);

        Long id = notificationEvents.getId();

        defaultNotificationEventsShouldBeFound("id.equals=" + id);
        defaultNotificationEventsShouldNotBeFound("id.notEquals=" + id);

        defaultNotificationEventsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNotificationEventsShouldNotBeFound("id.greaterThan=" + id);

        defaultNotificationEventsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNotificationEventsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNotificationEventsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationEventsRepository.saveAndFlush(notificationEvents);

        // Get all the notificationEventsList where name equals to DEFAULT_NAME
        defaultNotificationEventsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the notificationEventsList where name equals to UPDATED_NAME
        defaultNotificationEventsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllNotificationEventsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        notificationEventsRepository.saveAndFlush(notificationEvents);

        // Get all the notificationEventsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultNotificationEventsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the notificationEventsList where name equals to UPDATED_NAME
        defaultNotificationEventsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllNotificationEventsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationEventsRepository.saveAndFlush(notificationEvents);

        // Get all the notificationEventsList where name is not null
        defaultNotificationEventsShouldBeFound("name.specified=true");

        // Get all the notificationEventsList where name is null
        defaultNotificationEventsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationEventsByNameContainsSomething() throws Exception {
        // Initialize the database
        notificationEventsRepository.saveAndFlush(notificationEvents);

        // Get all the notificationEventsList where name contains DEFAULT_NAME
        defaultNotificationEventsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the notificationEventsList where name contains UPDATED_NAME
        defaultNotificationEventsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllNotificationEventsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        notificationEventsRepository.saveAndFlush(notificationEvents);

        // Get all the notificationEventsList where name does not contain DEFAULT_NAME
        defaultNotificationEventsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the notificationEventsList where name does not contain UPDATED_NAME
        defaultNotificationEventsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllNotificationEventsByEffDateIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationEventsRepository.saveAndFlush(notificationEvents);

        // Get all the notificationEventsList where effDate equals to DEFAULT_EFF_DATE
        defaultNotificationEventsShouldBeFound("effDate.equals=" + DEFAULT_EFF_DATE);

        // Get all the notificationEventsList where effDate equals to UPDATED_EFF_DATE
        defaultNotificationEventsShouldNotBeFound("effDate.equals=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllNotificationEventsByEffDateIsInShouldWork() throws Exception {
        // Initialize the database
        notificationEventsRepository.saveAndFlush(notificationEvents);

        // Get all the notificationEventsList where effDate in DEFAULT_EFF_DATE or UPDATED_EFF_DATE
        defaultNotificationEventsShouldBeFound("effDate.in=" + DEFAULT_EFF_DATE + "," + UPDATED_EFF_DATE);

        // Get all the notificationEventsList where effDate equals to UPDATED_EFF_DATE
        defaultNotificationEventsShouldNotBeFound("effDate.in=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllNotificationEventsByEffDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationEventsRepository.saveAndFlush(notificationEvents);

        // Get all the notificationEventsList where effDate is not null
        defaultNotificationEventsShouldBeFound("effDate.specified=true");

        // Get all the notificationEventsList where effDate is null
        defaultNotificationEventsShouldNotBeFound("effDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationEventsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationEventsRepository.saveAndFlush(notificationEvents);

        // Get all the notificationEventsList where createdAt equals to DEFAULT_CREATED_AT
        defaultNotificationEventsShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the notificationEventsList where createdAt equals to UPDATED_CREATED_AT
        defaultNotificationEventsShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllNotificationEventsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        notificationEventsRepository.saveAndFlush(notificationEvents);

        // Get all the notificationEventsList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultNotificationEventsShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the notificationEventsList where createdAt equals to UPDATED_CREATED_AT
        defaultNotificationEventsShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllNotificationEventsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationEventsRepository.saveAndFlush(notificationEvents);

        // Get all the notificationEventsList where createdAt is not null
        defaultNotificationEventsShouldBeFound("createdAt.specified=true");

        // Get all the notificationEventsList where createdAt is null
        defaultNotificationEventsShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationEventsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationEventsRepository.saveAndFlush(notificationEvents);

        // Get all the notificationEventsList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultNotificationEventsShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the notificationEventsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultNotificationEventsShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllNotificationEventsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        notificationEventsRepository.saveAndFlush(notificationEvents);

        // Get all the notificationEventsList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultNotificationEventsShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the notificationEventsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultNotificationEventsShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllNotificationEventsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationEventsRepository.saveAndFlush(notificationEvents);

        // Get all the notificationEventsList where updatedAt is not null
        defaultNotificationEventsShouldBeFound("updatedAt.specified=true");

        // Get all the notificationEventsList where updatedAt is null
        defaultNotificationEventsShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationEventsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationEventsRepository.saveAndFlush(notificationEvents);

        // Get all the notificationEventsList where endDate equals to DEFAULT_END_DATE
        defaultNotificationEventsShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the notificationEventsList where endDate equals to UPDATED_END_DATE
        defaultNotificationEventsShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllNotificationEventsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        notificationEventsRepository.saveAndFlush(notificationEvents);

        // Get all the notificationEventsList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultNotificationEventsShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the notificationEventsList where endDate equals to UPDATED_END_DATE
        defaultNotificationEventsShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllNotificationEventsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationEventsRepository.saveAndFlush(notificationEvents);

        // Get all the notificationEventsList where endDate is not null
        defaultNotificationEventsShouldBeFound("endDate.specified=true");

        // Get all the notificationEventsList where endDate is null
        defaultNotificationEventsShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationEventsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationEventsRepository.saveAndFlush(notificationEvents);

        // Get all the notificationEventsList where version equals to DEFAULT_VERSION
        defaultNotificationEventsShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the notificationEventsList where version equals to UPDATED_VERSION
        defaultNotificationEventsShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllNotificationEventsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        notificationEventsRepository.saveAndFlush(notificationEvents);

        // Get all the notificationEventsList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultNotificationEventsShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the notificationEventsList where version equals to UPDATED_VERSION
        defaultNotificationEventsShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllNotificationEventsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationEventsRepository.saveAndFlush(notificationEvents);

        // Get all the notificationEventsList where version is not null
        defaultNotificationEventsShouldBeFound("version.specified=true");

        // Get all the notificationEventsList where version is null
        defaultNotificationEventsShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationEventsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notificationEventsRepository.saveAndFlush(notificationEvents);

        // Get all the notificationEventsList where version is greater than or equal to DEFAULT_VERSION
        defaultNotificationEventsShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the notificationEventsList where version is greater than or equal to UPDATED_VERSION
        defaultNotificationEventsShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllNotificationEventsByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notificationEventsRepository.saveAndFlush(notificationEvents);

        // Get all the notificationEventsList where version is less than or equal to DEFAULT_VERSION
        defaultNotificationEventsShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the notificationEventsList where version is less than or equal to SMALLER_VERSION
        defaultNotificationEventsShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllNotificationEventsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        notificationEventsRepository.saveAndFlush(notificationEvents);

        // Get all the notificationEventsList where version is less than DEFAULT_VERSION
        defaultNotificationEventsShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the notificationEventsList where version is less than UPDATED_VERSION
        defaultNotificationEventsShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllNotificationEventsByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        notificationEventsRepository.saveAndFlush(notificationEvents);

        // Get all the notificationEventsList where version is greater than DEFAULT_VERSION
        defaultNotificationEventsShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the notificationEventsList where version is greater than SMALLER_VERSION
        defaultNotificationEventsShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllNotificationEventsByNotificationmergefieldsNotificationeventIsEqualToSomething() throws Exception {
        NotificationMergeFields notificationmergefieldsNotificationevent;
        if (TestUtil.findAll(em, NotificationMergeFields.class).isEmpty()) {
            notificationEventsRepository.saveAndFlush(notificationEvents);
            notificationmergefieldsNotificationevent = NotificationMergeFieldsResourceIT.createEntity(em);
        } else {
            notificationmergefieldsNotificationevent = TestUtil.findAll(em, NotificationMergeFields.class).get(0);
        }
        em.persist(notificationmergefieldsNotificationevent);
        em.flush();
        notificationEvents.addNotificationmergefieldsNotificationevent(notificationmergefieldsNotificationevent);
        notificationEventsRepository.saveAndFlush(notificationEvents);
        Long notificationmergefieldsNotificationeventId = notificationmergefieldsNotificationevent.getId();

        // Get all the notificationEventsList where notificationmergefieldsNotificationevent equals to notificationmergefieldsNotificationeventId
        defaultNotificationEventsShouldBeFound(
            "notificationmergefieldsNotificationeventId.equals=" + notificationmergefieldsNotificationeventId
        );

        // Get all the notificationEventsList where notificationmergefieldsNotificationevent equals to (notificationmergefieldsNotificationeventId + 1)
        defaultNotificationEventsShouldNotBeFound(
            "notificationmergefieldsNotificationeventId.equals=" + (notificationmergefieldsNotificationeventId + 1)
        );
    }

    @Test
    @Transactional
    void getAllNotificationEventsByNotificationtemplatesNotificationeventIsEqualToSomething() throws Exception {
        NotificationTemplates notificationtemplatesNotificationevent;
        if (TestUtil.findAll(em, NotificationTemplates.class).isEmpty()) {
            notificationEventsRepository.saveAndFlush(notificationEvents);
            notificationtemplatesNotificationevent = NotificationTemplatesResourceIT.createEntity(em);
        } else {
            notificationtemplatesNotificationevent = TestUtil.findAll(em, NotificationTemplates.class).get(0);
        }
        em.persist(notificationtemplatesNotificationevent);
        em.flush();
        notificationEvents.addNotificationtemplatesNotificationevent(notificationtemplatesNotificationevent);
        notificationEventsRepository.saveAndFlush(notificationEvents);
        Long notificationtemplatesNotificationeventId = notificationtemplatesNotificationevent.getId();

        // Get all the notificationEventsList where notificationtemplatesNotificationevent equals to notificationtemplatesNotificationeventId
        defaultNotificationEventsShouldBeFound(
            "notificationtemplatesNotificationeventId.equals=" + notificationtemplatesNotificationeventId
        );

        // Get all the notificationEventsList where notificationtemplatesNotificationevent equals to (notificationtemplatesNotificationeventId + 1)
        defaultNotificationEventsShouldNotBeFound(
            "notificationtemplatesNotificationeventId.equals=" + (notificationtemplatesNotificationeventId + 1)
        );
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNotificationEventsShouldBeFound(String filter) throws Exception {
        restNotificationEventsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notificationEvents.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restNotificationEventsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNotificationEventsShouldNotBeFound(String filter) throws Exception {
        restNotificationEventsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNotificationEventsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNotificationEvents() throws Exception {
        // Get the notificationEvents
        restNotificationEventsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNotificationEvents() throws Exception {
        // Initialize the database
        notificationEventsRepository.saveAndFlush(notificationEvents);

        int databaseSizeBeforeUpdate = notificationEventsRepository.findAll().size();

        // Update the notificationEvents
        NotificationEvents updatedNotificationEvents = notificationEventsRepository.findById(notificationEvents.getId()).get();
        // Disconnect from session so that the updates on updatedNotificationEvents are not directly saved in db
        em.detach(updatedNotificationEvents);
        updatedNotificationEvents
            .name(UPDATED_NAME)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restNotificationEventsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNotificationEvents.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNotificationEvents))
            )
            .andExpect(status().isOk());

        // Validate the NotificationEvents in the database
        List<NotificationEvents> notificationEventsList = notificationEventsRepository.findAll();
        assertThat(notificationEventsList).hasSize(databaseSizeBeforeUpdate);
        NotificationEvents testNotificationEvents = notificationEventsList.get(notificationEventsList.size() - 1);
        assertThat(testNotificationEvents.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNotificationEvents.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testNotificationEvents.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testNotificationEvents.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testNotificationEvents.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testNotificationEvents.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingNotificationEvents() throws Exception {
        int databaseSizeBeforeUpdate = notificationEventsRepository.findAll().size();
        notificationEvents.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificationEventsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, notificationEvents.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationEvents))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationEvents in the database
        List<NotificationEvents> notificationEventsList = notificationEventsRepository.findAll();
        assertThat(notificationEventsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNotificationEvents() throws Exception {
        int databaseSizeBeforeUpdate = notificationEventsRepository.findAll().size();
        notificationEvents.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationEventsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationEvents))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationEvents in the database
        List<NotificationEvents> notificationEventsList = notificationEventsRepository.findAll();
        assertThat(notificationEventsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNotificationEvents() throws Exception {
        int databaseSizeBeforeUpdate = notificationEventsRepository.findAll().size();
        notificationEvents.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationEventsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationEvents))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NotificationEvents in the database
        List<NotificationEvents> notificationEventsList = notificationEventsRepository.findAll();
        assertThat(notificationEventsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNotificationEventsWithPatch() throws Exception {
        // Initialize the database
        notificationEventsRepository.saveAndFlush(notificationEvents);

        int databaseSizeBeforeUpdate = notificationEventsRepository.findAll().size();

        // Update the notificationEvents using partial update
        NotificationEvents partialUpdatedNotificationEvents = new NotificationEvents();
        partialUpdatedNotificationEvents.setId(notificationEvents.getId());

        partialUpdatedNotificationEvents.name(UPDATED_NAME).effDate(UPDATED_EFF_DATE).createdAt(UPDATED_CREATED_AT);

        restNotificationEventsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotificationEvents.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNotificationEvents))
            )
            .andExpect(status().isOk());

        // Validate the NotificationEvents in the database
        List<NotificationEvents> notificationEventsList = notificationEventsRepository.findAll();
        assertThat(notificationEventsList).hasSize(databaseSizeBeforeUpdate);
        NotificationEvents testNotificationEvents = notificationEventsList.get(notificationEventsList.size() - 1);
        assertThat(testNotificationEvents.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNotificationEvents.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testNotificationEvents.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testNotificationEvents.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testNotificationEvents.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testNotificationEvents.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateNotificationEventsWithPatch() throws Exception {
        // Initialize the database
        notificationEventsRepository.saveAndFlush(notificationEvents);

        int databaseSizeBeforeUpdate = notificationEventsRepository.findAll().size();

        // Update the notificationEvents using partial update
        NotificationEvents partialUpdatedNotificationEvents = new NotificationEvents();
        partialUpdatedNotificationEvents.setId(notificationEvents.getId());

        partialUpdatedNotificationEvents
            .name(UPDATED_NAME)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restNotificationEventsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotificationEvents.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNotificationEvents))
            )
            .andExpect(status().isOk());

        // Validate the NotificationEvents in the database
        List<NotificationEvents> notificationEventsList = notificationEventsRepository.findAll();
        assertThat(notificationEventsList).hasSize(databaseSizeBeforeUpdate);
        NotificationEvents testNotificationEvents = notificationEventsList.get(notificationEventsList.size() - 1);
        assertThat(testNotificationEvents.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNotificationEvents.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testNotificationEvents.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testNotificationEvents.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testNotificationEvents.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testNotificationEvents.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingNotificationEvents() throws Exception {
        int databaseSizeBeforeUpdate = notificationEventsRepository.findAll().size();
        notificationEvents.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificationEventsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, notificationEvents.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notificationEvents))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationEvents in the database
        List<NotificationEvents> notificationEventsList = notificationEventsRepository.findAll();
        assertThat(notificationEventsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNotificationEvents() throws Exception {
        int databaseSizeBeforeUpdate = notificationEventsRepository.findAll().size();
        notificationEvents.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationEventsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notificationEvents))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationEvents in the database
        List<NotificationEvents> notificationEventsList = notificationEventsRepository.findAll();
        assertThat(notificationEventsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNotificationEvents() throws Exception {
        int databaseSizeBeforeUpdate = notificationEventsRepository.findAll().size();
        notificationEvents.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationEventsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notificationEvents))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NotificationEvents in the database
        List<NotificationEvents> notificationEventsList = notificationEventsRepository.findAll();
        assertThat(notificationEventsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNotificationEvents() throws Exception {
        // Initialize the database
        notificationEventsRepository.saveAndFlush(notificationEvents);

        int databaseSizeBeforeDelete = notificationEventsRepository.findAll().size();

        // Delete the notificationEvents
        restNotificationEventsMockMvc
            .perform(delete(ENTITY_API_URL_ID, notificationEvents.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NotificationEvents> notificationEventsList = notificationEventsRepository.findAll();
        assertThat(notificationEventsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
