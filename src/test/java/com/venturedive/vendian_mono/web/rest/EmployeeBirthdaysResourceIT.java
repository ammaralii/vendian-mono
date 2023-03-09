package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.EmployeeBirthdays;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.repository.EmployeeBirthdaysRepository;
import com.venturedive.vendian_mono.service.criteria.EmployeeBirthdaysCriteria;
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
 * Integration tests for the {@link EmployeeBirthdaysResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeeBirthdaysResourceIT {

    private static final String DEFAULT_BIRTHDAY_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_BIRTHDAY_DETAILS = "BBBBBBBBBB";

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;
    private static final Integer SMALLER_YEAR = 1 - 1;

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/employee-birthdays";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeeBirthdaysRepository employeeBirthdaysRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeBirthdaysMockMvc;

    private EmployeeBirthdays employeeBirthdays;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeBirthdays createEntity(EntityManager em) {
        EmployeeBirthdays employeeBirthdays = new EmployeeBirthdays()
            .birthdayDetails(DEFAULT_BIRTHDAY_DETAILS)
            .year(DEFAULT_YEAR)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        return employeeBirthdays;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeBirthdays createUpdatedEntity(EntityManager em) {
        EmployeeBirthdays employeeBirthdays = new EmployeeBirthdays()
            .birthdayDetails(UPDATED_BIRTHDAY_DETAILS)
            .year(UPDATED_YEAR)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        return employeeBirthdays;
    }

    @BeforeEach
    public void initTest() {
        employeeBirthdays = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployeeBirthdays() throws Exception {
        int databaseSizeBeforeCreate = employeeBirthdaysRepository.findAll().size();
        // Create the EmployeeBirthdays
        restEmployeeBirthdaysMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeBirthdays))
            )
            .andExpect(status().isCreated());

        // Validate the EmployeeBirthdays in the database
        List<EmployeeBirthdays> employeeBirthdaysList = employeeBirthdaysRepository.findAll();
        assertThat(employeeBirthdaysList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeBirthdays testEmployeeBirthdays = employeeBirthdaysList.get(employeeBirthdaysList.size() - 1);
        assertThat(testEmployeeBirthdays.getBirthdayDetails()).isEqualTo(DEFAULT_BIRTHDAY_DETAILS);
        assertThat(testEmployeeBirthdays.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testEmployeeBirthdays.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testEmployeeBirthdays.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createEmployeeBirthdaysWithExistingId() throws Exception {
        // Create the EmployeeBirthdays with an existing ID
        employeeBirthdays.setId(1L);

        int databaseSizeBeforeCreate = employeeBirthdaysRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeBirthdaysMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeBirthdays))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeBirthdays in the database
        List<EmployeeBirthdays> employeeBirthdaysList = employeeBirthdaysRepository.findAll();
        assertThat(employeeBirthdaysList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeBirthdaysRepository.findAll().size();
        // set the field null
        employeeBirthdays.setYear(null);

        // Create the EmployeeBirthdays, which fails.

        restEmployeeBirthdaysMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeBirthdays))
            )
            .andExpect(status().isBadRequest());

        List<EmployeeBirthdays> employeeBirthdaysList = employeeBirthdaysRepository.findAll();
        assertThat(employeeBirthdaysList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmployeeBirthdays() throws Exception {
        // Initialize the database
        employeeBirthdaysRepository.saveAndFlush(employeeBirthdays);

        // Get all the employeeBirthdaysList
        restEmployeeBirthdaysMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeBirthdays.getId().intValue())))
            .andExpect(jsonPath("$.[*].birthdayDetails").value(hasItem(DEFAULT_BIRTHDAY_DETAILS)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getEmployeeBirthdays() throws Exception {
        // Initialize the database
        employeeBirthdaysRepository.saveAndFlush(employeeBirthdays);

        // Get the employeeBirthdays
        restEmployeeBirthdaysMockMvc
            .perform(get(ENTITY_API_URL_ID, employeeBirthdays.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeBirthdays.getId().intValue()))
            .andExpect(jsonPath("$.birthdayDetails").value(DEFAULT_BIRTHDAY_DETAILS))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getEmployeeBirthdaysByIdFiltering() throws Exception {
        // Initialize the database
        employeeBirthdaysRepository.saveAndFlush(employeeBirthdays);

        Long id = employeeBirthdays.getId();

        defaultEmployeeBirthdaysShouldBeFound("id.equals=" + id);
        defaultEmployeeBirthdaysShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeBirthdaysShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeBirthdaysShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeBirthdaysShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeBirthdaysShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployeeBirthdaysByBirthdayDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeBirthdaysRepository.saveAndFlush(employeeBirthdays);

        // Get all the employeeBirthdaysList where birthdayDetails equals to DEFAULT_BIRTHDAY_DETAILS
        defaultEmployeeBirthdaysShouldBeFound("birthdayDetails.equals=" + DEFAULT_BIRTHDAY_DETAILS);

        // Get all the employeeBirthdaysList where birthdayDetails equals to UPDATED_BIRTHDAY_DETAILS
        defaultEmployeeBirthdaysShouldNotBeFound("birthdayDetails.equals=" + UPDATED_BIRTHDAY_DETAILS);
    }

    @Test
    @Transactional
    void getAllEmployeeBirthdaysByBirthdayDetailsIsInShouldWork() throws Exception {
        // Initialize the database
        employeeBirthdaysRepository.saveAndFlush(employeeBirthdays);

        // Get all the employeeBirthdaysList where birthdayDetails in DEFAULT_BIRTHDAY_DETAILS or UPDATED_BIRTHDAY_DETAILS
        defaultEmployeeBirthdaysShouldBeFound("birthdayDetails.in=" + DEFAULT_BIRTHDAY_DETAILS + "," + UPDATED_BIRTHDAY_DETAILS);

        // Get all the employeeBirthdaysList where birthdayDetails equals to UPDATED_BIRTHDAY_DETAILS
        defaultEmployeeBirthdaysShouldNotBeFound("birthdayDetails.in=" + UPDATED_BIRTHDAY_DETAILS);
    }

    @Test
    @Transactional
    void getAllEmployeeBirthdaysByBirthdayDetailsIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeBirthdaysRepository.saveAndFlush(employeeBirthdays);

        // Get all the employeeBirthdaysList where birthdayDetails is not null
        defaultEmployeeBirthdaysShouldBeFound("birthdayDetails.specified=true");

        // Get all the employeeBirthdaysList where birthdayDetails is null
        defaultEmployeeBirthdaysShouldNotBeFound("birthdayDetails.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeBirthdaysByBirthdayDetailsContainsSomething() throws Exception {
        // Initialize the database
        employeeBirthdaysRepository.saveAndFlush(employeeBirthdays);

        // Get all the employeeBirthdaysList where birthdayDetails contains DEFAULT_BIRTHDAY_DETAILS
        defaultEmployeeBirthdaysShouldBeFound("birthdayDetails.contains=" + DEFAULT_BIRTHDAY_DETAILS);

        // Get all the employeeBirthdaysList where birthdayDetails contains UPDATED_BIRTHDAY_DETAILS
        defaultEmployeeBirthdaysShouldNotBeFound("birthdayDetails.contains=" + UPDATED_BIRTHDAY_DETAILS);
    }

    @Test
    @Transactional
    void getAllEmployeeBirthdaysByBirthdayDetailsNotContainsSomething() throws Exception {
        // Initialize the database
        employeeBirthdaysRepository.saveAndFlush(employeeBirthdays);

        // Get all the employeeBirthdaysList where birthdayDetails does not contain DEFAULT_BIRTHDAY_DETAILS
        defaultEmployeeBirthdaysShouldNotBeFound("birthdayDetails.doesNotContain=" + DEFAULT_BIRTHDAY_DETAILS);

        // Get all the employeeBirthdaysList where birthdayDetails does not contain UPDATED_BIRTHDAY_DETAILS
        defaultEmployeeBirthdaysShouldBeFound("birthdayDetails.doesNotContain=" + UPDATED_BIRTHDAY_DETAILS);
    }

    @Test
    @Transactional
    void getAllEmployeeBirthdaysByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeBirthdaysRepository.saveAndFlush(employeeBirthdays);

        // Get all the employeeBirthdaysList where year equals to DEFAULT_YEAR
        defaultEmployeeBirthdaysShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the employeeBirthdaysList where year equals to UPDATED_YEAR
        defaultEmployeeBirthdaysShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllEmployeeBirthdaysByYearIsInShouldWork() throws Exception {
        // Initialize the database
        employeeBirthdaysRepository.saveAndFlush(employeeBirthdays);

        // Get all the employeeBirthdaysList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultEmployeeBirthdaysShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the employeeBirthdaysList where year equals to UPDATED_YEAR
        defaultEmployeeBirthdaysShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllEmployeeBirthdaysByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeBirthdaysRepository.saveAndFlush(employeeBirthdays);

        // Get all the employeeBirthdaysList where year is not null
        defaultEmployeeBirthdaysShouldBeFound("year.specified=true");

        // Get all the employeeBirthdaysList where year is null
        defaultEmployeeBirthdaysShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeBirthdaysByYearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeBirthdaysRepository.saveAndFlush(employeeBirthdays);

        // Get all the employeeBirthdaysList where year is greater than or equal to DEFAULT_YEAR
        defaultEmployeeBirthdaysShouldBeFound("year.greaterThanOrEqual=" + DEFAULT_YEAR);

        // Get all the employeeBirthdaysList where year is greater than or equal to UPDATED_YEAR
        defaultEmployeeBirthdaysShouldNotBeFound("year.greaterThanOrEqual=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllEmployeeBirthdaysByYearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeBirthdaysRepository.saveAndFlush(employeeBirthdays);

        // Get all the employeeBirthdaysList where year is less than or equal to DEFAULT_YEAR
        defaultEmployeeBirthdaysShouldBeFound("year.lessThanOrEqual=" + DEFAULT_YEAR);

        // Get all the employeeBirthdaysList where year is less than or equal to SMALLER_YEAR
        defaultEmployeeBirthdaysShouldNotBeFound("year.lessThanOrEqual=" + SMALLER_YEAR);
    }

    @Test
    @Transactional
    void getAllEmployeeBirthdaysByYearIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeBirthdaysRepository.saveAndFlush(employeeBirthdays);

        // Get all the employeeBirthdaysList where year is less than DEFAULT_YEAR
        defaultEmployeeBirthdaysShouldNotBeFound("year.lessThan=" + DEFAULT_YEAR);

        // Get all the employeeBirthdaysList where year is less than UPDATED_YEAR
        defaultEmployeeBirthdaysShouldBeFound("year.lessThan=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllEmployeeBirthdaysByYearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeBirthdaysRepository.saveAndFlush(employeeBirthdays);

        // Get all the employeeBirthdaysList where year is greater than DEFAULT_YEAR
        defaultEmployeeBirthdaysShouldNotBeFound("year.greaterThan=" + DEFAULT_YEAR);

        // Get all the employeeBirthdaysList where year is greater than SMALLER_YEAR
        defaultEmployeeBirthdaysShouldBeFound("year.greaterThan=" + SMALLER_YEAR);
    }

    @Test
    @Transactional
    void getAllEmployeeBirthdaysByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeBirthdaysRepository.saveAndFlush(employeeBirthdays);

        // Get all the employeeBirthdaysList where createdat equals to DEFAULT_CREATEDAT
        defaultEmployeeBirthdaysShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the employeeBirthdaysList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeBirthdaysShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeBirthdaysByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeBirthdaysRepository.saveAndFlush(employeeBirthdays);

        // Get all the employeeBirthdaysList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultEmployeeBirthdaysShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the employeeBirthdaysList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeBirthdaysShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeBirthdaysByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeBirthdaysRepository.saveAndFlush(employeeBirthdays);

        // Get all the employeeBirthdaysList where createdat is not null
        defaultEmployeeBirthdaysShouldBeFound("createdat.specified=true");

        // Get all the employeeBirthdaysList where createdat is null
        defaultEmployeeBirthdaysShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeBirthdaysByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeBirthdaysRepository.saveAndFlush(employeeBirthdays);

        // Get all the employeeBirthdaysList where updatedat equals to DEFAULT_UPDATEDAT
        defaultEmployeeBirthdaysShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the employeeBirthdaysList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeBirthdaysShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeBirthdaysByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeBirthdaysRepository.saveAndFlush(employeeBirthdays);

        // Get all the employeeBirthdaysList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultEmployeeBirthdaysShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the employeeBirthdaysList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeBirthdaysShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeBirthdaysByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeBirthdaysRepository.saveAndFlush(employeeBirthdays);

        // Get all the employeeBirthdaysList where updatedat is not null
        defaultEmployeeBirthdaysShouldBeFound("updatedat.specified=true");

        // Get all the employeeBirthdaysList where updatedat is null
        defaultEmployeeBirthdaysShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeBirthdaysByEmployeeIsEqualToSomething() throws Exception {
        Employees employee;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employeeBirthdaysRepository.saveAndFlush(employeeBirthdays);
            employee = EmployeesResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employee);
        em.flush();
        employeeBirthdays.setEmployee(employee);
        employeeBirthdaysRepository.saveAndFlush(employeeBirthdays);
        Long employeeId = employee.getId();

        // Get all the employeeBirthdaysList where employee equals to employeeId
        defaultEmployeeBirthdaysShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the employeeBirthdaysList where employee equals to (employeeId + 1)
        defaultEmployeeBirthdaysShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeBirthdaysShouldBeFound(String filter) throws Exception {
        restEmployeeBirthdaysMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeBirthdays.getId().intValue())))
            .andExpect(jsonPath("$.[*].birthdayDetails").value(hasItem(DEFAULT_BIRTHDAY_DETAILS)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restEmployeeBirthdaysMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeBirthdaysShouldNotBeFound(String filter) throws Exception {
        restEmployeeBirthdaysMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeBirthdaysMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployeeBirthdays() throws Exception {
        // Get the employeeBirthdays
        restEmployeeBirthdaysMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmployeeBirthdays() throws Exception {
        // Initialize the database
        employeeBirthdaysRepository.saveAndFlush(employeeBirthdays);

        int databaseSizeBeforeUpdate = employeeBirthdaysRepository.findAll().size();

        // Update the employeeBirthdays
        EmployeeBirthdays updatedEmployeeBirthdays = employeeBirthdaysRepository.findById(employeeBirthdays.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeBirthdays are not directly saved in db
        em.detach(updatedEmployeeBirthdays);
        updatedEmployeeBirthdays
            .birthdayDetails(UPDATED_BIRTHDAY_DETAILS)
            .year(UPDATED_YEAR)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restEmployeeBirthdaysMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmployeeBirthdays.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeBirthdays))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeBirthdays in the database
        List<EmployeeBirthdays> employeeBirthdaysList = employeeBirthdaysRepository.findAll();
        assertThat(employeeBirthdaysList).hasSize(databaseSizeBeforeUpdate);
        EmployeeBirthdays testEmployeeBirthdays = employeeBirthdaysList.get(employeeBirthdaysList.size() - 1);
        assertThat(testEmployeeBirthdays.getBirthdayDetails()).isEqualTo(UPDATED_BIRTHDAY_DETAILS);
        assertThat(testEmployeeBirthdays.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testEmployeeBirthdays.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeBirthdays.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingEmployeeBirthdays() throws Exception {
        int databaseSizeBeforeUpdate = employeeBirthdaysRepository.findAll().size();
        employeeBirthdays.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeBirthdaysMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeBirthdays.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeBirthdays))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeBirthdays in the database
        List<EmployeeBirthdays> employeeBirthdaysList = employeeBirthdaysRepository.findAll();
        assertThat(employeeBirthdaysList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployeeBirthdays() throws Exception {
        int databaseSizeBeforeUpdate = employeeBirthdaysRepository.findAll().size();
        employeeBirthdays.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeBirthdaysMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeBirthdays))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeBirthdays in the database
        List<EmployeeBirthdays> employeeBirthdaysList = employeeBirthdaysRepository.findAll();
        assertThat(employeeBirthdaysList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployeeBirthdays() throws Exception {
        int databaseSizeBeforeUpdate = employeeBirthdaysRepository.findAll().size();
        employeeBirthdays.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeBirthdaysMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeBirthdays))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeBirthdays in the database
        List<EmployeeBirthdays> employeeBirthdaysList = employeeBirthdaysRepository.findAll();
        assertThat(employeeBirthdaysList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeBirthdaysWithPatch() throws Exception {
        // Initialize the database
        employeeBirthdaysRepository.saveAndFlush(employeeBirthdays);

        int databaseSizeBeforeUpdate = employeeBirthdaysRepository.findAll().size();

        // Update the employeeBirthdays using partial update
        EmployeeBirthdays partialUpdatedEmployeeBirthdays = new EmployeeBirthdays();
        partialUpdatedEmployeeBirthdays.setId(employeeBirthdays.getId());

        partialUpdatedEmployeeBirthdays.createdat(UPDATED_CREATEDAT);

        restEmployeeBirthdaysMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeBirthdays.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeBirthdays))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeBirthdays in the database
        List<EmployeeBirthdays> employeeBirthdaysList = employeeBirthdaysRepository.findAll();
        assertThat(employeeBirthdaysList).hasSize(databaseSizeBeforeUpdate);
        EmployeeBirthdays testEmployeeBirthdays = employeeBirthdaysList.get(employeeBirthdaysList.size() - 1);
        assertThat(testEmployeeBirthdays.getBirthdayDetails()).isEqualTo(DEFAULT_BIRTHDAY_DETAILS);
        assertThat(testEmployeeBirthdays.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testEmployeeBirthdays.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeBirthdays.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateEmployeeBirthdaysWithPatch() throws Exception {
        // Initialize the database
        employeeBirthdaysRepository.saveAndFlush(employeeBirthdays);

        int databaseSizeBeforeUpdate = employeeBirthdaysRepository.findAll().size();

        // Update the employeeBirthdays using partial update
        EmployeeBirthdays partialUpdatedEmployeeBirthdays = new EmployeeBirthdays();
        partialUpdatedEmployeeBirthdays.setId(employeeBirthdays.getId());

        partialUpdatedEmployeeBirthdays
            .birthdayDetails(UPDATED_BIRTHDAY_DETAILS)
            .year(UPDATED_YEAR)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restEmployeeBirthdaysMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeBirthdays.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeBirthdays))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeBirthdays in the database
        List<EmployeeBirthdays> employeeBirthdaysList = employeeBirthdaysRepository.findAll();
        assertThat(employeeBirthdaysList).hasSize(databaseSizeBeforeUpdate);
        EmployeeBirthdays testEmployeeBirthdays = employeeBirthdaysList.get(employeeBirthdaysList.size() - 1);
        assertThat(testEmployeeBirthdays.getBirthdayDetails()).isEqualTo(UPDATED_BIRTHDAY_DETAILS);
        assertThat(testEmployeeBirthdays.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testEmployeeBirthdays.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeBirthdays.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingEmployeeBirthdays() throws Exception {
        int databaseSizeBeforeUpdate = employeeBirthdaysRepository.findAll().size();
        employeeBirthdays.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeBirthdaysMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employeeBirthdays.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeBirthdays))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeBirthdays in the database
        List<EmployeeBirthdays> employeeBirthdaysList = employeeBirthdaysRepository.findAll();
        assertThat(employeeBirthdaysList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployeeBirthdays() throws Exception {
        int databaseSizeBeforeUpdate = employeeBirthdaysRepository.findAll().size();
        employeeBirthdays.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeBirthdaysMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeBirthdays))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeBirthdays in the database
        List<EmployeeBirthdays> employeeBirthdaysList = employeeBirthdaysRepository.findAll();
        assertThat(employeeBirthdaysList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployeeBirthdays() throws Exception {
        int databaseSizeBeforeUpdate = employeeBirthdaysRepository.findAll().size();
        employeeBirthdays.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeBirthdaysMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeBirthdays))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeBirthdays in the database
        List<EmployeeBirthdays> employeeBirthdaysList = employeeBirthdaysRepository.findAll();
        assertThat(employeeBirthdaysList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployeeBirthdays() throws Exception {
        // Initialize the database
        employeeBirthdaysRepository.saveAndFlush(employeeBirthdays);

        int databaseSizeBeforeDelete = employeeBirthdaysRepository.findAll().size();

        // Delete the employeeBirthdays
        restEmployeeBirthdaysMockMvc
            .perform(delete(ENTITY_API_URL_ID, employeeBirthdays.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeBirthdays> employeeBirthdaysList = employeeBirthdaysRepository.findAll();
        assertThat(employeeBirthdaysList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
