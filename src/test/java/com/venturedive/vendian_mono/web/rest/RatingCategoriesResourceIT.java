package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.PcRatingAttributesCategories;
import com.venturedive.vendian_mono.domain.RatingCategories;
import com.venturedive.vendian_mono.repository.RatingCategoriesRepository;
import com.venturedive.vendian_mono.service.criteria.RatingCategoriesCriteria;
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
 * Integration tests for the {@link RatingCategoriesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RatingCategoriesResourceIT {

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

    private static final String ENTITY_API_URL = "/api/rating-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RatingCategoriesRepository ratingCategoriesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRatingCategoriesMockMvc;

    private RatingCategories ratingCategories;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RatingCategories createEntity(EntityManager em) {
        RatingCategories ratingCategories = new RatingCategories()
            .name(DEFAULT_NAME)
            .effDate(DEFAULT_EFF_DATE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .endDate(DEFAULT_END_DATE)
            .version(DEFAULT_VERSION);
        return ratingCategories;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RatingCategories createUpdatedEntity(EntityManager em) {
        RatingCategories ratingCategories = new RatingCategories()
            .name(UPDATED_NAME)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);
        return ratingCategories;
    }

    @BeforeEach
    public void initTest() {
        ratingCategories = createEntity(em);
    }

    @Test
    @Transactional
    void createRatingCategories() throws Exception {
        int databaseSizeBeforeCreate = ratingCategoriesRepository.findAll().size();
        // Create the RatingCategories
        restRatingCategoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingCategories))
            )
            .andExpect(status().isCreated());

        // Validate the RatingCategories in the database
        List<RatingCategories> ratingCategoriesList = ratingCategoriesRepository.findAll();
        assertThat(ratingCategoriesList).hasSize(databaseSizeBeforeCreate + 1);
        RatingCategories testRatingCategories = ratingCategoriesList.get(ratingCategoriesList.size() - 1);
        assertThat(testRatingCategories.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRatingCategories.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testRatingCategories.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testRatingCategories.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testRatingCategories.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testRatingCategories.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createRatingCategoriesWithExistingId() throws Exception {
        // Create the RatingCategories with an existing ID
        ratingCategories.setId(1L);

        int databaseSizeBeforeCreate = ratingCategoriesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRatingCategoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingCategories))
            )
            .andExpect(status().isBadRequest());

        // Validate the RatingCategories in the database
        List<RatingCategories> ratingCategoriesList = ratingCategoriesRepository.findAll();
        assertThat(ratingCategoriesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ratingCategoriesRepository.findAll().size();
        // set the field null
        ratingCategories.setName(null);

        // Create the RatingCategories, which fails.

        restRatingCategoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingCategories))
            )
            .andExpect(status().isBadRequest());

        List<RatingCategories> ratingCategoriesList = ratingCategoriesRepository.findAll();
        assertThat(ratingCategoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEffDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = ratingCategoriesRepository.findAll().size();
        // set the field null
        ratingCategories.setEffDate(null);

        // Create the RatingCategories, which fails.

        restRatingCategoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingCategories))
            )
            .andExpect(status().isBadRequest());

        List<RatingCategories> ratingCategoriesList = ratingCategoriesRepository.findAll();
        assertThat(ratingCategoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = ratingCategoriesRepository.findAll().size();
        // set the field null
        ratingCategories.setCreatedAt(null);

        // Create the RatingCategories, which fails.

        restRatingCategoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingCategories))
            )
            .andExpect(status().isBadRequest());

        List<RatingCategories> ratingCategoriesList = ratingCategoriesRepository.findAll();
        assertThat(ratingCategoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = ratingCategoriesRepository.findAll().size();
        // set the field null
        ratingCategories.setUpdatedAt(null);

        // Create the RatingCategories, which fails.

        restRatingCategoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingCategories))
            )
            .andExpect(status().isBadRequest());

        List<RatingCategories> ratingCategoriesList = ratingCategoriesRepository.findAll();
        assertThat(ratingCategoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = ratingCategoriesRepository.findAll().size();
        // set the field null
        ratingCategories.setVersion(null);

        // Create the RatingCategories, which fails.

        restRatingCategoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingCategories))
            )
            .andExpect(status().isBadRequest());

        List<RatingCategories> ratingCategoriesList = ratingCategoriesRepository.findAll();
        assertThat(ratingCategoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRatingCategories() throws Exception {
        // Initialize the database
        ratingCategoriesRepository.saveAndFlush(ratingCategories);

        // Get all the ratingCategoriesList
        restRatingCategoriesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ratingCategories.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getRatingCategories() throws Exception {
        // Initialize the database
        ratingCategoriesRepository.saveAndFlush(ratingCategories);

        // Get the ratingCategories
        restRatingCategoriesMockMvc
            .perform(get(ENTITY_API_URL_ID, ratingCategories.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ratingCategories.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.effDate").value(DEFAULT_EFF_DATE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getRatingCategoriesByIdFiltering() throws Exception {
        // Initialize the database
        ratingCategoriesRepository.saveAndFlush(ratingCategories);

        Long id = ratingCategories.getId();

        defaultRatingCategoriesShouldBeFound("id.equals=" + id);
        defaultRatingCategoriesShouldNotBeFound("id.notEquals=" + id);

        defaultRatingCategoriesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRatingCategoriesShouldNotBeFound("id.greaterThan=" + id);

        defaultRatingCategoriesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRatingCategoriesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRatingCategoriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        ratingCategoriesRepository.saveAndFlush(ratingCategories);

        // Get all the ratingCategoriesList where name equals to DEFAULT_NAME
        defaultRatingCategoriesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the ratingCategoriesList where name equals to UPDATED_NAME
        defaultRatingCategoriesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRatingCategoriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        ratingCategoriesRepository.saveAndFlush(ratingCategories);

        // Get all the ratingCategoriesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultRatingCategoriesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the ratingCategoriesList where name equals to UPDATED_NAME
        defaultRatingCategoriesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRatingCategoriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        ratingCategoriesRepository.saveAndFlush(ratingCategories);

        // Get all the ratingCategoriesList where name is not null
        defaultRatingCategoriesShouldBeFound("name.specified=true");

        // Get all the ratingCategoriesList where name is null
        defaultRatingCategoriesShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllRatingCategoriesByNameContainsSomething() throws Exception {
        // Initialize the database
        ratingCategoriesRepository.saveAndFlush(ratingCategories);

        // Get all the ratingCategoriesList where name contains DEFAULT_NAME
        defaultRatingCategoriesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the ratingCategoriesList where name contains UPDATED_NAME
        defaultRatingCategoriesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRatingCategoriesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        ratingCategoriesRepository.saveAndFlush(ratingCategories);

        // Get all the ratingCategoriesList where name does not contain DEFAULT_NAME
        defaultRatingCategoriesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the ratingCategoriesList where name does not contain UPDATED_NAME
        defaultRatingCategoriesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRatingCategoriesByEffDateIsEqualToSomething() throws Exception {
        // Initialize the database
        ratingCategoriesRepository.saveAndFlush(ratingCategories);

        // Get all the ratingCategoriesList where effDate equals to DEFAULT_EFF_DATE
        defaultRatingCategoriesShouldBeFound("effDate.equals=" + DEFAULT_EFF_DATE);

        // Get all the ratingCategoriesList where effDate equals to UPDATED_EFF_DATE
        defaultRatingCategoriesShouldNotBeFound("effDate.equals=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllRatingCategoriesByEffDateIsInShouldWork() throws Exception {
        // Initialize the database
        ratingCategoriesRepository.saveAndFlush(ratingCategories);

        // Get all the ratingCategoriesList where effDate in DEFAULT_EFF_DATE or UPDATED_EFF_DATE
        defaultRatingCategoriesShouldBeFound("effDate.in=" + DEFAULT_EFF_DATE + "," + UPDATED_EFF_DATE);

        // Get all the ratingCategoriesList where effDate equals to UPDATED_EFF_DATE
        defaultRatingCategoriesShouldNotBeFound("effDate.in=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllRatingCategoriesByEffDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        ratingCategoriesRepository.saveAndFlush(ratingCategories);

        // Get all the ratingCategoriesList where effDate is not null
        defaultRatingCategoriesShouldBeFound("effDate.specified=true");

        // Get all the ratingCategoriesList where effDate is null
        defaultRatingCategoriesShouldNotBeFound("effDate.specified=false");
    }

    @Test
    @Transactional
    void getAllRatingCategoriesByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        ratingCategoriesRepository.saveAndFlush(ratingCategories);

        // Get all the ratingCategoriesList where createdAt equals to DEFAULT_CREATED_AT
        defaultRatingCategoriesShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the ratingCategoriesList where createdAt equals to UPDATED_CREATED_AT
        defaultRatingCategoriesShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllRatingCategoriesByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        ratingCategoriesRepository.saveAndFlush(ratingCategories);

        // Get all the ratingCategoriesList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultRatingCategoriesShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the ratingCategoriesList where createdAt equals to UPDATED_CREATED_AT
        defaultRatingCategoriesShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllRatingCategoriesByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        ratingCategoriesRepository.saveAndFlush(ratingCategories);

        // Get all the ratingCategoriesList where createdAt is not null
        defaultRatingCategoriesShouldBeFound("createdAt.specified=true");

        // Get all the ratingCategoriesList where createdAt is null
        defaultRatingCategoriesShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllRatingCategoriesByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        ratingCategoriesRepository.saveAndFlush(ratingCategories);

        // Get all the ratingCategoriesList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultRatingCategoriesShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the ratingCategoriesList where updatedAt equals to UPDATED_UPDATED_AT
        defaultRatingCategoriesShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllRatingCategoriesByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        ratingCategoriesRepository.saveAndFlush(ratingCategories);

        // Get all the ratingCategoriesList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultRatingCategoriesShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the ratingCategoriesList where updatedAt equals to UPDATED_UPDATED_AT
        defaultRatingCategoriesShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllRatingCategoriesByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        ratingCategoriesRepository.saveAndFlush(ratingCategories);

        // Get all the ratingCategoriesList where updatedAt is not null
        defaultRatingCategoriesShouldBeFound("updatedAt.specified=true");

        // Get all the ratingCategoriesList where updatedAt is null
        defaultRatingCategoriesShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllRatingCategoriesByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        ratingCategoriesRepository.saveAndFlush(ratingCategories);

        // Get all the ratingCategoriesList where endDate equals to DEFAULT_END_DATE
        defaultRatingCategoriesShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the ratingCategoriesList where endDate equals to UPDATED_END_DATE
        defaultRatingCategoriesShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllRatingCategoriesByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        ratingCategoriesRepository.saveAndFlush(ratingCategories);

        // Get all the ratingCategoriesList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultRatingCategoriesShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the ratingCategoriesList where endDate equals to UPDATED_END_DATE
        defaultRatingCategoriesShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllRatingCategoriesByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        ratingCategoriesRepository.saveAndFlush(ratingCategories);

        // Get all the ratingCategoriesList where endDate is not null
        defaultRatingCategoriesShouldBeFound("endDate.specified=true");

        // Get all the ratingCategoriesList where endDate is null
        defaultRatingCategoriesShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllRatingCategoriesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        ratingCategoriesRepository.saveAndFlush(ratingCategories);

        // Get all the ratingCategoriesList where version equals to DEFAULT_VERSION
        defaultRatingCategoriesShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the ratingCategoriesList where version equals to UPDATED_VERSION
        defaultRatingCategoriesShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllRatingCategoriesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        ratingCategoriesRepository.saveAndFlush(ratingCategories);

        // Get all the ratingCategoriesList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultRatingCategoriesShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the ratingCategoriesList where version equals to UPDATED_VERSION
        defaultRatingCategoriesShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllRatingCategoriesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        ratingCategoriesRepository.saveAndFlush(ratingCategories);

        // Get all the ratingCategoriesList where version is not null
        defaultRatingCategoriesShouldBeFound("version.specified=true");

        // Get all the ratingCategoriesList where version is null
        defaultRatingCategoriesShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllRatingCategoriesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ratingCategoriesRepository.saveAndFlush(ratingCategories);

        // Get all the ratingCategoriesList where version is greater than or equal to DEFAULT_VERSION
        defaultRatingCategoriesShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the ratingCategoriesList where version is greater than or equal to UPDATED_VERSION
        defaultRatingCategoriesShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllRatingCategoriesByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ratingCategoriesRepository.saveAndFlush(ratingCategories);

        // Get all the ratingCategoriesList where version is less than or equal to DEFAULT_VERSION
        defaultRatingCategoriesShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the ratingCategoriesList where version is less than or equal to SMALLER_VERSION
        defaultRatingCategoriesShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllRatingCategoriesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        ratingCategoriesRepository.saveAndFlush(ratingCategories);

        // Get all the ratingCategoriesList where version is less than DEFAULT_VERSION
        defaultRatingCategoriesShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the ratingCategoriesList where version is less than UPDATED_VERSION
        defaultRatingCategoriesShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllRatingCategoriesByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ratingCategoriesRepository.saveAndFlush(ratingCategories);

        // Get all the ratingCategoriesList where version is greater than DEFAULT_VERSION
        defaultRatingCategoriesShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the ratingCategoriesList where version is greater than SMALLER_VERSION
        defaultRatingCategoriesShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllRatingCategoriesByPcratingattributescategoriesCategoryIsEqualToSomething() throws Exception {
        PcRatingAttributesCategories pcratingattributescategoriesCategory;
        if (TestUtil.findAll(em, PcRatingAttributesCategories.class).isEmpty()) {
            ratingCategoriesRepository.saveAndFlush(ratingCategories);
            pcratingattributescategoriesCategory = PcRatingAttributesCategoriesResourceIT.createEntity(em);
        } else {
            pcratingattributescategoriesCategory = TestUtil.findAll(em, PcRatingAttributesCategories.class).get(0);
        }
        em.persist(pcratingattributescategoriesCategory);
        em.flush();
        ratingCategories.addPcratingattributescategoriesCategory(pcratingattributescategoriesCategory);
        ratingCategoriesRepository.saveAndFlush(ratingCategories);
        Long pcratingattributescategoriesCategoryId = pcratingattributescategoriesCategory.getId();

        // Get all the ratingCategoriesList where pcratingattributescategoriesCategory equals to pcratingattributescategoriesCategoryId
        defaultRatingCategoriesShouldBeFound("pcratingattributescategoriesCategoryId.equals=" + pcratingattributescategoriesCategoryId);

        // Get all the ratingCategoriesList where pcratingattributescategoriesCategory equals to (pcratingattributescategoriesCategoryId + 1)
        defaultRatingCategoriesShouldNotBeFound(
            "pcratingattributescategoriesCategoryId.equals=" + (pcratingattributescategoriesCategoryId + 1)
        );
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRatingCategoriesShouldBeFound(String filter) throws Exception {
        restRatingCategoriesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ratingCategories.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restRatingCategoriesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRatingCategoriesShouldNotBeFound(String filter) throws Exception {
        restRatingCategoriesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRatingCategoriesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRatingCategories() throws Exception {
        // Get the ratingCategories
        restRatingCategoriesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRatingCategories() throws Exception {
        // Initialize the database
        ratingCategoriesRepository.saveAndFlush(ratingCategories);

        int databaseSizeBeforeUpdate = ratingCategoriesRepository.findAll().size();

        // Update the ratingCategories
        RatingCategories updatedRatingCategories = ratingCategoriesRepository.findById(ratingCategories.getId()).get();
        // Disconnect from session so that the updates on updatedRatingCategories are not directly saved in db
        em.detach(updatedRatingCategories);
        updatedRatingCategories
            .name(UPDATED_NAME)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restRatingCategoriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRatingCategories.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRatingCategories))
            )
            .andExpect(status().isOk());

        // Validate the RatingCategories in the database
        List<RatingCategories> ratingCategoriesList = ratingCategoriesRepository.findAll();
        assertThat(ratingCategoriesList).hasSize(databaseSizeBeforeUpdate);
        RatingCategories testRatingCategories = ratingCategoriesList.get(ratingCategoriesList.size() - 1);
        assertThat(testRatingCategories.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRatingCategories.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testRatingCategories.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testRatingCategories.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testRatingCategories.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testRatingCategories.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingRatingCategories() throws Exception {
        int databaseSizeBeforeUpdate = ratingCategoriesRepository.findAll().size();
        ratingCategories.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRatingCategoriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ratingCategories.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingCategories))
            )
            .andExpect(status().isBadRequest());

        // Validate the RatingCategories in the database
        List<RatingCategories> ratingCategoriesList = ratingCategoriesRepository.findAll();
        assertThat(ratingCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRatingCategories() throws Exception {
        int databaseSizeBeforeUpdate = ratingCategoriesRepository.findAll().size();
        ratingCategories.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatingCategoriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingCategories))
            )
            .andExpect(status().isBadRequest());

        // Validate the RatingCategories in the database
        List<RatingCategories> ratingCategoriesList = ratingCategoriesRepository.findAll();
        assertThat(ratingCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRatingCategories() throws Exception {
        int databaseSizeBeforeUpdate = ratingCategoriesRepository.findAll().size();
        ratingCategories.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatingCategoriesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingCategories))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RatingCategories in the database
        List<RatingCategories> ratingCategoriesList = ratingCategoriesRepository.findAll();
        assertThat(ratingCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRatingCategoriesWithPatch() throws Exception {
        // Initialize the database
        ratingCategoriesRepository.saveAndFlush(ratingCategories);

        int databaseSizeBeforeUpdate = ratingCategoriesRepository.findAll().size();

        // Update the ratingCategories using partial update
        RatingCategories partialUpdatedRatingCategories = new RatingCategories();
        partialUpdatedRatingCategories.setId(ratingCategories.getId());

        partialUpdatedRatingCategories.createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT).version(UPDATED_VERSION);

        restRatingCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRatingCategories.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRatingCategories))
            )
            .andExpect(status().isOk());

        // Validate the RatingCategories in the database
        List<RatingCategories> ratingCategoriesList = ratingCategoriesRepository.findAll();
        assertThat(ratingCategoriesList).hasSize(databaseSizeBeforeUpdate);
        RatingCategories testRatingCategories = ratingCategoriesList.get(ratingCategoriesList.size() - 1);
        assertThat(testRatingCategories.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRatingCategories.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testRatingCategories.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testRatingCategories.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testRatingCategories.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testRatingCategories.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateRatingCategoriesWithPatch() throws Exception {
        // Initialize the database
        ratingCategoriesRepository.saveAndFlush(ratingCategories);

        int databaseSizeBeforeUpdate = ratingCategoriesRepository.findAll().size();

        // Update the ratingCategories using partial update
        RatingCategories partialUpdatedRatingCategories = new RatingCategories();
        partialUpdatedRatingCategories.setId(ratingCategories.getId());

        partialUpdatedRatingCategories
            .name(UPDATED_NAME)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restRatingCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRatingCategories.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRatingCategories))
            )
            .andExpect(status().isOk());

        // Validate the RatingCategories in the database
        List<RatingCategories> ratingCategoriesList = ratingCategoriesRepository.findAll();
        assertThat(ratingCategoriesList).hasSize(databaseSizeBeforeUpdate);
        RatingCategories testRatingCategories = ratingCategoriesList.get(ratingCategoriesList.size() - 1);
        assertThat(testRatingCategories.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRatingCategories.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testRatingCategories.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testRatingCategories.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testRatingCategories.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testRatingCategories.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingRatingCategories() throws Exception {
        int databaseSizeBeforeUpdate = ratingCategoriesRepository.findAll().size();
        ratingCategories.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRatingCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ratingCategories.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ratingCategories))
            )
            .andExpect(status().isBadRequest());

        // Validate the RatingCategories in the database
        List<RatingCategories> ratingCategoriesList = ratingCategoriesRepository.findAll();
        assertThat(ratingCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRatingCategories() throws Exception {
        int databaseSizeBeforeUpdate = ratingCategoriesRepository.findAll().size();
        ratingCategories.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatingCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ratingCategories))
            )
            .andExpect(status().isBadRequest());

        // Validate the RatingCategories in the database
        List<RatingCategories> ratingCategoriesList = ratingCategoriesRepository.findAll();
        assertThat(ratingCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRatingCategories() throws Exception {
        int databaseSizeBeforeUpdate = ratingCategoriesRepository.findAll().size();
        ratingCategories.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatingCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ratingCategories))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RatingCategories in the database
        List<RatingCategories> ratingCategoriesList = ratingCategoriesRepository.findAll();
        assertThat(ratingCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRatingCategories() throws Exception {
        // Initialize the database
        ratingCategoriesRepository.saveAndFlush(ratingCategories);

        int databaseSizeBeforeDelete = ratingCategoriesRepository.findAll().size();

        // Delete the ratingCategories
        restRatingCategoriesMockMvc
            .perform(delete(ENTITY_API_URL_ID, ratingCategories.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RatingCategories> ratingCategoriesList = ratingCategoriesRepository.findAll();
        assertThat(ratingCategoriesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
