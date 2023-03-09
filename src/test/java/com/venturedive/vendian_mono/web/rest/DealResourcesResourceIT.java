package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.DealRequirementMatchingResources;
import com.venturedive.vendian_mono.domain.DealResourceSkills;
import com.venturedive.vendian_mono.domain.DealResources;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.repository.DealResourcesRepository;
import com.venturedive.vendian_mono.service.criteria.DealResourcesCriteria;
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
 * Integration tests for the {@link DealResourcesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DealResourcesResourceIT {

    private static final String DEFAULT_FIRSTNAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRSTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_LASTNAME = "AAAAAAAAAA";
    private static final String UPDATED_LASTNAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_JOININGDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_JOININGDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_EXTERNALEXPYEARS = 1;
    private static final Integer UPDATED_EXTERNALEXPYEARS = 2;
    private static final Integer SMALLER_EXTERNALEXPYEARS = 1 - 1;

    private static final Integer DEFAULT_EXTERNALEXPMONTHS = 1;
    private static final Integer UPDATED_EXTERNALEXPMONTHS = 2;
    private static final Integer SMALLER_EXTERNALEXPMONTHS = 1 - 1;

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ISACTIVE = false;
    private static final Boolean UPDATED_ISACTIVE = true;

    private static final String ENTITY_API_URL = "/api/deal-resources";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DealResourcesRepository dealResourcesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDealResourcesMockMvc;

    private DealResources dealResources;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DealResources createEntity(EntityManager em) {
        DealResources dealResources = new DealResources()
            .firstname(DEFAULT_FIRSTNAME)
            .lastname(DEFAULT_LASTNAME)
            .joiningdate(DEFAULT_JOININGDATE)
            .externalexpyears(DEFAULT_EXTERNALEXPYEARS)
            .externalexpmonths(DEFAULT_EXTERNALEXPMONTHS)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT)
            .type(DEFAULT_TYPE)
            .isactive(DEFAULT_ISACTIVE);
        return dealResources;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DealResources createUpdatedEntity(EntityManager em) {
        DealResources dealResources = new DealResources()
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .joiningdate(UPDATED_JOININGDATE)
            .externalexpyears(UPDATED_EXTERNALEXPYEARS)
            .externalexpmonths(UPDATED_EXTERNALEXPMONTHS)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .type(UPDATED_TYPE)
            .isactive(UPDATED_ISACTIVE);
        return dealResources;
    }

    @BeforeEach
    public void initTest() {
        dealResources = createEntity(em);
    }

    @Test
    @Transactional
    void createDealResources() throws Exception {
        int databaseSizeBeforeCreate = dealResourcesRepository.findAll().size();
        // Create the DealResources
        restDealResourcesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealResources))
            )
            .andExpect(status().isCreated());

        // Validate the DealResources in the database
        List<DealResources> dealResourcesList = dealResourcesRepository.findAll();
        assertThat(dealResourcesList).hasSize(databaseSizeBeforeCreate + 1);
        DealResources testDealResources = dealResourcesList.get(dealResourcesList.size() - 1);
        assertThat(testDealResources.getFirstname()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(testDealResources.getLastname()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testDealResources.getJoiningdate()).isEqualTo(DEFAULT_JOININGDATE);
        assertThat(testDealResources.getExternalexpyears()).isEqualTo(DEFAULT_EXTERNALEXPYEARS);
        assertThat(testDealResources.getExternalexpmonths()).isEqualTo(DEFAULT_EXTERNALEXPMONTHS);
        assertThat(testDealResources.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testDealResources.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testDealResources.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDealResources.getIsactive()).isEqualTo(DEFAULT_ISACTIVE);
    }

    @Test
    @Transactional
    void createDealResourcesWithExistingId() throws Exception {
        // Create the DealResources with an existing ID
        dealResources.setId(1L);

        int databaseSizeBeforeCreate = dealResourcesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDealResourcesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealResources))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealResources in the database
        List<DealResources> dealResourcesList = dealResourcesRepository.findAll();
        assertThat(dealResourcesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealResourcesRepository.findAll().size();
        // set the field null
        dealResources.setType(null);

        // Create the DealResources, which fails.

        restDealResourcesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealResources))
            )
            .andExpect(status().isBadRequest());

        List<DealResources> dealResourcesList = dealResourcesRepository.findAll();
        assertThat(dealResourcesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsactiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealResourcesRepository.findAll().size();
        // set the field null
        dealResources.setIsactive(null);

        // Create the DealResources, which fails.

        restDealResourcesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealResources))
            )
            .andExpect(status().isBadRequest());

        List<DealResources> dealResourcesList = dealResourcesRepository.findAll();
        assertThat(dealResourcesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDealResources() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList
        restDealResourcesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dealResources.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME)))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME)))
            .andExpect(jsonPath("$.[*].joiningdate").value(hasItem(DEFAULT_JOININGDATE.toString())))
            .andExpect(jsonPath("$.[*].externalexpyears").value(hasItem(DEFAULT_EXTERNALEXPYEARS)))
            .andExpect(jsonPath("$.[*].externalexpmonths").value(hasItem(DEFAULT_EXTERNALEXPMONTHS)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    void getDealResources() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get the dealResources
        restDealResourcesMockMvc
            .perform(get(ENTITY_API_URL_ID, dealResources.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dealResources.getId().intValue()))
            .andExpect(jsonPath("$.firstname").value(DEFAULT_FIRSTNAME))
            .andExpect(jsonPath("$.lastname").value(DEFAULT_LASTNAME))
            .andExpect(jsonPath("$.joiningdate").value(DEFAULT_JOININGDATE.toString()))
            .andExpect(jsonPath("$.externalexpyears").value(DEFAULT_EXTERNALEXPYEARS))
            .andExpect(jsonPath("$.externalexpmonths").value(DEFAULT_EXTERNALEXPMONTHS))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.isactive").value(DEFAULT_ISACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    void getDealResourcesByIdFiltering() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        Long id = dealResources.getId();

        defaultDealResourcesShouldBeFound("id.equals=" + id);
        defaultDealResourcesShouldNotBeFound("id.notEquals=" + id);

        defaultDealResourcesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDealResourcesShouldNotBeFound("id.greaterThan=" + id);

        defaultDealResourcesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDealResourcesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDealResourcesByFirstnameIsEqualToSomething() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where firstname equals to DEFAULT_FIRSTNAME
        defaultDealResourcesShouldBeFound("firstname.equals=" + DEFAULT_FIRSTNAME);

        // Get all the dealResourcesList where firstname equals to UPDATED_FIRSTNAME
        defaultDealResourcesShouldNotBeFound("firstname.equals=" + UPDATED_FIRSTNAME);
    }

    @Test
    @Transactional
    void getAllDealResourcesByFirstnameIsInShouldWork() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where firstname in DEFAULT_FIRSTNAME or UPDATED_FIRSTNAME
        defaultDealResourcesShouldBeFound("firstname.in=" + DEFAULT_FIRSTNAME + "," + UPDATED_FIRSTNAME);

        // Get all the dealResourcesList where firstname equals to UPDATED_FIRSTNAME
        defaultDealResourcesShouldNotBeFound("firstname.in=" + UPDATED_FIRSTNAME);
    }

    @Test
    @Transactional
    void getAllDealResourcesByFirstnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where firstname is not null
        defaultDealResourcesShouldBeFound("firstname.specified=true");

        // Get all the dealResourcesList where firstname is null
        defaultDealResourcesShouldNotBeFound("firstname.specified=false");
    }

    @Test
    @Transactional
    void getAllDealResourcesByFirstnameContainsSomething() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where firstname contains DEFAULT_FIRSTNAME
        defaultDealResourcesShouldBeFound("firstname.contains=" + DEFAULT_FIRSTNAME);

        // Get all the dealResourcesList where firstname contains UPDATED_FIRSTNAME
        defaultDealResourcesShouldNotBeFound("firstname.contains=" + UPDATED_FIRSTNAME);
    }

    @Test
    @Transactional
    void getAllDealResourcesByFirstnameNotContainsSomething() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where firstname does not contain DEFAULT_FIRSTNAME
        defaultDealResourcesShouldNotBeFound("firstname.doesNotContain=" + DEFAULT_FIRSTNAME);

        // Get all the dealResourcesList where firstname does not contain UPDATED_FIRSTNAME
        defaultDealResourcesShouldBeFound("firstname.doesNotContain=" + UPDATED_FIRSTNAME);
    }

    @Test
    @Transactional
    void getAllDealResourcesByLastnameIsEqualToSomething() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where lastname equals to DEFAULT_LASTNAME
        defaultDealResourcesShouldBeFound("lastname.equals=" + DEFAULT_LASTNAME);

        // Get all the dealResourcesList where lastname equals to UPDATED_LASTNAME
        defaultDealResourcesShouldNotBeFound("lastname.equals=" + UPDATED_LASTNAME);
    }

    @Test
    @Transactional
    void getAllDealResourcesByLastnameIsInShouldWork() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where lastname in DEFAULT_LASTNAME or UPDATED_LASTNAME
        defaultDealResourcesShouldBeFound("lastname.in=" + DEFAULT_LASTNAME + "," + UPDATED_LASTNAME);

        // Get all the dealResourcesList where lastname equals to UPDATED_LASTNAME
        defaultDealResourcesShouldNotBeFound("lastname.in=" + UPDATED_LASTNAME);
    }

    @Test
    @Transactional
    void getAllDealResourcesByLastnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where lastname is not null
        defaultDealResourcesShouldBeFound("lastname.specified=true");

        // Get all the dealResourcesList where lastname is null
        defaultDealResourcesShouldNotBeFound("lastname.specified=false");
    }

    @Test
    @Transactional
    void getAllDealResourcesByLastnameContainsSomething() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where lastname contains DEFAULT_LASTNAME
        defaultDealResourcesShouldBeFound("lastname.contains=" + DEFAULT_LASTNAME);

        // Get all the dealResourcesList where lastname contains UPDATED_LASTNAME
        defaultDealResourcesShouldNotBeFound("lastname.contains=" + UPDATED_LASTNAME);
    }

    @Test
    @Transactional
    void getAllDealResourcesByLastnameNotContainsSomething() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where lastname does not contain DEFAULT_LASTNAME
        defaultDealResourcesShouldNotBeFound("lastname.doesNotContain=" + DEFAULT_LASTNAME);

        // Get all the dealResourcesList where lastname does not contain UPDATED_LASTNAME
        defaultDealResourcesShouldBeFound("lastname.doesNotContain=" + UPDATED_LASTNAME);
    }

    @Test
    @Transactional
    void getAllDealResourcesByJoiningdateIsEqualToSomething() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where joiningdate equals to DEFAULT_JOININGDATE
        defaultDealResourcesShouldBeFound("joiningdate.equals=" + DEFAULT_JOININGDATE);

        // Get all the dealResourcesList where joiningdate equals to UPDATED_JOININGDATE
        defaultDealResourcesShouldNotBeFound("joiningdate.equals=" + UPDATED_JOININGDATE);
    }

    @Test
    @Transactional
    void getAllDealResourcesByJoiningdateIsInShouldWork() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where joiningdate in DEFAULT_JOININGDATE or UPDATED_JOININGDATE
        defaultDealResourcesShouldBeFound("joiningdate.in=" + DEFAULT_JOININGDATE + "," + UPDATED_JOININGDATE);

        // Get all the dealResourcesList where joiningdate equals to UPDATED_JOININGDATE
        defaultDealResourcesShouldNotBeFound("joiningdate.in=" + UPDATED_JOININGDATE);
    }

    @Test
    @Transactional
    void getAllDealResourcesByJoiningdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where joiningdate is not null
        defaultDealResourcesShouldBeFound("joiningdate.specified=true");

        // Get all the dealResourcesList where joiningdate is null
        defaultDealResourcesShouldNotBeFound("joiningdate.specified=false");
    }

    @Test
    @Transactional
    void getAllDealResourcesByExternalexpyearsIsEqualToSomething() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where externalexpyears equals to DEFAULT_EXTERNALEXPYEARS
        defaultDealResourcesShouldBeFound("externalexpyears.equals=" + DEFAULT_EXTERNALEXPYEARS);

        // Get all the dealResourcesList where externalexpyears equals to UPDATED_EXTERNALEXPYEARS
        defaultDealResourcesShouldNotBeFound("externalexpyears.equals=" + UPDATED_EXTERNALEXPYEARS);
    }

    @Test
    @Transactional
    void getAllDealResourcesByExternalexpyearsIsInShouldWork() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where externalexpyears in DEFAULT_EXTERNALEXPYEARS or UPDATED_EXTERNALEXPYEARS
        defaultDealResourcesShouldBeFound("externalexpyears.in=" + DEFAULT_EXTERNALEXPYEARS + "," + UPDATED_EXTERNALEXPYEARS);

        // Get all the dealResourcesList where externalexpyears equals to UPDATED_EXTERNALEXPYEARS
        defaultDealResourcesShouldNotBeFound("externalexpyears.in=" + UPDATED_EXTERNALEXPYEARS);
    }

    @Test
    @Transactional
    void getAllDealResourcesByExternalexpyearsIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where externalexpyears is not null
        defaultDealResourcesShouldBeFound("externalexpyears.specified=true");

        // Get all the dealResourcesList where externalexpyears is null
        defaultDealResourcesShouldNotBeFound("externalexpyears.specified=false");
    }

    @Test
    @Transactional
    void getAllDealResourcesByExternalexpyearsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where externalexpyears is greater than or equal to DEFAULT_EXTERNALEXPYEARS
        defaultDealResourcesShouldBeFound("externalexpyears.greaterThanOrEqual=" + DEFAULT_EXTERNALEXPYEARS);

        // Get all the dealResourcesList where externalexpyears is greater than or equal to UPDATED_EXTERNALEXPYEARS
        defaultDealResourcesShouldNotBeFound("externalexpyears.greaterThanOrEqual=" + UPDATED_EXTERNALEXPYEARS);
    }

    @Test
    @Transactional
    void getAllDealResourcesByExternalexpyearsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where externalexpyears is less than or equal to DEFAULT_EXTERNALEXPYEARS
        defaultDealResourcesShouldBeFound("externalexpyears.lessThanOrEqual=" + DEFAULT_EXTERNALEXPYEARS);

        // Get all the dealResourcesList where externalexpyears is less than or equal to SMALLER_EXTERNALEXPYEARS
        defaultDealResourcesShouldNotBeFound("externalexpyears.lessThanOrEqual=" + SMALLER_EXTERNALEXPYEARS);
    }

    @Test
    @Transactional
    void getAllDealResourcesByExternalexpyearsIsLessThanSomething() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where externalexpyears is less than DEFAULT_EXTERNALEXPYEARS
        defaultDealResourcesShouldNotBeFound("externalexpyears.lessThan=" + DEFAULT_EXTERNALEXPYEARS);

        // Get all the dealResourcesList where externalexpyears is less than UPDATED_EXTERNALEXPYEARS
        defaultDealResourcesShouldBeFound("externalexpyears.lessThan=" + UPDATED_EXTERNALEXPYEARS);
    }

    @Test
    @Transactional
    void getAllDealResourcesByExternalexpyearsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where externalexpyears is greater than DEFAULT_EXTERNALEXPYEARS
        defaultDealResourcesShouldNotBeFound("externalexpyears.greaterThan=" + DEFAULT_EXTERNALEXPYEARS);

        // Get all the dealResourcesList where externalexpyears is greater than SMALLER_EXTERNALEXPYEARS
        defaultDealResourcesShouldBeFound("externalexpyears.greaterThan=" + SMALLER_EXTERNALEXPYEARS);
    }

    @Test
    @Transactional
    void getAllDealResourcesByExternalexpmonthsIsEqualToSomething() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where externalexpmonths equals to DEFAULT_EXTERNALEXPMONTHS
        defaultDealResourcesShouldBeFound("externalexpmonths.equals=" + DEFAULT_EXTERNALEXPMONTHS);

        // Get all the dealResourcesList where externalexpmonths equals to UPDATED_EXTERNALEXPMONTHS
        defaultDealResourcesShouldNotBeFound("externalexpmonths.equals=" + UPDATED_EXTERNALEXPMONTHS);
    }

    @Test
    @Transactional
    void getAllDealResourcesByExternalexpmonthsIsInShouldWork() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where externalexpmonths in DEFAULT_EXTERNALEXPMONTHS or UPDATED_EXTERNALEXPMONTHS
        defaultDealResourcesShouldBeFound("externalexpmonths.in=" + DEFAULT_EXTERNALEXPMONTHS + "," + UPDATED_EXTERNALEXPMONTHS);

        // Get all the dealResourcesList where externalexpmonths equals to UPDATED_EXTERNALEXPMONTHS
        defaultDealResourcesShouldNotBeFound("externalexpmonths.in=" + UPDATED_EXTERNALEXPMONTHS);
    }

    @Test
    @Transactional
    void getAllDealResourcesByExternalexpmonthsIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where externalexpmonths is not null
        defaultDealResourcesShouldBeFound("externalexpmonths.specified=true");

        // Get all the dealResourcesList where externalexpmonths is null
        defaultDealResourcesShouldNotBeFound("externalexpmonths.specified=false");
    }

    @Test
    @Transactional
    void getAllDealResourcesByExternalexpmonthsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where externalexpmonths is greater than or equal to DEFAULT_EXTERNALEXPMONTHS
        defaultDealResourcesShouldBeFound("externalexpmonths.greaterThanOrEqual=" + DEFAULT_EXTERNALEXPMONTHS);

        // Get all the dealResourcesList where externalexpmonths is greater than or equal to UPDATED_EXTERNALEXPMONTHS
        defaultDealResourcesShouldNotBeFound("externalexpmonths.greaterThanOrEqual=" + UPDATED_EXTERNALEXPMONTHS);
    }

    @Test
    @Transactional
    void getAllDealResourcesByExternalexpmonthsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where externalexpmonths is less than or equal to DEFAULT_EXTERNALEXPMONTHS
        defaultDealResourcesShouldBeFound("externalexpmonths.lessThanOrEqual=" + DEFAULT_EXTERNALEXPMONTHS);

        // Get all the dealResourcesList where externalexpmonths is less than or equal to SMALLER_EXTERNALEXPMONTHS
        defaultDealResourcesShouldNotBeFound("externalexpmonths.lessThanOrEqual=" + SMALLER_EXTERNALEXPMONTHS);
    }

    @Test
    @Transactional
    void getAllDealResourcesByExternalexpmonthsIsLessThanSomething() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where externalexpmonths is less than DEFAULT_EXTERNALEXPMONTHS
        defaultDealResourcesShouldNotBeFound("externalexpmonths.lessThan=" + DEFAULT_EXTERNALEXPMONTHS);

        // Get all the dealResourcesList where externalexpmonths is less than UPDATED_EXTERNALEXPMONTHS
        defaultDealResourcesShouldBeFound("externalexpmonths.lessThan=" + UPDATED_EXTERNALEXPMONTHS);
    }

    @Test
    @Transactional
    void getAllDealResourcesByExternalexpmonthsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where externalexpmonths is greater than DEFAULT_EXTERNALEXPMONTHS
        defaultDealResourcesShouldNotBeFound("externalexpmonths.greaterThan=" + DEFAULT_EXTERNALEXPMONTHS);

        // Get all the dealResourcesList where externalexpmonths is greater than SMALLER_EXTERNALEXPMONTHS
        defaultDealResourcesShouldBeFound("externalexpmonths.greaterThan=" + SMALLER_EXTERNALEXPMONTHS);
    }

    @Test
    @Transactional
    void getAllDealResourcesByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where createdat equals to DEFAULT_CREATEDAT
        defaultDealResourcesShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the dealResourcesList where createdat equals to UPDATED_CREATEDAT
        defaultDealResourcesShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllDealResourcesByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultDealResourcesShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the dealResourcesList where createdat equals to UPDATED_CREATEDAT
        defaultDealResourcesShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllDealResourcesByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where createdat is not null
        defaultDealResourcesShouldBeFound("createdat.specified=true");

        // Get all the dealResourcesList where createdat is null
        defaultDealResourcesShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllDealResourcesByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where updatedat equals to DEFAULT_UPDATEDAT
        defaultDealResourcesShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the dealResourcesList where updatedat equals to UPDATED_UPDATEDAT
        defaultDealResourcesShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllDealResourcesByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultDealResourcesShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the dealResourcesList where updatedat equals to UPDATED_UPDATEDAT
        defaultDealResourcesShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllDealResourcesByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where updatedat is not null
        defaultDealResourcesShouldBeFound("updatedat.specified=true");

        // Get all the dealResourcesList where updatedat is null
        defaultDealResourcesShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllDealResourcesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where type equals to DEFAULT_TYPE
        defaultDealResourcesShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the dealResourcesList where type equals to UPDATED_TYPE
        defaultDealResourcesShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllDealResourcesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultDealResourcesShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the dealResourcesList where type equals to UPDATED_TYPE
        defaultDealResourcesShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllDealResourcesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where type is not null
        defaultDealResourcesShouldBeFound("type.specified=true");

        // Get all the dealResourcesList where type is null
        defaultDealResourcesShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllDealResourcesByTypeContainsSomething() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where type contains DEFAULT_TYPE
        defaultDealResourcesShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the dealResourcesList where type contains UPDATED_TYPE
        defaultDealResourcesShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllDealResourcesByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where type does not contain DEFAULT_TYPE
        defaultDealResourcesShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the dealResourcesList where type does not contain UPDATED_TYPE
        defaultDealResourcesShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllDealResourcesByIsactiveIsEqualToSomething() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where isactive equals to DEFAULT_ISACTIVE
        defaultDealResourcesShouldBeFound("isactive.equals=" + DEFAULT_ISACTIVE);

        // Get all the dealResourcesList where isactive equals to UPDATED_ISACTIVE
        defaultDealResourcesShouldNotBeFound("isactive.equals=" + UPDATED_ISACTIVE);
    }

    @Test
    @Transactional
    void getAllDealResourcesByIsactiveIsInShouldWork() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where isactive in DEFAULT_ISACTIVE or UPDATED_ISACTIVE
        defaultDealResourcesShouldBeFound("isactive.in=" + DEFAULT_ISACTIVE + "," + UPDATED_ISACTIVE);

        // Get all the dealResourcesList where isactive equals to UPDATED_ISACTIVE
        defaultDealResourcesShouldNotBeFound("isactive.in=" + UPDATED_ISACTIVE);
    }

    @Test
    @Transactional
    void getAllDealResourcesByIsactiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        // Get all the dealResourcesList where isactive is not null
        defaultDealResourcesShouldBeFound("isactive.specified=true");

        // Get all the dealResourcesList where isactive is null
        defaultDealResourcesShouldNotBeFound("isactive.specified=false");
    }

    @Test
    @Transactional
    void getAllDealResourcesByEmployeeIsEqualToSomething() throws Exception {
        Employees employee;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            dealResourcesRepository.saveAndFlush(dealResources);
            employee = EmployeesResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employee);
        em.flush();
        dealResources.setEmployee(employee);
        dealResourcesRepository.saveAndFlush(dealResources);
        Long employeeId = employee.getId();

        // Get all the dealResourcesList where employee equals to employeeId
        defaultDealResourcesShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the dealResourcesList where employee equals to (employeeId + 1)
        defaultDealResourcesShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    @Test
    @Transactional
    void getAllDealResourcesByDealrequirementmatchingresourcesResourceIsEqualToSomething() throws Exception {
        DealRequirementMatchingResources dealrequirementmatchingresourcesResource;
        if (TestUtil.findAll(em, DealRequirementMatchingResources.class).isEmpty()) {
            dealResourcesRepository.saveAndFlush(dealResources);
            dealrequirementmatchingresourcesResource = DealRequirementMatchingResourcesResourceIT.createEntity(em);
        } else {
            dealrequirementmatchingresourcesResource = TestUtil.findAll(em, DealRequirementMatchingResources.class).get(0);
        }
        em.persist(dealrequirementmatchingresourcesResource);
        em.flush();
        dealResources.addDealrequirementmatchingresourcesResource(dealrequirementmatchingresourcesResource);
        dealResourcesRepository.saveAndFlush(dealResources);
        Long dealrequirementmatchingresourcesResourceId = dealrequirementmatchingresourcesResource.getId();

        // Get all the dealResourcesList where dealrequirementmatchingresourcesResource equals to dealrequirementmatchingresourcesResourceId
        defaultDealResourcesShouldBeFound(
            "dealrequirementmatchingresourcesResourceId.equals=" + dealrequirementmatchingresourcesResourceId
        );

        // Get all the dealResourcesList where dealrequirementmatchingresourcesResource equals to (dealrequirementmatchingresourcesResourceId + 1)
        defaultDealResourcesShouldNotBeFound(
            "dealrequirementmatchingresourcesResourceId.equals=" + (dealrequirementmatchingresourcesResourceId + 1)
        );
    }

    @Test
    @Transactional
    void getAllDealResourcesByDealresourceskillsResourceIsEqualToSomething() throws Exception {
        DealResourceSkills dealresourceskillsResource;
        if (TestUtil.findAll(em, DealResourceSkills.class).isEmpty()) {
            dealResourcesRepository.saveAndFlush(dealResources);
            dealresourceskillsResource = DealResourceSkillsResourceIT.createEntity(em);
        } else {
            dealresourceskillsResource = TestUtil.findAll(em, DealResourceSkills.class).get(0);
        }
        em.persist(dealresourceskillsResource);
        em.flush();
        dealResources.addDealresourceskillsResource(dealresourceskillsResource);
        dealResourcesRepository.saveAndFlush(dealResources);
        Long dealresourceskillsResourceId = dealresourceskillsResource.getId();

        // Get all the dealResourcesList where dealresourceskillsResource equals to dealresourceskillsResourceId
        defaultDealResourcesShouldBeFound("dealresourceskillsResourceId.equals=" + dealresourceskillsResourceId);

        // Get all the dealResourcesList where dealresourceskillsResource equals to (dealresourceskillsResourceId + 1)
        defaultDealResourcesShouldNotBeFound("dealresourceskillsResourceId.equals=" + (dealresourceskillsResourceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDealResourcesShouldBeFound(String filter) throws Exception {
        restDealResourcesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dealResources.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME)))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME)))
            .andExpect(jsonPath("$.[*].joiningdate").value(hasItem(DEFAULT_JOININGDATE.toString())))
            .andExpect(jsonPath("$.[*].externalexpyears").value(hasItem(DEFAULT_EXTERNALEXPYEARS)))
            .andExpect(jsonPath("$.[*].externalexpmonths").value(hasItem(DEFAULT_EXTERNALEXPMONTHS)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restDealResourcesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDealResourcesShouldNotBeFound(String filter) throws Exception {
        restDealResourcesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDealResourcesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDealResources() throws Exception {
        // Get the dealResources
        restDealResourcesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDealResources() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        int databaseSizeBeforeUpdate = dealResourcesRepository.findAll().size();

        // Update the dealResources
        DealResources updatedDealResources = dealResourcesRepository.findById(dealResources.getId()).get();
        // Disconnect from session so that the updates on updatedDealResources are not directly saved in db
        em.detach(updatedDealResources);
        updatedDealResources
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .joiningdate(UPDATED_JOININGDATE)
            .externalexpyears(UPDATED_EXTERNALEXPYEARS)
            .externalexpmonths(UPDATED_EXTERNALEXPMONTHS)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .type(UPDATED_TYPE)
            .isactive(UPDATED_ISACTIVE);

        restDealResourcesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDealResources.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDealResources))
            )
            .andExpect(status().isOk());

        // Validate the DealResources in the database
        List<DealResources> dealResourcesList = dealResourcesRepository.findAll();
        assertThat(dealResourcesList).hasSize(databaseSizeBeforeUpdate);
        DealResources testDealResources = dealResourcesList.get(dealResourcesList.size() - 1);
        assertThat(testDealResources.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testDealResources.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testDealResources.getJoiningdate()).isEqualTo(UPDATED_JOININGDATE);
        assertThat(testDealResources.getExternalexpyears()).isEqualTo(UPDATED_EXTERNALEXPYEARS);
        assertThat(testDealResources.getExternalexpmonths()).isEqualTo(UPDATED_EXTERNALEXPMONTHS);
        assertThat(testDealResources.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testDealResources.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testDealResources.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDealResources.getIsactive()).isEqualTo(UPDATED_ISACTIVE);
    }

    @Test
    @Transactional
    void putNonExistingDealResources() throws Exception {
        int databaseSizeBeforeUpdate = dealResourcesRepository.findAll().size();
        dealResources.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDealResourcesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dealResources.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealResources))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealResources in the database
        List<DealResources> dealResourcesList = dealResourcesRepository.findAll();
        assertThat(dealResourcesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDealResources() throws Exception {
        int databaseSizeBeforeUpdate = dealResourcesRepository.findAll().size();
        dealResources.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealResourcesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealResources))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealResources in the database
        List<DealResources> dealResourcesList = dealResourcesRepository.findAll();
        assertThat(dealResourcesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDealResources() throws Exception {
        int databaseSizeBeforeUpdate = dealResourcesRepository.findAll().size();
        dealResources.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealResourcesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealResources))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DealResources in the database
        List<DealResources> dealResourcesList = dealResourcesRepository.findAll();
        assertThat(dealResourcesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDealResourcesWithPatch() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        int databaseSizeBeforeUpdate = dealResourcesRepository.findAll().size();

        // Update the dealResources using partial update
        DealResources partialUpdatedDealResources = new DealResources();
        partialUpdatedDealResources.setId(dealResources.getId());

        partialUpdatedDealResources
            .firstname(UPDATED_FIRSTNAME)
            .joiningdate(UPDATED_JOININGDATE)
            .externalexpyears(UPDATED_EXTERNALEXPYEARS)
            .externalexpmonths(UPDATED_EXTERNALEXPMONTHS)
            .createdat(UPDATED_CREATEDAT);

        restDealResourcesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDealResources.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDealResources))
            )
            .andExpect(status().isOk());

        // Validate the DealResources in the database
        List<DealResources> dealResourcesList = dealResourcesRepository.findAll();
        assertThat(dealResourcesList).hasSize(databaseSizeBeforeUpdate);
        DealResources testDealResources = dealResourcesList.get(dealResourcesList.size() - 1);
        assertThat(testDealResources.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testDealResources.getLastname()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testDealResources.getJoiningdate()).isEqualTo(UPDATED_JOININGDATE);
        assertThat(testDealResources.getExternalexpyears()).isEqualTo(UPDATED_EXTERNALEXPYEARS);
        assertThat(testDealResources.getExternalexpmonths()).isEqualTo(UPDATED_EXTERNALEXPMONTHS);
        assertThat(testDealResources.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testDealResources.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testDealResources.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDealResources.getIsactive()).isEqualTo(DEFAULT_ISACTIVE);
    }

    @Test
    @Transactional
    void fullUpdateDealResourcesWithPatch() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        int databaseSizeBeforeUpdate = dealResourcesRepository.findAll().size();

        // Update the dealResources using partial update
        DealResources partialUpdatedDealResources = new DealResources();
        partialUpdatedDealResources.setId(dealResources.getId());

        partialUpdatedDealResources
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .joiningdate(UPDATED_JOININGDATE)
            .externalexpyears(UPDATED_EXTERNALEXPYEARS)
            .externalexpmonths(UPDATED_EXTERNALEXPMONTHS)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .type(UPDATED_TYPE)
            .isactive(UPDATED_ISACTIVE);

        restDealResourcesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDealResources.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDealResources))
            )
            .andExpect(status().isOk());

        // Validate the DealResources in the database
        List<DealResources> dealResourcesList = dealResourcesRepository.findAll();
        assertThat(dealResourcesList).hasSize(databaseSizeBeforeUpdate);
        DealResources testDealResources = dealResourcesList.get(dealResourcesList.size() - 1);
        assertThat(testDealResources.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testDealResources.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testDealResources.getJoiningdate()).isEqualTo(UPDATED_JOININGDATE);
        assertThat(testDealResources.getExternalexpyears()).isEqualTo(UPDATED_EXTERNALEXPYEARS);
        assertThat(testDealResources.getExternalexpmonths()).isEqualTo(UPDATED_EXTERNALEXPMONTHS);
        assertThat(testDealResources.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testDealResources.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testDealResources.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDealResources.getIsactive()).isEqualTo(UPDATED_ISACTIVE);
    }

    @Test
    @Transactional
    void patchNonExistingDealResources() throws Exception {
        int databaseSizeBeforeUpdate = dealResourcesRepository.findAll().size();
        dealResources.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDealResourcesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dealResources.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dealResources))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealResources in the database
        List<DealResources> dealResourcesList = dealResourcesRepository.findAll();
        assertThat(dealResourcesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDealResources() throws Exception {
        int databaseSizeBeforeUpdate = dealResourcesRepository.findAll().size();
        dealResources.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealResourcesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dealResources))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealResources in the database
        List<DealResources> dealResourcesList = dealResourcesRepository.findAll();
        assertThat(dealResourcesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDealResources() throws Exception {
        int databaseSizeBeforeUpdate = dealResourcesRepository.findAll().size();
        dealResources.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealResourcesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dealResources))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DealResources in the database
        List<DealResources> dealResourcesList = dealResourcesRepository.findAll();
        assertThat(dealResourcesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDealResources() throws Exception {
        // Initialize the database
        dealResourcesRepository.saveAndFlush(dealResources);

        int databaseSizeBeforeDelete = dealResourcesRepository.findAll().size();

        // Delete the dealResources
        restDealResourcesMockMvc
            .perform(delete(ENTITY_API_URL_ID, dealResources.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DealResources> dealResourcesList = dealResourcesRepository.findAll();
        assertThat(dealResourcesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
