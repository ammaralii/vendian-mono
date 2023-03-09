package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.PcRatingAttributes;
import com.venturedive.vendian_mono.domain.PcRatingAttributesCategories;
import com.venturedive.vendian_mono.domain.RatingAttributeMappings;
import com.venturedive.vendian_mono.repository.PcRatingAttributesRepository;
import com.venturedive.vendian_mono.service.criteria.PcRatingAttributesCriteria;
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
 * Integration tests for the {@link PcRatingAttributesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PcRatingAttributesResourceIT {

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

    private static final String DEFAULT_SUB_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_SUB_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/pc-rating-attributes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PcRatingAttributesRepository pcRatingAttributesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPcRatingAttributesMockMvc;

    private PcRatingAttributes pcRatingAttributes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PcRatingAttributes createEntity(EntityManager em) {
        PcRatingAttributes pcRatingAttributes = new PcRatingAttributes()
            .name(DEFAULT_NAME)
            .effDate(DEFAULT_EFF_DATE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .endDate(DEFAULT_END_DATE)
            .version(DEFAULT_VERSION)
            .subCategory(DEFAULT_SUB_CATEGORY)
            .description(DEFAULT_DESCRIPTION);
        return pcRatingAttributes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PcRatingAttributes createUpdatedEntity(EntityManager em) {
        PcRatingAttributes pcRatingAttributes = new PcRatingAttributes()
            .name(UPDATED_NAME)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION)
            .subCategory(UPDATED_SUB_CATEGORY)
            .description(UPDATED_DESCRIPTION);
        return pcRatingAttributes;
    }

    @BeforeEach
    public void initTest() {
        pcRatingAttributes = createEntity(em);
    }

    @Test
    @Transactional
    void createPcRatingAttributes() throws Exception {
        int databaseSizeBeforeCreate = pcRatingAttributesRepository.findAll().size();
        // Create the PcRatingAttributes
        restPcRatingAttributesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRatingAttributes))
            )
            .andExpect(status().isCreated());

        // Validate the PcRatingAttributes in the database
        List<PcRatingAttributes> pcRatingAttributesList = pcRatingAttributesRepository.findAll();
        assertThat(pcRatingAttributesList).hasSize(databaseSizeBeforeCreate + 1);
        PcRatingAttributes testPcRatingAttributes = pcRatingAttributesList.get(pcRatingAttributesList.size() - 1);
        assertThat(testPcRatingAttributes.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPcRatingAttributes.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testPcRatingAttributes.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testPcRatingAttributes.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testPcRatingAttributes.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testPcRatingAttributes.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testPcRatingAttributes.getSubCategory()).isEqualTo(DEFAULT_SUB_CATEGORY);
        assertThat(testPcRatingAttributes.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createPcRatingAttributesWithExistingId() throws Exception {
        // Create the PcRatingAttributes with an existing ID
        pcRatingAttributes.setId(1L);

        int databaseSizeBeforeCreate = pcRatingAttributesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPcRatingAttributesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRatingAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the PcRatingAttributes in the database
        List<PcRatingAttributes> pcRatingAttributesList = pcRatingAttributesRepository.findAll();
        assertThat(pcRatingAttributesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = pcRatingAttributesRepository.findAll().size();
        // set the field null
        pcRatingAttributes.setName(null);

        // Create the PcRatingAttributes, which fails.

        restPcRatingAttributesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRatingAttributes))
            )
            .andExpect(status().isBadRequest());

        List<PcRatingAttributes> pcRatingAttributesList = pcRatingAttributesRepository.findAll();
        assertThat(pcRatingAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEffDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pcRatingAttributesRepository.findAll().size();
        // set the field null
        pcRatingAttributes.setEffDate(null);

        // Create the PcRatingAttributes, which fails.

        restPcRatingAttributesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRatingAttributes))
            )
            .andExpect(status().isBadRequest());

        List<PcRatingAttributes> pcRatingAttributesList = pcRatingAttributesRepository.findAll();
        assertThat(pcRatingAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = pcRatingAttributesRepository.findAll().size();
        // set the field null
        pcRatingAttributes.setCreatedAt(null);

        // Create the PcRatingAttributes, which fails.

        restPcRatingAttributesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRatingAttributes))
            )
            .andExpect(status().isBadRequest());

        List<PcRatingAttributes> pcRatingAttributesList = pcRatingAttributesRepository.findAll();
        assertThat(pcRatingAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = pcRatingAttributesRepository.findAll().size();
        // set the field null
        pcRatingAttributes.setUpdatedAt(null);

        // Create the PcRatingAttributes, which fails.

        restPcRatingAttributesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRatingAttributes))
            )
            .andExpect(status().isBadRequest());

        List<PcRatingAttributes> pcRatingAttributesList = pcRatingAttributesRepository.findAll();
        assertThat(pcRatingAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = pcRatingAttributesRepository.findAll().size();
        // set the field null
        pcRatingAttributes.setVersion(null);

        // Create the PcRatingAttributes, which fails.

        restPcRatingAttributesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRatingAttributes))
            )
            .andExpect(status().isBadRequest());

        List<PcRatingAttributes> pcRatingAttributesList = pcRatingAttributesRepository.findAll();
        assertThat(pcRatingAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributes() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        // Get all the pcRatingAttributesList
        restPcRatingAttributesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pcRatingAttributes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].subCategory").value(hasItem(DEFAULT_SUB_CATEGORY)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getPcRatingAttributes() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        // Get the pcRatingAttributes
        restPcRatingAttributesMockMvc
            .perform(get(ENTITY_API_URL_ID, pcRatingAttributes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pcRatingAttributes.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.effDate").value(DEFAULT_EFF_DATE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.subCategory").value(DEFAULT_SUB_CATEGORY))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getPcRatingAttributesByIdFiltering() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        Long id = pcRatingAttributes.getId();

        defaultPcRatingAttributesShouldBeFound("id.equals=" + id);
        defaultPcRatingAttributesShouldNotBeFound("id.notEquals=" + id);

        defaultPcRatingAttributesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPcRatingAttributesShouldNotBeFound("id.greaterThan=" + id);

        defaultPcRatingAttributesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPcRatingAttributesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        // Get all the pcRatingAttributesList where name equals to DEFAULT_NAME
        defaultPcRatingAttributesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the pcRatingAttributesList where name equals to UPDATED_NAME
        defaultPcRatingAttributesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        // Get all the pcRatingAttributesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPcRatingAttributesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the pcRatingAttributesList where name equals to UPDATED_NAME
        defaultPcRatingAttributesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        // Get all the pcRatingAttributesList where name is not null
        defaultPcRatingAttributesShouldBeFound("name.specified=true");

        // Get all the pcRatingAttributesList where name is null
        defaultPcRatingAttributesShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesByNameContainsSomething() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        // Get all the pcRatingAttributesList where name contains DEFAULT_NAME
        defaultPcRatingAttributesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the pcRatingAttributesList where name contains UPDATED_NAME
        defaultPcRatingAttributesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        // Get all the pcRatingAttributesList where name does not contain DEFAULT_NAME
        defaultPcRatingAttributesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the pcRatingAttributesList where name does not contain UPDATED_NAME
        defaultPcRatingAttributesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesByEffDateIsEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        // Get all the pcRatingAttributesList where effDate equals to DEFAULT_EFF_DATE
        defaultPcRatingAttributesShouldBeFound("effDate.equals=" + DEFAULT_EFF_DATE);

        // Get all the pcRatingAttributesList where effDate equals to UPDATED_EFF_DATE
        defaultPcRatingAttributesShouldNotBeFound("effDate.equals=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesByEffDateIsInShouldWork() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        // Get all the pcRatingAttributesList where effDate in DEFAULT_EFF_DATE or UPDATED_EFF_DATE
        defaultPcRatingAttributesShouldBeFound("effDate.in=" + DEFAULT_EFF_DATE + "," + UPDATED_EFF_DATE);

        // Get all the pcRatingAttributesList where effDate equals to UPDATED_EFF_DATE
        defaultPcRatingAttributesShouldNotBeFound("effDate.in=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesByEffDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        // Get all the pcRatingAttributesList where effDate is not null
        defaultPcRatingAttributesShouldBeFound("effDate.specified=true");

        // Get all the pcRatingAttributesList where effDate is null
        defaultPcRatingAttributesShouldNotBeFound("effDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        // Get all the pcRatingAttributesList where createdAt equals to DEFAULT_CREATED_AT
        defaultPcRatingAttributesShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the pcRatingAttributesList where createdAt equals to UPDATED_CREATED_AT
        defaultPcRatingAttributesShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        // Get all the pcRatingAttributesList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultPcRatingAttributesShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the pcRatingAttributesList where createdAt equals to UPDATED_CREATED_AT
        defaultPcRatingAttributesShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        // Get all the pcRatingAttributesList where createdAt is not null
        defaultPcRatingAttributesShouldBeFound("createdAt.specified=true");

        // Get all the pcRatingAttributesList where createdAt is null
        defaultPcRatingAttributesShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        // Get all the pcRatingAttributesList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultPcRatingAttributesShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the pcRatingAttributesList where updatedAt equals to UPDATED_UPDATED_AT
        defaultPcRatingAttributesShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        // Get all the pcRatingAttributesList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultPcRatingAttributesShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the pcRatingAttributesList where updatedAt equals to UPDATED_UPDATED_AT
        defaultPcRatingAttributesShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        // Get all the pcRatingAttributesList where updatedAt is not null
        defaultPcRatingAttributesShouldBeFound("updatedAt.specified=true");

        // Get all the pcRatingAttributesList where updatedAt is null
        defaultPcRatingAttributesShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        // Get all the pcRatingAttributesList where endDate equals to DEFAULT_END_DATE
        defaultPcRatingAttributesShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the pcRatingAttributesList where endDate equals to UPDATED_END_DATE
        defaultPcRatingAttributesShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        // Get all the pcRatingAttributesList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultPcRatingAttributesShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the pcRatingAttributesList where endDate equals to UPDATED_END_DATE
        defaultPcRatingAttributesShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        // Get all the pcRatingAttributesList where endDate is not null
        defaultPcRatingAttributesShouldBeFound("endDate.specified=true");

        // Get all the pcRatingAttributesList where endDate is null
        defaultPcRatingAttributesShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        // Get all the pcRatingAttributesList where version equals to DEFAULT_VERSION
        defaultPcRatingAttributesShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the pcRatingAttributesList where version equals to UPDATED_VERSION
        defaultPcRatingAttributesShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        // Get all the pcRatingAttributesList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultPcRatingAttributesShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the pcRatingAttributesList where version equals to UPDATED_VERSION
        defaultPcRatingAttributesShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        // Get all the pcRatingAttributesList where version is not null
        defaultPcRatingAttributesShouldBeFound("version.specified=true");

        // Get all the pcRatingAttributesList where version is null
        defaultPcRatingAttributesShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        // Get all the pcRatingAttributesList where version is greater than or equal to DEFAULT_VERSION
        defaultPcRatingAttributesShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the pcRatingAttributesList where version is greater than or equal to UPDATED_VERSION
        defaultPcRatingAttributesShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        // Get all the pcRatingAttributesList where version is less than or equal to DEFAULT_VERSION
        defaultPcRatingAttributesShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the pcRatingAttributesList where version is less than or equal to SMALLER_VERSION
        defaultPcRatingAttributesShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        // Get all the pcRatingAttributesList where version is less than DEFAULT_VERSION
        defaultPcRatingAttributesShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the pcRatingAttributesList where version is less than UPDATED_VERSION
        defaultPcRatingAttributesShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        // Get all the pcRatingAttributesList where version is greater than DEFAULT_VERSION
        defaultPcRatingAttributesShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the pcRatingAttributesList where version is greater than SMALLER_VERSION
        defaultPcRatingAttributesShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesBySubCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        // Get all the pcRatingAttributesList where subCategory equals to DEFAULT_SUB_CATEGORY
        defaultPcRatingAttributesShouldBeFound("subCategory.equals=" + DEFAULT_SUB_CATEGORY);

        // Get all the pcRatingAttributesList where subCategory equals to UPDATED_SUB_CATEGORY
        defaultPcRatingAttributesShouldNotBeFound("subCategory.equals=" + UPDATED_SUB_CATEGORY);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesBySubCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        // Get all the pcRatingAttributesList where subCategory in DEFAULT_SUB_CATEGORY or UPDATED_SUB_CATEGORY
        defaultPcRatingAttributesShouldBeFound("subCategory.in=" + DEFAULT_SUB_CATEGORY + "," + UPDATED_SUB_CATEGORY);

        // Get all the pcRatingAttributesList where subCategory equals to UPDATED_SUB_CATEGORY
        defaultPcRatingAttributesShouldNotBeFound("subCategory.in=" + UPDATED_SUB_CATEGORY);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesBySubCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        // Get all the pcRatingAttributesList where subCategory is not null
        defaultPcRatingAttributesShouldBeFound("subCategory.specified=true");

        // Get all the pcRatingAttributesList where subCategory is null
        defaultPcRatingAttributesShouldNotBeFound("subCategory.specified=false");
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesBySubCategoryContainsSomething() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        // Get all the pcRatingAttributesList where subCategory contains DEFAULT_SUB_CATEGORY
        defaultPcRatingAttributesShouldBeFound("subCategory.contains=" + DEFAULT_SUB_CATEGORY);

        // Get all the pcRatingAttributesList where subCategory contains UPDATED_SUB_CATEGORY
        defaultPcRatingAttributesShouldNotBeFound("subCategory.contains=" + UPDATED_SUB_CATEGORY);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesBySubCategoryNotContainsSomething() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        // Get all the pcRatingAttributesList where subCategory does not contain DEFAULT_SUB_CATEGORY
        defaultPcRatingAttributesShouldNotBeFound("subCategory.doesNotContain=" + DEFAULT_SUB_CATEGORY);

        // Get all the pcRatingAttributesList where subCategory does not contain UPDATED_SUB_CATEGORY
        defaultPcRatingAttributesShouldBeFound("subCategory.doesNotContain=" + UPDATED_SUB_CATEGORY);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        // Get all the pcRatingAttributesList where description equals to DEFAULT_DESCRIPTION
        defaultPcRatingAttributesShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the pcRatingAttributesList where description equals to UPDATED_DESCRIPTION
        defaultPcRatingAttributesShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        // Get all the pcRatingAttributesList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultPcRatingAttributesShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the pcRatingAttributesList where description equals to UPDATED_DESCRIPTION
        defaultPcRatingAttributesShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        // Get all the pcRatingAttributesList where description is not null
        defaultPcRatingAttributesShouldBeFound("description.specified=true");

        // Get all the pcRatingAttributesList where description is null
        defaultPcRatingAttributesShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        // Get all the pcRatingAttributesList where description contains DEFAULT_DESCRIPTION
        defaultPcRatingAttributesShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the pcRatingAttributesList where description contains UPDATED_DESCRIPTION
        defaultPcRatingAttributesShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        // Get all the pcRatingAttributesList where description does not contain DEFAULT_DESCRIPTION
        defaultPcRatingAttributesShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the pcRatingAttributesList where description does not contain UPDATED_DESCRIPTION
        defaultPcRatingAttributesShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesByPcratingattributescategoriesRatingattributeIsEqualToSomething() throws Exception {
        PcRatingAttributesCategories pcratingattributescategoriesRatingattribute;
        if (TestUtil.findAll(em, PcRatingAttributesCategories.class).isEmpty()) {
            pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);
            pcratingattributescategoriesRatingattribute = PcRatingAttributesCategoriesResourceIT.createEntity(em);
        } else {
            pcratingattributescategoriesRatingattribute = TestUtil.findAll(em, PcRatingAttributesCategories.class).get(0);
        }
        em.persist(pcratingattributescategoriesRatingattribute);
        em.flush();
        pcRatingAttributes.addPcratingattributescategoriesRatingattribute(pcratingattributescategoriesRatingattribute);
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);
        Long pcratingattributescategoriesRatingattributeId = pcratingattributescategoriesRatingattribute.getId();

        // Get all the pcRatingAttributesList where pcratingattributescategoriesRatingattribute equals to pcratingattributescategoriesRatingattributeId
        defaultPcRatingAttributesShouldBeFound(
            "pcratingattributescategoriesRatingattributeId.equals=" + pcratingattributescategoriesRatingattributeId
        );

        // Get all the pcRatingAttributesList where pcratingattributescategoriesRatingattribute equals to (pcratingattributescategoriesRatingattributeId + 1)
        defaultPcRatingAttributesShouldNotBeFound(
            "pcratingattributescategoriesRatingattributeId.equals=" + (pcratingattributescategoriesRatingattributeId + 1)
        );
    }

    @Test
    @Transactional
    void getAllPcRatingAttributesByRatingattributemappingsRatingattributeIsEqualToSomething() throws Exception {
        RatingAttributeMappings ratingattributemappingsRatingattribute;
        if (TestUtil.findAll(em, RatingAttributeMappings.class).isEmpty()) {
            pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);
            ratingattributemappingsRatingattribute = RatingAttributeMappingsResourceIT.createEntity(em);
        } else {
            ratingattributemappingsRatingattribute = TestUtil.findAll(em, RatingAttributeMappings.class).get(0);
        }
        em.persist(ratingattributemappingsRatingattribute);
        em.flush();
        pcRatingAttributes.addRatingattributemappingsRatingattribute(ratingattributemappingsRatingattribute);
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);
        Long ratingattributemappingsRatingattributeId = ratingattributemappingsRatingattribute.getId();

        // Get all the pcRatingAttributesList where ratingattributemappingsRatingattribute equals to ratingattributemappingsRatingattributeId
        defaultPcRatingAttributesShouldBeFound(
            "ratingattributemappingsRatingattributeId.equals=" + ratingattributemappingsRatingattributeId
        );

        // Get all the pcRatingAttributesList where ratingattributemappingsRatingattribute equals to (ratingattributemappingsRatingattributeId + 1)
        defaultPcRatingAttributesShouldNotBeFound(
            "ratingattributemappingsRatingattributeId.equals=" + (ratingattributemappingsRatingattributeId + 1)
        );
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPcRatingAttributesShouldBeFound(String filter) throws Exception {
        restPcRatingAttributesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pcRatingAttributes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].subCategory").value(hasItem(DEFAULT_SUB_CATEGORY)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restPcRatingAttributesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPcRatingAttributesShouldNotBeFound(String filter) throws Exception {
        restPcRatingAttributesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPcRatingAttributesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPcRatingAttributes() throws Exception {
        // Get the pcRatingAttributes
        restPcRatingAttributesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPcRatingAttributes() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        int databaseSizeBeforeUpdate = pcRatingAttributesRepository.findAll().size();

        // Update the pcRatingAttributes
        PcRatingAttributes updatedPcRatingAttributes = pcRatingAttributesRepository.findById(pcRatingAttributes.getId()).get();
        // Disconnect from session so that the updates on updatedPcRatingAttributes are not directly saved in db
        em.detach(updatedPcRatingAttributes);
        updatedPcRatingAttributes
            .name(UPDATED_NAME)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION)
            .subCategory(UPDATED_SUB_CATEGORY)
            .description(UPDATED_DESCRIPTION);

        restPcRatingAttributesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPcRatingAttributes.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPcRatingAttributes))
            )
            .andExpect(status().isOk());

        // Validate the PcRatingAttributes in the database
        List<PcRatingAttributes> pcRatingAttributesList = pcRatingAttributesRepository.findAll();
        assertThat(pcRatingAttributesList).hasSize(databaseSizeBeforeUpdate);
        PcRatingAttributes testPcRatingAttributes = pcRatingAttributesList.get(pcRatingAttributesList.size() - 1);
        assertThat(testPcRatingAttributes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPcRatingAttributes.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testPcRatingAttributes.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPcRatingAttributes.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testPcRatingAttributes.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testPcRatingAttributes.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testPcRatingAttributes.getSubCategory()).isEqualTo(UPDATED_SUB_CATEGORY);
        assertThat(testPcRatingAttributes.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingPcRatingAttributes() throws Exception {
        int databaseSizeBeforeUpdate = pcRatingAttributesRepository.findAll().size();
        pcRatingAttributes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPcRatingAttributesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pcRatingAttributes.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRatingAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the PcRatingAttributes in the database
        List<PcRatingAttributes> pcRatingAttributesList = pcRatingAttributesRepository.findAll();
        assertThat(pcRatingAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPcRatingAttributes() throws Exception {
        int databaseSizeBeforeUpdate = pcRatingAttributesRepository.findAll().size();
        pcRatingAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPcRatingAttributesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRatingAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the PcRatingAttributes in the database
        List<PcRatingAttributes> pcRatingAttributesList = pcRatingAttributesRepository.findAll();
        assertThat(pcRatingAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPcRatingAttributes() throws Exception {
        int databaseSizeBeforeUpdate = pcRatingAttributesRepository.findAll().size();
        pcRatingAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPcRatingAttributesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRatingAttributes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PcRatingAttributes in the database
        List<PcRatingAttributes> pcRatingAttributesList = pcRatingAttributesRepository.findAll();
        assertThat(pcRatingAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePcRatingAttributesWithPatch() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        int databaseSizeBeforeUpdate = pcRatingAttributesRepository.findAll().size();

        // Update the pcRatingAttributes using partial update
        PcRatingAttributes partialUpdatedPcRatingAttributes = new PcRatingAttributes();
        partialUpdatedPcRatingAttributes.setId(pcRatingAttributes.getId());

        partialUpdatedPcRatingAttributes
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .subCategory(UPDATED_SUB_CATEGORY)
            .description(UPDATED_DESCRIPTION);

        restPcRatingAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPcRatingAttributes.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPcRatingAttributes))
            )
            .andExpect(status().isOk());

        // Validate the PcRatingAttributes in the database
        List<PcRatingAttributes> pcRatingAttributesList = pcRatingAttributesRepository.findAll();
        assertThat(pcRatingAttributesList).hasSize(databaseSizeBeforeUpdate);
        PcRatingAttributes testPcRatingAttributes = pcRatingAttributesList.get(pcRatingAttributesList.size() - 1);
        assertThat(testPcRatingAttributes.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPcRatingAttributes.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testPcRatingAttributes.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPcRatingAttributes.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testPcRatingAttributes.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testPcRatingAttributes.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testPcRatingAttributes.getSubCategory()).isEqualTo(UPDATED_SUB_CATEGORY);
        assertThat(testPcRatingAttributes.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdatePcRatingAttributesWithPatch() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        int databaseSizeBeforeUpdate = pcRatingAttributesRepository.findAll().size();

        // Update the pcRatingAttributes using partial update
        PcRatingAttributes partialUpdatedPcRatingAttributes = new PcRatingAttributes();
        partialUpdatedPcRatingAttributes.setId(pcRatingAttributes.getId());

        partialUpdatedPcRatingAttributes
            .name(UPDATED_NAME)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION)
            .subCategory(UPDATED_SUB_CATEGORY)
            .description(UPDATED_DESCRIPTION);

        restPcRatingAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPcRatingAttributes.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPcRatingAttributes))
            )
            .andExpect(status().isOk());

        // Validate the PcRatingAttributes in the database
        List<PcRatingAttributes> pcRatingAttributesList = pcRatingAttributesRepository.findAll();
        assertThat(pcRatingAttributesList).hasSize(databaseSizeBeforeUpdate);
        PcRatingAttributes testPcRatingAttributes = pcRatingAttributesList.get(pcRatingAttributesList.size() - 1);
        assertThat(testPcRatingAttributes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPcRatingAttributes.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testPcRatingAttributes.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPcRatingAttributes.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testPcRatingAttributes.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testPcRatingAttributes.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testPcRatingAttributes.getSubCategory()).isEqualTo(UPDATED_SUB_CATEGORY);
        assertThat(testPcRatingAttributes.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingPcRatingAttributes() throws Exception {
        int databaseSizeBeforeUpdate = pcRatingAttributesRepository.findAll().size();
        pcRatingAttributes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPcRatingAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pcRatingAttributes.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pcRatingAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the PcRatingAttributes in the database
        List<PcRatingAttributes> pcRatingAttributesList = pcRatingAttributesRepository.findAll();
        assertThat(pcRatingAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPcRatingAttributes() throws Exception {
        int databaseSizeBeforeUpdate = pcRatingAttributesRepository.findAll().size();
        pcRatingAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPcRatingAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pcRatingAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the PcRatingAttributes in the database
        List<PcRatingAttributes> pcRatingAttributesList = pcRatingAttributesRepository.findAll();
        assertThat(pcRatingAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPcRatingAttributes() throws Exception {
        int databaseSizeBeforeUpdate = pcRatingAttributesRepository.findAll().size();
        pcRatingAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPcRatingAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pcRatingAttributes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PcRatingAttributes in the database
        List<PcRatingAttributes> pcRatingAttributesList = pcRatingAttributesRepository.findAll();
        assertThat(pcRatingAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePcRatingAttributes() throws Exception {
        // Initialize the database
        pcRatingAttributesRepository.saveAndFlush(pcRatingAttributes);

        int databaseSizeBeforeDelete = pcRatingAttributesRepository.findAll().size();

        // Delete the pcRatingAttributes
        restPcRatingAttributesMockMvc
            .perform(delete(ENTITY_API_URL_ID, pcRatingAttributes.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PcRatingAttributes> pcRatingAttributesList = pcRatingAttributesRepository.findAll();
        assertThat(pcRatingAttributesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
