package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Sequelizedata;
import com.venturedive.vendian_mono.repository.SequelizedataRepository;
import com.venturedive.vendian_mono.service.criteria.SequelizedataCriteria;
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
 * Integration tests for the {@link SequelizedataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SequelizedataResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sequelizedata";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SequelizedataRepository sequelizedataRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSequelizedataMockMvc;

    private Sequelizedata sequelizedata;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sequelizedata createEntity(EntityManager em) {
        Sequelizedata sequelizedata = new Sequelizedata().name(DEFAULT_NAME);
        return sequelizedata;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sequelizedata createUpdatedEntity(EntityManager em) {
        Sequelizedata sequelizedata = new Sequelizedata().name(UPDATED_NAME);
        return sequelizedata;
    }

    @BeforeEach
    public void initTest() {
        sequelizedata = createEntity(em);
    }

    @Test
    @Transactional
    void createSequelizedata() throws Exception {
        int databaseSizeBeforeCreate = sequelizedataRepository.findAll().size();
        // Create the Sequelizedata
        restSequelizedataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sequelizedata))
            )
            .andExpect(status().isCreated());

        // Validate the Sequelizedata in the database
        List<Sequelizedata> sequelizedataList = sequelizedataRepository.findAll();
        assertThat(sequelizedataList).hasSize(databaseSizeBeforeCreate + 1);
        Sequelizedata testSequelizedata = sequelizedataList.get(sequelizedataList.size() - 1);
        assertThat(testSequelizedata.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createSequelizedataWithExistingId() throws Exception {
        // Create the Sequelizedata with an existing ID
        sequelizedata.setId(1L);

        int databaseSizeBeforeCreate = sequelizedataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSequelizedataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sequelizedata))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sequelizedata in the database
        List<Sequelizedata> sequelizedataList = sequelizedataRepository.findAll();
        assertThat(sequelizedataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sequelizedataRepository.findAll().size();
        // set the field null
        sequelizedata.setName(null);

        // Create the Sequelizedata, which fails.

        restSequelizedataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sequelizedata))
            )
            .andExpect(status().isBadRequest());

        List<Sequelizedata> sequelizedataList = sequelizedataRepository.findAll();
        assertThat(sequelizedataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSequelizedata() throws Exception {
        // Initialize the database
        sequelizedataRepository.saveAndFlush(sequelizedata);

        // Get all the sequelizedataList
        restSequelizedataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sequelizedata.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getSequelizedata() throws Exception {
        // Initialize the database
        sequelizedataRepository.saveAndFlush(sequelizedata);

        // Get the sequelizedata
        restSequelizedataMockMvc
            .perform(get(ENTITY_API_URL_ID, sequelizedata.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sequelizedata.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getSequelizedataByIdFiltering() throws Exception {
        // Initialize the database
        sequelizedataRepository.saveAndFlush(sequelizedata);

        Long id = sequelizedata.getId();

        defaultSequelizedataShouldBeFound("id.equals=" + id);
        defaultSequelizedataShouldNotBeFound("id.notEquals=" + id);

        defaultSequelizedataShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSequelizedataShouldNotBeFound("id.greaterThan=" + id);

        defaultSequelizedataShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSequelizedataShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSequelizedataByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        sequelizedataRepository.saveAndFlush(sequelizedata);

        // Get all the sequelizedataList where name equals to DEFAULT_NAME
        defaultSequelizedataShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the sequelizedataList where name equals to UPDATED_NAME
        defaultSequelizedataShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSequelizedataByNameIsInShouldWork() throws Exception {
        // Initialize the database
        sequelizedataRepository.saveAndFlush(sequelizedata);

        // Get all the sequelizedataList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSequelizedataShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the sequelizedataList where name equals to UPDATED_NAME
        defaultSequelizedataShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSequelizedataByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequelizedataRepository.saveAndFlush(sequelizedata);

        // Get all the sequelizedataList where name is not null
        defaultSequelizedataShouldBeFound("name.specified=true");

        // Get all the sequelizedataList where name is null
        defaultSequelizedataShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllSequelizedataByNameContainsSomething() throws Exception {
        // Initialize the database
        sequelizedataRepository.saveAndFlush(sequelizedata);

        // Get all the sequelizedataList where name contains DEFAULT_NAME
        defaultSequelizedataShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the sequelizedataList where name contains UPDATED_NAME
        defaultSequelizedataShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSequelizedataByNameNotContainsSomething() throws Exception {
        // Initialize the database
        sequelizedataRepository.saveAndFlush(sequelizedata);

        // Get all the sequelizedataList where name does not contain DEFAULT_NAME
        defaultSequelizedataShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the sequelizedataList where name does not contain UPDATED_NAME
        defaultSequelizedataShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSequelizedataShouldBeFound(String filter) throws Exception {
        restSequelizedataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sequelizedata.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restSequelizedataMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSequelizedataShouldNotBeFound(String filter) throws Exception {
        restSequelizedataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSequelizedataMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSequelizedata() throws Exception {
        // Get the sequelizedata
        restSequelizedataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSequelizedata() throws Exception {
        // Initialize the database
        sequelizedataRepository.saveAndFlush(sequelizedata);

        int databaseSizeBeforeUpdate = sequelizedataRepository.findAll().size();

        // Update the sequelizedata
        Sequelizedata updatedSequelizedata = sequelizedataRepository.findById(sequelizedata.getId()).get();
        // Disconnect from session so that the updates on updatedSequelizedata are not directly saved in db
        em.detach(updatedSequelizedata);
        updatedSequelizedata.name(UPDATED_NAME);

        restSequelizedataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSequelizedata.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSequelizedata))
            )
            .andExpect(status().isOk());

        // Validate the Sequelizedata in the database
        List<Sequelizedata> sequelizedataList = sequelizedataRepository.findAll();
        assertThat(sequelizedataList).hasSize(databaseSizeBeforeUpdate);
        Sequelizedata testSequelizedata = sequelizedataList.get(sequelizedataList.size() - 1);
        assertThat(testSequelizedata.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingSequelizedata() throws Exception {
        int databaseSizeBeforeUpdate = sequelizedataRepository.findAll().size();
        sequelizedata.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSequelizedataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sequelizedata.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sequelizedata))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sequelizedata in the database
        List<Sequelizedata> sequelizedataList = sequelizedataRepository.findAll();
        assertThat(sequelizedataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSequelizedata() throws Exception {
        int databaseSizeBeforeUpdate = sequelizedataRepository.findAll().size();
        sequelizedata.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSequelizedataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sequelizedata))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sequelizedata in the database
        List<Sequelizedata> sequelizedataList = sequelizedataRepository.findAll();
        assertThat(sequelizedataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSequelizedata() throws Exception {
        int databaseSizeBeforeUpdate = sequelizedataRepository.findAll().size();
        sequelizedata.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSequelizedataMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sequelizedata))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sequelizedata in the database
        List<Sequelizedata> sequelizedataList = sequelizedataRepository.findAll();
        assertThat(sequelizedataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSequelizedataWithPatch() throws Exception {
        // Initialize the database
        sequelizedataRepository.saveAndFlush(sequelizedata);

        int databaseSizeBeforeUpdate = sequelizedataRepository.findAll().size();

        // Update the sequelizedata using partial update
        Sequelizedata partialUpdatedSequelizedata = new Sequelizedata();
        partialUpdatedSequelizedata.setId(sequelizedata.getId());

        restSequelizedataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSequelizedata.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSequelizedata))
            )
            .andExpect(status().isOk());

        // Validate the Sequelizedata in the database
        List<Sequelizedata> sequelizedataList = sequelizedataRepository.findAll();
        assertThat(sequelizedataList).hasSize(databaseSizeBeforeUpdate);
        Sequelizedata testSequelizedata = sequelizedataList.get(sequelizedataList.size() - 1);
        assertThat(testSequelizedata.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateSequelizedataWithPatch() throws Exception {
        // Initialize the database
        sequelizedataRepository.saveAndFlush(sequelizedata);

        int databaseSizeBeforeUpdate = sequelizedataRepository.findAll().size();

        // Update the sequelizedata using partial update
        Sequelizedata partialUpdatedSequelizedata = new Sequelizedata();
        partialUpdatedSequelizedata.setId(sequelizedata.getId());

        partialUpdatedSequelizedata.name(UPDATED_NAME);

        restSequelizedataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSequelizedata.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSequelizedata))
            )
            .andExpect(status().isOk());

        // Validate the Sequelizedata in the database
        List<Sequelizedata> sequelizedataList = sequelizedataRepository.findAll();
        assertThat(sequelizedataList).hasSize(databaseSizeBeforeUpdate);
        Sequelizedata testSequelizedata = sequelizedataList.get(sequelizedataList.size() - 1);
        assertThat(testSequelizedata.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingSequelizedata() throws Exception {
        int databaseSizeBeforeUpdate = sequelizedataRepository.findAll().size();
        sequelizedata.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSequelizedataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sequelizedata.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sequelizedata))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sequelizedata in the database
        List<Sequelizedata> sequelizedataList = sequelizedataRepository.findAll();
        assertThat(sequelizedataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSequelizedata() throws Exception {
        int databaseSizeBeforeUpdate = sequelizedataRepository.findAll().size();
        sequelizedata.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSequelizedataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sequelizedata))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sequelizedata in the database
        List<Sequelizedata> sequelizedataList = sequelizedataRepository.findAll();
        assertThat(sequelizedataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSequelizedata() throws Exception {
        int databaseSizeBeforeUpdate = sequelizedataRepository.findAll().size();
        sequelizedata.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSequelizedataMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sequelizedata))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sequelizedata in the database
        List<Sequelizedata> sequelizedataList = sequelizedataRepository.findAll();
        assertThat(sequelizedataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSequelizedata() throws Exception {
        // Initialize the database
        sequelizedataRepository.saveAndFlush(sequelizedata);

        int databaseSizeBeforeDelete = sequelizedataRepository.findAll().size();

        // Delete the sequelizedata
        restSequelizedataMockMvc
            .perform(delete(ENTITY_API_URL_ID, sequelizedata.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sequelizedata> sequelizedataList = sequelizedataRepository.findAll();
        assertThat(sequelizedataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
