package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Sequelizemeta;
import com.venturedive.vendian_mono.repository.SequelizemetaRepository;
import com.venturedive.vendian_mono.service.criteria.SequelizemetaCriteria;
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
 * Integration tests for the {@link SequelizemetaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SequelizemetaResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sequelizemetas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SequelizemetaRepository sequelizemetaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSequelizemetaMockMvc;

    private Sequelizemeta sequelizemeta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sequelizemeta createEntity(EntityManager em) {
        Sequelizemeta sequelizemeta = new Sequelizemeta().name(DEFAULT_NAME);
        return sequelizemeta;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sequelizemeta createUpdatedEntity(EntityManager em) {
        Sequelizemeta sequelizemeta = new Sequelizemeta().name(UPDATED_NAME);
        return sequelizemeta;
    }

    @BeforeEach
    public void initTest() {
        sequelizemeta = createEntity(em);
    }

    @Test
    @Transactional
    void createSequelizemeta() throws Exception {
        int databaseSizeBeforeCreate = sequelizemetaRepository.findAll().size();
        // Create the Sequelizemeta
        restSequelizemetaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sequelizemeta))
            )
            .andExpect(status().isCreated());

        // Validate the Sequelizemeta in the database
        List<Sequelizemeta> sequelizemetaList = sequelizemetaRepository.findAll();
        assertThat(sequelizemetaList).hasSize(databaseSizeBeforeCreate + 1);
        Sequelizemeta testSequelizemeta = sequelizemetaList.get(sequelizemetaList.size() - 1);
        assertThat(testSequelizemeta.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createSequelizemetaWithExistingId() throws Exception {
        // Create the Sequelizemeta with an existing ID
        sequelizemeta.setId(1L);

        int databaseSizeBeforeCreate = sequelizemetaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSequelizemetaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sequelizemeta))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sequelizemeta in the database
        List<Sequelizemeta> sequelizemetaList = sequelizemetaRepository.findAll();
        assertThat(sequelizemetaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sequelizemetaRepository.findAll().size();
        // set the field null
        sequelizemeta.setName(null);

        // Create the Sequelizemeta, which fails.

        restSequelizemetaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sequelizemeta))
            )
            .andExpect(status().isBadRequest());

        List<Sequelizemeta> sequelizemetaList = sequelizemetaRepository.findAll();
        assertThat(sequelizemetaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSequelizemetas() throws Exception {
        // Initialize the database
        sequelizemetaRepository.saveAndFlush(sequelizemeta);

        // Get all the sequelizemetaList
        restSequelizemetaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sequelizemeta.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getSequelizemeta() throws Exception {
        // Initialize the database
        sequelizemetaRepository.saveAndFlush(sequelizemeta);

        // Get the sequelizemeta
        restSequelizemetaMockMvc
            .perform(get(ENTITY_API_URL_ID, sequelizemeta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sequelizemeta.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getSequelizemetasByIdFiltering() throws Exception {
        // Initialize the database
        sequelizemetaRepository.saveAndFlush(sequelizemeta);

        Long id = sequelizemeta.getId();

        defaultSequelizemetaShouldBeFound("id.equals=" + id);
        defaultSequelizemetaShouldNotBeFound("id.notEquals=" + id);

        defaultSequelizemetaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSequelizemetaShouldNotBeFound("id.greaterThan=" + id);

        defaultSequelizemetaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSequelizemetaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSequelizemetasByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        sequelizemetaRepository.saveAndFlush(sequelizemeta);

        // Get all the sequelizemetaList where name equals to DEFAULT_NAME
        defaultSequelizemetaShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the sequelizemetaList where name equals to UPDATED_NAME
        defaultSequelizemetaShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSequelizemetasByNameIsInShouldWork() throws Exception {
        // Initialize the database
        sequelizemetaRepository.saveAndFlush(sequelizemeta);

        // Get all the sequelizemetaList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSequelizemetaShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the sequelizemetaList where name equals to UPDATED_NAME
        defaultSequelizemetaShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSequelizemetasByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequelizemetaRepository.saveAndFlush(sequelizemeta);

        // Get all the sequelizemetaList where name is not null
        defaultSequelizemetaShouldBeFound("name.specified=true");

        // Get all the sequelizemetaList where name is null
        defaultSequelizemetaShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllSequelizemetasByNameContainsSomething() throws Exception {
        // Initialize the database
        sequelizemetaRepository.saveAndFlush(sequelizemeta);

        // Get all the sequelizemetaList where name contains DEFAULT_NAME
        defaultSequelizemetaShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the sequelizemetaList where name contains UPDATED_NAME
        defaultSequelizemetaShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSequelizemetasByNameNotContainsSomething() throws Exception {
        // Initialize the database
        sequelizemetaRepository.saveAndFlush(sequelizemeta);

        // Get all the sequelizemetaList where name does not contain DEFAULT_NAME
        defaultSequelizemetaShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the sequelizemetaList where name does not contain UPDATED_NAME
        defaultSequelizemetaShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSequelizemetaShouldBeFound(String filter) throws Exception {
        restSequelizemetaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sequelizemeta.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restSequelizemetaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSequelizemetaShouldNotBeFound(String filter) throws Exception {
        restSequelizemetaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSequelizemetaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSequelizemeta() throws Exception {
        // Get the sequelizemeta
        restSequelizemetaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSequelizemeta() throws Exception {
        // Initialize the database
        sequelizemetaRepository.saveAndFlush(sequelizemeta);

        int databaseSizeBeforeUpdate = sequelizemetaRepository.findAll().size();

        // Update the sequelizemeta
        Sequelizemeta updatedSequelizemeta = sequelizemetaRepository.findById(sequelizemeta.getId()).get();
        // Disconnect from session so that the updates on updatedSequelizemeta are not directly saved in db
        em.detach(updatedSequelizemeta);
        updatedSequelizemeta.name(UPDATED_NAME);

        restSequelizemetaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSequelizemeta.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSequelizemeta))
            )
            .andExpect(status().isOk());

        // Validate the Sequelizemeta in the database
        List<Sequelizemeta> sequelizemetaList = sequelizemetaRepository.findAll();
        assertThat(sequelizemetaList).hasSize(databaseSizeBeforeUpdate);
        Sequelizemeta testSequelizemeta = sequelizemetaList.get(sequelizemetaList.size() - 1);
        assertThat(testSequelizemeta.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingSequelizemeta() throws Exception {
        int databaseSizeBeforeUpdate = sequelizemetaRepository.findAll().size();
        sequelizemeta.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSequelizemetaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sequelizemeta.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sequelizemeta))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sequelizemeta in the database
        List<Sequelizemeta> sequelizemetaList = sequelizemetaRepository.findAll();
        assertThat(sequelizemetaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSequelizemeta() throws Exception {
        int databaseSizeBeforeUpdate = sequelizemetaRepository.findAll().size();
        sequelizemeta.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSequelizemetaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sequelizemeta))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sequelizemeta in the database
        List<Sequelizemeta> sequelizemetaList = sequelizemetaRepository.findAll();
        assertThat(sequelizemetaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSequelizemeta() throws Exception {
        int databaseSizeBeforeUpdate = sequelizemetaRepository.findAll().size();
        sequelizemeta.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSequelizemetaMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sequelizemeta))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sequelizemeta in the database
        List<Sequelizemeta> sequelizemetaList = sequelizemetaRepository.findAll();
        assertThat(sequelizemetaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSequelizemetaWithPatch() throws Exception {
        // Initialize the database
        sequelizemetaRepository.saveAndFlush(sequelizemeta);

        int databaseSizeBeforeUpdate = sequelizemetaRepository.findAll().size();

        // Update the sequelizemeta using partial update
        Sequelizemeta partialUpdatedSequelizemeta = new Sequelizemeta();
        partialUpdatedSequelizemeta.setId(sequelizemeta.getId());

        partialUpdatedSequelizemeta.name(UPDATED_NAME);

        restSequelizemetaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSequelizemeta.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSequelizemeta))
            )
            .andExpect(status().isOk());

        // Validate the Sequelizemeta in the database
        List<Sequelizemeta> sequelizemetaList = sequelizemetaRepository.findAll();
        assertThat(sequelizemetaList).hasSize(databaseSizeBeforeUpdate);
        Sequelizemeta testSequelizemeta = sequelizemetaList.get(sequelizemetaList.size() - 1);
        assertThat(testSequelizemeta.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateSequelizemetaWithPatch() throws Exception {
        // Initialize the database
        sequelizemetaRepository.saveAndFlush(sequelizemeta);

        int databaseSizeBeforeUpdate = sequelizemetaRepository.findAll().size();

        // Update the sequelizemeta using partial update
        Sequelizemeta partialUpdatedSequelizemeta = new Sequelizemeta();
        partialUpdatedSequelizemeta.setId(sequelizemeta.getId());

        partialUpdatedSequelizemeta.name(UPDATED_NAME);

        restSequelizemetaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSequelizemeta.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSequelizemeta))
            )
            .andExpect(status().isOk());

        // Validate the Sequelizemeta in the database
        List<Sequelizemeta> sequelizemetaList = sequelizemetaRepository.findAll();
        assertThat(sequelizemetaList).hasSize(databaseSizeBeforeUpdate);
        Sequelizemeta testSequelizemeta = sequelizemetaList.get(sequelizemetaList.size() - 1);
        assertThat(testSequelizemeta.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingSequelizemeta() throws Exception {
        int databaseSizeBeforeUpdate = sequelizemetaRepository.findAll().size();
        sequelizemeta.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSequelizemetaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sequelizemeta.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sequelizemeta))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sequelizemeta in the database
        List<Sequelizemeta> sequelizemetaList = sequelizemetaRepository.findAll();
        assertThat(sequelizemetaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSequelizemeta() throws Exception {
        int databaseSizeBeforeUpdate = sequelizemetaRepository.findAll().size();
        sequelizemeta.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSequelizemetaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sequelizemeta))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sequelizemeta in the database
        List<Sequelizemeta> sequelizemetaList = sequelizemetaRepository.findAll();
        assertThat(sequelizemetaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSequelizemeta() throws Exception {
        int databaseSizeBeforeUpdate = sequelizemetaRepository.findAll().size();
        sequelizemeta.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSequelizemetaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sequelizemeta))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sequelizemeta in the database
        List<Sequelizemeta> sequelizemetaList = sequelizemetaRepository.findAll();
        assertThat(sequelizemetaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSequelizemeta() throws Exception {
        // Initialize the database
        sequelizemetaRepository.saveAndFlush(sequelizemeta);

        int databaseSizeBeforeDelete = sequelizemetaRepository.findAll().size();

        // Delete the sequelizemeta
        restSequelizemetaMockMvc
            .perform(delete(ENTITY_API_URL_ID, sequelizemeta.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sequelizemeta> sequelizemetaList = sequelizemetaRepository.findAll();
        assertThat(sequelizemetaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
