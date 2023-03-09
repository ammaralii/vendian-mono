package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.EmployeeProfileHistoryLogs;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.repository.EmployeeProfileHistoryLogsRepository;
import com.venturedive.vendian_mono.service.criteria.EmployeeProfileHistoryLogsCriteria;
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
 * Integration tests for the {@link EmployeeProfileHistoryLogsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeeProfileHistoryLogsResourceIT {

    private static final String DEFAULT_TABLENAME = "AAAAAAAAAA";
    private static final String UPDATED_TABLENAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_ROWID = 1;
    private static final Integer UPDATED_ROWID = 2;
    private static final Integer SMALLER_ROWID = 1 - 1;

    private static final String DEFAULT_EVENTTYPE = "AAAAAAAAAA";
    private static final String UPDATED_EVENTTYPE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FIELDS = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FIELDS = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FIELDS_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FIELDS_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_UPDATEDBYID = 1;
    private static final Integer UPDATED_UPDATEDBYID = 2;
    private static final Integer SMALLER_UPDATEDBYID = 1 - 1;

    private static final String DEFAULT_ACTIVITYID = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITYID = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/employee-profile-history-logs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeeProfileHistoryLogsRepository employeeProfileHistoryLogsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeProfileHistoryLogsMockMvc;

    private EmployeeProfileHistoryLogs employeeProfileHistoryLogs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeProfileHistoryLogs createEntity(EntityManager em) {
        EmployeeProfileHistoryLogs employeeProfileHistoryLogs = new EmployeeProfileHistoryLogs()
            .tablename(DEFAULT_TABLENAME)
            .rowid(DEFAULT_ROWID)
            .eventtype(DEFAULT_EVENTTYPE)
            .fields(DEFAULT_FIELDS)
            .fieldsContentType(DEFAULT_FIELDS_CONTENT_TYPE)
            .updatedbyid(DEFAULT_UPDATEDBYID)
            .activityid(DEFAULT_ACTIVITYID)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT)
            .category(DEFAULT_CATEGORY);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        employeeProfileHistoryLogs.setEmployee(employees);
        return employeeProfileHistoryLogs;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeProfileHistoryLogs createUpdatedEntity(EntityManager em) {
        EmployeeProfileHistoryLogs employeeProfileHistoryLogs = new EmployeeProfileHistoryLogs()
            .tablename(UPDATED_TABLENAME)
            .rowid(UPDATED_ROWID)
            .eventtype(UPDATED_EVENTTYPE)
            .fields(UPDATED_FIELDS)
            .fieldsContentType(UPDATED_FIELDS_CONTENT_TYPE)
            .updatedbyid(UPDATED_UPDATEDBYID)
            .activityid(UPDATED_ACTIVITYID)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .category(UPDATED_CATEGORY);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createUpdatedEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        employeeProfileHistoryLogs.setEmployee(employees);
        return employeeProfileHistoryLogs;
    }

    @BeforeEach
    public void initTest() {
        employeeProfileHistoryLogs = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployeeProfileHistoryLogs() throws Exception {
        int databaseSizeBeforeCreate = employeeProfileHistoryLogsRepository.findAll().size();
        // Create the EmployeeProfileHistoryLogs
        restEmployeeProfileHistoryLogsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeProfileHistoryLogs))
            )
            .andExpect(status().isCreated());

        // Validate the EmployeeProfileHistoryLogs in the database
        List<EmployeeProfileHistoryLogs> employeeProfileHistoryLogsList = employeeProfileHistoryLogsRepository.findAll();
        assertThat(employeeProfileHistoryLogsList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeProfileHistoryLogs testEmployeeProfileHistoryLogs = employeeProfileHistoryLogsList.get(
            employeeProfileHistoryLogsList.size() - 1
        );
        assertThat(testEmployeeProfileHistoryLogs.getTablename()).isEqualTo(DEFAULT_TABLENAME);
        assertThat(testEmployeeProfileHistoryLogs.getRowid()).isEqualTo(DEFAULT_ROWID);
        assertThat(testEmployeeProfileHistoryLogs.getEventtype()).isEqualTo(DEFAULT_EVENTTYPE);
        assertThat(testEmployeeProfileHistoryLogs.getFields()).isEqualTo(DEFAULT_FIELDS);
        assertThat(testEmployeeProfileHistoryLogs.getFieldsContentType()).isEqualTo(DEFAULT_FIELDS_CONTENT_TYPE);
        assertThat(testEmployeeProfileHistoryLogs.getUpdatedbyid()).isEqualTo(DEFAULT_UPDATEDBYID);
        assertThat(testEmployeeProfileHistoryLogs.getActivityid()).isEqualTo(DEFAULT_ACTIVITYID);
        assertThat(testEmployeeProfileHistoryLogs.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testEmployeeProfileHistoryLogs.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testEmployeeProfileHistoryLogs.getCategory()).isEqualTo(DEFAULT_CATEGORY);
    }

    @Test
    @Transactional
    void createEmployeeProfileHistoryLogsWithExistingId() throws Exception {
        // Create the EmployeeProfileHistoryLogs with an existing ID
        employeeProfileHistoryLogs.setId(1L);

        int databaseSizeBeforeCreate = employeeProfileHistoryLogsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeProfileHistoryLogsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeProfileHistoryLogs))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeProfileHistoryLogs in the database
        List<EmployeeProfileHistoryLogs> employeeProfileHistoryLogsList = employeeProfileHistoryLogsRepository.findAll();
        assertThat(employeeProfileHistoryLogsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTablenameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeProfileHistoryLogsRepository.findAll().size();
        // set the field null
        employeeProfileHistoryLogs.setTablename(null);

        // Create the EmployeeProfileHistoryLogs, which fails.

        restEmployeeProfileHistoryLogsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeProfileHistoryLogs))
            )
            .andExpect(status().isBadRequest());

        List<EmployeeProfileHistoryLogs> employeeProfileHistoryLogsList = employeeProfileHistoryLogsRepository.findAll();
        assertThat(employeeProfileHistoryLogsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRowidIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeProfileHistoryLogsRepository.findAll().size();
        // set the field null
        employeeProfileHistoryLogs.setRowid(null);

        // Create the EmployeeProfileHistoryLogs, which fails.

        restEmployeeProfileHistoryLogsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeProfileHistoryLogs))
            )
            .andExpect(status().isBadRequest());

        List<EmployeeProfileHistoryLogs> employeeProfileHistoryLogsList = employeeProfileHistoryLogsRepository.findAll();
        assertThat(employeeProfileHistoryLogsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEventtypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeProfileHistoryLogsRepository.findAll().size();
        // set the field null
        employeeProfileHistoryLogs.setEventtype(null);

        // Create the EmployeeProfileHistoryLogs, which fails.

        restEmployeeProfileHistoryLogsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeProfileHistoryLogs))
            )
            .andExpect(status().isBadRequest());

        List<EmployeeProfileHistoryLogs> employeeProfileHistoryLogsList = employeeProfileHistoryLogsRepository.findAll();
        assertThat(employeeProfileHistoryLogsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedbyidIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeProfileHistoryLogsRepository.findAll().size();
        // set the field null
        employeeProfileHistoryLogs.setUpdatedbyid(null);

        // Create the EmployeeProfileHistoryLogs, which fails.

        restEmployeeProfileHistoryLogsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeProfileHistoryLogs))
            )
            .andExpect(status().isBadRequest());

        List<EmployeeProfileHistoryLogs> employeeProfileHistoryLogsList = employeeProfileHistoryLogsRepository.findAll();
        assertThat(employeeProfileHistoryLogsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActivityidIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeProfileHistoryLogsRepository.findAll().size();
        // set the field null
        employeeProfileHistoryLogs.setActivityid(null);

        // Create the EmployeeProfileHistoryLogs, which fails.

        restEmployeeProfileHistoryLogsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeProfileHistoryLogs))
            )
            .andExpect(status().isBadRequest());

        List<EmployeeProfileHistoryLogs> employeeProfileHistoryLogsList = employeeProfileHistoryLogsRepository.findAll();
        assertThat(employeeProfileHistoryLogsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeProfileHistoryLogsRepository.findAll().size();
        // set the field null
        employeeProfileHistoryLogs.setCategory(null);

        // Create the EmployeeProfileHistoryLogs, which fails.

        restEmployeeProfileHistoryLogsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeProfileHistoryLogs))
            )
            .andExpect(status().isBadRequest());

        List<EmployeeProfileHistoryLogs> employeeProfileHistoryLogsList = employeeProfileHistoryLogsRepository.findAll();
        assertThat(employeeProfileHistoryLogsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogs() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList
        restEmployeeProfileHistoryLogsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeProfileHistoryLogs.getId().intValue())))
            .andExpect(jsonPath("$.[*].tablename").value(hasItem(DEFAULT_TABLENAME)))
            .andExpect(jsonPath("$.[*].rowid").value(hasItem(DEFAULT_ROWID)))
            .andExpect(jsonPath("$.[*].eventtype").value(hasItem(DEFAULT_EVENTTYPE)))
            .andExpect(jsonPath("$.[*].fieldsContentType").value(hasItem(DEFAULT_FIELDS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fields").value(hasItem(Base64Utils.encodeToString(DEFAULT_FIELDS))))
            .andExpect(jsonPath("$.[*].updatedbyid").value(hasItem(DEFAULT_UPDATEDBYID)))
            .andExpect(jsonPath("$.[*].activityid").value(hasItem(DEFAULT_ACTIVITYID)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)));
    }

    @Test
    @Transactional
    void getEmployeeProfileHistoryLogs() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get the employeeProfileHistoryLogs
        restEmployeeProfileHistoryLogsMockMvc
            .perform(get(ENTITY_API_URL_ID, employeeProfileHistoryLogs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeProfileHistoryLogs.getId().intValue()))
            .andExpect(jsonPath("$.tablename").value(DEFAULT_TABLENAME))
            .andExpect(jsonPath("$.rowid").value(DEFAULT_ROWID))
            .andExpect(jsonPath("$.eventtype").value(DEFAULT_EVENTTYPE))
            .andExpect(jsonPath("$.fieldsContentType").value(DEFAULT_FIELDS_CONTENT_TYPE))
            .andExpect(jsonPath("$.fields").value(Base64Utils.encodeToString(DEFAULT_FIELDS)))
            .andExpect(jsonPath("$.updatedbyid").value(DEFAULT_UPDATEDBYID))
            .andExpect(jsonPath("$.activityid").value(DEFAULT_ACTIVITYID))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY));
    }

    @Test
    @Transactional
    void getEmployeeProfileHistoryLogsByIdFiltering() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        Long id = employeeProfileHistoryLogs.getId();

        defaultEmployeeProfileHistoryLogsShouldBeFound("id.equals=" + id);
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeProfileHistoryLogsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeProfileHistoryLogsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByTablenameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where tablename equals to DEFAULT_TABLENAME
        defaultEmployeeProfileHistoryLogsShouldBeFound("tablename.equals=" + DEFAULT_TABLENAME);

        // Get all the employeeProfileHistoryLogsList where tablename equals to UPDATED_TABLENAME
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("tablename.equals=" + UPDATED_TABLENAME);
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByTablenameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where tablename in DEFAULT_TABLENAME or UPDATED_TABLENAME
        defaultEmployeeProfileHistoryLogsShouldBeFound("tablename.in=" + DEFAULT_TABLENAME + "," + UPDATED_TABLENAME);

        // Get all the employeeProfileHistoryLogsList where tablename equals to UPDATED_TABLENAME
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("tablename.in=" + UPDATED_TABLENAME);
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByTablenameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where tablename is not null
        defaultEmployeeProfileHistoryLogsShouldBeFound("tablename.specified=true");

        // Get all the employeeProfileHistoryLogsList where tablename is null
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("tablename.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByTablenameContainsSomething() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where tablename contains DEFAULT_TABLENAME
        defaultEmployeeProfileHistoryLogsShouldBeFound("tablename.contains=" + DEFAULT_TABLENAME);

        // Get all the employeeProfileHistoryLogsList where tablename contains UPDATED_TABLENAME
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("tablename.contains=" + UPDATED_TABLENAME);
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByTablenameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where tablename does not contain DEFAULT_TABLENAME
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("tablename.doesNotContain=" + DEFAULT_TABLENAME);

        // Get all the employeeProfileHistoryLogsList where tablename does not contain UPDATED_TABLENAME
        defaultEmployeeProfileHistoryLogsShouldBeFound("tablename.doesNotContain=" + UPDATED_TABLENAME);
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByRowidIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where rowid equals to DEFAULT_ROWID
        defaultEmployeeProfileHistoryLogsShouldBeFound("rowid.equals=" + DEFAULT_ROWID);

        // Get all the employeeProfileHistoryLogsList where rowid equals to UPDATED_ROWID
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("rowid.equals=" + UPDATED_ROWID);
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByRowidIsInShouldWork() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where rowid in DEFAULT_ROWID or UPDATED_ROWID
        defaultEmployeeProfileHistoryLogsShouldBeFound("rowid.in=" + DEFAULT_ROWID + "," + UPDATED_ROWID);

        // Get all the employeeProfileHistoryLogsList where rowid equals to UPDATED_ROWID
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("rowid.in=" + UPDATED_ROWID);
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByRowidIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where rowid is not null
        defaultEmployeeProfileHistoryLogsShouldBeFound("rowid.specified=true");

        // Get all the employeeProfileHistoryLogsList where rowid is null
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("rowid.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByRowidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where rowid is greater than or equal to DEFAULT_ROWID
        defaultEmployeeProfileHistoryLogsShouldBeFound("rowid.greaterThanOrEqual=" + DEFAULT_ROWID);

        // Get all the employeeProfileHistoryLogsList where rowid is greater than or equal to UPDATED_ROWID
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("rowid.greaterThanOrEqual=" + UPDATED_ROWID);
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByRowidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where rowid is less than or equal to DEFAULT_ROWID
        defaultEmployeeProfileHistoryLogsShouldBeFound("rowid.lessThanOrEqual=" + DEFAULT_ROWID);

        // Get all the employeeProfileHistoryLogsList where rowid is less than or equal to SMALLER_ROWID
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("rowid.lessThanOrEqual=" + SMALLER_ROWID);
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByRowidIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where rowid is less than DEFAULT_ROWID
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("rowid.lessThan=" + DEFAULT_ROWID);

        // Get all the employeeProfileHistoryLogsList where rowid is less than UPDATED_ROWID
        defaultEmployeeProfileHistoryLogsShouldBeFound("rowid.lessThan=" + UPDATED_ROWID);
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByRowidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where rowid is greater than DEFAULT_ROWID
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("rowid.greaterThan=" + DEFAULT_ROWID);

        // Get all the employeeProfileHistoryLogsList where rowid is greater than SMALLER_ROWID
        defaultEmployeeProfileHistoryLogsShouldBeFound("rowid.greaterThan=" + SMALLER_ROWID);
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByEventtypeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where eventtype equals to DEFAULT_EVENTTYPE
        defaultEmployeeProfileHistoryLogsShouldBeFound("eventtype.equals=" + DEFAULT_EVENTTYPE);

        // Get all the employeeProfileHistoryLogsList where eventtype equals to UPDATED_EVENTTYPE
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("eventtype.equals=" + UPDATED_EVENTTYPE);
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByEventtypeIsInShouldWork() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where eventtype in DEFAULT_EVENTTYPE or UPDATED_EVENTTYPE
        defaultEmployeeProfileHistoryLogsShouldBeFound("eventtype.in=" + DEFAULT_EVENTTYPE + "," + UPDATED_EVENTTYPE);

        // Get all the employeeProfileHistoryLogsList where eventtype equals to UPDATED_EVENTTYPE
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("eventtype.in=" + UPDATED_EVENTTYPE);
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByEventtypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where eventtype is not null
        defaultEmployeeProfileHistoryLogsShouldBeFound("eventtype.specified=true");

        // Get all the employeeProfileHistoryLogsList where eventtype is null
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("eventtype.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByEventtypeContainsSomething() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where eventtype contains DEFAULT_EVENTTYPE
        defaultEmployeeProfileHistoryLogsShouldBeFound("eventtype.contains=" + DEFAULT_EVENTTYPE);

        // Get all the employeeProfileHistoryLogsList where eventtype contains UPDATED_EVENTTYPE
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("eventtype.contains=" + UPDATED_EVENTTYPE);
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByEventtypeNotContainsSomething() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where eventtype does not contain DEFAULT_EVENTTYPE
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("eventtype.doesNotContain=" + DEFAULT_EVENTTYPE);

        // Get all the employeeProfileHistoryLogsList where eventtype does not contain UPDATED_EVENTTYPE
        defaultEmployeeProfileHistoryLogsShouldBeFound("eventtype.doesNotContain=" + UPDATED_EVENTTYPE);
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByUpdatedbyidIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where updatedbyid equals to DEFAULT_UPDATEDBYID
        defaultEmployeeProfileHistoryLogsShouldBeFound("updatedbyid.equals=" + DEFAULT_UPDATEDBYID);

        // Get all the employeeProfileHistoryLogsList where updatedbyid equals to UPDATED_UPDATEDBYID
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("updatedbyid.equals=" + UPDATED_UPDATEDBYID);
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByUpdatedbyidIsInShouldWork() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where updatedbyid in DEFAULT_UPDATEDBYID or UPDATED_UPDATEDBYID
        defaultEmployeeProfileHistoryLogsShouldBeFound("updatedbyid.in=" + DEFAULT_UPDATEDBYID + "," + UPDATED_UPDATEDBYID);

        // Get all the employeeProfileHistoryLogsList where updatedbyid equals to UPDATED_UPDATEDBYID
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("updatedbyid.in=" + UPDATED_UPDATEDBYID);
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByUpdatedbyidIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where updatedbyid is not null
        defaultEmployeeProfileHistoryLogsShouldBeFound("updatedbyid.specified=true");

        // Get all the employeeProfileHistoryLogsList where updatedbyid is null
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("updatedbyid.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByUpdatedbyidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where updatedbyid is greater than or equal to DEFAULT_UPDATEDBYID
        defaultEmployeeProfileHistoryLogsShouldBeFound("updatedbyid.greaterThanOrEqual=" + DEFAULT_UPDATEDBYID);

        // Get all the employeeProfileHistoryLogsList where updatedbyid is greater than or equal to UPDATED_UPDATEDBYID
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("updatedbyid.greaterThanOrEqual=" + UPDATED_UPDATEDBYID);
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByUpdatedbyidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where updatedbyid is less than or equal to DEFAULT_UPDATEDBYID
        defaultEmployeeProfileHistoryLogsShouldBeFound("updatedbyid.lessThanOrEqual=" + DEFAULT_UPDATEDBYID);

        // Get all the employeeProfileHistoryLogsList where updatedbyid is less than or equal to SMALLER_UPDATEDBYID
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("updatedbyid.lessThanOrEqual=" + SMALLER_UPDATEDBYID);
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByUpdatedbyidIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where updatedbyid is less than DEFAULT_UPDATEDBYID
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("updatedbyid.lessThan=" + DEFAULT_UPDATEDBYID);

        // Get all the employeeProfileHistoryLogsList where updatedbyid is less than UPDATED_UPDATEDBYID
        defaultEmployeeProfileHistoryLogsShouldBeFound("updatedbyid.lessThan=" + UPDATED_UPDATEDBYID);
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByUpdatedbyidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where updatedbyid is greater than DEFAULT_UPDATEDBYID
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("updatedbyid.greaterThan=" + DEFAULT_UPDATEDBYID);

        // Get all the employeeProfileHistoryLogsList where updatedbyid is greater than SMALLER_UPDATEDBYID
        defaultEmployeeProfileHistoryLogsShouldBeFound("updatedbyid.greaterThan=" + SMALLER_UPDATEDBYID);
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByActivityidIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where activityid equals to DEFAULT_ACTIVITYID
        defaultEmployeeProfileHistoryLogsShouldBeFound("activityid.equals=" + DEFAULT_ACTIVITYID);

        // Get all the employeeProfileHistoryLogsList where activityid equals to UPDATED_ACTIVITYID
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("activityid.equals=" + UPDATED_ACTIVITYID);
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByActivityidIsInShouldWork() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where activityid in DEFAULT_ACTIVITYID or UPDATED_ACTIVITYID
        defaultEmployeeProfileHistoryLogsShouldBeFound("activityid.in=" + DEFAULT_ACTIVITYID + "," + UPDATED_ACTIVITYID);

        // Get all the employeeProfileHistoryLogsList where activityid equals to UPDATED_ACTIVITYID
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("activityid.in=" + UPDATED_ACTIVITYID);
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByActivityidIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where activityid is not null
        defaultEmployeeProfileHistoryLogsShouldBeFound("activityid.specified=true");

        // Get all the employeeProfileHistoryLogsList where activityid is null
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("activityid.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByActivityidContainsSomething() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where activityid contains DEFAULT_ACTIVITYID
        defaultEmployeeProfileHistoryLogsShouldBeFound("activityid.contains=" + DEFAULT_ACTIVITYID);

        // Get all the employeeProfileHistoryLogsList where activityid contains UPDATED_ACTIVITYID
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("activityid.contains=" + UPDATED_ACTIVITYID);
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByActivityidNotContainsSomething() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where activityid does not contain DEFAULT_ACTIVITYID
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("activityid.doesNotContain=" + DEFAULT_ACTIVITYID);

        // Get all the employeeProfileHistoryLogsList where activityid does not contain UPDATED_ACTIVITYID
        defaultEmployeeProfileHistoryLogsShouldBeFound("activityid.doesNotContain=" + UPDATED_ACTIVITYID);
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where createdat equals to DEFAULT_CREATEDAT
        defaultEmployeeProfileHistoryLogsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the employeeProfileHistoryLogsList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultEmployeeProfileHistoryLogsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the employeeProfileHistoryLogsList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where createdat is not null
        defaultEmployeeProfileHistoryLogsShouldBeFound("createdat.specified=true");

        // Get all the employeeProfileHistoryLogsList where createdat is null
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultEmployeeProfileHistoryLogsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the employeeProfileHistoryLogsList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultEmployeeProfileHistoryLogsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the employeeProfileHistoryLogsList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where updatedat is not null
        defaultEmployeeProfileHistoryLogsShouldBeFound("updatedat.specified=true");

        // Get all the employeeProfileHistoryLogsList where updatedat is null
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where category equals to DEFAULT_CATEGORY
        defaultEmployeeProfileHistoryLogsShouldBeFound("category.equals=" + DEFAULT_CATEGORY);

        // Get all the employeeProfileHistoryLogsList where category equals to UPDATED_CATEGORY
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("category.equals=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where category in DEFAULT_CATEGORY or UPDATED_CATEGORY
        defaultEmployeeProfileHistoryLogsShouldBeFound("category.in=" + DEFAULT_CATEGORY + "," + UPDATED_CATEGORY);

        // Get all the employeeProfileHistoryLogsList where category equals to UPDATED_CATEGORY
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("category.in=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where category is not null
        defaultEmployeeProfileHistoryLogsShouldBeFound("category.specified=true");

        // Get all the employeeProfileHistoryLogsList where category is null
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("category.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByCategoryContainsSomething() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where category contains DEFAULT_CATEGORY
        defaultEmployeeProfileHistoryLogsShouldBeFound("category.contains=" + DEFAULT_CATEGORY);

        // Get all the employeeProfileHistoryLogsList where category contains UPDATED_CATEGORY
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("category.contains=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByCategoryNotContainsSomething() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        // Get all the employeeProfileHistoryLogsList where category does not contain DEFAULT_CATEGORY
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("category.doesNotContain=" + DEFAULT_CATEGORY);

        // Get all the employeeProfileHistoryLogsList where category does not contain UPDATED_CATEGORY
        defaultEmployeeProfileHistoryLogsShouldBeFound("category.doesNotContain=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllEmployeeProfileHistoryLogsByEmployeeIsEqualToSomething() throws Exception {
        Employees employee;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);
            employee = EmployeesResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employee);
        em.flush();
        employeeProfileHistoryLogs.setEmployee(employee);
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);
        Long employeeId = employee.getId();

        // Get all the employeeProfileHistoryLogsList where employee equals to employeeId
        defaultEmployeeProfileHistoryLogsShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the employeeProfileHistoryLogsList where employee equals to (employeeId + 1)
        defaultEmployeeProfileHistoryLogsShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeProfileHistoryLogsShouldBeFound(String filter) throws Exception {
        restEmployeeProfileHistoryLogsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeProfileHistoryLogs.getId().intValue())))
            .andExpect(jsonPath("$.[*].tablename").value(hasItem(DEFAULT_TABLENAME)))
            .andExpect(jsonPath("$.[*].rowid").value(hasItem(DEFAULT_ROWID)))
            .andExpect(jsonPath("$.[*].eventtype").value(hasItem(DEFAULT_EVENTTYPE)))
            .andExpect(jsonPath("$.[*].fieldsContentType").value(hasItem(DEFAULT_FIELDS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fields").value(hasItem(Base64Utils.encodeToString(DEFAULT_FIELDS))))
            .andExpect(jsonPath("$.[*].updatedbyid").value(hasItem(DEFAULT_UPDATEDBYID)))
            .andExpect(jsonPath("$.[*].activityid").value(hasItem(DEFAULT_ACTIVITYID)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)));

        // Check, that the count call also returns 1
        restEmployeeProfileHistoryLogsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeProfileHistoryLogsShouldNotBeFound(String filter) throws Exception {
        restEmployeeProfileHistoryLogsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeProfileHistoryLogsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployeeProfileHistoryLogs() throws Exception {
        // Get the employeeProfileHistoryLogs
        restEmployeeProfileHistoryLogsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmployeeProfileHistoryLogs() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        int databaseSizeBeforeUpdate = employeeProfileHistoryLogsRepository.findAll().size();

        // Update the employeeProfileHistoryLogs
        EmployeeProfileHistoryLogs updatedEmployeeProfileHistoryLogs = employeeProfileHistoryLogsRepository
            .findById(employeeProfileHistoryLogs.getId())
            .get();
        // Disconnect from session so that the updates on updatedEmployeeProfileHistoryLogs are not directly saved in db
        em.detach(updatedEmployeeProfileHistoryLogs);
        updatedEmployeeProfileHistoryLogs
            .tablename(UPDATED_TABLENAME)
            .rowid(UPDATED_ROWID)
            .eventtype(UPDATED_EVENTTYPE)
            .fields(UPDATED_FIELDS)
            .fieldsContentType(UPDATED_FIELDS_CONTENT_TYPE)
            .updatedbyid(UPDATED_UPDATEDBYID)
            .activityid(UPDATED_ACTIVITYID)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .category(UPDATED_CATEGORY);

        restEmployeeProfileHistoryLogsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmployeeProfileHistoryLogs.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeProfileHistoryLogs))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeProfileHistoryLogs in the database
        List<EmployeeProfileHistoryLogs> employeeProfileHistoryLogsList = employeeProfileHistoryLogsRepository.findAll();
        assertThat(employeeProfileHistoryLogsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeProfileHistoryLogs testEmployeeProfileHistoryLogs = employeeProfileHistoryLogsList.get(
            employeeProfileHistoryLogsList.size() - 1
        );
        assertThat(testEmployeeProfileHistoryLogs.getTablename()).isEqualTo(UPDATED_TABLENAME);
        assertThat(testEmployeeProfileHistoryLogs.getRowid()).isEqualTo(UPDATED_ROWID);
        assertThat(testEmployeeProfileHistoryLogs.getEventtype()).isEqualTo(UPDATED_EVENTTYPE);
        assertThat(testEmployeeProfileHistoryLogs.getFields()).isEqualTo(UPDATED_FIELDS);
        assertThat(testEmployeeProfileHistoryLogs.getFieldsContentType()).isEqualTo(UPDATED_FIELDS_CONTENT_TYPE);
        assertThat(testEmployeeProfileHistoryLogs.getUpdatedbyid()).isEqualTo(UPDATED_UPDATEDBYID);
        assertThat(testEmployeeProfileHistoryLogs.getActivityid()).isEqualTo(UPDATED_ACTIVITYID);
        assertThat(testEmployeeProfileHistoryLogs.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeProfileHistoryLogs.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testEmployeeProfileHistoryLogs.getCategory()).isEqualTo(UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void putNonExistingEmployeeProfileHistoryLogs() throws Exception {
        int databaseSizeBeforeUpdate = employeeProfileHistoryLogsRepository.findAll().size();
        employeeProfileHistoryLogs.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeProfileHistoryLogsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeProfileHistoryLogs.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeProfileHistoryLogs))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeProfileHistoryLogs in the database
        List<EmployeeProfileHistoryLogs> employeeProfileHistoryLogsList = employeeProfileHistoryLogsRepository.findAll();
        assertThat(employeeProfileHistoryLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployeeProfileHistoryLogs() throws Exception {
        int databaseSizeBeforeUpdate = employeeProfileHistoryLogsRepository.findAll().size();
        employeeProfileHistoryLogs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeProfileHistoryLogsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeProfileHistoryLogs))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeProfileHistoryLogs in the database
        List<EmployeeProfileHistoryLogs> employeeProfileHistoryLogsList = employeeProfileHistoryLogsRepository.findAll();
        assertThat(employeeProfileHistoryLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployeeProfileHistoryLogs() throws Exception {
        int databaseSizeBeforeUpdate = employeeProfileHistoryLogsRepository.findAll().size();
        employeeProfileHistoryLogs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeProfileHistoryLogsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeProfileHistoryLogs))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeProfileHistoryLogs in the database
        List<EmployeeProfileHistoryLogs> employeeProfileHistoryLogsList = employeeProfileHistoryLogsRepository.findAll();
        assertThat(employeeProfileHistoryLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeProfileHistoryLogsWithPatch() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        int databaseSizeBeforeUpdate = employeeProfileHistoryLogsRepository.findAll().size();

        // Update the employeeProfileHistoryLogs using partial update
        EmployeeProfileHistoryLogs partialUpdatedEmployeeProfileHistoryLogs = new EmployeeProfileHistoryLogs();
        partialUpdatedEmployeeProfileHistoryLogs.setId(employeeProfileHistoryLogs.getId());

        partialUpdatedEmployeeProfileHistoryLogs
            .tablename(UPDATED_TABLENAME)
            .rowid(UPDATED_ROWID)
            .eventtype(UPDATED_EVENTTYPE)
            .activityid(UPDATED_ACTIVITYID)
            .category(UPDATED_CATEGORY);

        restEmployeeProfileHistoryLogsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeProfileHistoryLogs.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeProfileHistoryLogs))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeProfileHistoryLogs in the database
        List<EmployeeProfileHistoryLogs> employeeProfileHistoryLogsList = employeeProfileHistoryLogsRepository.findAll();
        assertThat(employeeProfileHistoryLogsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeProfileHistoryLogs testEmployeeProfileHistoryLogs = employeeProfileHistoryLogsList.get(
            employeeProfileHistoryLogsList.size() - 1
        );
        assertThat(testEmployeeProfileHistoryLogs.getTablename()).isEqualTo(UPDATED_TABLENAME);
        assertThat(testEmployeeProfileHistoryLogs.getRowid()).isEqualTo(UPDATED_ROWID);
        assertThat(testEmployeeProfileHistoryLogs.getEventtype()).isEqualTo(UPDATED_EVENTTYPE);
        assertThat(testEmployeeProfileHistoryLogs.getFields()).isEqualTo(DEFAULT_FIELDS);
        assertThat(testEmployeeProfileHistoryLogs.getFieldsContentType()).isEqualTo(DEFAULT_FIELDS_CONTENT_TYPE);
        assertThat(testEmployeeProfileHistoryLogs.getUpdatedbyid()).isEqualTo(DEFAULT_UPDATEDBYID);
        assertThat(testEmployeeProfileHistoryLogs.getActivityid()).isEqualTo(UPDATED_ACTIVITYID);
        assertThat(testEmployeeProfileHistoryLogs.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testEmployeeProfileHistoryLogs.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testEmployeeProfileHistoryLogs.getCategory()).isEqualTo(UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void fullUpdateEmployeeProfileHistoryLogsWithPatch() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        int databaseSizeBeforeUpdate = employeeProfileHistoryLogsRepository.findAll().size();

        // Update the employeeProfileHistoryLogs using partial update
        EmployeeProfileHistoryLogs partialUpdatedEmployeeProfileHistoryLogs = new EmployeeProfileHistoryLogs();
        partialUpdatedEmployeeProfileHistoryLogs.setId(employeeProfileHistoryLogs.getId());

        partialUpdatedEmployeeProfileHistoryLogs
            .tablename(UPDATED_TABLENAME)
            .rowid(UPDATED_ROWID)
            .eventtype(UPDATED_EVENTTYPE)
            .fields(UPDATED_FIELDS)
            .fieldsContentType(UPDATED_FIELDS_CONTENT_TYPE)
            .updatedbyid(UPDATED_UPDATEDBYID)
            .activityid(UPDATED_ACTIVITYID)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .category(UPDATED_CATEGORY);

        restEmployeeProfileHistoryLogsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeProfileHistoryLogs.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeProfileHistoryLogs))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeProfileHistoryLogs in the database
        List<EmployeeProfileHistoryLogs> employeeProfileHistoryLogsList = employeeProfileHistoryLogsRepository.findAll();
        assertThat(employeeProfileHistoryLogsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeProfileHistoryLogs testEmployeeProfileHistoryLogs = employeeProfileHistoryLogsList.get(
            employeeProfileHistoryLogsList.size() - 1
        );
        assertThat(testEmployeeProfileHistoryLogs.getTablename()).isEqualTo(UPDATED_TABLENAME);
        assertThat(testEmployeeProfileHistoryLogs.getRowid()).isEqualTo(UPDATED_ROWID);
        assertThat(testEmployeeProfileHistoryLogs.getEventtype()).isEqualTo(UPDATED_EVENTTYPE);
        assertThat(testEmployeeProfileHistoryLogs.getFields()).isEqualTo(UPDATED_FIELDS);
        assertThat(testEmployeeProfileHistoryLogs.getFieldsContentType()).isEqualTo(UPDATED_FIELDS_CONTENT_TYPE);
        assertThat(testEmployeeProfileHistoryLogs.getUpdatedbyid()).isEqualTo(UPDATED_UPDATEDBYID);
        assertThat(testEmployeeProfileHistoryLogs.getActivityid()).isEqualTo(UPDATED_ACTIVITYID);
        assertThat(testEmployeeProfileHistoryLogs.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeProfileHistoryLogs.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testEmployeeProfileHistoryLogs.getCategory()).isEqualTo(UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void patchNonExistingEmployeeProfileHistoryLogs() throws Exception {
        int databaseSizeBeforeUpdate = employeeProfileHistoryLogsRepository.findAll().size();
        employeeProfileHistoryLogs.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeProfileHistoryLogsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employeeProfileHistoryLogs.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeProfileHistoryLogs))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeProfileHistoryLogs in the database
        List<EmployeeProfileHistoryLogs> employeeProfileHistoryLogsList = employeeProfileHistoryLogsRepository.findAll();
        assertThat(employeeProfileHistoryLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployeeProfileHistoryLogs() throws Exception {
        int databaseSizeBeforeUpdate = employeeProfileHistoryLogsRepository.findAll().size();
        employeeProfileHistoryLogs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeProfileHistoryLogsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeProfileHistoryLogs))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeProfileHistoryLogs in the database
        List<EmployeeProfileHistoryLogs> employeeProfileHistoryLogsList = employeeProfileHistoryLogsRepository.findAll();
        assertThat(employeeProfileHistoryLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployeeProfileHistoryLogs() throws Exception {
        int databaseSizeBeforeUpdate = employeeProfileHistoryLogsRepository.findAll().size();
        employeeProfileHistoryLogs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeProfileHistoryLogsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeProfileHistoryLogs))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeProfileHistoryLogs in the database
        List<EmployeeProfileHistoryLogs> employeeProfileHistoryLogsList = employeeProfileHistoryLogsRepository.findAll();
        assertThat(employeeProfileHistoryLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployeeProfileHistoryLogs() throws Exception {
        // Initialize the database
        employeeProfileHistoryLogsRepository.saveAndFlush(employeeProfileHistoryLogs);

        int databaseSizeBeforeDelete = employeeProfileHistoryLogsRepository.findAll().size();

        // Delete the employeeProfileHistoryLogs
        restEmployeeProfileHistoryLogsMockMvc
            .perform(delete(ENTITY_API_URL_ID, employeeProfileHistoryLogs.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeProfileHistoryLogs> employeeProfileHistoryLogsList = employeeProfileHistoryLogsRepository.findAll();
        assertThat(employeeProfileHistoryLogsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
