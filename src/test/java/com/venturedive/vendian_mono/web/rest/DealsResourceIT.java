package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.DealRequirements;
import com.venturedive.vendian_mono.domain.Deals;
import com.venturedive.vendian_mono.repository.DealsRepository;
import com.venturedive.vendian_mono.service.criteria.DealsCriteria;
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
 * Integration tests for the {@link DealsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DealsResourceIT {

    private static final String DEFAULT_DEALNUMBER = "AAAAAAAAAA";
    private static final String UPDATED_DEALNUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_DEALNAME = "AAAAAAAAAA";
    private static final String UPDATED_DEALNAME = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESSUNIT = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESSUNIT = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENTNAME = "AAAAAAAAAA";
    private static final String UPDATED_CLIENTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_DEALOWNER = "AAAAAAAAAA";
    private static final String UPDATED_DEALOWNER = "BBBBBBBBBB";

    private static final String DEFAULT_PROPOSALTYPE = "AAAAAAAAAA";
    private static final String UPDATED_PROPOSALTYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_PROJECTID = 1;
    private static final Integer UPDATED_PROJECTID = 2;
    private static final Integer SMALLER_PROJECTID = 1 - 1;

    private static final Instant DEFAULT_EXPECTEDSTARTDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPECTEDSTARTDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_STAGE = "AAAAAAAAAA";
    private static final String UPDATED_STAGE = "BBBBBBBBBB";

    private static final Double DEFAULT_PROBABILITY = 1D;
    private static final Double UPDATED_PROBABILITY = 2D;
    private static final Double SMALLER_PROBABILITY = 1D - 1D;

    private static final Double DEFAULT_PROJECTDURATION = 1D;
    private static final Double UPDATED_PROJECTDURATION = 2D;
    private static final Double SMALLER_PROJECTDURATION = 1D - 1D;

    private static final String DEFAULT_TYPE = "AAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBB";

    private static final Instant DEFAULT_CLOSEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CLOSEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_RESOURCINGENTEREDINVENDIANS = false;
    private static final Boolean UPDATED_RESOURCINGENTEREDINVENDIANS = true;

    private static final String ENTITY_API_URL = "/api/deals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DealsRepository dealsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDealsMockMvc;

    private Deals deals;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deals createEntity(EntityManager em) {
        Deals deals = new Deals()
            .dealnumber(DEFAULT_DEALNUMBER)
            .dealname(DEFAULT_DEALNAME)
            .businessunit(DEFAULT_BUSINESSUNIT)
            .clientname(DEFAULT_CLIENTNAME)
            .dealowner(DEFAULT_DEALOWNER)
            .proposaltype(DEFAULT_PROPOSALTYPE)
            .projectid(DEFAULT_PROJECTID)
            .expectedstartdate(DEFAULT_EXPECTEDSTARTDATE)
            .stage(DEFAULT_STAGE)
            .probability(DEFAULT_PROBABILITY)
            .projectduration(DEFAULT_PROJECTDURATION)
            .type(DEFAULT_TYPE)
            .status(DEFAULT_STATUS)
            .closedat(DEFAULT_CLOSEDAT)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT)
            .deletedat(DEFAULT_DELETEDAT)
            .resourcingenteredinvendians(DEFAULT_RESOURCINGENTEREDINVENDIANS);
        return deals;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deals createUpdatedEntity(EntityManager em) {
        Deals deals = new Deals()
            .dealnumber(UPDATED_DEALNUMBER)
            .dealname(UPDATED_DEALNAME)
            .businessunit(UPDATED_BUSINESSUNIT)
            .clientname(UPDATED_CLIENTNAME)
            .dealowner(UPDATED_DEALOWNER)
            .proposaltype(UPDATED_PROPOSALTYPE)
            .projectid(UPDATED_PROJECTID)
            .expectedstartdate(UPDATED_EXPECTEDSTARTDATE)
            .stage(UPDATED_STAGE)
            .probability(UPDATED_PROBABILITY)
            .projectduration(UPDATED_PROJECTDURATION)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS)
            .closedat(UPDATED_CLOSEDAT)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .deletedat(UPDATED_DELETEDAT)
            .resourcingenteredinvendians(UPDATED_RESOURCINGENTEREDINVENDIANS);
        return deals;
    }

    @BeforeEach
    public void initTest() {
        deals = createEntity(em);
    }

    @Test
    @Transactional
    void createDeals() throws Exception {
        int databaseSizeBeforeCreate = dealsRepository.findAll().size();
        // Create the Deals
        restDealsMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deals))
            )
            .andExpect(status().isCreated());

        // Validate the Deals in the database
        List<Deals> dealsList = dealsRepository.findAll();
        assertThat(dealsList).hasSize(databaseSizeBeforeCreate + 1);
        Deals testDeals = dealsList.get(dealsList.size() - 1);
        assertThat(testDeals.getDealnumber()).isEqualTo(DEFAULT_DEALNUMBER);
        assertThat(testDeals.getDealname()).isEqualTo(DEFAULT_DEALNAME);
        assertThat(testDeals.getBusinessunit()).isEqualTo(DEFAULT_BUSINESSUNIT);
        assertThat(testDeals.getClientname()).isEqualTo(DEFAULT_CLIENTNAME);
        assertThat(testDeals.getDealowner()).isEqualTo(DEFAULT_DEALOWNER);
        assertThat(testDeals.getProposaltype()).isEqualTo(DEFAULT_PROPOSALTYPE);
        assertThat(testDeals.getProjectid()).isEqualTo(DEFAULT_PROJECTID);
        assertThat(testDeals.getExpectedstartdate()).isEqualTo(DEFAULT_EXPECTEDSTARTDATE);
        assertThat(testDeals.getStage()).isEqualTo(DEFAULT_STAGE);
        assertThat(testDeals.getProbability()).isEqualTo(DEFAULT_PROBABILITY);
        assertThat(testDeals.getProjectduration()).isEqualTo(DEFAULT_PROJECTDURATION);
        assertThat(testDeals.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDeals.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDeals.getClosedat()).isEqualTo(DEFAULT_CLOSEDAT);
        assertThat(testDeals.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testDeals.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testDeals.getDeletedat()).isEqualTo(DEFAULT_DELETEDAT);
        assertThat(testDeals.getResourcingenteredinvendians()).isEqualTo(DEFAULT_RESOURCINGENTEREDINVENDIANS);
    }

    @Test
    @Transactional
    void createDealsWithExistingId() throws Exception {
        // Create the Deals with an existing ID
        deals.setId(1L);

        int databaseSizeBeforeCreate = dealsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDealsMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deals))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deals in the database
        List<Deals> dealsList = dealsRepository.findAll();
        assertThat(dealsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDealnumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealsRepository.findAll().size();
        // set the field null
        deals.setDealnumber(null);

        // Create the Deals, which fails.

        restDealsMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deals))
            )
            .andExpect(status().isBadRequest());

        List<Deals> dealsList = dealsRepository.findAll();
        assertThat(dealsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDealnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealsRepository.findAll().size();
        // set the field null
        deals.setDealname(null);

        // Create the Deals, which fails.

        restDealsMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deals))
            )
            .andExpect(status().isBadRequest());

        List<Deals> dealsList = dealsRepository.findAll();
        assertThat(dealsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkClientnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealsRepository.findAll().size();
        // set the field null
        deals.setClientname(null);

        // Create the Deals, which fails.

        restDealsMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deals))
            )
            .andExpect(status().isBadRequest());

        List<Deals> dealsList = dealsRepository.findAll();
        assertThat(dealsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkExpectedstartdateIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealsRepository.findAll().size();
        // set the field null
        deals.setExpectedstartdate(null);

        // Create the Deals, which fails.

        restDealsMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deals))
            )
            .andExpect(status().isBadRequest());

        List<Deals> dealsList = dealsRepository.findAll();
        assertThat(dealsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkProbabilityIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealsRepository.findAll().size();
        // set the field null
        deals.setProbability(null);

        // Create the Deals, which fails.

        restDealsMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deals))
            )
            .andExpect(status().isBadRequest());

        List<Deals> dealsList = dealsRepository.findAll();
        assertThat(dealsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkProjectdurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealsRepository.findAll().size();
        // set the field null
        deals.setProjectduration(null);

        // Create the Deals, which fails.

        restDealsMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deals))
            )
            .andExpect(status().isBadRequest());

        List<Deals> dealsList = dealsRepository.findAll();
        assertThat(dealsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealsRepository.findAll().size();
        // set the field null
        deals.setType(null);

        // Create the Deals, which fails.

        restDealsMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deals))
            )
            .andExpect(status().isBadRequest());

        List<Deals> dealsList = dealsRepository.findAll();
        assertThat(dealsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealsRepository.findAll().size();
        // set the field null
        deals.setStatus(null);

        // Create the Deals, which fails.

        restDealsMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deals))
            )
            .andExpect(status().isBadRequest());

        List<Deals> dealsList = dealsRepository.findAll();
        assertThat(dealsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDeals() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList
        restDealsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deals.getId().intValue())))
            .andExpect(jsonPath("$.[*].dealnumber").value(hasItem(DEFAULT_DEALNUMBER)))
            .andExpect(jsonPath("$.[*].dealname").value(hasItem(DEFAULT_DEALNAME)))
            .andExpect(jsonPath("$.[*].businessunit").value(hasItem(DEFAULT_BUSINESSUNIT)))
            .andExpect(jsonPath("$.[*].clientname").value(hasItem(DEFAULT_CLIENTNAME)))
            .andExpect(jsonPath("$.[*].dealowner").value(hasItem(DEFAULT_DEALOWNER)))
            .andExpect(jsonPath("$.[*].proposaltype").value(hasItem(DEFAULT_PROPOSALTYPE)))
            .andExpect(jsonPath("$.[*].projectid").value(hasItem(DEFAULT_PROJECTID)))
            .andExpect(jsonPath("$.[*].expectedstartdate").value(hasItem(DEFAULT_EXPECTEDSTARTDATE.toString())))
            .andExpect(jsonPath("$.[*].stage").value(hasItem(DEFAULT_STAGE)))
            .andExpect(jsonPath("$.[*].probability").value(hasItem(DEFAULT_PROBABILITY.doubleValue())))
            .andExpect(jsonPath("$.[*].projectduration").value(hasItem(DEFAULT_PROJECTDURATION.doubleValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].closedat").value(hasItem(DEFAULT_CLOSEDAT.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].deletedat").value(hasItem(DEFAULT_DELETEDAT.toString())))
            .andExpect(jsonPath("$.[*].resourcingenteredinvendians").value(hasItem(DEFAULT_RESOURCINGENTEREDINVENDIANS.booleanValue())));
    }

    @Test
    @Transactional
    void getDeals() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get the deals
        restDealsMockMvc
            .perform(get(ENTITY_API_URL_ID, deals.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deals.getId().intValue()))
            .andExpect(jsonPath("$.dealnumber").value(DEFAULT_DEALNUMBER))
            .andExpect(jsonPath("$.dealname").value(DEFAULT_DEALNAME))
            .andExpect(jsonPath("$.businessunit").value(DEFAULT_BUSINESSUNIT))
            .andExpect(jsonPath("$.clientname").value(DEFAULT_CLIENTNAME))
            .andExpect(jsonPath("$.dealowner").value(DEFAULT_DEALOWNER))
            .andExpect(jsonPath("$.proposaltype").value(DEFAULT_PROPOSALTYPE))
            .andExpect(jsonPath("$.projectid").value(DEFAULT_PROJECTID))
            .andExpect(jsonPath("$.expectedstartdate").value(DEFAULT_EXPECTEDSTARTDATE.toString()))
            .andExpect(jsonPath("$.stage").value(DEFAULT_STAGE))
            .andExpect(jsonPath("$.probability").value(DEFAULT_PROBABILITY.doubleValue()))
            .andExpect(jsonPath("$.projectduration").value(DEFAULT_PROJECTDURATION.doubleValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.closedat").value(DEFAULT_CLOSEDAT.toString()))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()))
            .andExpect(jsonPath("$.deletedat").value(DEFAULT_DELETEDAT.toString()))
            .andExpect(jsonPath("$.resourcingenteredinvendians").value(DEFAULT_RESOURCINGENTEREDINVENDIANS.booleanValue()));
    }

    @Test
    @Transactional
    void getDealsByIdFiltering() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        Long id = deals.getId();

        defaultDealsShouldBeFound("id.equals=" + id);
        defaultDealsShouldNotBeFound("id.notEquals=" + id);

        defaultDealsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDealsShouldNotBeFound("id.greaterThan=" + id);

        defaultDealsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDealsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDealsByDealnumberIsEqualToSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where dealnumber equals to DEFAULT_DEALNUMBER
        defaultDealsShouldBeFound("dealnumber.equals=" + DEFAULT_DEALNUMBER);

        // Get all the dealsList where dealnumber equals to UPDATED_DEALNUMBER
        defaultDealsShouldNotBeFound("dealnumber.equals=" + UPDATED_DEALNUMBER);
    }

    @Test
    @Transactional
    void getAllDealsByDealnumberIsInShouldWork() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where dealnumber in DEFAULT_DEALNUMBER or UPDATED_DEALNUMBER
        defaultDealsShouldBeFound("dealnumber.in=" + DEFAULT_DEALNUMBER + "," + UPDATED_DEALNUMBER);

        // Get all the dealsList where dealnumber equals to UPDATED_DEALNUMBER
        defaultDealsShouldNotBeFound("dealnumber.in=" + UPDATED_DEALNUMBER);
    }

    @Test
    @Transactional
    void getAllDealsByDealnumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where dealnumber is not null
        defaultDealsShouldBeFound("dealnumber.specified=true");

        // Get all the dealsList where dealnumber is null
        defaultDealsShouldNotBeFound("dealnumber.specified=false");
    }

    @Test
    @Transactional
    void getAllDealsByDealnumberContainsSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where dealnumber contains DEFAULT_DEALNUMBER
        defaultDealsShouldBeFound("dealnumber.contains=" + DEFAULT_DEALNUMBER);

        // Get all the dealsList where dealnumber contains UPDATED_DEALNUMBER
        defaultDealsShouldNotBeFound("dealnumber.contains=" + UPDATED_DEALNUMBER);
    }

    @Test
    @Transactional
    void getAllDealsByDealnumberNotContainsSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where dealnumber does not contain DEFAULT_DEALNUMBER
        defaultDealsShouldNotBeFound("dealnumber.doesNotContain=" + DEFAULT_DEALNUMBER);

        // Get all the dealsList where dealnumber does not contain UPDATED_DEALNUMBER
        defaultDealsShouldBeFound("dealnumber.doesNotContain=" + UPDATED_DEALNUMBER);
    }

    @Test
    @Transactional
    void getAllDealsByDealnameIsEqualToSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where dealname equals to DEFAULT_DEALNAME
        defaultDealsShouldBeFound("dealname.equals=" + DEFAULT_DEALNAME);

        // Get all the dealsList where dealname equals to UPDATED_DEALNAME
        defaultDealsShouldNotBeFound("dealname.equals=" + UPDATED_DEALNAME);
    }

    @Test
    @Transactional
    void getAllDealsByDealnameIsInShouldWork() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where dealname in DEFAULT_DEALNAME or UPDATED_DEALNAME
        defaultDealsShouldBeFound("dealname.in=" + DEFAULT_DEALNAME + "," + UPDATED_DEALNAME);

        // Get all the dealsList where dealname equals to UPDATED_DEALNAME
        defaultDealsShouldNotBeFound("dealname.in=" + UPDATED_DEALNAME);
    }

    @Test
    @Transactional
    void getAllDealsByDealnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where dealname is not null
        defaultDealsShouldBeFound("dealname.specified=true");

        // Get all the dealsList where dealname is null
        defaultDealsShouldNotBeFound("dealname.specified=false");
    }

    @Test
    @Transactional
    void getAllDealsByDealnameContainsSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where dealname contains DEFAULT_DEALNAME
        defaultDealsShouldBeFound("dealname.contains=" + DEFAULT_DEALNAME);

        // Get all the dealsList where dealname contains UPDATED_DEALNAME
        defaultDealsShouldNotBeFound("dealname.contains=" + UPDATED_DEALNAME);
    }

    @Test
    @Transactional
    void getAllDealsByDealnameNotContainsSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where dealname does not contain DEFAULT_DEALNAME
        defaultDealsShouldNotBeFound("dealname.doesNotContain=" + DEFAULT_DEALNAME);

        // Get all the dealsList where dealname does not contain UPDATED_DEALNAME
        defaultDealsShouldBeFound("dealname.doesNotContain=" + UPDATED_DEALNAME);
    }

    @Test
    @Transactional
    void getAllDealsByBusinessunitIsEqualToSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where businessunit equals to DEFAULT_BUSINESSUNIT
        defaultDealsShouldBeFound("businessunit.equals=" + DEFAULT_BUSINESSUNIT);

        // Get all the dealsList where businessunit equals to UPDATED_BUSINESSUNIT
        defaultDealsShouldNotBeFound("businessunit.equals=" + UPDATED_BUSINESSUNIT);
    }

    @Test
    @Transactional
    void getAllDealsByBusinessunitIsInShouldWork() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where businessunit in DEFAULT_BUSINESSUNIT or UPDATED_BUSINESSUNIT
        defaultDealsShouldBeFound("businessunit.in=" + DEFAULT_BUSINESSUNIT + "," + UPDATED_BUSINESSUNIT);

        // Get all the dealsList where businessunit equals to UPDATED_BUSINESSUNIT
        defaultDealsShouldNotBeFound("businessunit.in=" + UPDATED_BUSINESSUNIT);
    }

    @Test
    @Transactional
    void getAllDealsByBusinessunitIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where businessunit is not null
        defaultDealsShouldBeFound("businessunit.specified=true");

        // Get all the dealsList where businessunit is null
        defaultDealsShouldNotBeFound("businessunit.specified=false");
    }

    @Test
    @Transactional
    void getAllDealsByBusinessunitContainsSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where businessunit contains DEFAULT_BUSINESSUNIT
        defaultDealsShouldBeFound("businessunit.contains=" + DEFAULT_BUSINESSUNIT);

        // Get all the dealsList where businessunit contains UPDATED_BUSINESSUNIT
        defaultDealsShouldNotBeFound("businessunit.contains=" + UPDATED_BUSINESSUNIT);
    }

    @Test
    @Transactional
    void getAllDealsByBusinessunitNotContainsSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where businessunit does not contain DEFAULT_BUSINESSUNIT
        defaultDealsShouldNotBeFound("businessunit.doesNotContain=" + DEFAULT_BUSINESSUNIT);

        // Get all the dealsList where businessunit does not contain UPDATED_BUSINESSUNIT
        defaultDealsShouldBeFound("businessunit.doesNotContain=" + UPDATED_BUSINESSUNIT);
    }

    @Test
    @Transactional
    void getAllDealsByClientnameIsEqualToSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where clientname equals to DEFAULT_CLIENTNAME
        defaultDealsShouldBeFound("clientname.equals=" + DEFAULT_CLIENTNAME);

        // Get all the dealsList where clientname equals to UPDATED_CLIENTNAME
        defaultDealsShouldNotBeFound("clientname.equals=" + UPDATED_CLIENTNAME);
    }

    @Test
    @Transactional
    void getAllDealsByClientnameIsInShouldWork() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where clientname in DEFAULT_CLIENTNAME or UPDATED_CLIENTNAME
        defaultDealsShouldBeFound("clientname.in=" + DEFAULT_CLIENTNAME + "," + UPDATED_CLIENTNAME);

        // Get all the dealsList where clientname equals to UPDATED_CLIENTNAME
        defaultDealsShouldNotBeFound("clientname.in=" + UPDATED_CLIENTNAME);
    }

    @Test
    @Transactional
    void getAllDealsByClientnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where clientname is not null
        defaultDealsShouldBeFound("clientname.specified=true");

        // Get all the dealsList where clientname is null
        defaultDealsShouldNotBeFound("clientname.specified=false");
    }

    @Test
    @Transactional
    void getAllDealsByClientnameContainsSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where clientname contains DEFAULT_CLIENTNAME
        defaultDealsShouldBeFound("clientname.contains=" + DEFAULT_CLIENTNAME);

        // Get all the dealsList where clientname contains UPDATED_CLIENTNAME
        defaultDealsShouldNotBeFound("clientname.contains=" + UPDATED_CLIENTNAME);
    }

    @Test
    @Transactional
    void getAllDealsByClientnameNotContainsSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where clientname does not contain DEFAULT_CLIENTNAME
        defaultDealsShouldNotBeFound("clientname.doesNotContain=" + DEFAULT_CLIENTNAME);

        // Get all the dealsList where clientname does not contain UPDATED_CLIENTNAME
        defaultDealsShouldBeFound("clientname.doesNotContain=" + UPDATED_CLIENTNAME);
    }

    @Test
    @Transactional
    void getAllDealsByDealownerIsEqualToSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where dealowner equals to DEFAULT_DEALOWNER
        defaultDealsShouldBeFound("dealowner.equals=" + DEFAULT_DEALOWNER);

        // Get all the dealsList where dealowner equals to UPDATED_DEALOWNER
        defaultDealsShouldNotBeFound("dealowner.equals=" + UPDATED_DEALOWNER);
    }

    @Test
    @Transactional
    void getAllDealsByDealownerIsInShouldWork() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where dealowner in DEFAULT_DEALOWNER or UPDATED_DEALOWNER
        defaultDealsShouldBeFound("dealowner.in=" + DEFAULT_DEALOWNER + "," + UPDATED_DEALOWNER);

        // Get all the dealsList where dealowner equals to UPDATED_DEALOWNER
        defaultDealsShouldNotBeFound("dealowner.in=" + UPDATED_DEALOWNER);
    }

    @Test
    @Transactional
    void getAllDealsByDealownerIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where dealowner is not null
        defaultDealsShouldBeFound("dealowner.specified=true");

        // Get all the dealsList where dealowner is null
        defaultDealsShouldNotBeFound("dealowner.specified=false");
    }

    @Test
    @Transactional
    void getAllDealsByDealownerContainsSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where dealowner contains DEFAULT_DEALOWNER
        defaultDealsShouldBeFound("dealowner.contains=" + DEFAULT_DEALOWNER);

        // Get all the dealsList where dealowner contains UPDATED_DEALOWNER
        defaultDealsShouldNotBeFound("dealowner.contains=" + UPDATED_DEALOWNER);
    }

    @Test
    @Transactional
    void getAllDealsByDealownerNotContainsSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where dealowner does not contain DEFAULT_DEALOWNER
        defaultDealsShouldNotBeFound("dealowner.doesNotContain=" + DEFAULT_DEALOWNER);

        // Get all the dealsList where dealowner does not contain UPDATED_DEALOWNER
        defaultDealsShouldBeFound("dealowner.doesNotContain=" + UPDATED_DEALOWNER);
    }

    @Test
    @Transactional
    void getAllDealsByProposaltypeIsEqualToSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where proposaltype equals to DEFAULT_PROPOSALTYPE
        defaultDealsShouldBeFound("proposaltype.equals=" + DEFAULT_PROPOSALTYPE);

        // Get all the dealsList where proposaltype equals to UPDATED_PROPOSALTYPE
        defaultDealsShouldNotBeFound("proposaltype.equals=" + UPDATED_PROPOSALTYPE);
    }

    @Test
    @Transactional
    void getAllDealsByProposaltypeIsInShouldWork() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where proposaltype in DEFAULT_PROPOSALTYPE or UPDATED_PROPOSALTYPE
        defaultDealsShouldBeFound("proposaltype.in=" + DEFAULT_PROPOSALTYPE + "," + UPDATED_PROPOSALTYPE);

        // Get all the dealsList where proposaltype equals to UPDATED_PROPOSALTYPE
        defaultDealsShouldNotBeFound("proposaltype.in=" + UPDATED_PROPOSALTYPE);
    }

    @Test
    @Transactional
    void getAllDealsByProposaltypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where proposaltype is not null
        defaultDealsShouldBeFound("proposaltype.specified=true");

        // Get all the dealsList where proposaltype is null
        defaultDealsShouldNotBeFound("proposaltype.specified=false");
    }

    @Test
    @Transactional
    void getAllDealsByProposaltypeContainsSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where proposaltype contains DEFAULT_PROPOSALTYPE
        defaultDealsShouldBeFound("proposaltype.contains=" + DEFAULT_PROPOSALTYPE);

        // Get all the dealsList where proposaltype contains UPDATED_PROPOSALTYPE
        defaultDealsShouldNotBeFound("proposaltype.contains=" + UPDATED_PROPOSALTYPE);
    }

    @Test
    @Transactional
    void getAllDealsByProposaltypeNotContainsSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where proposaltype does not contain DEFAULT_PROPOSALTYPE
        defaultDealsShouldNotBeFound("proposaltype.doesNotContain=" + DEFAULT_PROPOSALTYPE);

        // Get all the dealsList where proposaltype does not contain UPDATED_PROPOSALTYPE
        defaultDealsShouldBeFound("proposaltype.doesNotContain=" + UPDATED_PROPOSALTYPE);
    }

    @Test
    @Transactional
    void getAllDealsByProjectidIsEqualToSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where projectid equals to DEFAULT_PROJECTID
        defaultDealsShouldBeFound("projectid.equals=" + DEFAULT_PROJECTID);

        // Get all the dealsList where projectid equals to UPDATED_PROJECTID
        defaultDealsShouldNotBeFound("projectid.equals=" + UPDATED_PROJECTID);
    }

    @Test
    @Transactional
    void getAllDealsByProjectidIsInShouldWork() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where projectid in DEFAULT_PROJECTID or UPDATED_PROJECTID
        defaultDealsShouldBeFound("projectid.in=" + DEFAULT_PROJECTID + "," + UPDATED_PROJECTID);

        // Get all the dealsList where projectid equals to UPDATED_PROJECTID
        defaultDealsShouldNotBeFound("projectid.in=" + UPDATED_PROJECTID);
    }

    @Test
    @Transactional
    void getAllDealsByProjectidIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where projectid is not null
        defaultDealsShouldBeFound("projectid.specified=true");

        // Get all the dealsList where projectid is null
        defaultDealsShouldNotBeFound("projectid.specified=false");
    }

    @Test
    @Transactional
    void getAllDealsByProjectidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where projectid is greater than or equal to DEFAULT_PROJECTID
        defaultDealsShouldBeFound("projectid.greaterThanOrEqual=" + DEFAULT_PROJECTID);

        // Get all the dealsList where projectid is greater than or equal to UPDATED_PROJECTID
        defaultDealsShouldNotBeFound("projectid.greaterThanOrEqual=" + UPDATED_PROJECTID);
    }

    @Test
    @Transactional
    void getAllDealsByProjectidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where projectid is less than or equal to DEFAULT_PROJECTID
        defaultDealsShouldBeFound("projectid.lessThanOrEqual=" + DEFAULT_PROJECTID);

        // Get all the dealsList where projectid is less than or equal to SMALLER_PROJECTID
        defaultDealsShouldNotBeFound("projectid.lessThanOrEqual=" + SMALLER_PROJECTID);
    }

    @Test
    @Transactional
    void getAllDealsByProjectidIsLessThanSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where projectid is less than DEFAULT_PROJECTID
        defaultDealsShouldNotBeFound("projectid.lessThan=" + DEFAULT_PROJECTID);

        // Get all the dealsList where projectid is less than UPDATED_PROJECTID
        defaultDealsShouldBeFound("projectid.lessThan=" + UPDATED_PROJECTID);
    }

    @Test
    @Transactional
    void getAllDealsByProjectidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where projectid is greater than DEFAULT_PROJECTID
        defaultDealsShouldNotBeFound("projectid.greaterThan=" + DEFAULT_PROJECTID);

        // Get all the dealsList where projectid is greater than SMALLER_PROJECTID
        defaultDealsShouldBeFound("projectid.greaterThan=" + SMALLER_PROJECTID);
    }

    @Test
    @Transactional
    void getAllDealsByExpectedstartdateIsEqualToSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where expectedstartdate equals to DEFAULT_EXPECTEDSTARTDATE
        defaultDealsShouldBeFound("expectedstartdate.equals=" + DEFAULT_EXPECTEDSTARTDATE);

        // Get all the dealsList where expectedstartdate equals to UPDATED_EXPECTEDSTARTDATE
        defaultDealsShouldNotBeFound("expectedstartdate.equals=" + UPDATED_EXPECTEDSTARTDATE);
    }

    @Test
    @Transactional
    void getAllDealsByExpectedstartdateIsInShouldWork() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where expectedstartdate in DEFAULT_EXPECTEDSTARTDATE or UPDATED_EXPECTEDSTARTDATE
        defaultDealsShouldBeFound("expectedstartdate.in=" + DEFAULT_EXPECTEDSTARTDATE + "," + UPDATED_EXPECTEDSTARTDATE);

        // Get all the dealsList where expectedstartdate equals to UPDATED_EXPECTEDSTARTDATE
        defaultDealsShouldNotBeFound("expectedstartdate.in=" + UPDATED_EXPECTEDSTARTDATE);
    }

    @Test
    @Transactional
    void getAllDealsByExpectedstartdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where expectedstartdate is not null
        defaultDealsShouldBeFound("expectedstartdate.specified=true");

        // Get all the dealsList where expectedstartdate is null
        defaultDealsShouldNotBeFound("expectedstartdate.specified=false");
    }

    @Test
    @Transactional
    void getAllDealsByStageIsEqualToSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where stage equals to DEFAULT_STAGE
        defaultDealsShouldBeFound("stage.equals=" + DEFAULT_STAGE);

        // Get all the dealsList where stage equals to UPDATED_STAGE
        defaultDealsShouldNotBeFound("stage.equals=" + UPDATED_STAGE);
    }

    @Test
    @Transactional
    void getAllDealsByStageIsInShouldWork() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where stage in DEFAULT_STAGE or UPDATED_STAGE
        defaultDealsShouldBeFound("stage.in=" + DEFAULT_STAGE + "," + UPDATED_STAGE);

        // Get all the dealsList where stage equals to UPDATED_STAGE
        defaultDealsShouldNotBeFound("stage.in=" + UPDATED_STAGE);
    }

    @Test
    @Transactional
    void getAllDealsByStageIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where stage is not null
        defaultDealsShouldBeFound("stage.specified=true");

        // Get all the dealsList where stage is null
        defaultDealsShouldNotBeFound("stage.specified=false");
    }

    @Test
    @Transactional
    void getAllDealsByStageContainsSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where stage contains DEFAULT_STAGE
        defaultDealsShouldBeFound("stage.contains=" + DEFAULT_STAGE);

        // Get all the dealsList where stage contains UPDATED_STAGE
        defaultDealsShouldNotBeFound("stage.contains=" + UPDATED_STAGE);
    }

    @Test
    @Transactional
    void getAllDealsByStageNotContainsSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where stage does not contain DEFAULT_STAGE
        defaultDealsShouldNotBeFound("stage.doesNotContain=" + DEFAULT_STAGE);

        // Get all the dealsList where stage does not contain UPDATED_STAGE
        defaultDealsShouldBeFound("stage.doesNotContain=" + UPDATED_STAGE);
    }

    @Test
    @Transactional
    void getAllDealsByProbabilityIsEqualToSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where probability equals to DEFAULT_PROBABILITY
        defaultDealsShouldBeFound("probability.equals=" + DEFAULT_PROBABILITY);

        // Get all the dealsList where probability equals to UPDATED_PROBABILITY
        defaultDealsShouldNotBeFound("probability.equals=" + UPDATED_PROBABILITY);
    }

    @Test
    @Transactional
    void getAllDealsByProbabilityIsInShouldWork() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where probability in DEFAULT_PROBABILITY or UPDATED_PROBABILITY
        defaultDealsShouldBeFound("probability.in=" + DEFAULT_PROBABILITY + "," + UPDATED_PROBABILITY);

        // Get all the dealsList where probability equals to UPDATED_PROBABILITY
        defaultDealsShouldNotBeFound("probability.in=" + UPDATED_PROBABILITY);
    }

    @Test
    @Transactional
    void getAllDealsByProbabilityIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where probability is not null
        defaultDealsShouldBeFound("probability.specified=true");

        // Get all the dealsList where probability is null
        defaultDealsShouldNotBeFound("probability.specified=false");
    }

    @Test
    @Transactional
    void getAllDealsByProbabilityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where probability is greater than or equal to DEFAULT_PROBABILITY
        defaultDealsShouldBeFound("probability.greaterThanOrEqual=" + DEFAULT_PROBABILITY);

        // Get all the dealsList where probability is greater than or equal to UPDATED_PROBABILITY
        defaultDealsShouldNotBeFound("probability.greaterThanOrEqual=" + UPDATED_PROBABILITY);
    }

    @Test
    @Transactional
    void getAllDealsByProbabilityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where probability is less than or equal to DEFAULT_PROBABILITY
        defaultDealsShouldBeFound("probability.lessThanOrEqual=" + DEFAULT_PROBABILITY);

        // Get all the dealsList where probability is less than or equal to SMALLER_PROBABILITY
        defaultDealsShouldNotBeFound("probability.lessThanOrEqual=" + SMALLER_PROBABILITY);
    }

    @Test
    @Transactional
    void getAllDealsByProbabilityIsLessThanSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where probability is less than DEFAULT_PROBABILITY
        defaultDealsShouldNotBeFound("probability.lessThan=" + DEFAULT_PROBABILITY);

        // Get all the dealsList where probability is less than UPDATED_PROBABILITY
        defaultDealsShouldBeFound("probability.lessThan=" + UPDATED_PROBABILITY);
    }

    @Test
    @Transactional
    void getAllDealsByProbabilityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where probability is greater than DEFAULT_PROBABILITY
        defaultDealsShouldNotBeFound("probability.greaterThan=" + DEFAULT_PROBABILITY);

        // Get all the dealsList where probability is greater than SMALLER_PROBABILITY
        defaultDealsShouldBeFound("probability.greaterThan=" + SMALLER_PROBABILITY);
    }

    @Test
    @Transactional
    void getAllDealsByProjectdurationIsEqualToSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where projectduration equals to DEFAULT_PROJECTDURATION
        defaultDealsShouldBeFound("projectduration.equals=" + DEFAULT_PROJECTDURATION);

        // Get all the dealsList where projectduration equals to UPDATED_PROJECTDURATION
        defaultDealsShouldNotBeFound("projectduration.equals=" + UPDATED_PROJECTDURATION);
    }

    @Test
    @Transactional
    void getAllDealsByProjectdurationIsInShouldWork() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where projectduration in DEFAULT_PROJECTDURATION or UPDATED_PROJECTDURATION
        defaultDealsShouldBeFound("projectduration.in=" + DEFAULT_PROJECTDURATION + "," + UPDATED_PROJECTDURATION);

        // Get all the dealsList where projectduration equals to UPDATED_PROJECTDURATION
        defaultDealsShouldNotBeFound("projectduration.in=" + UPDATED_PROJECTDURATION);
    }

    @Test
    @Transactional
    void getAllDealsByProjectdurationIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where projectduration is not null
        defaultDealsShouldBeFound("projectduration.specified=true");

        // Get all the dealsList where projectduration is null
        defaultDealsShouldNotBeFound("projectduration.specified=false");
    }

    @Test
    @Transactional
    void getAllDealsByProjectdurationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where projectduration is greater than or equal to DEFAULT_PROJECTDURATION
        defaultDealsShouldBeFound("projectduration.greaterThanOrEqual=" + DEFAULT_PROJECTDURATION);

        // Get all the dealsList where projectduration is greater than or equal to UPDATED_PROJECTDURATION
        defaultDealsShouldNotBeFound("projectduration.greaterThanOrEqual=" + UPDATED_PROJECTDURATION);
    }

    @Test
    @Transactional
    void getAllDealsByProjectdurationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where projectduration is less than or equal to DEFAULT_PROJECTDURATION
        defaultDealsShouldBeFound("projectduration.lessThanOrEqual=" + DEFAULT_PROJECTDURATION);

        // Get all the dealsList where projectduration is less than or equal to SMALLER_PROJECTDURATION
        defaultDealsShouldNotBeFound("projectduration.lessThanOrEqual=" + SMALLER_PROJECTDURATION);
    }

    @Test
    @Transactional
    void getAllDealsByProjectdurationIsLessThanSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where projectduration is less than DEFAULT_PROJECTDURATION
        defaultDealsShouldNotBeFound("projectduration.lessThan=" + DEFAULT_PROJECTDURATION);

        // Get all the dealsList where projectduration is less than UPDATED_PROJECTDURATION
        defaultDealsShouldBeFound("projectduration.lessThan=" + UPDATED_PROJECTDURATION);
    }

    @Test
    @Transactional
    void getAllDealsByProjectdurationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where projectduration is greater than DEFAULT_PROJECTDURATION
        defaultDealsShouldNotBeFound("projectduration.greaterThan=" + DEFAULT_PROJECTDURATION);

        // Get all the dealsList where projectduration is greater than SMALLER_PROJECTDURATION
        defaultDealsShouldBeFound("projectduration.greaterThan=" + SMALLER_PROJECTDURATION);
    }

    @Test
    @Transactional
    void getAllDealsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where type equals to DEFAULT_TYPE
        defaultDealsShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the dealsList where type equals to UPDATED_TYPE
        defaultDealsShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllDealsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultDealsShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the dealsList where type equals to UPDATED_TYPE
        defaultDealsShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllDealsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where type is not null
        defaultDealsShouldBeFound("type.specified=true");

        // Get all the dealsList where type is null
        defaultDealsShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllDealsByTypeContainsSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where type contains DEFAULT_TYPE
        defaultDealsShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the dealsList where type contains UPDATED_TYPE
        defaultDealsShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllDealsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where type does not contain DEFAULT_TYPE
        defaultDealsShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the dealsList where type does not contain UPDATED_TYPE
        defaultDealsShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllDealsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where status equals to DEFAULT_STATUS
        defaultDealsShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the dealsList where status equals to UPDATED_STATUS
        defaultDealsShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllDealsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultDealsShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the dealsList where status equals to UPDATED_STATUS
        defaultDealsShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllDealsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where status is not null
        defaultDealsShouldBeFound("status.specified=true");

        // Get all the dealsList where status is null
        defaultDealsShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllDealsByStatusContainsSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where status contains DEFAULT_STATUS
        defaultDealsShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the dealsList where status contains UPDATED_STATUS
        defaultDealsShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllDealsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where status does not contain DEFAULT_STATUS
        defaultDealsShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the dealsList where status does not contain UPDATED_STATUS
        defaultDealsShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllDealsByClosedatIsEqualToSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where closedat equals to DEFAULT_CLOSEDAT
        defaultDealsShouldBeFound("closedat.equals=" + DEFAULT_CLOSEDAT);

        // Get all the dealsList where closedat equals to UPDATED_CLOSEDAT
        defaultDealsShouldNotBeFound("closedat.equals=" + UPDATED_CLOSEDAT);
    }

    @Test
    @Transactional
    void getAllDealsByClosedatIsInShouldWork() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where closedat in DEFAULT_CLOSEDAT or UPDATED_CLOSEDAT
        defaultDealsShouldBeFound("closedat.in=" + DEFAULT_CLOSEDAT + "," + UPDATED_CLOSEDAT);

        // Get all the dealsList where closedat equals to UPDATED_CLOSEDAT
        defaultDealsShouldNotBeFound("closedat.in=" + UPDATED_CLOSEDAT);
    }

    @Test
    @Transactional
    void getAllDealsByClosedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where closedat is not null
        defaultDealsShouldBeFound("closedat.specified=true");

        // Get all the dealsList where closedat is null
        defaultDealsShouldNotBeFound("closedat.specified=false");
    }

    @Test
    @Transactional
    void getAllDealsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where createdat equals to DEFAULT_CREATEDAT
        defaultDealsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the dealsList where createdat equals to UPDATED_CREATEDAT
        defaultDealsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllDealsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultDealsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the dealsList where createdat equals to UPDATED_CREATEDAT
        defaultDealsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllDealsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where createdat is not null
        defaultDealsShouldBeFound("createdat.specified=true");

        // Get all the dealsList where createdat is null
        defaultDealsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllDealsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultDealsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the dealsList where updatedat equals to UPDATED_UPDATEDAT
        defaultDealsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllDealsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultDealsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the dealsList where updatedat equals to UPDATED_UPDATEDAT
        defaultDealsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllDealsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where updatedat is not null
        defaultDealsShouldBeFound("updatedat.specified=true");

        // Get all the dealsList where updatedat is null
        defaultDealsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllDealsByDeletedatIsEqualToSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where deletedat equals to DEFAULT_DELETEDAT
        defaultDealsShouldBeFound("deletedat.equals=" + DEFAULT_DELETEDAT);

        // Get all the dealsList where deletedat equals to UPDATED_DELETEDAT
        defaultDealsShouldNotBeFound("deletedat.equals=" + UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void getAllDealsByDeletedatIsInShouldWork() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where deletedat in DEFAULT_DELETEDAT or UPDATED_DELETEDAT
        defaultDealsShouldBeFound("deletedat.in=" + DEFAULT_DELETEDAT + "," + UPDATED_DELETEDAT);

        // Get all the dealsList where deletedat equals to UPDATED_DELETEDAT
        defaultDealsShouldNotBeFound("deletedat.in=" + UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void getAllDealsByDeletedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where deletedat is not null
        defaultDealsShouldBeFound("deletedat.specified=true");

        // Get all the dealsList where deletedat is null
        defaultDealsShouldNotBeFound("deletedat.specified=false");
    }

    @Test
    @Transactional
    void getAllDealsByResourcingenteredinvendiansIsEqualToSomething() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where resourcingenteredinvendians equals to DEFAULT_RESOURCINGENTEREDINVENDIANS
        defaultDealsShouldBeFound("resourcingenteredinvendians.equals=" + DEFAULT_RESOURCINGENTEREDINVENDIANS);

        // Get all the dealsList where resourcingenteredinvendians equals to UPDATED_RESOURCINGENTEREDINVENDIANS
        defaultDealsShouldNotBeFound("resourcingenteredinvendians.equals=" + UPDATED_RESOURCINGENTEREDINVENDIANS);
    }

    @Test
    @Transactional
    void getAllDealsByResourcingenteredinvendiansIsInShouldWork() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where resourcingenteredinvendians in DEFAULT_RESOURCINGENTEREDINVENDIANS or UPDATED_RESOURCINGENTEREDINVENDIANS
        defaultDealsShouldBeFound(
            "resourcingenteredinvendians.in=" + DEFAULT_RESOURCINGENTEREDINVENDIANS + "," + UPDATED_RESOURCINGENTEREDINVENDIANS
        );

        // Get all the dealsList where resourcingenteredinvendians equals to UPDATED_RESOURCINGENTEREDINVENDIANS
        defaultDealsShouldNotBeFound("resourcingenteredinvendians.in=" + UPDATED_RESOURCINGENTEREDINVENDIANS);
    }

    @Test
    @Transactional
    void getAllDealsByResourcingenteredinvendiansIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        // Get all the dealsList where resourcingenteredinvendians is not null
        defaultDealsShouldBeFound("resourcingenteredinvendians.specified=true");

        // Get all the dealsList where resourcingenteredinvendians is null
        defaultDealsShouldNotBeFound("resourcingenteredinvendians.specified=false");
    }

    @Test
    @Transactional
    void getAllDealsByDealrequirementsDealIsEqualToSomething() throws Exception {
        DealRequirements dealrequirementsDeal;
        if (TestUtil.findAll(em, DealRequirements.class).isEmpty()) {
            dealsRepository.saveAndFlush(deals);
            dealrequirementsDeal = DealRequirementsResourceIT.createEntity(em);
        } else {
            dealrequirementsDeal = TestUtil.findAll(em, DealRequirements.class).get(0);
        }
        em.persist(dealrequirementsDeal);
        em.flush();
        deals.addDealrequirementsDeal(dealrequirementsDeal);
        dealsRepository.saveAndFlush(deals);
        Long dealrequirementsDealId = dealrequirementsDeal.getId();

        // Get all the dealsList where dealrequirementsDeal equals to dealrequirementsDealId
        defaultDealsShouldBeFound("dealrequirementsDealId.equals=" + dealrequirementsDealId);

        // Get all the dealsList where dealrequirementsDeal equals to (dealrequirementsDealId + 1)
        defaultDealsShouldNotBeFound("dealrequirementsDealId.equals=" + (dealrequirementsDealId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDealsShouldBeFound(String filter) throws Exception {
        restDealsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deals.getId().intValue())))
            .andExpect(jsonPath("$.[*].dealnumber").value(hasItem(DEFAULT_DEALNUMBER)))
            .andExpect(jsonPath("$.[*].dealname").value(hasItem(DEFAULT_DEALNAME)))
            .andExpect(jsonPath("$.[*].businessunit").value(hasItem(DEFAULT_BUSINESSUNIT)))
            .andExpect(jsonPath("$.[*].clientname").value(hasItem(DEFAULT_CLIENTNAME)))
            .andExpect(jsonPath("$.[*].dealowner").value(hasItem(DEFAULT_DEALOWNER)))
            .andExpect(jsonPath("$.[*].proposaltype").value(hasItem(DEFAULT_PROPOSALTYPE)))
            .andExpect(jsonPath("$.[*].projectid").value(hasItem(DEFAULT_PROJECTID)))
            .andExpect(jsonPath("$.[*].expectedstartdate").value(hasItem(DEFAULT_EXPECTEDSTARTDATE.toString())))
            .andExpect(jsonPath("$.[*].stage").value(hasItem(DEFAULT_STAGE)))
            .andExpect(jsonPath("$.[*].probability").value(hasItem(DEFAULT_PROBABILITY.doubleValue())))
            .andExpect(jsonPath("$.[*].projectduration").value(hasItem(DEFAULT_PROJECTDURATION.doubleValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].closedat").value(hasItem(DEFAULT_CLOSEDAT.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].deletedat").value(hasItem(DEFAULT_DELETEDAT.toString())))
            .andExpect(jsonPath("$.[*].resourcingenteredinvendians").value(hasItem(DEFAULT_RESOURCINGENTEREDINVENDIANS.booleanValue())));

        // Check, that the count call also returns 1
        restDealsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDealsShouldNotBeFound(String filter) throws Exception {
        restDealsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDealsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDeals() throws Exception {
        // Get the deals
        restDealsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDeals() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        int databaseSizeBeforeUpdate = dealsRepository.findAll().size();

        // Update the deals
        Deals updatedDeals = dealsRepository.findById(deals.getId()).get();
        // Disconnect from session so that the updates on updatedDeals are not directly saved in db
        em.detach(updatedDeals);
        updatedDeals
            .dealnumber(UPDATED_DEALNUMBER)
            .dealname(UPDATED_DEALNAME)
            .businessunit(UPDATED_BUSINESSUNIT)
            .clientname(UPDATED_CLIENTNAME)
            .dealowner(UPDATED_DEALOWNER)
            .proposaltype(UPDATED_PROPOSALTYPE)
            .projectid(UPDATED_PROJECTID)
            .expectedstartdate(UPDATED_EXPECTEDSTARTDATE)
            .stage(UPDATED_STAGE)
            .probability(UPDATED_PROBABILITY)
            .projectduration(UPDATED_PROJECTDURATION)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS)
            .closedat(UPDATED_CLOSEDAT)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .deletedat(UPDATED_DELETEDAT)
            .resourcingenteredinvendians(UPDATED_RESOURCINGENTEREDINVENDIANS);

        restDealsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDeals.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDeals))
            )
            .andExpect(status().isOk());

        // Validate the Deals in the database
        List<Deals> dealsList = dealsRepository.findAll();
        assertThat(dealsList).hasSize(databaseSizeBeforeUpdate);
        Deals testDeals = dealsList.get(dealsList.size() - 1);
        assertThat(testDeals.getDealnumber()).isEqualTo(UPDATED_DEALNUMBER);
        assertThat(testDeals.getDealname()).isEqualTo(UPDATED_DEALNAME);
        assertThat(testDeals.getBusinessunit()).isEqualTo(UPDATED_BUSINESSUNIT);
        assertThat(testDeals.getClientname()).isEqualTo(UPDATED_CLIENTNAME);
        assertThat(testDeals.getDealowner()).isEqualTo(UPDATED_DEALOWNER);
        assertThat(testDeals.getProposaltype()).isEqualTo(UPDATED_PROPOSALTYPE);
        assertThat(testDeals.getProjectid()).isEqualTo(UPDATED_PROJECTID);
        assertThat(testDeals.getExpectedstartdate()).isEqualTo(UPDATED_EXPECTEDSTARTDATE);
        assertThat(testDeals.getStage()).isEqualTo(UPDATED_STAGE);
        assertThat(testDeals.getProbability()).isEqualTo(UPDATED_PROBABILITY);
        assertThat(testDeals.getProjectduration()).isEqualTo(UPDATED_PROJECTDURATION);
        assertThat(testDeals.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDeals.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDeals.getClosedat()).isEqualTo(UPDATED_CLOSEDAT);
        assertThat(testDeals.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testDeals.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testDeals.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
        assertThat(testDeals.getResourcingenteredinvendians()).isEqualTo(UPDATED_RESOURCINGENTEREDINVENDIANS);
    }

    @Test
    @Transactional
    void putNonExistingDeals() throws Exception {
        int databaseSizeBeforeUpdate = dealsRepository.findAll().size();
        deals.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDealsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, deals.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deals))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deals in the database
        List<Deals> dealsList = dealsRepository.findAll();
        assertThat(dealsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDeals() throws Exception {
        int databaseSizeBeforeUpdate = dealsRepository.findAll().size();
        deals.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deals))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deals in the database
        List<Deals> dealsList = dealsRepository.findAll();
        assertThat(dealsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDeals() throws Exception {
        int databaseSizeBeforeUpdate = dealsRepository.findAll().size();
        deals.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealsMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deals))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Deals in the database
        List<Deals> dealsList = dealsRepository.findAll();
        assertThat(dealsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDealsWithPatch() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        int databaseSizeBeforeUpdate = dealsRepository.findAll().size();

        // Update the deals using partial update
        Deals partialUpdatedDeals = new Deals();
        partialUpdatedDeals.setId(deals.getId());

        partialUpdatedDeals
            .dealnumber(UPDATED_DEALNUMBER)
            .businessunit(UPDATED_BUSINESSUNIT)
            .clientname(UPDATED_CLIENTNAME)
            .projectid(UPDATED_PROJECTID)
            .stage(UPDATED_STAGE)
            .status(UPDATED_STATUS)
            .createdat(UPDATED_CREATEDAT)
            .deletedat(UPDATED_DELETEDAT);

        restDealsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeals.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeals))
            )
            .andExpect(status().isOk());

        // Validate the Deals in the database
        List<Deals> dealsList = dealsRepository.findAll();
        assertThat(dealsList).hasSize(databaseSizeBeforeUpdate);
        Deals testDeals = dealsList.get(dealsList.size() - 1);
        assertThat(testDeals.getDealnumber()).isEqualTo(UPDATED_DEALNUMBER);
        assertThat(testDeals.getDealname()).isEqualTo(DEFAULT_DEALNAME);
        assertThat(testDeals.getBusinessunit()).isEqualTo(UPDATED_BUSINESSUNIT);
        assertThat(testDeals.getClientname()).isEqualTo(UPDATED_CLIENTNAME);
        assertThat(testDeals.getDealowner()).isEqualTo(DEFAULT_DEALOWNER);
        assertThat(testDeals.getProposaltype()).isEqualTo(DEFAULT_PROPOSALTYPE);
        assertThat(testDeals.getProjectid()).isEqualTo(UPDATED_PROJECTID);
        assertThat(testDeals.getExpectedstartdate()).isEqualTo(DEFAULT_EXPECTEDSTARTDATE);
        assertThat(testDeals.getStage()).isEqualTo(UPDATED_STAGE);
        assertThat(testDeals.getProbability()).isEqualTo(DEFAULT_PROBABILITY);
        assertThat(testDeals.getProjectduration()).isEqualTo(DEFAULT_PROJECTDURATION);
        assertThat(testDeals.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDeals.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDeals.getClosedat()).isEqualTo(DEFAULT_CLOSEDAT);
        assertThat(testDeals.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testDeals.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testDeals.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
        assertThat(testDeals.getResourcingenteredinvendians()).isEqualTo(DEFAULT_RESOURCINGENTEREDINVENDIANS);
    }

    @Test
    @Transactional
    void fullUpdateDealsWithPatch() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        int databaseSizeBeforeUpdate = dealsRepository.findAll().size();

        // Update the deals using partial update
        Deals partialUpdatedDeals = new Deals();
        partialUpdatedDeals.setId(deals.getId());

        partialUpdatedDeals
            .dealnumber(UPDATED_DEALNUMBER)
            .dealname(UPDATED_DEALNAME)
            .businessunit(UPDATED_BUSINESSUNIT)
            .clientname(UPDATED_CLIENTNAME)
            .dealowner(UPDATED_DEALOWNER)
            .proposaltype(UPDATED_PROPOSALTYPE)
            .projectid(UPDATED_PROJECTID)
            .expectedstartdate(UPDATED_EXPECTEDSTARTDATE)
            .stage(UPDATED_STAGE)
            .probability(UPDATED_PROBABILITY)
            .projectduration(UPDATED_PROJECTDURATION)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS)
            .closedat(UPDATED_CLOSEDAT)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .deletedat(UPDATED_DELETEDAT)
            .resourcingenteredinvendians(UPDATED_RESOURCINGENTEREDINVENDIANS);

        restDealsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeals.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeals))
            )
            .andExpect(status().isOk());

        // Validate the Deals in the database
        List<Deals> dealsList = dealsRepository.findAll();
        assertThat(dealsList).hasSize(databaseSizeBeforeUpdate);
        Deals testDeals = dealsList.get(dealsList.size() - 1);
        assertThat(testDeals.getDealnumber()).isEqualTo(UPDATED_DEALNUMBER);
        assertThat(testDeals.getDealname()).isEqualTo(UPDATED_DEALNAME);
        assertThat(testDeals.getBusinessunit()).isEqualTo(UPDATED_BUSINESSUNIT);
        assertThat(testDeals.getClientname()).isEqualTo(UPDATED_CLIENTNAME);
        assertThat(testDeals.getDealowner()).isEqualTo(UPDATED_DEALOWNER);
        assertThat(testDeals.getProposaltype()).isEqualTo(UPDATED_PROPOSALTYPE);
        assertThat(testDeals.getProjectid()).isEqualTo(UPDATED_PROJECTID);
        assertThat(testDeals.getExpectedstartdate()).isEqualTo(UPDATED_EXPECTEDSTARTDATE);
        assertThat(testDeals.getStage()).isEqualTo(UPDATED_STAGE);
        assertThat(testDeals.getProbability()).isEqualTo(UPDATED_PROBABILITY);
        assertThat(testDeals.getProjectduration()).isEqualTo(UPDATED_PROJECTDURATION);
        assertThat(testDeals.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDeals.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDeals.getClosedat()).isEqualTo(UPDATED_CLOSEDAT);
        assertThat(testDeals.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testDeals.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testDeals.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
        assertThat(testDeals.getResourcingenteredinvendians()).isEqualTo(UPDATED_RESOURCINGENTEREDINVENDIANS);
    }

    @Test
    @Transactional
    void patchNonExistingDeals() throws Exception {
        int databaseSizeBeforeUpdate = dealsRepository.findAll().size();
        deals.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDealsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, deals.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deals))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deals in the database
        List<Deals> dealsList = dealsRepository.findAll();
        assertThat(dealsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDeals() throws Exception {
        int databaseSizeBeforeUpdate = dealsRepository.findAll().size();
        deals.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deals))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deals in the database
        List<Deals> dealsList = dealsRepository.findAll();
        assertThat(dealsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDeals() throws Exception {
        int databaseSizeBeforeUpdate = dealsRepository.findAll().size();
        deals.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deals))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Deals in the database
        List<Deals> dealsList = dealsRepository.findAll();
        assertThat(dealsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDeals() throws Exception {
        // Initialize the database
        dealsRepository.saveAndFlush(deals);

        int databaseSizeBeforeDelete = dealsRepository.findAll().size();

        // Delete the deals
        restDealsMockMvc
            .perform(delete(ENTITY_API_URL_ID, deals.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Deals> dealsList = dealsRepository.findAll();
        assertThat(dealsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
