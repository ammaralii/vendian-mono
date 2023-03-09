package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.ClaimDetails;
import com.venturedive.vendian_mono.domain.ClaimRequests;
import com.venturedive.vendian_mono.domain.ClaimTypes;
import com.venturedive.vendian_mono.repository.ClaimDetailsRepository;
import com.venturedive.vendian_mono.service.criteria.ClaimDetailsCriteria;
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
 * Integration tests for the {@link ClaimDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClaimDetailsResourceIT {

    private static final Integer DEFAULT_AMOUNT = 1;
    private static final Integer UPDATED_AMOUNT = 2;
    private static final Integer SMALLER_AMOUNT = 1 - 1;

    private static final Instant DEFAULT_STARTDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STARTDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ENDDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ENDDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/claim-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClaimDetailsRepository claimDetailsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClaimDetailsMockMvc;

    private ClaimDetails claimDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClaimDetails createEntity(EntityManager em) {
        ClaimDetails claimDetails = new ClaimDetails()
            .amount(DEFAULT_AMOUNT)
            .startdate(DEFAULT_STARTDATE)
            .enddate(DEFAULT_ENDDATE)
            .description(DEFAULT_DESCRIPTION)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        return claimDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClaimDetails createUpdatedEntity(EntityManager em) {
        ClaimDetails claimDetails = new ClaimDetails()
            .amount(UPDATED_AMOUNT)
            .startdate(UPDATED_STARTDATE)
            .enddate(UPDATED_ENDDATE)
            .description(UPDATED_DESCRIPTION)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        return claimDetails;
    }

    @BeforeEach
    public void initTest() {
        claimDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createClaimDetails() throws Exception {
        int databaseSizeBeforeCreate = claimDetailsRepository.findAll().size();
        // Create the ClaimDetails
        restClaimDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimDetails))
            )
            .andExpect(status().isCreated());

        // Validate the ClaimDetails in the database
        List<ClaimDetails> claimDetailsList = claimDetailsRepository.findAll();
        assertThat(claimDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        ClaimDetails testClaimDetails = claimDetailsList.get(claimDetailsList.size() - 1);
        assertThat(testClaimDetails.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testClaimDetails.getStartdate()).isEqualTo(DEFAULT_STARTDATE);
        assertThat(testClaimDetails.getEnddate()).isEqualTo(DEFAULT_ENDDATE);
        assertThat(testClaimDetails.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testClaimDetails.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testClaimDetails.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createClaimDetailsWithExistingId() throws Exception {
        // Create the ClaimDetails with an existing ID
        claimDetails.setId(1L);

        int databaseSizeBeforeCreate = claimDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClaimDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimDetails in the database
        List<ClaimDetails> claimDetailsList = claimDetailsRepository.findAll();
        assertThat(claimDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClaimDetails() throws Exception {
        // Initialize the database
        claimDetailsRepository.saveAndFlush(claimDetails);

        // Get all the claimDetailsList
        restClaimDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(claimDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.[*].startdate").value(hasItem(DEFAULT_STARTDATE.toString())))
            .andExpect(jsonPath("$.[*].enddate").value(hasItem(DEFAULT_ENDDATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getClaimDetails() throws Exception {
        // Initialize the database
        claimDetailsRepository.saveAndFlush(claimDetails);

        // Get the claimDetails
        restClaimDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, claimDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(claimDetails.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT))
            .andExpect(jsonPath("$.startdate").value(DEFAULT_STARTDATE.toString()))
            .andExpect(jsonPath("$.enddate").value(DEFAULT_ENDDATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getClaimDetailsByIdFiltering() throws Exception {
        // Initialize the database
        claimDetailsRepository.saveAndFlush(claimDetails);

        Long id = claimDetails.getId();

        defaultClaimDetailsShouldBeFound("id.equals=" + id);
        defaultClaimDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultClaimDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClaimDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultClaimDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClaimDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllClaimDetailsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        claimDetailsRepository.saveAndFlush(claimDetails);

        // Get all the claimDetailsList where amount equals to DEFAULT_AMOUNT
        defaultClaimDetailsShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the claimDetailsList where amount equals to UPDATED_AMOUNT
        defaultClaimDetailsShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllClaimDetailsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        claimDetailsRepository.saveAndFlush(claimDetails);

        // Get all the claimDetailsList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultClaimDetailsShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the claimDetailsList where amount equals to UPDATED_AMOUNT
        defaultClaimDetailsShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllClaimDetailsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimDetailsRepository.saveAndFlush(claimDetails);

        // Get all the claimDetailsList where amount is not null
        defaultClaimDetailsShouldBeFound("amount.specified=true");

        // Get all the claimDetailsList where amount is null
        defaultClaimDetailsShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimDetailsByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        claimDetailsRepository.saveAndFlush(claimDetails);

        // Get all the claimDetailsList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultClaimDetailsShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the claimDetailsList where amount is greater than or equal to UPDATED_AMOUNT
        defaultClaimDetailsShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllClaimDetailsByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        claimDetailsRepository.saveAndFlush(claimDetails);

        // Get all the claimDetailsList where amount is less than or equal to DEFAULT_AMOUNT
        defaultClaimDetailsShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the claimDetailsList where amount is less than or equal to SMALLER_AMOUNT
        defaultClaimDetailsShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllClaimDetailsByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        claimDetailsRepository.saveAndFlush(claimDetails);

        // Get all the claimDetailsList where amount is less than DEFAULT_AMOUNT
        defaultClaimDetailsShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the claimDetailsList where amount is less than UPDATED_AMOUNT
        defaultClaimDetailsShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllClaimDetailsByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        claimDetailsRepository.saveAndFlush(claimDetails);

        // Get all the claimDetailsList where amount is greater than DEFAULT_AMOUNT
        defaultClaimDetailsShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the claimDetailsList where amount is greater than SMALLER_AMOUNT
        defaultClaimDetailsShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllClaimDetailsByStartdateIsEqualToSomething() throws Exception {
        // Initialize the database
        claimDetailsRepository.saveAndFlush(claimDetails);

        // Get all the claimDetailsList where startdate equals to DEFAULT_STARTDATE
        defaultClaimDetailsShouldBeFound("startdate.equals=" + DEFAULT_STARTDATE);

        // Get all the claimDetailsList where startdate equals to UPDATED_STARTDATE
        defaultClaimDetailsShouldNotBeFound("startdate.equals=" + UPDATED_STARTDATE);
    }

    @Test
    @Transactional
    void getAllClaimDetailsByStartdateIsInShouldWork() throws Exception {
        // Initialize the database
        claimDetailsRepository.saveAndFlush(claimDetails);

        // Get all the claimDetailsList where startdate in DEFAULT_STARTDATE or UPDATED_STARTDATE
        defaultClaimDetailsShouldBeFound("startdate.in=" + DEFAULT_STARTDATE + "," + UPDATED_STARTDATE);

        // Get all the claimDetailsList where startdate equals to UPDATED_STARTDATE
        defaultClaimDetailsShouldNotBeFound("startdate.in=" + UPDATED_STARTDATE);
    }

    @Test
    @Transactional
    void getAllClaimDetailsByStartdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimDetailsRepository.saveAndFlush(claimDetails);

        // Get all the claimDetailsList where startdate is not null
        defaultClaimDetailsShouldBeFound("startdate.specified=true");

        // Get all the claimDetailsList where startdate is null
        defaultClaimDetailsShouldNotBeFound("startdate.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimDetailsByEnddateIsEqualToSomething() throws Exception {
        // Initialize the database
        claimDetailsRepository.saveAndFlush(claimDetails);

        // Get all the claimDetailsList where enddate equals to DEFAULT_ENDDATE
        defaultClaimDetailsShouldBeFound("enddate.equals=" + DEFAULT_ENDDATE);

        // Get all the claimDetailsList where enddate equals to UPDATED_ENDDATE
        defaultClaimDetailsShouldNotBeFound("enddate.equals=" + UPDATED_ENDDATE);
    }

    @Test
    @Transactional
    void getAllClaimDetailsByEnddateIsInShouldWork() throws Exception {
        // Initialize the database
        claimDetailsRepository.saveAndFlush(claimDetails);

        // Get all the claimDetailsList where enddate in DEFAULT_ENDDATE or UPDATED_ENDDATE
        defaultClaimDetailsShouldBeFound("enddate.in=" + DEFAULT_ENDDATE + "," + UPDATED_ENDDATE);

        // Get all the claimDetailsList where enddate equals to UPDATED_ENDDATE
        defaultClaimDetailsShouldNotBeFound("enddate.in=" + UPDATED_ENDDATE);
    }

    @Test
    @Transactional
    void getAllClaimDetailsByEnddateIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimDetailsRepository.saveAndFlush(claimDetails);

        // Get all the claimDetailsList where enddate is not null
        defaultClaimDetailsShouldBeFound("enddate.specified=true");

        // Get all the claimDetailsList where enddate is null
        defaultClaimDetailsShouldNotBeFound("enddate.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimDetailsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        claimDetailsRepository.saveAndFlush(claimDetails);

        // Get all the claimDetailsList where description equals to DEFAULT_DESCRIPTION
        defaultClaimDetailsShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the claimDetailsList where description equals to UPDATED_DESCRIPTION
        defaultClaimDetailsShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllClaimDetailsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        claimDetailsRepository.saveAndFlush(claimDetails);

        // Get all the claimDetailsList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultClaimDetailsShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the claimDetailsList where description equals to UPDATED_DESCRIPTION
        defaultClaimDetailsShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllClaimDetailsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimDetailsRepository.saveAndFlush(claimDetails);

        // Get all the claimDetailsList where description is not null
        defaultClaimDetailsShouldBeFound("description.specified=true");

        // Get all the claimDetailsList where description is null
        defaultClaimDetailsShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimDetailsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        claimDetailsRepository.saveAndFlush(claimDetails);

        // Get all the claimDetailsList where description contains DEFAULT_DESCRIPTION
        defaultClaimDetailsShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the claimDetailsList where description contains UPDATED_DESCRIPTION
        defaultClaimDetailsShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllClaimDetailsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        claimDetailsRepository.saveAndFlush(claimDetails);

        // Get all the claimDetailsList where description does not contain DEFAULT_DESCRIPTION
        defaultClaimDetailsShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the claimDetailsList where description does not contain UPDATED_DESCRIPTION
        defaultClaimDetailsShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllClaimDetailsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        claimDetailsRepository.saveAndFlush(claimDetails);

        // Get all the claimDetailsList where createdat equals to DEFAULT_CREATEDAT
        defaultClaimDetailsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the claimDetailsList where createdat equals to UPDATED_CREATEDAT
        defaultClaimDetailsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllClaimDetailsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        claimDetailsRepository.saveAndFlush(claimDetails);

        // Get all the claimDetailsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultClaimDetailsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the claimDetailsList where createdat equals to UPDATED_CREATEDAT
        defaultClaimDetailsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllClaimDetailsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimDetailsRepository.saveAndFlush(claimDetails);

        // Get all the claimDetailsList where createdat is not null
        defaultClaimDetailsShouldBeFound("createdat.specified=true");

        // Get all the claimDetailsList where createdat is null
        defaultClaimDetailsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimDetailsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        claimDetailsRepository.saveAndFlush(claimDetails);

        // Get all the claimDetailsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultClaimDetailsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the claimDetailsList where updatedat equals to UPDATED_UPDATEDAT
        defaultClaimDetailsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllClaimDetailsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        claimDetailsRepository.saveAndFlush(claimDetails);

        // Get all the claimDetailsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultClaimDetailsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the claimDetailsList where updatedat equals to UPDATED_UPDATEDAT
        defaultClaimDetailsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllClaimDetailsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimDetailsRepository.saveAndFlush(claimDetails);

        // Get all the claimDetailsList where updatedat is not null
        defaultClaimDetailsShouldBeFound("updatedat.specified=true");

        // Get all the claimDetailsList where updatedat is null
        defaultClaimDetailsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimDetailsByClaimrequestIsEqualToSomething() throws Exception {
        ClaimRequests claimrequest;
        if (TestUtil.findAll(em, ClaimRequests.class).isEmpty()) {
            claimDetailsRepository.saveAndFlush(claimDetails);
            claimrequest = ClaimRequestsResourceIT.createEntity(em);
        } else {
            claimrequest = TestUtil.findAll(em, ClaimRequests.class).get(0);
        }
        em.persist(claimrequest);
        em.flush();
        claimDetails.setClaimrequest(claimrequest);
        claimDetailsRepository.saveAndFlush(claimDetails);
        Long claimrequestId = claimrequest.getId();

        // Get all the claimDetailsList where claimrequest equals to claimrequestId
        defaultClaimDetailsShouldBeFound("claimrequestId.equals=" + claimrequestId);

        // Get all the claimDetailsList where claimrequest equals to (claimrequestId + 1)
        defaultClaimDetailsShouldNotBeFound("claimrequestId.equals=" + (claimrequestId + 1));
    }

    @Test
    @Transactional
    void getAllClaimDetailsByClaimtypeIsEqualToSomething() throws Exception {
        ClaimTypes claimtype;
        if (TestUtil.findAll(em, ClaimTypes.class).isEmpty()) {
            claimDetailsRepository.saveAndFlush(claimDetails);
            claimtype = ClaimTypesResourceIT.createEntity(em);
        } else {
            claimtype = TestUtil.findAll(em, ClaimTypes.class).get(0);
        }
        em.persist(claimtype);
        em.flush();
        claimDetails.setClaimtype(claimtype);
        claimDetailsRepository.saveAndFlush(claimDetails);
        Long claimtypeId = claimtype.getId();

        // Get all the claimDetailsList where claimtype equals to claimtypeId
        defaultClaimDetailsShouldBeFound("claimtypeId.equals=" + claimtypeId);

        // Get all the claimDetailsList where claimtype equals to (claimtypeId + 1)
        defaultClaimDetailsShouldNotBeFound("claimtypeId.equals=" + (claimtypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClaimDetailsShouldBeFound(String filter) throws Exception {
        restClaimDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(claimDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.[*].startdate").value(hasItem(DEFAULT_STARTDATE.toString())))
            .andExpect(jsonPath("$.[*].enddate").value(hasItem(DEFAULT_ENDDATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restClaimDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClaimDetailsShouldNotBeFound(String filter) throws Exception {
        restClaimDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClaimDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingClaimDetails() throws Exception {
        // Get the claimDetails
        restClaimDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingClaimDetails() throws Exception {
        // Initialize the database
        claimDetailsRepository.saveAndFlush(claimDetails);

        int databaseSizeBeforeUpdate = claimDetailsRepository.findAll().size();

        // Update the claimDetails
        ClaimDetails updatedClaimDetails = claimDetailsRepository.findById(claimDetails.getId()).get();
        // Disconnect from session so that the updates on updatedClaimDetails are not directly saved in db
        em.detach(updatedClaimDetails);
        updatedClaimDetails
            .amount(UPDATED_AMOUNT)
            .startdate(UPDATED_STARTDATE)
            .enddate(UPDATED_ENDDATE)
            .description(UPDATED_DESCRIPTION)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restClaimDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClaimDetails.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedClaimDetails))
            )
            .andExpect(status().isOk());

        // Validate the ClaimDetails in the database
        List<ClaimDetails> claimDetailsList = claimDetailsRepository.findAll();
        assertThat(claimDetailsList).hasSize(databaseSizeBeforeUpdate);
        ClaimDetails testClaimDetails = claimDetailsList.get(claimDetailsList.size() - 1);
        assertThat(testClaimDetails.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testClaimDetails.getStartdate()).isEqualTo(UPDATED_STARTDATE);
        assertThat(testClaimDetails.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testClaimDetails.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testClaimDetails.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testClaimDetails.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingClaimDetails() throws Exception {
        int databaseSizeBeforeUpdate = claimDetailsRepository.findAll().size();
        claimDetails.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClaimDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, claimDetails.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimDetails in the database
        List<ClaimDetails> claimDetailsList = claimDetailsRepository.findAll();
        assertThat(claimDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClaimDetails() throws Exception {
        int databaseSizeBeforeUpdate = claimDetailsRepository.findAll().size();
        claimDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimDetails in the database
        List<ClaimDetails> claimDetailsList = claimDetailsRepository.findAll();
        assertThat(claimDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClaimDetails() throws Exception {
        int databaseSizeBeforeUpdate = claimDetailsRepository.findAll().size();
        claimDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimDetailsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimDetails))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClaimDetails in the database
        List<ClaimDetails> claimDetailsList = claimDetailsRepository.findAll();
        assertThat(claimDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClaimDetailsWithPatch() throws Exception {
        // Initialize the database
        claimDetailsRepository.saveAndFlush(claimDetails);

        int databaseSizeBeforeUpdate = claimDetailsRepository.findAll().size();

        // Update the claimDetails using partial update
        ClaimDetails partialUpdatedClaimDetails = new ClaimDetails();
        partialUpdatedClaimDetails.setId(claimDetails.getId());

        partialUpdatedClaimDetails.description(UPDATED_DESCRIPTION);

        restClaimDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClaimDetails.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClaimDetails))
            )
            .andExpect(status().isOk());

        // Validate the ClaimDetails in the database
        List<ClaimDetails> claimDetailsList = claimDetailsRepository.findAll();
        assertThat(claimDetailsList).hasSize(databaseSizeBeforeUpdate);
        ClaimDetails testClaimDetails = claimDetailsList.get(claimDetailsList.size() - 1);
        assertThat(testClaimDetails.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testClaimDetails.getStartdate()).isEqualTo(DEFAULT_STARTDATE);
        assertThat(testClaimDetails.getEnddate()).isEqualTo(DEFAULT_ENDDATE);
        assertThat(testClaimDetails.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testClaimDetails.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testClaimDetails.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateClaimDetailsWithPatch() throws Exception {
        // Initialize the database
        claimDetailsRepository.saveAndFlush(claimDetails);

        int databaseSizeBeforeUpdate = claimDetailsRepository.findAll().size();

        // Update the claimDetails using partial update
        ClaimDetails partialUpdatedClaimDetails = new ClaimDetails();
        partialUpdatedClaimDetails.setId(claimDetails.getId());

        partialUpdatedClaimDetails
            .amount(UPDATED_AMOUNT)
            .startdate(UPDATED_STARTDATE)
            .enddate(UPDATED_ENDDATE)
            .description(UPDATED_DESCRIPTION)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restClaimDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClaimDetails.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClaimDetails))
            )
            .andExpect(status().isOk());

        // Validate the ClaimDetails in the database
        List<ClaimDetails> claimDetailsList = claimDetailsRepository.findAll();
        assertThat(claimDetailsList).hasSize(databaseSizeBeforeUpdate);
        ClaimDetails testClaimDetails = claimDetailsList.get(claimDetailsList.size() - 1);
        assertThat(testClaimDetails.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testClaimDetails.getStartdate()).isEqualTo(UPDATED_STARTDATE);
        assertThat(testClaimDetails.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testClaimDetails.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testClaimDetails.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testClaimDetails.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingClaimDetails() throws Exception {
        int databaseSizeBeforeUpdate = claimDetailsRepository.findAll().size();
        claimDetails.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClaimDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, claimDetails.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(claimDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimDetails in the database
        List<ClaimDetails> claimDetailsList = claimDetailsRepository.findAll();
        assertThat(claimDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClaimDetails() throws Exception {
        int databaseSizeBeforeUpdate = claimDetailsRepository.findAll().size();
        claimDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(claimDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimDetails in the database
        List<ClaimDetails> claimDetailsList = claimDetailsRepository.findAll();
        assertThat(claimDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClaimDetails() throws Exception {
        int databaseSizeBeforeUpdate = claimDetailsRepository.findAll().size();
        claimDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(claimDetails))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClaimDetails in the database
        List<ClaimDetails> claimDetailsList = claimDetailsRepository.findAll();
        assertThat(claimDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClaimDetails() throws Exception {
        // Initialize the database
        claimDetailsRepository.saveAndFlush(claimDetails);

        int databaseSizeBeforeDelete = claimDetailsRepository.findAll().size();

        // Delete the claimDetails
        restClaimDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, claimDetails.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClaimDetails> claimDetailsList = claimDetailsRepository.findAll();
        assertThat(claimDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
