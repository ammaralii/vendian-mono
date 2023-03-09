package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Addresses;
import com.venturedive.vendian_mono.domain.EmployeeAddresses;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.repository.EmployeeAddressesRepository;
import com.venturedive.vendian_mono.service.criteria.EmployeeAddressesCriteria;
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
 * Integration tests for the {@link EmployeeAddressesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeeAddressesResourceIT {

    private static final Boolean DEFAULT_ISDELETED = false;
    private static final Boolean UPDATED_ISDELETED = true;

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TYPE = "AAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/employee-addresses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeeAddressesRepository employeeAddressesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeAddressesMockMvc;

    private EmployeeAddresses employeeAddresses;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeAddresses createEntity(EntityManager em) {
        EmployeeAddresses employeeAddresses = new EmployeeAddresses()
            .isdeleted(DEFAULT_ISDELETED)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT)
            .type(DEFAULT_TYPE);
        // Add required entity
        Addresses addresses;
        if (TestUtil.findAll(em, Addresses.class).isEmpty()) {
            addresses = AddressesResourceIT.createEntity(em);
            em.persist(addresses);
            em.flush();
        } else {
            addresses = TestUtil.findAll(em, Addresses.class).get(0);
        }
        employeeAddresses.setAddress(addresses);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        employeeAddresses.setEmployee(employees);
        return employeeAddresses;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeAddresses createUpdatedEntity(EntityManager em) {
        EmployeeAddresses employeeAddresses = new EmployeeAddresses()
            .isdeleted(UPDATED_ISDELETED)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .type(UPDATED_TYPE);
        // Add required entity
        Addresses addresses;
        if (TestUtil.findAll(em, Addresses.class).isEmpty()) {
            addresses = AddressesResourceIT.createUpdatedEntity(em);
            em.persist(addresses);
            em.flush();
        } else {
            addresses = TestUtil.findAll(em, Addresses.class).get(0);
        }
        employeeAddresses.setAddress(addresses);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createUpdatedEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        employeeAddresses.setEmployee(employees);
        return employeeAddresses;
    }

    @BeforeEach
    public void initTest() {
        employeeAddresses = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployeeAddresses() throws Exception {
        int databaseSizeBeforeCreate = employeeAddressesRepository.findAll().size();
        // Create the EmployeeAddresses
        restEmployeeAddressesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeAddresses))
            )
            .andExpect(status().isCreated());

        // Validate the EmployeeAddresses in the database
        List<EmployeeAddresses> employeeAddressesList = employeeAddressesRepository.findAll();
        assertThat(employeeAddressesList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeAddresses testEmployeeAddresses = employeeAddressesList.get(employeeAddressesList.size() - 1);
        assertThat(testEmployeeAddresses.getIsdeleted()).isEqualTo(DEFAULT_ISDELETED);
        assertThat(testEmployeeAddresses.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testEmployeeAddresses.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testEmployeeAddresses.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void createEmployeeAddressesWithExistingId() throws Exception {
        // Create the EmployeeAddresses with an existing ID
        employeeAddresses.setId(1L);

        int databaseSizeBeforeCreate = employeeAddressesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeAddressesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeAddresses))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeAddresses in the database
        List<EmployeeAddresses> employeeAddressesList = employeeAddressesRepository.findAll();
        assertThat(employeeAddressesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeAddressesRepository.findAll().size();
        // set the field null
        employeeAddresses.setType(null);

        // Create the EmployeeAddresses, which fails.

        restEmployeeAddressesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeAddresses))
            )
            .andExpect(status().isBadRequest());

        List<EmployeeAddresses> employeeAddressesList = employeeAddressesRepository.findAll();
        assertThat(employeeAddressesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmployeeAddresses() throws Exception {
        // Initialize the database
        employeeAddressesRepository.saveAndFlush(employeeAddresses);

        // Get all the employeeAddressesList
        restEmployeeAddressesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeAddresses.getId().intValue())))
            .andExpect(jsonPath("$.[*].isdeleted").value(hasItem(DEFAULT_ISDELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getEmployeeAddresses() throws Exception {
        // Initialize the database
        employeeAddressesRepository.saveAndFlush(employeeAddresses);

        // Get the employeeAddresses
        restEmployeeAddressesMockMvc
            .perform(get(ENTITY_API_URL_ID, employeeAddresses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeAddresses.getId().intValue()))
            .andExpect(jsonPath("$.isdeleted").value(DEFAULT_ISDELETED.booleanValue()))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getEmployeeAddressesByIdFiltering() throws Exception {
        // Initialize the database
        employeeAddressesRepository.saveAndFlush(employeeAddresses);

        Long id = employeeAddresses.getId();

        defaultEmployeeAddressesShouldBeFound("id.equals=" + id);
        defaultEmployeeAddressesShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeAddressesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeAddressesShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeAddressesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeAddressesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployeeAddressesByIsdeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeAddressesRepository.saveAndFlush(employeeAddresses);

        // Get all the employeeAddressesList where isdeleted equals to DEFAULT_ISDELETED
        defaultEmployeeAddressesShouldBeFound("isdeleted.equals=" + DEFAULT_ISDELETED);

        // Get all the employeeAddressesList where isdeleted equals to UPDATED_ISDELETED
        defaultEmployeeAddressesShouldNotBeFound("isdeleted.equals=" + UPDATED_ISDELETED);
    }

    @Test
    @Transactional
    void getAllEmployeeAddressesByIsdeletedIsInShouldWork() throws Exception {
        // Initialize the database
        employeeAddressesRepository.saveAndFlush(employeeAddresses);

        // Get all the employeeAddressesList where isdeleted in DEFAULT_ISDELETED or UPDATED_ISDELETED
        defaultEmployeeAddressesShouldBeFound("isdeleted.in=" + DEFAULT_ISDELETED + "," + UPDATED_ISDELETED);

        // Get all the employeeAddressesList where isdeleted equals to UPDATED_ISDELETED
        defaultEmployeeAddressesShouldNotBeFound("isdeleted.in=" + UPDATED_ISDELETED);
    }

    @Test
    @Transactional
    void getAllEmployeeAddressesByIsdeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeAddressesRepository.saveAndFlush(employeeAddresses);

        // Get all the employeeAddressesList where isdeleted is not null
        defaultEmployeeAddressesShouldBeFound("isdeleted.specified=true");

        // Get all the employeeAddressesList where isdeleted is null
        defaultEmployeeAddressesShouldNotBeFound("isdeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeAddressesByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeAddressesRepository.saveAndFlush(employeeAddresses);

        // Get all the employeeAddressesList where createdat equals to DEFAULT_CREATEDAT
        defaultEmployeeAddressesShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the employeeAddressesList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeAddressesShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeAddressesByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeAddressesRepository.saveAndFlush(employeeAddresses);

        // Get all the employeeAddressesList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultEmployeeAddressesShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the employeeAddressesList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeAddressesShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeAddressesByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeAddressesRepository.saveAndFlush(employeeAddresses);

        // Get all the employeeAddressesList where createdat is not null
        defaultEmployeeAddressesShouldBeFound("createdat.specified=true");

        // Get all the employeeAddressesList where createdat is null
        defaultEmployeeAddressesShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeAddressesByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeAddressesRepository.saveAndFlush(employeeAddresses);

        // Get all the employeeAddressesList where updatedat equals to DEFAULT_UPDATEDAT
        defaultEmployeeAddressesShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the employeeAddressesList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeAddressesShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeAddressesByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeAddressesRepository.saveAndFlush(employeeAddresses);

        // Get all the employeeAddressesList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultEmployeeAddressesShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the employeeAddressesList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeAddressesShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeAddressesByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeAddressesRepository.saveAndFlush(employeeAddresses);

        // Get all the employeeAddressesList where updatedat is not null
        defaultEmployeeAddressesShouldBeFound("updatedat.specified=true");

        // Get all the employeeAddressesList where updatedat is null
        defaultEmployeeAddressesShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeAddressesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeAddressesRepository.saveAndFlush(employeeAddresses);

        // Get all the employeeAddressesList where type equals to DEFAULT_TYPE
        defaultEmployeeAddressesShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the employeeAddressesList where type equals to UPDATED_TYPE
        defaultEmployeeAddressesShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllEmployeeAddressesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        employeeAddressesRepository.saveAndFlush(employeeAddresses);

        // Get all the employeeAddressesList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultEmployeeAddressesShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the employeeAddressesList where type equals to UPDATED_TYPE
        defaultEmployeeAddressesShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllEmployeeAddressesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeAddressesRepository.saveAndFlush(employeeAddresses);

        // Get all the employeeAddressesList where type is not null
        defaultEmployeeAddressesShouldBeFound("type.specified=true");

        // Get all the employeeAddressesList where type is null
        defaultEmployeeAddressesShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeAddressesByTypeContainsSomething() throws Exception {
        // Initialize the database
        employeeAddressesRepository.saveAndFlush(employeeAddresses);

        // Get all the employeeAddressesList where type contains DEFAULT_TYPE
        defaultEmployeeAddressesShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the employeeAddressesList where type contains UPDATED_TYPE
        defaultEmployeeAddressesShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllEmployeeAddressesByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        employeeAddressesRepository.saveAndFlush(employeeAddresses);

        // Get all the employeeAddressesList where type does not contain DEFAULT_TYPE
        defaultEmployeeAddressesShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the employeeAddressesList where type does not contain UPDATED_TYPE
        defaultEmployeeAddressesShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllEmployeeAddressesByAddressIsEqualToSomething() throws Exception {
        Addresses address;
        if (TestUtil.findAll(em, Addresses.class).isEmpty()) {
            employeeAddressesRepository.saveAndFlush(employeeAddresses);
            address = AddressesResourceIT.createEntity(em);
        } else {
            address = TestUtil.findAll(em, Addresses.class).get(0);
        }
        em.persist(address);
        em.flush();
        employeeAddresses.setAddress(address);
        employeeAddressesRepository.saveAndFlush(employeeAddresses);
        Long addressId = address.getId();

        // Get all the employeeAddressesList where address equals to addressId
        defaultEmployeeAddressesShouldBeFound("addressId.equals=" + addressId);

        // Get all the employeeAddressesList where address equals to (addressId + 1)
        defaultEmployeeAddressesShouldNotBeFound("addressId.equals=" + (addressId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeeAddressesByEmployeeIsEqualToSomething() throws Exception {
        Employees employee;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employeeAddressesRepository.saveAndFlush(employeeAddresses);
            employee = EmployeesResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employee);
        em.flush();
        employeeAddresses.setEmployee(employee);
        employeeAddressesRepository.saveAndFlush(employeeAddresses);
        Long employeeId = employee.getId();

        // Get all the employeeAddressesList where employee equals to employeeId
        defaultEmployeeAddressesShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the employeeAddressesList where employee equals to (employeeId + 1)
        defaultEmployeeAddressesShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeAddressesShouldBeFound(String filter) throws Exception {
        restEmployeeAddressesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeAddresses.getId().intValue())))
            .andExpect(jsonPath("$.[*].isdeleted").value(hasItem(DEFAULT_ISDELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));

        // Check, that the count call also returns 1
        restEmployeeAddressesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeAddressesShouldNotBeFound(String filter) throws Exception {
        restEmployeeAddressesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeAddressesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployeeAddresses() throws Exception {
        // Get the employeeAddresses
        restEmployeeAddressesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmployeeAddresses() throws Exception {
        // Initialize the database
        employeeAddressesRepository.saveAndFlush(employeeAddresses);

        int databaseSizeBeforeUpdate = employeeAddressesRepository.findAll().size();

        // Update the employeeAddresses
        EmployeeAddresses updatedEmployeeAddresses = employeeAddressesRepository.findById(employeeAddresses.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeAddresses are not directly saved in db
        em.detach(updatedEmployeeAddresses);
        updatedEmployeeAddresses.isdeleted(UPDATED_ISDELETED).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT).type(UPDATED_TYPE);

        restEmployeeAddressesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmployeeAddresses.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeAddresses))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeAddresses in the database
        List<EmployeeAddresses> employeeAddressesList = employeeAddressesRepository.findAll();
        assertThat(employeeAddressesList).hasSize(databaseSizeBeforeUpdate);
        EmployeeAddresses testEmployeeAddresses = employeeAddressesList.get(employeeAddressesList.size() - 1);
        assertThat(testEmployeeAddresses.getIsdeleted()).isEqualTo(UPDATED_ISDELETED);
        assertThat(testEmployeeAddresses.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeAddresses.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testEmployeeAddresses.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingEmployeeAddresses() throws Exception {
        int databaseSizeBeforeUpdate = employeeAddressesRepository.findAll().size();
        employeeAddresses.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeAddressesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeAddresses.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeAddresses))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeAddresses in the database
        List<EmployeeAddresses> employeeAddressesList = employeeAddressesRepository.findAll();
        assertThat(employeeAddressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployeeAddresses() throws Exception {
        int databaseSizeBeforeUpdate = employeeAddressesRepository.findAll().size();
        employeeAddresses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeAddressesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeAddresses))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeAddresses in the database
        List<EmployeeAddresses> employeeAddressesList = employeeAddressesRepository.findAll();
        assertThat(employeeAddressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployeeAddresses() throws Exception {
        int databaseSizeBeforeUpdate = employeeAddressesRepository.findAll().size();
        employeeAddresses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeAddressesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeAddresses))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeAddresses in the database
        List<EmployeeAddresses> employeeAddressesList = employeeAddressesRepository.findAll();
        assertThat(employeeAddressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeAddressesWithPatch() throws Exception {
        // Initialize the database
        employeeAddressesRepository.saveAndFlush(employeeAddresses);

        int databaseSizeBeforeUpdate = employeeAddressesRepository.findAll().size();

        // Update the employeeAddresses using partial update
        EmployeeAddresses partialUpdatedEmployeeAddresses = new EmployeeAddresses();
        partialUpdatedEmployeeAddresses.setId(employeeAddresses.getId());

        partialUpdatedEmployeeAddresses.createdat(UPDATED_CREATEDAT);

        restEmployeeAddressesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeAddresses.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeAddresses))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeAddresses in the database
        List<EmployeeAddresses> employeeAddressesList = employeeAddressesRepository.findAll();
        assertThat(employeeAddressesList).hasSize(databaseSizeBeforeUpdate);
        EmployeeAddresses testEmployeeAddresses = employeeAddressesList.get(employeeAddressesList.size() - 1);
        assertThat(testEmployeeAddresses.getIsdeleted()).isEqualTo(DEFAULT_ISDELETED);
        assertThat(testEmployeeAddresses.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeAddresses.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testEmployeeAddresses.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateEmployeeAddressesWithPatch() throws Exception {
        // Initialize the database
        employeeAddressesRepository.saveAndFlush(employeeAddresses);

        int databaseSizeBeforeUpdate = employeeAddressesRepository.findAll().size();

        // Update the employeeAddresses using partial update
        EmployeeAddresses partialUpdatedEmployeeAddresses = new EmployeeAddresses();
        partialUpdatedEmployeeAddresses.setId(employeeAddresses.getId());

        partialUpdatedEmployeeAddresses
            .isdeleted(UPDATED_ISDELETED)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .type(UPDATED_TYPE);

        restEmployeeAddressesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeAddresses.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeAddresses))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeAddresses in the database
        List<EmployeeAddresses> employeeAddressesList = employeeAddressesRepository.findAll();
        assertThat(employeeAddressesList).hasSize(databaseSizeBeforeUpdate);
        EmployeeAddresses testEmployeeAddresses = employeeAddressesList.get(employeeAddressesList.size() - 1);
        assertThat(testEmployeeAddresses.getIsdeleted()).isEqualTo(UPDATED_ISDELETED);
        assertThat(testEmployeeAddresses.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeAddresses.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testEmployeeAddresses.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingEmployeeAddresses() throws Exception {
        int databaseSizeBeforeUpdate = employeeAddressesRepository.findAll().size();
        employeeAddresses.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeAddressesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employeeAddresses.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeAddresses))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeAddresses in the database
        List<EmployeeAddresses> employeeAddressesList = employeeAddressesRepository.findAll();
        assertThat(employeeAddressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployeeAddresses() throws Exception {
        int databaseSizeBeforeUpdate = employeeAddressesRepository.findAll().size();
        employeeAddresses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeAddressesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeAddresses))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeAddresses in the database
        List<EmployeeAddresses> employeeAddressesList = employeeAddressesRepository.findAll();
        assertThat(employeeAddressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployeeAddresses() throws Exception {
        int databaseSizeBeforeUpdate = employeeAddressesRepository.findAll().size();
        employeeAddresses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeAddressesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeAddresses))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeAddresses in the database
        List<EmployeeAddresses> employeeAddressesList = employeeAddressesRepository.findAll();
        assertThat(employeeAddressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployeeAddresses() throws Exception {
        // Initialize the database
        employeeAddressesRepository.saveAndFlush(employeeAddresses);

        int databaseSizeBeforeDelete = employeeAddressesRepository.findAll().size();

        // Delete the employeeAddresses
        restEmployeeAddressesMockMvc
            .perform(delete(ENTITY_API_URL_ID, employeeAddresses.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeAddresses> employeeAddressesList = employeeAddressesRepository.findAll();
        assertThat(employeeAddressesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
