package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.MaritalStatuses;
import com.venturedive.vendian_mono.repository.MaritalStatusesRepository;
import com.venturedive.vendian_mono.service.criteria.MaritalStatusesCriteria;
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
 * Integration tests for the {@link MaritalStatusesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MaritalStatusesResourceIT {

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/marital-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MaritalStatusesRepository maritalStatusesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMaritalStatusesMockMvc;

    private MaritalStatuses maritalStatuses;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MaritalStatuses createEntity(EntityManager em) {
        MaritalStatuses maritalStatuses = new MaritalStatuses()
            .status(DEFAULT_STATUS)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        return maritalStatuses;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MaritalStatuses createUpdatedEntity(EntityManager em) {
        MaritalStatuses maritalStatuses = new MaritalStatuses()
            .status(UPDATED_STATUS)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        return maritalStatuses;
    }

    @BeforeEach
    public void initTest() {
        maritalStatuses = createEntity(em);
    }

    @Test
    @Transactional
    void createMaritalStatuses() throws Exception {
        int databaseSizeBeforeCreate = maritalStatusesRepository.findAll().size();
        // Create the MaritalStatuses
        restMaritalStatusesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(maritalStatuses))
            )
            .andExpect(status().isCreated());

        // Validate the MaritalStatuses in the database
        List<MaritalStatuses> maritalStatusesList = maritalStatusesRepository.findAll();
        assertThat(maritalStatusesList).hasSize(databaseSizeBeforeCreate + 1);
        MaritalStatuses testMaritalStatuses = maritalStatusesList.get(maritalStatusesList.size() - 1);
        assertThat(testMaritalStatuses.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMaritalStatuses.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testMaritalStatuses.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createMaritalStatusesWithExistingId() throws Exception {
        // Create the MaritalStatuses with an existing ID
        maritalStatuses.setId(1L);

        int databaseSizeBeforeCreate = maritalStatusesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaritalStatusesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(maritalStatuses))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaritalStatuses in the database
        List<MaritalStatuses> maritalStatusesList = maritalStatusesRepository.findAll();
        assertThat(maritalStatusesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = maritalStatusesRepository.findAll().size();
        // set the field null
        maritalStatuses.setCreatedat(null);

        // Create the MaritalStatuses, which fails.

        restMaritalStatusesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(maritalStatuses))
            )
            .andExpect(status().isBadRequest());

        List<MaritalStatuses> maritalStatusesList = maritalStatusesRepository.findAll();
        assertThat(maritalStatusesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = maritalStatusesRepository.findAll().size();
        // set the field null
        maritalStatuses.setUpdatedat(null);

        // Create the MaritalStatuses, which fails.

        restMaritalStatusesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(maritalStatuses))
            )
            .andExpect(status().isBadRequest());

        List<MaritalStatuses> maritalStatusesList = maritalStatusesRepository.findAll();
        assertThat(maritalStatusesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMaritalStatuses() throws Exception {
        // Initialize the database
        maritalStatusesRepository.saveAndFlush(maritalStatuses);

        // Get all the maritalStatusesList
        restMaritalStatusesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(maritalStatuses.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getMaritalStatuses() throws Exception {
        // Initialize the database
        maritalStatusesRepository.saveAndFlush(maritalStatuses);

        // Get the maritalStatuses
        restMaritalStatusesMockMvc
            .perform(get(ENTITY_API_URL_ID, maritalStatuses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(maritalStatuses.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getMaritalStatusesByIdFiltering() throws Exception {
        // Initialize the database
        maritalStatusesRepository.saveAndFlush(maritalStatuses);

        Long id = maritalStatuses.getId();

        defaultMaritalStatusesShouldBeFound("id.equals=" + id);
        defaultMaritalStatusesShouldNotBeFound("id.notEquals=" + id);

        defaultMaritalStatusesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMaritalStatusesShouldNotBeFound("id.greaterThan=" + id);

        defaultMaritalStatusesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMaritalStatusesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMaritalStatusesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        maritalStatusesRepository.saveAndFlush(maritalStatuses);

        // Get all the maritalStatusesList where status equals to DEFAULT_STATUS
        defaultMaritalStatusesShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the maritalStatusesList where status equals to UPDATED_STATUS
        defaultMaritalStatusesShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllMaritalStatusesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        maritalStatusesRepository.saveAndFlush(maritalStatuses);

        // Get all the maritalStatusesList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultMaritalStatusesShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the maritalStatusesList where status equals to UPDATED_STATUS
        defaultMaritalStatusesShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllMaritalStatusesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        maritalStatusesRepository.saveAndFlush(maritalStatuses);

        // Get all the maritalStatusesList where status is not null
        defaultMaritalStatusesShouldBeFound("status.specified=true");

        // Get all the maritalStatusesList where status is null
        defaultMaritalStatusesShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllMaritalStatusesByStatusContainsSomething() throws Exception {
        // Initialize the database
        maritalStatusesRepository.saveAndFlush(maritalStatuses);

        // Get all the maritalStatusesList where status contains DEFAULT_STATUS
        defaultMaritalStatusesShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the maritalStatusesList where status contains UPDATED_STATUS
        defaultMaritalStatusesShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllMaritalStatusesByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        maritalStatusesRepository.saveAndFlush(maritalStatuses);

        // Get all the maritalStatusesList where status does not contain DEFAULT_STATUS
        defaultMaritalStatusesShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the maritalStatusesList where status does not contain UPDATED_STATUS
        defaultMaritalStatusesShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllMaritalStatusesByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        maritalStatusesRepository.saveAndFlush(maritalStatuses);

        // Get all the maritalStatusesList where createdat equals to DEFAULT_CREATEDAT
        defaultMaritalStatusesShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the maritalStatusesList where createdat equals to UPDATED_CREATEDAT
        defaultMaritalStatusesShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllMaritalStatusesByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        maritalStatusesRepository.saveAndFlush(maritalStatuses);

        // Get all the maritalStatusesList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultMaritalStatusesShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the maritalStatusesList where createdat equals to UPDATED_CREATEDAT
        defaultMaritalStatusesShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllMaritalStatusesByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        maritalStatusesRepository.saveAndFlush(maritalStatuses);

        // Get all the maritalStatusesList where createdat is not null
        defaultMaritalStatusesShouldBeFound("createdat.specified=true");

        // Get all the maritalStatusesList where createdat is null
        defaultMaritalStatusesShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllMaritalStatusesByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        maritalStatusesRepository.saveAndFlush(maritalStatuses);

        // Get all the maritalStatusesList where updatedat equals to DEFAULT_UPDATEDAT
        defaultMaritalStatusesShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the maritalStatusesList where updatedat equals to UPDATED_UPDATEDAT
        defaultMaritalStatusesShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllMaritalStatusesByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        maritalStatusesRepository.saveAndFlush(maritalStatuses);

        // Get all the maritalStatusesList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultMaritalStatusesShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the maritalStatusesList where updatedat equals to UPDATED_UPDATEDAT
        defaultMaritalStatusesShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllMaritalStatusesByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        maritalStatusesRepository.saveAndFlush(maritalStatuses);

        // Get all the maritalStatusesList where updatedat is not null
        defaultMaritalStatusesShouldBeFound("updatedat.specified=true");

        // Get all the maritalStatusesList where updatedat is null
        defaultMaritalStatusesShouldNotBeFound("updatedat.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMaritalStatusesShouldBeFound(String filter) throws Exception {
        restMaritalStatusesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(maritalStatuses.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restMaritalStatusesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMaritalStatusesShouldNotBeFound(String filter) throws Exception {
        restMaritalStatusesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMaritalStatusesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMaritalStatuses() throws Exception {
        // Get the maritalStatuses
        restMaritalStatusesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMaritalStatuses() throws Exception {
        // Initialize the database
        maritalStatusesRepository.saveAndFlush(maritalStatuses);

        int databaseSizeBeforeUpdate = maritalStatusesRepository.findAll().size();

        // Update the maritalStatuses
        MaritalStatuses updatedMaritalStatuses = maritalStatusesRepository.findById(maritalStatuses.getId()).get();
        // Disconnect from session so that the updates on updatedMaritalStatuses are not directly saved in db
        em.detach(updatedMaritalStatuses);
        updatedMaritalStatuses.status(UPDATED_STATUS).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restMaritalStatusesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMaritalStatuses.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMaritalStatuses))
            )
            .andExpect(status().isOk());

        // Validate the MaritalStatuses in the database
        List<MaritalStatuses> maritalStatusesList = maritalStatusesRepository.findAll();
        assertThat(maritalStatusesList).hasSize(databaseSizeBeforeUpdate);
        MaritalStatuses testMaritalStatuses = maritalStatusesList.get(maritalStatusesList.size() - 1);
        assertThat(testMaritalStatuses.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMaritalStatuses.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testMaritalStatuses.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingMaritalStatuses() throws Exception {
        int databaseSizeBeforeUpdate = maritalStatusesRepository.findAll().size();
        maritalStatuses.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaritalStatusesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, maritalStatuses.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(maritalStatuses))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaritalStatuses in the database
        List<MaritalStatuses> maritalStatusesList = maritalStatusesRepository.findAll();
        assertThat(maritalStatusesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMaritalStatuses() throws Exception {
        int databaseSizeBeforeUpdate = maritalStatusesRepository.findAll().size();
        maritalStatuses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaritalStatusesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(maritalStatuses))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaritalStatuses in the database
        List<MaritalStatuses> maritalStatusesList = maritalStatusesRepository.findAll();
        assertThat(maritalStatusesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMaritalStatuses() throws Exception {
        int databaseSizeBeforeUpdate = maritalStatusesRepository.findAll().size();
        maritalStatuses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaritalStatusesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(maritalStatuses))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MaritalStatuses in the database
        List<MaritalStatuses> maritalStatusesList = maritalStatusesRepository.findAll();
        assertThat(maritalStatusesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMaritalStatusesWithPatch() throws Exception {
        // Initialize the database
        maritalStatusesRepository.saveAndFlush(maritalStatuses);

        int databaseSizeBeforeUpdate = maritalStatusesRepository.findAll().size();

        // Update the maritalStatuses using partial update
        MaritalStatuses partialUpdatedMaritalStatuses = new MaritalStatuses();
        partialUpdatedMaritalStatuses.setId(maritalStatuses.getId());

        partialUpdatedMaritalStatuses.createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restMaritalStatusesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMaritalStatuses.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMaritalStatuses))
            )
            .andExpect(status().isOk());

        // Validate the MaritalStatuses in the database
        List<MaritalStatuses> maritalStatusesList = maritalStatusesRepository.findAll();
        assertThat(maritalStatusesList).hasSize(databaseSizeBeforeUpdate);
        MaritalStatuses testMaritalStatuses = maritalStatusesList.get(maritalStatusesList.size() - 1);
        assertThat(testMaritalStatuses.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMaritalStatuses.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testMaritalStatuses.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateMaritalStatusesWithPatch() throws Exception {
        // Initialize the database
        maritalStatusesRepository.saveAndFlush(maritalStatuses);

        int databaseSizeBeforeUpdate = maritalStatusesRepository.findAll().size();

        // Update the maritalStatuses using partial update
        MaritalStatuses partialUpdatedMaritalStatuses = new MaritalStatuses();
        partialUpdatedMaritalStatuses.setId(maritalStatuses.getId());

        partialUpdatedMaritalStatuses.status(UPDATED_STATUS).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restMaritalStatusesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMaritalStatuses.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMaritalStatuses))
            )
            .andExpect(status().isOk());

        // Validate the MaritalStatuses in the database
        List<MaritalStatuses> maritalStatusesList = maritalStatusesRepository.findAll();
        assertThat(maritalStatusesList).hasSize(databaseSizeBeforeUpdate);
        MaritalStatuses testMaritalStatuses = maritalStatusesList.get(maritalStatusesList.size() - 1);
        assertThat(testMaritalStatuses.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMaritalStatuses.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testMaritalStatuses.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingMaritalStatuses() throws Exception {
        int databaseSizeBeforeUpdate = maritalStatusesRepository.findAll().size();
        maritalStatuses.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaritalStatusesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, maritalStatuses.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(maritalStatuses))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaritalStatuses in the database
        List<MaritalStatuses> maritalStatusesList = maritalStatusesRepository.findAll();
        assertThat(maritalStatusesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMaritalStatuses() throws Exception {
        int databaseSizeBeforeUpdate = maritalStatusesRepository.findAll().size();
        maritalStatuses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaritalStatusesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(maritalStatuses))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaritalStatuses in the database
        List<MaritalStatuses> maritalStatusesList = maritalStatusesRepository.findAll();
        assertThat(maritalStatusesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMaritalStatuses() throws Exception {
        int databaseSizeBeforeUpdate = maritalStatusesRepository.findAll().size();
        maritalStatuses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaritalStatusesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(maritalStatuses))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MaritalStatuses in the database
        List<MaritalStatuses> maritalStatusesList = maritalStatusesRepository.findAll();
        assertThat(maritalStatusesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMaritalStatuses() throws Exception {
        // Initialize the database
        maritalStatusesRepository.saveAndFlush(maritalStatuses);

        int databaseSizeBeforeDelete = maritalStatusesRepository.findAll().size();

        // Delete the maritalStatuses
        restMaritalStatusesMockMvc
            .perform(delete(ENTITY_API_URL_ID, maritalStatuses.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MaritalStatuses> maritalStatusesList = maritalStatusesRepository.findAll();
        assertThat(maritalStatusesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
