package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.BloodGroups;
import com.venturedive.vendian_mono.repository.BloodGroupsRepository;
import com.venturedive.vendian_mono.service.criteria.BloodGroupsCriteria;
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
 * Integration tests for the {@link BloodGroupsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BloodGroupsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/blood-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BloodGroupsRepository bloodGroupsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBloodGroupsMockMvc;

    private BloodGroups bloodGroups;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BloodGroups createEntity(EntityManager em) {
        BloodGroups bloodGroups = new BloodGroups().name(DEFAULT_NAME).createdat(DEFAULT_CREATEDAT).updatedat(DEFAULT_UPDATEDAT);
        return bloodGroups;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BloodGroups createUpdatedEntity(EntityManager em) {
        BloodGroups bloodGroups = new BloodGroups().name(UPDATED_NAME).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);
        return bloodGroups;
    }

    @BeforeEach
    public void initTest() {
        bloodGroups = createEntity(em);
    }

    @Test
    @Transactional
    void createBloodGroups() throws Exception {
        int databaseSizeBeforeCreate = bloodGroupsRepository.findAll().size();
        // Create the BloodGroups
        restBloodGroupsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bloodGroups))
            )
            .andExpect(status().isCreated());

        // Validate the BloodGroups in the database
        List<BloodGroups> bloodGroupsList = bloodGroupsRepository.findAll();
        assertThat(bloodGroupsList).hasSize(databaseSizeBeforeCreate + 1);
        BloodGroups testBloodGroups = bloodGroupsList.get(bloodGroupsList.size() - 1);
        assertThat(testBloodGroups.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBloodGroups.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testBloodGroups.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createBloodGroupsWithExistingId() throws Exception {
        // Create the BloodGroups with an existing ID
        bloodGroups.setId(1L);

        int databaseSizeBeforeCreate = bloodGroupsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBloodGroupsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bloodGroups))
            )
            .andExpect(status().isBadRequest());

        // Validate the BloodGroups in the database
        List<BloodGroups> bloodGroupsList = bloodGroupsRepository.findAll();
        assertThat(bloodGroupsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = bloodGroupsRepository.findAll().size();
        // set the field null
        bloodGroups.setCreatedat(null);

        // Create the BloodGroups, which fails.

        restBloodGroupsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bloodGroups))
            )
            .andExpect(status().isBadRequest());

        List<BloodGroups> bloodGroupsList = bloodGroupsRepository.findAll();
        assertThat(bloodGroupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = bloodGroupsRepository.findAll().size();
        // set the field null
        bloodGroups.setUpdatedat(null);

        // Create the BloodGroups, which fails.

        restBloodGroupsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bloodGroups))
            )
            .andExpect(status().isBadRequest());

        List<BloodGroups> bloodGroupsList = bloodGroupsRepository.findAll();
        assertThat(bloodGroupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBloodGroups() throws Exception {
        // Initialize the database
        bloodGroupsRepository.saveAndFlush(bloodGroups);

        // Get all the bloodGroupsList
        restBloodGroupsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bloodGroups.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getBloodGroups() throws Exception {
        // Initialize the database
        bloodGroupsRepository.saveAndFlush(bloodGroups);

        // Get the bloodGroups
        restBloodGroupsMockMvc
            .perform(get(ENTITY_API_URL_ID, bloodGroups.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bloodGroups.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getBloodGroupsByIdFiltering() throws Exception {
        // Initialize the database
        bloodGroupsRepository.saveAndFlush(bloodGroups);

        Long id = bloodGroups.getId();

        defaultBloodGroupsShouldBeFound("id.equals=" + id);
        defaultBloodGroupsShouldNotBeFound("id.notEquals=" + id);

        defaultBloodGroupsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBloodGroupsShouldNotBeFound("id.greaterThan=" + id);

        defaultBloodGroupsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBloodGroupsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBloodGroupsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodGroupsRepository.saveAndFlush(bloodGroups);

        // Get all the bloodGroupsList where name equals to DEFAULT_NAME
        defaultBloodGroupsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the bloodGroupsList where name equals to UPDATED_NAME
        defaultBloodGroupsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBloodGroupsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        bloodGroupsRepository.saveAndFlush(bloodGroups);

        // Get all the bloodGroupsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultBloodGroupsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the bloodGroupsList where name equals to UPDATED_NAME
        defaultBloodGroupsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBloodGroupsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodGroupsRepository.saveAndFlush(bloodGroups);

        // Get all the bloodGroupsList where name is not null
        defaultBloodGroupsShouldBeFound("name.specified=true");

        // Get all the bloodGroupsList where name is null
        defaultBloodGroupsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllBloodGroupsByNameContainsSomething() throws Exception {
        // Initialize the database
        bloodGroupsRepository.saveAndFlush(bloodGroups);

        // Get all the bloodGroupsList where name contains DEFAULT_NAME
        defaultBloodGroupsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the bloodGroupsList where name contains UPDATED_NAME
        defaultBloodGroupsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBloodGroupsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        bloodGroupsRepository.saveAndFlush(bloodGroups);

        // Get all the bloodGroupsList where name does not contain DEFAULT_NAME
        defaultBloodGroupsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the bloodGroupsList where name does not contain UPDATED_NAME
        defaultBloodGroupsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBloodGroupsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodGroupsRepository.saveAndFlush(bloodGroups);

        // Get all the bloodGroupsList where createdat equals to DEFAULT_CREATEDAT
        defaultBloodGroupsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the bloodGroupsList where createdat equals to UPDATED_CREATEDAT
        defaultBloodGroupsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllBloodGroupsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        bloodGroupsRepository.saveAndFlush(bloodGroups);

        // Get all the bloodGroupsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultBloodGroupsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the bloodGroupsList where createdat equals to UPDATED_CREATEDAT
        defaultBloodGroupsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllBloodGroupsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodGroupsRepository.saveAndFlush(bloodGroups);

        // Get all the bloodGroupsList where createdat is not null
        defaultBloodGroupsShouldBeFound("createdat.specified=true");

        // Get all the bloodGroupsList where createdat is null
        defaultBloodGroupsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllBloodGroupsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodGroupsRepository.saveAndFlush(bloodGroups);

        // Get all the bloodGroupsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultBloodGroupsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the bloodGroupsList where updatedat equals to UPDATED_UPDATEDAT
        defaultBloodGroupsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllBloodGroupsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        bloodGroupsRepository.saveAndFlush(bloodGroups);

        // Get all the bloodGroupsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultBloodGroupsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the bloodGroupsList where updatedat equals to UPDATED_UPDATEDAT
        defaultBloodGroupsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllBloodGroupsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodGroupsRepository.saveAndFlush(bloodGroups);

        // Get all the bloodGroupsList where updatedat is not null
        defaultBloodGroupsShouldBeFound("updatedat.specified=true");

        // Get all the bloodGroupsList where updatedat is null
        defaultBloodGroupsShouldNotBeFound("updatedat.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBloodGroupsShouldBeFound(String filter) throws Exception {
        restBloodGroupsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bloodGroups.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restBloodGroupsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBloodGroupsShouldNotBeFound(String filter) throws Exception {
        restBloodGroupsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBloodGroupsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBloodGroups() throws Exception {
        // Get the bloodGroups
        restBloodGroupsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBloodGroups() throws Exception {
        // Initialize the database
        bloodGroupsRepository.saveAndFlush(bloodGroups);

        int databaseSizeBeforeUpdate = bloodGroupsRepository.findAll().size();

        // Update the bloodGroups
        BloodGroups updatedBloodGroups = bloodGroupsRepository.findById(bloodGroups.getId()).get();
        // Disconnect from session so that the updates on updatedBloodGroups are not directly saved in db
        em.detach(updatedBloodGroups);
        updatedBloodGroups.name(UPDATED_NAME).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restBloodGroupsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBloodGroups.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBloodGroups))
            )
            .andExpect(status().isOk());

        // Validate the BloodGroups in the database
        List<BloodGroups> bloodGroupsList = bloodGroupsRepository.findAll();
        assertThat(bloodGroupsList).hasSize(databaseSizeBeforeUpdate);
        BloodGroups testBloodGroups = bloodGroupsList.get(bloodGroupsList.size() - 1);
        assertThat(testBloodGroups.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBloodGroups.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testBloodGroups.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingBloodGroups() throws Exception {
        int databaseSizeBeforeUpdate = bloodGroupsRepository.findAll().size();
        bloodGroups.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBloodGroupsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bloodGroups.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bloodGroups))
            )
            .andExpect(status().isBadRequest());

        // Validate the BloodGroups in the database
        List<BloodGroups> bloodGroupsList = bloodGroupsRepository.findAll();
        assertThat(bloodGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBloodGroups() throws Exception {
        int databaseSizeBeforeUpdate = bloodGroupsRepository.findAll().size();
        bloodGroups.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBloodGroupsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bloodGroups))
            )
            .andExpect(status().isBadRequest());

        // Validate the BloodGroups in the database
        List<BloodGroups> bloodGroupsList = bloodGroupsRepository.findAll();
        assertThat(bloodGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBloodGroups() throws Exception {
        int databaseSizeBeforeUpdate = bloodGroupsRepository.findAll().size();
        bloodGroups.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBloodGroupsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bloodGroups))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BloodGroups in the database
        List<BloodGroups> bloodGroupsList = bloodGroupsRepository.findAll();
        assertThat(bloodGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBloodGroupsWithPatch() throws Exception {
        // Initialize the database
        bloodGroupsRepository.saveAndFlush(bloodGroups);

        int databaseSizeBeforeUpdate = bloodGroupsRepository.findAll().size();

        // Update the bloodGroups using partial update
        BloodGroups partialUpdatedBloodGroups = new BloodGroups();
        partialUpdatedBloodGroups.setId(bloodGroups.getId());

        partialUpdatedBloodGroups.updatedat(UPDATED_UPDATEDAT);

        restBloodGroupsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBloodGroups.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBloodGroups))
            )
            .andExpect(status().isOk());

        // Validate the BloodGroups in the database
        List<BloodGroups> bloodGroupsList = bloodGroupsRepository.findAll();
        assertThat(bloodGroupsList).hasSize(databaseSizeBeforeUpdate);
        BloodGroups testBloodGroups = bloodGroupsList.get(bloodGroupsList.size() - 1);
        assertThat(testBloodGroups.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBloodGroups.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testBloodGroups.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateBloodGroupsWithPatch() throws Exception {
        // Initialize the database
        bloodGroupsRepository.saveAndFlush(bloodGroups);

        int databaseSizeBeforeUpdate = bloodGroupsRepository.findAll().size();

        // Update the bloodGroups using partial update
        BloodGroups partialUpdatedBloodGroups = new BloodGroups();
        partialUpdatedBloodGroups.setId(bloodGroups.getId());

        partialUpdatedBloodGroups.name(UPDATED_NAME).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restBloodGroupsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBloodGroups.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBloodGroups))
            )
            .andExpect(status().isOk());

        // Validate the BloodGroups in the database
        List<BloodGroups> bloodGroupsList = bloodGroupsRepository.findAll();
        assertThat(bloodGroupsList).hasSize(databaseSizeBeforeUpdate);
        BloodGroups testBloodGroups = bloodGroupsList.get(bloodGroupsList.size() - 1);
        assertThat(testBloodGroups.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBloodGroups.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testBloodGroups.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingBloodGroups() throws Exception {
        int databaseSizeBeforeUpdate = bloodGroupsRepository.findAll().size();
        bloodGroups.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBloodGroupsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bloodGroups.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bloodGroups))
            )
            .andExpect(status().isBadRequest());

        // Validate the BloodGroups in the database
        List<BloodGroups> bloodGroupsList = bloodGroupsRepository.findAll();
        assertThat(bloodGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBloodGroups() throws Exception {
        int databaseSizeBeforeUpdate = bloodGroupsRepository.findAll().size();
        bloodGroups.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBloodGroupsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bloodGroups))
            )
            .andExpect(status().isBadRequest());

        // Validate the BloodGroups in the database
        List<BloodGroups> bloodGroupsList = bloodGroupsRepository.findAll();
        assertThat(bloodGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBloodGroups() throws Exception {
        int databaseSizeBeforeUpdate = bloodGroupsRepository.findAll().size();
        bloodGroups.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBloodGroupsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bloodGroups))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BloodGroups in the database
        List<BloodGroups> bloodGroupsList = bloodGroupsRepository.findAll();
        assertThat(bloodGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBloodGroups() throws Exception {
        // Initialize the database
        bloodGroupsRepository.saveAndFlush(bloodGroups);

        int databaseSizeBeforeDelete = bloodGroupsRepository.findAll().size();

        // Delete the bloodGroups
        restBloodGroupsMockMvc
            .perform(delete(ENTITY_API_URL_ID, bloodGroups.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BloodGroups> bloodGroupsList = bloodGroupsRepository.findAll();
        assertThat(bloodGroupsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
