package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.HrPerformanceCycles;
import com.venturedive.vendian_mono.domain.PcRatings;
import com.venturedive.vendian_mono.domain.PerformanceCycleEmployeeRating;
import com.venturedive.vendian_mono.domain.UserRatingsRemarks;
import com.venturedive.vendian_mono.repository.PerformanceCycleEmployeeRatingRepository;
import com.venturedive.vendian_mono.service.criteria.PerformanceCycleEmployeeRatingCriteria;
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
 * Integration tests for the {@link PerformanceCycleEmployeeRatingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PerformanceCycleEmployeeRatingResourceIT {

    private static final byte[] DEFAULT_RATING = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_RATING = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_RATING_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_RATING_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_COMMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_COMMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_COMMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_COMMENT_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;
    private static final Integer SMALLER_VERSION = 1 - 1;

    private static final String ENTITY_API_URL = "/api/performance-cycle-employee-ratings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PerformanceCycleEmployeeRatingRepository performanceCycleEmployeeRatingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPerformanceCycleEmployeeRatingMockMvc;

    private PerformanceCycleEmployeeRating performanceCycleEmployeeRating;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PerformanceCycleEmployeeRating createEntity(EntityManager em) {
        PerformanceCycleEmployeeRating performanceCycleEmployeeRating = new PerformanceCycleEmployeeRating()
            .rating(DEFAULT_RATING)
            .ratingContentType(DEFAULT_RATING_CONTENT_TYPE)
            .comment(DEFAULT_COMMENT)
            .commentContentType(DEFAULT_COMMENT_CONTENT_TYPE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT)
            .version(DEFAULT_VERSION);
        // Add required entity
        HrPerformanceCycles hrPerformanceCycles;
        if (TestUtil.findAll(em, HrPerformanceCycles.class).isEmpty()) {
            hrPerformanceCycles = HrPerformanceCyclesResourceIT.createEntity(em);
            em.persist(hrPerformanceCycles);
            em.flush();
        } else {
            hrPerformanceCycles = TestUtil.findAll(em, HrPerformanceCycles.class).get(0);
        }
        performanceCycleEmployeeRating.setPerformancecycle(hrPerformanceCycles);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        performanceCycleEmployeeRating.setEmployee(employees);
        // Add required entity
        performanceCycleEmployeeRating.setManager(employees);
        return performanceCycleEmployeeRating;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PerformanceCycleEmployeeRating createUpdatedEntity(EntityManager em) {
        PerformanceCycleEmployeeRating performanceCycleEmployeeRating = new PerformanceCycleEmployeeRating()
            .rating(UPDATED_RATING)
            .ratingContentType(UPDATED_RATING_CONTENT_TYPE)
            .comment(UPDATED_COMMENT)
            .commentContentType(UPDATED_COMMENT_CONTENT_TYPE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);
        // Add required entity
        HrPerformanceCycles hrPerformanceCycles;
        if (TestUtil.findAll(em, HrPerformanceCycles.class).isEmpty()) {
            hrPerformanceCycles = HrPerformanceCyclesResourceIT.createUpdatedEntity(em);
            em.persist(hrPerformanceCycles);
            em.flush();
        } else {
            hrPerformanceCycles = TestUtil.findAll(em, HrPerformanceCycles.class).get(0);
        }
        performanceCycleEmployeeRating.setPerformancecycle(hrPerformanceCycles);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createUpdatedEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        performanceCycleEmployeeRating.setEmployee(employees);
        // Add required entity
        performanceCycleEmployeeRating.setManager(employees);
        return performanceCycleEmployeeRating;
    }

    @BeforeEach
    public void initTest() {
        performanceCycleEmployeeRating = createEntity(em);
    }

    @Test
    @Transactional
    void createPerformanceCycleEmployeeRating() throws Exception {
        int databaseSizeBeforeCreate = performanceCycleEmployeeRatingRepository.findAll().size();
        // Create the PerformanceCycleEmployeeRating
        restPerformanceCycleEmployeeRatingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(performanceCycleEmployeeRating))
            )
            .andExpect(status().isCreated());

        // Validate the PerformanceCycleEmployeeRating in the database
        List<PerformanceCycleEmployeeRating> performanceCycleEmployeeRatingList = performanceCycleEmployeeRatingRepository.findAll();
        assertThat(performanceCycleEmployeeRatingList).hasSize(databaseSizeBeforeCreate + 1);
        PerformanceCycleEmployeeRating testPerformanceCycleEmployeeRating = performanceCycleEmployeeRatingList.get(
            performanceCycleEmployeeRatingList.size() - 1
        );
        assertThat(testPerformanceCycleEmployeeRating.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testPerformanceCycleEmployeeRating.getRatingContentType()).isEqualTo(DEFAULT_RATING_CONTENT_TYPE);
        assertThat(testPerformanceCycleEmployeeRating.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testPerformanceCycleEmployeeRating.getCommentContentType()).isEqualTo(DEFAULT_COMMENT_CONTENT_TYPE);
        assertThat(testPerformanceCycleEmployeeRating.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testPerformanceCycleEmployeeRating.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testPerformanceCycleEmployeeRating.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testPerformanceCycleEmployeeRating.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createPerformanceCycleEmployeeRatingWithExistingId() throws Exception {
        // Create the PerformanceCycleEmployeeRating with an existing ID
        performanceCycleEmployeeRating.setId(1L);

        int databaseSizeBeforeCreate = performanceCycleEmployeeRatingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPerformanceCycleEmployeeRatingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(performanceCycleEmployeeRating))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerformanceCycleEmployeeRating in the database
        List<PerformanceCycleEmployeeRating> performanceCycleEmployeeRatingList = performanceCycleEmployeeRatingRepository.findAll();
        assertThat(performanceCycleEmployeeRatingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = performanceCycleEmployeeRatingRepository.findAll().size();
        // set the field null
        performanceCycleEmployeeRating.setCreatedAt(null);

        // Create the PerformanceCycleEmployeeRating, which fails.

        restPerformanceCycleEmployeeRatingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(performanceCycleEmployeeRating))
            )
            .andExpect(status().isBadRequest());

        List<PerformanceCycleEmployeeRating> performanceCycleEmployeeRatingList = performanceCycleEmployeeRatingRepository.findAll();
        assertThat(performanceCycleEmployeeRatingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = performanceCycleEmployeeRatingRepository.findAll().size();
        // set the field null
        performanceCycleEmployeeRating.setUpdatedAt(null);

        // Create the PerformanceCycleEmployeeRating, which fails.

        restPerformanceCycleEmployeeRatingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(performanceCycleEmployeeRating))
            )
            .andExpect(status().isBadRequest());

        List<PerformanceCycleEmployeeRating> performanceCycleEmployeeRatingList = performanceCycleEmployeeRatingRepository.findAll();
        assertThat(performanceCycleEmployeeRatingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = performanceCycleEmployeeRatingRepository.findAll().size();
        // set the field null
        performanceCycleEmployeeRating.setVersion(null);

        // Create the PerformanceCycleEmployeeRating, which fails.

        restPerformanceCycleEmployeeRatingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(performanceCycleEmployeeRating))
            )
            .andExpect(status().isBadRequest());

        List<PerformanceCycleEmployeeRating> performanceCycleEmployeeRatingList = performanceCycleEmployeeRatingRepository.findAll();
        assertThat(performanceCycleEmployeeRatingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPerformanceCycleEmployeeRatings() throws Exception {
        // Initialize the database
        performanceCycleEmployeeRatingRepository.saveAndFlush(performanceCycleEmployeeRating);

        // Get all the performanceCycleEmployeeRatingList
        restPerformanceCycleEmployeeRatingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(performanceCycleEmployeeRating.getId().intValue())))
            .andExpect(jsonPath("$.[*].ratingContentType").value(hasItem(DEFAULT_RATING_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(Base64Utils.encodeToString(DEFAULT_RATING))))
            .andExpect(jsonPath("$.[*].commentContentType").value(hasItem(DEFAULT_COMMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(Base64Utils.encodeToString(DEFAULT_COMMENT))))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getPerformanceCycleEmployeeRating() throws Exception {
        // Initialize the database
        performanceCycleEmployeeRatingRepository.saveAndFlush(performanceCycleEmployeeRating);

        // Get the performanceCycleEmployeeRating
        restPerformanceCycleEmployeeRatingMockMvc
            .perform(get(ENTITY_API_URL_ID, performanceCycleEmployeeRating.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(performanceCycleEmployeeRating.getId().intValue()))
            .andExpect(jsonPath("$.ratingContentType").value(DEFAULT_RATING_CONTENT_TYPE))
            .andExpect(jsonPath("$.rating").value(Base64Utils.encodeToString(DEFAULT_RATING)))
            .andExpect(jsonPath("$.commentContentType").value(DEFAULT_COMMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.comment").value(Base64Utils.encodeToString(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getPerformanceCycleEmployeeRatingsByIdFiltering() throws Exception {
        // Initialize the database
        performanceCycleEmployeeRatingRepository.saveAndFlush(performanceCycleEmployeeRating);

        Long id = performanceCycleEmployeeRating.getId();

        defaultPerformanceCycleEmployeeRatingShouldBeFound("id.equals=" + id);
        defaultPerformanceCycleEmployeeRatingShouldNotBeFound("id.notEquals=" + id);

        defaultPerformanceCycleEmployeeRatingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPerformanceCycleEmployeeRatingShouldNotBeFound("id.greaterThan=" + id);

        defaultPerformanceCycleEmployeeRatingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPerformanceCycleEmployeeRatingShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPerformanceCycleEmployeeRatingsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceCycleEmployeeRatingRepository.saveAndFlush(performanceCycleEmployeeRating);

        // Get all the performanceCycleEmployeeRatingList where createdAt equals to DEFAULT_CREATED_AT
        defaultPerformanceCycleEmployeeRatingShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the performanceCycleEmployeeRatingList where createdAt equals to UPDATED_CREATED_AT
        defaultPerformanceCycleEmployeeRatingShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllPerformanceCycleEmployeeRatingsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        performanceCycleEmployeeRatingRepository.saveAndFlush(performanceCycleEmployeeRating);

        // Get all the performanceCycleEmployeeRatingList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultPerformanceCycleEmployeeRatingShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the performanceCycleEmployeeRatingList where createdAt equals to UPDATED_CREATED_AT
        defaultPerformanceCycleEmployeeRatingShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllPerformanceCycleEmployeeRatingsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceCycleEmployeeRatingRepository.saveAndFlush(performanceCycleEmployeeRating);

        // Get all the performanceCycleEmployeeRatingList where createdAt is not null
        defaultPerformanceCycleEmployeeRatingShouldBeFound("createdAt.specified=true");

        // Get all the performanceCycleEmployeeRatingList where createdAt is null
        defaultPerformanceCycleEmployeeRatingShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceCycleEmployeeRatingsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceCycleEmployeeRatingRepository.saveAndFlush(performanceCycleEmployeeRating);

        // Get all the performanceCycleEmployeeRatingList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultPerformanceCycleEmployeeRatingShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the performanceCycleEmployeeRatingList where updatedAt equals to UPDATED_UPDATED_AT
        defaultPerformanceCycleEmployeeRatingShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllPerformanceCycleEmployeeRatingsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        performanceCycleEmployeeRatingRepository.saveAndFlush(performanceCycleEmployeeRating);

        // Get all the performanceCycleEmployeeRatingList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultPerformanceCycleEmployeeRatingShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the performanceCycleEmployeeRatingList where updatedAt equals to UPDATED_UPDATED_AT
        defaultPerformanceCycleEmployeeRatingShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllPerformanceCycleEmployeeRatingsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceCycleEmployeeRatingRepository.saveAndFlush(performanceCycleEmployeeRating);

        // Get all the performanceCycleEmployeeRatingList where updatedAt is not null
        defaultPerformanceCycleEmployeeRatingShouldBeFound("updatedAt.specified=true");

        // Get all the performanceCycleEmployeeRatingList where updatedAt is null
        defaultPerformanceCycleEmployeeRatingShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceCycleEmployeeRatingsByDeletedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceCycleEmployeeRatingRepository.saveAndFlush(performanceCycleEmployeeRating);

        // Get all the performanceCycleEmployeeRatingList where deletedAt equals to DEFAULT_DELETED_AT
        defaultPerformanceCycleEmployeeRatingShouldBeFound("deletedAt.equals=" + DEFAULT_DELETED_AT);

        // Get all the performanceCycleEmployeeRatingList where deletedAt equals to UPDATED_DELETED_AT
        defaultPerformanceCycleEmployeeRatingShouldNotBeFound("deletedAt.equals=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllPerformanceCycleEmployeeRatingsByDeletedAtIsInShouldWork() throws Exception {
        // Initialize the database
        performanceCycleEmployeeRatingRepository.saveAndFlush(performanceCycleEmployeeRating);

        // Get all the performanceCycleEmployeeRatingList where deletedAt in DEFAULT_DELETED_AT or UPDATED_DELETED_AT
        defaultPerformanceCycleEmployeeRatingShouldBeFound("deletedAt.in=" + DEFAULT_DELETED_AT + "," + UPDATED_DELETED_AT);

        // Get all the performanceCycleEmployeeRatingList where deletedAt equals to UPDATED_DELETED_AT
        defaultPerformanceCycleEmployeeRatingShouldNotBeFound("deletedAt.in=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllPerformanceCycleEmployeeRatingsByDeletedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceCycleEmployeeRatingRepository.saveAndFlush(performanceCycleEmployeeRating);

        // Get all the performanceCycleEmployeeRatingList where deletedAt is not null
        defaultPerformanceCycleEmployeeRatingShouldBeFound("deletedAt.specified=true");

        // Get all the performanceCycleEmployeeRatingList where deletedAt is null
        defaultPerformanceCycleEmployeeRatingShouldNotBeFound("deletedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceCycleEmployeeRatingsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceCycleEmployeeRatingRepository.saveAndFlush(performanceCycleEmployeeRating);

        // Get all the performanceCycleEmployeeRatingList where version equals to DEFAULT_VERSION
        defaultPerformanceCycleEmployeeRatingShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the performanceCycleEmployeeRatingList where version equals to UPDATED_VERSION
        defaultPerformanceCycleEmployeeRatingShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllPerformanceCycleEmployeeRatingsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        performanceCycleEmployeeRatingRepository.saveAndFlush(performanceCycleEmployeeRating);

        // Get all the performanceCycleEmployeeRatingList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultPerformanceCycleEmployeeRatingShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the performanceCycleEmployeeRatingList where version equals to UPDATED_VERSION
        defaultPerformanceCycleEmployeeRatingShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllPerformanceCycleEmployeeRatingsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceCycleEmployeeRatingRepository.saveAndFlush(performanceCycleEmployeeRating);

        // Get all the performanceCycleEmployeeRatingList where version is not null
        defaultPerformanceCycleEmployeeRatingShouldBeFound("version.specified=true");

        // Get all the performanceCycleEmployeeRatingList where version is null
        defaultPerformanceCycleEmployeeRatingShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceCycleEmployeeRatingsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        performanceCycleEmployeeRatingRepository.saveAndFlush(performanceCycleEmployeeRating);

        // Get all the performanceCycleEmployeeRatingList where version is greater than or equal to DEFAULT_VERSION
        defaultPerformanceCycleEmployeeRatingShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the performanceCycleEmployeeRatingList where version is greater than or equal to UPDATED_VERSION
        defaultPerformanceCycleEmployeeRatingShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllPerformanceCycleEmployeeRatingsByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        performanceCycleEmployeeRatingRepository.saveAndFlush(performanceCycleEmployeeRating);

        // Get all the performanceCycleEmployeeRatingList where version is less than or equal to DEFAULT_VERSION
        defaultPerformanceCycleEmployeeRatingShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the performanceCycleEmployeeRatingList where version is less than or equal to SMALLER_VERSION
        defaultPerformanceCycleEmployeeRatingShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllPerformanceCycleEmployeeRatingsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        performanceCycleEmployeeRatingRepository.saveAndFlush(performanceCycleEmployeeRating);

        // Get all the performanceCycleEmployeeRatingList where version is less than DEFAULT_VERSION
        defaultPerformanceCycleEmployeeRatingShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the performanceCycleEmployeeRatingList where version is less than UPDATED_VERSION
        defaultPerformanceCycleEmployeeRatingShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllPerformanceCycleEmployeeRatingsByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        performanceCycleEmployeeRatingRepository.saveAndFlush(performanceCycleEmployeeRating);

        // Get all the performanceCycleEmployeeRatingList where version is greater than DEFAULT_VERSION
        defaultPerformanceCycleEmployeeRatingShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the performanceCycleEmployeeRatingList where version is greater than SMALLER_VERSION
        defaultPerformanceCycleEmployeeRatingShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllPerformanceCycleEmployeeRatingsByPerformancecycleIsEqualToSomething() throws Exception {
        HrPerformanceCycles performancecycle;
        if (TestUtil.findAll(em, HrPerformanceCycles.class).isEmpty()) {
            performanceCycleEmployeeRatingRepository.saveAndFlush(performanceCycleEmployeeRating);
            performancecycle = HrPerformanceCyclesResourceIT.createEntity(em);
        } else {
            performancecycle = TestUtil.findAll(em, HrPerformanceCycles.class).get(0);
        }
        em.persist(performancecycle);
        em.flush();
        performanceCycleEmployeeRating.setPerformancecycle(performancecycle);
        performanceCycleEmployeeRatingRepository.saveAndFlush(performanceCycleEmployeeRating);
        Long performancecycleId = performancecycle.getId();

        // Get all the performanceCycleEmployeeRatingList where performancecycle equals to performancecycleId
        defaultPerformanceCycleEmployeeRatingShouldBeFound("performancecycleId.equals=" + performancecycleId);

        // Get all the performanceCycleEmployeeRatingList where performancecycle equals to (performancecycleId + 1)
        defaultPerformanceCycleEmployeeRatingShouldNotBeFound("performancecycleId.equals=" + (performancecycleId + 1));
    }

    @Test
    @Transactional
    void getAllPerformanceCycleEmployeeRatingsByEmployeeIsEqualToSomething() throws Exception {
        Employees employee;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            performanceCycleEmployeeRatingRepository.saveAndFlush(performanceCycleEmployeeRating);
            employee = EmployeesResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employee);
        em.flush();
        performanceCycleEmployeeRating.setEmployee(employee);
        performanceCycleEmployeeRatingRepository.saveAndFlush(performanceCycleEmployeeRating);
        Long employeeId = employee.getId();

        // Get all the performanceCycleEmployeeRatingList where employee equals to employeeId
        defaultPerformanceCycleEmployeeRatingShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the performanceCycleEmployeeRatingList where employee equals to (employeeId + 1)
        defaultPerformanceCycleEmployeeRatingShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    @Test
    @Transactional
    void getAllPerformanceCycleEmployeeRatingsByManagerIsEqualToSomething() throws Exception {
        Employees manager;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            performanceCycleEmployeeRatingRepository.saveAndFlush(performanceCycleEmployeeRating);
            manager = EmployeesResourceIT.createEntity(em);
        } else {
            manager = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(manager);
        em.flush();
        performanceCycleEmployeeRating.setManager(manager);
        performanceCycleEmployeeRatingRepository.saveAndFlush(performanceCycleEmployeeRating);
        Long managerId = manager.getId();

        // Get all the performanceCycleEmployeeRatingList where manager equals to managerId
        defaultPerformanceCycleEmployeeRatingShouldBeFound("managerId.equals=" + managerId);

        // Get all the performanceCycleEmployeeRatingList where manager equals to (managerId + 1)
        defaultPerformanceCycleEmployeeRatingShouldNotBeFound("managerId.equals=" + (managerId + 1));
    }

    @Test
    @Transactional
    void getAllPerformanceCycleEmployeeRatingsByPcratingsPcemployeeratingIsEqualToSomething() throws Exception {
        PcRatings pcratingsPcemployeerating;
        if (TestUtil.findAll(em, PcRatings.class).isEmpty()) {
            performanceCycleEmployeeRatingRepository.saveAndFlush(performanceCycleEmployeeRating);
            pcratingsPcemployeerating = PcRatingsResourceIT.createEntity(em);
        } else {
            pcratingsPcemployeerating = TestUtil.findAll(em, PcRatings.class).get(0);
        }
        em.persist(pcratingsPcemployeerating);
        em.flush();
        performanceCycleEmployeeRating.addPcratingsPcemployeerating(pcratingsPcemployeerating);
        performanceCycleEmployeeRatingRepository.saveAndFlush(performanceCycleEmployeeRating);
        Long pcratingsPcemployeeratingId = pcratingsPcemployeerating.getId();

        // Get all the performanceCycleEmployeeRatingList where pcratingsPcemployeerating equals to pcratingsPcemployeeratingId
        defaultPerformanceCycleEmployeeRatingShouldBeFound("pcratingsPcemployeeratingId.equals=" + pcratingsPcemployeeratingId);

        // Get all the performanceCycleEmployeeRatingList where pcratingsPcemployeerating equals to (pcratingsPcemployeeratingId + 1)
        defaultPerformanceCycleEmployeeRatingShouldNotBeFound("pcratingsPcemployeeratingId.equals=" + (pcratingsPcemployeeratingId + 1));
    }

    @Test
    @Transactional
    void getAllPerformanceCycleEmployeeRatingsByUserratingsremarksPcemployeeratingIsEqualToSomething() throws Exception {
        UserRatingsRemarks userratingsremarksPcemployeerating;
        if (TestUtil.findAll(em, UserRatingsRemarks.class).isEmpty()) {
            performanceCycleEmployeeRatingRepository.saveAndFlush(performanceCycleEmployeeRating);
            userratingsremarksPcemployeerating = UserRatingsRemarksResourceIT.createEntity(em);
        } else {
            userratingsremarksPcemployeerating = TestUtil.findAll(em, UserRatingsRemarks.class).get(0);
        }
        em.persist(userratingsremarksPcemployeerating);
        em.flush();
        performanceCycleEmployeeRating.addUserratingsremarksPcemployeerating(userratingsremarksPcemployeerating);
        performanceCycleEmployeeRatingRepository.saveAndFlush(performanceCycleEmployeeRating);
        Long userratingsremarksPcemployeeratingId = userratingsremarksPcemployeerating.getId();

        // Get all the performanceCycleEmployeeRatingList where userratingsremarksPcemployeerating equals to userratingsremarksPcemployeeratingId
        defaultPerformanceCycleEmployeeRatingShouldBeFound(
            "userratingsremarksPcemployeeratingId.equals=" + userratingsremarksPcemployeeratingId
        );

        // Get all the performanceCycleEmployeeRatingList where userratingsremarksPcemployeerating equals to (userratingsremarksPcemployeeratingId + 1)
        defaultPerformanceCycleEmployeeRatingShouldNotBeFound(
            "userratingsremarksPcemployeeratingId.equals=" + (userratingsremarksPcemployeeratingId + 1)
        );
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPerformanceCycleEmployeeRatingShouldBeFound(String filter) throws Exception {
        restPerformanceCycleEmployeeRatingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(performanceCycleEmployeeRating.getId().intValue())))
            .andExpect(jsonPath("$.[*].ratingContentType").value(hasItem(DEFAULT_RATING_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(Base64Utils.encodeToString(DEFAULT_RATING))))
            .andExpect(jsonPath("$.[*].commentContentType").value(hasItem(DEFAULT_COMMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(Base64Utils.encodeToString(DEFAULT_COMMENT))))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restPerformanceCycleEmployeeRatingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPerformanceCycleEmployeeRatingShouldNotBeFound(String filter) throws Exception {
        restPerformanceCycleEmployeeRatingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPerformanceCycleEmployeeRatingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPerformanceCycleEmployeeRating() throws Exception {
        // Get the performanceCycleEmployeeRating
        restPerformanceCycleEmployeeRatingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPerformanceCycleEmployeeRating() throws Exception {
        // Initialize the database
        performanceCycleEmployeeRatingRepository.saveAndFlush(performanceCycleEmployeeRating);

        int databaseSizeBeforeUpdate = performanceCycleEmployeeRatingRepository.findAll().size();

        // Update the performanceCycleEmployeeRating
        PerformanceCycleEmployeeRating updatedPerformanceCycleEmployeeRating = performanceCycleEmployeeRatingRepository
            .findById(performanceCycleEmployeeRating.getId())
            .get();
        // Disconnect from session so that the updates on updatedPerformanceCycleEmployeeRating are not directly saved in db
        em.detach(updatedPerformanceCycleEmployeeRating);
        updatedPerformanceCycleEmployeeRating
            .rating(UPDATED_RATING)
            .ratingContentType(UPDATED_RATING_CONTENT_TYPE)
            .comment(UPDATED_COMMENT)
            .commentContentType(UPDATED_COMMENT_CONTENT_TYPE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restPerformanceCycleEmployeeRatingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPerformanceCycleEmployeeRating.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPerformanceCycleEmployeeRating))
            )
            .andExpect(status().isOk());

        // Validate the PerformanceCycleEmployeeRating in the database
        List<PerformanceCycleEmployeeRating> performanceCycleEmployeeRatingList = performanceCycleEmployeeRatingRepository.findAll();
        assertThat(performanceCycleEmployeeRatingList).hasSize(databaseSizeBeforeUpdate);
        PerformanceCycleEmployeeRating testPerformanceCycleEmployeeRating = performanceCycleEmployeeRatingList.get(
            performanceCycleEmployeeRatingList.size() - 1
        );
        assertThat(testPerformanceCycleEmployeeRating.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testPerformanceCycleEmployeeRating.getRatingContentType()).isEqualTo(UPDATED_RATING_CONTENT_TYPE);
        assertThat(testPerformanceCycleEmployeeRating.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testPerformanceCycleEmployeeRating.getCommentContentType()).isEqualTo(UPDATED_COMMENT_CONTENT_TYPE);
        assertThat(testPerformanceCycleEmployeeRating.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPerformanceCycleEmployeeRating.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testPerformanceCycleEmployeeRating.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testPerformanceCycleEmployeeRating.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingPerformanceCycleEmployeeRating() throws Exception {
        int databaseSizeBeforeUpdate = performanceCycleEmployeeRatingRepository.findAll().size();
        performanceCycleEmployeeRating.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPerformanceCycleEmployeeRatingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, performanceCycleEmployeeRating.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(performanceCycleEmployeeRating))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerformanceCycleEmployeeRating in the database
        List<PerformanceCycleEmployeeRating> performanceCycleEmployeeRatingList = performanceCycleEmployeeRatingRepository.findAll();
        assertThat(performanceCycleEmployeeRatingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPerformanceCycleEmployeeRating() throws Exception {
        int databaseSizeBeforeUpdate = performanceCycleEmployeeRatingRepository.findAll().size();
        performanceCycleEmployeeRating.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerformanceCycleEmployeeRatingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(performanceCycleEmployeeRating))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerformanceCycleEmployeeRating in the database
        List<PerformanceCycleEmployeeRating> performanceCycleEmployeeRatingList = performanceCycleEmployeeRatingRepository.findAll();
        assertThat(performanceCycleEmployeeRatingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPerformanceCycleEmployeeRating() throws Exception {
        int databaseSizeBeforeUpdate = performanceCycleEmployeeRatingRepository.findAll().size();
        performanceCycleEmployeeRating.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerformanceCycleEmployeeRatingMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(performanceCycleEmployeeRating))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PerformanceCycleEmployeeRating in the database
        List<PerformanceCycleEmployeeRating> performanceCycleEmployeeRatingList = performanceCycleEmployeeRatingRepository.findAll();
        assertThat(performanceCycleEmployeeRatingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePerformanceCycleEmployeeRatingWithPatch() throws Exception {
        // Initialize the database
        performanceCycleEmployeeRatingRepository.saveAndFlush(performanceCycleEmployeeRating);

        int databaseSizeBeforeUpdate = performanceCycleEmployeeRatingRepository.findAll().size();

        // Update the performanceCycleEmployeeRating using partial update
        PerformanceCycleEmployeeRating partialUpdatedPerformanceCycleEmployeeRating = new PerformanceCycleEmployeeRating();
        partialUpdatedPerformanceCycleEmployeeRating.setId(performanceCycleEmployeeRating.getId());

        partialUpdatedPerformanceCycleEmployeeRating
            .rating(UPDATED_RATING)
            .ratingContentType(UPDATED_RATING_CONTENT_TYPE)
            .version(UPDATED_VERSION);

        restPerformanceCycleEmployeeRatingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerformanceCycleEmployeeRating.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPerformanceCycleEmployeeRating))
            )
            .andExpect(status().isOk());

        // Validate the PerformanceCycleEmployeeRating in the database
        List<PerformanceCycleEmployeeRating> performanceCycleEmployeeRatingList = performanceCycleEmployeeRatingRepository.findAll();
        assertThat(performanceCycleEmployeeRatingList).hasSize(databaseSizeBeforeUpdate);
        PerformanceCycleEmployeeRating testPerformanceCycleEmployeeRating = performanceCycleEmployeeRatingList.get(
            performanceCycleEmployeeRatingList.size() - 1
        );
        assertThat(testPerformanceCycleEmployeeRating.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testPerformanceCycleEmployeeRating.getRatingContentType()).isEqualTo(UPDATED_RATING_CONTENT_TYPE);
        assertThat(testPerformanceCycleEmployeeRating.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testPerformanceCycleEmployeeRating.getCommentContentType()).isEqualTo(DEFAULT_COMMENT_CONTENT_TYPE);
        assertThat(testPerformanceCycleEmployeeRating.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testPerformanceCycleEmployeeRating.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testPerformanceCycleEmployeeRating.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testPerformanceCycleEmployeeRating.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void fullUpdatePerformanceCycleEmployeeRatingWithPatch() throws Exception {
        // Initialize the database
        performanceCycleEmployeeRatingRepository.saveAndFlush(performanceCycleEmployeeRating);

        int databaseSizeBeforeUpdate = performanceCycleEmployeeRatingRepository.findAll().size();

        // Update the performanceCycleEmployeeRating using partial update
        PerformanceCycleEmployeeRating partialUpdatedPerformanceCycleEmployeeRating = new PerformanceCycleEmployeeRating();
        partialUpdatedPerformanceCycleEmployeeRating.setId(performanceCycleEmployeeRating.getId());

        partialUpdatedPerformanceCycleEmployeeRating
            .rating(UPDATED_RATING)
            .ratingContentType(UPDATED_RATING_CONTENT_TYPE)
            .comment(UPDATED_COMMENT)
            .commentContentType(UPDATED_COMMENT_CONTENT_TYPE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restPerformanceCycleEmployeeRatingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerformanceCycleEmployeeRating.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPerformanceCycleEmployeeRating))
            )
            .andExpect(status().isOk());

        // Validate the PerformanceCycleEmployeeRating in the database
        List<PerformanceCycleEmployeeRating> performanceCycleEmployeeRatingList = performanceCycleEmployeeRatingRepository.findAll();
        assertThat(performanceCycleEmployeeRatingList).hasSize(databaseSizeBeforeUpdate);
        PerformanceCycleEmployeeRating testPerformanceCycleEmployeeRating = performanceCycleEmployeeRatingList.get(
            performanceCycleEmployeeRatingList.size() - 1
        );
        assertThat(testPerformanceCycleEmployeeRating.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testPerformanceCycleEmployeeRating.getRatingContentType()).isEqualTo(UPDATED_RATING_CONTENT_TYPE);
        assertThat(testPerformanceCycleEmployeeRating.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testPerformanceCycleEmployeeRating.getCommentContentType()).isEqualTo(UPDATED_COMMENT_CONTENT_TYPE);
        assertThat(testPerformanceCycleEmployeeRating.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPerformanceCycleEmployeeRating.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testPerformanceCycleEmployeeRating.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testPerformanceCycleEmployeeRating.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingPerformanceCycleEmployeeRating() throws Exception {
        int databaseSizeBeforeUpdate = performanceCycleEmployeeRatingRepository.findAll().size();
        performanceCycleEmployeeRating.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPerformanceCycleEmployeeRatingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, performanceCycleEmployeeRating.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(performanceCycleEmployeeRating))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerformanceCycleEmployeeRating in the database
        List<PerformanceCycleEmployeeRating> performanceCycleEmployeeRatingList = performanceCycleEmployeeRatingRepository.findAll();
        assertThat(performanceCycleEmployeeRatingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPerformanceCycleEmployeeRating() throws Exception {
        int databaseSizeBeforeUpdate = performanceCycleEmployeeRatingRepository.findAll().size();
        performanceCycleEmployeeRating.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerformanceCycleEmployeeRatingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(performanceCycleEmployeeRating))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerformanceCycleEmployeeRating in the database
        List<PerformanceCycleEmployeeRating> performanceCycleEmployeeRatingList = performanceCycleEmployeeRatingRepository.findAll();
        assertThat(performanceCycleEmployeeRatingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPerformanceCycleEmployeeRating() throws Exception {
        int databaseSizeBeforeUpdate = performanceCycleEmployeeRatingRepository.findAll().size();
        performanceCycleEmployeeRating.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerformanceCycleEmployeeRatingMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(performanceCycleEmployeeRating))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PerformanceCycleEmployeeRating in the database
        List<PerformanceCycleEmployeeRating> performanceCycleEmployeeRatingList = performanceCycleEmployeeRatingRepository.findAll();
        assertThat(performanceCycleEmployeeRatingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePerformanceCycleEmployeeRating() throws Exception {
        // Initialize the database
        performanceCycleEmployeeRatingRepository.saveAndFlush(performanceCycleEmployeeRating);

        int databaseSizeBeforeDelete = performanceCycleEmployeeRatingRepository.findAll().size();

        // Delete the performanceCycleEmployeeRating
        restPerformanceCycleEmployeeRatingMockMvc
            .perform(delete(ENTITY_API_URL_ID, performanceCycleEmployeeRating.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PerformanceCycleEmployeeRating> performanceCycleEmployeeRatingList = performanceCycleEmployeeRatingRepository.findAll();
        assertThat(performanceCycleEmployeeRatingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
