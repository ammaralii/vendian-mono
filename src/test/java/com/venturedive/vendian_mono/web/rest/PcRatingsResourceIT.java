package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.PcRaterAttributes;
import com.venturedive.vendian_mono.domain.PcRatings;
import com.venturedive.vendian_mono.domain.PcRatingsTrainings;
import com.venturedive.vendian_mono.domain.PcStatuses;
import com.venturedive.vendian_mono.domain.PerformanceCycleEmployeeRating;
import com.venturedive.vendian_mono.domain.UserGoalRaterAttributes;
import com.venturedive.vendian_mono.repository.PcRatingsRepository;
import com.venturedive.vendian_mono.service.criteria.PcRatingsCriteria;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link PcRatingsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PcRatingsResourceIT {

    private static final byte[] DEFAULT_RATING = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_RATING = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_RATING_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_RATING_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_COMMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_COMMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_COMMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_COMMENT_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_STAUS_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STAUS_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final LocalDate DEFAULT_DUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DUE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DUE_DATE = LocalDate.ofEpochDay(-1L);

    private static final Boolean DEFAULT_FREEZE = false;
    private static final Boolean UPDATED_FREEZE = true;

    private static final Boolean DEFAULT_INCLUDE_IN_FINAL_RATING = false;
    private static final Boolean UPDATED_INCLUDE_IN_FINAL_RATING = true;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;
    private static final Integer SMALLER_VERSION = 1 - 1;

    private static final String ENTITY_API_URL = "/api/pc-ratings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PcRatingsRepository pcRatingsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPcRatingsMockMvc;

    private PcRatings pcRatings;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PcRatings createEntity(EntityManager em) {
        PcRatings pcRatings = new PcRatings()
            .rating(DEFAULT_RATING)
            .ratingContentType(DEFAULT_RATING_CONTENT_TYPE)
            .comment(DEFAULT_COMMENT)
            .commentContentType(DEFAULT_COMMENT_CONTENT_TYPE)
            .stausDate(DEFAULT_STAUS_DATE)
            .dueDate(DEFAULT_DUE_DATE)
            .freeze(DEFAULT_FREEZE)
            .includeInFinalRating(DEFAULT_INCLUDE_IN_FINAL_RATING)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT)
            .version(DEFAULT_VERSION);
        // Add required entity
        PerformanceCycleEmployeeRating performanceCycleEmployeeRating;
        if (TestUtil.findAll(em, PerformanceCycleEmployeeRating.class).isEmpty()) {
            performanceCycleEmployeeRating = PerformanceCycleEmployeeRatingResourceIT.createEntity(em);
            em.persist(performanceCycleEmployeeRating);
            em.flush();
        } else {
            performanceCycleEmployeeRating = TestUtil.findAll(em, PerformanceCycleEmployeeRating.class).get(0);
        }
        pcRatings.setPcemployeerating(performanceCycleEmployeeRating);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        pcRatings.setEmployee(employees);
        return pcRatings;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PcRatings createUpdatedEntity(EntityManager em) {
        PcRatings pcRatings = new PcRatings()
            .rating(UPDATED_RATING)
            .ratingContentType(UPDATED_RATING_CONTENT_TYPE)
            .comment(UPDATED_COMMENT)
            .commentContentType(UPDATED_COMMENT_CONTENT_TYPE)
            .stausDate(UPDATED_STAUS_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .freeze(UPDATED_FREEZE)
            .includeInFinalRating(UPDATED_INCLUDE_IN_FINAL_RATING)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);
        // Add required entity
        PerformanceCycleEmployeeRating performanceCycleEmployeeRating;
        if (TestUtil.findAll(em, PerformanceCycleEmployeeRating.class).isEmpty()) {
            performanceCycleEmployeeRating = PerformanceCycleEmployeeRatingResourceIT.createUpdatedEntity(em);
            em.persist(performanceCycleEmployeeRating);
            em.flush();
        } else {
            performanceCycleEmployeeRating = TestUtil.findAll(em, PerformanceCycleEmployeeRating.class).get(0);
        }
        pcRatings.setPcemployeerating(performanceCycleEmployeeRating);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createUpdatedEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        pcRatings.setEmployee(employees);
        return pcRatings;
    }

    @BeforeEach
    public void initTest() {
        pcRatings = createEntity(em);
    }

    @Test
    @Transactional
    void createPcRatings() throws Exception {
        int databaseSizeBeforeCreate = pcRatingsRepository.findAll().size();
        // Create the PcRatings
        restPcRatingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRatings))
            )
            .andExpect(status().isCreated());

        // Validate the PcRatings in the database
        List<PcRatings> pcRatingsList = pcRatingsRepository.findAll();
        assertThat(pcRatingsList).hasSize(databaseSizeBeforeCreate + 1);
        PcRatings testPcRatings = pcRatingsList.get(pcRatingsList.size() - 1);
        assertThat(testPcRatings.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testPcRatings.getRatingContentType()).isEqualTo(DEFAULT_RATING_CONTENT_TYPE);
        assertThat(testPcRatings.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testPcRatings.getCommentContentType()).isEqualTo(DEFAULT_COMMENT_CONTENT_TYPE);
        assertThat(testPcRatings.getStausDate()).isEqualTo(DEFAULT_STAUS_DATE);
        assertThat(testPcRatings.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testPcRatings.getFreeze()).isEqualTo(DEFAULT_FREEZE);
        assertThat(testPcRatings.getIncludeInFinalRating()).isEqualTo(DEFAULT_INCLUDE_IN_FINAL_RATING);
        assertThat(testPcRatings.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testPcRatings.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testPcRatings.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testPcRatings.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createPcRatingsWithExistingId() throws Exception {
        // Create the PcRatings with an existing ID
        pcRatings.setId(1L);

        int databaseSizeBeforeCreate = pcRatingsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPcRatingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRatings))
            )
            .andExpect(status().isBadRequest());

        // Validate the PcRatings in the database
        List<PcRatings> pcRatingsList = pcRatingsRepository.findAll();
        assertThat(pcRatingsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = pcRatingsRepository.findAll().size();
        // set the field null
        pcRatings.setCreatedAt(null);

        // Create the PcRatings, which fails.

        restPcRatingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRatings))
            )
            .andExpect(status().isBadRequest());

        List<PcRatings> pcRatingsList = pcRatingsRepository.findAll();
        assertThat(pcRatingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = pcRatingsRepository.findAll().size();
        // set the field null
        pcRatings.setUpdatedAt(null);

        // Create the PcRatings, which fails.

        restPcRatingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRatings))
            )
            .andExpect(status().isBadRequest());

        List<PcRatings> pcRatingsList = pcRatingsRepository.findAll();
        assertThat(pcRatingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = pcRatingsRepository.findAll().size();
        // set the field null
        pcRatings.setVersion(null);

        // Create the PcRatings, which fails.

        restPcRatingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRatings))
            )
            .andExpect(status().isBadRequest());

        List<PcRatings> pcRatingsList = pcRatingsRepository.findAll();
        assertThat(pcRatingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPcRatings() throws Exception {
        // Initialize the database
        pcRatingsRepository.saveAndFlush(pcRatings);

        // Get all the pcRatingsList
        restPcRatingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pcRatings.getId().intValue())))
            .andExpect(jsonPath("$.[*].ratingContentType").value(hasItem(DEFAULT_RATING_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(Base64Utils.encodeToString(DEFAULT_RATING))))
            .andExpect(jsonPath("$.[*].commentContentType").value(hasItem(DEFAULT_COMMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(Base64Utils.encodeToString(DEFAULT_COMMENT))))
            .andExpect(jsonPath("$.[*].stausDate").value(hasItem(DEFAULT_STAUS_DATE.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].freeze").value(hasItem(DEFAULT_FREEZE.booleanValue())))
            .andExpect(jsonPath("$.[*].includeInFinalRating").value(hasItem(DEFAULT_INCLUDE_IN_FINAL_RATING.booleanValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getPcRatings() throws Exception {
        // Initialize the database
        pcRatingsRepository.saveAndFlush(pcRatings);

        // Get the pcRatings
        restPcRatingsMockMvc
            .perform(get(ENTITY_API_URL_ID, pcRatings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pcRatings.getId().intValue()))
            .andExpect(jsonPath("$.ratingContentType").value(DEFAULT_RATING_CONTENT_TYPE))
            .andExpect(jsonPath("$.rating").value(Base64Utils.encodeToString(DEFAULT_RATING)))
            .andExpect(jsonPath("$.commentContentType").value(DEFAULT_COMMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.comment").value(Base64Utils.encodeToString(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.stausDate").value(DEFAULT_STAUS_DATE.toString()))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.freeze").value(DEFAULT_FREEZE.booleanValue()))
            .andExpect(jsonPath("$.includeInFinalRating").value(DEFAULT_INCLUDE_IN_FINAL_RATING.booleanValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getPcRatingsByIdFiltering() throws Exception {
        // Initialize the database
        pcRatingsRepository.saveAndFlush(pcRatings);

        Long id = pcRatings.getId();

        defaultPcRatingsShouldBeFound("id.equals=" + id);
        defaultPcRatingsShouldNotBeFound("id.notEquals=" + id);

        defaultPcRatingsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPcRatingsShouldNotBeFound("id.greaterThan=" + id);

        defaultPcRatingsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPcRatingsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPcRatingsByStausDateIsEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingsRepository.saveAndFlush(pcRatings);

        // Get all the pcRatingsList where stausDate equals to DEFAULT_STAUS_DATE
        defaultPcRatingsShouldBeFound("stausDate.equals=" + DEFAULT_STAUS_DATE);

        // Get all the pcRatingsList where stausDate equals to UPDATED_STAUS_DATE
        defaultPcRatingsShouldNotBeFound("stausDate.equals=" + UPDATED_STAUS_DATE);
    }

    @Test
    @Transactional
    void getAllPcRatingsByStausDateIsInShouldWork() throws Exception {
        // Initialize the database
        pcRatingsRepository.saveAndFlush(pcRatings);

        // Get all the pcRatingsList where stausDate in DEFAULT_STAUS_DATE or UPDATED_STAUS_DATE
        defaultPcRatingsShouldBeFound("stausDate.in=" + DEFAULT_STAUS_DATE + "," + UPDATED_STAUS_DATE);

        // Get all the pcRatingsList where stausDate equals to UPDATED_STAUS_DATE
        defaultPcRatingsShouldNotBeFound("stausDate.in=" + UPDATED_STAUS_DATE);
    }

    @Test
    @Transactional
    void getAllPcRatingsByStausDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcRatingsRepository.saveAndFlush(pcRatings);

        // Get all the pcRatingsList where stausDate is not null
        defaultPcRatingsShouldBeFound("stausDate.specified=true");

        // Get all the pcRatingsList where stausDate is null
        defaultPcRatingsShouldNotBeFound("stausDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPcRatingsByDueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingsRepository.saveAndFlush(pcRatings);

        // Get all the pcRatingsList where dueDate equals to DEFAULT_DUE_DATE
        defaultPcRatingsShouldBeFound("dueDate.equals=" + DEFAULT_DUE_DATE);

        // Get all the pcRatingsList where dueDate equals to UPDATED_DUE_DATE
        defaultPcRatingsShouldNotBeFound("dueDate.equals=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllPcRatingsByDueDateIsInShouldWork() throws Exception {
        // Initialize the database
        pcRatingsRepository.saveAndFlush(pcRatings);

        // Get all the pcRatingsList where dueDate in DEFAULT_DUE_DATE or UPDATED_DUE_DATE
        defaultPcRatingsShouldBeFound("dueDate.in=" + DEFAULT_DUE_DATE + "," + UPDATED_DUE_DATE);

        // Get all the pcRatingsList where dueDate equals to UPDATED_DUE_DATE
        defaultPcRatingsShouldNotBeFound("dueDate.in=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllPcRatingsByDueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcRatingsRepository.saveAndFlush(pcRatings);

        // Get all the pcRatingsList where dueDate is not null
        defaultPcRatingsShouldBeFound("dueDate.specified=true");

        // Get all the pcRatingsList where dueDate is null
        defaultPcRatingsShouldNotBeFound("dueDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPcRatingsByDueDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingsRepository.saveAndFlush(pcRatings);

        // Get all the pcRatingsList where dueDate is greater than or equal to DEFAULT_DUE_DATE
        defaultPcRatingsShouldBeFound("dueDate.greaterThanOrEqual=" + DEFAULT_DUE_DATE);

        // Get all the pcRatingsList where dueDate is greater than or equal to UPDATED_DUE_DATE
        defaultPcRatingsShouldNotBeFound("dueDate.greaterThanOrEqual=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllPcRatingsByDueDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingsRepository.saveAndFlush(pcRatings);

        // Get all the pcRatingsList where dueDate is less than or equal to DEFAULT_DUE_DATE
        defaultPcRatingsShouldBeFound("dueDate.lessThanOrEqual=" + DEFAULT_DUE_DATE);

        // Get all the pcRatingsList where dueDate is less than or equal to SMALLER_DUE_DATE
        defaultPcRatingsShouldNotBeFound("dueDate.lessThanOrEqual=" + SMALLER_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllPcRatingsByDueDateIsLessThanSomething() throws Exception {
        // Initialize the database
        pcRatingsRepository.saveAndFlush(pcRatings);

        // Get all the pcRatingsList where dueDate is less than DEFAULT_DUE_DATE
        defaultPcRatingsShouldNotBeFound("dueDate.lessThan=" + DEFAULT_DUE_DATE);

        // Get all the pcRatingsList where dueDate is less than UPDATED_DUE_DATE
        defaultPcRatingsShouldBeFound("dueDate.lessThan=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllPcRatingsByDueDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pcRatingsRepository.saveAndFlush(pcRatings);

        // Get all the pcRatingsList where dueDate is greater than DEFAULT_DUE_DATE
        defaultPcRatingsShouldNotBeFound("dueDate.greaterThan=" + DEFAULT_DUE_DATE);

        // Get all the pcRatingsList where dueDate is greater than SMALLER_DUE_DATE
        defaultPcRatingsShouldBeFound("dueDate.greaterThan=" + SMALLER_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllPcRatingsByFreezeIsEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingsRepository.saveAndFlush(pcRatings);

        // Get all the pcRatingsList where freeze equals to DEFAULT_FREEZE
        defaultPcRatingsShouldBeFound("freeze.equals=" + DEFAULT_FREEZE);

        // Get all the pcRatingsList where freeze equals to UPDATED_FREEZE
        defaultPcRatingsShouldNotBeFound("freeze.equals=" + UPDATED_FREEZE);
    }

    @Test
    @Transactional
    void getAllPcRatingsByFreezeIsInShouldWork() throws Exception {
        // Initialize the database
        pcRatingsRepository.saveAndFlush(pcRatings);

        // Get all the pcRatingsList where freeze in DEFAULT_FREEZE or UPDATED_FREEZE
        defaultPcRatingsShouldBeFound("freeze.in=" + DEFAULT_FREEZE + "," + UPDATED_FREEZE);

        // Get all the pcRatingsList where freeze equals to UPDATED_FREEZE
        defaultPcRatingsShouldNotBeFound("freeze.in=" + UPDATED_FREEZE);
    }

    @Test
    @Transactional
    void getAllPcRatingsByFreezeIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcRatingsRepository.saveAndFlush(pcRatings);

        // Get all the pcRatingsList where freeze is not null
        defaultPcRatingsShouldBeFound("freeze.specified=true");

        // Get all the pcRatingsList where freeze is null
        defaultPcRatingsShouldNotBeFound("freeze.specified=false");
    }

    @Test
    @Transactional
    void getAllPcRatingsByIncludeInFinalRatingIsEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingsRepository.saveAndFlush(pcRatings);

        // Get all the pcRatingsList where includeInFinalRating equals to DEFAULT_INCLUDE_IN_FINAL_RATING
        defaultPcRatingsShouldBeFound("includeInFinalRating.equals=" + DEFAULT_INCLUDE_IN_FINAL_RATING);

        // Get all the pcRatingsList where includeInFinalRating equals to UPDATED_INCLUDE_IN_FINAL_RATING
        defaultPcRatingsShouldNotBeFound("includeInFinalRating.equals=" + UPDATED_INCLUDE_IN_FINAL_RATING);
    }

    @Test
    @Transactional
    void getAllPcRatingsByIncludeInFinalRatingIsInShouldWork() throws Exception {
        // Initialize the database
        pcRatingsRepository.saveAndFlush(pcRatings);

        // Get all the pcRatingsList where includeInFinalRating in DEFAULT_INCLUDE_IN_FINAL_RATING or UPDATED_INCLUDE_IN_FINAL_RATING
        defaultPcRatingsShouldBeFound("includeInFinalRating.in=" + DEFAULT_INCLUDE_IN_FINAL_RATING + "," + UPDATED_INCLUDE_IN_FINAL_RATING);

        // Get all the pcRatingsList where includeInFinalRating equals to UPDATED_INCLUDE_IN_FINAL_RATING
        defaultPcRatingsShouldNotBeFound("includeInFinalRating.in=" + UPDATED_INCLUDE_IN_FINAL_RATING);
    }

    @Test
    @Transactional
    void getAllPcRatingsByIncludeInFinalRatingIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcRatingsRepository.saveAndFlush(pcRatings);

        // Get all the pcRatingsList where includeInFinalRating is not null
        defaultPcRatingsShouldBeFound("includeInFinalRating.specified=true");

        // Get all the pcRatingsList where includeInFinalRating is null
        defaultPcRatingsShouldNotBeFound("includeInFinalRating.specified=false");
    }

    @Test
    @Transactional
    void getAllPcRatingsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingsRepository.saveAndFlush(pcRatings);

        // Get all the pcRatingsList where createdAt equals to DEFAULT_CREATED_AT
        defaultPcRatingsShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the pcRatingsList where createdAt equals to UPDATED_CREATED_AT
        defaultPcRatingsShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllPcRatingsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        pcRatingsRepository.saveAndFlush(pcRatings);

        // Get all the pcRatingsList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultPcRatingsShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the pcRatingsList where createdAt equals to UPDATED_CREATED_AT
        defaultPcRatingsShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllPcRatingsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcRatingsRepository.saveAndFlush(pcRatings);

        // Get all the pcRatingsList where createdAt is not null
        defaultPcRatingsShouldBeFound("createdAt.specified=true");

        // Get all the pcRatingsList where createdAt is null
        defaultPcRatingsShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllPcRatingsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingsRepository.saveAndFlush(pcRatings);

        // Get all the pcRatingsList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultPcRatingsShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the pcRatingsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultPcRatingsShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllPcRatingsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        pcRatingsRepository.saveAndFlush(pcRatings);

        // Get all the pcRatingsList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultPcRatingsShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the pcRatingsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultPcRatingsShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllPcRatingsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcRatingsRepository.saveAndFlush(pcRatings);

        // Get all the pcRatingsList where updatedAt is not null
        defaultPcRatingsShouldBeFound("updatedAt.specified=true");

        // Get all the pcRatingsList where updatedAt is null
        defaultPcRatingsShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllPcRatingsByDeletedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingsRepository.saveAndFlush(pcRatings);

        // Get all the pcRatingsList where deletedAt equals to DEFAULT_DELETED_AT
        defaultPcRatingsShouldBeFound("deletedAt.equals=" + DEFAULT_DELETED_AT);

        // Get all the pcRatingsList where deletedAt equals to UPDATED_DELETED_AT
        defaultPcRatingsShouldNotBeFound("deletedAt.equals=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllPcRatingsByDeletedAtIsInShouldWork() throws Exception {
        // Initialize the database
        pcRatingsRepository.saveAndFlush(pcRatings);

        // Get all the pcRatingsList where deletedAt in DEFAULT_DELETED_AT or UPDATED_DELETED_AT
        defaultPcRatingsShouldBeFound("deletedAt.in=" + DEFAULT_DELETED_AT + "," + UPDATED_DELETED_AT);

        // Get all the pcRatingsList where deletedAt equals to UPDATED_DELETED_AT
        defaultPcRatingsShouldNotBeFound("deletedAt.in=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllPcRatingsByDeletedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcRatingsRepository.saveAndFlush(pcRatings);

        // Get all the pcRatingsList where deletedAt is not null
        defaultPcRatingsShouldBeFound("deletedAt.specified=true");

        // Get all the pcRatingsList where deletedAt is null
        defaultPcRatingsShouldNotBeFound("deletedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllPcRatingsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingsRepository.saveAndFlush(pcRatings);

        // Get all the pcRatingsList where version equals to DEFAULT_VERSION
        defaultPcRatingsShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the pcRatingsList where version equals to UPDATED_VERSION
        defaultPcRatingsShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllPcRatingsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        pcRatingsRepository.saveAndFlush(pcRatings);

        // Get all the pcRatingsList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultPcRatingsShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the pcRatingsList where version equals to UPDATED_VERSION
        defaultPcRatingsShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllPcRatingsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcRatingsRepository.saveAndFlush(pcRatings);

        // Get all the pcRatingsList where version is not null
        defaultPcRatingsShouldBeFound("version.specified=true");

        // Get all the pcRatingsList where version is null
        defaultPcRatingsShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllPcRatingsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingsRepository.saveAndFlush(pcRatings);

        // Get all the pcRatingsList where version is greater than or equal to DEFAULT_VERSION
        defaultPcRatingsShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the pcRatingsList where version is greater than or equal to UPDATED_VERSION
        defaultPcRatingsShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllPcRatingsByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingsRepository.saveAndFlush(pcRatings);

        // Get all the pcRatingsList where version is less than or equal to DEFAULT_VERSION
        defaultPcRatingsShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the pcRatingsList where version is less than or equal to SMALLER_VERSION
        defaultPcRatingsShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllPcRatingsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        pcRatingsRepository.saveAndFlush(pcRatings);

        // Get all the pcRatingsList where version is less than DEFAULT_VERSION
        defaultPcRatingsShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the pcRatingsList where version is less than UPDATED_VERSION
        defaultPcRatingsShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllPcRatingsByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pcRatingsRepository.saveAndFlush(pcRatings);

        // Get all the pcRatingsList where version is greater than DEFAULT_VERSION
        defaultPcRatingsShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the pcRatingsList where version is greater than SMALLER_VERSION
        defaultPcRatingsShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllPcRatingsByStatusIsEqualToSomething() throws Exception {
        PcStatuses status;
        if (TestUtil.findAll(em, PcStatuses.class).isEmpty()) {
            pcRatingsRepository.saveAndFlush(pcRatings);
            status = PcStatusesResourceIT.createEntity(em);
        } else {
            status = TestUtil.findAll(em, PcStatuses.class).get(0);
        }
        em.persist(status);
        em.flush();
        pcRatings.setStatus(status);
        pcRatingsRepository.saveAndFlush(pcRatings);
        Long statusId = status.getId();

        // Get all the pcRatingsList where status equals to statusId
        defaultPcRatingsShouldBeFound("statusId.equals=" + statusId);

        // Get all the pcRatingsList where status equals to (statusId + 1)
        defaultPcRatingsShouldNotBeFound("statusId.equals=" + (statusId + 1));
    }

    @Test
    @Transactional
    void getAllPcRatingsByPcemployeeratingIsEqualToSomething() throws Exception {
        PerformanceCycleEmployeeRating pcemployeerating;
        if (TestUtil.findAll(em, PerformanceCycleEmployeeRating.class).isEmpty()) {
            pcRatingsRepository.saveAndFlush(pcRatings);
            pcemployeerating = PerformanceCycleEmployeeRatingResourceIT.createEntity(em);
        } else {
            pcemployeerating = TestUtil.findAll(em, PerformanceCycleEmployeeRating.class).get(0);
        }
        em.persist(pcemployeerating);
        em.flush();
        pcRatings.setPcemployeerating(pcemployeerating);
        pcRatingsRepository.saveAndFlush(pcRatings);
        Long pcemployeeratingId = pcemployeerating.getId();

        // Get all the pcRatingsList where pcemployeerating equals to pcemployeeratingId
        defaultPcRatingsShouldBeFound("pcemployeeratingId.equals=" + pcemployeeratingId);

        // Get all the pcRatingsList where pcemployeerating equals to (pcemployeeratingId + 1)
        defaultPcRatingsShouldNotBeFound("pcemployeeratingId.equals=" + (pcemployeeratingId + 1));
    }

    @Test
    @Transactional
    void getAllPcRatingsByEmployeeIsEqualToSomething() throws Exception {
        Employees employee;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            pcRatingsRepository.saveAndFlush(pcRatings);
            employee = EmployeesResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employee);
        em.flush();
        pcRatings.setEmployee(employee);
        pcRatingsRepository.saveAndFlush(pcRatings);
        Long employeeId = employee.getId();

        // Get all the pcRatingsList where employee equals to employeeId
        defaultPcRatingsShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the pcRatingsList where employee equals to (employeeId + 1)
        defaultPcRatingsShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    @Test
    @Transactional
    void getAllPcRatingsByPcraterattributesRatingIsEqualToSomething() throws Exception {
        PcRaterAttributes pcraterattributesRating;
        if (TestUtil.findAll(em, PcRaterAttributes.class).isEmpty()) {
            pcRatingsRepository.saveAndFlush(pcRatings);
            pcraterattributesRating = PcRaterAttributesResourceIT.createEntity(em);
        } else {
            pcraterattributesRating = TestUtil.findAll(em, PcRaterAttributes.class).get(0);
        }
        em.persist(pcraterattributesRating);
        em.flush();
        pcRatings.addPcraterattributesRating(pcraterattributesRating);
        pcRatingsRepository.saveAndFlush(pcRatings);
        Long pcraterattributesRatingId = pcraterattributesRating.getId();

        // Get all the pcRatingsList where pcraterattributesRating equals to pcraterattributesRatingId
        defaultPcRatingsShouldBeFound("pcraterattributesRatingId.equals=" + pcraterattributesRatingId);

        // Get all the pcRatingsList where pcraterattributesRating equals to (pcraterattributesRatingId + 1)
        defaultPcRatingsShouldNotBeFound("pcraterattributesRatingId.equals=" + (pcraterattributesRatingId + 1));
    }

    @Test
    @Transactional
    void getAllPcRatingsByPcratingstrainingsRatingIsEqualToSomething() throws Exception {
        PcRatingsTrainings pcratingstrainingsRating;
        if (TestUtil.findAll(em, PcRatingsTrainings.class).isEmpty()) {
            pcRatingsRepository.saveAndFlush(pcRatings);
            pcratingstrainingsRating = PcRatingsTrainingsResourceIT.createEntity(em);
        } else {
            pcratingstrainingsRating = TestUtil.findAll(em, PcRatingsTrainings.class).get(0);
        }
        em.persist(pcratingstrainingsRating);
        em.flush();
        pcRatings.addPcratingstrainingsRating(pcratingstrainingsRating);
        pcRatingsRepository.saveAndFlush(pcRatings);
        Long pcratingstrainingsRatingId = pcratingstrainingsRating.getId();

        // Get all the pcRatingsList where pcratingstrainingsRating equals to pcratingstrainingsRatingId
        defaultPcRatingsShouldBeFound("pcratingstrainingsRatingId.equals=" + pcratingstrainingsRatingId);

        // Get all the pcRatingsList where pcratingstrainingsRating equals to (pcratingstrainingsRatingId + 1)
        defaultPcRatingsShouldNotBeFound("pcratingstrainingsRatingId.equals=" + (pcratingstrainingsRatingId + 1));
    }

    @Test
    @Transactional
    void getAllPcRatingsByUsergoalraterattributesRatingIsEqualToSomething() throws Exception {
        UserGoalRaterAttributes usergoalraterattributesRating;
        if (TestUtil.findAll(em, UserGoalRaterAttributes.class).isEmpty()) {
            pcRatingsRepository.saveAndFlush(pcRatings);
            usergoalraterattributesRating = UserGoalRaterAttributesResourceIT.createEntity(em);
        } else {
            usergoalraterattributesRating = TestUtil.findAll(em, UserGoalRaterAttributes.class).get(0);
        }
        em.persist(usergoalraterattributesRating);
        em.flush();
        pcRatings.addUsergoalraterattributesRating(usergoalraterattributesRating);
        pcRatingsRepository.saveAndFlush(pcRatings);
        Long usergoalraterattributesRatingId = usergoalraterattributesRating.getId();

        // Get all the pcRatingsList where usergoalraterattributesRating equals to usergoalraterattributesRatingId
        defaultPcRatingsShouldBeFound("usergoalraterattributesRatingId.equals=" + usergoalraterattributesRatingId);

        // Get all the pcRatingsList where usergoalraterattributesRating equals to (usergoalraterattributesRatingId + 1)
        defaultPcRatingsShouldNotBeFound("usergoalraterattributesRatingId.equals=" + (usergoalraterattributesRatingId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPcRatingsShouldBeFound(String filter) throws Exception {
        restPcRatingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pcRatings.getId().intValue())))
            .andExpect(jsonPath("$.[*].ratingContentType").value(hasItem(DEFAULT_RATING_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(Base64Utils.encodeToString(DEFAULT_RATING))))
            .andExpect(jsonPath("$.[*].commentContentType").value(hasItem(DEFAULT_COMMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(Base64Utils.encodeToString(DEFAULT_COMMENT))))
            .andExpect(jsonPath("$.[*].stausDate").value(hasItem(DEFAULT_STAUS_DATE.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].freeze").value(hasItem(DEFAULT_FREEZE.booleanValue())))
            .andExpect(jsonPath("$.[*].includeInFinalRating").value(hasItem(DEFAULT_INCLUDE_IN_FINAL_RATING.booleanValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restPcRatingsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPcRatingsShouldNotBeFound(String filter) throws Exception {
        restPcRatingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPcRatingsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPcRatings() throws Exception {
        // Get the pcRatings
        restPcRatingsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPcRatings() throws Exception {
        // Initialize the database
        pcRatingsRepository.saveAndFlush(pcRatings);

        int databaseSizeBeforeUpdate = pcRatingsRepository.findAll().size();

        // Update the pcRatings
        PcRatings updatedPcRatings = pcRatingsRepository.findById(pcRatings.getId()).get();
        // Disconnect from session so that the updates on updatedPcRatings are not directly saved in db
        em.detach(updatedPcRatings);
        updatedPcRatings
            .rating(UPDATED_RATING)
            .ratingContentType(UPDATED_RATING_CONTENT_TYPE)
            .comment(UPDATED_COMMENT)
            .commentContentType(UPDATED_COMMENT_CONTENT_TYPE)
            .stausDate(UPDATED_STAUS_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .freeze(UPDATED_FREEZE)
            .includeInFinalRating(UPDATED_INCLUDE_IN_FINAL_RATING)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restPcRatingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPcRatings.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPcRatings))
            )
            .andExpect(status().isOk());

        // Validate the PcRatings in the database
        List<PcRatings> pcRatingsList = pcRatingsRepository.findAll();
        assertThat(pcRatingsList).hasSize(databaseSizeBeforeUpdate);
        PcRatings testPcRatings = pcRatingsList.get(pcRatingsList.size() - 1);
        assertThat(testPcRatings.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testPcRatings.getRatingContentType()).isEqualTo(UPDATED_RATING_CONTENT_TYPE);
        assertThat(testPcRatings.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testPcRatings.getCommentContentType()).isEqualTo(UPDATED_COMMENT_CONTENT_TYPE);
        assertThat(testPcRatings.getStausDate()).isEqualTo(UPDATED_STAUS_DATE);
        assertThat(testPcRatings.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testPcRatings.getFreeze()).isEqualTo(UPDATED_FREEZE);
        assertThat(testPcRatings.getIncludeInFinalRating()).isEqualTo(UPDATED_INCLUDE_IN_FINAL_RATING);
        assertThat(testPcRatings.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPcRatings.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testPcRatings.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testPcRatings.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingPcRatings() throws Exception {
        int databaseSizeBeforeUpdate = pcRatingsRepository.findAll().size();
        pcRatings.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPcRatingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pcRatings.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRatings))
            )
            .andExpect(status().isBadRequest());

        // Validate the PcRatings in the database
        List<PcRatings> pcRatingsList = pcRatingsRepository.findAll();
        assertThat(pcRatingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPcRatings() throws Exception {
        int databaseSizeBeforeUpdate = pcRatingsRepository.findAll().size();
        pcRatings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPcRatingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRatings))
            )
            .andExpect(status().isBadRequest());

        // Validate the PcRatings in the database
        List<PcRatings> pcRatingsList = pcRatingsRepository.findAll();
        assertThat(pcRatingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPcRatings() throws Exception {
        int databaseSizeBeforeUpdate = pcRatingsRepository.findAll().size();
        pcRatings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPcRatingsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRatings))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PcRatings in the database
        List<PcRatings> pcRatingsList = pcRatingsRepository.findAll();
        assertThat(pcRatingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePcRatingsWithPatch() throws Exception {
        // Initialize the database
        pcRatingsRepository.saveAndFlush(pcRatings);

        int databaseSizeBeforeUpdate = pcRatingsRepository.findAll().size();

        // Update the pcRatings using partial update
        PcRatings partialUpdatedPcRatings = new PcRatings();
        partialUpdatedPcRatings.setId(pcRatings.getId());

        partialUpdatedPcRatings
            .rating(UPDATED_RATING)
            .ratingContentType(UPDATED_RATING_CONTENT_TYPE)
            .stausDate(UPDATED_STAUS_DATE)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restPcRatingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPcRatings.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPcRatings))
            )
            .andExpect(status().isOk());

        // Validate the PcRatings in the database
        List<PcRatings> pcRatingsList = pcRatingsRepository.findAll();
        assertThat(pcRatingsList).hasSize(databaseSizeBeforeUpdate);
        PcRatings testPcRatings = pcRatingsList.get(pcRatingsList.size() - 1);
        assertThat(testPcRatings.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testPcRatings.getRatingContentType()).isEqualTo(UPDATED_RATING_CONTENT_TYPE);
        assertThat(testPcRatings.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testPcRatings.getCommentContentType()).isEqualTo(DEFAULT_COMMENT_CONTENT_TYPE);
        assertThat(testPcRatings.getStausDate()).isEqualTo(UPDATED_STAUS_DATE);
        assertThat(testPcRatings.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testPcRatings.getFreeze()).isEqualTo(DEFAULT_FREEZE);
        assertThat(testPcRatings.getIncludeInFinalRating()).isEqualTo(DEFAULT_INCLUDE_IN_FINAL_RATING);
        assertThat(testPcRatings.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testPcRatings.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testPcRatings.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testPcRatings.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void fullUpdatePcRatingsWithPatch() throws Exception {
        // Initialize the database
        pcRatingsRepository.saveAndFlush(pcRatings);

        int databaseSizeBeforeUpdate = pcRatingsRepository.findAll().size();

        // Update the pcRatings using partial update
        PcRatings partialUpdatedPcRatings = new PcRatings();
        partialUpdatedPcRatings.setId(pcRatings.getId());

        partialUpdatedPcRatings
            .rating(UPDATED_RATING)
            .ratingContentType(UPDATED_RATING_CONTENT_TYPE)
            .comment(UPDATED_COMMENT)
            .commentContentType(UPDATED_COMMENT_CONTENT_TYPE)
            .stausDate(UPDATED_STAUS_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .freeze(UPDATED_FREEZE)
            .includeInFinalRating(UPDATED_INCLUDE_IN_FINAL_RATING)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restPcRatingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPcRatings.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPcRatings))
            )
            .andExpect(status().isOk());

        // Validate the PcRatings in the database
        List<PcRatings> pcRatingsList = pcRatingsRepository.findAll();
        assertThat(pcRatingsList).hasSize(databaseSizeBeforeUpdate);
        PcRatings testPcRatings = pcRatingsList.get(pcRatingsList.size() - 1);
        assertThat(testPcRatings.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testPcRatings.getRatingContentType()).isEqualTo(UPDATED_RATING_CONTENT_TYPE);
        assertThat(testPcRatings.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testPcRatings.getCommentContentType()).isEqualTo(UPDATED_COMMENT_CONTENT_TYPE);
        assertThat(testPcRatings.getStausDate()).isEqualTo(UPDATED_STAUS_DATE);
        assertThat(testPcRatings.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testPcRatings.getFreeze()).isEqualTo(UPDATED_FREEZE);
        assertThat(testPcRatings.getIncludeInFinalRating()).isEqualTo(UPDATED_INCLUDE_IN_FINAL_RATING);
        assertThat(testPcRatings.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPcRatings.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testPcRatings.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testPcRatings.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingPcRatings() throws Exception {
        int databaseSizeBeforeUpdate = pcRatingsRepository.findAll().size();
        pcRatings.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPcRatingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pcRatings.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pcRatings))
            )
            .andExpect(status().isBadRequest());

        // Validate the PcRatings in the database
        List<PcRatings> pcRatingsList = pcRatingsRepository.findAll();
        assertThat(pcRatingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPcRatings() throws Exception {
        int databaseSizeBeforeUpdate = pcRatingsRepository.findAll().size();
        pcRatings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPcRatingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pcRatings))
            )
            .andExpect(status().isBadRequest());

        // Validate the PcRatings in the database
        List<PcRatings> pcRatingsList = pcRatingsRepository.findAll();
        assertThat(pcRatingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPcRatings() throws Exception {
        int databaseSizeBeforeUpdate = pcRatingsRepository.findAll().size();
        pcRatings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPcRatingsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pcRatings))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PcRatings in the database
        List<PcRatings> pcRatingsList = pcRatingsRepository.findAll();
        assertThat(pcRatingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePcRatings() throws Exception {
        // Initialize the database
        pcRatingsRepository.saveAndFlush(pcRatings);

        int databaseSizeBeforeDelete = pcRatingsRepository.findAll().size();

        // Delete the pcRatings
        restPcRatingsMockMvc
            .perform(delete(ENTITY_API_URL_ID, pcRatings.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PcRatings> pcRatingsList = pcRatingsRepository.findAll();
        assertThat(pcRatingsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
