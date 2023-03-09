package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.ProjectLeadership;
import com.venturedive.vendian_mono.domain.ProjectRoles;
import com.venturedive.vendian_mono.domain.Projects;
import com.venturedive.vendian_mono.repository.ProjectLeadershipRepository;
import com.venturedive.vendian_mono.service.criteria.ProjectLeadershipCriteria;
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
 * Integration tests for the {@link ProjectLeadershipResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProjectLeadershipResourceIT {

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/project-leaderships";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProjectLeadershipRepository projectLeadershipRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectLeadershipMockMvc;

    private ProjectLeadership projectLeadership;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectLeadership createEntity(EntityManager em) {
        ProjectLeadership projectLeadership = new ProjectLeadership().createdat(DEFAULT_CREATEDAT).updatedat(DEFAULT_UPDATEDAT);
        return projectLeadership;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectLeadership createUpdatedEntity(EntityManager em) {
        ProjectLeadership projectLeadership = new ProjectLeadership().createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);
        return projectLeadership;
    }

    @BeforeEach
    public void initTest() {
        projectLeadership = createEntity(em);
    }

    @Test
    @Transactional
    void createProjectLeadership() throws Exception {
        int databaseSizeBeforeCreate = projectLeadershipRepository.findAll().size();
        // Create the ProjectLeadership
        restProjectLeadershipMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectLeadership))
            )
            .andExpect(status().isCreated());

        // Validate the ProjectLeadership in the database
        List<ProjectLeadership> projectLeadershipList = projectLeadershipRepository.findAll();
        assertThat(projectLeadershipList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectLeadership testProjectLeadership = projectLeadershipList.get(projectLeadershipList.size() - 1);
        assertThat(testProjectLeadership.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testProjectLeadership.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createProjectLeadershipWithExistingId() throws Exception {
        // Create the ProjectLeadership with an existing ID
        projectLeadership.setId(1L);

        int databaseSizeBeforeCreate = projectLeadershipRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectLeadershipMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectLeadership))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectLeadership in the database
        List<ProjectLeadership> projectLeadershipList = projectLeadershipRepository.findAll();
        assertThat(projectLeadershipList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProjectLeaderships() throws Exception {
        // Initialize the database
        projectLeadershipRepository.saveAndFlush(projectLeadership);

        // Get all the projectLeadershipList
        restProjectLeadershipMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectLeadership.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getProjectLeadership() throws Exception {
        // Initialize the database
        projectLeadershipRepository.saveAndFlush(projectLeadership);

        // Get the projectLeadership
        restProjectLeadershipMockMvc
            .perform(get(ENTITY_API_URL_ID, projectLeadership.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projectLeadership.getId().intValue()))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getProjectLeadershipsByIdFiltering() throws Exception {
        // Initialize the database
        projectLeadershipRepository.saveAndFlush(projectLeadership);

        Long id = projectLeadership.getId();

        defaultProjectLeadershipShouldBeFound("id.equals=" + id);
        defaultProjectLeadershipShouldNotBeFound("id.notEquals=" + id);

        defaultProjectLeadershipShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProjectLeadershipShouldNotBeFound("id.greaterThan=" + id);

        defaultProjectLeadershipShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProjectLeadershipShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProjectLeadershipsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        projectLeadershipRepository.saveAndFlush(projectLeadership);

        // Get all the projectLeadershipList where createdat equals to DEFAULT_CREATEDAT
        defaultProjectLeadershipShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the projectLeadershipList where createdat equals to UPDATED_CREATEDAT
        defaultProjectLeadershipShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllProjectLeadershipsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        projectLeadershipRepository.saveAndFlush(projectLeadership);

        // Get all the projectLeadershipList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultProjectLeadershipShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the projectLeadershipList where createdat equals to UPDATED_CREATEDAT
        defaultProjectLeadershipShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllProjectLeadershipsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectLeadershipRepository.saveAndFlush(projectLeadership);

        // Get all the projectLeadershipList where createdat is not null
        defaultProjectLeadershipShouldBeFound("createdat.specified=true");

        // Get all the projectLeadershipList where createdat is null
        defaultProjectLeadershipShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectLeadershipsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        projectLeadershipRepository.saveAndFlush(projectLeadership);

        // Get all the projectLeadershipList where updatedat equals to DEFAULT_UPDATEDAT
        defaultProjectLeadershipShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the projectLeadershipList where updatedat equals to UPDATED_UPDATEDAT
        defaultProjectLeadershipShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllProjectLeadershipsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        projectLeadershipRepository.saveAndFlush(projectLeadership);

        // Get all the projectLeadershipList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultProjectLeadershipShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the projectLeadershipList where updatedat equals to UPDATED_UPDATEDAT
        defaultProjectLeadershipShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllProjectLeadershipsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectLeadershipRepository.saveAndFlush(projectLeadership);

        // Get all the projectLeadershipList where updatedat is not null
        defaultProjectLeadershipShouldBeFound("updatedat.specified=true");

        // Get all the projectLeadershipList where updatedat is null
        defaultProjectLeadershipShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectLeadershipsByProjectroleIsEqualToSomething() throws Exception {
        ProjectRoles projectrole;
        if (TestUtil.findAll(em, ProjectRoles.class).isEmpty()) {
            projectLeadershipRepository.saveAndFlush(projectLeadership);
            projectrole = ProjectRolesResourceIT.createEntity(em);
        } else {
            projectrole = TestUtil.findAll(em, ProjectRoles.class).get(0);
        }
        em.persist(projectrole);
        em.flush();
        projectLeadership.setProjectrole(projectrole);
        projectLeadershipRepository.saveAndFlush(projectLeadership);
        Long projectroleId = projectrole.getId();

        // Get all the projectLeadershipList where projectrole equals to projectroleId
        defaultProjectLeadershipShouldBeFound("projectroleId.equals=" + projectroleId);

        // Get all the projectLeadershipList where projectrole equals to (projectroleId + 1)
        defaultProjectLeadershipShouldNotBeFound("projectroleId.equals=" + (projectroleId + 1));
    }

    @Test
    @Transactional
    void getAllProjectLeadershipsByProjectIsEqualToSomething() throws Exception {
        Projects project;
        if (TestUtil.findAll(em, Projects.class).isEmpty()) {
            projectLeadershipRepository.saveAndFlush(projectLeadership);
            project = ProjectsResourceIT.createEntity(em);
        } else {
            project = TestUtil.findAll(em, Projects.class).get(0);
        }
        em.persist(project);
        em.flush();
        projectLeadership.setProject(project);
        projectLeadershipRepository.saveAndFlush(projectLeadership);
        Long projectId = project.getId();

        // Get all the projectLeadershipList where project equals to projectId
        defaultProjectLeadershipShouldBeFound("projectId.equals=" + projectId);

        // Get all the projectLeadershipList where project equals to (projectId + 1)
        defaultProjectLeadershipShouldNotBeFound("projectId.equals=" + (projectId + 1));
    }

    @Test
    @Transactional
    void getAllProjectLeadershipsByEmployeeIsEqualToSomething() throws Exception {
        Employees employee;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            projectLeadershipRepository.saveAndFlush(projectLeadership);
            employee = EmployeesResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employee);
        em.flush();
        projectLeadership.setEmployee(employee);
        projectLeadershipRepository.saveAndFlush(projectLeadership);
        Long employeeId = employee.getId();

        // Get all the projectLeadershipList where employee equals to employeeId
        defaultProjectLeadershipShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the projectLeadershipList where employee equals to (employeeId + 1)
        defaultProjectLeadershipShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProjectLeadershipShouldBeFound(String filter) throws Exception {
        restProjectLeadershipMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectLeadership.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restProjectLeadershipMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProjectLeadershipShouldNotBeFound(String filter) throws Exception {
        restProjectLeadershipMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProjectLeadershipMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProjectLeadership() throws Exception {
        // Get the projectLeadership
        restProjectLeadershipMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProjectLeadership() throws Exception {
        // Initialize the database
        projectLeadershipRepository.saveAndFlush(projectLeadership);

        int databaseSizeBeforeUpdate = projectLeadershipRepository.findAll().size();

        // Update the projectLeadership
        ProjectLeadership updatedProjectLeadership = projectLeadershipRepository.findById(projectLeadership.getId()).get();
        // Disconnect from session so that the updates on updatedProjectLeadership are not directly saved in db
        em.detach(updatedProjectLeadership);
        updatedProjectLeadership.createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restProjectLeadershipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProjectLeadership.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProjectLeadership))
            )
            .andExpect(status().isOk());

        // Validate the ProjectLeadership in the database
        List<ProjectLeadership> projectLeadershipList = projectLeadershipRepository.findAll();
        assertThat(projectLeadershipList).hasSize(databaseSizeBeforeUpdate);
        ProjectLeadership testProjectLeadership = projectLeadershipList.get(projectLeadershipList.size() - 1);
        assertThat(testProjectLeadership.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testProjectLeadership.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingProjectLeadership() throws Exception {
        int databaseSizeBeforeUpdate = projectLeadershipRepository.findAll().size();
        projectLeadership.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectLeadershipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectLeadership.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectLeadership))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectLeadership in the database
        List<ProjectLeadership> projectLeadershipList = projectLeadershipRepository.findAll();
        assertThat(projectLeadershipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProjectLeadership() throws Exception {
        int databaseSizeBeforeUpdate = projectLeadershipRepository.findAll().size();
        projectLeadership.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectLeadershipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectLeadership))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectLeadership in the database
        List<ProjectLeadership> projectLeadershipList = projectLeadershipRepository.findAll();
        assertThat(projectLeadershipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProjectLeadership() throws Exception {
        int databaseSizeBeforeUpdate = projectLeadershipRepository.findAll().size();
        projectLeadership.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectLeadershipMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectLeadership))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectLeadership in the database
        List<ProjectLeadership> projectLeadershipList = projectLeadershipRepository.findAll();
        assertThat(projectLeadershipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjectLeadershipWithPatch() throws Exception {
        // Initialize the database
        projectLeadershipRepository.saveAndFlush(projectLeadership);

        int databaseSizeBeforeUpdate = projectLeadershipRepository.findAll().size();

        // Update the projectLeadership using partial update
        ProjectLeadership partialUpdatedProjectLeadership = new ProjectLeadership();
        partialUpdatedProjectLeadership.setId(projectLeadership.getId());

        partialUpdatedProjectLeadership.updatedat(UPDATED_UPDATEDAT);

        restProjectLeadershipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectLeadership.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectLeadership))
            )
            .andExpect(status().isOk());

        // Validate the ProjectLeadership in the database
        List<ProjectLeadership> projectLeadershipList = projectLeadershipRepository.findAll();
        assertThat(projectLeadershipList).hasSize(databaseSizeBeforeUpdate);
        ProjectLeadership testProjectLeadership = projectLeadershipList.get(projectLeadershipList.size() - 1);
        assertThat(testProjectLeadership.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testProjectLeadership.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateProjectLeadershipWithPatch() throws Exception {
        // Initialize the database
        projectLeadershipRepository.saveAndFlush(projectLeadership);

        int databaseSizeBeforeUpdate = projectLeadershipRepository.findAll().size();

        // Update the projectLeadership using partial update
        ProjectLeadership partialUpdatedProjectLeadership = new ProjectLeadership();
        partialUpdatedProjectLeadership.setId(projectLeadership.getId());

        partialUpdatedProjectLeadership.createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restProjectLeadershipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectLeadership.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectLeadership))
            )
            .andExpect(status().isOk());

        // Validate the ProjectLeadership in the database
        List<ProjectLeadership> projectLeadershipList = projectLeadershipRepository.findAll();
        assertThat(projectLeadershipList).hasSize(databaseSizeBeforeUpdate);
        ProjectLeadership testProjectLeadership = projectLeadershipList.get(projectLeadershipList.size() - 1);
        assertThat(testProjectLeadership.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testProjectLeadership.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingProjectLeadership() throws Exception {
        int databaseSizeBeforeUpdate = projectLeadershipRepository.findAll().size();
        projectLeadership.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectLeadershipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projectLeadership.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectLeadership))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectLeadership in the database
        List<ProjectLeadership> projectLeadershipList = projectLeadershipRepository.findAll();
        assertThat(projectLeadershipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProjectLeadership() throws Exception {
        int databaseSizeBeforeUpdate = projectLeadershipRepository.findAll().size();
        projectLeadership.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectLeadershipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectLeadership))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectLeadership in the database
        List<ProjectLeadership> projectLeadershipList = projectLeadershipRepository.findAll();
        assertThat(projectLeadershipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProjectLeadership() throws Exception {
        int databaseSizeBeforeUpdate = projectLeadershipRepository.findAll().size();
        projectLeadership.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectLeadershipMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectLeadership))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectLeadership in the database
        List<ProjectLeadership> projectLeadershipList = projectLeadershipRepository.findAll();
        assertThat(projectLeadershipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProjectLeadership() throws Exception {
        // Initialize the database
        projectLeadershipRepository.saveAndFlush(projectLeadership);

        int databaseSizeBeforeDelete = projectLeadershipRepository.findAll().size();

        // Delete the projectLeadership
        restProjectLeadershipMockMvc
            .perform(delete(ENTITY_API_URL_ID, projectLeadership.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProjectLeadership> projectLeadershipList = projectLeadershipRepository.findAll();
        assertThat(projectLeadershipList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
