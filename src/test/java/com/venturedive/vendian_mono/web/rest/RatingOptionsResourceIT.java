package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.PcRaterAttributes;
import com.venturedive.vendian_mono.domain.RatingOptions;
import com.venturedive.vendian_mono.repository.RatingOptionsRepository;
import com.venturedive.vendian_mono.service.criteria.RatingOptionsCriteria;
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
 * Integration tests for the {@link RatingOptionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RatingOptionsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Float DEFAULT_FROM = 1F;
    private static final Float UPDATED_FROM = 2F;
    private static final Float SMALLER_FROM = 1F - 1F;

    private static final Float DEFAULT_TO = 1F;
    private static final Float UPDATED_TO = 2F;
    private static final Float SMALLER_TO = 1F - 1F;

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

    private static final String ENTITY_API_URL = "/api/rating-options";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RatingOptionsRepository ratingOptionsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRatingOptionsMockMvc;

    private RatingOptions ratingOptions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RatingOptions createEntity(EntityManager em) {
        RatingOptions ratingOptions = new RatingOptions()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .from(DEFAULT_FROM)
            .to(DEFAULT_TO)
            .effDate(DEFAULT_EFF_DATE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .endDate(DEFAULT_END_DATE)
            .version(DEFAULT_VERSION);
        return ratingOptions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RatingOptions createUpdatedEntity(EntityManager em) {
        RatingOptions ratingOptions = new RatingOptions()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .from(UPDATED_FROM)
            .to(UPDATED_TO)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);
        return ratingOptions;
    }

    @BeforeEach
    public void initTest() {
        ratingOptions = createEntity(em);
    }

    @Test
    @Transactional
    void createRatingOptions() throws Exception {
        int databaseSizeBeforeCreate = ratingOptionsRepository.findAll().size();
        // Create the RatingOptions
        restRatingOptionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingOptions))
            )
            .andExpect(status().isCreated());

        // Validate the RatingOptions in the database
        List<RatingOptions> ratingOptionsList = ratingOptionsRepository.findAll();
        assertThat(ratingOptionsList).hasSize(databaseSizeBeforeCreate + 1);
        RatingOptions testRatingOptions = ratingOptionsList.get(ratingOptionsList.size() - 1);
        assertThat(testRatingOptions.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRatingOptions.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRatingOptions.getFrom()).isEqualTo(DEFAULT_FROM);
        assertThat(testRatingOptions.getTo()).isEqualTo(DEFAULT_TO);
        assertThat(testRatingOptions.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testRatingOptions.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testRatingOptions.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testRatingOptions.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testRatingOptions.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createRatingOptionsWithExistingId() throws Exception {
        // Create the RatingOptions with an existing ID
        ratingOptions.setId(1L);

        int databaseSizeBeforeCreate = ratingOptionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRatingOptionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingOptions))
            )
            .andExpect(status().isBadRequest());

        // Validate the RatingOptions in the database
        List<RatingOptions> ratingOptionsList = ratingOptionsRepository.findAll();
        assertThat(ratingOptionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ratingOptionsRepository.findAll().size();
        // set the field null
        ratingOptions.setName(null);

        // Create the RatingOptions, which fails.

        restRatingOptionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingOptions))
            )
            .andExpect(status().isBadRequest());

        List<RatingOptions> ratingOptionsList = ratingOptionsRepository.findAll();
        assertThat(ratingOptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = ratingOptionsRepository.findAll().size();
        // set the field null
        ratingOptions.setFrom(null);

        // Create the RatingOptions, which fails.

        restRatingOptionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingOptions))
            )
            .andExpect(status().isBadRequest());

        List<RatingOptions> ratingOptionsList = ratingOptionsRepository.findAll();
        assertThat(ratingOptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEffDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = ratingOptionsRepository.findAll().size();
        // set the field null
        ratingOptions.setEffDate(null);

        // Create the RatingOptions, which fails.

        restRatingOptionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingOptions))
            )
            .andExpect(status().isBadRequest());

        List<RatingOptions> ratingOptionsList = ratingOptionsRepository.findAll();
        assertThat(ratingOptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = ratingOptionsRepository.findAll().size();
        // set the field null
        ratingOptions.setCreatedAt(null);

        // Create the RatingOptions, which fails.

        restRatingOptionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingOptions))
            )
            .andExpect(status().isBadRequest());

        List<RatingOptions> ratingOptionsList = ratingOptionsRepository.findAll();
        assertThat(ratingOptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = ratingOptionsRepository.findAll().size();
        // set the field null
        ratingOptions.setUpdatedAt(null);

        // Create the RatingOptions, which fails.

        restRatingOptionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingOptions))
            )
            .andExpect(status().isBadRequest());

        List<RatingOptions> ratingOptionsList = ratingOptionsRepository.findAll();
        assertThat(ratingOptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = ratingOptionsRepository.findAll().size();
        // set the field null
        ratingOptions.setVersion(null);

        // Create the RatingOptions, which fails.

        restRatingOptionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingOptions))
            )
            .andExpect(status().isBadRequest());

        List<RatingOptions> ratingOptionsList = ratingOptionsRepository.findAll();
        assertThat(ratingOptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRatingOptions() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList
        restRatingOptionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ratingOptions.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].from").value(hasItem(DEFAULT_FROM.doubleValue())))
            .andExpect(jsonPath("$.[*].to").value(hasItem(DEFAULT_TO.doubleValue())))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getRatingOptions() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get the ratingOptions
        restRatingOptionsMockMvc
            .perform(get(ENTITY_API_URL_ID, ratingOptions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ratingOptions.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.from").value(DEFAULT_FROM.doubleValue()))
            .andExpect(jsonPath("$.to").value(DEFAULT_TO.doubleValue()))
            .andExpect(jsonPath("$.effDate").value(DEFAULT_EFF_DATE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getRatingOptionsByIdFiltering() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        Long id = ratingOptions.getId();

        defaultRatingOptionsShouldBeFound("id.equals=" + id);
        defaultRatingOptionsShouldNotBeFound("id.notEquals=" + id);

        defaultRatingOptionsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRatingOptionsShouldNotBeFound("id.greaterThan=" + id);

        defaultRatingOptionsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRatingOptionsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRatingOptionsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where name equals to DEFAULT_NAME
        defaultRatingOptionsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the ratingOptionsList where name equals to UPDATED_NAME
        defaultRatingOptionsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRatingOptionsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultRatingOptionsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the ratingOptionsList where name equals to UPDATED_NAME
        defaultRatingOptionsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRatingOptionsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where name is not null
        defaultRatingOptionsShouldBeFound("name.specified=true");

        // Get all the ratingOptionsList where name is null
        defaultRatingOptionsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllRatingOptionsByNameContainsSomething() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where name contains DEFAULT_NAME
        defaultRatingOptionsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the ratingOptionsList where name contains UPDATED_NAME
        defaultRatingOptionsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRatingOptionsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where name does not contain DEFAULT_NAME
        defaultRatingOptionsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the ratingOptionsList where name does not contain UPDATED_NAME
        defaultRatingOptionsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRatingOptionsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where description equals to DEFAULT_DESCRIPTION
        defaultRatingOptionsShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the ratingOptionsList where description equals to UPDATED_DESCRIPTION
        defaultRatingOptionsShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllRatingOptionsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultRatingOptionsShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the ratingOptionsList where description equals to UPDATED_DESCRIPTION
        defaultRatingOptionsShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllRatingOptionsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where description is not null
        defaultRatingOptionsShouldBeFound("description.specified=true");

        // Get all the ratingOptionsList where description is null
        defaultRatingOptionsShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllRatingOptionsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where description contains DEFAULT_DESCRIPTION
        defaultRatingOptionsShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the ratingOptionsList where description contains UPDATED_DESCRIPTION
        defaultRatingOptionsShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllRatingOptionsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where description does not contain DEFAULT_DESCRIPTION
        defaultRatingOptionsShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the ratingOptionsList where description does not contain UPDATED_DESCRIPTION
        defaultRatingOptionsShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllRatingOptionsByFromIsEqualToSomething() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where from equals to DEFAULT_FROM
        defaultRatingOptionsShouldBeFound("from.equals=" + DEFAULT_FROM);

        // Get all the ratingOptionsList where from equals to UPDATED_FROM
        defaultRatingOptionsShouldNotBeFound("from.equals=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    void getAllRatingOptionsByFromIsInShouldWork() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where from in DEFAULT_FROM or UPDATED_FROM
        defaultRatingOptionsShouldBeFound("from.in=" + DEFAULT_FROM + "," + UPDATED_FROM);

        // Get all the ratingOptionsList where from equals to UPDATED_FROM
        defaultRatingOptionsShouldNotBeFound("from.in=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    void getAllRatingOptionsByFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where from is not null
        defaultRatingOptionsShouldBeFound("from.specified=true");

        // Get all the ratingOptionsList where from is null
        defaultRatingOptionsShouldNotBeFound("from.specified=false");
    }

    @Test
    @Transactional
    void getAllRatingOptionsByFromIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where from is greater than or equal to DEFAULT_FROM
        defaultRatingOptionsShouldBeFound("from.greaterThanOrEqual=" + DEFAULT_FROM);

        // Get all the ratingOptionsList where from is greater than or equal to UPDATED_FROM
        defaultRatingOptionsShouldNotBeFound("from.greaterThanOrEqual=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    void getAllRatingOptionsByFromIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where from is less than or equal to DEFAULT_FROM
        defaultRatingOptionsShouldBeFound("from.lessThanOrEqual=" + DEFAULT_FROM);

        // Get all the ratingOptionsList where from is less than or equal to SMALLER_FROM
        defaultRatingOptionsShouldNotBeFound("from.lessThanOrEqual=" + SMALLER_FROM);
    }

    @Test
    @Transactional
    void getAllRatingOptionsByFromIsLessThanSomething() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where from is less than DEFAULT_FROM
        defaultRatingOptionsShouldNotBeFound("from.lessThan=" + DEFAULT_FROM);

        // Get all the ratingOptionsList where from is less than UPDATED_FROM
        defaultRatingOptionsShouldBeFound("from.lessThan=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    void getAllRatingOptionsByFromIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where from is greater than DEFAULT_FROM
        defaultRatingOptionsShouldNotBeFound("from.greaterThan=" + DEFAULT_FROM);

        // Get all the ratingOptionsList where from is greater than SMALLER_FROM
        defaultRatingOptionsShouldBeFound("from.greaterThan=" + SMALLER_FROM);
    }

    @Test
    @Transactional
    void getAllRatingOptionsByToIsEqualToSomething() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where to equals to DEFAULT_TO
        defaultRatingOptionsShouldBeFound("to.equals=" + DEFAULT_TO);

        // Get all the ratingOptionsList where to equals to UPDATED_TO
        defaultRatingOptionsShouldNotBeFound("to.equals=" + UPDATED_TO);
    }

    @Test
    @Transactional
    void getAllRatingOptionsByToIsInShouldWork() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where to in DEFAULT_TO or UPDATED_TO
        defaultRatingOptionsShouldBeFound("to.in=" + DEFAULT_TO + "," + UPDATED_TO);

        // Get all the ratingOptionsList where to equals to UPDATED_TO
        defaultRatingOptionsShouldNotBeFound("to.in=" + UPDATED_TO);
    }

    @Test
    @Transactional
    void getAllRatingOptionsByToIsNullOrNotNull() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where to is not null
        defaultRatingOptionsShouldBeFound("to.specified=true");

        // Get all the ratingOptionsList where to is null
        defaultRatingOptionsShouldNotBeFound("to.specified=false");
    }

    @Test
    @Transactional
    void getAllRatingOptionsByToIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where to is greater than or equal to DEFAULT_TO
        defaultRatingOptionsShouldBeFound("to.greaterThanOrEqual=" + DEFAULT_TO);

        // Get all the ratingOptionsList where to is greater than or equal to UPDATED_TO
        defaultRatingOptionsShouldNotBeFound("to.greaterThanOrEqual=" + UPDATED_TO);
    }

    @Test
    @Transactional
    void getAllRatingOptionsByToIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where to is less than or equal to DEFAULT_TO
        defaultRatingOptionsShouldBeFound("to.lessThanOrEqual=" + DEFAULT_TO);

        // Get all the ratingOptionsList where to is less than or equal to SMALLER_TO
        defaultRatingOptionsShouldNotBeFound("to.lessThanOrEqual=" + SMALLER_TO);
    }

    @Test
    @Transactional
    void getAllRatingOptionsByToIsLessThanSomething() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where to is less than DEFAULT_TO
        defaultRatingOptionsShouldNotBeFound("to.lessThan=" + DEFAULT_TO);

        // Get all the ratingOptionsList where to is less than UPDATED_TO
        defaultRatingOptionsShouldBeFound("to.lessThan=" + UPDATED_TO);
    }

    @Test
    @Transactional
    void getAllRatingOptionsByToIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where to is greater than DEFAULT_TO
        defaultRatingOptionsShouldNotBeFound("to.greaterThan=" + DEFAULT_TO);

        // Get all the ratingOptionsList where to is greater than SMALLER_TO
        defaultRatingOptionsShouldBeFound("to.greaterThan=" + SMALLER_TO);
    }

    @Test
    @Transactional
    void getAllRatingOptionsByEffDateIsEqualToSomething() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where effDate equals to DEFAULT_EFF_DATE
        defaultRatingOptionsShouldBeFound("effDate.equals=" + DEFAULT_EFF_DATE);

        // Get all the ratingOptionsList where effDate equals to UPDATED_EFF_DATE
        defaultRatingOptionsShouldNotBeFound("effDate.equals=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllRatingOptionsByEffDateIsInShouldWork() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where effDate in DEFAULT_EFF_DATE or UPDATED_EFF_DATE
        defaultRatingOptionsShouldBeFound("effDate.in=" + DEFAULT_EFF_DATE + "," + UPDATED_EFF_DATE);

        // Get all the ratingOptionsList where effDate equals to UPDATED_EFF_DATE
        defaultRatingOptionsShouldNotBeFound("effDate.in=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllRatingOptionsByEffDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where effDate is not null
        defaultRatingOptionsShouldBeFound("effDate.specified=true");

        // Get all the ratingOptionsList where effDate is null
        defaultRatingOptionsShouldNotBeFound("effDate.specified=false");
    }

    @Test
    @Transactional
    void getAllRatingOptionsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where createdAt equals to DEFAULT_CREATED_AT
        defaultRatingOptionsShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the ratingOptionsList where createdAt equals to UPDATED_CREATED_AT
        defaultRatingOptionsShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllRatingOptionsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultRatingOptionsShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the ratingOptionsList where createdAt equals to UPDATED_CREATED_AT
        defaultRatingOptionsShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllRatingOptionsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where createdAt is not null
        defaultRatingOptionsShouldBeFound("createdAt.specified=true");

        // Get all the ratingOptionsList where createdAt is null
        defaultRatingOptionsShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllRatingOptionsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultRatingOptionsShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the ratingOptionsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultRatingOptionsShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllRatingOptionsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultRatingOptionsShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the ratingOptionsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultRatingOptionsShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllRatingOptionsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where updatedAt is not null
        defaultRatingOptionsShouldBeFound("updatedAt.specified=true");

        // Get all the ratingOptionsList where updatedAt is null
        defaultRatingOptionsShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllRatingOptionsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where endDate equals to DEFAULT_END_DATE
        defaultRatingOptionsShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the ratingOptionsList where endDate equals to UPDATED_END_DATE
        defaultRatingOptionsShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllRatingOptionsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultRatingOptionsShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the ratingOptionsList where endDate equals to UPDATED_END_DATE
        defaultRatingOptionsShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllRatingOptionsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where endDate is not null
        defaultRatingOptionsShouldBeFound("endDate.specified=true");

        // Get all the ratingOptionsList where endDate is null
        defaultRatingOptionsShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllRatingOptionsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where version equals to DEFAULT_VERSION
        defaultRatingOptionsShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the ratingOptionsList where version equals to UPDATED_VERSION
        defaultRatingOptionsShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllRatingOptionsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultRatingOptionsShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the ratingOptionsList where version equals to UPDATED_VERSION
        defaultRatingOptionsShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllRatingOptionsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where version is not null
        defaultRatingOptionsShouldBeFound("version.specified=true");

        // Get all the ratingOptionsList where version is null
        defaultRatingOptionsShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllRatingOptionsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where version is greater than or equal to DEFAULT_VERSION
        defaultRatingOptionsShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the ratingOptionsList where version is greater than or equal to UPDATED_VERSION
        defaultRatingOptionsShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllRatingOptionsByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where version is less than or equal to DEFAULT_VERSION
        defaultRatingOptionsShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the ratingOptionsList where version is less than or equal to SMALLER_VERSION
        defaultRatingOptionsShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllRatingOptionsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where version is less than DEFAULT_VERSION
        defaultRatingOptionsShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the ratingOptionsList where version is less than UPDATED_VERSION
        defaultRatingOptionsShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllRatingOptionsByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        // Get all the ratingOptionsList where version is greater than DEFAULT_VERSION
        defaultRatingOptionsShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the ratingOptionsList where version is greater than SMALLER_VERSION
        defaultRatingOptionsShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllRatingOptionsByPcraterattributesRatingoptionIsEqualToSomething() throws Exception {
        PcRaterAttributes pcraterattributesRatingoption;
        if (TestUtil.findAll(em, PcRaterAttributes.class).isEmpty()) {
            ratingOptionsRepository.saveAndFlush(ratingOptions);
            pcraterattributesRatingoption = PcRaterAttributesResourceIT.createEntity(em);
        } else {
            pcraterattributesRatingoption = TestUtil.findAll(em, PcRaterAttributes.class).get(0);
        }
        em.persist(pcraterattributesRatingoption);
        em.flush();
        ratingOptions.addPcraterattributesRatingoption(pcraterattributesRatingoption);
        ratingOptionsRepository.saveAndFlush(ratingOptions);
        Long pcraterattributesRatingoptionId = pcraterattributesRatingoption.getId();

        // Get all the ratingOptionsList where pcraterattributesRatingoption equals to pcraterattributesRatingoptionId
        defaultRatingOptionsShouldBeFound("pcraterattributesRatingoptionId.equals=" + pcraterattributesRatingoptionId);

        // Get all the ratingOptionsList where pcraterattributesRatingoption equals to (pcraterattributesRatingoptionId + 1)
        defaultRatingOptionsShouldNotBeFound("pcraterattributesRatingoptionId.equals=" + (pcraterattributesRatingoptionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRatingOptionsShouldBeFound(String filter) throws Exception {
        restRatingOptionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ratingOptions.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].from").value(hasItem(DEFAULT_FROM.doubleValue())))
            .andExpect(jsonPath("$.[*].to").value(hasItem(DEFAULT_TO.doubleValue())))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restRatingOptionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRatingOptionsShouldNotBeFound(String filter) throws Exception {
        restRatingOptionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRatingOptionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRatingOptions() throws Exception {
        // Get the ratingOptions
        restRatingOptionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRatingOptions() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        int databaseSizeBeforeUpdate = ratingOptionsRepository.findAll().size();

        // Update the ratingOptions
        RatingOptions updatedRatingOptions = ratingOptionsRepository.findById(ratingOptions.getId()).get();
        // Disconnect from session so that the updates on updatedRatingOptions are not directly saved in db
        em.detach(updatedRatingOptions);
        updatedRatingOptions
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .from(UPDATED_FROM)
            .to(UPDATED_TO)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restRatingOptionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRatingOptions.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRatingOptions))
            )
            .andExpect(status().isOk());

        // Validate the RatingOptions in the database
        List<RatingOptions> ratingOptionsList = ratingOptionsRepository.findAll();
        assertThat(ratingOptionsList).hasSize(databaseSizeBeforeUpdate);
        RatingOptions testRatingOptions = ratingOptionsList.get(ratingOptionsList.size() - 1);
        assertThat(testRatingOptions.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRatingOptions.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRatingOptions.getFrom()).isEqualTo(UPDATED_FROM);
        assertThat(testRatingOptions.getTo()).isEqualTo(UPDATED_TO);
        assertThat(testRatingOptions.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testRatingOptions.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testRatingOptions.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testRatingOptions.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testRatingOptions.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingRatingOptions() throws Exception {
        int databaseSizeBeforeUpdate = ratingOptionsRepository.findAll().size();
        ratingOptions.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRatingOptionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ratingOptions.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingOptions))
            )
            .andExpect(status().isBadRequest());

        // Validate the RatingOptions in the database
        List<RatingOptions> ratingOptionsList = ratingOptionsRepository.findAll();
        assertThat(ratingOptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRatingOptions() throws Exception {
        int databaseSizeBeforeUpdate = ratingOptionsRepository.findAll().size();
        ratingOptions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatingOptionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingOptions))
            )
            .andExpect(status().isBadRequest());

        // Validate the RatingOptions in the database
        List<RatingOptions> ratingOptionsList = ratingOptionsRepository.findAll();
        assertThat(ratingOptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRatingOptions() throws Exception {
        int databaseSizeBeforeUpdate = ratingOptionsRepository.findAll().size();
        ratingOptions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatingOptionsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingOptions))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RatingOptions in the database
        List<RatingOptions> ratingOptionsList = ratingOptionsRepository.findAll();
        assertThat(ratingOptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRatingOptionsWithPatch() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        int databaseSizeBeforeUpdate = ratingOptionsRepository.findAll().size();

        // Update the ratingOptions using partial update
        RatingOptions partialUpdatedRatingOptions = new RatingOptions();
        partialUpdatedRatingOptions.setId(ratingOptions.getId());

        partialUpdatedRatingOptions.name(UPDATED_NAME).from(UPDATED_FROM).endDate(UPDATED_END_DATE).version(UPDATED_VERSION);

        restRatingOptionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRatingOptions.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRatingOptions))
            )
            .andExpect(status().isOk());

        // Validate the RatingOptions in the database
        List<RatingOptions> ratingOptionsList = ratingOptionsRepository.findAll();
        assertThat(ratingOptionsList).hasSize(databaseSizeBeforeUpdate);
        RatingOptions testRatingOptions = ratingOptionsList.get(ratingOptionsList.size() - 1);
        assertThat(testRatingOptions.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRatingOptions.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRatingOptions.getFrom()).isEqualTo(UPDATED_FROM);
        assertThat(testRatingOptions.getTo()).isEqualTo(DEFAULT_TO);
        assertThat(testRatingOptions.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testRatingOptions.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testRatingOptions.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testRatingOptions.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testRatingOptions.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateRatingOptionsWithPatch() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        int databaseSizeBeforeUpdate = ratingOptionsRepository.findAll().size();

        // Update the ratingOptions using partial update
        RatingOptions partialUpdatedRatingOptions = new RatingOptions();
        partialUpdatedRatingOptions.setId(ratingOptions.getId());

        partialUpdatedRatingOptions
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .from(UPDATED_FROM)
            .to(UPDATED_TO)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restRatingOptionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRatingOptions.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRatingOptions))
            )
            .andExpect(status().isOk());

        // Validate the RatingOptions in the database
        List<RatingOptions> ratingOptionsList = ratingOptionsRepository.findAll();
        assertThat(ratingOptionsList).hasSize(databaseSizeBeforeUpdate);
        RatingOptions testRatingOptions = ratingOptionsList.get(ratingOptionsList.size() - 1);
        assertThat(testRatingOptions.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRatingOptions.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRatingOptions.getFrom()).isEqualTo(UPDATED_FROM);
        assertThat(testRatingOptions.getTo()).isEqualTo(UPDATED_TO);
        assertThat(testRatingOptions.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testRatingOptions.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testRatingOptions.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testRatingOptions.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testRatingOptions.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingRatingOptions() throws Exception {
        int databaseSizeBeforeUpdate = ratingOptionsRepository.findAll().size();
        ratingOptions.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRatingOptionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ratingOptions.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ratingOptions))
            )
            .andExpect(status().isBadRequest());

        // Validate the RatingOptions in the database
        List<RatingOptions> ratingOptionsList = ratingOptionsRepository.findAll();
        assertThat(ratingOptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRatingOptions() throws Exception {
        int databaseSizeBeforeUpdate = ratingOptionsRepository.findAll().size();
        ratingOptions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatingOptionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ratingOptions))
            )
            .andExpect(status().isBadRequest());

        // Validate the RatingOptions in the database
        List<RatingOptions> ratingOptionsList = ratingOptionsRepository.findAll();
        assertThat(ratingOptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRatingOptions() throws Exception {
        int databaseSizeBeforeUpdate = ratingOptionsRepository.findAll().size();
        ratingOptions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatingOptionsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ratingOptions))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RatingOptions in the database
        List<RatingOptions> ratingOptionsList = ratingOptionsRepository.findAll();
        assertThat(ratingOptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRatingOptions() throws Exception {
        // Initialize the database
        ratingOptionsRepository.saveAndFlush(ratingOptions);

        int databaseSizeBeforeDelete = ratingOptionsRepository.findAll().size();

        // Delete the ratingOptions
        restRatingOptionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, ratingOptions.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RatingOptions> ratingOptionsList = ratingOptionsRepository.findAll();
        assertThat(ratingOptionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
