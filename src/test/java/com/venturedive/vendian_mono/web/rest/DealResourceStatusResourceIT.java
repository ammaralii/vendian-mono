package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.DealRequirementMatchingResources;
import com.venturedive.vendian_mono.domain.DealResourceEventLogs;
import com.venturedive.vendian_mono.domain.DealResourceStatus;
import com.venturedive.vendian_mono.repository.DealResourceStatusRepository;
import com.venturedive.vendian_mono.service.criteria.DealResourceStatusCriteria;
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
 * Integration tests for the {@link DealResourceStatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DealResourceStatusResourceIT {

    private static final String DEFAULT_DISPLAYNAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAYNAME = "BBBBBBBBBB";

    private static final String DEFAULT_GROUP = "AAAAAAAAAA";
    private static final String UPDATED_GROUP = "BBBBBBBBBB";

    private static final String DEFAULT_SYSTEM_KEY = "AAAAAAAAAA";
    private static final String UPDATED_SYSTEM_KEY = "BBBBBBBBBB";

    private static final Instant DEFAULT_EFFECTIVEDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EFFECTIVEDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ENDDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ENDDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/deal-resource-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DealResourceStatusRepository dealResourceStatusRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDealResourceStatusMockMvc;

    private DealResourceStatus dealResourceStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DealResourceStatus createEntity(EntityManager em) {
        DealResourceStatus dealResourceStatus = new DealResourceStatus()
            .displayname(DEFAULT_DISPLAYNAME)
            .group(DEFAULT_GROUP)
            .systemKey(DEFAULT_SYSTEM_KEY)
            .effectivedate(DEFAULT_EFFECTIVEDATE)
            .enddate(DEFAULT_ENDDATE)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        return dealResourceStatus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DealResourceStatus createUpdatedEntity(EntityManager em) {
        DealResourceStatus dealResourceStatus = new DealResourceStatus()
            .displayname(UPDATED_DISPLAYNAME)
            .group(UPDATED_GROUP)
            .systemKey(UPDATED_SYSTEM_KEY)
            .effectivedate(UPDATED_EFFECTIVEDATE)
            .enddate(UPDATED_ENDDATE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        return dealResourceStatus;
    }

    @BeforeEach
    public void initTest() {
        dealResourceStatus = createEntity(em);
    }

    @Test
    @Transactional
    void createDealResourceStatus() throws Exception {
        int databaseSizeBeforeCreate = dealResourceStatusRepository.findAll().size();
        // Create the DealResourceStatus
        restDealResourceStatusMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealResourceStatus))
            )
            .andExpect(status().isCreated());

        // Validate the DealResourceStatus in the database
        List<DealResourceStatus> dealResourceStatusList = dealResourceStatusRepository.findAll();
        assertThat(dealResourceStatusList).hasSize(databaseSizeBeforeCreate + 1);
        DealResourceStatus testDealResourceStatus = dealResourceStatusList.get(dealResourceStatusList.size() - 1);
        assertThat(testDealResourceStatus.getDisplayname()).isEqualTo(DEFAULT_DISPLAYNAME);
        assertThat(testDealResourceStatus.getGroup()).isEqualTo(DEFAULT_GROUP);
        assertThat(testDealResourceStatus.getSystemKey()).isEqualTo(DEFAULT_SYSTEM_KEY);
        assertThat(testDealResourceStatus.getEffectivedate()).isEqualTo(DEFAULT_EFFECTIVEDATE);
        assertThat(testDealResourceStatus.getEnddate()).isEqualTo(DEFAULT_ENDDATE);
        assertThat(testDealResourceStatus.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testDealResourceStatus.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createDealResourceStatusWithExistingId() throws Exception {
        // Create the DealResourceStatus with an existing ID
        dealResourceStatus.setId(1L);

        int databaseSizeBeforeCreate = dealResourceStatusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDealResourceStatusMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealResourceStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealResourceStatus in the database
        List<DealResourceStatus> dealResourceStatusList = dealResourceStatusRepository.findAll();
        assertThat(dealResourceStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDisplaynameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealResourceStatusRepository.findAll().size();
        // set the field null
        dealResourceStatus.setDisplayname(null);

        // Create the DealResourceStatus, which fails.

        restDealResourceStatusMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealResourceStatus))
            )
            .andExpect(status().isBadRequest());

        List<DealResourceStatus> dealResourceStatusList = dealResourceStatusRepository.findAll();
        assertThat(dealResourceStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGroupIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealResourceStatusRepository.findAll().size();
        // set the field null
        dealResourceStatus.setGroup(null);

        // Create the DealResourceStatus, which fails.

        restDealResourceStatusMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealResourceStatus))
            )
            .andExpect(status().isBadRequest());

        List<DealResourceStatus> dealResourceStatusList = dealResourceStatusRepository.findAll();
        assertThat(dealResourceStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSystemKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealResourceStatusRepository.findAll().size();
        // set the field null
        dealResourceStatus.setSystemKey(null);

        // Create the DealResourceStatus, which fails.

        restDealResourceStatusMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealResourceStatus))
            )
            .andExpect(status().isBadRequest());

        List<DealResourceStatus> dealResourceStatusList = dealResourceStatusRepository.findAll();
        assertThat(dealResourceStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDealResourceStatuses() throws Exception {
        // Initialize the database
        dealResourceStatusRepository.saveAndFlush(dealResourceStatus);

        // Get all the dealResourceStatusList
        restDealResourceStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dealResourceStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].displayname").value(hasItem(DEFAULT_DISPLAYNAME)))
            .andExpect(jsonPath("$.[*].group").value(hasItem(DEFAULT_GROUP)))
            .andExpect(jsonPath("$.[*].systemKey").value(hasItem(DEFAULT_SYSTEM_KEY)))
            .andExpect(jsonPath("$.[*].effectivedate").value(hasItem(DEFAULT_EFFECTIVEDATE.toString())))
            .andExpect(jsonPath("$.[*].enddate").value(hasItem(DEFAULT_ENDDATE.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getDealResourceStatus() throws Exception {
        // Initialize the database
        dealResourceStatusRepository.saveAndFlush(dealResourceStatus);

        // Get the dealResourceStatus
        restDealResourceStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, dealResourceStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dealResourceStatus.getId().intValue()))
            .andExpect(jsonPath("$.displayname").value(DEFAULT_DISPLAYNAME))
            .andExpect(jsonPath("$.group").value(DEFAULT_GROUP))
            .andExpect(jsonPath("$.systemKey").value(DEFAULT_SYSTEM_KEY))
            .andExpect(jsonPath("$.effectivedate").value(DEFAULT_EFFECTIVEDATE.toString()))
            .andExpect(jsonPath("$.enddate").value(DEFAULT_ENDDATE.toString()))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getDealResourceStatusesByIdFiltering() throws Exception {
        // Initialize the database
        dealResourceStatusRepository.saveAndFlush(dealResourceStatus);

        Long id = dealResourceStatus.getId();

        defaultDealResourceStatusShouldBeFound("id.equals=" + id);
        defaultDealResourceStatusShouldNotBeFound("id.notEquals=" + id);

        defaultDealResourceStatusShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDealResourceStatusShouldNotBeFound("id.greaterThan=" + id);

        defaultDealResourceStatusShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDealResourceStatusShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDealResourceStatusesByDisplaynameIsEqualToSomething() throws Exception {
        // Initialize the database
        dealResourceStatusRepository.saveAndFlush(dealResourceStatus);

        // Get all the dealResourceStatusList where displayname equals to DEFAULT_DISPLAYNAME
        defaultDealResourceStatusShouldBeFound("displayname.equals=" + DEFAULT_DISPLAYNAME);

        // Get all the dealResourceStatusList where displayname equals to UPDATED_DISPLAYNAME
        defaultDealResourceStatusShouldNotBeFound("displayname.equals=" + UPDATED_DISPLAYNAME);
    }

    @Test
    @Transactional
    void getAllDealResourceStatusesByDisplaynameIsInShouldWork() throws Exception {
        // Initialize the database
        dealResourceStatusRepository.saveAndFlush(dealResourceStatus);

        // Get all the dealResourceStatusList where displayname in DEFAULT_DISPLAYNAME or UPDATED_DISPLAYNAME
        defaultDealResourceStatusShouldBeFound("displayname.in=" + DEFAULT_DISPLAYNAME + "," + UPDATED_DISPLAYNAME);

        // Get all the dealResourceStatusList where displayname equals to UPDATED_DISPLAYNAME
        defaultDealResourceStatusShouldNotBeFound("displayname.in=" + UPDATED_DISPLAYNAME);
    }

    @Test
    @Transactional
    void getAllDealResourceStatusesByDisplaynameIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealResourceStatusRepository.saveAndFlush(dealResourceStatus);

        // Get all the dealResourceStatusList where displayname is not null
        defaultDealResourceStatusShouldBeFound("displayname.specified=true");

        // Get all the dealResourceStatusList where displayname is null
        defaultDealResourceStatusShouldNotBeFound("displayname.specified=false");
    }

    @Test
    @Transactional
    void getAllDealResourceStatusesByDisplaynameContainsSomething() throws Exception {
        // Initialize the database
        dealResourceStatusRepository.saveAndFlush(dealResourceStatus);

        // Get all the dealResourceStatusList where displayname contains DEFAULT_DISPLAYNAME
        defaultDealResourceStatusShouldBeFound("displayname.contains=" + DEFAULT_DISPLAYNAME);

        // Get all the dealResourceStatusList where displayname contains UPDATED_DISPLAYNAME
        defaultDealResourceStatusShouldNotBeFound("displayname.contains=" + UPDATED_DISPLAYNAME);
    }

    @Test
    @Transactional
    void getAllDealResourceStatusesByDisplaynameNotContainsSomething() throws Exception {
        // Initialize the database
        dealResourceStatusRepository.saveAndFlush(dealResourceStatus);

        // Get all the dealResourceStatusList where displayname does not contain DEFAULT_DISPLAYNAME
        defaultDealResourceStatusShouldNotBeFound("displayname.doesNotContain=" + DEFAULT_DISPLAYNAME);

        // Get all the dealResourceStatusList where displayname does not contain UPDATED_DISPLAYNAME
        defaultDealResourceStatusShouldBeFound("displayname.doesNotContain=" + UPDATED_DISPLAYNAME);
    }

    @Test
    @Transactional
    void getAllDealResourceStatusesByGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        dealResourceStatusRepository.saveAndFlush(dealResourceStatus);

        // Get all the dealResourceStatusList where group equals to DEFAULT_GROUP
        defaultDealResourceStatusShouldBeFound("group.equals=" + DEFAULT_GROUP);

        // Get all the dealResourceStatusList where group equals to UPDATED_GROUP
        defaultDealResourceStatusShouldNotBeFound("group.equals=" + UPDATED_GROUP);
    }

    @Test
    @Transactional
    void getAllDealResourceStatusesByGroupIsInShouldWork() throws Exception {
        // Initialize the database
        dealResourceStatusRepository.saveAndFlush(dealResourceStatus);

        // Get all the dealResourceStatusList where group in DEFAULT_GROUP or UPDATED_GROUP
        defaultDealResourceStatusShouldBeFound("group.in=" + DEFAULT_GROUP + "," + UPDATED_GROUP);

        // Get all the dealResourceStatusList where group equals to UPDATED_GROUP
        defaultDealResourceStatusShouldNotBeFound("group.in=" + UPDATED_GROUP);
    }

    @Test
    @Transactional
    void getAllDealResourceStatusesByGroupIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealResourceStatusRepository.saveAndFlush(dealResourceStatus);

        // Get all the dealResourceStatusList where group is not null
        defaultDealResourceStatusShouldBeFound("group.specified=true");

        // Get all the dealResourceStatusList where group is null
        defaultDealResourceStatusShouldNotBeFound("group.specified=false");
    }

    @Test
    @Transactional
    void getAllDealResourceStatusesByGroupContainsSomething() throws Exception {
        // Initialize the database
        dealResourceStatusRepository.saveAndFlush(dealResourceStatus);

        // Get all the dealResourceStatusList where group contains DEFAULT_GROUP
        defaultDealResourceStatusShouldBeFound("group.contains=" + DEFAULT_GROUP);

        // Get all the dealResourceStatusList where group contains UPDATED_GROUP
        defaultDealResourceStatusShouldNotBeFound("group.contains=" + UPDATED_GROUP);
    }

    @Test
    @Transactional
    void getAllDealResourceStatusesByGroupNotContainsSomething() throws Exception {
        // Initialize the database
        dealResourceStatusRepository.saveAndFlush(dealResourceStatus);

        // Get all the dealResourceStatusList where group does not contain DEFAULT_GROUP
        defaultDealResourceStatusShouldNotBeFound("group.doesNotContain=" + DEFAULT_GROUP);

        // Get all the dealResourceStatusList where group does not contain UPDATED_GROUP
        defaultDealResourceStatusShouldBeFound("group.doesNotContain=" + UPDATED_GROUP);
    }

    @Test
    @Transactional
    void getAllDealResourceStatusesBySystemKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        dealResourceStatusRepository.saveAndFlush(dealResourceStatus);

        // Get all the dealResourceStatusList where systemKey equals to DEFAULT_SYSTEM_KEY
        defaultDealResourceStatusShouldBeFound("systemKey.equals=" + DEFAULT_SYSTEM_KEY);

        // Get all the dealResourceStatusList where systemKey equals to UPDATED_SYSTEM_KEY
        defaultDealResourceStatusShouldNotBeFound("systemKey.equals=" + UPDATED_SYSTEM_KEY);
    }

    @Test
    @Transactional
    void getAllDealResourceStatusesBySystemKeyIsInShouldWork() throws Exception {
        // Initialize the database
        dealResourceStatusRepository.saveAndFlush(dealResourceStatus);

        // Get all the dealResourceStatusList where systemKey in DEFAULT_SYSTEM_KEY or UPDATED_SYSTEM_KEY
        defaultDealResourceStatusShouldBeFound("systemKey.in=" + DEFAULT_SYSTEM_KEY + "," + UPDATED_SYSTEM_KEY);

        // Get all the dealResourceStatusList where systemKey equals to UPDATED_SYSTEM_KEY
        defaultDealResourceStatusShouldNotBeFound("systemKey.in=" + UPDATED_SYSTEM_KEY);
    }

    @Test
    @Transactional
    void getAllDealResourceStatusesBySystemKeyIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealResourceStatusRepository.saveAndFlush(dealResourceStatus);

        // Get all the dealResourceStatusList where systemKey is not null
        defaultDealResourceStatusShouldBeFound("systemKey.specified=true");

        // Get all the dealResourceStatusList where systemKey is null
        defaultDealResourceStatusShouldNotBeFound("systemKey.specified=false");
    }

    @Test
    @Transactional
    void getAllDealResourceStatusesBySystemKeyContainsSomething() throws Exception {
        // Initialize the database
        dealResourceStatusRepository.saveAndFlush(dealResourceStatus);

        // Get all the dealResourceStatusList where systemKey contains DEFAULT_SYSTEM_KEY
        defaultDealResourceStatusShouldBeFound("systemKey.contains=" + DEFAULT_SYSTEM_KEY);

        // Get all the dealResourceStatusList where systemKey contains UPDATED_SYSTEM_KEY
        defaultDealResourceStatusShouldNotBeFound("systemKey.contains=" + UPDATED_SYSTEM_KEY);
    }

    @Test
    @Transactional
    void getAllDealResourceStatusesBySystemKeyNotContainsSomething() throws Exception {
        // Initialize the database
        dealResourceStatusRepository.saveAndFlush(dealResourceStatus);

        // Get all the dealResourceStatusList where systemKey does not contain DEFAULT_SYSTEM_KEY
        defaultDealResourceStatusShouldNotBeFound("systemKey.doesNotContain=" + DEFAULT_SYSTEM_KEY);

        // Get all the dealResourceStatusList where systemKey does not contain UPDATED_SYSTEM_KEY
        defaultDealResourceStatusShouldBeFound("systemKey.doesNotContain=" + UPDATED_SYSTEM_KEY);
    }

    @Test
    @Transactional
    void getAllDealResourceStatusesByEffectivedateIsEqualToSomething() throws Exception {
        // Initialize the database
        dealResourceStatusRepository.saveAndFlush(dealResourceStatus);

        // Get all the dealResourceStatusList where effectivedate equals to DEFAULT_EFFECTIVEDATE
        defaultDealResourceStatusShouldBeFound("effectivedate.equals=" + DEFAULT_EFFECTIVEDATE);

        // Get all the dealResourceStatusList where effectivedate equals to UPDATED_EFFECTIVEDATE
        defaultDealResourceStatusShouldNotBeFound("effectivedate.equals=" + UPDATED_EFFECTIVEDATE);
    }

    @Test
    @Transactional
    void getAllDealResourceStatusesByEffectivedateIsInShouldWork() throws Exception {
        // Initialize the database
        dealResourceStatusRepository.saveAndFlush(dealResourceStatus);

        // Get all the dealResourceStatusList where effectivedate in DEFAULT_EFFECTIVEDATE or UPDATED_EFFECTIVEDATE
        defaultDealResourceStatusShouldBeFound("effectivedate.in=" + DEFAULT_EFFECTIVEDATE + "," + UPDATED_EFFECTIVEDATE);

        // Get all the dealResourceStatusList where effectivedate equals to UPDATED_EFFECTIVEDATE
        defaultDealResourceStatusShouldNotBeFound("effectivedate.in=" + UPDATED_EFFECTIVEDATE);
    }

    @Test
    @Transactional
    void getAllDealResourceStatusesByEffectivedateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealResourceStatusRepository.saveAndFlush(dealResourceStatus);

        // Get all the dealResourceStatusList where effectivedate is not null
        defaultDealResourceStatusShouldBeFound("effectivedate.specified=true");

        // Get all the dealResourceStatusList where effectivedate is null
        defaultDealResourceStatusShouldNotBeFound("effectivedate.specified=false");
    }

    @Test
    @Transactional
    void getAllDealResourceStatusesByEnddateIsEqualToSomething() throws Exception {
        // Initialize the database
        dealResourceStatusRepository.saveAndFlush(dealResourceStatus);

        // Get all the dealResourceStatusList where enddate equals to DEFAULT_ENDDATE
        defaultDealResourceStatusShouldBeFound("enddate.equals=" + DEFAULT_ENDDATE);

        // Get all the dealResourceStatusList where enddate equals to UPDATED_ENDDATE
        defaultDealResourceStatusShouldNotBeFound("enddate.equals=" + UPDATED_ENDDATE);
    }

    @Test
    @Transactional
    void getAllDealResourceStatusesByEnddateIsInShouldWork() throws Exception {
        // Initialize the database
        dealResourceStatusRepository.saveAndFlush(dealResourceStatus);

        // Get all the dealResourceStatusList where enddate in DEFAULT_ENDDATE or UPDATED_ENDDATE
        defaultDealResourceStatusShouldBeFound("enddate.in=" + DEFAULT_ENDDATE + "," + UPDATED_ENDDATE);

        // Get all the dealResourceStatusList where enddate equals to UPDATED_ENDDATE
        defaultDealResourceStatusShouldNotBeFound("enddate.in=" + UPDATED_ENDDATE);
    }

    @Test
    @Transactional
    void getAllDealResourceStatusesByEnddateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealResourceStatusRepository.saveAndFlush(dealResourceStatus);

        // Get all the dealResourceStatusList where enddate is not null
        defaultDealResourceStatusShouldBeFound("enddate.specified=true");

        // Get all the dealResourceStatusList where enddate is null
        defaultDealResourceStatusShouldNotBeFound("enddate.specified=false");
    }

    @Test
    @Transactional
    void getAllDealResourceStatusesByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        dealResourceStatusRepository.saveAndFlush(dealResourceStatus);

        // Get all the dealResourceStatusList where createdat equals to DEFAULT_CREATEDAT
        defaultDealResourceStatusShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the dealResourceStatusList where createdat equals to UPDATED_CREATEDAT
        defaultDealResourceStatusShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllDealResourceStatusesByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        dealResourceStatusRepository.saveAndFlush(dealResourceStatus);

        // Get all the dealResourceStatusList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultDealResourceStatusShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the dealResourceStatusList where createdat equals to UPDATED_CREATEDAT
        defaultDealResourceStatusShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllDealResourceStatusesByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealResourceStatusRepository.saveAndFlush(dealResourceStatus);

        // Get all the dealResourceStatusList where createdat is not null
        defaultDealResourceStatusShouldBeFound("createdat.specified=true");

        // Get all the dealResourceStatusList where createdat is null
        defaultDealResourceStatusShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllDealResourceStatusesByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        dealResourceStatusRepository.saveAndFlush(dealResourceStatus);

        // Get all the dealResourceStatusList where updatedat equals to DEFAULT_UPDATEDAT
        defaultDealResourceStatusShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the dealResourceStatusList where updatedat equals to UPDATED_UPDATEDAT
        defaultDealResourceStatusShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllDealResourceStatusesByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        dealResourceStatusRepository.saveAndFlush(dealResourceStatus);

        // Get all the dealResourceStatusList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultDealResourceStatusShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the dealResourceStatusList where updatedat equals to UPDATED_UPDATEDAT
        defaultDealResourceStatusShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllDealResourceStatusesByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealResourceStatusRepository.saveAndFlush(dealResourceStatus);

        // Get all the dealResourceStatusList where updatedat is not null
        defaultDealResourceStatusShouldBeFound("updatedat.specified=true");

        // Get all the dealResourceStatusList where updatedat is null
        defaultDealResourceStatusShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllDealResourceStatusesByDealrequirementmatchingresourcesResourcestatusIsEqualToSomething() throws Exception {
        DealRequirementMatchingResources dealrequirementmatchingresourcesResourcestatus;
        if (TestUtil.findAll(em, DealRequirementMatchingResources.class).isEmpty()) {
            dealResourceStatusRepository.saveAndFlush(dealResourceStatus);
            dealrequirementmatchingresourcesResourcestatus = DealRequirementMatchingResourcesResourceIT.createEntity(em);
        } else {
            dealrequirementmatchingresourcesResourcestatus = TestUtil.findAll(em, DealRequirementMatchingResources.class).get(0);
        }
        em.persist(dealrequirementmatchingresourcesResourcestatus);
        em.flush();
        dealResourceStatus.addDealrequirementmatchingresourcesResourcestatus(dealrequirementmatchingresourcesResourcestatus);
        dealResourceStatusRepository.saveAndFlush(dealResourceStatus);
        Long dealrequirementmatchingresourcesResourcestatusId = dealrequirementmatchingresourcesResourcestatus.getId();

        // Get all the dealResourceStatusList where dealrequirementmatchingresourcesResourcestatus equals to dealrequirementmatchingresourcesResourcestatusId
        defaultDealResourceStatusShouldBeFound(
            "dealrequirementmatchingresourcesResourcestatusId.equals=" + dealrequirementmatchingresourcesResourcestatusId
        );

        // Get all the dealResourceStatusList where dealrequirementmatchingresourcesResourcestatus equals to (dealrequirementmatchingresourcesResourcestatusId + 1)
        defaultDealResourceStatusShouldNotBeFound(
            "dealrequirementmatchingresourcesResourcestatusId.equals=" + (dealrequirementmatchingresourcesResourcestatusId + 1)
        );
    }

    @Test
    @Transactional
    void getAllDealResourceStatusesByDealrequirementmatchingresourcesSystemstatusIsEqualToSomething() throws Exception {
        DealRequirementMatchingResources dealrequirementmatchingresourcesSystemstatus;
        if (TestUtil.findAll(em, DealRequirementMatchingResources.class).isEmpty()) {
            dealResourceStatusRepository.saveAndFlush(dealResourceStatus);
            dealrequirementmatchingresourcesSystemstatus = DealRequirementMatchingResourcesResourceIT.createEntity(em);
        } else {
            dealrequirementmatchingresourcesSystemstatus = TestUtil.findAll(em, DealRequirementMatchingResources.class).get(0);
        }
        em.persist(dealrequirementmatchingresourcesSystemstatus);
        em.flush();
        dealResourceStatus.addDealrequirementmatchingresourcesSystemstatus(dealrequirementmatchingresourcesSystemstatus);
        dealResourceStatusRepository.saveAndFlush(dealResourceStatus);
        Long dealrequirementmatchingresourcesSystemstatusId = dealrequirementmatchingresourcesSystemstatus.getId();

        // Get all the dealResourceStatusList where dealrequirementmatchingresourcesSystemstatus equals to dealrequirementmatchingresourcesSystemstatusId
        defaultDealResourceStatusShouldBeFound(
            "dealrequirementmatchingresourcesSystemstatusId.equals=" + dealrequirementmatchingresourcesSystemstatusId
        );

        // Get all the dealResourceStatusList where dealrequirementmatchingresourcesSystemstatus equals to (dealrequirementmatchingresourcesSystemstatusId + 1)
        defaultDealResourceStatusShouldNotBeFound(
            "dealrequirementmatchingresourcesSystemstatusId.equals=" + (dealrequirementmatchingresourcesSystemstatusId + 1)
        );
    }

    @Test
    @Transactional
    void getAllDealResourceStatusesByDealresourceeventlogsResourcestatusIsEqualToSomething() throws Exception {
        DealResourceEventLogs dealresourceeventlogsResourcestatus;
        if (TestUtil.findAll(em, DealResourceEventLogs.class).isEmpty()) {
            dealResourceStatusRepository.saveAndFlush(dealResourceStatus);
            dealresourceeventlogsResourcestatus = DealResourceEventLogsResourceIT.createEntity(em);
        } else {
            dealresourceeventlogsResourcestatus = TestUtil.findAll(em, DealResourceEventLogs.class).get(0);
        }
        em.persist(dealresourceeventlogsResourcestatus);
        em.flush();
        dealResourceStatus.addDealresourceeventlogsResourcestatus(dealresourceeventlogsResourcestatus);
        dealResourceStatusRepository.saveAndFlush(dealResourceStatus);
        Long dealresourceeventlogsResourcestatusId = dealresourceeventlogsResourcestatus.getId();

        // Get all the dealResourceStatusList where dealresourceeventlogsResourcestatus equals to dealresourceeventlogsResourcestatusId
        defaultDealResourceStatusShouldBeFound("dealresourceeventlogsResourcestatusId.equals=" + dealresourceeventlogsResourcestatusId);

        // Get all the dealResourceStatusList where dealresourceeventlogsResourcestatus equals to (dealresourceeventlogsResourcestatusId + 1)
        defaultDealResourceStatusShouldNotBeFound(
            "dealresourceeventlogsResourcestatusId.equals=" + (dealresourceeventlogsResourcestatusId + 1)
        );
    }

    @Test
    @Transactional
    void getAllDealResourceStatusesByDealresourceeventlogsSystemstatusIsEqualToSomething() throws Exception {
        DealResourceEventLogs dealresourceeventlogsSystemstatus;
        if (TestUtil.findAll(em, DealResourceEventLogs.class).isEmpty()) {
            dealResourceStatusRepository.saveAndFlush(dealResourceStatus);
            dealresourceeventlogsSystemstatus = DealResourceEventLogsResourceIT.createEntity(em);
        } else {
            dealresourceeventlogsSystemstatus = TestUtil.findAll(em, DealResourceEventLogs.class).get(0);
        }
        em.persist(dealresourceeventlogsSystemstatus);
        em.flush();
        dealResourceStatus.addDealresourceeventlogsSystemstatus(dealresourceeventlogsSystemstatus);
        dealResourceStatusRepository.saveAndFlush(dealResourceStatus);
        Long dealresourceeventlogsSystemstatusId = dealresourceeventlogsSystemstatus.getId();

        // Get all the dealResourceStatusList where dealresourceeventlogsSystemstatus equals to dealresourceeventlogsSystemstatusId
        defaultDealResourceStatusShouldBeFound("dealresourceeventlogsSystemstatusId.equals=" + dealresourceeventlogsSystemstatusId);

        // Get all the dealResourceStatusList where dealresourceeventlogsSystemstatus equals to (dealresourceeventlogsSystemstatusId + 1)
        defaultDealResourceStatusShouldNotBeFound(
            "dealresourceeventlogsSystemstatusId.equals=" + (dealresourceeventlogsSystemstatusId + 1)
        );
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDealResourceStatusShouldBeFound(String filter) throws Exception {
        restDealResourceStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dealResourceStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].displayname").value(hasItem(DEFAULT_DISPLAYNAME)))
            .andExpect(jsonPath("$.[*].group").value(hasItem(DEFAULT_GROUP)))
            .andExpect(jsonPath("$.[*].systemKey").value(hasItem(DEFAULT_SYSTEM_KEY)))
            .andExpect(jsonPath("$.[*].effectivedate").value(hasItem(DEFAULT_EFFECTIVEDATE.toString())))
            .andExpect(jsonPath("$.[*].enddate").value(hasItem(DEFAULT_ENDDATE.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restDealResourceStatusMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDealResourceStatusShouldNotBeFound(String filter) throws Exception {
        restDealResourceStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDealResourceStatusMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDealResourceStatus() throws Exception {
        // Get the dealResourceStatus
        restDealResourceStatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDealResourceStatus() throws Exception {
        // Initialize the database
        dealResourceStatusRepository.saveAndFlush(dealResourceStatus);

        int databaseSizeBeforeUpdate = dealResourceStatusRepository.findAll().size();

        // Update the dealResourceStatus
        DealResourceStatus updatedDealResourceStatus = dealResourceStatusRepository.findById(dealResourceStatus.getId()).get();
        // Disconnect from session so that the updates on updatedDealResourceStatus are not directly saved in db
        em.detach(updatedDealResourceStatus);
        updatedDealResourceStatus
            .displayname(UPDATED_DISPLAYNAME)
            .group(UPDATED_GROUP)
            .systemKey(UPDATED_SYSTEM_KEY)
            .effectivedate(UPDATED_EFFECTIVEDATE)
            .enddate(UPDATED_ENDDATE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restDealResourceStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDealResourceStatus.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDealResourceStatus))
            )
            .andExpect(status().isOk());

        // Validate the DealResourceStatus in the database
        List<DealResourceStatus> dealResourceStatusList = dealResourceStatusRepository.findAll();
        assertThat(dealResourceStatusList).hasSize(databaseSizeBeforeUpdate);
        DealResourceStatus testDealResourceStatus = dealResourceStatusList.get(dealResourceStatusList.size() - 1);
        assertThat(testDealResourceStatus.getDisplayname()).isEqualTo(UPDATED_DISPLAYNAME);
        assertThat(testDealResourceStatus.getGroup()).isEqualTo(UPDATED_GROUP);
        assertThat(testDealResourceStatus.getSystemKey()).isEqualTo(UPDATED_SYSTEM_KEY);
        assertThat(testDealResourceStatus.getEffectivedate()).isEqualTo(UPDATED_EFFECTIVEDATE);
        assertThat(testDealResourceStatus.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testDealResourceStatus.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testDealResourceStatus.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingDealResourceStatus() throws Exception {
        int databaseSizeBeforeUpdate = dealResourceStatusRepository.findAll().size();
        dealResourceStatus.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDealResourceStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dealResourceStatus.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealResourceStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealResourceStatus in the database
        List<DealResourceStatus> dealResourceStatusList = dealResourceStatusRepository.findAll();
        assertThat(dealResourceStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDealResourceStatus() throws Exception {
        int databaseSizeBeforeUpdate = dealResourceStatusRepository.findAll().size();
        dealResourceStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealResourceStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealResourceStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealResourceStatus in the database
        List<DealResourceStatus> dealResourceStatusList = dealResourceStatusRepository.findAll();
        assertThat(dealResourceStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDealResourceStatus() throws Exception {
        int databaseSizeBeforeUpdate = dealResourceStatusRepository.findAll().size();
        dealResourceStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealResourceStatusMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealResourceStatus))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DealResourceStatus in the database
        List<DealResourceStatus> dealResourceStatusList = dealResourceStatusRepository.findAll();
        assertThat(dealResourceStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDealResourceStatusWithPatch() throws Exception {
        // Initialize the database
        dealResourceStatusRepository.saveAndFlush(dealResourceStatus);

        int databaseSizeBeforeUpdate = dealResourceStatusRepository.findAll().size();

        // Update the dealResourceStatus using partial update
        DealResourceStatus partialUpdatedDealResourceStatus = new DealResourceStatus();
        partialUpdatedDealResourceStatus.setId(dealResourceStatus.getId());

        partialUpdatedDealResourceStatus.group(UPDATED_GROUP).enddate(UPDATED_ENDDATE).createdat(UPDATED_CREATEDAT);

        restDealResourceStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDealResourceStatus.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDealResourceStatus))
            )
            .andExpect(status().isOk());

        // Validate the DealResourceStatus in the database
        List<DealResourceStatus> dealResourceStatusList = dealResourceStatusRepository.findAll();
        assertThat(dealResourceStatusList).hasSize(databaseSizeBeforeUpdate);
        DealResourceStatus testDealResourceStatus = dealResourceStatusList.get(dealResourceStatusList.size() - 1);
        assertThat(testDealResourceStatus.getDisplayname()).isEqualTo(DEFAULT_DISPLAYNAME);
        assertThat(testDealResourceStatus.getGroup()).isEqualTo(UPDATED_GROUP);
        assertThat(testDealResourceStatus.getSystemKey()).isEqualTo(DEFAULT_SYSTEM_KEY);
        assertThat(testDealResourceStatus.getEffectivedate()).isEqualTo(DEFAULT_EFFECTIVEDATE);
        assertThat(testDealResourceStatus.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testDealResourceStatus.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testDealResourceStatus.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateDealResourceStatusWithPatch() throws Exception {
        // Initialize the database
        dealResourceStatusRepository.saveAndFlush(dealResourceStatus);

        int databaseSizeBeforeUpdate = dealResourceStatusRepository.findAll().size();

        // Update the dealResourceStatus using partial update
        DealResourceStatus partialUpdatedDealResourceStatus = new DealResourceStatus();
        partialUpdatedDealResourceStatus.setId(dealResourceStatus.getId());

        partialUpdatedDealResourceStatus
            .displayname(UPDATED_DISPLAYNAME)
            .group(UPDATED_GROUP)
            .systemKey(UPDATED_SYSTEM_KEY)
            .effectivedate(UPDATED_EFFECTIVEDATE)
            .enddate(UPDATED_ENDDATE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restDealResourceStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDealResourceStatus.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDealResourceStatus))
            )
            .andExpect(status().isOk());

        // Validate the DealResourceStatus in the database
        List<DealResourceStatus> dealResourceStatusList = dealResourceStatusRepository.findAll();
        assertThat(dealResourceStatusList).hasSize(databaseSizeBeforeUpdate);
        DealResourceStatus testDealResourceStatus = dealResourceStatusList.get(dealResourceStatusList.size() - 1);
        assertThat(testDealResourceStatus.getDisplayname()).isEqualTo(UPDATED_DISPLAYNAME);
        assertThat(testDealResourceStatus.getGroup()).isEqualTo(UPDATED_GROUP);
        assertThat(testDealResourceStatus.getSystemKey()).isEqualTo(UPDATED_SYSTEM_KEY);
        assertThat(testDealResourceStatus.getEffectivedate()).isEqualTo(UPDATED_EFFECTIVEDATE);
        assertThat(testDealResourceStatus.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testDealResourceStatus.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testDealResourceStatus.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingDealResourceStatus() throws Exception {
        int databaseSizeBeforeUpdate = dealResourceStatusRepository.findAll().size();
        dealResourceStatus.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDealResourceStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dealResourceStatus.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dealResourceStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealResourceStatus in the database
        List<DealResourceStatus> dealResourceStatusList = dealResourceStatusRepository.findAll();
        assertThat(dealResourceStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDealResourceStatus() throws Exception {
        int databaseSizeBeforeUpdate = dealResourceStatusRepository.findAll().size();
        dealResourceStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealResourceStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dealResourceStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealResourceStatus in the database
        List<DealResourceStatus> dealResourceStatusList = dealResourceStatusRepository.findAll();
        assertThat(dealResourceStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDealResourceStatus() throws Exception {
        int databaseSizeBeforeUpdate = dealResourceStatusRepository.findAll().size();
        dealResourceStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealResourceStatusMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dealResourceStatus))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DealResourceStatus in the database
        List<DealResourceStatus> dealResourceStatusList = dealResourceStatusRepository.findAll();
        assertThat(dealResourceStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDealResourceStatus() throws Exception {
        // Initialize the database
        dealResourceStatusRepository.saveAndFlush(dealResourceStatus);

        int databaseSizeBeforeDelete = dealResourceStatusRepository.findAll().size();

        // Delete the dealResourceStatus
        restDealResourceStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, dealResourceStatus.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DealResourceStatus> dealResourceStatusList = dealResourceStatusRepository.findAll();
        assertThat(dealResourceStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
