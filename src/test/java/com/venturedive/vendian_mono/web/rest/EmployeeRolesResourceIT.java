package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.EmployeeRoles;
import com.venturedive.vendian_mono.domain.Roles;
import com.venturedive.vendian_mono.repository.EmployeeRolesRepository;
import com.venturedive.vendian_mono.service.criteria.EmployeeRolesCriteria;
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
 * Integration tests for the {@link EmployeeRolesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeeRolesResourceIT {

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_EMPLOYEEID = 1;
    private static final Integer UPDATED_EMPLOYEEID = 2;
    private static final Integer SMALLER_EMPLOYEEID = 1 - 1;

    private static final String ENTITY_API_URL = "/api/employee-roles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeeRolesRepository employeeRolesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeRolesMockMvc;

    private EmployeeRoles employeeRoles;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeRoles createEntity(EntityManager em) {
        EmployeeRoles employeeRoles = new EmployeeRoles()
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT)
            .employeeid(DEFAULT_EMPLOYEEID);
        return employeeRoles;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeRoles createUpdatedEntity(EntityManager em) {
        EmployeeRoles employeeRoles = new EmployeeRoles()
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .employeeid(UPDATED_EMPLOYEEID);
        return employeeRoles;
    }

    @BeforeEach
    public void initTest() {
        employeeRoles = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployeeRoles() throws Exception {
        int databaseSizeBeforeCreate = employeeRolesRepository.findAll().size();
        // Create the EmployeeRoles
        restEmployeeRolesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeRoles))
            )
            .andExpect(status().isCreated());

        // Validate the EmployeeRoles in the database
        List<EmployeeRoles> employeeRolesList = employeeRolesRepository.findAll();
        assertThat(employeeRolesList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeRoles testEmployeeRoles = employeeRolesList.get(employeeRolesList.size() - 1);
        assertThat(testEmployeeRoles.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testEmployeeRoles.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testEmployeeRoles.getEmployeeid()).isEqualTo(DEFAULT_EMPLOYEEID);
    }

    @Test
    @Transactional
    void createEmployeeRolesWithExistingId() throws Exception {
        // Create the EmployeeRoles with an existing ID
        employeeRoles.setId(1L);

        int databaseSizeBeforeCreate = employeeRolesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeRolesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeRoles))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeRoles in the database
        List<EmployeeRoles> employeeRolesList = employeeRolesRepository.findAll();
        assertThat(employeeRolesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRolesRepository.findAll().size();
        // set the field null
        employeeRoles.setCreatedat(null);

        // Create the EmployeeRoles, which fails.

        restEmployeeRolesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeRoles))
            )
            .andExpect(status().isBadRequest());

        List<EmployeeRoles> employeeRolesList = employeeRolesRepository.findAll();
        assertThat(employeeRolesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRolesRepository.findAll().size();
        // set the field null
        employeeRoles.setUpdatedat(null);

        // Create the EmployeeRoles, which fails.

        restEmployeeRolesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeRoles))
            )
            .andExpect(status().isBadRequest());

        List<EmployeeRoles> employeeRolesList = employeeRolesRepository.findAll();
        assertThat(employeeRolesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmployeeRoles() throws Exception {
        // Initialize the database
        employeeRolesRepository.saveAndFlush(employeeRoles);

        // Get all the employeeRolesList
        restEmployeeRolesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeRoles.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].employeeid").value(hasItem(DEFAULT_EMPLOYEEID)));
    }

    @Test
    @Transactional
    void getEmployeeRoles() throws Exception {
        // Initialize the database
        employeeRolesRepository.saveAndFlush(employeeRoles);

        // Get the employeeRoles
        restEmployeeRolesMockMvc
            .perform(get(ENTITY_API_URL_ID, employeeRoles.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeRoles.getId().intValue()))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()))
            .andExpect(jsonPath("$.employeeid").value(DEFAULT_EMPLOYEEID));
    }

    @Test
    @Transactional
    void getEmployeeRolesByIdFiltering() throws Exception {
        // Initialize the database
        employeeRolesRepository.saveAndFlush(employeeRoles);

        Long id = employeeRoles.getId();

        defaultEmployeeRolesShouldBeFound("id.equals=" + id);
        defaultEmployeeRolesShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeRolesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeRolesShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeRolesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeRolesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployeeRolesByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRolesRepository.saveAndFlush(employeeRoles);

        // Get all the employeeRolesList where createdat equals to DEFAULT_CREATEDAT
        defaultEmployeeRolesShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the employeeRolesList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeRolesShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeRolesByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRolesRepository.saveAndFlush(employeeRoles);

        // Get all the employeeRolesList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultEmployeeRolesShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the employeeRolesList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeRolesShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeRolesByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRolesRepository.saveAndFlush(employeeRoles);

        // Get all the employeeRolesList where createdat is not null
        defaultEmployeeRolesShouldBeFound("createdat.specified=true");

        // Get all the employeeRolesList where createdat is null
        defaultEmployeeRolesShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeRolesByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRolesRepository.saveAndFlush(employeeRoles);

        // Get all the employeeRolesList where updatedat equals to DEFAULT_UPDATEDAT
        defaultEmployeeRolesShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the employeeRolesList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeRolesShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeRolesByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRolesRepository.saveAndFlush(employeeRoles);

        // Get all the employeeRolesList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultEmployeeRolesShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the employeeRolesList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeRolesShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeRolesByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRolesRepository.saveAndFlush(employeeRoles);

        // Get all the employeeRolesList where updatedat is not null
        defaultEmployeeRolesShouldBeFound("updatedat.specified=true");

        // Get all the employeeRolesList where updatedat is null
        defaultEmployeeRolesShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeRolesByEmployeeidIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRolesRepository.saveAndFlush(employeeRoles);

        // Get all the employeeRolesList where employeeid equals to DEFAULT_EMPLOYEEID
        defaultEmployeeRolesShouldBeFound("employeeid.equals=" + DEFAULT_EMPLOYEEID);

        // Get all the employeeRolesList where employeeid equals to UPDATED_EMPLOYEEID
        defaultEmployeeRolesShouldNotBeFound("employeeid.equals=" + UPDATED_EMPLOYEEID);
    }

    @Test
    @Transactional
    void getAllEmployeeRolesByEmployeeidIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRolesRepository.saveAndFlush(employeeRoles);

        // Get all the employeeRolesList where employeeid in DEFAULT_EMPLOYEEID or UPDATED_EMPLOYEEID
        defaultEmployeeRolesShouldBeFound("employeeid.in=" + DEFAULT_EMPLOYEEID + "," + UPDATED_EMPLOYEEID);

        // Get all the employeeRolesList where employeeid equals to UPDATED_EMPLOYEEID
        defaultEmployeeRolesShouldNotBeFound("employeeid.in=" + UPDATED_EMPLOYEEID);
    }

    @Test
    @Transactional
    void getAllEmployeeRolesByEmployeeidIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRolesRepository.saveAndFlush(employeeRoles);

        // Get all the employeeRolesList where employeeid is not null
        defaultEmployeeRolesShouldBeFound("employeeid.specified=true");

        // Get all the employeeRolesList where employeeid is null
        defaultEmployeeRolesShouldNotBeFound("employeeid.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeRolesByEmployeeidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRolesRepository.saveAndFlush(employeeRoles);

        // Get all the employeeRolesList where employeeid is greater than or equal to DEFAULT_EMPLOYEEID
        defaultEmployeeRolesShouldBeFound("employeeid.greaterThanOrEqual=" + DEFAULT_EMPLOYEEID);

        // Get all the employeeRolesList where employeeid is greater than or equal to UPDATED_EMPLOYEEID
        defaultEmployeeRolesShouldNotBeFound("employeeid.greaterThanOrEqual=" + UPDATED_EMPLOYEEID);
    }

    @Test
    @Transactional
    void getAllEmployeeRolesByEmployeeidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRolesRepository.saveAndFlush(employeeRoles);

        // Get all the employeeRolesList where employeeid is less than or equal to DEFAULT_EMPLOYEEID
        defaultEmployeeRolesShouldBeFound("employeeid.lessThanOrEqual=" + DEFAULT_EMPLOYEEID);

        // Get all the employeeRolesList where employeeid is less than or equal to SMALLER_EMPLOYEEID
        defaultEmployeeRolesShouldNotBeFound("employeeid.lessThanOrEqual=" + SMALLER_EMPLOYEEID);
    }

    @Test
    @Transactional
    void getAllEmployeeRolesByEmployeeidIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRolesRepository.saveAndFlush(employeeRoles);

        // Get all the employeeRolesList where employeeid is less than DEFAULT_EMPLOYEEID
        defaultEmployeeRolesShouldNotBeFound("employeeid.lessThan=" + DEFAULT_EMPLOYEEID);

        // Get all the employeeRolesList where employeeid is less than UPDATED_EMPLOYEEID
        defaultEmployeeRolesShouldBeFound("employeeid.lessThan=" + UPDATED_EMPLOYEEID);
    }

    @Test
    @Transactional
    void getAllEmployeeRolesByEmployeeidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRolesRepository.saveAndFlush(employeeRoles);

        // Get all the employeeRolesList where employeeid is greater than DEFAULT_EMPLOYEEID
        defaultEmployeeRolesShouldNotBeFound("employeeid.greaterThan=" + DEFAULT_EMPLOYEEID);

        // Get all the employeeRolesList where employeeid is greater than SMALLER_EMPLOYEEID
        defaultEmployeeRolesShouldBeFound("employeeid.greaterThan=" + SMALLER_EMPLOYEEID);
    }

    @Test
    @Transactional
    void getAllEmployeeRolesByRoleIsEqualToSomething() throws Exception {
        Roles role;
        if (TestUtil.findAll(em, Roles.class).isEmpty()) {
            employeeRolesRepository.saveAndFlush(employeeRoles);
            role = RolesResourceIT.createEntity(em);
        } else {
            role = TestUtil.findAll(em, Roles.class).get(0);
        }
        em.persist(role);
        em.flush();
        employeeRoles.setRole(role);
        employeeRolesRepository.saveAndFlush(employeeRoles);
        Long roleId = role.getId();

        // Get all the employeeRolesList where role equals to roleId
        defaultEmployeeRolesShouldBeFound("roleId.equals=" + roleId);

        // Get all the employeeRolesList where role equals to (roleId + 1)
        defaultEmployeeRolesShouldNotBeFound("roleId.equals=" + (roleId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeRolesShouldBeFound(String filter) throws Exception {
        restEmployeeRolesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeRoles.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].employeeid").value(hasItem(DEFAULT_EMPLOYEEID)));

        // Check, that the count call also returns 1
        restEmployeeRolesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeRolesShouldNotBeFound(String filter) throws Exception {
        restEmployeeRolesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeRolesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployeeRoles() throws Exception {
        // Get the employeeRoles
        restEmployeeRolesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmployeeRoles() throws Exception {
        // Initialize the database
        employeeRolesRepository.saveAndFlush(employeeRoles);

        int databaseSizeBeforeUpdate = employeeRolesRepository.findAll().size();

        // Update the employeeRoles
        EmployeeRoles updatedEmployeeRoles = employeeRolesRepository.findById(employeeRoles.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeRoles are not directly saved in db
        em.detach(updatedEmployeeRoles);
        updatedEmployeeRoles.createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT).employeeid(UPDATED_EMPLOYEEID);

        restEmployeeRolesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmployeeRoles.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeRoles))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeRoles in the database
        List<EmployeeRoles> employeeRolesList = employeeRolesRepository.findAll();
        assertThat(employeeRolesList).hasSize(databaseSizeBeforeUpdate);
        EmployeeRoles testEmployeeRoles = employeeRolesList.get(employeeRolesList.size() - 1);
        assertThat(testEmployeeRoles.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeRoles.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testEmployeeRoles.getEmployeeid()).isEqualTo(UPDATED_EMPLOYEEID);
    }

    @Test
    @Transactional
    void putNonExistingEmployeeRoles() throws Exception {
        int databaseSizeBeforeUpdate = employeeRolesRepository.findAll().size();
        employeeRoles.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeRolesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeRoles.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeRoles))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeRoles in the database
        List<EmployeeRoles> employeeRolesList = employeeRolesRepository.findAll();
        assertThat(employeeRolesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployeeRoles() throws Exception {
        int databaseSizeBeforeUpdate = employeeRolesRepository.findAll().size();
        employeeRoles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeRolesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeRoles))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeRoles in the database
        List<EmployeeRoles> employeeRolesList = employeeRolesRepository.findAll();
        assertThat(employeeRolesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployeeRoles() throws Exception {
        int databaseSizeBeforeUpdate = employeeRolesRepository.findAll().size();
        employeeRoles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeRolesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeRoles))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeRoles in the database
        List<EmployeeRoles> employeeRolesList = employeeRolesRepository.findAll();
        assertThat(employeeRolesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeRolesWithPatch() throws Exception {
        // Initialize the database
        employeeRolesRepository.saveAndFlush(employeeRoles);

        int databaseSizeBeforeUpdate = employeeRolesRepository.findAll().size();

        // Update the employeeRoles using partial update
        EmployeeRoles partialUpdatedEmployeeRoles = new EmployeeRoles();
        partialUpdatedEmployeeRoles.setId(employeeRoles.getId());

        partialUpdatedEmployeeRoles.createdat(UPDATED_CREATEDAT).employeeid(UPDATED_EMPLOYEEID);

        restEmployeeRolesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeRoles.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeRoles))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeRoles in the database
        List<EmployeeRoles> employeeRolesList = employeeRolesRepository.findAll();
        assertThat(employeeRolesList).hasSize(databaseSizeBeforeUpdate);
        EmployeeRoles testEmployeeRoles = employeeRolesList.get(employeeRolesList.size() - 1);
        assertThat(testEmployeeRoles.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeRoles.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testEmployeeRoles.getEmployeeid()).isEqualTo(UPDATED_EMPLOYEEID);
    }

    @Test
    @Transactional
    void fullUpdateEmployeeRolesWithPatch() throws Exception {
        // Initialize the database
        employeeRolesRepository.saveAndFlush(employeeRoles);

        int databaseSizeBeforeUpdate = employeeRolesRepository.findAll().size();

        // Update the employeeRoles using partial update
        EmployeeRoles partialUpdatedEmployeeRoles = new EmployeeRoles();
        partialUpdatedEmployeeRoles.setId(employeeRoles.getId());

        partialUpdatedEmployeeRoles.createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT).employeeid(UPDATED_EMPLOYEEID);

        restEmployeeRolesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeRoles.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeRoles))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeRoles in the database
        List<EmployeeRoles> employeeRolesList = employeeRolesRepository.findAll();
        assertThat(employeeRolesList).hasSize(databaseSizeBeforeUpdate);
        EmployeeRoles testEmployeeRoles = employeeRolesList.get(employeeRolesList.size() - 1);
        assertThat(testEmployeeRoles.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeRoles.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testEmployeeRoles.getEmployeeid()).isEqualTo(UPDATED_EMPLOYEEID);
    }

    @Test
    @Transactional
    void patchNonExistingEmployeeRoles() throws Exception {
        int databaseSizeBeforeUpdate = employeeRolesRepository.findAll().size();
        employeeRoles.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeRolesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employeeRoles.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeRoles))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeRoles in the database
        List<EmployeeRoles> employeeRolesList = employeeRolesRepository.findAll();
        assertThat(employeeRolesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployeeRoles() throws Exception {
        int databaseSizeBeforeUpdate = employeeRolesRepository.findAll().size();
        employeeRoles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeRolesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeRoles))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeRoles in the database
        List<EmployeeRoles> employeeRolesList = employeeRolesRepository.findAll();
        assertThat(employeeRolesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployeeRoles() throws Exception {
        int databaseSizeBeforeUpdate = employeeRolesRepository.findAll().size();
        employeeRoles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeRolesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeRoles))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeRoles in the database
        List<EmployeeRoles> employeeRolesList = employeeRolesRepository.findAll();
        assertThat(employeeRolesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployeeRoles() throws Exception {
        // Initialize the database
        employeeRolesRepository.saveAndFlush(employeeRoles);

        int databaseSizeBeforeDelete = employeeRolesRepository.findAll().size();

        // Delete the employeeRoles
        restEmployeeRolesMockMvc
            .perform(delete(ENTITY_API_URL_ID, employeeRoles.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeRoles> employeeRolesList = employeeRolesRepository.findAll();
        assertThat(employeeRolesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
