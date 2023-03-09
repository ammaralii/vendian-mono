package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Companies;
import com.venturedive.vendian_mono.domain.EmployeeWorks;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.repository.EmployeeWorksRepository;
import com.venturedive.vendian_mono.service.criteria.EmployeeWorksCriteria;
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
 * Integration tests for the {@link EmployeeWorksResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeeWorksResourceIT {

    private static final Instant DEFAULT_STARTDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STARTDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ENDDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ENDDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final String DEFAULT_LEAVINGREASON = "AAAAAAAAAA";
    private static final String UPDATED_LEAVINGREASON = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/employee-works";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeeWorksRepository employeeWorksRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeWorksMockMvc;

    private EmployeeWorks employeeWorks;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeWorks createEntity(EntityManager em) {
        EmployeeWorks employeeWorks = new EmployeeWorks()
            .startdate(DEFAULT_STARTDATE)
            .enddate(DEFAULT_ENDDATE)
            .designation(DEFAULT_DESIGNATION)
            .leavingreason(DEFAULT_LEAVINGREASON)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        return employeeWorks;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeWorks createUpdatedEntity(EntityManager em) {
        EmployeeWorks employeeWorks = new EmployeeWorks()
            .startdate(UPDATED_STARTDATE)
            .enddate(UPDATED_ENDDATE)
            .designation(UPDATED_DESIGNATION)
            .leavingreason(UPDATED_LEAVINGREASON)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        return employeeWorks;
    }

    @BeforeEach
    public void initTest() {
        employeeWorks = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployeeWorks() throws Exception {
        int databaseSizeBeforeCreate = employeeWorksRepository.findAll().size();
        // Create the EmployeeWorks
        restEmployeeWorksMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeWorks))
            )
            .andExpect(status().isCreated());

        // Validate the EmployeeWorks in the database
        List<EmployeeWorks> employeeWorksList = employeeWorksRepository.findAll();
        assertThat(employeeWorksList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeWorks testEmployeeWorks = employeeWorksList.get(employeeWorksList.size() - 1);
        assertThat(testEmployeeWorks.getStartdate()).isEqualTo(DEFAULT_STARTDATE);
        assertThat(testEmployeeWorks.getEnddate()).isEqualTo(DEFAULT_ENDDATE);
        assertThat(testEmployeeWorks.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testEmployeeWorks.getLeavingreason()).isEqualTo(DEFAULT_LEAVINGREASON);
        assertThat(testEmployeeWorks.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testEmployeeWorks.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createEmployeeWorksWithExistingId() throws Exception {
        // Create the EmployeeWorks with an existing ID
        employeeWorks.setId(1L);

        int databaseSizeBeforeCreate = employeeWorksRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeWorksMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeWorks))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeWorks in the database
        List<EmployeeWorks> employeeWorksList = employeeWorksRepository.findAll();
        assertThat(employeeWorksList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeWorksRepository.findAll().size();
        // set the field null
        employeeWorks.setCreatedat(null);

        // Create the EmployeeWorks, which fails.

        restEmployeeWorksMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeWorks))
            )
            .andExpect(status().isBadRequest());

        List<EmployeeWorks> employeeWorksList = employeeWorksRepository.findAll();
        assertThat(employeeWorksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeWorksRepository.findAll().size();
        // set the field null
        employeeWorks.setUpdatedat(null);

        // Create the EmployeeWorks, which fails.

        restEmployeeWorksMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeWorks))
            )
            .andExpect(status().isBadRequest());

        List<EmployeeWorks> employeeWorksList = employeeWorksRepository.findAll();
        assertThat(employeeWorksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmployeeWorks() throws Exception {
        // Initialize the database
        employeeWorksRepository.saveAndFlush(employeeWorks);

        // Get all the employeeWorksList
        restEmployeeWorksMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeWorks.getId().intValue())))
            .andExpect(jsonPath("$.[*].startdate").value(hasItem(DEFAULT_STARTDATE.toString())))
            .andExpect(jsonPath("$.[*].enddate").value(hasItem(DEFAULT_ENDDATE.toString())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].leavingreason").value(hasItem(DEFAULT_LEAVINGREASON)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getEmployeeWorks() throws Exception {
        // Initialize the database
        employeeWorksRepository.saveAndFlush(employeeWorks);

        // Get the employeeWorks
        restEmployeeWorksMockMvc
            .perform(get(ENTITY_API_URL_ID, employeeWorks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeWorks.getId().intValue()))
            .andExpect(jsonPath("$.startdate").value(DEFAULT_STARTDATE.toString()))
            .andExpect(jsonPath("$.enddate").value(DEFAULT_ENDDATE.toString()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.leavingreason").value(DEFAULT_LEAVINGREASON))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getEmployeeWorksByIdFiltering() throws Exception {
        // Initialize the database
        employeeWorksRepository.saveAndFlush(employeeWorks);

        Long id = employeeWorks.getId();

        defaultEmployeeWorksShouldBeFound("id.equals=" + id);
        defaultEmployeeWorksShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeWorksShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeWorksShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeWorksShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeWorksShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployeeWorksByStartdateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeWorksRepository.saveAndFlush(employeeWorks);

        // Get all the employeeWorksList where startdate equals to DEFAULT_STARTDATE
        defaultEmployeeWorksShouldBeFound("startdate.equals=" + DEFAULT_STARTDATE);

        // Get all the employeeWorksList where startdate equals to UPDATED_STARTDATE
        defaultEmployeeWorksShouldNotBeFound("startdate.equals=" + UPDATED_STARTDATE);
    }

    @Test
    @Transactional
    void getAllEmployeeWorksByStartdateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeWorksRepository.saveAndFlush(employeeWorks);

        // Get all the employeeWorksList where startdate in DEFAULT_STARTDATE or UPDATED_STARTDATE
        defaultEmployeeWorksShouldBeFound("startdate.in=" + DEFAULT_STARTDATE + "," + UPDATED_STARTDATE);

        // Get all the employeeWorksList where startdate equals to UPDATED_STARTDATE
        defaultEmployeeWorksShouldNotBeFound("startdate.in=" + UPDATED_STARTDATE);
    }

    @Test
    @Transactional
    void getAllEmployeeWorksByStartdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeWorksRepository.saveAndFlush(employeeWorks);

        // Get all the employeeWorksList where startdate is not null
        defaultEmployeeWorksShouldBeFound("startdate.specified=true");

        // Get all the employeeWorksList where startdate is null
        defaultEmployeeWorksShouldNotBeFound("startdate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeWorksByEnddateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeWorksRepository.saveAndFlush(employeeWorks);

        // Get all the employeeWorksList where enddate equals to DEFAULT_ENDDATE
        defaultEmployeeWorksShouldBeFound("enddate.equals=" + DEFAULT_ENDDATE);

        // Get all the employeeWorksList where enddate equals to UPDATED_ENDDATE
        defaultEmployeeWorksShouldNotBeFound("enddate.equals=" + UPDATED_ENDDATE);
    }

    @Test
    @Transactional
    void getAllEmployeeWorksByEnddateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeWorksRepository.saveAndFlush(employeeWorks);

        // Get all the employeeWorksList where enddate in DEFAULT_ENDDATE or UPDATED_ENDDATE
        defaultEmployeeWorksShouldBeFound("enddate.in=" + DEFAULT_ENDDATE + "," + UPDATED_ENDDATE);

        // Get all the employeeWorksList where enddate equals to UPDATED_ENDDATE
        defaultEmployeeWorksShouldNotBeFound("enddate.in=" + UPDATED_ENDDATE);
    }

    @Test
    @Transactional
    void getAllEmployeeWorksByEnddateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeWorksRepository.saveAndFlush(employeeWorks);

        // Get all the employeeWorksList where enddate is not null
        defaultEmployeeWorksShouldBeFound("enddate.specified=true");

        // Get all the employeeWorksList where enddate is null
        defaultEmployeeWorksShouldNotBeFound("enddate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeWorksByDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeWorksRepository.saveAndFlush(employeeWorks);

        // Get all the employeeWorksList where designation equals to DEFAULT_DESIGNATION
        defaultEmployeeWorksShouldBeFound("designation.equals=" + DEFAULT_DESIGNATION);

        // Get all the employeeWorksList where designation equals to UPDATED_DESIGNATION
        defaultEmployeeWorksShouldNotBeFound("designation.equals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllEmployeeWorksByDesignationIsInShouldWork() throws Exception {
        // Initialize the database
        employeeWorksRepository.saveAndFlush(employeeWorks);

        // Get all the employeeWorksList where designation in DEFAULT_DESIGNATION or UPDATED_DESIGNATION
        defaultEmployeeWorksShouldBeFound("designation.in=" + DEFAULT_DESIGNATION + "," + UPDATED_DESIGNATION);

        // Get all the employeeWorksList where designation equals to UPDATED_DESIGNATION
        defaultEmployeeWorksShouldNotBeFound("designation.in=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllEmployeeWorksByDesignationIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeWorksRepository.saveAndFlush(employeeWorks);

        // Get all the employeeWorksList where designation is not null
        defaultEmployeeWorksShouldBeFound("designation.specified=true");

        // Get all the employeeWorksList where designation is null
        defaultEmployeeWorksShouldNotBeFound("designation.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeWorksByDesignationContainsSomething() throws Exception {
        // Initialize the database
        employeeWorksRepository.saveAndFlush(employeeWorks);

        // Get all the employeeWorksList where designation contains DEFAULT_DESIGNATION
        defaultEmployeeWorksShouldBeFound("designation.contains=" + DEFAULT_DESIGNATION);

        // Get all the employeeWorksList where designation contains UPDATED_DESIGNATION
        defaultEmployeeWorksShouldNotBeFound("designation.contains=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllEmployeeWorksByDesignationNotContainsSomething() throws Exception {
        // Initialize the database
        employeeWorksRepository.saveAndFlush(employeeWorks);

        // Get all the employeeWorksList where designation does not contain DEFAULT_DESIGNATION
        defaultEmployeeWorksShouldNotBeFound("designation.doesNotContain=" + DEFAULT_DESIGNATION);

        // Get all the employeeWorksList where designation does not contain UPDATED_DESIGNATION
        defaultEmployeeWorksShouldBeFound("designation.doesNotContain=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllEmployeeWorksByLeavingreasonIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeWorksRepository.saveAndFlush(employeeWorks);

        // Get all the employeeWorksList where leavingreason equals to DEFAULT_LEAVINGREASON
        defaultEmployeeWorksShouldBeFound("leavingreason.equals=" + DEFAULT_LEAVINGREASON);

        // Get all the employeeWorksList where leavingreason equals to UPDATED_LEAVINGREASON
        defaultEmployeeWorksShouldNotBeFound("leavingreason.equals=" + UPDATED_LEAVINGREASON);
    }

    @Test
    @Transactional
    void getAllEmployeeWorksByLeavingreasonIsInShouldWork() throws Exception {
        // Initialize the database
        employeeWorksRepository.saveAndFlush(employeeWorks);

        // Get all the employeeWorksList where leavingreason in DEFAULT_LEAVINGREASON or UPDATED_LEAVINGREASON
        defaultEmployeeWorksShouldBeFound("leavingreason.in=" + DEFAULT_LEAVINGREASON + "," + UPDATED_LEAVINGREASON);

        // Get all the employeeWorksList where leavingreason equals to UPDATED_LEAVINGREASON
        defaultEmployeeWorksShouldNotBeFound("leavingreason.in=" + UPDATED_LEAVINGREASON);
    }

    @Test
    @Transactional
    void getAllEmployeeWorksByLeavingreasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeWorksRepository.saveAndFlush(employeeWorks);

        // Get all the employeeWorksList where leavingreason is not null
        defaultEmployeeWorksShouldBeFound("leavingreason.specified=true");

        // Get all the employeeWorksList where leavingreason is null
        defaultEmployeeWorksShouldNotBeFound("leavingreason.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeWorksByLeavingreasonContainsSomething() throws Exception {
        // Initialize the database
        employeeWorksRepository.saveAndFlush(employeeWorks);

        // Get all the employeeWorksList where leavingreason contains DEFAULT_LEAVINGREASON
        defaultEmployeeWorksShouldBeFound("leavingreason.contains=" + DEFAULT_LEAVINGREASON);

        // Get all the employeeWorksList where leavingreason contains UPDATED_LEAVINGREASON
        defaultEmployeeWorksShouldNotBeFound("leavingreason.contains=" + UPDATED_LEAVINGREASON);
    }

    @Test
    @Transactional
    void getAllEmployeeWorksByLeavingreasonNotContainsSomething() throws Exception {
        // Initialize the database
        employeeWorksRepository.saveAndFlush(employeeWorks);

        // Get all the employeeWorksList where leavingreason does not contain DEFAULT_LEAVINGREASON
        defaultEmployeeWorksShouldNotBeFound("leavingreason.doesNotContain=" + DEFAULT_LEAVINGREASON);

        // Get all the employeeWorksList where leavingreason does not contain UPDATED_LEAVINGREASON
        defaultEmployeeWorksShouldBeFound("leavingreason.doesNotContain=" + UPDATED_LEAVINGREASON);
    }

    @Test
    @Transactional
    void getAllEmployeeWorksByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeWorksRepository.saveAndFlush(employeeWorks);

        // Get all the employeeWorksList where createdat equals to DEFAULT_CREATEDAT
        defaultEmployeeWorksShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the employeeWorksList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeWorksShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeWorksByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeWorksRepository.saveAndFlush(employeeWorks);

        // Get all the employeeWorksList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultEmployeeWorksShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the employeeWorksList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeWorksShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeWorksByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeWorksRepository.saveAndFlush(employeeWorks);

        // Get all the employeeWorksList where createdat is not null
        defaultEmployeeWorksShouldBeFound("createdat.specified=true");

        // Get all the employeeWorksList where createdat is null
        defaultEmployeeWorksShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeWorksByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeWorksRepository.saveAndFlush(employeeWorks);

        // Get all the employeeWorksList where updatedat equals to DEFAULT_UPDATEDAT
        defaultEmployeeWorksShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the employeeWorksList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeWorksShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeWorksByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeWorksRepository.saveAndFlush(employeeWorks);

        // Get all the employeeWorksList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultEmployeeWorksShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the employeeWorksList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeWorksShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeWorksByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeWorksRepository.saveAndFlush(employeeWorks);

        // Get all the employeeWorksList where updatedat is not null
        defaultEmployeeWorksShouldBeFound("updatedat.specified=true");

        // Get all the employeeWorksList where updatedat is null
        defaultEmployeeWorksShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeWorksByEmployeeIsEqualToSomething() throws Exception {
        Employees employee;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employeeWorksRepository.saveAndFlush(employeeWorks);
            employee = EmployeesResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employee);
        em.flush();
        employeeWorks.setEmployee(employee);
        employeeWorksRepository.saveAndFlush(employeeWorks);
        Long employeeId = employee.getId();

        // Get all the employeeWorksList where employee equals to employeeId
        defaultEmployeeWorksShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the employeeWorksList where employee equals to (employeeId + 1)
        defaultEmployeeWorksShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeeWorksByCompanyIsEqualToSomething() throws Exception {
        Companies company;
        if (TestUtil.findAll(em, Companies.class).isEmpty()) {
            employeeWorksRepository.saveAndFlush(employeeWorks);
            company = CompaniesResourceIT.createEntity(em);
        } else {
            company = TestUtil.findAll(em, Companies.class).get(0);
        }
        em.persist(company);
        em.flush();
        employeeWorks.setCompany(company);
        employeeWorksRepository.saveAndFlush(employeeWorks);
        Long companyId = company.getId();

        // Get all the employeeWorksList where company equals to companyId
        defaultEmployeeWorksShouldBeFound("companyId.equals=" + companyId);

        // Get all the employeeWorksList where company equals to (companyId + 1)
        defaultEmployeeWorksShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeWorksShouldBeFound(String filter) throws Exception {
        restEmployeeWorksMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeWorks.getId().intValue())))
            .andExpect(jsonPath("$.[*].startdate").value(hasItem(DEFAULT_STARTDATE.toString())))
            .andExpect(jsonPath("$.[*].enddate").value(hasItem(DEFAULT_ENDDATE.toString())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].leavingreason").value(hasItem(DEFAULT_LEAVINGREASON)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restEmployeeWorksMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeWorksShouldNotBeFound(String filter) throws Exception {
        restEmployeeWorksMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeWorksMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployeeWorks() throws Exception {
        // Get the employeeWorks
        restEmployeeWorksMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmployeeWorks() throws Exception {
        // Initialize the database
        employeeWorksRepository.saveAndFlush(employeeWorks);

        int databaseSizeBeforeUpdate = employeeWorksRepository.findAll().size();

        // Update the employeeWorks
        EmployeeWorks updatedEmployeeWorks = employeeWorksRepository.findById(employeeWorks.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeWorks are not directly saved in db
        em.detach(updatedEmployeeWorks);
        updatedEmployeeWorks
            .startdate(UPDATED_STARTDATE)
            .enddate(UPDATED_ENDDATE)
            .designation(UPDATED_DESIGNATION)
            .leavingreason(UPDATED_LEAVINGREASON)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restEmployeeWorksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmployeeWorks.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeWorks))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeWorks in the database
        List<EmployeeWorks> employeeWorksList = employeeWorksRepository.findAll();
        assertThat(employeeWorksList).hasSize(databaseSizeBeforeUpdate);
        EmployeeWorks testEmployeeWorks = employeeWorksList.get(employeeWorksList.size() - 1);
        assertThat(testEmployeeWorks.getStartdate()).isEqualTo(UPDATED_STARTDATE);
        assertThat(testEmployeeWorks.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testEmployeeWorks.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testEmployeeWorks.getLeavingreason()).isEqualTo(UPDATED_LEAVINGREASON);
        assertThat(testEmployeeWorks.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeWorks.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingEmployeeWorks() throws Exception {
        int databaseSizeBeforeUpdate = employeeWorksRepository.findAll().size();
        employeeWorks.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeWorksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeWorks.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeWorks))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeWorks in the database
        List<EmployeeWorks> employeeWorksList = employeeWorksRepository.findAll();
        assertThat(employeeWorksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployeeWorks() throws Exception {
        int databaseSizeBeforeUpdate = employeeWorksRepository.findAll().size();
        employeeWorks.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeWorksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeWorks))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeWorks in the database
        List<EmployeeWorks> employeeWorksList = employeeWorksRepository.findAll();
        assertThat(employeeWorksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployeeWorks() throws Exception {
        int databaseSizeBeforeUpdate = employeeWorksRepository.findAll().size();
        employeeWorks.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeWorksMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeWorks))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeWorks in the database
        List<EmployeeWorks> employeeWorksList = employeeWorksRepository.findAll();
        assertThat(employeeWorksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeWorksWithPatch() throws Exception {
        // Initialize the database
        employeeWorksRepository.saveAndFlush(employeeWorks);

        int databaseSizeBeforeUpdate = employeeWorksRepository.findAll().size();

        // Update the employeeWorks using partial update
        EmployeeWorks partialUpdatedEmployeeWorks = new EmployeeWorks();
        partialUpdatedEmployeeWorks.setId(employeeWorks.getId());

        partialUpdatedEmployeeWorks
            .enddate(UPDATED_ENDDATE)
            .designation(UPDATED_DESIGNATION)
            .leavingreason(UPDATED_LEAVINGREASON)
            .createdat(UPDATED_CREATEDAT);

        restEmployeeWorksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeWorks.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeWorks))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeWorks in the database
        List<EmployeeWorks> employeeWorksList = employeeWorksRepository.findAll();
        assertThat(employeeWorksList).hasSize(databaseSizeBeforeUpdate);
        EmployeeWorks testEmployeeWorks = employeeWorksList.get(employeeWorksList.size() - 1);
        assertThat(testEmployeeWorks.getStartdate()).isEqualTo(DEFAULT_STARTDATE);
        assertThat(testEmployeeWorks.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testEmployeeWorks.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testEmployeeWorks.getLeavingreason()).isEqualTo(UPDATED_LEAVINGREASON);
        assertThat(testEmployeeWorks.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeWorks.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateEmployeeWorksWithPatch() throws Exception {
        // Initialize the database
        employeeWorksRepository.saveAndFlush(employeeWorks);

        int databaseSizeBeforeUpdate = employeeWorksRepository.findAll().size();

        // Update the employeeWorks using partial update
        EmployeeWorks partialUpdatedEmployeeWorks = new EmployeeWorks();
        partialUpdatedEmployeeWorks.setId(employeeWorks.getId());

        partialUpdatedEmployeeWorks
            .startdate(UPDATED_STARTDATE)
            .enddate(UPDATED_ENDDATE)
            .designation(UPDATED_DESIGNATION)
            .leavingreason(UPDATED_LEAVINGREASON)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restEmployeeWorksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeWorks.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeWorks))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeWorks in the database
        List<EmployeeWorks> employeeWorksList = employeeWorksRepository.findAll();
        assertThat(employeeWorksList).hasSize(databaseSizeBeforeUpdate);
        EmployeeWorks testEmployeeWorks = employeeWorksList.get(employeeWorksList.size() - 1);
        assertThat(testEmployeeWorks.getStartdate()).isEqualTo(UPDATED_STARTDATE);
        assertThat(testEmployeeWorks.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testEmployeeWorks.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testEmployeeWorks.getLeavingreason()).isEqualTo(UPDATED_LEAVINGREASON);
        assertThat(testEmployeeWorks.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeWorks.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingEmployeeWorks() throws Exception {
        int databaseSizeBeforeUpdate = employeeWorksRepository.findAll().size();
        employeeWorks.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeWorksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employeeWorks.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeWorks))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeWorks in the database
        List<EmployeeWorks> employeeWorksList = employeeWorksRepository.findAll();
        assertThat(employeeWorksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployeeWorks() throws Exception {
        int databaseSizeBeforeUpdate = employeeWorksRepository.findAll().size();
        employeeWorks.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeWorksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeWorks))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeWorks in the database
        List<EmployeeWorks> employeeWorksList = employeeWorksRepository.findAll();
        assertThat(employeeWorksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployeeWorks() throws Exception {
        int databaseSizeBeforeUpdate = employeeWorksRepository.findAll().size();
        employeeWorks.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeWorksMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeWorks))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeWorks in the database
        List<EmployeeWorks> employeeWorksList = employeeWorksRepository.findAll();
        assertThat(employeeWorksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployeeWorks() throws Exception {
        // Initialize the database
        employeeWorksRepository.saveAndFlush(employeeWorks);

        int databaseSizeBeforeDelete = employeeWorksRepository.findAll().size();

        // Delete the employeeWorks
        restEmployeeWorksMockMvc
            .perform(delete(ENTITY_API_URL_ID, employeeWorks.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeWorks> employeeWorksList = employeeWorksRepository.findAll();
        assertThat(employeeWorksList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
