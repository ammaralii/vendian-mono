package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Permissions;
import com.venturedive.vendian_mono.domain.RolePermissions;
import com.venturedive.vendian_mono.domain.Roles;
import com.venturedive.vendian_mono.repository.RolePermissionsRepository;
import com.venturedive.vendian_mono.service.criteria.RolePermissionsCriteria;
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
 * Integration tests for the {@link RolePermissionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RolePermissionsResourceIT {

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/role-permissions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RolePermissionsRepository rolePermissionsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRolePermissionsMockMvc;

    private RolePermissions rolePermissions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RolePermissions createEntity(EntityManager em) {
        RolePermissions rolePermissions = new RolePermissions().createdat(DEFAULT_CREATEDAT).updatedat(DEFAULT_UPDATEDAT);
        return rolePermissions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RolePermissions createUpdatedEntity(EntityManager em) {
        RolePermissions rolePermissions = new RolePermissions().createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);
        return rolePermissions;
    }

    @BeforeEach
    public void initTest() {
        rolePermissions = createEntity(em);
    }

    @Test
    @Transactional
    void createRolePermissions() throws Exception {
        int databaseSizeBeforeCreate = rolePermissionsRepository.findAll().size();
        // Create the RolePermissions
        restRolePermissionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rolePermissions))
            )
            .andExpect(status().isCreated());

        // Validate the RolePermissions in the database
        List<RolePermissions> rolePermissionsList = rolePermissionsRepository.findAll();
        assertThat(rolePermissionsList).hasSize(databaseSizeBeforeCreate + 1);
        RolePermissions testRolePermissions = rolePermissionsList.get(rolePermissionsList.size() - 1);
        assertThat(testRolePermissions.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testRolePermissions.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createRolePermissionsWithExistingId() throws Exception {
        // Create the RolePermissions with an existing ID
        rolePermissions.setId(1L);

        int databaseSizeBeforeCreate = rolePermissionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRolePermissionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rolePermissions))
            )
            .andExpect(status().isBadRequest());

        // Validate the RolePermissions in the database
        List<RolePermissions> rolePermissionsList = rolePermissionsRepository.findAll();
        assertThat(rolePermissionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = rolePermissionsRepository.findAll().size();
        // set the field null
        rolePermissions.setCreatedat(null);

        // Create the RolePermissions, which fails.

        restRolePermissionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rolePermissions))
            )
            .andExpect(status().isBadRequest());

        List<RolePermissions> rolePermissionsList = rolePermissionsRepository.findAll();
        assertThat(rolePermissionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = rolePermissionsRepository.findAll().size();
        // set the field null
        rolePermissions.setUpdatedat(null);

        // Create the RolePermissions, which fails.

        restRolePermissionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rolePermissions))
            )
            .andExpect(status().isBadRequest());

        List<RolePermissions> rolePermissionsList = rolePermissionsRepository.findAll();
        assertThat(rolePermissionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRolePermissions() throws Exception {
        // Initialize the database
        rolePermissionsRepository.saveAndFlush(rolePermissions);

        // Get all the rolePermissionsList
        restRolePermissionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rolePermissions.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getRolePermissions() throws Exception {
        // Initialize the database
        rolePermissionsRepository.saveAndFlush(rolePermissions);

        // Get the rolePermissions
        restRolePermissionsMockMvc
            .perform(get(ENTITY_API_URL_ID, rolePermissions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rolePermissions.getId().intValue()))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getRolePermissionsByIdFiltering() throws Exception {
        // Initialize the database
        rolePermissionsRepository.saveAndFlush(rolePermissions);

        Long id = rolePermissions.getId();

        defaultRolePermissionsShouldBeFound("id.equals=" + id);
        defaultRolePermissionsShouldNotBeFound("id.notEquals=" + id);

        defaultRolePermissionsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRolePermissionsShouldNotBeFound("id.greaterThan=" + id);

        defaultRolePermissionsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRolePermissionsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRolePermissionsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        rolePermissionsRepository.saveAndFlush(rolePermissions);

        // Get all the rolePermissionsList where createdat equals to DEFAULT_CREATEDAT
        defaultRolePermissionsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the rolePermissionsList where createdat equals to UPDATED_CREATEDAT
        defaultRolePermissionsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllRolePermissionsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        rolePermissionsRepository.saveAndFlush(rolePermissions);

        // Get all the rolePermissionsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultRolePermissionsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the rolePermissionsList where createdat equals to UPDATED_CREATEDAT
        defaultRolePermissionsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllRolePermissionsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        rolePermissionsRepository.saveAndFlush(rolePermissions);

        // Get all the rolePermissionsList where createdat is not null
        defaultRolePermissionsShouldBeFound("createdat.specified=true");

        // Get all the rolePermissionsList where createdat is null
        defaultRolePermissionsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllRolePermissionsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        rolePermissionsRepository.saveAndFlush(rolePermissions);

        // Get all the rolePermissionsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultRolePermissionsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the rolePermissionsList where updatedat equals to UPDATED_UPDATEDAT
        defaultRolePermissionsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllRolePermissionsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        rolePermissionsRepository.saveAndFlush(rolePermissions);

        // Get all the rolePermissionsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultRolePermissionsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the rolePermissionsList where updatedat equals to UPDATED_UPDATEDAT
        defaultRolePermissionsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllRolePermissionsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        rolePermissionsRepository.saveAndFlush(rolePermissions);

        // Get all the rolePermissionsList where updatedat is not null
        defaultRolePermissionsShouldBeFound("updatedat.specified=true");

        // Get all the rolePermissionsList where updatedat is null
        defaultRolePermissionsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllRolePermissionsByRoleIsEqualToSomething() throws Exception {
        Roles role;
        if (TestUtil.findAll(em, Roles.class).isEmpty()) {
            rolePermissionsRepository.saveAndFlush(rolePermissions);
            role = RolesResourceIT.createEntity(em);
        } else {
            role = TestUtil.findAll(em, Roles.class).get(0);
        }
        em.persist(role);
        em.flush();
        rolePermissions.setRole(role);
        rolePermissionsRepository.saveAndFlush(rolePermissions);
        Long roleId = role.getId();

        // Get all the rolePermissionsList where role equals to roleId
        defaultRolePermissionsShouldBeFound("roleId.equals=" + roleId);

        // Get all the rolePermissionsList where role equals to (roleId + 1)
        defaultRolePermissionsShouldNotBeFound("roleId.equals=" + (roleId + 1));
    }

    @Test
    @Transactional
    void getAllRolePermissionsByPermissionIsEqualToSomething() throws Exception {
        Permissions permission;
        if (TestUtil.findAll(em, Permissions.class).isEmpty()) {
            rolePermissionsRepository.saveAndFlush(rolePermissions);
            permission = PermissionsResourceIT.createEntity(em);
        } else {
            permission = TestUtil.findAll(em, Permissions.class).get(0);
        }
        em.persist(permission);
        em.flush();
        rolePermissions.setPermission(permission);
        rolePermissionsRepository.saveAndFlush(rolePermissions);
        Long permissionId = permission.getId();

        // Get all the rolePermissionsList where permission equals to permissionId
        defaultRolePermissionsShouldBeFound("permissionId.equals=" + permissionId);

        // Get all the rolePermissionsList where permission equals to (permissionId + 1)
        defaultRolePermissionsShouldNotBeFound("permissionId.equals=" + (permissionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRolePermissionsShouldBeFound(String filter) throws Exception {
        restRolePermissionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rolePermissions.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restRolePermissionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRolePermissionsShouldNotBeFound(String filter) throws Exception {
        restRolePermissionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRolePermissionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRolePermissions() throws Exception {
        // Get the rolePermissions
        restRolePermissionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRolePermissions() throws Exception {
        // Initialize the database
        rolePermissionsRepository.saveAndFlush(rolePermissions);

        int databaseSizeBeforeUpdate = rolePermissionsRepository.findAll().size();

        // Update the rolePermissions
        RolePermissions updatedRolePermissions = rolePermissionsRepository.findById(rolePermissions.getId()).get();
        // Disconnect from session so that the updates on updatedRolePermissions are not directly saved in db
        em.detach(updatedRolePermissions);
        updatedRolePermissions.createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restRolePermissionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRolePermissions.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRolePermissions))
            )
            .andExpect(status().isOk());

        // Validate the RolePermissions in the database
        List<RolePermissions> rolePermissionsList = rolePermissionsRepository.findAll();
        assertThat(rolePermissionsList).hasSize(databaseSizeBeforeUpdate);
        RolePermissions testRolePermissions = rolePermissionsList.get(rolePermissionsList.size() - 1);
        assertThat(testRolePermissions.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testRolePermissions.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingRolePermissions() throws Exception {
        int databaseSizeBeforeUpdate = rolePermissionsRepository.findAll().size();
        rolePermissions.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRolePermissionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rolePermissions.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rolePermissions))
            )
            .andExpect(status().isBadRequest());

        // Validate the RolePermissions in the database
        List<RolePermissions> rolePermissionsList = rolePermissionsRepository.findAll();
        assertThat(rolePermissionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRolePermissions() throws Exception {
        int databaseSizeBeforeUpdate = rolePermissionsRepository.findAll().size();
        rolePermissions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRolePermissionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rolePermissions))
            )
            .andExpect(status().isBadRequest());

        // Validate the RolePermissions in the database
        List<RolePermissions> rolePermissionsList = rolePermissionsRepository.findAll();
        assertThat(rolePermissionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRolePermissions() throws Exception {
        int databaseSizeBeforeUpdate = rolePermissionsRepository.findAll().size();
        rolePermissions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRolePermissionsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rolePermissions))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RolePermissions in the database
        List<RolePermissions> rolePermissionsList = rolePermissionsRepository.findAll();
        assertThat(rolePermissionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRolePermissionsWithPatch() throws Exception {
        // Initialize the database
        rolePermissionsRepository.saveAndFlush(rolePermissions);

        int databaseSizeBeforeUpdate = rolePermissionsRepository.findAll().size();

        // Update the rolePermissions using partial update
        RolePermissions partialUpdatedRolePermissions = new RolePermissions();
        partialUpdatedRolePermissions.setId(rolePermissions.getId());

        partialUpdatedRolePermissions.createdat(UPDATED_CREATEDAT);

        restRolePermissionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRolePermissions.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRolePermissions))
            )
            .andExpect(status().isOk());

        // Validate the RolePermissions in the database
        List<RolePermissions> rolePermissionsList = rolePermissionsRepository.findAll();
        assertThat(rolePermissionsList).hasSize(databaseSizeBeforeUpdate);
        RolePermissions testRolePermissions = rolePermissionsList.get(rolePermissionsList.size() - 1);
        assertThat(testRolePermissions.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testRolePermissions.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateRolePermissionsWithPatch() throws Exception {
        // Initialize the database
        rolePermissionsRepository.saveAndFlush(rolePermissions);

        int databaseSizeBeforeUpdate = rolePermissionsRepository.findAll().size();

        // Update the rolePermissions using partial update
        RolePermissions partialUpdatedRolePermissions = new RolePermissions();
        partialUpdatedRolePermissions.setId(rolePermissions.getId());

        partialUpdatedRolePermissions.createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restRolePermissionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRolePermissions.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRolePermissions))
            )
            .andExpect(status().isOk());

        // Validate the RolePermissions in the database
        List<RolePermissions> rolePermissionsList = rolePermissionsRepository.findAll();
        assertThat(rolePermissionsList).hasSize(databaseSizeBeforeUpdate);
        RolePermissions testRolePermissions = rolePermissionsList.get(rolePermissionsList.size() - 1);
        assertThat(testRolePermissions.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testRolePermissions.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingRolePermissions() throws Exception {
        int databaseSizeBeforeUpdate = rolePermissionsRepository.findAll().size();
        rolePermissions.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRolePermissionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rolePermissions.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rolePermissions))
            )
            .andExpect(status().isBadRequest());

        // Validate the RolePermissions in the database
        List<RolePermissions> rolePermissionsList = rolePermissionsRepository.findAll();
        assertThat(rolePermissionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRolePermissions() throws Exception {
        int databaseSizeBeforeUpdate = rolePermissionsRepository.findAll().size();
        rolePermissions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRolePermissionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rolePermissions))
            )
            .andExpect(status().isBadRequest());

        // Validate the RolePermissions in the database
        List<RolePermissions> rolePermissionsList = rolePermissionsRepository.findAll();
        assertThat(rolePermissionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRolePermissions() throws Exception {
        int databaseSizeBeforeUpdate = rolePermissionsRepository.findAll().size();
        rolePermissions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRolePermissionsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rolePermissions))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RolePermissions in the database
        List<RolePermissions> rolePermissionsList = rolePermissionsRepository.findAll();
        assertThat(rolePermissionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRolePermissions() throws Exception {
        // Initialize the database
        rolePermissionsRepository.saveAndFlush(rolePermissions);

        int databaseSizeBeforeDelete = rolePermissionsRepository.findAll().size();

        // Delete the rolePermissions
        restRolePermissionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, rolePermissions.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RolePermissions> rolePermissionsList = rolePermissionsRepository.findAll();
        assertThat(rolePermissionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
