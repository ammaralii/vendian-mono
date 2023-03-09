package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.EmployeeProjectRoles;
import com.venturedive.vendian_mono.domain.EmployeeProjects;
import com.venturedive.vendian_mono.domain.ProjectRoles;
import com.venturedive.vendian_mono.repository.EmployeeProjectRolesRepository;
import com.venturedive.vendian_mono.service.criteria.EmployeeProjectRolesCriteria;
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
 * Integration tests for the {@link EmployeeProjectRolesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeeProjectRolesResourceIT {

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/employee-project-roles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeeProjectRolesRepository employeeProjectRolesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeProjectRolesMockMvc;

    private EmployeeProjectRoles employeeProjectRoles;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeProjectRoles createEntity(EntityManager em) {
        EmployeeProjectRoles employeeProjectRoles = new EmployeeProjectRoles()
            .status(DEFAULT_STATUS)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        return employeeProjectRoles;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeProjectRoles createUpdatedEntity(EntityManager em) {
        EmployeeProjectRoles employeeProjectRoles = new EmployeeProjectRoles()
            .status(UPDATED_STATUS)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        return employeeProjectRoles;
    }

    @BeforeEach
    public void initTest() {
        employeeProjectRoles = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployeeProjectRoles() throws Exception {
        int databaseSizeBeforeCreate = employeeProjectRolesRepository.findAll().size();
        // Create the EmployeeProjectRoles
        restEmployeeProjectRolesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeProjectRoles))
            )
            .andExpect(status().isCreated());

        // Validate the EmployeeProjectRoles in the database
        List<EmployeeProjectRoles> employeeProjectRolesList = employeeProjectRolesRepository.findAll();
        assertThat(employeeProjectRolesList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeProjectRoles testEmployeeProjectRoles = employeeProjectRolesList.get(employeeProjectRolesList.size() - 1);
        assertThat(testEmployeeProjectRoles.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testEmployeeProjectRoles.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testEmployeeProjectRoles.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createEmployeeProjectRolesWithExistingId() throws Exception {
        // Create the EmployeeProjectRoles with an existing ID
        employeeProjectRoles.setId(1L);

        int databaseSizeBeforeCreate = employeeProjectRolesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeProjectRolesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeProjectRoles))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeProjectRoles in the database
        List<EmployeeProjectRoles> employeeProjectRolesList = employeeProjectRolesRepository.findAll();
        assertThat(employeeProjectRolesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeProjectRolesRepository.findAll().size();
        // set the field null
        employeeProjectRoles.setCreatedat(null);

        // Create the EmployeeProjectRoles, which fails.

        restEmployeeProjectRolesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeProjectRoles))
            )
            .andExpect(status().isBadRequest());

        List<EmployeeProjectRoles> employeeProjectRolesList = employeeProjectRolesRepository.findAll();
        assertThat(employeeProjectRolesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeProjectRolesRepository.findAll().size();
        // set the field null
        employeeProjectRoles.setUpdatedat(null);

        // Create the EmployeeProjectRoles, which fails.

        restEmployeeProjectRolesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeProjectRoles))
            )
            .andExpect(status().isBadRequest());

        List<EmployeeProjectRoles> employeeProjectRolesList = employeeProjectRolesRepository.findAll();
        assertThat(employeeProjectRolesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRoles() throws Exception {
        // Initialize the database
        employeeProjectRolesRepository.saveAndFlush(employeeProjectRoles);

        // Get all the employeeProjectRolesList
        restEmployeeProjectRolesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeProjectRoles.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getEmployeeProjectRoles() throws Exception {
        // Initialize the database
        employeeProjectRolesRepository.saveAndFlush(employeeProjectRoles);

        // Get the employeeProjectRoles
        restEmployeeProjectRolesMockMvc
            .perform(get(ENTITY_API_URL_ID, employeeProjectRoles.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeProjectRoles.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getEmployeeProjectRolesByIdFiltering() throws Exception {
        // Initialize the database
        employeeProjectRolesRepository.saveAndFlush(employeeProjectRoles);

        Long id = employeeProjectRoles.getId();

        defaultEmployeeProjectRolesShouldBeFound("id.equals=" + id);
        defaultEmployeeProjectRolesShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeProjectRolesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeProjectRolesShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeProjectRolesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeProjectRolesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRolesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeProjectRolesRepository.saveAndFlush(employeeProjectRoles);

        // Get all the employeeProjectRolesList where status equals to DEFAULT_STATUS
        defaultEmployeeProjectRolesShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the employeeProjectRolesList where status equals to UPDATED_STATUS
        defaultEmployeeProjectRolesShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRolesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        employeeProjectRolesRepository.saveAndFlush(employeeProjectRoles);

        // Get all the employeeProjectRolesList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultEmployeeProjectRolesShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the employeeProjectRolesList where status equals to UPDATED_STATUS
        defaultEmployeeProjectRolesShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRolesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeProjectRolesRepository.saveAndFlush(employeeProjectRoles);

        // Get all the employeeProjectRolesList where status is not null
        defaultEmployeeProjectRolesShouldBeFound("status.specified=true");

        // Get all the employeeProjectRolesList where status is null
        defaultEmployeeProjectRolesShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRolesByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeProjectRolesRepository.saveAndFlush(employeeProjectRoles);

        // Get all the employeeProjectRolesList where createdat equals to DEFAULT_CREATEDAT
        defaultEmployeeProjectRolesShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the employeeProjectRolesList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeProjectRolesShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRolesByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeProjectRolesRepository.saveAndFlush(employeeProjectRoles);

        // Get all the employeeProjectRolesList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultEmployeeProjectRolesShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the employeeProjectRolesList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeProjectRolesShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRolesByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeProjectRolesRepository.saveAndFlush(employeeProjectRoles);

        // Get all the employeeProjectRolesList where createdat is not null
        defaultEmployeeProjectRolesShouldBeFound("createdat.specified=true");

        // Get all the employeeProjectRolesList where createdat is null
        defaultEmployeeProjectRolesShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRolesByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeProjectRolesRepository.saveAndFlush(employeeProjectRoles);

        // Get all the employeeProjectRolesList where updatedat equals to DEFAULT_UPDATEDAT
        defaultEmployeeProjectRolesShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the employeeProjectRolesList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeProjectRolesShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRolesByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeProjectRolesRepository.saveAndFlush(employeeProjectRoles);

        // Get all the employeeProjectRolesList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultEmployeeProjectRolesShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the employeeProjectRolesList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeProjectRolesShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRolesByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeProjectRolesRepository.saveAndFlush(employeeProjectRoles);

        // Get all the employeeProjectRolesList where updatedat is not null
        defaultEmployeeProjectRolesShouldBeFound("updatedat.specified=true");

        // Get all the employeeProjectRolesList where updatedat is null
        defaultEmployeeProjectRolesShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRolesByEmployeeprojectIsEqualToSomething() throws Exception {
        EmployeeProjects employeeproject;
        if (TestUtil.findAll(em, EmployeeProjects.class).isEmpty()) {
            employeeProjectRolesRepository.saveAndFlush(employeeProjectRoles);
            employeeproject = EmployeeProjectsResourceIT.createEntity(em);
        } else {
            employeeproject = TestUtil.findAll(em, EmployeeProjects.class).get(0);
        }
        em.persist(employeeproject);
        em.flush();
        employeeProjectRoles.setEmployeeproject(employeeproject);
        employeeProjectRolesRepository.saveAndFlush(employeeProjectRoles);
        Long employeeprojectId = employeeproject.getId();

        // Get all the employeeProjectRolesList where employeeproject equals to employeeprojectId
        defaultEmployeeProjectRolesShouldBeFound("employeeprojectId.equals=" + employeeprojectId);

        // Get all the employeeProjectRolesList where employeeproject equals to (employeeprojectId + 1)
        defaultEmployeeProjectRolesShouldNotBeFound("employeeprojectId.equals=" + (employeeprojectId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRolesByProjectroleIsEqualToSomething() throws Exception {
        ProjectRoles projectrole;
        if (TestUtil.findAll(em, ProjectRoles.class).isEmpty()) {
            employeeProjectRolesRepository.saveAndFlush(employeeProjectRoles);
            projectrole = ProjectRolesResourceIT.createEntity(em);
        } else {
            projectrole = TestUtil.findAll(em, ProjectRoles.class).get(0);
        }
        em.persist(projectrole);
        em.flush();
        employeeProjectRoles.setProjectrole(projectrole);
        employeeProjectRolesRepository.saveAndFlush(employeeProjectRoles);
        Long projectroleId = projectrole.getId();

        // Get all the employeeProjectRolesList where projectrole equals to projectroleId
        defaultEmployeeProjectRolesShouldBeFound("projectroleId.equals=" + projectroleId);

        // Get all the employeeProjectRolesList where projectrole equals to (projectroleId + 1)
        defaultEmployeeProjectRolesShouldNotBeFound("projectroleId.equals=" + (projectroleId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeProjectRolesShouldBeFound(String filter) throws Exception {
        restEmployeeProjectRolesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeProjectRoles.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restEmployeeProjectRolesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeProjectRolesShouldNotBeFound(String filter) throws Exception {
        restEmployeeProjectRolesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeProjectRolesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployeeProjectRoles() throws Exception {
        // Get the employeeProjectRoles
        restEmployeeProjectRolesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmployeeProjectRoles() throws Exception {
        // Initialize the database
        employeeProjectRolesRepository.saveAndFlush(employeeProjectRoles);

        int databaseSizeBeforeUpdate = employeeProjectRolesRepository.findAll().size();

        // Update the employeeProjectRoles
        EmployeeProjectRoles updatedEmployeeProjectRoles = employeeProjectRolesRepository.findById(employeeProjectRoles.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeProjectRoles are not directly saved in db
        em.detach(updatedEmployeeProjectRoles);
        updatedEmployeeProjectRoles.status(UPDATED_STATUS).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restEmployeeProjectRolesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmployeeProjectRoles.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeProjectRoles))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeProjectRoles in the database
        List<EmployeeProjectRoles> employeeProjectRolesList = employeeProjectRolesRepository.findAll();
        assertThat(employeeProjectRolesList).hasSize(databaseSizeBeforeUpdate);
        EmployeeProjectRoles testEmployeeProjectRoles = employeeProjectRolesList.get(employeeProjectRolesList.size() - 1);
        assertThat(testEmployeeProjectRoles.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEmployeeProjectRoles.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeProjectRoles.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingEmployeeProjectRoles() throws Exception {
        int databaseSizeBeforeUpdate = employeeProjectRolesRepository.findAll().size();
        employeeProjectRoles.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeProjectRolesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeProjectRoles.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeProjectRoles))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeProjectRoles in the database
        List<EmployeeProjectRoles> employeeProjectRolesList = employeeProjectRolesRepository.findAll();
        assertThat(employeeProjectRolesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployeeProjectRoles() throws Exception {
        int databaseSizeBeforeUpdate = employeeProjectRolesRepository.findAll().size();
        employeeProjectRoles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeProjectRolesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeProjectRoles))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeProjectRoles in the database
        List<EmployeeProjectRoles> employeeProjectRolesList = employeeProjectRolesRepository.findAll();
        assertThat(employeeProjectRolesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployeeProjectRoles() throws Exception {
        int databaseSizeBeforeUpdate = employeeProjectRolesRepository.findAll().size();
        employeeProjectRoles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeProjectRolesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeProjectRoles))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeProjectRoles in the database
        List<EmployeeProjectRoles> employeeProjectRolesList = employeeProjectRolesRepository.findAll();
        assertThat(employeeProjectRolesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeProjectRolesWithPatch() throws Exception {
        // Initialize the database
        employeeProjectRolesRepository.saveAndFlush(employeeProjectRoles);

        int databaseSizeBeforeUpdate = employeeProjectRolesRepository.findAll().size();

        // Update the employeeProjectRoles using partial update
        EmployeeProjectRoles partialUpdatedEmployeeProjectRoles = new EmployeeProjectRoles();
        partialUpdatedEmployeeProjectRoles.setId(employeeProjectRoles.getId());

        restEmployeeProjectRolesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeProjectRoles.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeProjectRoles))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeProjectRoles in the database
        List<EmployeeProjectRoles> employeeProjectRolesList = employeeProjectRolesRepository.findAll();
        assertThat(employeeProjectRolesList).hasSize(databaseSizeBeforeUpdate);
        EmployeeProjectRoles testEmployeeProjectRoles = employeeProjectRolesList.get(employeeProjectRolesList.size() - 1);
        assertThat(testEmployeeProjectRoles.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testEmployeeProjectRoles.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testEmployeeProjectRoles.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateEmployeeProjectRolesWithPatch() throws Exception {
        // Initialize the database
        employeeProjectRolesRepository.saveAndFlush(employeeProjectRoles);

        int databaseSizeBeforeUpdate = employeeProjectRolesRepository.findAll().size();

        // Update the employeeProjectRoles using partial update
        EmployeeProjectRoles partialUpdatedEmployeeProjectRoles = new EmployeeProjectRoles();
        partialUpdatedEmployeeProjectRoles.setId(employeeProjectRoles.getId());

        partialUpdatedEmployeeProjectRoles.status(UPDATED_STATUS).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restEmployeeProjectRolesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeProjectRoles.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeProjectRoles))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeProjectRoles in the database
        List<EmployeeProjectRoles> employeeProjectRolesList = employeeProjectRolesRepository.findAll();
        assertThat(employeeProjectRolesList).hasSize(databaseSizeBeforeUpdate);
        EmployeeProjectRoles testEmployeeProjectRoles = employeeProjectRolesList.get(employeeProjectRolesList.size() - 1);
        assertThat(testEmployeeProjectRoles.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEmployeeProjectRoles.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeProjectRoles.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingEmployeeProjectRoles() throws Exception {
        int databaseSizeBeforeUpdate = employeeProjectRolesRepository.findAll().size();
        employeeProjectRoles.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeProjectRolesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employeeProjectRoles.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeProjectRoles))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeProjectRoles in the database
        List<EmployeeProjectRoles> employeeProjectRolesList = employeeProjectRolesRepository.findAll();
        assertThat(employeeProjectRolesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployeeProjectRoles() throws Exception {
        int databaseSizeBeforeUpdate = employeeProjectRolesRepository.findAll().size();
        employeeProjectRoles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeProjectRolesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeProjectRoles))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeProjectRoles in the database
        List<EmployeeProjectRoles> employeeProjectRolesList = employeeProjectRolesRepository.findAll();
        assertThat(employeeProjectRolesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployeeProjectRoles() throws Exception {
        int databaseSizeBeforeUpdate = employeeProjectRolesRepository.findAll().size();
        employeeProjectRoles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeProjectRolesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeProjectRoles))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeProjectRoles in the database
        List<EmployeeProjectRoles> employeeProjectRolesList = employeeProjectRolesRepository.findAll();
        assertThat(employeeProjectRolesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployeeProjectRoles() throws Exception {
        // Initialize the database
        employeeProjectRolesRepository.saveAndFlush(employeeProjectRoles);

        int databaseSizeBeforeDelete = employeeProjectRolesRepository.findAll().size();

        // Delete the employeeProjectRoles
        restEmployeeProjectRolesMockMvc
            .perform(delete(ENTITY_API_URL_ID, employeeProjectRoles.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeProjectRoles> employeeProjectRolesList = employeeProjectRolesRepository.findAll();
        assertThat(employeeProjectRolesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
