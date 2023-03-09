package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Permissions;
import com.venturedive.vendian_mono.domain.RolePermissions;
import com.venturedive.vendian_mono.repository.PermissionsRepository;
import com.venturedive.vendian_mono.service.criteria.PermissionsCriteria;
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
 * Integration tests for the {@link PermissionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PermissionsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_GROUP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ISACTIVE = false;
    private static final Boolean UPDATED_ISACTIVE = true;

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/permissions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PermissionsRepository permissionsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPermissionsMockMvc;

    private Permissions permissions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Permissions createEntity(EntityManager em) {
        Permissions permissions = new Permissions()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .groupName(DEFAULT_GROUP_NAME)
            .isactive(DEFAULT_ISACTIVE)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        return permissions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Permissions createUpdatedEntity(EntityManager em) {
        Permissions permissions = new Permissions()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .groupName(UPDATED_GROUP_NAME)
            .isactive(UPDATED_ISACTIVE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        return permissions;
    }

    @BeforeEach
    public void initTest() {
        permissions = createEntity(em);
    }

    @Test
    @Transactional
    void createPermissions() throws Exception {
        int databaseSizeBeforeCreate = permissionsRepository.findAll().size();
        // Create the Permissions
        restPermissionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(permissions))
            )
            .andExpect(status().isCreated());

        // Validate the Permissions in the database
        List<Permissions> permissionsList = permissionsRepository.findAll();
        assertThat(permissionsList).hasSize(databaseSizeBeforeCreate + 1);
        Permissions testPermissions = permissionsList.get(permissionsList.size() - 1);
        assertThat(testPermissions.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPermissions.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPermissions.getGroupName()).isEqualTo(DEFAULT_GROUP_NAME);
        assertThat(testPermissions.getIsactive()).isEqualTo(DEFAULT_ISACTIVE);
        assertThat(testPermissions.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testPermissions.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createPermissionsWithExistingId() throws Exception {
        // Create the Permissions with an existing ID
        permissions.setId(1L);

        int databaseSizeBeforeCreate = permissionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPermissionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(permissions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Permissions in the database
        List<Permissions> permissionsList = permissionsRepository.findAll();
        assertThat(permissionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = permissionsRepository.findAll().size();
        // set the field null
        permissions.setCreatedat(null);

        // Create the Permissions, which fails.

        restPermissionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(permissions))
            )
            .andExpect(status().isBadRequest());

        List<Permissions> permissionsList = permissionsRepository.findAll();
        assertThat(permissionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = permissionsRepository.findAll().size();
        // set the field null
        permissions.setUpdatedat(null);

        // Create the Permissions, which fails.

        restPermissionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(permissions))
            )
            .andExpect(status().isBadRequest());

        List<Permissions> permissionsList = permissionsRepository.findAll();
        assertThat(permissionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPermissions() throws Exception {
        // Initialize the database
        permissionsRepository.saveAndFlush(permissions);

        // Get all the permissionsList
        restPermissionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(permissions.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].groupName").value(hasItem(DEFAULT_GROUP_NAME)))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getPermissions() throws Exception {
        // Initialize the database
        permissionsRepository.saveAndFlush(permissions);

        // Get the permissions
        restPermissionsMockMvc
            .perform(get(ENTITY_API_URL_ID, permissions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(permissions.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.groupName").value(DEFAULT_GROUP_NAME))
            .andExpect(jsonPath("$.isactive").value(DEFAULT_ISACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getPermissionsByIdFiltering() throws Exception {
        // Initialize the database
        permissionsRepository.saveAndFlush(permissions);

        Long id = permissions.getId();

        defaultPermissionsShouldBeFound("id.equals=" + id);
        defaultPermissionsShouldNotBeFound("id.notEquals=" + id);

        defaultPermissionsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPermissionsShouldNotBeFound("id.greaterThan=" + id);

        defaultPermissionsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPermissionsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPermissionsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        permissionsRepository.saveAndFlush(permissions);

        // Get all the permissionsList where name equals to DEFAULT_NAME
        defaultPermissionsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the permissionsList where name equals to UPDATED_NAME
        defaultPermissionsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPermissionsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        permissionsRepository.saveAndFlush(permissions);

        // Get all the permissionsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPermissionsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the permissionsList where name equals to UPDATED_NAME
        defaultPermissionsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPermissionsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        permissionsRepository.saveAndFlush(permissions);

        // Get all the permissionsList where name is not null
        defaultPermissionsShouldBeFound("name.specified=true");

        // Get all the permissionsList where name is null
        defaultPermissionsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllPermissionsByNameContainsSomething() throws Exception {
        // Initialize the database
        permissionsRepository.saveAndFlush(permissions);

        // Get all the permissionsList where name contains DEFAULT_NAME
        defaultPermissionsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the permissionsList where name contains UPDATED_NAME
        defaultPermissionsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPermissionsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        permissionsRepository.saveAndFlush(permissions);

        // Get all the permissionsList where name does not contain DEFAULT_NAME
        defaultPermissionsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the permissionsList where name does not contain UPDATED_NAME
        defaultPermissionsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPermissionsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        permissionsRepository.saveAndFlush(permissions);

        // Get all the permissionsList where description equals to DEFAULT_DESCRIPTION
        defaultPermissionsShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the permissionsList where description equals to UPDATED_DESCRIPTION
        defaultPermissionsShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPermissionsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        permissionsRepository.saveAndFlush(permissions);

        // Get all the permissionsList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultPermissionsShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the permissionsList where description equals to UPDATED_DESCRIPTION
        defaultPermissionsShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPermissionsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        permissionsRepository.saveAndFlush(permissions);

        // Get all the permissionsList where description is not null
        defaultPermissionsShouldBeFound("description.specified=true");

        // Get all the permissionsList where description is null
        defaultPermissionsShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllPermissionsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        permissionsRepository.saveAndFlush(permissions);

        // Get all the permissionsList where description contains DEFAULT_DESCRIPTION
        defaultPermissionsShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the permissionsList where description contains UPDATED_DESCRIPTION
        defaultPermissionsShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPermissionsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        permissionsRepository.saveAndFlush(permissions);

        // Get all the permissionsList where description does not contain DEFAULT_DESCRIPTION
        defaultPermissionsShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the permissionsList where description does not contain UPDATED_DESCRIPTION
        defaultPermissionsShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPermissionsByGroupNameIsEqualToSomething() throws Exception {
        // Initialize the database
        permissionsRepository.saveAndFlush(permissions);

        // Get all the permissionsList where groupName equals to DEFAULT_GROUP_NAME
        defaultPermissionsShouldBeFound("groupName.equals=" + DEFAULT_GROUP_NAME);

        // Get all the permissionsList where groupName equals to UPDATED_GROUP_NAME
        defaultPermissionsShouldNotBeFound("groupName.equals=" + UPDATED_GROUP_NAME);
    }

    @Test
    @Transactional
    void getAllPermissionsByGroupNameIsInShouldWork() throws Exception {
        // Initialize the database
        permissionsRepository.saveAndFlush(permissions);

        // Get all the permissionsList where groupName in DEFAULT_GROUP_NAME or UPDATED_GROUP_NAME
        defaultPermissionsShouldBeFound("groupName.in=" + DEFAULT_GROUP_NAME + "," + UPDATED_GROUP_NAME);

        // Get all the permissionsList where groupName equals to UPDATED_GROUP_NAME
        defaultPermissionsShouldNotBeFound("groupName.in=" + UPDATED_GROUP_NAME);
    }

    @Test
    @Transactional
    void getAllPermissionsByGroupNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        permissionsRepository.saveAndFlush(permissions);

        // Get all the permissionsList where groupName is not null
        defaultPermissionsShouldBeFound("groupName.specified=true");

        // Get all the permissionsList where groupName is null
        defaultPermissionsShouldNotBeFound("groupName.specified=false");
    }

    @Test
    @Transactional
    void getAllPermissionsByGroupNameContainsSomething() throws Exception {
        // Initialize the database
        permissionsRepository.saveAndFlush(permissions);

        // Get all the permissionsList where groupName contains DEFAULT_GROUP_NAME
        defaultPermissionsShouldBeFound("groupName.contains=" + DEFAULT_GROUP_NAME);

        // Get all the permissionsList where groupName contains UPDATED_GROUP_NAME
        defaultPermissionsShouldNotBeFound("groupName.contains=" + UPDATED_GROUP_NAME);
    }

    @Test
    @Transactional
    void getAllPermissionsByGroupNameNotContainsSomething() throws Exception {
        // Initialize the database
        permissionsRepository.saveAndFlush(permissions);

        // Get all the permissionsList where groupName does not contain DEFAULT_GROUP_NAME
        defaultPermissionsShouldNotBeFound("groupName.doesNotContain=" + DEFAULT_GROUP_NAME);

        // Get all the permissionsList where groupName does not contain UPDATED_GROUP_NAME
        defaultPermissionsShouldBeFound("groupName.doesNotContain=" + UPDATED_GROUP_NAME);
    }

    @Test
    @Transactional
    void getAllPermissionsByIsactiveIsEqualToSomething() throws Exception {
        // Initialize the database
        permissionsRepository.saveAndFlush(permissions);

        // Get all the permissionsList where isactive equals to DEFAULT_ISACTIVE
        defaultPermissionsShouldBeFound("isactive.equals=" + DEFAULT_ISACTIVE);

        // Get all the permissionsList where isactive equals to UPDATED_ISACTIVE
        defaultPermissionsShouldNotBeFound("isactive.equals=" + UPDATED_ISACTIVE);
    }

    @Test
    @Transactional
    void getAllPermissionsByIsactiveIsInShouldWork() throws Exception {
        // Initialize the database
        permissionsRepository.saveAndFlush(permissions);

        // Get all the permissionsList where isactive in DEFAULT_ISACTIVE or UPDATED_ISACTIVE
        defaultPermissionsShouldBeFound("isactive.in=" + DEFAULT_ISACTIVE + "," + UPDATED_ISACTIVE);

        // Get all the permissionsList where isactive equals to UPDATED_ISACTIVE
        defaultPermissionsShouldNotBeFound("isactive.in=" + UPDATED_ISACTIVE);
    }

    @Test
    @Transactional
    void getAllPermissionsByIsactiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        permissionsRepository.saveAndFlush(permissions);

        // Get all the permissionsList where isactive is not null
        defaultPermissionsShouldBeFound("isactive.specified=true");

        // Get all the permissionsList where isactive is null
        defaultPermissionsShouldNotBeFound("isactive.specified=false");
    }

    @Test
    @Transactional
    void getAllPermissionsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        permissionsRepository.saveAndFlush(permissions);

        // Get all the permissionsList where createdat equals to DEFAULT_CREATEDAT
        defaultPermissionsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the permissionsList where createdat equals to UPDATED_CREATEDAT
        defaultPermissionsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllPermissionsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        permissionsRepository.saveAndFlush(permissions);

        // Get all the permissionsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultPermissionsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the permissionsList where createdat equals to UPDATED_CREATEDAT
        defaultPermissionsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllPermissionsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        permissionsRepository.saveAndFlush(permissions);

        // Get all the permissionsList where createdat is not null
        defaultPermissionsShouldBeFound("createdat.specified=true");

        // Get all the permissionsList where createdat is null
        defaultPermissionsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllPermissionsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        permissionsRepository.saveAndFlush(permissions);

        // Get all the permissionsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultPermissionsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the permissionsList where updatedat equals to UPDATED_UPDATEDAT
        defaultPermissionsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllPermissionsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        permissionsRepository.saveAndFlush(permissions);

        // Get all the permissionsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultPermissionsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the permissionsList where updatedat equals to UPDATED_UPDATEDAT
        defaultPermissionsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllPermissionsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        permissionsRepository.saveAndFlush(permissions);

        // Get all the permissionsList where updatedat is not null
        defaultPermissionsShouldBeFound("updatedat.specified=true");

        // Get all the permissionsList where updatedat is null
        defaultPermissionsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllPermissionsByRolepermissionsPermissionIsEqualToSomething() throws Exception {
        RolePermissions rolepermissionsPermission;
        if (TestUtil.findAll(em, RolePermissions.class).isEmpty()) {
            permissionsRepository.saveAndFlush(permissions);
            rolepermissionsPermission = RolePermissionsResourceIT.createEntity(em);
        } else {
            rolepermissionsPermission = TestUtil.findAll(em, RolePermissions.class).get(0);
        }
        em.persist(rolepermissionsPermission);
        em.flush();
        permissions.addRolepermissionsPermission(rolepermissionsPermission);
        permissionsRepository.saveAndFlush(permissions);
        Long rolepermissionsPermissionId = rolepermissionsPermission.getId();

        // Get all the permissionsList where rolepermissionsPermission equals to rolepermissionsPermissionId
        defaultPermissionsShouldBeFound("rolepermissionsPermissionId.equals=" + rolepermissionsPermissionId);

        // Get all the permissionsList where rolepermissionsPermission equals to (rolepermissionsPermissionId + 1)
        defaultPermissionsShouldNotBeFound("rolepermissionsPermissionId.equals=" + (rolepermissionsPermissionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPermissionsShouldBeFound(String filter) throws Exception {
        restPermissionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(permissions.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].groupName").value(hasItem(DEFAULT_GROUP_NAME)))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restPermissionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPermissionsShouldNotBeFound(String filter) throws Exception {
        restPermissionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPermissionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPermissions() throws Exception {
        // Get the permissions
        restPermissionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPermissions() throws Exception {
        // Initialize the database
        permissionsRepository.saveAndFlush(permissions);

        int databaseSizeBeforeUpdate = permissionsRepository.findAll().size();

        // Update the permissions
        Permissions updatedPermissions = permissionsRepository.findById(permissions.getId()).get();
        // Disconnect from session so that the updates on updatedPermissions are not directly saved in db
        em.detach(updatedPermissions);
        updatedPermissions
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .groupName(UPDATED_GROUP_NAME)
            .isactive(UPDATED_ISACTIVE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restPermissionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPermissions.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPermissions))
            )
            .andExpect(status().isOk());

        // Validate the Permissions in the database
        List<Permissions> permissionsList = permissionsRepository.findAll();
        assertThat(permissionsList).hasSize(databaseSizeBeforeUpdate);
        Permissions testPermissions = permissionsList.get(permissionsList.size() - 1);
        assertThat(testPermissions.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPermissions.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPermissions.getGroupName()).isEqualTo(UPDATED_GROUP_NAME);
        assertThat(testPermissions.getIsactive()).isEqualTo(UPDATED_ISACTIVE);
        assertThat(testPermissions.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testPermissions.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingPermissions() throws Exception {
        int databaseSizeBeforeUpdate = permissionsRepository.findAll().size();
        permissions.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPermissionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, permissions.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(permissions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Permissions in the database
        List<Permissions> permissionsList = permissionsRepository.findAll();
        assertThat(permissionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPermissions() throws Exception {
        int databaseSizeBeforeUpdate = permissionsRepository.findAll().size();
        permissions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPermissionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(permissions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Permissions in the database
        List<Permissions> permissionsList = permissionsRepository.findAll();
        assertThat(permissionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPermissions() throws Exception {
        int databaseSizeBeforeUpdate = permissionsRepository.findAll().size();
        permissions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPermissionsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(permissions))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Permissions in the database
        List<Permissions> permissionsList = permissionsRepository.findAll();
        assertThat(permissionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePermissionsWithPatch() throws Exception {
        // Initialize the database
        permissionsRepository.saveAndFlush(permissions);

        int databaseSizeBeforeUpdate = permissionsRepository.findAll().size();

        // Update the permissions using partial update
        Permissions partialUpdatedPermissions = new Permissions();
        partialUpdatedPermissions.setId(permissions.getId());

        partialUpdatedPermissions
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .groupName(UPDATED_GROUP_NAME)
            .isactive(UPDATED_ISACTIVE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restPermissionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPermissions.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPermissions))
            )
            .andExpect(status().isOk());

        // Validate the Permissions in the database
        List<Permissions> permissionsList = permissionsRepository.findAll();
        assertThat(permissionsList).hasSize(databaseSizeBeforeUpdate);
        Permissions testPermissions = permissionsList.get(permissionsList.size() - 1);
        assertThat(testPermissions.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPermissions.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPermissions.getGroupName()).isEqualTo(UPDATED_GROUP_NAME);
        assertThat(testPermissions.getIsactive()).isEqualTo(UPDATED_ISACTIVE);
        assertThat(testPermissions.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testPermissions.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdatePermissionsWithPatch() throws Exception {
        // Initialize the database
        permissionsRepository.saveAndFlush(permissions);

        int databaseSizeBeforeUpdate = permissionsRepository.findAll().size();

        // Update the permissions using partial update
        Permissions partialUpdatedPermissions = new Permissions();
        partialUpdatedPermissions.setId(permissions.getId());

        partialUpdatedPermissions
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .groupName(UPDATED_GROUP_NAME)
            .isactive(UPDATED_ISACTIVE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restPermissionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPermissions.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPermissions))
            )
            .andExpect(status().isOk());

        // Validate the Permissions in the database
        List<Permissions> permissionsList = permissionsRepository.findAll();
        assertThat(permissionsList).hasSize(databaseSizeBeforeUpdate);
        Permissions testPermissions = permissionsList.get(permissionsList.size() - 1);
        assertThat(testPermissions.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPermissions.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPermissions.getGroupName()).isEqualTo(UPDATED_GROUP_NAME);
        assertThat(testPermissions.getIsactive()).isEqualTo(UPDATED_ISACTIVE);
        assertThat(testPermissions.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testPermissions.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingPermissions() throws Exception {
        int databaseSizeBeforeUpdate = permissionsRepository.findAll().size();
        permissions.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPermissionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, permissions.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(permissions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Permissions in the database
        List<Permissions> permissionsList = permissionsRepository.findAll();
        assertThat(permissionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPermissions() throws Exception {
        int databaseSizeBeforeUpdate = permissionsRepository.findAll().size();
        permissions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPermissionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(permissions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Permissions in the database
        List<Permissions> permissionsList = permissionsRepository.findAll();
        assertThat(permissionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPermissions() throws Exception {
        int databaseSizeBeforeUpdate = permissionsRepository.findAll().size();
        permissions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPermissionsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(permissions))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Permissions in the database
        List<Permissions> permissionsList = permissionsRepository.findAll();
        assertThat(permissionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePermissions() throws Exception {
        // Initialize the database
        permissionsRepository.saveAndFlush(permissions);

        int databaseSizeBeforeDelete = permissionsRepository.findAll().size();

        // Delete the permissions
        restPermissionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, permissions.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Permissions> permissionsList = permissionsRepository.findAll();
        assertThat(permissionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
