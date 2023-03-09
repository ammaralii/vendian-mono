package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Attributes;
import com.venturedive.vendian_mono.domain.PcRaterAttributes;
import com.venturedive.vendian_mono.domain.PcRatingAttributes;
import com.venturedive.vendian_mono.domain.RatingAttributeMappings;
import com.venturedive.vendian_mono.repository.RatingAttributeMappingsRepository;
import com.venturedive.vendian_mono.service.criteria.RatingAttributeMappingsCriteria;
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
 * Integration tests for the {@link RatingAttributeMappingsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RatingAttributeMappingsResourceIT {

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

    private static final String ENTITY_API_URL = "/api/rating-attribute-mappings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RatingAttributeMappingsRepository ratingAttributeMappingsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRatingAttributeMappingsMockMvc;

    private RatingAttributeMappings ratingAttributeMappings;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RatingAttributeMappings createEntity(EntityManager em) {
        RatingAttributeMappings ratingAttributeMappings = new RatingAttributeMappings()
            .effDate(DEFAULT_EFF_DATE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .endDate(DEFAULT_END_DATE)
            .version(DEFAULT_VERSION);
        // Add required entity
        Attributes attributes;
        if (TestUtil.findAll(em, Attributes.class).isEmpty()) {
            attributes = AttributesResourceIT.createEntity(em);
            em.persist(attributes);
            em.flush();
        } else {
            attributes = TestUtil.findAll(em, Attributes.class).get(0);
        }
        ratingAttributeMappings.setAttribute(attributes);
        // Add required entity
        PcRatingAttributes pcRatingAttributes;
        if (TestUtil.findAll(em, PcRatingAttributes.class).isEmpty()) {
            pcRatingAttributes = PcRatingAttributesResourceIT.createEntity(em);
            em.persist(pcRatingAttributes);
            em.flush();
        } else {
            pcRatingAttributes = TestUtil.findAll(em, PcRatingAttributes.class).get(0);
        }
        ratingAttributeMappings.setRatingAttribute(pcRatingAttributes);
        return ratingAttributeMappings;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RatingAttributeMappings createUpdatedEntity(EntityManager em) {
        RatingAttributeMappings ratingAttributeMappings = new RatingAttributeMappings()
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);
        // Add required entity
        Attributes attributes;
        if (TestUtil.findAll(em, Attributes.class).isEmpty()) {
            attributes = AttributesResourceIT.createUpdatedEntity(em);
            em.persist(attributes);
            em.flush();
        } else {
            attributes = TestUtil.findAll(em, Attributes.class).get(0);
        }
        ratingAttributeMappings.setAttribute(attributes);
        // Add required entity
        PcRatingAttributes pcRatingAttributes;
        if (TestUtil.findAll(em, PcRatingAttributes.class).isEmpty()) {
            pcRatingAttributes = PcRatingAttributesResourceIT.createUpdatedEntity(em);
            em.persist(pcRatingAttributes);
            em.flush();
        } else {
            pcRatingAttributes = TestUtil.findAll(em, PcRatingAttributes.class).get(0);
        }
        ratingAttributeMappings.setRatingAttribute(pcRatingAttributes);
        return ratingAttributeMappings;
    }

    @BeforeEach
    public void initTest() {
        ratingAttributeMappings = createEntity(em);
    }

    @Test
    @Transactional
    void createRatingAttributeMappings() throws Exception {
        int databaseSizeBeforeCreate = ratingAttributeMappingsRepository.findAll().size();
        // Create the RatingAttributeMappings
        restRatingAttributeMappingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingAttributeMappings))
            )
            .andExpect(status().isCreated());

        // Validate the RatingAttributeMappings in the database
        List<RatingAttributeMappings> ratingAttributeMappingsList = ratingAttributeMappingsRepository.findAll();
        assertThat(ratingAttributeMappingsList).hasSize(databaseSizeBeforeCreate + 1);
        RatingAttributeMappings testRatingAttributeMappings = ratingAttributeMappingsList.get(ratingAttributeMappingsList.size() - 1);
        assertThat(testRatingAttributeMappings.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testRatingAttributeMappings.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testRatingAttributeMappings.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testRatingAttributeMappings.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testRatingAttributeMappings.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createRatingAttributeMappingsWithExistingId() throws Exception {
        // Create the RatingAttributeMappings with an existing ID
        ratingAttributeMappings.setId(1L);

        int databaseSizeBeforeCreate = ratingAttributeMappingsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRatingAttributeMappingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingAttributeMappings))
            )
            .andExpect(status().isBadRequest());

        // Validate the RatingAttributeMappings in the database
        List<RatingAttributeMappings> ratingAttributeMappingsList = ratingAttributeMappingsRepository.findAll();
        assertThat(ratingAttributeMappingsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEffDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = ratingAttributeMappingsRepository.findAll().size();
        // set the field null
        ratingAttributeMappings.setEffDate(null);

        // Create the RatingAttributeMappings, which fails.

        restRatingAttributeMappingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingAttributeMappings))
            )
            .andExpect(status().isBadRequest());

        List<RatingAttributeMappings> ratingAttributeMappingsList = ratingAttributeMappingsRepository.findAll();
        assertThat(ratingAttributeMappingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = ratingAttributeMappingsRepository.findAll().size();
        // set the field null
        ratingAttributeMappings.setCreatedAt(null);

        // Create the RatingAttributeMappings, which fails.

        restRatingAttributeMappingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingAttributeMappings))
            )
            .andExpect(status().isBadRequest());

        List<RatingAttributeMappings> ratingAttributeMappingsList = ratingAttributeMappingsRepository.findAll();
        assertThat(ratingAttributeMappingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = ratingAttributeMappingsRepository.findAll().size();
        // set the field null
        ratingAttributeMappings.setUpdatedAt(null);

        // Create the RatingAttributeMappings, which fails.

        restRatingAttributeMappingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingAttributeMappings))
            )
            .andExpect(status().isBadRequest());

        List<RatingAttributeMappings> ratingAttributeMappingsList = ratingAttributeMappingsRepository.findAll();
        assertThat(ratingAttributeMappingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = ratingAttributeMappingsRepository.findAll().size();
        // set the field null
        ratingAttributeMappings.setVersion(null);

        // Create the RatingAttributeMappings, which fails.

        restRatingAttributeMappingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingAttributeMappings))
            )
            .andExpect(status().isBadRequest());

        List<RatingAttributeMappings> ratingAttributeMappingsList = ratingAttributeMappingsRepository.findAll();
        assertThat(ratingAttributeMappingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRatingAttributeMappings() throws Exception {
        // Initialize the database
        ratingAttributeMappingsRepository.saveAndFlush(ratingAttributeMappings);

        // Get all the ratingAttributeMappingsList
        restRatingAttributeMappingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ratingAttributeMappings.getId().intValue())))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getRatingAttributeMappings() throws Exception {
        // Initialize the database
        ratingAttributeMappingsRepository.saveAndFlush(ratingAttributeMappings);

        // Get the ratingAttributeMappings
        restRatingAttributeMappingsMockMvc
            .perform(get(ENTITY_API_URL_ID, ratingAttributeMappings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ratingAttributeMappings.getId().intValue()))
            .andExpect(jsonPath("$.effDate").value(DEFAULT_EFF_DATE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getRatingAttributeMappingsByIdFiltering() throws Exception {
        // Initialize the database
        ratingAttributeMappingsRepository.saveAndFlush(ratingAttributeMappings);

        Long id = ratingAttributeMappings.getId();

        defaultRatingAttributeMappingsShouldBeFound("id.equals=" + id);
        defaultRatingAttributeMappingsShouldNotBeFound("id.notEquals=" + id);

        defaultRatingAttributeMappingsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRatingAttributeMappingsShouldNotBeFound("id.greaterThan=" + id);

        defaultRatingAttributeMappingsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRatingAttributeMappingsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRatingAttributeMappingsByEffDateIsEqualToSomething() throws Exception {
        // Initialize the database
        ratingAttributeMappingsRepository.saveAndFlush(ratingAttributeMappings);

        // Get all the ratingAttributeMappingsList where effDate equals to DEFAULT_EFF_DATE
        defaultRatingAttributeMappingsShouldBeFound("effDate.equals=" + DEFAULT_EFF_DATE);

        // Get all the ratingAttributeMappingsList where effDate equals to UPDATED_EFF_DATE
        defaultRatingAttributeMappingsShouldNotBeFound("effDate.equals=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllRatingAttributeMappingsByEffDateIsInShouldWork() throws Exception {
        // Initialize the database
        ratingAttributeMappingsRepository.saveAndFlush(ratingAttributeMappings);

        // Get all the ratingAttributeMappingsList where effDate in DEFAULT_EFF_DATE or UPDATED_EFF_DATE
        defaultRatingAttributeMappingsShouldBeFound("effDate.in=" + DEFAULT_EFF_DATE + "," + UPDATED_EFF_DATE);

        // Get all the ratingAttributeMappingsList where effDate equals to UPDATED_EFF_DATE
        defaultRatingAttributeMappingsShouldNotBeFound("effDate.in=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllRatingAttributeMappingsByEffDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        ratingAttributeMappingsRepository.saveAndFlush(ratingAttributeMappings);

        // Get all the ratingAttributeMappingsList where effDate is not null
        defaultRatingAttributeMappingsShouldBeFound("effDate.specified=true");

        // Get all the ratingAttributeMappingsList where effDate is null
        defaultRatingAttributeMappingsShouldNotBeFound("effDate.specified=false");
    }

    @Test
    @Transactional
    void getAllRatingAttributeMappingsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        ratingAttributeMappingsRepository.saveAndFlush(ratingAttributeMappings);

        // Get all the ratingAttributeMappingsList where createdAt equals to DEFAULT_CREATED_AT
        defaultRatingAttributeMappingsShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the ratingAttributeMappingsList where createdAt equals to UPDATED_CREATED_AT
        defaultRatingAttributeMappingsShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllRatingAttributeMappingsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        ratingAttributeMappingsRepository.saveAndFlush(ratingAttributeMappings);

        // Get all the ratingAttributeMappingsList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultRatingAttributeMappingsShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the ratingAttributeMappingsList where createdAt equals to UPDATED_CREATED_AT
        defaultRatingAttributeMappingsShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllRatingAttributeMappingsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        ratingAttributeMappingsRepository.saveAndFlush(ratingAttributeMappings);

        // Get all the ratingAttributeMappingsList where createdAt is not null
        defaultRatingAttributeMappingsShouldBeFound("createdAt.specified=true");

        // Get all the ratingAttributeMappingsList where createdAt is null
        defaultRatingAttributeMappingsShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllRatingAttributeMappingsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        ratingAttributeMappingsRepository.saveAndFlush(ratingAttributeMappings);

        // Get all the ratingAttributeMappingsList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultRatingAttributeMappingsShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the ratingAttributeMappingsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultRatingAttributeMappingsShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllRatingAttributeMappingsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        ratingAttributeMappingsRepository.saveAndFlush(ratingAttributeMappings);

        // Get all the ratingAttributeMappingsList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultRatingAttributeMappingsShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the ratingAttributeMappingsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultRatingAttributeMappingsShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllRatingAttributeMappingsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        ratingAttributeMappingsRepository.saveAndFlush(ratingAttributeMappings);

        // Get all the ratingAttributeMappingsList where updatedAt is not null
        defaultRatingAttributeMappingsShouldBeFound("updatedAt.specified=true");

        // Get all the ratingAttributeMappingsList where updatedAt is null
        defaultRatingAttributeMappingsShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllRatingAttributeMappingsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        ratingAttributeMappingsRepository.saveAndFlush(ratingAttributeMappings);

        // Get all the ratingAttributeMappingsList where endDate equals to DEFAULT_END_DATE
        defaultRatingAttributeMappingsShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the ratingAttributeMappingsList where endDate equals to UPDATED_END_DATE
        defaultRatingAttributeMappingsShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllRatingAttributeMappingsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        ratingAttributeMappingsRepository.saveAndFlush(ratingAttributeMappings);

        // Get all the ratingAttributeMappingsList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultRatingAttributeMappingsShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the ratingAttributeMappingsList where endDate equals to UPDATED_END_DATE
        defaultRatingAttributeMappingsShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllRatingAttributeMappingsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        ratingAttributeMappingsRepository.saveAndFlush(ratingAttributeMappings);

        // Get all the ratingAttributeMappingsList where endDate is not null
        defaultRatingAttributeMappingsShouldBeFound("endDate.specified=true");

        // Get all the ratingAttributeMappingsList where endDate is null
        defaultRatingAttributeMappingsShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllRatingAttributeMappingsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        ratingAttributeMappingsRepository.saveAndFlush(ratingAttributeMappings);

        // Get all the ratingAttributeMappingsList where version equals to DEFAULT_VERSION
        defaultRatingAttributeMappingsShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the ratingAttributeMappingsList where version equals to UPDATED_VERSION
        defaultRatingAttributeMappingsShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllRatingAttributeMappingsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        ratingAttributeMappingsRepository.saveAndFlush(ratingAttributeMappings);

        // Get all the ratingAttributeMappingsList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultRatingAttributeMappingsShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the ratingAttributeMappingsList where version equals to UPDATED_VERSION
        defaultRatingAttributeMappingsShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllRatingAttributeMappingsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        ratingAttributeMappingsRepository.saveAndFlush(ratingAttributeMappings);

        // Get all the ratingAttributeMappingsList where version is not null
        defaultRatingAttributeMappingsShouldBeFound("version.specified=true");

        // Get all the ratingAttributeMappingsList where version is null
        defaultRatingAttributeMappingsShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllRatingAttributeMappingsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ratingAttributeMappingsRepository.saveAndFlush(ratingAttributeMappings);

        // Get all the ratingAttributeMappingsList where version is greater than or equal to DEFAULT_VERSION
        defaultRatingAttributeMappingsShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the ratingAttributeMappingsList where version is greater than or equal to UPDATED_VERSION
        defaultRatingAttributeMappingsShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllRatingAttributeMappingsByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ratingAttributeMappingsRepository.saveAndFlush(ratingAttributeMappings);

        // Get all the ratingAttributeMappingsList where version is less than or equal to DEFAULT_VERSION
        defaultRatingAttributeMappingsShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the ratingAttributeMappingsList where version is less than or equal to SMALLER_VERSION
        defaultRatingAttributeMappingsShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllRatingAttributeMappingsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        ratingAttributeMappingsRepository.saveAndFlush(ratingAttributeMappings);

        // Get all the ratingAttributeMappingsList where version is less than DEFAULT_VERSION
        defaultRatingAttributeMappingsShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the ratingAttributeMappingsList where version is less than UPDATED_VERSION
        defaultRatingAttributeMappingsShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllRatingAttributeMappingsByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ratingAttributeMappingsRepository.saveAndFlush(ratingAttributeMappings);

        // Get all the ratingAttributeMappingsList where version is greater than DEFAULT_VERSION
        defaultRatingAttributeMappingsShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the ratingAttributeMappingsList where version is greater than SMALLER_VERSION
        defaultRatingAttributeMappingsShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllRatingAttributeMappingsByAttributeIsEqualToSomething() throws Exception {
        Attributes attribute;
        if (TestUtil.findAll(em, Attributes.class).isEmpty()) {
            ratingAttributeMappingsRepository.saveAndFlush(ratingAttributeMappings);
            attribute = AttributesResourceIT.createEntity(em);
        } else {
            attribute = TestUtil.findAll(em, Attributes.class).get(0);
        }
        em.persist(attribute);
        em.flush();
        ratingAttributeMappings.setAttribute(attribute);
        ratingAttributeMappingsRepository.saveAndFlush(ratingAttributeMappings);
        Long attributeId = attribute.getId();

        // Get all the ratingAttributeMappingsList where attribute equals to attributeId
        defaultRatingAttributeMappingsShouldBeFound("attributeId.equals=" + attributeId);

        // Get all the ratingAttributeMappingsList where attribute equals to (attributeId + 1)
        defaultRatingAttributeMappingsShouldNotBeFound("attributeId.equals=" + (attributeId + 1));
    }

    @Test
    @Transactional
    void getAllRatingAttributeMappingsByRatingAttributeIsEqualToSomething() throws Exception {
        PcRatingAttributes ratingAttribute;
        if (TestUtil.findAll(em, PcRatingAttributes.class).isEmpty()) {
            ratingAttributeMappingsRepository.saveAndFlush(ratingAttributeMappings);
            ratingAttribute = PcRatingAttributesResourceIT.createEntity(em);
        } else {
            ratingAttribute = TestUtil.findAll(em, PcRatingAttributes.class).get(0);
        }
        em.persist(ratingAttribute);
        em.flush();
        ratingAttributeMappings.setRatingAttribute(ratingAttribute);
        ratingAttributeMappingsRepository.saveAndFlush(ratingAttributeMappings);
        Long ratingAttributeId = ratingAttribute.getId();

        // Get all the ratingAttributeMappingsList where ratingAttribute equals to ratingAttributeId
        defaultRatingAttributeMappingsShouldBeFound("ratingAttributeId.equals=" + ratingAttributeId);

        // Get all the ratingAttributeMappingsList where ratingAttribute equals to (ratingAttributeId + 1)
        defaultRatingAttributeMappingsShouldNotBeFound("ratingAttributeId.equals=" + (ratingAttributeId + 1));
    }

    @Test
    @Transactional
    void getAllRatingAttributeMappingsByPcraterattributesRatingattributemappingIsEqualToSomething() throws Exception {
        PcRaterAttributes pcraterattributesRatingattributemapping;
        if (TestUtil.findAll(em, PcRaterAttributes.class).isEmpty()) {
            ratingAttributeMappingsRepository.saveAndFlush(ratingAttributeMappings);
            pcraterattributesRatingattributemapping = PcRaterAttributesResourceIT.createEntity(em);
        } else {
            pcraterattributesRatingattributemapping = TestUtil.findAll(em, PcRaterAttributes.class).get(0);
        }
        em.persist(pcraterattributesRatingattributemapping);
        em.flush();
        ratingAttributeMappings.addPcraterattributesRatingattributemapping(pcraterattributesRatingattributemapping);
        ratingAttributeMappingsRepository.saveAndFlush(ratingAttributeMappings);
        Long pcraterattributesRatingattributemappingId = pcraterattributesRatingattributemapping.getId();

        // Get all the ratingAttributeMappingsList where pcraterattributesRatingattributemapping equals to pcraterattributesRatingattributemappingId
        defaultRatingAttributeMappingsShouldBeFound(
            "pcraterattributesRatingattributemappingId.equals=" + pcraterattributesRatingattributemappingId
        );

        // Get all the ratingAttributeMappingsList where pcraterattributesRatingattributemapping equals to (pcraterattributesRatingattributemappingId + 1)
        defaultRatingAttributeMappingsShouldNotBeFound(
            "pcraterattributesRatingattributemappingId.equals=" + (pcraterattributesRatingattributemappingId + 1)
        );
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRatingAttributeMappingsShouldBeFound(String filter) throws Exception {
        restRatingAttributeMappingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ratingAttributeMappings.getId().intValue())))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restRatingAttributeMappingsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRatingAttributeMappingsShouldNotBeFound(String filter) throws Exception {
        restRatingAttributeMappingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRatingAttributeMappingsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRatingAttributeMappings() throws Exception {
        // Get the ratingAttributeMappings
        restRatingAttributeMappingsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRatingAttributeMappings() throws Exception {
        // Initialize the database
        ratingAttributeMappingsRepository.saveAndFlush(ratingAttributeMappings);

        int databaseSizeBeforeUpdate = ratingAttributeMappingsRepository.findAll().size();

        // Update the ratingAttributeMappings
        RatingAttributeMappings updatedRatingAttributeMappings = ratingAttributeMappingsRepository
            .findById(ratingAttributeMappings.getId())
            .get();
        // Disconnect from session so that the updates on updatedRatingAttributeMappings are not directly saved in db
        em.detach(updatedRatingAttributeMappings);
        updatedRatingAttributeMappings
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restRatingAttributeMappingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRatingAttributeMappings.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRatingAttributeMappings))
            )
            .andExpect(status().isOk());

        // Validate the RatingAttributeMappings in the database
        List<RatingAttributeMappings> ratingAttributeMappingsList = ratingAttributeMappingsRepository.findAll();
        assertThat(ratingAttributeMappingsList).hasSize(databaseSizeBeforeUpdate);
        RatingAttributeMappings testRatingAttributeMappings = ratingAttributeMappingsList.get(ratingAttributeMappingsList.size() - 1);
        assertThat(testRatingAttributeMappings.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testRatingAttributeMappings.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testRatingAttributeMappings.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testRatingAttributeMappings.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testRatingAttributeMappings.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingRatingAttributeMappings() throws Exception {
        int databaseSizeBeforeUpdate = ratingAttributeMappingsRepository.findAll().size();
        ratingAttributeMappings.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRatingAttributeMappingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ratingAttributeMappings.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingAttributeMappings))
            )
            .andExpect(status().isBadRequest());

        // Validate the RatingAttributeMappings in the database
        List<RatingAttributeMappings> ratingAttributeMappingsList = ratingAttributeMappingsRepository.findAll();
        assertThat(ratingAttributeMappingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRatingAttributeMappings() throws Exception {
        int databaseSizeBeforeUpdate = ratingAttributeMappingsRepository.findAll().size();
        ratingAttributeMappings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatingAttributeMappingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingAttributeMappings))
            )
            .andExpect(status().isBadRequest());

        // Validate the RatingAttributeMappings in the database
        List<RatingAttributeMappings> ratingAttributeMappingsList = ratingAttributeMappingsRepository.findAll();
        assertThat(ratingAttributeMappingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRatingAttributeMappings() throws Exception {
        int databaseSizeBeforeUpdate = ratingAttributeMappingsRepository.findAll().size();
        ratingAttributeMappings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatingAttributeMappingsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingAttributeMappings))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RatingAttributeMappings in the database
        List<RatingAttributeMappings> ratingAttributeMappingsList = ratingAttributeMappingsRepository.findAll();
        assertThat(ratingAttributeMappingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRatingAttributeMappingsWithPatch() throws Exception {
        // Initialize the database
        ratingAttributeMappingsRepository.saveAndFlush(ratingAttributeMappings);

        int databaseSizeBeforeUpdate = ratingAttributeMappingsRepository.findAll().size();

        // Update the ratingAttributeMappings using partial update
        RatingAttributeMappings partialUpdatedRatingAttributeMappings = new RatingAttributeMappings();
        partialUpdatedRatingAttributeMappings.setId(ratingAttributeMappings.getId());

        partialUpdatedRatingAttributeMappings.effDate(UPDATED_EFF_DATE).createdAt(UPDATED_CREATED_AT).version(UPDATED_VERSION);

        restRatingAttributeMappingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRatingAttributeMappings.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRatingAttributeMappings))
            )
            .andExpect(status().isOk());

        // Validate the RatingAttributeMappings in the database
        List<RatingAttributeMappings> ratingAttributeMappingsList = ratingAttributeMappingsRepository.findAll();
        assertThat(ratingAttributeMappingsList).hasSize(databaseSizeBeforeUpdate);
        RatingAttributeMappings testRatingAttributeMappings = ratingAttributeMappingsList.get(ratingAttributeMappingsList.size() - 1);
        assertThat(testRatingAttributeMappings.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testRatingAttributeMappings.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testRatingAttributeMappings.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testRatingAttributeMappings.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testRatingAttributeMappings.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateRatingAttributeMappingsWithPatch() throws Exception {
        // Initialize the database
        ratingAttributeMappingsRepository.saveAndFlush(ratingAttributeMappings);

        int databaseSizeBeforeUpdate = ratingAttributeMappingsRepository.findAll().size();

        // Update the ratingAttributeMappings using partial update
        RatingAttributeMappings partialUpdatedRatingAttributeMappings = new RatingAttributeMappings();
        partialUpdatedRatingAttributeMappings.setId(ratingAttributeMappings.getId());

        partialUpdatedRatingAttributeMappings
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);

        restRatingAttributeMappingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRatingAttributeMappings.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRatingAttributeMappings))
            )
            .andExpect(status().isOk());

        // Validate the RatingAttributeMappings in the database
        List<RatingAttributeMappings> ratingAttributeMappingsList = ratingAttributeMappingsRepository.findAll();
        assertThat(ratingAttributeMappingsList).hasSize(databaseSizeBeforeUpdate);
        RatingAttributeMappings testRatingAttributeMappings = ratingAttributeMappingsList.get(ratingAttributeMappingsList.size() - 1);
        assertThat(testRatingAttributeMappings.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testRatingAttributeMappings.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testRatingAttributeMappings.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testRatingAttributeMappings.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testRatingAttributeMappings.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingRatingAttributeMappings() throws Exception {
        int databaseSizeBeforeUpdate = ratingAttributeMappingsRepository.findAll().size();
        ratingAttributeMappings.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRatingAttributeMappingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ratingAttributeMappings.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ratingAttributeMappings))
            )
            .andExpect(status().isBadRequest());

        // Validate the RatingAttributeMappings in the database
        List<RatingAttributeMappings> ratingAttributeMappingsList = ratingAttributeMappingsRepository.findAll();
        assertThat(ratingAttributeMappingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRatingAttributeMappings() throws Exception {
        int databaseSizeBeforeUpdate = ratingAttributeMappingsRepository.findAll().size();
        ratingAttributeMappings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatingAttributeMappingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ratingAttributeMappings))
            )
            .andExpect(status().isBadRequest());

        // Validate the RatingAttributeMappings in the database
        List<RatingAttributeMappings> ratingAttributeMappingsList = ratingAttributeMappingsRepository.findAll();
        assertThat(ratingAttributeMappingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRatingAttributeMappings() throws Exception {
        int databaseSizeBeforeUpdate = ratingAttributeMappingsRepository.findAll().size();
        ratingAttributeMappings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatingAttributeMappingsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ratingAttributeMappings))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RatingAttributeMappings in the database
        List<RatingAttributeMappings> ratingAttributeMappingsList = ratingAttributeMappingsRepository.findAll();
        assertThat(ratingAttributeMappingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRatingAttributeMappings() throws Exception {
        // Initialize the database
        ratingAttributeMappingsRepository.saveAndFlush(ratingAttributeMappings);

        int databaseSizeBeforeDelete = ratingAttributeMappingsRepository.findAll().size();

        // Delete the ratingAttributeMappings
        restRatingAttributeMappingsMockMvc
            .perform(delete(ENTITY_API_URL_ID, ratingAttributeMappings.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RatingAttributeMappings> ratingAttributeMappingsList = ratingAttributeMappingsRepository.findAll();
        assertThat(ratingAttributeMappingsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
