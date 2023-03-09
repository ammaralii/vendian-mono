package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.LeaveRequestsOlds;
import com.venturedive.vendian_mono.domain.LeaveTypesOlds;
import com.venturedive.vendian_mono.repository.LeaveRequestsOldsRepository;
import com.venturedive.vendian_mono.service.criteria.LeaveRequestsOldsCriteria;
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
 * Integration tests for the {@link LeaveRequestsOldsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeaveRequestsOldsResourceIT {

    private static final Instant DEFAULT_STARTDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STARTDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ENDDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ENDDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_REQUESTERNOTE = "AAAAAAAAAA";
    private static final String UPDATED_REQUESTERNOTE = "BBBBBBBBBB";

    private static final String DEFAULT_MANAGERNOTE = "AAAAAAAAAA";
    private static final String UPDATED_MANAGERNOTE = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENTSTATUS = "AAAAAAAAAA";
    private static final String UPDATED_CURRENTSTATUS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_LEAVESCANCELED = false;
    private static final Boolean UPDATED_LEAVESCANCELED = true;

    private static final Instant DEFAULT_REQUESTDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REQUESTDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LINKSTRING = "AAAAAAAAAA";
    private static final String UPDATED_LINKSTRING = "BBBBBBBBBB";

    private static final Boolean DEFAULT_LINKUSED = false;
    private static final Boolean UPDATED_LINKUSED = true;

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ISHALFDAY = false;
    private static final Boolean UPDATED_ISHALFDAY = true;

    private static final Instant DEFAULT_ACTIONDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ACTIONDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LMSTATUS = "AAAAAAAAAA";
    private static final String UPDATED_LMSTATUS = "BBBBBBBBBB";

    private static final String DEFAULT_PMSTATUS = "AAAAAAAAAA";
    private static final String UPDATED_PMSTATUS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ISBENCH = false;
    private static final Boolean UPDATED_ISBENCH = true;

    private static final Boolean DEFAULT_ISESCALATED = false;
    private static final Boolean UPDATED_ISESCALATED = true;

    private static final Boolean DEFAULT_ISCOMMENTED = false;
    private static final Boolean UPDATED_ISCOMMENTED = true;

    private static final Boolean DEFAULT_ISREMINDED = false;
    private static final Boolean UPDATED_ISREMINDED = true;

    private static final Boolean DEFAULT_ISNOTIFIED = false;
    private static final Boolean UPDATED_ISNOTIFIED = true;

    private static final Boolean DEFAULT_ISREMINDEDHR = false;
    private static final Boolean UPDATED_ISREMINDEDHR = true;

    private static final Boolean DEFAULT_ISDM = false;
    private static final Boolean UPDATED_ISDM = true;

    private static final String ENTITY_API_URL = "/api/leave-requests-olds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LeaveRequestsOldsRepository leaveRequestsOldsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeaveRequestsOldsMockMvc;

    private LeaveRequestsOlds leaveRequestsOlds;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveRequestsOlds createEntity(EntityManager em) {
        LeaveRequestsOlds leaveRequestsOlds = new LeaveRequestsOlds()
            .startdate(DEFAULT_STARTDATE)
            .enddate(DEFAULT_ENDDATE)
            .requesternote(DEFAULT_REQUESTERNOTE)
            .managernote(DEFAULT_MANAGERNOTE)
            .currentstatus(DEFAULT_CURRENTSTATUS)
            .leavescanceled(DEFAULT_LEAVESCANCELED)
            .requestdate(DEFAULT_REQUESTDATE)
            .linkstring(DEFAULT_LINKSTRING)
            .linkused(DEFAULT_LINKUSED)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT)
            .ishalfday(DEFAULT_ISHALFDAY)
            .actiondate(DEFAULT_ACTIONDATE)
            .lmstatus(DEFAULT_LMSTATUS)
            .pmstatus(DEFAULT_PMSTATUS)
            .isbench(DEFAULT_ISBENCH)
            .isescalated(DEFAULT_ISESCALATED)
            .iscommented(DEFAULT_ISCOMMENTED)
            .isreminded(DEFAULT_ISREMINDED)
            .isnotified(DEFAULT_ISNOTIFIED)
            .isremindedhr(DEFAULT_ISREMINDEDHR)
            .isdm(DEFAULT_ISDM);
        return leaveRequestsOlds;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveRequestsOlds createUpdatedEntity(EntityManager em) {
        LeaveRequestsOlds leaveRequestsOlds = new LeaveRequestsOlds()
            .startdate(UPDATED_STARTDATE)
            .enddate(UPDATED_ENDDATE)
            .requesternote(UPDATED_REQUESTERNOTE)
            .managernote(UPDATED_MANAGERNOTE)
            .currentstatus(UPDATED_CURRENTSTATUS)
            .leavescanceled(UPDATED_LEAVESCANCELED)
            .requestdate(UPDATED_REQUESTDATE)
            .linkstring(UPDATED_LINKSTRING)
            .linkused(UPDATED_LINKUSED)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .ishalfday(UPDATED_ISHALFDAY)
            .actiondate(UPDATED_ACTIONDATE)
            .lmstatus(UPDATED_LMSTATUS)
            .pmstatus(UPDATED_PMSTATUS)
            .isbench(UPDATED_ISBENCH)
            .isescalated(UPDATED_ISESCALATED)
            .iscommented(UPDATED_ISCOMMENTED)
            .isreminded(UPDATED_ISREMINDED)
            .isnotified(UPDATED_ISNOTIFIED)
            .isremindedhr(UPDATED_ISREMINDEDHR)
            .isdm(UPDATED_ISDM);
        return leaveRequestsOlds;
    }

    @BeforeEach
    public void initTest() {
        leaveRequestsOlds = createEntity(em);
    }

    @Test
    @Transactional
    void createLeaveRequestsOlds() throws Exception {
        int databaseSizeBeforeCreate = leaveRequestsOldsRepository.findAll().size();
        // Create the LeaveRequestsOlds
        restLeaveRequestsOldsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequestsOlds))
            )
            .andExpect(status().isCreated());

        // Validate the LeaveRequestsOlds in the database
        List<LeaveRequestsOlds> leaveRequestsOldsList = leaveRequestsOldsRepository.findAll();
        assertThat(leaveRequestsOldsList).hasSize(databaseSizeBeforeCreate + 1);
        LeaveRequestsOlds testLeaveRequestsOlds = leaveRequestsOldsList.get(leaveRequestsOldsList.size() - 1);
        assertThat(testLeaveRequestsOlds.getStartdate()).isEqualTo(DEFAULT_STARTDATE);
        assertThat(testLeaveRequestsOlds.getEnddate()).isEqualTo(DEFAULT_ENDDATE);
        assertThat(testLeaveRequestsOlds.getRequesternote()).isEqualTo(DEFAULT_REQUESTERNOTE);
        assertThat(testLeaveRequestsOlds.getManagernote()).isEqualTo(DEFAULT_MANAGERNOTE);
        assertThat(testLeaveRequestsOlds.getCurrentstatus()).isEqualTo(DEFAULT_CURRENTSTATUS);
        assertThat(testLeaveRequestsOlds.getLeavescanceled()).isEqualTo(DEFAULT_LEAVESCANCELED);
        assertThat(testLeaveRequestsOlds.getRequestdate()).isEqualTo(DEFAULT_REQUESTDATE);
        assertThat(testLeaveRequestsOlds.getLinkstring()).isEqualTo(DEFAULT_LINKSTRING);
        assertThat(testLeaveRequestsOlds.getLinkused()).isEqualTo(DEFAULT_LINKUSED);
        assertThat(testLeaveRequestsOlds.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testLeaveRequestsOlds.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testLeaveRequestsOlds.getIshalfday()).isEqualTo(DEFAULT_ISHALFDAY);
        assertThat(testLeaveRequestsOlds.getActiondate()).isEqualTo(DEFAULT_ACTIONDATE);
        assertThat(testLeaveRequestsOlds.getLmstatus()).isEqualTo(DEFAULT_LMSTATUS);
        assertThat(testLeaveRequestsOlds.getPmstatus()).isEqualTo(DEFAULT_PMSTATUS);
        assertThat(testLeaveRequestsOlds.getIsbench()).isEqualTo(DEFAULT_ISBENCH);
        assertThat(testLeaveRequestsOlds.getIsescalated()).isEqualTo(DEFAULT_ISESCALATED);
        assertThat(testLeaveRequestsOlds.getIscommented()).isEqualTo(DEFAULT_ISCOMMENTED);
        assertThat(testLeaveRequestsOlds.getIsreminded()).isEqualTo(DEFAULT_ISREMINDED);
        assertThat(testLeaveRequestsOlds.getIsnotified()).isEqualTo(DEFAULT_ISNOTIFIED);
        assertThat(testLeaveRequestsOlds.getIsremindedhr()).isEqualTo(DEFAULT_ISREMINDEDHR);
        assertThat(testLeaveRequestsOlds.getIsdm()).isEqualTo(DEFAULT_ISDM);
    }

    @Test
    @Transactional
    void createLeaveRequestsOldsWithExistingId() throws Exception {
        // Create the LeaveRequestsOlds with an existing ID
        leaveRequestsOlds.setId(1L);

        int databaseSizeBeforeCreate = leaveRequestsOldsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeaveRequestsOldsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequestsOlds))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveRequestsOlds in the database
        List<LeaveRequestsOlds> leaveRequestsOldsList = leaveRequestsOldsRepository.findAll();
        assertThat(leaveRequestsOldsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveRequestsOldsRepository.findAll().size();
        // set the field null
        leaveRequestsOlds.setCreatedat(null);

        // Create the LeaveRequestsOlds, which fails.

        restLeaveRequestsOldsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequestsOlds))
            )
            .andExpect(status().isBadRequest());

        List<LeaveRequestsOlds> leaveRequestsOldsList = leaveRequestsOldsRepository.findAll();
        assertThat(leaveRequestsOldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveRequestsOldsRepository.findAll().size();
        // set the field null
        leaveRequestsOlds.setUpdatedat(null);

        // Create the LeaveRequestsOlds, which fails.

        restLeaveRequestsOldsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequestsOlds))
            )
            .andExpect(status().isBadRequest());

        List<LeaveRequestsOlds> leaveRequestsOldsList = leaveRequestsOldsRepository.findAll();
        assertThat(leaveRequestsOldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOlds() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList
        restLeaveRequestsOldsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveRequestsOlds.getId().intValue())))
            .andExpect(jsonPath("$.[*].startdate").value(hasItem(DEFAULT_STARTDATE.toString())))
            .andExpect(jsonPath("$.[*].enddate").value(hasItem(DEFAULT_ENDDATE.toString())))
            .andExpect(jsonPath("$.[*].requesternote").value(hasItem(DEFAULT_REQUESTERNOTE)))
            .andExpect(jsonPath("$.[*].managernote").value(hasItem(DEFAULT_MANAGERNOTE)))
            .andExpect(jsonPath("$.[*].currentstatus").value(hasItem(DEFAULT_CURRENTSTATUS)))
            .andExpect(jsonPath("$.[*].leavescanceled").value(hasItem(DEFAULT_LEAVESCANCELED.booleanValue())))
            .andExpect(jsonPath("$.[*].requestdate").value(hasItem(DEFAULT_REQUESTDATE.toString())))
            .andExpect(jsonPath("$.[*].linkstring").value(hasItem(DEFAULT_LINKSTRING)))
            .andExpect(jsonPath("$.[*].linkused").value(hasItem(DEFAULT_LINKUSED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].ishalfday").value(hasItem(DEFAULT_ISHALFDAY.booleanValue())))
            .andExpect(jsonPath("$.[*].actiondate").value(hasItem(DEFAULT_ACTIONDATE.toString())))
            .andExpect(jsonPath("$.[*].lmstatus").value(hasItem(DEFAULT_LMSTATUS)))
            .andExpect(jsonPath("$.[*].pmstatus").value(hasItem(DEFAULT_PMSTATUS)))
            .andExpect(jsonPath("$.[*].isbench").value(hasItem(DEFAULT_ISBENCH.booleanValue())))
            .andExpect(jsonPath("$.[*].isescalated").value(hasItem(DEFAULT_ISESCALATED.booleanValue())))
            .andExpect(jsonPath("$.[*].iscommented").value(hasItem(DEFAULT_ISCOMMENTED.booleanValue())))
            .andExpect(jsonPath("$.[*].isreminded").value(hasItem(DEFAULT_ISREMINDED.booleanValue())))
            .andExpect(jsonPath("$.[*].isnotified").value(hasItem(DEFAULT_ISNOTIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].isremindedhr").value(hasItem(DEFAULT_ISREMINDEDHR.booleanValue())))
            .andExpect(jsonPath("$.[*].isdm").value(hasItem(DEFAULT_ISDM.booleanValue())));
    }

    @Test
    @Transactional
    void getLeaveRequestsOlds() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get the leaveRequestsOlds
        restLeaveRequestsOldsMockMvc
            .perform(get(ENTITY_API_URL_ID, leaveRequestsOlds.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leaveRequestsOlds.getId().intValue()))
            .andExpect(jsonPath("$.startdate").value(DEFAULT_STARTDATE.toString()))
            .andExpect(jsonPath("$.enddate").value(DEFAULT_ENDDATE.toString()))
            .andExpect(jsonPath("$.requesternote").value(DEFAULT_REQUESTERNOTE))
            .andExpect(jsonPath("$.managernote").value(DEFAULT_MANAGERNOTE))
            .andExpect(jsonPath("$.currentstatus").value(DEFAULT_CURRENTSTATUS))
            .andExpect(jsonPath("$.leavescanceled").value(DEFAULT_LEAVESCANCELED.booleanValue()))
            .andExpect(jsonPath("$.requestdate").value(DEFAULT_REQUESTDATE.toString()))
            .andExpect(jsonPath("$.linkstring").value(DEFAULT_LINKSTRING))
            .andExpect(jsonPath("$.linkused").value(DEFAULT_LINKUSED.booleanValue()))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()))
            .andExpect(jsonPath("$.ishalfday").value(DEFAULT_ISHALFDAY.booleanValue()))
            .andExpect(jsonPath("$.actiondate").value(DEFAULT_ACTIONDATE.toString()))
            .andExpect(jsonPath("$.lmstatus").value(DEFAULT_LMSTATUS))
            .andExpect(jsonPath("$.pmstatus").value(DEFAULT_PMSTATUS))
            .andExpect(jsonPath("$.isbench").value(DEFAULT_ISBENCH.booleanValue()))
            .andExpect(jsonPath("$.isescalated").value(DEFAULT_ISESCALATED.booleanValue()))
            .andExpect(jsonPath("$.iscommented").value(DEFAULT_ISCOMMENTED.booleanValue()))
            .andExpect(jsonPath("$.isreminded").value(DEFAULT_ISREMINDED.booleanValue()))
            .andExpect(jsonPath("$.isnotified").value(DEFAULT_ISNOTIFIED.booleanValue()))
            .andExpect(jsonPath("$.isremindedhr").value(DEFAULT_ISREMINDEDHR.booleanValue()))
            .andExpect(jsonPath("$.isdm").value(DEFAULT_ISDM.booleanValue()));
    }

    @Test
    @Transactional
    void getLeaveRequestsOldsByIdFiltering() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        Long id = leaveRequestsOlds.getId();

        defaultLeaveRequestsOldsShouldBeFound("id.equals=" + id);
        defaultLeaveRequestsOldsShouldNotBeFound("id.notEquals=" + id);

        defaultLeaveRequestsOldsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLeaveRequestsOldsShouldNotBeFound("id.greaterThan=" + id);

        defaultLeaveRequestsOldsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLeaveRequestsOldsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByStartdateIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where startdate equals to DEFAULT_STARTDATE
        defaultLeaveRequestsOldsShouldBeFound("startdate.equals=" + DEFAULT_STARTDATE);

        // Get all the leaveRequestsOldsList where startdate equals to UPDATED_STARTDATE
        defaultLeaveRequestsOldsShouldNotBeFound("startdate.equals=" + UPDATED_STARTDATE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByStartdateIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where startdate in DEFAULT_STARTDATE or UPDATED_STARTDATE
        defaultLeaveRequestsOldsShouldBeFound("startdate.in=" + DEFAULT_STARTDATE + "," + UPDATED_STARTDATE);

        // Get all the leaveRequestsOldsList where startdate equals to UPDATED_STARTDATE
        defaultLeaveRequestsOldsShouldNotBeFound("startdate.in=" + UPDATED_STARTDATE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByStartdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where startdate is not null
        defaultLeaveRequestsOldsShouldBeFound("startdate.specified=true");

        // Get all the leaveRequestsOldsList where startdate is null
        defaultLeaveRequestsOldsShouldNotBeFound("startdate.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByEnddateIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where enddate equals to DEFAULT_ENDDATE
        defaultLeaveRequestsOldsShouldBeFound("enddate.equals=" + DEFAULT_ENDDATE);

        // Get all the leaveRequestsOldsList where enddate equals to UPDATED_ENDDATE
        defaultLeaveRequestsOldsShouldNotBeFound("enddate.equals=" + UPDATED_ENDDATE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByEnddateIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where enddate in DEFAULT_ENDDATE or UPDATED_ENDDATE
        defaultLeaveRequestsOldsShouldBeFound("enddate.in=" + DEFAULT_ENDDATE + "," + UPDATED_ENDDATE);

        // Get all the leaveRequestsOldsList where enddate equals to UPDATED_ENDDATE
        defaultLeaveRequestsOldsShouldNotBeFound("enddate.in=" + UPDATED_ENDDATE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByEnddateIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where enddate is not null
        defaultLeaveRequestsOldsShouldBeFound("enddate.specified=true");

        // Get all the leaveRequestsOldsList where enddate is null
        defaultLeaveRequestsOldsShouldNotBeFound("enddate.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByRequesternoteIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where requesternote equals to DEFAULT_REQUESTERNOTE
        defaultLeaveRequestsOldsShouldBeFound("requesternote.equals=" + DEFAULT_REQUESTERNOTE);

        // Get all the leaveRequestsOldsList where requesternote equals to UPDATED_REQUESTERNOTE
        defaultLeaveRequestsOldsShouldNotBeFound("requesternote.equals=" + UPDATED_REQUESTERNOTE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByRequesternoteIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where requesternote in DEFAULT_REQUESTERNOTE or UPDATED_REQUESTERNOTE
        defaultLeaveRequestsOldsShouldBeFound("requesternote.in=" + DEFAULT_REQUESTERNOTE + "," + UPDATED_REQUESTERNOTE);

        // Get all the leaveRequestsOldsList where requesternote equals to UPDATED_REQUESTERNOTE
        defaultLeaveRequestsOldsShouldNotBeFound("requesternote.in=" + UPDATED_REQUESTERNOTE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByRequesternoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where requesternote is not null
        defaultLeaveRequestsOldsShouldBeFound("requesternote.specified=true");

        // Get all the leaveRequestsOldsList where requesternote is null
        defaultLeaveRequestsOldsShouldNotBeFound("requesternote.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByRequesternoteContainsSomething() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where requesternote contains DEFAULT_REQUESTERNOTE
        defaultLeaveRequestsOldsShouldBeFound("requesternote.contains=" + DEFAULT_REQUESTERNOTE);

        // Get all the leaveRequestsOldsList where requesternote contains UPDATED_REQUESTERNOTE
        defaultLeaveRequestsOldsShouldNotBeFound("requesternote.contains=" + UPDATED_REQUESTERNOTE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByRequesternoteNotContainsSomething() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where requesternote does not contain DEFAULT_REQUESTERNOTE
        defaultLeaveRequestsOldsShouldNotBeFound("requesternote.doesNotContain=" + DEFAULT_REQUESTERNOTE);

        // Get all the leaveRequestsOldsList where requesternote does not contain UPDATED_REQUESTERNOTE
        defaultLeaveRequestsOldsShouldBeFound("requesternote.doesNotContain=" + UPDATED_REQUESTERNOTE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByManagernoteIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where managernote equals to DEFAULT_MANAGERNOTE
        defaultLeaveRequestsOldsShouldBeFound("managernote.equals=" + DEFAULT_MANAGERNOTE);

        // Get all the leaveRequestsOldsList where managernote equals to UPDATED_MANAGERNOTE
        defaultLeaveRequestsOldsShouldNotBeFound("managernote.equals=" + UPDATED_MANAGERNOTE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByManagernoteIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where managernote in DEFAULT_MANAGERNOTE or UPDATED_MANAGERNOTE
        defaultLeaveRequestsOldsShouldBeFound("managernote.in=" + DEFAULT_MANAGERNOTE + "," + UPDATED_MANAGERNOTE);

        // Get all the leaveRequestsOldsList where managernote equals to UPDATED_MANAGERNOTE
        defaultLeaveRequestsOldsShouldNotBeFound("managernote.in=" + UPDATED_MANAGERNOTE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByManagernoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where managernote is not null
        defaultLeaveRequestsOldsShouldBeFound("managernote.specified=true");

        // Get all the leaveRequestsOldsList where managernote is null
        defaultLeaveRequestsOldsShouldNotBeFound("managernote.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByManagernoteContainsSomething() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where managernote contains DEFAULT_MANAGERNOTE
        defaultLeaveRequestsOldsShouldBeFound("managernote.contains=" + DEFAULT_MANAGERNOTE);

        // Get all the leaveRequestsOldsList where managernote contains UPDATED_MANAGERNOTE
        defaultLeaveRequestsOldsShouldNotBeFound("managernote.contains=" + UPDATED_MANAGERNOTE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByManagernoteNotContainsSomething() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where managernote does not contain DEFAULT_MANAGERNOTE
        defaultLeaveRequestsOldsShouldNotBeFound("managernote.doesNotContain=" + DEFAULT_MANAGERNOTE);

        // Get all the leaveRequestsOldsList where managernote does not contain UPDATED_MANAGERNOTE
        defaultLeaveRequestsOldsShouldBeFound("managernote.doesNotContain=" + UPDATED_MANAGERNOTE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByCurrentstatusIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where currentstatus equals to DEFAULT_CURRENTSTATUS
        defaultLeaveRequestsOldsShouldBeFound("currentstatus.equals=" + DEFAULT_CURRENTSTATUS);

        // Get all the leaveRequestsOldsList where currentstatus equals to UPDATED_CURRENTSTATUS
        defaultLeaveRequestsOldsShouldNotBeFound("currentstatus.equals=" + UPDATED_CURRENTSTATUS);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByCurrentstatusIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where currentstatus in DEFAULT_CURRENTSTATUS or UPDATED_CURRENTSTATUS
        defaultLeaveRequestsOldsShouldBeFound("currentstatus.in=" + DEFAULT_CURRENTSTATUS + "," + UPDATED_CURRENTSTATUS);

        // Get all the leaveRequestsOldsList where currentstatus equals to UPDATED_CURRENTSTATUS
        defaultLeaveRequestsOldsShouldNotBeFound("currentstatus.in=" + UPDATED_CURRENTSTATUS);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByCurrentstatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where currentstatus is not null
        defaultLeaveRequestsOldsShouldBeFound("currentstatus.specified=true");

        // Get all the leaveRequestsOldsList where currentstatus is null
        defaultLeaveRequestsOldsShouldNotBeFound("currentstatus.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByCurrentstatusContainsSomething() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where currentstatus contains DEFAULT_CURRENTSTATUS
        defaultLeaveRequestsOldsShouldBeFound("currentstatus.contains=" + DEFAULT_CURRENTSTATUS);

        // Get all the leaveRequestsOldsList where currentstatus contains UPDATED_CURRENTSTATUS
        defaultLeaveRequestsOldsShouldNotBeFound("currentstatus.contains=" + UPDATED_CURRENTSTATUS);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByCurrentstatusNotContainsSomething() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where currentstatus does not contain DEFAULT_CURRENTSTATUS
        defaultLeaveRequestsOldsShouldNotBeFound("currentstatus.doesNotContain=" + DEFAULT_CURRENTSTATUS);

        // Get all the leaveRequestsOldsList where currentstatus does not contain UPDATED_CURRENTSTATUS
        defaultLeaveRequestsOldsShouldBeFound("currentstatus.doesNotContain=" + UPDATED_CURRENTSTATUS);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByLeavescanceledIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where leavescanceled equals to DEFAULT_LEAVESCANCELED
        defaultLeaveRequestsOldsShouldBeFound("leavescanceled.equals=" + DEFAULT_LEAVESCANCELED);

        // Get all the leaveRequestsOldsList where leavescanceled equals to UPDATED_LEAVESCANCELED
        defaultLeaveRequestsOldsShouldNotBeFound("leavescanceled.equals=" + UPDATED_LEAVESCANCELED);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByLeavescanceledIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where leavescanceled in DEFAULT_LEAVESCANCELED or UPDATED_LEAVESCANCELED
        defaultLeaveRequestsOldsShouldBeFound("leavescanceled.in=" + DEFAULT_LEAVESCANCELED + "," + UPDATED_LEAVESCANCELED);

        // Get all the leaveRequestsOldsList where leavescanceled equals to UPDATED_LEAVESCANCELED
        defaultLeaveRequestsOldsShouldNotBeFound("leavescanceled.in=" + UPDATED_LEAVESCANCELED);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByLeavescanceledIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where leavescanceled is not null
        defaultLeaveRequestsOldsShouldBeFound("leavescanceled.specified=true");

        // Get all the leaveRequestsOldsList where leavescanceled is null
        defaultLeaveRequestsOldsShouldNotBeFound("leavescanceled.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByRequestdateIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where requestdate equals to DEFAULT_REQUESTDATE
        defaultLeaveRequestsOldsShouldBeFound("requestdate.equals=" + DEFAULT_REQUESTDATE);

        // Get all the leaveRequestsOldsList where requestdate equals to UPDATED_REQUESTDATE
        defaultLeaveRequestsOldsShouldNotBeFound("requestdate.equals=" + UPDATED_REQUESTDATE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByRequestdateIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where requestdate in DEFAULT_REQUESTDATE or UPDATED_REQUESTDATE
        defaultLeaveRequestsOldsShouldBeFound("requestdate.in=" + DEFAULT_REQUESTDATE + "," + UPDATED_REQUESTDATE);

        // Get all the leaveRequestsOldsList where requestdate equals to UPDATED_REQUESTDATE
        defaultLeaveRequestsOldsShouldNotBeFound("requestdate.in=" + UPDATED_REQUESTDATE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByRequestdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where requestdate is not null
        defaultLeaveRequestsOldsShouldBeFound("requestdate.specified=true");

        // Get all the leaveRequestsOldsList where requestdate is null
        defaultLeaveRequestsOldsShouldNotBeFound("requestdate.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByLinkstringIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where linkstring equals to DEFAULT_LINKSTRING
        defaultLeaveRequestsOldsShouldBeFound("linkstring.equals=" + DEFAULT_LINKSTRING);

        // Get all the leaveRequestsOldsList where linkstring equals to UPDATED_LINKSTRING
        defaultLeaveRequestsOldsShouldNotBeFound("linkstring.equals=" + UPDATED_LINKSTRING);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByLinkstringIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where linkstring in DEFAULT_LINKSTRING or UPDATED_LINKSTRING
        defaultLeaveRequestsOldsShouldBeFound("linkstring.in=" + DEFAULT_LINKSTRING + "," + UPDATED_LINKSTRING);

        // Get all the leaveRequestsOldsList where linkstring equals to UPDATED_LINKSTRING
        defaultLeaveRequestsOldsShouldNotBeFound("linkstring.in=" + UPDATED_LINKSTRING);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByLinkstringIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where linkstring is not null
        defaultLeaveRequestsOldsShouldBeFound("linkstring.specified=true");

        // Get all the leaveRequestsOldsList where linkstring is null
        defaultLeaveRequestsOldsShouldNotBeFound("linkstring.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByLinkstringContainsSomething() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where linkstring contains DEFAULT_LINKSTRING
        defaultLeaveRequestsOldsShouldBeFound("linkstring.contains=" + DEFAULT_LINKSTRING);

        // Get all the leaveRequestsOldsList where linkstring contains UPDATED_LINKSTRING
        defaultLeaveRequestsOldsShouldNotBeFound("linkstring.contains=" + UPDATED_LINKSTRING);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByLinkstringNotContainsSomething() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where linkstring does not contain DEFAULT_LINKSTRING
        defaultLeaveRequestsOldsShouldNotBeFound("linkstring.doesNotContain=" + DEFAULT_LINKSTRING);

        // Get all the leaveRequestsOldsList where linkstring does not contain UPDATED_LINKSTRING
        defaultLeaveRequestsOldsShouldBeFound("linkstring.doesNotContain=" + UPDATED_LINKSTRING);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByLinkusedIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where linkused equals to DEFAULT_LINKUSED
        defaultLeaveRequestsOldsShouldBeFound("linkused.equals=" + DEFAULT_LINKUSED);

        // Get all the leaveRequestsOldsList where linkused equals to UPDATED_LINKUSED
        defaultLeaveRequestsOldsShouldNotBeFound("linkused.equals=" + UPDATED_LINKUSED);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByLinkusedIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where linkused in DEFAULT_LINKUSED or UPDATED_LINKUSED
        defaultLeaveRequestsOldsShouldBeFound("linkused.in=" + DEFAULT_LINKUSED + "," + UPDATED_LINKUSED);

        // Get all the leaveRequestsOldsList where linkused equals to UPDATED_LINKUSED
        defaultLeaveRequestsOldsShouldNotBeFound("linkused.in=" + UPDATED_LINKUSED);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByLinkusedIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where linkused is not null
        defaultLeaveRequestsOldsShouldBeFound("linkused.specified=true");

        // Get all the leaveRequestsOldsList where linkused is null
        defaultLeaveRequestsOldsShouldNotBeFound("linkused.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where createdat equals to DEFAULT_CREATEDAT
        defaultLeaveRequestsOldsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the leaveRequestsOldsList where createdat equals to UPDATED_CREATEDAT
        defaultLeaveRequestsOldsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultLeaveRequestsOldsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the leaveRequestsOldsList where createdat equals to UPDATED_CREATEDAT
        defaultLeaveRequestsOldsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where createdat is not null
        defaultLeaveRequestsOldsShouldBeFound("createdat.specified=true");

        // Get all the leaveRequestsOldsList where createdat is null
        defaultLeaveRequestsOldsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultLeaveRequestsOldsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the leaveRequestsOldsList where updatedat equals to UPDATED_UPDATEDAT
        defaultLeaveRequestsOldsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultLeaveRequestsOldsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the leaveRequestsOldsList where updatedat equals to UPDATED_UPDATEDAT
        defaultLeaveRequestsOldsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where updatedat is not null
        defaultLeaveRequestsOldsShouldBeFound("updatedat.specified=true");

        // Get all the leaveRequestsOldsList where updatedat is null
        defaultLeaveRequestsOldsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByIshalfdayIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where ishalfday equals to DEFAULT_ISHALFDAY
        defaultLeaveRequestsOldsShouldBeFound("ishalfday.equals=" + DEFAULT_ISHALFDAY);

        // Get all the leaveRequestsOldsList where ishalfday equals to UPDATED_ISHALFDAY
        defaultLeaveRequestsOldsShouldNotBeFound("ishalfday.equals=" + UPDATED_ISHALFDAY);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByIshalfdayIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where ishalfday in DEFAULT_ISHALFDAY or UPDATED_ISHALFDAY
        defaultLeaveRequestsOldsShouldBeFound("ishalfday.in=" + DEFAULT_ISHALFDAY + "," + UPDATED_ISHALFDAY);

        // Get all the leaveRequestsOldsList where ishalfday equals to UPDATED_ISHALFDAY
        defaultLeaveRequestsOldsShouldNotBeFound("ishalfday.in=" + UPDATED_ISHALFDAY);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByIshalfdayIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where ishalfday is not null
        defaultLeaveRequestsOldsShouldBeFound("ishalfday.specified=true");

        // Get all the leaveRequestsOldsList where ishalfday is null
        defaultLeaveRequestsOldsShouldNotBeFound("ishalfday.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByActiondateIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where actiondate equals to DEFAULT_ACTIONDATE
        defaultLeaveRequestsOldsShouldBeFound("actiondate.equals=" + DEFAULT_ACTIONDATE);

        // Get all the leaveRequestsOldsList where actiondate equals to UPDATED_ACTIONDATE
        defaultLeaveRequestsOldsShouldNotBeFound("actiondate.equals=" + UPDATED_ACTIONDATE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByActiondateIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where actiondate in DEFAULT_ACTIONDATE or UPDATED_ACTIONDATE
        defaultLeaveRequestsOldsShouldBeFound("actiondate.in=" + DEFAULT_ACTIONDATE + "," + UPDATED_ACTIONDATE);

        // Get all the leaveRequestsOldsList where actiondate equals to UPDATED_ACTIONDATE
        defaultLeaveRequestsOldsShouldNotBeFound("actiondate.in=" + UPDATED_ACTIONDATE);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByActiondateIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where actiondate is not null
        defaultLeaveRequestsOldsShouldBeFound("actiondate.specified=true");

        // Get all the leaveRequestsOldsList where actiondate is null
        defaultLeaveRequestsOldsShouldNotBeFound("actiondate.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByLmstatusIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where lmstatus equals to DEFAULT_LMSTATUS
        defaultLeaveRequestsOldsShouldBeFound("lmstatus.equals=" + DEFAULT_LMSTATUS);

        // Get all the leaveRequestsOldsList where lmstatus equals to UPDATED_LMSTATUS
        defaultLeaveRequestsOldsShouldNotBeFound("lmstatus.equals=" + UPDATED_LMSTATUS);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByLmstatusIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where lmstatus in DEFAULT_LMSTATUS or UPDATED_LMSTATUS
        defaultLeaveRequestsOldsShouldBeFound("lmstatus.in=" + DEFAULT_LMSTATUS + "," + UPDATED_LMSTATUS);

        // Get all the leaveRequestsOldsList where lmstatus equals to UPDATED_LMSTATUS
        defaultLeaveRequestsOldsShouldNotBeFound("lmstatus.in=" + UPDATED_LMSTATUS);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByLmstatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where lmstatus is not null
        defaultLeaveRequestsOldsShouldBeFound("lmstatus.specified=true");

        // Get all the leaveRequestsOldsList where lmstatus is null
        defaultLeaveRequestsOldsShouldNotBeFound("lmstatus.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByLmstatusContainsSomething() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where lmstatus contains DEFAULT_LMSTATUS
        defaultLeaveRequestsOldsShouldBeFound("lmstatus.contains=" + DEFAULT_LMSTATUS);

        // Get all the leaveRequestsOldsList where lmstatus contains UPDATED_LMSTATUS
        defaultLeaveRequestsOldsShouldNotBeFound("lmstatus.contains=" + UPDATED_LMSTATUS);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByLmstatusNotContainsSomething() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where lmstatus does not contain DEFAULT_LMSTATUS
        defaultLeaveRequestsOldsShouldNotBeFound("lmstatus.doesNotContain=" + DEFAULT_LMSTATUS);

        // Get all the leaveRequestsOldsList where lmstatus does not contain UPDATED_LMSTATUS
        defaultLeaveRequestsOldsShouldBeFound("lmstatus.doesNotContain=" + UPDATED_LMSTATUS);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByPmstatusIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where pmstatus equals to DEFAULT_PMSTATUS
        defaultLeaveRequestsOldsShouldBeFound("pmstatus.equals=" + DEFAULT_PMSTATUS);

        // Get all the leaveRequestsOldsList where pmstatus equals to UPDATED_PMSTATUS
        defaultLeaveRequestsOldsShouldNotBeFound("pmstatus.equals=" + UPDATED_PMSTATUS);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByPmstatusIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where pmstatus in DEFAULT_PMSTATUS or UPDATED_PMSTATUS
        defaultLeaveRequestsOldsShouldBeFound("pmstatus.in=" + DEFAULT_PMSTATUS + "," + UPDATED_PMSTATUS);

        // Get all the leaveRequestsOldsList where pmstatus equals to UPDATED_PMSTATUS
        defaultLeaveRequestsOldsShouldNotBeFound("pmstatus.in=" + UPDATED_PMSTATUS);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByPmstatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where pmstatus is not null
        defaultLeaveRequestsOldsShouldBeFound("pmstatus.specified=true");

        // Get all the leaveRequestsOldsList where pmstatus is null
        defaultLeaveRequestsOldsShouldNotBeFound("pmstatus.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByPmstatusContainsSomething() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where pmstatus contains DEFAULT_PMSTATUS
        defaultLeaveRequestsOldsShouldBeFound("pmstatus.contains=" + DEFAULT_PMSTATUS);

        // Get all the leaveRequestsOldsList where pmstatus contains UPDATED_PMSTATUS
        defaultLeaveRequestsOldsShouldNotBeFound("pmstatus.contains=" + UPDATED_PMSTATUS);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByPmstatusNotContainsSomething() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where pmstatus does not contain DEFAULT_PMSTATUS
        defaultLeaveRequestsOldsShouldNotBeFound("pmstatus.doesNotContain=" + DEFAULT_PMSTATUS);

        // Get all the leaveRequestsOldsList where pmstatus does not contain UPDATED_PMSTATUS
        defaultLeaveRequestsOldsShouldBeFound("pmstatus.doesNotContain=" + UPDATED_PMSTATUS);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByIsbenchIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where isbench equals to DEFAULT_ISBENCH
        defaultLeaveRequestsOldsShouldBeFound("isbench.equals=" + DEFAULT_ISBENCH);

        // Get all the leaveRequestsOldsList where isbench equals to UPDATED_ISBENCH
        defaultLeaveRequestsOldsShouldNotBeFound("isbench.equals=" + UPDATED_ISBENCH);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByIsbenchIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where isbench in DEFAULT_ISBENCH or UPDATED_ISBENCH
        defaultLeaveRequestsOldsShouldBeFound("isbench.in=" + DEFAULT_ISBENCH + "," + UPDATED_ISBENCH);

        // Get all the leaveRequestsOldsList where isbench equals to UPDATED_ISBENCH
        defaultLeaveRequestsOldsShouldNotBeFound("isbench.in=" + UPDATED_ISBENCH);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByIsbenchIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where isbench is not null
        defaultLeaveRequestsOldsShouldBeFound("isbench.specified=true");

        // Get all the leaveRequestsOldsList where isbench is null
        defaultLeaveRequestsOldsShouldNotBeFound("isbench.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByIsescalatedIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where isescalated equals to DEFAULT_ISESCALATED
        defaultLeaveRequestsOldsShouldBeFound("isescalated.equals=" + DEFAULT_ISESCALATED);

        // Get all the leaveRequestsOldsList where isescalated equals to UPDATED_ISESCALATED
        defaultLeaveRequestsOldsShouldNotBeFound("isescalated.equals=" + UPDATED_ISESCALATED);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByIsescalatedIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where isescalated in DEFAULT_ISESCALATED or UPDATED_ISESCALATED
        defaultLeaveRequestsOldsShouldBeFound("isescalated.in=" + DEFAULT_ISESCALATED + "," + UPDATED_ISESCALATED);

        // Get all the leaveRequestsOldsList where isescalated equals to UPDATED_ISESCALATED
        defaultLeaveRequestsOldsShouldNotBeFound("isescalated.in=" + UPDATED_ISESCALATED);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByIsescalatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where isescalated is not null
        defaultLeaveRequestsOldsShouldBeFound("isescalated.specified=true");

        // Get all the leaveRequestsOldsList where isescalated is null
        defaultLeaveRequestsOldsShouldNotBeFound("isescalated.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByIscommentedIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where iscommented equals to DEFAULT_ISCOMMENTED
        defaultLeaveRequestsOldsShouldBeFound("iscommented.equals=" + DEFAULT_ISCOMMENTED);

        // Get all the leaveRequestsOldsList where iscommented equals to UPDATED_ISCOMMENTED
        defaultLeaveRequestsOldsShouldNotBeFound("iscommented.equals=" + UPDATED_ISCOMMENTED);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByIscommentedIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where iscommented in DEFAULT_ISCOMMENTED or UPDATED_ISCOMMENTED
        defaultLeaveRequestsOldsShouldBeFound("iscommented.in=" + DEFAULT_ISCOMMENTED + "," + UPDATED_ISCOMMENTED);

        // Get all the leaveRequestsOldsList where iscommented equals to UPDATED_ISCOMMENTED
        defaultLeaveRequestsOldsShouldNotBeFound("iscommented.in=" + UPDATED_ISCOMMENTED);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByIscommentedIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where iscommented is not null
        defaultLeaveRequestsOldsShouldBeFound("iscommented.specified=true");

        // Get all the leaveRequestsOldsList where iscommented is null
        defaultLeaveRequestsOldsShouldNotBeFound("iscommented.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByIsremindedIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where isreminded equals to DEFAULT_ISREMINDED
        defaultLeaveRequestsOldsShouldBeFound("isreminded.equals=" + DEFAULT_ISREMINDED);

        // Get all the leaveRequestsOldsList where isreminded equals to UPDATED_ISREMINDED
        defaultLeaveRequestsOldsShouldNotBeFound("isreminded.equals=" + UPDATED_ISREMINDED);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByIsremindedIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where isreminded in DEFAULT_ISREMINDED or UPDATED_ISREMINDED
        defaultLeaveRequestsOldsShouldBeFound("isreminded.in=" + DEFAULT_ISREMINDED + "," + UPDATED_ISREMINDED);

        // Get all the leaveRequestsOldsList where isreminded equals to UPDATED_ISREMINDED
        defaultLeaveRequestsOldsShouldNotBeFound("isreminded.in=" + UPDATED_ISREMINDED);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByIsremindedIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where isreminded is not null
        defaultLeaveRequestsOldsShouldBeFound("isreminded.specified=true");

        // Get all the leaveRequestsOldsList where isreminded is null
        defaultLeaveRequestsOldsShouldNotBeFound("isreminded.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByIsnotifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where isnotified equals to DEFAULT_ISNOTIFIED
        defaultLeaveRequestsOldsShouldBeFound("isnotified.equals=" + DEFAULT_ISNOTIFIED);

        // Get all the leaveRequestsOldsList where isnotified equals to UPDATED_ISNOTIFIED
        defaultLeaveRequestsOldsShouldNotBeFound("isnotified.equals=" + UPDATED_ISNOTIFIED);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByIsnotifiedIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where isnotified in DEFAULT_ISNOTIFIED or UPDATED_ISNOTIFIED
        defaultLeaveRequestsOldsShouldBeFound("isnotified.in=" + DEFAULT_ISNOTIFIED + "," + UPDATED_ISNOTIFIED);

        // Get all the leaveRequestsOldsList where isnotified equals to UPDATED_ISNOTIFIED
        defaultLeaveRequestsOldsShouldNotBeFound("isnotified.in=" + UPDATED_ISNOTIFIED);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByIsnotifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where isnotified is not null
        defaultLeaveRequestsOldsShouldBeFound("isnotified.specified=true");

        // Get all the leaveRequestsOldsList where isnotified is null
        defaultLeaveRequestsOldsShouldNotBeFound("isnotified.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByIsremindedhrIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where isremindedhr equals to DEFAULT_ISREMINDEDHR
        defaultLeaveRequestsOldsShouldBeFound("isremindedhr.equals=" + DEFAULT_ISREMINDEDHR);

        // Get all the leaveRequestsOldsList where isremindedhr equals to UPDATED_ISREMINDEDHR
        defaultLeaveRequestsOldsShouldNotBeFound("isremindedhr.equals=" + UPDATED_ISREMINDEDHR);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByIsremindedhrIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where isremindedhr in DEFAULT_ISREMINDEDHR or UPDATED_ISREMINDEDHR
        defaultLeaveRequestsOldsShouldBeFound("isremindedhr.in=" + DEFAULT_ISREMINDEDHR + "," + UPDATED_ISREMINDEDHR);

        // Get all the leaveRequestsOldsList where isremindedhr equals to UPDATED_ISREMINDEDHR
        defaultLeaveRequestsOldsShouldNotBeFound("isremindedhr.in=" + UPDATED_ISREMINDEDHR);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByIsremindedhrIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where isremindedhr is not null
        defaultLeaveRequestsOldsShouldBeFound("isremindedhr.specified=true");

        // Get all the leaveRequestsOldsList where isremindedhr is null
        defaultLeaveRequestsOldsShouldNotBeFound("isremindedhr.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByIsdmIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where isdm equals to DEFAULT_ISDM
        defaultLeaveRequestsOldsShouldBeFound("isdm.equals=" + DEFAULT_ISDM);

        // Get all the leaveRequestsOldsList where isdm equals to UPDATED_ISDM
        defaultLeaveRequestsOldsShouldNotBeFound("isdm.equals=" + UPDATED_ISDM);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByIsdmIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where isdm in DEFAULT_ISDM or UPDATED_ISDM
        defaultLeaveRequestsOldsShouldBeFound("isdm.in=" + DEFAULT_ISDM + "," + UPDATED_ISDM);

        // Get all the leaveRequestsOldsList where isdm equals to UPDATED_ISDM
        defaultLeaveRequestsOldsShouldNotBeFound("isdm.in=" + UPDATED_ISDM);
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByIsdmIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        // Get all the leaveRequestsOldsList where isdm is not null
        defaultLeaveRequestsOldsShouldBeFound("isdm.specified=true");

        // Get all the leaveRequestsOldsList where isdm is null
        defaultLeaveRequestsOldsShouldNotBeFound("isdm.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByLeavetypeIsEqualToSomething() throws Exception {
        LeaveTypesOlds leavetype;
        if (TestUtil.findAll(em, LeaveTypesOlds.class).isEmpty()) {
            leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);
            leavetype = LeaveTypesOldsResourceIT.createEntity(em);
        } else {
            leavetype = TestUtil.findAll(em, LeaveTypesOlds.class).get(0);
        }
        em.persist(leavetype);
        em.flush();
        leaveRequestsOlds.setLeavetype(leavetype);
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);
        Long leavetypeId = leavetype.getId();

        // Get all the leaveRequestsOldsList where leavetype equals to leavetypeId
        defaultLeaveRequestsOldsShouldBeFound("leavetypeId.equals=" + leavetypeId);

        // Get all the leaveRequestsOldsList where leavetype equals to (leavetypeId + 1)
        defaultLeaveRequestsOldsShouldNotBeFound("leavetypeId.equals=" + (leavetypeId + 1));
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByManagerIsEqualToSomething() throws Exception {
        Employees manager;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);
            manager = EmployeesResourceIT.createEntity(em);
        } else {
            manager = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(manager);
        em.flush();
        leaveRequestsOlds.setManager(manager);
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);
        Long managerId = manager.getId();

        // Get all the leaveRequestsOldsList where manager equals to managerId
        defaultLeaveRequestsOldsShouldBeFound("managerId.equals=" + managerId);

        // Get all the leaveRequestsOldsList where manager equals to (managerId + 1)
        defaultLeaveRequestsOldsShouldNotBeFound("managerId.equals=" + (managerId + 1));
    }

    @Test
    @Transactional
    void getAllLeaveRequestsOldsByEmployeeIsEqualToSomething() throws Exception {
        Employees employee;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);
            employee = EmployeesResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employee);
        em.flush();
        leaveRequestsOlds.setEmployee(employee);
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);
        Long employeeId = employee.getId();

        // Get all the leaveRequestsOldsList where employee equals to employeeId
        defaultLeaveRequestsOldsShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the leaveRequestsOldsList where employee equals to (employeeId + 1)
        defaultLeaveRequestsOldsShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLeaveRequestsOldsShouldBeFound(String filter) throws Exception {
        restLeaveRequestsOldsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveRequestsOlds.getId().intValue())))
            .andExpect(jsonPath("$.[*].startdate").value(hasItem(DEFAULT_STARTDATE.toString())))
            .andExpect(jsonPath("$.[*].enddate").value(hasItem(DEFAULT_ENDDATE.toString())))
            .andExpect(jsonPath("$.[*].requesternote").value(hasItem(DEFAULT_REQUESTERNOTE)))
            .andExpect(jsonPath("$.[*].managernote").value(hasItem(DEFAULT_MANAGERNOTE)))
            .andExpect(jsonPath("$.[*].currentstatus").value(hasItem(DEFAULT_CURRENTSTATUS)))
            .andExpect(jsonPath("$.[*].leavescanceled").value(hasItem(DEFAULT_LEAVESCANCELED.booleanValue())))
            .andExpect(jsonPath("$.[*].requestdate").value(hasItem(DEFAULT_REQUESTDATE.toString())))
            .andExpect(jsonPath("$.[*].linkstring").value(hasItem(DEFAULT_LINKSTRING)))
            .andExpect(jsonPath("$.[*].linkused").value(hasItem(DEFAULT_LINKUSED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].ishalfday").value(hasItem(DEFAULT_ISHALFDAY.booleanValue())))
            .andExpect(jsonPath("$.[*].actiondate").value(hasItem(DEFAULT_ACTIONDATE.toString())))
            .andExpect(jsonPath("$.[*].lmstatus").value(hasItem(DEFAULT_LMSTATUS)))
            .andExpect(jsonPath("$.[*].pmstatus").value(hasItem(DEFAULT_PMSTATUS)))
            .andExpect(jsonPath("$.[*].isbench").value(hasItem(DEFAULT_ISBENCH.booleanValue())))
            .andExpect(jsonPath("$.[*].isescalated").value(hasItem(DEFAULT_ISESCALATED.booleanValue())))
            .andExpect(jsonPath("$.[*].iscommented").value(hasItem(DEFAULT_ISCOMMENTED.booleanValue())))
            .andExpect(jsonPath("$.[*].isreminded").value(hasItem(DEFAULT_ISREMINDED.booleanValue())))
            .andExpect(jsonPath("$.[*].isnotified").value(hasItem(DEFAULT_ISNOTIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].isremindedhr").value(hasItem(DEFAULT_ISREMINDEDHR.booleanValue())))
            .andExpect(jsonPath("$.[*].isdm").value(hasItem(DEFAULT_ISDM.booleanValue())));

        // Check, that the count call also returns 1
        restLeaveRequestsOldsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLeaveRequestsOldsShouldNotBeFound(String filter) throws Exception {
        restLeaveRequestsOldsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLeaveRequestsOldsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLeaveRequestsOlds() throws Exception {
        // Get the leaveRequestsOlds
        restLeaveRequestsOldsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLeaveRequestsOlds() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        int databaseSizeBeforeUpdate = leaveRequestsOldsRepository.findAll().size();

        // Update the leaveRequestsOlds
        LeaveRequestsOlds updatedLeaveRequestsOlds = leaveRequestsOldsRepository.findById(leaveRequestsOlds.getId()).get();
        // Disconnect from session so that the updates on updatedLeaveRequestsOlds are not directly saved in db
        em.detach(updatedLeaveRequestsOlds);
        updatedLeaveRequestsOlds
            .startdate(UPDATED_STARTDATE)
            .enddate(UPDATED_ENDDATE)
            .requesternote(UPDATED_REQUESTERNOTE)
            .managernote(UPDATED_MANAGERNOTE)
            .currentstatus(UPDATED_CURRENTSTATUS)
            .leavescanceled(UPDATED_LEAVESCANCELED)
            .requestdate(UPDATED_REQUESTDATE)
            .linkstring(UPDATED_LINKSTRING)
            .linkused(UPDATED_LINKUSED)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .ishalfday(UPDATED_ISHALFDAY)
            .actiondate(UPDATED_ACTIONDATE)
            .lmstatus(UPDATED_LMSTATUS)
            .pmstatus(UPDATED_PMSTATUS)
            .isbench(UPDATED_ISBENCH)
            .isescalated(UPDATED_ISESCALATED)
            .iscommented(UPDATED_ISCOMMENTED)
            .isreminded(UPDATED_ISREMINDED)
            .isnotified(UPDATED_ISNOTIFIED)
            .isremindedhr(UPDATED_ISREMINDEDHR)
            .isdm(UPDATED_ISDM);

        restLeaveRequestsOldsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLeaveRequestsOlds.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLeaveRequestsOlds))
            )
            .andExpect(status().isOk());

        // Validate the LeaveRequestsOlds in the database
        List<LeaveRequestsOlds> leaveRequestsOldsList = leaveRequestsOldsRepository.findAll();
        assertThat(leaveRequestsOldsList).hasSize(databaseSizeBeforeUpdate);
        LeaveRequestsOlds testLeaveRequestsOlds = leaveRequestsOldsList.get(leaveRequestsOldsList.size() - 1);
        assertThat(testLeaveRequestsOlds.getStartdate()).isEqualTo(UPDATED_STARTDATE);
        assertThat(testLeaveRequestsOlds.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testLeaveRequestsOlds.getRequesternote()).isEqualTo(UPDATED_REQUESTERNOTE);
        assertThat(testLeaveRequestsOlds.getManagernote()).isEqualTo(UPDATED_MANAGERNOTE);
        assertThat(testLeaveRequestsOlds.getCurrentstatus()).isEqualTo(UPDATED_CURRENTSTATUS);
        assertThat(testLeaveRequestsOlds.getLeavescanceled()).isEqualTo(UPDATED_LEAVESCANCELED);
        assertThat(testLeaveRequestsOlds.getRequestdate()).isEqualTo(UPDATED_REQUESTDATE);
        assertThat(testLeaveRequestsOlds.getLinkstring()).isEqualTo(UPDATED_LINKSTRING);
        assertThat(testLeaveRequestsOlds.getLinkused()).isEqualTo(UPDATED_LINKUSED);
        assertThat(testLeaveRequestsOlds.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testLeaveRequestsOlds.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testLeaveRequestsOlds.getIshalfday()).isEqualTo(UPDATED_ISHALFDAY);
        assertThat(testLeaveRequestsOlds.getActiondate()).isEqualTo(UPDATED_ACTIONDATE);
        assertThat(testLeaveRequestsOlds.getLmstatus()).isEqualTo(UPDATED_LMSTATUS);
        assertThat(testLeaveRequestsOlds.getPmstatus()).isEqualTo(UPDATED_PMSTATUS);
        assertThat(testLeaveRequestsOlds.getIsbench()).isEqualTo(UPDATED_ISBENCH);
        assertThat(testLeaveRequestsOlds.getIsescalated()).isEqualTo(UPDATED_ISESCALATED);
        assertThat(testLeaveRequestsOlds.getIscommented()).isEqualTo(UPDATED_ISCOMMENTED);
        assertThat(testLeaveRequestsOlds.getIsreminded()).isEqualTo(UPDATED_ISREMINDED);
        assertThat(testLeaveRequestsOlds.getIsnotified()).isEqualTo(UPDATED_ISNOTIFIED);
        assertThat(testLeaveRequestsOlds.getIsremindedhr()).isEqualTo(UPDATED_ISREMINDEDHR);
        assertThat(testLeaveRequestsOlds.getIsdm()).isEqualTo(UPDATED_ISDM);
    }

    @Test
    @Transactional
    void putNonExistingLeaveRequestsOlds() throws Exception {
        int databaseSizeBeforeUpdate = leaveRequestsOldsRepository.findAll().size();
        leaveRequestsOlds.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveRequestsOldsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leaveRequestsOlds.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequestsOlds))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveRequestsOlds in the database
        List<LeaveRequestsOlds> leaveRequestsOldsList = leaveRequestsOldsRepository.findAll();
        assertThat(leaveRequestsOldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeaveRequestsOlds() throws Exception {
        int databaseSizeBeforeUpdate = leaveRequestsOldsRepository.findAll().size();
        leaveRequestsOlds.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveRequestsOldsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequestsOlds))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveRequestsOlds in the database
        List<LeaveRequestsOlds> leaveRequestsOldsList = leaveRequestsOldsRepository.findAll();
        assertThat(leaveRequestsOldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeaveRequestsOlds() throws Exception {
        int databaseSizeBeforeUpdate = leaveRequestsOldsRepository.findAll().size();
        leaveRequestsOlds.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveRequestsOldsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequestsOlds))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveRequestsOlds in the database
        List<LeaveRequestsOlds> leaveRequestsOldsList = leaveRequestsOldsRepository.findAll();
        assertThat(leaveRequestsOldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeaveRequestsOldsWithPatch() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        int databaseSizeBeforeUpdate = leaveRequestsOldsRepository.findAll().size();

        // Update the leaveRequestsOlds using partial update
        LeaveRequestsOlds partialUpdatedLeaveRequestsOlds = new LeaveRequestsOlds();
        partialUpdatedLeaveRequestsOlds.setId(leaveRequestsOlds.getId());

        partialUpdatedLeaveRequestsOlds
            .startdate(UPDATED_STARTDATE)
            .requesternote(UPDATED_REQUESTERNOTE)
            .currentstatus(UPDATED_CURRENTSTATUS)
            .requestdate(UPDATED_REQUESTDATE)
            .ishalfday(UPDATED_ISHALFDAY)
            .actiondate(UPDATED_ACTIONDATE)
            .lmstatus(UPDATED_LMSTATUS)
            .pmstatus(UPDATED_PMSTATUS)
            .isbench(UPDATED_ISBENCH)
            .isescalated(UPDATED_ISESCALATED)
            .isremindedhr(UPDATED_ISREMINDEDHR);

        restLeaveRequestsOldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveRequestsOlds.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveRequestsOlds))
            )
            .andExpect(status().isOk());

        // Validate the LeaveRequestsOlds in the database
        List<LeaveRequestsOlds> leaveRequestsOldsList = leaveRequestsOldsRepository.findAll();
        assertThat(leaveRequestsOldsList).hasSize(databaseSizeBeforeUpdate);
        LeaveRequestsOlds testLeaveRequestsOlds = leaveRequestsOldsList.get(leaveRequestsOldsList.size() - 1);
        assertThat(testLeaveRequestsOlds.getStartdate()).isEqualTo(UPDATED_STARTDATE);
        assertThat(testLeaveRequestsOlds.getEnddate()).isEqualTo(DEFAULT_ENDDATE);
        assertThat(testLeaveRequestsOlds.getRequesternote()).isEqualTo(UPDATED_REQUESTERNOTE);
        assertThat(testLeaveRequestsOlds.getManagernote()).isEqualTo(DEFAULT_MANAGERNOTE);
        assertThat(testLeaveRequestsOlds.getCurrentstatus()).isEqualTo(UPDATED_CURRENTSTATUS);
        assertThat(testLeaveRequestsOlds.getLeavescanceled()).isEqualTo(DEFAULT_LEAVESCANCELED);
        assertThat(testLeaveRequestsOlds.getRequestdate()).isEqualTo(UPDATED_REQUESTDATE);
        assertThat(testLeaveRequestsOlds.getLinkstring()).isEqualTo(DEFAULT_LINKSTRING);
        assertThat(testLeaveRequestsOlds.getLinkused()).isEqualTo(DEFAULT_LINKUSED);
        assertThat(testLeaveRequestsOlds.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testLeaveRequestsOlds.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testLeaveRequestsOlds.getIshalfday()).isEqualTo(UPDATED_ISHALFDAY);
        assertThat(testLeaveRequestsOlds.getActiondate()).isEqualTo(UPDATED_ACTIONDATE);
        assertThat(testLeaveRequestsOlds.getLmstatus()).isEqualTo(UPDATED_LMSTATUS);
        assertThat(testLeaveRequestsOlds.getPmstatus()).isEqualTo(UPDATED_PMSTATUS);
        assertThat(testLeaveRequestsOlds.getIsbench()).isEqualTo(UPDATED_ISBENCH);
        assertThat(testLeaveRequestsOlds.getIsescalated()).isEqualTo(UPDATED_ISESCALATED);
        assertThat(testLeaveRequestsOlds.getIscommented()).isEqualTo(DEFAULT_ISCOMMENTED);
        assertThat(testLeaveRequestsOlds.getIsreminded()).isEqualTo(DEFAULT_ISREMINDED);
        assertThat(testLeaveRequestsOlds.getIsnotified()).isEqualTo(DEFAULT_ISNOTIFIED);
        assertThat(testLeaveRequestsOlds.getIsremindedhr()).isEqualTo(UPDATED_ISREMINDEDHR);
        assertThat(testLeaveRequestsOlds.getIsdm()).isEqualTo(DEFAULT_ISDM);
    }

    @Test
    @Transactional
    void fullUpdateLeaveRequestsOldsWithPatch() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        int databaseSizeBeforeUpdate = leaveRequestsOldsRepository.findAll().size();

        // Update the leaveRequestsOlds using partial update
        LeaveRequestsOlds partialUpdatedLeaveRequestsOlds = new LeaveRequestsOlds();
        partialUpdatedLeaveRequestsOlds.setId(leaveRequestsOlds.getId());

        partialUpdatedLeaveRequestsOlds
            .startdate(UPDATED_STARTDATE)
            .enddate(UPDATED_ENDDATE)
            .requesternote(UPDATED_REQUESTERNOTE)
            .managernote(UPDATED_MANAGERNOTE)
            .currentstatus(UPDATED_CURRENTSTATUS)
            .leavescanceled(UPDATED_LEAVESCANCELED)
            .requestdate(UPDATED_REQUESTDATE)
            .linkstring(UPDATED_LINKSTRING)
            .linkused(UPDATED_LINKUSED)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .ishalfday(UPDATED_ISHALFDAY)
            .actiondate(UPDATED_ACTIONDATE)
            .lmstatus(UPDATED_LMSTATUS)
            .pmstatus(UPDATED_PMSTATUS)
            .isbench(UPDATED_ISBENCH)
            .isescalated(UPDATED_ISESCALATED)
            .iscommented(UPDATED_ISCOMMENTED)
            .isreminded(UPDATED_ISREMINDED)
            .isnotified(UPDATED_ISNOTIFIED)
            .isremindedhr(UPDATED_ISREMINDEDHR)
            .isdm(UPDATED_ISDM);

        restLeaveRequestsOldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveRequestsOlds.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveRequestsOlds))
            )
            .andExpect(status().isOk());

        // Validate the LeaveRequestsOlds in the database
        List<LeaveRequestsOlds> leaveRequestsOldsList = leaveRequestsOldsRepository.findAll();
        assertThat(leaveRequestsOldsList).hasSize(databaseSizeBeforeUpdate);
        LeaveRequestsOlds testLeaveRequestsOlds = leaveRequestsOldsList.get(leaveRequestsOldsList.size() - 1);
        assertThat(testLeaveRequestsOlds.getStartdate()).isEqualTo(UPDATED_STARTDATE);
        assertThat(testLeaveRequestsOlds.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testLeaveRequestsOlds.getRequesternote()).isEqualTo(UPDATED_REQUESTERNOTE);
        assertThat(testLeaveRequestsOlds.getManagernote()).isEqualTo(UPDATED_MANAGERNOTE);
        assertThat(testLeaveRequestsOlds.getCurrentstatus()).isEqualTo(UPDATED_CURRENTSTATUS);
        assertThat(testLeaveRequestsOlds.getLeavescanceled()).isEqualTo(UPDATED_LEAVESCANCELED);
        assertThat(testLeaveRequestsOlds.getRequestdate()).isEqualTo(UPDATED_REQUESTDATE);
        assertThat(testLeaveRequestsOlds.getLinkstring()).isEqualTo(UPDATED_LINKSTRING);
        assertThat(testLeaveRequestsOlds.getLinkused()).isEqualTo(UPDATED_LINKUSED);
        assertThat(testLeaveRequestsOlds.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testLeaveRequestsOlds.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testLeaveRequestsOlds.getIshalfday()).isEqualTo(UPDATED_ISHALFDAY);
        assertThat(testLeaveRequestsOlds.getActiondate()).isEqualTo(UPDATED_ACTIONDATE);
        assertThat(testLeaveRequestsOlds.getLmstatus()).isEqualTo(UPDATED_LMSTATUS);
        assertThat(testLeaveRequestsOlds.getPmstatus()).isEqualTo(UPDATED_PMSTATUS);
        assertThat(testLeaveRequestsOlds.getIsbench()).isEqualTo(UPDATED_ISBENCH);
        assertThat(testLeaveRequestsOlds.getIsescalated()).isEqualTo(UPDATED_ISESCALATED);
        assertThat(testLeaveRequestsOlds.getIscommented()).isEqualTo(UPDATED_ISCOMMENTED);
        assertThat(testLeaveRequestsOlds.getIsreminded()).isEqualTo(UPDATED_ISREMINDED);
        assertThat(testLeaveRequestsOlds.getIsnotified()).isEqualTo(UPDATED_ISNOTIFIED);
        assertThat(testLeaveRequestsOlds.getIsremindedhr()).isEqualTo(UPDATED_ISREMINDEDHR);
        assertThat(testLeaveRequestsOlds.getIsdm()).isEqualTo(UPDATED_ISDM);
    }

    @Test
    @Transactional
    void patchNonExistingLeaveRequestsOlds() throws Exception {
        int databaseSizeBeforeUpdate = leaveRequestsOldsRepository.findAll().size();
        leaveRequestsOlds.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveRequestsOldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leaveRequestsOlds.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequestsOlds))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveRequestsOlds in the database
        List<LeaveRequestsOlds> leaveRequestsOldsList = leaveRequestsOldsRepository.findAll();
        assertThat(leaveRequestsOldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeaveRequestsOlds() throws Exception {
        int databaseSizeBeforeUpdate = leaveRequestsOldsRepository.findAll().size();
        leaveRequestsOlds.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveRequestsOldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequestsOlds))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveRequestsOlds in the database
        List<LeaveRequestsOlds> leaveRequestsOldsList = leaveRequestsOldsRepository.findAll();
        assertThat(leaveRequestsOldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeaveRequestsOlds() throws Exception {
        int databaseSizeBeforeUpdate = leaveRequestsOldsRepository.findAll().size();
        leaveRequestsOlds.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveRequestsOldsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveRequestsOlds))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveRequestsOlds in the database
        List<LeaveRequestsOlds> leaveRequestsOldsList = leaveRequestsOldsRepository.findAll();
        assertThat(leaveRequestsOldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLeaveRequestsOlds() throws Exception {
        // Initialize the database
        leaveRequestsOldsRepository.saveAndFlush(leaveRequestsOlds);

        int databaseSizeBeforeDelete = leaveRequestsOldsRepository.findAll().size();

        // Delete the leaveRequestsOlds
        restLeaveRequestsOldsMockMvc
            .perform(delete(ENTITY_API_URL_ID, leaveRequestsOlds.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeaveRequestsOlds> leaveRequestsOldsList = leaveRequestsOldsRepository.findAll();
        assertThat(leaveRequestsOldsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
