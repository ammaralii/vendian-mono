package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.EmployeeRoles;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.RolePermissions;
import com.venturedive.vendian_mono.domain.Roles;
import com.venturedive.vendian_mono.repository.RolesRepository;
import com.venturedive.vendian_mono.service.criteria.RolesCriteria;
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
 * Integration tests for the {@link RolesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RolesResourceIT {

    private static final String DEFAULT_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_ROLE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/roles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRolesMockMvc;

    private Roles roles;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Roles createEntity(EntityManager em) {
        Roles roles = new Roles().role(DEFAULT_ROLE).createdat(DEFAULT_CREATEDAT).updatedat(DEFAULT_UPDATEDAT);
        return roles;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Roles createUpdatedEntity(EntityManager em) {
        Roles roles = new Roles().role(UPDATED_ROLE).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);
        return roles;
    }

    @BeforeEach
    public void initTest() {
        roles = createEntity(em);
    }

    @Test
    @Transactional
    void createRoles() throws Exception {
        int databaseSizeBeforeCreate = rolesRepository.findAll().size();
        // Create the Roles
        restRolesMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roles))
            )
            .andExpect(status().isCreated());

        // Validate the Roles in the database
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeCreate + 1);
        Roles testRoles = rolesList.get(rolesList.size() - 1);
        assertThat(testRoles.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testRoles.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testRoles.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createRolesWithExistingId() throws Exception {
        // Create the Roles with an existing ID
        roles.setId(1L);

        int databaseSizeBeforeCreate = rolesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRolesMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roles))
            )
            .andExpect(status().isBadRequest());

        // Validate the Roles in the database
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = rolesRepository.findAll().size();
        // set the field null
        roles.setCreatedat(null);

        // Create the Roles, which fails.

        restRolesMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roles))
            )
            .andExpect(status().isBadRequest());

        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = rolesRepository.findAll().size();
        // set the field null
        roles.setUpdatedat(null);

        // Create the Roles, which fails.

        restRolesMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roles))
            )
            .andExpect(status().isBadRequest());

        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRoles() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList
        restRolesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roles.getId().intValue())))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getRoles() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get the roles
        restRolesMockMvc
            .perform(get(ENTITY_API_URL_ID, roles.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(roles.getId().intValue()))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getRolesByIdFiltering() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        Long id = roles.getId();

        defaultRolesShouldBeFound("id.equals=" + id);
        defaultRolesShouldNotBeFound("id.notEquals=" + id);

        defaultRolesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRolesShouldNotBeFound("id.greaterThan=" + id);

        defaultRolesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRolesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRolesByRoleIsEqualToSomething() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where role equals to DEFAULT_ROLE
        defaultRolesShouldBeFound("role.equals=" + DEFAULT_ROLE);

        // Get all the rolesList where role equals to UPDATED_ROLE
        defaultRolesShouldNotBeFound("role.equals=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllRolesByRoleIsInShouldWork() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where role in DEFAULT_ROLE or UPDATED_ROLE
        defaultRolesShouldBeFound("role.in=" + DEFAULT_ROLE + "," + UPDATED_ROLE);

        // Get all the rolesList where role equals to UPDATED_ROLE
        defaultRolesShouldNotBeFound("role.in=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllRolesByRoleIsNullOrNotNull() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where role is not null
        defaultRolesShouldBeFound("role.specified=true");

        // Get all the rolesList where role is null
        defaultRolesShouldNotBeFound("role.specified=false");
    }

    @Test
    @Transactional
    void getAllRolesByRoleContainsSomething() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where role contains DEFAULT_ROLE
        defaultRolesShouldBeFound("role.contains=" + DEFAULT_ROLE);

        // Get all the rolesList where role contains UPDATED_ROLE
        defaultRolesShouldNotBeFound("role.contains=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllRolesByRoleNotContainsSomething() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where role does not contain DEFAULT_ROLE
        defaultRolesShouldNotBeFound("role.doesNotContain=" + DEFAULT_ROLE);

        // Get all the rolesList where role does not contain UPDATED_ROLE
        defaultRolesShouldBeFound("role.doesNotContain=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllRolesByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where createdat equals to DEFAULT_CREATEDAT
        defaultRolesShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the rolesList where createdat equals to UPDATED_CREATEDAT
        defaultRolesShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllRolesByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultRolesShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the rolesList where createdat equals to UPDATED_CREATEDAT
        defaultRolesShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllRolesByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where createdat is not null
        defaultRolesShouldBeFound("createdat.specified=true");

        // Get all the rolesList where createdat is null
        defaultRolesShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllRolesByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where updatedat equals to DEFAULT_UPDATEDAT
        defaultRolesShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the rolesList where updatedat equals to UPDATED_UPDATEDAT
        defaultRolesShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllRolesByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultRolesShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the rolesList where updatedat equals to UPDATED_UPDATEDAT
        defaultRolesShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllRolesByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where updatedat is not null
        defaultRolesShouldBeFound("updatedat.specified=true");

        // Get all the rolesList where updatedat is null
        defaultRolesShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllRolesByEmployeerolesRoleIsEqualToSomething() throws Exception {
        EmployeeRoles employeerolesRole;
        if (TestUtil.findAll(em, EmployeeRoles.class).isEmpty()) {
            rolesRepository.saveAndFlush(roles);
            employeerolesRole = EmployeeRolesResourceIT.createEntity(em);
        } else {
            employeerolesRole = TestUtil.findAll(em, EmployeeRoles.class).get(0);
        }
        em.persist(employeerolesRole);
        em.flush();
        roles.addEmployeerolesRole(employeerolesRole);
        rolesRepository.saveAndFlush(roles);
        Long employeerolesRoleId = employeerolesRole.getId();

        // Get all the rolesList where employeerolesRole equals to employeerolesRoleId
        defaultRolesShouldBeFound("employeerolesRoleId.equals=" + employeerolesRoleId);

        // Get all the rolesList where employeerolesRole equals to (employeerolesRoleId + 1)
        defaultRolesShouldNotBeFound("employeerolesRoleId.equals=" + (employeerolesRoleId + 1));
    }

    @Test
    @Transactional
    void getAllRolesByEmployeesRoleIsEqualToSomething() throws Exception {
        Employees employeesRole;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            rolesRepository.saveAndFlush(roles);
            employeesRole = EmployeesResourceIT.createEntity(em);
        } else {
            employeesRole = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employeesRole);
        em.flush();
        roles.addEmployeesRole(employeesRole);
        rolesRepository.saveAndFlush(roles);
        Long employeesRoleId = employeesRole.getId();

        // Get all the rolesList where employeesRole equals to employeesRoleId
        defaultRolesShouldBeFound("employeesRoleId.equals=" + employeesRoleId);

        // Get all the rolesList where employeesRole equals to (employeesRoleId + 1)
        defaultRolesShouldNotBeFound("employeesRoleId.equals=" + (employeesRoleId + 1));
    }

    @Test
    @Transactional
    void getAllRolesByRolepermissionsRoleIsEqualToSomething() throws Exception {
        RolePermissions rolepermissionsRole;
        if (TestUtil.findAll(em, RolePermissions.class).isEmpty()) {
            rolesRepository.saveAndFlush(roles);
            rolepermissionsRole = RolePermissionsResourceIT.createEntity(em);
        } else {
            rolepermissionsRole = TestUtil.findAll(em, RolePermissions.class).get(0);
        }
        em.persist(rolepermissionsRole);
        em.flush();
        roles.addRolepermissionsRole(rolepermissionsRole);
        rolesRepository.saveAndFlush(roles);
        Long rolepermissionsRoleId = rolepermissionsRole.getId();

        // Get all the rolesList where rolepermissionsRole equals to rolepermissionsRoleId
        defaultRolesShouldBeFound("rolepermissionsRoleId.equals=" + rolepermissionsRoleId);

        // Get all the rolesList where rolepermissionsRole equals to (rolepermissionsRoleId + 1)
        defaultRolesShouldNotBeFound("rolepermissionsRoleId.equals=" + (rolepermissionsRoleId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRolesShouldBeFound(String filter) throws Exception {
        restRolesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roles.getId().intValue())))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restRolesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRolesShouldNotBeFound(String filter) throws Exception {
        restRolesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRolesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRoles() throws Exception {
        // Get the roles
        restRolesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRoles() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        int databaseSizeBeforeUpdate = rolesRepository.findAll().size();

        // Update the roles
        Roles updatedRoles = rolesRepository.findById(roles.getId()).get();
        // Disconnect from session so that the updates on updatedRoles are not directly saved in db
        em.detach(updatedRoles);
        updatedRoles.role(UPDATED_ROLE).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restRolesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRoles.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRoles))
            )
            .andExpect(status().isOk());

        // Validate the Roles in the database
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeUpdate);
        Roles testRoles = rolesList.get(rolesList.size() - 1);
        assertThat(testRoles.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testRoles.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testRoles.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingRoles() throws Exception {
        int databaseSizeBeforeUpdate = rolesRepository.findAll().size();
        roles.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRolesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, roles.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roles))
            )
            .andExpect(status().isBadRequest());

        // Validate the Roles in the database
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRoles() throws Exception {
        int databaseSizeBeforeUpdate = rolesRepository.findAll().size();
        roles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRolesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roles))
            )
            .andExpect(status().isBadRequest());

        // Validate the Roles in the database
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRoles() throws Exception {
        int databaseSizeBeforeUpdate = rolesRepository.findAll().size();
        roles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRolesMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roles))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Roles in the database
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRolesWithPatch() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        int databaseSizeBeforeUpdate = rolesRepository.findAll().size();

        // Update the roles using partial update
        Roles partialUpdatedRoles = new Roles();
        partialUpdatedRoles.setId(roles.getId());

        restRolesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoles.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoles))
            )
            .andExpect(status().isOk());

        // Validate the Roles in the database
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeUpdate);
        Roles testRoles = rolesList.get(rolesList.size() - 1);
        assertThat(testRoles.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testRoles.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testRoles.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateRolesWithPatch() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        int databaseSizeBeforeUpdate = rolesRepository.findAll().size();

        // Update the roles using partial update
        Roles partialUpdatedRoles = new Roles();
        partialUpdatedRoles.setId(roles.getId());

        partialUpdatedRoles.role(UPDATED_ROLE).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restRolesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoles.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoles))
            )
            .andExpect(status().isOk());

        // Validate the Roles in the database
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeUpdate);
        Roles testRoles = rolesList.get(rolesList.size() - 1);
        assertThat(testRoles.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testRoles.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testRoles.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingRoles() throws Exception {
        int databaseSizeBeforeUpdate = rolesRepository.findAll().size();
        roles.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRolesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, roles.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roles))
            )
            .andExpect(status().isBadRequest());

        // Validate the Roles in the database
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRoles() throws Exception {
        int databaseSizeBeforeUpdate = rolesRepository.findAll().size();
        roles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRolesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roles))
            )
            .andExpect(status().isBadRequest());

        // Validate the Roles in the database
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRoles() throws Exception {
        int databaseSizeBeforeUpdate = rolesRepository.findAll().size();
        roles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRolesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roles))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Roles in the database
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRoles() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        int databaseSizeBeforeDelete = rolesRepository.findAll().size();

        // Delete the roles
        restRolesMockMvc
            .perform(delete(ENTITY_API_URL_ID, roles.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
