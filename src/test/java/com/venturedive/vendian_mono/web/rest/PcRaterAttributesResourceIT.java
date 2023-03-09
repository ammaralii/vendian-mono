package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.PcRaterAttributes;
import com.venturedive.vendian_mono.domain.PcRatings;
import com.venturedive.vendian_mono.domain.RatingAttributeMappings;
import com.venturedive.vendian_mono.domain.RatingOptions;
import com.venturedive.vendian_mono.repository.PcRaterAttributesRepository;
import com.venturedive.vendian_mono.service.criteria.PcRaterAttributesCriteria;
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
 * Integration tests for the {@link PcRaterAttributesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PcRaterAttributesResourceIT {

    private static final byte[] DEFAULT_PC_RATING = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PC_RATING = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PC_RATING_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PC_RATING_CONTENT_TYPE = "image/png";

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

    private static final String ENTITY_API_URL = "/api/pc-rater-attributes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PcRaterAttributesRepository pcRaterAttributesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPcRaterAttributesMockMvc;

    private PcRaterAttributes pcRaterAttributes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PcRaterAttributes createEntity(EntityManager em) {
        PcRaterAttributes pcRaterAttributes = new PcRaterAttributes()
            .pcRating(DEFAULT_PC_RATING)
            .pcRatingContentType(DEFAULT_PC_RATING_CONTENT_TYPE)
            .comment(DEFAULT_COMMENT)
            .commentContentType(DEFAULT_COMMENT_CONTENT_TYPE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT)
            .version(DEFAULT_VERSION);
        // Add required entity
        RatingAttributeMappings ratingAttributeMappings;
        if (TestUtil.findAll(em, RatingAttributeMappings.class).isEmpty()) {
            ratingAttributeMappings = RatingAttributeMappingsResourceIT.createEntity(em);
            em.persist(ratingAttributeMappings);
            em.flush();
        } else {
            ratingAttributeMappings = TestUtil.findAll(em, RatingAttributeMappings.class).get(0);
        }
        pcRaterAttributes.setRatingAttributeMapping(ratingAttributeMappings);
        // Add required entity
        PcRatings pcRatings;
        if (TestUtil.findAll(em, PcRatings.class).isEmpty()) {
            pcRatings = PcRatingsResourceIT.createEntity(em);
            em.persist(pcRatings);
            em.flush();
        } else {
            pcRatings = TestUtil.findAll(em, PcRatings.class).get(0);
        }
        pcRaterAttributes.setRating(pcRatings);
        return pcRaterAttributes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PcRaterAttributes createUpdatedEntity(EntityManager em) {
        PcRaterAttributes pcRaterAttributes = new PcRaterAttributes()
            .pcRating(UPDATED_PC_RATING)
            .pcRatingContentType(UPDATED_PC_RATING_CONTENT_TYPE)
            .comment(UPDATED_COMMENT)
            .commentContentType(UPDATED_COMMENT_CONTENT_TYPE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);
        // Add required entity
        RatingAttributeMappings ratingAttributeMappings;
        if (TestUtil.findAll(em, RatingAttributeMappings.class).isEmpty()) {
            ratingAttributeMappings = RatingAttributeMappingsResourceIT.createUpdatedEntity(em);
            em.persist(ratingAttributeMappings);
            em.flush();
        } else {
            ratingAttributeMappings = TestUtil.findAll(em, RatingAttributeMappings.class).get(0);
        }
        pcRaterAttributes.setRatingAttributeMapping(ratingAttributeMappings);
        // Add required entity
        PcRatings pcRatings;
        if (TestUtil.findAll(em, PcRatings.class).isEmpty()) {
            pcRatings = PcRatingsResourceIT.createUpdatedEntity(em);
            em.persist(pcRatings);
            em.flush();
        } else {
            pcRatings = TestUtil.findAll(em, PcRatings.class).get(0);
        }
        pcRaterAttributes.setRating(pcRatings);
        return pcRaterAttributes;
    }

    @BeforeEach
    public void initTest() {
        pcRaterAttributes = createEntity(em);
    }

    @Test
    @Transactional
    void createPcRaterAttributes() throws Exception {
        int databaseSizeBeforeCreate = pcRaterAttributesRepository.findAll().size();
        // Create the PcRaterAttributes
        restPcRaterAttributesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRaterAttributes))
            )
            .andExpect(status().isCreated());

        // Validate the PcRaterAttributes in the database
        List<PcRaterAttributes> pcRaterAttributesList = pcRaterAttributesRepository.findAll();
        assertThat(pcRaterAttributesList).hasSize(databaseSizeBeforeCreate + 1);
        PcRaterAttributes testPcRaterAttributes = pcRaterAttributesList.get(pcRaterAttributesList.size() - 1);
        assertThat(testPcRaterAttributes.getPcRating()).isEqualTo(DEFAULT_PC_RATING);
        assertThat(testPcRaterAttributes.getPcRatingContentType()).isEqualTo(DEFAULT_PC_RATING_CONTENT_TYPE);
        assertThat(testPcRaterAttributes.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testPcRaterAttributes.getCommentContentType()).isEqualTo(DEFAULT_COMMENT_CONTENT_TYPE);
        assertThat(testPcRaterAttributes.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testPcRaterAttributes.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testPcRaterAttributes.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testPcRaterAttributes.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createPcRaterAttributesWithExistingId() throws Exception {
        // Create the PcRaterAttributes with an existing ID
        pcRaterAttributes.setId(1L);

        int databaseSizeBeforeCreate = pcRaterAttributesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPcRaterAttributesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRaterAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the PcRaterAttributes in the database
        List<PcRaterAttributes> pcRaterAttributesList = pcRaterAttributesRepository.findAll();
        assertThat(pcRaterAttributesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = pcRaterAttributesRepository.findAll().size();
        // set the field null
        pcRaterAttributes.setCreatedAt(null);

        // Create the PcRaterAttributes, which fails.

        restPcRaterAttributesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRaterAttributes))
            )
            .andExpect(status().isBadRequest());

        List<PcRaterAttributes> pcRaterAttributesList = pcRaterAttributesRepository.findAll();
        assertThat(pcRaterAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = pcRaterAttributesRepository.findAll().size();
        // set the field null
        pcRaterAttributes.setUpdatedAt(null);

        // Create the PcRaterAttributes, which fails.

        restPcRaterAttributesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRaterAttributes))
            )
            .andExpect(status().isBadRequest());

        List<PcRaterAttributes> pcRaterAttributesList = pcRaterAttributesRepository.findAll();
        assertThat(pcRaterAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = pcRaterAttributesRepository.findAll().size();
        // set the field null
        pcRaterAttributes.setVersion(null);

        // Create the PcRaterAttributes, which fails.

        restPcRaterAttributesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRaterAttributes))
            )
            .andExpect(status().isBadRequest());

        List<PcRaterAttributes> pcRaterAttributesList = pcRaterAttributesRepository.findAll();
        assertThat(pcRaterAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPcRaterAttributes() throws Exception {
        // Initialize the database
        pcRaterAttributesRepository.saveAndFlush(pcRaterAttributes);

        // Get all the pcRaterAttributesList
        restPcRaterAttributesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pcRaterAttributes.getId().intValue())))
            .andExpect(jsonPath("$.[*].pcRatingContentType").value(hasItem(DEFAULT_PC_RATING_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pcRating").value(hasItem(Base64Utils.encodeToString(DEFAULT_PC_RATING))))
            .andExpect(jsonPath("$.[*].commentContentType").value(hasItem(DEFAULT_COMMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(Base64Utils.encodeToString(DEFAULT_COMMENT))))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getPcRaterAttributes() throws Exception {
        // Initialize the database
        pcRaterAttributesRepository.saveAndFlush(pcRaterAttributes);

        // Get the pcRaterAttributes
        restPcRaterAttributesMockMvc
            .perform(get(ENTITY_API_URL_ID, pcRaterAttributes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pcRaterAttributes.getId().intValue()))
            .andExpect(jsonPath("$.pcRatingContentType").value(DEFAULT_PC_RATING_CONTENT_TYPE))
            .andExpect(jsonPath("$.pcRating").value(Base64Utils.encodeToString(DEFAULT_PC_RATING)))
            .andExpect(jsonPath("$.commentContentType").value(DEFAULT_COMMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.comment").value(Base64Utils.encodeToString(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getPcRaterAttributesByIdFiltering() throws Exception {
        // Initialize the database
        pcRaterAttributesRepository.saveAndFlush(pcRaterAttributes);

        Long id = pcRaterAttributes.getId();

        defaultPcRaterAttributesShouldBeFound("id.equals=" + id);
        defaultPcRaterAttributesShouldNotBeFound("id.notEquals=" + id);

        defaultPcRaterAttributesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPcRaterAttributesShouldNotBeFound("id.greaterThan=" + id);

        defaultPcRaterAttributesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPcRaterAttributesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPcRaterAttributesByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        pcRaterAttributesRepository.saveAndFlush(pcRaterAttributes);

        // Get all the pcRaterAttributesList where createdAt equals to DEFAULT_CREATED_AT
        defaultPcRaterAttributesShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the pcRaterAttributesList where createdAt equals to UPDATED_CREATED_AT
        defaultPcRaterAttributesShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllPcRaterAttributesByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        pcRaterAttributesRepository.saveAndFlush(pcRaterAttributes);

        // Get all the pcRaterAttributesList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultPcRaterAttributesShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the pcRaterAttributesList where createdAt equals to UPDATED_CREATED_AT
        defaultPcRaterAttributesShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllPcRaterAttributesByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcRaterAttributesRepository.saveAndFlush(pcRaterAttributes);

        // Get all the pcRaterAttributesList where createdAt is not null
        defaultPcRaterAttributesShouldBeFound("createdAt.specified=true");

        // Get all the pcRaterAttributesList where createdAt is null
        defaultPcRaterAttributesShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllPcRaterAttributesByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        pcRaterAttributesRepository.saveAndFlush(pcRaterAttributes);

        // Get all the pcRaterAttributesList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultPcRaterAttributesShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the pcRaterAttributesList where updatedAt equals to UPDATED_UPDATED_AT
        defaultPcRaterAttributesShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllPcRaterAttributesByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        pcRaterAttributesRepository.saveAndFlush(pcRaterAttributes);

        // Get all the pcRaterAttributesList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultPcRaterAttributesShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the pcRaterAttributesList where updatedAt equals to UPDATED_UPDATED_AT
        defaultPcRaterAttributesShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllPcRaterAttributesByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcRaterAttributesRepository.saveAndFlush(pcRaterAttributes);

        // Get all the pcRaterAttributesList where updatedAt is not null
        defaultPcRaterAttributesShouldBeFound("updatedAt.specified=true");

        // Get all the pcRaterAttributesList where updatedAt is null
        defaultPcRaterAttributesShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllPcRaterAttributesByDeletedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        pcRaterAttributesRepository.saveAndFlush(pcRaterAttributes);

        // Get all the pcRaterAttributesList where deletedAt equals to DEFAULT_DELETED_AT
        defaultPcRaterAttributesShouldBeFound("deletedAt.equals=" + DEFAULT_DELETED_AT);

        // Get all the pcRaterAttributesList where deletedAt equals to UPDATED_DELETED_AT
        defaultPcRaterAttributesShouldNotBeFound("deletedAt.equals=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllPcRaterAttributesByDeletedAtIsInShouldWork() throws Exception {
        // Initialize the database
        pcRaterAttributesRepository.saveAndFlush(pcRaterAttributes);

        // Get all the pcRaterAttributesList where deletedAt in DEFAULT_DELETED_AT or UPDATED_DELETED_AT
        defaultPcRaterAttributesShouldBeFound("deletedAt.in=" + DEFAULT_DELETED_AT + "," + UPDATED_DELETED_AT);

        // Get all the pcRaterAttributesList where deletedAt equals to UPDATED_DELETED_AT
        defaultPcRaterAttributesShouldNotBeFound("deletedAt.in=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllPcRaterAttributesByDeletedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcRaterAttributesRepository.saveAndFlush(pcRaterAttributes);

        // Get all the pcRaterAttributesList where deletedAt is not null
        defaultPcRaterAttributesShouldBeFound("deletedAt.specified=true");

        // Get all the pcRaterAttributesList where deletedAt is null
        defaultPcRaterAttributesShouldNotBeFound("deletedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllPcRaterAttributesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        pcRaterAttributesRepository.saveAndFlush(pcRaterAttributes);

        // Get all the pcRaterAttributesList where version equals to DEFAULT_VERSION
        defaultPcRaterAttributesShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the pcRaterAttributesList where version equals to UPDATED_VERSION
        defaultPcRaterAttributesShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllPcRaterAttributesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        pcRaterAttributesRepository.saveAndFlush(pcRaterAttributes);

        // Get all the pcRaterAttributesList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultPcRaterAttributesShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the pcRaterAttributesList where version equals to UPDATED_VERSION
        defaultPcRaterAttributesShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllPcRaterAttributesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcRaterAttributesRepository.saveAndFlush(pcRaterAttributes);

        // Get all the pcRaterAttributesList where version is not null
        defaultPcRaterAttributesShouldBeFound("version.specified=true");

        // Get all the pcRaterAttributesList where version is null
        defaultPcRaterAttributesShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllPcRaterAttributesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pcRaterAttributesRepository.saveAndFlush(pcRaterAttributes);

        // Get all the pcRaterAttributesList where version is greater than or equal to DEFAULT_VERSION
        defaultPcRaterAttributesShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the pcRaterAttributesList where version is greater than or equal to UPDATED_VERSION
        defaultPcRaterAttributesShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllPcRaterAttributesByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pcRaterAttributesRepository.saveAndFlush(pcRaterAttributes);

        // Get all the pcRaterAttributesList where version is less than or equal to DEFAULT_VERSION
        defaultPcRaterAttributesShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the pcRaterAttributesList where version is less than or equal to SMALLER_VERSION
        defaultPcRaterAttributesShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllPcRaterAttributesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        pcRaterAttributesRepository.saveAndFlush(pcRaterAttributes);

        // Get all the pcRaterAttributesList where version is less than DEFAULT_VERSION
        defaultPcRaterAttributesShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the pcRaterAttributesList where version is less than UPDATED_VERSION
        defaultPcRaterAttributesShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllPcRaterAttributesByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pcRaterAttributesRepository.saveAndFlush(pcRaterAttributes);

        // Get all the pcRaterAttributesList where version is greater than DEFAULT_VERSION
        defaultPcRaterAttributesShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the pcRaterAttributesList where version is greater than SMALLER_VERSION
        defaultPcRaterAttributesShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllPcRaterAttributesByRatingAttributeMappingIsEqualToSomething() throws Exception {
        RatingAttributeMappings ratingAttributeMapping;
        if (TestUtil.findAll(em, RatingAttributeMappings.class).isEmpty()) {
            pcRaterAttributesRepository.saveAndFlush(pcRaterAttributes);
            ratingAttributeMapping = RatingAttributeMappingsResourceIT.createEntity(em);
        } else {
            ratingAttributeMapping = TestUtil.findAll(em, RatingAttributeMappings.class).get(0);
        }
        em.persist(ratingAttributeMapping);
        em.flush();
        pcRaterAttributes.setRatingAttributeMapping(ratingAttributeMapping);
        pcRaterAttributesRepository.saveAndFlush(pcRaterAttributes);
        Long ratingAttributeMappingId = ratingAttributeMapping.getId();

        // Get all the pcRaterAttributesList where ratingAttributeMapping equals to ratingAttributeMappingId
        defaultPcRaterAttributesShouldBeFound("ratingAttributeMappingId.equals=" + ratingAttributeMappingId);

        // Get all the pcRaterAttributesList where ratingAttributeMapping equals to (ratingAttributeMappingId + 1)
        defaultPcRaterAttributesShouldNotBeFound("ratingAttributeMappingId.equals=" + (ratingAttributeMappingId + 1));
    }

    @Test
    @Transactional
    void getAllPcRaterAttributesByRatingOptionIsEqualToSomething() throws Exception {
        RatingOptions ratingOption;
        if (TestUtil.findAll(em, RatingOptions.class).isEmpty()) {
            pcRaterAttributesRepository.saveAndFlush(pcRaterAttributes);
            ratingOption = RatingOptionsResourceIT.createEntity(em);
        } else {
            ratingOption = TestUtil.findAll(em, RatingOptions.class).get(0);
        }
        em.persist(ratingOption);
        em.flush();
        pcRaterAttributes.setRatingOption(ratingOption);
        pcRaterAttributesRepository.saveAndFlush(pcRaterAttributes);
        Long ratingOptionId = ratingOption.getId();

        // Get all the pcRaterAttributesList where ratingOption equals to ratingOptionId
        defaultPcRaterAttributesShouldBeFound("ratingOptionId.equals=" + ratingOptionId);

        // Get all the pcRaterAttributesList where ratingOption equals to (ratingOptionId + 1)
        defaultPcRaterAttributesShouldNotBeFound("ratingOptionId.equals=" + (ratingOptionId + 1));
    }

    @Test
    @Transactional
    void getAllPcRaterAttributesByRatingIsEqualToSomething() throws Exception {
        PcRatings rating;
        if (TestUtil.findAll(em, PcRatings.class).isEmpty()) {
            pcRaterAttributesRepository.saveAndFlush(pcRaterAttributes);
            rating = PcRatingsResourceIT.createEntity(em);
        } else {
            rating = TestUtil.findAll(em, PcRatings.class).get(0);
        }
        em.persist(rating);
        em.flush();
        pcRaterAttributes.setRating(rating);
        pcRaterAttributesRepository.saveAndFlush(pcRaterAttributes);
        Long ratingId = rating.getId();

        // Get all the pcRaterAttributesList where rating equals to ratingId
        defaultPcRaterAttributesShouldBeFound("ratingId.equals=" + ratingId);

        // Get all the pcRaterAttributesList where rating equals to (ratingId + 1)
        defaultPcRaterAttributesShouldNotBeFound("ratingId.equals=" + (ratingId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPcRaterAttributesShouldBeFound(String filter) throws Exception {
        restPcRaterAttributesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pcRaterAttributes.getId().intValue())))
            .andExpect(jsonPath("$.[*].pcRatingContentType").value(hasItem(DEFAULT_PC_RATING_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pcRating").value(hasItem(Base64Utils.encodeToString(DEFAULT_PC_RATING))))
            .andExpect(jsonPath("$.[*].commentContentType").value(hasItem(DEFAULT_COMMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(Base64Utils.encodeToString(DEFAULT_COMMENT))))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restPcRaterAttributesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPcRaterAttributesShouldNotBeFound(String filter) throws Exception {
        restPcRaterAttributesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPcRaterAttributesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPcRaterAttributes() throws Exception {
        // Get the pcRaterAttributes
        restPcRaterAttributesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPcRaterAttributes() throws Exception {
        // Initialize the database
        pcRaterAttributesRepository.saveAndFlush(pcRaterAttributes);

        int databaseSizeBeforeUpdate = pcRaterAttributesRepository.findAll().size();

        // Update the pcRaterAttributes
        PcRaterAttributes updatedPcRaterAttributes = pcRaterAttributesRepository.findById(pcRaterAttributes.getId()).get();
        // Disconnect from session so that the updates on updatedPcRaterAttributes are not directly saved in db
        em.detach(updatedPcRaterAttributes);
        updatedPcRaterAttributes
            .pcRating(UPDATED_PC_RATING)
            .pcRatingContentType(UPDATED_PC_RATING_CONTENT_TYPE)
            .comment(UPDATED_COMMENT)
            .commentContentType(UPDATED_COMMENT_CONTENT_TYPE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restPcRaterAttributesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPcRaterAttributes.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPcRaterAttributes))
            )
            .andExpect(status().isOk());

        // Validate the PcRaterAttributes in the database
        List<PcRaterAttributes> pcRaterAttributesList = pcRaterAttributesRepository.findAll();
        assertThat(pcRaterAttributesList).hasSize(databaseSizeBeforeUpdate);
        PcRaterAttributes testPcRaterAttributes = pcRaterAttributesList.get(pcRaterAttributesList.size() - 1);
        assertThat(testPcRaterAttributes.getPcRating()).isEqualTo(UPDATED_PC_RATING);
        assertThat(testPcRaterAttributes.getPcRatingContentType()).isEqualTo(UPDATED_PC_RATING_CONTENT_TYPE);
        assertThat(testPcRaterAttributes.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testPcRaterAttributes.getCommentContentType()).isEqualTo(UPDATED_COMMENT_CONTENT_TYPE);
        assertThat(testPcRaterAttributes.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPcRaterAttributes.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testPcRaterAttributes.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testPcRaterAttributes.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingPcRaterAttributes() throws Exception {
        int databaseSizeBeforeUpdate = pcRaterAttributesRepository.findAll().size();
        pcRaterAttributes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPcRaterAttributesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pcRaterAttributes.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRaterAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the PcRaterAttributes in the database
        List<PcRaterAttributes> pcRaterAttributesList = pcRaterAttributesRepository.findAll();
        assertThat(pcRaterAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPcRaterAttributes() throws Exception {
        int databaseSizeBeforeUpdate = pcRaterAttributesRepository.findAll().size();
        pcRaterAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPcRaterAttributesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRaterAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the PcRaterAttributes in the database
        List<PcRaterAttributes> pcRaterAttributesList = pcRaterAttributesRepository.findAll();
        assertThat(pcRaterAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPcRaterAttributes() throws Exception {
        int databaseSizeBeforeUpdate = pcRaterAttributesRepository.findAll().size();
        pcRaterAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPcRaterAttributesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRaterAttributes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PcRaterAttributes in the database
        List<PcRaterAttributes> pcRaterAttributesList = pcRaterAttributesRepository.findAll();
        assertThat(pcRaterAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePcRaterAttributesWithPatch() throws Exception {
        // Initialize the database
        pcRaterAttributesRepository.saveAndFlush(pcRaterAttributes);

        int databaseSizeBeforeUpdate = pcRaterAttributesRepository.findAll().size();

        // Update the pcRaterAttributes using partial update
        PcRaterAttributes partialUpdatedPcRaterAttributes = new PcRaterAttributes();
        partialUpdatedPcRaterAttributes.setId(pcRaterAttributes.getId());

        partialUpdatedPcRaterAttributes.pcRating(UPDATED_PC_RATING).pcRatingContentType(UPDATED_PC_RATING_CONTENT_TYPE);

        restPcRaterAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPcRaterAttributes.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPcRaterAttributes))
            )
            .andExpect(status().isOk());

        // Validate the PcRaterAttributes in the database
        List<PcRaterAttributes> pcRaterAttributesList = pcRaterAttributesRepository.findAll();
        assertThat(pcRaterAttributesList).hasSize(databaseSizeBeforeUpdate);
        PcRaterAttributes testPcRaterAttributes = pcRaterAttributesList.get(pcRaterAttributesList.size() - 1);
        assertThat(testPcRaterAttributes.getPcRating()).isEqualTo(UPDATED_PC_RATING);
        assertThat(testPcRaterAttributes.getPcRatingContentType()).isEqualTo(UPDATED_PC_RATING_CONTENT_TYPE);
        assertThat(testPcRaterAttributes.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testPcRaterAttributes.getCommentContentType()).isEqualTo(DEFAULT_COMMENT_CONTENT_TYPE);
        assertThat(testPcRaterAttributes.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testPcRaterAttributes.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testPcRaterAttributes.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testPcRaterAttributes.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void fullUpdatePcRaterAttributesWithPatch() throws Exception {
        // Initialize the database
        pcRaterAttributesRepository.saveAndFlush(pcRaterAttributes);

        int databaseSizeBeforeUpdate = pcRaterAttributesRepository.findAll().size();

        // Update the pcRaterAttributes using partial update
        PcRaterAttributes partialUpdatedPcRaterAttributes = new PcRaterAttributes();
        partialUpdatedPcRaterAttributes.setId(pcRaterAttributes.getId());

        partialUpdatedPcRaterAttributes
            .pcRating(UPDATED_PC_RATING)
            .pcRatingContentType(UPDATED_PC_RATING_CONTENT_TYPE)
            .comment(UPDATED_COMMENT)
            .commentContentType(UPDATED_COMMENT_CONTENT_TYPE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restPcRaterAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPcRaterAttributes.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPcRaterAttributes))
            )
            .andExpect(status().isOk());

        // Validate the PcRaterAttributes in the database
        List<PcRaterAttributes> pcRaterAttributesList = pcRaterAttributesRepository.findAll();
        assertThat(pcRaterAttributesList).hasSize(databaseSizeBeforeUpdate);
        PcRaterAttributes testPcRaterAttributes = pcRaterAttributesList.get(pcRaterAttributesList.size() - 1);
        assertThat(testPcRaterAttributes.getPcRating()).isEqualTo(UPDATED_PC_RATING);
        assertThat(testPcRaterAttributes.getPcRatingContentType()).isEqualTo(UPDATED_PC_RATING_CONTENT_TYPE);
        assertThat(testPcRaterAttributes.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testPcRaterAttributes.getCommentContentType()).isEqualTo(UPDATED_COMMENT_CONTENT_TYPE);
        assertThat(testPcRaterAttributes.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPcRaterAttributes.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testPcRaterAttributes.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testPcRaterAttributes.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingPcRaterAttributes() throws Exception {
        int databaseSizeBeforeUpdate = pcRaterAttributesRepository.findAll().size();
        pcRaterAttributes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPcRaterAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pcRaterAttributes.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pcRaterAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the PcRaterAttributes in the database
        List<PcRaterAttributes> pcRaterAttributesList = pcRaterAttributesRepository.findAll();
        assertThat(pcRaterAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPcRaterAttributes() throws Exception {
        int databaseSizeBeforeUpdate = pcRaterAttributesRepository.findAll().size();
        pcRaterAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPcRaterAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pcRaterAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the PcRaterAttributes in the database
        List<PcRaterAttributes> pcRaterAttributesList = pcRaterAttributesRepository.findAll();
        assertThat(pcRaterAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPcRaterAttributes() throws Exception {
        int databaseSizeBeforeUpdate = pcRaterAttributesRepository.findAll().size();
        pcRaterAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPcRaterAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pcRaterAttributes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PcRaterAttributes in the database
        List<PcRaterAttributes> pcRaterAttributesList = pcRaterAttributesRepository.findAll();
        assertThat(pcRaterAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePcRaterAttributes() throws Exception {
        // Initialize the database
        pcRaterAttributesRepository.saveAndFlush(pcRaterAttributes);

        int databaseSizeBeforeDelete = pcRaterAttributesRepository.findAll().size();

        // Delete the pcRaterAttributes
        restPcRaterAttributesMockMvc
            .perform(delete(ENTITY_API_URL_ID, pcRaterAttributes.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PcRaterAttributes> pcRaterAttributesList = pcRaterAttributesRepository.findAll();
        assertThat(pcRaterAttributesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
