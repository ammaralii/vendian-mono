package com.venturedive.vendian_mono.web.rest;

import static com.venturedive.vendian_mono.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.BusinessUnits;
import com.venturedive.vendian_mono.domain.ClaimRequestViews;
import com.venturedive.vendian_mono.domain.ClaimRequests;
import com.venturedive.vendian_mono.domain.Competencies;
import com.venturedive.vendian_mono.domain.DealResources;
import com.venturedive.vendian_mono.domain.Departments;
import com.venturedive.vendian_mono.domain.Designations;
import com.venturedive.vendian_mono.domain.Divisions;
import com.venturedive.vendian_mono.domain.EmployeeAddresses;
import com.venturedive.vendian_mono.domain.EmployeeBirthdays;
import com.venturedive.vendian_mono.domain.EmployeeCertificates;
import com.venturedive.vendian_mono.domain.EmployeeComments;
import com.venturedive.vendian_mono.domain.EmployeeCompensation;
import com.venturedive.vendian_mono.domain.EmployeeContacts;
import com.venturedive.vendian_mono.domain.EmployeeDetails;
import com.venturedive.vendian_mono.domain.EmployeeDocuments;
import com.venturedive.vendian_mono.domain.EmployeeEducation;
import com.venturedive.vendian_mono.domain.EmployeeEmergencyContacts;
import com.venturedive.vendian_mono.domain.EmployeeFamilyInfo;
import com.venturedive.vendian_mono.domain.EmployeeJobInfo;
import com.venturedive.vendian_mono.domain.EmployeeProfileHistoryLogs;
import com.venturedive.vendian_mono.domain.EmployeeProjectRatings;
import com.venturedive.vendian_mono.domain.EmployeeProjects;
import com.venturedive.vendian_mono.domain.EmployeeSkills;
import com.venturedive.vendian_mono.domain.EmployeeTalents;
import com.venturedive.vendian_mono.domain.EmployeeWorks;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.EmploymentHistory;
import com.venturedive.vendian_mono.domain.EmploymentStatuses;
import com.venturedive.vendian_mono.domain.EmploymentTypes;
import com.venturedive.vendian_mono.domain.FeedbackQuestions;
import com.venturedive.vendian_mono.domain.FeedbackRequests;
import com.venturedive.vendian_mono.domain.FeedbackRespondents;
import com.venturedive.vendian_mono.domain.Interviews;
import com.venturedive.vendian_mono.domain.LeaveDetailAdjustmentLogs;
import com.venturedive.vendian_mono.domain.LeaveRequestApprovers;
import com.venturedive.vendian_mono.domain.LeaveRequestsOlds;
import com.venturedive.vendian_mono.domain.Leaves;
import com.venturedive.vendian_mono.domain.LeavesOlds;
import com.venturedive.vendian_mono.domain.Locations;
import com.venturedive.vendian_mono.domain.NotificationSentEmailLogs;
import com.venturedive.vendian_mono.domain.PcRatings;
import com.venturedive.vendian_mono.domain.PcRatingsTrainings;
import com.venturedive.vendian_mono.domain.PerformanceCycleEmployeeRating;
import com.venturedive.vendian_mono.domain.ProjectCycles;
import com.venturedive.vendian_mono.domain.ProjectLeadership;
import com.venturedive.vendian_mono.domain.Projects;
import com.venturedive.vendian_mono.domain.Ratings;
import com.venturedive.vendian_mono.domain.Roles;
import com.venturedive.vendian_mono.domain.UserAttributes;
import com.venturedive.vendian_mono.domain.UserGoals;
import com.venturedive.vendian_mono.domain.UserRatings;
import com.venturedive.vendian_mono.domain.UserRatingsRemarks;
import com.venturedive.vendian_mono.domain.UserRelations;
import com.venturedive.vendian_mono.domain.WorkLogs;
import com.venturedive.vendian_mono.domain.ZEmployeeProjects;
import com.venturedive.vendian_mono.repository.EmployeesRepository;
import com.venturedive.vendian_mono.service.EmployeesService;
import com.venturedive.vendian_mono.service.criteria.EmployeesCriteria;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link EmployeesResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EmployeesResourceIT {

    private static final String DEFAULT_FIRSTNAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRSTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_LASTNAME = "AAAAAAAAAA";
    private static final String UPDATED_LASTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONENUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONENUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATEOFBIRTH = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATEOFBIRTH = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_SKYPE = "AAAAAAAAAA";
    private static final String UPDATED_SKYPE = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYEE_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_DESIGNATION = "BBBBBBBBBB";

    private static final Instant DEFAULT_JOININGDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_JOININGDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_AREA = "AAAAAAAAAA";
    private static final String UPDATED_AREA = "BBBBBBBBBB";

    private static final Instant DEFAULT_LEAVINGDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LEAVINGDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ISACTIVE = false;
    private static final Boolean UPDATED_ISACTIVE = true;

    private static final String DEFAULT_GOOGLEID = "AAAAAAAAAA";
    private static final String UPDATED_GOOGLEID = "BBBBBBBBBB";

    private static final String DEFAULT_ORACLEID = "AAAAAAAAAA";
    private static final String UPDATED_ORACLEID = "BBBBBBBBBB";

    private static final String DEFAULT_DEPTT = "AAAAAAAAAA";
    private static final String UPDATED_DEPTT = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_GENDERID = "AAAAAA";
    private static final String UPDATED_GENDERID = "BBBBBB";

    private static final Boolean DEFAULT_ONPROBATION = false;
    private static final Boolean UPDATED_ONPROBATION = true;

    private static final String DEFAULT_EMPLOYEE_COMPETENCY = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_COMPETENCY = "BBBBBBBBBB";

    private static final String DEFAULT_RESOURCETYPE = "AAAAAAAAAA";
    private static final String UPDATED_RESOURCETYPE = "BBBBBBBBBB";

    private static final String DEFAULT_GRADE = "AAAAAAAAAA";
    private static final String UPDATED_GRADE = "BBBBBBBBBB";

    private static final String DEFAULT_SUBGRADE = "AAAAAAAAAA";
    private static final String UPDATED_SUBGRADE = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGEURL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGEURL = "BBBBBBBBBB";

    private static final Instant DEFAULT_RESIGNATIONDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RESIGNATIONDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final LocalDate DEFAULT_GRADUATIONDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_GRADUATIONDATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_GRADUATIONDATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_CAREERSTARTDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CAREERSTARTDATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CAREERSTARTDATE = LocalDate.ofEpochDay(-1L);

    private static final Integer DEFAULT_EXTERNALEXPYEARS = 1;
    private static final Integer UPDATED_EXTERNALEXPYEARS = 2;
    private static final Integer SMALLER_EXTERNALEXPYEARS = 1 - 1;

    private static final BigDecimal DEFAULT_EXTERNALEXPMONTHS = new BigDecimal(1);
    private static final BigDecimal UPDATED_EXTERNALEXPMONTHS = new BigDecimal(2);
    private static final BigDecimal SMALLER_EXTERNALEXPMONTHS = new BigDecimal(1 - 1);

    private static final String DEFAULT_PLACEOFBIRTH = "AAAAAAAAAA";
    private static final String UPDATED_PLACEOFBIRTH = "BBBBBBBBBB";

    private static final Instant DEFAULT_HIREDDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HIREDDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LASTTRACKINGUPDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LASTTRACKINGUPDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MIDDLENAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLENAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_GROSSSALARY = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_GROSSSALARY = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_GROSSSALARY_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_GROSSSALARY_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_FUELALLOWANCE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FUELALLOWANCE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FUELALLOWANCE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FUELALLOWANCE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_MOBILENUMBER = "AAAAAAAAAA";
    private static final String UPDATED_MOBILENUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_RESIGNATIONTYPE = "AAAAAAAAAA";
    private static final String UPDATED_RESIGNATIONTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMARYREASONFORLEAVING = "AAAAAAAAAA";
    private static final String UPDATED_PRIMARYREASONFORLEAVING = "BBBBBBBBBB";

    private static final String DEFAULT_SECONDARYREASONFORLEAVING = "AAAAAAAAAA";
    private static final String UPDATED_SECONDARYREASONFORLEAVING = "BBBBBBBBBB";

    private static final Integer DEFAULT_NOTICEPERIODDURATION = 1;
    private static final Integer UPDATED_NOTICEPERIODDURATION = 2;
    private static final Integer SMALLER_NOTICEPERIODDURATION = 1 - 1;

    private static final Integer DEFAULT_NOTICEPERIODSERVED = 1;
    private static final Integer UPDATED_NOTICEPERIODSERVED = 2;
    private static final Integer SMALLER_NOTICEPERIODSERVED = 1 - 1;

    private static final Integer DEFAULT_PROBATIONPERIODDURATION = 1;
    private static final Integer UPDATED_PROBATIONPERIODDURATION = 2;
    private static final Integer SMALLER_PROBATIONPERIODDURATION = 1 - 1;

    private static final String ENTITY_API_URL = "/api/employees";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeesRepository employeesRepository;

    @Mock
    private EmployeesRepository employeesRepositoryMock;

    @Mock
    private EmployeesService employeesServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeesMockMvc;

    private Employees employees;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employees createEntity(EntityManager em) {
        Employees employees = new Employees()
            .firstname(DEFAULT_FIRSTNAME)
            .lastname(DEFAULT_LASTNAME)
            .phonenumber(DEFAULT_PHONENUMBER)
            .dateofbirth(DEFAULT_DATEOFBIRTH)
            .email(DEFAULT_EMAIL)
            .skype(DEFAULT_SKYPE)
            .employeeDesignation(DEFAULT_EMPLOYEE_DESIGNATION)
            .joiningdate(DEFAULT_JOININGDATE)
            .area(DEFAULT_AREA)
            .leavingdate(DEFAULT_LEAVINGDATE)
            .notes(DEFAULT_NOTES)
            .isactive(DEFAULT_ISACTIVE)
            .googleid(DEFAULT_GOOGLEID)
            .oracleid(DEFAULT_ORACLEID)
            .deptt(DEFAULT_DEPTT)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT)
            .genderid(DEFAULT_GENDERID)
            .onprobation(DEFAULT_ONPROBATION)
            .employeeCompetency(DEFAULT_EMPLOYEE_COMPETENCY)
            .resourcetype(DEFAULT_RESOURCETYPE)
            .grade(DEFAULT_GRADE)
            .subgrade(DEFAULT_SUBGRADE)
            .imageurl(DEFAULT_IMAGEURL)
            .resignationdate(DEFAULT_RESIGNATIONDATE)
            .graduationdate(DEFAULT_GRADUATIONDATE)
            .careerstartdate(DEFAULT_CAREERSTARTDATE)
            .externalexpyears(DEFAULT_EXTERNALEXPYEARS)
            .externalexpmonths(DEFAULT_EXTERNALEXPMONTHS)
            .placeofbirth(DEFAULT_PLACEOFBIRTH)
            .hireddate(DEFAULT_HIREDDATE)
            .lasttrackingupdate(DEFAULT_LASTTRACKINGUPDATE)
            .middlename(DEFAULT_MIDDLENAME)
            .grosssalary(DEFAULT_GROSSSALARY)
            .grosssalaryContentType(DEFAULT_GROSSSALARY_CONTENT_TYPE)
            .fuelallowance(DEFAULT_FUELALLOWANCE)
            .fuelallowanceContentType(DEFAULT_FUELALLOWANCE_CONTENT_TYPE)
            .mobilenumber(DEFAULT_MOBILENUMBER)
            .resignationtype(DEFAULT_RESIGNATIONTYPE)
            .primaryreasonforleaving(DEFAULT_PRIMARYREASONFORLEAVING)
            .secondaryreasonforleaving(DEFAULT_SECONDARYREASONFORLEAVING)
            .noticeperiodduration(DEFAULT_NOTICEPERIODDURATION)
            .noticeperiodserved(DEFAULT_NOTICEPERIODSERVED)
            .probationperiodduration(DEFAULT_PROBATIONPERIODDURATION);
        return employees;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employees createUpdatedEntity(EntityManager em) {
        Employees employees = new Employees()
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .phonenumber(UPDATED_PHONENUMBER)
            .dateofbirth(UPDATED_DATEOFBIRTH)
            .email(UPDATED_EMAIL)
            .skype(UPDATED_SKYPE)
            .employeeDesignation(UPDATED_EMPLOYEE_DESIGNATION)
            .joiningdate(UPDATED_JOININGDATE)
            .area(UPDATED_AREA)
            .leavingdate(UPDATED_LEAVINGDATE)
            .notes(UPDATED_NOTES)
            .isactive(UPDATED_ISACTIVE)
            .googleid(UPDATED_GOOGLEID)
            .oracleid(UPDATED_ORACLEID)
            .deptt(UPDATED_DEPTT)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .genderid(UPDATED_GENDERID)
            .onprobation(UPDATED_ONPROBATION)
            .employeeCompetency(UPDATED_EMPLOYEE_COMPETENCY)
            .resourcetype(UPDATED_RESOURCETYPE)
            .grade(UPDATED_GRADE)
            .subgrade(UPDATED_SUBGRADE)
            .imageurl(UPDATED_IMAGEURL)
            .resignationdate(UPDATED_RESIGNATIONDATE)
            .graduationdate(UPDATED_GRADUATIONDATE)
            .careerstartdate(UPDATED_CAREERSTARTDATE)
            .externalexpyears(UPDATED_EXTERNALEXPYEARS)
            .externalexpmonths(UPDATED_EXTERNALEXPMONTHS)
            .placeofbirth(UPDATED_PLACEOFBIRTH)
            .hireddate(UPDATED_HIREDDATE)
            .lasttrackingupdate(UPDATED_LASTTRACKINGUPDATE)
            .middlename(UPDATED_MIDDLENAME)
            .grosssalary(UPDATED_GROSSSALARY)
            .grosssalaryContentType(UPDATED_GROSSSALARY_CONTENT_TYPE)
            .fuelallowance(UPDATED_FUELALLOWANCE)
            .fuelallowanceContentType(UPDATED_FUELALLOWANCE_CONTENT_TYPE)
            .mobilenumber(UPDATED_MOBILENUMBER)
            .resignationtype(UPDATED_RESIGNATIONTYPE)
            .primaryreasonforleaving(UPDATED_PRIMARYREASONFORLEAVING)
            .secondaryreasonforleaving(UPDATED_SECONDARYREASONFORLEAVING)
            .noticeperiodduration(UPDATED_NOTICEPERIODDURATION)
            .noticeperiodserved(UPDATED_NOTICEPERIODSERVED)
            .probationperiodduration(UPDATED_PROBATIONPERIODDURATION);
        return employees;
    }

    @BeforeEach
    public void initTest() {
        employees = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployees() throws Exception {
        int databaseSizeBeforeCreate = employeesRepository.findAll().size();
        // Create the Employees
        restEmployeesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employees))
            )
            .andExpect(status().isCreated());

        // Validate the Employees in the database
        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeCreate + 1);
        Employees testEmployees = employeesList.get(employeesList.size() - 1);
        assertThat(testEmployees.getFirstname()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(testEmployees.getLastname()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testEmployees.getPhonenumber()).isEqualTo(DEFAULT_PHONENUMBER);
        assertThat(testEmployees.getDateofbirth()).isEqualTo(DEFAULT_DATEOFBIRTH);
        assertThat(testEmployees.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEmployees.getSkype()).isEqualTo(DEFAULT_SKYPE);
        assertThat(testEmployees.getEmployeeDesignation()).isEqualTo(DEFAULT_EMPLOYEE_DESIGNATION);
        assertThat(testEmployees.getJoiningdate()).isEqualTo(DEFAULT_JOININGDATE);
        assertThat(testEmployees.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testEmployees.getLeavingdate()).isEqualTo(DEFAULT_LEAVINGDATE);
        assertThat(testEmployees.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testEmployees.getIsactive()).isEqualTo(DEFAULT_ISACTIVE);
        assertThat(testEmployees.getGoogleid()).isEqualTo(DEFAULT_GOOGLEID);
        assertThat(testEmployees.getOracleid()).isEqualTo(DEFAULT_ORACLEID);
        assertThat(testEmployees.getDeptt()).isEqualTo(DEFAULT_DEPTT);
        assertThat(testEmployees.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testEmployees.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testEmployees.getGenderid()).isEqualTo(DEFAULT_GENDERID);
        assertThat(testEmployees.getOnprobation()).isEqualTo(DEFAULT_ONPROBATION);
        assertThat(testEmployees.getEmployeeCompetency()).isEqualTo(DEFAULT_EMPLOYEE_COMPETENCY);
        assertThat(testEmployees.getResourcetype()).isEqualTo(DEFAULT_RESOURCETYPE);
        assertThat(testEmployees.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testEmployees.getSubgrade()).isEqualTo(DEFAULT_SUBGRADE);
        assertThat(testEmployees.getImageurl()).isEqualTo(DEFAULT_IMAGEURL);
        assertThat(testEmployees.getResignationdate()).isEqualTo(DEFAULT_RESIGNATIONDATE);
        assertThat(testEmployees.getGraduationdate()).isEqualTo(DEFAULT_GRADUATIONDATE);
        assertThat(testEmployees.getCareerstartdate()).isEqualTo(DEFAULT_CAREERSTARTDATE);
        assertThat(testEmployees.getExternalexpyears()).isEqualTo(DEFAULT_EXTERNALEXPYEARS);
        assertThat(testEmployees.getExternalexpmonths()).isEqualByComparingTo(DEFAULT_EXTERNALEXPMONTHS);
        assertThat(testEmployees.getPlaceofbirth()).isEqualTo(DEFAULT_PLACEOFBIRTH);
        assertThat(testEmployees.getHireddate()).isEqualTo(DEFAULT_HIREDDATE);
        assertThat(testEmployees.getLasttrackingupdate()).isEqualTo(DEFAULT_LASTTRACKINGUPDATE);
        assertThat(testEmployees.getMiddlename()).isEqualTo(DEFAULT_MIDDLENAME);
        assertThat(testEmployees.getGrosssalary()).isEqualTo(DEFAULT_GROSSSALARY);
        assertThat(testEmployees.getGrosssalaryContentType()).isEqualTo(DEFAULT_GROSSSALARY_CONTENT_TYPE);
        assertThat(testEmployees.getFuelallowance()).isEqualTo(DEFAULT_FUELALLOWANCE);
        assertThat(testEmployees.getFuelallowanceContentType()).isEqualTo(DEFAULT_FUELALLOWANCE_CONTENT_TYPE);
        assertThat(testEmployees.getMobilenumber()).isEqualTo(DEFAULT_MOBILENUMBER);
        assertThat(testEmployees.getResignationtype()).isEqualTo(DEFAULT_RESIGNATIONTYPE);
        assertThat(testEmployees.getPrimaryreasonforleaving()).isEqualTo(DEFAULT_PRIMARYREASONFORLEAVING);
        assertThat(testEmployees.getSecondaryreasonforleaving()).isEqualTo(DEFAULT_SECONDARYREASONFORLEAVING);
        assertThat(testEmployees.getNoticeperiodduration()).isEqualTo(DEFAULT_NOTICEPERIODDURATION);
        assertThat(testEmployees.getNoticeperiodserved()).isEqualTo(DEFAULT_NOTICEPERIODSERVED);
        assertThat(testEmployees.getProbationperiodduration()).isEqualTo(DEFAULT_PROBATIONPERIODDURATION);
    }

    @Test
    @Transactional
    void createEmployeesWithExistingId() throws Exception {
        // Create the Employees with an existing ID
        employees.setId(1L);

        int databaseSizeBeforeCreate = employeesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employees))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employees in the database
        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeesRepository.findAll().size();
        // set the field null
        employees.setCreatedat(null);

        // Create the Employees, which fails.

        restEmployeesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employees))
            )
            .andExpect(status().isBadRequest());

        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeesRepository.findAll().size();
        // set the field null
        employees.setUpdatedat(null);

        // Create the Employees, which fails.

        restEmployeesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employees))
            )
            .andExpect(status().isBadRequest());

        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGenderidIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeesRepository.findAll().size();
        // set the field null
        employees.setGenderid(null);

        // Create the Employees, which fails.

        restEmployeesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employees))
            )
            .andExpect(status().isBadRequest());

        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmployees() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList
        restEmployeesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employees.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME)))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME)))
            .andExpect(jsonPath("$.[*].phonenumber").value(hasItem(DEFAULT_PHONENUMBER)))
            .andExpect(jsonPath("$.[*].dateofbirth").value(hasItem(DEFAULT_DATEOFBIRTH.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].skype").value(hasItem(DEFAULT_SKYPE)))
            .andExpect(jsonPath("$.[*].employeeDesignation").value(hasItem(DEFAULT_EMPLOYEE_DESIGNATION)))
            .andExpect(jsonPath("$.[*].joiningdate").value(hasItem(DEFAULT_JOININGDATE.toString())))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA)))
            .andExpect(jsonPath("$.[*].leavingdate").value(hasItem(DEFAULT_LEAVINGDATE.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].googleid").value(hasItem(DEFAULT_GOOGLEID)))
            .andExpect(jsonPath("$.[*].oracleid").value(hasItem(DEFAULT_ORACLEID)))
            .andExpect(jsonPath("$.[*].deptt").value(hasItem(DEFAULT_DEPTT)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].genderid").value(hasItem(DEFAULT_GENDERID)))
            .andExpect(jsonPath("$.[*].onprobation").value(hasItem(DEFAULT_ONPROBATION.booleanValue())))
            .andExpect(jsonPath("$.[*].employeeCompetency").value(hasItem(DEFAULT_EMPLOYEE_COMPETENCY)))
            .andExpect(jsonPath("$.[*].resourcetype").value(hasItem(DEFAULT_RESOURCETYPE)))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE)))
            .andExpect(jsonPath("$.[*].subgrade").value(hasItem(DEFAULT_SUBGRADE)))
            .andExpect(jsonPath("$.[*].imageurl").value(hasItem(DEFAULT_IMAGEURL)))
            .andExpect(jsonPath("$.[*].resignationdate").value(hasItem(DEFAULT_RESIGNATIONDATE.toString())))
            .andExpect(jsonPath("$.[*].graduationdate").value(hasItem(DEFAULT_GRADUATIONDATE.toString())))
            .andExpect(jsonPath("$.[*].careerstartdate").value(hasItem(DEFAULT_CAREERSTARTDATE.toString())))
            .andExpect(jsonPath("$.[*].externalexpyears").value(hasItem(DEFAULT_EXTERNALEXPYEARS)))
            .andExpect(jsonPath("$.[*].externalexpmonths").value(hasItem(sameNumber(DEFAULT_EXTERNALEXPMONTHS))))
            .andExpect(jsonPath("$.[*].placeofbirth").value(hasItem(DEFAULT_PLACEOFBIRTH)))
            .andExpect(jsonPath("$.[*].hireddate").value(hasItem(DEFAULT_HIREDDATE.toString())))
            .andExpect(jsonPath("$.[*].lasttrackingupdate").value(hasItem(DEFAULT_LASTTRACKINGUPDATE.toString())))
            .andExpect(jsonPath("$.[*].middlename").value(hasItem(DEFAULT_MIDDLENAME)))
            .andExpect(jsonPath("$.[*].grosssalaryContentType").value(hasItem(DEFAULT_GROSSSALARY_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].grosssalary").value(hasItem(Base64Utils.encodeToString(DEFAULT_GROSSSALARY))))
            .andExpect(jsonPath("$.[*].fuelallowanceContentType").value(hasItem(DEFAULT_FUELALLOWANCE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fuelallowance").value(hasItem(Base64Utils.encodeToString(DEFAULT_FUELALLOWANCE))))
            .andExpect(jsonPath("$.[*].mobilenumber").value(hasItem(DEFAULT_MOBILENUMBER)))
            .andExpect(jsonPath("$.[*].resignationtype").value(hasItem(DEFAULT_RESIGNATIONTYPE)))
            .andExpect(jsonPath("$.[*].primaryreasonforleaving").value(hasItem(DEFAULT_PRIMARYREASONFORLEAVING)))
            .andExpect(jsonPath("$.[*].secondaryreasonforleaving").value(hasItem(DEFAULT_SECONDARYREASONFORLEAVING)))
            .andExpect(jsonPath("$.[*].noticeperiodduration").value(hasItem(DEFAULT_NOTICEPERIODDURATION)))
            .andExpect(jsonPath("$.[*].noticeperiodserved").value(hasItem(DEFAULT_NOTICEPERIODSERVED)))
            .andExpect(jsonPath("$.[*].probationperiodduration").value(hasItem(DEFAULT_PROBATIONPERIODDURATION)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEmployeesWithEagerRelationshipsIsEnabled() throws Exception {
        when(employeesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEmployeesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(employeesServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEmployeesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(employeesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEmployeesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(employeesRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getEmployees() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get the employees
        restEmployeesMockMvc
            .perform(get(ENTITY_API_URL_ID, employees.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employees.getId().intValue()))
            .andExpect(jsonPath("$.firstname").value(DEFAULT_FIRSTNAME))
            .andExpect(jsonPath("$.lastname").value(DEFAULT_LASTNAME))
            .andExpect(jsonPath("$.phonenumber").value(DEFAULT_PHONENUMBER))
            .andExpect(jsonPath("$.dateofbirth").value(DEFAULT_DATEOFBIRTH.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.skype").value(DEFAULT_SKYPE))
            .andExpect(jsonPath("$.employeeDesignation").value(DEFAULT_EMPLOYEE_DESIGNATION))
            .andExpect(jsonPath("$.joiningdate").value(DEFAULT_JOININGDATE.toString()))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA))
            .andExpect(jsonPath("$.leavingdate").value(DEFAULT_LEAVINGDATE.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.isactive").value(DEFAULT_ISACTIVE.booleanValue()))
            .andExpect(jsonPath("$.googleid").value(DEFAULT_GOOGLEID))
            .andExpect(jsonPath("$.oracleid").value(DEFAULT_ORACLEID))
            .andExpect(jsonPath("$.deptt").value(DEFAULT_DEPTT))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()))
            .andExpect(jsonPath("$.genderid").value(DEFAULT_GENDERID))
            .andExpect(jsonPath("$.onprobation").value(DEFAULT_ONPROBATION.booleanValue()))
            .andExpect(jsonPath("$.employeeCompetency").value(DEFAULT_EMPLOYEE_COMPETENCY))
            .andExpect(jsonPath("$.resourcetype").value(DEFAULT_RESOURCETYPE))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE))
            .andExpect(jsonPath("$.subgrade").value(DEFAULT_SUBGRADE))
            .andExpect(jsonPath("$.imageurl").value(DEFAULT_IMAGEURL))
            .andExpect(jsonPath("$.resignationdate").value(DEFAULT_RESIGNATIONDATE.toString()))
            .andExpect(jsonPath("$.graduationdate").value(DEFAULT_GRADUATIONDATE.toString()))
            .andExpect(jsonPath("$.careerstartdate").value(DEFAULT_CAREERSTARTDATE.toString()))
            .andExpect(jsonPath("$.externalexpyears").value(DEFAULT_EXTERNALEXPYEARS))
            .andExpect(jsonPath("$.externalexpmonths").value(sameNumber(DEFAULT_EXTERNALEXPMONTHS)))
            .andExpect(jsonPath("$.placeofbirth").value(DEFAULT_PLACEOFBIRTH))
            .andExpect(jsonPath("$.hireddate").value(DEFAULT_HIREDDATE.toString()))
            .andExpect(jsonPath("$.lasttrackingupdate").value(DEFAULT_LASTTRACKINGUPDATE.toString()))
            .andExpect(jsonPath("$.middlename").value(DEFAULT_MIDDLENAME))
            .andExpect(jsonPath("$.grosssalaryContentType").value(DEFAULT_GROSSSALARY_CONTENT_TYPE))
            .andExpect(jsonPath("$.grosssalary").value(Base64Utils.encodeToString(DEFAULT_GROSSSALARY)))
            .andExpect(jsonPath("$.fuelallowanceContentType").value(DEFAULT_FUELALLOWANCE_CONTENT_TYPE))
            .andExpect(jsonPath("$.fuelallowance").value(Base64Utils.encodeToString(DEFAULT_FUELALLOWANCE)))
            .andExpect(jsonPath("$.mobilenumber").value(DEFAULT_MOBILENUMBER))
            .andExpect(jsonPath("$.resignationtype").value(DEFAULT_RESIGNATIONTYPE))
            .andExpect(jsonPath("$.primaryreasonforleaving").value(DEFAULT_PRIMARYREASONFORLEAVING))
            .andExpect(jsonPath("$.secondaryreasonforleaving").value(DEFAULT_SECONDARYREASONFORLEAVING))
            .andExpect(jsonPath("$.noticeperiodduration").value(DEFAULT_NOTICEPERIODDURATION))
            .andExpect(jsonPath("$.noticeperiodserved").value(DEFAULT_NOTICEPERIODSERVED))
            .andExpect(jsonPath("$.probationperiodduration").value(DEFAULT_PROBATIONPERIODDURATION));
    }

    @Test
    @Transactional
    void getEmployeesByIdFiltering() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        Long id = employees.getId();

        defaultEmployeesShouldBeFound("id.equals=" + id);
        defaultEmployeesShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeesShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployeesByFirstnameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where firstname equals to DEFAULT_FIRSTNAME
        defaultEmployeesShouldBeFound("firstname.equals=" + DEFAULT_FIRSTNAME);

        // Get all the employeesList where firstname equals to UPDATED_FIRSTNAME
        defaultEmployeesShouldNotBeFound("firstname.equals=" + UPDATED_FIRSTNAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByFirstnameIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where firstname in DEFAULT_FIRSTNAME or UPDATED_FIRSTNAME
        defaultEmployeesShouldBeFound("firstname.in=" + DEFAULT_FIRSTNAME + "," + UPDATED_FIRSTNAME);

        // Get all the employeesList where firstname equals to UPDATED_FIRSTNAME
        defaultEmployeesShouldNotBeFound("firstname.in=" + UPDATED_FIRSTNAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByFirstnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where firstname is not null
        defaultEmployeesShouldBeFound("firstname.specified=true");

        // Get all the employeesList where firstname is null
        defaultEmployeesShouldNotBeFound("firstname.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByFirstnameContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where firstname contains DEFAULT_FIRSTNAME
        defaultEmployeesShouldBeFound("firstname.contains=" + DEFAULT_FIRSTNAME);

        // Get all the employeesList where firstname contains UPDATED_FIRSTNAME
        defaultEmployeesShouldNotBeFound("firstname.contains=" + UPDATED_FIRSTNAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByFirstnameNotContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where firstname does not contain DEFAULT_FIRSTNAME
        defaultEmployeesShouldNotBeFound("firstname.doesNotContain=" + DEFAULT_FIRSTNAME);

        // Get all the employeesList where firstname does not contain UPDATED_FIRSTNAME
        defaultEmployeesShouldBeFound("firstname.doesNotContain=" + UPDATED_FIRSTNAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByLastnameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where lastname equals to DEFAULT_LASTNAME
        defaultEmployeesShouldBeFound("lastname.equals=" + DEFAULT_LASTNAME);

        // Get all the employeesList where lastname equals to UPDATED_LASTNAME
        defaultEmployeesShouldNotBeFound("lastname.equals=" + UPDATED_LASTNAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByLastnameIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where lastname in DEFAULT_LASTNAME or UPDATED_LASTNAME
        defaultEmployeesShouldBeFound("lastname.in=" + DEFAULT_LASTNAME + "," + UPDATED_LASTNAME);

        // Get all the employeesList where lastname equals to UPDATED_LASTNAME
        defaultEmployeesShouldNotBeFound("lastname.in=" + UPDATED_LASTNAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByLastnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where lastname is not null
        defaultEmployeesShouldBeFound("lastname.specified=true");

        // Get all the employeesList where lastname is null
        defaultEmployeesShouldNotBeFound("lastname.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByLastnameContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where lastname contains DEFAULT_LASTNAME
        defaultEmployeesShouldBeFound("lastname.contains=" + DEFAULT_LASTNAME);

        // Get all the employeesList where lastname contains UPDATED_LASTNAME
        defaultEmployeesShouldNotBeFound("lastname.contains=" + UPDATED_LASTNAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByLastnameNotContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where lastname does not contain DEFAULT_LASTNAME
        defaultEmployeesShouldNotBeFound("lastname.doesNotContain=" + DEFAULT_LASTNAME);

        // Get all the employeesList where lastname does not contain UPDATED_LASTNAME
        defaultEmployeesShouldBeFound("lastname.doesNotContain=" + UPDATED_LASTNAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByPhonenumberIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where phonenumber equals to DEFAULT_PHONENUMBER
        defaultEmployeesShouldBeFound("phonenumber.equals=" + DEFAULT_PHONENUMBER);

        // Get all the employeesList where phonenumber equals to UPDATED_PHONENUMBER
        defaultEmployeesShouldNotBeFound("phonenumber.equals=" + UPDATED_PHONENUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByPhonenumberIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where phonenumber in DEFAULT_PHONENUMBER or UPDATED_PHONENUMBER
        defaultEmployeesShouldBeFound("phonenumber.in=" + DEFAULT_PHONENUMBER + "," + UPDATED_PHONENUMBER);

        // Get all the employeesList where phonenumber equals to UPDATED_PHONENUMBER
        defaultEmployeesShouldNotBeFound("phonenumber.in=" + UPDATED_PHONENUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByPhonenumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where phonenumber is not null
        defaultEmployeesShouldBeFound("phonenumber.specified=true");

        // Get all the employeesList where phonenumber is null
        defaultEmployeesShouldNotBeFound("phonenumber.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByPhonenumberContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where phonenumber contains DEFAULT_PHONENUMBER
        defaultEmployeesShouldBeFound("phonenumber.contains=" + DEFAULT_PHONENUMBER);

        // Get all the employeesList where phonenumber contains UPDATED_PHONENUMBER
        defaultEmployeesShouldNotBeFound("phonenumber.contains=" + UPDATED_PHONENUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByPhonenumberNotContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where phonenumber does not contain DEFAULT_PHONENUMBER
        defaultEmployeesShouldNotBeFound("phonenumber.doesNotContain=" + DEFAULT_PHONENUMBER);

        // Get all the employeesList where phonenumber does not contain UPDATED_PHONENUMBER
        defaultEmployeesShouldBeFound("phonenumber.doesNotContain=" + UPDATED_PHONENUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByDateofbirthIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where dateofbirth equals to DEFAULT_DATEOFBIRTH
        defaultEmployeesShouldBeFound("dateofbirth.equals=" + DEFAULT_DATEOFBIRTH);

        // Get all the employeesList where dateofbirth equals to UPDATED_DATEOFBIRTH
        defaultEmployeesShouldNotBeFound("dateofbirth.equals=" + UPDATED_DATEOFBIRTH);
    }

    @Test
    @Transactional
    void getAllEmployeesByDateofbirthIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where dateofbirth in DEFAULT_DATEOFBIRTH or UPDATED_DATEOFBIRTH
        defaultEmployeesShouldBeFound("dateofbirth.in=" + DEFAULT_DATEOFBIRTH + "," + UPDATED_DATEOFBIRTH);

        // Get all the employeesList where dateofbirth equals to UPDATED_DATEOFBIRTH
        defaultEmployeesShouldNotBeFound("dateofbirth.in=" + UPDATED_DATEOFBIRTH);
    }

    @Test
    @Transactional
    void getAllEmployeesByDateofbirthIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where dateofbirth is not null
        defaultEmployeesShouldBeFound("dateofbirth.specified=true");

        // Get all the employeesList where dateofbirth is null
        defaultEmployeesShouldNotBeFound("dateofbirth.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where email equals to DEFAULT_EMAIL
        defaultEmployeesShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the employeesList where email equals to UPDATED_EMAIL
        defaultEmployeesShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultEmployeesShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the employeesList where email equals to UPDATED_EMAIL
        defaultEmployeesShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where email is not null
        defaultEmployeesShouldBeFound("email.specified=true");

        // Get all the employeesList where email is null
        defaultEmployeesShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByEmailContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where email contains DEFAULT_EMAIL
        defaultEmployeesShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the employeesList where email contains UPDATED_EMAIL
        defaultEmployeesShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where email does not contain DEFAULT_EMAIL
        defaultEmployeesShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the employeesList where email does not contain UPDATED_EMAIL
        defaultEmployeesShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEmployeesBySkypeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where skype equals to DEFAULT_SKYPE
        defaultEmployeesShouldBeFound("skype.equals=" + DEFAULT_SKYPE);

        // Get all the employeesList where skype equals to UPDATED_SKYPE
        defaultEmployeesShouldNotBeFound("skype.equals=" + UPDATED_SKYPE);
    }

    @Test
    @Transactional
    void getAllEmployeesBySkypeIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where skype in DEFAULT_SKYPE or UPDATED_SKYPE
        defaultEmployeesShouldBeFound("skype.in=" + DEFAULT_SKYPE + "," + UPDATED_SKYPE);

        // Get all the employeesList where skype equals to UPDATED_SKYPE
        defaultEmployeesShouldNotBeFound("skype.in=" + UPDATED_SKYPE);
    }

    @Test
    @Transactional
    void getAllEmployeesBySkypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where skype is not null
        defaultEmployeesShouldBeFound("skype.specified=true");

        // Get all the employeesList where skype is null
        defaultEmployeesShouldNotBeFound("skype.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesBySkypeContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where skype contains DEFAULT_SKYPE
        defaultEmployeesShouldBeFound("skype.contains=" + DEFAULT_SKYPE);

        // Get all the employeesList where skype contains UPDATED_SKYPE
        defaultEmployeesShouldNotBeFound("skype.contains=" + UPDATED_SKYPE);
    }

    @Test
    @Transactional
    void getAllEmployeesBySkypeNotContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where skype does not contain DEFAULT_SKYPE
        defaultEmployeesShouldNotBeFound("skype.doesNotContain=" + DEFAULT_SKYPE);

        // Get all the employeesList where skype does not contain UPDATED_SKYPE
        defaultEmployeesShouldBeFound("skype.doesNotContain=" + UPDATED_SKYPE);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeDesignation equals to DEFAULT_EMPLOYEE_DESIGNATION
        defaultEmployeesShouldBeFound("employeeDesignation.equals=" + DEFAULT_EMPLOYEE_DESIGNATION);

        // Get all the employeesList where employeeDesignation equals to UPDATED_EMPLOYEE_DESIGNATION
        defaultEmployeesShouldNotBeFound("employeeDesignation.equals=" + UPDATED_EMPLOYEE_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeDesignationIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeDesignation in DEFAULT_EMPLOYEE_DESIGNATION or UPDATED_EMPLOYEE_DESIGNATION
        defaultEmployeesShouldBeFound("employeeDesignation.in=" + DEFAULT_EMPLOYEE_DESIGNATION + "," + UPDATED_EMPLOYEE_DESIGNATION);

        // Get all the employeesList where employeeDesignation equals to UPDATED_EMPLOYEE_DESIGNATION
        defaultEmployeesShouldNotBeFound("employeeDesignation.in=" + UPDATED_EMPLOYEE_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeDesignationIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeDesignation is not null
        defaultEmployeesShouldBeFound("employeeDesignation.specified=true");

        // Get all the employeesList where employeeDesignation is null
        defaultEmployeesShouldNotBeFound("employeeDesignation.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeDesignationContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeDesignation contains DEFAULT_EMPLOYEE_DESIGNATION
        defaultEmployeesShouldBeFound("employeeDesignation.contains=" + DEFAULT_EMPLOYEE_DESIGNATION);

        // Get all the employeesList where employeeDesignation contains UPDATED_EMPLOYEE_DESIGNATION
        defaultEmployeesShouldNotBeFound("employeeDesignation.contains=" + UPDATED_EMPLOYEE_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeDesignationNotContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeDesignation does not contain DEFAULT_EMPLOYEE_DESIGNATION
        defaultEmployeesShouldNotBeFound("employeeDesignation.doesNotContain=" + DEFAULT_EMPLOYEE_DESIGNATION);

        // Get all the employeesList where employeeDesignation does not contain UPDATED_EMPLOYEE_DESIGNATION
        defaultEmployeesShouldBeFound("employeeDesignation.doesNotContain=" + UPDATED_EMPLOYEE_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllEmployeesByJoiningdateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where joiningdate equals to DEFAULT_JOININGDATE
        defaultEmployeesShouldBeFound("joiningdate.equals=" + DEFAULT_JOININGDATE);

        // Get all the employeesList where joiningdate equals to UPDATED_JOININGDATE
        defaultEmployeesShouldNotBeFound("joiningdate.equals=" + UPDATED_JOININGDATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByJoiningdateIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where joiningdate in DEFAULT_JOININGDATE or UPDATED_JOININGDATE
        defaultEmployeesShouldBeFound("joiningdate.in=" + DEFAULT_JOININGDATE + "," + UPDATED_JOININGDATE);

        // Get all the employeesList where joiningdate equals to UPDATED_JOININGDATE
        defaultEmployeesShouldNotBeFound("joiningdate.in=" + UPDATED_JOININGDATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByJoiningdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where joiningdate is not null
        defaultEmployeesShouldBeFound("joiningdate.specified=true");

        // Get all the employeesList where joiningdate is null
        defaultEmployeesShouldNotBeFound("joiningdate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where area equals to DEFAULT_AREA
        defaultEmployeesShouldBeFound("area.equals=" + DEFAULT_AREA);

        // Get all the employeesList where area equals to UPDATED_AREA
        defaultEmployeesShouldNotBeFound("area.equals=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllEmployeesByAreaIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where area in DEFAULT_AREA or UPDATED_AREA
        defaultEmployeesShouldBeFound("area.in=" + DEFAULT_AREA + "," + UPDATED_AREA);

        // Get all the employeesList where area equals to UPDATED_AREA
        defaultEmployeesShouldNotBeFound("area.in=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllEmployeesByAreaIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where area is not null
        defaultEmployeesShouldBeFound("area.specified=true");

        // Get all the employeesList where area is null
        defaultEmployeesShouldNotBeFound("area.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByAreaContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where area contains DEFAULT_AREA
        defaultEmployeesShouldBeFound("area.contains=" + DEFAULT_AREA);

        // Get all the employeesList where area contains UPDATED_AREA
        defaultEmployeesShouldNotBeFound("area.contains=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllEmployeesByAreaNotContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where area does not contain DEFAULT_AREA
        defaultEmployeesShouldNotBeFound("area.doesNotContain=" + DEFAULT_AREA);

        // Get all the employeesList where area does not contain UPDATED_AREA
        defaultEmployeesShouldBeFound("area.doesNotContain=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllEmployeesByLeavingdateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where leavingdate equals to DEFAULT_LEAVINGDATE
        defaultEmployeesShouldBeFound("leavingdate.equals=" + DEFAULT_LEAVINGDATE);

        // Get all the employeesList where leavingdate equals to UPDATED_LEAVINGDATE
        defaultEmployeesShouldNotBeFound("leavingdate.equals=" + UPDATED_LEAVINGDATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByLeavingdateIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where leavingdate in DEFAULT_LEAVINGDATE or UPDATED_LEAVINGDATE
        defaultEmployeesShouldBeFound("leavingdate.in=" + DEFAULT_LEAVINGDATE + "," + UPDATED_LEAVINGDATE);

        // Get all the employeesList where leavingdate equals to UPDATED_LEAVINGDATE
        defaultEmployeesShouldNotBeFound("leavingdate.in=" + UPDATED_LEAVINGDATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByLeavingdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where leavingdate is not null
        defaultEmployeesShouldBeFound("leavingdate.specified=true");

        // Get all the employeesList where leavingdate is null
        defaultEmployeesShouldNotBeFound("leavingdate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByNotesIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where notes equals to DEFAULT_NOTES
        defaultEmployeesShouldBeFound("notes.equals=" + DEFAULT_NOTES);

        // Get all the employeesList where notes equals to UPDATED_NOTES
        defaultEmployeesShouldNotBeFound("notes.equals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllEmployeesByNotesIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where notes in DEFAULT_NOTES or UPDATED_NOTES
        defaultEmployeesShouldBeFound("notes.in=" + DEFAULT_NOTES + "," + UPDATED_NOTES);

        // Get all the employeesList where notes equals to UPDATED_NOTES
        defaultEmployeesShouldNotBeFound("notes.in=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllEmployeesByNotesIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where notes is not null
        defaultEmployeesShouldBeFound("notes.specified=true");

        // Get all the employeesList where notes is null
        defaultEmployeesShouldNotBeFound("notes.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByNotesContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where notes contains DEFAULT_NOTES
        defaultEmployeesShouldBeFound("notes.contains=" + DEFAULT_NOTES);

        // Get all the employeesList where notes contains UPDATED_NOTES
        defaultEmployeesShouldNotBeFound("notes.contains=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllEmployeesByNotesNotContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where notes does not contain DEFAULT_NOTES
        defaultEmployeesShouldNotBeFound("notes.doesNotContain=" + DEFAULT_NOTES);

        // Get all the employeesList where notes does not contain UPDATED_NOTES
        defaultEmployeesShouldBeFound("notes.doesNotContain=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllEmployeesByIsactiveIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where isactive equals to DEFAULT_ISACTIVE
        defaultEmployeesShouldBeFound("isactive.equals=" + DEFAULT_ISACTIVE);

        // Get all the employeesList where isactive equals to UPDATED_ISACTIVE
        defaultEmployeesShouldNotBeFound("isactive.equals=" + UPDATED_ISACTIVE);
    }

    @Test
    @Transactional
    void getAllEmployeesByIsactiveIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where isactive in DEFAULT_ISACTIVE or UPDATED_ISACTIVE
        defaultEmployeesShouldBeFound("isactive.in=" + DEFAULT_ISACTIVE + "," + UPDATED_ISACTIVE);

        // Get all the employeesList where isactive equals to UPDATED_ISACTIVE
        defaultEmployeesShouldNotBeFound("isactive.in=" + UPDATED_ISACTIVE);
    }

    @Test
    @Transactional
    void getAllEmployeesByIsactiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where isactive is not null
        defaultEmployeesShouldBeFound("isactive.specified=true");

        // Get all the employeesList where isactive is null
        defaultEmployeesShouldNotBeFound("isactive.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByGoogleidIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where googleid equals to DEFAULT_GOOGLEID
        defaultEmployeesShouldBeFound("googleid.equals=" + DEFAULT_GOOGLEID);

        // Get all the employeesList where googleid equals to UPDATED_GOOGLEID
        defaultEmployeesShouldNotBeFound("googleid.equals=" + UPDATED_GOOGLEID);
    }

    @Test
    @Transactional
    void getAllEmployeesByGoogleidIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where googleid in DEFAULT_GOOGLEID or UPDATED_GOOGLEID
        defaultEmployeesShouldBeFound("googleid.in=" + DEFAULT_GOOGLEID + "," + UPDATED_GOOGLEID);

        // Get all the employeesList where googleid equals to UPDATED_GOOGLEID
        defaultEmployeesShouldNotBeFound("googleid.in=" + UPDATED_GOOGLEID);
    }

    @Test
    @Transactional
    void getAllEmployeesByGoogleidIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where googleid is not null
        defaultEmployeesShouldBeFound("googleid.specified=true");

        // Get all the employeesList where googleid is null
        defaultEmployeesShouldNotBeFound("googleid.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByGoogleidContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where googleid contains DEFAULT_GOOGLEID
        defaultEmployeesShouldBeFound("googleid.contains=" + DEFAULT_GOOGLEID);

        // Get all the employeesList where googleid contains UPDATED_GOOGLEID
        defaultEmployeesShouldNotBeFound("googleid.contains=" + UPDATED_GOOGLEID);
    }

    @Test
    @Transactional
    void getAllEmployeesByGoogleidNotContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where googleid does not contain DEFAULT_GOOGLEID
        defaultEmployeesShouldNotBeFound("googleid.doesNotContain=" + DEFAULT_GOOGLEID);

        // Get all the employeesList where googleid does not contain UPDATED_GOOGLEID
        defaultEmployeesShouldBeFound("googleid.doesNotContain=" + UPDATED_GOOGLEID);
    }

    @Test
    @Transactional
    void getAllEmployeesByOracleidIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where oracleid equals to DEFAULT_ORACLEID
        defaultEmployeesShouldBeFound("oracleid.equals=" + DEFAULT_ORACLEID);

        // Get all the employeesList where oracleid equals to UPDATED_ORACLEID
        defaultEmployeesShouldNotBeFound("oracleid.equals=" + UPDATED_ORACLEID);
    }

    @Test
    @Transactional
    void getAllEmployeesByOracleidIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where oracleid in DEFAULT_ORACLEID or UPDATED_ORACLEID
        defaultEmployeesShouldBeFound("oracleid.in=" + DEFAULT_ORACLEID + "," + UPDATED_ORACLEID);

        // Get all the employeesList where oracleid equals to UPDATED_ORACLEID
        defaultEmployeesShouldNotBeFound("oracleid.in=" + UPDATED_ORACLEID);
    }

    @Test
    @Transactional
    void getAllEmployeesByOracleidIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where oracleid is not null
        defaultEmployeesShouldBeFound("oracleid.specified=true");

        // Get all the employeesList where oracleid is null
        defaultEmployeesShouldNotBeFound("oracleid.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByOracleidContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where oracleid contains DEFAULT_ORACLEID
        defaultEmployeesShouldBeFound("oracleid.contains=" + DEFAULT_ORACLEID);

        // Get all the employeesList where oracleid contains UPDATED_ORACLEID
        defaultEmployeesShouldNotBeFound("oracleid.contains=" + UPDATED_ORACLEID);
    }

    @Test
    @Transactional
    void getAllEmployeesByOracleidNotContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where oracleid does not contain DEFAULT_ORACLEID
        defaultEmployeesShouldNotBeFound("oracleid.doesNotContain=" + DEFAULT_ORACLEID);

        // Get all the employeesList where oracleid does not contain UPDATED_ORACLEID
        defaultEmployeesShouldBeFound("oracleid.doesNotContain=" + UPDATED_ORACLEID);
    }

    @Test
    @Transactional
    void getAllEmployeesByDepttIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where deptt equals to DEFAULT_DEPTT
        defaultEmployeesShouldBeFound("deptt.equals=" + DEFAULT_DEPTT);

        // Get all the employeesList where deptt equals to UPDATED_DEPTT
        defaultEmployeesShouldNotBeFound("deptt.equals=" + UPDATED_DEPTT);
    }

    @Test
    @Transactional
    void getAllEmployeesByDepttIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where deptt in DEFAULT_DEPTT or UPDATED_DEPTT
        defaultEmployeesShouldBeFound("deptt.in=" + DEFAULT_DEPTT + "," + UPDATED_DEPTT);

        // Get all the employeesList where deptt equals to UPDATED_DEPTT
        defaultEmployeesShouldNotBeFound("deptt.in=" + UPDATED_DEPTT);
    }

    @Test
    @Transactional
    void getAllEmployeesByDepttIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where deptt is not null
        defaultEmployeesShouldBeFound("deptt.specified=true");

        // Get all the employeesList where deptt is null
        defaultEmployeesShouldNotBeFound("deptt.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByDepttContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where deptt contains DEFAULT_DEPTT
        defaultEmployeesShouldBeFound("deptt.contains=" + DEFAULT_DEPTT);

        // Get all the employeesList where deptt contains UPDATED_DEPTT
        defaultEmployeesShouldNotBeFound("deptt.contains=" + UPDATED_DEPTT);
    }

    @Test
    @Transactional
    void getAllEmployeesByDepttNotContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where deptt does not contain DEFAULT_DEPTT
        defaultEmployeesShouldNotBeFound("deptt.doesNotContain=" + DEFAULT_DEPTT);

        // Get all the employeesList where deptt does not contain UPDATED_DEPTT
        defaultEmployeesShouldBeFound("deptt.doesNotContain=" + UPDATED_DEPTT);
    }

    @Test
    @Transactional
    void getAllEmployeesByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where createdat equals to DEFAULT_CREATEDAT
        defaultEmployeesShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the employeesList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeesShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeesByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultEmployeesShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the employeesList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeesShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeesByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where createdat is not null
        defaultEmployeesShouldBeFound("createdat.specified=true");

        // Get all the employeesList where createdat is null
        defaultEmployeesShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where updatedat equals to DEFAULT_UPDATEDAT
        defaultEmployeesShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the employeesList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeesShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeesByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultEmployeesShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the employeesList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeesShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeesByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where updatedat is not null
        defaultEmployeesShouldBeFound("updatedat.specified=true");

        // Get all the employeesList where updatedat is null
        defaultEmployeesShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByGenderidIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where genderid equals to DEFAULT_GENDERID
        defaultEmployeesShouldBeFound("genderid.equals=" + DEFAULT_GENDERID);

        // Get all the employeesList where genderid equals to UPDATED_GENDERID
        defaultEmployeesShouldNotBeFound("genderid.equals=" + UPDATED_GENDERID);
    }

    @Test
    @Transactional
    void getAllEmployeesByGenderidIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where genderid in DEFAULT_GENDERID or UPDATED_GENDERID
        defaultEmployeesShouldBeFound("genderid.in=" + DEFAULT_GENDERID + "," + UPDATED_GENDERID);

        // Get all the employeesList where genderid equals to UPDATED_GENDERID
        defaultEmployeesShouldNotBeFound("genderid.in=" + UPDATED_GENDERID);
    }

    @Test
    @Transactional
    void getAllEmployeesByGenderidIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where genderid is not null
        defaultEmployeesShouldBeFound("genderid.specified=true");

        // Get all the employeesList where genderid is null
        defaultEmployeesShouldNotBeFound("genderid.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByGenderidContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where genderid contains DEFAULT_GENDERID
        defaultEmployeesShouldBeFound("genderid.contains=" + DEFAULT_GENDERID);

        // Get all the employeesList where genderid contains UPDATED_GENDERID
        defaultEmployeesShouldNotBeFound("genderid.contains=" + UPDATED_GENDERID);
    }

    @Test
    @Transactional
    void getAllEmployeesByGenderidNotContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where genderid does not contain DEFAULT_GENDERID
        defaultEmployeesShouldNotBeFound("genderid.doesNotContain=" + DEFAULT_GENDERID);

        // Get all the employeesList where genderid does not contain UPDATED_GENDERID
        defaultEmployeesShouldBeFound("genderid.doesNotContain=" + UPDATED_GENDERID);
    }

    @Test
    @Transactional
    void getAllEmployeesByOnprobationIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where onprobation equals to DEFAULT_ONPROBATION
        defaultEmployeesShouldBeFound("onprobation.equals=" + DEFAULT_ONPROBATION);

        // Get all the employeesList where onprobation equals to UPDATED_ONPROBATION
        defaultEmployeesShouldNotBeFound("onprobation.equals=" + UPDATED_ONPROBATION);
    }

    @Test
    @Transactional
    void getAllEmployeesByOnprobationIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where onprobation in DEFAULT_ONPROBATION or UPDATED_ONPROBATION
        defaultEmployeesShouldBeFound("onprobation.in=" + DEFAULT_ONPROBATION + "," + UPDATED_ONPROBATION);

        // Get all the employeesList where onprobation equals to UPDATED_ONPROBATION
        defaultEmployeesShouldNotBeFound("onprobation.in=" + UPDATED_ONPROBATION);
    }

    @Test
    @Transactional
    void getAllEmployeesByOnprobationIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where onprobation is not null
        defaultEmployeesShouldBeFound("onprobation.specified=true");

        // Get all the employeesList where onprobation is null
        defaultEmployeesShouldNotBeFound("onprobation.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeCompetencyIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeCompetency equals to DEFAULT_EMPLOYEE_COMPETENCY
        defaultEmployeesShouldBeFound("employeeCompetency.equals=" + DEFAULT_EMPLOYEE_COMPETENCY);

        // Get all the employeesList where employeeCompetency equals to UPDATED_EMPLOYEE_COMPETENCY
        defaultEmployeesShouldNotBeFound("employeeCompetency.equals=" + UPDATED_EMPLOYEE_COMPETENCY);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeCompetencyIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeCompetency in DEFAULT_EMPLOYEE_COMPETENCY or UPDATED_EMPLOYEE_COMPETENCY
        defaultEmployeesShouldBeFound("employeeCompetency.in=" + DEFAULT_EMPLOYEE_COMPETENCY + "," + UPDATED_EMPLOYEE_COMPETENCY);

        // Get all the employeesList where employeeCompetency equals to UPDATED_EMPLOYEE_COMPETENCY
        defaultEmployeesShouldNotBeFound("employeeCompetency.in=" + UPDATED_EMPLOYEE_COMPETENCY);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeCompetencyIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeCompetency is not null
        defaultEmployeesShouldBeFound("employeeCompetency.specified=true");

        // Get all the employeesList where employeeCompetency is null
        defaultEmployeesShouldNotBeFound("employeeCompetency.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeCompetencyContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeCompetency contains DEFAULT_EMPLOYEE_COMPETENCY
        defaultEmployeesShouldBeFound("employeeCompetency.contains=" + DEFAULT_EMPLOYEE_COMPETENCY);

        // Get all the employeesList where employeeCompetency contains UPDATED_EMPLOYEE_COMPETENCY
        defaultEmployeesShouldNotBeFound("employeeCompetency.contains=" + UPDATED_EMPLOYEE_COMPETENCY);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeCompetencyNotContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeCompetency does not contain DEFAULT_EMPLOYEE_COMPETENCY
        defaultEmployeesShouldNotBeFound("employeeCompetency.doesNotContain=" + DEFAULT_EMPLOYEE_COMPETENCY);

        // Get all the employeesList where employeeCompetency does not contain UPDATED_EMPLOYEE_COMPETENCY
        defaultEmployeesShouldBeFound("employeeCompetency.doesNotContain=" + UPDATED_EMPLOYEE_COMPETENCY);
    }

    @Test
    @Transactional
    void getAllEmployeesByResourcetypeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where resourcetype equals to DEFAULT_RESOURCETYPE
        defaultEmployeesShouldBeFound("resourcetype.equals=" + DEFAULT_RESOURCETYPE);

        // Get all the employeesList where resourcetype equals to UPDATED_RESOURCETYPE
        defaultEmployeesShouldNotBeFound("resourcetype.equals=" + UPDATED_RESOURCETYPE);
    }

    @Test
    @Transactional
    void getAllEmployeesByResourcetypeIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where resourcetype in DEFAULT_RESOURCETYPE or UPDATED_RESOURCETYPE
        defaultEmployeesShouldBeFound("resourcetype.in=" + DEFAULT_RESOURCETYPE + "," + UPDATED_RESOURCETYPE);

        // Get all the employeesList where resourcetype equals to UPDATED_RESOURCETYPE
        defaultEmployeesShouldNotBeFound("resourcetype.in=" + UPDATED_RESOURCETYPE);
    }

    @Test
    @Transactional
    void getAllEmployeesByResourcetypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where resourcetype is not null
        defaultEmployeesShouldBeFound("resourcetype.specified=true");

        // Get all the employeesList where resourcetype is null
        defaultEmployeesShouldNotBeFound("resourcetype.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByResourcetypeContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where resourcetype contains DEFAULT_RESOURCETYPE
        defaultEmployeesShouldBeFound("resourcetype.contains=" + DEFAULT_RESOURCETYPE);

        // Get all the employeesList where resourcetype contains UPDATED_RESOURCETYPE
        defaultEmployeesShouldNotBeFound("resourcetype.contains=" + UPDATED_RESOURCETYPE);
    }

    @Test
    @Transactional
    void getAllEmployeesByResourcetypeNotContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where resourcetype does not contain DEFAULT_RESOURCETYPE
        defaultEmployeesShouldNotBeFound("resourcetype.doesNotContain=" + DEFAULT_RESOURCETYPE);

        // Get all the employeesList where resourcetype does not contain UPDATED_RESOURCETYPE
        defaultEmployeesShouldBeFound("resourcetype.doesNotContain=" + UPDATED_RESOURCETYPE);
    }

    @Test
    @Transactional
    void getAllEmployeesByGradeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where grade equals to DEFAULT_GRADE
        defaultEmployeesShouldBeFound("grade.equals=" + DEFAULT_GRADE);

        // Get all the employeesList where grade equals to UPDATED_GRADE
        defaultEmployeesShouldNotBeFound("grade.equals=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    void getAllEmployeesByGradeIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where grade in DEFAULT_GRADE or UPDATED_GRADE
        defaultEmployeesShouldBeFound("grade.in=" + DEFAULT_GRADE + "," + UPDATED_GRADE);

        // Get all the employeesList where grade equals to UPDATED_GRADE
        defaultEmployeesShouldNotBeFound("grade.in=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    void getAllEmployeesByGradeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where grade is not null
        defaultEmployeesShouldBeFound("grade.specified=true");

        // Get all the employeesList where grade is null
        defaultEmployeesShouldNotBeFound("grade.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByGradeContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where grade contains DEFAULT_GRADE
        defaultEmployeesShouldBeFound("grade.contains=" + DEFAULT_GRADE);

        // Get all the employeesList where grade contains UPDATED_GRADE
        defaultEmployeesShouldNotBeFound("grade.contains=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    void getAllEmployeesByGradeNotContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where grade does not contain DEFAULT_GRADE
        defaultEmployeesShouldNotBeFound("grade.doesNotContain=" + DEFAULT_GRADE);

        // Get all the employeesList where grade does not contain UPDATED_GRADE
        defaultEmployeesShouldBeFound("grade.doesNotContain=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    void getAllEmployeesBySubgradeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where subgrade equals to DEFAULT_SUBGRADE
        defaultEmployeesShouldBeFound("subgrade.equals=" + DEFAULT_SUBGRADE);

        // Get all the employeesList where subgrade equals to UPDATED_SUBGRADE
        defaultEmployeesShouldNotBeFound("subgrade.equals=" + UPDATED_SUBGRADE);
    }

    @Test
    @Transactional
    void getAllEmployeesBySubgradeIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where subgrade in DEFAULT_SUBGRADE or UPDATED_SUBGRADE
        defaultEmployeesShouldBeFound("subgrade.in=" + DEFAULT_SUBGRADE + "," + UPDATED_SUBGRADE);

        // Get all the employeesList where subgrade equals to UPDATED_SUBGRADE
        defaultEmployeesShouldNotBeFound("subgrade.in=" + UPDATED_SUBGRADE);
    }

    @Test
    @Transactional
    void getAllEmployeesBySubgradeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where subgrade is not null
        defaultEmployeesShouldBeFound("subgrade.specified=true");

        // Get all the employeesList where subgrade is null
        defaultEmployeesShouldNotBeFound("subgrade.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesBySubgradeContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where subgrade contains DEFAULT_SUBGRADE
        defaultEmployeesShouldBeFound("subgrade.contains=" + DEFAULT_SUBGRADE);

        // Get all the employeesList where subgrade contains UPDATED_SUBGRADE
        defaultEmployeesShouldNotBeFound("subgrade.contains=" + UPDATED_SUBGRADE);
    }

    @Test
    @Transactional
    void getAllEmployeesBySubgradeNotContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where subgrade does not contain DEFAULT_SUBGRADE
        defaultEmployeesShouldNotBeFound("subgrade.doesNotContain=" + DEFAULT_SUBGRADE);

        // Get all the employeesList where subgrade does not contain UPDATED_SUBGRADE
        defaultEmployeesShouldBeFound("subgrade.doesNotContain=" + UPDATED_SUBGRADE);
    }

    @Test
    @Transactional
    void getAllEmployeesByImageurlIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where imageurl equals to DEFAULT_IMAGEURL
        defaultEmployeesShouldBeFound("imageurl.equals=" + DEFAULT_IMAGEURL);

        // Get all the employeesList where imageurl equals to UPDATED_IMAGEURL
        defaultEmployeesShouldNotBeFound("imageurl.equals=" + UPDATED_IMAGEURL);
    }

    @Test
    @Transactional
    void getAllEmployeesByImageurlIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where imageurl in DEFAULT_IMAGEURL or UPDATED_IMAGEURL
        defaultEmployeesShouldBeFound("imageurl.in=" + DEFAULT_IMAGEURL + "," + UPDATED_IMAGEURL);

        // Get all the employeesList where imageurl equals to UPDATED_IMAGEURL
        defaultEmployeesShouldNotBeFound("imageurl.in=" + UPDATED_IMAGEURL);
    }

    @Test
    @Transactional
    void getAllEmployeesByImageurlIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where imageurl is not null
        defaultEmployeesShouldBeFound("imageurl.specified=true");

        // Get all the employeesList where imageurl is null
        defaultEmployeesShouldNotBeFound("imageurl.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByImageurlContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where imageurl contains DEFAULT_IMAGEURL
        defaultEmployeesShouldBeFound("imageurl.contains=" + DEFAULT_IMAGEURL);

        // Get all the employeesList where imageurl contains UPDATED_IMAGEURL
        defaultEmployeesShouldNotBeFound("imageurl.contains=" + UPDATED_IMAGEURL);
    }

    @Test
    @Transactional
    void getAllEmployeesByImageurlNotContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where imageurl does not contain DEFAULT_IMAGEURL
        defaultEmployeesShouldNotBeFound("imageurl.doesNotContain=" + DEFAULT_IMAGEURL);

        // Get all the employeesList where imageurl does not contain UPDATED_IMAGEURL
        defaultEmployeesShouldBeFound("imageurl.doesNotContain=" + UPDATED_IMAGEURL);
    }

    @Test
    @Transactional
    void getAllEmployeesByResignationdateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where resignationdate equals to DEFAULT_RESIGNATIONDATE
        defaultEmployeesShouldBeFound("resignationdate.equals=" + DEFAULT_RESIGNATIONDATE);

        // Get all the employeesList where resignationdate equals to UPDATED_RESIGNATIONDATE
        defaultEmployeesShouldNotBeFound("resignationdate.equals=" + UPDATED_RESIGNATIONDATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByResignationdateIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where resignationdate in DEFAULT_RESIGNATIONDATE or UPDATED_RESIGNATIONDATE
        defaultEmployeesShouldBeFound("resignationdate.in=" + DEFAULT_RESIGNATIONDATE + "," + UPDATED_RESIGNATIONDATE);

        // Get all the employeesList where resignationdate equals to UPDATED_RESIGNATIONDATE
        defaultEmployeesShouldNotBeFound("resignationdate.in=" + UPDATED_RESIGNATIONDATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByResignationdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where resignationdate is not null
        defaultEmployeesShouldBeFound("resignationdate.specified=true");

        // Get all the employeesList where resignationdate is null
        defaultEmployeesShouldNotBeFound("resignationdate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByGraduationdateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where graduationdate equals to DEFAULT_GRADUATIONDATE
        defaultEmployeesShouldBeFound("graduationdate.equals=" + DEFAULT_GRADUATIONDATE);

        // Get all the employeesList where graduationdate equals to UPDATED_GRADUATIONDATE
        defaultEmployeesShouldNotBeFound("graduationdate.equals=" + UPDATED_GRADUATIONDATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByGraduationdateIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where graduationdate in DEFAULT_GRADUATIONDATE or UPDATED_GRADUATIONDATE
        defaultEmployeesShouldBeFound("graduationdate.in=" + DEFAULT_GRADUATIONDATE + "," + UPDATED_GRADUATIONDATE);

        // Get all the employeesList where graduationdate equals to UPDATED_GRADUATIONDATE
        defaultEmployeesShouldNotBeFound("graduationdate.in=" + UPDATED_GRADUATIONDATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByGraduationdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where graduationdate is not null
        defaultEmployeesShouldBeFound("graduationdate.specified=true");

        // Get all the employeesList where graduationdate is null
        defaultEmployeesShouldNotBeFound("graduationdate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByGraduationdateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where graduationdate is greater than or equal to DEFAULT_GRADUATIONDATE
        defaultEmployeesShouldBeFound("graduationdate.greaterThanOrEqual=" + DEFAULT_GRADUATIONDATE);

        // Get all the employeesList where graduationdate is greater than or equal to UPDATED_GRADUATIONDATE
        defaultEmployeesShouldNotBeFound("graduationdate.greaterThanOrEqual=" + UPDATED_GRADUATIONDATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByGraduationdateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where graduationdate is less than or equal to DEFAULT_GRADUATIONDATE
        defaultEmployeesShouldBeFound("graduationdate.lessThanOrEqual=" + DEFAULT_GRADUATIONDATE);

        // Get all the employeesList where graduationdate is less than or equal to SMALLER_GRADUATIONDATE
        defaultEmployeesShouldNotBeFound("graduationdate.lessThanOrEqual=" + SMALLER_GRADUATIONDATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByGraduationdateIsLessThanSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where graduationdate is less than DEFAULT_GRADUATIONDATE
        defaultEmployeesShouldNotBeFound("graduationdate.lessThan=" + DEFAULT_GRADUATIONDATE);

        // Get all the employeesList where graduationdate is less than UPDATED_GRADUATIONDATE
        defaultEmployeesShouldBeFound("graduationdate.lessThan=" + UPDATED_GRADUATIONDATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByGraduationdateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where graduationdate is greater than DEFAULT_GRADUATIONDATE
        defaultEmployeesShouldNotBeFound("graduationdate.greaterThan=" + DEFAULT_GRADUATIONDATE);

        // Get all the employeesList where graduationdate is greater than SMALLER_GRADUATIONDATE
        defaultEmployeesShouldBeFound("graduationdate.greaterThan=" + SMALLER_GRADUATIONDATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByCareerstartdateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where careerstartdate equals to DEFAULT_CAREERSTARTDATE
        defaultEmployeesShouldBeFound("careerstartdate.equals=" + DEFAULT_CAREERSTARTDATE);

        // Get all the employeesList where careerstartdate equals to UPDATED_CAREERSTARTDATE
        defaultEmployeesShouldNotBeFound("careerstartdate.equals=" + UPDATED_CAREERSTARTDATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByCareerstartdateIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where careerstartdate in DEFAULT_CAREERSTARTDATE or UPDATED_CAREERSTARTDATE
        defaultEmployeesShouldBeFound("careerstartdate.in=" + DEFAULT_CAREERSTARTDATE + "," + UPDATED_CAREERSTARTDATE);

        // Get all the employeesList where careerstartdate equals to UPDATED_CAREERSTARTDATE
        defaultEmployeesShouldNotBeFound("careerstartdate.in=" + UPDATED_CAREERSTARTDATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByCareerstartdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where careerstartdate is not null
        defaultEmployeesShouldBeFound("careerstartdate.specified=true");

        // Get all the employeesList where careerstartdate is null
        defaultEmployeesShouldNotBeFound("careerstartdate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByCareerstartdateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where careerstartdate is greater than or equal to DEFAULT_CAREERSTARTDATE
        defaultEmployeesShouldBeFound("careerstartdate.greaterThanOrEqual=" + DEFAULT_CAREERSTARTDATE);

        // Get all the employeesList where careerstartdate is greater than or equal to UPDATED_CAREERSTARTDATE
        defaultEmployeesShouldNotBeFound("careerstartdate.greaterThanOrEqual=" + UPDATED_CAREERSTARTDATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByCareerstartdateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where careerstartdate is less than or equal to DEFAULT_CAREERSTARTDATE
        defaultEmployeesShouldBeFound("careerstartdate.lessThanOrEqual=" + DEFAULT_CAREERSTARTDATE);

        // Get all the employeesList where careerstartdate is less than or equal to SMALLER_CAREERSTARTDATE
        defaultEmployeesShouldNotBeFound("careerstartdate.lessThanOrEqual=" + SMALLER_CAREERSTARTDATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByCareerstartdateIsLessThanSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where careerstartdate is less than DEFAULT_CAREERSTARTDATE
        defaultEmployeesShouldNotBeFound("careerstartdate.lessThan=" + DEFAULT_CAREERSTARTDATE);

        // Get all the employeesList where careerstartdate is less than UPDATED_CAREERSTARTDATE
        defaultEmployeesShouldBeFound("careerstartdate.lessThan=" + UPDATED_CAREERSTARTDATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByCareerstartdateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where careerstartdate is greater than DEFAULT_CAREERSTARTDATE
        defaultEmployeesShouldNotBeFound("careerstartdate.greaterThan=" + DEFAULT_CAREERSTARTDATE);

        // Get all the employeesList where careerstartdate is greater than SMALLER_CAREERSTARTDATE
        defaultEmployeesShouldBeFound("careerstartdate.greaterThan=" + SMALLER_CAREERSTARTDATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByExternalexpyearsIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where externalexpyears equals to DEFAULT_EXTERNALEXPYEARS
        defaultEmployeesShouldBeFound("externalexpyears.equals=" + DEFAULT_EXTERNALEXPYEARS);

        // Get all the employeesList where externalexpyears equals to UPDATED_EXTERNALEXPYEARS
        defaultEmployeesShouldNotBeFound("externalexpyears.equals=" + UPDATED_EXTERNALEXPYEARS);
    }

    @Test
    @Transactional
    void getAllEmployeesByExternalexpyearsIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where externalexpyears in DEFAULT_EXTERNALEXPYEARS or UPDATED_EXTERNALEXPYEARS
        defaultEmployeesShouldBeFound("externalexpyears.in=" + DEFAULT_EXTERNALEXPYEARS + "," + UPDATED_EXTERNALEXPYEARS);

        // Get all the employeesList where externalexpyears equals to UPDATED_EXTERNALEXPYEARS
        defaultEmployeesShouldNotBeFound("externalexpyears.in=" + UPDATED_EXTERNALEXPYEARS);
    }

    @Test
    @Transactional
    void getAllEmployeesByExternalexpyearsIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where externalexpyears is not null
        defaultEmployeesShouldBeFound("externalexpyears.specified=true");

        // Get all the employeesList where externalexpyears is null
        defaultEmployeesShouldNotBeFound("externalexpyears.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByExternalexpyearsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where externalexpyears is greater than or equal to DEFAULT_EXTERNALEXPYEARS
        defaultEmployeesShouldBeFound("externalexpyears.greaterThanOrEqual=" + DEFAULT_EXTERNALEXPYEARS);

        // Get all the employeesList where externalexpyears is greater than or equal to UPDATED_EXTERNALEXPYEARS
        defaultEmployeesShouldNotBeFound("externalexpyears.greaterThanOrEqual=" + UPDATED_EXTERNALEXPYEARS);
    }

    @Test
    @Transactional
    void getAllEmployeesByExternalexpyearsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where externalexpyears is less than or equal to DEFAULT_EXTERNALEXPYEARS
        defaultEmployeesShouldBeFound("externalexpyears.lessThanOrEqual=" + DEFAULT_EXTERNALEXPYEARS);

        // Get all the employeesList where externalexpyears is less than or equal to SMALLER_EXTERNALEXPYEARS
        defaultEmployeesShouldNotBeFound("externalexpyears.lessThanOrEqual=" + SMALLER_EXTERNALEXPYEARS);
    }

    @Test
    @Transactional
    void getAllEmployeesByExternalexpyearsIsLessThanSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where externalexpyears is less than DEFAULT_EXTERNALEXPYEARS
        defaultEmployeesShouldNotBeFound("externalexpyears.lessThan=" + DEFAULT_EXTERNALEXPYEARS);

        // Get all the employeesList where externalexpyears is less than UPDATED_EXTERNALEXPYEARS
        defaultEmployeesShouldBeFound("externalexpyears.lessThan=" + UPDATED_EXTERNALEXPYEARS);
    }

    @Test
    @Transactional
    void getAllEmployeesByExternalexpyearsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where externalexpyears is greater than DEFAULT_EXTERNALEXPYEARS
        defaultEmployeesShouldNotBeFound("externalexpyears.greaterThan=" + DEFAULT_EXTERNALEXPYEARS);

        // Get all the employeesList where externalexpyears is greater than SMALLER_EXTERNALEXPYEARS
        defaultEmployeesShouldBeFound("externalexpyears.greaterThan=" + SMALLER_EXTERNALEXPYEARS);
    }

    @Test
    @Transactional
    void getAllEmployeesByExternalexpmonthsIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where externalexpmonths equals to DEFAULT_EXTERNALEXPMONTHS
        defaultEmployeesShouldBeFound("externalexpmonths.equals=" + DEFAULT_EXTERNALEXPMONTHS);

        // Get all the employeesList where externalexpmonths equals to UPDATED_EXTERNALEXPMONTHS
        defaultEmployeesShouldNotBeFound("externalexpmonths.equals=" + UPDATED_EXTERNALEXPMONTHS);
    }

    @Test
    @Transactional
    void getAllEmployeesByExternalexpmonthsIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where externalexpmonths in DEFAULT_EXTERNALEXPMONTHS or UPDATED_EXTERNALEXPMONTHS
        defaultEmployeesShouldBeFound("externalexpmonths.in=" + DEFAULT_EXTERNALEXPMONTHS + "," + UPDATED_EXTERNALEXPMONTHS);

        // Get all the employeesList where externalexpmonths equals to UPDATED_EXTERNALEXPMONTHS
        defaultEmployeesShouldNotBeFound("externalexpmonths.in=" + UPDATED_EXTERNALEXPMONTHS);
    }

    @Test
    @Transactional
    void getAllEmployeesByExternalexpmonthsIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where externalexpmonths is not null
        defaultEmployeesShouldBeFound("externalexpmonths.specified=true");

        // Get all the employeesList where externalexpmonths is null
        defaultEmployeesShouldNotBeFound("externalexpmonths.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByExternalexpmonthsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where externalexpmonths is greater than or equal to DEFAULT_EXTERNALEXPMONTHS
        defaultEmployeesShouldBeFound("externalexpmonths.greaterThanOrEqual=" + DEFAULT_EXTERNALEXPMONTHS);

        // Get all the employeesList where externalexpmonths is greater than or equal to UPDATED_EXTERNALEXPMONTHS
        defaultEmployeesShouldNotBeFound("externalexpmonths.greaterThanOrEqual=" + UPDATED_EXTERNALEXPMONTHS);
    }

    @Test
    @Transactional
    void getAllEmployeesByExternalexpmonthsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where externalexpmonths is less than or equal to DEFAULT_EXTERNALEXPMONTHS
        defaultEmployeesShouldBeFound("externalexpmonths.lessThanOrEqual=" + DEFAULT_EXTERNALEXPMONTHS);

        // Get all the employeesList where externalexpmonths is less than or equal to SMALLER_EXTERNALEXPMONTHS
        defaultEmployeesShouldNotBeFound("externalexpmonths.lessThanOrEqual=" + SMALLER_EXTERNALEXPMONTHS);
    }

    @Test
    @Transactional
    void getAllEmployeesByExternalexpmonthsIsLessThanSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where externalexpmonths is less than DEFAULT_EXTERNALEXPMONTHS
        defaultEmployeesShouldNotBeFound("externalexpmonths.lessThan=" + DEFAULT_EXTERNALEXPMONTHS);

        // Get all the employeesList where externalexpmonths is less than UPDATED_EXTERNALEXPMONTHS
        defaultEmployeesShouldBeFound("externalexpmonths.lessThan=" + UPDATED_EXTERNALEXPMONTHS);
    }

    @Test
    @Transactional
    void getAllEmployeesByExternalexpmonthsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where externalexpmonths is greater than DEFAULT_EXTERNALEXPMONTHS
        defaultEmployeesShouldNotBeFound("externalexpmonths.greaterThan=" + DEFAULT_EXTERNALEXPMONTHS);

        // Get all the employeesList where externalexpmonths is greater than SMALLER_EXTERNALEXPMONTHS
        defaultEmployeesShouldBeFound("externalexpmonths.greaterThan=" + SMALLER_EXTERNALEXPMONTHS);
    }

    @Test
    @Transactional
    void getAllEmployeesByPlaceofbirthIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where placeofbirth equals to DEFAULT_PLACEOFBIRTH
        defaultEmployeesShouldBeFound("placeofbirth.equals=" + DEFAULT_PLACEOFBIRTH);

        // Get all the employeesList where placeofbirth equals to UPDATED_PLACEOFBIRTH
        defaultEmployeesShouldNotBeFound("placeofbirth.equals=" + UPDATED_PLACEOFBIRTH);
    }

    @Test
    @Transactional
    void getAllEmployeesByPlaceofbirthIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where placeofbirth in DEFAULT_PLACEOFBIRTH or UPDATED_PLACEOFBIRTH
        defaultEmployeesShouldBeFound("placeofbirth.in=" + DEFAULT_PLACEOFBIRTH + "," + UPDATED_PLACEOFBIRTH);

        // Get all the employeesList where placeofbirth equals to UPDATED_PLACEOFBIRTH
        defaultEmployeesShouldNotBeFound("placeofbirth.in=" + UPDATED_PLACEOFBIRTH);
    }

    @Test
    @Transactional
    void getAllEmployeesByPlaceofbirthIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where placeofbirth is not null
        defaultEmployeesShouldBeFound("placeofbirth.specified=true");

        // Get all the employeesList where placeofbirth is null
        defaultEmployeesShouldNotBeFound("placeofbirth.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByPlaceofbirthContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where placeofbirth contains DEFAULT_PLACEOFBIRTH
        defaultEmployeesShouldBeFound("placeofbirth.contains=" + DEFAULT_PLACEOFBIRTH);

        // Get all the employeesList where placeofbirth contains UPDATED_PLACEOFBIRTH
        defaultEmployeesShouldNotBeFound("placeofbirth.contains=" + UPDATED_PLACEOFBIRTH);
    }

    @Test
    @Transactional
    void getAllEmployeesByPlaceofbirthNotContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where placeofbirth does not contain DEFAULT_PLACEOFBIRTH
        defaultEmployeesShouldNotBeFound("placeofbirth.doesNotContain=" + DEFAULT_PLACEOFBIRTH);

        // Get all the employeesList where placeofbirth does not contain UPDATED_PLACEOFBIRTH
        defaultEmployeesShouldBeFound("placeofbirth.doesNotContain=" + UPDATED_PLACEOFBIRTH);
    }

    @Test
    @Transactional
    void getAllEmployeesByHireddateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where hireddate equals to DEFAULT_HIREDDATE
        defaultEmployeesShouldBeFound("hireddate.equals=" + DEFAULT_HIREDDATE);

        // Get all the employeesList where hireddate equals to UPDATED_HIREDDATE
        defaultEmployeesShouldNotBeFound("hireddate.equals=" + UPDATED_HIREDDATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByHireddateIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where hireddate in DEFAULT_HIREDDATE or UPDATED_HIREDDATE
        defaultEmployeesShouldBeFound("hireddate.in=" + DEFAULT_HIREDDATE + "," + UPDATED_HIREDDATE);

        // Get all the employeesList where hireddate equals to UPDATED_HIREDDATE
        defaultEmployeesShouldNotBeFound("hireddate.in=" + UPDATED_HIREDDATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByHireddateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where hireddate is not null
        defaultEmployeesShouldBeFound("hireddate.specified=true");

        // Get all the employeesList where hireddate is null
        defaultEmployeesShouldNotBeFound("hireddate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByLasttrackingupdateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where lasttrackingupdate equals to DEFAULT_LASTTRACKINGUPDATE
        defaultEmployeesShouldBeFound("lasttrackingupdate.equals=" + DEFAULT_LASTTRACKINGUPDATE);

        // Get all the employeesList where lasttrackingupdate equals to UPDATED_LASTTRACKINGUPDATE
        defaultEmployeesShouldNotBeFound("lasttrackingupdate.equals=" + UPDATED_LASTTRACKINGUPDATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByLasttrackingupdateIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where lasttrackingupdate in DEFAULT_LASTTRACKINGUPDATE or UPDATED_LASTTRACKINGUPDATE
        defaultEmployeesShouldBeFound("lasttrackingupdate.in=" + DEFAULT_LASTTRACKINGUPDATE + "," + UPDATED_LASTTRACKINGUPDATE);

        // Get all the employeesList where lasttrackingupdate equals to UPDATED_LASTTRACKINGUPDATE
        defaultEmployeesShouldNotBeFound("lasttrackingupdate.in=" + UPDATED_LASTTRACKINGUPDATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByLasttrackingupdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where lasttrackingupdate is not null
        defaultEmployeesShouldBeFound("lasttrackingupdate.specified=true");

        // Get all the employeesList where lasttrackingupdate is null
        defaultEmployeesShouldNotBeFound("lasttrackingupdate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByMiddlenameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where middlename equals to DEFAULT_MIDDLENAME
        defaultEmployeesShouldBeFound("middlename.equals=" + DEFAULT_MIDDLENAME);

        // Get all the employeesList where middlename equals to UPDATED_MIDDLENAME
        defaultEmployeesShouldNotBeFound("middlename.equals=" + UPDATED_MIDDLENAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByMiddlenameIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where middlename in DEFAULT_MIDDLENAME or UPDATED_MIDDLENAME
        defaultEmployeesShouldBeFound("middlename.in=" + DEFAULT_MIDDLENAME + "," + UPDATED_MIDDLENAME);

        // Get all the employeesList where middlename equals to UPDATED_MIDDLENAME
        defaultEmployeesShouldNotBeFound("middlename.in=" + UPDATED_MIDDLENAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByMiddlenameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where middlename is not null
        defaultEmployeesShouldBeFound("middlename.specified=true");

        // Get all the employeesList where middlename is null
        defaultEmployeesShouldNotBeFound("middlename.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByMiddlenameContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where middlename contains DEFAULT_MIDDLENAME
        defaultEmployeesShouldBeFound("middlename.contains=" + DEFAULT_MIDDLENAME);

        // Get all the employeesList where middlename contains UPDATED_MIDDLENAME
        defaultEmployeesShouldNotBeFound("middlename.contains=" + UPDATED_MIDDLENAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByMiddlenameNotContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where middlename does not contain DEFAULT_MIDDLENAME
        defaultEmployeesShouldNotBeFound("middlename.doesNotContain=" + DEFAULT_MIDDLENAME);

        // Get all the employeesList where middlename does not contain UPDATED_MIDDLENAME
        defaultEmployeesShouldBeFound("middlename.doesNotContain=" + UPDATED_MIDDLENAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByMobilenumberIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where mobilenumber equals to DEFAULT_MOBILENUMBER
        defaultEmployeesShouldBeFound("mobilenumber.equals=" + DEFAULT_MOBILENUMBER);

        // Get all the employeesList where mobilenumber equals to UPDATED_MOBILENUMBER
        defaultEmployeesShouldNotBeFound("mobilenumber.equals=" + UPDATED_MOBILENUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByMobilenumberIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where mobilenumber in DEFAULT_MOBILENUMBER or UPDATED_MOBILENUMBER
        defaultEmployeesShouldBeFound("mobilenumber.in=" + DEFAULT_MOBILENUMBER + "," + UPDATED_MOBILENUMBER);

        // Get all the employeesList where mobilenumber equals to UPDATED_MOBILENUMBER
        defaultEmployeesShouldNotBeFound("mobilenumber.in=" + UPDATED_MOBILENUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByMobilenumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where mobilenumber is not null
        defaultEmployeesShouldBeFound("mobilenumber.specified=true");

        // Get all the employeesList where mobilenumber is null
        defaultEmployeesShouldNotBeFound("mobilenumber.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByMobilenumberContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where mobilenumber contains DEFAULT_MOBILENUMBER
        defaultEmployeesShouldBeFound("mobilenumber.contains=" + DEFAULT_MOBILENUMBER);

        // Get all the employeesList where mobilenumber contains UPDATED_MOBILENUMBER
        defaultEmployeesShouldNotBeFound("mobilenumber.contains=" + UPDATED_MOBILENUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByMobilenumberNotContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where mobilenumber does not contain DEFAULT_MOBILENUMBER
        defaultEmployeesShouldNotBeFound("mobilenumber.doesNotContain=" + DEFAULT_MOBILENUMBER);

        // Get all the employeesList where mobilenumber does not contain UPDATED_MOBILENUMBER
        defaultEmployeesShouldBeFound("mobilenumber.doesNotContain=" + UPDATED_MOBILENUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByResignationtypeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where resignationtype equals to DEFAULT_RESIGNATIONTYPE
        defaultEmployeesShouldBeFound("resignationtype.equals=" + DEFAULT_RESIGNATIONTYPE);

        // Get all the employeesList where resignationtype equals to UPDATED_RESIGNATIONTYPE
        defaultEmployeesShouldNotBeFound("resignationtype.equals=" + UPDATED_RESIGNATIONTYPE);
    }

    @Test
    @Transactional
    void getAllEmployeesByResignationtypeIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where resignationtype in DEFAULT_RESIGNATIONTYPE or UPDATED_RESIGNATIONTYPE
        defaultEmployeesShouldBeFound("resignationtype.in=" + DEFAULT_RESIGNATIONTYPE + "," + UPDATED_RESIGNATIONTYPE);

        // Get all the employeesList where resignationtype equals to UPDATED_RESIGNATIONTYPE
        defaultEmployeesShouldNotBeFound("resignationtype.in=" + UPDATED_RESIGNATIONTYPE);
    }

    @Test
    @Transactional
    void getAllEmployeesByResignationtypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where resignationtype is not null
        defaultEmployeesShouldBeFound("resignationtype.specified=true");

        // Get all the employeesList where resignationtype is null
        defaultEmployeesShouldNotBeFound("resignationtype.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByResignationtypeContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where resignationtype contains DEFAULT_RESIGNATIONTYPE
        defaultEmployeesShouldBeFound("resignationtype.contains=" + DEFAULT_RESIGNATIONTYPE);

        // Get all the employeesList where resignationtype contains UPDATED_RESIGNATIONTYPE
        defaultEmployeesShouldNotBeFound("resignationtype.contains=" + UPDATED_RESIGNATIONTYPE);
    }

    @Test
    @Transactional
    void getAllEmployeesByResignationtypeNotContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where resignationtype does not contain DEFAULT_RESIGNATIONTYPE
        defaultEmployeesShouldNotBeFound("resignationtype.doesNotContain=" + DEFAULT_RESIGNATIONTYPE);

        // Get all the employeesList where resignationtype does not contain UPDATED_RESIGNATIONTYPE
        defaultEmployeesShouldBeFound("resignationtype.doesNotContain=" + UPDATED_RESIGNATIONTYPE);
    }

    @Test
    @Transactional
    void getAllEmployeesByPrimaryreasonforleavingIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where primaryreasonforleaving equals to DEFAULT_PRIMARYREASONFORLEAVING
        defaultEmployeesShouldBeFound("primaryreasonforleaving.equals=" + DEFAULT_PRIMARYREASONFORLEAVING);

        // Get all the employeesList where primaryreasonforleaving equals to UPDATED_PRIMARYREASONFORLEAVING
        defaultEmployeesShouldNotBeFound("primaryreasonforleaving.equals=" + UPDATED_PRIMARYREASONFORLEAVING);
    }

    @Test
    @Transactional
    void getAllEmployeesByPrimaryreasonforleavingIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where primaryreasonforleaving in DEFAULT_PRIMARYREASONFORLEAVING or UPDATED_PRIMARYREASONFORLEAVING
        defaultEmployeesShouldBeFound(
            "primaryreasonforleaving.in=" + DEFAULT_PRIMARYREASONFORLEAVING + "," + UPDATED_PRIMARYREASONFORLEAVING
        );

        // Get all the employeesList where primaryreasonforleaving equals to UPDATED_PRIMARYREASONFORLEAVING
        defaultEmployeesShouldNotBeFound("primaryreasonforleaving.in=" + UPDATED_PRIMARYREASONFORLEAVING);
    }

    @Test
    @Transactional
    void getAllEmployeesByPrimaryreasonforleavingIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where primaryreasonforleaving is not null
        defaultEmployeesShouldBeFound("primaryreasonforleaving.specified=true");

        // Get all the employeesList where primaryreasonforleaving is null
        defaultEmployeesShouldNotBeFound("primaryreasonforleaving.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByPrimaryreasonforleavingContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where primaryreasonforleaving contains DEFAULT_PRIMARYREASONFORLEAVING
        defaultEmployeesShouldBeFound("primaryreasonforleaving.contains=" + DEFAULT_PRIMARYREASONFORLEAVING);

        // Get all the employeesList where primaryreasonforleaving contains UPDATED_PRIMARYREASONFORLEAVING
        defaultEmployeesShouldNotBeFound("primaryreasonforleaving.contains=" + UPDATED_PRIMARYREASONFORLEAVING);
    }

    @Test
    @Transactional
    void getAllEmployeesByPrimaryreasonforleavingNotContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where primaryreasonforleaving does not contain DEFAULT_PRIMARYREASONFORLEAVING
        defaultEmployeesShouldNotBeFound("primaryreasonforleaving.doesNotContain=" + DEFAULT_PRIMARYREASONFORLEAVING);

        // Get all the employeesList where primaryreasonforleaving does not contain UPDATED_PRIMARYREASONFORLEAVING
        defaultEmployeesShouldBeFound("primaryreasonforleaving.doesNotContain=" + UPDATED_PRIMARYREASONFORLEAVING);
    }

    @Test
    @Transactional
    void getAllEmployeesBySecondaryreasonforleavingIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where secondaryreasonforleaving equals to DEFAULT_SECONDARYREASONFORLEAVING
        defaultEmployeesShouldBeFound("secondaryreasonforleaving.equals=" + DEFAULT_SECONDARYREASONFORLEAVING);

        // Get all the employeesList where secondaryreasonforleaving equals to UPDATED_SECONDARYREASONFORLEAVING
        defaultEmployeesShouldNotBeFound("secondaryreasonforleaving.equals=" + UPDATED_SECONDARYREASONFORLEAVING);
    }

    @Test
    @Transactional
    void getAllEmployeesBySecondaryreasonforleavingIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where secondaryreasonforleaving in DEFAULT_SECONDARYREASONFORLEAVING or UPDATED_SECONDARYREASONFORLEAVING
        defaultEmployeesShouldBeFound(
            "secondaryreasonforleaving.in=" + DEFAULT_SECONDARYREASONFORLEAVING + "," + UPDATED_SECONDARYREASONFORLEAVING
        );

        // Get all the employeesList where secondaryreasonforleaving equals to UPDATED_SECONDARYREASONFORLEAVING
        defaultEmployeesShouldNotBeFound("secondaryreasonforleaving.in=" + UPDATED_SECONDARYREASONFORLEAVING);
    }

    @Test
    @Transactional
    void getAllEmployeesBySecondaryreasonforleavingIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where secondaryreasonforleaving is not null
        defaultEmployeesShouldBeFound("secondaryreasonforleaving.specified=true");

        // Get all the employeesList where secondaryreasonforleaving is null
        defaultEmployeesShouldNotBeFound("secondaryreasonforleaving.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesBySecondaryreasonforleavingContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where secondaryreasonforleaving contains DEFAULT_SECONDARYREASONFORLEAVING
        defaultEmployeesShouldBeFound("secondaryreasonforleaving.contains=" + DEFAULT_SECONDARYREASONFORLEAVING);

        // Get all the employeesList where secondaryreasonforleaving contains UPDATED_SECONDARYREASONFORLEAVING
        defaultEmployeesShouldNotBeFound("secondaryreasonforleaving.contains=" + UPDATED_SECONDARYREASONFORLEAVING);
    }

    @Test
    @Transactional
    void getAllEmployeesBySecondaryreasonforleavingNotContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where secondaryreasonforleaving does not contain DEFAULT_SECONDARYREASONFORLEAVING
        defaultEmployeesShouldNotBeFound("secondaryreasonforleaving.doesNotContain=" + DEFAULT_SECONDARYREASONFORLEAVING);

        // Get all the employeesList where secondaryreasonforleaving does not contain UPDATED_SECONDARYREASONFORLEAVING
        defaultEmployeesShouldBeFound("secondaryreasonforleaving.doesNotContain=" + UPDATED_SECONDARYREASONFORLEAVING);
    }

    @Test
    @Transactional
    void getAllEmployeesByNoticeperioddurationIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where noticeperiodduration equals to DEFAULT_NOTICEPERIODDURATION
        defaultEmployeesShouldBeFound("noticeperiodduration.equals=" + DEFAULT_NOTICEPERIODDURATION);

        // Get all the employeesList where noticeperiodduration equals to UPDATED_NOTICEPERIODDURATION
        defaultEmployeesShouldNotBeFound("noticeperiodduration.equals=" + UPDATED_NOTICEPERIODDURATION);
    }

    @Test
    @Transactional
    void getAllEmployeesByNoticeperioddurationIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where noticeperiodduration in DEFAULT_NOTICEPERIODDURATION or UPDATED_NOTICEPERIODDURATION
        defaultEmployeesShouldBeFound("noticeperiodduration.in=" + DEFAULT_NOTICEPERIODDURATION + "," + UPDATED_NOTICEPERIODDURATION);

        // Get all the employeesList where noticeperiodduration equals to UPDATED_NOTICEPERIODDURATION
        defaultEmployeesShouldNotBeFound("noticeperiodduration.in=" + UPDATED_NOTICEPERIODDURATION);
    }

    @Test
    @Transactional
    void getAllEmployeesByNoticeperioddurationIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where noticeperiodduration is not null
        defaultEmployeesShouldBeFound("noticeperiodduration.specified=true");

        // Get all the employeesList where noticeperiodduration is null
        defaultEmployeesShouldNotBeFound("noticeperiodduration.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByNoticeperioddurationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where noticeperiodduration is greater than or equal to DEFAULT_NOTICEPERIODDURATION
        defaultEmployeesShouldBeFound("noticeperiodduration.greaterThanOrEqual=" + DEFAULT_NOTICEPERIODDURATION);

        // Get all the employeesList where noticeperiodduration is greater than or equal to UPDATED_NOTICEPERIODDURATION
        defaultEmployeesShouldNotBeFound("noticeperiodduration.greaterThanOrEqual=" + UPDATED_NOTICEPERIODDURATION);
    }

    @Test
    @Transactional
    void getAllEmployeesByNoticeperioddurationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where noticeperiodduration is less than or equal to DEFAULT_NOTICEPERIODDURATION
        defaultEmployeesShouldBeFound("noticeperiodduration.lessThanOrEqual=" + DEFAULT_NOTICEPERIODDURATION);

        // Get all the employeesList where noticeperiodduration is less than or equal to SMALLER_NOTICEPERIODDURATION
        defaultEmployeesShouldNotBeFound("noticeperiodduration.lessThanOrEqual=" + SMALLER_NOTICEPERIODDURATION);
    }

    @Test
    @Transactional
    void getAllEmployeesByNoticeperioddurationIsLessThanSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where noticeperiodduration is less than DEFAULT_NOTICEPERIODDURATION
        defaultEmployeesShouldNotBeFound("noticeperiodduration.lessThan=" + DEFAULT_NOTICEPERIODDURATION);

        // Get all the employeesList where noticeperiodduration is less than UPDATED_NOTICEPERIODDURATION
        defaultEmployeesShouldBeFound("noticeperiodduration.lessThan=" + UPDATED_NOTICEPERIODDURATION);
    }

    @Test
    @Transactional
    void getAllEmployeesByNoticeperioddurationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where noticeperiodduration is greater than DEFAULT_NOTICEPERIODDURATION
        defaultEmployeesShouldNotBeFound("noticeperiodduration.greaterThan=" + DEFAULT_NOTICEPERIODDURATION);

        // Get all the employeesList where noticeperiodduration is greater than SMALLER_NOTICEPERIODDURATION
        defaultEmployeesShouldBeFound("noticeperiodduration.greaterThan=" + SMALLER_NOTICEPERIODDURATION);
    }

    @Test
    @Transactional
    void getAllEmployeesByNoticeperiodservedIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where noticeperiodserved equals to DEFAULT_NOTICEPERIODSERVED
        defaultEmployeesShouldBeFound("noticeperiodserved.equals=" + DEFAULT_NOTICEPERIODSERVED);

        // Get all the employeesList where noticeperiodserved equals to UPDATED_NOTICEPERIODSERVED
        defaultEmployeesShouldNotBeFound("noticeperiodserved.equals=" + UPDATED_NOTICEPERIODSERVED);
    }

    @Test
    @Transactional
    void getAllEmployeesByNoticeperiodservedIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where noticeperiodserved in DEFAULT_NOTICEPERIODSERVED or UPDATED_NOTICEPERIODSERVED
        defaultEmployeesShouldBeFound("noticeperiodserved.in=" + DEFAULT_NOTICEPERIODSERVED + "," + UPDATED_NOTICEPERIODSERVED);

        // Get all the employeesList where noticeperiodserved equals to UPDATED_NOTICEPERIODSERVED
        defaultEmployeesShouldNotBeFound("noticeperiodserved.in=" + UPDATED_NOTICEPERIODSERVED);
    }

    @Test
    @Transactional
    void getAllEmployeesByNoticeperiodservedIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where noticeperiodserved is not null
        defaultEmployeesShouldBeFound("noticeperiodserved.specified=true");

        // Get all the employeesList where noticeperiodserved is null
        defaultEmployeesShouldNotBeFound("noticeperiodserved.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByNoticeperiodservedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where noticeperiodserved is greater than or equal to DEFAULT_NOTICEPERIODSERVED
        defaultEmployeesShouldBeFound("noticeperiodserved.greaterThanOrEqual=" + DEFAULT_NOTICEPERIODSERVED);

        // Get all the employeesList where noticeperiodserved is greater than or equal to UPDATED_NOTICEPERIODSERVED
        defaultEmployeesShouldNotBeFound("noticeperiodserved.greaterThanOrEqual=" + UPDATED_NOTICEPERIODSERVED);
    }

    @Test
    @Transactional
    void getAllEmployeesByNoticeperiodservedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where noticeperiodserved is less than or equal to DEFAULT_NOTICEPERIODSERVED
        defaultEmployeesShouldBeFound("noticeperiodserved.lessThanOrEqual=" + DEFAULT_NOTICEPERIODSERVED);

        // Get all the employeesList where noticeperiodserved is less than or equal to SMALLER_NOTICEPERIODSERVED
        defaultEmployeesShouldNotBeFound("noticeperiodserved.lessThanOrEqual=" + SMALLER_NOTICEPERIODSERVED);
    }

    @Test
    @Transactional
    void getAllEmployeesByNoticeperiodservedIsLessThanSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where noticeperiodserved is less than DEFAULT_NOTICEPERIODSERVED
        defaultEmployeesShouldNotBeFound("noticeperiodserved.lessThan=" + DEFAULT_NOTICEPERIODSERVED);

        // Get all the employeesList where noticeperiodserved is less than UPDATED_NOTICEPERIODSERVED
        defaultEmployeesShouldBeFound("noticeperiodserved.lessThan=" + UPDATED_NOTICEPERIODSERVED);
    }

    @Test
    @Transactional
    void getAllEmployeesByNoticeperiodservedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where noticeperiodserved is greater than DEFAULT_NOTICEPERIODSERVED
        defaultEmployeesShouldNotBeFound("noticeperiodserved.greaterThan=" + DEFAULT_NOTICEPERIODSERVED);

        // Get all the employeesList where noticeperiodserved is greater than SMALLER_NOTICEPERIODSERVED
        defaultEmployeesShouldBeFound("noticeperiodserved.greaterThan=" + SMALLER_NOTICEPERIODSERVED);
    }

    @Test
    @Transactional
    void getAllEmployeesByProbationperioddurationIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where probationperiodduration equals to DEFAULT_PROBATIONPERIODDURATION
        defaultEmployeesShouldBeFound("probationperiodduration.equals=" + DEFAULT_PROBATIONPERIODDURATION);

        // Get all the employeesList where probationperiodduration equals to UPDATED_PROBATIONPERIODDURATION
        defaultEmployeesShouldNotBeFound("probationperiodduration.equals=" + UPDATED_PROBATIONPERIODDURATION);
    }

    @Test
    @Transactional
    void getAllEmployeesByProbationperioddurationIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where probationperiodduration in DEFAULT_PROBATIONPERIODDURATION or UPDATED_PROBATIONPERIODDURATION
        defaultEmployeesShouldBeFound(
            "probationperiodduration.in=" + DEFAULT_PROBATIONPERIODDURATION + "," + UPDATED_PROBATIONPERIODDURATION
        );

        // Get all the employeesList where probationperiodduration equals to UPDATED_PROBATIONPERIODDURATION
        defaultEmployeesShouldNotBeFound("probationperiodduration.in=" + UPDATED_PROBATIONPERIODDURATION);
    }

    @Test
    @Transactional
    void getAllEmployeesByProbationperioddurationIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where probationperiodduration is not null
        defaultEmployeesShouldBeFound("probationperiodduration.specified=true");

        // Get all the employeesList where probationperiodduration is null
        defaultEmployeesShouldNotBeFound("probationperiodduration.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByProbationperioddurationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where probationperiodduration is greater than or equal to DEFAULT_PROBATIONPERIODDURATION
        defaultEmployeesShouldBeFound("probationperiodduration.greaterThanOrEqual=" + DEFAULT_PROBATIONPERIODDURATION);

        // Get all the employeesList where probationperiodduration is greater than or equal to UPDATED_PROBATIONPERIODDURATION
        defaultEmployeesShouldNotBeFound("probationperiodduration.greaterThanOrEqual=" + UPDATED_PROBATIONPERIODDURATION);
    }

    @Test
    @Transactional
    void getAllEmployeesByProbationperioddurationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where probationperiodduration is less than or equal to DEFAULT_PROBATIONPERIODDURATION
        defaultEmployeesShouldBeFound("probationperiodduration.lessThanOrEqual=" + DEFAULT_PROBATIONPERIODDURATION);

        // Get all the employeesList where probationperiodduration is less than or equal to SMALLER_PROBATIONPERIODDURATION
        defaultEmployeesShouldNotBeFound("probationperiodduration.lessThanOrEqual=" + SMALLER_PROBATIONPERIODDURATION);
    }

    @Test
    @Transactional
    void getAllEmployeesByProbationperioddurationIsLessThanSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where probationperiodduration is less than DEFAULT_PROBATIONPERIODDURATION
        defaultEmployeesShouldNotBeFound("probationperiodduration.lessThan=" + DEFAULT_PROBATIONPERIODDURATION);

        // Get all the employeesList where probationperiodduration is less than UPDATED_PROBATIONPERIODDURATION
        defaultEmployeesShouldBeFound("probationperiodduration.lessThan=" + UPDATED_PROBATIONPERIODDURATION);
    }

    @Test
    @Transactional
    void getAllEmployeesByProbationperioddurationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where probationperiodduration is greater than DEFAULT_PROBATIONPERIODDURATION
        defaultEmployeesShouldNotBeFound("probationperiodduration.greaterThan=" + DEFAULT_PROBATIONPERIODDURATION);

        // Get all the employeesList where probationperiodduration is greater than SMALLER_PROBATIONPERIODDURATION
        defaultEmployeesShouldBeFound("probationperiodduration.greaterThan=" + SMALLER_PROBATIONPERIODDURATION);
    }

    @Test
    @Transactional
    void getAllEmployeesByLocationIsEqualToSomething() throws Exception {
        Locations location;
        if (TestUtil.findAll(em, Locations.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            location = LocationsResourceIT.createEntity(em);
        } else {
            location = TestUtil.findAll(em, Locations.class).get(0);
        }
        em.persist(location);
        em.flush();
        employees.setLocation(location);
        employeesRepository.saveAndFlush(employees);
        Long locationId = location.getId();

        // Get all the employeesList where location equals to locationId
        defaultEmployeesShouldBeFound("locationId.equals=" + locationId);

        // Get all the employeesList where location equals to (locationId + 1)
        defaultEmployeesShouldNotBeFound("locationId.equals=" + (locationId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByRoleIsEqualToSomething() throws Exception {
        Roles role;
        if (TestUtil.findAll(em, Roles.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            role = RolesResourceIT.createEntity(em);
        } else {
            role = TestUtil.findAll(em, Roles.class).get(0);
        }
        em.persist(role);
        em.flush();
        employees.setRole(role);
        employeesRepository.saveAndFlush(employees);
        Long roleId = role.getId();

        // Get all the employeesList where role equals to roleId
        defaultEmployeesShouldBeFound("roleId.equals=" + roleId);

        // Get all the employeesList where role equals to (roleId + 1)
        defaultEmployeesShouldNotBeFound("roleId.equals=" + (roleId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByManagerIsEqualToSomething() throws Exception {
        Employees manager;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            manager = EmployeesResourceIT.createEntity(em);
        } else {
            manager = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(manager);
        em.flush();
        employees.setManager(manager);
        employeesRepository.saveAndFlush(employees);
        Long managerId = manager.getId();

        // Get all the employeesList where manager equals to managerId
        defaultEmployeesShouldBeFound("managerId.equals=" + managerId);

        // Get all the employeesList where manager equals to (managerId + 1)
        defaultEmployeesShouldNotBeFound("managerId.equals=" + (managerId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByLeaveIsEqualToSomething() throws Exception {
        LeavesOlds leave;
        if (TestUtil.findAll(em, LeavesOlds.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            leave = LeavesOldsResourceIT.createEntity(em);
        } else {
            leave = TestUtil.findAll(em, LeavesOlds.class).get(0);
        }
        em.persist(leave);
        em.flush();
        employees.setLeave(leave);
        employeesRepository.saveAndFlush(employees);
        Long leaveId = leave.getId();

        // Get all the employeesList where leave equals to leaveId
        defaultEmployeesShouldBeFound("leaveId.equals=" + leaveId);

        // Get all the employeesList where leave equals to (leaveId + 1)
        defaultEmployeesShouldNotBeFound("leaveId.equals=" + (leaveId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByDepartmentIsEqualToSomething() throws Exception {
        Departments department;
        if (TestUtil.findAll(em, Departments.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            department = DepartmentsResourceIT.createEntity(em);
        } else {
            department = TestUtil.findAll(em, Departments.class).get(0);
        }
        em.persist(department);
        em.flush();
        employees.setDepartment(department);
        employeesRepository.saveAndFlush(employees);
        Long departmentId = department.getId();

        // Get all the employeesList where department equals to departmentId
        defaultEmployeesShouldBeFound("departmentId.equals=" + departmentId);

        // Get all the employeesList where department equals to (departmentId + 1)
        defaultEmployeesShouldNotBeFound("departmentId.equals=" + (departmentId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByBusinessunitIsEqualToSomething() throws Exception {
        BusinessUnits businessunit;
        if (TestUtil.findAll(em, BusinessUnits.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            businessunit = BusinessUnitsResourceIT.createEntity(em);
        } else {
            businessunit = TestUtil.findAll(em, BusinessUnits.class).get(0);
        }
        em.persist(businessunit);
        em.flush();
        employees.setBusinessunit(businessunit);
        employeesRepository.saveAndFlush(employees);
        Long businessunitId = businessunit.getId();

        // Get all the employeesList where businessunit equals to businessunitId
        defaultEmployeesShouldBeFound("businessunitId.equals=" + businessunitId);

        // Get all the employeesList where businessunit equals to (businessunitId + 1)
        defaultEmployeesShouldNotBeFound("businessunitId.equals=" + (businessunitId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByDivisionIsEqualToSomething() throws Exception {
        Divisions division;
        if (TestUtil.findAll(em, Divisions.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            division = DivisionsResourceIT.createEntity(em);
        } else {
            division = TestUtil.findAll(em, Divisions.class).get(0);
        }
        em.persist(division);
        em.flush();
        employees.setDivision(division);
        employeesRepository.saveAndFlush(employees);
        Long divisionId = division.getId();

        // Get all the employeesList where division equals to divisionId
        defaultEmployeesShouldBeFound("divisionId.equals=" + divisionId);

        // Get all the employeesList where division equals to (divisionId + 1)
        defaultEmployeesShouldNotBeFound("divisionId.equals=" + (divisionId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByCompetencyIsEqualToSomething() throws Exception {
        Competencies competency;
        if (TestUtil.findAll(em, Competencies.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            competency = CompetenciesResourceIT.createEntity(em);
        } else {
            competency = TestUtil.findAll(em, Competencies.class).get(0);
        }
        em.persist(competency);
        em.flush();
        employees.setCompetency(competency);
        employeesRepository.saveAndFlush(employees);
        Long competencyId = competency.getId();

        // Get all the employeesList where competency equals to competencyId
        defaultEmployeesShouldBeFound("competencyId.equals=" + competencyId);

        // Get all the employeesList where competency equals to (competencyId + 1)
        defaultEmployeesShouldNotBeFound("competencyId.equals=" + (competencyId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByEmploymentstatusIsEqualToSomething() throws Exception {
        EmploymentStatuses employmentstatus;
        if (TestUtil.findAll(em, EmploymentStatuses.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            employmentstatus = EmploymentStatusesResourceIT.createEntity(em);
        } else {
            employmentstatus = TestUtil.findAll(em, EmploymentStatuses.class).get(0);
        }
        em.persist(employmentstatus);
        em.flush();
        employees.setEmploymentstatus(employmentstatus);
        employeesRepository.saveAndFlush(employees);
        Long employmentstatusId = employmentstatus.getId();

        // Get all the employeesList where employmentstatus equals to employmentstatusId
        defaultEmployeesShouldBeFound("employmentstatusId.equals=" + employmentstatusId);

        // Get all the employeesList where employmentstatus equals to (employmentstatusId + 1)
        defaultEmployeesShouldNotBeFound("employmentstatusId.equals=" + (employmentstatusId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByEmploymenttypeIsEqualToSomething() throws Exception {
        EmploymentTypes employmenttype;
        if (TestUtil.findAll(em, EmploymentTypes.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            employmenttype = EmploymentTypesResourceIT.createEntity(em);
        } else {
            employmenttype = TestUtil.findAll(em, EmploymentTypes.class).get(0);
        }
        em.persist(employmenttype);
        em.flush();
        employees.setEmploymenttype(employmenttype);
        employeesRepository.saveAndFlush(employees);
        Long employmenttypeId = employmenttype.getId();

        // Get all the employeesList where employmenttype equals to employmenttypeId
        defaultEmployeesShouldBeFound("employmenttypeId.equals=" + employmenttypeId);

        // Get all the employeesList where employmenttype equals to (employmenttypeId + 1)
        defaultEmployeesShouldNotBeFound("employmenttypeId.equals=" + (employmenttypeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByDesignationIsEqualToSomething() throws Exception {
        Designations designation;
        if (TestUtil.findAll(em, Designations.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            designation = DesignationsResourceIT.createEntity(em);
        } else {
            designation = TestUtil.findAll(em, Designations.class).get(0);
        }
        em.persist(designation);
        em.flush();
        employees.setDesignation(designation);
        employeesRepository.saveAndFlush(employees);
        Long designationId = designation.getId();

        // Get all the employeesList where designation equals to designationId
        defaultEmployeesShouldBeFound("designationId.equals=" + designationId);

        // Get all the employeesList where designation equals to (designationId + 1)
        defaultEmployeesShouldNotBeFound("designationId.equals=" + (designationId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByClaimrequestviewsEmployeeIsEqualToSomething() throws Exception {
        ClaimRequestViews claimrequestviewsEmployee;
        if (TestUtil.findAll(em, ClaimRequestViews.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            claimrequestviewsEmployee = ClaimRequestViewsResourceIT.createEntity(em);
        } else {
            claimrequestviewsEmployee = TestUtil.findAll(em, ClaimRequestViews.class).get(0);
        }
        em.persist(claimrequestviewsEmployee);
        em.flush();
        employees.addClaimrequestviewsEmployee(claimrequestviewsEmployee);
        employeesRepository.saveAndFlush(employees);
        Long claimrequestviewsEmployeeId = claimrequestviewsEmployee.getId();

        // Get all the employeesList where claimrequestviewsEmployee equals to claimrequestviewsEmployeeId
        defaultEmployeesShouldBeFound("claimrequestviewsEmployeeId.equals=" + claimrequestviewsEmployeeId);

        // Get all the employeesList where claimrequestviewsEmployee equals to (claimrequestviewsEmployeeId + 1)
        defaultEmployeesShouldNotBeFound("claimrequestviewsEmployeeId.equals=" + (claimrequestviewsEmployeeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByClaimrequestsEmployeeIsEqualToSomething() throws Exception {
        ClaimRequests claimrequestsEmployee;
        if (TestUtil.findAll(em, ClaimRequests.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            claimrequestsEmployee = ClaimRequestsResourceIT.createEntity(em);
        } else {
            claimrequestsEmployee = TestUtil.findAll(em, ClaimRequests.class).get(0);
        }
        em.persist(claimrequestsEmployee);
        em.flush();
        employees.addClaimrequestsEmployee(claimrequestsEmployee);
        employeesRepository.saveAndFlush(employees);
        Long claimrequestsEmployeeId = claimrequestsEmployee.getId();

        // Get all the employeesList where claimrequestsEmployee equals to claimrequestsEmployeeId
        defaultEmployeesShouldBeFound("claimrequestsEmployeeId.equals=" + claimrequestsEmployeeId);

        // Get all the employeesList where claimrequestsEmployee equals to (claimrequestsEmployeeId + 1)
        defaultEmployeesShouldNotBeFound("claimrequestsEmployeeId.equals=" + (claimrequestsEmployeeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByDealresourcesEmployeeIsEqualToSomething() throws Exception {
        DealResources dealresourcesEmployee;
        if (TestUtil.findAll(em, DealResources.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            dealresourcesEmployee = DealResourcesResourceIT.createEntity(em);
        } else {
            dealresourcesEmployee = TestUtil.findAll(em, DealResources.class).get(0);
        }
        em.persist(dealresourcesEmployee);
        em.flush();
        employees.addDealresourcesEmployee(dealresourcesEmployee);
        employeesRepository.saveAndFlush(employees);
        Long dealresourcesEmployeeId = dealresourcesEmployee.getId();

        // Get all the employeesList where dealresourcesEmployee equals to dealresourcesEmployeeId
        defaultEmployeesShouldBeFound("dealresourcesEmployeeId.equals=" + dealresourcesEmployeeId);

        // Get all the employeesList where dealresourcesEmployee equals to (dealresourcesEmployeeId + 1)
        defaultEmployeesShouldNotBeFound("dealresourcesEmployeeId.equals=" + (dealresourcesEmployeeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeaddressesEmployeeIsEqualToSomething() throws Exception {
        EmployeeAddresses employeeaddressesEmployee;
        if (TestUtil.findAll(em, EmployeeAddresses.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            employeeaddressesEmployee = EmployeeAddressesResourceIT.createEntity(em);
        } else {
            employeeaddressesEmployee = TestUtil.findAll(em, EmployeeAddresses.class).get(0);
        }
        em.persist(employeeaddressesEmployee);
        em.flush();
        employees.addEmployeeaddressesEmployee(employeeaddressesEmployee);
        employeesRepository.saveAndFlush(employees);
        Long employeeaddressesEmployeeId = employeeaddressesEmployee.getId();

        // Get all the employeesList where employeeaddressesEmployee equals to employeeaddressesEmployeeId
        defaultEmployeesShouldBeFound("employeeaddressesEmployeeId.equals=" + employeeaddressesEmployeeId);

        // Get all the employeesList where employeeaddressesEmployee equals to (employeeaddressesEmployeeId + 1)
        defaultEmployeesShouldNotBeFound("employeeaddressesEmployeeId.equals=" + (employeeaddressesEmployeeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeebirthdaysEmployeeIsEqualToSomething() throws Exception {
        EmployeeBirthdays employeebirthdaysEmployee;
        if (TestUtil.findAll(em, EmployeeBirthdays.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            employeebirthdaysEmployee = EmployeeBirthdaysResourceIT.createEntity(em);
        } else {
            employeebirthdaysEmployee = TestUtil.findAll(em, EmployeeBirthdays.class).get(0);
        }
        em.persist(employeebirthdaysEmployee);
        em.flush();
        employees.addEmployeebirthdaysEmployee(employeebirthdaysEmployee);
        employeesRepository.saveAndFlush(employees);
        Long employeebirthdaysEmployeeId = employeebirthdaysEmployee.getId();

        // Get all the employeesList where employeebirthdaysEmployee equals to employeebirthdaysEmployeeId
        defaultEmployeesShouldBeFound("employeebirthdaysEmployeeId.equals=" + employeebirthdaysEmployeeId);

        // Get all the employeesList where employeebirthdaysEmployee equals to (employeebirthdaysEmployeeId + 1)
        defaultEmployeesShouldNotBeFound("employeebirthdaysEmployeeId.equals=" + (employeebirthdaysEmployeeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeecertificatesEmployeeIsEqualToSomething() throws Exception {
        EmployeeCertificates employeecertificatesEmployee;
        if (TestUtil.findAll(em, EmployeeCertificates.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            employeecertificatesEmployee = EmployeeCertificatesResourceIT.createEntity(em);
        } else {
            employeecertificatesEmployee = TestUtil.findAll(em, EmployeeCertificates.class).get(0);
        }
        em.persist(employeecertificatesEmployee);
        em.flush();
        employees.addEmployeecertificatesEmployee(employeecertificatesEmployee);
        employeesRepository.saveAndFlush(employees);
        Long employeecertificatesEmployeeId = employeecertificatesEmployee.getId();

        // Get all the employeesList where employeecertificatesEmployee equals to employeecertificatesEmployeeId
        defaultEmployeesShouldBeFound("employeecertificatesEmployeeId.equals=" + employeecertificatesEmployeeId);

        // Get all the employeesList where employeecertificatesEmployee equals to (employeecertificatesEmployeeId + 1)
        defaultEmployeesShouldNotBeFound("employeecertificatesEmployeeId.equals=" + (employeecertificatesEmployeeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeecommentsCommenterIsEqualToSomething() throws Exception {
        EmployeeComments employeecommentsCommenter;
        if (TestUtil.findAll(em, EmployeeComments.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            employeecommentsCommenter = EmployeeCommentsResourceIT.createEntity(em);
        } else {
            employeecommentsCommenter = TestUtil.findAll(em, EmployeeComments.class).get(0);
        }
        em.persist(employeecommentsCommenter);
        em.flush();
        employees.addEmployeecommentsCommenter(employeecommentsCommenter);
        employeesRepository.saveAndFlush(employees);
        Long employeecommentsCommenterId = employeecommentsCommenter.getId();

        // Get all the employeesList where employeecommentsCommenter equals to employeecommentsCommenterId
        defaultEmployeesShouldBeFound("employeecommentsCommenterId.equals=" + employeecommentsCommenterId);

        // Get all the employeesList where employeecommentsCommenter equals to (employeecommentsCommenterId + 1)
        defaultEmployeesShouldNotBeFound("employeecommentsCommenterId.equals=" + (employeecommentsCommenterId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeecommentsEmployeeIsEqualToSomething() throws Exception {
        EmployeeComments employeecommentsEmployee;
        if (TestUtil.findAll(em, EmployeeComments.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            employeecommentsEmployee = EmployeeCommentsResourceIT.createEntity(em);
        } else {
            employeecommentsEmployee = TestUtil.findAll(em, EmployeeComments.class).get(0);
        }
        em.persist(employeecommentsEmployee);
        em.flush();
        employees.addEmployeecommentsEmployee(employeecommentsEmployee);
        employeesRepository.saveAndFlush(employees);
        Long employeecommentsEmployeeId = employeecommentsEmployee.getId();

        // Get all the employeesList where employeecommentsEmployee equals to employeecommentsEmployeeId
        defaultEmployeesShouldBeFound("employeecommentsEmployeeId.equals=" + employeecommentsEmployeeId);

        // Get all the employeesList where employeecommentsEmployee equals to (employeecommentsEmployeeId + 1)
        defaultEmployeesShouldNotBeFound("employeecommentsEmployeeId.equals=" + (employeecommentsEmployeeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeecompensationEmployeeIsEqualToSomething() throws Exception {
        EmployeeCompensation employeecompensationEmployee;
        if (TestUtil.findAll(em, EmployeeCompensation.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            employeecompensationEmployee = EmployeeCompensationResourceIT.createEntity(em);
        } else {
            employeecompensationEmployee = TestUtil.findAll(em, EmployeeCompensation.class).get(0);
        }
        em.persist(employeecompensationEmployee);
        em.flush();
        employees.addEmployeecompensationEmployee(employeecompensationEmployee);
        employeesRepository.saveAndFlush(employees);
        Long employeecompensationEmployeeId = employeecompensationEmployee.getId();

        // Get all the employeesList where employeecompensationEmployee equals to employeecompensationEmployeeId
        defaultEmployeesShouldBeFound("employeecompensationEmployeeId.equals=" + employeecompensationEmployeeId);

        // Get all the employeesList where employeecompensationEmployee equals to (employeecompensationEmployeeId + 1)
        defaultEmployeesShouldNotBeFound("employeecompensationEmployeeId.equals=" + (employeecompensationEmployeeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeecontactsEmployeeIsEqualToSomething() throws Exception {
        EmployeeContacts employeecontactsEmployee;
        if (TestUtil.findAll(em, EmployeeContacts.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            employeecontactsEmployee = EmployeeContactsResourceIT.createEntity(em);
        } else {
            employeecontactsEmployee = TestUtil.findAll(em, EmployeeContacts.class).get(0);
        }
        em.persist(employeecontactsEmployee);
        em.flush();
        employees.addEmployeecontactsEmployee(employeecontactsEmployee);
        employeesRepository.saveAndFlush(employees);
        Long employeecontactsEmployeeId = employeecontactsEmployee.getId();

        // Get all the employeesList where employeecontactsEmployee equals to employeecontactsEmployeeId
        defaultEmployeesShouldBeFound("employeecontactsEmployeeId.equals=" + employeecontactsEmployeeId);

        // Get all the employeesList where employeecontactsEmployee equals to (employeecontactsEmployeeId + 1)
        defaultEmployeesShouldNotBeFound("employeecontactsEmployeeId.equals=" + (employeecontactsEmployeeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeedetailsEmployeeIsEqualToSomething() throws Exception {
        EmployeeDetails employeedetailsEmployee;
        if (TestUtil.findAll(em, EmployeeDetails.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            employeedetailsEmployee = EmployeeDetailsResourceIT.createEntity(em);
        } else {
            employeedetailsEmployee = TestUtil.findAll(em, EmployeeDetails.class).get(0);
        }
        em.persist(employeedetailsEmployee);
        em.flush();
        employees.addEmployeedetailsEmployee(employeedetailsEmployee);
        employeesRepository.saveAndFlush(employees);
        Long employeedetailsEmployeeId = employeedetailsEmployee.getId();

        // Get all the employeesList where employeedetailsEmployee equals to employeedetailsEmployeeId
        defaultEmployeesShouldBeFound("employeedetailsEmployeeId.equals=" + employeedetailsEmployeeId);

        // Get all the employeesList where employeedetailsEmployee equals to (employeedetailsEmployeeId + 1)
        defaultEmployeesShouldNotBeFound("employeedetailsEmployeeId.equals=" + (employeedetailsEmployeeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeedocumentsEmployeeIsEqualToSomething() throws Exception {
        EmployeeDocuments employeedocumentsEmployee;
        if (TestUtil.findAll(em, EmployeeDocuments.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            employeedocumentsEmployee = EmployeeDocumentsResourceIT.createEntity(em);
        } else {
            employeedocumentsEmployee = TestUtil.findAll(em, EmployeeDocuments.class).get(0);
        }
        em.persist(employeedocumentsEmployee);
        em.flush();
        employees.addEmployeedocumentsEmployee(employeedocumentsEmployee);
        employeesRepository.saveAndFlush(employees);
        Long employeedocumentsEmployeeId = employeedocumentsEmployee.getId();

        // Get all the employeesList where employeedocumentsEmployee equals to employeedocumentsEmployeeId
        defaultEmployeesShouldBeFound("employeedocumentsEmployeeId.equals=" + employeedocumentsEmployeeId);

        // Get all the employeesList where employeedocumentsEmployee equals to (employeedocumentsEmployeeId + 1)
        defaultEmployeesShouldNotBeFound("employeedocumentsEmployeeId.equals=" + (employeedocumentsEmployeeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeeducationEmployeeIsEqualToSomething() throws Exception {
        EmployeeEducation employeeeducationEmployee;
        if (TestUtil.findAll(em, EmployeeEducation.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            employeeeducationEmployee = EmployeeEducationResourceIT.createEntity(em);
        } else {
            employeeeducationEmployee = TestUtil.findAll(em, EmployeeEducation.class).get(0);
        }
        em.persist(employeeeducationEmployee);
        em.flush();
        employees.addEmployeeeducationEmployee(employeeeducationEmployee);
        employeesRepository.saveAndFlush(employees);
        Long employeeeducationEmployeeId = employeeeducationEmployee.getId();

        // Get all the employeesList where employeeeducationEmployee equals to employeeeducationEmployeeId
        defaultEmployeesShouldBeFound("employeeeducationEmployeeId.equals=" + employeeeducationEmployeeId);

        // Get all the employeesList where employeeeducationEmployee equals to (employeeeducationEmployeeId + 1)
        defaultEmployeesShouldNotBeFound("employeeeducationEmployeeId.equals=" + (employeeeducationEmployeeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeemergencycontactsEmployeeIsEqualToSomething() throws Exception {
        EmployeeEmergencyContacts employeeemergencycontactsEmployee;
        if (TestUtil.findAll(em, EmployeeEmergencyContacts.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            employeeemergencycontactsEmployee = EmployeeEmergencyContactsResourceIT.createEntity(em);
        } else {
            employeeemergencycontactsEmployee = TestUtil.findAll(em, EmployeeEmergencyContacts.class).get(0);
        }
        em.persist(employeeemergencycontactsEmployee);
        em.flush();
        employees.addEmployeeemergencycontactsEmployee(employeeemergencycontactsEmployee);
        employeesRepository.saveAndFlush(employees);
        Long employeeemergencycontactsEmployeeId = employeeemergencycontactsEmployee.getId();

        // Get all the employeesList where employeeemergencycontactsEmployee equals to employeeemergencycontactsEmployeeId
        defaultEmployeesShouldBeFound("employeeemergencycontactsEmployeeId.equals=" + employeeemergencycontactsEmployeeId);

        // Get all the employeesList where employeeemergencycontactsEmployee equals to (employeeemergencycontactsEmployeeId + 1)
        defaultEmployeesShouldNotBeFound("employeeemergencycontactsEmployeeId.equals=" + (employeeemergencycontactsEmployeeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeefamilyinfoEmployeeIsEqualToSomething() throws Exception {
        EmployeeFamilyInfo employeefamilyinfoEmployee;
        if (TestUtil.findAll(em, EmployeeFamilyInfo.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            employeefamilyinfoEmployee = EmployeeFamilyInfoResourceIT.createEntity(em);
        } else {
            employeefamilyinfoEmployee = TestUtil.findAll(em, EmployeeFamilyInfo.class).get(0);
        }
        em.persist(employeefamilyinfoEmployee);
        em.flush();
        employees.addEmployeefamilyinfoEmployee(employeefamilyinfoEmployee);
        employeesRepository.saveAndFlush(employees);
        Long employeefamilyinfoEmployeeId = employeefamilyinfoEmployee.getId();

        // Get all the employeesList where employeefamilyinfoEmployee equals to employeefamilyinfoEmployeeId
        defaultEmployeesShouldBeFound("employeefamilyinfoEmployeeId.equals=" + employeefamilyinfoEmployeeId);

        // Get all the employeesList where employeefamilyinfoEmployee equals to (employeefamilyinfoEmployeeId + 1)
        defaultEmployeesShouldNotBeFound("employeefamilyinfoEmployeeId.equals=" + (employeefamilyinfoEmployeeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeejobinfoEmployeeIsEqualToSomething() throws Exception {
        EmployeeJobInfo employeejobinfoEmployee;
        if (TestUtil.findAll(em, EmployeeJobInfo.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            employeejobinfoEmployee = EmployeeJobInfoResourceIT.createEntity(em);
        } else {
            employeejobinfoEmployee = TestUtil.findAll(em, EmployeeJobInfo.class).get(0);
        }
        em.persist(employeejobinfoEmployee);
        em.flush();
        employees.addEmployeejobinfoEmployee(employeejobinfoEmployee);
        employeesRepository.saveAndFlush(employees);
        Long employeejobinfoEmployeeId = employeejobinfoEmployee.getId();

        // Get all the employeesList where employeejobinfoEmployee equals to employeejobinfoEmployeeId
        defaultEmployeesShouldBeFound("employeejobinfoEmployeeId.equals=" + employeejobinfoEmployeeId);

        // Get all the employeesList where employeejobinfoEmployee equals to (employeejobinfoEmployeeId + 1)
        defaultEmployeesShouldNotBeFound("employeejobinfoEmployeeId.equals=" + (employeejobinfoEmployeeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeejobinfoReviewmanagerIsEqualToSomething() throws Exception {
        EmployeeJobInfo employeejobinfoReviewmanager;
        if (TestUtil.findAll(em, EmployeeJobInfo.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            employeejobinfoReviewmanager = EmployeeJobInfoResourceIT.createEntity(em);
        } else {
            employeejobinfoReviewmanager = TestUtil.findAll(em, EmployeeJobInfo.class).get(0);
        }
        em.persist(employeejobinfoReviewmanager);
        em.flush();
        employees.addEmployeejobinfoReviewmanager(employeejobinfoReviewmanager);
        employeesRepository.saveAndFlush(employees);
        Long employeejobinfoReviewmanagerId = employeejobinfoReviewmanager.getId();

        // Get all the employeesList where employeejobinfoReviewmanager equals to employeejobinfoReviewmanagerId
        defaultEmployeesShouldBeFound("employeejobinfoReviewmanagerId.equals=" + employeejobinfoReviewmanagerId);

        // Get all the employeesList where employeejobinfoReviewmanager equals to (employeejobinfoReviewmanagerId + 1)
        defaultEmployeesShouldNotBeFound("employeejobinfoReviewmanagerId.equals=" + (employeejobinfoReviewmanagerId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeejobinfoManagerIsEqualToSomething() throws Exception {
        EmployeeJobInfo employeejobinfoManager;
        if (TestUtil.findAll(em, EmployeeJobInfo.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            employeejobinfoManager = EmployeeJobInfoResourceIT.createEntity(em);
        } else {
            employeejobinfoManager = TestUtil.findAll(em, EmployeeJobInfo.class).get(0);
        }
        em.persist(employeejobinfoManager);
        em.flush();
        employees.addEmployeejobinfoManager(employeejobinfoManager);
        employeesRepository.saveAndFlush(employees);
        Long employeejobinfoManagerId = employeejobinfoManager.getId();

        // Get all the employeesList where employeejobinfoManager equals to employeejobinfoManagerId
        defaultEmployeesShouldBeFound("employeejobinfoManagerId.equals=" + employeejobinfoManagerId);

        // Get all the employeesList where employeejobinfoManager equals to (employeejobinfoManagerId + 1)
        defaultEmployeesShouldNotBeFound("employeejobinfoManagerId.equals=" + (employeejobinfoManagerId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeprofilehistorylogsEmployeeIsEqualToSomething() throws Exception {
        EmployeeProfileHistoryLogs employeeprofilehistorylogsEmployee;
        if (TestUtil.findAll(em, EmployeeProfileHistoryLogs.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            employeeprofilehistorylogsEmployee = EmployeeProfileHistoryLogsResourceIT.createEntity(em);
        } else {
            employeeprofilehistorylogsEmployee = TestUtil.findAll(em, EmployeeProfileHistoryLogs.class).get(0);
        }
        em.persist(employeeprofilehistorylogsEmployee);
        em.flush();
        employees.addEmployeeprofilehistorylogsEmployee(employeeprofilehistorylogsEmployee);
        employeesRepository.saveAndFlush(employees);
        Long employeeprofilehistorylogsEmployeeId = employeeprofilehistorylogsEmployee.getId();

        // Get all the employeesList where employeeprofilehistorylogsEmployee equals to employeeprofilehistorylogsEmployeeId
        defaultEmployeesShouldBeFound("employeeprofilehistorylogsEmployeeId.equals=" + employeeprofilehistorylogsEmployeeId);

        // Get all the employeesList where employeeprofilehistorylogsEmployee equals to (employeeprofilehistorylogsEmployeeId + 1)
        defaultEmployeesShouldNotBeFound("employeeprofilehistorylogsEmployeeId.equals=" + (employeeprofilehistorylogsEmployeeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeprojectratingsProjectarchitectIsEqualToSomething() throws Exception {
        EmployeeProjectRatings employeeprojectratingsProjectarchitect;
        if (TestUtil.findAll(em, EmployeeProjectRatings.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            employeeprojectratingsProjectarchitect = EmployeeProjectRatingsResourceIT.createEntity(em);
        } else {
            employeeprojectratingsProjectarchitect = TestUtil.findAll(em, EmployeeProjectRatings.class).get(0);
        }
        em.persist(employeeprojectratingsProjectarchitect);
        em.flush();
        employees.addEmployeeprojectratingsProjectarchitect(employeeprojectratingsProjectarchitect);
        employeesRepository.saveAndFlush(employees);
        Long employeeprojectratingsProjectarchitectId = employeeprojectratingsProjectarchitect.getId();

        // Get all the employeesList where employeeprojectratingsProjectarchitect equals to employeeprojectratingsProjectarchitectId
        defaultEmployeesShouldBeFound("employeeprojectratingsProjectarchitectId.equals=" + employeeprojectratingsProjectarchitectId);

        // Get all the employeesList where employeeprojectratingsProjectarchitect equals to (employeeprojectratingsProjectarchitectId + 1)
        defaultEmployeesShouldNotBeFound(
            "employeeprojectratingsProjectarchitectId.equals=" + (employeeprojectratingsProjectarchitectId + 1)
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeprojectratingsProjectmanagerIsEqualToSomething() throws Exception {
        EmployeeProjectRatings employeeprojectratingsProjectmanager;
        if (TestUtil.findAll(em, EmployeeProjectRatings.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            employeeprojectratingsProjectmanager = EmployeeProjectRatingsResourceIT.createEntity(em);
        } else {
            employeeprojectratingsProjectmanager = TestUtil.findAll(em, EmployeeProjectRatings.class).get(0);
        }
        em.persist(employeeprojectratingsProjectmanager);
        em.flush();
        employees.addEmployeeprojectratingsProjectmanager(employeeprojectratingsProjectmanager);
        employeesRepository.saveAndFlush(employees);
        Long employeeprojectratingsProjectmanagerId = employeeprojectratingsProjectmanager.getId();

        // Get all the employeesList where employeeprojectratingsProjectmanager equals to employeeprojectratingsProjectmanagerId
        defaultEmployeesShouldBeFound("employeeprojectratingsProjectmanagerId.equals=" + employeeprojectratingsProjectmanagerId);

        // Get all the employeesList where employeeprojectratingsProjectmanager equals to (employeeprojectratingsProjectmanagerId + 1)
        defaultEmployeesShouldNotBeFound("employeeprojectratingsProjectmanagerId.equals=" + (employeeprojectratingsProjectmanagerId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeprojectratingsEmployeeIsEqualToSomething() throws Exception {
        EmployeeProjectRatings employeeprojectratingsEmployee;
        if (TestUtil.findAll(em, EmployeeProjectRatings.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            employeeprojectratingsEmployee = EmployeeProjectRatingsResourceIT.createEntity(em);
        } else {
            employeeprojectratingsEmployee = TestUtil.findAll(em, EmployeeProjectRatings.class).get(0);
        }
        em.persist(employeeprojectratingsEmployee);
        em.flush();
        employees.addEmployeeprojectratingsEmployee(employeeprojectratingsEmployee);
        employeesRepository.saveAndFlush(employees);
        Long employeeprojectratingsEmployeeId = employeeprojectratingsEmployee.getId();

        // Get all the employeesList where employeeprojectratingsEmployee equals to employeeprojectratingsEmployeeId
        defaultEmployeesShouldBeFound("employeeprojectratingsEmployeeId.equals=" + employeeprojectratingsEmployeeId);

        // Get all the employeesList where employeeprojectratingsEmployee equals to (employeeprojectratingsEmployeeId + 1)
        defaultEmployeesShouldNotBeFound("employeeprojectratingsEmployeeId.equals=" + (employeeprojectratingsEmployeeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeprojectsEmployeeIsEqualToSomething() throws Exception {
        EmployeeProjects employeeprojectsEmployee;
        if (TestUtil.findAll(em, EmployeeProjects.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            employeeprojectsEmployee = EmployeeProjectsResourceIT.createEntity(em);
        } else {
            employeeprojectsEmployee = TestUtil.findAll(em, EmployeeProjects.class).get(0);
        }
        em.persist(employeeprojectsEmployee);
        em.flush();
        employees.addEmployeeprojectsEmployee(employeeprojectsEmployee);
        employeesRepository.saveAndFlush(employees);
        Long employeeprojectsEmployeeId = employeeprojectsEmployee.getId();

        // Get all the employeesList where employeeprojectsEmployee equals to employeeprojectsEmployeeId
        defaultEmployeesShouldBeFound("employeeprojectsEmployeeId.equals=" + employeeprojectsEmployeeId);

        // Get all the employeesList where employeeprojectsEmployee equals to (employeeprojectsEmployeeId + 1)
        defaultEmployeesShouldNotBeFound("employeeprojectsEmployeeId.equals=" + (employeeprojectsEmployeeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeprojectsAssignorIsEqualToSomething() throws Exception {
        EmployeeProjects employeeprojectsAssignor;
        if (TestUtil.findAll(em, EmployeeProjects.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            employeeprojectsAssignor = EmployeeProjectsResourceIT.createEntity(em);
        } else {
            employeeprojectsAssignor = TestUtil.findAll(em, EmployeeProjects.class).get(0);
        }
        em.persist(employeeprojectsAssignor);
        em.flush();
        employees.addEmployeeprojectsAssignor(employeeprojectsAssignor);
        employeesRepository.saveAndFlush(employees);
        Long employeeprojectsAssignorId = employeeprojectsAssignor.getId();

        // Get all the employeesList where employeeprojectsAssignor equals to employeeprojectsAssignorId
        defaultEmployeesShouldBeFound("employeeprojectsAssignorId.equals=" + employeeprojectsAssignorId);

        // Get all the employeesList where employeeprojectsAssignor equals to (employeeprojectsAssignorId + 1)
        defaultEmployeesShouldNotBeFound("employeeprojectsAssignorId.equals=" + (employeeprojectsAssignorId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeskillsEmployeeIsEqualToSomething() throws Exception {
        EmployeeSkills employeeskillsEmployee;
        if (TestUtil.findAll(em, EmployeeSkills.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            employeeskillsEmployee = EmployeeSkillsResourceIT.createEntity(em);
        } else {
            employeeskillsEmployee = TestUtil.findAll(em, EmployeeSkills.class).get(0);
        }
        em.persist(employeeskillsEmployee);
        em.flush();
        employees.addEmployeeskillsEmployee(employeeskillsEmployee);
        employeesRepository.saveAndFlush(employees);
        Long employeeskillsEmployeeId = employeeskillsEmployee.getId();

        // Get all the employeesList where employeeskillsEmployee equals to employeeskillsEmployeeId
        defaultEmployeesShouldBeFound("employeeskillsEmployeeId.equals=" + employeeskillsEmployeeId);

        // Get all the employeesList where employeeskillsEmployee equals to (employeeskillsEmployeeId + 1)
        defaultEmployeesShouldNotBeFound("employeeskillsEmployeeId.equals=" + (employeeskillsEmployeeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeetalentsEmployeeIsEqualToSomething() throws Exception {
        EmployeeTalents employeetalentsEmployee;
        if (TestUtil.findAll(em, EmployeeTalents.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            employeetalentsEmployee = EmployeeTalentsResourceIT.createEntity(em);
        } else {
            employeetalentsEmployee = TestUtil.findAll(em, EmployeeTalents.class).get(0);
        }
        em.persist(employeetalentsEmployee);
        em.flush();
        employees.addEmployeetalentsEmployee(employeetalentsEmployee);
        employeesRepository.saveAndFlush(employees);
        Long employeetalentsEmployeeId = employeetalentsEmployee.getId();

        // Get all the employeesList where employeetalentsEmployee equals to employeetalentsEmployeeId
        defaultEmployeesShouldBeFound("employeetalentsEmployeeId.equals=" + employeetalentsEmployeeId);

        // Get all the employeesList where employeetalentsEmployee equals to (employeetalentsEmployeeId + 1)
        defaultEmployeesShouldNotBeFound("employeetalentsEmployeeId.equals=" + (employeetalentsEmployeeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeworksEmployeeIsEqualToSomething() throws Exception {
        EmployeeWorks employeeworksEmployee;
        if (TestUtil.findAll(em, EmployeeWorks.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            employeeworksEmployee = EmployeeWorksResourceIT.createEntity(em);
        } else {
            employeeworksEmployee = TestUtil.findAll(em, EmployeeWorks.class).get(0);
        }
        em.persist(employeeworksEmployee);
        em.flush();
        employees.addEmployeeworksEmployee(employeeworksEmployee);
        employeesRepository.saveAndFlush(employees);
        Long employeeworksEmployeeId = employeeworksEmployee.getId();

        // Get all the employeesList where employeeworksEmployee equals to employeeworksEmployeeId
        defaultEmployeesShouldBeFound("employeeworksEmployeeId.equals=" + employeeworksEmployeeId);

        // Get all the employeesList where employeeworksEmployee equals to (employeeworksEmployeeId + 1)
        defaultEmployeesShouldNotBeFound("employeeworksEmployeeId.equals=" + (employeeworksEmployeeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeesManagerIsEqualToSomething() throws Exception {
        Employees employeesManager;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            employeesManager = EmployeesResourceIT.createEntity(em);
        } else {
            employeesManager = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employeesManager);
        em.flush();
        employees.addEmployeesManager(employeesManager);
        employeesRepository.saveAndFlush(employees);
        Long employeesManagerId = employeesManager.getId();

        // Get all the employeesList where employeesManager equals to employeesManagerId
        defaultEmployeesShouldBeFound("employeesManagerId.equals=" + employeesManagerId);

        // Get all the employeesList where employeesManager equals to (employeesManagerId + 1)
        defaultEmployeesShouldNotBeFound("employeesManagerId.equals=" + (employeesManagerId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByEmploymenthistoryEmployeeIsEqualToSomething() throws Exception {
        EmploymentHistory employmenthistoryEmployee;
        if (TestUtil.findAll(em, EmploymentHistory.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            employmenthistoryEmployee = EmploymentHistoryResourceIT.createEntity(em);
        } else {
            employmenthistoryEmployee = TestUtil.findAll(em, EmploymentHistory.class).get(0);
        }
        em.persist(employmenthistoryEmployee);
        em.flush();
        employees.addEmploymenthistoryEmployee(employmenthistoryEmployee);
        employeesRepository.saveAndFlush(employees);
        Long employmenthistoryEmployeeId = employmenthistoryEmployee.getId();

        // Get all the employeesList where employmenthistoryEmployee equals to employmenthistoryEmployeeId
        defaultEmployeesShouldBeFound("employmenthistoryEmployeeId.equals=" + employmenthistoryEmployeeId);

        // Get all the employeesList where employmenthistoryEmployee equals to (employmenthistoryEmployeeId + 1)
        defaultEmployeesShouldNotBeFound("employmenthistoryEmployeeId.equals=" + (employmenthistoryEmployeeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByFeedbackquestionsEmployeeIsEqualToSomething() throws Exception {
        FeedbackQuestions feedbackquestionsEmployee;
        if (TestUtil.findAll(em, FeedbackQuestions.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            feedbackquestionsEmployee = FeedbackQuestionsResourceIT.createEntity(em);
        } else {
            feedbackquestionsEmployee = TestUtil.findAll(em, FeedbackQuestions.class).get(0);
        }
        em.persist(feedbackquestionsEmployee);
        em.flush();
        employees.addFeedbackquestionsEmployee(feedbackquestionsEmployee);
        employeesRepository.saveAndFlush(employees);
        Long feedbackquestionsEmployeeId = feedbackquestionsEmployee.getId();

        // Get all the employeesList where feedbackquestionsEmployee equals to feedbackquestionsEmployeeId
        defaultEmployeesShouldBeFound("feedbackquestionsEmployeeId.equals=" + feedbackquestionsEmployeeId);

        // Get all the employeesList where feedbackquestionsEmployee equals to (feedbackquestionsEmployeeId + 1)
        defaultEmployeesShouldNotBeFound("feedbackquestionsEmployeeId.equals=" + (feedbackquestionsEmployeeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByFeedbackrequestsEmployeeIsEqualToSomething() throws Exception {
        FeedbackRequests feedbackrequestsEmployee;
        if (TestUtil.findAll(em, FeedbackRequests.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            feedbackrequestsEmployee = FeedbackRequestsResourceIT.createEntity(em);
        } else {
            feedbackrequestsEmployee = TestUtil.findAll(em, FeedbackRequests.class).get(0);
        }
        em.persist(feedbackrequestsEmployee);
        em.flush();
        employees.addFeedbackrequestsEmployee(feedbackrequestsEmployee);
        employeesRepository.saveAndFlush(employees);
        Long feedbackrequestsEmployeeId = feedbackrequestsEmployee.getId();

        // Get all the employeesList where feedbackrequestsEmployee equals to feedbackrequestsEmployeeId
        defaultEmployeesShouldBeFound("feedbackrequestsEmployeeId.equals=" + feedbackrequestsEmployeeId);

        // Get all the employeesList where feedbackrequestsEmployee equals to (feedbackrequestsEmployeeId + 1)
        defaultEmployeesShouldNotBeFound("feedbackrequestsEmployeeId.equals=" + (feedbackrequestsEmployeeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByFeedbackrequestsLinemanagerIsEqualToSomething() throws Exception {
        FeedbackRequests feedbackrequestsLinemanager;
        if (TestUtil.findAll(em, FeedbackRequests.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            feedbackrequestsLinemanager = FeedbackRequestsResourceIT.createEntity(em);
        } else {
            feedbackrequestsLinemanager = TestUtil.findAll(em, FeedbackRequests.class).get(0);
        }
        em.persist(feedbackrequestsLinemanager);
        em.flush();
        employees.addFeedbackrequestsLinemanager(feedbackrequestsLinemanager);
        employeesRepository.saveAndFlush(employees);
        Long feedbackrequestsLinemanagerId = feedbackrequestsLinemanager.getId();

        // Get all the employeesList where feedbackrequestsLinemanager equals to feedbackrequestsLinemanagerId
        defaultEmployeesShouldBeFound("feedbackrequestsLinemanagerId.equals=" + feedbackrequestsLinemanagerId);

        // Get all the employeesList where feedbackrequestsLinemanager equals to (feedbackrequestsLinemanagerId + 1)
        defaultEmployeesShouldNotBeFound("feedbackrequestsLinemanagerId.equals=" + (feedbackrequestsLinemanagerId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByFeedbackrespondentsEmployeeIsEqualToSomething() throws Exception {
        FeedbackRespondents feedbackrespondentsEmployee;
        if (TestUtil.findAll(em, FeedbackRespondents.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            feedbackrespondentsEmployee = FeedbackRespondentsResourceIT.createEntity(em);
        } else {
            feedbackrespondentsEmployee = TestUtil.findAll(em, FeedbackRespondents.class).get(0);
        }
        em.persist(feedbackrespondentsEmployee);
        em.flush();
        employees.addFeedbackrespondentsEmployee(feedbackrespondentsEmployee);
        employeesRepository.saveAndFlush(employees);
        Long feedbackrespondentsEmployeeId = feedbackrespondentsEmployee.getId();

        // Get all the employeesList where feedbackrespondentsEmployee equals to feedbackrespondentsEmployeeId
        defaultEmployeesShouldBeFound("feedbackrespondentsEmployeeId.equals=" + feedbackrespondentsEmployeeId);

        // Get all the employeesList where feedbackrespondentsEmployee equals to (feedbackrespondentsEmployeeId + 1)
        defaultEmployeesShouldNotBeFound("feedbackrespondentsEmployeeId.equals=" + (feedbackrespondentsEmployeeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByInterviewsEmployeeIsEqualToSomething() throws Exception {
        Interviews interviewsEmployee;
        if (TestUtil.findAll(em, Interviews.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            interviewsEmployee = InterviewsResourceIT.createEntity(em);
        } else {
            interviewsEmployee = TestUtil.findAll(em, Interviews.class).get(0);
        }
        em.persist(interviewsEmployee);
        em.flush();
        employees.addInterviewsEmployee(interviewsEmployee);
        employeesRepository.saveAndFlush(employees);
        Long interviewsEmployeeId = interviewsEmployee.getId();

        // Get all the employeesList where interviewsEmployee equals to interviewsEmployeeId
        defaultEmployeesShouldBeFound("interviewsEmployeeId.equals=" + interviewsEmployeeId);

        // Get all the employeesList where interviewsEmployee equals to (interviewsEmployeeId + 1)
        defaultEmployeesShouldNotBeFound("interviewsEmployeeId.equals=" + (interviewsEmployeeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByLeavedetailadjustmentlogsAdjustedbyIsEqualToSomething() throws Exception {
        LeaveDetailAdjustmentLogs leavedetailadjustmentlogsAdjustedby;
        if (TestUtil.findAll(em, LeaveDetailAdjustmentLogs.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            leavedetailadjustmentlogsAdjustedby = LeaveDetailAdjustmentLogsResourceIT.createEntity(em);
        } else {
            leavedetailadjustmentlogsAdjustedby = TestUtil.findAll(em, LeaveDetailAdjustmentLogs.class).get(0);
        }
        em.persist(leavedetailadjustmentlogsAdjustedby);
        em.flush();
        employees.addLeavedetailadjustmentlogsAdjustedby(leavedetailadjustmentlogsAdjustedby);
        employeesRepository.saveAndFlush(employees);
        Long leavedetailadjustmentlogsAdjustedbyId = leavedetailadjustmentlogsAdjustedby.getId();

        // Get all the employeesList where leavedetailadjustmentlogsAdjustedby equals to leavedetailadjustmentlogsAdjustedbyId
        defaultEmployeesShouldBeFound("leavedetailadjustmentlogsAdjustedbyId.equals=" + leavedetailadjustmentlogsAdjustedbyId);

        // Get all the employeesList where leavedetailadjustmentlogsAdjustedby equals to (leavedetailadjustmentlogsAdjustedbyId + 1)
        defaultEmployeesShouldNotBeFound("leavedetailadjustmentlogsAdjustedbyId.equals=" + (leavedetailadjustmentlogsAdjustedbyId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByLeaverequestapproversUserIsEqualToSomething() throws Exception {
        LeaveRequestApprovers leaverequestapproversUser;
        if (TestUtil.findAll(em, LeaveRequestApprovers.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            leaverequestapproversUser = LeaveRequestApproversResourceIT.createEntity(em);
        } else {
            leaverequestapproversUser = TestUtil.findAll(em, LeaveRequestApprovers.class).get(0);
        }
        em.persist(leaverequestapproversUser);
        em.flush();
        employees.addLeaverequestapproversUser(leaverequestapproversUser);
        employeesRepository.saveAndFlush(employees);
        Long leaverequestapproversUserId = leaverequestapproversUser.getId();

        // Get all the employeesList where leaverequestapproversUser equals to leaverequestapproversUserId
        defaultEmployeesShouldBeFound("leaverequestapproversUserId.equals=" + leaverequestapproversUserId);

        // Get all the employeesList where leaverequestapproversUser equals to (leaverequestapproversUserId + 1)
        defaultEmployeesShouldNotBeFound("leaverequestapproversUserId.equals=" + (leaverequestapproversUserId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByLeaverequestsoldsManagerIsEqualToSomething() throws Exception {
        LeaveRequestsOlds leaverequestsoldsManager;
        if (TestUtil.findAll(em, LeaveRequestsOlds.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            leaverequestsoldsManager = LeaveRequestsOldsResourceIT.createEntity(em);
        } else {
            leaverequestsoldsManager = TestUtil.findAll(em, LeaveRequestsOlds.class).get(0);
        }
        em.persist(leaverequestsoldsManager);
        em.flush();
        employees.addLeaverequestsoldsManager(leaverequestsoldsManager);
        employeesRepository.saveAndFlush(employees);
        Long leaverequestsoldsManagerId = leaverequestsoldsManager.getId();

        // Get all the employeesList where leaverequestsoldsManager equals to leaverequestsoldsManagerId
        defaultEmployeesShouldBeFound("leaverequestsoldsManagerId.equals=" + leaverequestsoldsManagerId);

        // Get all the employeesList where leaverequestsoldsManager equals to (leaverequestsoldsManagerId + 1)
        defaultEmployeesShouldNotBeFound("leaverequestsoldsManagerId.equals=" + (leaverequestsoldsManagerId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByLeaverequestsoldsEmployeeIsEqualToSomething() throws Exception {
        LeaveRequestsOlds leaverequestsoldsEmployee;
        if (TestUtil.findAll(em, LeaveRequestsOlds.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            leaverequestsoldsEmployee = LeaveRequestsOldsResourceIT.createEntity(em);
        } else {
            leaverequestsoldsEmployee = TestUtil.findAll(em, LeaveRequestsOlds.class).get(0);
        }
        em.persist(leaverequestsoldsEmployee);
        em.flush();
        employees.addLeaverequestsoldsEmployee(leaverequestsoldsEmployee);
        employeesRepository.saveAndFlush(employees);
        Long leaverequestsoldsEmployeeId = leaverequestsoldsEmployee.getId();

        // Get all the employeesList where leaverequestsoldsEmployee equals to leaverequestsoldsEmployeeId
        defaultEmployeesShouldBeFound("leaverequestsoldsEmployeeId.equals=" + leaverequestsoldsEmployeeId);

        // Get all the employeesList where leaverequestsoldsEmployee equals to (leaverequestsoldsEmployeeId + 1)
        defaultEmployeesShouldNotBeFound("leaverequestsoldsEmployeeId.equals=" + (leaverequestsoldsEmployeeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByLeavesUserIsEqualToSomething() throws Exception {
        Leaves leavesUser;
        if (TestUtil.findAll(em, Leaves.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            leavesUser = LeavesResourceIT.createEntity(em);
        } else {
            leavesUser = TestUtil.findAll(em, Leaves.class).get(0);
        }
        em.persist(leavesUser);
        em.flush();
        employees.addLeavesUser(leavesUser);
        employeesRepository.saveAndFlush(employees);
        Long leavesUserId = leavesUser.getId();

        // Get all the employeesList where leavesUser equals to leavesUserId
        defaultEmployeesShouldBeFound("leavesUserId.equals=" + leavesUserId);

        // Get all the employeesList where leavesUser equals to (leavesUserId + 1)
        defaultEmployeesShouldNotBeFound("leavesUserId.equals=" + (leavesUserId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByNotificationsentemaillogsRecipientIsEqualToSomething() throws Exception {
        NotificationSentEmailLogs notificationsentemaillogsRecipient;
        if (TestUtil.findAll(em, NotificationSentEmailLogs.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            notificationsentemaillogsRecipient = NotificationSentEmailLogsResourceIT.createEntity(em);
        } else {
            notificationsentemaillogsRecipient = TestUtil.findAll(em, NotificationSentEmailLogs.class).get(0);
        }
        em.persist(notificationsentemaillogsRecipient);
        em.flush();
        employees.addNotificationsentemaillogsRecipient(notificationsentemaillogsRecipient);
        employeesRepository.saveAndFlush(employees);
        Long notificationsentemaillogsRecipientId = notificationsentemaillogsRecipient.getId();

        // Get all the employeesList where notificationsentemaillogsRecipient equals to notificationsentemaillogsRecipientId
        defaultEmployeesShouldBeFound("notificationsentemaillogsRecipientId.equals=" + notificationsentemaillogsRecipientId);

        // Get all the employeesList where notificationsentemaillogsRecipient equals to (notificationsentemaillogsRecipientId + 1)
        defaultEmployeesShouldNotBeFound("notificationsentemaillogsRecipientId.equals=" + (notificationsentemaillogsRecipientId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByPcratingsEmployeeIsEqualToSomething() throws Exception {
        PcRatings pcratingsEmployee;
        if (TestUtil.findAll(em, PcRatings.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            pcratingsEmployee = PcRatingsResourceIT.createEntity(em);
        } else {
            pcratingsEmployee = TestUtil.findAll(em, PcRatings.class).get(0);
        }
        em.persist(pcratingsEmployee);
        em.flush();
        employees.addPcratingsEmployee(pcratingsEmployee);
        employeesRepository.saveAndFlush(employees);
        Long pcratingsEmployeeId = pcratingsEmployee.getId();

        // Get all the employeesList where pcratingsEmployee equals to pcratingsEmployeeId
        defaultEmployeesShouldBeFound("pcratingsEmployeeId.equals=" + pcratingsEmployeeId);

        // Get all the employeesList where pcratingsEmployee equals to (pcratingsEmployeeId + 1)
        defaultEmployeesShouldNotBeFound("pcratingsEmployeeId.equals=" + (pcratingsEmployeeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByPcratingstrainingsSuccessorforIsEqualToSomething() throws Exception {
        PcRatingsTrainings pcratingstrainingsSuccessorfor;
        if (TestUtil.findAll(em, PcRatingsTrainings.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            pcratingstrainingsSuccessorfor = PcRatingsTrainingsResourceIT.createEntity(em);
        } else {
            pcratingstrainingsSuccessorfor = TestUtil.findAll(em, PcRatingsTrainings.class).get(0);
        }
        em.persist(pcratingstrainingsSuccessorfor);
        em.flush();
        employees.addPcratingstrainingsSuccessorfor(pcratingstrainingsSuccessorfor);
        employeesRepository.saveAndFlush(employees);
        Long pcratingstrainingsSuccessorforId = pcratingstrainingsSuccessorfor.getId();

        // Get all the employeesList where pcratingstrainingsSuccessorfor equals to pcratingstrainingsSuccessorforId
        defaultEmployeesShouldBeFound("pcratingstrainingsSuccessorforId.equals=" + pcratingstrainingsSuccessorforId);

        // Get all the employeesList where pcratingstrainingsSuccessorfor equals to (pcratingstrainingsSuccessorforId + 1)
        defaultEmployeesShouldNotBeFound("pcratingstrainingsSuccessorforId.equals=" + (pcratingstrainingsSuccessorforId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByPerformancecycleemployeeratingEmployeeIsEqualToSomething() throws Exception {
        PerformanceCycleEmployeeRating performancecycleemployeeratingEmployee;
        if (TestUtil.findAll(em, PerformanceCycleEmployeeRating.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            performancecycleemployeeratingEmployee = PerformanceCycleEmployeeRatingResourceIT.createEntity(em);
        } else {
            performancecycleemployeeratingEmployee = TestUtil.findAll(em, PerformanceCycleEmployeeRating.class).get(0);
        }
        em.persist(performancecycleemployeeratingEmployee);
        em.flush();
        employees.addPerformancecycleemployeeratingEmployee(performancecycleemployeeratingEmployee);
        employeesRepository.saveAndFlush(employees);
        Long performancecycleemployeeratingEmployeeId = performancecycleemployeeratingEmployee.getId();

        // Get all the employeesList where performancecycleemployeeratingEmployee equals to performancecycleemployeeratingEmployeeId
        defaultEmployeesShouldBeFound("performancecycleemployeeratingEmployeeId.equals=" + performancecycleemployeeratingEmployeeId);

        // Get all the employeesList where performancecycleemployeeratingEmployee equals to (performancecycleemployeeratingEmployeeId + 1)
        defaultEmployeesShouldNotBeFound(
            "performancecycleemployeeratingEmployeeId.equals=" + (performancecycleemployeeratingEmployeeId + 1)
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByPerformancecycleemployeeratingManagerIsEqualToSomething() throws Exception {
        PerformanceCycleEmployeeRating performancecycleemployeeratingManager;
        if (TestUtil.findAll(em, PerformanceCycleEmployeeRating.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            performancecycleemployeeratingManager = PerformanceCycleEmployeeRatingResourceIT.createEntity(em);
        } else {
            performancecycleemployeeratingManager = TestUtil.findAll(em, PerformanceCycleEmployeeRating.class).get(0);
        }
        em.persist(performancecycleemployeeratingManager);
        em.flush();
        employees.addPerformancecycleemployeeratingManager(performancecycleemployeeratingManager);
        employeesRepository.saveAndFlush(employees);
        Long performancecycleemployeeratingManagerId = performancecycleemployeeratingManager.getId();

        // Get all the employeesList where performancecycleemployeeratingManager equals to performancecycleemployeeratingManagerId
        defaultEmployeesShouldBeFound("performancecycleemployeeratingManagerId.equals=" + performancecycleemployeeratingManagerId);

        // Get all the employeesList where performancecycleemployeeratingManager equals to (performancecycleemployeeratingManagerId + 1)
        defaultEmployeesShouldNotBeFound("performancecycleemployeeratingManagerId.equals=" + (performancecycleemployeeratingManagerId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByProjectcyclesAllowedafterduedatebyIsEqualToSomething() throws Exception {
        ProjectCycles projectcyclesAllowedafterduedateby;
        if (TestUtil.findAll(em, ProjectCycles.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            projectcyclesAllowedafterduedateby = ProjectCyclesResourceIT.createEntity(em);
        } else {
            projectcyclesAllowedafterduedateby = TestUtil.findAll(em, ProjectCycles.class).get(0);
        }
        em.persist(projectcyclesAllowedafterduedateby);
        em.flush();
        employees.addProjectcyclesAllowedafterduedateby(projectcyclesAllowedafterduedateby);
        employeesRepository.saveAndFlush(employees);
        Long projectcyclesAllowedafterduedatebyId = projectcyclesAllowedafterduedateby.getId();

        // Get all the employeesList where projectcyclesAllowedafterduedateby equals to projectcyclesAllowedafterduedatebyId
        defaultEmployeesShouldBeFound("projectcyclesAllowedafterduedatebyId.equals=" + projectcyclesAllowedafterduedatebyId);

        // Get all the employeesList where projectcyclesAllowedafterduedateby equals to (projectcyclesAllowedafterduedatebyId + 1)
        defaultEmployeesShouldNotBeFound("projectcyclesAllowedafterduedatebyId.equals=" + (projectcyclesAllowedafterduedatebyId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByProjectcyclesArchitectIsEqualToSomething() throws Exception {
        ProjectCycles projectcyclesArchitect;
        if (TestUtil.findAll(em, ProjectCycles.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            projectcyclesArchitect = ProjectCyclesResourceIT.createEntity(em);
        } else {
            projectcyclesArchitect = TestUtil.findAll(em, ProjectCycles.class).get(0);
        }
        em.persist(projectcyclesArchitect);
        em.flush();
        employees.addProjectcyclesArchitect(projectcyclesArchitect);
        employeesRepository.saveAndFlush(employees);
        Long projectcyclesArchitectId = projectcyclesArchitect.getId();

        // Get all the employeesList where projectcyclesArchitect equals to projectcyclesArchitectId
        defaultEmployeesShouldBeFound("projectcyclesArchitectId.equals=" + projectcyclesArchitectId);

        // Get all the employeesList where projectcyclesArchitect equals to (projectcyclesArchitectId + 1)
        defaultEmployeesShouldNotBeFound("projectcyclesArchitectId.equals=" + (projectcyclesArchitectId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByProjectcyclesProjectmanagerIsEqualToSomething() throws Exception {
        ProjectCycles projectcyclesProjectmanager;
        if (TestUtil.findAll(em, ProjectCycles.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            projectcyclesProjectmanager = ProjectCyclesResourceIT.createEntity(em);
        } else {
            projectcyclesProjectmanager = TestUtil.findAll(em, ProjectCycles.class).get(0);
        }
        em.persist(projectcyclesProjectmanager);
        em.flush();
        employees.addProjectcyclesProjectmanager(projectcyclesProjectmanager);
        employeesRepository.saveAndFlush(employees);
        Long projectcyclesProjectmanagerId = projectcyclesProjectmanager.getId();

        // Get all the employeesList where projectcyclesProjectmanager equals to projectcyclesProjectmanagerId
        defaultEmployeesShouldBeFound("projectcyclesProjectmanagerId.equals=" + projectcyclesProjectmanagerId);

        // Get all the employeesList where projectcyclesProjectmanager equals to (projectcyclesProjectmanagerId + 1)
        defaultEmployeesShouldNotBeFound("projectcyclesProjectmanagerId.equals=" + (projectcyclesProjectmanagerId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByProjectleadershipEmployeeIsEqualToSomething() throws Exception {
        ProjectLeadership projectleadershipEmployee;
        if (TestUtil.findAll(em, ProjectLeadership.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            projectleadershipEmployee = ProjectLeadershipResourceIT.createEntity(em);
        } else {
            projectleadershipEmployee = TestUtil.findAll(em, ProjectLeadership.class).get(0);
        }
        em.persist(projectleadershipEmployee);
        em.flush();
        employees.addProjectleadershipEmployee(projectleadershipEmployee);
        employeesRepository.saveAndFlush(employees);
        Long projectleadershipEmployeeId = projectleadershipEmployee.getId();

        // Get all the employeesList where projectleadershipEmployee equals to projectleadershipEmployeeId
        defaultEmployeesShouldBeFound("projectleadershipEmployeeId.equals=" + projectleadershipEmployeeId);

        // Get all the employeesList where projectleadershipEmployee equals to (projectleadershipEmployeeId + 1)
        defaultEmployeesShouldNotBeFound("projectleadershipEmployeeId.equals=" + (projectleadershipEmployeeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByProjectsProjectmanagerIsEqualToSomething() throws Exception {
        Projects projectsProjectmanager;
        if (TestUtil.findAll(em, Projects.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            projectsProjectmanager = ProjectsResourceIT.createEntity(em);
        } else {
            projectsProjectmanager = TestUtil.findAll(em, Projects.class).get(0);
        }
        em.persist(projectsProjectmanager);
        em.flush();
        employees.addProjectsProjectmanager(projectsProjectmanager);
        employeesRepository.saveAndFlush(employees);
        Long projectsProjectmanagerId = projectsProjectmanager.getId();

        // Get all the employeesList where projectsProjectmanager equals to projectsProjectmanagerId
        defaultEmployeesShouldBeFound("projectsProjectmanagerId.equals=" + projectsProjectmanagerId);

        // Get all the employeesList where projectsProjectmanager equals to (projectsProjectmanagerId + 1)
        defaultEmployeesShouldNotBeFound("projectsProjectmanagerId.equals=" + (projectsProjectmanagerId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByRatingsRaterIsEqualToSomething() throws Exception {
        Ratings ratingsRater;
        if (TestUtil.findAll(em, Ratings.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            ratingsRater = RatingsResourceIT.createEntity(em);
        } else {
            ratingsRater = TestUtil.findAll(em, Ratings.class).get(0);
        }
        em.persist(ratingsRater);
        em.flush();
        employees.addRatingsRater(ratingsRater);
        employeesRepository.saveAndFlush(employees);
        Long ratingsRaterId = ratingsRater.getId();

        // Get all the employeesList where ratingsRater equals to ratingsRaterId
        defaultEmployeesShouldBeFound("ratingsRaterId.equals=" + ratingsRaterId);

        // Get all the employeesList where ratingsRater equals to (ratingsRaterId + 1)
        defaultEmployeesShouldNotBeFound("ratingsRaterId.equals=" + (ratingsRaterId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByUserattributesUserIsEqualToSomething() throws Exception {
        UserAttributes userattributesUser;
        if (TestUtil.findAll(em, UserAttributes.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            userattributesUser = UserAttributesResourceIT.createEntity(em);
        } else {
            userattributesUser = TestUtil.findAll(em, UserAttributes.class).get(0);
        }
        em.persist(userattributesUser);
        em.flush();
        employees.addUserattributesUser(userattributesUser);
        employeesRepository.saveAndFlush(employees);
        Long userattributesUserId = userattributesUser.getId();

        // Get all the employeesList where userattributesUser equals to userattributesUserId
        defaultEmployeesShouldBeFound("userattributesUserId.equals=" + userattributesUserId);

        // Get all the employeesList where userattributesUser equals to (userattributesUserId + 1)
        defaultEmployeesShouldNotBeFound("userattributesUserId.equals=" + (userattributesUserId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByUsergoalsUserIsEqualToSomething() throws Exception {
        UserGoals usergoalsUser;
        if (TestUtil.findAll(em, UserGoals.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            usergoalsUser = UserGoalsResourceIT.createEntity(em);
        } else {
            usergoalsUser = TestUtil.findAll(em, UserGoals.class).get(0);
        }
        em.persist(usergoalsUser);
        em.flush();
        employees.addUsergoalsUser(usergoalsUser);
        employeesRepository.saveAndFlush(employees);
        Long usergoalsUserId = usergoalsUser.getId();

        // Get all the employeesList where usergoalsUser equals to usergoalsUserId
        defaultEmployeesShouldBeFound("usergoalsUserId.equals=" + usergoalsUserId);

        // Get all the employeesList where usergoalsUser equals to (usergoalsUserId + 1)
        defaultEmployeesShouldNotBeFound("usergoalsUserId.equals=" + (usergoalsUserId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByUserratingsUserIsEqualToSomething() throws Exception {
        UserRatings userratingsUser;
        if (TestUtil.findAll(em, UserRatings.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            userratingsUser = UserRatingsResourceIT.createEntity(em);
        } else {
            userratingsUser = TestUtil.findAll(em, UserRatings.class).get(0);
        }
        em.persist(userratingsUser);
        em.flush();
        employees.addUserratingsUser(userratingsUser);
        employeesRepository.saveAndFlush(employees);
        Long userratingsUserId = userratingsUser.getId();

        // Get all the employeesList where userratingsUser equals to userratingsUserId
        defaultEmployeesShouldBeFound("userratingsUserId.equals=" + userratingsUserId);

        // Get all the employeesList where userratingsUser equals to (userratingsUserId + 1)
        defaultEmployeesShouldNotBeFound("userratingsUserId.equals=" + (userratingsUserId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByUserratingsReviewmanagerIsEqualToSomething() throws Exception {
        UserRatings userratingsReviewmanager;
        if (TestUtil.findAll(em, UserRatings.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            userratingsReviewmanager = UserRatingsResourceIT.createEntity(em);
        } else {
            userratingsReviewmanager = TestUtil.findAll(em, UserRatings.class).get(0);
        }
        em.persist(userratingsReviewmanager);
        em.flush();
        employees.addUserratingsReviewmanager(userratingsReviewmanager);
        employeesRepository.saveAndFlush(employees);
        Long userratingsReviewmanagerId = userratingsReviewmanager.getId();

        // Get all the employeesList where userratingsReviewmanager equals to userratingsReviewmanagerId
        defaultEmployeesShouldBeFound("userratingsReviewmanagerId.equals=" + userratingsReviewmanagerId);

        // Get all the employeesList where userratingsReviewmanager equals to (userratingsReviewmanagerId + 1)
        defaultEmployeesShouldNotBeFound("userratingsReviewmanagerId.equals=" + (userratingsReviewmanagerId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByUserratingsremarksRaterIsEqualToSomething() throws Exception {
        UserRatingsRemarks userratingsremarksRater;
        if (TestUtil.findAll(em, UserRatingsRemarks.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            userratingsremarksRater = UserRatingsRemarksResourceIT.createEntity(em);
        } else {
            userratingsremarksRater = TestUtil.findAll(em, UserRatingsRemarks.class).get(0);
        }
        em.persist(userratingsremarksRater);
        em.flush();
        employees.addUserratingsremarksRater(userratingsremarksRater);
        employeesRepository.saveAndFlush(employees);
        Long userratingsremarksRaterId = userratingsremarksRater.getId();

        // Get all the employeesList where userratingsremarksRater equals to userratingsremarksRaterId
        defaultEmployeesShouldBeFound("userratingsremarksRaterId.equals=" + userratingsremarksRaterId);

        // Get all the employeesList where userratingsremarksRater equals to (userratingsremarksRaterId + 1)
        defaultEmployeesShouldNotBeFound("userratingsremarksRaterId.equals=" + (userratingsremarksRaterId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByUserrelationsUserIsEqualToSomething() throws Exception {
        UserRelations userrelationsUser;
        if (TestUtil.findAll(em, UserRelations.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            userrelationsUser = UserRelationsResourceIT.createEntity(em);
        } else {
            userrelationsUser = TestUtil.findAll(em, UserRelations.class).get(0);
        }
        em.persist(userrelationsUser);
        em.flush();
        employees.addUserrelationsUser(userrelationsUser);
        employeesRepository.saveAndFlush(employees);
        Long userrelationsUserId = userrelationsUser.getId();

        // Get all the employeesList where userrelationsUser equals to userrelationsUserId
        defaultEmployeesShouldBeFound("userrelationsUserId.equals=" + userrelationsUserId);

        // Get all the employeesList where userrelationsUser equals to (userrelationsUserId + 1)
        defaultEmployeesShouldNotBeFound("userrelationsUserId.equals=" + (userrelationsUserId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByUserrelationsRelateduserIsEqualToSomething() throws Exception {
        UserRelations userrelationsRelateduser;
        if (TestUtil.findAll(em, UserRelations.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            userrelationsRelateduser = UserRelationsResourceIT.createEntity(em);
        } else {
            userrelationsRelateduser = TestUtil.findAll(em, UserRelations.class).get(0);
        }
        em.persist(userrelationsRelateduser);
        em.flush();
        employees.addUserrelationsRelateduser(userrelationsRelateduser);
        employeesRepository.saveAndFlush(employees);
        Long userrelationsRelateduserId = userrelationsRelateduser.getId();

        // Get all the employeesList where userrelationsRelateduser equals to userrelationsRelateduserId
        defaultEmployeesShouldBeFound("userrelationsRelateduserId.equals=" + userrelationsRelateduserId);

        // Get all the employeesList where userrelationsRelateduser equals to (userrelationsRelateduserId + 1)
        defaultEmployeesShouldNotBeFound("userrelationsRelateduserId.equals=" + (userrelationsRelateduserId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByWorklogsEmployeeIsEqualToSomething() throws Exception {
        WorkLogs worklogsEmployee;
        if (TestUtil.findAll(em, WorkLogs.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            worklogsEmployee = WorkLogsResourceIT.createEntity(em);
        } else {
            worklogsEmployee = TestUtil.findAll(em, WorkLogs.class).get(0);
        }
        em.persist(worklogsEmployee);
        em.flush();
        employees.addWorklogsEmployee(worklogsEmployee);
        employeesRepository.saveAndFlush(employees);
        Long worklogsEmployeeId = worklogsEmployee.getId();

        // Get all the employeesList where worklogsEmployee equals to worklogsEmployeeId
        defaultEmployeesShouldBeFound("worklogsEmployeeId.equals=" + worklogsEmployeeId);

        // Get all the employeesList where worklogsEmployee equals to (worklogsEmployeeId + 1)
        defaultEmployeesShouldNotBeFound("worklogsEmployeeId.equals=" + (worklogsEmployeeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByZemployeeprojectsEmployeeIsEqualToSomething() throws Exception {
        ZEmployeeProjects zemployeeprojectsEmployee;
        if (TestUtil.findAll(em, ZEmployeeProjects.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            zemployeeprojectsEmployee = ZEmployeeProjectsResourceIT.createEntity(em);
        } else {
            zemployeeprojectsEmployee = TestUtil.findAll(em, ZEmployeeProjects.class).get(0);
        }
        em.persist(zemployeeprojectsEmployee);
        em.flush();
        employees.addZemployeeprojectsEmployee(zemployeeprojectsEmployee);
        employeesRepository.saveAndFlush(employees);
        Long zemployeeprojectsEmployeeId = zemployeeprojectsEmployee.getId();

        // Get all the employeesList where zemployeeprojectsEmployee equals to zemployeeprojectsEmployeeId
        defaultEmployeesShouldBeFound("zemployeeprojectsEmployeeId.equals=" + zemployeeprojectsEmployeeId);

        // Get all the employeesList where zemployeeprojectsEmployee equals to (zemployeeprojectsEmployeeId + 1)
        defaultEmployeesShouldNotBeFound("zemployeeprojectsEmployeeId.equals=" + (zemployeeprojectsEmployeeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByZemployeeprojectsAssignorIsEqualToSomething() throws Exception {
        ZEmployeeProjects zemployeeprojectsAssignor;
        if (TestUtil.findAll(em, ZEmployeeProjects.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            zemployeeprojectsAssignor = ZEmployeeProjectsResourceIT.createEntity(em);
        } else {
            zemployeeprojectsAssignor = TestUtil.findAll(em, ZEmployeeProjects.class).get(0);
        }
        em.persist(zemployeeprojectsAssignor);
        em.flush();
        employees.addZemployeeprojectsAssignor(zemployeeprojectsAssignor);
        employeesRepository.saveAndFlush(employees);
        Long zemployeeprojectsAssignorId = zemployeeprojectsAssignor.getId();

        // Get all the employeesList where zemployeeprojectsAssignor equals to zemployeeprojectsAssignorId
        defaultEmployeesShouldBeFound("zemployeeprojectsAssignorId.equals=" + zemployeeprojectsAssignorId);

        // Get all the employeesList where zemployeeprojectsAssignor equals to (zemployeeprojectsAssignorId + 1)
        defaultEmployeesShouldNotBeFound("zemployeeprojectsAssignorId.equals=" + (zemployeeprojectsAssignorId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByZemployeeprojectsProjectmanagerIsEqualToSomething() throws Exception {
        ZEmployeeProjects zemployeeprojectsProjectmanager;
        if (TestUtil.findAll(em, ZEmployeeProjects.class).isEmpty()) {
            employeesRepository.saveAndFlush(employees);
            zemployeeprojectsProjectmanager = ZEmployeeProjectsResourceIT.createEntity(em);
        } else {
            zemployeeprojectsProjectmanager = TestUtil.findAll(em, ZEmployeeProjects.class).get(0);
        }
        em.persist(zemployeeprojectsProjectmanager);
        em.flush();
        employees.addZemployeeprojectsProjectmanager(zemployeeprojectsProjectmanager);
        employeesRepository.saveAndFlush(employees);
        Long zemployeeprojectsProjectmanagerId = zemployeeprojectsProjectmanager.getId();

        // Get all the employeesList where zemployeeprojectsProjectmanager equals to zemployeeprojectsProjectmanagerId
        defaultEmployeesShouldBeFound("zemployeeprojectsProjectmanagerId.equals=" + zemployeeprojectsProjectmanagerId);

        // Get all the employeesList where zemployeeprojectsProjectmanager equals to (zemployeeprojectsProjectmanagerId + 1)
        defaultEmployeesShouldNotBeFound("zemployeeprojectsProjectmanagerId.equals=" + (zemployeeprojectsProjectmanagerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeesShouldBeFound(String filter) throws Exception {
        restEmployeesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employees.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME)))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME)))
            .andExpect(jsonPath("$.[*].phonenumber").value(hasItem(DEFAULT_PHONENUMBER)))
            .andExpect(jsonPath("$.[*].dateofbirth").value(hasItem(DEFAULT_DATEOFBIRTH.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].skype").value(hasItem(DEFAULT_SKYPE)))
            .andExpect(jsonPath("$.[*].employeeDesignation").value(hasItem(DEFAULT_EMPLOYEE_DESIGNATION)))
            .andExpect(jsonPath("$.[*].joiningdate").value(hasItem(DEFAULT_JOININGDATE.toString())))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA)))
            .andExpect(jsonPath("$.[*].leavingdate").value(hasItem(DEFAULT_LEAVINGDATE.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].googleid").value(hasItem(DEFAULT_GOOGLEID)))
            .andExpect(jsonPath("$.[*].oracleid").value(hasItem(DEFAULT_ORACLEID)))
            .andExpect(jsonPath("$.[*].deptt").value(hasItem(DEFAULT_DEPTT)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].genderid").value(hasItem(DEFAULT_GENDERID)))
            .andExpect(jsonPath("$.[*].onprobation").value(hasItem(DEFAULT_ONPROBATION.booleanValue())))
            .andExpect(jsonPath("$.[*].employeeCompetency").value(hasItem(DEFAULT_EMPLOYEE_COMPETENCY)))
            .andExpect(jsonPath("$.[*].resourcetype").value(hasItem(DEFAULT_RESOURCETYPE)))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE)))
            .andExpect(jsonPath("$.[*].subgrade").value(hasItem(DEFAULT_SUBGRADE)))
            .andExpect(jsonPath("$.[*].imageurl").value(hasItem(DEFAULT_IMAGEURL)))
            .andExpect(jsonPath("$.[*].resignationdate").value(hasItem(DEFAULT_RESIGNATIONDATE.toString())))
            .andExpect(jsonPath("$.[*].graduationdate").value(hasItem(DEFAULT_GRADUATIONDATE.toString())))
            .andExpect(jsonPath("$.[*].careerstartdate").value(hasItem(DEFAULT_CAREERSTARTDATE.toString())))
            .andExpect(jsonPath("$.[*].externalexpyears").value(hasItem(DEFAULT_EXTERNALEXPYEARS)))
            .andExpect(jsonPath("$.[*].externalexpmonths").value(hasItem(sameNumber(DEFAULT_EXTERNALEXPMONTHS))))
            .andExpect(jsonPath("$.[*].placeofbirth").value(hasItem(DEFAULT_PLACEOFBIRTH)))
            .andExpect(jsonPath("$.[*].hireddate").value(hasItem(DEFAULT_HIREDDATE.toString())))
            .andExpect(jsonPath("$.[*].lasttrackingupdate").value(hasItem(DEFAULT_LASTTRACKINGUPDATE.toString())))
            .andExpect(jsonPath("$.[*].middlename").value(hasItem(DEFAULT_MIDDLENAME)))
            .andExpect(jsonPath("$.[*].grosssalaryContentType").value(hasItem(DEFAULT_GROSSSALARY_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].grosssalary").value(hasItem(Base64Utils.encodeToString(DEFAULT_GROSSSALARY))))
            .andExpect(jsonPath("$.[*].fuelallowanceContentType").value(hasItem(DEFAULT_FUELALLOWANCE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fuelallowance").value(hasItem(Base64Utils.encodeToString(DEFAULT_FUELALLOWANCE))))
            .andExpect(jsonPath("$.[*].mobilenumber").value(hasItem(DEFAULT_MOBILENUMBER)))
            .andExpect(jsonPath("$.[*].resignationtype").value(hasItem(DEFAULT_RESIGNATIONTYPE)))
            .andExpect(jsonPath("$.[*].primaryreasonforleaving").value(hasItem(DEFAULT_PRIMARYREASONFORLEAVING)))
            .andExpect(jsonPath("$.[*].secondaryreasonforleaving").value(hasItem(DEFAULT_SECONDARYREASONFORLEAVING)))
            .andExpect(jsonPath("$.[*].noticeperiodduration").value(hasItem(DEFAULT_NOTICEPERIODDURATION)))
            .andExpect(jsonPath("$.[*].noticeperiodserved").value(hasItem(DEFAULT_NOTICEPERIODSERVED)))
            .andExpect(jsonPath("$.[*].probationperiodduration").value(hasItem(DEFAULT_PROBATIONPERIODDURATION)));

        // Check, that the count call also returns 1
        restEmployeesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeesShouldNotBeFound(String filter) throws Exception {
        restEmployeesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployees() throws Exception {
        // Get the employees
        restEmployeesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmployees() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        int databaseSizeBeforeUpdate = employeesRepository.findAll().size();

        // Update the employees
        Employees updatedEmployees = employeesRepository.findById(employees.getId()).get();
        // Disconnect from session so that the updates on updatedEmployees are not directly saved in db
        em.detach(updatedEmployees);
        updatedEmployees
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .phonenumber(UPDATED_PHONENUMBER)
            .dateofbirth(UPDATED_DATEOFBIRTH)
            .email(UPDATED_EMAIL)
            .skype(UPDATED_SKYPE)
            .employeeDesignation(UPDATED_EMPLOYEE_DESIGNATION)
            .joiningdate(UPDATED_JOININGDATE)
            .area(UPDATED_AREA)
            .leavingdate(UPDATED_LEAVINGDATE)
            .notes(UPDATED_NOTES)
            .isactive(UPDATED_ISACTIVE)
            .googleid(UPDATED_GOOGLEID)
            .oracleid(UPDATED_ORACLEID)
            .deptt(UPDATED_DEPTT)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .genderid(UPDATED_GENDERID)
            .onprobation(UPDATED_ONPROBATION)
            .employeeCompetency(UPDATED_EMPLOYEE_COMPETENCY)
            .resourcetype(UPDATED_RESOURCETYPE)
            .grade(UPDATED_GRADE)
            .subgrade(UPDATED_SUBGRADE)
            .imageurl(UPDATED_IMAGEURL)
            .resignationdate(UPDATED_RESIGNATIONDATE)
            .graduationdate(UPDATED_GRADUATIONDATE)
            .careerstartdate(UPDATED_CAREERSTARTDATE)
            .externalexpyears(UPDATED_EXTERNALEXPYEARS)
            .externalexpmonths(UPDATED_EXTERNALEXPMONTHS)
            .placeofbirth(UPDATED_PLACEOFBIRTH)
            .hireddate(UPDATED_HIREDDATE)
            .lasttrackingupdate(UPDATED_LASTTRACKINGUPDATE)
            .middlename(UPDATED_MIDDLENAME)
            .grosssalary(UPDATED_GROSSSALARY)
            .grosssalaryContentType(UPDATED_GROSSSALARY_CONTENT_TYPE)
            .fuelallowance(UPDATED_FUELALLOWANCE)
            .fuelallowanceContentType(UPDATED_FUELALLOWANCE_CONTENT_TYPE)
            .mobilenumber(UPDATED_MOBILENUMBER)
            .resignationtype(UPDATED_RESIGNATIONTYPE)
            .primaryreasonforleaving(UPDATED_PRIMARYREASONFORLEAVING)
            .secondaryreasonforleaving(UPDATED_SECONDARYREASONFORLEAVING)
            .noticeperiodduration(UPDATED_NOTICEPERIODDURATION)
            .noticeperiodserved(UPDATED_NOTICEPERIODSERVED)
            .probationperiodduration(UPDATED_PROBATIONPERIODDURATION);

        restEmployeesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmployees.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmployees))
            )
            .andExpect(status().isOk());

        // Validate the Employees in the database
        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeUpdate);
        Employees testEmployees = employeesList.get(employeesList.size() - 1);
        assertThat(testEmployees.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testEmployees.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testEmployees.getPhonenumber()).isEqualTo(UPDATED_PHONENUMBER);
        assertThat(testEmployees.getDateofbirth()).isEqualTo(UPDATED_DATEOFBIRTH);
        assertThat(testEmployees.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmployees.getSkype()).isEqualTo(UPDATED_SKYPE);
        assertThat(testEmployees.getEmployeeDesignation()).isEqualTo(UPDATED_EMPLOYEE_DESIGNATION);
        assertThat(testEmployees.getJoiningdate()).isEqualTo(UPDATED_JOININGDATE);
        assertThat(testEmployees.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testEmployees.getLeavingdate()).isEqualTo(UPDATED_LEAVINGDATE);
        assertThat(testEmployees.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testEmployees.getIsactive()).isEqualTo(UPDATED_ISACTIVE);
        assertThat(testEmployees.getGoogleid()).isEqualTo(UPDATED_GOOGLEID);
        assertThat(testEmployees.getOracleid()).isEqualTo(UPDATED_ORACLEID);
        assertThat(testEmployees.getDeptt()).isEqualTo(UPDATED_DEPTT);
        assertThat(testEmployees.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployees.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testEmployees.getGenderid()).isEqualTo(UPDATED_GENDERID);
        assertThat(testEmployees.getOnprobation()).isEqualTo(UPDATED_ONPROBATION);
        assertThat(testEmployees.getEmployeeCompetency()).isEqualTo(UPDATED_EMPLOYEE_COMPETENCY);
        assertThat(testEmployees.getResourcetype()).isEqualTo(UPDATED_RESOURCETYPE);
        assertThat(testEmployees.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testEmployees.getSubgrade()).isEqualTo(UPDATED_SUBGRADE);
        assertThat(testEmployees.getImageurl()).isEqualTo(UPDATED_IMAGEURL);
        assertThat(testEmployees.getResignationdate()).isEqualTo(UPDATED_RESIGNATIONDATE);
        assertThat(testEmployees.getGraduationdate()).isEqualTo(UPDATED_GRADUATIONDATE);
        assertThat(testEmployees.getCareerstartdate()).isEqualTo(UPDATED_CAREERSTARTDATE);
        assertThat(testEmployees.getExternalexpyears()).isEqualTo(UPDATED_EXTERNALEXPYEARS);
        assertThat(testEmployees.getExternalexpmonths()).isEqualByComparingTo(UPDATED_EXTERNALEXPMONTHS);
        assertThat(testEmployees.getPlaceofbirth()).isEqualTo(UPDATED_PLACEOFBIRTH);
        assertThat(testEmployees.getHireddate()).isEqualTo(UPDATED_HIREDDATE);
        assertThat(testEmployees.getLasttrackingupdate()).isEqualTo(UPDATED_LASTTRACKINGUPDATE);
        assertThat(testEmployees.getMiddlename()).isEqualTo(UPDATED_MIDDLENAME);
        assertThat(testEmployees.getGrosssalary()).isEqualTo(UPDATED_GROSSSALARY);
        assertThat(testEmployees.getGrosssalaryContentType()).isEqualTo(UPDATED_GROSSSALARY_CONTENT_TYPE);
        assertThat(testEmployees.getFuelallowance()).isEqualTo(UPDATED_FUELALLOWANCE);
        assertThat(testEmployees.getFuelallowanceContentType()).isEqualTo(UPDATED_FUELALLOWANCE_CONTENT_TYPE);
        assertThat(testEmployees.getMobilenumber()).isEqualTo(UPDATED_MOBILENUMBER);
        assertThat(testEmployees.getResignationtype()).isEqualTo(UPDATED_RESIGNATIONTYPE);
        assertThat(testEmployees.getPrimaryreasonforleaving()).isEqualTo(UPDATED_PRIMARYREASONFORLEAVING);
        assertThat(testEmployees.getSecondaryreasonforleaving()).isEqualTo(UPDATED_SECONDARYREASONFORLEAVING);
        assertThat(testEmployees.getNoticeperiodduration()).isEqualTo(UPDATED_NOTICEPERIODDURATION);
        assertThat(testEmployees.getNoticeperiodserved()).isEqualTo(UPDATED_NOTICEPERIODSERVED);
        assertThat(testEmployees.getProbationperiodduration()).isEqualTo(UPDATED_PROBATIONPERIODDURATION);
    }

    @Test
    @Transactional
    void putNonExistingEmployees() throws Exception {
        int databaseSizeBeforeUpdate = employeesRepository.findAll().size();
        employees.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employees.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employees))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employees in the database
        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployees() throws Exception {
        int databaseSizeBeforeUpdate = employeesRepository.findAll().size();
        employees.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employees))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employees in the database
        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployees() throws Exception {
        int databaseSizeBeforeUpdate = employeesRepository.findAll().size();
        employees.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employees))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Employees in the database
        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeesWithPatch() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        int databaseSizeBeforeUpdate = employeesRepository.findAll().size();

        // Update the employees using partial update
        Employees partialUpdatedEmployees = new Employees();
        partialUpdatedEmployees.setId(employees.getId());

        partialUpdatedEmployees
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .dateofbirth(UPDATED_DATEOFBIRTH)
            .skype(UPDATED_SKYPE)
            .joiningdate(UPDATED_JOININGDATE)
            .notes(UPDATED_NOTES)
            .deptt(UPDATED_DEPTT)
            .imageurl(UPDATED_IMAGEURL)
            .resignationdate(UPDATED_RESIGNATIONDATE)
            .graduationdate(UPDATED_GRADUATIONDATE)
            .placeofbirth(UPDATED_PLACEOFBIRTH)
            .hireddate(UPDATED_HIREDDATE)
            .lasttrackingupdate(UPDATED_LASTTRACKINGUPDATE)
            .mobilenumber(UPDATED_MOBILENUMBER)
            .primaryreasonforleaving(UPDATED_PRIMARYREASONFORLEAVING)
            .noticeperiodduration(UPDATED_NOTICEPERIODDURATION)
            .probationperiodduration(UPDATED_PROBATIONPERIODDURATION);

        restEmployeesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployees.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployees))
            )
            .andExpect(status().isOk());

        // Validate the Employees in the database
        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeUpdate);
        Employees testEmployees = employeesList.get(employeesList.size() - 1);
        assertThat(testEmployees.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testEmployees.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testEmployees.getPhonenumber()).isEqualTo(DEFAULT_PHONENUMBER);
        assertThat(testEmployees.getDateofbirth()).isEqualTo(UPDATED_DATEOFBIRTH);
        assertThat(testEmployees.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEmployees.getSkype()).isEqualTo(UPDATED_SKYPE);
        assertThat(testEmployees.getEmployeeDesignation()).isEqualTo(DEFAULT_EMPLOYEE_DESIGNATION);
        assertThat(testEmployees.getJoiningdate()).isEqualTo(UPDATED_JOININGDATE);
        assertThat(testEmployees.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testEmployees.getLeavingdate()).isEqualTo(DEFAULT_LEAVINGDATE);
        assertThat(testEmployees.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testEmployees.getIsactive()).isEqualTo(DEFAULT_ISACTIVE);
        assertThat(testEmployees.getGoogleid()).isEqualTo(DEFAULT_GOOGLEID);
        assertThat(testEmployees.getOracleid()).isEqualTo(DEFAULT_ORACLEID);
        assertThat(testEmployees.getDeptt()).isEqualTo(UPDATED_DEPTT);
        assertThat(testEmployees.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testEmployees.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testEmployees.getGenderid()).isEqualTo(DEFAULT_GENDERID);
        assertThat(testEmployees.getOnprobation()).isEqualTo(DEFAULT_ONPROBATION);
        assertThat(testEmployees.getEmployeeCompetency()).isEqualTo(DEFAULT_EMPLOYEE_COMPETENCY);
        assertThat(testEmployees.getResourcetype()).isEqualTo(DEFAULT_RESOURCETYPE);
        assertThat(testEmployees.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testEmployees.getSubgrade()).isEqualTo(DEFAULT_SUBGRADE);
        assertThat(testEmployees.getImageurl()).isEqualTo(UPDATED_IMAGEURL);
        assertThat(testEmployees.getResignationdate()).isEqualTo(UPDATED_RESIGNATIONDATE);
        assertThat(testEmployees.getGraduationdate()).isEqualTo(UPDATED_GRADUATIONDATE);
        assertThat(testEmployees.getCareerstartdate()).isEqualTo(DEFAULT_CAREERSTARTDATE);
        assertThat(testEmployees.getExternalexpyears()).isEqualTo(DEFAULT_EXTERNALEXPYEARS);
        assertThat(testEmployees.getExternalexpmonths()).isEqualByComparingTo(DEFAULT_EXTERNALEXPMONTHS);
        assertThat(testEmployees.getPlaceofbirth()).isEqualTo(UPDATED_PLACEOFBIRTH);
        assertThat(testEmployees.getHireddate()).isEqualTo(UPDATED_HIREDDATE);
        assertThat(testEmployees.getLasttrackingupdate()).isEqualTo(UPDATED_LASTTRACKINGUPDATE);
        assertThat(testEmployees.getMiddlename()).isEqualTo(DEFAULT_MIDDLENAME);
        assertThat(testEmployees.getGrosssalary()).isEqualTo(DEFAULT_GROSSSALARY);
        assertThat(testEmployees.getGrosssalaryContentType()).isEqualTo(DEFAULT_GROSSSALARY_CONTENT_TYPE);
        assertThat(testEmployees.getFuelallowance()).isEqualTo(DEFAULT_FUELALLOWANCE);
        assertThat(testEmployees.getFuelallowanceContentType()).isEqualTo(DEFAULT_FUELALLOWANCE_CONTENT_TYPE);
        assertThat(testEmployees.getMobilenumber()).isEqualTo(UPDATED_MOBILENUMBER);
        assertThat(testEmployees.getResignationtype()).isEqualTo(DEFAULT_RESIGNATIONTYPE);
        assertThat(testEmployees.getPrimaryreasonforleaving()).isEqualTo(UPDATED_PRIMARYREASONFORLEAVING);
        assertThat(testEmployees.getSecondaryreasonforleaving()).isEqualTo(DEFAULT_SECONDARYREASONFORLEAVING);
        assertThat(testEmployees.getNoticeperiodduration()).isEqualTo(UPDATED_NOTICEPERIODDURATION);
        assertThat(testEmployees.getNoticeperiodserved()).isEqualTo(DEFAULT_NOTICEPERIODSERVED);
        assertThat(testEmployees.getProbationperiodduration()).isEqualTo(UPDATED_PROBATIONPERIODDURATION);
    }

    @Test
    @Transactional
    void fullUpdateEmployeesWithPatch() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        int databaseSizeBeforeUpdate = employeesRepository.findAll().size();

        // Update the employees using partial update
        Employees partialUpdatedEmployees = new Employees();
        partialUpdatedEmployees.setId(employees.getId());

        partialUpdatedEmployees
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .phonenumber(UPDATED_PHONENUMBER)
            .dateofbirth(UPDATED_DATEOFBIRTH)
            .email(UPDATED_EMAIL)
            .skype(UPDATED_SKYPE)
            .employeeDesignation(UPDATED_EMPLOYEE_DESIGNATION)
            .joiningdate(UPDATED_JOININGDATE)
            .area(UPDATED_AREA)
            .leavingdate(UPDATED_LEAVINGDATE)
            .notes(UPDATED_NOTES)
            .isactive(UPDATED_ISACTIVE)
            .googleid(UPDATED_GOOGLEID)
            .oracleid(UPDATED_ORACLEID)
            .deptt(UPDATED_DEPTT)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .genderid(UPDATED_GENDERID)
            .onprobation(UPDATED_ONPROBATION)
            .employeeCompetency(UPDATED_EMPLOYEE_COMPETENCY)
            .resourcetype(UPDATED_RESOURCETYPE)
            .grade(UPDATED_GRADE)
            .subgrade(UPDATED_SUBGRADE)
            .imageurl(UPDATED_IMAGEURL)
            .resignationdate(UPDATED_RESIGNATIONDATE)
            .graduationdate(UPDATED_GRADUATIONDATE)
            .careerstartdate(UPDATED_CAREERSTARTDATE)
            .externalexpyears(UPDATED_EXTERNALEXPYEARS)
            .externalexpmonths(UPDATED_EXTERNALEXPMONTHS)
            .placeofbirth(UPDATED_PLACEOFBIRTH)
            .hireddate(UPDATED_HIREDDATE)
            .lasttrackingupdate(UPDATED_LASTTRACKINGUPDATE)
            .middlename(UPDATED_MIDDLENAME)
            .grosssalary(UPDATED_GROSSSALARY)
            .grosssalaryContentType(UPDATED_GROSSSALARY_CONTENT_TYPE)
            .fuelallowance(UPDATED_FUELALLOWANCE)
            .fuelallowanceContentType(UPDATED_FUELALLOWANCE_CONTENT_TYPE)
            .mobilenumber(UPDATED_MOBILENUMBER)
            .resignationtype(UPDATED_RESIGNATIONTYPE)
            .primaryreasonforleaving(UPDATED_PRIMARYREASONFORLEAVING)
            .secondaryreasonforleaving(UPDATED_SECONDARYREASONFORLEAVING)
            .noticeperiodduration(UPDATED_NOTICEPERIODDURATION)
            .noticeperiodserved(UPDATED_NOTICEPERIODSERVED)
            .probationperiodduration(UPDATED_PROBATIONPERIODDURATION);

        restEmployeesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployees.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployees))
            )
            .andExpect(status().isOk());

        // Validate the Employees in the database
        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeUpdate);
        Employees testEmployees = employeesList.get(employeesList.size() - 1);
        assertThat(testEmployees.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testEmployees.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testEmployees.getPhonenumber()).isEqualTo(UPDATED_PHONENUMBER);
        assertThat(testEmployees.getDateofbirth()).isEqualTo(UPDATED_DATEOFBIRTH);
        assertThat(testEmployees.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmployees.getSkype()).isEqualTo(UPDATED_SKYPE);
        assertThat(testEmployees.getEmployeeDesignation()).isEqualTo(UPDATED_EMPLOYEE_DESIGNATION);
        assertThat(testEmployees.getJoiningdate()).isEqualTo(UPDATED_JOININGDATE);
        assertThat(testEmployees.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testEmployees.getLeavingdate()).isEqualTo(UPDATED_LEAVINGDATE);
        assertThat(testEmployees.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testEmployees.getIsactive()).isEqualTo(UPDATED_ISACTIVE);
        assertThat(testEmployees.getGoogleid()).isEqualTo(UPDATED_GOOGLEID);
        assertThat(testEmployees.getOracleid()).isEqualTo(UPDATED_ORACLEID);
        assertThat(testEmployees.getDeptt()).isEqualTo(UPDATED_DEPTT);
        assertThat(testEmployees.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployees.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testEmployees.getGenderid()).isEqualTo(UPDATED_GENDERID);
        assertThat(testEmployees.getOnprobation()).isEqualTo(UPDATED_ONPROBATION);
        assertThat(testEmployees.getEmployeeCompetency()).isEqualTo(UPDATED_EMPLOYEE_COMPETENCY);
        assertThat(testEmployees.getResourcetype()).isEqualTo(UPDATED_RESOURCETYPE);
        assertThat(testEmployees.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testEmployees.getSubgrade()).isEqualTo(UPDATED_SUBGRADE);
        assertThat(testEmployees.getImageurl()).isEqualTo(UPDATED_IMAGEURL);
        assertThat(testEmployees.getResignationdate()).isEqualTo(UPDATED_RESIGNATIONDATE);
        assertThat(testEmployees.getGraduationdate()).isEqualTo(UPDATED_GRADUATIONDATE);
        assertThat(testEmployees.getCareerstartdate()).isEqualTo(UPDATED_CAREERSTARTDATE);
        assertThat(testEmployees.getExternalexpyears()).isEqualTo(UPDATED_EXTERNALEXPYEARS);
        assertThat(testEmployees.getExternalexpmonths()).isEqualByComparingTo(UPDATED_EXTERNALEXPMONTHS);
        assertThat(testEmployees.getPlaceofbirth()).isEqualTo(UPDATED_PLACEOFBIRTH);
        assertThat(testEmployees.getHireddate()).isEqualTo(UPDATED_HIREDDATE);
        assertThat(testEmployees.getLasttrackingupdate()).isEqualTo(UPDATED_LASTTRACKINGUPDATE);
        assertThat(testEmployees.getMiddlename()).isEqualTo(UPDATED_MIDDLENAME);
        assertThat(testEmployees.getGrosssalary()).isEqualTo(UPDATED_GROSSSALARY);
        assertThat(testEmployees.getGrosssalaryContentType()).isEqualTo(UPDATED_GROSSSALARY_CONTENT_TYPE);
        assertThat(testEmployees.getFuelallowance()).isEqualTo(UPDATED_FUELALLOWANCE);
        assertThat(testEmployees.getFuelallowanceContentType()).isEqualTo(UPDATED_FUELALLOWANCE_CONTENT_TYPE);
        assertThat(testEmployees.getMobilenumber()).isEqualTo(UPDATED_MOBILENUMBER);
        assertThat(testEmployees.getResignationtype()).isEqualTo(UPDATED_RESIGNATIONTYPE);
        assertThat(testEmployees.getPrimaryreasonforleaving()).isEqualTo(UPDATED_PRIMARYREASONFORLEAVING);
        assertThat(testEmployees.getSecondaryreasonforleaving()).isEqualTo(UPDATED_SECONDARYREASONFORLEAVING);
        assertThat(testEmployees.getNoticeperiodduration()).isEqualTo(UPDATED_NOTICEPERIODDURATION);
        assertThat(testEmployees.getNoticeperiodserved()).isEqualTo(UPDATED_NOTICEPERIODSERVED);
        assertThat(testEmployees.getProbationperiodduration()).isEqualTo(UPDATED_PROBATIONPERIODDURATION);
    }

    @Test
    @Transactional
    void patchNonExistingEmployees() throws Exception {
        int databaseSizeBeforeUpdate = employeesRepository.findAll().size();
        employees.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employees.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employees))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employees in the database
        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployees() throws Exception {
        int databaseSizeBeforeUpdate = employeesRepository.findAll().size();
        employees.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employees))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employees in the database
        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployees() throws Exception {
        int databaseSizeBeforeUpdate = employeesRepository.findAll().size();
        employees.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employees))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Employees in the database
        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployees() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        int databaseSizeBeforeDelete = employeesRepository.findAll().size();

        // Delete the employees
        restEmployeesMockMvc
            .perform(delete(ENTITY_API_URL_ID, employees.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
