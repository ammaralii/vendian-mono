package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.EmployeeEmergencyContacts;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.repository.EmployeeEmergencyContactsRepository;
import com.venturedive.vendian_mono.service.criteria.EmployeeEmergencyContactsCriteria;
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
 * Integration tests for the {@link EmployeeEmergencyContactsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeeEmergencyContactsResourceIT {

    private static final String DEFAULT_FULLNAME = "AAAAAAAAAA";
    private static final String UPDATED_FULLNAME = "BBBBBBBBBB";

    private static final String DEFAULT_RELATIONSHIP = "AAAAAAAAAA";
    private static final String UPDATED_RELATIONSHIP = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACTNO = "AAAAAAAAAA";
    private static final String UPDATED_CONTACTNO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/employee-emergency-contacts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeeEmergencyContactsRepository employeeEmergencyContactsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeEmergencyContactsMockMvc;

    private EmployeeEmergencyContacts employeeEmergencyContacts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeEmergencyContacts createEntity(EntityManager em) {
        EmployeeEmergencyContacts employeeEmergencyContacts = new EmployeeEmergencyContacts()
            .fullname(DEFAULT_FULLNAME)
            .relationship(DEFAULT_RELATIONSHIP)
            .contactno(DEFAULT_CONTACTNO)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        employeeEmergencyContacts.setEmployee(employees);
        return employeeEmergencyContacts;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeEmergencyContacts createUpdatedEntity(EntityManager em) {
        EmployeeEmergencyContacts employeeEmergencyContacts = new EmployeeEmergencyContacts()
            .fullname(UPDATED_FULLNAME)
            .relationship(UPDATED_RELATIONSHIP)
            .contactno(UPDATED_CONTACTNO)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createUpdatedEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        employeeEmergencyContacts.setEmployee(employees);
        return employeeEmergencyContacts;
    }

    @BeforeEach
    public void initTest() {
        employeeEmergencyContacts = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployeeEmergencyContacts() throws Exception {
        int databaseSizeBeforeCreate = employeeEmergencyContactsRepository.findAll().size();
        // Create the EmployeeEmergencyContacts
        restEmployeeEmergencyContactsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeEmergencyContacts))
            )
            .andExpect(status().isCreated());

        // Validate the EmployeeEmergencyContacts in the database
        List<EmployeeEmergencyContacts> employeeEmergencyContactsList = employeeEmergencyContactsRepository.findAll();
        assertThat(employeeEmergencyContactsList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeEmergencyContacts testEmployeeEmergencyContacts = employeeEmergencyContactsList.get(
            employeeEmergencyContactsList.size() - 1
        );
        assertThat(testEmployeeEmergencyContacts.getFullname()).isEqualTo(DEFAULT_FULLNAME);
        assertThat(testEmployeeEmergencyContacts.getRelationship()).isEqualTo(DEFAULT_RELATIONSHIP);
        assertThat(testEmployeeEmergencyContacts.getContactno()).isEqualTo(DEFAULT_CONTACTNO);
        assertThat(testEmployeeEmergencyContacts.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testEmployeeEmergencyContacts.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createEmployeeEmergencyContactsWithExistingId() throws Exception {
        // Create the EmployeeEmergencyContacts with an existing ID
        employeeEmergencyContacts.setId(1L);

        int databaseSizeBeforeCreate = employeeEmergencyContactsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeEmergencyContactsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeEmergencyContacts))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeEmergencyContacts in the database
        List<EmployeeEmergencyContacts> employeeEmergencyContactsList = employeeEmergencyContactsRepository.findAll();
        assertThat(employeeEmergencyContactsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFullnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeEmergencyContactsRepository.findAll().size();
        // set the field null
        employeeEmergencyContacts.setFullname(null);

        // Create the EmployeeEmergencyContacts, which fails.

        restEmployeeEmergencyContactsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeEmergencyContacts))
            )
            .andExpect(status().isBadRequest());

        List<EmployeeEmergencyContacts> employeeEmergencyContactsList = employeeEmergencyContactsRepository.findAll();
        assertThat(employeeEmergencyContactsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRelationshipIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeEmergencyContactsRepository.findAll().size();
        // set the field null
        employeeEmergencyContacts.setRelationship(null);

        // Create the EmployeeEmergencyContacts, which fails.

        restEmployeeEmergencyContactsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeEmergencyContacts))
            )
            .andExpect(status().isBadRequest());

        List<EmployeeEmergencyContacts> employeeEmergencyContactsList = employeeEmergencyContactsRepository.findAll();
        assertThat(employeeEmergencyContactsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContactnoIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeEmergencyContactsRepository.findAll().size();
        // set the field null
        employeeEmergencyContacts.setContactno(null);

        // Create the EmployeeEmergencyContacts, which fails.

        restEmployeeEmergencyContactsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeEmergencyContacts))
            )
            .andExpect(status().isBadRequest());

        List<EmployeeEmergencyContacts> employeeEmergencyContactsList = employeeEmergencyContactsRepository.findAll();
        assertThat(employeeEmergencyContactsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmployeeEmergencyContacts() throws Exception {
        // Initialize the database
        employeeEmergencyContactsRepository.saveAndFlush(employeeEmergencyContacts);

        // Get all the employeeEmergencyContactsList
        restEmployeeEmergencyContactsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeEmergencyContacts.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullname").value(hasItem(DEFAULT_FULLNAME)))
            .andExpect(jsonPath("$.[*].relationship").value(hasItem(DEFAULT_RELATIONSHIP)))
            .andExpect(jsonPath("$.[*].contactno").value(hasItem(DEFAULT_CONTACTNO)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getEmployeeEmergencyContacts() throws Exception {
        // Initialize the database
        employeeEmergencyContactsRepository.saveAndFlush(employeeEmergencyContacts);

        // Get the employeeEmergencyContacts
        restEmployeeEmergencyContactsMockMvc
            .perform(get(ENTITY_API_URL_ID, employeeEmergencyContacts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeEmergencyContacts.getId().intValue()))
            .andExpect(jsonPath("$.fullname").value(DEFAULT_FULLNAME))
            .andExpect(jsonPath("$.relationship").value(DEFAULT_RELATIONSHIP))
            .andExpect(jsonPath("$.contactno").value(DEFAULT_CONTACTNO))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getEmployeeEmergencyContactsByIdFiltering() throws Exception {
        // Initialize the database
        employeeEmergencyContactsRepository.saveAndFlush(employeeEmergencyContacts);

        Long id = employeeEmergencyContacts.getId();

        defaultEmployeeEmergencyContactsShouldBeFound("id.equals=" + id);
        defaultEmployeeEmergencyContactsShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeEmergencyContactsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeEmergencyContactsShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeEmergencyContactsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeEmergencyContactsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployeeEmergencyContactsByFullnameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeEmergencyContactsRepository.saveAndFlush(employeeEmergencyContacts);

        // Get all the employeeEmergencyContactsList where fullname equals to DEFAULT_FULLNAME
        defaultEmployeeEmergencyContactsShouldBeFound("fullname.equals=" + DEFAULT_FULLNAME);

        // Get all the employeeEmergencyContactsList where fullname equals to UPDATED_FULLNAME
        defaultEmployeeEmergencyContactsShouldNotBeFound("fullname.equals=" + UPDATED_FULLNAME);
    }

    @Test
    @Transactional
    void getAllEmployeeEmergencyContactsByFullnameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeEmergencyContactsRepository.saveAndFlush(employeeEmergencyContacts);

        // Get all the employeeEmergencyContactsList where fullname in DEFAULT_FULLNAME or UPDATED_FULLNAME
        defaultEmployeeEmergencyContactsShouldBeFound("fullname.in=" + DEFAULT_FULLNAME + "," + UPDATED_FULLNAME);

        // Get all the employeeEmergencyContactsList where fullname equals to UPDATED_FULLNAME
        defaultEmployeeEmergencyContactsShouldNotBeFound("fullname.in=" + UPDATED_FULLNAME);
    }

    @Test
    @Transactional
    void getAllEmployeeEmergencyContactsByFullnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeEmergencyContactsRepository.saveAndFlush(employeeEmergencyContacts);

        // Get all the employeeEmergencyContactsList where fullname is not null
        defaultEmployeeEmergencyContactsShouldBeFound("fullname.specified=true");

        // Get all the employeeEmergencyContactsList where fullname is null
        defaultEmployeeEmergencyContactsShouldNotBeFound("fullname.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeEmergencyContactsByFullnameContainsSomething() throws Exception {
        // Initialize the database
        employeeEmergencyContactsRepository.saveAndFlush(employeeEmergencyContacts);

        // Get all the employeeEmergencyContactsList where fullname contains DEFAULT_FULLNAME
        defaultEmployeeEmergencyContactsShouldBeFound("fullname.contains=" + DEFAULT_FULLNAME);

        // Get all the employeeEmergencyContactsList where fullname contains UPDATED_FULLNAME
        defaultEmployeeEmergencyContactsShouldNotBeFound("fullname.contains=" + UPDATED_FULLNAME);
    }

    @Test
    @Transactional
    void getAllEmployeeEmergencyContactsByFullnameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeEmergencyContactsRepository.saveAndFlush(employeeEmergencyContacts);

        // Get all the employeeEmergencyContactsList where fullname does not contain DEFAULT_FULLNAME
        defaultEmployeeEmergencyContactsShouldNotBeFound("fullname.doesNotContain=" + DEFAULT_FULLNAME);

        // Get all the employeeEmergencyContactsList where fullname does not contain UPDATED_FULLNAME
        defaultEmployeeEmergencyContactsShouldBeFound("fullname.doesNotContain=" + UPDATED_FULLNAME);
    }

    @Test
    @Transactional
    void getAllEmployeeEmergencyContactsByRelationshipIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeEmergencyContactsRepository.saveAndFlush(employeeEmergencyContacts);

        // Get all the employeeEmergencyContactsList where relationship equals to DEFAULT_RELATIONSHIP
        defaultEmployeeEmergencyContactsShouldBeFound("relationship.equals=" + DEFAULT_RELATIONSHIP);

        // Get all the employeeEmergencyContactsList where relationship equals to UPDATED_RELATIONSHIP
        defaultEmployeeEmergencyContactsShouldNotBeFound("relationship.equals=" + UPDATED_RELATIONSHIP);
    }

    @Test
    @Transactional
    void getAllEmployeeEmergencyContactsByRelationshipIsInShouldWork() throws Exception {
        // Initialize the database
        employeeEmergencyContactsRepository.saveAndFlush(employeeEmergencyContacts);

        // Get all the employeeEmergencyContactsList where relationship in DEFAULT_RELATIONSHIP or UPDATED_RELATIONSHIP
        defaultEmployeeEmergencyContactsShouldBeFound("relationship.in=" + DEFAULT_RELATIONSHIP + "," + UPDATED_RELATIONSHIP);

        // Get all the employeeEmergencyContactsList where relationship equals to UPDATED_RELATIONSHIP
        defaultEmployeeEmergencyContactsShouldNotBeFound("relationship.in=" + UPDATED_RELATIONSHIP);
    }

    @Test
    @Transactional
    void getAllEmployeeEmergencyContactsByRelationshipIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeEmergencyContactsRepository.saveAndFlush(employeeEmergencyContacts);

        // Get all the employeeEmergencyContactsList where relationship is not null
        defaultEmployeeEmergencyContactsShouldBeFound("relationship.specified=true");

        // Get all the employeeEmergencyContactsList where relationship is null
        defaultEmployeeEmergencyContactsShouldNotBeFound("relationship.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeEmergencyContactsByRelationshipContainsSomething() throws Exception {
        // Initialize the database
        employeeEmergencyContactsRepository.saveAndFlush(employeeEmergencyContacts);

        // Get all the employeeEmergencyContactsList where relationship contains DEFAULT_RELATIONSHIP
        defaultEmployeeEmergencyContactsShouldBeFound("relationship.contains=" + DEFAULT_RELATIONSHIP);

        // Get all the employeeEmergencyContactsList where relationship contains UPDATED_RELATIONSHIP
        defaultEmployeeEmergencyContactsShouldNotBeFound("relationship.contains=" + UPDATED_RELATIONSHIP);
    }

    @Test
    @Transactional
    void getAllEmployeeEmergencyContactsByRelationshipNotContainsSomething() throws Exception {
        // Initialize the database
        employeeEmergencyContactsRepository.saveAndFlush(employeeEmergencyContacts);

        // Get all the employeeEmergencyContactsList where relationship does not contain DEFAULT_RELATIONSHIP
        defaultEmployeeEmergencyContactsShouldNotBeFound("relationship.doesNotContain=" + DEFAULT_RELATIONSHIP);

        // Get all the employeeEmergencyContactsList where relationship does not contain UPDATED_RELATIONSHIP
        defaultEmployeeEmergencyContactsShouldBeFound("relationship.doesNotContain=" + UPDATED_RELATIONSHIP);
    }

    @Test
    @Transactional
    void getAllEmployeeEmergencyContactsByContactnoIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeEmergencyContactsRepository.saveAndFlush(employeeEmergencyContacts);

        // Get all the employeeEmergencyContactsList where contactno equals to DEFAULT_CONTACTNO
        defaultEmployeeEmergencyContactsShouldBeFound("contactno.equals=" + DEFAULT_CONTACTNO);

        // Get all the employeeEmergencyContactsList where contactno equals to UPDATED_CONTACTNO
        defaultEmployeeEmergencyContactsShouldNotBeFound("contactno.equals=" + UPDATED_CONTACTNO);
    }

    @Test
    @Transactional
    void getAllEmployeeEmergencyContactsByContactnoIsInShouldWork() throws Exception {
        // Initialize the database
        employeeEmergencyContactsRepository.saveAndFlush(employeeEmergencyContacts);

        // Get all the employeeEmergencyContactsList where contactno in DEFAULT_CONTACTNO or UPDATED_CONTACTNO
        defaultEmployeeEmergencyContactsShouldBeFound("contactno.in=" + DEFAULT_CONTACTNO + "," + UPDATED_CONTACTNO);

        // Get all the employeeEmergencyContactsList where contactno equals to UPDATED_CONTACTNO
        defaultEmployeeEmergencyContactsShouldNotBeFound("contactno.in=" + UPDATED_CONTACTNO);
    }

    @Test
    @Transactional
    void getAllEmployeeEmergencyContactsByContactnoIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeEmergencyContactsRepository.saveAndFlush(employeeEmergencyContacts);

        // Get all the employeeEmergencyContactsList where contactno is not null
        defaultEmployeeEmergencyContactsShouldBeFound("contactno.specified=true");

        // Get all the employeeEmergencyContactsList where contactno is null
        defaultEmployeeEmergencyContactsShouldNotBeFound("contactno.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeEmergencyContactsByContactnoContainsSomething() throws Exception {
        // Initialize the database
        employeeEmergencyContactsRepository.saveAndFlush(employeeEmergencyContacts);

        // Get all the employeeEmergencyContactsList where contactno contains DEFAULT_CONTACTNO
        defaultEmployeeEmergencyContactsShouldBeFound("contactno.contains=" + DEFAULT_CONTACTNO);

        // Get all the employeeEmergencyContactsList where contactno contains UPDATED_CONTACTNO
        defaultEmployeeEmergencyContactsShouldNotBeFound("contactno.contains=" + UPDATED_CONTACTNO);
    }

    @Test
    @Transactional
    void getAllEmployeeEmergencyContactsByContactnoNotContainsSomething() throws Exception {
        // Initialize the database
        employeeEmergencyContactsRepository.saveAndFlush(employeeEmergencyContacts);

        // Get all the employeeEmergencyContactsList where contactno does not contain DEFAULT_CONTACTNO
        defaultEmployeeEmergencyContactsShouldNotBeFound("contactno.doesNotContain=" + DEFAULT_CONTACTNO);

        // Get all the employeeEmergencyContactsList where contactno does not contain UPDATED_CONTACTNO
        defaultEmployeeEmergencyContactsShouldBeFound("contactno.doesNotContain=" + UPDATED_CONTACTNO);
    }

    @Test
    @Transactional
    void getAllEmployeeEmergencyContactsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeEmergencyContactsRepository.saveAndFlush(employeeEmergencyContacts);

        // Get all the employeeEmergencyContactsList where createdat equals to DEFAULT_CREATEDAT
        defaultEmployeeEmergencyContactsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the employeeEmergencyContactsList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeEmergencyContactsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeEmergencyContactsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeEmergencyContactsRepository.saveAndFlush(employeeEmergencyContacts);

        // Get all the employeeEmergencyContactsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultEmployeeEmergencyContactsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the employeeEmergencyContactsList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeEmergencyContactsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeEmergencyContactsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeEmergencyContactsRepository.saveAndFlush(employeeEmergencyContacts);

        // Get all the employeeEmergencyContactsList where createdat is not null
        defaultEmployeeEmergencyContactsShouldBeFound("createdat.specified=true");

        // Get all the employeeEmergencyContactsList where createdat is null
        defaultEmployeeEmergencyContactsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeEmergencyContactsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeEmergencyContactsRepository.saveAndFlush(employeeEmergencyContacts);

        // Get all the employeeEmergencyContactsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultEmployeeEmergencyContactsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the employeeEmergencyContactsList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeEmergencyContactsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeEmergencyContactsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeEmergencyContactsRepository.saveAndFlush(employeeEmergencyContacts);

        // Get all the employeeEmergencyContactsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultEmployeeEmergencyContactsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the employeeEmergencyContactsList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeEmergencyContactsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeEmergencyContactsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeEmergencyContactsRepository.saveAndFlush(employeeEmergencyContacts);

        // Get all the employeeEmergencyContactsList where updatedat is not null
        defaultEmployeeEmergencyContactsShouldBeFound("updatedat.specified=true");

        // Get all the employeeEmergencyContactsList where updatedat is null
        defaultEmployeeEmergencyContactsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeEmergencyContactsByEmployeeIsEqualToSomething() throws Exception {
        Employees employee;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employeeEmergencyContactsRepository.saveAndFlush(employeeEmergencyContacts);
            employee = EmployeesResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employee);
        em.flush();
        employeeEmergencyContacts.setEmployee(employee);
        employeeEmergencyContactsRepository.saveAndFlush(employeeEmergencyContacts);
        Long employeeId = employee.getId();

        // Get all the employeeEmergencyContactsList where employee equals to employeeId
        defaultEmployeeEmergencyContactsShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the employeeEmergencyContactsList where employee equals to (employeeId + 1)
        defaultEmployeeEmergencyContactsShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeEmergencyContactsShouldBeFound(String filter) throws Exception {
        restEmployeeEmergencyContactsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeEmergencyContacts.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullname").value(hasItem(DEFAULT_FULLNAME)))
            .andExpect(jsonPath("$.[*].relationship").value(hasItem(DEFAULT_RELATIONSHIP)))
            .andExpect(jsonPath("$.[*].contactno").value(hasItem(DEFAULT_CONTACTNO)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restEmployeeEmergencyContactsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeEmergencyContactsShouldNotBeFound(String filter) throws Exception {
        restEmployeeEmergencyContactsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeEmergencyContactsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployeeEmergencyContacts() throws Exception {
        // Get the employeeEmergencyContacts
        restEmployeeEmergencyContactsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmployeeEmergencyContacts() throws Exception {
        // Initialize the database
        employeeEmergencyContactsRepository.saveAndFlush(employeeEmergencyContacts);

        int databaseSizeBeforeUpdate = employeeEmergencyContactsRepository.findAll().size();

        // Update the employeeEmergencyContacts
        EmployeeEmergencyContacts updatedEmployeeEmergencyContacts = employeeEmergencyContactsRepository
            .findById(employeeEmergencyContacts.getId())
            .get();
        // Disconnect from session so that the updates on updatedEmployeeEmergencyContacts are not directly saved in db
        em.detach(updatedEmployeeEmergencyContacts);
        updatedEmployeeEmergencyContacts
            .fullname(UPDATED_FULLNAME)
            .relationship(UPDATED_RELATIONSHIP)
            .contactno(UPDATED_CONTACTNO)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restEmployeeEmergencyContactsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmployeeEmergencyContacts.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeEmergencyContacts))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeEmergencyContacts in the database
        List<EmployeeEmergencyContacts> employeeEmergencyContactsList = employeeEmergencyContactsRepository.findAll();
        assertThat(employeeEmergencyContactsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeEmergencyContacts testEmployeeEmergencyContacts = employeeEmergencyContactsList.get(
            employeeEmergencyContactsList.size() - 1
        );
        assertThat(testEmployeeEmergencyContacts.getFullname()).isEqualTo(UPDATED_FULLNAME);
        assertThat(testEmployeeEmergencyContacts.getRelationship()).isEqualTo(UPDATED_RELATIONSHIP);
        assertThat(testEmployeeEmergencyContacts.getContactno()).isEqualTo(UPDATED_CONTACTNO);
        assertThat(testEmployeeEmergencyContacts.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeEmergencyContacts.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingEmployeeEmergencyContacts() throws Exception {
        int databaseSizeBeforeUpdate = employeeEmergencyContactsRepository.findAll().size();
        employeeEmergencyContacts.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeEmergencyContactsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeEmergencyContacts.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeEmergencyContacts))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeEmergencyContacts in the database
        List<EmployeeEmergencyContacts> employeeEmergencyContactsList = employeeEmergencyContactsRepository.findAll();
        assertThat(employeeEmergencyContactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployeeEmergencyContacts() throws Exception {
        int databaseSizeBeforeUpdate = employeeEmergencyContactsRepository.findAll().size();
        employeeEmergencyContacts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeEmergencyContactsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeEmergencyContacts))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeEmergencyContacts in the database
        List<EmployeeEmergencyContacts> employeeEmergencyContactsList = employeeEmergencyContactsRepository.findAll();
        assertThat(employeeEmergencyContactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployeeEmergencyContacts() throws Exception {
        int databaseSizeBeforeUpdate = employeeEmergencyContactsRepository.findAll().size();
        employeeEmergencyContacts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeEmergencyContactsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeEmergencyContacts))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeEmergencyContacts in the database
        List<EmployeeEmergencyContacts> employeeEmergencyContactsList = employeeEmergencyContactsRepository.findAll();
        assertThat(employeeEmergencyContactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeEmergencyContactsWithPatch() throws Exception {
        // Initialize the database
        employeeEmergencyContactsRepository.saveAndFlush(employeeEmergencyContacts);

        int databaseSizeBeforeUpdate = employeeEmergencyContactsRepository.findAll().size();

        // Update the employeeEmergencyContacts using partial update
        EmployeeEmergencyContacts partialUpdatedEmployeeEmergencyContacts = new EmployeeEmergencyContacts();
        partialUpdatedEmployeeEmergencyContacts.setId(employeeEmergencyContacts.getId());

        partialUpdatedEmployeeEmergencyContacts
            .relationship(UPDATED_RELATIONSHIP)
            .contactno(UPDATED_CONTACTNO)
            .createdat(UPDATED_CREATEDAT);

        restEmployeeEmergencyContactsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeEmergencyContacts.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeEmergencyContacts))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeEmergencyContacts in the database
        List<EmployeeEmergencyContacts> employeeEmergencyContactsList = employeeEmergencyContactsRepository.findAll();
        assertThat(employeeEmergencyContactsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeEmergencyContacts testEmployeeEmergencyContacts = employeeEmergencyContactsList.get(
            employeeEmergencyContactsList.size() - 1
        );
        assertThat(testEmployeeEmergencyContacts.getFullname()).isEqualTo(DEFAULT_FULLNAME);
        assertThat(testEmployeeEmergencyContacts.getRelationship()).isEqualTo(UPDATED_RELATIONSHIP);
        assertThat(testEmployeeEmergencyContacts.getContactno()).isEqualTo(UPDATED_CONTACTNO);
        assertThat(testEmployeeEmergencyContacts.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeEmergencyContacts.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateEmployeeEmergencyContactsWithPatch() throws Exception {
        // Initialize the database
        employeeEmergencyContactsRepository.saveAndFlush(employeeEmergencyContacts);

        int databaseSizeBeforeUpdate = employeeEmergencyContactsRepository.findAll().size();

        // Update the employeeEmergencyContacts using partial update
        EmployeeEmergencyContacts partialUpdatedEmployeeEmergencyContacts = new EmployeeEmergencyContacts();
        partialUpdatedEmployeeEmergencyContacts.setId(employeeEmergencyContacts.getId());

        partialUpdatedEmployeeEmergencyContacts
            .fullname(UPDATED_FULLNAME)
            .relationship(UPDATED_RELATIONSHIP)
            .contactno(UPDATED_CONTACTNO)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restEmployeeEmergencyContactsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeEmergencyContacts.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeEmergencyContacts))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeEmergencyContacts in the database
        List<EmployeeEmergencyContacts> employeeEmergencyContactsList = employeeEmergencyContactsRepository.findAll();
        assertThat(employeeEmergencyContactsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeEmergencyContacts testEmployeeEmergencyContacts = employeeEmergencyContactsList.get(
            employeeEmergencyContactsList.size() - 1
        );
        assertThat(testEmployeeEmergencyContacts.getFullname()).isEqualTo(UPDATED_FULLNAME);
        assertThat(testEmployeeEmergencyContacts.getRelationship()).isEqualTo(UPDATED_RELATIONSHIP);
        assertThat(testEmployeeEmergencyContacts.getContactno()).isEqualTo(UPDATED_CONTACTNO);
        assertThat(testEmployeeEmergencyContacts.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeEmergencyContacts.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingEmployeeEmergencyContacts() throws Exception {
        int databaseSizeBeforeUpdate = employeeEmergencyContactsRepository.findAll().size();
        employeeEmergencyContacts.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeEmergencyContactsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employeeEmergencyContacts.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeEmergencyContacts))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeEmergencyContacts in the database
        List<EmployeeEmergencyContacts> employeeEmergencyContactsList = employeeEmergencyContactsRepository.findAll();
        assertThat(employeeEmergencyContactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployeeEmergencyContacts() throws Exception {
        int databaseSizeBeforeUpdate = employeeEmergencyContactsRepository.findAll().size();
        employeeEmergencyContacts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeEmergencyContactsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeEmergencyContacts))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeEmergencyContacts in the database
        List<EmployeeEmergencyContacts> employeeEmergencyContactsList = employeeEmergencyContactsRepository.findAll();
        assertThat(employeeEmergencyContactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployeeEmergencyContacts() throws Exception {
        int databaseSizeBeforeUpdate = employeeEmergencyContactsRepository.findAll().size();
        employeeEmergencyContacts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeEmergencyContactsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeEmergencyContacts))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeEmergencyContacts in the database
        List<EmployeeEmergencyContacts> employeeEmergencyContactsList = employeeEmergencyContactsRepository.findAll();
        assertThat(employeeEmergencyContactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployeeEmergencyContacts() throws Exception {
        // Initialize the database
        employeeEmergencyContactsRepository.saveAndFlush(employeeEmergencyContacts);

        int databaseSizeBeforeDelete = employeeEmergencyContactsRepository.findAll().size();

        // Delete the employeeEmergencyContacts
        restEmployeeEmergencyContactsMockMvc
            .perform(delete(ENTITY_API_URL_ID, employeeEmergencyContacts.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeEmergencyContacts> employeeEmergencyContactsList = employeeEmergencyContactsRepository.findAll();
        assertThat(employeeEmergencyContactsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
