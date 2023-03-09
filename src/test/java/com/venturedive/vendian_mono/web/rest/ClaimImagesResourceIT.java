package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.ClaimImages;
import com.venturedive.vendian_mono.domain.ClaimRequests;
import com.venturedive.vendian_mono.repository.ClaimImagesRepository;
import com.venturedive.vendian_mono.service.criteria.ClaimImagesCriteria;
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
 * Integration tests for the {@link ClaimImagesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClaimImagesResourceIT {

    private static final String DEFAULT_IMAGES = "AAAAAAAAAA";
    private static final String UPDATED_IMAGES = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/claim-images";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClaimImagesRepository claimImagesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClaimImagesMockMvc;

    private ClaimImages claimImages;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClaimImages createEntity(EntityManager em) {
        ClaimImages claimImages = new ClaimImages().images(DEFAULT_IMAGES).createdat(DEFAULT_CREATEDAT).updatedat(DEFAULT_UPDATEDAT);
        return claimImages;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClaimImages createUpdatedEntity(EntityManager em) {
        ClaimImages claimImages = new ClaimImages().images(UPDATED_IMAGES).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);
        return claimImages;
    }

    @BeforeEach
    public void initTest() {
        claimImages = createEntity(em);
    }

    @Test
    @Transactional
    void createClaimImages() throws Exception {
        int databaseSizeBeforeCreate = claimImagesRepository.findAll().size();
        // Create the ClaimImages
        restClaimImagesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimImages))
            )
            .andExpect(status().isCreated());

        // Validate the ClaimImages in the database
        List<ClaimImages> claimImagesList = claimImagesRepository.findAll();
        assertThat(claimImagesList).hasSize(databaseSizeBeforeCreate + 1);
        ClaimImages testClaimImages = claimImagesList.get(claimImagesList.size() - 1);
        assertThat(testClaimImages.getImages()).isEqualTo(DEFAULT_IMAGES);
        assertThat(testClaimImages.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testClaimImages.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createClaimImagesWithExistingId() throws Exception {
        // Create the ClaimImages with an existing ID
        claimImages.setId(1L);

        int databaseSizeBeforeCreate = claimImagesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClaimImagesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimImages))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimImages in the database
        List<ClaimImages> claimImagesList = claimImagesRepository.findAll();
        assertThat(claimImagesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClaimImages() throws Exception {
        // Initialize the database
        claimImagesRepository.saveAndFlush(claimImages);

        // Get all the claimImagesList
        restClaimImagesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(claimImages.getId().intValue())))
            .andExpect(jsonPath("$.[*].images").value(hasItem(DEFAULT_IMAGES)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getClaimImages() throws Exception {
        // Initialize the database
        claimImagesRepository.saveAndFlush(claimImages);

        // Get the claimImages
        restClaimImagesMockMvc
            .perform(get(ENTITY_API_URL_ID, claimImages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(claimImages.getId().intValue()))
            .andExpect(jsonPath("$.images").value(DEFAULT_IMAGES))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getClaimImagesByIdFiltering() throws Exception {
        // Initialize the database
        claimImagesRepository.saveAndFlush(claimImages);

        Long id = claimImages.getId();

        defaultClaimImagesShouldBeFound("id.equals=" + id);
        defaultClaimImagesShouldNotBeFound("id.notEquals=" + id);

        defaultClaimImagesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClaimImagesShouldNotBeFound("id.greaterThan=" + id);

        defaultClaimImagesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClaimImagesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllClaimImagesByImagesIsEqualToSomething() throws Exception {
        // Initialize the database
        claimImagesRepository.saveAndFlush(claimImages);

        // Get all the claimImagesList where images equals to DEFAULT_IMAGES
        defaultClaimImagesShouldBeFound("images.equals=" + DEFAULT_IMAGES);

        // Get all the claimImagesList where images equals to UPDATED_IMAGES
        defaultClaimImagesShouldNotBeFound("images.equals=" + UPDATED_IMAGES);
    }

    @Test
    @Transactional
    void getAllClaimImagesByImagesIsInShouldWork() throws Exception {
        // Initialize the database
        claimImagesRepository.saveAndFlush(claimImages);

        // Get all the claimImagesList where images in DEFAULT_IMAGES or UPDATED_IMAGES
        defaultClaimImagesShouldBeFound("images.in=" + DEFAULT_IMAGES + "," + UPDATED_IMAGES);

        // Get all the claimImagesList where images equals to UPDATED_IMAGES
        defaultClaimImagesShouldNotBeFound("images.in=" + UPDATED_IMAGES);
    }

    @Test
    @Transactional
    void getAllClaimImagesByImagesIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimImagesRepository.saveAndFlush(claimImages);

        // Get all the claimImagesList where images is not null
        defaultClaimImagesShouldBeFound("images.specified=true");

        // Get all the claimImagesList where images is null
        defaultClaimImagesShouldNotBeFound("images.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimImagesByImagesContainsSomething() throws Exception {
        // Initialize the database
        claimImagesRepository.saveAndFlush(claimImages);

        // Get all the claimImagesList where images contains DEFAULT_IMAGES
        defaultClaimImagesShouldBeFound("images.contains=" + DEFAULT_IMAGES);

        // Get all the claimImagesList where images contains UPDATED_IMAGES
        defaultClaimImagesShouldNotBeFound("images.contains=" + UPDATED_IMAGES);
    }

    @Test
    @Transactional
    void getAllClaimImagesByImagesNotContainsSomething() throws Exception {
        // Initialize the database
        claimImagesRepository.saveAndFlush(claimImages);

        // Get all the claimImagesList where images does not contain DEFAULT_IMAGES
        defaultClaimImagesShouldNotBeFound("images.doesNotContain=" + DEFAULT_IMAGES);

        // Get all the claimImagesList where images does not contain UPDATED_IMAGES
        defaultClaimImagesShouldBeFound("images.doesNotContain=" + UPDATED_IMAGES);
    }

    @Test
    @Transactional
    void getAllClaimImagesByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        claimImagesRepository.saveAndFlush(claimImages);

        // Get all the claimImagesList where createdat equals to DEFAULT_CREATEDAT
        defaultClaimImagesShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the claimImagesList where createdat equals to UPDATED_CREATEDAT
        defaultClaimImagesShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllClaimImagesByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        claimImagesRepository.saveAndFlush(claimImages);

        // Get all the claimImagesList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultClaimImagesShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the claimImagesList where createdat equals to UPDATED_CREATEDAT
        defaultClaimImagesShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllClaimImagesByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimImagesRepository.saveAndFlush(claimImages);

        // Get all the claimImagesList where createdat is not null
        defaultClaimImagesShouldBeFound("createdat.specified=true");

        // Get all the claimImagesList where createdat is null
        defaultClaimImagesShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimImagesByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        claimImagesRepository.saveAndFlush(claimImages);

        // Get all the claimImagesList where updatedat equals to DEFAULT_UPDATEDAT
        defaultClaimImagesShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the claimImagesList where updatedat equals to UPDATED_UPDATEDAT
        defaultClaimImagesShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllClaimImagesByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        claimImagesRepository.saveAndFlush(claimImages);

        // Get all the claimImagesList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultClaimImagesShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the claimImagesList where updatedat equals to UPDATED_UPDATEDAT
        defaultClaimImagesShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllClaimImagesByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimImagesRepository.saveAndFlush(claimImages);

        // Get all the claimImagesList where updatedat is not null
        defaultClaimImagesShouldBeFound("updatedat.specified=true");

        // Get all the claimImagesList where updatedat is null
        defaultClaimImagesShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimImagesByClaimrequestIsEqualToSomething() throws Exception {
        ClaimRequests claimrequest;
        if (TestUtil.findAll(em, ClaimRequests.class).isEmpty()) {
            claimImagesRepository.saveAndFlush(claimImages);
            claimrequest = ClaimRequestsResourceIT.createEntity(em);
        } else {
            claimrequest = TestUtil.findAll(em, ClaimRequests.class).get(0);
        }
        em.persist(claimrequest);
        em.flush();
        claimImages.setClaimrequest(claimrequest);
        claimImagesRepository.saveAndFlush(claimImages);
        Long claimrequestId = claimrequest.getId();

        // Get all the claimImagesList where claimrequest equals to claimrequestId
        defaultClaimImagesShouldBeFound("claimrequestId.equals=" + claimrequestId);

        // Get all the claimImagesList where claimrequest equals to (claimrequestId + 1)
        defaultClaimImagesShouldNotBeFound("claimrequestId.equals=" + (claimrequestId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClaimImagesShouldBeFound(String filter) throws Exception {
        restClaimImagesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(claimImages.getId().intValue())))
            .andExpect(jsonPath("$.[*].images").value(hasItem(DEFAULT_IMAGES)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restClaimImagesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClaimImagesShouldNotBeFound(String filter) throws Exception {
        restClaimImagesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClaimImagesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingClaimImages() throws Exception {
        // Get the claimImages
        restClaimImagesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingClaimImages() throws Exception {
        // Initialize the database
        claimImagesRepository.saveAndFlush(claimImages);

        int databaseSizeBeforeUpdate = claimImagesRepository.findAll().size();

        // Update the claimImages
        ClaimImages updatedClaimImages = claimImagesRepository.findById(claimImages.getId()).get();
        // Disconnect from session so that the updates on updatedClaimImages are not directly saved in db
        em.detach(updatedClaimImages);
        updatedClaimImages.images(UPDATED_IMAGES).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restClaimImagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClaimImages.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedClaimImages))
            )
            .andExpect(status().isOk());

        // Validate the ClaimImages in the database
        List<ClaimImages> claimImagesList = claimImagesRepository.findAll();
        assertThat(claimImagesList).hasSize(databaseSizeBeforeUpdate);
        ClaimImages testClaimImages = claimImagesList.get(claimImagesList.size() - 1);
        assertThat(testClaimImages.getImages()).isEqualTo(UPDATED_IMAGES);
        assertThat(testClaimImages.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testClaimImages.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingClaimImages() throws Exception {
        int databaseSizeBeforeUpdate = claimImagesRepository.findAll().size();
        claimImages.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClaimImagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, claimImages.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimImages))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimImages in the database
        List<ClaimImages> claimImagesList = claimImagesRepository.findAll();
        assertThat(claimImagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClaimImages() throws Exception {
        int databaseSizeBeforeUpdate = claimImagesRepository.findAll().size();
        claimImages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimImagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimImages))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimImages in the database
        List<ClaimImages> claimImagesList = claimImagesRepository.findAll();
        assertThat(claimImagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClaimImages() throws Exception {
        int databaseSizeBeforeUpdate = claimImagesRepository.findAll().size();
        claimImages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimImagesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimImages))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClaimImages in the database
        List<ClaimImages> claimImagesList = claimImagesRepository.findAll();
        assertThat(claimImagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClaimImagesWithPatch() throws Exception {
        // Initialize the database
        claimImagesRepository.saveAndFlush(claimImages);

        int databaseSizeBeforeUpdate = claimImagesRepository.findAll().size();

        // Update the claimImages using partial update
        ClaimImages partialUpdatedClaimImages = new ClaimImages();
        partialUpdatedClaimImages.setId(claimImages.getId());

        partialUpdatedClaimImages.images(UPDATED_IMAGES).updatedat(UPDATED_UPDATEDAT);

        restClaimImagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClaimImages.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClaimImages))
            )
            .andExpect(status().isOk());

        // Validate the ClaimImages in the database
        List<ClaimImages> claimImagesList = claimImagesRepository.findAll();
        assertThat(claimImagesList).hasSize(databaseSizeBeforeUpdate);
        ClaimImages testClaimImages = claimImagesList.get(claimImagesList.size() - 1);
        assertThat(testClaimImages.getImages()).isEqualTo(UPDATED_IMAGES);
        assertThat(testClaimImages.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testClaimImages.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateClaimImagesWithPatch() throws Exception {
        // Initialize the database
        claimImagesRepository.saveAndFlush(claimImages);

        int databaseSizeBeforeUpdate = claimImagesRepository.findAll().size();

        // Update the claimImages using partial update
        ClaimImages partialUpdatedClaimImages = new ClaimImages();
        partialUpdatedClaimImages.setId(claimImages.getId());

        partialUpdatedClaimImages.images(UPDATED_IMAGES).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restClaimImagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClaimImages.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClaimImages))
            )
            .andExpect(status().isOk());

        // Validate the ClaimImages in the database
        List<ClaimImages> claimImagesList = claimImagesRepository.findAll();
        assertThat(claimImagesList).hasSize(databaseSizeBeforeUpdate);
        ClaimImages testClaimImages = claimImagesList.get(claimImagesList.size() - 1);
        assertThat(testClaimImages.getImages()).isEqualTo(UPDATED_IMAGES);
        assertThat(testClaimImages.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testClaimImages.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingClaimImages() throws Exception {
        int databaseSizeBeforeUpdate = claimImagesRepository.findAll().size();
        claimImages.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClaimImagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, claimImages.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(claimImages))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimImages in the database
        List<ClaimImages> claimImagesList = claimImagesRepository.findAll();
        assertThat(claimImagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClaimImages() throws Exception {
        int databaseSizeBeforeUpdate = claimImagesRepository.findAll().size();
        claimImages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimImagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(claimImages))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimImages in the database
        List<ClaimImages> claimImagesList = claimImagesRepository.findAll();
        assertThat(claimImagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClaimImages() throws Exception {
        int databaseSizeBeforeUpdate = claimImagesRepository.findAll().size();
        claimImages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimImagesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(claimImages))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClaimImages in the database
        List<ClaimImages> claimImagesList = claimImagesRepository.findAll();
        assertThat(claimImagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClaimImages() throws Exception {
        // Initialize the database
        claimImagesRepository.saveAndFlush(claimImages);

        int databaseSizeBeforeDelete = claimImagesRepository.findAll().size();

        // Delete the claimImages
        restClaimImagesMockMvc
            .perform(delete(ENTITY_API_URL_ID, claimImages.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClaimImages> claimImagesList = claimImagesRepository.findAll();
        assertThat(claimImagesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
