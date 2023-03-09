package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.PcRatingAttributes;
import com.venturedive.vendian_mono.domain.PcRatingAttributesCategories;
import com.venturedive.vendian_mono.domain.RatingCategories;
import com.venturedive.vendian_mono.repository.PcRatingAttributesCategoriesRepository;
import com.venturedive.vendian_mono.service.criteria.PcRatingAttributesCategoriesCriteria;
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
 * Integration tests for the {@link PcRatingAttributesCategoriesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PcRatingAttributesCategoriesResourceIT {

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

    private static final String ENTITY_API_URL = "/api/pc-rating-attributes-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PcRatingAttributesCategoriesRepository pcRatingAttributesCategoriesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPcRatingAttributesCategoriesMockMvc;

    private PcRatingAttributesCategories pcRatingAttributesCategories;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PcRatingAttributesCategories createEntity(EntityManager em) {
        PcRatingAttributesCategories pcRatingAttributesCategories = new PcRatingAttributesCategories()
            .effDate(DEFAULT_EFF_DATE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .endDate(DEFAULT_END_DATE)
            .version(DEFAULT_VERSION);
        // Add required entity
        RatingCategories ratingCategories;
        if (TestUtil.findAll(em, RatingCategories.class).isEmpty()) {
            ratingCategories = RatingCategoriesResourceIT.createEntity(em);
            em.persist(ratingCategories);
            em.flush();
        } else {
            ratingCategories = TestUtil.findAll(em, RatingCategories.class).get(0);
        }
        pcRatingAttributesCategories.setCategory(ratingCategories);
        // Add required entity
        PcRatingAttributes pcRatingAttributes;
        if (TestUtil.findAll(em, PcRatingAttributes.class).isEmpty()) {
            pcRatingAttributes = PcRatingAttributesResourceIT.createEntity(em);
            em.persist(pcRatingAttributes);
            em.flush();
        } else {
            pcRatingAttributes = TestUtil.findAll(em, PcRatingAttributes.class).get(0);
        }
        pcRatingAttributesCategories.setRatingAttribute(pcRatingAttributes);
        return pcRatingAttributesCategories;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PcRatingAttributesCategories createUpdatedEntity(EntityManager em) {
        PcRatingAttributesCategories pcRatingAttributesCategories = new PcRatingAttributesCategories()
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);
        // Add required entity
        RatingCategories ratingCategories;
        if (TestUtil.findAll(em, RatingCategories.class).isEmpty()) {
            ratingCategories = RatingCategoriesResourceIT.createUpdatedEntity(em);
            em.persist(ratingCategories);
            em.flush();
        } else {
            ratingCategories = TestUtil.findAll(em, RatingCategories.class).get(0);
        }
        pcRatingAttributesCategories.setCategory(ratingCategories);
        // Add required entity
        PcRatingAttributes pcRatingAttributes;
        if (TestUtil.findAll(em, PcRatingAttributes.class).isEmpty()) {
            pcRatingAttributes = PcRatingAttributesResourceIT.createUpdatedEntity(em);
            em.persist(pcRatingAttributes);
            em.flush();
        } else {
            pcRatingAttributes = TestUtil.findAll(em, PcRatingAttributes.class).get(0);
        }
        pcRatingAttributesCategories.setRatingAttribute(pcRatingAttributes);
        return pcRatingAttributesCategories;
    }

    @BeforeEach
    public void initTest() {
        pcRatingAttributesCategories = createEntity(em);
    }

    @Test
    @Transactional
    void createPcRatingAttributesCategories() throws Exception {
        int databaseSizeBeforeCreate = pcRatingAttributesCategoriesRepository.findAll().size();
        // Create the PcRatingAttributesCategories
        restPcRatingAttributesCategoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRatingAttributesCategories))
            )
            .andExpect(status().isCreated());

        // Validate the PcRatingAttributesCategories in the database
        List<PcRatingAttributesCategories> pcRatingAttributesCategoriesList = pcRatingAttributesCategoriesRepository.findAll();
        assertThat(pcRatingAttributesCategoriesList).hasSize(databaseSizeBeforeCreate + 1);
        PcRatingAttributesCategories testPcRatingAttributesCategories = pcRatingAttributesCategoriesList.get(
            pcRatingAttributesCategoriesList.size() - 1
        );
        assertThat(testPcRatingAttributesCategories.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testPcRatingAttributesCategories.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testPcRatingAttributesCategories.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testPcRatingAttributesCategories.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testPcRatingAttributesCategories.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createPcRatingAttributesCategoriesWithExistingId() throws Exception {
        // Create the PcRatingAttributesCategories with an existing ID
        pcRatingAttributesCategories.setId(1L);

        int databaseSizeBeforeCreate = pcRatingAttributesCategoriesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPcRatingAttributesCategoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRatingAttributesCategories))
            )
            .andExpect(status().isBadRequest());

        // Validate the PcRatingAttributesCategories in the database
        List<PcRatingAttributesCategories> pcRatingAttributesCategoriesList = pcRatingAttributesCategoriesRepository.findAll();
        assertThat(pcRatingAttributesCategoriesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEffDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pcRatingAttributesCategoriesRepository.findAll().size();
        // set the field null
        pcRatingAttributesCategories.setEffDate(null);

        // Create the PcRatingAttributesCategories, which fails.

        restPcRatingAttributesCategoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRatingAttributesCategories))
            )
            .andExpect(status().isBadRequest());

        List<PcRatingAttributesCategories> pcRatingAttributesCategoriesList = pcRatingAttributesCategoriesRepository.findAll();
        assertThat(pcRatingAttributesCategoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = pcRatingAttributesCategoriesRepository.findAll().size();
        // set the field null
        pcRatingAttributesCategories.setCreatedAt(null);

        // Create the PcRatingAttributesCategories, which fails.

        restPcRatingAttributesCategoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRatingAttributesCategories))
            )
            .andExpect(status().isBadRequest());

        List<PcRatingAttributesCategories> pcRatingAttributesCategoriesList = pcRatingAttributesCategoriesRepository.findAll();
        assertThat(pcRatingAttributesCategoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = pcRatingAttributesCategoriesRepository.findAll().size();
        // set the field null
        pcRatingAttributesCategories.setUpdatedAt(null);

        // Create the PcRatingAttributesCategories, which fails.

        restPcRatingAttributesCategoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRatingAttributesCategories))
            )
            .andExpect(status().isBadRequest());

        List<PcRatingAttributesCategories> pcRatingAttributesCategoriesList = pcRatingAttributesCategoriesRepository.findAll();
        assertThat(pcRatingAttributesCategoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = pcRatingAttributesCategoriesRepository.findAll().size();
        // set the field null
        pcRatingAttributesCategories.setVersion(null);

        // Create the PcRatingAttributesCategories, which fails.

        restPcRatingAttributesCategoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRatingAttributesCategories))
            )
            .andExpect(status().isBadRequest());

        List<PcRatingAttributesCategories> pcRatingAttributesCategoriesList = pcRatingAttributesCategoriesRepository.findAll();
        assertThat(pcRatingAttributesCategoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesCategories() throws Exception {
        // Initialize the database
        pcRatingAttributesCategoriesRepository.saveAndFlush(pcRatingAttributesCategories);

        // Get all the pcRatingAttributesCategoriesList
        restPcRatingAttributesCategoriesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pcRatingAttributesCategories.getId().intValue())))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getPcRatingAttributesCategories() throws Exception {
        // Initialize the database
        pcRatingAttributesCategoriesRepository.saveAndFlush(pcRatingAttributesCategories);

        // Get the pcRatingAttributesCategories
        restPcRatingAttributesCategoriesMockMvc
            .perform(get(ENTITY_API_URL_ID, pcRatingAttributesCategories.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pcRatingAttributesCategories.getId().intValue()))
            .andExpect(jsonPath("$.effDate").value(DEFAULT_EFF_DATE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getPcRatingAttributesCategoriesByIdFiltering() throws Exception {
        // Initialize the database
        pcRatingAttributesCategoriesRepository.saveAndFlush(pcRatingAttributesCategories);

        Long id = pcRatingAttributesCategories.getId();

        defaultPcRatingAttributesCategoriesShouldBeFound("id.equals=" + id);
        defaultPcRatingAttributesCategoriesShouldNotBeFound("id.notEquals=" + id);

        defaultPcRatingAttributesCategoriesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPcRatingAttributesCategoriesShouldNotBeFound("id.greaterThan=" + id);

        defaultPcRatingAttributesCategoriesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPcRatingAttributesCategoriesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesCategoriesByEffDateIsEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingAttributesCategoriesRepository.saveAndFlush(pcRatingAttributesCategories);

        // Get all the pcRatingAttributesCategoriesList where effDate equals to DEFAULT_EFF_DATE
        defaultPcRatingAttributesCategoriesShouldBeFound("effDate.equals=" + DEFAULT_EFF_DATE);

        // Get all the pcRatingAttributesCategoriesList where effDate equals to UPDATED_EFF_DATE
        defaultPcRatingAttributesCategoriesShouldNotBeFound("effDate.equals=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesCategoriesByEffDateIsInShouldWork() throws Exception {
        // Initialize the database
        pcRatingAttributesCategoriesRepository.saveAndFlush(pcRatingAttributesCategories);

        // Get all the pcRatingAttributesCategoriesList where effDate in DEFAULT_EFF_DATE or UPDATED_EFF_DATE
        defaultPcRatingAttributesCategoriesShouldBeFound("effDate.in=" + DEFAULT_EFF_DATE + "," + UPDATED_EFF_DATE);

        // Get all the pcRatingAttributesCategoriesList where effDate equals to UPDATED_EFF_DATE
        defaultPcRatingAttributesCategoriesShouldNotBeFound("effDate.in=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesCategoriesByEffDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcRatingAttributesCategoriesRepository.saveAndFlush(pcRatingAttributesCategories);

        // Get all the pcRatingAttributesCategoriesList where effDate is not null
        defaultPcRatingAttributesCategoriesShouldBeFound("effDate.specified=true");

        // Get all the pcRatingAttributesCategoriesList where effDate is null
        defaultPcRatingAttributesCategoriesShouldNotBeFound("effDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesCategoriesByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingAttributesCategoriesRepository.saveAndFlush(pcRatingAttributesCategories);

        // Get all the pcRatingAttributesCategoriesList where createdAt equals to DEFAULT_CREATED_AT
        defaultPcRatingAttributesCategoriesShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the pcRatingAttributesCategoriesList where createdAt equals to UPDATED_CREATED_AT
        defaultPcRatingAttributesCategoriesShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesCategoriesByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        pcRatingAttributesCategoriesRepository.saveAndFlush(pcRatingAttributesCategories);

        // Get all the pcRatingAttributesCategoriesList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultPcRatingAttributesCategoriesShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the pcRatingAttributesCategoriesList where createdAt equals to UPDATED_CREATED_AT
        defaultPcRatingAttributesCategoriesShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesCategoriesByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcRatingAttributesCategoriesRepository.saveAndFlush(pcRatingAttributesCategories);

        // Get all the pcRatingAttributesCategoriesList where createdAt is not null
        defaultPcRatingAttributesCategoriesShouldBeFound("createdAt.specified=true");

        // Get all the pcRatingAttributesCategoriesList where createdAt is null
        defaultPcRatingAttributesCategoriesShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesCategoriesByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingAttributesCategoriesRepository.saveAndFlush(pcRatingAttributesCategories);

        // Get all the pcRatingAttributesCategoriesList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultPcRatingAttributesCategoriesShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the pcRatingAttributesCategoriesList where updatedAt equals to UPDATED_UPDATED_AT
        defaultPcRatingAttributesCategoriesShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesCategoriesByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        pcRatingAttributesCategoriesRepository.saveAndFlush(pcRatingAttributesCategories);

        // Get all the pcRatingAttributesCategoriesList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultPcRatingAttributesCategoriesShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the pcRatingAttributesCategoriesList where updatedAt equals to UPDATED_UPDATED_AT
        defaultPcRatingAttributesCategoriesShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesCategoriesByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcRatingAttributesCategoriesRepository.saveAndFlush(pcRatingAttributesCategories);

        // Get all the pcRatingAttributesCategoriesList where updatedAt is not null
        defaultPcRatingAttributesCategoriesShouldBeFound("updatedAt.specified=true");

        // Get all the pcRatingAttributesCategoriesList where updatedAt is null
        defaultPcRatingAttributesCategoriesShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesCategoriesByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingAttributesCategoriesRepository.saveAndFlush(pcRatingAttributesCategories);

        // Get all the pcRatingAttributesCategoriesList where endDate equals to DEFAULT_END_DATE
        defaultPcRatingAttributesCategoriesShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the pcRatingAttributesCategoriesList where endDate equals to UPDATED_END_DATE
        defaultPcRatingAttributesCategoriesShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesCategoriesByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        pcRatingAttributesCategoriesRepository.saveAndFlush(pcRatingAttributesCategories);

        // Get all the pcRatingAttributesCategoriesList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultPcRatingAttributesCategoriesShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the pcRatingAttributesCategoriesList where endDate equals to UPDATED_END_DATE
        defaultPcRatingAttributesCategoriesShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesCategoriesByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcRatingAttributesCategoriesRepository.saveAndFlush(pcRatingAttributesCategories);

        // Get all the pcRatingAttributesCategoriesList where endDate is not null
        defaultPcRatingAttributesCategoriesShouldBeFound("endDate.specified=true");

        // Get all the pcRatingAttributesCategoriesList where endDate is null
        defaultPcRatingAttributesCategoriesShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesCategoriesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingAttributesCategoriesRepository.saveAndFlush(pcRatingAttributesCategories);

        // Get all the pcRatingAttributesCategoriesList where version equals to DEFAULT_VERSION
        defaultPcRatingAttributesCategoriesShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the pcRatingAttributesCategoriesList where version equals to UPDATED_VERSION
        defaultPcRatingAttributesCategoriesShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesCategoriesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        pcRatingAttributesCategoriesRepository.saveAndFlush(pcRatingAttributesCategories);

        // Get all the pcRatingAttributesCategoriesList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultPcRatingAttributesCategoriesShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the pcRatingAttributesCategoriesList where version equals to UPDATED_VERSION
        defaultPcRatingAttributesCategoriesShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesCategoriesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcRatingAttributesCategoriesRepository.saveAndFlush(pcRatingAttributesCategories);

        // Get all the pcRatingAttributesCategoriesList where version is not null
        defaultPcRatingAttributesCategoriesShouldBeFound("version.specified=true");

        // Get all the pcRatingAttributesCategoriesList where version is null
        defaultPcRatingAttributesCategoriesShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesCategoriesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingAttributesCategoriesRepository.saveAndFlush(pcRatingAttributesCategories);

        // Get all the pcRatingAttributesCategoriesList where version is greater than or equal to DEFAULT_VERSION
        defaultPcRatingAttributesCategoriesShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the pcRatingAttributesCategoriesList where version is greater than or equal to UPDATED_VERSION
        defaultPcRatingAttributesCategoriesShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesCategoriesByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingAttributesCategoriesRepository.saveAndFlush(pcRatingAttributesCategories);

        // Get all the pcRatingAttributesCategoriesList where version is less than or equal to DEFAULT_VERSION
        defaultPcRatingAttributesCategoriesShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the pcRatingAttributesCategoriesList where version is less than or equal to SMALLER_VERSION
        defaultPcRatingAttributesCategoriesShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesCategoriesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        pcRatingAttributesCategoriesRepository.saveAndFlush(pcRatingAttributesCategories);

        // Get all the pcRatingAttributesCategoriesList where version is less than DEFAULT_VERSION
        defaultPcRatingAttributesCategoriesShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the pcRatingAttributesCategoriesList where version is less than UPDATED_VERSION
        defaultPcRatingAttributesCategoriesShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesCategoriesByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pcRatingAttributesCategoriesRepository.saveAndFlush(pcRatingAttributesCategories);

        // Get all the pcRatingAttributesCategoriesList where version is greater than DEFAULT_VERSION
        defaultPcRatingAttributesCategoriesShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the pcRatingAttributesCategoriesList where version is greater than SMALLER_VERSION
        defaultPcRatingAttributesCategoriesShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesCategoriesByCategoryIsEqualToSomething() throws Exception {
        RatingCategories category;
        if (TestUtil.findAll(em, RatingCategories.class).isEmpty()) {
            pcRatingAttributesCategoriesRepository.saveAndFlush(pcRatingAttributesCategories);
            category = RatingCategoriesResourceIT.createEntity(em);
        } else {
            category = TestUtil.findAll(em, RatingCategories.class).get(0);
        }
        em.persist(category);
        em.flush();
        pcRatingAttributesCategories.setCategory(category);
        pcRatingAttributesCategoriesRepository.saveAndFlush(pcRatingAttributesCategories);
        Long categoryId = category.getId();

        // Get all the pcRatingAttributesCategoriesList where category equals to categoryId
        defaultPcRatingAttributesCategoriesShouldBeFound("categoryId.equals=" + categoryId);

        // Get all the pcRatingAttributesCategoriesList where category equals to (categoryId + 1)
        defaultPcRatingAttributesCategoriesShouldNotBeFound("categoryId.equals=" + (categoryId + 1));
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesCategoriesByRatingAttributeIsEqualToSomething() throws Exception {
        PcRatingAttributes ratingAttribute;
        if (TestUtil.findAll(em, PcRatingAttributes.class).isEmpty()) {
            pcRatingAttributesCategoriesRepository.saveAndFlush(pcRatingAttributesCategories);
            ratingAttribute = PcRatingAttributesResourceIT.createEntity(em);
        } else {
            ratingAttribute = TestUtil.findAll(em, PcRatingAttributes.class).get(0);
        }
        em.persist(ratingAttribute);
        em.flush();
        pcRatingAttributesCategories.setRatingAttribute(ratingAttribute);
        pcRatingAttributesCategoriesRepository.saveAndFlush(pcRatingAttributesCategories);
        Long ratingAttributeId = ratingAttribute.getId();

        // Get all the pcRatingAttributesCategoriesList where ratingAttribute equals to ratingAttributeId
        defaultPcRatingAttributesCategoriesShouldBeFound("ratingAttributeId.equals=" + ratingAttributeId);

        // Get all the pcRatingAttributesCategoriesList where ratingAttribute equals to (ratingAttributeId + 1)
        defaultPcRatingAttributesCategoriesShouldNotBeFound("ratingAttributeId.equals=" + (ratingAttributeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPcRatingAttributesCategoriesShouldBeFound(String filter) throws Exception {
        restPcRatingAttributesCategoriesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pcRatingAttributesCategories.getId().intValue())))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restPcRatingAttributesCategoriesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPcRatingAttributesCategoriesShouldNotBeFound(String filter) throws Exception {
        restPcRatingAttributesCategoriesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPcRatingAttributesCategoriesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPcRatingAttributesCategories() throws Exception {
        // Get the pcRatingAttributesCategories
        restPcRatingAttributesCategoriesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPcRatingAttributesCategories() throws Exception {
        // Initialize the database
        pcRatingAttributesCategoriesRepository.saveAndFlush(pcRatingAttributesCategories);

        int databaseSizeBeforeUpdate = pcRatingAttributesCategoriesRepository.findAll().size();

        // Update the pcRatingAttributesCategories
        PcRatingAttributesCategories updatedPcRatingAttributesCategories = pcRatingAttributesCategoriesRepository
            .findById(pcRatingAttributesCategories.getId())
            .get();
        // Disconnect from session so that the updates on updatedPcRatingAttributesCategories are not directly saved in db
        em.detach(updatedPcRatingAttributesCategories);
        updatedPcRatingAttributesCategories
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restPcRatingAttributesCategoriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPcRatingAttributesCategories.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPcRatingAttributesCategories))
            )
            .andExpect(status().isOk());

        // Validate the PcRatingAttributesCategories in the database
        List<PcRatingAttributesCategories> pcRatingAttributesCategoriesList = pcRatingAttributesCategoriesRepository.findAll();
        assertThat(pcRatingAttributesCategoriesList).hasSize(databaseSizeBeforeUpdate);
        PcRatingAttributesCategories testPcRatingAttributesCategories = pcRatingAttributesCategoriesList.get(
            pcRatingAttributesCategoriesList.size() - 1
        );
        assertThat(testPcRatingAttributesCategories.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testPcRatingAttributesCategories.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPcRatingAttributesCategories.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testPcRatingAttributesCategories.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testPcRatingAttributesCategories.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingPcRatingAttributesCategories() throws Exception {
        int databaseSizeBeforeUpdate = pcRatingAttributesCategoriesRepository.findAll().size();
        pcRatingAttributesCategories.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPcRatingAttributesCategoriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pcRatingAttributesCategories.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRatingAttributesCategories))
            )
            .andExpect(status().isBadRequest());

        // Validate the PcRatingAttributesCategories in the database
        List<PcRatingAttributesCategories> pcRatingAttributesCategoriesList = pcRatingAttributesCategoriesRepository.findAll();
        assertThat(pcRatingAttributesCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPcRatingAttributesCategories() throws Exception {
        int databaseSizeBeforeUpdate = pcRatingAttributesCategoriesRepository.findAll().size();
        pcRatingAttributesCategories.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPcRatingAttributesCategoriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRatingAttributesCategories))
            )
            .andExpect(status().isBadRequest());

        // Validate the PcRatingAttributesCategories in the database
        List<PcRatingAttributesCategories> pcRatingAttributesCategoriesList = pcRatingAttributesCategoriesRepository.findAll();
        assertThat(pcRatingAttributesCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPcRatingAttributesCategories() throws Exception {
        int databaseSizeBeforeUpdate = pcRatingAttributesCategoriesRepository.findAll().size();
        pcRatingAttributesCategories.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPcRatingAttributesCategoriesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRatingAttributesCategories))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PcRatingAttributesCategories in the database
        List<PcRatingAttributesCategories> pcRatingAttributesCategoriesList = pcRatingAttributesCategoriesRepository.findAll();
        assertThat(pcRatingAttributesCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePcRatingAttributesCategoriesWithPatch() throws Exception {
        // Initialize the database
        pcRatingAttributesCategoriesRepository.saveAndFlush(pcRatingAttributesCategories);

        int databaseSizeBeforeUpdate = pcRatingAttributesCategoriesRepository.findAll().size();

        // Update the pcRatingAttributesCategories using partial update
        PcRatingAttributesCategories partialUpdatedPcRatingAttributesCategories = new PcRatingAttributesCategories();
        partialUpdatedPcRatingAttributesCategories.setId(pcRatingAttributesCategories.getId());

        partialUpdatedPcRatingAttributesCategories.version(UPDATED_VERSION);

        restPcRatingAttributesCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPcRatingAttributesCategories.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPcRatingAttributesCategories))
            )
            .andExpect(status().isOk());

        // Validate the PcRatingAttributesCategories in the database
        List<PcRatingAttributesCategories> pcRatingAttributesCategoriesList = pcRatingAttributesCategoriesRepository.findAll();
        assertThat(pcRatingAttributesCategoriesList).hasSize(databaseSizeBeforeUpdate);
        PcRatingAttributesCategories testPcRatingAttributesCategories = pcRatingAttributesCategoriesList.get(
            pcRatingAttributesCategoriesList.size() - 1
        );
        assertThat(testPcRatingAttributesCategories.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testPcRatingAttributesCategories.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testPcRatingAttributesCategories.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testPcRatingAttributesCategories.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testPcRatingAttributesCategories.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void fullUpdatePcRatingAttributesCategoriesWithPatch() throws Exception {
        // Initialize the database
        pcRatingAttributesCategoriesRepository.saveAndFlush(pcRatingAttributesCategories);

        int databaseSizeBeforeUpdate = pcRatingAttributesCategoriesRepository.findAll().size();

        // Update the pcRatingAttributesCategories using partial update
        PcRatingAttributesCategories partialUpdatedPcRatingAttributesCategories = new PcRatingAttributesCategories();
        partialUpdatedPcRatingAttributesCategories.setId(pcRatingAttributesCategories.getId());

        partialUpdatedPcRatingAttributesCategories
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restPcRatingAttributesCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPcRatingAttributesCategories.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPcRatingAttributesCategories))
            )
            .andExpect(status().isOk());

        // Validate the PcRatingAttributesCategories in the database
        List<PcRatingAttributesCategories> pcRatingAttributesCategoriesList = pcRatingAttributesCategoriesRepository.findAll();
        assertThat(pcRatingAttributesCategoriesList).hasSize(databaseSizeBeforeUpdate);
        PcRatingAttributesCategories testPcRatingAttributesCategories = pcRatingAttributesCategoriesList.get(
            pcRatingAttributesCategoriesList.size() - 1
        );
        assertThat(testPcRatingAttributesCategories.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testPcRatingAttributesCategories.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPcRatingAttributesCategories.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testPcRatingAttributesCategories.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testPcRatingAttributesCategories.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingPcRatingAttributesCategories() throws Exception {
        int databaseSizeBeforeUpdate = pcRatingAttributesCategoriesRepository.findAll().size();
        pcRatingAttributesCategories.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPcRatingAttributesCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pcRatingAttributesCategories.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pcRatingAttributesCategories))
            )
            .andExpect(status().isBadRequest());

        // Validate the PcRatingAttributesCategories in the database
        List<PcRatingAttributesCategories> pcRatingAttributesCategoriesList = pcRatingAttributesCategoriesRepository.findAll();
        assertThat(pcRatingAttributesCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPcRatingAttributesCategories() throws Exception {
        int databaseSizeBeforeUpdate = pcRatingAttributesCategoriesRepository.findAll().size();
        pcRatingAttributesCategories.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPcRatingAttributesCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pcRatingAttributesCategories))
            )
            .andExpect(status().isBadRequest());

        // Validate the PcRatingAttributesCategories in the database
        List<PcRatingAttributesCategories> pcRatingAttributesCategoriesList = pcRatingAttributesCategoriesRepository.findAll();
        assertThat(pcRatingAttributesCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPcRatingAttributesCategories() throws Exception {
        int databaseSizeBeforeUpdate = pcRatingAttributesCategoriesRepository.findAll().size();
        pcRatingAttributesCategories.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPcRatingAttributesCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pcRatingAttributesCategories))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PcRatingAttributesCategories in the database
        List<PcRatingAttributesCategories> pcRatingAttributesCategoriesList = pcRatingAttributesCategoriesRepository.findAll();
        assertThat(pcRatingAttributesCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePcRatingAttributesCategories() throws Exception {
        // Initialize the database
        pcRatingAttributesCategoriesRepository.saveAndFlush(pcRatingAttributesCategories);

        int databaseSizeBeforeDelete = pcRatingAttributesCategoriesRepository.findAll().size();

        // Delete the pcRatingAttributesCategories
        restPcRatingAttributesCategoriesMockMvc
            .perform(delete(ENTITY_API_URL_ID, pcRatingAttributesCategories.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PcRatingAttributesCategories> pcRatingAttributesCategoriesList = pcRatingAttributesCategoriesRepository.findAll();
        assertThat(pcRatingAttributesCategoriesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
