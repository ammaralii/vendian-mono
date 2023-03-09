package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.RaterAttributeMappings;
import com.venturedive.vendian_mono.domain.RatingAttributes;
import com.venturedive.vendian_mono.domain.Ratings;
import com.venturedive.vendian_mono.repository.RatingAttributesRepository;
import com.venturedive.vendian_mono.service.criteria.RatingAttributesCriteria;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link RatingAttributesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RatingAttributesResourceIT {

    private static final byte[] DEFAULT_RA_RATING = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_RA_RATING = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_RA_RATING_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_RA_RATING_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_COMMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_COMMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_COMMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_COMMENT_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/rating-attributes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RatingAttributesRepository ratingAttributesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRatingAttributesMockMvc;

    private RatingAttributes ratingAttributes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RatingAttributes createEntity(EntityManager em) {
        RatingAttributes ratingAttributes = new RatingAttributes()
            .raRating(DEFAULT_RA_RATING)
            .raRatingContentType(DEFAULT_RA_RATING_CONTENT_TYPE)
            .comment(DEFAULT_COMMENT)
            .commentContentType(DEFAULT_COMMENT_CONTENT_TYPE)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        return ratingAttributes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RatingAttributes createUpdatedEntity(EntityManager em) {
        RatingAttributes ratingAttributes = new RatingAttributes()
            .raRating(UPDATED_RA_RATING)
            .raRatingContentType(UPDATED_RA_RATING_CONTENT_TYPE)
            .comment(UPDATED_COMMENT)
            .commentContentType(UPDATED_COMMENT_CONTENT_TYPE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        return ratingAttributes;
    }

    @BeforeEach
    public void initTest() {
        ratingAttributes = createEntity(em);
    }

    @Test
    @Transactional
    void createRatingAttributes() throws Exception {
        int databaseSizeBeforeCreate = ratingAttributesRepository.findAll().size();
        // Create the RatingAttributes
        restRatingAttributesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingAttributes))
            )
            .andExpect(status().isCreated());

        // Validate the RatingAttributes in the database
        List<RatingAttributes> ratingAttributesList = ratingAttributesRepository.findAll();
        assertThat(ratingAttributesList).hasSize(databaseSizeBeforeCreate + 1);
        RatingAttributes testRatingAttributes = ratingAttributesList.get(ratingAttributesList.size() - 1);
        assertThat(testRatingAttributes.getRaRating()).isEqualTo(DEFAULT_RA_RATING);
        assertThat(testRatingAttributes.getRaRatingContentType()).isEqualTo(DEFAULT_RA_RATING_CONTENT_TYPE);
        assertThat(testRatingAttributes.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testRatingAttributes.getCommentContentType()).isEqualTo(DEFAULT_COMMENT_CONTENT_TYPE);
        assertThat(testRatingAttributes.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testRatingAttributes.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createRatingAttributesWithExistingId() throws Exception {
        // Create the RatingAttributes with an existing ID
        ratingAttributes.setId(1L);

        int databaseSizeBeforeCreate = ratingAttributesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRatingAttributesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the RatingAttributes in the database
        List<RatingAttributes> ratingAttributesList = ratingAttributesRepository.findAll();
        assertThat(ratingAttributesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = ratingAttributesRepository.findAll().size();
        // set the field null
        ratingAttributes.setCreatedat(null);

        // Create the RatingAttributes, which fails.

        restRatingAttributesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingAttributes))
            )
            .andExpect(status().isBadRequest());

        List<RatingAttributes> ratingAttributesList = ratingAttributesRepository.findAll();
        assertThat(ratingAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = ratingAttributesRepository.findAll().size();
        // set the field null
        ratingAttributes.setUpdatedat(null);

        // Create the RatingAttributes, which fails.

        restRatingAttributesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingAttributes))
            )
            .andExpect(status().isBadRequest());

        List<RatingAttributes> ratingAttributesList = ratingAttributesRepository.findAll();
        assertThat(ratingAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRatingAttributes() throws Exception {
        // Initialize the database
        ratingAttributesRepository.saveAndFlush(ratingAttributes);

        // Get all the ratingAttributesList
        restRatingAttributesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ratingAttributes.getId().intValue())))
            .andExpect(jsonPath("$.[*].raRatingContentType").value(hasItem(DEFAULT_RA_RATING_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].raRating").value(hasItem(Base64Utils.encodeToString(DEFAULT_RA_RATING))))
            .andExpect(jsonPath("$.[*].commentContentType").value(hasItem(DEFAULT_COMMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(Base64Utils.encodeToString(DEFAULT_COMMENT))))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getRatingAttributes() throws Exception {
        // Initialize the database
        ratingAttributesRepository.saveAndFlush(ratingAttributes);

        // Get the ratingAttributes
        restRatingAttributesMockMvc
            .perform(get(ENTITY_API_URL_ID, ratingAttributes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ratingAttributes.getId().intValue()))
            .andExpect(jsonPath("$.raRatingContentType").value(DEFAULT_RA_RATING_CONTENT_TYPE))
            .andExpect(jsonPath("$.raRating").value(Base64Utils.encodeToString(DEFAULT_RA_RATING)))
            .andExpect(jsonPath("$.commentContentType").value(DEFAULT_COMMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.comment").value(Base64Utils.encodeToString(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getRatingAttributesByIdFiltering() throws Exception {
        // Initialize the database
        ratingAttributesRepository.saveAndFlush(ratingAttributes);

        Long id = ratingAttributes.getId();

        defaultRatingAttributesShouldBeFound("id.equals=" + id);
        defaultRatingAttributesShouldNotBeFound("id.notEquals=" + id);

        defaultRatingAttributesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRatingAttributesShouldNotBeFound("id.greaterThan=" + id);

        defaultRatingAttributesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRatingAttributesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRatingAttributesByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        ratingAttributesRepository.saveAndFlush(ratingAttributes);

        // Get all the ratingAttributesList where createdat equals to DEFAULT_CREATEDAT
        defaultRatingAttributesShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the ratingAttributesList where createdat equals to UPDATED_CREATEDAT
        defaultRatingAttributesShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllRatingAttributesByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        ratingAttributesRepository.saveAndFlush(ratingAttributes);

        // Get all the ratingAttributesList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultRatingAttributesShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the ratingAttributesList where createdat equals to UPDATED_CREATEDAT
        defaultRatingAttributesShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllRatingAttributesByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        ratingAttributesRepository.saveAndFlush(ratingAttributes);

        // Get all the ratingAttributesList where createdat is not null
        defaultRatingAttributesShouldBeFound("createdat.specified=true");

        // Get all the ratingAttributesList where createdat is null
        defaultRatingAttributesShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllRatingAttributesByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        ratingAttributesRepository.saveAndFlush(ratingAttributes);

        // Get all the ratingAttributesList where updatedat equals to DEFAULT_UPDATEDAT
        defaultRatingAttributesShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the ratingAttributesList where updatedat equals to UPDATED_UPDATEDAT
        defaultRatingAttributesShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllRatingAttributesByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        ratingAttributesRepository.saveAndFlush(ratingAttributes);

        // Get all the ratingAttributesList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultRatingAttributesShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the ratingAttributesList where updatedat equals to UPDATED_UPDATEDAT
        defaultRatingAttributesShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllRatingAttributesByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        ratingAttributesRepository.saveAndFlush(ratingAttributes);

        // Get all the ratingAttributesList where updatedat is not null
        defaultRatingAttributesShouldBeFound("updatedat.specified=true");

        // Get all the ratingAttributesList where updatedat is null
        defaultRatingAttributesShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllRatingAttributesByRatingIsEqualToSomething() throws Exception {
        Ratings rating;
        if (TestUtil.findAll(em, Ratings.class).isEmpty()) {
            ratingAttributesRepository.saveAndFlush(ratingAttributes);
            rating = RatingsResourceIT.createEntity(em);
        } else {
            rating = TestUtil.findAll(em, Ratings.class).get(0);
        }
        em.persist(rating);
        em.flush();
        ratingAttributes.setRating(rating);
        ratingAttributesRepository.saveAndFlush(ratingAttributes);
        Long ratingId = rating.getId();

        // Get all the ratingAttributesList where rating equals to ratingId
        defaultRatingAttributesShouldBeFound("ratingId.equals=" + ratingId);

        // Get all the ratingAttributesList where rating equals to (ratingId + 1)
        defaultRatingAttributesShouldNotBeFound("ratingId.equals=" + (ratingId + 1));
    }

    @Test
    @Transactional
    void getAllRatingAttributesByRaterattributemappingIsEqualToSomething() throws Exception {
        RaterAttributeMappings raterattributemapping;
        if (TestUtil.findAll(em, RaterAttributeMappings.class).isEmpty()) {
            ratingAttributesRepository.saveAndFlush(ratingAttributes);
            raterattributemapping = RaterAttributeMappingsResourceIT.createEntity(em);
        } else {
            raterattributemapping = TestUtil.findAll(em, RaterAttributeMappings.class).get(0);
        }
        em.persist(raterattributemapping);
        em.flush();
        ratingAttributes.setRaterattributemapping(raterattributemapping);
        ratingAttributesRepository.saveAndFlush(ratingAttributes);
        Long raterattributemappingId = raterattributemapping.getId();

        // Get all the ratingAttributesList where raterattributemapping equals to raterattributemappingId
        defaultRatingAttributesShouldBeFound("raterattributemappingId.equals=" + raterattributemappingId);

        // Get all the ratingAttributesList where raterattributemapping equals to (raterattributemappingId + 1)
        defaultRatingAttributesShouldNotBeFound("raterattributemappingId.equals=" + (raterattributemappingId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRatingAttributesShouldBeFound(String filter) throws Exception {
        restRatingAttributesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ratingAttributes.getId().intValue())))
            .andExpect(jsonPath("$.[*].raRatingContentType").value(hasItem(DEFAULT_RA_RATING_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].raRating").value(hasItem(Base64Utils.encodeToString(DEFAULT_RA_RATING))))
            .andExpect(jsonPath("$.[*].commentContentType").value(hasItem(DEFAULT_COMMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(Base64Utils.encodeToString(DEFAULT_COMMENT))))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restRatingAttributesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRatingAttributesShouldNotBeFound(String filter) throws Exception {
        restRatingAttributesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRatingAttributesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRatingAttributes() throws Exception {
        // Get the ratingAttributes
        restRatingAttributesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRatingAttributes() throws Exception {
        // Initialize the database
        ratingAttributesRepository.saveAndFlush(ratingAttributes);

        int databaseSizeBeforeUpdate = ratingAttributesRepository.findAll().size();

        // Update the ratingAttributes
        RatingAttributes updatedRatingAttributes = ratingAttributesRepository.findById(ratingAttributes.getId()).get();
        // Disconnect from session so that the updates on updatedRatingAttributes are not directly saved in db
        em.detach(updatedRatingAttributes);
        updatedRatingAttributes
            .raRating(UPDATED_RA_RATING)
            .raRatingContentType(UPDATED_RA_RATING_CONTENT_TYPE)
            .comment(UPDATED_COMMENT)
            .commentContentType(UPDATED_COMMENT_CONTENT_TYPE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restRatingAttributesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRatingAttributes.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRatingAttributes))
            )
            .andExpect(status().isOk());

        // Validate the RatingAttributes in the database
        List<RatingAttributes> ratingAttributesList = ratingAttributesRepository.findAll();
        assertThat(ratingAttributesList).hasSize(databaseSizeBeforeUpdate);
        RatingAttributes testRatingAttributes = ratingAttributesList.get(ratingAttributesList.size() - 1);
        assertThat(testRatingAttributes.getRaRating()).isEqualTo(UPDATED_RA_RATING);
        assertThat(testRatingAttributes.getRaRatingContentType()).isEqualTo(UPDATED_RA_RATING_CONTENT_TYPE);
        assertThat(testRatingAttributes.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testRatingAttributes.getCommentContentType()).isEqualTo(UPDATED_COMMENT_CONTENT_TYPE);
        assertThat(testRatingAttributes.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testRatingAttributes.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingRatingAttributes() throws Exception {
        int databaseSizeBeforeUpdate = ratingAttributesRepository.findAll().size();
        ratingAttributes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRatingAttributesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ratingAttributes.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the RatingAttributes in the database
        List<RatingAttributes> ratingAttributesList = ratingAttributesRepository.findAll();
        assertThat(ratingAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRatingAttributes() throws Exception {
        int databaseSizeBeforeUpdate = ratingAttributesRepository.findAll().size();
        ratingAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatingAttributesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the RatingAttributes in the database
        List<RatingAttributes> ratingAttributesList = ratingAttributesRepository.findAll();
        assertThat(ratingAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRatingAttributes() throws Exception {
        int databaseSizeBeforeUpdate = ratingAttributesRepository.findAll().size();
        ratingAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatingAttributesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingAttributes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RatingAttributes in the database
        List<RatingAttributes> ratingAttributesList = ratingAttributesRepository.findAll();
        assertThat(ratingAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRatingAttributesWithPatch() throws Exception {
        // Initialize the database
        ratingAttributesRepository.saveAndFlush(ratingAttributes);

        int databaseSizeBeforeUpdate = ratingAttributesRepository.findAll().size();

        // Update the ratingAttributes using partial update
        RatingAttributes partialUpdatedRatingAttributes = new RatingAttributes();
        partialUpdatedRatingAttributes.setId(ratingAttributes.getId());

        partialUpdatedRatingAttributes.createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restRatingAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRatingAttributes.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRatingAttributes))
            )
            .andExpect(status().isOk());

        // Validate the RatingAttributes in the database
        List<RatingAttributes> ratingAttributesList = ratingAttributesRepository.findAll();
        assertThat(ratingAttributesList).hasSize(databaseSizeBeforeUpdate);
        RatingAttributes testRatingAttributes = ratingAttributesList.get(ratingAttributesList.size() - 1);
        assertThat(testRatingAttributes.getRaRating()).isEqualTo(DEFAULT_RA_RATING);
        assertThat(testRatingAttributes.getRaRatingContentType()).isEqualTo(DEFAULT_RA_RATING_CONTENT_TYPE);
        assertThat(testRatingAttributes.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testRatingAttributes.getCommentContentType()).isEqualTo(DEFAULT_COMMENT_CONTENT_TYPE);
        assertThat(testRatingAttributes.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testRatingAttributes.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateRatingAttributesWithPatch() throws Exception {
        // Initialize the database
        ratingAttributesRepository.saveAndFlush(ratingAttributes);

        int databaseSizeBeforeUpdate = ratingAttributesRepository.findAll().size();

        // Update the ratingAttributes using partial update
        RatingAttributes partialUpdatedRatingAttributes = new RatingAttributes();
        partialUpdatedRatingAttributes.setId(ratingAttributes.getId());

        partialUpdatedRatingAttributes
            .raRating(UPDATED_RA_RATING)
            .raRatingContentType(UPDATED_RA_RATING_CONTENT_TYPE)
            .comment(UPDATED_COMMENT)
            .commentContentType(UPDATED_COMMENT_CONTENT_TYPE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restRatingAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRatingAttributes.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRatingAttributes))
            )
            .andExpect(status().isOk());

        // Validate the RatingAttributes in the database
        List<RatingAttributes> ratingAttributesList = ratingAttributesRepository.findAll();
        assertThat(ratingAttributesList).hasSize(databaseSizeBeforeUpdate);
        RatingAttributes testRatingAttributes = ratingAttributesList.get(ratingAttributesList.size() - 1);
        assertThat(testRatingAttributes.getRaRating()).isEqualTo(UPDATED_RA_RATING);
        assertThat(testRatingAttributes.getRaRatingContentType()).isEqualTo(UPDATED_RA_RATING_CONTENT_TYPE);
        assertThat(testRatingAttributes.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testRatingAttributes.getCommentContentType()).isEqualTo(UPDATED_COMMENT_CONTENT_TYPE);
        assertThat(testRatingAttributes.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testRatingAttributes.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingRatingAttributes() throws Exception {
        int databaseSizeBeforeUpdate = ratingAttributesRepository.findAll().size();
        ratingAttributes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRatingAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ratingAttributes.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ratingAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the RatingAttributes in the database
        List<RatingAttributes> ratingAttributesList = ratingAttributesRepository.findAll();
        assertThat(ratingAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRatingAttributes() throws Exception {
        int databaseSizeBeforeUpdate = ratingAttributesRepository.findAll().size();
        ratingAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatingAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ratingAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the RatingAttributes in the database
        List<RatingAttributes> ratingAttributesList = ratingAttributesRepository.findAll();
        assertThat(ratingAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRatingAttributes() throws Exception {
        int databaseSizeBeforeUpdate = ratingAttributesRepository.findAll().size();
        ratingAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatingAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ratingAttributes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RatingAttributes in the database
        List<RatingAttributes> ratingAttributesList = ratingAttributesRepository.findAll();
        assertThat(ratingAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRatingAttributes() throws Exception {
        // Initialize the database
        ratingAttributesRepository.saveAndFlush(ratingAttributes);

        int databaseSizeBeforeDelete = ratingAttributesRepository.findAll().size();

        // Delete the ratingAttributes
        restRatingAttributesMockMvc
            .perform(delete(ENTITY_API_URL_ID, ratingAttributes.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RatingAttributes> ratingAttributesList = ratingAttributesRepository.findAll();
        assertThat(ratingAttributesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
