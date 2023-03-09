package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.HrPerformanceCycles;
import com.venturedive.vendian_mono.domain.UserRatings;
import com.venturedive.vendian_mono.repository.UserRatingsRepository;
import com.venturedive.vendian_mono.service.criteria.UserRatingsCriteria;
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
 * Integration tests for the {@link UserRatingsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserRatingsResourceIT {

    private static final String DEFAULT_RATING = "AAAAAAA";
    private static final String UPDATED_RATING = "BBBBBBB";

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;
    private static final Integer SMALLER_VERSION = 1 - 1;

    private static final String ENTITY_API_URL = "/api/user-ratings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserRatingsRepository userRatingsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserRatingsMockMvc;

    private UserRatings userRatings;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserRatings createEntity(EntityManager em) {
        UserRatings userRatings = new UserRatings()
            .rating(DEFAULT_RATING)
            .comment(DEFAULT_COMMENT)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT)
            .version(DEFAULT_VERSION);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        userRatings.setUser(employees);
        // Add required entity
        userRatings.setReviewManager(employees);
        // Add required entity
        HrPerformanceCycles hrPerformanceCycles;
        if (TestUtil.findAll(em, HrPerformanceCycles.class).isEmpty()) {
            hrPerformanceCycles = HrPerformanceCyclesResourceIT.createEntity(em);
            em.persist(hrPerformanceCycles);
            em.flush();
        } else {
            hrPerformanceCycles = TestUtil.findAll(em, HrPerformanceCycles.class).get(0);
        }
        userRatings.setPerformanceCycle(hrPerformanceCycles);
        return userRatings;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserRatings createUpdatedEntity(EntityManager em) {
        UserRatings userRatings = new UserRatings()
            .rating(UPDATED_RATING)
            .comment(UPDATED_COMMENT)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createUpdatedEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        userRatings.setUser(employees);
        // Add required entity
        userRatings.setReviewManager(employees);
        // Add required entity
        HrPerformanceCycles hrPerformanceCycles;
        if (TestUtil.findAll(em, HrPerformanceCycles.class).isEmpty()) {
            hrPerformanceCycles = HrPerformanceCyclesResourceIT.createUpdatedEntity(em);
            em.persist(hrPerformanceCycles);
            em.flush();
        } else {
            hrPerformanceCycles = TestUtil.findAll(em, HrPerformanceCycles.class).get(0);
        }
        userRatings.setPerformanceCycle(hrPerformanceCycles);
        return userRatings;
    }

    @BeforeEach
    public void initTest() {
        userRatings = createEntity(em);
    }

    @Test
    @Transactional
    void createUserRatings() throws Exception {
        int databaseSizeBeforeCreate = userRatingsRepository.findAll().size();
        // Create the UserRatings
        restUserRatingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRatings))
            )
            .andExpect(status().isCreated());

        // Validate the UserRatings in the database
        List<UserRatings> userRatingsList = userRatingsRepository.findAll();
        assertThat(userRatingsList).hasSize(databaseSizeBeforeCreate + 1);
        UserRatings testUserRatings = userRatingsList.get(userRatingsList.size() - 1);
        assertThat(testUserRatings.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testUserRatings.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testUserRatings.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testUserRatings.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testUserRatings.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testUserRatings.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createUserRatingsWithExistingId() throws Exception {
        // Create the UserRatings with an existing ID
        userRatings.setId(1L);

        int databaseSizeBeforeCreate = userRatingsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserRatingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRatings))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserRatings in the database
        List<UserRatings> userRatingsList = userRatingsRepository.findAll();
        assertThat(userRatingsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRatingIsRequired() throws Exception {
        int databaseSizeBeforeTest = userRatingsRepository.findAll().size();
        // set the field null
        userRatings.setRating(null);

        // Create the UserRatings, which fails.

        restUserRatingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRatings))
            )
            .andExpect(status().isBadRequest());

        List<UserRatings> userRatingsList = userRatingsRepository.findAll();
        assertThat(userRatingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = userRatingsRepository.findAll().size();
        // set the field null
        userRatings.setCreatedAt(null);

        // Create the UserRatings, which fails.

        restUserRatingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRatings))
            )
            .andExpect(status().isBadRequest());

        List<UserRatings> userRatingsList = userRatingsRepository.findAll();
        assertThat(userRatingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = userRatingsRepository.findAll().size();
        // set the field null
        userRatings.setUpdatedAt(null);

        // Create the UserRatings, which fails.

        restUserRatingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRatings))
            )
            .andExpect(status().isBadRequest());

        List<UserRatings> userRatingsList = userRatingsRepository.findAll();
        assertThat(userRatingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = userRatingsRepository.findAll().size();
        // set the field null
        userRatings.setVersion(null);

        // Create the UserRatings, which fails.

        restUserRatingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRatings))
            )
            .andExpect(status().isBadRequest());

        List<UserRatings> userRatingsList = userRatingsRepository.findAll();
        assertThat(userRatingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUserRatings() throws Exception {
        // Initialize the database
        userRatingsRepository.saveAndFlush(userRatings);

        // Get all the userRatingsList
        restUserRatingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userRatings.getId().intValue())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getUserRatings() throws Exception {
        // Initialize the database
        userRatingsRepository.saveAndFlush(userRatings);

        // Get the userRatings
        restUserRatingsMockMvc
            .perform(get(ENTITY_API_URL_ID, userRatings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userRatings.getId().intValue()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getUserRatingsByIdFiltering() throws Exception {
        // Initialize the database
        userRatingsRepository.saveAndFlush(userRatings);

        Long id = userRatings.getId();

        defaultUserRatingsShouldBeFound("id.equals=" + id);
        defaultUserRatingsShouldNotBeFound("id.notEquals=" + id);

        defaultUserRatingsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUserRatingsShouldNotBeFound("id.greaterThan=" + id);

        defaultUserRatingsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUserRatingsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllUserRatingsByRatingIsEqualToSomething() throws Exception {
        // Initialize the database
        userRatingsRepository.saveAndFlush(userRatings);

        // Get all the userRatingsList where rating equals to DEFAULT_RATING
        defaultUserRatingsShouldBeFound("rating.equals=" + DEFAULT_RATING);

        // Get all the userRatingsList where rating equals to UPDATED_RATING
        defaultUserRatingsShouldNotBeFound("rating.equals=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    void getAllUserRatingsByRatingIsInShouldWork() throws Exception {
        // Initialize the database
        userRatingsRepository.saveAndFlush(userRatings);

        // Get all the userRatingsList where rating in DEFAULT_RATING or UPDATED_RATING
        defaultUserRatingsShouldBeFound("rating.in=" + DEFAULT_RATING + "," + UPDATED_RATING);

        // Get all the userRatingsList where rating equals to UPDATED_RATING
        defaultUserRatingsShouldNotBeFound("rating.in=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    void getAllUserRatingsByRatingIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRatingsRepository.saveAndFlush(userRatings);

        // Get all the userRatingsList where rating is not null
        defaultUserRatingsShouldBeFound("rating.specified=true");

        // Get all the userRatingsList where rating is null
        defaultUserRatingsShouldNotBeFound("rating.specified=false");
    }

    @Test
    @Transactional
    void getAllUserRatingsByRatingContainsSomething() throws Exception {
        // Initialize the database
        userRatingsRepository.saveAndFlush(userRatings);

        // Get all the userRatingsList where rating contains DEFAULT_RATING
        defaultUserRatingsShouldBeFound("rating.contains=" + DEFAULT_RATING);

        // Get all the userRatingsList where rating contains UPDATED_RATING
        defaultUserRatingsShouldNotBeFound("rating.contains=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    void getAllUserRatingsByRatingNotContainsSomething() throws Exception {
        // Initialize the database
        userRatingsRepository.saveAndFlush(userRatings);

        // Get all the userRatingsList where rating does not contain DEFAULT_RATING
        defaultUserRatingsShouldNotBeFound("rating.doesNotContain=" + DEFAULT_RATING);

        // Get all the userRatingsList where rating does not contain UPDATED_RATING
        defaultUserRatingsShouldBeFound("rating.doesNotContain=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    void getAllUserRatingsByCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        userRatingsRepository.saveAndFlush(userRatings);

        // Get all the userRatingsList where comment equals to DEFAULT_COMMENT
        defaultUserRatingsShouldBeFound("comment.equals=" + DEFAULT_COMMENT);

        // Get all the userRatingsList where comment equals to UPDATED_COMMENT
        defaultUserRatingsShouldNotBeFound("comment.equals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllUserRatingsByCommentIsInShouldWork() throws Exception {
        // Initialize the database
        userRatingsRepository.saveAndFlush(userRatings);

        // Get all the userRatingsList where comment in DEFAULT_COMMENT or UPDATED_COMMENT
        defaultUserRatingsShouldBeFound("comment.in=" + DEFAULT_COMMENT + "," + UPDATED_COMMENT);

        // Get all the userRatingsList where comment equals to UPDATED_COMMENT
        defaultUserRatingsShouldNotBeFound("comment.in=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllUserRatingsByCommentIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRatingsRepository.saveAndFlush(userRatings);

        // Get all the userRatingsList where comment is not null
        defaultUserRatingsShouldBeFound("comment.specified=true");

        // Get all the userRatingsList where comment is null
        defaultUserRatingsShouldNotBeFound("comment.specified=false");
    }

    @Test
    @Transactional
    void getAllUserRatingsByCommentContainsSomething() throws Exception {
        // Initialize the database
        userRatingsRepository.saveAndFlush(userRatings);

        // Get all the userRatingsList where comment contains DEFAULT_COMMENT
        defaultUserRatingsShouldBeFound("comment.contains=" + DEFAULT_COMMENT);

        // Get all the userRatingsList where comment contains UPDATED_COMMENT
        defaultUserRatingsShouldNotBeFound("comment.contains=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllUserRatingsByCommentNotContainsSomething() throws Exception {
        // Initialize the database
        userRatingsRepository.saveAndFlush(userRatings);

        // Get all the userRatingsList where comment does not contain DEFAULT_COMMENT
        defaultUserRatingsShouldNotBeFound("comment.doesNotContain=" + DEFAULT_COMMENT);

        // Get all the userRatingsList where comment does not contain UPDATED_COMMENT
        defaultUserRatingsShouldBeFound("comment.doesNotContain=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllUserRatingsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        userRatingsRepository.saveAndFlush(userRatings);

        // Get all the userRatingsList where createdAt equals to DEFAULT_CREATED_AT
        defaultUserRatingsShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the userRatingsList where createdAt equals to UPDATED_CREATED_AT
        defaultUserRatingsShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllUserRatingsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        userRatingsRepository.saveAndFlush(userRatings);

        // Get all the userRatingsList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultUserRatingsShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the userRatingsList where createdAt equals to UPDATED_CREATED_AT
        defaultUserRatingsShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllUserRatingsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRatingsRepository.saveAndFlush(userRatings);

        // Get all the userRatingsList where createdAt is not null
        defaultUserRatingsShouldBeFound("createdAt.specified=true");

        // Get all the userRatingsList where createdAt is null
        defaultUserRatingsShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllUserRatingsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        userRatingsRepository.saveAndFlush(userRatings);

        // Get all the userRatingsList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultUserRatingsShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the userRatingsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultUserRatingsShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllUserRatingsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        userRatingsRepository.saveAndFlush(userRatings);

        // Get all the userRatingsList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultUserRatingsShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the userRatingsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultUserRatingsShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllUserRatingsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRatingsRepository.saveAndFlush(userRatings);

        // Get all the userRatingsList where updatedAt is not null
        defaultUserRatingsShouldBeFound("updatedAt.specified=true");

        // Get all the userRatingsList where updatedAt is null
        defaultUserRatingsShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllUserRatingsByDeletedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        userRatingsRepository.saveAndFlush(userRatings);

        // Get all the userRatingsList where deletedAt equals to DEFAULT_DELETED_AT
        defaultUserRatingsShouldBeFound("deletedAt.equals=" + DEFAULT_DELETED_AT);

        // Get all the userRatingsList where deletedAt equals to UPDATED_DELETED_AT
        defaultUserRatingsShouldNotBeFound("deletedAt.equals=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllUserRatingsByDeletedAtIsInShouldWork() throws Exception {
        // Initialize the database
        userRatingsRepository.saveAndFlush(userRatings);

        // Get all the userRatingsList where deletedAt in DEFAULT_DELETED_AT or UPDATED_DELETED_AT
        defaultUserRatingsShouldBeFound("deletedAt.in=" + DEFAULT_DELETED_AT + "," + UPDATED_DELETED_AT);

        // Get all the userRatingsList where deletedAt equals to UPDATED_DELETED_AT
        defaultUserRatingsShouldNotBeFound("deletedAt.in=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllUserRatingsByDeletedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRatingsRepository.saveAndFlush(userRatings);

        // Get all the userRatingsList where deletedAt is not null
        defaultUserRatingsShouldBeFound("deletedAt.specified=true");

        // Get all the userRatingsList where deletedAt is null
        defaultUserRatingsShouldNotBeFound("deletedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllUserRatingsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        userRatingsRepository.saveAndFlush(userRatings);

        // Get all the userRatingsList where version equals to DEFAULT_VERSION
        defaultUserRatingsShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the userRatingsList where version equals to UPDATED_VERSION
        defaultUserRatingsShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllUserRatingsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        userRatingsRepository.saveAndFlush(userRatings);

        // Get all the userRatingsList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultUserRatingsShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the userRatingsList where version equals to UPDATED_VERSION
        defaultUserRatingsShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllUserRatingsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRatingsRepository.saveAndFlush(userRatings);

        // Get all the userRatingsList where version is not null
        defaultUserRatingsShouldBeFound("version.specified=true");

        // Get all the userRatingsList where version is null
        defaultUserRatingsShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllUserRatingsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userRatingsRepository.saveAndFlush(userRatings);

        // Get all the userRatingsList where version is greater than or equal to DEFAULT_VERSION
        defaultUserRatingsShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the userRatingsList where version is greater than or equal to UPDATED_VERSION
        defaultUserRatingsShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllUserRatingsByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userRatingsRepository.saveAndFlush(userRatings);

        // Get all the userRatingsList where version is less than or equal to DEFAULT_VERSION
        defaultUserRatingsShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the userRatingsList where version is less than or equal to SMALLER_VERSION
        defaultUserRatingsShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllUserRatingsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        userRatingsRepository.saveAndFlush(userRatings);

        // Get all the userRatingsList where version is less than DEFAULT_VERSION
        defaultUserRatingsShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the userRatingsList where version is less than UPDATED_VERSION
        defaultUserRatingsShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllUserRatingsByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userRatingsRepository.saveAndFlush(userRatings);

        // Get all the userRatingsList where version is greater than DEFAULT_VERSION
        defaultUserRatingsShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the userRatingsList where version is greater than SMALLER_VERSION
        defaultUserRatingsShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllUserRatingsByUserIsEqualToSomething() throws Exception {
        Employees user;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            userRatingsRepository.saveAndFlush(userRatings);
            user = EmployeesResourceIT.createEntity(em);
        } else {
            user = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(user);
        em.flush();
        userRatings.setUser(user);
        userRatingsRepository.saveAndFlush(userRatings);
        Long userId = user.getId();

        // Get all the userRatingsList where user equals to userId
        defaultUserRatingsShouldBeFound("userId.equals=" + userId);

        // Get all the userRatingsList where user equals to (userId + 1)
        defaultUserRatingsShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    @Test
    @Transactional
    void getAllUserRatingsByReviewManagerIsEqualToSomething() throws Exception {
        Employees reviewManager;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            userRatingsRepository.saveAndFlush(userRatings);
            reviewManager = EmployeesResourceIT.createEntity(em);
        } else {
            reviewManager = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(reviewManager);
        em.flush();
        userRatings.setReviewManager(reviewManager);
        userRatingsRepository.saveAndFlush(userRatings);
        Long reviewManagerId = reviewManager.getId();

        // Get all the userRatingsList where reviewManager equals to reviewManagerId
        defaultUserRatingsShouldBeFound("reviewManagerId.equals=" + reviewManagerId);

        // Get all the userRatingsList where reviewManager equals to (reviewManagerId + 1)
        defaultUserRatingsShouldNotBeFound("reviewManagerId.equals=" + (reviewManagerId + 1));
    }

    @Test
    @Transactional
    void getAllUserRatingsByPerformanceCycleIsEqualToSomething() throws Exception {
        HrPerformanceCycles performanceCycle;
        if (TestUtil.findAll(em, HrPerformanceCycles.class).isEmpty()) {
            userRatingsRepository.saveAndFlush(userRatings);
            performanceCycle = HrPerformanceCyclesResourceIT.createEntity(em);
        } else {
            performanceCycle = TestUtil.findAll(em, HrPerformanceCycles.class).get(0);
        }
        em.persist(performanceCycle);
        em.flush();
        userRatings.setPerformanceCycle(performanceCycle);
        userRatingsRepository.saveAndFlush(userRatings);
        Long performanceCycleId = performanceCycle.getId();

        // Get all the userRatingsList where performanceCycle equals to performanceCycleId
        defaultUserRatingsShouldBeFound("performanceCycleId.equals=" + performanceCycleId);

        // Get all the userRatingsList where performanceCycle equals to (performanceCycleId + 1)
        defaultUserRatingsShouldNotBeFound("performanceCycleId.equals=" + (performanceCycleId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserRatingsShouldBeFound(String filter) throws Exception {
        restUserRatingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userRatings.getId().intValue())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restUserRatingsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUserRatingsShouldNotBeFound(String filter) throws Exception {
        restUserRatingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserRatingsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingUserRatings() throws Exception {
        // Get the userRatings
        restUserRatingsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUserRatings() throws Exception {
        // Initialize the database
        userRatingsRepository.saveAndFlush(userRatings);

        int databaseSizeBeforeUpdate = userRatingsRepository.findAll().size();

        // Update the userRatings
        UserRatings updatedUserRatings = userRatingsRepository.findById(userRatings.getId()).get();
        // Disconnect from session so that the updates on updatedUserRatings are not directly saved in db
        em.detach(updatedUserRatings);
        updatedUserRatings
            .rating(UPDATED_RATING)
            .comment(UPDATED_COMMENT)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restUserRatingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUserRatings.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUserRatings))
            )
            .andExpect(status().isOk());

        // Validate the UserRatings in the database
        List<UserRatings> userRatingsList = userRatingsRepository.findAll();
        assertThat(userRatingsList).hasSize(databaseSizeBeforeUpdate);
        UserRatings testUserRatings = userRatingsList.get(userRatingsList.size() - 1);
        assertThat(testUserRatings.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testUserRatings.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testUserRatings.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testUserRatings.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testUserRatings.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testUserRatings.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingUserRatings() throws Exception {
        int databaseSizeBeforeUpdate = userRatingsRepository.findAll().size();
        userRatings.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserRatingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userRatings.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRatings))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserRatings in the database
        List<UserRatings> userRatingsList = userRatingsRepository.findAll();
        assertThat(userRatingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserRatings() throws Exception {
        int databaseSizeBeforeUpdate = userRatingsRepository.findAll().size();
        userRatings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserRatingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRatings))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserRatings in the database
        List<UserRatings> userRatingsList = userRatingsRepository.findAll();
        assertThat(userRatingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserRatings() throws Exception {
        int databaseSizeBeforeUpdate = userRatingsRepository.findAll().size();
        userRatings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserRatingsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRatings))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserRatings in the database
        List<UserRatings> userRatingsList = userRatingsRepository.findAll();
        assertThat(userRatingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserRatingsWithPatch() throws Exception {
        // Initialize the database
        userRatingsRepository.saveAndFlush(userRatings);

        int databaseSizeBeforeUpdate = userRatingsRepository.findAll().size();

        // Update the userRatings using partial update
        UserRatings partialUpdatedUserRatings = new UserRatings();
        partialUpdatedUserRatings.setId(userRatings.getId());

        partialUpdatedUserRatings
            .rating(UPDATED_RATING)
            .comment(UPDATED_COMMENT)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT);

        restUserRatingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserRatings.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserRatings))
            )
            .andExpect(status().isOk());

        // Validate the UserRatings in the database
        List<UserRatings> userRatingsList = userRatingsRepository.findAll();
        assertThat(userRatingsList).hasSize(databaseSizeBeforeUpdate);
        UserRatings testUserRatings = userRatingsList.get(userRatingsList.size() - 1);
        assertThat(testUserRatings.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testUserRatings.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testUserRatings.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testUserRatings.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testUserRatings.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testUserRatings.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateUserRatingsWithPatch() throws Exception {
        // Initialize the database
        userRatingsRepository.saveAndFlush(userRatings);

        int databaseSizeBeforeUpdate = userRatingsRepository.findAll().size();

        // Update the userRatings using partial update
        UserRatings partialUpdatedUserRatings = new UserRatings();
        partialUpdatedUserRatings.setId(userRatings.getId());

        partialUpdatedUserRatings
            .rating(UPDATED_RATING)
            .comment(UPDATED_COMMENT)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restUserRatingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserRatings.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserRatings))
            )
            .andExpect(status().isOk());

        // Validate the UserRatings in the database
        List<UserRatings> userRatingsList = userRatingsRepository.findAll();
        assertThat(userRatingsList).hasSize(databaseSizeBeforeUpdate);
        UserRatings testUserRatings = userRatingsList.get(userRatingsList.size() - 1);
        assertThat(testUserRatings.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testUserRatings.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testUserRatings.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testUserRatings.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testUserRatings.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testUserRatings.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingUserRatings() throws Exception {
        int databaseSizeBeforeUpdate = userRatingsRepository.findAll().size();
        userRatings.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserRatingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userRatings.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userRatings))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserRatings in the database
        List<UserRatings> userRatingsList = userRatingsRepository.findAll();
        assertThat(userRatingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserRatings() throws Exception {
        int databaseSizeBeforeUpdate = userRatingsRepository.findAll().size();
        userRatings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserRatingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userRatings))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserRatings in the database
        List<UserRatings> userRatingsList = userRatingsRepository.findAll();
        assertThat(userRatingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserRatings() throws Exception {
        int databaseSizeBeforeUpdate = userRatingsRepository.findAll().size();
        userRatings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserRatingsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userRatings))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserRatings in the database
        List<UserRatings> userRatingsList = userRatingsRepository.findAll();
        assertThat(userRatingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserRatings() throws Exception {
        // Initialize the database
        userRatingsRepository.saveAndFlush(userRatings);

        int databaseSizeBeforeDelete = userRatingsRepository.findAll().size();

        // Delete the userRatings
        restUserRatingsMockMvc
            .perform(delete(ENTITY_API_URL_ID, userRatings.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserRatings> userRatingsList = userRatingsRepository.findAll();
        assertThat(userRatingsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
