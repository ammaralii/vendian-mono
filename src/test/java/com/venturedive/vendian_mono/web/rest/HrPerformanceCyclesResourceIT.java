package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.HrPerformanceCycles;
import com.venturedive.vendian_mono.domain.PerformanceCycleEmployeeRating;
import com.venturedive.vendian_mono.domain.UserRatings;
import com.venturedive.vendian_mono.repository.HrPerformanceCyclesRepository;
import com.venturedive.vendian_mono.service.criteria.HrPerformanceCyclesCriteria;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link HrPerformanceCyclesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HrPerformanceCyclesResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FREEZE = false;
    private static final Boolean UPDATED_FREEZE = true;

    private static final LocalDate DEFAULT_DUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DUE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DUE_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_START_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_END_DATE = LocalDate.ofEpochDay(-1L);

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;
    private static final Integer SMALLER_VERSION = 1 - 1;

    private static final String ENTITY_API_URL = "/api/hr-performance-cycles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HrPerformanceCyclesRepository hrPerformanceCyclesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHrPerformanceCyclesMockMvc;

    private HrPerformanceCycles hrPerformanceCycles;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HrPerformanceCycles createEntity(EntityManager em) {
        HrPerformanceCycles hrPerformanceCycles = new HrPerformanceCycles()
            .title(DEFAULT_TITLE)
            .freeze(DEFAULT_FREEZE)
            .dueDate(DEFAULT_DUE_DATE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT)
            .version(DEFAULT_VERSION);
        return hrPerformanceCycles;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HrPerformanceCycles createUpdatedEntity(EntityManager em) {
        HrPerformanceCycles hrPerformanceCycles = new HrPerformanceCycles()
            .title(UPDATED_TITLE)
            .freeze(UPDATED_FREEZE)
            .dueDate(UPDATED_DUE_DATE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);
        return hrPerformanceCycles;
    }

    @BeforeEach
    public void initTest() {
        hrPerformanceCycles = createEntity(em);
    }

    @Test
    @Transactional
    void createHrPerformanceCycles() throws Exception {
        int databaseSizeBeforeCreate = hrPerformanceCyclesRepository.findAll().size();
        // Create the HrPerformanceCycles
        restHrPerformanceCyclesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hrPerformanceCycles))
            )
            .andExpect(status().isCreated());

        // Validate the HrPerformanceCycles in the database
        List<HrPerformanceCycles> hrPerformanceCyclesList = hrPerformanceCyclesRepository.findAll();
        assertThat(hrPerformanceCyclesList).hasSize(databaseSizeBeforeCreate + 1);
        HrPerformanceCycles testHrPerformanceCycles = hrPerformanceCyclesList.get(hrPerformanceCyclesList.size() - 1);
        assertThat(testHrPerformanceCycles.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testHrPerformanceCycles.getFreeze()).isEqualTo(DEFAULT_FREEZE);
        assertThat(testHrPerformanceCycles.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testHrPerformanceCycles.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testHrPerformanceCycles.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testHrPerformanceCycles.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testHrPerformanceCycles.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testHrPerformanceCycles.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testHrPerformanceCycles.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createHrPerformanceCyclesWithExistingId() throws Exception {
        // Create the HrPerformanceCycles with an existing ID
        hrPerformanceCycles.setId(1L);

        int databaseSizeBeforeCreate = hrPerformanceCyclesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHrPerformanceCyclesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hrPerformanceCycles))
            )
            .andExpect(status().isBadRequest());

        // Validate the HrPerformanceCycles in the database
        List<HrPerformanceCycles> hrPerformanceCyclesList = hrPerformanceCyclesRepository.findAll();
        assertThat(hrPerformanceCyclesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrPerformanceCyclesRepository.findAll().size();
        // set the field null
        hrPerformanceCycles.setTitle(null);

        // Create the HrPerformanceCycles, which fails.

        restHrPerformanceCyclesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hrPerformanceCycles))
            )
            .andExpect(status().isBadRequest());

        List<HrPerformanceCycles> hrPerformanceCyclesList = hrPerformanceCyclesRepository.findAll();
        assertThat(hrPerformanceCyclesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrPerformanceCyclesRepository.findAll().size();
        // set the field null
        hrPerformanceCycles.setCreatedAt(null);

        // Create the HrPerformanceCycles, which fails.

        restHrPerformanceCyclesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hrPerformanceCycles))
            )
            .andExpect(status().isBadRequest());

        List<HrPerformanceCycles> hrPerformanceCyclesList = hrPerformanceCyclesRepository.findAll();
        assertThat(hrPerformanceCyclesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrPerformanceCyclesRepository.findAll().size();
        // set the field null
        hrPerformanceCycles.setUpdatedAt(null);

        // Create the HrPerformanceCycles, which fails.

        restHrPerformanceCyclesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hrPerformanceCycles))
            )
            .andExpect(status().isBadRequest());

        List<HrPerformanceCycles> hrPerformanceCyclesList = hrPerformanceCyclesRepository.findAll();
        assertThat(hrPerformanceCyclesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = hrPerformanceCyclesRepository.findAll().size();
        // set the field null
        hrPerformanceCycles.setVersion(null);

        // Create the HrPerformanceCycles, which fails.

        restHrPerformanceCyclesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hrPerformanceCycles))
            )
            .andExpect(status().isBadRequest());

        List<HrPerformanceCycles> hrPerformanceCyclesList = hrPerformanceCyclesRepository.findAll();
        assertThat(hrPerformanceCyclesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHrPerformanceCycles() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList
        restHrPerformanceCyclesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hrPerformanceCycles.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].freeze").value(hasItem(DEFAULT_FREEZE.booleanValue())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getHrPerformanceCycles() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get the hrPerformanceCycles
        restHrPerformanceCyclesMockMvc
            .perform(get(ENTITY_API_URL_ID, hrPerformanceCycles.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hrPerformanceCycles.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.freeze").value(DEFAULT_FREEZE.booleanValue()))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getHrPerformanceCyclesByIdFiltering() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        Long id = hrPerformanceCycles.getId();

        defaultHrPerformanceCyclesShouldBeFound("id.equals=" + id);
        defaultHrPerformanceCyclesShouldNotBeFound("id.notEquals=" + id);

        defaultHrPerformanceCyclesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultHrPerformanceCyclesShouldNotBeFound("id.greaterThan=" + id);

        defaultHrPerformanceCyclesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultHrPerformanceCyclesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where title equals to DEFAULT_TITLE
        defaultHrPerformanceCyclesShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the hrPerformanceCyclesList where title equals to UPDATED_TITLE
        defaultHrPerformanceCyclesShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultHrPerformanceCyclesShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the hrPerformanceCyclesList where title equals to UPDATED_TITLE
        defaultHrPerformanceCyclesShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where title is not null
        defaultHrPerformanceCyclesShouldBeFound("title.specified=true");

        // Get all the hrPerformanceCyclesList where title is null
        defaultHrPerformanceCyclesShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByTitleContainsSomething() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where title contains DEFAULT_TITLE
        defaultHrPerformanceCyclesShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the hrPerformanceCyclesList where title contains UPDATED_TITLE
        defaultHrPerformanceCyclesShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where title does not contain DEFAULT_TITLE
        defaultHrPerformanceCyclesShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the hrPerformanceCyclesList where title does not contain UPDATED_TITLE
        defaultHrPerformanceCyclesShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByFreezeIsEqualToSomething() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where freeze equals to DEFAULT_FREEZE
        defaultHrPerformanceCyclesShouldBeFound("freeze.equals=" + DEFAULT_FREEZE);

        // Get all the hrPerformanceCyclesList where freeze equals to UPDATED_FREEZE
        defaultHrPerformanceCyclesShouldNotBeFound("freeze.equals=" + UPDATED_FREEZE);
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByFreezeIsInShouldWork() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where freeze in DEFAULT_FREEZE or UPDATED_FREEZE
        defaultHrPerformanceCyclesShouldBeFound("freeze.in=" + DEFAULT_FREEZE + "," + UPDATED_FREEZE);

        // Get all the hrPerformanceCyclesList where freeze equals to UPDATED_FREEZE
        defaultHrPerformanceCyclesShouldNotBeFound("freeze.in=" + UPDATED_FREEZE);
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByFreezeIsNullOrNotNull() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where freeze is not null
        defaultHrPerformanceCyclesShouldBeFound("freeze.specified=true");

        // Get all the hrPerformanceCyclesList where freeze is null
        defaultHrPerformanceCyclesShouldNotBeFound("freeze.specified=false");
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByDueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where dueDate equals to DEFAULT_DUE_DATE
        defaultHrPerformanceCyclesShouldBeFound("dueDate.equals=" + DEFAULT_DUE_DATE);

        // Get all the hrPerformanceCyclesList where dueDate equals to UPDATED_DUE_DATE
        defaultHrPerformanceCyclesShouldNotBeFound("dueDate.equals=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByDueDateIsInShouldWork() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where dueDate in DEFAULT_DUE_DATE or UPDATED_DUE_DATE
        defaultHrPerformanceCyclesShouldBeFound("dueDate.in=" + DEFAULT_DUE_DATE + "," + UPDATED_DUE_DATE);

        // Get all the hrPerformanceCyclesList where dueDate equals to UPDATED_DUE_DATE
        defaultHrPerformanceCyclesShouldNotBeFound("dueDate.in=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByDueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where dueDate is not null
        defaultHrPerformanceCyclesShouldBeFound("dueDate.specified=true");

        // Get all the hrPerformanceCyclesList where dueDate is null
        defaultHrPerformanceCyclesShouldNotBeFound("dueDate.specified=false");
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByDueDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where dueDate is greater than or equal to DEFAULT_DUE_DATE
        defaultHrPerformanceCyclesShouldBeFound("dueDate.greaterThanOrEqual=" + DEFAULT_DUE_DATE);

        // Get all the hrPerformanceCyclesList where dueDate is greater than or equal to UPDATED_DUE_DATE
        defaultHrPerformanceCyclesShouldNotBeFound("dueDate.greaterThanOrEqual=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByDueDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where dueDate is less than or equal to DEFAULT_DUE_DATE
        defaultHrPerformanceCyclesShouldBeFound("dueDate.lessThanOrEqual=" + DEFAULT_DUE_DATE);

        // Get all the hrPerformanceCyclesList where dueDate is less than or equal to SMALLER_DUE_DATE
        defaultHrPerformanceCyclesShouldNotBeFound("dueDate.lessThanOrEqual=" + SMALLER_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByDueDateIsLessThanSomething() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where dueDate is less than DEFAULT_DUE_DATE
        defaultHrPerformanceCyclesShouldNotBeFound("dueDate.lessThan=" + DEFAULT_DUE_DATE);

        // Get all the hrPerformanceCyclesList where dueDate is less than UPDATED_DUE_DATE
        defaultHrPerformanceCyclesShouldBeFound("dueDate.lessThan=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByDueDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where dueDate is greater than DEFAULT_DUE_DATE
        defaultHrPerformanceCyclesShouldNotBeFound("dueDate.greaterThan=" + DEFAULT_DUE_DATE);

        // Get all the hrPerformanceCyclesList where dueDate is greater than SMALLER_DUE_DATE
        defaultHrPerformanceCyclesShouldBeFound("dueDate.greaterThan=" + SMALLER_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where startDate equals to DEFAULT_START_DATE
        defaultHrPerformanceCyclesShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the hrPerformanceCyclesList where startDate equals to UPDATED_START_DATE
        defaultHrPerformanceCyclesShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultHrPerformanceCyclesShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the hrPerformanceCyclesList where startDate equals to UPDATED_START_DATE
        defaultHrPerformanceCyclesShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where startDate is not null
        defaultHrPerformanceCyclesShouldBeFound("startDate.specified=true");

        // Get all the hrPerformanceCyclesList where startDate is null
        defaultHrPerformanceCyclesShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where startDate is greater than or equal to DEFAULT_START_DATE
        defaultHrPerformanceCyclesShouldBeFound("startDate.greaterThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the hrPerformanceCyclesList where startDate is greater than or equal to UPDATED_START_DATE
        defaultHrPerformanceCyclesShouldNotBeFound("startDate.greaterThanOrEqual=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where startDate is less than or equal to DEFAULT_START_DATE
        defaultHrPerformanceCyclesShouldBeFound("startDate.lessThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the hrPerformanceCyclesList where startDate is less than or equal to SMALLER_START_DATE
        defaultHrPerformanceCyclesShouldNotBeFound("startDate.lessThanOrEqual=" + SMALLER_START_DATE);
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where startDate is less than DEFAULT_START_DATE
        defaultHrPerformanceCyclesShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the hrPerformanceCyclesList where startDate is less than UPDATED_START_DATE
        defaultHrPerformanceCyclesShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where startDate is greater than DEFAULT_START_DATE
        defaultHrPerformanceCyclesShouldNotBeFound("startDate.greaterThan=" + DEFAULT_START_DATE);

        // Get all the hrPerformanceCyclesList where startDate is greater than SMALLER_START_DATE
        defaultHrPerformanceCyclesShouldBeFound("startDate.greaterThan=" + SMALLER_START_DATE);
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where endDate equals to DEFAULT_END_DATE
        defaultHrPerformanceCyclesShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the hrPerformanceCyclesList where endDate equals to UPDATED_END_DATE
        defaultHrPerformanceCyclesShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultHrPerformanceCyclesShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the hrPerformanceCyclesList where endDate equals to UPDATED_END_DATE
        defaultHrPerformanceCyclesShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where endDate is not null
        defaultHrPerformanceCyclesShouldBeFound("endDate.specified=true");

        // Get all the hrPerformanceCyclesList where endDate is null
        defaultHrPerformanceCyclesShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where endDate is greater than or equal to DEFAULT_END_DATE
        defaultHrPerformanceCyclesShouldBeFound("endDate.greaterThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the hrPerformanceCyclesList where endDate is greater than or equal to UPDATED_END_DATE
        defaultHrPerformanceCyclesShouldNotBeFound("endDate.greaterThanOrEqual=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByEndDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where endDate is less than or equal to DEFAULT_END_DATE
        defaultHrPerformanceCyclesShouldBeFound("endDate.lessThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the hrPerformanceCyclesList where endDate is less than or equal to SMALLER_END_DATE
        defaultHrPerformanceCyclesShouldNotBeFound("endDate.lessThanOrEqual=" + SMALLER_END_DATE);
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where endDate is less than DEFAULT_END_DATE
        defaultHrPerformanceCyclesShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

        // Get all the hrPerformanceCyclesList where endDate is less than UPDATED_END_DATE
        defaultHrPerformanceCyclesShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByEndDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where endDate is greater than DEFAULT_END_DATE
        defaultHrPerformanceCyclesShouldNotBeFound("endDate.greaterThan=" + DEFAULT_END_DATE);

        // Get all the hrPerformanceCyclesList where endDate is greater than SMALLER_END_DATE
        defaultHrPerformanceCyclesShouldBeFound("endDate.greaterThan=" + SMALLER_END_DATE);
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where createdAt equals to DEFAULT_CREATED_AT
        defaultHrPerformanceCyclesShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the hrPerformanceCyclesList where createdAt equals to UPDATED_CREATED_AT
        defaultHrPerformanceCyclesShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultHrPerformanceCyclesShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the hrPerformanceCyclesList where createdAt equals to UPDATED_CREATED_AT
        defaultHrPerformanceCyclesShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where createdAt is not null
        defaultHrPerformanceCyclesShouldBeFound("createdAt.specified=true");

        // Get all the hrPerformanceCyclesList where createdAt is null
        defaultHrPerformanceCyclesShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultHrPerformanceCyclesShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the hrPerformanceCyclesList where updatedAt equals to UPDATED_UPDATED_AT
        defaultHrPerformanceCyclesShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultHrPerformanceCyclesShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the hrPerformanceCyclesList where updatedAt equals to UPDATED_UPDATED_AT
        defaultHrPerformanceCyclesShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where updatedAt is not null
        defaultHrPerformanceCyclesShouldBeFound("updatedAt.specified=true");

        // Get all the hrPerformanceCyclesList where updatedAt is null
        defaultHrPerformanceCyclesShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByDeletedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where deletedAt equals to DEFAULT_DELETED_AT
        defaultHrPerformanceCyclesShouldBeFound("deletedAt.equals=" + DEFAULT_DELETED_AT);

        // Get all the hrPerformanceCyclesList where deletedAt equals to UPDATED_DELETED_AT
        defaultHrPerformanceCyclesShouldNotBeFound("deletedAt.equals=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByDeletedAtIsInShouldWork() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where deletedAt in DEFAULT_DELETED_AT or UPDATED_DELETED_AT
        defaultHrPerformanceCyclesShouldBeFound("deletedAt.in=" + DEFAULT_DELETED_AT + "," + UPDATED_DELETED_AT);

        // Get all the hrPerformanceCyclesList where deletedAt equals to UPDATED_DELETED_AT
        defaultHrPerformanceCyclesShouldNotBeFound("deletedAt.in=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByDeletedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where deletedAt is not null
        defaultHrPerformanceCyclesShouldBeFound("deletedAt.specified=true");

        // Get all the hrPerformanceCyclesList where deletedAt is null
        defaultHrPerformanceCyclesShouldNotBeFound("deletedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where version equals to DEFAULT_VERSION
        defaultHrPerformanceCyclesShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the hrPerformanceCyclesList where version equals to UPDATED_VERSION
        defaultHrPerformanceCyclesShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultHrPerformanceCyclesShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the hrPerformanceCyclesList where version equals to UPDATED_VERSION
        defaultHrPerformanceCyclesShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where version is not null
        defaultHrPerformanceCyclesShouldBeFound("version.specified=true");

        // Get all the hrPerformanceCyclesList where version is null
        defaultHrPerformanceCyclesShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where version is greater than or equal to DEFAULT_VERSION
        defaultHrPerformanceCyclesShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the hrPerformanceCyclesList where version is greater than or equal to UPDATED_VERSION
        defaultHrPerformanceCyclesShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where version is less than or equal to DEFAULT_VERSION
        defaultHrPerformanceCyclesShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the hrPerformanceCyclesList where version is less than or equal to SMALLER_VERSION
        defaultHrPerformanceCyclesShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where version is less than DEFAULT_VERSION
        defaultHrPerformanceCyclesShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the hrPerformanceCyclesList where version is less than UPDATED_VERSION
        defaultHrPerformanceCyclesShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        // Get all the hrPerformanceCyclesList where version is greater than DEFAULT_VERSION
        defaultHrPerformanceCyclesShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the hrPerformanceCyclesList where version is greater than SMALLER_VERSION
        defaultHrPerformanceCyclesShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByPerformancecycleemployeeratingPerformancecycleIsEqualToSomething() throws Exception {
        PerformanceCycleEmployeeRating performancecycleemployeeratingPerformancecycle;
        if (TestUtil.findAll(em, PerformanceCycleEmployeeRating.class).isEmpty()) {
            hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);
            performancecycleemployeeratingPerformancecycle = PerformanceCycleEmployeeRatingResourceIT.createEntity(em);
        } else {
            performancecycleemployeeratingPerformancecycle = TestUtil.findAll(em, PerformanceCycleEmployeeRating.class).get(0);
        }
        em.persist(performancecycleemployeeratingPerformancecycle);
        em.flush();
        hrPerformanceCycles.addPerformancecycleemployeeratingPerformancecycle(performancecycleemployeeratingPerformancecycle);
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);
        Long performancecycleemployeeratingPerformancecycleId = performancecycleemployeeratingPerformancecycle.getId();

        // Get all the hrPerformanceCyclesList where performancecycleemployeeratingPerformancecycle equals to performancecycleemployeeratingPerformancecycleId
        defaultHrPerformanceCyclesShouldBeFound(
            "performancecycleemployeeratingPerformancecycleId.equals=" + performancecycleemployeeratingPerformancecycleId
        );

        // Get all the hrPerformanceCyclesList where performancecycleemployeeratingPerformancecycle equals to (performancecycleemployeeratingPerformancecycleId + 1)
        defaultHrPerformanceCyclesShouldNotBeFound(
            "performancecycleemployeeratingPerformancecycleId.equals=" + (performancecycleemployeeratingPerformancecycleId + 1)
        );
    }

    @Test
    @Transactional
    void getAllHrPerformanceCyclesByUserratingsPerformancecycleIsEqualToSomething() throws Exception {
        UserRatings userratingsPerformancecycle;
        if (TestUtil.findAll(em, UserRatings.class).isEmpty()) {
            hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);
            userratingsPerformancecycle = UserRatingsResourceIT.createEntity(em);
        } else {
            userratingsPerformancecycle = TestUtil.findAll(em, UserRatings.class).get(0);
        }
        em.persist(userratingsPerformancecycle);
        em.flush();
        hrPerformanceCycles.addUserratingsPerformancecycle(userratingsPerformancecycle);
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);
        Long userratingsPerformancecycleId = userratingsPerformancecycle.getId();

        // Get all the hrPerformanceCyclesList where userratingsPerformancecycle equals to userratingsPerformancecycleId
        defaultHrPerformanceCyclesShouldBeFound("userratingsPerformancecycleId.equals=" + userratingsPerformancecycleId);

        // Get all the hrPerformanceCyclesList where userratingsPerformancecycle equals to (userratingsPerformancecycleId + 1)
        defaultHrPerformanceCyclesShouldNotBeFound("userratingsPerformancecycleId.equals=" + (userratingsPerformancecycleId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultHrPerformanceCyclesShouldBeFound(String filter) throws Exception {
        restHrPerformanceCyclesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hrPerformanceCycles.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].freeze").value(hasItem(DEFAULT_FREEZE.booleanValue())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restHrPerformanceCyclesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultHrPerformanceCyclesShouldNotBeFound(String filter) throws Exception {
        restHrPerformanceCyclesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restHrPerformanceCyclesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingHrPerformanceCycles() throws Exception {
        // Get the hrPerformanceCycles
        restHrPerformanceCyclesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHrPerformanceCycles() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        int databaseSizeBeforeUpdate = hrPerformanceCyclesRepository.findAll().size();

        // Update the hrPerformanceCycles
        HrPerformanceCycles updatedHrPerformanceCycles = hrPerformanceCyclesRepository.findById(hrPerformanceCycles.getId()).get();
        // Disconnect from session so that the updates on updatedHrPerformanceCycles are not directly saved in db
        em.detach(updatedHrPerformanceCycles);
        updatedHrPerformanceCycles
            .title(UPDATED_TITLE)
            .freeze(UPDATED_FREEZE)
            .dueDate(UPDATED_DUE_DATE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restHrPerformanceCyclesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHrPerformanceCycles.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedHrPerformanceCycles))
            )
            .andExpect(status().isOk());

        // Validate the HrPerformanceCycles in the database
        List<HrPerformanceCycles> hrPerformanceCyclesList = hrPerformanceCyclesRepository.findAll();
        assertThat(hrPerformanceCyclesList).hasSize(databaseSizeBeforeUpdate);
        HrPerformanceCycles testHrPerformanceCycles = hrPerformanceCyclesList.get(hrPerformanceCyclesList.size() - 1);
        assertThat(testHrPerformanceCycles.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testHrPerformanceCycles.getFreeze()).isEqualTo(UPDATED_FREEZE);
        assertThat(testHrPerformanceCycles.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testHrPerformanceCycles.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testHrPerformanceCycles.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testHrPerformanceCycles.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testHrPerformanceCycles.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testHrPerformanceCycles.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testHrPerformanceCycles.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingHrPerformanceCycles() throws Exception {
        int databaseSizeBeforeUpdate = hrPerformanceCyclesRepository.findAll().size();
        hrPerformanceCycles.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHrPerformanceCyclesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hrPerformanceCycles.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hrPerformanceCycles))
            )
            .andExpect(status().isBadRequest());

        // Validate the HrPerformanceCycles in the database
        List<HrPerformanceCycles> hrPerformanceCyclesList = hrPerformanceCyclesRepository.findAll();
        assertThat(hrPerformanceCyclesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHrPerformanceCycles() throws Exception {
        int databaseSizeBeforeUpdate = hrPerformanceCyclesRepository.findAll().size();
        hrPerformanceCycles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHrPerformanceCyclesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hrPerformanceCycles))
            )
            .andExpect(status().isBadRequest());

        // Validate the HrPerformanceCycles in the database
        List<HrPerformanceCycles> hrPerformanceCyclesList = hrPerformanceCyclesRepository.findAll();
        assertThat(hrPerformanceCyclesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHrPerformanceCycles() throws Exception {
        int databaseSizeBeforeUpdate = hrPerformanceCyclesRepository.findAll().size();
        hrPerformanceCycles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHrPerformanceCyclesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hrPerformanceCycles))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HrPerformanceCycles in the database
        List<HrPerformanceCycles> hrPerformanceCyclesList = hrPerformanceCyclesRepository.findAll();
        assertThat(hrPerformanceCyclesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHrPerformanceCyclesWithPatch() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        int databaseSizeBeforeUpdate = hrPerformanceCyclesRepository.findAll().size();

        // Update the hrPerformanceCycles using partial update
        HrPerformanceCycles partialUpdatedHrPerformanceCycles = new HrPerformanceCycles();
        partialUpdatedHrPerformanceCycles.setId(hrPerformanceCycles.getId());

        partialUpdatedHrPerformanceCycles
            .title(UPDATED_TITLE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .createdAt(UPDATED_CREATED_AT);

        restHrPerformanceCyclesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHrPerformanceCycles.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHrPerformanceCycles))
            )
            .andExpect(status().isOk());

        // Validate the HrPerformanceCycles in the database
        List<HrPerformanceCycles> hrPerformanceCyclesList = hrPerformanceCyclesRepository.findAll();
        assertThat(hrPerformanceCyclesList).hasSize(databaseSizeBeforeUpdate);
        HrPerformanceCycles testHrPerformanceCycles = hrPerformanceCyclesList.get(hrPerformanceCyclesList.size() - 1);
        assertThat(testHrPerformanceCycles.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testHrPerformanceCycles.getFreeze()).isEqualTo(DEFAULT_FREEZE);
        assertThat(testHrPerformanceCycles.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testHrPerformanceCycles.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testHrPerformanceCycles.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testHrPerformanceCycles.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testHrPerformanceCycles.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testHrPerformanceCycles.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testHrPerformanceCycles.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateHrPerformanceCyclesWithPatch() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        int databaseSizeBeforeUpdate = hrPerformanceCyclesRepository.findAll().size();

        // Update the hrPerformanceCycles using partial update
        HrPerformanceCycles partialUpdatedHrPerformanceCycles = new HrPerformanceCycles();
        partialUpdatedHrPerformanceCycles.setId(hrPerformanceCycles.getId());

        partialUpdatedHrPerformanceCycles
            .title(UPDATED_TITLE)
            .freeze(UPDATED_FREEZE)
            .dueDate(UPDATED_DUE_DATE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restHrPerformanceCyclesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHrPerformanceCycles.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHrPerformanceCycles))
            )
            .andExpect(status().isOk());

        // Validate the HrPerformanceCycles in the database
        List<HrPerformanceCycles> hrPerformanceCyclesList = hrPerformanceCyclesRepository.findAll();
        assertThat(hrPerformanceCyclesList).hasSize(databaseSizeBeforeUpdate);
        HrPerformanceCycles testHrPerformanceCycles = hrPerformanceCyclesList.get(hrPerformanceCyclesList.size() - 1);
        assertThat(testHrPerformanceCycles.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testHrPerformanceCycles.getFreeze()).isEqualTo(UPDATED_FREEZE);
        assertThat(testHrPerformanceCycles.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testHrPerformanceCycles.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testHrPerformanceCycles.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testHrPerformanceCycles.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testHrPerformanceCycles.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testHrPerformanceCycles.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testHrPerformanceCycles.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingHrPerformanceCycles() throws Exception {
        int databaseSizeBeforeUpdate = hrPerformanceCyclesRepository.findAll().size();
        hrPerformanceCycles.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHrPerformanceCyclesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hrPerformanceCycles.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hrPerformanceCycles))
            )
            .andExpect(status().isBadRequest());

        // Validate the HrPerformanceCycles in the database
        List<HrPerformanceCycles> hrPerformanceCyclesList = hrPerformanceCyclesRepository.findAll();
        assertThat(hrPerformanceCyclesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHrPerformanceCycles() throws Exception {
        int databaseSizeBeforeUpdate = hrPerformanceCyclesRepository.findAll().size();
        hrPerformanceCycles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHrPerformanceCyclesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hrPerformanceCycles))
            )
            .andExpect(status().isBadRequest());

        // Validate the HrPerformanceCycles in the database
        List<HrPerformanceCycles> hrPerformanceCyclesList = hrPerformanceCyclesRepository.findAll();
        assertThat(hrPerformanceCyclesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHrPerformanceCycles() throws Exception {
        int databaseSizeBeforeUpdate = hrPerformanceCyclesRepository.findAll().size();
        hrPerformanceCycles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHrPerformanceCyclesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hrPerformanceCycles))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HrPerformanceCycles in the database
        List<HrPerformanceCycles> hrPerformanceCyclesList = hrPerformanceCyclesRepository.findAll();
        assertThat(hrPerformanceCyclesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHrPerformanceCycles() throws Exception {
        // Initialize the database
        hrPerformanceCyclesRepository.saveAndFlush(hrPerformanceCycles);

        int databaseSizeBeforeDelete = hrPerformanceCyclesRepository.findAll().size();

        // Delete the hrPerformanceCycles
        restHrPerformanceCyclesMockMvc
            .perform(delete(ENTITY_API_URL_ID, hrPerformanceCycles.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HrPerformanceCycles> hrPerformanceCyclesList = hrPerformanceCyclesRepository.findAll();
        assertThat(hrPerformanceCyclesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
