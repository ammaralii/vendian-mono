package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.EmployeeContacts;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.repository.EmployeeContactsRepository;
import com.venturedive.vendian_mono.service.criteria.EmployeeContactsCriteria;
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
 * Integration tests for the {@link EmployeeContactsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeeContactsResourceIT {

    private static final byte[] DEFAULT_NUMBER = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_NUMBER = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_NUMBER_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_NUMBER_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_TYPE = "AAAAAA";
    private static final String UPDATED_TYPE = "BBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/employee-contacts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeeContactsRepository employeeContactsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeContactsMockMvc;

    private EmployeeContacts employeeContacts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeContacts createEntity(EntityManager em) {
        EmployeeContacts employeeContacts = new EmployeeContacts()
            .number(DEFAULT_NUMBER)
            .numberContentType(DEFAULT_NUMBER_CONTENT_TYPE)
            .type(DEFAULT_TYPE)
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
        employeeContacts.setEmployee(employees);
        return employeeContacts;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeContacts createUpdatedEntity(EntityManager em) {
        EmployeeContacts employeeContacts = new EmployeeContacts()
            .number(UPDATED_NUMBER)
            .numberContentType(UPDATED_NUMBER_CONTENT_TYPE)
            .type(UPDATED_TYPE)
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
        employeeContacts.setEmployee(employees);
        return employeeContacts;
    }

    @BeforeEach
    public void initTest() {
        employeeContacts = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployeeContacts() throws Exception {
        int databaseSizeBeforeCreate = employeeContactsRepository.findAll().size();
        // Create the EmployeeContacts
        restEmployeeContactsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeContacts))
            )
            .andExpect(status().isCreated());

        // Validate the EmployeeContacts in the database
        List<EmployeeContacts> employeeContactsList = employeeContactsRepository.findAll();
        assertThat(employeeContactsList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeContacts testEmployeeContacts = employeeContactsList.get(employeeContactsList.size() - 1);
        assertThat(testEmployeeContacts.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testEmployeeContacts.getNumberContentType()).isEqualTo(DEFAULT_NUMBER_CONTENT_TYPE);
        assertThat(testEmployeeContacts.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testEmployeeContacts.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testEmployeeContacts.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createEmployeeContactsWithExistingId() throws Exception {
        // Create the EmployeeContacts with an existing ID
        employeeContacts.setId(1L);

        int databaseSizeBeforeCreate = employeeContactsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeContactsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeContacts))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeContacts in the database
        List<EmployeeContacts> employeeContactsList = employeeContactsRepository.findAll();
        assertThat(employeeContactsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmployeeContacts() throws Exception {
        // Initialize the database
        employeeContactsRepository.saveAndFlush(employeeContacts);

        // Get all the employeeContactsList
        restEmployeeContactsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeContacts.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberContentType").value(hasItem(DEFAULT_NUMBER_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].number").value(hasItem(Base64Utils.encodeToString(DEFAULT_NUMBER))))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getEmployeeContacts() throws Exception {
        // Initialize the database
        employeeContactsRepository.saveAndFlush(employeeContacts);

        // Get the employeeContacts
        restEmployeeContactsMockMvc
            .perform(get(ENTITY_API_URL_ID, employeeContacts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeContacts.getId().intValue()))
            .andExpect(jsonPath("$.numberContentType").value(DEFAULT_NUMBER_CONTENT_TYPE))
            .andExpect(jsonPath("$.number").value(Base64Utils.encodeToString(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getEmployeeContactsByIdFiltering() throws Exception {
        // Initialize the database
        employeeContactsRepository.saveAndFlush(employeeContacts);

        Long id = employeeContacts.getId();

        defaultEmployeeContactsShouldBeFound("id.equals=" + id);
        defaultEmployeeContactsShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeContactsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeContactsShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeContactsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeContactsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployeeContactsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeContactsRepository.saveAndFlush(employeeContacts);

        // Get all the employeeContactsList where type equals to DEFAULT_TYPE
        defaultEmployeeContactsShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the employeeContactsList where type equals to UPDATED_TYPE
        defaultEmployeeContactsShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllEmployeeContactsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        employeeContactsRepository.saveAndFlush(employeeContacts);

        // Get all the employeeContactsList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultEmployeeContactsShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the employeeContactsList where type equals to UPDATED_TYPE
        defaultEmployeeContactsShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllEmployeeContactsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeContactsRepository.saveAndFlush(employeeContacts);

        // Get all the employeeContactsList where type is not null
        defaultEmployeeContactsShouldBeFound("type.specified=true");

        // Get all the employeeContactsList where type is null
        defaultEmployeeContactsShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeContactsByTypeContainsSomething() throws Exception {
        // Initialize the database
        employeeContactsRepository.saveAndFlush(employeeContacts);

        // Get all the employeeContactsList where type contains DEFAULT_TYPE
        defaultEmployeeContactsShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the employeeContactsList where type contains UPDATED_TYPE
        defaultEmployeeContactsShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllEmployeeContactsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        employeeContactsRepository.saveAndFlush(employeeContacts);

        // Get all the employeeContactsList where type does not contain DEFAULT_TYPE
        defaultEmployeeContactsShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the employeeContactsList where type does not contain UPDATED_TYPE
        defaultEmployeeContactsShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllEmployeeContactsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeContactsRepository.saveAndFlush(employeeContacts);

        // Get all the employeeContactsList where createdat equals to DEFAULT_CREATEDAT
        defaultEmployeeContactsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the employeeContactsList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeContactsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeContactsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeContactsRepository.saveAndFlush(employeeContacts);

        // Get all the employeeContactsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultEmployeeContactsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the employeeContactsList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeContactsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeContactsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeContactsRepository.saveAndFlush(employeeContacts);

        // Get all the employeeContactsList where createdat is not null
        defaultEmployeeContactsShouldBeFound("createdat.specified=true");

        // Get all the employeeContactsList where createdat is null
        defaultEmployeeContactsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeContactsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeContactsRepository.saveAndFlush(employeeContacts);

        // Get all the employeeContactsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultEmployeeContactsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the employeeContactsList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeContactsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeContactsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeContactsRepository.saveAndFlush(employeeContacts);

        // Get all the employeeContactsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultEmployeeContactsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the employeeContactsList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeContactsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeContactsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeContactsRepository.saveAndFlush(employeeContacts);

        // Get all the employeeContactsList where updatedat is not null
        defaultEmployeeContactsShouldBeFound("updatedat.specified=true");

        // Get all the employeeContactsList where updatedat is null
        defaultEmployeeContactsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeContactsByEmployeeIsEqualToSomething() throws Exception {
        Employees employee;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employeeContactsRepository.saveAndFlush(employeeContacts);
            employee = EmployeesResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employee);
        em.flush();
        employeeContacts.setEmployee(employee);
        employeeContactsRepository.saveAndFlush(employeeContacts);
        Long employeeId = employee.getId();

        // Get all the employeeContactsList where employee equals to employeeId
        defaultEmployeeContactsShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the employeeContactsList where employee equals to (employeeId + 1)
        defaultEmployeeContactsShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeContactsShouldBeFound(String filter) throws Exception {
        restEmployeeContactsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeContacts.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberContentType").value(hasItem(DEFAULT_NUMBER_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].number").value(hasItem(Base64Utils.encodeToString(DEFAULT_NUMBER))))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restEmployeeContactsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeContactsShouldNotBeFound(String filter) throws Exception {
        restEmployeeContactsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeContactsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployeeContacts() throws Exception {
        // Get the employeeContacts
        restEmployeeContactsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmployeeContacts() throws Exception {
        // Initialize the database
        employeeContactsRepository.saveAndFlush(employeeContacts);

        int databaseSizeBeforeUpdate = employeeContactsRepository.findAll().size();

        // Update the employeeContacts
        EmployeeContacts updatedEmployeeContacts = employeeContactsRepository.findById(employeeContacts.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeContacts are not directly saved in db
        em.detach(updatedEmployeeContacts);
        updatedEmployeeContacts
            .number(UPDATED_NUMBER)
            .numberContentType(UPDATED_NUMBER_CONTENT_TYPE)
            .type(UPDATED_TYPE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restEmployeeContactsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmployeeContacts.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeContacts))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeContacts in the database
        List<EmployeeContacts> employeeContactsList = employeeContactsRepository.findAll();
        assertThat(employeeContactsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeContacts testEmployeeContacts = employeeContactsList.get(employeeContactsList.size() - 1);
        assertThat(testEmployeeContacts.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testEmployeeContacts.getNumberContentType()).isEqualTo(UPDATED_NUMBER_CONTENT_TYPE);
        assertThat(testEmployeeContacts.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEmployeeContacts.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeContacts.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingEmployeeContacts() throws Exception {
        int databaseSizeBeforeUpdate = employeeContactsRepository.findAll().size();
        employeeContacts.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeContactsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeContacts.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeContacts))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeContacts in the database
        List<EmployeeContacts> employeeContactsList = employeeContactsRepository.findAll();
        assertThat(employeeContactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployeeContacts() throws Exception {
        int databaseSizeBeforeUpdate = employeeContactsRepository.findAll().size();
        employeeContacts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeContactsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeContacts))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeContacts in the database
        List<EmployeeContacts> employeeContactsList = employeeContactsRepository.findAll();
        assertThat(employeeContactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployeeContacts() throws Exception {
        int databaseSizeBeforeUpdate = employeeContactsRepository.findAll().size();
        employeeContacts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeContactsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeContacts))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeContacts in the database
        List<EmployeeContacts> employeeContactsList = employeeContactsRepository.findAll();
        assertThat(employeeContactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeContactsWithPatch() throws Exception {
        // Initialize the database
        employeeContactsRepository.saveAndFlush(employeeContacts);

        int databaseSizeBeforeUpdate = employeeContactsRepository.findAll().size();

        // Update the employeeContacts using partial update
        EmployeeContacts partialUpdatedEmployeeContacts = new EmployeeContacts();
        partialUpdatedEmployeeContacts.setId(employeeContacts.getId());

        partialUpdatedEmployeeContacts.number(UPDATED_NUMBER).numberContentType(UPDATED_NUMBER_CONTENT_TYPE).createdat(UPDATED_CREATEDAT);

        restEmployeeContactsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeContacts.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeContacts))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeContacts in the database
        List<EmployeeContacts> employeeContactsList = employeeContactsRepository.findAll();
        assertThat(employeeContactsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeContacts testEmployeeContacts = employeeContactsList.get(employeeContactsList.size() - 1);
        assertThat(testEmployeeContacts.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testEmployeeContacts.getNumberContentType()).isEqualTo(UPDATED_NUMBER_CONTENT_TYPE);
        assertThat(testEmployeeContacts.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testEmployeeContacts.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeContacts.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateEmployeeContactsWithPatch() throws Exception {
        // Initialize the database
        employeeContactsRepository.saveAndFlush(employeeContacts);

        int databaseSizeBeforeUpdate = employeeContactsRepository.findAll().size();

        // Update the employeeContacts using partial update
        EmployeeContacts partialUpdatedEmployeeContacts = new EmployeeContacts();
        partialUpdatedEmployeeContacts.setId(employeeContacts.getId());

        partialUpdatedEmployeeContacts
            .number(UPDATED_NUMBER)
            .numberContentType(UPDATED_NUMBER_CONTENT_TYPE)
            .type(UPDATED_TYPE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restEmployeeContactsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeContacts.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeContacts))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeContacts in the database
        List<EmployeeContacts> employeeContactsList = employeeContactsRepository.findAll();
        assertThat(employeeContactsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeContacts testEmployeeContacts = employeeContactsList.get(employeeContactsList.size() - 1);
        assertThat(testEmployeeContacts.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testEmployeeContacts.getNumberContentType()).isEqualTo(UPDATED_NUMBER_CONTENT_TYPE);
        assertThat(testEmployeeContacts.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEmployeeContacts.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeContacts.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingEmployeeContacts() throws Exception {
        int databaseSizeBeforeUpdate = employeeContactsRepository.findAll().size();
        employeeContacts.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeContactsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employeeContacts.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeContacts))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeContacts in the database
        List<EmployeeContacts> employeeContactsList = employeeContactsRepository.findAll();
        assertThat(employeeContactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployeeContacts() throws Exception {
        int databaseSizeBeforeUpdate = employeeContactsRepository.findAll().size();
        employeeContacts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeContactsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeContacts))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeContacts in the database
        List<EmployeeContacts> employeeContactsList = employeeContactsRepository.findAll();
        assertThat(employeeContactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployeeContacts() throws Exception {
        int databaseSizeBeforeUpdate = employeeContactsRepository.findAll().size();
        employeeContacts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeContactsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeContacts))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeContacts in the database
        List<EmployeeContacts> employeeContactsList = employeeContactsRepository.findAll();
        assertThat(employeeContactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployeeContacts() throws Exception {
        // Initialize the database
        employeeContactsRepository.saveAndFlush(employeeContacts);

        int databaseSizeBeforeDelete = employeeContactsRepository.findAll().size();

        // Delete the employeeContacts
        restEmployeeContactsMockMvc
            .perform(delete(ENTITY_API_URL_ID, employeeContacts.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeContacts> employeeContactsList = employeeContactsRepository.findAll();
        assertThat(employeeContactsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
