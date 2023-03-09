package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.EmployeeProjectRoles;
import com.venturedive.vendian_mono.domain.ProjectLeadership;
import com.venturedive.vendian_mono.domain.ProjectRoles;
import com.venturedive.vendian_mono.repository.ProjectRolesRepository;
import com.venturedive.vendian_mono.service.criteria.ProjectRolesCriteria;
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
 * Integration tests for the {@link ProjectRolesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProjectRolesResourceIT {

    private static final String DEFAULT_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_ROLE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ISLEADERSHIP = false;
    private static final Boolean UPDATED_ISLEADERSHIP = true;

    private static final String ENTITY_API_URL = "/api/project-roles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProjectRolesRepository projectRolesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectRolesMockMvc;

    private ProjectRoles projectRoles;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectRoles createEntity(EntityManager em) {
        ProjectRoles projectRoles = new ProjectRoles()
            .role(DEFAULT_ROLE)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT)
            .isleadership(DEFAULT_ISLEADERSHIP);
        return projectRoles;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectRoles createUpdatedEntity(EntityManager em) {
        ProjectRoles projectRoles = new ProjectRoles()
            .role(UPDATED_ROLE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .isleadership(UPDATED_ISLEADERSHIP);
        return projectRoles;
    }

    @BeforeEach
    public void initTest() {
        projectRoles = createEntity(em);
    }

    @Test
    @Transactional
    void createProjectRoles() throws Exception {
        int databaseSizeBeforeCreate = projectRolesRepository.findAll().size();
        // Create the ProjectRoles
        restProjectRolesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectRoles))
            )
            .andExpect(status().isCreated());

        // Validate the ProjectRoles in the database
        List<ProjectRoles> projectRolesList = projectRolesRepository.findAll();
        assertThat(projectRolesList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectRoles testProjectRoles = projectRolesList.get(projectRolesList.size() - 1);
        assertThat(testProjectRoles.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testProjectRoles.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testProjectRoles.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testProjectRoles.getIsleadership()).isEqualTo(DEFAULT_ISLEADERSHIP);
    }

    @Test
    @Transactional
    void createProjectRolesWithExistingId() throws Exception {
        // Create the ProjectRoles with an existing ID
        projectRoles.setId(1L);

        int databaseSizeBeforeCreate = projectRolesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectRolesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectRoles))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectRoles in the database
        List<ProjectRoles> projectRolesList = projectRolesRepository.findAll();
        assertThat(projectRolesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRolesRepository.findAll().size();
        // set the field null
        projectRoles.setCreatedat(null);

        // Create the ProjectRoles, which fails.

        restProjectRolesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectRoles))
            )
            .andExpect(status().isBadRequest());

        List<ProjectRoles> projectRolesList = projectRolesRepository.findAll();
        assertThat(projectRolesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRolesRepository.findAll().size();
        // set the field null
        projectRoles.setUpdatedat(null);

        // Create the ProjectRoles, which fails.

        restProjectRolesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectRoles))
            )
            .andExpect(status().isBadRequest());

        List<ProjectRoles> projectRolesList = projectRolesRepository.findAll();
        assertThat(projectRolesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProjectRoles() throws Exception {
        // Initialize the database
        projectRolesRepository.saveAndFlush(projectRoles);

        // Get all the projectRolesList
        restProjectRolesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectRoles.getId().intValue())))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].isleadership").value(hasItem(DEFAULT_ISLEADERSHIP.booleanValue())));
    }

    @Test
    @Transactional
    void getProjectRoles() throws Exception {
        // Initialize the database
        projectRolesRepository.saveAndFlush(projectRoles);

        // Get the projectRoles
        restProjectRolesMockMvc
            .perform(get(ENTITY_API_URL_ID, projectRoles.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projectRoles.getId().intValue()))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()))
            .andExpect(jsonPath("$.isleadership").value(DEFAULT_ISLEADERSHIP.booleanValue()));
    }

    @Test
    @Transactional
    void getProjectRolesByIdFiltering() throws Exception {
        // Initialize the database
        projectRolesRepository.saveAndFlush(projectRoles);

        Long id = projectRoles.getId();

        defaultProjectRolesShouldBeFound("id.equals=" + id);
        defaultProjectRolesShouldNotBeFound("id.notEquals=" + id);

        defaultProjectRolesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProjectRolesShouldNotBeFound("id.greaterThan=" + id);

        defaultProjectRolesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProjectRolesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProjectRolesByRoleIsEqualToSomething() throws Exception {
        // Initialize the database
        projectRolesRepository.saveAndFlush(projectRoles);

        // Get all the projectRolesList where role equals to DEFAULT_ROLE
        defaultProjectRolesShouldBeFound("role.equals=" + DEFAULT_ROLE);

        // Get all the projectRolesList where role equals to UPDATED_ROLE
        defaultProjectRolesShouldNotBeFound("role.equals=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllProjectRolesByRoleIsInShouldWork() throws Exception {
        // Initialize the database
        projectRolesRepository.saveAndFlush(projectRoles);

        // Get all the projectRolesList where role in DEFAULT_ROLE or UPDATED_ROLE
        defaultProjectRolesShouldBeFound("role.in=" + DEFAULT_ROLE + "," + UPDATED_ROLE);

        // Get all the projectRolesList where role equals to UPDATED_ROLE
        defaultProjectRolesShouldNotBeFound("role.in=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllProjectRolesByRoleIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectRolesRepository.saveAndFlush(projectRoles);

        // Get all the projectRolesList where role is not null
        defaultProjectRolesShouldBeFound("role.specified=true");

        // Get all the projectRolesList where role is null
        defaultProjectRolesShouldNotBeFound("role.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectRolesByRoleContainsSomething() throws Exception {
        // Initialize the database
        projectRolesRepository.saveAndFlush(projectRoles);

        // Get all the projectRolesList where role contains DEFAULT_ROLE
        defaultProjectRolesShouldBeFound("role.contains=" + DEFAULT_ROLE);

        // Get all the projectRolesList where role contains UPDATED_ROLE
        defaultProjectRolesShouldNotBeFound("role.contains=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllProjectRolesByRoleNotContainsSomething() throws Exception {
        // Initialize the database
        projectRolesRepository.saveAndFlush(projectRoles);

        // Get all the projectRolesList where role does not contain DEFAULT_ROLE
        defaultProjectRolesShouldNotBeFound("role.doesNotContain=" + DEFAULT_ROLE);

        // Get all the projectRolesList where role does not contain UPDATED_ROLE
        defaultProjectRolesShouldBeFound("role.doesNotContain=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllProjectRolesByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        projectRolesRepository.saveAndFlush(projectRoles);

        // Get all the projectRolesList where createdat equals to DEFAULT_CREATEDAT
        defaultProjectRolesShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the projectRolesList where createdat equals to UPDATED_CREATEDAT
        defaultProjectRolesShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllProjectRolesByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        projectRolesRepository.saveAndFlush(projectRoles);

        // Get all the projectRolesList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultProjectRolesShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the projectRolesList where createdat equals to UPDATED_CREATEDAT
        defaultProjectRolesShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllProjectRolesByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectRolesRepository.saveAndFlush(projectRoles);

        // Get all the projectRolesList where createdat is not null
        defaultProjectRolesShouldBeFound("createdat.specified=true");

        // Get all the projectRolesList where createdat is null
        defaultProjectRolesShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectRolesByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        projectRolesRepository.saveAndFlush(projectRoles);

        // Get all the projectRolesList where updatedat equals to DEFAULT_UPDATEDAT
        defaultProjectRolesShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the projectRolesList where updatedat equals to UPDATED_UPDATEDAT
        defaultProjectRolesShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllProjectRolesByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        projectRolesRepository.saveAndFlush(projectRoles);

        // Get all the projectRolesList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultProjectRolesShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the projectRolesList where updatedat equals to UPDATED_UPDATEDAT
        defaultProjectRolesShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllProjectRolesByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectRolesRepository.saveAndFlush(projectRoles);

        // Get all the projectRolesList where updatedat is not null
        defaultProjectRolesShouldBeFound("updatedat.specified=true");

        // Get all the projectRolesList where updatedat is null
        defaultProjectRolesShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectRolesByIsleadershipIsEqualToSomething() throws Exception {
        // Initialize the database
        projectRolesRepository.saveAndFlush(projectRoles);

        // Get all the projectRolesList where isleadership equals to DEFAULT_ISLEADERSHIP
        defaultProjectRolesShouldBeFound("isleadership.equals=" + DEFAULT_ISLEADERSHIP);

        // Get all the projectRolesList where isleadership equals to UPDATED_ISLEADERSHIP
        defaultProjectRolesShouldNotBeFound("isleadership.equals=" + UPDATED_ISLEADERSHIP);
    }

    @Test
    @Transactional
    void getAllProjectRolesByIsleadershipIsInShouldWork() throws Exception {
        // Initialize the database
        projectRolesRepository.saveAndFlush(projectRoles);

        // Get all the projectRolesList where isleadership in DEFAULT_ISLEADERSHIP or UPDATED_ISLEADERSHIP
        defaultProjectRolesShouldBeFound("isleadership.in=" + DEFAULT_ISLEADERSHIP + "," + UPDATED_ISLEADERSHIP);

        // Get all the projectRolesList where isleadership equals to UPDATED_ISLEADERSHIP
        defaultProjectRolesShouldNotBeFound("isleadership.in=" + UPDATED_ISLEADERSHIP);
    }

    @Test
    @Transactional
    void getAllProjectRolesByIsleadershipIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectRolesRepository.saveAndFlush(projectRoles);

        // Get all the projectRolesList where isleadership is not null
        defaultProjectRolesShouldBeFound("isleadership.specified=true");

        // Get all the projectRolesList where isleadership is null
        defaultProjectRolesShouldNotBeFound("isleadership.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectRolesByEmployeeprojectrolesProjectroleIsEqualToSomething() throws Exception {
        EmployeeProjectRoles employeeprojectrolesProjectrole;
        if (TestUtil.findAll(em, EmployeeProjectRoles.class).isEmpty()) {
            projectRolesRepository.saveAndFlush(projectRoles);
            employeeprojectrolesProjectrole = EmployeeProjectRolesResourceIT.createEntity(em);
        } else {
            employeeprojectrolesProjectrole = TestUtil.findAll(em, EmployeeProjectRoles.class).get(0);
        }
        em.persist(employeeprojectrolesProjectrole);
        em.flush();
        projectRoles.addEmployeeprojectrolesProjectrole(employeeprojectrolesProjectrole);
        projectRolesRepository.saveAndFlush(projectRoles);
        Long employeeprojectrolesProjectroleId = employeeprojectrolesProjectrole.getId();

        // Get all the projectRolesList where employeeprojectrolesProjectrole equals to employeeprojectrolesProjectroleId
        defaultProjectRolesShouldBeFound("employeeprojectrolesProjectroleId.equals=" + employeeprojectrolesProjectroleId);

        // Get all the projectRolesList where employeeprojectrolesProjectrole equals to (employeeprojectrolesProjectroleId + 1)
        defaultProjectRolesShouldNotBeFound("employeeprojectrolesProjectroleId.equals=" + (employeeprojectrolesProjectroleId + 1));
    }

    @Test
    @Transactional
    void getAllProjectRolesByProjectleadershipProjectroleIsEqualToSomething() throws Exception {
        ProjectLeadership projectleadershipProjectrole;
        if (TestUtil.findAll(em, ProjectLeadership.class).isEmpty()) {
            projectRolesRepository.saveAndFlush(projectRoles);
            projectleadershipProjectrole = ProjectLeadershipResourceIT.createEntity(em);
        } else {
            projectleadershipProjectrole = TestUtil.findAll(em, ProjectLeadership.class).get(0);
        }
        em.persist(projectleadershipProjectrole);
        em.flush();
        projectRoles.addProjectleadershipProjectrole(projectleadershipProjectrole);
        projectRolesRepository.saveAndFlush(projectRoles);
        Long projectleadershipProjectroleId = projectleadershipProjectrole.getId();

        // Get all the projectRolesList where projectleadershipProjectrole equals to projectleadershipProjectroleId
        defaultProjectRolesShouldBeFound("projectleadershipProjectroleId.equals=" + projectleadershipProjectroleId);

        // Get all the projectRolesList where projectleadershipProjectrole equals to (projectleadershipProjectroleId + 1)
        defaultProjectRolesShouldNotBeFound("projectleadershipProjectroleId.equals=" + (projectleadershipProjectroleId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProjectRolesShouldBeFound(String filter) throws Exception {
        restProjectRolesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectRoles.getId().intValue())))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].isleadership").value(hasItem(DEFAULT_ISLEADERSHIP.booleanValue())));

        // Check, that the count call also returns 1
        restProjectRolesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProjectRolesShouldNotBeFound(String filter) throws Exception {
        restProjectRolesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProjectRolesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProjectRoles() throws Exception {
        // Get the projectRoles
        restProjectRolesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProjectRoles() throws Exception {
        // Initialize the database
        projectRolesRepository.saveAndFlush(projectRoles);

        int databaseSizeBeforeUpdate = projectRolesRepository.findAll().size();

        // Update the projectRoles
        ProjectRoles updatedProjectRoles = projectRolesRepository.findById(projectRoles.getId()).get();
        // Disconnect from session so that the updates on updatedProjectRoles are not directly saved in db
        em.detach(updatedProjectRoles);
        updatedProjectRoles.role(UPDATED_ROLE).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT).isleadership(UPDATED_ISLEADERSHIP);

        restProjectRolesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProjectRoles.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProjectRoles))
            )
            .andExpect(status().isOk());

        // Validate the ProjectRoles in the database
        List<ProjectRoles> projectRolesList = projectRolesRepository.findAll();
        assertThat(projectRolesList).hasSize(databaseSizeBeforeUpdate);
        ProjectRoles testProjectRoles = projectRolesList.get(projectRolesList.size() - 1);
        assertThat(testProjectRoles.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testProjectRoles.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testProjectRoles.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testProjectRoles.getIsleadership()).isEqualTo(UPDATED_ISLEADERSHIP);
    }

    @Test
    @Transactional
    void putNonExistingProjectRoles() throws Exception {
        int databaseSizeBeforeUpdate = projectRolesRepository.findAll().size();
        projectRoles.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectRolesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectRoles.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectRoles))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectRoles in the database
        List<ProjectRoles> projectRolesList = projectRolesRepository.findAll();
        assertThat(projectRolesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProjectRoles() throws Exception {
        int databaseSizeBeforeUpdate = projectRolesRepository.findAll().size();
        projectRoles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectRolesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectRoles))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectRoles in the database
        List<ProjectRoles> projectRolesList = projectRolesRepository.findAll();
        assertThat(projectRolesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProjectRoles() throws Exception {
        int databaseSizeBeforeUpdate = projectRolesRepository.findAll().size();
        projectRoles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectRolesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectRoles))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectRoles in the database
        List<ProjectRoles> projectRolesList = projectRolesRepository.findAll();
        assertThat(projectRolesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjectRolesWithPatch() throws Exception {
        // Initialize the database
        projectRolesRepository.saveAndFlush(projectRoles);

        int databaseSizeBeforeUpdate = projectRolesRepository.findAll().size();

        // Update the projectRoles using partial update
        ProjectRoles partialUpdatedProjectRoles = new ProjectRoles();
        partialUpdatedProjectRoles.setId(projectRoles.getId());

        partialUpdatedProjectRoles.role(UPDATED_ROLE).updatedat(UPDATED_UPDATEDAT);

        restProjectRolesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectRoles.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectRoles))
            )
            .andExpect(status().isOk());

        // Validate the ProjectRoles in the database
        List<ProjectRoles> projectRolesList = projectRolesRepository.findAll();
        assertThat(projectRolesList).hasSize(databaseSizeBeforeUpdate);
        ProjectRoles testProjectRoles = projectRolesList.get(projectRolesList.size() - 1);
        assertThat(testProjectRoles.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testProjectRoles.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testProjectRoles.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testProjectRoles.getIsleadership()).isEqualTo(DEFAULT_ISLEADERSHIP);
    }

    @Test
    @Transactional
    void fullUpdateProjectRolesWithPatch() throws Exception {
        // Initialize the database
        projectRolesRepository.saveAndFlush(projectRoles);

        int databaseSizeBeforeUpdate = projectRolesRepository.findAll().size();

        // Update the projectRoles using partial update
        ProjectRoles partialUpdatedProjectRoles = new ProjectRoles();
        partialUpdatedProjectRoles.setId(projectRoles.getId());

        partialUpdatedProjectRoles
            .role(UPDATED_ROLE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .isleadership(UPDATED_ISLEADERSHIP);

        restProjectRolesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectRoles.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectRoles))
            )
            .andExpect(status().isOk());

        // Validate the ProjectRoles in the database
        List<ProjectRoles> projectRolesList = projectRolesRepository.findAll();
        assertThat(projectRolesList).hasSize(databaseSizeBeforeUpdate);
        ProjectRoles testProjectRoles = projectRolesList.get(projectRolesList.size() - 1);
        assertThat(testProjectRoles.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testProjectRoles.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testProjectRoles.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testProjectRoles.getIsleadership()).isEqualTo(UPDATED_ISLEADERSHIP);
    }

    @Test
    @Transactional
    void patchNonExistingProjectRoles() throws Exception {
        int databaseSizeBeforeUpdate = projectRolesRepository.findAll().size();
        projectRoles.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectRolesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projectRoles.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectRoles))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectRoles in the database
        List<ProjectRoles> projectRolesList = projectRolesRepository.findAll();
        assertThat(projectRolesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProjectRoles() throws Exception {
        int databaseSizeBeforeUpdate = projectRolesRepository.findAll().size();
        projectRoles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectRolesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectRoles))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectRoles in the database
        List<ProjectRoles> projectRolesList = projectRolesRepository.findAll();
        assertThat(projectRolesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProjectRoles() throws Exception {
        int databaseSizeBeforeUpdate = projectRolesRepository.findAll().size();
        projectRoles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectRolesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectRoles))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectRoles in the database
        List<ProjectRoles> projectRolesList = projectRolesRepository.findAll();
        assertThat(projectRolesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProjectRoles() throws Exception {
        // Initialize the database
        projectRolesRepository.saveAndFlush(projectRoles);

        int databaseSizeBeforeDelete = projectRolesRepository.findAll().size();

        // Delete the projectRoles
        restProjectRolesMockMvc
            .perform(delete(ENTITY_API_URL_ID, projectRoles.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProjectRoles> projectRolesList = projectRolesRepository.findAll();
        assertThat(projectRolesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
