package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.ClaimDetails;
import com.venturedive.vendian_mono.domain.ClaimTypes;
import com.venturedive.vendian_mono.repository.ClaimTypesRepository;
import com.venturedive.vendian_mono.service.criteria.ClaimTypesCriteria;
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
 * Integration tests for the {@link ClaimTypesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClaimTypesResourceIT {

    private static final String DEFAULT_CLAIMTYPE = "AAAAAAAAAA";
    private static final String UPDATED_CLAIMTYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DATERANGEREQUIRED = false;
    private static final Boolean UPDATED_DATERANGEREQUIRED = true;

    private static final Boolean DEFAULT_DESCRIPTIONREQUIRED = false;
    private static final Boolean UPDATED_DESCRIPTIONREQUIRED = true;

    private static final Integer DEFAULT_PARENTID = 1;
    private static final Integer UPDATED_PARENTID = 2;
    private static final Integer SMALLER_PARENTID = 1 - 1;

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/claim-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClaimTypesRepository claimTypesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClaimTypesMockMvc;

    private ClaimTypes claimTypes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClaimTypes createEntity(EntityManager em) {
        ClaimTypes claimTypes = new ClaimTypes()
            .claimtype(DEFAULT_CLAIMTYPE)
            .daterangerequired(DEFAULT_DATERANGEREQUIRED)
            .descriptionrequired(DEFAULT_DESCRIPTIONREQUIRED)
            .parentid(DEFAULT_PARENTID)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        return claimTypes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClaimTypes createUpdatedEntity(EntityManager em) {
        ClaimTypes claimTypes = new ClaimTypes()
            .claimtype(UPDATED_CLAIMTYPE)
            .daterangerequired(UPDATED_DATERANGEREQUIRED)
            .descriptionrequired(UPDATED_DESCRIPTIONREQUIRED)
            .parentid(UPDATED_PARENTID)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        return claimTypes;
    }

    @BeforeEach
    public void initTest() {
        claimTypes = createEntity(em);
    }

    @Test
    @Transactional
    void createClaimTypes() throws Exception {
        int databaseSizeBeforeCreate = claimTypesRepository.findAll().size();
        // Create the ClaimTypes
        restClaimTypesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimTypes))
            )
            .andExpect(status().isCreated());

        // Validate the ClaimTypes in the database
        List<ClaimTypes> claimTypesList = claimTypesRepository.findAll();
        assertThat(claimTypesList).hasSize(databaseSizeBeforeCreate + 1);
        ClaimTypes testClaimTypes = claimTypesList.get(claimTypesList.size() - 1);
        assertThat(testClaimTypes.getClaimtype()).isEqualTo(DEFAULT_CLAIMTYPE);
        assertThat(testClaimTypes.getDaterangerequired()).isEqualTo(DEFAULT_DATERANGEREQUIRED);
        assertThat(testClaimTypes.getDescriptionrequired()).isEqualTo(DEFAULT_DESCRIPTIONREQUIRED);
        assertThat(testClaimTypes.getParentid()).isEqualTo(DEFAULT_PARENTID);
        assertThat(testClaimTypes.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testClaimTypes.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createClaimTypesWithExistingId() throws Exception {
        // Create the ClaimTypes with an existing ID
        claimTypes.setId(1L);

        int databaseSizeBeforeCreate = claimTypesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClaimTypesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimTypes))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimTypes in the database
        List<ClaimTypes> claimTypesList = claimTypesRepository.findAll();
        assertThat(claimTypesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClaimTypes() throws Exception {
        // Initialize the database
        claimTypesRepository.saveAndFlush(claimTypes);

        // Get all the claimTypesList
        restClaimTypesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(claimTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].claimtype").value(hasItem(DEFAULT_CLAIMTYPE)))
            .andExpect(jsonPath("$.[*].daterangerequired").value(hasItem(DEFAULT_DATERANGEREQUIRED.booleanValue())))
            .andExpect(jsonPath("$.[*].descriptionrequired").value(hasItem(DEFAULT_DESCRIPTIONREQUIRED.booleanValue())))
            .andExpect(jsonPath("$.[*].parentid").value(hasItem(DEFAULT_PARENTID)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getClaimTypes() throws Exception {
        // Initialize the database
        claimTypesRepository.saveAndFlush(claimTypes);

        // Get the claimTypes
        restClaimTypesMockMvc
            .perform(get(ENTITY_API_URL_ID, claimTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(claimTypes.getId().intValue()))
            .andExpect(jsonPath("$.claimtype").value(DEFAULT_CLAIMTYPE))
            .andExpect(jsonPath("$.daterangerequired").value(DEFAULT_DATERANGEREQUIRED.booleanValue()))
            .andExpect(jsonPath("$.descriptionrequired").value(DEFAULT_DESCRIPTIONREQUIRED.booleanValue()))
            .andExpect(jsonPath("$.parentid").value(DEFAULT_PARENTID))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getClaimTypesByIdFiltering() throws Exception {
        // Initialize the database
        claimTypesRepository.saveAndFlush(claimTypes);

        Long id = claimTypes.getId();

        defaultClaimTypesShouldBeFound("id.equals=" + id);
        defaultClaimTypesShouldNotBeFound("id.notEquals=" + id);

        defaultClaimTypesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClaimTypesShouldNotBeFound("id.greaterThan=" + id);

        defaultClaimTypesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClaimTypesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllClaimTypesByClaimtypeIsEqualToSomething() throws Exception {
        // Initialize the database
        claimTypesRepository.saveAndFlush(claimTypes);

        // Get all the claimTypesList where claimtype equals to DEFAULT_CLAIMTYPE
        defaultClaimTypesShouldBeFound("claimtype.equals=" + DEFAULT_CLAIMTYPE);

        // Get all the claimTypesList where claimtype equals to UPDATED_CLAIMTYPE
        defaultClaimTypesShouldNotBeFound("claimtype.equals=" + UPDATED_CLAIMTYPE);
    }

    @Test
    @Transactional
    void getAllClaimTypesByClaimtypeIsInShouldWork() throws Exception {
        // Initialize the database
        claimTypesRepository.saveAndFlush(claimTypes);

        // Get all the claimTypesList where claimtype in DEFAULT_CLAIMTYPE or UPDATED_CLAIMTYPE
        defaultClaimTypesShouldBeFound("claimtype.in=" + DEFAULT_CLAIMTYPE + "," + UPDATED_CLAIMTYPE);

        // Get all the claimTypesList where claimtype equals to UPDATED_CLAIMTYPE
        defaultClaimTypesShouldNotBeFound("claimtype.in=" + UPDATED_CLAIMTYPE);
    }

    @Test
    @Transactional
    void getAllClaimTypesByClaimtypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimTypesRepository.saveAndFlush(claimTypes);

        // Get all the claimTypesList where claimtype is not null
        defaultClaimTypesShouldBeFound("claimtype.specified=true");

        // Get all the claimTypesList where claimtype is null
        defaultClaimTypesShouldNotBeFound("claimtype.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimTypesByClaimtypeContainsSomething() throws Exception {
        // Initialize the database
        claimTypesRepository.saveAndFlush(claimTypes);

        // Get all the claimTypesList where claimtype contains DEFAULT_CLAIMTYPE
        defaultClaimTypesShouldBeFound("claimtype.contains=" + DEFAULT_CLAIMTYPE);

        // Get all the claimTypesList where claimtype contains UPDATED_CLAIMTYPE
        defaultClaimTypesShouldNotBeFound("claimtype.contains=" + UPDATED_CLAIMTYPE);
    }

    @Test
    @Transactional
    void getAllClaimTypesByClaimtypeNotContainsSomething() throws Exception {
        // Initialize the database
        claimTypesRepository.saveAndFlush(claimTypes);

        // Get all the claimTypesList where claimtype does not contain DEFAULT_CLAIMTYPE
        defaultClaimTypesShouldNotBeFound("claimtype.doesNotContain=" + DEFAULT_CLAIMTYPE);

        // Get all the claimTypesList where claimtype does not contain UPDATED_CLAIMTYPE
        defaultClaimTypesShouldBeFound("claimtype.doesNotContain=" + UPDATED_CLAIMTYPE);
    }

    @Test
    @Transactional
    void getAllClaimTypesByDaterangerequiredIsEqualToSomething() throws Exception {
        // Initialize the database
        claimTypesRepository.saveAndFlush(claimTypes);

        // Get all the claimTypesList where daterangerequired equals to DEFAULT_DATERANGEREQUIRED
        defaultClaimTypesShouldBeFound("daterangerequired.equals=" + DEFAULT_DATERANGEREQUIRED);

        // Get all the claimTypesList where daterangerequired equals to UPDATED_DATERANGEREQUIRED
        defaultClaimTypesShouldNotBeFound("daterangerequired.equals=" + UPDATED_DATERANGEREQUIRED);
    }

    @Test
    @Transactional
    void getAllClaimTypesByDaterangerequiredIsInShouldWork() throws Exception {
        // Initialize the database
        claimTypesRepository.saveAndFlush(claimTypes);

        // Get all the claimTypesList where daterangerequired in DEFAULT_DATERANGEREQUIRED or UPDATED_DATERANGEREQUIRED
        defaultClaimTypesShouldBeFound("daterangerequired.in=" + DEFAULT_DATERANGEREQUIRED + "," + UPDATED_DATERANGEREQUIRED);

        // Get all the claimTypesList where daterangerequired equals to UPDATED_DATERANGEREQUIRED
        defaultClaimTypesShouldNotBeFound("daterangerequired.in=" + UPDATED_DATERANGEREQUIRED);
    }

    @Test
    @Transactional
    void getAllClaimTypesByDaterangerequiredIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimTypesRepository.saveAndFlush(claimTypes);

        // Get all the claimTypesList where daterangerequired is not null
        defaultClaimTypesShouldBeFound("daterangerequired.specified=true");

        // Get all the claimTypesList where daterangerequired is null
        defaultClaimTypesShouldNotBeFound("daterangerequired.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimTypesByDescriptionrequiredIsEqualToSomething() throws Exception {
        // Initialize the database
        claimTypesRepository.saveAndFlush(claimTypes);

        // Get all the claimTypesList where descriptionrequired equals to DEFAULT_DESCRIPTIONREQUIRED
        defaultClaimTypesShouldBeFound("descriptionrequired.equals=" + DEFAULT_DESCRIPTIONREQUIRED);

        // Get all the claimTypesList where descriptionrequired equals to UPDATED_DESCRIPTIONREQUIRED
        defaultClaimTypesShouldNotBeFound("descriptionrequired.equals=" + UPDATED_DESCRIPTIONREQUIRED);
    }

    @Test
    @Transactional
    void getAllClaimTypesByDescriptionrequiredIsInShouldWork() throws Exception {
        // Initialize the database
        claimTypesRepository.saveAndFlush(claimTypes);

        // Get all the claimTypesList where descriptionrequired in DEFAULT_DESCRIPTIONREQUIRED or UPDATED_DESCRIPTIONREQUIRED
        defaultClaimTypesShouldBeFound("descriptionrequired.in=" + DEFAULT_DESCRIPTIONREQUIRED + "," + UPDATED_DESCRIPTIONREQUIRED);

        // Get all the claimTypesList where descriptionrequired equals to UPDATED_DESCRIPTIONREQUIRED
        defaultClaimTypesShouldNotBeFound("descriptionrequired.in=" + UPDATED_DESCRIPTIONREQUIRED);
    }

    @Test
    @Transactional
    void getAllClaimTypesByDescriptionrequiredIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimTypesRepository.saveAndFlush(claimTypes);

        // Get all the claimTypesList where descriptionrequired is not null
        defaultClaimTypesShouldBeFound("descriptionrequired.specified=true");

        // Get all the claimTypesList where descriptionrequired is null
        defaultClaimTypesShouldNotBeFound("descriptionrequired.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimTypesByParentidIsEqualToSomething() throws Exception {
        // Initialize the database
        claimTypesRepository.saveAndFlush(claimTypes);

        // Get all the claimTypesList where parentid equals to DEFAULT_PARENTID
        defaultClaimTypesShouldBeFound("parentid.equals=" + DEFAULT_PARENTID);

        // Get all the claimTypesList where parentid equals to UPDATED_PARENTID
        defaultClaimTypesShouldNotBeFound("parentid.equals=" + UPDATED_PARENTID);
    }

    @Test
    @Transactional
    void getAllClaimTypesByParentidIsInShouldWork() throws Exception {
        // Initialize the database
        claimTypesRepository.saveAndFlush(claimTypes);

        // Get all the claimTypesList where parentid in DEFAULT_PARENTID or UPDATED_PARENTID
        defaultClaimTypesShouldBeFound("parentid.in=" + DEFAULT_PARENTID + "," + UPDATED_PARENTID);

        // Get all the claimTypesList where parentid equals to UPDATED_PARENTID
        defaultClaimTypesShouldNotBeFound("parentid.in=" + UPDATED_PARENTID);
    }

    @Test
    @Transactional
    void getAllClaimTypesByParentidIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimTypesRepository.saveAndFlush(claimTypes);

        // Get all the claimTypesList where parentid is not null
        defaultClaimTypesShouldBeFound("parentid.specified=true");

        // Get all the claimTypesList where parentid is null
        defaultClaimTypesShouldNotBeFound("parentid.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimTypesByParentidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        claimTypesRepository.saveAndFlush(claimTypes);

        // Get all the claimTypesList where parentid is greater than or equal to DEFAULT_PARENTID
        defaultClaimTypesShouldBeFound("parentid.greaterThanOrEqual=" + DEFAULT_PARENTID);

        // Get all the claimTypesList where parentid is greater than or equal to UPDATED_PARENTID
        defaultClaimTypesShouldNotBeFound("parentid.greaterThanOrEqual=" + UPDATED_PARENTID);
    }

    @Test
    @Transactional
    void getAllClaimTypesByParentidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        claimTypesRepository.saveAndFlush(claimTypes);

        // Get all the claimTypesList where parentid is less than or equal to DEFAULT_PARENTID
        defaultClaimTypesShouldBeFound("parentid.lessThanOrEqual=" + DEFAULT_PARENTID);

        // Get all the claimTypesList where parentid is less than or equal to SMALLER_PARENTID
        defaultClaimTypesShouldNotBeFound("parentid.lessThanOrEqual=" + SMALLER_PARENTID);
    }

    @Test
    @Transactional
    void getAllClaimTypesByParentidIsLessThanSomething() throws Exception {
        // Initialize the database
        claimTypesRepository.saveAndFlush(claimTypes);

        // Get all the claimTypesList where parentid is less than DEFAULT_PARENTID
        defaultClaimTypesShouldNotBeFound("parentid.lessThan=" + DEFAULT_PARENTID);

        // Get all the claimTypesList where parentid is less than UPDATED_PARENTID
        defaultClaimTypesShouldBeFound("parentid.lessThan=" + UPDATED_PARENTID);
    }

    @Test
    @Transactional
    void getAllClaimTypesByParentidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        claimTypesRepository.saveAndFlush(claimTypes);

        // Get all the claimTypesList where parentid is greater than DEFAULT_PARENTID
        defaultClaimTypesShouldNotBeFound("parentid.greaterThan=" + DEFAULT_PARENTID);

        // Get all the claimTypesList where parentid is greater than SMALLER_PARENTID
        defaultClaimTypesShouldBeFound("parentid.greaterThan=" + SMALLER_PARENTID);
    }

    @Test
    @Transactional
    void getAllClaimTypesByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        claimTypesRepository.saveAndFlush(claimTypes);

        // Get all the claimTypesList where createdat equals to DEFAULT_CREATEDAT
        defaultClaimTypesShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the claimTypesList where createdat equals to UPDATED_CREATEDAT
        defaultClaimTypesShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllClaimTypesByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        claimTypesRepository.saveAndFlush(claimTypes);

        // Get all the claimTypesList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultClaimTypesShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the claimTypesList where createdat equals to UPDATED_CREATEDAT
        defaultClaimTypesShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllClaimTypesByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimTypesRepository.saveAndFlush(claimTypes);

        // Get all the claimTypesList where createdat is not null
        defaultClaimTypesShouldBeFound("createdat.specified=true");

        // Get all the claimTypesList where createdat is null
        defaultClaimTypesShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimTypesByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        claimTypesRepository.saveAndFlush(claimTypes);

        // Get all the claimTypesList where updatedat equals to DEFAULT_UPDATEDAT
        defaultClaimTypesShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the claimTypesList where updatedat equals to UPDATED_UPDATEDAT
        defaultClaimTypesShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllClaimTypesByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        claimTypesRepository.saveAndFlush(claimTypes);

        // Get all the claimTypesList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultClaimTypesShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the claimTypesList where updatedat equals to UPDATED_UPDATEDAT
        defaultClaimTypesShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllClaimTypesByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimTypesRepository.saveAndFlush(claimTypes);

        // Get all the claimTypesList where updatedat is not null
        defaultClaimTypesShouldBeFound("updatedat.specified=true");

        // Get all the claimTypesList where updatedat is null
        defaultClaimTypesShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimTypesByClaimdetailsClaimtypeIsEqualToSomething() throws Exception {
        ClaimDetails claimdetailsClaimtype;
        if (TestUtil.findAll(em, ClaimDetails.class).isEmpty()) {
            claimTypesRepository.saveAndFlush(claimTypes);
            claimdetailsClaimtype = ClaimDetailsResourceIT.createEntity(em);
        } else {
            claimdetailsClaimtype = TestUtil.findAll(em, ClaimDetails.class).get(0);
        }
        em.persist(claimdetailsClaimtype);
        em.flush();
        claimTypes.addClaimdetailsClaimtype(claimdetailsClaimtype);
        claimTypesRepository.saveAndFlush(claimTypes);
        Long claimdetailsClaimtypeId = claimdetailsClaimtype.getId();

        // Get all the claimTypesList where claimdetailsClaimtype equals to claimdetailsClaimtypeId
        defaultClaimTypesShouldBeFound("claimdetailsClaimtypeId.equals=" + claimdetailsClaimtypeId);

        // Get all the claimTypesList where claimdetailsClaimtype equals to (claimdetailsClaimtypeId + 1)
        defaultClaimTypesShouldNotBeFound("claimdetailsClaimtypeId.equals=" + (claimdetailsClaimtypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClaimTypesShouldBeFound(String filter) throws Exception {
        restClaimTypesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(claimTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].claimtype").value(hasItem(DEFAULT_CLAIMTYPE)))
            .andExpect(jsonPath("$.[*].daterangerequired").value(hasItem(DEFAULT_DATERANGEREQUIRED.booleanValue())))
            .andExpect(jsonPath("$.[*].descriptionrequired").value(hasItem(DEFAULT_DESCRIPTIONREQUIRED.booleanValue())))
            .andExpect(jsonPath("$.[*].parentid").value(hasItem(DEFAULT_PARENTID)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restClaimTypesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClaimTypesShouldNotBeFound(String filter) throws Exception {
        restClaimTypesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClaimTypesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingClaimTypes() throws Exception {
        // Get the claimTypes
        restClaimTypesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingClaimTypes() throws Exception {
        // Initialize the database
        claimTypesRepository.saveAndFlush(claimTypes);

        int databaseSizeBeforeUpdate = claimTypesRepository.findAll().size();

        // Update the claimTypes
        ClaimTypes updatedClaimTypes = claimTypesRepository.findById(claimTypes.getId()).get();
        // Disconnect from session so that the updates on updatedClaimTypes are not directly saved in db
        em.detach(updatedClaimTypes);
        updatedClaimTypes
            .claimtype(UPDATED_CLAIMTYPE)
            .daterangerequired(UPDATED_DATERANGEREQUIRED)
            .descriptionrequired(UPDATED_DESCRIPTIONREQUIRED)
            .parentid(UPDATED_PARENTID)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restClaimTypesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClaimTypes.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedClaimTypes))
            )
            .andExpect(status().isOk());

        // Validate the ClaimTypes in the database
        List<ClaimTypes> claimTypesList = claimTypesRepository.findAll();
        assertThat(claimTypesList).hasSize(databaseSizeBeforeUpdate);
        ClaimTypes testClaimTypes = claimTypesList.get(claimTypesList.size() - 1);
        assertThat(testClaimTypes.getClaimtype()).isEqualTo(UPDATED_CLAIMTYPE);
        assertThat(testClaimTypes.getDaterangerequired()).isEqualTo(UPDATED_DATERANGEREQUIRED);
        assertThat(testClaimTypes.getDescriptionrequired()).isEqualTo(UPDATED_DESCRIPTIONREQUIRED);
        assertThat(testClaimTypes.getParentid()).isEqualTo(UPDATED_PARENTID);
        assertThat(testClaimTypes.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testClaimTypes.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingClaimTypes() throws Exception {
        int databaseSizeBeforeUpdate = claimTypesRepository.findAll().size();
        claimTypes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClaimTypesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, claimTypes.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimTypes))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimTypes in the database
        List<ClaimTypes> claimTypesList = claimTypesRepository.findAll();
        assertThat(claimTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClaimTypes() throws Exception {
        int databaseSizeBeforeUpdate = claimTypesRepository.findAll().size();
        claimTypes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimTypesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimTypes))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimTypes in the database
        List<ClaimTypes> claimTypesList = claimTypesRepository.findAll();
        assertThat(claimTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClaimTypes() throws Exception {
        int databaseSizeBeforeUpdate = claimTypesRepository.findAll().size();
        claimTypes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimTypesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimTypes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClaimTypes in the database
        List<ClaimTypes> claimTypesList = claimTypesRepository.findAll();
        assertThat(claimTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClaimTypesWithPatch() throws Exception {
        // Initialize the database
        claimTypesRepository.saveAndFlush(claimTypes);

        int databaseSizeBeforeUpdate = claimTypesRepository.findAll().size();

        // Update the claimTypes using partial update
        ClaimTypes partialUpdatedClaimTypes = new ClaimTypes();
        partialUpdatedClaimTypes.setId(claimTypes.getId());

        partialUpdatedClaimTypes
            .claimtype(UPDATED_CLAIMTYPE)
            .daterangerequired(UPDATED_DATERANGEREQUIRED)
            .descriptionrequired(UPDATED_DESCRIPTIONREQUIRED)
            .parentid(UPDATED_PARENTID)
            .createdat(UPDATED_CREATEDAT);

        restClaimTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClaimTypes.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClaimTypes))
            )
            .andExpect(status().isOk());

        // Validate the ClaimTypes in the database
        List<ClaimTypes> claimTypesList = claimTypesRepository.findAll();
        assertThat(claimTypesList).hasSize(databaseSizeBeforeUpdate);
        ClaimTypes testClaimTypes = claimTypesList.get(claimTypesList.size() - 1);
        assertThat(testClaimTypes.getClaimtype()).isEqualTo(UPDATED_CLAIMTYPE);
        assertThat(testClaimTypes.getDaterangerequired()).isEqualTo(UPDATED_DATERANGEREQUIRED);
        assertThat(testClaimTypes.getDescriptionrequired()).isEqualTo(UPDATED_DESCRIPTIONREQUIRED);
        assertThat(testClaimTypes.getParentid()).isEqualTo(UPDATED_PARENTID);
        assertThat(testClaimTypes.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testClaimTypes.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateClaimTypesWithPatch() throws Exception {
        // Initialize the database
        claimTypesRepository.saveAndFlush(claimTypes);

        int databaseSizeBeforeUpdate = claimTypesRepository.findAll().size();

        // Update the claimTypes using partial update
        ClaimTypes partialUpdatedClaimTypes = new ClaimTypes();
        partialUpdatedClaimTypes.setId(claimTypes.getId());

        partialUpdatedClaimTypes
            .claimtype(UPDATED_CLAIMTYPE)
            .daterangerequired(UPDATED_DATERANGEREQUIRED)
            .descriptionrequired(UPDATED_DESCRIPTIONREQUIRED)
            .parentid(UPDATED_PARENTID)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restClaimTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClaimTypes.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClaimTypes))
            )
            .andExpect(status().isOk());

        // Validate the ClaimTypes in the database
        List<ClaimTypes> claimTypesList = claimTypesRepository.findAll();
        assertThat(claimTypesList).hasSize(databaseSizeBeforeUpdate);
        ClaimTypes testClaimTypes = claimTypesList.get(claimTypesList.size() - 1);
        assertThat(testClaimTypes.getClaimtype()).isEqualTo(UPDATED_CLAIMTYPE);
        assertThat(testClaimTypes.getDaterangerequired()).isEqualTo(UPDATED_DATERANGEREQUIRED);
        assertThat(testClaimTypes.getDescriptionrequired()).isEqualTo(UPDATED_DESCRIPTIONREQUIRED);
        assertThat(testClaimTypes.getParentid()).isEqualTo(UPDATED_PARENTID);
        assertThat(testClaimTypes.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testClaimTypes.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingClaimTypes() throws Exception {
        int databaseSizeBeforeUpdate = claimTypesRepository.findAll().size();
        claimTypes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClaimTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, claimTypes.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(claimTypes))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimTypes in the database
        List<ClaimTypes> claimTypesList = claimTypesRepository.findAll();
        assertThat(claimTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClaimTypes() throws Exception {
        int databaseSizeBeforeUpdate = claimTypesRepository.findAll().size();
        claimTypes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(claimTypes))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimTypes in the database
        List<ClaimTypes> claimTypesList = claimTypesRepository.findAll();
        assertThat(claimTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClaimTypes() throws Exception {
        int databaseSizeBeforeUpdate = claimTypesRepository.findAll().size();
        claimTypes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimTypesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(claimTypes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClaimTypes in the database
        List<ClaimTypes> claimTypesList = claimTypesRepository.findAll();
        assertThat(claimTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClaimTypes() throws Exception {
        // Initialize the database
        claimTypesRepository.saveAndFlush(claimTypes);

        int databaseSizeBeforeDelete = claimTypesRepository.findAll().size();

        // Delete the claimTypes
        restClaimTypesMockMvc
            .perform(delete(ENTITY_API_URL_ID, claimTypes.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClaimTypes> claimTypesList = claimTypesRepository.findAll();
        assertThat(claimTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
