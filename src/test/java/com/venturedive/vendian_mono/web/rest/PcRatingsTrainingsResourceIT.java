package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.PcRatings;
import com.venturedive.vendian_mono.domain.PcRatingsTrainings;
import com.venturedive.vendian_mono.repository.PcRatingsTrainingsRepository;
import com.venturedive.vendian_mono.service.criteria.PcRatingsTrainingsCriteria;
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
 * Integration tests for the {@link PcRatingsTrainingsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PcRatingsTrainingsResourceIT {

    private static final String DEFAULT_STRENGTH = "AAAAAAAAAA";
    private static final String UPDATED_STRENGTH = "BBBBBBBBBB";

    private static final String DEFAULT_DEVELOPMENT_AREA = "AAAAAAAAAA";
    private static final String UPDATED_DEVELOPMENT_AREA = "BBBBBBBBBB";

    private static final String DEFAULT_CAREER_AMBITION = "AAAAAAAAAA";
    private static final String UPDATED_CAREER_AMBITION = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_COURSE = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_COURSE = "BBBBBBBBBB";

    private static final String DEFAULT_TECHNICAL_TRAINING = "AAAAAAAAAA";
    private static final String UPDATED_TECHNICAL_TRAINING = "BBBBBBBBBB";

    private static final String DEFAULT_SOFT_SKILLS_TRAINING = "AAAAAAAAAA";
    private static final String UPDATED_SOFT_SKILLS_TRAINING = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CRITICAL_POSITION = false;
    private static final Boolean UPDATED_CRITICAL_POSITION = true;

    private static final Boolean DEFAULT_HIGH_POTENTIAL = false;
    private static final Boolean UPDATED_HIGH_POTENTIAL = true;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;
    private static final Integer SMALLER_VERSION = 1 - 1;

    private static final String ENTITY_API_URL = "/api/pc-ratings-trainings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PcRatingsTrainingsRepository pcRatingsTrainingsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPcRatingsTrainingsMockMvc;

    private PcRatingsTrainings pcRatingsTrainings;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PcRatingsTrainings createEntity(EntityManager em) {
        PcRatingsTrainings pcRatingsTrainings = new PcRatingsTrainings()
            .strength(DEFAULT_STRENGTH)
            .developmentArea(DEFAULT_DEVELOPMENT_AREA)
            .careerAmbition(DEFAULT_CAREER_AMBITION)
            .shortCourse(DEFAULT_SHORT_COURSE)
            .technicalTraining(DEFAULT_TECHNICAL_TRAINING)
            .softSkillsTraining(DEFAULT_SOFT_SKILLS_TRAINING)
            .criticalPosition(DEFAULT_CRITICAL_POSITION)
            .highPotential(DEFAULT_HIGH_POTENTIAL)
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
        pcRatingsTrainings.setRating(pcRatings);
        return pcRatingsTrainings;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PcRatingsTrainings createUpdatedEntity(EntityManager em) {
        PcRatingsTrainings pcRatingsTrainings = new PcRatingsTrainings()
            .strength(UPDATED_STRENGTH)
            .developmentArea(UPDATED_DEVELOPMENT_AREA)
            .careerAmbition(UPDATED_CAREER_AMBITION)
            .shortCourse(UPDATED_SHORT_COURSE)
            .technicalTraining(UPDATED_TECHNICAL_TRAINING)
            .softSkillsTraining(UPDATED_SOFT_SKILLS_TRAINING)
            .criticalPosition(UPDATED_CRITICAL_POSITION)
            .highPotential(UPDATED_HIGH_POTENTIAL)
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
        pcRatingsTrainings.setRating(pcRatings);
        return pcRatingsTrainings;
    }

    @BeforeEach
    public void initTest() {
        pcRatingsTrainings = createEntity(em);
    }

    @Test
    @Transactional
    void createPcRatingsTrainings() throws Exception {
        int databaseSizeBeforeCreate = pcRatingsTrainingsRepository.findAll().size();
        // Create the PcRatingsTrainings
        restPcRatingsTrainingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRatingsTrainings))
            )
            .andExpect(status().isCreated());

        // Validate the PcRatingsTrainings in the database
        List<PcRatingsTrainings> pcRatingsTrainingsList = pcRatingsTrainingsRepository.findAll();
        assertThat(pcRatingsTrainingsList).hasSize(databaseSizeBeforeCreate + 1);
        PcRatingsTrainings testPcRatingsTrainings = pcRatingsTrainingsList.get(pcRatingsTrainingsList.size() - 1);
        assertThat(testPcRatingsTrainings.getStrength()).isEqualTo(DEFAULT_STRENGTH);
        assertThat(testPcRatingsTrainings.getDevelopmentArea()).isEqualTo(DEFAULT_DEVELOPMENT_AREA);
        assertThat(testPcRatingsTrainings.getCareerAmbition()).isEqualTo(DEFAULT_CAREER_AMBITION);
        assertThat(testPcRatingsTrainings.getShortCourse()).isEqualTo(DEFAULT_SHORT_COURSE);
        assertThat(testPcRatingsTrainings.getTechnicalTraining()).isEqualTo(DEFAULT_TECHNICAL_TRAINING);
        assertThat(testPcRatingsTrainings.getSoftSkillsTraining()).isEqualTo(DEFAULT_SOFT_SKILLS_TRAINING);
        assertThat(testPcRatingsTrainings.getCriticalPosition()).isEqualTo(DEFAULT_CRITICAL_POSITION);
        assertThat(testPcRatingsTrainings.getHighPotential()).isEqualTo(DEFAULT_HIGH_POTENTIAL);
        assertThat(testPcRatingsTrainings.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testPcRatingsTrainings.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testPcRatingsTrainings.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testPcRatingsTrainings.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createPcRatingsTrainingsWithExistingId() throws Exception {
        // Create the PcRatingsTrainings with an existing ID
        pcRatingsTrainings.setId(1L);

        int databaseSizeBeforeCreate = pcRatingsTrainingsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPcRatingsTrainingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRatingsTrainings))
            )
            .andExpect(status().isBadRequest());

        // Validate the PcRatingsTrainings in the database
        List<PcRatingsTrainings> pcRatingsTrainingsList = pcRatingsTrainingsRepository.findAll();
        assertThat(pcRatingsTrainingsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = pcRatingsTrainingsRepository.findAll().size();
        // set the field null
        pcRatingsTrainings.setCreatedAt(null);

        // Create the PcRatingsTrainings, which fails.

        restPcRatingsTrainingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRatingsTrainings))
            )
            .andExpect(status().isBadRequest());

        List<PcRatingsTrainings> pcRatingsTrainingsList = pcRatingsTrainingsRepository.findAll();
        assertThat(pcRatingsTrainingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = pcRatingsTrainingsRepository.findAll().size();
        // set the field null
        pcRatingsTrainings.setUpdatedAt(null);

        // Create the PcRatingsTrainings, which fails.

        restPcRatingsTrainingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRatingsTrainings))
            )
            .andExpect(status().isBadRequest());

        List<PcRatingsTrainings> pcRatingsTrainingsList = pcRatingsTrainingsRepository.findAll();
        assertThat(pcRatingsTrainingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = pcRatingsTrainingsRepository.findAll().size();
        // set the field null
        pcRatingsTrainings.setVersion(null);

        // Create the PcRatingsTrainings, which fails.

        restPcRatingsTrainingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRatingsTrainings))
            )
            .andExpect(status().isBadRequest());

        List<PcRatingsTrainings> pcRatingsTrainingsList = pcRatingsTrainingsRepository.findAll();
        assertThat(pcRatingsTrainingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainings() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList
        restPcRatingsTrainingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pcRatingsTrainings.getId().intValue())))
            .andExpect(jsonPath("$.[*].strength").value(hasItem(DEFAULT_STRENGTH)))
            .andExpect(jsonPath("$.[*].developmentArea").value(hasItem(DEFAULT_DEVELOPMENT_AREA)))
            .andExpect(jsonPath("$.[*].careerAmbition").value(hasItem(DEFAULT_CAREER_AMBITION)))
            .andExpect(jsonPath("$.[*].shortCourse").value(hasItem(DEFAULT_SHORT_COURSE)))
            .andExpect(jsonPath("$.[*].technicalTraining").value(hasItem(DEFAULT_TECHNICAL_TRAINING)))
            .andExpect(jsonPath("$.[*].softSkillsTraining").value(hasItem(DEFAULT_SOFT_SKILLS_TRAINING)))
            .andExpect(jsonPath("$.[*].criticalPosition").value(hasItem(DEFAULT_CRITICAL_POSITION.booleanValue())))
            .andExpect(jsonPath("$.[*].highPotential").value(hasItem(DEFAULT_HIGH_POTENTIAL.booleanValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getPcRatingsTrainings() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get the pcRatingsTrainings
        restPcRatingsTrainingsMockMvc
            .perform(get(ENTITY_API_URL_ID, pcRatingsTrainings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pcRatingsTrainings.getId().intValue()))
            .andExpect(jsonPath("$.strength").value(DEFAULT_STRENGTH))
            .andExpect(jsonPath("$.developmentArea").value(DEFAULT_DEVELOPMENT_AREA))
            .andExpect(jsonPath("$.careerAmbition").value(DEFAULT_CAREER_AMBITION))
            .andExpect(jsonPath("$.shortCourse").value(DEFAULT_SHORT_COURSE))
            .andExpect(jsonPath("$.technicalTraining").value(DEFAULT_TECHNICAL_TRAINING))
            .andExpect(jsonPath("$.softSkillsTraining").value(DEFAULT_SOFT_SKILLS_TRAINING))
            .andExpect(jsonPath("$.criticalPosition").value(DEFAULT_CRITICAL_POSITION.booleanValue()))
            .andExpect(jsonPath("$.highPotential").value(DEFAULT_HIGH_POTENTIAL.booleanValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getPcRatingsTrainingsByIdFiltering() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        Long id = pcRatingsTrainings.getId();

        defaultPcRatingsTrainingsShouldBeFound("id.equals=" + id);
        defaultPcRatingsTrainingsShouldNotBeFound("id.notEquals=" + id);

        defaultPcRatingsTrainingsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPcRatingsTrainingsShouldNotBeFound("id.greaterThan=" + id);

        defaultPcRatingsTrainingsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPcRatingsTrainingsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByStrengthIsEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where strength equals to DEFAULT_STRENGTH
        defaultPcRatingsTrainingsShouldBeFound("strength.equals=" + DEFAULT_STRENGTH);

        // Get all the pcRatingsTrainingsList where strength equals to UPDATED_STRENGTH
        defaultPcRatingsTrainingsShouldNotBeFound("strength.equals=" + UPDATED_STRENGTH);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByStrengthIsInShouldWork() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where strength in DEFAULT_STRENGTH or UPDATED_STRENGTH
        defaultPcRatingsTrainingsShouldBeFound("strength.in=" + DEFAULT_STRENGTH + "," + UPDATED_STRENGTH);

        // Get all the pcRatingsTrainingsList where strength equals to UPDATED_STRENGTH
        defaultPcRatingsTrainingsShouldNotBeFound("strength.in=" + UPDATED_STRENGTH);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByStrengthIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where strength is not null
        defaultPcRatingsTrainingsShouldBeFound("strength.specified=true");

        // Get all the pcRatingsTrainingsList where strength is null
        defaultPcRatingsTrainingsShouldNotBeFound("strength.specified=false");
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByStrengthContainsSomething() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where strength contains DEFAULT_STRENGTH
        defaultPcRatingsTrainingsShouldBeFound("strength.contains=" + DEFAULT_STRENGTH);

        // Get all the pcRatingsTrainingsList where strength contains UPDATED_STRENGTH
        defaultPcRatingsTrainingsShouldNotBeFound("strength.contains=" + UPDATED_STRENGTH);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByStrengthNotContainsSomething() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where strength does not contain DEFAULT_STRENGTH
        defaultPcRatingsTrainingsShouldNotBeFound("strength.doesNotContain=" + DEFAULT_STRENGTH);

        // Get all the pcRatingsTrainingsList where strength does not contain UPDATED_STRENGTH
        defaultPcRatingsTrainingsShouldBeFound("strength.doesNotContain=" + UPDATED_STRENGTH);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByDevelopmentAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where developmentArea equals to DEFAULT_DEVELOPMENT_AREA
        defaultPcRatingsTrainingsShouldBeFound("developmentArea.equals=" + DEFAULT_DEVELOPMENT_AREA);

        // Get all the pcRatingsTrainingsList where developmentArea equals to UPDATED_DEVELOPMENT_AREA
        defaultPcRatingsTrainingsShouldNotBeFound("developmentArea.equals=" + UPDATED_DEVELOPMENT_AREA);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByDevelopmentAreaIsInShouldWork() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where developmentArea in DEFAULT_DEVELOPMENT_AREA or UPDATED_DEVELOPMENT_AREA
        defaultPcRatingsTrainingsShouldBeFound("developmentArea.in=" + DEFAULT_DEVELOPMENT_AREA + "," + UPDATED_DEVELOPMENT_AREA);

        // Get all the pcRatingsTrainingsList where developmentArea equals to UPDATED_DEVELOPMENT_AREA
        defaultPcRatingsTrainingsShouldNotBeFound("developmentArea.in=" + UPDATED_DEVELOPMENT_AREA);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByDevelopmentAreaIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where developmentArea is not null
        defaultPcRatingsTrainingsShouldBeFound("developmentArea.specified=true");

        // Get all the pcRatingsTrainingsList where developmentArea is null
        defaultPcRatingsTrainingsShouldNotBeFound("developmentArea.specified=false");
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByDevelopmentAreaContainsSomething() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where developmentArea contains DEFAULT_DEVELOPMENT_AREA
        defaultPcRatingsTrainingsShouldBeFound("developmentArea.contains=" + DEFAULT_DEVELOPMENT_AREA);

        // Get all the pcRatingsTrainingsList where developmentArea contains UPDATED_DEVELOPMENT_AREA
        defaultPcRatingsTrainingsShouldNotBeFound("developmentArea.contains=" + UPDATED_DEVELOPMENT_AREA);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByDevelopmentAreaNotContainsSomething() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where developmentArea does not contain DEFAULT_DEVELOPMENT_AREA
        defaultPcRatingsTrainingsShouldNotBeFound("developmentArea.doesNotContain=" + DEFAULT_DEVELOPMENT_AREA);

        // Get all the pcRatingsTrainingsList where developmentArea does not contain UPDATED_DEVELOPMENT_AREA
        defaultPcRatingsTrainingsShouldBeFound("developmentArea.doesNotContain=" + UPDATED_DEVELOPMENT_AREA);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByCareerAmbitionIsEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where careerAmbition equals to DEFAULT_CAREER_AMBITION
        defaultPcRatingsTrainingsShouldBeFound("careerAmbition.equals=" + DEFAULT_CAREER_AMBITION);

        // Get all the pcRatingsTrainingsList where careerAmbition equals to UPDATED_CAREER_AMBITION
        defaultPcRatingsTrainingsShouldNotBeFound("careerAmbition.equals=" + UPDATED_CAREER_AMBITION);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByCareerAmbitionIsInShouldWork() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where careerAmbition in DEFAULT_CAREER_AMBITION or UPDATED_CAREER_AMBITION
        defaultPcRatingsTrainingsShouldBeFound("careerAmbition.in=" + DEFAULT_CAREER_AMBITION + "," + UPDATED_CAREER_AMBITION);

        // Get all the pcRatingsTrainingsList where careerAmbition equals to UPDATED_CAREER_AMBITION
        defaultPcRatingsTrainingsShouldNotBeFound("careerAmbition.in=" + UPDATED_CAREER_AMBITION);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByCareerAmbitionIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where careerAmbition is not null
        defaultPcRatingsTrainingsShouldBeFound("careerAmbition.specified=true");

        // Get all the pcRatingsTrainingsList where careerAmbition is null
        defaultPcRatingsTrainingsShouldNotBeFound("careerAmbition.specified=false");
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByCareerAmbitionContainsSomething() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where careerAmbition contains DEFAULT_CAREER_AMBITION
        defaultPcRatingsTrainingsShouldBeFound("careerAmbition.contains=" + DEFAULT_CAREER_AMBITION);

        // Get all the pcRatingsTrainingsList where careerAmbition contains UPDATED_CAREER_AMBITION
        defaultPcRatingsTrainingsShouldNotBeFound("careerAmbition.contains=" + UPDATED_CAREER_AMBITION);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByCareerAmbitionNotContainsSomething() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where careerAmbition does not contain DEFAULT_CAREER_AMBITION
        defaultPcRatingsTrainingsShouldNotBeFound("careerAmbition.doesNotContain=" + DEFAULT_CAREER_AMBITION);

        // Get all the pcRatingsTrainingsList where careerAmbition does not contain UPDATED_CAREER_AMBITION
        defaultPcRatingsTrainingsShouldBeFound("careerAmbition.doesNotContain=" + UPDATED_CAREER_AMBITION);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByShortCourseIsEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where shortCourse equals to DEFAULT_SHORT_COURSE
        defaultPcRatingsTrainingsShouldBeFound("shortCourse.equals=" + DEFAULT_SHORT_COURSE);

        // Get all the pcRatingsTrainingsList where shortCourse equals to UPDATED_SHORT_COURSE
        defaultPcRatingsTrainingsShouldNotBeFound("shortCourse.equals=" + UPDATED_SHORT_COURSE);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByShortCourseIsInShouldWork() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where shortCourse in DEFAULT_SHORT_COURSE or UPDATED_SHORT_COURSE
        defaultPcRatingsTrainingsShouldBeFound("shortCourse.in=" + DEFAULT_SHORT_COURSE + "," + UPDATED_SHORT_COURSE);

        // Get all the pcRatingsTrainingsList where shortCourse equals to UPDATED_SHORT_COURSE
        defaultPcRatingsTrainingsShouldNotBeFound("shortCourse.in=" + UPDATED_SHORT_COURSE);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByShortCourseIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where shortCourse is not null
        defaultPcRatingsTrainingsShouldBeFound("shortCourse.specified=true");

        // Get all the pcRatingsTrainingsList where shortCourse is null
        defaultPcRatingsTrainingsShouldNotBeFound("shortCourse.specified=false");
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByShortCourseContainsSomething() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where shortCourse contains DEFAULT_SHORT_COURSE
        defaultPcRatingsTrainingsShouldBeFound("shortCourse.contains=" + DEFAULT_SHORT_COURSE);

        // Get all the pcRatingsTrainingsList where shortCourse contains UPDATED_SHORT_COURSE
        defaultPcRatingsTrainingsShouldNotBeFound("shortCourse.contains=" + UPDATED_SHORT_COURSE);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByShortCourseNotContainsSomething() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where shortCourse does not contain DEFAULT_SHORT_COURSE
        defaultPcRatingsTrainingsShouldNotBeFound("shortCourse.doesNotContain=" + DEFAULT_SHORT_COURSE);

        // Get all the pcRatingsTrainingsList where shortCourse does not contain UPDATED_SHORT_COURSE
        defaultPcRatingsTrainingsShouldBeFound("shortCourse.doesNotContain=" + UPDATED_SHORT_COURSE);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByTechnicalTrainingIsEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where technicalTraining equals to DEFAULT_TECHNICAL_TRAINING
        defaultPcRatingsTrainingsShouldBeFound("technicalTraining.equals=" + DEFAULT_TECHNICAL_TRAINING);

        // Get all the pcRatingsTrainingsList where technicalTraining equals to UPDATED_TECHNICAL_TRAINING
        defaultPcRatingsTrainingsShouldNotBeFound("technicalTraining.equals=" + UPDATED_TECHNICAL_TRAINING);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByTechnicalTrainingIsInShouldWork() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where technicalTraining in DEFAULT_TECHNICAL_TRAINING or UPDATED_TECHNICAL_TRAINING
        defaultPcRatingsTrainingsShouldBeFound("technicalTraining.in=" + DEFAULT_TECHNICAL_TRAINING + "," + UPDATED_TECHNICAL_TRAINING);

        // Get all the pcRatingsTrainingsList where technicalTraining equals to UPDATED_TECHNICAL_TRAINING
        defaultPcRatingsTrainingsShouldNotBeFound("technicalTraining.in=" + UPDATED_TECHNICAL_TRAINING);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByTechnicalTrainingIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where technicalTraining is not null
        defaultPcRatingsTrainingsShouldBeFound("technicalTraining.specified=true");

        // Get all the pcRatingsTrainingsList where technicalTraining is null
        defaultPcRatingsTrainingsShouldNotBeFound("technicalTraining.specified=false");
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByTechnicalTrainingContainsSomething() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where technicalTraining contains DEFAULT_TECHNICAL_TRAINING
        defaultPcRatingsTrainingsShouldBeFound("technicalTraining.contains=" + DEFAULT_TECHNICAL_TRAINING);

        // Get all the pcRatingsTrainingsList where technicalTraining contains UPDATED_TECHNICAL_TRAINING
        defaultPcRatingsTrainingsShouldNotBeFound("technicalTraining.contains=" + UPDATED_TECHNICAL_TRAINING);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByTechnicalTrainingNotContainsSomething() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where technicalTraining does not contain DEFAULT_TECHNICAL_TRAINING
        defaultPcRatingsTrainingsShouldNotBeFound("technicalTraining.doesNotContain=" + DEFAULT_TECHNICAL_TRAINING);

        // Get all the pcRatingsTrainingsList where technicalTraining does not contain UPDATED_TECHNICAL_TRAINING
        defaultPcRatingsTrainingsShouldBeFound("technicalTraining.doesNotContain=" + UPDATED_TECHNICAL_TRAINING);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsBySoftSkillsTrainingIsEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where softSkillsTraining equals to DEFAULT_SOFT_SKILLS_TRAINING
        defaultPcRatingsTrainingsShouldBeFound("softSkillsTraining.equals=" + DEFAULT_SOFT_SKILLS_TRAINING);

        // Get all the pcRatingsTrainingsList where softSkillsTraining equals to UPDATED_SOFT_SKILLS_TRAINING
        defaultPcRatingsTrainingsShouldNotBeFound("softSkillsTraining.equals=" + UPDATED_SOFT_SKILLS_TRAINING);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsBySoftSkillsTrainingIsInShouldWork() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where softSkillsTraining in DEFAULT_SOFT_SKILLS_TRAINING or UPDATED_SOFT_SKILLS_TRAINING
        defaultPcRatingsTrainingsShouldBeFound(
            "softSkillsTraining.in=" + DEFAULT_SOFT_SKILLS_TRAINING + "," + UPDATED_SOFT_SKILLS_TRAINING
        );

        // Get all the pcRatingsTrainingsList where softSkillsTraining equals to UPDATED_SOFT_SKILLS_TRAINING
        defaultPcRatingsTrainingsShouldNotBeFound("softSkillsTraining.in=" + UPDATED_SOFT_SKILLS_TRAINING);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsBySoftSkillsTrainingIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where softSkillsTraining is not null
        defaultPcRatingsTrainingsShouldBeFound("softSkillsTraining.specified=true");

        // Get all the pcRatingsTrainingsList where softSkillsTraining is null
        defaultPcRatingsTrainingsShouldNotBeFound("softSkillsTraining.specified=false");
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsBySoftSkillsTrainingContainsSomething() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where softSkillsTraining contains DEFAULT_SOFT_SKILLS_TRAINING
        defaultPcRatingsTrainingsShouldBeFound("softSkillsTraining.contains=" + DEFAULT_SOFT_SKILLS_TRAINING);

        // Get all the pcRatingsTrainingsList where softSkillsTraining contains UPDATED_SOFT_SKILLS_TRAINING
        defaultPcRatingsTrainingsShouldNotBeFound("softSkillsTraining.contains=" + UPDATED_SOFT_SKILLS_TRAINING);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsBySoftSkillsTrainingNotContainsSomething() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where softSkillsTraining does not contain DEFAULT_SOFT_SKILLS_TRAINING
        defaultPcRatingsTrainingsShouldNotBeFound("softSkillsTraining.doesNotContain=" + DEFAULT_SOFT_SKILLS_TRAINING);

        // Get all the pcRatingsTrainingsList where softSkillsTraining does not contain UPDATED_SOFT_SKILLS_TRAINING
        defaultPcRatingsTrainingsShouldBeFound("softSkillsTraining.doesNotContain=" + UPDATED_SOFT_SKILLS_TRAINING);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByCriticalPositionIsEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where criticalPosition equals to DEFAULT_CRITICAL_POSITION
        defaultPcRatingsTrainingsShouldBeFound("criticalPosition.equals=" + DEFAULT_CRITICAL_POSITION);

        // Get all the pcRatingsTrainingsList where criticalPosition equals to UPDATED_CRITICAL_POSITION
        defaultPcRatingsTrainingsShouldNotBeFound("criticalPosition.equals=" + UPDATED_CRITICAL_POSITION);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByCriticalPositionIsInShouldWork() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where criticalPosition in DEFAULT_CRITICAL_POSITION or UPDATED_CRITICAL_POSITION
        defaultPcRatingsTrainingsShouldBeFound("criticalPosition.in=" + DEFAULT_CRITICAL_POSITION + "," + UPDATED_CRITICAL_POSITION);

        // Get all the pcRatingsTrainingsList where criticalPosition equals to UPDATED_CRITICAL_POSITION
        defaultPcRatingsTrainingsShouldNotBeFound("criticalPosition.in=" + UPDATED_CRITICAL_POSITION);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByCriticalPositionIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where criticalPosition is not null
        defaultPcRatingsTrainingsShouldBeFound("criticalPosition.specified=true");

        // Get all the pcRatingsTrainingsList where criticalPosition is null
        defaultPcRatingsTrainingsShouldNotBeFound("criticalPosition.specified=false");
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByHighPotentialIsEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where highPotential equals to DEFAULT_HIGH_POTENTIAL
        defaultPcRatingsTrainingsShouldBeFound("highPotential.equals=" + DEFAULT_HIGH_POTENTIAL);

        // Get all the pcRatingsTrainingsList where highPotential equals to UPDATED_HIGH_POTENTIAL
        defaultPcRatingsTrainingsShouldNotBeFound("highPotential.equals=" + UPDATED_HIGH_POTENTIAL);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByHighPotentialIsInShouldWork() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where highPotential in DEFAULT_HIGH_POTENTIAL or UPDATED_HIGH_POTENTIAL
        defaultPcRatingsTrainingsShouldBeFound("highPotential.in=" + DEFAULT_HIGH_POTENTIAL + "," + UPDATED_HIGH_POTENTIAL);

        // Get all the pcRatingsTrainingsList where highPotential equals to UPDATED_HIGH_POTENTIAL
        defaultPcRatingsTrainingsShouldNotBeFound("highPotential.in=" + UPDATED_HIGH_POTENTIAL);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByHighPotentialIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where highPotential is not null
        defaultPcRatingsTrainingsShouldBeFound("highPotential.specified=true");

        // Get all the pcRatingsTrainingsList where highPotential is null
        defaultPcRatingsTrainingsShouldNotBeFound("highPotential.specified=false");
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where createdAt equals to DEFAULT_CREATED_AT
        defaultPcRatingsTrainingsShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the pcRatingsTrainingsList where createdAt equals to UPDATED_CREATED_AT
        defaultPcRatingsTrainingsShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultPcRatingsTrainingsShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the pcRatingsTrainingsList where createdAt equals to UPDATED_CREATED_AT
        defaultPcRatingsTrainingsShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where createdAt is not null
        defaultPcRatingsTrainingsShouldBeFound("createdAt.specified=true");

        // Get all the pcRatingsTrainingsList where createdAt is null
        defaultPcRatingsTrainingsShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultPcRatingsTrainingsShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the pcRatingsTrainingsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultPcRatingsTrainingsShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultPcRatingsTrainingsShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the pcRatingsTrainingsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultPcRatingsTrainingsShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where updatedAt is not null
        defaultPcRatingsTrainingsShouldBeFound("updatedAt.specified=true");

        // Get all the pcRatingsTrainingsList where updatedAt is null
        defaultPcRatingsTrainingsShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByDeletedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where deletedAt equals to DEFAULT_DELETED_AT
        defaultPcRatingsTrainingsShouldBeFound("deletedAt.equals=" + DEFAULT_DELETED_AT);

        // Get all the pcRatingsTrainingsList where deletedAt equals to UPDATED_DELETED_AT
        defaultPcRatingsTrainingsShouldNotBeFound("deletedAt.equals=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByDeletedAtIsInShouldWork() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where deletedAt in DEFAULT_DELETED_AT or UPDATED_DELETED_AT
        defaultPcRatingsTrainingsShouldBeFound("deletedAt.in=" + DEFAULT_DELETED_AT + "," + UPDATED_DELETED_AT);

        // Get all the pcRatingsTrainingsList where deletedAt equals to UPDATED_DELETED_AT
        defaultPcRatingsTrainingsShouldNotBeFound("deletedAt.in=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByDeletedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where deletedAt is not null
        defaultPcRatingsTrainingsShouldBeFound("deletedAt.specified=true");

        // Get all the pcRatingsTrainingsList where deletedAt is null
        defaultPcRatingsTrainingsShouldNotBeFound("deletedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where version equals to DEFAULT_VERSION
        defaultPcRatingsTrainingsShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the pcRatingsTrainingsList where version equals to UPDATED_VERSION
        defaultPcRatingsTrainingsShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultPcRatingsTrainingsShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the pcRatingsTrainingsList where version equals to UPDATED_VERSION
        defaultPcRatingsTrainingsShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where version is not null
        defaultPcRatingsTrainingsShouldBeFound("version.specified=true");

        // Get all the pcRatingsTrainingsList where version is null
        defaultPcRatingsTrainingsShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where version is greater than or equal to DEFAULT_VERSION
        defaultPcRatingsTrainingsShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the pcRatingsTrainingsList where version is greater than or equal to UPDATED_VERSION
        defaultPcRatingsTrainingsShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where version is less than or equal to DEFAULT_VERSION
        defaultPcRatingsTrainingsShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the pcRatingsTrainingsList where version is less than or equal to SMALLER_VERSION
        defaultPcRatingsTrainingsShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where version is less than DEFAULT_VERSION
        defaultPcRatingsTrainingsShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the pcRatingsTrainingsList where version is less than UPDATED_VERSION
        defaultPcRatingsTrainingsShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        // Get all the pcRatingsTrainingsList where version is greater than DEFAULT_VERSION
        defaultPcRatingsTrainingsShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the pcRatingsTrainingsList where version is greater than SMALLER_VERSION
        defaultPcRatingsTrainingsShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsBySuccessorForIsEqualToSomething() throws Exception {
        Employees successorFor;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);
            successorFor = EmployeesResourceIT.createEntity(em);
        } else {
            successorFor = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(successorFor);
        em.flush();
        pcRatingsTrainings.setSuccessorFor(successorFor);
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);
        Long successorForId = successorFor.getId();

        // Get all the pcRatingsTrainingsList where successorFor equals to successorForId
        defaultPcRatingsTrainingsShouldBeFound("successorForId.equals=" + successorForId);

        // Get all the pcRatingsTrainingsList where successorFor equals to (successorForId + 1)
        defaultPcRatingsTrainingsShouldNotBeFound("successorForId.equals=" + (successorForId + 1));
    }

    @Test
    @Transactional
    void getAllPcRatingsTrainingsByRatingIsEqualToSomething() throws Exception {
        PcRatings rating;
        if (TestUtil.findAll(em, PcRatings.class).isEmpty()) {
            pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);
            rating = PcRatingsResourceIT.createEntity(em);
        } else {
            rating = TestUtil.findAll(em, PcRatings.class).get(0);
        }
        em.persist(rating);
        em.flush();
        pcRatingsTrainings.setRating(rating);
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);
        Long ratingId = rating.getId();

        // Get all the pcRatingsTrainingsList where rating equals to ratingId
        defaultPcRatingsTrainingsShouldBeFound("ratingId.equals=" + ratingId);

        // Get all the pcRatingsTrainingsList where rating equals to (ratingId + 1)
        defaultPcRatingsTrainingsShouldNotBeFound("ratingId.equals=" + (ratingId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPcRatingsTrainingsShouldBeFound(String filter) throws Exception {
        restPcRatingsTrainingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pcRatingsTrainings.getId().intValue())))
            .andExpect(jsonPath("$.[*].strength").value(hasItem(DEFAULT_STRENGTH)))
            .andExpect(jsonPath("$.[*].developmentArea").value(hasItem(DEFAULT_DEVELOPMENT_AREA)))
            .andExpect(jsonPath("$.[*].careerAmbition").value(hasItem(DEFAULT_CAREER_AMBITION)))
            .andExpect(jsonPath("$.[*].shortCourse").value(hasItem(DEFAULT_SHORT_COURSE)))
            .andExpect(jsonPath("$.[*].technicalTraining").value(hasItem(DEFAULT_TECHNICAL_TRAINING)))
            .andExpect(jsonPath("$.[*].softSkillsTraining").value(hasItem(DEFAULT_SOFT_SKILLS_TRAINING)))
            .andExpect(jsonPath("$.[*].criticalPosition").value(hasItem(DEFAULT_CRITICAL_POSITION.booleanValue())))
            .andExpect(jsonPath("$.[*].highPotential").value(hasItem(DEFAULT_HIGH_POTENTIAL.booleanValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restPcRatingsTrainingsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPcRatingsTrainingsShouldNotBeFound(String filter) throws Exception {
        restPcRatingsTrainingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPcRatingsTrainingsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPcRatingsTrainings() throws Exception {
        // Get the pcRatingsTrainings
        restPcRatingsTrainingsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPcRatingsTrainings() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        int databaseSizeBeforeUpdate = pcRatingsTrainingsRepository.findAll().size();

        // Update the pcRatingsTrainings
        PcRatingsTrainings updatedPcRatingsTrainings = pcRatingsTrainingsRepository.findById(pcRatingsTrainings.getId()).get();
        // Disconnect from session so that the updates on updatedPcRatingsTrainings are not directly saved in db
        em.detach(updatedPcRatingsTrainings);
        updatedPcRatingsTrainings
            .strength(UPDATED_STRENGTH)
            .developmentArea(UPDATED_DEVELOPMENT_AREA)
            .careerAmbition(UPDATED_CAREER_AMBITION)
            .shortCourse(UPDATED_SHORT_COURSE)
            .technicalTraining(UPDATED_TECHNICAL_TRAINING)
            .softSkillsTraining(UPDATED_SOFT_SKILLS_TRAINING)
            .criticalPosition(UPDATED_CRITICAL_POSITION)
            .highPotential(UPDATED_HIGH_POTENTIAL)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restPcRatingsTrainingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPcRatingsTrainings.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPcRatingsTrainings))
            )
            .andExpect(status().isOk());

        // Validate the PcRatingsTrainings in the database
        List<PcRatingsTrainings> pcRatingsTrainingsList = pcRatingsTrainingsRepository.findAll();
        assertThat(pcRatingsTrainingsList).hasSize(databaseSizeBeforeUpdate);
        PcRatingsTrainings testPcRatingsTrainings = pcRatingsTrainingsList.get(pcRatingsTrainingsList.size() - 1);
        assertThat(testPcRatingsTrainings.getStrength()).isEqualTo(UPDATED_STRENGTH);
        assertThat(testPcRatingsTrainings.getDevelopmentArea()).isEqualTo(UPDATED_DEVELOPMENT_AREA);
        assertThat(testPcRatingsTrainings.getCareerAmbition()).isEqualTo(UPDATED_CAREER_AMBITION);
        assertThat(testPcRatingsTrainings.getShortCourse()).isEqualTo(UPDATED_SHORT_COURSE);
        assertThat(testPcRatingsTrainings.getTechnicalTraining()).isEqualTo(UPDATED_TECHNICAL_TRAINING);
        assertThat(testPcRatingsTrainings.getSoftSkillsTraining()).isEqualTo(UPDATED_SOFT_SKILLS_TRAINING);
        assertThat(testPcRatingsTrainings.getCriticalPosition()).isEqualTo(UPDATED_CRITICAL_POSITION);
        assertThat(testPcRatingsTrainings.getHighPotential()).isEqualTo(UPDATED_HIGH_POTENTIAL);
        assertThat(testPcRatingsTrainings.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPcRatingsTrainings.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testPcRatingsTrainings.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testPcRatingsTrainings.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingPcRatingsTrainings() throws Exception {
        int databaseSizeBeforeUpdate = pcRatingsTrainingsRepository.findAll().size();
        pcRatingsTrainings.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPcRatingsTrainingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pcRatingsTrainings.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRatingsTrainings))
            )
            .andExpect(status().isBadRequest());

        // Validate the PcRatingsTrainings in the database
        List<PcRatingsTrainings> pcRatingsTrainingsList = pcRatingsTrainingsRepository.findAll();
        assertThat(pcRatingsTrainingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPcRatingsTrainings() throws Exception {
        int databaseSizeBeforeUpdate = pcRatingsTrainingsRepository.findAll().size();
        pcRatingsTrainings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPcRatingsTrainingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRatingsTrainings))
            )
            .andExpect(status().isBadRequest());

        // Validate the PcRatingsTrainings in the database
        List<PcRatingsTrainings> pcRatingsTrainingsList = pcRatingsTrainingsRepository.findAll();
        assertThat(pcRatingsTrainingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPcRatingsTrainings() throws Exception {
        int databaseSizeBeforeUpdate = pcRatingsTrainingsRepository.findAll().size();
        pcRatingsTrainings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPcRatingsTrainingsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pcRatingsTrainings))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PcRatingsTrainings in the database
        List<PcRatingsTrainings> pcRatingsTrainingsList = pcRatingsTrainingsRepository.findAll();
        assertThat(pcRatingsTrainingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePcRatingsTrainingsWithPatch() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        int databaseSizeBeforeUpdate = pcRatingsTrainingsRepository.findAll().size();

        // Update the pcRatingsTrainings using partial update
        PcRatingsTrainings partialUpdatedPcRatingsTrainings = new PcRatingsTrainings();
        partialUpdatedPcRatingsTrainings.setId(pcRatingsTrainings.getId());

        partialUpdatedPcRatingsTrainings
            .developmentArea(UPDATED_DEVELOPMENT_AREA)
            .careerAmbition(UPDATED_CAREER_AMBITION)
            .shortCourse(UPDATED_SHORT_COURSE)
            .highPotential(UPDATED_HIGH_POTENTIAL)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .version(UPDATED_VERSION);

        restPcRatingsTrainingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPcRatingsTrainings.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPcRatingsTrainings))
            )
            .andExpect(status().isOk());

        // Validate the PcRatingsTrainings in the database
        List<PcRatingsTrainings> pcRatingsTrainingsList = pcRatingsTrainingsRepository.findAll();
        assertThat(pcRatingsTrainingsList).hasSize(databaseSizeBeforeUpdate);
        PcRatingsTrainings testPcRatingsTrainings = pcRatingsTrainingsList.get(pcRatingsTrainingsList.size() - 1);
        assertThat(testPcRatingsTrainings.getStrength()).isEqualTo(DEFAULT_STRENGTH);
        assertThat(testPcRatingsTrainings.getDevelopmentArea()).isEqualTo(UPDATED_DEVELOPMENT_AREA);
        assertThat(testPcRatingsTrainings.getCareerAmbition()).isEqualTo(UPDATED_CAREER_AMBITION);
        assertThat(testPcRatingsTrainings.getShortCourse()).isEqualTo(UPDATED_SHORT_COURSE);
        assertThat(testPcRatingsTrainings.getTechnicalTraining()).isEqualTo(DEFAULT_TECHNICAL_TRAINING);
        assertThat(testPcRatingsTrainings.getSoftSkillsTraining()).isEqualTo(DEFAULT_SOFT_SKILLS_TRAINING);
        assertThat(testPcRatingsTrainings.getCriticalPosition()).isEqualTo(DEFAULT_CRITICAL_POSITION);
        assertThat(testPcRatingsTrainings.getHighPotential()).isEqualTo(UPDATED_HIGH_POTENTIAL);
        assertThat(testPcRatingsTrainings.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPcRatingsTrainings.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testPcRatingsTrainings.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testPcRatingsTrainings.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void fullUpdatePcRatingsTrainingsWithPatch() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        int databaseSizeBeforeUpdate = pcRatingsTrainingsRepository.findAll().size();

        // Update the pcRatingsTrainings using partial update
        PcRatingsTrainings partialUpdatedPcRatingsTrainings = new PcRatingsTrainings();
        partialUpdatedPcRatingsTrainings.setId(pcRatingsTrainings.getId());

        partialUpdatedPcRatingsTrainings
            .strength(UPDATED_STRENGTH)
            .developmentArea(UPDATED_DEVELOPMENT_AREA)
            .careerAmbition(UPDATED_CAREER_AMBITION)
            .shortCourse(UPDATED_SHORT_COURSE)
            .technicalTraining(UPDATED_TECHNICAL_TRAINING)
            .softSkillsTraining(UPDATED_SOFT_SKILLS_TRAINING)
            .criticalPosition(UPDATED_CRITICAL_POSITION)
            .highPotential(UPDATED_HIGH_POTENTIAL)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restPcRatingsTrainingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPcRatingsTrainings.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPcRatingsTrainings))
            )
            .andExpect(status().isOk());

        // Validate the PcRatingsTrainings in the database
        List<PcRatingsTrainings> pcRatingsTrainingsList = pcRatingsTrainingsRepository.findAll();
        assertThat(pcRatingsTrainingsList).hasSize(databaseSizeBeforeUpdate);
        PcRatingsTrainings testPcRatingsTrainings = pcRatingsTrainingsList.get(pcRatingsTrainingsList.size() - 1);
        assertThat(testPcRatingsTrainings.getStrength()).isEqualTo(UPDATED_STRENGTH);
        assertThat(testPcRatingsTrainings.getDevelopmentArea()).isEqualTo(UPDATED_DEVELOPMENT_AREA);
        assertThat(testPcRatingsTrainings.getCareerAmbition()).isEqualTo(UPDATED_CAREER_AMBITION);
        assertThat(testPcRatingsTrainings.getShortCourse()).isEqualTo(UPDATED_SHORT_COURSE);
        assertThat(testPcRatingsTrainings.getTechnicalTraining()).isEqualTo(UPDATED_TECHNICAL_TRAINING);
        assertThat(testPcRatingsTrainings.getSoftSkillsTraining()).isEqualTo(UPDATED_SOFT_SKILLS_TRAINING);
        assertThat(testPcRatingsTrainings.getCriticalPosition()).isEqualTo(UPDATED_CRITICAL_POSITION);
        assertThat(testPcRatingsTrainings.getHighPotential()).isEqualTo(UPDATED_HIGH_POTENTIAL);
        assertThat(testPcRatingsTrainings.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPcRatingsTrainings.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testPcRatingsTrainings.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testPcRatingsTrainings.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingPcRatingsTrainings() throws Exception {
        int databaseSizeBeforeUpdate = pcRatingsTrainingsRepository.findAll().size();
        pcRatingsTrainings.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPcRatingsTrainingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pcRatingsTrainings.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pcRatingsTrainings))
            )
            .andExpect(status().isBadRequest());

        // Validate the PcRatingsTrainings in the database
        List<PcRatingsTrainings> pcRatingsTrainingsList = pcRatingsTrainingsRepository.findAll();
        assertThat(pcRatingsTrainingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPcRatingsTrainings() throws Exception {
        int databaseSizeBeforeUpdate = pcRatingsTrainingsRepository.findAll().size();
        pcRatingsTrainings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPcRatingsTrainingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pcRatingsTrainings))
            )
            .andExpect(status().isBadRequest());

        // Validate the PcRatingsTrainings in the database
        List<PcRatingsTrainings> pcRatingsTrainingsList = pcRatingsTrainingsRepository.findAll();
        assertThat(pcRatingsTrainingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPcRatingsTrainings() throws Exception {
        int databaseSizeBeforeUpdate = pcRatingsTrainingsRepository.findAll().size();
        pcRatingsTrainings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPcRatingsTrainingsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pcRatingsTrainings))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PcRatingsTrainings in the database
        List<PcRatingsTrainings> pcRatingsTrainingsList = pcRatingsTrainingsRepository.findAll();
        assertThat(pcRatingsTrainingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePcRatingsTrainings() throws Exception {
        // Initialize the database
        pcRatingsTrainingsRepository.saveAndFlush(pcRatingsTrainings);

        int databaseSizeBeforeDelete = pcRatingsTrainingsRepository.findAll().size();

        // Delete the pcRatingsTrainings
        restPcRatingsTrainingsMockMvc
            .perform(delete(ENTITY_API_URL_ID, pcRatingsTrainings.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PcRatingsTrainings> pcRatingsTrainingsList = pcRatingsTrainingsRepository.findAll();
        assertThat(pcRatingsTrainingsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
