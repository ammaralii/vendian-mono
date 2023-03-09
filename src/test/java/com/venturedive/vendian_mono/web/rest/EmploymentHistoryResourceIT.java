package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.EmploymentHistory;
import com.venturedive.vendian_mono.repository.EmploymentHistoryRepository;
import com.venturedive.vendian_mono.service.criteria.EmploymentHistoryCriteria;
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
 * Integration tests for the {@link EmploymentHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmploymentHistoryResourceIT {

    private static final String DEFAULT_POSITIONTITLE = "AAAAAAAAAA";
    private static final String UPDATED_POSITIONTITLE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANYNAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANYNAME = "BBBBBBBBBB";

    private static final String DEFAULT_GRADE = "AAAAAAAAAA";
    private static final String UPDATED_GRADE = "BBBBBBBBBB";

    private static final String DEFAULT_JOBDESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_JOBDESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final Instant DEFAULT_STARTDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STARTDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ENDDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ENDDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_REASONFORLEAVING = "AAAAAAAAAA";
    private static final String UPDATED_REASONFORLEAVING = "BBBBBBBBBB";

    private static final byte[] DEFAULT_GROSSSALARY = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_GROSSSALARY = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_GROSSSALARY_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_GROSSSALARY_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/employment-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmploymentHistoryRepository employmentHistoryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmploymentHistoryMockMvc;

    private EmploymentHistory employmentHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmploymentHistory createEntity(EntityManager em) {
        EmploymentHistory employmentHistory = new EmploymentHistory()
            .positiontitle(DEFAULT_POSITIONTITLE)
            .companyname(DEFAULT_COMPANYNAME)
            .grade(DEFAULT_GRADE)
            .jobdescription(DEFAULT_JOBDESCRIPTION)
            .city(DEFAULT_CITY)
            .country(DEFAULT_COUNTRY)
            .startdate(DEFAULT_STARTDATE)
            .enddate(DEFAULT_ENDDATE)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT)
            .reasonforleaving(DEFAULT_REASONFORLEAVING)
            .grosssalary(DEFAULT_GROSSSALARY)
            .grosssalaryContentType(DEFAULT_GROSSSALARY_CONTENT_TYPE);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        employmentHistory.setEmployee(employees);
        return employmentHistory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmploymentHistory createUpdatedEntity(EntityManager em) {
        EmploymentHistory employmentHistory = new EmploymentHistory()
            .positiontitle(UPDATED_POSITIONTITLE)
            .companyname(UPDATED_COMPANYNAME)
            .grade(UPDATED_GRADE)
            .jobdescription(UPDATED_JOBDESCRIPTION)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY)
            .startdate(UPDATED_STARTDATE)
            .enddate(UPDATED_ENDDATE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .reasonforleaving(UPDATED_REASONFORLEAVING)
            .grosssalary(UPDATED_GROSSSALARY)
            .grosssalaryContentType(UPDATED_GROSSSALARY_CONTENT_TYPE);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createUpdatedEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        employmentHistory.setEmployee(employees);
        return employmentHistory;
    }

    @BeforeEach
    public void initTest() {
        employmentHistory = createEntity(em);
    }

    @Test
    @Transactional
    void createEmploymentHistory() throws Exception {
        int databaseSizeBeforeCreate = employmentHistoryRepository.findAll().size();
        // Create the EmploymentHistory
        restEmploymentHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employmentHistory))
            )
            .andExpect(status().isCreated());

        // Validate the EmploymentHistory in the database
        List<EmploymentHistory> employmentHistoryList = employmentHistoryRepository.findAll();
        assertThat(employmentHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        EmploymentHistory testEmploymentHistory = employmentHistoryList.get(employmentHistoryList.size() - 1);
        assertThat(testEmploymentHistory.getPositiontitle()).isEqualTo(DEFAULT_POSITIONTITLE);
        assertThat(testEmploymentHistory.getCompanyname()).isEqualTo(DEFAULT_COMPANYNAME);
        assertThat(testEmploymentHistory.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testEmploymentHistory.getJobdescription()).isEqualTo(DEFAULT_JOBDESCRIPTION);
        assertThat(testEmploymentHistory.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testEmploymentHistory.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testEmploymentHistory.getStartdate()).isEqualTo(DEFAULT_STARTDATE);
        assertThat(testEmploymentHistory.getEnddate()).isEqualTo(DEFAULT_ENDDATE);
        assertThat(testEmploymentHistory.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testEmploymentHistory.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testEmploymentHistory.getReasonforleaving()).isEqualTo(DEFAULT_REASONFORLEAVING);
        assertThat(testEmploymentHistory.getGrosssalary()).isEqualTo(DEFAULT_GROSSSALARY);
        assertThat(testEmploymentHistory.getGrosssalaryContentType()).isEqualTo(DEFAULT_GROSSSALARY_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createEmploymentHistoryWithExistingId() throws Exception {
        // Create the EmploymentHistory with an existing ID
        employmentHistory.setId(1L);

        int databaseSizeBeforeCreate = employmentHistoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmploymentHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employmentHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmploymentHistory in the database
        List<EmploymentHistory> employmentHistoryList = employmentHistoryRepository.findAll();
        assertThat(employmentHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPositiontitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = employmentHistoryRepository.findAll().size();
        // set the field null
        employmentHistory.setPositiontitle(null);

        // Create the EmploymentHistory, which fails.

        restEmploymentHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employmentHistory))
            )
            .andExpect(status().isBadRequest());

        List<EmploymentHistory> employmentHistoryList = employmentHistoryRepository.findAll();
        assertThat(employmentHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCompanynameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employmentHistoryRepository.findAll().size();
        // set the field null
        employmentHistory.setCompanyname(null);

        // Create the EmploymentHistory, which fails.

        restEmploymentHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employmentHistory))
            )
            .andExpect(status().isBadRequest());

        List<EmploymentHistory> employmentHistoryList = employmentHistoryRepository.findAll();
        assertThat(employmentHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStartdateIsRequired() throws Exception {
        int databaseSizeBeforeTest = employmentHistoryRepository.findAll().size();
        // set the field null
        employmentHistory.setStartdate(null);

        // Create the EmploymentHistory, which fails.

        restEmploymentHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employmentHistory))
            )
            .andExpect(status().isBadRequest());

        List<EmploymentHistory> employmentHistoryList = employmentHistoryRepository.findAll();
        assertThat(employmentHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEnddateIsRequired() throws Exception {
        int databaseSizeBeforeTest = employmentHistoryRepository.findAll().size();
        // set the field null
        employmentHistory.setEnddate(null);

        // Create the EmploymentHistory, which fails.

        restEmploymentHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employmentHistory))
            )
            .andExpect(status().isBadRequest());

        List<EmploymentHistory> employmentHistoryList = employmentHistoryRepository.findAll();
        assertThat(employmentHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmploymentHistories() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList
        restEmploymentHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employmentHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].positiontitle").value(hasItem(DEFAULT_POSITIONTITLE)))
            .andExpect(jsonPath("$.[*].companyname").value(hasItem(DEFAULT_COMPANYNAME)))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE)))
            .andExpect(jsonPath("$.[*].jobdescription").value(hasItem(DEFAULT_JOBDESCRIPTION)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].startdate").value(hasItem(DEFAULT_STARTDATE.toString())))
            .andExpect(jsonPath("$.[*].enddate").value(hasItem(DEFAULT_ENDDATE.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].reasonforleaving").value(hasItem(DEFAULT_REASONFORLEAVING)))
            .andExpect(jsonPath("$.[*].grosssalaryContentType").value(hasItem(DEFAULT_GROSSSALARY_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].grosssalary").value(hasItem(Base64Utils.encodeToString(DEFAULT_GROSSSALARY))));
    }

    @Test
    @Transactional
    void getEmploymentHistory() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get the employmentHistory
        restEmploymentHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, employmentHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employmentHistory.getId().intValue()))
            .andExpect(jsonPath("$.positiontitle").value(DEFAULT_POSITIONTITLE))
            .andExpect(jsonPath("$.companyname").value(DEFAULT_COMPANYNAME))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE))
            .andExpect(jsonPath("$.jobdescription").value(DEFAULT_JOBDESCRIPTION))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.startdate").value(DEFAULT_STARTDATE.toString()))
            .andExpect(jsonPath("$.enddate").value(DEFAULT_ENDDATE.toString()))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()))
            .andExpect(jsonPath("$.reasonforleaving").value(DEFAULT_REASONFORLEAVING))
            .andExpect(jsonPath("$.grosssalaryContentType").value(DEFAULT_GROSSSALARY_CONTENT_TYPE))
            .andExpect(jsonPath("$.grosssalary").value(Base64Utils.encodeToString(DEFAULT_GROSSSALARY)));
    }

    @Test
    @Transactional
    void getEmploymentHistoriesByIdFiltering() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        Long id = employmentHistory.getId();

        defaultEmploymentHistoryShouldBeFound("id.equals=" + id);
        defaultEmploymentHistoryShouldNotBeFound("id.notEquals=" + id);

        defaultEmploymentHistoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmploymentHistoryShouldNotBeFound("id.greaterThan=" + id);

        defaultEmploymentHistoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmploymentHistoryShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByPositiontitleIsEqualToSomething() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where positiontitle equals to DEFAULT_POSITIONTITLE
        defaultEmploymentHistoryShouldBeFound("positiontitle.equals=" + DEFAULT_POSITIONTITLE);

        // Get all the employmentHistoryList where positiontitle equals to UPDATED_POSITIONTITLE
        defaultEmploymentHistoryShouldNotBeFound("positiontitle.equals=" + UPDATED_POSITIONTITLE);
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByPositiontitleIsInShouldWork() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where positiontitle in DEFAULT_POSITIONTITLE or UPDATED_POSITIONTITLE
        defaultEmploymentHistoryShouldBeFound("positiontitle.in=" + DEFAULT_POSITIONTITLE + "," + UPDATED_POSITIONTITLE);

        // Get all the employmentHistoryList where positiontitle equals to UPDATED_POSITIONTITLE
        defaultEmploymentHistoryShouldNotBeFound("positiontitle.in=" + UPDATED_POSITIONTITLE);
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByPositiontitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where positiontitle is not null
        defaultEmploymentHistoryShouldBeFound("positiontitle.specified=true");

        // Get all the employmentHistoryList where positiontitle is null
        defaultEmploymentHistoryShouldNotBeFound("positiontitle.specified=false");
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByPositiontitleContainsSomething() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where positiontitle contains DEFAULT_POSITIONTITLE
        defaultEmploymentHistoryShouldBeFound("positiontitle.contains=" + DEFAULT_POSITIONTITLE);

        // Get all the employmentHistoryList where positiontitle contains UPDATED_POSITIONTITLE
        defaultEmploymentHistoryShouldNotBeFound("positiontitle.contains=" + UPDATED_POSITIONTITLE);
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByPositiontitleNotContainsSomething() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where positiontitle does not contain DEFAULT_POSITIONTITLE
        defaultEmploymentHistoryShouldNotBeFound("positiontitle.doesNotContain=" + DEFAULT_POSITIONTITLE);

        // Get all the employmentHistoryList where positiontitle does not contain UPDATED_POSITIONTITLE
        defaultEmploymentHistoryShouldBeFound("positiontitle.doesNotContain=" + UPDATED_POSITIONTITLE);
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByCompanynameIsEqualToSomething() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where companyname equals to DEFAULT_COMPANYNAME
        defaultEmploymentHistoryShouldBeFound("companyname.equals=" + DEFAULT_COMPANYNAME);

        // Get all the employmentHistoryList where companyname equals to UPDATED_COMPANYNAME
        defaultEmploymentHistoryShouldNotBeFound("companyname.equals=" + UPDATED_COMPANYNAME);
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByCompanynameIsInShouldWork() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where companyname in DEFAULT_COMPANYNAME or UPDATED_COMPANYNAME
        defaultEmploymentHistoryShouldBeFound("companyname.in=" + DEFAULT_COMPANYNAME + "," + UPDATED_COMPANYNAME);

        // Get all the employmentHistoryList where companyname equals to UPDATED_COMPANYNAME
        defaultEmploymentHistoryShouldNotBeFound("companyname.in=" + UPDATED_COMPANYNAME);
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByCompanynameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where companyname is not null
        defaultEmploymentHistoryShouldBeFound("companyname.specified=true");

        // Get all the employmentHistoryList where companyname is null
        defaultEmploymentHistoryShouldNotBeFound("companyname.specified=false");
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByCompanynameContainsSomething() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where companyname contains DEFAULT_COMPANYNAME
        defaultEmploymentHistoryShouldBeFound("companyname.contains=" + DEFAULT_COMPANYNAME);

        // Get all the employmentHistoryList where companyname contains UPDATED_COMPANYNAME
        defaultEmploymentHistoryShouldNotBeFound("companyname.contains=" + UPDATED_COMPANYNAME);
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByCompanynameNotContainsSomething() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where companyname does not contain DEFAULT_COMPANYNAME
        defaultEmploymentHistoryShouldNotBeFound("companyname.doesNotContain=" + DEFAULT_COMPANYNAME);

        // Get all the employmentHistoryList where companyname does not contain UPDATED_COMPANYNAME
        defaultEmploymentHistoryShouldBeFound("companyname.doesNotContain=" + UPDATED_COMPANYNAME);
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByGradeIsEqualToSomething() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where grade equals to DEFAULT_GRADE
        defaultEmploymentHistoryShouldBeFound("grade.equals=" + DEFAULT_GRADE);

        // Get all the employmentHistoryList where grade equals to UPDATED_GRADE
        defaultEmploymentHistoryShouldNotBeFound("grade.equals=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByGradeIsInShouldWork() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where grade in DEFAULT_GRADE or UPDATED_GRADE
        defaultEmploymentHistoryShouldBeFound("grade.in=" + DEFAULT_GRADE + "," + UPDATED_GRADE);

        // Get all the employmentHistoryList where grade equals to UPDATED_GRADE
        defaultEmploymentHistoryShouldNotBeFound("grade.in=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByGradeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where grade is not null
        defaultEmploymentHistoryShouldBeFound("grade.specified=true");

        // Get all the employmentHistoryList where grade is null
        defaultEmploymentHistoryShouldNotBeFound("grade.specified=false");
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByGradeContainsSomething() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where grade contains DEFAULT_GRADE
        defaultEmploymentHistoryShouldBeFound("grade.contains=" + DEFAULT_GRADE);

        // Get all the employmentHistoryList where grade contains UPDATED_GRADE
        defaultEmploymentHistoryShouldNotBeFound("grade.contains=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByGradeNotContainsSomething() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where grade does not contain DEFAULT_GRADE
        defaultEmploymentHistoryShouldNotBeFound("grade.doesNotContain=" + DEFAULT_GRADE);

        // Get all the employmentHistoryList where grade does not contain UPDATED_GRADE
        defaultEmploymentHistoryShouldBeFound("grade.doesNotContain=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByJobdescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where jobdescription equals to DEFAULT_JOBDESCRIPTION
        defaultEmploymentHistoryShouldBeFound("jobdescription.equals=" + DEFAULT_JOBDESCRIPTION);

        // Get all the employmentHistoryList where jobdescription equals to UPDATED_JOBDESCRIPTION
        defaultEmploymentHistoryShouldNotBeFound("jobdescription.equals=" + UPDATED_JOBDESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByJobdescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where jobdescription in DEFAULT_JOBDESCRIPTION or UPDATED_JOBDESCRIPTION
        defaultEmploymentHistoryShouldBeFound("jobdescription.in=" + DEFAULT_JOBDESCRIPTION + "," + UPDATED_JOBDESCRIPTION);

        // Get all the employmentHistoryList where jobdescription equals to UPDATED_JOBDESCRIPTION
        defaultEmploymentHistoryShouldNotBeFound("jobdescription.in=" + UPDATED_JOBDESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByJobdescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where jobdescription is not null
        defaultEmploymentHistoryShouldBeFound("jobdescription.specified=true");

        // Get all the employmentHistoryList where jobdescription is null
        defaultEmploymentHistoryShouldNotBeFound("jobdescription.specified=false");
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByJobdescriptionContainsSomething() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where jobdescription contains DEFAULT_JOBDESCRIPTION
        defaultEmploymentHistoryShouldBeFound("jobdescription.contains=" + DEFAULT_JOBDESCRIPTION);

        // Get all the employmentHistoryList where jobdescription contains UPDATED_JOBDESCRIPTION
        defaultEmploymentHistoryShouldNotBeFound("jobdescription.contains=" + UPDATED_JOBDESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByJobdescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where jobdescription does not contain DEFAULT_JOBDESCRIPTION
        defaultEmploymentHistoryShouldNotBeFound("jobdescription.doesNotContain=" + DEFAULT_JOBDESCRIPTION);

        // Get all the employmentHistoryList where jobdescription does not contain UPDATED_JOBDESCRIPTION
        defaultEmploymentHistoryShouldBeFound("jobdescription.doesNotContain=" + UPDATED_JOBDESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where city equals to DEFAULT_CITY
        defaultEmploymentHistoryShouldBeFound("city.equals=" + DEFAULT_CITY);

        // Get all the employmentHistoryList where city equals to UPDATED_CITY
        defaultEmploymentHistoryShouldNotBeFound("city.equals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByCityIsInShouldWork() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where city in DEFAULT_CITY or UPDATED_CITY
        defaultEmploymentHistoryShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

        // Get all the employmentHistoryList where city equals to UPDATED_CITY
        defaultEmploymentHistoryShouldNotBeFound("city.in=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where city is not null
        defaultEmploymentHistoryShouldBeFound("city.specified=true");

        // Get all the employmentHistoryList where city is null
        defaultEmploymentHistoryShouldNotBeFound("city.specified=false");
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByCityContainsSomething() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where city contains DEFAULT_CITY
        defaultEmploymentHistoryShouldBeFound("city.contains=" + DEFAULT_CITY);

        // Get all the employmentHistoryList where city contains UPDATED_CITY
        defaultEmploymentHistoryShouldNotBeFound("city.contains=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByCityNotContainsSomething() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where city does not contain DEFAULT_CITY
        defaultEmploymentHistoryShouldNotBeFound("city.doesNotContain=" + DEFAULT_CITY);

        // Get all the employmentHistoryList where city does not contain UPDATED_CITY
        defaultEmploymentHistoryShouldBeFound("city.doesNotContain=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where country equals to DEFAULT_COUNTRY
        defaultEmploymentHistoryShouldBeFound("country.equals=" + DEFAULT_COUNTRY);

        // Get all the employmentHistoryList where country equals to UPDATED_COUNTRY
        defaultEmploymentHistoryShouldNotBeFound("country.equals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where country in DEFAULT_COUNTRY or UPDATED_COUNTRY
        defaultEmploymentHistoryShouldBeFound("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY);

        // Get all the employmentHistoryList where country equals to UPDATED_COUNTRY
        defaultEmploymentHistoryShouldNotBeFound("country.in=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where country is not null
        defaultEmploymentHistoryShouldBeFound("country.specified=true");

        // Get all the employmentHistoryList where country is null
        defaultEmploymentHistoryShouldNotBeFound("country.specified=false");
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByCountryContainsSomething() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where country contains DEFAULT_COUNTRY
        defaultEmploymentHistoryShouldBeFound("country.contains=" + DEFAULT_COUNTRY);

        // Get all the employmentHistoryList where country contains UPDATED_COUNTRY
        defaultEmploymentHistoryShouldNotBeFound("country.contains=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByCountryNotContainsSomething() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where country does not contain DEFAULT_COUNTRY
        defaultEmploymentHistoryShouldNotBeFound("country.doesNotContain=" + DEFAULT_COUNTRY);

        // Get all the employmentHistoryList where country does not contain UPDATED_COUNTRY
        defaultEmploymentHistoryShouldBeFound("country.doesNotContain=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByStartdateIsEqualToSomething() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where startdate equals to DEFAULT_STARTDATE
        defaultEmploymentHistoryShouldBeFound("startdate.equals=" + DEFAULT_STARTDATE);

        // Get all the employmentHistoryList where startdate equals to UPDATED_STARTDATE
        defaultEmploymentHistoryShouldNotBeFound("startdate.equals=" + UPDATED_STARTDATE);
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByStartdateIsInShouldWork() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where startdate in DEFAULT_STARTDATE or UPDATED_STARTDATE
        defaultEmploymentHistoryShouldBeFound("startdate.in=" + DEFAULT_STARTDATE + "," + UPDATED_STARTDATE);

        // Get all the employmentHistoryList where startdate equals to UPDATED_STARTDATE
        defaultEmploymentHistoryShouldNotBeFound("startdate.in=" + UPDATED_STARTDATE);
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByStartdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where startdate is not null
        defaultEmploymentHistoryShouldBeFound("startdate.specified=true");

        // Get all the employmentHistoryList where startdate is null
        defaultEmploymentHistoryShouldNotBeFound("startdate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByEnddateIsEqualToSomething() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where enddate equals to DEFAULT_ENDDATE
        defaultEmploymentHistoryShouldBeFound("enddate.equals=" + DEFAULT_ENDDATE);

        // Get all the employmentHistoryList where enddate equals to UPDATED_ENDDATE
        defaultEmploymentHistoryShouldNotBeFound("enddate.equals=" + UPDATED_ENDDATE);
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByEnddateIsInShouldWork() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where enddate in DEFAULT_ENDDATE or UPDATED_ENDDATE
        defaultEmploymentHistoryShouldBeFound("enddate.in=" + DEFAULT_ENDDATE + "," + UPDATED_ENDDATE);

        // Get all the employmentHistoryList where enddate equals to UPDATED_ENDDATE
        defaultEmploymentHistoryShouldNotBeFound("enddate.in=" + UPDATED_ENDDATE);
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByEnddateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where enddate is not null
        defaultEmploymentHistoryShouldBeFound("enddate.specified=true");

        // Get all the employmentHistoryList where enddate is null
        defaultEmploymentHistoryShouldNotBeFound("enddate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where createdat equals to DEFAULT_CREATEDAT
        defaultEmploymentHistoryShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the employmentHistoryList where createdat equals to UPDATED_CREATEDAT
        defaultEmploymentHistoryShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultEmploymentHistoryShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the employmentHistoryList where createdat equals to UPDATED_CREATEDAT
        defaultEmploymentHistoryShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where createdat is not null
        defaultEmploymentHistoryShouldBeFound("createdat.specified=true");

        // Get all the employmentHistoryList where createdat is null
        defaultEmploymentHistoryShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where updatedat equals to DEFAULT_UPDATEDAT
        defaultEmploymentHistoryShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the employmentHistoryList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmploymentHistoryShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultEmploymentHistoryShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the employmentHistoryList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmploymentHistoryShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where updatedat is not null
        defaultEmploymentHistoryShouldBeFound("updatedat.specified=true");

        // Get all the employmentHistoryList where updatedat is null
        defaultEmploymentHistoryShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByReasonforleavingIsEqualToSomething() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where reasonforleaving equals to DEFAULT_REASONFORLEAVING
        defaultEmploymentHistoryShouldBeFound("reasonforleaving.equals=" + DEFAULT_REASONFORLEAVING);

        // Get all the employmentHistoryList where reasonforleaving equals to UPDATED_REASONFORLEAVING
        defaultEmploymentHistoryShouldNotBeFound("reasonforleaving.equals=" + UPDATED_REASONFORLEAVING);
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByReasonforleavingIsInShouldWork() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where reasonforleaving in DEFAULT_REASONFORLEAVING or UPDATED_REASONFORLEAVING
        defaultEmploymentHistoryShouldBeFound("reasonforleaving.in=" + DEFAULT_REASONFORLEAVING + "," + UPDATED_REASONFORLEAVING);

        // Get all the employmentHistoryList where reasonforleaving equals to UPDATED_REASONFORLEAVING
        defaultEmploymentHistoryShouldNotBeFound("reasonforleaving.in=" + UPDATED_REASONFORLEAVING);
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByReasonforleavingIsNullOrNotNull() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where reasonforleaving is not null
        defaultEmploymentHistoryShouldBeFound("reasonforleaving.specified=true");

        // Get all the employmentHistoryList where reasonforleaving is null
        defaultEmploymentHistoryShouldNotBeFound("reasonforleaving.specified=false");
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByReasonforleavingContainsSomething() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where reasonforleaving contains DEFAULT_REASONFORLEAVING
        defaultEmploymentHistoryShouldBeFound("reasonforleaving.contains=" + DEFAULT_REASONFORLEAVING);

        // Get all the employmentHistoryList where reasonforleaving contains UPDATED_REASONFORLEAVING
        defaultEmploymentHistoryShouldNotBeFound("reasonforleaving.contains=" + UPDATED_REASONFORLEAVING);
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByReasonforleavingNotContainsSomething() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        // Get all the employmentHistoryList where reasonforleaving does not contain DEFAULT_REASONFORLEAVING
        defaultEmploymentHistoryShouldNotBeFound("reasonforleaving.doesNotContain=" + DEFAULT_REASONFORLEAVING);

        // Get all the employmentHistoryList where reasonforleaving does not contain UPDATED_REASONFORLEAVING
        defaultEmploymentHistoryShouldBeFound("reasonforleaving.doesNotContain=" + UPDATED_REASONFORLEAVING);
    }

    @Test
    @Transactional
    void getAllEmploymentHistoriesByEmployeeIsEqualToSomething() throws Exception {
        Employees employee;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employmentHistoryRepository.saveAndFlush(employmentHistory);
            employee = EmployeesResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employee);
        em.flush();
        employmentHistory.setEmployee(employee);
        employmentHistoryRepository.saveAndFlush(employmentHistory);
        Long employeeId = employee.getId();

        // Get all the employmentHistoryList where employee equals to employeeId
        defaultEmploymentHistoryShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the employmentHistoryList where employee equals to (employeeId + 1)
        defaultEmploymentHistoryShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmploymentHistoryShouldBeFound(String filter) throws Exception {
        restEmploymentHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employmentHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].positiontitle").value(hasItem(DEFAULT_POSITIONTITLE)))
            .andExpect(jsonPath("$.[*].companyname").value(hasItem(DEFAULT_COMPANYNAME)))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE)))
            .andExpect(jsonPath("$.[*].jobdescription").value(hasItem(DEFAULT_JOBDESCRIPTION)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].startdate").value(hasItem(DEFAULT_STARTDATE.toString())))
            .andExpect(jsonPath("$.[*].enddate").value(hasItem(DEFAULT_ENDDATE.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].reasonforleaving").value(hasItem(DEFAULT_REASONFORLEAVING)))
            .andExpect(jsonPath("$.[*].grosssalaryContentType").value(hasItem(DEFAULT_GROSSSALARY_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].grosssalary").value(hasItem(Base64Utils.encodeToString(DEFAULT_GROSSSALARY))));

        // Check, that the count call also returns 1
        restEmploymentHistoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmploymentHistoryShouldNotBeFound(String filter) throws Exception {
        restEmploymentHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmploymentHistoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmploymentHistory() throws Exception {
        // Get the employmentHistory
        restEmploymentHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmploymentHistory() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        int databaseSizeBeforeUpdate = employmentHistoryRepository.findAll().size();

        // Update the employmentHistory
        EmploymentHistory updatedEmploymentHistory = employmentHistoryRepository.findById(employmentHistory.getId()).get();
        // Disconnect from session so that the updates on updatedEmploymentHistory are not directly saved in db
        em.detach(updatedEmploymentHistory);
        updatedEmploymentHistory
            .positiontitle(UPDATED_POSITIONTITLE)
            .companyname(UPDATED_COMPANYNAME)
            .grade(UPDATED_GRADE)
            .jobdescription(UPDATED_JOBDESCRIPTION)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY)
            .startdate(UPDATED_STARTDATE)
            .enddate(UPDATED_ENDDATE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .reasonforleaving(UPDATED_REASONFORLEAVING)
            .grosssalary(UPDATED_GROSSSALARY)
            .grosssalaryContentType(UPDATED_GROSSSALARY_CONTENT_TYPE);

        restEmploymentHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmploymentHistory.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmploymentHistory))
            )
            .andExpect(status().isOk());

        // Validate the EmploymentHistory in the database
        List<EmploymentHistory> employmentHistoryList = employmentHistoryRepository.findAll();
        assertThat(employmentHistoryList).hasSize(databaseSizeBeforeUpdate);
        EmploymentHistory testEmploymentHistory = employmentHistoryList.get(employmentHistoryList.size() - 1);
        assertThat(testEmploymentHistory.getPositiontitle()).isEqualTo(UPDATED_POSITIONTITLE);
        assertThat(testEmploymentHistory.getCompanyname()).isEqualTo(UPDATED_COMPANYNAME);
        assertThat(testEmploymentHistory.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testEmploymentHistory.getJobdescription()).isEqualTo(UPDATED_JOBDESCRIPTION);
        assertThat(testEmploymentHistory.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testEmploymentHistory.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testEmploymentHistory.getStartdate()).isEqualTo(UPDATED_STARTDATE);
        assertThat(testEmploymentHistory.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testEmploymentHistory.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmploymentHistory.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testEmploymentHistory.getReasonforleaving()).isEqualTo(UPDATED_REASONFORLEAVING);
        assertThat(testEmploymentHistory.getGrosssalary()).isEqualTo(UPDATED_GROSSSALARY);
        assertThat(testEmploymentHistory.getGrosssalaryContentType()).isEqualTo(UPDATED_GROSSSALARY_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingEmploymentHistory() throws Exception {
        int databaseSizeBeforeUpdate = employmentHistoryRepository.findAll().size();
        employmentHistory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmploymentHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employmentHistory.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employmentHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmploymentHistory in the database
        List<EmploymentHistory> employmentHistoryList = employmentHistoryRepository.findAll();
        assertThat(employmentHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmploymentHistory() throws Exception {
        int databaseSizeBeforeUpdate = employmentHistoryRepository.findAll().size();
        employmentHistory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmploymentHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employmentHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmploymentHistory in the database
        List<EmploymentHistory> employmentHistoryList = employmentHistoryRepository.findAll();
        assertThat(employmentHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmploymentHistory() throws Exception {
        int databaseSizeBeforeUpdate = employmentHistoryRepository.findAll().size();
        employmentHistory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmploymentHistoryMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employmentHistory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmploymentHistory in the database
        List<EmploymentHistory> employmentHistoryList = employmentHistoryRepository.findAll();
        assertThat(employmentHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmploymentHistoryWithPatch() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        int databaseSizeBeforeUpdate = employmentHistoryRepository.findAll().size();

        // Update the employmentHistory using partial update
        EmploymentHistory partialUpdatedEmploymentHistory = new EmploymentHistory();
        partialUpdatedEmploymentHistory.setId(employmentHistory.getId());

        partialUpdatedEmploymentHistory
            .positiontitle(UPDATED_POSITIONTITLE)
            .companyname(UPDATED_COMPANYNAME)
            .jobdescription(UPDATED_JOBDESCRIPTION)
            .city(UPDATED_CITY)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .reasonforleaving(UPDATED_REASONFORLEAVING);

        restEmploymentHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmploymentHistory.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmploymentHistory))
            )
            .andExpect(status().isOk());

        // Validate the EmploymentHistory in the database
        List<EmploymentHistory> employmentHistoryList = employmentHistoryRepository.findAll();
        assertThat(employmentHistoryList).hasSize(databaseSizeBeforeUpdate);
        EmploymentHistory testEmploymentHistory = employmentHistoryList.get(employmentHistoryList.size() - 1);
        assertThat(testEmploymentHistory.getPositiontitle()).isEqualTo(UPDATED_POSITIONTITLE);
        assertThat(testEmploymentHistory.getCompanyname()).isEqualTo(UPDATED_COMPANYNAME);
        assertThat(testEmploymentHistory.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testEmploymentHistory.getJobdescription()).isEqualTo(UPDATED_JOBDESCRIPTION);
        assertThat(testEmploymentHistory.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testEmploymentHistory.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testEmploymentHistory.getStartdate()).isEqualTo(DEFAULT_STARTDATE);
        assertThat(testEmploymentHistory.getEnddate()).isEqualTo(DEFAULT_ENDDATE);
        assertThat(testEmploymentHistory.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmploymentHistory.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testEmploymentHistory.getReasonforleaving()).isEqualTo(UPDATED_REASONFORLEAVING);
        assertThat(testEmploymentHistory.getGrosssalary()).isEqualTo(DEFAULT_GROSSSALARY);
        assertThat(testEmploymentHistory.getGrosssalaryContentType()).isEqualTo(DEFAULT_GROSSSALARY_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateEmploymentHistoryWithPatch() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        int databaseSizeBeforeUpdate = employmentHistoryRepository.findAll().size();

        // Update the employmentHistory using partial update
        EmploymentHistory partialUpdatedEmploymentHistory = new EmploymentHistory();
        partialUpdatedEmploymentHistory.setId(employmentHistory.getId());

        partialUpdatedEmploymentHistory
            .positiontitle(UPDATED_POSITIONTITLE)
            .companyname(UPDATED_COMPANYNAME)
            .grade(UPDATED_GRADE)
            .jobdescription(UPDATED_JOBDESCRIPTION)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY)
            .startdate(UPDATED_STARTDATE)
            .enddate(UPDATED_ENDDATE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .reasonforleaving(UPDATED_REASONFORLEAVING)
            .grosssalary(UPDATED_GROSSSALARY)
            .grosssalaryContentType(UPDATED_GROSSSALARY_CONTENT_TYPE);

        restEmploymentHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmploymentHistory.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmploymentHistory))
            )
            .andExpect(status().isOk());

        // Validate the EmploymentHistory in the database
        List<EmploymentHistory> employmentHistoryList = employmentHistoryRepository.findAll();
        assertThat(employmentHistoryList).hasSize(databaseSizeBeforeUpdate);
        EmploymentHistory testEmploymentHistory = employmentHistoryList.get(employmentHistoryList.size() - 1);
        assertThat(testEmploymentHistory.getPositiontitle()).isEqualTo(UPDATED_POSITIONTITLE);
        assertThat(testEmploymentHistory.getCompanyname()).isEqualTo(UPDATED_COMPANYNAME);
        assertThat(testEmploymentHistory.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testEmploymentHistory.getJobdescription()).isEqualTo(UPDATED_JOBDESCRIPTION);
        assertThat(testEmploymentHistory.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testEmploymentHistory.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testEmploymentHistory.getStartdate()).isEqualTo(UPDATED_STARTDATE);
        assertThat(testEmploymentHistory.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testEmploymentHistory.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmploymentHistory.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testEmploymentHistory.getReasonforleaving()).isEqualTo(UPDATED_REASONFORLEAVING);
        assertThat(testEmploymentHistory.getGrosssalary()).isEqualTo(UPDATED_GROSSSALARY);
        assertThat(testEmploymentHistory.getGrosssalaryContentType()).isEqualTo(UPDATED_GROSSSALARY_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingEmploymentHistory() throws Exception {
        int databaseSizeBeforeUpdate = employmentHistoryRepository.findAll().size();
        employmentHistory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmploymentHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employmentHistory.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employmentHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmploymentHistory in the database
        List<EmploymentHistory> employmentHistoryList = employmentHistoryRepository.findAll();
        assertThat(employmentHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmploymentHistory() throws Exception {
        int databaseSizeBeforeUpdate = employmentHistoryRepository.findAll().size();
        employmentHistory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmploymentHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employmentHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmploymentHistory in the database
        List<EmploymentHistory> employmentHistoryList = employmentHistoryRepository.findAll();
        assertThat(employmentHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmploymentHistory() throws Exception {
        int databaseSizeBeforeUpdate = employmentHistoryRepository.findAll().size();
        employmentHistory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmploymentHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employmentHistory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmploymentHistory in the database
        List<EmploymentHistory> employmentHistoryList = employmentHistoryRepository.findAll();
        assertThat(employmentHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmploymentHistory() throws Exception {
        // Initialize the database
        employmentHistoryRepository.saveAndFlush(employmentHistory);

        int databaseSizeBeforeDelete = employmentHistoryRepository.findAll().size();

        // Delete the employmentHistory
        restEmploymentHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, employmentHistory.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmploymentHistory> employmentHistoryList = employmentHistoryRepository.findAll();
        assertThat(employmentHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
