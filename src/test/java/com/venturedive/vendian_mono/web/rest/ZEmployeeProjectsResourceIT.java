package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.EmployeeProjects;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.Events;
import com.venturedive.vendian_mono.domain.Projects;
import com.venturedive.vendian_mono.domain.ZEmployeeProjects;
import com.venturedive.vendian_mono.repository.ZEmployeeProjectsRepository;
import com.venturedive.vendian_mono.service.criteria.ZEmployeeProjectsCriteria;
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
 * Integration tests for the {@link ZEmployeeProjectsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ZEmployeeProjectsResourceIT {

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Instant DEFAULT_STARTDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STARTDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ENDDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ENDDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ALLOCATION = false;
    private static final Boolean UPDATED_ALLOCATION = true;

    private static final String DEFAULT_BILLED = "AAAAAAAAAA";
    private static final String UPDATED_BILLED = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_DELIVERYMANAGERID = 1;
    private static final Integer UPDATED_DELIVERYMANAGERID = 2;
    private static final Integer SMALLER_DELIVERYMANAGERID = 1 - 1;

    private static final Integer DEFAULT_ARCHITECTID = 1;
    private static final Integer UPDATED_ARCHITECTID = 2;
    private static final Integer SMALLER_ARCHITECTID = 1 - 1;

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final Instant DEFAULT_PREVIOUSENDDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PREVIOUSENDDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DATA = "AAAAAAAAAA";
    private static final String UPDATED_DATA = "BBBBBBBBBB";

    private static final Instant DEFAULT_EXTENDEDENDDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXTENDEDENDDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PROBABILITY = "AAAAAAAAAA";
    private static final String UPDATED_PROBABILITY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/z-employee-projects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ZEmployeeProjectsRepository zEmployeeProjectsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restZEmployeeProjectsMockMvc;

    private ZEmployeeProjects zEmployeeProjects;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ZEmployeeProjects createEntity(EntityManager em) {
        ZEmployeeProjects zEmployeeProjects = new ZEmployeeProjects()
            .status(DEFAULT_STATUS)
            .type(DEFAULT_TYPE)
            .startdate(DEFAULT_STARTDATE)
            .enddate(DEFAULT_ENDDATE)
            .name(DEFAULT_NAME)
            .allocation(DEFAULT_ALLOCATION)
            .billed(DEFAULT_BILLED)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT)
            .deliverymanagerid(DEFAULT_DELIVERYMANAGERID)
            .architectid(DEFAULT_ARCHITECTID)
            .notes(DEFAULT_NOTES)
            .previousenddate(DEFAULT_PREVIOUSENDDATE)
            .data(DEFAULT_DATA)
            .extendedenddate(DEFAULT_EXTENDEDENDDATE)
            .probability(DEFAULT_PROBABILITY);
        return zEmployeeProjects;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ZEmployeeProjects createUpdatedEntity(EntityManager em) {
        ZEmployeeProjects zEmployeeProjects = new ZEmployeeProjects()
            .status(UPDATED_STATUS)
            .type(UPDATED_TYPE)
            .startdate(UPDATED_STARTDATE)
            .enddate(UPDATED_ENDDATE)
            .name(UPDATED_NAME)
            .allocation(UPDATED_ALLOCATION)
            .billed(UPDATED_BILLED)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .deliverymanagerid(UPDATED_DELIVERYMANAGERID)
            .architectid(UPDATED_ARCHITECTID)
            .notes(UPDATED_NOTES)
            .previousenddate(UPDATED_PREVIOUSENDDATE)
            .data(UPDATED_DATA)
            .extendedenddate(UPDATED_EXTENDEDENDDATE)
            .probability(UPDATED_PROBABILITY);
        return zEmployeeProjects;
    }

    @BeforeEach
    public void initTest() {
        zEmployeeProjects = createEntity(em);
    }

    @Test
    @Transactional
    void createZEmployeeProjects() throws Exception {
        int databaseSizeBeforeCreate = zEmployeeProjectsRepository.findAll().size();
        // Create the ZEmployeeProjects
        restZEmployeeProjectsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(zEmployeeProjects))
            )
            .andExpect(status().isCreated());

        // Validate the ZEmployeeProjects in the database
        List<ZEmployeeProjects> zEmployeeProjectsList = zEmployeeProjectsRepository.findAll();
        assertThat(zEmployeeProjectsList).hasSize(databaseSizeBeforeCreate + 1);
        ZEmployeeProjects testZEmployeeProjects = zEmployeeProjectsList.get(zEmployeeProjectsList.size() - 1);
        assertThat(testZEmployeeProjects.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testZEmployeeProjects.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testZEmployeeProjects.getStartdate()).isEqualTo(DEFAULT_STARTDATE);
        assertThat(testZEmployeeProjects.getEnddate()).isEqualTo(DEFAULT_ENDDATE);
        assertThat(testZEmployeeProjects.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testZEmployeeProjects.getAllocation()).isEqualTo(DEFAULT_ALLOCATION);
        assertThat(testZEmployeeProjects.getBilled()).isEqualTo(DEFAULT_BILLED);
        assertThat(testZEmployeeProjects.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testZEmployeeProjects.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testZEmployeeProjects.getDeliverymanagerid()).isEqualTo(DEFAULT_DELIVERYMANAGERID);
        assertThat(testZEmployeeProjects.getArchitectid()).isEqualTo(DEFAULT_ARCHITECTID);
        assertThat(testZEmployeeProjects.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testZEmployeeProjects.getPreviousenddate()).isEqualTo(DEFAULT_PREVIOUSENDDATE);
        assertThat(testZEmployeeProjects.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testZEmployeeProjects.getExtendedenddate()).isEqualTo(DEFAULT_EXTENDEDENDDATE);
        assertThat(testZEmployeeProjects.getProbability()).isEqualTo(DEFAULT_PROBABILITY);
    }

    @Test
    @Transactional
    void createZEmployeeProjectsWithExistingId() throws Exception {
        // Create the ZEmployeeProjects with an existing ID
        zEmployeeProjects.setId(1L);

        int databaseSizeBeforeCreate = zEmployeeProjectsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restZEmployeeProjectsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(zEmployeeProjects))
            )
            .andExpect(status().isBadRequest());

        // Validate the ZEmployeeProjects in the database
        List<ZEmployeeProjects> zEmployeeProjectsList = zEmployeeProjectsRepository.findAll();
        assertThat(zEmployeeProjectsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = zEmployeeProjectsRepository.findAll().size();
        // set the field null
        zEmployeeProjects.setCreatedat(null);

        // Create the ZEmployeeProjects, which fails.

        restZEmployeeProjectsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(zEmployeeProjects))
            )
            .andExpect(status().isBadRequest());

        List<ZEmployeeProjects> zEmployeeProjectsList = zEmployeeProjectsRepository.findAll();
        assertThat(zEmployeeProjectsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = zEmployeeProjectsRepository.findAll().size();
        // set the field null
        zEmployeeProjects.setUpdatedat(null);

        // Create the ZEmployeeProjects, which fails.

        restZEmployeeProjectsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(zEmployeeProjects))
            )
            .andExpect(status().isBadRequest());

        List<ZEmployeeProjects> zEmployeeProjectsList = zEmployeeProjectsRepository.findAll();
        assertThat(zEmployeeProjectsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjects() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList
        restZEmployeeProjectsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zEmployeeProjects.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].startdate").value(hasItem(DEFAULT_STARTDATE.toString())))
            .andExpect(jsonPath("$.[*].enddate").value(hasItem(DEFAULT_ENDDATE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].allocation").value(hasItem(DEFAULT_ALLOCATION.booleanValue())))
            .andExpect(jsonPath("$.[*].billed").value(hasItem(DEFAULT_BILLED)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].deliverymanagerid").value(hasItem(DEFAULT_DELIVERYMANAGERID)))
            .andExpect(jsonPath("$.[*].architectid").value(hasItem(DEFAULT_ARCHITECTID)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].previousenddate").value(hasItem(DEFAULT_PREVIOUSENDDATE.toString())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA)))
            .andExpect(jsonPath("$.[*].extendedenddate").value(hasItem(DEFAULT_EXTENDEDENDDATE.toString())))
            .andExpect(jsonPath("$.[*].probability").value(hasItem(DEFAULT_PROBABILITY)));
    }

    @Test
    @Transactional
    void getZEmployeeProjects() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get the zEmployeeProjects
        restZEmployeeProjectsMockMvc
            .perform(get(ENTITY_API_URL_ID, zEmployeeProjects.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(zEmployeeProjects.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.startdate").value(DEFAULT_STARTDATE.toString()))
            .andExpect(jsonPath("$.enddate").value(DEFAULT_ENDDATE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.allocation").value(DEFAULT_ALLOCATION.booleanValue()))
            .andExpect(jsonPath("$.billed").value(DEFAULT_BILLED))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()))
            .andExpect(jsonPath("$.deliverymanagerid").value(DEFAULT_DELIVERYMANAGERID))
            .andExpect(jsonPath("$.architectid").value(DEFAULT_ARCHITECTID))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.previousenddate").value(DEFAULT_PREVIOUSENDDATE.toString()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA))
            .andExpect(jsonPath("$.extendedenddate").value(DEFAULT_EXTENDEDENDDATE.toString()))
            .andExpect(jsonPath("$.probability").value(DEFAULT_PROBABILITY));
    }

    @Test
    @Transactional
    void getZEmployeeProjectsByIdFiltering() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        Long id = zEmployeeProjects.getId();

        defaultZEmployeeProjectsShouldBeFound("id.equals=" + id);
        defaultZEmployeeProjectsShouldNotBeFound("id.notEquals=" + id);

        defaultZEmployeeProjectsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultZEmployeeProjectsShouldNotBeFound("id.greaterThan=" + id);

        defaultZEmployeeProjectsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultZEmployeeProjectsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where status equals to DEFAULT_STATUS
        defaultZEmployeeProjectsShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the zEmployeeProjectsList where status equals to UPDATED_STATUS
        defaultZEmployeeProjectsShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultZEmployeeProjectsShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the zEmployeeProjectsList where status equals to UPDATED_STATUS
        defaultZEmployeeProjectsShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where status is not null
        defaultZEmployeeProjectsShouldBeFound("status.specified=true");

        // Get all the zEmployeeProjectsList where status is null
        defaultZEmployeeProjectsShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where type equals to DEFAULT_TYPE
        defaultZEmployeeProjectsShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the zEmployeeProjectsList where type equals to UPDATED_TYPE
        defaultZEmployeeProjectsShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultZEmployeeProjectsShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the zEmployeeProjectsList where type equals to UPDATED_TYPE
        defaultZEmployeeProjectsShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where type is not null
        defaultZEmployeeProjectsShouldBeFound("type.specified=true");

        // Get all the zEmployeeProjectsList where type is null
        defaultZEmployeeProjectsShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByTypeContainsSomething() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where type contains DEFAULT_TYPE
        defaultZEmployeeProjectsShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the zEmployeeProjectsList where type contains UPDATED_TYPE
        defaultZEmployeeProjectsShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where type does not contain DEFAULT_TYPE
        defaultZEmployeeProjectsShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the zEmployeeProjectsList where type does not contain UPDATED_TYPE
        defaultZEmployeeProjectsShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByStartdateIsEqualToSomething() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where startdate equals to DEFAULT_STARTDATE
        defaultZEmployeeProjectsShouldBeFound("startdate.equals=" + DEFAULT_STARTDATE);

        // Get all the zEmployeeProjectsList where startdate equals to UPDATED_STARTDATE
        defaultZEmployeeProjectsShouldNotBeFound("startdate.equals=" + UPDATED_STARTDATE);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByStartdateIsInShouldWork() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where startdate in DEFAULT_STARTDATE or UPDATED_STARTDATE
        defaultZEmployeeProjectsShouldBeFound("startdate.in=" + DEFAULT_STARTDATE + "," + UPDATED_STARTDATE);

        // Get all the zEmployeeProjectsList where startdate equals to UPDATED_STARTDATE
        defaultZEmployeeProjectsShouldNotBeFound("startdate.in=" + UPDATED_STARTDATE);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByStartdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where startdate is not null
        defaultZEmployeeProjectsShouldBeFound("startdate.specified=true");

        // Get all the zEmployeeProjectsList where startdate is null
        defaultZEmployeeProjectsShouldNotBeFound("startdate.specified=false");
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByEnddateIsEqualToSomething() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where enddate equals to DEFAULT_ENDDATE
        defaultZEmployeeProjectsShouldBeFound("enddate.equals=" + DEFAULT_ENDDATE);

        // Get all the zEmployeeProjectsList where enddate equals to UPDATED_ENDDATE
        defaultZEmployeeProjectsShouldNotBeFound("enddate.equals=" + UPDATED_ENDDATE);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByEnddateIsInShouldWork() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where enddate in DEFAULT_ENDDATE or UPDATED_ENDDATE
        defaultZEmployeeProjectsShouldBeFound("enddate.in=" + DEFAULT_ENDDATE + "," + UPDATED_ENDDATE);

        // Get all the zEmployeeProjectsList where enddate equals to UPDATED_ENDDATE
        defaultZEmployeeProjectsShouldNotBeFound("enddate.in=" + UPDATED_ENDDATE);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByEnddateIsNullOrNotNull() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where enddate is not null
        defaultZEmployeeProjectsShouldBeFound("enddate.specified=true");

        // Get all the zEmployeeProjectsList where enddate is null
        defaultZEmployeeProjectsShouldNotBeFound("enddate.specified=false");
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where name equals to DEFAULT_NAME
        defaultZEmployeeProjectsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the zEmployeeProjectsList where name equals to UPDATED_NAME
        defaultZEmployeeProjectsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultZEmployeeProjectsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the zEmployeeProjectsList where name equals to UPDATED_NAME
        defaultZEmployeeProjectsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where name is not null
        defaultZEmployeeProjectsShouldBeFound("name.specified=true");

        // Get all the zEmployeeProjectsList where name is null
        defaultZEmployeeProjectsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByNameContainsSomething() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where name contains DEFAULT_NAME
        defaultZEmployeeProjectsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the zEmployeeProjectsList where name contains UPDATED_NAME
        defaultZEmployeeProjectsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where name does not contain DEFAULT_NAME
        defaultZEmployeeProjectsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the zEmployeeProjectsList where name does not contain UPDATED_NAME
        defaultZEmployeeProjectsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByAllocationIsEqualToSomething() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where allocation equals to DEFAULT_ALLOCATION
        defaultZEmployeeProjectsShouldBeFound("allocation.equals=" + DEFAULT_ALLOCATION);

        // Get all the zEmployeeProjectsList where allocation equals to UPDATED_ALLOCATION
        defaultZEmployeeProjectsShouldNotBeFound("allocation.equals=" + UPDATED_ALLOCATION);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByAllocationIsInShouldWork() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where allocation in DEFAULT_ALLOCATION or UPDATED_ALLOCATION
        defaultZEmployeeProjectsShouldBeFound("allocation.in=" + DEFAULT_ALLOCATION + "," + UPDATED_ALLOCATION);

        // Get all the zEmployeeProjectsList where allocation equals to UPDATED_ALLOCATION
        defaultZEmployeeProjectsShouldNotBeFound("allocation.in=" + UPDATED_ALLOCATION);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByAllocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where allocation is not null
        defaultZEmployeeProjectsShouldBeFound("allocation.specified=true");

        // Get all the zEmployeeProjectsList where allocation is null
        defaultZEmployeeProjectsShouldNotBeFound("allocation.specified=false");
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByBilledIsEqualToSomething() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where billed equals to DEFAULT_BILLED
        defaultZEmployeeProjectsShouldBeFound("billed.equals=" + DEFAULT_BILLED);

        // Get all the zEmployeeProjectsList where billed equals to UPDATED_BILLED
        defaultZEmployeeProjectsShouldNotBeFound("billed.equals=" + UPDATED_BILLED);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByBilledIsInShouldWork() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where billed in DEFAULT_BILLED or UPDATED_BILLED
        defaultZEmployeeProjectsShouldBeFound("billed.in=" + DEFAULT_BILLED + "," + UPDATED_BILLED);

        // Get all the zEmployeeProjectsList where billed equals to UPDATED_BILLED
        defaultZEmployeeProjectsShouldNotBeFound("billed.in=" + UPDATED_BILLED);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByBilledIsNullOrNotNull() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where billed is not null
        defaultZEmployeeProjectsShouldBeFound("billed.specified=true");

        // Get all the zEmployeeProjectsList where billed is null
        defaultZEmployeeProjectsShouldNotBeFound("billed.specified=false");
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByBilledContainsSomething() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where billed contains DEFAULT_BILLED
        defaultZEmployeeProjectsShouldBeFound("billed.contains=" + DEFAULT_BILLED);

        // Get all the zEmployeeProjectsList where billed contains UPDATED_BILLED
        defaultZEmployeeProjectsShouldNotBeFound("billed.contains=" + UPDATED_BILLED);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByBilledNotContainsSomething() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where billed does not contain DEFAULT_BILLED
        defaultZEmployeeProjectsShouldNotBeFound("billed.doesNotContain=" + DEFAULT_BILLED);

        // Get all the zEmployeeProjectsList where billed does not contain UPDATED_BILLED
        defaultZEmployeeProjectsShouldBeFound("billed.doesNotContain=" + UPDATED_BILLED);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where createdat equals to DEFAULT_CREATEDAT
        defaultZEmployeeProjectsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the zEmployeeProjectsList where createdat equals to UPDATED_CREATEDAT
        defaultZEmployeeProjectsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultZEmployeeProjectsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the zEmployeeProjectsList where createdat equals to UPDATED_CREATEDAT
        defaultZEmployeeProjectsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where createdat is not null
        defaultZEmployeeProjectsShouldBeFound("createdat.specified=true");

        // Get all the zEmployeeProjectsList where createdat is null
        defaultZEmployeeProjectsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultZEmployeeProjectsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the zEmployeeProjectsList where updatedat equals to UPDATED_UPDATEDAT
        defaultZEmployeeProjectsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultZEmployeeProjectsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the zEmployeeProjectsList where updatedat equals to UPDATED_UPDATEDAT
        defaultZEmployeeProjectsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where updatedat is not null
        defaultZEmployeeProjectsShouldBeFound("updatedat.specified=true");

        // Get all the zEmployeeProjectsList where updatedat is null
        defaultZEmployeeProjectsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByDeliverymanageridIsEqualToSomething() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where deliverymanagerid equals to DEFAULT_DELIVERYMANAGERID
        defaultZEmployeeProjectsShouldBeFound("deliverymanagerid.equals=" + DEFAULT_DELIVERYMANAGERID);

        // Get all the zEmployeeProjectsList where deliverymanagerid equals to UPDATED_DELIVERYMANAGERID
        defaultZEmployeeProjectsShouldNotBeFound("deliverymanagerid.equals=" + UPDATED_DELIVERYMANAGERID);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByDeliverymanageridIsInShouldWork() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where deliverymanagerid in DEFAULT_DELIVERYMANAGERID or UPDATED_DELIVERYMANAGERID
        defaultZEmployeeProjectsShouldBeFound("deliverymanagerid.in=" + DEFAULT_DELIVERYMANAGERID + "," + UPDATED_DELIVERYMANAGERID);

        // Get all the zEmployeeProjectsList where deliverymanagerid equals to UPDATED_DELIVERYMANAGERID
        defaultZEmployeeProjectsShouldNotBeFound("deliverymanagerid.in=" + UPDATED_DELIVERYMANAGERID);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByDeliverymanageridIsNullOrNotNull() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where deliverymanagerid is not null
        defaultZEmployeeProjectsShouldBeFound("deliverymanagerid.specified=true");

        // Get all the zEmployeeProjectsList where deliverymanagerid is null
        defaultZEmployeeProjectsShouldNotBeFound("deliverymanagerid.specified=false");
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByDeliverymanageridIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where deliverymanagerid is greater than or equal to DEFAULT_DELIVERYMANAGERID
        defaultZEmployeeProjectsShouldBeFound("deliverymanagerid.greaterThanOrEqual=" + DEFAULT_DELIVERYMANAGERID);

        // Get all the zEmployeeProjectsList where deliverymanagerid is greater than or equal to UPDATED_DELIVERYMANAGERID
        defaultZEmployeeProjectsShouldNotBeFound("deliverymanagerid.greaterThanOrEqual=" + UPDATED_DELIVERYMANAGERID);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByDeliverymanageridIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where deliverymanagerid is less than or equal to DEFAULT_DELIVERYMANAGERID
        defaultZEmployeeProjectsShouldBeFound("deliverymanagerid.lessThanOrEqual=" + DEFAULT_DELIVERYMANAGERID);

        // Get all the zEmployeeProjectsList where deliverymanagerid is less than or equal to SMALLER_DELIVERYMANAGERID
        defaultZEmployeeProjectsShouldNotBeFound("deliverymanagerid.lessThanOrEqual=" + SMALLER_DELIVERYMANAGERID);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByDeliverymanageridIsLessThanSomething() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where deliverymanagerid is less than DEFAULT_DELIVERYMANAGERID
        defaultZEmployeeProjectsShouldNotBeFound("deliverymanagerid.lessThan=" + DEFAULT_DELIVERYMANAGERID);

        // Get all the zEmployeeProjectsList where deliverymanagerid is less than UPDATED_DELIVERYMANAGERID
        defaultZEmployeeProjectsShouldBeFound("deliverymanagerid.lessThan=" + UPDATED_DELIVERYMANAGERID);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByDeliverymanageridIsGreaterThanSomething() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where deliverymanagerid is greater than DEFAULT_DELIVERYMANAGERID
        defaultZEmployeeProjectsShouldNotBeFound("deliverymanagerid.greaterThan=" + DEFAULT_DELIVERYMANAGERID);

        // Get all the zEmployeeProjectsList where deliverymanagerid is greater than SMALLER_DELIVERYMANAGERID
        defaultZEmployeeProjectsShouldBeFound("deliverymanagerid.greaterThan=" + SMALLER_DELIVERYMANAGERID);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByArchitectidIsEqualToSomething() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where architectid equals to DEFAULT_ARCHITECTID
        defaultZEmployeeProjectsShouldBeFound("architectid.equals=" + DEFAULT_ARCHITECTID);

        // Get all the zEmployeeProjectsList where architectid equals to UPDATED_ARCHITECTID
        defaultZEmployeeProjectsShouldNotBeFound("architectid.equals=" + UPDATED_ARCHITECTID);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByArchitectidIsInShouldWork() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where architectid in DEFAULT_ARCHITECTID or UPDATED_ARCHITECTID
        defaultZEmployeeProjectsShouldBeFound("architectid.in=" + DEFAULT_ARCHITECTID + "," + UPDATED_ARCHITECTID);

        // Get all the zEmployeeProjectsList where architectid equals to UPDATED_ARCHITECTID
        defaultZEmployeeProjectsShouldNotBeFound("architectid.in=" + UPDATED_ARCHITECTID);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByArchitectidIsNullOrNotNull() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where architectid is not null
        defaultZEmployeeProjectsShouldBeFound("architectid.specified=true");

        // Get all the zEmployeeProjectsList where architectid is null
        defaultZEmployeeProjectsShouldNotBeFound("architectid.specified=false");
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByArchitectidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where architectid is greater than or equal to DEFAULT_ARCHITECTID
        defaultZEmployeeProjectsShouldBeFound("architectid.greaterThanOrEqual=" + DEFAULT_ARCHITECTID);

        // Get all the zEmployeeProjectsList where architectid is greater than or equal to UPDATED_ARCHITECTID
        defaultZEmployeeProjectsShouldNotBeFound("architectid.greaterThanOrEqual=" + UPDATED_ARCHITECTID);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByArchitectidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where architectid is less than or equal to DEFAULT_ARCHITECTID
        defaultZEmployeeProjectsShouldBeFound("architectid.lessThanOrEqual=" + DEFAULT_ARCHITECTID);

        // Get all the zEmployeeProjectsList where architectid is less than or equal to SMALLER_ARCHITECTID
        defaultZEmployeeProjectsShouldNotBeFound("architectid.lessThanOrEqual=" + SMALLER_ARCHITECTID);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByArchitectidIsLessThanSomething() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where architectid is less than DEFAULT_ARCHITECTID
        defaultZEmployeeProjectsShouldNotBeFound("architectid.lessThan=" + DEFAULT_ARCHITECTID);

        // Get all the zEmployeeProjectsList where architectid is less than UPDATED_ARCHITECTID
        defaultZEmployeeProjectsShouldBeFound("architectid.lessThan=" + UPDATED_ARCHITECTID);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByArchitectidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where architectid is greater than DEFAULT_ARCHITECTID
        defaultZEmployeeProjectsShouldNotBeFound("architectid.greaterThan=" + DEFAULT_ARCHITECTID);

        // Get all the zEmployeeProjectsList where architectid is greater than SMALLER_ARCHITECTID
        defaultZEmployeeProjectsShouldBeFound("architectid.greaterThan=" + SMALLER_ARCHITECTID);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByNotesIsEqualToSomething() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where notes equals to DEFAULT_NOTES
        defaultZEmployeeProjectsShouldBeFound("notes.equals=" + DEFAULT_NOTES);

        // Get all the zEmployeeProjectsList where notes equals to UPDATED_NOTES
        defaultZEmployeeProjectsShouldNotBeFound("notes.equals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByNotesIsInShouldWork() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where notes in DEFAULT_NOTES or UPDATED_NOTES
        defaultZEmployeeProjectsShouldBeFound("notes.in=" + DEFAULT_NOTES + "," + UPDATED_NOTES);

        // Get all the zEmployeeProjectsList where notes equals to UPDATED_NOTES
        defaultZEmployeeProjectsShouldNotBeFound("notes.in=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByNotesIsNullOrNotNull() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where notes is not null
        defaultZEmployeeProjectsShouldBeFound("notes.specified=true");

        // Get all the zEmployeeProjectsList where notes is null
        defaultZEmployeeProjectsShouldNotBeFound("notes.specified=false");
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByNotesContainsSomething() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where notes contains DEFAULT_NOTES
        defaultZEmployeeProjectsShouldBeFound("notes.contains=" + DEFAULT_NOTES);

        // Get all the zEmployeeProjectsList where notes contains UPDATED_NOTES
        defaultZEmployeeProjectsShouldNotBeFound("notes.contains=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByNotesNotContainsSomething() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where notes does not contain DEFAULT_NOTES
        defaultZEmployeeProjectsShouldNotBeFound("notes.doesNotContain=" + DEFAULT_NOTES);

        // Get all the zEmployeeProjectsList where notes does not contain UPDATED_NOTES
        defaultZEmployeeProjectsShouldBeFound("notes.doesNotContain=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByPreviousenddateIsEqualToSomething() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where previousenddate equals to DEFAULT_PREVIOUSENDDATE
        defaultZEmployeeProjectsShouldBeFound("previousenddate.equals=" + DEFAULT_PREVIOUSENDDATE);

        // Get all the zEmployeeProjectsList where previousenddate equals to UPDATED_PREVIOUSENDDATE
        defaultZEmployeeProjectsShouldNotBeFound("previousenddate.equals=" + UPDATED_PREVIOUSENDDATE);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByPreviousenddateIsInShouldWork() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where previousenddate in DEFAULT_PREVIOUSENDDATE or UPDATED_PREVIOUSENDDATE
        defaultZEmployeeProjectsShouldBeFound("previousenddate.in=" + DEFAULT_PREVIOUSENDDATE + "," + UPDATED_PREVIOUSENDDATE);

        // Get all the zEmployeeProjectsList where previousenddate equals to UPDATED_PREVIOUSENDDATE
        defaultZEmployeeProjectsShouldNotBeFound("previousenddate.in=" + UPDATED_PREVIOUSENDDATE);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByPreviousenddateIsNullOrNotNull() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where previousenddate is not null
        defaultZEmployeeProjectsShouldBeFound("previousenddate.specified=true");

        // Get all the zEmployeeProjectsList where previousenddate is null
        defaultZEmployeeProjectsShouldNotBeFound("previousenddate.specified=false");
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByDataIsEqualToSomething() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where data equals to DEFAULT_DATA
        defaultZEmployeeProjectsShouldBeFound("data.equals=" + DEFAULT_DATA);

        // Get all the zEmployeeProjectsList where data equals to UPDATED_DATA
        defaultZEmployeeProjectsShouldNotBeFound("data.equals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByDataIsInShouldWork() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where data in DEFAULT_DATA or UPDATED_DATA
        defaultZEmployeeProjectsShouldBeFound("data.in=" + DEFAULT_DATA + "," + UPDATED_DATA);

        // Get all the zEmployeeProjectsList where data equals to UPDATED_DATA
        defaultZEmployeeProjectsShouldNotBeFound("data.in=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where data is not null
        defaultZEmployeeProjectsShouldBeFound("data.specified=true");

        // Get all the zEmployeeProjectsList where data is null
        defaultZEmployeeProjectsShouldNotBeFound("data.specified=false");
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByDataContainsSomething() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where data contains DEFAULT_DATA
        defaultZEmployeeProjectsShouldBeFound("data.contains=" + DEFAULT_DATA);

        // Get all the zEmployeeProjectsList where data contains UPDATED_DATA
        defaultZEmployeeProjectsShouldNotBeFound("data.contains=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByDataNotContainsSomething() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where data does not contain DEFAULT_DATA
        defaultZEmployeeProjectsShouldNotBeFound("data.doesNotContain=" + DEFAULT_DATA);

        // Get all the zEmployeeProjectsList where data does not contain UPDATED_DATA
        defaultZEmployeeProjectsShouldBeFound("data.doesNotContain=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByExtendedenddateIsEqualToSomething() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where extendedenddate equals to DEFAULT_EXTENDEDENDDATE
        defaultZEmployeeProjectsShouldBeFound("extendedenddate.equals=" + DEFAULT_EXTENDEDENDDATE);

        // Get all the zEmployeeProjectsList where extendedenddate equals to UPDATED_EXTENDEDENDDATE
        defaultZEmployeeProjectsShouldNotBeFound("extendedenddate.equals=" + UPDATED_EXTENDEDENDDATE);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByExtendedenddateIsInShouldWork() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where extendedenddate in DEFAULT_EXTENDEDENDDATE or UPDATED_EXTENDEDENDDATE
        defaultZEmployeeProjectsShouldBeFound("extendedenddate.in=" + DEFAULT_EXTENDEDENDDATE + "," + UPDATED_EXTENDEDENDDATE);

        // Get all the zEmployeeProjectsList where extendedenddate equals to UPDATED_EXTENDEDENDDATE
        defaultZEmployeeProjectsShouldNotBeFound("extendedenddate.in=" + UPDATED_EXTENDEDENDDATE);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByExtendedenddateIsNullOrNotNull() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where extendedenddate is not null
        defaultZEmployeeProjectsShouldBeFound("extendedenddate.specified=true");

        // Get all the zEmployeeProjectsList where extendedenddate is null
        defaultZEmployeeProjectsShouldNotBeFound("extendedenddate.specified=false");
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByProbabilityIsEqualToSomething() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where probability equals to DEFAULT_PROBABILITY
        defaultZEmployeeProjectsShouldBeFound("probability.equals=" + DEFAULT_PROBABILITY);

        // Get all the zEmployeeProjectsList where probability equals to UPDATED_PROBABILITY
        defaultZEmployeeProjectsShouldNotBeFound("probability.equals=" + UPDATED_PROBABILITY);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByProbabilityIsInShouldWork() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where probability in DEFAULT_PROBABILITY or UPDATED_PROBABILITY
        defaultZEmployeeProjectsShouldBeFound("probability.in=" + DEFAULT_PROBABILITY + "," + UPDATED_PROBABILITY);

        // Get all the zEmployeeProjectsList where probability equals to UPDATED_PROBABILITY
        defaultZEmployeeProjectsShouldNotBeFound("probability.in=" + UPDATED_PROBABILITY);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByProbabilityIsNullOrNotNull() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where probability is not null
        defaultZEmployeeProjectsShouldBeFound("probability.specified=true");

        // Get all the zEmployeeProjectsList where probability is null
        defaultZEmployeeProjectsShouldNotBeFound("probability.specified=false");
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByProbabilityContainsSomething() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where probability contains DEFAULT_PROBABILITY
        defaultZEmployeeProjectsShouldBeFound("probability.contains=" + DEFAULT_PROBABILITY);

        // Get all the zEmployeeProjectsList where probability contains UPDATED_PROBABILITY
        defaultZEmployeeProjectsShouldNotBeFound("probability.contains=" + UPDATED_PROBABILITY);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByProbabilityNotContainsSomething() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        // Get all the zEmployeeProjectsList where probability does not contain DEFAULT_PROBABILITY
        defaultZEmployeeProjectsShouldNotBeFound("probability.doesNotContain=" + DEFAULT_PROBABILITY);

        // Get all the zEmployeeProjectsList where probability does not contain UPDATED_PROBABILITY
        defaultZEmployeeProjectsShouldBeFound("probability.doesNotContain=" + UPDATED_PROBABILITY);
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByEventIsEqualToSomething() throws Exception {
        Events event;
        if (TestUtil.findAll(em, Events.class).isEmpty()) {
            zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);
            event = EventsResourceIT.createEntity(em);
        } else {
            event = TestUtil.findAll(em, Events.class).get(0);
        }
        em.persist(event);
        em.flush();
        zEmployeeProjects.setEvent(event);
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);
        Long eventId = event.getId();

        // Get all the zEmployeeProjectsList where event equals to eventId
        defaultZEmployeeProjectsShouldBeFound("eventId.equals=" + eventId);

        // Get all the zEmployeeProjectsList where event equals to (eventId + 1)
        defaultZEmployeeProjectsShouldNotBeFound("eventId.equals=" + (eventId + 1));
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByEmployeeIsEqualToSomething() throws Exception {
        Employees employee;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);
            employee = EmployeesResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employee);
        em.flush();
        zEmployeeProjects.setEmployee(employee);
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);
        Long employeeId = employee.getId();

        // Get all the zEmployeeProjectsList where employee equals to employeeId
        defaultZEmployeeProjectsShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the zEmployeeProjectsList where employee equals to (employeeId + 1)
        defaultZEmployeeProjectsShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByProjectIsEqualToSomething() throws Exception {
        Projects project;
        if (TestUtil.findAll(em, Projects.class).isEmpty()) {
            zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);
            project = ProjectsResourceIT.createEntity(em);
        } else {
            project = TestUtil.findAll(em, Projects.class).get(0);
        }
        em.persist(project);
        em.flush();
        zEmployeeProjects.setProject(project);
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);
        Long projectId = project.getId();

        // Get all the zEmployeeProjectsList where project equals to projectId
        defaultZEmployeeProjectsShouldBeFound("projectId.equals=" + projectId);

        // Get all the zEmployeeProjectsList where project equals to (projectId + 1)
        defaultZEmployeeProjectsShouldNotBeFound("projectId.equals=" + (projectId + 1));
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByEmployeeprojectIsEqualToSomething() throws Exception {
        EmployeeProjects employeeproject;
        if (TestUtil.findAll(em, EmployeeProjects.class).isEmpty()) {
            zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);
            employeeproject = EmployeeProjectsResourceIT.createEntity(em);
        } else {
            employeeproject = TestUtil.findAll(em, EmployeeProjects.class).get(0);
        }
        em.persist(employeeproject);
        em.flush();
        zEmployeeProjects.setEmployeeproject(employeeproject);
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);
        Long employeeprojectId = employeeproject.getId();

        // Get all the zEmployeeProjectsList where employeeproject equals to employeeprojectId
        defaultZEmployeeProjectsShouldBeFound("employeeprojectId.equals=" + employeeprojectId);

        // Get all the zEmployeeProjectsList where employeeproject equals to (employeeprojectId + 1)
        defaultZEmployeeProjectsShouldNotBeFound("employeeprojectId.equals=" + (employeeprojectId + 1));
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByAssignorIsEqualToSomething() throws Exception {
        Employees assignor;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);
            assignor = EmployeesResourceIT.createEntity(em);
        } else {
            assignor = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(assignor);
        em.flush();
        zEmployeeProjects.setAssignor(assignor);
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);
        Long assignorId = assignor.getId();

        // Get all the zEmployeeProjectsList where assignor equals to assignorId
        defaultZEmployeeProjectsShouldBeFound("assignorId.equals=" + assignorId);

        // Get all the zEmployeeProjectsList where assignor equals to (assignorId + 1)
        defaultZEmployeeProjectsShouldNotBeFound("assignorId.equals=" + (assignorId + 1));
    }

    @Test
    @Transactional
    void getAllZEmployeeProjectsByProjectmanagerIsEqualToSomething() throws Exception {
        Employees projectmanager;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);
            projectmanager = EmployeesResourceIT.createEntity(em);
        } else {
            projectmanager = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(projectmanager);
        em.flush();
        zEmployeeProjects.setProjectmanager(projectmanager);
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);
        Long projectmanagerId = projectmanager.getId();

        // Get all the zEmployeeProjectsList where projectmanager equals to projectmanagerId
        defaultZEmployeeProjectsShouldBeFound("projectmanagerId.equals=" + projectmanagerId);

        // Get all the zEmployeeProjectsList where projectmanager equals to (projectmanagerId + 1)
        defaultZEmployeeProjectsShouldNotBeFound("projectmanagerId.equals=" + (projectmanagerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultZEmployeeProjectsShouldBeFound(String filter) throws Exception {
        restZEmployeeProjectsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zEmployeeProjects.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].startdate").value(hasItem(DEFAULT_STARTDATE.toString())))
            .andExpect(jsonPath("$.[*].enddate").value(hasItem(DEFAULT_ENDDATE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].allocation").value(hasItem(DEFAULT_ALLOCATION.booleanValue())))
            .andExpect(jsonPath("$.[*].billed").value(hasItem(DEFAULT_BILLED)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].deliverymanagerid").value(hasItem(DEFAULT_DELIVERYMANAGERID)))
            .andExpect(jsonPath("$.[*].architectid").value(hasItem(DEFAULT_ARCHITECTID)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].previousenddate").value(hasItem(DEFAULT_PREVIOUSENDDATE.toString())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA)))
            .andExpect(jsonPath("$.[*].extendedenddate").value(hasItem(DEFAULT_EXTENDEDENDDATE.toString())))
            .andExpect(jsonPath("$.[*].probability").value(hasItem(DEFAULT_PROBABILITY)));

        // Check, that the count call also returns 1
        restZEmployeeProjectsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultZEmployeeProjectsShouldNotBeFound(String filter) throws Exception {
        restZEmployeeProjectsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restZEmployeeProjectsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingZEmployeeProjects() throws Exception {
        // Get the zEmployeeProjects
        restZEmployeeProjectsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingZEmployeeProjects() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        int databaseSizeBeforeUpdate = zEmployeeProjectsRepository.findAll().size();

        // Update the zEmployeeProjects
        ZEmployeeProjects updatedZEmployeeProjects = zEmployeeProjectsRepository.findById(zEmployeeProjects.getId()).get();
        // Disconnect from session so that the updates on updatedZEmployeeProjects are not directly saved in db
        em.detach(updatedZEmployeeProjects);
        updatedZEmployeeProjects
            .status(UPDATED_STATUS)
            .type(UPDATED_TYPE)
            .startdate(UPDATED_STARTDATE)
            .enddate(UPDATED_ENDDATE)
            .name(UPDATED_NAME)
            .allocation(UPDATED_ALLOCATION)
            .billed(UPDATED_BILLED)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .deliverymanagerid(UPDATED_DELIVERYMANAGERID)
            .architectid(UPDATED_ARCHITECTID)
            .notes(UPDATED_NOTES)
            .previousenddate(UPDATED_PREVIOUSENDDATE)
            .data(UPDATED_DATA)
            .extendedenddate(UPDATED_EXTENDEDENDDATE)
            .probability(UPDATED_PROBABILITY);

        restZEmployeeProjectsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedZEmployeeProjects.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedZEmployeeProjects))
            )
            .andExpect(status().isOk());

        // Validate the ZEmployeeProjects in the database
        List<ZEmployeeProjects> zEmployeeProjectsList = zEmployeeProjectsRepository.findAll();
        assertThat(zEmployeeProjectsList).hasSize(databaseSizeBeforeUpdate);
        ZEmployeeProjects testZEmployeeProjects = zEmployeeProjectsList.get(zEmployeeProjectsList.size() - 1);
        assertThat(testZEmployeeProjects.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testZEmployeeProjects.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testZEmployeeProjects.getStartdate()).isEqualTo(UPDATED_STARTDATE);
        assertThat(testZEmployeeProjects.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testZEmployeeProjects.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testZEmployeeProjects.getAllocation()).isEqualTo(UPDATED_ALLOCATION);
        assertThat(testZEmployeeProjects.getBilled()).isEqualTo(UPDATED_BILLED);
        assertThat(testZEmployeeProjects.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testZEmployeeProjects.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testZEmployeeProjects.getDeliverymanagerid()).isEqualTo(UPDATED_DELIVERYMANAGERID);
        assertThat(testZEmployeeProjects.getArchitectid()).isEqualTo(UPDATED_ARCHITECTID);
        assertThat(testZEmployeeProjects.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testZEmployeeProjects.getPreviousenddate()).isEqualTo(UPDATED_PREVIOUSENDDATE);
        assertThat(testZEmployeeProjects.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testZEmployeeProjects.getExtendedenddate()).isEqualTo(UPDATED_EXTENDEDENDDATE);
        assertThat(testZEmployeeProjects.getProbability()).isEqualTo(UPDATED_PROBABILITY);
    }

    @Test
    @Transactional
    void putNonExistingZEmployeeProjects() throws Exception {
        int databaseSizeBeforeUpdate = zEmployeeProjectsRepository.findAll().size();
        zEmployeeProjects.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZEmployeeProjectsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, zEmployeeProjects.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(zEmployeeProjects))
            )
            .andExpect(status().isBadRequest());

        // Validate the ZEmployeeProjects in the database
        List<ZEmployeeProjects> zEmployeeProjectsList = zEmployeeProjectsRepository.findAll();
        assertThat(zEmployeeProjectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchZEmployeeProjects() throws Exception {
        int databaseSizeBeforeUpdate = zEmployeeProjectsRepository.findAll().size();
        zEmployeeProjects.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZEmployeeProjectsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(zEmployeeProjects))
            )
            .andExpect(status().isBadRequest());

        // Validate the ZEmployeeProjects in the database
        List<ZEmployeeProjects> zEmployeeProjectsList = zEmployeeProjectsRepository.findAll();
        assertThat(zEmployeeProjectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamZEmployeeProjects() throws Exception {
        int databaseSizeBeforeUpdate = zEmployeeProjectsRepository.findAll().size();
        zEmployeeProjects.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZEmployeeProjectsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(zEmployeeProjects))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ZEmployeeProjects in the database
        List<ZEmployeeProjects> zEmployeeProjectsList = zEmployeeProjectsRepository.findAll();
        assertThat(zEmployeeProjectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateZEmployeeProjectsWithPatch() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        int databaseSizeBeforeUpdate = zEmployeeProjectsRepository.findAll().size();

        // Update the zEmployeeProjects using partial update
        ZEmployeeProjects partialUpdatedZEmployeeProjects = new ZEmployeeProjects();
        partialUpdatedZEmployeeProjects.setId(zEmployeeProjects.getId());

        partialUpdatedZEmployeeProjects
            .status(UPDATED_STATUS)
            .enddate(UPDATED_ENDDATE)
            .name(UPDATED_NAME)
            .billed(UPDATED_BILLED)
            .updatedat(UPDATED_UPDATEDAT)
            .deliverymanagerid(UPDATED_DELIVERYMANAGERID)
            .architectid(UPDATED_ARCHITECTID)
            .notes(UPDATED_NOTES)
            .probability(UPDATED_PROBABILITY);

        restZEmployeeProjectsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedZEmployeeProjects.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedZEmployeeProjects))
            )
            .andExpect(status().isOk());

        // Validate the ZEmployeeProjects in the database
        List<ZEmployeeProjects> zEmployeeProjectsList = zEmployeeProjectsRepository.findAll();
        assertThat(zEmployeeProjectsList).hasSize(databaseSizeBeforeUpdate);
        ZEmployeeProjects testZEmployeeProjects = zEmployeeProjectsList.get(zEmployeeProjectsList.size() - 1);
        assertThat(testZEmployeeProjects.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testZEmployeeProjects.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testZEmployeeProjects.getStartdate()).isEqualTo(DEFAULT_STARTDATE);
        assertThat(testZEmployeeProjects.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testZEmployeeProjects.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testZEmployeeProjects.getAllocation()).isEqualTo(DEFAULT_ALLOCATION);
        assertThat(testZEmployeeProjects.getBilled()).isEqualTo(UPDATED_BILLED);
        assertThat(testZEmployeeProjects.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testZEmployeeProjects.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testZEmployeeProjects.getDeliverymanagerid()).isEqualTo(UPDATED_DELIVERYMANAGERID);
        assertThat(testZEmployeeProjects.getArchitectid()).isEqualTo(UPDATED_ARCHITECTID);
        assertThat(testZEmployeeProjects.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testZEmployeeProjects.getPreviousenddate()).isEqualTo(DEFAULT_PREVIOUSENDDATE);
        assertThat(testZEmployeeProjects.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testZEmployeeProjects.getExtendedenddate()).isEqualTo(DEFAULT_EXTENDEDENDDATE);
        assertThat(testZEmployeeProjects.getProbability()).isEqualTo(UPDATED_PROBABILITY);
    }

    @Test
    @Transactional
    void fullUpdateZEmployeeProjectsWithPatch() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        int databaseSizeBeforeUpdate = zEmployeeProjectsRepository.findAll().size();

        // Update the zEmployeeProjects using partial update
        ZEmployeeProjects partialUpdatedZEmployeeProjects = new ZEmployeeProjects();
        partialUpdatedZEmployeeProjects.setId(zEmployeeProjects.getId());

        partialUpdatedZEmployeeProjects
            .status(UPDATED_STATUS)
            .type(UPDATED_TYPE)
            .startdate(UPDATED_STARTDATE)
            .enddate(UPDATED_ENDDATE)
            .name(UPDATED_NAME)
            .allocation(UPDATED_ALLOCATION)
            .billed(UPDATED_BILLED)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .deliverymanagerid(UPDATED_DELIVERYMANAGERID)
            .architectid(UPDATED_ARCHITECTID)
            .notes(UPDATED_NOTES)
            .previousenddate(UPDATED_PREVIOUSENDDATE)
            .data(UPDATED_DATA)
            .extendedenddate(UPDATED_EXTENDEDENDDATE)
            .probability(UPDATED_PROBABILITY);

        restZEmployeeProjectsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedZEmployeeProjects.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedZEmployeeProjects))
            )
            .andExpect(status().isOk());

        // Validate the ZEmployeeProjects in the database
        List<ZEmployeeProjects> zEmployeeProjectsList = zEmployeeProjectsRepository.findAll();
        assertThat(zEmployeeProjectsList).hasSize(databaseSizeBeforeUpdate);
        ZEmployeeProjects testZEmployeeProjects = zEmployeeProjectsList.get(zEmployeeProjectsList.size() - 1);
        assertThat(testZEmployeeProjects.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testZEmployeeProjects.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testZEmployeeProjects.getStartdate()).isEqualTo(UPDATED_STARTDATE);
        assertThat(testZEmployeeProjects.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testZEmployeeProjects.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testZEmployeeProjects.getAllocation()).isEqualTo(UPDATED_ALLOCATION);
        assertThat(testZEmployeeProjects.getBilled()).isEqualTo(UPDATED_BILLED);
        assertThat(testZEmployeeProjects.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testZEmployeeProjects.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testZEmployeeProjects.getDeliverymanagerid()).isEqualTo(UPDATED_DELIVERYMANAGERID);
        assertThat(testZEmployeeProjects.getArchitectid()).isEqualTo(UPDATED_ARCHITECTID);
        assertThat(testZEmployeeProjects.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testZEmployeeProjects.getPreviousenddate()).isEqualTo(UPDATED_PREVIOUSENDDATE);
        assertThat(testZEmployeeProjects.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testZEmployeeProjects.getExtendedenddate()).isEqualTo(UPDATED_EXTENDEDENDDATE);
        assertThat(testZEmployeeProjects.getProbability()).isEqualTo(UPDATED_PROBABILITY);
    }

    @Test
    @Transactional
    void patchNonExistingZEmployeeProjects() throws Exception {
        int databaseSizeBeforeUpdate = zEmployeeProjectsRepository.findAll().size();
        zEmployeeProjects.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZEmployeeProjectsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, zEmployeeProjects.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(zEmployeeProjects))
            )
            .andExpect(status().isBadRequest());

        // Validate the ZEmployeeProjects in the database
        List<ZEmployeeProjects> zEmployeeProjectsList = zEmployeeProjectsRepository.findAll();
        assertThat(zEmployeeProjectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchZEmployeeProjects() throws Exception {
        int databaseSizeBeforeUpdate = zEmployeeProjectsRepository.findAll().size();
        zEmployeeProjects.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZEmployeeProjectsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(zEmployeeProjects))
            )
            .andExpect(status().isBadRequest());

        // Validate the ZEmployeeProjects in the database
        List<ZEmployeeProjects> zEmployeeProjectsList = zEmployeeProjectsRepository.findAll();
        assertThat(zEmployeeProjectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamZEmployeeProjects() throws Exception {
        int databaseSizeBeforeUpdate = zEmployeeProjectsRepository.findAll().size();
        zEmployeeProjects.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZEmployeeProjectsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(zEmployeeProjects))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ZEmployeeProjects in the database
        List<ZEmployeeProjects> zEmployeeProjectsList = zEmployeeProjectsRepository.findAll();
        assertThat(zEmployeeProjectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteZEmployeeProjects() throws Exception {
        // Initialize the database
        zEmployeeProjectsRepository.saveAndFlush(zEmployeeProjects);

        int databaseSizeBeforeDelete = zEmployeeProjectsRepository.findAll().size();

        // Delete the zEmployeeProjects
        restZEmployeeProjectsMockMvc
            .perform(delete(ENTITY_API_URL_ID, zEmployeeProjects.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ZEmployeeProjects> zEmployeeProjectsList = zEmployeeProjectsRepository.findAll();
        assertThat(zEmployeeProjectsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
