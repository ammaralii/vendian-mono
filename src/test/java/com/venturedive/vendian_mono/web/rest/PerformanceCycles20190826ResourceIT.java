package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.PerformanceCycles20190826;
import com.venturedive.vendian_mono.repository.PerformanceCycles20190826Repository;
import com.venturedive.vendian_mono.service.criteria.PerformanceCycles20190826Criteria;
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
 * Integration tests for the {@link PerformanceCycles20190826Resource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PerformanceCycles20190826ResourceIT {

    private static final Boolean DEFAULT_MONTH = false;
    private static final Boolean UPDATED_MONTH = true;

    private static final Boolean DEFAULT_YEAR = false;
    private static final Boolean UPDATED_YEAR = true;

    private static final Boolean DEFAULT_TOTALRESOURCES = false;
    private static final Boolean UPDATED_TOTALRESOURCES = true;

    private static final Boolean DEFAULT_PMREVIEWED = false;
    private static final Boolean UPDATED_PMREVIEWED = true;

    private static final Boolean DEFAULT_ARCHREVIEWED = false;
    private static final Boolean UPDATED_ARCHREVIEWED = true;

    private static final Instant DEFAULT_STARTDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STARTDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ENDDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ENDDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ISACTIVE = false;
    private static final Boolean UPDATED_ISACTIVE = true;

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_PROJECTCOUNT = false;
    private static final Boolean UPDATED_PROJECTCOUNT = true;

    private static final Integer DEFAULT_CRITERIA = 1;
    private static final Integer UPDATED_CRITERIA = 2;
    private static final Integer SMALLER_CRITERIA = 1 - 1;

    private static final Boolean DEFAULT_NOTIFICATIONSENT = false;
    private static final Boolean UPDATED_NOTIFICATIONSENT = true;

    private static final Instant DEFAULT_DUEDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DUEDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_INITIATIONDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INITIATIONDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/performance-cycles-20190826-s";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PerformanceCycles20190826Repository performanceCycles20190826Repository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPerformanceCycles20190826MockMvc;

    private PerformanceCycles20190826 performanceCycles20190826;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PerformanceCycles20190826 createEntity(EntityManager em) {
        PerformanceCycles20190826 performanceCycles20190826 = new PerformanceCycles20190826()
            .month(DEFAULT_MONTH)
            .year(DEFAULT_YEAR)
            .totalresources(DEFAULT_TOTALRESOURCES)
            .pmreviewed(DEFAULT_PMREVIEWED)
            .archreviewed(DEFAULT_ARCHREVIEWED)
            .startdate(DEFAULT_STARTDATE)
            .enddate(DEFAULT_ENDDATE)
            .isactive(DEFAULT_ISACTIVE)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT)
            .projectcount(DEFAULT_PROJECTCOUNT)
            .criteria(DEFAULT_CRITERIA)
            .notificationsent(DEFAULT_NOTIFICATIONSENT)
            .duedate(DEFAULT_DUEDATE)
            .initiationdate(DEFAULT_INITIATIONDATE);
        return performanceCycles20190826;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PerformanceCycles20190826 createUpdatedEntity(EntityManager em) {
        PerformanceCycles20190826 performanceCycles20190826 = new PerformanceCycles20190826()
            .month(UPDATED_MONTH)
            .year(UPDATED_YEAR)
            .totalresources(UPDATED_TOTALRESOURCES)
            .pmreviewed(UPDATED_PMREVIEWED)
            .archreviewed(UPDATED_ARCHREVIEWED)
            .startdate(UPDATED_STARTDATE)
            .enddate(UPDATED_ENDDATE)
            .isactive(UPDATED_ISACTIVE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .projectcount(UPDATED_PROJECTCOUNT)
            .criteria(UPDATED_CRITERIA)
            .notificationsent(UPDATED_NOTIFICATIONSENT)
            .duedate(UPDATED_DUEDATE)
            .initiationdate(UPDATED_INITIATIONDATE);
        return performanceCycles20190826;
    }

    @BeforeEach
    public void initTest() {
        performanceCycles20190826 = createEntity(em);
    }

    @Test
    @Transactional
    void createPerformanceCycles20190826() throws Exception {
        int databaseSizeBeforeCreate = performanceCycles20190826Repository.findAll().size();
        // Create the PerformanceCycles20190826
        restPerformanceCycles20190826MockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(performanceCycles20190826))
            )
            .andExpect(status().isCreated());

        // Validate the PerformanceCycles20190826 in the database
        List<PerformanceCycles20190826> performanceCycles20190826List = performanceCycles20190826Repository.findAll();
        assertThat(performanceCycles20190826List).hasSize(databaseSizeBeforeCreate + 1);
        PerformanceCycles20190826 testPerformanceCycles20190826 = performanceCycles20190826List.get(
            performanceCycles20190826List.size() - 1
        );
        assertThat(testPerformanceCycles20190826.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testPerformanceCycles20190826.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testPerformanceCycles20190826.getTotalresources()).isEqualTo(DEFAULT_TOTALRESOURCES);
        assertThat(testPerformanceCycles20190826.getPmreviewed()).isEqualTo(DEFAULT_PMREVIEWED);
        assertThat(testPerformanceCycles20190826.getArchreviewed()).isEqualTo(DEFAULT_ARCHREVIEWED);
        assertThat(testPerformanceCycles20190826.getStartdate()).isEqualTo(DEFAULT_STARTDATE);
        assertThat(testPerformanceCycles20190826.getEnddate()).isEqualTo(DEFAULT_ENDDATE);
        assertThat(testPerformanceCycles20190826.getIsactive()).isEqualTo(DEFAULT_ISACTIVE);
        assertThat(testPerformanceCycles20190826.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testPerformanceCycles20190826.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testPerformanceCycles20190826.getProjectcount()).isEqualTo(DEFAULT_PROJECTCOUNT);
        assertThat(testPerformanceCycles20190826.getCriteria()).isEqualTo(DEFAULT_CRITERIA);
        assertThat(testPerformanceCycles20190826.getNotificationsent()).isEqualTo(DEFAULT_NOTIFICATIONSENT);
        assertThat(testPerformanceCycles20190826.getDuedate()).isEqualTo(DEFAULT_DUEDATE);
        assertThat(testPerformanceCycles20190826.getInitiationdate()).isEqualTo(DEFAULT_INITIATIONDATE);
    }

    @Test
    @Transactional
    void createPerformanceCycles20190826WithExistingId() throws Exception {
        // Create the PerformanceCycles20190826 with an existing ID
        performanceCycles20190826.setId(1L);

        int databaseSizeBeforeCreate = performanceCycles20190826Repository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPerformanceCycles20190826MockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(performanceCycles20190826))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerformanceCycles20190826 in the database
        List<PerformanceCycles20190826> performanceCycles20190826List = performanceCycles20190826Repository.findAll();
        assertThat(performanceCycles20190826List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826s() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List
        restPerformanceCycles20190826MockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(performanceCycles20190826.getId().intValue())))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.booleanValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR.booleanValue())))
            .andExpect(jsonPath("$.[*].totalresources").value(hasItem(DEFAULT_TOTALRESOURCES.booleanValue())))
            .andExpect(jsonPath("$.[*].pmreviewed").value(hasItem(DEFAULT_PMREVIEWED.booleanValue())))
            .andExpect(jsonPath("$.[*].archreviewed").value(hasItem(DEFAULT_ARCHREVIEWED.booleanValue())))
            .andExpect(jsonPath("$.[*].startdate").value(hasItem(DEFAULT_STARTDATE.toString())))
            .andExpect(jsonPath("$.[*].enddate").value(hasItem(DEFAULT_ENDDATE.toString())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].projectcount").value(hasItem(DEFAULT_PROJECTCOUNT.booleanValue())))
            .andExpect(jsonPath("$.[*].criteria").value(hasItem(DEFAULT_CRITERIA)))
            .andExpect(jsonPath("$.[*].notificationsent").value(hasItem(DEFAULT_NOTIFICATIONSENT.booleanValue())))
            .andExpect(jsonPath("$.[*].duedate").value(hasItem(DEFAULT_DUEDATE.toString())))
            .andExpect(jsonPath("$.[*].initiationdate").value(hasItem(DEFAULT_INITIATIONDATE.toString())));
    }

    @Test
    @Transactional
    void getPerformanceCycles20190826() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get the performanceCycles20190826
        restPerformanceCycles20190826MockMvc
            .perform(get(ENTITY_API_URL_ID, performanceCycles20190826.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(performanceCycles20190826.getId().intValue()))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH.booleanValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR.booleanValue()))
            .andExpect(jsonPath("$.totalresources").value(DEFAULT_TOTALRESOURCES.booleanValue()))
            .andExpect(jsonPath("$.pmreviewed").value(DEFAULT_PMREVIEWED.booleanValue()))
            .andExpect(jsonPath("$.archreviewed").value(DEFAULT_ARCHREVIEWED.booleanValue()))
            .andExpect(jsonPath("$.startdate").value(DEFAULT_STARTDATE.toString()))
            .andExpect(jsonPath("$.enddate").value(DEFAULT_ENDDATE.toString()))
            .andExpect(jsonPath("$.isactive").value(DEFAULT_ISACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()))
            .andExpect(jsonPath("$.projectcount").value(DEFAULT_PROJECTCOUNT.booleanValue()))
            .andExpect(jsonPath("$.criteria").value(DEFAULT_CRITERIA))
            .andExpect(jsonPath("$.notificationsent").value(DEFAULT_NOTIFICATIONSENT.booleanValue()))
            .andExpect(jsonPath("$.duedate").value(DEFAULT_DUEDATE.toString()))
            .andExpect(jsonPath("$.initiationdate").value(DEFAULT_INITIATIONDATE.toString()));
    }

    @Test
    @Transactional
    void getPerformanceCycles20190826sByIdFiltering() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        Long id = performanceCycles20190826.getId();

        defaultPerformanceCycles20190826ShouldBeFound("id.equals=" + id);
        defaultPerformanceCycles20190826ShouldNotBeFound("id.notEquals=" + id);

        defaultPerformanceCycles20190826ShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPerformanceCycles20190826ShouldNotBeFound("id.greaterThan=" + id);

        defaultPerformanceCycles20190826ShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPerformanceCycles20190826ShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByMonthIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where month equals to DEFAULT_MONTH
        defaultPerformanceCycles20190826ShouldBeFound("month.equals=" + DEFAULT_MONTH);

        // Get all the performanceCycles20190826List where month equals to UPDATED_MONTH
        defaultPerformanceCycles20190826ShouldNotBeFound("month.equals=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByMonthIsInShouldWork() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where month in DEFAULT_MONTH or UPDATED_MONTH
        defaultPerformanceCycles20190826ShouldBeFound("month.in=" + DEFAULT_MONTH + "," + UPDATED_MONTH);

        // Get all the performanceCycles20190826List where month equals to UPDATED_MONTH
        defaultPerformanceCycles20190826ShouldNotBeFound("month.in=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByMonthIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where month is not null
        defaultPerformanceCycles20190826ShouldBeFound("month.specified=true");

        // Get all the performanceCycles20190826List where month is null
        defaultPerformanceCycles20190826ShouldNotBeFound("month.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where year equals to DEFAULT_YEAR
        defaultPerformanceCycles20190826ShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the performanceCycles20190826List where year equals to UPDATED_YEAR
        defaultPerformanceCycles20190826ShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByYearIsInShouldWork() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultPerformanceCycles20190826ShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the performanceCycles20190826List where year equals to UPDATED_YEAR
        defaultPerformanceCycles20190826ShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where year is not null
        defaultPerformanceCycles20190826ShouldBeFound("year.specified=true");

        // Get all the performanceCycles20190826List where year is null
        defaultPerformanceCycles20190826ShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByTotalresourcesIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where totalresources equals to DEFAULT_TOTALRESOURCES
        defaultPerformanceCycles20190826ShouldBeFound("totalresources.equals=" + DEFAULT_TOTALRESOURCES);

        // Get all the performanceCycles20190826List where totalresources equals to UPDATED_TOTALRESOURCES
        defaultPerformanceCycles20190826ShouldNotBeFound("totalresources.equals=" + UPDATED_TOTALRESOURCES);
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByTotalresourcesIsInShouldWork() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where totalresources in DEFAULT_TOTALRESOURCES or UPDATED_TOTALRESOURCES
        defaultPerformanceCycles20190826ShouldBeFound("totalresources.in=" + DEFAULT_TOTALRESOURCES + "," + UPDATED_TOTALRESOURCES);

        // Get all the performanceCycles20190826List where totalresources equals to UPDATED_TOTALRESOURCES
        defaultPerformanceCycles20190826ShouldNotBeFound("totalresources.in=" + UPDATED_TOTALRESOURCES);
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByTotalresourcesIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where totalresources is not null
        defaultPerformanceCycles20190826ShouldBeFound("totalresources.specified=true");

        // Get all the performanceCycles20190826List where totalresources is null
        defaultPerformanceCycles20190826ShouldNotBeFound("totalresources.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByPmreviewedIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where pmreviewed equals to DEFAULT_PMREVIEWED
        defaultPerformanceCycles20190826ShouldBeFound("pmreviewed.equals=" + DEFAULT_PMREVIEWED);

        // Get all the performanceCycles20190826List where pmreviewed equals to UPDATED_PMREVIEWED
        defaultPerformanceCycles20190826ShouldNotBeFound("pmreviewed.equals=" + UPDATED_PMREVIEWED);
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByPmreviewedIsInShouldWork() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where pmreviewed in DEFAULT_PMREVIEWED or UPDATED_PMREVIEWED
        defaultPerformanceCycles20190826ShouldBeFound("pmreviewed.in=" + DEFAULT_PMREVIEWED + "," + UPDATED_PMREVIEWED);

        // Get all the performanceCycles20190826List where pmreviewed equals to UPDATED_PMREVIEWED
        defaultPerformanceCycles20190826ShouldNotBeFound("pmreviewed.in=" + UPDATED_PMREVIEWED);
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByPmreviewedIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where pmreviewed is not null
        defaultPerformanceCycles20190826ShouldBeFound("pmreviewed.specified=true");

        // Get all the performanceCycles20190826List where pmreviewed is null
        defaultPerformanceCycles20190826ShouldNotBeFound("pmreviewed.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByArchreviewedIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where archreviewed equals to DEFAULT_ARCHREVIEWED
        defaultPerformanceCycles20190826ShouldBeFound("archreviewed.equals=" + DEFAULT_ARCHREVIEWED);

        // Get all the performanceCycles20190826List where archreviewed equals to UPDATED_ARCHREVIEWED
        defaultPerformanceCycles20190826ShouldNotBeFound("archreviewed.equals=" + UPDATED_ARCHREVIEWED);
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByArchreviewedIsInShouldWork() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where archreviewed in DEFAULT_ARCHREVIEWED or UPDATED_ARCHREVIEWED
        defaultPerformanceCycles20190826ShouldBeFound("archreviewed.in=" + DEFAULT_ARCHREVIEWED + "," + UPDATED_ARCHREVIEWED);

        // Get all the performanceCycles20190826List where archreviewed equals to UPDATED_ARCHREVIEWED
        defaultPerformanceCycles20190826ShouldNotBeFound("archreviewed.in=" + UPDATED_ARCHREVIEWED);
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByArchreviewedIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where archreviewed is not null
        defaultPerformanceCycles20190826ShouldBeFound("archreviewed.specified=true");

        // Get all the performanceCycles20190826List where archreviewed is null
        defaultPerformanceCycles20190826ShouldNotBeFound("archreviewed.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByStartdateIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where startdate equals to DEFAULT_STARTDATE
        defaultPerformanceCycles20190826ShouldBeFound("startdate.equals=" + DEFAULT_STARTDATE);

        // Get all the performanceCycles20190826List where startdate equals to UPDATED_STARTDATE
        defaultPerformanceCycles20190826ShouldNotBeFound("startdate.equals=" + UPDATED_STARTDATE);
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByStartdateIsInShouldWork() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where startdate in DEFAULT_STARTDATE or UPDATED_STARTDATE
        defaultPerformanceCycles20190826ShouldBeFound("startdate.in=" + DEFAULT_STARTDATE + "," + UPDATED_STARTDATE);

        // Get all the performanceCycles20190826List where startdate equals to UPDATED_STARTDATE
        defaultPerformanceCycles20190826ShouldNotBeFound("startdate.in=" + UPDATED_STARTDATE);
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByStartdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where startdate is not null
        defaultPerformanceCycles20190826ShouldBeFound("startdate.specified=true");

        // Get all the performanceCycles20190826List where startdate is null
        defaultPerformanceCycles20190826ShouldNotBeFound("startdate.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByEnddateIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where enddate equals to DEFAULT_ENDDATE
        defaultPerformanceCycles20190826ShouldBeFound("enddate.equals=" + DEFAULT_ENDDATE);

        // Get all the performanceCycles20190826List where enddate equals to UPDATED_ENDDATE
        defaultPerformanceCycles20190826ShouldNotBeFound("enddate.equals=" + UPDATED_ENDDATE);
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByEnddateIsInShouldWork() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where enddate in DEFAULT_ENDDATE or UPDATED_ENDDATE
        defaultPerformanceCycles20190826ShouldBeFound("enddate.in=" + DEFAULT_ENDDATE + "," + UPDATED_ENDDATE);

        // Get all the performanceCycles20190826List where enddate equals to UPDATED_ENDDATE
        defaultPerformanceCycles20190826ShouldNotBeFound("enddate.in=" + UPDATED_ENDDATE);
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByEnddateIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where enddate is not null
        defaultPerformanceCycles20190826ShouldBeFound("enddate.specified=true");

        // Get all the performanceCycles20190826List where enddate is null
        defaultPerformanceCycles20190826ShouldNotBeFound("enddate.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByIsactiveIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where isactive equals to DEFAULT_ISACTIVE
        defaultPerformanceCycles20190826ShouldBeFound("isactive.equals=" + DEFAULT_ISACTIVE);

        // Get all the performanceCycles20190826List where isactive equals to UPDATED_ISACTIVE
        defaultPerformanceCycles20190826ShouldNotBeFound("isactive.equals=" + UPDATED_ISACTIVE);
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByIsactiveIsInShouldWork() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where isactive in DEFAULT_ISACTIVE or UPDATED_ISACTIVE
        defaultPerformanceCycles20190826ShouldBeFound("isactive.in=" + DEFAULT_ISACTIVE + "," + UPDATED_ISACTIVE);

        // Get all the performanceCycles20190826List where isactive equals to UPDATED_ISACTIVE
        defaultPerformanceCycles20190826ShouldNotBeFound("isactive.in=" + UPDATED_ISACTIVE);
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByIsactiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where isactive is not null
        defaultPerformanceCycles20190826ShouldBeFound("isactive.specified=true");

        // Get all the performanceCycles20190826List where isactive is null
        defaultPerformanceCycles20190826ShouldNotBeFound("isactive.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where createdat equals to DEFAULT_CREATEDAT
        defaultPerformanceCycles20190826ShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the performanceCycles20190826List where createdat equals to UPDATED_CREATEDAT
        defaultPerformanceCycles20190826ShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultPerformanceCycles20190826ShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the performanceCycles20190826List where createdat equals to UPDATED_CREATEDAT
        defaultPerformanceCycles20190826ShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where createdat is not null
        defaultPerformanceCycles20190826ShouldBeFound("createdat.specified=true");

        // Get all the performanceCycles20190826List where createdat is null
        defaultPerformanceCycles20190826ShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where updatedat equals to DEFAULT_UPDATEDAT
        defaultPerformanceCycles20190826ShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the performanceCycles20190826List where updatedat equals to UPDATED_UPDATEDAT
        defaultPerformanceCycles20190826ShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultPerformanceCycles20190826ShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the performanceCycles20190826List where updatedat equals to UPDATED_UPDATEDAT
        defaultPerformanceCycles20190826ShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where updatedat is not null
        defaultPerformanceCycles20190826ShouldBeFound("updatedat.specified=true");

        // Get all the performanceCycles20190826List where updatedat is null
        defaultPerformanceCycles20190826ShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByProjectcountIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where projectcount equals to DEFAULT_PROJECTCOUNT
        defaultPerformanceCycles20190826ShouldBeFound("projectcount.equals=" + DEFAULT_PROJECTCOUNT);

        // Get all the performanceCycles20190826List where projectcount equals to UPDATED_PROJECTCOUNT
        defaultPerformanceCycles20190826ShouldNotBeFound("projectcount.equals=" + UPDATED_PROJECTCOUNT);
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByProjectcountIsInShouldWork() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where projectcount in DEFAULT_PROJECTCOUNT or UPDATED_PROJECTCOUNT
        defaultPerformanceCycles20190826ShouldBeFound("projectcount.in=" + DEFAULT_PROJECTCOUNT + "," + UPDATED_PROJECTCOUNT);

        // Get all the performanceCycles20190826List where projectcount equals to UPDATED_PROJECTCOUNT
        defaultPerformanceCycles20190826ShouldNotBeFound("projectcount.in=" + UPDATED_PROJECTCOUNT);
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByProjectcountIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where projectcount is not null
        defaultPerformanceCycles20190826ShouldBeFound("projectcount.specified=true");

        // Get all the performanceCycles20190826List where projectcount is null
        defaultPerformanceCycles20190826ShouldNotBeFound("projectcount.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByCriteriaIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where criteria equals to DEFAULT_CRITERIA
        defaultPerformanceCycles20190826ShouldBeFound("criteria.equals=" + DEFAULT_CRITERIA);

        // Get all the performanceCycles20190826List where criteria equals to UPDATED_CRITERIA
        defaultPerformanceCycles20190826ShouldNotBeFound("criteria.equals=" + UPDATED_CRITERIA);
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByCriteriaIsInShouldWork() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where criteria in DEFAULT_CRITERIA or UPDATED_CRITERIA
        defaultPerformanceCycles20190826ShouldBeFound("criteria.in=" + DEFAULT_CRITERIA + "," + UPDATED_CRITERIA);

        // Get all the performanceCycles20190826List where criteria equals to UPDATED_CRITERIA
        defaultPerformanceCycles20190826ShouldNotBeFound("criteria.in=" + UPDATED_CRITERIA);
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByCriteriaIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where criteria is not null
        defaultPerformanceCycles20190826ShouldBeFound("criteria.specified=true");

        // Get all the performanceCycles20190826List where criteria is null
        defaultPerformanceCycles20190826ShouldNotBeFound("criteria.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByCriteriaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where criteria is greater than or equal to DEFAULT_CRITERIA
        defaultPerformanceCycles20190826ShouldBeFound("criteria.greaterThanOrEqual=" + DEFAULT_CRITERIA);

        // Get all the performanceCycles20190826List where criteria is greater than or equal to UPDATED_CRITERIA
        defaultPerformanceCycles20190826ShouldNotBeFound("criteria.greaterThanOrEqual=" + UPDATED_CRITERIA);
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByCriteriaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where criteria is less than or equal to DEFAULT_CRITERIA
        defaultPerformanceCycles20190826ShouldBeFound("criteria.lessThanOrEqual=" + DEFAULT_CRITERIA);

        // Get all the performanceCycles20190826List where criteria is less than or equal to SMALLER_CRITERIA
        defaultPerformanceCycles20190826ShouldNotBeFound("criteria.lessThanOrEqual=" + SMALLER_CRITERIA);
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByCriteriaIsLessThanSomething() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where criteria is less than DEFAULT_CRITERIA
        defaultPerformanceCycles20190826ShouldNotBeFound("criteria.lessThan=" + DEFAULT_CRITERIA);

        // Get all the performanceCycles20190826List where criteria is less than UPDATED_CRITERIA
        defaultPerformanceCycles20190826ShouldBeFound("criteria.lessThan=" + UPDATED_CRITERIA);
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByCriteriaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where criteria is greater than DEFAULT_CRITERIA
        defaultPerformanceCycles20190826ShouldNotBeFound("criteria.greaterThan=" + DEFAULT_CRITERIA);

        // Get all the performanceCycles20190826List where criteria is greater than SMALLER_CRITERIA
        defaultPerformanceCycles20190826ShouldBeFound("criteria.greaterThan=" + SMALLER_CRITERIA);
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByNotificationsentIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where notificationsent equals to DEFAULT_NOTIFICATIONSENT
        defaultPerformanceCycles20190826ShouldBeFound("notificationsent.equals=" + DEFAULT_NOTIFICATIONSENT);

        // Get all the performanceCycles20190826List where notificationsent equals to UPDATED_NOTIFICATIONSENT
        defaultPerformanceCycles20190826ShouldNotBeFound("notificationsent.equals=" + UPDATED_NOTIFICATIONSENT);
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByNotificationsentIsInShouldWork() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where notificationsent in DEFAULT_NOTIFICATIONSENT or UPDATED_NOTIFICATIONSENT
        defaultPerformanceCycles20190826ShouldBeFound("notificationsent.in=" + DEFAULT_NOTIFICATIONSENT + "," + UPDATED_NOTIFICATIONSENT);

        // Get all the performanceCycles20190826List where notificationsent equals to UPDATED_NOTIFICATIONSENT
        defaultPerformanceCycles20190826ShouldNotBeFound("notificationsent.in=" + UPDATED_NOTIFICATIONSENT);
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByNotificationsentIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where notificationsent is not null
        defaultPerformanceCycles20190826ShouldBeFound("notificationsent.specified=true");

        // Get all the performanceCycles20190826List where notificationsent is null
        defaultPerformanceCycles20190826ShouldNotBeFound("notificationsent.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByDuedateIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where duedate equals to DEFAULT_DUEDATE
        defaultPerformanceCycles20190826ShouldBeFound("duedate.equals=" + DEFAULT_DUEDATE);

        // Get all the performanceCycles20190826List where duedate equals to UPDATED_DUEDATE
        defaultPerformanceCycles20190826ShouldNotBeFound("duedate.equals=" + UPDATED_DUEDATE);
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByDuedateIsInShouldWork() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where duedate in DEFAULT_DUEDATE or UPDATED_DUEDATE
        defaultPerformanceCycles20190826ShouldBeFound("duedate.in=" + DEFAULT_DUEDATE + "," + UPDATED_DUEDATE);

        // Get all the performanceCycles20190826List where duedate equals to UPDATED_DUEDATE
        defaultPerformanceCycles20190826ShouldNotBeFound("duedate.in=" + UPDATED_DUEDATE);
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByDuedateIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where duedate is not null
        defaultPerformanceCycles20190826ShouldBeFound("duedate.specified=true");

        // Get all the performanceCycles20190826List where duedate is null
        defaultPerformanceCycles20190826ShouldNotBeFound("duedate.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByInitiationdateIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where initiationdate equals to DEFAULT_INITIATIONDATE
        defaultPerformanceCycles20190826ShouldBeFound("initiationdate.equals=" + DEFAULT_INITIATIONDATE);

        // Get all the performanceCycles20190826List where initiationdate equals to UPDATED_INITIATIONDATE
        defaultPerformanceCycles20190826ShouldNotBeFound("initiationdate.equals=" + UPDATED_INITIATIONDATE);
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByInitiationdateIsInShouldWork() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where initiationdate in DEFAULT_INITIATIONDATE or UPDATED_INITIATIONDATE
        defaultPerformanceCycles20190826ShouldBeFound("initiationdate.in=" + DEFAULT_INITIATIONDATE + "," + UPDATED_INITIATIONDATE);

        // Get all the performanceCycles20190826List where initiationdate equals to UPDATED_INITIATIONDATE
        defaultPerformanceCycles20190826ShouldNotBeFound("initiationdate.in=" + UPDATED_INITIATIONDATE);
    }

    @Test
    @Transactional
    void getAllPerformanceCycles20190826sByInitiationdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        // Get all the performanceCycles20190826List where initiationdate is not null
        defaultPerformanceCycles20190826ShouldBeFound("initiationdate.specified=true");

        // Get all the performanceCycles20190826List where initiationdate is null
        defaultPerformanceCycles20190826ShouldNotBeFound("initiationdate.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPerformanceCycles20190826ShouldBeFound(String filter) throws Exception {
        restPerformanceCycles20190826MockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(performanceCycles20190826.getId().intValue())))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.booleanValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR.booleanValue())))
            .andExpect(jsonPath("$.[*].totalresources").value(hasItem(DEFAULT_TOTALRESOURCES.booleanValue())))
            .andExpect(jsonPath("$.[*].pmreviewed").value(hasItem(DEFAULT_PMREVIEWED.booleanValue())))
            .andExpect(jsonPath("$.[*].archreviewed").value(hasItem(DEFAULT_ARCHREVIEWED.booleanValue())))
            .andExpect(jsonPath("$.[*].startdate").value(hasItem(DEFAULT_STARTDATE.toString())))
            .andExpect(jsonPath("$.[*].enddate").value(hasItem(DEFAULT_ENDDATE.toString())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].projectcount").value(hasItem(DEFAULT_PROJECTCOUNT.booleanValue())))
            .andExpect(jsonPath("$.[*].criteria").value(hasItem(DEFAULT_CRITERIA)))
            .andExpect(jsonPath("$.[*].notificationsent").value(hasItem(DEFAULT_NOTIFICATIONSENT.booleanValue())))
            .andExpect(jsonPath("$.[*].duedate").value(hasItem(DEFAULT_DUEDATE.toString())))
            .andExpect(jsonPath("$.[*].initiationdate").value(hasItem(DEFAULT_INITIATIONDATE.toString())));

        // Check, that the count call also returns 1
        restPerformanceCycles20190826MockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPerformanceCycles20190826ShouldNotBeFound(String filter) throws Exception {
        restPerformanceCycles20190826MockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPerformanceCycles20190826MockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPerformanceCycles20190826() throws Exception {
        // Get the performanceCycles20190826
        restPerformanceCycles20190826MockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPerformanceCycles20190826() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        int databaseSizeBeforeUpdate = performanceCycles20190826Repository.findAll().size();

        // Update the performanceCycles20190826
        PerformanceCycles20190826 updatedPerformanceCycles20190826 = performanceCycles20190826Repository
            .findById(performanceCycles20190826.getId())
            .get();
        // Disconnect from session so that the updates on updatedPerformanceCycles20190826 are not directly saved in db
        em.detach(updatedPerformanceCycles20190826);
        updatedPerformanceCycles20190826
            .month(UPDATED_MONTH)
            .year(UPDATED_YEAR)
            .totalresources(UPDATED_TOTALRESOURCES)
            .pmreviewed(UPDATED_PMREVIEWED)
            .archreviewed(UPDATED_ARCHREVIEWED)
            .startdate(UPDATED_STARTDATE)
            .enddate(UPDATED_ENDDATE)
            .isactive(UPDATED_ISACTIVE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .projectcount(UPDATED_PROJECTCOUNT)
            .criteria(UPDATED_CRITERIA)
            .notificationsent(UPDATED_NOTIFICATIONSENT)
            .duedate(UPDATED_DUEDATE)
            .initiationdate(UPDATED_INITIATIONDATE);

        restPerformanceCycles20190826MockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPerformanceCycles20190826.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPerformanceCycles20190826))
            )
            .andExpect(status().isOk());

        // Validate the PerformanceCycles20190826 in the database
        List<PerformanceCycles20190826> performanceCycles20190826List = performanceCycles20190826Repository.findAll();
        assertThat(performanceCycles20190826List).hasSize(databaseSizeBeforeUpdate);
        PerformanceCycles20190826 testPerformanceCycles20190826 = performanceCycles20190826List.get(
            performanceCycles20190826List.size() - 1
        );
        assertThat(testPerformanceCycles20190826.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testPerformanceCycles20190826.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testPerformanceCycles20190826.getTotalresources()).isEqualTo(UPDATED_TOTALRESOURCES);
        assertThat(testPerformanceCycles20190826.getPmreviewed()).isEqualTo(UPDATED_PMREVIEWED);
        assertThat(testPerformanceCycles20190826.getArchreviewed()).isEqualTo(UPDATED_ARCHREVIEWED);
        assertThat(testPerformanceCycles20190826.getStartdate()).isEqualTo(UPDATED_STARTDATE);
        assertThat(testPerformanceCycles20190826.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testPerformanceCycles20190826.getIsactive()).isEqualTo(UPDATED_ISACTIVE);
        assertThat(testPerformanceCycles20190826.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testPerformanceCycles20190826.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testPerformanceCycles20190826.getProjectcount()).isEqualTo(UPDATED_PROJECTCOUNT);
        assertThat(testPerformanceCycles20190826.getCriteria()).isEqualTo(UPDATED_CRITERIA);
        assertThat(testPerformanceCycles20190826.getNotificationsent()).isEqualTo(UPDATED_NOTIFICATIONSENT);
        assertThat(testPerformanceCycles20190826.getDuedate()).isEqualTo(UPDATED_DUEDATE);
        assertThat(testPerformanceCycles20190826.getInitiationdate()).isEqualTo(UPDATED_INITIATIONDATE);
    }

    @Test
    @Transactional
    void putNonExistingPerformanceCycles20190826() throws Exception {
        int databaseSizeBeforeUpdate = performanceCycles20190826Repository.findAll().size();
        performanceCycles20190826.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPerformanceCycles20190826MockMvc
            .perform(
                put(ENTITY_API_URL_ID, performanceCycles20190826.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(performanceCycles20190826))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerformanceCycles20190826 in the database
        List<PerformanceCycles20190826> performanceCycles20190826List = performanceCycles20190826Repository.findAll();
        assertThat(performanceCycles20190826List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPerformanceCycles20190826() throws Exception {
        int databaseSizeBeforeUpdate = performanceCycles20190826Repository.findAll().size();
        performanceCycles20190826.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerformanceCycles20190826MockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(performanceCycles20190826))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerformanceCycles20190826 in the database
        List<PerformanceCycles20190826> performanceCycles20190826List = performanceCycles20190826Repository.findAll();
        assertThat(performanceCycles20190826List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPerformanceCycles20190826() throws Exception {
        int databaseSizeBeforeUpdate = performanceCycles20190826Repository.findAll().size();
        performanceCycles20190826.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerformanceCycles20190826MockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(performanceCycles20190826))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PerformanceCycles20190826 in the database
        List<PerformanceCycles20190826> performanceCycles20190826List = performanceCycles20190826Repository.findAll();
        assertThat(performanceCycles20190826List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePerformanceCycles20190826WithPatch() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        int databaseSizeBeforeUpdate = performanceCycles20190826Repository.findAll().size();

        // Update the performanceCycles20190826 using partial update
        PerformanceCycles20190826 partialUpdatedPerformanceCycles20190826 = new PerformanceCycles20190826();
        partialUpdatedPerformanceCycles20190826.setId(performanceCycles20190826.getId());

        partialUpdatedPerformanceCycles20190826
            .year(UPDATED_YEAR)
            .totalresources(UPDATED_TOTALRESOURCES)
            .pmreviewed(UPDATED_PMREVIEWED)
            .startdate(UPDATED_STARTDATE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .criteria(UPDATED_CRITERIA)
            .notificationsent(UPDATED_NOTIFICATIONSENT);

        restPerformanceCycles20190826MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerformanceCycles20190826.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPerformanceCycles20190826))
            )
            .andExpect(status().isOk());

        // Validate the PerformanceCycles20190826 in the database
        List<PerformanceCycles20190826> performanceCycles20190826List = performanceCycles20190826Repository.findAll();
        assertThat(performanceCycles20190826List).hasSize(databaseSizeBeforeUpdate);
        PerformanceCycles20190826 testPerformanceCycles20190826 = performanceCycles20190826List.get(
            performanceCycles20190826List.size() - 1
        );
        assertThat(testPerformanceCycles20190826.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testPerformanceCycles20190826.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testPerformanceCycles20190826.getTotalresources()).isEqualTo(UPDATED_TOTALRESOURCES);
        assertThat(testPerformanceCycles20190826.getPmreviewed()).isEqualTo(UPDATED_PMREVIEWED);
        assertThat(testPerformanceCycles20190826.getArchreviewed()).isEqualTo(DEFAULT_ARCHREVIEWED);
        assertThat(testPerformanceCycles20190826.getStartdate()).isEqualTo(UPDATED_STARTDATE);
        assertThat(testPerformanceCycles20190826.getEnddate()).isEqualTo(DEFAULT_ENDDATE);
        assertThat(testPerformanceCycles20190826.getIsactive()).isEqualTo(DEFAULT_ISACTIVE);
        assertThat(testPerformanceCycles20190826.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testPerformanceCycles20190826.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testPerformanceCycles20190826.getProjectcount()).isEqualTo(DEFAULT_PROJECTCOUNT);
        assertThat(testPerformanceCycles20190826.getCriteria()).isEqualTo(UPDATED_CRITERIA);
        assertThat(testPerformanceCycles20190826.getNotificationsent()).isEqualTo(UPDATED_NOTIFICATIONSENT);
        assertThat(testPerformanceCycles20190826.getDuedate()).isEqualTo(DEFAULT_DUEDATE);
        assertThat(testPerformanceCycles20190826.getInitiationdate()).isEqualTo(DEFAULT_INITIATIONDATE);
    }

    @Test
    @Transactional
    void fullUpdatePerformanceCycles20190826WithPatch() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        int databaseSizeBeforeUpdate = performanceCycles20190826Repository.findAll().size();

        // Update the performanceCycles20190826 using partial update
        PerformanceCycles20190826 partialUpdatedPerformanceCycles20190826 = new PerformanceCycles20190826();
        partialUpdatedPerformanceCycles20190826.setId(performanceCycles20190826.getId());

        partialUpdatedPerformanceCycles20190826
            .month(UPDATED_MONTH)
            .year(UPDATED_YEAR)
            .totalresources(UPDATED_TOTALRESOURCES)
            .pmreviewed(UPDATED_PMREVIEWED)
            .archreviewed(UPDATED_ARCHREVIEWED)
            .startdate(UPDATED_STARTDATE)
            .enddate(UPDATED_ENDDATE)
            .isactive(UPDATED_ISACTIVE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .projectcount(UPDATED_PROJECTCOUNT)
            .criteria(UPDATED_CRITERIA)
            .notificationsent(UPDATED_NOTIFICATIONSENT)
            .duedate(UPDATED_DUEDATE)
            .initiationdate(UPDATED_INITIATIONDATE);

        restPerformanceCycles20190826MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerformanceCycles20190826.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPerformanceCycles20190826))
            )
            .andExpect(status().isOk());

        // Validate the PerformanceCycles20190826 in the database
        List<PerformanceCycles20190826> performanceCycles20190826List = performanceCycles20190826Repository.findAll();
        assertThat(performanceCycles20190826List).hasSize(databaseSizeBeforeUpdate);
        PerformanceCycles20190826 testPerformanceCycles20190826 = performanceCycles20190826List.get(
            performanceCycles20190826List.size() - 1
        );
        assertThat(testPerformanceCycles20190826.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testPerformanceCycles20190826.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testPerformanceCycles20190826.getTotalresources()).isEqualTo(UPDATED_TOTALRESOURCES);
        assertThat(testPerformanceCycles20190826.getPmreviewed()).isEqualTo(UPDATED_PMREVIEWED);
        assertThat(testPerformanceCycles20190826.getArchreviewed()).isEqualTo(UPDATED_ARCHREVIEWED);
        assertThat(testPerformanceCycles20190826.getStartdate()).isEqualTo(UPDATED_STARTDATE);
        assertThat(testPerformanceCycles20190826.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testPerformanceCycles20190826.getIsactive()).isEqualTo(UPDATED_ISACTIVE);
        assertThat(testPerformanceCycles20190826.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testPerformanceCycles20190826.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testPerformanceCycles20190826.getProjectcount()).isEqualTo(UPDATED_PROJECTCOUNT);
        assertThat(testPerformanceCycles20190826.getCriteria()).isEqualTo(UPDATED_CRITERIA);
        assertThat(testPerformanceCycles20190826.getNotificationsent()).isEqualTo(UPDATED_NOTIFICATIONSENT);
        assertThat(testPerformanceCycles20190826.getDuedate()).isEqualTo(UPDATED_DUEDATE);
        assertThat(testPerformanceCycles20190826.getInitiationdate()).isEqualTo(UPDATED_INITIATIONDATE);
    }

    @Test
    @Transactional
    void patchNonExistingPerformanceCycles20190826() throws Exception {
        int databaseSizeBeforeUpdate = performanceCycles20190826Repository.findAll().size();
        performanceCycles20190826.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPerformanceCycles20190826MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, performanceCycles20190826.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(performanceCycles20190826))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerformanceCycles20190826 in the database
        List<PerformanceCycles20190826> performanceCycles20190826List = performanceCycles20190826Repository.findAll();
        assertThat(performanceCycles20190826List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPerformanceCycles20190826() throws Exception {
        int databaseSizeBeforeUpdate = performanceCycles20190826Repository.findAll().size();
        performanceCycles20190826.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerformanceCycles20190826MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(performanceCycles20190826))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerformanceCycles20190826 in the database
        List<PerformanceCycles20190826> performanceCycles20190826List = performanceCycles20190826Repository.findAll();
        assertThat(performanceCycles20190826List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPerformanceCycles20190826() throws Exception {
        int databaseSizeBeforeUpdate = performanceCycles20190826Repository.findAll().size();
        performanceCycles20190826.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerformanceCycles20190826MockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(performanceCycles20190826))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PerformanceCycles20190826 in the database
        List<PerformanceCycles20190826> performanceCycles20190826List = performanceCycles20190826Repository.findAll();
        assertThat(performanceCycles20190826List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePerformanceCycles20190826() throws Exception {
        // Initialize the database
        performanceCycles20190826Repository.saveAndFlush(performanceCycles20190826);

        int databaseSizeBeforeDelete = performanceCycles20190826Repository.findAll().size();

        // Delete the performanceCycles20190826
        restPerformanceCycles20190826MockMvc
            .perform(delete(ENTITY_API_URL_ID, performanceCycles20190826.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PerformanceCycles20190826> performanceCycles20190826List = performanceCycles20190826Repository.findAll();
        assertThat(performanceCycles20190826List).hasSize(databaseSizeBeforeDelete - 1);
    }
}
