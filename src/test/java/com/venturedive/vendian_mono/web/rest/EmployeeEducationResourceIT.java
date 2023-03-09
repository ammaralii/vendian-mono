package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.EmployeeEducation;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.QualificationTypes;
import com.venturedive.vendian_mono.repository.EmployeeEducationRepository;
import com.venturedive.vendian_mono.service.criteria.EmployeeEducationCriteria;
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
 * Integration tests for the {@link EmployeeEducationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeeEducationResourceIT {

    private static final String DEFAULT_INSTITUTE = "AAAAAAAAAA";
    private static final String UPDATED_INSTITUTE = "BBBBBBBBBB";

    private static final String DEFAULT_MAJOR = "AAAAAAAAAA";
    private static final String UPDATED_MAJOR = "BBBBBBBBBB";

    private static final String DEFAULT_DEGREE = "AAAAAAAAAA";
    private static final String UPDATED_DEGREE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATEFROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATEFROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATETO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATETO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/employee-educations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeeEducationRepository employeeEducationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeEducationMockMvc;

    private EmployeeEducation employeeEducation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeEducation createEntity(EntityManager em) {
        EmployeeEducation employeeEducation = new EmployeeEducation()
            .institute(DEFAULT_INSTITUTE)
            .major(DEFAULT_MAJOR)
            .degree(DEFAULT_DEGREE)
            .value(DEFAULT_VALUE)
            .city(DEFAULT_CITY)
            .province(DEFAULT_PROVINCE)
            .country(DEFAULT_COUNTRY)
            .datefrom(DEFAULT_DATEFROM)
            .dateto(DEFAULT_DATETO)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        // Add required entity
        QualificationTypes qualificationTypes;
        if (TestUtil.findAll(em, QualificationTypes.class).isEmpty()) {
            qualificationTypes = QualificationTypesResourceIT.createEntity(em);
            em.persist(qualificationTypes);
            em.flush();
        } else {
            qualificationTypes = TestUtil.findAll(em, QualificationTypes.class).get(0);
        }
        employeeEducation.setQualificationtype(qualificationTypes);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        employeeEducation.setEmployee(employees);
        return employeeEducation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeEducation createUpdatedEntity(EntityManager em) {
        EmployeeEducation employeeEducation = new EmployeeEducation()
            .institute(UPDATED_INSTITUTE)
            .major(UPDATED_MAJOR)
            .degree(UPDATED_DEGREE)
            .value(UPDATED_VALUE)
            .city(UPDATED_CITY)
            .province(UPDATED_PROVINCE)
            .country(UPDATED_COUNTRY)
            .datefrom(UPDATED_DATEFROM)
            .dateto(UPDATED_DATETO)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        // Add required entity
        QualificationTypes qualificationTypes;
        if (TestUtil.findAll(em, QualificationTypes.class).isEmpty()) {
            qualificationTypes = QualificationTypesResourceIT.createUpdatedEntity(em);
            em.persist(qualificationTypes);
            em.flush();
        } else {
            qualificationTypes = TestUtil.findAll(em, QualificationTypes.class).get(0);
        }
        employeeEducation.setQualificationtype(qualificationTypes);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createUpdatedEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        employeeEducation.setEmployee(employees);
        return employeeEducation;
    }

    @BeforeEach
    public void initTest() {
        employeeEducation = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployeeEducation() throws Exception {
        int databaseSizeBeforeCreate = employeeEducationRepository.findAll().size();
        // Create the EmployeeEducation
        restEmployeeEducationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeEducation))
            )
            .andExpect(status().isCreated());

        // Validate the EmployeeEducation in the database
        List<EmployeeEducation> employeeEducationList = employeeEducationRepository.findAll();
        assertThat(employeeEducationList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeEducation testEmployeeEducation = employeeEducationList.get(employeeEducationList.size() - 1);
        assertThat(testEmployeeEducation.getInstitute()).isEqualTo(DEFAULT_INSTITUTE);
        assertThat(testEmployeeEducation.getMajor()).isEqualTo(DEFAULT_MAJOR);
        assertThat(testEmployeeEducation.getDegree()).isEqualTo(DEFAULT_DEGREE);
        assertThat(testEmployeeEducation.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testEmployeeEducation.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testEmployeeEducation.getProvince()).isEqualTo(DEFAULT_PROVINCE);
        assertThat(testEmployeeEducation.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testEmployeeEducation.getDatefrom()).isEqualTo(DEFAULT_DATEFROM);
        assertThat(testEmployeeEducation.getDateto()).isEqualTo(DEFAULT_DATETO);
        assertThat(testEmployeeEducation.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testEmployeeEducation.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createEmployeeEducationWithExistingId() throws Exception {
        // Create the EmployeeEducation with an existing ID
        employeeEducation.setId(1L);

        int databaseSizeBeforeCreate = employeeEducationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeEducationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeEducation))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeEducation in the database
        List<EmployeeEducation> employeeEducationList = employeeEducationRepository.findAll();
        assertThat(employeeEducationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkInstituteIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeEducationRepository.findAll().size();
        // set the field null
        employeeEducation.setInstitute(null);

        // Create the EmployeeEducation, which fails.

        restEmployeeEducationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeEducation))
            )
            .andExpect(status().isBadRequest());

        List<EmployeeEducation> employeeEducationList = employeeEducationRepository.findAll();
        assertThat(employeeEducationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmployeeEducations() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList
        restEmployeeEducationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeEducation.getId().intValue())))
            .andExpect(jsonPath("$.[*].institute").value(hasItem(DEFAULT_INSTITUTE)))
            .andExpect(jsonPath("$.[*].major").value(hasItem(DEFAULT_MAJOR)))
            .andExpect(jsonPath("$.[*].degree").value(hasItem(DEFAULT_DEGREE)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].province").value(hasItem(DEFAULT_PROVINCE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].datefrom").value(hasItem(DEFAULT_DATEFROM.toString())))
            .andExpect(jsonPath("$.[*].dateto").value(hasItem(DEFAULT_DATETO.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getEmployeeEducation() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get the employeeEducation
        restEmployeeEducationMockMvc
            .perform(get(ENTITY_API_URL_ID, employeeEducation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeEducation.getId().intValue()))
            .andExpect(jsonPath("$.institute").value(DEFAULT_INSTITUTE))
            .andExpect(jsonPath("$.major").value(DEFAULT_MAJOR))
            .andExpect(jsonPath("$.degree").value(DEFAULT_DEGREE))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.province").value(DEFAULT_PROVINCE))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.datefrom").value(DEFAULT_DATEFROM.toString()))
            .andExpect(jsonPath("$.dateto").value(DEFAULT_DATETO.toString()))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getEmployeeEducationsByIdFiltering() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        Long id = employeeEducation.getId();

        defaultEmployeeEducationShouldBeFound("id.equals=" + id);
        defaultEmployeeEducationShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeEducationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeEducationShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeEducationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeEducationShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByInstituteIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where institute equals to DEFAULT_INSTITUTE
        defaultEmployeeEducationShouldBeFound("institute.equals=" + DEFAULT_INSTITUTE);

        // Get all the employeeEducationList where institute equals to UPDATED_INSTITUTE
        defaultEmployeeEducationShouldNotBeFound("institute.equals=" + UPDATED_INSTITUTE);
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByInstituteIsInShouldWork() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where institute in DEFAULT_INSTITUTE or UPDATED_INSTITUTE
        defaultEmployeeEducationShouldBeFound("institute.in=" + DEFAULT_INSTITUTE + "," + UPDATED_INSTITUTE);

        // Get all the employeeEducationList where institute equals to UPDATED_INSTITUTE
        defaultEmployeeEducationShouldNotBeFound("institute.in=" + UPDATED_INSTITUTE);
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByInstituteIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where institute is not null
        defaultEmployeeEducationShouldBeFound("institute.specified=true");

        // Get all the employeeEducationList where institute is null
        defaultEmployeeEducationShouldNotBeFound("institute.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByInstituteContainsSomething() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where institute contains DEFAULT_INSTITUTE
        defaultEmployeeEducationShouldBeFound("institute.contains=" + DEFAULT_INSTITUTE);

        // Get all the employeeEducationList where institute contains UPDATED_INSTITUTE
        defaultEmployeeEducationShouldNotBeFound("institute.contains=" + UPDATED_INSTITUTE);
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByInstituteNotContainsSomething() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where institute does not contain DEFAULT_INSTITUTE
        defaultEmployeeEducationShouldNotBeFound("institute.doesNotContain=" + DEFAULT_INSTITUTE);

        // Get all the employeeEducationList where institute does not contain UPDATED_INSTITUTE
        defaultEmployeeEducationShouldBeFound("institute.doesNotContain=" + UPDATED_INSTITUTE);
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByMajorIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where major equals to DEFAULT_MAJOR
        defaultEmployeeEducationShouldBeFound("major.equals=" + DEFAULT_MAJOR);

        // Get all the employeeEducationList where major equals to UPDATED_MAJOR
        defaultEmployeeEducationShouldNotBeFound("major.equals=" + UPDATED_MAJOR);
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByMajorIsInShouldWork() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where major in DEFAULT_MAJOR or UPDATED_MAJOR
        defaultEmployeeEducationShouldBeFound("major.in=" + DEFAULT_MAJOR + "," + UPDATED_MAJOR);

        // Get all the employeeEducationList where major equals to UPDATED_MAJOR
        defaultEmployeeEducationShouldNotBeFound("major.in=" + UPDATED_MAJOR);
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByMajorIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where major is not null
        defaultEmployeeEducationShouldBeFound("major.specified=true");

        // Get all the employeeEducationList where major is null
        defaultEmployeeEducationShouldNotBeFound("major.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByMajorContainsSomething() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where major contains DEFAULT_MAJOR
        defaultEmployeeEducationShouldBeFound("major.contains=" + DEFAULT_MAJOR);

        // Get all the employeeEducationList where major contains UPDATED_MAJOR
        defaultEmployeeEducationShouldNotBeFound("major.contains=" + UPDATED_MAJOR);
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByMajorNotContainsSomething() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where major does not contain DEFAULT_MAJOR
        defaultEmployeeEducationShouldNotBeFound("major.doesNotContain=" + DEFAULT_MAJOR);

        // Get all the employeeEducationList where major does not contain UPDATED_MAJOR
        defaultEmployeeEducationShouldBeFound("major.doesNotContain=" + UPDATED_MAJOR);
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByDegreeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where degree equals to DEFAULT_DEGREE
        defaultEmployeeEducationShouldBeFound("degree.equals=" + DEFAULT_DEGREE);

        // Get all the employeeEducationList where degree equals to UPDATED_DEGREE
        defaultEmployeeEducationShouldNotBeFound("degree.equals=" + UPDATED_DEGREE);
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByDegreeIsInShouldWork() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where degree in DEFAULT_DEGREE or UPDATED_DEGREE
        defaultEmployeeEducationShouldBeFound("degree.in=" + DEFAULT_DEGREE + "," + UPDATED_DEGREE);

        // Get all the employeeEducationList where degree equals to UPDATED_DEGREE
        defaultEmployeeEducationShouldNotBeFound("degree.in=" + UPDATED_DEGREE);
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByDegreeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where degree is not null
        defaultEmployeeEducationShouldBeFound("degree.specified=true");

        // Get all the employeeEducationList where degree is null
        defaultEmployeeEducationShouldNotBeFound("degree.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByDegreeContainsSomething() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where degree contains DEFAULT_DEGREE
        defaultEmployeeEducationShouldBeFound("degree.contains=" + DEFAULT_DEGREE);

        // Get all the employeeEducationList where degree contains UPDATED_DEGREE
        defaultEmployeeEducationShouldNotBeFound("degree.contains=" + UPDATED_DEGREE);
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByDegreeNotContainsSomething() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where degree does not contain DEFAULT_DEGREE
        defaultEmployeeEducationShouldNotBeFound("degree.doesNotContain=" + DEFAULT_DEGREE);

        // Get all the employeeEducationList where degree does not contain UPDATED_DEGREE
        defaultEmployeeEducationShouldBeFound("degree.doesNotContain=" + UPDATED_DEGREE);
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where value equals to DEFAULT_VALUE
        defaultEmployeeEducationShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the employeeEducationList where value equals to UPDATED_VALUE
        defaultEmployeeEducationShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultEmployeeEducationShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the employeeEducationList where value equals to UPDATED_VALUE
        defaultEmployeeEducationShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where value is not null
        defaultEmployeeEducationShouldBeFound("value.specified=true");

        // Get all the employeeEducationList where value is null
        defaultEmployeeEducationShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByValueContainsSomething() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where value contains DEFAULT_VALUE
        defaultEmployeeEducationShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the employeeEducationList where value contains UPDATED_VALUE
        defaultEmployeeEducationShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByValueNotContainsSomething() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where value does not contain DEFAULT_VALUE
        defaultEmployeeEducationShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the employeeEducationList where value does not contain UPDATED_VALUE
        defaultEmployeeEducationShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where city equals to DEFAULT_CITY
        defaultEmployeeEducationShouldBeFound("city.equals=" + DEFAULT_CITY);

        // Get all the employeeEducationList where city equals to UPDATED_CITY
        defaultEmployeeEducationShouldNotBeFound("city.equals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByCityIsInShouldWork() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where city in DEFAULT_CITY or UPDATED_CITY
        defaultEmployeeEducationShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

        // Get all the employeeEducationList where city equals to UPDATED_CITY
        defaultEmployeeEducationShouldNotBeFound("city.in=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where city is not null
        defaultEmployeeEducationShouldBeFound("city.specified=true");

        // Get all the employeeEducationList where city is null
        defaultEmployeeEducationShouldNotBeFound("city.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByCityContainsSomething() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where city contains DEFAULT_CITY
        defaultEmployeeEducationShouldBeFound("city.contains=" + DEFAULT_CITY);

        // Get all the employeeEducationList where city contains UPDATED_CITY
        defaultEmployeeEducationShouldNotBeFound("city.contains=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByCityNotContainsSomething() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where city does not contain DEFAULT_CITY
        defaultEmployeeEducationShouldNotBeFound("city.doesNotContain=" + DEFAULT_CITY);

        // Get all the employeeEducationList where city does not contain UPDATED_CITY
        defaultEmployeeEducationShouldBeFound("city.doesNotContain=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByProvinceIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where province equals to DEFAULT_PROVINCE
        defaultEmployeeEducationShouldBeFound("province.equals=" + DEFAULT_PROVINCE);

        // Get all the employeeEducationList where province equals to UPDATED_PROVINCE
        defaultEmployeeEducationShouldNotBeFound("province.equals=" + UPDATED_PROVINCE);
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByProvinceIsInShouldWork() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where province in DEFAULT_PROVINCE or UPDATED_PROVINCE
        defaultEmployeeEducationShouldBeFound("province.in=" + DEFAULT_PROVINCE + "," + UPDATED_PROVINCE);

        // Get all the employeeEducationList where province equals to UPDATED_PROVINCE
        defaultEmployeeEducationShouldNotBeFound("province.in=" + UPDATED_PROVINCE);
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByProvinceIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where province is not null
        defaultEmployeeEducationShouldBeFound("province.specified=true");

        // Get all the employeeEducationList where province is null
        defaultEmployeeEducationShouldNotBeFound("province.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByProvinceContainsSomething() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where province contains DEFAULT_PROVINCE
        defaultEmployeeEducationShouldBeFound("province.contains=" + DEFAULT_PROVINCE);

        // Get all the employeeEducationList where province contains UPDATED_PROVINCE
        defaultEmployeeEducationShouldNotBeFound("province.contains=" + UPDATED_PROVINCE);
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByProvinceNotContainsSomething() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where province does not contain DEFAULT_PROVINCE
        defaultEmployeeEducationShouldNotBeFound("province.doesNotContain=" + DEFAULT_PROVINCE);

        // Get all the employeeEducationList where province does not contain UPDATED_PROVINCE
        defaultEmployeeEducationShouldBeFound("province.doesNotContain=" + UPDATED_PROVINCE);
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where country equals to DEFAULT_COUNTRY
        defaultEmployeeEducationShouldBeFound("country.equals=" + DEFAULT_COUNTRY);

        // Get all the employeeEducationList where country equals to UPDATED_COUNTRY
        defaultEmployeeEducationShouldNotBeFound("country.equals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where country in DEFAULT_COUNTRY or UPDATED_COUNTRY
        defaultEmployeeEducationShouldBeFound("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY);

        // Get all the employeeEducationList where country equals to UPDATED_COUNTRY
        defaultEmployeeEducationShouldNotBeFound("country.in=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where country is not null
        defaultEmployeeEducationShouldBeFound("country.specified=true");

        // Get all the employeeEducationList where country is null
        defaultEmployeeEducationShouldNotBeFound("country.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByCountryContainsSomething() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where country contains DEFAULT_COUNTRY
        defaultEmployeeEducationShouldBeFound("country.contains=" + DEFAULT_COUNTRY);

        // Get all the employeeEducationList where country contains UPDATED_COUNTRY
        defaultEmployeeEducationShouldNotBeFound("country.contains=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByCountryNotContainsSomething() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where country does not contain DEFAULT_COUNTRY
        defaultEmployeeEducationShouldNotBeFound("country.doesNotContain=" + DEFAULT_COUNTRY);

        // Get all the employeeEducationList where country does not contain UPDATED_COUNTRY
        defaultEmployeeEducationShouldBeFound("country.doesNotContain=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByDatefromIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where datefrom equals to DEFAULT_DATEFROM
        defaultEmployeeEducationShouldBeFound("datefrom.equals=" + DEFAULT_DATEFROM);

        // Get all the employeeEducationList where datefrom equals to UPDATED_DATEFROM
        defaultEmployeeEducationShouldNotBeFound("datefrom.equals=" + UPDATED_DATEFROM);
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByDatefromIsInShouldWork() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where datefrom in DEFAULT_DATEFROM or UPDATED_DATEFROM
        defaultEmployeeEducationShouldBeFound("datefrom.in=" + DEFAULT_DATEFROM + "," + UPDATED_DATEFROM);

        // Get all the employeeEducationList where datefrom equals to UPDATED_DATEFROM
        defaultEmployeeEducationShouldNotBeFound("datefrom.in=" + UPDATED_DATEFROM);
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByDatefromIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where datefrom is not null
        defaultEmployeeEducationShouldBeFound("datefrom.specified=true");

        // Get all the employeeEducationList where datefrom is null
        defaultEmployeeEducationShouldNotBeFound("datefrom.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByDatetoIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where dateto equals to DEFAULT_DATETO
        defaultEmployeeEducationShouldBeFound("dateto.equals=" + DEFAULT_DATETO);

        // Get all the employeeEducationList where dateto equals to UPDATED_DATETO
        defaultEmployeeEducationShouldNotBeFound("dateto.equals=" + UPDATED_DATETO);
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByDatetoIsInShouldWork() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where dateto in DEFAULT_DATETO or UPDATED_DATETO
        defaultEmployeeEducationShouldBeFound("dateto.in=" + DEFAULT_DATETO + "," + UPDATED_DATETO);

        // Get all the employeeEducationList where dateto equals to UPDATED_DATETO
        defaultEmployeeEducationShouldNotBeFound("dateto.in=" + UPDATED_DATETO);
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByDatetoIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where dateto is not null
        defaultEmployeeEducationShouldBeFound("dateto.specified=true");

        // Get all the employeeEducationList where dateto is null
        defaultEmployeeEducationShouldNotBeFound("dateto.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where createdat equals to DEFAULT_CREATEDAT
        defaultEmployeeEducationShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the employeeEducationList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeEducationShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultEmployeeEducationShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the employeeEducationList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeEducationShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where createdat is not null
        defaultEmployeeEducationShouldBeFound("createdat.specified=true");

        // Get all the employeeEducationList where createdat is null
        defaultEmployeeEducationShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where updatedat equals to DEFAULT_UPDATEDAT
        defaultEmployeeEducationShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the employeeEducationList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeEducationShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultEmployeeEducationShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the employeeEducationList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeEducationShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        // Get all the employeeEducationList where updatedat is not null
        defaultEmployeeEducationShouldBeFound("updatedat.specified=true");

        // Get all the employeeEducationList where updatedat is null
        defaultEmployeeEducationShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByQualificationtypeIsEqualToSomething() throws Exception {
        QualificationTypes qualificationtype;
        if (TestUtil.findAll(em, QualificationTypes.class).isEmpty()) {
            employeeEducationRepository.saveAndFlush(employeeEducation);
            qualificationtype = QualificationTypesResourceIT.createEntity(em);
        } else {
            qualificationtype = TestUtil.findAll(em, QualificationTypes.class).get(0);
        }
        em.persist(qualificationtype);
        em.flush();
        employeeEducation.setQualificationtype(qualificationtype);
        employeeEducationRepository.saveAndFlush(employeeEducation);
        Long qualificationtypeId = qualificationtype.getId();

        // Get all the employeeEducationList where qualificationtype equals to qualificationtypeId
        defaultEmployeeEducationShouldBeFound("qualificationtypeId.equals=" + qualificationtypeId);

        // Get all the employeeEducationList where qualificationtype equals to (qualificationtypeId + 1)
        defaultEmployeeEducationShouldNotBeFound("qualificationtypeId.equals=" + (qualificationtypeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeeEducationsByEmployeeIsEqualToSomething() throws Exception {
        Employees employee;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employeeEducationRepository.saveAndFlush(employeeEducation);
            employee = EmployeesResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employee);
        em.flush();
        employeeEducation.setEmployee(employee);
        employeeEducationRepository.saveAndFlush(employeeEducation);
        Long employeeId = employee.getId();

        // Get all the employeeEducationList where employee equals to employeeId
        defaultEmployeeEducationShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the employeeEducationList where employee equals to (employeeId + 1)
        defaultEmployeeEducationShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeEducationShouldBeFound(String filter) throws Exception {
        restEmployeeEducationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeEducation.getId().intValue())))
            .andExpect(jsonPath("$.[*].institute").value(hasItem(DEFAULT_INSTITUTE)))
            .andExpect(jsonPath("$.[*].major").value(hasItem(DEFAULT_MAJOR)))
            .andExpect(jsonPath("$.[*].degree").value(hasItem(DEFAULT_DEGREE)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].province").value(hasItem(DEFAULT_PROVINCE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].datefrom").value(hasItem(DEFAULT_DATEFROM.toString())))
            .andExpect(jsonPath("$.[*].dateto").value(hasItem(DEFAULT_DATETO.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restEmployeeEducationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeEducationShouldNotBeFound(String filter) throws Exception {
        restEmployeeEducationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeEducationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployeeEducation() throws Exception {
        // Get the employeeEducation
        restEmployeeEducationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmployeeEducation() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        int databaseSizeBeforeUpdate = employeeEducationRepository.findAll().size();

        // Update the employeeEducation
        EmployeeEducation updatedEmployeeEducation = employeeEducationRepository.findById(employeeEducation.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeEducation are not directly saved in db
        em.detach(updatedEmployeeEducation);
        updatedEmployeeEducation
            .institute(UPDATED_INSTITUTE)
            .major(UPDATED_MAJOR)
            .degree(UPDATED_DEGREE)
            .value(UPDATED_VALUE)
            .city(UPDATED_CITY)
            .province(UPDATED_PROVINCE)
            .country(UPDATED_COUNTRY)
            .datefrom(UPDATED_DATEFROM)
            .dateto(UPDATED_DATETO)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restEmployeeEducationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmployeeEducation.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeEducation))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeEducation in the database
        List<EmployeeEducation> employeeEducationList = employeeEducationRepository.findAll();
        assertThat(employeeEducationList).hasSize(databaseSizeBeforeUpdate);
        EmployeeEducation testEmployeeEducation = employeeEducationList.get(employeeEducationList.size() - 1);
        assertThat(testEmployeeEducation.getInstitute()).isEqualTo(UPDATED_INSTITUTE);
        assertThat(testEmployeeEducation.getMajor()).isEqualTo(UPDATED_MAJOR);
        assertThat(testEmployeeEducation.getDegree()).isEqualTo(UPDATED_DEGREE);
        assertThat(testEmployeeEducation.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testEmployeeEducation.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testEmployeeEducation.getProvince()).isEqualTo(UPDATED_PROVINCE);
        assertThat(testEmployeeEducation.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testEmployeeEducation.getDatefrom()).isEqualTo(UPDATED_DATEFROM);
        assertThat(testEmployeeEducation.getDateto()).isEqualTo(UPDATED_DATETO);
        assertThat(testEmployeeEducation.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeEducation.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingEmployeeEducation() throws Exception {
        int databaseSizeBeforeUpdate = employeeEducationRepository.findAll().size();
        employeeEducation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeEducationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeEducation.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeEducation))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeEducation in the database
        List<EmployeeEducation> employeeEducationList = employeeEducationRepository.findAll();
        assertThat(employeeEducationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployeeEducation() throws Exception {
        int databaseSizeBeforeUpdate = employeeEducationRepository.findAll().size();
        employeeEducation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeEducationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeEducation))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeEducation in the database
        List<EmployeeEducation> employeeEducationList = employeeEducationRepository.findAll();
        assertThat(employeeEducationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployeeEducation() throws Exception {
        int databaseSizeBeforeUpdate = employeeEducationRepository.findAll().size();
        employeeEducation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeEducationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeEducation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeEducation in the database
        List<EmployeeEducation> employeeEducationList = employeeEducationRepository.findAll();
        assertThat(employeeEducationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeEducationWithPatch() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        int databaseSizeBeforeUpdate = employeeEducationRepository.findAll().size();

        // Update the employeeEducation using partial update
        EmployeeEducation partialUpdatedEmployeeEducation = new EmployeeEducation();
        partialUpdatedEmployeeEducation.setId(employeeEducation.getId());

        partialUpdatedEmployeeEducation
            .institute(UPDATED_INSTITUTE)
            .degree(UPDATED_DEGREE)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY)
            .datefrom(UPDATED_DATEFROM)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restEmployeeEducationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeEducation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeEducation))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeEducation in the database
        List<EmployeeEducation> employeeEducationList = employeeEducationRepository.findAll();
        assertThat(employeeEducationList).hasSize(databaseSizeBeforeUpdate);
        EmployeeEducation testEmployeeEducation = employeeEducationList.get(employeeEducationList.size() - 1);
        assertThat(testEmployeeEducation.getInstitute()).isEqualTo(UPDATED_INSTITUTE);
        assertThat(testEmployeeEducation.getMajor()).isEqualTo(DEFAULT_MAJOR);
        assertThat(testEmployeeEducation.getDegree()).isEqualTo(UPDATED_DEGREE);
        assertThat(testEmployeeEducation.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testEmployeeEducation.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testEmployeeEducation.getProvince()).isEqualTo(DEFAULT_PROVINCE);
        assertThat(testEmployeeEducation.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testEmployeeEducation.getDatefrom()).isEqualTo(UPDATED_DATEFROM);
        assertThat(testEmployeeEducation.getDateto()).isEqualTo(DEFAULT_DATETO);
        assertThat(testEmployeeEducation.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeEducation.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateEmployeeEducationWithPatch() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        int databaseSizeBeforeUpdate = employeeEducationRepository.findAll().size();

        // Update the employeeEducation using partial update
        EmployeeEducation partialUpdatedEmployeeEducation = new EmployeeEducation();
        partialUpdatedEmployeeEducation.setId(employeeEducation.getId());

        partialUpdatedEmployeeEducation
            .institute(UPDATED_INSTITUTE)
            .major(UPDATED_MAJOR)
            .degree(UPDATED_DEGREE)
            .value(UPDATED_VALUE)
            .city(UPDATED_CITY)
            .province(UPDATED_PROVINCE)
            .country(UPDATED_COUNTRY)
            .datefrom(UPDATED_DATEFROM)
            .dateto(UPDATED_DATETO)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restEmployeeEducationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeEducation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeEducation))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeEducation in the database
        List<EmployeeEducation> employeeEducationList = employeeEducationRepository.findAll();
        assertThat(employeeEducationList).hasSize(databaseSizeBeforeUpdate);
        EmployeeEducation testEmployeeEducation = employeeEducationList.get(employeeEducationList.size() - 1);
        assertThat(testEmployeeEducation.getInstitute()).isEqualTo(UPDATED_INSTITUTE);
        assertThat(testEmployeeEducation.getMajor()).isEqualTo(UPDATED_MAJOR);
        assertThat(testEmployeeEducation.getDegree()).isEqualTo(UPDATED_DEGREE);
        assertThat(testEmployeeEducation.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testEmployeeEducation.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testEmployeeEducation.getProvince()).isEqualTo(UPDATED_PROVINCE);
        assertThat(testEmployeeEducation.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testEmployeeEducation.getDatefrom()).isEqualTo(UPDATED_DATEFROM);
        assertThat(testEmployeeEducation.getDateto()).isEqualTo(UPDATED_DATETO);
        assertThat(testEmployeeEducation.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeEducation.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingEmployeeEducation() throws Exception {
        int databaseSizeBeforeUpdate = employeeEducationRepository.findAll().size();
        employeeEducation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeEducationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employeeEducation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeEducation))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeEducation in the database
        List<EmployeeEducation> employeeEducationList = employeeEducationRepository.findAll();
        assertThat(employeeEducationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployeeEducation() throws Exception {
        int databaseSizeBeforeUpdate = employeeEducationRepository.findAll().size();
        employeeEducation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeEducationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeEducation))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeEducation in the database
        List<EmployeeEducation> employeeEducationList = employeeEducationRepository.findAll();
        assertThat(employeeEducationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployeeEducation() throws Exception {
        int databaseSizeBeforeUpdate = employeeEducationRepository.findAll().size();
        employeeEducation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeEducationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeEducation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeEducation in the database
        List<EmployeeEducation> employeeEducationList = employeeEducationRepository.findAll();
        assertThat(employeeEducationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployeeEducation() throws Exception {
        // Initialize the database
        employeeEducationRepository.saveAndFlush(employeeEducation);

        int databaseSizeBeforeDelete = employeeEducationRepository.findAll().size();

        // Delete the employeeEducation
        restEmployeeEducationMockMvc
            .perform(delete(ENTITY_API_URL_ID, employeeEducation.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeEducation> employeeEducationList = employeeEducationRepository.findAll();
        assertThat(employeeEducationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
