package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Attributes;
import com.venturedive.vendian_mono.domain.RaterAttributeMappings;
import com.venturedive.vendian_mono.domain.RaterAttributes;
import com.venturedive.vendian_mono.domain.RatingAttributes;
import com.venturedive.vendian_mono.repository.RaterAttributeMappingsRepository;
import com.venturedive.vendian_mono.service.criteria.RaterAttributeMappingsCriteria;
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
 * Integration tests for the {@link RaterAttributeMappingsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RaterAttributeMappingsResourceIT {

    private static final Instant DEFAULT_EFFDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EFFDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ENDDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ENDDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/rater-attribute-mappings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RaterAttributeMappingsRepository raterAttributeMappingsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRaterAttributeMappingsMockMvc;

    private RaterAttributeMappings raterAttributeMappings;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RaterAttributeMappings createEntity(EntityManager em) {
        RaterAttributeMappings raterAttributeMappings = new RaterAttributeMappings()
            .effdate(DEFAULT_EFFDATE)
            .enddate(DEFAULT_ENDDATE)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        return raterAttributeMappings;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RaterAttributeMappings createUpdatedEntity(EntityManager em) {
        RaterAttributeMappings raterAttributeMappings = new RaterAttributeMappings()
            .effdate(UPDATED_EFFDATE)
            .enddate(UPDATED_ENDDATE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        return raterAttributeMappings;
    }

    @BeforeEach
    public void initTest() {
        raterAttributeMappings = createEntity(em);
    }

    @Test
    @Transactional
    void createRaterAttributeMappings() throws Exception {
        int databaseSizeBeforeCreate = raterAttributeMappingsRepository.findAll().size();
        // Create the RaterAttributeMappings
        restRaterAttributeMappingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(raterAttributeMappings))
            )
            .andExpect(status().isCreated());

        // Validate the RaterAttributeMappings in the database
        List<RaterAttributeMappings> raterAttributeMappingsList = raterAttributeMappingsRepository.findAll();
        assertThat(raterAttributeMappingsList).hasSize(databaseSizeBeforeCreate + 1);
        RaterAttributeMappings testRaterAttributeMappings = raterAttributeMappingsList.get(raterAttributeMappingsList.size() - 1);
        assertThat(testRaterAttributeMappings.getEffdate()).isEqualTo(DEFAULT_EFFDATE);
        assertThat(testRaterAttributeMappings.getEnddate()).isEqualTo(DEFAULT_ENDDATE);
        assertThat(testRaterAttributeMappings.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testRaterAttributeMappings.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createRaterAttributeMappingsWithExistingId() throws Exception {
        // Create the RaterAttributeMappings with an existing ID
        raterAttributeMappings.setId(1L);

        int databaseSizeBeforeCreate = raterAttributeMappingsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRaterAttributeMappingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(raterAttributeMappings))
            )
            .andExpect(status().isBadRequest());

        // Validate the RaterAttributeMappings in the database
        List<RaterAttributeMappings> raterAttributeMappingsList = raterAttributeMappingsRepository.findAll();
        assertThat(raterAttributeMappingsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = raterAttributeMappingsRepository.findAll().size();
        // set the field null
        raterAttributeMappings.setCreatedat(null);

        // Create the RaterAttributeMappings, which fails.

        restRaterAttributeMappingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(raterAttributeMappings))
            )
            .andExpect(status().isBadRequest());

        List<RaterAttributeMappings> raterAttributeMappingsList = raterAttributeMappingsRepository.findAll();
        assertThat(raterAttributeMappingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = raterAttributeMappingsRepository.findAll().size();
        // set the field null
        raterAttributeMappings.setUpdatedat(null);

        // Create the RaterAttributeMappings, which fails.

        restRaterAttributeMappingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(raterAttributeMappings))
            )
            .andExpect(status().isBadRequest());

        List<RaterAttributeMappings> raterAttributeMappingsList = raterAttributeMappingsRepository.findAll();
        assertThat(raterAttributeMappingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRaterAttributeMappings() throws Exception {
        // Initialize the database
        raterAttributeMappingsRepository.saveAndFlush(raterAttributeMappings);

        // Get all the raterAttributeMappingsList
        restRaterAttributeMappingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(raterAttributeMappings.getId().intValue())))
            .andExpect(jsonPath("$.[*].effdate").value(hasItem(DEFAULT_EFFDATE.toString())))
            .andExpect(jsonPath("$.[*].enddate").value(hasItem(DEFAULT_ENDDATE.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getRaterAttributeMappings() throws Exception {
        // Initialize the database
        raterAttributeMappingsRepository.saveAndFlush(raterAttributeMappings);

        // Get the raterAttributeMappings
        restRaterAttributeMappingsMockMvc
            .perform(get(ENTITY_API_URL_ID, raterAttributeMappings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(raterAttributeMappings.getId().intValue()))
            .andExpect(jsonPath("$.effdate").value(DEFAULT_EFFDATE.toString()))
            .andExpect(jsonPath("$.enddate").value(DEFAULT_ENDDATE.toString()))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getRaterAttributeMappingsByIdFiltering() throws Exception {
        // Initialize the database
        raterAttributeMappingsRepository.saveAndFlush(raterAttributeMappings);

        Long id = raterAttributeMappings.getId();

        defaultRaterAttributeMappingsShouldBeFound("id.equals=" + id);
        defaultRaterAttributeMappingsShouldNotBeFound("id.notEquals=" + id);

        defaultRaterAttributeMappingsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRaterAttributeMappingsShouldNotBeFound("id.greaterThan=" + id);

        defaultRaterAttributeMappingsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRaterAttributeMappingsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRaterAttributeMappingsByEffdateIsEqualToSomething() throws Exception {
        // Initialize the database
        raterAttributeMappingsRepository.saveAndFlush(raterAttributeMappings);

        // Get all the raterAttributeMappingsList where effdate equals to DEFAULT_EFFDATE
        defaultRaterAttributeMappingsShouldBeFound("effdate.equals=" + DEFAULT_EFFDATE);

        // Get all the raterAttributeMappingsList where effdate equals to UPDATED_EFFDATE
        defaultRaterAttributeMappingsShouldNotBeFound("effdate.equals=" + UPDATED_EFFDATE);
    }

    @Test
    @Transactional
    void getAllRaterAttributeMappingsByEffdateIsInShouldWork() throws Exception {
        // Initialize the database
        raterAttributeMappingsRepository.saveAndFlush(raterAttributeMappings);

        // Get all the raterAttributeMappingsList where effdate in DEFAULT_EFFDATE or UPDATED_EFFDATE
        defaultRaterAttributeMappingsShouldBeFound("effdate.in=" + DEFAULT_EFFDATE + "," + UPDATED_EFFDATE);

        // Get all the raterAttributeMappingsList where effdate equals to UPDATED_EFFDATE
        defaultRaterAttributeMappingsShouldNotBeFound("effdate.in=" + UPDATED_EFFDATE);
    }

    @Test
    @Transactional
    void getAllRaterAttributeMappingsByEffdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        raterAttributeMappingsRepository.saveAndFlush(raterAttributeMappings);

        // Get all the raterAttributeMappingsList where effdate is not null
        defaultRaterAttributeMappingsShouldBeFound("effdate.specified=true");

        // Get all the raterAttributeMappingsList where effdate is null
        defaultRaterAttributeMappingsShouldNotBeFound("effdate.specified=false");
    }

    @Test
    @Transactional
    void getAllRaterAttributeMappingsByEnddateIsEqualToSomething() throws Exception {
        // Initialize the database
        raterAttributeMappingsRepository.saveAndFlush(raterAttributeMappings);

        // Get all the raterAttributeMappingsList where enddate equals to DEFAULT_ENDDATE
        defaultRaterAttributeMappingsShouldBeFound("enddate.equals=" + DEFAULT_ENDDATE);

        // Get all the raterAttributeMappingsList where enddate equals to UPDATED_ENDDATE
        defaultRaterAttributeMappingsShouldNotBeFound("enddate.equals=" + UPDATED_ENDDATE);
    }

    @Test
    @Transactional
    void getAllRaterAttributeMappingsByEnddateIsInShouldWork() throws Exception {
        // Initialize the database
        raterAttributeMappingsRepository.saveAndFlush(raterAttributeMappings);

        // Get all the raterAttributeMappingsList where enddate in DEFAULT_ENDDATE or UPDATED_ENDDATE
        defaultRaterAttributeMappingsShouldBeFound("enddate.in=" + DEFAULT_ENDDATE + "," + UPDATED_ENDDATE);

        // Get all the raterAttributeMappingsList where enddate equals to UPDATED_ENDDATE
        defaultRaterAttributeMappingsShouldNotBeFound("enddate.in=" + UPDATED_ENDDATE);
    }

    @Test
    @Transactional
    void getAllRaterAttributeMappingsByEnddateIsNullOrNotNull() throws Exception {
        // Initialize the database
        raterAttributeMappingsRepository.saveAndFlush(raterAttributeMappings);

        // Get all the raterAttributeMappingsList where enddate is not null
        defaultRaterAttributeMappingsShouldBeFound("enddate.specified=true");

        // Get all the raterAttributeMappingsList where enddate is null
        defaultRaterAttributeMappingsShouldNotBeFound("enddate.specified=false");
    }

    @Test
    @Transactional
    void getAllRaterAttributeMappingsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        raterAttributeMappingsRepository.saveAndFlush(raterAttributeMappings);

        // Get all the raterAttributeMappingsList where createdat equals to DEFAULT_CREATEDAT
        defaultRaterAttributeMappingsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the raterAttributeMappingsList where createdat equals to UPDATED_CREATEDAT
        defaultRaterAttributeMappingsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllRaterAttributeMappingsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        raterAttributeMappingsRepository.saveAndFlush(raterAttributeMappings);

        // Get all the raterAttributeMappingsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultRaterAttributeMappingsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the raterAttributeMappingsList where createdat equals to UPDATED_CREATEDAT
        defaultRaterAttributeMappingsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllRaterAttributeMappingsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        raterAttributeMappingsRepository.saveAndFlush(raterAttributeMappings);

        // Get all the raterAttributeMappingsList where createdat is not null
        defaultRaterAttributeMappingsShouldBeFound("createdat.specified=true");

        // Get all the raterAttributeMappingsList where createdat is null
        defaultRaterAttributeMappingsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllRaterAttributeMappingsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        raterAttributeMappingsRepository.saveAndFlush(raterAttributeMappings);

        // Get all the raterAttributeMappingsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultRaterAttributeMappingsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the raterAttributeMappingsList where updatedat equals to UPDATED_UPDATEDAT
        defaultRaterAttributeMappingsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllRaterAttributeMappingsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        raterAttributeMappingsRepository.saveAndFlush(raterAttributeMappings);

        // Get all the raterAttributeMappingsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultRaterAttributeMappingsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the raterAttributeMappingsList where updatedat equals to UPDATED_UPDATEDAT
        defaultRaterAttributeMappingsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllRaterAttributeMappingsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        raterAttributeMappingsRepository.saveAndFlush(raterAttributeMappings);

        // Get all the raterAttributeMappingsList where updatedat is not null
        defaultRaterAttributeMappingsShouldBeFound("updatedat.specified=true");

        // Get all the raterAttributeMappingsList where updatedat is null
        defaultRaterAttributeMappingsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllRaterAttributeMappingsByRaterattributeIsEqualToSomething() throws Exception {
        RaterAttributes raterattribute;
        if (TestUtil.findAll(em, RaterAttributes.class).isEmpty()) {
            raterAttributeMappingsRepository.saveAndFlush(raterAttributeMappings);
            raterattribute = RaterAttributesResourceIT.createEntity(em);
        } else {
            raterattribute = TestUtil.findAll(em, RaterAttributes.class).get(0);
        }
        em.persist(raterattribute);
        em.flush();
        raterAttributeMappings.setRaterattribute(raterattribute);
        raterAttributeMappingsRepository.saveAndFlush(raterAttributeMappings);
        Long raterattributeId = raterattribute.getId();

        // Get all the raterAttributeMappingsList where raterattribute equals to raterattributeId
        defaultRaterAttributeMappingsShouldBeFound("raterattributeId.equals=" + raterattributeId);

        // Get all the raterAttributeMappingsList where raterattribute equals to (raterattributeId + 1)
        defaultRaterAttributeMappingsShouldNotBeFound("raterattributeId.equals=" + (raterattributeId + 1));
    }

    @Test
    @Transactional
    void getAllRaterAttributeMappingsByAttributesForIsEqualToSomething() throws Exception {
        Attributes attributesFor;
        if (TestUtil.findAll(em, Attributes.class).isEmpty()) {
            raterAttributeMappingsRepository.saveAndFlush(raterAttributeMappings);
            attributesFor = AttributesResourceIT.createEntity(em);
        } else {
            attributesFor = TestUtil.findAll(em, Attributes.class).get(0);
        }
        em.persist(attributesFor);
        em.flush();
        raterAttributeMappings.setAttributesFor(attributesFor);
        raterAttributeMappingsRepository.saveAndFlush(raterAttributeMappings);
        Long attributesForId = attributesFor.getId();

        // Get all the raterAttributeMappingsList where attributesFor equals to attributesForId
        defaultRaterAttributeMappingsShouldBeFound("attributesForId.equals=" + attributesForId);

        // Get all the raterAttributeMappingsList where attributesFor equals to (attributesForId + 1)
        defaultRaterAttributeMappingsShouldNotBeFound("attributesForId.equals=" + (attributesForId + 1));
    }

    @Test
    @Transactional
    void getAllRaterAttributeMappingsByAttributesByIsEqualToSomething() throws Exception {
        Attributes attributesBy;
        if (TestUtil.findAll(em, Attributes.class).isEmpty()) {
            raterAttributeMappingsRepository.saveAndFlush(raterAttributeMappings);
            attributesBy = AttributesResourceIT.createEntity(em);
        } else {
            attributesBy = TestUtil.findAll(em, Attributes.class).get(0);
        }
        em.persist(attributesBy);
        em.flush();
        raterAttributeMappings.setAttributesBy(attributesBy);
        raterAttributeMappingsRepository.saveAndFlush(raterAttributeMappings);
        Long attributesById = attributesBy.getId();

        // Get all the raterAttributeMappingsList where attributesBy equals to attributesById
        defaultRaterAttributeMappingsShouldBeFound("attributesById.equals=" + attributesById);

        // Get all the raterAttributeMappingsList where attributesBy equals to (attributesById + 1)
        defaultRaterAttributeMappingsShouldNotBeFound("attributesById.equals=" + (attributesById + 1));
    }

    @Test
    @Transactional
    void getAllRaterAttributeMappingsByRatingattributesRaterattributemappingIsEqualToSomething() throws Exception {
        RatingAttributes ratingattributesRaterattributemapping;
        if (TestUtil.findAll(em, RatingAttributes.class).isEmpty()) {
            raterAttributeMappingsRepository.saveAndFlush(raterAttributeMappings);
            ratingattributesRaterattributemapping = RatingAttributesResourceIT.createEntity(em);
        } else {
            ratingattributesRaterattributemapping = TestUtil.findAll(em, RatingAttributes.class).get(0);
        }
        em.persist(ratingattributesRaterattributemapping);
        em.flush();
        raterAttributeMappings.addRatingattributesRaterattributemapping(ratingattributesRaterattributemapping);
        raterAttributeMappingsRepository.saveAndFlush(raterAttributeMappings);
        Long ratingattributesRaterattributemappingId = ratingattributesRaterattributemapping.getId();

        // Get all the raterAttributeMappingsList where ratingattributesRaterattributemapping equals to ratingattributesRaterattributemappingId
        defaultRaterAttributeMappingsShouldBeFound(
            "ratingattributesRaterattributemappingId.equals=" + ratingattributesRaterattributemappingId
        );

        // Get all the raterAttributeMappingsList where ratingattributesRaterattributemapping equals to (ratingattributesRaterattributemappingId + 1)
        defaultRaterAttributeMappingsShouldNotBeFound(
            "ratingattributesRaterattributemappingId.equals=" + (ratingattributesRaterattributemappingId + 1)
        );
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRaterAttributeMappingsShouldBeFound(String filter) throws Exception {
        restRaterAttributeMappingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(raterAttributeMappings.getId().intValue())))
            .andExpect(jsonPath("$.[*].effdate").value(hasItem(DEFAULT_EFFDATE.toString())))
            .andExpect(jsonPath("$.[*].enddate").value(hasItem(DEFAULT_ENDDATE.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restRaterAttributeMappingsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRaterAttributeMappingsShouldNotBeFound(String filter) throws Exception {
        restRaterAttributeMappingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRaterAttributeMappingsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRaterAttributeMappings() throws Exception {
        // Get the raterAttributeMappings
        restRaterAttributeMappingsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRaterAttributeMappings() throws Exception {
        // Initialize the database
        raterAttributeMappingsRepository.saveAndFlush(raterAttributeMappings);

        int databaseSizeBeforeUpdate = raterAttributeMappingsRepository.findAll().size();

        // Update the raterAttributeMappings
        RaterAttributeMappings updatedRaterAttributeMappings = raterAttributeMappingsRepository
            .findById(raterAttributeMappings.getId())
            .get();
        // Disconnect from session so that the updates on updatedRaterAttributeMappings are not directly saved in db
        em.detach(updatedRaterAttributeMappings);
        updatedRaterAttributeMappings
            .effdate(UPDATED_EFFDATE)
            .enddate(UPDATED_ENDDATE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restRaterAttributeMappingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRaterAttributeMappings.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRaterAttributeMappings))
            )
            .andExpect(status().isOk());

        // Validate the RaterAttributeMappings in the database
        List<RaterAttributeMappings> raterAttributeMappingsList = raterAttributeMappingsRepository.findAll();
        assertThat(raterAttributeMappingsList).hasSize(databaseSizeBeforeUpdate);
        RaterAttributeMappings testRaterAttributeMappings = raterAttributeMappingsList.get(raterAttributeMappingsList.size() - 1);
        assertThat(testRaterAttributeMappings.getEffdate()).isEqualTo(UPDATED_EFFDATE);
        assertThat(testRaterAttributeMappings.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testRaterAttributeMappings.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testRaterAttributeMappings.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingRaterAttributeMappings() throws Exception {
        int databaseSizeBeforeUpdate = raterAttributeMappingsRepository.findAll().size();
        raterAttributeMappings.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRaterAttributeMappingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, raterAttributeMappings.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(raterAttributeMappings))
            )
            .andExpect(status().isBadRequest());

        // Validate the RaterAttributeMappings in the database
        List<RaterAttributeMappings> raterAttributeMappingsList = raterAttributeMappingsRepository.findAll();
        assertThat(raterAttributeMappingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRaterAttributeMappings() throws Exception {
        int databaseSizeBeforeUpdate = raterAttributeMappingsRepository.findAll().size();
        raterAttributeMappings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRaterAttributeMappingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(raterAttributeMappings))
            )
            .andExpect(status().isBadRequest());

        // Validate the RaterAttributeMappings in the database
        List<RaterAttributeMappings> raterAttributeMappingsList = raterAttributeMappingsRepository.findAll();
        assertThat(raterAttributeMappingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRaterAttributeMappings() throws Exception {
        int databaseSizeBeforeUpdate = raterAttributeMappingsRepository.findAll().size();
        raterAttributeMappings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRaterAttributeMappingsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(raterAttributeMappings))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RaterAttributeMappings in the database
        List<RaterAttributeMappings> raterAttributeMappingsList = raterAttributeMappingsRepository.findAll();
        assertThat(raterAttributeMappingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRaterAttributeMappingsWithPatch() throws Exception {
        // Initialize the database
        raterAttributeMappingsRepository.saveAndFlush(raterAttributeMappings);

        int databaseSizeBeforeUpdate = raterAttributeMappingsRepository.findAll().size();

        // Update the raterAttributeMappings using partial update
        RaterAttributeMappings partialUpdatedRaterAttributeMappings = new RaterAttributeMappings();
        partialUpdatedRaterAttributeMappings.setId(raterAttributeMappings.getId());

        partialUpdatedRaterAttributeMappings.enddate(UPDATED_ENDDATE).createdat(UPDATED_CREATEDAT);

        restRaterAttributeMappingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRaterAttributeMappings.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRaterAttributeMappings))
            )
            .andExpect(status().isOk());

        // Validate the RaterAttributeMappings in the database
        List<RaterAttributeMappings> raterAttributeMappingsList = raterAttributeMappingsRepository.findAll();
        assertThat(raterAttributeMappingsList).hasSize(databaseSizeBeforeUpdate);
        RaterAttributeMappings testRaterAttributeMappings = raterAttributeMappingsList.get(raterAttributeMappingsList.size() - 1);
        assertThat(testRaterAttributeMappings.getEffdate()).isEqualTo(DEFAULT_EFFDATE);
        assertThat(testRaterAttributeMappings.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testRaterAttributeMappings.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testRaterAttributeMappings.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateRaterAttributeMappingsWithPatch() throws Exception {
        // Initialize the database
        raterAttributeMappingsRepository.saveAndFlush(raterAttributeMappings);

        int databaseSizeBeforeUpdate = raterAttributeMappingsRepository.findAll().size();

        // Update the raterAttributeMappings using partial update
        RaterAttributeMappings partialUpdatedRaterAttributeMappings = new RaterAttributeMappings();
        partialUpdatedRaterAttributeMappings.setId(raterAttributeMappings.getId());

        partialUpdatedRaterAttributeMappings
            .effdate(UPDATED_EFFDATE)
            .enddate(UPDATED_ENDDATE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restRaterAttributeMappingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRaterAttributeMappings.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRaterAttributeMappings))
            )
            .andExpect(status().isOk());

        // Validate the RaterAttributeMappings in the database
        List<RaterAttributeMappings> raterAttributeMappingsList = raterAttributeMappingsRepository.findAll();
        assertThat(raterAttributeMappingsList).hasSize(databaseSizeBeforeUpdate);
        RaterAttributeMappings testRaterAttributeMappings = raterAttributeMappingsList.get(raterAttributeMappingsList.size() - 1);
        assertThat(testRaterAttributeMappings.getEffdate()).isEqualTo(UPDATED_EFFDATE);
        assertThat(testRaterAttributeMappings.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testRaterAttributeMappings.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testRaterAttributeMappings.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingRaterAttributeMappings() throws Exception {
        int databaseSizeBeforeUpdate = raterAttributeMappingsRepository.findAll().size();
        raterAttributeMappings.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRaterAttributeMappingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, raterAttributeMappings.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(raterAttributeMappings))
            )
            .andExpect(status().isBadRequest());

        // Validate the RaterAttributeMappings in the database
        List<RaterAttributeMappings> raterAttributeMappingsList = raterAttributeMappingsRepository.findAll();
        assertThat(raterAttributeMappingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRaterAttributeMappings() throws Exception {
        int databaseSizeBeforeUpdate = raterAttributeMappingsRepository.findAll().size();
        raterAttributeMappings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRaterAttributeMappingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(raterAttributeMappings))
            )
            .andExpect(status().isBadRequest());

        // Validate the RaterAttributeMappings in the database
        List<RaterAttributeMappings> raterAttributeMappingsList = raterAttributeMappingsRepository.findAll();
        assertThat(raterAttributeMappingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRaterAttributeMappings() throws Exception {
        int databaseSizeBeforeUpdate = raterAttributeMappingsRepository.findAll().size();
        raterAttributeMappings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRaterAttributeMappingsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(raterAttributeMappings))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RaterAttributeMappings in the database
        List<RaterAttributeMappings> raterAttributeMappingsList = raterAttributeMappingsRepository.findAll();
        assertThat(raterAttributeMappingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRaterAttributeMappings() throws Exception {
        // Initialize the database
        raterAttributeMappingsRepository.saveAndFlush(raterAttributeMappings);

        int databaseSizeBeforeDelete = raterAttributeMappingsRepository.findAll().size();

        // Delete the raterAttributeMappings
        restRaterAttributeMappingsMockMvc
            .perform(delete(ENTITY_API_URL_ID, raterAttributeMappings.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RaterAttributeMappings> raterAttributeMappingsList = raterAttributeMappingsRepository.findAll();
        assertThat(raterAttributeMappingsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
