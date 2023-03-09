package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.ZLeaveRequests;
import com.venturedive.vendian_mono.repository.ZLeaveRequestsRepository;
import com.venturedive.vendian_mono.service.criteria.ZLeaveRequestsCriteria;
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
 * Integration tests for the {@link ZLeaveRequestsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ZLeaveRequestsResourceIT {

    private static final String DEFAULT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_ACTION = "BBBBBBBBBB";

    private static final String DEFAULT_USERID = "AAAAAAAAAA";
    private static final String UPDATED_USERID = "BBBBBBBBBB";

    private static final String DEFAULT_MANAGERID = "AAAAAAAAAA";
    private static final String UPDATED_MANAGERID = "BBBBBBBBBB";

    private static final String DEFAULT_REQUESTPARAMS = "AAAAAAAAAA";
    private static final String UPDATED_REQUESTPARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSEPARAMS = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSEPARAMS = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/z-leave-requests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ZLeaveRequestsRepository zLeaveRequestsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restZLeaveRequestsMockMvc;

    private ZLeaveRequests zLeaveRequests;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ZLeaveRequests createEntity(EntityManager em) {
        ZLeaveRequests zLeaveRequests = new ZLeaveRequests()
            .action(DEFAULT_ACTION)
            .userid(DEFAULT_USERID)
            .managerid(DEFAULT_MANAGERID)
            .requestparams(DEFAULT_REQUESTPARAMS)
            .responseparams(DEFAULT_RESPONSEPARAMS)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        return zLeaveRequests;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ZLeaveRequests createUpdatedEntity(EntityManager em) {
        ZLeaveRequests zLeaveRequests = new ZLeaveRequests()
            .action(UPDATED_ACTION)
            .userid(UPDATED_USERID)
            .managerid(UPDATED_MANAGERID)
            .requestparams(UPDATED_REQUESTPARAMS)
            .responseparams(UPDATED_RESPONSEPARAMS)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        return zLeaveRequests;
    }

    @BeforeEach
    public void initTest() {
        zLeaveRequests = createEntity(em);
    }

    @Test
    @Transactional
    void createZLeaveRequests() throws Exception {
        int databaseSizeBeforeCreate = zLeaveRequestsRepository.findAll().size();
        // Create the ZLeaveRequests
        restZLeaveRequestsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(zLeaveRequests))
            )
            .andExpect(status().isCreated());

        // Validate the ZLeaveRequests in the database
        List<ZLeaveRequests> zLeaveRequestsList = zLeaveRequestsRepository.findAll();
        assertThat(zLeaveRequestsList).hasSize(databaseSizeBeforeCreate + 1);
        ZLeaveRequests testZLeaveRequests = zLeaveRequestsList.get(zLeaveRequestsList.size() - 1);
        assertThat(testZLeaveRequests.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testZLeaveRequests.getUserid()).isEqualTo(DEFAULT_USERID);
        assertThat(testZLeaveRequests.getManagerid()).isEqualTo(DEFAULT_MANAGERID);
        assertThat(testZLeaveRequests.getRequestparams()).isEqualTo(DEFAULT_REQUESTPARAMS);
        assertThat(testZLeaveRequests.getResponseparams()).isEqualTo(DEFAULT_RESPONSEPARAMS);
        assertThat(testZLeaveRequests.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testZLeaveRequests.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createZLeaveRequestsWithExistingId() throws Exception {
        // Create the ZLeaveRequests with an existing ID
        zLeaveRequests.setId(1L);

        int databaseSizeBeforeCreate = zLeaveRequestsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restZLeaveRequestsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(zLeaveRequests))
            )
            .andExpect(status().isBadRequest());

        // Validate the ZLeaveRequests in the database
        List<ZLeaveRequests> zLeaveRequestsList = zLeaveRequestsRepository.findAll();
        assertThat(zLeaveRequestsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = zLeaveRequestsRepository.findAll().size();
        // set the field null
        zLeaveRequests.setCreatedat(null);

        // Create the ZLeaveRequests, which fails.

        restZLeaveRequestsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(zLeaveRequests))
            )
            .andExpect(status().isBadRequest());

        List<ZLeaveRequests> zLeaveRequestsList = zLeaveRequestsRepository.findAll();
        assertThat(zLeaveRequestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = zLeaveRequestsRepository.findAll().size();
        // set the field null
        zLeaveRequests.setUpdatedat(null);

        // Create the ZLeaveRequests, which fails.

        restZLeaveRequestsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(zLeaveRequests))
            )
            .andExpect(status().isBadRequest());

        List<ZLeaveRequests> zLeaveRequestsList = zLeaveRequestsRepository.findAll();
        assertThat(zLeaveRequestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllZLeaveRequests() throws Exception {
        // Initialize the database
        zLeaveRequestsRepository.saveAndFlush(zLeaveRequests);

        // Get all the zLeaveRequestsList
        restZLeaveRequestsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zLeaveRequests.getId().intValue())))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)))
            .andExpect(jsonPath("$.[*].userid").value(hasItem(DEFAULT_USERID)))
            .andExpect(jsonPath("$.[*].managerid").value(hasItem(DEFAULT_MANAGERID)))
            .andExpect(jsonPath("$.[*].requestparams").value(hasItem(DEFAULT_REQUESTPARAMS)))
            .andExpect(jsonPath("$.[*].responseparams").value(hasItem(DEFAULT_RESPONSEPARAMS)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getZLeaveRequests() throws Exception {
        // Initialize the database
        zLeaveRequestsRepository.saveAndFlush(zLeaveRequests);

        // Get the zLeaveRequests
        restZLeaveRequestsMockMvc
            .perform(get(ENTITY_API_URL_ID, zLeaveRequests.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(zLeaveRequests.getId().intValue()))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION))
            .andExpect(jsonPath("$.userid").value(DEFAULT_USERID))
            .andExpect(jsonPath("$.managerid").value(DEFAULT_MANAGERID))
            .andExpect(jsonPath("$.requestparams").value(DEFAULT_REQUESTPARAMS))
            .andExpect(jsonPath("$.responseparams").value(DEFAULT_RESPONSEPARAMS))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getZLeaveRequestsByIdFiltering() throws Exception {
        // Initialize the database
        zLeaveRequestsRepository.saveAndFlush(zLeaveRequests);

        Long id = zLeaveRequests.getId();

        defaultZLeaveRequestsShouldBeFound("id.equals=" + id);
        defaultZLeaveRequestsShouldNotBeFound("id.notEquals=" + id);

        defaultZLeaveRequestsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultZLeaveRequestsShouldNotBeFound("id.greaterThan=" + id);

        defaultZLeaveRequestsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultZLeaveRequestsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllZLeaveRequestsByActionIsEqualToSomething() throws Exception {
        // Initialize the database
        zLeaveRequestsRepository.saveAndFlush(zLeaveRequests);

        // Get all the zLeaveRequestsList where action equals to DEFAULT_ACTION
        defaultZLeaveRequestsShouldBeFound("action.equals=" + DEFAULT_ACTION);

        // Get all the zLeaveRequestsList where action equals to UPDATED_ACTION
        defaultZLeaveRequestsShouldNotBeFound("action.equals=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    void getAllZLeaveRequestsByActionIsInShouldWork() throws Exception {
        // Initialize the database
        zLeaveRequestsRepository.saveAndFlush(zLeaveRequests);

        // Get all the zLeaveRequestsList where action in DEFAULT_ACTION or UPDATED_ACTION
        defaultZLeaveRequestsShouldBeFound("action.in=" + DEFAULT_ACTION + "," + UPDATED_ACTION);

        // Get all the zLeaveRequestsList where action equals to UPDATED_ACTION
        defaultZLeaveRequestsShouldNotBeFound("action.in=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    void getAllZLeaveRequestsByActionIsNullOrNotNull() throws Exception {
        // Initialize the database
        zLeaveRequestsRepository.saveAndFlush(zLeaveRequests);

        // Get all the zLeaveRequestsList where action is not null
        defaultZLeaveRequestsShouldBeFound("action.specified=true");

        // Get all the zLeaveRequestsList where action is null
        defaultZLeaveRequestsShouldNotBeFound("action.specified=false");
    }

    @Test
    @Transactional
    void getAllZLeaveRequestsByActionContainsSomething() throws Exception {
        // Initialize the database
        zLeaveRequestsRepository.saveAndFlush(zLeaveRequests);

        // Get all the zLeaveRequestsList where action contains DEFAULT_ACTION
        defaultZLeaveRequestsShouldBeFound("action.contains=" + DEFAULT_ACTION);

        // Get all the zLeaveRequestsList where action contains UPDATED_ACTION
        defaultZLeaveRequestsShouldNotBeFound("action.contains=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    void getAllZLeaveRequestsByActionNotContainsSomething() throws Exception {
        // Initialize the database
        zLeaveRequestsRepository.saveAndFlush(zLeaveRequests);

        // Get all the zLeaveRequestsList where action does not contain DEFAULT_ACTION
        defaultZLeaveRequestsShouldNotBeFound("action.doesNotContain=" + DEFAULT_ACTION);

        // Get all the zLeaveRequestsList where action does not contain UPDATED_ACTION
        defaultZLeaveRequestsShouldBeFound("action.doesNotContain=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    void getAllZLeaveRequestsByUseridIsEqualToSomething() throws Exception {
        // Initialize the database
        zLeaveRequestsRepository.saveAndFlush(zLeaveRequests);

        // Get all the zLeaveRequestsList where userid equals to DEFAULT_USERID
        defaultZLeaveRequestsShouldBeFound("userid.equals=" + DEFAULT_USERID);

        // Get all the zLeaveRequestsList where userid equals to UPDATED_USERID
        defaultZLeaveRequestsShouldNotBeFound("userid.equals=" + UPDATED_USERID);
    }

    @Test
    @Transactional
    void getAllZLeaveRequestsByUseridIsInShouldWork() throws Exception {
        // Initialize the database
        zLeaveRequestsRepository.saveAndFlush(zLeaveRequests);

        // Get all the zLeaveRequestsList where userid in DEFAULT_USERID or UPDATED_USERID
        defaultZLeaveRequestsShouldBeFound("userid.in=" + DEFAULT_USERID + "," + UPDATED_USERID);

        // Get all the zLeaveRequestsList where userid equals to UPDATED_USERID
        defaultZLeaveRequestsShouldNotBeFound("userid.in=" + UPDATED_USERID);
    }

    @Test
    @Transactional
    void getAllZLeaveRequestsByUseridIsNullOrNotNull() throws Exception {
        // Initialize the database
        zLeaveRequestsRepository.saveAndFlush(zLeaveRequests);

        // Get all the zLeaveRequestsList where userid is not null
        defaultZLeaveRequestsShouldBeFound("userid.specified=true");

        // Get all the zLeaveRequestsList where userid is null
        defaultZLeaveRequestsShouldNotBeFound("userid.specified=false");
    }

    @Test
    @Transactional
    void getAllZLeaveRequestsByUseridContainsSomething() throws Exception {
        // Initialize the database
        zLeaveRequestsRepository.saveAndFlush(zLeaveRequests);

        // Get all the zLeaveRequestsList where userid contains DEFAULT_USERID
        defaultZLeaveRequestsShouldBeFound("userid.contains=" + DEFAULT_USERID);

        // Get all the zLeaveRequestsList where userid contains UPDATED_USERID
        defaultZLeaveRequestsShouldNotBeFound("userid.contains=" + UPDATED_USERID);
    }

    @Test
    @Transactional
    void getAllZLeaveRequestsByUseridNotContainsSomething() throws Exception {
        // Initialize the database
        zLeaveRequestsRepository.saveAndFlush(zLeaveRequests);

        // Get all the zLeaveRequestsList where userid does not contain DEFAULT_USERID
        defaultZLeaveRequestsShouldNotBeFound("userid.doesNotContain=" + DEFAULT_USERID);

        // Get all the zLeaveRequestsList where userid does not contain UPDATED_USERID
        defaultZLeaveRequestsShouldBeFound("userid.doesNotContain=" + UPDATED_USERID);
    }

    @Test
    @Transactional
    void getAllZLeaveRequestsByManageridIsEqualToSomething() throws Exception {
        // Initialize the database
        zLeaveRequestsRepository.saveAndFlush(zLeaveRequests);

        // Get all the zLeaveRequestsList where managerid equals to DEFAULT_MANAGERID
        defaultZLeaveRequestsShouldBeFound("managerid.equals=" + DEFAULT_MANAGERID);

        // Get all the zLeaveRequestsList where managerid equals to UPDATED_MANAGERID
        defaultZLeaveRequestsShouldNotBeFound("managerid.equals=" + UPDATED_MANAGERID);
    }

    @Test
    @Transactional
    void getAllZLeaveRequestsByManageridIsInShouldWork() throws Exception {
        // Initialize the database
        zLeaveRequestsRepository.saveAndFlush(zLeaveRequests);

        // Get all the zLeaveRequestsList where managerid in DEFAULT_MANAGERID or UPDATED_MANAGERID
        defaultZLeaveRequestsShouldBeFound("managerid.in=" + DEFAULT_MANAGERID + "," + UPDATED_MANAGERID);

        // Get all the zLeaveRequestsList where managerid equals to UPDATED_MANAGERID
        defaultZLeaveRequestsShouldNotBeFound("managerid.in=" + UPDATED_MANAGERID);
    }

    @Test
    @Transactional
    void getAllZLeaveRequestsByManageridIsNullOrNotNull() throws Exception {
        // Initialize the database
        zLeaveRequestsRepository.saveAndFlush(zLeaveRequests);

        // Get all the zLeaveRequestsList where managerid is not null
        defaultZLeaveRequestsShouldBeFound("managerid.specified=true");

        // Get all the zLeaveRequestsList where managerid is null
        defaultZLeaveRequestsShouldNotBeFound("managerid.specified=false");
    }

    @Test
    @Transactional
    void getAllZLeaveRequestsByManageridContainsSomething() throws Exception {
        // Initialize the database
        zLeaveRequestsRepository.saveAndFlush(zLeaveRequests);

        // Get all the zLeaveRequestsList where managerid contains DEFAULT_MANAGERID
        defaultZLeaveRequestsShouldBeFound("managerid.contains=" + DEFAULT_MANAGERID);

        // Get all the zLeaveRequestsList where managerid contains UPDATED_MANAGERID
        defaultZLeaveRequestsShouldNotBeFound("managerid.contains=" + UPDATED_MANAGERID);
    }

    @Test
    @Transactional
    void getAllZLeaveRequestsByManageridNotContainsSomething() throws Exception {
        // Initialize the database
        zLeaveRequestsRepository.saveAndFlush(zLeaveRequests);

        // Get all the zLeaveRequestsList where managerid does not contain DEFAULT_MANAGERID
        defaultZLeaveRequestsShouldNotBeFound("managerid.doesNotContain=" + DEFAULT_MANAGERID);

        // Get all the zLeaveRequestsList where managerid does not contain UPDATED_MANAGERID
        defaultZLeaveRequestsShouldBeFound("managerid.doesNotContain=" + UPDATED_MANAGERID);
    }

    @Test
    @Transactional
    void getAllZLeaveRequestsByRequestparamsIsEqualToSomething() throws Exception {
        // Initialize the database
        zLeaveRequestsRepository.saveAndFlush(zLeaveRequests);

        // Get all the zLeaveRequestsList where requestparams equals to DEFAULT_REQUESTPARAMS
        defaultZLeaveRequestsShouldBeFound("requestparams.equals=" + DEFAULT_REQUESTPARAMS);

        // Get all the zLeaveRequestsList where requestparams equals to UPDATED_REQUESTPARAMS
        defaultZLeaveRequestsShouldNotBeFound("requestparams.equals=" + UPDATED_REQUESTPARAMS);
    }

    @Test
    @Transactional
    void getAllZLeaveRequestsByRequestparamsIsInShouldWork() throws Exception {
        // Initialize the database
        zLeaveRequestsRepository.saveAndFlush(zLeaveRequests);

        // Get all the zLeaveRequestsList where requestparams in DEFAULT_REQUESTPARAMS or UPDATED_REQUESTPARAMS
        defaultZLeaveRequestsShouldBeFound("requestparams.in=" + DEFAULT_REQUESTPARAMS + "," + UPDATED_REQUESTPARAMS);

        // Get all the zLeaveRequestsList where requestparams equals to UPDATED_REQUESTPARAMS
        defaultZLeaveRequestsShouldNotBeFound("requestparams.in=" + UPDATED_REQUESTPARAMS);
    }

    @Test
    @Transactional
    void getAllZLeaveRequestsByRequestparamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        zLeaveRequestsRepository.saveAndFlush(zLeaveRequests);

        // Get all the zLeaveRequestsList where requestparams is not null
        defaultZLeaveRequestsShouldBeFound("requestparams.specified=true");

        // Get all the zLeaveRequestsList where requestparams is null
        defaultZLeaveRequestsShouldNotBeFound("requestparams.specified=false");
    }

    @Test
    @Transactional
    void getAllZLeaveRequestsByRequestparamsContainsSomething() throws Exception {
        // Initialize the database
        zLeaveRequestsRepository.saveAndFlush(zLeaveRequests);

        // Get all the zLeaveRequestsList where requestparams contains DEFAULT_REQUESTPARAMS
        defaultZLeaveRequestsShouldBeFound("requestparams.contains=" + DEFAULT_REQUESTPARAMS);

        // Get all the zLeaveRequestsList where requestparams contains UPDATED_REQUESTPARAMS
        defaultZLeaveRequestsShouldNotBeFound("requestparams.contains=" + UPDATED_REQUESTPARAMS);
    }

    @Test
    @Transactional
    void getAllZLeaveRequestsByRequestparamsNotContainsSomething() throws Exception {
        // Initialize the database
        zLeaveRequestsRepository.saveAndFlush(zLeaveRequests);

        // Get all the zLeaveRequestsList where requestparams does not contain DEFAULT_REQUESTPARAMS
        defaultZLeaveRequestsShouldNotBeFound("requestparams.doesNotContain=" + DEFAULT_REQUESTPARAMS);

        // Get all the zLeaveRequestsList where requestparams does not contain UPDATED_REQUESTPARAMS
        defaultZLeaveRequestsShouldBeFound("requestparams.doesNotContain=" + UPDATED_REQUESTPARAMS);
    }

    @Test
    @Transactional
    void getAllZLeaveRequestsByResponseparamsIsEqualToSomething() throws Exception {
        // Initialize the database
        zLeaveRequestsRepository.saveAndFlush(zLeaveRequests);

        // Get all the zLeaveRequestsList where responseparams equals to DEFAULT_RESPONSEPARAMS
        defaultZLeaveRequestsShouldBeFound("responseparams.equals=" + DEFAULT_RESPONSEPARAMS);

        // Get all the zLeaveRequestsList where responseparams equals to UPDATED_RESPONSEPARAMS
        defaultZLeaveRequestsShouldNotBeFound("responseparams.equals=" + UPDATED_RESPONSEPARAMS);
    }

    @Test
    @Transactional
    void getAllZLeaveRequestsByResponseparamsIsInShouldWork() throws Exception {
        // Initialize the database
        zLeaveRequestsRepository.saveAndFlush(zLeaveRequests);

        // Get all the zLeaveRequestsList where responseparams in DEFAULT_RESPONSEPARAMS or UPDATED_RESPONSEPARAMS
        defaultZLeaveRequestsShouldBeFound("responseparams.in=" + DEFAULT_RESPONSEPARAMS + "," + UPDATED_RESPONSEPARAMS);

        // Get all the zLeaveRequestsList where responseparams equals to UPDATED_RESPONSEPARAMS
        defaultZLeaveRequestsShouldNotBeFound("responseparams.in=" + UPDATED_RESPONSEPARAMS);
    }

    @Test
    @Transactional
    void getAllZLeaveRequestsByResponseparamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        zLeaveRequestsRepository.saveAndFlush(zLeaveRequests);

        // Get all the zLeaveRequestsList where responseparams is not null
        defaultZLeaveRequestsShouldBeFound("responseparams.specified=true");

        // Get all the zLeaveRequestsList where responseparams is null
        defaultZLeaveRequestsShouldNotBeFound("responseparams.specified=false");
    }

    @Test
    @Transactional
    void getAllZLeaveRequestsByResponseparamsContainsSomething() throws Exception {
        // Initialize the database
        zLeaveRequestsRepository.saveAndFlush(zLeaveRequests);

        // Get all the zLeaveRequestsList where responseparams contains DEFAULT_RESPONSEPARAMS
        defaultZLeaveRequestsShouldBeFound("responseparams.contains=" + DEFAULT_RESPONSEPARAMS);

        // Get all the zLeaveRequestsList where responseparams contains UPDATED_RESPONSEPARAMS
        defaultZLeaveRequestsShouldNotBeFound("responseparams.contains=" + UPDATED_RESPONSEPARAMS);
    }

    @Test
    @Transactional
    void getAllZLeaveRequestsByResponseparamsNotContainsSomething() throws Exception {
        // Initialize the database
        zLeaveRequestsRepository.saveAndFlush(zLeaveRequests);

        // Get all the zLeaveRequestsList where responseparams does not contain DEFAULT_RESPONSEPARAMS
        defaultZLeaveRequestsShouldNotBeFound("responseparams.doesNotContain=" + DEFAULT_RESPONSEPARAMS);

        // Get all the zLeaveRequestsList where responseparams does not contain UPDATED_RESPONSEPARAMS
        defaultZLeaveRequestsShouldBeFound("responseparams.doesNotContain=" + UPDATED_RESPONSEPARAMS);
    }

    @Test
    @Transactional
    void getAllZLeaveRequestsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        zLeaveRequestsRepository.saveAndFlush(zLeaveRequests);

        // Get all the zLeaveRequestsList where createdat equals to DEFAULT_CREATEDAT
        defaultZLeaveRequestsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the zLeaveRequestsList where createdat equals to UPDATED_CREATEDAT
        defaultZLeaveRequestsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllZLeaveRequestsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        zLeaveRequestsRepository.saveAndFlush(zLeaveRequests);

        // Get all the zLeaveRequestsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultZLeaveRequestsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the zLeaveRequestsList where createdat equals to UPDATED_CREATEDAT
        defaultZLeaveRequestsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllZLeaveRequestsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        zLeaveRequestsRepository.saveAndFlush(zLeaveRequests);

        // Get all the zLeaveRequestsList where createdat is not null
        defaultZLeaveRequestsShouldBeFound("createdat.specified=true");

        // Get all the zLeaveRequestsList where createdat is null
        defaultZLeaveRequestsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllZLeaveRequestsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        zLeaveRequestsRepository.saveAndFlush(zLeaveRequests);

        // Get all the zLeaveRequestsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultZLeaveRequestsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the zLeaveRequestsList where updatedat equals to UPDATED_UPDATEDAT
        defaultZLeaveRequestsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllZLeaveRequestsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        zLeaveRequestsRepository.saveAndFlush(zLeaveRequests);

        // Get all the zLeaveRequestsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultZLeaveRequestsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the zLeaveRequestsList where updatedat equals to UPDATED_UPDATEDAT
        defaultZLeaveRequestsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllZLeaveRequestsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        zLeaveRequestsRepository.saveAndFlush(zLeaveRequests);

        // Get all the zLeaveRequestsList where updatedat is not null
        defaultZLeaveRequestsShouldBeFound("updatedat.specified=true");

        // Get all the zLeaveRequestsList where updatedat is null
        defaultZLeaveRequestsShouldNotBeFound("updatedat.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultZLeaveRequestsShouldBeFound(String filter) throws Exception {
        restZLeaveRequestsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zLeaveRequests.getId().intValue())))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)))
            .andExpect(jsonPath("$.[*].userid").value(hasItem(DEFAULT_USERID)))
            .andExpect(jsonPath("$.[*].managerid").value(hasItem(DEFAULT_MANAGERID)))
            .andExpect(jsonPath("$.[*].requestparams").value(hasItem(DEFAULT_REQUESTPARAMS)))
            .andExpect(jsonPath("$.[*].responseparams").value(hasItem(DEFAULT_RESPONSEPARAMS)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restZLeaveRequestsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultZLeaveRequestsShouldNotBeFound(String filter) throws Exception {
        restZLeaveRequestsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restZLeaveRequestsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingZLeaveRequests() throws Exception {
        // Get the zLeaveRequests
        restZLeaveRequestsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingZLeaveRequests() throws Exception {
        // Initialize the database
        zLeaveRequestsRepository.saveAndFlush(zLeaveRequests);

        int databaseSizeBeforeUpdate = zLeaveRequestsRepository.findAll().size();

        // Update the zLeaveRequests
        ZLeaveRequests updatedZLeaveRequests = zLeaveRequestsRepository.findById(zLeaveRequests.getId()).get();
        // Disconnect from session so that the updates on updatedZLeaveRequests are not directly saved in db
        em.detach(updatedZLeaveRequests);
        updatedZLeaveRequests
            .action(UPDATED_ACTION)
            .userid(UPDATED_USERID)
            .managerid(UPDATED_MANAGERID)
            .requestparams(UPDATED_REQUESTPARAMS)
            .responseparams(UPDATED_RESPONSEPARAMS)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restZLeaveRequestsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedZLeaveRequests.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedZLeaveRequests))
            )
            .andExpect(status().isOk());

        // Validate the ZLeaveRequests in the database
        List<ZLeaveRequests> zLeaveRequestsList = zLeaveRequestsRepository.findAll();
        assertThat(zLeaveRequestsList).hasSize(databaseSizeBeforeUpdate);
        ZLeaveRequests testZLeaveRequests = zLeaveRequestsList.get(zLeaveRequestsList.size() - 1);
        assertThat(testZLeaveRequests.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testZLeaveRequests.getUserid()).isEqualTo(UPDATED_USERID);
        assertThat(testZLeaveRequests.getManagerid()).isEqualTo(UPDATED_MANAGERID);
        assertThat(testZLeaveRequests.getRequestparams()).isEqualTo(UPDATED_REQUESTPARAMS);
        assertThat(testZLeaveRequests.getResponseparams()).isEqualTo(UPDATED_RESPONSEPARAMS);
        assertThat(testZLeaveRequests.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testZLeaveRequests.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingZLeaveRequests() throws Exception {
        int databaseSizeBeforeUpdate = zLeaveRequestsRepository.findAll().size();
        zLeaveRequests.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZLeaveRequestsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, zLeaveRequests.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(zLeaveRequests))
            )
            .andExpect(status().isBadRequest());

        // Validate the ZLeaveRequests in the database
        List<ZLeaveRequests> zLeaveRequestsList = zLeaveRequestsRepository.findAll();
        assertThat(zLeaveRequestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchZLeaveRequests() throws Exception {
        int databaseSizeBeforeUpdate = zLeaveRequestsRepository.findAll().size();
        zLeaveRequests.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZLeaveRequestsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(zLeaveRequests))
            )
            .andExpect(status().isBadRequest());

        // Validate the ZLeaveRequests in the database
        List<ZLeaveRequests> zLeaveRequestsList = zLeaveRequestsRepository.findAll();
        assertThat(zLeaveRequestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamZLeaveRequests() throws Exception {
        int databaseSizeBeforeUpdate = zLeaveRequestsRepository.findAll().size();
        zLeaveRequests.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZLeaveRequestsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(zLeaveRequests))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ZLeaveRequests in the database
        List<ZLeaveRequests> zLeaveRequestsList = zLeaveRequestsRepository.findAll();
        assertThat(zLeaveRequestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateZLeaveRequestsWithPatch() throws Exception {
        // Initialize the database
        zLeaveRequestsRepository.saveAndFlush(zLeaveRequests);

        int databaseSizeBeforeUpdate = zLeaveRequestsRepository.findAll().size();

        // Update the zLeaveRequests using partial update
        ZLeaveRequests partialUpdatedZLeaveRequests = new ZLeaveRequests();
        partialUpdatedZLeaveRequests.setId(zLeaveRequests.getId());

        partialUpdatedZLeaveRequests
            .action(UPDATED_ACTION)
            .userid(UPDATED_USERID)
            .managerid(UPDATED_MANAGERID)
            .requestparams(UPDATED_REQUESTPARAMS)
            .responseparams(UPDATED_RESPONSEPARAMS)
            .updatedat(UPDATED_UPDATEDAT);

        restZLeaveRequestsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedZLeaveRequests.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedZLeaveRequests))
            )
            .andExpect(status().isOk());

        // Validate the ZLeaveRequests in the database
        List<ZLeaveRequests> zLeaveRequestsList = zLeaveRequestsRepository.findAll();
        assertThat(zLeaveRequestsList).hasSize(databaseSizeBeforeUpdate);
        ZLeaveRequests testZLeaveRequests = zLeaveRequestsList.get(zLeaveRequestsList.size() - 1);
        assertThat(testZLeaveRequests.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testZLeaveRequests.getUserid()).isEqualTo(UPDATED_USERID);
        assertThat(testZLeaveRequests.getManagerid()).isEqualTo(UPDATED_MANAGERID);
        assertThat(testZLeaveRequests.getRequestparams()).isEqualTo(UPDATED_REQUESTPARAMS);
        assertThat(testZLeaveRequests.getResponseparams()).isEqualTo(UPDATED_RESPONSEPARAMS);
        assertThat(testZLeaveRequests.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testZLeaveRequests.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateZLeaveRequestsWithPatch() throws Exception {
        // Initialize the database
        zLeaveRequestsRepository.saveAndFlush(zLeaveRequests);

        int databaseSizeBeforeUpdate = zLeaveRequestsRepository.findAll().size();

        // Update the zLeaveRequests using partial update
        ZLeaveRequests partialUpdatedZLeaveRequests = new ZLeaveRequests();
        partialUpdatedZLeaveRequests.setId(zLeaveRequests.getId());

        partialUpdatedZLeaveRequests
            .action(UPDATED_ACTION)
            .userid(UPDATED_USERID)
            .managerid(UPDATED_MANAGERID)
            .requestparams(UPDATED_REQUESTPARAMS)
            .responseparams(UPDATED_RESPONSEPARAMS)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restZLeaveRequestsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedZLeaveRequests.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedZLeaveRequests))
            )
            .andExpect(status().isOk());

        // Validate the ZLeaveRequests in the database
        List<ZLeaveRequests> zLeaveRequestsList = zLeaveRequestsRepository.findAll();
        assertThat(zLeaveRequestsList).hasSize(databaseSizeBeforeUpdate);
        ZLeaveRequests testZLeaveRequests = zLeaveRequestsList.get(zLeaveRequestsList.size() - 1);
        assertThat(testZLeaveRequests.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testZLeaveRequests.getUserid()).isEqualTo(UPDATED_USERID);
        assertThat(testZLeaveRequests.getManagerid()).isEqualTo(UPDATED_MANAGERID);
        assertThat(testZLeaveRequests.getRequestparams()).isEqualTo(UPDATED_REQUESTPARAMS);
        assertThat(testZLeaveRequests.getResponseparams()).isEqualTo(UPDATED_RESPONSEPARAMS);
        assertThat(testZLeaveRequests.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testZLeaveRequests.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingZLeaveRequests() throws Exception {
        int databaseSizeBeforeUpdate = zLeaveRequestsRepository.findAll().size();
        zLeaveRequests.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZLeaveRequestsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, zLeaveRequests.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(zLeaveRequests))
            )
            .andExpect(status().isBadRequest());

        // Validate the ZLeaveRequests in the database
        List<ZLeaveRequests> zLeaveRequestsList = zLeaveRequestsRepository.findAll();
        assertThat(zLeaveRequestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchZLeaveRequests() throws Exception {
        int databaseSizeBeforeUpdate = zLeaveRequestsRepository.findAll().size();
        zLeaveRequests.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZLeaveRequestsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(zLeaveRequests))
            )
            .andExpect(status().isBadRequest());

        // Validate the ZLeaveRequests in the database
        List<ZLeaveRequests> zLeaveRequestsList = zLeaveRequestsRepository.findAll();
        assertThat(zLeaveRequestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamZLeaveRequests() throws Exception {
        int databaseSizeBeforeUpdate = zLeaveRequestsRepository.findAll().size();
        zLeaveRequests.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZLeaveRequestsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(zLeaveRequests))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ZLeaveRequests in the database
        List<ZLeaveRequests> zLeaveRequestsList = zLeaveRequestsRepository.findAll();
        assertThat(zLeaveRequestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteZLeaveRequests() throws Exception {
        // Initialize the database
        zLeaveRequestsRepository.saveAndFlush(zLeaveRequests);

        int databaseSizeBeforeDelete = zLeaveRequestsRepository.findAll().size();

        // Delete the zLeaveRequests
        restZLeaveRequestsMockMvc
            .perform(delete(ENTITY_API_URL_ID, zLeaveRequests.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ZLeaveRequests> zLeaveRequestsList = zLeaveRequestsRepository.findAll();
        assertThat(zLeaveRequestsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
