package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.PerformanceCycles;
import com.venturedive.vendian_mono.domain.ProjectCycles;
import com.venturedive.vendian_mono.domain.Ratings;
import com.venturedive.vendian_mono.repository.PerformanceCyclesRepository;
import com.venturedive.vendian_mono.service.PerformanceCyclesService;
import com.venturedive.vendian_mono.service.criteria.PerformanceCyclesCriteria;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PerformanceCyclesResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PerformanceCyclesResourceIT {

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

    private static final String ENTITY_API_URL = "/api/performance-cycles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PerformanceCyclesRepository performanceCyclesRepository;

    @Mock
    private PerformanceCyclesRepository performanceCyclesRepositoryMock;

    @Mock
    private PerformanceCyclesService performanceCyclesServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPerformanceCyclesMockMvc;

    private PerformanceCycles performanceCycles;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PerformanceCycles createEntity(EntityManager em) {
        PerformanceCycles performanceCycles = new PerformanceCycles()
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
        return performanceCycles;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PerformanceCycles createUpdatedEntity(EntityManager em) {
        PerformanceCycles performanceCycles = new PerformanceCycles()
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
        return performanceCycles;
    }

    @BeforeEach
    public void initTest() {
        performanceCycles = createEntity(em);
    }

    @Test
    @Transactional
    void createPerformanceCycles() throws Exception {
        int databaseSizeBeforeCreate = performanceCyclesRepository.findAll().size();
        // Create the PerformanceCycles
        restPerformanceCyclesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(performanceCycles))
            )
            .andExpect(status().isCreated());

        // Validate the PerformanceCycles in the database
        List<PerformanceCycles> performanceCyclesList = performanceCyclesRepository.findAll();
        assertThat(performanceCyclesList).hasSize(databaseSizeBeforeCreate + 1);
        PerformanceCycles testPerformanceCycles = performanceCyclesList.get(performanceCyclesList.size() - 1);
        assertThat(testPerformanceCycles.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testPerformanceCycles.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testPerformanceCycles.getTotalresources()).isEqualTo(DEFAULT_TOTALRESOURCES);
        assertThat(testPerformanceCycles.getPmreviewed()).isEqualTo(DEFAULT_PMREVIEWED);
        assertThat(testPerformanceCycles.getArchreviewed()).isEqualTo(DEFAULT_ARCHREVIEWED);
        assertThat(testPerformanceCycles.getStartdate()).isEqualTo(DEFAULT_STARTDATE);
        assertThat(testPerformanceCycles.getEnddate()).isEqualTo(DEFAULT_ENDDATE);
        assertThat(testPerformanceCycles.getIsactive()).isEqualTo(DEFAULT_ISACTIVE);
        assertThat(testPerformanceCycles.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testPerformanceCycles.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testPerformanceCycles.getProjectcount()).isEqualTo(DEFAULT_PROJECTCOUNT);
        assertThat(testPerformanceCycles.getCriteria()).isEqualTo(DEFAULT_CRITERIA);
        assertThat(testPerformanceCycles.getNotificationsent()).isEqualTo(DEFAULT_NOTIFICATIONSENT);
        assertThat(testPerformanceCycles.getDuedate()).isEqualTo(DEFAULT_DUEDATE);
        assertThat(testPerformanceCycles.getInitiationdate()).isEqualTo(DEFAULT_INITIATIONDATE);
    }

    @Test
    @Transactional
    void createPerformanceCyclesWithExistingId() throws Exception {
        // Create the PerformanceCycles with an existing ID
        performanceCycles.setId(1L);

        int databaseSizeBeforeCreate = performanceCyclesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPerformanceCyclesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(performanceCycles))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerformanceCycles in the database
        List<PerformanceCycles> performanceCyclesList = performanceCyclesRepository.findAll();
        assertThat(performanceCyclesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPerformanceCycles() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList
        restPerformanceCyclesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(performanceCycles.getId().intValue())))
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

    @SuppressWarnings({ "unchecked" })
    void getAllPerformanceCyclesWithEagerRelationshipsIsEnabled() throws Exception {
        when(performanceCyclesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPerformanceCyclesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(performanceCyclesServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPerformanceCyclesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(performanceCyclesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPerformanceCyclesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(performanceCyclesRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPerformanceCycles() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get the performanceCycles
        restPerformanceCyclesMockMvc
            .perform(get(ENTITY_API_URL_ID, performanceCycles.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(performanceCycles.getId().intValue()))
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
    void getPerformanceCyclesByIdFiltering() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        Long id = performanceCycles.getId();

        defaultPerformanceCyclesShouldBeFound("id.equals=" + id);
        defaultPerformanceCyclesShouldNotBeFound("id.notEquals=" + id);

        defaultPerformanceCyclesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPerformanceCyclesShouldNotBeFound("id.greaterThan=" + id);

        defaultPerformanceCyclesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPerformanceCyclesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByMonthIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where month equals to DEFAULT_MONTH
        defaultPerformanceCyclesShouldBeFound("month.equals=" + DEFAULT_MONTH);

        // Get all the performanceCyclesList where month equals to UPDATED_MONTH
        defaultPerformanceCyclesShouldNotBeFound("month.equals=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByMonthIsInShouldWork() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where month in DEFAULT_MONTH or UPDATED_MONTH
        defaultPerformanceCyclesShouldBeFound("month.in=" + DEFAULT_MONTH + "," + UPDATED_MONTH);

        // Get all the performanceCyclesList where month equals to UPDATED_MONTH
        defaultPerformanceCyclesShouldNotBeFound("month.in=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByMonthIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where month is not null
        defaultPerformanceCyclesShouldBeFound("month.specified=true");

        // Get all the performanceCyclesList where month is null
        defaultPerformanceCyclesShouldNotBeFound("month.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where year equals to DEFAULT_YEAR
        defaultPerformanceCyclesShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the performanceCyclesList where year equals to UPDATED_YEAR
        defaultPerformanceCyclesShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByYearIsInShouldWork() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultPerformanceCyclesShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the performanceCyclesList where year equals to UPDATED_YEAR
        defaultPerformanceCyclesShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where year is not null
        defaultPerformanceCyclesShouldBeFound("year.specified=true");

        // Get all the performanceCyclesList where year is null
        defaultPerformanceCyclesShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByTotalresourcesIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where totalresources equals to DEFAULT_TOTALRESOURCES
        defaultPerformanceCyclesShouldBeFound("totalresources.equals=" + DEFAULT_TOTALRESOURCES);

        // Get all the performanceCyclesList where totalresources equals to UPDATED_TOTALRESOURCES
        defaultPerformanceCyclesShouldNotBeFound("totalresources.equals=" + UPDATED_TOTALRESOURCES);
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByTotalresourcesIsInShouldWork() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where totalresources in DEFAULT_TOTALRESOURCES or UPDATED_TOTALRESOURCES
        defaultPerformanceCyclesShouldBeFound("totalresources.in=" + DEFAULT_TOTALRESOURCES + "," + UPDATED_TOTALRESOURCES);

        // Get all the performanceCyclesList where totalresources equals to UPDATED_TOTALRESOURCES
        defaultPerformanceCyclesShouldNotBeFound("totalresources.in=" + UPDATED_TOTALRESOURCES);
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByTotalresourcesIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where totalresources is not null
        defaultPerformanceCyclesShouldBeFound("totalresources.specified=true");

        // Get all the performanceCyclesList where totalresources is null
        defaultPerformanceCyclesShouldNotBeFound("totalresources.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByPmreviewedIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where pmreviewed equals to DEFAULT_PMREVIEWED
        defaultPerformanceCyclesShouldBeFound("pmreviewed.equals=" + DEFAULT_PMREVIEWED);

        // Get all the performanceCyclesList where pmreviewed equals to UPDATED_PMREVIEWED
        defaultPerformanceCyclesShouldNotBeFound("pmreviewed.equals=" + UPDATED_PMREVIEWED);
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByPmreviewedIsInShouldWork() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where pmreviewed in DEFAULT_PMREVIEWED or UPDATED_PMREVIEWED
        defaultPerformanceCyclesShouldBeFound("pmreviewed.in=" + DEFAULT_PMREVIEWED + "," + UPDATED_PMREVIEWED);

        // Get all the performanceCyclesList where pmreviewed equals to UPDATED_PMREVIEWED
        defaultPerformanceCyclesShouldNotBeFound("pmreviewed.in=" + UPDATED_PMREVIEWED);
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByPmreviewedIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where pmreviewed is not null
        defaultPerformanceCyclesShouldBeFound("pmreviewed.specified=true");

        // Get all the performanceCyclesList where pmreviewed is null
        defaultPerformanceCyclesShouldNotBeFound("pmreviewed.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByArchreviewedIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where archreviewed equals to DEFAULT_ARCHREVIEWED
        defaultPerformanceCyclesShouldBeFound("archreviewed.equals=" + DEFAULT_ARCHREVIEWED);

        // Get all the performanceCyclesList where archreviewed equals to UPDATED_ARCHREVIEWED
        defaultPerformanceCyclesShouldNotBeFound("archreviewed.equals=" + UPDATED_ARCHREVIEWED);
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByArchreviewedIsInShouldWork() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where archreviewed in DEFAULT_ARCHREVIEWED or UPDATED_ARCHREVIEWED
        defaultPerformanceCyclesShouldBeFound("archreviewed.in=" + DEFAULT_ARCHREVIEWED + "," + UPDATED_ARCHREVIEWED);

        // Get all the performanceCyclesList where archreviewed equals to UPDATED_ARCHREVIEWED
        defaultPerformanceCyclesShouldNotBeFound("archreviewed.in=" + UPDATED_ARCHREVIEWED);
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByArchreviewedIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where archreviewed is not null
        defaultPerformanceCyclesShouldBeFound("archreviewed.specified=true");

        // Get all the performanceCyclesList where archreviewed is null
        defaultPerformanceCyclesShouldNotBeFound("archreviewed.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByStartdateIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where startdate equals to DEFAULT_STARTDATE
        defaultPerformanceCyclesShouldBeFound("startdate.equals=" + DEFAULT_STARTDATE);

        // Get all the performanceCyclesList where startdate equals to UPDATED_STARTDATE
        defaultPerformanceCyclesShouldNotBeFound("startdate.equals=" + UPDATED_STARTDATE);
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByStartdateIsInShouldWork() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where startdate in DEFAULT_STARTDATE or UPDATED_STARTDATE
        defaultPerformanceCyclesShouldBeFound("startdate.in=" + DEFAULT_STARTDATE + "," + UPDATED_STARTDATE);

        // Get all the performanceCyclesList where startdate equals to UPDATED_STARTDATE
        defaultPerformanceCyclesShouldNotBeFound("startdate.in=" + UPDATED_STARTDATE);
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByStartdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where startdate is not null
        defaultPerformanceCyclesShouldBeFound("startdate.specified=true");

        // Get all the performanceCyclesList where startdate is null
        defaultPerformanceCyclesShouldNotBeFound("startdate.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByEnddateIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where enddate equals to DEFAULT_ENDDATE
        defaultPerformanceCyclesShouldBeFound("enddate.equals=" + DEFAULT_ENDDATE);

        // Get all the performanceCyclesList where enddate equals to UPDATED_ENDDATE
        defaultPerformanceCyclesShouldNotBeFound("enddate.equals=" + UPDATED_ENDDATE);
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByEnddateIsInShouldWork() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where enddate in DEFAULT_ENDDATE or UPDATED_ENDDATE
        defaultPerformanceCyclesShouldBeFound("enddate.in=" + DEFAULT_ENDDATE + "," + UPDATED_ENDDATE);

        // Get all the performanceCyclesList where enddate equals to UPDATED_ENDDATE
        defaultPerformanceCyclesShouldNotBeFound("enddate.in=" + UPDATED_ENDDATE);
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByEnddateIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where enddate is not null
        defaultPerformanceCyclesShouldBeFound("enddate.specified=true");

        // Get all the performanceCyclesList where enddate is null
        defaultPerformanceCyclesShouldNotBeFound("enddate.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByIsactiveIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where isactive equals to DEFAULT_ISACTIVE
        defaultPerformanceCyclesShouldBeFound("isactive.equals=" + DEFAULT_ISACTIVE);

        // Get all the performanceCyclesList where isactive equals to UPDATED_ISACTIVE
        defaultPerformanceCyclesShouldNotBeFound("isactive.equals=" + UPDATED_ISACTIVE);
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByIsactiveIsInShouldWork() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where isactive in DEFAULT_ISACTIVE or UPDATED_ISACTIVE
        defaultPerformanceCyclesShouldBeFound("isactive.in=" + DEFAULT_ISACTIVE + "," + UPDATED_ISACTIVE);

        // Get all the performanceCyclesList where isactive equals to UPDATED_ISACTIVE
        defaultPerformanceCyclesShouldNotBeFound("isactive.in=" + UPDATED_ISACTIVE);
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByIsactiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where isactive is not null
        defaultPerformanceCyclesShouldBeFound("isactive.specified=true");

        // Get all the performanceCyclesList where isactive is null
        defaultPerformanceCyclesShouldNotBeFound("isactive.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where createdat equals to DEFAULT_CREATEDAT
        defaultPerformanceCyclesShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the performanceCyclesList where createdat equals to UPDATED_CREATEDAT
        defaultPerformanceCyclesShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultPerformanceCyclesShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the performanceCyclesList where createdat equals to UPDATED_CREATEDAT
        defaultPerformanceCyclesShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where createdat is not null
        defaultPerformanceCyclesShouldBeFound("createdat.specified=true");

        // Get all the performanceCyclesList where createdat is null
        defaultPerformanceCyclesShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where updatedat equals to DEFAULT_UPDATEDAT
        defaultPerformanceCyclesShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the performanceCyclesList where updatedat equals to UPDATED_UPDATEDAT
        defaultPerformanceCyclesShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultPerformanceCyclesShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the performanceCyclesList where updatedat equals to UPDATED_UPDATEDAT
        defaultPerformanceCyclesShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where updatedat is not null
        defaultPerformanceCyclesShouldBeFound("updatedat.specified=true");

        // Get all the performanceCyclesList where updatedat is null
        defaultPerformanceCyclesShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByProjectcountIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where projectcount equals to DEFAULT_PROJECTCOUNT
        defaultPerformanceCyclesShouldBeFound("projectcount.equals=" + DEFAULT_PROJECTCOUNT);

        // Get all the performanceCyclesList where projectcount equals to UPDATED_PROJECTCOUNT
        defaultPerformanceCyclesShouldNotBeFound("projectcount.equals=" + UPDATED_PROJECTCOUNT);
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByProjectcountIsInShouldWork() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where projectcount in DEFAULT_PROJECTCOUNT or UPDATED_PROJECTCOUNT
        defaultPerformanceCyclesShouldBeFound("projectcount.in=" + DEFAULT_PROJECTCOUNT + "," + UPDATED_PROJECTCOUNT);

        // Get all the performanceCyclesList where projectcount equals to UPDATED_PROJECTCOUNT
        defaultPerformanceCyclesShouldNotBeFound("projectcount.in=" + UPDATED_PROJECTCOUNT);
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByProjectcountIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where projectcount is not null
        defaultPerformanceCyclesShouldBeFound("projectcount.specified=true");

        // Get all the performanceCyclesList where projectcount is null
        defaultPerformanceCyclesShouldNotBeFound("projectcount.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByCriteriaIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where criteria equals to DEFAULT_CRITERIA
        defaultPerformanceCyclesShouldBeFound("criteria.equals=" + DEFAULT_CRITERIA);

        // Get all the performanceCyclesList where criteria equals to UPDATED_CRITERIA
        defaultPerformanceCyclesShouldNotBeFound("criteria.equals=" + UPDATED_CRITERIA);
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByCriteriaIsInShouldWork() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where criteria in DEFAULT_CRITERIA or UPDATED_CRITERIA
        defaultPerformanceCyclesShouldBeFound("criteria.in=" + DEFAULT_CRITERIA + "," + UPDATED_CRITERIA);

        // Get all the performanceCyclesList where criteria equals to UPDATED_CRITERIA
        defaultPerformanceCyclesShouldNotBeFound("criteria.in=" + UPDATED_CRITERIA);
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByCriteriaIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where criteria is not null
        defaultPerformanceCyclesShouldBeFound("criteria.specified=true");

        // Get all the performanceCyclesList where criteria is null
        defaultPerformanceCyclesShouldNotBeFound("criteria.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByCriteriaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where criteria is greater than or equal to DEFAULT_CRITERIA
        defaultPerformanceCyclesShouldBeFound("criteria.greaterThanOrEqual=" + DEFAULT_CRITERIA);

        // Get all the performanceCyclesList where criteria is greater than or equal to UPDATED_CRITERIA
        defaultPerformanceCyclesShouldNotBeFound("criteria.greaterThanOrEqual=" + UPDATED_CRITERIA);
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByCriteriaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where criteria is less than or equal to DEFAULT_CRITERIA
        defaultPerformanceCyclesShouldBeFound("criteria.lessThanOrEqual=" + DEFAULT_CRITERIA);

        // Get all the performanceCyclesList where criteria is less than or equal to SMALLER_CRITERIA
        defaultPerformanceCyclesShouldNotBeFound("criteria.lessThanOrEqual=" + SMALLER_CRITERIA);
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByCriteriaIsLessThanSomething() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where criteria is less than DEFAULT_CRITERIA
        defaultPerformanceCyclesShouldNotBeFound("criteria.lessThan=" + DEFAULT_CRITERIA);

        // Get all the performanceCyclesList where criteria is less than UPDATED_CRITERIA
        defaultPerformanceCyclesShouldBeFound("criteria.lessThan=" + UPDATED_CRITERIA);
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByCriteriaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where criteria is greater than DEFAULT_CRITERIA
        defaultPerformanceCyclesShouldNotBeFound("criteria.greaterThan=" + DEFAULT_CRITERIA);

        // Get all the performanceCyclesList where criteria is greater than SMALLER_CRITERIA
        defaultPerformanceCyclesShouldBeFound("criteria.greaterThan=" + SMALLER_CRITERIA);
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByNotificationsentIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where notificationsent equals to DEFAULT_NOTIFICATIONSENT
        defaultPerformanceCyclesShouldBeFound("notificationsent.equals=" + DEFAULT_NOTIFICATIONSENT);

        // Get all the performanceCyclesList where notificationsent equals to UPDATED_NOTIFICATIONSENT
        defaultPerformanceCyclesShouldNotBeFound("notificationsent.equals=" + UPDATED_NOTIFICATIONSENT);
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByNotificationsentIsInShouldWork() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where notificationsent in DEFAULT_NOTIFICATIONSENT or UPDATED_NOTIFICATIONSENT
        defaultPerformanceCyclesShouldBeFound("notificationsent.in=" + DEFAULT_NOTIFICATIONSENT + "," + UPDATED_NOTIFICATIONSENT);

        // Get all the performanceCyclesList where notificationsent equals to UPDATED_NOTIFICATIONSENT
        defaultPerformanceCyclesShouldNotBeFound("notificationsent.in=" + UPDATED_NOTIFICATIONSENT);
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByNotificationsentIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where notificationsent is not null
        defaultPerformanceCyclesShouldBeFound("notificationsent.specified=true");

        // Get all the performanceCyclesList where notificationsent is null
        defaultPerformanceCyclesShouldNotBeFound("notificationsent.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByDuedateIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where duedate equals to DEFAULT_DUEDATE
        defaultPerformanceCyclesShouldBeFound("duedate.equals=" + DEFAULT_DUEDATE);

        // Get all the performanceCyclesList where duedate equals to UPDATED_DUEDATE
        defaultPerformanceCyclesShouldNotBeFound("duedate.equals=" + UPDATED_DUEDATE);
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByDuedateIsInShouldWork() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where duedate in DEFAULT_DUEDATE or UPDATED_DUEDATE
        defaultPerformanceCyclesShouldBeFound("duedate.in=" + DEFAULT_DUEDATE + "," + UPDATED_DUEDATE);

        // Get all the performanceCyclesList where duedate equals to UPDATED_DUEDATE
        defaultPerformanceCyclesShouldNotBeFound("duedate.in=" + UPDATED_DUEDATE);
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByDuedateIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where duedate is not null
        defaultPerformanceCyclesShouldBeFound("duedate.specified=true");

        // Get all the performanceCyclesList where duedate is null
        defaultPerformanceCyclesShouldNotBeFound("duedate.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByInitiationdateIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where initiationdate equals to DEFAULT_INITIATIONDATE
        defaultPerformanceCyclesShouldBeFound("initiationdate.equals=" + DEFAULT_INITIATIONDATE);

        // Get all the performanceCyclesList where initiationdate equals to UPDATED_INITIATIONDATE
        defaultPerformanceCyclesShouldNotBeFound("initiationdate.equals=" + UPDATED_INITIATIONDATE);
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByInitiationdateIsInShouldWork() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where initiationdate in DEFAULT_INITIATIONDATE or UPDATED_INITIATIONDATE
        defaultPerformanceCyclesShouldBeFound("initiationdate.in=" + DEFAULT_INITIATIONDATE + "," + UPDATED_INITIATIONDATE);

        // Get all the performanceCyclesList where initiationdate equals to UPDATED_INITIATIONDATE
        defaultPerformanceCyclesShouldNotBeFound("initiationdate.in=" + UPDATED_INITIATIONDATE);
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByInitiationdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        // Get all the performanceCyclesList where initiationdate is not null
        defaultPerformanceCyclesShouldBeFound("initiationdate.specified=true");

        // Get all the performanceCyclesList where initiationdate is null
        defaultPerformanceCyclesShouldNotBeFound("initiationdate.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByProjectcycleIsEqualToSomething() throws Exception {
        ProjectCycles projectcycle;
        if (TestUtil.findAll(em, ProjectCycles.class).isEmpty()) {
            performanceCyclesRepository.saveAndFlush(performanceCycles);
            projectcycle = ProjectCyclesResourceIT.createEntity(em);
        } else {
            projectcycle = TestUtil.findAll(em, ProjectCycles.class).get(0);
        }
        em.persist(projectcycle);
        em.flush();
        performanceCycles.addProjectcycle(projectcycle);
        performanceCyclesRepository.saveAndFlush(performanceCycles);
        Long projectcycleId = projectcycle.getId();

        // Get all the performanceCyclesList where projectcycle equals to projectcycleId
        defaultPerformanceCyclesShouldBeFound("projectcycleId.equals=" + projectcycleId);

        // Get all the performanceCyclesList where projectcycle equals to (projectcycleId + 1)
        defaultPerformanceCyclesShouldNotBeFound("projectcycleId.equals=" + (projectcycleId + 1));
    }

    @Test
    @Transactional
    void getAllPerformanceCyclesByEmployeeratingIsEqualToSomething() throws Exception {
        Ratings employeerating;
        if (TestUtil.findAll(em, Ratings.class).isEmpty()) {
            performanceCyclesRepository.saveAndFlush(performanceCycles);
            employeerating = RatingsResourceIT.createEntity(em);
        } else {
            employeerating = TestUtil.findAll(em, Ratings.class).get(0);
        }
        em.persist(employeerating);
        em.flush();
        performanceCycles.addEmployeerating(employeerating);
        performanceCyclesRepository.saveAndFlush(performanceCycles);
        Long employeeratingId = employeerating.getId();

        // Get all the performanceCyclesList where employeerating equals to employeeratingId
        defaultPerformanceCyclesShouldBeFound("employeeratingId.equals=" + employeeratingId);

        // Get all the performanceCyclesList where employeerating equals to (employeeratingId + 1)
        defaultPerformanceCyclesShouldNotBeFound("employeeratingId.equals=" + (employeeratingId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPerformanceCyclesShouldBeFound(String filter) throws Exception {
        restPerformanceCyclesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(performanceCycles.getId().intValue())))
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
        restPerformanceCyclesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPerformanceCyclesShouldNotBeFound(String filter) throws Exception {
        restPerformanceCyclesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPerformanceCyclesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPerformanceCycles() throws Exception {
        // Get the performanceCycles
        restPerformanceCyclesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPerformanceCycles() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        int databaseSizeBeforeUpdate = performanceCyclesRepository.findAll().size();

        // Update the performanceCycles
        PerformanceCycles updatedPerformanceCycles = performanceCyclesRepository.findById(performanceCycles.getId()).get();
        // Disconnect from session so that the updates on updatedPerformanceCycles are not directly saved in db
        em.detach(updatedPerformanceCycles);
        updatedPerformanceCycles
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

        restPerformanceCyclesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPerformanceCycles.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPerformanceCycles))
            )
            .andExpect(status().isOk());

        // Validate the PerformanceCycles in the database
        List<PerformanceCycles> performanceCyclesList = performanceCyclesRepository.findAll();
        assertThat(performanceCyclesList).hasSize(databaseSizeBeforeUpdate);
        PerformanceCycles testPerformanceCycles = performanceCyclesList.get(performanceCyclesList.size() - 1);
        assertThat(testPerformanceCycles.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testPerformanceCycles.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testPerformanceCycles.getTotalresources()).isEqualTo(UPDATED_TOTALRESOURCES);
        assertThat(testPerformanceCycles.getPmreviewed()).isEqualTo(UPDATED_PMREVIEWED);
        assertThat(testPerformanceCycles.getArchreviewed()).isEqualTo(UPDATED_ARCHREVIEWED);
        assertThat(testPerformanceCycles.getStartdate()).isEqualTo(UPDATED_STARTDATE);
        assertThat(testPerformanceCycles.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testPerformanceCycles.getIsactive()).isEqualTo(UPDATED_ISACTIVE);
        assertThat(testPerformanceCycles.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testPerformanceCycles.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testPerformanceCycles.getProjectcount()).isEqualTo(UPDATED_PROJECTCOUNT);
        assertThat(testPerformanceCycles.getCriteria()).isEqualTo(UPDATED_CRITERIA);
        assertThat(testPerformanceCycles.getNotificationsent()).isEqualTo(UPDATED_NOTIFICATIONSENT);
        assertThat(testPerformanceCycles.getDuedate()).isEqualTo(UPDATED_DUEDATE);
        assertThat(testPerformanceCycles.getInitiationdate()).isEqualTo(UPDATED_INITIATIONDATE);
    }

    @Test
    @Transactional
    void putNonExistingPerformanceCycles() throws Exception {
        int databaseSizeBeforeUpdate = performanceCyclesRepository.findAll().size();
        performanceCycles.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPerformanceCyclesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, performanceCycles.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(performanceCycles))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerformanceCycles in the database
        List<PerformanceCycles> performanceCyclesList = performanceCyclesRepository.findAll();
        assertThat(performanceCyclesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPerformanceCycles() throws Exception {
        int databaseSizeBeforeUpdate = performanceCyclesRepository.findAll().size();
        performanceCycles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerformanceCyclesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(performanceCycles))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerformanceCycles in the database
        List<PerformanceCycles> performanceCyclesList = performanceCyclesRepository.findAll();
        assertThat(performanceCyclesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPerformanceCycles() throws Exception {
        int databaseSizeBeforeUpdate = performanceCyclesRepository.findAll().size();
        performanceCycles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerformanceCyclesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(performanceCycles))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PerformanceCycles in the database
        List<PerformanceCycles> performanceCyclesList = performanceCyclesRepository.findAll();
        assertThat(performanceCyclesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePerformanceCyclesWithPatch() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        int databaseSizeBeforeUpdate = performanceCyclesRepository.findAll().size();

        // Update the performanceCycles using partial update
        PerformanceCycles partialUpdatedPerformanceCycles = new PerformanceCycles();
        partialUpdatedPerformanceCycles.setId(performanceCycles.getId());

        partialUpdatedPerformanceCycles
            .year(UPDATED_YEAR)
            .archreviewed(UPDATED_ARCHREVIEWED)
            .startdate(UPDATED_STARTDATE)
            .enddate(UPDATED_ENDDATE)
            .updatedat(UPDATED_UPDATEDAT)
            .projectcount(UPDATED_PROJECTCOUNT)
            .criteria(UPDATED_CRITERIA)
            .notificationsent(UPDATED_NOTIFICATIONSENT)
            .duedate(UPDATED_DUEDATE)
            .initiationdate(UPDATED_INITIATIONDATE);

        restPerformanceCyclesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerformanceCycles.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPerformanceCycles))
            )
            .andExpect(status().isOk());

        // Validate the PerformanceCycles in the database
        List<PerformanceCycles> performanceCyclesList = performanceCyclesRepository.findAll();
        assertThat(performanceCyclesList).hasSize(databaseSizeBeforeUpdate);
        PerformanceCycles testPerformanceCycles = performanceCyclesList.get(performanceCyclesList.size() - 1);
        assertThat(testPerformanceCycles.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testPerformanceCycles.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testPerformanceCycles.getTotalresources()).isEqualTo(DEFAULT_TOTALRESOURCES);
        assertThat(testPerformanceCycles.getPmreviewed()).isEqualTo(DEFAULT_PMREVIEWED);
        assertThat(testPerformanceCycles.getArchreviewed()).isEqualTo(UPDATED_ARCHREVIEWED);
        assertThat(testPerformanceCycles.getStartdate()).isEqualTo(UPDATED_STARTDATE);
        assertThat(testPerformanceCycles.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testPerformanceCycles.getIsactive()).isEqualTo(DEFAULT_ISACTIVE);
        assertThat(testPerformanceCycles.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testPerformanceCycles.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testPerformanceCycles.getProjectcount()).isEqualTo(UPDATED_PROJECTCOUNT);
        assertThat(testPerformanceCycles.getCriteria()).isEqualTo(UPDATED_CRITERIA);
        assertThat(testPerformanceCycles.getNotificationsent()).isEqualTo(UPDATED_NOTIFICATIONSENT);
        assertThat(testPerformanceCycles.getDuedate()).isEqualTo(UPDATED_DUEDATE);
        assertThat(testPerformanceCycles.getInitiationdate()).isEqualTo(UPDATED_INITIATIONDATE);
    }

    @Test
    @Transactional
    void fullUpdatePerformanceCyclesWithPatch() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        int databaseSizeBeforeUpdate = performanceCyclesRepository.findAll().size();

        // Update the performanceCycles using partial update
        PerformanceCycles partialUpdatedPerformanceCycles = new PerformanceCycles();
        partialUpdatedPerformanceCycles.setId(performanceCycles.getId());

        partialUpdatedPerformanceCycles
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

        restPerformanceCyclesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerformanceCycles.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPerformanceCycles))
            )
            .andExpect(status().isOk());

        // Validate the PerformanceCycles in the database
        List<PerformanceCycles> performanceCyclesList = performanceCyclesRepository.findAll();
        assertThat(performanceCyclesList).hasSize(databaseSizeBeforeUpdate);
        PerformanceCycles testPerformanceCycles = performanceCyclesList.get(performanceCyclesList.size() - 1);
        assertThat(testPerformanceCycles.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testPerformanceCycles.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testPerformanceCycles.getTotalresources()).isEqualTo(UPDATED_TOTALRESOURCES);
        assertThat(testPerformanceCycles.getPmreviewed()).isEqualTo(UPDATED_PMREVIEWED);
        assertThat(testPerformanceCycles.getArchreviewed()).isEqualTo(UPDATED_ARCHREVIEWED);
        assertThat(testPerformanceCycles.getStartdate()).isEqualTo(UPDATED_STARTDATE);
        assertThat(testPerformanceCycles.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testPerformanceCycles.getIsactive()).isEqualTo(UPDATED_ISACTIVE);
        assertThat(testPerformanceCycles.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testPerformanceCycles.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testPerformanceCycles.getProjectcount()).isEqualTo(UPDATED_PROJECTCOUNT);
        assertThat(testPerformanceCycles.getCriteria()).isEqualTo(UPDATED_CRITERIA);
        assertThat(testPerformanceCycles.getNotificationsent()).isEqualTo(UPDATED_NOTIFICATIONSENT);
        assertThat(testPerformanceCycles.getDuedate()).isEqualTo(UPDATED_DUEDATE);
        assertThat(testPerformanceCycles.getInitiationdate()).isEqualTo(UPDATED_INITIATIONDATE);
    }

    @Test
    @Transactional
    void patchNonExistingPerformanceCycles() throws Exception {
        int databaseSizeBeforeUpdate = performanceCyclesRepository.findAll().size();
        performanceCycles.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPerformanceCyclesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, performanceCycles.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(performanceCycles))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerformanceCycles in the database
        List<PerformanceCycles> performanceCyclesList = performanceCyclesRepository.findAll();
        assertThat(performanceCyclesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPerformanceCycles() throws Exception {
        int databaseSizeBeforeUpdate = performanceCyclesRepository.findAll().size();
        performanceCycles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerformanceCyclesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(performanceCycles))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerformanceCycles in the database
        List<PerformanceCycles> performanceCyclesList = performanceCyclesRepository.findAll();
        assertThat(performanceCyclesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPerformanceCycles() throws Exception {
        int databaseSizeBeforeUpdate = performanceCyclesRepository.findAll().size();
        performanceCycles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerformanceCyclesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(performanceCycles))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PerformanceCycles in the database
        List<PerformanceCycles> performanceCyclesList = performanceCyclesRepository.findAll();
        assertThat(performanceCyclesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePerformanceCycles() throws Exception {
        // Initialize the database
        performanceCyclesRepository.saveAndFlush(performanceCycles);

        int databaseSizeBeforeDelete = performanceCyclesRepository.findAll().size();

        // Delete the performanceCycles
        restPerformanceCyclesMockMvc
            .perform(delete(ENTITY_API_URL_ID, performanceCycles.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PerformanceCycles> performanceCyclesList = performanceCyclesRepository.findAll();
        assertThat(performanceCyclesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
