package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.EmployeeCertificates;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.repository.EmployeeCertificatesRepository;
import com.venturedive.vendian_mono.service.criteria.EmployeeCertificatesCriteria;
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
 * Integration tests for the {@link EmployeeCertificatesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeeCertificatesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CERTIFICATENO = "AAAAAAAAAA";
    private static final String UPDATED_CERTIFICATENO = "BBBBBBBBBB";

    private static final String DEFAULT_ISSUINGBODY = "AAAAAAAAAA";
    private static final String UPDATED_ISSUINGBODY = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALIDTILL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALIDTILL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CERTIFICATECOMPETENCY = "AAAAAAAAAA";
    private static final String UPDATED_CERTIFICATECOMPETENCY = "BBBBBBBBBB";

    private static final Instant DEFAULT_DELETEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/employee-certificates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeeCertificatesRepository employeeCertificatesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeCertificatesMockMvc;

    private EmployeeCertificates employeeCertificates;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeCertificates createEntity(EntityManager em) {
        EmployeeCertificates employeeCertificates = new EmployeeCertificates()
            .name(DEFAULT_NAME)
            .certificateno(DEFAULT_CERTIFICATENO)
            .issuingbody(DEFAULT_ISSUINGBODY)
            .date(DEFAULT_DATE)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT)
            .validtill(DEFAULT_VALIDTILL)
            .certificatecompetency(DEFAULT_CERTIFICATECOMPETENCY)
            .deletedat(DEFAULT_DELETEDAT);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        employeeCertificates.setEmployee(employees);
        return employeeCertificates;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeCertificates createUpdatedEntity(EntityManager em) {
        EmployeeCertificates employeeCertificates = new EmployeeCertificates()
            .name(UPDATED_NAME)
            .certificateno(UPDATED_CERTIFICATENO)
            .issuingbody(UPDATED_ISSUINGBODY)
            .date(UPDATED_DATE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .validtill(UPDATED_VALIDTILL)
            .certificatecompetency(UPDATED_CERTIFICATECOMPETENCY)
            .deletedat(UPDATED_DELETEDAT);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createUpdatedEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        employeeCertificates.setEmployee(employees);
        return employeeCertificates;
    }

    @BeforeEach
    public void initTest() {
        employeeCertificates = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployeeCertificates() throws Exception {
        int databaseSizeBeforeCreate = employeeCertificatesRepository.findAll().size();
        // Create the EmployeeCertificates
        restEmployeeCertificatesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeCertificates))
            )
            .andExpect(status().isCreated());

        // Validate the EmployeeCertificates in the database
        List<EmployeeCertificates> employeeCertificatesList = employeeCertificatesRepository.findAll();
        assertThat(employeeCertificatesList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeCertificates testEmployeeCertificates = employeeCertificatesList.get(employeeCertificatesList.size() - 1);
        assertThat(testEmployeeCertificates.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEmployeeCertificates.getCertificateno()).isEqualTo(DEFAULT_CERTIFICATENO);
        assertThat(testEmployeeCertificates.getIssuingbody()).isEqualTo(DEFAULT_ISSUINGBODY);
        assertThat(testEmployeeCertificates.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testEmployeeCertificates.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testEmployeeCertificates.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testEmployeeCertificates.getValidtill()).isEqualTo(DEFAULT_VALIDTILL);
        assertThat(testEmployeeCertificates.getCertificatecompetency()).isEqualTo(DEFAULT_CERTIFICATECOMPETENCY);
        assertThat(testEmployeeCertificates.getDeletedat()).isEqualTo(DEFAULT_DELETEDAT);
    }

    @Test
    @Transactional
    void createEmployeeCertificatesWithExistingId() throws Exception {
        // Create the EmployeeCertificates with an existing ID
        employeeCertificates.setId(1L);

        int databaseSizeBeforeCreate = employeeCertificatesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeCertificatesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeCertificates))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeCertificates in the database
        List<EmployeeCertificates> employeeCertificatesList = employeeCertificatesRepository.findAll();
        assertThat(employeeCertificatesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeCertificatesRepository.findAll().size();
        // set the field null
        employeeCertificates.setName(null);

        // Create the EmployeeCertificates, which fails.

        restEmployeeCertificatesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeCertificates))
            )
            .andExpect(status().isBadRequest());

        List<EmployeeCertificates> employeeCertificatesList = employeeCertificatesRepository.findAll();
        assertThat(employeeCertificatesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeCertificatesRepository.findAll().size();
        // set the field null
        employeeCertificates.setDate(null);

        // Create the EmployeeCertificates, which fails.

        restEmployeeCertificatesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeCertificates))
            )
            .andExpect(status().isBadRequest());

        List<EmployeeCertificates> employeeCertificatesList = employeeCertificatesRepository.findAll();
        assertThat(employeeCertificatesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmployeeCertificates() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        // Get all the employeeCertificatesList
        restEmployeeCertificatesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeCertificates.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].certificateno").value(hasItem(DEFAULT_CERTIFICATENO)))
            .andExpect(jsonPath("$.[*].issuingbody").value(hasItem(DEFAULT_ISSUINGBODY)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].validtill").value(hasItem(DEFAULT_VALIDTILL.toString())))
            .andExpect(jsonPath("$.[*].certificatecompetency").value(hasItem(DEFAULT_CERTIFICATECOMPETENCY)))
            .andExpect(jsonPath("$.[*].deletedat").value(hasItem(DEFAULT_DELETEDAT.toString())));
    }

    @Test
    @Transactional
    void getEmployeeCertificates() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        // Get the employeeCertificates
        restEmployeeCertificatesMockMvc
            .perform(get(ENTITY_API_URL_ID, employeeCertificates.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeCertificates.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.certificateno").value(DEFAULT_CERTIFICATENO))
            .andExpect(jsonPath("$.issuingbody").value(DEFAULT_ISSUINGBODY))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()))
            .andExpect(jsonPath("$.validtill").value(DEFAULT_VALIDTILL.toString()))
            .andExpect(jsonPath("$.certificatecompetency").value(DEFAULT_CERTIFICATECOMPETENCY))
            .andExpect(jsonPath("$.deletedat").value(DEFAULT_DELETEDAT.toString()));
    }

    @Test
    @Transactional
    void getEmployeeCertificatesByIdFiltering() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        Long id = employeeCertificates.getId();

        defaultEmployeeCertificatesShouldBeFound("id.equals=" + id);
        defaultEmployeeCertificatesShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeCertificatesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeCertificatesShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeCertificatesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeCertificatesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployeeCertificatesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        // Get all the employeeCertificatesList where name equals to DEFAULT_NAME
        defaultEmployeeCertificatesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the employeeCertificatesList where name equals to UPDATED_NAME
        defaultEmployeeCertificatesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeeCertificatesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        // Get all the employeeCertificatesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultEmployeeCertificatesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the employeeCertificatesList where name equals to UPDATED_NAME
        defaultEmployeeCertificatesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeeCertificatesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        // Get all the employeeCertificatesList where name is not null
        defaultEmployeeCertificatesShouldBeFound("name.specified=true");

        // Get all the employeeCertificatesList where name is null
        defaultEmployeeCertificatesShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeCertificatesByNameContainsSomething() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        // Get all the employeeCertificatesList where name contains DEFAULT_NAME
        defaultEmployeeCertificatesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the employeeCertificatesList where name contains UPDATED_NAME
        defaultEmployeeCertificatesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeeCertificatesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        // Get all the employeeCertificatesList where name does not contain DEFAULT_NAME
        defaultEmployeeCertificatesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the employeeCertificatesList where name does not contain UPDATED_NAME
        defaultEmployeeCertificatesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeeCertificatesByCertificatenoIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        // Get all the employeeCertificatesList where certificateno equals to DEFAULT_CERTIFICATENO
        defaultEmployeeCertificatesShouldBeFound("certificateno.equals=" + DEFAULT_CERTIFICATENO);

        // Get all the employeeCertificatesList where certificateno equals to UPDATED_CERTIFICATENO
        defaultEmployeeCertificatesShouldNotBeFound("certificateno.equals=" + UPDATED_CERTIFICATENO);
    }

    @Test
    @Transactional
    void getAllEmployeeCertificatesByCertificatenoIsInShouldWork() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        // Get all the employeeCertificatesList where certificateno in DEFAULT_CERTIFICATENO or UPDATED_CERTIFICATENO
        defaultEmployeeCertificatesShouldBeFound("certificateno.in=" + DEFAULT_CERTIFICATENO + "," + UPDATED_CERTIFICATENO);

        // Get all the employeeCertificatesList where certificateno equals to UPDATED_CERTIFICATENO
        defaultEmployeeCertificatesShouldNotBeFound("certificateno.in=" + UPDATED_CERTIFICATENO);
    }

    @Test
    @Transactional
    void getAllEmployeeCertificatesByCertificatenoIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        // Get all the employeeCertificatesList where certificateno is not null
        defaultEmployeeCertificatesShouldBeFound("certificateno.specified=true");

        // Get all the employeeCertificatesList where certificateno is null
        defaultEmployeeCertificatesShouldNotBeFound("certificateno.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeCertificatesByCertificatenoContainsSomething() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        // Get all the employeeCertificatesList where certificateno contains DEFAULT_CERTIFICATENO
        defaultEmployeeCertificatesShouldBeFound("certificateno.contains=" + DEFAULT_CERTIFICATENO);

        // Get all the employeeCertificatesList where certificateno contains UPDATED_CERTIFICATENO
        defaultEmployeeCertificatesShouldNotBeFound("certificateno.contains=" + UPDATED_CERTIFICATENO);
    }

    @Test
    @Transactional
    void getAllEmployeeCertificatesByCertificatenoNotContainsSomething() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        // Get all the employeeCertificatesList where certificateno does not contain DEFAULT_CERTIFICATENO
        defaultEmployeeCertificatesShouldNotBeFound("certificateno.doesNotContain=" + DEFAULT_CERTIFICATENO);

        // Get all the employeeCertificatesList where certificateno does not contain UPDATED_CERTIFICATENO
        defaultEmployeeCertificatesShouldBeFound("certificateno.doesNotContain=" + UPDATED_CERTIFICATENO);
    }

    @Test
    @Transactional
    void getAllEmployeeCertificatesByIssuingbodyIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        // Get all the employeeCertificatesList where issuingbody equals to DEFAULT_ISSUINGBODY
        defaultEmployeeCertificatesShouldBeFound("issuingbody.equals=" + DEFAULT_ISSUINGBODY);

        // Get all the employeeCertificatesList where issuingbody equals to UPDATED_ISSUINGBODY
        defaultEmployeeCertificatesShouldNotBeFound("issuingbody.equals=" + UPDATED_ISSUINGBODY);
    }

    @Test
    @Transactional
    void getAllEmployeeCertificatesByIssuingbodyIsInShouldWork() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        // Get all the employeeCertificatesList where issuingbody in DEFAULT_ISSUINGBODY or UPDATED_ISSUINGBODY
        defaultEmployeeCertificatesShouldBeFound("issuingbody.in=" + DEFAULT_ISSUINGBODY + "," + UPDATED_ISSUINGBODY);

        // Get all the employeeCertificatesList where issuingbody equals to UPDATED_ISSUINGBODY
        defaultEmployeeCertificatesShouldNotBeFound("issuingbody.in=" + UPDATED_ISSUINGBODY);
    }

    @Test
    @Transactional
    void getAllEmployeeCertificatesByIssuingbodyIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        // Get all the employeeCertificatesList where issuingbody is not null
        defaultEmployeeCertificatesShouldBeFound("issuingbody.specified=true");

        // Get all the employeeCertificatesList where issuingbody is null
        defaultEmployeeCertificatesShouldNotBeFound("issuingbody.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeCertificatesByIssuingbodyContainsSomething() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        // Get all the employeeCertificatesList where issuingbody contains DEFAULT_ISSUINGBODY
        defaultEmployeeCertificatesShouldBeFound("issuingbody.contains=" + DEFAULT_ISSUINGBODY);

        // Get all the employeeCertificatesList where issuingbody contains UPDATED_ISSUINGBODY
        defaultEmployeeCertificatesShouldNotBeFound("issuingbody.contains=" + UPDATED_ISSUINGBODY);
    }

    @Test
    @Transactional
    void getAllEmployeeCertificatesByIssuingbodyNotContainsSomething() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        // Get all the employeeCertificatesList where issuingbody does not contain DEFAULT_ISSUINGBODY
        defaultEmployeeCertificatesShouldNotBeFound("issuingbody.doesNotContain=" + DEFAULT_ISSUINGBODY);

        // Get all the employeeCertificatesList where issuingbody does not contain UPDATED_ISSUINGBODY
        defaultEmployeeCertificatesShouldBeFound("issuingbody.doesNotContain=" + UPDATED_ISSUINGBODY);
    }

    @Test
    @Transactional
    void getAllEmployeeCertificatesByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        // Get all the employeeCertificatesList where date equals to DEFAULT_DATE
        defaultEmployeeCertificatesShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the employeeCertificatesList where date equals to UPDATED_DATE
        defaultEmployeeCertificatesShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeeCertificatesByDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        // Get all the employeeCertificatesList where date in DEFAULT_DATE or UPDATED_DATE
        defaultEmployeeCertificatesShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the employeeCertificatesList where date equals to UPDATED_DATE
        defaultEmployeeCertificatesShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeeCertificatesByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        // Get all the employeeCertificatesList where date is not null
        defaultEmployeeCertificatesShouldBeFound("date.specified=true");

        // Get all the employeeCertificatesList where date is null
        defaultEmployeeCertificatesShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeCertificatesByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        // Get all the employeeCertificatesList where createdat equals to DEFAULT_CREATEDAT
        defaultEmployeeCertificatesShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the employeeCertificatesList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeCertificatesShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeCertificatesByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        // Get all the employeeCertificatesList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultEmployeeCertificatesShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the employeeCertificatesList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeCertificatesShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeCertificatesByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        // Get all the employeeCertificatesList where createdat is not null
        defaultEmployeeCertificatesShouldBeFound("createdat.specified=true");

        // Get all the employeeCertificatesList where createdat is null
        defaultEmployeeCertificatesShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeCertificatesByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        // Get all the employeeCertificatesList where updatedat equals to DEFAULT_UPDATEDAT
        defaultEmployeeCertificatesShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the employeeCertificatesList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeCertificatesShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeCertificatesByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        // Get all the employeeCertificatesList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultEmployeeCertificatesShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the employeeCertificatesList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeCertificatesShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeCertificatesByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        // Get all the employeeCertificatesList where updatedat is not null
        defaultEmployeeCertificatesShouldBeFound("updatedat.specified=true");

        // Get all the employeeCertificatesList where updatedat is null
        defaultEmployeeCertificatesShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeCertificatesByValidtillIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        // Get all the employeeCertificatesList where validtill equals to DEFAULT_VALIDTILL
        defaultEmployeeCertificatesShouldBeFound("validtill.equals=" + DEFAULT_VALIDTILL);

        // Get all the employeeCertificatesList where validtill equals to UPDATED_VALIDTILL
        defaultEmployeeCertificatesShouldNotBeFound("validtill.equals=" + UPDATED_VALIDTILL);
    }

    @Test
    @Transactional
    void getAllEmployeeCertificatesByValidtillIsInShouldWork() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        // Get all the employeeCertificatesList where validtill in DEFAULT_VALIDTILL or UPDATED_VALIDTILL
        defaultEmployeeCertificatesShouldBeFound("validtill.in=" + DEFAULT_VALIDTILL + "," + UPDATED_VALIDTILL);

        // Get all the employeeCertificatesList where validtill equals to UPDATED_VALIDTILL
        defaultEmployeeCertificatesShouldNotBeFound("validtill.in=" + UPDATED_VALIDTILL);
    }

    @Test
    @Transactional
    void getAllEmployeeCertificatesByValidtillIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        // Get all the employeeCertificatesList where validtill is not null
        defaultEmployeeCertificatesShouldBeFound("validtill.specified=true");

        // Get all the employeeCertificatesList where validtill is null
        defaultEmployeeCertificatesShouldNotBeFound("validtill.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeCertificatesByCertificatecompetencyIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        // Get all the employeeCertificatesList where certificatecompetency equals to DEFAULT_CERTIFICATECOMPETENCY
        defaultEmployeeCertificatesShouldBeFound("certificatecompetency.equals=" + DEFAULT_CERTIFICATECOMPETENCY);

        // Get all the employeeCertificatesList where certificatecompetency equals to UPDATED_CERTIFICATECOMPETENCY
        defaultEmployeeCertificatesShouldNotBeFound("certificatecompetency.equals=" + UPDATED_CERTIFICATECOMPETENCY);
    }

    @Test
    @Transactional
    void getAllEmployeeCertificatesByCertificatecompetencyIsInShouldWork() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        // Get all the employeeCertificatesList where certificatecompetency in DEFAULT_CERTIFICATECOMPETENCY or UPDATED_CERTIFICATECOMPETENCY
        defaultEmployeeCertificatesShouldBeFound(
            "certificatecompetency.in=" + DEFAULT_CERTIFICATECOMPETENCY + "," + UPDATED_CERTIFICATECOMPETENCY
        );

        // Get all the employeeCertificatesList where certificatecompetency equals to UPDATED_CERTIFICATECOMPETENCY
        defaultEmployeeCertificatesShouldNotBeFound("certificatecompetency.in=" + UPDATED_CERTIFICATECOMPETENCY);
    }

    @Test
    @Transactional
    void getAllEmployeeCertificatesByCertificatecompetencyIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        // Get all the employeeCertificatesList where certificatecompetency is not null
        defaultEmployeeCertificatesShouldBeFound("certificatecompetency.specified=true");

        // Get all the employeeCertificatesList where certificatecompetency is null
        defaultEmployeeCertificatesShouldNotBeFound("certificatecompetency.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeCertificatesByCertificatecompetencyContainsSomething() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        // Get all the employeeCertificatesList where certificatecompetency contains DEFAULT_CERTIFICATECOMPETENCY
        defaultEmployeeCertificatesShouldBeFound("certificatecompetency.contains=" + DEFAULT_CERTIFICATECOMPETENCY);

        // Get all the employeeCertificatesList where certificatecompetency contains UPDATED_CERTIFICATECOMPETENCY
        defaultEmployeeCertificatesShouldNotBeFound("certificatecompetency.contains=" + UPDATED_CERTIFICATECOMPETENCY);
    }

    @Test
    @Transactional
    void getAllEmployeeCertificatesByCertificatecompetencyNotContainsSomething() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        // Get all the employeeCertificatesList where certificatecompetency does not contain DEFAULT_CERTIFICATECOMPETENCY
        defaultEmployeeCertificatesShouldNotBeFound("certificatecompetency.doesNotContain=" + DEFAULT_CERTIFICATECOMPETENCY);

        // Get all the employeeCertificatesList where certificatecompetency does not contain UPDATED_CERTIFICATECOMPETENCY
        defaultEmployeeCertificatesShouldBeFound("certificatecompetency.doesNotContain=" + UPDATED_CERTIFICATECOMPETENCY);
    }

    @Test
    @Transactional
    void getAllEmployeeCertificatesByDeletedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        // Get all the employeeCertificatesList where deletedat equals to DEFAULT_DELETEDAT
        defaultEmployeeCertificatesShouldBeFound("deletedat.equals=" + DEFAULT_DELETEDAT);

        // Get all the employeeCertificatesList where deletedat equals to UPDATED_DELETEDAT
        defaultEmployeeCertificatesShouldNotBeFound("deletedat.equals=" + UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeCertificatesByDeletedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        // Get all the employeeCertificatesList where deletedat in DEFAULT_DELETEDAT or UPDATED_DELETEDAT
        defaultEmployeeCertificatesShouldBeFound("deletedat.in=" + DEFAULT_DELETEDAT + "," + UPDATED_DELETEDAT);

        // Get all the employeeCertificatesList where deletedat equals to UPDATED_DELETEDAT
        defaultEmployeeCertificatesShouldNotBeFound("deletedat.in=" + UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeCertificatesByDeletedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        // Get all the employeeCertificatesList where deletedat is not null
        defaultEmployeeCertificatesShouldBeFound("deletedat.specified=true");

        // Get all the employeeCertificatesList where deletedat is null
        defaultEmployeeCertificatesShouldNotBeFound("deletedat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeCertificatesByEmployeeIsEqualToSomething() throws Exception {
        Employees employee;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employeeCertificatesRepository.saveAndFlush(employeeCertificates);
            employee = EmployeesResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employee);
        em.flush();
        employeeCertificates.setEmployee(employee);
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);
        Long employeeId = employee.getId();

        // Get all the employeeCertificatesList where employee equals to employeeId
        defaultEmployeeCertificatesShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the employeeCertificatesList where employee equals to (employeeId + 1)
        defaultEmployeeCertificatesShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeCertificatesShouldBeFound(String filter) throws Exception {
        restEmployeeCertificatesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeCertificates.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].certificateno").value(hasItem(DEFAULT_CERTIFICATENO)))
            .andExpect(jsonPath("$.[*].issuingbody").value(hasItem(DEFAULT_ISSUINGBODY)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].validtill").value(hasItem(DEFAULT_VALIDTILL.toString())))
            .andExpect(jsonPath("$.[*].certificatecompetency").value(hasItem(DEFAULT_CERTIFICATECOMPETENCY)))
            .andExpect(jsonPath("$.[*].deletedat").value(hasItem(DEFAULT_DELETEDAT.toString())));

        // Check, that the count call also returns 1
        restEmployeeCertificatesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeCertificatesShouldNotBeFound(String filter) throws Exception {
        restEmployeeCertificatesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeCertificatesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployeeCertificates() throws Exception {
        // Get the employeeCertificates
        restEmployeeCertificatesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmployeeCertificates() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        int databaseSizeBeforeUpdate = employeeCertificatesRepository.findAll().size();

        // Update the employeeCertificates
        EmployeeCertificates updatedEmployeeCertificates = employeeCertificatesRepository.findById(employeeCertificates.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeCertificates are not directly saved in db
        em.detach(updatedEmployeeCertificates);
        updatedEmployeeCertificates
            .name(UPDATED_NAME)
            .certificateno(UPDATED_CERTIFICATENO)
            .issuingbody(UPDATED_ISSUINGBODY)
            .date(UPDATED_DATE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .validtill(UPDATED_VALIDTILL)
            .certificatecompetency(UPDATED_CERTIFICATECOMPETENCY)
            .deletedat(UPDATED_DELETEDAT);

        restEmployeeCertificatesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmployeeCertificates.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeCertificates))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeCertificates in the database
        List<EmployeeCertificates> employeeCertificatesList = employeeCertificatesRepository.findAll();
        assertThat(employeeCertificatesList).hasSize(databaseSizeBeforeUpdate);
        EmployeeCertificates testEmployeeCertificates = employeeCertificatesList.get(employeeCertificatesList.size() - 1);
        assertThat(testEmployeeCertificates.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmployeeCertificates.getCertificateno()).isEqualTo(UPDATED_CERTIFICATENO);
        assertThat(testEmployeeCertificates.getIssuingbody()).isEqualTo(UPDATED_ISSUINGBODY);
        assertThat(testEmployeeCertificates.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testEmployeeCertificates.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeCertificates.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testEmployeeCertificates.getValidtill()).isEqualTo(UPDATED_VALIDTILL);
        assertThat(testEmployeeCertificates.getCertificatecompetency()).isEqualTo(UPDATED_CERTIFICATECOMPETENCY);
        assertThat(testEmployeeCertificates.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void putNonExistingEmployeeCertificates() throws Exception {
        int databaseSizeBeforeUpdate = employeeCertificatesRepository.findAll().size();
        employeeCertificates.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeCertificatesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeCertificates.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeCertificates))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeCertificates in the database
        List<EmployeeCertificates> employeeCertificatesList = employeeCertificatesRepository.findAll();
        assertThat(employeeCertificatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployeeCertificates() throws Exception {
        int databaseSizeBeforeUpdate = employeeCertificatesRepository.findAll().size();
        employeeCertificates.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeCertificatesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeCertificates))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeCertificates in the database
        List<EmployeeCertificates> employeeCertificatesList = employeeCertificatesRepository.findAll();
        assertThat(employeeCertificatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployeeCertificates() throws Exception {
        int databaseSizeBeforeUpdate = employeeCertificatesRepository.findAll().size();
        employeeCertificates.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeCertificatesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeCertificates))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeCertificates in the database
        List<EmployeeCertificates> employeeCertificatesList = employeeCertificatesRepository.findAll();
        assertThat(employeeCertificatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeCertificatesWithPatch() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        int databaseSizeBeforeUpdate = employeeCertificatesRepository.findAll().size();

        // Update the employeeCertificates using partial update
        EmployeeCertificates partialUpdatedEmployeeCertificates = new EmployeeCertificates();
        partialUpdatedEmployeeCertificates.setId(employeeCertificates.getId());

        partialUpdatedEmployeeCertificates
            .name(UPDATED_NAME)
            .certificateno(UPDATED_CERTIFICATENO)
            .issuingbody(UPDATED_ISSUINGBODY)
            .certificatecompetency(UPDATED_CERTIFICATECOMPETENCY)
            .deletedat(UPDATED_DELETEDAT);

        restEmployeeCertificatesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeCertificates.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeCertificates))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeCertificates in the database
        List<EmployeeCertificates> employeeCertificatesList = employeeCertificatesRepository.findAll();
        assertThat(employeeCertificatesList).hasSize(databaseSizeBeforeUpdate);
        EmployeeCertificates testEmployeeCertificates = employeeCertificatesList.get(employeeCertificatesList.size() - 1);
        assertThat(testEmployeeCertificates.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmployeeCertificates.getCertificateno()).isEqualTo(UPDATED_CERTIFICATENO);
        assertThat(testEmployeeCertificates.getIssuingbody()).isEqualTo(UPDATED_ISSUINGBODY);
        assertThat(testEmployeeCertificates.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testEmployeeCertificates.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testEmployeeCertificates.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testEmployeeCertificates.getValidtill()).isEqualTo(DEFAULT_VALIDTILL);
        assertThat(testEmployeeCertificates.getCertificatecompetency()).isEqualTo(UPDATED_CERTIFICATECOMPETENCY);
        assertThat(testEmployeeCertificates.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void fullUpdateEmployeeCertificatesWithPatch() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        int databaseSizeBeforeUpdate = employeeCertificatesRepository.findAll().size();

        // Update the employeeCertificates using partial update
        EmployeeCertificates partialUpdatedEmployeeCertificates = new EmployeeCertificates();
        partialUpdatedEmployeeCertificates.setId(employeeCertificates.getId());

        partialUpdatedEmployeeCertificates
            .name(UPDATED_NAME)
            .certificateno(UPDATED_CERTIFICATENO)
            .issuingbody(UPDATED_ISSUINGBODY)
            .date(UPDATED_DATE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .validtill(UPDATED_VALIDTILL)
            .certificatecompetency(UPDATED_CERTIFICATECOMPETENCY)
            .deletedat(UPDATED_DELETEDAT);

        restEmployeeCertificatesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeCertificates.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeCertificates))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeCertificates in the database
        List<EmployeeCertificates> employeeCertificatesList = employeeCertificatesRepository.findAll();
        assertThat(employeeCertificatesList).hasSize(databaseSizeBeforeUpdate);
        EmployeeCertificates testEmployeeCertificates = employeeCertificatesList.get(employeeCertificatesList.size() - 1);
        assertThat(testEmployeeCertificates.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmployeeCertificates.getCertificateno()).isEqualTo(UPDATED_CERTIFICATENO);
        assertThat(testEmployeeCertificates.getIssuingbody()).isEqualTo(UPDATED_ISSUINGBODY);
        assertThat(testEmployeeCertificates.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testEmployeeCertificates.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeCertificates.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testEmployeeCertificates.getValidtill()).isEqualTo(UPDATED_VALIDTILL);
        assertThat(testEmployeeCertificates.getCertificatecompetency()).isEqualTo(UPDATED_CERTIFICATECOMPETENCY);
        assertThat(testEmployeeCertificates.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingEmployeeCertificates() throws Exception {
        int databaseSizeBeforeUpdate = employeeCertificatesRepository.findAll().size();
        employeeCertificates.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeCertificatesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employeeCertificates.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeCertificates))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeCertificates in the database
        List<EmployeeCertificates> employeeCertificatesList = employeeCertificatesRepository.findAll();
        assertThat(employeeCertificatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployeeCertificates() throws Exception {
        int databaseSizeBeforeUpdate = employeeCertificatesRepository.findAll().size();
        employeeCertificates.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeCertificatesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeCertificates))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeCertificates in the database
        List<EmployeeCertificates> employeeCertificatesList = employeeCertificatesRepository.findAll();
        assertThat(employeeCertificatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployeeCertificates() throws Exception {
        int databaseSizeBeforeUpdate = employeeCertificatesRepository.findAll().size();
        employeeCertificates.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeCertificatesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeCertificates))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeCertificates in the database
        List<EmployeeCertificates> employeeCertificatesList = employeeCertificatesRepository.findAll();
        assertThat(employeeCertificatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployeeCertificates() throws Exception {
        // Initialize the database
        employeeCertificatesRepository.saveAndFlush(employeeCertificates);

        int databaseSizeBeforeDelete = employeeCertificatesRepository.findAll().size();

        // Delete the employeeCertificates
        restEmployeeCertificatesMockMvc
            .perform(delete(ENTITY_API_URL_ID, employeeCertificates.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeCertificates> employeeCertificatesList = employeeCertificatesRepository.findAll();
        assertThat(employeeCertificatesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
