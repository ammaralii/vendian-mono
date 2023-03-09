package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.PcRatings;
import com.venturedive.vendian_mono.domain.UserGoalRaterAttributes;
import com.venturedive.vendian_mono.domain.UserGoals;
import com.venturedive.vendian_mono.repository.UserGoalRaterAttributesRepository;
import com.venturedive.vendian_mono.service.criteria.UserGoalRaterAttributesCriteria;
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
 * Integration tests for the {@link UserGoalRaterAttributesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserGoalRaterAttributesResourceIT {

    private static final byte[] DEFAULT_UGRA_RATING = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_UGRA_RATING = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_UGRA_RATING_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_UGRA_RATING_CONTENT_TYPE = "image/png";

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

    private static final String ENTITY_API_URL = "/api/user-goal-rater-attributes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserGoalRaterAttributesRepository userGoalRaterAttributesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserGoalRaterAttributesMockMvc;

    private UserGoalRaterAttributes userGoalRaterAttributes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserGoalRaterAttributes createEntity(EntityManager em) {
        UserGoalRaterAttributes userGoalRaterAttributes = new UserGoalRaterAttributes()
            .ugraRating(DEFAULT_UGRA_RATING)
            .ugraRatingContentType(DEFAULT_UGRA_RATING_CONTENT_TYPE)
            .comment(DEFAULT_COMMENT)
            .commentContentType(DEFAULT_COMMENT_CONTENT_TYPE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT)
            .version(DEFAULT_VERSION);
        // Add required entity
        PcRatings pcRatings;
        if (TestUtil.findAll(em, PcRatings.class).isEmpty()) {
            pcRatings = PcRatingsResourceIT.createEntity(em);
            em.persist(pcRatings);
            em.flush();
        } else {
            pcRatings = TestUtil.findAll(em, PcRatings.class).get(0);
        }
        userGoalRaterAttributes.setRating(pcRatings);
        return userGoalRaterAttributes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserGoalRaterAttributes createUpdatedEntity(EntityManager em) {
        UserGoalRaterAttributes userGoalRaterAttributes = new UserGoalRaterAttributes()
            .ugraRating(UPDATED_UGRA_RATING)
            .ugraRatingContentType(UPDATED_UGRA_RATING_CONTENT_TYPE)
            .comment(UPDATED_COMMENT)
            .commentContentType(UPDATED_COMMENT_CONTENT_TYPE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);
        // Add required entity
        PcRatings pcRatings;
        if (TestUtil.findAll(em, PcRatings.class).isEmpty()) {
            pcRatings = PcRatingsResourceIT.createUpdatedEntity(em);
            em.persist(pcRatings);
            em.flush();
        } else {
            pcRatings = TestUtil.findAll(em, PcRatings.class).get(0);
        }
        userGoalRaterAttributes.setRating(pcRatings);
        return userGoalRaterAttributes;
    }

    @BeforeEach
    public void initTest() {
        userGoalRaterAttributes = createEntity(em);
    }

    @Test
    @Transactional
    void createUserGoalRaterAttributes() throws Exception {
        int databaseSizeBeforeCreate = userGoalRaterAttributesRepository.findAll().size();
        // Create the UserGoalRaterAttributes
        restUserGoalRaterAttributesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userGoalRaterAttributes))
            )
            .andExpect(status().isCreated());

        // Validate the UserGoalRaterAttributes in the database
        List<UserGoalRaterAttributes> userGoalRaterAttributesList = userGoalRaterAttributesRepository.findAll();
        assertThat(userGoalRaterAttributesList).hasSize(databaseSizeBeforeCreate + 1);
        UserGoalRaterAttributes testUserGoalRaterAttributes = userGoalRaterAttributesList.get(userGoalRaterAttributesList.size() - 1);
        assertThat(testUserGoalRaterAttributes.getUgraRating()).isEqualTo(DEFAULT_UGRA_RATING);
        assertThat(testUserGoalRaterAttributes.getUgraRatingContentType()).isEqualTo(DEFAULT_UGRA_RATING_CONTENT_TYPE);
        assertThat(testUserGoalRaterAttributes.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testUserGoalRaterAttributes.getCommentContentType()).isEqualTo(DEFAULT_COMMENT_CONTENT_TYPE);
        assertThat(testUserGoalRaterAttributes.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testUserGoalRaterAttributes.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testUserGoalRaterAttributes.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testUserGoalRaterAttributes.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createUserGoalRaterAttributesWithExistingId() throws Exception {
        // Create the UserGoalRaterAttributes with an existing ID
        userGoalRaterAttributes.setId(1L);

        int databaseSizeBeforeCreate = userGoalRaterAttributesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserGoalRaterAttributesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userGoalRaterAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserGoalRaterAttributes in the database
        List<UserGoalRaterAttributes> userGoalRaterAttributesList = userGoalRaterAttributesRepository.findAll();
        assertThat(userGoalRaterAttributesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = userGoalRaterAttributesRepository.findAll().size();
        // set the field null
        userGoalRaterAttributes.setCreatedAt(null);

        // Create the UserGoalRaterAttributes, which fails.

        restUserGoalRaterAttributesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userGoalRaterAttributes))
            )
            .andExpect(status().isBadRequest());

        List<UserGoalRaterAttributes> userGoalRaterAttributesList = userGoalRaterAttributesRepository.findAll();
        assertThat(userGoalRaterAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = userGoalRaterAttributesRepository.findAll().size();
        // set the field null
        userGoalRaterAttributes.setUpdatedAt(null);

        // Create the UserGoalRaterAttributes, which fails.

        restUserGoalRaterAttributesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userGoalRaterAttributes))
            )
            .andExpect(status().isBadRequest());

        List<UserGoalRaterAttributes> userGoalRaterAttributesList = userGoalRaterAttributesRepository.findAll();
        assertThat(userGoalRaterAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = userGoalRaterAttributesRepository.findAll().size();
        // set the field null
        userGoalRaterAttributes.setVersion(null);

        // Create the UserGoalRaterAttributes, which fails.

        restUserGoalRaterAttributesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userGoalRaterAttributes))
            )
            .andExpect(status().isBadRequest());

        List<UserGoalRaterAttributes> userGoalRaterAttributesList = userGoalRaterAttributesRepository.findAll();
        assertThat(userGoalRaterAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUserGoalRaterAttributes() throws Exception {
        // Initialize the database
        userGoalRaterAttributesRepository.saveAndFlush(userGoalRaterAttributes);

        // Get all the userGoalRaterAttributesList
        restUserGoalRaterAttributesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userGoalRaterAttributes.getId().intValue())))
            .andExpect(jsonPath("$.[*].ugraRatingContentType").value(hasItem(DEFAULT_UGRA_RATING_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].ugraRating").value(hasItem(Base64Utils.encodeToString(DEFAULT_UGRA_RATING))))
            .andExpect(jsonPath("$.[*].commentContentType").value(hasItem(DEFAULT_COMMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(Base64Utils.encodeToString(DEFAULT_COMMENT))))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getUserGoalRaterAttributes() throws Exception {
        // Initialize the database
        userGoalRaterAttributesRepository.saveAndFlush(userGoalRaterAttributes);

        // Get the userGoalRaterAttributes
        restUserGoalRaterAttributesMockMvc
            .perform(get(ENTITY_API_URL_ID, userGoalRaterAttributes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userGoalRaterAttributes.getId().intValue()))
            .andExpect(jsonPath("$.ugraRatingContentType").value(DEFAULT_UGRA_RATING_CONTENT_TYPE))
            .andExpect(jsonPath("$.ugraRating").value(Base64Utils.encodeToString(DEFAULT_UGRA_RATING)))
            .andExpect(jsonPath("$.commentContentType").value(DEFAULT_COMMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.comment").value(Base64Utils.encodeToString(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getUserGoalRaterAttributesByIdFiltering() throws Exception {
        // Initialize the database
        userGoalRaterAttributesRepository.saveAndFlush(userGoalRaterAttributes);

        Long id = userGoalRaterAttributes.getId();

        defaultUserGoalRaterAttributesShouldBeFound("id.equals=" + id);
        defaultUserGoalRaterAttributesShouldNotBeFound("id.notEquals=" + id);

        defaultUserGoalRaterAttributesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUserGoalRaterAttributesShouldNotBeFound("id.greaterThan=" + id);

        defaultUserGoalRaterAttributesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUserGoalRaterAttributesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllUserGoalRaterAttributesByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        userGoalRaterAttributesRepository.saveAndFlush(userGoalRaterAttributes);

        // Get all the userGoalRaterAttributesList where createdAt equals to DEFAULT_CREATED_AT
        defaultUserGoalRaterAttributesShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the userGoalRaterAttributesList where createdAt equals to UPDATED_CREATED_AT
        defaultUserGoalRaterAttributesShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllUserGoalRaterAttributesByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        userGoalRaterAttributesRepository.saveAndFlush(userGoalRaterAttributes);

        // Get all the userGoalRaterAttributesList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultUserGoalRaterAttributesShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the userGoalRaterAttributesList where createdAt equals to UPDATED_CREATED_AT
        defaultUserGoalRaterAttributesShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllUserGoalRaterAttributesByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        userGoalRaterAttributesRepository.saveAndFlush(userGoalRaterAttributes);

        // Get all the userGoalRaterAttributesList where createdAt is not null
        defaultUserGoalRaterAttributesShouldBeFound("createdAt.specified=true");

        // Get all the userGoalRaterAttributesList where createdAt is null
        defaultUserGoalRaterAttributesShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllUserGoalRaterAttributesByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        userGoalRaterAttributesRepository.saveAndFlush(userGoalRaterAttributes);

        // Get all the userGoalRaterAttributesList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultUserGoalRaterAttributesShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the userGoalRaterAttributesList where updatedAt equals to UPDATED_UPDATED_AT
        defaultUserGoalRaterAttributesShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllUserGoalRaterAttributesByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        userGoalRaterAttributesRepository.saveAndFlush(userGoalRaterAttributes);

        // Get all the userGoalRaterAttributesList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultUserGoalRaterAttributesShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the userGoalRaterAttributesList where updatedAt equals to UPDATED_UPDATED_AT
        defaultUserGoalRaterAttributesShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllUserGoalRaterAttributesByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        userGoalRaterAttributesRepository.saveAndFlush(userGoalRaterAttributes);

        // Get all the userGoalRaterAttributesList where updatedAt is not null
        defaultUserGoalRaterAttributesShouldBeFound("updatedAt.specified=true");

        // Get all the userGoalRaterAttributesList where updatedAt is null
        defaultUserGoalRaterAttributesShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllUserGoalRaterAttributesByDeletedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        userGoalRaterAttributesRepository.saveAndFlush(userGoalRaterAttributes);

        // Get all the userGoalRaterAttributesList where deletedAt equals to DEFAULT_DELETED_AT
        defaultUserGoalRaterAttributesShouldBeFound("deletedAt.equals=" + DEFAULT_DELETED_AT);

        // Get all the userGoalRaterAttributesList where deletedAt equals to UPDATED_DELETED_AT
        defaultUserGoalRaterAttributesShouldNotBeFound("deletedAt.equals=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllUserGoalRaterAttributesByDeletedAtIsInShouldWork() throws Exception {
        // Initialize the database
        userGoalRaterAttributesRepository.saveAndFlush(userGoalRaterAttributes);

        // Get all the userGoalRaterAttributesList where deletedAt in DEFAULT_DELETED_AT or UPDATED_DELETED_AT
        defaultUserGoalRaterAttributesShouldBeFound("deletedAt.in=" + DEFAULT_DELETED_AT + "," + UPDATED_DELETED_AT);

        // Get all the userGoalRaterAttributesList where deletedAt equals to UPDATED_DELETED_AT
        defaultUserGoalRaterAttributesShouldNotBeFound("deletedAt.in=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllUserGoalRaterAttributesByDeletedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        userGoalRaterAttributesRepository.saveAndFlush(userGoalRaterAttributes);

        // Get all the userGoalRaterAttributesList where deletedAt is not null
        defaultUserGoalRaterAttributesShouldBeFound("deletedAt.specified=true");

        // Get all the userGoalRaterAttributesList where deletedAt is null
        defaultUserGoalRaterAttributesShouldNotBeFound("deletedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllUserGoalRaterAttributesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        userGoalRaterAttributesRepository.saveAndFlush(userGoalRaterAttributes);

        // Get all the userGoalRaterAttributesList where version equals to DEFAULT_VERSION
        defaultUserGoalRaterAttributesShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the userGoalRaterAttributesList where version equals to UPDATED_VERSION
        defaultUserGoalRaterAttributesShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllUserGoalRaterAttributesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        userGoalRaterAttributesRepository.saveAndFlush(userGoalRaterAttributes);

        // Get all the userGoalRaterAttributesList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultUserGoalRaterAttributesShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the userGoalRaterAttributesList where version equals to UPDATED_VERSION
        defaultUserGoalRaterAttributesShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllUserGoalRaterAttributesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        userGoalRaterAttributesRepository.saveAndFlush(userGoalRaterAttributes);

        // Get all the userGoalRaterAttributesList where version is not null
        defaultUserGoalRaterAttributesShouldBeFound("version.specified=true");

        // Get all the userGoalRaterAttributesList where version is null
        defaultUserGoalRaterAttributesShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllUserGoalRaterAttributesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userGoalRaterAttributesRepository.saveAndFlush(userGoalRaterAttributes);

        // Get all the userGoalRaterAttributesList where version is greater than or equal to DEFAULT_VERSION
        defaultUserGoalRaterAttributesShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the userGoalRaterAttributesList where version is greater than or equal to UPDATED_VERSION
        defaultUserGoalRaterAttributesShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllUserGoalRaterAttributesByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userGoalRaterAttributesRepository.saveAndFlush(userGoalRaterAttributes);

        // Get all the userGoalRaterAttributesList where version is less than or equal to DEFAULT_VERSION
        defaultUserGoalRaterAttributesShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the userGoalRaterAttributesList where version is less than or equal to SMALLER_VERSION
        defaultUserGoalRaterAttributesShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllUserGoalRaterAttributesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        userGoalRaterAttributesRepository.saveAndFlush(userGoalRaterAttributes);

        // Get all the userGoalRaterAttributesList where version is less than DEFAULT_VERSION
        defaultUserGoalRaterAttributesShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the userGoalRaterAttributesList where version is less than UPDATED_VERSION
        defaultUserGoalRaterAttributesShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllUserGoalRaterAttributesByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userGoalRaterAttributesRepository.saveAndFlush(userGoalRaterAttributes);

        // Get all the userGoalRaterAttributesList where version is greater than DEFAULT_VERSION
        defaultUserGoalRaterAttributesShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the userGoalRaterAttributesList where version is greater than SMALLER_VERSION
        defaultUserGoalRaterAttributesShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllUserGoalRaterAttributesByRatingIsEqualToSomething() throws Exception {
        PcRatings rating;
        if (TestUtil.findAll(em, PcRatings.class).isEmpty()) {
            userGoalRaterAttributesRepository.saveAndFlush(userGoalRaterAttributes);
            rating = PcRatingsResourceIT.createEntity(em);
        } else {
            rating = TestUtil.findAll(em, PcRatings.class).get(0);
        }
        em.persist(rating);
        em.flush();
        userGoalRaterAttributes.setRating(rating);
        userGoalRaterAttributesRepository.saveAndFlush(userGoalRaterAttributes);
        Long ratingId = rating.getId();

        // Get all the userGoalRaterAttributesList where rating equals to ratingId
        defaultUserGoalRaterAttributesShouldBeFound("ratingId.equals=" + ratingId);

        // Get all the userGoalRaterAttributesList where rating equals to (ratingId + 1)
        defaultUserGoalRaterAttributesShouldNotBeFound("ratingId.equals=" + (ratingId + 1));
    }

    @Test
    @Transactional
    void getAllUserGoalRaterAttributesByUsergoalIsEqualToSomething() throws Exception {
        UserGoals usergoal;
        if (TestUtil.findAll(em, UserGoals.class).isEmpty()) {
            userGoalRaterAttributesRepository.saveAndFlush(userGoalRaterAttributes);
            usergoal = UserGoalsResourceIT.createEntity(em);
        } else {
            usergoal = TestUtil.findAll(em, UserGoals.class).get(0);
        }
        em.persist(usergoal);
        em.flush();
        userGoalRaterAttributes.setUsergoal(usergoal);
        userGoalRaterAttributesRepository.saveAndFlush(userGoalRaterAttributes);
        Long usergoalId = usergoal.getId();

        // Get all the userGoalRaterAttributesList where usergoal equals to usergoalId
        defaultUserGoalRaterAttributesShouldBeFound("usergoalId.equals=" + usergoalId);

        // Get all the userGoalRaterAttributesList where usergoal equals to (usergoalId + 1)
        defaultUserGoalRaterAttributesShouldNotBeFound("usergoalId.equals=" + (usergoalId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserGoalRaterAttributesShouldBeFound(String filter) throws Exception {
        restUserGoalRaterAttributesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userGoalRaterAttributes.getId().intValue())))
            .andExpect(jsonPath("$.[*].ugraRatingContentType").value(hasItem(DEFAULT_UGRA_RATING_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].ugraRating").value(hasItem(Base64Utils.encodeToString(DEFAULT_UGRA_RATING))))
            .andExpect(jsonPath("$.[*].commentContentType").value(hasItem(DEFAULT_COMMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(Base64Utils.encodeToString(DEFAULT_COMMENT))))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restUserGoalRaterAttributesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUserGoalRaterAttributesShouldNotBeFound(String filter) throws Exception {
        restUserGoalRaterAttributesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserGoalRaterAttributesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingUserGoalRaterAttributes() throws Exception {
        // Get the userGoalRaterAttributes
        restUserGoalRaterAttributesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUserGoalRaterAttributes() throws Exception {
        // Initialize the database
        userGoalRaterAttributesRepository.saveAndFlush(userGoalRaterAttributes);

        int databaseSizeBeforeUpdate = userGoalRaterAttributesRepository.findAll().size();

        // Update the userGoalRaterAttributes
        UserGoalRaterAttributes updatedUserGoalRaterAttributes = userGoalRaterAttributesRepository
            .findById(userGoalRaterAttributes.getId())
            .get();
        // Disconnect from session so that the updates on updatedUserGoalRaterAttributes are not directly saved in db
        em.detach(updatedUserGoalRaterAttributes);
        updatedUserGoalRaterAttributes
            .ugraRating(UPDATED_UGRA_RATING)
            .ugraRatingContentType(UPDATED_UGRA_RATING_CONTENT_TYPE)
            .comment(UPDATED_COMMENT)
            .commentContentType(UPDATED_COMMENT_CONTENT_TYPE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restUserGoalRaterAttributesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUserGoalRaterAttributes.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUserGoalRaterAttributes))
            )
            .andExpect(status().isOk());

        // Validate the UserGoalRaterAttributes in the database
        List<UserGoalRaterAttributes> userGoalRaterAttributesList = userGoalRaterAttributesRepository.findAll();
        assertThat(userGoalRaterAttributesList).hasSize(databaseSizeBeforeUpdate);
        UserGoalRaterAttributes testUserGoalRaterAttributes = userGoalRaterAttributesList.get(userGoalRaterAttributesList.size() - 1);
        assertThat(testUserGoalRaterAttributes.getUgraRating()).isEqualTo(UPDATED_UGRA_RATING);
        assertThat(testUserGoalRaterAttributes.getUgraRatingContentType()).isEqualTo(UPDATED_UGRA_RATING_CONTENT_TYPE);
        assertThat(testUserGoalRaterAttributes.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testUserGoalRaterAttributes.getCommentContentType()).isEqualTo(UPDATED_COMMENT_CONTENT_TYPE);
        assertThat(testUserGoalRaterAttributes.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testUserGoalRaterAttributes.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testUserGoalRaterAttributes.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testUserGoalRaterAttributes.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingUserGoalRaterAttributes() throws Exception {
        int databaseSizeBeforeUpdate = userGoalRaterAttributesRepository.findAll().size();
        userGoalRaterAttributes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserGoalRaterAttributesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userGoalRaterAttributes.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userGoalRaterAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserGoalRaterAttributes in the database
        List<UserGoalRaterAttributes> userGoalRaterAttributesList = userGoalRaterAttributesRepository.findAll();
        assertThat(userGoalRaterAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserGoalRaterAttributes() throws Exception {
        int databaseSizeBeforeUpdate = userGoalRaterAttributesRepository.findAll().size();
        userGoalRaterAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserGoalRaterAttributesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userGoalRaterAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserGoalRaterAttributes in the database
        List<UserGoalRaterAttributes> userGoalRaterAttributesList = userGoalRaterAttributesRepository.findAll();
        assertThat(userGoalRaterAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserGoalRaterAttributes() throws Exception {
        int databaseSizeBeforeUpdate = userGoalRaterAttributesRepository.findAll().size();
        userGoalRaterAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserGoalRaterAttributesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userGoalRaterAttributes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserGoalRaterAttributes in the database
        List<UserGoalRaterAttributes> userGoalRaterAttributesList = userGoalRaterAttributesRepository.findAll();
        assertThat(userGoalRaterAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserGoalRaterAttributesWithPatch() throws Exception {
        // Initialize the database
        userGoalRaterAttributesRepository.saveAndFlush(userGoalRaterAttributes);

        int databaseSizeBeforeUpdate = userGoalRaterAttributesRepository.findAll().size();

        // Update the userGoalRaterAttributes using partial update
        UserGoalRaterAttributes partialUpdatedUserGoalRaterAttributes = new UserGoalRaterAttributes();
        partialUpdatedUserGoalRaterAttributes.setId(userGoalRaterAttributes.getId());

        partialUpdatedUserGoalRaterAttributes
            .ugraRating(UPDATED_UGRA_RATING)
            .ugraRatingContentType(UPDATED_UGRA_RATING_CONTENT_TYPE)
            .comment(UPDATED_COMMENT)
            .commentContentType(UPDATED_COMMENT_CONTENT_TYPE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restUserGoalRaterAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserGoalRaterAttributes.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserGoalRaterAttributes))
            )
            .andExpect(status().isOk());

        // Validate the UserGoalRaterAttributes in the database
        List<UserGoalRaterAttributes> userGoalRaterAttributesList = userGoalRaterAttributesRepository.findAll();
        assertThat(userGoalRaterAttributesList).hasSize(databaseSizeBeforeUpdate);
        UserGoalRaterAttributes testUserGoalRaterAttributes = userGoalRaterAttributesList.get(userGoalRaterAttributesList.size() - 1);
        assertThat(testUserGoalRaterAttributes.getUgraRating()).isEqualTo(UPDATED_UGRA_RATING);
        assertThat(testUserGoalRaterAttributes.getUgraRatingContentType()).isEqualTo(UPDATED_UGRA_RATING_CONTENT_TYPE);
        assertThat(testUserGoalRaterAttributes.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testUserGoalRaterAttributes.getCommentContentType()).isEqualTo(UPDATED_COMMENT_CONTENT_TYPE);
        assertThat(testUserGoalRaterAttributes.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testUserGoalRaterAttributes.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testUserGoalRaterAttributes.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testUserGoalRaterAttributes.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateUserGoalRaterAttributesWithPatch() throws Exception {
        // Initialize the database
        userGoalRaterAttributesRepository.saveAndFlush(userGoalRaterAttributes);

        int databaseSizeBeforeUpdate = userGoalRaterAttributesRepository.findAll().size();

        // Update the userGoalRaterAttributes using partial update
        UserGoalRaterAttributes partialUpdatedUserGoalRaterAttributes = new UserGoalRaterAttributes();
        partialUpdatedUserGoalRaterAttributes.setId(userGoalRaterAttributes.getId());

        partialUpdatedUserGoalRaterAttributes
            .ugraRating(UPDATED_UGRA_RATING)
            .ugraRatingContentType(UPDATED_UGRA_RATING_CONTENT_TYPE)
            .comment(UPDATED_COMMENT)
            .commentContentType(UPDATED_COMMENT_CONTENT_TYPE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restUserGoalRaterAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserGoalRaterAttributes.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserGoalRaterAttributes))
            )
            .andExpect(status().isOk());

        // Validate the UserGoalRaterAttributes in the database
        List<UserGoalRaterAttributes> userGoalRaterAttributesList = userGoalRaterAttributesRepository.findAll();
        assertThat(userGoalRaterAttributesList).hasSize(databaseSizeBeforeUpdate);
        UserGoalRaterAttributes testUserGoalRaterAttributes = userGoalRaterAttributesList.get(userGoalRaterAttributesList.size() - 1);
        assertThat(testUserGoalRaterAttributes.getUgraRating()).isEqualTo(UPDATED_UGRA_RATING);
        assertThat(testUserGoalRaterAttributes.getUgraRatingContentType()).isEqualTo(UPDATED_UGRA_RATING_CONTENT_TYPE);
        assertThat(testUserGoalRaterAttributes.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testUserGoalRaterAttributes.getCommentContentType()).isEqualTo(UPDATED_COMMENT_CONTENT_TYPE);
        assertThat(testUserGoalRaterAttributes.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testUserGoalRaterAttributes.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testUserGoalRaterAttributes.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testUserGoalRaterAttributes.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingUserGoalRaterAttributes() throws Exception {
        int databaseSizeBeforeUpdate = userGoalRaterAttributesRepository.findAll().size();
        userGoalRaterAttributes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserGoalRaterAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userGoalRaterAttributes.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userGoalRaterAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserGoalRaterAttributes in the database
        List<UserGoalRaterAttributes> userGoalRaterAttributesList = userGoalRaterAttributesRepository.findAll();
        assertThat(userGoalRaterAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserGoalRaterAttributes() throws Exception {
        int databaseSizeBeforeUpdate = userGoalRaterAttributesRepository.findAll().size();
        userGoalRaterAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserGoalRaterAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userGoalRaterAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserGoalRaterAttributes in the database
        List<UserGoalRaterAttributes> userGoalRaterAttributesList = userGoalRaterAttributesRepository.findAll();
        assertThat(userGoalRaterAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserGoalRaterAttributes() throws Exception {
        int databaseSizeBeforeUpdate = userGoalRaterAttributesRepository.findAll().size();
        userGoalRaterAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserGoalRaterAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userGoalRaterAttributes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserGoalRaterAttributes in the database
        List<UserGoalRaterAttributes> userGoalRaterAttributesList = userGoalRaterAttributesRepository.findAll();
        assertThat(userGoalRaterAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserGoalRaterAttributes() throws Exception {
        // Initialize the database
        userGoalRaterAttributesRepository.saveAndFlush(userGoalRaterAttributes);

        int databaseSizeBeforeDelete = userGoalRaterAttributesRepository.findAll().size();

        // Delete the userGoalRaterAttributes
        restUserGoalRaterAttributesMockMvc
            .perform(delete(ENTITY_API_URL_ID, userGoalRaterAttributes.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserGoalRaterAttributes> userGoalRaterAttributesList = userGoalRaterAttributesRepository.findAll();
        assertThat(userGoalRaterAttributesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
