package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.DealRequirementMatchingResources;
import com.venturedive.vendian_mono.domain.DealRequirements;
import com.venturedive.vendian_mono.domain.Deals;
import com.venturedive.vendian_mono.repository.DealRequirementsRepository;
import com.venturedive.vendian_mono.service.criteria.DealRequirementsCriteria;
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
 * Integration tests for the {@link DealRequirementsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DealRequirementsResourceIT {

    private static final String DEFAULT_DEALREQIDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_DEALREQIDENTIFIER = "BBBBBBBBBB";

    private static final String DEFAULT_COMPETENCYNAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPETENCYNAME = "BBBBBBBBBB";

    private static final String DEFAULT_SKILLS = "AAAAAAAAAA";
    private static final String UPDATED_SKILLS = "BBBBBBBBBB";

    private static final Double DEFAULT_RESOURCEREQUIRED = 1D;
    private static final Double UPDATED_RESOURCEREQUIRED = 2D;
    private static final Double SMALLER_RESOURCEREQUIRED = 1D - 1D;

    private static final String DEFAULT_MINEXPERIENCELEVEL = "AAAAAAAAAA";
    private static final String UPDATED_MINEXPERIENCELEVEL = "BBBBBBBBBB";

    private static final String DEFAULT_MAXEXPERIENCELEVEL = "AAAAAAAAAA";
    private static final String UPDATED_MAXEXPERIENCELEVEL = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/deal-requirements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DealRequirementsRepository dealRequirementsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDealRequirementsMockMvc;

    private DealRequirements dealRequirements;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DealRequirements createEntity(EntityManager em) {
        DealRequirements dealRequirements = new DealRequirements()
            .dealreqidentifier(DEFAULT_DEALREQIDENTIFIER)
            .competencyname(DEFAULT_COMPETENCYNAME)
            .skills(DEFAULT_SKILLS)
            .resourcerequired(DEFAULT_RESOURCEREQUIRED)
            .minexperiencelevel(DEFAULT_MINEXPERIENCELEVEL)
            .maxexperiencelevel(DEFAULT_MAXEXPERIENCELEVEL)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT)
            .deletedat(DEFAULT_DELETEDAT);
        return dealRequirements;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DealRequirements createUpdatedEntity(EntityManager em) {
        DealRequirements dealRequirements = new DealRequirements()
            .dealreqidentifier(UPDATED_DEALREQIDENTIFIER)
            .competencyname(UPDATED_COMPETENCYNAME)
            .skills(UPDATED_SKILLS)
            .resourcerequired(UPDATED_RESOURCEREQUIRED)
            .minexperiencelevel(UPDATED_MINEXPERIENCELEVEL)
            .maxexperiencelevel(UPDATED_MAXEXPERIENCELEVEL)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .deletedat(UPDATED_DELETEDAT);
        return dealRequirements;
    }

    @BeforeEach
    public void initTest() {
        dealRequirements = createEntity(em);
    }

    @Test
    @Transactional
    void createDealRequirements() throws Exception {
        int databaseSizeBeforeCreate = dealRequirementsRepository.findAll().size();
        // Create the DealRequirements
        restDealRequirementsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealRequirements))
            )
            .andExpect(status().isCreated());

        // Validate the DealRequirements in the database
        List<DealRequirements> dealRequirementsList = dealRequirementsRepository.findAll();
        assertThat(dealRequirementsList).hasSize(databaseSizeBeforeCreate + 1);
        DealRequirements testDealRequirements = dealRequirementsList.get(dealRequirementsList.size() - 1);
        assertThat(testDealRequirements.getDealreqidentifier()).isEqualTo(DEFAULT_DEALREQIDENTIFIER);
        assertThat(testDealRequirements.getCompetencyname()).isEqualTo(DEFAULT_COMPETENCYNAME);
        assertThat(testDealRequirements.getSkills()).isEqualTo(DEFAULT_SKILLS);
        assertThat(testDealRequirements.getResourcerequired()).isEqualTo(DEFAULT_RESOURCEREQUIRED);
        assertThat(testDealRequirements.getMinexperiencelevel()).isEqualTo(DEFAULT_MINEXPERIENCELEVEL);
        assertThat(testDealRequirements.getMaxexperiencelevel()).isEqualTo(DEFAULT_MAXEXPERIENCELEVEL);
        assertThat(testDealRequirements.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testDealRequirements.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testDealRequirements.getDeletedat()).isEqualTo(DEFAULT_DELETEDAT);
    }

    @Test
    @Transactional
    void createDealRequirementsWithExistingId() throws Exception {
        // Create the DealRequirements with an existing ID
        dealRequirements.setId(1L);

        int databaseSizeBeforeCreate = dealRequirementsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDealRequirementsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealRequirements))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealRequirements in the database
        List<DealRequirements> dealRequirementsList = dealRequirementsRepository.findAll();
        assertThat(dealRequirementsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDealreqidentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealRequirementsRepository.findAll().size();
        // set the field null
        dealRequirements.setDealreqidentifier(null);

        // Create the DealRequirements, which fails.

        restDealRequirementsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealRequirements))
            )
            .andExpect(status().isBadRequest());

        List<DealRequirements> dealRequirementsList = dealRequirementsRepository.findAll();
        assertThat(dealRequirementsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCompetencynameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealRequirementsRepository.findAll().size();
        // set the field null
        dealRequirements.setCompetencyname(null);

        // Create the DealRequirements, which fails.

        restDealRequirementsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealRequirements))
            )
            .andExpect(status().isBadRequest());

        List<DealRequirements> dealRequirementsList = dealRequirementsRepository.findAll();
        assertThat(dealRequirementsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkResourcerequiredIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealRequirementsRepository.findAll().size();
        // set the field null
        dealRequirements.setResourcerequired(null);

        // Create the DealRequirements, which fails.

        restDealRequirementsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealRequirements))
            )
            .andExpect(status().isBadRequest());

        List<DealRequirements> dealRequirementsList = dealRequirementsRepository.findAll();
        assertThat(dealRequirementsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDealRequirements() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList
        restDealRequirementsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dealRequirements.getId().intValue())))
            .andExpect(jsonPath("$.[*].dealreqidentifier").value(hasItem(DEFAULT_DEALREQIDENTIFIER)))
            .andExpect(jsonPath("$.[*].competencyname").value(hasItem(DEFAULT_COMPETENCYNAME)))
            .andExpect(jsonPath("$.[*].skills").value(hasItem(DEFAULT_SKILLS)))
            .andExpect(jsonPath("$.[*].resourcerequired").value(hasItem(DEFAULT_RESOURCEREQUIRED.doubleValue())))
            .andExpect(jsonPath("$.[*].minexperiencelevel").value(hasItem(DEFAULT_MINEXPERIENCELEVEL)))
            .andExpect(jsonPath("$.[*].maxexperiencelevel").value(hasItem(DEFAULT_MAXEXPERIENCELEVEL)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].deletedat").value(hasItem(DEFAULT_DELETEDAT.toString())));
    }

    @Test
    @Transactional
    void getDealRequirements() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get the dealRequirements
        restDealRequirementsMockMvc
            .perform(get(ENTITY_API_URL_ID, dealRequirements.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dealRequirements.getId().intValue()))
            .andExpect(jsonPath("$.dealreqidentifier").value(DEFAULT_DEALREQIDENTIFIER))
            .andExpect(jsonPath("$.competencyname").value(DEFAULT_COMPETENCYNAME))
            .andExpect(jsonPath("$.skills").value(DEFAULT_SKILLS))
            .andExpect(jsonPath("$.resourcerequired").value(DEFAULT_RESOURCEREQUIRED.doubleValue()))
            .andExpect(jsonPath("$.minexperiencelevel").value(DEFAULT_MINEXPERIENCELEVEL))
            .andExpect(jsonPath("$.maxexperiencelevel").value(DEFAULT_MAXEXPERIENCELEVEL))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()))
            .andExpect(jsonPath("$.deletedat").value(DEFAULT_DELETEDAT.toString()));
    }

    @Test
    @Transactional
    void getDealRequirementsByIdFiltering() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        Long id = dealRequirements.getId();

        defaultDealRequirementsShouldBeFound("id.equals=" + id);
        defaultDealRequirementsShouldNotBeFound("id.notEquals=" + id);

        defaultDealRequirementsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDealRequirementsShouldNotBeFound("id.greaterThan=" + id);

        defaultDealRequirementsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDealRequirementsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDealRequirementsByDealreqidentifierIsEqualToSomething() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where dealreqidentifier equals to DEFAULT_DEALREQIDENTIFIER
        defaultDealRequirementsShouldBeFound("dealreqidentifier.equals=" + DEFAULT_DEALREQIDENTIFIER);

        // Get all the dealRequirementsList where dealreqidentifier equals to UPDATED_DEALREQIDENTIFIER
        defaultDealRequirementsShouldNotBeFound("dealreqidentifier.equals=" + UPDATED_DEALREQIDENTIFIER);
    }

    @Test
    @Transactional
    void getAllDealRequirementsByDealreqidentifierIsInShouldWork() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where dealreqidentifier in DEFAULT_DEALREQIDENTIFIER or UPDATED_DEALREQIDENTIFIER
        defaultDealRequirementsShouldBeFound("dealreqidentifier.in=" + DEFAULT_DEALREQIDENTIFIER + "," + UPDATED_DEALREQIDENTIFIER);

        // Get all the dealRequirementsList where dealreqidentifier equals to UPDATED_DEALREQIDENTIFIER
        defaultDealRequirementsShouldNotBeFound("dealreqidentifier.in=" + UPDATED_DEALREQIDENTIFIER);
    }

    @Test
    @Transactional
    void getAllDealRequirementsByDealreqidentifierIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where dealreqidentifier is not null
        defaultDealRequirementsShouldBeFound("dealreqidentifier.specified=true");

        // Get all the dealRequirementsList where dealreqidentifier is null
        defaultDealRequirementsShouldNotBeFound("dealreqidentifier.specified=false");
    }

    @Test
    @Transactional
    void getAllDealRequirementsByDealreqidentifierContainsSomething() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where dealreqidentifier contains DEFAULT_DEALREQIDENTIFIER
        defaultDealRequirementsShouldBeFound("dealreqidentifier.contains=" + DEFAULT_DEALREQIDENTIFIER);

        // Get all the dealRequirementsList where dealreqidentifier contains UPDATED_DEALREQIDENTIFIER
        defaultDealRequirementsShouldNotBeFound("dealreqidentifier.contains=" + UPDATED_DEALREQIDENTIFIER);
    }

    @Test
    @Transactional
    void getAllDealRequirementsByDealreqidentifierNotContainsSomething() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where dealreqidentifier does not contain DEFAULT_DEALREQIDENTIFIER
        defaultDealRequirementsShouldNotBeFound("dealreqidentifier.doesNotContain=" + DEFAULT_DEALREQIDENTIFIER);

        // Get all the dealRequirementsList where dealreqidentifier does not contain UPDATED_DEALREQIDENTIFIER
        defaultDealRequirementsShouldBeFound("dealreqidentifier.doesNotContain=" + UPDATED_DEALREQIDENTIFIER);
    }

    @Test
    @Transactional
    void getAllDealRequirementsByCompetencynameIsEqualToSomething() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where competencyname equals to DEFAULT_COMPETENCYNAME
        defaultDealRequirementsShouldBeFound("competencyname.equals=" + DEFAULT_COMPETENCYNAME);

        // Get all the dealRequirementsList where competencyname equals to UPDATED_COMPETENCYNAME
        defaultDealRequirementsShouldNotBeFound("competencyname.equals=" + UPDATED_COMPETENCYNAME);
    }

    @Test
    @Transactional
    void getAllDealRequirementsByCompetencynameIsInShouldWork() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where competencyname in DEFAULT_COMPETENCYNAME or UPDATED_COMPETENCYNAME
        defaultDealRequirementsShouldBeFound("competencyname.in=" + DEFAULT_COMPETENCYNAME + "," + UPDATED_COMPETENCYNAME);

        // Get all the dealRequirementsList where competencyname equals to UPDATED_COMPETENCYNAME
        defaultDealRequirementsShouldNotBeFound("competencyname.in=" + UPDATED_COMPETENCYNAME);
    }

    @Test
    @Transactional
    void getAllDealRequirementsByCompetencynameIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where competencyname is not null
        defaultDealRequirementsShouldBeFound("competencyname.specified=true");

        // Get all the dealRequirementsList where competencyname is null
        defaultDealRequirementsShouldNotBeFound("competencyname.specified=false");
    }

    @Test
    @Transactional
    void getAllDealRequirementsByCompetencynameContainsSomething() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where competencyname contains DEFAULT_COMPETENCYNAME
        defaultDealRequirementsShouldBeFound("competencyname.contains=" + DEFAULT_COMPETENCYNAME);

        // Get all the dealRequirementsList where competencyname contains UPDATED_COMPETENCYNAME
        defaultDealRequirementsShouldNotBeFound("competencyname.contains=" + UPDATED_COMPETENCYNAME);
    }

    @Test
    @Transactional
    void getAllDealRequirementsByCompetencynameNotContainsSomething() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where competencyname does not contain DEFAULT_COMPETENCYNAME
        defaultDealRequirementsShouldNotBeFound("competencyname.doesNotContain=" + DEFAULT_COMPETENCYNAME);

        // Get all the dealRequirementsList where competencyname does not contain UPDATED_COMPETENCYNAME
        defaultDealRequirementsShouldBeFound("competencyname.doesNotContain=" + UPDATED_COMPETENCYNAME);
    }

    @Test
    @Transactional
    void getAllDealRequirementsBySkillsIsEqualToSomething() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where skills equals to DEFAULT_SKILLS
        defaultDealRequirementsShouldBeFound("skills.equals=" + DEFAULT_SKILLS);

        // Get all the dealRequirementsList where skills equals to UPDATED_SKILLS
        defaultDealRequirementsShouldNotBeFound("skills.equals=" + UPDATED_SKILLS);
    }

    @Test
    @Transactional
    void getAllDealRequirementsBySkillsIsInShouldWork() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where skills in DEFAULT_SKILLS or UPDATED_SKILLS
        defaultDealRequirementsShouldBeFound("skills.in=" + DEFAULT_SKILLS + "," + UPDATED_SKILLS);

        // Get all the dealRequirementsList where skills equals to UPDATED_SKILLS
        defaultDealRequirementsShouldNotBeFound("skills.in=" + UPDATED_SKILLS);
    }

    @Test
    @Transactional
    void getAllDealRequirementsBySkillsIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where skills is not null
        defaultDealRequirementsShouldBeFound("skills.specified=true");

        // Get all the dealRequirementsList where skills is null
        defaultDealRequirementsShouldNotBeFound("skills.specified=false");
    }

    @Test
    @Transactional
    void getAllDealRequirementsBySkillsContainsSomething() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where skills contains DEFAULT_SKILLS
        defaultDealRequirementsShouldBeFound("skills.contains=" + DEFAULT_SKILLS);

        // Get all the dealRequirementsList where skills contains UPDATED_SKILLS
        defaultDealRequirementsShouldNotBeFound("skills.contains=" + UPDATED_SKILLS);
    }

    @Test
    @Transactional
    void getAllDealRequirementsBySkillsNotContainsSomething() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where skills does not contain DEFAULT_SKILLS
        defaultDealRequirementsShouldNotBeFound("skills.doesNotContain=" + DEFAULT_SKILLS);

        // Get all the dealRequirementsList where skills does not contain UPDATED_SKILLS
        defaultDealRequirementsShouldBeFound("skills.doesNotContain=" + UPDATED_SKILLS);
    }

    @Test
    @Transactional
    void getAllDealRequirementsByResourcerequiredIsEqualToSomething() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where resourcerequired equals to DEFAULT_RESOURCEREQUIRED
        defaultDealRequirementsShouldBeFound("resourcerequired.equals=" + DEFAULT_RESOURCEREQUIRED);

        // Get all the dealRequirementsList where resourcerequired equals to UPDATED_RESOURCEREQUIRED
        defaultDealRequirementsShouldNotBeFound("resourcerequired.equals=" + UPDATED_RESOURCEREQUIRED);
    }

    @Test
    @Transactional
    void getAllDealRequirementsByResourcerequiredIsInShouldWork() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where resourcerequired in DEFAULT_RESOURCEREQUIRED or UPDATED_RESOURCEREQUIRED
        defaultDealRequirementsShouldBeFound("resourcerequired.in=" + DEFAULT_RESOURCEREQUIRED + "," + UPDATED_RESOURCEREQUIRED);

        // Get all the dealRequirementsList where resourcerequired equals to UPDATED_RESOURCEREQUIRED
        defaultDealRequirementsShouldNotBeFound("resourcerequired.in=" + UPDATED_RESOURCEREQUIRED);
    }

    @Test
    @Transactional
    void getAllDealRequirementsByResourcerequiredIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where resourcerequired is not null
        defaultDealRequirementsShouldBeFound("resourcerequired.specified=true");

        // Get all the dealRequirementsList where resourcerequired is null
        defaultDealRequirementsShouldNotBeFound("resourcerequired.specified=false");
    }

    @Test
    @Transactional
    void getAllDealRequirementsByResourcerequiredIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where resourcerequired is greater than or equal to DEFAULT_RESOURCEREQUIRED
        defaultDealRequirementsShouldBeFound("resourcerequired.greaterThanOrEqual=" + DEFAULT_RESOURCEREQUIRED);

        // Get all the dealRequirementsList where resourcerequired is greater than or equal to UPDATED_RESOURCEREQUIRED
        defaultDealRequirementsShouldNotBeFound("resourcerequired.greaterThanOrEqual=" + UPDATED_RESOURCEREQUIRED);
    }

    @Test
    @Transactional
    void getAllDealRequirementsByResourcerequiredIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where resourcerequired is less than or equal to DEFAULT_RESOURCEREQUIRED
        defaultDealRequirementsShouldBeFound("resourcerequired.lessThanOrEqual=" + DEFAULT_RESOURCEREQUIRED);

        // Get all the dealRequirementsList where resourcerequired is less than or equal to SMALLER_RESOURCEREQUIRED
        defaultDealRequirementsShouldNotBeFound("resourcerequired.lessThanOrEqual=" + SMALLER_RESOURCEREQUIRED);
    }

    @Test
    @Transactional
    void getAllDealRequirementsByResourcerequiredIsLessThanSomething() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where resourcerequired is less than DEFAULT_RESOURCEREQUIRED
        defaultDealRequirementsShouldNotBeFound("resourcerequired.lessThan=" + DEFAULT_RESOURCEREQUIRED);

        // Get all the dealRequirementsList where resourcerequired is less than UPDATED_RESOURCEREQUIRED
        defaultDealRequirementsShouldBeFound("resourcerequired.lessThan=" + UPDATED_RESOURCEREQUIRED);
    }

    @Test
    @Transactional
    void getAllDealRequirementsByResourcerequiredIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where resourcerequired is greater than DEFAULT_RESOURCEREQUIRED
        defaultDealRequirementsShouldNotBeFound("resourcerequired.greaterThan=" + DEFAULT_RESOURCEREQUIRED);

        // Get all the dealRequirementsList where resourcerequired is greater than SMALLER_RESOURCEREQUIRED
        defaultDealRequirementsShouldBeFound("resourcerequired.greaterThan=" + SMALLER_RESOURCEREQUIRED);
    }

    @Test
    @Transactional
    void getAllDealRequirementsByMinexperiencelevelIsEqualToSomething() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where minexperiencelevel equals to DEFAULT_MINEXPERIENCELEVEL
        defaultDealRequirementsShouldBeFound("minexperiencelevel.equals=" + DEFAULT_MINEXPERIENCELEVEL);

        // Get all the dealRequirementsList where minexperiencelevel equals to UPDATED_MINEXPERIENCELEVEL
        defaultDealRequirementsShouldNotBeFound("minexperiencelevel.equals=" + UPDATED_MINEXPERIENCELEVEL);
    }

    @Test
    @Transactional
    void getAllDealRequirementsByMinexperiencelevelIsInShouldWork() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where minexperiencelevel in DEFAULT_MINEXPERIENCELEVEL or UPDATED_MINEXPERIENCELEVEL
        defaultDealRequirementsShouldBeFound("minexperiencelevel.in=" + DEFAULT_MINEXPERIENCELEVEL + "," + UPDATED_MINEXPERIENCELEVEL);

        // Get all the dealRequirementsList where minexperiencelevel equals to UPDATED_MINEXPERIENCELEVEL
        defaultDealRequirementsShouldNotBeFound("minexperiencelevel.in=" + UPDATED_MINEXPERIENCELEVEL);
    }

    @Test
    @Transactional
    void getAllDealRequirementsByMinexperiencelevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where minexperiencelevel is not null
        defaultDealRequirementsShouldBeFound("minexperiencelevel.specified=true");

        // Get all the dealRequirementsList where minexperiencelevel is null
        defaultDealRequirementsShouldNotBeFound("minexperiencelevel.specified=false");
    }

    @Test
    @Transactional
    void getAllDealRequirementsByMinexperiencelevelContainsSomething() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where minexperiencelevel contains DEFAULT_MINEXPERIENCELEVEL
        defaultDealRequirementsShouldBeFound("minexperiencelevel.contains=" + DEFAULT_MINEXPERIENCELEVEL);

        // Get all the dealRequirementsList where minexperiencelevel contains UPDATED_MINEXPERIENCELEVEL
        defaultDealRequirementsShouldNotBeFound("minexperiencelevel.contains=" + UPDATED_MINEXPERIENCELEVEL);
    }

    @Test
    @Transactional
    void getAllDealRequirementsByMinexperiencelevelNotContainsSomething() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where minexperiencelevel does not contain DEFAULT_MINEXPERIENCELEVEL
        defaultDealRequirementsShouldNotBeFound("minexperiencelevel.doesNotContain=" + DEFAULT_MINEXPERIENCELEVEL);

        // Get all the dealRequirementsList where minexperiencelevel does not contain UPDATED_MINEXPERIENCELEVEL
        defaultDealRequirementsShouldBeFound("minexperiencelevel.doesNotContain=" + UPDATED_MINEXPERIENCELEVEL);
    }

    @Test
    @Transactional
    void getAllDealRequirementsByMaxexperiencelevelIsEqualToSomething() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where maxexperiencelevel equals to DEFAULT_MAXEXPERIENCELEVEL
        defaultDealRequirementsShouldBeFound("maxexperiencelevel.equals=" + DEFAULT_MAXEXPERIENCELEVEL);

        // Get all the dealRequirementsList where maxexperiencelevel equals to UPDATED_MAXEXPERIENCELEVEL
        defaultDealRequirementsShouldNotBeFound("maxexperiencelevel.equals=" + UPDATED_MAXEXPERIENCELEVEL);
    }

    @Test
    @Transactional
    void getAllDealRequirementsByMaxexperiencelevelIsInShouldWork() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where maxexperiencelevel in DEFAULT_MAXEXPERIENCELEVEL or UPDATED_MAXEXPERIENCELEVEL
        defaultDealRequirementsShouldBeFound("maxexperiencelevel.in=" + DEFAULT_MAXEXPERIENCELEVEL + "," + UPDATED_MAXEXPERIENCELEVEL);

        // Get all the dealRequirementsList where maxexperiencelevel equals to UPDATED_MAXEXPERIENCELEVEL
        defaultDealRequirementsShouldNotBeFound("maxexperiencelevel.in=" + UPDATED_MAXEXPERIENCELEVEL);
    }

    @Test
    @Transactional
    void getAllDealRequirementsByMaxexperiencelevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where maxexperiencelevel is not null
        defaultDealRequirementsShouldBeFound("maxexperiencelevel.specified=true");

        // Get all the dealRequirementsList where maxexperiencelevel is null
        defaultDealRequirementsShouldNotBeFound("maxexperiencelevel.specified=false");
    }

    @Test
    @Transactional
    void getAllDealRequirementsByMaxexperiencelevelContainsSomething() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where maxexperiencelevel contains DEFAULT_MAXEXPERIENCELEVEL
        defaultDealRequirementsShouldBeFound("maxexperiencelevel.contains=" + DEFAULT_MAXEXPERIENCELEVEL);

        // Get all the dealRequirementsList where maxexperiencelevel contains UPDATED_MAXEXPERIENCELEVEL
        defaultDealRequirementsShouldNotBeFound("maxexperiencelevel.contains=" + UPDATED_MAXEXPERIENCELEVEL);
    }

    @Test
    @Transactional
    void getAllDealRequirementsByMaxexperiencelevelNotContainsSomething() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where maxexperiencelevel does not contain DEFAULT_MAXEXPERIENCELEVEL
        defaultDealRequirementsShouldNotBeFound("maxexperiencelevel.doesNotContain=" + DEFAULT_MAXEXPERIENCELEVEL);

        // Get all the dealRequirementsList where maxexperiencelevel does not contain UPDATED_MAXEXPERIENCELEVEL
        defaultDealRequirementsShouldBeFound("maxexperiencelevel.doesNotContain=" + UPDATED_MAXEXPERIENCELEVEL);
    }

    @Test
    @Transactional
    void getAllDealRequirementsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where createdat equals to DEFAULT_CREATEDAT
        defaultDealRequirementsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the dealRequirementsList where createdat equals to UPDATED_CREATEDAT
        defaultDealRequirementsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllDealRequirementsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultDealRequirementsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the dealRequirementsList where createdat equals to UPDATED_CREATEDAT
        defaultDealRequirementsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllDealRequirementsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where createdat is not null
        defaultDealRequirementsShouldBeFound("createdat.specified=true");

        // Get all the dealRequirementsList where createdat is null
        defaultDealRequirementsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllDealRequirementsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultDealRequirementsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the dealRequirementsList where updatedat equals to UPDATED_UPDATEDAT
        defaultDealRequirementsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllDealRequirementsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultDealRequirementsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the dealRequirementsList where updatedat equals to UPDATED_UPDATEDAT
        defaultDealRequirementsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllDealRequirementsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where updatedat is not null
        defaultDealRequirementsShouldBeFound("updatedat.specified=true");

        // Get all the dealRequirementsList where updatedat is null
        defaultDealRequirementsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllDealRequirementsByDeletedatIsEqualToSomething() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where deletedat equals to DEFAULT_DELETEDAT
        defaultDealRequirementsShouldBeFound("deletedat.equals=" + DEFAULT_DELETEDAT);

        // Get all the dealRequirementsList where deletedat equals to UPDATED_DELETEDAT
        defaultDealRequirementsShouldNotBeFound("deletedat.equals=" + UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void getAllDealRequirementsByDeletedatIsInShouldWork() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where deletedat in DEFAULT_DELETEDAT or UPDATED_DELETEDAT
        defaultDealRequirementsShouldBeFound("deletedat.in=" + DEFAULT_DELETEDAT + "," + UPDATED_DELETEDAT);

        // Get all the dealRequirementsList where deletedat equals to UPDATED_DELETEDAT
        defaultDealRequirementsShouldNotBeFound("deletedat.in=" + UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void getAllDealRequirementsByDeletedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        // Get all the dealRequirementsList where deletedat is not null
        defaultDealRequirementsShouldBeFound("deletedat.specified=true");

        // Get all the dealRequirementsList where deletedat is null
        defaultDealRequirementsShouldNotBeFound("deletedat.specified=false");
    }

    @Test
    @Transactional
    void getAllDealRequirementsByDealIsEqualToSomething() throws Exception {
        Deals deal;
        if (TestUtil.findAll(em, Deals.class).isEmpty()) {
            dealRequirementsRepository.saveAndFlush(dealRequirements);
            deal = DealsResourceIT.createEntity(em);
        } else {
            deal = TestUtil.findAll(em, Deals.class).get(0);
        }
        em.persist(deal);
        em.flush();
        dealRequirements.setDeal(deal);
        dealRequirementsRepository.saveAndFlush(dealRequirements);
        Long dealId = deal.getId();

        // Get all the dealRequirementsList where deal equals to dealId
        defaultDealRequirementsShouldBeFound("dealId.equals=" + dealId);

        // Get all the dealRequirementsList where deal equals to (dealId + 1)
        defaultDealRequirementsShouldNotBeFound("dealId.equals=" + (dealId + 1));
    }

    @Test
    @Transactional
    void getAllDealRequirementsByDealrequirementmatchingresourcesDealrequirementIsEqualToSomething() throws Exception {
        DealRequirementMatchingResources dealrequirementmatchingresourcesDealrequirement;
        if (TestUtil.findAll(em, DealRequirementMatchingResources.class).isEmpty()) {
            dealRequirementsRepository.saveAndFlush(dealRequirements);
            dealrequirementmatchingresourcesDealrequirement = DealRequirementMatchingResourcesResourceIT.createEntity(em);
        } else {
            dealrequirementmatchingresourcesDealrequirement = TestUtil.findAll(em, DealRequirementMatchingResources.class).get(0);
        }
        em.persist(dealrequirementmatchingresourcesDealrequirement);
        em.flush();
        dealRequirements.addDealrequirementmatchingresourcesDealrequirement(dealrequirementmatchingresourcesDealrequirement);
        dealRequirementsRepository.saveAndFlush(dealRequirements);
        Long dealrequirementmatchingresourcesDealrequirementId = dealrequirementmatchingresourcesDealrequirement.getId();

        // Get all the dealRequirementsList where dealrequirementmatchingresourcesDealrequirement equals to dealrequirementmatchingresourcesDealrequirementId
        defaultDealRequirementsShouldBeFound(
            "dealrequirementmatchingresourcesDealrequirementId.equals=" + dealrequirementmatchingresourcesDealrequirementId
        );

        // Get all the dealRequirementsList where dealrequirementmatchingresourcesDealrequirement equals to (dealrequirementmatchingresourcesDealrequirementId + 1)
        defaultDealRequirementsShouldNotBeFound(
            "dealrequirementmatchingresourcesDealrequirementId.equals=" + (dealrequirementmatchingresourcesDealrequirementId + 1)
        );
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDealRequirementsShouldBeFound(String filter) throws Exception {
        restDealRequirementsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dealRequirements.getId().intValue())))
            .andExpect(jsonPath("$.[*].dealreqidentifier").value(hasItem(DEFAULT_DEALREQIDENTIFIER)))
            .andExpect(jsonPath("$.[*].competencyname").value(hasItem(DEFAULT_COMPETENCYNAME)))
            .andExpect(jsonPath("$.[*].skills").value(hasItem(DEFAULT_SKILLS)))
            .andExpect(jsonPath("$.[*].resourcerequired").value(hasItem(DEFAULT_RESOURCEREQUIRED.doubleValue())))
            .andExpect(jsonPath("$.[*].minexperiencelevel").value(hasItem(DEFAULT_MINEXPERIENCELEVEL)))
            .andExpect(jsonPath("$.[*].maxexperiencelevel").value(hasItem(DEFAULT_MAXEXPERIENCELEVEL)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].deletedat").value(hasItem(DEFAULT_DELETEDAT.toString())));

        // Check, that the count call also returns 1
        restDealRequirementsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDealRequirementsShouldNotBeFound(String filter) throws Exception {
        restDealRequirementsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDealRequirementsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDealRequirements() throws Exception {
        // Get the dealRequirements
        restDealRequirementsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDealRequirements() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        int databaseSizeBeforeUpdate = dealRequirementsRepository.findAll().size();

        // Update the dealRequirements
        DealRequirements updatedDealRequirements = dealRequirementsRepository.findById(dealRequirements.getId()).get();
        // Disconnect from session so that the updates on updatedDealRequirements are not directly saved in db
        em.detach(updatedDealRequirements);
        updatedDealRequirements
            .dealreqidentifier(UPDATED_DEALREQIDENTIFIER)
            .competencyname(UPDATED_COMPETENCYNAME)
            .skills(UPDATED_SKILLS)
            .resourcerequired(UPDATED_RESOURCEREQUIRED)
            .minexperiencelevel(UPDATED_MINEXPERIENCELEVEL)
            .maxexperiencelevel(UPDATED_MAXEXPERIENCELEVEL)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .deletedat(UPDATED_DELETEDAT);

        restDealRequirementsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDealRequirements.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDealRequirements))
            )
            .andExpect(status().isOk());

        // Validate the DealRequirements in the database
        List<DealRequirements> dealRequirementsList = dealRequirementsRepository.findAll();
        assertThat(dealRequirementsList).hasSize(databaseSizeBeforeUpdate);
        DealRequirements testDealRequirements = dealRequirementsList.get(dealRequirementsList.size() - 1);
        assertThat(testDealRequirements.getDealreqidentifier()).isEqualTo(UPDATED_DEALREQIDENTIFIER);
        assertThat(testDealRequirements.getCompetencyname()).isEqualTo(UPDATED_COMPETENCYNAME);
        assertThat(testDealRequirements.getSkills()).isEqualTo(UPDATED_SKILLS);
        assertThat(testDealRequirements.getResourcerequired()).isEqualTo(UPDATED_RESOURCEREQUIRED);
        assertThat(testDealRequirements.getMinexperiencelevel()).isEqualTo(UPDATED_MINEXPERIENCELEVEL);
        assertThat(testDealRequirements.getMaxexperiencelevel()).isEqualTo(UPDATED_MAXEXPERIENCELEVEL);
        assertThat(testDealRequirements.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testDealRequirements.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testDealRequirements.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void putNonExistingDealRequirements() throws Exception {
        int databaseSizeBeforeUpdate = dealRequirementsRepository.findAll().size();
        dealRequirements.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDealRequirementsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dealRequirements.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealRequirements))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealRequirements in the database
        List<DealRequirements> dealRequirementsList = dealRequirementsRepository.findAll();
        assertThat(dealRequirementsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDealRequirements() throws Exception {
        int databaseSizeBeforeUpdate = dealRequirementsRepository.findAll().size();
        dealRequirements.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealRequirementsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealRequirements))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealRequirements in the database
        List<DealRequirements> dealRequirementsList = dealRequirementsRepository.findAll();
        assertThat(dealRequirementsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDealRequirements() throws Exception {
        int databaseSizeBeforeUpdate = dealRequirementsRepository.findAll().size();
        dealRequirements.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealRequirementsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealRequirements))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DealRequirements in the database
        List<DealRequirements> dealRequirementsList = dealRequirementsRepository.findAll();
        assertThat(dealRequirementsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDealRequirementsWithPatch() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        int databaseSizeBeforeUpdate = dealRequirementsRepository.findAll().size();

        // Update the dealRequirements using partial update
        DealRequirements partialUpdatedDealRequirements = new DealRequirements();
        partialUpdatedDealRequirements.setId(dealRequirements.getId());

        partialUpdatedDealRequirements
            .competencyname(UPDATED_COMPETENCYNAME)
            .resourcerequired(UPDATED_RESOURCEREQUIRED)
            .updatedat(UPDATED_UPDATEDAT);

        restDealRequirementsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDealRequirements.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDealRequirements))
            )
            .andExpect(status().isOk());

        // Validate the DealRequirements in the database
        List<DealRequirements> dealRequirementsList = dealRequirementsRepository.findAll();
        assertThat(dealRequirementsList).hasSize(databaseSizeBeforeUpdate);
        DealRequirements testDealRequirements = dealRequirementsList.get(dealRequirementsList.size() - 1);
        assertThat(testDealRequirements.getDealreqidentifier()).isEqualTo(DEFAULT_DEALREQIDENTIFIER);
        assertThat(testDealRequirements.getCompetencyname()).isEqualTo(UPDATED_COMPETENCYNAME);
        assertThat(testDealRequirements.getSkills()).isEqualTo(DEFAULT_SKILLS);
        assertThat(testDealRequirements.getResourcerequired()).isEqualTo(UPDATED_RESOURCEREQUIRED);
        assertThat(testDealRequirements.getMinexperiencelevel()).isEqualTo(DEFAULT_MINEXPERIENCELEVEL);
        assertThat(testDealRequirements.getMaxexperiencelevel()).isEqualTo(DEFAULT_MAXEXPERIENCELEVEL);
        assertThat(testDealRequirements.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testDealRequirements.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testDealRequirements.getDeletedat()).isEqualTo(DEFAULT_DELETEDAT);
    }

    @Test
    @Transactional
    void fullUpdateDealRequirementsWithPatch() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        int databaseSizeBeforeUpdate = dealRequirementsRepository.findAll().size();

        // Update the dealRequirements using partial update
        DealRequirements partialUpdatedDealRequirements = new DealRequirements();
        partialUpdatedDealRequirements.setId(dealRequirements.getId());

        partialUpdatedDealRequirements
            .dealreqidentifier(UPDATED_DEALREQIDENTIFIER)
            .competencyname(UPDATED_COMPETENCYNAME)
            .skills(UPDATED_SKILLS)
            .resourcerequired(UPDATED_RESOURCEREQUIRED)
            .minexperiencelevel(UPDATED_MINEXPERIENCELEVEL)
            .maxexperiencelevel(UPDATED_MAXEXPERIENCELEVEL)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .deletedat(UPDATED_DELETEDAT);

        restDealRequirementsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDealRequirements.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDealRequirements))
            )
            .andExpect(status().isOk());

        // Validate the DealRequirements in the database
        List<DealRequirements> dealRequirementsList = dealRequirementsRepository.findAll();
        assertThat(dealRequirementsList).hasSize(databaseSizeBeforeUpdate);
        DealRequirements testDealRequirements = dealRequirementsList.get(dealRequirementsList.size() - 1);
        assertThat(testDealRequirements.getDealreqidentifier()).isEqualTo(UPDATED_DEALREQIDENTIFIER);
        assertThat(testDealRequirements.getCompetencyname()).isEqualTo(UPDATED_COMPETENCYNAME);
        assertThat(testDealRequirements.getSkills()).isEqualTo(UPDATED_SKILLS);
        assertThat(testDealRequirements.getResourcerequired()).isEqualTo(UPDATED_RESOURCEREQUIRED);
        assertThat(testDealRequirements.getMinexperiencelevel()).isEqualTo(UPDATED_MINEXPERIENCELEVEL);
        assertThat(testDealRequirements.getMaxexperiencelevel()).isEqualTo(UPDATED_MAXEXPERIENCELEVEL);
        assertThat(testDealRequirements.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testDealRequirements.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testDealRequirements.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingDealRequirements() throws Exception {
        int databaseSizeBeforeUpdate = dealRequirementsRepository.findAll().size();
        dealRequirements.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDealRequirementsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dealRequirements.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dealRequirements))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealRequirements in the database
        List<DealRequirements> dealRequirementsList = dealRequirementsRepository.findAll();
        assertThat(dealRequirementsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDealRequirements() throws Exception {
        int databaseSizeBeforeUpdate = dealRequirementsRepository.findAll().size();
        dealRequirements.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealRequirementsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dealRequirements))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealRequirements in the database
        List<DealRequirements> dealRequirementsList = dealRequirementsRepository.findAll();
        assertThat(dealRequirementsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDealRequirements() throws Exception {
        int databaseSizeBeforeUpdate = dealRequirementsRepository.findAll().size();
        dealRequirements.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealRequirementsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dealRequirements))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DealRequirements in the database
        List<DealRequirements> dealRequirementsList = dealRequirementsRepository.findAll();
        assertThat(dealRequirementsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDealRequirements() throws Exception {
        // Initialize the database
        dealRequirementsRepository.saveAndFlush(dealRequirements);

        int databaseSizeBeforeDelete = dealRequirementsRepository.findAll().size();

        // Delete the dealRequirements
        restDealRequirementsMockMvc
            .perform(delete(ENTITY_API_URL_ID, dealRequirements.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DealRequirements> dealRequirementsList = dealRequirementsRepository.findAll();
        assertThat(dealRequirementsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
