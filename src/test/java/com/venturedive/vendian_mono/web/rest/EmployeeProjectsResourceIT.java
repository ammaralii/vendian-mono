package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.EmployeeProjectRoles;
import com.venturedive.vendian_mono.domain.EmployeeProjects;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.Projects;
import com.venturedive.vendian_mono.domain.ZEmployeeProjects;
import com.venturedive.vendian_mono.repository.EmployeeProjectsRepository;
import com.venturedive.vendian_mono.service.criteria.EmployeeProjectsCriteria;
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
 * Integration tests for the {@link EmployeeProjectsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeeProjectsResourceIT {

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Instant DEFAULT_STARTDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STARTDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ENDDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ENDDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ALLOCATION = false;
    private static final Boolean UPDATED_ALLOCATION = true;

    private static final String DEFAULT_BILLED = "AAAAAAAAAA";
    private static final String UPDATED_BILLED = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_ROLEID = 1;
    private static final Integer UPDATED_ROLEID = 2;
    private static final Integer SMALLER_ROLEID = 1 - 1;

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final Instant DEFAULT_EXTENDEDENDDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXTENDEDENDDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PROBABILITY = "AAAAAAAAAA";
    private static final String UPDATED_PROBABILITY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/employee-projects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeeProjectsRepository employeeProjectsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeProjectsMockMvc;

    private EmployeeProjects employeeProjects;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeProjects createEntity(EntityManager em) {
        EmployeeProjects employeeProjects = new EmployeeProjects()
            .status(DEFAULT_STATUS)
            .type(DEFAULT_TYPE)
            .startdate(DEFAULT_STARTDATE)
            .enddate(DEFAULT_ENDDATE)
            .allocation(DEFAULT_ALLOCATION)
            .billed(DEFAULT_BILLED)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT)
            .roleid(DEFAULT_ROLEID)
            .notes(DEFAULT_NOTES)
            .extendedenddate(DEFAULT_EXTENDEDENDDATE)
            .probability(DEFAULT_PROBABILITY);
        return employeeProjects;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeProjects createUpdatedEntity(EntityManager em) {
        EmployeeProjects employeeProjects = new EmployeeProjects()
            .status(UPDATED_STATUS)
            .type(UPDATED_TYPE)
            .startdate(UPDATED_STARTDATE)
            .enddate(UPDATED_ENDDATE)
            .allocation(UPDATED_ALLOCATION)
            .billed(UPDATED_BILLED)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .roleid(UPDATED_ROLEID)
            .notes(UPDATED_NOTES)
            .extendedenddate(UPDATED_EXTENDEDENDDATE)
            .probability(UPDATED_PROBABILITY);
        return employeeProjects;
    }

    @BeforeEach
    public void initTest() {
        employeeProjects = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployeeProjects() throws Exception {
        int databaseSizeBeforeCreate = employeeProjectsRepository.findAll().size();
        // Create the EmployeeProjects
        restEmployeeProjectsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeProjects))
            )
            .andExpect(status().isCreated());

        // Validate the EmployeeProjects in the database
        List<EmployeeProjects> employeeProjectsList = employeeProjectsRepository.findAll();
        assertThat(employeeProjectsList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeProjects testEmployeeProjects = employeeProjectsList.get(employeeProjectsList.size() - 1);
        assertThat(testEmployeeProjects.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testEmployeeProjects.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testEmployeeProjects.getStartdate()).isEqualTo(DEFAULT_STARTDATE);
        assertThat(testEmployeeProjects.getEnddate()).isEqualTo(DEFAULT_ENDDATE);
        assertThat(testEmployeeProjects.getAllocation()).isEqualTo(DEFAULT_ALLOCATION);
        assertThat(testEmployeeProjects.getBilled()).isEqualTo(DEFAULT_BILLED);
        assertThat(testEmployeeProjects.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testEmployeeProjects.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testEmployeeProjects.getRoleid()).isEqualTo(DEFAULT_ROLEID);
        assertThat(testEmployeeProjects.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testEmployeeProjects.getExtendedenddate()).isEqualTo(DEFAULT_EXTENDEDENDDATE);
        assertThat(testEmployeeProjects.getProbability()).isEqualTo(DEFAULT_PROBABILITY);
    }

    @Test
    @Transactional
    void createEmployeeProjectsWithExistingId() throws Exception {
        // Create the EmployeeProjects with an existing ID
        employeeProjects.setId(1L);

        int databaseSizeBeforeCreate = employeeProjectsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeProjectsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeProjects))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeProjects in the database
        List<EmployeeProjects> employeeProjectsList = employeeProjectsRepository.findAll();
        assertThat(employeeProjectsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeProjectsRepository.findAll().size();
        // set the field null
        employeeProjects.setCreatedat(null);

        // Create the EmployeeProjects, which fails.

        restEmployeeProjectsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeProjects))
            )
            .andExpect(status().isBadRequest());

        List<EmployeeProjects> employeeProjectsList = employeeProjectsRepository.findAll();
        assertThat(employeeProjectsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeProjectsRepository.findAll().size();
        // set the field null
        employeeProjects.setUpdatedat(null);

        // Create the EmployeeProjects, which fails.

        restEmployeeProjectsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeProjects))
            )
            .andExpect(status().isBadRequest());

        List<EmployeeProjects> employeeProjectsList = employeeProjectsRepository.findAll();
        assertThat(employeeProjectsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmployeeProjects() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList
        restEmployeeProjectsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeProjects.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].startdate").value(hasItem(DEFAULT_STARTDATE.toString())))
            .andExpect(jsonPath("$.[*].enddate").value(hasItem(DEFAULT_ENDDATE.toString())))
            .andExpect(jsonPath("$.[*].allocation").value(hasItem(DEFAULT_ALLOCATION.booleanValue())))
            .andExpect(jsonPath("$.[*].billed").value(hasItem(DEFAULT_BILLED)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].roleid").value(hasItem(DEFAULT_ROLEID)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].extendedenddate").value(hasItem(DEFAULT_EXTENDEDENDDATE.toString())))
            .andExpect(jsonPath("$.[*].probability").value(hasItem(DEFAULT_PROBABILITY)));
    }

    @Test
    @Transactional
    void getEmployeeProjects() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get the employeeProjects
        restEmployeeProjectsMockMvc
            .perform(get(ENTITY_API_URL_ID, employeeProjects.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeProjects.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.startdate").value(DEFAULT_STARTDATE.toString()))
            .andExpect(jsonPath("$.enddate").value(DEFAULT_ENDDATE.toString()))
            .andExpect(jsonPath("$.allocation").value(DEFAULT_ALLOCATION.booleanValue()))
            .andExpect(jsonPath("$.billed").value(DEFAULT_BILLED))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()))
            .andExpect(jsonPath("$.roleid").value(DEFAULT_ROLEID))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.extendedenddate").value(DEFAULT_EXTENDEDENDDATE.toString()))
            .andExpect(jsonPath("$.probability").value(DEFAULT_PROBABILITY));
    }

    @Test
    @Transactional
    void getEmployeeProjectsByIdFiltering() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        Long id = employeeProjects.getId();

        defaultEmployeeProjectsShouldBeFound("id.equals=" + id);
        defaultEmployeeProjectsShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeProjectsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeProjectsShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeProjectsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeProjectsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where status equals to DEFAULT_STATUS
        defaultEmployeeProjectsShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the employeeProjectsList where status equals to UPDATED_STATUS
        defaultEmployeeProjectsShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultEmployeeProjectsShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the employeeProjectsList where status equals to UPDATED_STATUS
        defaultEmployeeProjectsShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where status is not null
        defaultEmployeeProjectsShouldBeFound("status.specified=true");

        // Get all the employeeProjectsList where status is null
        defaultEmployeeProjectsShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where type equals to DEFAULT_TYPE
        defaultEmployeeProjectsShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the employeeProjectsList where type equals to UPDATED_TYPE
        defaultEmployeeProjectsShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultEmployeeProjectsShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the employeeProjectsList where type equals to UPDATED_TYPE
        defaultEmployeeProjectsShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where type is not null
        defaultEmployeeProjectsShouldBeFound("type.specified=true");

        // Get all the employeeProjectsList where type is null
        defaultEmployeeProjectsShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByTypeContainsSomething() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where type contains DEFAULT_TYPE
        defaultEmployeeProjectsShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the employeeProjectsList where type contains UPDATED_TYPE
        defaultEmployeeProjectsShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where type does not contain DEFAULT_TYPE
        defaultEmployeeProjectsShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the employeeProjectsList where type does not contain UPDATED_TYPE
        defaultEmployeeProjectsShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByStartdateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where startdate equals to DEFAULT_STARTDATE
        defaultEmployeeProjectsShouldBeFound("startdate.equals=" + DEFAULT_STARTDATE);

        // Get all the employeeProjectsList where startdate equals to UPDATED_STARTDATE
        defaultEmployeeProjectsShouldNotBeFound("startdate.equals=" + UPDATED_STARTDATE);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByStartdateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where startdate in DEFAULT_STARTDATE or UPDATED_STARTDATE
        defaultEmployeeProjectsShouldBeFound("startdate.in=" + DEFAULT_STARTDATE + "," + UPDATED_STARTDATE);

        // Get all the employeeProjectsList where startdate equals to UPDATED_STARTDATE
        defaultEmployeeProjectsShouldNotBeFound("startdate.in=" + UPDATED_STARTDATE);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByStartdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where startdate is not null
        defaultEmployeeProjectsShouldBeFound("startdate.specified=true");

        // Get all the employeeProjectsList where startdate is null
        defaultEmployeeProjectsShouldNotBeFound("startdate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByEnddateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where enddate equals to DEFAULT_ENDDATE
        defaultEmployeeProjectsShouldBeFound("enddate.equals=" + DEFAULT_ENDDATE);

        // Get all the employeeProjectsList where enddate equals to UPDATED_ENDDATE
        defaultEmployeeProjectsShouldNotBeFound("enddate.equals=" + UPDATED_ENDDATE);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByEnddateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where enddate in DEFAULT_ENDDATE or UPDATED_ENDDATE
        defaultEmployeeProjectsShouldBeFound("enddate.in=" + DEFAULT_ENDDATE + "," + UPDATED_ENDDATE);

        // Get all the employeeProjectsList where enddate equals to UPDATED_ENDDATE
        defaultEmployeeProjectsShouldNotBeFound("enddate.in=" + UPDATED_ENDDATE);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByEnddateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where enddate is not null
        defaultEmployeeProjectsShouldBeFound("enddate.specified=true");

        // Get all the employeeProjectsList where enddate is null
        defaultEmployeeProjectsShouldNotBeFound("enddate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByAllocationIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where allocation equals to DEFAULT_ALLOCATION
        defaultEmployeeProjectsShouldBeFound("allocation.equals=" + DEFAULT_ALLOCATION);

        // Get all the employeeProjectsList where allocation equals to UPDATED_ALLOCATION
        defaultEmployeeProjectsShouldNotBeFound("allocation.equals=" + UPDATED_ALLOCATION);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByAllocationIsInShouldWork() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where allocation in DEFAULT_ALLOCATION or UPDATED_ALLOCATION
        defaultEmployeeProjectsShouldBeFound("allocation.in=" + DEFAULT_ALLOCATION + "," + UPDATED_ALLOCATION);

        // Get all the employeeProjectsList where allocation equals to UPDATED_ALLOCATION
        defaultEmployeeProjectsShouldNotBeFound("allocation.in=" + UPDATED_ALLOCATION);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByAllocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where allocation is not null
        defaultEmployeeProjectsShouldBeFound("allocation.specified=true");

        // Get all the employeeProjectsList where allocation is null
        defaultEmployeeProjectsShouldNotBeFound("allocation.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByBilledIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where billed equals to DEFAULT_BILLED
        defaultEmployeeProjectsShouldBeFound("billed.equals=" + DEFAULT_BILLED);

        // Get all the employeeProjectsList where billed equals to UPDATED_BILLED
        defaultEmployeeProjectsShouldNotBeFound("billed.equals=" + UPDATED_BILLED);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByBilledIsInShouldWork() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where billed in DEFAULT_BILLED or UPDATED_BILLED
        defaultEmployeeProjectsShouldBeFound("billed.in=" + DEFAULT_BILLED + "," + UPDATED_BILLED);

        // Get all the employeeProjectsList where billed equals to UPDATED_BILLED
        defaultEmployeeProjectsShouldNotBeFound("billed.in=" + UPDATED_BILLED);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByBilledIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where billed is not null
        defaultEmployeeProjectsShouldBeFound("billed.specified=true");

        // Get all the employeeProjectsList where billed is null
        defaultEmployeeProjectsShouldNotBeFound("billed.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByBilledContainsSomething() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where billed contains DEFAULT_BILLED
        defaultEmployeeProjectsShouldBeFound("billed.contains=" + DEFAULT_BILLED);

        // Get all the employeeProjectsList where billed contains UPDATED_BILLED
        defaultEmployeeProjectsShouldNotBeFound("billed.contains=" + UPDATED_BILLED);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByBilledNotContainsSomething() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where billed does not contain DEFAULT_BILLED
        defaultEmployeeProjectsShouldNotBeFound("billed.doesNotContain=" + DEFAULT_BILLED);

        // Get all the employeeProjectsList where billed does not contain UPDATED_BILLED
        defaultEmployeeProjectsShouldBeFound("billed.doesNotContain=" + UPDATED_BILLED);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where createdat equals to DEFAULT_CREATEDAT
        defaultEmployeeProjectsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the employeeProjectsList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeProjectsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultEmployeeProjectsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the employeeProjectsList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeProjectsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where createdat is not null
        defaultEmployeeProjectsShouldBeFound("createdat.specified=true");

        // Get all the employeeProjectsList where createdat is null
        defaultEmployeeProjectsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultEmployeeProjectsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the employeeProjectsList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeProjectsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultEmployeeProjectsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the employeeProjectsList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeProjectsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where updatedat is not null
        defaultEmployeeProjectsShouldBeFound("updatedat.specified=true");

        // Get all the employeeProjectsList where updatedat is null
        defaultEmployeeProjectsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByRoleidIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where roleid equals to DEFAULT_ROLEID
        defaultEmployeeProjectsShouldBeFound("roleid.equals=" + DEFAULT_ROLEID);

        // Get all the employeeProjectsList where roleid equals to UPDATED_ROLEID
        defaultEmployeeProjectsShouldNotBeFound("roleid.equals=" + UPDATED_ROLEID);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByRoleidIsInShouldWork() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where roleid in DEFAULT_ROLEID or UPDATED_ROLEID
        defaultEmployeeProjectsShouldBeFound("roleid.in=" + DEFAULT_ROLEID + "," + UPDATED_ROLEID);

        // Get all the employeeProjectsList where roleid equals to UPDATED_ROLEID
        defaultEmployeeProjectsShouldNotBeFound("roleid.in=" + UPDATED_ROLEID);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByRoleidIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where roleid is not null
        defaultEmployeeProjectsShouldBeFound("roleid.specified=true");

        // Get all the employeeProjectsList where roleid is null
        defaultEmployeeProjectsShouldNotBeFound("roleid.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByRoleidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where roleid is greater than or equal to DEFAULT_ROLEID
        defaultEmployeeProjectsShouldBeFound("roleid.greaterThanOrEqual=" + DEFAULT_ROLEID);

        // Get all the employeeProjectsList where roleid is greater than or equal to UPDATED_ROLEID
        defaultEmployeeProjectsShouldNotBeFound("roleid.greaterThanOrEqual=" + UPDATED_ROLEID);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByRoleidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where roleid is less than or equal to DEFAULT_ROLEID
        defaultEmployeeProjectsShouldBeFound("roleid.lessThanOrEqual=" + DEFAULT_ROLEID);

        // Get all the employeeProjectsList where roleid is less than or equal to SMALLER_ROLEID
        defaultEmployeeProjectsShouldNotBeFound("roleid.lessThanOrEqual=" + SMALLER_ROLEID);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByRoleidIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where roleid is less than DEFAULT_ROLEID
        defaultEmployeeProjectsShouldNotBeFound("roleid.lessThan=" + DEFAULT_ROLEID);

        // Get all the employeeProjectsList where roleid is less than UPDATED_ROLEID
        defaultEmployeeProjectsShouldBeFound("roleid.lessThan=" + UPDATED_ROLEID);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByRoleidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where roleid is greater than DEFAULT_ROLEID
        defaultEmployeeProjectsShouldNotBeFound("roleid.greaterThan=" + DEFAULT_ROLEID);

        // Get all the employeeProjectsList where roleid is greater than SMALLER_ROLEID
        defaultEmployeeProjectsShouldBeFound("roleid.greaterThan=" + SMALLER_ROLEID);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByNotesIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where notes equals to DEFAULT_NOTES
        defaultEmployeeProjectsShouldBeFound("notes.equals=" + DEFAULT_NOTES);

        // Get all the employeeProjectsList where notes equals to UPDATED_NOTES
        defaultEmployeeProjectsShouldNotBeFound("notes.equals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByNotesIsInShouldWork() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where notes in DEFAULT_NOTES or UPDATED_NOTES
        defaultEmployeeProjectsShouldBeFound("notes.in=" + DEFAULT_NOTES + "," + UPDATED_NOTES);

        // Get all the employeeProjectsList where notes equals to UPDATED_NOTES
        defaultEmployeeProjectsShouldNotBeFound("notes.in=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByNotesIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where notes is not null
        defaultEmployeeProjectsShouldBeFound("notes.specified=true");

        // Get all the employeeProjectsList where notes is null
        defaultEmployeeProjectsShouldNotBeFound("notes.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByNotesContainsSomething() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where notes contains DEFAULT_NOTES
        defaultEmployeeProjectsShouldBeFound("notes.contains=" + DEFAULT_NOTES);

        // Get all the employeeProjectsList where notes contains UPDATED_NOTES
        defaultEmployeeProjectsShouldNotBeFound("notes.contains=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByNotesNotContainsSomething() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where notes does not contain DEFAULT_NOTES
        defaultEmployeeProjectsShouldNotBeFound("notes.doesNotContain=" + DEFAULT_NOTES);

        // Get all the employeeProjectsList where notes does not contain UPDATED_NOTES
        defaultEmployeeProjectsShouldBeFound("notes.doesNotContain=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByExtendedenddateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where extendedenddate equals to DEFAULT_EXTENDEDENDDATE
        defaultEmployeeProjectsShouldBeFound("extendedenddate.equals=" + DEFAULT_EXTENDEDENDDATE);

        // Get all the employeeProjectsList where extendedenddate equals to UPDATED_EXTENDEDENDDATE
        defaultEmployeeProjectsShouldNotBeFound("extendedenddate.equals=" + UPDATED_EXTENDEDENDDATE);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByExtendedenddateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where extendedenddate in DEFAULT_EXTENDEDENDDATE or UPDATED_EXTENDEDENDDATE
        defaultEmployeeProjectsShouldBeFound("extendedenddate.in=" + DEFAULT_EXTENDEDENDDATE + "," + UPDATED_EXTENDEDENDDATE);

        // Get all the employeeProjectsList where extendedenddate equals to UPDATED_EXTENDEDENDDATE
        defaultEmployeeProjectsShouldNotBeFound("extendedenddate.in=" + UPDATED_EXTENDEDENDDATE);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByExtendedenddateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where extendedenddate is not null
        defaultEmployeeProjectsShouldBeFound("extendedenddate.specified=true");

        // Get all the employeeProjectsList where extendedenddate is null
        defaultEmployeeProjectsShouldNotBeFound("extendedenddate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByProbabilityIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where probability equals to DEFAULT_PROBABILITY
        defaultEmployeeProjectsShouldBeFound("probability.equals=" + DEFAULT_PROBABILITY);

        // Get all the employeeProjectsList where probability equals to UPDATED_PROBABILITY
        defaultEmployeeProjectsShouldNotBeFound("probability.equals=" + UPDATED_PROBABILITY);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByProbabilityIsInShouldWork() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where probability in DEFAULT_PROBABILITY or UPDATED_PROBABILITY
        defaultEmployeeProjectsShouldBeFound("probability.in=" + DEFAULT_PROBABILITY + "," + UPDATED_PROBABILITY);

        // Get all the employeeProjectsList where probability equals to UPDATED_PROBABILITY
        defaultEmployeeProjectsShouldNotBeFound("probability.in=" + UPDATED_PROBABILITY);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByProbabilityIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where probability is not null
        defaultEmployeeProjectsShouldBeFound("probability.specified=true");

        // Get all the employeeProjectsList where probability is null
        defaultEmployeeProjectsShouldNotBeFound("probability.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByProbabilityContainsSomething() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where probability contains DEFAULT_PROBABILITY
        defaultEmployeeProjectsShouldBeFound("probability.contains=" + DEFAULT_PROBABILITY);

        // Get all the employeeProjectsList where probability contains UPDATED_PROBABILITY
        defaultEmployeeProjectsShouldNotBeFound("probability.contains=" + UPDATED_PROBABILITY);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByProbabilityNotContainsSomething() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        // Get all the employeeProjectsList where probability does not contain DEFAULT_PROBABILITY
        defaultEmployeeProjectsShouldNotBeFound("probability.doesNotContain=" + DEFAULT_PROBABILITY);

        // Get all the employeeProjectsList where probability does not contain UPDATED_PROBABILITY
        defaultEmployeeProjectsShouldBeFound("probability.doesNotContain=" + UPDATED_PROBABILITY);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByEmployeeIsEqualToSomething() throws Exception {
        Employees employee;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employeeProjectsRepository.saveAndFlush(employeeProjects);
            employee = EmployeesResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employee);
        em.flush();
        employeeProjects.setEmployee(employee);
        employeeProjectsRepository.saveAndFlush(employeeProjects);
        Long employeeId = employee.getId();

        // Get all the employeeProjectsList where employee equals to employeeId
        defaultEmployeeProjectsShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the employeeProjectsList where employee equals to (employeeId + 1)
        defaultEmployeeProjectsShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByProjectIsEqualToSomething() throws Exception {
        Projects project;
        if (TestUtil.findAll(em, Projects.class).isEmpty()) {
            employeeProjectsRepository.saveAndFlush(employeeProjects);
            project = ProjectsResourceIT.createEntity(em);
        } else {
            project = TestUtil.findAll(em, Projects.class).get(0);
        }
        em.persist(project);
        em.flush();
        employeeProjects.setProject(project);
        employeeProjectsRepository.saveAndFlush(employeeProjects);
        Long projectId = project.getId();

        // Get all the employeeProjectsList where project equals to projectId
        defaultEmployeeProjectsShouldBeFound("projectId.equals=" + projectId);

        // Get all the employeeProjectsList where project equals to (projectId + 1)
        defaultEmployeeProjectsShouldNotBeFound("projectId.equals=" + (projectId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByAssignorIsEqualToSomething() throws Exception {
        Employees assignor;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employeeProjectsRepository.saveAndFlush(employeeProjects);
            assignor = EmployeesResourceIT.createEntity(em);
        } else {
            assignor = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(assignor);
        em.flush();
        employeeProjects.setAssignor(assignor);
        employeeProjectsRepository.saveAndFlush(employeeProjects);
        Long assignorId = assignor.getId();

        // Get all the employeeProjectsList where assignor equals to assignorId
        defaultEmployeeProjectsShouldBeFound("assignorId.equals=" + assignorId);

        // Get all the employeeProjectsList where assignor equals to (assignorId + 1)
        defaultEmployeeProjectsShouldNotBeFound("assignorId.equals=" + (assignorId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByEmployeeprojectrolesEmployeeprojectIsEqualToSomething() throws Exception {
        EmployeeProjectRoles employeeprojectrolesEmployeeproject;
        if (TestUtil.findAll(em, EmployeeProjectRoles.class).isEmpty()) {
            employeeProjectsRepository.saveAndFlush(employeeProjects);
            employeeprojectrolesEmployeeproject = EmployeeProjectRolesResourceIT.createEntity(em);
        } else {
            employeeprojectrolesEmployeeproject = TestUtil.findAll(em, EmployeeProjectRoles.class).get(0);
        }
        em.persist(employeeprojectrolesEmployeeproject);
        em.flush();
        employeeProjects.addEmployeeprojectrolesEmployeeproject(employeeprojectrolesEmployeeproject);
        employeeProjectsRepository.saveAndFlush(employeeProjects);
        Long employeeprojectrolesEmployeeprojectId = employeeprojectrolesEmployeeproject.getId();

        // Get all the employeeProjectsList where employeeprojectrolesEmployeeproject equals to employeeprojectrolesEmployeeprojectId
        defaultEmployeeProjectsShouldBeFound("employeeprojectrolesEmployeeprojectId.equals=" + employeeprojectrolesEmployeeprojectId);

        // Get all the employeeProjectsList where employeeprojectrolesEmployeeproject equals to (employeeprojectrolesEmployeeprojectId + 1)
        defaultEmployeeProjectsShouldNotBeFound(
            "employeeprojectrolesEmployeeprojectId.equals=" + (employeeprojectrolesEmployeeprojectId + 1)
        );
    }

    @Test
    @Transactional
    void getAllEmployeeProjectsByZemployeeprojectsEmployeeprojectIsEqualToSomething() throws Exception {
        ZEmployeeProjects zemployeeprojectsEmployeeproject;
        if (TestUtil.findAll(em, ZEmployeeProjects.class).isEmpty()) {
            employeeProjectsRepository.saveAndFlush(employeeProjects);
            zemployeeprojectsEmployeeproject = ZEmployeeProjectsResourceIT.createEntity(em);
        } else {
            zemployeeprojectsEmployeeproject = TestUtil.findAll(em, ZEmployeeProjects.class).get(0);
        }
        em.persist(zemployeeprojectsEmployeeproject);
        em.flush();
        employeeProjects.addZemployeeprojectsEmployeeproject(zemployeeprojectsEmployeeproject);
        employeeProjectsRepository.saveAndFlush(employeeProjects);
        Long zemployeeprojectsEmployeeprojectId = zemployeeprojectsEmployeeproject.getId();

        // Get all the employeeProjectsList where zemployeeprojectsEmployeeproject equals to zemployeeprojectsEmployeeprojectId
        defaultEmployeeProjectsShouldBeFound("zemployeeprojectsEmployeeprojectId.equals=" + zemployeeprojectsEmployeeprojectId);

        // Get all the employeeProjectsList where zemployeeprojectsEmployeeproject equals to (zemployeeprojectsEmployeeprojectId + 1)
        defaultEmployeeProjectsShouldNotBeFound("zemployeeprojectsEmployeeprojectId.equals=" + (zemployeeprojectsEmployeeprojectId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeProjectsShouldBeFound(String filter) throws Exception {
        restEmployeeProjectsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeProjects.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].startdate").value(hasItem(DEFAULT_STARTDATE.toString())))
            .andExpect(jsonPath("$.[*].enddate").value(hasItem(DEFAULT_ENDDATE.toString())))
            .andExpect(jsonPath("$.[*].allocation").value(hasItem(DEFAULT_ALLOCATION.booleanValue())))
            .andExpect(jsonPath("$.[*].billed").value(hasItem(DEFAULT_BILLED)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].roleid").value(hasItem(DEFAULT_ROLEID)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].extendedenddate").value(hasItem(DEFAULT_EXTENDEDENDDATE.toString())))
            .andExpect(jsonPath("$.[*].probability").value(hasItem(DEFAULT_PROBABILITY)));

        // Check, that the count call also returns 1
        restEmployeeProjectsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeProjectsShouldNotBeFound(String filter) throws Exception {
        restEmployeeProjectsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeProjectsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployeeProjects() throws Exception {
        // Get the employeeProjects
        restEmployeeProjectsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmployeeProjects() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        int databaseSizeBeforeUpdate = employeeProjectsRepository.findAll().size();

        // Update the employeeProjects
        EmployeeProjects updatedEmployeeProjects = employeeProjectsRepository.findById(employeeProjects.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeProjects are not directly saved in db
        em.detach(updatedEmployeeProjects);
        updatedEmployeeProjects
            .status(UPDATED_STATUS)
            .type(UPDATED_TYPE)
            .startdate(UPDATED_STARTDATE)
            .enddate(UPDATED_ENDDATE)
            .allocation(UPDATED_ALLOCATION)
            .billed(UPDATED_BILLED)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .roleid(UPDATED_ROLEID)
            .notes(UPDATED_NOTES)
            .extendedenddate(UPDATED_EXTENDEDENDDATE)
            .probability(UPDATED_PROBABILITY);

        restEmployeeProjectsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmployeeProjects.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeProjects))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeProjects in the database
        List<EmployeeProjects> employeeProjectsList = employeeProjectsRepository.findAll();
        assertThat(employeeProjectsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeProjects testEmployeeProjects = employeeProjectsList.get(employeeProjectsList.size() - 1);
        assertThat(testEmployeeProjects.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEmployeeProjects.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEmployeeProjects.getStartdate()).isEqualTo(UPDATED_STARTDATE);
        assertThat(testEmployeeProjects.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testEmployeeProjects.getAllocation()).isEqualTo(UPDATED_ALLOCATION);
        assertThat(testEmployeeProjects.getBilled()).isEqualTo(UPDATED_BILLED);
        assertThat(testEmployeeProjects.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeProjects.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testEmployeeProjects.getRoleid()).isEqualTo(UPDATED_ROLEID);
        assertThat(testEmployeeProjects.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testEmployeeProjects.getExtendedenddate()).isEqualTo(UPDATED_EXTENDEDENDDATE);
        assertThat(testEmployeeProjects.getProbability()).isEqualTo(UPDATED_PROBABILITY);
    }

    @Test
    @Transactional
    void putNonExistingEmployeeProjects() throws Exception {
        int databaseSizeBeforeUpdate = employeeProjectsRepository.findAll().size();
        employeeProjects.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeProjectsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeProjects.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeProjects))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeProjects in the database
        List<EmployeeProjects> employeeProjectsList = employeeProjectsRepository.findAll();
        assertThat(employeeProjectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployeeProjects() throws Exception {
        int databaseSizeBeforeUpdate = employeeProjectsRepository.findAll().size();
        employeeProjects.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeProjectsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeProjects))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeProjects in the database
        List<EmployeeProjects> employeeProjectsList = employeeProjectsRepository.findAll();
        assertThat(employeeProjectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployeeProjects() throws Exception {
        int databaseSizeBeforeUpdate = employeeProjectsRepository.findAll().size();
        employeeProjects.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeProjectsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeProjects))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeProjects in the database
        List<EmployeeProjects> employeeProjectsList = employeeProjectsRepository.findAll();
        assertThat(employeeProjectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeProjectsWithPatch() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        int databaseSizeBeforeUpdate = employeeProjectsRepository.findAll().size();

        // Update the employeeProjects using partial update
        EmployeeProjects partialUpdatedEmployeeProjects = new EmployeeProjects();
        partialUpdatedEmployeeProjects.setId(employeeProjects.getId());

        partialUpdatedEmployeeProjects
            .startdate(UPDATED_STARTDATE)
            .enddate(UPDATED_ENDDATE)
            .allocation(UPDATED_ALLOCATION)
            .updatedat(UPDATED_UPDATEDAT)
            .roleid(UPDATED_ROLEID)
            .notes(UPDATED_NOTES)
            .extendedenddate(UPDATED_EXTENDEDENDDATE)
            .probability(UPDATED_PROBABILITY);

        restEmployeeProjectsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeProjects.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeProjects))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeProjects in the database
        List<EmployeeProjects> employeeProjectsList = employeeProjectsRepository.findAll();
        assertThat(employeeProjectsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeProjects testEmployeeProjects = employeeProjectsList.get(employeeProjectsList.size() - 1);
        assertThat(testEmployeeProjects.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testEmployeeProjects.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testEmployeeProjects.getStartdate()).isEqualTo(UPDATED_STARTDATE);
        assertThat(testEmployeeProjects.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testEmployeeProjects.getAllocation()).isEqualTo(UPDATED_ALLOCATION);
        assertThat(testEmployeeProjects.getBilled()).isEqualTo(DEFAULT_BILLED);
        assertThat(testEmployeeProjects.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testEmployeeProjects.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testEmployeeProjects.getRoleid()).isEqualTo(UPDATED_ROLEID);
        assertThat(testEmployeeProjects.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testEmployeeProjects.getExtendedenddate()).isEqualTo(UPDATED_EXTENDEDENDDATE);
        assertThat(testEmployeeProjects.getProbability()).isEqualTo(UPDATED_PROBABILITY);
    }

    @Test
    @Transactional
    void fullUpdateEmployeeProjectsWithPatch() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        int databaseSizeBeforeUpdate = employeeProjectsRepository.findAll().size();

        // Update the employeeProjects using partial update
        EmployeeProjects partialUpdatedEmployeeProjects = new EmployeeProjects();
        partialUpdatedEmployeeProjects.setId(employeeProjects.getId());

        partialUpdatedEmployeeProjects
            .status(UPDATED_STATUS)
            .type(UPDATED_TYPE)
            .startdate(UPDATED_STARTDATE)
            .enddate(UPDATED_ENDDATE)
            .allocation(UPDATED_ALLOCATION)
            .billed(UPDATED_BILLED)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .roleid(UPDATED_ROLEID)
            .notes(UPDATED_NOTES)
            .extendedenddate(UPDATED_EXTENDEDENDDATE)
            .probability(UPDATED_PROBABILITY);

        restEmployeeProjectsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeProjects.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeProjects))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeProjects in the database
        List<EmployeeProjects> employeeProjectsList = employeeProjectsRepository.findAll();
        assertThat(employeeProjectsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeProjects testEmployeeProjects = employeeProjectsList.get(employeeProjectsList.size() - 1);
        assertThat(testEmployeeProjects.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEmployeeProjects.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEmployeeProjects.getStartdate()).isEqualTo(UPDATED_STARTDATE);
        assertThat(testEmployeeProjects.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testEmployeeProjects.getAllocation()).isEqualTo(UPDATED_ALLOCATION);
        assertThat(testEmployeeProjects.getBilled()).isEqualTo(UPDATED_BILLED);
        assertThat(testEmployeeProjects.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeProjects.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testEmployeeProjects.getRoleid()).isEqualTo(UPDATED_ROLEID);
        assertThat(testEmployeeProjects.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testEmployeeProjects.getExtendedenddate()).isEqualTo(UPDATED_EXTENDEDENDDATE);
        assertThat(testEmployeeProjects.getProbability()).isEqualTo(UPDATED_PROBABILITY);
    }

    @Test
    @Transactional
    void patchNonExistingEmployeeProjects() throws Exception {
        int databaseSizeBeforeUpdate = employeeProjectsRepository.findAll().size();
        employeeProjects.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeProjectsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employeeProjects.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeProjects))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeProjects in the database
        List<EmployeeProjects> employeeProjectsList = employeeProjectsRepository.findAll();
        assertThat(employeeProjectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployeeProjects() throws Exception {
        int databaseSizeBeforeUpdate = employeeProjectsRepository.findAll().size();
        employeeProjects.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeProjectsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeProjects))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeProjects in the database
        List<EmployeeProjects> employeeProjectsList = employeeProjectsRepository.findAll();
        assertThat(employeeProjectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployeeProjects() throws Exception {
        int databaseSizeBeforeUpdate = employeeProjectsRepository.findAll().size();
        employeeProjects.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeProjectsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeProjects))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeProjects in the database
        List<EmployeeProjects> employeeProjectsList = employeeProjectsRepository.findAll();
        assertThat(employeeProjectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployeeProjects() throws Exception {
        // Initialize the database
        employeeProjectsRepository.saveAndFlush(employeeProjects);

        int databaseSizeBeforeDelete = employeeProjectsRepository.findAll().size();

        // Delete the employeeProjects
        restEmployeeProjectsMockMvc
            .perform(delete(ENTITY_API_URL_ID, employeeProjects.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeProjects> employeeProjectsList = employeeProjectsRepository.findAll();
        assertThat(employeeProjectsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
