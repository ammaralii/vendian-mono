package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Universities;
import com.venturedive.vendian_mono.repository.UniversitiesRepository;
import com.venturedive.vendian_mono.service.criteria.UniversitiesCriteria;
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
 * Integration tests for the {@link UniversitiesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UniversitiesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/universities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UniversitiesRepository universitiesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUniversitiesMockMvc;

    private Universities universities;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Universities createEntity(EntityManager em) {
        Universities universities = new Universities().name(DEFAULT_NAME).createdat(DEFAULT_CREATEDAT).updatedat(DEFAULT_UPDATEDAT);
        return universities;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Universities createUpdatedEntity(EntityManager em) {
        Universities universities = new Universities().name(UPDATED_NAME).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);
        return universities;
    }

    @BeforeEach
    public void initTest() {
        universities = createEntity(em);
    }

    @Test
    @Transactional
    void createUniversities() throws Exception {
        int databaseSizeBeforeCreate = universitiesRepository.findAll().size();
        // Create the Universities
        restUniversitiesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(universities))
            )
            .andExpect(status().isCreated());

        // Validate the Universities in the database
        List<Universities> universitiesList = universitiesRepository.findAll();
        assertThat(universitiesList).hasSize(databaseSizeBeforeCreate + 1);
        Universities testUniversities = universitiesList.get(universitiesList.size() - 1);
        assertThat(testUniversities.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUniversities.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testUniversities.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createUniversitiesWithExistingId() throws Exception {
        // Create the Universities with an existing ID
        universities.setId(1L);

        int databaseSizeBeforeCreate = universitiesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUniversitiesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(universities))
            )
            .andExpect(status().isBadRequest());

        // Validate the Universities in the database
        List<Universities> universitiesList = universitiesRepository.findAll();
        assertThat(universitiesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = universitiesRepository.findAll().size();
        // set the field null
        universities.setCreatedat(null);

        // Create the Universities, which fails.

        restUniversitiesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(universities))
            )
            .andExpect(status().isBadRequest());

        List<Universities> universitiesList = universitiesRepository.findAll();
        assertThat(universitiesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = universitiesRepository.findAll().size();
        // set the field null
        universities.setUpdatedat(null);

        // Create the Universities, which fails.

        restUniversitiesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(universities))
            )
            .andExpect(status().isBadRequest());

        List<Universities> universitiesList = universitiesRepository.findAll();
        assertThat(universitiesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUniversities() throws Exception {
        // Initialize the database
        universitiesRepository.saveAndFlush(universities);

        // Get all the universitiesList
        restUniversitiesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(universities.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getUniversities() throws Exception {
        // Initialize the database
        universitiesRepository.saveAndFlush(universities);

        // Get the universities
        restUniversitiesMockMvc
            .perform(get(ENTITY_API_URL_ID, universities.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(universities.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getUniversitiesByIdFiltering() throws Exception {
        // Initialize the database
        universitiesRepository.saveAndFlush(universities);

        Long id = universities.getId();

        defaultUniversitiesShouldBeFound("id.equals=" + id);
        defaultUniversitiesShouldNotBeFound("id.notEquals=" + id);

        defaultUniversitiesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUniversitiesShouldNotBeFound("id.greaterThan=" + id);

        defaultUniversitiesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUniversitiesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllUniversitiesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        universitiesRepository.saveAndFlush(universities);

        // Get all the universitiesList where name equals to DEFAULT_NAME
        defaultUniversitiesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the universitiesList where name equals to UPDATED_NAME
        defaultUniversitiesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllUniversitiesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        universitiesRepository.saveAndFlush(universities);

        // Get all the universitiesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultUniversitiesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the universitiesList where name equals to UPDATED_NAME
        defaultUniversitiesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllUniversitiesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        universitiesRepository.saveAndFlush(universities);

        // Get all the universitiesList where name is not null
        defaultUniversitiesShouldBeFound("name.specified=true");

        // Get all the universitiesList where name is null
        defaultUniversitiesShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllUniversitiesByNameContainsSomething() throws Exception {
        // Initialize the database
        universitiesRepository.saveAndFlush(universities);

        // Get all the universitiesList where name contains DEFAULT_NAME
        defaultUniversitiesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the universitiesList where name contains UPDATED_NAME
        defaultUniversitiesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllUniversitiesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        universitiesRepository.saveAndFlush(universities);

        // Get all the universitiesList where name does not contain DEFAULT_NAME
        defaultUniversitiesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the universitiesList where name does not contain UPDATED_NAME
        defaultUniversitiesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllUniversitiesByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        universitiesRepository.saveAndFlush(universities);

        // Get all the universitiesList where createdat equals to DEFAULT_CREATEDAT
        defaultUniversitiesShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the universitiesList where createdat equals to UPDATED_CREATEDAT
        defaultUniversitiesShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllUniversitiesByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        universitiesRepository.saveAndFlush(universities);

        // Get all the universitiesList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultUniversitiesShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the universitiesList where createdat equals to UPDATED_CREATEDAT
        defaultUniversitiesShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllUniversitiesByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        universitiesRepository.saveAndFlush(universities);

        // Get all the universitiesList where createdat is not null
        defaultUniversitiesShouldBeFound("createdat.specified=true");

        // Get all the universitiesList where createdat is null
        defaultUniversitiesShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllUniversitiesByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        universitiesRepository.saveAndFlush(universities);

        // Get all the universitiesList where updatedat equals to DEFAULT_UPDATEDAT
        defaultUniversitiesShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the universitiesList where updatedat equals to UPDATED_UPDATEDAT
        defaultUniversitiesShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllUniversitiesByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        universitiesRepository.saveAndFlush(universities);

        // Get all the universitiesList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultUniversitiesShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the universitiesList where updatedat equals to UPDATED_UPDATEDAT
        defaultUniversitiesShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllUniversitiesByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        universitiesRepository.saveAndFlush(universities);

        // Get all the universitiesList where updatedat is not null
        defaultUniversitiesShouldBeFound("updatedat.specified=true");

        // Get all the universitiesList where updatedat is null
        defaultUniversitiesShouldNotBeFound("updatedat.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUniversitiesShouldBeFound(String filter) throws Exception {
        restUniversitiesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(universities.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restUniversitiesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUniversitiesShouldNotBeFound(String filter) throws Exception {
        restUniversitiesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUniversitiesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingUniversities() throws Exception {
        // Get the universities
        restUniversitiesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUniversities() throws Exception {
        // Initialize the database
        universitiesRepository.saveAndFlush(universities);

        int databaseSizeBeforeUpdate = universitiesRepository.findAll().size();

        // Update the universities
        Universities updatedUniversities = universitiesRepository.findById(universities.getId()).get();
        // Disconnect from session so that the updates on updatedUniversities are not directly saved in db
        em.detach(updatedUniversities);
        updatedUniversities.name(UPDATED_NAME).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restUniversitiesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUniversities.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUniversities))
            )
            .andExpect(status().isOk());

        // Validate the Universities in the database
        List<Universities> universitiesList = universitiesRepository.findAll();
        assertThat(universitiesList).hasSize(databaseSizeBeforeUpdate);
        Universities testUniversities = universitiesList.get(universitiesList.size() - 1);
        assertThat(testUniversities.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUniversities.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testUniversities.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingUniversities() throws Exception {
        int databaseSizeBeforeUpdate = universitiesRepository.findAll().size();
        universities.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUniversitiesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, universities.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(universities))
            )
            .andExpect(status().isBadRequest());

        // Validate the Universities in the database
        List<Universities> universitiesList = universitiesRepository.findAll();
        assertThat(universitiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUniversities() throws Exception {
        int databaseSizeBeforeUpdate = universitiesRepository.findAll().size();
        universities.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUniversitiesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(universities))
            )
            .andExpect(status().isBadRequest());

        // Validate the Universities in the database
        List<Universities> universitiesList = universitiesRepository.findAll();
        assertThat(universitiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUniversities() throws Exception {
        int databaseSizeBeforeUpdate = universitiesRepository.findAll().size();
        universities.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUniversitiesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(universities))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Universities in the database
        List<Universities> universitiesList = universitiesRepository.findAll();
        assertThat(universitiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUniversitiesWithPatch() throws Exception {
        // Initialize the database
        universitiesRepository.saveAndFlush(universities);

        int databaseSizeBeforeUpdate = universitiesRepository.findAll().size();

        // Update the universities using partial update
        Universities partialUpdatedUniversities = new Universities();
        partialUpdatedUniversities.setId(universities.getId());

        partialUpdatedUniversities.name(UPDATED_NAME);

        restUniversitiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUniversities.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUniversities))
            )
            .andExpect(status().isOk());

        // Validate the Universities in the database
        List<Universities> universitiesList = universitiesRepository.findAll();
        assertThat(universitiesList).hasSize(databaseSizeBeforeUpdate);
        Universities testUniversities = universitiesList.get(universitiesList.size() - 1);
        assertThat(testUniversities.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUniversities.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testUniversities.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateUniversitiesWithPatch() throws Exception {
        // Initialize the database
        universitiesRepository.saveAndFlush(universities);

        int databaseSizeBeforeUpdate = universitiesRepository.findAll().size();

        // Update the universities using partial update
        Universities partialUpdatedUniversities = new Universities();
        partialUpdatedUniversities.setId(universities.getId());

        partialUpdatedUniversities.name(UPDATED_NAME).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restUniversitiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUniversities.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUniversities))
            )
            .andExpect(status().isOk());

        // Validate the Universities in the database
        List<Universities> universitiesList = universitiesRepository.findAll();
        assertThat(universitiesList).hasSize(databaseSizeBeforeUpdate);
        Universities testUniversities = universitiesList.get(universitiesList.size() - 1);
        assertThat(testUniversities.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUniversities.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testUniversities.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingUniversities() throws Exception {
        int databaseSizeBeforeUpdate = universitiesRepository.findAll().size();
        universities.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUniversitiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, universities.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(universities))
            )
            .andExpect(status().isBadRequest());

        // Validate the Universities in the database
        List<Universities> universitiesList = universitiesRepository.findAll();
        assertThat(universitiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUniversities() throws Exception {
        int databaseSizeBeforeUpdate = universitiesRepository.findAll().size();
        universities.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUniversitiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(universities))
            )
            .andExpect(status().isBadRequest());

        // Validate the Universities in the database
        List<Universities> universitiesList = universitiesRepository.findAll();
        assertThat(universitiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUniversities() throws Exception {
        int databaseSizeBeforeUpdate = universitiesRepository.findAll().size();
        universities.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUniversitiesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(universities))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Universities in the database
        List<Universities> universitiesList = universitiesRepository.findAll();
        assertThat(universitiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUniversities() throws Exception {
        // Initialize the database
        universitiesRepository.saveAndFlush(universities);

        int databaseSizeBeforeDelete = universitiesRepository.findAll().size();

        // Delete the universities
        restUniversitiesMockMvc
            .perform(delete(ENTITY_API_URL_ID, universities.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Universities> universitiesList = universitiesRepository.findAll();
        assertThat(universitiesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
