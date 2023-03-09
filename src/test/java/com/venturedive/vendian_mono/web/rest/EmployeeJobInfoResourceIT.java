package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.BusinessUnits;
import com.venturedive.vendian_mono.domain.Competencies;
import com.venturedive.vendian_mono.domain.Departments;
import com.venturedive.vendian_mono.domain.DesignationJobDescriptions;
import com.venturedive.vendian_mono.domain.Designations;
import com.venturedive.vendian_mono.domain.Divisions;
import com.venturedive.vendian_mono.domain.EmployeeJobInfo;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.EmploymentTypes;
import com.venturedive.vendian_mono.repository.EmployeeJobInfoRepository;
import com.venturedive.vendian_mono.service.EmployeeJobInfoService;
import com.venturedive.vendian_mono.service.criteria.EmployeeJobInfoCriteria;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link EmployeeJobInfoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EmployeeJobInfoResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_GRADE = "AAAAAAAAAA";
    private static final String UPDATED_GRADE = "BBBBBBBBBB";

    private static final String DEFAULT_SUBGRADE = "AAAAAAAAAA";
    private static final String UPDATED_SUBGRADE = "BBBBBBBBBB";

    private static final Instant DEFAULT_STARTDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STARTDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ENDDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ENDDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_LOCATION = 1;
    private static final Integer UPDATED_LOCATION = 2;
    private static final Integer SMALLER_LOCATION = 1 - 1;

    private static final byte[] DEFAULT_GROSSSALARY = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_GROSSSALARY = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_GROSSSALARY_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_GROSSSALARY_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_FUELALLOWANCE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FUELALLOWANCE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FUELALLOWANCE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FUELALLOWANCE_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/employee-job-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeeJobInfoRepository employeeJobInfoRepository;

    @Mock
    private EmployeeJobInfoRepository employeeJobInfoRepositoryMock;

    @Mock
    private EmployeeJobInfoService employeeJobInfoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeJobInfoMockMvc;

    private EmployeeJobInfo employeeJobInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeJobInfo createEntity(EntityManager em) {
        EmployeeJobInfo employeeJobInfo = new EmployeeJobInfo()
            .title(DEFAULT_TITLE)
            .grade(DEFAULT_GRADE)
            .subgrade(DEFAULT_SUBGRADE)
            .startdate(DEFAULT_STARTDATE)
            .enddate(DEFAULT_ENDDATE)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT)
            .location(DEFAULT_LOCATION)
            .grosssalary(DEFAULT_GROSSSALARY)
            .grosssalaryContentType(DEFAULT_GROSSSALARY_CONTENT_TYPE)
            .fuelallowance(DEFAULT_FUELALLOWANCE)
            .fuelallowanceContentType(DEFAULT_FUELALLOWANCE_CONTENT_TYPE);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        employeeJobInfo.setEmployee(employees);
        // Add required entity
        Designations designations;
        if (TestUtil.findAll(em, Designations.class).isEmpty()) {
            designations = DesignationsResourceIT.createEntity(em);
            em.persist(designations);
            em.flush();
        } else {
            designations = TestUtil.findAll(em, Designations.class).get(0);
        }
        employeeJobInfo.setDesignation(designations);
        // Add required entity
        Departments departments;
        if (TestUtil.findAll(em, Departments.class).isEmpty()) {
            departments = DepartmentsResourceIT.createEntity(em);
            em.persist(departments);
            em.flush();
        } else {
            departments = TestUtil.findAll(em, Departments.class).get(0);
        }
        employeeJobInfo.setDepartment(departments);
        return employeeJobInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeJobInfo createUpdatedEntity(EntityManager em) {
        EmployeeJobInfo employeeJobInfo = new EmployeeJobInfo()
            .title(UPDATED_TITLE)
            .grade(UPDATED_GRADE)
            .subgrade(UPDATED_SUBGRADE)
            .startdate(UPDATED_STARTDATE)
            .enddate(UPDATED_ENDDATE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .location(UPDATED_LOCATION)
            .grosssalary(UPDATED_GROSSSALARY)
            .grosssalaryContentType(UPDATED_GROSSSALARY_CONTENT_TYPE)
            .fuelallowance(UPDATED_FUELALLOWANCE)
            .fuelallowanceContentType(UPDATED_FUELALLOWANCE_CONTENT_TYPE);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createUpdatedEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        employeeJobInfo.setEmployee(employees);
        // Add required entity
        Designations designations;
        if (TestUtil.findAll(em, Designations.class).isEmpty()) {
            designations = DesignationsResourceIT.createUpdatedEntity(em);
            em.persist(designations);
            em.flush();
        } else {
            designations = TestUtil.findAll(em, Designations.class).get(0);
        }
        employeeJobInfo.setDesignation(designations);
        // Add required entity
        Departments departments;
        if (TestUtil.findAll(em, Departments.class).isEmpty()) {
            departments = DepartmentsResourceIT.createUpdatedEntity(em);
            em.persist(departments);
            em.flush();
        } else {
            departments = TestUtil.findAll(em, Departments.class).get(0);
        }
        employeeJobInfo.setDepartment(departments);
        return employeeJobInfo;
    }

    @BeforeEach
    public void initTest() {
        employeeJobInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployeeJobInfo() throws Exception {
        int databaseSizeBeforeCreate = employeeJobInfoRepository.findAll().size();
        // Create the EmployeeJobInfo
        restEmployeeJobInfoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeJobInfo))
            )
            .andExpect(status().isCreated());

        // Validate the EmployeeJobInfo in the database
        List<EmployeeJobInfo> employeeJobInfoList = employeeJobInfoRepository.findAll();
        assertThat(employeeJobInfoList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeJobInfo testEmployeeJobInfo = employeeJobInfoList.get(employeeJobInfoList.size() - 1);
        assertThat(testEmployeeJobInfo.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testEmployeeJobInfo.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testEmployeeJobInfo.getSubgrade()).isEqualTo(DEFAULT_SUBGRADE);
        assertThat(testEmployeeJobInfo.getStartdate()).isEqualTo(DEFAULT_STARTDATE);
        assertThat(testEmployeeJobInfo.getEnddate()).isEqualTo(DEFAULT_ENDDATE);
        assertThat(testEmployeeJobInfo.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testEmployeeJobInfo.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testEmployeeJobInfo.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testEmployeeJobInfo.getGrosssalary()).isEqualTo(DEFAULT_GROSSSALARY);
        assertThat(testEmployeeJobInfo.getGrosssalaryContentType()).isEqualTo(DEFAULT_GROSSSALARY_CONTENT_TYPE);
        assertThat(testEmployeeJobInfo.getFuelallowance()).isEqualTo(DEFAULT_FUELALLOWANCE);
        assertThat(testEmployeeJobInfo.getFuelallowanceContentType()).isEqualTo(DEFAULT_FUELALLOWANCE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createEmployeeJobInfoWithExistingId() throws Exception {
        // Create the EmployeeJobInfo with an existing ID
        employeeJobInfo.setId(1L);

        int databaseSizeBeforeCreate = employeeJobInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeJobInfoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeJobInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeJobInfo in the database
        List<EmployeeJobInfo> employeeJobInfoList = employeeJobInfoRepository.findAll();
        assertThat(employeeJobInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeJobInfoRepository.findAll().size();
        // set the field null
        employeeJobInfo.setTitle(null);

        // Create the EmployeeJobInfo, which fails.

        restEmployeeJobInfoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeJobInfo))
            )
            .andExpect(status().isBadRequest());

        List<EmployeeJobInfo> employeeJobInfoList = employeeJobInfoRepository.findAll();
        assertThat(employeeJobInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGradeIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeJobInfoRepository.findAll().size();
        // set the field null
        employeeJobInfo.setGrade(null);

        // Create the EmployeeJobInfo, which fails.

        restEmployeeJobInfoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeJobInfo))
            )
            .andExpect(status().isBadRequest());

        List<EmployeeJobInfo> employeeJobInfoList = employeeJobInfoRepository.findAll();
        assertThat(employeeJobInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSubgradeIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeJobInfoRepository.findAll().size();
        // set the field null
        employeeJobInfo.setSubgrade(null);

        // Create the EmployeeJobInfo, which fails.

        restEmployeeJobInfoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeJobInfo))
            )
            .andExpect(status().isBadRequest());

        List<EmployeeJobInfo> employeeJobInfoList = employeeJobInfoRepository.findAll();
        assertThat(employeeJobInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStartdateIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeJobInfoRepository.findAll().size();
        // set the field null
        employeeJobInfo.setStartdate(null);

        // Create the EmployeeJobInfo, which fails.

        restEmployeeJobInfoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeJobInfo))
            )
            .andExpect(status().isBadRequest());

        List<EmployeeJobInfo> employeeJobInfoList = employeeJobInfoRepository.findAll();
        assertThat(employeeJobInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfos() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        // Get all the employeeJobInfoList
        restEmployeeJobInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeJobInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE)))
            .andExpect(jsonPath("$.[*].subgrade").value(hasItem(DEFAULT_SUBGRADE)))
            .andExpect(jsonPath("$.[*].startdate").value(hasItem(DEFAULT_STARTDATE.toString())))
            .andExpect(jsonPath("$.[*].enddate").value(hasItem(DEFAULT_ENDDATE.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].grosssalaryContentType").value(hasItem(DEFAULT_GROSSSALARY_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].grosssalary").value(hasItem(Base64Utils.encodeToString(DEFAULT_GROSSSALARY))))
            .andExpect(jsonPath("$.[*].fuelallowanceContentType").value(hasItem(DEFAULT_FUELALLOWANCE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fuelallowance").value(hasItem(Base64Utils.encodeToString(DEFAULT_FUELALLOWANCE))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEmployeeJobInfosWithEagerRelationshipsIsEnabled() throws Exception {
        when(employeeJobInfoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEmployeeJobInfoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(employeeJobInfoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEmployeeJobInfosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(employeeJobInfoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEmployeeJobInfoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(employeeJobInfoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getEmployeeJobInfo() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        // Get the employeeJobInfo
        restEmployeeJobInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, employeeJobInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeJobInfo.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE))
            .andExpect(jsonPath("$.subgrade").value(DEFAULT_SUBGRADE))
            .andExpect(jsonPath("$.startdate").value(DEFAULT_STARTDATE.toString()))
            .andExpect(jsonPath("$.enddate").value(DEFAULT_ENDDATE.toString()))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.grosssalaryContentType").value(DEFAULT_GROSSSALARY_CONTENT_TYPE))
            .andExpect(jsonPath("$.grosssalary").value(Base64Utils.encodeToString(DEFAULT_GROSSSALARY)))
            .andExpect(jsonPath("$.fuelallowanceContentType").value(DEFAULT_FUELALLOWANCE_CONTENT_TYPE))
            .andExpect(jsonPath("$.fuelallowance").value(Base64Utils.encodeToString(DEFAULT_FUELALLOWANCE)));
    }

    @Test
    @Transactional
    void getEmployeeJobInfosByIdFiltering() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        Long id = employeeJobInfo.getId();

        defaultEmployeeJobInfoShouldBeFound("id.equals=" + id);
        defaultEmployeeJobInfoShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeJobInfoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeJobInfoShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeJobInfoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeJobInfoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        // Get all the employeeJobInfoList where title equals to DEFAULT_TITLE
        defaultEmployeeJobInfoShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the employeeJobInfoList where title equals to UPDATED_TITLE
        defaultEmployeeJobInfoShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        // Get all the employeeJobInfoList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultEmployeeJobInfoShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the employeeJobInfoList where title equals to UPDATED_TITLE
        defaultEmployeeJobInfoShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        // Get all the employeeJobInfoList where title is not null
        defaultEmployeeJobInfoShouldBeFound("title.specified=true");

        // Get all the employeeJobInfoList where title is null
        defaultEmployeeJobInfoShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosByTitleContainsSomething() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        // Get all the employeeJobInfoList where title contains DEFAULT_TITLE
        defaultEmployeeJobInfoShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the employeeJobInfoList where title contains UPDATED_TITLE
        defaultEmployeeJobInfoShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        // Get all the employeeJobInfoList where title does not contain DEFAULT_TITLE
        defaultEmployeeJobInfoShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the employeeJobInfoList where title does not contain UPDATED_TITLE
        defaultEmployeeJobInfoShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosByGradeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        // Get all the employeeJobInfoList where grade equals to DEFAULT_GRADE
        defaultEmployeeJobInfoShouldBeFound("grade.equals=" + DEFAULT_GRADE);

        // Get all the employeeJobInfoList where grade equals to UPDATED_GRADE
        defaultEmployeeJobInfoShouldNotBeFound("grade.equals=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosByGradeIsInShouldWork() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        // Get all the employeeJobInfoList where grade in DEFAULT_GRADE or UPDATED_GRADE
        defaultEmployeeJobInfoShouldBeFound("grade.in=" + DEFAULT_GRADE + "," + UPDATED_GRADE);

        // Get all the employeeJobInfoList where grade equals to UPDATED_GRADE
        defaultEmployeeJobInfoShouldNotBeFound("grade.in=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosByGradeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        // Get all the employeeJobInfoList where grade is not null
        defaultEmployeeJobInfoShouldBeFound("grade.specified=true");

        // Get all the employeeJobInfoList where grade is null
        defaultEmployeeJobInfoShouldNotBeFound("grade.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosByGradeContainsSomething() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        // Get all the employeeJobInfoList where grade contains DEFAULT_GRADE
        defaultEmployeeJobInfoShouldBeFound("grade.contains=" + DEFAULT_GRADE);

        // Get all the employeeJobInfoList where grade contains UPDATED_GRADE
        defaultEmployeeJobInfoShouldNotBeFound("grade.contains=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosByGradeNotContainsSomething() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        // Get all the employeeJobInfoList where grade does not contain DEFAULT_GRADE
        defaultEmployeeJobInfoShouldNotBeFound("grade.doesNotContain=" + DEFAULT_GRADE);

        // Get all the employeeJobInfoList where grade does not contain UPDATED_GRADE
        defaultEmployeeJobInfoShouldBeFound("grade.doesNotContain=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosBySubgradeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        // Get all the employeeJobInfoList where subgrade equals to DEFAULT_SUBGRADE
        defaultEmployeeJobInfoShouldBeFound("subgrade.equals=" + DEFAULT_SUBGRADE);

        // Get all the employeeJobInfoList where subgrade equals to UPDATED_SUBGRADE
        defaultEmployeeJobInfoShouldNotBeFound("subgrade.equals=" + UPDATED_SUBGRADE);
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosBySubgradeIsInShouldWork() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        // Get all the employeeJobInfoList where subgrade in DEFAULT_SUBGRADE or UPDATED_SUBGRADE
        defaultEmployeeJobInfoShouldBeFound("subgrade.in=" + DEFAULT_SUBGRADE + "," + UPDATED_SUBGRADE);

        // Get all the employeeJobInfoList where subgrade equals to UPDATED_SUBGRADE
        defaultEmployeeJobInfoShouldNotBeFound("subgrade.in=" + UPDATED_SUBGRADE);
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosBySubgradeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        // Get all the employeeJobInfoList where subgrade is not null
        defaultEmployeeJobInfoShouldBeFound("subgrade.specified=true");

        // Get all the employeeJobInfoList where subgrade is null
        defaultEmployeeJobInfoShouldNotBeFound("subgrade.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosBySubgradeContainsSomething() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        // Get all the employeeJobInfoList where subgrade contains DEFAULT_SUBGRADE
        defaultEmployeeJobInfoShouldBeFound("subgrade.contains=" + DEFAULT_SUBGRADE);

        // Get all the employeeJobInfoList where subgrade contains UPDATED_SUBGRADE
        defaultEmployeeJobInfoShouldNotBeFound("subgrade.contains=" + UPDATED_SUBGRADE);
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosBySubgradeNotContainsSomething() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        // Get all the employeeJobInfoList where subgrade does not contain DEFAULT_SUBGRADE
        defaultEmployeeJobInfoShouldNotBeFound("subgrade.doesNotContain=" + DEFAULT_SUBGRADE);

        // Get all the employeeJobInfoList where subgrade does not contain UPDATED_SUBGRADE
        defaultEmployeeJobInfoShouldBeFound("subgrade.doesNotContain=" + UPDATED_SUBGRADE);
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosByStartdateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        // Get all the employeeJobInfoList where startdate equals to DEFAULT_STARTDATE
        defaultEmployeeJobInfoShouldBeFound("startdate.equals=" + DEFAULT_STARTDATE);

        // Get all the employeeJobInfoList where startdate equals to UPDATED_STARTDATE
        defaultEmployeeJobInfoShouldNotBeFound("startdate.equals=" + UPDATED_STARTDATE);
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosByStartdateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        // Get all the employeeJobInfoList where startdate in DEFAULT_STARTDATE or UPDATED_STARTDATE
        defaultEmployeeJobInfoShouldBeFound("startdate.in=" + DEFAULT_STARTDATE + "," + UPDATED_STARTDATE);

        // Get all the employeeJobInfoList where startdate equals to UPDATED_STARTDATE
        defaultEmployeeJobInfoShouldNotBeFound("startdate.in=" + UPDATED_STARTDATE);
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosByStartdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        // Get all the employeeJobInfoList where startdate is not null
        defaultEmployeeJobInfoShouldBeFound("startdate.specified=true");

        // Get all the employeeJobInfoList where startdate is null
        defaultEmployeeJobInfoShouldNotBeFound("startdate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosByEnddateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        // Get all the employeeJobInfoList where enddate equals to DEFAULT_ENDDATE
        defaultEmployeeJobInfoShouldBeFound("enddate.equals=" + DEFAULT_ENDDATE);

        // Get all the employeeJobInfoList where enddate equals to UPDATED_ENDDATE
        defaultEmployeeJobInfoShouldNotBeFound("enddate.equals=" + UPDATED_ENDDATE);
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosByEnddateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        // Get all the employeeJobInfoList where enddate in DEFAULT_ENDDATE or UPDATED_ENDDATE
        defaultEmployeeJobInfoShouldBeFound("enddate.in=" + DEFAULT_ENDDATE + "," + UPDATED_ENDDATE);

        // Get all the employeeJobInfoList where enddate equals to UPDATED_ENDDATE
        defaultEmployeeJobInfoShouldNotBeFound("enddate.in=" + UPDATED_ENDDATE);
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosByEnddateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        // Get all the employeeJobInfoList where enddate is not null
        defaultEmployeeJobInfoShouldBeFound("enddate.specified=true");

        // Get all the employeeJobInfoList where enddate is null
        defaultEmployeeJobInfoShouldNotBeFound("enddate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        // Get all the employeeJobInfoList where createdat equals to DEFAULT_CREATEDAT
        defaultEmployeeJobInfoShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the employeeJobInfoList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeJobInfoShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        // Get all the employeeJobInfoList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultEmployeeJobInfoShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the employeeJobInfoList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeJobInfoShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        // Get all the employeeJobInfoList where createdat is not null
        defaultEmployeeJobInfoShouldBeFound("createdat.specified=true");

        // Get all the employeeJobInfoList where createdat is null
        defaultEmployeeJobInfoShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        // Get all the employeeJobInfoList where updatedat equals to DEFAULT_UPDATEDAT
        defaultEmployeeJobInfoShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the employeeJobInfoList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeJobInfoShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        // Get all the employeeJobInfoList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultEmployeeJobInfoShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the employeeJobInfoList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeJobInfoShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        // Get all the employeeJobInfoList where updatedat is not null
        defaultEmployeeJobInfoShouldBeFound("updatedat.specified=true");

        // Get all the employeeJobInfoList where updatedat is null
        defaultEmployeeJobInfoShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        // Get all the employeeJobInfoList where location equals to DEFAULT_LOCATION
        defaultEmployeeJobInfoShouldBeFound("location.equals=" + DEFAULT_LOCATION);

        // Get all the employeeJobInfoList where location equals to UPDATED_LOCATION
        defaultEmployeeJobInfoShouldNotBeFound("location.equals=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosByLocationIsInShouldWork() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        // Get all the employeeJobInfoList where location in DEFAULT_LOCATION or UPDATED_LOCATION
        defaultEmployeeJobInfoShouldBeFound("location.in=" + DEFAULT_LOCATION + "," + UPDATED_LOCATION);

        // Get all the employeeJobInfoList where location equals to UPDATED_LOCATION
        defaultEmployeeJobInfoShouldNotBeFound("location.in=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosByLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        // Get all the employeeJobInfoList where location is not null
        defaultEmployeeJobInfoShouldBeFound("location.specified=true");

        // Get all the employeeJobInfoList where location is null
        defaultEmployeeJobInfoShouldNotBeFound("location.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosByLocationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        // Get all the employeeJobInfoList where location is greater than or equal to DEFAULT_LOCATION
        defaultEmployeeJobInfoShouldBeFound("location.greaterThanOrEqual=" + DEFAULT_LOCATION);

        // Get all the employeeJobInfoList where location is greater than or equal to UPDATED_LOCATION
        defaultEmployeeJobInfoShouldNotBeFound("location.greaterThanOrEqual=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosByLocationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        // Get all the employeeJobInfoList where location is less than or equal to DEFAULT_LOCATION
        defaultEmployeeJobInfoShouldBeFound("location.lessThanOrEqual=" + DEFAULT_LOCATION);

        // Get all the employeeJobInfoList where location is less than or equal to SMALLER_LOCATION
        defaultEmployeeJobInfoShouldNotBeFound("location.lessThanOrEqual=" + SMALLER_LOCATION);
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosByLocationIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        // Get all the employeeJobInfoList where location is less than DEFAULT_LOCATION
        defaultEmployeeJobInfoShouldNotBeFound("location.lessThan=" + DEFAULT_LOCATION);

        // Get all the employeeJobInfoList where location is less than UPDATED_LOCATION
        defaultEmployeeJobInfoShouldBeFound("location.lessThan=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosByLocationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        // Get all the employeeJobInfoList where location is greater than DEFAULT_LOCATION
        defaultEmployeeJobInfoShouldNotBeFound("location.greaterThan=" + DEFAULT_LOCATION);

        // Get all the employeeJobInfoList where location is greater than SMALLER_LOCATION
        defaultEmployeeJobInfoShouldBeFound("location.greaterThan=" + SMALLER_LOCATION);
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosByEmployeeIsEqualToSomething() throws Exception {
        Employees employee;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employeeJobInfoRepository.saveAndFlush(employeeJobInfo);
            employee = EmployeesResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employee);
        em.flush();
        employeeJobInfo.setEmployee(employee);
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);
        Long employeeId = employee.getId();

        // Get all the employeeJobInfoList where employee equals to employeeId
        defaultEmployeeJobInfoShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the employeeJobInfoList where employee equals to (employeeId + 1)
        defaultEmployeeJobInfoShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosByDesignationIsEqualToSomething() throws Exception {
        Designations designation;
        if (TestUtil.findAll(em, Designations.class).isEmpty()) {
            employeeJobInfoRepository.saveAndFlush(employeeJobInfo);
            designation = DesignationsResourceIT.createEntity(em);
        } else {
            designation = TestUtil.findAll(em, Designations.class).get(0);
        }
        em.persist(designation);
        em.flush();
        employeeJobInfo.setDesignation(designation);
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);
        Long designationId = designation.getId();

        // Get all the employeeJobInfoList where designation equals to designationId
        defaultEmployeeJobInfoShouldBeFound("designationId.equals=" + designationId);

        // Get all the employeeJobInfoList where designation equals to (designationId + 1)
        defaultEmployeeJobInfoShouldNotBeFound("designationId.equals=" + (designationId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosByReviewmanagerIsEqualToSomething() throws Exception {
        Employees reviewmanager;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employeeJobInfoRepository.saveAndFlush(employeeJobInfo);
            reviewmanager = EmployeesResourceIT.createEntity(em);
        } else {
            reviewmanager = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(reviewmanager);
        em.flush();
        employeeJobInfo.setReviewmanager(reviewmanager);
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);
        Long reviewmanagerId = reviewmanager.getId();

        // Get all the employeeJobInfoList where reviewmanager equals to reviewmanagerId
        defaultEmployeeJobInfoShouldBeFound("reviewmanagerId.equals=" + reviewmanagerId);

        // Get all the employeeJobInfoList where reviewmanager equals to (reviewmanagerId + 1)
        defaultEmployeeJobInfoShouldNotBeFound("reviewmanagerId.equals=" + (reviewmanagerId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosByManagerIsEqualToSomething() throws Exception {
        Employees manager;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employeeJobInfoRepository.saveAndFlush(employeeJobInfo);
            manager = EmployeesResourceIT.createEntity(em);
        } else {
            manager = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(manager);
        em.flush();
        employeeJobInfo.setManager(manager);
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);
        Long managerId = manager.getId();

        // Get all the employeeJobInfoList where manager equals to managerId
        defaultEmployeeJobInfoShouldBeFound("managerId.equals=" + managerId);

        // Get all the employeeJobInfoList where manager equals to (managerId + 1)
        defaultEmployeeJobInfoShouldNotBeFound("managerId.equals=" + (managerId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosByDepartmentIsEqualToSomething() throws Exception {
        Departments department;
        if (TestUtil.findAll(em, Departments.class).isEmpty()) {
            employeeJobInfoRepository.saveAndFlush(employeeJobInfo);
            department = DepartmentsResourceIT.createEntity(em);
        } else {
            department = TestUtil.findAll(em, Departments.class).get(0);
        }
        em.persist(department);
        em.flush();
        employeeJobInfo.setDepartment(department);
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);
        Long departmentId = department.getId();

        // Get all the employeeJobInfoList where department equals to departmentId
        defaultEmployeeJobInfoShouldBeFound("departmentId.equals=" + departmentId);

        // Get all the employeeJobInfoList where department equals to (departmentId + 1)
        defaultEmployeeJobInfoShouldNotBeFound("departmentId.equals=" + (departmentId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosByEmploymenttypeIsEqualToSomething() throws Exception {
        EmploymentTypes employmenttype;
        if (TestUtil.findAll(em, EmploymentTypes.class).isEmpty()) {
            employeeJobInfoRepository.saveAndFlush(employeeJobInfo);
            employmenttype = EmploymentTypesResourceIT.createEntity(em);
        } else {
            employmenttype = TestUtil.findAll(em, EmploymentTypes.class).get(0);
        }
        em.persist(employmenttype);
        em.flush();
        employeeJobInfo.setEmploymenttype(employmenttype);
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);
        Long employmenttypeId = employmenttype.getId();

        // Get all the employeeJobInfoList where employmenttype equals to employmenttypeId
        defaultEmployeeJobInfoShouldBeFound("employmenttypeId.equals=" + employmenttypeId);

        // Get all the employeeJobInfoList where employmenttype equals to (employmenttypeId + 1)
        defaultEmployeeJobInfoShouldNotBeFound("employmenttypeId.equals=" + (employmenttypeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosByJobdescriptionIsEqualToSomething() throws Exception {
        DesignationJobDescriptions jobdescription;
        if (TestUtil.findAll(em, DesignationJobDescriptions.class).isEmpty()) {
            employeeJobInfoRepository.saveAndFlush(employeeJobInfo);
            jobdescription = DesignationJobDescriptionsResourceIT.createEntity(em);
        } else {
            jobdescription = TestUtil.findAll(em, DesignationJobDescriptions.class).get(0);
        }
        em.persist(jobdescription);
        em.flush();
        employeeJobInfo.setJobdescription(jobdescription);
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);
        Long jobdescriptionId = jobdescription.getId();

        // Get all the employeeJobInfoList where jobdescription equals to jobdescriptionId
        defaultEmployeeJobInfoShouldBeFound("jobdescriptionId.equals=" + jobdescriptionId);

        // Get all the employeeJobInfoList where jobdescription equals to (jobdescriptionId + 1)
        defaultEmployeeJobInfoShouldNotBeFound("jobdescriptionId.equals=" + (jobdescriptionId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosByDivisionIsEqualToSomething() throws Exception {
        Divisions division;
        if (TestUtil.findAll(em, Divisions.class).isEmpty()) {
            employeeJobInfoRepository.saveAndFlush(employeeJobInfo);
            division = DivisionsResourceIT.createEntity(em);
        } else {
            division = TestUtil.findAll(em, Divisions.class).get(0);
        }
        em.persist(division);
        em.flush();
        employeeJobInfo.setDivision(division);
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);
        Long divisionId = division.getId();

        // Get all the employeeJobInfoList where division equals to divisionId
        defaultEmployeeJobInfoShouldBeFound("divisionId.equals=" + divisionId);

        // Get all the employeeJobInfoList where division equals to (divisionId + 1)
        defaultEmployeeJobInfoShouldNotBeFound("divisionId.equals=" + (divisionId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosByBusinessunitIsEqualToSomething() throws Exception {
        BusinessUnits businessunit;
        if (TestUtil.findAll(em, BusinessUnits.class).isEmpty()) {
            employeeJobInfoRepository.saveAndFlush(employeeJobInfo);
            businessunit = BusinessUnitsResourceIT.createEntity(em);
        } else {
            businessunit = TestUtil.findAll(em, BusinessUnits.class).get(0);
        }
        em.persist(businessunit);
        em.flush();
        employeeJobInfo.setBusinessunit(businessunit);
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);
        Long businessunitId = businessunit.getId();

        // Get all the employeeJobInfoList where businessunit equals to businessunitId
        defaultEmployeeJobInfoShouldBeFound("businessunitId.equals=" + businessunitId);

        // Get all the employeeJobInfoList where businessunit equals to (businessunitId + 1)
        defaultEmployeeJobInfoShouldNotBeFound("businessunitId.equals=" + (businessunitId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeeJobInfosByCompetencyIsEqualToSomething() throws Exception {
        Competencies competency;
        if (TestUtil.findAll(em, Competencies.class).isEmpty()) {
            employeeJobInfoRepository.saveAndFlush(employeeJobInfo);
            competency = CompetenciesResourceIT.createEntity(em);
        } else {
            competency = TestUtil.findAll(em, Competencies.class).get(0);
        }
        em.persist(competency);
        em.flush();
        employeeJobInfo.setCompetency(competency);
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);
        Long competencyId = competency.getId();

        // Get all the employeeJobInfoList where competency equals to competencyId
        defaultEmployeeJobInfoShouldBeFound("competencyId.equals=" + competencyId);

        // Get all the employeeJobInfoList where competency equals to (competencyId + 1)
        defaultEmployeeJobInfoShouldNotBeFound("competencyId.equals=" + (competencyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeJobInfoShouldBeFound(String filter) throws Exception {
        restEmployeeJobInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeJobInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE)))
            .andExpect(jsonPath("$.[*].subgrade").value(hasItem(DEFAULT_SUBGRADE)))
            .andExpect(jsonPath("$.[*].startdate").value(hasItem(DEFAULT_STARTDATE.toString())))
            .andExpect(jsonPath("$.[*].enddate").value(hasItem(DEFAULT_ENDDATE.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].grosssalaryContentType").value(hasItem(DEFAULT_GROSSSALARY_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].grosssalary").value(hasItem(Base64Utils.encodeToString(DEFAULT_GROSSSALARY))))
            .andExpect(jsonPath("$.[*].fuelallowanceContentType").value(hasItem(DEFAULT_FUELALLOWANCE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fuelallowance").value(hasItem(Base64Utils.encodeToString(DEFAULT_FUELALLOWANCE))));

        // Check, that the count call also returns 1
        restEmployeeJobInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeJobInfoShouldNotBeFound(String filter) throws Exception {
        restEmployeeJobInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeJobInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployeeJobInfo() throws Exception {
        // Get the employeeJobInfo
        restEmployeeJobInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmployeeJobInfo() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        int databaseSizeBeforeUpdate = employeeJobInfoRepository.findAll().size();

        // Update the employeeJobInfo
        EmployeeJobInfo updatedEmployeeJobInfo = employeeJobInfoRepository.findById(employeeJobInfo.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeJobInfo are not directly saved in db
        em.detach(updatedEmployeeJobInfo);
        updatedEmployeeJobInfo
            .title(UPDATED_TITLE)
            .grade(UPDATED_GRADE)
            .subgrade(UPDATED_SUBGRADE)
            .startdate(UPDATED_STARTDATE)
            .enddate(UPDATED_ENDDATE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .location(UPDATED_LOCATION)
            .grosssalary(UPDATED_GROSSSALARY)
            .grosssalaryContentType(UPDATED_GROSSSALARY_CONTENT_TYPE)
            .fuelallowance(UPDATED_FUELALLOWANCE)
            .fuelallowanceContentType(UPDATED_FUELALLOWANCE_CONTENT_TYPE);

        restEmployeeJobInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmployeeJobInfo.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeJobInfo))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeJobInfo in the database
        List<EmployeeJobInfo> employeeJobInfoList = employeeJobInfoRepository.findAll();
        assertThat(employeeJobInfoList).hasSize(databaseSizeBeforeUpdate);
        EmployeeJobInfo testEmployeeJobInfo = employeeJobInfoList.get(employeeJobInfoList.size() - 1);
        assertThat(testEmployeeJobInfo.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testEmployeeJobInfo.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testEmployeeJobInfo.getSubgrade()).isEqualTo(UPDATED_SUBGRADE);
        assertThat(testEmployeeJobInfo.getStartdate()).isEqualTo(UPDATED_STARTDATE);
        assertThat(testEmployeeJobInfo.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testEmployeeJobInfo.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeJobInfo.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testEmployeeJobInfo.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testEmployeeJobInfo.getGrosssalary()).isEqualTo(UPDATED_GROSSSALARY);
        assertThat(testEmployeeJobInfo.getGrosssalaryContentType()).isEqualTo(UPDATED_GROSSSALARY_CONTENT_TYPE);
        assertThat(testEmployeeJobInfo.getFuelallowance()).isEqualTo(UPDATED_FUELALLOWANCE);
        assertThat(testEmployeeJobInfo.getFuelallowanceContentType()).isEqualTo(UPDATED_FUELALLOWANCE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingEmployeeJobInfo() throws Exception {
        int databaseSizeBeforeUpdate = employeeJobInfoRepository.findAll().size();
        employeeJobInfo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeJobInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeJobInfo.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeJobInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeJobInfo in the database
        List<EmployeeJobInfo> employeeJobInfoList = employeeJobInfoRepository.findAll();
        assertThat(employeeJobInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployeeJobInfo() throws Exception {
        int databaseSizeBeforeUpdate = employeeJobInfoRepository.findAll().size();
        employeeJobInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeJobInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeJobInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeJobInfo in the database
        List<EmployeeJobInfo> employeeJobInfoList = employeeJobInfoRepository.findAll();
        assertThat(employeeJobInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployeeJobInfo() throws Exception {
        int databaseSizeBeforeUpdate = employeeJobInfoRepository.findAll().size();
        employeeJobInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeJobInfoMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeJobInfo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeJobInfo in the database
        List<EmployeeJobInfo> employeeJobInfoList = employeeJobInfoRepository.findAll();
        assertThat(employeeJobInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeJobInfoWithPatch() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        int databaseSizeBeforeUpdate = employeeJobInfoRepository.findAll().size();

        // Update the employeeJobInfo using partial update
        EmployeeJobInfo partialUpdatedEmployeeJobInfo = new EmployeeJobInfo();
        partialUpdatedEmployeeJobInfo.setId(employeeJobInfo.getId());

        partialUpdatedEmployeeJobInfo
            .title(UPDATED_TITLE)
            .subgrade(UPDATED_SUBGRADE)
            .startdate(UPDATED_STARTDATE)
            .enddate(UPDATED_ENDDATE)
            .fuelallowance(UPDATED_FUELALLOWANCE)
            .fuelallowanceContentType(UPDATED_FUELALLOWANCE_CONTENT_TYPE);

        restEmployeeJobInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeJobInfo.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeJobInfo))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeJobInfo in the database
        List<EmployeeJobInfo> employeeJobInfoList = employeeJobInfoRepository.findAll();
        assertThat(employeeJobInfoList).hasSize(databaseSizeBeforeUpdate);
        EmployeeJobInfo testEmployeeJobInfo = employeeJobInfoList.get(employeeJobInfoList.size() - 1);
        assertThat(testEmployeeJobInfo.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testEmployeeJobInfo.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testEmployeeJobInfo.getSubgrade()).isEqualTo(UPDATED_SUBGRADE);
        assertThat(testEmployeeJobInfo.getStartdate()).isEqualTo(UPDATED_STARTDATE);
        assertThat(testEmployeeJobInfo.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testEmployeeJobInfo.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testEmployeeJobInfo.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testEmployeeJobInfo.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testEmployeeJobInfo.getGrosssalary()).isEqualTo(DEFAULT_GROSSSALARY);
        assertThat(testEmployeeJobInfo.getGrosssalaryContentType()).isEqualTo(DEFAULT_GROSSSALARY_CONTENT_TYPE);
        assertThat(testEmployeeJobInfo.getFuelallowance()).isEqualTo(UPDATED_FUELALLOWANCE);
        assertThat(testEmployeeJobInfo.getFuelallowanceContentType()).isEqualTo(UPDATED_FUELALLOWANCE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateEmployeeJobInfoWithPatch() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        int databaseSizeBeforeUpdate = employeeJobInfoRepository.findAll().size();

        // Update the employeeJobInfo using partial update
        EmployeeJobInfo partialUpdatedEmployeeJobInfo = new EmployeeJobInfo();
        partialUpdatedEmployeeJobInfo.setId(employeeJobInfo.getId());

        partialUpdatedEmployeeJobInfo
            .title(UPDATED_TITLE)
            .grade(UPDATED_GRADE)
            .subgrade(UPDATED_SUBGRADE)
            .startdate(UPDATED_STARTDATE)
            .enddate(UPDATED_ENDDATE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .location(UPDATED_LOCATION)
            .grosssalary(UPDATED_GROSSSALARY)
            .grosssalaryContentType(UPDATED_GROSSSALARY_CONTENT_TYPE)
            .fuelallowance(UPDATED_FUELALLOWANCE)
            .fuelallowanceContentType(UPDATED_FUELALLOWANCE_CONTENT_TYPE);

        restEmployeeJobInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeJobInfo.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeJobInfo))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeJobInfo in the database
        List<EmployeeJobInfo> employeeJobInfoList = employeeJobInfoRepository.findAll();
        assertThat(employeeJobInfoList).hasSize(databaseSizeBeforeUpdate);
        EmployeeJobInfo testEmployeeJobInfo = employeeJobInfoList.get(employeeJobInfoList.size() - 1);
        assertThat(testEmployeeJobInfo.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testEmployeeJobInfo.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testEmployeeJobInfo.getSubgrade()).isEqualTo(UPDATED_SUBGRADE);
        assertThat(testEmployeeJobInfo.getStartdate()).isEqualTo(UPDATED_STARTDATE);
        assertThat(testEmployeeJobInfo.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testEmployeeJobInfo.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeJobInfo.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testEmployeeJobInfo.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testEmployeeJobInfo.getGrosssalary()).isEqualTo(UPDATED_GROSSSALARY);
        assertThat(testEmployeeJobInfo.getGrosssalaryContentType()).isEqualTo(UPDATED_GROSSSALARY_CONTENT_TYPE);
        assertThat(testEmployeeJobInfo.getFuelallowance()).isEqualTo(UPDATED_FUELALLOWANCE);
        assertThat(testEmployeeJobInfo.getFuelallowanceContentType()).isEqualTo(UPDATED_FUELALLOWANCE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingEmployeeJobInfo() throws Exception {
        int databaseSizeBeforeUpdate = employeeJobInfoRepository.findAll().size();
        employeeJobInfo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeJobInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employeeJobInfo.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeJobInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeJobInfo in the database
        List<EmployeeJobInfo> employeeJobInfoList = employeeJobInfoRepository.findAll();
        assertThat(employeeJobInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployeeJobInfo() throws Exception {
        int databaseSizeBeforeUpdate = employeeJobInfoRepository.findAll().size();
        employeeJobInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeJobInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeJobInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeJobInfo in the database
        List<EmployeeJobInfo> employeeJobInfoList = employeeJobInfoRepository.findAll();
        assertThat(employeeJobInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployeeJobInfo() throws Exception {
        int databaseSizeBeforeUpdate = employeeJobInfoRepository.findAll().size();
        employeeJobInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeJobInfoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeJobInfo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeJobInfo in the database
        List<EmployeeJobInfo> employeeJobInfoList = employeeJobInfoRepository.findAll();
        assertThat(employeeJobInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployeeJobInfo() throws Exception {
        // Initialize the database
        employeeJobInfoRepository.saveAndFlush(employeeJobInfo);

        int databaseSizeBeforeDelete = employeeJobInfoRepository.findAll().size();

        // Delete the employeeJobInfo
        restEmployeeJobInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, employeeJobInfo.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeJobInfo> employeeJobInfoList = employeeJobInfoRepository.findAll();
        assertThat(employeeJobInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
