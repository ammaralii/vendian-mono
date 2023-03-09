package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Religions;
import com.venturedive.vendian_mono.repository.ReligionsRepository;
import com.venturedive.vendian_mono.service.criteria.ReligionsCriteria;
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
 * Integration tests for the {@link ReligionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReligionsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/religions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ReligionsRepository religionsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReligionsMockMvc;

    private Religions religions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Religions createEntity(EntityManager em) {
        Religions religions = new Religions().name(DEFAULT_NAME).createdat(DEFAULT_CREATEDAT).updatedat(DEFAULT_UPDATEDAT);
        return religions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Religions createUpdatedEntity(EntityManager em) {
        Religions religions = new Religions().name(UPDATED_NAME).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);
        return religions;
    }

    @BeforeEach
    public void initTest() {
        religions = createEntity(em);
    }

    @Test
    @Transactional
    void createReligions() throws Exception {
        int databaseSizeBeforeCreate = religionsRepository.findAll().size();
        // Create the Religions
        restReligionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(religions))
            )
            .andExpect(status().isCreated());

        // Validate the Religions in the database
        List<Religions> religionsList = religionsRepository.findAll();
        assertThat(religionsList).hasSize(databaseSizeBeforeCreate + 1);
        Religions testReligions = religionsList.get(religionsList.size() - 1);
        assertThat(testReligions.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testReligions.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testReligions.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createReligionsWithExistingId() throws Exception {
        // Create the Religions with an existing ID
        religions.setId(1L);

        int databaseSizeBeforeCreate = religionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReligionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(religions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Religions in the database
        List<Religions> religionsList = religionsRepository.findAll();
        assertThat(religionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = religionsRepository.findAll().size();
        // set the field null
        religions.setCreatedat(null);

        // Create the Religions, which fails.

        restReligionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(religions))
            )
            .andExpect(status().isBadRequest());

        List<Religions> religionsList = religionsRepository.findAll();
        assertThat(religionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = religionsRepository.findAll().size();
        // set the field null
        religions.setUpdatedat(null);

        // Create the Religions, which fails.

        restReligionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(religions))
            )
            .andExpect(status().isBadRequest());

        List<Religions> religionsList = religionsRepository.findAll();
        assertThat(religionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllReligions() throws Exception {
        // Initialize the database
        religionsRepository.saveAndFlush(religions);

        // Get all the religionsList
        restReligionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(religions.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getReligions() throws Exception {
        // Initialize the database
        religionsRepository.saveAndFlush(religions);

        // Get the religions
        restReligionsMockMvc
            .perform(get(ENTITY_API_URL_ID, religions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(religions.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getReligionsByIdFiltering() throws Exception {
        // Initialize the database
        religionsRepository.saveAndFlush(religions);

        Long id = religions.getId();

        defaultReligionsShouldBeFound("id.equals=" + id);
        defaultReligionsShouldNotBeFound("id.notEquals=" + id);

        defaultReligionsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultReligionsShouldNotBeFound("id.greaterThan=" + id);

        defaultReligionsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultReligionsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllReligionsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        religionsRepository.saveAndFlush(religions);

        // Get all the religionsList where name equals to DEFAULT_NAME
        defaultReligionsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the religionsList where name equals to UPDATED_NAME
        defaultReligionsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllReligionsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        religionsRepository.saveAndFlush(religions);

        // Get all the religionsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultReligionsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the religionsList where name equals to UPDATED_NAME
        defaultReligionsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllReligionsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        religionsRepository.saveAndFlush(religions);

        // Get all the religionsList where name is not null
        defaultReligionsShouldBeFound("name.specified=true");

        // Get all the religionsList where name is null
        defaultReligionsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllReligionsByNameContainsSomething() throws Exception {
        // Initialize the database
        religionsRepository.saveAndFlush(religions);

        // Get all the religionsList where name contains DEFAULT_NAME
        defaultReligionsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the religionsList where name contains UPDATED_NAME
        defaultReligionsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllReligionsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        religionsRepository.saveAndFlush(religions);

        // Get all the religionsList where name does not contain DEFAULT_NAME
        defaultReligionsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the religionsList where name does not contain UPDATED_NAME
        defaultReligionsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllReligionsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        religionsRepository.saveAndFlush(religions);

        // Get all the religionsList where createdat equals to DEFAULT_CREATEDAT
        defaultReligionsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the religionsList where createdat equals to UPDATED_CREATEDAT
        defaultReligionsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllReligionsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        religionsRepository.saveAndFlush(religions);

        // Get all the religionsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultReligionsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the religionsList where createdat equals to UPDATED_CREATEDAT
        defaultReligionsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllReligionsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        religionsRepository.saveAndFlush(religions);

        // Get all the religionsList where createdat is not null
        defaultReligionsShouldBeFound("createdat.specified=true");

        // Get all the religionsList where createdat is null
        defaultReligionsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllReligionsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        religionsRepository.saveAndFlush(religions);

        // Get all the religionsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultReligionsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the religionsList where updatedat equals to UPDATED_UPDATEDAT
        defaultReligionsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllReligionsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        religionsRepository.saveAndFlush(religions);

        // Get all the religionsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultReligionsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the religionsList where updatedat equals to UPDATED_UPDATEDAT
        defaultReligionsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllReligionsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        religionsRepository.saveAndFlush(religions);

        // Get all the religionsList where updatedat is not null
        defaultReligionsShouldBeFound("updatedat.specified=true");

        // Get all the religionsList where updatedat is null
        defaultReligionsShouldNotBeFound("updatedat.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultReligionsShouldBeFound(String filter) throws Exception {
        restReligionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(religions.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restReligionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultReligionsShouldNotBeFound(String filter) throws Exception {
        restReligionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restReligionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingReligions() throws Exception {
        // Get the religions
        restReligionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingReligions() throws Exception {
        // Initialize the database
        religionsRepository.saveAndFlush(religions);

        int databaseSizeBeforeUpdate = religionsRepository.findAll().size();

        // Update the religions
        Religions updatedReligions = religionsRepository.findById(religions.getId()).get();
        // Disconnect from session so that the updates on updatedReligions are not directly saved in db
        em.detach(updatedReligions);
        updatedReligions.name(UPDATED_NAME).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restReligionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedReligions.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedReligions))
            )
            .andExpect(status().isOk());

        // Validate the Religions in the database
        List<Religions> religionsList = religionsRepository.findAll();
        assertThat(religionsList).hasSize(databaseSizeBeforeUpdate);
        Religions testReligions = religionsList.get(religionsList.size() - 1);
        assertThat(testReligions.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testReligions.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testReligions.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingReligions() throws Exception {
        int databaseSizeBeforeUpdate = religionsRepository.findAll().size();
        religions.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReligionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, religions.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(religions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Religions in the database
        List<Religions> religionsList = religionsRepository.findAll();
        assertThat(religionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReligions() throws Exception {
        int databaseSizeBeforeUpdate = religionsRepository.findAll().size();
        religions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReligionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(religions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Religions in the database
        List<Religions> religionsList = religionsRepository.findAll();
        assertThat(religionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReligions() throws Exception {
        int databaseSizeBeforeUpdate = religionsRepository.findAll().size();
        religions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReligionsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(religions))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Religions in the database
        List<Religions> religionsList = religionsRepository.findAll();
        assertThat(religionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReligionsWithPatch() throws Exception {
        // Initialize the database
        religionsRepository.saveAndFlush(religions);

        int databaseSizeBeforeUpdate = religionsRepository.findAll().size();

        // Update the religions using partial update
        Religions partialUpdatedReligions = new Religions();
        partialUpdatedReligions.setId(religions.getId());

        partialUpdatedReligions.createdat(UPDATED_CREATEDAT);

        restReligionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReligions.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReligions))
            )
            .andExpect(status().isOk());

        // Validate the Religions in the database
        List<Religions> religionsList = religionsRepository.findAll();
        assertThat(religionsList).hasSize(databaseSizeBeforeUpdate);
        Religions testReligions = religionsList.get(religionsList.size() - 1);
        assertThat(testReligions.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testReligions.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testReligions.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateReligionsWithPatch() throws Exception {
        // Initialize the database
        religionsRepository.saveAndFlush(religions);

        int databaseSizeBeforeUpdate = religionsRepository.findAll().size();

        // Update the religions using partial update
        Religions partialUpdatedReligions = new Religions();
        partialUpdatedReligions.setId(religions.getId());

        partialUpdatedReligions.name(UPDATED_NAME).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restReligionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReligions.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReligions))
            )
            .andExpect(status().isOk());

        // Validate the Religions in the database
        List<Religions> religionsList = religionsRepository.findAll();
        assertThat(religionsList).hasSize(databaseSizeBeforeUpdate);
        Religions testReligions = religionsList.get(religionsList.size() - 1);
        assertThat(testReligions.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testReligions.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testReligions.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingReligions() throws Exception {
        int databaseSizeBeforeUpdate = religionsRepository.findAll().size();
        religions.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReligionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, religions.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(religions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Religions in the database
        List<Religions> religionsList = religionsRepository.findAll();
        assertThat(religionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReligions() throws Exception {
        int databaseSizeBeforeUpdate = religionsRepository.findAll().size();
        religions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReligionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(religions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Religions in the database
        List<Religions> religionsList = religionsRepository.findAll();
        assertThat(religionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReligions() throws Exception {
        int databaseSizeBeforeUpdate = religionsRepository.findAll().size();
        religions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReligionsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(religions))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Religions in the database
        List<Religions> religionsList = religionsRepository.findAll();
        assertThat(religionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReligions() throws Exception {
        // Initialize the database
        religionsRepository.saveAndFlush(religions);

        int databaseSizeBeforeDelete = religionsRepository.findAll().size();

        // Delete the religions
        restReligionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, religions.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Religions> religionsList = religionsRepository.findAll();
        assertThat(religionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
