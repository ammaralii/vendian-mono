package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.ClaimApprovers;
import com.venturedive.vendian_mono.domain.ClaimStatus;
import com.venturedive.vendian_mono.repository.ClaimStatusRepository;
import com.venturedive.vendian_mono.service.criteria.ClaimStatusCriteria;
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
 * Integration tests for the {@link ClaimStatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClaimStatusResourceIT {

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/claim-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClaimStatusRepository claimStatusRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClaimStatusMockMvc;

    private ClaimStatus claimStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClaimStatus createEntity(EntityManager em) {
        ClaimStatus claimStatus = new ClaimStatus().status(DEFAULT_STATUS).createdat(DEFAULT_CREATEDAT).updatedat(DEFAULT_UPDATEDAT);
        return claimStatus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClaimStatus createUpdatedEntity(EntityManager em) {
        ClaimStatus claimStatus = new ClaimStatus().status(UPDATED_STATUS).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);
        return claimStatus;
    }

    @BeforeEach
    public void initTest() {
        claimStatus = createEntity(em);
    }

    @Test
    @Transactional
    void createClaimStatus() throws Exception {
        int databaseSizeBeforeCreate = claimStatusRepository.findAll().size();
        // Create the ClaimStatus
        restClaimStatusMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimStatus))
            )
            .andExpect(status().isCreated());

        // Validate the ClaimStatus in the database
        List<ClaimStatus> claimStatusList = claimStatusRepository.findAll();
        assertThat(claimStatusList).hasSize(databaseSizeBeforeCreate + 1);
        ClaimStatus testClaimStatus = claimStatusList.get(claimStatusList.size() - 1);
        assertThat(testClaimStatus.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testClaimStatus.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testClaimStatus.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createClaimStatusWithExistingId() throws Exception {
        // Create the ClaimStatus with an existing ID
        claimStatus.setId(1L);

        int databaseSizeBeforeCreate = claimStatusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClaimStatusMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimStatus in the database
        List<ClaimStatus> claimStatusList = claimStatusRepository.findAll();
        assertThat(claimStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClaimStatuses() throws Exception {
        // Initialize the database
        claimStatusRepository.saveAndFlush(claimStatus);

        // Get all the claimStatusList
        restClaimStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(claimStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getClaimStatus() throws Exception {
        // Initialize the database
        claimStatusRepository.saveAndFlush(claimStatus);

        // Get the claimStatus
        restClaimStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, claimStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(claimStatus.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getClaimStatusesByIdFiltering() throws Exception {
        // Initialize the database
        claimStatusRepository.saveAndFlush(claimStatus);

        Long id = claimStatus.getId();

        defaultClaimStatusShouldBeFound("id.equals=" + id);
        defaultClaimStatusShouldNotBeFound("id.notEquals=" + id);

        defaultClaimStatusShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClaimStatusShouldNotBeFound("id.greaterThan=" + id);

        defaultClaimStatusShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClaimStatusShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllClaimStatusesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        claimStatusRepository.saveAndFlush(claimStatus);

        // Get all the claimStatusList where status equals to DEFAULT_STATUS
        defaultClaimStatusShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the claimStatusList where status equals to UPDATED_STATUS
        defaultClaimStatusShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllClaimStatusesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        claimStatusRepository.saveAndFlush(claimStatus);

        // Get all the claimStatusList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultClaimStatusShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the claimStatusList where status equals to UPDATED_STATUS
        defaultClaimStatusShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllClaimStatusesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimStatusRepository.saveAndFlush(claimStatus);

        // Get all the claimStatusList where status is not null
        defaultClaimStatusShouldBeFound("status.specified=true");

        // Get all the claimStatusList where status is null
        defaultClaimStatusShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimStatusesByStatusContainsSomething() throws Exception {
        // Initialize the database
        claimStatusRepository.saveAndFlush(claimStatus);

        // Get all the claimStatusList where status contains DEFAULT_STATUS
        defaultClaimStatusShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the claimStatusList where status contains UPDATED_STATUS
        defaultClaimStatusShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllClaimStatusesByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        claimStatusRepository.saveAndFlush(claimStatus);

        // Get all the claimStatusList where status does not contain DEFAULT_STATUS
        defaultClaimStatusShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the claimStatusList where status does not contain UPDATED_STATUS
        defaultClaimStatusShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllClaimStatusesByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        claimStatusRepository.saveAndFlush(claimStatus);

        // Get all the claimStatusList where createdat equals to DEFAULT_CREATEDAT
        defaultClaimStatusShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the claimStatusList where createdat equals to UPDATED_CREATEDAT
        defaultClaimStatusShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllClaimStatusesByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        claimStatusRepository.saveAndFlush(claimStatus);

        // Get all the claimStatusList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultClaimStatusShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the claimStatusList where createdat equals to UPDATED_CREATEDAT
        defaultClaimStatusShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllClaimStatusesByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimStatusRepository.saveAndFlush(claimStatus);

        // Get all the claimStatusList where createdat is not null
        defaultClaimStatusShouldBeFound("createdat.specified=true");

        // Get all the claimStatusList where createdat is null
        defaultClaimStatusShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimStatusesByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        claimStatusRepository.saveAndFlush(claimStatus);

        // Get all the claimStatusList where updatedat equals to DEFAULT_UPDATEDAT
        defaultClaimStatusShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the claimStatusList where updatedat equals to UPDATED_UPDATEDAT
        defaultClaimStatusShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllClaimStatusesByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        claimStatusRepository.saveAndFlush(claimStatus);

        // Get all the claimStatusList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultClaimStatusShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the claimStatusList where updatedat equals to UPDATED_UPDATEDAT
        defaultClaimStatusShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllClaimStatusesByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimStatusRepository.saveAndFlush(claimStatus);

        // Get all the claimStatusList where updatedat is not null
        defaultClaimStatusShouldBeFound("updatedat.specified=true");

        // Get all the claimStatusList where updatedat is null
        defaultClaimStatusShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimStatusesByClaimapproversStatusIsEqualToSomething() throws Exception {
        ClaimApprovers claimapproversStatus;
        if (TestUtil.findAll(em, ClaimApprovers.class).isEmpty()) {
            claimStatusRepository.saveAndFlush(claimStatus);
            claimapproversStatus = ClaimApproversResourceIT.createEntity(em);
        } else {
            claimapproversStatus = TestUtil.findAll(em, ClaimApprovers.class).get(0);
        }
        em.persist(claimapproversStatus);
        em.flush();
        claimStatus.addClaimapproversStatus(claimapproversStatus);
        claimStatusRepository.saveAndFlush(claimStatus);
        Long claimapproversStatusId = claimapproversStatus.getId();

        // Get all the claimStatusList where claimapproversStatus equals to claimapproversStatusId
        defaultClaimStatusShouldBeFound("claimapproversStatusId.equals=" + claimapproversStatusId);

        // Get all the claimStatusList where claimapproversStatus equals to (claimapproversStatusId + 1)
        defaultClaimStatusShouldNotBeFound("claimapproversStatusId.equals=" + (claimapproversStatusId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClaimStatusShouldBeFound(String filter) throws Exception {
        restClaimStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(claimStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restClaimStatusMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClaimStatusShouldNotBeFound(String filter) throws Exception {
        restClaimStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClaimStatusMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingClaimStatus() throws Exception {
        // Get the claimStatus
        restClaimStatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingClaimStatus() throws Exception {
        // Initialize the database
        claimStatusRepository.saveAndFlush(claimStatus);

        int databaseSizeBeforeUpdate = claimStatusRepository.findAll().size();

        // Update the claimStatus
        ClaimStatus updatedClaimStatus = claimStatusRepository.findById(claimStatus.getId()).get();
        // Disconnect from session so that the updates on updatedClaimStatus are not directly saved in db
        em.detach(updatedClaimStatus);
        updatedClaimStatus.status(UPDATED_STATUS).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restClaimStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClaimStatus.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedClaimStatus))
            )
            .andExpect(status().isOk());

        // Validate the ClaimStatus in the database
        List<ClaimStatus> claimStatusList = claimStatusRepository.findAll();
        assertThat(claimStatusList).hasSize(databaseSizeBeforeUpdate);
        ClaimStatus testClaimStatus = claimStatusList.get(claimStatusList.size() - 1);
        assertThat(testClaimStatus.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testClaimStatus.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testClaimStatus.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingClaimStatus() throws Exception {
        int databaseSizeBeforeUpdate = claimStatusRepository.findAll().size();
        claimStatus.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClaimStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, claimStatus.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimStatus in the database
        List<ClaimStatus> claimStatusList = claimStatusRepository.findAll();
        assertThat(claimStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClaimStatus() throws Exception {
        int databaseSizeBeforeUpdate = claimStatusRepository.findAll().size();
        claimStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimStatus in the database
        List<ClaimStatus> claimStatusList = claimStatusRepository.findAll();
        assertThat(claimStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClaimStatus() throws Exception {
        int databaseSizeBeforeUpdate = claimStatusRepository.findAll().size();
        claimStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimStatusMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimStatus))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClaimStatus in the database
        List<ClaimStatus> claimStatusList = claimStatusRepository.findAll();
        assertThat(claimStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClaimStatusWithPatch() throws Exception {
        // Initialize the database
        claimStatusRepository.saveAndFlush(claimStatus);

        int databaseSizeBeforeUpdate = claimStatusRepository.findAll().size();

        // Update the claimStatus using partial update
        ClaimStatus partialUpdatedClaimStatus = new ClaimStatus();
        partialUpdatedClaimStatus.setId(claimStatus.getId());

        restClaimStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClaimStatus.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClaimStatus))
            )
            .andExpect(status().isOk());

        // Validate the ClaimStatus in the database
        List<ClaimStatus> claimStatusList = claimStatusRepository.findAll();
        assertThat(claimStatusList).hasSize(databaseSizeBeforeUpdate);
        ClaimStatus testClaimStatus = claimStatusList.get(claimStatusList.size() - 1);
        assertThat(testClaimStatus.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testClaimStatus.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testClaimStatus.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateClaimStatusWithPatch() throws Exception {
        // Initialize the database
        claimStatusRepository.saveAndFlush(claimStatus);

        int databaseSizeBeforeUpdate = claimStatusRepository.findAll().size();

        // Update the claimStatus using partial update
        ClaimStatus partialUpdatedClaimStatus = new ClaimStatus();
        partialUpdatedClaimStatus.setId(claimStatus.getId());

        partialUpdatedClaimStatus.status(UPDATED_STATUS).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restClaimStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClaimStatus.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClaimStatus))
            )
            .andExpect(status().isOk());

        // Validate the ClaimStatus in the database
        List<ClaimStatus> claimStatusList = claimStatusRepository.findAll();
        assertThat(claimStatusList).hasSize(databaseSizeBeforeUpdate);
        ClaimStatus testClaimStatus = claimStatusList.get(claimStatusList.size() - 1);
        assertThat(testClaimStatus.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testClaimStatus.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testClaimStatus.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingClaimStatus() throws Exception {
        int databaseSizeBeforeUpdate = claimStatusRepository.findAll().size();
        claimStatus.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClaimStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, claimStatus.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(claimStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimStatus in the database
        List<ClaimStatus> claimStatusList = claimStatusRepository.findAll();
        assertThat(claimStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClaimStatus() throws Exception {
        int databaseSizeBeforeUpdate = claimStatusRepository.findAll().size();
        claimStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(claimStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimStatus in the database
        List<ClaimStatus> claimStatusList = claimStatusRepository.findAll();
        assertThat(claimStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClaimStatus() throws Exception {
        int databaseSizeBeforeUpdate = claimStatusRepository.findAll().size();
        claimStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimStatusMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(claimStatus))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClaimStatus in the database
        List<ClaimStatus> claimStatusList = claimStatusRepository.findAll();
        assertThat(claimStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClaimStatus() throws Exception {
        // Initialize the database
        claimStatusRepository.saveAndFlush(claimStatus);

        int databaseSizeBeforeDelete = claimStatusRepository.findAll().size();

        // Delete the claimStatus
        restClaimStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, claimStatus.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClaimStatus> claimStatusList = claimStatusRepository.findAll();
        assertThat(claimStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
